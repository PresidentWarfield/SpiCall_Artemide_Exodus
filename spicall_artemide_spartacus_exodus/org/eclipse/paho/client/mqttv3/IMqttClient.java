package org.eclipse.paho.client.mqttv3;

public abstract interface IMqttClient
{
  public abstract void close();
  
  public abstract void connect();
  
  public abstract void connect(MqttConnectOptions paramMqttConnectOptions);
  
  public abstract IMqttToken connectWithResult(MqttConnectOptions paramMqttConnectOptions);
  
  public abstract void disconnect();
  
  public abstract void disconnect(long paramLong);
  
  public abstract void disconnectForcibly();
  
  public abstract void disconnectForcibly(long paramLong);
  
  public abstract void disconnectForcibly(long paramLong1, long paramLong2);
  
  public abstract String getClientId();
  
  public abstract IMqttDeliveryToken[] getPendingDeliveryTokens();
  
  public abstract String getServerURI();
  
  public abstract MqttTopic getTopic(String paramString);
  
  public abstract boolean isConnected();
  
  public abstract void messageArrivedComplete(int paramInt1, int paramInt2);
  
  public abstract void publish(String paramString, MqttMessage paramMqttMessage);
  
  public abstract void publish(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean);
  
  public abstract void setCallback(MqttCallback paramMqttCallback);
  
  public abstract void setManualAcks(boolean paramBoolean);
  
  public abstract void subscribe(String paramString);
  
  public abstract void subscribe(String paramString, int paramInt);
  
  public abstract void subscribe(String paramString, int paramInt, IMqttMessageListener paramIMqttMessageListener);
  
  public abstract void subscribe(String paramString, IMqttMessageListener paramIMqttMessageListener);
  
  public abstract void subscribe(String[] paramArrayOfString);
  
  public abstract void subscribe(String[] paramArrayOfString, int[] paramArrayOfInt);
  
  public abstract void subscribe(String[] paramArrayOfString, int[] paramArrayOfInt, IMqttMessageListener[] paramArrayOfIMqttMessageListener);
  
  public abstract void subscribe(String[] paramArrayOfString, IMqttMessageListener[] paramArrayOfIMqttMessageListener);
  
  public abstract IMqttToken subscribeWithResponse(String paramString);
  
  public abstract IMqttToken subscribeWithResponse(String paramString, int paramInt);
  
  public abstract IMqttToken subscribeWithResponse(String paramString, int paramInt, IMqttMessageListener paramIMqttMessageListener);
  
  public abstract IMqttToken subscribeWithResponse(String paramString, IMqttMessageListener paramIMqttMessageListener);
  
  public abstract IMqttToken subscribeWithResponse(String[] paramArrayOfString);
  
  public abstract IMqttToken subscribeWithResponse(String[] paramArrayOfString, int[] paramArrayOfInt);
  
  public abstract IMqttToken subscribeWithResponse(String[] paramArrayOfString, int[] paramArrayOfInt, IMqttMessageListener[] paramArrayOfIMqttMessageListener);
  
  public abstract IMqttToken subscribeWithResponse(String[] paramArrayOfString, IMqttMessageListener[] paramArrayOfIMqttMessageListener);
  
  public abstract void unsubscribe(String paramString);
  
  public abstract void unsubscribe(String[] paramArrayOfString);
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/IMqttClient.class
 *
 * Reversed by:           J
 */