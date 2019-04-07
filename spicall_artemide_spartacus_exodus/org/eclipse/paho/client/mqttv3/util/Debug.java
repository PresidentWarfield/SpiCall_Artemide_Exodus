package org.eclipse.paho.client.mqttv3.util;

import java.util.Enumeration;
import java.util.Properties;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.internal.ClientState;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class Debug
{
  private static final String CLASS_NAME = ClientComms.class.getName();
  private static final String lineSep = System.getProperty("line.separator", "\n");
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private static final String separator = "==============";
  private String clientID;
  private ClientComms comms;
  
  public Debug(String paramString, ClientComms paramClientComms)
  {
    this.clientID = paramString;
    this.comms = paramClientComms;
    log.setResourceName(paramString);
  }
  
  public static String dumpProperties(Properties paramProperties, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Enumeration localEnumeration = paramProperties.propertyNames();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(lineSep);
    ((StringBuilder)localObject).append("==============");
    ((StringBuilder)localObject).append(" ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(" ");
    ((StringBuilder)localObject).append("==============");
    ((StringBuilder)localObject).append(lineSep);
    localStringBuffer.append(((StringBuilder)localObject).toString());
    while (localEnumeration.hasMoreElements())
    {
      localObject = (String)localEnumeration.nextElement();
      paramString = new StringBuilder();
      paramString.append(left((String)localObject, 28, ' '));
      paramString.append(":  ");
      paramString.append(paramProperties.get(localObject));
      paramString.append(lineSep);
      localStringBuffer.append(paramString.toString());
    }
    paramProperties = new StringBuilder();
    paramProperties.append("==========================================");
    paramProperties.append(lineSep);
    localStringBuffer.append(paramProperties.toString());
    return localStringBuffer.toString();
  }
  
  public static String left(String paramString, int paramInt, char paramChar)
  {
    if (paramString.length() >= paramInt) {
      return paramString;
    }
    StringBuffer localStringBuffer = new StringBuffer(paramInt);
    localStringBuffer.append(paramString);
    paramInt -= paramString.length();
    for (;;)
    {
      paramInt--;
      if (paramInt < 0) {
        break;
      }
      localStringBuffer.append(paramChar);
    }
    return localStringBuffer.toString();
  }
  
  public void dumpBaseDebug()
  {
    dumpVersion();
    dumpSystemProperties();
    dumpMemoryTrace();
  }
  
  public void dumpClientComms()
  {
    Object localObject = this.comms;
    if (localObject != null)
    {
      Properties localProperties = ((ClientComms)localObject).getDebug();
      Logger localLogger = log;
      String str = CLASS_NAME;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(this.clientID);
      ((StringBuilder)localObject).append(" : ClientComms");
      localLogger.fine(str, "dumpClientComms", dumpProperties(localProperties, ((StringBuilder)localObject).toString()).toString());
    }
  }
  
  public void dumpClientDebug()
  {
    dumpClientComms();
    dumpConOptions();
    dumpClientState();
    dumpBaseDebug();
  }
  
  public void dumpClientState()
  {
    Object localObject = this.comms;
    if ((localObject != null) && (((ClientComms)localObject).getClientState() != null))
    {
      Properties localProperties = this.comms.getClientState().getDebug();
      localObject = log;
      String str = CLASS_NAME;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.clientID);
      localStringBuilder.append(" : ClientState");
      ((Logger)localObject).fine(str, "dumpClientState", dumpProperties(localProperties, localStringBuilder.toString()).toString());
    }
  }
  
  public void dumpConOptions()
  {
    Object localObject = this.comms;
    if (localObject != null)
    {
      localObject = ((ClientComms)localObject).getConOptions().getDebug();
      Logger localLogger = log;
      String str = CLASS_NAME;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.clientID);
      localStringBuilder.append(" : Connect Options");
      localLogger.fine(str, "dumpConOptions", dumpProperties((Properties)localObject, localStringBuilder.toString()).toString());
    }
  }
  
  protected void dumpMemoryTrace()
  {
    log.dumpTrace();
  }
  
  public void dumpSystemProperties()
  {
    Properties localProperties = System.getProperties();
    log.fine(CLASS_NAME, "dumpSystemProperties", dumpProperties(localProperties, "SystemProperties").toString());
  }
  
  protected void dumpVersion()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(lineSep);
    localStringBuilder.append("==============");
    localStringBuilder.append(" Version Info ");
    localStringBuilder.append("==============");
    localStringBuilder.append(lineSep);
    localStringBuffer.append(localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(left("Version", 20, ' '));
    localStringBuilder.append(":  ");
    localStringBuilder.append(ClientComms.VERSION);
    localStringBuilder.append(lineSep);
    localStringBuffer.append(localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(left("Build Level", 20, ' '));
    localStringBuilder.append(":  ");
    localStringBuilder.append(ClientComms.BUILD_LEVEL);
    localStringBuilder.append(lineSep);
    localStringBuffer.append(localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("==========================================");
    localStringBuilder.append(lineSep);
    localStringBuffer.append(localStringBuilder.toString());
    log.fine(CLASS_NAME, "dumpVersion", localStringBuffer.toString());
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/util/Debug.class
 *
 * Reversed by:           J
 */