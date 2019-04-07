package android.support.v4.app;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.Notification.DecoratedCustomViewStyle;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.a.a.a;
import android.support.a.a.b;
import android.support.a.a.c;
import android.support.a.a.d;
import android.support.a.a.e;
import android.support.a.a.f;
import android.support.a.a.g;
import android.support.v4.text.BidiFormatter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.RemoteViews;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class NotificationCompat
{
  public static final int BADGE_ICON_LARGE = 2;
  public static final int BADGE_ICON_NONE = 0;
  public static final int BADGE_ICON_SMALL = 1;
  public static final String CATEGORY_ALARM = "alarm";
  public static final String CATEGORY_CALL = "call";
  public static final String CATEGORY_EMAIL = "email";
  public static final String CATEGORY_ERROR = "err";
  public static final String CATEGORY_EVENT = "event";
  public static final String CATEGORY_MESSAGE = "msg";
  public static final String CATEGORY_PROGRESS = "progress";
  public static final String CATEGORY_PROMO = "promo";
  public static final String CATEGORY_RECOMMENDATION = "recommendation";
  public static final String CATEGORY_REMINDER = "reminder";
  public static final String CATEGORY_SERVICE = "service";
  public static final String CATEGORY_SOCIAL = "social";
  public static final String CATEGORY_STATUS = "status";
  public static final String CATEGORY_SYSTEM = "sys";
  public static final String CATEGORY_TRANSPORT = "transport";
  public static final int COLOR_DEFAULT = 0;
  public static final int DEFAULT_ALL = -1;
  public static final int DEFAULT_LIGHTS = 4;
  public static final int DEFAULT_SOUND = 1;
  public static final int DEFAULT_VIBRATE = 2;
  public static final String EXTRA_AUDIO_CONTENTS_URI = "android.audioContents";
  public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
  public static final String EXTRA_BIG_TEXT = "android.bigText";
  public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
  public static final String EXTRA_CONVERSATION_TITLE = "android.conversationTitle";
  public static final String EXTRA_INFO_TEXT = "android.infoText";
  public static final String EXTRA_LARGE_ICON = "android.largeIcon";
  public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
  public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";
  public static final String EXTRA_MESSAGES = "android.messages";
  public static final String EXTRA_PEOPLE = "android.people";
  public static final String EXTRA_PICTURE = "android.picture";
  public static final String EXTRA_PROGRESS = "android.progress";
  public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
  public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
  public static final String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";
  public static final String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";
  public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
  public static final String EXTRA_SHOW_WHEN = "android.showWhen";
  public static final String EXTRA_SMALL_ICON = "android.icon";
  public static final String EXTRA_SUB_TEXT = "android.subText";
  public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
  public static final String EXTRA_TEMPLATE = "android.template";
  public static final String EXTRA_TEXT = "android.text";
  public static final String EXTRA_TEXT_LINES = "android.textLines";
  public static final String EXTRA_TITLE = "android.title";
  public static final String EXTRA_TITLE_BIG = "android.title.big";
  public static final int FLAG_AUTO_CANCEL = 16;
  public static final int FLAG_FOREGROUND_SERVICE = 64;
  public static final int FLAG_GROUP_SUMMARY = 512;
  @Deprecated
  public static final int FLAG_HIGH_PRIORITY = 128;
  public static final int FLAG_INSISTENT = 4;
  public static final int FLAG_LOCAL_ONLY = 256;
  public static final int FLAG_NO_CLEAR = 32;
  public static final int FLAG_ONGOING_EVENT = 2;
  public static final int FLAG_ONLY_ALERT_ONCE = 8;
  public static final int FLAG_SHOW_LIGHTS = 1;
  public static final int GROUP_ALERT_ALL = 0;
  public static final int GROUP_ALERT_CHILDREN = 2;
  public static final int GROUP_ALERT_SUMMARY = 1;
  static final NotificationCompatImpl IMPL;
  public static final int PRIORITY_DEFAULT = 0;
  public static final int PRIORITY_HIGH = 1;
  public static final int PRIORITY_LOW = -1;
  public static final int PRIORITY_MAX = 2;
  public static final int PRIORITY_MIN = -2;
  public static final int STREAM_DEFAULT = -1;
  public static final int VISIBILITY_PRIVATE = 0;
  public static final int VISIBILITY_PUBLIC = 1;
  public static final int VISIBILITY_SECRET = -1;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 26) {
      IMPL = new NotificationCompatApi26Impl();
    } else if (Build.VERSION.SDK_INT >= 24) {
      IMPL = new NotificationCompatApi24Impl();
    } else if (Build.VERSION.SDK_INT >= 21) {
      IMPL = new NotificationCompatApi21Impl();
    } else if (Build.VERSION.SDK_INT >= 20) {
      IMPL = new NotificationCompatApi20Impl();
    } else if (Build.VERSION.SDK_INT >= 19) {
      IMPL = new NotificationCompatApi19Impl();
    } else if (Build.VERSION.SDK_INT >= 16) {
      IMPL = new NotificationCompatApi16Impl();
    } else {
      IMPL = new NotificationCompatBaseImpl();
    }
  }
  
  static void addActionsToBuilder(NotificationBuilderWithActions paramNotificationBuilderWithActions, ArrayList<Action> paramArrayList)
  {
    paramArrayList = paramArrayList.iterator();
    while (paramArrayList.hasNext()) {
      paramNotificationBuilderWithActions.addAction((Action)paramArrayList.next());
    }
  }
  
  public static Action getAction(Notification paramNotification, int paramInt)
  {
    return IMPL.getAction(paramNotification, paramInt);
  }
  
  public static int getActionCount(Notification paramNotification)
  {
    int i = Build.VERSION.SDK_INT;
    int j = 0;
    if (i >= 19)
    {
      if (paramNotification.actions != null) {
        j = paramNotification.actions.length;
      }
      return j;
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return NotificationCompatJellybean.getActionCount(paramNotification);
    }
    return 0;
  }
  
  public static int getBadgeIconType(Notification paramNotification)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return paramNotification.getBadgeIconType();
    }
    return 0;
  }
  
  public static String getCategory(Notification paramNotification)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return paramNotification.category;
    }
    return null;
  }
  
  public static String getChannelId(Notification paramNotification)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return paramNotification.getChannelId();
    }
    return null;
  }
  
  public static Bundle getExtras(Notification paramNotification)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return paramNotification.extras;
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return NotificationCompatJellybean.getExtras(paramNotification);
    }
    return null;
  }
  
  public static String getGroup(Notification paramNotification)
  {
    if (Build.VERSION.SDK_INT >= 20) {
      return paramNotification.getGroup();
    }
    if (Build.VERSION.SDK_INT >= 19) {
      return paramNotification.extras.getString("android.support.groupKey");
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return NotificationCompatJellybean.getExtras(paramNotification).getString("android.support.groupKey");
    }
    return null;
  }
  
  public static int getGroupAlertBehavior(Notification paramNotification)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return paramNotification.getGroupAlertBehavior();
    }
    return 0;
  }
  
  public static boolean getLocalOnly(Notification paramNotification)
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool = false;
    if (i >= 20)
    {
      if ((paramNotification.flags & 0x100) != 0) {
        bool = true;
      }
      return bool;
    }
    if (Build.VERSION.SDK_INT >= 19) {
      return paramNotification.extras.getBoolean("android.support.localOnly");
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return NotificationCompatJellybean.getExtras(paramNotification).getBoolean("android.support.localOnly");
    }
    return false;
  }
  
  static Notification[] getNotificationArrayFromBundle(Bundle paramBundle, String paramString)
  {
    Parcelable[] arrayOfParcelable = paramBundle.getParcelableArray(paramString);
    if ((!(arrayOfParcelable instanceof Notification[])) && (arrayOfParcelable != null))
    {
      Notification[] arrayOfNotification = new Notification[arrayOfParcelable.length];
      for (int i = 0; i < arrayOfParcelable.length; i++) {
        arrayOfNotification[i] = ((Notification)arrayOfParcelable[i]);
      }
      paramBundle.putParcelableArray(paramString, arrayOfNotification);
      return arrayOfNotification;
    }
    return (Notification[])arrayOfParcelable;
  }
  
  public static String getShortcutId(Notification paramNotification)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return paramNotification.getShortcutId();
    }
    return null;
  }
  
  public static String getSortKey(Notification paramNotification)
  {
    if (Build.VERSION.SDK_INT >= 20) {
      return paramNotification.getSortKey();
    }
    if (Build.VERSION.SDK_INT >= 19) {
      return paramNotification.extras.getString("android.support.sortKey");
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return NotificationCompatJellybean.getExtras(paramNotification).getString("android.support.sortKey");
    }
    return null;
  }
  
  public static long getTimeoutAfter(Notification paramNotification)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return paramNotification.getTimeoutAfter();
    }
    return 0L;
  }
  
  public static boolean isGroupSummary(Notification paramNotification)
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool = false;
    if (i >= 20)
    {
      if ((paramNotification.flags & 0x200) != 0) {
        bool = true;
      }
      return bool;
    }
    if (Build.VERSION.SDK_INT >= 19) {
      return paramNotification.extras.getBoolean("android.support.isGroupSummary");
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return NotificationCompatJellybean.getExtras(paramNotification).getBoolean("android.support.isGroupSummary");
    }
    return false;
  }
  
  public static class Action
    extends NotificationCompatBase.Action
  {
    public static final NotificationCompatBase.Action.Factory FACTORY = new NotificationCompatBase.Action.Factory()
    {
      public NotificationCompatBase.Action build(int paramAnonymousInt, CharSequence paramAnonymousCharSequence, PendingIntent paramAnonymousPendingIntent, Bundle paramAnonymousBundle, RemoteInputCompatBase.RemoteInput[] paramAnonymousArrayOfRemoteInput1, RemoteInputCompatBase.RemoteInput[] paramAnonymousArrayOfRemoteInput2, boolean paramAnonymousBoolean)
      {
        return new NotificationCompat.Action(paramAnonymousInt, paramAnonymousCharSequence, paramAnonymousPendingIntent, paramAnonymousBundle, (RemoteInput[])paramAnonymousArrayOfRemoteInput1, (RemoteInput[])paramAnonymousArrayOfRemoteInput2, paramAnonymousBoolean);
      }
      
      public NotificationCompat.Action[] newArray(int paramAnonymousInt)
      {
        return new NotificationCompat.Action[paramAnonymousInt];
      }
    };
    public PendingIntent actionIntent;
    public int icon;
    private boolean mAllowGeneratedReplies;
    private final RemoteInput[] mDataOnlyRemoteInputs;
    final Bundle mExtras;
    private final RemoteInput[] mRemoteInputs;
    public CharSequence title;
    
    public Action(int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent)
    {
      this(paramInt, paramCharSequence, paramPendingIntent, new Bundle(), null, null, true);
    }
    
    Action(int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent, Bundle paramBundle, RemoteInput[] paramArrayOfRemoteInput1, RemoteInput[] paramArrayOfRemoteInput2, boolean paramBoolean)
    {
      this.icon = paramInt;
      this.title = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      this.actionIntent = paramPendingIntent;
      if (paramBundle == null) {
        paramBundle = new Bundle();
      }
      this.mExtras = paramBundle;
      this.mRemoteInputs = paramArrayOfRemoteInput1;
      this.mDataOnlyRemoteInputs = paramArrayOfRemoteInput2;
      this.mAllowGeneratedReplies = paramBoolean;
    }
    
    public PendingIntent getActionIntent()
    {
      return this.actionIntent;
    }
    
    public boolean getAllowGeneratedReplies()
    {
      return this.mAllowGeneratedReplies;
    }
    
    public RemoteInput[] getDataOnlyRemoteInputs()
    {
      return this.mDataOnlyRemoteInputs;
    }
    
    public Bundle getExtras()
    {
      return this.mExtras;
    }
    
    public int getIcon()
    {
      return this.icon;
    }
    
    public RemoteInput[] getRemoteInputs()
    {
      return this.mRemoteInputs;
    }
    
    public CharSequence getTitle()
    {
      return this.title;
    }
    
    public static final class Builder
    {
      private boolean mAllowGeneratedReplies = true;
      private final Bundle mExtras;
      private final int mIcon;
      private final PendingIntent mIntent;
      private ArrayList<RemoteInput> mRemoteInputs;
      private final CharSequence mTitle;
      
      public Builder(int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent)
      {
        this(paramInt, paramCharSequence, paramPendingIntent, new Bundle(), null, true);
      }
      
      private Builder(int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent, Bundle paramBundle, RemoteInput[] paramArrayOfRemoteInput, boolean paramBoolean)
      {
        this.mIcon = paramInt;
        this.mTitle = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
        this.mIntent = paramPendingIntent;
        this.mExtras = paramBundle;
        if (paramArrayOfRemoteInput == null) {
          paramCharSequence = null;
        } else {
          paramCharSequence = new ArrayList(Arrays.asList(paramArrayOfRemoteInput));
        }
        this.mRemoteInputs = paramCharSequence;
        this.mAllowGeneratedReplies = paramBoolean;
      }
      
      public Builder(NotificationCompat.Action paramAction)
      {
        this(paramAction.icon, paramAction.title, paramAction.actionIntent, new Bundle(paramAction.mExtras), paramAction.getRemoteInputs(), paramAction.getAllowGeneratedReplies());
      }
      
      public Builder addExtras(Bundle paramBundle)
      {
        if (paramBundle != null) {
          this.mExtras.putAll(paramBundle);
        }
        return this;
      }
      
      public Builder addRemoteInput(RemoteInput paramRemoteInput)
      {
        if (this.mRemoteInputs == null) {
          this.mRemoteInputs = new ArrayList();
        }
        this.mRemoteInputs.add(paramRemoteInput);
        return this;
      }
      
      public NotificationCompat.Action build()
      {
        Object localObject1 = new ArrayList();
        Object localObject2 = new ArrayList();
        Object localObject3 = this.mRemoteInputs;
        if (localObject3 != null)
        {
          localObject3 = ((ArrayList)localObject3).iterator();
          while (((Iterator)localObject3).hasNext())
          {
            RemoteInput localRemoteInput = (RemoteInput)((Iterator)localObject3).next();
            if (localRemoteInput.isDataOnly()) {
              ((List)localObject1).add(localRemoteInput);
            } else {
              ((List)localObject2).add(localRemoteInput);
            }
          }
        }
        if (((List)localObject1).isEmpty()) {
          localObject1 = null;
        } else {
          localObject1 = (RemoteInput[])((List)localObject1).toArray(new RemoteInput[((List)localObject1).size()]);
        }
        if (((List)localObject2).isEmpty()) {
          localObject2 = null;
        } else {
          localObject2 = (RemoteInput[])((List)localObject2).toArray(new RemoteInput[((List)localObject2).size()]);
        }
        return new NotificationCompat.Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, (RemoteInput[])localObject2, (RemoteInput[])localObject1, this.mAllowGeneratedReplies);
      }
      
      public Builder extend(NotificationCompat.Action.Extender paramExtender)
      {
        paramExtender.extend(this);
        return this;
      }
      
      public Bundle getExtras()
      {
        return this.mExtras;
      }
      
      public Builder setAllowGeneratedReplies(boolean paramBoolean)
      {
        this.mAllowGeneratedReplies = paramBoolean;
        return this;
      }
    }
    
    public static abstract interface Extender
    {
      public abstract NotificationCompat.Action.Builder extend(NotificationCompat.Action.Builder paramBuilder);
    }
    
    public static final class WearableExtender
      implements NotificationCompat.Action.Extender
    {
      private static final int DEFAULT_FLAGS = 1;
      private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
      private static final int FLAG_AVAILABLE_OFFLINE = 1;
      private static final int FLAG_HINT_DISPLAY_INLINE = 4;
      private static final int FLAG_HINT_LAUNCHES_ACTIVITY = 2;
      private static final String KEY_CANCEL_LABEL = "cancelLabel";
      private static final String KEY_CONFIRM_LABEL = "confirmLabel";
      private static final String KEY_FLAGS = "flags";
      private static final String KEY_IN_PROGRESS_LABEL = "inProgressLabel";
      private CharSequence mCancelLabel;
      private CharSequence mConfirmLabel;
      private int mFlags = 1;
      private CharSequence mInProgressLabel;
      
      public WearableExtender() {}
      
      public WearableExtender(NotificationCompat.Action paramAction)
      {
        paramAction = paramAction.getExtras().getBundle("android.wearable.EXTENSIONS");
        if (paramAction != null)
        {
          this.mFlags = paramAction.getInt("flags", 1);
          this.mInProgressLabel = paramAction.getCharSequence("inProgressLabel");
          this.mConfirmLabel = paramAction.getCharSequence("confirmLabel");
          this.mCancelLabel = paramAction.getCharSequence("cancelLabel");
        }
      }
      
      private void setFlag(int paramInt, boolean paramBoolean)
      {
        if (paramBoolean) {
          this.mFlags = (paramInt | this.mFlags);
        } else {
          this.mFlags = ((paramInt ^ 0xFFFFFFFF) & this.mFlags);
        }
      }
      
      public WearableExtender clone()
      {
        WearableExtender localWearableExtender = new WearableExtender();
        localWearableExtender.mFlags = this.mFlags;
        localWearableExtender.mInProgressLabel = this.mInProgressLabel;
        localWearableExtender.mConfirmLabel = this.mConfirmLabel;
        localWearableExtender.mCancelLabel = this.mCancelLabel;
        return localWearableExtender;
      }
      
      public NotificationCompat.Action.Builder extend(NotificationCompat.Action.Builder paramBuilder)
      {
        Bundle localBundle = new Bundle();
        int i = this.mFlags;
        if (i != 1) {
          localBundle.putInt("flags", i);
        }
        CharSequence localCharSequence = this.mInProgressLabel;
        if (localCharSequence != null) {
          localBundle.putCharSequence("inProgressLabel", localCharSequence);
        }
        localCharSequence = this.mConfirmLabel;
        if (localCharSequence != null) {
          localBundle.putCharSequence("confirmLabel", localCharSequence);
        }
        localCharSequence = this.mCancelLabel;
        if (localCharSequence != null) {
          localBundle.putCharSequence("cancelLabel", localCharSequence);
        }
        paramBuilder.getExtras().putBundle("android.wearable.EXTENSIONS", localBundle);
        return paramBuilder;
      }
      
      public CharSequence getCancelLabel()
      {
        return this.mCancelLabel;
      }
      
      public CharSequence getConfirmLabel()
      {
        return this.mConfirmLabel;
      }
      
      public boolean getHintDisplayActionInline()
      {
        boolean bool;
        if ((this.mFlags & 0x4) != 0) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean getHintLaunchesActivity()
      {
        boolean bool;
        if ((this.mFlags & 0x2) != 0) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public CharSequence getInProgressLabel()
      {
        return this.mInProgressLabel;
      }
      
      public boolean isAvailableOffline()
      {
        int i = this.mFlags;
        boolean bool = true;
        if ((i & 0x1) == 0) {
          bool = false;
        }
        return bool;
      }
      
      public WearableExtender setAvailableOffline(boolean paramBoolean)
      {
        setFlag(1, paramBoolean);
        return this;
      }
      
      public WearableExtender setCancelLabel(CharSequence paramCharSequence)
      {
        this.mCancelLabel = paramCharSequence;
        return this;
      }
      
      public WearableExtender setConfirmLabel(CharSequence paramCharSequence)
      {
        this.mConfirmLabel = paramCharSequence;
        return this;
      }
      
      public WearableExtender setHintDisplayActionInline(boolean paramBoolean)
      {
        setFlag(4, paramBoolean);
        return this;
      }
      
      public WearableExtender setHintLaunchesActivity(boolean paramBoolean)
      {
        setFlag(2, paramBoolean);
        return this;
      }
      
      public WearableExtender setInProgressLabel(CharSequence paramCharSequence)
      {
        this.mInProgressLabel = paramCharSequence;
        return this;
      }
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface BadgeIconType {}
  
  public static class BigPictureStyle
    extends NotificationCompat.Style
  {
    private Bitmap mBigLargeIcon;
    private boolean mBigLargeIconSet;
    private Bitmap mPicture;
    
    public BigPictureStyle() {}
    
    public BigPictureStyle(NotificationCompat.Builder paramBuilder)
    {
      setBuilder(paramBuilder);
    }
    
    public void apply(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      if (Build.VERSION.SDK_INT >= 16) {
        NotificationCompatJellybean.addBigPictureStyle(paramNotificationBuilderWithBuilderAccessor, this.mBigContentTitle, this.mSummaryTextSet, this.mSummaryText, this.mPicture, this.mBigLargeIcon, this.mBigLargeIconSet);
      }
    }
    
    public BigPictureStyle bigLargeIcon(Bitmap paramBitmap)
    {
      this.mBigLargeIcon = paramBitmap;
      this.mBigLargeIconSet = true;
      return this;
    }
    
    public BigPictureStyle bigPicture(Bitmap paramBitmap)
    {
      this.mPicture = paramBitmap;
      return this;
    }
    
    public BigPictureStyle setBigContentTitle(CharSequence paramCharSequence)
    {
      this.mBigContentTitle = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      return this;
    }
    
    public BigPictureStyle setSummaryText(CharSequence paramCharSequence)
    {
      this.mSummaryText = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      this.mSummaryTextSet = true;
      return this;
    }
  }
  
  public static class BigTextStyle
    extends NotificationCompat.Style
  {
    private CharSequence mBigText;
    
    public BigTextStyle() {}
    
    public BigTextStyle(NotificationCompat.Builder paramBuilder)
    {
      setBuilder(paramBuilder);
    }
    
    public void apply(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      if (Build.VERSION.SDK_INT >= 16) {
        NotificationCompatJellybean.addBigTextStyle(paramNotificationBuilderWithBuilderAccessor, this.mBigContentTitle, this.mSummaryTextSet, this.mSummaryText, this.mBigText);
      }
    }
    
    public BigTextStyle bigText(CharSequence paramCharSequence)
    {
      this.mBigText = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      return this;
    }
    
    public BigTextStyle setBigContentTitle(CharSequence paramCharSequence)
    {
      this.mBigContentTitle = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      return this;
    }
    
    public BigTextStyle setSummaryText(CharSequence paramCharSequence)
    {
      this.mSummaryText = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      this.mSummaryTextSet = true;
      return this;
    }
  }
  
  public static class Builder
  {
    private static final int MAX_CHARSEQUENCE_LENGTH = 5120;
    public ArrayList<NotificationCompat.Action> mActions = new ArrayList();
    int mBadgeIcon = 0;
    RemoteViews mBigContentView;
    String mCategory;
    String mChannelId;
    int mColor = 0;
    boolean mColorized;
    boolean mColorizedSet;
    public CharSequence mContentInfo;
    PendingIntent mContentIntent;
    public CharSequence mContentText;
    public CharSequence mContentTitle;
    RemoteViews mContentView;
    public Context mContext;
    Bundle mExtras;
    PendingIntent mFullScreenIntent;
    private int mGroupAlertBehavior = 0;
    String mGroupKey;
    boolean mGroupSummary;
    RemoteViews mHeadsUpContentView;
    public Bitmap mLargeIcon;
    boolean mLocalOnly = false;
    public Notification mNotification = new Notification();
    public int mNumber;
    public ArrayList<String> mPeople;
    int mPriority;
    int mProgress;
    boolean mProgressIndeterminate;
    int mProgressMax;
    Notification mPublicVersion;
    public CharSequence[] mRemoteInputHistory;
    String mShortcutId;
    boolean mShowWhen = true;
    String mSortKey;
    public NotificationCompat.Style mStyle;
    public CharSequence mSubText;
    RemoteViews mTickerView;
    long mTimeout;
    public boolean mUseChronometer;
    int mVisibility = 0;
    
    @Deprecated
    public Builder(Context paramContext)
    {
      this(paramContext, null);
    }
    
    public Builder(Context paramContext, String paramString)
    {
      this.mContext = paramContext;
      this.mChannelId = paramString;
      this.mNotification.when = System.currentTimeMillis();
      this.mNotification.audioStreamType = -1;
      this.mPriority = 0;
      this.mPeople = new ArrayList();
    }
    
    protected static CharSequence limitCharSequenceLength(CharSequence paramCharSequence)
    {
      if (paramCharSequence == null) {
        return paramCharSequence;
      }
      CharSequence localCharSequence = paramCharSequence;
      if (paramCharSequence.length() > 5120) {
        localCharSequence = paramCharSequence.subSequence(0, 5120);
      }
      return localCharSequence;
    }
    
    private void setFlag(int paramInt, boolean paramBoolean)
    {
      Notification localNotification;
      if (paramBoolean)
      {
        localNotification = this.mNotification;
        localNotification.flags = (paramInt | localNotification.flags);
      }
      else
      {
        localNotification = this.mNotification;
        localNotification.flags = ((paramInt ^ 0xFFFFFFFF) & localNotification.flags);
      }
    }
    
    public Builder addAction(int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent)
    {
      this.mActions.add(new NotificationCompat.Action(paramInt, paramCharSequence, paramPendingIntent));
      return this;
    }
    
    public Builder addAction(NotificationCompat.Action paramAction)
    {
      this.mActions.add(paramAction);
      return this;
    }
    
    public Builder addExtras(Bundle paramBundle)
    {
      if (paramBundle != null)
      {
        Bundle localBundle = this.mExtras;
        if (localBundle == null) {
          this.mExtras = new Bundle(paramBundle);
        } else {
          localBundle.putAll(paramBundle);
        }
      }
      return this;
    }
    
    public Builder addPerson(String paramString)
    {
      this.mPeople.add(paramString);
      return this;
    }
    
    public Notification build()
    {
      return NotificationCompat.IMPL.build(this, getExtender());
    }
    
    public Builder extend(NotificationCompat.Extender paramExtender)
    {
      paramExtender.extend(this);
      return this;
    }
    
    public RemoteViews getBigContentView()
    {
      return this.mBigContentView;
    }
    
    public int getColor()
    {
      return this.mColor;
    }
    
    public RemoteViews getContentView()
    {
      return this.mContentView;
    }
    
    protected NotificationCompat.BuilderExtender getExtender()
    {
      return new NotificationCompat.BuilderExtender();
    }
    
    public Bundle getExtras()
    {
      if (this.mExtras == null) {
        this.mExtras = new Bundle();
      }
      return this.mExtras;
    }
    
    public RemoteViews getHeadsUpContentView()
    {
      return this.mHeadsUpContentView;
    }
    
    @Deprecated
    public Notification getNotification()
    {
      return build();
    }
    
    public int getPriority()
    {
      return this.mPriority;
    }
    
    public long getWhenIfShowing()
    {
      long l;
      if (this.mShowWhen) {
        l = this.mNotification.when;
      } else {
        l = 0L;
      }
      return l;
    }
    
    public Builder setAutoCancel(boolean paramBoolean)
    {
      setFlag(16, paramBoolean);
      return this;
    }
    
    public Builder setBadgeIconType(int paramInt)
    {
      this.mBadgeIcon = paramInt;
      return this;
    }
    
    public Builder setCategory(String paramString)
    {
      this.mCategory = paramString;
      return this;
    }
    
    public Builder setChannelId(String paramString)
    {
      this.mChannelId = paramString;
      return this;
    }
    
    public Builder setColor(int paramInt)
    {
      this.mColor = paramInt;
      return this;
    }
    
    public Builder setColorized(boolean paramBoolean)
    {
      this.mColorized = paramBoolean;
      this.mColorizedSet = true;
      return this;
    }
    
    public Builder setContent(RemoteViews paramRemoteViews)
    {
      this.mNotification.contentView = paramRemoteViews;
      return this;
    }
    
    public Builder setContentInfo(CharSequence paramCharSequence)
    {
      this.mContentInfo = limitCharSequenceLength(paramCharSequence);
      return this;
    }
    
    public Builder setContentIntent(PendingIntent paramPendingIntent)
    {
      this.mContentIntent = paramPendingIntent;
      return this;
    }
    
    public Builder setContentText(CharSequence paramCharSequence)
    {
      this.mContentText = limitCharSequenceLength(paramCharSequence);
      return this;
    }
    
    public Builder setContentTitle(CharSequence paramCharSequence)
    {
      this.mContentTitle = limitCharSequenceLength(paramCharSequence);
      return this;
    }
    
    public Builder setCustomBigContentView(RemoteViews paramRemoteViews)
    {
      this.mBigContentView = paramRemoteViews;
      return this;
    }
    
    public Builder setCustomContentView(RemoteViews paramRemoteViews)
    {
      this.mContentView = paramRemoteViews;
      return this;
    }
    
    public Builder setCustomHeadsUpContentView(RemoteViews paramRemoteViews)
    {
      this.mHeadsUpContentView = paramRemoteViews;
      return this;
    }
    
    public Builder setDefaults(int paramInt)
    {
      Notification localNotification = this.mNotification;
      localNotification.defaults = paramInt;
      if ((paramInt & 0x4) != 0) {
        localNotification.flags |= 0x1;
      }
      return this;
    }
    
    public Builder setDeleteIntent(PendingIntent paramPendingIntent)
    {
      this.mNotification.deleteIntent = paramPendingIntent;
      return this;
    }
    
    public Builder setExtras(Bundle paramBundle)
    {
      this.mExtras = paramBundle;
      return this;
    }
    
    public Builder setFullScreenIntent(PendingIntent paramPendingIntent, boolean paramBoolean)
    {
      this.mFullScreenIntent = paramPendingIntent;
      setFlag(128, paramBoolean);
      return this;
    }
    
    public Builder setGroup(String paramString)
    {
      this.mGroupKey = paramString;
      return this;
    }
    
    public Builder setGroupAlertBehavior(int paramInt)
    {
      this.mGroupAlertBehavior = paramInt;
      return this;
    }
    
    public Builder setGroupSummary(boolean paramBoolean)
    {
      this.mGroupSummary = paramBoolean;
      return this;
    }
    
    public Builder setLargeIcon(Bitmap paramBitmap)
    {
      this.mLargeIcon = paramBitmap;
      return this;
    }
    
    public Builder setLights(int paramInt1, int paramInt2, int paramInt3)
    {
      Notification localNotification = this.mNotification;
      localNotification.ledARGB = paramInt1;
      localNotification.ledOnMS = paramInt2;
      localNotification.ledOffMS = paramInt3;
      if ((localNotification.ledOnMS != 0) && (this.mNotification.ledOffMS != 0)) {
        paramInt1 = 1;
      } else {
        paramInt1 = 0;
      }
      localNotification = this.mNotification;
      localNotification.flags = (paramInt1 | localNotification.flags & 0xFFFFFFFE);
      return this;
    }
    
    public Builder setLocalOnly(boolean paramBoolean)
    {
      this.mLocalOnly = paramBoolean;
      return this;
    }
    
    public Builder setNumber(int paramInt)
    {
      this.mNumber = paramInt;
      return this;
    }
    
    public Builder setOngoing(boolean paramBoolean)
    {
      setFlag(2, paramBoolean);
      return this;
    }
    
    public Builder setOnlyAlertOnce(boolean paramBoolean)
    {
      setFlag(8, paramBoolean);
      return this;
    }
    
    public Builder setPriority(int paramInt)
    {
      this.mPriority = paramInt;
      return this;
    }
    
    public Builder setProgress(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      this.mProgressMax = paramInt1;
      this.mProgress = paramInt2;
      this.mProgressIndeterminate = paramBoolean;
      return this;
    }
    
    public Builder setPublicVersion(Notification paramNotification)
    {
      this.mPublicVersion = paramNotification;
      return this;
    }
    
    public Builder setRemoteInputHistory(CharSequence[] paramArrayOfCharSequence)
    {
      this.mRemoteInputHistory = paramArrayOfCharSequence;
      return this;
    }
    
    public Builder setShortcutId(String paramString)
    {
      this.mShortcutId = paramString;
      return this;
    }
    
    public Builder setShowWhen(boolean paramBoolean)
    {
      this.mShowWhen = paramBoolean;
      return this;
    }
    
    public Builder setSmallIcon(int paramInt)
    {
      this.mNotification.icon = paramInt;
      return this;
    }
    
    public Builder setSmallIcon(int paramInt1, int paramInt2)
    {
      Notification localNotification = this.mNotification;
      localNotification.icon = paramInt1;
      localNotification.iconLevel = paramInt2;
      return this;
    }
    
    public Builder setSortKey(String paramString)
    {
      this.mSortKey = paramString;
      return this;
    }
    
    public Builder setSound(Uri paramUri)
    {
      Notification localNotification = this.mNotification;
      localNotification.sound = paramUri;
      localNotification.audioStreamType = -1;
      return this;
    }
    
    public Builder setSound(Uri paramUri, int paramInt)
    {
      Notification localNotification = this.mNotification;
      localNotification.sound = paramUri;
      localNotification.audioStreamType = paramInt;
      return this;
    }
    
    public Builder setStyle(NotificationCompat.Style paramStyle)
    {
      if (this.mStyle != paramStyle)
      {
        this.mStyle = paramStyle;
        paramStyle = this.mStyle;
        if (paramStyle != null) {
          paramStyle.setBuilder(this);
        }
      }
      return this;
    }
    
    public Builder setSubText(CharSequence paramCharSequence)
    {
      this.mSubText = limitCharSequenceLength(paramCharSequence);
      return this;
    }
    
    public Builder setTicker(CharSequence paramCharSequence)
    {
      this.mNotification.tickerText = limitCharSequenceLength(paramCharSequence);
      return this;
    }
    
    public Builder setTicker(CharSequence paramCharSequence, RemoteViews paramRemoteViews)
    {
      this.mNotification.tickerText = limitCharSequenceLength(paramCharSequence);
      this.mTickerView = paramRemoteViews;
      return this;
    }
    
    public Builder setTimeoutAfter(long paramLong)
    {
      this.mTimeout = paramLong;
      return this;
    }
    
    public Builder setUsesChronometer(boolean paramBoolean)
    {
      this.mUseChronometer = paramBoolean;
      return this;
    }
    
    public Builder setVibrate(long[] paramArrayOfLong)
    {
      this.mNotification.vibrate = paramArrayOfLong;
      return this;
    }
    
    public Builder setVisibility(int paramInt)
    {
      this.mVisibility = paramInt;
      return this;
    }
    
    public Builder setWhen(long paramLong)
    {
      this.mNotification.when = paramLong;
      return this;
    }
  }
  
  protected static class BuilderExtender
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      RemoteViews localRemoteViews;
      if (paramBuilder.mStyle != null) {
        localRemoteViews = paramBuilder.mStyle.makeContentView(paramNotificationBuilderWithBuilderAccessor);
      } else {
        localRemoteViews = null;
      }
      Notification localNotification = paramNotificationBuilderWithBuilderAccessor.build();
      if (localRemoteViews != null) {
        localNotification.contentView = localRemoteViews;
      } else if (paramBuilder.mContentView != null) {
        localNotification.contentView = paramBuilder.mContentView;
      }
      if ((Build.VERSION.SDK_INT >= 16) && (paramBuilder.mStyle != null))
      {
        localRemoteViews = paramBuilder.mStyle.makeBigContentView(paramNotificationBuilderWithBuilderAccessor);
        if (localRemoteViews != null) {
          localNotification.bigContentView = localRemoteViews;
        }
      }
      if ((Build.VERSION.SDK_INT >= 21) && (paramBuilder.mStyle != null))
      {
        paramBuilder = paramBuilder.mStyle.makeHeadsUpContentView(paramNotificationBuilderWithBuilderAccessor);
        if (paramBuilder != null) {
          localNotification.headsUpContentView = paramBuilder;
        }
      }
      return localNotification;
    }
  }
  
  public static final class CarExtender
    implements NotificationCompat.Extender
  {
    private static final String EXTRA_CAR_EXTENDER = "android.car.EXTENSIONS";
    private static final String EXTRA_COLOR = "app_color";
    private static final String EXTRA_CONVERSATION = "car_conversation";
    private static final String EXTRA_LARGE_ICON = "large_icon";
    private static final String TAG = "CarExtender";
    private int mColor = 0;
    private Bitmap mLargeIcon;
    private UnreadConversation mUnreadConversation;
    
    public CarExtender() {}
    
    public CarExtender(Notification paramNotification)
    {
      if (Build.VERSION.SDK_INT < 21) {
        return;
      }
      if (NotificationCompat.getExtras(paramNotification) == null) {
        paramNotification = null;
      } else {
        paramNotification = NotificationCompat.getExtras(paramNotification).getBundle("android.car.EXTENSIONS");
      }
      if (paramNotification != null)
      {
        this.mLargeIcon = ((Bitmap)paramNotification.getParcelable("large_icon"));
        this.mColor = paramNotification.getInt("app_color", 0);
        paramNotification = paramNotification.getBundle("car_conversation");
        this.mUnreadConversation = ((UnreadConversation)NotificationCompat.IMPL.getUnreadConversationFromBundle(paramNotification, UnreadConversation.FACTORY, RemoteInput.FACTORY));
      }
    }
    
    public NotificationCompat.Builder extend(NotificationCompat.Builder paramBuilder)
    {
      if (Build.VERSION.SDK_INT < 21) {
        return paramBuilder;
      }
      Bundle localBundle = new Bundle();
      Bitmap localBitmap = this.mLargeIcon;
      if (localBitmap != null) {
        localBundle.putParcelable("large_icon", localBitmap);
      }
      int i = this.mColor;
      if (i != 0) {
        localBundle.putInt("app_color", i);
      }
      if (this.mUnreadConversation != null) {
        localBundle.putBundle("car_conversation", NotificationCompat.IMPL.getBundleForUnreadConversation(this.mUnreadConversation));
      }
      paramBuilder.getExtras().putBundle("android.car.EXTENSIONS", localBundle);
      return paramBuilder;
    }
    
    public int getColor()
    {
      return this.mColor;
    }
    
    public Bitmap getLargeIcon()
    {
      return this.mLargeIcon;
    }
    
    public UnreadConversation getUnreadConversation()
    {
      return this.mUnreadConversation;
    }
    
    public CarExtender setColor(int paramInt)
    {
      this.mColor = paramInt;
      return this;
    }
    
    public CarExtender setLargeIcon(Bitmap paramBitmap)
    {
      this.mLargeIcon = paramBitmap;
      return this;
    }
    
    public CarExtender setUnreadConversation(UnreadConversation paramUnreadConversation)
    {
      this.mUnreadConversation = paramUnreadConversation;
      return this;
    }
    
    public static class UnreadConversation
      extends NotificationCompatBase.UnreadConversation
    {
      static final NotificationCompatBase.UnreadConversation.Factory FACTORY = new NotificationCompatBase.UnreadConversation.Factory()
      {
        public NotificationCompat.CarExtender.UnreadConversation build(String[] paramAnonymousArrayOfString1, RemoteInputCompatBase.RemoteInput paramAnonymousRemoteInput, PendingIntent paramAnonymousPendingIntent1, PendingIntent paramAnonymousPendingIntent2, String[] paramAnonymousArrayOfString2, long paramAnonymousLong)
        {
          return new NotificationCompat.CarExtender.UnreadConversation(paramAnonymousArrayOfString1, (RemoteInput)paramAnonymousRemoteInput, paramAnonymousPendingIntent1, paramAnonymousPendingIntent2, paramAnonymousArrayOfString2, paramAnonymousLong);
        }
      };
      private final long mLatestTimestamp;
      private final String[] mMessages;
      private final String[] mParticipants;
      private final PendingIntent mReadPendingIntent;
      private final RemoteInput mRemoteInput;
      private final PendingIntent mReplyPendingIntent;
      
      UnreadConversation(String[] paramArrayOfString1, RemoteInput paramRemoteInput, PendingIntent paramPendingIntent1, PendingIntent paramPendingIntent2, String[] paramArrayOfString2, long paramLong)
      {
        this.mMessages = paramArrayOfString1;
        this.mRemoteInput = paramRemoteInput;
        this.mReadPendingIntent = paramPendingIntent2;
        this.mReplyPendingIntent = paramPendingIntent1;
        this.mParticipants = paramArrayOfString2;
        this.mLatestTimestamp = paramLong;
      }
      
      public long getLatestTimestamp()
      {
        return this.mLatestTimestamp;
      }
      
      public String[] getMessages()
      {
        return this.mMessages;
      }
      
      public String getParticipant()
      {
        Object localObject = this.mParticipants;
        if (localObject.length > 0) {
          localObject = localObject[0];
        } else {
          localObject = null;
        }
        return (String)localObject;
      }
      
      public String[] getParticipants()
      {
        return this.mParticipants;
      }
      
      public PendingIntent getReadPendingIntent()
      {
        return this.mReadPendingIntent;
      }
      
      public RemoteInput getRemoteInput()
      {
        return this.mRemoteInput;
      }
      
      public PendingIntent getReplyPendingIntent()
      {
        return this.mReplyPendingIntent;
      }
      
      public static class Builder
      {
        private long mLatestTimestamp;
        private final List<String> mMessages = new ArrayList();
        private final String mParticipant;
        private PendingIntent mReadPendingIntent;
        private RemoteInput mRemoteInput;
        private PendingIntent mReplyPendingIntent;
        
        public Builder(String paramString)
        {
          this.mParticipant = paramString;
        }
        
        public Builder addMessage(String paramString)
        {
          this.mMessages.add(paramString);
          return this;
        }
        
        public NotificationCompat.CarExtender.UnreadConversation build()
        {
          Object localObject = this.mMessages;
          String[] arrayOfString = (String[])((List)localObject).toArray(new String[((List)localObject).size()]);
          String str = this.mParticipant;
          localObject = this.mRemoteInput;
          PendingIntent localPendingIntent1 = this.mReplyPendingIntent;
          PendingIntent localPendingIntent2 = this.mReadPendingIntent;
          long l = this.mLatestTimestamp;
          return new NotificationCompat.CarExtender.UnreadConversation(arrayOfString, (RemoteInput)localObject, localPendingIntent1, localPendingIntent2, new String[] { str }, l);
        }
        
        public Builder setLatestTimestamp(long paramLong)
        {
          this.mLatestTimestamp = paramLong;
          return this;
        }
        
        public Builder setReadPendingIntent(PendingIntent paramPendingIntent)
        {
          this.mReadPendingIntent = paramPendingIntent;
          return this;
        }
        
        public Builder setReplyAction(PendingIntent paramPendingIntent, RemoteInput paramRemoteInput)
        {
          this.mRemoteInput = paramRemoteInput;
          this.mReplyPendingIntent = paramPendingIntent;
          return this;
        }
      }
    }
  }
  
  public static class DecoratedCustomViewStyle
    extends NotificationCompat.Style
  {
    private static final int MAX_ACTION_BUTTONS = 3;
    
    private RemoteViews createRemoteViews(RemoteViews paramRemoteViews, boolean paramBoolean)
    {
      int i = a.f.notification_template_custom_big;
      int j = 1;
      int k = 0;
      RemoteViews localRemoteViews1 = applyStandardTemplate(true, i, false);
      localRemoteViews1.removeAllViews(a.d.actions);
      if ((paramBoolean) && (this.mBuilder.mActions != null))
      {
        int m = Math.min(this.mBuilder.mActions.size(), 3);
        if (m > 0) {
          for (int n = 0;; n++)
          {
            i = j;
            if (n >= m) {
              break;
            }
            RemoteViews localRemoteViews2 = generateActionButton((NotificationCompat.Action)this.mBuilder.mActions.get(n));
            localRemoteViews1.addView(a.d.actions, localRemoteViews2);
          }
        }
      }
      i = 0;
      if (i != 0) {
        i = k;
      } else {
        i = 8;
      }
      localRemoteViews1.setViewVisibility(a.d.actions, i);
      localRemoteViews1.setViewVisibility(a.d.action_divider, i);
      buildIntoRemoteViews(localRemoteViews1, paramRemoteViews);
      return localRemoteViews1;
    }
    
    private RemoteViews generateActionButton(NotificationCompat.Action paramAction)
    {
      int i;
      if (paramAction.actionIntent == null) {
        i = 1;
      } else {
        i = 0;
      }
      Object localObject = this.mBuilder.mContext.getPackageName();
      int j;
      if (i != 0) {
        j = a.f.notification_action_tombstone;
      } else {
        j = a.f.notification_action;
      }
      localObject = new RemoteViews((String)localObject, j);
      ((RemoteViews)localObject).setImageViewBitmap(a.d.action_image, createColoredBitmap(paramAction.getIcon(), this.mBuilder.mContext.getResources().getColor(a.a.notification_action_color_filter)));
      ((RemoteViews)localObject).setTextViewText(a.d.action_text, paramAction.title);
      if (i == 0) {
        ((RemoteViews)localObject).setOnClickPendingIntent(a.d.action_container, paramAction.actionIntent);
      }
      if (Build.VERSION.SDK_INT >= 15) {
        ((RemoteViews)localObject).setContentDescription(a.d.action_container, paramAction.title);
      }
      return (RemoteViews)localObject;
    }
    
    public void apply(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      if (Build.VERSION.SDK_INT >= 24) {
        paramNotificationBuilderWithBuilderAccessor.getBuilder().setStyle(new Notification.DecoratedCustomViewStyle());
      }
    }
    
    public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      if (Build.VERSION.SDK_INT >= 24) {
        return null;
      }
      paramNotificationBuilderWithBuilderAccessor = this.mBuilder.getBigContentView();
      if (paramNotificationBuilderWithBuilderAccessor == null) {
        paramNotificationBuilderWithBuilderAccessor = this.mBuilder.getContentView();
      }
      if (paramNotificationBuilderWithBuilderAccessor == null) {
        return null;
      }
      return createRemoteViews(paramNotificationBuilderWithBuilderAccessor, true);
    }
    
    public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      if (Build.VERSION.SDK_INT >= 24) {
        return null;
      }
      if (this.mBuilder.getContentView() == null) {
        return null;
      }
      return createRemoteViews(this.mBuilder.getContentView(), false);
    }
    
    public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      if (Build.VERSION.SDK_INT >= 24) {
        return null;
      }
      RemoteViews localRemoteViews = this.mBuilder.getHeadsUpContentView();
      if (localRemoteViews != null) {
        paramNotificationBuilderWithBuilderAccessor = localRemoteViews;
      } else {
        paramNotificationBuilderWithBuilderAccessor = this.mBuilder.getContentView();
      }
      if (localRemoteViews == null) {
        return null;
      }
      return createRemoteViews(paramNotificationBuilderWithBuilderAccessor, true);
    }
  }
  
  public static abstract interface Extender
  {
    public abstract NotificationCompat.Builder extend(NotificationCompat.Builder paramBuilder);
  }
  
  public static class InboxStyle
    extends NotificationCompat.Style
  {
    private ArrayList<CharSequence> mTexts = new ArrayList();
    
    public InboxStyle() {}
    
    public InboxStyle(NotificationCompat.Builder paramBuilder)
    {
      setBuilder(paramBuilder);
    }
    
    public InboxStyle addLine(CharSequence paramCharSequence)
    {
      this.mTexts.add(NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence));
      return this;
    }
    
    public void apply(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      if (Build.VERSION.SDK_INT >= 16) {
        NotificationCompatJellybean.addInboxStyle(paramNotificationBuilderWithBuilderAccessor, this.mBigContentTitle, this.mSummaryTextSet, this.mSummaryText, this.mTexts);
      }
    }
    
    public InboxStyle setBigContentTitle(CharSequence paramCharSequence)
    {
      this.mBigContentTitle = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      return this;
    }
    
    public InboxStyle setSummaryText(CharSequence paramCharSequence)
    {
      this.mSummaryText = NotificationCompat.Builder.limitCharSequenceLength(paramCharSequence);
      this.mSummaryTextSet = true;
      return this;
    }
  }
  
  public static class MessagingStyle
    extends NotificationCompat.Style
  {
    public static final int MAXIMUM_RETAINED_MESSAGES = 25;
    CharSequence mConversationTitle;
    List<Message> mMessages = new ArrayList();
    CharSequence mUserDisplayName;
    
    MessagingStyle() {}
    
    public MessagingStyle(CharSequence paramCharSequence)
    {
      this.mUserDisplayName = paramCharSequence;
    }
    
    public static MessagingStyle extractMessagingStyleFromNotification(Notification paramNotification)
    {
      Bundle localBundle = NotificationCompat.getExtras(paramNotification);
      paramNotification = null;
      if ((localBundle == null) || (localBundle.containsKey("android.selfDisplayName"))) {}
      try
      {
        MessagingStyle localMessagingStyle = new android/support/v4/app/NotificationCompat$MessagingStyle;
        localMessagingStyle.<init>();
        localMessagingStyle.restoreFromCompatExtras(localBundle);
        paramNotification = localMessagingStyle;
      }
      catch (ClassCastException localClassCastException)
      {
        for (;;) {}
      }
      return paramNotification;
    }
    
    private Message findLatestIncomingMessage()
    {
      Object localObject;
      for (int i = this.mMessages.size() - 1; i >= 0; i--)
      {
        localObject = (Message)this.mMessages.get(i);
        if (!TextUtils.isEmpty(((Message)localObject).getSender())) {
          return (Message)localObject;
        }
      }
      if (!this.mMessages.isEmpty())
      {
        localObject = this.mMessages;
        return (Message)((List)localObject).get(((List)localObject).size() - 1);
      }
      return null;
    }
    
    private boolean hasMessagesWithoutSender()
    {
      for (int i = this.mMessages.size() - 1; i >= 0; i--) {
        if (((Message)this.mMessages.get(i)).getSender() == null) {
          return true;
        }
      }
      return false;
    }
    
    private TextAppearanceSpan makeFontColorSpan(int paramInt)
    {
      return new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(paramInt), null);
    }
    
    private CharSequence makeMessageLine(Message paramMessage)
    {
      BidiFormatter localBidiFormatter = BidiFormatter.getInstance();
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
      int i;
      if (Build.VERSION.SDK_INT >= 21) {
        i = 1;
      } else {
        i = 0;
      }
      int j;
      if (i != 0) {
        j = -16777216;
      } else {
        j = -1;
      }
      Object localObject1 = paramMessage.getSender();
      int k = j;
      if (TextUtils.isEmpty(paramMessage.getSender()))
      {
        localObject1 = this.mUserDisplayName;
        localObject2 = localObject1;
        if (localObject1 == null) {
          localObject2 = "";
        }
        k = j;
        localObject1 = localObject2;
        if (i != 0)
        {
          k = j;
          localObject1 = localObject2;
          if (this.mBuilder.getColor() != 0)
          {
            k = this.mBuilder.getColor();
            localObject1 = localObject2;
          }
        }
      }
      Object localObject2 = localBidiFormatter.unicodeWrap((CharSequence)localObject1);
      localSpannableStringBuilder.append((CharSequence)localObject2);
      localSpannableStringBuilder.setSpan(makeFontColorSpan(k), localSpannableStringBuilder.length() - ((CharSequence)localObject2).length(), localSpannableStringBuilder.length(), 33);
      if (paramMessage.getText() == null) {
        paramMessage = "";
      } else {
        paramMessage = paramMessage.getText();
      }
      localSpannableStringBuilder.append("  ").append(localBidiFormatter.unicodeWrap(paramMessage));
      return localSpannableStringBuilder;
    }
    
    public void addCompatExtras(Bundle paramBundle)
    {
      super.addCompatExtras(paramBundle);
      CharSequence localCharSequence = this.mUserDisplayName;
      if (localCharSequence != null) {
        paramBundle.putCharSequence("android.selfDisplayName", localCharSequence);
      }
      localCharSequence = this.mConversationTitle;
      if (localCharSequence != null) {
        paramBundle.putCharSequence("android.conversationTitle", localCharSequence);
      }
      if (!this.mMessages.isEmpty()) {
        paramBundle.putParcelableArray("android.messages", Message.getBundleArrayForMessages(this.mMessages));
      }
    }
    
    public MessagingStyle addMessage(Message paramMessage)
    {
      this.mMessages.add(paramMessage);
      if (this.mMessages.size() > 25) {
        this.mMessages.remove(0);
      }
      return this;
    }
    
    public MessagingStyle addMessage(CharSequence paramCharSequence1, long paramLong, CharSequence paramCharSequence2)
    {
      this.mMessages.add(new Message(paramCharSequence1, paramLong, paramCharSequence2));
      if (this.mMessages.size() > 25) {
        this.mMessages.remove(0);
      }
      return this;
    }
    
    public void apply(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      Object localObject1;
      Object localObject2;
      if (Build.VERSION.SDK_INT >= 24)
      {
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        localObject1 = new ArrayList();
        ArrayList localArrayList3 = new ArrayList();
        ArrayList localArrayList4 = new ArrayList();
        localObject2 = this.mMessages.iterator();
        while (((Iterator)localObject2).hasNext())
        {
          Message localMessage = (Message)((Iterator)localObject2).next();
          localArrayList1.add(localMessage.getText());
          localArrayList2.add(Long.valueOf(localMessage.getTimestamp()));
          ((List)localObject1).add(localMessage.getSender());
          localArrayList3.add(localMessage.getDataMimeType());
          localArrayList4.add(localMessage.getDataUri());
        }
        NotificationCompatApi24.addMessagingStyle(paramNotificationBuilderWithBuilderAccessor, this.mUserDisplayName, this.mConversationTitle, localArrayList1, localArrayList2, (List)localObject1, localArrayList3, localArrayList4);
      }
      else
      {
        localObject1 = findLatestIncomingMessage();
        if (this.mConversationTitle != null) {
          paramNotificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(this.mConversationTitle);
        } else if (localObject1 != null) {
          paramNotificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(((Message)localObject1).getSender());
        }
        if (localObject1 != null)
        {
          localObject2 = paramNotificationBuilderWithBuilderAccessor.getBuilder();
          if (this.mConversationTitle != null) {
            localObject1 = makeMessageLine((Message)localObject1);
          } else {
            localObject1 = ((Message)localObject1).getText();
          }
          ((Notification.Builder)localObject2).setContentText((CharSequence)localObject1);
        }
        if (Build.VERSION.SDK_INT >= 16)
        {
          localObject2 = new SpannableStringBuilder();
          int i;
          if ((this.mConversationTitle == null) && (!hasMessagesWithoutSender())) {
            i = 0;
          } else {
            i = 1;
          }
          for (int j = this.mMessages.size() - 1; j >= 0; j--)
          {
            localObject1 = (Message)this.mMessages.get(j);
            if (i != 0) {
              localObject1 = makeMessageLine((Message)localObject1);
            } else {
              localObject1 = ((Message)localObject1).getText();
            }
            if (j != this.mMessages.size() - 1) {
              ((SpannableStringBuilder)localObject2).insert(0, "\n");
            }
            ((SpannableStringBuilder)localObject2).insert(0, (CharSequence)localObject1);
          }
          NotificationCompatJellybean.addBigTextStyle(paramNotificationBuilderWithBuilderAccessor, null, false, null, (CharSequence)localObject2);
        }
      }
    }
    
    public CharSequence getConversationTitle()
    {
      return this.mConversationTitle;
    }
    
    public List<Message> getMessages()
    {
      return this.mMessages;
    }
    
    public CharSequence getUserDisplayName()
    {
      return this.mUserDisplayName;
    }
    
    protected void restoreFromCompatExtras(Bundle paramBundle)
    {
      this.mMessages.clear();
      this.mUserDisplayName = paramBundle.getString("android.selfDisplayName");
      this.mConversationTitle = paramBundle.getString("android.conversationTitle");
      paramBundle = paramBundle.getParcelableArray("android.messages");
      if (paramBundle != null) {
        this.mMessages = Message.getMessagesFromBundleArray(paramBundle);
      }
    }
    
    public MessagingStyle setConversationTitle(CharSequence paramCharSequence)
    {
      this.mConversationTitle = paramCharSequence;
      return this;
    }
    
    public static final class Message
    {
      static final String KEY_DATA_MIME_TYPE = "type";
      static final String KEY_DATA_URI = "uri";
      static final String KEY_EXTRAS_BUNDLE = "extras";
      static final String KEY_SENDER = "sender";
      static final String KEY_TEXT = "text";
      static final String KEY_TIMESTAMP = "time";
      private String mDataMimeType;
      private Uri mDataUri;
      private Bundle mExtras = new Bundle();
      private final CharSequence mSender;
      private final CharSequence mText;
      private final long mTimestamp;
      
      public Message(CharSequence paramCharSequence1, long paramLong, CharSequence paramCharSequence2)
      {
        this.mText = paramCharSequence1;
        this.mTimestamp = paramLong;
        this.mSender = paramCharSequence2;
      }
      
      static Bundle[] getBundleArrayForMessages(List<Message> paramList)
      {
        Bundle[] arrayOfBundle = new Bundle[paramList.size()];
        int i = paramList.size();
        for (int j = 0; j < i; j++) {
          arrayOfBundle[j] = ((Message)paramList.get(j)).toBundle();
        }
        return arrayOfBundle;
      }
      
      static Message getMessageFromBundle(Bundle paramBundle)
      {
        try
        {
          if ((paramBundle.containsKey("text")) && (paramBundle.containsKey("time")))
          {
            Message localMessage = new android/support/v4/app/NotificationCompat$MessagingStyle$Message;
            localMessage.<init>(paramBundle.getCharSequence("text"), paramBundle.getLong("time"), paramBundle.getCharSequence("sender"));
            if ((paramBundle.containsKey("type")) && (paramBundle.containsKey("uri"))) {
              localMessage.setData(paramBundle.getString("type"), (Uri)paramBundle.getParcelable("uri"));
            }
            if (paramBundle.containsKey("extras")) {
              localMessage.getExtras().putAll(paramBundle.getBundle("extras"));
            }
            return localMessage;
          }
          return null;
        }
        catch (ClassCastException paramBundle) {}
        return null;
      }
      
      static List<Message> getMessagesFromBundleArray(Parcelable[] paramArrayOfParcelable)
      {
        ArrayList localArrayList = new ArrayList(paramArrayOfParcelable.length);
        for (int i = 0; i < paramArrayOfParcelable.length; i++) {
          if ((paramArrayOfParcelable[i] instanceof Bundle))
          {
            Message localMessage = getMessageFromBundle((Bundle)paramArrayOfParcelable[i]);
            if (localMessage != null) {
              localArrayList.add(localMessage);
            }
          }
        }
        return localArrayList;
      }
      
      private Bundle toBundle()
      {
        Bundle localBundle = new Bundle();
        Object localObject = this.mText;
        if (localObject != null) {
          localBundle.putCharSequence("text", (CharSequence)localObject);
        }
        localBundle.putLong("time", this.mTimestamp);
        localObject = this.mSender;
        if (localObject != null) {
          localBundle.putCharSequence("sender", (CharSequence)localObject);
        }
        localObject = this.mDataMimeType;
        if (localObject != null) {
          localBundle.putString("type", (String)localObject);
        }
        localObject = this.mDataUri;
        if (localObject != null) {
          localBundle.putParcelable("uri", (Parcelable)localObject);
        }
        localObject = this.mExtras;
        if (localObject != null) {
          localBundle.putBundle("extras", (Bundle)localObject);
        }
        return localBundle;
      }
      
      public String getDataMimeType()
      {
        return this.mDataMimeType;
      }
      
      public Uri getDataUri()
      {
        return this.mDataUri;
      }
      
      public Bundle getExtras()
      {
        return this.mExtras;
      }
      
      public CharSequence getSender()
      {
        return this.mSender;
      }
      
      public CharSequence getText()
      {
        return this.mText;
      }
      
      public long getTimestamp()
      {
        return this.mTimestamp;
      }
      
      public Message setData(String paramString, Uri paramUri)
      {
        this.mDataMimeType = paramString;
        this.mDataUri = paramUri;
        return this;
      }
    }
  }
  
  static class NotificationCompatApi16Impl
    extends NotificationCompat.NotificationCompatBaseImpl
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender)
    {
      Object localObject = new NotificationCompatJellybean.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate, paramBuilder.mUseChronometer, paramBuilder.mPriority, paramBuilder.mSubText, paramBuilder.mLocalOnly, paramBuilder.mExtras, paramBuilder.mGroupKey, paramBuilder.mGroupSummary, paramBuilder.mSortKey, paramBuilder.mContentView, paramBuilder.mBigContentView);
      NotificationCompat.addActionsToBuilder((NotificationBuilderWithActions)localObject, paramBuilder.mActions);
      if (paramBuilder.mStyle != null) {
        paramBuilder.mStyle.apply((NotificationBuilderWithBuilderAccessor)localObject);
      }
      localObject = paramBuilderExtender.build(paramBuilder, (NotificationBuilderWithBuilderAccessor)localObject);
      if (paramBuilder.mStyle != null)
      {
        paramBuilderExtender = NotificationCompat.getExtras((Notification)localObject);
        if (paramBuilderExtender != null) {
          paramBuilder.mStyle.addCompatExtras(paramBuilderExtender);
        }
      }
      return (Notification)localObject;
    }
    
    public NotificationCompat.Action getAction(Notification paramNotification, int paramInt)
    {
      return (NotificationCompat.Action)NotificationCompatJellybean.getAction(paramNotification, paramInt, NotificationCompat.Action.FACTORY, RemoteInput.FACTORY);
    }
    
    public NotificationCompat.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> paramArrayList)
    {
      return (NotificationCompat.Action[])NotificationCompatJellybean.getActionsFromParcelableArrayList(paramArrayList, NotificationCompat.Action.FACTORY, RemoteInput.FACTORY);
    }
    
    public ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompat.Action[] paramArrayOfAction)
    {
      return NotificationCompatJellybean.getParcelableArrayListForActions(paramArrayOfAction);
    }
  }
  
  static class NotificationCompatApi19Impl
    extends NotificationCompat.NotificationCompatApi16Impl
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender)
    {
      NotificationCompatKitKat.Builder localBuilder = new NotificationCompatKitKat.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate, paramBuilder.mShowWhen, paramBuilder.mUseChronometer, paramBuilder.mPriority, paramBuilder.mSubText, paramBuilder.mLocalOnly, paramBuilder.mPeople, paramBuilder.mExtras, paramBuilder.mGroupKey, paramBuilder.mGroupSummary, paramBuilder.mSortKey, paramBuilder.mContentView, paramBuilder.mBigContentView);
      NotificationCompat.addActionsToBuilder(localBuilder, paramBuilder.mActions);
      if (paramBuilder.mStyle != null) {
        paramBuilder.mStyle.apply(localBuilder);
      }
      return paramBuilderExtender.build(paramBuilder, localBuilder);
    }
    
    public NotificationCompat.Action getAction(Notification paramNotification, int paramInt)
    {
      return (NotificationCompat.Action)NotificationCompatKitKat.getAction(paramNotification, paramInt, NotificationCompat.Action.FACTORY, RemoteInput.FACTORY);
    }
  }
  
  static class NotificationCompatApi20Impl
    extends NotificationCompat.NotificationCompatApi19Impl
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender)
    {
      NotificationCompatApi20.Builder localBuilder = new NotificationCompatApi20.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate, paramBuilder.mShowWhen, paramBuilder.mUseChronometer, paramBuilder.mPriority, paramBuilder.mSubText, paramBuilder.mLocalOnly, paramBuilder.mPeople, paramBuilder.mExtras, paramBuilder.mGroupKey, paramBuilder.mGroupSummary, paramBuilder.mSortKey, paramBuilder.mContentView, paramBuilder.mBigContentView, paramBuilder.mGroupAlertBehavior);
      NotificationCompat.addActionsToBuilder(localBuilder, paramBuilder.mActions);
      if (paramBuilder.mStyle != null) {
        paramBuilder.mStyle.apply(localBuilder);
      }
      paramBuilderExtender = paramBuilderExtender.build(paramBuilder, localBuilder);
      if (paramBuilder.mStyle != null) {
        paramBuilder.mStyle.addCompatExtras(NotificationCompat.getExtras(paramBuilderExtender));
      }
      return paramBuilderExtender;
    }
    
    public NotificationCompat.Action getAction(Notification paramNotification, int paramInt)
    {
      return (NotificationCompat.Action)NotificationCompatApi20.getAction(paramNotification, paramInt, NotificationCompat.Action.FACTORY, RemoteInput.FACTORY);
    }
    
    public NotificationCompat.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> paramArrayList)
    {
      return (NotificationCompat.Action[])NotificationCompatApi20.getActionsFromParcelableArrayList(paramArrayList, NotificationCompat.Action.FACTORY, RemoteInput.FACTORY);
    }
    
    public ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompat.Action[] paramArrayOfAction)
    {
      return NotificationCompatApi20.getParcelableArrayListForActions(paramArrayOfAction);
    }
  }
  
  static class NotificationCompatApi21Impl
    extends NotificationCompat.NotificationCompatApi20Impl
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender)
    {
      NotificationCompatApi21.Builder localBuilder = new NotificationCompatApi21.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate, paramBuilder.mShowWhen, paramBuilder.mUseChronometer, paramBuilder.mPriority, paramBuilder.mSubText, paramBuilder.mLocalOnly, paramBuilder.mCategory, paramBuilder.mPeople, paramBuilder.mExtras, paramBuilder.mColor, paramBuilder.mVisibility, paramBuilder.mPublicVersion, paramBuilder.mGroupKey, paramBuilder.mGroupSummary, paramBuilder.mSortKey, paramBuilder.mContentView, paramBuilder.mBigContentView, paramBuilder.mHeadsUpContentView, paramBuilder.mGroupAlertBehavior);
      NotificationCompat.addActionsToBuilder(localBuilder, paramBuilder.mActions);
      if (paramBuilder.mStyle != null) {
        paramBuilder.mStyle.apply(localBuilder);
      }
      paramBuilderExtender = paramBuilderExtender.build(paramBuilder, localBuilder);
      if (paramBuilder.mStyle != null) {
        paramBuilder.mStyle.addCompatExtras(NotificationCompat.getExtras(paramBuilderExtender));
      }
      return paramBuilderExtender;
    }
    
    public Bundle getBundleForUnreadConversation(NotificationCompatBase.UnreadConversation paramUnreadConversation)
    {
      return NotificationCompatApi21.getBundleForUnreadConversation(paramUnreadConversation);
    }
    
    public NotificationCompatBase.UnreadConversation getUnreadConversationFromBundle(Bundle paramBundle, NotificationCompatBase.UnreadConversation.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1)
    {
      return NotificationCompatApi21.getUnreadConversationFromBundle(paramBundle, paramFactory, paramFactory1);
    }
  }
  
  static class NotificationCompatApi24Impl
    extends NotificationCompat.NotificationCompatApi21Impl
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender)
    {
      NotificationCompatApi24.Builder localBuilder = new NotificationCompatApi24.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate, paramBuilder.mShowWhen, paramBuilder.mUseChronometer, paramBuilder.mPriority, paramBuilder.mSubText, paramBuilder.mLocalOnly, paramBuilder.mCategory, paramBuilder.mPeople, paramBuilder.mExtras, paramBuilder.mColor, paramBuilder.mVisibility, paramBuilder.mPublicVersion, paramBuilder.mGroupKey, paramBuilder.mGroupSummary, paramBuilder.mSortKey, paramBuilder.mRemoteInputHistory, paramBuilder.mContentView, paramBuilder.mBigContentView, paramBuilder.mHeadsUpContentView, paramBuilder.mGroupAlertBehavior);
      NotificationCompat.addActionsToBuilder(localBuilder, paramBuilder.mActions);
      if (paramBuilder.mStyle != null) {
        paramBuilder.mStyle.apply(localBuilder);
      }
      paramBuilderExtender = paramBuilderExtender.build(paramBuilder, localBuilder);
      if (paramBuilder.mStyle != null) {
        paramBuilder.mStyle.addCompatExtras(NotificationCompat.getExtras(paramBuilderExtender));
      }
      return paramBuilderExtender;
    }
    
    public NotificationCompat.Action getAction(Notification paramNotification, int paramInt)
    {
      return (NotificationCompat.Action)NotificationCompatApi24.getAction(paramNotification, paramInt, NotificationCompat.Action.FACTORY, RemoteInput.FACTORY);
    }
    
    public NotificationCompat.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> paramArrayList)
    {
      return (NotificationCompat.Action[])NotificationCompatApi24.getActionsFromParcelableArrayList(paramArrayList, NotificationCompat.Action.FACTORY, RemoteInput.FACTORY);
    }
    
    public ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompat.Action[] paramArrayOfAction)
    {
      return NotificationCompatApi24.getParcelableArrayListForActions(paramArrayOfAction);
    }
  }
  
  static class NotificationCompatApi26Impl
    extends NotificationCompat.NotificationCompatApi24Impl
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender)
    {
      NotificationCompatApi26.Builder localBuilder = new NotificationCompatApi26.Builder(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate, paramBuilder.mShowWhen, paramBuilder.mUseChronometer, paramBuilder.mPriority, paramBuilder.mSubText, paramBuilder.mLocalOnly, paramBuilder.mCategory, paramBuilder.mPeople, paramBuilder.mExtras, paramBuilder.mColor, paramBuilder.mVisibility, paramBuilder.mPublicVersion, paramBuilder.mGroupKey, paramBuilder.mGroupSummary, paramBuilder.mSortKey, paramBuilder.mRemoteInputHistory, paramBuilder.mContentView, paramBuilder.mBigContentView, paramBuilder.mHeadsUpContentView, paramBuilder.mChannelId, paramBuilder.mBadgeIcon, paramBuilder.mShortcutId, paramBuilder.mTimeout, paramBuilder.mColorized, paramBuilder.mColorizedSet, paramBuilder.mGroupAlertBehavior);
      NotificationCompat.addActionsToBuilder(localBuilder, paramBuilder.mActions);
      if (paramBuilder.mStyle != null) {
        paramBuilder.mStyle.apply(localBuilder);
      }
      paramBuilderExtender = paramBuilderExtender.build(paramBuilder, localBuilder);
      if (paramBuilder.mStyle != null) {
        paramBuilder.mStyle.addCompatExtras(NotificationCompat.getExtras(paramBuilderExtender));
      }
      return paramBuilderExtender;
    }
  }
  
  static class NotificationCompatBaseImpl
    implements NotificationCompat.NotificationCompatImpl
  {
    public Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender)
    {
      return paramBuilderExtender.build(paramBuilder, new BuilderBase(paramBuilder.mContext, paramBuilder.mNotification, paramBuilder.mContentTitle, paramBuilder.mContentText, paramBuilder.mContentInfo, paramBuilder.mTickerView, paramBuilder.mNumber, paramBuilder.mContentIntent, paramBuilder.mFullScreenIntent, paramBuilder.mLargeIcon, paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate));
    }
    
    public NotificationCompat.Action getAction(Notification paramNotification, int paramInt)
    {
      return null;
    }
    
    public NotificationCompat.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> paramArrayList)
    {
      return null;
    }
    
    public Bundle getBundleForUnreadConversation(NotificationCompatBase.UnreadConversation paramUnreadConversation)
    {
      return null;
    }
    
    public ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompat.Action[] paramArrayOfAction)
    {
      return null;
    }
    
    public NotificationCompatBase.UnreadConversation getUnreadConversationFromBundle(Bundle paramBundle, NotificationCompatBase.UnreadConversation.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1)
    {
      return null;
    }
    
    public static class BuilderBase
      implements NotificationBuilderWithBuilderAccessor
    {
      private Notification.Builder mBuilder;
      
      BuilderBase(Context paramContext, Notification paramNotification, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, RemoteViews paramRemoteViews, int paramInt1, PendingIntent paramPendingIntent1, PendingIntent paramPendingIntent2, Bitmap paramBitmap, int paramInt2, int paramInt3, boolean paramBoolean)
      {
        paramContext = new Notification.Builder(paramContext).setWhen(paramNotification.when).setSmallIcon(paramNotification.icon, paramNotification.iconLevel).setContent(paramNotification.contentView).setTicker(paramNotification.tickerText, paramRemoteViews).setSound(paramNotification.sound, paramNotification.audioStreamType).setVibrate(paramNotification.vibrate).setLights(paramNotification.ledARGB, paramNotification.ledOnMS, paramNotification.ledOffMS);
        int i = paramNotification.flags;
        boolean bool1 = true;
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
        paramContext = paramContext.setAutoCancel(bool2).setDefaults(paramNotification.defaults).setContentTitle(paramCharSequence1).setContentText(paramCharSequence2).setContentInfo(paramCharSequence3).setContentIntent(paramPendingIntent1).setDeleteIntent(paramNotification.deleteIntent);
        if ((paramNotification.flags & 0x80) != 0) {
          bool2 = bool1;
        } else {
          bool2 = false;
        }
        this.mBuilder = paramContext.setFullScreenIntent(paramPendingIntent2, bool2).setLargeIcon(paramBitmap).setNumber(paramInt1).setProgress(paramInt2, paramInt3, paramBoolean);
      }
      
      public Notification build()
      {
        return this.mBuilder.getNotification();
      }
      
      public Notification.Builder getBuilder()
      {
        return this.mBuilder;
      }
    }
  }
  
  static abstract interface NotificationCompatImpl
  {
    public abstract Notification build(NotificationCompat.Builder paramBuilder, NotificationCompat.BuilderExtender paramBuilderExtender);
    
    public abstract NotificationCompat.Action getAction(Notification paramNotification, int paramInt);
    
    public abstract NotificationCompat.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> paramArrayList);
    
    public abstract Bundle getBundleForUnreadConversation(NotificationCompatBase.UnreadConversation paramUnreadConversation);
    
    public abstract ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompat.Action[] paramArrayOfAction);
    
    public abstract NotificationCompatBase.UnreadConversation getUnreadConversationFromBundle(Bundle paramBundle, NotificationCompatBase.UnreadConversation.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface NotificationVisibility {}
  
  public static abstract class Style
  {
    CharSequence mBigContentTitle;
    protected NotificationCompat.Builder mBuilder;
    CharSequence mSummaryText;
    boolean mSummaryTextSet = false;
    
    private int calculateTopPadding()
    {
      Resources localResources = this.mBuilder.mContext.getResources();
      int i = localResources.getDimensionPixelSize(a.b.notification_top_pad);
      int j = localResources.getDimensionPixelSize(a.b.notification_top_pad_large_text);
      float f = (constrain(localResources.getConfiguration().fontScale, 1.0F, 1.3F) - 1.0F) / 0.29999995F;
      return Math.round((1.0F - f) * i + f * j);
    }
    
    private static float constrain(float paramFloat1, float paramFloat2, float paramFloat3)
    {
      if (paramFloat1 >= paramFloat2)
      {
        paramFloat2 = paramFloat1;
        if (paramFloat1 > paramFloat3) {
          paramFloat2 = paramFloat3;
        }
      }
      return paramFloat2;
    }
    
    private Bitmap createColoredBitmap(int paramInt1, int paramInt2, int paramInt3)
    {
      Drawable localDrawable = this.mBuilder.mContext.getResources().getDrawable(paramInt1);
      if (paramInt3 == 0) {
        paramInt1 = localDrawable.getIntrinsicWidth();
      } else {
        paramInt1 = paramInt3;
      }
      int i = paramInt3;
      if (paramInt3 == 0) {
        i = localDrawable.getIntrinsicHeight();
      }
      Bitmap localBitmap = Bitmap.createBitmap(paramInt1, i, Bitmap.Config.ARGB_8888);
      localDrawable.setBounds(0, 0, paramInt1, i);
      if (paramInt2 != 0) {
        localDrawable.mutate().setColorFilter(new PorterDuffColorFilter(paramInt2, PorterDuff.Mode.SRC_IN));
      }
      localDrawable.draw(new Canvas(localBitmap));
      return localBitmap;
    }
    
    private Bitmap createIconWithBackground(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      int i = a.c.notification_icon_background;
      int j = paramInt4;
      if (paramInt4 == 0) {
        j = 0;
      }
      Bitmap localBitmap = createColoredBitmap(i, j, paramInt2);
      Canvas localCanvas = new Canvas(localBitmap);
      Drawable localDrawable = this.mBuilder.mContext.getResources().getDrawable(paramInt1).mutate();
      localDrawable.setFilterBitmap(true);
      paramInt1 = (paramInt2 - paramInt3) / 2;
      paramInt2 = paramInt3 + paramInt1;
      localDrawable.setBounds(paramInt1, paramInt1, paramInt2, paramInt2);
      localDrawable.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_ATOP));
      localDrawable.draw(localCanvas);
      return localBitmap;
    }
    
    private void hideNormalContent(RemoteViews paramRemoteViews)
    {
      paramRemoteViews.setViewVisibility(a.d.title, 8);
      paramRemoteViews.setViewVisibility(a.d.text2, 8);
      paramRemoteViews.setViewVisibility(a.d.text, 8);
    }
    
    public void addCompatExtras(Bundle paramBundle) {}
    
    public void apply(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor) {}
    
    public RemoteViews applyStandardTemplate(boolean paramBoolean1, int paramInt, boolean paramBoolean2)
    {
      Resources localResources = this.mBuilder.mContext.getResources();
      RemoteViews localRemoteViews = new RemoteViews(this.mBuilder.mContext.getPackageName(), paramInt);
      paramInt = this.mBuilder.getPriority();
      int i = 0;
      if (paramInt < -1) {
        paramInt = 1;
      } else {
        paramInt = 0;
      }
      if ((Build.VERSION.SDK_INT >= 16) && (Build.VERSION.SDK_INT < 21)) {
        if (paramInt != 0)
        {
          localRemoteViews.setInt(a.d.notification_background, "setBackgroundResource", a.c.notification_bg_low);
          localRemoteViews.setInt(a.d.icon, "setBackgroundResource", a.c.notification_template_icon_low_bg);
        }
        else
        {
          localRemoteViews.setInt(a.d.notification_background, "setBackgroundResource", a.c.notification_bg);
          localRemoteViews.setInt(a.d.icon, "setBackgroundResource", a.c.notification_template_icon_bg);
        }
      }
      int j;
      Object localObject;
      if (this.mBuilder.mLargeIcon != null)
      {
        if (Build.VERSION.SDK_INT >= 16)
        {
          localRemoteViews.setViewVisibility(a.d.icon, 0);
          localRemoteViews.setImageViewBitmap(a.d.icon, this.mBuilder.mLargeIcon);
        }
        else
        {
          localRemoteViews.setViewVisibility(a.d.icon, 8);
        }
        if ((paramBoolean1) && (this.mBuilder.mNotification.icon != 0))
        {
          j = localResources.getDimensionPixelSize(a.b.notification_right_icon_size);
          paramInt = localResources.getDimensionPixelSize(a.b.notification_small_icon_background_padding);
          if (Build.VERSION.SDK_INT >= 21)
          {
            localObject = createIconWithBackground(this.mBuilder.mNotification.icon, j, j - paramInt * 2, this.mBuilder.getColor());
            localRemoteViews.setImageViewBitmap(a.d.right_icon, (Bitmap)localObject);
          }
          else
          {
            localRemoteViews.setImageViewBitmap(a.d.right_icon, createColoredBitmap(this.mBuilder.mNotification.icon, -1));
          }
          localRemoteViews.setViewVisibility(a.d.right_icon, 0);
        }
      }
      else if ((paramBoolean1) && (this.mBuilder.mNotification.icon != 0))
      {
        localRemoteViews.setViewVisibility(a.d.icon, 0);
        if (Build.VERSION.SDK_INT >= 21)
        {
          paramInt = localResources.getDimensionPixelSize(a.b.notification_large_icon_width);
          j = localResources.getDimensionPixelSize(a.b.notification_big_circle_margin);
          k = localResources.getDimensionPixelSize(a.b.notification_small_icon_size_as_large);
          localObject = createIconWithBackground(this.mBuilder.mNotification.icon, paramInt - j, k, this.mBuilder.getColor());
          localRemoteViews.setImageViewBitmap(a.d.icon, (Bitmap)localObject);
        }
        else
        {
          localRemoteViews.setImageViewBitmap(a.d.icon, createColoredBitmap(this.mBuilder.mNotification.icon, -1));
        }
      }
      if (this.mBuilder.mContentTitle != null) {
        localRemoteViews.setTextViewText(a.d.title, this.mBuilder.mContentTitle);
      }
      if (this.mBuilder.mContentText != null)
      {
        localRemoteViews.setTextViewText(a.d.text, this.mBuilder.mContentText);
        j = 1;
      }
      else
      {
        j = 0;
      }
      if ((Build.VERSION.SDK_INT < 21) && (this.mBuilder.mLargeIcon != null)) {
        paramInt = 1;
      } else {
        paramInt = 0;
      }
      if (this.mBuilder.mContentInfo != null)
      {
        localRemoteViews.setTextViewText(a.d.info, this.mBuilder.mContentInfo);
        localRemoteViews.setViewVisibility(a.d.info, 0);
        j = 1;
        paramInt = 1;
      }
      else if (this.mBuilder.mNumber > 0)
      {
        paramInt = localResources.getInteger(a.e.status_bar_notification_info_maxnum);
        if (this.mBuilder.mNumber > paramInt)
        {
          localRemoteViews.setTextViewText(a.d.info, localResources.getString(a.g.status_bar_notification_info_overflow));
        }
        else
        {
          localObject = NumberFormat.getIntegerInstance();
          localRemoteViews.setTextViewText(a.d.info, ((NumberFormat)localObject).format(this.mBuilder.mNumber));
        }
        localRemoteViews.setViewVisibility(a.d.info, 0);
        j = 1;
        paramInt = 1;
      }
      else
      {
        localRemoteViews.setViewVisibility(a.d.info, 8);
      }
      if ((this.mBuilder.mSubText != null) && (Build.VERSION.SDK_INT >= 16))
      {
        localRemoteViews.setTextViewText(a.d.text, this.mBuilder.mSubText);
        if (this.mBuilder.mContentText != null)
        {
          localRemoteViews.setTextViewText(a.d.text2, this.mBuilder.mContentText);
          localRemoteViews.setViewVisibility(a.d.text2, 0);
          k = 1;
        }
        else
        {
          localRemoteViews.setViewVisibility(a.d.text2, 8);
        }
      }
      else
      {
        k = 0;
      }
      if ((k != 0) && (Build.VERSION.SDK_INT >= 16))
      {
        if (paramBoolean2)
        {
          float f = localResources.getDimensionPixelSize(a.b.notification_subtext_size);
          localRemoteViews.setTextViewTextSize(a.d.text, 0, f);
        }
        localRemoteViews.setViewPadding(a.d.line1, 0, 0, 0, 0);
      }
      if (this.mBuilder.getWhenIfShowing() != 0L)
      {
        if ((this.mBuilder.mUseChronometer) && (Build.VERSION.SDK_INT >= 16))
        {
          localRemoteViews.setViewVisibility(a.d.chronometer, 0);
          localRemoteViews.setLong(a.d.chronometer, "setBase", this.mBuilder.getWhenIfShowing() + (SystemClock.elapsedRealtime() - System.currentTimeMillis()));
          localRemoteViews.setBoolean(a.d.chronometer, "setStarted", true);
        }
        else
        {
          localRemoteViews.setViewVisibility(a.d.time, 0);
          localRemoteViews.setLong(a.d.time, "setTime", this.mBuilder.getWhenIfShowing());
        }
        paramInt = 1;
      }
      int k = a.d.right_side;
      if (paramInt != 0) {
        paramInt = 0;
      } else {
        paramInt = 8;
      }
      localRemoteViews.setViewVisibility(k, paramInt);
      k = a.d.line3;
      if (j != 0) {
        paramInt = i;
      } else {
        paramInt = 8;
      }
      localRemoteViews.setViewVisibility(k, paramInt);
      return localRemoteViews;
    }
    
    public Notification build()
    {
      Object localObject = this.mBuilder;
      if (localObject != null) {
        localObject = ((NotificationCompat.Builder)localObject).build();
      } else {
        localObject = null;
      }
      return (Notification)localObject;
    }
    
    public void buildIntoRemoteViews(RemoteViews paramRemoteViews1, RemoteViews paramRemoteViews2)
    {
      hideNormalContent(paramRemoteViews1);
      paramRemoteViews1.removeAllViews(a.d.notification_main_column);
      paramRemoteViews1.addView(a.d.notification_main_column, paramRemoteViews2.clone());
      paramRemoteViews1.setViewVisibility(a.d.notification_main_column, 0);
      if (Build.VERSION.SDK_INT >= 21) {
        paramRemoteViews1.setViewPadding(a.d.notification_main_column_container, 0, calculateTopPadding(), 0, 0);
      }
    }
    
    public Bitmap createColoredBitmap(int paramInt1, int paramInt2)
    {
      return createColoredBitmap(paramInt1, paramInt2, 0);
    }
    
    public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      return null;
    }
    
    public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      return null;
    }
    
    public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor)
    {
      return null;
    }
    
    protected void restoreFromCompatExtras(Bundle paramBundle) {}
    
    public void setBuilder(NotificationCompat.Builder paramBuilder)
    {
      if (this.mBuilder != paramBuilder)
      {
        this.mBuilder = paramBuilder;
        paramBuilder = this.mBuilder;
        if (paramBuilder != null) {
          paramBuilder.setStyle(this);
        }
      }
    }
  }
  
  public static final class WearableExtender
    implements NotificationCompat.Extender
  {
    private static final int DEFAULT_CONTENT_ICON_GRAVITY = 8388613;
    private static final int DEFAULT_FLAGS = 1;
    private static final int DEFAULT_GRAVITY = 80;
    private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
    private static final int FLAG_BIG_PICTURE_AMBIENT = 32;
    private static final int FLAG_CONTENT_INTENT_AVAILABLE_OFFLINE = 1;
    private static final int FLAG_HINT_AVOID_BACKGROUND_CLIPPING = 16;
    private static final int FLAG_HINT_CONTENT_INTENT_LAUNCHES_ACTIVITY = 64;
    private static final int FLAG_HINT_HIDE_ICON = 2;
    private static final int FLAG_HINT_SHOW_BACKGROUND_ONLY = 4;
    private static final int FLAG_START_SCROLL_BOTTOM = 8;
    private static final String KEY_ACTIONS = "actions";
    private static final String KEY_BACKGROUND = "background";
    private static final String KEY_BRIDGE_TAG = "bridgeTag";
    private static final String KEY_CONTENT_ACTION_INDEX = "contentActionIndex";
    private static final String KEY_CONTENT_ICON = "contentIcon";
    private static final String KEY_CONTENT_ICON_GRAVITY = "contentIconGravity";
    private static final String KEY_CUSTOM_CONTENT_HEIGHT = "customContentHeight";
    private static final String KEY_CUSTOM_SIZE_PRESET = "customSizePreset";
    private static final String KEY_DISMISSAL_ID = "dismissalId";
    private static final String KEY_DISPLAY_INTENT = "displayIntent";
    private static final String KEY_FLAGS = "flags";
    private static final String KEY_GRAVITY = "gravity";
    private static final String KEY_HINT_SCREEN_TIMEOUT = "hintScreenTimeout";
    private static final String KEY_PAGES = "pages";
    public static final int SCREEN_TIMEOUT_LONG = -1;
    public static final int SCREEN_TIMEOUT_SHORT = 0;
    public static final int SIZE_DEFAULT = 0;
    public static final int SIZE_FULL_SCREEN = 5;
    public static final int SIZE_LARGE = 4;
    public static final int SIZE_MEDIUM = 3;
    public static final int SIZE_SMALL = 2;
    public static final int SIZE_XSMALL = 1;
    public static final int UNSET_ACTION_INDEX = -1;
    private ArrayList<NotificationCompat.Action> mActions = new ArrayList();
    private Bitmap mBackground;
    private String mBridgeTag;
    private int mContentActionIndex = -1;
    private int mContentIcon;
    private int mContentIconGravity = 8388613;
    private int mCustomContentHeight;
    private int mCustomSizePreset = 0;
    private String mDismissalId;
    private PendingIntent mDisplayIntent;
    private int mFlags = 1;
    private int mGravity = 80;
    private int mHintScreenTimeout;
    private ArrayList<Notification> mPages = new ArrayList();
    
    public WearableExtender() {}
    
    public WearableExtender(Notification paramNotification)
    {
      paramNotification = NotificationCompat.getExtras(paramNotification);
      if (paramNotification != null) {
        paramNotification = paramNotification.getBundle("android.wearable.EXTENSIONS");
      } else {
        paramNotification = null;
      }
      if (paramNotification != null)
      {
        Object localObject = NotificationCompat.IMPL.getActionsFromParcelableArrayList(paramNotification.getParcelableArrayList("actions"));
        if (localObject != null) {
          Collections.addAll(this.mActions, (Object[])localObject);
        }
        this.mFlags = paramNotification.getInt("flags", 1);
        this.mDisplayIntent = ((PendingIntent)paramNotification.getParcelable("displayIntent"));
        localObject = NotificationCompat.getNotificationArrayFromBundle(paramNotification, "pages");
        if (localObject != null) {
          Collections.addAll(this.mPages, (Object[])localObject);
        }
        this.mBackground = ((Bitmap)paramNotification.getParcelable("background"));
        this.mContentIcon = paramNotification.getInt("contentIcon");
        this.mContentIconGravity = paramNotification.getInt("contentIconGravity", 8388613);
        this.mContentActionIndex = paramNotification.getInt("contentActionIndex", -1);
        this.mCustomSizePreset = paramNotification.getInt("customSizePreset", 0);
        this.mCustomContentHeight = paramNotification.getInt("customContentHeight");
        this.mGravity = paramNotification.getInt("gravity", 80);
        this.mHintScreenTimeout = paramNotification.getInt("hintScreenTimeout");
        this.mDismissalId = paramNotification.getString("dismissalId");
        this.mBridgeTag = paramNotification.getString("bridgeTag");
      }
    }
    
    private void setFlag(int paramInt, boolean paramBoolean)
    {
      if (paramBoolean) {
        this.mFlags = (paramInt | this.mFlags);
      } else {
        this.mFlags = ((paramInt ^ 0xFFFFFFFF) & this.mFlags);
      }
    }
    
    public WearableExtender addAction(NotificationCompat.Action paramAction)
    {
      this.mActions.add(paramAction);
      return this;
    }
    
    public WearableExtender addActions(List<NotificationCompat.Action> paramList)
    {
      this.mActions.addAll(paramList);
      return this;
    }
    
    public WearableExtender addPage(Notification paramNotification)
    {
      this.mPages.add(paramNotification);
      return this;
    }
    
    public WearableExtender addPages(List<Notification> paramList)
    {
      this.mPages.addAll(paramList);
      return this;
    }
    
    public WearableExtender clearActions()
    {
      this.mActions.clear();
      return this;
    }
    
    public WearableExtender clearPages()
    {
      this.mPages.clear();
      return this;
    }
    
    public WearableExtender clone()
    {
      WearableExtender localWearableExtender = new WearableExtender();
      localWearableExtender.mActions = new ArrayList(this.mActions);
      localWearableExtender.mFlags = this.mFlags;
      localWearableExtender.mDisplayIntent = this.mDisplayIntent;
      localWearableExtender.mPages = new ArrayList(this.mPages);
      localWearableExtender.mBackground = this.mBackground;
      localWearableExtender.mContentIcon = this.mContentIcon;
      localWearableExtender.mContentIconGravity = this.mContentIconGravity;
      localWearableExtender.mContentActionIndex = this.mContentActionIndex;
      localWearableExtender.mCustomSizePreset = this.mCustomSizePreset;
      localWearableExtender.mCustomContentHeight = this.mCustomContentHeight;
      localWearableExtender.mGravity = this.mGravity;
      localWearableExtender.mHintScreenTimeout = this.mHintScreenTimeout;
      localWearableExtender.mDismissalId = this.mDismissalId;
      localWearableExtender.mBridgeTag = this.mBridgeTag;
      return localWearableExtender;
    }
    
    public NotificationCompat.Builder extend(NotificationCompat.Builder paramBuilder)
    {
      Bundle localBundle = new Bundle();
      if (!this.mActions.isEmpty())
      {
        localObject = NotificationCompat.IMPL;
        ArrayList localArrayList = this.mActions;
        localBundle.putParcelableArrayList("actions", ((NotificationCompat.NotificationCompatImpl)localObject).getParcelableArrayListForActions((NotificationCompat.Action[])localArrayList.toArray(new NotificationCompat.Action[localArrayList.size()])));
      }
      int i = this.mFlags;
      if (i != 1) {
        localBundle.putInt("flags", i);
      }
      Object localObject = this.mDisplayIntent;
      if (localObject != null) {
        localBundle.putParcelable("displayIntent", (Parcelable)localObject);
      }
      if (!this.mPages.isEmpty())
      {
        localObject = this.mPages;
        localBundle.putParcelableArray("pages", (Parcelable[])((ArrayList)localObject).toArray(new Notification[((ArrayList)localObject).size()]));
      }
      localObject = this.mBackground;
      if (localObject != null) {
        localBundle.putParcelable("background", (Parcelable)localObject);
      }
      i = this.mContentIcon;
      if (i != 0) {
        localBundle.putInt("contentIcon", i);
      }
      i = this.mContentIconGravity;
      if (i != 8388613) {
        localBundle.putInt("contentIconGravity", i);
      }
      i = this.mContentActionIndex;
      if (i != -1) {
        localBundle.putInt("contentActionIndex", i);
      }
      i = this.mCustomSizePreset;
      if (i != 0) {
        localBundle.putInt("customSizePreset", i);
      }
      i = this.mCustomContentHeight;
      if (i != 0) {
        localBundle.putInt("customContentHeight", i);
      }
      i = this.mGravity;
      if (i != 80) {
        localBundle.putInt("gravity", i);
      }
      i = this.mHintScreenTimeout;
      if (i != 0) {
        localBundle.putInt("hintScreenTimeout", i);
      }
      localObject = this.mDismissalId;
      if (localObject != null) {
        localBundle.putString("dismissalId", (String)localObject);
      }
      localObject = this.mBridgeTag;
      if (localObject != null) {
        localBundle.putString("bridgeTag", (String)localObject);
      }
      paramBuilder.getExtras().putBundle("android.wearable.EXTENSIONS", localBundle);
      return paramBuilder;
    }
    
    public List<NotificationCompat.Action> getActions()
    {
      return this.mActions;
    }
    
    public Bitmap getBackground()
    {
      return this.mBackground;
    }
    
    public String getBridgeTag()
    {
      return this.mBridgeTag;
    }
    
    public int getContentAction()
    {
      return this.mContentActionIndex;
    }
    
    public int getContentIcon()
    {
      return this.mContentIcon;
    }
    
    public int getContentIconGravity()
    {
      return this.mContentIconGravity;
    }
    
    public boolean getContentIntentAvailableOffline()
    {
      int i = this.mFlags;
      boolean bool = true;
      if ((i & 0x1) == 0) {
        bool = false;
      }
      return bool;
    }
    
    public int getCustomContentHeight()
    {
      return this.mCustomContentHeight;
    }
    
    public int getCustomSizePreset()
    {
      return this.mCustomSizePreset;
    }
    
    public String getDismissalId()
    {
      return this.mDismissalId;
    }
    
    public PendingIntent getDisplayIntent()
    {
      return this.mDisplayIntent;
    }
    
    public int getGravity()
    {
      return this.mGravity;
    }
    
    public boolean getHintAmbientBigPicture()
    {
      boolean bool;
      if ((this.mFlags & 0x20) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean getHintAvoidBackgroundClipping()
    {
      boolean bool;
      if ((this.mFlags & 0x10) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean getHintContentIntentLaunchesActivity()
    {
      boolean bool;
      if ((this.mFlags & 0x40) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean getHintHideIcon()
    {
      boolean bool;
      if ((this.mFlags & 0x2) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public int getHintScreenTimeout()
    {
      return this.mHintScreenTimeout;
    }
    
    public boolean getHintShowBackgroundOnly()
    {
      boolean bool;
      if ((this.mFlags & 0x4) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public List<Notification> getPages()
    {
      return this.mPages;
    }
    
    public boolean getStartScrollBottom()
    {
      boolean bool;
      if ((this.mFlags & 0x8) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public WearableExtender setBackground(Bitmap paramBitmap)
    {
      this.mBackground = paramBitmap;
      return this;
    }
    
    public WearableExtender setBridgeTag(String paramString)
    {
      this.mBridgeTag = paramString;
      return this;
    }
    
    public WearableExtender setContentAction(int paramInt)
    {
      this.mContentActionIndex = paramInt;
      return this;
    }
    
    public WearableExtender setContentIcon(int paramInt)
    {
      this.mContentIcon = paramInt;
      return this;
    }
    
    public WearableExtender setContentIconGravity(int paramInt)
    {
      this.mContentIconGravity = paramInt;
      return this;
    }
    
    public WearableExtender setContentIntentAvailableOffline(boolean paramBoolean)
    {
      setFlag(1, paramBoolean);
      return this;
    }
    
    public WearableExtender setCustomContentHeight(int paramInt)
    {
      this.mCustomContentHeight = paramInt;
      return this;
    }
    
    public WearableExtender setCustomSizePreset(int paramInt)
    {
      this.mCustomSizePreset = paramInt;
      return this;
    }
    
    public WearableExtender setDismissalId(String paramString)
    {
      this.mDismissalId = paramString;
      return this;
    }
    
    public WearableExtender setDisplayIntent(PendingIntent paramPendingIntent)
    {
      this.mDisplayIntent = paramPendingIntent;
      return this;
    }
    
    public WearableExtender setGravity(int paramInt)
    {
      this.mGravity = paramInt;
      return this;
    }
    
    public WearableExtender setHintAmbientBigPicture(boolean paramBoolean)
    {
      setFlag(32, paramBoolean);
      return this;
    }
    
    public WearableExtender setHintAvoidBackgroundClipping(boolean paramBoolean)
    {
      setFlag(16, paramBoolean);
      return this;
    }
    
    public WearableExtender setHintContentIntentLaunchesActivity(boolean paramBoolean)
    {
      setFlag(64, paramBoolean);
      return this;
    }
    
    public WearableExtender setHintHideIcon(boolean paramBoolean)
    {
      setFlag(2, paramBoolean);
      return this;
    }
    
    public WearableExtender setHintScreenTimeout(int paramInt)
    {
      this.mHintScreenTimeout = paramInt;
      return this;
    }
    
    public WearableExtender setHintShowBackgroundOnly(boolean paramBoolean)
    {
      setFlag(4, paramBoolean);
      return this;
    }
    
    public WearableExtender setStartScrollBottom(boolean paramBoolean)
    {
      setFlag(8, paramBoolean);
      return this;
    }
  }
}


/* Location:              ~/android/support/v4/app/NotificationCompat.class
 *
 * Reversed by:           J
 */