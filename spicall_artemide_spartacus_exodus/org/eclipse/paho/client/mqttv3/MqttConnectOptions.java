package org.eclipse.paho.client.mqttv3;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import org.eclipse.paho.client.mqttv3.util.Debug;

public class MqttConnectOptions
{
  public static final boolean CLEAN_SESSION_DEFAULT = true;
  public static final int CONNECTION_TIMEOUT_DEFAULT = 30;
  public static final int KEEP_ALIVE_INTERVAL_DEFAULT = 60;
  public static final int MAX_INFLIGHT_DEFAULT = 10;
  public static final int MQTT_VERSION_3_1 = 3;
  public static final int MQTT_VERSION_3_1_1 = 4;
  public static final int MQTT_VERSION_DEFAULT = 0;
  protected static final int URI_TYPE_LOCAL = 2;
  protected static final int URI_TYPE_SSL = 1;
  protected static final int URI_TYPE_TCP = 0;
  protected static final int URI_TYPE_WS = 3;
  protected static final int URI_TYPE_WSS = 4;
  private int MqttVersion = 0;
  private boolean automaticReconnect = false;
  private boolean cleanSession = true;
  private int connectionTimeout = 30;
  private int keepAliveInterval = 60;
  private int maxInflight = 10;
  private char[] password;
  private String[] serverURIs = null;
  private SocketFactory socketFactory;
  private Properties sslClientProps = null;
  private HostnameVerifier sslHostnameVerifier = null;
  private String userName;
  private String willDestination = null;
  private MqttMessage willMessage = null;
  
  public static int validateURI(String paramString)
  {
    try
    {
      Object localObject = new java/net/URI;
      ((URI)localObject).<init>(paramString);
      if ("ws".equals(((URI)localObject).getScheme())) {
        return 3;
      }
      if ("wss".equals(((URI)localObject).getScheme())) {
        return 4;
      }
      if ((((URI)localObject).getPath() != null) && (!((URI)localObject).getPath().isEmpty()))
      {
        localObject = new java/lang/IllegalArgumentException;
        ((IllegalArgumentException)localObject).<init>(paramString);
        throw ((Throwable)localObject);
      }
      if ("tcp".equals(((URI)localObject).getScheme())) {
        return 0;
      }
      if ("ssl".equals(((URI)localObject).getScheme())) {
        return 1;
      }
      if ("local".equals(((URI)localObject).getScheme())) {
        return 2;
      }
      localObject = new java/lang/IllegalArgumentException;
      ((IllegalArgumentException)localObject).<init>(paramString);
      throw ((Throwable)localObject);
    }
    catch (URISyntaxException localURISyntaxException)
    {
      throw new IllegalArgumentException(paramString);
    }
  }
  
  private void validateWill(String paramString, Object paramObject)
  {
    if ((paramString != null) && (paramObject != null))
    {
      MqttTopic.validate(paramString, false);
      return;
    }
    throw new IllegalArgumentException();
  }
  
  public int getConnectionTimeout()
  {
    return this.connectionTimeout;
  }
  
  public Properties getDebug()
  {
    Properties localProperties = new Properties();
    localProperties.put("MqttVersion", new Integer(getMqttVersion()));
    localProperties.put("CleanSession", Boolean.valueOf(isCleanSession()));
    localProperties.put("ConTimeout", new Integer(getConnectionTimeout()));
    localProperties.put("KeepAliveInterval", new Integer(getKeepAliveInterval()));
    String str;
    if (getUserName() == null) {
      str = "null";
    } else {
      str = getUserName();
    }
    localProperties.put("UserName", str);
    if (getWillDestination() == null) {
      str = "null";
    } else {
      str = getWillDestination();
    }
    localProperties.put("WillDestination", str);
    if (getSocketFactory() == null) {
      localProperties.put("SocketFactory", "null");
    } else {
      localProperties.put("SocketFactory", getSocketFactory());
    }
    if (getSSLProperties() == null) {
      localProperties.put("SSLProperties", "null");
    } else {
      localProperties.put("SSLProperties", getSSLProperties());
    }
    return localProperties;
  }
  
