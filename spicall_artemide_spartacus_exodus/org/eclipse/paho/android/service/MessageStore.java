package org.eclipse.paho.android.service;

import java.util.Iterator;
import org.eclipse.paho.client.mqttv3.MqttMessage;

abstract interface MessageStore
{
  public abstract void clearArrivedMessages(String paramString);
  
  public abstract void close();
  
  public abstract boolean discardArrived(String paramString1, String paramString2);
  
  public abstract Iterator<StoredMessage> getAllArrivedMessages(String paramString);
  
  public abstract String storeArrived(String paramString1, String paramString2, MqttMessage paramMqttMessage);
  
  public static abstract interface StoredMessage
  {
    public abstract String getClientHandle();
    
    public abstract MqttMessage getMessage();
    
    public abstract String getMessageId();
    
    public abstract String getTopic();
  }
}


/* Location:              ~/org/eclipse/paho/android/service/MessageStore.class
 *
 * Reversed by:           J
 */