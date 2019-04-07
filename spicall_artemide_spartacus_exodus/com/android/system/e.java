package com.android.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.app.system.common.service.EventsAndReceiveService;

public class e
  extends BroadcastReceiver
{
  private static Boolean a;
  private static long b;
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(1);
    com.security.d.a.d("WIFI", paramIntent.toString(), new Object[0]);
    paramIntent = a;
    if ((paramIntent == null) || (paramIntent.booleanValue() != localNetworkInfo.isConnected()))
    {
      a = Boolean.valueOf(localNetworkInfo.isConnected());
      if (a.booleanValue()) {
        paramIntent = "CONNECTED";
      } else {
        paramIntent = "DISCONNECTED";
      }
      com.security.d.a.d("WIFI", paramIntent, new Object[0]);
      paramIntent = new Intent(paramContext, EventsAndReceiveService.class);
      paramIntent.putExtra("event_core_app", "event_wifi_state");
      paramIntent.putExtra("wifi_state", a);
      com.b.a.a.a.a(paramContext, paramIntent);
    }
    paramIntent = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if ((paramIntent != null) && (paramIntent.isConnected()))
    {
      com.security.d.a.d("NetworkChangeReceiver", "CONNESSO A INTERNET", new Object[0]);
      long l = System.currentTimeMillis();
      if (l - b > 2000L)
      {
        paramIntent = new Intent(paramContext.getApplicationContext(), EventsAndReceiveService.class);
        paramIntent.putExtra("event_core_app", "event_has_connected");
        com.b.a.a.a.a(paramContext, paramIntent);
      }
      b = l;
    }
    else
    {
      com.security.d.a.d("NetworkChangeReceiver", "DISCONNESSO DA INTERNET", new Object[0]);
    }
  }
}


/* Location:              ~/com/android/system/e.class
 *
 * Reversed by:           J
 */