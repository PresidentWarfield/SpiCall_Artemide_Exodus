package android.support.v4.app;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;

class NotificationCompatApi26
{
  public static class Builder
    implements NotificationBuilderWithActions, NotificationBuilderWithBuilderAccessor
  {
    private Notification.Builder mB;
    
    Builder(Context paramContext, Notification paramNotification1, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, RemoteViews paramRemoteViews1, int paramInt1, PendingIntent paramPendingIntent1, PendingIntent paramPendingIntent2, Bitmap paramBitmap, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt4, CharSequence paramCharSequence4, boolean paramBoolean4, String paramString1, ArrayList<String> paramArrayList, Bundle paramBundle, int paramInt5, int paramInt6, Notification paramNotification2, String paramString2, boolean paramBoolean5, String paramString3, CharSequence[] paramArrayOfCharSequence, RemoteViews paramRemoteViews2, RemoteViews paramRemoteViews3, RemoteViews paramRemoteViews4, String paramString4, int paramInt7, String paramString5, long paramLong, boolean paramBoolean6, boolean paramBoolean7, int paramInt8)
    {
      paramContext = new Notification.Builder(paramContext, paramString4).setWhen(paramNotification1.when).setShowWhen(paramBoolean2).setSmallIcon(paramNotification1.icon, paramNotification1.iconLevel).setContent(paramNotification1.contentView).setTicker(paramNotification1.tickerText, paramRemoteViews1).setSound(paramNotification1.sound, paramNotification1.audioStreamType).setVibrate(paramNotification1.vibrate).setLights(paramNotification1.ledARGB, paramNotification1.ledOnMS, paramNotification1.ledOffMS);
      int i = paramNotification1.flags;
      boolean bool = true;
      if ((i & 0x2) != 0) {
        paramBoolean2 = true;
      } else {
        paramBoolean2 = false;
      }
      paramContext = paramContext.setOngoing(paramBoolean2);
      if ((paramNotification1.flags & 0x8) != 0) {
        paramBoolean2 = true;
      } else {
        paramBoolean2 = false;
      }
      paramContext = paramContext.setOnlyAlertOnce(paramBoolean2);
      if ((paramNotification1.flags & 0x10) != 0) {
        paramBoolean2 = true;
      } else {
        paramBoolean2 = false;
      }
      paramContext = paramContext.setAutoCancel(paramBoolean2).setDefaults(paramNotification1.defaults).setContentTitle(paramCharSequence1).setContentText(paramCharSequence2).setSubText(paramCharSequence4).setContentInfo(paramCharSequence3).setContentIntent(paramPendingIntent1).setDeleteIntent(paramNotification1.deleteIntent);
      if ((paramNotification1.flags & 0x80) != 0) {
        paramBoolean2 = bool;
      } else {
        paramBoolean2 = false;
      }
      this.mB = paramContext.setFullScreenIntent(paramPendingIntent2, paramBoolean2).setLargeIcon(paramBitmap).setNumber(paramInt1).setUsesChronometer(paramBoolean3).setPriority(paramInt4).setProgress(paramInt2, paramInt3, paramBoolean1).setLocalOnly(paramBoolean4).setExtras(paramBundle).setGroup(paramString2).setGroupSummary(paramBoolean5).setSortKey(paramString3).setCategory(paramString1).setColor(paramInt5).setVisibility(paramInt6).setPublicVersion(paramNotification2).setRemoteInputHistory(paramArrayOfCharSequence).setChannelId(paramString4).setBadgeIconType(paramInt7).setShortcutId(paramString5).setTimeoutAfter(paramLong).setGroupAlertBehavior(paramInt8);
      if (paramBoolean7) {
        this.mB.setColorized(paramBoolean6);
      }
      if (paramRemoteViews2 != null) {
        this.mB.setCustomContentView(paramRemoteViews2);
      }
      if (paramRemoteViews3 != null) {
        this.mB.setCustomBigContentView(paramRemoteViews3);
      }
      if (paramRemoteViews4 != null) {
        this.mB.setCustomHeadsUpContentView(paramRemoteViews4);
      }
      paramNotification1 = paramArrayList.iterator();
      while (paramNotification1.hasNext())
      {
        paramContext = (String)paramNotification1.next();
        this.mB.addPerson(paramContext);
      }
    }
    
    public void addAction(NotificationCompatBase.Action paramAction)
    {
      NotificationCompatApi24.addAction(this.mB, paramAction);
    }
    
    public Notification build()
    {
      return this.mB.build();
    }
    
    public Notification.Builder getBuilder()
    {
      return this.mB;
    }
  }
}


/* Location:              ~/android/support/v4/app/NotificationCompatApi26.class
 *
 * Reversed by:           J
 */