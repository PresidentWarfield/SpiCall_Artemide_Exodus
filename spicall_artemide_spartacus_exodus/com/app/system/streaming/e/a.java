package com.app.system.streaming.e;

import android.os.SystemClock;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class a
{
  private MulticastSocket a;
  private DatagramPacket b;
  private int c = 0;
  private OutputStream d = null;
  private byte[] e = new byte['ל'];
  private int f;
  private int g = -1;
  private int h = 0;
  private int i = 0;
  private long j;
  private long k;
  private long l;
  private long m;
  private byte[] n = { 36, 0, 0, 28 };
  
  public a()
  {
    this.e[0] = ((byte)(byte)Integer.parseInt("10000000", 2));
    this.e[1] = ((byte)-56);
    a(6L, 2, 4);
    try
    {
      MulticastSocket localMulticastSocket = new java/net/MulticastSocket;
      localMulticastSocket.<init>();
      this.a = localMulticastSocket;
      this.b = new DatagramPacket(this.e, 1);
      this.j = 3000L;
      return;
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException.getMessage());
    }
  }
  
  private void a(long paramLong, int paramInt1, int paramInt2)
  {
    for (;;)
    {
      
      if (paramInt2 < paramInt1) {
        break;
      }
      this.e[paramInt2] = ((byte)(byte)(int)(paramLong % 256L));
      paramLong >>= 8;
    }
  }
  
  private void a(long paramLong1, long paramLong2)
  {
    long l1 = paramLong1 / 1000000000L;
    paramLong1 = (paramLong1 - l1 * 1000000000L) * 4294967296L / 1000000000L;
    a(l1, 8, 12);
    a(paramLong1, 12, 16);
    a(paramLong2, 16, 20);
    if (this.c == 0)
    {
      this.b.setLength(28);
      this.a.send(this.b);
    }
    try
    {
      synchronized (this.d)
      {
        this.d.write(this.n);
        this.d.write(this.e, 0, 28);
      }
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return;
    throw ((Throwable)localObject);
  }
  
  public void a()
  {
    this.i = 0;
    this.h = 0;
    a(this.i, 20, 24);
    a(this.h, 24, 28);
    this.m = 0L;
    this.l = 0L;
    this.k = 0L;
  }
  
  public void a(int paramInt)
  {
    this.f = paramInt;
    a(paramInt, 4, 8);
    this.i = 0;
    this.h = 0;
    a(this.i, 20, 24);
    a(this.h, 24, 28);
  }
  
  public void a(int paramInt, long paramLong)
  {
    this.i += 1;
    this.h += paramInt;
    a(this.i, 20, 24);
    a(this.h, 24, 28);
    this.l = SystemClock.elapsedRealtime();
    long l1 = this.k;
    long l2 = this.m;
    if (l2 != 0L) {
      l2 = this.l - l2;
    } else {
      l2 = 0L;
    }
    this.k = (l1 + l2);
    this.m = this.l;
    l2 = this.j;
    if ((l2 > 0L) && (this.k >= l2))
    {
      a(System.nanoTime(), paramLong);
      this.k = 0L;
    }
  }
  
  public void a(OutputStream paramOutputStream, byte paramByte)
  {
    this.c = 1;
    this.d = paramOutputStream;
    this.n[1] = ((byte)paramByte);
  }
  
  public void a(InetAddress paramInetAddress, int paramInt)
  {
    this.c = 0;
    this.g = paramInt;
    this.b.setPort(paramInt);
    this.b.setAddress(paramInetAddress);
  }
}


/* Location:              ~/com/app/system/streaming/e/a.class
 *
 * Reversed by:           J
 */