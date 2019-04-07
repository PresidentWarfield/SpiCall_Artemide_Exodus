package a;

import java.io.Closeable;
import java.io.EOFException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;
import javax.annotation.Nullable;

public final class c
  implements d, e, Cloneable, ByteChannel
{
  private static final byte[] c = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  @Nullable
  o a;
  long b;
  
  public int a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    u.a(paramArrayOfByte.length, paramInt1, paramInt2);
    o localo = this.a;
    if (localo == null) {
      return -1;
    }
    paramInt2 = Math.min(paramInt2, localo.c - localo.b);
    System.arraycopy(localo.a, localo.b, paramArrayOfByte, paramInt1, paramInt2);
    localo.b += paramInt2;
    this.b -= paramInt2;
    if (localo.b == localo.c)
    {
      this.a = localo.c();
      p.a(localo);
    }
    return paramInt2;
  }
  
  public final long a()
  {
    return this.b;
  }
  
  public long a(byte paramByte)
  {
    return a(paramByte, 0L, Long.MAX_VALUE);
  }
  
  public long a(byte paramByte, long paramLong1, long paramLong2)
  {
    if ((paramLong1 >= 0L) && (paramLong2 >= paramLong1))
    {
      long l1 = this.b;
      if (paramLong2 <= l1) {
        l1 = paramLong2;
      }
      if (paramLong1 == l1) {
        return -1L;
      }
      o localo = this.a;
      if (localo == null) {
        return -1L;
      }
      paramLong2 = this.b;
      if (paramLong2 - paramLong1 < paramLong1)
      {
        while (paramLong2 > paramLong1)
        {
          localo = localo.g;
          paramLong2 -= localo.c - localo.b;
        }
      }
      else
      {
        long l2;
        for (paramLong2 = 0L;; paramLong2 = l2)
        {
          l2 = localo.c - localo.b + paramLong2;
          if (l2 >= paramLong1) {
            break;
          }
          localo = localo.f;
        }
      }
      while (paramLong2 < l1)
      {
        byte[] arrayOfByte = localo.a;
        int i = (int)Math.min(localo.c, localo.b + l1 - paramLong2);
        for (int j = (int)(localo.b + paramLong1 - paramLong2); j < i; j++) {
          if (arrayOfByte[j] == paramByte) {
            return j - localo.b + paramLong2;
          }
        }
        paramLong1 = localo.c - localo.b + paramLong2;
        localo = localo.f;
        paramLong2 = paramLong1;
      }
      return -1L;
    }
    throw new IllegalArgumentException(String.format("size=%s fromIndex=%s toIndex=%s", new Object[] { Long.valueOf(this.b), Long.valueOf(paramLong1), Long.valueOf(paramLong2) }));
  }
  
  public long a(f paramf, long paramLong)
  {
    if (paramLong >= 0L)
    {
      Object localObject1 = this.a;
      if (localObject1 == null) {
        return -1L;
      }
      long l1 = this.b;
      Object localObject2;
      if (l1 - paramLong < paramLong) {
        for (;;)
        {
          localObject2 = localObject1;
          l2 = l1;
          if (l1 <= paramLong) {
            break;
          }
          localObject1 = ((o)localObject1).g;
          l1 -= ((o)localObject1).c - ((o)localObject1).b;
        }
      }
      for (long l2 = 0L;; l2 = l1)
      {
        l1 = ((o)localObject1).c - ((o)localObject1).b + l2;
        localObject2 = localObject1;
        if (l1 >= paramLong) {
          break;
        }
        localObject1 = ((o)localObject1).f;
      }
      int i;
      int j;
      int k;
      int m;
      int n;
      if (paramf.h() == 2)
      {
        i = paramf.a(0);
        j = paramf.a(1);
        while (l2 < this.b)
        {
          paramf = ((o)localObject2).a;
          k = (int)(((o)localObject2).b + paramLong - l2);
          m = ((o)localObject2).c;
          while (k < m)
          {
            n = paramf[k];
            if ((n != i) && (n != j)) {
              k++;
            } else {
              return k - ((o)localObject2).b + l2;
            }
          }
          paramLong = ((o)localObject2).c - ((o)localObject2).b + l2;
          localObject2 = ((o)localObject2).f;
          l2 = paramLong;
        }
      }
      paramf = paramf.j();
      while (l2 < this.b)
      {
        localObject1 = ((o)localObject2).a;
        k = (int)(((o)localObject2).b + paramLong - l2);
        m = ((o)localObject2).c;
        while (k < m)
        {
          j = localObject1[k];
          n = paramf.length;
          for (i = 0; i < n; i++) {
            if (j == paramf[i]) {
              return k - ((o)localObject2).b + l2;
            }
          }
          k++;
        }
        paramLong = ((o)localObject2).c - ((o)localObject2).b + l2;
        localObject2 = ((o)localObject2).f;
        l2 = paramLong;
      }
      return -1L;
    }
    throw new IllegalArgumentException("fromIndex < 0");
  }
  
  public long a(s params)
  {
    if (params != null)
    {
      long l2;
      for (long l1 = 0L;; l1 += l2)
      {
        l2 = params.read(this, 8192L);
        if (l2 == -1L) {
          break;
        }
      }
      return l1;
    }
    throw new IllegalArgumentException("source == null");
  }
  
  public final a a(a parama)
  {
    if (parama.a == null)
    {
      parama.a = this;
      parama.b = true;
      return parama;
    }
    throw new IllegalStateException("already attached to a buffer");
  }
  
  public c a(int paramInt)
  {
    if (paramInt < 128)
    {
      b(paramInt);
    }
    else if (paramInt < 2048)
    {
      b(paramInt >> 6 | 0xC0);
      b(paramInt & 0x3F | 0x80);
    }
    else if (paramInt < 65536)
    {
      if ((paramInt >= 55296) && (paramInt <= 57343))
      {
        b(63);
      }
      else
      {
        b(paramInt >> 12 | 0xE0);
        b(paramInt >> 6 & 0x3F | 0x80);
        b(paramInt & 0x3F | 0x80);
      }
    }
    else
    {
      if (paramInt > 1114111) {
        break label191;
      }
      b(paramInt >> 18 | 0xF0);
      b(paramInt >> 12 & 0x3F | 0x80);
      b(paramInt >> 6 & 0x3F | 0x80);
      b(paramInt & 0x3F | 0x80);
    }
    return this;
    label191:
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Unexpected code point: ");
    localStringBuilder.append(Integer.toHexString(paramInt));
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public final c a(c paramc, long paramLong1, long paramLong2)
  {
    if (paramc != null)
    {
      u.a(this.b, paramLong1, paramLong2);
      if (paramLong2 == 0L) {
        return this;
      }
      paramc.b += paramLong2;
      o localo2;
      long l1;
      long l2;
      for (o localo1 = this.a;; localo1 = localo1.f)
      {
        localo2 = localo1;
        l1 = paramLong1;
        l2 = paramLong2;
        if (paramLong1 < localo1.c - localo1.b) {
          break;
        }
        paramLong1 -= localo1.c - localo1.b;
      }
      while (l2 > 0L)
      {
        o localo3 = localo2.a();
        localo3.b = ((int)(localo3.b + l1));
        localo3.c = Math.min(localo3.b + (int)l2, localo3.c);
        localo1 = paramc.a;
        if (localo1 == null)
        {
          localo3.g = localo3;
          localo3.f = localo3;
          paramc.a = localo3;
        }
        else
        {
          localo1.g.a(localo3);
        }
        l2 -= localo3.c - localo3.b;
        localo2 = localo2.f;
        l1 = 0L;
      }
      return this;
    }
    throw new IllegalArgumentException("out == null");
  }
  
  public c a(f paramf)
  {
    if (paramf != null)
    {
      paramf.a(this);
      return this;
    }
    throw new IllegalArgumentException("byteString == null");
  }
  
  public c a(String paramString)
  {
    return a(paramString, 0, paramString.length());
  }
  
  public c a(String paramString, int paramInt1, int paramInt2)
  {
    if (paramString != null)
    {
      if (paramInt1 >= 0)
      {
        if (paramInt2 >= paramInt1)
        {
          if (paramInt2 <= paramString.length())
          {
            while (paramInt1 < paramInt2)
            {
              int i = paramString.charAt(paramInt1);
              int k;
              int m;
              if (i < 128)
              {
                localObject = e(1);
                byte[] arrayOfByte = ((o)localObject).a;
                int j = ((o)localObject).c - paramInt1;
                k = Math.min(paramInt2, 8192 - j);
                m = paramInt1 + 1;
                arrayOfByte[(paramInt1 + j)] = ((byte)(byte)i);
                for (paramInt1 = m; paramInt1 < k; paramInt1++)
                {
                  m = paramString.charAt(paramInt1);
                  if (m >= 128) {
                    break;
                  }
                  arrayOfByte[(paramInt1 + j)] = ((byte)(byte)m);
                }
                m = j + paramInt1 - ((o)localObject).c;
                ((o)localObject).c += m;
                this.b += m;
              }
              else if (i < 2048)
              {
                b(i >> 6 | 0xC0);
                b(i & 0x3F | 0x80);
                paramInt1++;
              }
              else if ((i >= 55296) && (i <= 57343))
              {
                k = paramInt1 + 1;
                if (k < paramInt2) {
                  m = paramString.charAt(k);
                } else {
                  m = 0;
                }
                if ((i <= 56319) && (m >= 56320) && (m <= 57343))
                {
                  m = ((i & 0xFFFF27FF) << 10 | 0xFFFF23FF & m) + 65536;
                  b(m >> 18 | 0xF0);
                  b(m >> 12 & 0x3F | 0x80);
                  b(m >> 6 & 0x3F | 0x80);
                  b(m & 0x3F | 0x80);
                  paramInt1 += 2;
                }
                else
                {
                  b(63);
                  paramInt1 = k;
                }
              }
              else
              {
                b(i >> 12 | 0xE0);
                b(i >> 6 & 0x3F | 0x80);
                b(i & 0x3F | 0x80);
                paramInt1++;
              }
            }
            return this;
          }
          Object localObject = new StringBuilder();
          ((StringBuilder)localObject).append("endIndex > string.length: ");
          ((StringBuilder)localObject).append(paramInt2);
          ((StringBuilder)localObject).append(" > ");
          ((StringBuilder)localObject).append(paramString.length());
          throw new IllegalArgumentException(((StringBuilder)localObject).toString());
        }
        paramString = new StringBuilder();
        paramString.append("endIndex < beginIndex: ");
        paramString.append(paramInt2);
        paramString.append(" < ");
        paramString.append(paramInt1);
        throw new IllegalArgumentException(paramString.toString());
      }
      paramString = new StringBuilder();
      paramString.append("Index < 0: ");
      paramString.append(paramInt1);
      throw new IllegalArgumentException(paramString.toString());
    }
    throw new IllegalArgumentException("string == null");
  }
  
  public c a(String paramString, int paramInt1, int paramInt2, Charset paramCharset)
  {
    if (paramString != null)
    {
      if (paramInt1 >= 0)
      {
        if (paramInt2 >= paramInt1)
        {
          if (paramInt2 <= paramString.length())
          {
            if (paramCharset != null)
            {
              if (paramCharset.equals(u.a)) {
                return a(paramString, paramInt1, paramInt2);
              }
              paramString = paramString.substring(paramInt1, paramInt2).getBytes(paramCharset);
              return b(paramString, 0, paramString.length);
            }
            throw new IllegalArgumentException("charset == null");
          }
          paramCharset = new StringBuilder();
          paramCharset.append("endIndex > string.length: ");
          paramCharset.append(paramInt2);
          paramCharset.append(" > ");
          paramCharset.append(paramString.length());
          throw new IllegalArgumentException(paramCharset.toString());
        }
        paramString = new StringBuilder();
        paramString.append("endIndex < beginIndex: ");
        paramString.append(paramInt2);
        paramString.append(" < ");
        paramString.append(paramInt1);
        throw new IllegalArgumentException(paramString.toString());
      }
      paramString = new StringBuilder();
      paramString.append("beginIndex < 0: ");
      paramString.append(paramInt1);
      throw new IllegalAccessError(paramString.toString());
    }
    throw new IllegalArgumentException("string == null");
  }
  
  public c a(String paramString, Charset paramCharset)
  {
    return a(paramString, 0, paramString.length(), paramCharset);
  }
  
  public String a(long paramLong, Charset paramCharset)
  {
    u.a(this.b, 0L, paramLong);
    if (paramCharset != null)
    {
      if (paramLong <= 2147483647L)
      {
        if (paramLong == 0L) {
          return "";
        }
        o localo = this.a;
        if (localo.b + paramLong > localo.c) {
          return new String(h(paramLong), paramCharset);
        }
        paramCharset = new String(localo.a, localo.b, (int)paramLong, paramCharset);
        localo.b = ((int)(localo.b + paramLong));
        this.b -= paramLong;
        if (localo.b == localo.c)
        {
          this.a = localo.c();
          p.a(localo);
        }
        return paramCharset;
      }
      paramCharset = new StringBuilder();
      paramCharset.append("byteCount > Integer.MAX_VALUE: ");
      paramCharset.append(paramLong);
      throw new IllegalArgumentException(paramCharset.toString());
    }
    throw new IllegalArgumentException("charset == null");
  }
  
  public String a(Charset paramCharset)
  {
    try
    {
      paramCharset = a(this.b, paramCharset);
      return paramCharset;
    }
    catch (EOFException paramCharset)
    {
      throw new AssertionError(paramCharset);
    }
  }
  
  public void a(long paramLong)
  {
    if (this.b >= paramLong) {
      return;
    }
    throw new EOFException();
  }
  
  public void a(c paramc, long paramLong)
  {
    long l = this.b;
    if (l >= paramLong)
    {
      paramc.write(this, paramLong);
      return;
    }
    paramc.write(this, l);
    throw new EOFException();
  }
  
  public void a(byte[] paramArrayOfByte)
  {
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      int j = a(paramArrayOfByte, i, paramArrayOfByte.length - i);
      if (j != -1) {
        i += j;
      } else {
        throw new EOFException();
      }
    }
  }
  
  public boolean a(long paramLong, f paramf)
  {
    return a(paramLong, paramf, 0, paramf.h());
  }
  
  public boolean a(long paramLong, f paramf, int paramInt1, int paramInt2)
  {
    if ((paramLong >= 0L) && (paramInt1 >= 0) && (paramInt2 >= 0) && (this.b - paramLong >= paramInt2) && (paramf.h() - paramInt1 >= paramInt2))
    {
      for (int i = 0; i < paramInt2; i++) {
        if (c(i + paramLong) != paramf.a(paramInt1 + i)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  public long b(f paramf)
  {
    return a(paramf, 0L);
  }
  
  public c b()
  {
    return this;
  }
  
  public c b(int paramInt)
  {
    o localo = e(1);
    byte[] arrayOfByte = localo.a;
    int i = localo.c;
    localo.c = (i + 1);
    arrayOfByte[i] = ((byte)(byte)paramInt);
    this.b += 1L;
    return this;
  }
  
  public c b(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null) {
      return b(paramArrayOfByte, 0, paramArrayOfByte.length);
    }
    throw new IllegalArgumentException("source == null");
  }
  
  public c b(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramArrayOfByte != null)
    {
      long l1 = paramArrayOfByte.length;
      long l2 = paramInt1;
      long l3 = paramInt2;
      u.a(l1, l2, l3);
      paramInt2 += paramInt1;
      while (paramInt1 < paramInt2)
      {
        o localo = e(1);
        int i = Math.min(paramInt2 - paramInt1, 8192 - localo.c);
        System.arraycopy(paramArrayOfByte, paramInt1, localo.a, localo.c, i);
        paramInt1 += i;
        localo.c += i;
      }
      this.b += l3;
      return this;
    }
    throw new IllegalArgumentException("source == null");
  }
  
  public boolean b(long paramLong)
  {
    boolean bool;
    if (this.b >= paramLong) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final byte c(long paramLong)
  {
    u.a(this.b, paramLong, 1L);
    long l = this.b;
    if (l - paramLong > paramLong) {
      for (localObject = this.a;; localObject = ((o)localObject).f)
      {
        l = ((o)localObject).c - ((o)localObject).b;
        if (paramLong < l) {
          return localObject.a[(localObject.b + (int)paramLong)];
        }
        paramLong -= l;
      }
    }
    paramLong -= l;
    Object localObject = this.a;
    o localo;
    do
    {
      localo = ((o)localObject).g;
      l = paramLong + (localo.c - localo.b);
      localObject = localo;
      paramLong = l;
    } while (l < 0L);
    return localo.a[(localo.b + (int)l)];
  }
  
  public c c()
  {
    return this;
  }
  
  public c c(int paramInt)
  {
    o localo = e(2);
    byte[] arrayOfByte = localo.a;
    int i = localo.c;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(byte)(paramInt >>> 8 & 0xFF));
    arrayOfByte[j] = ((byte)(byte)(paramInt & 0xFF));
    localo.c = (j + 1);
    this.b += 2L;
    return this;
  }
  
  public void close() {}
  
  public c d(int paramInt)
  {
    o localo = e(4);
    byte[] arrayOfByte = localo.a;
    int i = localo.c;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(byte)(paramInt >>> 24 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(byte)(paramInt >>> 16 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(byte)(paramInt >>> 8 & 0xFF));
    arrayOfByte[j] = ((byte)(byte)(paramInt & 0xFF));
    localo.c = (j + 1);
    this.b += 4L;
    return this;
  }
  
  public d d()
  {
    return this;
  }
  
  public f d(long paramLong)
  {
    return new f(h(paramLong));
  }
  
  o e(int paramInt)
  {
    if ((paramInt >= 1) && (paramInt <= 8192))
    {
      Object localObject = this.a;
      if (localObject == null)
      {
        this.a = p.a();
        localObject = this.a;
        ((o)localObject).g = ((o)localObject);
        ((o)localObject).f = ((o)localObject);
        return (o)localObject;
      }
      o localo = ((o)localObject).g;
      if (localo.c + paramInt <= 8192)
      {
        localObject = localo;
        if (localo.e) {}
      }
      else
      {
        localObject = localo.a(p.a());
      }
      return (o)localObject;
    }
    throw new IllegalArgumentException();
  }
  
  public String e(long paramLong)
  {
    return a(paramLong, u.a);
  }
  
  public boolean e()
  {
    boolean bool;
    if (this.b == 0L) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof c)) {
      return false;
    }
    paramObject = (c)paramObject;
    long l1 = this.b;
    if (l1 != ((c)paramObject).b) {
      return false;
    }
    long l2 = 0L;
    if (l1 == 0L) {
      return true;
    }
    o localo = this.a;
    paramObject = ((c)paramObject).a;
    int i = localo.b;
    int j = ((o)paramObject).b;
    while (l2 < this.b)
    {
      l1 = Math.min(localo.c - i, ((o)paramObject).c - j);
      int k = 0;
      while (k < l1)
      {
        if (localo.a[i] != paramObject.a[j]) {
          return false;
        }
        k++;
        i++;
        j++;
      }
      if (i == localo.c)
      {
        localo = localo.f;
        i = localo.b;
      }
      if (j == ((o)paramObject).c)
      {
        paramObject = ((o)paramObject).f;
        j = ((o)paramObject).b;
      }
      l2 += l1;
    }
    return true;
  }
  
  public final f f(int paramInt)
  {
    if (paramInt == 0) {
      return f.b;
    }
    return new q(this, paramInt);
  }
  
  public InputStream f()
  {
    new InputStream()
    {
      public int available()
      {
        return (int)Math.min(c.this.b, 2147483647L);
      }
      
      public void close() {}
      
      public int read()
      {
        if (c.this.b > 0L) {
          return c.this.h() & 0xFF;
        }
        return -1;
      }
      
      public int read(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return c.this.a(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(c.this);
        localStringBuilder.append(".inputStream()");
        return localStringBuilder.toString();
      }
    };
  }
  
  public String f(long paramLong)
  {
    if (paramLong >= 0L)
    {
      long l1 = Long.MAX_VALUE;
      if (paramLong != Long.MAX_VALUE) {
        l1 = paramLong + 1L;
      }
      long l2 = a((byte)10, 0L, l1);
      if (l2 != -1L) {
        return g(l2);
      }
      if ((l1 < a()) && (c(l1 - 1L) == 13) && (c(l1) == 10)) {
        return g(l1);
      }
      c localc = new c();
      a(localc, 0L, Math.min(32L, a()));
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("\\n not found: limit=");
      localStringBuilder.append(Math.min(a(), paramLong));
      localStringBuilder.append(" content=");
      localStringBuilder.append(localc.p().f());
      localStringBuilder.append('…');
      throw new EOFException(localStringBuilder.toString());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("limit < 0: ");
    localStringBuilder.append(paramLong);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public void flush() {}
  
  public final long g()
  {
    long l1 = this.b;
    if (l1 == 0L) {
      return 0L;
    }
    o localo = this.a.g;
    long l2 = l1;
    if (localo.c < 8192)
    {
      l2 = l1;
      if (localo.e) {
        l2 = l1 - (localo.c - localo.b);
      }
    }
    return l2;
  }
  
  String g(long paramLong)
  {
    if (paramLong > 0L)
    {
      long l = paramLong - 1L;
      if (c(l) == 13)
      {
        str = e(l);
        i(2L);
        return str;
      }
    }
    String str = e(paramLong);
    i(1L);
    return str;
  }
  
  public byte h()
  {
    if (this.b != 0L)
    {
      o localo = this.a;
      int i = localo.b;
      int j = localo.c;
      byte[] arrayOfByte = localo.a;
      int k = i + 1;
      byte b1 = arrayOfByte[i];
      this.b -= 1L;
      if (k == j)
      {
        this.a = localo.c();
        p.a(localo);
      }
      else
      {
        localo.b = k;
      }
      return b1;
    }
    throw new IllegalStateException("size == 0");
  }
  
  public byte[] h(long paramLong)
  {
    u.a(this.b, 0L, paramLong);
    if (paramLong <= 2147483647L)
    {
      localObject = new byte[(int)paramLong];
      a((byte[])localObject);
      return (byte[])localObject;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("byteCount > Integer.MAX_VALUE: ");
    ((StringBuilder)localObject).append(paramLong);
    throw new IllegalArgumentException(((StringBuilder)localObject).toString());
  }
  
  public int hashCode()
  {
    Object localObject = this.a;
    if (localObject == null) {
      return 0;
    }
    int i = 1;
    int m;
    o localo;
    do
    {
      int j = ((o)localObject).b;
      int k = ((o)localObject).c;
      m = i;
      while (j < k)
      {
        m = m * 31 + localObject.a[j];
        j++;
      }
      localo = ((o)localObject).f;
      localObject = localo;
      i = m;
    } while (localo != this.a);
    return m;
  }
  
  public short i()
  {
    if (this.b >= 2L)
    {
      o localo = this.a;
      int i = localo.b;
      int j = localo.c;
      if (j - i < 2) {
        return (short)((h() & 0xFF) << 8 | h() & 0xFF);
      }
      localObject = localo.a;
      int k = i + 1;
      int m = localObject[i];
      i = k + 1;
      k = localObject[k];
      this.b -= 2L;
      if (i == j)
      {
        this.a = localo.c();
        p.a(localo);
      }
      else
      {
        localo.b = i;
      }
      return (short)((m & 0xFF) << 8 | k & 0xFF);
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("size < 2: ");
    ((StringBuilder)localObject).append(this.b);
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public void i(long paramLong)
  {
    while (paramLong > 0L)
    {
      o localo = this.a;
      if (localo != null)
      {
        int i = (int)Math.min(paramLong, localo.c - this.a.b);
        long l1 = this.b;
        long l2 = i;
        this.b = (l1 - l2);
        l1 = paramLong - l2;
        localo = this.a;
        localo.b += i;
        paramLong = l1;
        if (this.a.b == this.a.c)
        {
          localo = this.a;
          this.a = localo.c();
          p.a(localo);
          paramLong = l1;
        }
      }
      else
      {
        throw new EOFException();
      }
    }
  }
  
  public boolean isOpen()
  {
    return true;
  }
  
  public int j()
  {
    if (this.b >= 4L)
    {
      localObject = this.a;
      int i = ((o)localObject).b;
      int j = ((o)localObject).c;
      if (j - i < 4) {
        return (h() & 0xFF) << 24 | (h() & 0xFF) << 16 | (h() & 0xFF) << 8 | h() & 0xFF;
      }
      byte[] arrayOfByte = ((o)localObject).a;
      int k = i + 1;
      i = arrayOfByte[i];
      int m = k + 1;
      k = arrayOfByte[k];
      int n = m + 1;
      m = arrayOfByte[m];
      int i1 = n + 1;
      n = arrayOfByte[n];
      this.b -= 4L;
      if (i1 == j)
      {
        this.a = ((o)localObject).c();
        p.a((o)localObject);
      }
      else
      {
        ((o)localObject).b = i1;
      }
      return (i & 0xFF) << 24 | (k & 0xFF) << 16 | (m & 0xFF) << 8 | n & 0xFF;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("size < 4: ");
    ((StringBuilder)localObject).append(this.b);
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public c j(long paramLong)
  {
    o localo = e(8);
    byte[] arrayOfByte = localo.a;
    int i = localo.c;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(byte)(int)(paramLong >>> 56 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(byte)(int)(paramLong >>> 48 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(byte)(int)(paramLong >>> 40 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(byte)(int)(paramLong >>> 32 & 0xFF));
    int k = i + 1;
    arrayOfByte[i] = ((byte)(byte)(int)(paramLong >>> 24 & 0xFF));
    j = k + 1;
    arrayOfByte[k] = ((byte)(byte)(int)(paramLong >>> 16 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(byte)(int)(paramLong >>> 8 & 0xFF));
    arrayOfByte[i] = ((byte)(byte)(int)(paramLong & 0xFF));
    localo.c = (i + 1);
    this.b += 8L;
    return this;
  }
  
  public long k()
  {
    if (this.b >= 8L)
    {
      o localo = this.a;
      int i = localo.b;
      int j = localo.c;
      if (j - i < 8) {
        return (j() & 0xFFFFFFFF) << 32 | 0xFFFFFFFF & j();
      }
      localObject = localo.a;
      int k = i + 1;
      long l1 = localObject[i];
      i = k + 1;
      long l2 = localObject[k];
      k = i + 1;
      long l3 = localObject[i];
      int m = k + 1;
      long l4 = localObject[k];
      i = m + 1;
      long l5 = localObject[m];
      k = i + 1;
      long l6 = localObject[i];
      i = k + 1;
      long l7 = localObject[k];
      k = i + 1;
      long l8 = localObject[i];
      this.b -= 8L;
      if (k == j)
      {
        this.a = localo.c();
        p.a(localo);
      }
      else
      {
        localo.b = k;
      }
      return l8 & 0xFF | (l7 & 0xFF) << 8 | (l1 & 0xFF) << 56 | (l2 & 0xFF) << 48 | (l3 & 0xFF) << 40 | (l4 & 0xFF) << 32 | (l5 & 0xFF) << 24 | (l6 & 0xFF) << 16;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("size < 8: ");
    ((StringBuilder)localObject).append(this.b);
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public c k(long paramLong)
  {
    if (paramLong == 0L) {
      return b(48);
    }
    int i = 0;
    int j = 1;
    long l = paramLong;
    if (paramLong < 0L)
    {
      l = -paramLong;
      if (l < 0L) {
        return a("-9223372036854775808");
      }
      i = 1;
    }
    if (l < 100000000L)
    {
      if (l < 10000L)
      {
        if (l < 100L)
        {
          if (l >= 10L) {
            j = 2;
          }
        }
        else if (l < 1000L) {
          j = 3;
        } else {
          j = 4;
        }
      }
      else if (l < 1000000L)
      {
        if (l < 100000L) {
          j = 5;
        } else {
          j = 6;
        }
      }
      else if (l < 10000000L) {
        j = 7;
      } else {
        j = 8;
      }
    }
    else if (l < 1000000000000L)
    {
      if (l < 10000000000L)
      {
        if (l < 1000000000L) {
          j = 9;
        } else {
          j = 10;
        }
      }
      else if (l < 100000000000L) {
        j = 11;
      } else {
        j = 12;
      }
    }
    else if (l < 1000000000000000L)
    {
      if (l < 10000000000000L) {
        j = 13;
      } else if (l < 100000000000000L) {
        j = 14;
      } else {
        j = 15;
      }
    }
    else if (l < 100000000000000000L)
    {
      if (l < 10000000000000000L) {
        j = 16;
      } else {
        j = 17;
      }
    }
    else if (l < 1000000000000000000L) {
      j = 18;
    } else {
      j = 19;
    }
    int k = j;
    if (i != 0) {
      k = j + 1;
    }
    o localo = e(k);
    byte[] arrayOfByte = localo.a;
    j = localo.c + k;
    while (l != 0L)
    {
      int m = (int)(l % 10L);
      j--;
      arrayOfByte[j] = ((byte)c[m]);
      l /= 10L;
    }
    if (i != 0) {
      arrayOfByte[(j - 1)] = ((byte)45);
    }
    localo.c += k;
    this.b += k;
    return this;
  }
  
  public c l(long paramLong)
  {
    if (paramLong == 0L) {
      return b(48);
    }
    int i = Long.numberOfTrailingZeros(Long.highestOneBit(paramLong)) / 4 + 1;
    o localo = e(i);
    byte[] arrayOfByte = localo.a;
    int j = localo.c + i - 1;
    int k = localo.c;
    while (j >= k)
    {
      arrayOfByte[j] = ((byte)c[((int)(0xF & paramLong))]);
      paramLong >>>= 4;
      j--;
    }
    localo.c += i;
    this.b += i;
    return this;
  }
  
  public short l()
  {
    return u.a(i());
  }
  
  public int m()
  {
    return u.a(j());
  }
  
  public long n()
  {
    long l1 = this.b;
    long l2 = 0L;
    if (l1 != 0L)
    {
      int i = 0;
      long l3 = -7L;
      int j = 0;
      int k = 0;
      int i1;
      int i2;
      label298:
      do
      {
        Object localObject1 = this.a;
        Object localObject2 = ((o)localObject1).a;
        int m = ((o)localObject1).b;
        int n = ((o)localObject1).c;
        i1 = i;
        i2 = j;
        l1 = l2;
        for (;;)
        {
          i = k;
          if (m >= n) {
            break label298;
          }
          i = localObject2[m];
          if ((i >= 48) && (i <= 57))
          {
            j = 48 - i;
            if ((l1 >= -922337203685477580L) && ((l1 != -922337203685477580L) || (j >= l3)))
            {
              l1 = l1 * 10L + j;
            }
            else
            {
              localObject2 = new c().k(l1).b(i);
              if (i2 == 0) {
                ((c)localObject2).h();
              }
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("Number too large: ");
              ((StringBuilder)localObject1).append(((c)localObject2).q());
              throw new NumberFormatException(((StringBuilder)localObject1).toString());
            }
          }
          else
          {
            if ((i != 45) || (i1 != 0)) {
              break;
            }
            l3 -= 1L;
            i2 = 1;
          }
          m++;
          i1++;
        }
        if (i1 != 0)
        {
          i = 1;
        }
        else
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("Expected leading [0-9] or '-' character but was 0x");
          ((StringBuilder)localObject2).append(Integer.toHexString(i));
          throw new NumberFormatException(((StringBuilder)localObject2).toString());
        }
        if (m == n)
        {
          this.a = ((o)localObject1).c();
          p.a((o)localObject1);
        }
        else
        {
          ((o)localObject1).b = m;
        }
        if (i != 0) {
          break;
        }
        l2 = l1;
        j = i2;
        k = i;
        i = i1;
      } while (this.a != null);
      this.b -= i1;
      if (i2 == 0) {
        l1 = -l1;
      }
      return l1;
    }
    throw new IllegalStateException("size == 0");
  }
  
  public long o()
  {
    if (this.b != 0L)
    {
      int i = 0;
      long l1 = 0L;
      int j = 0;
      long l2;
      int n;
      label233:
      label286:
      do
      {
        Object localObject1 = this.a;
        Object localObject2 = ((o)localObject1).a;
        int k = ((o)localObject1).b;
        int m = ((o)localObject1).c;
        l2 = l1;
        int i1;
        for (n = j;; n++)
        {
          j = i;
          if (k >= m) {
            break label286;
          }
          i1 = localObject2[k];
          if ((i1 >= 48) && (i1 <= 57))
          {
            j = i1 - 48;
          }
          else if ((i1 >= 97) && (i1 <= 102))
          {
            j = i1 - 97 + 10;
          }
          else
          {
            if ((i1 < 65) || (i1 > 70)) {
              break label233;
            }
            j = i1 - 65 + 10;
          }
          if ((0xF000000000000000 & l2) != 0L) {
            break;
          }
          l2 = l2 << 4 | j;
          k++;
        }
        localObject1 = new c().l(l2).b(i1);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("Number too large: ");
        ((StringBuilder)localObject2).append(((c)localObject1).q());
        throw new NumberFormatException(((StringBuilder)localObject2).toString());
        if (n != 0)
        {
          j = 1;
        }
        else
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("Expected leading [0-9a-fA-F] character but was 0x");
          ((StringBuilder)localObject2).append(Integer.toHexString(i1));
          throw new NumberFormatException(((StringBuilder)localObject2).toString());
        }
        if (k == m)
        {
          this.a = ((o)localObject1).c();
          p.a((o)localObject1);
        }
        else
        {
          ((o)localObject1).b = k;
        }
        if (j != 0) {
          break;
        }
        i = j;
        j = n;
        l1 = l2;
      } while (this.a != null);
      this.b -= n;
      return l2;
    }
    throw new IllegalStateException("size == 0");
  }
  
  public f p()
  {
    return new f(s());
  }
  
  public String q()
  {
    try
    {
      String str = a(this.b, u.a);
      return str;
    }
    catch (EOFException localEOFException)
    {
      throw new AssertionError(localEOFException);
    }
  }
  
  public String r()
  {
    return f(Long.MAX_VALUE);
  }
  
  public int read(ByteBuffer paramByteBuffer)
  {
    o localo = this.a;
    if (localo == null) {
      return -1;
    }
    int i = Math.min(paramByteBuffer.remaining(), localo.c - localo.b);
    paramByteBuffer.put(localo.a, localo.b, i);
    localo.b += i;
    this.b -= i;
    if (localo.b == localo.c)
    {
      this.a = localo.c();
      p.a(localo);
    }
    return i;
  }
  
  public long read(c paramc, long paramLong)
  {
    if (paramc != null)
    {
      if (paramLong >= 0L)
      {
        long l1 = this.b;
        if (l1 == 0L) {
          return -1L;
        }
        long l2 = paramLong;
        if (paramLong > l1) {
          l2 = l1;
        }
        paramc.write(this, l2);
        return l2;
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
    try
    {
      byte[] arrayOfByte = h(this.b);
      return arrayOfByte;
    }
    catch (EOFException localEOFException)
    {
      throw new AssertionError(localEOFException);
    }
  }
  
  public final void t()
  {
    try
    {
      i(this.b);
      return;
    }
    catch (EOFException localEOFException)
    {
      throw new AssertionError(localEOFException);
    }
  }
  
  public t timeout()
  {
    return t.NONE;
  }
  
  public String toString()
  {
    return v().toString();
  }
  
  public c u()
  {
    c localc = new c();
    if (this.b == 0L) {
      return localc;
    }
    localc.a = this.a.a();
    o localo = localc.a;
    localo.g = localo;
    localo.f = localo;
    localo = this.a;
    for (;;)
    {
      localo = localo.f;
      if (localo == this.a) {
        break;
      }
      localc.a.g.a(localo.a());
    }
    localc.b = this.b;
    return localc;
  }
  
  public final f v()
  {
    long l = this.b;
    if (l <= 2147483647L) {
      return f((int)l);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("size > Integer.MAX_VALUE: ");
    localStringBuilder.append(this.b);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public int write(ByteBuffer paramByteBuffer)
  {
    if (paramByteBuffer != null)
    {
      int i = paramByteBuffer.remaining();
      int j = i;
      while (j > 0)
      {
        o localo = e(1);
        int k = Math.min(j, 8192 - localo.c);
        paramByteBuffer.get(localo.a, localo.c, k);
        j -= k;
        localo.c += k;
      }
      this.b += i;
      return i;
    }
    throw new IllegalArgumentException("source == null");
  }
  
  public void write(c paramc, long paramLong)
  {
    if (paramc != null)
    {
      if (paramc != this)
      {
        u.a(paramc.b, 0L, paramLong);
        while (paramLong > 0L)
        {
          if (paramLong < paramc.a.c - paramc.a.b)
          {
            localo1 = this.a;
            if (localo1 != null) {
              localo1 = localo1.g;
            } else {
              localo1 = null;
            }
            if ((localo1 != null) && (localo1.e))
            {
              l = localo1.c;
              int i;
              if (localo1.d) {
                i = 0;
              } else {
                i = localo1.b;
              }
              if (l + paramLong - i <= 8192L)
              {
                paramc.a.a(localo1, (int)paramLong);
                paramc.b -= paramLong;
                this.b += paramLong;
                return;
              }
            }
            paramc.a = paramc.a.a((int)paramLong);
          }
          o localo1 = paramc.a;
          long l = localo1.c - localo1.b;
          paramc.a = localo1.c();
          o localo2 = this.a;
          if (localo2 == null)
          {
            this.a = localo1;
            localo1 = this.a;
            localo1.g = localo1;
            localo1.f = localo1;
          }
          else
          {
            localo2.g.a(localo1).d();
          }
          paramc.b -= l;
          this.b += l;
          paramLong -= l;
        }
        return;
      }
      throw new IllegalArgumentException("source == this");
    }
    throw new IllegalArgumentException("source == null");
  }
  
  public static final class a
    implements Closeable
  {
    public c a;
    public boolean b;
    public long c = -1L;
    public byte[] d;
    public int e = -1;
    public int f = -1;
    private o g;
    
    public final int a()
    {
      if (this.c != this.a.b)
      {
        long l = this.c;
        if (l == -1L) {
          return a(0L);
        }
        return a(l + (this.f - this.e));
      }
      throw new IllegalStateException();
    }
    
    public final int a(long paramLong)
    {
      if ((paramLong >= -1L) && (paramLong <= this.a.b))
      {
        if ((paramLong != -1L) && (paramLong != this.a.b))
        {
          long l1 = 0L;
          long l2 = this.a.b;
          o localo1 = this.a.a;
          o localo2 = this.a.a;
          o localo3 = this.g;
          long l3 = l1;
          long l4 = l2;
          Object localObject1 = localo1;
          Object localObject2 = localo2;
          if (localo3 != null)
          {
            l3 = this.c - (this.e - localo3.b);
            if (l3 > paramLong)
            {
              localObject2 = this.g;
              l4 = l3;
              l3 = l1;
              localObject1 = localo1;
            }
            else
            {
              localObject1 = this.g;
              localObject2 = localo2;
              l4 = l2;
            }
          }
          if (l4 - paramLong > paramLong - l3) {
            for (;;)
            {
              l4 = l3;
              localObject2 = localObject1;
              if (paramLong < ((o)localObject1).c - ((o)localObject1).b + l3) {
                break;
              }
              l3 += ((o)localObject1).c - ((o)localObject1).b;
              localObject1 = ((o)localObject1).f;
            }
          }
          l3 = l4;
          localObject1 = localObject2;
          for (;;)
          {
            l4 = l3;
            localObject2 = localObject1;
            if (l3 <= paramLong) {
              break;
            }
            localObject1 = ((o)localObject1).g;
            l3 -= ((o)localObject1).c - ((o)localObject1).b;
          }
          localObject1 = localObject2;
          if (this.b)
          {
            localObject1 = localObject2;
            if (((o)localObject2).d)
            {
              localObject1 = ((o)localObject2).b();
              if (this.a.a == localObject2) {
                this.a.a = ((o)localObject1);
              }
              localObject1 = ((o)localObject2).a((o)localObject1);
              ((o)localObject1).g.c();
            }
          }
          this.g = ((o)localObject1);
          this.c = paramLong;
          this.d = ((o)localObject1).a;
          this.e = (((o)localObject1).b + (int)(paramLong - l4));
          this.f = ((o)localObject1).c;
          return this.f - this.e;
        }
        this.g = null;
        this.c = paramLong;
        this.d = null;
        this.e = -1;
        this.f = -1;
        return -1;
      }
      throw new ArrayIndexOutOfBoundsException(String.format("offset=%s > size=%s", new Object[] { Long.valueOf(paramLong), Long.valueOf(this.a.b) }));
    }
    
    public void close()
    {
      if (this.a != null)
      {
        this.a = null;
        this.g = null;
        this.c = -1L;
        this.d = null;
        this.e = -1;
        this.f = -1;
        return;
      }
      throw new IllegalStateException("not attached to a buffer");
    }
  }
}


/* Location:              ~/a/c.class
 *
 * Reversed by:           J
 */