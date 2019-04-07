package io.fabric.sdk.android.services.network;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public final class e
{
  public static final SSLSocketFactory a(f paramf)
  {
    SSLContext localSSLContext = SSLContext.getInstance("TLS");
    localSSLContext.init(null, new TrustManager[] { new g(new h(paramf.getKeyStoreStream(), paramf.getKeyStorePassword()), paramf) }, null);
    return localSSLContext.getSocketFactory();
  }
}


/* Location:              ~/io/fabric/sdk/android/services/network/e.class
 *
 * Reversed by:           J
 */