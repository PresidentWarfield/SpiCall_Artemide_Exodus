package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.BadParcelableException;
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
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.IMediaSession.Stub;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public final class MediaBrowserCompat
{
  public static final String CUSTOM_ACTION_DOWNLOAD = "android.support.v4.media.action.DOWNLOAD";
  public static final String CUSTOM_ACTION_REMOVE_DOWNLOADED_FILE = "android.support.v4.media.action.REMOVE_DOWNLOADED_FILE";
  static final boolean DEBUG = Log.isLoggable("MediaBrowserCompat", 3);
  public static final String EXTRA_DOWNLOAD_PROGRESS = "android.media.browse.extra.DOWNLOAD_PROGRESS";
  public static final String EXTRA_MEDIA_ID = "android.media.browse.extra.MEDIA_ID";
  public static final String EXTRA_PAGE = "android.media.browse.extra.PAGE";
  public static final String EXTRA_PAGE_SIZE = "android.media.browse.extra.PAGE_SIZE";
  static final String TAG = "MediaBrowserCompat";
  private final MediaBrowserImpl mImpl;
  
  public MediaBrowserCompat(Context paramContext, ComponentName paramComponentName, ConnectionCallback paramConnectionCallback, Bundle paramBundle)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      this.mImpl = new MediaBrowserImplApi24(paramContext, paramComponentName, paramConnectionCallback, paramBundle);
    } else if (Build.VERSION.SDK_INT >= 23) {
      this.mImpl = new MediaBrowserImplApi23(paramContext, paramComponentName, paramConnectionCallback, paramBundle);
    } else if (Build.VERSION.SDK_INT >= 21) {
      this.mImpl = new MediaBrowserImplApi21(paramContext, paramComponentName, paramConnectionCallback, paramBundle);
    } else {
      this.mImpl = new MediaBrowserImplBase(paramContext, paramComponentName, paramConnectionCallback, paramBundle);
    }
  }
  
  public void connect()
  {
    this.mImpl.connect();
  }
  
  public void disconnect()
  {
    this.mImpl.disconnect();
  }
  
  public Bundle getExtras()
  {
    return this.mImpl.getExtras();
  }
  
  public void getItem(String paramString, ItemCallback paramItemCallback)
  {
    this.mImpl.getItem(paramString, paramItemCallback);
  }
  
  public String getRoot()
  {
    return this.mImpl.getRoot();
  }
  
  public ComponentName getServiceComponent()
  {
    return this.mImpl.getServiceComponent();
  }
  
  public MediaSessionCompat.Token getSessionToken()
  {
    return this.mImpl.getSessionToken();
  }
  
  public boolean isConnected()
  {
    return this.mImpl.isConnected();
  }
  
  public void search(String paramString, Bundle paramBundle, SearchCallback paramSearchCallback)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (paramSearchCallback != null)
      {
        this.mImpl.search(paramString, paramBundle, paramSearchCallback);
        return;
      }
      throw new IllegalArgumentException("callback cannot be null");
    }
    throw new IllegalArgumentException("query cannot be empty");
  }
  
  public void sendCustomAction(String paramString, Bundle paramBundle, CustomActionCallback paramCustomActionCallback)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.mImpl.sendCustomAction(paramString, paramBundle, paramCustomActionCallback);
      return;
    }
    throw new IllegalArgumentException("action cannot be empty");
  }
  
  public void subscribe(String paramString, Bundle paramBundle, SubscriptionCallback paramSubscriptionCallback)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (paramSubscriptionCallback != null)
      {
        if (paramBundle != null)
        {
          this.mImpl.subscribe(paramString, paramBundle, paramSubscriptionCallback);
          return;
        }
        throw new IllegalArgumentException("options are null");
      }
      throw new IllegalArgumentException("callback is null");
    }
    throw new IllegalArgumentException("parentId is empty");
  }
  
  public void subscribe(String paramString, SubscriptionCallback paramSubscriptionCallback)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (paramSubscriptionCallback != null)
      {
        this.mImpl.subscribe(paramString, null, paramSubscriptionCallback);
        return;
      }
      throw new IllegalArgumentException("callback is null");
    }
    throw new IllegalArgumentException("parentId is empty");
  }
  
  public void unsubscribe(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.mImpl.unsubscribe(paramString, null);
      return;
    }
    throw new IllegalArgumentException("parentId is empty");
  }
  
  public void unsubscribe(String paramString, SubscriptionCallback paramSubscriptionCallback)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (paramSubscriptionCallback != null)
      {
        this.mImpl.unsubscribe(paramString, paramSubscriptionCallback);
        return;
      }
      throw new IllegalArgumentException("callback is null");
    }
    throw new IllegalArgumentException("parentId is empty");
  }
  
  private static class CallbackHandler
    extends Handler
  {
    private final WeakReference<MediaBrowserCompat.MediaBrowserServiceCallbackImpl> mCallbackImplRef;
    private WeakReference<Messenger> mCallbacksMessengerRef;
    
    CallbackHandler(MediaBrowserCompat.MediaBrowserServiceCallbackImpl paramMediaBrowserServiceCallbackImpl)
    {
      this.mCallbackImplRef = new WeakReference(paramMediaBrowserServiceCallbackImpl);
    }
    
    public void handleMessage(Message paramMessage)
    {
      Object localObject1 = this.mCallbacksMessengerRef;
      if ((localObject1 != null) && (((WeakReference)localObject1).get() != null) && (this.mCallbackImplRef.get() != null))
      {
        Object localObject2 = paramMessage.getData();
        ((Bundle)localObject2).setClassLoader(MediaSessionCompat.class.getClassLoader());
        localObject1 = (MediaBrowserCompat.MediaBrowserServiceCallbackImpl)this.mCallbackImplRef.get();
        Messenger localMessenger = (Messenger)this.mCallbacksMessengerRef.get();
        try
        {
          switch (paramMessage.what)
          {
          default: 
            break;
          case 3: 
            ((MediaBrowserCompat.MediaBrowserServiceCallbackImpl)localObject1).onLoadChildren(localMessenger, ((Bundle)localObject2).getString("data_media_item_id"), ((Bundle)localObject2).getParcelableArrayList("data_media_item_list"), ((Bundle)localObject2).getBundle("data_options"));
            break;
          case 2: 
            ((MediaBrowserCompat.MediaBrowserServiceCallbackImpl)localObject1).onConnectionFailed(localMessenger);
            break;
          case 1: 
            ((MediaBrowserCompat.MediaBrowserServiceCallbackImpl)localObject1).onServiceConnected(localMessenger, ((Bundle)localObject2).getString("data_media_item_id"), (MediaSessionCompat.Token)((Bundle)localObject2).getParcelable("data_media_session_token"), ((Bundle)localObject2).getBundle("data_root_hints"));
            break;
          }
          localObject2 = new java/lang/StringBuilder;
          ((StringBuilder)localObject2).<init>();
          ((StringBuilder)localObject2).append("Unhandled message: ");
          ((StringBuilder)localObject2).append(paramMessage);
          ((StringBuilder)localObject2).append("\n  Client version: ");
          ((StringBuilder)localObject2).append(1);
          ((StringBuilder)localObject2).append("\n  Service version: ");
          ((StringBuilder)localObject2).append(paramMessage.arg1);
          Log.w("MediaBrowserCompat", ((StringBuilder)localObject2).toString());
        }
        catch (BadParcelableException localBadParcelableException)
        {
          Log.e("MediaBrowserCompat", "Could not unparcel the data.");
          if (paramMessage.what == 1) {
            ((MediaBrowserCompat.MediaBrowserServiceCallbackImpl)localObject1).onConnectionFailed(localMessenger);
          }
        }
        return;
      }
    }
    
    void setCallbacksMessenger(Messenger paramMessenger)
    {
      this.mCallbacksMessengerRef = new WeakReference(paramMessenger);
    }
  }
  
  public static class ConnectionCallback
  {
    ConnectionCallbackInternal mConnectionCallbackInternal;
    final Object mConnectionCallbackObj;
    
    public ConnectionCallback()
    {
      if (Build.VERSION.SDK_INT >= 21) {
        this.mConnectionCallbackObj = MediaBrowserCompatApi21.createConnectionCallback(new StubApi21());
      } else {
        this.mConnectionCallbackObj = null;
      }
    }
    
    public void onConnected() {}
    
    public void onConnectionFailed() {}
    
    public void onConnectionSuspended() {}
    
    void setInternalConnectionCallback(ConnectionCallbackInternal paramConnectionCallbackInternal)
    {
      this.mConnectionCallbackInternal = paramConnectionCallbackInternal;
    }
    
    static abstract interface ConnectionCallbackInternal
    {
      public abstract void onConnected();
      
      public abstract void onConnectionFailed();
      
      public abstract void onConnectionSuspended();
    }
    
    private class StubApi21
      implements MediaBrowserCompatApi21.ConnectionCallback
    {
      StubApi21() {}
      
      public void onConnected()
      {
        if (MediaBrowserCompat.ConnectionCallback.this.mConnectionCallbackInternal != null) {
          MediaBrowserCompat.ConnectionCallback.this.mConnectionCallbackInternal.onConnected();
        }
        MediaBrowserCompat.ConnectionCallback.this.onConnected();
      }
      
      public void onConnectionFailed()
      {
        if (MediaBrowserCompat.ConnectionCallback.this.mConnectionCallbackInternal != null) {
          MediaBrowserCompat.ConnectionCallback.this.mConnectionCallbackInternal.onConnectionFailed();
        }
        MediaBrowserCompat.ConnectionCallback.this.onConnectionFailed();
      }
      
      public void onConnectionSuspended()
      {
        if (MediaBrowserCompat.ConnectionCallback.this.mConnectionCallbackInternal != null) {
          MediaBrowserCompat.ConnectionCallback.this.mConnectionCallbackInternal.onConnectionSuspended();
        }
        MediaBrowserCompat.ConnectionCallback.this.onConnectionSuspended();
      }
    }
  }
  
  public static abstract class CustomActionCallback
  {
    public void onError(String paramString, Bundle paramBundle1, Bundle paramBundle2) {}
    
    public void onProgressUpdate(String paramString, Bundle paramBundle1, Bundle paramBundle2) {}
    
    public void onResult(String paramString, Bundle paramBundle1, Bundle paramBundle2) {}
  }
  
  private static class CustomActionResultReceiver
    extends ResultReceiver
  {
    private final String mAction;
    private final MediaBrowserCompat.CustomActionCallback mCallback;
    private final Bundle mExtras;
    
    CustomActionResultReceiver(String paramString, Bundle paramBundle, MediaBrowserCompat.CustomActionCallback paramCustomActionCallback, Handler paramHandler)
    {
      super();
      this.mAction = paramString;
      this.mExtras = paramBundle;
      this.mCallback = paramCustomActionCallback;
    }
    
    protected void onReceiveResult(int paramInt, Bundle paramBundle)
    {
      Object localObject = this.mCallback;
      if (localObject == null) {
        return;
      }
      switch (paramInt)
      {
      default: 
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Unknown result code: ");
        ((StringBuilder)localObject).append(paramInt);
        ((StringBuilder)localObject).append(" (extras=");
        ((StringBuilder)localObject).append(this.mExtras);
        ((StringBuilder)localObject).append(", resultData=");
        ((StringBuilder)localObject).append(paramBundle);
        ((StringBuilder)localObject).append(")");
        Log.w("MediaBrowserCompat", ((StringBuilder)localObject).toString());
        break;
      case 1: 
        ((MediaBrowserCompat.CustomActionCallback)localObject).onProgressUpdate(this.mAction, this.mExtras, paramBundle);
        break;
      case 0: 
        ((MediaBrowserCompat.CustomActionCallback)localObject).onResult(this.mAction, this.mExtras, paramBundle);
        break;
      case -1: 
        ((MediaBrowserCompat.CustomActionCallback)localObject).onError(this.mAction, this.mExtras, paramBundle);
      }
    }
  }
  
  public static abstract class ItemCallback
  {
    final Object mItemCallbackObj;
    
    public ItemCallback()
    {
      if (Build.VERSION.SDK_INT >= 23) {
        this.mItemCallbackObj = MediaBrowserCompatApi23.createItemCallback(new StubApi23());
      } else {
        this.mItemCallbackObj = null;
      }
    }
    
    public void onError(String paramString) {}
    
    public void onItemLoaded(MediaBrowserCompat.MediaItem paramMediaItem) {}
    
    private class StubApi23
      implements MediaBrowserCompatApi23.ItemCallback
    {
      StubApi23() {}
      
      public void onError(String paramString)
      {
        MediaBrowserCompat.ItemCallback.this.onError(paramString);
      }
      
      public void onItemLoaded(Parcel paramParcel)
      {
        if (paramParcel == null)
        {
          MediaBrowserCompat.ItemCallback.this.onItemLoaded(null);
        }
        else
        {
          paramParcel.setDataPosition(0);
          MediaBrowserCompat.MediaItem localMediaItem = (MediaBrowserCompat.MediaItem)MediaBrowserCompat.MediaItem.CREATOR.createFromParcel(paramParcel);
          paramParcel.recycle();
          MediaBrowserCompat.ItemCallback.this.onItemLoaded(localMediaItem);
        }
      }
    }
  }
  
  private static class ItemReceiver
    extends ResultReceiver
  {
    private final MediaBrowserCompat.ItemCallback mCallback;
    private final String mMediaId;
    
    ItemReceiver(String paramString, MediaBrowserCompat.ItemCallback paramItemCallback, Handler paramHandler)
    {
      super();
      this.mMediaId = paramString;
      this.mCallback = paramItemCallback;
    }
    
    protected void onReceiveResult(int paramInt, Bundle paramBundle)
    {
      if (paramBundle != null) {
        paramBundle.setClassLoader(MediaBrowserCompat.class.getClassLoader());
      }
      if ((paramInt == 0) && (paramBundle != null) && (paramBundle.containsKey("media_item")))
      {
        paramBundle = paramBundle.getParcelable("media_item");
        if ((paramBundle != null) && (!(paramBundle instanceof MediaBrowserCompat.MediaItem))) {
          this.mCallback.onError(this.mMediaId);
        } else {
          this.mCallback.onItemLoaded((MediaBrowserCompat.MediaItem)paramBundle);
        }
        return;
      }
      this.mCallback.onError(this.mMediaId);
    }
  }
  
  static abstract interface MediaBrowserImpl
  {
    public abstract void connect();
    
    public abstract void disconnect();
    
    public abstract Bundle getExtras();
    
    public abstract void getItem(String paramString, MediaBrowserCompat.ItemCallback paramItemCallback);
    
    public abstract String getRoot();
    
    public abstract ComponentName getServiceComponent();
    
    public abstract MediaSessionCompat.Token getSessionToken();
    
    public abstract boolean isConnected();
    
    public abstract void search(String paramString, Bundle paramBundle, MediaBrowserCompat.SearchCallback paramSearchCallback);
    
    public abstract void sendCustomAction(String paramString, Bundle paramBundle, MediaBrowserCompat.CustomActionCallback paramCustomActionCallback);
    
    public abstract void subscribe(String paramString, Bundle paramBundle, MediaBrowserCompat.SubscriptionCallback paramSubscriptionCallback);
    
    public abstract void unsubscribe(String paramString, MediaBrowserCompat.SubscriptionCallback paramSubscriptionCallback);
  }
  
  static class MediaBrowserImplApi21
    implements MediaBrowserCompat.ConnectionCallback.ConnectionCallbackInternal, MediaBrowserCompat.MediaBrowserImpl, MediaBrowserCompat.MediaBrowserServiceCallbackImpl
  {
    protected final Object mBrowserObj;
    protected Messenger mCallbacksMessenger;
    final Context mContext;
    protected final MediaBrowserCompat.CallbackHandler mHandler = new MediaBrowserCompat.CallbackHandler(this);
    private MediaSessionCompat.Token mMediaSessionToken;
    protected final Bundle mRootHints;
    protected MediaBrowserCompat.ServiceBinderWrapper mServiceBinderWrapper;
    private final ArrayMap<String, MediaBrowserCompat.Subscription> mSubscriptions = new ArrayMap();
    
    public MediaBrowserImplApi21(Context paramContext, ComponentName paramComponentName, MediaBrowserCompat.ConnectionCallback paramConnectionCallback, Bundle paramBundle)
    {
      this.mContext = paramContext;
      Bundle localBundle = paramBundle;
      if (paramBundle == null) {
        localBundle = new Bundle();
      }
      localBundle.putInt("extra_client_version", 1);
      this.mRootHints = new Bundle(localBundle);
      paramConnectionCallback.setInternalConnectionCallback(this);
      this.mBrowserObj = MediaBrowserCompatApi21.createBrowser(paramContext, paramComponentName, paramConnectionCallback.mConnectionCallbackObj, this.mRootHints);
    }
    
    public void connect()
    {
      MediaBrowserCompatApi21.connect(this.mBrowserObj);
    }
    
    public void disconnect()
    {
      MediaBrowserCompat.ServiceBinderWrapper localServiceBinderWrapper = this.mServiceBinderWrapper;
      if (localServiceBinderWrapper != null)
      {
        Messenger localMessenger = this.mCallbacksMessenger;
        if (localMessenger != null) {
          try
          {
            localServiceBinderWrapper.unregisterCallbackMessenger(localMessenger);
          }
          catch (RemoteException localRemoteException)
          {
            Log.i("MediaBrowserCompat", "Remote error unregistering client messenger.");
          }
        }
      }
      MediaBrowserCompatApi21.disconnect(this.mBrowserObj);
    }
    
    public Bundle getExtras()
    {
      return MediaBrowserCompatApi21.getExtras(this.mBrowserObj);
    }
    
    public void getItem(final String paramString, final MediaBrowserCompat.ItemCallback paramItemCallback)
    {
      if (!TextUtils.isEmpty(paramString))
      {
        if (paramItemCallback != null)
        {
          if (!MediaBrowserCompatApi21.isConnected(this.mBrowserObj))
          {
            Log.i("MediaBrowserCompat", "Not connected, unable to retrieve the MediaItem.");
            this.mHandler.post(new Runnable()
            {
              public void run()
              {
                paramItemCallback.onError(paramString);
              }
            });
            return;
          }
          if (this.mServiceBinderWrapper == null)
          {
            this.mHandler.post(new Runnable()
            {
              public void run()
              {
                paramItemCallback.onError(paramString);
              }
            });
            return;
          }
          MediaBrowserCompat.ItemReceiver localItemReceiver = new MediaBrowserCompat.ItemReceiver(paramString, paramItemCallback, this.mHandler);
          try
          {
            this.mServiceBinderWrapper.getMediaItem(paramString, localItemReceiver, this.mCallbacksMessenger);
          }
          catch (RemoteException localRemoteException)
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("Remote error getting media item: ");
            localStringBuilder.append(paramString);
            Log.i("MediaBrowserCompat", localStringBuilder.toString());
            this.mHandler.post(new Runnable()
            {
              public void run()
              {
                paramItemCallback.onError(paramString);
              }
            });
          }
          return;
        }
        throw new IllegalArgumentException("cb is null");
      }
      throw new IllegalArgumentException("mediaId is empty");
    }
    
    public String getRoot()
    {
      return MediaBrowserCompatApi21.getRoot(this.mBrowserObj);
    }
    
    public ComponentName getServiceComponent()
    {
      return MediaBrowserCompatApi21.getServiceComponent(this.mBrowserObj);
    }
    
    public MediaSessionCompat.Token getSessionToken()
    {
      if (this.mMediaSessionToken == null) {
        this.mMediaSessionToken = MediaSessionCompat.Token.fromToken(MediaBrowserCompatApi21.getSessionToken(this.mBrowserObj));
      }
      return this.mMediaSessionToken;
    }
    
    public boolean isConnected()
    {
      return MediaBrowserCompatApi21.isConnected(this.mBrowserObj);
    }
    
    public void onConnected()
    {
      Object localObject = MediaBrowserCompatApi21.getExtras(this.mBrowserObj);
      if (localObject == null) {
        return;
      }
      IBinder localIBinder = BundleCompat.getBinder((Bundle)localObject, "extra_messenger");
      if (localIBinder != null)
      {
        this.mServiceBinderWrapper = new MediaBrowserCompat.ServiceBinderWrapper(localIBinder, this.mRootHints);
        this.mCallbacksMessenger = new Messenger(this.mHandler);
        this.mHandler.setCallbacksMessenger(this.mCallbacksMessenger);
        try
        {
          this.mServiceBinderWrapper.registerCallbackMessenger(this.mCallbacksMessenger);
        }
        catch (RemoteException localRemoteException)
        {
          Log.i("MediaBrowserCompat", "Remote error registering client messenger.");
        }
      }
      localObject = IMediaSession.Stub.asInterface(BundleCompat.getBinder((Bundle)localObject, "extra_session_binder"));
      if (localObject != null) {
        this.mMediaSessionToken = MediaSessionCompat.Token.fromToken(MediaBrowserCompatApi21.getSessionToken(this.mBrowserObj), (IMediaSession)localObject);
      }
    }
    
    public void onConnectionFailed() {}
    
    public void onConnectionFailed(Messenger paramMessenger) {}
    
    public void onConnectionSuspended()
    {
      this.mServiceBinderWrapper = null;
      this.mCallbacksMessenger = null;
      this.mMediaSessionToken = null;
      this.mHandler.setCallbacksMessenger(null);
    }
    
    public void onLoadChildren(Messenger paramMessenger, String paramString, List paramList, Bundle paramBundle)
    {
      if (this.mCallbacksMessenger != paramMessenger) {
        return;
      }
      paramMessenger = (MediaBrowserCompat.Subscription)this.mSubscriptions.get(paramString);
      if (paramMessenger == null)
      {
        if (MediaBrowserCompat.DEBUG)
        {
          paramMessenger = new StringBuilder();
          paramMessenger.append("onLoadChildren for id that isn't subscribed id=");
          paramMessenger.append(paramString);
          Log.d("MediaBrowserCompat", paramMessenger.toString());
        }
        return;
      }
      paramMessenger = paramMessenger.getCallback(this.mContext, paramBundle);
      if (paramMessenger != null) {
        if (paramBundle == null)
        {
          if (paramList == null) {
            paramMessenger.onError(paramString);
          } else {
            paramMessenger.onChildrenLoaded(paramString, paramList);
          }
        }
        else if (paramList == null) {
          paramMessenger.onError(paramString, paramBundle);
        } else {
          paramMessenger.onChildrenLoaded(paramString, paramList, paramBundle);
        }
      }
    }
    
    public void onServiceConnected(Messenger paramMessenger, String paramString, MediaSessionCompat.Token paramToken, Bundle paramBundle) {}
    
    public void search(final String paramString, final Bundle paramBundle, final MediaBrowserCompat.SearchCallback paramSearchCallback)
    {
      if (isConnected())
      {
        if (this.mServiceBinderWrapper == null)
        {
          Log.i("MediaBrowserCompat", "The connected service doesn't support search.");
          this.mHandler.post(new Runnable()
          {
            public void run()
            {
              paramSearchCallback.onError(paramString, paramBundle);
            }
          });
          return;
        }
        Object localObject = new MediaBrowserCompat.SearchResultReceiver(paramString, paramBundle, paramSearchCallback, this.mHandler);
        try
        {
          this.mServiceBinderWrapper.search(paramString, paramBundle, (ResultReceiver)localObject, this.mCallbacksMessenger);
        }
        catch (RemoteException localRemoteException)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Remote error searching items with query: ");
          ((StringBuilder)localObject).append(paramString);
          Log.i("MediaBrowserCompat", ((StringBuilder)localObject).toString(), localRemoteException);
          this.mHandler.post(new Runnable()
          {
            public void run()
            {
              paramSearchCallback.onError(paramString, paramBundle);
            }
          });
        }
        return;
      }
      throw new IllegalStateException("search() called while not connected");
    }
    
    public void sendCustomAction(final String paramString, final Bundle paramBundle, final MediaBrowserCompat.CustomActionCallback paramCustomActionCallback)
    {
      if (isConnected())
      {
        if (this.mServiceBinderWrapper == null)
        {
          Log.i("MediaBrowserCompat", "The connected service doesn't support sendCustomAction.");
          if (paramCustomActionCallback != null) {
            this.mHandler.post(new Runnable()
            {
              public void run()
              {
                paramCustomActionCallback.onError(paramString, paramBundle, null);
              }
            });
          }
        }
        Object localObject = new MediaBrowserCompat.CustomActionResultReceiver(paramString, paramBundle, paramCustomActionCallback, this.mHandler);
        try
        {
          this.mServiceBinderWrapper.sendCustomAction(paramString, paramBundle, (ResultReceiver)localObject, this.mCallbacksMessenger);
        }
        catch (RemoteException localRemoteException)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Remote error sending a custom action: action=");
          ((StringBuilder)localObject).append(paramString);
          ((StringBuilder)localObject).append(", extras=");
          ((StringBuilder)localObject).append(paramBundle);
          Log.i("MediaBrowserCompat", ((StringBuilder)localObject).toString(), localRemoteException);
          if (paramCustomActionCallback != null) {
            this.mHandler.post(new Runnable()
            {
              public void run()
              {
                paramCustomActionCallback.onError(paramString, paramBundle, null);
              }
            });
          }
        }
        return;
      }
      paramCustomActionCallback = new StringBuilder();
      paramCustomActionCallback.append("Cannot send a custom action (");
      paramCustomActionCallback.append(paramString);
      paramCustomActionCallback.append(") with ");
      paramCustomActionCallback.append("extras ");
      paramCustomActionCallback.append(paramBundle);
      paramCustomActionCallback.append(" because the browser is not connected to the ");
      paramCustomActionCallback.append("service.");
      throw new IllegalStateException(paramCustomActionCallback.toString());
    }
    
    public void subscribe(String paramString, Bundle paramBundle, MediaBrowserCompat.SubscriptionCallback paramSubscriptionCallback)
    {
      MediaBrowserCompat.Subscription localSubscription = (MediaBrowserCompat.Subscription)this.mSubscriptions.get(paramString);
      Object localObject = localSubscription;
      if (localSubscription == null)
      {
        localObject = new MediaBrowserCompat.Subscription();
        this.mSubscriptions.put(paramString, localObject);
      }
      MediaBrowserCompat.SubscriptionCallback.access$100(paramSubscriptionCallback, (MediaBrowserCompat.Subscription)localObject);
      if (paramBundle == null) {
        paramBundle = null;
      } else {
        paramBundle = new Bundle(paramBundle);
      }
      ((MediaBrowserCompat.Subscription)localObject).putCallback(this.mContext, paramBundle, paramSubscriptionCallback);
      localObject = this.mServiceBinderWrapper;
      if (localObject == null) {
        MediaBrowserCompatApi21.subscribe(this.mBrowserObj, paramString, MediaBrowserCompat.SubscriptionCallback.access$200(paramSubscriptionCallback));
      } else {
        try
        {
          ((MediaBrowserCompat.ServiceBinderWrapper)localObject).addSubscription(paramString, MediaBrowserCompat.SubscriptionCallback.access$000(paramSubscriptionCallback), paramBundle, this.mCallbacksMessenger);
        }
        catch (RemoteException paramBundle)
        {
          paramBundle = new StringBuilder();
          paramBundle.append("Remote error subscribing media item: ");
          paramBundle.append(paramString);
          Log.i("MediaBrowserCompat", paramBundle.toString());
        }
      }
    }
    
    public void unsubscribe(String paramString, MediaBrowserCompat.SubscriptionCallback paramSubscriptionCallback)
    {
      MediaBrowserCompat.Subscription localSubscription = (MediaBrowserCompat.Subscription)this.mSubscriptions.get(paramString);
      if (localSubscription == null) {
        return;
      }
      Object localObject = this.mServiceBinderWrapper;
      List localList;
      int i;
      if (localObject == null)
      {
        if (paramSubscriptionCallback == null)
        {
          MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, paramString);
        }
        else
        {
          localList = localSubscription.getCallbacks();
          localObject = localSubscription.getOptionsList();
          for (i = localList.size() - 1; i >= 0; i--) {
            if (localList.get(i) == paramSubscriptionCallback)
            {
              localList.remove(i);
              ((List)localObject).remove(i);
            }
          }
          if (localList.size() == 0) {
            MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, paramString);
          }
        }
      }
      else
      {
        if (paramSubscriptionCallback == null) {}
        try
        {
          ((MediaBrowserCompat.ServiceBinderWrapper)localObject).removeSubscription(paramString, null, this.mCallbacksMessenger);
        }
        catch (RemoteException localRemoteException)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("removeSubscription failed with RemoteException parentId=");
          localStringBuilder.append(paramString);
          Log.d("MediaBrowserCompat", localStringBuilder.toString());
        }
        localObject = localSubscription.getCallbacks();
        localList = localSubscription.getOptionsList();
        for (i = ((List)localObject).size() - 1; i >= 0; i--) {
          if (((List)localObject).get(i) == paramSubscriptionCallback)
          {
            this.mServiceBinderWrapper.removeSubscription(paramString, MediaBrowserCompat.SubscriptionCallback.access$000(paramSubscriptionCallback), this.mCallbacksMessenger);
            ((List)localObject).remove(i);
            localList.remove(i);
          }
        }
      }
      if ((localSubscription.isEmpty()) || (paramSubscriptionCallback == null)) {
        this.mSubscriptions.remove(paramString);
      }
    }
  }
  
  static class MediaBrowserImplApi23
    extends MediaBrowserCompat.MediaBrowserImplApi21
  {
    public MediaBrowserImplApi23(Context paramContext, ComponentName paramComponentName, MediaBrowserCompat.ConnectionCallback paramConnectionCallback, Bundle paramBundle)
    {
      super(paramComponentName, paramConnectionCallback, paramBundle);
    }
    
    public void getItem(String paramString, MediaBrowserCompat.ItemCallback paramItemCallback)
    {
      if (this.mServiceBinderWrapper == null) {
        MediaBrowserCompatApi23.getItem(this.mBrowserObj, paramString, paramItemCallback.mItemCallbackObj);
      } else {
        super.getItem(paramString, paramItemCallback);
      }
    }
  }
  
  static class MediaBrowserImplApi24
    extends MediaBrowserCompat.MediaBrowserImplApi23
  {
    public MediaBrowserImplApi24(Context paramContext, ComponentName paramComponentName, MediaBrowserCompat.ConnectionCallback paramConnectionCallback, Bundle paramBundle)
    {
      super(paramComponentName, paramConnectionCallback, paramBundle);
    }
    
    public void subscribe(String paramString, Bundle paramBundle, MediaBrowserCompat.SubscriptionCallback paramSubscriptionCallback)
    {
      if (paramBundle == null) {
        MediaBrowserCompatApi21.subscribe(this.mBrowserObj, paramString, MediaBrowserCompat.SubscriptionCallback.access$200(paramSubscriptionCallback));
      } else {
        MediaBrowserCompatApi24.subscribe(this.mBrowserObj, paramString, paramBundle, MediaBrowserCompat.SubscriptionCallback.access$200(paramSubscriptionCallback));
      }
    }
    
    public void unsubscribe(String paramString, MediaBrowserCompat.SubscriptionCallback paramSubscriptionCallback)
    {
      if (paramSubscriptionCallback == null) {
        MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, paramString);
      } else {
        MediaBrowserCompatApi24.unsubscribe(this.mBrowserObj, paramString, MediaBrowserCompat.SubscriptionCallback.access$200(paramSubscriptionCallback));
      }
    }
  }
  
  static class MediaBrowserImplBase
    implements MediaBrowserCompat.MediaBrowserImpl, MediaBrowserCompat.MediaBrowserServiceCallbackImpl
  {
    static final int CONNECT_STATE_CONNECTED = 3;
    static final int CONNECT_STATE_CONNECTING = 2;
    static final int CONNECT_STATE_DISCONNECTED = 1;
    static final int CONNECT_STATE_DISCONNECTING = 0;
    static final int CONNECT_STATE_SUSPENDED = 4;
    final MediaBrowserCompat.ConnectionCallback mCallback;
    Messenger mCallbacksMessenger;
    final Context mContext;
    private Bundle mExtras;
    final MediaBrowserCompat.CallbackHandler mHandler = new MediaBrowserCompat.CallbackHandler(this);
    private MediaSessionCompat.Token mMediaSessionToken;
    final Bundle mRootHints;
    private String mRootId;
    MediaBrowserCompat.ServiceBinderWrapper mServiceBinderWrapper;
    final ComponentName mServiceComponent;
    MediaServiceConnection mServiceConnection;
    int mState = 1;
    private final ArrayMap<String, MediaBrowserCompat.Subscription> mSubscriptions = new ArrayMap();
    
    public MediaBrowserImplBase(Context paramContext, ComponentName paramComponentName, MediaBrowserCompat.ConnectionCallback paramConnectionCallback, Bundle paramBundle)
    {
      if (paramContext != null)
      {
        if (paramComponentName != null)
        {
          if (paramConnectionCallback != null)
          {
            this.mContext = paramContext;
            this.mServiceComponent = paramComponentName;
            this.mCallback = paramConnectionCallback;
            if (paramBundle == null) {
              paramContext = null;
            } else {
              paramContext = new Bundle(paramBundle);
            }
            this.mRootHints = paramContext;
            return;
          }
          throw new IllegalArgumentException("connection callback must not be null");
        }
        throw new IllegalArgumentException("service component must not be null");
      }
      throw new IllegalArgumentException("context must not be null");
    }
    
    private static String getStateLabel(int paramInt)
    {
      switch (paramInt)
      {
      default: 
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("UNKNOWN/");
        localStringBuilder.append(paramInt);
        return localStringBuilder.toString();
      case 4: 
        return "CONNECT_STATE_SUSPENDED";
      case 3: 
        return "CONNECT_STATE_CONNECTED";
      case 2: 
        return "CONNECT_STATE_CONNECTING";
      case 1: 
        return "CONNECT_STATE_DISCONNECTED";
      }
      return "CONNECT_STATE_DISCONNECTING";
    }
    
    private boolean isCurrent(Messenger paramMessenger, String paramString)
    {
      if (this.mCallbacksMessenger == paramMessenger)
      {
        i = this.mState;
        if ((i != 0) && (i != 1)) {
          return true;
        }
      }
      int i = this.mState;
      if ((i != 0) && (i != 1))
      {
        paramMessenger = new StringBuilder();
        paramMessenger.append(paramString);
        paramMessenger.append(" for ");
        paramMessenger.append(this.mServiceComponent);
        paramMessenger.append(" with mCallbacksMessenger=");
        paramMessenger.append(this.mCallbacksMessenger);
        paramMessenger.append(" this=");
        paramMessenger.append(this);
        Log.i("MediaBrowserCompat", paramMessenger.toString());
      }
      return false;
    }
    
    public void connect()
    {
      int i = this.mState;
      if ((i != 0) && (i != 1))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("connect() called while neigther disconnecting nor disconnected (state=");
        localStringBuilder.append(getStateLabel(this.mState));
        localStringBuilder.append(")");
        throw new IllegalStateException(localStringBuilder.toString());
      }
      this.mState = 2;
      this.mHandler.post(new Runnable()
      {
        public void run()
        {
          if (MediaBrowserCompat.MediaBrowserImplBase.this.mState == 0) {
            return;
          }
          MediaBrowserCompat.MediaBrowserImplBase.this.mState = 2;
          Object localObject;
          if ((MediaBrowserCompat.DEBUG) && (MediaBrowserCompat.MediaBrowserImplBase.this.mServiceConnection != null))
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("mServiceConnection should be null. Instead it is ");
            ((StringBuilder)localObject).append(MediaBrowserCompat.MediaBrowserImplBase.this.mServiceConnection);
            throw new RuntimeException(((StringBuilder)localObject).toString());
          }
          if (MediaBrowserCompat.MediaBrowserImplBase.this.mServiceBinderWrapper == null)
          {
            if (MediaBrowserCompat.MediaBrowserImplBase.this.mCallbacksMessenger == null)
            {
              Intent localIntent = new Intent("android.media.browse.MediaBrowserService");
              localIntent.setComponent(MediaBrowserCompat.MediaBrowserImplBase.this.mServiceComponent);
              localObject = MediaBrowserCompat.MediaBrowserImplBase.this;
              ((MediaBrowserCompat.MediaBrowserImplBase)localObject).mServiceConnection = new MediaBrowserCompat.MediaBrowserImplBase.MediaServiceConnection((MediaBrowserCompat.MediaBrowserImplBase)localObject);
              boolean bool1 = false;
              boolean bool2;
              try
              {
                bool2 = MediaBrowserCompat.MediaBrowserImplBase.this.mContext.bindService(localIntent, MediaBrowserCompat.MediaBrowserImplBase.this.mServiceConnection, 1);
              }
              catch (Exception localException)
              {
                localStringBuilder = new StringBuilder();
                localStringBuilder.append("Failed binding to service ");
                localStringBuilder.append(MediaBrowserCompat.MediaBrowserImplBase.this.mServiceComponent);
                Log.e("MediaBrowserCompat", localStringBuilder.toString());
                bool2 = bool1;
              }
              if (!bool2)
              {
                MediaBrowserCompat.MediaBrowserImplBase.this.forceCloseConnection();
                MediaBrowserCompat.MediaBrowserImplBase.this.mCallback.onConnectionFailed();
              }
              if (MediaBrowserCompat.DEBUG)
              {
                Log.d("MediaBrowserCompat", "connect...");
                MediaBrowserCompat.MediaBrowserImplBase.this.dump();
              }
              return;
            }
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("mCallbacksMessenger should be null. Instead it is ");
            localStringBuilder.append(MediaBrowserCompat.MediaBrowserImplBase.this.mCallbacksMessenger);
            throw new RuntimeException(localStringBuilder.toString());
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("mServiceBinderWrapper should be null. Instead it is ");
          localStringBuilder.append(MediaBrowserCompat.MediaBrowserImplBase.this.mServiceBinderWrapper);
          throw new RuntimeException(localStringBuilder.toString());
        }
      });
    }
    
    public void disconnect()
    {
      this.mState = 0;
      this.mHandler.post(new Runnable()
      {
        public void run()
        {
          if (MediaBrowserCompat.MediaBrowserImplBase.this.mCallbacksMessenger != null) {
            try
            {
              MediaBrowserCompat.MediaBrowserImplBase.this.mServiceBinderWrapper.disconnect(MediaBrowserCompat.MediaBrowserImplBase.this.mCallbacksMessenger);
            }
            catch (RemoteException localRemoteException)
            {
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("RemoteException during connect for ");
              localStringBuilder.append(MediaBrowserCompat.MediaBrowserImplBase.this.mServiceComponent);
              Log.w("MediaBrowserCompat", localStringBuilder.toString());
            }
          }
          int i = MediaBrowserCompat.MediaBrowserImplBase.this.mState;
          MediaBrowserCompat.MediaBrowserImplBase.this.forceCloseConnection();
          if (i != 0) {
            MediaBrowserCompat.MediaBrowserImplBase.this.mState = i;
          }
          if (MediaBrowserCompat.DEBUG)
          {
            Log.d("MediaBrowserCompat", "disconnect...");
            MediaBrowserCompat.MediaBrowserImplBase.this.dump();
          }
        }
      });
    }
    
    void dump()
    {
      Log.d("MediaBrowserCompat", "MediaBrowserCompat...");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("  mServiceComponent=");
      localStringBuilder.append(this.mServiceComponent);
      Log.d("MediaBrowserCompat", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("  mCallback=");
      localStringBuilder.append(this.mCallback);
      Log.d("MediaBrowserCompat", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("  mRootHints=");
      localStringBuilder.append(this.mRootHints);
      Log.d("MediaBrowserCompat", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("  mState=");
      localStringBuilder.append(getStateLabel(this.mState));
      Log.d("MediaBrowserCompat", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("  mServiceConnection=");
      localStringBuilder.append(this.mServiceConnection);
      Log.d("MediaBrowserCompat", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("  mServiceBinderWrapper=");
      localStringBuilder.append(this.mServiceBinderWrapper);
      Log.d("MediaBrowserCompat", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("  mCallbacksMessenger=");
      localStringBuilder.append(this.mCallbacksMessenger);
      Log.d("MediaBrowserCompat", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("  mRootId=");
      localStringBuilder.append(this.mRootId);
      Log.d("MediaBrowserCompat", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("  mMediaSessionToken=");
      localStringBuilder.append(this.mMediaSessionToken);
      Log.d("MediaBrowserCompat", localStringBuilder.toString());
    }
    
    void forceCloseConnection()
    {
      MediaServiceConnection localMediaServiceConnection = this.mServiceConnection;
      if (localMediaServiceConnection != null) {
        this.mContext.unbindService(localMediaServiceConnection);
      }
      this.mState = 1;
      this.mServiceConnection = null;
      this.mServiceBinderWrapper = null;
      this.mCallbacksMessenger = null;
      this.mHandler.setCallbacksMessenger(null);
      this.mRootId = null;
      this.mMediaSessionToken = null;
    }
    
    public Bundle getExtras()
    {
      if (isConnected()) {
        return this.mExtras;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getExtras() called while not connected (state=");
      localStringBuilder.append(getStateLabel(this.mState));
      localStringBuilder.append(")");
      throw new IllegalStateException(localStringBuilder.toString());
    }
    
    public void getItem(final String paramString, final MediaBrowserCompat.ItemCallback paramItemCallback)
    {
      if (!TextUtils.isEmpty(paramString))
      {
        if (paramItemCallback != null)
        {
          if (!isConnected())
          {
            Log.i("MediaBrowserCompat", "Not connected, unable to retrieve the MediaItem.");
            this.mHandler.post(new Runnable()
            {
              public void run()
              {
                paramItemCallback.onError(paramString);
              }
            });
            return;
          }
          MediaBrowserCompat.ItemReceiver localItemReceiver = new MediaBrowserCompat.ItemReceiver(paramString, paramItemCallback, this.mHandler);
          try
          {
            this.mServiceBinderWrapper.getMediaItem(paramString, localItemReceiver, this.mCallbacksMessenger);
          }
          catch (RemoteException localRemoteException)
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("Remote error getting media item: ");
            localStringBuilder.append(paramString);
            Log.i("MediaBrowserCompat", localStringBuilder.toString());
            this.mHandler.post(new Runnable()
            {
              public void run()
              {
                paramItemCallback.onError(paramString);
              }
            });
          }
          return;
        }
        throw new IllegalArgumentException("cb is null");
      }
      throw new IllegalArgumentException("mediaId is empty");
    }
    
    public String getRoot()
    {
      if (isConnected()) {
        return this.mRootId;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getRoot() called while not connected(state=");
      localStringBuilder.append(getStateLabel(this.mState));
      localStringBuilder.append(")");
      throw new IllegalStateException(localStringBuilder.toString());
    }
    
    public ComponentName getServiceComponent()
    {
      if (isConnected()) {
        return this.mServiceComponent;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getServiceComponent() called while not connected (state=");
      localStringBuilder.append(this.mState);
      localStringBuilder.append(")");
      throw new IllegalStateException(localStringBuilder.toString());
    }
    
    public MediaSessionCompat.Token getSessionToken()
    {
      if (isConnected()) {
        return this.mMediaSessionToken;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getSessionToken() called while not connected(state=");
      localStringBuilder.append(this.mState);
      localStringBuilder.append(")");
      throw new IllegalStateException(localStringBuilder.toString());
    }
    
    public boolean isConnected()
    {
      boolean bool;
      if (this.mState == 3) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onConnectionFailed(Messenger paramMessenger)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("onConnectFailed for ");
      localStringBuilder.append(this.mServiceComponent);
      Log.e("MediaBrowserCompat", localStringBuilder.toString());
      if (!isCurrent(paramMessenger, "onConnectFailed")) {
        return;
      }
      if (this.mState != 2)
      {
        paramMessenger = new StringBuilder();
        paramMessenger.append("onConnect from service while mState=");
        paramMessenger.append(getStateLabel(this.mState));
        paramMessenger.append("... ignoring");
        Log.w("MediaBrowserCompat", paramMessenger.toString());
        return;
      }
      forceCloseConnection();
      this.mCallback.onConnectionFailed();
    }
    
    public void onLoadChildren(Messenger paramMessenger, String paramString, List paramList, Bundle paramBundle)
    {
      if (!isCurrent(paramMessenger, "onLoadChildren")) {
        return;
      }
      if (MediaBrowserCompat.DEBUG)
      {
        paramMessenger = new StringBuilder();
        paramMessenger.append("onLoadChildren for ");
        paramMessenger.append(this.mServiceComponent);
        paramMessenger.append(" id=");
        paramMessenger.append(paramString);
        Log.d("MediaBrowserCompat", paramMessenger.toString());
      }
      paramMessenger = (MediaBrowserCompat.Subscription)this.mSubscriptions.get(paramString);
      if (paramMessenger == null)
      {
        if (MediaBrowserCompat.DEBUG)
        {
          paramMessenger = new StringBuilder();
          paramMessenger.append("onLoadChildren for id that isn't subscribed id=");
          paramMessenger.append(paramString);
          Log.d("MediaBrowserCompat", paramMessenger.toString());
        }
        return;
      }
      paramMessenger = paramMessenger.getCallback(this.mContext, paramBundle);
      if (paramMessenger != null) {
        if (paramBundle == null)
        {
          if (paramList == null) {
            paramMessenger.onError(paramString);
          } else {
            paramMessenger.onChildrenLoaded(paramString, paramList);
          }
        }
        else if (paramList == null) {
          paramMessenger.onError(paramString, paramBundle);
        } else {
          paramMessenger.onChildrenLoaded(paramString, paramList, paramBundle);
        }
      }
    }
    
    public void onServiceConnected(Messenger paramMessenger, String paramString, MediaSessionCompat.Token paramToken, Bundle paramBundle)
    {
      if (!isCurrent(paramMessenger, "onConnect")) {
        return;
      }
      if (this.mState != 2)
      {
        paramMessenger = new StringBuilder();
        paramMessenger.append("onConnect from service while mState=");
        paramMessenger.append(getStateLabel(this.mState));
        paramMessenger.append("... ignoring");
        Log.w("MediaBrowserCompat", paramMessenger.toString());
        return;
      }
      this.mRootId = paramString;
      this.mMediaSessionToken = paramToken;
      this.mExtras = paramBundle;
      this.mState = 3;
      if (MediaBrowserCompat.DEBUG)
      {
        Log.d("MediaBrowserCompat", "ServiceCallbacks.onConnect...");
        dump();
      }
      this.mCallback.onConnected();
      try
      {
        paramMessenger = this.mSubscriptions.entrySet().iterator();
        while (paramMessenger.hasNext())
        {
          paramToken = (Map.Entry)paramMessenger.next();
          paramString = (String)paramToken.getKey();
          paramBundle = (MediaBrowserCompat.Subscription)paramToken.getValue();
          paramToken = paramBundle.getCallbacks();
          paramBundle = paramBundle.getOptionsList();
          for (int i = 0; i < paramToken.size(); i++) {
            this.mServiceBinderWrapper.addSubscription(paramString, MediaBrowserCompat.SubscriptionCallback.access$000((MediaBrowserCompat.SubscriptionCallback)paramToken.get(i)), (Bundle)paramBundle.get(i), this.mCallbacksMessenger);
          }
        }
        return;
      }
      catch (RemoteException paramMessenger)
      {
        Log.d("MediaBrowserCompat", "addSubscription failed with RemoteException.");
      }
    }
    
    public void search(final String paramString, final Bundle paramBundle, final MediaBrowserCompat.SearchCallback paramSearchCallback)
    {
      if (isConnected())
      {
        Object localObject = new MediaBrowserCompat.SearchResultReceiver(paramString, paramBundle, paramSearchCallback, this.mHandler);
        try
        {
          this.mServiceBinderWrapper.search(paramString, paramBundle, (ResultReceiver)localObject, this.mCallbacksMessenger);
        }
        catch (RemoteException localRemoteException)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Remote error searching items with query: ");
          ((StringBuilder)localObject).append(paramString);
          Log.i("MediaBrowserCompat", ((StringBuilder)localObject).toString(), localRemoteException);
          this.mHandler.post(new Runnable()
          {
            public void run()
            {
              paramSearchCallback.onError(paramString, paramBundle);
            }
          });
        }
        return;
      }
      paramString = new StringBuilder();
      paramString.append("search() called while not connected (state=");
      paramString.append(getStateLabel(this.mState));
      paramString.append(")");
      throw new IllegalStateException(paramString.toString());
    }
    
    public void sendCustomAction(final String paramString, final Bundle paramBundle, final MediaBrowserCompat.CustomActionCallback paramCustomActionCallback)
    {
      if (isConnected())
      {
        Object localObject = new MediaBrowserCompat.CustomActionResultReceiver(paramString, paramBundle, paramCustomActionCallback, this.mHandler);
        try
        {
          this.mServiceBinderWrapper.sendCustomAction(paramString, paramBundle, (ResultReceiver)localObject, this.mCallbacksMessenger);
        }
        catch (RemoteException localRemoteException)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Remote error sending a custom action: action=");
          ((StringBuilder)localObject).append(paramString);
          ((StringBuilder)localObject).append(", extras=");
          ((StringBuilder)localObject).append(paramBundle);
          Log.i("MediaBrowserCompat", ((StringBuilder)localObject).toString(), localRemoteException);
          if (paramCustomActionCallback != null) {
            this.mHandler.post(new Runnable()
            {
              public void run()
              {
                paramCustomActionCallback.onError(paramString, paramBundle, null);
              }
            });
          }
        }
        return;
      }
      paramCustomActionCallback = new StringBuilder();
      paramCustomActionCallback.append("Cannot send a custom action (");
      paramCustomActionCallback.append(paramString);
      paramCustomActionCallback.append(") with ");
      paramCustomActionCallback.append("extras ");
      paramCustomActionCallback.append(paramBundle);
      paramCustomActionCallback.append(" because the browser is not connected to the ");
      paramCustomActionCallback.append("service.");
      throw new IllegalStateException(paramCustomActionCallback.toString());
    }
    
    public void subscribe(String paramString, Bundle paramBundle, MediaBrowserCompat.SubscriptionCallback paramSubscriptionCallback)
    {
      MediaBrowserCompat.Subscription localSubscription1 = (MediaBrowserCompat.Subscription)this.mSubscriptions.get(paramString);
      MediaBrowserCompat.Subscription localSubscription2 = localSubscription1;
      if (localSubscription1 == null)
      {
        localSubscription2 = new MediaBrowserCompat.Subscription();
        this.mSubscriptions.put(paramString, localSubscription2);
      }
      if (paramBundle == null) {
        paramBundle = null;
      } else {
        paramBundle = new Bundle(paramBundle);
      }
      localSubscription2.putCallback(this.mContext, paramBundle, paramSubscriptionCallback);
      if (isConnected()) {
        try
        {
          this.mServiceBinderWrapper.addSubscription(paramString, MediaBrowserCompat.SubscriptionCallback.access$000(paramSubscriptionCallback), paramBundle, this.mCallbacksMessenger);
        }
        catch (RemoteException paramBundle)
        {
          paramBundle = new StringBuilder();
          paramBundle.append("addSubscription failed with RemoteException parentId=");
          paramBundle.append(paramString);
          Log.d("MediaBrowserCompat", paramBundle.toString());
        }
      }
    }
    
    public void unsubscribe(String paramString, MediaBrowserCompat.SubscriptionCallback paramSubscriptionCallback)
    {
      MediaBrowserCompat.Subscription localSubscription = (MediaBrowserCompat.Subscription)this.mSubscriptions.get(paramString);
      if (localSubscription == null) {
        return;
      }
      if (paramSubscriptionCallback == null) {}
      try
      {
        if (!isConnected()) {
          break label172;
        }
        this.mServiceBinderWrapper.removeSubscription(paramString, null, this.mCallbacksMessenger);
      }
      catch (RemoteException localRemoteException)
      {
        List localList1;
        List localList2;
        int i;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("removeSubscription failed with RemoteException parentId=");
        localStringBuilder.append(paramString);
        Log.d("MediaBrowserCompat", localStringBuilder.toString());
        label172:
        if ((!localSubscription.isEmpty()) && (paramSubscriptionCallback != null)) {
          return;
        }
        this.mSubscriptions.remove(paramString);
      }
      localList1 = localSubscription.getCallbacks();
      localList2 = localSubscription.getOptionsList();
      for (i = localList1.size() - 1; i >= 0; i--) {
        if (localList1.get(i) == paramSubscriptionCallback)
        {
          if (isConnected()) {
            this.mServiceBinderWrapper.removeSubscription(paramString, MediaBrowserCompat.SubscriptionCallback.access$000(paramSubscriptionCallback), this.mCallbacksMessenger);
          }
          localList1.remove(i);
          localList2.remove(i);
        }
      }
    }
    
    private class MediaServiceConnection
      implements ServiceConnection
    {
      MediaServiceConnection() {}
      
      private void postOrRun(Runnable paramRunnable)
      {
        if (Thread.currentThread() == MediaBrowserCompat.MediaBrowserImplBase.this.mHandler.getLooper().getThread()) {
          paramRunnable.run();
        } else {
          MediaBrowserCompat.MediaBrowserImplBase.this.mHandler.post(paramRunnable);
        }
      }
      
      boolean isCurrent(String paramString)
      {
        if ((MediaBrowserCompat.MediaBrowserImplBase.this.mServiceConnection == this) && (MediaBrowserCompat.MediaBrowserImplBase.this.mState != 0) && (MediaBrowserCompat.MediaBrowserImplBase.this.mState != 1)) {
          return true;
        }
        if ((MediaBrowserCompat.MediaBrowserImplBase.this.mState != 0) && (MediaBrowserCompat.MediaBrowserImplBase.this.mState != 1))
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(paramString);
          localStringBuilder.append(" for ");
          localStringBuilder.append(MediaBrowserCompat.MediaBrowserImplBase.this.mServiceComponent);
          localStringBuilder.append(" with mServiceConnection=");
          localStringBuilder.append(MediaBrowserCompat.MediaBrowserImplBase.this.mServiceConnection);
          localStringBuilder.append(" this=");
          localStringBuilder.append(this);
          Log.i("MediaBrowserCompat", localStringBuilder.toString());
        }
        return false;
      }
      
      public void onServiceConnected(final ComponentName paramComponentName, final IBinder paramIBinder)
      {
        postOrRun(new Runnable()
        {
          public void run()
          {
            if (MediaBrowserCompat.DEBUG)
            {
              StringBuilder localStringBuilder1 = new StringBuilder();
              localStringBuilder1.append("MediaServiceConnection.onServiceConnected name=");
              localStringBuilder1.append(paramComponentName);
              localStringBuilder1.append(" binder=");
              localStringBuilder1.append(paramIBinder);
              Log.d("MediaBrowserCompat", localStringBuilder1.toString());
              MediaBrowserCompat.MediaBrowserImplBase.this.dump();
            }
            if (!MediaBrowserCompat.MediaBrowserImplBase.MediaServiceConnection.this.isCurrent("onServiceConnected")) {
              return;
            }
            MediaBrowserCompat.MediaBrowserImplBase.this.mServiceBinderWrapper = new MediaBrowserCompat.ServiceBinderWrapper(paramIBinder, MediaBrowserCompat.MediaBrowserImplBase.this.mRootHints);
            MediaBrowserCompat.MediaBrowserImplBase.this.mCallbacksMessenger = new Messenger(MediaBrowserCompat.MediaBrowserImplBase.this.mHandler);
            MediaBrowserCompat.MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(MediaBrowserCompat.MediaBrowserImplBase.this.mCallbacksMessenger);
            MediaBrowserCompat.MediaBrowserImplBase.this.mState = 2;
            try
            {
              if (MediaBrowserCompat.DEBUG)
              {
                Log.d("MediaBrowserCompat", "ServiceCallbacks.onConnect...");
                MediaBrowserCompat.MediaBrowserImplBase.this.dump();
              }
              MediaBrowserCompat.MediaBrowserImplBase.this.mServiceBinderWrapper.connect(MediaBrowserCompat.MediaBrowserImplBase.this.mContext, MediaBrowserCompat.MediaBrowserImplBase.this.mCallbacksMessenger);
            }
            catch (RemoteException localRemoteException)
            {
              StringBuilder localStringBuilder2 = new StringBuilder();
              localStringBuilder2.append("RemoteException during connect for ");
              localStringBuilder2.append(MediaBrowserCompat.MediaBrowserImplBase.this.mServiceComponent);
              Log.w("MediaBrowserCompat", localStringBuilder2.toString());
              if (MediaBrowserCompat.DEBUG)
              {
                Log.d("MediaBrowserCompat", "ServiceCallbacks.onConnect...");
                MediaBrowserCompat.MediaBrowserImplBase.this.dump();
              }
            }
          }
        });
      }
      
      public void onServiceDisconnected(final ComponentName paramComponentName)
      {
        postOrRun(new Runnable()
        {
          public void run()
          {
            if (MediaBrowserCompat.DEBUG)
            {
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("MediaServiceConnection.onServiceDisconnected name=");
              localStringBuilder.append(paramComponentName);
              localStringBuilder.append(" this=");
              localStringBuilder.append(this);
              localStringBuilder.append(" mServiceConnection=");
              localStringBuilder.append(MediaBrowserCompat.MediaBrowserImplBase.this.mServiceConnection);
              Log.d("MediaBrowserCompat", localStringBuilder.toString());
              MediaBrowserCompat.MediaBrowserImplBase.this.dump();
            }
            if (!MediaBrowserCompat.MediaBrowserImplBase.MediaServiceConnection.this.isCurrent("onServiceDisconnected")) {
              return;
            }
            MediaBrowserCompat.MediaBrowserImplBase.this.mServiceBinderWrapper = null;
            MediaBrowserCompat.MediaBrowserImplBase.this.mCallbacksMessenger = null;
            MediaBrowserCompat.MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(null);
            MediaBrowserCompat.MediaBrowserImplBase.this.mState = 4;
            MediaBrowserCompat.MediaBrowserImplBase.this.mCallback.onConnectionSuspended();
          }
        });
      }
    }
  }
  
  static abstract interface MediaBrowserServiceCallbackImpl
  {
    public abstract void onConnectionFailed(Messenger paramMessenger);
    
    public abstract void onLoadChildren(Messenger paramMessenger, String paramString, List paramList, Bundle paramBundle);
    
    public abstract void onServiceConnected(Messenger paramMessenger, String paramString, MediaSessionCompat.Token paramToken, Bundle paramBundle);
  }
  
  public static class MediaItem
    implements Parcelable
  {
    public static final Parcelable.Creator<MediaItem> CREATOR = new Parcelable.Creator()
    {
      public MediaBrowserCompat.MediaItem createFromParcel(Parcel paramAnonymousParcel)
      {
        return new MediaBrowserCompat.MediaItem(paramAnonymousParcel);
      }
      
      public MediaBrowserCompat.MediaItem[] newArray(int paramAnonymousInt)
      {
        return new MediaBrowserCompat.MediaItem[paramAnonymousInt];
      }
    };
    public static final int FLAG_BROWSABLE = 1;
    public static final int FLAG_PLAYABLE = 2;
    private final MediaDescriptionCompat mDescription;
    private final int mFlags;
    
    MediaItem(Parcel paramParcel)
    {
      this.mFlags = paramParcel.readInt();
      this.mDescription = ((MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(paramParcel));
    }
    
    public MediaItem(MediaDescriptionCompat paramMediaDescriptionCompat, int paramInt)
    {
      if (paramMediaDescriptionCompat != null)
      {
        if (!TextUtils.isEmpty(paramMediaDescriptionCompat.getMediaId()))
        {
          this.mFlags = paramInt;
          this.mDescription = paramMediaDescriptionCompat;
          return;
        }
        throw new IllegalArgumentException("description must have a non-empty media id");
      }
      throw new IllegalArgumentException("description cannot be null");
    }
    
    public static MediaItem fromMediaItem(Object paramObject)
    {
      if ((paramObject != null) && (Build.VERSION.SDK_INT >= 21))
      {
        int i = MediaBrowserCompatApi21.MediaItem.getFlags(paramObject);
        return new MediaItem(MediaDescriptionCompat.fromMediaDescription(MediaBrowserCompatApi21.MediaItem.getDescription(paramObject)), i);
      }
      return null;
    }
    
    public static List<MediaItem> fromMediaItemList(List<?> paramList)
    {
      if ((paramList != null) && (Build.VERSION.SDK_INT >= 21))
      {
        ArrayList localArrayList = new ArrayList(paramList.size());
        paramList = paramList.iterator();
        while (paramList.hasNext()) {
          localArrayList.add(fromMediaItem(paramList.next()));
        }
        return localArrayList;
      }
      return null;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public MediaDescriptionCompat getDescription()
    {
      return this.mDescription;
    }
    
    public int getFlags()
    {
      return this.mFlags;
    }
    
    public String getMediaId()
    {
      return this.mDescription.getMediaId();
    }
    
    public boolean isBrowsable()
    {
      int i = this.mFlags;
      boolean bool = true;
      if ((i & 0x1) == 0) {
        bool = false;
      }
      return bool;
    }
    
    public boolean isPlayable()
    {
      boolean bool;
      if ((this.mFlags & 0x2) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder("MediaItem{");
      localStringBuilder.append("mFlags=");
      localStringBuilder.append(this.mFlags);
      localStringBuilder.append(", mDescription=");
      localStringBuilder.append(this.mDescription);
      localStringBuilder.append('}');
      return localStringBuilder.toString();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.mFlags);
      this.mDescription.writeToParcel(paramParcel, paramInt);
    }
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface Flags {}
  }
  
  public static abstract class SearchCallback
  {
    public void onError(String paramString, Bundle paramBundle) {}
    
    public void onSearchResult(String paramString, Bundle paramBundle, List<MediaBrowserCompat.MediaItem> paramList) {}
  }
  
  private static class SearchResultReceiver
    extends ResultReceiver
  {
    private final MediaBrowserCompat.SearchCallback mCallback;
    private final Bundle mExtras;
    private final String mQuery;
    
    SearchResultReceiver(String paramString, Bundle paramBundle, MediaBrowserCompat.SearchCallback paramSearchCallback, Handler paramHandler)
    {
      super();
      this.mQuery = paramString;
      this.mExtras = paramBundle;
      this.mCallback = paramSearchCallback;
    }
    
    protected void onReceiveResult(int paramInt, Bundle paramBundle)
    {
      if (paramBundle != null) {
        paramBundle.setClassLoader(MediaBrowserCompat.class.getClassLoader());
      }
      if ((paramInt == 0) && (paramBundle != null) && (paramBundle.containsKey("search_results")))
      {
        Parcelable[] arrayOfParcelable = paramBundle.getParcelableArray("search_results");
        paramBundle = null;
        if (arrayOfParcelable != null)
        {
          ArrayList localArrayList = new ArrayList();
          int i = arrayOfParcelable.length;
          for (paramInt = 0;; paramInt++)
          {
            paramBundle = localArrayList;
            if (paramInt >= i) {
              break;
            }
            localArrayList.add((MediaBrowserCompat.MediaItem)arrayOfParcelable[paramInt]);
          }
        }
        this.mCallback.onSearchResult(this.mQuery, this.mExtras, paramBundle);
        return;
      }
      this.mCallback.onError(this.mQuery, this.mExtras);
    }
  }
  
  private static class ServiceBinderWrapper
  {
    private Messenger mMessenger;
    private Bundle mRootHints;
    
    public ServiceBinderWrapper(IBinder paramIBinder, Bundle paramBundle)
    {
      this.mMessenger = new Messenger(paramIBinder);
      this.mRootHints = paramBundle;
    }
    
    private void sendRequest(int paramInt, Bundle paramBundle, Messenger paramMessenger)
    {
      Message localMessage = Message.obtain();
      localMessage.what = paramInt;
      localMessage.arg1 = 1;
      localMessage.setData(paramBundle);
      localMessage.replyTo = paramMessenger;
      this.mMessenger.send(localMessage);
    }
    
    void addSubscription(String paramString, IBinder paramIBinder, Bundle paramBundle, Messenger paramMessenger)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("data_media_item_id", paramString);
      BundleCompat.putBinder(localBundle, "data_callback_token", paramIBinder);
      localBundle.putBundle("data_options", paramBundle);
      sendRequest(3, localBundle, paramMessenger);
    }
    
    void connect(Context paramContext, Messenger paramMessenger)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("data_package_name", paramContext.getPackageName());
      localBundle.putBundle("data_root_hints", this.mRootHints);
      sendRequest(1, localBundle, paramMessenger);
    }
    
    void disconnect(Messenger paramMessenger)
    {
      sendRequest(2, null, paramMessenger);
    }
    
    void getMediaItem(String paramString, ResultReceiver paramResultReceiver, Messenger paramMessenger)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("data_media_item_id", paramString);
      localBundle.putParcelable("data_result_receiver", paramResultReceiver);
      sendRequest(5, localBundle, paramMessenger);
    }
    
    void registerCallbackMessenger(Messenger paramMessenger)
    {
      Bundle localBundle = new Bundle();
      localBundle.putBundle("data_root_hints", this.mRootHints);
      sendRequest(6, localBundle, paramMessenger);
    }
    
    void removeSubscription(String paramString, IBinder paramIBinder, Messenger paramMessenger)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("data_media_item_id", paramString);
      BundleCompat.putBinder(localBundle, "data_callback_token", paramIBinder);
      sendRequest(4, localBundle, paramMessenger);
    }
    
    void search(String paramString, Bundle paramBundle, ResultReceiver paramResultReceiver, Messenger paramMessenger)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("data_search_query", paramString);
      localBundle.putBundle("data_search_extras", paramBundle);
      localBundle.putParcelable("data_result_receiver", paramResultReceiver);
      sendRequest(8, localBundle, paramMessenger);
    }
    
    void sendCustomAction(String paramString, Bundle paramBundle, ResultReceiver paramResultReceiver, Messenger paramMessenger)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("data_custom_action", paramString);
      localBundle.putBundle("data_custom_action_extras", paramBundle);
      localBundle.putParcelable("data_result_receiver", paramResultReceiver);
      sendRequest(9, localBundle, paramMessenger);
    }
    
    void unregisterCallbackMessenger(Messenger paramMessenger)
    {
      sendRequest(7, null, paramMessenger);
    }
  }
  
  private static class Subscription
  {
    private final List<MediaBrowserCompat.SubscriptionCallback> mCallbacks = new ArrayList();
    private final List<Bundle> mOptionsList = new ArrayList();
    
    public MediaBrowserCompat.SubscriptionCallback getCallback(Context paramContext, Bundle paramBundle)
    {
      if (paramBundle != null) {
        paramBundle.setClassLoader(paramContext.getClassLoader());
      }
      for (int i = 0; i < this.mOptionsList.size(); i++) {
        if (MediaBrowserCompatUtils.areSameOptions((Bundle)this.mOptionsList.get(i), paramBundle)) {
          return (MediaBrowserCompat.SubscriptionCallback)this.mCallbacks.get(i);
        }
      }
      return null;
    }
    
    public List<MediaBrowserCompat.SubscriptionCallback> getCallbacks()
    {
      return this.mCallbacks;
    }
    
    public List<Bundle> getOptionsList()
    {
      return this.mOptionsList;
    }
    
    public boolean isEmpty()
    {
      return this.mCallbacks.isEmpty();
    }
    
    public void putCallback(Context paramContext, Bundle paramBundle, MediaBrowserCompat.SubscriptionCallback paramSubscriptionCallback)
    {
      if (paramBundle != null) {
        paramBundle.setClassLoader(paramContext.getClassLoader());
      }
      for (int i = 0; i < this.mOptionsList.size(); i++) {
        if (MediaBrowserCompatUtils.areSameOptions((Bundle)this.mOptionsList.get(i), paramBundle))
        {
          this.mCallbacks.set(i, paramSubscriptionCallback);
          return;
        }
      }
      this.mCallbacks.add(paramSubscriptionCallback);
      this.mOptionsList.add(paramBundle);
    }
  }
  
  public static abstract class SubscriptionCallback
  {
    private final Object mSubscriptionCallbackObj;
    WeakReference<MediaBrowserCompat.Subscription> mSubscriptionRef;
    private final IBinder mToken;
    
    public SubscriptionCallback()
    {
      if (Build.VERSION.SDK_INT >= 26)
      {
        this.mSubscriptionCallbackObj = MediaBrowserCompatApi24.createSubscriptionCallback(new StubApi24());
        this.mToken = null;
      }
      else if (Build.VERSION.SDK_INT >= 21)
      {
        this.mSubscriptionCallbackObj = MediaBrowserCompatApi21.createSubscriptionCallback(new StubApi21());
        this.mToken = new Binder();
      }
      else
      {
        this.mSubscriptionCallbackObj = null;
        this.mToken = new Binder();
      }
    }
    
    private void setSubscription(MediaBrowserCompat.Subscription paramSubscription)
    {
      this.mSubscriptionRef = new WeakReference(paramSubscription);
    }
    
    public void onChildrenLoaded(String paramString, List<MediaBrowserCompat.MediaItem> paramList) {}
    
    public void onChildrenLoaded(String paramString, List<MediaBrowserCompat.MediaItem> paramList, Bundle paramBundle) {}
    
    public void onError(String paramString) {}
    
    public void onError(String paramString, Bundle paramBundle) {}
    
    private class StubApi21
      implements MediaBrowserCompatApi21.SubscriptionCallback
    {
      StubApi21() {}
      
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
          i = m;
          if (m > paramList.size()) {
            i = paramList.size();
          }
          return paramList.subList(k, i);
        }
        return Collections.EMPTY_LIST;
      }
      
      public void onChildrenLoaded(String paramString, List<?> paramList)
      {
        Object localObject;
        if (MediaBrowserCompat.SubscriptionCallback.this.mSubscriptionRef == null) {
          localObject = null;
        } else {
          localObject = (MediaBrowserCompat.Subscription)MediaBrowserCompat.SubscriptionCallback.this.mSubscriptionRef.get();
        }
        if (localObject == null)
        {
          MediaBrowserCompat.SubscriptionCallback.this.onChildrenLoaded(paramString, MediaBrowserCompat.MediaItem.fromMediaItemList(paramList));
        }
        else
        {
          paramList = MediaBrowserCompat.MediaItem.fromMediaItemList(paramList);
          List localList = ((MediaBrowserCompat.Subscription)localObject).getCallbacks();
          localObject = ((MediaBrowserCompat.Subscription)localObject).getOptionsList();
          for (int i = 0; i < localList.size(); i++)
          {
            Bundle localBundle = (Bundle)((List)localObject).get(i);
            if (localBundle == null) {
              MediaBrowserCompat.SubscriptionCallback.this.onChildrenLoaded(paramString, paramList);
            } else {
              MediaBrowserCompat.SubscriptionCallback.this.onChildrenLoaded(paramString, applyOptions(paramList, localBundle), localBundle);
            }
          }
        }
      }
      
      public void onError(String paramString)
      {
        MediaBrowserCompat.SubscriptionCallback.this.onError(paramString);
      }
    }
    
    private class StubApi24
      extends MediaBrowserCompat.SubscriptionCallback.StubApi21
      implements MediaBrowserCompatApi24.SubscriptionCallback
    {
      StubApi24()
      {
        super();
      }
      
      public void onChildrenLoaded(String paramString, List<?> paramList, Bundle paramBundle)
      {
        MediaBrowserCompat.SubscriptionCallback.this.onChildrenLoaded(paramString, MediaBrowserCompat.MediaItem.fromMediaItemList(paramList), paramBundle);
      }
      
      public void onError(String paramString, Bundle paramBundle)
      {
        MediaBrowserCompat.SubscriptionCallback.this.onError(paramString, paramBundle);
      }
    }
  }
}


/* Location:              ~/android/support/v4/media/MediaBrowserCompat.class
 *
 * Reversed by:           J
 */