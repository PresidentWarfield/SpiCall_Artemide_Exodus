package org.eclipse.paho.client.mqttv3;

public class MqttMessage
{
  private boolean dup = false;
  private int messageId;
  private boolean mutable = true;
  private byte[] payload;
  private int qos = 1;
  private boolean retained = false;
  
  public MqttMessage()
  {
    setPayload(new byte[0]);
  }
  
  public MqttMessage(byte[] paramArrayOfByte)
  {
    setPayload(paramArrayOfByte);
  }
  
  public static void validateQos(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt <= 2)) {
      return;
    }
    throw new IllegalArgumentException();
  }
  
  protected void checkMutable()
  {
    if (this.mutable) {
      return;
    }
    throw new IllegalStateException();
  }
  
  public void clearPayload()
  {
    checkMutable();
    this.payload = new byte[0];
  }
  
  public int getId()
  {
    return this.messageId;
  }
  
  public byte[] getPayload()
  {
    return this.payload;
  }
  
  public int getQos()
  {
    return this.qos;
  }
  
  public boolean isDuplicate()
  {
    return this.dup;
  }
  
  public boolean isRetained()
  {
    return this.retained;
  }
  
  protected void setDuplicate(boolean paramBoolean)
  {
    this.dup = paramBoolean;
  }
  
  public void setId(int paramInt)
  {
    this.messageId = paramInt;
  }
  
  protected void setMutable(boolean paramBoolean)
  {
    this.mutable = paramBoolean;
  }
  
  public void setPayload(byte[] paramArrayOfByte)
  {
    checkMutable();
    if (paramArrayOfByte != null)
    {
      this.payload = paramArrayOfByte;
      return;
    }
    throw new NullPointerException();
  }
  
  public void setQos(int paramInt)
  {
    checkMutable();
    validateQos(paramInt);
    this.qos = paramInt;
  }
  
  public void setRetained(boolean paramBoolean)
  {
    checkMutable();
    this.retained = paramBoolean;
  }
  
  public String toString()
  {
    return new String(this.payload);
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/MqttMessage.class
 *
 * Reversed by:           J
 */