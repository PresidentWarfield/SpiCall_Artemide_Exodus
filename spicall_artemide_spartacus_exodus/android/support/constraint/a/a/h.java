package android.support.constraint.a.a;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class h
{
  public List<f> a;
  int b = -1;
  int c = -1;
  public boolean d = false;
  public final int[] e = { this.b, this.c };
  List<f> f = new ArrayList();
  List<f> g = new ArrayList();
  HashSet<f> h = new HashSet();
  HashSet<f> i = new HashSet();
  List<f> j = new ArrayList();
  List<f> k = new ArrayList();
  
  h(List<f> paramList)
  {
    this.a = paramList;
  }
  
  h(List<f> paramList, boolean paramBoolean)
  {
    this.a = paramList;
    this.d = paramBoolean;
  }
  
  private void a(f paramf)
  {
    if (paramf.W)
    {
      if (paramf.h()) {
        return;
      }
      e locale = paramf.u.c;
      int m = 0;
      if (locale != null) {
        n = 1;
      } else {
        n = 0;
      }
      if (n != 0) {
        locale = paramf.u.c;
      } else {
        locale = paramf.s.c;
      }
      if (locale != null)
      {
        if (!locale.a.X) {
          a(locale.a);
        }
        if (locale.b == e.c.d)
        {
          i1 = locale.a.I;
          i1 = locale.a.p() + i1;
          break label143;
        }
        if (locale.b == e.c.b)
        {
          i1 = locale.a.I;
          break label143;
        }
      }
      int i1 = 0;
      label143:
      if (n != 0) {
        i1 -= paramf.u.e();
      } else {
        i1 += paramf.s.e() + paramf.p();
      }
      paramf.c(i1 - paramf.p(), i1);
      if (paramf.w.c != null)
      {
        locale = paramf.w.c;
        if (!locale.a.X) {
          a(locale.a);
        }
        i1 = locale.a.J + locale.a.O - paramf.O;
        paramf.d(i1, paramf.F + i1);
        paramf.X = true;
        return;
      }
      if (paramf.v.c != null) {
        m = 1;
      }
      if (m != 0) {
        locale = paramf.v.c;
      } else {
        locale = paramf.t.c;
      }
      int n = i1;
      if (locale != null)
      {
        if (!locale.a.X) {
          a(locale.a);
        }
        if (locale.b == e.c.e)
        {
          n = locale.a.J + locale.a.r();
        }
        else
        {
          n = i1;
          if (locale.b == e.c.c) {
            n = locale.a.J;
          }
        }
      }
      if (m != 0) {
        i1 = n - paramf.v.e();
      } else {
        i1 = n + (paramf.t.e() + paramf.r());
      }
      paramf.d(i1 - paramf.r(), i1);
      paramf.X = true;
    }
  }
  
  private void a(ArrayList<f> paramArrayList, f paramf)
  {
    if (paramf.Y) {
      return;
    }
    paramArrayList.add(paramf);
    paramf.Y = true;
    if (paramf.h()) {
      return;
    }
    boolean bool = paramf instanceof j;
    int m = 0;
    Object localObject;
    if (bool)
    {
      localObject = (j)paramf;
      n = ((j)localObject).aj;
      for (i1 = 0; i1 < n; i1++) {
        a(paramArrayList, localObject.ai[i1]);
      }
    }
    int n = paramf.A.length;
    for (int i1 = m; i1 < n; i1++)
    {
      e locale = paramf.A[i1].c;
      if (locale != null)
      {
        localObject = locale.a;
        if ((locale != null) && (localObject != paramf.k())) {
          a(paramArrayList, (f)localObject);
        }
      }
    }
  }
  
  List<f> a()
  {
    if (!this.j.isEmpty()) {
      return this.j;
    }
    int m = this.a.size();
    for (int n = 0; n < m; n++)
    {
      f localf = (f)this.a.get(n);
      if (!localf.W) {
        a((ArrayList)this.j, localf);
      }
    }
    this.k.clear();
    this.k.addAll(this.a);
    this.k.removeAll(this.j);
    return this.j;
  }
  
  public List<f> a(int paramInt)
  {
    if (paramInt == 0) {
      return this.f;
    }
    if (paramInt == 1) {
      return this.g;
    }
    return null;
  }
  
  void a(f paramf, int paramInt)
  {
    if (paramInt == 0) {
      this.h.add(paramf);
    } else if (paramInt == 1) {
      this.i.add(paramf);
    }
  }
  
  Set<f> b(int paramInt)
  {
    if (paramInt == 0) {
      return this.h;
    }
    if (paramInt == 1) {
      return this.i;
    }
    return null;
  }
  
  void b()
  {
    int m = this.k.size();
    for (int n = 0; n < m; n++) {
      a((f)this.k.get(n));
    }
  }
}


/* Location:              ~/android/support/constraint/a/a/h.class
 *
 * Reversed by:           J
 */