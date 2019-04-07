package android.arch.a.a;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.WeakHashMap;

public class b<K, V>
  implements Iterable<Map.Entry<K, V>>
{
  private c<K, V> a;
  private c<K, V> b;
  private WeakHashMap<Object<K, V>, Boolean> c = new WeakHashMap();
  private int d = 0;
  
  public int a()
  {
    return this.d;
  }
  
  public Iterator<Map.Entry<K, V>> b()
  {
    b localb = new b(this.b, this.a);
    this.c.put(localb, Boolean.valueOf(false));
    return localb;
  }
  
  public b<K, V>.d c()
  {
    d locald = new d(null);
    this.c.put(locald, Boolean.valueOf(false));
    return locald;
  }
  
  public Map.Entry<K, V> d()
  {
    return this.a;
  }
  
  public Map.Entry<K, V> e()
  {
    return this.b;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof b)) {
      return false;
    }
    Object localObject1 = (b)paramObject;
    if (a() != ((b)localObject1).a()) {
      return false;
    }
    paramObject = iterator();
    localObject1 = ((b)localObject1).iterator();
    while ((((Iterator)paramObject).hasNext()) && (((Iterator)localObject1).hasNext()))
    {
      Map.Entry localEntry = (Map.Entry)((Iterator)paramObject).next();
      Object localObject2 = ((Iterator)localObject1).next();
      if (((localEntry == null) && (localObject2 != null)) || ((localEntry != null) && (!localEntry.equals(localObject2)))) {
        return false;
      }
    }
    if ((((Iterator)paramObject).hasNext()) || (((Iterator)localObject1).hasNext())) {
      bool = false;
    }
    return bool;
  }
  
  public Iterator<Map.Entry<K, V>> iterator()
  {
    a locala = new a(this.a, this.b);
    this.c.put(locala, Boolean.valueOf(false));
    return locala;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localStringBuilder.append(((Map.Entry)localIterator.next()).toString());
      if (localIterator.hasNext()) {
        localStringBuilder.append(", ");
      }
    }
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
  
  static class a<K, V>
    extends b.e<K, V>
  {
    a(b.c<K, V> paramc1, b.c<K, V> paramc2)
    {
      super(paramc2);
    }
    
    b.c<K, V> a(b.c<K, V> paramc)
    {
      return paramc.c;
    }
  }
  
  private static class b<K, V>
    extends b.e<K, V>
  {
    b(b.c<K, V> paramc1, b.c<K, V> paramc2)
    {
      super(paramc2);
    }
    
    b.c<K, V> a(b.c<K, V> paramc)
    {
      return paramc.d;
    }
  }
  
  static class c<K, V>
    implements Map.Entry<K, V>
  {
    final K a;
    final V b;
    c<K, V> c;
    c<K, V> d;
    
    public boolean equals(Object paramObject)
    {
      boolean bool = true;
      if (paramObject == this) {
        return true;
      }
      if (!(paramObject instanceof c)) {
        return false;
      }
      paramObject = (c)paramObject;
      if ((!this.a.equals(((c)paramObject).a)) || (!this.b.equals(((c)paramObject).b))) {
        bool = false;
      }
      return bool;
    }
    
    public K getKey()
    {
      return (K)this.a;
    }
    
    public V getValue()
    {
      return (V)this.b;
    }
    
    public V setValue(V paramV)
    {
      throw new UnsupportedOperationException("An entry modification is not supported");
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.a);
      localStringBuilder.append("=");
      localStringBuilder.append(this.b);
      return localStringBuilder.toString();
    }
  }
  
  private class d
    implements Iterator<Map.Entry<K, V>>
  {
    private b.c<K, V> b;
    private boolean c = true;
    
    private d() {}
    
    public Map.Entry<K, V> a()
    {
      if (this.c)
      {
        this.c = false;
        this.b = b.a(b.this);
      }
      else
      {
        b.c localc = this.b;
        if (localc != null) {
          localc = localc.c;
        } else {
          localc = null;
        }
        this.b = localc;
      }
      return this.b;
    }
    
    public boolean hasNext()
    {
      boolean bool1 = this.c;
      boolean bool2 = true;
      boolean bool3 = true;
      if (bool1)
      {
        if (b.a(b.this) == null) {
          bool3 = false;
        }
        return bool3;
      }
      b.c localc = this.b;
      if ((localc != null) && (localc.c != null)) {
        bool3 = bool2;
      } else {
        bool3 = false;
      }
      return bool3;
    }
  }
  
  private static abstract class e<K, V>
    implements Iterator<Map.Entry<K, V>>
  {
    b.c<K, V> a;
    b.c<K, V> b;
    
    e(b.c<K, V> paramc1, b.c<K, V> paramc2)
    {
      this.a = paramc2;
      this.b = paramc1;
    }
    
    private b.c<K, V> b()
    {
      b.c localc1 = this.b;
      b.c localc2 = this.a;
      if ((localc1 != localc2) && (localc2 != null)) {
        return a(localc1);
      }
      return null;
    }
    
    abstract b.c<K, V> a(b.c<K, V> paramc);
    
    public Map.Entry<K, V> a()
    {
      b.c localc = this.b;
      this.b = b();
      return localc;
    }
    
    public boolean hasNext()
    {
      boolean bool;
      if (this.b != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
}


/* Location:              ~/android/arch/a/a/b.class
 *
 * Reversed by:           J
 */