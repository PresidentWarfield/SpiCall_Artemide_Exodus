package android.support.v4.app;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.app.RemoteInput.Builder;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;

class NotificationCompatApi21
{
  private static final String KEY_AUTHOR = "author";
  private static final String KEY_MESSAGES = "messages";
  private static final String KEY_ON_READ = "on_read";
  private static final String KEY_ON_REPLY = "on_reply";
  private static final String KEY_PARTICIPANTS = "participants";
  private static final String KEY_REMOTE_INPUT = "remote_input";
  private static final String KEY_TEXT = "text";
  private static final String KEY_TIMESTAMP = "timestamp";
  
  private static RemoteInput fromCompatRemoteInput(RemoteInputCompatBase.RemoteInput paramRemoteInput)
  {
    return new RemoteInput.Builder(paramRemoteInput.getResultKey()).setLabel(paramRemoteInput.getLabel()).setChoices(paramRemoteInput.getChoices()).setAllowFreeFormInput(paramRemoteInput.getAllowFreeFormInput()).addExtras(paramRemoteInput.getExtras()).build();
  }
  
  static Bundle getBundleForUnreadConversation(NotificationCompatBase.UnreadConversation paramUnreadConversation)
  {
    Bundle localBundle1 = null;
    if (paramUnreadConversation == null) {
      return null;
    }
    Bundle localBundle2 = new Bundle();
    Object localObject1 = paramUnreadConversation.getParticipants();
    int i = 0;
    Object localObject2 = localBundle1;
    if (localObject1 != null)
    {
      localObject2 = localBundle1;
      if (paramUnreadConversation.getParticipants().length > 1) {
        localObject2 = paramUnreadConversation.getParticipants()[0];
      }
    }
    localObject1 = new Parcelable[paramUnreadConversation.getMessages().length];
    while (i < localObject1.length)
    {
      localBundle1 = new Bundle();
      localBundle1.putString("text", paramUnreadConversation.getMessages()[i]);
      localBundle1.putString("author", (String)localObject2);
      localObject1[i] = localBundle1;
      i++;
    }
    localBundle2.putParcelableArray("messages", (Parcelable[])localObject1);
    localObject2 = paramUnreadConversation.getRemoteInput();
    if (localObject2 != null) {
      localBundle2.putParcelable("remote_input", fromCompatRemoteInput((RemoteInputCompatBase.RemoteInput)localObject2));
    }
    localBundle2.putParcelable("on_reply", paramUnreadConversation.getReplyPendingIntent());
    localBundle2.putParcelable("on_read", paramUnreadConversation.getReadPendingIntent());
    localBundle2.putStringArray("participants", paramUnreadConversation.getParticipants());
    localBundle2.putLong("timestamp", paramUnreadConversation.getLatestTimestamp());
    return localBundle2;
  }
  
  static NotificationCompatBase.UnreadConversation getUnreadConversationFromBundle(Bundle paramBundle, NotificationCompatBase.UnreadConversation.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1)
  {
    RemoteInputCompatBase.RemoteInput localRemoteInput = null;
    if (paramBundle == null) {
      return null;
    }
    Object localObject = paramBundle.getParcelableArray("messages");
    String[] arrayOfString1;
    if (localObject != null)
    {
      arrayOfString1 = new String[localObject.length];
      int i = 0;
      for (int j = 0; j < arrayOfString1.length; j++)
      {
        if (!(localObject[j] instanceof Bundle))
        {
          j = i;
          break label103;
        }
        arrayOfString1[j] = ((Bundle)localObject[j]).getString("text");
        if (arrayOfString1[j] == null)
        {
          j = i;
          break label103;
        }
      }
      j = 1;
      label103:
      if (j == 0) {
        return null;
      }
    }
    else
    {
      arrayOfString1 = null;
    }
    PendingIntent localPendingIntent = (PendingIntent)paramBundle.getParcelable("on_read");
    localObject = (PendingIntent)paramBundle.getParcelable("on_reply");
    RemoteInput localRemoteInput1 = (RemoteInput)paramBundle.getParcelable("remote_input");
    String[] arrayOfString2 = paramBundle.getStringArray("participants");
    if ((arrayOfString2 != null) && (arrayOfString2.length == 1))
    {
      if (localRemoteInput1 != null) {
        localRemoteInput = toCompatRemoteInput(localRemoteInput1, paramFactory1);
      }
      return paramFactory.build(arrayOfString1, localRemoteInput, (PendingIntent)localObject, localPendingIntent, arrayOfString2, paramBundle.getLong("timestamp"));
    }
    return null;
  }
  
  private static RemoteInputCompatBase.RemoteInput toCompatRemoteInput(RemoteInput paramRemoteInput, RemoteInputCompatBase.RemoteInput.Factory paramFactory)
  {
    return paramFactory.build(paramRemoteInput.getResultKey(), paramRemoteInput.getLabel(), paramRemoteInput.getChoices(), paramRemoteInput.getAllowFreeFormInput(), paramRemoteInput.getExtras(), null);
  }
  
  public static class Builder
    implements NotificationBuilderWithActions, NotificationBuilderWithBuilderAccessor
  {
    private Notification.Builder b;
    private RemoteViews mBigContentView;
    private RemoteViews mContentView;
    private Bundle mExtras;
    private int mGroupAlertBehavior;
    private RemoteViews mHeadsUpContentView;
    
    public Builder(Context paramContext, Notification paramNotification1, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, RemoteViews paramRemoteViews1, int paramInt1, PendingIntent paramPendingIntent1, PendingIntent paramPendingIntent2, Bitmap paramBitmap, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt4, CharSequence paramCharSequence4, boolean paramBoolean4, String paramString1, ArrayList<String> paramArrayList, Bundle paramBundle, int paramInt5, int paramInt6, Notification paramNotification2, String paramString2, boolean paramBoolean5, String paramString3, RemoteViews paramRemoteViews2, RemoteViews paramRemoteViews3, RemoteViews paramRemoteViews4, int paramInt7)
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
      this.b = paramContext.setFullScreenIntent(paramPendingIntent2, paramBoolean2).setLargeIcon(paramBitmap).setNumber(paramInt1).setUsesChronometer(paramBoolean3).setPriority(paramInt4).setProgress(paramInt2, paramInt3, paramBoolean1).setLocalOnly(paramBoolean4).setGroup(paramString2).setGroupSummary(paramBoolean5).setSortKey(paramString3).setCategory(paramString1).setColor(paramInt5).setVisibility(paramInt6).setPublicVersion(paramNotification2);
      this.mExtras = new Bundle();
      if (paramBundle != null) {
        this.mExtras.putAll(paramBundle);
      }
      paramContext = paramArrayList.iterator();
      while (paramContext.hasNext())
      {
        paramNotification1 = (String)paramContext.next();
        this.b.addPerson(paramNotification1);
      }
      this.mContentView = paramRemoteViews2;
      this.mBigContentView = paramRemoteViews3;
      this.mHeadsUpContentView = paramRemoteViews4;
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
      localRemoteViews = this.mHeadsUpContentView;
      if (localRemoteViews != null) {
        localNotification.headsUpContentView = localRemoteViews;
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


/* Location:              ~/android/support/v4/app/NotificationCompatApi21.class
 *
 * Reversed by:           J
 */