package com.app.system.streaming;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.app.system.streaming.exceptions.CameraInUseException;
import com.app.system.streaming.exceptions.ConfNotSupportedException;
import com.app.system.streaming.exceptions.InvalidSurfaceException;
import com.app.system.streaming.exceptions.StorageUnavailableException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class b
{
  private String a;
  private String b;
  private int c = 64;
  private long d;
  private com.app.system.streaming.a.d e = null;
  private com.app.system.streaming.h.d f = null;
  private a g;
  private Handler h;
  private Handler i;
  private Runnable j = new Runnable()
  {
    public void run()
    {
      if (b.this.g())
      {
        b localb = b.this;
        b.a(localb, localb.f());
        b.c(b.this).postDelayed(b.b(b.this), 500L);
      }
      else
      {
        b.a(b.this, 0L);
      }
    }
  };
  
  public b()
  {
    long l1 = System.currentTimeMillis();
    HandlerThread localHandlerThread = new HandlerThread("com.app.system.streaming.Session");
    localHandlerThread.start();
    this.i = new Handler(localHandlerThread.getLooper());
    this.h = new Handler(Looper.getMainLooper());
    long l2 = l1 / 1000L;
    this.d = ((l1 - l2 * 1000L >> 32) / 1000L & l2 << 32);
    this.a = "127.0.0.1";
  }
  
  private void a(final int paramInt1, final int paramInt2, final Exception paramException)
  {
    this.h.post(new Runnable()
    {
      public void run()
      {
        if (b.a(b.this) != null) {
          b.a(b.this).a(paramInt1, paramInt2, paramException);
        }
      }
    });
  }
  
  private void a(final long paramLong)
  {
    this.h.post(new Runnable()
    {
      public void run()
      {
        if (b.a(b.this) != null) {
          b.a(b.this).a(paramLong);
        }
      }
    });
  }
  
  private void d(int paramInt)
  {
    Object localObject;
    if (paramInt == 0) {
      localObject = this.e;
    } else {
      localObject = this.f;
    }
    if (localObject != null) {
      ((d)localObject).f();
    }
  }
  
  private void m()
  {
    this.h.post(new Runnable()
    {
      public void run()
      {
        if (b.a(b.this) != null) {
          b.a(b.this).a();
        }
      }
    });
  }
  
  private void n()
  {
    this.h.post(new Runnable()
    {
      public void run()
      {
        if (b.a(b.this) != null) {
          b.a(b.this).b();
        }
      }
    });
  }
  
  private void o()
  {
    this.h.post(new Runnable()
    {
      public void run()
      {
        if (b.a(b.this) != null) {
          b.a(b.this).c();
        }
      }
    });
  }
  
  void a()
  {
    com.app.system.streaming.a.d locald = this.e;
    if (locald != null)
    {
      locald.f();
      this.e = null;
    }
  }
  
  public void a(int paramInt)
  {
    this.c = paramInt;
  }
  
  void a(com.app.system.streaming.a.d paramd)
  {
    a();
    this.e = paramd;
  }
  
  public void a(a parama)
  {
    this.g = parama;
  }
  
  void a(com.app.system.streaming.h.d paramd)
  {
    b();
    this.f = paramd;
  }
  
  public void a(String paramString)
  {
    this.a = paramString;
  }
  
  void b()
  {
    com.app.system.streaming.h.d locald = this.f;
    if (locald != null)
    {
      locald.m();
      this.f = null;
    }
  }
  
  public void b(int paramInt)
  {
    Object localObject;
    if (paramInt == 0) {
      localObject = this.e;
    } else {
      localObject = this.f;
    }
    if ((localObject != null) && (!((d)localObject).c())) {
      try
      {
        InetAddress localInetAddress = InetAddress.getByName(this.b);
        ((d)localObject).b(this.c);
        ((d)localObject).a(localInetAddress);
        ((d)localObject).e();
        int k = 1 - paramInt;
        if ((c(k) == null) || (c(k).c())) {
          n();
        }
        if ((c(k) == null) || (!c(k).c())) {
          this.i.post(this.j);
        }
      }
      catch (RuntimeException localRuntimeException)
      {
        a(6, paramInt, localRuntimeException);
        throw localRuntimeException;
      }
      catch (IOException localIOException)
      {
        a(6, paramInt, localIOException);
        throw localIOException;
      }
      catch (InvalidSurfaceException localInvalidSurfaceException)
      {
        a(4, paramInt, localInvalidSurfaceException);
        throw localInvalidSurfaceException;
      }
      catch (ConfNotSupportedException localConfNotSupportedException)
      {
        a(1, paramInt, localConfNotSupportedException);
        throw localConfNotSupportedException;
      }
      catch (StorageUnavailableException localStorageUnavailableException)
      {
        a(2, paramInt, localStorageUnavailableException);
        throw localStorageUnavailableException;
      }
      catch (CameraInUseException localCameraInUseException)
      {
        a(0, paramInt, localCameraInUseException);
        throw localCameraInUseException;
      }
      catch (UnknownHostException localUnknownHostException)
      {
        a(5, paramInt, localUnknownHostException);
        throw localUnknownHostException;
      }
    }
  }
  
  public void b(String paramString)
  {
    this.b = paramString;
  }
  
  public com.app.system.streaming.a.d c()
  {
    return this.e;
  }
  
  public d c(int paramInt)
  {
    if (paramInt == 0) {
      return this.e;
    }
    return this.f;
  }
  
  public com.app.system.streaming.h.d d()
  {
    return this.f;
  }
  
  public String e()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (this.b != null)
    {
      localStringBuilder.append("v=0\r\n");
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("o=- ");
      ((StringBuilder)localObject).append(this.d);
      ((StringBuilder)localObject).append(" ");
      ((StringBuilder)localObject).append(this.d);
      ((StringBuilder)localObject).append(" IN IP4 ");
      ((StringBuilder)localObject).append(this.a);
      ((StringBuilder)localObject).append("\r\n");
      localStringBuilder.append(((StringBuilder)localObject).toString());
      localStringBuilder.append("s=Unnamed\r\n");
      localStringBuilder.append("i=N/A\r\n");
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("c=IN IP4 ");
      ((StringBuilder)localObject).append(this.b);
      ((StringBuilder)localObject).append("\r\n");
      localStringBuilder.append(((StringBuilder)localObject).toString());
      localStringBuilder.append("t=0 0\r\n");
      localStringBuilder.append("a=recvonly\r\n");
      localObject = this.e;
      if (localObject != null)
      {
        localStringBuilder.append(((com.app.system.streaming.a.d)localObject).i());
        localStringBuilder.append("a=control:trackID=0\r\n");
      }
      localObject = this.f;
      if (localObject != null)
      {
        localStringBuilder.append(((com.app.system.streaming.h.d)localObject).i());
        localStringBuilder.append("a=control:trackID=1\r\n");
      }
      return localStringBuilder.toString();
    }
    throw new IllegalStateException("setDestination() has not been called !");
  }
  
  public long f()
  {
    Object localObject = this.e;
    long l1 = 0L;
    if (localObject != null) {
      l1 = 0L + ((com.app.system.streaming.a.d)localObject).b();
    }
    localObject = this.f;
    long l2 = l1;
    if (localObject != null) {
      l2 = l1 + ((com.app.system.streaming.h.d)localObject).b();
    }
    return l2;
  }
  
  public boolean g()
  {
    Object localObject = this.e;
    if ((localObject == null) || (!((com.app.system.streaming.a.d)localObject).c()))
    {
      localObject = this.f;
      if ((localObject == null) || (!((com.app.system.streaming.h.d)localObject).c())) {}
    }
    else
    {
      return true;
    }
    boolean bool = false;
    return bool;
  }
  
  public void h()
  {
    for (int k = 0; k < 2; k++)
    {
      Object localObject;
      if (k == 0) {
        localObject = this.e;
      } else {
        localObject = this.f;
      }
      if ((localObject != null) && (!((d)localObject).c())) {
        try
        {
          ((d)localObject).d();
        }
        catch (RuntimeException localRuntimeException)
        {
          a(6, k, localRuntimeException);
          throw localRuntimeException;
        }
        catch (IOException localIOException)
        {
          a(6, k, localIOException);
          throw localIOException;
        }
        catch (InvalidSurfaceException localInvalidSurfaceException)
        {
          a(4, k, localInvalidSurfaceException);
          throw localInvalidSurfaceException;
        }
        catch (ConfNotSupportedException localConfNotSupportedException)
        {
          a(1, k, localConfNotSupportedException);
          throw localConfNotSupportedException;
        }
        catch (StorageUnavailableException localStorageUnavailableException)
        {
          a(2, k, localStorageUnavailableException);
          throw localStorageUnavailableException;
        }
        catch (CameraInUseException localCameraInUseException)
        {
          a(0, k, localCameraInUseException);
          throw localCameraInUseException;
        }
      }
    }
    m();
  }
  
  public void i()
  {
    this.i.post(new Runnable()
    {
      public void run()
      {
        try
        {
          b.this.j();
          return;
        }
        catch (Exception localException)
        {
          for (;;) {}
        }
      }
    });
  }
  
  public void j()
  {
    b(1);
    try
    {
      b(0);
      return;
    }
    catch (IOException localIOException)
    {
      d(1);
      throw localIOException;
    }
    catch (RuntimeException localRuntimeException)
    {
      d(1);
      throw localRuntimeException;
    }
  }
  
  public void k()
  {
    this.i.post(new Runnable()
    {
      public void run()
      {
        b.this.l();
      }
    });
  }
  
  public void l()
  {
    d(0);
    d(1);
    o();
  }
  
  public static abstract interface a
  {
    public abstract void a();
    
    public abstract void a(int paramInt1, int paramInt2, Exception paramException);
    
    public abstract void a(long paramLong);
    
    public abstract void b();
    
    public abstract void c();
  }
}


/* Location:              ~/com/app/system/streaming/b.class
 *
 * Reversed by:           J
 */