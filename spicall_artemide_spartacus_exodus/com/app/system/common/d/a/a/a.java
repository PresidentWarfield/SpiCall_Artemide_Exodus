package com.app.system.common.d.a.a;

import a.c;
import android.content.Context;
import android.content.SharedPreferences;
import com.android.system.CoreApp;
import com.security.b;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class a
{
  public static final MediaType a;
  public static final MediaType b = MediaType.parse("text/plain; charset=utf-8");
  private static final MediaType c = MediaType.parse("application/octet-stream");
  private static final MediaType d;
  private static final MediaType e = MediaType.parse("image/png");
  private static final MediaType f = MediaType.parse("application/octet-stream");
  private static OkHttpClient g = b();
  
  static
  {
    a = MediaType.parse("application/json;charset=utf-8");
    d = MediaType.parse("multipart/form-data");
  }
  
  private static KeyStore a(char[] paramArrayOfChar)
  {
    try
    {
      KeyStore localKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      localKeyStore.load(null, paramArrayOfChar);
      return localKeyStore;
    }
    catch (IOException paramArrayOfChar)
    {
      throw new AssertionError(paramArrayOfChar);
    }
  }
  
  private static X509TrustManager a(InputStream paramInputStream)
  {
    Object localObject1 = CertificateFactory.getInstance("X.509").generateCertificates(paramInputStream);
    if (!((Collection)localObject1).isEmpty())
    {
      Object localObject2 = "password".toCharArray();
      paramInputStream = a((char[])localObject2);
      Iterator localIterator = ((Collection)localObject1).iterator();
      for (int i = 0; localIterator.hasNext(); i++)
      {
        localObject1 = (Certificate)localIterator.next();
        paramInputStream.setCertificateEntry(Integer.toString(i), (Certificate)localObject1);
      }
      KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()).init(paramInputStream, (char[])localObject2);
      localObject2 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      ((TrustManagerFactory)localObject2).init(paramInputStream);
      localObject2 = ((TrustManagerFactory)localObject2).getTrustManagers();
      if ((localObject2.length == 1) && ((localObject2[0] instanceof X509TrustManager))) {
        return (X509TrustManager)localObject2[0];
      }
      paramInputStream = new StringBuilder();
      paramInputStream.append("Unexpected default trust managers:");
      paramInputStream.append(Arrays.toString((Object[])localObject2));
      throw new IllegalStateException(paramInputStream.toString());
    }
    throw new IllegalArgumentException("expected non-empty set of trusted certificates");
  }
  
  public static Response a(String paramString)
  {
    try
    {
      Object localObject1 = g;
      Object localObject2 = new okhttp3/Request$Builder;
      ((Request.Builder)localObject2).<init>();
      localObject1 = ((OkHttpClient)localObject1).newCall(((Request.Builder)localObject2).url(paramString).post(RequestBody.create(b, "")).build()).execute();
      if (((Response)localObject1).isSuccessful()) {
        return (Response)localObject1;
      }
      paramString = new java/io/IOException;
      localObject2 = new java/lang/StringBuilder;
      ((StringBuilder)localObject2).<init>();
      ((StringBuilder)localObject2).append("Unexpected code ");
      ((StringBuilder)localObject2).append(localObject1);
      paramString.<init>(((StringBuilder)localObject2).toString());
      throw paramString;
    }
    catch (IOException paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public static Response a(String paramString, File paramFile)
  {
    if (b.a(CoreApp.a(), "android.permission.READ_EXTERNAL_STORAGE", "sendFile@MyHttpClient.java")) {
      try
      {
        Object localObject = g;
        Request.Builder localBuilder = new okhttp3/Request$Builder;
        localBuilder.<init>();
        localBuilder = localBuilder.url(paramString);
        paramString = new okhttp3/MultipartBody$Builder;
        paramString.<init>();
        paramString = ((OkHttpClient)localObject).newCall(localBuilder.post(paramString.setType(MultipartBody.FORM).addFormDataPart("fileName", paramFile.getAbsolutePath(), RequestBody.create(f, paramFile)).build()).build()).execute();
        if (paramString.isSuccessful()) {
          return paramString;
        }
        localObject = new java/io/IOException;
        paramFile = new java/lang/StringBuilder;
        paramFile.<init>();
        paramFile.append("Unexpected code ");
        paramFile.append(paramString);
        ((IOException)localObject).<init>(paramFile.toString());
        throw ((Throwable)localObject);
      }
      catch (IOException paramString)
      {
        paramString.printStackTrace();
        return null;
      }
    }
    return null;
  }
  
  public static Response a(String paramString1, String paramString2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("URL: ");
    ((StringBuilder)localObject).append(paramString1);
    com.security.d.a.d("MyHttpClient", ((StringBuilder)localObject).toString(), new Object[0]);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("json: ");
    ((StringBuilder)localObject).append(paramString2);
    com.security.d.a.d("MyHttpClient", ((StringBuilder)localObject).toString(), new Object[0]);
    try
    {
      OkHttpClient localOkHttpClient = g;
      localObject = new okhttp3/Request$Builder;
      ((Request.Builder)localObject).<init>();
      paramString2 = localOkHttpClient.newCall(((Request.Builder)localObject).url(paramString1).post(RequestBody.create(a, paramString2)).build()).execute();
      if (paramString2.isSuccessful()) {
        return paramString2;
      }
      paramString1 = new java/io/IOException;
      localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("Unexpected code ");
      ((StringBuilder)localObject).append(paramString2);
      paramString1.<init>(((StringBuilder)localObject).toString());
      throw paramString1;
    }
    catch (IOException paramString1)
    {
      paramString1.printStackTrace();
    }
    return null;
  }
  
  public static Response a(String paramString1, String paramString2, byte[] paramArrayOfByte)
  {
    try
    {
      OkHttpClient localOkHttpClient = g;
      paramString2 = new okhttp3/Request$Builder;
      paramString2.<init>();
      paramString2 = localOkHttpClient.newCall(paramString2.url(paramString1).post(RequestBody.create(e, paramArrayOfByte)).build()).execute();
      if (paramString2.isSuccessful()) {
        return paramString2;
      }
      paramArrayOfByte = new java/io/IOException;
      paramString1 = new java/lang/StringBuilder;
      paramString1.<init>();
      paramString1.append("Unexpected code ");
      paramString1.append(paramString2);
      paramArrayOfByte.<init>(paramString1.toString());
      throw paramArrayOfByte;
    }
    catch (IOException paramString1)
    {
      paramString1.printStackTrace();
    }
    return null;
  }
  
  public static void a()
  {
    g = b();
  }
  
  private static OkHttpClient b()
  {
    OkHttpClient localOkHttpClient;
    if (CoreApp.a().getSharedPreferences("pref", 0).getBoolean("DEST_SERVER_SSL_ACTIVE", false)) {
      localOkHttpClient = c();
    } else {
      localOkHttpClient = new OkHttpClient();
    }
    return localOkHttpClient;
  }
  
  public static Response b(String paramString, File paramFile)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("url: ");
    ((StringBuilder)localObject).append(paramString);
    com.security.d.a.d("MyHttpClient", ((StringBuilder)localObject).toString(), new Object[0]);
    try
    {
      localObject = g;
      Request.Builder localBuilder = new okhttp3/Request$Builder;
      localBuilder.<init>();
      localObject = ((OkHttpClient)localObject).newCall(localBuilder.url(paramString).post(RequestBody.create(c, paramFile)).build()).execute();
      if (((Response)localObject).isSuccessful()) {
        return (Response)localObject;
      }
      paramFile = new java/io/IOException;
      paramString = new java/lang/StringBuilder;
      paramString.<init>();
      paramString.append("Unexpected code ");
      paramString.append(localObject);
      paramFile.<init>(paramString.toString());
      throw paramFile;
    }
    catch (IOException paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  private static OkHttpClient c()
  {
    try
    {
      X509TrustManager localX509TrustManager = a(d());
      Object localObject = SSLContext.getInstance("TLS");
      ((SSLContext)localObject).init(null, new TrustManager[] { localX509TrustManager }, null);
      localObject = ((SSLContext)localObject).getSocketFactory();
      new OkHttpClient.Builder().sslSocketFactory((SSLSocketFactory)localObject, localX509TrustManager).hostnameVerifier(new HostnameVerifier()
      {
        public boolean verify(String paramAnonymousString, SSLSession paramAnonymousSSLSession)
        {
          String str1 = "";
          try
          {
            localObject = paramAnonymousSSLSession.getPeerPrincipal();
            if (localObject != null) {
              str1 = ((Principal)localObject).getName();
            }
          }
          catch (SSLPeerUnverifiedException localSSLPeerUnverifiedException)
          {
            str2 = "Ex.Unverified!";
          }
          com.security.d.a.e("MyHttpClient", String.format("hostname = %s, session: host: %s:%d, principal: %s", new Object[] { paramAnonymousString, paramAnonymousSSLSession.getPeerHost(), Integer.valueOf(paramAnonymousSSLSession.getPeerPort()), str2 }), new Object[0]);
          paramAnonymousSSLSession = CoreApp.a().getSharedPreferences("pref", 0);
          String str2 = paramAnonymousSSLSession.getString("DEST_SERVER_IP", "5.56.12.200");
          int i = paramAnonymousSSLSession.getInt("DEST_SERVER_PORT", 2224);
          if (paramAnonymousSSLSession.getBoolean("DEST_SERVER_SSL_ACTIVE", false)) {
            paramAnonymousSSLSession = "https://";
          } else {
            paramAnonymousSSLSession = "http://";
          }
          Object localObject = new StringBuilder();
          ((StringBuilder)localObject).append(paramAnonymousSSLSession);
          ((StringBuilder)localObject).append(str2);
          ((StringBuilder)localObject).append(":");
          ((StringBuilder)localObject).append(i);
          return paramAnonymousString.equals(((StringBuilder)localObject).toString());
        }
      }).build();
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new RuntimeException(localGeneralSecurityException);
    }
  }
  
  private static InputStream d()
  {
    return new c().a("-----BEGIN CERTIFICATE-----\nMIIDlDCCAnygAwIBAgIDSwIzMA0GCSqGSIb3DQEBCwUAMHsxCzAJBgNVBAYTAklU\nMRAwDgYDVQQIEwdVbmtub3duMQ0wCwYDVQQHEwRSb21hMREwDwYDVQQKEwhDb2xp\nc2V1bTEbMBkGA1UECxMSTG90dGEgR3JlY28tUm9tYW5hMRswGQYDVQQDExJBcnRl\nbWlkZS9TcGFydGFjdXMwHhcNMTkwMjE4MTUxNTQ4WhcNMjkwMjE1MTUxNTQ4WjB7\nMQswCQYDVQQGEwJJVDEQMA4GA1UECBMHVW5rbm93bjENMAsGA1UEBxMEUm9tYTER\nMA8GA1UEChMIQ29saXNldW0xGzAZBgNVBAsTEkxvdHRhIEdyZWNvLVJvbWFuYTEb\nMBkGA1UEAxMSQXJ0ZW1pZGUvU3BhcnRhY3VzMIIBIjANBgkqhkiG9w0BAQEFAAOC\nAQ8AMIIBCgKCAQEAgGTMsBKmIwGHNrwK237qrO3erpt6k1Wh2jh0jgBJ+pT+1AME\nZQSRWT8d/EOVR3zeBu7Fig6tkxLSj882yYz9IiFquIh8wNUoylmp8bLaPtYuuqEx\nenT5d3wXrYOLt7L6vCwH/dUh05IWItHxtssGHV0mgGjgwJ6yHUCSrvKYyoqffC6v\nelqjnsUieCHffTYXwVV8whneGhfEVoTyTDrsEjkKTbJMIK+6C7ziMS+rDjctEm73\njDoJPgGB0ayxJZgoo3aqHno+BfENDKso6UATxkRw990zZ6jArEj0FqNLF1V0q352\nlQBXA95Ngnq5TMM8Xd3B8AjQ03uRVAEftpc0KQIDAQABoyEwHzAdBgNVHQ4EFgQU\nIFRC0rUWm2MQkenXgT2TbJvQ92IwDQYJKoZIhvcNAQELBQADggEBAF8c/Py+PuXY\nEaEoSiYiCotcWVu1EhmfTxBIM64uCS3Q5a0ivPOpHO5tb+P238WQ4zrvHUaOg5Dv\nBXZ+oKFfgrG7uAyC7A+QosZbELoTmss8vSrzw8+swnKTMO60+fd7unN3uR7eSrS8\n7GQ0wHl0absNEpQcu8dshFalruxr61bzHm31QtLWCnrqpWVTTybpXhPKfkaFVLDn\nJN6PKoA74RInkenbIihJk3U3b8wA3x/vQ3AH0eW9f9A4RFmWnEolBc22zVugtwQB\nZnmBPfGSMPwTcUgcjI4mK3mbfKh4IOJyQ7LGWa3VbBe+5j/aOf8GuT/Az0G3s2q/\n************\n-----END CERTIFICATE-----\n").f(); /* TRUNCATED FOR PRIVACY */
  }
}


/* Location:              ~/com/app/system/common/d/a/a/a.class
 *
 * Reversed by:           J
 */