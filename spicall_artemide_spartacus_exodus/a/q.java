package a;

import java.util.Arrays;

final class q
  extends f
{
  final transient byte[][] f;
  final transient int[] g;
  
  q(c paramc, int paramInt)
  {
    super(null);
    u.a(paramc.b, 0L, paramInt);
    Object localObject = paramc.a;
    int i = 0;
    int j = 0;
    int k = 0;
    while (j < paramInt) {
      if (((o)localObject).c != ((o)localObject).b)
      {
        j += ((o)localObject).c - ((o)localObject).b;
        k++;
        localObject = ((o)localObject).f;
      }
      else
      {
        throw new AssertionError("s.limit == s.pos");
      }
    }
    this.f = new byte[k][];
    this.g = new int[k * 2];
    paramc = paramc.a;
    j = 0;
    k = i;
    while (k < paramInt)
    {
      this.f[j] = paramc.a;
      i = k + (paramc.c - paramc.b);
      k = i;
      if (i > paramInt) {
        k = paramInt;
      }
      localObject = this.g;
      localObject[j] = k;
      localObject[(this.f.length + j)] = paramc.b;
      paramc.d = true;
      j++;
      paramc = paramc.f;
    }
  }
  
  private int b(int paramInt)
  {
    paramInt = Arrays.binarySearch(this.g, 0, this.f.length, paramInt + 1);
    if (paramInt < 0) {
      paramInt ^= 0xFFFFFFFF;
    }
    return paramInt;
  }
  
  private f k()
  {
    return new f(i());
  }
  
  public byte a(int paramInt)
  {
    u.a(this.g[(this.f.length - 1)], paramInt, 1L);
    int i = b(paramInt);
    int j;
    if (i == 0) {
      j = 0;
    } else {
      j = this.g[(i - 1)];
    }
    int[] arrayOfInt = this.g;
    byte[][] arrayOfByte = this.f;
    int k = arrayOfInt[(arrayOfByte.length + i)];
    return arrayOfByte[i][(paramInt - j + k)];
  }
  
  public f a(int paramInt1, int paramInt2)
  {
    return k().a(paramInt1, paramInt2);
  }
  
  public String a()
  {
    return k().a();
  }
  
  void a(c paramc)
  {
    int i = this.f.length;
    int j = 0;
    int n;
    for (int k = 0; j < i; k = n)
    {
      Object localObject = this.g;
      int m = localObject[(i + j)];
      n = localObject[j];
      localObject = new o(this.f[j], m, m + n - k, true, false);
      if (paramc.a == null)
      {
        ((o)localObject).g = ((o)localObject);
        ((o)localObject).f = ((o)localObject);
        paramc.a = ((o)localObject);
      }
      else
      {
        paramc.a.g.a((o)localObject);
      }
      j++;
    }
    paramc.b += k;
  }
  
  public boolean a(int paramInt1, f paramf, int paramInt2, int paramInt3)
  {
    if ((paramInt1 >= 0) && (paramInt1 <= h() - paramInt3))
    {
      int i = b(paramInt1);
      int j = paramInt1;
      for (paramInt1 = i; paramInt3 > 0; paramInt1++)
      {
        if (paramInt1 == 0) {
          i = 0;
        } else {
          i = this.g[(paramInt1 - 1)];
        }
        int k = Math.min(paramInt3, this.g[paramInt1] - i + i - j);
        int[] arrayOfInt = this.g;
        byte[][] arrayOfByte = this.f;
        int m = arrayOfInt[(arrayOfByte.length + paramInt1)];
        if (!paramf.a(paramInt2, arrayOfByte[paramInt1], j - i + m, k)) {
          return false;
        }
        j += k;
        paramInt2 += k;
        paramInt3 -= k;
      }
      return true;
    }
    return false;
  }
  
  public boolean a(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    if ((paramInt1 >= 0) && (paramInt1 <= h() - paramInt3) && (paramInt2 >= 0) && (paramInt2 <= paramArrayOfByte.length - paramInt3))
    {
      int i = b(paramInt1);
      int j = paramInt1;
      for (paramInt1 = i; paramInt3 > 0; paramInt1++)
      {
        if (paramInt1 == 0) {
          i = 0;
        } else {
          i = this.g[(paramInt1 - 1)];
        }
        int k = Math.min(paramInt3, this.g[paramInt1] - i + i - j);
        int[] arrayOfInt = this.g;
        byte[][] arrayOfByte = this.f;
        int m = arrayOfInt[(arrayOfByte.length + paramInt1)];
        if (!u.a(arrayOfByte[paramInt1], j - i + m, paramArrayOfByte, paramInt2, k)) {
          return false;
        }
        j += k;
        paramInt2 += k;
        paramInt3 -= k;
      }
      return true;
    }
    return false;
  }
  
  public String b()
  {
    return k().b();
  }
  
  public f c()
  {
    return k().c();
  }
  
  public f d()
  {
    return k().d();
  }
  
  public f e()
  {
    return k().e();
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof f))
    {
      paramObject = (f)paramObject;
      if ((((f)paramObject).h() == h()) && (a(0, (f)paramObject, 0, h()))) {}
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  public String f()
  {
    return k().f();
  }
  
  public f g()
  {
    return k().g();
  }
  
  public int h()
  {
    return this.g[(this.f.length - 1)];
  }
  
  public int hashCode()
  {
    int i = this.d;
    if (i != 0) {
      return i;
    }
    int j = this.f.length;
    int k = 0;
    int m = 1;
    int i2;
    for (int n = 0; k < j; n = i2)
    {
      byte[] arrayOfByte = this.f[k];
      int[] arrayOfInt = this.g;
      int i1 = arrayOfInt[(j + k)];
      i2 = arrayOfInt[k];
      for (i = i1; i < i2 - n + i1; i++) {
        m = m * 31 + arrayOfByte[i];
      }
      k++;
    }
    this.d = m;
    return m;
  }
  
  public byte[] i()
  {
    Object localObject1 = this.g;
    Object localObject2 = this.f;
    localObject1 = new byte[localObject1[(localObject2.length - 1)]];
    int i = localObject2.length;
    int j = 0;
    int n;
    for (int k = 0; j < i; k = n)
    {
      localObject2 = this.g;
      int m = localObject2[(i + j)];
      n = localObject2[j];
      System.arraycopy(this.f[j], m, localObject1, k, n - k);
      j++;
    }
    return (byte[])localObject1;
  }
  
  byte[] j()
  {
    return i();
  }
  
  public String toString()
  {
    return k().toString();
  }
}


/* Location:              ~/a/q.class
 *
 * Reversed by:           J
 */