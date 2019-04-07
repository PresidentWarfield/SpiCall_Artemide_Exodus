package android.support.v7.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.view.b;
import android.support.v7.view.f.a;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Window;
import android.view.Window.Callback;

class k
  extends j
{
  private int t = -100;
  private boolean u;
  private boolean v = true;
  private b w;
  
  k(Context paramContext, Window paramWindow, f paramf)
  {
    super(paramContext, paramWindow, paramf);
  }
  
  private boolean h(int paramInt)
  {
    Resources localResources = this.a.getResources();
    Configuration localConfiguration = localResources.getConfiguration();
    int i = localConfiguration.uiMode;
    if (paramInt == 2) {
      paramInt = 32;
    } else {
      paramInt = 16;
    }
    if ((i & 0x30) != paramInt)
    {
      if (z())
      {
        ((Activity)this.a).recreate();
      }
      else
      {
        localConfiguration = new Configuration(localConfiguration);
        DisplayMetrics localDisplayMetrics = localResources.getDisplayMetrics();
        localConfiguration.uiMode = (paramInt | localConfiguration.uiMode & 0xFFFFFFCF);
        localResources.updateConfiguration(localConfiguration, localDisplayMetrics);
        if (Build.VERSION.SDK_INT < 26) {
          p.a(localResources);
        }
      }
      return true;
    }
    return false;
  }
  
  private int x()
  {
    int i = this.t;
    if (i == -100) {
      i = k();
    }
    return i;
  }
  
  private void y()
  {
    if (this.w == null) {
      this.w = new b(s.a(this.a));
    }
  }
  
  private boolean z()
  {
    boolean bool1 = this.u;
    boolean bool2 = false;
    if ((bool1) && ((this.a instanceof Activity)))
    {
      PackageManager localPackageManager = this.a.getPackageManager();
      try
      {
        ComponentName localComponentName = new android/content/ComponentName;
        localComponentName.<init>(this.a, this.a.getClass());
        int i = localPackageManager.getActivityInfo(localComponentName, 0).configChanges;
        if ((i & 0x200) == 0) {
          bool2 = true;
        }
        return bool2;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", localNameNotFoundException);
        return true;
      }
    }
    return false;
  }
  
  Window.Callback a(Window.Callback paramCallback)
  {
    return new a(paramCallback);
  }
  
  public void a(Bundle paramBundle)
  {
    super.a(paramBundle);
    if ((paramBundle != null) && (this.t == -100)) {
      this.t = paramBundle.getInt("appcompat:local_night_mode", -100);
    }
  }
  
  public void c()
  {
    super.c();
    j();
  }
  
  public void c(Bundle paramBundle)
  {
    super.c(paramBundle);
    int i = this.t;
    if (i != -100) {
      paramBundle.putInt("appcompat:local_night_mode", i);
    }
  }
  
  int d(int paramInt)
  {
    if (paramInt != -100)
    {
      if (paramInt != 0) {
        return paramInt;
      }
      y();
      return this.w.a();
    }
    return -1;
  }
  
  public void d()
  {
    super.d();
    b localb = this.w;
    if (localb != null) {
      localb.d();
    }
  }
  
  public void g()
  {
    super.g();
    b localb = this.w;
    if (localb != null) {
      localb.d();
    }
  }
  
  public boolean j()
  {
    int i = x();
    int j = d(i);
    boolean bool;
    if (j != -1) {
      bool = h(j);
    } else {
      bool = false;
    }
    if (i == 0)
    {
      y();
      this.w.c();
    }
    this.u = true;
    return bool;
  }
  
  public boolean p()
  {
    return this.v;
  }
  
  class a
    extends h.b
  {
    a(Window.Callback paramCallback)
    {
      super(paramCallback);
    }
    
    final ActionMode a(ActionMode.Callback paramCallback)
    {
      paramCallback = new f.a(k.this.a, paramCallback);
      b localb = k.this.b(paramCallback);
      if (localb != null) {
        return paramCallback.b(localb);
      }
      return null;
    }
    
    public ActionMode onWindowStartingActionMode(ActionMode.Callback paramCallback)
    {
      if (k.this.p()) {
        return a(paramCallback);
      }
      return super.onWindowStartingActionMode(paramCallback);
    }
  }
  
  final class b
  {
    private s b;
    private boolean c;
    private BroadcastReceiver d;
    private IntentFilter e;
    
    b(s params)
    {
      this.b = params;
      this.c = params.a();
    }
    
    final int a()
    {
      this.c = this.b.a();
      int i;
      if (this.c) {
        i = 2;
      } else {
        i = 1;
      }
      return i;
    }
    
    final void b()
    {
      boolean bool = this.b.a();
      if (bool != this.c)
      {
        this.c = bool;
        k.this.j();
      }
    }
    
    final void c()
    {
      d();
      if (this.d == null) {
        this.d = new BroadcastReceiver()
        {
          public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
          {
            k.b.this.b();
          }
        };
      }
      if (this.e == null)
      {
        this.e = new IntentFilter();
        this.e.addAction("android.intent.action.TIME_SET");
        this.e.addAction("android.intent.action.TIMEZONE_CHANGED");
        this.e.addAction("android.intent.action.TIME_TICK");
      }
      k.this.a.registerReceiver(this.d, this.e);
    }
    
    final void d()
    {
      if (this.d != null)
      {
        k.this.a.unregisterReceiver(this.d);
        this.d = null;
      }
    }
  }
}


/* Location:              ~/android/support/v7/app/k.class
 *
 * Reversed by:           J
 */