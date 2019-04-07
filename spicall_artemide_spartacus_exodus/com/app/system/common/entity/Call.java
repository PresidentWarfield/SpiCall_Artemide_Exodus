package com.app.system.common.entity;

public class Call
{
  public static final int CALL_LOG_DIRECTON_RECEIVED = 0;
  public static final int CALL_LOG_DIRECTON_SENT = 1;
  public static final int CALL_TYPE_DIRECTION_UNKNOWN = 2;
  String address;
  long date;
  long duration;
  int flags;
  String personName;
  long rowId;
  
  public Call()
  {
    this.rowId = 0L;
    this.address = "";
    this.personName = "";
    this.date = 0L;
    this.duration = 0L;
    this.flags = 0;
  }
  
  public Call(long paramLong1, String paramString1, String paramString2, long paramLong2, long paramLong3, int paramInt)
  {
    this.rowId = paramLong1;
    this.personName = paramString1;
    this.address = paramString2;
    this.date = paramLong2;
    this.duration = paramLong3;
    this.flags = paramInt;
  }
  
  public String a()
  {
    return this.address;
  }
  
  public void a(int paramInt)
  {
    this.flags = paramInt;
  }
  
  public void a(long paramLong)
  {
    this.date = paramLong;
  }
  
  public void a(String paramString)
  {
    this.address = paramString;
  }
  
  public long b()
  {
    return this.date;
  }
  
  public void b(long paramLong)
  {
    this.duration = paramLong;
  }
  
  public void b(String paramString)
  {
    this.personName = paramString;
  }
  
  public long c()
  {
    return this.duration;
  }
  
  public void c(long paramLong)
  {
    this.rowId = paramLong;
  }
  
  public int d()
  {
    return this.flags;
  }
  
  public String e()
  {
    return this.personName;
  }
  
  public long f()
  {
    return this.rowId;
  }
}


/* Location:              ~/com/app/system/common/entity/Call.class
 *
 * Reversed by:           J
 */