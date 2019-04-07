package android.support.constraint.a.a;

import android.support.constraint.a.h;

public class m
  extends o
{
  e a;
  float b;
  m c;
  float d;
  m e;
  float f;
  int g = 0;
  private m j;
  private float k;
  private n l = null;
  private int m = 1;
  private n n = null;
  private int o = 1;
  
  public m(e parame)
  {
    this.a = parame;
  }
  
  String a(int paramInt)
  {
    if (paramInt == 1) {
      return "DIRECT";
    }
    if (paramInt == 2) {
      return "CENTER";
    }
    if (paramInt == 3) {
      return "MATCH";
    }
    if (paramInt == 4) {
      return "CHAIN";
    }
    if (paramInt == 5) {
      return "BARRIER";
    }
    return "UNCONNECTED";
  }
  
  public void a()
  {
    int i = this.i;
    int i1 = 1;
    if (i == 1) {
      return;
    }
    if (this.g == 4) {
      return;
    }
    Object localObject1 = this.l;
    if (localObject1 != null)
    {
      if (((n)localObject1).i != 1) {
        return;
      }
      this.d = (this.m * this.l.a);
    }
    localObject1 = this.n;
    if (localObject1 != null)
    {
      if (((n)localObject1).i != 1) {
        return;
      }
      this.k = (this.o * this.n.a);
    }
    if (this.g == 1)
    {
      localObject1 = this.c;
      if ((localObject1 == null) || (((m)localObject1).i == 1))
      {
        localObject1 = this.c;
        if (localObject1 == null)
        {
          this.e = this;
          this.f = this.d;
        }
        else
        {
          this.e = ((m)localObject1).e;
          this.f = (((m)localObject1).f + this.d);
        }
        f();
        return;
      }
    }
    Object localObject2;
    if (this.g == 2)
    {
      localObject1 = this.c;
      if ((localObject1 != null) && (((m)localObject1).i == 1))
      {
        localObject1 = this.j;
        if (localObject1 != null)
        {
          localObject1 = ((m)localObject1).c;
          if ((localObject1 != null) && (((m)localObject1).i == 1))
          {
            if (android.support.constraint.a.e.a() != null)
            {
              localObject1 = android.support.constraint.a.e.a();
              ((android.support.constraint.a.f)localObject1).w += 1L;
            }
            this.e = this.c.e;
            localObject1 = this.j;
            ((m)localObject1).e = ((m)localObject1).c.e;
            localObject1 = this.a.b;
            localObject2 = e.c.d;
            int i2 = 0;
            i = i1;
            if (localObject1 != localObject2) {
              if (this.a.b == e.c.e) {
                i = i1;
              } else {
                i = 0;
              }
            }
            float f1;
            if (i != 0) {
              f1 = this.c.f - this.j.c.f;
            } else {
              f1 = this.j.c.f - this.c.f;
            }
            if ((this.a.b != e.c.b) && (this.a.b != e.c.d))
            {
              f2 = f1 - this.a.a.r();
              f1 = this.a.a.T;
            }
            else
            {
              f2 = f1 - this.a.a.p();
              f1 = this.a.a.S;
            }
            int i3 = this.a.e();
            i1 = this.j.a.e();
            if (this.a.g() == this.j.a.g())
            {
              f1 = 0.5F;
              i1 = 0;
            }
            else
            {
              i2 = i3;
            }
            float f3 = i2;
            float f4 = i1;
            float f2 = f2 - f3 - f4;
            if (i != 0)
            {
              localObject1 = this.j;
              ((m)localObject1).f = (((m)localObject1).c.f + f4 + f2 * f1);
              this.f = (this.c.f - f3 - f2 * (1.0F - f1));
            }
            else
            {
              this.f = (this.c.f + f3 + f2 * f1);
              localObject1 = this.j;
              ((m)localObject1).f = (((m)localObject1).c.f - f4 - f2 * (1.0F - f1));
            }
            f();
            this.j.f();
            return;
          }
        }
      }
    }
    if (this.g == 3)
    {
      localObject1 = this.c;
      if ((localObject1 != null) && (((m)localObject1).i == 1))
      {
        localObject1 = this.j;
        if (localObject1 != null)
        {
          localObject1 = ((m)localObject1).c;
          if ((localObject1 != null) && (((m)localObject1).i == 1))
          {
            if (android.support.constraint.a.e.a() != null)
            {
              localObject1 = android.support.constraint.a.e.a();
              ((android.support.constraint.a.f)localObject1).x += 1L;
            }
            m localm = this.c;
            this.e = localm.e;
            localObject1 = this.j;
            localObject2 = ((m)localObject1).c;
            ((m)localObject1).e = ((m)localObject2).e;
            localm.f += this.d;
            ((m)localObject2).f += ((m)localObject1).d;
            f();
            this.j.f();
            return;
          }
        }
      }
    }
    if (this.g == 5) {
      this.a.a.c();
    }
  }
  
  public void a(int paramInt1, m paramm, int paramInt2)
  {
    this.g = paramInt1;
    this.c = paramm;
    this.d = paramInt2;
    this.c.a(this);
  }
  
  public void a(m paramm, float paramFloat)
  {
    if ((this.i == 0) || ((this.e != paramm) && (this.f != paramFloat)))
    {
      this.e = paramm;
      this.f = paramFloat;
      if (this.i == 1) {
        e();
      }
      f();
    }
  }
  
  public void a(m paramm, int paramInt)
  {
    this.c = paramm;
    this.d = paramInt;
    this.c.a(this);
  }
  
  public void a(m paramm, int paramInt, n paramn)
  {
    this.c = paramm;
    this.c.a(this);
    this.l = paramn;
    this.m = paramInt;
    this.l.a(this);
  }
  
  void a(android.support.constraint.a.e parame)
  {
    h localh = this.a.b();
    m localm = this.e;
    if (localm == null) {
      parame.a(localh, (int)(this.f + 0.5F));
    } else {
      parame.c(localh, parame.a(localm.a), (int)(this.f + 0.5F), 6);
    }
  }
  
  public void b()
  {
    super.b();
    this.c = null;
    this.d = 0.0F;
    this.l = null;
    this.m = 1;
    this.n = null;
    this.o = 1;
    this.e = null;
    this.f = 0.0F;
    this.b = 0.0F;
    this.j = null;
    this.k = 0.0F;
    this.g = 0;
  }
  
  public void b(int paramInt)
  {
    this.g = paramInt;
  }
  
  public void b(m paramm, float paramFloat)
  {
    this.j = paramm;
    this.k = paramFloat;
  }
  
  public void b(m paramm, int paramInt, n paramn)
  {
    this.j = paramm;
    this.n = paramn;
    this.o = paramInt;
  }
  
  public void c()
  {
    e locale = this.a.g();
    if (locale == null) {
      return;
    }
    if (locale.g() == this.a)
    {
      this.g = 4;
      locale.a().g = 4;
    }
    int i = this.a.e();
    int i1;
    if (this.a.b != e.c.d)
    {
      i1 = i;
      if (this.a.b != e.c.e) {}
    }
    else
    {
      i1 = -i;
    }
    a(locale.a(), i1);
  }
  
  public float d()
  {
    return this.f;
  }
  
  public String toString()
  {
    if (this.i == 1)
    {
      if (this.e == this)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("[");
        localStringBuilder.append(this.a);
        localStringBuilder.append(", RESOLVED: ");
        localStringBuilder.append(this.f);
        localStringBuilder.append("]  type: ");
        localStringBuilder.append(a(this.g));
        return localStringBuilder.toString();
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("[");
      localStringBuilder.append(this.a);
      localStringBuilder.append(", RESOLVED: ");
      localStringBuilder.append(this.e);
      localStringBuilder.append(":");
      localStringBuilder.append(this.f);
      localStringBuilder.append("] type: ");
      localStringBuilder.append(a(this.g));
      return localStringBuilder.toString();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("{ ");
    localStringBuilder.append(this.a);
    localStringBuilder.append(" UNRESOLVED} type: ");
    localStringBuilder.append(a(this.g));
    return localStringBuilder.toString();
  }
}


/* Location:              ~/android/support/constraint/a/a/m.class
 *
 * Reversed by:           J
 */