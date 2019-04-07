package com.app.system.common.entity;

import java.util.Date;

public class MyConfig
{
  private boolean bbmActive = false;
  private boolean callActive = false;
  private long clientTime = new Date().getTime() / 1000L;
  private boolean contactActive = false;
  private boolean emailActive = false;
  private boolean facebookActive = false;
  private boolean gpsActive = false;
  private boolean photoActive = false;
  private boolean recordCallActive;
  private boolean smsActive = false;
  private boolean urlActive = false;
  private boolean viberActive = false;
  private boolean whatsappActive = false;
  private boolean yahooActive = false;
  
  public void a(long paramLong)
  {
    this.clientTime = paramLong;
  }
  
  public void a(boolean paramBoolean)
  {
    this.gpsActive = paramBoolean;
  }
  
  public void b(boolean paramBoolean)
  {
    this.callActive = paramBoolean;
  }
  
  public void c(boolean paramBoolean)
  {
    this.urlActive = paramBoolean;
  }
  
  public void d(boolean paramBoolean)
  {
    this.smsActive = paramBoolean;
  }
  
  public void e(boolean paramBoolean)
  {
    this.contactActive = paramBoolean;
  }
  
  public void f(boolean paramBoolean)
  {
    this.recordCallActive = paramBoolean;
  }
}


/* Location:              ~/com/app/system/common/entity/MyConfig.class
 *
 * Reversed by:           J
 */