package android.support.constraint.a.a;

public class k
{
  static boolean[] a = new boolean[3];
  
  static void a(int paramInt, f paramf)
  {
    paramf.g();
    m localm1 = paramf.s.a();
    m localm2 = paramf.t.a();
    m localm3 = paramf.u.a();
    m localm4 = paramf.v.a();
    if ((paramInt & 0x8) == 8) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    int i;
    if ((paramf.C[0] == f.a.c) && (a(paramf, 0))) {
      i = 1;
    } else {
      i = 0;
    }
    if ((localm1.g != 4) && (localm3.g != 4)) {
      if ((paramf.C[0] != f.a.a) && ((i == 0) || (paramf.l() != 8)))
      {
        if (i != 0)
        {
          i = paramf.p();
          localm1.b(1);
          localm3.b(1);
          if ((paramf.s.c == null) && (paramf.u.c == null))
          {
            if (paramInt != 0) {
              localm3.a(localm1, 1, paramf.i());
            } else {
              localm3.a(localm1, i);
            }
          }
          else if ((paramf.s.c != null) && (paramf.u.c == null))
          {
            if (paramInt != 0) {
              localm3.a(localm1, 1, paramf.i());
            } else {
              localm3.a(localm1, i);
            }
          }
          else if ((paramf.s.c == null) && (paramf.u.c != null))
          {
            if (paramInt != 0) {
              localm1.a(localm3, -1, paramf.i());
            } else {
              localm1.a(localm3, -i);
            }
          }
          else if ((paramf.s.c != null) && (paramf.u.c != null))
          {
            if (paramInt != 0)
            {
              paramf.i().a(localm1);
              paramf.i().a(localm3);
            }
            if (paramf.G == 0.0F)
            {
              localm1.b(3);
              localm3.b(3);
              localm1.b(localm3, 0.0F);
              localm3.b(localm1, 0.0F);
            }
            else
            {
              localm1.b(2);
              localm3.b(2);
              localm1.b(localm3, -i);
              localm3.b(localm1, i);
              paramf.j(i);
            }
          }
        }
      }
      else if ((paramf.s.c == null) && (paramf.u.c == null))
      {
        localm1.b(1);
        localm3.b(1);
        if (paramInt != 0) {
          localm3.a(localm1, 1, paramf.i());
        } else {
          localm3.a(localm1, paramf.p());
        }
      }
      else if ((paramf.s.c != null) && (paramf.u.c == null))
      {
        localm1.b(1);
        localm3.b(1);
        if (paramInt != 0) {
          localm3.a(localm1, 1, paramf.i());
        } else {
          localm3.a(localm1, paramf.p());
        }
      }
      else if ((paramf.s.c == null) && (paramf.u.c != null))
      {
        localm1.b(1);
        localm3.b(1);
        localm1.a(localm3, -paramf.p());
        if (paramInt != 0) {
          localm1.a(localm3, -1, paramf.i());
        } else {
          localm1.a(localm3, -paramf.p());
        }
      }
      else if ((paramf.s.c != null) && (paramf.u.c != null))
      {
        localm1.b(2);
        localm3.b(2);
        if (paramInt != 0)
        {
          paramf.i().a(localm1);
          paramf.i().a(localm3);
          localm1.b(localm3, -1, paramf.i());
          localm3.b(localm1, 1, paramf.i());
        }
        else
        {
          localm1.b(localm3, -paramf.p());
          localm3.b(localm1, paramf.p());
        }
      }
    }
    if ((paramf.C[1] == f.a.c) && (a(paramf, 1))) {
      i = 1;
    } else {
      i = 0;
    }
    if ((localm2.g != 4) && (localm4.g != 4)) {
      if ((paramf.C[1] != f.a.a) && ((i == 0) || (paramf.l() != 8)))
      {
        if (i != 0)
        {
          i = paramf.r();
          localm2.b(1);
          localm4.b(1);
          if ((paramf.t.c == null) && (paramf.v.c == null))
          {
            if (paramInt != 0) {
              localm4.a(localm2, 1, paramf.j());
            } else {
              localm4.a(localm2, i);
            }
          }
          else if ((paramf.t.c != null) && (paramf.v.c == null))
          {
            if (paramInt != 0) {
              localm4.a(localm2, 1, paramf.j());
            } else {
              localm4.a(localm2, i);
            }
          }
          else if ((paramf.t.c == null) && (paramf.v.c != null))
          {
            if (paramInt != 0) {
              localm2.a(localm4, -1, paramf.j());
            } else {
              localm2.a(localm4, -i);
            }
          }
          else if ((paramf.t.c != null) && (paramf.v.c != null))
          {
            if (paramInt != 0)
            {
              paramf.j().a(localm2);
              paramf.i().a(localm4);
            }
            if (paramf.G == 0.0F)
            {
              localm2.b(3);
              localm4.b(3);
              localm2.b(localm4, 0.0F);
              localm4.b(localm2, 0.0F);
            }
            else
            {
              localm2.b(2);
              localm4.b(2);
              localm2.b(localm4, -i);
              localm4.b(localm2, i);
              paramf.k(i);
              if (paramf.O > 0) {
                paramf.w.a().a(1, localm2, paramf.O);
              }
            }
          }
        }
      }
      else if ((paramf.t.c == null) && (paramf.v.c == null))
      {
        localm2.b(1);
        localm4.b(1);
        if (paramInt != 0) {
          localm4.a(localm2, 1, paramf.j());
        } else {
          localm4.a(localm2, paramf.r());
        }
        if (paramf.w.c != null)
        {
          paramf.w.a().b(1);
          localm2.a(1, paramf.w.a(), -paramf.O);
        }
      }
      else if ((paramf.t.c != null) && (paramf.v.c == null))
      {
        localm2.b(1);
        localm4.b(1);
        if (paramInt != 0) {
          localm4.a(localm2, 1, paramf.j());
        } else {
          localm4.a(localm2, paramf.r());
        }
        if (paramf.O > 0) {
          paramf.w.a().a(1, localm2, paramf.O);
        }
      }
      else if ((paramf.t.c == null) && (paramf.v.c != null))
      {
        localm2.b(1);
        localm4.b(1);
        if (paramInt != 0) {
          localm2.a(localm4, -1, paramf.j());
        } else {
          localm2.a(localm4, -paramf.r());
        }
        if (paramf.O > 0) {
          paramf.w.a().a(1, localm2, paramf.O);
        }
      }
      else if ((paramf.t.c != null) && (paramf.v.c != null))
      {
        localm2.b(2);
        localm4.b(2);
        if (paramInt != 0)
        {
          localm2.b(localm4, -1, paramf.j());
          localm4.b(localm2, 1, paramf.j());
          paramf.j().a(localm2);
          paramf.i().a(localm4);
        }
        else
        {
          localm2.b(localm4, -paramf.r());
          localm4.b(localm2, paramf.r());
        }
        if (paramf.O > 0) {
          paramf.w.a().a(1, localm2, paramf.O);
        }
      }
    }
  }
  
