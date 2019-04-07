package com.google.gson;

import com.google.gson.b.g;
import java.util.Map.Entry;
import java.util.Set;

public final class n
  extends l
{
  private final g<String, l> a = new g();
  
  public void a(String paramString, l paraml)
  {
    Object localObject = paraml;
    if (paraml == null) {
      localObject = m.a;
    }
    this.a.put(paramString, localObject);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if ((paramObject != this) && ((!(paramObject instanceof n)) || (!((n)paramObject).a.equals(this.a)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public int hashCode()
  {
    return this.a.hashCode();
  }
  
  public Set<Map.Entry<String, l>> o()
  {
    return this.a.entrySet();
  }
}


/* Location:              ~/com/google/gson/n.class
 *
 * Reversed by:           J
 */