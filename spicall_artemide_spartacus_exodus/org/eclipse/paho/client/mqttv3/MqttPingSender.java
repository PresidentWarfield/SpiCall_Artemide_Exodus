package org.eclipse.paho.client.mqttv3;

import org.eclipse.paho.client.mqttv3.internal.ClientComms;

public abstract interface MqttPingSender
{
  public abstract void init(ClientComms paramClientComms);
  
  public abstract void schedule(long paramLong);
  
  public abstract void start();
  
  public abstract void stop();
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/MqttPingSender.class
 *
 * Reversed by:           J
 */