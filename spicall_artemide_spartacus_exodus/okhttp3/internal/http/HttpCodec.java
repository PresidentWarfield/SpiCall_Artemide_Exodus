package okhttp3.internal.http;

import a.r;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;

public abstract interface HttpCodec
{
  public static final int DISCARD_STREAM_TIMEOUT_MILLIS = 100;
  
  public abstract void cancel();
  
  public abstract r createRequestBody(Request paramRequest, long paramLong);
  
  public abstract void finishRequest();
  
  public abstract void flushRequest();
  
  public abstract ResponseBody openResponseBody(Response paramResponse);
  
  public abstract Response.Builder readResponseHeaders(boolean paramBoolean);
  
  public abstract void writeRequestHeaders(Request paramRequest);
}


/* Location:              ~/okhttp3/internal/http/HttpCodec.class
 *
 * Reversed by:           J
 */