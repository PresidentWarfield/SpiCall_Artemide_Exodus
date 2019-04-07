package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttSubscribe
  extends MqttWireMessage
{
  private int count;
  private String[] names;
  private int[] qos;
  
  public MqttSubscribe(byte paramByte, byte[] paramArrayOfByte)
  {
    super((byte)8);
    paramArrayOfByte = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    this.msgId = paramArrayOfByte.readUnsignedShort();
    paramByte = 0;
    this.count = 0;
    this.names = new String[10];
    this.qos = new int[10];
    while (paramByte == 0) {
      try
      {
        this.names[this.count] = decodeUTF8(paramArrayOfByte);
        int[] arrayOfInt = this.qos;
        int i = this.count;
        this.count = (i + 1);
        arrayOfInt[i] = paramArrayOfByte.readByte();
      }
      catch (Exception localException)
      {
        paramByte = 1;
      }
    }
    paramArrayOfByte.close();
  }
  
  public MqttSubscribe(String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    super((byte)8);
    this.names = paramArrayOfString;
    this.qos = paramArrayOfInt;
    if (paramArrayOfString.length == paramArrayOfInt.length)
    {
      this.count = paramArrayOfString.length;
      for (int i = 0; i < paramArrayOfInt.length; i++) {
        MqttMessage.validateQos(paramArrayOfInt[i]);
      }
      return;
    }
    throw new IllegalArgumentException();
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
      for (int i = 0; i < this.names.length; i++)
      {
        encodeUTF8((DataOutputStream)localObject, this.names[i]);
        ((DataOutputStream)localObject).writeByte(this.qos[i]);
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
      Object localObject = new java/io/ByteArrayOutputStream;
      ((ByteArrayOutputStream)localObject).<init>();
      DataOutputStream localDataOutputStream = new java/io/DataOutputStream;
      localDataOutputStream.<init>((OutputStream)localObject);
      localDataOutputStream.writeShort(this.msgId);
      localDataOutputStream.flush();
      localObject = ((ByteArrayOutputStream)localObject).toByteArray();
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
    int i = 0;
    for (int j = 0; j < this.count; j++)
    {
      if (j > 0) {
        localStringBuffer.append(", ");
      }
      localStringBuffer.append("\"");
      localStringBuffer.append(this.names[j]);
      localStringBuffer.append("\"");
    }
    localStringBuffer.append("] qos:[");
    for (j = i; j < this.count; j++)
    {
      if (j > 0) {
        localStringBuffer.append(", ");
      }
      localStringBuffer.append(this.qos[j]);
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttSubscribe.class
 *
 * Reversed by:           J
 */