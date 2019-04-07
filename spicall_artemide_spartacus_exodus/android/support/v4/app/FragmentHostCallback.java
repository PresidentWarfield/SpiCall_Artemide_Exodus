package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.SimpleArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class FragmentHostCallback<E>
  extends FragmentContainer
{
  private final Activity mActivity;
  private SimpleArrayMap<String, LoaderManager> mAllLoaderManagers;
  private boolean mCheckedForLoaderManager;
  final Context mContext;
  final FragmentManagerImpl mFragmentManager = new FragmentManagerImpl();
  private final Handler mHandler;
  private LoaderManagerImpl mLoaderManager;
  private boolean mLoadersStarted;
  private boolean mRetainLoaders;
  final int mWindowAnimations;
  
  FragmentHostCallback(Activity paramActivity, Context paramContext, Handler paramHandler, int paramInt)
  {
    this.mActivity = paramActivity;
    this.mContext = paramContext;
    this.mHandler = paramHandler;
    this.mWindowAnimations = paramInt;
  }
  
  public FragmentHostCallback(Context paramContext, Handler paramHandler, int paramInt)
  {
    this(localActivity, paramContext, paramHandler, paramInt);
  }
  
  FragmentHostCallback(FragmentActivity paramFragmentActivity)
  {
    this(paramFragmentActivity, paramFragmentActivity, paramFragmentActivity.mHandler, 0);
  }
  
  void doLoaderDestroy()
  {
    LoaderManagerImpl localLoaderManagerImpl = this.mLoaderManager;
    if (localLoaderManagerImpl == null) {
      return;
    }
    localLoaderManagerImpl.doDestroy();
  }
  
  void doLoaderRetain()
  {
    LoaderManagerImpl localLoaderManagerImpl = this.mLoaderManager;
    if (localLoaderManagerImpl == null) {
      return;
    }
    localLoaderManagerImpl.doRetain();
  }
  
  void doLoaderStart()
  {
    if (this.mLoadersStarted) {
      return;
    }
    this.mLoadersStarted = true;
    LoaderManagerImpl localLoaderManagerImpl = this.mLoaderManager;
    if (localLoaderManagerImpl != null)
    {
      localLoaderManagerImpl.doStart();
    }
    else if (!this.mCheckedForLoaderManager)
    {
      this.mLoaderManager = getLoaderManager("(root)", this.mLoadersStarted, false);
      localLoaderManagerImpl = this.mLoaderManager;
      if ((localLoaderManagerImpl != null) && (!localLoaderManagerImpl.mStarted)) {
        this.mLoaderManager.doStart();
      }
    }
    this.mCheckedForLoaderManager = true;
  }
  
  void doLoaderStop(boolean paramBoolean)
  {
    this.mRetainLoaders = paramBoolean;
    LoaderManagerImpl localLoaderManagerImpl = this.mLoaderManager;
    if (localLoaderManagerImpl == null) {
      return;
    }
    if (!this.mLoadersStarted) {
      return;
    }
    this.mLoadersStarted = false;
    if (paramBoolean) {
      localLoaderManagerImpl.doRetain();
    } else {
      localLoaderManagerImpl.doStop();
    }
  }
  
  void dumpLoaders(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mLoadersStarted=");
    paramPrintWriter.println(this.mLoadersStarted);
    if (this.mLoaderManager != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("Loader Manager ");
      paramPrintWriter.print(Integer.toHexString(System.identityHashCode(this.mLoaderManager)));
      paramPrintWriter.println(":");
      LoaderManagerImpl localLoaderManagerImpl = this.mLoaderManager;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append("  ");
      localLoaderManagerImpl.dump(localStringBuilder.toString(), paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    }
  }
  
  Activity getActivity()
  {
    return this.mActivity;
  }
  
  Context getContext()
  {
    return this.mContext;
  }
  
  FragmentManagerImpl getFragmentManagerImpl()
  {
    return this.mFragmentManager;
  }
  
  Handler getHandler()
  {
    return this.mHandler;
  }
  
  LoaderManagerImpl getLoaderManager(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mAllLoaderManagers == null) {
      this.mAllLoaderManagers = new SimpleArrayMap();
    }
    LoaderManagerImpl localLoaderManagerImpl = (LoaderManagerImpl)this.mAllLoaderManagers.get(paramString);
    if ((localLoaderManagerImpl == null) && (paramBoolean2))
    {
      localLoaderManagerImpl = new LoaderManagerImpl(paramString, this, paramBoolean1);
      this.mAllLoaderManagers.put(paramString, localLoaderManagerImpl);
      paramString = localLoaderManagerImpl;
    }
    else
    {
      paramString = localLoaderManagerImpl;
      if (paramBoolean1)
      {
        paramString = localLoaderManagerImpl;
        if (localLoaderManagerImpl != null)
        {
          paramString = localLoaderManagerImpl;
          if (!localLoaderManagerImpl.mStarted)
          {
            localLoaderManagerImpl.doStart();
            paramString = localLoaderManagerImpl;
          }
        }
      }
    }
    return paramString;
  }
  
  LoaderManagerImpl getLoaderManagerImpl()
  {
    LoaderManagerImpl localLoaderManagerImpl = this.mLoaderManager;
    if (localLoaderManagerImpl != null) {
      return localLoaderManagerImpl;
    }
    this.mCheckedForLoaderManager = true;
    this.mLoaderManager = getLoaderManager("(root)", this.mLoadersStarted, true);
    return this.mLoaderManager;
  }
  
  boolean getRetainLoaders()
  {
    return this.mRetainLoaders;
  }
  
  void inactivateFragment(String paramString)
  {
    Object localObject = this.mAllLoaderManagers;
    if (localObject != null)
    {
      localObject = (LoaderManagerImpl)((SimpleArrayMap)localObject).get(paramString);
      if ((localObject != null) && (!((LoaderManagerImpl)localObject).mRetaining))
      {
        ((LoaderManagerImpl)localObject).doDestroy();
        this.mAllLoaderManagers.remove(paramString);
      }
    }
  }
  
  void onAttachFragment(Fragment paramFragment) {}
  
  public void onDump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString) {}
  
  public View onFindViewById(int paramInt)
  {
    return null;
  }
  
  public abstract E onGetHost();
  
  public LayoutInflater onGetLayoutInflater()
  {
    return (LayoutInflater)this.mContext.getSystemService("layout_inflater");
  }
  
  public int onGetWindowAnimations()
  {
    return this.mWindowAnimations;
  }
  
  public boolean onHasView()
  {
    return true;
  }
  
  public boolean onHasWindowAnimations()
  {
    return true;
  }
  
  public void onRequestPermissionsFromFragment(Fragment paramFragment, String[] paramArrayOfString, int paramInt) {}
  
  public boolean onShouldSaveFragmentState(Fragment paramFragment)
  {
    return true;
  }
  
  public boolean onShouldShowRequestPermissionRationale(String paramString)
  {
    return false;
  }
  
  public void onStartActivityFromFragment(Fragment paramFragment, Intent paramIntent, int paramInt)
  {
    onStartActivityFromFragment(paramFragment, paramIntent, paramInt, null);
  }
  
  public void onStartActivityFromFragment(Fragment paramFragment, Intent paramIntent, int paramInt, Bundle paramBundle)
  {
    if (paramInt == -1)
    {
      this.mContext.startActivity(paramIntent);
      return;
    }
    throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
  }
  
  public void onStartIntentSenderFromFragment(Fragment paramFragment, IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4, Bundle paramBundle)
  {
    if (paramInt1 == -1)
    {
      ActivityCompat.startIntentSenderForResult(this.mActivity, paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
      return;
    }
    throw new IllegalStateException("Starting intent sender with a requestCode requires a FragmentActivity host");
  }
  
  public void onSupportInvalidateOptionsMenu() {}
  
  void reportLoaderStart()
  {
    Object localObject1 = this.mAllLoaderManagers;
    if (localObject1 != null)
    {
      int i = ((SimpleArrayMap)localObject1).size();
      localObject1 = new LoaderManagerImpl[i];
      for (int j = i - 1; j >= 0; j--) {
        localObject1[j] = ((LoaderManagerImpl)this.mAllLoaderManagers.valueAt(j));
      }
      for (j = 0; j < i; j++)
      {
        Object localObject2 = localObject1[j];
        ((LoaderManagerImpl)localObject2).finishRetain();
        ((LoaderManagerImpl)localObject2).doReportStart();
      }
    }
  }
  
  void restoreLoaderNonConfig(SimpleArrayMap<String, LoaderManager> paramSimpleArrayMap)
  {
    if (paramSimpleArrayMap != null)
    {
      int i = 0;
      int j = paramSimpleArrayMap.size();
      while (i < j)
      {
        ((LoaderManagerImpl)paramSimpleArrayMap.valueAt(i)).updateHostController(this);
        i++;
      }
    }
    this.mAllLoaderManagers = paramSimpleArrayMap;
  }
  
  SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig()
  {
    Object localObject1 = this.mAllLoaderManagers;
    int i = 0;
    int j = 0;
    if (localObject1 != null)
    {
      int k = ((SimpleArrayMap)localObject1).size();
      localObject1 = new LoaderManagerImpl[k];
      for (i = k - 1; i >= 0; i--) {
        localObject1[i] = ((LoaderManagerImpl)this.mAllLoaderManagers.valueAt(i));
      }
      boolean bool = getRetainLoaders();
      i = 0;
      while (j < k)
      {
        Object localObject2 = localObject1[j];
        if ((!((LoaderManagerImpl)localObject2).mRetaining) && (bool))
        {
          if (!((LoaderManagerImpl)localObject2).mStarted) {
            ((LoaderManagerImpl)localObject2).doStart();
          }
          ((LoaderManagerImpl)localObject2).doRetain();
        }
        if (((LoaderManagerImpl)localObject2).mRetaining)
        {
          i = 1;
        }
        else
        {
          ((LoaderManagerImpl)localObject2).doDestroy();
          this.mAllLoaderManagers.remove(((LoaderManagerImpl)localObject2).mWho);
        }
        j++;
      }
    }
    if (i != 0) {
      return this.mAllLoaderManagers;
    }
    return null;
  }
}


/* Location:              ~/android/support/v4/app/FragmentHostCallback.class
 *
 * Reversed by:           J
 */