package okhttp3.internal.platform;

import android.os.Build.VERSION;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Protocol;
import okhttp3.internal.Util;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;

class AndroidPlatform
  extends Platform
{
  private static final int MAX_LOG_LENGTH = 4000;
  private final CloseGuard closeGuard = CloseGuard.get();
  private final OptionalMethod<Socket> getAlpnSelectedProtocol;
  private final OptionalMethod<Socket> setAlpnProtocols;
  private final OptionalMethod<Socket> setHostname;
  private final OptionalMethod<Socket> setUseSessionTickets;
  private final Class<?> sslParametersClass;
  
  AndroidPlatform(Class<?> paramClass, OptionalMethod<Socket> paramOptionalMethod1, OptionalMethod<Socket> paramOptionalMethod2, OptionalMethod<Socket> paramOptionalMethod3, OptionalMethod<Socket> paramOptionalMethod4)
  {
    this.sslParametersClass = paramClass;
    this.setUseSessionTickets = paramOptionalMethod1;
    this.setHostname = paramOptionalMethod2;
    this.getAlpnSelectedProtocol = paramOptionalMethod3;
    this.setAlpnProtocols = paramOptionalMethod4;
  }
  
  private boolean api23IsCleartextTrafficPermitted(String paramString, Class<?> paramClass, Object paramObject)
  {
    try
    {
      boolean bool = ((Boolean)paramClass.getMethod("isCleartextTrafficPermitted", new Class[0]).invoke(paramObject, new Object[0])).booleanValue();
      return bool;
    }
    catch (NoSuchMethodException paramClass) {}
    return super.isCleartextTrafficPermitted(paramString);
  }
  
  private boolean api24IsCleartextTrafficPermitted(String paramString, Class<?> paramClass, Object paramObject)
  {
    try
    {
      boolean bool = ((Boolean)paramClass.getMethod("isCleartextTrafficPermitted", new Class[] { String.class }).invoke(paramObject, new Object[] { paramString })).booleanValue();
      return bool;
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}
    return api23IsCleartextTrafficPermitted(paramString, paramClass, paramObject);
  }
  
  public static Platform buildIfSupported()
  {
    try
    {
      Class localClass = Class.forName("com.android.org.conscrypt.SSLParametersImpl");
    }
    catch (ClassNotFoundException localClassNotFoundException1) {}
    try
    {
      Object localObject = Class.forName("org.apache.harmony.xnet.provider.jsse.SSLParametersImpl");
      OptionalMethod localOptionalMethod1 = new okhttp3/internal/platform/OptionalMethod;
      localOptionalMethod1.<init>(null, "setUseSessionTickets", new Class[] { Boolean.TYPE });
      OptionalMethod localOptionalMethod2 = new okhttp3/internal/platform/OptionalMethod;
      localOptionalMethod2.<init>(null, "setHostname", new Class[] { String.class });
      OptionalMethod localOptionalMethod3;
      OptionalMethod localOptionalMethod4;
      if (supportsAlpn())
      {
        localOptionalMethod3 = new okhttp3/internal/platform/OptionalMethod;
        localOptionalMethod3.<init>(byte[].class, "getAlpnSelectedProtocol", new Class[0]);
        localOptionalMethod4 = new okhttp3/internal/platform/OptionalMethod;
        localOptionalMethod4.<init>(null, "setAlpnProtocols", new Class[] { byte[].class });
      }
      else
      {
        localOptionalMethod3 = null;
        localOptionalMethod4 = localOptionalMethod3;
      }
      localObject = new AndroidPlatform((Class)localObject, localOptionalMethod1, localOptionalMethod2, localOptionalMethod3, localOptionalMethod4);
      return (Platform)localObject;
    }
    catch (ClassNotFoundException localClassNotFoundException2) {}
    return null;
  }
  
  private static boolean supportsAlpn()
  {
    if (Security.getProvider("GMSCore_OpenSSL") != null) {
      return true;
    }
    try
    {
      Class.forName("android.net.Network");
      return true;
    }
    catch (ClassNotFoundException localClassNotFoundException) {}
    return false;
  }
  
  public CertificateChainCleaner buildCertificateChainCleaner(X509TrustManager paramX509TrustManager)
  {
    try
    {
      Object localObject = Class.forName("android.net.http.X509TrustManagerExtensions");
      localObject = new AndroidCertificateChainCleaner(((Class)localObject).getConstructor(new Class[] { X509TrustManager.class }).newInstance(new Object[] { paramX509TrustManager }), ((Class)localObject).getMethod("checkServerTrusted", new Class[] { X509Certificate[].class, String.class, String.class }));
      return (CertificateChainCleaner)localObject;
    }
    catch (Exception localException) {}
    return super.buildCertificateChainCleaner(paramX509TrustManager);
  }
  
  public TrustRootIndex buildTrustRootIndex(X509TrustManager paramX509TrustManager)
  {
    try
    {
      Object localObject = paramX509TrustManager.getClass().getDeclaredMethod("findTrustAnchorByIssuerAndSignature", new Class[] { X509Certificate.class });
      ((Method)localObject).setAccessible(true);
      localObject = new AndroidTrustRootIndex(paramX509TrustManager, (Method)localObject);
      return (TrustRootIndex)localObject;
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}
    return super.buildTrustRootIndex(paramX509TrustManager);
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<Protocol> paramList)
  {
    if (paramString != null)
    {
      this.setUseSessionTickets.invokeOptionalWithoutCheckedException(paramSSLSocket, new Object[] { Boolean.valueOf(true) });
      this.setHostname.invokeOptionalWithoutCheckedException(paramSSLSocket, new Object[] { paramString });
    }
    paramString = this.setAlpnProtocols;
    if ((paramString != null) && (paramString.isSupported(paramSSLSocket)))
    {
      paramString = concatLengthPrefixed(paramList);
      this.setAlpnProtocols.invokeWithoutCheckedException(paramSSLSocket, new Object[] { paramString });
    }
  }
  
  public void connectSocket(Socket paramSocket, InetSocketAddress paramInetSocketAddress, int paramInt)
  {
    try
    {
      paramSocket.connect(paramInetSocketAddress, paramInt);
      return;
    }
    catch (ClassCastException paramSocket)
    {
      if (Build.VERSION.SDK_INT == 26)
      {
        paramInetSocketAddress = new IOException("Exception in connect");
        paramInetSocketAddress.initCause(paramSocket);
        throw paramInetSocketAddress;
      }
      throw paramSocket;
    }
    catch (SecurityException paramSocket)
    {
      paramInetSocketAddress = new IOException("Exception in connect");
      paramInetSocketAddress.initCause(paramSocket);
      throw paramInetSocketAddress;
    }
    catch (AssertionError paramSocket)
    {
      if (Util.isAndroidGetsocknameError(paramSocket)) {
        throw new IOException(paramSocket);
      }
      throw paramSocket;
    }
  }
  
  public SSLContext getSSLContext()
  {
    int i = 1;
    try
    {
      if (Build.VERSION.SDK_INT >= 16)
      {
        int j = Build.VERSION.SDK_INT;
        if (j < 22) {}
      }
      else
      {
        i = 0;
      }
    }
    catch (NoClassDefFoundError localNoClassDefFoundError)
    {
      for (;;) {}
    }
    if (i != 0) {}
    for (;;)
    {
      try
      {
        localSSLContext = SSLContext.getInstance("TLSv1.2");
        return localSSLContext;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException2)
      {
        SSLContext localSSLContext;
        continue;
      }
      try
      {
        localSSLContext = SSLContext.getInstance("TLS");
        return localSSLContext;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException1)
      {
        throw new IllegalStateException("No TLS provider", localNoSuchAlgorithmException1);
      }
    }
  }
  
  @Nullable
  public String getSelectedProtocol(SSLSocket paramSSLSocket)
  {
    Object localObject1 = this.getAlpnSelectedProtocol;
    Object localObject2 = null;
    if (localObject1 == null) {
      return null;
    }
    if (!((OptionalMethod)localObject1).isSupported(paramSSLSocket)) {
      return null;
    }
    localObject1 = (byte[])this.getAlpnSelectedProtocol.invokeWithoutCheckedException(paramSSLSocket, new Object[0]);
    paramSSLSocket = (SSLSocket)localObject2;
    if (localObject1 != null) {
      paramSSLSocket = new String((byte[])localObject1, Util.UTF_8);
    }
    return paramSSLSocket;
  }
  
  public Object getStackTraceForCloseable(String paramString)
  {
    return this.closeGuard.createAndOpen(paramString);
  }
  
  public boolean isCleartextTrafficPermitted(String paramString)
  {
    try
    {
      try
      {
        Class localClass = Class.forName("android.security.NetworkSecurityPolicy");
        boolean bool = api24IsCleartextTrafficPermitted(paramString, localClass, localClass.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]));
        return bool;
      }
      catch (InvocationTargetException paramString) {}catch (IllegalArgumentException paramString) {}catch (IllegalAccessException paramString) {}
      throw Util.assertionError("unable to determine cleartext support", paramString);
    }
    catch (ClassNotFoundException|NoSuchMethodException localClassNotFoundException) {}
    return super.isCleartextTrafficPermitted(paramString);
  }
  
  public void log(int paramInt, String paramString, @Nullable Throwable paramThrowable)
  {
    int i = 5;
    if (paramInt != 5) {
      i = 3;
    }
    Object localObject = paramString;
    if (paramThrowable != null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append('\n');
      ((StringBuilder)localObject).append(Log.getStackTraceString(paramThrowable));
      localObject = ((StringBuilder)localObject).toString();
    }
    paramInt = 0;
    int j = ((String)localObject).length();
    if (paramInt < j)
    {
      int k = ((String)localObject).indexOf('\n', paramInt);
      if (k == -1) {
        k = j;
      }
      for (;;)
      {
        int m = Math.min(k, paramInt + 4000);
        Log.println(i, "OkHttp", ((String)localObject).substring(paramInt, m));
        if (m >= k)
        {
          paramInt = m + 1;
          break;
        }
        paramInt = m;
      }
    }
  }
  
  public void logCloseableLeak(String paramString, Object paramObject)
  {
    if (!this.closeGuard.warnIfOpen(paramObject)) {
      log(5, paramString, null);
    }
  }
  
  @Nullable
  protected X509TrustManager trustManager(SSLSocketFactory paramSSLSocketFactory)
  {
    Object localObject1 = readFieldOrNull(paramSSLSocketFactory, this.sslParametersClass, "sslParameters");
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      try
      {
        localObject2 = readFieldOrNull(paramSSLSocketFactory, Class.forName("com.google.android.gms.org.conscrypt.SSLParametersImpl", false, paramSSLSocketFactory.getClass().getClassLoader()), "sslParameters");
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        return super.trustManager(paramSSLSocketFactory);
      }
    }
    paramSSLSocketFactory = (X509TrustManager)readFieldOrNull(localClassNotFoundException, X509TrustManager.class, "x509TrustManager");
    if (paramSSLSocketFactory != null) {
      return paramSSLSocketFactory;
    }
    return (X509TrustManager)readFieldOrNull(localClassNotFoundException, X509TrustManager.class, "trustManager");
  }
  
  static final class AndroidCertificateChainCleaner
    extends CertificateChainCleaner
  {
    private final Method checkServerTrusted;
    private final Object x509TrustManagerExtensions;
    
    AndroidCertificateChainCleaner(Object paramObject, Method paramMethod)
    {
      this.x509TrustManagerExtensions = paramObject;
      this.checkServerTrusted = paramMethod;
    }
    
    public List<Certificate> clean(List<Certificate> paramList, String paramString)
    {
      try
      {
        paramList = (X509Certificate[])paramList.toArray(new X509Certificate[paramList.size()]);
        paramList = (List)this.checkServerTrusted.invoke(this.x509TrustManagerExtensions, new Object[] { paramList, "RSA", paramString });
        return paramList;
      }
      catch (IllegalAccessException paramList)
      {
        throw new AssertionError(paramList);
      }
      catch (InvocationTargetException paramString)
      {
        paramList = new SSLPeerUnverifiedException(paramString.getMessage());
        paramList.initCause(paramString);
        throw paramList;
      }
    }
    
    public boolean equals(Object paramObject)
    {
      return paramObject instanceof AndroidCertificateChainCleaner;
    }
    
    public int hashCode()
    {
      return 0;
    }
  }
  
  static final class AndroidTrustRootIndex
    implements TrustRootIndex
  {
    private final Method findByIssuerAndSignatureMethod;
    private final X509TrustManager trustManager;
    
    AndroidTrustRootIndex(X509TrustManager paramX509TrustManager, Method paramMethod)
    {
      this.findByIssuerAndSignatureMethod = paramMethod;
      this.trustManager = paramX509TrustManager;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool = true;
      if (paramObject == this) {
        return true;
      }
      if (!(paramObject instanceof AndroidTrustRootIndex)) {
        return false;
      }
      paramObject = (AndroidTrustRootIndex)paramObject;
      if ((!this.trustManager.equals(((AndroidTrustRootIndex)paramObject).trustManager)) || (!this.findByIssuerAndSignatureMethod.equals(((AndroidTrustRootIndex)paramObject).findByIssuerAndSignatureMethod))) {
        bool = false;
      }
      return bool;
    }
    
    public X509Certificate findByIssuerAndSignature(X509Certificate paramX509Certificate)
    {
      Object localObject = null;
      try
      {
        TrustAnchor localTrustAnchor = (TrustAnchor)this.findByIssuerAndSignatureMethod.invoke(this.trustManager, new Object[] { paramX509Certificate });
        paramX509Certificate = (X509Certificate)localObject;
        if (localTrustAnchor != null) {
          paramX509Certificate = localTrustAnchor.getTrustedCert();
        }
        return paramX509Certificate;
      }
      catch (InvocationTargetException paramX509Certificate)
      {
        return null;
      }
      catch (IllegalAccessException paramX509Certificate)
      {
        throw Util.assertionError("unable to get issues and signature", paramX509Certificate);
      }
    }
    
    public int hashCode()
    {
      return this.trustManager.hashCode() + this.findByIssuerAndSignatureMethod.hashCode() * 31;
    }
  }
  
  static final class CloseGuard
  {
    private final Method getMethod;
    private final Method openMethod;
    private final Method warnIfOpenMethod;
    
    CloseGuard(Method paramMethod1, Method paramMethod2, Method paramMethod3)
    {
      this.getMethod = paramMethod1;
      this.openMethod = paramMethod2;
      this.warnIfOpenMethod = paramMethod3;
    }
    
    static CloseGuard get()
    {
      Object localObject1 = null;
      Object localObject2;
      Object localObject3;
      try
      {
        localObject2 = Class.forName("dalvik.system.CloseGuard");
        Method localMethod1 = ((Class)localObject2).getMethod("get", new Class[0]);
        Method localMethod2 = ((Class)localObject2).getMethod("open", new Class[] { String.class });
        localObject2 = ((Class)localObject2).getMethod("warnIfOpen", new Class[0]);
        localObject1 = localMethod1;
      }
      catch (Exception localException)
      {
        localObject2 = null;
        localObject3 = localObject2;
      }
      return new CloseGuard((Method)localObject1, (Method)localObject3, (Method)localObject2);
    }
    
    Object createAndOpen(String paramString)
    {
      Object localObject = this.getMethod;
      if (localObject != null) {}
      try
      {
        localObject = ((Method)localObject).invoke(null, new Object[0]);
        this.openMethod.invoke(localObject, new Object[] { paramString });
        return localObject;
      }
      catch (Exception paramString)
      {
        for (;;) {}
      }
      return null;
    }
    
    boolean warnIfOpen(Object paramObject)
    {
      bool1 = false;
      bool2 = bool1;
      if (paramObject != null) {}
      try
      {
        this.warnIfOpenMethod.invoke(paramObject, new Object[0]);
        bool2 = true;
      }
      catch (Exception paramObject)
      {
        for (;;)
        {
          bool2 = bool1;
        }
      }
      return bool2;
    }
  }
}


/* Location:              ~/okhttp3/internal/platform/AndroidPlatform.class
 *
 * Reversed by:           J
 */