package com.app.system.common.c;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import com.app.system.common.service.EventsAndReceiveService;
import java.util.Date;

public class e
  extends d
{
  long b;
  
  public void a(Context paramContext)
  {
    b(paramContext);
    this.b = 0L;
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
            Object localObject = new java/util/Date;
            ((Date)localObject).<init>();
            long l1 = ((Date)localObject).getTime();
            if (e.this.b == 0L) {
              e.this.b = l1;
            }
            long l2 = l1 - e.this.b;
            if ((l2 <= 0L) || (l2 > 10000L))
            {
              e.this.b = l1;
              com.security.d.a.d("ContentObserverForPhoto", "count again. and continute to get photo from database", new Object[0]);
              localObject = new android/content/Intent;
              ((Intent)localObject).<init>(paramContext, EventsAndReceiveService.class);
              ((Intent)localObject).putExtra("event_core_app", "event_photo_log");
              com.b.a.a.a.a(paramContext, (Intent)localObject);
            }
          }
          catch (Exception localException)
          {
            com.security.d.a.d("ContentObserverForPhoto", localException.getMessage(), new Object[0]);
          }
        }
      };
      paramContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, this.a);
    }
  }
  
  public void c(Context paramContext)
  {
    paramContext.getContentResolver().unregisterContentObserver(this.a);
    this.a = null;
  }
}


/* Location:              ~/com/app/system/common/c/e.class
 *
 * Reversed by:           J
 */