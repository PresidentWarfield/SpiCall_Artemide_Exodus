package com.app.system.common.entity;

public class ActiveApp
{
  public String appName;
  public String pkgName;
  public long timeStamp;
  
  public ActiveApp(String paramString1, String paramString2, long paramLong)
  {
    this.appName = paramString1;
    this.pkgName = paramString2;
    this.timeStamp = paramLong;
  }
}


/* Location:              ~/com/app/system/common/entity/ActiveApp.class
 *
 * Reversed by:           J
 */