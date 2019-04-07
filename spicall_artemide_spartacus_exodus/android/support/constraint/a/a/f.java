package android.support.constraint.a.a;

import android.support.constraint.a.c;
import java.util.ArrayList;

public class f
{
  public static float R = 0.5F;
  protected e[] A = { this.s, this.u, this.t, this.v, this.w, this.z };
  protected ArrayList<e> B = new ArrayList();
  protected a[] C = { a.a, a.a };
  f D = null;
  int E = 0;
  int F = 0;
  protected float G = 0.0F;
  protected int H = -1;
  protected int I = 0;
  protected int J = 0;
  int K = 0;
  int L = 0;
  protected int M = 0;
  protected int N = 0;
  int O = 0;
  protected int P;
  protected int Q;
  float S;
  float T;
  boolean U;
  boolean V;
  boolean W;
  boolean X;
  boolean Y;
  int Z;
  public int a = -1;
  int aa;
  boolean ab;
  boolean ac;
  float[] ad;
  protected f[] ae;
  protected f[] af;
  f ag;
  f ah;
  private int[] ai = { Integer.MAX_VALUE, Integer.MAX_VALUE };
  private float aj = 0.0F;
  private int ak = 0;
  private int al = 0;
  private int am = 0;
  private int an = 0;
  private int ao;
  private int ap;
  private Object aq;
  private int ar;
  private int as;
  private String at;
  private String au;
  public int b = -1;
  n c;
  n d;
  int e = 0;
  int f = 0;
  int[] g = new int[2];
  int h = 0;
  int i = 0;
  float j = 1.0F;
  int k = 0;
  int l = 0;
  float m = 1.0F;
  boolean n;
  boolean o;
  int p = -1;
  float q = 1.0F;
  h r = null;
  e s = new e(this, e.c.b);
  e t = new e(this, e.c.c);
  e u = new e(this, e.c.d);
  e v = new e(this, e.c.e);
  e w = new e(this, e.c.f);
  e x = new e(this, e.c.h);
  e y = new e(this, e.c.i);
  e z = new e(this, e.c.g);
  
  public f()
  {
    float f1 = R;
    this.S = f1;
    this.T = f1;
    this.ar = 0;
    this.as = 0;
    this.at = null;
    this.au = null;
    this.W = false;
    this.X = false;
    this.Y = false;
    this.Z = 0;
    this.aa = 0;
    this.ad = new float[] { -1.0F, -1.0F };
    this.ae = new f[] { null, null };
    this.af = new f[] { null, null };
    this.ag = null;
    this.ah = null;
    J();
  }
  
  private void J()
  {
    this.B.add(this.s);
    this.B.add(this.t);
    this.B.add(this.u);
    this.B.add(this.v);
    this.B.add(this.x);
    this.B.add(this.y);
    this.B.add(this.z);
    this.B.add(this.w);
  }
  
