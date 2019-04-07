package a;

import java.nio.ByteBuffer;

final class m
  implements d
{
  public final c a = new c();
  public final r b;
  boolean c;
  
  m(r paramr)
  {
    if (paramr != null)
    {
      this.b = paramr;
      return;
    }
    throw new NullPointerException("sink == null");
  }
  
  public long a(s params)
  {
    if (params != null)
    {
      long l1 = 0L;
      for (;;)
      {
        long l2 = params.read(this.a, 8192L);
        if (l2 == -1L) {
          break;
        }
        l1 += l2;
        w();
      }
      return l1;
    }
    throw new IllegalArgumentException("source == null");
  }
  
  public c b()
  {
    return this.a;
  }
  
  public d b(String paramString)
  {
    if (!this.c)
    {
      this.a.a(paramString);
      return w();
    }
    throw new IllegalStateException("closed");
  }
  
  public d c(f paramf)
  {
    if (!this.c)
    {
      this.a.a(paramf);
      return w();
    }
    throw new IllegalStateException("closed");
  }
  
  public d c(byte[] paramArrayOfByte)
  {
    if (!this.c)
    {
      this.a.b(paramArrayOfByte);
      return w();
    }
    throw new IllegalStateException("closed");
  }
  
  public d c(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (!this.c)
    {
      this.a.b(paramArrayOfByte, paramInt1, paramInt2);
      return w();
    }
    throw new IllegalStateException("closed");
  }
  
  public void close()
  {
    if (this.c) {
      return;
    }
    Object localObject1 = null;
    Object localObject2 = localObject1;
    try
    {
      if (this.a.b > 0L)
      {
        this.b.write(this.a, this.a.b);
        localObject2 = localObject1;
      }
    }
    catch (Throwable localThrowable1) {}
    try
    {
      this.b.close();
      localObject1 = localThrowable1;
    }
    catch (Throwable localThrowable2)
    {
      localObject1 = localThrowable1;
      if (localThrowable1 == null) {
        localObject1 = localThrowable2;
      }
    }
    this.c = true;
    if (localObject1 != null) {
      u.a((Throwable)localObject1);
    }
  }
  
  public d d()
  {
    if (!this.c)
    {
      long l = this.a.a();
      if (l > 0L) {
        this.b.write(this.a, l);
      }
      return this;
    }
    throw new IllegalStateException("closed");
  }
  
  public void flush()
  {
    if (!this.c)
    {
      if (this.a.b > 0L)
      {
        r localr = this.b;
        c localc = this.a;
        localr.write(localc, localc.b);
      }
      this.b.flush();
      return;
    }
    throw new IllegalStateException("closed");
  }
  
  public d g(int paramInt)
  {
    if (!this.c)
    {
      this.a.d(paramInt);
      return w();
    }
    throw new IllegalStateException("closed");
  }
  
  public d h(int paramInt)
  {
    if (!this.c)
    {
      this.a.c(paramInt);
      return w();
    }
    throw new IllegalStateException("closed");
  }
  
  public d i(int paramInt)
  {
    if (!this.c)
    {
      this.a.b(paramInt);
      return w();
    }
    throw new IllegalStateException("closed");
  }
  
  public boolean isOpen()
  {
    return this.c ^ true;
  }
  
  public d m(long paramLong)
  {
    if (!this.c)
    {
      this.a.l(paramLong);
      return w();
    }
    throw new IllegalStateException("closed");
  }
  
  public d n(long paramLong)
  {
    if (!this.c)
    {
      this.a.k(paramLong);
      return w();
    }
    throw new IllegalStateException("closed");
  }
  
  public t timeout()
  {
    return this.b.timeout();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("buffer(");
    localStringBuilder.append(this.b);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
  
  public d w()
  {
    if (!this.c)
    {
      long l = this.a.g();
      if (l > 0L) {
        this.b.write(this.a, l);
      }
      return this;
    }
    throw new IllegalStateException("closed");
  }
  
  public int write(ByteBuffer paramByteBuffer)
  {
    if (!this.c)
    {
      int i = this.a.write(paramByteBuffer);
      w();
      return i;
    }
    throw new IllegalStateException("closed");
  }
  
  public void write(c paramc, long paramLong)
  {
    if (!this.c)
    {
      this.a.write(paramc, paramLong);
      w();
      return;
    }
    throw new IllegalStateException("closed");
  }
}


/* Location:              ~/a/m.class
 *
 * Reversed by:           J
 */