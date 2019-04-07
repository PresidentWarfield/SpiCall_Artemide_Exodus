package org.eclipse.paho.client.mqttv3;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.internal.ConnectActionListener;
import org.eclipse.paho.client.mqttv3.internal.DisconnectedMessageBuffer;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;
import org.eclipse.paho.client.mqttv3.internal.NetworkModule;
import org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.Token;
import org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory;
import org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketSecureNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSubscribe;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttUnsubscribe;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.eclipse.paho.client.mqttv3.util.Debug;

public class MqttAsyncClient
  implements IMqttAsyncClient
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.MqttAsyncClient";
  private static final String CLIENT_ID_PREFIX = "paho";
  private static final long DISCONNECT_TIMEOUT = 10000L;
  private static final char MAX_HIGH_SURROGATE = '?';
  private static final char MIN_HIGH_SURROGATE = '?';
  private static final long QUIESCE_TIMEOUT = 30000L;
  private static Object clientLock = new Object();
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private static int reconnectDelay = 1000;
  private String clientId;
  protected ClientComms comms;
  private MqttConnectOptions connOpts;
  private ScheduledExecutorService executorService;
  private MqttCallback mqttCallback;
  private MqttClientPersistence persistence;
  private Timer reconnectTimer;
  private boolean reconnecting = false;
  private String serverURI;
  private Hashtable topics;
  private Object userContext;
  
  public MqttAsyncClient(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, new MqttDefaultFilePersistence());
  }
  
  public MqttAsyncClient(String paramString1, String paramString2, MqttClientPersistence paramMqttClientPersistence)
  {
    this(paramString1, paramString2, paramMqttClientPersistence, new TimerPingSender());
  }
  
  public MqttAsyncClient(String paramString1, String paramString2, MqttClientPersistence paramMqttClientPersistence, MqttPingSender paramMqttPingSender)
  {
    this(paramString1, paramString2, paramMqttClientPersistence, paramMqttPingSender, null);
  }
  
  public MqttAsyncClient(String paramString1, String paramString2, MqttClientPersistence paramMqttClientPersistence, MqttPingSender paramMqttPingSender, ScheduledExecutorService paramScheduledExecutorService)
  {
    log.setResourceName(paramString2);
    if (paramString2 != null)
    {
      int i = 0;
      int j = 0;
      while (i < paramString2.length() - 1)
      {
        int k = i;
        if (Character_isHighSurrogate(paramString2.charAt(i))) {
          k = i + 1;
        }
        j++;
        i = k + 1;
      }
      if (j <= 65535)
      {
        MqttConnectOptions.validateURI(paramString1);
        this.serverURI = paramString1;
        this.clientId = paramString2;
        this.persistence = paramMqttClientPersistence;
        if (this.persistence == null) {
          this.persistence = new MemoryPersistence();
        }
        this.executorService = paramScheduledExecutorService;
        if (this.executorService == null) {
          this.executorService = Executors.newScheduledThreadPool(10);
        }
        log.fine(CLASS_NAME, "MqttAsyncClient", "101", new Object[] { paramString2, paramString1, paramMqttClientPersistence });
        this.persistence.open(paramString2, paramString1);
        this.comms = new ClientComms(this, this.persistence, paramMqttPingSender, this.executorService);
        this.persistence.close();
        this.topics = new Hashtable();
        return;
      }
      throw new IllegalArgumentException("ClientId longer than 65535 characters");
    }
    throw new IllegalArgumentException("Null clientId");
  }
  
  protected static boolean Character_isHighSurrogate(char paramChar)
  {
    boolean bool;
    if ((paramChar >= 55296) && (paramChar <= 56319)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void attemptReconnect()
  {
    log.fine(CLASS_NAME, "attemptReconnect", "500", new Object[] { this.clientId });
    try
    {
      MqttConnectOptions localMqttConnectOptions = this.connOpts;
      Object localObject = this.userContext;
      MqttReconnectActionListener localMqttReconnectActionListener = new org/eclipse/paho/client/mqttv3/MqttAsyncClient$MqttReconnectActionListener;
      localMqttReconnectActionListener.<init>(this, "attemptReconnect");
      connect(localMqttConnectOptions, localObject, localMqttReconnectActionListener);
    }
    catch (MqttException localMqttException)
    {
      log.fine(CLASS_NAME, "attemptReconnect", "804", null, localMqttException);
    }
    catch (MqttSecurityException localMqttSecurityException)
    {
      log.fine(CLASS_NAME, "attemptReconnect", "804", null, localMqttSecurityException);
    }
  }
  
  private NetworkModule createNetworkModule(String paramString, MqttConnectOptions paramMqttConnectOptions)
  {
    log.fine(CLASS_NAME, "createNetworkModule", "115", new Object[] { paramString });
    Object localObject1 = paramMqttConnectOptions.getSocketFactory();
    int i = MqttConnectOptions.validateURI(paramString);
    try
    {
      Object localObject2 = new java/net/URI;
      ((URI)localObject2).<init>(paramString);
      Object localObject3;
      if (((URI)localObject2).getHost() == null)
      {
        boolean bool = paramString.contains("_");
        if (bool)
        {
          try
          {
            localObject3 = URI.class.getDeclaredField("host");
            ((Field)localObject3).setAccessible(true);
            ((Field)localObject3).set(localObject2, getHostName(paramString.substring(((URI)localObject2).getScheme().length() + 3)));
          }
          catch (IllegalAccessException paramMqttConnectOptions) {}catch (IllegalArgumentException paramMqttConnectOptions) {}catch (SecurityException paramMqttConnectOptions) {}catch (NoSuchFieldException paramMqttConnectOptions) {}
          throw ExceptionHelper.createMqttException(paramMqttConnectOptions.getCause());
        }
      }
      String str = ((URI)localObject2).getHost();
      int j = ((URI)localObject2).getPort();
      localObject2 = null;
      switch (i)
      {
      case 2: 
      default: 
        log.fine(CLASS_NAME, "createNetworkModule", "119", new Object[] { paramString });
        paramString = (String)localObject2;
        break;
      case 4: 
        if (j == -1) {
          j = 443;
        }
        if (localObject1 == null)
        {
          localObject1 = new SSLSocketFactoryFactory();
          localObject2 = paramMqttConnectOptions.getSSLProperties();
          if (localObject2 != null) {
            ((SSLSocketFactoryFactory)localObject1).initialize((Properties)localObject2, null);
          }
          localObject3 = ((SSLSocketFactoryFactory)localObject1).createSocketFactory(null);
          localObject2 = localObject1;
        }
        else
        {
          if (!(localObject1 instanceof SSLSocketFactory)) {
            break label344;
          }
          localObject2 = null;
          localObject3 = localObject1;
        }
        paramString = new WebSocketSecureNetworkModule((SSLSocketFactory)localObject3, paramString, str, j, this.clientId);
        ((WebSocketSecureNetworkModule)paramString).setSSLhandshakeTimeout(paramMqttConnectOptions.getConnectionTimeout());
        if (localObject2 != null)
        {
          paramMqttConnectOptions = ((SSLSocketFactoryFactory)localObject2).getEnabledCipherSuites(null);
          if (paramMqttConnectOptions != null) {
            ((SSLNetworkModule)paramString).setEnabledCiphers(paramMqttConnectOptions);
          }
        }
        break;
        throw ExceptionHelper.createMqttException(32105);
      case 3: 
        if (j == -1) {
          j = 80;
        }
        if (localObject1 == null) {
          localObject1 = SocketFactory.getDefault();
        } else {
          if ((localObject1 instanceof SSLSocketFactory)) {
            break label414;
          }
        }
        paramString = new WebSocketNetworkModule((SocketFactory)localObject1, paramString, str, j, this.clientId);
        ((WebSocketNetworkModule)paramString).setConnectTimeout(paramMqttConnectOptions.getConnectionTimeout());
        break;
        throw ExceptionHelper.createMqttException(32105);
      case 1: 
        i = j;
        if (j == -1) {
          i = 8883;
        }
        if (localObject1 == null)
        {
          paramString = new SSLSocketFactoryFactory();
          localObject1 = paramMqttConnectOptions.getSSLProperties();
          if (localObject1 != null) {
            paramString.initialize((Properties)localObject1, null);
          }
          localObject1 = paramString.createSocketFactory(null);
        }
        else
        {
          if (!(localObject1 instanceof SSLSocketFactory)) {
            break label550;
          }
          paramString = null;
        }
        localObject1 = new SSLNetworkModule((SSLSocketFactory)localObject1, str, i, this.clientId);
        localObject2 = (SSLNetworkModule)localObject1;
        ((SSLNetworkModule)localObject2).setSSLhandshakeTimeout(paramMqttConnectOptions.getConnectionTimeout());
        ((SSLNetworkModule)localObject2).setSSLHostnameVerifier(paramMqttConnectOptions.getSSLHostnameVerifier());
        if (paramString != null)
        {
          paramString = paramString.getEnabledCipherSuites(null);
          if (paramString != null) {
            ((SSLNetworkModule)localObject2).setEnabledCiphers(paramString);
          }
        }
        paramString = (String)localObject1;
        break;
        throw ExceptionHelper.createMqttException(32105);
      case 0: 
        label344:
        label414:
        label550:
        i = j;
        if (j == -1) {
          i = 1883;
        }
        if (localObject1 == null) {
          localObject1 = SocketFactory.getDefault();
        } else {
          if ((localObject1 instanceof SSLSocketFactory)) {
            break label621;
          }
        }
        paramString = new TCPNetworkModule((SocketFactory)localObject1, str, i, this.clientId);
        ((TCPNetworkModule)paramString).setConnectTimeout(paramMqttConnectOptions.getConnectionTimeout());
        break;
        label621:
        throw ExceptionHelper.createMqttException(32105);
      }
      return paramString;
    }
    catch (URISyntaxException paramMqttConnectOptions)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Malformed URI: ");
      ((StringBuilder)localObject1).append(paramString);
      ((StringBuilder)localObject1).append(", ");
      ((StringBuilder)localObject1).append(paramMqttConnectOptions.getMessage());
      throw new IllegalArgumentException(((StringBuilder)localObject1).toString());
    }
  }
  
  public static String generateClientId()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("paho");
    localStringBuilder.append(System.nanoTime());
    return localStringBuilder.toString();
  }
  
  private String getHostName(String paramString)
  {
    int i = paramString.indexOf(':');
    int j = i;
    if (i == -1) {
      j = paramString.indexOf('/');
    }
    i = j;
    if (j == -1) {
      i = paramString.length();
    }
    return paramString.substring(0, i);
  }
  
  private void startReconnectCycle()
  {
    log.fine(CLASS_NAME, "startReconnectCycle", "503", new Object[] { this.clientId, new Long(reconnectDelay) });
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("MQTT Reconnect: ");
    localStringBuilder.append(this.clientId);
    this.reconnectTimer = new Timer(localStringBuilder.toString());
    this.reconnectTimer.schedule(new ReconnectTask(null), reconnectDelay);
  }
  
  private void stopReconnectCycle()
  {
    log.fine(CLASS_NAME, "stopReconnectCycle", "504", new Object[] { this.clientId });
    synchronized (clientLock)
    {
      if (this.connOpts.isAutomaticReconnect())
      {
        if (this.reconnectTimer != null)
        {
          this.reconnectTimer.cancel();
          this.reconnectTimer = null;
        }
        reconnectDelay = 1000;
      }
      return;
    }
  }
  
  public IMqttToken checkPing(Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    log.fine(CLASS_NAME, "ping", "117");
    paramObject = this.comms.checkForActivity();
    log.fine(CLASS_NAME, "ping", "118");
    return (IMqttToken)paramObject;
  }
  
  public void close()
  {
    close(false);
  }
  
  public void close(boolean paramBoolean)
  {
    log.fine(CLASS_NAME, "close", "113");
    this.comms.close(paramBoolean);
    log.fine(CLASS_NAME, "close", "114");
  }
  
  public IMqttToken connect()
  {
    return connect(null, null);
  }
  
  public IMqttToken connect(Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    return connect(new MqttConnectOptions(), paramObject, paramIMqttActionListener);
  }
  
  public IMqttToken connect(MqttConnectOptions paramMqttConnectOptions)
  {
    return connect(paramMqttConnectOptions, null, null);
  }
  
  public IMqttToken connect(MqttConnectOptions paramMqttConnectOptions, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    if (!this.comms.isConnected())
    {
      if (!this.comms.isConnecting())
      {
        if (!this.comms.isDisconnecting())
        {
          if (!this.comms.isClosed())
          {
            if (paramMqttConnectOptions == null) {
              paramMqttConnectOptions = new MqttConnectOptions();
            }
            this.connOpts = paramMqttConnectOptions;
            this.userContext = paramObject;
            boolean bool1 = paramMqttConnectOptions.isAutomaticReconnect();
            Logger localLogger = log;
            String str1 = CLASS_NAME;
            boolean bool2 = paramMqttConnectOptions.isCleanSession();
            Integer localInteger1 = new Integer(paramMqttConnectOptions.getConnectionTimeout());
            Integer localInteger2 = new Integer(paramMqttConnectOptions.getKeepAliveInterval());
            String str2 = paramMqttConnectOptions.getUserName();
            if (paramMqttConnectOptions.getPassword() == null) {
              localObject = "[null]";
            } else {
              localObject = "[notnull]";
            }
            String str3;
            if (paramMqttConnectOptions.getWillMessage() == null) {
              str3 = "[null]";
            } else {
              str3 = "[notnull]";
            }
            localLogger.fine(str1, "connect", "103", new Object[] { Boolean.valueOf(bool2), localInteger1, localInteger2, str2, localObject, str3, paramObject, paramIMqttActionListener });
            this.comms.setNetworkModules(createNetworkModules(this.serverURI, paramMqttConnectOptions));
            this.comms.setReconnectCallback(new MqttReconnectCallback(bool1));
            Object localObject = new MqttToken(getClientId());
            paramMqttConnectOptions = new ConnectActionListener(this, this.persistence, this.comms, paramMqttConnectOptions, (MqttToken)localObject, paramObject, paramIMqttActionListener, this.reconnecting);
            ((MqttToken)localObject).setActionCallback(paramMqttConnectOptions);
            ((MqttToken)localObject).setUserContext(this);
            paramObject = this.mqttCallback;
            if ((paramObject instanceof MqttCallbackExtended)) {
              paramMqttConnectOptions.setMqttCallbackExtended((MqttCallbackExtended)paramObject);
            }
            this.comms.setNetworkModuleIndex(0);
            paramMqttConnectOptions.connect();
            return (IMqttToken)localObject;
          }
          throw new MqttException(32111);
        }
        throw new MqttException(32102);
      }
      throw new MqttException(32110);
    }
    throw ExceptionHelper.createMqttException(32100);
  }
  
  protected NetworkModule[] createNetworkModules(String paramString, MqttConnectOptions paramMqttConnectOptions)
  {
    Object localObject1 = log;
    Object localObject2 = CLASS_NAME;
    int i = 0;
    ((Logger)localObject1).fine((String)localObject2, "createNetworkModules", "116", new Object[] { paramString });
    localObject2 = paramMqttConnectOptions.getServerURIs();
    if (localObject2 == null)
    {
      localObject1 = new String[1];
      localObject1[0] = paramString;
    }
    else
    {
      localObject1 = localObject2;
      if (localObject2.length == 0)
      {
        localObject1 = new String[1];
        localObject1[0] = paramString;
      }
    }
    paramString = new NetworkModule[localObject1.length];
    while (i < localObject1.length)
    {
      paramString[i] = createNetworkModule(localObject1[i], paramMqttConnectOptions);
      i++;
    }
    log.fine(CLASS_NAME, "createNetworkModules", "108");
    return paramString;
  }
  
  public void deleteBufferedMessage(int paramInt)
  {
    this.comms.deleteBufferedMessage(paramInt);
  }
  
  public IMqttToken disconnect()
  {
    return disconnect(null, null);
  }
  
  public IMqttToken disconnect(long paramLong)
  {
    return disconnect(paramLong, null, null);
  }
  
  public IMqttToken disconnect(long paramLong, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    log.fine(CLASS_NAME, "disconnect", "104", new Object[] { new Long(paramLong), paramObject, paramIMqttActionListener });
    MqttToken localMqttToken = new MqttToken(getClientId());
    localMqttToken.setActionCallback(paramIMqttActionListener);
    localMqttToken.setUserContext(paramObject);
    paramObject = new MqttDisconnect();
    try
    {
      this.comms.disconnect((MqttDisconnect)paramObject, paramLong, localMqttToken);
      log.fine(CLASS_NAME, "disconnect", "108");
      return localMqttToken;
    }
    catch (MqttException paramObject)
    {
      log.fine(CLASS_NAME, "disconnect", "105", null, (Throwable)paramObject);
      throw ((Throwable)paramObject);
    }
  }
  
  public IMqttToken disconnect(Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    return disconnect(30000L, paramObject, paramIMqttActionListener);
  }
  
  public void disconnectForcibly()
  {
    disconnectForcibly(30000L, 10000L);
  }
  
  public void disconnectForcibly(long paramLong)
  {
    disconnectForcibly(30000L, paramLong);
  }
  
  public void disconnectForcibly(long paramLong1, long paramLong2)
  {
    this.comms.disconnectForcibly(paramLong1, paramLong2);
  }
  
  public void disconnectForcibly(long paramLong1, long paramLong2, boolean paramBoolean)
  {
    this.comms.disconnectForcibly(paramLong1, paramLong2, paramBoolean);
  }
  
  public MqttMessage getBufferedMessage(int paramInt)
  {
    return this.comms.getBufferedMessage(paramInt);
  }
  
  public int getBufferedMessageCount()
  {
    return this.comms.getBufferedMessageCount();
  }
  
  public String getClientId()
  {
    return this.clientId;
  }
  
  public String getCurrentServerURI()
  {
    return this.comms.getNetworkModules()[this.comms.getNetworkModuleIndex()].getServerURI();
  }
  
  public Debug getDebug()
  {
    return new Debug(this.clientId, this.comms);
  }
  
  public int getInFlightMessageCount()
  {
    return this.comms.getActualInFlight();
  }
  
  public IMqttDeliveryToken[] getPendingDeliveryTokens()
  {
    return this.comms.getPendingDeliveryTokens();
  }
  
  public String getServerURI()
  {
    return this.serverURI;
  }
  
  protected MqttTopic getTopic(String paramString)
  {
    MqttTopic.validate(paramString, false);
    MqttTopic localMqttTopic1 = (MqttTopic)this.topics.get(paramString);
    MqttTopic localMqttTopic2 = localMqttTopic1;
    if (localMqttTopic1 == null)
    {
      localMqttTopic2 = new MqttTopic(paramString, this.comms);
      this.topics.put(paramString, localMqttTopic2);
    }
    return localMqttTopic2;
  }
  
  public boolean isConnected()
  {
    return this.comms.isConnected();
  }
  
  public void messageArrivedComplete(int paramInt1, int paramInt2)
  {
    this.comms.messageArrivedComplete(paramInt1, paramInt2);
  }
  
  public IMqttDeliveryToken publish(String paramString, MqttMessage paramMqttMessage)
  {
    return publish(paramString, paramMqttMessage, null, null);
  }
  
  public IMqttDeliveryToken publish(String paramString, MqttMessage paramMqttMessage, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    log.fine(CLASS_NAME, "publish", "111", new Object[] { paramString, paramObject, paramIMqttActionListener });
    MqttTopic.validate(paramString, false);
    MqttDeliveryToken localMqttDeliveryToken = new MqttDeliveryToken(getClientId());
    localMqttDeliveryToken.setActionCallback(paramIMqttActionListener);
    localMqttDeliveryToken.setUserContext(paramObject);
    localMqttDeliveryToken.setMessage(paramMqttMessage);
    localMqttDeliveryToken.internalTok.setTopics(new String[] { paramString });
    paramString = new MqttPublish(paramString, paramMqttMessage);
    this.comms.sendNoWait(paramString, localMqttDeliveryToken);
    log.fine(CLASS_NAME, "publish", "112");
    return localMqttDeliveryToken;
  }
  
  public IMqttDeliveryToken publish(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
  {
    return publish(paramString, paramArrayOfByte, paramInt, paramBoolean, null, null);
  }
  
  public IMqttDeliveryToken publish(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    paramArrayOfByte = new MqttMessage(paramArrayOfByte);
    paramArrayOfByte.setQos(paramInt);
    paramArrayOfByte.setRetained(paramBoolean);
    return publish(paramString, paramArrayOfByte, paramObject, paramIMqttActionListener);
  }
  
  public void reconnect()
  {
    log.fine(CLASS_NAME, "reconnect", "500", new Object[] { this.clientId });
    if (!this.comms.isConnected())
    {
      if (!this.comms.isConnecting())
      {
        if (!this.comms.isDisconnecting())
        {
          if (!this.comms.isClosed())
          {
            stopReconnectCycle();
            attemptReconnect();
            return;
          }
          throw new MqttException(32111);
        }
        throw new MqttException(32102);
      }
      throw new MqttException(32110);
    }
    throw ExceptionHelper.createMqttException(32100);
  }
  
  public void setBufferOpts(DisconnectedBufferOptions paramDisconnectedBufferOptions)
  {
    this.comms.setDisconnectedMessageBuffer(new DisconnectedMessageBuffer(paramDisconnectedBufferOptions));
  }
  
  public void setCallback(MqttCallback paramMqttCallback)
  {
    this.mqttCallback = paramMqttCallback;
    this.comms.setCallback(paramMqttCallback);
  }
  
  public void setManualAcks(boolean paramBoolean)
  {
    this.comms.setManualAcks(paramBoolean);
  }
  
  public IMqttToken subscribe(String paramString, int paramInt)
  {
    return subscribe(new String[] { paramString }, new int[] { paramInt }, null, null);
  }
  
  public IMqttToken subscribe(String paramString, int paramInt, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    return subscribe(new String[] { paramString }, new int[] { paramInt }, paramObject, paramIMqttActionListener);
  }
  
  public IMqttToken subscribe(String paramString, int paramInt, Object paramObject, IMqttActionListener paramIMqttActionListener, IMqttMessageListener paramIMqttMessageListener)
  {
    return subscribe(new String[] { paramString }, new int[] { paramInt }, paramObject, paramIMqttActionListener, new IMqttMessageListener[] { paramIMqttMessageListener });
  }
  
  public IMqttToken subscribe(String paramString, int paramInt, IMqttMessageListener paramIMqttMessageListener)
  {
    return subscribe(new String[] { paramString }, new int[] { paramInt }, null, null, new IMqttMessageListener[] { paramIMqttMessageListener });
  }
  
  public IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    return subscribe(paramArrayOfString, paramArrayOfInt, null, null);
  }
  
  public IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    if (paramArrayOfString.length == paramArrayOfInt.length)
    {
      for (int i = 0; i < paramArrayOfString.length; i++) {
        this.comms.removeMessageListener(paramArrayOfString[i]);
      }
      if (log.isLoggable(5))
      {
        localObject = new StringBuffer();
        for (i = 0; i < paramArrayOfString.length; i++)
        {
          if (i > 0) {
            ((StringBuffer)localObject).append(", ");
          }
          ((StringBuffer)localObject).append("topic=");
          ((StringBuffer)localObject).append(paramArrayOfString[i]);
          ((StringBuffer)localObject).append(" qos=");
          ((StringBuffer)localObject).append(paramArrayOfInt[i]);
          MqttTopic.validate(paramArrayOfString[i], true);
        }
        log.fine(CLASS_NAME, "subscribe", "106", new Object[] { ((StringBuffer)localObject).toString(), paramObject, paramIMqttActionListener });
      }
      Object localObject = new MqttToken(getClientId());
      ((MqttToken)localObject).setActionCallback(paramIMqttActionListener);
      ((MqttToken)localObject).setUserContext(paramObject);
      ((MqttToken)localObject).internalTok.setTopics(paramArrayOfString);
      paramArrayOfString = new MqttSubscribe(paramArrayOfString, paramArrayOfInt);
      this.comms.sendNoWait(paramArrayOfString, (MqttToken)localObject);
      log.fine(CLASS_NAME, "subscribe", "109");
      return (IMqttToken)localObject;
    }
    throw new IllegalArgumentException();
  }
  
  public IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt, Object paramObject, IMqttActionListener paramIMqttActionListener, IMqttMessageListener[] paramArrayOfIMqttMessageListener)
  {
    if ((paramArrayOfIMqttMessageListener.length == paramArrayOfInt.length) && (paramArrayOfInt.length == paramArrayOfString.length))
    {
      paramArrayOfInt = subscribe(paramArrayOfString, paramArrayOfInt, paramObject, paramIMqttActionListener);
      for (int i = 0; i < paramArrayOfString.length; i++) {
        this.comms.setMessageListener(paramArrayOfString[i], paramArrayOfIMqttMessageListener[i]);
      }
      return paramArrayOfInt;
    }
    throw new IllegalArgumentException();
  }
  
  public IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt, IMqttMessageListener[] paramArrayOfIMqttMessageListener)
  {
    return subscribe(paramArrayOfString, paramArrayOfInt, null, null, paramArrayOfIMqttMessageListener);
  }
  
  public IMqttToken unsubscribe(String paramString)
  {
    return unsubscribe(new String[] { paramString }, null, null);
  }
  
  public IMqttToken unsubscribe(String paramString, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    return unsubscribe(new String[] { paramString }, paramObject, paramIMqttActionListener);
  }
  
  public IMqttToken unsubscribe(String[] paramArrayOfString)
  {
    return unsubscribe(paramArrayOfString, null, null);
  }
  
  public IMqttToken unsubscribe(String[] paramArrayOfString, Object paramObject, IMqttActionListener paramIMqttActionListener)
  {
    boolean bool = log.isLoggable(5);
    int i = 0;
    if (bool)
    {
      localObject1 = "";
      for (j = 0; j < paramArrayOfString.length; j++)
      {
        Object localObject2 = localObject1;
        if (j > 0)
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append((String)localObject1);
          ((StringBuilder)localObject2).append(", ");
          localObject2 = ((StringBuilder)localObject2).toString();
        }
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append(paramArrayOfString[j]);
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      log.fine(CLASS_NAME, "unsubscribe", "107", new Object[] { localObject1, paramObject, paramIMqttActionListener });
    }
    int k;
    for (int j = 0;; j++)
    {
      k = i;
      if (j >= paramArrayOfString.length) {
        break;
      }
      MqttTopic.validate(paramArrayOfString[j], true);
    }
    while (k < paramArrayOfString.length)
    {
      this.comms.removeMessageListener(paramArrayOfString[k]);
      k++;
    }
    Object localObject1 = new MqttToken(getClientId());
    ((MqttToken)localObject1).setActionCallback(paramIMqttActionListener);
    ((MqttToken)localObject1).setUserContext(paramObject);
    ((MqttToken)localObject1).internalTok.setTopics(paramArrayOfString);
    paramArrayOfString = new MqttUnsubscribe(paramArrayOfString);
    this.comms.sendNoWait(paramArrayOfString, (MqttToken)localObject1);
    log.fine(CLASS_NAME, "unsubscribe", "110");
    return (IMqttToken)localObject1;
  }
  
  class MqttReconnectActionListener
    implements IMqttActionListener
  {
    final String methodName;
    
    MqttReconnectActionListener(String paramString)
    {
      this.methodName = paramString;
    }
    
    private void rescheduleReconnectCycle(int paramInt)
    {
      ??? = new StringBuilder();
      ((StringBuilder)???).append(this.methodName);
      ((StringBuilder)???).append(":rescheduleReconnectCycle");
      ??? = ((StringBuilder)???).toString();
      MqttAsyncClient.log.fine(MqttAsyncClient.CLASS_NAME, (String)???, "505", new Object[] { MqttAsyncClient.this.clientId, String.valueOf(MqttAsyncClient.reconnectDelay) });
      synchronized (MqttAsyncClient.clientLock)
      {
        if (MqttAsyncClient.this.connOpts.isAutomaticReconnect()) {
          if (MqttAsyncClient.this.reconnectTimer != null)
          {
            Timer localTimer = MqttAsyncClient.this.reconnectTimer;
            MqttAsyncClient.ReconnectTask localReconnectTask = new org/eclipse/paho/client/mqttv3/MqttAsyncClient$ReconnectTask;
            localReconnectTask.<init>(MqttAsyncClient.this, null);
            localTimer.schedule(localReconnectTask, paramInt);
          }
          else
          {
            MqttAsyncClient.access$702(paramInt);
            MqttAsyncClient.this.startReconnectCycle();
          }
        }
        return;
      }
    }
    
    public void onFailure(IMqttToken paramIMqttToken, Throwable paramThrowable)
    {
      MqttAsyncClient.log.fine(MqttAsyncClient.CLASS_NAME, this.methodName, "502", new Object[] { paramIMqttToken.getClient().getClientId() });
      if (MqttAsyncClient.reconnectDelay < 128000) {
        MqttAsyncClient.access$702(MqttAsyncClient.reconnectDelay * 2);
      }
      rescheduleReconnectCycle(MqttAsyncClient.reconnectDelay);
    }
    
    public void onSuccess(IMqttToken paramIMqttToken)
    {
      MqttAsyncClient.log.fine(MqttAsyncClient.CLASS_NAME, this.methodName, "501", new Object[] { paramIMqttToken.getClient().getClientId() });
      MqttAsyncClient.this.comms.setRestingState(false);
      MqttAsyncClient.this.stopReconnectCycle();
    }
  }
  
  class MqttReconnectCallback
    implements MqttCallbackExtended
  {
    final boolean automaticReconnect;
    
    MqttReconnectCallback(boolean paramBoolean)
    {
      this.automaticReconnect = paramBoolean;
    }
    
    public void connectComplete(boolean paramBoolean, String paramString) {}
    
    public void connectionLost(Throwable paramThrowable)
    {
      if (this.automaticReconnect)
      {
        MqttAsyncClient.this.comms.setRestingState(true);
        MqttAsyncClient.access$402(MqttAsyncClient.this, true);
        MqttAsyncClient.this.startReconnectCycle();
      }
    }
    
    public void deliveryComplete(IMqttDeliveryToken paramIMqttDeliveryToken) {}
    
    public void messageArrived(String paramString, MqttMessage paramMqttMessage) {}
  }
  
  private class ReconnectTask
    extends TimerTask
  {
    private static final String methodName = "ReconnectTask.run";
    
    private ReconnectTask() {}
    
    public void run()
    {
      MqttAsyncClient.log.fine(MqttAsyncClient.CLASS_NAME, "ReconnectTask.run", "506");
      MqttAsyncClient.this.attemptReconnect();
    }
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/MqttAsyncClient.class
 *
 * Reversed by:           J
 */