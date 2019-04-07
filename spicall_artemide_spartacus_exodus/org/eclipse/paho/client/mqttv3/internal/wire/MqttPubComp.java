package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class MqttPubComp
  extends MqttAck
{
  public MqttPubComp(byte paramByte, byte[] paramArrayOfByte)
  {
    super((byte)7);
    paramArrayOfByte = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    this.msgId = paramArrayOfByte.readUnsignedShort();
    paramArrayOfByte.close();
  }
  
  public MqttPubComp(int paramInt)
  {
    super((byte)7);
    this.msgId = paramInt;
  }
  
  public MqttPubComp(MqttPublish paramMqttPublish)
  {
    super((byte)7);
    this.msgId = paramMqttPublish.getMessageId();
  }
  
  protected byte[] getVariableHeader()
  {
    return encodeMessageId();
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttPubComp.class
 *
 * Reversed by:           J
 */