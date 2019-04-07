package android.support.v4.media;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class MediaBrowserServiceCompat
  extends Service
{
  static final boolean DEBUG = Log.isLoggable("MBServiceCompat", 3);
  private static final float EPSILON = 1.0E-5F;
  public static final String KEY_MEDIA_ITEM = "media_item";
  public static final String KEY_SEARCH_RESULTS = "search_results";
  static final int RESULT_ERROR = -1;
  static final int RESULT_FLAG_ON_LOAD_ITEM_NOT_IMPLEMENTED = 2;
  static final int RESULT_FLAG_ON_SEARCH_NOT_IMPLEMENTED = 4;
  static final int RESULT_FLAG_OPTION_NOT_HANDLED = 1;
  static final int RESULT_OK = 0;
  static final int RESULT_PROGRESS_UPDATE = 1;
  public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
  static final String TAG = "MBServiceCompat";
  final ArrayMap<IBinder, ConnectionRecord> mConnections = new ArrayMap();
  ConnectionRecord mCurConnection;
  final ServiceHandler mHandler = new ServiceHandler();
  private MediaBrowserServiceImpl mImpl;
  MediaSessionCompat.Token mSession;
  
  void addSubscription(String paramString, ConnectionRecord paramConnectionRecord, IBinder paramIBinder, Bundle paramBundle)
  {
    Object localObject1 = (List)paramConnectionRecord.subscriptions.get(paramString);
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = new ArrayList();
    }
    localObject1 = ((List)localObject2).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      Pair localPair = (Pair)((Iterator)localObject1).next();
      if ((paramIBinder == localPair.first) && (MediaBrowserCompatUtils.areSameOptions(paramBundle, (Bundle)localPair.second))) {
        return;
      }
    }
    ((List)localObject2).add(new Pair(paramIBinder, paramBundle));
    paramConnectionRecord.subscriptions.put(paramString, localObject2);
    performLoadChildren(paramString, paramConnectionRecord, paramBundle);
  }
  
  List<MediaBrowserCompat.MediaItem> applyOptions(List<MediaBrowserCompat.MediaItem> paramList, Bundle paramBundle)
  {
    if (paramList == null) {
      return null;
    }
    int i = paramBundle.getInt("android.media.browse.extra.PAGE", -1);
    int j = paramBundle.getInt("android.media.browse.extra.PAGE_SIZE", -1);
    if ((i == -1) && (j == -1)) {
      return paramList;
    }
    int k = j * i;
    int m = k + j;
    if ((i >= 0) && (j >= 1) && (k < paramList.size()))
    {
      j = m;
      if (m > paramList.size()) {
        j = paramList.size();
      }
      return paramList.subList(k, j);
    }
    return Collections.EMPTY_LIST;
  }
  
  public void dump(FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString) {}
  
  public final Bundle getBrowserRootHints()
  {
    return this.mImpl.getBrowserRootHints();
  }
  
  public MediaSessionCompat.Token getSessionToken()
  {
    return this.mSession;
  }
  
  boolean isValidPackage(String paramString, int paramInt)
  {
    if (paramString == null) {
      return false;
    }
    String[] arrayOfString = getPackageManager().getPackagesForUid(paramInt);
    int i = arrayOfString.length;
    for (paramInt = 0; paramInt < i; paramInt++) {
      if (arrayOfString[paramInt].equals(paramString)) {
        return true;
      }
    }
    return false;
  }
  
  public void notifyChildrenChanged(String paramString)
  {
    if (paramString != null)
    {
      this.mImpl.notifyChildrenChanged(paramString, null);
      return;
    }
    throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
  }
  
  public void notifyChildrenChanged(String paramString, Bundle paramBundle)
  {
    if (paramString != null)
    {
      if (paramBundle != null)
      {
        this.mImpl.notifyChildrenChanged(paramString, paramBundle);
        return;
      }
      throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
    }
    throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mImpl.onBind(paramIntent);
  }
  
  public void onCreate()
  {
    super.onCreate();
    if (Build.VERSION.SDK_INT >= 26) {
      this.mImpl = new MediaBrowserServiceImplApi24();
    } else if (Build.VERSION.SDK_INT >= 23) {
      this.mImpl = new MediaBrowserServiceImplApi23();
    } else if (Build.VERSION.SDK_INT >= 21) {
      this.mImpl = new MediaBrowserServiceImplApi21();
    } else {
      this.mImpl = new MediaBrowserServiceImplBase();
    }
    this.mImpl.onCreate();
  }
  
  public void onCustomAction(String paramString, Bundle paramBundle, Result<Bundle> paramResult)
  {
    paramResult.sendError(null);
  }
  
  public abstract BrowserRoot onGetRoot(String paramString, int paramInt, Bundle paramBundle);
  
  public abstract void onLoadChildren(String paramString, Result<List<MediaBrowserCompat.MediaItem>> paramResult);
  
  public void onLoadChildren(String paramString, Result<List<MediaBrowserCompat.MediaItem>> paramResult, Bundle paramBundle)
  {
    paramResult.setFlags(1);
    onLoadChildren(paramString, paramResult);
  }
  
  public void onLoadItem(String paramString, Result<MediaBrowserCompat.MediaItem> paramResult)
  {
    paramResult.setFlags(2);
    paramResult.sendResult(null);
  }
  
  public void onSearch(String paramString, Bundle paramBundle, Result<List<MediaBrowserCompat.MediaItem>> paramResult)
  {
    paramResult.setFlags(4);
    paramResult.sendResult(null);
  }
  
  void performCustomAction(String paramString, Bundle paramBundle, ConnectionRecord paramConnectionRecord, final ResultReceiver paramResultReceiver)
  {
    paramResultReceiver = new Result(paramString)
    {
      void onErrorSent(Bundle paramAnonymousBundle)
      {
        paramResultReceiver.send(-1, paramAnonymousBundle);
      }
      
      void onProgressUpdateSent(Bundle paramAnonymousBundle)
      {
        paramResultReceiver.send(1, paramAnonymousBundle);
      }
      
      void onResultSent(Bundle paramAnonymousBundle)
      {
        paramResultReceiver.send(0, paramAnonymousBundle);
      }
    };
    this.mCurConnection = paramConnectionRecord;
    onCustomAction(paramString, paramBundle, paramResultReceiver);
    this.mCurConnection = null;
    if (paramResultReceiver.isDone()) {
      return;
    }
    paramConnectionRecord = new StringBuilder();
    paramConnectionRecord.append("onCustomAction must call detach() or sendResult() or sendError() before returning for action=");
    paramConnectionRecord.append(paramString);
    paramConnectionRecord.append(" extras=");
    paramConnectionRecord.append(paramBundle);
    throw new IllegalStateException(paramConnectionRecord.toString());
  }
  
  void performLoadChildren(final String paramString, final ConnectionRecord paramConnectionRecord, final Bundle paramBundle)
  {
    Result local1 = new Result(paramString)
    {
      void onResultSent(List<MediaBrowserCompat.MediaItem> paramAnonymousList)
      {
        if (MediaBrowserServiceCompat.this.mConnections.get(paramConnectionRecord.callbacks.asBinder()) != paramConnectionRecord)
        {
          if (MediaBrowserServiceCompat.DEBUG)
          {
            paramAnonymousList = new StringBuilder();
            paramAnonymousList.append("Not sending onLoadChildren result for connection that has been disconnected. pkg=");
            paramAnonymousList.append(paramConnectionRecord.pkg);
            paramAnonymousList.append(" id=");
            paramAnonymousList.append(paramString);
            Log.d("MBServiceCompat", paramAnonymousList.toString());
          }
          return;
        }
        Object localObject = paramAnonymousList;
        if ((getFlags() & 0x1) != 0) {
          localObject = MediaBrowserServiceCompat.this.applyOptions(paramAnonymousList, paramBundle);
        }
        try
        {
          paramConnectionRecord.callbacks.onLoadChildren(paramString, (List)localObject, paramBundle);
        }
        catch (RemoteException paramAnonymousList)
        {
          paramAnonymousList = new StringBuilder();
          paramAnonymousList.append("Calling onLoadChildren() failed for id=");
          paramAnonymousList.append(paramString);
          paramAnonymousList.append(" package=");
          paramAnonymousList.append(paramConnectionRecord.pkg);
          Log.w("MBServiceCompat", paramAnonymousList.toString());
        }
      }
    };
    this.mCurConnection = paramConnectionRecord;
    if (paramBundle == null) {
      onLoadChildren(paramString, local1);
    } else {
      onLoadChildren(paramString, local1, paramBundle);
    }
    this.mCurConnection = null;
    if (local1.isDone()) {
      return;
    }
    paramBundle = new StringBuilder();
    paramBundle.append("onLoadChildren must call detach() or sendResult() before returning for package=");
    paramBundle.append(paramConnectionRecord.pkg);
    paramBundle.append(" id=");
    paramBundle.append(paramString);
    throw new IllegalStateException(paramBundle.toString());
  }
  
  void performLoadItem(String paramString, ConnectionRecord paramConnectionRecord, final ResultReceiver paramResultReceiver)
  {
    paramResultReceiver = new Result(paramString)
    {
      void onResultSent(MediaBrowserCompat.MediaItem paramAnonymousMediaItem)
      {
        if ((getFlags() & 0x2) != 0)
        {
          paramResultReceiver.send(-1, null);
          return;
        }
        Bundle localBundle = new Bundle();
        localBundle.putParcelable("media_item", paramAnonymousMediaItem);
        paramResultReceiver.send(0, localBundle);
      }
    };
    this.mCurConnection = paramConnectionRecord;
    onLoadItem(paramString, paramResultReceiver);
    this.mCurConnection = null;
    if (paramResultReceiver.isDone()) {
      return;
    }
    paramConnectionRecord = new StringBuilder();
    paramConnectionRecord.append("onLoadItem must call detach() or sendResult() before returning for id=");
    paramConnectionRecord.append(paramString);
    throw new IllegalStateException(paramConnectionRecord.toString());
  }
  
  void performSearch(String paramString, Bundle paramBundle, ConnectionRecord paramConnectionRecord, final ResultReceiver paramResultReceiver)
  {
    paramResultReceiver = new Result(paramString)
    {
      void onResultSent(List<MediaBrowserCompat.MediaItem> paramAnonymousList)
      {
        if (((getFlags() & 0x4) == 0) && (paramAnonymousList != null))
        {
          Bundle localBundle = new Bundle();
          localBundle.putParcelableArray("search_results", (Parcelable[])paramAnonymousList.toArray(new MediaBrowserCompat.MediaItem[0]));
          paramResultReceiver.send(0, localBundle);
          return;
        }
        paramResultReceiver.send(-1, null);
      }
    };
    this.mCurConnection = paramConnectionRecord;
    onSearch(paramString, paramBundle, paramResultReceiver);
    this.mCurConnection = null;
    if (paramResultReceiver.isDone()) {
      return;
    }
    paramBundle = new StringBuilder();
    paramBundle.append("onSearch must call detach() or sendResult() before returning for query=");
    paramBundle.append(paramString);
    throw new IllegalStateException(paramBundle.toString());
  }
  
  boolean removeSubscription(String paramString, ConnectionRecord paramConnectionRecord, IBinder paramIBinder)
  {
    boolean bool1 = true;
    boolean bool2 = false;
    boolean bool3 = false;
    if (paramIBinder == null)
    {
      if (paramConnectionRecord.subscriptions.remove(paramString) != null) {
        bool3 = bool1;
      } else {
        bool3 = false;
      }
      return bool3;
    }
    List localList = (List)paramConnectionRecord.subscriptions.get(paramString);
    bool1 = bool2;
    if (localList != null)
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        if (paramIBinder == ((Pair)localIterator.next()).first)
        {
          localIterator.remove();
          bool3 = true;
        }
      }
      bool1 = bool3;
      if (localList.size() == 0)
      {
        paramConnectionRecord.subscriptions.remove(paramString);
        bool1 = bool3;
      }
    }
    return bool1;
  }
  
  public void setSessionToken(MediaSessionCompat.Token paramToken)
  {
    if (paramToken != null)
    {
      if (this.mSession == null)
      {
        this.mSession = paramToken;
        this.mImpl.setSessionToken(paramToken);
        return;
      }
      throw new IllegalStateException("The session token has already been set.");
    }
    throw new IllegalArgumentException("Session token may not be null.");
  }
  
  public static final class BrowserRoot
  {
    public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
    public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
    public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
    @Deprecated
    public static final String EXTRA_SUGGESTION_KEYWORDS = "android.service.media.extra.SUGGESTION_KEYWORDS";
    private final Bundle mExtras;
    private final String mRootId;
    
    public BrowserRoot(String paramString, Bundle paramBundle)
    {
      if (paramString != null)
      {
        this.mRootId = paramString;
        this.mExtras = paramBundle;
        return;
      }
      throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
    }
    
    public Bundle getExtras()
    {
      return this.mExtras;
    }
    
    public String getRootId()
    {
      return this.mRootId;
    }
  }
  
  private static class ConnectionRecord
  {
    MediaBrowserServiceCompat.ServiceCallbacks callbacks;
    String pkg;
    MediaBrowserServiceCompat.BrowserRoot root;
    Bundle rootHints;
    HashMap<String, List<Pair<IBinder, Bundle>>> subscriptions = new HashMap();
  }
  
  static abstract interface MediaBrowserServiceImpl
  {
    public abstract Bundle getBrowserRootHints();
    
    public abstract void notifyChildrenChanged(String paramString, Bundle paramBundle);
    
    public abstract IBinder onBind(Intent paramIntent);
    
    public abstract void onCreate();
    
    public abstract void setSessionToken(MediaSessionCompat.Token paramToken);
  }
  
  class MediaBrowserServiceImplApi21
    implements MediaBrowserServiceCompat.MediaBrowserServiceImpl, MediaBrowserServiceCompatApi21.ServiceCompatProxy
  {
    Messenger mMessenger;
    final List<Bundle> mRootExtrasList = new ArrayList();
    Object mServiceObj;
    
    MediaBrowserServiceImplApi21() {}
    
    public Bundle getBrowserRootHints()
    {
      Messenger localMessenger = this.mMessenger;
      Bundle localBundle = null;
      if (localMessenger == null) {
        return null;
      }
      if (MediaBrowserServiceCompat.this.mCurConnection != null)
      {
        if (MediaBrowserServiceCompat.this.mCurConnection.rootHints != null) {
          localBundle = new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
        }
        return localBundle;
      }
      throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem or onSearch methods");
    }
    
    public void notifyChildrenChanged(final String paramString, final Bundle paramBundle)
    {
      if (this.mMessenger == null) {
        MediaBrowserServiceCompatApi21.notifyChildrenChanged(this.mServiceObj, paramString);
      } else {
        MediaBrowserServiceCompat.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            Iterator localIterator = MediaBrowserServiceCompat.this.mConnections.keySet().iterator();
            while (localIterator.hasNext())
            {
              Object localObject1 = (IBinder)localIterator.next();
              localObject1 = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(localObject1);
              Object localObject2 = (List)((MediaBrowserServiceCompat.ConnectionRecord)localObject1).subscriptions.get(paramString);
              if (localObject2 != null)
              {
                localObject2 = ((List)localObject2).iterator();
                while (((Iterator)localObject2).hasNext())
                {
                  Pair localPair = (Pair)((Iterator)localObject2).next();
                  if (MediaBrowserCompatUtils.hasDuplicatedItems(paramBundle, (Bundle)localPair.second)) {
                    MediaBrowserServiceCompat.this.performLoadChildren(paramString, (MediaBrowserServiceCompat.ConnectionRecord)localObject1, (Bundle)localPair.second);
                  }
                }
              }
            }
          }
        });
      }
    }
    
    public IBinder onBind(Intent paramIntent)
    {
      return MediaBrowserServiceCompatApi21.onBind(this.mServiceObj, paramIntent);
    }
    
    public void onCreate()
    {
      this.mServiceObj = MediaBrowserServiceCompatApi21.createService(MediaBrowserServiceCompat.this, this);
      MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
    }
    
    public MediaBrowserServiceCompatApi21.BrowserRoot onGetRoot(String paramString, int paramInt, Bundle paramBundle)
    {
      Object localObject;
      if ((paramBundle != null) && (paramBundle.getInt("extra_client_version", 0) != 0))
      {
        paramBundle.remove("extra_client_version");
        this.mMessenger = new Messenger(MediaBrowserServiceCompat.this.mHandler);
        Bundle localBundle = new Bundle();
        localBundle.putInt("extra_service_version", 1);
        BundleCompat.putBinder(localBundle, "extra_messenger", this.mMessenger.getBinder());
        if (MediaBrowserServiceCompat.this.mSession != null)
        {
          localObject = MediaBrowserServiceCompat.this.mSession.getExtraBinder();
          if (localObject == null) {
            localObject = null;
          } else {
            localObject = ((IMediaSession)localObject).asBinder();
          }
          BundleCompat.putBinder(localBundle, "extra_session_binder", (IBinder)localObject);
          localObject = localBundle;
        }
        else
        {
          this.mRootExtrasList.add(localBundle);
          localObject = localBundle;
        }
      }
      else
      {
        localObject = null;
      }
      paramBundle = MediaBrowserServiceCompat.this.onGetRoot(paramString, paramInt, paramBundle);
      if (paramBundle == null) {
        return null;
      }
      if (localObject == null)
      {
        paramString = paramBundle.getExtras();
      }
      else
      {
        paramString = (String)localObject;
        if (paramBundle.getExtras() != null)
        {
          ((Bundle)localObject).putAll(paramBundle.getExtras());
          paramString = (String)localObject;
        }
      }
      return new MediaBrowserServiceCompatApi21.BrowserRoot(paramBundle.getRootId(), paramString);
    }
    
    public void onLoadChildren(String paramString, final MediaBrowserServiceCompatApi21.ResultWrapper<List<Parcel>> paramResultWrapper)
    {
      paramResultWrapper = new MediaBrowserServiceCompat.Result(paramString)
      {
        public void detach()
        {
          paramResultWrapper.detach();
        }
        
        void onResultSent(List<MediaBrowserCompat.MediaItem> paramAnonymousList)
        {
          if (paramAnonymousList != null)
          {
            ArrayList localArrayList = new ArrayList();
            Iterator localIterator = paramAnonymousList.iterator();
            for (;;)
            {
              paramAnonymousList = localArrayList;
              if (!localIterator.hasNext()) {
                break;
              }
              MediaBrowserCompat.MediaItem localMediaItem = (MediaBrowserCompat.MediaItem)localIterator.next();
              paramAnonymousList = Parcel.obtain();
              localMediaItem.writeToParcel(paramAnonymousList, 0);
              localArrayList.add(paramAnonymousList);
            }
          }
          paramAnonymousList = null;
          paramResultWrapper.sendResult(paramAnonymousList);
        }
      };
      MediaBrowserServiceCompat.this.onLoadChildren(paramString, paramResultWrapper);
    }
    
    public void setSessionToken(final MediaSessionCompat.Token paramToken)
    {
      MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable()
      {
        public void run()
        {
          if (!MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.this.mRootExtrasList.isEmpty())
          {
            IMediaSession localIMediaSession = paramToken.getExtraBinder();
            if (localIMediaSession != null)
            {
              Iterator localIterator = MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.this.mRootExtrasList.iterator();
              while (localIterator.hasNext()) {
                BundleCompat.putBinder((Bundle)localIterator.next(), "extra_session_binder", localIMediaSession.asBinder());
              }
            }
            MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.this.mRootExtrasList.clear();
          }
          MediaBrowserServiceCompatApi21.setSessionToken(MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.this.mServiceObj, paramToken.getToken());
        }
      });
    }
  }
  
  class MediaBrowserServiceImplApi23
    extends MediaBrowserServiceCompat.MediaBrowserServiceImplApi21
    implements MediaBrowserServiceCompatApi23.ServiceCompatProxy
  {
    MediaBrowserServiceImplApi23()
    {
      super();
    }
    
    public void onCreate()
    {
      this.mServiceObj = MediaBrowserServiceCompatApi23.createService(MediaBrowserServiceCompat.this, this);
      MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
    }
    
    public void onLoadItem(String paramString, final MediaBrowserServiceCompatApi21.ResultWrapper<Parcel> paramResultWrapper)
    {
      paramResultWrapper = new MediaBrowserServiceCompat.Result(paramString)
      {
        public void detach()
        {
          paramResultWrapper.detach();
        }
        
        void onResultSent(MediaBrowserCompat.MediaItem paramAnonymousMediaItem)
        {
          if (paramAnonymousMediaItem == null)
          {
            paramResultWrapper.sendResult(null);
          }
          else
          {
            Parcel localParcel = Parcel.obtain();
            paramAnonymousMediaItem.writeToParcel(localParcel, 0);
            paramResultWrapper.sendResult(localParcel);
          }
        }
      };
      MediaBrowserServiceCompat.this.onLoadItem(paramString, paramResultWrapper);
    }
  }
  
  class MediaBrowserServiceImplApi24
    extends MediaBrowserServiceCompat.MediaBrowserServiceImplApi23
    implements MediaBrowserServiceCompatApi24.ServiceCompatProxy
  {
    MediaBrowserServiceImplApi24()
    {
      super();
    }
    
    public Bundle getBrowserRootHints()
    {
      if (MediaBrowserServiceCompat.this.mCurConnection != null)
      {
        Bundle localBundle;
        if (MediaBrowserServiceCompat.this.mCurConnection.rootHints == null) {
          localBundle = null;
        } else {
          localBundle = new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
        }
        return localBundle;
      }
      return MediaBrowserServiceCompatApi24.getBrowserRootHints(this.mServiceObj);
    }
    
    public void notifyChildrenChanged(String paramString, Bundle paramBundle)
    {
      if (paramBundle == null) {
        MediaBrowserServiceCompatApi21.notifyChildrenChanged(this.mServiceObj, paramString);
      } else {
        MediaBrowserServiceCompatApi24.notifyChildrenChanged(this.mServiceObj, paramString, paramBundle);
      }
    }
    
    public void onCreate()
    {
      this.mServiceObj = MediaBrowserServiceCompatApi24.createService(MediaBrowserServiceCompat.this, this);
      MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
    }
    
    public void onLoadChildren(String paramString, final MediaBrowserServiceCompatApi24.ResultWrapper paramResultWrapper, Bundle paramBundle)
    {
      paramResultWrapper = new MediaBrowserServiceCompat.Result(paramString)
      {
        public void detach()
        {
          paramResultWrapper.detach();
        }
        
        void onResultSent(List<MediaBrowserCompat.MediaItem> paramAnonymousList)
        {
          if (paramAnonymousList != null)
          {
            ArrayList localArrayList = new ArrayList();
            Iterator localIterator = paramAnonymousList.iterator();
            for (;;)
            {
              paramAnonymousList = localArrayList;
              if (!localIterator.hasNext()) {
                break;
              }
              MediaBrowserCompat.MediaItem localMediaItem = (MediaBrowserCompat.MediaItem)localIterator.next();
              paramAnonymousList = Parcel.obtain();
              localMediaItem.writeToParcel(paramAnonymousList, 0);
              localArrayList.add(paramAnonymousList);
            }
          }
          paramAnonymousList = null;
          paramResultWrapper.sendResult(paramAnonymousList, getFlags());
        }
      };
      MediaBrowserServiceCompat.this.onLoadChildren(paramString, paramResultWrapper, paramBundle);
    }
  }
  
  class MediaBrowserServiceImplBase
    implements MediaBrowserServiceCompat.MediaBrowserServiceImpl
  {
    private Messenger mMessenger;
    
    MediaBrowserServiceImplBase() {}
    
    public Bundle getBrowserRootHints()
    {
      if (MediaBrowserServiceCompat.this.mCurConnection != null)
      {
        Bundle localBundle;
        if (MediaBrowserServiceCompat.this.mCurConnection.rootHints == null) {
          localBundle = null;
        } else {
          localBundle = new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
        }
        return localBundle;
      }
      throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem or onSearch methods");
    }
    
    public void notifyChildrenChanged(final String paramString, final Bundle paramBundle)
    {
      MediaBrowserServiceCompat.this.mHandler.post(new Runnable()
      {
        public void run()
        {
          Iterator localIterator1 = MediaBrowserServiceCompat.this.mConnections.keySet().iterator();
          while (localIterator1.hasNext())
          {
            Object localObject1 = (IBinder)localIterator1.next();
            localObject1 = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(localObject1);
            Object localObject2 = (List)((MediaBrowserServiceCompat.ConnectionRecord)localObject1).subscriptions.get(paramString);
            if (localObject2 != null)
            {
              Iterator localIterator2 = ((List)localObject2).iterator();
              while (localIterator2.hasNext())
              {
                localObject2 = (Pair)localIterator2.next();
                if (MediaBrowserCompatUtils.hasDuplicatedItems(paramBundle, (Bundle)((Pair)localObject2).second)) {
                  MediaBrowserServiceCompat.this.performLoadChildren(paramString, (MediaBrowserServiceCompat.ConnectionRecord)localObject1, (Bundle)((Pair)localObject2).second);
                }
              }
            }
          }
        }
      });
    }
    
    public IBinder onBind(Intent paramIntent)
    {
      if ("android.media.browse.MediaBrowserService".equals(paramIntent.getAction())) {
        return this.mMessenger.getBinder();
      }
      return null;
    }
    
    public void onCreate()
    {
      this.mMessenger = new Messenger(MediaBrowserServiceCompat.this.mHandler);
    }
    
    public void setSessionToken(final MediaSessionCompat.Token paramToken)
    {
      MediaBrowserServiceCompat.this.mHandler.post(new Runnable()
      {
        public void run()
        {
          Iterator localIterator = MediaBrowserServiceCompat.this.mConnections.values().iterator();
          while (localIterator.hasNext())
          {
            MediaBrowserServiceCompat.ConnectionRecord localConnectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)localIterator.next();
            try
            {
              localConnectionRecord.callbacks.onConnect(localConnectionRecord.root.getRootId(), paramToken, localConnectionRecord.root.getExtras());
            }
            catch (RemoteException localRemoteException)
            {
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("Connection for ");
              localStringBuilder.append(localConnectionRecord.pkg);
              localStringBuilder.append(" is no longer valid.");
              Log.w("MBServiceCompat", localStringBuilder.toString());
              localIterator.remove();
            }
          }
        }
      });
    }
  }
  
  public static class Result<T>
  {
    private final Object mDebug;
    private boolean mDetachCalled;
    private int mFlags;
    private boolean mSendErrorCalled;
    private boolean mSendProgressUpdateCalled;
    private boolean mSendResultCalled;
    
    Result(Object paramObject)
    {
      this.mDebug = paramObject;
    }
    
    private void checkExtraFields(Bundle paramBundle)
    {
      if (paramBundle == null) {
        return;
      }
      if (paramBundle.containsKey("android.media.browse.extra.DOWNLOAD_PROGRESS"))
      {
        float f = paramBundle.getFloat("android.media.browse.extra.DOWNLOAD_PROGRESS");
        if ((f < -1.0E-5F) || (f > 1.00001F)) {
          throw new IllegalArgumentException("The value of the EXTRA_DOWNLOAD_PROGRESS field must be a float number within [0.0, 1.0].");
        }
      }
    }
    
    public void detach()
    {
      if (!this.mDetachCalled)
      {
        if (!this.mSendResultCalled)
        {
          if (!this.mSendErrorCalled)
          {
            this.mDetachCalled = true;
            return;
          }
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("detach() called when sendError() had already been called for: ");
          localStringBuilder.append(this.mDebug);
          throw new IllegalStateException(localStringBuilder.toString());
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("detach() called when sendResult() had already been called for: ");
        localStringBuilder.append(this.mDebug);
        throw new IllegalStateException(localStringBuilder.toString());
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("detach() called when detach() had already been called for: ");
      localStringBuilder.append(this.mDebug);
      throw new IllegalStateException(localStringBuilder.toString());
    }
    
    int getFlags()
    {
      return this.mFlags;
    }
    
    boolean isDone()
    {
      boolean bool;
      if ((!this.mDetachCalled) && (!this.mSendResultCalled) && (!this.mSendErrorCalled)) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    void onErrorSent(Bundle paramBundle)
    {
      paramBundle = new StringBuilder();
      paramBundle.append("It is not supported to send an error for ");
      paramBundle.append(this.mDebug);
      throw new UnsupportedOperationException(paramBundle.toString());
    }
    
    void onProgressUpdateSent(Bundle paramBundle)
    {
      paramBundle = new StringBuilder();
      paramBundle.append("It is not supported to send an interim update for ");
      paramBundle.append(this.mDebug);
      throw new UnsupportedOperationException(paramBundle.toString());
    }
    
    void onResultSent(T paramT) {}
    
    public void sendError(Bundle paramBundle)
    {
      if ((!this.mSendResultCalled) && (!this.mSendErrorCalled))
      {
        this.mSendErrorCalled = true;
        onErrorSent(paramBundle);
        return;
      }
      paramBundle = new StringBuilder();
      paramBundle.append("sendError() called when either sendResult() or sendError() had already been called for: ");
      paramBundle.append(this.mDebug);
      throw new IllegalStateException(paramBundle.toString());
    }
    
    public void sendProgressUpdate(Bundle paramBundle)
    {
      if ((!this.mSendResultCalled) && (!this.mSendErrorCalled))
      {
        checkExtraFields(paramBundle);
        this.mSendProgressUpdateCalled = true;
        onProgressUpdateSent(paramBundle);
        return;
      }
      paramBundle = new StringBuilder();
      paramBundle.append("sendProgressUpdate() called when either sendResult() or sendError() had already been called for: ");
      paramBundle.append(this.mDebug);
      throw new IllegalStateException(paramBundle.toString());
    }
    
    public void sendResult(T paramT)
    {
      if ((!this.mSendResultCalled) && (!this.mSendErrorCalled))
      {
        this.mSendResultCalled = true;
        onResultSent(paramT);
        return;
      }
      paramT = new StringBuilder();
      paramT.append("sendResult() called when either sendResult() or sendError() had already been called for: ");
      paramT.append(this.mDebug);
      throw new IllegalStateException(paramT.toString());
    }
    
    void setFlags(int paramInt)
    {
      this.mFlags = paramInt;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  private static @interface ResultFlags {}
  
  private class ServiceBinderImpl
  {
    ServiceBinderImpl() {}
    
    public void addSubscription(final String paramString, final IBinder paramIBinder, final Bundle paramBundle, final MediaBrowserServiceCompat.ServiceCallbacks paramServiceCallbacks)
    {
      MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable()
      {
        public void run()
        {
          Object localObject = paramServiceCallbacks.asBinder();
          localObject = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(localObject);
          if (localObject == null)
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("addSubscription for callback that isn't registered id=");
            ((StringBuilder)localObject).append(paramString);
            Log.w("MBServiceCompat", ((StringBuilder)localObject).toString());
            return;
          }
          MediaBrowserServiceCompat.this.addSubscription(paramString, (MediaBrowserServiceCompat.ConnectionRecord)localObject, paramIBinder, paramBundle);
        }
      });
    }
    
    public void connect(final String paramString, final int paramInt, final Bundle paramBundle, final MediaBrowserServiceCompat.ServiceCallbacks paramServiceCallbacks)
    {
      if (MediaBrowserServiceCompat.this.isValidPackage(paramString, paramInt))
      {
        MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable()
        {
          public void run()
          {
            Object localObject = paramServiceCallbacks.asBinder();
            MediaBrowserServiceCompat.this.mConnections.remove(localObject);
            MediaBrowserServiceCompat.ConnectionRecord localConnectionRecord = new MediaBrowserServiceCompat.ConnectionRecord();
            localConnectionRecord.pkg = paramString;
            localConnectionRecord.rootHints = paramBundle;
            localConnectionRecord.callbacks = paramServiceCallbacks;
            localConnectionRecord.root = MediaBrowserServiceCompat.this.onGetRoot(paramString, paramInt, paramBundle);
            StringBuilder localStringBuilder1;
            if (localConnectionRecord.root == null)
            {
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append("No root for client ");
              ((StringBuilder)localObject).append(paramString);
              ((StringBuilder)localObject).append(" from service ");
              ((StringBuilder)localObject).append(getClass().getName());
              Log.i("MBServiceCompat", ((StringBuilder)localObject).toString());
              try
              {
                paramServiceCallbacks.onConnectFailed();
              }
              catch (RemoteException localRemoteException1)
              {
                localStringBuilder1 = new StringBuilder();
                localStringBuilder1.append("Calling onConnectFailed() failed. Ignoring. pkg=");
                localStringBuilder1.append(paramString);
                Log.w("MBServiceCompat", localStringBuilder1.toString());
              }
            }
            else
            {
              try
              {
                MediaBrowserServiceCompat.this.mConnections.put(localStringBuilder1, localConnectionRecord);
                if (MediaBrowserServiceCompat.this.mSession != null) {
                  paramServiceCallbacks.onConnect(localConnectionRecord.root.getRootId(), MediaBrowserServiceCompat.this.mSession, localConnectionRecord.root.getExtras());
                }
              }
              catch (RemoteException localRemoteException2)
              {
                StringBuilder localStringBuilder2 = new StringBuilder();
                localStringBuilder2.append("Calling onConnect() failed. Dropping client. pkg=");
                localStringBuilder2.append(paramString);
                Log.w("MBServiceCompat", localStringBuilder2.toString());
                MediaBrowserServiceCompat.this.mConnections.remove(localStringBuilder1);
              }
            }
          }
        });
        return;
      }
      paramBundle = new StringBuilder();
      paramBundle.append("Package/uid mismatch: uid=");
      paramBundle.append(paramInt);
      paramBundle.append(" package=");
      paramBundle.append(paramString);
      throw new IllegalArgumentException(paramBundle.toString());
    }
    
    public void disconnect(final MediaBrowserServiceCompat.ServiceCallbacks paramServiceCallbacks)
    {
      MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable()
      {
        public void run()
        {
          Object localObject = paramServiceCallbacks.asBinder();
          localObject = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.remove(localObject);
        }
      });
    }
    
    public void getMediaItem(final String paramString, final ResultReceiver paramResultReceiver, final MediaBrowserServiceCompat.ServiceCallbacks paramServiceCallbacks)
    {
      if ((!TextUtils.isEmpty(paramString)) && (paramResultReceiver != null))
      {
        MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable()
        {
          public void run()
          {
            Object localObject = paramServiceCallbacks.asBinder();
            localObject = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(localObject);
            if (localObject == null)
            {
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append("getMediaItem for callback that isn't registered id=");
              ((StringBuilder)localObject).append(paramString);
              Log.w("MBServiceCompat", ((StringBuilder)localObject).toString());
              return;
            }
            MediaBrowserServiceCompat.this.performLoadItem(paramString, (MediaBrowserServiceCompat.ConnectionRecord)localObject, paramResultReceiver);
          }
        });
        return;
      }
    }
    
    public void registerCallbacks(final MediaBrowserServiceCompat.ServiceCallbacks paramServiceCallbacks, final Bundle paramBundle)
    {
      MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable()
      {
        public void run()
        {
          IBinder localIBinder = paramServiceCallbacks.asBinder();
          MediaBrowserServiceCompat.this.mConnections.remove(localIBinder);
          MediaBrowserServiceCompat.ConnectionRecord localConnectionRecord = new MediaBrowserServiceCompat.ConnectionRecord();
          localConnectionRecord.callbacks = paramServiceCallbacks;
          localConnectionRecord.rootHints = paramBundle;
          MediaBrowserServiceCompat.this.mConnections.put(localIBinder, localConnectionRecord);
        }
      });
    }
    
    public void removeSubscription(final String paramString, final IBinder paramIBinder, final MediaBrowserServiceCompat.ServiceCallbacks paramServiceCallbacks)
    {
      MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable()
      {
        public void run()
        {
          Object localObject = paramServiceCallbacks.asBinder();
          localObject = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(localObject);
          if (localObject == null)
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("removeSubscription for callback that isn't registered id=");
            ((StringBuilder)localObject).append(paramString);
            Log.w("MBServiceCompat", ((StringBuilder)localObject).toString());
            return;
          }
          if (!MediaBrowserServiceCompat.this.removeSubscription(paramString, (MediaBrowserServiceCompat.ConnectionRecord)localObject, paramIBinder))
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("removeSubscription called for ");
            ((StringBuilder)localObject).append(paramString);
            ((StringBuilder)localObject).append(" which is not subscribed");
            Log.w("MBServiceCompat", ((StringBuilder)localObject).toString());
          }
        }
      });
    }
    
    public void search(final String paramString, final Bundle paramBundle, final ResultReceiver paramResultReceiver, final MediaBrowserServiceCompat.ServiceCallbacks paramServiceCallbacks)
    {
      if ((!TextUtils.isEmpty(paramString)) && (paramResultReceiver != null))
      {
        MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable()
        {
          public void run()
          {
            Object localObject = paramServiceCallbacks.asBinder();
            localObject = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(localObject);
            if (localObject == null)
            {
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append("search for callback that isn't registered query=");
              ((StringBuilder)localObject).append(paramString);
              Log.w("MBServiceCompat", ((StringBuilder)localObject).toString());
              return;
            }
            MediaBrowserServiceCompat.this.performSearch(paramString, paramBundle, (MediaBrowserServiceCompat.ConnectionRecord)localObject, paramResultReceiver);
          }
        });
        return;
      }
    }
    
    public void sendCustomAction(final String paramString, final Bundle paramBundle, final ResultReceiver paramResultReceiver, final MediaBrowserServiceCompat.ServiceCallbacks paramServiceCallbacks)
    {
      if ((!TextUtils.isEmpty(paramString)) && (paramResultReceiver != null))
      {
        MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable()
        {
          public void run()
          {
            Object localObject = paramServiceCallbacks.asBinder();
            localObject = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(localObject);
            if (localObject == null)
            {
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append("sendCustomAction for callback that isn't registered action=");
              ((StringBuilder)localObject).append(paramString);
              ((StringBuilder)localObject).append(", extras=");
              ((StringBuilder)localObject).append(paramBundle);
              Log.w("MBServiceCompat", ((StringBuilder)localObject).toString());
              return;
            }
            MediaBrowserServiceCompat.this.performCustomAction(paramString, paramBundle, (MediaBrowserServiceCompat.ConnectionRecord)localObject, paramResultReceiver);
          }
        });
        return;
      }
    }
    
    public void unregisterCallbacks(final MediaBrowserServiceCompat.ServiceCallbacks paramServiceCallbacks)
    {
      MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable()
      {
        public void run()
        {
          IBinder localIBinder = paramServiceCallbacks.asBinder();
          MediaBrowserServiceCompat.this.mConnections.remove(localIBinder);
        }
      });
    }
  }
  
  private static abstract interface ServiceCallbacks
  {
    public abstract IBinder asBinder();
    
    public abstract void onConnect(String paramString, MediaSessionCompat.Token paramToken, Bundle paramBundle);
    
    public abstract void onConnectFailed();
    
    public abstract void onLoadChildren(String paramString, List<MediaBrowserCompat.MediaItem> paramList, Bundle paramBundle);
  }
  
  private static class ServiceCallbacksCompat
    implements MediaBrowserServiceCompat.ServiceCallbacks
  {
    final Messenger mCallbacks;
    
    ServiceCallbacksCompat(Messenger paramMessenger)
    {
      this.mCallbacks = paramMessenger;
    }
    
    private void sendRequest(int paramInt, Bundle paramBundle)
    {
      Message localMessage = Message.obtain();
      localMessage.what = paramInt;
      localMessage.arg1 = 1;
      localMessage.setData(paramBundle);
      this.mCallbacks.send(localMessage);
    }
    
    public IBinder asBinder()
    {
      return this.mCallbacks.getBinder();
    }
    
    public void onConnect(String paramString, MediaSessionCompat.Token paramToken, Bundle paramBundle)
    {
      Bundle localBundle = paramBundle;
      if (paramBundle == null) {
        localBundle = new Bundle();
      }
      localBundle.putInt("extra_service_version", 1);
      paramBundle = new Bundle();
      paramBundle.putString("data_media_item_id", paramString);
      paramBundle.putParcelable("data_media_session_token", paramToken);
      paramBundle.putBundle("data_root_hints", localBundle);
      sendRequest(1, paramBundle);
    }
    
    public void onConnectFailed()
    {
      sendRequest(2, null);
    }
    
    public void onLoadChildren(String paramString, List<MediaBrowserCompat.MediaItem> paramList, Bundle paramBundle)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("data_media_item_id", paramString);
      localBundle.putBundle("data_options", paramBundle);
      if (paramList != null)
      {
        if ((paramList instanceof ArrayList)) {
          paramString = (ArrayList)paramList;
        } else {
          paramString = new ArrayList(paramList);
        }
        localBundle.putParcelableArrayList("data_media_item_list", paramString);
      }
      sendRequest(3, localBundle);
    }
  }
  
  private final class ServiceHandler
    extends Handler
  {
    private final MediaBrowserServiceCompat.ServiceBinderImpl mServiceBinderImpl = new MediaBrowserServiceCompat.ServiceBinderImpl(MediaBrowserServiceCompat.this);
    
    ServiceHandler() {}
    
    public void handleMessage(Message paramMessage)
    {
      Object localObject = paramMessage.getData();
      switch (paramMessage.what)
      {
      default: 
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Unhandled message: ");
        ((StringBuilder)localObject).append(paramMessage);
        ((StringBuilder)localObject).append("\n  Service version: ");
        ((StringBuilder)localObject).append(1);
        ((StringBuilder)localObject).append("\n  Client version: ");
        ((StringBuilder)localObject).append(paramMessage.arg1);
        Log.w("MBServiceCompat", ((StringBuilder)localObject).toString());
        break;
      case 9: 
        this.mServiceBinderImpl.sendCustomAction(((Bundle)localObject).getString("data_custom_action"), ((Bundle)localObject).getBundle("data_custom_action_extras"), (ResultReceiver)((Bundle)localObject).getParcelable("data_result_receiver"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(paramMessage.replyTo));
        break;
      case 8: 
        this.mServiceBinderImpl.search(((Bundle)localObject).getString("data_search_query"), ((Bundle)localObject).getBundle("data_search_extras"), (ResultReceiver)((Bundle)localObject).getParcelable("data_result_receiver"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(paramMessage.replyTo));
        break;
      case 7: 
        this.mServiceBinderImpl.unregisterCallbacks(new MediaBrowserServiceCompat.ServiceCallbacksCompat(paramMessage.replyTo));
        break;
      case 6: 
        this.mServiceBinderImpl.registerCallbacks(new MediaBrowserServiceCompat.ServiceCallbacksCompat(paramMessage.replyTo), ((Bundle)localObject).getBundle("data_root_hints"));
        break;
      case 5: 
        this.mServiceBinderImpl.getMediaItem(((Bundle)localObject).getString("data_media_item_id"), (ResultReceiver)((Bundle)localObject).getParcelable("data_result_receiver"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(paramMessage.replyTo));
        break;
      case 4: 
        this.mServiceBinderImpl.removeSubscription(((Bundle)localObject).getString("data_media_item_id"), BundleCompat.getBinder((Bundle)localObject, "data_callback_token"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(paramMessage.replyTo));
        break;
      case 3: 
        this.mServiceBinderImpl.addSubscription(((Bundle)localObject).getString("data_media_item_id"), BundleCompat.getBinder((Bundle)localObject, "data_callback_token"), ((Bundle)localObject).getBundle("data_options"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(paramMessage.replyTo));
        break;
      case 2: 
        this.mServiceBinderImpl.disconnect(new MediaBrowserServiceCompat.ServiceCallbacksCompat(paramMessage.replyTo));
        break;
      case 1: 
        this.mServiceBinderImpl.connect(((Bundle)localObject).getString("data_package_name"), ((Bundle)localObject).getInt("data_calling_uid"), ((Bundle)localObject).getBundle("data_root_hints"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(paramMessage.replyTo));
      }
    }
    
    public void postOrRun(Runnable paramRunnable)
    {
      if (Thread.currentThread() == getLooper().getThread()) {
        paramRunnable.run();
      } else {
        post(paramRunnable);
      }
    }
    
    public boolean sendMessageAtTime(Message paramMessage, long paramLong)
    {
      Bundle localBundle = paramMessage.getData();
      localBundle.setClassLoader(MediaBrowserCompat.class.getClassLoader());
      localBundle.putInt("data_calling_uid", Binder.getCallingUid());
      return super.sendMessageAtTime(paramMessage, paramLong);
    }
  }
}


/* Location:              ~/android/support/v4/media/MediaBrowserServiceCompat.class
 *
 * Reversed by:           J
 */