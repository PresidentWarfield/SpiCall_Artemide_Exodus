package com.android.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PowerConnectionReceiver
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    com.security.d.a.d("PowerConnectionReceiver", "PowerConnectionReceiver", new Object[0]);
    if (com.app.system.common.f.a.b(paramContext) == 1) {
      SyncAndFlushReceiver.c(paramContext);
    }
    if (com.app.system.common.f.a.a(paramContext) == 1) {
      LocationReceiver.c(paramContext);
    }
  }
}


/* Location:              ~/com/android/system/PowerConnectionReceiver.class
 *
 * Reversed by:           J
 */