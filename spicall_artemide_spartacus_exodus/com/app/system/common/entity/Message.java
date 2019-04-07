package com.app.system.common.entity;

public class Message
{
  public static final int MESSAGE_LOG_DIRECTON_RECEIVED = 1;
  public static final int MESSAGE_LOG_DIRECTON_SENT = 0;
  public static final int MESSAGE_PROVIDER_DIRECTON_SENT = 2;
  public static final int SMS_LOG_APP_TYPE_ALL = 1000;
  public static final int SMS_LOG_APP_TYPE_BBM = 9;
  public static final int SMS_LOG_APP_TYPE_DEFAULT = 0;
  public static final int SMS_LOG_APP_TYPE_FACEBOOK = 4;
  public static final int SMS_LOG_APP_TYPE_OLA = 8;
  public static final int SMS_LOG_APP_TYPE_SIGNAL = 11;
  public static final int SMS_LOG_APP_TYPE_SKYPE = 5;
  public static final int SMS_LOG_APP_TYPE_TANGO = 6;
  public static final int SMS_LOG_APP_TYPE_TELEGRAM = 10;
  public static final int SMS_LOG_APP_TYPE_VIBER = 3;
  public static final int SMS_LOG_APP_TYPE_WECHAT = 7;
  public static final int SMS_LOG_APP_TYPE_WHATSAPP = 1;
  public static final int SMS_LOG_APP_TYPE_YAHOO_MESSENGER = 2;
  String address;
  int appTypeId;
  long date;
  int flags;
  String personName;
  long rowId;
  String text;
  
  public Message()
  {
    this.rowId = 0L;
  }
  
  public Message(long paramLong1, String paramString1, String paramString2, long paramLong2, String paramString3, int paramInt1, int paramInt2)
  {
    this.rowId = paramLong1;
    this.address = paramString1;
    this.personName = paramString2;
    this.date = paramLong2;
    this.text = paramString3;
    this.flags = paramInt1;
    this.appTypeId = paramInt2;
  }
  
  public String a()
  {
    return this.address;
  }
  
  public void a(int paramInt)
  {
    this.appTypeId = paramInt;
  }
  
  public void a(long paramLong)
  {
    this.date = paramLong;
  }
  
  public void a(String paramString)
  {
    this.address = paramString;
  }
  
  public int b()
  {
    return this.appTypeId;
  }
  
  public void b(int paramInt)
  {
    this.flags = paramInt;
  }
  
  public void b(long paramLong)
  {
    this.rowId = paramLong;
  }
  
  public void b(String paramString)
  {
    this.personName = paramString;
  }
  
  public long c()
  {
    return this.date;
  }
  
  public void c(String paramString)
  {
    this.text = paramString;
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
  
  public String g()
  {
    return this.text;
  }
}


/* Location:              ~/com/app/system/common/entity/Message.class
 *
 * Reversed by:           J
 */