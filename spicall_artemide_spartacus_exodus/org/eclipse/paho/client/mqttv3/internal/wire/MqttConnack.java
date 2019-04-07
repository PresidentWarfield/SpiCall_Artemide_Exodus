package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class MqttConnack
  extends MqttAck
{
  public static final String KEY = "Con";
  private int returnCode;
  private boolean sessionPresent;
  
  public MqttConnack(byte paramByte, byte[] paramArrayOfByte)
  {
    super((byte)2);
    paramArrayOfByte = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    paramByte = paramArrayOfByte.readUnsignedByte();
    boolean bool = true;
    if ((paramByte & 0x1) != 1) {
      bool = false;
    }
    this.sessionPresent = bool;
    this.returnCode = paramArrayOfByte.readUnsignedByte();
    paramArrayOfByte.close();
  }
  
  public String getKey()
  {
    return "Con";
  }
  
  public int getReturnCode()
  {
    return this.returnCode;
  }
  
  public boolean getSessionPresent()
  {
    return this.sessionPresent;
  }
  
  protected byte[] getVariableHeader()
  {
    return new byte[0];
  }
  
  public boolean isMessageIdRequired()
  {
    return false;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(super.toString());
    localStringBuilder.append(" session present:");
    localStringBuilder.append(this.sessionPresent);
    localStringBuilder.append(" return code: ");
    localStringBuilder.append(this.returnCode);
    return localStringBuilder.toString();
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttConnack.class
 *
 * Reversed by:           J
 */