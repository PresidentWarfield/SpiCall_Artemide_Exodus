package android.support.v4.app;

import android.app.Notification;
import android.app.Notification.Action;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.List;

class NotificationCompatKitKat
{
  public static NotificationCompatBase.Action getAction(Notification paramNotification, int paramInt, NotificationCompatBase.Action.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1)
  {
    Notification.Action localAction = paramNotification.actions[paramInt];
    paramNotification = paramNotification.extras.getSparseParcelableArray("android.support.actionExtras");
    if (paramNotification != null) {
      paramNotification = (Bundle)paramNotification.get(paramInt);
    } else {
      paramNotification = null;
    }
    return NotificationCompatJellybean.readAction(paramFactory, paramFactory1, localAction.icon, localAction.title, localAction.actionIntent, paramNotification);
  }
  
  public static class Builder
    implements NotificationBuilderWithActions, NotificationBuilderWithBuilderAccessor
  {
    private Notification.Builder b;
    private List<Bundle> mActionExtrasList = new ArrayList();
    private RemoteViews mBigContentView;
    private RemoteViews mContentView;
    private Bundle mExtras;
    
    public Builder(Context paramContext, Notification paramNotification, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, RemoteViews paramRemoteViews1, int paramInt1, PendingIntent paramPendingIntent1, PendingIntent paramPendingIntent2, Bitmap paramBitmap, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt4, CharSequence paramCharSequence4, boolean paramBoolean4, ArrayList<String> paramArrayList, Bundle paramBundle, String paramString1, boolean paramBoolean5, String paramString2, RemoteViews paramRemoteViews2, RemoteViews paramRemoteViews3)
    {
      paramContext = new Notification.Builder(paramContext).setWhen(paramNotification.when).setShowWhen(paramBoolean2).setSmallIcon(paramNotification.icon, paramNotification.iconLevel).setContent(paramNotification.contentView).setTicker(paramNotification.tickerText, paramRemoteViews1).setSound(paramNotification.sound, paramNotification.audioStreamType).setVibrate(paramNotification.vibrate).setLights(paramNotification.ledARGB, paramNotification.ledOnMS, paramNotification.ledOffMS);
      int i = paramNotification.flags;
      boolean bool = false;
      if ((i & 0x2) != 0) {
        paramBoolean2 = true;
      } else {
        paramBoolean2 = false;
      }
      paramContext = paramContext.setOngoing(paramBoolean2);
      if ((paramNotification.flags & 0x8) != 0) {
        paramBoolean2 = true;
      } else {
        paramBoolean2 = false;
      }
      paramContext = paramContext.setOnlyAlertOnce(paramBoolean2);
      if ((paramNotification.flags & 0x10) != 0) {
        paramBoolean2 = true;
      } else {
        paramBoolean2 = false;
      }
      paramContext = paramContext.setAutoCancel(paramBoolean2).setDefaults(paramNotification.defaults).setContentTitle(paramCharSequence1).setContentText(paramCharSequence2).setSubText(paramCharSequence4).setContentInfo(paramCharSequence3).setContentIntent(paramPendingIntent1).setDeleteIntent(paramNotification.deleteIntent);
      if ((paramNotification.flags & 0x80) != 0) {
        paramBoolean2 = true;
      } else {
        paramBoolean2 = bool;
      }
      this.b = paramContext.setFullScreenIntent(paramPendingIntent2, paramBoolean2).setLargeIcon(paramBitmap).setNumber(paramInt1).setUsesChronometer(paramBoolean3).setPriority(paramInt4).setProgress(paramInt2, paramInt3, paramBoolean1);
      this.mExtras = new Bundle();
      if (paramBundle != null) {
        this.mExtras.putAll(paramBundle);
      }
      if ((paramArrayList != null) && (!paramArrayList.isEmpty())) {
        this.mExtras.putStringArray("android.people", (String[])paramArrayList.toArray(new String[paramArrayList.size()]));
      }
      if (paramBoolean4) {
        this.mExtras.putBoolean("android.support.localOnly", true);
      }
      if (paramString1 != null)
      {
        this.mExtras.putString("android.support.groupKey", paramString1);
        if (paramBoolean5) {
          this.mExtras.putBoolean("android.support.isGroupSummary", true);
        } else {
          this.mExtras.putBoolean("android.support.useSideChannel", true);
        }
      }
      if (paramString2 != null) {
        this.mExtras.putString("android.support.sortKey", paramString2);
      }
      this.mContentView = paramRemoteViews2;
      this.mBigContentView = paramRemoteViews3;
    }
    
    public void addAction(NotificationCompatBase.Action paramAction)
    {
      this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.b, paramAction));
    }
    
    public Notification build()
    {
      Object localObject = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
      if (localObject != null) {
        this.mExtras.putSparseParcelableArray("android.support.actionExtras", (SparseArray)localObject);
      }
      this.b.setExtras(this.mExtras);
      localObject = this.b.build();
      RemoteViews localRemoteViews = this.mContentView;
      if (localRemoteViews != null) {
        ((Notification)localObject).contentView = localRemoteViews;
      }
      localRemoteViews = this.mBigContentView;
      if (localRemoteViews != null) {
        ((Notification)localObject).bigContentView = localRemoteViews;
      }
      return (Notification)localObject;
    }
    
    public Notification.Builder getBuilder()
    {
      return this.b;
    }
  }
}


/* Location:              ~/android/support/v4/app/NotificationCompatKitKat.class
 *
 * Reversed by:           J
 */