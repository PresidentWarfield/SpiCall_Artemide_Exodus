package io.fabric.sdk.android.services.b;

import android.content.Context;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.lang.reflect.Method;

class d
  implements f
{
  private final Context a;
  
  public d(Context paramContext)
  {
    this.a = paramContext.getApplicationContext();
  }
  
  private String b()
  {
    try
    {
      String str = (String)Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info").getMethod("getId", new Class[0]).invoke(d(), new Object[0]);
      return str;
    }
    catch (Exception localException)
    {
      c.g().d("Fabric", "Could not call getId on com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
    }
    return null;
  }
  
  private boolean c()
  {
    try
    {
      boolean bool = ((Boolean)Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info").getMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(d(), new Object[0])).booleanValue();
      return bool;
    }
    catch (Exception localException)
    {
      c.g().d("Fabric", "Could not call isLimitAdTrackingEnabled on com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
    }
    return false;
  }
  
  private Object d()
  {
    try
    {
      Object localObject = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", new Class[] { Context.class }).invoke(null, new Object[] { this.a });
      return localObject;
    }
    catch (Exception localException)
    {
      c.g().d("Fabric", "Could not call getAdvertisingIdInfo on com.google.android.gms.ads.identifier.AdvertisingIdClient");
    }
    return null;
  }
  
  public b a()
  {
    if (a(this.a)) {
      return new b(b(), c());
    }
    return null;
  }
  
  boolean a(Context paramContext)
  {
    boolean bool = false;
    try
    {
      int i = ((Integer)Class.forName("com.google.android.gms.common.GooglePlayServicesUtil").getMethod("isGooglePlayServicesAvailable", new Class[] { Context.class }).invoke(null, new Object[] { paramContext })).intValue();
      if (i == 0) {
        bool = true;
      }
      return bool;
    }
    catch (Exception paramContext) {}
    return false;
  }
}


/* Location:              ~/io/fabric/sdk/android/services/b/d.class
 *
 * Reversed by:           J
 */