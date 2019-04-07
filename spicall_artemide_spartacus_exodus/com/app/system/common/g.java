package com.app.system.common;

import android.content.Context;
import android.net.Proxy;
import android.os.Build.VERSION;

public class g
{
  public static boolean a(Context paramContext)
  {
    String str1;
    int i;
    if (Build.VERSION.SDK_INT >= 14)
    {
      str1 = System.getProperty("http.proxyHost");
      String str2 = System.getProperty("http.proxyPort");
      paramContext = str2;
      if (str2 == null) {
        paramContext = "-1";
      }
      i = Integer.parseInt(paramContext);
      paramContext = str1;
    }
    else
    {
      str1 = Proxy.getHost(paramContext);
      i = Proxy.getPort(paramContext);
      paramContext = str1;
    }
    return (i != -1) && (paramContext != null);
  }
}


/* Location:              ~/com/app/system/common/g.class
 *
 * Reversed by:           J
 */