  private void a(android.support.constraint.a.e parame, boolean paramBoolean1, android.support.constraint.a.h paramh1, android.support.constraint.a.h paramh2, a parama, boolean paramBoolean2, e parame1, e parame2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, boolean paramBoolean3, boolean paramBoolean4, int paramInt5, int paramInt6, int paramInt7, float paramFloat2, boolean paramBoolean5)
  {
    android.support.constraint.a.h localh1 = parame.a(parame1);
    android.support.constraint.a.h localh2 = parame.a(parame2);
    android.support.constraint.a.h localh3 = parame.a(parame1.g());
    android.support.constraint.a.h localh4 = parame.a(parame2.g());
    if ((parame.c) && (parame1.a().i == 1) && (parame2.a().i == 1))
    {
      if (android.support.constraint.a.e.a() != null)
      {
        paramh1 = android.support.constraint.a.e.a();
        paramh1.s += 1L;
      }
      parame1.a().a(parame);
      parame2.a().a(parame);
      if ((!paramBoolean4) && (paramBoolean1)) {
        parame.a(paramh2, localh2, 0, 6);
      }
      return;
    }
    Object localObject;
    if (android.support.constraint.a.e.a() != null)
    {
      localObject = android.support.constraint.a.e.a();
      ((android.support.constraint.a.f)localObject).B += 1L;
    }
    boolean bool1 = parame1.j();
    boolean bool2 = parame2.j();
    boolean bool3 = this.z.j();
    if (bool1) {
      i1 = 1;
    } else {
      i1 = 0;
    }
    int i2 = i1;
    if (bool2) {
      i2 = i1 + 1;
    }
    int i1 = i2;
    if (bool3) {
      i1 = i2 + 1;
    }
    if (paramBoolean3) {
      i2 = 3;
    } else {
      i2 = paramInt5;
    }
    switch (1.b[parama.ordinal()])
    {
    default: 
      paramInt5 = 0;
      break;
    case 4: 
      if (i2 == 4) {
        paramInt5 = 0;
      } else {
        paramInt5 = 1;
      }
      break;
    case 3: 
      paramInt5 = 0;
      break;
    case 2: 
      paramInt5 = 0;
      break;
    case 1: 
      paramInt5 = 0;
    }
    if (this.as == 8)
    {
      paramInt2 = 0;
      paramInt5 = 0;
    }
    if (paramBoolean5) {
      if ((!bool1) && (!bool2) && (!bool3)) {
        parame.a(localh1, paramInt1);
      } else if ((bool1) && (!bool2)) {
        parame.c(localh1, localh3, parame1.e(), 6);
      }
    }
    if (paramInt5 == 0)
    {
      if (paramBoolean2)
      {
        parame.c(localh2, localh1, 0, 3);
        if (paramInt3 > 0) {
          parame.a(localh2, localh1, paramInt3, 6);
        }
        if (paramInt4 < Integer.MAX_VALUE) {
          parame.b(localh2, localh1, paramInt4, 6);
        }
      }
      else
      {
        parame.c(localh2, localh1, paramInt2, 6);
      }
      paramInt4 = paramInt6;
      paramInt2 = paramInt7;
    }
    else
    {
      if (paramInt6 == -2) {
        paramInt1 = paramInt2;
      } else {
        paramInt1 = paramInt6;
      }
      paramInt4 = paramInt7;
      if (paramInt7 == -2) {
        paramInt4 = paramInt2;
      }
      if (paramInt1 > 0)
      {
        parame.a(localh2, localh1, paramInt1, 6);
        paramInt2 = Math.max(paramInt2, paramInt1);
      }
      if (paramInt4 > 0)
      {
        parame.b(localh2, localh1, paramInt4, 6);
        paramInt7 = Math.min(paramInt2, paramInt4);
      }
      else
      {
        paramInt7 = paramInt2;
      }
      if (i2 == 1)
      {
        if (paramBoolean1) {
          parame.c(localh2, localh1, paramInt7, 6);
        } else if (paramBoolean4) {
          parame.c(localh2, localh1, paramInt7, 4);
        } else {
          parame.c(localh2, localh1, paramInt7, 1);
        }
      }
      else if (i2 == 2)
      {
        if ((parame1.d() != e.c.c) && (parame1.d() != e.c.e))
        {
          parama = parame.a(this.D.a(e.c.b));
          localObject = parame.a(this.D.a(e.c.d));
        }
        else
        {
          parama = parame.a(this.D.a(e.c.c));
          localObject = parame.a(this.D.a(e.c.e));
        }
        parame.a(parame.c().a(localh2, localh1, (android.support.constraint.a.h)localObject, parama, paramFloat2));
        paramInt2 = 0;
        break label765;
      }
      paramInt2 = paramInt5;
      label765:
      paramInt6 = paramInt4;
      if ((paramInt2 != 0) && (i1 != 2) && (!paramBoolean3))
      {
        paramInt2 = Math.max(paramInt1, paramInt7);
        if (paramInt6 > 0) {
          paramInt2 = Math.min(paramInt6, paramInt2);
        }
        parame.c(localh2, localh1, paramInt2, 6);
        paramInt5 = 0;
        paramInt2 = paramInt6;
        paramInt4 = paramInt1;
      }
      else
      {
        paramInt5 = paramInt2;
        paramInt4 = paramInt1;
        paramInt2 = paramInt6;
      }
    }
    parama = localh3;
    if ((paramBoolean5) && (!paramBoolean4))
    {
      if ((!bool1) && (!bool2) && (!bool3))
      {
        if (paramBoolean1) {
          parame.a(paramh2, localh2, 0, 5);
        }
      }
      else if ((bool1) && (!bool2))
      {
        if (paramBoolean1) {
          parame.a(paramh2, localh2, 0, 5);
        }
      }
      else if ((!bool1) && (bool2))
      {
        parame.c(localh2, localh4, -parame2.e(), 6);
        if (paramBoolean1) {
          parame.a(localh1, paramh1, 0, 5);
        }
      }
      else if ((bool1) && (bool2))
      {
        if (paramInt5 != 0)
        {
          if ((paramBoolean1) && (paramInt3 == 0)) {
            parame.a(localh2, localh1, 0, 6);
          }
          if (i2 == 0)
          {
            if ((paramInt2 <= 0) && (paramInt4 <= 0))
            {
              paramInt3 = 6;
              paramInt1 = 0;
            }
            else
            {
              paramInt3 = 4;
              paramInt1 = 1;
            }
            parame.c(localh1, parama, parame1.e(), paramInt3);
            parame.c(localh2, localh4, -parame2.e(), paramInt3);
            if ((paramInt2 <= 0) && (paramInt4 <= 0)) {
              paramInt2 = 0;
            } else {
              paramInt2 = 1;
            }
            paramInt3 = 5;
          }
          else if (i2 == 1)
          {
            paramInt2 = 1;
            paramInt3 = 6;
            paramInt1 = 1;
          }
          else if (i2 == 3)
          {
            if ((!paramBoolean3) && (this.p != -1) && (paramInt2 <= 0)) {
              paramInt1 = 6;
            } else {
              paramInt1 = 4;
            }
            parame.c(localh1, parama, parame1.e(), paramInt1);
            parame.c(localh2, localh4, -parame2.e(), paramInt1);
            paramInt2 = 1;
            paramInt3 = 5;
            paramInt1 = 1;
          }
          else
          {
            paramInt2 = 0;
            paramInt3 = 5;
            paramInt1 = 0;
          }
        }
        else
        {
          paramInt2 = 1;
          paramInt3 = 5;
          paramInt1 = 0;
        }
        if (paramInt2 != 0)
        {
          parame.a(localh1, parama, parame1.e(), paramFloat1, localh4, localh2, parame2.e(), paramInt3);
          paramBoolean2 = parame1.c.a instanceof b;
          paramBoolean3 = parame2.c.a instanceof b;
          if ((paramBoolean2) && (!paramBoolean3))
          {
            paramBoolean3 = paramBoolean1;
            paramBoolean2 = true;
            paramInt3 = 5;
            paramInt2 = 6;
            break label1352;
          }
          if ((!paramBoolean2) && (paramBoolean3))
          {
            paramBoolean2 = paramBoolean1;
            paramInt3 = 6;
            paramInt2 = 5;
            paramBoolean3 = true;
            break label1352;
          }
        }
        paramBoolean2 = paramBoolean1;
        paramBoolean3 = paramBoolean2;
        paramInt3 = 5;
        paramInt2 = 5;
        label1352:
        if (paramInt1 != 0)
        {
          paramInt3 = 6;
          paramInt2 = 6;
        }
        if (((paramInt5 == 0) && (paramBoolean3)) || (paramInt1 != 0)) {
          parame.a(localh1, parama, parame1.e(), paramInt3);
        }
        if (((paramInt5 == 0) && (paramBoolean2)) || (paramInt1 != 0)) {
          parame.b(localh2, localh4, -parame2.e(), paramInt2);
        }
        if (paramBoolean1) {
          parame.a(localh1, paramh1, 0, 6);
        } else {}
      }
      if (paramBoolean1) {
        parame.a(paramh2, localh2, 0, 6);
      }
      return;
    }
    if ((i1 < 2) && (paramBoolean1))
    {
      parame.a(localh1, paramh1, 0, 6);
      parame.a(paramh2, localh2, 0, 6);
    }
  }
  
