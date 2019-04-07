package org.eclipse.paho.client.mqttv3.persist;

import java.util.Enumeration;
import java.util.Hashtable;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttPersistable;

public class MemoryPersistence
  implements MqttClientPersistence
{
  private Hashtable data;
  
  public void clear()
  {
    this.data.clear();
  }
  
  public void close()
  {
    this.data.clear();
  }
  
  public boolean containsKey(String paramString)
  {
    return this.data.containsKey(paramString);
  }
  
  public MqttPersistable get(String paramString)
  {
    return (MqttPersistable)this.data.get(paramString);
  }
  
  public Enumeration keys()
  {
    return this.data.keys();
  }
  
  public void open(String paramString1, String paramString2)
  {
    this.data = new Hashtable();
  }
  
  public void put(String paramString, MqttPersistable paramMqttPersistable)
  {
    this.data.put(paramString, paramMqttPersistable);
  }
  
  public void remove(String paramString)
  {
    this.data.remove(paramString);
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/persist/MemoryPersistence.class
 *
 * Reversed by:           J
 */