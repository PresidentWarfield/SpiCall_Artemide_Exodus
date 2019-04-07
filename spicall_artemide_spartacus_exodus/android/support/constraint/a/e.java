package android.support.constraint.a;

import android.support.constraint.a.a.e.c;
import java.util.Arrays;
import java.util.HashMap;

public class e
{
  public static f g;
  private static int h = 1000;
  int a = 0;
  b[] b;
  public boolean c;
  int d;
  int e;
  final c f;
  private HashMap<String, h> i = null;
  private a j;
  private int k = 32;
  private int l;
  private boolean[] m;
  private int n;
  private h[] o;
  private int p;
  private b[] q;
  private final a r;
  
  public e()
  {
    int i1 = this.k;
    this.l = i1;
    this.b = null;
    this.c = false;
    this.m = new boolean[i1];
    this.d = 1;
    this.e = 0;
    this.n = i1;
    this.o = new h[h];
    this.p = 0;
    this.q = new b[i1];
    this.b = new b[i1];
    i();
    this.f = new c();
    this.j = new d(this.f);
    this.r = new b(this.f);
  }
  
  private final int a(a parama, boolean paramBoolean)
  {
    Object localObject = g;
    if (localObject != null) {
      ((f)localObject).h += 1L;
    }
    for (int i1 = 0; i1 < this.d; i1++) {
      this.m[i1] = false;
    }
    i1 = 0;
    int i2 = 0;
    while (i1 == 0)
    {
      localObject = g;
      if (localObject != null) {
        ((f)localObject).i += 1L;
      }
      int i3 = i2 + 1;
      if (i3 >= this.d * 2) {
        return i3;
      }
      if (parama.g() != null) {
        this.m[parama.g().a] = true;
      }
      localObject = parama.a(this, this.m);
      if (localObject != null)
      {
        if (this.m[localObject.a] != 0) {
          return i3;
        }
        this.m[localObject.a] = true;
      }
      if (localObject != null)
      {
        i2 = 0;
        int i4 = -1;
        b localb;
        float f2;
        for (float f1 = Float.MAX_VALUE; i2 < this.e; f1 = f2)
        {
          localb = this.b[i2];
          int i5;
          if (localb.a.f == h.a.a)
          {
            i5 = i4;
            f2 = f1;
          }
          else if (localb.e)
          {
            i5 = i4;
            f2 = f1;
          }
          else
          {
            i5 = i4;
            f2 = f1;
            if (localb.a((h)localObject))
            {
              float f3 = localb.d.b((h)localObject);
              i5 = i4;
              f2 = f1;
              if (f3 < 0.0F)
              {
                f3 = -localb.b / f3;
                i5 = i4;
                f2 = f1;
                if (f3 < f1)
                {
                  i5 = i2;
                  f2 = f3;
                }
              }
            }
          }
          i2++;
          i4 = i5;
        }
        if (i4 > -1)
        {
          localb = this.b[i4];
          localb.a.b = -1;
          f localf = g;
          if (localf != null) {
            localf.j += 1L;
          }
          localb.c((h)localObject);
          localb.a.b = i4;
          localb.a.c(localb);
          i2 = i3;
        }
        else
        {
          i1 = 1;
          i2 = i3;
        }
      }
      else
      {
        i1 = 1;
        i2 = i3;
      }
    }
    return i2;
  }
  
  public static b a(e parame, h paramh1, h paramh2, h paramh3, float paramFloat, boolean paramBoolean)
  {
    b localb = parame.c();
    if (paramBoolean) {
      parame.b(localb);
    }
    return localb.a(paramh1, paramh2, paramh3, paramFloat);
  }
  
  public static f a()
  {
    return g;
  }
  
