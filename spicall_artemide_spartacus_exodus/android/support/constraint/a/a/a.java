package android.support.constraint.a.a;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class a
{
  private static int a(f paramf)
  {
    int i;
    if (paramf.F() == f.a.c)
    {
      if (paramf.H == 0) {
        i = (int)(paramf.r() * paramf.G);
      } else {
        i = (int)(paramf.r() / paramf.G);
      }
      paramf.j(i);
    }
    else if (paramf.G() == f.a.c)
    {
      if (paramf.H == 1) {
        i = (int)(paramf.p() * paramf.G);
      } else {
        i = (int)(paramf.p() / paramf.G);
      }
      paramf.k(i);
    }
    else
    {
      i = -1;
    }
    return i;
  }
  
  private static int a(f paramf, int paramInt)
  {
    int i = paramInt * 2;
    e locale1 = paramf.A[i];
    e locale2 = paramf.A[(i + 1)];
    if ((locale1.c != null) && (locale1.c.a == paramf.D) && (locale2.c != null) && (locale2.c.a == paramf.D))
    {
      i = paramf.D.f(paramInt);
      float f;
      if (paramInt == 0) {
        f = paramf.S;
      } else {
        f = paramf.T;
      }
      paramInt = paramf.f(paramInt);
      return (int)((i - locale1.e() - locale2.e() - paramInt) * f);
    }
    return 0;
  }
  
  private static int a(f paramf, int paramInt1, boolean paramBoolean, int paramInt2)
  {
    boolean bool = paramf.W;
    int i = 0;
    if (!bool) {
      return 0;
    }
    if ((paramf.w.c != null) && (paramInt1 == 1)) {
      j = 1;
    } else {
      j = 0;
    }
    int n;
    int i1;
    if (paramBoolean)
    {
      k = paramf.A();
      m = paramf.r() - paramf.A();
      n = paramInt1 * 2;
      i1 = n + 1;
    }
    else
    {
      k = paramf.r() - paramf.A();
      m = paramf.A();
      i1 = paramInt1 * 2;
      n = i1 + 1;
    }
    int i3;
    if ((paramf.A[i1].c != null) && (paramf.A[n].c == null))
    {
      i2 = n;
      i3 = -1;
      n = i1;
      i1 = i2;
    }
    else
    {
      i3 = 1;
    }
    if (j != 0) {
      paramInt2 -= k;
    }
    int i4 = paramf.A[n].e() * i3 + a(paramf, paramInt1);
    int i2 = paramInt2 + i4;
    if (paramInt1 == 0) {
      paramInt2 = paramf.p();
    } else {
      paramInt2 = paramf.r();
    }
    int i5 = paramInt2 * i3;
    Object localObject = paramf.A[n].a().h.iterator();
    for (paramInt2 = i; ((Iterator)localObject).hasNext(); paramInt2 = Math.max(paramInt2, a(((m)((Iterator)localObject).next()).a.a, paramInt1, paramBoolean, i2))) {}
    localObject = paramf.A[i1].a().h.iterator();
    for (i = 0; ((Iterator)localObject).hasNext(); i = Math.max(i, a(((m)((Iterator)localObject).next()).a.a, paramInt1, paramBoolean, i5 + i2))) {}
    int i6;
    if (j != 0)
    {
      i6 = paramInt2 - k;
      i += m;
    }
    else
    {
      if (paramInt1 == 0) {
        i6 = paramf.p();
      } else {
        i6 = paramf.r();
      }
      i += i6 * i3;
      i6 = paramInt2;
    }
    if (paramInt1 == 1)
    {
      localObject = paramf.w.a().h.iterator();
      paramInt2 = 0;
      while (((Iterator)localObject).hasNext())
      {
        m localm = (m)((Iterator)localObject).next();
        if (i3 == 1) {
          paramInt2 = Math.max(paramInt2, a(localm.a.a, paramInt1, paramBoolean, k + i2));
        } else {
          paramInt2 = Math.max(paramInt2, a(localm.a.a, paramInt1, paramBoolean, m * i3 + i2));
        }
      }
      if ((paramf.w.a().h.size() > 0) && (j == 0)) {
        if (i3 == 1) {
          paramInt2 += k;
        } else {
          paramInt2 -= m;
        }
      }
    }
    else
    {
      paramInt2 = 0;
    }
    int k = Math.max(i6, Math.max(i, paramInt2));
    int j = i2 + i5;
    int m = j;
    paramInt2 = i2;
    if (i3 == -1)
    {
      paramInt2 = j;
      m = i2;
    }
    if (paramBoolean)
    {
      k.a(paramf, paramInt1, paramInt2);
      paramf.a(paramInt2, m, paramInt1);
    }
    else
    {
      paramf.r.a(paramf, paramInt1);
      paramf.e(paramInt2, paramInt1);
    }
    if ((paramf.t(paramInt1) == f.a.c) && (paramf.G != 0.0F)) {
      paramf.r.a(paramf, paramInt1);
    }
    if ((paramf.A[n].c != null) && (paramf.A[i1].c != null))
    {
      localObject = paramf.k();
      if ((paramf.A[n].c.a == localObject) && (paramf.A[i1].c.a == localObject)) {
        paramf.r.a(paramf, paramInt1);
      }
    }
    return i4 + k;
  }
  
  private static int a(h paramh, int paramInt)
  {
    int i = paramInt * 2;
    List localList = paramh.a(paramInt);
    int j = localList.size();
    int k = 0;
    int m = 0;
    while (k < j)
    {
      f localf = (f)localList.get(k);
      e[] arrayOfe = localf.A;
      int n = i + 1;
      boolean bool;
      if ((arrayOfe[n].c != null) && ((localf.A[i].c == null) || (localf.A[n].c == null))) {
        bool = false;
      } else {
        bool = true;
      }
      m = Math.max(m, a(localf, paramInt, bool, 0));
      k++;
    }
    paramh.e[paramInt] = m;
    return m;
  }
  
  private static void a(e parame)
  {
    m localm = parame.a();
    if ((parame.c != null) && (parame.c.c != parame)) {
      parame.c.a().a(localm);
    }
  }
  
  private static void a(f paramf, int paramInt1, int paramInt2)
  {
    int i = paramInt1 * 2;
    e locale1 = paramf.A[i];
    e locale2 = paramf.A[(i + 1)];
    if ((locale1.c != null) && (locale2.c != null)) {
      j = 1;
    } else {
      j = 0;
    }
    if (j != 0)
    {
      k.a(paramf, paramInt1, a(paramf, paramInt1) + locale1.e());
      return;
    }
    if ((paramf.G != 0.0F) && (paramf.t(paramInt1) == f.a.c))
    {
      paramInt2 = a(paramf);
      j = (int)paramf.A[i].a().f;
      locale2.a().e = locale1.a();
      locale2.a().f = paramInt2;
      locale2.a().i = 1;
      paramf.a(j, j + paramInt2, paramInt1);
      return;
    }
    int j = paramInt2 - paramf.p(paramInt1);
    paramInt2 = j - paramf.f(paramInt1);
    paramf.a(paramInt2, j, paramInt1);
    k.a(paramf, paramInt1, paramInt2);
  }
  
  public static void a(g paramg)
  {
    if ((paramg.J() & 0x20) != 32)
    {
      b(paramg);
      return;
    }
    paramg.ax = true;
    paramg.as = false;
    paramg.at = false;
    paramg.au = false;
    ArrayList localArrayList = paramg.az;
    List localList = paramg.ar;
    int i;
    if (paramg.F() == f.a.b) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if (paramg.G() == f.a.b) {
      j = 1;
    } else {
      j = 0;
    }
    boolean bool;
    if ((i == 0) && (j == 0)) {
      bool = false;
    } else {
      bool = true;
    }
    localList.clear();
    Iterator localIterator = localArrayList.iterator();
    Object localObject;
    while (localIterator.hasNext())
    {
      localObject = (f)localIterator.next();
      ((f)localObject).r = null;
      ((f)localObject).Y = false;
      ((f)localObject).b();
    }
    localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      localObject = (f)localIterator.next();
      if ((((f)localObject).r == null) && (!a((f)localObject, localList, bool)))
      {
        b(paramg);
        paramg.ax = false;
        return;
      }
    }
    localIterator = localList.iterator();
    int k = 0;
    for (int m = 0; localIterator.hasNext(); m = Math.max(m, a((h)localObject, 1)))
    {
      localObject = (h)localIterator.next();
      k = Math.max(k, a((h)localObject, 0));
    }
    if (i != 0)
    {
      paramg.a(f.a.a);
      paramg.j(k);
      paramg.as = true;
      paramg.at = true;
      paramg.av = k;
    }
    if (j != 0)
    {
      paramg.b(f.a.a);
      paramg.k(m);
      paramg.as = true;
      paramg.au = true;
      paramg.aw = m;
    }
    a(localList, 0, paramg.p());
    a(localList, 1, paramg.r());
  }
  
  private static void a(g paramg, f paramf, h paramh)
  {
    paramh.d = false;
    paramg.ax = false;
    paramf.W = false;
  }
  
  public static void a(List<h> paramList, int paramInt1, int paramInt2)
  {
    int i = paramList.size();
    for (int j = 0; j < i; j++)
    {
      Iterator localIterator = ((h)paramList.get(j)).b(paramInt1).iterator();
      while (localIterator.hasNext())
      {
        f localf = (f)localIterator.next();
        if (localf.W) {
          a(localf, paramInt1, paramInt2);
        }
      }
    }
  }
  
  private static boolean a(f paramf, h paramh, List<h> paramList, boolean paramBoolean)
  {
    if (paramf == null) {
      return true;
    }
    paramf.X = false;
    g localg = (g)paramf.k();
    if (paramf.r == null)
    {
      paramf.W = true;
      paramh.a.add(paramf);
      paramf.r = paramh;
      if ((paramf.s.c == null) && (paramf.u.c == null) && (paramf.t.c == null) && (paramf.v.c == null) && (paramf.w.c == null) && (paramf.z.c == null))
      {
        a(localg, paramf, paramh);
        if (paramBoolean) {
          return false;
        }
      }
      Object localObject;
      if ((paramf.t.c != null) && (paramf.v.c != null))
      {
        localg.G();
        localObject = f.a.b;
        if (paramBoolean)
        {
          a(localg, paramf, paramh);
          return false;
        }
        if ((paramf.t.c.a != paramf.k()) || (paramf.v.c.a != paramf.k())) {
          a(localg, paramf, paramh);
        }
      }
      if ((paramf.s.c != null) && (paramf.u.c != null))
      {
        localg.F();
        localObject = f.a.b;
        if (paramBoolean)
        {
          a(localg, paramf, paramh);
          return false;
        }
        if ((paramf.s.c.a != paramf.k()) || (paramf.u.c.a != paramf.k())) {
          a(localg, paramf, paramh);
        }
      }
      if (paramf.F() == f.a.c) {
        i = 1;
      } else {
        i = 0;
      }
      if (paramf.G() == f.a.c) {
        j = 1;
      } else {
        j = 0;
      }
      if (((i ^ j) != 0) && (paramf.G != 0.0F))
      {
        a(paramf);
      }
      else if ((paramf.F() == f.a.c) || (paramf.G() == f.a.c))
      {
        a(localg, paramf, paramh);
        if (paramBoolean) {
          return false;
        }
      }
      if (((paramf.s.c == null) && (paramf.u.c == null)) || ((paramf.s.c != null) && (paramf.s.c.a == paramf.D) && (paramf.u.c == null)) || ((paramf.u.c != null) && (paramf.u.c.a == paramf.D) && (paramf.s.c == null)) || ((paramf.s.c != null) && (paramf.s.c.a == paramf.D) && (paramf.u.c != null) && (paramf.u.c.a == paramf.D) && (paramf.z.c == null) && (!(paramf instanceof i)) && (!(paramf instanceof j)))) {
        paramh.f.add(paramf);
      }
      if (((paramf.t.c == null) && (paramf.v.c == null)) || ((paramf.t.c != null) && (paramf.t.c.a == paramf.D) && (paramf.v.c == null)) || ((paramf.v.c != null) && (paramf.v.c.a == paramf.D) && (paramf.t.c == null)) || ((paramf.t.c != null) && (paramf.t.c.a == paramf.D) && (paramf.v.c != null) && (paramf.v.c.a == paramf.D) && (paramf.z.c == null) && (paramf.w.c == null) && (!(paramf instanceof i)) && (!(paramf instanceof j)))) {
        paramh.g.add(paramf);
      }
      if ((paramf instanceof j))
      {
        a(localg, paramf, paramh);
        if (paramBoolean) {
          return false;
        }
        localObject = (j)paramf;
        for (i = 0; i < ((j)localObject).aj; i++) {
          if (!a(localObject.ai[i], paramh, paramList, paramBoolean)) {
            return false;
          }
        }
      }
      int j = paramf.A.length;
      for (int i = 0; i < j; i++)
      {
        localObject = paramf.A[i];
        if ((((e)localObject).c != null) && (((e)localObject).c.a != paramf.k()))
        {
          if (((e)localObject).b == e.c.g)
          {
            a(localg, paramf, paramh);
            if (paramBoolean) {
              return false;
            }
          }
          else
          {
            a((e)localObject);
          }
          if (!a(((e)localObject).c.a, paramh, paramList, paramBoolean)) {
            return false;
          }
        }
      }
      return true;
    }
    if (paramf.r != paramh)
    {
      paramh.a.addAll(paramf.r.a);
      paramh.f.addAll(paramf.r.f);
      paramh.g.addAll(paramf.r.g);
      if (!paramf.r.d) {
        paramh.d = false;
      }
      paramList.remove(paramf.r);
      paramf = paramf.r.a.iterator();
      while (paramf.hasNext()) {
        ((f)paramf.next()).r = paramh;
      }
    }
    return true;
  }
  
  private static boolean a(f paramf, List<h> paramList, boolean paramBoolean)
  {
    h localh = new h(new ArrayList(), true);
    paramList.add(localh);
    return a(paramf, localh, paramList, paramBoolean);
  }
  
  private static void b(g paramg)
  {
    paramg.ar.clear();
    paramg.ar.add(0, new h(paramg.az));
  }
}


/* Location:              ~/android/support/constraint/a/a/a.class
 *
 * Reversed by:           J
 */