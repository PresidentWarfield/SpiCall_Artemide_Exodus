package org.eclipse.paho.client.mqttv3.internal;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class CommsReceiver
  implements Runnable
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.CommsReceiver";
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private ClientComms clientComms = null;
  private ClientState clientState = null;
  private MqttInputStream in;
  private Object lifecycle = new Object();
  private Thread recThread = null;
  private Future receiverFuture;
  private volatile boolean receiving;
  private boolean running = false;
  private final Semaphore runningSemaphore = new Semaphore(1);
  private String threadName;
  private CommsTokenStore tokenStore = null;
  
  public CommsReceiver(ClientComms paramClientComms, ClientState paramClientState, CommsTokenStore paramCommsTokenStore, InputStream paramInputStream)
  {
    this.in = new MqttInputStream(paramClientState, paramInputStream);
    this.clientComms = paramClientComms;
    this.clientState = paramClientState;
    this.tokenStore = paramCommsTokenStore;
    log.setResourceName(paramClientComms.getClient().getClientId());
  }
  
  public boolean isReceiving()
  {
    return this.receiving;
  }
  
  public boolean isRunning()
  {
    return this.running;
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 113	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   4: putfield 63	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:recThread	Ljava/lang/Thread;
    //   7: aload_0
    //   8: getfield 63	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:recThread	Ljava/lang/Thread;
    //   11: aload_0
    //   12: getfield 115	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:threadName	Ljava/lang/String;
    //   15: invokevirtual 118	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   18: aload_0
    //   19: getfield 70	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   22: invokevirtual 121	java/util/concurrent/Semaphore:acquire	()V
    //   25: aconst_null
    //   26: astore_1
    //   27: aload_0
    //   28: getfield 53	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:running	Z
    //   31: ifeq +395 -> 426
    //   34: aload_0
    //   35: getfield 77	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:in	Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttInputStream;
    //   38: ifnull +388 -> 426
    //   41: aload_1
    //   42: astore_2
    //   43: aload_1
    //   44: astore_3
    //   45: getstatic 46	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   48: getstatic 38	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:CLASS_NAME	Ljava/lang/String;
    //   51: ldc 122
    //   53: ldc 124
    //   55: invokeinterface 128 4 0
    //   60: aload_1
    //   61: astore_2
    //   62: aload_1
    //   63: astore_3
    //   64: aload_0
    //   65: getfield 77	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:in	Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttInputStream;
    //   68: invokevirtual 132	org/eclipse/paho/client/mqttv3/internal/wire/MqttInputStream:available	()I
    //   71: ifle +9 -> 80
    //   74: iconst_1
    //   75: istore 4
    //   77: goto +6 -> 83
    //   80: iconst_0
    //   81: istore 4
    //   83: aload_1
    //   84: astore_2
    //   85: aload_1
    //   86: astore_3
    //   87: aload_0
    //   88: iload 4
    //   90: putfield 99	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:receiving	Z
    //   93: aload_1
    //   94: astore_2
    //   95: aload_1
    //   96: astore_3
    //   97: aload_0
    //   98: getfield 77	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:in	Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttInputStream;
    //   101: invokevirtual 136	org/eclipse/paho/client/mqttv3/internal/wire/MqttInputStream:readMqttWireMessage	()Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;
    //   104: astore 5
    //   106: aload_1
    //   107: astore_2
    //   108: aload_1
    //   109: astore_3
    //   110: aload_0
    //   111: iconst_0
    //   112: putfield 99	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:receiving	Z
    //   115: aload_1
    //   116: astore_2
    //   117: aload_1
    //   118: astore_3
    //   119: aload 5
    //   121: instanceof 138
    //   124: ifeq +147 -> 271
    //   127: aload_1
    //   128: astore_2
    //   129: aload_1
    //   130: astore_3
    //   131: aload_0
    //   132: getfield 61	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:tokenStore	Lorg/eclipse/paho/client/mqttv3/internal/CommsTokenStore;
    //   135: aload 5
    //   137: invokevirtual 144	org/eclipse/paho/client/mqttv3/internal/CommsTokenStore:getToken	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;)Lorg/eclipse/paho/client/mqttv3/MqttToken;
    //   140: astore_1
    //   141: aload_1
    //   142: ifnull +39 -> 181
    //   145: aload_1
    //   146: astore_2
    //   147: aload_1
    //   148: astore_3
    //   149: aload_1
    //   150: monitorenter
    //   151: aload_0
    //   152: getfield 57	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:clientState	Lorg/eclipse/paho/client/mqttv3/internal/ClientState;
    //   155: aload 5
    //   157: checkcast 138	org/eclipse/paho/client/mqttv3/internal/wire/MqttAck
    //   160: invokevirtual 150	org/eclipse/paho/client/mqttv3/internal/ClientState:notifyReceivedAck	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttAck;)V
    //   163: aload_1
    //   164: monitorexit
    //   165: aload_1
    //   166: astore_3
    //   167: goto +228 -> 395
    //   170: astore 5
    //   172: aload_1
    //   173: monitorexit
    //   174: aload_1
    //   175: astore_2
    //   176: aload_1
    //   177: astore_3
    //   178: aload 5
    //   180: athrow
    //   181: aload_1
    //   182: astore_2
    //   183: aload_1
    //   184: astore_3
    //   185: aload 5
    //   187: instanceof 152
    //   190: ifne +57 -> 247
    //   193: aload_1
    //   194: astore_2
    //   195: aload_1
    //   196: astore_3
    //   197: aload 5
    //   199: instanceof 154
    //   202: ifne +45 -> 247
    //   205: aload_1
    //   206: astore_2
    //   207: aload_1
    //   208: astore_3
    //   209: aload 5
    //   211: instanceof 156
    //   214: ifeq +6 -> 220
    //   217: goto +30 -> 247
    //   220: aload_1
    //   221: astore_2
    //   222: aload_1
    //   223: astore_3
    //   224: new 105	org/eclipse/paho/client/mqttv3/MqttException
    //   227: astore 5
    //   229: aload_1
    //   230: astore_2
    //   231: aload_1
    //   232: astore_3
    //   233: aload 5
    //   235: bipush 6
    //   237: invokespecial 157	org/eclipse/paho/client/mqttv3/MqttException:<init>	(I)V
    //   240: aload_1
    //   241: astore_2
    //   242: aload_1
    //   243: astore_3
    //   244: aload 5
    //   246: athrow
    //   247: aload_1
    //   248: astore_2
    //   249: aload_1
    //   250: astore_3
    //   251: getstatic 46	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   254: getstatic 38	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:CLASS_NAME	Ljava/lang/String;
    //   257: ldc 122
    //   259: ldc -97
    //   261: invokeinterface 128 4 0
    //   266: aload_1
    //   267: astore_3
    //   268: goto +127 -> 395
    //   271: aload_1
    //   272: astore_3
    //   273: aload 5
    //   275: ifnull +120 -> 395
    //   278: aload_1
    //   279: astore_2
    //   280: aload_1
    //   281: astore_3
    //   282: aload_0
    //   283: getfield 57	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:clientState	Lorg/eclipse/paho/client/mqttv3/internal/ClientState;
    //   286: aload 5
    //   288: invokevirtual 163	org/eclipse/paho/client/mqttv3/internal/ClientState:notifyReceivedMsg	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;)V
    //   291: aload_1
    //   292: astore_3
    //   293: goto +102 -> 395
    //   296: astore_1
    //   297: goto +115 -> 412
    //   300: astore_1
    //   301: getstatic 46	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   304: getstatic 38	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:CLASS_NAME	Ljava/lang/String;
    //   307: ldc 122
    //   309: ldc -91
    //   311: invokeinterface 128 4 0
    //   316: aload_0
    //   317: iconst_0
    //   318: putfield 53	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:running	Z
    //   321: aload_2
    //   322: astore_3
    //   323: aload_0
    //   324: getfield 59	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:clientComms	Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;
    //   327: invokevirtual 168	org/eclipse/paho/client/mqttv3/internal/ClientComms:isDisconnecting	()Z
    //   330: ifne +65 -> 395
    //   333: aload_0
    //   334: getfield 59	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:clientComms	Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;
    //   337: astore 5
    //   339: new 105	org/eclipse/paho/client/mqttv3/MqttException
    //   342: astore_3
    //   343: aload_3
    //   344: sipush 32109
    //   347: aload_1
    //   348: invokespecial 171	org/eclipse/paho/client/mqttv3/MqttException:<init>	(ILjava/lang/Throwable;)V
    //   351: aload 5
    //   353: aload_2
    //   354: aload_3
    //   355: invokevirtual 175	org/eclipse/paho/client/mqttv3/internal/ClientComms:shutdownConnection	(Lorg/eclipse/paho/client/mqttv3/MqttToken;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
    //   358: aload_2
    //   359: astore_3
    //   360: goto +35 -> 395
    //   363: astore_1
    //   364: getstatic 46	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   367: getstatic 38	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:CLASS_NAME	Ljava/lang/String;
    //   370: ldc 122
    //   372: ldc -79
    //   374: aconst_null
    //   375: aload_1
    //   376: invokeinterface 180 6 0
    //   381: aload_0
    //   382: iconst_0
    //   383: putfield 53	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:running	Z
    //   386: aload_0
    //   387: getfield 59	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:clientComms	Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;
    //   390: aload_3
    //   391: aload_1
    //   392: invokevirtual 175	org/eclipse/paho/client/mqttv3/internal/ClientComms:shutdownConnection	(Lorg/eclipse/paho/client/mqttv3/MqttToken;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
    //   395: aload_0
    //   396: iconst_0
    //   397: putfield 99	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:receiving	Z
    //   400: aload_0
    //   401: getfield 70	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   404: invokevirtual 183	java/util/concurrent/Semaphore:release	()V
    //   407: aload_3
    //   408: astore_1
    //   409: goto -382 -> 27
    //   412: aload_0
    //   413: iconst_0
    //   414: putfield 99	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:receiving	Z
    //   417: aload_0
    //   418: getfield 70	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   421: invokevirtual 183	java/util/concurrent/Semaphore:release	()V
    //   424: aload_1
    //   425: athrow
    //   426: getstatic 46	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   429: getstatic 38	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:CLASS_NAME	Ljava/lang/String;
    //   432: ldc 122
    //   434: ldc -71
    //   436: invokeinterface 128 4 0
    //   441: return
    //   442: astore_1
    //   443: aload_0
    //   444: iconst_0
    //   445: putfield 53	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:running	Z
    //   448: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	449	0	this	CommsReceiver
    //   26	266	1	localMqttToken1	org.eclipse.paho.client.mqttv3.MqttToken
    //   296	1	1	localObject1	Object
    //   300	48	1	localIOException	java.io.IOException
    //   363	29	1	localMqttException	org.eclipse.paho.client.mqttv3.MqttException
    //   408	17	1	localObject2	Object
    //   442	1	1	localInterruptedException	InterruptedException
    //   42	317	2	localMqttToken2	org.eclipse.paho.client.mqttv3.MqttToken
    //   44	364	3	localObject3	Object
    //   75	14	4	bool	boolean
    //   104	52	5	localMqttWireMessage	org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    //   170	40	5	localObject4	Object
    //   227	125	5	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   151	165	170	finally
    //   172	174	170	finally
    //   45	60	296	finally
    //   64	74	296	finally
    //   87	93	296	finally
    //   97	106	296	finally
    //   110	115	296	finally
    //   119	127	296	finally
    //   131	141	296	finally
    //   149	151	296	finally
    //   178	181	296	finally
    //   185	193	296	finally
    //   197	205	296	finally
    //   209	217	296	finally
    //   224	229	296	finally
    //   233	240	296	finally
    //   244	247	296	finally
    //   251	266	296	finally
    //   282	291	296	finally
    //   301	321	296	finally
    //   323	358	296	finally
    //   364	395	296	finally
    //   45	60	300	java/io/IOException
    //   64	74	300	java/io/IOException
    //   87	93	300	java/io/IOException
    //   97	106	300	java/io/IOException
    //   110	115	300	java/io/IOException
    //   119	127	300	java/io/IOException
    //   131	141	300	java/io/IOException
    //   149	151	300	java/io/IOException
    //   178	181	300	java/io/IOException
    //   185	193	300	java/io/IOException
    //   197	205	300	java/io/IOException
    //   209	217	300	java/io/IOException
    //   224	229	300	java/io/IOException
    //   233	240	300	java/io/IOException
    //   244	247	300	java/io/IOException
    //   251	266	300	java/io/IOException
    //   282	291	300	java/io/IOException
    //   45	60	363	org/eclipse/paho/client/mqttv3/MqttException
    //   64	74	363	org/eclipse/paho/client/mqttv3/MqttException
    //   87	93	363	org/eclipse/paho/client/mqttv3/MqttException
    //   97	106	363	org/eclipse/paho/client/mqttv3/MqttException
    //   110	115	363	org/eclipse/paho/client/mqttv3/MqttException
    //   119	127	363	org/eclipse/paho/client/mqttv3/MqttException
    //   131	141	363	org/eclipse/paho/client/mqttv3/MqttException
    //   149	151	363	org/eclipse/paho/client/mqttv3/MqttException
    //   178	181	363	org/eclipse/paho/client/mqttv3/MqttException
    //   185	193	363	org/eclipse/paho/client/mqttv3/MqttException
    //   197	205	363	org/eclipse/paho/client/mqttv3/MqttException
    //   209	217	363	org/eclipse/paho/client/mqttv3/MqttException
    //   224	229	363	org/eclipse/paho/client/mqttv3/MqttException
    //   233	240	363	org/eclipse/paho/client/mqttv3/MqttException
    //   244	247	363	org/eclipse/paho/client/mqttv3/MqttException
    //   251	266	363	org/eclipse/paho/client/mqttv3/MqttException
    //   282	291	363	org/eclipse/paho/client/mqttv3/MqttException
    //   18	25	442	java/lang/InterruptedException
  }
  
  public void start(String arg1, ExecutorService paramExecutorService)
  {
    this.threadName = ???;
    log.fine(CLASS_NAME, "start", "855");
    synchronized (this.lifecycle)
    {
      if (!this.running)
      {
        this.running = true;
        this.receiverFuture = paramExecutorService.submit(this);
      }
      return;
    }
  }
  
  /* Error */
  public void stop()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 55	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:lifecycle	Ljava/lang/Object;
    //   4: astore_1
    //   5: aload_1
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 198	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:receiverFuture	Ljava/util/concurrent/Future;
    //   11: ifnull +14 -> 25
    //   14: aload_0
    //   15: getfield 198	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:receiverFuture	Ljava/util/concurrent/Future;
    //   18: iconst_1
    //   19: invokeinterface 205 2 0
    //   24: pop
    //   25: getstatic 46	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   28: getstatic 38	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:CLASS_NAME	Ljava/lang/String;
    //   31: ldc -50
    //   33: ldc -48
    //   35: invokeinterface 128 4 0
    //   40: aload_0
    //   41: getfield 53	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:running	Z
    //   44: ifeq +66 -> 110
    //   47: aload_0
    //   48: iconst_0
    //   49: putfield 53	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:running	Z
    //   52: aload_0
    //   53: iconst_0
    //   54: putfield 99	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:receiving	Z
    //   57: invokestatic 113	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   60: aload_0
    //   61: getfield 63	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:recThread	Ljava/lang/Thread;
    //   64: invokevirtual 212	java/lang/Object:equals	(Ljava/lang/Object;)Z
    //   67: istore_2
    //   68: iload_2
    //   69: ifne +41 -> 110
    //   72: aload_0
    //   73: getfield 70	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   76: invokevirtual 121	java/util/concurrent/Semaphore:acquire	()V
    //   79: aload_0
    //   80: getfield 70	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   83: astore_3
    //   84: aload_3
    //   85: invokevirtual 183	java/util/concurrent/Semaphore:release	()V
    //   88: goto +22 -> 110
    //   91: astore_3
    //   92: aload_0
    //   93: getfield 70	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   96: invokevirtual 183	java/util/concurrent/Semaphore:release	()V
    //   99: aload_3
    //   100: athrow
    //   101: astore_3
    //   102: aload_0
    //   103: getfield 70	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:runningSemaphore	Ljava/util/concurrent/Semaphore;
    //   106: astore_3
    //   107: goto -23 -> 84
    //   110: aload_1
    //   111: monitorexit
    //   112: aload_0
    //   113: aconst_null
    //   114: putfield 63	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:recThread	Ljava/lang/Thread;
    //   117: getstatic 46	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   120: getstatic 38	org/eclipse/paho/client/mqttv3/internal/CommsReceiver:CLASS_NAME	Ljava/lang/String;
    //   123: ldc -50
    //   125: ldc -42
    //   127: invokeinterface 128 4 0
    //   132: return
    //   133: astore_3
    //   134: aload_1
    //   135: monitorexit
    //   136: aload_3
    //   137: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	138	0	this	CommsReceiver
    //   4	131	1	localObject1	Object
    //   67	2	2	bool	boolean
    //   83	2	3	localSemaphore1	Semaphore
    //   91	9	3	localObject2	Object
    //   101	1	3	localInterruptedException	InterruptedException
    //   106	1	3	localSemaphore2	Semaphore
    //   133	4	3	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   72	79	91	finally
    //   72	79	101	java/lang/InterruptedException
    //   7	25	133	finally
    //   25	68	133	finally
    //   79	84	133	finally
    //   84	88	133	finally
    //   92	101	133	finally
    //   102	107	133	finally
    //   110	112	133	finally
    //   134	136	133	finally
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/CommsReceiver.class
 *
 * Reversed by:           J
 */