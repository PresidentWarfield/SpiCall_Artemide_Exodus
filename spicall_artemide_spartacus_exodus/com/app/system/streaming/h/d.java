package com.app.system.streaming.h;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.ErrorCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import com.app.system.streaming.exceptions.CameraInUseException;
import com.app.system.streaming.exceptions.InvalidSurfaceException;
import com.app.system.streaming.f.g;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public abstract class d
  extends com.app.system.streaming.a
{
  protected int A = 0;
  protected int B = 0;
  protected Camera C;
  protected Thread D;
  protected Looper E;
  protected boolean F = true;
  protected boolean G = false;
  protected boolean H = false;
  protected boolean I = false;
  protected boolean J = false;
  protected boolean K = false;
  protected String L;
  protected int M;
  protected int N = 0;
  protected c t = c.a.a();
  protected c u = this.t.a();
  protected SurfaceHolder.Callback v = null;
  protected com.app.system.streaming.b.b w = null;
  protected SharedPreferences x = null;
  protected int y;
  protected int z = 0;
  
  @SuppressLint({"InlinedApi"})
  public d(int paramInt)
  {
    c(paramInt);
  }
  
  private void u()
  {
    final Semaphore localSemaphore = new Semaphore(0);
    final RuntimeException[] arrayOfRuntimeException = new RuntimeException[1];
    this.D = new Thread(new Runnable()
    {
      /* Error */
      public void run()
      {
        // Byte code:
        //   0: invokestatic 36	android/os/Looper:prepare	()V
        //   3: aload_0
        //   4: getfield 21	com/app/system/streaming/h/d$3:c	Lcom/app/system/streaming/h/d;
        //   7: invokestatic 40	android/os/Looper:myLooper	()Landroid/os/Looper;
        //   10: putfield 44	com/app/system/streaming/h/d:E	Landroid/os/Looper;
        //   13: aload_0
        //   14: getfield 21	com/app/system/streaming/h/d$3:c	Lcom/app/system/streaming/h/d;
        //   17: aload_0
        //   18: getfield 21	com/app/system/streaming/h/d$3:c	Lcom/app/system/streaming/h/d;
        //   21: getfield 48	com/app/system/streaming/h/d:z	I
        //   24: invokestatic 54	android/hardware/Camera:open	(I)Landroid/hardware/Camera;
        //   27: putfield 58	com/app/system/streaming/h/d:C	Landroid/hardware/Camera;
        //   30: goto +15 -> 45
        //   33: astore_1
        //   34: goto +22 -> 56
        //   37: astore_1
        //   38: aload_0
        //   39: getfield 23	com/app/system/streaming/h/d$3:a	[Ljava/lang/RuntimeException;
        //   42: iconst_0
        //   43: aload_1
        //   44: aastore
        //   45: aload_0
        //   46: getfield 25	com/app/system/streaming/h/d$3:b	Ljava/util/concurrent/Semaphore;
        //   49: invokevirtual 63	java/util/concurrent/Semaphore:release	()V
        //   52: invokestatic 66	android/os/Looper:loop	()V
        //   55: return
        //   56: aload_0
        //   57: getfield 25	com/app/system/streaming/h/d$3:b	Ljava/util/concurrent/Semaphore;
        //   60: invokevirtual 63	java/util/concurrent/Semaphore:release	()V
        //   63: invokestatic 66	android/os/Looper:loop	()V
        //   66: aload_1
        //   67: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	68	0	this	3
        //   33	1	1	localObject	Object
        //   37	30	1	localRuntimeException	RuntimeException
        // Exception table:
        //   from	to	target	type
        //   13	30	33	finally
        //   38	45	33	finally
        //   13	30	37	java/lang/RuntimeException
      }
    });
    this.D.start();
    localSemaphore.acquireUninterruptibly();
    if (arrayOfRuntimeException[0] == null) {
      return;
    }
    throw new CameraInUseException(arrayOfRuntimeException[0].getMessage());
  }
  
  private void v()
  {
    final Object localObject1 = new Semaphore(0);
    Object localObject2 = new Camera.PreviewCallback()
    {
      int a = 0;
      int b = 0;
      long c;
      long d;
      long e = 0L;
      
      public void onPreviewFrame(byte[] paramAnonymousArrayOfByte, Camera paramAnonymousCamera)
      {
        this.a += 1;
        this.c = (System.nanoTime() / 1000L);
        if (this.a > 3)
        {
          this.b = ((int)(this.b + (this.c - this.d)));
          this.e += 1L;
        }
        if (this.a > 20)
        {
          d.this.u.b = ((int)(1000000L / (this.b / this.e) + 1L));
          localObject1.release();
        }
        this.d = this.c;
      }
    };
    this.C.setPreviewCallback((Camera.PreviewCallback)localObject2);
    try
    {
      ((Semaphore)localObject1).tryAcquire(2L, TimeUnit.SECONDS);
      localObject1 = new java/lang/StringBuilder;
      ((StringBuilder)localObject1).<init>();
      ((StringBuilder)localObject1).append("Actual framerate: ");
      ((StringBuilder)localObject1).append(this.u.b);
      com.security.d.a.d("VideoStream", ((StringBuilder)localObject1).toString(), new Object[0]);
      if (this.x != null)
      {
        localObject1 = this.x.edit();
        localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        ((StringBuilder)localObject2).append("libstreaming-fps");
        ((StringBuilder)localObject2).append(this.t.b);
        ((StringBuilder)localObject2).append(",");
        ((StringBuilder)localObject2).append(this.M);
        ((StringBuilder)localObject2).append(",");
        ((StringBuilder)localObject2).append(this.t.d);
        ((StringBuilder)localObject2).append(this.t.e);
        ((SharedPreferences.Editor)localObject1).putInt(((StringBuilder)localObject2).toString(), this.u.b);
        ((SharedPreferences.Editor)localObject1).commit();
      }
      this.C.setPreviewCallback(null);
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;) {}
    }
  }
  
  public void a(SharedPreferences paramSharedPreferences)
  {
    this.x = paramSharedPreferences;
  }
  
  public void a(com.app.system.streaming.b.b paramb)
  {
    try
    {
      this.w = paramb;
      if ((this.v != null) && (this.w != null) && (this.w.getHolder() != null)) {
        this.w.getHolder().removeCallback(this.v);
      }
      if ((this.w != null) && (this.w.getHolder() != null))
      {
        paramb = new com/app/system/streaming/h/d$1;
        paramb.<init>(this);
        this.v = paramb;
        this.w.getHolder().addCallback(this.v);
        this.H = true;
      }
      return;
    }
    finally {}
  }
  
  public void a(c paramc)
  {
    if (!this.t.a(paramc))
    {
      this.t = paramc.a();
      this.K = false;
    }
  }
  
  /* Error */
  public void a(boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 139	com/app/system/streaming/h/d:C	Landroid/hardware/Camera;
    //   6: ifnull +148 -> 154
    //   9: aload_0
    //   10: getfield 239	com/app/system/streaming/h/d:f	Z
    //   13: ifeq +15 -> 28
    //   16: aload_0
    //   17: getfield 241	com/app/system/streaming/h/d:c	B
    //   20: iconst_1
    //   21: if_icmpne +7 -> 28
    //   24: aload_0
    //   25: invokevirtual 243	com/app/system/streaming/h/d:s	()V
    //   28: aload_0
    //   29: getfield 139	com/app/system/streaming/h/d:C	Landroid/hardware/Camera;
    //   32: invokevirtual 247	android/hardware/Camera:getParameters	()Landroid/hardware/Camera$Parameters;
    //   35: astore_2
    //   36: aload_2
    //   37: invokevirtual 252	android/hardware/Camera$Parameters:getFlashMode	()Ljava/lang/String;
    //   40: ifnull +101 -> 141
    //   43: iload_1
    //   44: ifeq +9 -> 53
    //   47: ldc -2
    //   49: astore_3
    //   50: goto +7 -> 57
    //   53: ldc_w 256
    //   56: astore_3
    //   57: aload_2
    //   58: aload_3
    //   59: invokevirtual 259	android/hardware/Camera$Parameters:setFlashMode	(Ljava/lang/String;)V
    //   62: aload_0
    //   63: getfield 139	com/app/system/streaming/h/d:C	Landroid/hardware/Camera;
    //   66: aload_2
    //   67: invokevirtual 263	android/hardware/Camera:setParameters	(Landroid/hardware/Camera$Parameters;)V
    //   70: aload_0
    //   71: iload_1
    //   72: putfield 80	com/app/system/streaming/h/d:G	Z
    //   75: aload_0
    //   76: getfield 239	com/app/system/streaming/h/d:f	Z
    //   79: ifeq +80 -> 159
    //   82: aload_0
    //   83: getfield 241	com/app/system/streaming/h/d:c	B
    //   86: iconst_1
    //   87: if_icmpne +72 -> 159
    //   90: aload_0
    //   91: invokevirtual 265	com/app/system/streaming/h/d:t	()V
    //   94: goto +65 -> 159
    //   97: astore_3
    //   98: goto +22 -> 120
    //   101: astore_3
    //   102: aload_0
    //   103: iconst_0
    //   104: putfield 80	com/app/system/streaming/h/d:G	Z
    //   107: new 107	java/lang/RuntimeException
    //   110: astore_3
    //   111: aload_3
    //   112: ldc_w 267
    //   115: invokespecial 268	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   118: aload_3
    //   119: athrow
    //   120: aload_0
    //   121: getfield 239	com/app/system/streaming/h/d:f	Z
    //   124: ifeq +15 -> 139
    //   127: aload_0
    //   128: getfield 241	com/app/system/streaming/h/d:c	B
    //   131: iconst_1
    //   132: if_icmpne +7 -> 139
    //   135: aload_0
    //   136: invokevirtual 265	com/app/system/streaming/h/d:t	()V
    //   139: aload_3
    //   140: athrow
    //   141: new 107	java/lang/RuntimeException
    //   144: astore_3
    //   145: aload_3
    //   146: ldc_w 267
    //   149: invokespecial 268	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   152: aload_3
    //   153: athrow
    //   154: aload_0
    //   155: iload_1
    //   156: putfield 80	com/app/system/streaming/h/d:G	Z
    //   159: aload_0
    //   160: monitorexit
    //   161: return
    //   162: astore_3
    //   163: aload_0
    //   164: monitorexit
    //   165: aload_3
    //   166: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	167	0	this	d
    //   0	167	1	paramBoolean	boolean
    //   35	32	2	localParameters	Camera.Parameters
    //   49	10	3	str	String
    //   97	1	3	localObject1	Object
    //   101	1	3	localRuntimeException1	RuntimeException
    //   110	43	3	localRuntimeException2	RuntimeException
    //   162	4	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   62	75	97	finally
    //   102	120	97	finally
    //   62	75	101	java/lang/RuntimeException
    //   2	28	162	finally
    //   28	43	162	finally
    //   57	62	162	finally
    //   75	94	162	finally
    //   120	139	162	finally
    //   139	141	162	finally
    //   141	154	162	finally
    //   154	159	162	finally
  }
  
  public void c(int paramInt)
  {
    Camera.CameraInfo localCameraInfo = new Camera.CameraInfo();
    int i = Camera.getNumberOfCameras();
    for (int j = 0; j < i; j++)
    {
      Camera.getCameraInfo(j, localCameraInfo);
      if (localCameraInfo.facing == paramInt)
      {
        this.z = j;
        break;
      }
    }
  }
  
  public void d()
  {
    try
    {
      super.d();
      this.B = this.A;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void d(int paramInt)
  {
    this.A = paramInt;
    this.K = false;
  }
  
  public void e()
  {
    try
    {
      if (!this.J) {
        this.F = false;
      }
      super.e();
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("Stream configuration: FPS: ");
      localStringBuilder.append(this.u.b);
      localStringBuilder.append(" Width: ");
      localStringBuilder.append(this.u.d);
      localStringBuilder.append(" Height: ");
      localStringBuilder.append(this.u.e);
      com.security.d.a.d("VideoStream", localStringBuilder.toString(), new Object[0]);
      return;
    }
    finally {}
  }
  
  public void f()
  {
    try
    {
      if (this.C != null)
      {
        if (this.c == 2) {
          this.C.setPreviewCallbackWithBuffer(null);
        }
        if (this.c == 5) {
          this.w.a();
        }
        super.f();
        if (!this.F) {
          q();
        } else {
          try
          {
            l();
          }
          catch (RuntimeException localRuntimeException)
          {
            localRuntimeException.printStackTrace();
          }
        }
      }
      return;
    }
    finally {}
  }
  
  /* Error */
  protected void g()
  {
    // Byte code:
    //   0: ldc -83
    //   2: ldc_w 315
    //   5: iconst_0
    //   6: anewarray 178	java/lang/Object
    //   9: invokestatic 184	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   12: aload_0
    //   13: invokevirtual 318	com/app/system/streaming/h/d:j	()V
    //   16: aload_0
    //   17: invokevirtual 302	com/app/system/streaming/h/d:q	()V
    //   20: aload_0
    //   21: invokevirtual 321	com/app/system/streaming/h/d:p	()V
    //   24: aload_0
    //   25: invokevirtual 265	com/app/system/streaming/h/d:t	()V
    //   28: new 323	android/media/MediaRecorder
    //   31: astore_1
    //   32: aload_1
    //   33: invokespecial 324	android/media/MediaRecorder:<init>	()V
    //   36: aload_0
    //   37: aload_1
    //   38: putfield 328	com/app/system/streaming/h/d:r	Landroid/media/MediaRecorder;
    //   41: aload_0
    //   42: getfield 328	com/app/system/streaming/h/d:r	Landroid/media/MediaRecorder;
    //   45: aload_0
    //   46: getfield 139	com/app/system/streaming/h/d:C	Landroid/hardware/Camera;
    //   49: invokevirtual 332	android/media/MediaRecorder:setCamera	(Landroid/hardware/Camera;)V
    //   52: aload_0
    //   53: getfield 328	com/app/system/streaming/h/d:r	Landroid/media/MediaRecorder;
    //   56: iconst_1
    //   57: invokevirtual 335	android/media/MediaRecorder:setVideoSource	(I)V
    //   60: aload_0
    //   61: getfield 328	com/app/system/streaming/h/d:r	Landroid/media/MediaRecorder;
    //   64: iconst_1
    //   65: invokevirtual 338	android/media/MediaRecorder:setOutputFormat	(I)V
    //   68: aload_0
    //   69: getfield 328	com/app/system/streaming/h/d:r	Landroid/media/MediaRecorder;
    //   72: aload_0
    //   73: getfield 340	com/app/system/streaming/h/d:y	I
    //   76: invokevirtual 343	android/media/MediaRecorder:setVideoEncoder	(I)V
    //   79: aload_0
    //   80: getfield 328	com/app/system/streaming/h/d:r	Landroid/media/MediaRecorder;
    //   83: aload_0
    //   84: getfield 68	com/app/system/streaming/h/d:w	Lcom/app/system/streaming/b/b;
    //   87: invokevirtual 219	com/app/system/streaming/b/b:getHolder	()Landroid/view/SurfaceHolder;
    //   90: invokeinterface 347 1 0
    //   95: invokevirtual 351	android/media/MediaRecorder:setPreviewDisplay	(Landroid/view/Surface;)V
    //   98: aload_0
    //   99: getfield 328	com/app/system/streaming/h/d:r	Landroid/media/MediaRecorder;
    //   102: aload_0
    //   103: getfield 62	com/app/system/streaming/h/d:t	Lcom/app/system/streaming/h/c;
    //   106: getfield 198	com/app/system/streaming/h/c:d	I
    //   109: aload_0
    //   110: getfield 62	com/app/system/streaming/h/d:t	Lcom/app/system/streaming/h/c;
    //   113: getfield 201	com/app/system/streaming/h/c:e	I
    //   116: invokevirtual 355	android/media/MediaRecorder:setVideoSize	(II)V
    //   119: aload_0
    //   120: getfield 328	com/app/system/streaming/h/d:r	Landroid/media/MediaRecorder;
    //   123: aload_0
    //   124: getfield 62	com/app/system/streaming/h/d:t	Lcom/app/system/streaming/h/c;
    //   127: getfield 168	com/app/system/streaming/h/c:b	I
    //   130: invokevirtual 358	android/media/MediaRecorder:setVideoFrameRate	(I)V
    //   133: aload_0
    //   134: getfield 328	com/app/system/streaming/h/d:r	Landroid/media/MediaRecorder;
    //   137: astore_1
    //   138: aload_0
    //   139: getfield 62	com/app/system/streaming/h/d:t	Lcom/app/system/streaming/h/c;
    //   142: getfield 360	com/app/system/streaming/h/c:c	I
    //   145: istore_2
    //   146: iload_2
    //   147: i2d
    //   148: dstore_3
    //   149: dload_3
    //   150: invokestatic 366	java/lang/Double:isNaN	(D)Z
    //   153: pop
    //   154: dload_3
    //   155: ldc2_w 367
    //   158: dmul
    //   159: d2i
    //   160: istore_2
    //   161: aload_1
    //   162: iload_2
    //   163: invokevirtual 371	android/media/MediaRecorder:setVideoEncodingBitRate	(I)V
    //   166: getstatic 373	com/app/system/streaming/h/d:e	B
    //   169: iconst_2
    //   170: if_icmpne +14 -> 184
    //   173: aload_0
    //   174: getfield 377	com/app/system/streaming/h/d:o	Landroid/os/ParcelFileDescriptor;
    //   177: invokevirtual 383	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
    //   180: astore_1
    //   181: goto +11 -> 192
    //   184: aload_0
    //   185: getfield 386	com/app/system/streaming/h/d:q	Landroid/net/LocalSocket;
    //   188: invokevirtual 389	android/net/LocalSocket:getFileDescriptor	()Ljava/io/FileDescriptor;
    //   191: astore_1
    //   192: aload_0
    //   193: getfield 328	com/app/system/streaming/h/d:r	Landroid/media/MediaRecorder;
    //   196: aload_1
    //   197: invokevirtual 393	android/media/MediaRecorder:setOutputFile	(Ljava/io/FileDescriptor;)V
    //   200: aload_0
    //   201: getfield 328	com/app/system/streaming/h/d:r	Landroid/media/MediaRecorder;
    //   204: invokevirtual 396	android/media/MediaRecorder:prepare	()V
    //   207: aload_0
    //   208: getfield 328	com/app/system/streaming/h/d:r	Landroid/media/MediaRecorder;
    //   211: invokevirtual 397	android/media/MediaRecorder:start	()V
    //   214: getstatic 373	com/app/system/streaming/h/d:e	B
    //   217: iconst_2
    //   218: if_icmpne +18 -> 236
    //   221: new 399	android/os/ParcelFileDescriptor$AutoCloseInputStream
    //   224: dup
    //   225: aload_0
    //   226: getfield 402	com/app/system/streaming/h/d:n	Landroid/os/ParcelFileDescriptor;
    //   229: invokespecial 405	android/os/ParcelFileDescriptor$AutoCloseInputStream:<init>	(Landroid/os/ParcelFileDescriptor;)V
    //   232: astore_1
    //   233: goto +11 -> 244
    //   236: aload_0
    //   237: getfield 407	com/app/system/streaming/h/d:p	Landroid/net/LocalSocket;
    //   240: invokevirtual 411	android/net/LocalSocket:getInputStream	()Ljava/io/InputStream;
    //   243: astore_1
    //   244: iconst_4
    //   245: newarray <illegal type>
    //   247: astore 5
    //   249: invokestatic 414	java/lang/Thread:interrupted	()Z
    //   252: ifne +53 -> 305
    //   255: aload_1
    //   256: invokevirtual 419	java/io/InputStream:read	()I
    //   259: bipush 109
    //   261: if_icmpeq +6 -> 267
    //   264: goto -9 -> 255
    //   267: aload_1
    //   268: aload 5
    //   270: iconst_0
    //   271: iconst_3
    //   272: invokevirtual 422	java/io/InputStream:read	([BII)I
    //   275: pop
    //   276: aload 5
    //   278: iconst_0
    //   279: baload
    //   280: bipush 100
    //   282: if_icmpne -33 -> 249
    //   285: aload 5
    //   287: iconst_1
    //   288: baload
    //   289: bipush 97
    //   291: if_icmpne -42 -> 249
    //   294: aload 5
    //   296: iconst_2
    //   297: baload
    //   298: istore_2
    //   299: iload_2
    //   300: bipush 116
    //   302: if_icmpne -53 -> 249
    //   305: aload_0
    //   306: getfield 425	com/app/system/streaming/h/d:a	Lcom/app/system/streaming/f/d;
    //   309: aload_1
    //   310: invokevirtual 430	com/app/system/streaming/f/d:a	(Ljava/io/InputStream;)V
    //   313: aload_0
    //   314: getfield 425	com/app/system/streaming/h/d:a	Lcom/app/system/streaming/f/d;
    //   317: invokevirtual 431	com/app/system/streaming/f/d:a	()V
    //   320: aload_0
    //   321: iconst_1
    //   322: putfield 239	com/app/system/streaming/h/d:f	Z
    //   325: return
    //   326: astore_1
    //   327: ldc -83
    //   329: ldc_w 433
    //   332: iconst_0
    //   333: anewarray 178	java/lang/Object
    //   336: invokestatic 435	com/security/d/a:a	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   339: aload_0
    //   340: invokevirtual 436	com/app/system/streaming/h/d:f	()V
    //   343: aload_1
    //   344: athrow
    //   345: astore_1
    //   346: new 438	com/app/system/streaming/exceptions/ConfNotSupportedException
    //   349: dup
    //   350: aload_1
    //   351: invokevirtual 439	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   354: invokespecial 440	com/app/system/streaming/exceptions/ConfNotSupportedException:<init>	(Ljava/lang/String;)V
    //   357: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	358	0	this	d
    //   31	279	1	localObject	Object
    //   326	18	1	localIOException	IOException
    //   345	6	1	localException	Exception
    //   145	158	2	i	int
    //   148	7	3	d	double
    //   247	48	5	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   244	249	326	java/io/IOException
    //   249	255	326	java/io/IOException
    //   255	264	326	java/io/IOException
    //   267	276	326	java/io/IOException
    //   28	146	345	java/lang/Exception
    //   161	181	345	java/lang/Exception
    //   184	192	345	java/lang/Exception
    //   192	214	345	java/lang/Exception
  }
  
  protected void h()
  {
    if (this.c == 5) {
      o();
    } else {
      n();
    }
  }
  
  public abstract String i();
  
  public void l()
  {
    try
    {
      this.F = true;
      if (!this.J)
      {
        p();
        r();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void m()
  {
    try
    {
      this.F = false;
      f();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  @SuppressLint({"NewApi"})
  protected void n()
  {
    int i = 0;
    com.security.d.a.d("VideoStream", "Video encoded using the MediaCodec API with a buffer", new Object[0]);
    p();
    r();
    v();
    if (!this.J) {
      try
      {
        this.C.startPreview();
        this.J = true;
      }
      catch (RuntimeException localRuntimeException)
      {
        q();
        throw localRuntimeException;
      }
    }
    Object localObject = com.app.system.streaming.c.b.a(this.x, this.u.d, this.u.e);
    final com.app.system.streaming.c.c localc = ((com.app.system.streaming.c.b)localObject).e();
    this.s = MediaCodec.createByCodecName(((com.app.system.streaming.c.b)localObject).c());
    MediaFormat localMediaFormat = MediaFormat.createVideoFormat("video/avc", this.u.d, this.u.e);
    localMediaFormat.setInteger("bitrate", this.u.c);
    localMediaFormat.setInteger("frame-rate", this.u.b);
    localMediaFormat.setInteger("color-format", ((com.app.system.streaming.c.b)localObject).d());
    localMediaFormat.setInteger("i-frame-interval", 1);
    this.s.configure(localMediaFormat, null, null, 1);
    this.s.start();
    localObject = new Camera.PreviewCallback()
    {
      long a = System.nanoTime() / 1000L;
      long b = this.a;
      long c = 0L;
      ByteBuffer[] d = d.a(d.this).getInputBuffers();
      
      public void onPreviewFrame(byte[] paramAnonymousArrayOfByte, Camera paramAnonymousCamera)
      {
        this.b = this.a;
        this.a = (System.nanoTime() / 1000L);
        long l = this.c;
        this.c = (1L + l);
        if (l > 3L) {
          this.c = 0L;
        }
        try
        {
          int i = d.b(d.this).dequeueInputBuffer(500000L);
          if (i >= 0)
          {
            this.d[i].clear();
            if (paramAnonymousArrayOfByte == null) {
              com.security.d.a.a("VideoStream", "Symptom of the \"Callback buffer was to small\" problem...", new Object[0]);
            } else {
              localc.a(paramAnonymousArrayOfByte, this.d[i]);
            }
            d.c(d.this).queueInputBuffer(i, 0, this.d[i].position(), this.a, 0);
          }
          else
          {
            com.security.d.a.a("VideoStream", "No buffer available !", new Object[0]);
          }
          return;
        }
        finally
        {
          d.this.C.addCallbackBuffer(paramAnonymousArrayOfByte);
        }
      }
    };
    while (i < 10)
    {
      this.C.addCallbackBuffer(new byte[localc.a()]);
      i++;
    }
    this.C.setPreviewCallbackWithBuffer((Camera.PreviewCallback)localObject);
    this.a.a(new g(this.s));
    this.a.a();
    this.f = true;
  }
  
  @SuppressLint({"InlinedApi", "NewApi"})
  protected void o()
  {
    com.security.d.a.d("VideoStream", "Video encoded using the MediaCodec API with a surface", new Object[0]);
    p();
    r();
    v();
    this.s = MediaCodec.createByCodecName(com.app.system.streaming.c.b.a(this.x, this.u.d, this.u.e).c());
    Object localObject = MediaFormat.createVideoFormat("video/avc", this.u.d, this.u.e);
    ((MediaFormat)localObject).setInteger("bitrate", this.u.c);
    ((MediaFormat)localObject).setInteger("frame-rate", this.u.b);
    ((MediaFormat)localObject).setInteger("color-format", 2130708361);
    ((MediaFormat)localObject).setInteger("i-frame-interval", 1);
    this.s.configure((MediaFormat)localObject, null, null, 1);
    localObject = this.s.createInputSurface();
    this.w.a((Surface)localObject);
    this.s.start();
    this.a.a(new g(this.s));
    this.a.a();
    this.f = true;
  }
  
  protected void p()
  {
    try
    {
      if (this.w != null)
      {
        if ((this.w.getHolder() != null) && (this.H))
        {
          if (this.C == null)
          {
            u();
            this.K = false;
            this.I = false;
            Object localObject1 = this.C;
            Object localObject3 = new com/app/system/streaming/h/d$4;
            ((4)localObject3).<init>(this);
            ((Camera)localObject1).setErrorCallback((Camera.ErrorCallback)localObject3);
            try
            {
              localObject3 = this.C.getParameters();
              if (((Camera.Parameters)localObject3).getFlashMode() != null)
              {
                if (this.G) {
                  localObject1 = "torch";
                } else {
                  localObject1 = "off";
                }
                ((Camera.Parameters)localObject3).setFlashMode((String)localObject1);
              }
              ((Camera.Parameters)localObject3).setRecordingHint(true);
              this.C.setParameters((Camera.Parameters)localObject3);
              this.C.setDisplayOrientation(this.B);
              try
              {
                if (this.c == 5)
                {
                  this.w.b();
                  this.C.setPreviewTexture(this.w.getSurfaceTexture());
                }
                else
                {
                  this.C.setPreviewDisplay(this.w.getHolder());
                }
              }
              catch (IOException localIOException)
              {
                InvalidSurfaceException localInvalidSurfaceException1 = new com/app/system/streaming/exceptions/InvalidSurfaceException;
                localInvalidSurfaceException1.<init>("Invalid surface !");
                throw localInvalidSurfaceException1;
              }
            }
            catch (RuntimeException localRuntimeException)
            {
              q();
              throw localRuntimeException;
            }
          }
          return;
        }
        localInvalidSurfaceException2 = new com/app/system/streaming/exceptions/InvalidSurfaceException;
        localInvalidSurfaceException2.<init>("Invalid surface !");
        throw localInvalidSurfaceException2;
      }
      InvalidSurfaceException localInvalidSurfaceException2 = new com/app/system/streaming/exceptions/InvalidSurfaceException;
      localInvalidSurfaceException2.<init>("Invalid surface !");
      throw localInvalidSurfaceException2;
    }
    finally {}
  }
  
  protected void q()
  {
    try
    {
      if (this.C != null)
      {
        if (this.f) {
          super.f();
        }
        s();
        this.C.stopPreview();
        try
        {
          this.C.release();
        }
        catch (Exception localException)
        {
          String str;
          if (localException.getMessage() != null) {
            str = localException.getMessage();
          } else {
            str = "unknown error";
          }
          com.security.d.a.a("VideoStream", str, new Object[0]);
        }
        this.C = null;
        this.E.quit();
        this.I = false;
        this.J = false;
      }
      return;
    }
    finally {}
  }
  
  /* Error */
  protected void r()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 88	com/app/system/streaming/h/d:K	Z
    //   6: istore_1
    //   7: iload_1
    //   8: ifeq +6 -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_0
    //   15: getfield 86	com/app/system/streaming/h/d:J	Z
    //   18: ifeq +15 -> 33
    //   21: aload_0
    //   22: iconst_0
    //   23: putfield 86	com/app/system/streaming/h/d:J	Z
    //   26: aload_0
    //   27: getfield 139	com/app/system/streaming/h/d:C	Landroid/hardware/Camera;
    //   30: invokevirtual 558	android/hardware/Camera:stopPreview	()V
    //   33: aload_0
    //   34: getfield 139	com/app/system/streaming/h/d:C	Landroid/hardware/Camera;
    //   37: invokevirtual 247	android/hardware/Camera:getParameters	()Landroid/hardware/Camera$Parameters;
    //   40: astore_2
    //   41: aload_0
    //   42: aload_2
    //   43: aload_0
    //   44: getfield 64	com/app/system/streaming/h/d:u	Lcom/app/system/streaming/h/c;
    //   47: invokestatic 573	com/app/system/streaming/h/c:a	(Landroid/hardware/Camera$Parameters;Lcom/app/system/streaming/h/c;)Lcom/app/system/streaming/h/c;
    //   50: putfield 64	com/app/system/streaming/h/d:u	Lcom/app/system/streaming/h/c;
    //   53: aload_2
    //   54: invokestatic 576	com/app/system/streaming/h/c:a	(Landroid/hardware/Camera$Parameters;)[I
    //   57: astore_3
    //   58: aload_0
    //   59: getfield 64	com/app/system/streaming/h/d:u	Lcom/app/system/streaming/h/c;
    //   62: getfield 198	com/app/system/streaming/h/c:d	I
    //   65: i2d
    //   66: dstore 4
    //   68: aload_0
    //   69: getfield 64	com/app/system/streaming/h/d:u	Lcom/app/system/streaming/h/c;
    //   72: getfield 201	com/app/system/streaming/h/c:e	I
    //   75: istore 6
    //   77: iload 6
    //   79: i2d
    //   80: dstore 7
    //   82: dload 4
    //   84: invokestatic 366	java/lang/Double:isNaN	(D)Z
    //   87: pop
    //   88: dload 7
    //   90: invokestatic 366	java/lang/Double:isNaN	(D)Z
    //   93: pop
    //   94: dload 4
    //   96: dload 7
    //   98: ddiv
    //   99: dstore 4
    //   101: aload_0
    //   102: getfield 68	com/app/system/streaming/h/d:w	Lcom/app/system/streaming/b/b;
    //   105: dload 4
    //   107: invokevirtual 579	com/app/system/streaming/b/b:a	(D)V
    //   110: aload_2
    //   111: aload_0
    //   112: getfield 196	com/app/system/streaming/h/d:M	I
    //   115: invokevirtual 582	android/hardware/Camera$Parameters:setPreviewFormat	(I)V
    //   118: aload_2
    //   119: aload_0
    //   120: getfield 64	com/app/system/streaming/h/d:u	Lcom/app/system/streaming/h/c;
    //   123: getfield 198	com/app/system/streaming/h/c:d	I
    //   126: aload_0
    //   127: getfield 64	com/app/system/streaming/h/d:u	Lcom/app/system/streaming/h/c;
    //   130: getfield 201	com/app/system/streaming/h/c:e	I
    //   133: invokevirtual 585	android/hardware/Camera$Parameters:setPreviewSize	(II)V
    //   136: aload_2
    //   137: aload_3
    //   138: iconst_0
    //   139: iaload
    //   140: aload_3
    //   141: iconst_1
    //   142: iaload
    //   143: invokevirtual 588	android/hardware/Camera$Parameters:setPreviewFpsRange	(II)V
    //   146: aload_0
    //   147: getfield 139	com/app/system/streaming/h/d:C	Landroid/hardware/Camera;
    //   150: aload_2
    //   151: invokevirtual 263	android/hardware/Camera:setParameters	(Landroid/hardware/Camera$Parameters;)V
    //   154: aload_0
    //   155: getfield 139	com/app/system/streaming/h/d:C	Landroid/hardware/Camera;
    //   158: aload_0
    //   159: getfield 76	com/app/system/streaming/h/d:B	I
    //   162: invokevirtual 537	android/hardware/Camera:setDisplayOrientation	(I)V
    //   165: aload_0
    //   166: getfield 139	com/app/system/streaming/h/d:C	Landroid/hardware/Camera;
    //   169: invokevirtual 457	android/hardware/Camera:startPreview	()V
    //   172: aload_0
    //   173: iconst_1
    //   174: putfield 86	com/app/system/streaming/h/d:J	Z
    //   177: aload_0
    //   178: iconst_1
    //   179: putfield 88	com/app/system/streaming/h/d:K	Z
    //   182: aload_0
    //   183: monitorexit
    //   184: return
    //   185: astore_3
    //   186: aload_0
    //   187: invokevirtual 302	com/app/system/streaming/h/d:q	()V
    //   190: aload_3
    //   191: athrow
    //   192: astore_3
    //   193: aload_0
    //   194: monitorexit
    //   195: aload_3
    //   196: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	197	0	this	d
    //   6	2	1	bool	boolean
    //   40	111	2	localParameters	Camera.Parameters
    //   57	84	3	arrayOfInt	int[]
    //   185	6	3	localRuntimeException	RuntimeException
    //   192	4	3	localObject	Object
    //   66	40	4	d1	double
    //   75	3	6	i	int
    //   80	17	7	d2	double
    // Exception table:
    //   from	to	target	type
    //   146	182	185	java/lang/RuntimeException
    //   2	7	192	finally
    //   14	33	192	finally
    //   33	77	192	finally
    //   101	146	192	finally
    //   146	182	192	finally
    //   186	192	192	finally
  }
  
  protected void s()
  {
    if (this.I)
    {
      com.security.d.a.d("VideoStream", "Locking camera", new Object[0]);
      try
      {
        this.C.reconnect();
      }
      catch (Exception localException)
      {
        com.security.d.a.a("VideoStream", localException.getMessage(), new Object[0]);
      }
      this.I = false;
    }
  }
  
  protected void t()
  {
    if (!this.I)
    {
      com.security.d.a.d("VideoStream", "Unlocking camera", new Object[0]);
      try
      {
        this.C.unlock();
      }
      catch (Exception localException)
      {
        com.security.d.a.a("VideoStream", localException.getMessage(), new Object[0]);
      }
      this.I = true;
    }
  }
}


/* Location:              ~/com/app/system/streaming/h/d.class
 *
 * Reversed by:           J
 */