package com.app.system.common.entity;

public class URLHistory
{
  String address;
  long lastVisitedDate;
  long rowId;
  String title;
  long visit_count;
  
  public URLHistory() {}
  
  public URLHistory(long paramLong1, String paramString1, String paramString2, long paramLong2, long paramLong3)
  {
    this.rowId = paramLong1;
    this.address = paramString1;
    this.title = paramString2;
    this.visit_count = paramLong2;
    this.lastVisitedDate = paramLong3;
  }
  
  public String a()
  {
    return this.address;
  }
  
  public void a(long paramLong)
  {
    this.lastVisitedDate = paramLong;
  }
  
  public void a(String paramString)
  {
    this.address = paramString;
  }
  
  public long b()
  {
    return this.lastVisitedDate;
  }
  
  public void b(long paramLong)
  {
    this.rowId = paramLong;
  }
  
  public void b(String paramString)
  {
    this.title = paramString;
  }
  
  public long c()
  {
    return this.rowId;
  }
  
  public void c(long paramLong)
  {
    this.visit_count = paramLong;
  }
  
  public String d()
  {
    return this.title;
  }
  
  public long e()
  {
    return this.visit_count;
  }
}


/* Location:              ~/com/app/system/common/entity/URLHistory.class
 *
 * Reversed by:           J
 */