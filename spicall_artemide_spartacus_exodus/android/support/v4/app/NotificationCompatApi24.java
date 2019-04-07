package android.support.v4.app;

import android.app.Notification;
import android.app.Notification.Action;
import android.app.Notification.Action.Builder;
import android.app.Notification.Builder;
import android.app.Notification.MessagingStyle;
import android.app.Notification.MessagingStyle.Message;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class NotificationCompatApi24
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
    localBuilder.setAllowGeneratedReplies(paramAction.getAllowGeneratedReplies());
    localBuilder.addExtras((Bundle)localObject);
    paramBuilder.addAction(localBuilder.build());
  }
  
  public static void addMessagingStyle(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor, CharSequence paramCharSequence1, CharSequence paramCharSequence2, List<CharSequence> paramList1, List<Long> paramList, List<CharSequence> paramList2, List<String> paramList3, List<Uri> paramList4)
  {
    paramCharSequence1 = new Notification.MessagingStyle(paramCharSequence1).setConversationTitle(paramCharSequence2);
    for (int i = 0; i < paramList1.size(); i++)
    {
      paramCharSequence2 = new Notification.MessagingStyle.Message((CharSequence)paramList1.get(i), ((Long)paramList.get(i)).longValue(), (CharSequence)paramList2.get(i));
      if (paramList3.get(i) != null) {
        paramCharSequence2.setData((String)paramList3.get(i), (Uri)paramList4.get(i));
      }
      paramCharSequence1.addMessage(paramCharSequence2);
    }
    paramCharSequence1.setBuilder(paramNotificationBuilderWithBuilderAccessor.getBuilder());
  }
  
  public static NotificationCompatBase.Action getAction(Notification paramNotification, int paramInt, NotificationCompatBase.Action.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1)
  {
    return getActionCompatFromAction(paramNotification.actions[paramInt], paramFactory, paramFactory1);
  }
  
  private static NotificationCompatBase.Action getActionCompatFromAction(Notification.Action paramAction, NotificationCompatBase.Action.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1)
  {
    paramFactory1 = RemoteInputCompatApi20.toCompat(paramAction.getRemoteInputs(), paramFactory1);
    boolean bool;
    if ((!paramAction.getExtras().getBoolean("android.support.allowGeneratedReplies")) && (!paramAction.getAllowGeneratedReplies())) {
      bool = false;
    } else {
      bool = true;
    }
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
    localBuilder.setAllowGeneratedReplies(paramAction.getAllowGeneratedReplies());
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
    private int mGroupAlertBehavior;
    
    public Builder(Context paramContext, Notification paramNotification1, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, RemoteViews paramRemoteViews1, int paramInt1, PendingIntent paramPendingIntent1, PendingIntent paramPendingIntent2, Bitmap paramBitmap, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt4, CharSequence paramCharSequence4, boolean paramBoolean4, String paramString1, ArrayList<String> paramArrayList, Bundle paramBundle, int paramInt5, int paramInt6, Notification paramNotification2, String paramString2, boolean paramBoolean5, String paramString3, CharSequence[] paramArrayOfCharSequence, RemoteViews paramRemoteViews2, RemoteViews paramRemoteViews3, RemoteViews paramRemoteViews4, int paramInt7)
    {
      paramContext = new Notification.Builder(paramContext).setWhen(paramNotification1.when).setShowWhen(paramBoolean2).setSmallIcon(paramNotification1.icon, paramNotification1.iconLevel).setContent(paramNotification1.contentView).setTicker(paramNotification1.tickerText, paramRemoteViews1).setSound(paramNotification1.sound, paramNotification1.audioStreamType).setVibrate(paramNotification1.vibrate).setLights(paramNotification1.ledARGB, paramNotification1.ledOnMS, paramNotification1.ledOffMS);
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
      this.b = paramContext.setFullScreenIntent(paramPendingIntent2, paramBoolean2).setLargeIcon(paramBitmap).setNumber(paramInt1).setUsesChronometer(paramBoolean3).setPriority(paramInt4).setProgress(paramInt2, paramInt3, paramBoolean1).setLocalOnly(paramBoolean4).setExtras(paramBundle).setGroup(paramString2).setGroupSummary(paramBoolean5).setSortKey(paramString3).setCategory(paramString1).setColor(paramInt5).setVisibility(paramInt6).setPublicVersion(paramNotification2).setRemoteInputHistory(paramArrayOfCharSequence);
      if (paramRemoteViews2 != null) {
        this.b.setCustomContentView(paramRemoteViews2);
      }
      if (paramRemoteViews3 != null) {
        this.b.setCustomBigContentView(paramRemoteViews3);
      }
      if (paramRemoteViews4 != null) {
        this.b.setCustomHeadsUpContentView(paramRemoteViews4);
      }
      paramContext = paramArrayList.iterator();
      while (paramContext.hasNext())
      {
        paramNotification1 = (String)paramContext.next();
        this.b.addPerson(paramNotification1);
      }
      this.mGroupAlertBehavior = paramInt7;
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
      NotificationCompatApi24.addAction(this.b, paramAction);
    }
    
    public Notification build()
    {
      Notification localNotification = this.b.build();
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


/* Location:              ~/android/support/v4/app/NotificationCompatApi24.class
 *
 * Reversed by:           J
 */