package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.MqttTopic;

public abstract interface DestinationProvider
{
  public abstract MqttTopic getTopic(String paramString);
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/DestinationProvider.class
 *
 * Reversed by:           J
 */