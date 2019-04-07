package org.eclipse.paho.client.mqttv3.internal;

import java.net.Socket;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class SSLNetworkModule
  extends TCPNetworkModule
{
  private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule";
  private static final Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
  private String[] enabledCiphers;
  private int handshakeTimeoutSecs;
  private String host;
  private HostnameVerifier hostnameVerifier;
  private int port;
  
  public SSLNetworkModule(SSLSocketFactory paramSSLSocketFactory, String paramString1, int paramInt, String paramString2)
  {
    super(paramSSLSocketFactory, paramString1, paramInt, paramString2);
    this.host = paramString1;
    this.port = paramInt;
    log.setResourceName(paramString2);
  }
  
  public String[] getEnabledCiphers()
  {
    return this.enabledCiphers;
  }
  
  public HostnameVerifier getSSLHostnameVerifier()
  {
    return this.hostnameVerifier;
  }
  
  public String getServerURI()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ssl://");
    localStringBuilder.append(this.host);
    localStringBuilder.append(":");
    localStringBuilder.append(this.port);
    return localStringBuilder.toString();
  }
  
  public void setEnabledCiphers(String[] paramArrayOfString)
  {
    this.enabledCiphers = paramArrayOfString;
    if ((this.socket != null) && (paramArrayOfString != null))
    {
      if (log.isLoggable(5))
      {
        Object localObject1 = "";
        for (int i = 0; i < paramArrayOfString.length; i++)
        {
          Object localObject2 = localObject1;
          if (i > 0)
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append((String)localObject1);
            ((StringBuilder)localObject2).append(",");
            localObject2 = ((StringBuilder)localObject2).toString();
          }
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append((String)localObject2);
          ((StringBuilder)localObject1).append(paramArrayOfString[i]);
          localObject1 = ((StringBuilder)localObject1).toString();
        }
        log.fine(CLASS_NAME, "setEnabledCiphers", "260", new Object[] { localObject1 });
      }
      ((SSLSocket)this.socket).setEnabledCipherSuites(paramArrayOfString);
    }
  }
  
  public void setSSLHostnameVerifier(HostnameVerifier paramHostnameVerifier)
  {
    this.hostnameVerifier = paramHostnameVerifier;
  }
  
  public void setSSLhandshakeTimeout(int paramInt)
  {
    super.setConnectTimeout(paramInt);
    this.handshakeTimeoutSecs = paramInt;
  }
  
  public void start()
  {
    super.start();
    setEnabledCiphers(this.enabledCiphers);
    int i = this.socket.getSoTimeout();
    this.socket.setSoTimeout(this.handshakeTimeoutSecs * 1000);
    ((SSLSocket)this.socket).startHandshake();
    if (this.hostnameVerifier != null)
    {
      SSLSession localSSLSession = ((SSLSocket)this.socket).getSession();
      this.hostnameVerifier.verify(this.host, localSSLSession);
    }
    this.socket.setSoTimeout(i);
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/SSLNetworkModule.class
 *
 * Reversed by:           J
 */