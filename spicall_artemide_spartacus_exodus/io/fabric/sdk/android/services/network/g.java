package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

class g
  implements X509TrustManager
{
  private static final X509Certificate[] a = new X509Certificate[0];
  private final TrustManager[] b = a(paramh);
  private final h c;
  private final long d;
  private final List<byte[]> e = new LinkedList();
  private final Set<X509Certificate> f = Collections.synchronizedSet(new HashSet());
  
  public g(h paramh, f paramf)
  {
    this.c = paramh;
    this.d = paramf.getPinCreationTimeInMillis();
    for (paramf : paramf.getPins()) {
      this.e.add(a(paramf));
    }
  }
  
  private void a(X509Certificate[] paramArrayOfX509Certificate)
  {
    if ((this.d != -1L) && (System.currentTimeMillis() - this.d > 15552000000L))
    {
      paramArrayOfX509Certificate = c.g();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Certificate pins are stale, (");
      localStringBuilder.append(System.currentTimeMillis() - this.d);
      localStringBuilder.append(" millis vs ");
      localStringBuilder.append(15552000000L);
      localStringBuilder.append(" millis) falling back to system trust.");
      paramArrayOfX509Certificate.d("Fabric", localStringBuilder.toString());
      return;
    }
    paramArrayOfX509Certificate = a.a(paramArrayOfX509Certificate, this.c);
    int i = paramArrayOfX509Certificate.length;
    for (int j = 0; j < i; j++) {
      if (a(paramArrayOfX509Certificate[j])) {
        return;
      }
    }
    throw new CertificateException("No valid pins found in chain!");
  }
  
  private void a(X509Certificate[] paramArrayOfX509Certificate, String paramString)
  {
    TrustManager[] arrayOfTrustManager = this.b;
    int i = arrayOfTrustManager.length;
    for (int j = 0; j < i; j++) {
      ((X509TrustManager)arrayOfTrustManager[j]).checkServerTrusted(paramArrayOfX509Certificate, paramString);
    }
  }
  
  private boolean a(X509Certificate paramX509Certificate)
  {
    try
    {
      byte[] arrayOfByte = MessageDigest.getInstance("SHA1").digest(paramX509Certificate.getPublicKey().getEncoded());
      paramX509Certificate = this.e.iterator();
      while (paramX509Certificate.hasNext())
      {
        boolean bool = Arrays.equals((byte[])paramX509Certificate.next(), arrayOfByte);
        if (bool) {
          return true;
        }
      }
      return false;
    }
    catch (NoSuchAlgorithmException paramX509Certificate)
    {
      throw new CertificateException(paramX509Certificate);
    }
  }
  
  private byte[] a(String paramString)
  {
    int i = paramString.length();
    byte[] arrayOfByte = new byte[i / 2];
    for (int j = 0; j < i; j += 2) {
      arrayOfByte[(j / 2)] = ((byte)(byte)((Character.digit(paramString.charAt(j), 16) << 4) + Character.digit(paramString.charAt(j + 1), 16)));
    }
    return arrayOfByte;
  }
  
  private TrustManager[] a(h paramh)
  {
    try
    {
      TrustManagerFactory localTrustManagerFactory = TrustManagerFactory.getInstance("X509");
      localTrustManagerFactory.init(paramh.a);
      paramh = localTrustManagerFactory.getTrustManagers();
      return paramh;
    }
    catch (KeyStoreException paramh)
    {
      throw new AssertionError(paramh);
    }
    catch (NoSuchAlgorithmException paramh)
    {
      throw new AssertionError(paramh);
    }
  }
  
  public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
  {
    throw new CertificateException("Client certificates not supported!");
  }
  
  public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
  {
    if (this.f.contains(paramArrayOfX509Certificate[0])) {
      return;
    }
    a(paramArrayOfX509Certificate, paramString);
    a(paramArrayOfX509Certificate);
    this.f.add(paramArrayOfX509Certificate[0]);
  }
  
  public X509Certificate[] getAcceptedIssuers()
  {
    return a;
  }
}


/* Location:              ~/io/fabric/sdk/android/services/network/g.class
 *
 * Reversed by:           J
 */