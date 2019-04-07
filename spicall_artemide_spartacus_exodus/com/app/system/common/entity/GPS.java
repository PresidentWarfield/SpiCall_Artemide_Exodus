package com.app.system.common.entity;

public class GPS
{
  double altitude;
  long date;
  String latitude;
  String longitude;
  long rowId;
  
  public GPS() {}
  
  public GPS(long paramLong1, long paramLong2, String paramString1, String paramString2, double paramDouble)
  {
    this.rowId = paramLong1;
    this.date = paramLong2;
    this.latitude = paramString1;
    this.longitude = paramString2;
    this.altitude = paramDouble;
  }
  
  public double a()
  {
    return this.altitude;
  }
  
  public void a(double paramDouble)
  {
    this.altitude = paramDouble;
  }
  
  public void a(long paramLong)
  {
    this.date = paramLong;
  }
  
  public void a(String paramString)
  {
    this.latitude = paramString;
  }
  
  public long b()
  {
    return this.date;
  }
  
  public void b(long paramLong)
  {
    this.rowId = paramLong;
  }
  
  public void b(String paramString)
  {
    this.longitude = paramString;
  }
  
  public String c()
  {
    return this.latitude;
  }
  
  public String d()
  {
    return this.longitude;
  }
  
  public long e()
  {
    return this.rowId;
  }
}


/* Location:              ~/com/app/system/common/entity/GPS.class
 *
 * Reversed by:           J
 */