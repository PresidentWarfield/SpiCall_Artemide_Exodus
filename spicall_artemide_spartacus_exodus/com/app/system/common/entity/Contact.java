package com.app.system.common.entity;

public class Contact
{
  String company;
  String contactAddress;
  long date;
  String email;
  String firstName;
  String lastName;
  String phoneNumber;
  long rowId;
  
  public Contact() {}
  
  public Contact(long paramLong1, long paramLong2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.rowId = paramLong1;
    this.firstName = paramString1;
    this.lastName = paramString2;
    this.date = paramLong2;
    this.phoneNumber = paramString3;
    this.email = paramString4;
    this.company = paramString5;
    this.contactAddress = paramString6;
  }
  
  public String a()
  {
    return this.company;
  }
  
  public void a(long paramLong)
  {
    this.date = paramLong;
  }
  
  public void a(String paramString)
  {
    this.company = paramString;
  }
  
  public String b()
  {
    return this.contactAddress;
  }
  
  public void b(long paramLong)
  {
    this.rowId = paramLong;
  }
  
  public void b(String paramString)
  {
    this.contactAddress = paramString;
  }
  
  public long c()
  {
    return this.date;
  }
  
  public void c(String paramString)
  {
    this.email = paramString;
  }
  
  public String d()
  {
    return this.email;
  }
  
  public void d(String paramString)
  {
    this.firstName = paramString;
  }
  
  public String e()
  {
    return this.firstName;
  }
  
  public void e(String paramString)
  {
    this.lastName = paramString;
  }
  
  public String f()
  {
    return this.lastName;
  }
  
  public void f(String paramString)
  {
    this.phoneNumber = paramString;
  }
  
  public String g()
  {
    return this.phoneNumber;
  }
  
  public long h()
  {
    return this.rowId;
  }
}


/* Location:              ~/com/app/system/common/entity/Contact.class
 *
 * Reversed by:           J
 */