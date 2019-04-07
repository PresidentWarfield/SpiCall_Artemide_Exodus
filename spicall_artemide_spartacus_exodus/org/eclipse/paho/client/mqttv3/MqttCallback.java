package org.eclipse.paho.client.mqttv3;

public abstract interface MqttCallback
{
  public abstract void connectionLost(Throwable paramThrowable);
  
  public abstract void deliveryComplete(IMqttDeliveryToken paramIMqttDeliveryToken);
  
  public abstract void messageArrived(String paramString, MqttMessage paramMqttMessage);
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/MqttCallback.class
 *
 * Reversed by:           J
 */