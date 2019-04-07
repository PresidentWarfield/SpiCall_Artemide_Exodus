package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class MqttUnsubAck
  extends MqttAck
{
  public MqttUnsubAck(byte paramByte, byte[] paramArrayOfByte)
  {
    super((byte)11);
    paramArrayOfByte = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    this.msgId = paramArrayOfByte.readUnsignedShort();
    paramArrayOfByte.close();
  }
  
  protected byte[] getVariableHeader()
  {
    return new byte[0];
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttUnsubAck.class
 *
 * Reversed by:           J
 */