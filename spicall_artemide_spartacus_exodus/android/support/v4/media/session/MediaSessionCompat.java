package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaMetadataEditor;
import android.media.Rating;
import android.media.RemoteControlClient;
import android.media.RemoteControlClient.MetadataEditor;
import android.media.RemoteControlClient.OnMetadataUpdateListener;
import android.media.RemoteControlClient.OnPlaybackPositionUpdateListener;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.MediaMetadataCompat.Builder;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.VolumeProviderCompat.Callback;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MediaSessionCompat
{
  static final String ACTION_ARGUMENT_CAPTIONING_ENABLED = "android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED";
  static final String ACTION_ARGUMENT_EXTRAS = "android.support.v4.media.session.action.ARGUMENT_EXTRAS";
  static final String ACTION_ARGUMENT_MEDIA_ID = "android.support.v4.media.session.action.ARGUMENT_MEDIA_ID";
  static final String ACTION_ARGUMENT_QUERY = "android.support.v4.media.session.action.ARGUMENT_QUERY";
  static final String ACTION_ARGUMENT_RATING = "android.support.v4.media.session.action.ARGUMENT_RATING";
  static final String ACTION_ARGUMENT_REPEAT_MODE = "android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE";
  static final String ACTION_ARGUMENT_SHUFFLE_MODE = "android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE";
  static final String ACTION_ARGUMENT_SHUFFLE_MODE_ENABLED = "android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE_ENABLED";
  static final String ACTION_ARGUMENT_URI = "android.support.v4.media.session.action.ARGUMENT_URI";
  public static final String ACTION_FLAG_AS_INAPPROPRIATE = "android.support.v4.media.session.action.FLAG_AS_INAPPROPRIATE";
  public static final String ACTION_FOLLOW = "android.support.v4.media.session.action.FOLLOW";
  static final String ACTION_PLAY_FROM_URI = "android.support.v4.media.session.action.PLAY_FROM_URI";
  static final String ACTION_PREPARE = "android.support.v4.media.session.action.PREPARE";
  static final String ACTION_PREPARE_FROM_MEDIA_ID = "android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID";
  static final String ACTION_PREPARE_FROM_SEARCH = "android.support.v4.media.session.action.PREPARE_FROM_SEARCH";
  static final String ACTION_PREPARE_FROM_URI = "android.support.v4.media.session.action.PREPARE_FROM_URI";
  static final String ACTION_SET_CAPTIONING_ENABLED = "android.support.v4.media.session.action.SET_CAPTIONING_ENABLED";
  static final String ACTION_SET_RATING = "android.support.v4.media.session.action.SET_RATING";
  static final String ACTION_SET_REPEAT_MODE = "android.support.v4.media.session.action.SET_REPEAT_MODE";
  static final String ACTION_SET_SHUFFLE_MODE = "android.support.v4.media.session.action.SET_SHUFFLE_MODE";
  static final String ACTION_SET_SHUFFLE_MODE_ENABLED = "android.support.v4.media.session.action.SET_SHUFFLE_MODE_ENABLED";
  public static final String ACTION_SKIP_AD = "android.support.v4.media.session.action.SKIP_AD";
  public static final String ACTION_UNFOLLOW = "android.support.v4.media.session.action.UNFOLLOW";
  public static final String ARGUMENT_MEDIA_ATTRIBUTE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE";
  public static final String ARGUMENT_MEDIA_ATTRIBUTE_VALUE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE_VALUE";
  static final String EXTRA_BINDER = "android.support.v4.media.session.EXTRA_BINDER";
  public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;
  public static final int FLAG_HANDLES_QUEUE_COMMANDS = 4;
  public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
  private static final int MAX_BITMAP_SIZE_IN_DP = 320;
  public static final int MEDIA_ATTRIBUTE_ALBUM = 1;
  public static final int MEDIA_ATTRIBUTE_ARTIST = 0;
  public static final int MEDIA_ATTRIBUTE_PLAYLIST = 2;
  static final String TAG = "MediaSessionCompat";
  static int sMaxBitmapSize;
  private final ArrayList<OnActiveChangeListener> mActiveListeners = new ArrayList();
  private final MediaControllerCompat mController;
  private final MediaSessionImpl mImpl;
  
  private MediaSessionCompat(Context paramContext, MediaSessionImpl paramMediaSessionImpl)
  {
    this.mImpl = paramMediaSessionImpl;
    if ((Build.VERSION.SDK_INT >= 21) && (!MediaSessionCompatApi21.hasCallback(paramMediaSessionImpl.getMediaSession()))) {
      setCallback(new Callback() {});
    }
    this.mController = new MediaControllerCompat(paramContext, this);
  }
  
  public MediaSessionCompat(Context paramContext, String paramString)
  {
    this(paramContext, paramString, null, null);
  }
  
  public MediaSessionCompat(Context paramContext, String paramString, ComponentName paramComponentName, PendingIntent paramPendingIntent)
  {
    if (paramContext != null)
    {
      if (!TextUtils.isEmpty(paramString))
      {
        ComponentName localComponentName = paramComponentName;
        if (paramComponentName == null)
        {
          paramComponentName = MediaButtonReceiver.getMediaButtonReceiverComponent(paramContext);
          localComponentName = paramComponentName;
          if (paramComponentName == null)
          {
            Log.w("MediaSessionCompat", "Couldn't find a unique registered media button receiver in the given context.");
            localComponentName = paramComponentName;
          }
        }
        paramComponentName = paramPendingIntent;
        if (localComponentName != null)
        {
          paramComponentName = paramPendingIntent;
          if (paramPendingIntent == null)
          {
            paramComponentName = new Intent("android.intent.action.MEDIA_BUTTON");
            paramComponentName.setComponent(localComponentName);
            paramComponentName = PendingIntent.getBroadcast(paramContext, 0, paramComponentName, 0);
          }
        }
        if (Build.VERSION.SDK_INT >= 21)
        {
          this.mImpl = new MediaSessionImplApi21(paramContext, paramString);
          setCallback(new Callback() {});
          this.mImpl.setMediaButtonReceiver(paramComponentName);
        }
        else if (Build.VERSION.SDK_INT >= 19)
        {
          this.mImpl = new MediaSessionImplApi19(paramContext, paramString, localComponentName, paramComponentName);
        }
        else if (Build.VERSION.SDK_INT >= 18)
        {
          this.mImpl = new MediaSessionImplApi18(paramContext, paramString, localComponentName, paramComponentName);
        }
        else
        {
          this.mImpl = new MediaSessionImplBase(paramContext, paramString, localComponentName, paramComponentName);
        }
        this.mController = new MediaControllerCompat(paramContext, this);
        if (sMaxBitmapSize == 0) {
          sMaxBitmapSize = (int)TypedValue.applyDimension(1, 320.0F, paramContext.getResources().getDisplayMetrics());
        }
        return;
      }
      throw new IllegalArgumentException("tag must not be null or empty");
    }
    throw new IllegalArgumentException("context must not be null");
  }
  
  public static MediaSessionCompat fromMediaSession(Context paramContext, Object paramObject)
  {
    if ((paramContext != null) && (paramObject != null) && (Build.VERSION.SDK_INT >= 21)) {
      return new MediaSessionCompat(paramContext, new MediaSessionImplApi21(paramObject));
    }
    return null;
  }
  
  private static PlaybackStateCompat getStateWithUpdatedPosition(PlaybackStateCompat paramPlaybackStateCompat, MediaMetadataCompat paramMediaMetadataCompat)
  {
    if (paramPlaybackStateCompat != null)
    {
      long l1 = paramPlaybackStateCompat.getPosition();
      long l2 = -1L;
      if (l1 != -1L)
      {
        if ((paramPlaybackStateCompat.getState() == 3) || (paramPlaybackStateCompat.getState() == 4) || (paramPlaybackStateCompat.getState() == 5))
        {
          l1 = paramPlaybackStateCompat.getLastPositionUpdateTime();
          if (l1 > 0L)
          {
            long l3 = SystemClock.elapsedRealtime();
            long l4 = (paramPlaybackStateCompat.getPlaybackSpeed() * (float)(l3 - l1)) + paramPlaybackStateCompat.getPosition();
            l1 = l2;
            if (paramMediaMetadataCompat != null)
            {
              l1 = l2;
              if (paramMediaMetadataCompat.containsKey("android.media.metadata.DURATION")) {
                l1 = paramMediaMetadataCompat.getLong("android.media.metadata.DURATION");
              }
            }
            if ((l1 < 0L) || (l4 <= l1)) {
              if (l4 < 0L) {
                l1 = 0L;
              } else {
                l1 = l4;
              }
            }
            return new PlaybackStateCompat.Builder(paramPlaybackStateCompat).setState(paramPlaybackStateCompat.getState(), l1, paramPlaybackStateCompat.getPlaybackSpeed(), l3).build();
          }
        }
        return paramPlaybackStateCompat;
      }
    }
    return paramPlaybackStateCompat;
  }
  
  public void addOnActiveChangeListener(OnActiveChangeListener paramOnActiveChangeListener)
  {
    if (paramOnActiveChangeListener != null)
    {
      this.mActiveListeners.add(paramOnActiveChangeListener);
      return;
    }
    throw new IllegalArgumentException("Listener may not be null");
  }
  
  public String getCallingPackage()
  {
    return this.mImpl.getCallingPackage();
  }
  
  public MediaControllerCompat getController()
  {
    return this.mController;
  }
  
  public Object getMediaSession()
  {
    return this.mImpl.getMediaSession();
  }
  
  public Object getRemoteControlClient()
  {
    return this.mImpl.getRemoteControlClient();
  }
  
  public Token getSessionToken()
  {
    return this.mImpl.getSessionToken();
  }
  
  public boolean isActive()
  {
    return this.mImpl.isActive();
  }
  
  public void release()
  {
    this.mImpl.release();
  }
  
  public void removeOnActiveChangeListener(OnActiveChangeListener paramOnActiveChangeListener)
  {
    if (paramOnActiveChangeListener != null)
    {
      this.mActiveListeners.remove(paramOnActiveChangeListener);
      return;
    }
    throw new IllegalArgumentException("Listener may not be null");
  }
  
  public void sendSessionEvent(String paramString, Bundle paramBundle)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.mImpl.sendSessionEvent(paramString, paramBundle);
      return;
    }
    throw new IllegalArgumentException("event cannot be null or empty");
  }
  
  public void setActive(boolean paramBoolean)
  {
    this.mImpl.setActive(paramBoolean);
    Iterator localIterator = this.mActiveListeners.iterator();
    while (localIterator.hasNext()) {
      ((OnActiveChangeListener)localIterator.next()).onActiveChanged();
    }
  }
  
  public void setCallback(Callback paramCallback)
  {
    setCallback(paramCallback, null);
  }
  
  public void setCallback(Callback paramCallback, Handler paramHandler)
  {
    MediaSessionImpl localMediaSessionImpl = this.mImpl;
    if (paramHandler == null) {
      paramHandler = new Handler();
    }
    localMediaSessionImpl.setCallback(paramCallback, paramHandler);
  }
  
  public void setCaptioningEnabled(boolean paramBoolean)
  {
    this.mImpl.setCaptioningEnabled(paramBoolean);
  }
  
  public void setExtras(Bundle paramBundle)
  {
    this.mImpl.setExtras(paramBundle);
  }
  
  public void setFlags(int paramInt)
  {
    this.mImpl.setFlags(paramInt);
  }
  
  public void setMediaButtonReceiver(PendingIntent paramPendingIntent)
  {
    this.mImpl.setMediaButtonReceiver(paramPendingIntent);
  }
  
  public void setMetadata(MediaMetadataCompat paramMediaMetadataCompat)
  {
    this.mImpl.setMetadata(paramMediaMetadataCompat);
  }
  
  public void setPlaybackState(PlaybackStateCompat paramPlaybackStateCompat)
  {
    this.mImpl.setPlaybackState(paramPlaybackStateCompat);
  }
  
  public void setPlaybackToLocal(int paramInt)
  {
    this.mImpl.setPlaybackToLocal(paramInt);
  }
  
  public void setPlaybackToRemote(VolumeProviderCompat paramVolumeProviderCompat)
  {
    if (paramVolumeProviderCompat != null)
    {
      this.mImpl.setPlaybackToRemote(paramVolumeProviderCompat);
      return;
    }
    throw new IllegalArgumentException("volumeProvider may not be null!");
  }
  
  public void setQueue(List<QueueItem> paramList)
  {
    this.mImpl.setQueue(paramList);
  }
  
  public void setQueueTitle(CharSequence paramCharSequence)
  {
    this.mImpl.setQueueTitle(paramCharSequence);
  }
  
  public void setRatingType(int paramInt)
  {
    this.mImpl.setRatingType(paramInt);
  }
  
  public void setRepeatMode(int paramInt)
  {
    this.mImpl.setRepeatMode(paramInt);
  }
  
  public void setSessionActivity(PendingIntent paramPendingIntent)
  {
    this.mImpl.setSessionActivity(paramPendingIntent);
  }
  
  public void setShuffleMode(int paramInt)
  {
    this.mImpl.setShuffleMode(paramInt);
  }
  
  @Deprecated
  public void setShuffleModeEnabled(boolean paramBoolean)
  {
    this.mImpl.setShuffleModeEnabled(paramBoolean);
  }
  
  public static abstract class Callback
  {
    private CallbackHandler mCallbackHandler = null;
    final Object mCallbackObj;
    private boolean mMediaPlayPauseKeyPending;
    private WeakReference<MediaSessionCompat.MediaSessionImpl> mSessionImpl;
    
    public Callback()
    {
      if (Build.VERSION.SDK_INT >= 24) {
        this.mCallbackObj = MediaSessionCompatApi24.createCallback(new StubApi24());
      } else if (Build.VERSION.SDK_INT >= 23) {
        this.mCallbackObj = MediaSessionCompatApi23.createCallback(new StubApi23());
      } else if (Build.VERSION.SDK_INT >= 21) {
        this.mCallbackObj = MediaSessionCompatApi21.createCallback(new StubApi21());
      } else {
        this.mCallbackObj = null;
      }
    }
    
    private void handleMediaPlayPauseKeySingleTapIfPending()
    {
      if (!this.mMediaPlayPauseKeyPending) {
        return;
      }
      int i = 0;
      this.mMediaPlayPauseKeyPending = false;
      this.mCallbackHandler.removeMessages(1);
      Object localObject = (MediaSessionCompat.MediaSessionImpl)this.mSessionImpl.get();
      if (localObject == null) {
        return;
      }
      localObject = ((MediaSessionCompat.MediaSessionImpl)localObject).getPlaybackState();
      long l;
      if (localObject == null) {
        l = 0L;
      } else {
        l = ((PlaybackStateCompat)localObject).getActions();
      }
      int j;
      if ((localObject != null) && (((PlaybackStateCompat)localObject).getState() == 3)) {
        j = 1;
      } else {
        j = 0;
      }
      int k;
      if ((0x204 & l) != 0L) {
        k = 1;
      } else {
        k = 0;
      }
      if ((l & 0x202) != 0L) {
        i = 1;
      }
      if ((j != 0) && (i != 0)) {
        onPause();
      } else if ((j == 0) && (k != 0)) {
        onPlay();
      }
    }
    
    private void setSessionImpl(MediaSessionCompat.MediaSessionImpl paramMediaSessionImpl, Handler paramHandler)
    {
      this.mSessionImpl = new WeakReference(paramMediaSessionImpl);
      paramMediaSessionImpl = this.mCallbackHandler;
      if (paramMediaSessionImpl != null) {
        paramMediaSessionImpl.removeCallbacksAndMessages(null);
      }
      this.mCallbackHandler = new CallbackHandler(paramHandler.getLooper());
    }
    
    public void onAddQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat) {}
    
    public void onAddQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat, int paramInt) {}
    
    public void onCommand(String paramString, Bundle paramBundle, ResultReceiver paramResultReceiver) {}
    
    public void onCustomAction(String paramString, Bundle paramBundle) {}
    
    public void onFastForward() {}
    
    public boolean onMediaButtonEvent(Intent paramIntent)
    {
      MediaSessionCompat.MediaSessionImpl localMediaSessionImpl = (MediaSessionCompat.MediaSessionImpl)this.mSessionImpl.get();
      if ((localMediaSessionImpl != null) && (this.mCallbackHandler != null))
      {
        paramIntent = (KeyEvent)paramIntent.getParcelableExtra("android.intent.extra.KEY_EVENT");
        if ((paramIntent != null) && (paramIntent.getAction() == 0))
        {
          int i = paramIntent.getKeyCode();
          if ((i != 79) && (i != 85))
          {
            handleMediaPlayPauseKeySingleTapIfPending();
            return false;
          }
          if (paramIntent.getRepeatCount() > 0)
          {
            handleMediaPlayPauseKeySingleTapIfPending();
          }
          else if (this.mMediaPlayPauseKeyPending)
          {
            this.mCallbackHandler.removeMessages(1);
            this.mMediaPlayPauseKeyPending = false;
            paramIntent = localMediaSessionImpl.getPlaybackState();
            long l;
            if (paramIntent == null) {
              l = 0L;
            } else {
              l = paramIntent.getActions();
            }
            if ((l & 0x20) != 0L) {
              onSkipToNext();
            }
          }
          else
          {
            this.mMediaPlayPauseKeyPending = true;
            this.mCallbackHandler.sendEmptyMessageDelayed(1, ViewConfiguration.getDoubleTapTimeout());
          }
          return true;
        }
        return false;
      }
      return false;
    }
    
    public void onPause() {}
    
    public void onPlay() {}
    
    public void onPlayFromMediaId(String paramString, Bundle paramBundle) {}
    
    public void onPlayFromSearch(String paramString, Bundle paramBundle) {}
    
    public void onPlayFromUri(Uri paramUri, Bundle paramBundle) {}
    
    public void onPrepare() {}
    
    public void onPrepareFromMediaId(String paramString, Bundle paramBundle) {}
    
    public void onPrepareFromSearch(String paramString, Bundle paramBundle) {}
    
    public void onPrepareFromUri(Uri paramUri, Bundle paramBundle) {}
    
    public void onRemoveQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat) {}
    
    @Deprecated
    public void onRemoveQueueItemAt(int paramInt) {}
    
    public void onRewind() {}
    
    public void onSeekTo(long paramLong) {}
    
    public void onSetCaptioningEnabled(boolean paramBoolean) {}
    
    public void onSetRating(RatingCompat paramRatingCompat) {}
    
    public void onSetRating(RatingCompat paramRatingCompat, Bundle paramBundle) {}
    
    public void onSetRepeatMode(int paramInt) {}
    
    public void onSetShuffleMode(int paramInt) {}
    
    @Deprecated
    public void onSetShuffleModeEnabled(boolean paramBoolean) {}
    
    public void onSkipToNext() {}
    
    public void onSkipToPrevious() {}
    
    public void onSkipToQueueItem(long paramLong) {}
    
    public void onStop() {}
    
    private class CallbackHandler
      extends Handler
    {
      private static final int MSG_MEDIA_PLAY_PAUSE_KEY_DOUBLE_TAP_TIMEOUT = 1;
      
      CallbackHandler(Looper paramLooper)
      {
        super();
      }
      
      public void handleMessage(Message paramMessage)
      {
        if (paramMessage.what == 1) {
          MediaSessionCompat.Callback.this.handleMediaPlayPauseKeySingleTapIfPending();
        }
      }
    }
    
    private class StubApi21
      implements MediaSessionCompatApi21.Callback
    {
      StubApi21() {}
      
      public void onCommand(String paramString, Bundle paramBundle, ResultReceiver paramResultReceiver)
      {
        try
        {
          boolean bool = paramString.equals("android.support.v4.media.session.command.GET_EXTRA_BINDER");
          Object localObject1 = null;
          Object localObject2 = null;
          if (bool)
          {
            paramString = (MediaSessionCompat.MediaSessionImplApi21)MediaSessionCompat.Callback.this.mSessionImpl.get();
            if (paramString != null)
            {
              paramBundle = new android/os/Bundle;
              paramBundle.<init>();
              paramString = paramString.getSessionToken().getExtraBinder();
              if (paramString == null) {
                paramString = (String)localObject2;
              } else {
                paramString = paramString.asBinder();
              }
              BundleCompat.putBinder(paramBundle, "android.support.v4.media.session.EXTRA_BINDER", paramString);
              paramResultReceiver.send(0, paramBundle);
            }
          }
          else if (paramString.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM"))
          {
            paramBundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
            MediaSessionCompat.Callback.this.onAddQueueItem((MediaDescriptionCompat)paramBundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"));
          }
          else if (paramString.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT"))
          {
            paramBundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
            MediaSessionCompat.Callback.this.onAddQueueItem((MediaDescriptionCompat)paramBundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"), paramBundle.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX"));
          }
          else if (paramString.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM"))
          {
            paramBundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
            MediaSessionCompat.Callback.this.onRemoveQueueItem((MediaDescriptionCompat)paramBundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"));
          }
          else if (paramString.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT"))
          {
            paramResultReceiver = (MediaSessionCompat.MediaSessionImplApi21)MediaSessionCompat.Callback.this.mSessionImpl.get();
            if ((paramResultReceiver != null) && (MediaSessionCompat.MediaSessionImplApi21.access$200(paramResultReceiver) != null))
            {
              int i = paramBundle.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX", -1);
              paramString = (String)localObject1;
              if (i >= 0)
              {
                paramString = (String)localObject1;
                if (i < MediaSessionCompat.MediaSessionImplApi21.access$200(paramResultReceiver).size()) {
                  paramString = (MediaSessionCompat.QueueItem)MediaSessionCompat.MediaSessionImplApi21.access$200(paramResultReceiver).get(i);
                }
              }
              if (paramString != null) {
                MediaSessionCompat.Callback.this.onRemoveQueueItem(paramString.getDescription());
              }
            }
          }
          else
          {
            MediaSessionCompat.Callback.this.onCommand(paramString, paramBundle, paramResultReceiver);
          }
        }
        catch (BadParcelableException paramString)
        {
          Log.e("MediaSessionCompat", "Could not unparcel the extra data.");
        }
      }
      
      public void onCustomAction(String paramString, Bundle paramBundle)
      {
        if (paramString.equals("android.support.v4.media.session.action.PLAY_FROM_URI"))
        {
          paramString = (Uri)paramBundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_URI");
          paramBundle = (Bundle)paramBundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS");
          MediaSessionCompat.Callback.this.onPlayFromUri(paramString, paramBundle);
        }
        else if (paramString.equals("android.support.v4.media.session.action.PREPARE"))
        {
          MediaSessionCompat.Callback.this.onPrepare();
        }
        else if (paramString.equals("android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID"))
        {
          paramString = paramBundle.getString("android.support.v4.media.session.action.ARGUMENT_MEDIA_ID");
          paramBundle = paramBundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS");
          MediaSessionCompat.Callback.this.onPrepareFromMediaId(paramString, paramBundle);
        }
        else if (paramString.equals("android.support.v4.media.session.action.PREPARE_FROM_SEARCH"))
        {
          paramString = paramBundle.getString("android.support.v4.media.session.action.ARGUMENT_QUERY");
          paramBundle = paramBundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS");
          MediaSessionCompat.Callback.this.onPrepareFromSearch(paramString, paramBundle);
        }
        else if (paramString.equals("android.support.v4.media.session.action.PREPARE_FROM_URI"))
        {
          paramString = (Uri)paramBundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_URI");
          paramBundle = paramBundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS");
          MediaSessionCompat.Callback.this.onPrepareFromUri(paramString, paramBundle);
        }
        else
        {
          boolean bool;
          if (paramString.equals("android.support.v4.media.session.action.SET_CAPTIONING_ENABLED"))
          {
            bool = paramBundle.getBoolean("android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED");
            MediaSessionCompat.Callback.this.onSetCaptioningEnabled(bool);
          }
          else
          {
            int i;
            if (paramString.equals("android.support.v4.media.session.action.SET_REPEAT_MODE"))
            {
              i = paramBundle.getInt("android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE");
              MediaSessionCompat.Callback.this.onSetRepeatMode(i);
            }
            else if (paramString.equals("android.support.v4.media.session.action.SET_SHUFFLE_MODE_ENABLED"))
            {
              bool = paramBundle.getBoolean("android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE_ENABLED");
              MediaSessionCompat.Callback.this.onSetShuffleModeEnabled(bool);
            }
            else if (paramString.equals("android.support.v4.media.session.action.SET_SHUFFLE_MODE"))
            {
              i = paramBundle.getInt("android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE");
              MediaSessionCompat.Callback.this.onSetShuffleMode(i);
            }
            else if (paramString.equals("android.support.v4.media.session.action.SET_RATING"))
            {
              paramBundle.setClassLoader(RatingCompat.class.getClassLoader());
              paramString = (RatingCompat)paramBundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_RATING");
              paramBundle = paramBundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS");
              MediaSessionCompat.Callback.this.onSetRating(paramString, paramBundle);
            }
            else
            {
              MediaSessionCompat.Callback.this.onCustomAction(paramString, paramBundle);
            }
          }
        }
      }
      
      public void onFastForward()
      {
        MediaSessionCompat.Callback.this.onFastForward();
      }
      
      public boolean onMediaButtonEvent(Intent paramIntent)
      {
        return MediaSessionCompat.Callback.this.onMediaButtonEvent(paramIntent);
      }
      
      public void onPause()
      {
        MediaSessionCompat.Callback.this.onPause();
      }
      
      public void onPlay()
      {
        MediaSessionCompat.Callback.this.onPlay();
      }
      
      public void onPlayFromMediaId(String paramString, Bundle paramBundle)
      {
        MediaSessionCompat.Callback.this.onPlayFromMediaId(paramString, paramBundle);
      }
      
      public void onPlayFromSearch(String paramString, Bundle paramBundle)
      {
        MediaSessionCompat.Callback.this.onPlayFromSearch(paramString, paramBundle);
      }
      
      public void onRewind()
      {
        MediaSessionCompat.Callback.this.onRewind();
      }
      
      public void onSeekTo(long paramLong)
      {
        MediaSessionCompat.Callback.this.onSeekTo(paramLong);
      }
      
      public void onSetRating(Object paramObject)
      {
        MediaSessionCompat.Callback.this.onSetRating(RatingCompat.fromRating(paramObject));
      }
      
      public void onSetRating(Object paramObject, Bundle paramBundle)
      {
        MediaSessionCompat.Callback.this.onSetRating(RatingCompat.fromRating(paramObject), paramBundle);
      }
      
      public void onSkipToNext()
      {
        MediaSessionCompat.Callback.this.onSkipToNext();
      }
      
      public void onSkipToPrevious()
      {
        MediaSessionCompat.Callback.this.onSkipToPrevious();
      }
      
      public void onSkipToQueueItem(long paramLong)
      {
        MediaSessionCompat.Callback.this.onSkipToQueueItem(paramLong);
      }
      
      public void onStop()
      {
        MediaSessionCompat.Callback.this.onStop();
      }
    }
    
    private class StubApi23
      extends MediaSessionCompat.Callback.StubApi21
      implements MediaSessionCompatApi23.Callback
    {
      StubApi23()
      {
        super();
      }
      
      public void onPlayFromUri(Uri paramUri, Bundle paramBundle)
      {
        MediaSessionCompat.Callback.this.onPlayFromUri(paramUri, paramBundle);
      }
    }
    
    private class StubApi24
      extends MediaSessionCompat.Callback.StubApi23
      implements MediaSessionCompatApi24.Callback
    {
      StubApi24()
      {
        super();
      }
      
      public void onPrepare()
      {
        MediaSessionCompat.Callback.this.onPrepare();
      }
      
      public void onPrepareFromMediaId(String paramString, Bundle paramBundle)
      {
        MediaSessionCompat.Callback.this.onPrepareFromMediaId(paramString, paramBundle);
      }
      
      public void onPrepareFromSearch(String paramString, Bundle paramBundle)
      {
        MediaSessionCompat.Callback.this.onPrepareFromSearch(paramString, paramBundle);
      }
      
      public void onPrepareFromUri(Uri paramUri, Bundle paramBundle)
      {
        MediaSessionCompat.Callback.this.onPrepareFromUri(paramUri, paramBundle);
      }
    }
  }
  
  static abstract interface MediaSessionImpl
  {
    public abstract String getCallingPackage();
    
    public abstract Object getMediaSession();
    
    public abstract PlaybackStateCompat getPlaybackState();
    
    public abstract Object getRemoteControlClient();
    
    public abstract MediaSessionCompat.Token getSessionToken();
    
    public abstract boolean isActive();
    
    public abstract void release();
    
    public abstract void sendSessionEvent(String paramString, Bundle paramBundle);
    
    public abstract void setActive(boolean paramBoolean);
    
    public abstract void setCallback(MediaSessionCompat.Callback paramCallback, Handler paramHandler);
    
    public abstract void setCaptioningEnabled(boolean paramBoolean);
    
    public abstract void setExtras(Bundle paramBundle);
    
    public abstract void setFlags(int paramInt);
    
    public abstract void setMediaButtonReceiver(PendingIntent paramPendingIntent);
    
    public abstract void setMetadata(MediaMetadataCompat paramMediaMetadataCompat);
    
    public abstract void setPlaybackState(PlaybackStateCompat paramPlaybackStateCompat);
    
    public abstract void setPlaybackToLocal(int paramInt);
    
    public abstract void setPlaybackToRemote(VolumeProviderCompat paramVolumeProviderCompat);
    
    public abstract void setQueue(List<MediaSessionCompat.QueueItem> paramList);
    
    public abstract void setQueueTitle(CharSequence paramCharSequence);
    
    public abstract void setRatingType(int paramInt);
    
    public abstract void setRepeatMode(int paramInt);
    
    public abstract void setSessionActivity(PendingIntent paramPendingIntent);
    
    public abstract void setShuffleMode(int paramInt);
    
    public abstract void setShuffleModeEnabled(boolean paramBoolean);
  }
  
  static class MediaSessionImplApi18
    extends MediaSessionCompat.MediaSessionImplBase
  {
    private static boolean sIsMbrPendingIntentSupported = true;
    
    MediaSessionImplApi18(Context paramContext, String paramString, ComponentName paramComponentName, PendingIntent paramPendingIntent)
    {
      super(paramString, paramComponentName, paramPendingIntent);
    }
    
    int getRccTransportControlFlagsFromActions(long paramLong)
    {
      int i = super.getRccTransportControlFlagsFromActions(paramLong);
      int j = i;
      if ((paramLong & 0x100) != 0L) {
        j = i | 0x100;
      }
      return j;
    }
    
    void registerMediaButtonEventReceiver(PendingIntent paramPendingIntent, ComponentName paramComponentName)
    {
      if (sIsMbrPendingIntentSupported) {
        try
        {
          this.mAudioManager.registerMediaButtonEventReceiver(paramPendingIntent);
        }
        catch (NullPointerException localNullPointerException)
        {
          Log.w("MediaSessionCompat", "Unable to register media button event receiver with PendingIntent, falling back to ComponentName.");
          sIsMbrPendingIntentSupported = false;
        }
      }
      if (!sIsMbrPendingIntentSupported) {
        super.registerMediaButtonEventReceiver(paramPendingIntent, paramComponentName);
      }
    }
    
    public void setCallback(MediaSessionCompat.Callback paramCallback, Handler paramHandler)
    {
      super.setCallback(paramCallback, paramHandler);
      if (paramCallback == null)
      {
        this.mRcc.setPlaybackPositionUpdateListener(null);
      }
      else
      {
        paramCallback = new RemoteControlClient.OnPlaybackPositionUpdateListener()
        {
          public void onPlaybackPositionUpdate(long paramAnonymousLong)
          {
            MediaSessionCompat.MediaSessionImplApi18.this.postToHandler(18, Long.valueOf(paramAnonymousLong));
          }
        };
        this.mRcc.setPlaybackPositionUpdateListener(paramCallback);
      }
    }
    
    void setRccState(PlaybackStateCompat paramPlaybackStateCompat)
    {
      long l1 = paramPlaybackStateCompat.getPosition();
      float f = paramPlaybackStateCompat.getPlaybackSpeed();
      long l2 = paramPlaybackStateCompat.getLastPositionUpdateTime();
      long l3 = SystemClock.elapsedRealtime();
      long l4 = l1;
      if (paramPlaybackStateCompat.getState() == 3)
      {
        long l5 = 0L;
        l4 = l1;
        if (l1 > 0L)
        {
          l4 = l5;
          if (l2 > 0L)
          {
            l5 = l3 - l2;
            l4 = l5;
            if (f > 0.0F)
            {
              l4 = l5;
              if (f != 1.0F) {
                l4 = ((float)l5 * f);
              }
            }
          }
          l4 = l1 + l4;
        }
      }
      this.mRcc.setPlaybackState(getRccStateFromState(paramPlaybackStateCompat.getState()), l4, f);
    }
    
    void unregisterMediaButtonEventReceiver(PendingIntent paramPendingIntent, ComponentName paramComponentName)
    {
      if (sIsMbrPendingIntentSupported) {
        this.mAudioManager.unregisterMediaButtonEventReceiver(paramPendingIntent);
      } else {
        super.unregisterMediaButtonEventReceiver(paramPendingIntent, paramComponentName);
      }
    }
  }
  
  static class MediaSessionImplApi19
    extends MediaSessionCompat.MediaSessionImplApi18
  {
    MediaSessionImplApi19(Context paramContext, String paramString, ComponentName paramComponentName, PendingIntent paramPendingIntent)
    {
      super(paramString, paramComponentName, paramPendingIntent);
    }
    
    RemoteControlClient.MetadataEditor buildRccMetadata(Bundle paramBundle)
    {
      RemoteControlClient.MetadataEditor localMetadataEditor = super.buildRccMetadata(paramBundle);
      long l;
      if (this.mState == null) {
        l = 0L;
      } else {
        l = this.mState.getActions();
      }
      if ((l & 0x80) != 0L) {
        localMetadataEditor.addEditableKey(268435457);
      }
      if (paramBundle == null) {
        return localMetadataEditor;
      }
      if (paramBundle.containsKey("android.media.metadata.YEAR")) {
        localMetadataEditor.putLong(8, paramBundle.getLong("android.media.metadata.YEAR"));
      }
      if (paramBundle.containsKey("android.media.metadata.RATING")) {
        localMetadataEditor.putObject(101, paramBundle.getParcelable("android.media.metadata.RATING"));
      }
      if (paramBundle.containsKey("android.media.metadata.USER_RATING")) {
        localMetadataEditor.putObject(268435457, paramBundle.getParcelable("android.media.metadata.USER_RATING"));
      }
      return localMetadataEditor;
    }
    
    int getRccTransportControlFlagsFromActions(long paramLong)
    {
      int i = super.getRccTransportControlFlagsFromActions(paramLong);
      int j = i;
      if ((paramLong & 0x80) != 0L) {
        j = i | 0x200;
      }
      return j;
    }
    
    public void setCallback(MediaSessionCompat.Callback paramCallback, Handler paramHandler)
    {
      super.setCallback(paramCallback, paramHandler);
      if (paramCallback == null)
      {
        this.mRcc.setMetadataUpdateListener(null);
      }
      else
      {
        paramCallback = new RemoteControlClient.OnMetadataUpdateListener()
        {
          public void onMetadataUpdate(int paramAnonymousInt, Object paramAnonymousObject)
          {
            if ((paramAnonymousInt == 268435457) && ((paramAnonymousObject instanceof Rating))) {
              MediaSessionCompat.MediaSessionImplApi19.this.postToHandler(19, RatingCompat.fromRating(paramAnonymousObject));
            }
          }
        };
        this.mRcc.setMetadataUpdateListener(paramCallback);
      }
    }
  }
  
  static class MediaSessionImplApi21
    implements MediaSessionCompat.MediaSessionImpl
  {
    boolean mCaptioningEnabled;
    private boolean mDestroyed = false;
    private final RemoteCallbackList<IMediaControllerCallback> mExtraControllerCallbacks = new RemoteCallbackList();
    private MediaMetadataCompat mMetadata;
    private PlaybackStateCompat mPlaybackState;
    private List<MediaSessionCompat.QueueItem> mQueue;
    int mRatingType;
    int mRepeatMode;
    private final Object mSessionObj;
    int mShuffleMode;
    boolean mShuffleModeEnabled;
    private final MediaSessionCompat.Token mToken;
    
    public MediaSessionImplApi21(Context paramContext, String paramString)
    {
      this.mSessionObj = MediaSessionCompatApi21.createSession(paramContext, paramString);
      this.mToken = new MediaSessionCompat.Token(MediaSessionCompatApi21.getSessionToken(this.mSessionObj), new ExtraSession());
    }
    
    public MediaSessionImplApi21(Object paramObject)
    {
      this.mSessionObj = MediaSessionCompatApi21.verifySession(paramObject);
      this.mToken = new MediaSessionCompat.Token(MediaSessionCompatApi21.getSessionToken(this.mSessionObj), new ExtraSession());
    }
    
    public String getCallingPackage()
    {
      if (Build.VERSION.SDK_INT < 24) {
        return null;
      }
      return MediaSessionCompatApi24.getCallingPackage(this.mSessionObj);
    }
    
    public Object getMediaSession()
    {
      return this.mSessionObj;
    }
    
    public PlaybackStateCompat getPlaybackState()
    {
      return this.mPlaybackState;
    }
    
    public Object getRemoteControlClient()
    {
      return null;
    }
    
    public MediaSessionCompat.Token getSessionToken()
    {
      return this.mToken;
    }
    
    public boolean isActive()
    {
      return MediaSessionCompatApi21.isActive(this.mSessionObj);
    }
    
    public void release()
    {
      this.mDestroyed = true;
      MediaSessionCompatApi21.release(this.mSessionObj);
    }
    
    public void sendSessionEvent(String paramString, Bundle paramBundle)
    {
      int i;
      if (Build.VERSION.SDK_INT < 23) {
        i = this.mExtraControllerCallbacks.beginBroadcast() - 1;
      }
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onEvent(paramString, paramBundle);
          i--;
          continue;
          this.mExtraControllerCallbacks.finishBroadcast();
          MediaSessionCompatApi21.sendSessionEvent(this.mSessionObj, paramString, paramBundle);
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    public void setActive(boolean paramBoolean)
    {
      MediaSessionCompatApi21.setActive(this.mSessionObj, paramBoolean);
    }
    
    public void setCallback(MediaSessionCompat.Callback paramCallback, Handler paramHandler)
    {
      Object localObject1 = this.mSessionObj;
      Object localObject2;
      if (paramCallback == null) {
        localObject2 = null;
      } else {
        localObject2 = paramCallback.mCallbackObj;
      }
      MediaSessionCompatApi21.setCallback(localObject1, localObject2, paramHandler);
      if (paramCallback != null) {
        paramCallback.setSessionImpl(this, paramHandler);
      }
    }
    
    public void setCaptioningEnabled(boolean paramBoolean)
    {
      int i;
      if (this.mCaptioningEnabled != paramBoolean)
      {
        this.mCaptioningEnabled = paramBoolean;
        i = this.mExtraControllerCallbacks.beginBroadcast() - 1;
      }
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onCaptioningEnabledChanged(paramBoolean);
          i--;
          continue;
          this.mExtraControllerCallbacks.finishBroadcast();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    public void setExtras(Bundle paramBundle)
    {
      MediaSessionCompatApi21.setExtras(this.mSessionObj, paramBundle);
    }
    
    public void setFlags(int paramInt)
    {
      MediaSessionCompatApi21.setFlags(this.mSessionObj, paramInt);
    }
    
    public void setMediaButtonReceiver(PendingIntent paramPendingIntent)
    {
      MediaSessionCompatApi21.setMediaButtonReceiver(this.mSessionObj, paramPendingIntent);
    }
    
    public void setMetadata(MediaMetadataCompat paramMediaMetadataCompat)
    {
      this.mMetadata = paramMediaMetadataCompat;
      Object localObject = this.mSessionObj;
      if (paramMediaMetadataCompat == null) {
        paramMediaMetadataCompat = null;
      } else {
        paramMediaMetadataCompat = paramMediaMetadataCompat.getMediaMetadata();
      }
      MediaSessionCompatApi21.setMetadata(localObject, paramMediaMetadataCompat);
    }
    
    public void setPlaybackState(PlaybackStateCompat paramPlaybackStateCompat)
    {
      this.mPlaybackState = paramPlaybackStateCompat;
      int i = this.mExtraControllerCallbacks.beginBroadcast() - 1;
      for (;;)
      {
        Object localObject;
        if (i >= 0) {
          localObject = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          ((IMediaControllerCallback)localObject).onPlaybackStateChanged(paramPlaybackStateCompat);
          i--;
          continue;
          this.mExtraControllerCallbacks.finishBroadcast();
          localObject = this.mSessionObj;
          if (paramPlaybackStateCompat == null) {
            paramPlaybackStateCompat = null;
          } else {
            paramPlaybackStateCompat = paramPlaybackStateCompat.getPlaybackState();
          }
          MediaSessionCompatApi21.setPlaybackState(localObject, paramPlaybackStateCompat);
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    public void setPlaybackToLocal(int paramInt)
    {
      MediaSessionCompatApi21.setPlaybackToLocal(this.mSessionObj, paramInt);
    }
    
    public void setPlaybackToRemote(VolumeProviderCompat paramVolumeProviderCompat)
    {
      MediaSessionCompatApi21.setPlaybackToRemote(this.mSessionObj, paramVolumeProviderCompat.getVolumeProvider());
    }
    
    public void setQueue(List<MediaSessionCompat.QueueItem> paramList)
    {
      this.mQueue = paramList;
      if (paramList != null)
      {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = paramList.iterator();
        for (;;)
        {
          paramList = localArrayList;
          if (!localIterator.hasNext()) {
            break;
          }
          localArrayList.add(((MediaSessionCompat.QueueItem)localIterator.next()).getQueueItem());
        }
      }
      paramList = null;
      MediaSessionCompatApi21.setQueue(this.mSessionObj, paramList);
    }
    
    public void setQueueTitle(CharSequence paramCharSequence)
    {
      MediaSessionCompatApi21.setQueueTitle(this.mSessionObj, paramCharSequence);
    }
    
    public void setRatingType(int paramInt)
    {
      if (Build.VERSION.SDK_INT < 22) {
        this.mRatingType = paramInt;
      } else {
        MediaSessionCompatApi22.setRatingType(this.mSessionObj, paramInt);
      }
    }
    
    public void setRepeatMode(int paramInt)
    {
      int i;
      if (this.mRepeatMode != paramInt)
      {
        this.mRepeatMode = paramInt;
        i = this.mExtraControllerCallbacks.beginBroadcast() - 1;
      }
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onRepeatModeChanged(paramInt);
          i--;
          continue;
          this.mExtraControllerCallbacks.finishBroadcast();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    public void setSessionActivity(PendingIntent paramPendingIntent)
    {
      MediaSessionCompatApi21.setSessionActivity(this.mSessionObj, paramPendingIntent);
    }
    
    public void setShuffleMode(int paramInt)
    {
      int i;
      if (this.mShuffleMode != paramInt)
      {
        this.mShuffleMode = paramInt;
        i = this.mExtraControllerCallbacks.beginBroadcast() - 1;
      }
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onShuffleModeChanged(paramInt);
          i--;
          continue;
          this.mExtraControllerCallbacks.finishBroadcast();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    public void setShuffleModeEnabled(boolean paramBoolean)
    {
      int i;
      if (this.mShuffleModeEnabled != paramBoolean)
      {
        this.mShuffleModeEnabled = paramBoolean;
        i = this.mExtraControllerCallbacks.beginBroadcast() - 1;
      }
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onShuffleModeChangedDeprecated(paramBoolean);
          i--;
          continue;
          this.mExtraControllerCallbacks.finishBroadcast();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    class ExtraSession
      extends IMediaSession.Stub
    {
      ExtraSession() {}
      
      public void addQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat)
      {
        throw new AssertionError();
      }
      
      public void addQueueItemAt(MediaDescriptionCompat paramMediaDescriptionCompat, int paramInt)
      {
        throw new AssertionError();
      }
      
      public void adjustVolume(int paramInt1, int paramInt2, String paramString)
      {
        throw new AssertionError();
      }
      
      public void fastForward()
      {
        throw new AssertionError();
      }
      
      public Bundle getExtras()
      {
        throw new AssertionError();
      }
      
      public long getFlags()
      {
        throw new AssertionError();
      }
      
      public PendingIntent getLaunchPendingIntent()
      {
        throw new AssertionError();
      }
      
      public MediaMetadataCompat getMetadata()
      {
        throw new AssertionError();
      }
      
      public String getPackageName()
      {
        throw new AssertionError();
      }
      
      public PlaybackStateCompat getPlaybackState()
      {
        return MediaSessionCompat.getStateWithUpdatedPosition(MediaSessionCompat.MediaSessionImplApi21.this.mPlaybackState, MediaSessionCompat.MediaSessionImplApi21.this.mMetadata);
      }
      
      public List<MediaSessionCompat.QueueItem> getQueue()
      {
        return null;
      }
      
      public CharSequence getQueueTitle()
      {
        throw new AssertionError();
      }
      
      public int getRatingType()
      {
        return MediaSessionCompat.MediaSessionImplApi21.this.mRatingType;
      }
      
      public int getRepeatMode()
      {
        return MediaSessionCompat.MediaSessionImplApi21.this.mRepeatMode;
      }
      
      public int getShuffleMode()
      {
        return MediaSessionCompat.MediaSessionImplApi21.this.mShuffleMode;
      }
      
      public String getTag()
      {
        throw new AssertionError();
      }
      
      public ParcelableVolumeInfo getVolumeAttributes()
      {
        throw new AssertionError();
      }
      
      public boolean isCaptioningEnabled()
      {
        return MediaSessionCompat.MediaSessionImplApi21.this.mCaptioningEnabled;
      }
      
      public boolean isShuffleModeEnabledDeprecated()
      {
        return MediaSessionCompat.MediaSessionImplApi21.this.mShuffleModeEnabled;
      }
      
      public boolean isTransportControlEnabled()
      {
        throw new AssertionError();
      }
      
      public void next()
      {
        throw new AssertionError();
      }
      
      public void pause()
      {
        throw new AssertionError();
      }
      
      public void play()
      {
        throw new AssertionError();
      }
      
      public void playFromMediaId(String paramString, Bundle paramBundle)
      {
        throw new AssertionError();
      }
      
      public void playFromSearch(String paramString, Bundle paramBundle)
      {
        throw new AssertionError();
      }
      
      public void playFromUri(Uri paramUri, Bundle paramBundle)
      {
        throw new AssertionError();
      }
      
      public void prepare()
      {
        throw new AssertionError();
      }
      
      public void prepareFromMediaId(String paramString, Bundle paramBundle)
      {
        throw new AssertionError();
      }
      
      public void prepareFromSearch(String paramString, Bundle paramBundle)
      {
        throw new AssertionError();
      }
      
      public void prepareFromUri(Uri paramUri, Bundle paramBundle)
      {
        throw new AssertionError();
      }
      
      public void previous()
      {
        throw new AssertionError();
      }
      
      public void rate(RatingCompat paramRatingCompat)
      {
        throw new AssertionError();
      }
      
      public void rateWithExtras(RatingCompat paramRatingCompat, Bundle paramBundle)
      {
        throw new AssertionError();
      }
      
      public void registerCallbackListener(IMediaControllerCallback paramIMediaControllerCallback)
      {
        if (!MediaSessionCompat.MediaSessionImplApi21.this.mDestroyed) {
          MediaSessionCompat.MediaSessionImplApi21.this.mExtraControllerCallbacks.register(paramIMediaControllerCallback);
        }
      }
      
      public void removeQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat)
      {
        throw new AssertionError();
      }
      
      public void removeQueueItemAt(int paramInt)
      {
        throw new AssertionError();
      }
      
      public void rewind()
      {
        throw new AssertionError();
      }
      
      public void seekTo(long paramLong)
      {
        throw new AssertionError();
      }
      
      public void sendCommand(String paramString, Bundle paramBundle, MediaSessionCompat.ResultReceiverWrapper paramResultReceiverWrapper)
      {
        throw new AssertionError();
      }
      
      public void sendCustomAction(String paramString, Bundle paramBundle)
      {
        throw new AssertionError();
      }
      
      public boolean sendMediaButton(KeyEvent paramKeyEvent)
      {
        throw new AssertionError();
      }
      
      public void setCaptioningEnabled(boolean paramBoolean)
      {
        throw new AssertionError();
      }
      
      public void setRepeatMode(int paramInt)
      {
        throw new AssertionError();
      }
      
      public void setShuffleMode(int paramInt)
      {
        throw new AssertionError();
      }
      
      public void setShuffleModeEnabledDeprecated(boolean paramBoolean)
      {
        throw new AssertionError();
      }
      
      public void setVolumeTo(int paramInt1, int paramInt2, String paramString)
      {
        throw new AssertionError();
      }
      
      public void skipToQueueItem(long paramLong)
      {
        throw new AssertionError();
      }
      
      public void stop()
      {
        throw new AssertionError();
      }
      
      public void unregisterCallbackListener(IMediaControllerCallback paramIMediaControllerCallback)
      {
        MediaSessionCompat.MediaSessionImplApi21.this.mExtraControllerCallbacks.unregister(paramIMediaControllerCallback);
      }
    }
  }
  
  static class MediaSessionImplBase
    implements MediaSessionCompat.MediaSessionImpl
  {
    static final int RCC_PLAYSTATE_NONE = 0;
    final AudioManager mAudioManager;
    volatile MediaSessionCompat.Callback mCallback;
    boolean mCaptioningEnabled;
    private final Context mContext;
    final RemoteCallbackList<IMediaControllerCallback> mControllerCallbacks = new RemoteCallbackList();
    boolean mDestroyed = false;
    Bundle mExtras;
    int mFlags;
    private MessageHandler mHandler;
    boolean mIsActive = false;
    private boolean mIsMbrRegistered = false;
    private boolean mIsRccRegistered = false;
    int mLocalStream;
    final Object mLock = new Object();
    private final ComponentName mMediaButtonReceiverComponentName;
    private final PendingIntent mMediaButtonReceiverIntent;
    MediaMetadataCompat mMetadata;
    final String mPackageName;
    List<MediaSessionCompat.QueueItem> mQueue;
    CharSequence mQueueTitle;
    int mRatingType;
    final RemoteControlClient mRcc;
    int mRepeatMode;
    PendingIntent mSessionActivity;
    int mShuffleMode;
    boolean mShuffleModeEnabled;
    PlaybackStateCompat mState;
    private final MediaSessionStub mStub;
    final String mTag;
    private final MediaSessionCompat.Token mToken;
    private VolumeProviderCompat.Callback mVolumeCallback = new VolumeProviderCompat.Callback()
    {
      public void onVolumeChanged(VolumeProviderCompat paramAnonymousVolumeProviderCompat)
      {
        if (MediaSessionCompat.MediaSessionImplBase.this.mVolumeProvider != paramAnonymousVolumeProviderCompat) {
          return;
        }
        paramAnonymousVolumeProviderCompat = new ParcelableVolumeInfo(MediaSessionCompat.MediaSessionImplBase.this.mVolumeType, MediaSessionCompat.MediaSessionImplBase.this.mLocalStream, paramAnonymousVolumeProviderCompat.getVolumeControl(), paramAnonymousVolumeProviderCompat.getMaxVolume(), paramAnonymousVolumeProviderCompat.getCurrentVolume());
        MediaSessionCompat.MediaSessionImplBase.this.sendVolumeInfoChanged(paramAnonymousVolumeProviderCompat);
      }
    };
    VolumeProviderCompat mVolumeProvider;
    int mVolumeType;
    
    public MediaSessionImplBase(Context paramContext, String paramString, ComponentName paramComponentName, PendingIntent paramPendingIntent)
    {
      if (paramComponentName != null)
      {
        this.mContext = paramContext;
        this.mPackageName = paramContext.getPackageName();
        this.mAudioManager = ((AudioManager)paramContext.getSystemService("audio"));
        this.mTag = paramString;
        this.mMediaButtonReceiverComponentName = paramComponentName;
        this.mMediaButtonReceiverIntent = paramPendingIntent;
        this.mStub = new MediaSessionStub();
        this.mToken = new MediaSessionCompat.Token(this.mStub);
        this.mRatingType = 0;
        this.mVolumeType = 1;
        this.mLocalStream = 3;
        this.mRcc = new RemoteControlClient(paramPendingIntent);
        return;
      }
      throw new IllegalArgumentException("MediaButtonReceiver component may not be null.");
    }
    
    private void sendCaptioningEnabled(boolean paramBoolean)
    {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onCaptioningEnabledChanged(paramBoolean);
          i--;
          continue;
          this.mControllerCallbacks.finishBroadcast();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    private void sendEvent(String paramString, Bundle paramBundle)
    {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onEvent(paramString, paramBundle);
          i--;
          continue;
          this.mControllerCallbacks.finishBroadcast();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    private void sendExtras(Bundle paramBundle)
    {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onExtrasChanged(paramBundle);
          i--;
          continue;
          this.mControllerCallbacks.finishBroadcast();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    private void sendMetadata(MediaMetadataCompat paramMediaMetadataCompat)
    {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onMetadataChanged(paramMediaMetadataCompat);
          i--;
          continue;
          this.mControllerCallbacks.finishBroadcast();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    private void sendQueue(List<MediaSessionCompat.QueueItem> paramList)
    {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onQueueChanged(paramList);
          i--;
          continue;
          this.mControllerCallbacks.finishBroadcast();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    private void sendQueueTitle(CharSequence paramCharSequence)
    {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onQueueTitleChanged(paramCharSequence);
          i--;
          continue;
          this.mControllerCallbacks.finishBroadcast();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    private void sendRepeatMode(int paramInt)
    {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onRepeatModeChanged(paramInt);
          i--;
          continue;
          this.mControllerCallbacks.finishBroadcast();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    private void sendSessionDestroyed()
    {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onSessionDestroyed();
          i--;
          continue;
          this.mControllerCallbacks.finishBroadcast();
          this.mControllerCallbacks.kill();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    private void sendShuffleMode(int paramInt)
    {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onShuffleModeChanged(paramInt);
          i--;
          continue;
          this.mControllerCallbacks.finishBroadcast();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    private void sendShuffleModeEnabled(boolean paramBoolean)
    {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onShuffleModeChangedDeprecated(paramBoolean);
          i--;
          continue;
          this.mControllerCallbacks.finishBroadcast();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    private void sendState(PlaybackStateCompat paramPlaybackStateCompat)
    {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onPlaybackStateChanged(paramPlaybackStateCompat);
          i--;
          continue;
          this.mControllerCallbacks.finishBroadcast();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    void adjustVolume(int paramInt1, int paramInt2)
    {
      if (this.mVolumeType == 2)
      {
        VolumeProviderCompat localVolumeProviderCompat = this.mVolumeProvider;
        if (localVolumeProviderCompat != null) {
          localVolumeProviderCompat.onAdjustVolume(paramInt1);
        }
      }
      else
      {
        this.mAudioManager.adjustStreamVolume(this.mLocalStream, paramInt1, paramInt2);
      }
    }
    
    RemoteControlClient.MetadataEditor buildRccMetadata(Bundle paramBundle)
    {
      RemoteControlClient.MetadataEditor localMetadataEditor = this.mRcc.editMetadata(true);
      if (paramBundle == null) {
        return localMetadataEditor;
      }
      Bitmap localBitmap1;
      Bitmap localBitmap2;
      if (paramBundle.containsKey("android.media.metadata.ART"))
      {
        localBitmap1 = (Bitmap)paramBundle.getParcelable("android.media.metadata.ART");
        localBitmap2 = localBitmap1;
        if (localBitmap1 != null) {
          localBitmap2 = localBitmap1.copy(localBitmap1.getConfig(), false);
        }
        localMetadataEditor.putBitmap(100, localBitmap2);
      }
      else if (paramBundle.containsKey("android.media.metadata.ALBUM_ART"))
      {
        localBitmap1 = (Bitmap)paramBundle.getParcelable("android.media.metadata.ALBUM_ART");
        localBitmap2 = localBitmap1;
        if (localBitmap1 != null) {
          localBitmap2 = localBitmap1.copy(localBitmap1.getConfig(), false);
        }
        localMetadataEditor.putBitmap(100, localBitmap2);
      }
      if (paramBundle.containsKey("android.media.metadata.ALBUM")) {
        localMetadataEditor.putString(1, paramBundle.getString("android.media.metadata.ALBUM"));
      }
      if (paramBundle.containsKey("android.media.metadata.ALBUM_ARTIST")) {
        localMetadataEditor.putString(13, paramBundle.getString("android.media.metadata.ALBUM_ARTIST"));
      }
      if (paramBundle.containsKey("android.media.metadata.ARTIST")) {
        localMetadataEditor.putString(2, paramBundle.getString("android.media.metadata.ARTIST"));
      }
      if (paramBundle.containsKey("android.media.metadata.AUTHOR")) {
        localMetadataEditor.putString(3, paramBundle.getString("android.media.metadata.AUTHOR"));
      }
      if (paramBundle.containsKey("android.media.metadata.COMPILATION")) {
        localMetadataEditor.putString(15, paramBundle.getString("android.media.metadata.COMPILATION"));
      }
      if (paramBundle.containsKey("android.media.metadata.COMPOSER")) {
        localMetadataEditor.putString(4, paramBundle.getString("android.media.metadata.COMPOSER"));
      }
      if (paramBundle.containsKey("android.media.metadata.DATE")) {
        localMetadataEditor.putString(5, paramBundle.getString("android.media.metadata.DATE"));
      }
      if (paramBundle.containsKey("android.media.metadata.DISC_NUMBER")) {
        localMetadataEditor.putLong(14, paramBundle.getLong("android.media.metadata.DISC_NUMBER"));
      }
      if (paramBundle.containsKey("android.media.metadata.DURATION")) {
        localMetadataEditor.putLong(9, paramBundle.getLong("android.media.metadata.DURATION"));
      }
      if (paramBundle.containsKey("android.media.metadata.GENRE")) {
        localMetadataEditor.putString(6, paramBundle.getString("android.media.metadata.GENRE"));
      }
      if (paramBundle.containsKey("android.media.metadata.TITLE")) {
        localMetadataEditor.putString(7, paramBundle.getString("android.media.metadata.TITLE"));
      }
      if (paramBundle.containsKey("android.media.metadata.TRACK_NUMBER")) {
        localMetadataEditor.putLong(0, paramBundle.getLong("android.media.metadata.TRACK_NUMBER"));
      }
      if (paramBundle.containsKey("android.media.metadata.WRITER")) {
        localMetadataEditor.putString(11, paramBundle.getString("android.media.metadata.WRITER"));
      }
      return localMetadataEditor;
    }
    
    public String getCallingPackage()
    {
      return null;
    }
    
    public Object getMediaSession()
    {
      return null;
    }
    
    public PlaybackStateCompat getPlaybackState()
    {
      synchronized (this.mLock)
      {
        PlaybackStateCompat localPlaybackStateCompat = this.mState;
        return localPlaybackStateCompat;
      }
    }
    
    int getRccStateFromState(int paramInt)
    {
      switch (paramInt)
      {
      default: 
        return -1;
      case 10: 
      case 11: 
        return 6;
      case 9: 
        return 7;
      case 7: 
        return 9;
      case 6: 
      case 8: 
        return 8;
      case 5: 
        return 5;
      case 4: 
        return 4;
      case 3: 
        return 3;
      case 2: 
        return 2;
      case 1: 
        return 1;
      }
      return 0;
    }
    
    int getRccTransportControlFlagsFromActions(long paramLong)
    {
      if ((1L & paramLong) != 0L) {
        i = 32;
      } else {
        i = 0;
      }
      int j = i;
      if ((0x2 & paramLong) != 0L) {
        j = i | 0x10;
      }
      int i = j;
      if ((0x4 & paramLong) != 0L) {
        i = j | 0x4;
      }
      j = i;
      if ((0x8 & paramLong) != 0L) {
        j = i | 0x2;
      }
      i = j;
      if ((0x10 & paramLong) != 0L) {
        i = j | 0x1;
      }
      j = i;
      if ((0x20 & paramLong) != 0L) {
        j = i | 0x80;
      }
      i = j;
      if ((0x40 & paramLong) != 0L) {
        i = j | 0x40;
      }
      j = i;
      if ((paramLong & 0x200) != 0L) {
        j = i | 0x8;
      }
      return j;
    }
    
    public Object getRemoteControlClient()
    {
      return null;
    }
    
    public MediaSessionCompat.Token getSessionToken()
    {
      return this.mToken;
    }
    
    public boolean isActive()
    {
      return this.mIsActive;
    }
    
    void postToHandler(int paramInt)
    {
      postToHandler(paramInt, null);
    }
    
    void postToHandler(int paramInt1, int paramInt2)
    {
      postToHandler(paramInt1, null, paramInt2);
    }
    
    void postToHandler(int paramInt, Object paramObject)
    {
      postToHandler(paramInt, paramObject, null);
    }
    
    void postToHandler(int paramInt1, Object paramObject, int paramInt2)
    {
      synchronized (this.mLock)
      {
        if (this.mHandler != null) {
          this.mHandler.post(paramInt1, paramObject, paramInt2);
        }
        return;
      }
    }
    
    void postToHandler(int paramInt, Object paramObject, Bundle paramBundle)
    {
      synchronized (this.mLock)
      {
        if (this.mHandler != null) {
          this.mHandler.post(paramInt, paramObject, paramBundle);
        }
        return;
      }
    }
    
    void registerMediaButtonEventReceiver(PendingIntent paramPendingIntent, ComponentName paramComponentName)
    {
      this.mAudioManager.registerMediaButtonEventReceiver(paramComponentName);
    }
    
    public void release()
    {
      this.mIsActive = false;
      this.mDestroyed = true;
      update();
      sendSessionDestroyed();
    }
    
    public void sendSessionEvent(String paramString, Bundle paramBundle)
    {
      sendEvent(paramString, paramBundle);
    }
    
    void sendVolumeInfoChanged(ParcelableVolumeInfo paramParcelableVolumeInfo)
    {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      for (;;)
      {
        IMediaControllerCallback localIMediaControllerCallback;
        if (i >= 0) {
          localIMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
        }
        try
        {
          localIMediaControllerCallback.onVolumeInfoChanged(paramParcelableVolumeInfo);
          i--;
          continue;
          this.mControllerCallbacks.finishBroadcast();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          for (;;) {}
        }
      }
    }
    
    public void setActive(boolean paramBoolean)
    {
      if (paramBoolean == this.mIsActive) {
        return;
      }
      this.mIsActive = paramBoolean;
      if (update())
      {
        setMetadata(this.mMetadata);
        setPlaybackState(this.mState);
      }
    }
    
    public void setCallback(MediaSessionCompat.Callback paramCallback, Handler arg2)
    {
      this.mCallback = paramCallback;
      if (paramCallback != null)
      {
        paramCallback = ???;
        if (??? == null) {
          paramCallback = new Handler();
        }
        synchronized (this.mLock)
        {
          if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages(null);
          }
          MessageHandler localMessageHandler = new android/support/v4/media/session/MediaSessionCompat$MediaSessionImplBase$MessageHandler;
          localMessageHandler.<init>(this, paramCallback.getLooper());
          this.mHandler = localMessageHandler;
          this.mCallback.setSessionImpl(this, paramCallback);
        }
      }
    }
    
    public void setCaptioningEnabled(boolean paramBoolean)
    {
      if (this.mCaptioningEnabled != paramBoolean)
      {
        this.mCaptioningEnabled = paramBoolean;
        sendCaptioningEnabled(paramBoolean);
      }
    }
    
    public void setExtras(Bundle paramBundle)
    {
      this.mExtras = paramBundle;
      sendExtras(paramBundle);
    }
    
    public void setFlags(int paramInt)
    {
      synchronized (this.mLock)
      {
        this.mFlags = paramInt;
        update();
        return;
      }
    }
    
    public void setMediaButtonReceiver(PendingIntent paramPendingIntent) {}
    
    public void setMetadata(MediaMetadataCompat arg1)
    {
      MediaMetadataCompat localMediaMetadataCompat = ???;
      if (??? != null) {
        localMediaMetadataCompat = new MediaMetadataCompat.Builder(???, MediaSessionCompat.sMaxBitmapSize).build();
      }
      synchronized (this.mLock)
      {
        this.mMetadata = localMediaMetadataCompat;
        sendMetadata(localMediaMetadataCompat);
        if (!this.mIsActive) {
          return;
        }
        if (localMediaMetadataCompat == null) {
          ??? = null;
        } else {
          ??? = localMediaMetadataCompat.getBundle();
        }
        buildRccMetadata(???).apply();
        return;
      }
    }
    
    public void setPlaybackState(PlaybackStateCompat paramPlaybackStateCompat)
    {
      synchronized (this.mLock)
      {
        this.mState = paramPlaybackStateCompat;
        sendState(paramPlaybackStateCompat);
        if (!this.mIsActive) {
          return;
        }
        if (paramPlaybackStateCompat == null)
        {
          this.mRcc.setPlaybackState(0);
          this.mRcc.setTransportControlFlags(0);
        }
        else
        {
          setRccState(paramPlaybackStateCompat);
          this.mRcc.setTransportControlFlags(getRccTransportControlFlagsFromActions(paramPlaybackStateCompat.getActions()));
        }
        return;
      }
    }
    
    public void setPlaybackToLocal(int paramInt)
    {
      VolumeProviderCompat localVolumeProviderCompat = this.mVolumeProvider;
      if (localVolumeProviderCompat != null) {
        localVolumeProviderCompat.setCallback(null);
      }
      this.mVolumeType = 1;
      paramInt = this.mVolumeType;
      int i = this.mLocalStream;
      sendVolumeInfoChanged(new ParcelableVolumeInfo(paramInt, i, 2, this.mAudioManager.getStreamMaxVolume(i), this.mAudioManager.getStreamVolume(this.mLocalStream)));
    }
    
    public void setPlaybackToRemote(VolumeProviderCompat paramVolumeProviderCompat)
    {
      if (paramVolumeProviderCompat != null)
      {
        VolumeProviderCompat localVolumeProviderCompat = this.mVolumeProvider;
        if (localVolumeProviderCompat != null) {
          localVolumeProviderCompat.setCallback(null);
        }
        this.mVolumeType = 2;
        this.mVolumeProvider = paramVolumeProviderCompat;
        sendVolumeInfoChanged(new ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, this.mVolumeProvider.getVolumeControl(), this.mVolumeProvider.getMaxVolume(), this.mVolumeProvider.getCurrentVolume()));
        paramVolumeProviderCompat.setCallback(this.mVolumeCallback);
        return;
      }
      throw new IllegalArgumentException("volumeProvider may not be null");
    }
    
    public void setQueue(List<MediaSessionCompat.QueueItem> paramList)
    {
      this.mQueue = paramList;
      sendQueue(paramList);
    }
    
    public void setQueueTitle(CharSequence paramCharSequence)
    {
      this.mQueueTitle = paramCharSequence;
      sendQueueTitle(paramCharSequence);
    }
    
    public void setRatingType(int paramInt)
    {
      this.mRatingType = paramInt;
    }
    
    void setRccState(PlaybackStateCompat paramPlaybackStateCompat)
    {
      this.mRcc.setPlaybackState(getRccStateFromState(paramPlaybackStateCompat.getState()));
    }
    
    public void setRepeatMode(int paramInt)
    {
      if (this.mRepeatMode != paramInt)
      {
        this.mRepeatMode = paramInt;
        sendRepeatMode(paramInt);
      }
    }
    
    public void setSessionActivity(PendingIntent paramPendingIntent)
    {
      synchronized (this.mLock)
      {
        this.mSessionActivity = paramPendingIntent;
        return;
      }
    }
    
    public void setShuffleMode(int paramInt)
    {
      if (this.mShuffleMode != paramInt)
      {
        this.mShuffleMode = paramInt;
        sendShuffleMode(paramInt);
      }
    }
    
    public void setShuffleModeEnabled(boolean paramBoolean)
    {
      if (this.mShuffleModeEnabled != paramBoolean)
      {
        this.mShuffleModeEnabled = paramBoolean;
        sendShuffleModeEnabled(paramBoolean);
      }
    }
    
    void setVolumeTo(int paramInt1, int paramInt2)
    {
      if (this.mVolumeType == 2)
      {
        VolumeProviderCompat localVolumeProviderCompat = this.mVolumeProvider;
        if (localVolumeProviderCompat != null) {
          localVolumeProviderCompat.onSetVolumeTo(paramInt1);
        }
      }
      else
      {
        this.mAudioManager.setStreamVolume(this.mLocalStream, paramInt1, paramInt2);
      }
    }
    
    void unregisterMediaButtonEventReceiver(PendingIntent paramPendingIntent, ComponentName paramComponentName)
    {
      this.mAudioManager.unregisterMediaButtonEventReceiver(paramComponentName);
    }
    
    boolean update()
    {
      boolean bool1 = this.mIsActive;
      boolean bool2 = true;
      if (bool1)
      {
        if ((!this.mIsMbrRegistered) && ((this.mFlags & 0x1) != 0))
        {
          registerMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
          this.mIsMbrRegistered = true;
        }
        else if ((this.mIsMbrRegistered) && ((this.mFlags & 0x1) == 0))
        {
          unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
          this.mIsMbrRegistered = false;
        }
        if ((!this.mIsRccRegistered) && ((this.mFlags & 0x2) != 0))
        {
          this.mAudioManager.registerRemoteControlClient(this.mRcc);
          this.mIsRccRegistered = true;
          return bool2;
        }
        if ((this.mIsRccRegistered) && ((this.mFlags & 0x2) == 0))
        {
          this.mRcc.setPlaybackState(0);
          this.mAudioManager.unregisterRemoteControlClient(this.mRcc);
          this.mIsRccRegistered = false;
        }
      }
      else
      {
        if (this.mIsMbrRegistered)
        {
          unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
          this.mIsMbrRegistered = false;
        }
        if (this.mIsRccRegistered)
        {
          this.mRcc.setPlaybackState(0);
          this.mAudioManager.unregisterRemoteControlClient(this.mRcc);
          this.mIsRccRegistered = false;
        }
      }
      bool2 = false;
      return bool2;
    }
    
    private static final class Command
    {
      public final String command;
      public final Bundle extras;
      public final ResultReceiver stub;
      
      public Command(String paramString, Bundle paramBundle, ResultReceiver paramResultReceiver)
      {
        this.command = paramString;
        this.extras = paramBundle;
        this.stub = paramResultReceiver;
      }
    }
    
    class MediaSessionStub
      extends IMediaSession.Stub
    {
      MediaSessionStub() {}
      
      public void addQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(25, paramMediaDescriptionCompat);
      }
      
      public void addQueueItemAt(MediaDescriptionCompat paramMediaDescriptionCompat, int paramInt)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(26, paramMediaDescriptionCompat, paramInt);
      }
      
      public void adjustVolume(int paramInt1, int paramInt2, String paramString)
      {
        MediaSessionCompat.MediaSessionImplBase.this.adjustVolume(paramInt1, paramInt2);
      }
      
      public void fastForward()
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(16);
      }
      
      public Bundle getExtras()
      {
        synchronized (MediaSessionCompat.MediaSessionImplBase.this.mLock)
        {
          Bundle localBundle = MediaSessionCompat.MediaSessionImplBase.this.mExtras;
          return localBundle;
        }
      }
      
      public long getFlags()
      {
        synchronized (MediaSessionCompat.MediaSessionImplBase.this.mLock)
        {
          long l = MediaSessionCompat.MediaSessionImplBase.this.mFlags;
          return l;
        }
      }
      
      public PendingIntent getLaunchPendingIntent()
      {
        synchronized (MediaSessionCompat.MediaSessionImplBase.this.mLock)
        {
          PendingIntent localPendingIntent = MediaSessionCompat.MediaSessionImplBase.this.mSessionActivity;
          return localPendingIntent;
        }
      }
      
      public MediaMetadataCompat getMetadata()
      {
        return MediaSessionCompat.MediaSessionImplBase.this.mMetadata;
      }
      
      public String getPackageName()
      {
        return MediaSessionCompat.MediaSessionImplBase.this.mPackageName;
      }
      
      public PlaybackStateCompat getPlaybackState()
      {
        synchronized (MediaSessionCompat.MediaSessionImplBase.this.mLock)
        {
          PlaybackStateCompat localPlaybackStateCompat = MediaSessionCompat.MediaSessionImplBase.this.mState;
          MediaMetadataCompat localMediaMetadataCompat = MediaSessionCompat.MediaSessionImplBase.this.mMetadata;
          return MediaSessionCompat.getStateWithUpdatedPosition(localPlaybackStateCompat, localMediaMetadataCompat);
        }
      }
      
      public List<MediaSessionCompat.QueueItem> getQueue()
      {
        synchronized (MediaSessionCompat.MediaSessionImplBase.this.mLock)
        {
          List localList = MediaSessionCompat.MediaSessionImplBase.this.mQueue;
          return localList;
        }
      }
      
      public CharSequence getQueueTitle()
      {
        return MediaSessionCompat.MediaSessionImplBase.this.mQueueTitle;
      }
      
      public int getRatingType()
      {
        return MediaSessionCompat.MediaSessionImplBase.this.mRatingType;
      }
      
      public int getRepeatMode()
      {
        return MediaSessionCompat.MediaSessionImplBase.this.mRepeatMode;
      }
      
      public int getShuffleMode()
      {
        return MediaSessionCompat.MediaSessionImplBase.this.mShuffleMode;
      }
      
      public String getTag()
      {
        return MediaSessionCompat.MediaSessionImplBase.this.mTag;
      }
      
      public ParcelableVolumeInfo getVolumeAttributes()
      {
        synchronized (MediaSessionCompat.MediaSessionImplBase.this.mLock)
        {
          int i = MediaSessionCompat.MediaSessionImplBase.this.mVolumeType;
          int j = MediaSessionCompat.MediaSessionImplBase.this.mLocalStream;
          VolumeProviderCompat localVolumeProviderCompat = MediaSessionCompat.MediaSessionImplBase.this.mVolumeProvider;
          int k;
          int m;
          int n;
          if (i == 2)
          {
            k = localVolumeProviderCompat.getVolumeControl();
            m = localVolumeProviderCompat.getMaxVolume();
            n = localVolumeProviderCompat.getCurrentVolume();
          }
          else
          {
            k = MediaSessionCompat.MediaSessionImplBase.this.mAudioManager.getStreamMaxVolume(j);
            n = MediaSessionCompat.MediaSessionImplBase.this.mAudioManager.getStreamVolume(j);
            int i1 = 2;
            m = k;
            k = i1;
          }
          return new ParcelableVolumeInfo(i, j, k, m, n);
        }
      }
      
      public boolean isCaptioningEnabled()
      {
        return MediaSessionCompat.MediaSessionImplBase.this.mCaptioningEnabled;
      }
      
      public boolean isShuffleModeEnabledDeprecated()
      {
        return MediaSessionCompat.MediaSessionImplBase.this.mShuffleModeEnabled;
      }
      
      public boolean isTransportControlEnabled()
      {
        boolean bool;
        if ((MediaSessionCompat.MediaSessionImplBase.this.mFlags & 0x2) != 0) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public void next()
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(14);
      }
      
      public void pause()
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(12);
      }
      
      public void play()
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(7);
      }
      
      public void playFromMediaId(String paramString, Bundle paramBundle)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(8, paramString, paramBundle);
      }
      
      public void playFromSearch(String paramString, Bundle paramBundle)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(9, paramString, paramBundle);
      }
      
      public void playFromUri(Uri paramUri, Bundle paramBundle)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(10, paramUri, paramBundle);
      }
      
      public void prepare()
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(3);
      }
      
      public void prepareFromMediaId(String paramString, Bundle paramBundle)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(4, paramString, paramBundle);
      }
      
      public void prepareFromSearch(String paramString, Bundle paramBundle)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(5, paramString, paramBundle);
      }
      
      public void prepareFromUri(Uri paramUri, Bundle paramBundle)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(6, paramUri, paramBundle);
      }
      
      public void previous()
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(15);
      }
      
      public void rate(RatingCompat paramRatingCompat)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(19, paramRatingCompat);
      }
      
      public void rateWithExtras(RatingCompat paramRatingCompat, Bundle paramBundle)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(31, paramRatingCompat, paramBundle);
      }
      
      public void registerCallbackListener(IMediaControllerCallback paramIMediaControllerCallback)
      {
        if (MediaSessionCompat.MediaSessionImplBase.this.mDestroyed) {}
        try
        {
          paramIMediaControllerCallback.onSessionDestroyed();
          return;
          MediaSessionCompat.MediaSessionImplBase.this.mControllerCallbacks.register(paramIMediaControllerCallback);
          return;
        }
        catch (Exception paramIMediaControllerCallback)
        {
          for (;;) {}
        }
      }
      
      public void removeQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(27, paramMediaDescriptionCompat);
      }
      
      public void removeQueueItemAt(int paramInt)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(28, paramInt);
      }
      
      public void rewind()
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(17);
      }
      
      public void seekTo(long paramLong)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(18, Long.valueOf(paramLong));
      }
      
      public void sendCommand(String paramString, Bundle paramBundle, MediaSessionCompat.ResultReceiverWrapper paramResultReceiverWrapper)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(1, new MediaSessionCompat.MediaSessionImplBase.Command(paramString, paramBundle, MediaSessionCompat.ResultReceiverWrapper.access$400(paramResultReceiverWrapper)));
      }
      
      public void sendCustomAction(String paramString, Bundle paramBundle)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(20, paramString, paramBundle);
      }
      
      public boolean sendMediaButton(KeyEvent paramKeyEvent)
      {
        int i = MediaSessionCompat.MediaSessionImplBase.this.mFlags;
        boolean bool = true;
        if ((i & 0x1) == 0) {
          bool = false;
        }
        if (bool) {
          MediaSessionCompat.MediaSessionImplBase.this.postToHandler(21, paramKeyEvent);
        }
        return bool;
      }
      
      public void setCaptioningEnabled(boolean paramBoolean)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(29, Boolean.valueOf(paramBoolean));
      }
      
      public void setRepeatMode(int paramInt)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(23, paramInt);
      }
      
      public void setShuffleMode(int paramInt)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(30, paramInt);
      }
      
      public void setShuffleModeEnabledDeprecated(boolean paramBoolean)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(24, Boolean.valueOf(paramBoolean));
      }
      
      public void setVolumeTo(int paramInt1, int paramInt2, String paramString)
      {
        MediaSessionCompat.MediaSessionImplBase.this.setVolumeTo(paramInt1, paramInt2);
      }
      
      public void skipToQueueItem(long paramLong)
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(11, Long.valueOf(paramLong));
      }
      
      public void stop()
      {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(13);
      }
      
      public void unregisterCallbackListener(IMediaControllerCallback paramIMediaControllerCallback)
      {
        MediaSessionCompat.MediaSessionImplBase.this.mControllerCallbacks.unregister(paramIMediaControllerCallback);
      }
    }
    
    class MessageHandler
      extends Handler
    {
      private static final int KEYCODE_MEDIA_PAUSE = 127;
      private static final int KEYCODE_MEDIA_PLAY = 126;
      private static final int MSG_ADD_QUEUE_ITEM = 25;
      private static final int MSG_ADD_QUEUE_ITEM_AT = 26;
      private static final int MSG_ADJUST_VOLUME = 2;
      private static final int MSG_COMMAND = 1;
      private static final int MSG_CUSTOM_ACTION = 20;
      private static final int MSG_FAST_FORWARD = 16;
      private static final int MSG_MEDIA_BUTTON = 21;
      private static final int MSG_NEXT = 14;
      private static final int MSG_PAUSE = 12;
      private static final int MSG_PLAY = 7;
      private static final int MSG_PLAY_MEDIA_ID = 8;
      private static final int MSG_PLAY_SEARCH = 9;
      private static final int MSG_PLAY_URI = 10;
      private static final int MSG_PREPARE = 3;
      private static final int MSG_PREPARE_MEDIA_ID = 4;
      private static final int MSG_PREPARE_SEARCH = 5;
      private static final int MSG_PREPARE_URI = 6;
      private static final int MSG_PREVIOUS = 15;
      private static final int MSG_RATE = 19;
      private static final int MSG_RATE_EXTRA = 31;
      private static final int MSG_REMOVE_QUEUE_ITEM = 27;
      private static final int MSG_REMOVE_QUEUE_ITEM_AT = 28;
      private static final int MSG_REWIND = 17;
      private static final int MSG_SEEK_TO = 18;
      private static final int MSG_SET_CAPTIONING_ENABLED = 29;
      private static final int MSG_SET_REPEAT_MODE = 23;
      private static final int MSG_SET_SHUFFLE_MODE = 30;
      private static final int MSG_SET_SHUFFLE_MODE_ENABLED = 24;
      private static final int MSG_SET_VOLUME = 22;
      private static final int MSG_SKIP_TO_ITEM = 11;
      private static final int MSG_STOP = 13;
      
      public MessageHandler(Looper paramLooper)
      {
        super();
      }
      
      private void onMediaButtonEvent(KeyEvent paramKeyEvent, MediaSessionCompat.Callback paramCallback)
      {
        if ((paramKeyEvent != null) && (paramKeyEvent.getAction() == 0))
        {
          long l;
          if (MediaSessionCompat.MediaSessionImplBase.this.mState == null) {
            l = 0L;
          } else {
            l = MediaSessionCompat.MediaSessionImplBase.this.mState.getActions();
          }
          int i = paramKeyEvent.getKeyCode();
          if (i != 79) {
            switch (i)
            {
            default: 
              switch (i)
              {
              default: 
                break;
              case 127: 
                if ((l & 0x2) == 0L) {
                  break;
                }
                paramCallback.onPause();
                break;
              case 126: 
                if ((l & 0x4) == 0L) {
                  break;
                }
                paramCallback.onPlay();
              }
              break;
            case 90: 
              if ((l & 0x40) == 0L) {
                break;
              }
              paramCallback.onFastForward();
              break;
            case 89: 
              if ((l & 0x8) == 0L) {
                break;
              }
              paramCallback.onRewind();
              break;
            case 88: 
              if ((l & 0x10) == 0L) {
                break;
              }
              paramCallback.onSkipToPrevious();
              break;
            case 87: 
              if ((l & 0x20) == 0L) {
                break;
              }
              paramCallback.onSkipToNext();
              break;
            case 86: 
              if ((l & 1L) == 0L) {
                break;
              }
              paramCallback.onStop();
              break;
            }
          } else {
            Log.w("MediaSessionCompat", "KEYCODE_MEDIA_PLAY_PAUSE and KEYCODE_HEADSETHOOK are handled already");
          }
          return;
        }
      }
      
      public void handleMessage(Message paramMessage)
      {
        MediaSessionCompat.Callback localCallback = MediaSessionCompat.MediaSessionImplBase.this.mCallback;
        if (localCallback == null) {
          return;
        }
        switch (paramMessage.what)
        {
        default: 
          break;
        case 31: 
          localCallback.onSetRating((RatingCompat)paramMessage.obj, paramMessage.getData());
          break;
        case 30: 
          localCallback.onSetShuffleMode(paramMessage.arg1);
          break;
        case 29: 
          localCallback.onSetCaptioningEnabled(((Boolean)paramMessage.obj).booleanValue());
          break;
        case 28: 
          if (MediaSessionCompat.MediaSessionImplBase.this.mQueue != null)
          {
            if ((paramMessage.arg1 >= 0) && (paramMessage.arg1 < MediaSessionCompat.MediaSessionImplBase.this.mQueue.size())) {
              paramMessage = (MediaSessionCompat.QueueItem)MediaSessionCompat.MediaSessionImplBase.this.mQueue.get(paramMessage.arg1);
            } else {
              paramMessage = null;
            }
            if (paramMessage != null) {
              localCallback.onRemoveQueueItem(paramMessage.getDescription());
            }
          }
          break;
        case 27: 
          localCallback.onRemoveQueueItem((MediaDescriptionCompat)paramMessage.obj);
          break;
        case 26: 
          localCallback.onAddQueueItem((MediaDescriptionCompat)paramMessage.obj, paramMessage.arg1);
          break;
        case 25: 
          localCallback.onAddQueueItem((MediaDescriptionCompat)paramMessage.obj);
          break;
        case 24: 
          localCallback.onSetShuffleModeEnabled(((Boolean)paramMessage.obj).booleanValue());
          break;
        case 23: 
          localCallback.onSetRepeatMode(paramMessage.arg1);
          break;
        case 22: 
          MediaSessionCompat.MediaSessionImplBase.this.setVolumeTo(paramMessage.arg1, 0);
          break;
        case 21: 
          KeyEvent localKeyEvent = (KeyEvent)paramMessage.obj;
          paramMessage = new Intent("android.intent.action.MEDIA_BUTTON");
          paramMessage.putExtra("android.intent.extra.KEY_EVENT", localKeyEvent);
          if (!localCallback.onMediaButtonEvent(paramMessage)) {
            onMediaButtonEvent(localKeyEvent, localCallback);
          }
          break;
        case 20: 
          localCallback.onCustomAction((String)paramMessage.obj, paramMessage.getData());
          break;
        case 19: 
          localCallback.onSetRating((RatingCompat)paramMessage.obj);
          break;
        case 18: 
          localCallback.onSeekTo(((Long)paramMessage.obj).longValue());
          break;
        case 17: 
          localCallback.onRewind();
          break;
        case 16: 
          localCallback.onFastForward();
          break;
        case 15: 
          localCallback.onSkipToPrevious();
          break;
        case 14: 
          localCallback.onSkipToNext();
          break;
        case 13: 
          localCallback.onStop();
          break;
        case 12: 
          localCallback.onPause();
          break;
        case 11: 
          localCallback.onSkipToQueueItem(((Long)paramMessage.obj).longValue());
          break;
        case 10: 
          localCallback.onPlayFromUri((Uri)paramMessage.obj, paramMessage.getData());
          break;
        case 9: 
          localCallback.onPlayFromSearch((String)paramMessage.obj, paramMessage.getData());
          break;
        case 8: 
          localCallback.onPlayFromMediaId((String)paramMessage.obj, paramMessage.getData());
          break;
        case 7: 
          localCallback.onPlay();
          break;
        case 6: 
          localCallback.onPrepareFromUri((Uri)paramMessage.obj, paramMessage.getData());
          break;
        case 5: 
          localCallback.onPrepareFromSearch((String)paramMessage.obj, paramMessage.getData());
          break;
        case 4: 
          localCallback.onPrepareFromMediaId((String)paramMessage.obj, paramMessage.getData());
          break;
        case 3: 
          localCallback.onPrepare();
          break;
        case 2: 
          MediaSessionCompat.MediaSessionImplBase.this.adjustVolume(paramMessage.arg1, 0);
          break;
        case 1: 
          paramMessage = (MediaSessionCompat.MediaSessionImplBase.Command)paramMessage.obj;
          localCallback.onCommand(paramMessage.command, paramMessage.extras, paramMessage.stub);
        }
      }
      
      public void post(int paramInt)
      {
        post(paramInt, null);
      }
      
      public void post(int paramInt, Object paramObject)
      {
        obtainMessage(paramInt, paramObject).sendToTarget();
      }
      
      public void post(int paramInt1, Object paramObject, int paramInt2)
      {
        obtainMessage(paramInt1, paramInt2, 0, paramObject).sendToTarget();
      }
      
      public void post(int paramInt, Object paramObject, Bundle paramBundle)
      {
        paramObject = obtainMessage(paramInt, paramObject);
        ((Message)paramObject).setData(paramBundle);
        ((Message)paramObject).sendToTarget();
      }
    }
  }
  
  public static abstract interface OnActiveChangeListener
  {
    public abstract void onActiveChanged();
  }
  
  public static final class QueueItem
    implements Parcelable
  {
    public static final Parcelable.Creator<QueueItem> CREATOR = new Parcelable.Creator()
    {
      public MediaSessionCompat.QueueItem createFromParcel(Parcel paramAnonymousParcel)
      {
        return new MediaSessionCompat.QueueItem(paramAnonymousParcel);
      }
      
      public MediaSessionCompat.QueueItem[] newArray(int paramAnonymousInt)
      {
        return new MediaSessionCompat.QueueItem[paramAnonymousInt];
      }
    };
    public static final int UNKNOWN_ID = -1;
    private final MediaDescriptionCompat mDescription;
    private final long mId;
    private Object mItem;
    
    QueueItem(Parcel paramParcel)
    {
      this.mDescription = ((MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(paramParcel));
      this.mId = paramParcel.readLong();
    }
    
    public QueueItem(MediaDescriptionCompat paramMediaDescriptionCompat, long paramLong)
    {
      this(null, paramMediaDescriptionCompat, paramLong);
    }
    
    private QueueItem(Object paramObject, MediaDescriptionCompat paramMediaDescriptionCompat, long paramLong)
    {
      if (paramMediaDescriptionCompat != null)
      {
        if (paramLong != -1L)
        {
          this.mDescription = paramMediaDescriptionCompat;
          this.mId = paramLong;
          this.mItem = paramObject;
          return;
        }
        throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
      }
      throw new IllegalArgumentException("Description cannot be null.");
    }
    
    public static QueueItem fromQueueItem(Object paramObject)
    {
      if ((paramObject != null) && (Build.VERSION.SDK_INT >= 21)) {
        return new QueueItem(paramObject, MediaDescriptionCompat.fromMediaDescription(MediaSessionCompatApi21.QueueItem.getDescription(paramObject)), MediaSessionCompatApi21.QueueItem.getQueueId(paramObject));
      }
      return null;
    }
    
    public static List<QueueItem> fromQueueItemList(List<?> paramList)
    {
      if ((paramList != null) && (Build.VERSION.SDK_INT >= 21))
      {
        ArrayList localArrayList = new ArrayList();
        paramList = paramList.iterator();
        while (paramList.hasNext()) {
          localArrayList.add(fromQueueItem(paramList.next()));
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
    
    public long getQueueId()
    {
      return this.mId;
    }
    
    public Object getQueueItem()
    {
      if ((this.mItem == null) && (Build.VERSION.SDK_INT >= 21))
      {
        this.mItem = MediaSessionCompatApi21.QueueItem.createItem(this.mDescription.getMediaDescription(), this.mId);
        return this.mItem;
      }
      return this.mItem;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("MediaSession.QueueItem {Description=");
      localStringBuilder.append(this.mDescription);
      localStringBuilder.append(", Id=");
      localStringBuilder.append(this.mId);
      localStringBuilder.append(" }");
      return localStringBuilder.toString();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      this.mDescription.writeToParcel(paramParcel, paramInt);
      paramParcel.writeLong(this.mId);
    }
  }
  
  static final class ResultReceiverWrapper
    implements Parcelable
  {
    public static final Parcelable.Creator<ResultReceiverWrapper> CREATOR = new Parcelable.Creator()
    {
      public MediaSessionCompat.ResultReceiverWrapper createFromParcel(Parcel paramAnonymousParcel)
      {
        return new MediaSessionCompat.ResultReceiverWrapper(paramAnonymousParcel);
      }
      
      public MediaSessionCompat.ResultReceiverWrapper[] newArray(int paramAnonymousInt)
      {
        return new MediaSessionCompat.ResultReceiverWrapper[paramAnonymousInt];
      }
    };
    private ResultReceiver mResultReceiver;
    
    ResultReceiverWrapper(Parcel paramParcel)
    {
      this.mResultReceiver = ((ResultReceiver)ResultReceiver.CREATOR.createFromParcel(paramParcel));
    }
    
    public ResultReceiverWrapper(ResultReceiver paramResultReceiver)
    {
      this.mResultReceiver = paramResultReceiver;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      this.mResultReceiver.writeToParcel(paramParcel, paramInt);
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface SessionFlags {}
  
  public static final class Token
    implements Parcelable
  {
    public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator()
    {
      public MediaSessionCompat.Token createFromParcel(Parcel paramAnonymousParcel)
      {
        if (Build.VERSION.SDK_INT >= 21) {
          paramAnonymousParcel = paramAnonymousParcel.readParcelable(null);
        } else {
          paramAnonymousParcel = paramAnonymousParcel.readStrongBinder();
        }
        return new MediaSessionCompat.Token(paramAnonymousParcel);
      }
      
      public MediaSessionCompat.Token[] newArray(int paramAnonymousInt)
      {
        return new MediaSessionCompat.Token[paramAnonymousInt];
      }
    };
    private final IMediaSession mExtraBinder;
    private final Object mInner;
    
    Token(Object paramObject)
    {
      this(paramObject, null);
    }
    
    Token(Object paramObject, IMediaSession paramIMediaSession)
    {
      this.mInner = paramObject;
      this.mExtraBinder = paramIMediaSession;
    }
    
    public static Token fromToken(Object paramObject)
    {
      return fromToken(paramObject, null);
    }
    
    public static Token fromToken(Object paramObject, IMediaSession paramIMediaSession)
    {
      if ((paramObject != null) && (Build.VERSION.SDK_INT >= 21)) {
        return new Token(MediaSessionCompatApi21.verifyToken(paramObject), paramIMediaSession);
      }
      return null;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool = true;
      if (this == paramObject) {
        return true;
      }
      if (!(paramObject instanceof Token)) {
        return false;
      }
      Object localObject = (Token)paramObject;
      paramObject = this.mInner;
      if (paramObject == null)
      {
        if (((Token)localObject).mInner != null) {
          bool = false;
        }
        return bool;
      }
      localObject = ((Token)localObject).mInner;
      if (localObject == null) {
        return false;
      }
      return paramObject.equals(localObject);
    }
    
    public IMediaSession getExtraBinder()
    {
      return this.mExtraBinder;
    }
    
    public Object getToken()
    {
      return this.mInner;
    }
    
    public int hashCode()
    {
      Object localObject = this.mInner;
      if (localObject == null) {
        return 0;
      }
      return localObject.hashCode();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      if (Build.VERSION.SDK_INT >= 21) {
        paramParcel.writeParcelable((Parcelable)this.mInner, paramInt);
      } else {
        paramParcel.writeStrongBinder((IBinder)this.mInner);
      }
    }
  }
}


/* Location:              ~/android/support/v4/media/session/MediaSessionCompat.class
 *
 * Reversed by:           J
 */