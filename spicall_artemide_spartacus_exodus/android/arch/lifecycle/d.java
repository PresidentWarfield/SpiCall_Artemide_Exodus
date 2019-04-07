package android.arch.lifecycle;

import android.arch.a.a.b.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

public class d
  extends b
{
  private android.arch.a.a.a<Object, a> a = new android.arch.a.a.a();
  private b.b b;
  private final c c;
  private int d = 0;
  private boolean e = false;
  private boolean f = false;
  private ArrayList<b.b> g = new ArrayList();
  
  public d(c paramc)
  {
    this.c = paramc;
    this.b = b.b.b;
  }
  
  static b.b a(b.b paramb1, b.b paramb2)
  {
    b.b localb = paramb1;
    if (paramb2 != null)
    {
      localb = paramb1;
      if (paramb2.compareTo(paramb1) < 0) {
        localb = paramb2;
      }
    }
    return localb;
  }
  
  private boolean a()
  {
    int i = this.a.a();
    boolean bool = true;
    if (i == 0) {
      return true;
    }
    b.b localb1 = ((a)this.a.d().getValue()).a;
    b.b localb2 = ((a)this.a.e().getValue()).a;
    if ((localb1 != localb2) || (this.b != localb2)) {
      bool = false;
    }
    return bool;
  }
  
  static b.b b(b.a parama)
  {
    switch (1.a[parama.ordinal()])
    {
    default: 
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unexpected event value ");
      localStringBuilder.append(parama);
      throw new IllegalArgumentException(localStringBuilder.toString());
    case 6: 
      return b.b.a;
    case 5: 
      return b.b.e;
    case 3: 
    case 4: 
      return b.b.d;
    }
    return b.b.c;
  }
  
  private void b()
  {
    ArrayList localArrayList = this.g;
    localArrayList.remove(localArrayList.size() - 1);
  }
  
  private void b(b.b paramb)
  {
    this.g.add(paramb);
  }
  
  private static b.a c(b.b paramb)
  {
    switch (1.b[paramb.ordinal()])
    {
    default: 
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unexpected state value ");
      localStringBuilder.append(paramb);
      throw new IllegalArgumentException(localStringBuilder.toString());
    case 5: 
      throw new IllegalArgumentException();
    case 4: 
      return b.a.ON_PAUSE;
    case 3: 
      return b.a.ON_STOP;
    case 2: 
      return b.a.ON_DESTROY;
    }
    throw new IllegalArgumentException();
  }
  
  private void c()
  {
    b.d locald = this.a.c();
    while ((locald.hasNext()) && (!this.f))
    {
      Map.Entry localEntry = (Map.Entry)locald.next();
      a locala = (a)localEntry.getValue();
      while ((locala.a.compareTo(this.b) < 0) && (!this.f) && (this.a.a(localEntry.getKey())))
      {
        b(locala.a);
        locala.a(this.c, d(locala.a));
        b();
      }
    }
  }
  
  private static b.a d(b.b paramb)
  {
    switch (1.b[paramb.ordinal()])
    {
    default: 
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unexpected state value ");
      localStringBuilder.append(paramb);
      throw new IllegalArgumentException(localStringBuilder.toString());
    case 4: 
      throw new IllegalArgumentException();
    case 3: 
      return b.a.ON_RESUME;
    case 2: 
      return b.a.ON_START;
    }
    return b.a.ON_CREATE;
  }
  
  private void d()
  {
    Iterator localIterator = this.a.b();
    while ((localIterator.hasNext()) && (!this.f))
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      a locala = (a)localEntry.getValue();
      while ((locala.a.compareTo(this.b) > 0) && (!this.f) && (this.a.a(localEntry.getKey())))
      {
        b.a locala1 = c(locala.a);
        b(b(locala1));
        locala.a(this.c, locala1);
        b();
      }
    }
  }
  
  private void e()
  {
    while (!a())
    {
      this.f = false;
      if (this.b.compareTo(((a)this.a.d().getValue()).a) < 0) {
        d();
      }
      Map.Entry localEntry = this.a.e();
      if ((!this.f) && (localEntry != null) && (this.b.compareTo(((a)localEntry.getValue()).a) > 0)) {
        c();
      }
    }
    this.f = false;
  }
  
  public void a(b.a parama)
  {
    this.b = b(parama);
    if ((!this.e) && (this.d == 0))
    {
      this.e = true;
      e();
      this.e = false;
      return;
    }
    this.f = true;
  }
  
  public void a(b.b paramb)
  {
    this.b = paramb;
  }
  
  static class a
  {
    b.b a;
    a b;
    
    void a(c paramc, b.a parama)
    {
      b.b localb = d.b(parama);
      this.a = d.a(this.a, localb);
      this.b.a(paramc, parama);
      this.a = localb;
    }
  }
}


/* Location:              ~/android/arch/lifecycle/d.class
 *
 * Reversed by:           J
 */