  static void a(f paramf, int paramInt1, int paramInt2)
  {
    int i = paramInt1 * 2;
    int j = i + 1;
    paramf.A[i].a().e = paramf.k().s.a();
    paramf.A[i].a().f = paramInt2;
    paramf.A[i].a().i = 1;
    paramf.A[j].a().e = paramf.A[i].a();
    paramf.A[j].a().f = paramf.f(paramInt1);
    paramf.A[j].a().i = 1;
  }
  
  static void a(g paramg, android.support.constraint.a.e parame, f paramf)
  {
    int i;
    int j;
    if ((paramg.C[0] != f.a.b) && (paramf.C[0] == f.a.d))
    {
      i = paramf.s.d;
      j = paramg.p() - paramf.u.d;
      paramf.s.f = parame.a(paramf.s);
      paramf.u.f = parame.a(paramf.u);
      parame.a(paramf.s.f, i);
      parame.a(paramf.u.f, j);
      paramf.a = 2;
      paramf.c(i, j);
    }
    if ((paramg.C[1] != f.a.b) && (paramf.C[1] == f.a.d))
    {
      j = paramf.t.d;
      i = paramg.r() - paramf.v.d;
      paramf.t.f = parame.a(paramf.t);
      paramf.v.f = parame.a(paramf.v);
      parame.a(paramf.t.f, j);
      parame.a(paramf.v.f, i);
      if ((paramf.O > 0) || (paramf.l() == 8))
      {
        paramf.w.f = parame.a(paramf.w);
        parame.a(paramf.w.f, paramf.O + j);
      }
      paramf.b = 2;
      paramf.d(j, i);
    }
  }
  
  private static boolean a(f paramf, int paramInt)
  {
    if (paramf.C[paramInt] != f.a.c) {
      return false;
    }
    float f = paramf.G;
    int i = 1;
    if (f != 0.0F)
    {
      paramf = paramf.C;
      if (paramInt == 0) {
        paramInt = i;
      } else {
        paramInt = 0;
      }
      return paramf[paramInt] != f.a.c;
    }
    if (paramInt == 0)
    {
      if (paramf.e != 0) {
        return false;
      }
      if ((paramf.h != 0) || (paramf.i != 0)) {
        return false;
      }
    }
    else if (paramf.f != 0)
    {
      return false;
    }
    return (paramf.k == 0) && (paramf.l == 0);
  }
  
