package org.eclipse.paho.client.mqttv3.internal;

public abstract class MessageCatalog
{
  private static MessageCatalog INSTANCE;
  
  public static final String getMessage(int paramInt)
  {
    if (INSTANCE == null) {
      if (ExceptionHelper.isClassAvailable("java.util.ResourceBundle")) {
        try
        {
          INSTANCE = (MessageCatalog)Class.forName("org.eclipse.paho.client.mqttv3.internal.ResourceBundleCatalog").newInstance();
        }
        catch (Exception localException1)
        {
          return "";
        }
      } else if (ExceptionHelper.isClassAvailable("org.eclipse.paho.client.mqttv3.internal.MIDPCatalog")) {
        try
        {
          INSTANCE = (MessageCatalog)Class.forName("org.eclipse.paho.client.mqttv3.internal.MIDPCatalog").newInstance();
        }
        catch (Exception localException2)
        {
          return "";
        }
      }
    }
    return INSTANCE.getLocalizedMessage(paramInt);
  }
  
  protected abstract String getLocalizedMessage(int paramInt);
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/MessageCatalog.class
 *
 * Reversed by:           J
 */