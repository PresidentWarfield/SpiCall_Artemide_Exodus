package com.android.system;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.app.system.common.c.f;
import com.security.d.a;

public class CoreApp
  extends Application
{
  private static CoreApp a;
  private f b = new f();
  
  public CoreApp()
  {
    a = this;
  }
  
  public static Context a()
  {
    return a;
  }
  
  public void onCreate()
  {
    super.onCreate();
    try
    {
      a.c("CoreApp", "Core App Restart After Reboot", new Object[0]);
      a.d("CoreApp", "Lancio sequenza di avvio", new Object[0]);
      if (getSharedPreferences("pref", 0).getBoolean("installation-completed", false))
      {
        BootBroadcast.c(this);
        this.b.b(getApplicationContext());
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void onLowMemory()
  {
    super.onLowMemory();
  }
  
  public void onTerminate()
  {
    super.onTerminate();
  }
}


/* Location:              ~/com/android/system/CoreApp.class
 *
 * Reversed by:           J
 */