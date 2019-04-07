package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class MqttPubRel
  extends MqttPersistableWireMessage
{
  public MqttPubRel(byte paramByte, byte[] paramArrayOfByte)
  {
    super((byte)6);
    paramArrayOfByte = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    this.msgId = paramArrayOfByte.readUnsignedShort();
    paramArrayOfByte.close();
  }
  
  public MqttPubRel(MqttPubRec paramMqttPubRec)
  {
    super((byte)6);
    setMessageId(paramMqttPubRec.getMessageId());
  }
  
  protected byte getMessageInfo()
  {
    int i;
    if (this.duplicate) {
      i = 8;
    } else {
      i = 0;
    }
    return (byte)(i | 0x2);
  }
  
  protected byte[] getVariableHeader()
  {
    return encodeMessageId();
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


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttPubRel.class
 *
 * Reversed by:           J
 */