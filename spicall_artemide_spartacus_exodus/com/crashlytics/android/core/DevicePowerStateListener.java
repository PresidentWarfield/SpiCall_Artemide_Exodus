package com.crashlytics.android.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.concurrent.atomic.AtomicBoolean;

class DevicePowerStateListener
{
  private static final IntentFilter FILTER_BATTERY_CHANGED = new IntentFilter("android.intent.action.BATTERY_CHANGED");
  private static final IntentFilter FILTER_POWER_CONNECTED = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED");
  private static final IntentFilter FILTER_POWER_DISCONNECTED = new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED");
  private final Context context;
  private boolean isPowerConnected;
  private final BroadcastReceiver powerConnectedReceiver;
  private final BroadcastReceiver powerDisconnectedReceiver;
  private final AtomicBoolean receiversRegistered;
  
  public DevicePowerStateListener(Context paramContext)
  {
    this.context = paramContext;
    this.powerConnectedReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        DevicePowerStateListener.access$002(DevicePowerStateListener.this, true);
      }
    };
    this.powerDisconnectedReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        DevicePowerStateListener.access$002(DevicePowerStateListener.this, false);
      }
    };
    this.receiversRegistered = new AtomicBoolean(false);
  }
  
  public void dispose()
  {
    if (!this.receiversRegistered.getAndSet(false)) {
      return;
    }
    this.context.unregisterReceiver(this.powerConnectedReceiver);
    this.context.unregisterReceiver(this.powerDisconnectedReceiver);
  }
  
  public void initialize()
  {
    Object localObject = this.receiversRegistered;
    boolean bool1 = true;
    if (((AtomicBoolean)localObject).getAndSet(true)) {
      return;
    }
    localObject = this.context.registerReceiver(null, FILTER_BATTERY_CHANGED);
    int i = -1;
    if (localObject != null) {
      i = ((Intent)localObject).getIntExtra("status", -1);
    }
    boolean bool2 = bool1;
    if (i != 2) {
      if (i == 5) {
        bool2 = bool1;
      } else {
        bool2 = false;
      }
    }
    this.isPowerConnected = bool2;
    this.context.registerReceiver(this.powerConnectedReceiver, FILTER_POWER_CONNECTED);
    this.context.registerReceiver(this.powerDisconnectedReceiver, FILTER_POWER_DISCONNECTED);
  }
  
  public boolean isPowerConnected()
  {
    return this.isPowerConnected;
  }
}


/* Location:              ~/com/crashlytics/android/core/DevicePowerStateListener.class
 *
 * Reversed by:           J
 */