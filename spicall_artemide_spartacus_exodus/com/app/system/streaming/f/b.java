package com.app.system.streaming.f;

import android.annotation.SuppressLint;
import java.io.IOException;
import java.io.InputStream;

@SuppressLint({"NewApi"})
public class b
  extends d
  implements Runnable
{
  private Thread e;
  
  public b()
  {
    this.a.b(0L);
  }
  
  public void a()
  {
    if (this.e == null)
    {
      this.e = new Thread(this);
      this.e.start();
    }
  }
  
  public void a(int paramInt)
  {
    this.a.a(paramInt);
  }
  
  public void b()
  {
    if (this.e != null) {}
    try
    {
      this.b.close();
      this.e.interrupt();
    }
    catch (IOException localIOException)
    {
      try
      {
        this.e.join();
        this.e = null;
        return;
        localIOException = localIOException;
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;) {}
      }
    }
  }
  
  /* Error */
  @SuppressLint({"NewApi"})
  public void run()
  {
    // Byte code:
    //   0: ldc 62
    //   2: ldc 64
    //   4: iconst_0
    //   5: anewarray 66	java/lang/Object
    //   8: invokestatic 72	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   11: invokestatic 76	java/lang/Thread:interrupted	()Z
    //   14: ifne +267 -> 281
    //   17: aload_0
    //   18: aload_0
    //   19: getfield 19	com/app/system/streaming/f/b:a	Lcom/app/system/streaming/f/h;
    //   22: invokevirtual 79	com/app/system/streaming/f/h:a	()[B
    //   25: putfield 83	com/app/system/streaming/f/b:c	[B
    //   28: aload_0
    //   29: getfield 46	com/app/system/streaming/f/b:b	Ljava/io/InputStream;
    //   32: aload_0
    //   33: getfield 83	com/app/system/streaming/f/b:c	[B
    //   36: bipush 16
    //   38: sipush 1256
    //   41: invokevirtual 87	java/io/InputStream:read	([BII)I
    //   44: istore_1
    //   45: iload_1
    //   46: ifle +160 -> 206
    //   49: aload_0
    //   50: getfield 46	com/app/system/streaming/f/b:b	Ljava/io/InputStream;
    //   53: checkcast 89	com/app/system/streaming/f/g
    //   56: invokevirtual 92	com/app/system/streaming/f/g:a	()Landroid/media/MediaCodec$BufferInfo;
    //   59: astore_2
    //   60: aload_0
    //   61: getfield 95	com/app/system/streaming/f/b:d	J
    //   64: lstore_3
    //   65: aload_0
    //   66: aload_2
    //   67: getfield 100	android/media/MediaCodec$BufferInfo:presentationTimeUs	J
    //   70: ldc2_w 101
    //   73: lmul
    //   74: putfield 95	com/app/system/streaming/f/b:d	J
    //   77: lload_3
    //   78: aload_0
    //   79: getfield 95	com/app/system/streaming/f/b:d	J
    //   82: lcmp
    //   83: ifle +13 -> 96
    //   86: aload_0
    //   87: getfield 19	com/app/system/streaming/f/b:a	Lcom/app/system/streaming/f/h;
    //   90: invokevirtual 104	com/app/system/streaming/f/h:b	()V
    //   93: goto -82 -> 11
    //   96: aload_0
    //   97: getfield 19	com/app/system/streaming/f/b:a	Lcom/app/system/streaming/f/h;
    //   100: invokevirtual 106	com/app/system/streaming/f/h:d	()V
    //   103: aload_0
    //   104: getfield 19	com/app/system/streaming/f/b:a	Lcom/app/system/streaming/f/h;
    //   107: aload_0
    //   108: getfield 95	com/app/system/streaming/f/b:d	J
    //   111: invokevirtual 108	com/app/system/streaming/f/h:c	(J)V
    //   114: aload_0
    //   115: getfield 83	com/app/system/streaming/f/b:c	[B
    //   118: bipush 12
    //   120: iconst_0
    //   121: i2b
    //   122: bastore
    //   123: aload_0
    //   124: getfield 83	com/app/system/streaming/f/b:c	[B
    //   127: bipush 13
    //   129: bipush 16
    //   131: i2b
    //   132: bastore
    //   133: aload_0
    //   134: getfield 83	com/app/system/streaming/f/b:c	[B
    //   137: bipush 14
    //   139: iload_1
    //   140: iconst_5
    //   141: ishr
    //   142: i2b
    //   143: i2b
    //   144: bastore
    //   145: aload_0
    //   146: getfield 83	com/app/system/streaming/f/b:c	[B
    //   149: bipush 15
    //   151: iload_1
    //   152: iconst_3
    //   153: ishl
    //   154: i2b
    //   155: i2b
    //   156: bastore
    //   157: aload_0
    //   158: getfield 83	com/app/system/streaming/f/b:c	[B
    //   161: astore_2
    //   162: aload_2
    //   163: bipush 15
    //   165: aload_2
    //   166: bipush 15
    //   168: baload
    //   169: sipush 248
    //   172: iand
    //   173: i2b
    //   174: i2b
    //   175: bastore
    //   176: aload_0
    //   177: getfield 83	com/app/system/streaming/f/b:c	[B
    //   180: astore_2
    //   181: aload_2
    //   182: bipush 15
    //   184: aload_2
    //   185: bipush 15
    //   187: baload
    //   188: iconst_0
    //   189: ior
    //   190: i2b
    //   191: i2b
    //   192: bastore
    //   193: aload_0
    //   194: iload_1
    //   195: bipush 12
    //   197: iadd
    //   198: iconst_4
    //   199: iadd
    //   200: invokevirtual 110	com/app/system/streaming/f/b:c	(I)V
    //   203: goto -192 -> 11
    //   206: aload_0
    //   207: getfield 19	com/app/system/streaming/f/b:a	Lcom/app/system/streaming/f/h;
    //   210: invokevirtual 104	com/app/system/streaming/f/h:b	()V
    //   213: goto -202 -> 11
    //   216: astore 5
    //   218: new 112	java/lang/StringBuilder
    //   221: dup
    //   222: invokespecial 113	java/lang/StringBuilder:<init>	()V
    //   225: astore 6
    //   227: aload 6
    //   229: ldc 115
    //   231: invokevirtual 119	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   234: pop
    //   235: aload 5
    //   237: invokevirtual 123	java/lang/ArrayIndexOutOfBoundsException:getMessage	()Ljava/lang/String;
    //   240: ifnull +12 -> 252
    //   243: aload 5
    //   245: invokevirtual 123	java/lang/ArrayIndexOutOfBoundsException:getMessage	()Ljava/lang/String;
    //   248: astore_2
    //   249: goto +6 -> 255
    //   252: ldc 125
    //   254: astore_2
    //   255: aload 6
    //   257: aload_2
    //   258: invokevirtual 119	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   261: pop
    //   262: ldc 62
    //   264: aload 6
    //   266: invokevirtual 128	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   269: iconst_0
    //   270: anewarray 66	java/lang/Object
    //   273: invokestatic 130	com/security/d/a:a	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   276: aload 5
    //   278: invokevirtual 133	java/lang/ArrayIndexOutOfBoundsException:printStackTrace	()V
    //   281: ldc 62
    //   283: ldc -121
    //   285: iconst_0
    //   286: anewarray 66	java/lang/Object
    //   289: invokestatic 72	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   292: return
    //   293: astore_2
    //   294: goto -13 -> 281
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	297	0	this	b
    //   44	154	1	i	int
    //   59	199	2	localObject	Object
    //   293	1	2	localIOException	IOException
    //   64	14	3	l	long
    //   216	61	5	localArrayIndexOutOfBoundsException	ArrayIndexOutOfBoundsException
    //   225	40	6	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   11	45	216	java/lang/ArrayIndexOutOfBoundsException
    //   49	93	216	java/lang/ArrayIndexOutOfBoundsException
    //   96	162	216	java/lang/ArrayIndexOutOfBoundsException
    //   176	181	216	java/lang/ArrayIndexOutOfBoundsException
    //   193	203	216	java/lang/ArrayIndexOutOfBoundsException
    //   206	213	216	java/lang/ArrayIndexOutOfBoundsException
    //   11	45	293	java/io/IOException
    //   11	45	293	java/lang/InterruptedException
    //   49	93	293	java/io/IOException
    //   49	93	293	java/lang/InterruptedException
    //   96	162	293	java/io/IOException
    //   96	162	293	java/lang/InterruptedException
    //   176	181	293	java/io/IOException
    //   176	181	293	java/lang/InterruptedException
    //   193	203	293	java/io/IOException
    //   193	203	293	java/lang/InterruptedException
    //   206	213	293	java/io/IOException
    //   206	213	293	java/lang/InterruptedException
  }
}


/* Location:              ~/com/app/system/streaming/f/b.class
 *
 * Reversed by:           J
 */