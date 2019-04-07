package com.app.system.common.f;

import android.content.Context;
import com.app.system.common.entity.ActiveApp;
import com.app.system.common.h;
import com.security.d.a;
import java.util.ArrayList;

public class b
{
  private static ArrayList<ActiveApp> a = new ArrayList();
  
  public static void a(Context paramContext)
  {
    try
    {
      if (a.isEmpty()) {
        return;
      }
      com.app.system.common.d.a.b localb = new com/app/system/common/d/a/b;
      localb.<init>(h.c(paramContext));
      Thread localThread = new java/lang/Thread;
      Runnable local1 = new com/app/system/common/f/b$1;
      local1.<init>(localb, paramContext);
      localThread.<init>(local1);
      localThread.start();
    }
    catch (Exception paramContext)
    {
      a.a("SendActiveApp", "sendPicsToServer:", new Object[] { paramContext });
    }
  }
  
  public static void a(Context paramContext, String paramString1, String paramString2, long paramLong)
  {
    synchronized (a)
    {
      ArrayList localArrayList2 = a;
      ActiveApp localActiveApp = new com/app/system/common/entity/ActiveApp;
      localActiveApp.<init>(paramString1, paramString2, paramLong);
      localArrayList2.add(localActiveApp);
      a(paramContext);
      return;
    }
  }
}


/* Location:              ~/com/app/system/common/f/b.class
 *
 * Reversed by:           J
 */