package com.security.b;

import java.util.HashMap;
import java.util.regex.Pattern;

public class d
  implements a
{
  public final b a = b.a;
  private final Pattern b = Pattern.compile("<destination:.*:\\d{1,5}:\\d{1,5}:\\d{1}>");
  
  public HashMap<String, Object> a(String paramString)
  {
    String[] arrayOfString = paramString.replaceAll("<", "").replace(">", "").split(":");
    HashMap localHashMap = new HashMap();
    paramString = arrayOfString[1];
    int i = Integer.parseInt(arrayOfString[2]);
    int j = Integer.parseInt(arrayOfString[3]);
    boolean bool = arrayOfString[4].equals("1");
    localHashMap.put("name", this.a);
    localHashMap.put("host", paramString);
    localHashMap.put("port", Integer.valueOf(i));
    localHashMap.put("mqtt_port", Integer.valueOf(j));
    localHashMap.put("use_SSL", Boolean.valueOf(bool));
    return localHashMap;
  }
  
  public Pattern a()
  {
    return this.b;
  }
}


/* Location:              ~/com/security/b/d.class
 *
 * Reversed by:           J
 */