  private boolean a(int paramInt)
  {
    paramInt *= 2;
    e locale = this.A[paramInt].c;
    boolean bool = true;
    if (locale != null)
    {
      locale = this.A[paramInt].c.c;
      e[] arrayOfe = this.A;
      if (locale != arrayOfe[paramInt])
      {
        paramInt++;
        if ((arrayOfe[paramInt].c != null) && (this.A[paramInt].c.c == this.A[paramInt])) {
          return bool;
        }
      }
    }
    bool = false;
    return bool;
  }
  
  public int A()
  {
    return this.O;
  }
  
  public Object B()
  {
    return this.aq;
  }
  
  public ArrayList<e> C()
  {
    return this.B;
  }
  
  public void D()
  {
    int i1 = this.I;
    int i2 = this.J;
    int i3 = this.E;
    int i4 = this.F;
    this.ak = i1;
    this.al = i2;
    this.am = (i3 + i1 - i1);
    this.an = (i4 + i2 - i2);
  }
  
  public void E()
  {
    f localf = k();
    if ((localf != null) && ((localf instanceof g)) && (((g)k()).S())) {
      return;
    }
    int i1 = 0;
    int i2 = this.B.size();
    while (i1 < i2)
    {
      ((e)this.B.get(i1)).i();
      i1++;
    }
  }
  
  public a F()
  {
    return this.C[0];
  }
  
  public a G()
  {
    return this.C[1];
  }
  
  public boolean H()
  {
    return ((this.s.c != null) && (this.s.c.c == this.s)) || ((this.u.c != null) && (this.u.c.c == this.u));
  }
  
  public boolean I()
  {
    return ((this.t.c != null) && (this.t.c.c == this.t)) || ((this.v.c != null) && (this.v.c.c == this.v));
  }
  
  public e a(e.c paramc)
  {
    switch (1.a[paramc.ordinal()])
    {
    default: 
      throw new AssertionError(paramc.name());
    case 9: 
      return null;
    case 8: 
      return this.y;
    case 7: 
      return this.x;
    case 6: 
      return this.z;
    case 5: 
      return this.w;
    case 4: 
      return this.v;
    case 3: 
      return this.u;
    case 2: 
      return this.t;
    }
    return this.s;
  }
  
  public void a(float paramFloat)
  {
    this.S = paramFloat;
  }
  
