package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttPublish
  extends MqttPersistableWireMessage
{
  private byte[] encodedPayload = null;
  private MqttMessage message;
  private String topicName;
  
  public MqttPublish(byte paramByte, byte[] paramArrayOfByte)
  {
    super((byte)3);
    this.message = new MqttReceivedMessage();
    this.message.setQos(0x3 & paramByte >> 1);
    if ((paramByte & 0x1) == 1) {
      this.message.setRetained(true);
    }
    if ((paramByte & 0x8) == 8) {
      ((MqttReceivedMessage)this.message).setDuplicate(true);
    }
    CountingInputStream localCountingInputStream = new CountingInputStream(new ByteArrayInputStream(paramArrayOfByte));
    DataInputStream localDataInputStream = new DataInputStream(localCountingInputStream);
    this.topicName = decodeUTF8(localDataInputStream);
    if (this.message.getQos() > 0) {
      this.msgId = localDataInputStream.readUnsignedShort();
    }
    paramArrayOfByte = new byte[paramArrayOfByte.length - localCountingInputStream.getCounter()];
    localDataInputStream.readFully(paramArrayOfByte);
    localDataInputStream.close();
    this.message.setPayload(paramArrayOfByte);
  }
  
  public MqttPublish(String paramString, MqttMessage paramMqttMessage)
  {
    super((byte)3);
    this.topicName = paramString;
    this.message = paramMqttMessage;
  }
  
  protected static byte[] encodePayload(MqttMessage paramMqttMessage)
  {
    return paramMqttMessage.getPayload();
  }
  
  public MqttMessage getMessage()
  {
    return this.message;
  }
  
  protected byte getMessageInfo()
  {
    byte b1 = (byte)(this.message.getQos() << 1);
    byte b2 = b1;
    if (this.message.isRetained()) {
      b2 = (byte)(b1 | 0x1);
    }
    byte b3;
    if (!this.message.isDuplicate())
    {
      b3 = b2;
      if (!this.duplicate) {}
    }
    else
    {
      b2 = (byte)(b2 | 0x8);
      b3 = b2;
    }
    return b3;
  }
  
  public byte[] getPayload()
  {
    if (this.encodedPayload == null) {
      this.encodedPayload = encodePayload(this.message);
    }
    return this.encodedPayload;
  }
  
  public int getPayloadLength()
  {
    int i;
    try
    {
      i = getPayload().length;
    }
    catch (MqttException localMqttException)
    {
      i = 0;
    }
    return i;
  }
  
  public String getTopicName()
  {
    return this.topicName;
  }
  
  protected byte[] getVariableHeader()
  {
    try
    {
      Object localObject = new java/io/ByteArrayOutputStream;
      ((ByteArrayOutputStream)localObject).<init>();
      DataOutputStream localDataOutputStream = new java/io/DataOutputStream;
      localDataOutputStream.<init>((OutputStream)localObject);
      encodeUTF8(localDataOutputStream, this.topicName);
      if (this.message.getQos() > 0) {
        localDataOutputStream.writeShort(this.msgId);
      }
      localDataOutputStream.flush();
      localObject = ((ByteArrayOutputStream)localObject).toByteArray();
      return (byte[])localObject;
    }
    catch (IOException localIOException)
    {
      throw new MqttException(localIOException);
    }
  }
  
  public boolean isMessageIdRequired()
  {
    return true;
  }
  
  public void setMessageId(int paramInt)
  {
    super.setMessageId(paramInt);
    MqttMessage localMqttMessage = this.message;
    if ((localMqttMessage instanceof MqttReceivedMessage)) {
      ((MqttReceivedMessage)localMqttMessage).setMessageId(paramInt);
    }
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    byte[] arrayOfByte = this.message.getPayload();
    int i = Math.min(arrayOfByte.length, 20);
    Object localObject2;
    for (int j = 0; j < i; j++)
    {
      localObject1 = Integer.toHexString(arrayOfByte[j]);
      localObject2 = localObject1;
      if (((String)localObject1).length() == 1)
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("0");
        ((StringBuilder)localObject2).append((String)localObject1);
        localObject2 = ((StringBuilder)localObject2).toString();
      }
      localStringBuffer.append((String)localObject2);
    }
    String str;
    try
    {
      localObject2 = new java/lang/String;
      ((String)localObject2).<init>(arrayOfByte, 0, i, "UTF-8");
    }
    catch (Exception localException)
    {
      str = "?";
    }
    Object localObject1 = new StringBuffer();
    ((StringBuffer)localObject1).append(super.toString());
    ((StringBuffer)localObject1).append(" qos:");
    ((StringBuffer)localObject1).append(this.message.getQos());
    if (this.message.getQos() > 0)
    {
      ((StringBuffer)localObject1).append(" msgId:");
      ((StringBuffer)localObject1).append(this.msgId);
    }
    ((StringBuffer)localObject1).append(" retained:");
    ((StringBuffer)localObject1).append(this.message.isRetained());
    ((StringBuffer)localObject1).append(" dup:");
    ((StringBuffer)localObject1).append(this.duplicate);
    ((StringBuffer)localObject1).append(" topic:\"");
    ((StringBuffer)localObject1).append(this.topicName);
    ((StringBuffer)localObject1).append("\"");
    ((StringBuffer)localObject1).append(" payload:[hex:");
    ((StringBuffer)localObject1).append(localStringBuffer);
    ((StringBuffer)localObject1).append(" utf8:\"");
    ((StringBuffer)localObject1).append(str);
    ((StringBuffer)localObject1).append("\"");
    ((StringBuffer)localObject1).append(" length:");
    ((StringBuffer)localObject1).append(arrayOfByte.length);
    ((StringBuffer)localObject1).append("]");
    return ((StringBuffer)localObject1).toString();
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttPublish.class
 *
 * Reversed by:           J
 */