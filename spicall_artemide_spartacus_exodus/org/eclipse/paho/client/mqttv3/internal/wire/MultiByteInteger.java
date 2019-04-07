package org.eclipse.paho.client.mqttv3.internal.wire;

public class MultiByteInteger
{
  private int length;
  private long value;
  
  public MultiByteInteger(long paramLong)
  {
    this(paramLong, -1);
  }
  
  public MultiByteInteger(long paramLong, int paramInt)
  {
    this.value = paramLong;
    this.length = paramInt;
  }
  
  public int getEncodedLength()
  {
    return this.length;
  }
  
  public long getValue()
  {
    return this.value;
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MultiByteInteger.class
 *
 * Reversed by:           J
 */