package android.support.v4.content.res;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;

public final class ConfigurationHelper
{
  public static int getDensityDpi(Resources paramResources)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return paramResources.getConfiguration().densityDpi;
    }
    return paramResources.getDisplayMetrics().densityDpi;
  }
  
  @Deprecated
  public static int getScreenHeightDp(Resources paramResources)
  {
    return paramResources.getConfiguration().screenHeightDp;
  }
  
  @Deprecated
  public static int getScreenWidthDp(Resources paramResources)
  {
    return paramResources.getConfiguration().screenWidthDp;
  }
  
  @Deprecated
  public static int getSmallestScreenWidthDp(Resources paramResources)
  {
    return paramResources.getConfiguration().smallestScreenWidthDp;
  }
}


/* Location:              ~/android/support/v4/content/res/ConfigurationHelper.class
 *
 * Reversed by:           J
 */