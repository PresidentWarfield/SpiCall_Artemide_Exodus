package okhttp3.internal.connection;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.UnknownServiceException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.SSLSocket;
import okhttp3.ConnectionSpec;
import okhttp3.internal.Internal;

public final class ConnectionSpecSelector
{
  private final List<ConnectionSpec> connectionSpecs;
  private boolean isFallback;
  private boolean isFallbackPossible;
  private int nextModeIndex = 0;
  
  public ConnectionSpecSelector(List<ConnectionSpec> paramList)
  {
    this.connectionSpecs = paramList;
  }
  
  private boolean isFallbackPossible(SSLSocket paramSSLSocket)
  {
    for (int i = this.nextModeIndex; i < this.connectionSpecs.size(); i++) {
      if (((ConnectionSpec)this.connectionSpecs.get(i)).isCompatible(paramSSLSocket)) {
        return true;
      }
    }
    return false;
  }
  
  public ConnectionSpec configureSecureSocket(SSLSocket paramSSLSocket)
  {
    int i = this.nextModeIndex;
    int j = this.connectionSpecs.size();
    while (i < j)
    {
      localObject = (ConnectionSpec)this.connectionSpecs.get(i);
      if (((ConnectionSpec)localObject).isCompatible(paramSSLSocket))
      {
        this.nextModeIndex = (i + 1);
        break label63;
      }
      i++;
    }
    Object localObject = null;
    label63:
    if (localObject != null)
    {
      this.isFallbackPossible = isFallbackPossible(paramSSLSocket);
      Internal.instance.apply((ConnectionSpec)localObject, paramSSLSocket, this.isFallback);
      return (ConnectionSpec)localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Unable to find acceptable protocols. isFallback=");
    ((StringBuilder)localObject).append(this.isFallback);
    ((StringBuilder)localObject).append(", modes=");
    ((StringBuilder)localObject).append(this.connectionSpecs);
    ((StringBuilder)localObject).append(", supported protocols=");
    ((StringBuilder)localObject).append(Arrays.toString(paramSSLSocket.getEnabledProtocols()));
    throw new UnknownServiceException(((StringBuilder)localObject).toString());
  }
  
  public boolean connectionFailed(IOException paramIOException)
  {
    boolean bool1 = true;
    this.isFallback = true;
    if (!this.isFallbackPossible) {
      return false;
    }
    if ((paramIOException instanceof ProtocolException)) {
      return false;
    }
    if ((paramIOException instanceof InterruptedIOException)) {
      return false;
    }
    boolean bool2 = paramIOException instanceof SSLHandshakeException;
    if ((bool2) && ((paramIOException.getCause() instanceof CertificateException))) {
      return false;
    }
    if ((paramIOException instanceof SSLPeerUnverifiedException)) {
      return false;
    }
    boolean bool3 = bool1;
    if (!bool2)
    {
      bool3 = bool1;
      if (!(paramIOException instanceof SSLProtocolException)) {
        if ((paramIOException instanceof SSLException)) {
          bool3 = bool1;
        } else {
          bool3 = false;
        }
      }
    }
    return bool3;
  }
}


/* Location:              ~/okhttp3/internal/connection/ConnectionSpecSelector.class
 *
 * Reversed by:           J
 */