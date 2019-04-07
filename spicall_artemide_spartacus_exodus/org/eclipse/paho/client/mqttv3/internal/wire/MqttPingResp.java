package org.eclipse.paho.client.mqttv3.internal.wire;

public class MqttPingResp
  extends MqttAck
{
  public static final String KEY = "Ping";
  
  public MqttPingResp(byte paramByte, byte[] paramArrayOfByte)
  {
    super((byte)13);
  }
  
  public String getKey()
  {
    return "Ping";
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


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttPingResp.class
 *
 * Reversed by:           J
 */