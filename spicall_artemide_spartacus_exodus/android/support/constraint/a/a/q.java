package android.support.constraint.a.a;

import android.support.constraint.a.c;
import java.util.ArrayList;

public class q
  extends f
{
  protected ArrayList<f> az = new ArrayList();
  
  public void D()
  {
    super.D();
    Object localObject = this.az;
    if (localObject == null) {
      return;
    }
    int i = ((ArrayList)localObject).size();
    for (int j = 0; j < i; j++)
    {
      localObject = (f)this.az.get(j);
      ((f)localObject).b(t(), u());
      if (!(localObject instanceof g)) {
        ((f)localObject).D();
      }
    }
  }
  
  public void N()
  {
    D();
    Object localObject = this.az;
    if (localObject == null) {
      return;
    }
    int i = ((ArrayList)localObject).size();
    for (int j = 0; j < i; j++)
    {
      localObject = (f)this.az.get(j);
      if ((localObject instanceof q)) {
        ((q)localObject).N();
      }
    }
  }
  
  public g T()
  {
    Object localObject = k();
    g localg;
    if ((this instanceof g)) {
      localg = (g)this;
    } else {
      localg = null;
    }
    while (localObject != null)
    {
      f localf = ((f)localObject).k();
      if ((localObject instanceof g))
      {
        localg = (g)localObject;
        localObject = localf;
      }
      else
      {
        localObject = localf;
      }
    }
    return localg;
  }
  
  public void U()
  {
    this.az.clear();
  }
  
  public void a(c paramc)
  {
    super.a(paramc);
    int i = this.az.size();
    for (int j = 0; j < i; j++) {
      ((f)this.az.get(j)).a(paramc);
    }
  }
  
  public void b(int paramInt1, int paramInt2)
  {
    super.b(paramInt1, paramInt2);
    paramInt2 = this.az.size();
    for (paramInt1 = 0; paramInt1 < paramInt2; paramInt1++) {
      ((f)this.az.get(paramInt1)).b(v(), w());
    }
  }
  
  public void b(f paramf)
  {
    this.az.add(paramf);
    if (paramf.k() != null) {
      ((q)paramf.k()).c(paramf);
    }
    paramf.a(this);
  }
  
  public void c(f paramf)
  {
    this.az.remove(paramf);
    paramf.a(null);
  }
  
  public void f()
  {
    this.az.clear();
    super.f();
  }
}


/* Location:              ~/android/support/constraint/a/a/q.class
 *
 * Reversed by:           J
 */