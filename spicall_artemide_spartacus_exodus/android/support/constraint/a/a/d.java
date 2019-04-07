package android.support.constraint.a.a;

import java.util.ArrayList;

public class d
{
  protected f a;
  protected f b;
  protected f c;
  protected f d;
  protected f e;
  protected f f;
  protected f g;
  protected ArrayList<f> h;
  protected int i;
  protected int j;
  protected float k = 0.0F;
  protected boolean l;
  protected boolean m;
  protected boolean n;
  private int o;
  private boolean p = false;
  private boolean q;
  
  public d(f paramf, int paramInt, boolean paramBoolean)
  {
    this.a = paramf;
    this.o = paramInt;
    this.p = paramBoolean;
  }
  
  private static boolean a(f paramf, int paramInt)
  {
    boolean bool;
    if ((paramf.l() != 8) && (paramf.C[paramInt] == f.a.c) && ((paramf.g[paramInt] == 0) || (paramf.g[paramInt] == 3))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void b()
  {
    int i1 = this.o * 2;
    Object localObject1 = this.a;
    boolean bool1 = false;
    Object localObject2 = localObject1;
    int i2 = 0;
    while (i2 == 0)
    {
      this.i += 1;
      Object localObject3 = ((f)localObject1).af;
      int i3 = this.o;
      Object localObject4 = null;
      localObject3[i3] = null;
      ((f)localObject1).ae[this.o] = null;
      if (((f)localObject1).l() != 8)
      {
        if (this.b == null) {
          this.b = ((f)localObject1);
        }
        this.d = ((f)localObject1);
        if ((localObject1.C[this.o] == f.a.c) && ((localObject1.g[this.o] == 0) || (localObject1.g[this.o] == 3) || (localObject1.g[this.o] == 2)))
        {
          this.j += 1;
          float f1 = localObject1.ad[this.o];
          if (f1 > 0.0F) {
            this.k += localObject1.ad[this.o];
          }
          if (a((f)localObject1, this.o))
          {
            if (f1 < 0.0F) {
              this.l = true;
            } else {
              this.m = true;
            }
            if (this.h == null) {
              this.h = new ArrayList();
            }
            this.h.add(localObject1);
          }
          if (this.f == null) {
            this.f = ((f)localObject1);
          }
          localObject3 = this.g;
          if (localObject3 != null) {
            ((f)localObject3).ae[this.o] = localObject1;
          }
          this.g = ((f)localObject1);
        }
      }
      if (localObject2 != localObject1) {
        ((f)localObject2).af[this.o] = localObject1;
      }
      localObject3 = localObject1.A[(i1 + 1)].c;
      localObject2 = localObject4;
      if (localObject3 != null)
      {
        localObject3 = ((e)localObject3).a;
        localObject2 = localObject4;
        if (localObject3.A[i1].c != null) {
          if (localObject3.A[i1].c.a != localObject1) {
            localObject2 = localObject4;
          } else {
            localObject2 = localObject3;
          }
        }
      }
      if (localObject2 == null)
      {
        localObject2 = localObject1;
        i2 = 1;
      }
      localObject4 = localObject1;
      localObject1 = localObject2;
      localObject2 = localObject4;
    }
    this.c = ((f)localObject1);
    if ((this.o == 0) && (this.p)) {
      this.e = this.c;
    } else {
      this.e = this.a;
    }
    boolean bool2 = bool1;
    if (this.m)
    {
      bool2 = bool1;
      if (this.l) {
        bool2 = true;
      }
    }
    this.n = bool2;
  }
  
  public void a()
  {
    if (!this.q) {
      b();
    }
    this.q = true;
  }
}


/* Location:              ~/android/support/constraint/a/a/d.class
 *
 * Reversed by:           J
 */