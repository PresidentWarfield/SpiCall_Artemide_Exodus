package org.eclipse.paho.client.mqttv3.logging;

import java.lang.reflect.Method;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LoggerFactory
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.logging.LoggerFactory";
  public static final String MQTT_CLIENT_MSG_CAT = "org.eclipse.paho.client.mqttv3.internal.nls.logcat";
  private static String jsr47LoggerClassName = JSR47Logger.class.getName();
  private static String overrideloggerClassName;
  
  public static Logger getLogger(String paramString1, String paramString2)
  {
    String str1 = overrideloggerClassName;
    String str2 = str1;
    if (str1 == null) {
      str2 = jsr47LoggerClassName;
    }
    paramString1 = getLogger(str2, ResourceBundle.getBundle(paramString1), paramString2, null);
    if (paramString1 != null) {
      return paramString1;
    }
    throw new MissingResourceException("Error locating the logging class", CLASS_NAME, paramString2);
  }
  
  private static Logger getLogger(String paramString1, ResourceBundle paramResourceBundle, String paramString2, String paramString3)
  {
    try
    {
      paramString1 = Class.forName(paramString1);
      if (paramString1 != null) {
        try
        {
          paramString1 = (Logger)paramString1.newInstance();
          paramString1.initialise(paramResourceBundle, paramString2, paramString3);
        }
        catch (SecurityException paramString1)
        {
          return null;
        }
        catch (ExceptionInInitializerError paramString1)
        {
          return null;
        }
        catch (InstantiationException paramString1)
        {
          return null;
        }
        catch (IllegalAccessException paramString1)
        {
          return null;
        }
      } else {
        paramString1 = null;
      }
      return paramString1;
    }
    catch (ClassNotFoundException paramString1)
    {
      return null;
    }
    catch (NoClassDefFoundError paramString1) {}
    return null;
  }
  
  public static String getLoggingProperty(String paramString)
  {
    try
    {
      Class localClass = Class.forName("java.util.logging.LogManager");
      Object localObject = localClass.getMethod("getLogManager", new Class[0]).invoke(null, null);
      paramString = (String)localClass.getMethod("getProperty", new Class[] { String.class }).invoke(localObject, new Object[] { paramString });
    }
    catch (Exception paramString)
    {
      paramString = null;
    }
    return paramString;
  }
  
  public static void setLogger(String paramString)
  {
    overrideloggerClassName = paramString;
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/logging/LoggerFactory.class
 *
 * Reversed by:           J
 */