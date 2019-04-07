package com.app.system.common.d.a;

import android.content.Context;
import com.app.system.common.entity.Notify;
import java.io.IOException;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class k
  extends a
{
  public k(String paramString)
  {
    super(null, paramString);
  }
  
  public int a(Context paramContext, Notify paramNotify)
  {
    try
    {
      paramContext = new java/lang/StringBuilder;
      paramContext.<init>();
      paramContext.append(this.c);
      paramContext.append("/LogNotify");
      paramContext.append('/');
      paramContext.append(this.a);
      paramContext.append('/');
      paramContext.append(paramNotify.a());
      paramNotify = com.app.system.common.d.a.a.a.a(paramContext.toString(), paramNotify.b());
      if (paramNotify != null)
      {
        paramContext = paramNotify.body().string();
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("SendList Result: ");
        localStringBuilder.append(paramNotify);
        com.security.d.a.d("ProtocolForNotify", localStringBuilder.toString(), new Object[0]);
        boolean bool = paramContext.equals("1");
        if (bool) {
          return 1;
        }
      }
    }
    catch (IOException paramContext)
    {
      com.security.d.a.a("ProtocolForNotify", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/d/a/k.class
 *
 * Reversed by:           J
 */