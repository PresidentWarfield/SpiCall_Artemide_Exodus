package com.app.system.common.f;

import android.content.Context;
import com.app.system.common.c;
import com.app.system.common.f.a.a;
import com.app.system.common.f.a.e;
import java.io.File;

public class k
{
  public static void a(Context paramContext)
  {
    Object localObject1 = new File(c.f, "Camera/");
    if (((File)localObject1).exists())
    {
      localObject1 = ((File)localObject1).listFiles();
      int i = localObject1.length;
      int j = 0;
      for (int k = 0; j < i; k = 1)
      {
        Object localObject2 = localObject1[j];
        localObject2 = new a(com.app.system.common.h.c(paramContext), (File)localObject2);
        com.app.system.common.f.a.h.a().a((e)localObject2);
        j++;
      }
      if (k != 0) {
        com.app.system.common.f.a.h.a().a(paramContext);
      }
    }
  }
}


/* Location:              ~/com/app/system/common/f/k.class
 *
 * Reversed by:           J
 */