package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import org.eclipse.paho.client.mqttv3.internal.ClientState;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class MqttInputStream
  extends InputStream
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream";
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private ByteArrayOutputStream bais;
  private ClientState clientState = null;
  private DataInputStream in;
  private byte[] packet;
  private long packetLen;
  private long remLen;
  
  public MqttInputStream(ClientState paramClientState, InputStream paramInputStream)
  {
    this.clientState = paramClientState;
    this.in = new DataInputStream(paramInputStream);
    this.bais = new ByteArrayOutputStream();
    this.remLen = -1L;
  }
  
  private void readFully()
  {
    int i = this.bais.size();
    long l = this.packetLen;
    int j = (int)l;
    int k = (int)(this.remLen - l);
    if (k >= 0)
    {
      int m = 0;
      while (m < k) {
        try
        {
          int n = this.in.read(this.packet, i + j + m, k - m);
          this.clientState.notifyReceivedBytes(n);
          if (n >= 0) {
            m += n;
          } else {
            throw new EOFException();
          }
        }
        catch (SocketTimeoutException localSocketTimeoutException)
        {
          this.packetLen += m;
          throw localSocketTimeoutException;
        }
      }
      return;
    }
    throw new IndexOutOfBoundsException();
  }
  
  public int available()
  {
    return this.in.available();
  }
  
  public void close()
  {
    this.in.close();
  }
  
  public int read()
  {
    return this.in.read();
  }
  
  public MqttWireMessage readMqttWireMessage()
  {
    Object localObject1 = null;
    Object localObject2 = null;
    localObject4 = localObject1;
    try
    {
      if (this.remLen < 0L)
      {
        localObject4 = localObject1;
        this.bais.reset();
        localObject4 = localObject1;
        int i = this.in.readByte();
        localObject4 = localObject1;
        this.clientState.notifyReceivedBytes(1);
        int j = (byte)(i >>> 4 & 0xF);
        if ((j >= 1) && (j <= 14))
        {
          localObject4 = localObject1;
          this.remLen = MqttWireMessage.readMBI(this.in).getValue();
          localObject4 = localObject1;
          this.bais.write(i);
          localObject4 = localObject1;
          this.bais.write(MqttWireMessage.encodeMBI(this.remLen));
          localObject4 = localObject1;
          this.packet = new byte[(int)(this.bais.size() + this.remLen)];
          localObject4 = localObject1;
          this.packetLen = 0L;
        }
        else
        {
          localObject4 = localObject1;
          throw ExceptionHelper.createMqttException(32108);
        }
      }
      localObject4 = localObject1;
      if (this.remLen >= 0L)
      {
        localObject4 = localObject1;
        readFully();
        localObject4 = localObject1;
        this.remLen = -1L;
        localObject4 = localObject1;
        localObject2 = this.bais.toByteArray();
        localObject4 = localObject1;
        System.arraycopy(localObject2, 0, this.packet, 0, localObject2.length);
        localObject4 = localObject1;
        localObject2 = MqttWireMessage.createWireMessage(this.packet);
        localObject4 = localObject2;
        log.fine(CLASS_NAME, "readMqttWireMessage", "501", new Object[] { localObject2 });
      }
    }
    catch (SocketTimeoutException localSocketTimeoutException)
    {
      for (;;)
      {
        Object localObject3 = localObject4;
      }
    }
    return (MqttWireMessage)localObject2;
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttInputStream.class
 *
 * Reversed by:           J
 */