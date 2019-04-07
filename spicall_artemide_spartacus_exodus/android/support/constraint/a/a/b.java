package android.support.constraint.a.a;

import android.support.constraint.a.h;
import java.util.ArrayList;

public class b
  extends j
{
  private int ak = 0;
  private ArrayList<m> al = new ArrayList(4);
  private boolean am = true;
  
  public void a(int paramInt)
  {
    this.ak = paramInt;
  }
  
  public void a(android.support.constraint.a.e parame)
  {
    this.A[0] = this.s;
    this.A[2] = this.t;
    this.A[1] = this.u;
    this.A[3] = this.v;
    for (int i = 0; i < this.A.length; i++) {
      this.A[i].f = parame.a(this.A[i]);
    }
    i = this.ak;
    if ((i >= 0) && (i < 4))
    {
      e locale = this.A[this.ak];
      Object localObject1;
      int j;
      for (i = 0; i < this.aj; i++)
      {
        localObject1 = this.ai[i];
        if ((this.am) || (((f)localObject1).a()))
        {
          j = this.ak;
          if (((j == 0) || (j == 1)) && (((f)localObject1).F() == f.a.c))
          {
            bool = true;
            break label214;
          }
          j = this.ak;
          if (((j == 2) || (j == 3)) && (((f)localObject1).G() == f.a.c))
          {
            bool = true;
            break label214;
          }
        }
      }
      boolean bool = false;
      label214:
      i = this.ak;
      if ((i != 0) && (i != 1))
      {
        if (k().G() == f.a.b) {
          bool = false;
        }
      }
      else if (k().F() == f.a.b) {
        bool = false;
      }
      for (i = 0; i < this.aj; i++)
      {
        Object localObject2 = this.ai[i];
        if ((this.am) || (((f)localObject2).a()))
        {
          localObject1 = parame.a(localObject2.A[this.ak]);
          localObject2 = ((f)localObject2).A;
          j = this.ak;
          localObject2[j].f = ((h)localObject1);
          if ((j != 0) && (j != 2)) {
            parame.a(locale.f, (h)localObject1, bool);
          } else {
            parame.b(locale.f, (h)localObject1, bool);
          }
        }
      }
      i = this.ak;
      if (i == 0)
      {
        parame.c(this.u.f, this.s.f, 0, 6);
        if (!bool) {
          parame.c(this.s.f, this.D.u.f, 0, 5);
        }
      }
      else if (i == 1)
      {
        parame.c(this.s.f, this.u.f, 0, 6);
        if (!bool) {
          parame.c(this.s.f, this.D.s.f, 0, 5);
        }
      }
      else if (i == 2)
      {
        parame.c(this.v.f, this.t.f, 0, 6);
        if (!bool) {
          parame.c(this.t.f, this.D.v.f, 0, 5);
        }
      }
      else if (i == 3)
      {
        parame.c(this.t.f, this.v.f, 0, 6);
        if (!bool) {
          parame.c(this.t.f, this.D.t.f, 0, 5);
        }
      }
      return;
    }
  }
  
  public void a(boolean paramBoolean)
  {
    this.am = paramBoolean;
  }
  
  public boolean a()
  {
    return true;
  }
  
  public void b()
  {
    super.b();
    this.al.clear();
  }
  
  public void b(int paramInt)
  {
    if (this.D == null) {
      return;
    }
    if (!((g)this.D).u(2)) {
      return;
    }
    m localm;
    switch (this.ak)
    {
    default: 
      return;
    case 3: 
      localm = this.v.a();
      break;
    case 2: 
      localm = this.t.a();
      break;
    case 1: 
      localm = this.u.a();
      break;
    case 0: 
      localm = this.s.a();
    }
    localm.b(5);
    paramInt = this.ak;
    if ((paramInt != 0) && (paramInt != 1))
    {
      this.s.a().a(null, 0.0F);
      this.u.a().a(null, 0.0F);
    }
    else
    {
      this.t.a().a(null, 0.0F);
      this.v.a().a(null, 0.0F);
    }
    this.al.clear();
    for (paramInt = 0; paramInt < this.aj; paramInt++)
    {
      Object localObject = this.ai[paramInt];
      if ((this.am) || (((f)localObject).a()))
      {
        switch (this.ak)
        {
        default: 
          localObject = null;
          break;
        case 3: 
          localObject = ((f)localObject).v.a();
          break;
        case 2: 
          localObject = ((f)localObject).t.a();
          break;
        case 1: 
          localObject = ((f)localObject).u.a();
          break;
        case 0: 
          localObject = ((f)localObject).s.a();
        }
        if (localObject != null)
        {
          this.al.add(localObject);
          ((m)localObject).a(localm);
        }
      }
    }
  }
  
  public void c()
  {
    int i = this.ak;
    float f1 = Float.MAX_VALUE;
    m localm1;
    switch (i)
    {
    default: 
      return;
    case 3: 
      localm1 = this.v.a();
      f1 = 0.0F;
      break;
    case 2: 
      localm1 = this.t.a();
      break;
    case 1: 
      localm1 = this.u.a();
      f1 = 0.0F;
      break;
    case 0: 
      localm1 = this.s.a();
    }
    int j = this.al.size();
    m localm2 = null;
    i = 0;
    Object localObject;
    for (float f2 = f1; i < j; f2 = f1)
    {
      localObject = (m)this.al.get(i);
      if (((m)localObject).i != 1) {
        return;
      }
      int k = this.ak;
      if ((k != 0) && (k != 2))
      {
        f1 = f2;
        if (((m)localObject).f > f2)
        {
          f1 = ((m)localObject).f;
          localm2 = ((m)localObject).e;
        }
      }
      else
      {
        f1 = f2;
        if (((m)localObject).f < f2)
        {
          f1 = ((m)localObject).f;
          localm2 = ((m)localObject).e;
        }
      }
      i++;
    }
    if (android.support.constraint.a.e.a() != null)
    {
      localObject = android.support.constraint.a.e.a();
      ((android.support.constraint.a.f)localObject).z += 1L;
    }
    localm1.e = localm2;
    localm1.f = f2;
    localm1.f();
    switch (this.ak)
    {
    default: 
      
    case 3: 
      this.t.a().a(localm2, f2);
      break;
    case 2: 
      this.v.a().a(localm2, f2);
      break;
    case 1: 
      this.s.a().a(localm2, f2);
      break;
    case 0: 
      this.u.a().a(localm2, f2);
    }
  }
}


/* Location:              ~/android/support/constraint/a/a/b.class
 *
 * Reversed by:           J
 */