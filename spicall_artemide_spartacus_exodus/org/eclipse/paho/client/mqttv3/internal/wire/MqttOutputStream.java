package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import org.eclipse.paho.client.mqttv3.internal.ClientState;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class MqttOutputStream
  extends OutputStream
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.wire.MqttOutputStream";
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private ClientState clientState = null;
  private BufferedOutputStream out;
  
  public MqttOutputStream(ClientState paramClientState, OutputStream paramOutputStream)
  {
    this.clientState = paramClientState;
    this.out = new BufferedOutputStream(paramOutputStream);
  }
  
  public void close()
  {
    this.out.close();
  }
  
  public void flush()
  {
    this.out.flush();
  }
  
  public void write(int paramInt)
  {
    this.out.write(paramInt);
  }
  
  public void write(MqttWireMessage paramMqttWireMessage)
  {
    byte[] arrayOfByte1 = paramMqttWireMessage.getHeader();
    byte[] arrayOfByte2 = paramMqttWireMessage.getPayload();
    this.out.write(arrayOfByte1, 0, arrayOfByte1.length);
    this.clientState.notifySentBytes(arrayOfByte1.length);
    int i = 0;
    while (i < arrayOfByte2.length)
    {
      int j = Math.min(1024, arrayOfByte2.length - i);
      this.out.write(arrayOfByte2, i, j);
      i += 1024;
      this.clientState.notifySentBytes(j);
    }
    log.fine(CLASS_NAME, "write", "529", new Object[] { paramMqttWireMessage });
  }
  
  public void write(byte[] paramArrayOfByte)
  {
    this.out.write(paramArrayOfByte);
    this.clientState.notifySentBytes(paramArrayOfByte.length);
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.out.write(paramArrayOfByte, paramInt1, paramInt2);
    this.clientState.notifySentBytes(paramInt2);
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttOutputStream.class
 *
 * Reversed by:           J
 */