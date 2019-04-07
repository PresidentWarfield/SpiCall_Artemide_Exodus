package okhttp3;

import a.f;
import java.nio.charset.Charset;
import okhttp3.internal.Util;

public final class Credentials
{
  public static String basic(String paramString1, String paramString2)
  {
    return basic(paramString1, paramString2, Util.ISO_8859_1);
  }
  
  public static String basic(String paramString1, String paramString2, Charset paramCharset)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append(":");
    localStringBuilder.append(paramString2);
    paramString2 = f.a(localStringBuilder.toString(), paramCharset).b();
    paramString1 = new StringBuilder();
    paramString1.append("Basic ");
    paramString1.append(paramString2);
    return paramString1.toString();
  }
}


/* Location:              ~/okhttp3/Credentials.class
 *
 * Reversed by:           J
 */