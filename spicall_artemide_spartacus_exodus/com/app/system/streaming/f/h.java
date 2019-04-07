package com.app.system.streaming.f;

import android.os.SystemClock;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class h
  implements Runnable
{
  protected OutputStream a = null;
  private MulticastSocket b;
  private DatagramPacket[] c;
  private byte[][] d;
  private long[] e;
  private com.app.system.streaming.e.a f;
  private Semaphore g;
  private Semaphore h;
  private Thread i;
  private int j;
  private long k = 0L;
  private long l = 0L;
  private long m = 0L;
  private int n;
  private int o = 0;
  private int p = -1;
  private int q = 300;
  private int r;
  private int s;
  private int t = 0;
  private byte[] u;
  private a v;
  
  public h()
  {
    int i1 = this.q;
    this.d = new byte[i1][];
    this.c = new DatagramPacket[i1];
    this.f = new com.app.system.streaming.e.a();
    this.v = new a();
    this.j = 0;
    this.u = new byte[] { 36, 0, 0, 0 };
    e();
    Object localObject;
    for (i1 = 0; i1 < this.q; i1++)
    {
      localObject = this.d;
      localObject[i1] = new byte['Ԕ'];
      this.c[i1] = new DatagramPacket(localObject[i1], 1);
      this.d[i1][0] = ((byte)(byte)Integer.parseInt("10000000", 2));
      this.d[i1][1] = ((byte)96);
    }
    try
    {
      localObject = new java/net/MulticastSocket;
      ((MulticastSocket)localObject).<init>();
      this.b = ((MulticastSocket)localObject);
      return;
    }
    catch (Exception localException)
    {
      throw new RuntimeException(localException.getMessage());
    }
  }
  
  private void a(byte[] paramArrayOfByte, long paramLong, int paramInt1, int paramInt2)
  {
    for (;;)
    {
      
      if (paramInt2 < paramInt1) {
        break;
      }
      paramArrayOfByte[paramInt2] = ((byte)(byte)(int)(paramLong % 256L));
      paramLong >>= 8;
    }
  }
  
  private void e()
  {
    this.t = 0;
    this.r = 0;
    this.s = 0;
    int i1 = this.q;
    this.e = new long[i1];
    this.g = new Semaphore(i1);
    this.h = new Semaphore(0);
    this.f.a();
    this.v.a();
  }
  
  private void f()
  {
    byte[] arrayOfByte = this.d[this.r];
    int i1 = this.o + 1;
    this.o = i1;
    a(arrayOfByte, i1, 2, 4);
  }
  
  private void g()
  {
    int i1;
    synchronized (this.a)
    {
      i1 = this.c[this.s].getLength();
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("sent ");
      localStringBuilder.append(i1);
      com.security.d.a.d("RtpSocket", localStringBuilder.toString(), new Object[0]);
      this.u[2] = ((byte)(byte)(i1 >> 8));
      this.u[3] = ((byte)(byte)(i1 & 0xFF));
    }
    try
    {
      this.a.write(this.u);
      this.a.write(this.d[this.s], 0, i1);
      return;
      localObject = finally;
      throw ((Throwable)localObject);
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  public void a(int paramInt)
  {
    this.n = paramInt;
    for (int i1 = 0; i1 < this.q; i1++) {
      a(this.d[i1], paramInt, 8, 12);
    }
    this.f.a(this.n);
  }
  
  public void a(long paramLong)
  {
    this.l = paramLong;
  }
  
  public void a(OutputStream paramOutputStream, byte paramByte)
  {
    if (paramOutputStream != null)
    {
      this.j = 1;
      this.a = paramOutputStream;
      this.u[1] = ((byte)paramByte);
      this.f.a(paramOutputStream, (byte)(paramByte + 1));
    }
  }
  
  public void a(InetAddress paramInetAddress, int paramInt1, int paramInt2)
  {
    if ((paramInt1 != 0) && (paramInt2 != 0))
    {
      int i1 = 0;
      this.j = 0;
      this.p = paramInt1;
      while (i1 < this.q)
      {
        this.c[i1].setPort(paramInt1);
        this.c[i1].setAddress(paramInetAddress);
        i1++;
      }
      this.f.a(paramInetAddress, paramInt2);
    }
  }
  
  public byte[] a()
  {
    this.g.acquire();
    byte[][] arrayOfByte = this.d;
    int i1 = this.r;
    byte[] arrayOfByte1 = arrayOfByte[i1];
    arrayOfByte1[1] = ((byte)(byte)(arrayOfByte1[1] & 0x7F));
    return arrayOfByte[i1];
  }
  
  public void b()
  {
    if (this.i == null)
    {
      this.i = new Thread(this);
      this.i.start();
    }
    int i1 = this.r + 1;
    this.r = i1;
    if (i1 >= this.q) {
      this.r = 0;
    }
    this.h.release();
  }
  
  public void b(int paramInt)
  {
    this.b.setTimeToLive(paramInt);
  }
  
  public void b(long paramLong)
  {
    this.k = paramLong;
  }
  
  public long c()
  {
    return this.v.b();
  }
  
  public void c(int paramInt)
  {
    f();
    this.c[this.r].setLength(paramInt);
    this.v.a(paramInt);
    paramInt = this.r + 1;
    this.r = paramInt;
    if (paramInt >= this.q) {
      this.r = 0;
    }
    this.h.release();
    if (this.i == null)
    {
      this.i = new Thread(this);
      this.i.start();
    }
  }
  
  public void c(long paramLong)
  {
    long[] arrayOfLong = this.e;
    int i1 = this.r;
    arrayOfLong[i1] = paramLong;
    a(this.d[i1], paramLong / 100L * (this.l / 1000L) / 10000L, 4, 8);
  }
  
  public void d()
  {
    byte[] arrayOfByte = this.d[this.r];
    arrayOfByte[1] = ((byte)(byte)(arrayOfByte[1] | 0x80));
  }
  
  public void run()
  {
    b localb = new b(50, 3000L);
    try
    {
      Thread.sleep(this.k);
      long l2;
      for (long l1 = 0L; this.h.tryAcquire(4L, TimeUnit.SECONDS); l1 = l2)
      {
        l2 = l1;
        if (this.m != 0L)
        {
          if (this.e[this.s] - this.m > 0L)
          {
            localb.a(this.e[this.s] - this.m);
            l2 = localb.a() / 1000000L;
            if (this.k > 0L) {
              Thread.sleep(l2);
            }
          }
          else if (this.e[this.s] - this.m < 0L)
          {
            StringBuilder localStringBuilder = new java/lang/StringBuilder;
            localStringBuilder.<init>();
            localStringBuilder.append("TS: ");
            localStringBuilder.append(this.e[this.s]);
            localStringBuilder.append(" OLD: ");
            localStringBuilder.append(this.m);
            com.security.d.a.a("RtpSocket", localStringBuilder.toString(), new Object[0]);
          }
          l1 += this.e[this.s] - this.m;
          if (l1 <= 500000000L)
          {
            l2 = l1;
            if (l1 >= 0L) {}
          }
          else
          {
            l2 = 0L;
          }
        }
        this.f.a(this.c[this.s].getLength(), this.e[this.s] / 100L * (this.l / 1000L) / 10000L);
        this.m = this.e[this.s];
        int i1 = this.t;
        this.t = (i1 + 1);
        if (i1 > 30) {
          if (this.j == 0) {
            this.b.send(this.c[this.s]);
          } else {
            g();
          }
        }
        i1 = this.s + 1;
        this.s = i1;
        if (i1 >= this.q) {
          this.s = 0;
        }
        this.g.release();
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.i = null;
      e();
    }
  }
  
  protected static class a
  {
    private long a;
    private long b;
    private long c;
    private long[] d;
    private long[] e;
    private int f;
    private int g;
    private int h;
    private int i = 25;
    
    public a()
    {
      a();
    }
    
    public void a()
    {
      int j = this.i;
      this.e = new long[j];
      this.d = new long[j];
      this.b = SystemClock.elapsedRealtime();
      this.a = this.b;
      this.f = 0;
      this.c = 0L;
      this.h = 0;
      this.g = 0;
    }
    
    public void a(int paramInt)
    {
      this.b = SystemClock.elapsedRealtime();
      if (this.f > 0)
      {
        this.c += this.b - this.a;
        this.h += paramInt;
        long l = this.c;
        if (l > 200L)
        {
          long[] arrayOfLong = this.e;
          paramInt = this.g;
          arrayOfLong[paramInt] = this.h;
          this.h = 0;
          this.d[paramInt] = l;
          this.c = 0L;
          this.g = (paramInt + 1);
          if (this.g >= this.i) {
            this.g = 0;
          }
        }
      }
      this.a = this.b;
      this.f += 1;
    }
    
    public int b()
    {
      long l1 = 0L;
      int j = 0;
      long l2 = 0L;
      long l3 = l2;
      while (j < this.i)
      {
        l3 += this.e[j];
        l2 += this.d[j];
        j++;
      }
      if (l2 > 0L) {
        l1 = l3 * 8000L / l2;
      }
      return (int)l1;
    }
  }
  
  protected static class b
  {
    private int a = 500;
    private int b = 0;
    private float c = 0.0F;
    private float d = 0.0F;
    private long e = 0L;
    private long f = 0L;
    private long g = 0L;
    private long h = 6000000000L;
    private boolean i = false;
    
    public b(int paramInt, long paramLong)
    {
      this.a = paramInt;
      this.h = (paramLong * 1000000L);
    }
    
    public long a()
    {
      long l = this.c - 2000000L;
      if (l <= 0L) {
        l = 0L;
      }
      return l;
    }
    
    public void a(long paramLong)
    {
      this.g += paramLong;
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
        l = paramLong - (l - this.f - this.g);
      }
      int j = this.b;
      if (j < 40)
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
  }
}


/* Location:              ~/com/app/system/streaming/f/h.class
 *
 * Reversed by:           J
 */