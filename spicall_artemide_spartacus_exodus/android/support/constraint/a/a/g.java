package android.support.constraint.a.a;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class g
  extends q
{
  private boolean aA = false;
  private p aB;
  private int aC = 7;
  private boolean aD = false;
  private boolean aE = false;
  protected android.support.constraint.a.e ai = new android.support.constraint.a.e();
  int aj;
  int ak;
  int al;
  int am;
  int an = 0;
  int ao = 0;
  d[] ap = new d[4];
  d[] aq = new d[4];
  public List<h> ar = new ArrayList();
  public boolean as = false;
  public boolean at = false;
  public boolean au = false;
  public int av = 0;
  public int aw = 0;
  public boolean ax = false;
  int ay = 0;
  
  private void V()
  {
    this.an = 0;
    this.ao = 0;
  }
  
  private void d(f paramf)
  {
    int i = this.an;
    d[] arrayOfd = this.aq;
    if (i + 1 >= arrayOfd.length) {
      this.aq = ((d[])Arrays.copyOf(arrayOfd, arrayOfd.length * 2));
    }
    this.aq[this.an] = new d(paramf, 0, M());
    this.an += 1;
  }
  
  private void e(f paramf)
  {
    int i = this.ao;
    d[] arrayOfd = this.ap;
    if (i + 1 >= arrayOfd.length) {
      this.ap = ((d[])Arrays.copyOf(arrayOfd, arrayOfd.length * 2));
    }
    this.ap[this.ao] = new d(paramf, 1, M());
    this.ao += 1;
  }
  
  public int J()
  {
    return this.aC;
  }
  
  public boolean K()
  {
    return this.aD;
  }
  
  public boolean L()
  {
    return this.aE;
  }
  
  public boolean M()
  {
    return this.aA;
  }
  
  public void N()
  {
    int i = this.I;
    int j = this.J;
    int k = Math.max(0, p());
    int m = Math.max(0, r());
    this.aD = false;
    this.aE = false;
    if (this.D != null)
    {
      if (this.aB == null) {
        this.aB = new p(this);
      }
      this.aB.a(this);
      h(this.aj);
      i(this.ak);
      E();
      a(this.ai.g());
    }
    else
    {
      this.I = 0;
      this.J = 0;
    }
    if (this.aC != 0)
    {
      if (!u(8)) {
        Q();
      }
      if (!u(32)) {
        R();
      }
      this.ai.c = true;
    }
    else
    {
      this.ai.c = false;
    }
    f.a locala1 = this.C[1];
    f.a locala2 = this.C[0];
    V();
    if (this.ar.size() == 0)
    {
      this.ar.clear();
      this.ar.add(0, new h(this.az));
    }
    int n = this.ar.size();
    ArrayList localArrayList = this.az;
    int i1;
    if ((F() != f.a.b) && (G() != f.a.b)) {
      i1 = 0;
    } else {
      i1 = 1;
    }
    int i2 = 0;
    int i5;
    for (int i3 = 0; (i3 < n) && (!this.ax); i3++) {
      if (!((h)this.ar.get(i3)).d)
      {
        if (u(32)) {
          if ((F() == f.a.a) && (G() == f.a.a)) {
            this.az = ((ArrayList)((h)this.ar.get(i3)).a());
          } else {
            this.az = ((ArrayList)((h)this.ar.get(i3)).a);
          }
        }
        V();
        int i4 = this.az.size();
        f localf1;
        for (i5 = 0; i5 < i4; i5++)
        {
          localf1 = (f)this.az.get(i5);
          if ((localf1 instanceof q)) {
            ((q)localf1).N();
          }
        }
        i5 = i2;
        int i6 = 0;
        boolean bool1 = true;
        i2 = n;
        n = i5;
        i5 = i6;
        while (bool1)
        {
          i6 = i5 + 1;
          i5 = n;
          int i7;
          boolean bool2;
          try
          {
            this.ai.b();
            i5 = n;
            V();
            i5 = n;
            b(this.ai);
            i7 = 0;
            for (;;)
            {
              if (i7 < i4)
              {
                i5 = n;
                localf1 = (f)this.az.get(i7);
              }
              try
              {
                localf1.b(this.ai);
                i7++;
              }
              catch (Exception localException2)
              {
                break label633;
              }
            }
            i5 = n;
            bool2 = d(this.ai);
            if (bool2) {
              try
              {
                this.ai.f();
              }
              catch (Exception localException1)
              {
                bool1 = bool2;
                n = i5;
                break label633;
              }
            }
            bool1 = bool2;
            n = i5;
          }
          catch (Exception localException3)
          {
            n = i5;
          }
          label633:
          localException3.printStackTrace();
          PrintStream localPrintStream = System.out;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("EXCEPTION : ");
          localStringBuilder.append(localException3);
          localPrintStream.println(localStringBuilder.toString());
          f localf2;
          if (bool1)
          {
            a(this.ai, k.a);
          }
          else
          {
            c(this.ai);
            for (i5 = 0; i5 < i4; i5++)
            {
              localf2 = (f)this.az.get(i5);
              if (localf2.C[0] == f.a.c) {
                if (localf2.p() < localf2.q())
                {
                  k.a[2] = true;
                  break;
                }
              }
              if (localf2.C[1] == f.a.c) {
                if (localf2.r() < localf2.s())
                {
                  k.a[2] = true;
                  break;
                }
              }
            }
          }
          if ((i1 != 0) && (i6 < 8) && (k.a[2] != 0))
          {
            int i8 = 0;
            int i9 = 0;
            i7 = 0;
            i5 = i6;
            for (i6 = i8; i6 < i4; i6++)
            {
              localf2 = (f)this.az.get(i6);
              i9 = Math.max(i9, localf2.I + localf2.p());
              i7 = Math.max(i7, localf2.J + localf2.r());
            }
            i9 = Math.max(this.P, i9);
            i6 = Math.max(this.Q, i7);
            if ((locala2 == f.a.b) && (p() < i9))
            {
              j(i9);
              this.C[0] = f.a.b;
              bool1 = true;
              n = 1;
            }
            else
            {
              bool1 = false;
            }
            if ((locala1 == f.a.b) && (r() < i6))
            {
              k(i6);
              this.C[1] = f.a.b;
              bool1 = true;
              n = 1;
            }
          }
          else
          {
            i5 = i6;
            bool1 = false;
          }
          i6 = Math.max(this.P, p());
          if (i6 > p())
          {
            j(i6);
            this.C[0] = f.a.a;
            bool1 = true;
            n = 1;
          }
          i6 = Math.max(this.Q, r());
          if (i6 > r())
          {
            k(i6);
            this.C[1] = f.a.a;
            bool1 = true;
            n = 1;
          }
          if (n == 0)
          {
            bool2 = bool1;
            i6 = n;
            if (this.C[0] == f.a.b)
            {
              bool2 = bool1;
              i6 = n;
              if (k > 0)
              {
                bool2 = bool1;
                i6 = n;
                if (p() > k)
                {
                  this.aD = true;
                  this.C[0] = f.a.a;
                  j(k);
                  bool2 = true;
                  i6 = 1;
                }
              }
            }
            if ((this.C[1] == f.a.b) && (m > 0) && (r() > m))
            {
              this.aE = true;
              this.C[1] = f.a.a;
              k(m);
              bool1 = true;
              n = 1;
            }
            else
            {
              bool1 = bool2;
              n = i6;
            }
          }
        }
        i5 = i2;
        ((h)this.ar.get(i3)).b();
        i2 = n;
        n = i5;
      }
    }
    this.az = ((ArrayList)localArrayList);
    if (this.D != null)
    {
      i5 = Math.max(this.P, p());
      n = Math.max(this.Q, r());
      this.aB.b(this);
      j(i5 + this.aj + this.al);
      k(n + this.ak + this.am);
    }
    else
    {
      this.I = i;
      this.J = j;
    }
    if (i2 != 0)
    {
      this.C[0] = locala2;
      this.C[1] = locala1;
    }
    a(this.ai.g());
    if (this == T()) {
      D();
    }
  }
  
  public void O()
  {
    Q();
    b(this.aC);
  }
  
  public void P()
  {
    m localm1 = a(e.c.b).a();
    m localm2 = a(e.c.c).a();
    localm1.a(null, 0.0F);
    localm2.a(null, 0.0F);
  }
  
  public void Q()
  {
    int i = this.az.size();
    b();
    for (int j = 0; j < i; j++) {
      ((f)this.az.get(j)).b();
    }
  }
  
  public void R()
  {
    if (!u(8)) {
      b(this.aC);
    }
    P();
  }
  
  public boolean S()
  {
    return false;
  }
  
  public void a(int paramInt)
  {
    this.aC = paramInt;
  }
  
  void a(f paramf, int paramInt)
  {
    if (paramInt == 0) {
      d(paramf);
    } else if (paramInt == 1) {
      e(paramf);
    }
  }
  
  public void a(android.support.constraint.a.e parame, boolean[] paramArrayOfBoolean)
  {
    paramArrayOfBoolean[2] = false;
    c(parame);
    int i = this.az.size();
    for (int j = 0; j < i; j++)
    {
      f localf = (f)this.az.get(j);
      localf.c(parame);
      if ((localf.C[0] == f.a.c) && (localf.p() < localf.q())) {
        paramArrayOfBoolean[2] = true;
      }
      if ((localf.C[1] == f.a.c) && (localf.r() < localf.s())) {
        paramArrayOfBoolean[2] = true;
      }
    }
  }
  
  public void a(boolean paramBoolean)
  {
    this.aA = paramBoolean;
  }
  
  public void b(int paramInt)
  {
    super.b(paramInt);
    int i = this.az.size();
    for (int j = 0; j < i; j++) {
      ((f)this.az.get(j)).b(paramInt);
    }
  }
  
  public boolean d(android.support.constraint.a.e parame)
  {
    a(parame);
    int i = this.az.size();
    for (int j = 0; j < i; j++)
    {
      f localf = (f)this.az.get(j);
      if ((localf instanceof g))
      {
        f.a locala1 = localf.C[0];
        f.a locala2 = localf.C[1];
        if (locala1 == f.a.b) {
          localf.a(f.a.a);
        }
        if (locala2 == f.a.b) {
          localf.b(f.a.a);
        }
        localf.a(parame);
        if (locala1 == f.a.b) {
          localf.a(locala1);
        }
        if (locala2 == f.a.b) {
          localf.b(locala2);
        }
      }
      else
      {
        k.a(this, parame, localf);
        localf.a(parame);
      }
    }
    if (this.an > 0) {
      c.a(this, parame, 0);
    }
    if (this.ao > 0) {
      c.a(this, parame, 1);
    }
    return true;
  }
  
  public void f()
  {
    this.ai.b();
    this.aj = 0;
    this.al = 0;
    this.ak = 0;
    this.am = 0;
    this.ar.clear();
    this.ax = false;
    super.f();
  }
  
  public void f(int paramInt1, int paramInt2)
  {
    if ((this.C[0] != f.a.b) && (this.c != null)) {
      this.c.a(paramInt1);
    }
    if ((this.C[1] != f.a.b) && (this.d != null)) {
      this.d.a(paramInt2);
    }
  }
  
  public boolean u(int paramInt)
  {
    boolean bool;
    if ((this.aC & paramInt) == paramInt) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:              ~/android/support/constraint/a/a/g.class
 *
 * Reversed by:           J
 */