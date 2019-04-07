package okhttp3.internal.http;

import java.net.ProtocolException;
import okhttp3.Protocol;
import okhttp3.Response;

public final class StatusLine
{
  public static final int HTTP_CONTINUE = 100;
  public static final int HTTP_PERM_REDIRECT = 308;
  public static final int HTTP_TEMP_REDIRECT = 307;
  public final int code;
  public final String message;
  public final Protocol protocol;
  
  public StatusLine(Protocol paramProtocol, int paramInt, String paramString)
  {
    this.protocol = paramProtocol;
    this.code = paramInt;
    this.message = paramString;
  }
  
  public static StatusLine get(Response paramResponse)
  {
    return new StatusLine(paramResponse.protocol(), paramResponse.code(), paramResponse.message());
  }
  
  public static StatusLine parse(String paramString)
  {
    boolean bool = paramString.startsWith("HTTP/1.");
    int i = 9;
    Object localObject;
    if (bool)
    {
      if ((paramString.length() >= 9) && (paramString.charAt(8) == ' '))
      {
        j = paramString.charAt(7) - '0';
        if (j == 0)
        {
          localObject = Protocol.HTTP_1_0;
        }
        else if (j == 1)
        {
          localObject = Protocol.HTTP_1_1;
        }
        else
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Unexpected status line: ");
          ((StringBuilder)localObject).append(paramString);
          throw new ProtocolException(((StringBuilder)localObject).toString());
        }
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Unexpected status line: ");
        ((StringBuilder)localObject).append(paramString);
        throw new ProtocolException(((StringBuilder)localObject).toString());
      }
    }
    else
    {
      if (!paramString.startsWith("ICY ")) {
        break label347;
      }
      localObject = Protocol.HTTP_1_0;
      i = 4;
    }
    int k = paramString.length();
    int j = i + 3;
    if (k >= j) {
      try
      {
        k = Integer.parseInt(paramString.substring(i, j));
        String str = "";
        if (paramString.length() > j) {
          if (paramString.charAt(j) == ' ')
          {
            str = paramString.substring(i + 4);
          }
          else
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("Unexpected status line: ");
            ((StringBuilder)localObject).append(paramString);
            throw new ProtocolException(((StringBuilder)localObject).toString());
          }
        }
        return new StatusLine((Protocol)localObject, k, str);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Unexpected status line: ");
        localStringBuilder.append(paramString);
        throw new ProtocolException(localStringBuilder.toString());
      }
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Unexpected status line: ");
    localStringBuilder.append(paramString);
    throw new ProtocolException(localStringBuilder.toString());
    label347:
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("Unexpected status line: ");
    localStringBuilder.append(paramString);
    throw new ProtocolException(localStringBuilder.toString());
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str;
    if (this.protocol == Protocol.HTTP_1_0) {
      str = "HTTP/1.0";
    } else {
      str = "HTTP/1.1";
    }
    localStringBuilder.append(str);
    localStringBuilder.append(' ');
    localStringBuilder.append(this.code);
    if (this.message != null)
    {
      localStringBuilder.append(' ');
      localStringBuilder.append(this.message);
    }
    return localStringBuilder.toString();
  }
}


/* Location:              ~/okhttp3/internal/http/StatusLine.class
 *
 * Reversed by:           J
 */