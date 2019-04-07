package com.google.gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class i
  extends l
  implements Iterable<l>
{
  private final List<l> a = new ArrayList();
  
  public Number a()
  {
    if (this.a.size() == 1) {
      return ((l)this.a.get(0)).a();
    }
    throw new IllegalStateException();
  }
  
  public void a(l paraml)
  {
    Object localObject = paraml;
    if (paraml == null) {
      localObject = m.a;
    }
    this.a.add(localObject);
  }
  
  public String b()
  {
    if (this.a.size() == 1) {
      return ((l)this.a.get(0)).b();
    }
    throw new IllegalStateException();
  }
  
  public double c()
  {
    if (this.a.size() == 1) {
      return ((l)this.a.get(0)).c();
    }
    throw new IllegalStateException();
  }
  
  public long d()
  {
    if (this.a.size() == 1) {
      return ((l)this.a.get(0)).d();
    }
    throw new IllegalStateException();
  }
  
  public int e()
  {
    if (this.a.size() == 1) {
      return ((l)this.a.get(0)).e();
    }
    throw new IllegalStateException();
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if ((paramObject != this) && ((!(paramObject instanceof i)) || (!((i)paramObject).a.equals(this.a)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean f()
  {
    if (this.a.size() == 1) {
      return ((l)this.a.get(0)).f();
    }
    throw new IllegalStateException();
  }
  
  public int hashCode()
  {
    return this.a.hashCode();
  }
  
  public Iterator<l> iterator()
  {
    return this.a.iterator();
  }
}


/* Location:              ~/com/google/gson/i.class
 *
 * Reversed by:           J
 */