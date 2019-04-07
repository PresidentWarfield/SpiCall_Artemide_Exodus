package android.support.constraint.a;

import java.util.Arrays;

public class a
{
  int a = 0;
  private final b b;
  private final c c;
  private int d = 8;
  private h e = null;
  private int[] f;
  private int[] g;
  private float[] h;
  private int i;
  private int j;
  private boolean k;
  
  a(b paramb, c paramc)
  {
    int m = this.d;
    this.f = new int[m];
    this.g = new int[m];
    this.h = new float[m];
    this.i = -1;
    this.j = -1;
    this.k = false;
    this.b = paramb;
    this.c = paramc;
  }
  
  private boolean a(h paramh, e parame)
  {
    int m = paramh.i;
    boolean bool = true;
    if (m > 1) {
      bool = false;
    }
    return bool;
  }
  
  public final float a(h paramh, boolean paramBoolean)
  {
    if (this.e == paramh) {
      this.e = null;
    }
    int m = this.i;
    if (m == -1) {
      return 0.0F;
    }
    int n = 0;
    int i1 = -1;
    while ((m != -1) && (n < this.a))
    {
      if (this.f[m] == paramh.a)
      {
        if (m == this.i)
        {
          this.i = this.g[m];
        }
        else
        {
          int[] arrayOfInt = this.g;
          arrayOfInt[i1] = arrayOfInt[m];
        }
        if (paramBoolean) {
          paramh.b(this.b);
        }
        paramh.i -= 1;
        this.a -= 1;
        this.f[m] = -1;
        if (this.k) {
          this.j = m;
        }
        return this.h[m];
      }
      int i2 = this.g[m];
      n++;
      i1 = m;
      m = i2;
    }
    return 0.0F;
  }
  
  final h a(int paramInt)
  {
    int m = this.i;
    for (int n = 0; (m != -1) && (n < this.a); n++)
    {
      if (n == paramInt) {
        return this.c.c[this.f[m]];
      }
      m = this.g[m];
    }
    return null;
  }
  
  h a(e parame)
  {
    int m = this.i;
    Object localObject1 = null;
    int n = 0;
    Object localObject2 = null;
    float f1 = 0.0F;
    boolean bool1 = false;
    float f2 = 0.0F;
    boolean bool4;
    for (boolean bool2 = false; (m != -1) && (n < this.a); bool2 = bool4)
    {
      float f3 = this.h[m];
      h localh = this.c.c[this.f[m]];
      float f4;
      if (f3 < 0.0F)
      {
        f4 = f3;
        if (f3 > -0.001F)
        {
          this.h[m] = 0.0F;
          localh.b(this.b);
          f4 = 0.0F;
        }
      }
      else
      {
        f4 = f3;
        if (f3 < 0.001F)
        {
          this.h[m] = 0.0F;
          localh.b(this.b);
          f4 = 0.0F;
        }
      }
      Object localObject3 = localObject1;
      Object localObject4 = localObject2;
      f3 = f1;
      boolean bool3 = bool1;
      float f5 = f2;
      bool4 = bool2;
      if (f4 != 0.0F) {
        if (localh.f == h.a.a)
        {
          if (localObject2 == null)
          {
            bool3 = a(localh, parame);
            localObject3 = localObject1;
            localObject4 = localh;
            f3 = f4;
            f5 = f2;
            bool4 = bool2;
          }
          else if (f1 > f4)
          {
            bool3 = a(localh, parame);
            localObject3 = localObject1;
            localObject4 = localh;
            f3 = f4;
            f5 = f2;
            bool4 = bool2;
          }
          else
          {
            localObject3 = localObject1;
            localObject4 = localObject2;
            f3 = f1;
            bool3 = bool1;
            f5 = f2;
            bool4 = bool2;
            if (!bool1)
            {
              localObject3 = localObject1;
              localObject4 = localObject2;
              f3 = f1;
              bool3 = bool1;
              f5 = f2;
              bool4 = bool2;
              if (a(localh, parame))
              {
                bool3 = true;
                localObject3 = localObject1;
                localObject4 = localh;
                f3 = f4;
                f5 = f2;
                bool4 = bool2;
              }
            }
          }
        }
        else
        {
          localObject3 = localObject1;
          localObject4 = localObject2;
          f3 = f1;
          bool3 = bool1;
          f5 = f2;
          bool4 = bool2;
          if (localObject2 == null)
          {
            localObject3 = localObject1;
            localObject4 = localObject2;
            f3 = f1;
            bool3 = bool1;
            f5 = f2;
            bool4 = bool2;
            if (f4 < 0.0F) {
              if (localObject1 == null)
              {
                bool4 = a(localh, parame);
                localObject3 = localh;
                localObject4 = localObject2;
                f3 = f1;
                bool3 = bool1;
                f5 = f4;
              }
              else if (f2 > f4)
              {
                bool4 = a(localh, parame);
                localObject3 = localh;
                localObject4 = localObject2;
                f3 = f1;
                bool3 = bool1;
                f5 = f4;
              }
              else
              {
                localObject3 = localObject1;
                localObject4 = localObject2;
                f3 = f1;
                bool3 = bool1;
                f5 = f2;
                bool4 = bool2;
                if (!bool2)
                {
                  localObject3 = localObject1;
                  localObject4 = localObject2;
                  f3 = f1;
                  bool3 = bool1;
                  f5 = f2;
                  bool4 = bool2;
                  if (a(localh, parame))
                  {
                    bool4 = true;
                    f5 = f4;
                    bool3 = bool1;
                    f3 = f1;
                    localObject4 = localObject2;
                    localObject3 = localh;
                  }
                }
              }
            }
          }
        }
      }
      m = this.g[m];
      n++;
      localObject1 = localObject3;
      localObject2 = localObject4;
      f1 = f3;
      bool1 = bool3;
      f2 = f5;
    }
    if (localObject2 != null) {
      return (h)localObject2;
    }
    return (h)localObject1;
  }
  
