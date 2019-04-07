package com.app.system.common.entity;

import java.util.Date;

public class PhotoLog
{
  public static final int PHOTO_LOG_IMAGE_TYPE_BMP = 0;
  public static final int PHOTO_LOG_IMAGE_TYPE_GIF = 1;
  public static final int PHOTO_LOG_IMAGE_TYPE_JPEG = 4;
  public static final int PHOTO_LOG_IMAGE_TYPE_JPG = 2;
  public static final int PHOTO_LOG_IMAGE_TYPE_PNG = 3;
  Date date;
  long imageId;
  String imageName;
  int imageType;
  long rowId;
  
  public PhotoLog() {}
  
  public PhotoLog(long paramLong1, String paramString, long paramLong2, Date paramDate, int paramInt)
  {
    this.rowId = paramLong1;
    this.imageName = paramString;
    this.imageId = paramLong2;
    this.date = paramDate;
    this.imageType = paramInt;
  }
  
  public Date a()
  {
    return this.date;
  }
  
  public void a(int paramInt)
  {
    this.imageType = paramInt;
  }
  
  public void a(long paramLong)
  {
    this.imageId = paramLong;
  }
  
  public void a(String paramString)
  {
    this.imageName = paramString;
  }
  
  public void a(Date paramDate)
  {
    this.date = paramDate;
  }
  
  public long b()
  {
    return this.imageId;
  }
  
  public void b(long paramLong)
  {
    this.rowId = paramLong;
  }
  
  public String c()
  {
    return this.imageName;
  }
  
  public int d()
  {
    return this.imageType;
  }
  
  public long e()
  {
    return this.rowId;
  }
}


/* Location:              ~/com/app/system/common/entity/PhotoLog.class
 *
 * Reversed by:           J
 */