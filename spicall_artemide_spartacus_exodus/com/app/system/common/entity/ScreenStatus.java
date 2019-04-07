package com.app.system.common.entity;

public class ScreenStatus
  extends Notify
{
  public boolean on;
  public long timestamp;
  
  public ScreenStatus(boolean paramBoolean, long paramLong)
  {
    this.on = paramBoolean;
    this.timestamp = paramLong;
  }
  
  public String a()
  {
    return "SCREEN_STATUS";
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = false;
    if ((paramObject != null) && ((paramObject instanceof ScreenStatus)))
    {
      paramObject = (ScreenStatus)paramObject;
      boolean bool2 = bool1;
      if (this.on == ((ScreenStatus)paramObject).on)
      {
        bool2 = bool1;
        if (this.timestamp == ((ScreenStatus)paramObject).timestamp) {
          bool2 = true;
        }
      }
      return bool2;
    }
    return false;
  }
}


/* Location:              ~/com/app/system/common/entity/ScreenStatus.class
 *
 * Reversed by:           J
 */