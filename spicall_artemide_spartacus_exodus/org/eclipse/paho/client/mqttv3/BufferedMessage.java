package org.eclipse.paho.client.mqttv3;

import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

public class BufferedMessage
{
  private MqttWireMessage message;
  private MqttToken token;
  
  public BufferedMessage(MqttWireMessage paramMqttWireMessage, MqttToken paramMqttToken)
  {
    this.message = paramMqttWireMessage;
    this.token = paramMqttToken;
  }
  
  public MqttWireMessage getMessage()
  {
    return this.message;
  }
  
  public MqttToken getToken()
  {
    return this.token;
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/BufferedMessage.class
 *
 * Reversed by:           J
 */