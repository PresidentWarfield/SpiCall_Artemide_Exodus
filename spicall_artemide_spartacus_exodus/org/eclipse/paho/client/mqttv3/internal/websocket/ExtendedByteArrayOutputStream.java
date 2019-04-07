package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

class ExtendedByteArrayOutputStream
  extends ByteArrayOutputStream
{
  final WebSocketNetworkModule webSocketNetworkModule;
  final WebSocketSecureNetworkModule webSocketSecureNetworkModule;
  
  ExtendedByteArrayOutputStream(WebSocketNetworkModule paramWebSocketNetworkModule)
  {
    this.webSocketNetworkModule = paramWebSocketNetworkModule;
    this.webSocketSecureNetworkModule = null;
  }
  
  ExtendedByteArrayOutputStream(WebSocketSecureNetworkModule paramWebSocketSecureNetworkModule)
  {
    this.webSocketNetworkModule = null;
    this.webSocketSecureNetworkModule = paramWebSocketSecureNetworkModule;
  }
  
  public void flush()
  {
    try
    {
      Object localObject1 = ByteBuffer.wrap(toByteArray());
      reset();
      localObject1 = new WebSocketFrame((byte)2, true, ((ByteBuffer)localObject1).array()).encodeFrame();
      getSocketOutputStream().write((byte[])localObject1);
      getSocketOutputStream().flush();
      return;
    }
    finally {}
  }
  
  OutputStream getSocketOutputStream()
  {
    Object localObject = this.webSocketNetworkModule;
    if (localObject != null) {
      return ((WebSocketNetworkModule)localObject).getSocketOutputStream();
    }
    localObject = this.webSocketSecureNetworkModule;
    if (localObject != null) {
      return ((WebSocketSecureNetworkModule)localObject).getSocketOutputStream();
    }
    return null;
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/websocket/ExtendedByteArrayOutputStream.class
 *
 * Reversed by:           J
 */