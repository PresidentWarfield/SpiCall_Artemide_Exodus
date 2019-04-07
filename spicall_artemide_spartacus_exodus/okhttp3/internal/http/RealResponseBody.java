package okhttp3.internal.http;

import a.e;
import javax.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;

public final class RealResponseBody
  extends ResponseBody
{
  private final long contentLength;
  @Nullable
  private final String contentTypeString;
  private final e source;
  
  public RealResponseBody(@Nullable String paramString, long paramLong, e parame)
  {
    this.contentTypeString = paramString;
    this.contentLength = paramLong;
    this.source = parame;
  }
  
  public long contentLength()
  {
    return this.contentLength;
  }
  
  public MediaType contentType()
  {
    Object localObject = this.contentTypeString;
    if (localObject != null) {
      localObject = MediaType.parse((String)localObject);
    } else {
      localObject = null;
    }
    return (MediaType)localObject;
  }
  
  public e source()
  {
    return this.source;
  }
}


/* Location:              ~/okhttp3/internal/http/RealResponseBody.class
 *
 * Reversed by:           J
 */