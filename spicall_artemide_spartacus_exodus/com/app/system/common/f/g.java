package com.app.system.common.f;

import android.content.Context;
import com.app.system.common.entity.FileEntry;
import com.app.system.common.f.a.d;
import com.app.system.common.f.a.e;
import java.util.Iterator;
import java.util.List;

public class g
  extends a
{
  private static boolean a = false;
  
  public static void k(Context paramContext)
  {
    l(paramContext);
    com.app.system.common.h.g localg = new com.app.system.common.h.g(paramContext);
    Object localObject = localg.a(50);
    if ((localObject != null) && (((List)localObject).size() > 0))
    {
      String str = com.app.system.common.h.c(paramContext);
      Iterator localIterator = ((List)localObject).iterator();
      while (localIterator.hasNext())
      {
        localObject = new d(str, localg, (FileEntry)localIterator.next());
        com.app.system.common.f.a.h.a().a((e)localObject);
      }
      com.app.system.common.f.a.h.a().a(paramContext);
    }
  }
  
  private static void l(Context paramContext) {}
}


/* Location:              ~/com/app/system/common/f/g.class
 *
 * Reversed by:           J
 */