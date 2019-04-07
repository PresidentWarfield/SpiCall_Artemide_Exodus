package org.eclipse.paho.client.mqttv3;

public abstract interface IMqttAsyncClient
{
  public abstract void close();
  
  public abstract IMqttToken connect();
  
  public abstract IMqttToken connect(Object paramObject, IMqttActionListener paramIMqttActionListener);
  
  public abstract IMqttToken connect(MqttConnectOptions paramMqttConnectOptions);
  
  public abstract IMqttToken connect(MqttConnectOptions paramMqttConnectOptions, Object paramObject, IMqttActionListener paramIMqttActionListener);
  
  public abstract IMqttToken disconnect();
  
  public abstract IMqttToken disconnect(long paramLong);
  
  public abstract IMqttToken disconnect(long paramLong, Object paramObject, IMqttActionListener paramIMqttActionListener);
  
  public abstract IMqttToken disconnect(Object paramObject, IMqttActionListener paramIMqttActionListener);
  
  public abstract void disconnectForcibly();
  
  public abstract void disconnectForcibly(long paramLong);
  
  public abstract void disconnectForcibly(long paramLong1, long paramLong2);
  
  public abstract String getClientId();
  
  public abstract IMqttDeliveryToken[] getPendingDeliveryTokens();
  
  public abstract String getServerURI();
  
  public abstract boolean isConnected();
  
  public abstract void messageArrivedComplete(int paramInt1, int paramInt2);
  
  public abstract IMqttDeliveryToken publish(String paramString, MqttMessage paramMqttMessage);
  
  public abstract IMqttDeliveryToken publish(String paramString, MqttMessage paramMqttMessage, Object paramObject, IMqttActionListener paramIMqttActionListener);
  
  public abstract IMqttDeliveryToken publish(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean);
  
  public abstract IMqttDeliveryToken publish(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean, Object paramObject, IMqttActionListener paramIMqttActionListener);
  
  public abstract void setCallback(MqttCallback paramMqttCallback);
  
  public abstract void setManualAcks(boolean paramBoolean);
  
  public abstract IMqttToken subscribe(String paramString, int paramInt);
  
  public abstract IMqttToken subscribe(String paramString, int paramInt, Object paramObject, IMqttActionListener paramIMqttActionListener);
  
  public abstract IMqttToken subscribe(String paramString, int paramInt, Object paramObject, IMqttActionListener paramIMqttActionListener, IMqttMessageListener paramIMqttMessageListener);
  
  public abstract IMqttToken subscribe(String paramString, int paramInt, IMqttMessageListener paramIMqttMessageListener);
  
  public abstract IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt);
  
  public abstract IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt, Object paramObject, IMqttActionListener paramIMqttActionListener);
  
  public abstract IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt, Object paramObject, IMqttActionListener paramIMqttActionListener, IMqttMessageListener[] paramArrayOfIMqttMessageListener);
  
  public abstract IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt, IMqttMessageListener[] paramArrayOfIMqttMessageListener);
  
  public abstract IMqttToken unsubscribe(String paramString);
  
  public abstract IMqttToken unsubscribe(String paramString, Object paramObject, IMqttActionListener paramIMqttActionListener);
  
  public abstract IMqttToken unsubscribe(String[] paramArrayOfString);
  
  public abstract IMqttToken unsubscribe(String[] paramArrayOfString, Object paramObject, IMqttActionListener paramIMqttActionListener);
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/IMqttAsyncClient.class
 *
 * Reversed by:           J
 */