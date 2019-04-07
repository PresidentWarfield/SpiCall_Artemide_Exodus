package com.app.system.streaming;

import android.annotation.SuppressLint;
import android.media.MediaCodec;
import android.media.MediaRecorder;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Build.VERSION;
import android.os.ParcelFileDescriptor;
import com.app.system.streaming.f.h;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.Random;

public abstract class a
  implements d
{
  protected static byte b = 1;
  protected static final byte e;
  protected com.app.system.streaming.f.d a = null;
  protected byte c;
  protected byte d;
  protected boolean f = false;
  protected boolean g = false;
  protected int h = 0;
  protected int i = 0;
  protected byte j = (byte)0;
  protected OutputStream k = null;
  protected InetAddress l;
  protected ParcelFileDescriptor[] m;
  protected ParcelFileDescriptor n;
  protected ParcelFileDescriptor o;
  protected LocalSocket p;
  protected LocalSocket q = null;
  protected MediaRecorder r;
  protected MediaCodec s;
  private LocalServerSocket t = null;
  private int u;
  private int v = 64;
  
  static
  {
    try
    {
      Class.forName("android.media.MediaCodec");
      b = (byte)2;
      com.security.d.a.c("MediaStream", "Phone supports the MediaCoded API", new Object[0]);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      b = (byte)1;
      com.security.d.a.c("MediaStream", "Phone does not support the MediaCodec API", new Object[0]);
    }
    if (Build.VERSION.SDK_INT > 20) {
      e = (byte)2;
    } else {
      e = (byte)1;
    }
  }
  
  public a()
  {
    int i1 = b;
    this.d = ((byte)i1);
    this.c = ((byte)i1);
  }
  
  public void a(int paramInt)
  {
    if (paramInt % 2 == 1)
    {
      this.h = (paramInt - 1);
      this.i = paramInt;
    }
    else
    {
      this.h = paramInt;
      this.i = (paramInt + 1);
    }
  }
  
  public void a(int paramInt1, int paramInt2)
  {
    this.h = paramInt1;
    this.i = paramInt2;
    this.k = null;
  }
  
  public void a(OutputStream paramOutputStream, byte paramByte)
  {
    this.k = paramOutputStream;
    this.j = ((byte)paramByte);
  }
  
  public void a(InetAddress paramInetAddress)
  {
    this.l = paramInetAddress;
  }
  
  public int[] a()
  {
    return new int[] { this.h, this.i };
  }
  
  public long b()
  {
    long l1;
    if (!this.f) {
      l1 = 0L;
    } else {
      l1 = this.a.c().c();
    }
    return l1;
  }
  
  public void b(int paramInt)
  {
    this.v = paramInt;
  }
  
  public boolean c()
  {
    return this.f;
  }
  
  public void d()
  {
    try
    {
      if (!this.f)
      {
        if (this.a != null)
        {
          this.a.a(this.l, this.h, this.i);
          this.a.c().a(this.k, this.j);
        }
        this.c = ((byte)this.d);
        this.g = true;
        return;
      }
      IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
      localIllegalStateException.<init>("Can't be called while streaming.");
      throw localIllegalStateException;
    }
    finally {}
  }
  
  public void e()
  {
    try
    {
      if (this.l != null)
      {
        if ((this.h > 0) && (this.i > 0))
        {
          this.a.b(this.v);
          if (this.c != 1) {
            h();
          } else {
            g();
          }
          return;
        }
        localIllegalStateException = new java/lang/IllegalStateException;
        localIllegalStateException.<init>("No destination ports set for the stream !");
        throw localIllegalStateException;
      }
      IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
      localIllegalStateException.<init>("No destination ip address set for the stream !");
      throw localIllegalStateException;
    }
    finally {}
  }
  
  @SuppressLint({"NewApi"})
  public void f()
  {
    try
    {
      boolean bool = this.f;
      if (bool)
      {
        try
        {
          if (this.c == 1)
          {
            this.r.stop();
            this.r.release();
            this.r = null;
            k();
            this.a.b();
          }
          else
          {
            this.a.b();
            this.s.stop();
            this.s.release();
            this.s = null;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        this.f = false;
      }
      return;
    }
    finally {}
  }
  
  protected abstract void g();
  
  protected abstract void h();
  
  public abstract String i();
  
  protected void j()
  {
    int i1 = e;
    int i2 = 0;
    if (i1 == 1)
    {
      while (i2 < 10) {
        try
        {
          Object localObject1 = new java/util/Random;
          ((Random)localObject1).<init>();
          this.u = ((Random)localObject1).nextInt();
          localObject2 = new android/net/LocalServerSocket;
          localObject1 = new java/lang/StringBuilder;
          ((StringBuilder)localObject1).<init>();
          ((StringBuilder)localObject1).append("com.app.system.streaming-");
          ((StringBuilder)localObject1).append(this.u);
          ((LocalServerSocket)localObject2).<init>(((StringBuilder)localObject1).toString());
          this.t = ((LocalServerSocket)localObject2);
        }
        catch (IOException localIOException)
        {
          i2++;
        }
      }
      this.p = new LocalSocket();
      LocalSocket localLocalSocket = this.p;
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("com.app.system.streaming-");
      ((StringBuilder)localObject2).append(this.u);
      localLocalSocket.connect(new LocalSocketAddress(((StringBuilder)localObject2).toString()));
      this.p.setReceiveBufferSize(500000);
      this.p.setSoTimeout(3000);
      this.q = this.t.accept();
      this.q.setSendBufferSize(500000);
    }
    else
    {
      com.security.d.a.a("MediaStream", "parcelFileDescriptors createPipe version = Lollipop", new Object[0]);
      this.m = ParcelFileDescriptor.createPipe();
      this.n = new ParcelFileDescriptor(this.m[0]);
      this.o = new ParcelFileDescriptor(this.m[1]);
    }
  }
  
  protected void k()
  {
    if (e == 1)
    {
      try
      {
        this.p.close();
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
      try
      {
        this.q.close();
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
      try
      {
        this.t.close();
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
      }
      this.t = null;
      this.q = null;
      this.p = null;
    }
    else
    {
      try
      {
        if (this.n != null) {
          this.n.close();
        }
      }
      catch (Exception localException4)
      {
        localException4.printStackTrace();
      }
      try
      {
        if (this.o != null) {
          this.o.close();
        }
      }
      catch (Exception localException5)
      {
        localException5.printStackTrace();
      }
    }
  }
}


/* Location:              ~/com/app/system/streaming/a.class
 *
 * Reversed by:           J
 */