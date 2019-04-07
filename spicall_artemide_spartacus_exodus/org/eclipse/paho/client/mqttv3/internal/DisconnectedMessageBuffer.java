package org.eclipse.paho.client.mqttv3.internal;

import java.util.ArrayList;
import org.eclipse.paho.client.mqttv3.BufferedMessage;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class DisconnectedMessageBuffer
  implements Runnable
{
  private static final String CLASS_NAME = "DisconnectedMessageBuffer";
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", "DisconnectedMessageBuffer");
  private Object bufLock = new Object();
  private ArrayList buffer;
  private DisconnectedBufferOptions bufferOpts;
  private IDisconnectedBufferCallback callback;
  
  public DisconnectedMessageBuffer(DisconnectedBufferOptions paramDisconnectedBufferOptions)
  {
    this.bufferOpts = paramDisconnectedBufferOptions;
    this.buffer = new ArrayList();
  }
  
  public void deleteMessage(int paramInt)
  {
    synchronized (this.bufLock)
    {
      this.buffer.remove(paramInt);
      return;
    }
  }
  
  public BufferedMessage getMessage(int paramInt)
  {
    synchronized (this.bufLock)
    {
      BufferedMessage localBufferedMessage = (BufferedMessage)this.buffer.get(paramInt);
      return localBufferedMessage;
    }
  }
  
  public int getMessageCount()
  {
    synchronized (this.bufLock)
    {
      int i = this.buffer.size();
      return i;
    }
  }
  
  public boolean isPersistBuffer()
  {
    return this.bufferOpts.isPersistBuffer();
  }
  
  public void putMessage(MqttWireMessage arg1, MqttToken paramMqttToken)
  {
    paramMqttToken = new BufferedMessage(???, paramMqttToken);
    synchronized (this.bufLock)
    {
      if (this.buffer.size() < this.bufferOpts.getBufferSize())
      {
        this.buffer.add(paramMqttToken);
      }
      else
      {
        if (this.bufferOpts.isDeleteOldestMessages() != true) {
          break label78;
        }
        this.buffer.remove(0);
        this.buffer.add(paramMqttToken);
      }
      return;
      label78:
      paramMqttToken = new org/eclipse/paho/client/mqttv3/MqttException;
      paramMqttToken.<init>(32203);
      throw paramMqttToken;
    }
  }
  
  public void run()
  {
    log.fine("DisconnectedMessageBuffer", "run", "516");
    for (;;)
    {
      if (getMessageCount() > 0) {
        try
        {
          BufferedMessage localBufferedMessage = getMessage(0);
          this.callback.publishBufferedMessage(localBufferedMessage);
          deleteMessage(0);
        }
        catch (MqttException localMqttException)
        {
          log.warning("DisconnectedMessageBuffer", "run", "517");
        }
      }
    }
  }
  
  public void setPublishCallback(IDisconnectedBufferCallback paramIDisconnectedBufferCallback)
  {
    this.callback = paramIDisconnectedBufferCallback;
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/DisconnectedMessageBuffer.class
 *
 * Reversed by:           J
 */