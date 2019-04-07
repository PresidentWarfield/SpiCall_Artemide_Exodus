package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class MqttPubAck
  extends MqttAck
{
  public MqttPubAck(byte paramByte, byte[] paramArrayOfByte)
  {
    super((byte)4);
    paramArrayOfByte = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    this.msgId = paramArrayOfByte.readUnsignedShort();
    paramArrayOfByte.close();
  }
  
  public MqttPubAck(int paramInt)
  {
    super((byte)4);
    this.msgId = paramInt;
  }
  
  public MqttPubAck(MqttPublish paramMqttPublish)
  {
    super((byte)4);
    this.msgId = paramMqttPublish.getMessageId();
  }
  
  protected byte[] getVariableHeader()
  {
    return encodeMessageId();
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttPubAck.class
 *
 * Reversed by:           J
 */