package org.eclipse.paho.android.service;

import android.os.Binder;

class MqttServiceBinder
  extends Binder
{
  private String activityToken;
  private MqttService mqttService;
  
  MqttServiceBinder(MqttService paramMqttService)
  {
    this.mqttService = paramMqttService;
  }
  
  public String getActivityToken()
  {
    return this.activityToken;
  }
  
  public MqttService getService()
  {
    return this.mqttService;
  }
  
  void setActivityToken(String paramString)
  {
    this.activityToken = paramString;
  }
}


/* Location:              ~/org/eclipse/paho/android/service/MqttServiceBinder.class
 *
 * Reversed by:           J
 */