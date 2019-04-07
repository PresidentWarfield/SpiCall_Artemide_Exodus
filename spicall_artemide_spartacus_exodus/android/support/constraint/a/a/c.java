package android.support.constraint.a.a;

import android.support.constraint.a.b;
import android.support.constraint.a.h;
import java.util.ArrayList;

class c
{
  static void a(g paramg, android.support.constraint.a.e parame, int paramInt)
  {
    int i = 0;
    int j;
    d[] arrayOfd;
    int k;
    if (paramInt == 0)
    {
      j = paramg.an;
      arrayOfd = paramg.aq;
      k = 0;
    }
    else
    {
      k = 2;
      j = paramg.ao;
      arrayOfd = paramg.ap;
    }
    while (i < j)
    {
      d locald = arrayOfd[i];
      locald.a();
      if (paramg.u(4))
      {
        if (!k.a(paramg, parame, paramInt, k, locald)) {
          a(paramg, parame, paramInt, k, locald);
        }
      }
      else {
        a(paramg, parame, paramInt, k, locald);
      }
      i++;
    }
  }
  
  static void a(g paramg, android.support.constraint.a.e parame, int paramInt1, int paramInt2, d paramd)
  {
    f localf1 = paramd.a;
    f localf2 = paramd.c;
    f localf3 = paramd.b;
    f localf4 = paramd.d;
    Object localObject1 = paramd.e;
    float f1 = paramd.k;
    Object localObject2 = paramd.f;
    localObject2 = paramd.g;
    int i;
    if (paramg.C[paramInt1] == f.a.b) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    int k;
    int m;
    int n;
    int i1;
    int i2;
    if (paramInt1 == 0)
    {
      if (((f)localObject1).Z == 0) {
        j = 1;
      } else {
        j = 0;
      }
      if (((f)localObject1).Z == 1) {
        k = 1;
      } else {
        k = 0;
      }
      if (((f)localObject1).Z == 2) {
        m = 1;
      } else {
        m = 0;
      }
      n = j;
      localObject2 = localf1;
      i1 = k;
      j = 0;
      i2 = m;
      k = n;
      n = i1;
    }
    else
    {
      if (((f)localObject1).aa == 0) {
        j = 1;
      } else {
        j = 0;
      }
      if (((f)localObject1).aa == 1) {
        k = 1;
      } else {
        k = 0;
      }
      if (((f)localObject1).aa == 2) {
        m = 1;
      } else {
        m = 0;
      }
      localObject2 = localf1;
      i1 = 0;
      n = k;
      k = j;
      i2 = m;
      j = i1;
    }
    Object localObject3;
    Object localObject4;
    Object localObject5;
    for (;;)
    {
      localObject3 = null;
      if (j != 0) {
        break;
      }
      localObject4 = localObject2.A[paramInt2];
      if ((i == 0) && (i2 == 0)) {
        m = 4;
      } else {
        m = 1;
      }
      i1 = ((e)localObject4).e();
      if ((((e)localObject4).c != null) && (localObject2 != localf1)) {
        i1 += ((e)localObject4).c.e();
      }
      if ((i2 != 0) && (localObject2 != localf1) && (localObject2 != localf3)) {
        m = 6;
      } else if ((k != 0) && (i != 0)) {
        m = 4;
      }
      if (((e)localObject4).c != null)
      {
        if (localObject2 == localf3) {
          parame.a(((e)localObject4).f, ((e)localObject4).c.f, i1, 5);
        } else {
          parame.a(((e)localObject4).f, ((e)localObject4).c.f, i1, 6);
        }
        parame.c(((e)localObject4).f, ((e)localObject4).c.f, i1, m);
      }
      if (i != 0)
      {
        if ((((f)localObject2).l() != 8) && (localObject2.C[paramInt1] == f.a.c)) {
          parame.a(localObject2.A[(paramInt2 + 1)].f, localObject2.A[paramInt2].f, 0, 5);
        }
        parame.a(localObject2.A[paramInt2].f, paramg.A[paramInt2].f, 0, 6);
      }
      localObject5 = localObject2.A[(paramInt2 + 1)].c;
      localObject4 = localObject3;
      if (localObject5 != null)
      {
        localObject5 = ((e)localObject5).a;
        localObject4 = localObject3;
        if (localObject5.A[paramInt2].c != null) {
          if (localObject5.A[paramInt2].c.a != localObject2) {
            localObject4 = localObject3;
          } else {
            localObject4 = localObject5;
          }
        }
      }
      if (localObject4 != null) {
        localObject2 = localObject4;
      } else {
        j = 1;
      }
    }
    if (localf4 != null)
    {
      localObject2 = localf2.A;
      j = paramInt2 + 1;
      if (localObject2[j].c != null)
      {
        localObject2 = localf4.A[j];
        parame.b(((e)localObject2).f, localf2.A[j].c.f, -((e)localObject2).e(), 5);
      }
    }
    if (i != 0)
    {
      paramg = paramg.A;
      j = paramInt2 + 1;
      parame.a(paramg[j].f, localf2.A[j].f, localf2.A[j].e(), 6);
    }
    paramg = paramd.h;
    label922:
    h localh1;
    h localh2;
    if (paramg != null)
    {
      j = paramg.size();
      if (j > 1)
      {
        float f2;
        if ((paramd.l) && (!paramd.n)) {
          f2 = paramd.j;
        } else {
          f2 = f1;
        }
        localObject2 = null;
        m = 0;
        float f3 = 0.0F;
        while (m < j)
        {
          localObject4 = (f)paramg.get(m);
          f1 = localObject4.ad[paramInt1];
          if (f1 < 0.0F)
          {
            if (paramd.n)
            {
              parame.c(localObject4.A[(paramInt2 + 1)].f, localObject4.A[paramInt2].f, 0, 4);
              break label922;
            }
            f1 = 1.0F;
          }
          if (f1 == 0.0F)
          {
            parame.c(localObject4.A[(paramInt2 + 1)].f, localObject4.A[paramInt2].f, 0, 6);
          }
          else
          {
            if (localObject2 != null)
            {
              localObject3 = localObject2.A[paramInt2].f;
              localObject2 = ((f)localObject2).A;
              i = paramInt2 + 1;
              localh1 = localObject2[i].f;
              localh2 = localObject4.A[paramInt2].f;
              localObject2 = localObject4.A[i].f;
              localObject5 = parame.c();
              ((b)localObject5).a(f3, f2, f1, (h)localObject3, localh1, localh2, (h)localObject2);
              parame.a((b)localObject5);
            }
            localObject2 = localObject4;
            f3 = f1;
          }
          m++;
        }
      }
    }
    if ((localf3 != null) && ((localf3 == localf4) || (i2 != 0)))
    {
      localObject4 = localf1.A[paramInt2];
      paramg = localf2.A;
      j = paramInt2 + 1;
      localObject2 = paramg[j];
      if (localf1.A[paramInt2].c != null) {
        paramg = localf1.A[paramInt2].c.f;
      } else {
        paramg = null;
      }
      if (localf2.A[j].c != null) {
        paramd = localf2.A[j].c.f;
      } else {
        paramd = null;
      }
      if (localf3 == localf4)
      {
        localObject4 = localf3.A[paramInt2];
        localObject2 = localf3.A[j];
      }
      if ((paramg != null) && (paramd != null))
      {
        if (paramInt1 == 0) {
          f1 = ((f)localObject1).S;
        } else {
          f1 = ((f)localObject1).T;
        }
        paramInt1 = ((e)localObject4).e();
        j = ((e)localObject2).e();
        parame.a(((e)localObject4).f, paramg, paramInt1, f1, paramd, ((e)localObject2).f, j, 5);
      }
    }
    else
    {
      if ((k != 0) && (localf3 != null))
      {
        if ((paramd.j > 0) && (paramd.i == paramd.j)) {
          i = 1;
        } else {
          i = 0;
        }
        paramd = localf3;
        localObject1 = paramd;
      }
      while (paramd != null)
      {
        for (localObject2 = paramd.af[paramInt1]; (localObject2 != null) && (((f)localObject2).l() == 8); localObject2 = localObject2.af[paramInt1]) {}
        if ((localObject2 == null) && (paramd != localf4)) {
          break label1721;
        }
        localObject3 = paramd.A[paramInt2];
        localh2 = ((e)localObject3).f;
        if (((e)localObject3).c != null) {
          localObject4 = ((e)localObject3).c.f;
        } else {
          localObject4 = null;
        }
        if (localObject1 != paramd)
        {
          paramg = localObject1.A[(paramInt2 + 1)].f;
        }
        else
        {
          paramg = (g)localObject4;
          if (paramd == localf3)
          {
            paramg = (g)localObject4;
            if (localObject1 == paramd) {
              if (localf1.A[paramInt2].c != null) {
                paramg = localf1.A[paramInt2].c.f;
              } else {
                paramg = null;
              }
            }
          }
        }
        i2 = ((e)localObject3).e();
        localObject4 = paramd.A;
        i1 = paramInt2 + 1;
        m = localObject4[i1].e();
        if (localObject2 != null)
        {
          localObject3 = localObject2.A[paramInt2];
          localObject4 = ((e)localObject3).f;
          localObject5 = paramd.A[i1].f;
        }
        else
        {
          localObject3 = localf2.A[i1].c;
          if (localObject3 != null) {
            localObject4 = ((e)localObject3).f;
          } else {
            localObject4 = null;
          }
          localObject5 = paramd.A[i1].f;
        }
        j = m;
        if (localObject3 != null) {
          j = m + ((e)localObject3).e();
        }
        m = i2;
        if (localObject1 != null) {
          m = i2 + localObject1.A[i1].e();
        }
        if ((localh2 != null) && (paramg != null) && (localObject4 != null) && (localObject5 != null))
        {
          if (paramd == localf3) {
            m = localf3.A[paramInt2].e();
          }
          if (paramd == localf4) {
            j = localf4.A[i1].e();
          }
          if (i != 0) {
            i2 = 6;
          } else {
            i2 = 4;
          }
          parame.a(localh2, paramg, m, 0.5F, (h)localObject4, (h)localObject5, j, i2);
        }
        label1721:
        if (paramd.l() != 8) {
          localObject1 = paramd;
        }
        paramd = (d)localObject2;
        continue;
        if ((n != 0) && (localf3 != null))
        {
          if ((paramd.j > 0) && (paramd.i == paramd.j)) {
            j = 1;
          } else {
            j = 0;
          }
          localObject2 = localf3;
          paramd = (d)localObject2;
          while (localObject2 != null)
          {
            for (paramg = localObject2.af[paramInt1]; (paramg != null) && (paramg.l() == 8); paramg = paramg.af[paramInt1]) {}
            if ((localObject2 != localf3) && (localObject2 != localf4) && (paramg != null))
            {
              if (paramg == localf4) {
                paramg = null;
              }
              localObject4 = localObject2.A[paramInt2];
              localh2 = ((e)localObject4).f;
              if (((e)localObject4).c != null) {
                localObject1 = ((e)localObject4).c.f;
              }
              localObject1 = paramd.A;
              i2 = paramInt2 + 1;
              localh1 = localObject1[i2].f;
              i = ((e)localObject4).e();
              m = localObject2.A[i2].e();
              if (paramg != null)
              {
                localObject1 = paramg.A[paramInt2];
                localObject3 = ((e)localObject1).f;
                if (((e)localObject1).c != null) {
                  localObject4 = ((e)localObject1).c.f;
                } else {
                  localObject4 = null;
                }
              }
              else
              {
                localObject5 = localObject2.A[i2].c;
                if (localObject5 != null) {
                  localObject1 = ((e)localObject5).f;
                } else {
                  localObject1 = null;
                }
                localObject4 = localObject2.A[i2].f;
                localObject3 = localObject1;
                localObject1 = localObject5;
              }
              if (localObject1 != null) {
                m += ((e)localObject1).e();
              }
              if (paramd != null) {
                i += paramd.A[i2].e();
              }
              if (j != 0) {
                i2 = 6;
              } else {
                i2 = 4;
              }
              if ((localh2 != null) && (localh1 != null) && (localObject3 != null) && (localObject4 != null)) {
                parame.a(localh2, localh1, i, 0.5F, (h)localObject3, (h)localObject4, m, i2);
              }
            }
            if (((f)localObject2).l() == 8) {
              localObject2 = paramd;
            }
            paramd = (d)localObject2;
            localObject2 = paramg;
          }
          paramg = localf3.A[paramInt2];
          paramd = localf1.A[paramInt2].c;
          localObject2 = localf4.A;
          paramInt1 = paramInt2 + 1;
          localObject2 = localObject2[paramInt1];
          localObject4 = localf2.A[paramInt1].c;
          if (paramd != null) {
            if (localf3 != localf4) {
              parame.c(paramg.f, paramd.f, paramg.e(), 5);
            } else if (localObject4 != null) {
              parame.a(paramg.f, paramd.f, paramg.e(), 0.5F, ((e)localObject2).f, ((e)localObject4).f, ((e)localObject2).e(), 5);
            }
          }
          if ((localObject4 != null) && (localf3 != localf4)) {
            parame.c(((e)localObject2).f, ((e)localObject4).f, -((e)localObject2).e(), 5);
          }
        }
      }
    }
    if (((k != 0) || (n != 0)) && (localf3 != null))
    {
      localObject4 = localf3.A[paramInt2];
      paramg = localf4.A;
      paramInt1 = paramInt2 + 1;
      localObject2 = paramg[paramInt1];
      if (((e)localObject4).c != null) {
        paramd = ((e)localObject4).c.f;
      } else {
        paramd = null;
      }
      if (((e)localObject2).c != null) {
        paramg = ((e)localObject2).c.f;
      } else {
        paramg = null;
      }
      if (localf2 != localf4)
      {
        paramg = localf2.A[paramInt1];
        if (paramg.c != null) {
          paramg = paramg.c.f;
        } else {
          paramg = null;
        }
      }
      if (localf3 == localf4)
      {
        localObject4 = localf3.A[paramInt2];
        localObject2 = localf3.A[paramInt1];
      }
      if ((paramd != null) && (paramg != null))
      {
        paramInt2 = ((e)localObject4).e();
        if (localf4 == null) {
          localObject1 = localf2;
        } else {
          localObject1 = localf4;
        }
        paramInt1 = localObject1.A[paramInt1].e();
        parame.a(((e)localObject4).f, paramd, paramInt2, 0.5F, paramg, ((e)localObject2).f, paramInt1, 5);
      }
    }
  }
}


/* Location:              ~/android/support/constraint/a/a/c.class
 *
 * Reversed by:           J
 */