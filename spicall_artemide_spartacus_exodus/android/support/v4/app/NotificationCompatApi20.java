package android.support.v4.app;

import android.app.Notification;
import android.app.Notification.Action;
import android.app.Notification.Action.Builder;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.RemoteViews;
import java.util.ArrayList;

class NotificationCompatApi20
{
  public static void addAction(Notification.Builder paramBuilder, NotificationCompatBase.Action paramAction)
  {
    Notification.Action.Builder localBuilder = new Notification.Action.Builder(paramAction.getIcon(), paramAction.getTitle(), paramAction.getActionIntent());
    Object localObject;
    if (paramAction.getRemoteInputs() != null)
    {
      localObject = RemoteInputCompatApi20.fromCompat(paramAction.getRemoteInputs());
      int i = localObject.length;
      for (int j = 0; j < i; j++) {
        localBuilder.addRemoteInput(localObject[j]);
      }
    }
    if (paramAction.getExtras() != null) {
      localObject = new Bundle(paramAction.getExtras());
    } else {
      localObject = new Bundle();
    }
    ((Bundle)localObject).putBoolean("android.support.allowGeneratedReplies", paramAction.getAllowGeneratedReplies());
    localBuilder.addExtras((Bundle)localObject);
    paramBuilder.addAction(localBuilder.build());
  }
  
  public static NotificationCompatBase.Action getAction(Notification paramNotification, int paramInt, NotificationCompatBase.Action.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1)
  {
    return getActionCompatFromAction(paramNotification.actions[paramInt], paramFactory, paramFactory1);
  }
  
  private static NotificationCompatBase.Action getActionCompatFromAction(Notification.Action paramAction, NotificationCompatBase.Action.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1)
  {
    paramFactory1 = RemoteInputCompatApi20.toCompat(paramAction.getRemoteInputs(), paramFactory1);
    boolean bool = paramAction.getExtras().getBoolean("android.support.allowGeneratedReplies");
    return paramFactory.build(paramAction.icon, paramAction.title, paramAction.actionIntent, paramAction.getExtras(), paramFactory1, null, bool);
  }
  
  private static Notification.Action getActionFromActionCompat(NotificationCompatBase.Action paramAction)
  {
    Notification.Action.Builder localBuilder = new Notification.Action.Builder(paramAction.getIcon(), paramAction.getTitle(), paramAction.getActionIntent());
    Bundle localBundle;
    if (paramAction.getExtras() != null) {
      localBundle = new Bundle(paramAction.getExtras());
    } else {
      localBundle = new Bundle();
    }
    localBundle.putBoolean("android.support.allowGeneratedReplies", paramAction.getAllowGeneratedReplies());
    localBuilder.addExtras(localBundle);
    paramAction = paramAction.getRemoteInputs();
    if (paramAction != null)
    {
      paramAction = RemoteInputCompatApi20.fromCompat(paramAction);
      int i = paramAction.length;
      for (int j = 0; j < i; j++) {
        localBuilder.addRemoteInput(paramAction[j]);
      }
    }
    return localBuilder.build();
  }
  
