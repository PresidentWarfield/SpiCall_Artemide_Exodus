package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;

public final class NavUtils
{
  public static final String PARENT_ACTIVITY = "android.support.PARENT_ACTIVITY";
  private static final String TAG = "NavUtils";
  
  public static Intent getParentActivityIntent(Activity paramActivity)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      localObject = paramActivity.getParentActivityIntent();
      if (localObject != null) {
        return (Intent)localObject;
      }
    }
    Object localObject = getParentActivityName(paramActivity);
    if (localObject == null) {
      return null;
    }
    ComponentName localComponentName = new ComponentName(paramActivity, (String)localObject);
    try
    {
      if (getParentActivityName(paramActivity, localComponentName) == null)
      {
        paramActivity = Intent.makeMainActivity(localComponentName);
      }
      else
      {
        paramActivity = new android/content/Intent;
        paramActivity.<init>();
        paramActivity = paramActivity.setComponent(localComponentName);
      }
      return paramActivity;
    }
    catch (PackageManager.NameNotFoundException paramActivity)
    {
      paramActivity = new StringBuilder();
      paramActivity.append("getParentActivityIntent: bad parentActivityName '");
      paramActivity.append((String)localObject);
      paramActivity.append("' in manifest");
      Log.e("NavUtils", paramActivity.toString());
    }
    return null;
  }
  
  public static Intent getParentActivityIntent(Context paramContext, ComponentName paramComponentName)
  {
    String str = getParentActivityName(paramContext, paramComponentName);
    if (str == null) {
      return null;
    }
    paramComponentName = new ComponentName(paramComponentName.getPackageName(), str);
    if (getParentActivityName(paramContext, paramComponentName) == null) {
      paramContext = Intent.makeMainActivity(paramComponentName);
    } else {
      paramContext = new Intent().setComponent(paramComponentName);
    }
    return paramContext;
  }
  
  public static Intent getParentActivityIntent(Context paramContext, Class<?> paramClass)
  {
    paramClass = getParentActivityName(paramContext, new ComponentName(paramContext, paramClass));
    if (paramClass == null) {
      return null;
    }
    paramClass = new ComponentName(paramContext, paramClass);
    if (getParentActivityName(paramContext, paramClass) == null) {
      paramContext = Intent.makeMainActivity(paramClass);
    } else {
      paramContext = new Intent().setComponent(paramClass);
    }
    return paramContext;
  }
  
  public static String getParentActivityName(Activity paramActivity)
  {
    try
    {
      paramActivity = getParentActivityName(paramActivity, paramActivity.getComponentName());
      return paramActivity;
    }
    catch (PackageManager.NameNotFoundException paramActivity)
    {
      throw new IllegalArgumentException(paramActivity);
    }
  }
  
  public static String getParentActivityName(Context paramContext, ComponentName paramComponentName)
  {
    paramComponentName = paramContext.getPackageManager().getActivityInfo(paramComponentName, 128);
    if (Build.VERSION.SDK_INT >= 16)
    {
      str = paramComponentName.parentActivityName;
      if (str != null) {
        return str;
      }
    }
    if (paramComponentName.metaData == null) {
      return null;
    }
    String str = paramComponentName.metaData.getString("android.support.PARENT_ACTIVITY");
    if (str == null) {
      return null;
    }
    paramComponentName = str;
    if (str.charAt(0) == '.')
    {
      paramComponentName = new StringBuilder();
      paramComponentName.append(paramContext.getPackageName());
      paramComponentName.append(str);
      paramComponentName = paramComponentName.toString();
    }
    return paramComponentName;
  }
  
  public static void navigateUpFromSameTask(Activity paramActivity)
  {
    Object localObject = getParentActivityIntent(paramActivity);
    if (localObject != null)
    {
      navigateUpTo(paramActivity, (Intent)localObject);
      return;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Activity ");
    ((StringBuilder)localObject).append(paramActivity.getClass().getSimpleName());
    ((StringBuilder)localObject).append(" does not have a parent activity name specified.");
    ((StringBuilder)localObject).append(" (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data> ");
    ((StringBuilder)localObject).append(" element in your manifest?)");
    throw new IllegalArgumentException(((StringBuilder)localObject).toString());
  }
  
  public static void navigateUpTo(Activity paramActivity, Intent paramIntent)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      paramActivity.navigateUpTo(paramIntent);
    }
    else
    {
      paramIntent.addFlags(67108864);
      paramActivity.startActivity(paramIntent);
      paramActivity.finish();
    }
  }
  
  public static boolean shouldUpRecreateTask(Activity paramActivity, Intent paramIntent)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return paramActivity.shouldUpRecreateTask(paramIntent);
    }
    paramActivity = paramActivity.getIntent().getAction();
    boolean bool;
    if ((paramActivity != null) && (!paramActivity.equals("android.intent.action.MAIN"))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:              ~/android/support/v4/app/NavUtils.class
 *
 * Reversed by:           J
 */