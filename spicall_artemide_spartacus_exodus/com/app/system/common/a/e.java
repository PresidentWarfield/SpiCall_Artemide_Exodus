package com.app.system.common.a;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import com.app.system.common.entity.PhotoLog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class e
  extends a
{
  public e(Context paramContext)
  {
    super(paramContext);
  }
  
  public static PhotoLog a(Context paramContext)
  {
    try
    {
      paramContext = paramContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, "datetaken DESC");
      try
      {
        PhotoLog localPhotoLog;
        if (paramContext.moveToNext())
        {
          long l = paramContext.getLong(paramContext.getColumnIndex("_id"));
          Date localDate = new java/util/Date;
          localDate.<init>(Long.parseLong(paramContext.getString(paramContext.getColumnIndex("datetaken"))));
          localPhotoLog = new com/app/system/common/entity/PhotoLog;
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localStringBuilder.append("_");
          SimpleDateFormat localSimpleDateFormat = new java/text/SimpleDateFormat;
          localSimpleDateFormat.<init>("yyyy-MM-dd HH:mm:ss");
          localStringBuilder.append(localSimpleDateFormat.format(localDate).replace("-", "_").replace(":", "_").replace(" ", "_"));
          localStringBuilder.append(".PNG");
          localPhotoLog.<init>(l, localStringBuilder.toString(), l, localDate, 3);
        }
        else
        {
          localPhotoLog = null;
        }
        if ((paramContext != null) && (!paramContext.isClosed()))
        {
          paramContext.close();
          return localPhotoLog;
        }
        return localPhotoLog;
      }
      catch (Throwable localThrowable) {}catch (Exception localException1) {}
      com.security.d.a.d("FilePhoto", localException2.getMessage(), new Object[0]);
    }
    catch (Throwable paramContext)
    {
      paramContext = null;
      if ((paramContext != null) && (!paramContext.isClosed())) {
        paramContext.close();
      }
      return null;
    }
    catch (Exception localException2)
    {
      paramContext = null;
    }
    if ((paramContext != null) && (!paramContext.isClosed()))
    {
      paramContext.close();
      return null;
    }
    return null;
  }
  
  public List<PhotoLog> a(boolean paramBoolean, Date paramDate1, Date paramDate2)
  {
    PhotoLog localPhotoLog = null;
    StringBuilder localStringBuilder1 = null;
    localObject1 = null;
    SimpleDateFormat localSimpleDateFormat = null;
    try
    {
      ContentResolver localContentResolver = this.a.getContentResolver();
      Object localObject2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
      StringBuilder localStringBuilder2 = new java/lang/StringBuilder;
      localStringBuilder2.<init>();
      localStringBuilder2.append("datetaken > ");
      localStringBuilder2.append(paramDate1.getTime());
      localStringBuilder2.append(" AND datetaken <= ");
      localStringBuilder2.append(paramDate2.getTime());
      localObject2 = localContentResolver.query((Uri)localObject2, null, localStringBuilder2.toString(), null, "datetaken DESC");
      paramDate1 = localStringBuilder1;
      try
      {
        paramDate2 = new java/lang/StringBuilder;
        paramDate1 = localStringBuilder1;
        paramDate2.<init>();
        paramDate1 = localStringBuilder1;
        paramDate2.append("There are ");
        paramDate1 = localStringBuilder1;
        paramDate2.append(((Cursor)localObject2).getCount());
        paramDate1 = localStringBuilder1;
        paramDate2.append(" IMAGE(S) in db");
        paramDate1 = localStringBuilder1;
        com.security.d.a.d("FilePhoto", paramDate2.toString(), new Object[0]);
        paramDate2 = localPhotoLog;
        paramDate1 = localStringBuilder1;
        if (((Cursor)localObject2).moveToFirst())
        {
          paramDate1 = localStringBuilder1;
          paramDate2 = new java/util/ArrayList;
          paramDate1 = localStringBuilder1;
          paramDate2.<init>();
          localObject1 = localSimpleDateFormat;
          do
          {
            try
            {
              long l = ((Cursor)localObject2).getLong(((Cursor)localObject2).getColumnIndex("_id"));
              paramDate1 = new java/util/Date;
              paramDate1.<init>(Long.parseLong(((Cursor)localObject2).getString(((Cursor)localObject2).getColumnIndex("datetaken"))));
              localSimpleDateFormat = new java/text/SimpleDateFormat;
              localSimpleDateFormat.<init>("yyyy-MM-dd HH:mm:ss");
              localPhotoLog = new com/app/system/common/entity/PhotoLog;
              localStringBuilder1 = new java/lang/StringBuilder;
              localStringBuilder1.<init>();
              localStringBuilder1.append("_");
              localStringBuilder1.append(localSimpleDateFormat.format(paramDate1).replace("-", "_").replace(":", "_").replace(" ", "_"));
              localStringBuilder1.append(".PNG");
              localPhotoLog.<init>(l, localStringBuilder1.toString(), l, paramDate1, 3);
              paramDate2.add(localPhotoLog);
              paramDate1 = (Date)localObject1;
            }
            catch (Exception paramDate1)
            {
              paramDate1 = paramDate2;
            }
            localObject1 = paramDate1;
          } while (((Cursor)localObject2).moveToNext());
        }
        if (localObject2 != null)
        {
          paramDate1 = paramDate2;
          if (!((Cursor)localObject2).isClosed())
          {
            paramDate1 = paramDate2;
            ((Cursor)localObject2).close();
          }
        }
        return paramDate2;
      }
      catch (Exception paramDate2)
      {
        localObject1 = paramDate1;
        paramDate1 = (Date)localObject2;
      }
      try
      {
        com.security.d.a.d("FilePhoto", paramDate2.getMessage(), new Object[0]);
        if ((paramDate1 != null) && (!paramDate1.isClosed())) {
          paramDate1.close();
        }
        return (List<PhotoLog>)localObject1;
      }
      catch (Exception paramDate2)
      {
        if ((paramDate1 != null) && (!paramDate1.isClosed())) {
          paramDate1.close();
        }
        throw paramDate2;
      }
    }
    catch (Exception paramDate2)
    {
      paramDate1 = null;
    }
  }
}


/* Location:              ~/com/app/system/common/a/e.class
 *
 * Reversed by:           J
 */