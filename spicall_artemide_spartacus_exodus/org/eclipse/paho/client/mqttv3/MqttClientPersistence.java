package org.eclipse.paho.client.mqttv3;

import java.util.Enumeration;

public abstract interface MqttClientPersistence
{
  public abstract void clear();
  
  public abstract void close();
  
  public abstract boolean containsKey(String paramString);
  
  public abstract MqttPersistable get(String paramString);
  
  public abstract Enumeration keys();
  
  public abstract void open(String paramString1, String paramString2);
  
  public abstract void put(String paramString, MqttPersistable paramMqttPersistable);
  
  public abstract void remove(String paramString);
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/MqttClientPersistence.class
 *
 * Reversed by:           J
 */