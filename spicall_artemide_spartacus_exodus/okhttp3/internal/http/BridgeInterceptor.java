package okhttp3.internal.http;

import a.j;
import a.l;
import a.s;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.Version;

public final class BridgeInterceptor
  implements Interceptor
{
  private final CookieJar cookieJar;
  
  public BridgeInterceptor(CookieJar paramCookieJar)
  {
    this.cookieJar = paramCookieJar;
  }
  
  private String cookieHeader(List<Cookie> paramList)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramList.size();
    for (int j = 0; j < i; j++)
    {
      if (j > 0) {
        localStringBuilder.append("; ");
      }
      Cookie localCookie = (Cookie)paramList.get(j);
      localStringBuilder.append(localCookie.name());
      localStringBuilder.append('=');
      localStringBuilder.append(localCookie.value());
    }
    return localStringBuilder.toString();
  }
  
  public Response intercept(Interceptor.Chain paramChain)
  {
    Object localObject1 = paramChain.request();
    Object localObject2 = ((Request)localObject1).newBuilder();
    RequestBody localRequestBody = ((Request)localObject1).body();
    if (localRequestBody != null)
    {
      localObject3 = localRequestBody.contentType();
      if (localObject3 != null) {
        ((Request.Builder)localObject2).header("Content-Type", ((MediaType)localObject3).toString());
      }
      long l = localRequestBody.contentLength();
      if (l != -1L)
      {
        ((Request.Builder)localObject2).header("Content-Length", Long.toString(l));
        ((Request.Builder)localObject2).removeHeader("Transfer-Encoding");
      }
      else
      {
        ((Request.Builder)localObject2).header("Transfer-Encoding", "chunked");
        ((Request.Builder)localObject2).removeHeader("Content-Length");
      }
    }
    Object localObject3 = ((Request)localObject1).header("Host");
    int i = 0;
    if (localObject3 == null) {
      ((Request.Builder)localObject2).header("Host", Util.hostHeader(((Request)localObject1).url(), false));
    }
    if (((Request)localObject1).header("Connection") == null) {
      ((Request.Builder)localObject2).header("Connection", "Keep-Alive");
    }
    int j = i;
    if (((Request)localObject1).header("Accept-Encoding") == null)
    {
      j = i;
      if (((Request)localObject1).header("Range") == null)
      {
        j = 1;
        ((Request.Builder)localObject2).header("Accept-Encoding", "gzip");
      }
    }
    localObject3 = this.cookieJar.loadForRequest(((Request)localObject1).url());
    if (!((List)localObject3).isEmpty()) {
      ((Request.Builder)localObject2).header("Cookie", cookieHeader((List)localObject3));
    }
    if (((Request)localObject1).header("User-Agent") == null) {
      ((Request.Builder)localObject2).header("User-Agent", Version.userAgent());
    }
    paramChain = paramChain.proceed(((Request.Builder)localObject2).build());
    HttpHeaders.receiveHeaders(this.cookieJar, ((Request)localObject1).url(), paramChain.headers());
    localObject2 = paramChain.newBuilder().request((Request)localObject1);
    if ((j != 0) && ("gzip".equalsIgnoreCase(paramChain.header("Content-Encoding"))) && (HttpHeaders.hasBody(paramChain)))
    {
      localObject1 = new j(paramChain.body().source());
      ((Response.Builder)localObject2).headers(paramChain.headers().newBuilder().removeAll("Content-Encoding").removeAll("Content-Length").build());
      ((Response.Builder)localObject2).body(new RealResponseBody(paramChain.header("Content-Type"), -1L, l.a((s)localObject1)));
    }
    return ((Response.Builder)localObject2).build();
  }
}


/* Location:              ~/okhttp3/internal/http/BridgeInterceptor.class
 *
 * Reversed by:           J
 */