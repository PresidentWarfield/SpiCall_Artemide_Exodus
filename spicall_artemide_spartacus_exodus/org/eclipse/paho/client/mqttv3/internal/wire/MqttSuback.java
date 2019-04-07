package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class MqttSuback
  extends MqttAck
{
  private int[] grantedQos;
  
  public MqttSuback(byte paramByte, byte[] paramArrayOfByte)
  {
    super((byte)9);
    DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    this.msgId = localDataInputStream.readUnsignedShort();
    this.grantedQos = new int[paramArrayOfByte.length - 2];
    paramByte = localDataInputStream.read();
    int i = 0;
    while (paramByte != -1)
    {
      this.grantedQos[i] = paramByte;
      i++;
      paramByte = localDataInputStream.read();
    }
    localDataInputStream.close();
  }
  
  public int[] getGrantedQos()
  {
    return this.grantedQos;
  }
  
  protected byte[] getVariableHeader()
  {
    return new byte[0];
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(super.toString());
    localStringBuffer.append(" granted Qos");
    for (int i = 0; i < this.grantedQos.length; i++)
    {
      localStringBuffer.append(" ");
      localStringBuffer.append(this.grantedQos[i]);
    }
    return localStringBuffer.toString();
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttSuback.class
 *
 * Reversed by:           J
 */