  private h a(h.a parama, String paramString)
  {
    h localh = (h)this.f.b.a();
    if (localh == null)
    {
      localh = new h(parama, paramString);
      localh.a(parama, paramString);
      parama = localh;
    }
    else
    {
      localh.b();
      localh.a(parama, paramString);
      parama = localh;
    }
    int i1 = this.p;
    int i2 = h;
    if (i1 >= i2)
    {
      h = i2 * 2;
      this.o = ((h[])Arrays.copyOf(this.o, h));
    }
    paramString = this.o;
    i1 = this.p;
    this.p = (i1 + 1);
    paramString[i1] = parama;
    return parama;
  }
  
  private int b(a parama)
  {
    for (int i1 = 0; i1 < this.e; i1++) {
      if ((this.b[i1].a.f != h.a.a) && (this.b[i1].b < 0.0F))
      {
        i1 = 1;
        break label58;
      }
    }
    i1 = 0;
    label58:
    if (i1 != 0)
    {
      int i2 = 0;
      int i4;
      for (i1 = 0;; i1 = i4)
      {
        i3 = i1;
        if (i2 != 0) {
          break;
        }
        parama = g;
        if (parama != null) {
          parama.k += 1L;
        }
        i4 = i1 + 1;
        int i5 = 0;
        i3 = -1;
        int i6 = -1;
        float f1 = Float.MAX_VALUE;
        Object localObject;
        int i9;
        for (i1 = 0; i5 < this.e; i1 = i9)
        {
          localObject = this.b[i5];
          int i7;
          int i8;
          float f2;
          if (((b)localObject).a.f == h.a.a)
          {
            i7 = i3;
            i8 = i6;
            f2 = f1;
            i9 = i1;
          }
          else if (((b)localObject).e)
          {
            i7 = i3;
            i8 = i6;
            f2 = f1;
            i9 = i1;
          }
          else
          {
            i7 = i3;
            i8 = i6;
            f2 = f1;
            i9 = i1;
            if (((b)localObject).b < 0.0F) {
              for (int i10 = 1;; i10++)
              {
                i7 = i3;
                i8 = i6;
                f2 = f1;
                i9 = i1;
                if (i10 >= this.d) {
                  break;
                }
                parama = this.f.c[i10];
                float f3 = ((b)localObject).d.b(parama);
                if (f3 > 0.0F)
                {
                  i9 = i1;
                  i1 = 0;
                  i8 = i3;
                  for (i3 = i9; i1 < 7; i3 = i9)
                  {
                    f2 = parama.e[i1] / f3;
                    if ((f2 >= f1) || (i1 != i3))
                    {
                      i9 = i3;
                      if (i1 <= i3) {}
                    }
                    else
                    {
                      i6 = i10;
                      i8 = i5;
                      f1 = f2;
                      i9 = i1;
                    }
                    i1++;
                  }
                  i1 = i3;
                  i3 = i8;
                }
              }
            }
          }
          i5++;
          i3 = i7;
          i6 = i8;
          f1 = f2;
        }
        if (i3 != -1)
        {
          parama = this.b[i3];
          parama.a.b = -1;
          localObject = g;
          if (localObject != null) {
            ((f)localObject).j += 1L;
          }
          parama.c(this.f.c[i6]);
          parama.a.b = i3;
          parama.a.c(parama);
        }
        else
        {
          i2 = 1;
        }
        if (i4 > this.d / 2) {
          i2 = 1;
        }
      }
    }
    int i3 = 0;
    return i3;
  }
  
  private void b(b paramb)
  {
    paramb.a(this, 0);
  }
  
  private final void c(b paramb)
  {
    if (this.e > 0)
    {
      paramb.d.a(paramb, this.b);
      if (paramb.d.a == 0) {
        paramb.e = true;
      }
    }
  }
  
  private final void d(b paramb)
  {
    if (this.b[this.e] != null) {
      this.f.a.a(this.b[this.e]);
    }
    this.b[this.e] = paramb;
    h localh = paramb.a;
    int i1 = this.e;
    localh.b = i1;
    this.e = (i1 + 1);
    paramb.a.c(paramb);
  }
  
