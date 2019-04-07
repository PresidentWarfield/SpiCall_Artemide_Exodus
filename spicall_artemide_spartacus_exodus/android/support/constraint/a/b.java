package android.support.constraint.a;

public class b
  implements e.a
{
  h a = null;
  float b = 0.0F;
  boolean c = false;
  public final a d;
  boolean e = false;
  
  public b(c paramc)
  {
    this.d = new a(this, paramc);
  }
  
  public b a(float paramFloat1, float paramFloat2, float paramFloat3, h paramh1, h paramh2, h paramh3, h paramh4)
  {
    this.b = 0.0F;
    if ((paramFloat2 != 0.0F) && (paramFloat1 != paramFloat3))
    {
      if (paramFloat1 == 0.0F)
      {
        this.d.a(paramh1, 1.0F);
        this.d.a(paramh2, -1.0F);
      }
      else if (paramFloat3 == 0.0F)
      {
        this.d.a(paramh3, 1.0F);
        this.d.a(paramh4, -1.0F);
      }
      else
      {
        paramFloat1 = paramFloat1 / paramFloat2 / (paramFloat3 / paramFloat2);
        this.d.a(paramh1, 1.0F);
        this.d.a(paramh2, -1.0F);
        this.d.a(paramh4, paramFloat1);
        this.d.a(paramh3, -paramFloat1);
      }
    }
    else
    {
      this.d.a(paramh1, 1.0F);
      this.d.a(paramh2, -1.0F);
      this.d.a(paramh4, 1.0F);
      this.d.a(paramh3, -1.0F);
    }
    return this;
  }
  
  public b a(e parame, int paramInt)
  {
    this.d.a(parame.a(paramInt, "ep"), 1.0F);
    this.d.a(parame.a(paramInt, "em"), -1.0F);
    return this;
  }
  
  b a(h paramh, int paramInt)
  {
    this.a = paramh;
    float f = paramInt;
    paramh.d = f;
    this.b = f;
    this.e = true;
    return this;
  }
  
  public b a(h paramh1, h paramh2, int paramInt)
  {
    int i = 0;
    int j = 0;
    if (paramInt != 0)
    {
      i = j;
      j = paramInt;
      if (paramInt < 0)
      {
        j = paramInt * -1;
        i = 1;
      }
      this.b = j;
    }
    if (i == 0)
    {
      this.d.a(paramh1, -1.0F);
      this.d.a(paramh2, 1.0F);
    }
    else
    {
      this.d.a(paramh1, 1.0F);
      this.d.a(paramh2, -1.0F);
    }
    return this;
  }
  
  b a(h paramh1, h paramh2, int paramInt1, float paramFloat, h paramh3, h paramh4, int paramInt2)
  {
    if (paramh2 == paramh3)
    {
      this.d.a(paramh1, 1.0F);
      this.d.a(paramh4, 1.0F);
      this.d.a(paramh2, -2.0F);
      return this;
    }
    if (paramFloat == 0.5F)
    {
      this.d.a(paramh1, 1.0F);
      this.d.a(paramh2, -1.0F);
      this.d.a(paramh3, -1.0F);
      this.d.a(paramh4, 1.0F);
      if ((paramInt1 > 0) || (paramInt2 > 0)) {
        this.b = (-paramInt1 + paramInt2);
      }
    }
    else if (paramFloat <= 0.0F)
    {
      this.d.a(paramh1, -1.0F);
      this.d.a(paramh2, 1.0F);
      this.b = paramInt1;
    }
    else if (paramFloat >= 1.0F)
    {
      this.d.a(paramh3, -1.0F);
      this.d.a(paramh4, 1.0F);
      this.b = paramInt2;
    }
    else
    {
      a locala = this.d;
      float f = 1.0F - paramFloat;
      locala.a(paramh1, f * 1.0F);
      this.d.a(paramh2, f * -1.0F);
      this.d.a(paramh3, -1.0F * paramFloat);
      this.d.a(paramh4, 1.0F * paramFloat);
      if ((paramInt1 > 0) || (paramInt2 > 0)) {
        this.b = (-paramInt1 * f + paramInt2 * paramFloat);
      }
    }
    return this;
  }
  
  b a(h paramh1, h paramh2, h paramh3, float paramFloat)
  {
    this.d.a(paramh1, -1.0F);
    this.d.a(paramh2, 1.0F - paramFloat);
    this.d.a(paramh3, paramFloat);
    return this;
  }
  
  public b a(h paramh1, h paramh2, h paramh3, int paramInt)
  {
    int i = 0;
    int j = 0;
    if (paramInt != 0)
    {
      i = j;
      j = paramInt;
      if (paramInt < 0)
      {
        j = paramInt * -1;
        i = 1;
      }
      this.b = j;
    }
    if (i == 0)
    {
      this.d.a(paramh1, -1.0F);
      this.d.a(paramh2, 1.0F);
      this.d.a(paramh3, 1.0F);
    }
    else
    {
      this.d.a(paramh1, 1.0F);
      this.d.a(paramh2, -1.0F);
      this.d.a(paramh3, -1.0F);
    }
    return this;
  }
  
  public b a(h paramh1, h paramh2, h paramh3, h paramh4, float paramFloat)
  {
    this.d.a(paramh1, -1.0F);
    this.d.a(paramh2, 1.0F);
    this.d.a(paramh3, paramFloat);
    this.d.a(paramh4, -paramFloat);
    return this;
  }
  
  public h a(e parame, boolean[] paramArrayOfBoolean)
  {
    return this.d.a(paramArrayOfBoolean, null);
  }
  
  public void a(e.a parama)
  {
    if ((parama instanceof b))
    {
      b localb = (b)parama;
      this.a = null;
      this.d.a();
      for (int i = 0; i < localb.d.a; i++)
      {
        parama = localb.d.a(i);
        float f = localb.d.b(i);
        this.d.a(parama, f, true);
      }
    }
  }
  
  boolean a()
  {
    h localh = this.a;
    boolean bool;
    if ((localh != null) && ((localh.f == h.a.a) || (this.b >= 0.0F))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean a(e parame)
  {
    parame = this.d.a(parame);
    boolean bool;
    if (parame == null)
    {
      bool = true;
    }
    else
    {
      c(parame);
      bool = false;
    }
    if (this.d.a == 0) {
      this.e = true;
    }
    return bool;
  }
  
  boolean a(h paramh)
  {
    return this.d.a(paramh);
  }
  
  public b b(h paramh, int paramInt)
  {
    if (paramInt < 0)
    {
      this.b = (paramInt * -1);
      this.d.a(paramh, 1.0F);
    }
    else
    {
      this.b = paramInt;
      this.d.a(paramh, -1.0F);
    }
    return this;
  }
  
  public b b(h paramh1, h paramh2, h paramh3, int paramInt)
  {
    int i = 0;
    int j = 0;
    if (paramInt != 0)
    {
      i = j;
      j = paramInt;
      if (paramInt < 0)
      {
        j = paramInt * -1;
        i = 1;
      }
      this.b = j;
    }
    if (i == 0)
    {
      this.d.a(paramh1, -1.0F);
      this.d.a(paramh2, 1.0F);
      this.d.a(paramh3, -1.0F);
    }
    else
    {
      this.d.a(paramh1, 1.0F);
      this.d.a(paramh2, -1.0F);
      this.d.a(paramh3, 1.0F);
    }
    return this;
  }
  
  public b b(h paramh1, h paramh2, h paramh3, h paramh4, float paramFloat)
  {
    this.d.a(paramh3, 0.5F);
    this.d.a(paramh4, 0.5F);
    this.d.a(paramh1, -0.5F);
    this.d.a(paramh2, -0.5F);
    this.b = (-paramFloat);
    return this;
  }
  
  h b(h paramh)
  {
    return this.d.a(null, paramh);
  }
  
  String b()
  {
    if (this.a == null)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("");
      ((StringBuilder)localObject1).append("0");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("");
      ((StringBuilder)localObject1).append(this.a);
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(" = ");
    Object localObject1 = ((StringBuilder)localObject2).toString();
    float f1 = this.b;
    int i = 0;
    int j;
    if (f1 != 0.0F)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append(this.b);
      localObject1 = ((StringBuilder)localObject2).toString();
      j = 1;
    }
    else
    {
      j = 0;
    }
    int k = this.d.a;
    while (i < k)
    {
      localObject2 = this.d.a(i);
      if (localObject2 != null)
      {
        float f2 = this.d.b(i);
        if (f2 != 0.0F)
        {
          String str = ((h)localObject2).toString();
          if (j == 0)
          {
            localObject2 = localObject1;
            f1 = f2;
            if (f2 < 0.0F)
            {
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append((String)localObject1);
              ((StringBuilder)localObject2).append("- ");
              localObject2 = ((StringBuilder)localObject2).toString();
              f1 = f2 * -1.0F;
            }
          }
          else if (f2 > 0.0F)
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append((String)localObject1);
            ((StringBuilder)localObject2).append(" + ");
            localObject2 = ((StringBuilder)localObject2).toString();
            f1 = f2;
          }
          else
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append((String)localObject1);
            ((StringBuilder)localObject2).append(" - ");
            localObject2 = ((StringBuilder)localObject2).toString();
            f1 = f2 * -1.0F;
          }
          if (f1 == 1.0F)
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append((String)localObject2);
            ((StringBuilder)localObject1).append(str);
            localObject1 = ((StringBuilder)localObject1).toString();
          }
          else
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append((String)localObject2);
            ((StringBuilder)localObject1).append(f1);
            ((StringBuilder)localObject1).append(" ");
            ((StringBuilder)localObject1).append(str);
            localObject1 = ((StringBuilder)localObject1).toString();
          }
          j = 1;
        }
      }
      i++;
    }
    localObject2 = localObject1;
    if (j == 0)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("0.0");
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    return (String)localObject2;
  }
  
  b c(h paramh, int paramInt)
  {
    this.d.a(paramh, paramInt);
    return this;
  }
  
  public void c()
  {
    this.a = null;
    this.d.a();
    this.b = 0.0F;
    this.e = false;
  }
  
  void c(h paramh)
  {
    h localh = this.a;
    if (localh != null)
    {
      this.d.a(localh, -1.0F);
      this.a = null;
    }
    float f = this.d.a(paramh, true) * -1.0F;
    this.a = paramh;
    if (f == 1.0F) {
      return;
    }
    this.b /= f;
    this.d.a(f);
  }
  
  void d()
  {
    float f = this.b;
    if (f < 0.0F)
    {
      this.b = (f * -1.0F);
      this.d.b();
    }
  }
  
  public void d(h paramh)
  {
    int i = paramh.c;
    float f = 1.0F;
    if (i != 1) {
      if (paramh.c == 2) {
        f = 1000.0F;
      } else if (paramh.c == 3) {
        f = 1000000.0F;
      } else if (paramh.c == 4) {
        f = 1.0E9F;
      } else if (paramh.c == 5) {
        f = 1.0E12F;
      }
    }
    this.d.a(paramh, f);
  }
  
  public boolean e()
  {
    boolean bool;
    if ((this.a == null) && (this.b == 0.0F) && (this.d.a == 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void f()
  {
    this.d.a();
    this.a = null;
    this.b = 0.0F;
  }
  
  public h g()
  {
    return this.a;
  }
  
  public String toString()
  {
    return b();
  }
}


/* Location:              ~/android/support/constraint/a/b.class
 *
 * Reversed by:           J
 */