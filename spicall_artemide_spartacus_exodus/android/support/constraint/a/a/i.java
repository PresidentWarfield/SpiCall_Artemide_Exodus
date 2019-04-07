package android.support.constraint.a.a;

import android.support.constraint.a.h;
import java.util.ArrayList;

public class i
  extends f
{
  protected float ai = -1.0F;
  protected int aj = -1;
  protected int ak = -1;
  private e al = this.t;
  private int am;
  private boolean an;
  private int ao;
  private l ap;
  private int aq;
  
  public i()
  {
    int i = 0;
    this.am = 0;
    this.an = false;
    this.ao = 0;
    this.ap = new l();
    this.aq = 8;
    this.B.clear();
    this.B.add(this.al);
    int j = this.A.length;
    while (i < j)
    {
      this.A[i] = this.al;
      i++;
    }
  }
  
  public ArrayList<e> C()
  {
    return this.B;
  }
  
  public int J()
  {
    return this.am;
  }
  
  public e a(e.c paramc)
  {
    switch (1.a[paramc.ordinal()])
    {
    default: 
      break;
    case 5: 
    case 6: 
    case 7: 
    case 8: 
    case 9: 
      return null;
    case 3: 
    case 4: 
      if (this.am == 0) {
        return this.al;
      }
      break;
    case 1: 
    case 2: 
      if (this.am == 1) {
        return this.al;
      }
      break;
    }
    throw new AssertionError(paramc.name());
  }
  
  public void a(int paramInt)
  {
    if (this.am == paramInt) {
      return;
    }
    this.am = paramInt;
    this.B.clear();
    if (this.am == 1) {
      this.al = this.s;
    } else {
      this.al = this.t;
    }
    this.B.add(this.al);
    int i = this.A.length;
    for (paramInt = 0; paramInt < i; paramInt++) {
      this.A[paramInt] = this.al;
    }
  }
  
  public void a(android.support.constraint.a.e parame)
  {
    Object localObject1 = (g)k();
    if (localObject1 == null) {
      return;
    }
    e locale = ((g)localObject1).a(e.c.b);
    Object localObject2 = ((g)localObject1).a(e.c.d);
    int i;
    if ((this.D != null) && (this.D.C[0] == f.a.b)) {
      i = 1;
    } else {
      i = 0;
    }
    if (this.am == 0)
    {
      locale = ((g)localObject1).a(e.c.c);
      localObject2 = ((g)localObject1).a(e.c.e);
      if ((this.D != null) && (this.D.C[1] == f.a.b)) {
        i = 1;
      } else {
        i = 0;
      }
    }
    if (this.aj != -1)
    {
      localObject1 = parame.a(this.al);
      parame.c((h)localObject1, parame.a(locale), this.aj, 6);
      if (i != 0) {
        parame.a(parame.a(localObject2), (h)localObject1, 0, 5);
      }
    }
    else if (this.ak != -1)
    {
      localObject1 = parame.a(this.al);
      localObject2 = parame.a(localObject2);
      parame.c((h)localObject1, (h)localObject2, -this.ak, 6);
      if (i != 0)
      {
        parame.a((h)localObject1, parame.a(locale), 0, 5);
        parame.a((h)localObject2, (h)localObject1, 0, 5);
      }
    }
    else if (this.ai != -1.0F)
    {
      parame.a(android.support.constraint.a.e.a(parame, parame.a(this.al), parame.a(locale), parame.a(localObject2), this.ai, this.an));
    }
  }
  
  public boolean a()
  {
    return true;
  }
  
  public void b(int paramInt)
  {
    f localf = k();
    if (localf == null) {
      return;
    }
    if (J() == 1)
    {
      this.t.a().a(1, localf.t.a(), 0);
      this.v.a().a(1, localf.t.a(), 0);
      if (this.aj != -1)
      {
        this.s.a().a(1, localf.s.a(), this.aj);
        this.u.a().a(1, localf.s.a(), this.aj);
      }
      else if (this.ak != -1)
      {
        this.s.a().a(1, localf.u.a(), -this.ak);
        this.u.a().a(1, localf.u.a(), -this.ak);
      }
      else if ((this.ai != -1.0F) && (localf.F() == f.a.a))
      {
        paramInt = (int)(localf.E * this.ai);
        this.s.a().a(1, localf.s.a(), paramInt);
        this.u.a().a(1, localf.s.a(), paramInt);
      }
    }
    else
    {
      this.s.a().a(1, localf.s.a(), 0);
      this.u.a().a(1, localf.s.a(), 0);
      if (this.aj != -1)
      {
        this.t.a().a(1, localf.t.a(), this.aj);
        this.v.a().a(1, localf.t.a(), this.aj);
      }
      else if (this.ak != -1)
      {
        this.t.a().a(1, localf.v.a(), -this.ak);
        this.v.a().a(1, localf.v.a(), -this.ak);
      }
      else if ((this.ai != -1.0F) && (localf.G() == f.a.a))
      {
        paramInt = (int)(localf.F * this.ai);
        this.t.a().a(1, localf.t.a(), paramInt);
        this.v.a().a(1, localf.t.a(), paramInt);
      }
    }
  }
  
  public void c(android.support.constraint.a.e parame)
  {
    if (k() == null) {
      return;
    }
    int i = parame.b(this.al);
    if (this.am == 1)
    {
      h(i);
      i(0);
      k(k().r());
      j(0);
    }
    else
    {
      h(0);
      i(i);
      j(k().p());
      k(0);
    }
  }
  
  public void e(float paramFloat)
  {
    if (paramFloat > -1.0F)
    {
      this.ai = paramFloat;
      this.aj = -1;
      this.ak = -1;
    }
  }
  
  public void u(int paramInt)
  {
    if (paramInt > -1)
    {
      this.ai = -1.0F;
      this.aj = paramInt;
      this.ak = -1;
    }
  }
  
  public void v(int paramInt)
  {
    if (paramInt > -1)
    {
      this.ai = -1.0F;
      this.aj = -1;
      this.ak = paramInt;
    }
  }
}


/* Location:              ~/android/support/constraint/a/a/i.class
 *
 * Reversed by:           J
 */