  private void h()
  {
    this.k *= 2;
    this.b = ((b[])Arrays.copyOf(this.b, this.k));
    Object localObject = this.f;
    ((c)localObject).c = ((h[])Arrays.copyOf(((c)localObject).c, this.k));
    int i1 = this.k;
    this.m = new boolean[i1];
    this.l = i1;
    this.n = i1;
    localObject = g;
    if (localObject != null)
    {
      ((f)localObject).d += 1L;
      localObject = g;
      ((f)localObject).p = Math.max(((f)localObject).p, this.k);
      localObject = g;
      ((f)localObject).D = ((f)localObject).p;
    }
  }
  
  private void i()
  {
    for (int i1 = 0;; i1++)
    {
      Object localObject = this.b;
      if (i1 >= localObject.length) {
        break;
      }
      localObject = localObject[i1];
      if (localObject != null) {
        this.f.a.a(localObject);
      }
      this.b[i1] = null;
    }
  }
  
  private void j()
  {
    for (int i1 = 0; i1 < this.e; i1++)
    {
      b localb = this.b[i1];
      localb.a.d = localb.b;
    }
  }
  
  public h a(int paramInt, String paramString)
  {
    f localf = g;
    if (localf != null) {
      localf.m += 1L;
    }
    if (this.d + 1 >= this.l) {
      h();
    }
    paramString = a(h.a.d, paramString);
    this.a += 1;
    this.d += 1;
    paramString.a = this.a;
    paramString.c = paramInt;
    this.f.c[this.a] = paramString;
    this.j.d(paramString);
    return paramString;
  }
  
  public h a(Object paramObject)
  {
    Object localObject = null;
    if (paramObject == null) {
      return null;
    }
    if (this.d + 1 >= this.l) {
      h();
    }
    if ((paramObject instanceof android.support.constraint.a.a.e))
    {
      android.support.constraint.a.a.e locale = (android.support.constraint.a.a.e)paramObject;
      localObject = locale.b();
      paramObject = localObject;
      if (localObject == null)
      {
        locale.a(this.f);
        paramObject = locale.b();
      }
      if ((((h)paramObject).a != -1) && (((h)paramObject).a <= this.a))
      {
        localObject = paramObject;
        if (this.f.c[paramObject.a] != null) {}
      }
      else
      {
        if (((h)paramObject).a != -1) {
          ((h)paramObject).b();
        }
        this.a += 1;
        this.d += 1;
        ((h)paramObject).a = this.a;
        ((h)paramObject).f = h.a.a;
        this.f.c[this.a] = paramObject;
        localObject = paramObject;
      }
    }
    return (h)localObject;
  }
  
  public void a(android.support.constraint.a.a.f paramf1, android.support.constraint.a.a.f paramf2, float paramFloat, int paramInt)
  {
    h localh1 = a(paramf1.a(e.c.b));
    h localh2 = a(paramf1.a(e.c.c));
    h localh3 = a(paramf1.a(e.c.d));
    h localh4 = a(paramf1.a(e.c.e));
    paramf1 = a(paramf2.a(e.c.b));
    h localh5 = a(paramf2.a(e.c.c));
    h localh6 = a(paramf2.a(e.c.d));
    paramf2 = a(paramf2.a(e.c.e));
    b localb = c();
    double d1 = paramFloat;
    double d2 = Math.sin(d1);
    double d3 = paramInt;
    Double.isNaN(d3);
    localb.b(localh2, localh4, localh5, paramf2, (float)(d2 * d3));
    a(localb);
    paramf2 = c();
    d1 = Math.cos(d1);
    Double.isNaN(d3);
    paramf2.b(localh1, localh3, paramf1, localh6, (float)(d1 * d3));
    a(paramf2);
  }
  
