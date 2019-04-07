package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttToken;

public class ConnectActionListener
  implements IMqttActionListener
{
  private MqttAsyncClient client;
  private ClientComms comms;
  private MqttCallbackExtended mqttCallbackExtended;
  private MqttConnectOptions options;
  private int originalMqttVersion;
  private MqttClientPersistence persistence;
  private boolean reconnect;
  private IMqttActionListener userCallback;
  private Object userContext;
  private MqttToken userToken;
  
  public ConnectActionListener(MqttAsyncClient paramMqttAsyncClient, MqttClientPersistence paramMqttClientPersistence, ClientComms paramClientComms, MqttConnectOptions paramMqttConnectOptions, MqttToken paramMqttToken, Object paramObject, IMqttActionListener paramIMqttActionListener, boolean paramBoolean)
  {
    this.persistence = paramMqttClientPersistence;
    this.client = paramMqttAsyncClient;
    this.comms = paramClientComms;
    this.options = paramMqttConnectOptions;
    this.userToken = paramMqttToken;
    this.userContext = paramObject;
    this.userCallback = paramIMqttActionListener;
    this.originalMqttVersion = paramMqttConnectOptions.getMqttVersion();
    this.reconnect = paramBoolean;
  }
  
  public void connect()
  {
    MqttToken localMqttToken = new MqttToken(this.client.getClientId());
    localMqttToken.setActionCallback(this);
    localMqttToken.setUserContext(this);
    this.persistence.open(this.client.getClientId(), this.client.getServerURI());
    if (this.options.isCleanSession()) {
      this.persistence.clear();
    }
    if (this.options.getMqttVersion() == 0) {
      this.options.setMqttVersion(4);
    }
    try
    {
      this.comms.connect(this.options, localMqttToken);
    }
    catch (MqttException localMqttException)
    {
      onFailure(localMqttToken, localMqttException);
    }
  }
  
  public void onFailure(IMqttToken paramIMqttToken, Throwable paramThrowable)
  {
    int i = this.comms.getNetworkModules().length;
    int j = this.comms.getNetworkModuleIndex() + 1;
    if ((j >= i) && ((this.originalMqttVersion != 0) || (this.options.getMqttVersion() != 4)))
    {
      if (this.originalMqttVersion == 0) {
        this.options.setMqttVersion(0);
      }
      if ((paramThrowable instanceof MqttException)) {
        paramIMqttToken = (MqttException)paramThrowable;
      } else {
        paramIMqttToken = new MqttException(paramThrowable);
      }
      this.userToken.internalTok.markComplete(null, paramIMqttToken);
      this.userToken.internalTok.notifyComplete();
      this.userToken.internalTok.setClient(this.client);
      if (this.userCallback != null)
      {
        this.userToken.setUserContext(this.userContext);
        this.userCallback.onFailure(this.userToken, paramThrowable);
      }
    }
    else
    {
      if (this.originalMqttVersion == 0)
      {
        if (this.options.getMqttVersion() == 4)
        {
          this.options.setMqttVersion(3);
        }
        else
        {
          this.options.setMqttVersion(4);
          this.comms.setNetworkModuleIndex(j);
        }
      }
      else {
        this.comms.setNetworkModuleIndex(j);
      }
      try
      {
        connect();
      }
      catch (MqttPersistenceException paramThrowable)
      {
        onFailure(paramIMqttToken, paramThrowable);
      }
    }
  }
  
  public void onSuccess(IMqttToken paramIMqttToken)
  {
    if (this.originalMqttVersion == 0) {
      this.options.setMqttVersion(0);
    }
    this.userToken.internalTok.markComplete(paramIMqttToken.getResponse(), null);
    this.userToken.internalTok.notifyComplete();
    this.userToken.internalTok.setClient(this.client);
    this.comms.notifyConnect();
    if (this.userCallback != null)
    {
      this.userToken.setUserContext(this.userContext);
      this.userCallback.onSuccess(this.userToken);
    }
    if (this.mqttCallbackExtended != null)
    {
      paramIMqttToken = this.comms.getNetworkModules()[this.comms.getNetworkModuleIndex()].getServerURI();
      this.mqttCallbackExtended.connectComplete(this.reconnect, paramIMqttToken);
    }
  }
  
  public void setMqttCallbackExtended(MqttCallbackExtended paramMqttCallbackExtended)
  {
    this.mqttCallbackExtended = paramMqttCallbackExtended;
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/ConnectActionListener.class
 *
 * Reversed by:           J
 */