  h a(boolean[] paramArrayOfBoolean, h paramh)
  {
    int m = this.i;
    int n = 0;
    Object localObject1 = null;
    float f2;
    for (float f1 = 0.0F; (m != -1) && (n < this.a); f1 = f2)
    {
      Object localObject2 = localObject1;
      f2 = f1;
      if (this.h[m] < 0.0F)
      {
        h localh = this.c.c[this.f[m]];
        if (paramArrayOfBoolean != null)
        {
          localObject2 = localObject1;
          f2 = f1;
          if (paramArrayOfBoolean[localh.a] != 0) {}
        }
        else
        {
          localObject2 = localObject1;
          f2 = f1;
          if (localh != paramh) {
            if (localh.f != h.a.c)
            {
              localObject2 = localObject1;
              f2 = f1;
              if (localh.f != h.a.d) {}
            }
            else
            {
              float f3 = this.h[m];
              localObject2 = localObject1;
              f2 = f1;
              if (f3 < f1)
              {
                localObject2 = localh;
                f2 = f3;
              }
            }
          }
        }
      }
      m = this.g[m];
      n++;
      localObject1 = localObject2;
    }
    return (h)localObject1;
  }
  
  public final void a()
  {
    int m = this.i;
    for (int n = 0; (m != -1) && (n < this.a); n++)
    {
      h localh = this.c.c[this.f[m]];
      if (localh != null) {
        localh.b(this.b);
      }
      m = this.g[m];
    }
    this.i = -1;
    this.j = -1;
    this.k = false;
    this.a = 0;
  }
  
  void a(float paramFloat)
  {
    int m = this.i;
    for (int n = 0; (m != -1) && (n < this.a); n++)
    {
      float[] arrayOfFloat = this.h;
      arrayOfFloat[m] /= paramFloat;
      m = this.g[m];
    }
  }
  
  final void a(b paramb1, b paramb2, boolean paramBoolean)
  {
    int m = this.i;
    int n = 0;
    while ((m != -1) && (n < this.a)) {
      if (this.f[m] == paramb2.a.a)
      {
        float f1 = this.h[m];
        a(paramb2.a, paramBoolean);
        a locala = (a)paramb2.d;
        n = locala.i;
        for (m = 0; (n != -1) && (m < locala.a); m++)
        {
          a(this.c.c[locala.f[n]], locala.h[n] * f1, paramBoolean);
          n = locala.g[n];
        }
        paramb1.b += paramb2.b * f1;
        if (paramBoolean) {
          paramb2.a.b(paramb1);
        }
        m = this.i;
        n = 0;
      }
      else
      {
        m = this.g[m];
        n++;
      }
    }
  }
  
