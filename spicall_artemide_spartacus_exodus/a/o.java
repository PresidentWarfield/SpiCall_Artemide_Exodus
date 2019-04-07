package a;

import javax.annotation.Nullable;

final class o
{
  final byte[] a;
  int b;
  int c;
  boolean d;
  boolean e;
  o f;
  o g;
  
  o()
  {
    this.a = new byte[' '];
    this.e = true;
    this.d = false;
  }
  
  o(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.a = paramArrayOfByte;
    this.b = paramInt1;
    this.c = paramInt2;
    this.d = paramBoolean1;
    this.e = paramBoolean2;
  }
  
  final o a()
  {
    this.d = true;
    return new o(this.a, this.b, this.c, true, false);
  }
  
  public final o a(int paramInt)
  {
    if ((paramInt > 0) && (paramInt <= this.c - this.b))
    {
      o localo;
      if (paramInt >= 1024)
      {
        localo = a();
      }
      else
      {
        localo = p.a();
        System.arraycopy(this.a, this.b, localo.a, 0, paramInt);
      }
      localo.c = (localo.b + paramInt);
      this.b += paramInt;
      this.g.a(localo);
      return localo;
    }
    throw new IllegalArgumentException();
  }
  
  public final o a(o paramo)
  {
    paramo.g = this;
    paramo.f = this.f;
    this.f.g = paramo;
    this.f = paramo;
    return paramo;
  }
  
  public final void a(o paramo, int paramInt)
  {
    if (paramo.e)
    {
      int i = paramo.c;
      if (i + paramInt > 8192) {
        if (!paramo.d)
        {
          int j = paramo.b;
          if (i + paramInt - j <= 8192)
          {
            byte[] arrayOfByte = paramo.a;
            System.arraycopy(arrayOfByte, j, arrayOfByte, 0, i - j);
            paramo.c -= paramo.b;
            paramo.b = 0;
          }
          else
          {
            throw new IllegalArgumentException();
          }
        }
        else
        {
          throw new IllegalArgumentException();
        }
      }
      System.arraycopy(this.a, this.b, paramo.a, paramo.c, paramInt);
      paramo.c += paramInt;
      this.b += paramInt;
      return;
    }
    throw new IllegalArgumentException();
  }
  
  final o b()
  {
    return new o((byte[])this.a.clone(), this.b, this.c, false, true);
  }
  
  @Nullable
  public final o c()
  {
    o localo1 = this.f;
    if (localo1 == this) {
      localo1 = null;
    }
    o localo2 = this.g;
    localo2.f = this.f;
    this.f.g = localo2;
    this.f = null;
    this.g = null;
    return localo1;
  }
  
  public final void d()
  {
    o localo = this.g;
    if (localo != this)
    {
      if (!localo.e) {
        return;
      }
      int i = this.c - this.b;
      int j = localo.c;
      int k;
      if (localo.d) {
        k = 0;
      } else {
        k = localo.b;
      }
      if (i > 8192 - j + k) {
        return;
      }
      a(this.g, i);
      c();
      p.a(this);
      return;
    }
    throw new IllegalStateException();
  }
}


/* Location:              ~/a/o.class
 *
 * Reversed by:           J
 */