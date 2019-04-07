package a;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

final class n
  implements e
{
  public final c a = new c();
  public final s b;
  boolean c;
  
  n(s params)
  {
    if (params != null)
    {
      this.b = params;
      return;
    }
    throw new NullPointerException("source == null");
  }
  
  public long a(byte paramByte)
  {
    return a(paramByte, 0L, Long.MAX_VALUE);
  }
  
  public long a(byte paramByte, long paramLong1, long paramLong2)
  {
    if (!this.c)
    {
      if ((paramLong1 >= 0L) && (paramLong2 >= paramLong1))
      {
        while (paramLong1 < paramLong2)
        {
          long l = this.a.a(paramByte, paramLong1, paramLong2);
          if (l != -1L) {
            return l;
          }
          l = this.a.b;
          if ((l < paramLong2) && (this.b.read(this.a, 8192L) != -1L)) {
            paramLong1 = Math.max(paramLong1, l);
          } else {
            return -1L;
          }
        }
        return -1L;
      }
      throw new IllegalArgumentException(String.format("fromIndex=%s toIndex=%s", new Object[] { Long.valueOf(paramLong1), Long.valueOf(paramLong2) }));
    }
    throw new IllegalStateException("closed");
  }
  
  public String a(Charset paramCharset)
  {
    if (paramCharset != null)
    {
      this.a.a(this.b);
      return this.a.a(paramCharset);
    }
    throw new IllegalArgumentException("charset == null");
  }
  
  public void a(long paramLong)
  {
    if (b(paramLong)) {
      return;
    }
    throw new EOFException();
  }
  
  public void a(c paramc, long paramLong)
  {
    try
    {
      a(paramLong);
      this.a.a(paramc, paramLong);
      return;
    }
    catch (EOFException localEOFException)
    {
      paramc.a(this.a);
      throw localEOFException;
    }
  }
  
  public void a(byte[] paramArrayOfByte)
  {
    try
    {
      a(paramArrayOfByte.length);
      this.a.a(paramArrayOfByte);
      return;
    }
    catch (EOFException localEOFException)
    {
      int i = 0;
      while (this.a.b > 0L)
      {
        c localc = this.a;
        int j = localc.a(paramArrayOfByte, i, (int)localc.b);
        if (j != -1) {
          i += j;
        } else {
          throw new AssertionError();
        }
      }
      throw localEOFException;
    }
  }
  
  public boolean a(long paramLong, f paramf)
  {
    return a(paramLong, paramf, 0, paramf.h());
  }
  
  public boolean a(long paramLong, f paramf, int paramInt1, int paramInt2)
  {
    if (!this.c)
    {
      if ((paramLong >= 0L) && (paramInt1 >= 0) && (paramInt2 >= 0) && (paramf.h() - paramInt1 >= paramInt2))
      {
        for (int i = 0; i < paramInt2; i++)
        {
          long l = i + paramLong;
          if (!b(1L + l)) {
            return false;
          }
          if (this.a.c(l) != paramf.a(paramInt1 + i)) {
            return false;
          }
        }
        return true;
      }
      return false;
    }
    throw new IllegalStateException("closed");
  }
  
  public c b()
  {
    return this.a;
  }
  
  public boolean b(long paramLong)
  {
    if (paramLong >= 0L)
    {
      if (!this.c)
      {
        while (this.a.b < paramLong) {
          if (this.b.read(this.a, 8192L) == -1L) {
            return false;
          }
        }
        return true;
      }
      throw new IllegalStateException("closed");
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("byteCount < 0: ");
    localStringBuilder.append(paramLong);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public void close()
  {
    if (this.c) {
      return;
    }
    this.c = true;
    this.b.close();
    this.a.t();
  }
  
  public f d(long paramLong)
  {
    a(paramLong);
    return this.a.d(paramLong);
  }
  
  public boolean e()
  {
    if (!this.c)
    {
      boolean bool;
      if ((this.a.e()) && (this.b.read(this.a, 8192L) == -1L)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    throw new IllegalStateException("closed");
  }
  
  public InputStream f()
  {
    new InputStream()
    {
      public int available()
      {
        if (!n.this.c) {
          return (int)Math.min(n.this.a.b, 2147483647L);
        }
        throw new IOException("closed");
      }
      
      public void close()
      {
        n.this.close();
      }
      
      public int read()
      {
        if (!n.this.c)
        {
          if ((n.this.a.b == 0L) && (n.this.b.read(n.this.a, 8192L) == -1L)) {
            return -1;
          }
          return n.this.a.h() & 0xFF;
        }
        throw new IOException("closed");
      }
      
      public int read(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (!n.this.c)
        {
          u.a(paramAnonymousArrayOfByte.length, paramAnonymousInt1, paramAnonymousInt2);
          if ((n.this.a.b == 0L) && (n.this.b.read(n.this.a, 8192L) == -1L)) {
            return -1;
          }
          return n.this.a.a(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
        }
        throw new IOException("closed");
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(n.this);
        localStringBuilder.append(".inputStream()");
        return localStringBuilder.toString();
      }
    };
  }
  
  public String f(long paramLong)
  {
    if (paramLong >= 0L)
    {
      long l1;
      if (paramLong == Long.MAX_VALUE) {
        l1 = Long.MAX_VALUE;
      } else {
        l1 = paramLong + 1L;
      }
      long l2 = a((byte)10, 0L, l1);
      if (l2 != -1L) {
        return this.a.g(l2);
      }
      if ((l1 < Long.MAX_VALUE) && (b(l1)) && (this.a.c(l1 - 1L) == 13) && (b(1L + l1)) && (this.a.c(l1) == 10)) {
        return this.a.g(l1);
      }
      localObject1 = new c();
      Object localObject2 = this.a;
      ((c)localObject2).a((c)localObject1, 0L, Math.min(32L, ((c)localObject2).a()));
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("\\n not found: limit=");
      ((StringBuilder)localObject2).append(Math.min(this.a.a(), paramLong));
      ((StringBuilder)localObject2).append(" content=");
      ((StringBuilder)localObject2).append(((c)localObject1).p().f());
      ((StringBuilder)localObject2).append('…');
      throw new EOFException(((StringBuilder)localObject2).toString());
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("limit < 0: ");
    ((StringBuilder)localObject1).append(paramLong);
    throw new IllegalArgumentException(((StringBuilder)localObject1).toString());
  }
  
  public byte h()
  {
    a(1L);
    return this.a.h();
  }
  
  public byte[] h(long paramLong)
  {
    a(paramLong);
    return this.a.h(paramLong);
  }
  
  public short i()
  {
    a(2L);
    return this.a.i();
  }
  
  public void i(long paramLong)
  {
    if (!this.c)
    {
      while (paramLong > 0L)
      {
        if ((this.a.b == 0L) && (this.b.read(this.a, 8192L) == -1L)) {
          throw new EOFException();
        }
        long l = Math.min(paramLong, this.a.a());
        this.a.i(l);
        paramLong -= l;
      }
      return;
    }
    throw new IllegalStateException("closed");
  }
  
  public boolean isOpen()
  {
    return this.c ^ true;
  }
  
  public int j()
  {
    a(4L);
    return this.a.j();
  }
  
  public long k()
  {
    a(8L);
    return this.a.k();
  }
  
  public short l()
  {
    a(2L);
    return this.a.l();
  }
  
  public int m()
  {
    a(4L);
    return this.a.m();
  }
  
  public long n()
  {
    a(1L);
    int j;
    byte b1;
    for (int i = 0;; i = j)
    {
      j = i + 1;
      if (!b(j)) {
        break label91;
      }
      b1 = this.a.c(i);
      if (((b1 < 48) || (b1 > 57)) && ((i != 0) || (b1 != 45))) {
        break;
      }
    }
    if (i == 0) {
      throw new NumberFormatException(String.format("Expected leading [0-9] or '-' character but was %#x", new Object[] { Byte.valueOf(b1) }));
    }
    label91:
    return this.a.n();
  }
  
  public long o()
  {
    a(1L);
    int j;
    byte b1;
    for (int i = 0;; i = j)
    {
      j = i + 1;
      if (!b(j)) {
        break label105;
      }
      b1 = this.a.c(i);
      if (((b1 < 48) || (b1 > 57)) && ((b1 < 97) || (b1 > 102)) && ((b1 < 65) || (b1 > 70))) {
        break;
      }
    }
    if (i == 0) {
      throw new NumberFormatException(String.format("Expected leading [0-9a-fA-F] character but was %#x", new Object[] { Byte.valueOf(b1) }));
    }
    label105:
    return this.a.o();
  }
  
  public String r()
  {
    return f(Long.MAX_VALUE);
  }
  
  public int read(ByteBuffer paramByteBuffer)
  {
    if ((this.a.b == 0L) && (this.b.read(this.a, 8192L) == -1L)) {
      return -1;
    }
    return this.a.read(paramByteBuffer);
  }
  
  public long read(c paramc, long paramLong)
  {
    if (paramc != null)
    {
      if (paramLong >= 0L)
      {
        if (!this.c)
        {
          if ((this.a.b == 0L) && (this.b.read(this.a, 8192L) == -1L)) {
            return -1L;
          }
          paramLong = Math.min(paramLong, this.a.b);
          return this.a.read(paramc, paramLong);
        }
        throw new IllegalStateException("closed");
      }
      paramc = new StringBuilder();
      paramc.append("byteCount < 0: ");
      paramc.append(paramLong);
      throw new IllegalArgumentException(paramc.toString());
    }
    throw new IllegalArgumentException("sink == null");
  }
  
  public byte[] s()
  {
    this.a.a(this.b);
    return this.a.s();
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
}


/* Location:              ~/a/n.class
 *
 * Reversed by:           J
 */