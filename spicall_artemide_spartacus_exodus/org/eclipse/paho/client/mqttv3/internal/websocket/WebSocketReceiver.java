package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class WebSocketReceiver
  implements Runnable
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketReceiver";
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private InputStream input;
  private Object lifecycle = new Object();
  private PipedOutputStream pipedOutputStream;
  private Thread receiverThread = null;
  private volatile boolean receiving;
  private boolean running = false;
  private boolean stopping = false;
  
  public WebSocketReceiver(InputStream paramInputStream, PipedInputStream paramPipedInputStream)
  {
    this.input = paramInputStream;
    this.pipedOutputStream = new PipedOutputStream();
    paramPipedInputStream.connect(this.pipedOutputStream);
  }
  
  private void closeOutputStream()
  {
    try
    {
      this.pipedOutputStream.close();
      return;
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
  }
  
  public boolean isReceiving()
  {
    return this.receiving;
  }
  
  public boolean isRunning()
  {
    return this.running;
  }
  
  public void run()
  {
    while ((this.running) && (this.input != null)) {
      try
      {
        log.fine(CLASS_NAME, "run", "852");
        boolean bool;
        if (this.input.available() > 0) {
          bool = true;
        } else {
          bool = false;
        }
        this.receiving = bool;
        Object localObject = new org/eclipse/paho/client/mqttv3/internal/websocket/WebSocketFrame;
        ((WebSocketFrame)localObject).<init>(this.input);
        if (!((WebSocketFrame)localObject).isCloseFlag())
        {
          for (int i = 0; i < ((WebSocketFrame)localObject).getPayload().length; i++) {
            this.pipedOutputStream.write(localObject.getPayload()[i]);
          }
          this.pipedOutputStream.flush();
        }
        else
        {
          if (!this.stopping) {
            break label125;
          }
        }
        this.receiving = false;
        continue;
        label125:
        localObject = new java/io/IOException;
        ((IOException)localObject).<init>("Server sent a WebSocket Frame with the Stop OpCode");
        throw ((Throwable)localObject);
      }
      catch (IOException localIOException)
      {
        stop();
      }
    }
  }
  
  public void start(String paramString)
  {
    log.fine(CLASS_NAME, "start", "855");
    synchronized (this.lifecycle)
    {
      if (!this.running)
      {
        this.running = true;
        Thread localThread = new java/lang/Thread;
        localThread.<init>(this, paramString);
        this.receiverThread = localThread;
        this.receiverThread.start();
      }
      return;
    }
  }
  
  public void stop()
  {
    int i = 1;
    this.stopping = true;
    synchronized (this.lifecycle)
    {
      log.fine(CLASS_NAME, "stop", "850");
      if (this.running)
      {
        this.running = false;
        this.receiving = false;
        closeOutputStream();
      }
      else
      {
        i = 0;
      }
      if ((i == 0) || (Thread.currentThread().equals(this.receiverThread))) {}
    }
    try
    {
      this.receiverThread.join();
      this.receiverThread = null;
      log.fine(CLASS_NAME, "stop", "851");
      return;
      localObject2 = finally;
      throw ((Throwable)localObject2);
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;) {}
    }
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/websocket/WebSocketReceiver.class
 *
 * Reversed by:           J
 */