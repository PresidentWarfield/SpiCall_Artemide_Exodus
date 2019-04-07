package android.support.v4.app;

import android.app.Notification;
import android.app.Notification.BigPictureStyle;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.Notification.InboxStyle;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class NotificationCompatJellybean
{
  static final String EXTRA_ALLOW_GENERATED_REPLIES = "android.support.allowGeneratedReplies";
  static final String EXTRA_DATA_ONLY_REMOTE_INPUTS = "android.support.dataRemoteInputs";
  private static final String KEY_ACTION_INTENT = "actionIntent";
  private static final String KEY_DATA_ONLY_REMOTE_INPUTS = "dataOnlyRemoteInputs";
  private static final String KEY_EXTRAS = "extras";
  private static final String KEY_ICON = "icon";
  private static final String KEY_REMOTE_INPUTS = "remoteInputs";
  private static final String KEY_TITLE = "title";
  public static final String TAG = "NotificationCompat";
  private static Class<?> sActionClass;
  private static Field sActionIconField;
  private static Field sActionIntentField;
  private static Field sActionTitleField;
  private static boolean sActionsAccessFailed;
  private static Field sActionsField;
  private static final Object sActionsLock = new Object();
  private static Field sExtrasField;
  private static boolean sExtrasFieldAccessFailed;
  private static final Object sExtrasLock = new Object();
  
  public static void addBigPictureStyle(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor, CharSequence paramCharSequence1, boolean paramBoolean1, CharSequence paramCharSequence2, Bitmap paramBitmap1, Bitmap paramBitmap2, boolean paramBoolean2)
  {
    paramNotificationBuilderWithBuilderAccessor = new Notification.BigPictureStyle(paramNotificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(paramCharSequence1).bigPicture(paramBitmap1);
    if (paramBoolean2) {
      paramNotificationBuilderWithBuilderAccessor.bigLargeIcon(paramBitmap2);
    }
    if (paramBoolean1) {
      paramNotificationBuilderWithBuilderAccessor.setSummaryText(paramCharSequence2);
    }
  }
  
  public static void addBigTextStyle(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor, CharSequence paramCharSequence1, boolean paramBoolean, CharSequence paramCharSequence2, CharSequence paramCharSequence3)
  {
    paramNotificationBuilderWithBuilderAccessor = new Notification.BigTextStyle(paramNotificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(paramCharSequence1).bigText(paramCharSequence3);
    if (paramBoolean) {
      paramNotificationBuilderWithBuilderAccessor.setSummaryText(paramCharSequence2);
    }
  }
  
  public static void addInboxStyle(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor, CharSequence paramCharSequence1, boolean paramBoolean, CharSequence paramCharSequence2, ArrayList<CharSequence> paramArrayList)
  {
    paramNotificationBuilderWithBuilderAccessor = new Notification.InboxStyle(paramNotificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(paramCharSequence1);
    if (paramBoolean) {
      paramNotificationBuilderWithBuilderAccessor.setSummaryText(paramCharSequence2);
    }
    paramCharSequence1 = paramArrayList.iterator();
    while (paramCharSequence1.hasNext()) {
      paramNotificationBuilderWithBuilderAccessor.addLine((CharSequence)paramCharSequence1.next());
    }
  }
  
  public static SparseArray<Bundle> buildActionExtrasMap(List<Bundle> paramList)
  {
    int i = paramList.size();
    Object localObject1 = null;
    int j = 0;
    while (j < i)
    {
      Bundle localBundle = (Bundle)paramList.get(j);
      Object localObject2 = localObject1;
      if (localBundle != null)
      {
        localObject2 = localObject1;
        if (localObject1 == null) {
          localObject2 = new SparseArray();
        }
        ((SparseArray)localObject2).put(j, localBundle);
      }
      j++;
      localObject1 = localObject2;
    }
    return (SparseArray<Bundle>)localObject1;
  }
  
  private static boolean ensureActionReflectionReadyLocked()
  {
    if (sActionsAccessFailed) {
      return false;
    }
    try
    {
      if (sActionsField == null)
      {
        sActionClass = Class.forName("android.app.Notification$Action");
        sActionIconField = sActionClass.getDeclaredField("icon");
        sActionTitleField = sActionClass.getDeclaredField("title");
        sActionIntentField = sActionClass.getDeclaredField("actionIntent");
        sActionsField = Notification.class.getDeclaredField("actions");
        sActionsField.setAccessible(true);
      }
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      Log.e("NotificationCompat", "Unable to access notification actions", localNoSuchFieldException);
      sActionsAccessFailed = true;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      Log.e("NotificationCompat", "Unable to access notification actions", localClassNotFoundException);
      sActionsAccessFailed = true;
    }
    return true ^ sActionsAccessFailed;
  }
  
  public static NotificationCompatBase.Action getAction(Notification paramNotification, int paramInt, NotificationCompatBase.Action.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1)
  {
    try
    {
      synchronized (sActionsLock)
      {
        Object localObject2 = getActionObjectsLocked(paramNotification);
        if (localObject2 != null)
        {
          localObject2 = localObject2[paramInt];
          paramNotification = getExtras(paramNotification);
          if (paramNotification != null)
          {
            paramNotification = paramNotification.getSparseParcelableArray("android.support.actionExtras");
            if (paramNotification != null)
            {
              paramNotification = (Bundle)paramNotification.get(paramInt);
              break label59;
            }
          }
          paramNotification = null;
          label59:
          paramNotification = readAction(paramFactory, paramFactory1, sActionIconField.getInt(localObject2), (CharSequence)sActionTitleField.get(localObject2), (PendingIntent)sActionIntentField.get(localObject2), paramNotification);
          return paramNotification;
        }
      }
    }
    catch (IllegalAccessException paramNotification)
    {
      Log.e("NotificationCompat", "Unable to access notification actions", paramNotification);
      sActionsAccessFailed = true;
      return null;
    }
  }
  
  public static int getActionCount(Notification paramNotification)
  {
    synchronized (sActionsLock)
    {
      paramNotification = getActionObjectsLocked(paramNotification);
      int i;
      if (paramNotification != null) {
        i = paramNotification.length;
      } else {
        i = 0;
      }
      return i;
    }
  }
  
  private static NotificationCompatBase.Action getActionFromBundle(Bundle paramBundle, NotificationCompatBase.Action.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1)
  {
    Bundle localBundle = paramBundle.getBundle("extras");
    boolean bool;
    if (localBundle != null) {
      bool = localBundle.getBoolean("android.support.allowGeneratedReplies", false);
    } else {
      bool = false;
    }
    return paramFactory.build(paramBundle.getInt("icon"), paramBundle.getCharSequence("title"), (PendingIntent)paramBundle.getParcelable("actionIntent"), paramBundle.getBundle("extras"), RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(paramBundle, "remoteInputs"), paramFactory1), RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(paramBundle, "dataOnlyRemoteInputs"), paramFactory1), bool);
  }
  
  private static Object[] getActionObjectsLocked(Notification paramNotification)
  {
    synchronized (sActionsLock)
    {
      if (!ensureActionReflectionReadyLocked()) {
        return null;
      }
      try
      {
        paramNotification = (Object[])sActionsField.get(paramNotification);
        return paramNotification;
      }
      catch (IllegalAccessException paramNotification)
      {
        Log.e("NotificationCompat", "Unable to access notification actions", paramNotification);
        sActionsAccessFailed = true;
        return null;
      }
    }
  }
  
  public static NotificationCompatBase.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> paramArrayList, NotificationCompatBase.Action.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1)
  {
    if (paramArrayList == null) {
      return null;
    }
    NotificationCompatBase.Action[] arrayOfAction = paramFactory.newArray(paramArrayList.size());
    for (int i = 0; i < arrayOfAction.length; i++) {
      arrayOfAction[i] = getActionFromBundle((Bundle)paramArrayList.get(i), paramFactory, paramFactory1);
    }
    return arrayOfAction;
  }
  
  private static Bundle getBundleForAction(NotificationCompatBase.Action paramAction)
  {
    Bundle localBundle1 = new Bundle();
    localBundle1.putInt("icon", paramAction.getIcon());
    localBundle1.putCharSequence("title", paramAction.getTitle());
    localBundle1.putParcelable("actionIntent", paramAction.getActionIntent());
    Bundle localBundle2;
    if (paramAction.getExtras() != null) {
      localBundle2 = new Bundle(paramAction.getExtras());
    } else {
      localBundle2 = new Bundle();
    }
    localBundle2.putBoolean("android.support.allowGeneratedReplies", paramAction.getAllowGeneratedReplies());
    localBundle1.putBundle("extras", localBundle2);
    localBundle1.putParcelableArray("remoteInputs", RemoteInputCompatJellybean.toBundleArray(paramAction.getRemoteInputs()));
    return localBundle1;
  }
  
  public static Bundle getExtras(Notification paramNotification)
  {
    synchronized (sExtrasLock)
    {
      if (sExtrasFieldAccessFailed) {
        return null;
      }
      try
      {
        if (sExtrasField == null)
        {
          localObject2 = Notification.class.getDeclaredField("extras");
          if (!Bundle.class.isAssignableFrom(((Field)localObject2).getType()))
          {
            Log.e("NotificationCompat", "Notification.extras field is not of type Bundle");
            sExtrasFieldAccessFailed = true;
            return null;
          }
          ((Field)localObject2).setAccessible(true);
          sExtrasField = (Field)localObject2;
        }
        Bundle localBundle = (Bundle)sExtrasField.get(paramNotification);
        Object localObject2 = localBundle;
        if (localBundle == null)
        {
          localObject2 = new android/os/Bundle;
          ((Bundle)localObject2).<init>();
          sExtrasField.set(paramNotification, localObject2);
        }
        return (Bundle)localObject2;
      }
      catch (NoSuchFieldException paramNotification)
      {
        Log.e("NotificationCompat", "Unable to access notification extras", paramNotification);
      }
      catch (IllegalAccessException paramNotification)
      {
        Log.e("NotificationCompat", "Unable to access notification extras", paramNotification);
      }
      sExtrasFieldAccessFailed = true;
      return null;
    }
  }
  
  public static ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompatBase.Action[] paramArrayOfAction)
  {
    if (paramArrayOfAction == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList(paramArrayOfAction.length);
    int i = paramArrayOfAction.length;
    for (int j = 0; j < i; j++) {
      localArrayList.add(getBundleForAction(paramArrayOfAction[j]));
    }
    return localArrayList;
  }
  
  public static NotificationCompatBase.Action readAction(NotificationCompatBase.Action.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1, int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent, Bundle paramBundle)
  {
    RemoteInputCompatBase.RemoteInput[] arrayOfRemoteInput;
    boolean bool;
    if (paramBundle != null)
    {
      arrayOfRemoteInput = RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(paramBundle, "android.support.remoteInputs"), paramFactory1);
      paramFactory1 = RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(paramBundle, "android.support.dataRemoteInputs"), paramFactory1);
      bool = paramBundle.getBoolean("android.support.allowGeneratedReplies");
    }
    else
    {
      arrayOfRemoteInput = null;
      paramFactory1 = arrayOfRemoteInput;
      bool = false;
    }
    return paramFactory.build(paramInt, paramCharSequence, paramPendingIntent, paramBundle, arrayOfRemoteInput, paramFactory1, bool);
  }
  
  public static Bundle writeActionAndGetExtras(Notification.Builder paramBuilder, NotificationCompatBase.Action paramAction)
  {
    paramBuilder.addAction(paramAction.getIcon(), paramAction.getTitle(), paramAction.getActionIntent());
    paramBuilder = new Bundle(paramAction.getExtras());
    if (paramAction.getRemoteInputs() != null) {
      paramBuilder.putParcelableArray("android.support.remoteInputs", RemoteInputCompatJellybean.toBundleArray(paramAction.getRemoteInputs()));
    }
    if (paramAction.getDataOnlyRemoteInputs() != null) {
      paramBuilder.putParcelableArray("android.support.dataRemoteInputs", RemoteInputCompatJellybean.toBundleArray(paramAction.getDataOnlyRemoteInputs()));
    }
    paramBuilder.putBoolean("android.support.allowGeneratedReplies", paramAction.getAllowGeneratedReplies());
    return paramBuilder;
  }
  
  public static class Builder
    implements NotificationBuilderWithActions, NotificationBuilderWithBuilderAccessor
  {
    private Notification.Builder b;
    private List<Bundle> mActionExtrasList = new ArrayList();
    private RemoteViews mBigContentView;
    private RemoteViews mContentView;
    private final Bundle mExtras;
    
    public Builder(Context paramContext, Notification paramNotification, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, RemoteViews paramRemoteViews1, int paramInt1, PendingIntent paramPendingIntent1, PendingIntent paramPendingIntent2, Bitmap paramBitmap, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, int paramInt4, CharSequence paramCharSequence4, boolean paramBoolean3, Bundle paramBundle, String paramString1, boolean paramBoolean4, String paramString2, RemoteViews paramRemoteViews2, RemoteViews paramRemoteViews3)
    {
      paramContext = new Notification.Builder(paramContext).setWhen(paramNotification.when).setSmallIcon(paramNotification.icon, paramNotification.iconLevel).setContent(paramNotification.contentView).setTicker(paramNotification.tickerText, paramRemoteViews1).setSound(paramNotification.sound, paramNotification.audioStreamType).setVibrate(paramNotification.vibrate).setLights(paramNotification.ledARGB, paramNotification.ledOnMS, paramNotification.ledOffMS);
      int i = paramNotification.flags;
      boolean bool1 = false;
      boolean bool2;
      if ((i & 0x2) != 0) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      paramContext = paramContext.setOngoing(bool2);
      if ((paramNotification.flags & 0x8) != 0) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      paramContext = paramContext.setOnlyAlertOnce(bool2);
      if ((paramNotification.flags & 0x10) != 0) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      paramContext = paramContext.setAutoCancel(bool2).setDefaults(paramNotification.defaults).setContentTitle(paramCharSequence1).setContentText(paramCharSequence2).setSubText(paramCharSequence4).setContentInfo(paramCharSequence3).setContentIntent(paramPendingIntent1).setDeleteIntent(paramNotification.deleteIntent);
      if ((paramNotification.flags & 0x80) != 0) {
        bool2 = true;
      } else {
        bool2 = bool1;
      }
      this.b = paramContext.setFullScreenIntent(paramPendingIntent2, bool2).setLargeIcon(paramBitmap).setNumber(paramInt1).setUsesChronometer(paramBoolean2).setPriority(paramInt4).setProgress(paramInt2, paramInt3, paramBoolean1);
      this.mExtras = new Bundle();
      if (paramBundle != null) {
        this.mExtras.putAll(paramBundle);
      }
      if (paramBoolean3) {
        this.mExtras.putBoolean("android.support.localOnly", true);
      }
      if (paramString1 != null)
      {
        this.mExtras.putString("android.support.groupKey", paramString1);
        if (paramBoolean4) {
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
      Notification localNotification = this.b.build();
      Bundle localBundle1 = NotificationCompatJellybean.getExtras(localNotification);
      Bundle localBundle2 = new Bundle(this.mExtras);
      Iterator localIterator = this.mExtras.keySet().iterator();
      while (localIterator.hasNext())
      {
        localObject = (String)localIterator.next();
        if (localBundle1.containsKey((String)localObject)) {
          localBundle2.remove((String)localObject);
        }
      }
      localBundle1.putAll(localBundle2);
      Object localObject = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
      if (localObject != null) {
        NotificationCompatJellybean.getExtras(localNotification).putSparseParcelableArray("android.support.actionExtras", (SparseArray)localObject);
      }
      localObject = this.mContentView;
      if (localObject != null) {
        localNotification.contentView = ((RemoteViews)localObject);
      }
      localObject = this.mBigContentView;
      if (localObject != null) {
        localNotification.bigContentView = ((RemoteViews)localObject);
      }
      return localNotification;
    }
    
    public Notification.Builder getBuilder()
    {
      return this.b;
    }
  }
}


/* Location:              ~/android/support/v4/app/NotificationCompatJellybean.class
 *
 * Reversed by:           J
 */