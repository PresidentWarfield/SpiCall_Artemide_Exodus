package android.support.v4.media.session;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public abstract interface IMediaSession
  extends IInterface
{
  public abstract void addQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat);
  
  public abstract void addQueueItemAt(MediaDescriptionCompat paramMediaDescriptionCompat, int paramInt);
  
  public abstract void adjustVolume(int paramInt1, int paramInt2, String paramString);
  
  public abstract void fastForward();
  
  public abstract Bundle getExtras();
  
  public abstract long getFlags();
  
  public abstract PendingIntent getLaunchPendingIntent();
  
  public abstract MediaMetadataCompat getMetadata();
  
  public abstract String getPackageName();
  
  public abstract PlaybackStateCompat getPlaybackState();
  
  public abstract List<MediaSessionCompat.QueueItem> getQueue();
  
  public abstract CharSequence getQueueTitle();
  
  public abstract int getRatingType();
  
  public abstract int getRepeatMode();
  
  public abstract int getShuffleMode();
  
  public abstract String getTag();
  
  public abstract ParcelableVolumeInfo getVolumeAttributes();
  
  public abstract boolean isCaptioningEnabled();
  
  public abstract boolean isShuffleModeEnabledDeprecated();
  
  public abstract boolean isTransportControlEnabled();
  
  public abstract void next();
  
  public abstract void pause();
  
  public abstract void play();
  
  public abstract void playFromMediaId(String paramString, Bundle paramBundle);
  
  public abstract void playFromSearch(String paramString, Bundle paramBundle);
  
  public abstract void playFromUri(Uri paramUri, Bundle paramBundle);
  
  public abstract void prepare();
  
  public abstract void prepareFromMediaId(String paramString, Bundle paramBundle);
  
  public abstract void prepareFromSearch(String paramString, Bundle paramBundle);
  
  public abstract void prepareFromUri(Uri paramUri, Bundle paramBundle);
  
  public abstract void previous();
  
  public abstract void rate(RatingCompat paramRatingCompat);
  
  public abstract void rateWithExtras(RatingCompat paramRatingCompat, Bundle paramBundle);
  
  public abstract void registerCallbackListener(IMediaControllerCallback paramIMediaControllerCallback);
  
  public abstract void removeQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat);
  
  public abstract void removeQueueItemAt(int paramInt);
  
  public abstract void rewind();
  
  public abstract void seekTo(long paramLong);
  
  public abstract void sendCommand(String paramString, Bundle paramBundle, MediaSessionCompat.ResultReceiverWrapper paramResultReceiverWrapper);
  
  public abstract void sendCustomAction(String paramString, Bundle paramBundle);
  
  public abstract boolean sendMediaButton(KeyEvent paramKeyEvent);
  
  public abstract void setCaptioningEnabled(boolean paramBoolean);
  
  public abstract void setRepeatMode(int paramInt);
  
  public abstract void setShuffleMode(int paramInt);
  
  public abstract void setShuffleModeEnabledDeprecated(boolean paramBoolean);
  
  public abstract void setVolumeTo(int paramInt1, int paramInt2, String paramString);
  
  public abstract void skipToQueueItem(long paramLong);
  
  public abstract void stop();
  
  public abstract void unregisterCallbackListener(IMediaControllerCallback paramIMediaControllerCallback);
  
  public static abstract class Stub
    extends Binder
    implements IMediaSession
  {
    private static final String DESCRIPTOR = "android.support.v4.media.session.IMediaSession";
    static final int TRANSACTION_addQueueItem = 41;
    static final int TRANSACTION_addQueueItemAt = 42;
    static final int TRANSACTION_adjustVolume = 11;
    static final int TRANSACTION_fastForward = 22;
    static final int TRANSACTION_getExtras = 31;
    static final int TRANSACTION_getFlags = 9;
    static final int TRANSACTION_getLaunchPendingIntent = 8;
    static final int TRANSACTION_getMetadata = 27;
    static final int TRANSACTION_getPackageName = 6;
    static final int TRANSACTION_getPlaybackState = 28;
    static final int TRANSACTION_getQueue = 29;
    static final int TRANSACTION_getQueueTitle = 30;
    static final int TRANSACTION_getRatingType = 32;
    static final int TRANSACTION_getRepeatMode = 37;
    static final int TRANSACTION_getShuffleMode = 47;
    static final int TRANSACTION_getTag = 7;
    static final int TRANSACTION_getVolumeAttributes = 10;
    static final int TRANSACTION_isCaptioningEnabled = 45;
    static final int TRANSACTION_isShuffleModeEnabledDeprecated = 38;
    static final int TRANSACTION_isTransportControlEnabled = 5;
    static final int TRANSACTION_next = 20;
    static final int TRANSACTION_pause = 18;
    static final int TRANSACTION_play = 13;
    static final int TRANSACTION_playFromMediaId = 14;
    static final int TRANSACTION_playFromSearch = 15;
    static final int TRANSACTION_playFromUri = 16;
    static final int TRANSACTION_prepare = 33;
    static final int TRANSACTION_prepareFromMediaId = 34;
    static final int TRANSACTION_prepareFromSearch = 35;
    static final int TRANSACTION_prepareFromUri = 36;
    static final int TRANSACTION_previous = 21;
    static final int TRANSACTION_rate = 25;
    static final int TRANSACTION_rateWithExtras = 51;
    static final int TRANSACTION_registerCallbackListener = 3;
    static final int TRANSACTION_removeQueueItem = 43;
    static final int TRANSACTION_removeQueueItemAt = 44;
    static final int TRANSACTION_rewind = 23;
    static final int TRANSACTION_seekTo = 24;
    static final int TRANSACTION_sendCommand = 1;
    static final int TRANSACTION_sendCustomAction = 26;
    static final int TRANSACTION_sendMediaButton = 2;
    static final int TRANSACTION_setCaptioningEnabled = 46;
    static final int TRANSACTION_setRepeatMode = 39;
    static final int TRANSACTION_setShuffleMode = 48;
    static final int TRANSACTION_setShuffleModeEnabledDeprecated = 40;
    static final int TRANSACTION_setVolumeTo = 12;
    static final int TRANSACTION_skipToQueueItem = 17;
    static final int TRANSACTION_stop = 19;
    static final int TRANSACTION_unregisterCallbackListener = 4;
    
    public Stub()
    {
      attachInterface(this, "android.support.v4.media.session.IMediaSession");
    }
    
    public static IMediaSession asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("android.support.v4.media.session.IMediaSession");
      if ((localIInterface != null) && ((localIInterface instanceof IMediaSession))) {
        return (IMediaSession)localIInterface;
      }
      return new Proxy(paramIBinder);
    }
    
    public IBinder asBinder()
    {
      return this;
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
    {
      Object localObject1 = null;
      Object localObject2 = null;
      String str = null;
      Object localObject3 = null;
      Object localObject4 = null;
      Object localObject5 = null;
      Object localObject6 = null;
      Object localObject7 = null;
      Object localObject8 = null;
      Object localObject9 = null;
      Object localObject10 = null;
      Object localObject11 = null;
      Object localObject12 = null;
      Object localObject13 = null;
      if (paramInt1 != 51)
      {
        if (paramInt1 != 1598968902)
        {
          boolean bool1 = false;
          boolean bool2 = false;
          switch (paramInt1)
          {
          default: 
            return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
          case 48: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            setShuffleMode(paramParcel1.readInt());
            paramParcel2.writeNoException();
            return true;
          case 47: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            paramInt1 = getShuffleMode();
            paramParcel2.writeNoException();
            paramParcel2.writeInt(paramInt1);
            return true;
          case 46: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            if (paramParcel1.readInt() != 0) {
              bool2 = true;
            }
            setCaptioningEnabled(bool2);
            paramParcel2.writeNoException();
            return true;
          case 45: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            paramInt1 = isCaptioningEnabled();
            paramParcel2.writeNoException();
            paramParcel2.writeInt(paramInt1);
            return true;
          case 44: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            removeQueueItemAt(paramParcel1.readInt());
            paramParcel2.writeNoException();
            return true;
          case 43: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            localObject3 = localObject13;
            if (paramParcel1.readInt() != 0) {
              localObject3 = (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(paramParcel1);
            }
            removeQueueItem((MediaDescriptionCompat)localObject3);
            paramParcel2.writeNoException();
            return true;
          case 42: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            localObject3 = localObject1;
            if (paramParcel1.readInt() != 0) {
              localObject3 = (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(paramParcel1);
            }
            addQueueItemAt((MediaDescriptionCompat)localObject3, paramParcel1.readInt());
            paramParcel2.writeNoException();
            return true;
          case 41: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            localObject3 = localObject2;
            if (paramParcel1.readInt() != 0) {
              localObject3 = (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(paramParcel1);
            }
            addQueueItem((MediaDescriptionCompat)localObject3);
            paramParcel2.writeNoException();
            return true;
          case 40: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            bool2 = bool1;
            if (paramParcel1.readInt() != 0) {
              bool2 = true;
            }
            setShuffleModeEnabledDeprecated(bool2);
            paramParcel2.writeNoException();
            return true;
          case 39: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            setRepeatMode(paramParcel1.readInt());
            paramParcel2.writeNoException();
            return true;
          case 38: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            paramInt1 = isShuffleModeEnabledDeprecated();
            paramParcel2.writeNoException();
            paramParcel2.writeInt(paramInt1);
            return true;
          case 37: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            paramInt1 = getRepeatMode();
            paramParcel2.writeNoException();
            paramParcel2.writeInt(paramInt1);
            return true;
          case 36: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            if (paramParcel1.readInt() != 0) {
              localObject3 = (Uri)Uri.CREATOR.createFromParcel(paramParcel1);
            } else {
              localObject3 = null;
            }
            localObject11 = str;
            if (paramParcel1.readInt() != 0) {
              localObject11 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
            }
            prepareFromUri((Uri)localObject3, (Bundle)localObject11);
            paramParcel2.writeNoException();
            return true;
          case 35: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            localObject11 = paramParcel1.readString();
            if (paramParcel1.readInt() != 0) {
              localObject3 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
            }
            prepareFromSearch((String)localObject11, (Bundle)localObject3);
            paramParcel2.writeNoException();
            return true;
          case 34: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            localObject11 = paramParcel1.readString();
            localObject3 = localObject4;
            if (paramParcel1.readInt() != 0) {
              localObject3 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
            }
            prepareFromMediaId((String)localObject11, (Bundle)localObject3);
            paramParcel2.writeNoException();
            return true;
          case 33: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            prepare();
            paramParcel2.writeNoException();
            return true;
          case 32: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            paramInt1 = getRatingType();
            paramParcel2.writeNoException();
            paramParcel2.writeInt(paramInt1);
            return true;
          case 31: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            paramParcel1 = getExtras();
            paramParcel2.writeNoException();
            if (paramParcel1 != null)
            {
              paramParcel2.writeInt(1);
              paramParcel1.writeToParcel(paramParcel2, 1);
            }
            else
            {
              paramParcel2.writeInt(0);
            }
            return true;
          case 30: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            paramParcel1 = getQueueTitle();
            paramParcel2.writeNoException();
            if (paramParcel1 != null)
            {
              paramParcel2.writeInt(1);
              TextUtils.writeToParcel(paramParcel1, paramParcel2, 1);
            }
            else
            {
              paramParcel2.writeInt(0);
            }
            return true;
          case 29: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            paramParcel1 = getQueue();
            paramParcel2.writeNoException();
            paramParcel2.writeTypedList(paramParcel1);
            return true;
          case 28: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            paramParcel1 = getPlaybackState();
            paramParcel2.writeNoException();
            if (paramParcel1 != null)
            {
              paramParcel2.writeInt(1);
              paramParcel1.writeToParcel(paramParcel2, 1);
            }
            else
            {
              paramParcel2.writeInt(0);
            }
            return true;
          case 27: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            paramParcel1 = getMetadata();
            paramParcel2.writeNoException();
            if (paramParcel1 != null)
            {
              paramParcel2.writeInt(1);
              paramParcel1.writeToParcel(paramParcel2, 1);
            }
            else
            {
              paramParcel2.writeInt(0);
            }
            return true;
          case 26: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            localObject11 = paramParcel1.readString();
            localObject3 = localObject5;
            if (paramParcel1.readInt() != 0) {
              localObject3 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
            }
            sendCustomAction((String)localObject11, (Bundle)localObject3);
            paramParcel2.writeNoException();
            return true;
          case 25: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            localObject3 = localObject6;
            if (paramParcel1.readInt() != 0) {
              localObject3 = (RatingCompat)RatingCompat.CREATOR.createFromParcel(paramParcel1);
            }
            rate((RatingCompat)localObject3);
            paramParcel2.writeNoException();
            return true;
          case 24: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            seekTo(paramParcel1.readLong());
            paramParcel2.writeNoException();
            return true;
          case 23: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            rewind();
            paramParcel2.writeNoException();
            return true;
          case 22: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            fastForward();
            paramParcel2.writeNoException();
            return true;
          case 21: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            previous();
            paramParcel2.writeNoException();
            return true;
          case 20: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            next();
            paramParcel2.writeNoException();
            return true;
          case 19: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            stop();
            paramParcel2.writeNoException();
            return true;
          case 18: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            pause();
            paramParcel2.writeNoException();
            return true;
          case 17: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            skipToQueueItem(paramParcel1.readLong());
            paramParcel2.writeNoException();
            return true;
          case 16: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            if (paramParcel1.readInt() != 0) {
              localObject3 = (Uri)Uri.CREATOR.createFromParcel(paramParcel1);
            } else {
              localObject3 = null;
            }
            localObject11 = localObject7;
            if (paramParcel1.readInt() != 0) {
              localObject11 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
            }
            playFromUri((Uri)localObject3, (Bundle)localObject11);
            paramParcel2.writeNoException();
            return true;
          case 15: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            localObject11 = paramParcel1.readString();
            localObject3 = localObject8;
            if (paramParcel1.readInt() != 0) {
              localObject3 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
            }
            playFromSearch((String)localObject11, (Bundle)localObject3);
            paramParcel2.writeNoException();
            return true;
          case 14: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            localObject11 = paramParcel1.readString();
            localObject3 = localObject9;
            if (paramParcel1.readInt() != 0) {
              localObject3 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
            }
            playFromMediaId((String)localObject11, (Bundle)localObject3);
            paramParcel2.writeNoException();
            return true;
          case 13: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            play();
            paramParcel2.writeNoException();
            return true;
          case 12: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            setVolumeTo(paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readString());
            paramParcel2.writeNoException();
            return true;
          case 11: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            adjustVolume(paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readString());
            paramParcel2.writeNoException();
            return true;
          case 10: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            paramParcel1 = getVolumeAttributes();
            paramParcel2.writeNoException();
            if (paramParcel1 != null)
            {
              paramParcel2.writeInt(1);
              paramParcel1.writeToParcel(paramParcel2, 1);
            }
            else
            {
              paramParcel2.writeInt(0);
            }
            return true;
          case 9: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            long l = getFlags();
            paramParcel2.writeNoException();
            paramParcel2.writeLong(l);
            return true;
          case 8: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            paramParcel1 = getLaunchPendingIntent();
            paramParcel2.writeNoException();
            if (paramParcel1 != null)
            {
              paramParcel2.writeInt(1);
              paramParcel1.writeToParcel(paramParcel2, 1);
            }
            else
            {
              paramParcel2.writeInt(0);
            }
            return true;
          case 7: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            paramParcel1 = getTag();
            paramParcel2.writeNoException();
            paramParcel2.writeString(paramParcel1);
            return true;
          case 6: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            paramParcel1 = getPackageName();
            paramParcel2.writeNoException();
            paramParcel2.writeString(paramParcel1);
            return true;
          case 5: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            paramInt1 = isTransportControlEnabled();
            paramParcel2.writeNoException();
            paramParcel2.writeInt(paramInt1);
            return true;
          case 4: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            unregisterCallbackListener(IMediaControllerCallback.Stub.asInterface(paramParcel1.readStrongBinder()));
            paramParcel2.writeNoException();
            return true;
          case 3: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            registerCallbackListener(IMediaControllerCallback.Stub.asInterface(paramParcel1.readStrongBinder()));
            paramParcel2.writeNoException();
            return true;
          case 2: 
            paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
            localObject3 = localObject10;
            if (paramParcel1.readInt() != 0) {
              localObject3 = (KeyEvent)KeyEvent.CREATOR.createFromParcel(paramParcel1);
            }
            paramInt1 = sendMediaButton((KeyEvent)localObject3);
            paramParcel2.writeNoException();
            paramParcel2.writeInt(paramInt1);
            return true;
          }
          paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
          str = paramParcel1.readString();
          if (paramParcel1.readInt() != 0) {
            localObject3 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
          } else {
            localObject3 = null;
          }
          if (paramParcel1.readInt() != 0) {
            localObject11 = (MediaSessionCompat.ResultReceiverWrapper)MediaSessionCompat.ResultReceiverWrapper.CREATOR.createFromParcel(paramParcel1);
          }
          sendCommand(str, (Bundle)localObject3, (MediaSessionCompat.ResultReceiverWrapper)localObject11);
          paramParcel2.writeNoException();
          return true;
        }
        paramParcel2.writeString("android.support.v4.media.session.IMediaSession");
        return true;
      }
      paramParcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
      if (paramParcel1.readInt() != 0) {
        localObject3 = (RatingCompat)RatingCompat.CREATOR.createFromParcel(paramParcel1);
      } else {
        localObject3 = null;
      }
      localObject11 = localObject12;
      if (paramParcel1.readInt() != 0) {
        localObject11 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
      }
      rateWithExtras((RatingCompat)localObject3, (Bundle)localObject11);
      paramParcel2.writeNoException();
      return true;
    }
    
    private static class Proxy
      implements IMediaSession
    {
      private IBinder mRemote;
      
      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }
      
      public void addQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (paramMediaDescriptionCompat != null)
          {
            localParcel1.writeInt(1);
            paramMediaDescriptionCompat.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.mRemote.transact(41, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void addQueueItemAt(MediaDescriptionCompat paramMediaDescriptionCompat, int paramInt)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (paramMediaDescriptionCompat != null)
          {
            localParcel1.writeInt(1);
            paramMediaDescriptionCompat.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(42, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void adjustVolume(int paramInt1, int paramInt2, String paramString)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          localParcel1.writeString(paramString);
          this.mRemote.transact(11, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public IBinder asBinder()
      {
        return this.mRemote;
      }
      
      public void fastForward()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(22, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public Bundle getExtras()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(31, localParcel1, localParcel2, 0);
          localParcel2.readException();
          Bundle localBundle;
          if (localParcel2.readInt() != 0) {
            localBundle = (Bundle)Bundle.CREATOR.createFromParcel(localParcel2);
          } else {
            localBundle = null;
          }
          return localBundle;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public long getFlags()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(9, localParcel1, localParcel2, 0);
          localParcel2.readException();
          long l = localParcel2.readLong();
          return l;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getInterfaceDescriptor()
      {
        return "android.support.v4.media.session.IMediaSession";
      }
      
      public PendingIntent getLaunchPendingIntent()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(8, localParcel1, localParcel2, 0);
          localParcel2.readException();
          PendingIntent localPendingIntent;
          if (localParcel2.readInt() != 0) {
            localPendingIntent = (PendingIntent)PendingIntent.CREATOR.createFromParcel(localParcel2);
          } else {
            localPendingIntent = null;
          }
          return localPendingIntent;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public MediaMetadataCompat getMetadata()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(27, localParcel1, localParcel2, 0);
          localParcel2.readException();
          MediaMetadataCompat localMediaMetadataCompat;
          if (localParcel2.readInt() != 0) {
            localMediaMetadataCompat = (MediaMetadataCompat)MediaMetadataCompat.CREATOR.createFromParcel(localParcel2);
          } else {
            localMediaMetadataCompat = null;
          }
          return localMediaMetadataCompat;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getPackageName()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(6, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public PlaybackStateCompat getPlaybackState()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(28, localParcel1, localParcel2, 0);
          localParcel2.readException();
          PlaybackStateCompat localPlaybackStateCompat;
          if (localParcel2.readInt() != 0) {
            localPlaybackStateCompat = (PlaybackStateCompat)PlaybackStateCompat.CREATOR.createFromParcel(localParcel2);
          } else {
            localPlaybackStateCompat = null;
          }
          return localPlaybackStateCompat;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public List<MediaSessionCompat.QueueItem> getQueue()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(29, localParcel1, localParcel2, 0);
          localParcel2.readException();
          ArrayList localArrayList = localParcel2.createTypedArrayList(MediaSessionCompat.QueueItem.CREATOR);
          return localArrayList;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public CharSequence getQueueTitle()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(30, localParcel1, localParcel2, 0);
          localParcel2.readException();
          CharSequence localCharSequence;
          if (localParcel2.readInt() != 0) {
            localCharSequence = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(localParcel2);
          } else {
            localCharSequence = null;
          }
          return localCharSequence;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getRatingType()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(32, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getRepeatMode()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(37, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int getShuffleMode()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(47, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getTag()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(7, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public ParcelableVolumeInfo getVolumeAttributes()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(10, localParcel1, localParcel2, 0);
          localParcel2.readException();
          ParcelableVolumeInfo localParcelableVolumeInfo;
          if (localParcel2.readInt() != 0) {
            localParcelableVolumeInfo = (ParcelableVolumeInfo)ParcelableVolumeInfo.CREATOR.createFromParcel(localParcel2);
          } else {
            localParcelableVolumeInfo = null;
          }
          return localParcelableVolumeInfo;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean isCaptioningEnabled()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          IBinder localIBinder = this.mRemote;
          boolean bool = false;
          localIBinder.transact(45, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean isShuffleModeEnabledDeprecated()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          IBinder localIBinder = this.mRemote;
          boolean bool = false;
          localIBinder.transact(38, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean isTransportControlEnabled()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          IBinder localIBinder = this.mRemote;
          boolean bool = false;
          localIBinder.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          if (i != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void next()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(20, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void pause()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(18, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void play()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(13, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void playFromMediaId(String paramString, Bundle paramBundle)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeString(paramString);
          if (paramBundle != null)
          {
            localParcel1.writeInt(1);
            paramBundle.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.mRemote.transact(14, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void playFromSearch(String paramString, Bundle paramBundle)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeString(paramString);
          if (paramBundle != null)
          {
            localParcel1.writeInt(1);
            paramBundle.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.mRemote.transact(15, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void playFromUri(Uri paramUri, Bundle paramBundle)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (paramUri != null)
          {
            localParcel1.writeInt(1);
            paramUri.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          if (paramBundle != null)
          {
            localParcel1.writeInt(1);
            paramBundle.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.mRemote.transact(16, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void prepare()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(33, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void prepareFromMediaId(String paramString, Bundle paramBundle)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeString(paramString);
          if (paramBundle != null)
          {
            localParcel1.writeInt(1);
            paramBundle.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.mRemote.transact(34, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void prepareFromSearch(String paramString, Bundle paramBundle)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeString(paramString);
          if (paramBundle != null)
          {
            localParcel1.writeInt(1);
            paramBundle.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.mRemote.transact(35, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void prepareFromUri(Uri paramUri, Bundle paramBundle)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (paramUri != null)
          {
            localParcel1.writeInt(1);
            paramUri.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          if (paramBundle != null)
          {
            localParcel1.writeInt(1);
            paramBundle.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.mRemote.transact(36, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void previous()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(21, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void rate(RatingCompat paramRatingCompat)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (paramRatingCompat != null)
          {
            localParcel1.writeInt(1);
            paramRatingCompat.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.mRemote.transact(25, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void rateWithExtras(RatingCompat paramRatingCompat, Bundle paramBundle)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (paramRatingCompat != null)
          {
            localParcel1.writeInt(1);
            paramRatingCompat.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          if (paramBundle != null)
          {
            localParcel1.writeInt(1);
            paramBundle.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.mRemote.transact(51, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void registerCallbackListener(IMediaControllerCallback paramIMediaControllerCallback)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (paramIMediaControllerCallback != null) {
            paramIMediaControllerCallback = paramIMediaControllerCallback.asBinder();
          } else {
            paramIMediaControllerCallback = null;
          }
          localParcel1.writeStrongBinder(paramIMediaControllerCallback);
          this.mRemote.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void removeQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (paramMediaDescriptionCompat != null)
          {
            localParcel1.writeInt(1);
            paramMediaDescriptionCompat.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.mRemote.transact(43, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void removeQueueItemAt(int paramInt)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(44, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void rewind()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(23, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void seekTo(long paramLong)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeLong(paramLong);
          this.mRemote.transact(24, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void sendCommand(String paramString, Bundle paramBundle, MediaSessionCompat.ResultReceiverWrapper paramResultReceiverWrapper)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeString(paramString);
          if (paramBundle != null)
          {
            localParcel1.writeInt(1);
            paramBundle.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          if (paramResultReceiverWrapper != null)
          {
            localParcel1.writeInt(1);
            paramResultReceiverWrapper.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.mRemote.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void sendCustomAction(String paramString, Bundle paramBundle)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeString(paramString);
          if (paramBundle != null)
          {
            localParcel1.writeInt(1);
            paramBundle.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.mRemote.transact(26, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean sendMediaButton(KeyEvent paramKeyEvent)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          boolean bool = true;
          if (paramKeyEvent != null)
          {
            localParcel1.writeInt(1);
            paramKeyEvent.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          if (i == 0) {
            bool = false;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setCaptioningEnabled(boolean paramBoolean)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          int i;
          if (paramBoolean) {
            i = 1;
          } else {
            i = 0;
          }
          localParcel1.writeInt(i);
          this.mRemote.transact(46, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setRepeatMode(int paramInt)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(39, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setShuffleMode(int paramInt)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(48, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setShuffleModeEnabledDeprecated(boolean paramBoolean)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          int i;
          if (paramBoolean) {
            i = 1;
          } else {
            i = 0;
          }
          localParcel1.writeInt(i);
          this.mRemote.transact(40, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void setVolumeTo(int paramInt1, int paramInt2, String paramString)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          localParcel1.writeString(paramString);
          this.mRemote.transact(12, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void skipToQueueItem(long paramLong)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          localParcel1.writeLong(paramLong);
          this.mRemote.transact(17, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void stop()
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(19, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void unregisterCallbackListener(IMediaControllerCallback paramIMediaControllerCallback)
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (paramIMediaControllerCallback != null) {
            paramIMediaControllerCallback = paramIMediaControllerCallback.asBinder();
          } else {
            paramIMediaControllerCallback = null;
          }
          localParcel1.writeStrongBinder(paramIMediaControllerCallback);
          this.mRemote.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
    }
  }
}


/* Location:              ~/android/support/v4/media/session/IMediaSession.class
 *
 * Reversed by:           J
 */