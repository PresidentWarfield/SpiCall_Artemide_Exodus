package okhttp3.internal.http;

import a.c;
import a.d;
import a.g;
import a.l;
import a.r;
import java.net.ProtocolException;
import okhttp3.EventListener;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;

public final class CallServerInterceptor
  implements Interceptor
{
  private final boolean forWebSocket;
  
  public CallServerInterceptor(boolean paramBoolean)
  {
    this.forWebSocket = paramBoolean;
  }
  
  public Response intercept(Interceptor.Chain paramChain)
  {
    RealInterceptorChain localRealInterceptorChain = (RealInterceptorChain)paramChain;
    HttpCodec localHttpCodec = localRealInterceptorChain.httpStream();
    StreamAllocation localStreamAllocation = localRealInterceptorChain.streamAllocation();
    RealConnection localRealConnection = (RealConnection)localRealInterceptorChain.connection();
    Request localRequest = localRealInterceptorChain.request();
    long l = System.currentTimeMillis();
    localRealInterceptorChain.eventListener().requestHeadersStart(localRealInterceptorChain.call());
    localHttpCodec.writeRequestHeaders(localRequest);
    localRealInterceptorChain.eventListener().requestHeadersEnd(localRealInterceptorChain.call(), localRequest);
    boolean bool = HttpMethod.permitsRequestBody(localRequest.method());
    CountingSink localCountingSink = null;
    Object localObject = null;
    paramChain = localCountingSink;
    if (bool)
    {
      paramChain = localCountingSink;
      if (localRequest.body() != null)
      {
        if ("100-continue".equalsIgnoreCase(localRequest.header("Expect")))
        {
          localHttpCodec.flushRequest();
          localRealInterceptorChain.eventListener().responseHeadersStart(localRealInterceptorChain.call());
          localObject = localHttpCodec.readResponseHeaders(true);
        }
        if (localObject == null)
        {
          localRealInterceptorChain.eventListener().requestBodyStart(localRealInterceptorChain.call());
          localCountingSink = new CountingSink(localHttpCodec.createRequestBody(localRequest, localRequest.body().contentLength()));
          paramChain = l.a(localCountingSink);
          localRequest.body().writeTo(paramChain);
          paramChain.close();
          localRealInterceptorChain.eventListener().requestBodyEnd(localRealInterceptorChain.call(), localCountingSink.successfulCount);
          paramChain = (Interceptor.Chain)localObject;
        }
        else
        {
          paramChain = (Interceptor.Chain)localObject;
          if (!localRealConnection.isMultiplexed())
          {
            localStreamAllocation.noNewStreams();
            paramChain = (Interceptor.Chain)localObject;
          }
        }
      }
    }
    localHttpCodec.finishRequest();
    localObject = paramChain;
    if (paramChain == null)
    {
      localRealInterceptorChain.eventListener().responseHeadersStart(localRealInterceptorChain.call());
      localObject = localHttpCodec.readResponseHeaders(false);
    }
    paramChain = ((Response.Builder)localObject).request(localRequest).handshake(localStreamAllocation.connection().handshake()).sentRequestAtMillis(l).receivedResponseAtMillis(System.currentTimeMillis()).build();
    int i = paramChain.code();
    int j = i;
    if (i == 100)
    {
      paramChain = localHttpCodec.readResponseHeaders(false).request(localRequest).handshake(localStreamAllocation.connection().handshake()).sentRequestAtMillis(l).receivedResponseAtMillis(System.currentTimeMillis()).build();
      j = paramChain.code();
    }
    localRealInterceptorChain.eventListener().responseHeadersEnd(localRealInterceptorChain.call(), paramChain);
    if ((this.forWebSocket) && (j == 101)) {
      paramChain = paramChain.newBuilder().body(Util.EMPTY_RESPONSE).build();
    } else {
      paramChain = paramChain.newBuilder().body(localHttpCodec.openResponseBody(paramChain)).build();
    }
    if (("close".equalsIgnoreCase(paramChain.request().header("Connection"))) || ("close".equalsIgnoreCase(paramChain.header("Connection")))) {
      localStreamAllocation.noNewStreams();
    }
    if (((j != 204) && (j != 205)) || (paramChain.body().contentLength() <= 0L)) {
      return paramChain;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("HTTP ");
    ((StringBuilder)localObject).append(j);
    ((StringBuilder)localObject).append(" had non-zero Content-Length: ");
    ((StringBuilder)localObject).append(paramChain.body().contentLength());
    throw new ProtocolException(((StringBuilder)localObject).toString());
  }
  
  static final class CountingSink
    extends g
  {
    long successfulCount;
    
    CountingSink(r paramr)
    {
      super();
    }
    
    public void write(c paramc, long paramLong)
    {
      super.write(paramc, paramLong);
      this.successfulCount += paramLong;
    }
  }
}


/* Location:              ~/okhttp3/internal/http/CallServerInterceptor.class
 *
 * Reversed by:           J
 */