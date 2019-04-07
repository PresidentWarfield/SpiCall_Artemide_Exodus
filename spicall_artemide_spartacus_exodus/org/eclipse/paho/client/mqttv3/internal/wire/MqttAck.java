package org.eclipse.paho.client.mqttv3.internal.wire;

public abstract class MqttAck
  extends MqttWireMessage
{
  public MqttAck(byte paramByte)
  {
    super(paramByte);
  }
  
  protected byte getMessageInfo()
  {
    return 0;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(super.toString());
    localStringBuilder.append(" msgId ");
    localStringBuilder.append(this.msgId);
    return localStringBuilder.toString();
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttAck.class
 *
 * Reversed by:           J
 */