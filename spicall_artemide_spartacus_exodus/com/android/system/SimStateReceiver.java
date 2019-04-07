package com.android.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import com.app.system.common.entity.DeviceInfo.SimInfo;
import com.security.b;
import com.security.d.a;

public class SimStateReceiver
  extends BroadcastReceiver
{
  private static DeviceInfo.SimInfo a = new DeviceInfo.SimInfo();
  
  public static DeviceInfo.SimInfo a()
  {
    return a;
  }
  
  public static void a(Context paramContext)
  {
    if (!b.a(paramContext, "android.permission.READ_PHONE_STATE", "onSimLoaded@SimStateReceiver")) {
      return;
    }
    paramContext = (TelephonyManager)paramContext.getSystemService("phone");
    try
    {
      a.IMEI = paramContext.getDeviceId();
      a.IMSI = paramContext.getSubscriberId();
      a.countryISO = paramContext.getSimCountryIso();
      a.operator = paramContext.getSimOperator();
      a.operatorName = paramContext.getSimOperatorName();
      a.serial = paramContext.getSimSerialNumber();
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("SIM - IMEI: ");
      localStringBuilder.append(a.IMEI);
      a.d("SIM STATE", localStringBuilder.toString(), new Object[0]);
      localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("SIM - SUBSCRIBER ID: ");
      localStringBuilder.append(a.IMSI);
      a.d("SIM STATE", localStringBuilder.toString(), new Object[0]);
      localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("SIM - COUNTRY ISO: ");
      localStringBuilder.append(a.countryISO);
      a.d("SIM STATE", localStringBuilder.toString(), new Object[0]);
      localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("SIM - OPERATOR: ");
      localStringBuilder.append(a.operator);
      a.d("SIM STATE", localStringBuilder.toString(), new Object[0]);
      localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("SIM - OPERATOR NAME: ");
      localStringBuilder.append(a.operatorName);
      a.d("SIM STATE", localStringBuilder.toString(), new Object[0]);
      localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("SIM - SERIAL NUMBER: ");
      localStringBuilder.append(a.serial);
      a.d("SIM STATE", localStringBuilder.toString(), new Object[0]);
      if (Build.VERSION.SDK_INT >= 26)
      {
        localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("SIM - STATE SIM 1: ");
        localStringBuilder.append(paramContext.getSimState(1));
        a.d("SIM STATE", localStringBuilder.toString(), new Object[0]);
        localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("SIM - STATE SIM 2: ");
        localStringBuilder.append(paramContext.getSimState(2));
        a.d("SIM STATE", localStringBuilder.toString(), new Object[0]);
      }
    }
    catch (SecurityException paramContext)
    {
      a.a("SIM STATE", "onSimLoaded: ", new Object[] { paramContext });
    }
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    paramIntent = paramIntent.getStringExtra("ss");
    if (paramIntent == null)
    {
      a.b("SIM STATE", "Extra 'ss' non presente", new Object[0]);
    }
    else
    {
      a.d("SIM STATE", paramIntent.toString(), new Object[0]);
      if (paramIntent.equals("LOADED")) {
        a(paramContext);
      }
    }
  }
}


/* Location:              ~/com/android/system/SimStateReceiver.class
 *
 * Reversed by:           J
 */