  static boolean a(g paramg, android.support.constraint.a.e parame, int paramInt1, int paramInt2, d paramd)
  {
    Object localObject1 = paramd.a;
    f localf1 = paramd.c;
    Object localObject2 = paramd.b;
    f localf2 = paramd.d;
    Object localObject3 = paramd.e;
    float f1 = paramd.k;
    f localf3 = paramd.f;
    paramd = paramd.g;
    paramg = paramg.C[paramInt1];
    paramg = f.a.b;
    int i;
    int j;
    int k;
    if (paramInt1 == 0)
    {
      if (((f)localObject3).Z == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (((f)localObject3).Z == 1) {
        j = 1;
      } else {
        j = 0;
      }
      if (((f)localObject3).Z == 2) {
        k = 1;
      } else {
        k = 0;
      }
    }
    else
    {
      if (((f)localObject3).aa == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (((f)localObject3).aa == 1) {
        j = 1;
      } else {
        j = 0;
      }
      if (((f)localObject3).aa == 2) {
        k = 1;
      } else {
        k = 0;
      }
    }
    paramd = (d)localObject1;
    int m = 0;
    int n = 0;
    int i1 = 0;
    float f2 = 0.0F;
    float f3 = 0.0F;
    float f4;
    float f5;
    while (n == 0)
    {
      int i2 = i1;
      f4 = f2;
      f5 = f3;
      if (paramd.l() != 8)
      {
        i2 = i1 + 1;
        if (paramInt1 == 0) {
          f4 = f2 + paramd.p();
        } else {
          f4 = f2 + paramd.r();
        }
        f5 = f4;
        if (paramd != localObject2) {
          f5 = f4 + paramd.A[paramInt2].e();
        }
        f4 = f5;
        if (paramd != localf2) {
          f4 = f5 + paramd.A[(paramInt2 + 1)].e();
        }
        f5 = f3 + paramd.A[paramInt2].e() + paramd.A[(paramInt2 + 1)].e();
      }
      paramg = paramd.A[paramInt2];
      i1 = m;
      if (paramd.l() != 8)
      {
        i1 = m;
        if (paramd.C[paramInt1] == f.a.c)
        {
          i1 = m + 1;
          if (paramInt1 == 0)
          {
            if (paramd.e != 0) {
              return false;
            }
            if ((paramd.h != 0) || (paramd.i != 0)) {
              return false;
            }
          }
          else
          {
            if (paramd.f != 0) {
              return false;
            }
            if ((paramd.k != 0) || (paramd.l != 0)) {
              break label465;
            }
          }
          if (paramd.G != 0.0F)
          {
            return false;
            label465:
            return false;
          }
        }
      }
      paramg = paramd.A[(paramInt2 + 1)].c;
      if (paramg != null)
      {
        paramg = paramg.a;
        if ((paramg.A[paramInt2].c != null) && (paramg.A[paramInt2].c.a == paramd)) {
          break label531;
        }
        paramg = null;
      }
      else
      {
        paramg = null;
      }
      label531:
      if (paramg != null)
      {
        m = i1;
        paramd = paramg;
        i1 = i2;
        f2 = f4;
        f3 = f5;
      }
      else
      {
        n = 1;
        m = i1;
        i1 = i2;
        f2 = f4;
        f3 = f5;
      }
    }
    localObject3 = localObject1.A[paramInt2].a();
    paramg = localf1.A;
    n = paramInt2 + 1;
    paramg = paramg[n].a();
    if ((((m)localObject3).c != null) && (paramg.c != null))
    {
      if ((((m)localObject3).c.i == 1) && (paramg.c.i == 1))
      {
        if ((m > 0) && (m != i1)) {
          return false;
        }
        if ((k == 0) && (i == 0) && (j == 0))
        {
          f4 = 0.0F;
        }
        else
        {
          if (localObject2 != null) {
            f5 = localObject2.A[paramInt2].e();
          } else {
            f5 = 0.0F;
          }
          f4 = f5;
          if (localf2 != null) {
            f4 = f5 + localf2.A[n].e();
          }
        }
        float f6 = ((m)localObject3).c.f;
        f5 = paramg.c.f;
        if (f6 < f5) {
          f5 = f5 - f6 - f2;
        } else {
          f5 = f6 - f5 - f2;
        }
        if ((m > 0) && (m == i1))
        {
          if ((paramd.k() != null) && (paramd.k().C[paramInt1] == f.a.b)) {
            return false;
          }
          f5 = f5 + f2 - f3;
          paramg = (g)localObject1;
          while (paramg != null)
          {
            if (android.support.constraint.a.e.g != null)
            {
              paramd = android.support.constraint.a.e.g;
              paramd.B -= 1L;
              paramd = android.support.constraint.a.e.g;
              paramd.s += 1L;
              paramd = android.support.constraint.a.e.g;
              paramd.y += 1L;
            }
            paramd = paramg.af[paramInt1];
            if (paramd == null)
            {
              f4 = f6;
              if (paramg != localf1) {}
            }
            else
            {
              f4 = f5 / m;
              if (f1 > 0.0F) {
                if (paramg.ad[paramInt1] == -1.0F) {
                  f4 = 0.0F;
                } else {
                  f4 = paramg.ad[paramInt1] * f5 / f1;
                }
              }
              if (paramg.l() == 8) {
                f4 = 0.0F;
              }
              f3 = f6 + paramg.A[paramInt2].e();
              paramg.A[paramInt2].a().a(((m)localObject3).e, f3);
              localObject1 = paramg.A[n].a();
              localObject2 = ((m)localObject3).e;
              f4 = f3 + f4;
              ((m)localObject1).a((m)localObject2, f4);
              paramg.A[paramInt2].a().a(parame);
              paramg.A[n].a().a(parame);
              f4 += paramg.A[n].e();
            }
            paramg = paramd;
            f6 = f4;
          }
          return true;
        }
        if (f5 < 0.0F)
        {
          k = 1;
          i = 0;
          j = 0;
        }
        if (k != 0)
        {
          paramg = (g)localObject1;
          for (f4 = f6 + (f5 - f4) * paramg.g(paramInt1); paramg != null; f4 = f5)
          {
            if (android.support.constraint.a.e.g != null)
            {
              paramd = android.support.constraint.a.e.g;
              paramd.B -= 1L;
              paramd = android.support.constraint.a.e.g;
              paramd.s += 1L;
              paramd = android.support.constraint.a.e.g;
              paramd.y += 1L;
            }
            paramd = paramg.af[paramInt1];
            if (paramd == null)
            {
              f5 = f4;
              if (paramg != localf1) {}
            }
            else
            {
              if (paramInt1 == 0) {
                f5 = paramg.p();
              } else {
                f5 = paramg.r();
              }
              f4 += paramg.A[paramInt2].e();
              paramg.A[paramInt2].a().a(((m)localObject3).e, f4);
              localObject2 = paramg.A[n].a();
              localObject1 = ((m)localObject3).e;
              f4 += f5;
              ((m)localObject2).a((m)localObject1, f4);
              paramg.A[paramInt2].a().a(parame);
              paramg.A[n].a().a(parame);
              f5 = f4 + paramg.A[n].e();
            }
            paramg = paramd;
          }
        }
        else
        {
          if ((i == 0) && (j == 0)) {
            break label1828;
          }
          if (i != 0)
          {
            f3 = f5 - f4;
          }
          else
          {
            f3 = f5;
            if (j != 0) {
              f3 = f5 - f4;
            }
          }
          f5 = f3 / (i1 + 1);
          if (j != 0) {
            if (i1 > 1) {
              f5 = f3 / (i1 - 1);
            } else {
              f5 = f3 / 2.0F;
            }
          }
          if (((f)localObject1).l() != 8) {
            f4 = f6 + f5;
          } else {
            f4 = f6;
          }
          f3 = f4;
          if (j != 0)
          {
            f3 = f4;
            if (i1 > 1) {
              f3 = localObject2.A[paramInt2].e() + f6;
            }
          }
          paramg = (g)localObject1;
          f4 = f3;
          if (i != 0)
          {
            paramg = (g)localObject1;
            f4 = f3;
            if (localObject2 != null) {
              f4 = f3 + localObject2.A[paramInt2].e();
            }
          }
          for (paramg = (g)localObject1; paramg != null; paramg = paramd)
          {
            if (android.support.constraint.a.e.g != null)
            {
              paramd = android.support.constraint.a.e.g;
              paramd.B -= 1L;
              paramd = android.support.constraint.a.e.g;
              paramd.s += 1L;
              paramd = android.support.constraint.a.e.g;
              paramd.y += 1L;
            }
            paramd = paramg.af[paramInt1];
            if ((paramd == null) && (paramg != localf1)) {
              continue;
            }
            if (paramInt1 == 0) {
              f3 = paramg.p();
            } else {
              f3 = paramg.r();
            }
            f6 = f4;
            if (paramg != localObject2) {
              f6 = f4 + paramg.A[paramInt2].e();
            }
            paramg.A[paramInt2].a().a(((m)localObject3).e, f6);
            paramg.A[n].a().a(((m)localObject3).e, f6 + f3);
            paramg.A[paramInt2].a().a(parame);
            paramg.A[n].a().a(parame);
            f3 = f6 + (f3 + paramg.A[n].e());
            if (paramd != null)
            {
              f4 = f3;
              if (paramd.l() != 8) {
                f4 = f3 + f5;
              }
            }
            else
            {
              f4 = f3;
            }
          }
        }
        label1828:
        return true;
      }
      return false;
    }
    return false;
  }
}


/* Location:              ~/android/support/constraint/a/a/k.class
 *
 * Reversed by:           J
 */