  public static NotificationCompatBase.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> paramArrayList, NotificationCompatBase.Action.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1)
  {
    if (paramArrayList == null) {
      return null;
    }
    NotificationCompatBase.Action[] arrayOfAction = paramFactory.newArray(paramArrayList.size());
    for (int i = 0; i < arrayOfAction.length; i++) {
      arrayOfAction[i] = getActionCompatFromAction((Notification.Action)paramArrayList.get(i), paramFactory, paramFactory1);
    }
    return arrayOfAction;
  }
  
  public static ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompatBase.Action[] paramArrayOfAction)
  {
    if (paramArrayOfAction == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList(paramArrayOfAction.length);
    int i = paramArrayOfAction.length;
    for (int j = 0; j < i; j++) {
      localArrayList.add(getActionFromActionCompat(paramArrayOfAction[j]));
    }
    return localArrayList;
  }
  
  public static class Builder
    implements NotificationBuilderWithActions, NotificationBuilderWithBuilderAccessor
  {
    private Notification.Builder b;
    private RemoteViews mBigContentView;
    private RemoteViews mContentView;
    private Bundle mExtras;
    private int mGroupAlertBehavior;
    
    public Builder(Context paramContext, Notification paramNotification, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, RemoteViews paramRemoteViews1, int paramInt1, PendingIntent paramPendingIntent1, PendingIntent paramPendingIntent2, Bitmap paramBitmap, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt4, CharSequence paramCharSequence4, boolean paramBoolean4, ArrayList<String> paramArrayList, Bundle paramBundle, String paramString1, boolean paramBoolean5, String paramString2, RemoteViews paramRemoteViews2, RemoteViews paramRemoteViews3, int paramInt5)
    {
      paramContext = new Notification.Builder(paramContext).setWhen(paramNotification.when).setShowWhen(paramBoolean2).setSmallIcon(paramNotification.icon, paramNotification.iconLevel).setContent(paramNotification.contentView).setTicker(paramNotification.tickerText, paramRemoteViews1).setSound(paramNotification.sound, paramNotification.audioStreamType).setVibrate(paramNotification.vibrate).setLights(paramNotification.ledARGB, paramNotification.ledOnMS, paramNotification.ledOffMS);
      int i = paramNotification.flags;
      boolean bool = true;
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
        paramBoolean2 = bool;
      } else {
        paramBoolean2 = false;
      }
      this.b = paramContext.setFullScreenIntent(paramPendingIntent2, paramBoolean2).setLargeIcon(paramBitmap).setNumber(paramInt1).setUsesChronometer(paramBoolean3).setPriority(paramInt4).setProgress(paramInt2, paramInt3, paramBoolean1).setLocalOnly(paramBoolean4).setGroup(paramString1).setGroupSummary(paramBoolean5).setSortKey(paramString2);
      this.mExtras = new Bundle();
      if (paramBundle != null) {
        this.mExtras.putAll(paramBundle);
      }
      if ((paramArrayList != null) && (!paramArrayList.isEmpty())) {
        this.mExtras.putStringArray("android.people", (String[])paramArrayList.toArray(new String[paramArrayList.size()]));
      }
      this.mContentView = paramRemoteViews2;
      this.mBigContentView = paramRemoteViews3;
      this.mGroupAlertBehavior = paramInt5;
    }
    
    private void removeSoundAndVibration(Notification paramNotification)
    {
      paramNotification.sound = null;
      paramNotification.vibrate = null;
      paramNotification.defaults &= 0xFFFFFFFE;
      paramNotification.defaults &= 0xFFFFFFFD;
    }
    
    public void addAction(NotificationCompatBase.Action paramAction)
    {
      NotificationCompatApi20.addAction(this.b, paramAction);
    }
    
    public Notification build()
    {
      this.b.setExtras(this.mExtras);
      Notification localNotification = this.b.build();
      RemoteViews localRemoteViews = this.mContentView;
      if (localRemoteViews != null) {
        localNotification.contentView = localRemoteViews;
      }
      localRemoteViews = this.mBigContentView;
      if (localRemoteViews != null) {
        localNotification.bigContentView = localRemoteViews;
      }
      if (this.mGroupAlertBehavior != 0)
      {
        if ((localNotification.getGroup() != null) && ((localNotification.flags & 0x200) != 0) && (this.mGroupAlertBehavior == 2)) {
          removeSoundAndVibration(localNotification);
        }
        if ((localNotification.getGroup() != null) && ((localNotification.flags & 0x200) == 0) && (this.mGroupAlertBehavior == 1)) {
          removeSoundAndVibration(localNotification);
        }
      }
      return localNotification;
    }
    
    public Notification.Builder getBuilder()
    {
      return this.b;
    }
  }
}


/* Location:              ~/android/support/v4/app/NotificationCompatApi20.class
 *
 * Reversed by:           J
 */