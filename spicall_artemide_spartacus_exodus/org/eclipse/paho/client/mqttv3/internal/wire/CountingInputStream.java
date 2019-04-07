package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.InputStream;

public class CountingInputStream
  extends InputStream
{
  private int counter;
  private InputStream in;
  
  public CountingInputStream(InputStream paramInputStream)
  {
    this.in = paramInputStream;
    this.counter = 0;
  }
  
  public int getCounter()
  {
    return this.counter;
  }
  
  public int read()
  {
    int i = this.in.read();
    if (i != -1) {
      this.counter += 1;
    }
    return i;
  }
  
  public void resetCounter()
  {
    this.counter = 0;
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/CountingInputStream.class
 *
 * Reversed by:           J
 */