package com.app.system.streaming.f;

import com.security.d.a;
import java.io.IOException;
import java.io.InputStream;

public class e
  extends d
  implements Runnable
{
  private d.a e = new d.a();
  private Thread f;
  
  public e()
  {
    this.a.a(90000L);
  }
  
  private int a(int paramInt1, int paramInt2)
  {
    int i = 0;
    while (i < paramInt2)
    {
      int j = this.b.read(this.c, paramInt1 + i, paramInt2 - i);
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
    if (this.f == null)
    {
      this.f = new Thread(this);
      this.f.start();
    }
  }
  
  public void b()
  {
    if (this.f != null) {}
    try
    {
      this.b.close();
      this.f.interrupt();
    }
    catch (IOException localIOException)
    {
      try
      {
        this.f.join();
        this.f = null;
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
    this.e.a();
    long l1 = 0L;
    int i = 0;
    int j = 1;
    try
    {
      while (!Thread.interrupted())
      {
        if (i == 0) {
          this.c = this.a.a();
        }
        this.a.c(this.d);
        this.c[12] = ((byte)0);
        this.c[13] = ((byte)0);
        long l2 = System.nanoTime();
        if (a(i + 12 + 2, 1260 - i - 2) < 0) {
          return;
        }
        l1 += System.nanoTime() - l2;
        for (int k = 14; k < 1271; k++) {
          if ((this.c[k] == 0) && (this.c[(k + 1)] == 0) && ((this.c[(k + 2)] & 0xFC) == 128))
          {
            i = k;
            break label166;
          }
        }
        i = 0;
        label166:
        int m = this.c[(k + 2)];
        k = this.c[(k + 3)];
        if (j != 0)
        {
          this.c[12] = ((byte)4);
          j = 0;
        }
        else
        {
          this.c[12] = ((byte)0);
        }
        if (i > 0)
        {
          this.e.a(l1);
          this.d += this.e.b();
          this.a.d();
          c(i);
          byte[] arrayOfByte1 = this.a.a();
          byte[] arrayOfByte2 = this.c;
          j = 1272 - i - 2;
          System.arraycopy(arrayOfByte2, i + 2, arrayOfByte1, 14, j);
          this.c = arrayOfByte1;
          l1 = 0L;
          i = j;
          j = 1;
        }
        else
        {
          c(1272);
        }
      }
    }
    catch (IOException|InterruptedException localIOException)
    {
      for (;;) {}
    }
    a.d("H263Packetizer", "H263 Packetizer stopped !", new Object[0]);
  }
}


/* Location:              ~/com/app/system/streaming/f/e.class
 *
 * Reversed by:           J
 */