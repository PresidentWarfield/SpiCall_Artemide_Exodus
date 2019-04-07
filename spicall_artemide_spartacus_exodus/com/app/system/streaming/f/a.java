package com.app.system.streaming.f;

import java.io.IOException;
import java.io.InputStream;

public class a
  extends d
  implements Runnable
{
  private Thread e;
  private int f = 8000;
  
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
    if (this.e == null)
    {
      this.e = new Thread(this);
      this.e.start();
    }
  }
  
  public void a(int paramInt)
  {
    this.f = paramInt;
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
  public void run()
  {
    // Byte code:
    //   0: ldc 70
    //   2: ldc 72
    //   4: iconst_0
    //   5: anewarray 74	java/lang/Object
    //   8: invokestatic 80	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   11: invokestatic 86	android/os/SystemClock:elapsedRealtime	()J
    //   14: pop2
    //   15: bipush 8
    //   17: newarray <illegal type>
    //   19: astore_1
    //   20: invokestatic 90	java/lang/Thread:interrupted	()Z
    //   23: ifne +427 -> 450
    //   26: aload_0
    //   27: getfield 23	com/app/system/streaming/f/a:b	Ljava/io/InputStream;
    //   30: invokevirtual 93	java/io/InputStream:read	()I
    //   33: sipush 255
    //   36: iand
    //   37: sipush 255
    //   40: if_icmpne -14 -> 26
    //   43: aload_0
    //   44: getfield 23	com/app/system/streaming/f/a:b	Ljava/io/InputStream;
    //   47: invokevirtual 93	java/io/InputStream:read	()I
    //   50: i2b
    //   51: istore_2
    //   52: iconst_1
    //   53: istore_3
    //   54: aload_1
    //   55: iconst_1
    //   56: iload_2
    //   57: i2b
    //   58: bastore
    //   59: aload_1
    //   60: iconst_1
    //   61: baload
    //   62: sipush 240
    //   65: iand
    //   66: sipush 240
    //   69: if_icmpne -43 -> 26
    //   72: aload_0
    //   73: aload_1
    //   74: iconst_2
    //   75: iconst_5
    //   76: invokespecial 95	com/app/system/streaming/f/a:a	([BII)I
    //   79: pop
    //   80: aload_1
    //   81: iconst_1
    //   82: baload
    //   83: iconst_1
    //   84: iand
    //   85: ifle +6 -> 91
    //   88: goto +5 -> 93
    //   91: iconst_0
    //   92: istore_3
    //   93: aload_1
    //   94: iconst_3
    //   95: baload
    //   96: istore 4
    //   98: aload_1
    //   99: iconst_4
    //   100: baload
    //   101: istore 5
    //   103: aload_1
    //   104: iconst_5
    //   105: baload
    //   106: istore 6
    //   108: iload_3
    //   109: ifeq +9 -> 118
    //   112: bipush 7
    //   114: istore_2
    //   115: goto +6 -> 121
    //   118: bipush 9
    //   120: istore_2
    //   121: sipush 255
    //   124: iload 6
    //   126: iand
    //   127: iconst_5
    //   128: ishr
    //   129: iload 5
    //   131: sipush 255
    //   134: iand
    //   135: iconst_3
    //   136: ishl
    //   137: iload 4
    //   139: iconst_3
    //   140: iand
    //   141: bipush 11
    //   143: ishl
    //   144: ior
    //   145: ior
    //   146: iload_2
    //   147: isub
    //   148: istore 6
    //   150: aload_1
    //   151: bipush 6
    //   153: baload
    //   154: istore_2
    //   155: iload 6
    //   157: sipush 1272
    //   160: idiv
    //   161: istore_2
    //   162: iload_3
    //   163: ifne +14 -> 177
    //   166: aload_0
    //   167: getfield 23	com/app/system/streaming/f/a:b	Ljava/io/InputStream;
    //   170: aload_1
    //   171: iconst_0
    //   172: iconst_2
    //   173: invokevirtual 28	java/io/InputStream:read	([BII)I
    //   176: pop
    //   177: aload_0
    //   178: getstatic 101	com/app/system/streaming/a/a:t	[I
    //   181: aload_1
    //   182: iconst_2
    //   183: baload
    //   184: bipush 60
    //   186: iand
    //   187: iconst_2
    //   188: ishr
    //   189: iaload
    //   190: putfield 16	com/app/system/streaming/f/a:f	I
    //   193: aload_1
    //   194: iconst_2
    //   195: baload
    //   196: istore_3
    //   197: aload_0
    //   198: aload_0
    //   199: getfield 104	com/app/system/streaming/f/a:d	J
    //   202: ldc2_w 105
    //   205: aload_0
    //   206: getfield 16	com/app/system/streaming/f/a:f	I
    //   209: i2l
    //   210: ldiv
    //   211: ladd
    //   212: putfield 104	com/app/system/streaming/f/a:d	J
    //   215: iconst_0
    //   216: istore_3
    //   217: iload_3
    //   218: iload 6
    //   220: if_icmpge -200 -> 20
    //   223: aload_0
    //   224: aload_0
    //   225: getfield 49	com/app/system/streaming/f/a:a	Lcom/app/system/streaming/f/h;
    //   228: invokevirtual 109	com/app/system/streaming/f/h:a	()[B
    //   231: putfield 113	com/app/system/streaming/f/a:c	[B
    //   234: aload_0
    //   235: getfield 49	com/app/system/streaming/f/a:a	Lcom/app/system/streaming/f/h;
    //   238: aload_0
    //   239: getfield 104	com/app/system/streaming/f/a:d	J
    //   242: invokevirtual 115	com/app/system/streaming/f/h:c	(J)V
    //   245: iload 6
    //   247: iload_3
    //   248: isub
    //   249: istore_2
    //   250: iload_2
    //   251: sipush 1256
    //   254: if_icmple +10 -> 264
    //   257: sipush 1256
    //   260: istore_2
    //   261: goto +10 -> 271
    //   264: aload_0
    //   265: getfield 49	com/app/system/streaming/f/a:a	Lcom/app/system/streaming/f/h;
    //   268: invokevirtual 117	com/app/system/streaming/f/h:d	()V
    //   271: iload_3
    //   272: iload_2
    //   273: iadd
    //   274: istore_3
    //   275: aload_0
    //   276: aload_0
    //   277: getfield 113	com/app/system/streaming/f/a:c	[B
    //   280: bipush 16
    //   282: iload_2
    //   283: invokespecial 95	com/app/system/streaming/f/a:a	([BII)I
    //   286: pop
    //   287: aload_0
    //   288: getfield 113	com/app/system/streaming/f/a:c	[B
    //   291: bipush 12
    //   293: iconst_0
    //   294: i2b
    //   295: bastore
    //   296: aload_0
    //   297: getfield 113	com/app/system/streaming/f/a:c	[B
    //   300: bipush 13
    //   302: bipush 16
    //   304: i2b
    //   305: bastore
    //   306: aload_0
    //   307: getfield 113	com/app/system/streaming/f/a:c	[B
    //   310: bipush 14
    //   312: iload 6
    //   314: iconst_5
    //   315: ishr
    //   316: i2b
    //   317: i2b
    //   318: bastore
    //   319: aload_0
    //   320: getfield 113	com/app/system/streaming/f/a:c	[B
    //   323: bipush 15
    //   325: iload 6
    //   327: iconst_3
    //   328: ishl
    //   329: i2b
    //   330: i2b
    //   331: bastore
    //   332: aload_0
    //   333: getfield 113	com/app/system/streaming/f/a:c	[B
    //   336: astore 7
    //   338: aload 7
    //   340: bipush 15
    //   342: aload 7
    //   344: bipush 15
    //   346: baload
    //   347: sipush 248
    //   350: iand
    //   351: i2b
    //   352: i2b
    //   353: bastore
    //   354: aload_0
    //   355: getfield 113	com/app/system/streaming/f/a:c	[B
    //   358: astore 7
    //   360: aload 7
    //   362: bipush 15
    //   364: aload 7
    //   366: bipush 15
    //   368: baload
    //   369: iconst_0
    //   370: ior
    //   371: i2b
    //   372: i2b
    //   373: bastore
    //   374: aload_0
    //   375: iload_2
    //   376: bipush 16
    //   378: iadd
    //   379: invokevirtual 119	com/app/system/streaming/f/a:c	(I)V
    //   382: goto -165 -> 217
    //   385: astore 7
    //   387: new 121	java/lang/StringBuilder
    //   390: dup
    //   391: invokespecial 122	java/lang/StringBuilder:<init>	()V
    //   394: astore 8
    //   396: aload 8
    //   398: ldc 124
    //   400: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   403: pop
    //   404: aload 7
    //   406: invokevirtual 132	java/lang/ArrayIndexOutOfBoundsException:getMessage	()Ljava/lang/String;
    //   409: ifnull +12 -> 421
    //   412: aload 7
    //   414: invokevirtual 132	java/lang/ArrayIndexOutOfBoundsException:getMessage	()Ljava/lang/String;
    //   417: astore_1
    //   418: goto +6 -> 424
    //   421: ldc -122
    //   423: astore_1
    //   424: aload 8
    //   426: aload_1
    //   427: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   430: pop
    //   431: ldc 70
    //   433: aload 8
    //   435: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   438: iconst_0
    //   439: anewarray 74	java/lang/Object
    //   442: invokestatic 139	com/security/d/a:a	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   445: aload 7
    //   447: invokevirtual 142	java/lang/ArrayIndexOutOfBoundsException:printStackTrace	()V
    //   450: ldc 70
    //   452: ldc -112
    //   454: iconst_0
    //   455: anewarray 74	java/lang/Object
    //   458: invokestatic 80	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   461: return
    //   462: astore_1
    //   463: goto -13 -> 450
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	466	0	this	a
    //   19	408	1	localObject	Object
    //   462	1	1	localIOException	IOException
    //   51	328	2	i	int
    //   53	222	3	j	int
    //   96	45	4	k	int
    //   101	34	5	m	int
    //   106	223	6	n	int
    //   336	29	7	arrayOfByte	byte[]
    //   385	61	7	localArrayIndexOutOfBoundsException	ArrayIndexOutOfBoundsException
    //   394	40	8	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   20	26	385	java/lang/ArrayIndexOutOfBoundsException
    //   26	52	385	java/lang/ArrayIndexOutOfBoundsException
    //   72	80	385	java/lang/ArrayIndexOutOfBoundsException
    //   155	162	385	java/lang/ArrayIndexOutOfBoundsException
    //   166	177	385	java/lang/ArrayIndexOutOfBoundsException
    //   177	193	385	java/lang/ArrayIndexOutOfBoundsException
    //   197	215	385	java/lang/ArrayIndexOutOfBoundsException
    //   223	245	385	java/lang/ArrayIndexOutOfBoundsException
    //   264	271	385	java/lang/ArrayIndexOutOfBoundsException
    //   275	338	385	java/lang/ArrayIndexOutOfBoundsException
    //   354	360	385	java/lang/ArrayIndexOutOfBoundsException
    //   374	382	385	java/lang/ArrayIndexOutOfBoundsException
    //   20	26	462	java/io/IOException
    //   20	26	462	java/lang/InterruptedException
    //   26	52	462	java/io/IOException
    //   26	52	462	java/lang/InterruptedException
    //   72	80	462	java/io/IOException
    //   72	80	462	java/lang/InterruptedException
    //   155	162	462	java/io/IOException
    //   155	162	462	java/lang/InterruptedException
    //   166	177	462	java/io/IOException
    //   166	177	462	java/lang/InterruptedException
    //   177	193	462	java/io/IOException
    //   177	193	462	java/lang/InterruptedException
    //   197	215	462	java/io/IOException
    //   197	215	462	java/lang/InterruptedException
    //   223	245	462	java/io/IOException
    //   223	245	462	java/lang/InterruptedException
    //   264	271	462	java/io/IOException
    //   264	271	462	java/lang/InterruptedException
    //   275	338	462	java/io/IOException
    //   275	338	462	java/lang/InterruptedException
    //   354	360	462	java/io/IOException
    //   354	360	462	java/lang/InterruptedException
    //   374	382	462	java/io/IOException
    //   374	382	462	java/lang/InterruptedException
  }
}


/* Location:              ~/com/app/system/streaming/f/a.class
 *
 * Reversed by:           J
 */