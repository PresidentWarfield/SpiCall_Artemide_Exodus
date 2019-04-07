package okhttp3.internal.connection;

import a.c;
import a.d;
import a.e;
import a.l;
import a.s;
import a.t;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.SocketFactory;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.Address;
import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.Connection;
import okhttp3.ConnectionPool;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.Version;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http1.Http1Codec;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Http2Codec;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Http2Connection.Builder;
import okhttp3.internal.http2.Http2Connection.Listener;
import okhttp3.internal.http2.Http2Stream;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket.Streams;

public final class RealConnection
  extends Http2Connection.Listener
  implements Connection
{
  private static final int MAX_TUNNEL_ATTEMPTS = 21;
  private static final String NPE_THROW_WITH_NULL = "throw with null exception";
  public int allocationLimit = 1;
  public final List<Reference<StreamAllocation>> allocations = new ArrayList();
  private final ConnectionPool connectionPool;
  private Handshake handshake;
  private Http2Connection http2Connection;
  public long idleAtNanos = Long.MAX_VALUE;
  public boolean noNewStreams;
  private Protocol protocol;
  private Socket rawSocket;
  private final Route route;
  private d sink;
  private Socket socket;
  private e source;
  public int successCount;
  
  public RealConnection(ConnectionPool paramConnectionPool, Route paramRoute)
  {
    this.connectionPool = paramConnectionPool;
    this.route = paramRoute;
  }
  
  private void connectSocket(int paramInt1, int paramInt2, Call paramCall, EventListener paramEventListener)
  {
    Proxy localProxy = this.route.proxy();
    Object localObject = this.route.address();
    if ((localProxy.type() != Proxy.Type.DIRECT) && (localProxy.type() != Proxy.Type.HTTP)) {
      localObject = new Socket(localProxy);
    } else {
      localObject = ((Address)localObject).socketFactory().createSocket();
    }
    this.rawSocket = ((Socket)localObject);
    paramEventListener.connectStart(paramCall, this.route.socketAddress(), localProxy);
    this.rawSocket.setSoTimeout(paramInt2);
    try
    {
      Platform.get().connectSocket(this.rawSocket, this.route.socketAddress(), paramInt1);
      try
      {
        this.source = l.a(l.b(this.rawSocket));
        this.sink = l.a(l.a(this.rawSocket));
      }
      catch (NullPointerException paramCall)
      {
        if ("throw with null exception".equals(paramCall.getMessage())) {
          break label159;
        }
      }
      return;
    }
    catch (ConnectException paramCall)
    {
      label159:
      paramEventListener = new StringBuilder();
      paramEventListener.append("Failed to connect to ");
      paramEventListener.append(this.route.socketAddress());
      paramEventListener = new ConnectException(paramEventListener.toString());
      paramEventListener.initCause(paramCall);
      throw paramEventListener;
    }
    throw new IOException(paramCall);
  }
  
  /* Error */
  private void connectTls(ConnectionSpecSelector paramConnectionSpecSelector)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 61	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   4: invokevirtual 78	okhttp3/Route:address	()Lokhttp3/Address;
    //   7: astore_2
    //   8: aload_2
    //   9: invokevirtual 200	okhttp3/Address:sslSocketFactory	()Ljavax/net/ssl/SSLSocketFactory;
    //   12: astore_3
    //   13: aconst_null
    //   14: astore 4
    //   16: aconst_null
    //   17: astore 5
    //   19: aconst_null
    //   20: astore 6
    //   22: aload_3
    //   23: aload_0
    //   24: getfield 112	okhttp3/internal/connection/RealConnection:rawSocket	Ljava/net/Socket;
    //   27: aload_2
    //   28: invokevirtual 204	okhttp3/Address:url	()Lokhttp3/HttpUrl;
    //   31: invokevirtual 209	okhttp3/HttpUrl:host	()Ljava/lang/String;
    //   34: aload_2
    //   35: invokevirtual 204	okhttp3/Address:url	()Lokhttp3/HttpUrl;
    //   38: invokevirtual 213	okhttp3/HttpUrl:port	()I
    //   41: iconst_1
    //   42: invokevirtual 218	javax/net/ssl/SSLSocketFactory:createSocket	(Ljava/net/Socket;Ljava/lang/String;IZ)Ljava/net/Socket;
    //   45: checkcast 220	javax/net/ssl/SSLSocket
    //   48: astore_3
    //   49: aload_1
    //   50: aload_3
    //   51: invokevirtual 226	okhttp3/internal/connection/ConnectionSpecSelector:configureSecureSocket	(Ljavax/net/ssl/SSLSocket;)Lokhttp3/ConnectionSpec;
    //   54: astore 5
    //   56: aload 5
    //   58: invokevirtual 232	okhttp3/ConnectionSpec:supportsTlsExtensions	()Z
    //   61: ifeq +21 -> 82
    //   64: invokestatic 132	okhttp3/internal/platform/Platform:get	()Lokhttp3/internal/platform/Platform;
    //   67: aload_3
    //   68: aload_2
    //   69: invokevirtual 204	okhttp3/Address:url	()Lokhttp3/HttpUrl;
    //   72: invokevirtual 209	okhttp3/HttpUrl:host	()Ljava/lang/String;
    //   75: aload_2
    //   76: invokevirtual 236	okhttp3/Address:protocols	()Ljava/util/List;
    //   79: invokevirtual 240	okhttp3/internal/platform/Platform:configureTlsExtensions	(Ljavax/net/ssl/SSLSocket;Ljava/lang/String;Ljava/util/List;)V
    //   82: aload_3
    //   83: invokevirtual 243	javax/net/ssl/SSLSocket:startHandshake	()V
    //   86: aload_3
    //   87: invokevirtual 247	javax/net/ssl/SSLSocket:getSession	()Ljavax/net/ssl/SSLSession;
    //   90: astore_1
    //   91: aload_1
    //   92: invokestatic 252	okhttp3/Handshake:get	(Ljavax/net/ssl/SSLSession;)Lokhttp3/Handshake;
    //   95: astore 4
    //   97: aload_2
    //   98: invokevirtual 256	okhttp3/Address:hostnameVerifier	()Ljavax/net/ssl/HostnameVerifier;
    //   101: aload_2
    //   102: invokevirtual 204	okhttp3/Address:url	()Lokhttp3/HttpUrl;
    //   105: invokevirtual 209	okhttp3/HttpUrl:host	()Ljava/lang/String;
    //   108: aload_1
    //   109: invokeinterface 262 3 0
    //   114: ifeq +113 -> 227
    //   117: aload_2
    //   118: invokevirtual 266	okhttp3/Address:certificatePinner	()Lokhttp3/CertificatePinner;
    //   121: aload_2
    //   122: invokevirtual 204	okhttp3/Address:url	()Lokhttp3/HttpUrl;
    //   125: invokevirtual 209	okhttp3/HttpUrl:host	()Ljava/lang/String;
    //   128: aload 4
    //   130: invokevirtual 269	okhttp3/Handshake:peerCertificates	()Ljava/util/List;
    //   133: invokevirtual 275	okhttp3/CertificatePinner:check	(Ljava/lang/String;Ljava/util/List;)V
    //   136: aload 6
    //   138: astore_1
    //   139: aload 5
    //   141: invokevirtual 232	okhttp3/ConnectionSpec:supportsTlsExtensions	()Z
    //   144: ifeq +11 -> 155
    //   147: invokestatic 132	okhttp3/internal/platform/Platform:get	()Lokhttp3/internal/platform/Platform;
    //   150: aload_3
    //   151: invokevirtual 279	okhttp3/internal/platform/Platform:getSelectedProtocol	(Ljavax/net/ssl/SSLSocket;)Ljava/lang/String;
    //   154: astore_1
    //   155: aload_0
    //   156: aload_3
    //   157: putfield 281	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   160: aload_0
    //   161: aload_0
    //   162: getfield 281	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   165: invokestatic 141	a/l:b	(Ljava/net/Socket;)La/s;
    //   168: invokestatic 145	a/l:a	(La/s;)La/e;
    //   171: putfield 147	okhttp3/internal/connection/RealConnection:source	La/e;
    //   174: aload_0
    //   175: aload_0
    //   176: getfield 281	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   179: invokestatic 150	a/l:a	(Ljava/net/Socket;)La/r;
    //   182: invokestatic 153	a/l:a	(La/r;)La/d;
    //   185: putfield 155	okhttp3/internal/connection/RealConnection:sink	La/d;
    //   188: aload_0
    //   189: aload 4
    //   191: putfield 283	okhttp3/internal/connection/RealConnection:handshake	Lokhttp3/Handshake;
    //   194: aload_1
    //   195: ifnull +11 -> 206
    //   198: aload_1
    //   199: invokestatic 288	okhttp3/Protocol:get	(Ljava/lang/String;)Lokhttp3/Protocol;
    //   202: astore_1
    //   203: goto +7 -> 210
    //   206: getstatic 291	okhttp3/Protocol:HTTP_1_1	Lokhttp3/Protocol;
    //   209: astore_1
    //   210: aload_0
    //   211: aload_1
    //   212: putfield 293	okhttp3/internal/connection/RealConnection:protocol	Lokhttp3/Protocol;
    //   215: aload_3
    //   216: ifnull +10 -> 226
    //   219: invokestatic 132	okhttp3/internal/platform/Platform:get	()Lokhttp3/internal/platform/Platform;
    //   222: aload_3
    //   223: invokevirtual 297	okhttp3/internal/platform/Platform:afterHandshake	(Ljavax/net/ssl/SSLSocket;)V
    //   226: return
    //   227: aload 4
    //   229: invokevirtual 269	okhttp3/Handshake:peerCertificates	()Ljava/util/List;
    //   232: iconst_0
    //   233: invokeinterface 302 2 0
    //   238: checkcast 304	java/security/cert/X509Certificate
    //   241: astore_1
    //   242: new 306	javax/net/ssl/SSLPeerUnverifiedException
    //   245: astore 4
    //   247: new 172	java/lang/StringBuilder
    //   250: astore 6
    //   252: aload 6
    //   254: invokespecial 173	java/lang/StringBuilder:<init>	()V
    //   257: aload 6
    //   259: ldc_w 308
    //   262: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   265: pop
    //   266: aload 6
    //   268: aload_2
    //   269: invokevirtual 204	okhttp3/Address:url	()Lokhttp3/HttpUrl;
    //   272: invokevirtual 209	okhttp3/HttpUrl:host	()Ljava/lang/String;
    //   275: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   278: pop
    //   279: aload 6
    //   281: ldc_w 310
    //   284: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   287: pop
    //   288: aload 6
    //   290: aload_1
    //   291: invokestatic 314	okhttp3/CertificatePinner:pin	(Ljava/security/cert/Certificate;)Ljava/lang/String;
    //   294: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   297: pop
    //   298: aload 6
    //   300: ldc_w 316
    //   303: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   306: pop
    //   307: aload 6
    //   309: aload_1
    //   310: invokevirtual 320	java/security/cert/X509Certificate:getSubjectDN	()Ljava/security/Principal;
    //   313: invokeinterface 325 1 0
    //   318: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   321: pop
    //   322: aload 6
    //   324: ldc_w 327
    //   327: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   330: pop
    //   331: aload 6
    //   333: aload_1
    //   334: invokestatic 333	okhttp3/internal/tls/OkHostnameVerifier:allSubjectAltNames	(Ljava/security/cert/X509Certificate;)Ljava/util/List;
    //   337: invokevirtual 182	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   340: pop
    //   341: aload 4
    //   343: aload 6
    //   345: invokevirtual 185	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   348: invokespecial 334	javax/net/ssl/SSLPeerUnverifiedException:<init>	(Ljava/lang/String;)V
    //   351: aload 4
    //   353: athrow
    //   354: astore_1
    //   355: goto +62 -> 417
    //   358: astore 4
    //   360: aload_3
    //   361: astore_1
    //   362: aload 4
    //   364: astore_3
    //   365: goto +14 -> 379
    //   368: astore_1
    //   369: aload 4
    //   371: astore_3
    //   372: goto +45 -> 417
    //   375: astore_3
    //   376: aload 5
    //   378: astore_1
    //   379: aload_1
    //   380: astore 4
    //   382: aload_3
    //   383: invokestatic 340	okhttp3/internal/Util:isAndroidGetsocknameError	(Ljava/lang/AssertionError;)Z
    //   386: ifeq +26 -> 412
    //   389: aload_1
    //   390: astore 4
    //   392: new 167	java/io/IOException
    //   395: astore 6
    //   397: aload_1
    //   398: astore 4
    //   400: aload 6
    //   402: aload_3
    //   403: invokespecial 170	java/io/IOException:<init>	(Ljava/lang/Throwable;)V
    //   406: aload_1
    //   407: astore 4
    //   409: aload 6
    //   411: athrow
    //   412: aload_1
    //   413: astore 4
    //   415: aload_3
    //   416: athrow
    //   417: aload_3
    //   418: ifnull +10 -> 428
    //   421: invokestatic 132	okhttp3/internal/platform/Platform:get	()Lokhttp3/internal/platform/Platform;
    //   424: aload_3
    //   425: invokevirtual 297	okhttp3/internal/platform/Platform:afterHandshake	(Ljavax/net/ssl/SSLSocket;)V
    //   428: aload_3
    //   429: invokestatic 344	okhttp3/internal/Util:closeQuietly	(Ljava/net/Socket;)V
    //   432: aload_1
    //   433: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	434	0	this	RealConnection
    //   0	434	1	paramConnectionSpecSelector	ConnectionSpecSelector
    //   7	262	2	localAddress	Address
    //   12	360	3	localObject1	Object
    //   375	54	3	localAssertionError1	AssertionError
    //   14	338	4	localObject2	Object
    //   358	12	4	localAssertionError2	AssertionError
    //   380	34	4	localConnectionSpecSelector	ConnectionSpecSelector
    //   17	360	5	localConnectionSpec	okhttp3.ConnectionSpec
    //   20	390	6	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   49	82	354	finally
    //   82	136	354	finally
    //   139	155	354	finally
    //   155	194	354	finally
    //   198	203	354	finally
    //   206	210	354	finally
    //   210	215	354	finally
    //   227	354	354	finally
    //   49	82	358	java/lang/AssertionError
    //   82	136	358	java/lang/AssertionError
    //   139	155	358	java/lang/AssertionError
    //   155	194	358	java/lang/AssertionError
    //   198	203	358	java/lang/AssertionError
    //   206	210	358	java/lang/AssertionError
    //   210	215	358	java/lang/AssertionError
    //   227	354	358	java/lang/AssertionError
    //   22	49	368	finally
    //   382	389	368	finally
    //   392	397	368	finally
    //   400	406	368	finally
    //   409	412	368	finally
    //   415	417	368	finally
    //   22	49	375	java/lang/AssertionError
  }
  
  private void connectTunnel(int paramInt1, int paramInt2, int paramInt3, Call paramCall, EventListener paramEventListener)
  {
    Request localRequest = createTunnelRequest();
    HttpUrl localHttpUrl = localRequest.url();
    for (int i = 0; i < 21; i++)
    {
      connectSocket(paramInt1, paramInt2, paramCall, paramEventListener);
      localRequest = createTunnel(paramInt2, paramInt3, localRequest, localHttpUrl);
      if (localRequest == null) {
        break;
      }
      Util.closeQuietly(this.rawSocket);
      this.rawSocket = null;
      this.sink = null;
      this.source = null;
      paramEventListener.connectEnd(paramCall, this.route.socketAddress(), this.route.proxy(), null);
    }
  }
  
  private Request createTunnel(int paramInt1, int paramInt2, Request paramRequest, HttpUrl paramHttpUrl)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("CONNECT ");
    ((StringBuilder)localObject).append(Util.hostHeader(paramHttpUrl, true));
    ((StringBuilder)localObject).append(" HTTP/1.1");
    paramHttpUrl = ((StringBuilder)localObject).toString();
    for (;;)
    {
      Http1Codec localHttp1Codec = new Http1Codec(null, null, this.source, this.sink);
      this.source.timeout().timeout(paramInt1, TimeUnit.MILLISECONDS);
      this.sink.timeout().timeout(paramInt2, TimeUnit.MILLISECONDS);
      localHttp1Codec.writeRequest(paramRequest.headers(), paramHttpUrl);
      localHttp1Codec.finishRequest();
      localObject = localHttp1Codec.readResponseHeaders(false).request(paramRequest).build();
      long l1 = HttpHeaders.contentLength((Response)localObject);
      long l2 = l1;
      if (l1 == -1L) {
        l2 = 0L;
      }
      paramRequest = localHttp1Codec.newFixedLengthSource(l2);
      Util.skipAll(paramRequest, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
      paramRequest.close();
      int i = ((Response)localObject).code();
      if (i == 200) {
        break label300;
      }
      if (i != 407) {
        break label262;
      }
      paramRequest = this.route.address().proxyAuthenticator().authenticate(this.route, (Response)localObject);
      if (paramRequest == null) {
        break;
      }
      if ("close".equalsIgnoreCase(((Response)localObject).header("Connection"))) {
        return paramRequest;
      }
    }
    throw new IOException("Failed to authenticate with proxy");
    label262:
    paramRequest = new StringBuilder();
    paramRequest.append("Unexpected response code for CONNECT: ");
    paramRequest.append(((Response)localObject).code());
    throw new IOException(paramRequest.toString());
    label300:
    if ((this.source.b().e()) && (this.sink.b().e())) {
      return null;
    }
    throw new IOException("TLS tunnel buffered too many bytes!");
  }
  
  private Request createTunnelRequest()
  {
    Object localObject1 = new Request.Builder().url(this.route.address().url()).method("CONNECT", null).header("Host", Util.hostHeader(this.route.address().url(), true)).header("Proxy-Connection", "Keep-Alive").header("User-Agent", Version.userAgent()).build();
    Object localObject2 = new Response.Builder().request((Request)localObject1).protocol(Protocol.HTTP_1_1).code(407).message("Preemptive Authenticate").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).receivedResponseAtMillis(-1L).header("Proxy-Authenticate", "OkHttp-Preemptive").build();
    localObject2 = this.route.address().proxyAuthenticator().authenticate(this.route, (Response)localObject2);
    if (localObject2 != null) {
      localObject1 = localObject2;
    }
    return (Request)localObject1;
  }
  
  private void establishProtocol(ConnectionSpecSelector paramConnectionSpecSelector, int paramInt, Call paramCall, EventListener paramEventListener)
  {
    if (this.route.address().sslSocketFactory() == null)
    {
      if (this.route.address().protocols().contains(Protocol.H2_PRIOR_KNOWLEDGE))
      {
        this.socket = this.rawSocket;
        this.protocol = Protocol.H2_PRIOR_KNOWLEDGE;
        startHttp2(paramInt);
        return;
      }
      this.socket = this.rawSocket;
      this.protocol = Protocol.HTTP_1_1;
      return;
    }
    paramEventListener.secureConnectStart(paramCall);
    connectTls(paramConnectionSpecSelector);
    paramEventListener.secureConnectEnd(paramCall, this.handshake);
    if (this.protocol == Protocol.HTTP_2) {
      startHttp2(paramInt);
    }
  }
  
  private void startHttp2(int paramInt)
  {
    this.socket.setSoTimeout(0);
    this.http2Connection = new Http2Connection.Builder(true).socket(this.socket, this.route.address().url().host(), this.source, this.sink).listener(this).pingIntervalMillis(paramInt).build();
    this.http2Connection.start();
  }
  
  public static RealConnection testConnection(ConnectionPool paramConnectionPool, Route paramRoute, Socket paramSocket, long paramLong)
  {
    paramConnectionPool = new RealConnection(paramConnectionPool, paramRoute);
    paramConnectionPool.socket = paramSocket;
    paramConnectionPool.idleAtNanos = paramLong;
    return paramConnectionPool;
  }
  
  public void cancel()
  {
    Util.closeQuietly(this.rawSocket);
  }
  
  /* Error */
  public void connect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, Call arg6, EventListener paramEventListener)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 293	okhttp3/internal/connection/RealConnection:protocol	Lokhttp3/Protocol;
    //   4: ifnonnull +493 -> 497
    //   7: aload_0
    //   8: getfield 61	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   11: invokevirtual 78	okhttp3/Route:address	()Lokhttp3/Address;
    //   14: invokevirtual 613	okhttp3/Address:connectionSpecs	()Ljava/util/List;
    //   17: astore 8
    //   19: new 222	okhttp3/internal/connection/ConnectionSpecSelector
    //   22: dup
    //   23: aload 8
    //   25: invokespecial 616	okhttp3/internal/connection/ConnectionSpecSelector:<init>	(Ljava/util/List;)V
    //   28: astore 9
    //   30: aload_0
    //   31: getfield 61	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   34: invokevirtual 78	okhttp3/Route:address	()Lokhttp3/Address;
    //   37: invokevirtual 200	okhttp3/Address:sslSocketFactory	()Ljavax/net/ssl/SSLSocketFactory;
    //   40: ifnonnull +118 -> 158
    //   43: aload 8
    //   45: getstatic 620	okhttp3/ConnectionSpec:CLEARTEXT	Lokhttp3/ConnectionSpec;
    //   48: invokeinterface 561 2 0
    //   53: ifeq +87 -> 140
    //   56: aload_0
    //   57: getfield 61	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   60: invokevirtual 78	okhttp3/Route:address	()Lokhttp3/Address;
    //   63: invokevirtual 204	okhttp3/Address:url	()Lokhttp3/HttpUrl;
    //   66: invokevirtual 209	okhttp3/HttpUrl:host	()Ljava/lang/String;
    //   69: astore 8
    //   71: invokestatic 132	okhttp3/internal/platform/Platform:get	()Lokhttp3/internal/platform/Platform;
    //   74: aload 8
    //   76: invokevirtual 623	okhttp3/internal/platform/Platform:isCleartextTrafficPermitted	(Ljava/lang/String;)Z
    //   79: ifeq +6 -> 85
    //   82: goto +97 -> 179
    //   85: new 172	java/lang/StringBuilder
    //   88: dup
    //   89: invokespecial 173	java/lang/StringBuilder:<init>	()V
    //   92: astore 6
    //   94: aload 6
    //   96: ldc_w 625
    //   99: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: pop
    //   103: aload 6
    //   105: aload 8
    //   107: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: pop
    //   111: aload 6
    //   113: ldc_w 627
    //   116: invokevirtual 179	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   119: pop
    //   120: new 629	okhttp3/internal/connection/RouteException
    //   123: dup
    //   124: new 631	java/net/UnknownServiceException
    //   127: dup
    //   128: aload 6
    //   130: invokevirtual 185	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   133: invokespecial 632	java/net/UnknownServiceException:<init>	(Ljava/lang/String;)V
    //   136: invokespecial 635	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   139: athrow
    //   140: new 629	okhttp3/internal/connection/RouteException
    //   143: dup
    //   144: new 631	java/net/UnknownServiceException
    //   147: dup
    //   148: ldc_w 637
    //   151: invokespecial 632	java/net/UnknownServiceException:<init>	(Ljava/lang/String;)V
    //   154: invokespecial 635	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   157: athrow
    //   158: aload_0
    //   159: getfield 61	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   162: invokevirtual 78	okhttp3/Route:address	()Lokhttp3/Address;
    //   165: invokevirtual 236	okhttp3/Address:protocols	()Ljava/util/List;
    //   168: getstatic 558	okhttp3/Protocol:H2_PRIOR_KNOWLEDGE	Lokhttp3/Protocol;
    //   171: invokeinterface 561 2 0
    //   176: ifne +303 -> 479
    //   179: aconst_null
    //   180: astore 10
    //   182: aload_0
    //   183: getfield 61	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   186: invokevirtual 640	okhttp3/Route:requiresTunnel	()Z
    //   189: ifeq +31 -> 220
    //   192: aload_0
    //   193: iload_1
    //   194: iload_2
    //   195: iload_3
    //   196: aload 6
    //   198: aload 7
    //   200: invokespecial 642	okhttp3/internal/connection/RealConnection:connectTunnel	(IIILokhttp3/Call;Lokhttp3/EventListener;)V
    //   203: aload_0
    //   204: getfield 112	okhttp3/internal/connection/RealConnection:rawSocket	Ljava/net/Socket;
    //   207: astore 8
    //   209: aload 8
    //   211: ifnonnull +6 -> 217
    //   214: goto +53 -> 267
    //   217: goto +13 -> 230
    //   220: aload_0
    //   221: iload_1
    //   222: iload_2
    //   223: aload 6
    //   225: aload 7
    //   227: invokespecial 355	okhttp3/internal/connection/RealConnection:connectSocket	(IILokhttp3/Call;Lokhttp3/EventListener;)V
    //   230: aload_0
    //   231: aload 9
    //   233: iload 4
    //   235: aload 6
    //   237: aload 7
    //   239: invokespecial 644	okhttp3/internal/connection/RealConnection:establishProtocol	(Lokhttp3/internal/connection/ConnectionSpecSelector;ILokhttp3/Call;Lokhttp3/EventListener;)V
    //   242: aload 7
    //   244: aload 6
    //   246: aload_0
    //   247: getfield 61	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   250: invokevirtual 116	okhttp3/Route:socketAddress	()Ljava/net/InetSocketAddress;
    //   253: aload_0
    //   254: getfield 61	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   257: invokevirtual 74	okhttp3/Route:proxy	()Ljava/net/Proxy;
    //   260: aload_0
    //   261: getfield 293	okhttp3/internal/connection/RealConnection:protocol	Lokhttp3/Protocol;
    //   264: invokevirtual 363	okhttp3/EventListener:connectEnd	(Lokhttp3/Call;Ljava/net/InetSocketAddress;Ljava/net/Proxy;Lokhttp3/Protocol;)V
    //   267: aload_0
    //   268: getfield 61	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   271: invokevirtual 640	okhttp3/Route:requiresTunnel	()Z
    //   274: ifeq +31 -> 305
    //   277: aload_0
    //   278: getfield 112	okhttp3/internal/connection/RealConnection:rawSocket	Ljava/net/Socket;
    //   281: ifnull +6 -> 287
    //   284: goto +21 -> 305
    //   287: new 629	okhttp3/internal/connection/RouteException
    //   290: dup
    //   291: new 646	java/net/ProtocolException
    //   294: dup
    //   295: ldc_w 648
    //   298: invokespecial 649	java/net/ProtocolException:<init>	(Ljava/lang/String;)V
    //   301: invokespecial 635	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   304: athrow
    //   305: aload_0
    //   306: getfield 598	okhttp3/internal/connection/RealConnection:http2Connection	Lokhttp3/internal/http2/Http2Connection;
    //   309: ifnull +37 -> 346
    //   312: aload_0
    //   313: getfield 59	okhttp3/internal/connection/RealConnection:connectionPool	Lokhttp3/ConnectionPool;
    //   316: astore 6
    //   318: aload 6
    //   320: monitorenter
    //   321: aload_0
    //   322: aload_0
    //   323: getfield 598	okhttp3/internal/connection/RealConnection:http2Connection	Lokhttp3/internal/http2/Http2Connection;
    //   326: invokevirtual 652	okhttp3/internal/http2/Http2Connection:maxConcurrentStreams	()I
    //   329: putfield 48	okhttp3/internal/connection/RealConnection:allocationLimit	I
    //   332: aload 6
    //   334: monitorexit
    //   335: goto +11 -> 346
    //   338: astore 7
    //   340: aload 6
    //   342: monitorexit
    //   343: aload 7
    //   345: athrow
    //   346: return
    //   347: astore 8
    //   349: goto +10 -> 359
    //   352: astore 8
    //   354: goto +5 -> 359
    //   357: astore 8
    //   359: aload_0
    //   360: getfield 281	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   363: invokestatic 344	okhttp3/internal/Util:closeQuietly	(Ljava/net/Socket;)V
    //   366: aload_0
    //   367: getfield 112	okhttp3/internal/connection/RealConnection:rawSocket	Ljava/net/Socket;
    //   370: invokestatic 344	okhttp3/internal/Util:closeQuietly	(Ljava/net/Socket;)V
    //   373: aload_0
    //   374: aconst_null
    //   375: putfield 281	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   378: aload_0
    //   379: aconst_null
    //   380: putfield 112	okhttp3/internal/connection/RealConnection:rawSocket	Ljava/net/Socket;
    //   383: aload_0
    //   384: aconst_null
    //   385: putfield 147	okhttp3/internal/connection/RealConnection:source	La/e;
    //   388: aload_0
    //   389: aconst_null
    //   390: putfield 155	okhttp3/internal/connection/RealConnection:sink	La/d;
    //   393: aload_0
    //   394: aconst_null
    //   395: putfield 283	okhttp3/internal/connection/RealConnection:handshake	Lokhttp3/Handshake;
    //   398: aload_0
    //   399: aconst_null
    //   400: putfield 293	okhttp3/internal/connection/RealConnection:protocol	Lokhttp3/Protocol;
    //   403: aload_0
    //   404: aconst_null
    //   405: putfield 598	okhttp3/internal/connection/RealConnection:http2Connection	Lokhttp3/internal/http2/Http2Connection;
    //   408: aload 7
    //   410: aload 6
    //   412: aload_0
    //   413: getfield 61	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   416: invokevirtual 116	okhttp3/Route:socketAddress	()Ljava/net/InetSocketAddress;
    //   419: aload_0
    //   420: getfield 61	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   423: invokevirtual 74	okhttp3/Route:proxy	()Ljava/net/Proxy;
    //   426: aconst_null
    //   427: aload 8
    //   429: invokevirtual 656	okhttp3/EventListener:connectFailed	(Lokhttp3/Call;Ljava/net/InetSocketAddress;Ljava/net/Proxy;Lokhttp3/Protocol;Ljava/io/IOException;)V
    //   432: aload 10
    //   434: ifnonnull +17 -> 451
    //   437: new 629	okhttp3/internal/connection/RouteException
    //   440: dup
    //   441: aload 8
    //   443: invokespecial 635	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   446: astore 10
    //   448: goto +10 -> 458
    //   451: aload 10
    //   453: aload 8
    //   455: invokevirtual 659	okhttp3/internal/connection/RouteException:addConnectException	(Ljava/io/IOException;)V
    //   458: iload 5
    //   460: ifeq +16 -> 476
    //   463: aload 9
    //   465: aload 8
    //   467: invokevirtual 663	okhttp3/internal/connection/ConnectionSpecSelector:connectionFailed	(Ljava/io/IOException;)Z
    //   470: ifeq +6 -> 476
    //   473: goto -291 -> 182
    //   476: aload 10
    //   478: athrow
    //   479: new 629	okhttp3/internal/connection/RouteException
    //   482: dup
    //   483: new 631	java/net/UnknownServiceException
    //   486: dup
    //   487: ldc_w 665
    //   490: invokespecial 632	java/net/UnknownServiceException:<init>	(Ljava/lang/String;)V
    //   493: invokespecial 635	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   496: athrow
    //   497: new 667	java/lang/IllegalStateException
    //   500: dup
    //   501: ldc_w 669
    //   504: invokespecial 670	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   507: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	508	0	this	RealConnection
    //   0	508	1	paramInt1	int
    //   0	508	2	paramInt2	int
    //   0	508	3	paramInt3	int
    //   0	508	4	paramInt4	int
    //   0	508	5	paramBoolean	boolean
    //   0	508	7	paramEventListener	EventListener
    //   17	193	8	localObject	Object
    //   347	1	8	localIOException1	IOException
    //   352	1	8	localIOException2	IOException
    //   357	109	8	localIOException3	IOException
    //   28	436	9	localConnectionSpecSelector	ConnectionSpecSelector
    //   180	297	10	localRouteException	RouteException
    // Exception table:
    //   from	to	target	type
    //   321	335	338	finally
    //   340	343	338	finally
    //   230	267	347	java/io/IOException
    //   220	230	352	java/io/IOException
    //   182	209	357	java/io/IOException
  }
  
  public Handshake handshake()
  {
    return this.handshake;
  }
  
  public boolean isEligible(Address paramAddress, @Nullable Route paramRoute)
  {
    if ((this.allocations.size() < this.allocationLimit) && (!this.noNewStreams))
    {
      if (!Internal.instance.equalsNonHost(this.route.address(), paramAddress)) {
        return false;
      }
      if (paramAddress.url().host().equals(route().address().url().host())) {
        return true;
      }
      if (this.http2Connection == null) {
        return false;
      }
      if (paramRoute == null) {
        return false;
      }
      if (paramRoute.proxy().type() != Proxy.Type.DIRECT) {
        return false;
      }
      if (this.route.proxy().type() != Proxy.Type.DIRECT) {
        return false;
      }
      if (!this.route.socketAddress().equals(paramRoute.socketAddress())) {
        return false;
      }
      if (paramRoute.address().hostnameVerifier() != OkHostnameVerifier.INSTANCE) {
        return false;
      }
      if (!supportsUrl(paramAddress.url())) {
        return false;
      }
      try
      {
        paramAddress.certificatePinner().check(paramAddress.url().host(), handshake().peerCertificates());
        return true;
      }
      catch (SSLPeerUnverifiedException paramAddress)
      {
        return false;
      }
    }
    return false;
  }
  
  /* Error */
  public boolean isHealthy(boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 281	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   4: invokevirtual 713	java/net/Socket:isClosed	()Z
    //   7: ifne +112 -> 119
    //   10: aload_0
    //   11: getfield 281	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   14: invokevirtual 716	java/net/Socket:isInputShutdown	()Z
    //   17: ifne +102 -> 119
    //   20: aload_0
    //   21: getfield 281	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   24: invokevirtual 719	java/net/Socket:isOutputShutdown	()Z
    //   27: ifeq +6 -> 33
    //   30: goto +89 -> 119
    //   33: aload_0
    //   34: getfield 598	okhttp3/internal/connection/RealConnection:http2Connection	Lokhttp3/internal/http2/Http2Connection;
    //   37: astore_2
    //   38: aload_2
    //   39: ifnull +10 -> 49
    //   42: aload_2
    //   43: invokevirtual 722	okhttp3/internal/http2/Http2Connection:isShutdown	()Z
    //   46: iconst_1
    //   47: ixor
    //   48: ireturn
    //   49: iload_1
    //   50: ifeq +67 -> 117
    //   53: aload_0
    //   54: getfield 281	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   57: invokevirtual 725	java/net/Socket:getSoTimeout	()I
    //   60: istore_3
    //   61: aload_0
    //   62: getfield 281	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   65: iconst_1
    //   66: invokevirtual 126	java/net/Socket:setSoTimeout	(I)V
    //   69: aload_0
    //   70: getfield 147	okhttp3/internal/connection/RealConnection:source	La/e;
    //   73: invokeinterface 726 1 0
    //   78: istore_1
    //   79: iload_1
    //   80: ifeq +13 -> 93
    //   83: aload_0
    //   84: getfield 281	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   87: iload_3
    //   88: invokevirtual 126	java/net/Socket:setSoTimeout	(I)V
    //   91: iconst_0
    //   92: ireturn
    //   93: aload_0
    //   94: getfield 281	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   97: iload_3
    //   98: invokevirtual 126	java/net/Socket:setSoTimeout	(I)V
    //   101: iconst_1
    //   102: ireturn
    //   103: astore_2
    //   104: aload_0
    //   105: getfield 281	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   108: iload_3
    //   109: invokevirtual 126	java/net/Socket:setSoTimeout	(I)V
    //   112: aload_2
    //   113: athrow
    //   114: astore_2
    //   115: iconst_0
    //   116: ireturn
    //   117: iconst_1
    //   118: ireturn
    //   119: iconst_0
    //   120: ireturn
    //   121: astore_2
    //   122: goto -5 -> 117
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	125	0	this	RealConnection
    //   0	125	1	paramBoolean	boolean
    //   37	6	2	localHttp2Connection	Http2Connection
    //   103	10	2	localObject	Object
    //   114	1	2	localIOException	IOException
    //   121	1	2	localSocketTimeoutException	java.net.SocketTimeoutException
    //   60	49	3	i	int
    // Exception table:
    //   from	to	target	type
    //   61	79	103	finally
    //   53	61	114	java/io/IOException
    //   83	91	114	java/io/IOException
    //   93	101	114	java/io/IOException
    //   104	114	114	java/io/IOException
    //   53	61	121	java/net/SocketTimeoutException
    //   83	91	121	java/net/SocketTimeoutException
    //   93	101	121	java/net/SocketTimeoutException
    //   104	114	121	java/net/SocketTimeoutException
  }
  
  public boolean isMultiplexed()
  {
    boolean bool;
    if (this.http2Connection != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public HttpCodec newCodec(OkHttpClient paramOkHttpClient, Interceptor.Chain paramChain, StreamAllocation paramStreamAllocation)
  {
    Http2Connection localHttp2Connection = this.http2Connection;
    if (localHttp2Connection != null) {
      return new Http2Codec(paramOkHttpClient, paramChain, paramStreamAllocation, localHttp2Connection);
    }
    this.socket.setSoTimeout(paramChain.readTimeoutMillis());
    this.source.timeout().timeout(paramChain.readTimeoutMillis(), TimeUnit.MILLISECONDS);
    this.sink.timeout().timeout(paramChain.writeTimeoutMillis(), TimeUnit.MILLISECONDS);
    return new Http1Codec(paramOkHttpClient, paramStreamAllocation, this.source, this.sink);
  }
  
  public RealWebSocket.Streams newWebSocketStreams(final StreamAllocation paramStreamAllocation)
  {
    new RealWebSocket.Streams(true, this.source, this.sink)
    {
      public void close()
      {
        StreamAllocation localStreamAllocation = paramStreamAllocation;
        localStreamAllocation.streamFinished(true, localStreamAllocation.codec(), -1L, null);
      }
    };
  }
  
  public void onSettings(Http2Connection paramHttp2Connection)
  {
    synchronized (this.connectionPool)
    {
      this.allocationLimit = paramHttp2Connection.maxConcurrentStreams();
      return;
    }
  }
  
  public void onStream(Http2Stream paramHttp2Stream)
  {
    paramHttp2Stream.close(ErrorCode.REFUSED_STREAM);
  }
  
  public Protocol protocol()
  {
    return this.protocol;
  }
  
  public Route route()
  {
    return this.route;
  }
  
  public Socket socket()
  {
    return this.socket;
  }
  
  public boolean supportsUrl(HttpUrl paramHttpUrl)
  {
    if (paramHttpUrl.port() != this.route.address().url().port()) {
      return false;
    }
    boolean bool1 = paramHttpUrl.host().equals(this.route.address().url().host());
    boolean bool2 = true;
    if (!bool1)
    {
      if ((this.handshake == null) || (!OkHostnameVerifier.INSTANCE.verify(paramHttpUrl.host(), (X509Certificate)this.handshake.peerCertificates().get(0)))) {
        bool2 = false;
      }
      return bool2;
    }
    return true;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Connection{");
    localStringBuilder.append(this.route.address().url().host());
    localStringBuilder.append(":");
    localStringBuilder.append(this.route.address().url().port());
    localStringBuilder.append(", proxy=");
    localStringBuilder.append(this.route.proxy());
    localStringBuilder.append(" hostAddress=");
    localStringBuilder.append(this.route.socketAddress());
    localStringBuilder.append(" cipherSuite=");
    Object localObject = this.handshake;
    if (localObject != null) {
      localObject = ((Handshake)localObject).cipherSuite();
    } else {
      localObject = "none";
    }
    localStringBuilder.append(localObject);
    localStringBuilder.append(" protocol=");
    localStringBuilder.append(this.protocol);
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
}


/* Location:              ~/okhttp3/internal/connection/RealConnection.class
 *
 * Reversed by:           J
 */