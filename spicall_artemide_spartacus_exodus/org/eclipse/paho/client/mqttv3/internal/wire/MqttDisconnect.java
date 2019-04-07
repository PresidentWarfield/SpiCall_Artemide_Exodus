package org.eclipse.paho.client.mqttv3.internal.wire;

public class MqttDisconnect
  extends MqttWireMessage
{
  public static final String KEY = "Disc";
  
  public MqttDisconnect()
  {
    super((byte)14);
  }
  
  public MqttDisconnect(byte paramByte, byte[] paramArrayOfByte)
  {
    super((byte)14);
  }
  
  public String getKey()
  {
    return "Disc";
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


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttDisconnect.class
 *
 * Reversed by:           J
 */