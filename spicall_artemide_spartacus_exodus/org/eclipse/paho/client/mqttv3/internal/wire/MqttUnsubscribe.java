package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttUnsubscribe
  extends MqttWireMessage
{
  private int count;
  private String[] names;
  
  public MqttUnsubscribe(byte paramByte, byte[] paramArrayOfByte)
  {
    super((byte)10);
    paramArrayOfByte = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    this.msgId = paramArrayOfByte.readUnsignedShort();
    paramByte = 0;
    this.count = 0;
    this.names = new String[10];
    while (paramByte == 0) {
      try
      {
        this.names[this.count] = decodeUTF8(paramArrayOfByte);
      }
      catch (Exception localException)
      {
        paramByte = 1;
      }
    }
    paramArrayOfByte.close();
  }
  
  public MqttUnsubscribe(String[] paramArrayOfString)
  {
    super((byte)10);
    this.names = paramArrayOfString;
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
  
  public byte[] getPayload()
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new java/io/ByteArrayOutputStream;
      localByteArrayOutputStream.<init>();
      Object localObject = new java/io/DataOutputStream;
      ((DataOutputStream)localObject).<init>(localByteArrayOutputStream);
      for (int i = 0; i < this.names.length; i++) {
        encodeUTF8((DataOutputStream)localObject, this.names[i]);
      }
      ((DataOutputStream)localObject).flush();
      localObject = localByteArrayOutputStream.toByteArray();
      return (byte[])localObject;
    }
    catch (IOException localIOException)
    {
      throw new MqttException(localIOException);
    }
  }
  
  protected byte[] getVariableHeader()
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new java/io/ByteArrayOutputStream;
      localByteArrayOutputStream.<init>();
      Object localObject = new java/io/DataOutputStream;
      ((DataOutputStream)localObject).<init>(localByteArrayOutputStream);
      ((DataOutputStream)localObject).writeShort(this.msgId);
      ((DataOutputStream)localObject).flush();
      localObject = localByteArrayOutputStream.toByteArray();
      return (byte[])localObject;
    }
    catch (IOException localIOException)
    {
      throw new MqttException(localIOException);
    }
  }
  
  public boolean isRetryable()
  {
    return true;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(super.toString());
    localStringBuffer.append(" names:[");
    for (int i = 0; i < this.count; i++)
    {
      if (i > 0) {
        localStringBuffer.append(", ");
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("\"");
      localStringBuilder.append(this.names[i]);
      localStringBuilder.append("\"");
      localStringBuffer.append(localStringBuilder.toString());
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttUnsubscribe.class
 *
 * Reversed by:           J
 */