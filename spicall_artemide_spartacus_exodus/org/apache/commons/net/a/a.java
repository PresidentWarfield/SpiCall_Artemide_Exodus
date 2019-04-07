package org.apache.commons.net.a;

import java.nio.charset.Charset;

public class a
{
  public static Charset a(String paramString)
  {
    if (paramString == null) {
      paramString = Charset.defaultCharset();
    } else {
      paramString = Charset.forName(paramString);
    }
    return paramString;
  }
}


/* Location:              ~/org/apache/commons/net/a/a.class
 *
 * Reversed by:           J
 */