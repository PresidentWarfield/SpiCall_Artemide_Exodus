package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.nio.ByteBuffer;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class WebSocketSecureNetworkModule
  extends SSLNetworkModule
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketSecureNetworkModule";
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private String host;
  private ByteArrayOutputStream outputStream = new ExtendedByteArrayOutputStream(this);
  private PipedInputStream pipedInputStream;
  private int port;
  ByteBuffer recievedPayload;
  private String uri;
  private WebSocketReceiver webSocketReceiver;
  
  public WebSocketSecureNetworkModule(SSLSocketFactory paramSSLSocketFactory, String paramString1, String paramString2, int paramInt, String paramString3)
  {
    super(paramSSLSocketFactory, paramString2, paramInt, paramString3);
    this.uri = paramString1;
    this.host = paramString2;
    this.port = paramInt;
    this.pipedInputStream = new PipedInputStream();
    log.setResourceName(paramString3);
  }
  
  public InputStream getInputStream()
  {
    return this.pipedInputStream;
  }
  
  public OutputStream getOutputStream()
  {
    return this.outputStream;
  }
  
  public String getServerURI()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("wss://");
    localStringBuilder.append(this.host);
    localStringBuilder.append(":");
    localStringBuilder.append(this.port);
    return localStringBuilder.toString();
  }
  
  InputStream getSocketInputStream()
  {
    return super.getInputStream();
  }
  
  OutputStream getSocketOutputStream()
  {
    return super.getOutputStream();
  }
  
  public void start()
  {
    super.start();
    new WebSocketHandshake(super.getInputStream(), super.getOutputStream(), this.uri, this.host, this.port).execute();
    this.webSocketReceiver = new WebSocketReceiver(getSocketInputStream(), this.pipedInputStream);
    this.webSocketReceiver.start("WssSocketReceiver");
  }
  
  public void stop()
  {
    Object localObject = new WebSocketFrame((byte)8, true, "1000".getBytes()).encodeFrame();
    getSocketOutputStream().write((byte[])localObject);
    getSocketOutputStream().flush();
    localObject = this.webSocketReceiver;
    if (localObject != null) {
      ((WebSocketReceiver)localObject).stop();
    }
    super.stop();
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/websocket/WebSocketSecureNetworkModule.class
 *
 * Reversed by:           J
 */