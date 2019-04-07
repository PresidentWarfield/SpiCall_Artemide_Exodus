package okhttp3.internal.tls;

import java.security.cert.X509Certificate;

public abstract interface TrustRootIndex
{
  public abstract X509Certificate findByIssuerAndSignature(X509Certificate paramX509Certificate);
}


/* Location:              ~/okhttp3/internal/tls/TrustRootIndex.class
 *
 * Reversed by:           J
 */