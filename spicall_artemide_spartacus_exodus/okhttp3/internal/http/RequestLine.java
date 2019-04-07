package okhttp3.internal.http;

import java.net.Proxy.Type;
import okhttp3.HttpUrl;
import okhttp3.Request;

public final class RequestLine
{
  public static String get(Request paramRequest, Proxy.Type paramType)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramRequest.method());
    localStringBuilder.append(' ');
    if (includeAuthorityInRequestLine(paramRequest, paramType)) {
      localStringBuilder.append(paramRequest.url());
    } else {
      localStringBuilder.append(requestPath(paramRequest.url()));
    }
    localStringBuilder.append(" HTTP/1.1");
    return localStringBuilder.toString();
  }
  
  private static boolean includeAuthorityInRequestLine(Request paramRequest, Proxy.Type paramType)
  {
    boolean bool;
    if ((!paramRequest.isHttps()) && (paramType == Proxy.Type.HTTP)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static String requestPath(HttpUrl paramHttpUrl)
  {
    String str1 = paramHttpUrl.encodedPath();
    String str2 = paramHttpUrl.encodedQuery();
    paramHttpUrl = str1;
    if (str2 != null)
    {
      paramHttpUrl = new StringBuilder();
      paramHttpUrl.append(str1);
      paramHttpUrl.append('?');
      paramHttpUrl.append(str2);
      paramHttpUrl = paramHttpUrl.toString();
    }
    return paramHttpUrl;
  }
}


/* Location:              ~/okhttp3/internal/http/RequestLine.class
 *
 * Reversed by:           J
 */