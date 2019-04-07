package org.eclipse.paho.client.mqttv3.internal;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class CommsTokenStore
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.CommsTokenStore";
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private MqttException closedResponse = null;
  private String logContext;
  private Hashtable tokens;
  
  public CommsTokenStore(String paramString)
  {
    log.setResourceName(paramString);
    this.tokens = new Hashtable();
    this.logContext = paramString;
    log.fine(CLASS_NAME, "<Init>", "308");
  }
  
  public void clear()
  {
    log.fine(CLASS_NAME, "clear", "305", new Object[] { new Integer(this.tokens.size()) });
    synchronized (this.tokens)
    {
      this.tokens.clear();
      return;
    }
  }
  
  public int count()
  {
    synchronized (this.tokens)
    {
      int i = this.tokens.size();
      return i;
    }
  }
  
  public MqttDeliveryToken[] getOutstandingDelTokens()
  {
    synchronized (this.tokens)
    {
      log.fine(CLASS_NAME, "getOutstandingDelTokens", "311");
      Vector localVector = new java/util/Vector;
      localVector.<init>();
      Object localObject1 = this.tokens.elements();
      while (((Enumeration)localObject1).hasMoreElements())
      {
        MqttToken localMqttToken = (MqttToken)((Enumeration)localObject1).nextElement();
        if ((localMqttToken != null) && ((localMqttToken instanceof MqttDeliveryToken)) && (!localMqttToken.internalTok.isNotified())) {
          localVector.addElement(localMqttToken);
        }
      }
      localObject1 = (MqttDeliveryToken[])localVector.toArray(new MqttDeliveryToken[localVector.size()]);
      return (MqttDeliveryToken[])localObject1;
    }
  }
  
  public Vector getOutstandingTokens()
  {
    synchronized (this.tokens)
    {
      log.fine(CLASS_NAME, "getOutstandingTokens", "312");
      Vector localVector = new java/util/Vector;
      localVector.<init>();
      Enumeration localEnumeration = this.tokens.elements();
      while (localEnumeration.hasMoreElements())
      {
        MqttToken localMqttToken = (MqttToken)localEnumeration.nextElement();
        if (localMqttToken != null) {
          localVector.addElement(localMqttToken);
        }
      }
      return localVector;
    }
  }
  
  public MqttToken getToken(String paramString)
  {
    return (MqttToken)this.tokens.get(paramString);
  }
  
  public MqttToken getToken(MqttWireMessage paramMqttWireMessage)
  {
    paramMqttWireMessage = paramMqttWireMessage.getKey();
    return (MqttToken)this.tokens.get(paramMqttWireMessage);
  }
  
  public void open()
  {
    synchronized (this.tokens)
    {
      log.fine(CLASS_NAME, "open", "310");
      this.closedResponse = null;
      return;
    }
  }
  
  protected void quiesce(MqttException paramMqttException)
  {
    synchronized (this.tokens)
    {
      log.fine(CLASS_NAME, "quiesce", "309", new Object[] { paramMqttException });
      this.closedResponse = paramMqttException;
      return;
    }
  }
  
  public MqttToken removeToken(String paramString)
  {
    log.fine(CLASS_NAME, "removeToken", "306", new Object[] { paramString });
    if (paramString != null) {
      return (MqttToken)this.tokens.remove(paramString);
    }
    return null;
  }
  
  public MqttToken removeToken(MqttWireMessage paramMqttWireMessage)
  {
    if (paramMqttWireMessage != null) {
      return removeToken(paramMqttWireMessage.getKey());
    }
    return null;
  }
  
  protected MqttDeliveryToken restoreToken(MqttPublish paramMqttPublish)
  {
    synchronized (this.tokens)
    {
      Object localObject = new java/lang/Integer;
      ((Integer)localObject).<init>(paramMqttPublish.getMessageId());
      String str = ((Integer)localObject).toString();
      if (this.tokens.containsKey(str))
      {
        localObject = (MqttDeliveryToken)this.tokens.get(str);
        log.fine(CLASS_NAME, "restoreToken", "302", new Object[] { str, paramMqttPublish, localObject });
        paramMqttPublish = (MqttPublish)localObject;
      }
      else
      {
        localObject = new org/eclipse/paho/client/mqttv3/MqttDeliveryToken;
        ((MqttDeliveryToken)localObject).<init>(this.logContext);
        ((MqttDeliveryToken)localObject).internalTok.setKey(str);
        this.tokens.put(str, localObject);
        log.fine(CLASS_NAME, "restoreToken", "303", new Object[] { str, paramMqttPublish, localObject });
        paramMqttPublish = (MqttPublish)localObject;
      }
      return paramMqttPublish;
    }
  }
  
  protected void saveToken(MqttToken paramMqttToken, String paramString)
  {
    synchronized (this.tokens)
    {
      log.fine(CLASS_NAME, "saveToken", "307", new Object[] { paramString, paramMqttToken.toString() });
      paramMqttToken.internalTok.setKey(paramString);
      this.tokens.put(paramString, paramMqttToken);
      return;
    }
  }
  
  protected void saveToken(MqttToken paramMqttToken, MqttWireMessage paramMqttWireMessage)
  {
    synchronized (this.tokens)
    {
      if (this.closedResponse == null)
      {
        String str = paramMqttWireMessage.getKey();
        log.fine(CLASS_NAME, "saveToken", "300", new Object[] { str, paramMqttWireMessage });
        saveToken(paramMqttToken, str);
        return;
      }
      throw this.closedResponse;
    }
  }
  
  public String toString()
  {
    String str = System.getProperty("line.separator", "\n");
    Object localObject1 = new StringBuffer();
    synchronized (this.tokens)
    {
      Enumeration localEnumeration = this.tokens.elements();
      while (localEnumeration.hasMoreElements())
      {
        MqttToken localMqttToken = (MqttToken)localEnumeration.nextElement();
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("{");
        localStringBuilder.append(localMqttToken.internalTok);
        localStringBuilder.append("}");
        localStringBuilder.append(str);
        ((StringBuffer)localObject1).append(localStringBuilder.toString());
      }
      localObject1 = ((StringBuffer)localObject1).toString();
      return (String)localObject1;
    }
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/CommsTokenStore.class
 *
 * Reversed by:           J
 */