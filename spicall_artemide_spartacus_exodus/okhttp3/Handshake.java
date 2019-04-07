package okhttp3;

import java.io.IOException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;
import okhttp3.internal.Util;

public final class Handshake
{
  private final CipherSuite cipherSuite;
  private final List<Certificate> localCertificates;
  private final List<Certificate> peerCertificates;
  private final TlsVersion tlsVersion;
  
  private Handshake(TlsVersion paramTlsVersion, CipherSuite paramCipherSuite, List<Certificate> paramList1, List<Certificate> paramList2)
  {
    this.tlsVersion = paramTlsVersion;
    this.cipherSuite = paramCipherSuite;
    this.peerCertificates = paramList1;
    this.localCertificates = paramList2;
  }
  
  public static Handshake get(SSLSession paramSSLSession)
  {
    Object localObject1 = paramSSLSession.getCipherSuite();
    if (localObject1 != null)
    {
      if (!"SSL_NULL_WITH_NULL_NULL".equals(localObject1))
      {
        CipherSuite localCipherSuite = CipherSuite.forJavaName((String)localObject1);
        localObject1 = paramSSLSession.getProtocol();
        if (localObject1 != null)
        {
          if (!"NONE".equals(localObject1))
          {
            TlsVersion localTlsVersion = TlsVersion.forJavaName((String)localObject1);
            Object localObject2;
            try
            {
              localObject1 = paramSSLSession.getPeerCertificates();
            }
            catch (SSLPeerUnverifiedException localSSLPeerUnverifiedException)
            {
              localObject2 = null;
            }
            if (localObject2 != null) {
              localObject2 = Util.immutableList((Object[])localObject2);
            } else {
              localObject2 = Collections.emptyList();
            }
            paramSSLSession = paramSSLSession.getLocalCertificates();
            if (paramSSLSession != null) {
              paramSSLSession = Util.immutableList(paramSSLSession);
            } else {
              paramSSLSession = Collections.emptyList();
            }
            return new Handshake(localTlsVersion, localCipherSuite, (List)localObject2, paramSSLSession);
          }
          throw new IOException("tlsVersion == NONE");
        }
        throw new IllegalStateException("tlsVersion == null");
      }
      throw new IOException("cipherSuite == SSL_NULL_WITH_NULL_NULL");
    }
    throw new IllegalStateException("cipherSuite == null");
  }
  
  public static Handshake get(TlsVersion paramTlsVersion, CipherSuite paramCipherSuite, List<Certificate> paramList1, List<Certificate> paramList2)
  {
    if (paramTlsVersion != null)
    {
      if (paramCipherSuite != null) {
        return new Handshake(paramTlsVersion, paramCipherSuite, Util.immutableList(paramList1), Util.immutableList(paramList2));
      }
      throw new NullPointerException("cipherSuite == null");
    }
    throw new NullPointerException("tlsVersion == null");
  }
  
  public CipherSuite cipherSuite()
  {
    return this.cipherSuite;
  }
  
  public boolean equals(@Nullable Object paramObject)
  {
    boolean bool1 = paramObject instanceof Handshake;
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    paramObject = (Handshake)paramObject;
    bool1 = bool2;
    if (this.tlsVersion.equals(((Handshake)paramObject).tlsVersion))
    {
      bool1 = bool2;
      if (this.cipherSuite.equals(((Handshake)paramObject).cipherSuite))
      {
        bool1 = bool2;
        if (this.peerCertificates.equals(((Handshake)paramObject).peerCertificates))
        {
          bool1 = bool2;
          if (this.localCertificates.equals(((Handshake)paramObject).localCertificates)) {
            bool1 = true;
          }
        }
      }
    }
    return bool1;
  }
  
  public int hashCode()
  {
    return (((527 + this.tlsVersion.hashCode()) * 31 + this.cipherSuite.hashCode()) * 31 + this.peerCertificates.hashCode()) * 31 + this.localCertificates.hashCode();
  }
  
  public List<Certificate> localCertificates()
  {
    return this.localCertificates;
  }
  
  @Nullable
  public Principal localPrincipal()
  {
    X500Principal localX500Principal;
    if (!this.localCertificates.isEmpty()) {
      localX500Principal = ((X509Certificate)this.localCertificates.get(0)).getSubjectX500Principal();
    } else {
      localX500Principal = null;
    }
    return localX500Principal;
  }
  
  public List<Certificate> peerCertificates()
  {
    return this.peerCertificates;
  }
  
  @Nullable
  public Principal peerPrincipal()
  {
    X500Principal localX500Principal;
    if (!this.peerCertificates.isEmpty()) {
      localX500Principal = ((X509Certificate)this.peerCertificates.get(0)).getSubjectX500Principal();
    } else {
      localX500Principal = null;
    }
    return localX500Principal;
  }
  
  public TlsVersion tlsVersion()
  {
    return this.tlsVersion;
  }
}


/* Location:              ~/okhttp3/Handshake.class
 *
 * Reversed by:           J
 */