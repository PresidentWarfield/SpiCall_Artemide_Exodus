package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.InputStream;

public class MultiByteArrayInputStream
  extends InputStream
{
  private byte[] bytesA;
  private byte[] bytesB;
  private int lengthA;
  private int lengthB;
  private int offsetA;
  private int offsetB;
  private int pos = 0;
  
  public MultiByteArrayInputStream(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4)
  {
    this.bytesA = paramArrayOfByte1;
    this.bytesB = paramArrayOfByte2;
    this.offsetA = paramInt1;
    this.offsetB = paramInt3;
    this.lengthA = paramInt2;
    this.lengthB = paramInt4;
  }
  
  public int read()
  {
    int i = this.pos;
    int j = this.lengthA;
    if (i < j)
    {
      i = this.bytesA[(this.offsetA + i)];
    }
    else
    {
      if (i >= this.lengthB + j) {
        break label78;
      }
      i = this.bytesB[(this.offsetB + i - j)];
    }
    j = i;
    if (i < 0) {
      j = i + 256;
    }
    this.pos += 1;
    return j;
    label78:
    return -1;
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MultiByteArrayInputStream.class
 *
 * Reversed by:           J
 */