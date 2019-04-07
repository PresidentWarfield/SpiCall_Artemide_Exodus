package com.crashlytics.android.core;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

class ManifestUnityVersionProvider
  implements UnityVersionProvider
{
  static final String FABRIC_UNITY_CRASHLYTICS_VERSION_KEY = "io.fabric.unity.crashlytics.version";
  private final Context context;
  private final String packageName;
  
  public ManifestUnityVersionProvider(Context paramContext, String paramString)
  {
    this.context = paramContext;
    this.packageName = paramString;
  }
  
  public String getUnityVersion()
  {
    Object localObject1 = this.context.getPackageManager();
    localObject3 = null;
    try
    {
      Bundle localBundle = ((PackageManager)localObject1).getApplicationInfo(this.packageName, 128).metaData;
      localObject1 = localObject3;
      if (localBundle != null) {
        localObject1 = localBundle.getString("io.fabric.unity.crashlytics.version");
      }
    }
    catch (Exception localException)
    {
      for (;;)
      {
        Object localObject2 = localObject3;
      }
    }
    return (String)localObject1;
  }
}


/* Location:              ~/com/crashlytics/android/core/ManifestUnityVersionProvider.class
 *
 * Reversed by:           J
 */