  public void a(b paramb)
  {
    if (paramb == null) {
      return;
    }
    Object localObject = g;
    if (localObject != null)
    {
      ((f)localObject).f += 1L;
      if (paramb.e)
      {
        localObject = g;
        ((f)localObject).g += 1L;
      }
    }
    if ((this.e + 1 >= this.n) || (this.d + 1 >= this.l)) {
      h();
    }
    int i1 = 0;
    int i2 = 0;
    if (!paramb.e)
    {
      c(paramb);
      if (paramb.e()) {
        return;
      }
      paramb.d();
      i1 = i2;
      if (paramb.a(this))
      {
        localObject = e();
        paramb.a = ((h)localObject);
        d(paramb);
        this.r.a(paramb);
        a(this.r, true);
        if (((h)localObject).b == -1)
        {
          if (paramb.a == localObject)
          {
            localObject = paramb.b((h)localObject);
            if (localObject != null)
            {
              f localf = g;
              if (localf != null) {
                localf.j += 1L;
              }
              paramb.c((h)localObject);
            }
          }
          if (!paramb.e) {
            paramb.a.c(paramb);
          }
          this.e -= 1;
        }
        i1 = 1;
      }
      if (!paramb.a()) {
        return;
      }
    }
    if (i1 == 0) {
      d(paramb);
    }
  }
  
  void a(b paramb, int paramInt1, int paramInt2)
  {
    paramb.c(a(paramInt2, null), paramInt1);
  }
  
  void a(a parama)
  {
    f localf = g;
    if (localf != null)
    {
      localf.t += 1L;
      localf = g;
      localf.u = Math.max(localf.u, this.d);
      localf = g;
      localf.v = Math.max(localf.v, this.e);
    }
    c((b)parama);
    b(parama);
    a(parama, false);
    j();
  }
  
  public void a(h paramh, int paramInt)
  {
    int i1 = paramh.b;
    b localb;
    if (paramh.b != -1)
    {
      localb = this.b[i1];
      if (localb.e)
      {
        localb.b = paramInt;
      }
      else if (localb.d.a == 0)
      {
        localb.e = true;
        localb.b = paramInt;
      }
      else
      {
        localb = c();
        localb.b(paramh, paramInt);
        a(localb);
      }
    }
    else
    {
      localb = c();
      localb.a(paramh, paramInt);
      a(localb);
    }
  }
  
  public void a(h paramh1, h paramh2, int paramInt1, float paramFloat, h paramh3, h paramh4, int paramInt2, int paramInt3)
  {
    b localb = c();
    localb.a(paramh1, paramh2, paramInt1, paramFloat, paramh3, paramh4, paramInt2);
    if (paramInt3 != 6) {
      localb.a(this, paramInt3);
    }
    a(localb);
  }
  
  public void a(h paramh1, h paramh2, int paramInt1, int paramInt2)
  {
    b localb = c();
    h localh = d();
    localh.c = 0;
    localb.a(paramh1, paramh2, localh, paramInt1);
    if (paramInt2 != 6) {
      a(localb, (int)(localb.d.b(localh) * -1.0F), paramInt2);
    }
    a(localb);
  }
  
  public void a(h paramh1, h paramh2, h paramh3, h paramh4, float paramFloat, int paramInt)
  {
    b localb = c();
    localb.a(paramh1, paramh2, paramh3, paramh4, paramFloat);
    if (paramInt != 6) {
      localb.a(this, paramInt);
    }
    a(localb);
  }
  
  public void a(h paramh1, h paramh2, boolean paramBoolean)
  {
    b localb = c();
    h localh = d();
    localh.c = 0;
    localb.a(paramh1, paramh2, localh, 0);
    if (paramBoolean) {
      a(localb, (int)(localb.d.b(localh) * -1.0F), 1);
    }
    a(localb);
  }
  