  public int getKeepAliveInterval()
  {
    return this.keepAliveInterval;
  }
  
  public int getMaxInflight()
  {
    return this.maxInflight;
  }
  
  public int getMqttVersion()
  {
    return this.MqttVersion;
  }
  
  public char[] getPassword()
  {
    return this.password;
  }
  
  public HostnameVerifier getSSLHostnameVerifier()
  {
    return this.sslHostnameVerifier;
  }
  
  public Properties getSSLProperties()
  {
    return this.sslClientProps;
  }
  
  public String[] getServerURIs()
  {
    return this.serverURIs;
  }
  
  public SocketFactory getSocketFactory()
  {
    return this.socketFactory;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public String getWillDestination()
  {
    return this.willDestination;
  }
  
  public MqttMessage getWillMessage()
  {
    return this.willMessage;
  }
  
  public boolean isAutomaticReconnect()
  {
    return this.automaticReconnect;
  }
  
  public boolean isCleanSession()
  {
    return this.cleanSession;
  }
  
  public void setAutomaticReconnect(boolean paramBoolean)
  {
    this.automaticReconnect = paramBoolean;
  }
  
  public void setCleanSession(boolean paramBoolean)
  {
    this.cleanSession = paramBoolean;
  }
  
  public void setConnectionTimeout(int paramInt)
  {
    if (paramInt >= 0)
    {
      this.connectionTimeout = paramInt;
      return;
    }
    throw new IllegalArgumentException();
  }
  
  public void setKeepAliveInterval(int paramInt)
  {
    if (paramInt >= 0)
    {
      this.keepAliveInterval = paramInt;
      return;
    }
    throw new IllegalArgumentException();
  }
  
  public void setMaxInflight(int paramInt)
  {
    if (paramInt >= 0)
    {
      this.maxInflight = paramInt;
      return;
    }
    throw new IllegalArgumentException();
  }
  
  public void setMqttVersion(int paramInt)
  {
    if ((paramInt != 0) && (paramInt != 3) && (paramInt != 4)) {
      throw new IllegalArgumentException();
    }
    this.MqttVersion = paramInt;
  }
  
  public void setPassword(char[] paramArrayOfChar)
  {
    this.password = paramArrayOfChar;
  }
  
  public void setSSLHostnameVerifier(HostnameVerifier paramHostnameVerifier)
  {
    this.sslHostnameVerifier = paramHostnameVerifier;
  }
  
  public void setSSLProperties(Properties paramProperties)
  {
    this.sslClientProps = paramProperties;
  }
  
  public void setServerURIs(String[] paramArrayOfString)
  {
    for (int i = 0; i < paramArrayOfString.length; i++) {
      validateURI(paramArrayOfString[i]);
    }
    this.serverURIs = paramArrayOfString;
  }
  
  public void setSocketFactory(SocketFactory paramSocketFactory)
  {
    this.socketFactory = paramSocketFactory;
  }
  
  public void setUserName(String paramString)
  {
    if ((paramString != null) && (paramString.trim().equals(""))) {
      throw new IllegalArgumentException();
    }
    this.userName = paramString;
  }
  
  protected void setWill(String paramString, MqttMessage paramMqttMessage, int paramInt, boolean paramBoolean)
  {
    this.willDestination = paramString;
    this.willMessage = paramMqttMessage;
    this.willMessage.setQos(paramInt);
    this.willMessage.setRetained(paramBoolean);
    this.willMessage.setMutable(false);
  }
  
  public void setWill(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
  {
    validateWill(paramString, paramArrayOfByte);
    setWill(paramString, new MqttMessage(paramArrayOfByte), paramInt, paramBoolean);
  }
  
  public void setWill(MqttTopic paramMqttTopic, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
  {
    paramMqttTopic = paramMqttTopic.getName();
    validateWill(paramMqttTopic, paramArrayOfByte);
    setWill(paramMqttTopic, new MqttMessage(paramArrayOfByte), paramInt, paramBoolean);
  }
  
  public String toString()
  {
    return Debug.dumpProperties(getDebug(), "Connection options");
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/MqttConnectOptions.class
 *
 * Reversed by:           J
 */