package com.security;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import com.app.system.common.entity.DeviceInfo;
import com.security.d.a;

public class b
{
  public static boolean a(Context paramContext, String paramString1, String paramString2)
  {
    if (ContextCompat.checkSelfPermission(paramContext, paramString1) != 0)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("PERMISSION DENIED:");
      localStringBuilder.append(paramString1);
      localStringBuilder.append(" for:");
      localStringBuilder.append(paramString2);
      a.b("SECURITY", localStringBuilder.toString(), new Object[0]);
      paramString1 = DeviceInfo.a();
      if (paramString1 != null) {
        paramString1.c(paramContext);
      } else {
        a.b("SECURITY", "DeviceInfo non istanziato: sarà comunicato successivamente", new Object[0]);
      }
      return false;
    }
    return true;
  }
}


/* Location:              ~/com/security/b.class
 *
 * Reversed by:           J
 */