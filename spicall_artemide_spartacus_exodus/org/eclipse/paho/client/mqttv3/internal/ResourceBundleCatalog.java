package org.eclipse.paho.client.mqttv3.internal;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ResourceBundleCatalog
  extends MessageCatalog
{
  private ResourceBundle bundle = ResourceBundle.getBundle("org.eclipse.paho.client.mqttv3.internal.nls.messages");
  
  protected String getLocalizedMessage(int paramInt)
  {
    try
    {
      String str = this.bundle.getString(Integer.toString(paramInt));
      return str;
    }
    catch (MissingResourceException localMissingResourceException) {}
    return "MqttException";
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/ResourceBundleCatalog.class
 *
 * Reversed by:           J
 */