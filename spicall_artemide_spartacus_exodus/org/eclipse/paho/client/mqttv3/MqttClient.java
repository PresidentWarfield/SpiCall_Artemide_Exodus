package org.eclipse.paho.client.mqttv3;

import java.util.concurrent.ScheduledExecutorService;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.eclipse.paho.client.mqttv3.util.Debug;

public class MqttClient
  implements IMqttClient
{
  protected MqttAsyncClient aClient = null;
  protected long timeToWait = -1L;
  
  public MqttClient(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, new MqttDefaultFilePersistence());
  }
  
  public MqttClient(String paramString1, String paramString2, MqttClientPersistence paramMqttClientPersistence)
  {
    this.aClient = new MqttAsyncClient(paramString1, paramString2, paramMqttClientPersistence);
  }
  
  public MqttClient(String paramString1, String paramString2, MqttClientPersistence paramMqttClientPersistence, ScheduledExecutorService paramScheduledExecutorService)
  {
    this.aClient = new MqttAsyncClient(paramString1, paramString2, paramMqttClientPersistence, new ScheduledExecutorPingSender(paramScheduledExecutorService), paramScheduledExecutorService);
  }
  
  public static String generateClientId()
  {
    return MqttAsyncClient.generateClientId();
  }
  
  public void close()
  {
    this.aClient.close(false);
  }
  
  public void close(boolean paramBoolean)
  {
    this.aClient.close(paramBoolean);
  }
  
  public void connect()
  {
    connect(new MqttConnectOptions());
  }
  
  public void connect(MqttConnectOptions paramMqttConnectOptions)
  {
    this.aClient.connect(paramMqttConnectOptions, null, null).waitForCompletion(getTimeToWait());
  }
  
  public IMqttToken connectWithResult(MqttConnectOptions paramMqttConnectOptions)
  {
    paramMqttConnectOptions = this.aClient.connect(paramMqttConnectOptions, null, null);
    paramMqttConnectOptions.waitForCompletion(getTimeToWait());
    return paramMqttConnectOptions;
  }
  
  public void disconnect()
  {
    this.aClient.disconnect().waitForCompletion();
  }
  
  public void disconnect(long paramLong)
  {
    this.aClient.disconnect(paramLong, null, null).waitForCompletion();
  }
  
  public void disconnectForcibly()
  {
    this.aClient.disconnectForcibly();
  }
  
  public void disconnectForcibly(long paramLong)
  {
    this.aClient.disconnectForcibly(paramLong);
  }
  
  public void disconnectForcibly(long paramLong1, long paramLong2)
  {
    this.aClient.disconnectForcibly(paramLong1, paramLong2);
  }
  
  public void disconnectForcibly(long paramLong1, long paramLong2, boolean paramBoolean)
  {
    this.aClient.disconnectForcibly(paramLong1, paramLong2, paramBoolean);
  }
  
  public String getClientId()
  {
    return this.aClient.getClientId();
  }
  
  public String getCurrentServerURI()
  {
    return this.aClient.getCurrentServerURI();
  }
  
  public Debug getDebug()
  {
    return this.aClient.getDebug();
  }
  
  public IMqttDeliveryToken[] getPendingDeliveryTokens()
  {
    return this.aClient.getPendingDeliveryTokens();
  }
  
  public String getServerURI()
  {
    return this.aClient.getServerURI();
  }
  
  public long getTimeToWait()
  {
    return this.timeToWait;
  }
  
  public MqttTopic getTopic(String paramString)
  {
    return this.aClient.getTopic(paramString);
  }
  
  public boolean isConnected()
  {
    return this.aClient.isConnected();
  }
  
  public void messageArrivedComplete(int paramInt1, int paramInt2)
  {
    this.aClient.messageArrivedComplete(paramInt1, paramInt2);
  }
  
  public void publish(String paramString, MqttMessage paramMqttMessage)
  {
    this.aClient.publish(paramString, paramMqttMessage, null, null).waitForCompletion(getTimeToWait());
  }
  
  public void publish(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
  {
    paramArrayOfByte = new MqttMessage(paramArrayOfByte);
    paramArrayOfByte.setQos(paramInt);
    paramArrayOfByte.setRetained(paramBoolean);
    publish(paramString, paramArrayOfByte);
  }
  
  public void reconnect()
  {
    this.aClient.reconnect();
  }
  
  public void setCallback(MqttCallback paramMqttCallback)
  {
    this.aClient.setCallback(paramMqttCallback);
  }
  
  public void setManualAcks(boolean paramBoolean)
  {
    this.aClient.setManualAcks(paramBoolean);
  }
  
  public void setTimeToWait(long paramLong)
  {
    if (paramLong >= -1L)
    {
      this.timeToWait = paramLong;
      return;
    }
    throw new IllegalArgumentException();
  }
  
  public void subscribe(String paramString)
  {
    subscribe(new String[] { paramString }, new int[] { 1 });
  }
  
  public void subscribe(String paramString, int paramInt)
  {
    subscribe(new String[] { paramString }, new int[] { paramInt });
  }
  
  public void subscribe(String paramString, int paramInt, IMqttMessageListener paramIMqttMessageListener)
  {
    subscribe(new String[] { paramString }, new int[] { paramInt }, new IMqttMessageListener[] { paramIMqttMessageListener });
  }
  
  public void subscribe(String paramString, IMqttMessageListener paramIMqttMessageListener)
  {
    subscribe(new String[] { paramString }, new int[] { 1 }, new IMqttMessageListener[] { paramIMqttMessageListener });
  }
  
  public void subscribe(String[] paramArrayOfString)
  {
    int[] arrayOfInt = new int[paramArrayOfString.length];
    for (int i = 0; i < arrayOfInt.length; i++) {
      arrayOfInt[i] = 1;
    }
    subscribe(paramArrayOfString, arrayOfInt);
  }
  
  public void subscribe(String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    paramArrayOfString = this.aClient.subscribe(paramArrayOfString, paramArrayOfInt, null, null);
    paramArrayOfString.waitForCompletion(getTimeToWait());
    paramArrayOfString = paramArrayOfString.getGrantedQos();
    for (int i = 0; i < paramArrayOfString.length; i++) {
      paramArrayOfInt[i] = paramArrayOfString[i];
    }
    if ((paramArrayOfString.length == 1) && (paramArrayOfInt[0] == 128)) {
      throw new MqttException(128);
    }
  }
  
  public void subscribe(String[] paramArrayOfString, int[] paramArrayOfInt, IMqttMessageListener[] paramArrayOfIMqttMessageListener)
  {
    subscribe(paramArrayOfString, paramArrayOfInt);
    for (int i = 0; i < paramArrayOfString.length; i++) {
      this.aClient.comms.setMessageListener(paramArrayOfString[i], paramArrayOfIMqttMessageListener[i]);
    }
  }
  
  public void subscribe(String[] paramArrayOfString, IMqttMessageListener[] paramArrayOfIMqttMessageListener)
  {
    int[] arrayOfInt = new int[paramArrayOfString.length];
    for (int i = 0; i < arrayOfInt.length; i++) {
      arrayOfInt[i] = 1;
    }
    subscribe(paramArrayOfString, arrayOfInt, paramArrayOfIMqttMessageListener);
  }
  
  public IMqttToken subscribeWithResponse(String paramString)
  {
    return subscribeWithResponse(new String[] { paramString }, new int[] { 1 });
  }
  
  public IMqttToken subscribeWithResponse(String paramString, int paramInt)
  {
    return subscribeWithResponse(new String[] { paramString }, new int[] { paramInt });
  }
  
  public IMqttToken subscribeWithResponse(String paramString, int paramInt, IMqttMessageListener paramIMqttMessageListener)
  {
    return subscribeWithResponse(new String[] { paramString }, new int[] { paramInt }, new IMqttMessageListener[] { paramIMqttMessageListener });
  }
  
  public IMqttToken subscribeWithResponse(String paramString, IMqttMessageListener paramIMqttMessageListener)
  {
    return subscribeWithResponse(new String[] { paramString }, new int[] { 1 }, new IMqttMessageListener[] { paramIMqttMessageListener });
  }
  
  public IMqttToken subscribeWithResponse(String[] paramArrayOfString)
  {
    int[] arrayOfInt = new int[paramArrayOfString.length];
    for (int i = 0; i < arrayOfInt.length; i++) {
      arrayOfInt[i] = 1;
    }
    return subscribeWithResponse(paramArrayOfString, arrayOfInt);
  }
  
  public IMqttToken subscribeWithResponse(String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    paramArrayOfString = this.aClient.subscribe(paramArrayOfString, paramArrayOfInt, null, null);
    paramArrayOfString.waitForCompletion(getTimeToWait());
    return paramArrayOfString;
  }
  
  public IMqttToken subscribeWithResponse(String[] paramArrayOfString, int[] paramArrayOfInt, IMqttMessageListener[] paramArrayOfIMqttMessageListener)
  {
    paramArrayOfInt = subscribeWithResponse(paramArrayOfString, paramArrayOfInt);
    for (int i = 0; i < paramArrayOfString.length; i++) {
      this.aClient.comms.setMessageListener(paramArrayOfString[i], paramArrayOfIMqttMessageListener[i]);
    }
    return paramArrayOfInt;
  }
  
  public IMqttToken subscribeWithResponse(String[] paramArrayOfString, IMqttMessageListener[] paramArrayOfIMqttMessageListener)
  {
    int[] arrayOfInt = new int[paramArrayOfString.length];
    for (int i = 0; i < arrayOfInt.length; i++) {
      arrayOfInt[i] = 1;
    }
    return subscribeWithResponse(paramArrayOfString, arrayOfInt, paramArrayOfIMqttMessageListener);
  }
  
  public void unsubscribe(String paramString)
  {
    unsubscribe(new String[] { paramString });
  }
  
  public void unsubscribe(String[] paramArrayOfString)
  {
    this.aClient.unsubscribe(paramArrayOfString, null, null).waitForCompletion(getTimeToWait());
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/MqttClient.class
 *
 * Reversed by:           J
 */