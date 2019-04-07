package com.app.system.streaming.b;

import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.os.Handler;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View.MeasureSpec;
import java.util.concurrent.Semaphore;

public class b
  extends SurfaceView
  implements SurfaceTexture.OnFrameAvailableListener, SurfaceHolder.Callback, Runnable
{
  private Thread a;
  private Handler b;
  private boolean c;
  private boolean d;
  private int e;
  private a f;
  private a g;
  private c h;
  private final Semaphore i;
  private final Object j;
  private a k;
  
  public void a()
  {
    synchronized (this.j)
    {
      if (this.g != null)
      {
        this.g.c();
        this.g = null;
      }
      return;
    }
  }
  
  public void a(double paramDouble)
  {
    if (this.k.a() != paramDouble)
    {
      this.k.a(paramDouble);
      this.b.post(new Runnable()
      {
        public void run()
        {
          if (b.a(b.this) == 1) {
            b.this.requestLayout();
          }
        }
      });
    }
  }
  
  public void a(Surface paramSurface)
  {
    synchronized (this.j)
    {
      a locala = new com/app/system/streaming/b/a;
      locala.<init>(paramSurface, this.f);
      this.g = locala;
      return;
    }
  }
  
  public void b()
  {
    com.security.d.a.d("SurfaceView", "Thread started.", new Object[0]);
    if (this.h == null) {
      this.h = new c();
    }
    if (this.h.a() == null)
    {
      this.a = new Thread(this);
      this.d = true;
      this.a.start();
      this.i.acquireUninterruptibly();
    }
  }
  
  public SurfaceTexture getSurfaceTexture()
  {
    return this.h.a();
  }
  
  public void onFrameAvailable(SurfaceTexture arg1)
  {
    synchronized (this.j)
    {
      this.c = true;
      this.j.notifyAll();
      return;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if ((this.k.a() > 0.0D) && (this.e == 1))
    {
      this.k.a(paramInt1, paramInt2);
      setMeasuredDimension(this.k.b(), this.k.c());
    }
    else
    {
      super.onMeasure(paramInt1, paramInt2);
    }
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: new 45	com/app/system/streaming/b/a
    //   4: dup
    //   5: aload_0
    //   6: invokevirtual 141	com/app/system/streaming/b/b:getHolder	()Landroid/view/SurfaceHolder;
    //   9: invokeinterface 147 1 0
    //   14: invokespecial 149	com/app/system/streaming/b/a:<init>	(Landroid/view/Surface;)V
    //   17: putfield 70	com/app/system/streaming/b/b:f	Lcom/app/system/streaming/b/a;
    //   20: aload_0
    //   21: getfield 70	com/app/system/streaming/b/b:f	Lcom/app/system/streaming/b/a;
    //   24: invokevirtual 151	com/app/system/streaming/b/a:a	()V
    //   27: aload_0
    //   28: getfield 86	com/app/system/streaming/b/b:h	Lcom/app/system/streaming/b/c;
    //   31: invokevirtual 153	com/app/system/streaming/b/c:d	()Landroid/graphics/SurfaceTexture;
    //   34: aload_0
    //   35: invokevirtual 159	android/graphics/SurfaceTexture:setOnFrameAvailableListener	(Landroid/graphics/SurfaceTexture$OnFrameAvailableListener;)V
    //   38: aload_0
    //   39: getfield 107	com/app/system/streaming/b/b:i	Ljava/util/concurrent/Semaphore;
    //   42: invokevirtual 162	java/util/concurrent/Semaphore:release	()V
    //   45: aload_0
    //   46: getfield 102	com/app/system/streaming/b/b:d	Z
    //   49: ifeq +152 -> 201
    //   52: aload_0
    //   53: getfield 41	com/app/system/streaming/b/b:j	Ljava/lang/Object;
    //   56: astore_1
    //   57: aload_1
    //   58: monitorenter
    //   59: aload_0
    //   60: getfield 41	com/app/system/streaming/b/b:j	Ljava/lang/Object;
    //   63: ldc2_w 163
    //   66: invokevirtual 168	java/lang/Object:wait	(J)V
    //   69: aload_0
    //   70: getfield 117	com/app/system/streaming/b/b:c	Z
    //   73: ifeq +86 -> 159
    //   76: aload_0
    //   77: iconst_0
    //   78: putfield 117	com/app/system/streaming/b/b:c	Z
    //   81: aload_0
    //   82: getfield 70	com/app/system/streaming/b/b:f	Lcom/app/system/streaming/b/a;
    //   85: invokevirtual 151	com/app/system/streaming/b/a:a	()V
    //   88: aload_0
    //   89: getfield 86	com/app/system/streaming/b/b:h	Lcom/app/system/streaming/b/c;
    //   92: invokevirtual 170	com/app/system/streaming/b/c:b	()V
    //   95: aload_0
    //   96: getfield 86	com/app/system/streaming/b/b:h	Lcom/app/system/streaming/b/c;
    //   99: invokevirtual 171	com/app/system/streaming/b/c:c	()V
    //   102: aload_0
    //   103: getfield 70	com/app/system/streaming/b/b:f	Lcom/app/system/streaming/b/a;
    //   106: invokevirtual 172	com/app/system/streaming/b/a:b	()V
    //   109: aload_0
    //   110: getfield 43	com/app/system/streaming/b/b:g	Lcom/app/system/streaming/b/a;
    //   113: ifnull +57 -> 170
    //   116: aload_0
    //   117: getfield 43	com/app/system/streaming/b/b:g	Lcom/app/system/streaming/b/a;
    //   120: invokevirtual 151	com/app/system/streaming/b/a:a	()V
    //   123: aload_0
    //   124: getfield 86	com/app/system/streaming/b/b:h	Lcom/app/system/streaming/b/c;
    //   127: invokevirtual 171	com/app/system/streaming/b/c:c	()V
    //   130: aload_0
    //   131: getfield 86	com/app/system/streaming/b/b:h	Lcom/app/system/streaming/b/c;
    //   134: invokevirtual 93	com/app/system/streaming/b/c:a	()Landroid/graphics/SurfaceTexture;
    //   137: invokevirtual 176	android/graphics/SurfaceTexture:getTimestamp	()J
    //   140: lstore_2
    //   141: aload_0
    //   142: getfield 43	com/app/system/streaming/b/b:g	Lcom/app/system/streaming/b/a;
    //   145: lload_2
    //   146: invokevirtual 178	com/app/system/streaming/b/a:a	(J)V
    //   149: aload_0
    //   150: getfield 43	com/app/system/streaming/b/b:g	Lcom/app/system/streaming/b/a;
    //   153: invokevirtual 172	com/app/system/streaming/b/a:b	()V
    //   156: goto +14 -> 170
    //   159: ldc 75
    //   161: ldc -76
    //   163: iconst_0
    //   164: anewarray 79	java/lang/Object
    //   167: invokestatic 182	com/security/d/a:a	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   170: aload_1
    //   171: monitorexit
    //   172: goto -127 -> 45
    //   175: astore 4
    //   177: aload_1
    //   178: monitorexit
    //   179: aload 4
    //   181: athrow
    //   182: astore 4
    //   184: aload_0
    //   185: getfield 70	com/app/system/streaming/b/b:f	Lcom/app/system/streaming/b/a;
    //   188: invokevirtual 47	com/app/system/streaming/b/a:c	()V
    //   191: aload_0
    //   192: getfield 86	com/app/system/streaming/b/b:h	Lcom/app/system/streaming/b/c;
    //   195: invokevirtual 184	com/app/system/streaming/b/c:e	()V
    //   198: aload 4
    //   200: athrow
    //   201: aload_0
    //   202: getfield 70	com/app/system/streaming/b/b:f	Lcom/app/system/streaming/b/a;
    //   205: invokevirtual 47	com/app/system/streaming/b/a:c	()V
    //   208: aload_0
    //   209: getfield 86	com/app/system/streaming/b/b:h	Lcom/app/system/streaming/b/c;
    //   212: invokevirtual 184	com/app/system/streaming/b/c:e	()V
    //   215: return
    //   216: astore 4
    //   218: goto -17 -> 201
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	221	0	this	b
    //   140	6	2	l	long
    //   175	5	4	localObject2	Object
    //   182	17	4	localObject3	Object
    //   216	1	4	localInterruptedException	InterruptedException
    // Exception table:
    //   from	to	target	type
    //   59	156	175	finally
    //   159	170	175	finally
    //   170	172	175	finally
    //   177	179	175	finally
    //   45	59	182	finally
    //   179	182	182	finally
    //   45	59	216	java/lang/InterruptedException
    //   179	182	216	java/lang/InterruptedException
  }
  
  public void setAspectRatioMode(int paramInt)
  {
    this.e = paramInt;
  }
  
  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3) {}
  
  public void surfaceCreated(SurfaceHolder paramSurfaceHolder) {}
  
  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    paramSurfaceHolder = this.a;
    if (paramSurfaceHolder != null) {
      paramSurfaceHolder.interrupt();
    }
    this.d = false;
  }
  
  public class a
  {
    private double a;
    private Integer b;
    private Integer c;
    
    public double a()
    {
      return this.a;
    }
    
    public void a(double paramDouble)
    {
      this.a = paramDouble;
    }
    
    public void a(int paramInt1, int paramInt2)
    {
      a(paramInt1, paramInt2, this.a);
    }
    
    public void a(int paramInt1, int paramInt2, double paramDouble)
    {
      int i = View.MeasureSpec.getMode(paramInt1);
      int j = Integer.MAX_VALUE;
      if (i == 0) {
        paramInt1 = Integer.MAX_VALUE;
      } else {
        paramInt1 = View.MeasureSpec.getSize(paramInt1);
      }
      int k = View.MeasureSpec.getMode(paramInt2);
      if (k == 0) {
        paramInt2 = j;
      } else {
        paramInt2 = View.MeasureSpec.getSize(paramInt2);
      }
      if ((k == 1073741824) && (i == 1073741824))
      {
        this.b = Integer.valueOf(paramInt1);
        this.c = Integer.valueOf(paramInt2);
      }
      else
      {
        double d1;
        double d2;
        if (k == 1073741824)
        {
          d1 = paramInt1;
          d2 = paramInt2;
          Double.isNaN(d2);
          this.b = Integer.valueOf((int)Math.min(d1, d2 * paramDouble));
          d1 = this.b.intValue();
          Double.isNaN(d1);
          this.c = Integer.valueOf((int)(d1 / paramDouble));
        }
        else if (i == 1073741824)
        {
          d1 = paramInt2;
          d2 = paramInt1;
          Double.isNaN(d2);
          this.c = Integer.valueOf((int)Math.min(d1, d2 / paramDouble));
          d1 = this.c.intValue();
          Double.isNaN(d1);
          this.b = Integer.valueOf((int)(d1 * paramDouble));
        }
        else
        {
          d2 = paramInt1;
          d1 = paramInt2;
          Double.isNaN(d1);
          if (d2 > d1 * paramDouble)
          {
            this.c = Integer.valueOf(paramInt2);
            d1 = this.c.intValue();
            Double.isNaN(d1);
            this.b = Integer.valueOf((int)(d1 * paramDouble));
          }
          else
          {
            this.b = Integer.valueOf(paramInt1);
            d1 = this.b.intValue();
            Double.isNaN(d1);
            this.c = Integer.valueOf((int)(d1 / paramDouble));
          }
        }
      }
    }
    
    public int b()
    {
      Integer localInteger = this.b;
      if (localInteger != null) {
        return localInteger.intValue();
      }
      throw new IllegalStateException("You need to run measure() before trying to get measured dimensions");
    }
    
    public int c()
    {
      Integer localInteger = this.c;
      if (localInteger != null) {
        return localInteger.intValue();
      }
      throw new IllegalStateException("You need to run measure() before trying to get measured dimensions");
    }
  }
}


/* Location:              ~/com/app/system/streaming/b/b.class
 *
 * Reversed by:           J
 */