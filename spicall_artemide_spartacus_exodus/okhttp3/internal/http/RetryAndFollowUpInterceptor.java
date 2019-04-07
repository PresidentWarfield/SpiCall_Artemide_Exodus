package okhttp3.internal.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Address;
import okhttp3.Authenticator;
import okhttp3.CertificatePinner;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.connection.StreamAllocation;

public final class RetryAndFollowUpInterceptor
  implements Interceptor
{
  private static final int MAX_FOLLOW_UPS = 20;
  private Object callStackTrace;
  private volatile boolean canceled;
  private final OkHttpClient client;
  private final boolean forWebSocket;
  private volatile StreamAllocation streamAllocation;
  
  public RetryAndFollowUpInterceptor(OkHttpClient paramOkHttpClient, boolean paramBoolean)
  {
    this.client = paramOkHttpClient;
    this.forWebSocket = paramBoolean;
  }
  
  private Address createAddress(HttpUrl paramHttpUrl)
  {
    SSLSocketFactory localSSLSocketFactory;
    Object localObject1;
    Object localObject2;
    if (paramHttpUrl.isHttps())
    {
      localSSLSocketFactory = this.client.sslSocketFactory();
      localObject1 = this.client.hostnameVerifier();
      localObject2 = this.client.certificatePinner();
    }
    else
    {
      localSSLSocketFactory = null;
      localObject1 = localSSLSocketFactory;
      localObject2 = localObject1;
    }
    return new Address(paramHttpUrl.host(), paramHttpUrl.port(), this.client.dns(), this.client.socketFactory(), localSSLSocketFactory, (HostnameVerifier)localObject1, (CertificatePinner)localObject2, this.client.proxyAuthenticator(), this.client.proxy(), this.client.protocols(), this.client.connectionSpecs(), this.client.proxySelector());
  }
  
  private Request followUpRequest(Response paramResponse, Route paramRoute)
  {
    if (paramResponse != null)
    {
      int i = paramResponse.code();
      String str = paramResponse.request().method();
      Proxy localProxy = null;
      switch (i)
      {
      default: 
        return null;
      case 503: 
        if ((paramResponse.priorResponse() != null) && (paramResponse.priorResponse().code() == 503)) {
          return null;
        }
        if (retryAfter(paramResponse, Integer.MAX_VALUE) == 0) {
          return paramResponse.request();
        }
        return null;
      case 408: 
        if (!this.client.retryOnConnectionFailure()) {
          return null;
        }
        if ((paramResponse.request().body() instanceof UnrepeatableRequestBody)) {
          return null;
        }
        if ((paramResponse.priorResponse() != null) && (paramResponse.priorResponse().code() == 408)) {
          return null;
        }
        if (retryAfter(paramResponse, 0) > 0) {
          return null;
        }
        return paramResponse.request();
      case 407: 
        if (paramRoute != null) {
          localProxy = paramRoute.proxy();
        } else {
          localProxy = this.client.proxy();
        }
        if (localProxy.type() == Proxy.Type.HTTP) {
          return this.client.proxyAuthenticator().authenticate(paramRoute, paramResponse);
        }
        throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
      case 401: 
        return this.client.authenticator().authenticate(paramRoute, paramResponse);
      case 307: 
      case 308: 
        if ((!str.equals("GET")) && (!str.equals("HEAD"))) {
          return null;
        }
        break;
      }
      if (!this.client.followRedirects()) {
        return null;
      }
      paramRoute = paramResponse.header("Location");
      if (paramRoute == null) {
        return null;
      }
      HttpUrl localHttpUrl = paramResponse.request().url().resolve(paramRoute);
      if (localHttpUrl == null) {
        return null;
      }
      if ((!localHttpUrl.scheme().equals(paramResponse.request().url().scheme())) && (!this.client.followSslRedirects())) {
        return null;
      }
      Request.Builder localBuilder = paramResponse.request().newBuilder();
      if (HttpMethod.permitsRequestBody(str))
      {
        boolean bool = HttpMethod.redirectsWithBody(str);
        if (HttpMethod.redirectsToGet(str))
        {
          localBuilder.method("GET", null);
        }
        else
        {
          paramRoute = localProxy;
          if (bool) {
            paramRoute = paramResponse.request().body();
          }
          localBuilder.method(str, paramRoute);
        }
        if (!bool)
        {
          localBuilder.removeHeader("Transfer-Encoding");
          localBuilder.removeHeader("Content-Length");
          localBuilder.removeHeader("Content-Type");
        }
      }
      if (!sameConnection(paramResponse, localHttpUrl)) {
        localBuilder.removeHeader("Authorization");
      }
      return localBuilder.url(localHttpUrl).build();
    }
    throw new IllegalStateException();
  }
  
  private boolean isRecoverable(IOException paramIOException, boolean paramBoolean)
  {
    boolean bool1 = paramIOException instanceof ProtocolException;
    boolean bool2 = false;
    if (bool1) {
      return false;
    }
    if ((paramIOException instanceof InterruptedIOException))
    {
      bool1 = bool2;
      if ((paramIOException instanceof SocketTimeoutException))
      {
        bool1 = bool2;
        if (!paramBoolean) {
          bool1 = true;
        }
      }
      return bool1;
    }
    if (((paramIOException instanceof SSLHandshakeException)) && ((paramIOException.getCause() instanceof CertificateException))) {
      return false;
    }
    return !(paramIOException instanceof SSLPeerUnverifiedException);
  }
  
  private boolean recover(IOException paramIOException, StreamAllocation paramStreamAllocation, boolean paramBoolean, Request paramRequest)
  {
    paramStreamAllocation.streamFailed(paramIOException);
    if (!this.client.retryOnConnectionFailure()) {
      return false;
    }
    if ((paramBoolean) && ((paramRequest.body() instanceof UnrepeatableRequestBody))) {
      return false;
    }
    if (!isRecoverable(paramIOException, paramBoolean)) {
      return false;
    }
    return paramStreamAllocation.hasMoreRoutes();
  }
  
  private int retryAfter(Response paramResponse, int paramInt)
  {
    paramResponse = paramResponse.header("Retry-After");
    if (paramResponse == null) {
      return paramInt;
    }
    if (paramResponse.matches("\\d+")) {
      return Integer.valueOf(paramResponse).intValue();
    }
    return Integer.MAX_VALUE;
  }
  
  private boolean sameConnection(Response paramResponse, HttpUrl paramHttpUrl)
  {
    paramResponse = paramResponse.request().url();
    boolean bool;
    if ((paramResponse.host().equals(paramHttpUrl.host())) && (paramResponse.port() == paramHttpUrl.port()) && (paramResponse.scheme().equals(paramHttpUrl.scheme()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void cancel()
  {
    this.canceled = true;
    StreamAllocation localStreamAllocation = this.streamAllocation;
    if (localStreamAllocation != null) {
      localStreamAllocation.cancel();
    }
  }
  
  /* Error */
  public Response intercept(okhttp3.Interceptor.Chain paramChain)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokeinterface 295 1 0
    //   6: astore_2
    //   7: aload_1
    //   8: checkcast 297	okhttp3/internal/http/RealInterceptorChain
    //   11: astore_3
    //   12: aload_3
    //   13: invokevirtual 301	okhttp3/internal/http/RealInterceptorChain:call	()Lokhttp3/Call;
    //   16: astore 4
    //   18: aload_3
    //   19: invokevirtual 305	okhttp3/internal/http/RealInterceptorChain:eventListener	()Lokhttp3/EventListener;
    //   22: astore 5
    //   24: new 256	okhttp3/internal/connection/StreamAllocation
    //   27: dup
    //   28: aload_0
    //   29: getfield 25	okhttp3/internal/http/RetryAndFollowUpInterceptor:client	Lokhttp3/OkHttpClient;
    //   32: invokevirtual 309	okhttp3/OkHttpClient:connectionPool	()Lokhttp3/ConnectionPool;
    //   35: aload_0
    //   36: aload_2
    //   37: invokevirtual 178	okhttp3/Request:url	()Lokhttp3/HttpUrl;
    //   40: invokespecial 311	okhttp3/internal/http/RetryAndFollowUpInterceptor:createAddress	(Lokhttp3/HttpUrl;)Lokhttp3/Address;
    //   43: aload 4
    //   45: aload 5
    //   47: aload_0
    //   48: getfield 313	okhttp3/internal/http/RetryAndFollowUpInterceptor:callStackTrace	Ljava/lang/Object;
    //   51: invokespecial 316	okhttp3/internal/connection/StreamAllocation:<init>	(Lokhttp3/ConnectionPool;Lokhttp3/Address;Lokhttp3/Call;Lokhttp3/EventListener;Ljava/lang/Object;)V
    //   54: astore 6
    //   56: aload_0
    //   57: aload 6
    //   59: putfield 286	okhttp3/internal/http/RetryAndFollowUpInterceptor:streamAllocation	Lokhttp3/internal/connection/StreamAllocation;
    //   62: aconst_null
    //   63: astore 7
    //   65: iconst_0
    //   66: istore 8
    //   68: aload_2
    //   69: astore_1
    //   70: aload_0
    //   71: getfield 284	okhttp3/internal/http/RetryAndFollowUpInterceptor:canceled	Z
    //   74: ifne +357 -> 431
    //   77: aload_3
    //   78: aload_1
    //   79: aload 6
    //   81: aconst_null
    //   82: aconst_null
    //   83: invokevirtual 320	okhttp3/internal/http/RealInterceptorChain:proceed	(Lokhttp3/Request;Lokhttp3/internal/connection/StreamAllocation;Lokhttp3/internal/http/HttpCodec;Lokhttp3/internal/connection/RealConnection;)Lokhttp3/Response;
    //   86: astore_2
    //   87: aload_2
    //   88: astore_1
    //   89: aload 7
    //   91: ifnull +26 -> 117
    //   94: aload_2
    //   95: invokevirtual 323	okhttp3/Response:newBuilder	()Lokhttp3/Response$Builder;
    //   98: aload 7
    //   100: invokevirtual 323	okhttp3/Response:newBuilder	()Lokhttp3/Response$Builder;
    //   103: aconst_null
    //   104: invokevirtual 328	okhttp3/Response$Builder:body	(Lokhttp3/ResponseBody;)Lokhttp3/Response$Builder;
    //   107: invokevirtual 330	okhttp3/Response$Builder:build	()Lokhttp3/Response;
    //   110: invokevirtual 333	okhttp3/Response$Builder:priorResponse	(Lokhttp3/Response;)Lokhttp3/Response$Builder;
    //   113: invokevirtual 330	okhttp3/Response$Builder:build	()Lokhttp3/Response;
    //   116: astore_1
    //   117: aload_0
    //   118: aload_1
    //   119: aload 6
    //   121: invokevirtual 337	okhttp3/internal/connection/StreamAllocation:route	()Lokhttp3/Route;
    //   124: invokespecial 339	okhttp3/internal/http/RetryAndFollowUpInterceptor:followUpRequest	(Lokhttp3/Response;Lokhttp3/Route;)Lokhttp3/Request;
    //   127: astore_2
    //   128: aload_2
    //   129: ifnonnull +10 -> 139
    //   132: aload 6
    //   134: invokevirtual 342	okhttp3/internal/connection/StreamAllocation:release	()V
    //   137: aload_1
    //   138: areturn
    //   139: aload_1
    //   140: invokevirtual 345	okhttp3/Response:body	()Lokhttp3/ResponseBody;
    //   143: invokestatic 351	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   146: iinc 8 1
    //   149: iload 8
    //   151: bipush 20
    //   153: if_icmpgt +154 -> 307
    //   156: aload_2
    //   157: invokevirtual 122	okhttp3/Request:body	()Lokhttp3/RequestBody;
    //   160: instanceof 124
    //   163: ifne +124 -> 287
    //   166: aload_0
    //   167: aload_1
    //   168: aload_2
    //   169: invokevirtual 178	okhttp3/Request:url	()Lokhttp3/HttpUrl;
    //   172: invokespecial 223	okhttp3/internal/http/RetryAndFollowUpInterceptor:sameConnection	(Lokhttp3/Response;Lokhttp3/HttpUrl;)Z
    //   175: ifne +49 -> 224
    //   178: aload 6
    //   180: invokevirtual 342	okhttp3/internal/connection/StreamAllocation:release	()V
    //   183: new 256	okhttp3/internal/connection/StreamAllocation
    //   186: dup
    //   187: aload_0
    //   188: getfield 25	okhttp3/internal/http/RetryAndFollowUpInterceptor:client	Lokhttp3/OkHttpClient;
    //   191: invokevirtual 309	okhttp3/OkHttpClient:connectionPool	()Lokhttp3/ConnectionPool;
    //   194: aload_0
    //   195: aload_2
    //   196: invokevirtual 178	okhttp3/Request:url	()Lokhttp3/HttpUrl;
    //   199: invokespecial 311	okhttp3/internal/http/RetryAndFollowUpInterceptor:createAddress	(Lokhttp3/HttpUrl;)Lokhttp3/Address;
    //   202: aload 4
    //   204: aload 5
    //   206: aload_0
    //   207: getfield 313	okhttp3/internal/http/RetryAndFollowUpInterceptor:callStackTrace	Ljava/lang/Object;
    //   210: invokespecial 316	okhttp3/internal/connection/StreamAllocation:<init>	(Lokhttp3/ConnectionPool;Lokhttp3/Address;Lokhttp3/Call;Lokhttp3/EventListener;Ljava/lang/Object;)V
    //   213: astore 6
    //   215: aload_0
    //   216: aload 6
    //   218: putfield 286	okhttp3/internal/http/RetryAndFollowUpInterceptor:streamAllocation	Lokhttp3/internal/connection/StreamAllocation;
    //   221: goto +11 -> 232
    //   224: aload 6
    //   226: invokevirtual 355	okhttp3/internal/connection/StreamAllocation:codec	()Lokhttp3/internal/http/HttpCodec;
    //   229: ifnonnull +11 -> 240
    //   232: aload_1
    //   233: astore 7
    //   235: aload_2
    //   236: astore_1
    //   237: goto -167 -> 70
    //   240: new 357	java/lang/StringBuilder
    //   243: dup
    //   244: invokespecial 358	java/lang/StringBuilder:<init>	()V
    //   247: astore 6
    //   249: aload 6
    //   251: ldc_w 360
    //   254: invokevirtual 364	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   257: pop
    //   258: aload 6
    //   260: aload_1
    //   261: invokevirtual 367	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   264: pop
    //   265: aload 6
    //   267: ldc_w 369
    //   270: invokevirtual 364	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   273: pop
    //   274: new 233	java/lang/IllegalStateException
    //   277: dup
    //   278: aload 6
    //   280: invokevirtual 372	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   283: invokespecial 373	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   286: athrow
    //   287: aload 6
    //   289: invokevirtual 342	okhttp3/internal/connection/StreamAllocation:release	()V
    //   292: new 375	java/net/HttpRetryException
    //   295: dup
    //   296: ldc_w 377
    //   299: aload_1
    //   300: invokevirtual 97	okhttp3/Response:code	()I
    //   303: invokespecial 380	java/net/HttpRetryException:<init>	(Ljava/lang/String;I)V
    //   306: athrow
    //   307: aload 6
    //   309: invokevirtual 342	okhttp3/internal/connection/StreamAllocation:release	()V
    //   312: new 357	java/lang/StringBuilder
    //   315: dup
    //   316: invokespecial 358	java/lang/StringBuilder:<init>	()V
    //   319: astore_1
    //   320: aload_1
    //   321: ldc_w 382
    //   324: invokevirtual 364	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   327: pop
    //   328: aload_1
    //   329: iload 8
    //   331: invokevirtual 385	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   334: pop
    //   335: new 147	java/net/ProtocolException
    //   338: dup
    //   339: aload_1
    //   340: invokevirtual 372	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   343: invokespecial 152	java/net/ProtocolException:<init>	(Ljava/lang/String;)V
    //   346: athrow
    //   347: astore_1
    //   348: aload 6
    //   350: invokevirtual 342	okhttp3/internal/connection/StreamAllocation:release	()V
    //   353: aload_1
    //   354: athrow
    //   355: astore_1
    //   356: goto +62 -> 418
    //   359: astore_2
    //   360: aload_2
    //   361: instanceof 387
    //   364: ifne +9 -> 373
    //   367: iconst_1
    //   368: istore 9
    //   370: goto +6 -> 376
    //   373: iconst_0
    //   374: istore 9
    //   376: aload_0
    //   377: aload_2
    //   378: aload 6
    //   380: iload 9
    //   382: aload_1
    //   383: invokespecial 389	okhttp3/internal/http/RetryAndFollowUpInterceptor:recover	(Ljava/io/IOException;Lokhttp3/internal/connection/StreamAllocation;ZLokhttp3/Request;)Z
    //   386: ifeq +6 -> 392
    //   389: goto -319 -> 70
    //   392: aload_2
    //   393: athrow
    //   394: astore_2
    //   395: aload_0
    //   396: aload_2
    //   397: invokevirtual 393	okhttp3/internal/connection/RouteException:getLastConnectException	()Ljava/io/IOException;
    //   400: aload 6
    //   402: iconst_0
    //   403: aload_1
    //   404: invokespecial 389	okhttp3/internal/http/RetryAndFollowUpInterceptor:recover	(Ljava/io/IOException;Lokhttp3/internal/connection/StreamAllocation;ZLokhttp3/Request;)Z
    //   407: ifeq +6 -> 413
    //   410: goto -340 -> 70
    //   413: aload_2
    //   414: invokevirtual 396	okhttp3/internal/connection/RouteException:getFirstConnectException	()Ljava/io/IOException;
    //   417: athrow
    //   418: aload 6
    //   420: aconst_null
    //   421: invokevirtual 260	okhttp3/internal/connection/StreamAllocation:streamFailed	(Ljava/io/IOException;)V
    //   424: aload 6
    //   426: invokevirtual 342	okhttp3/internal/connection/StreamAllocation:release	()V
    //   429: aload_1
    //   430: athrow
    //   431: aload 6
    //   433: invokevirtual 342	okhttp3/internal/connection/StreamAllocation:release	()V
    //   436: new 244	java/io/IOException
    //   439: dup
    //   440: ldc_w 398
    //   443: invokespecial 399	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   446: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	447	0	this	RetryAndFollowUpInterceptor
    //   0	447	1	paramChain	okhttp3.Interceptor.Chain
    //   6	230	2	localObject1	Object
    //   359	34	2	localIOException	IOException
    //   394	20	2	localRouteException	okhttp3.internal.connection.RouteException
    //   11	67	3	localRealInterceptorChain	RealInterceptorChain
    //   16	187	4	localCall	okhttp3.Call
    //   22	183	5	localEventListener	okhttp3.EventListener
    //   54	378	6	localObject2	Object
    //   63	171	7	localChain	okhttp3.Interceptor.Chain
    //   66	264	8	i	int
    //   368	13	9	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   117	128	347	java/io/IOException
    //   77	87	355	finally
    //   360	367	355	finally
    //   376	389	355	finally
    //   392	394	355	finally
    //   395	410	355	finally
    //   413	418	355	finally
    //   77	87	359	java/io/IOException
    //   77	87	394	okhttp3/internal/connection/RouteException
  }
  
  public boolean isCanceled()
  {
    return this.canceled;
  }
  
  public void setCallStackTrace(Object paramObject)
  {
    this.callStackTrace = paramObject;
  }
  
  public StreamAllocation streamAllocation()
  {
    return this.streamAllocation;
  }
}


/* Location:              ~/okhttp3/internal/http/RetryAndFollowUpInterceptor.class
 *
 * Reversed by:           J
 */