  public int b(Object paramObject)
  {
    paramObject = ((android.support.constraint.a.a.e)paramObject).b();
    if (paramObject != null) {
      return (int)(((h)paramObject).d + 0.5F);
    }
    return 0;
  }
  
  public void b()
  {
    for (int i1 = 0; i1 < this.f.c.length; i1++)
    {
      localObject = this.f.c[i1];
      if (localObject != null) {
        ((h)localObject).b();
      }
    }
    this.f.b.a(this.o, this.p);
    this.p = 0;
    Arrays.fill(this.f.c, null);
    Object localObject = this.i;
    if (localObject != null) {
      ((HashMap)localObject).clear();
    }
    this.a = 0;
    this.j.f();
    this.d = 1;
    for (i1 = 0; i1 < this.e; i1++) {
      this.b[i1].c = false;
    }
    i();
    this.e = 0;
  }
  
  public void b(h paramh1, h paramh2, int paramInt1, int paramInt2)
  {
    b localb = c();
    h localh = d();
    localh.c = 0;
    localb.b(paramh1, paramh2, localh, paramInt1);
    if (paramInt2 != 6) {
      a(localb, (int)(localb.d.b(localh) * -1.0F), paramInt2);
    }
    a(localb);
  }
  
  public void b(h paramh1, h paramh2, boolean paramBoolean)
  {
    b localb = c();
    h localh = d();
    localh.c = 0;
    localb.b(paramh1, paramh2, localh, 0);
    if (paramBoolean) {
      a(localb, (int)(localb.d.b(localh) * -1.0F), 1);
    }
    a(localb);
  }
  
  public b c()
  {
    b localb = (b)this.f.a.a();
    if (localb == null) {
      localb = new b(this.f);
    } else {
      localb.c();
    }
    h.a();
    return localb;
  }
  
  public b c(h paramh1, h paramh2, int paramInt1, int paramInt2)
  {
    b localb = c();
    localb.a(paramh1, paramh2, paramInt1);
    if (paramInt2 != 6) {
      localb.a(this, paramInt2);
    }
    a(localb);
    return localb;
  }
  
  public h d()
  {
    Object localObject = g;
    if (localObject != null) {
      ((f)localObject).n += 1L;
    }
    if (this.d + 1 >= this.l) {
      h();
    }
    localObject = a(h.a.c, null);
    this.a += 1;
    this.d += 1;
    ((h)localObject).a = this.a;
    this.f.c[this.a] = localObject;
    return (h)localObject;
  }
  
  public h e()
  {
    Object localObject = g;
    if (localObject != null) {
      ((f)localObject).o += 1L;
    }
    if (this.d + 1 >= this.l) {
      h();
    }
    localObject = a(h.a.c, null);
    this.a += 1;
    this.d += 1;
    ((h)localObject).a = this.a;
    this.f.c[this.a] = localObject;
    return (h)localObject;
  }
  
  public void f()
  {
    f localf = g;
    if (localf != null) {
      localf.e += 1L;
    }
    if (this.c)
    {
      localf = g;
      if (localf != null) {
        localf.r += 1L;
      }
      int i1 = 0;
      for (int i2 = 0; i2 < this.e; i2++) {
        if (!this.b[i2].e)
        {
          i2 = i1;
          break label80;
        }
      }
      i2 = 1;
      label80:
      if (i2 == 0)
      {
        a(this.j);
      }
      else
      {
        localf = g;
        if (localf != null) {
          localf.q += 1L;
        }
        j();
      }
    }
    else
    {
      a(this.j);
    }
  }
  
  public c g()
  {
    return this.f;
  }
  
  static abstract interface a
  {
    public abstract h a(e parame, boolean[] paramArrayOfBoolean);
    
    public abstract void a(a parama);
    
    public abstract void d(h paramh);
    
    public abstract void f();
    
    public abstract h g();
  }
}


/* Location:              ~/android/support/constraint/a/e.class
 *
 * Reversed by:           J
 */