package com.android.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class a
  extends BroadcastReceiver
{
  private final String a = "AppInstallReceiver";
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    try
    {
      com.security.d.a.d("AppInstallReceiver", "--- onReceive AppLog ---", new Object[0]);
    }
    catch (Exception paramContext)
    {
      com.security.d.a.d("AppInstallReceiver", paramContext.getMessage(), new Object[0]);
    }
  }
}


/* Location:              ~/com/android/system/a.class
 *
 * Reversed by:           J
 */