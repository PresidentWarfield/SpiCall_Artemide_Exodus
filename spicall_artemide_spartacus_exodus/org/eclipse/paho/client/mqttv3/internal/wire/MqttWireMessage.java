package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;

public abstract class MqttWireMessage
{
  public static final byte MESSAGE_TYPE_CONNACK = 2;
  public static final byte MESSAGE_TYPE_CONNECT = 1;
  public static final byte MESSAGE_TYPE_DISCONNECT = 14;
  public static final byte MESSAGE_TYPE_PINGREQ = 12;
  public static final byte MESSAGE_TYPE_PINGRESP = 13;
  public static final byte MESSAGE_TYPE_PUBACK = 4;
  public static final byte MESSAGE_TYPE_PUBCOMP = 7;
  public static final byte MESSAGE_TYPE_PUBLISH = 3;
  public static final byte MESSAGE_TYPE_PUBREC = 5;
  public static final byte MESSAGE_TYPE_PUBREL = 6;
  public static final byte MESSAGE_TYPE_SUBACK = 9;
  public static final byte MESSAGE_TYPE_SUBSCRIBE = 8;
  public static final byte MESSAGE_TYPE_UNSUBACK = 11;
  public static final byte MESSAGE_TYPE_UNSUBSCRIBE = 10;
  private static final String[] PACKET_NAMES = { "reserved", "CONNECT", "CONNACK", "PUBLISH", "PUBACK", "PUBREC", "PUBREL", "PUBCOMP", "SUBSCRIBE", "SUBACK", "UNSUBSCRIBE", "UNSUBACK", "PINGREQ", "PINGRESP", "DISCONNECT" };
  protected static final String STRING_ENCODING = "UTF-8";
  protected boolean duplicate = false;
  protected int msgId;
  private byte type;
  
  public MqttWireMessage(byte paramByte)
  {
    this.type = ((byte)paramByte);
    this.msgId = 0;
  }
  
  private static MqttWireMessage createWireMessage(InputStream paramInputStream)
  {
    try
    {
      CountingInputStream localCountingInputStream = new org/eclipse/paho/client/mqttv3/internal/wire/CountingInputStream;
      localCountingInputStream.<init>(paramInputStream);
      DataInputStream localDataInputStream = new java/io/DataInputStream;
      localDataInputStream.<init>(localCountingInputStream);
      int i = localDataInputStream.readUnsignedByte();
      int j = (byte)(i >> 4);
      byte b = (byte)(i & 0xF);
      long l = readMBI(localDataInputStream).getValue();
      l = localCountingInputStream.getCounter() + l - localCountingInputStream.getCounter();
      paramInputStream = new byte[0];
      if (l > 0L)
      {
        paramInputStream = new byte[(int)l];
        localDataInputStream.readFully(paramInputStream, 0, paramInputStream.length);
      }
      if (j == 1)
      {
        paramInputStream = new MqttConnect(b, paramInputStream);
      }
      else if (j == 3)
      {
        paramInputStream = new MqttPublish(b, paramInputStream);
      }
      else if (j == 4)
      {
        paramInputStream = new MqttPubAck(b, paramInputStream);
      }
      else if (j == 7)
      {
        paramInputStream = new MqttPubComp(b, paramInputStream);
      }
      else if (j == 2)
      {
        paramInputStream = new MqttConnack(b, paramInputStream);
      }
      else if (j == 12)
      {
        paramInputStream = new MqttPingReq(b, paramInputStream);
      }
      else if (j == 13)
      {
        paramInputStream = new MqttPingResp(b, paramInputStream);
      }
      else if (j == 8)
      {
        paramInputStream = new MqttSubscribe(b, paramInputStream);
      }
      else if (j == 9)
      {
        paramInputStream = new MqttSuback(b, paramInputStream);
      }
      else if (j == 10)
      {
        paramInputStream = new MqttUnsubscribe(b, paramInputStream);
      }
      else if (j == 11)
      {
        paramInputStream = new MqttUnsubAck(b, paramInputStream);
      }
      else if (j == 6)
      {
        paramInputStream = new MqttPubRel(b, paramInputStream);
      }
      else if (j == 5)
      {
        paramInputStream = new MqttPubRec(b, paramInputStream);
      }
      else
      {
        if (j != 14) {
          break label374;
        }
        paramInputStream = new MqttDisconnect(b, paramInputStream);
      }
      return paramInputStream;
      label374:
      throw ExceptionHelper.createMqttException(6);
    }
    catch (IOException paramInputStream)
    {
      throw new MqttException(paramInputStream);
    }
  }
  
