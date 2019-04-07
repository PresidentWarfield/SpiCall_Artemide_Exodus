package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class MqttPubRec
  extends MqttAck
{
  public MqttPubRec(byte paramByte, byte[] paramArrayOfByte)
  {
    super((byte)5);
    paramArrayOfByte = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    this.msgId = paramArrayOfByte.readUnsignedShort();
    paramArrayOfByte.close();
  }
  
  public MqttPubRec(MqttPublish paramMqttPublish)
  {
    super((byte)5);
    this.msgId = paramMqttPublish.getMessageId();
  }
  
  protected byte[] getVariableHeader()
  {
    return encodeMessageId();
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttPubRec.class
 *
 * Reversed by:           J
 */