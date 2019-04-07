package android.support.v7.app;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import java.util.Calendar;

class s
{
  private static s a;
  private final Context b;
  private final LocationManager c;
  private final a d = new a();
  
  s(Context paramContext, LocationManager paramLocationManager)
  {
    this.b = paramContext;
    this.c = paramLocationManager;
  }
  
  private Location a(String paramString)
  {
    LocationManager localLocationManager = this.c;
    if (localLocationManager != null) {
      try
      {
        if (localLocationManager.isProviderEnabled(paramString))
        {
          paramString = this.c.getLastKnownLocation(paramString);
          return paramString;
        }
      }
      catch (Exception paramString)
      {
        Log.d("TwilightManager", "Failed to get last known location", paramString);
      }
    }
    return null;
  }
  
  static s a(Context paramContext)
  {
    if (a == null)
    {
      paramContext = paramContext.getApplicationContext();
      a = new s(paramContext, (LocationManager)paramContext.getSystemService("location"));
    }
    return a;
  }
  
  private void a(Location paramLocation)
  {
    a locala = this.d;
    long l1 = System.currentTimeMillis();
    r localr = r.a();
    localr.a(l1 - 86400000L, paramLocation.getLatitude(), paramLocation.getLongitude());
    long l2 = localr.a;
    localr.a(l1, paramLocation.getLatitude(), paramLocation.getLongitude());
    boolean bool;
    if (localr.c == 1) {
      bool = true;
    } else {
      bool = false;
    }
    long l3 = localr.b;
    long l4 = localr.a;
    localr.a(86400000L + l1, paramLocation.getLatitude(), paramLocation.getLongitude());
    long l5 = localr.b;
    if ((l3 != -1L) && (l4 != -1L))
    {
      if (l1 > l4) {
        l1 = 0L + l5;
      } else if (l1 > l3) {
        l1 = 0L + l4;
      } else {
        l1 = 0L + l3;
      }
      l1 += 60000L;
    }
    else
    {
      l1 = 43200000L + l1;
    }
    locala.a = bool;
    locala.b = l2;
    locala.c = l3;
    locala.d = l4;
    locala.e = l5;
    locala.f = l1;
  }
  
  private Location b()
  {
    int i = PermissionChecker.checkSelfPermission(this.b, "android.permission.ACCESS_COARSE_LOCATION");
    Location localLocation1 = null;
    Location localLocation2;
    if (i == 0) {
      localLocation2 = a("network");
    } else {
      localLocation2 = null;
    }
    if (PermissionChecker.checkSelfPermission(this.b, "android.permission.ACCESS_FINE_LOCATION") == 0) {
      localLocation1 = a("gps");
    }
    if ((localLocation1 != null) && (localLocation2 != null))
    {
      Location localLocation3 = localLocation2;
      if (localLocation1.getTime() > localLocation2.getTime()) {
        localLocation3 = localLocation1;
      }
      return localLocation3;
    }
    if (localLocation1 != null) {
      localLocation2 = localLocation1;
    }
    return localLocation2;
  }
  
  private boolean c()
  {
    a locala = this.d;
    boolean bool;
    if ((locala != null) && (locala.f > System.currentTimeMillis())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean a()
  {
    a locala = this.d;
    if (c()) {
      return locala.a;
    }
    Location localLocation = b();
    if (localLocation != null)
    {
      a(localLocation);
      return locala.a;
    }
    Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
    int i = Calendar.getInstance().get(11);
    boolean bool;
    if ((i >= 6) && (i < 22)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static class a
  {
    boolean a;
    long b;
    long c;
    long d;
    long e;
    long f;
  }
}


/* Location:              ~/android/support/v7/app/s.class
 *
 * Reversed by:           J
 */