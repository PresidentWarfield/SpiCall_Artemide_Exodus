package com.app.system.common.h;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.app.system.common.entity.GPS;
import com.security.d.a;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class h
  extends b
{
  public h(Context paramContext)
  {
    super(paramContext);
  }
  
  private String b(List<GPS> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      if (paramList.size() == 1)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("=");
        ((StringBuilder)localObject).append(((GPS)paramList.get(0)).e());
        return ((StringBuilder)localObject).toString();
      }
      Object localObject = new StringBuilder();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        paramList = (GPS)localIterator.next();
        ((StringBuilder)localObject).append(",");
        ((StringBuilder)localObject).append(paramList.e());
      }
      localObject = ((StringBuilder)localObject).substring(1);
      paramList = new StringBuilder();
      paramList.append(" IN (");
      paramList.append((String)localObject);
      paramList.append(')');
      return paramList.toString();
    }
    return "";
  }
  
  public int a(List<GPS> paramList)
  {
    int i = 0;
    if (paramList != null) {
      try
      {
        if (paramList.size() == 0) {
          return -2;
        }
        paramList = b(paramList);
        b();
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("delete from  GPS WHERE rowId");
        localStringBuilder.append(paramList);
        paramList = localStringBuilder.toString();
        localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("DELETE GPS Temp: ");
        localStringBuilder.append(paramList);
        a.d("TGps", localStringBuilder.toString(), new Object[0]);
        this.a.execSQL(paramList.toLowerCase());
        a();
      }
      catch (Exception paramList)
      {
        a.a("TGps", paramList.getMessage(), new Object[0]);
      }
    }
    i = -1;
    return i;
  }
  
  public GPS a(Cursor paramCursor)
  {
    GPS localGPS = new GPS();
    localGPS.b(paramCursor.getLong(0));
    localGPS.a(paramCursor.getLong(1));
    localGPS.a(paramCursor.getString(2));
    localGPS.b(paramCursor.getString(3));
    localGPS.a(paramCursor.getInt(4));
    return localGPS;
  }
  
  public List<GPS> a(Date paramDate1, Date paramDate2)
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
        a.a("TGps", localException.getMessage(), new Object[0]);
        localDate2 = paramDate1;
      }
    }
    paramDate1 = paramDate2;
    if (paramDate2 == null) {
      paramDate1 = new Date();
    }
    long l = paramDate1.getTime() / 1000L;
    paramDate1 = new StringBuilder();
    paramDate1.append("select rowId, Date, Latitude, Longitude, altitude from GPS  where date > ");
    paramDate1.append(localDate2.getTime() / 1000L);
    paramDate1.append(" and date <= ");
    paramDate1.append(l);
    paramDate1.append(" order by date asc ");
    paramDate1 = paramDate1.toString();
    b();
    paramDate1 = this.a.rawQuery(paramDate1.toLowerCase(), null);
    if ((paramDate1 != null) && (paramDate1.moveToFirst()))
    {
      do
      {
        localArrayList.add(a(paramDate1));
      } while (paramDate1.moveToNext());
      paramDate1.close();
      a();
      return localArrayList;
    }
    paramDate1.close();
    a();
    return localArrayList;
  }
  
  public boolean a(GPS paramGPS)
  {
    try
    {
      b();
      Object localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("insert into GPS (Date, Latitude, Longitude, Altitude) select ");
      ((StringBuilder)localObject).append(paramGPS.b());
      ((StringBuilder)localObject).append(", '");
      ((StringBuilder)localObject).append(paramGPS.c());
      ((StringBuilder)localObject).append("', '");
      ((StringBuilder)localObject).append(paramGPS.d());
      ((StringBuilder)localObject).append("', ");
      ((StringBuilder)localObject).append(paramGPS.a());
      ((StringBuilder)localObject).append(" where not EXISTS (select 1 from GPS  where  Date = ");
      ((StringBuilder)localObject).append(paramGPS.b());
      ((StringBuilder)localObject).append(" and Latitude = '");
      ((StringBuilder)localObject).append(paramGPS.c());
      ((StringBuilder)localObject).append("' and Longitude = '");
      ((StringBuilder)localObject).append(paramGPS.d());
      ((StringBuilder)localObject).append("' and Altitude=");
      ((StringBuilder)localObject).append(paramGPS.a());
      ((StringBuilder)localObject).append(")");
      localObject = ((StringBuilder)localObject).toString();
      paramGPS = new java/lang/StringBuilder;
      paramGPS.<init>();
      paramGPS.append("appendGPSRecord: ");
      paramGPS.append((String)localObject);
      a.d("TGps", paramGPS.toString(), new Object[0]);
      this.a.execSQL(((String)localObject).toLowerCase());
      a();
      return true;
    }
    catch (Exception paramGPS)
    {
      a.d("TGps", paramGPS.getMessage(), new Object[0]);
    }
    return false;
  }
}


/* Location:              ~/com/app/system/common/h/h.class
 *
 * Reversed by:           J
 */