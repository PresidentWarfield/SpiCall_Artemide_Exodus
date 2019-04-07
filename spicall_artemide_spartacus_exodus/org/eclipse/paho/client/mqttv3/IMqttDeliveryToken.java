package org.eclipse.paho.client.mqttv3;

public abstract interface IMqttDeliveryToken
  extends IMqttToken
{
  public abstract MqttMessage getMessage();
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/IMqttDeliveryToken.class
 *
 * Reversed by:           J
 */