package org.eclipse.paho.client.mqttv3;

public abstract interface IMqttActionListener
{
  public abstract void onFailure(IMqttToken paramIMqttToken, Throwable paramThrowable);
  
  public abstract void onSuccess(IMqttToken paramIMqttToken);
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/IMqttActionListener.class
 *
 * Reversed by:           J
 */