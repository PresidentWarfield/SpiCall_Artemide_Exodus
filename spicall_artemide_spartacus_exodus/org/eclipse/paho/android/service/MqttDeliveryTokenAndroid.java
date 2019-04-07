package org.eclipse.paho.android.service;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

class MqttDeliveryTokenAndroid
  extends MqttTokenAndroid
  implements IMqttDeliveryToken
{
  private MqttMessage message;
  
  MqttDeliveryTokenAndroid(MqttAndroidClient paramMqttAndroidClient, Object paramObject, IMqttActionListener paramIMqttActionListener, MqttMessage paramMqttMessage)
  {
    super(paramMqttAndroidClient, paramObject, paramIMqttActionListener);
    this.message = paramMqttMessage;
  }
  
  public MqttMessage getMessage()
  {
    return this.message;
  }
  
  void notifyDelivery(MqttMessage paramMqttMessage)
  {
    this.message = paramMqttMessage;
    super.notifyComplete();
  }
  
  void setMessage(MqttMessage paramMqttMessage)
  {
    this.message = paramMqttMessage;
  }
}


/* Location:              ~/org/eclipse/paho/android/service/MqttDeliveryTokenAndroid.class
 *
 * Reversed by:           J
 */