  public static MqttWireMessage createWireMessage(MqttPersistable paramMqttPersistable)
  {
    byte[] arrayOfByte = paramMqttPersistable.getPayloadBytes();
    if (arrayOfByte == null) {
      arrayOfByte = new byte[0];
    }
    return createWireMessage(new MultiByteArrayInputStream(paramMqttPersistable.getHeaderBytes(), paramMqttPersistable.getHeaderOffset(), paramMqttPersistable.getHeaderLength(), arrayOfByte, paramMqttPersistable.getPayloadOffset(), paramMqttPersistable.getPayloadLength()));
  }
  
  public static MqttWireMessage createWireMessage(byte[] paramArrayOfByte)
  {
    return createWireMessage(new ByteArrayInputStream(paramArrayOfByte));
  }
  
  protected static byte[] encodeMBI(long paramLong)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int i = 0;
    int k;
    do
    {
      int j = (byte)(int)(paramLong % 128L);
      paramLong /= 128L;
      k = j;
      if (paramLong > 0L) {
        k = (byte)(j | 0x80);
      }
      localByteArrayOutputStream.write(k);
      k = i + 1;
      if (paramLong <= 0L) {
        break;
      }
      i = k;
    } while (k < 4);
    return localByteArrayOutputStream.toByteArray();
  }
  
  protected static MultiByteInteger readMBI(DataInputStream paramDataInputStream)
  {
    long l1 = 0L;
    int i = 0;
    int j = 1;
    int k;
    int m;
    long l2;
    do
    {
      k = paramDataInputStream.readByte();
      m = i + 1;
      l2 = l1 + (k & 0x7F) * j;
      j *= 128;
      l1 = l2;
      i = m;
    } while ((k & 0x80) != 0);
    return new MultiByteInteger(l2, m);
  }
  
  protected String decodeUTF8(DataInputStream paramDataInputStream)
  {
    try
    {
      byte[] arrayOfByte = new byte[paramDataInputStream.readUnsignedShort()];
      paramDataInputStream.readFully(arrayOfByte);
      paramDataInputStream = new String(arrayOfByte, "UTF-8");
      return paramDataInputStream;
    }
    catch (IOException paramDataInputStream)
    {
      throw new MqttException(paramDataInputStream);
    }
  }
  
  protected byte[] encodeMessageId()
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
  
  protected void encodeUTF8(DataOutputStream paramDataOutputStream, String paramString)
  {
    try
    {
      paramString = paramString.getBytes("UTF-8");
      int i = (byte)(paramString.length >>> 8 & 0xFF);
      int j = (byte)(paramString.length >>> 0 & 0xFF);
      paramDataOutputStream.write(i);
      paramDataOutputStream.write(j);
      paramDataOutputStream.write(paramString);
      return;
    }
    catch (IOException paramDataOutputStream)
    {
      throw new MqttException(paramDataOutputStream);
    }
    catch (UnsupportedEncodingException paramDataOutputStream)
    {
      throw new MqttException(paramDataOutputStream);
    }
  }
  
  public byte[] getHeader()
  {
    try
    {
      int i = getType();
      int j = getMessageInfo();
      byte[] arrayOfByte = getVariableHeader();
      int k = arrayOfByte.length;
      int m = getPayload().length;
      Object localObject = new java/io/ByteArrayOutputStream;
      ((ByteArrayOutputStream)localObject).<init>();
      DataOutputStream localDataOutputStream = new java/io/DataOutputStream;
      localDataOutputStream.<init>((OutputStream)localObject);
      localDataOutputStream.writeByte((i & 0xF) << 4 ^ j & 0xF);
      localDataOutputStream.write(encodeMBI(k + m));
      localDataOutputStream.write(arrayOfByte);
      localDataOutputStream.flush();
      localObject = ((ByteArrayOutputStream)localObject).toByteArray();
      return (byte[])localObject;
    }
    catch (IOException localIOException)
    {
      throw new MqttException(localIOException);
    }
  }
  
  public String getKey()
  {
    return new Integer(getMessageId()).toString();
  }
  
  public int getMessageId()
  {
    return this.msgId;
  }
  
  protected abstract byte getMessageInfo();
  
  public byte[] getPayload()
  {
    return new byte[0];
  }
  
  public byte getType()
  {
    return this.type;
  }
  
  protected abstract byte[] getVariableHeader();
  
  public boolean isMessageIdRequired()
  {
    return true;
  }
  
  public boolean isRetryable()
  {
    return false;
  }
  
  public void setDuplicate(boolean paramBoolean)
  {
    this.duplicate = paramBoolean;
  }
  
  public void setMessageId(int paramInt)
  {
    this.msgId = paramInt;
  }
  
  public String toString()
  {
    return PACKET_NAMES[this.type];
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage.class
 *
 * Reversed by:           J
 */