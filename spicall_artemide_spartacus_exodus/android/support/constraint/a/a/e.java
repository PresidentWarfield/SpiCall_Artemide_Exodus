package android.support.constraint.a.a;

import android.support.constraint.a.c;
import android.support.constraint.a.h;
import android.support.constraint.a.h.a;

public class e
{
  final f a;
  final c b;
  e c;
  public int d = 0;
  int e = -1;
  h f;
  private m g = new m(this);
  private b h = b.a;
  private a i = a.a;
  private int j = 0;
  
  public e(f paramf, c paramc)
  {
    this.a = paramf;
    this.b = paramc;
  }
  
  public m a()
  {
    return this.g;
  }
  
  public void a(c paramc)
  {
    paramc = this.f;
    if (paramc == null) {
      this.f = new h(h.a.a, null);
    } else {
      paramc.b();
    }
  }
  
  public boolean a(e parame)
  {
    boolean bool1 = false;
    if (parame == null) {
      return false;
    }
    c localc1 = parame.d();
    c localc2 = this.b;
    if (localc1 == localc2) {
      return (localc2 != c.f) || ((parame.c().z()) && (c().z()));
    }
    switch (1.a[this.b.ordinal()])
    {
    default: 
      throw new AssertionError(this.b.name());
    case 6: 
    case 7: 
    case 8: 
    case 9: 
      return false;
    case 4: 
    case 5: 
      if ((localc1 != c.c) && (localc1 != c.e)) {
        bool1 = false;
      } else {
        bool1 = true;
      }
      bool2 = bool1;
      if ((parame.c() instanceof i)) {
        if ((!bool1) && (localc1 != c.i)) {
          bool2 = false;
        } else {
          bool2 = true;
        }
      }
      return bool2;
    case 2: 
    case 3: 
      if ((localc1 != c.b) && (localc1 != c.d)) {
        bool1 = false;
      } else {
        bool1 = true;
      }
      bool2 = bool1;
      if ((parame.c() instanceof i)) {
        if ((!bool1) && (localc1 != c.h)) {
          bool2 = false;
        } else {
          bool2 = true;
        }
      }
      return bool2;
    }
    boolean bool2 = bool1;
    if (localc1 != c.f)
    {
      bool2 = bool1;
      if (localc1 != c.h)
      {
        bool2 = bool1;
        if (localc1 != c.i) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  public boolean a(e parame, int paramInt1, int paramInt2, b paramb, int paramInt3, boolean paramBoolean)
  {
    if (parame == null)
    {
      this.c = null;
      this.d = 0;
      this.e = -1;
      this.h = b.a;
      this.j = 2;
      return true;
    }
    if ((!paramBoolean) && (!a(parame))) {
      return false;
    }
    this.c = parame;
    if (paramInt1 > 0) {
      this.d = paramInt1;
    } else {
      this.d = 0;
    }
    this.e = paramInt2;
    this.h = paramb;
    this.j = paramInt3;
    return true;
  }
  
  public boolean a(e parame, int paramInt1, b paramb, int paramInt2)
  {
    return a(parame, paramInt1, -1, paramb, paramInt2, false);
  }
  
  public h b()
  {
    return this.f;
  }
  
  public f c()
  {
    return this.a;
  }
  
  public c d()
  {
    return this.b;
  }
  
  public int e()
  {
    if (this.a.l() == 8) {
      return 0;
    }
    if (this.e > -1)
    {
      e locale = this.c;
      if ((locale != null) && (locale.a.l() == 8)) {
        return this.e;
      }
    }
    return this.d;
  }
  
  public b f()
  {
    return this.h;
  }
  
  public e g()
  {
    return this.c;
  }
  
  public int h()
  {
    return this.j;
  }
  
  public void i()
  {
    this.c = null;
    this.d = 0;
    this.e = -1;
    this.h = b.b;
    this.j = 0;
    this.i = a.a;
    this.g.b();
  }
  
  public boolean j()
  {
    boolean bool;
    if (this.c != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.a.m());
    localStringBuilder.append(":");
    localStringBuilder.append(this.b.toString());
    return localStringBuilder.toString();
  }
  
  public static enum a
  {
    private a() {}
  }
  
  public static enum b
  {
    private b() {}
  }
  
  public static enum c
  {
    private c() {}
  }
}


/* Location:              ~/android/support/constraint/a/a/e.class
 *
 * Reversed by:           J
 */