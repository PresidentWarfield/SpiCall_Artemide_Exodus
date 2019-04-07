package org.eclipse.paho.client.mqttv3.internal;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class TCPNetworkModule
  implements NetworkModule
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule";
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private int conTimeout;
  private SocketFactory factory;
  private String host;
  private int port;
  protected Socket socket;
  
  public TCPNetworkModule(SocketFactory paramSocketFactory, String paramString1, int paramInt, String paramString2)
  {
    log.setResourceName(paramString2);
    this.factory = paramSocketFactory;
    this.host = paramString1;
    this.port = paramInt;
  }
  
  public InputStream getInputStream()
  {
    return this.socket.getInputStream();
  }
  
  public OutputStream getOutputStream()
  {
    return this.socket.getOutputStream();
  }
  
  public String getServerURI()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("tcp://");
    localStringBuilder.append(this.host);
    localStringBuilder.append(":");
    localStringBuilder.append(this.port);
    return localStringBuilder.toString();
  }
  
  public void setConnectTimeout(int paramInt)
  {
    this.conTimeout = paramInt;
  }
  
  public void start()
  {
    try
    {
      Logger localLogger = log;
      Object localObject1 = CLASS_NAME;
      Object localObject2 = this.host;
      Integer localInteger = new java/lang/Integer;
      localInteger.<init>(this.port);
      Long localLong = new java/lang/Long;
      localLong.<init>(this.conTimeout * 1000);
      localLogger.fine((String)localObject1, "start", "252", new Object[] { localObject2, localInteger, localLong });
      localObject2 = new java/net/InetSocketAddress;
      ((InetSocketAddress)localObject2).<init>(this.host, this.port);
      if ((this.factory instanceof SSLSocketFactory))
      {
        localObject1 = new java/net/Socket;
        ((Socket)localObject1).<init>();
        ((Socket)localObject1).connect((SocketAddress)localObject2, this.conTimeout * 1000);
        this.socket = ((SSLSocketFactory)this.factory).createSocket((Socket)localObject1, this.host, this.port, true);
      }
      else
      {
        this.socket = this.factory.createSocket();
        this.socket.connect((SocketAddress)localObject2, this.conTimeout * 1000);
      }
      return;
    }
    catch (ConnectException localConnectException)
    {
      log.fine(CLASS_NAME, "start", "250", null, localConnectException);
      throw new MqttException(32103, localConnectException);
    }
  }
  
  public void stop()
  {
    Socket localSocket = this.socket;
    if (localSocket != null)
    {
      localSocket.shutdownInput();
      this.socket.close();
    }
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/TCPNetworkModule.class
 *
 * Reversed by:           J
 */