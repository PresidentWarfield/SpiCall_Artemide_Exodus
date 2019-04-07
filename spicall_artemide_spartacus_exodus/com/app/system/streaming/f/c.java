package com.app.system.streaming.f;

import com.security.d.a;
import java.io.IOException;
import java.io.InputStream;

public class c
  extends d
  implements Runnable
{
  private static final int[] f = { 95, 103, 118, 134, 148, 159, 204, 244 };
  private final int e = 6;
  private int g = 8000;
  private Thread h;
  
  public c()
  {
    this.a.a(this.g);
  }
  
  private int a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = 0;
    while (i < paramInt2)
    {
      int j = this.b.read(paramArrayOfByte, paramInt1 + i, paramInt2 - i);
      if (j >= 0) {
        i += j;
      } else {
        throw new IOException("End of stream");
      }
    }
    return i;
  }
  
  public void a()
  {
    if (this.h == null)
    {
      this.h = new Thread(this);
      this.h.start();
    }
  }
  
  public void b()
  {
    if (this.h != null) {}
    try
    {
      this.b.close();
      this.h.interrupt();
    }
    catch (IOException localIOException)
    {
      try
      {
        this.h.join();
        this.h = null;
        return;
        localIOException = localIOException;
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;) {}
      }
    }
  }
  
  public void run()
  {
    System.nanoTime();
    byte[] arrayOfByte = new byte[6];
    try
    {
      a(arrayOfByte, 0, 6);
      if (arrayOfByte[5] != 10)
      {
        a.a("AMRNBPacketizer", "Bad header ! AMR not correcty supported by the phone !", new Object[0]);
        return;
      }
      while (!Thread.interrupted())
      {
        this.c = this.a.a();
        this.c[12] = ((byte)-16);
        a(this.c, 13, 1);
        int i = Math.abs(this.c[13]);
        i = (f[(i >> 3 & 0xF)] + 7) / 8;
        a(this.c, 14, i);
        this.d += 160000000000L / this.g;
        this.a.c(this.d);
        this.a.d();
        c(i + 14);
      }
    }
    catch (IOException|InterruptedException localIOException)
    {
      for (;;) {}
    }
    a.d("AMRNBPacketizer", "AMR packetizer stopped !", new Object[0]);
  }
}


/* Location:              ~/com/app/system/streaming/f/c.class
 *
 * Reversed by:           J
 */