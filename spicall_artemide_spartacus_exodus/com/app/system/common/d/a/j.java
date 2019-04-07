package com.app.system.common.d.a;

import android.content.Context;
import com.app.system.common.entity.IPInfo;
import com.app.system.common.h;
import com.google.gson.f;
import java.io.IOException;
import java.util.List;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class j
  extends a
{
  public j(String paramString)
  {
    super(null, paramString);
  }
  
  public int a(Context paramContext, List<IPInfo> paramList)
  {
    try
    {
      Object localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("INVIO LISTA INDIRIZZI IP (tot. ");
      ((StringBuilder)localObject).append(paramList.size());
      ((StringBuilder)localObject).append(")");
      h.a(paramContext, ((StringBuilder)localObject).toString());
      paramContext = new java/lang/StringBuilder;
      paramContext.<init>();
      paramContext.append(this.c);
      paramContext.append("/LogIP");
      paramContext.append('/');
      paramContext.append(this.a);
      paramContext = com.app.system.common.d.a.a.a.a(paramContext.toString(), h.b().a(paramList));
      if (paramContext != null)
      {
        localObject = paramContext.body().string();
        paramList = new java/lang/StringBuilder;
        paramList.<init>();
        paramList.append("SendData Result: ");
        paramList.append(paramContext);
        com.security.d.a.d("ProtocolForIPAddress", paramList.toString(), new Object[0]);
        boolean bool = ((String)localObject).equals("1");
        if (bool) {
          return 1;
        }
      }
    }
    catch (IOException paramContext)
    {
      com.security.d.a.a("ProtocolForIPAddress", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/d/a/j.class
 *
 * Reversed by:           J
 */