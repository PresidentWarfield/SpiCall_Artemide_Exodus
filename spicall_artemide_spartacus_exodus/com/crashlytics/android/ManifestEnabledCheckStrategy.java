package com.crashlytics.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

class ManifestEnabledCheckStrategy
  implements CrashlyticsInitProvider.EnabledCheckStrategy
{
  public boolean isCrashlyticsEnabled(Context paramContext)
  {
    boolean bool1 = true;
    try
    {
      String str = paramContext.getPackageName();
      paramContext = paramContext.getPackageManager().getApplicationInfo(str, 128).metaData;
      boolean bool2 = bool1;
      if (paramContext != null)
      {
        bool2 = paramContext.getBoolean("firebase_crashlytics_collection_enabled", true);
        if (bool2) {
          bool2 = bool1;
        } else {
          bool2 = false;
        }
      }
      return bool2;
    }
    catch (PackageManager.NameNotFoundException paramContext) {}
    return true;
  }
}


/* Location:              ~/com/crashlytics/android/ManifestEnabledCheckStrategy.class
 *
 * Reversed by:           J
 */