  public void a(int paramInt1, int paramInt2)
  {
    this.I = paramInt1;
    this.J = paramInt2;
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 == 0) {
      c(paramInt1, paramInt2);
    } else if (paramInt3 == 1) {
      d(paramInt1, paramInt2);
    }
    this.X = true;
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    this.e = paramInt1;
    this.h = paramInt2;
    this.i = paramInt3;
    this.j = paramFloat;
    if ((paramFloat < 1.0F) && (this.e == 0)) {
      this.e = 2;
    }
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i1 = paramInt3 - paramInt1;
    paramInt3 = paramInt4 - paramInt2;
    this.I = paramInt1;
    this.J = paramInt2;
    if (this.as == 8)
    {
      this.E = 0;
      this.F = 0;
      return;
    }
    if (this.C[0] == a.a)
    {
      paramInt1 = this.E;
      if (i1 < paramInt1) {}
    }
    else
    {
      paramInt1 = i1;
    }
    if (this.C[1] == a.a)
    {
      paramInt2 = this.F;
      if (paramInt3 < paramInt2) {}
    }
    else
    {
      paramInt2 = paramInt3;
    }
    this.E = paramInt1;
    this.F = paramInt2;
    paramInt1 = this.F;
    paramInt2 = this.Q;
    if (paramInt1 < paramInt2) {
      this.F = paramInt2;
    }
    paramInt2 = this.E;
    paramInt1 = this.P;
    if (paramInt2 < paramInt1) {
      this.E = paramInt1;
    }
    this.X = true;
  }
  
  public void a(e.c paramc1, f paramf, e.c paramc2, int paramInt1, int paramInt2)
  {
    a(paramc1).a(paramf.a(paramc2), paramInt1, paramInt2, e.b.b, 0, true);
  }
  
  public void a(a parama)
  {
    this.C[0] = parama;
    if (parama == a.b) {
      j(this.ao);
    }
  }
  
  public void a(f paramf)
  {
    this.D = paramf;
  }
  
  public void a(f paramf, float paramFloat, int paramInt)
  {
    a(e.c.g, paramf, e.c.g, paramInt, 0);
    this.aj = paramFloat;
  }
  
  public void a(c paramc)
  {
    this.s.a(paramc);
    this.t.a(paramc);
    this.u.a(paramc);
    this.v.a(paramc);
    this.w.a(paramc);
    this.z.a(paramc);
    this.x.a(paramc);
    this.y.a(paramc);
  }
  
  public void a(android.support.constraint.a.e parame)
  {
    android.support.constraint.a.h localh1 = parame.a(this.s);
    Object localObject1 = parame.a(this.u);
    Object localObject2 = parame.a(this.t);
    android.support.constraint.a.h localh2 = parame.a(this.v);
    Object localObject3 = parame.a(this.w);
    Object localObject4 = this.D;
    boolean bool1;
    boolean bool2;
    boolean bool3;
    boolean bool6;
    if (localObject4 != null)
    {
      if ((localObject4 != null) && (localObject4.C[0] == a.b)) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      localObject4 = this.D;
      if ((localObject4 != null) && (localObject4.C[1] == a.b)) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      if (a(0))
      {
        ((g)this.D).a(this, 0);
        bool3 = true;
      }
      else
      {
        bool3 = H();
      }
      if (a(1))
      {
        ((g)this.D).a(this, 1);
        bool4 = true;
      }
      else
      {
        bool4 = I();
      }
      if ((bool1) && (this.as != 8) && (this.s.c == null) && (this.u.c == null)) {
        parame.a(parame.a(this.D.u), (android.support.constraint.a.h)localObject1, 0, 1);
      }
      if ((bool2) && (this.as != 8) && (this.t.c == null) && (this.v.c == null) && (this.w == null)) {
        parame.a(parame.a(this.D.v), localh2, 0, 1);
      }
      bool5 = bool2;
      bool2 = bool3;
      bool6 = bool4;
      bool3 = bool1;
      bool1 = bool5;
      bool4 = bool2;
      bool2 = bool6;
    }
    else
    {
      bool3 = false;
      bool1 = false;
      bool4 = false;
      bool2 = false;
    }
    int i1 = this.E;
    int i2 = this.P;
    int i3 = i1;
    if (i1 < i2) {
      i3 = i2;
    }
    i2 = this.F;
    int i4 = this.Q;
    i1 = i2;
    if (i2 < i4) {
      i1 = i4;
    }
    if (this.C[0] != a.c) {
      bool5 = true;
    } else {
      bool5 = false;
    }
    if (this.C[1] != a.c) {
      bool6 = true;
    } else {
      bool6 = false;
    }
    this.p = this.H;
    float f1 = this.G;
    this.q = f1;
    int i5 = this.e;
    int i6 = this.f;
    if ((f1 > 0.0F) && (this.as != 8))
    {
      i4 = i5;
      if (this.C[0] == a.c)
      {
        i4 = i5;
        if (i5 == 0) {
          i4 = 3;
        }
      }
      i2 = i6;
      if (this.C[1] == a.c)
      {
        i2 = i6;
        if (i6 == 0) {
          i2 = 3;
        }
      }
      if ((this.C[0] == a.c) && (this.C[1] == a.c) && (i4 == 3) && (i2 == 3))
      {
        a(bool3, bool1, bool5, bool6);
      }
      else
      {
        if ((this.C[0] == a.c) && (i4 == 3))
        {
          this.p = 0;
          i6 = (int)(this.q * this.F);
          if (this.C[1] != a.c)
          {
            i4 = i6;
            i6 = i1;
            i3 = 4;
            i1 = 0;
            break label834;
          }
          i3 = i4;
          i5 = i1;
          i1 = 1;
          i4 = i6;
          i6 = i5;
          break label834;
        }
        if ((this.C[1] == a.c) && (i2 == 3))
        {
          this.p = 1;
          if (this.H == -1) {
            this.q = (1.0F / this.q);
          }
          i6 = (int)(this.q * this.E);
          if (this.C[0] != a.c)
          {
            i2 = i4;
            i4 = i3;
            i5 = 4;
            i1 = 0;
            i3 = i2;
            i2 = i5;
            break label834;
          }
          i1 = i4;
          i4 = i3;
          i5 = 1;
          i3 = i1;
          i1 = i5;
          break label834;
        }
      }
      i5 = i4;
      i4 = i3;
      i6 = i1;
      i1 = 1;
      i3 = i5;
    }
    else
    {
      i2 = i6;
      int i7 = 0;
      i6 = i1;
      i4 = i3;
      i1 = i7;
      i3 = i5;
    }
    label834:
    localObject4 = this.g;
    localObject4[0] = i3;
    localObject4[1] = i2;
    if (i1 != 0)
    {
      i5 = this.p;
      if (i5 != 0) {
        if (i5 != -1) {
          break label883;
        }
      }
      bool5 = true;
      break label886;
    }
    label883:
    boolean bool5 = false;
    label886:
    if ((this.C[0] == a.b) && ((this instanceof g))) {
      bool6 = true;
    } else {
      bool6 = false;
    }
    boolean bool7 = this.z.j() ^ true;
    if (this.a != 2)
    {
      localObject4 = this.D;
      if (localObject4 != null) {
        localObject4 = parame.a(((f)localObject4).u);
      } else {
        localObject4 = null;
      }
      localObject5 = this.D;
      if (localObject5 != null) {
        localObject5 = parame.a(((f)localObject5).s);
      } else {
        localObject5 = null;
      }
      a(parame, bool3, (android.support.constraint.a.h)localObject5, (android.support.constraint.a.h)localObject4, this.C[0], bool6, this.s, this.u, this.I, i4, this.P, this.ai[0], this.S, bool5, bool4, i3, this.h, this.i, this.j, bool7);
    }
    localObject4 = localObject2;
    Object localObject5 = localObject1;
    localObject1 = this;
    if (((f)localObject1).b == 2) {
      return;
    }
    if ((localObject1.C[1] == a.b) && ((localObject1 instanceof g))) {
      bool3 = true;
    } else {
      bool3 = false;
    }
    if (i1 != 0)
    {
      i3 = ((f)localObject1).p;
      if ((i3 == 1) || (i3 == -1))
      {
        bool4 = true;
        break label1137;
      }
    }
    boolean bool4 = false;
    label1137:
    if (((f)localObject1).O > 0) {
      if (((f)localObject1).w.a().i == 1)
      {
        ((f)localObject1).w.a().a(parame);
      }
      else
      {
        localObject2 = parame;
        ((android.support.constraint.a.e)localObject2).c((android.support.constraint.a.h)localObject3, (android.support.constraint.a.h)localObject4, A(), 6);
        if (((f)localObject1).w.c != null)
        {
          ((android.support.constraint.a.e)localObject2).c((android.support.constraint.a.h)localObject3, ((android.support.constraint.a.e)localObject2).a(((f)localObject1).w.c), 0, 6);
          bool5 = false;
          break label1234;
        }
      }
    }
    bool5 = bool7;
    label1234:
    android.support.constraint.a.e locale = parame;
    localObject2 = localObject4;
    localObject3 = ((f)localObject1).D;
    if (localObject3 != null) {
      localObject3 = locale.a(((f)localObject3).v);
    } else {
      localObject3 = null;
    }
    localObject4 = ((f)localObject1).D;
    if (localObject4 != null) {
      localObject4 = locale.a(((f)localObject4).t);
    } else {
      localObject4 = null;
    }
    a(parame, bool1, (android.support.constraint.a.h)localObject4, (android.support.constraint.a.h)localObject3, localObject1.C[1], bool3, ((f)localObject1).t, ((f)localObject1).v, ((f)localObject1).J, i6, ((f)localObject1).Q, localObject1.ai[1], ((f)localObject1).T, bool4, bool2, i2, ((f)localObject1).k, ((f)localObject1).l, ((f)localObject1).m, bool5);
    if (i1 != 0)
    {
      localObject3 = this;
      if (((f)localObject3).p == 1) {
        parame.a(localh2, (android.support.constraint.a.h)localObject2, (android.support.constraint.a.h)localObject5, localh1, ((f)localObject3).q, 6);
      } else {
        parame.a((android.support.constraint.a.h)localObject5, localh1, localh2, (android.support.constraint.a.h)localObject2, ((f)localObject3).q, 6);
      }
    }
    localObject3 = this;
    if (((f)localObject3).z.j()) {
      parame.a((f)localObject3, ((f)localObject3).z.g().c(), (float)Math.toRadians(((f)localObject3).aj + 90.0F), ((f)localObject3).z.e());
    }
  }
  
  public void a(Object paramObject)
  {
    this.aq = paramObject;
  }
  
  public void a(String paramString)
  {
    this.at = paramString;
  }
  
  public void a(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    if (this.p == -1) {
      if ((paramBoolean3) && (!paramBoolean4))
      {
        this.p = 0;
      }
      else if ((!paramBoolean3) && (paramBoolean4))
      {
        this.p = 1;
        if (this.H == -1) {
          this.q = (1.0F / this.q);
        }
      }
    }
    if ((this.p == 0) && ((!this.t.j()) || (!this.v.j()))) {
      this.p = 1;
    } else if ((this.p == 1) && ((!this.s.j()) || (!this.u.j()))) {
      this.p = 0;
    }
    if ((this.p == -1) && ((!this.t.j()) || (!this.v.j()) || (!this.s.j()) || (!this.u.j()))) {
      if ((this.t.j()) && (this.v.j()))
      {
        this.p = 0;
      }
      else if ((this.s.j()) && (this.u.j()))
      {
        this.q = (1.0F / this.q);
        this.p = 1;
      }
    }
    if (this.p == -1) {
      if ((paramBoolean1) && (!paramBoolean2))
      {
        this.p = 0;
      }
      else if ((!paramBoolean1) && (paramBoolean2))
      {
        this.q = (1.0F / this.q);
        this.p = 1;
      }
    }
    if (this.p == -1) {
      if ((this.h > 0) && (this.k == 0))
      {
        this.p = 0;
      }
      else if ((this.h == 0) && (this.k > 0))
      {
        this.q = (1.0F / this.q);
        this.p = 1;
      }
    }
    if ((this.p == -1) && (paramBoolean1) && (paramBoolean2))
    {
      this.q = (1.0F / this.q);
      this.p = 1;
    }
  }
  
  public boolean a()
  {
    boolean bool;
    if (this.as != 8) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void b()
  {
    for (int i1 = 0; i1 < 6; i1++) {
      this.A[i1].a().b();
    }
  }
  
  public void b(float paramFloat)
  {
    this.T = paramFloat;
  }
  
  public void b(int paramInt)
  {
    k.a(paramInt, this);
  }
  
  public void b(int paramInt1, int paramInt2)
  {
    this.M = paramInt1;
    this.N = paramInt2;
  }
  
  public void b(int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    this.f = paramInt1;
    this.k = paramInt2;
    this.l = paramInt3;
    this.m = paramFloat;
    if ((paramFloat < 1.0F) && (this.f == 0)) {
      this.f = 2;
    }
  }
  
  public void b(a parama)
  {
    this.C[1] = parama;
    if (parama == a.b) {
      k(this.ap);
    }
  }
  
  public void b(android.support.constraint.a.e parame)
  {
    parame.a(this.s);
    parame.a(this.t);
    parame.a(this.u);
    parame.a(this.v);
    if (this.O > 0) {
      parame.a(this.w);
    }
  }
  
  public void b(String paramString)
  {
    int i5;
    int i6;
    String str;
    if ((paramString != null) && (paramString.length() != 0))
    {
      int i1 = -1;
      int i2 = paramString.length();
      int i3 = paramString.indexOf(',');
      int i4 = 0;
      i5 = i1;
      i6 = i4;
      if (i3 > 0)
      {
        i5 = i1;
        i6 = i4;
        if (i3 < i2 - 1)
        {
          str = paramString.substring(0, i3);
          if (str.equalsIgnoreCase("W"))
          {
            i5 = 0;
          }
          else
          {
            i5 = i1;
            if (str.equalsIgnoreCase("H")) {
              i5 = 1;
            }
          }
          i6 = i3 + 1;
        }
      }
      i1 = paramString.indexOf(':');
      if ((i1 >= 0) && (i1 < i2 - 1))
      {
        str = paramString.substring(i6, i1);
        paramString = paramString.substring(i1 + 1);
        if ((str.length() <= 0) || (paramString.length() <= 0)) {}
      }
    }
    try
    {
      float f1 = Float.parseFloat(str);
      f2 = Float.parseFloat(paramString);
      if ((f1 > 0.0F) && (f2 > 0.0F))
      {
        if (i5 == 1) {
          f2 = Math.abs(f2 / f1);
        } else {
          f2 = Math.abs(f1 / f2);
        }
      }
      else {
        f2 = 0.0F;
      }
    }
    catch (NumberFormatException paramString)
    {
      float f2;
      for (;;) {}
    }
    f2 = 0.0F;
    break label255;
    paramString = paramString.substring(i6);
    if (paramString.length() > 0) {}
    try
    {
      f2 = Float.parseFloat(paramString);
    }
    catch (NumberFormatException paramString)
    {
      for (;;) {}
    }
    f2 = 0.0F;
    label255:
    if (f2 > 0.0F)
    {
      this.G = f2;
      this.H = i5;
    }
    return;
    this.G = 0.0F;
  }
  
  public void b(boolean paramBoolean)
  {
    this.n = paramBoolean;
  }
  
  public void c() {}
  
  public void c(float paramFloat)
  {
    this.ad[0] = paramFloat;
  }
  
  public void c(int paramInt)
  {
    this.ai[0] = paramInt;
  }
  
  public void c(int paramInt1, int paramInt2)
  {
    this.I = paramInt1;
    this.E = (paramInt2 - paramInt1);
    paramInt2 = this.E;
    paramInt1 = this.P;
    if (paramInt2 < paramInt1) {
      this.E = paramInt1;
    }
  }
  
  public void c(android.support.constraint.a.e parame)
  {
    int i1 = parame.b(this.s);
    int i2 = parame.b(this.t);
    int i3 = parame.b(this.u);
    int i4 = parame.b(this.v);
    int i5;
    if ((i3 - i1 >= 0) && (i4 - i2 >= 0) && (i1 != Integer.MIN_VALUE) && (i1 != Integer.MAX_VALUE) && (i2 != Integer.MIN_VALUE) && (i2 != Integer.MAX_VALUE) && (i3 != Integer.MIN_VALUE) && (i3 != Integer.MAX_VALUE) && (i4 != Integer.MIN_VALUE))
    {
      i5 = i4;
      if (i4 != Integer.MAX_VALUE) {}
    }
    else
    {
      i5 = 0;
      i1 = 0;
      i2 = 0;
      i3 = 0;
    }
    a(i1, i2, i3, i5);
  }
  
  public void c(boolean paramBoolean)
  {
    this.o = paramBoolean;
  }
  
  public void d(float paramFloat)
  {
    this.ad[1] = paramFloat;
  }
  
  public void d(int paramInt)
  {
    this.ai[1] = paramInt;
  }
  
  public void d(int paramInt1, int paramInt2)
  {
    this.J = paramInt1;
    this.F = (paramInt2 - paramInt1);
    paramInt2 = this.F;
    paramInt1 = this.Q;
    if (paramInt2 < paramInt1) {
      this.F = paramInt1;
    }
  }
  
  public boolean d()
  {
    int i1 = this.e;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (i1 == 0)
    {
      bool2 = bool1;
      if (this.G == 0.0F)
      {
        bool2 = bool1;
        if (this.h == 0)
        {
          bool2 = bool1;
          if (this.i == 0)
          {
            bool2 = bool1;
            if (this.C[0] == a.c) {
              bool2 = true;
            }
          }
        }
      }
    }
    return bool2;
  }
  
  public void e(int paramInt)
  {
    this.as = paramInt;
  }
  
  void e(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0) {
      this.K = paramInt1;
    } else if (paramInt2 == 1) {
      this.L = paramInt1;
    }
  }
  
  public boolean e()
  {
    int i1 = this.f;
    boolean bool = true;
    if ((i1 != 0) || (this.G != 0.0F) || (this.k != 0) || (this.l != 0) || (this.C[1] != a.c)) {
      bool = false;
    }
    return bool;
  }
  
  public int f(int paramInt)
  {
    if (paramInt == 0) {
      return p();
    }
    if (paramInt == 1) {
      return r();
    }
    return 0;
  }
  
  public void f()
  {
    this.s.i();
    this.t.i();
    this.u.i();
    this.v.i();
    this.w.i();
    this.x.i();
    this.y.i();
    this.z.i();
    this.D = null;
    this.aj = 0.0F;
    this.E = 0;
    this.F = 0;
    this.G = 0.0F;
    this.H = -1;
    this.I = 0;
    this.J = 0;
    this.ak = 0;
    this.al = 0;
    this.am = 0;
    this.an = 0;
    this.M = 0;
    this.N = 0;
    this.O = 0;
    this.P = 0;
    this.Q = 0;
    this.ao = 0;
    this.ap = 0;
    float f1 = R;
    this.S = f1;
    this.T = f1;
    this.C[0] = a.a;
    this.C[1] = a.a;
    this.aq = null;
    this.ar = 0;
    this.as = 0;
    this.au = null;
    this.U = false;
    this.V = false;
    this.Z = 0;
    this.aa = 0;
    this.ab = false;
    this.ac = false;
    Object localObject = this.ad;
    localObject[0] = -1.0F;
    localObject[1] = -1.0F;
    this.a = -1;
    this.b = -1;
    localObject = this.ai;
    localObject[0] = Integer.MAX_VALUE;
    localObject[1] = Integer.MAX_VALUE;
    this.e = 0;
    this.f = 0;
    this.j = 1.0F;
    this.m = 1.0F;
    this.i = Integer.MAX_VALUE;
    this.l = Integer.MAX_VALUE;
    this.h = 0;
    this.k = 0;
    this.p = -1;
    this.q = 1.0F;
    localObject = this.c;
    if (localObject != null) {
      ((n)localObject).b();
    }
    localObject = this.d;
    if (localObject != null) {
      ((n)localObject).b();
    }
    this.r = null;
    this.W = false;
    this.X = false;
    this.Y = false;
  }
  
  public float g(int paramInt)
  {
    if (paramInt == 0) {
      return this.S;
    }
    if (paramInt == 1) {
      return this.T;
    }
    return -1.0F;
  }
  
  public void g()
  {
    for (int i1 = 0; i1 < 6; i1++) {
      this.A[i1].a().c();
    }
  }
  
  public void h(int paramInt)
  {
    this.I = paramInt;
  }
  
  public boolean h()
  {
    return (this.s.a().i == 1) && (this.u.a().i == 1) && (this.t.a().i == 1) && (this.v.a().i == 1);
  }
  
  public n i()
  {
    if (this.c == null) {
      this.c = new n();
    }
    return this.c;
  }
  
  public void i(int paramInt)
  {
    this.J = paramInt;
  }
  
  public n j()
  {
    if (this.d == null) {
      this.d = new n();
    }
    return this.d;
  }
  
  public void j(int paramInt)
  {
    this.E = paramInt;
    paramInt = this.E;
    int i1 = this.P;
    if (paramInt < i1) {
      this.E = i1;
    }
  }
  
  public f k()
  {
    return this.D;
  }
  
  public void k(int paramInt)
  {
    this.F = paramInt;
    int i1 = this.F;
    paramInt = this.Q;
    if (i1 < paramInt) {
      this.F = paramInt;
    }
  }
  
  public int l()
  {
    return this.as;
  }
  
  public void l(int paramInt)
  {
    if (paramInt < 0) {
      this.P = 0;
    } else {
      this.P = paramInt;
    }
  }
  
  public String m()
  {
    return this.at;
  }
  
  public void m(int paramInt)
  {
    if (paramInt < 0) {
      this.Q = 0;
    } else {
      this.Q = paramInt;
    }
  }
  
  public int n()
  {
    return this.I;
  }
  
  public void n(int paramInt)
  {
    this.ao = paramInt;
  }
  
  public int o()
  {
    return this.J;
  }
  
  public void o(int paramInt)
  {
    this.ap = paramInt;
  }
  
  public int p()
  {
    if (this.as == 8) {
      return 0;
    }
    return this.E;
  }
  
  int p(int paramInt)
  {
    if (paramInt == 0) {
      return this.K;
    }
    if (paramInt == 1) {
      return this.L;
    }
    return 0;
  }
  
  public int q()
  {
    return this.ao;
  }
  
  public void q(int paramInt)
  {
    this.O = paramInt;
  }
  
  public int r()
  {
    if (this.as == 8) {
      return 0;
    }
    return this.F;
  }
  
  public void r(int paramInt)
  {
    this.Z = paramInt;
  }
  
  public int s()
  {
    return this.ap;
  }
  
  public void s(int paramInt)
  {
    this.aa = paramInt;
  }
  
  public int t()
  {
    return this.ak + this.M;
  }
  
  public a t(int paramInt)
  {
    if (paramInt == 0) {
      return F();
    }
    if (paramInt == 1) {
      return G();
    }
    return null;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Object localObject;
    if (this.au != null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("type: ");
      ((StringBuilder)localObject).append(this.au);
      ((StringBuilder)localObject).append(" ");
      localObject = ((StringBuilder)localObject).toString();
    }
    else
    {
      localObject = "";
    }
    localStringBuilder.append((String)localObject);
    if (this.at != null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("id: ");
      ((StringBuilder)localObject).append(this.at);
      ((StringBuilder)localObject).append(" ");
      localObject = ((StringBuilder)localObject).toString();
    }
    else
    {
      localObject = "";
    }
    localStringBuilder.append((String)localObject);
    localStringBuilder.append("(");
    localStringBuilder.append(this.I);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.J);
    localStringBuilder.append(") - (");
    localStringBuilder.append(this.E);
    localStringBuilder.append(" x ");
    localStringBuilder.append(this.F);
    localStringBuilder.append(") wrap: (");
    localStringBuilder.append(this.ao);
    localStringBuilder.append(" x ");
    localStringBuilder.append(this.ap);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
  
  public int u()
  {
    return this.al + this.N;
  }
  
  protected int v()
  {
    return this.I + this.M;
  }
  
  protected int w()
  {
    return this.J + this.N;
  }
  
  public int x()
  {
    return n() + this.E;
  }
  
  public int y()
  {
    return o() + this.F;
  }
  
  public boolean z()
  {
    boolean bool;
    if (this.O > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static enum a
  {
    private a() {}
  }
}


/* Location:              ~/android/support/constraint/a/a/f.class
 *
 * Reversed by:           J
 */