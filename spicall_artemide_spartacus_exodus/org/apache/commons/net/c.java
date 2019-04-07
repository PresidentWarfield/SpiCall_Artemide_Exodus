package org.apache.commons.net;

import java.io.Serializable;
import java.util.Iterator;

public class c
  implements Serializable
{
  private final Object a;
  private final org.apache.commons.net.a.b b = new org.apache.commons.net.a.b();
  
  public c(Object paramObject)
  {
    this.a = paramObject;
  }
  
  public int a()
  {
    return this.b.a();
  }
  
  public void a(int paramInt, String paramString)
  {
    a locala = new a(this.a, paramInt, paramString);
    paramString = this.b.iterator();
    while (paramString.hasNext()) {
      ((b)paramString.next()).b(locala);
    }
  }
  
  public void a(String paramString1, String paramString2)
  {
    paramString1 = new a(this.a, paramString1, paramString2);
    paramString2 = this.b.iterator();
    while (paramString2.hasNext()) {
      ((b)paramString2.next()).a(paramString1);
    }
  }
}


/* Location:              ~/org/apache/commons/net/c.class
 *
 * Reversed by:           J
 */