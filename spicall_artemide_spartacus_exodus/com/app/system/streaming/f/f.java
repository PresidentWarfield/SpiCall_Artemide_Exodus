package com.app.system.streaming.f;

import android.annotation.SuppressLint;
import android.media.MediaCodec.BufferInfo;
import com.security.d.a;
import java.io.IOException;
import java.io.InputStream;

public class f
  extends d
  implements Runnable
{
  byte[] e = new byte[5];
  private Thread f = null;
  private int g = 0;
  private long h = 0L;
  private long i = 0L;
  private d.a j = new d.a();
  private byte[] k = null;
  private byte[] l = null;
  private byte[] m = null;
  private int n = 0;
  private int o = 1;
  
  public f()
  {
    this.a.a(90000L);
  }
  
  private int a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i1 = 0;
    while (i1 < paramInt2)
    {
      int i2 = this.b.read(paramArrayOfByte, paramInt1 + i1, paramInt2 - i1);
      if (i2 >= 0) {
        i1 += i2;
      } else {
        throw new IOException("End of stream");
      }
    }
    return i1;
  }
  
  @SuppressLint({"NewApi"})
  private void d()
  {
    int i1 = this.o;
    byte[] arrayOfByte;
    int i2;
    if (i1 == 0)
    {
      a(this.e, 0, 5);
      this.d += this.h;
      arrayOfByte = this.e;
      i2 = arrayOfByte[3];
      int i3 = arrayOfByte[2];
      i1 = arrayOfByte[1];
      this.g = ((arrayOfByte[0] & 0xFF) << 24 | i2 & 0xFF | (i3 & 0xFF) << 8 | (i1 & 0xFF) << 16);
      i1 = this.g;
      if ((i1 > 100000) || (i1 < 0)) {
        e();
      }
    }
    else if (i1 == 1)
    {
      a(this.e, 0, 5);
      this.d = (((g)this.b).a().presentationTimeUs * 1000L);
      this.g = (this.b.available() + 1);
      arrayOfByte = this.e;
      if ((arrayOfByte[0] != 0) || (arrayOfByte[1] != 0) || (arrayOfByte[2] != 0))
      {
        a.a("H264Packetizer", "NAL units are not preceeded by 0x00000001", new Object[0]);
        this.o = 2;
      }
    }
    else
    {
      a(this.e, 0, 1);
      arrayOfByte = this.e;
      arrayOfByte[4] = ((byte)arrayOfByte[0]);
      this.d = (((g)this.b).a().presentationTimeUs * 1000L);
      this.g = (this.b.available() + 1);
    }
    i1 = this.e[4] & 0x1F;
    if ((i1 == 7) || (i1 == 8))
    {
      a.e("H264Packetizer", "SPS or PPS present in the stream.", new Object[0]);
      this.n += 1;
      if (this.n > 4)
      {
        this.k = null;
        this.l = null;
      }
    }
    if ((i1 == 5) && (this.k != null) && (this.l != null))
    {
      this.c = this.a.a();
      this.a.d();
      this.a.c(this.d);
      System.arraycopy(this.m, 0, this.c, 12, this.m.length);
      super.c(this.m.length + 12);
    }
    if (this.g <= 1258)
    {
      this.c = this.a.a();
      this.c[12] = ((byte)this.e[4]);
      a(this.c, 13, this.g - 1);
      this.a.c(this.d);
      this.a.d();
      super.c(this.g + 12);
    }
    else
    {
      arrayOfByte = this.e;
      arrayOfByte[1] = ((byte)(byte)(arrayOfByte[4] & 0x1F));
      arrayOfByte[1] = ((byte)(byte)(arrayOfByte[1] + 128));
      arrayOfByte[0] = ((byte)(byte)(arrayOfByte[4] & 0x60 & 0xFF));
      arrayOfByte[0] = ((byte)(byte)(arrayOfByte[0] + 28));
      i1 = 1;
      while (i1 < this.g)
      {
        this.c = this.a.a();
        this.c[12] = ((byte)this.e[0]);
        this.c[13] = ((byte)this.e[1]);
        this.a.c(this.d);
        arrayOfByte = this.c;
        i2 = this.g;
        if (i2 - i1 > 1258) {
          i2 = 1258;
        } else {
          i2 -= i1;
        }
        i2 = a(arrayOfByte, 14, i2);
        if (i2 < 0) {
          return;
        }
        i1 += i2;
        if (i1 >= this.g)
        {
          arrayOfByte = this.c;
          arrayOfByte[13] = ((byte)(byte)(arrayOfByte[13] + 64));
          this.a.d();
        }
        super.c(i2 + 12 + 2);
        arrayOfByte = this.e;
        arrayOfByte[1] = ((byte)(byte)(arrayOfByte[1] & 0x7F));
      }
    }
  }
  
  private void e()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Packetizer out of sync ! Let's try to fix that...(NAL length: ");
    ((StringBuilder)localObject).append(this.g);
    ((StringBuilder)localObject).append(")");
    a.a("H264Packetizer", ((StringBuilder)localObject).toString(), new Object[0]);
    for (;;)
    {
      localObject = this.e;
      localObject[0] = ((byte)localObject[1]);
      localObject[1] = ((byte)localObject[2]);
      localObject[2] = ((byte)localObject[3]);
      localObject[3] = ((byte)localObject[4]);
      localObject[4] = ((byte)(byte)this.b.read());
      int i1 = this.e[4] & 0x1F;
      if ((i1 == 5) || (i1 == 1))
      {
        localObject = this.e;
        int i2 = localObject[3];
        int i3 = localObject[2];
        i1 = localObject[1];
        this.g = ((localObject[0] & 0xFF) << 24 | i2 & 0xFF | (i3 & 0xFF) << 8 | (i1 & 0xFF) << 16);
        i1 = this.g;
        if ((i1 > 0) && (i1 < 100000))
        {
          this.i = System.nanoTime();
          a.a("H264Packetizer", "A NAL unit may have been found in the bit stream !", new Object[0]);
          return;
        }
        if (this.g == 0)
        {
          a.a("H264Packetizer", "NAL unit with NULL size found...", new Object[0]);
        }
        else
        {
          localObject = this.e;
          if ((localObject[3] == 255) && (localObject[2] == 255) && (localObject[1] == 255) && (localObject[0] == 255)) {
            a.a("H264Packetizer", "NAL unit with 0xFFFFFFFF size found...", new Object[0]);
          }
        }
      }
    }
  }
  
  public void a()
  {
    if (this.f == null)
    {
      this.f = new Thread(this);
      this.f.start();
    }
  }
  
  public void a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    this.l = paramArrayOfByte1;
    this.k = paramArrayOfByte2;
    if ((paramArrayOfByte1 != null) && (paramArrayOfByte2 != null))
    {
      this.m = new byte[paramArrayOfByte2.length + paramArrayOfByte1.length + 5];
      byte[] arrayOfByte = this.m;
      arrayOfByte[0] = ((byte)24);
      arrayOfByte[1] = ((byte)(byte)(paramArrayOfByte2.length >> 8));
      arrayOfByte[2] = ((byte)(byte)(paramArrayOfByte2.length & 0xFF));
      arrayOfByte[(paramArrayOfByte2.length + 3)] = ((byte)(byte)(paramArrayOfByte1.length >> 8));
      arrayOfByte[(paramArrayOfByte2.length + 4)] = ((byte)(byte)(paramArrayOfByte1.length & 0xFF));
      System.arraycopy(paramArrayOfByte2, 0, arrayOfByte, 3, paramArrayOfByte2.length);
      System.arraycopy(paramArrayOfByte1, 0, this.m, paramArrayOfByte2.length + 5, paramArrayOfByte1.length);
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
    a.d("H264Packetizer", "H264 packetizer started !", new Object[0]);
    this.j.a();
    this.n = 0;
    if ((this.b instanceof g))
    {
      this.o = 1;
      this.a.b(0L);
    }
    else
    {
      this.o = 0;
      this.a.b(400L);
    }
    try
    {
      while (!Thread.interrupted())
      {
        this.i = System.nanoTime();
        d();
        long l1 = System.nanoTime();
        long l2 = this.i;
        this.j.a(l1 - l2);
        this.h = this.j.b();
      }
    }
    catch (IOException|InterruptedException localIOException)
    {
      for (;;) {}
    }
    a.d("H264Packetizer", "H264 packetizer stopped !", new Object[0]);
  }
}


/* Location:              ~/com/app/system/streaming/f/f.class
 *
 * Reversed by:           J
 */