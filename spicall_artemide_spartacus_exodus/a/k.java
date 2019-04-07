package a;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public final class k
  implements s
{
  private final e a;
  private final Inflater b;
  private int c;
  private boolean d;
  
  k(e parame, Inflater paramInflater)
  {
    if (parame != null)
    {
      if (paramInflater != null)
      {
        this.a = parame;
        this.b = paramInflater;
        return;
      }
      throw new IllegalArgumentException("inflater == null");
    }
    throw new IllegalArgumentException("source == null");
  }
  
  private void b()
  {
    int i = this.c;
    if (i == 0) {
      return;
    }
    i -= this.b.getRemaining();
    this.c -= i;
    this.a.i(i);
  }
  
  public final boolean a()
  {
    if (!this.b.needsInput()) {
      return false;
    }
    b();
    if (this.b.getRemaining() == 0)
    {
      if (this.a.e()) {
        return true;
      }
      o localo = this.a.b().a;
      this.c = (localo.c - localo.b);
      this.b.setInput(localo.a, localo.b, this.c);
      return false;
    }
    throw new IllegalStateException("?");
  }
  
  public void close()
  {
    if (this.d) {
      return;
    }
    this.b.end();
    this.d = true;
    this.a.close();
  }
  
  public long read(c paramc, long paramLong)
  {
    if (paramLong >= 0L)
    {
      if (!this.d)
      {
        if (paramLong == 0L) {
          return 0L;
        }
        for (;;)
        {
          boolean bool = a();
          try
          {
            o localo = paramc.e(1);
            int i = (int)Math.min(paramLong, 8192 - localo.c);
            i = this.b.inflate(localo.a, localo.c, i);
            if (i > 0)
            {
              localo.c += i;
              long l = paramc.b;
              paramLong = i;
              paramc.b = (l + paramLong);
              return paramLong;
            }
            if ((!this.b.finished()) && (!this.b.needsDictionary()))
            {
              if (bool)
              {
                paramc = new java/io/EOFException;
                paramc.<init>("source exhausted prematurely");
                throw paramc;
              }
            }
            else
            {
              b();
              if (localo.b == localo.c)
              {
                paramc.a = localo.c();
                p.a(localo);
              }
              return -1L;
            }
          }
          catch (DataFormatException paramc)
          {
            throw new IOException(paramc);
          }
        }
      }
      throw new IllegalStateException("closed");
    }
    paramc = new StringBuilder();
    paramc.append("byteCount < 0: ");
    paramc.append(paramLong);
    throw new IllegalArgumentException(paramc.toString());
  }
  
  public t timeout()
  {
    return this.a.timeout();
  }
}


/* Location:              ~/a/k.class
 *
 * Reversed by:           J
 */