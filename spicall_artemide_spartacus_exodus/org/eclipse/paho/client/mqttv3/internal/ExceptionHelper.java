package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class ExceptionHelper
{
  public static MqttException createMqttException(int paramInt)
  {
    if ((paramInt != 4) && (paramInt != 5)) {
      return new MqttException(paramInt);
    }
    return new MqttSecurityException(paramInt);
  }
  
  public static MqttException createMqttException(Throwable paramThrowable)
  {
    if (paramThrowable.getClass().getName().equals("java.security.GeneralSecurityException")) {
      return new MqttSecurityException(paramThrowable);
    }
    return new MqttException(paramThrowable);
  }
  
  public static boolean isClassAvailable(String paramString)
  {
    boolean bool;
    try
    {
      Class.forName(paramString);
      bool = true;
    }
    catch (ClassNotFoundException paramString)
    {
      bool = false;
    }
    return bool;
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/ExceptionHelper.class
 *
 * Reversed by:           J
 */