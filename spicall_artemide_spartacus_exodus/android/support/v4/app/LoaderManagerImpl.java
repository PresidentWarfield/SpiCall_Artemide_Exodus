package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.OnLoadCanceledListener;
import android.support.v4.content.Loader.OnLoadCompleteListener;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

class LoaderManagerImpl
  extends LoaderManager
{
  static boolean DEBUG = false;
  static final String TAG = "LoaderManager";
  boolean mCreatingLoader;
  FragmentHostCallback mHost;
  final SparseArrayCompat<LoaderInfo> mInactiveLoaders = new SparseArrayCompat();
  final SparseArrayCompat<LoaderInfo> mLoaders = new SparseArrayCompat();
  boolean mRetaining;
  boolean mRetainingStarted;
  boolean mStarted;
  final String mWho;
  
  LoaderManagerImpl(String paramString, FragmentHostCallback paramFragmentHostCallback, boolean paramBoolean)
  {
    this.mWho = paramString;
    this.mHost = paramFragmentHostCallback;
    this.mStarted = paramBoolean;
  }
  
  private LoaderInfo createAndInstallLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<Object> paramLoaderCallbacks)
  {
    try
    {
      this.mCreatingLoader = true;
      paramBundle = createLoader(paramInt, paramBundle, paramLoaderCallbacks);
      installLoader(paramBundle);
      return paramBundle;
    }
    finally
    {
      this.mCreatingLoader = false;
    }
  }
  
  private LoaderInfo createLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<Object> paramLoaderCallbacks)
  {
    LoaderInfo localLoaderInfo = new LoaderInfo(paramInt, paramBundle, paramLoaderCallbacks);
    localLoaderInfo.mLoader = paramLoaderCallbacks.onCreateLoader(paramInt, paramBundle);
    return localLoaderInfo;
  }
  
  public void destroyLoader(int paramInt)
  {
    if (!this.mCreatingLoader)
    {
      Object localObject;
      if (DEBUG)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("destroyLoader in ");
        ((StringBuilder)localObject).append(this);
        ((StringBuilder)localObject).append(" of ");
        ((StringBuilder)localObject).append(paramInt);
        Log.v("LoaderManager", ((StringBuilder)localObject).toString());
      }
      int i = this.mLoaders.indexOfKey(paramInt);
      if (i >= 0)
      {
        localObject = (LoaderInfo)this.mLoaders.valueAt(i);
        this.mLoaders.removeAt(i);
        ((LoaderInfo)localObject).destroy();
      }
      paramInt = this.mInactiveLoaders.indexOfKey(paramInt);
      if (paramInt >= 0)
      {
        localObject = (LoaderInfo)this.mInactiveLoaders.valueAt(paramInt);
        this.mInactiveLoaders.removeAt(paramInt);
        ((LoaderInfo)localObject).destroy();
      }
      if ((this.mHost != null) && (!hasRunningLoaders())) {
        this.mHost.mFragmentManager.startPendingDeferredFragments();
      }
      return;
    }
    throw new IllegalStateException("Called while creating a loader");
  }
  
  void doDestroy()
  {
    StringBuilder localStringBuilder;
    if (!this.mRetaining)
    {
      if (DEBUG)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Destroying Active in ");
        localStringBuilder.append(this);
        Log.v("LoaderManager", localStringBuilder.toString());
      }
      for (i = this.mLoaders.size() - 1; i >= 0; i--) {
        ((LoaderInfo)this.mLoaders.valueAt(i)).destroy();
      }
      this.mLoaders.clear();
    }
    if (DEBUG)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Destroying Inactive in ");
      localStringBuilder.append(this);
      Log.v("LoaderManager", localStringBuilder.toString());
    }
    for (int i = this.mInactiveLoaders.size() - 1; i >= 0; i--) {
      ((LoaderInfo)this.mInactiveLoaders.valueAt(i)).destroy();
    }
    this.mInactiveLoaders.clear();
    this.mHost = null;
  }
  
  void doReportNextStart()
  {
    for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
      ((LoaderInfo)this.mLoaders.valueAt(i)).mReportNextStart = true;
    }
  }
  
  void doReportStart()
  {
    for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
      ((LoaderInfo)this.mLoaders.valueAt(i)).reportStart();
    }
  }
  
  void doRetain()
  {
    Object localObject;
    if (DEBUG)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Retaining in ");
      ((StringBuilder)localObject).append(this);
      Log.v("LoaderManager", ((StringBuilder)localObject).toString());
    }
    if (!this.mStarted)
    {
      localObject = new RuntimeException("here");
      ((RuntimeException)localObject).fillInStackTrace();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Called doRetain when not started: ");
      localStringBuilder.append(this);
      Log.w("LoaderManager", localStringBuilder.toString(), (Throwable)localObject);
      return;
    }
    this.mRetaining = true;
    this.mStarted = false;
    for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
      ((LoaderInfo)this.mLoaders.valueAt(i)).retain();
    }
  }
  
  void doStart()
  {
    StringBuilder localStringBuilder;
    if (DEBUG)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Starting in ");
      localStringBuilder.append(this);
      Log.v("LoaderManager", localStringBuilder.toString());
    }
    if (this.mStarted)
    {
      RuntimeException localRuntimeException = new RuntimeException("here");
      localRuntimeException.fillInStackTrace();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Called doStart when already started: ");
      localStringBuilder.append(this);
      Log.w("LoaderManager", localStringBuilder.toString(), localRuntimeException);
      return;
    }
    this.mStarted = true;
    for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
      ((LoaderInfo)this.mLoaders.valueAt(i)).start();
    }
  }
  
  void doStop()
  {
    Object localObject;
    if (DEBUG)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Stopping in ");
      ((StringBuilder)localObject).append(this);
      Log.v("LoaderManager", ((StringBuilder)localObject).toString());
    }
    if (!this.mStarted)
    {
      localObject = new RuntimeException("here");
      ((RuntimeException)localObject).fillInStackTrace();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Called doStop when not started: ");
      localStringBuilder.append(this);
      Log.w("LoaderManager", localStringBuilder.toString(), (Throwable)localObject);
      return;
    }
    for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
      ((LoaderInfo)this.mLoaders.valueAt(i)).stop();
    }
    this.mStarted = false;
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    int i = this.mLoaders.size();
    int j = 0;
    Object localObject1;
    Object localObject2;
    if (i > 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Active Loaders:");
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(paramString);
      ((StringBuilder)localObject1).append("    ");
      localObject2 = ((StringBuilder)localObject1).toString();
      for (i = 0; i < this.mLoaders.size(); i++)
      {
        localObject1 = (LoaderInfo)this.mLoaders.valueAt(i);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  #");
        paramPrintWriter.print(this.mLoaders.keyAt(i));
        paramPrintWriter.print(": ");
        paramPrintWriter.println(((LoaderInfo)localObject1).toString());
        ((LoaderInfo)localObject1).dump((String)localObject2, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
      }
    }
    if (this.mInactiveLoaders.size() > 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Inactive Loaders:");
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(paramString);
      ((StringBuilder)localObject1).append("    ");
      localObject1 = ((StringBuilder)localObject1).toString();
      for (i = j; i < this.mInactiveLoaders.size(); i++)
      {
        localObject2 = (LoaderInfo)this.mInactiveLoaders.valueAt(i);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  #");
        paramPrintWriter.print(this.mInactiveLoaders.keyAt(i));
        paramPrintWriter.print(": ");
        paramPrintWriter.println(((LoaderInfo)localObject2).toString());
        ((LoaderInfo)localObject2).dump((String)localObject1, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
      }
    }
  }
  
  void finishRetain()
  {
    if (this.mRetaining)
    {
      if (DEBUG)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Finished Retaining in ");
        localStringBuilder.append(this);
        Log.v("LoaderManager", localStringBuilder.toString());
      }
      this.mRetaining = false;
      for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
        ((LoaderInfo)this.mLoaders.valueAt(i)).finishRetain();
      }
    }
  }
  
  public <D> Loader<D> getLoader(int paramInt)
  {
    if (!this.mCreatingLoader)
    {
      LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.get(paramInt);
      if (localLoaderInfo != null)
      {
        if (localLoaderInfo.mPendingLoader != null) {
          return localLoaderInfo.mPendingLoader.mLoader;
        }
        return localLoaderInfo.mLoader;
      }
      return null;
    }
    throw new IllegalStateException("Called while creating a loader");
  }
  
  public boolean hasRunningLoaders()
  {
    int i = this.mLoaders.size();
    int j = 0;
    boolean bool1 = false;
    while (j < i)
    {
      LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.valueAt(j);
      boolean bool2;
      if ((localLoaderInfo.mStarted) && (!localLoaderInfo.mDeliveredData)) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      bool1 |= bool2;
      j++;
    }
    return bool1;
  }
  
  public <D> Loader<D> initLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<D> paramLoaderCallbacks)
  {
    if (!this.mCreatingLoader)
    {
      LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.get(paramInt);
      if (DEBUG)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("initLoader in ");
        localStringBuilder.append(this);
        localStringBuilder.append(": args=");
        localStringBuilder.append(paramBundle);
        Log.v("LoaderManager", localStringBuilder.toString());
      }
      if (localLoaderInfo == null)
      {
        paramLoaderCallbacks = createAndInstallLoader(paramInt, paramBundle, paramLoaderCallbacks);
        paramBundle = paramLoaderCallbacks;
        if (DEBUG)
        {
          paramBundle = new StringBuilder();
          paramBundle.append("  Created new loader ");
          paramBundle.append(paramLoaderCallbacks);
          Log.v("LoaderManager", paramBundle.toString());
          paramBundle = paramLoaderCallbacks;
        }
      }
      else
      {
        if (DEBUG)
        {
          paramBundle = new StringBuilder();
          paramBundle.append("  Re-using existing loader ");
          paramBundle.append(localLoaderInfo);
          Log.v("LoaderManager", paramBundle.toString());
        }
        localLoaderInfo.mCallbacks = paramLoaderCallbacks;
        paramBundle = localLoaderInfo;
      }
      if ((paramBundle.mHaveData) && (this.mStarted)) {
        paramBundle.callOnLoadFinished(paramBundle.mLoader, paramBundle.mData);
      }
      return paramBundle.mLoader;
    }
    throw new IllegalStateException("Called while creating a loader");
  }
  
  void installLoader(LoaderInfo paramLoaderInfo)
  {
    this.mLoaders.put(paramLoaderInfo.mId, paramLoaderInfo);
    if (this.mStarted) {
      paramLoaderInfo.start();
    }
  }
  
  public <D> Loader<D> restartLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<D> paramLoaderCallbacks)
  {
    if (!this.mCreatingLoader)
    {
      LoaderInfo localLoaderInfo1 = (LoaderInfo)this.mLoaders.get(paramInt);
      StringBuilder localStringBuilder;
      if (DEBUG)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("restartLoader in ");
        localStringBuilder.append(this);
        localStringBuilder.append(": args=");
        localStringBuilder.append(paramBundle);
        Log.v("LoaderManager", localStringBuilder.toString());
      }
      if (localLoaderInfo1 != null)
      {
        LoaderInfo localLoaderInfo2 = (LoaderInfo)this.mInactiveLoaders.get(paramInt);
        if (localLoaderInfo2 != null)
        {
          if (localLoaderInfo1.mHaveData)
          {
            if (DEBUG)
            {
              localStringBuilder = new StringBuilder();
              localStringBuilder.append("  Removing last inactive loader: ");
              localStringBuilder.append(localLoaderInfo1);
              Log.v("LoaderManager", localStringBuilder.toString());
            }
            localLoaderInfo2.mDeliveredData = false;
            localLoaderInfo2.destroy();
            localLoaderInfo1.mLoader.abandon();
            this.mInactiveLoaders.put(paramInt, localLoaderInfo1);
          }
          else if (!localLoaderInfo1.cancel())
          {
            if (DEBUG) {
              Log.v("LoaderManager", "  Current loader is stopped; replacing");
            }
            this.mLoaders.put(paramInt, null);
            localLoaderInfo1.destroy();
          }
          else
          {
            if (DEBUG) {
              Log.v("LoaderManager", "  Current loader is running; configuring pending loader");
            }
            if (localLoaderInfo1.mPendingLoader != null)
            {
              if (DEBUG)
              {
                localStringBuilder = new StringBuilder();
                localStringBuilder.append("  Removing pending loader: ");
                localStringBuilder.append(localLoaderInfo1.mPendingLoader);
                Log.v("LoaderManager", localStringBuilder.toString());
              }
              localLoaderInfo1.mPendingLoader.destroy();
              localLoaderInfo1.mPendingLoader = null;
            }
            if (DEBUG) {
              Log.v("LoaderManager", "  Enqueuing as new pending loader");
            }
            localLoaderInfo1.mPendingLoader = createLoader(paramInt, paramBundle, paramLoaderCallbacks);
            return localLoaderInfo1.mPendingLoader.mLoader;
          }
        }
        else
        {
          if (DEBUG)
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("  Making last loader inactive: ");
            localStringBuilder.append(localLoaderInfo1);
            Log.v("LoaderManager", localStringBuilder.toString());
          }
          localLoaderInfo1.mLoader.abandon();
          this.mInactiveLoaders.put(paramInt, localLoaderInfo1);
        }
      }
      return createAndInstallLoader(paramInt, paramBundle, paramLoaderCallbacks).mLoader;
    }
    throw new IllegalStateException("Called while creating a loader");
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("LoaderManager{");
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    localStringBuilder.append(" in ");
    DebugUtils.buildShortClassTag(this.mHost, localStringBuilder);
    localStringBuilder.append("}}");
    return localStringBuilder.toString();
  }
  
  void updateHostController(FragmentHostCallback paramFragmentHostCallback)
  {
    this.mHost = paramFragmentHostCallback;
  }
  
  final class LoaderInfo
    implements Loader.OnLoadCanceledListener<Object>, Loader.OnLoadCompleteListener<Object>
  {
    final Bundle mArgs;
    LoaderManager.LoaderCallbacks<Object> mCallbacks;
    Object mData;
    boolean mDeliveredData;
    boolean mDestroyed;
    boolean mHaveData;
    final int mId;
    boolean mListenerRegistered;
    Loader<Object> mLoader;
    LoaderInfo mPendingLoader;
    boolean mReportNextStart;
    boolean mRetaining;
    boolean mRetainingStarted;
    boolean mStarted;
    
    public LoaderInfo(Bundle paramBundle, LoaderManager.LoaderCallbacks<Object> paramLoaderCallbacks)
    {
      this.mId = paramBundle;
      this.mArgs = paramLoaderCallbacks;
      LoaderManager.LoaderCallbacks localLoaderCallbacks;
      this.mCallbacks = localLoaderCallbacks;
    }
    
    void callOnLoadFinished(Loader<Object> paramLoader, Object paramObject)
    {
      if (this.mCallbacks != null)
      {
        String str = null;
        if (LoaderManagerImpl.this.mHost != null)
        {
          str = LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause;
          LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = "onLoadFinished";
        }
        try
        {
          if (LoaderManagerImpl.DEBUG)
          {
            StringBuilder localStringBuilder = new java/lang/StringBuilder;
            localStringBuilder.<init>();
            localStringBuilder.append("  onLoadFinished in ");
            localStringBuilder.append(paramLoader);
            localStringBuilder.append(": ");
            localStringBuilder.append(paramLoader.dataToString(paramObject));
            Log.v("LoaderManager", localStringBuilder.toString());
          }
          this.mCallbacks.onLoadFinished(paramLoader, paramObject);
          if (LoaderManagerImpl.this.mHost != null) {
            LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = str;
          }
          this.mDeliveredData = true;
        }
        finally
        {
          if (LoaderManagerImpl.this.mHost != null) {
            LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = str;
          }
        }
      }
    }
    
    boolean cancel()
    {
      Object localObject;
      if (LoaderManagerImpl.DEBUG)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("  Canceling: ");
        ((StringBuilder)localObject).append(this);
        Log.v("LoaderManager", ((StringBuilder)localObject).toString());
      }
      if (this.mStarted)
      {
        localObject = this.mLoader;
        if ((localObject != null) && (this.mListenerRegistered))
        {
          boolean bool = ((Loader)localObject).cancelLoad();
          if (!bool) {
            onLoadCanceled(this.mLoader);
          }
          return bool;
        }
      }
      return false;
    }
    
    /* Error */
    void destroy()
    {
      // Byte code:
      //   0: getstatic 76	android/support/v4/app/LoaderManagerImpl:DEBUG	Z
      //   3: ifeq +34 -> 37
      //   6: new 78	java/lang/StringBuilder
      //   9: dup
      //   10: invokespecial 79	java/lang/StringBuilder:<init>	()V
      //   13: astore_1
      //   14: aload_1
      //   15: ldc -121
      //   17: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   20: pop
      //   21: aload_1
      //   22: aload_0
      //   23: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   26: pop
      //   27: ldc 98
      //   29: aload_1
      //   30: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   33: invokestatic 108	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
      //   36: pop
      //   37: aload_0
      //   38: iconst_1
      //   39: putfield 137	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mDestroyed	Z
      //   42: aload_0
      //   43: getfield 114	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mDeliveredData	Z
      //   46: istore_2
      //   47: aload_0
      //   48: iconst_0
      //   49: putfield 114	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mDeliveredData	Z
      //   52: aload_0
      //   53: getfield 50	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mCallbacks	Landroid/support/v4/app/LoaderManager$LoaderCallbacks;
      //   56: ifnull +169 -> 225
      //   59: aload_0
      //   60: getfield 123	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mLoader	Landroid/support/v4/content/Loader;
      //   63: ifnull +162 -> 225
      //   66: aload_0
      //   67: getfield 139	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mHaveData	Z
      //   70: ifeq +155 -> 225
      //   73: iload_2
      //   74: ifeq +151 -> 225
      //   77: getstatic 76	android/support/v4/app/LoaderManagerImpl:DEBUG	Z
      //   80: ifeq +34 -> 114
      //   83: new 78	java/lang/StringBuilder
      //   86: dup
      //   87: invokespecial 79	java/lang/StringBuilder:<init>	()V
      //   90: astore_1
      //   91: aload_1
      //   92: ldc -115
      //   94: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   97: pop
      //   98: aload_1
      //   99: aload_0
      //   100: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   103: pop
      //   104: ldc 98
      //   106: aload_1
      //   107: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   110: invokestatic 108	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
      //   113: pop
      //   114: aload_0
      //   115: getfield 41	android/support/v4/app/LoaderManagerImpl$LoaderInfo:this$0	Landroid/support/v4/app/LoaderManagerImpl;
      //   118: getfield 59	android/support/v4/app/LoaderManagerImpl:mHost	Landroid/support/v4/app/FragmentHostCallback;
      //   121: ifnull +35 -> 156
      //   124: aload_0
      //   125: getfield 41	android/support/v4/app/LoaderManagerImpl$LoaderInfo:this$0	Landroid/support/v4/app/LoaderManagerImpl;
      //   128: getfield 59	android/support/v4/app/LoaderManagerImpl:mHost	Landroid/support/v4/app/FragmentHostCallback;
      //   131: getfield 65	android/support/v4/app/FragmentHostCallback:mFragmentManager	Landroid/support/v4/app/FragmentManagerImpl;
      //   134: getfield 71	android/support/v4/app/FragmentManagerImpl:mNoTransactionsBecause	Ljava/lang/String;
      //   137: astore_1
      //   138: aload_0
      //   139: getfield 41	android/support/v4/app/LoaderManagerImpl$LoaderInfo:this$0	Landroid/support/v4/app/LoaderManagerImpl;
      //   142: getfield 59	android/support/v4/app/LoaderManagerImpl:mHost	Landroid/support/v4/app/FragmentHostCallback;
      //   145: getfield 65	android/support/v4/app/FragmentHostCallback:mFragmentManager	Landroid/support/v4/app/FragmentManagerImpl;
      //   148: ldc -113
      //   150: putfield 71	android/support/v4/app/FragmentManagerImpl:mNoTransactionsBecause	Ljava/lang/String;
      //   153: goto +5 -> 158
      //   156: aconst_null
      //   157: astore_1
      //   158: aload_0
      //   159: getfield 50	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mCallbacks	Landroid/support/v4/app/LoaderManager$LoaderCallbacks;
      //   162: aload_0
      //   163: getfield 123	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mLoader	Landroid/support/v4/content/Loader;
      //   166: invokeinterface 145 2 0
      //   171: aload_0
      //   172: getfield 41	android/support/v4/app/LoaderManagerImpl$LoaderInfo:this$0	Landroid/support/v4/app/LoaderManagerImpl;
      //   175: getfield 59	android/support/v4/app/LoaderManagerImpl:mHost	Landroid/support/v4/app/FragmentHostCallback;
      //   178: ifnull +47 -> 225
      //   181: aload_0
      //   182: getfield 41	android/support/v4/app/LoaderManagerImpl$LoaderInfo:this$0	Landroid/support/v4/app/LoaderManagerImpl;
      //   185: getfield 59	android/support/v4/app/LoaderManagerImpl:mHost	Landroid/support/v4/app/FragmentHostCallback;
      //   188: getfield 65	android/support/v4/app/FragmentHostCallback:mFragmentManager	Landroid/support/v4/app/FragmentManagerImpl;
      //   191: aload_1
      //   192: putfield 71	android/support/v4/app/FragmentManagerImpl:mNoTransactionsBecause	Ljava/lang/String;
      //   195: goto +30 -> 225
      //   198: astore_3
      //   199: aload_0
      //   200: getfield 41	android/support/v4/app/LoaderManagerImpl$LoaderInfo:this$0	Landroid/support/v4/app/LoaderManagerImpl;
      //   203: getfield 59	android/support/v4/app/LoaderManagerImpl:mHost	Landroid/support/v4/app/FragmentHostCallback;
      //   206: ifnull +17 -> 223
      //   209: aload_0
      //   210: getfield 41	android/support/v4/app/LoaderManagerImpl$LoaderInfo:this$0	Landroid/support/v4/app/LoaderManagerImpl;
      //   213: getfield 59	android/support/v4/app/LoaderManagerImpl:mHost	Landroid/support/v4/app/FragmentHostCallback;
      //   216: getfield 65	android/support/v4/app/FragmentHostCallback:mFragmentManager	Landroid/support/v4/app/FragmentManagerImpl;
      //   219: aload_1
      //   220: putfield 71	android/support/v4/app/FragmentManagerImpl:mNoTransactionsBecause	Ljava/lang/String;
      //   223: aload_3
      //   224: athrow
      //   225: aload_0
      //   226: aconst_null
      //   227: putfield 50	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mCallbacks	Landroid/support/v4/app/LoaderManager$LoaderCallbacks;
      //   230: aload_0
      //   231: aconst_null
      //   232: putfield 147	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mData	Ljava/lang/Object;
      //   235: aload_0
      //   236: iconst_0
      //   237: putfield 139	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mHaveData	Z
      //   240: aload_0
      //   241: getfield 123	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mLoader	Landroid/support/v4/content/Loader;
      //   244: astore_1
      //   245: aload_1
      //   246: ifnull +35 -> 281
      //   249: aload_0
      //   250: getfield 125	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mListenerRegistered	Z
      //   253: ifeq +21 -> 274
      //   256: aload_0
      //   257: iconst_0
      //   258: putfield 125	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mListenerRegistered	Z
      //   261: aload_1
      //   262: aload_0
      //   263: invokevirtual 151	android/support/v4/content/Loader:unregisterListener	(Landroid/support/v4/content/Loader$OnLoadCompleteListener;)V
      //   266: aload_0
      //   267: getfield 123	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mLoader	Landroid/support/v4/content/Loader;
      //   270: aload_0
      //   271: invokevirtual 155	android/support/v4/content/Loader:unregisterOnLoadCanceledListener	(Landroid/support/v4/content/Loader$OnLoadCanceledListener;)V
      //   274: aload_0
      //   275: getfield 123	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mLoader	Landroid/support/v4/content/Loader;
      //   278: invokevirtual 158	android/support/v4/content/Loader:reset	()V
      //   281: aload_0
      //   282: getfield 160	android/support/v4/app/LoaderManagerImpl$LoaderInfo:mPendingLoader	Landroid/support/v4/app/LoaderManagerImpl$LoaderInfo;
      //   285: astore_1
      //   286: aload_1
      //   287: ifnull +7 -> 294
      //   290: aload_1
      //   291: invokevirtual 162	android/support/v4/app/LoaderManagerImpl$LoaderInfo:destroy	()V
      //   294: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	295	0	this	LoaderInfo
      //   13	278	1	localObject1	Object
      //   46	28	2	bool	boolean
      //   198	26	3	localObject2	Object
      // Exception table:
      //   from	to	target	type
      //   158	171	198	finally
    }
    
    public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mId=");
      paramPrintWriter.print(this.mId);
      paramPrintWriter.print(" mArgs=");
      paramPrintWriter.println(this.mArgs);
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mCallbacks=");
      paramPrintWriter.println(this.mCallbacks);
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mLoader=");
      paramPrintWriter.println(this.mLoader);
      Object localObject = this.mLoader;
      StringBuilder localStringBuilder;
      if (localObject != null)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString);
        localStringBuilder.append("  ");
        ((Loader)localObject).dump(localStringBuilder.toString(), paramFileDescriptor, paramPrintWriter, paramArrayOfString);
      }
      if ((this.mHaveData) || (this.mDeliveredData))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mHaveData=");
        paramPrintWriter.print(this.mHaveData);
        paramPrintWriter.print("  mDeliveredData=");
        paramPrintWriter.println(this.mDeliveredData);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mData=");
        paramPrintWriter.println(this.mData);
      }
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mStarted=");
      paramPrintWriter.print(this.mStarted);
      paramPrintWriter.print(" mReportNextStart=");
      paramPrintWriter.print(this.mReportNextStart);
      paramPrintWriter.print(" mDestroyed=");
      paramPrintWriter.println(this.mDestroyed);
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mRetaining=");
      paramPrintWriter.print(this.mRetaining);
      paramPrintWriter.print(" mRetainingStarted=");
      paramPrintWriter.print(this.mRetainingStarted);
      paramPrintWriter.print(" mListenerRegistered=");
      paramPrintWriter.println(this.mListenerRegistered);
      if (this.mPendingLoader != null)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Pending Loader ");
        paramPrintWriter.print(this.mPendingLoader);
        paramPrintWriter.println(":");
        localObject = this.mPendingLoader;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString);
        localStringBuilder.append("  ");
        ((LoaderInfo)localObject).dump(localStringBuilder.toString(), paramFileDescriptor, paramPrintWriter, paramArrayOfString);
      }
    }
    
    void finishRetain()
    {
      if (this.mRetaining)
      {
        if (LoaderManagerImpl.DEBUG)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("  Finished Retaining: ");
          localStringBuilder.append(this);
          Log.v("LoaderManager", localStringBuilder.toString());
        }
        this.mRetaining = false;
        boolean bool = this.mStarted;
        if ((bool != this.mRetainingStarted) && (!bool)) {
          stop();
        }
      }
      if ((this.mStarted) && (this.mHaveData) && (!this.mReportNextStart)) {
        callOnLoadFinished(this.mLoader, this.mData);
      }
    }
    
    public void onLoadCanceled(Loader<Object> paramLoader)
    {
      if (LoaderManagerImpl.DEBUG)
      {
        paramLoader = new StringBuilder();
        paramLoader.append("onLoadCanceled: ");
        paramLoader.append(this);
        Log.v("LoaderManager", paramLoader.toString());
      }
      if (this.mDestroyed)
      {
        if (LoaderManagerImpl.DEBUG) {
          Log.v("LoaderManager", "  Ignoring load canceled -- destroyed");
        }
        return;
      }
      if (LoaderManagerImpl.this.mLoaders.get(this.mId) != this)
      {
        if (LoaderManagerImpl.DEBUG) {
          Log.v("LoaderManager", "  Ignoring load canceled -- not active");
        }
        return;
      }
      LoaderInfo localLoaderInfo = this.mPendingLoader;
      if (localLoaderInfo != null)
      {
        if (LoaderManagerImpl.DEBUG)
        {
          paramLoader = new StringBuilder();
          paramLoader.append("  Switching to pending loader: ");
          paramLoader.append(localLoaderInfo);
          Log.v("LoaderManager", paramLoader.toString());
        }
        this.mPendingLoader = null;
        LoaderManagerImpl.this.mLoaders.put(this.mId, null);
        destroy();
        LoaderManagerImpl.this.installLoader(localLoaderInfo);
      }
    }
    
    public void onLoadComplete(Loader<Object> paramLoader, Object paramObject)
    {
      if (LoaderManagerImpl.DEBUG)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("onLoadComplete: ");
        ((StringBuilder)localObject).append(this);
        Log.v("LoaderManager", ((StringBuilder)localObject).toString());
      }
      if (this.mDestroyed)
      {
        if (LoaderManagerImpl.DEBUG) {
          Log.v("LoaderManager", "  Ignoring load complete -- destroyed");
        }
        return;
      }
      if (LoaderManagerImpl.this.mLoaders.get(this.mId) != this)
      {
        if (LoaderManagerImpl.DEBUG) {
          Log.v("LoaderManager", "  Ignoring load complete -- not active");
        }
        return;
      }
      Object localObject = this.mPendingLoader;
      if (localObject != null)
      {
        if (LoaderManagerImpl.DEBUG)
        {
          paramLoader = new StringBuilder();
          paramLoader.append("  Switching to pending loader: ");
          paramLoader.append(localObject);
          Log.v("LoaderManager", paramLoader.toString());
        }
        this.mPendingLoader = null;
        LoaderManagerImpl.this.mLoaders.put(this.mId, null);
        destroy();
        LoaderManagerImpl.this.installLoader((LoaderInfo)localObject);
        return;
      }
      if ((this.mData != paramObject) || (!this.mHaveData))
      {
        this.mData = paramObject;
        this.mHaveData = true;
        if (this.mStarted) {
          callOnLoadFinished(paramLoader, paramObject);
        }
      }
      paramLoader = (LoaderInfo)LoaderManagerImpl.this.mInactiveLoaders.get(this.mId);
      if ((paramLoader != null) && (paramLoader != this))
      {
        paramLoader.mDeliveredData = false;
        paramLoader.destroy();
        LoaderManagerImpl.this.mInactiveLoaders.remove(this.mId);
      }
      if ((LoaderManagerImpl.this.mHost != null) && (!LoaderManagerImpl.this.hasRunningLoaders())) {
        LoaderManagerImpl.this.mHost.mFragmentManager.startPendingDeferredFragments();
      }
    }
    
    void reportStart()
    {
      if ((this.mStarted) && (this.mReportNextStart))
      {
        this.mReportNextStart = false;
        if ((this.mHaveData) && (!this.mRetaining)) {
          callOnLoadFinished(this.mLoader, this.mData);
        }
      }
    }
    
    void retain()
    {
      if (LoaderManagerImpl.DEBUG)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("  Retaining: ");
        localStringBuilder.append(this);
        Log.v("LoaderManager", localStringBuilder.toString());
      }
      this.mRetaining = true;
      this.mRetainingStarted = this.mStarted;
      this.mStarted = false;
      this.mCallbacks = null;
    }
    
    void start()
    {
      if ((this.mRetaining) && (this.mRetainingStarted))
      {
        this.mStarted = true;
        return;
      }
      if (this.mStarted) {
        return;
      }
      this.mStarted = true;
      if (LoaderManagerImpl.DEBUG)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("  Starting: ");
        ((StringBuilder)localObject).append(this);
        Log.v("LoaderManager", ((StringBuilder)localObject).toString());
      }
      if (this.mLoader == null)
      {
        localObject = this.mCallbacks;
        if (localObject != null) {
          this.mLoader = ((LoaderManager.LoaderCallbacks)localObject).onCreateLoader(this.mId, this.mArgs);
        }
      }
      Object localObject = this.mLoader;
      if (localObject != null)
      {
        if ((localObject.getClass().isMemberClass()) && (!Modifier.isStatic(this.mLoader.getClass().getModifiers())))
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Object returned from onCreateLoader must not be a non-static inner member class: ");
          ((StringBuilder)localObject).append(this.mLoader);
          throw new IllegalArgumentException(((StringBuilder)localObject).toString());
        }
        if (!this.mListenerRegistered)
        {
          this.mLoader.registerListener(this.mId, this);
          this.mLoader.registerOnLoadCanceledListener(this);
          this.mListenerRegistered = true;
        }
        this.mLoader.startLoading();
      }
    }
    
    void stop()
    {
      Object localObject;
      if (LoaderManagerImpl.DEBUG)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("  Stopping: ");
        ((StringBuilder)localObject).append(this);
        Log.v("LoaderManager", ((StringBuilder)localObject).toString());
      }
      this.mStarted = false;
      if (!this.mRetaining)
      {
        localObject = this.mLoader;
        if ((localObject != null) && (this.mListenerRegistered))
        {
          this.mListenerRegistered = false;
          ((Loader)localObject).unregisterListener(this);
          this.mLoader.unregisterOnLoadCanceledListener(this);
          this.mLoader.stopLoading();
        }
      }
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder(64);
      localStringBuilder.append("LoaderInfo{");
      localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
      localStringBuilder.append(" #");
      localStringBuilder.append(this.mId);
      localStringBuilder.append(" : ");
      DebugUtils.buildShortClassTag(this.mLoader, localStringBuilder);
      localStringBuilder.append("}}");
      return localStringBuilder.toString();
    }
  }
}


/* Location:              ~/android/support/v4/app/LoaderManagerImpl.class
 *
 * Reversed by:           J
 */