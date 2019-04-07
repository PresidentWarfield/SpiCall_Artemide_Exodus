package com.app.system.common.c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

public class a
{
  BroadcastReceiver a;
  
  public void a(Context paramContext)
  {
    IntentFilter localIntentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
    localIntentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
    localIntentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
    localIntentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
    localIntentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
    localIntentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
    localIntentFilter.addDataScheme("package");
    this.a = new com.android.system.a();
    paramContext.registerReceiver(this.a, localIntentFilter);
  }
  
  public void b(Context paramContext)
  {
    paramContext.unregisterReceiver(this.a);
  }
}


/* Location:              ~/com/app/system/common/c/a.class
 *
 * Reversed by:           J
 */