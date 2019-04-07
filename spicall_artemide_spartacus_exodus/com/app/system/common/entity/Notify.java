package com.app.system.common.entity;

import com.app.system.common.h;
import com.google.gson.f;

public abstract class Notify
{
  static final String REC_STATUS = "REC_STATUS";
  static final String SCREEN_STATUS = "SCREEN_STATUS";
  static final String WIFI_STATUS = "WIFI_STATUS";
  
  public abstract String a();
  
  public String b()
  {
    return h.b().a(this);
  }
}


/* Location:              ~/com/app/system/common/entity/Notify.class
 *
 * Reversed by:           J
 */