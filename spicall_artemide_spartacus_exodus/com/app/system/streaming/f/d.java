package com.app.system.streaming.f;

import java.io.InputStream;
import java.net.InetAddress;
import java.util.Random;

public abstract class d
{
  protected h a = null;
  protected InputStream b = null;
  protected byte[] c;
  protected long d = 0L;
  
  public d()
  {
    int i = new Random().nextInt();
    this.d = new Random().nextInt();
    this.a = new h();
    this.a.a(i);
  }
  
  public abstract void a();
  
  public void a(InputStream paramInputStream)
  {
    this.b = paramInputStream;
  }
  
  public void a(InetAddress paramInetAddress, int paramInt1, int paramInt2)
  {
    this.a.a(paramInetAddress, paramInt1, paramInt2);
  }
  
  public abstract void b();
  
  public void b(int paramInt)
  {
    this.a.b(paramInt);
  }
  
  public h c()
  {
    return this.a;
  }
  
  protected void c(int paramInt)
  {
    this.a.c(paramInt);
  }
  
  protected static class a
  {
    private int a = 700;
    private int b = 0;
    private float c = 0.0F;
    private float d = 0.0F;
    private long e = 0L;
    private long f = 0L;
    private long g = 0L;
    private long h = 10000000000L;
    private boolean i = false;
    
    public void a()
    {
      this.i = false;
      this.d = 0.0F;
      this.c = 0.0F;
      this.b = 0;
      this.e = 0L;
      this.f = 0L;
      this.g = 0L;
    }
    
    public void a(long paramLong)
    {
      this.e += paramLong;
      long l = paramLong;
      if (this.e > this.h)
      {
        this.e = 0L;
        l = System.nanoTime();
        if ((!this.i) || (l - this.f < 0L))
        {
          this.f = l;
          this.g = 0L;
          this.i = true;
        }
        l = paramLong + (l - this.f - this.g);
      }
      int j = this.b;
      if (j < 5)
      {
        this.b = (j + 1);
        this.c = ((float)l);
      }
      else
      {
        float f1 = this.c;
        float f2 = this.d;
        this.c = ((f1 * f2 + (float)l) / (f2 + 1.0F));
        if (f2 < this.a) {
          this.d = (f2 + 1.0F);
        }
      }
    }
    
    public long b()
    {
      long l = this.c;
      this.g += l;
      return l;
    }
  }
}


/* Location:              ~/com/app/system/streaming/f/d.class
 *
 * Reversed by:           J
 */