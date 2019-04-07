package okhttp3;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.cache.InternalCache;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.proxy.NullProxySelector;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

public class OkHttpClient
  implements Cloneable, Call.Factory, WebSocket.Factory
{
  static final List<ConnectionSpec> DEFAULT_CONNECTION_SPECS;
  static final List<Protocol> DEFAULT_PROTOCOLS = Util.immutableList(new Protocol[] { Protocol.HTTP_2, Protocol.HTTP_1_1 });
  final Authenticator authenticator;
  @Nullable
  final Cache cache;
  final int callTimeout;
  final CertificateChainCleaner certificateChainCleaner;
  final CertificatePinner certificatePinner;
  final int connectTimeout;
  final ConnectionPool connectionPool;
  final List<ConnectionSpec> connectionSpecs;
  final CookieJar cookieJar;
  final Dispatcher dispatcher;
  final Dns dns;
  final EventListener.Factory eventListenerFactory;
  final boolean followRedirects;
  final boolean followSslRedirects;
  final HostnameVerifier hostnameVerifier;
  final List<Interceptor> interceptors;
  @Nullable
  final InternalCache internalCache;
  final List<Interceptor> networkInterceptors;
  final int pingInterval;
  final List<Protocol> protocols;
  @Nullable
  final Proxy proxy;
  final Authenticator proxyAuthenticator;
  final ProxySelector proxySelector;
  final int readTimeout;
  final boolean retryOnConnectionFailure;
  final SocketFactory socketFactory;
  final SSLSocketFactory sslSocketFactory;
  final int writeTimeout;
  
  static
  {
    DEFAULT_CONNECTION_SPECS = Util.immutableList(new ConnectionSpec[] { ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT });
    Internal.instance = new Internal()
    {
      public void addLenient(Headers.Builder paramAnonymousBuilder, String paramAnonymousString)
      {
        paramAnonymousBuilder.addLenient(paramAnonymousString);
      }
      
      public void addLenient(Headers.Builder paramAnonymousBuilder, String paramAnonymousString1, String paramAnonymousString2)
      {
        paramAnonymousBuilder.addLenient(paramAnonymousString1, paramAnonymousString2);
      }
      
      public void apply(ConnectionSpec paramAnonymousConnectionSpec, SSLSocket paramAnonymousSSLSocket, boolean paramAnonymousBoolean)
      {
        paramAnonymousConnectionSpec.apply(paramAnonymousSSLSocket, paramAnonymousBoolean);
      }
      
      public int code(Response.Builder paramAnonymousBuilder)
      {
        return paramAnonymousBuilder.code;
      }
      
      public boolean connectionBecameIdle(ConnectionPool paramAnonymousConnectionPool, RealConnection paramAnonymousRealConnection)
      {
        return paramAnonymousConnectionPool.connectionBecameIdle(paramAnonymousRealConnection);
      }
      
      public Socket deduplicate(ConnectionPool paramAnonymousConnectionPool, Address paramAnonymousAddress, StreamAllocation paramAnonymousStreamAllocation)
      {
        return paramAnonymousConnectionPool.deduplicate(paramAnonymousAddress, paramAnonymousStreamAllocation);
      }
      
      public boolean equalsNonHost(Address paramAnonymousAddress1, Address paramAnonymousAddress2)
      {
        return paramAnonymousAddress1.equalsNonHost(paramAnonymousAddress2);
      }
      
      public RealConnection get(ConnectionPool paramAnonymousConnectionPool, Address paramAnonymousAddress, StreamAllocation paramAnonymousStreamAllocation, Route paramAnonymousRoute)
      {
        return paramAnonymousConnectionPool.get(paramAnonymousAddress, paramAnonymousStreamAllocation, paramAnonymousRoute);
      }
      
      public boolean isInvalidHttpUrlHost(IllegalArgumentException paramAnonymousIllegalArgumentException)
      {
        return paramAnonymousIllegalArgumentException.getMessage().startsWith("Invalid URL host");
      }
      
      public Call newWebSocketCall(OkHttpClient paramAnonymousOkHttpClient, Request paramAnonymousRequest)
      {
        return RealCall.newRealCall(paramAnonymousOkHttpClient, paramAnonymousRequest, true);
      }
      
      public void put(ConnectionPool paramAnonymousConnectionPool, RealConnection paramAnonymousRealConnection)
      {
        paramAnonymousConnectionPool.put(paramAnonymousRealConnection);
      }
      
      public RouteDatabase routeDatabase(ConnectionPool paramAnonymousConnectionPool)
      {
        return paramAnonymousConnectionPool.routeDatabase;
      }
      
      public void setCache(OkHttpClient.Builder paramAnonymousBuilder, InternalCache paramAnonymousInternalCache)
      {
        paramAnonymousBuilder.setInternalCache(paramAnonymousInternalCache);
      }
      
      public StreamAllocation streamAllocation(Call paramAnonymousCall)
      {
        return ((RealCall)paramAnonymousCall).streamAllocation();
      }
      
      @Nullable
      public IOException timeoutExit(Call paramAnonymousCall, @Nullable IOException paramAnonymousIOException)
      {
        return ((RealCall)paramAnonymousCall).timeoutExit(paramAnonymousIOException);
      }
    };
  }
  
  public OkHttpClient()
  {
    this(new Builder());
  }
  
  OkHttpClient(Builder paramBuilder)
  {
    this.dispatcher = paramBuilder.dispatcher;
    this.proxy = paramBuilder.proxy;
    this.protocols = paramBuilder.protocols;
    this.connectionSpecs = paramBuilder.connectionSpecs;
    this.interceptors = Util.immutableList(paramBuilder.interceptors);
    this.networkInterceptors = Util.immutableList(paramBuilder.networkInterceptors);
    this.eventListenerFactory = paramBuilder.eventListenerFactory;
    this.proxySelector = paramBuilder.proxySelector;
    this.cookieJar = paramBuilder.cookieJar;
    this.cache = paramBuilder.cache;
    this.internalCache = paramBuilder.internalCache;
    this.socketFactory = paramBuilder.socketFactory;
    Iterator localIterator = this.connectionSpecs.iterator();
    int i = 0;
    Object localObject;
    while (localIterator.hasNext())
    {
      localObject = (ConnectionSpec)localIterator.next();
      if ((i == 0) && (!((ConnectionSpec)localObject).isTls())) {
        i = 0;
      } else {
        i = 1;
      }
    }
    if ((paramBuilder.sslSocketFactory == null) && (i != 0))
    {
      localObject = Util.platformTrustManager();
      this.sslSocketFactory = newSslSocketFactory((X509TrustManager)localObject);
      this.certificateChainCleaner = CertificateChainCleaner.get((X509TrustManager)localObject);
    }
    else
    {
      this.sslSocketFactory = paramBuilder.sslSocketFactory;
      this.certificateChainCleaner = paramBuilder.certificateChainCleaner;
    }
    if (this.sslSocketFactory != null) {
      Platform.get().configureSslSocketFactory(this.sslSocketFactory);
    }
    this.hostnameVerifier = paramBuilder.hostnameVerifier;
    this.certificatePinner = paramBuilder.certificatePinner.withCertificateChainCleaner(this.certificateChainCleaner);
    this.proxyAuthenticator = paramBuilder.proxyAuthenticator;
    this.authenticator = paramBuilder.authenticator;
    this.connectionPool = paramBuilder.connectionPool;
    this.dns = paramBuilder.dns;
    this.followSslRedirects = paramBuilder.followSslRedirects;
    this.followRedirects = paramBuilder.followRedirects;
    this.retryOnConnectionFailure = paramBuilder.retryOnConnectionFailure;
    this.callTimeout = paramBuilder.callTimeout;
    this.connectTimeout = paramBuilder.connectTimeout;
    this.readTimeout = paramBuilder.readTimeout;
    this.writeTimeout = paramBuilder.writeTimeout;
    this.pingInterval = paramBuilder.pingInterval;
    if (!this.interceptors.contains(null))
    {
      if (!this.networkInterceptors.contains(null)) {
        return;
      }
      paramBuilder = new StringBuilder();
      paramBuilder.append("Null network interceptor: ");
      paramBuilder.append(this.networkInterceptors);
      throw new IllegalStateException(paramBuilder.toString());
    }
    paramBuilder = new StringBuilder();
    paramBuilder.append("Null interceptor: ");
    paramBuilder.append(this.interceptors);
    throw new IllegalStateException(paramBuilder.toString());
  }
  
  private static SSLSocketFactory newSslSocketFactory(X509TrustManager paramX509TrustManager)
  {
    try
    {
      SSLContext localSSLContext = Platform.get().getSSLContext();
      localSSLContext.init(null, new TrustManager[] { paramX509TrustManager }, null);
      paramX509TrustManager = localSSLContext.getSocketFactory();
      return paramX509TrustManager;
    }
    catch (GeneralSecurityException paramX509TrustManager)
    {
      throw Util.assertionError("No System TLS", paramX509TrustManager);
    }
  }
  
  public Authenticator authenticator()
  {
    return this.authenticator;
  }
  
  @Nullable
  public Cache cache()
  {
    return this.cache;
  }
  
  public int callTimeoutMillis()
  {
    return this.callTimeout;
  }
  
  public CertificatePinner certificatePinner()
  {
    return this.certificatePinner;
  }
  
  public int connectTimeoutMillis()
  {
    return this.connectTimeout;
  }
  
  public ConnectionPool connectionPool()
  {
    return this.connectionPool;
  }
  
  public List<ConnectionSpec> connectionSpecs()
  {
    return this.connectionSpecs;
  }
  
  public CookieJar cookieJar()
  {
    return this.cookieJar;
  }
  
  public Dispatcher dispatcher()
  {
    return this.dispatcher;
  }
  
  public Dns dns()
  {
    return this.dns;
  }
  
  public EventListener.Factory eventListenerFactory()
  {
    return this.eventListenerFactory;
  }
  
  public boolean followRedirects()
  {
    return this.followRedirects;
  }
  
  public boolean followSslRedirects()
  {
    return this.followSslRedirects;
  }
  
  public HostnameVerifier hostnameVerifier()
  {
    return this.hostnameVerifier;
  }
  
  public List<Interceptor> interceptors()
  {
    return this.interceptors;
  }
  
  InternalCache internalCache()
  {
    Object localObject = this.cache;
    if (localObject != null) {
      localObject = ((Cache)localObject).internalCache;
    } else {
      localObject = this.internalCache;
    }
    return (InternalCache)localObject;
  }
  
  public List<Interceptor> networkInterceptors()
  {
    return this.networkInterceptors;
  }
  
  public Builder newBuilder()
  {
    return new Builder(this);
  }
  
  public Call newCall(Request paramRequest)
  {
    return RealCall.newRealCall(this, paramRequest, false);
  }
  
  public WebSocket newWebSocket(Request paramRequest, WebSocketListener paramWebSocketListener)
  {
    paramRequest = new RealWebSocket(paramRequest, paramWebSocketListener, new Random(), this.pingInterval);
    paramRequest.connect(this);
    return paramRequest;
  }
  
  public int pingIntervalMillis()
  {
    return this.pingInterval;
  }
  
  public List<Protocol> protocols()
  {
    return this.protocols;
  }
  
  @Nullable
  public Proxy proxy()
  {
    return this.proxy;
  }
  
  public Authenticator proxyAuthenticator()
  {
    return this.proxyAuthenticator;
  }
  
  public ProxySelector proxySelector()
  {
    return this.proxySelector;
  }
  
  public int readTimeoutMillis()
  {
    return this.readTimeout;
  }
  
  public boolean retryOnConnectionFailure()
  {
    return this.retryOnConnectionFailure;
  }
  
  public SocketFactory socketFactory()
  {
    return this.socketFactory;
  }
  
  public SSLSocketFactory sslSocketFactory()
  {
    return this.sslSocketFactory;
  }
  
  public int writeTimeoutMillis()
  {
    return this.writeTimeout;
  }
  
  public static final class Builder
  {
    Authenticator authenticator;
    @Nullable
    Cache cache;
    int callTimeout;
    @Nullable
    CertificateChainCleaner certificateChainCleaner;
    CertificatePinner certificatePinner;
    int connectTimeout;
    ConnectionPool connectionPool;
    List<ConnectionSpec> connectionSpecs;
    CookieJar cookieJar;
    Dispatcher dispatcher;
    Dns dns;
    EventListener.Factory eventListenerFactory;
    boolean followRedirects;
    boolean followSslRedirects;
    HostnameVerifier hostnameVerifier;
    final List<Interceptor> interceptors = new ArrayList();
    @Nullable
    InternalCache internalCache;
    final List<Interceptor> networkInterceptors = new ArrayList();
    int pingInterval;
    List<Protocol> protocols;
    @Nullable
    Proxy proxy;
    Authenticator proxyAuthenticator;
    ProxySelector proxySelector;
    int readTimeout;
    boolean retryOnConnectionFailure;
    SocketFactory socketFactory;
    @Nullable
    SSLSocketFactory sslSocketFactory;
    int writeTimeout;
    
    public Builder()
    {
      this.dispatcher = new Dispatcher();
      this.protocols = OkHttpClient.DEFAULT_PROTOCOLS;
      this.connectionSpecs = OkHttpClient.DEFAULT_CONNECTION_SPECS;
      this.eventListenerFactory = EventListener.factory(EventListener.NONE);
      this.proxySelector = ProxySelector.getDefault();
      if (this.proxySelector == null) {
        this.proxySelector = new NullProxySelector();
      }
      this.cookieJar = CookieJar.NO_COOKIES;
      this.socketFactory = SocketFactory.getDefault();
      this.hostnameVerifier = OkHostnameVerifier.INSTANCE;
      this.certificatePinner = CertificatePinner.DEFAULT;
      this.proxyAuthenticator = Authenticator.NONE;
      this.authenticator = Authenticator.NONE;
      this.connectionPool = new ConnectionPool();
      this.dns = Dns.SYSTEM;
      this.followSslRedirects = true;
      this.followRedirects = true;
      this.retryOnConnectionFailure = true;
      this.callTimeout = 0;
      this.connectTimeout = 10000;
      this.readTimeout = 10000;
      this.writeTimeout = 10000;
      this.pingInterval = 0;
    }
    
    Builder(OkHttpClient paramOkHttpClient)
    {
      this.dispatcher = paramOkHttpClient.dispatcher;
      this.proxy = paramOkHttpClient.proxy;
      this.protocols = paramOkHttpClient.protocols;
      this.connectionSpecs = paramOkHttpClient.connectionSpecs;
      this.interceptors.addAll(paramOkHttpClient.interceptors);
      this.networkInterceptors.addAll(paramOkHttpClient.networkInterceptors);
      this.eventListenerFactory = paramOkHttpClient.eventListenerFactory;
      this.proxySelector = paramOkHttpClient.proxySelector;
      this.cookieJar = paramOkHttpClient.cookieJar;
      this.internalCache = paramOkHttpClient.internalCache;
      this.cache = paramOkHttpClient.cache;
      this.socketFactory = paramOkHttpClient.socketFactory;
      this.sslSocketFactory = paramOkHttpClient.sslSocketFactory;
      this.certificateChainCleaner = paramOkHttpClient.certificateChainCleaner;
      this.hostnameVerifier = paramOkHttpClient.hostnameVerifier;
      this.certificatePinner = paramOkHttpClient.certificatePinner;
      this.proxyAuthenticator = paramOkHttpClient.proxyAuthenticator;
      this.authenticator = paramOkHttpClient.authenticator;
      this.connectionPool = paramOkHttpClient.connectionPool;
      this.dns = paramOkHttpClient.dns;
      this.followSslRedirects = paramOkHttpClient.followSslRedirects;
      this.followRedirects = paramOkHttpClient.followRedirects;
      this.retryOnConnectionFailure = paramOkHttpClient.retryOnConnectionFailure;
      this.callTimeout = paramOkHttpClient.callTimeout;
      this.connectTimeout = paramOkHttpClient.connectTimeout;
      this.readTimeout = paramOkHttpClient.readTimeout;
      this.writeTimeout = paramOkHttpClient.writeTimeout;
      this.pingInterval = paramOkHttpClient.pingInterval;
    }
    
    public Builder addInterceptor(Interceptor paramInterceptor)
    {
      if (paramInterceptor != null)
      {
        this.interceptors.add(paramInterceptor);
        return this;
      }
      throw new IllegalArgumentException("interceptor == null");
    }
    
    public Builder addNetworkInterceptor(Interceptor paramInterceptor)
    {
      if (paramInterceptor != null)
      {
        this.networkInterceptors.add(paramInterceptor);
        return this;
      }
      throw new IllegalArgumentException("interceptor == null");
    }
    
    public Builder authenticator(Authenticator paramAuthenticator)
    {
      if (paramAuthenticator != null)
      {
        this.authenticator = paramAuthenticator;
        return this;
      }
      throw new NullPointerException("authenticator == null");
    }
    
    public OkHttpClient build()
    {
      return new OkHttpClient(this);
    }
    
    public Builder cache(@Nullable Cache paramCache)
    {
      this.cache = paramCache;
      this.internalCache = null;
      return this;
    }
    
    public Builder callTimeout(long paramLong, TimeUnit paramTimeUnit)
    {
      this.callTimeout = Util.checkDuration("timeout", paramLong, paramTimeUnit);
      return this;
    }
    
    @IgnoreJRERequirement
    public Builder callTimeout(Duration paramDuration)
    {
      this.callTimeout = Util.checkDuration("timeout", paramDuration.toMillis(), TimeUnit.MILLISECONDS);
      return this;
    }
    
    public Builder certificatePinner(CertificatePinner paramCertificatePinner)
    {
      if (paramCertificatePinner != null)
      {
        this.certificatePinner = paramCertificatePinner;
        return this;
      }
      throw new NullPointerException("certificatePinner == null");
    }
    
    public Builder connectTimeout(long paramLong, TimeUnit paramTimeUnit)
    {
      this.connectTimeout = Util.checkDuration("timeout", paramLong, paramTimeUnit);
      return this;
    }
    
    @IgnoreJRERequirement
    public Builder connectTimeout(Duration paramDuration)
    {
      this.connectTimeout = Util.checkDuration("timeout", paramDuration.toMillis(), TimeUnit.MILLISECONDS);
      return this;
    }
    
    public Builder connectionPool(ConnectionPool paramConnectionPool)
    {
      if (paramConnectionPool != null)
      {
        this.connectionPool = paramConnectionPool;
        return this;
      }
      throw new NullPointerException("connectionPool == null");
    }
    
    public Builder connectionSpecs(List<ConnectionSpec> paramList)
    {
      this.connectionSpecs = Util.immutableList(paramList);
      return this;
    }
    
    public Builder cookieJar(CookieJar paramCookieJar)
    {
      if (paramCookieJar != null)
      {
        this.cookieJar = paramCookieJar;
        return this;
      }
      throw new NullPointerException("cookieJar == null");
    }
    
    public Builder dispatcher(Dispatcher paramDispatcher)
    {
      if (paramDispatcher != null)
      {
        this.dispatcher = paramDispatcher;
        return this;
      }
      throw new IllegalArgumentException("dispatcher == null");
    }
    
    public Builder dns(Dns paramDns)
    {
      if (paramDns != null)
      {
        this.dns = paramDns;
        return this;
      }
      throw new NullPointerException("dns == null");
    }
    
    public Builder eventListener(EventListener paramEventListener)
    {
      if (paramEventListener != null)
      {
        this.eventListenerFactory = EventListener.factory(paramEventListener);
        return this;
      }
      throw new NullPointerException("eventListener == null");
    }
    
    public Builder eventListenerFactory(EventListener.Factory paramFactory)
    {
      if (paramFactory != null)
      {
        this.eventListenerFactory = paramFactory;
        return this;
      }
      throw new NullPointerException("eventListenerFactory == null");
    }
    
    public Builder followRedirects(boolean paramBoolean)
    {
      this.followRedirects = paramBoolean;
      return this;
    }
    
    public Builder followSslRedirects(boolean paramBoolean)
    {
      this.followSslRedirects = paramBoolean;
      return this;
    }
    
    public Builder hostnameVerifier(HostnameVerifier paramHostnameVerifier)
    {
      if (paramHostnameVerifier != null)
      {
        this.hostnameVerifier = paramHostnameVerifier;
        return this;
      }
      throw new NullPointerException("hostnameVerifier == null");
    }
    
    public List<Interceptor> interceptors()
    {
      return this.interceptors;
    }
    
    public List<Interceptor> networkInterceptors()
    {
      return this.networkInterceptors;
    }
    
    public Builder pingInterval(long paramLong, TimeUnit paramTimeUnit)
    {
      this.pingInterval = Util.checkDuration("interval", paramLong, paramTimeUnit);
      return this;
    }
    
    @IgnoreJRERequirement
    public Builder pingInterval(Duration paramDuration)
    {
      this.pingInterval = Util.checkDuration("timeout", paramDuration.toMillis(), TimeUnit.MILLISECONDS);
      return this;
    }
    
    public Builder protocols(List<Protocol> paramList)
    {
      paramList = new ArrayList(paramList);
      if ((!paramList.contains(Protocol.H2_PRIOR_KNOWLEDGE)) && (!paramList.contains(Protocol.HTTP_1_1)))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("protocols must contain h2_prior_knowledge or http/1.1: ");
        localStringBuilder.append(paramList);
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
      if ((paramList.contains(Protocol.H2_PRIOR_KNOWLEDGE)) && (paramList.size() > 1))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("protocols containing h2_prior_knowledge cannot use other protocols: ");
        localStringBuilder.append(paramList);
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
      if (!paramList.contains(Protocol.HTTP_1_0))
      {
        if (!paramList.contains(null))
        {
          paramList.remove(Protocol.SPDY_3);
          this.protocols = Collections.unmodifiableList(paramList);
          return this;
        }
        throw new IllegalArgumentException("protocols must not contain null");
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("protocols must not contain http/1.0: ");
      localStringBuilder.append(paramList);
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    
    public Builder proxy(@Nullable Proxy paramProxy)
    {
      this.proxy = paramProxy;
      return this;
    }
    
    public Builder proxyAuthenticator(Authenticator paramAuthenticator)
    {
      if (paramAuthenticator != null)
      {
        this.proxyAuthenticator = paramAuthenticator;
        return this;
      }
      throw new NullPointerException("proxyAuthenticator == null");
    }
    
    public Builder proxySelector(ProxySelector paramProxySelector)
    {
      if (paramProxySelector != null)
      {
        this.proxySelector = paramProxySelector;
        return this;
      }
      throw new NullPointerException("proxySelector == null");
    }
    
    public Builder readTimeout(long paramLong, TimeUnit paramTimeUnit)
    {
      this.readTimeout = Util.checkDuration("timeout", paramLong, paramTimeUnit);
      return this;
    }
    
    @IgnoreJRERequirement
    public Builder readTimeout(Duration paramDuration)
    {
      this.readTimeout = Util.checkDuration("timeout", paramDuration.toMillis(), TimeUnit.MILLISECONDS);
      return this;
    }
    
    public Builder retryOnConnectionFailure(boolean paramBoolean)
    {
      this.retryOnConnectionFailure = paramBoolean;
      return this;
    }
    
    void setInternalCache(@Nullable InternalCache paramInternalCache)
    {
      this.internalCache = paramInternalCache;
      this.cache = null;
    }
    
    public Builder socketFactory(SocketFactory paramSocketFactory)
    {
      if (paramSocketFactory != null)
      {
        this.socketFactory = paramSocketFactory;
        return this;
      }
      throw new NullPointerException("socketFactory == null");
    }
    
    public Builder sslSocketFactory(SSLSocketFactory paramSSLSocketFactory)
    {
      if (paramSSLSocketFactory != null)
      {
        this.sslSocketFactory = paramSSLSocketFactory;
        this.certificateChainCleaner = Platform.get().buildCertificateChainCleaner(paramSSLSocketFactory);
        return this;
      }
      throw new NullPointerException("sslSocketFactory == null");
    }
    
    public Builder sslSocketFactory(SSLSocketFactory paramSSLSocketFactory, X509TrustManager paramX509TrustManager)
    {
      if (paramSSLSocketFactory != null)
      {
        if (paramX509TrustManager != null)
        {
          this.sslSocketFactory = paramSSLSocketFactory;
          this.certificateChainCleaner = CertificateChainCleaner.get(paramX509TrustManager);
          return this;
        }
        throw new NullPointerException("trustManager == null");
      }
      throw new NullPointerException("sslSocketFactory == null");
    }
    
    public Builder writeTimeout(long paramLong, TimeUnit paramTimeUnit)
    {
      this.writeTimeout = Util.checkDuration("timeout", paramLong, paramTimeUnit);
      return this;
    }
    
    @IgnoreJRERequirement
    public Builder writeTimeout(Duration paramDuration)
    {
      this.writeTimeout = Util.checkDuration("timeout", paramDuration.toMillis(), TimeUnit.MILLISECONDS);
      return this;
    }
  }
}


/* Location:              ~/okhttp3/OkHttpClient.class
 *
 * Reversed by:           J
 */