  void a(b paramb, b[] paramArrayOfb)
  {
    int m = this.i;
    int n = 0;
    while ((m != -1) && (n < this.a))
    {
      Object localObject = this.c.c[this.f[m]];
      if (((h)localObject).b != -1)
      {
        float f1 = this.h[m];
        a((h)localObject, true);
        localObject = paramArrayOfb[localObject.b];
        if (!((b)localObject).e)
        {
          a locala = (a)((b)localObject).d;
          n = locala.i;
          for (m = 0; (n != -1) && (m < locala.a); m++)
          {
            a(this.c.c[locala.f[n]], locala.h[n] * f1, true);
            n = locala.g[n];
          }
        }
        paramb.b += ((b)localObject).b * f1;
        ((b)localObject).a.b(paramb);
        m = this.i;
        n = 0;
      }
      else
      {
        m = this.g[m];
        n++;
      }
    }
  }
  
  public final void a(h paramh, float paramFloat)
  {
    if (paramFloat == 0.0F)
    {
      a(paramh, true);
      return;
    }
    int m = this.i;
    if (m == -1)
    {
      this.i = 0;
      localObject = this.h;
      m = this.i;
      localObject[m] = paramFloat;
      this.f[m] = paramh.a;
      this.g[this.i] = -1;
      paramh.i += 1;
      paramh.a(this.b);
      this.a += 1;
      if (!this.k)
      {
        this.j += 1;
        m = this.j;
        paramh = this.f;
        if (m >= paramh.length)
        {
          this.k = true;
          this.j = (paramh.length - 1);
        }
      }
      return;
    }
    int n = 0;
    int i1 = -1;
    while ((m != -1) && (n < this.a))
    {
      if (this.f[m] == paramh.a)
      {
        this.h[m] = paramFloat;
        return;
      }
      if (this.f[m] < paramh.a) {
        i1 = m;
      }
      m = this.g[m];
      n++;
    }
    m = this.j;
    if (this.k)
    {
      localObject = this.f;
      if (localObject[m] != -1) {
        m = localObject.length;
      }
    }
    else
    {
      m++;
    }
    Object localObject = this.f;
    n = m;
    if (m >= localObject.length)
    {
      n = m;
      if (this.a < localObject.length) {
        for (int i2 = 0;; i2++)
        {
          localObject = this.f;
          n = m;
          if (i2 >= localObject.length) {
            break;
          }
          if (localObject[i2] == -1)
          {
            n = i2;
            break;
          }
        }
      }
    }
    localObject = this.f;
    m = n;
    if (n >= localObject.length)
    {
      m = localObject.length;
      this.d *= 2;
      this.k = false;
      this.j = (m - 1);
      this.h = Arrays.copyOf(this.h, this.d);
      this.f = Arrays.copyOf(this.f, this.d);
      this.g = Arrays.copyOf(this.g, this.d);
    }
    this.f[m] = paramh.a;
    this.h[m] = paramFloat;
    if (i1 != -1)
    {
      localObject = this.g;
      localObject[m] = localObject[i1];
      localObject[i1] = m;
    }
    else
    {
      this.g[m] = this.i;
      this.i = m;
    }
    paramh.i += 1;
    paramh.a(this.b);
    this.a += 1;
    if (!this.k) {
      this.j += 1;
    }
    if (this.a >= this.f.length) {
      this.k = true;
    }
    m = this.j;
    paramh = this.f;
    if (m >= paramh.length)
    {
      this.k = true;
      this.j = (paramh.length - 1);
    }
  }
  
