package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.BufferedMessage;

public abstract interface IDisconnectedBufferCallback
{
  public abstract void publishBufferedMessage(BufferedMessage paramBufferedMessage);
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/IDisconnectedBufferCallback.class
 *
 * Reversed by:           J
 */