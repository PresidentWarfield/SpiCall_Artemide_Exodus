package android.support.v4.content;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build.VERSION;

public final class IntentCompat
{
  @Deprecated
  public static final String ACTION_EXTERNAL_APPLICATIONS_AVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE";
  @Deprecated
  public static final String ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE";
  public static final String CATEGORY_LEANBACK_LAUNCHER = "android.intent.category.LEANBACK_LAUNCHER";
  @Deprecated
  public static final String EXTRA_CHANGED_PACKAGE_LIST = "android.intent.extra.changed_package_list";
  @Deprecated
  public static final String EXTRA_CHANGED_UID_LIST = "android.intent.extra.changed_uid_list";
  public static final String EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT";
  public static final String EXTRA_START_PLAYBACK = "android.intent.extra.START_PLAYBACK";
  @Deprecated
  public static final int FLAG_ACTIVITY_CLEAR_TASK = 32768;
  @Deprecated
  public static final int FLAG_ACTIVITY_TASK_ON_HOME = 16384;
  private static final IntentCompatBaseImpl IMPL;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 15) {
      IMPL = new IntentCompatApi15Impl();
    } else {
      IMPL = new IntentCompatBaseImpl();
    }
  }
  
  @Deprecated
  public static Intent makeMainActivity(ComponentName paramComponentName)
  {
    return Intent.makeMainActivity(paramComponentName);
  }
  
  public static Intent makeMainSelectorActivity(String paramString1, String paramString2)
  {
    return IMPL.makeMainSelectorActivity(paramString1, paramString2);
  }
  
  @Deprecated
  public static Intent makeRestartActivityTask(ComponentName paramComponentName)
  {
    return Intent.makeRestartActivityTask(paramComponentName);
  }
  
  static class IntentCompatApi15Impl
    extends IntentCompat.IntentCompatBaseImpl
  {
    public Intent makeMainSelectorActivity(String paramString1, String paramString2)
    {
      return Intent.makeMainSelectorActivity(paramString1, paramString2);
    }
  }
  
  static class IntentCompatBaseImpl
  {
    public Intent makeMainSelectorActivity(String paramString1, String paramString2)
    {
      paramString1 = new Intent(paramString1);
      paramString1.addCategory(paramString2);
      return paramString1;
    }
  }
}


/* Location:              ~/android/support/v4/content/IntentCompat.class
 *
 * Reversed by:           J
 */