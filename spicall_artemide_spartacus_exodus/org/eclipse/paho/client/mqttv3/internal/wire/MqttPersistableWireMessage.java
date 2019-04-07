package org.eclipse.paho.client.mqttv3.internal.wire;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public abstract class MqttPersistableWireMessage
  extends MqttWireMessage
  implements MqttPersistable
{
  public MqttPersistableWireMessage(byte paramByte)
  {
    super(paramByte);
  }
  
  public byte[] getHeaderBytes()
  {
    try
    {
      byte[] arrayOfByte = getHeader();
      return arrayOfByte;
    }
    catch (MqttException localMqttException)
    {
      throw new MqttPersistenceException(localMqttException.getCause());
    }
  }
  
  public int getHeaderLength()
  {
    return getHeaderBytes().length;
  }
  
  public int getHeaderOffset()
  {
    return 0;
  }
  
  public byte[] getPayloadBytes()
  {
    try
    {
      byte[] arrayOfByte = getPayload();
      return arrayOfByte;
    }
    catch (MqttException localMqttException)
    {
      throw new MqttPersistenceException(localMqttException.getCause());
    }
  }
  
  public int getPayloadLength()
  {
    return 0;
  }
  
  public int getPayloadOffset()
  {
    return 0;
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/wire/MqttPersistableWireMessage.class
 *
 * Reversed by:           J
 */