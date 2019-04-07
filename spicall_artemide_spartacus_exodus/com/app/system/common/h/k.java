package com.app.system.common.h;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.app.system.common.entity.Message;
import com.security.d.a;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class k
  extends b
{
  public static int d = 256;
  
  public k(Context paramContext)
  {
    super(paramContext);
  }
  
  public int a(List<Message> paramList)
  {
    int i = 0;
    if (paramList != null) {
      try
      {
        if (paramList.size() == 0) {
          return -2;
        }
        b();
        String str = "delete from  message WHERE 1=0 ";
        Object localObject = paramList.iterator();
        while (((Iterator)localObject).hasNext())
        {
          Message localMessage = (Message)((Iterator)localObject).next();
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localStringBuilder.append(str);
          localStringBuilder.append(" or rowId = ");
          localStringBuilder.append(localMessage.f());
          str = localStringBuilder.toString();
        }
        localObject = new java/lang/StringBuilder;
        ((StringBuilder)localObject).<init>();
        ((StringBuilder)localObject).append("Delete: ");
        ((StringBuilder)localObject).append(paramList.size());
        ((StringBuilder)localObject).append("  MESSAGE.");
        a.d("TMessage", ((StringBuilder)localObject).toString(), new Object[0]);
        this.a.execSQL(str);
        a();
      }
      catch (Exception paramList)
      {
        a.a("TMessage", paramList.getMessage(), new Object[0]);
      }
    }
    i = -1;
    return i;
  }
  
  public long a(Message paramMessage)
  {
    try
    {
      b();
      SQLiteStatement localSQLiteStatement = this.a.compileStatement("INSERT INTO message (Address, Person_Name, Date, Text, Flags, APP_TYPE_ID) VALUES (?,?,?,?,?,?)");
      localSQLiteStatement.bindString(1, paramMessage.a());
      localSQLiteStatement.bindString(2, paramMessage.e());
      localSQLiteStatement.bindLong(3, paramMessage.c());
      localSQLiteStatement.bindString(4, paramMessage.g());
      localSQLiteStatement.bindLong(5, paramMessage.d());
      localSQLiteStatement.bindLong(6, paramMessage.b());
      long l = localSQLiteStatement.executeInsert();
      return l;
    }
    catch (Exception paramMessage)
    {
      a.d("TMessage", paramMessage.getMessage(), new Object[0]);
    }
    return -1L;
  }
  
  public Message a(Cursor paramCursor)
  {
    Message localMessage = new Message();
    localMessage.b(paramCursor.getLong(0));
    localMessage.a(paramCursor.getString(1));
    localMessage.b(paramCursor.getString(2));
    localMessage.a(paramCursor.getLong(3));
    localMessage.c(paramCursor.getString(4));
    localMessage.b(paramCursor.getInt(5));
    localMessage.a(paramCursor.getInt(6));
    return localMessage;
  }
  
  public List<Message> a(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2)
  {
    ArrayList localArrayList = new ArrayList();
    Date localDate1 = paramDate1;
    Date localDate2;
    if (paramDate1 == null) {
      try
      {
        localDate1 = new java/util/Date;
        Date localDate3 = new java/util/Date;
        localDate3.<init>();
        localDate1.<init>(localDate3.getTime() - 2592000000L);
      }
      catch (Exception localException)
      {
        a.a("TMessage", localException.getMessage(), new Object[0]);
        localDate2 = paramDate1;
      }
    }
    paramDate1 = paramDate2;
    if (paramDate2 == null) {
      paramDate1 = new Date();
    }
    long l = paramDate1.getTime() / 1000L;
    paramDate1 = new StringBuilder();
    paramDate1.append("select rowid, address, person_name, date, text, flags, app_type_id from message where date > ");
    paramDate1.append(localDate2.getTime() / 1000L);
    paramDate1.append(" and date <= ");
    paramDate1.append(l);
    paramDate1.append(" and app_type_id = ");
    paramDate1.append(paramInt2);
    paramDate1 = paramDate1.toString();
    if (paramInt1 == 0)
    {
      paramDate2 = new StringBuilder();
      paramDate2.append(paramDate1);
      paramDate2.append(" order by date desc");
      paramDate1 = paramDate2.toString();
    }
    else
    {
      paramDate2 = new StringBuilder();
      paramDate2.append(paramDate1);
      paramDate2.append(" order by date desc limit ");
      paramDate2.append(paramInt1);
      paramDate1 = paramDate2.toString();
    }
    b();
    paramDate1 = this.a.rawQuery(paramDate1, null);
    paramDate2 = new StringBuilder();
    paramDate2.append("MESSAGE : Get ");
    paramDate2.append(paramInt1);
    paramDate2.append(" / ");
    paramDate2.append(paramDate1.getCount());
    paramDate2.append(" RECORDS.");
    a.d("TMessage", paramDate2.toString(), new Object[0]);
    if (paramDate1.moveToFirst()) {
      do
      {
        localArrayList.add(a(paramDate1));
      } while (paramDate1.moveToNext());
    }
    paramDate1.close();
    a();
    return localArrayList;
  }
}


/* Location:              ~/com/app/system/common/h/k.class
 *
 * Reversed by:           J
 */