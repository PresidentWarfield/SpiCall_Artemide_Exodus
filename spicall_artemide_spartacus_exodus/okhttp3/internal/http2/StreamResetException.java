package okhttp3.internal.http2;

import java.io.IOException;

public final class StreamResetException
  extends IOException
{
  public final ErrorCode errorCode;
  
  public StreamResetException(ErrorCode paramErrorCode)
  {
    super(localStringBuilder.toString());
    this.errorCode = paramErrorCode;
  }
}


/* Location:              ~/okhttp3/internal/http2/StreamResetException.class
 *
 * Reversed by:           J
 */