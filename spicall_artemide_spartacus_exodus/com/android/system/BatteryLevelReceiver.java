package com.android.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.app.system.common.h;
import com.security.d.a;

public class BatteryLevelReceiver
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    a.d("BatteryLevelReceiver", "BatteryLevelReceiver", new Object[0]);
    if (!h.e(paramContext)) {
      if (paramContext.getSharedPreferences("pref", 0).getBoolean("agent-active", false))
      {
        a.d("BatteryLevelReceiver", "STARTING CORE SERVICE", new Object[0]);
        paramIntent = new Intent(paramContext, CoreService.class);
        paramIntent.setFlags(268435456);
        paramContext.startService(paramIntent);
      }
      else
      {
        a.d("BatteryLevelReceiver", "AGENT NON ATTIVO -- NOT STARTING CORE SERVICE", new Object[0]);
      }
    }
  }
}


/* Location:              ~/com/android/system/BatteryLevelReceiver.class
 *
 * Reversed by:           J
 */