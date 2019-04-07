package org.eclipse.paho.client.mqttv3.internal;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.BufferedMessage;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPingSender;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class ClientComms
{
  public static String BUILD_LEVEL = "L${build.level}";
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.ClientComms";
  private static final byte CLOSED = 4;
  private static final byte CONNECTED = 0;
  private static final byte CONNECTING = 1;
  private static final byte DISCONNECTED = 3;
  private static final byte DISCONNECTING = 2;
  public static String VERSION = "${project.version}";
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private CommsCallback callback;
  private IMqttAsyncClient client;
  private ClientState clientState;
  private boolean closePending = false;
  private Object conLock = new Object();
  private MqttConnectOptions conOptions;
  private byte conState = (byte)3;
  private DisconnectedMessageBuffer disconnectedMessageBuffer;
  private ExecutorService executorService;
  private int networkModuleIndex;
  private NetworkModule[] networkModules;
  private MqttClientPersistence persistence;
  private MqttPingSender pingSender;
  private CommsReceiver receiver;
  private boolean resting = false;
  private CommsSender sender;
  private boolean stoppingComms = false;
  private CommsTokenStore tokenStore;
  
  public ClientComms(IMqttAsyncClient paramIMqttAsyncClient, MqttClientPersistence paramMqttClientPersistence, MqttPingSender paramMqttPingSender, ExecutorService paramExecutorService)
  {
    this.client = paramIMqttAsyncClient;
    this.persistence = paramMqttClientPersistence;
    this.pingSender = paramMqttPingSender;
    this.pingSender.init(this);
    this.executorService = paramExecutorService;
    this.tokenStore = new CommsTokenStore(getClient().getClientId());
    this.callback = new CommsCallback(this);
    this.clientState = new ClientState(paramMqttClientPersistence, this.tokenStore, this.callback, this, paramMqttPingSender);
    this.callback.setClientState(this.clientState);
    log.setResourceName(getClient().getClientId());
  }
  
  private MqttToken handleOldTokens(MqttToken paramMqttToken, MqttException paramMqttException)
  {
    log.fine(CLASS_NAME, "handleOldTokens", "222");
    Enumeration localEnumeration = null;
    Object localObject1 = null;
    if (paramMqttToken != null) {
      localObject2 = localEnumeration;
    }
    try
    {
      if (this.tokenStore.getToken(paramMqttToken.internalTok.getKey()) == null)
      {
        localObject2 = localEnumeration;
        this.tokenStore.saveToken(paramMqttToken, paramMqttToken.internalTok.getKey());
      }
      localObject2 = localEnumeration;
      localEnumeration = this.clientState.resolveOldTokens(paramMqttException).elements();
      paramMqttToken = (MqttToken)localObject1;
      for (;;)
      {
        paramMqttException = paramMqttToken;
        localObject2 = paramMqttToken;
        if (!localEnumeration.hasMoreElements()) {
          break;
        }
        localObject2 = paramMqttToken;
        paramMqttException = (MqttToken)localEnumeration.nextElement();
        localObject2 = paramMqttToken;
        if (!paramMqttException.internalTok.getKey().equals("Disc"))
        {
          localObject2 = paramMqttToken;
          if (!paramMqttException.internalTok.getKey().equals("Con"))
          {
            localObject2 = paramMqttToken;
            this.callback.asyncOperationComplete(paramMqttException);
            continue;
          }
        }
        paramMqttToken = paramMqttException;
      }
    }
    catch (Exception paramMqttToken)
    {
      for (;;)
      {
        paramMqttException = (MqttException)localObject2;
      }
    }
    return paramMqttException;
  }
  
  private void handleRunException(Exception paramException)
  {
    log.fine(CLASS_NAME, "handleRunException", "804", null, paramException);
    if (!(paramException instanceof MqttException)) {
      paramException = new MqttException(32109, paramException);
    } else {
      paramException = (MqttException)paramException;
    }
    shutdownConnection(null, paramException);
  }
  
  private void shutdownExecutorService()
  {
    this.executorService.shutdown();
    try
    {
      if (!this.executorService.awaitTermination(1L, TimeUnit.SECONDS))
      {
        this.executorService.shutdownNow();
        if (!this.executorService.awaitTermination(1L, TimeUnit.SECONDS)) {
          log.fine(CLASS_NAME, "shutdownExecutorService", "executorService did not terminate");
        }
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      this.executorService.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }
  
  public MqttToken checkForActivity()
  {
    return checkForActivity(null);
  }
  
  public MqttToken checkForActivity(IMqttActionListener paramIMqttActionListener)
  {
    try
    {
      paramIMqttActionListener = this.clientState.checkForActivity(paramIMqttActionListener);
    }
    catch (Exception paramIMqttActionListener)
    {
      handleRunException(paramIMqttActionListener);
    }
    catch (MqttException paramIMqttActionListener)
    {
      handleRunException(paramIMqttActionListener);
    }
    paramIMqttActionListener = null;
    return paramIMqttActionListener;
  }
  
  public void close(boolean paramBoolean)
  {
    synchronized (this.conLock)
    {
      if (!isClosed())
      {
        if ((!isDisconnected()) || (paramBoolean))
        {
          log.fine(CLASS_NAME, "close", "224");
          if (isConnecting()) {
            break label143;
          }
          if (isConnected()) {
            break label136;
          }
          if (isDisconnecting())
          {
            this.closePending = true;
            return;
          }
        }
        this.conState = ((byte)4);
        shutdownExecutorService();
        this.clientState.close();
        this.clientState = null;
        this.callback = null;
        this.persistence = null;
        this.sender = null;
        this.pingSender = null;
        this.receiver = null;
        this.networkModules = null;
        this.conOptions = null;
        this.tokenStore = null;
        break label156;
        label136:
        throw ExceptionHelper.createMqttException(32100);
        label143:
        MqttException localMqttException = new org/eclipse/paho/client/mqttv3/MqttException;
        localMqttException.<init>(32110);
        throw localMqttException;
      }
      label156:
      return;
    }
  }
  
  public void connect(MqttConnectOptions paramMqttConnectOptions, MqttToken paramMqttToken)
  {
    synchronized (this.conLock)
    {
      if ((isDisconnected()) && (!this.closePending))
      {
        log.fine(CLASS_NAME, "connect", "214");
        this.conState = ((byte)1);
        this.conOptions = paramMqttConnectOptions;
        localObject2 = new org/eclipse/paho/client/mqttv3/internal/wire/MqttConnect;
        ((MqttConnect)localObject2).<init>(this.client.getClientId(), this.conOptions.getMqttVersion(), this.conOptions.isCleanSession(), this.conOptions.getKeepAliveInterval(), this.conOptions.getUserName(), this.conOptions.getPassword(), this.conOptions.getWillMessage(), this.conOptions.getWillDestination());
        this.clientState.setKeepAliveSecs(this.conOptions.getKeepAliveInterval());
        this.clientState.setCleanSession(this.conOptions.isCleanSession());
        this.clientState.setMaxInflight(this.conOptions.getMaxInflight());
        this.tokenStore.open();
        paramMqttConnectOptions = new org/eclipse/paho/client/mqttv3/internal/ClientComms$ConnectBG;
        paramMqttConnectOptions.<init>(this, this, paramMqttToken, (MqttConnect)localObject2, this.executorService);
        paramMqttConnectOptions.start();
        return;
      }
      paramMqttToken = log;
      Object localObject2 = CLASS_NAME;
      paramMqttConnectOptions = new java/lang/Byte;
      paramMqttConnectOptions.<init>(this.conState);
      paramMqttToken.fine((String)localObject2, "connect", "207", new Object[] { paramMqttConnectOptions });
      if ((!isClosed()) && (!this.closePending))
      {
        if (!isConnecting())
        {
          if (isDisconnecting())
          {
            paramMqttConnectOptions = new org/eclipse/paho/client/mqttv3/MqttException;
            paramMqttConnectOptions.<init>(32102);
            throw paramMqttConnectOptions;
          }
          throw ExceptionHelper.createMqttException(32100);
        }
        paramMqttConnectOptions = new org/eclipse/paho/client/mqttv3/MqttException;
        paramMqttConnectOptions.<init>(32110);
        throw paramMqttConnectOptions;
      }
      paramMqttConnectOptions = new org/eclipse/paho/client/mqttv3/MqttException;
      paramMqttConnectOptions.<init>(32111);
      throw paramMqttConnectOptions;
    }
  }
  
  public void connectComplete(MqttConnack paramMqttConnack, MqttException paramMqttException)
  {
    int i = paramMqttConnack.getReturnCode();
    paramMqttConnack = this.conLock;
    if (i == 0) {}
    try
    {
      log.fine(CLASS_NAME, "connectComplete", "215");
      this.conState = ((byte)0);
      return;
    }
    finally {}
    log.fine(CLASS_NAME, "connectComplete", "204", new Object[] { new Integer(i) });
    throw paramMqttException;
  }
  
  public void deleteBufferedMessage(int paramInt)
  {
    this.disconnectedMessageBuffer.deleteMessage(paramInt);
  }
  
  protected void deliveryComplete(int paramInt)
  {
    this.clientState.deliveryComplete(paramInt);
  }
  
  protected void deliveryComplete(MqttPublish paramMqttPublish)
  {
    this.clientState.deliveryComplete(paramMqttPublish);
  }
  
  public void disconnect(MqttDisconnect paramMqttDisconnect, long paramLong, MqttToken paramMqttToken)
  {
    synchronized (this.conLock)
    {
      if (!isClosed())
      {
        if (!isDisconnected())
        {
          if (!isDisconnecting())
          {
            if (Thread.currentThread() != this.callback.getThread())
            {
              log.fine(CLASS_NAME, "disconnect", "218");
              this.conState = ((byte)2);
              DisconnectBG localDisconnectBG = new org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG;
              localDisconnectBG.<init>(this, paramMqttDisconnect, paramLong, paramMqttToken, this.executorService);
              localDisconnectBG.start();
              return;
            }
            log.fine(CLASS_NAME, "disconnect", "210");
            throw ExceptionHelper.createMqttException(32107);
          }
          log.fine(CLASS_NAME, "disconnect", "219");
          throw ExceptionHelper.createMqttException(32102);
        }
        log.fine(CLASS_NAME, "disconnect", "211");
        throw ExceptionHelper.createMqttException(32101);
      }
      log.fine(CLASS_NAME, "disconnect", "223");
      throw ExceptionHelper.createMqttException(32111);
    }
  }
  
  public void disconnectForcibly(long paramLong1, long paramLong2)
  {
    disconnectForcibly(paramLong1, paramLong2, true);
  }
  
  /* Error */
  public void disconnectForcibly(long paramLong1, long paramLong2, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 142	org/eclipse/paho/client/mqttv3/internal/ClientComms:clientState	Lorg/eclipse/paho/client/mqttv3/internal/ClientState;
    //   4: astore 6
    //   6: aload 6
    //   8: ifnull +9 -> 17
    //   11: aload 6
    //   13: lload_1
    //   14: invokevirtual 465	org/eclipse/paho/client/mqttv3/internal/ClientState:quiesce	(J)V
    //   17: new 195	org/eclipse/paho/client/mqttv3/MqttToken
    //   20: dup
    //   21: aload_0
    //   22: getfield 100	org/eclipse/paho/client/mqttv3/internal/ClientComms:client	Lorg/eclipse/paho/client/mqttv3/IMqttAsyncClient;
    //   25: invokeinterface 124 1 0
    //   30: invokespecial 466	org/eclipse/paho/client/mqttv3/MqttToken:<init>	(Ljava/lang/String;)V
    //   33: astore 6
    //   35: iload 5
    //   37: ifeq +52 -> 89
    //   40: new 468	org/eclipse/paho/client/mqttv3/internal/wire/MqttDisconnect
    //   43: astore 7
    //   45: aload 7
    //   47: invokespecial 469	org/eclipse/paho/client/mqttv3/internal/wire/MqttDisconnect:<init>	()V
    //   50: aload_0
    //   51: aload 7
    //   53: aload 6
    //   55: invokevirtual 473	org/eclipse/paho/client/mqttv3/internal/ClientComms:internalSend	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;Lorg/eclipse/paho/client/mqttv3/MqttToken;)V
    //   58: aload 6
    //   60: lload_3
    //   61: invokevirtual 476	org/eclipse/paho/client/mqttv3/MqttToken:waitForCompletion	(J)V
    //   64: goto +25 -> 89
    //   67: astore 7
    //   69: aload 6
    //   71: getfield 199	org/eclipse/paho/client/mqttv3/MqttToken:internalTok	Lorg/eclipse/paho/client/mqttv3/internal/Token;
    //   74: aconst_null
    //   75: aconst_null
    //   76: invokevirtual 480	org/eclipse/paho/client/mqttv3/internal/Token:markComplete	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
    //   79: aload_0
    //   80: aload 6
    //   82: aconst_null
    //   83: invokevirtual 263	org/eclipse/paho/client/mqttv3/internal/ClientComms:shutdownConnection	(Lorg/eclipse/paho/client/mqttv3/MqttToken;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
    //   86: aload 7
    //   88: athrow
    //   89: aload 6
    //   91: getfield 199	org/eclipse/paho/client/mqttv3/MqttToken:internalTok	Lorg/eclipse/paho/client/mqttv3/internal/Token;
    //   94: aconst_null
    //   95: aconst_null
    //   96: invokevirtual 480	org/eclipse/paho/client/mqttv3/internal/Token:markComplete	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
    //   99: aload_0
    //   100: aload 6
    //   102: aconst_null
    //   103: invokevirtual 263	org/eclipse/paho/client/mqttv3/internal/ClientComms:shutdownConnection	(Lorg/eclipse/paho/client/mqttv3/MqttToken;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
    //   106: return
    //   107: astore 7
    //   109: goto -20 -> 89
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	112	0	this	ClientComms
    //   0	112	1	paramLong1	long
    //   0	112	3	paramLong2	long
    //   0	112	5	paramBoolean	boolean
    //   4	97	6	localObject1	Object
    //   43	9	7	localMqttDisconnect	MqttDisconnect
    //   67	20	7	localObject2	Object
    //   107	1	7	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   40	64	67	finally
    //   40	64	107	java/lang/Exception
  }
  
  public int getActualInFlight()
  {
    return this.clientState.getActualInFlight();
  }
  
  public MqttMessage getBufferedMessage(int paramInt)
  {
    return ((MqttPublish)this.disconnectedMessageBuffer.getMessage(paramInt).getMessage()).getMessage();
  }
  
  public int getBufferedMessageCount()
  {
    return this.disconnectedMessageBuffer.getMessageCount();
  }
  
  public IMqttAsyncClient getClient()
  {
    return this.client;
  }
  
  public ClientState getClientState()
  {
    return this.clientState;
  }
  
  public MqttConnectOptions getConOptions()
  {
    return this.conOptions;
  }
  
  public Properties getDebug()
  {
    Properties localProperties = new Properties();
    localProperties.put("conState", new Integer(this.conState));
    localProperties.put("serverURI", getClient().getServerURI());
    localProperties.put("callback", this.callback);
    localProperties.put("stoppingComms", new Boolean(this.stoppingComms));
    return localProperties;
  }
  
  public long getKeepAlive()
  {
    return this.clientState.getKeepAlive();
  }
  
  public int getNetworkModuleIndex()
  {
    return this.networkModuleIndex;
  }
  
  public NetworkModule[] getNetworkModules()
  {
    return this.networkModules;
  }
  
  public MqttDeliveryToken[] getPendingDeliveryTokens()
  {
    return this.tokenStore.getOutstandingDelTokens();
  }
  
  CommsReceiver getReceiver()
  {
    return this.receiver;
  }
  
  protected MqttTopic getTopic(String paramString)
  {
    return new MqttTopic(paramString, this);
  }
  
  void internalSend(MqttWireMessage paramMqttWireMessage, MqttToken paramMqttToken)
  {
    log.fine(CLASS_NAME, "internalSend", "200", new Object[] { paramMqttWireMessage.getKey(), paramMqttWireMessage, paramMqttToken });
    if (paramMqttToken.getClient() == null)
    {
      paramMqttToken.internalTok.setClient(getClient());
      try
      {
        this.clientState.send(paramMqttWireMessage, paramMqttToken);
        return;
      }
      catch (MqttException paramMqttToken)
      {
        if ((paramMqttWireMessage instanceof MqttPublish)) {
          this.clientState.undo((MqttPublish)paramMqttWireMessage);
        }
        throw paramMqttToken;
      }
    }
    log.fine(CLASS_NAME, "internalSend", "213", new Object[] { paramMqttWireMessage.getKey(), paramMqttWireMessage, paramMqttToken });
    throw new MqttException(32201);
  }
  
  public boolean isClosed()
  {
    synchronized (this.conLock)
    {
      boolean bool;
      if (this.conState == 4) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  public boolean isConnected()
  {
    synchronized (this.conLock)
    {
      boolean bool;
      if (this.conState == 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  public boolean isConnecting()
  {
    synchronized (this.conLock)
    {
      int i = this.conState;
      boolean bool = true;
      if (i != 1) {
        bool = false;
      }
      return bool;
    }
  }
  
  public boolean isDisconnected()
  {
    synchronized (this.conLock)
    {
      boolean bool;
      if (this.conState == 3) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  public boolean isDisconnecting()
  {
    synchronized (this.conLock)
    {
      boolean bool;
      if (this.conState == 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  public boolean isResting()
  {
    synchronized (this.conLock)
    {
      boolean bool = this.resting;
      return bool;
    }
  }
  
  public void messageArrivedComplete(int paramInt1, int paramInt2)
  {
    this.callback.messageArrivedComplete(paramInt1, paramInt2);
  }
  
  public void notifyConnect()
  {
    if (this.disconnectedMessageBuffer != null)
    {
      log.fine(CLASS_NAME, "notifyConnect", "509");
      this.disconnectedMessageBuffer.setPublishCallback(new ReconnectDisconnectedBufferCallback("notifyConnect"));
      this.executorService.execute(this.disconnectedMessageBuffer);
    }
  }
  
  public void removeMessageListener(String paramString)
  {
    this.callback.removeMessageListener(paramString);
  }
  
  public void sendNoWait(MqttWireMessage paramMqttWireMessage, MqttToken paramMqttToken)
  {
    if ((!isConnected()) && ((isConnected()) || (!(paramMqttWireMessage instanceof MqttConnect))) && ((!isDisconnecting()) || (!(paramMqttWireMessage instanceof MqttDisconnect))))
    {
      if (this.disconnectedMessageBuffer != null)
      {
        log.fine(CLASS_NAME, "sendNoWait", "508", new Object[] { paramMqttWireMessage.getKey() });
        if (this.disconnectedMessageBuffer.isPersistBuffer()) {
          this.clientState.persistBufferedMessage(paramMqttWireMessage);
        }
        this.disconnectedMessageBuffer.putMessage(paramMqttWireMessage, paramMqttToken);
      }
      else
      {
        log.fine(CLASS_NAME, "sendNoWait", "208");
        throw ExceptionHelper.createMqttException(32104);
      }
    }
    else
    {
      DisconnectedMessageBuffer localDisconnectedMessageBuffer = this.disconnectedMessageBuffer;
      if ((localDisconnectedMessageBuffer != null) && (localDisconnectedMessageBuffer.getMessageCount() != 0))
      {
        log.fine(CLASS_NAME, "sendNoWait", "507", new Object[] { paramMqttWireMessage.getKey() });
        if (this.disconnectedMessageBuffer.isPersistBuffer()) {
          this.clientState.persistBufferedMessage(paramMqttWireMessage);
        }
        this.disconnectedMessageBuffer.putMessage(paramMqttWireMessage, paramMqttToken);
      }
      else
      {
        internalSend(paramMqttWireMessage, paramMqttToken);
      }
    }
  }
  
  public void setCallback(MqttCallback paramMqttCallback)
  {
    this.callback.setCallback(paramMqttCallback);
  }
  
  public void setDisconnectedMessageBuffer(DisconnectedMessageBuffer paramDisconnectedMessageBuffer)
  {
    this.disconnectedMessageBuffer = paramDisconnectedMessageBuffer;
  }
  
  public void setManualAcks(boolean paramBoolean)
  {
    this.callback.setManualAcks(paramBoolean);
  }
  
  public void setMessageListener(String paramString, IMqttMessageListener paramIMqttMessageListener)
  {
    this.callback.setMessageListener(paramString, paramIMqttMessageListener);
  }
  
  public void setNetworkModuleIndex(int paramInt)
  {
    this.networkModuleIndex = paramInt;
  }
  
  public void setNetworkModules(NetworkModule[] paramArrayOfNetworkModule)
  {
    this.networkModules = paramArrayOfNetworkModule;
  }
  
  public void setReconnectCallback(MqttCallbackExtended paramMqttCallbackExtended)
  {
    this.callback.setReconnectCallback(paramMqttCallbackExtended);
  }
  
  public void setRestingState(boolean paramBoolean)
  {
    this.resting = paramBoolean;
  }
  
  public void shutdownConnection(MqttToken arg1, MqttException paramMqttException)
  {
    synchronized (this.conLock)
    {
      if ((!this.stoppingComms) && (!this.closePending) && (!isClosed()))
      {
        this.stoppingComms = true;
        log.fine(CLASS_NAME, "shutdownConnection", "216");
        bool = isConnected();
        i = 0;
        if ((!bool) && (!isDisconnecting())) {
          j = 0;
        } else {
          j = 1;
        }
        this.conState = ((byte)2);
        if ((??? != null) && (!???.isComplete())) {
          ???.internalTok.setException(paramMqttException);
        }
        ??? = this.callback;
        if (??? != null) {
          ((CommsCallback)???).stop();
        }
        ??? = this.receiver;
        if (??? != null) {
          ((CommsReceiver)???).stop();
        }
      }
      try
      {
        if (this.networkModules != null)
        {
          ??? = this.networkModules[this.networkModuleIndex];
          if (??? != null) {
            ((NetworkModule)???).stop();
          }
        }
      }
      catch (Exception localException3)
      {
        try
        {
          this.clientState.disconnected(paramMqttException);
          if (!this.clientState.getCleanSession()) {
            break label215;
          }
          this.callback.removeMessageListeners();
          ??? = this.sender;
          if (??? == null) {
            break label228;
          }
          ((CommsSender)???).stop();
          ??? = this.pingSender;
          if (??? == null) {
            break label243;
          }
          ((MqttPingSender)???).stop();
        }
        catch (Exception localException3)
        {
          try
          {
            if ((this.disconnectedMessageBuffer != null) || (this.persistence == null)) {
              break label266;
            }
            this.persistence.close();
            synchronized (this.conLock)
            {
              log.fine(CLASS_NAME, "shutdownConnection", "217");
              this.conState = ((byte)3);
              this.stoppingComms = false;
              if (??? == null) {
                break label313;
              }
              int k = 1;
              break label316;
              k = 0;
              if (this.callback == null) {
                break label326;
              }
              i = 1;
              if ((k & i) == 0) {
                break label342;
              }
              this.callback.asyncOperationComplete(???);
              if (j == 0) {
                break label361;
              }
              ??? = this.callback;
              if (??? == null) {
                break label361;
              }
              ???.connectionLost(paramMqttException);
              synchronized (this.conLock)
              {
                bool = this.closePending;
                if (!bool) {
                  break label384;
                }
              }
            }
          }
          catch (Exception localException3)
          {
            try
            {
              for (;;)
              {
                close(true);
                return;
                paramMqttException = finally;
                throw paramMqttException;
                ??? = finally;
                throw ???;
                return;
                ??? = finally;
                throw ???;
                localException1 = localException1;
                continue;
                localException2 = localException2;
              }
              localException3 = localException3;
            }
            catch (Exception paramMqttException)
            {
              for (;;) {}
            }
          }
        }
      }
      this.tokenStore.quiesce(new MqttException(32102));
      ??? = handleOldTokens(???, paramMqttException);
    }
  }
  
  private class ConnectBG
    implements Runnable
  {
    ClientComms clientComms = null;
    MqttConnect conPacket;
    MqttToken conToken;
    private String threadName;
    
    ConnectBG(ClientComms paramClientComms, MqttToken paramMqttToken, MqttConnect paramMqttConnect, ExecutorService paramExecutorService)
    {
      this.clientComms = paramClientComms;
      this.conToken = paramMqttToken;
      this.conPacket = paramMqttConnect;
      paramClientComms = new StringBuilder();
      paramClientComms.append("MQTT Con: ");
      paramClientComms.append(ClientComms.this.getClient().getClientId());
      this.threadName = paramClientComms.toString();
    }
    
    public void run()
    {
      Thread.currentThread().setName(this.threadName);
      ClientComms.log.fine(ClientComms.CLASS_NAME, "connectBG:run", "220");
      Object localObject1 = null;
      try
      {
        Object localObject2 = ClientComms.this.tokenStore.getOutstandingDelTokens();
        for (int i = 0; i < localObject2.length; i++) {
          localObject2[i].internalTok.setException(null);
        }
        ClientComms.this.tokenStore.saveToken(this.conToken, this.conPacket);
        localObject2 = ClientComms.this.networkModules[ClientComms.this.networkModuleIndex];
        ((NetworkModule)localObject2).start();
        Object localObject3 = ClientComms.this;
        Object localObject4 = new org/eclipse/paho/client/mqttv3/internal/CommsReceiver;
        ((CommsReceiver)localObject4).<init>(this.clientComms, ClientComms.this.clientState, ClientComms.this.tokenStore, ((NetworkModule)localObject2).getInputStream());
        ClientComms.access$602((ClientComms)localObject3, (CommsReceiver)localObject4);
        localObject3 = ClientComms.this.receiver;
        localObject4 = new java/lang/StringBuilder;
        ((StringBuilder)localObject4).<init>();
        ((StringBuilder)localObject4).append("MQTT Rec: ");
        ((StringBuilder)localObject4).append(ClientComms.this.getClient().getClientId());
        ((CommsReceiver)localObject3).start(((StringBuilder)localObject4).toString(), ClientComms.this.executorService);
        localObject3 = ClientComms.this;
        localObject4 = new org/eclipse/paho/client/mqttv3/internal/CommsSender;
        ((CommsSender)localObject4).<init>(this.clientComms, ClientComms.this.clientState, ClientComms.this.tokenStore, ((NetworkModule)localObject2).getOutputStream());
        ClientComms.access$802((ClientComms)localObject3, (CommsSender)localObject4);
        localObject3 = ClientComms.this.sender;
        localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        ((StringBuilder)localObject2).append("MQTT Snd: ");
        ((StringBuilder)localObject2).append(ClientComms.this.getClient().getClientId());
        ((CommsSender)localObject3).start(((StringBuilder)localObject2).toString(), ClientComms.this.executorService);
        localObject2 = ClientComms.this.callback;
        localObject3 = new java/lang/StringBuilder;
        ((StringBuilder)localObject3).<init>();
        ((StringBuilder)localObject3).append("MQTT Call: ");
        ((StringBuilder)localObject3).append(ClientComms.this.getClient().getClientId());
        ((CommsCallback)localObject2).start(((StringBuilder)localObject3).toString(), ClientComms.this.executorService);
        ClientComms.this.internalSend(this.conPacket, this.conToken);
      }
      catch (Exception localException)
      {
        ClientComms.log.fine(ClientComms.CLASS_NAME, "connectBG:run", "209", null, localException);
        MqttException localMqttException1 = ExceptionHelper.createMqttException(localException);
      }
      catch (MqttException localMqttException2)
      {
        ClientComms.log.fine(ClientComms.CLASS_NAME, "connectBG:run", "212", null, localMqttException2);
      }
      if (localMqttException2 != null) {
        ClientComms.this.shutdownConnection(this.conToken, localMqttException2);
      }
    }
    
    void start()
    {
      ClientComms.this.executorService.execute(this);
    }
  }
  
  private class DisconnectBG
    implements Runnable
  {
    MqttDisconnect disconnect;
    long quiesceTimeout;
    private String threadName;
    MqttToken token;
    
    DisconnectBG(MqttDisconnect paramMqttDisconnect, long paramLong, MqttToken paramMqttToken, ExecutorService paramExecutorService)
    {
      this.disconnect = paramMqttDisconnect;
      this.quiesceTimeout = paramLong;
      this.token = paramMqttToken;
    }
    
    /* Error */
    public void run()
    {
      // Byte code:
      //   0: invokestatic 42	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   3: aload_0
      //   4: getfield 44	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:threadName	Ljava/lang/String;
      //   7: invokevirtual 48	java/lang/Thread:setName	(Ljava/lang/String;)V
      //   10: invokestatic 52	org/eclipse/paho/client/mqttv3/internal/ClientComms:access$200	()Lorg/eclipse/paho/client/mqttv3/logging/Logger;
      //   13: invokestatic 56	org/eclipse/paho/client/mqttv3/internal/ClientComms:access$100	()Ljava/lang/String;
      //   16: ldc 58
      //   18: ldc 60
      //   20: invokeinterface 66 4 0
      //   25: aload_0
      //   26: getfield 23	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:this$0	Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;
      //   29: invokestatic 70	org/eclipse/paho/client/mqttv3/internal/ClientComms:access$700	(Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;)Lorg/eclipse/paho/client/mqttv3/internal/ClientState;
      //   32: aload_0
      //   33: getfield 30	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:quiesceTimeout	J
      //   36: invokevirtual 76	org/eclipse/paho/client/mqttv3/internal/ClientState:quiesce	(J)V
      //   39: aload_0
      //   40: getfield 23	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:this$0	Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;
      //   43: aload_0
      //   44: getfield 28	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:disconnect	Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttDisconnect;
      //   47: aload_0
      //   48: getfield 32	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:token	Lorg/eclipse/paho/client/mqttv3/MqttToken;
      //   51: invokevirtual 80	org/eclipse/paho/client/mqttv3/internal/ClientComms:internalSend	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;Lorg/eclipse/paho/client/mqttv3/MqttToken;)V
      //   54: aload_0
      //   55: getfield 32	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:token	Lorg/eclipse/paho/client/mqttv3/MqttToken;
      //   58: getfield 86	org/eclipse/paho/client/mqttv3/MqttToken:internalTok	Lorg/eclipse/paho/client/mqttv3/internal/Token;
      //   61: invokevirtual 91	org/eclipse/paho/client/mqttv3/internal/Token:waitUntilSent	()V
      //   64: goto +30 -> 94
      //   67: astore_1
      //   68: aload_0
      //   69: getfield 32	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:token	Lorg/eclipse/paho/client/mqttv3/MqttToken;
      //   72: getfield 86	org/eclipse/paho/client/mqttv3/MqttToken:internalTok	Lorg/eclipse/paho/client/mqttv3/internal/Token;
      //   75: aconst_null
      //   76: aconst_null
      //   77: invokevirtual 95	org/eclipse/paho/client/mqttv3/internal/Token:markComplete	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
      //   80: aload_0
      //   81: getfield 23	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:this$0	Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;
      //   84: aload_0
      //   85: getfield 32	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:token	Lorg/eclipse/paho/client/mqttv3/MqttToken;
      //   88: aconst_null
      //   89: invokevirtual 99	org/eclipse/paho/client/mqttv3/internal/ClientComms:shutdownConnection	(Lorg/eclipse/paho/client/mqttv3/MqttToken;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
      //   92: aload_1
      //   93: athrow
      //   94: aload_0
      //   95: getfield 32	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:token	Lorg/eclipse/paho/client/mqttv3/MqttToken;
      //   98: getfield 86	org/eclipse/paho/client/mqttv3/MqttToken:internalTok	Lorg/eclipse/paho/client/mqttv3/internal/Token;
      //   101: aconst_null
      //   102: aconst_null
      //   103: invokevirtual 95	org/eclipse/paho/client/mqttv3/internal/Token:markComplete	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
      //   106: aload_0
      //   107: getfield 23	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:this$0	Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;
      //   110: aload_0
      //   111: getfield 32	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:token	Lorg/eclipse/paho/client/mqttv3/MqttToken;
      //   114: aconst_null
      //   115: invokevirtual 99	org/eclipse/paho/client/mqttv3/internal/ClientComms:shutdownConnection	(Lorg/eclipse/paho/client/mqttv3/MqttToken;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
      //   118: return
      //   119: astore_1
      //   120: goto -26 -> 94
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	123	0	this	DisconnectBG
      //   67	26	1	localObject	Object
      //   119	1	1	localMqttException	MqttException
      // Exception table:
      //   from	to	target	type
      //   39	64	67	finally
      //   39	64	119	org/eclipse/paho/client/mqttv3/MqttException
    }
    
    void start()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("MQTT Disc: ");
      localStringBuilder.append(ClientComms.this.getClient().getClientId());
      this.threadName = localStringBuilder.toString();
      ClientComms.this.executorService.execute(this);
    }
  }
  
  class ReconnectDisconnectedBufferCallback
    implements IDisconnectedBufferCallback
  {
    final String methodName;
    
    ReconnectDisconnectedBufferCallback(String paramString)
    {
      this.methodName = paramString;
    }
    
    public void publishBufferedMessage(BufferedMessage paramBufferedMessage)
    {
      if (ClientComms.this.isConnected())
      {
        while (ClientComms.this.clientState.getActualInFlight() >= ClientComms.this.clientState.getMaxInFlight() - 1) {
          Thread.yield();
        }
        ClientComms.log.fine(ClientComms.CLASS_NAME, this.methodName, "510", new Object[] { paramBufferedMessage.getMessage().getKey() });
        ClientComms.this.internalSend(paramBufferedMessage.getMessage(), paramBufferedMessage.getToken());
        ClientComms.this.clientState.unPersistBufferedMessage(paramBufferedMessage.getMessage());
        return;
      }
      ClientComms.log.fine(ClientComms.CLASS_NAME, this.methodName, "208");
      throw ExceptionHelper.createMqttException(32104);
    }
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/ClientComms.class
 *
 * Reversed by:           J
 */