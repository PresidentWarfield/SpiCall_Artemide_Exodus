package org.eclipse.paho.client.mqttv3.internal.wire;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttReceivedMessage
  extends MqttMessage
{
  public int getMessageId()
  {
    return super.getId();
  }
  
  public void setDuplicate(boolean paramBoolean)
  {
    super.setDuplicate(paramBoolean);
  }
  
  public void setMessageId(int paramInt)
  {
    super.setId(paramInt);
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttReceivedMessage.class
 *
 * Reversed by:           J
 */