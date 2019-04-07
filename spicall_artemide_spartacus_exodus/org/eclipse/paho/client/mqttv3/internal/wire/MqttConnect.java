package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttConnect
  extends MqttWireMessage
{
  public static final String KEY = "Con";
  private int MqttVersion;
  private boolean cleanSession;
  private String clientId;
  private int keepAliveInterval;
  private char[] password;
  private String userName;
  private String willDestination;
  private MqttMessage willMessage;
  
  public MqttConnect(byte paramByte, byte[] paramArrayOfByte)
  {
    super((byte)1);
    paramArrayOfByte = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    decodeUTF8(paramArrayOfByte);
    paramArrayOfByte.readByte();
    paramArrayOfByte.readByte();
    this.keepAliveInterval = paramArrayOfByte.readUnsignedShort();
    this.clientId = decodeUTF8(paramArrayOfByte);
    paramArrayOfByte.close();
  }
  
  public MqttConnect(String paramString1, int paramInt1, boolean paramBoolean, int paramInt2, String paramString2, char[] paramArrayOfChar, MqttMessage paramMqttMessage, String paramString3)
  {
    super((byte)1);
    this.clientId = paramString1;
    this.cleanSession = paramBoolean;
    this.keepAliveInterval = paramInt2;
    this.userName = paramString2;
    this.password = paramArrayOfChar;
    this.willMessage = paramMqttMessage;
    this.willDestination = paramString3;
    this.MqttVersion = paramInt1;
  }
  
  public String getKey()
  {
    return "Con";
  }
  
  protected byte getMessageInfo()
  {
    return 0;
  }
  
  public byte[] getPayload()
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new java/io/ByteArrayOutputStream;
      localByteArrayOutputStream.<init>();
      Object localObject = new java/io/DataOutputStream;
      ((DataOutputStream)localObject).<init>(localByteArrayOutputStream);
      encodeUTF8((DataOutputStream)localObject, this.clientId);
      if (this.willMessage != null)
      {
        encodeUTF8((DataOutputStream)localObject, this.willDestination);
        ((DataOutputStream)localObject).writeShort(this.willMessage.getPayload().length);
        ((DataOutputStream)localObject).write(this.willMessage.getPayload());
      }
      if (this.userName != null)
      {
        encodeUTF8((DataOutputStream)localObject, this.userName);
        if (this.password != null)
        {
          String str = new java/lang/String;
          str.<init>(this.password);
          encodeUTF8((DataOutputStream)localObject, str);
        }
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
      if (this.MqttVersion == 3) {
        encodeUTF8(localDataOutputStream, "MQIsdp");
      } else if (this.MqttVersion == 4) {
        encodeUTF8(localDataOutputStream, "MQTT");
      }
      localDataOutputStream.write(this.MqttVersion);
      int i = 0;
      if (this.cleanSession) {
        i = (byte)2;
      }
      int j = i;
      if (this.willMessage != null)
      {
        i = (byte)((byte)(i | 0x4) | this.willMessage.getQos() << 3);
        j = i;
        if (this.willMessage.isRetained()) {
          j = (byte)(i | 0x20);
        }
      }
      i = j;
      if (this.userName != null)
      {
        j = (byte)(j | 0x80);
        i = j;
        if (this.password != null) {
          i = (byte)(j | 0x40);
        }
      }
      localDataOutputStream.write(i);
      localDataOutputStream.writeShort(this.keepAliveInterval);
      localDataOutputStream.flush();
      localObject = ((ByteArrayOutputStream)localObject).toByteArray();
      return (byte[])localObject;
    }
    catch (IOException localIOException)
    {
      throw new MqttException(localIOException);
    }
  }
  
  public boolean isCleanSession()
  {
    return this.cleanSession;
  }
  
  public boolean isMessageIdRequired()
  {
    return false;
  }
  
  public String toString()
  {
    String str = super.toString();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str);
    localStringBuilder.append(" clientId ");
    localStringBuilder.append(this.clientId);
    localStringBuilder.append(" keepAliveInterval ");
    localStringBuilder.append(this.keepAliveInterval);
    return localStringBuilder.toString();
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttConnect.class
 *
 * Reversed by:           J
 */