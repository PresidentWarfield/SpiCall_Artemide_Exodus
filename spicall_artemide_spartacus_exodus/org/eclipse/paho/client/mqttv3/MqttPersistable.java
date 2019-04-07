package org.eclipse.paho.client.mqttv3;

public abstract interface MqttPersistable
{
  public abstract byte[] getHeaderBytes();
  
  public abstract int getHeaderLength();
  
  public abstract int getHeaderOffset();
  
  public abstract byte[] getPayloadBytes();
  
  public abstract int getPayloadLength();
  
  public abstract int getPayloadOffset();
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/MqttPersistable.class
 *
 * Reversed by:           J
 */