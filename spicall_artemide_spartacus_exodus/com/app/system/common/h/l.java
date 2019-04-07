package com.app.system.common.h;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.app.system.common.entity.PhotoLog;
import com.security.d.a;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class l
  extends b
{
  public l(Context paramContext)
  {
    super(paramContext);
  }
  
  public long a(PhotoLog paramPhotoLog)
  {
    try
    {
      b();
      SQLiteDatabase localSQLiteDatabase = this.a;
      Object localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("insert into photo_log (image_name, imageId, date, image_type) select ?, ?, ?, ?  where not exists (select 1 from PHOTO_LOG where image_name = '");
      ((StringBuilder)localObject).append(paramPhotoLog.c());
      ((StringBuilder)localObject).append("' )");
      localObject = localSQLiteDatabase.compileStatement(((StringBuilder)localObject).toString());
      ((SQLiteStatement)localObject).clearBindings();
      ((SQLiteStatement)localObject).bindString(1, paramPhotoLog.c());
      ((SQLiteStatement)localObject).bindLong(2, paramPhotoLog.b());
      ((SQLiteStatement)localObject).bindLong(3, paramPhotoLog.a().getTime() / 1000L);
      ((SQLiteStatement)localObject).bindLong(4, paramPhotoLog.d());
      long l = ((SQLiteStatement)localObject).executeInsert();
      a();
      return l;
    }
    catch (Exception paramPhotoLog)
    {
      a.d("Photo", paramPhotoLog.getMessage(), new Object[0]);
    }
    return -1L;
  }
  
  public PhotoLog a(Cursor paramCursor)
  {
    PhotoLog localPhotoLog = new PhotoLog();
    localPhotoLog.b(paramCursor.getLong(0));
    localPhotoLog.a(paramCursor.getString(1));
    localPhotoLog.a(paramCursor.getLong(2));
    localPhotoLog.a(new Date(paramCursor.getLong(3) * 1000L));
    localPhotoLog.a(paramCursor.getInt(4));
    return localPhotoLog;
  }
  
  public List<PhotoLog> a(Date paramDate1, Date paramDate2, int paramInt)
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
        localDate1.<init>(localDate3.getTime() - 86400000L);
      }
      catch (Exception localException)
      {
        a.a("Photo", localException.getMessage(), new Object[0]);
        localDate2 = paramDate1;
      }
    }
    paramDate1 = paramDate2;
    if (paramDate2 == null) {
      paramDate1 = new Date();
    }
    long l = paramDate1.getTime() / 1000L;
    paramDate1 = new StringBuilder();
    paramDate1.append("select rowid, image_name, imageId, date, image_type from photo_log where date > ");
    paramDate1.append(localDate2.getTime() / 1000L);
    paramDate1.append(" and date <= ");
    paramDate1.append(l);
    paramDate1 = paramDate1.toString();
    if (paramInt == 0)
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
      paramDate2.append(paramInt);
      paramDate1 = paramDate2.toString();
    }
    b();
    paramDate1 = this.a.rawQuery(paramDate1, null);
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
  
  public boolean a(long paramLong)
  {
    try
    {
      b();
      SQLiteDatabase localSQLiteDatabase = this.a;
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("DELETE FROM photo_log WHERE rowid = ");
      localStringBuilder.append(paramLong);
      localSQLiteDatabase.execSQL(localStringBuilder.toString());
      a();
      return true;
    }
    catch (Exception localException)
    {
      a.d("Photo", localException.getMessage(), new Object[0]);
    }
    return false;
  }
}


/* Location:              ~/com/app/system/common/h/l.class
 *
 * Reversed by:           J
 */