  final void a(h paramh, float paramFloat, boolean paramBoolean)
  {
    if (paramFloat == 0.0F) {
      return;
    }
    int m = this.i;
    if (m == -1)
    {
      this.i = 0;
      localObject = this.h;
      m = this.i;
      localObject[m] = paramFloat;
      this.f[m] = paramh.a;
      this.g[this.i] = -1;
      paramh.i += 1;
      paramh.a(this.b);
      this.a += 1;
      if (!this.k)
      {
        this.j += 1;
        m = this.j;
        paramh = this.f;
        if (m >= paramh.length)
        {
          this.k = true;
          this.j = (paramh.length - 1);
        }
      }
      return;
    }
    int n = 0;
    int i1 = -1;
    while ((m != -1) && (n < this.a))
    {
      if (this.f[m] == paramh.a)
      {
        localObject = this.h;
        localObject[m] += paramFloat;
        if (localObject[m] == 0.0F)
        {
          if (m == this.i)
          {
            this.i = this.g[m];
          }
          else
          {
            localObject = this.g;
            localObject[i1] = localObject[m];
          }
          if (paramBoolean) {
            paramh.b(this.b);
          }
          if (this.k) {
            this.j = m;
          }
          paramh.i -= 1;
          this.a -= 1;
        }
        return;
      }
      if (this.f[m] < paramh.a) {
        i1 = m;
      }
      m = this.g[m];
      n++;
    }
    m = this.j;
    if (this.k)
    {
      localObject = this.f;
      if (localObject[m] != -1) {
        m = localObject.length;
      }
    }
    else
    {
      m++;
    }
    Object localObject = this.f;
    n = m;
    if (m >= localObject.length)
    {
      n = m;
      if (this.a < localObject.length) {
        for (int i2 = 0;; i2++)
        {
          localObject = this.f;
          n = m;
          if (i2 >= localObject.length) {
            break;
          }
          if (localObject[i2] == -1)
          {
            n = i2;
            break;
          }
        }
      }
    }
    localObject = this.f;
    m = n;
    if (n >= localObject.length)
    {
      m = localObject.length;
      this.d *= 2;
      this.k = false;
      this.j = (m - 1);
      this.h = Arrays.copyOf(this.h, this.d);
      this.f = Arrays.copyOf(this.f, this.d);
      this.g = Arrays.copyOf(this.g, this.d);
    }
    this.f[m] = paramh.a;
    this.h[m] = paramFloat;
    if (i1 != -1)
    {
      localObject = this.g;
      localObject[m] = localObject[i1];
      localObject[i1] = m;
    }
    else
    {
      this.g[m] = this.i;
      this.i = m;
    }
    paramh.i += 1;
    paramh.a(this.b);
    this.a += 1;
    if (!this.k) {
      this.j += 1;
    }
    m = this.j;
    paramh = this.f;
    if (m >= paramh.length)
    {
      this.k = true;
      this.j = (paramh.length - 1);
    }
  }
  
  final boolean a(h paramh)
  {
    int m = this.i;
    if (m == -1) {
      return false;
    }
    for (int n = 0; (m != -1) && (n < this.a); n++)
    {
      if (this.f[m] == paramh.a) {
        return true;
      }
      m = this.g[m];
    }
    return false;
  }
  
  final float b(int paramInt)
  {
    int m = this.i;
    for (int n = 0; (m != -1) && (n < this.a); n++)
    {
      if (n == paramInt) {
        return this.h[m];
      }
      m = this.g[m];
    }
    return 0.0F;
  }
  
  public final float b(h paramh)
  {
    int m = this.i;
    for (int n = 0; (m != -1) && (n < this.a); n++)
    {
      if (this.f[m] == paramh.a) {
        return this.h[m];
      }
      m = this.g[m];
    }
    return 0.0F;
  }
  
  void b()
  {
    int m = this.i;
    for (int n = 0; (m != -1) && (n < this.a); n++)
    {
      float[] arrayOfFloat = this.h;
      arrayOfFloat[m] *= -1.0F;
      m = this.g[m];
    }
  }
  
  public String toString()
  {
    Object localObject1 = "";
    int m = this.i;
    for (int n = 0; (m != -1) && (n < this.a); n++)
    {
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append(" -> ");
      localObject2 = ((StringBuilder)localObject2).toString();
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append(this.h[m]);
      ((StringBuilder)localObject1).append(" : ");
      localObject1 = ((StringBuilder)localObject1).toString();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append(this.c.c[this.f[m]]);
      localObject1 = ((StringBuilder)localObject2).toString();
      m = this.g[m];
    }
    return (String)localObject1;
  }
}


/* Location:              ~/android/support/constraint/a/a.class
 *
 * Reversed by:           J
 */