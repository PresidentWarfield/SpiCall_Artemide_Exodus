package org.eclipse.paho.android.service;

public abstract interface MqttTraceHandler
{
  public abstract void traceDebug(String paramString1, String paramString2);
  
  public abstract void traceError(String paramString1, String paramString2);
  
  public abstract void traceException(String paramString1, String paramString2, Exception paramException);
}


/* Location:              ~/org/eclipse/paho/android/service/MqttTraceHandler.class
 *
 * Reversed by:           J
 */