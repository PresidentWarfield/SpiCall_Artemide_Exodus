package com.app.system.common.entity;

public class WifiStatus
  extends Notify
{
  public boolean on;
  public long timestamp;
  
  public WifiStatus(boolean paramBoolean, long paramLong)
  {
    this.on = paramBoolean;
    this.timestamp = paramLong;
  }
  
  public String a()
  {
    return "WIFI_STATUS";
  }
}


/* Location:              ~/com/app/system/common/entity/WifiStatus.class
 *
 * Reversed by:           J
 */