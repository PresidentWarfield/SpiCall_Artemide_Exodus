package com.android.system;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import com.security.d.a;
import java.util.List;

public class ScanAppService
  extends Service
{
  Context a;
  public boolean b;
  private String c = "0";
  private String d = "0";
  private String e = "0";
  private String f = "0";
  
  public String a(String paramString1, String paramString2)
  {
    String str = paramString2.replace(".", "").replace("Activity", "").trim();
    if (str != null)
    {
      paramString2 = str;
      if (str.length() > 0) {}
    }
    else
    {
      paramString2 = paramString1.substring(TextUtils.lastIndexOf(paramString1, '.') + 1, paramString1.length());
    }
    return paramString2;
  }
  
  public void a()
  {
    while (!this.b) {
      try
      {
        Object localObject1 = ((ActivityManager)this.a.getSystemService("activity")).getRecentTasks(10, 0);
        int i = ((List)localObject1).size();
        Object localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        ((StringBuilder)localObject2).append("Total Recent Task: ");
        ((StringBuilder)localObject2).append(i);
        a.d("ScanAppService", ((StringBuilder)localObject2).toString(), new Object[0]);
        if (i > 1)
        {
          localObject2 = (ActivityManager.RecentTaskInfo)((List)localObject1).get(0);
          this.d = ((ActivityManager.RecentTaskInfo)localObject2).baseIntent.getComponent().getPackageName();
          if (this.d.endsWith("launcher"))
          {
            localObject2 = (ActivityManager.RecentTaskInfo)((List)localObject1).get(1);
            this.c = ((ActivityManager.RecentTaskInfo)localObject2).baseIntent.getComponent().getPackageName();
            if ((this.c != null) && (!this.c.equals(this.e)))
            {
              localObject1 = new java/lang/StringBuilder;
              ((StringBuilder)localObject1).<init>();
              ((StringBuilder)localObject1).append("CAPTURE - close Package = ");
              ((StringBuilder)localObject1).append(this.c);
              a.d("ScanAppService", ((StringBuilder)localObject1).toString(), new Object[0]);
              localObject2 = a(this.c, ((ActivityManager.RecentTaskInfo)localObject2).baseIntent.getComponent().getShortClassName());
              localObject1 = new java/lang/StringBuilder;
              ((StringBuilder)localObject1).<init>();
              ((StringBuilder)localObject1).append("App Close: name = ");
              ((StringBuilder)localObject1).append((String)localObject2);
              ((StringBuilder)localObject1).append(", appId = ");
              ((StringBuilder)localObject1).append(this.c);
              a.d("ScanAppService", ((StringBuilder)localObject1).toString(), new Object[0]);
              this.e = this.c;
            }
          }
          else if (!this.d.equals(this.f))
          {
            localObject1 = new java/lang/StringBuilder;
            ((StringBuilder)localObject1).<init>();
            ((StringBuilder)localObject1).append("CAPTURE - open Package = ");
            ((StringBuilder)localObject1).append(this.d);
            a.d("ScanAppService", ((StringBuilder)localObject1).toString(), new Object[0]);
            localObject2 = a(this.d, ((ActivityManager.RecentTaskInfo)localObject2).baseIntent.getComponent().getShortClassName());
            localObject1 = new java/lang/StringBuilder;
            ((StringBuilder)localObject1).<init>();
            ((StringBuilder)localObject1).append("New App Run: name = ");
            ((StringBuilder)localObject1).append((String)localObject2);
            ((StringBuilder)localObject1).append(", appId = ");
            ((StringBuilder)localObject1).append(this.d);
            a.d("ScanAppService", ((StringBuilder)localObject1).toString(), new Object[0]);
            this.f = this.d;
          }
        }
        Thread.sleep(2000L);
      }
      catch (Exception localException)
      {
        a.a("ScanAppService", localException.getMessage(), new Object[0]);
      }
    }
  }
  
  public void b()
  {
    a locala = new a(null);
    this.b = false;
    locala.start();
  }
  
  public void c()
  {
    this.b = true;
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    super.onCreate();
    this.a = getApplicationContext();
    b();
  }
  
  public void onDestroy()
  {
    c();
    super.onDestroy();
  }
  
  private class a
    extends Thread
  {
    private a() {}
    
    public void run()
    {
      ScanAppService.this.a();
    }
  }
}


/* Location:              ~/com/android/system/ScanAppService.class
 *
 * Reversed by:           J
 */