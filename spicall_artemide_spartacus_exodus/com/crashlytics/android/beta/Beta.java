package com.crashlytics.android.beta;

import io.fabric.sdk.android.c;
import io.fabric.sdk.android.h;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.m;
import io.fabric.sdk.android.services.b.r.a;
import java.util.Collections;
import java.util.Map;

public class Beta
  extends h<Boolean>
  implements m
{
  public static final String TAG = "Beta";
  
  public static Beta getInstance()
  {
    return (Beta)c.a(Beta.class);
  }
  
  protected Boolean doInBackground()
  {
    c.g().a("Beta", "Beta kit initializing...");
    return Boolean.valueOf(true);
  }
  
  public Map<r.a, String> getDeviceIdentifiers()
  {
    return Collections.emptyMap();
  }
  
  public String getIdentifier()
  {
    return "com.crashlytics.sdk.android:beta";
  }
  
  public String getVersion()
  {
    return "1.2.10.27";
  }
}


/* Location:              ~/com/crashlytics/android/beta/Beta.class
 *
 * Reversed by:           J
 */