package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttOutputStream;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class CommsSender
  implements Runnable
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.CommsSender";
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private ClientComms clientComms = null;
  private ClientState clientState = null;
  private Object lifecycle = new Object();
  private MqttOutputStream out;
  private boolean running = false;
  private final Semaphore runningSemaphore = new Semaphore(1);
  private Thread sendThread = null;
  private Future senderFuture;
  private String threadName;
  private CommsTokenStore tokenStore = null;
  
  public CommsSender(ClientComms paramClientComms, ClientState paramClientState, CommsTokenStore paramCommsTokenStore, OutputStream paramOutputStream)
  {
    this.out = new MqttOutputStream(paramClientState, paramOutputStream);
    this.clientComms = paramClientComms;
    this.clientState = paramClientState;
    this.tokenStore = paramCommsTokenStore;
    log.setResourceName(paramClientComms.getClient().getClientId());
  }
  
  private void handleRunException(MqttWireMessage paramMqttWireMessage, Exception paramException)
  {
    log.fine(CLASS_NAME, "handleRunException", "804", null, paramException);
    if (!(paramException instanceof MqttException)) {
      paramMqttWireMessage = new MqttException(32109, paramException);
    } else {
      paramMqttWireMessage = (MqttException)paramException;
    }
    this.running = false;
    this.clientComms.shutdownConnection(null, paramMqttWireMessage);
  }
  
  public void run()
  {
    this.sendThread = Thread.currentThread();
    this.sendThread.setName(this.threadName);
    try
    {
      this.runningSemaphore.acquire();
      Object localObject1 = null;
      try
      {
        while (this.running)
        {
          Object localObject5 = this.out;
          if (localObject5 == null) {
            break;
          }
          Object localObject6 = localObject1;
          try
          {
            localObject5 = this.clientState.get();
            MqttToken localMqttToken;
            Object localObject2;
            if (localObject5 != null)
            {
              localObject6 = localObject5;
              localObject1 = localObject5;
              log.fine(CLASS_NAME, "run", "802", new Object[] { ((MqttWireMessage)localObject5).getKey(), localObject5 });
              localObject6 = localObject5;
              localObject1 = localObject5;
              if ((localObject5 instanceof MqttAck))
              {
                localObject6 = localObject5;
                localObject1 = localObject5;
                this.out.write((MqttWireMessage)localObject5);
                localObject6 = localObject5;
                localObject1 = localObject5;
                this.out.flush();
                localObject1 = localObject5;
              }
              else
              {
                localObject6 = localObject5;
                localObject1 = localObject5;
                localMqttToken = this.tokenStore.getToken((MqttWireMessage)localObject5);
                localObject1 = localObject5;
                if (localMqttToken != null)
                {
                  localObject6 = localObject5;
                  localObject1 = localObject5;
                  try
                  {
                    this.out.write((MqttWireMessage)localObject5);
                    try
                    {
                      this.out.flush();
                    }
                    catch (IOException localIOException)
                    {
                      if (!(localObject5 instanceof MqttDisconnect)) {
                        break label200;
                      }
                    }
                    this.clientState.notifySent((MqttWireMessage)localObject5);
                    localObject2 = localObject5;
                    continue;
                  }
                  finally
                  {
                    label200:
                    localObject6 = localObject5;
                    localObject2 = localObject5;
                  }
                  throw ((Throwable)localObject2);
                }
              }
            }
            else
            {
              localObject6 = localObject5;
              localObject2 = localObject5;
              log.fine(CLASS_NAME, "run", "803");
              localObject6 = localObject5;
              localObject2 = localObject5;
              this.running = false;
              localObject2 = localObject5;
            }
          }
          catch (Exception localException)
          {
            handleRunException((MqttWireMessage)localObject6, localException);
            localObject3 = localObject6;
          }
          catch (MqttException localMqttException)
          {
            Object localObject3;
            handleRunException((MqttWireMessage)localObject3, localMqttException);
          }
        }
        this.running = false;
        this.runningSemaphore.release();
        log.fine(CLASS_NAME, "run", "805");
        return;
      }
      finally
      {
        this.running = false;
        this.runningSemaphore.release();
      }
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      this.running = false;
    }
  }
  
  public void start(String arg1, ExecutorService paramExecutorService)
  {
    this.threadName = ???;
    synchronized (this.lifecycle)
    {
      if (!this.running)
      {
        this.running = true;
        this.senderFuture = paramExecutorService.submit(this);
      }
      return;
    }
  }
  
  /* Error */
  public void stop()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 54	org/eclipse/paho/client/mqttv3/internal/CommsSender:lifecycle	Ljava/lang/Object;
    //   4: astore_1
    //   5: aload_1
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 192	org/eclipse/paho/client/mqttv3/internal/CommsSender:senderFuture	Ljava/util/concurrent/Future;
    //   11: ifnull +14 -> 25
    //   14: aload_0
    //   15: getfield 192	org/eclipse/paho/client/mqttv3/internal/CommsSender:senderFuture	Ljava/util/concurrent/Future;
    //   18: iconst_1
    //   19: invokeinterface 199 2 0
    //   24: pop
    //   25: getstatic 45	org/eclipse/paho/client/mqttv3/internal/CommsSender:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   28: getstatic 37	org/eclipse/paho/client/mqttv3/internal/CommsSender:CLASS_NAME	Ljava/lang/String;
    //   31: ldc -56
    //   33: ldc -54
    //   35: invokeinterface 175 4 0
    //   40: aload_0
    //   41: getfield 52	org/eclipse/paho/client/mqttv3/internal/CommsSender:running	Z
    //   44: ifeq +85 -> 129
    //   47: aload_0
    //   48: iconst_0
    //   49: putfield 52	org/eclipse/paho/client/mqttv3/internal/CommsSender:running	Z
    //   52: invokestatic 125	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   55: aload_0
    //   56: getfield 62	org/eclipse/paho/client/mqttv3/internal/CommsSender:sendThread	Ljava/lang/Thread;
    //   59: invokevirtual 206	java/lang/Object:equals	(Ljava/lang/Object;)Z
    //   62: istore_2
    //   63: iload_2
    //   64: ifne +65 -> 129
    //   67: aload_0
    //   68: getfield 52	org/eclipse/paho/client/mqttv3/internal/CommsSender:running	Z
    //   71: ifeq +27 -> 98
    //   74: aload_0
    //   75: getfield 56	org/eclipse/paho/client/mqttv3/internal/CommsSender:clientState	Lorg/eclipse/paho/client/mqttv3/internal/ClientState;
    //   78: invokevirtual 209	org/eclipse/paho/client/mqttv3/internal/ClientState:notifyQueueLock	()V
    //   81: aload_0
    //   82: getfield 69	org/eclipse/paho/client/mqttv3/internal/CommsSender:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   85: ldc2_w 210
    //   88: getstatic 217	java/util/concurrent/TimeUnit:MILLISECONDS	Ljava/util/concurrent/TimeUnit;
    //   91: invokevirtual 221	java/util/concurrent/Semaphore:tryAcquire	(JLjava/util/concurrent/TimeUnit;)Z
    //   94: pop
    //   95: goto -28 -> 67
    //   98: aload_0
    //   99: getfield 69	org/eclipse/paho/client/mqttv3/internal/CommsSender:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   102: astore_3
    //   103: aload_3
    //   104: invokevirtual 180	java/util/concurrent/Semaphore:release	()V
    //   107: goto +22 -> 129
    //   110: astore_3
    //   111: aload_0
    //   112: getfield 69	org/eclipse/paho/client/mqttv3/internal/CommsSender:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   115: invokevirtual 180	java/util/concurrent/Semaphore:release	()V
    //   118: aload_3
    //   119: athrow
    //   120: astore_3
    //   121: aload_0
    //   122: getfield 69	org/eclipse/paho/client/mqttv3/internal/CommsSender:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   125: astore_3
    //   126: goto -23 -> 103
    //   129: aload_0
    //   130: aconst_null
    //   131: putfield 62	org/eclipse/paho/client/mqttv3/internal/CommsSender:sendThread	Ljava/lang/Thread;
    //   134: getstatic 45	org/eclipse/paho/client/mqttv3/internal/CommsSender:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   137: getstatic 37	org/eclipse/paho/client/mqttv3/internal/CommsSender:CLASS_NAME	Ljava/lang/String;
    //   140: ldc -56
    //   142: ldc -33
    //   144: invokeinterface 175 4 0
    //   149: aload_1
    //   150: monitorexit
    //   151: return
    //   152: astore_3
    //   153: aload_1
    //   154: monitorexit
    //   155: aload_3
    //   156: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	157	0	this	CommsSender
    //   4	150	1	localObject1	Object
    //   62	2	2	bool	boolean
    //   102	2	3	localSemaphore1	Semaphore
    //   110	9	3	localObject2	Object
    //   120	1	3	localInterruptedException	InterruptedException
    //   125	1	3	localSemaphore2	Semaphore
    //   152	4	3	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   67	95	110	finally
    //   67	95	120	java/lang/InterruptedException
    //   7	25	152	finally
    //   25	63	152	finally
    //   98	103	152	finally
    //   103	107	152	finally
    //   111	120	152	finally
    //   121	126	152	finally
    //   129	151	152	finally
    //   153	155	152	finally
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/CommsSender.class
 *
 * Reversed by:           J
 */