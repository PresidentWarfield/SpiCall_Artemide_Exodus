package com.app.system.mqtt;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.app.system.common.service.EventsAndReceiveService;

public class MqttKeepAlive
  extends BroadcastReceiver
{
  public static void a(Context paramContext)
  {
    com.security.d.a.d("MqttKeepAlive", String.format("Scheduling alarm every %d minutes", new Object[] { Integer.valueOf(10) }), new Object[0]);
    b(paramContext);
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 0, new Intent(paramContext, MqttKeepAlive.class), 0);
    ((AlarmManager)paramContext.getSystemService("alarm")).setInexactRepeating(2, 600000L, 600000L, localPendingIntent);
  }
  
  private static void b(Context paramContext)
  {
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 0, new Intent(paramContext, MqttKeepAlive.class), 134217728);
    localPendingIntent.cancel();
    ((AlarmManager)paramContext.getSystemService("alarm")).cancel(localPendingIntent);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    com.security.d.a.d("MqttKeepAlive", "Check if MqttListener is connected", new Object[0]);
    if (!a.a().b())
    {
      com.security.d.a.d("MqttKeepAlive", "MqttListener NOT CONNECTED! Forcing reconnect", new Object[0]);
      paramIntent = new Intent(paramContext, EventsAndReceiveService.class);
      paramIntent.putExtra("event_core_app", "event_mqtt_reconnect");
      com.b.a.a.a.a(paramContext, paramIntent);
    }
  }
}


/* Location:              ~/com/app/system/mqtt/MqttKeepAlive.class
 *
 * Reversed by:           J
 */