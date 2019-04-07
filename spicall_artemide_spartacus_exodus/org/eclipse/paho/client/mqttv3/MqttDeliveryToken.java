package org.eclipse.paho.client.mqttv3;

import org.eclipse.paho.client.mqttv3.internal.Token;

public class MqttDeliveryToken
  extends MqttToken
  implements IMqttDeliveryToken
{
  public MqttDeliveryToken() {}
  
  public MqttDeliveryToken(String paramString)
  {
    super(paramString);
  }
  
  public MqttMessage getMessage()
  {
    return this.internalTok.getMessage();
  }
  
  protected void setMessage(MqttMessage paramMqttMessage)
  {
    this.internalTok.setMessage(paramMqttMessage);
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/MqttDeliveryToken.class
 *
 * Reversed by:           J
 */