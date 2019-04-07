package com.security.b;

import java.util.HashMap;
import java.util.regex.Pattern;

public class e
  implements a
{
  public final b a = b.d;
  private final Pattern b = Pattern.compile("<unauthorized>");
  
  public HashMap<String, Object> a(String paramString)
  {
    paramString.replaceAll("<", "").replace(">", "").replace("key", "").split(":");
    paramString = new HashMap();
    paramString.put("name", this.a);
    paramString.put("state", "unautorized");
    return paramString;
  }
  
  public Pattern a()
  {
    return this.b;
  }
}


/* Location:              ~/com/security/b/e.class
 *
 * Reversed by:           J
 */