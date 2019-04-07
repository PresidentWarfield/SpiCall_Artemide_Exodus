package com.app.system.common.a;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import com.app.system.common.entity.URLHistory;
import java.util.Date;

public class g
  extends a
{
  public g(Context paramContext)
  {
    super(paramContext);
  }
  
  public URLHistory a()
  {
    Cursor localCursor = this.a.getContentResolver().query(com.app.system.common.a.a, com.app.system.common.a.b, null, null, null);
    Object localObject;
    if (localCursor.moveToLast())
    {
      localObject = localCursor.getString(localCursor.getColumnIndex("title"));
      localObject = new URLHistory(0L, localCursor.getString(localCursor.getColumnIndex("url")), (String)localObject, localCursor.getLong(localCursor.getColumnIndex("visits")), new Date(localCursor.getLong(localCursor.getColumnIndex("date"))).getTime() / 1000L);
    }
    else
    {
      localObject = null;
    }
    localCursor.close();
    return (URLHistory)localObject;
  }
}


/* Location:              ~/com/app/system/common/a/g.class
 *
 * Reversed by:           J
 */