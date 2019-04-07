package org.eclipse.paho.client.mqttv3.internal;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubComp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class CommsCallback
  implements Runnable
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.CommsCallback";
  private static final int INBOUND_QUEUE_SIZE = 10;
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private Future callbackFuture;
  private Thread callbackThread;
  private Hashtable callbacks;
  private ClientComms clientComms;
  private ClientState clientState;
  private Vector completeQueue;
  private Object lifecycle = new Object();
  private boolean manualAcks = false;
  private Vector messageQueue;
  private MqttCallback mqttCallback;
  private boolean quiescing = false;
  private MqttCallbackExtended reconnectInternalCallback;
  public boolean running = false;
  private final Semaphore runningSemaphore = new Semaphore(1);
  private Object spaceAvailable = new Object();
  private String threadName;
  private Object workAvailable = new Object();
  
  CommsCallback(ClientComms paramClientComms)
  {
    this.clientComms = paramClientComms;
    this.messageQueue = new Vector(10);
    this.completeQueue = new Vector(10);
    this.callbacks = new Hashtable();
    log.setResourceName(paramClientComms.getClient().getClientId());
  }
  
  private void handleActionComplete(MqttToken paramMqttToken)
  {
    try
    {
      log.fine(CLASS_NAME, "handleActionComplete", "705", new Object[] { paramMqttToken.internalTok.getKey() });
      if (paramMqttToken.isComplete()) {
        this.clientState.notifyComplete(paramMqttToken);
      }
      paramMqttToken.internalTok.notifyComplete();
      if (!paramMqttToken.internalTok.isNotified())
      {
        if ((this.mqttCallback != null) && ((paramMqttToken instanceof MqttDeliveryToken)) && (paramMqttToken.isComplete())) {
          this.mqttCallback.deliveryComplete((MqttDeliveryToken)paramMqttToken);
        }
        fireActionEvent(paramMqttToken);
      }
      if ((paramMqttToken.isComplete()) && (((paramMqttToken instanceof MqttDeliveryToken)) || ((paramMqttToken.getActionCallback() instanceof IMqttActionListener)))) {
        paramMqttToken.internalTok.setNotified(true);
      }
      return;
    }
    finally {}
  }
  
  private void handleMessage(MqttPublish paramMqttPublish)
  {
    Object localObject = paramMqttPublish.getTopicName();
    log.fine(CLASS_NAME, "handleMessage", "713", new Object[] { new Integer(paramMqttPublish.getMessageId()), localObject });
    deliverMessage((String)localObject, paramMqttPublish.getMessageId(), paramMqttPublish.getMessage());
    if (!this.manualAcks) {
      if (paramMqttPublish.getMessage().getQos() == 1)
      {
        this.clientComms.internalSend(new MqttPubAck(paramMqttPublish), new MqttToken(this.clientComms.getClient().getClientId()));
      }
      else if (paramMqttPublish.getMessage().getQos() == 2)
      {
        this.clientComms.deliveryComplete(paramMqttPublish);
        paramMqttPublish = new MqttPubComp(paramMqttPublish);
        localObject = this.clientComms;
        ((ClientComms)localObject).internalSend(paramMqttPublish, new MqttToken(((ClientComms)localObject).getClient().getClientId()));
      }
    }
  }
  
  public void asyncOperationComplete(MqttToken paramMqttToken)
  {
    if (this.running)
    {
      this.completeQueue.addElement(paramMqttToken);
      synchronized (this.workAvailable)
      {
        log.fine(CLASS_NAME, "asyncOperationComplete", "715", new Object[] { paramMqttToken.internalTok.getKey() });
        this.workAvailable.notifyAll();
      }
    }
    try
    {
      handleActionComplete(paramMqttToken);
    }
    catch (Throwable paramMqttToken)
    {
      log.fine(CLASS_NAME, "asyncOperationComplete", "719", null, paramMqttToken);
      this.clientComms.shutdownConnection(null, new MqttException(paramMqttToken));
    }
  }
  
  public void connectionLost(MqttException paramMqttException)
  {
    try
    {
      if ((this.mqttCallback != null) && (paramMqttException != null))
      {
        log.fine(CLASS_NAME, "connectionLost", "708", new Object[] { paramMqttException });
        this.mqttCallback.connectionLost(paramMqttException);
      }
      if ((this.reconnectInternalCallback != null) && (paramMqttException != null)) {
        this.reconnectInternalCallback.connectionLost(paramMqttException);
      }
    }
    catch (Throwable paramMqttException)
    {
      log.fine(CLASS_NAME, "connectionLost", "720", new Object[] { paramMqttException });
    }
  }
  
  protected boolean deliverMessage(String paramString, int paramInt, MqttMessage paramMqttMessage)
  {
    Enumeration localEnumeration = this.callbacks.keys();
    boolean bool1 = false;
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      if (MqttTopic.isMatched(str, paramString))
      {
        paramMqttMessage.setId(paramInt);
        ((IMqttMessageListener)this.callbacks.get(str)).messageArrived(paramString, paramMqttMessage);
        bool1 = true;
      }
    }
    boolean bool2 = bool1;
    if (this.mqttCallback != null)
    {
      bool2 = bool1;
      if (!bool1)
      {
        paramMqttMessage.setId(paramInt);
        this.mqttCallback.messageArrived(paramString, paramMqttMessage);
        bool2 = true;
      }
    }
    return bool2;
  }
  
  public void fireActionEvent(MqttToken paramMqttToken)
  {
    if (paramMqttToken != null)
    {
      IMqttActionListener localIMqttActionListener = paramMqttToken.getActionCallback();
      if (localIMqttActionListener != null) {
        if (paramMqttToken.getException() == null)
        {
          log.fine(CLASS_NAME, "fireActionEvent", "716", new Object[] { paramMqttToken.internalTok.getKey() });
          localIMqttActionListener.onSuccess(paramMqttToken);
        }
        else
        {
          log.fine(CLASS_NAME, "fireActionEvent", "716", new Object[] { paramMqttToken.internalTok.getKey() });
          localIMqttActionListener.onFailure(paramMqttToken, paramMqttToken.getException());
        }
      }
    }
  }
  
  protected Thread getThread()
  {
    return this.callbackThread;
  }
  
  public boolean isQuiesced()
  {
    return (this.quiescing) && (this.completeQueue.size() == 0) && (this.messageQueue.size() == 0);
  }
  
  public void messageArrived(MqttPublish paramMqttPublish)
  {
    if ((this.mqttCallback != null) || (this.callbacks.size() > 0)) {}
    for (;;)
    {
      synchronized (this.spaceAvailable)
      {
        if ((this.running) && (!this.quiescing))
        {
          int i = this.messageQueue.size();
          if (i < 10) {}
        }
      }
      try
      {
        log.fine(CLASS_NAME, "messageArrived", "709");
        this.spaceAvailable.wait(200L);
      }
      catch (InterruptedException localInterruptedException) {}
      if (!this.quiescing)
      {
        this.messageQueue.addElement(paramMqttPublish);
        synchronized (this.workAvailable)
        {
          log.fine(CLASS_NAME, "messageArrived", "710");
          this.workAvailable.notifyAll();
        }
      }
      return;
      paramMqttPublish = finally;
      throw paramMqttPublish;
    }
  }
  
  public void messageArrivedComplete(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 1)
    {
      this.clientComms.internalSend(new MqttPubAck(paramInt1), new MqttToken(this.clientComms.getClient().getClientId()));
    }
    else if (paramInt2 == 2)
    {
      this.clientComms.deliveryComplete(paramInt1);
      MqttPubComp localMqttPubComp = new MqttPubComp(paramInt1);
      ClientComms localClientComms = this.clientComms;
      localClientComms.internalSend(localMqttPubComp, new MqttToken(localClientComms.getClient().getClientId()));
    }
  }
  
  public void quiesce()
  {
    this.quiescing = true;
    synchronized (this.spaceAvailable)
    {
      log.fine(CLASS_NAME, "quiesce", "711");
      this.spaceAvailable.notifyAll();
      return;
    }
  }
  
  public void removeMessageListener(String paramString)
  {
    this.callbacks.remove(paramString);
  }
  
  public void removeMessageListeners()
  {
    this.callbacks.clear();
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 359	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   4: putfield 314	org/eclipse/paho/client/mqttv3/internal/CommsCallback:callbackThread	Ljava/lang/Thread;
    //   7: aload_0
    //   8: getfield 314	org/eclipse/paho/client/mqttv3/internal/CommsCallback:callbackThread	Ljava/lang/Thread;
    //   11: aload_0
    //   12: getfield 361	org/eclipse/paho/client/mqttv3/internal/CommsCallback:threadName	Ljava/lang/String;
    //   15: invokevirtual 364	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   18: aload_0
    //   19: getfield 81	org/eclipse/paho/client/mqttv3/internal/CommsCallback:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   22: invokevirtual 367	java/util/concurrent/Semaphore:acquire	()V
    //   25: aload_0
    //   26: getfield 64	org/eclipse/paho/client/mqttv3/internal/CommsCallback:running	Z
    //   29: ifeq +407 -> 436
    //   32: aload_0
    //   33: getfield 70	org/eclipse/paho/client/mqttv3/internal/CommsCallback:workAvailable	Ljava/lang/Object;
    //   36: astore_1
    //   37: aload_1
    //   38: monitorenter
    //   39: aload_0
    //   40: getfield 64	org/eclipse/paho/client/mqttv3/internal/CommsCallback:running	Z
    //   43: ifeq +47 -> 90
    //   46: aload_0
    //   47: getfield 88	org/eclipse/paho/client/mqttv3/internal/CommsCallback:messageQueue	Ljava/util/Vector;
    //   50: invokevirtual 370	java/util/Vector:isEmpty	()Z
    //   53: ifeq +37 -> 90
    //   56: aload_0
    //   57: getfield 90	org/eclipse/paho/client/mqttv3/internal/CommsCallback:completeQueue	Ljava/util/Vector;
    //   60: invokevirtual 370	java/util/Vector:isEmpty	()Z
    //   63: ifeq +27 -> 90
    //   66: getstatic 57	org/eclipse/paho/client/mqttv3/internal/CommsCallback:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   69: getstatic 49	org/eclipse/paho/client/mqttv3/internal/CommsCallback:CLASS_NAME	Ljava/lang/String;
    //   72: ldc_w 371
    //   75: ldc_w 373
    //   78: invokeinterface 327 4 0
    //   83: aload_0
    //   84: getfield 70	org/eclipse/paho/client/mqttv3/internal/CommsCallback:workAvailable	Ljava/lang/Object;
    //   87: invokevirtual 375	java/lang/Object:wait	()V
    //   90: aload_1
    //   91: monitorexit
    //   92: goto +16 -> 108
    //   95: astore_2
    //   96: aload_1
    //   97: monitorexit
    //   98: aload_2
    //   99: athrow
    //   100: astore_2
    //   101: goto +288 -> 389
    //   104: astore_3
    //   105: goto +192 -> 297
    //   108: aload_0
    //   109: getfield 64	org/eclipse/paho/client/mqttv3/internal/CommsCallback:running	Z
    //   112: ifeq +122 -> 234
    //   115: aload_0
    //   116: getfield 90	org/eclipse/paho/client/mqttv3/internal/CommsCallback:completeQueue	Ljava/util/Vector;
    //   119: astore_2
    //   120: aload_2
    //   121: monitorenter
    //   122: aload_0
    //   123: getfield 90	org/eclipse/paho/client/mqttv3/internal/CommsCallback:completeQueue	Ljava/util/Vector;
    //   126: invokevirtual 370	java/util/Vector:isEmpty	()Z
    //   129: ifne +26 -> 155
    //   132: aload_0
    //   133: getfield 90	org/eclipse/paho/client/mqttv3/internal/CommsCallback:completeQueue	Ljava/util/Vector;
    //   136: iconst_0
    //   137: invokevirtual 379	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   140: checkcast 120	org/eclipse/paho/client/mqttv3/MqttToken
    //   143: astore_1
    //   144: aload_0
    //   145: getfield 90	org/eclipse/paho/client/mqttv3/internal/CommsCallback:completeQueue	Ljava/util/Vector;
    //   148: iconst_0
    //   149: invokevirtual 382	java/util/Vector:removeElementAt	(I)V
    //   152: goto +5 -> 157
    //   155: aconst_null
    //   156: astore_1
    //   157: aload_2
    //   158: monitorexit
    //   159: aload_1
    //   160: ifnull +8 -> 168
    //   163: aload_0
    //   164: aload_1
    //   165: invokespecial 232	org/eclipse/paho/client/mqttv3/internal/CommsCallback:handleActionComplete	(Lorg/eclipse/paho/client/mqttv3/MqttToken;)V
    //   168: aload_0
    //   169: getfield 88	org/eclipse/paho/client/mqttv3/internal/CommsCallback:messageQueue	Ljava/util/Vector;
    //   172: astore_2
    //   173: aload_2
    //   174: monitorenter
    //   175: aload_0
    //   176: getfield 88	org/eclipse/paho/client/mqttv3/internal/CommsCallback:messageQueue	Ljava/util/Vector;
    //   179: invokevirtual 370	java/util/Vector:isEmpty	()Z
    //   182: ifne +26 -> 208
    //   185: aload_0
    //   186: getfield 88	org/eclipse/paho/client/mqttv3/internal/CommsCallback:messageQueue	Ljava/util/Vector;
    //   189: iconst_0
    //   190: invokevirtual 379	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   193: checkcast 176	org/eclipse/paho/client/mqttv3/internal/wire/MqttPublish
    //   196: astore_1
    //   197: aload_0
    //   198: getfield 88	org/eclipse/paho/client/mqttv3/internal/CommsCallback:messageQueue	Ljava/util/Vector;
    //   201: iconst_0
    //   202: invokevirtual 382	java/util/Vector:removeElementAt	(I)V
    //   205: goto +5 -> 210
    //   208: aconst_null
    //   209: astore_1
    //   210: aload_2
    //   211: monitorexit
    //   212: aload_1
    //   213: ifnull +21 -> 234
    //   216: aload_0
    //   217: aload_1
    //   218: invokespecial 384	org/eclipse/paho/client/mqttv3/internal/CommsCallback:handleMessage	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttPublish;)V
    //   221: goto +13 -> 234
    //   224: astore_1
    //   225: aload_2
    //   226: monitorexit
    //   227: aload_1
    //   228: athrow
    //   229: astore_1
    //   230: aload_2
    //   231: monitorexit
    //   232: aload_1
    //   233: athrow
    //   234: aload_0
    //   235: getfield 66	org/eclipse/paho/client/mqttv3/internal/CommsCallback:quiescing	Z
    //   238: ifeq +11 -> 249
    //   241: aload_0
    //   242: getfield 139	org/eclipse/paho/client/mqttv3/internal/CommsCallback:clientState	Lorg/eclipse/paho/client/mqttv3/internal/ClientState;
    //   245: invokevirtual 387	org/eclipse/paho/client/mqttv3/internal/ClientState:checkQuiesceLock	()Z
    //   248: pop
    //   249: aload_0
    //   250: getfield 81	org/eclipse/paho/client/mqttv3/internal/CommsCallback:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   253: invokevirtual 390	java/util/concurrent/Semaphore:release	()V
    //   256: aload_0
    //   257: getfield 72	org/eclipse/paho/client/mqttv3/internal/CommsCallback:spaceAvailable	Ljava/lang/Object;
    //   260: astore_1
    //   261: aload_1
    //   262: monitorenter
    //   263: getstatic 57	org/eclipse/paho/client/mqttv3/internal/CommsCallback:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   266: getstatic 49	org/eclipse/paho/client/mqttv3/internal/CommsCallback:CLASS_NAME	Ljava/lang/String;
    //   269: ldc_w 371
    //   272: ldc_w 392
    //   275: invokeinterface 327 4 0
    //   280: aload_0
    //   281: getfield 72	org/eclipse/paho/client/mqttv3/internal/CommsCallback:spaceAvailable	Ljava/lang/Object;
    //   284: invokevirtual 230	java/lang/Object:notifyAll	()V
    //   287: aload_1
    //   288: monitorexit
    //   289: goto -264 -> 25
    //   292: astore_2
    //   293: aload_1
    //   294: monitorexit
    //   295: aload_2
    //   296: athrow
    //   297: getstatic 57	org/eclipse/paho/client/mqttv3/internal/CommsCallback:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   300: getstatic 49	org/eclipse/paho/client/mqttv3/internal/CommsCallback:CLASS_NAME	Ljava/lang/String;
    //   303: ldc_w 371
    //   306: ldc_w 394
    //   309: aconst_null
    //   310: aload_3
    //   311: invokeinterface 237 6 0
    //   316: aload_0
    //   317: iconst_0
    //   318: putfield 64	org/eclipse/paho/client/mqttv3/internal/CommsCallback:running	Z
    //   321: aload_0
    //   322: getfield 83	org/eclipse/paho/client/mqttv3/internal/CommsCallback:clientComms	Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;
    //   325: astore_1
    //   326: new 239	org/eclipse/paho/client/mqttv3/MqttException
    //   329: astore_2
    //   330: aload_2
    //   331: aload_3
    //   332: invokespecial 242	org/eclipse/paho/client/mqttv3/MqttException:<init>	(Ljava/lang/Throwable;)V
    //   335: aload_1
    //   336: aconst_null
    //   337: aload_2
    //   338: invokevirtual 246	org/eclipse/paho/client/mqttv3/internal/ClientComms:shutdownConnection	(Lorg/eclipse/paho/client/mqttv3/MqttToken;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
    //   341: aload_0
    //   342: getfield 81	org/eclipse/paho/client/mqttv3/internal/CommsCallback:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   345: invokevirtual 390	java/util/concurrent/Semaphore:release	()V
    //   348: aload_0
    //   349: getfield 72	org/eclipse/paho/client/mqttv3/internal/CommsCallback:spaceAvailable	Ljava/lang/Object;
    //   352: astore_2
    //   353: aload_2
    //   354: monitorenter
    //   355: getstatic 57	org/eclipse/paho/client/mqttv3/internal/CommsCallback:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   358: getstatic 49	org/eclipse/paho/client/mqttv3/internal/CommsCallback:CLASS_NAME	Ljava/lang/String;
    //   361: ldc_w 371
    //   364: ldc_w 392
    //   367: invokeinterface 327 4 0
    //   372: aload_0
    //   373: getfield 72	org/eclipse/paho/client/mqttv3/internal/CommsCallback:spaceAvailable	Ljava/lang/Object;
    //   376: invokevirtual 230	java/lang/Object:notifyAll	()V
    //   379: aload_2
    //   380: monitorexit
    //   381: goto -356 -> 25
    //   384: astore_1
    //   385: aload_2
    //   386: monitorexit
    //   387: aload_1
    //   388: athrow
    //   389: aload_0
    //   390: getfield 81	org/eclipse/paho/client/mqttv3/internal/CommsCallback:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   393: invokevirtual 390	java/util/concurrent/Semaphore:release	()V
    //   396: aload_0
    //   397: getfield 72	org/eclipse/paho/client/mqttv3/internal/CommsCallback:spaceAvailable	Ljava/lang/Object;
    //   400: astore_1
    //   401: aload_1
    //   402: monitorenter
    //   403: getstatic 57	org/eclipse/paho/client/mqttv3/internal/CommsCallback:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   406: getstatic 49	org/eclipse/paho/client/mqttv3/internal/CommsCallback:CLASS_NAME	Ljava/lang/String;
    //   409: ldc_w 371
    //   412: ldc_w 392
    //   415: invokeinterface 327 4 0
    //   420: aload_0
    //   421: getfield 72	org/eclipse/paho/client/mqttv3/internal/CommsCallback:spaceAvailable	Ljava/lang/Object;
    //   424: invokevirtual 230	java/lang/Object:notifyAll	()V
    //   427: aload_1
    //   428: monitorexit
    //   429: aload_2
    //   430: athrow
    //   431: astore_2
    //   432: aload_1
    //   433: monitorexit
    //   434: aload_2
    //   435: athrow
    //   436: return
    //   437: astore_1
    //   438: aload_0
    //   439: iconst_0
    //   440: putfield 64	org/eclipse/paho/client/mqttv3/internal/CommsCallback:running	Z
    //   443: return
    //   444: astore_1
    //   445: goto -337 -> 108
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	448	0	this	CommsCallback
    //   224	4	1	localObject2	Object
    //   229	4	1	localObject3	Object
    //   384	4	1	localObject5	Object
    //   437	1	1	localInterruptedException1	InterruptedException
    //   444	1	1	localInterruptedException2	InterruptedException
    //   95	4	2	localObject7	Object
    //   100	1	2	localObject8	Object
    //   292	4	2	localObject9	Object
    //   431	4	2	localObject11	Object
    //   104	228	3	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   39	90	95	finally
    //   90	92	95	finally
    //   96	98	95	finally
    //   32	39	100	finally
    //   98	100	100	finally
    //   108	122	100	finally
    //   163	168	100	finally
    //   168	175	100	finally
    //   216	221	100	finally
    //   227	229	100	finally
    //   232	234	100	finally
    //   234	249	100	finally
    //   297	341	100	finally
    //   32	39	104	java/lang/Throwable
    //   98	100	104	java/lang/Throwable
    //   108	122	104	java/lang/Throwable
    //   163	168	104	java/lang/Throwable
    //   168	175	104	java/lang/Throwable
    //   216	221	104	java/lang/Throwable
    //   227	229	104	java/lang/Throwable
    //   232	234	104	java/lang/Throwable
    //   234	249	104	java/lang/Throwable
    //   175	205	224	finally
    //   210	212	224	finally
    //   225	227	224	finally
    //   122	152	229	finally
    //   157	159	229	finally
    //   230	232	229	finally
    //   263	289	292	finally
    //   293	295	292	finally
    //   355	381	384	finally
    //   385	387	384	finally
    //   403	429	431	finally
    //   432	434	431	finally
    //   18	25	437	java/lang/InterruptedException
    //   32	39	444	java/lang/InterruptedException
    //   98	100	444	java/lang/InterruptedException
  }
  
  public void setCallback(MqttCallback paramMqttCallback)
  {
    this.mqttCallback = paramMqttCallback;
  }
  
  public void setClientState(ClientState paramClientState)
  {
    this.clientState = paramClientState;
  }
  
  public void setManualAcks(boolean paramBoolean)
  {
    this.manualAcks = paramBoolean;
  }
  
  public void setMessageListener(String paramString, IMqttMessageListener paramIMqttMessageListener)
  {
    this.callbacks.put(paramString, paramIMqttMessageListener);
  }
  
  public void setReconnectCallback(MqttCallbackExtended paramMqttCallbackExtended)
  {
    this.reconnectInternalCallback = paramMqttCallbackExtended;
  }
  
  public void start(String arg1, ExecutorService paramExecutorService)
  {
    this.threadName = ???;
    synchronized (this.lifecycle)
    {
      if (!this.running)
      {
        this.messageQueue.clear();
        this.completeQueue.clear();
        this.running = true;
        this.quiescing = false;
        this.callbackFuture = paramExecutorService.submit(this);
      }
      return;
    }
  }
  
  /* Error */
  public void stop()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 68	org/eclipse/paho/client/mqttv3/internal/CommsCallback:lifecycle	Ljava/lang/Object;
    //   4: astore_1
    //   5: aload_1
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 418	org/eclipse/paho/client/mqttv3/internal/CommsCallback:callbackFuture	Ljava/util/concurrent/Future;
    //   11: ifnull +14 -> 25
    //   14: aload_0
    //   15: getfield 418	org/eclipse/paho/client/mqttv3/internal/CommsCallback:callbackFuture	Ljava/util/concurrent/Future;
    //   18: iconst_1
    //   19: invokeinterface 425 2 0
    //   24: pop
    //   25: aload_0
    //   26: getfield 64	org/eclipse/paho/client/mqttv3/internal/CommsCallback:running	Z
    //   29: ifeq +124 -> 153
    //   32: getstatic 57	org/eclipse/paho/client/mqttv3/internal/CommsCallback:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   35: getstatic 49	org/eclipse/paho/client/mqttv3/internal/CommsCallback:CLASS_NAME	Ljava/lang/String;
    //   38: ldc_w 426
    //   41: ldc_w 428
    //   44: invokeinterface 327 4 0
    //   49: aload_0
    //   50: iconst_0
    //   51: putfield 64	org/eclipse/paho/client/mqttv3/internal/CommsCallback:running	Z
    //   54: invokestatic 359	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   57: aload_0
    //   58: getfield 314	org/eclipse/paho/client/mqttv3/internal/CommsCallback:callbackThread	Ljava/lang/Thread;
    //   61: invokevirtual 432	java/lang/Object:equals	(Ljava/lang/Object;)Z
    //   64: istore_2
    //   65: iload_2
    //   66: ifne +87 -> 153
    //   69: aload_0
    //   70: getfield 70	org/eclipse/paho/client/mqttv3/internal/CommsCallback:workAvailable	Ljava/lang/Object;
    //   73: astore_3
    //   74: aload_3
    //   75: monitorenter
    //   76: getstatic 57	org/eclipse/paho/client/mqttv3/internal/CommsCallback:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   79: getstatic 49	org/eclipse/paho/client/mqttv3/internal/CommsCallback:CLASS_NAME	Ljava/lang/String;
    //   82: ldc_w 426
    //   85: ldc_w 434
    //   88: invokeinterface 327 4 0
    //   93: aload_0
    //   94: getfield 70	org/eclipse/paho/client/mqttv3/internal/CommsCallback:workAvailable	Ljava/lang/Object;
    //   97: invokevirtual 230	java/lang/Object:notifyAll	()V
    //   100: aload_3
    //   101: monitorexit
    //   102: aload_0
    //   103: getfield 81	org/eclipse/paho/client/mqttv3/internal/CommsCallback:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   106: invokevirtual 367	java/util/concurrent/Semaphore:acquire	()V
    //   109: aload_0
    //   110: getfield 81	org/eclipse/paho/client/mqttv3/internal/CommsCallback:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   113: astore 4
    //   115: aload 4
    //   117: invokevirtual 390	java/util/concurrent/Semaphore:release	()V
    //   120: goto +33 -> 153
    //   123: astore 4
    //   125: aload_3
    //   126: monitorexit
    //   127: aload 4
    //   129: athrow
    //   130: astore 4
    //   132: aload_0
    //   133: getfield 81	org/eclipse/paho/client/mqttv3/internal/CommsCallback:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   136: invokevirtual 390	java/util/concurrent/Semaphore:release	()V
    //   139: aload 4
    //   141: athrow
    //   142: astore 4
    //   144: aload_0
    //   145: getfield 81	org/eclipse/paho/client/mqttv3/internal/CommsCallback:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   148: astore 4
    //   150: goto -35 -> 115
    //   153: aload_0
    //   154: aconst_null
    //   155: putfield 314	org/eclipse/paho/client/mqttv3/internal/CommsCallback:callbackThread	Ljava/lang/Thread;
    //   158: getstatic 57	org/eclipse/paho/client/mqttv3/internal/CommsCallback:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   161: getstatic 49	org/eclipse/paho/client/mqttv3/internal/CommsCallback:CLASS_NAME	Ljava/lang/String;
    //   164: ldc_w 426
    //   167: ldc_w 436
    //   170: invokeinterface 327 4 0
    //   175: aload_1
    //   176: monitorexit
    //   177: return
    //   178: astore 4
    //   180: aload_1
    //   181: monitorexit
    //   182: aload 4
    //   184: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	185	0	this	CommsCallback
    //   4	177	1	localObject1	Object
    //   64	2	2	bool	boolean
    //   113	3	4	localSemaphore1	Semaphore
    //   123	5	4	localObject3	Object
    //   130	10	4	localObject4	Object
    //   142	1	4	localInterruptedException	InterruptedException
    //   148	1	4	localSemaphore2	Semaphore
    //   178	5	4	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   76	102	123	finally
    //   125	127	123	finally
    //   69	76	130	finally
    //   102	109	130	finally
    //   127	130	130	finally
    //   69	76	142	java/lang/InterruptedException
    //   102	109	142	java/lang/InterruptedException
    //   127	130	142	java/lang/InterruptedException
    //   7	25	178	finally
    //   25	65	178	finally
    //   109	115	178	finally
    //   115	120	178	finally
    //   132	142	178	finally
    //   144	150	178	finally
    //   153	177	178	finally
    //   180	182	178	finally
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/CommsCallback.class
 *
 * Reversed by:           J
 */