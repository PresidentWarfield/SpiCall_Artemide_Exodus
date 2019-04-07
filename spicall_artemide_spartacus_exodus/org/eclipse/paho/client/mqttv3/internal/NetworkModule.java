package org.eclipse.paho.client.mqttv3.internal;

import java.io.InputStream;
import java.io.OutputStream;

public abstract interface NetworkModule
{
  public abstract InputStream getInputStream();
  
  public abstract OutputStream getOutputStream();
  
  public abstract String getServerURI();
  
  public abstract void start();
  
  public abstract void stop();
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/NetworkModule.class
 *
 * Reversed by:           J
 */