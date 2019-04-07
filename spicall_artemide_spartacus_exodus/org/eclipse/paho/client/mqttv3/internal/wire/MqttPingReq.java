package org.eclipse.paho.client.mqttv3.internal.wire;

public class MqttPingReq
  extends MqttWireMessage
{
  public static final String KEY = "Ping";
  
  public MqttPingReq()
  {
    super((byte)12);
  }
  
  public MqttPingReq(byte paramByte, byte[] paramArrayOfByte)
  {
    super((byte)12);
  }
  
  public String getKey()
  {
    return "Ping";
  }
  
  protected byte getMessageInfo()
  {
    return 0;
  }
  
  protected byte[] getVariableHeader()
  {
    return new byte[0];
  }
  
  public boolean isMessageIdRequired()
  {
    return false;
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttPingReq.class
 *
 * Reversed by:           J
 */