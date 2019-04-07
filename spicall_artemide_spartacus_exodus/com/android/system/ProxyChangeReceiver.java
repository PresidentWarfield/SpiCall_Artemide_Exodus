package com.android.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.security.d.a;

public class ProxyChangeReceiver
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    paramContext = new StringBuilder();
    paramContext.append("Proxy change receiver called: ");
    paramContext.append(paramIntent.toString());
    a.d("ProxyChangeReceiver", paramContext.toString(), new Object[0]);
  }
}


/* Location:              ~/com/android/system/ProxyChangeReceiver.class
 *
 * Reversed by:           J
 */