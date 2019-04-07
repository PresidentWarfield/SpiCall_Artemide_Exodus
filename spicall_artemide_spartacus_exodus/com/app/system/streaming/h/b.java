package com.app.system.streaming.h;

import android.annotation.SuppressLint;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.util.Base64;
import com.app.system.streaming.f.f;
import java.util.concurrent.Semaphore;

public class b
  extends d
{
  private Semaphore O = new Semaphore(0);
  private com.app.system.streaming.d.a P;
  
  public b()
  {
    this(0);
  }
  
  public b(int paramInt)
  {
    super(paramInt);
    this.L = "video/avc";
    this.M = 17;
    this.y = 2;
    this.a = new f();
  }
  
  private com.app.system.streaming.d.a u()
  {
    if (this.c != 1) {
      return v();
    }
    return w();
  }
  
  @SuppressLint({"NewApi"})
  private com.app.system.streaming.d.a v()
  {
    p();
    r();
    try
    {
      if (this.u.d >= 640) {
        this.c = ((byte)1);
      }
      Object localObject = com.app.system.streaming.c.b.a(this.x, this.u.d, this.u.e);
      localObject = new com.app.system.streaming.d.a(((com.app.system.streaming.c.b)localObject).b(), ((com.app.system.streaming.c.b)localObject).a());
      return (com.app.system.streaming.d.a)localObject;
    }
    catch (Exception localException)
    {
      com.security.d.a.a("H264Stream", "Resolution not supported with the MediaCodec API, we fallback on the old streamign method.", new Object[0]);
      this.c = ((byte)1);
    }
    return u();
  }
  
  /* Error */
  private com.app.system.streaming.d.a w()
  {
    // Byte code:
    //   0: new 120	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 121	java/lang/StringBuilder:<init>	()V
    //   7: astore_1
    //   8: aload_1
    //   9: ldc 123
    //   11: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: pop
    //   15: aload_1
    //   16: aload_0
    //   17: getfield 130	com/app/system/streaming/h/b:t	Lcom/app/system/streaming/h/c;
    //   20: getfield 132	com/app/system/streaming/h/c:b	I
    //   23: invokevirtual 135	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   26: pop
    //   27: aload_1
    //   28: ldc -119
    //   30: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: pop
    //   34: aload_1
    //   35: aload_0
    //   36: getfield 130	com/app/system/streaming/h/b:t	Lcom/app/system/streaming/h/c;
    //   39: getfield 75	com/app/system/streaming/h/c:d	I
    //   42: invokevirtual 135	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload_1
    //   47: ldc -119
    //   49: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: aload_1
    //   54: aload_0
    //   55: getfield 130	com/app/system/streaming/h/b:t	Lcom/app/system/streaming/h/c;
    //   58: getfield 82	com/app/system/streaming/h/c:e	I
    //   61: invokevirtual 135	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   64: pop
    //   65: aload_1
    //   66: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   69: astore_1
    //   70: aload_0
    //   71: getfield 79	com/app/system/streaming/h/b:x	Landroid/content/SharedPreferences;
    //   74: ifnull +51 -> 125
    //   77: aload_0
    //   78: getfield 79	com/app/system/streaming/h/b:x	Landroid/content/SharedPreferences;
    //   81: aload_1
    //   82: invokeinterface 146 2 0
    //   87: ifeq +38 -> 125
    //   90: aload_0
    //   91: getfield 79	com/app/system/streaming/h/b:x	Landroid/content/SharedPreferences;
    //   94: aload_1
    //   95: ldc -108
    //   97: invokeinterface 152 3 0
    //   102: ldc -119
    //   104: invokevirtual 158	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   107: astore_1
    //   108: new 89	com/app/system/streaming/d/a
    //   111: dup
    //   112: aload_1
    //   113: iconst_0
    //   114: aaload
    //   115: aload_1
    //   116: iconst_1
    //   117: aaload
    //   118: aload_1
    //   119: iconst_2
    //   120: aaload
    //   121: invokespecial 161	com/app/system/streaming/d/a:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   124: areturn
    //   125: invokestatic 166	android/os/Environment:getExternalStorageState	()Ljava/lang/String;
    //   128: ldc -88
    //   130: invokevirtual 172	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   133: ifeq +745 -> 878
    //   136: new 120	java/lang/StringBuilder
    //   139: dup
    //   140: invokespecial 121	java/lang/StringBuilder:<init>	()V
    //   143: astore_2
    //   144: aload_2
    //   145: invokestatic 176	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   148: invokevirtual 181	java/io/File:getPath	()Ljava/lang/String;
    //   151: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   154: pop
    //   155: aload_2
    //   156: ldc -73
    //   158: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   161: pop
    //   162: aload_2
    //   163: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   166: astore_2
    //   167: new 120	java/lang/StringBuilder
    //   170: dup
    //   171: invokespecial 121	java/lang/StringBuilder:<init>	()V
    //   174: astore_3
    //   175: aload_3
    //   176: ldc -71
    //   178: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   181: pop
    //   182: aload_3
    //   183: aload_2
    //   184: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   187: pop
    //   188: ldc 100
    //   190: aload_3
    //   191: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   194: iconst_0
    //   195: anewarray 104	java/lang/Object
    //   198: invokestatic 187	com/security/d/a:c	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   201: new 178	java/io/File
    //   204: astore_3
    //   205: aload_3
    //   206: aload_2
    //   207: invokespecial 190	java/io/File:<init>	(Ljava/lang/String;)V
    //   210: aload_3
    //   211: invokevirtual 194	java/io/File:createNewFile	()Z
    //   214: pop
    //   215: aload_0
    //   216: getfield 198	com/app/system/streaming/h/b:G	Z
    //   219: istore 4
    //   221: aload_0
    //   222: iconst_0
    //   223: putfield 198	com/app/system/streaming/h/b:G	Z
    //   226: aload_0
    //   227: getfield 201	com/app/system/streaming/h/b:J	Z
    //   230: istore 5
    //   232: aload_0
    //   233: getfield 205	com/app/system/streaming/h/b:C	Landroid/hardware/Camera;
    //   236: ifnull +9 -> 245
    //   239: iconst_1
    //   240: istore 6
    //   242: goto +6 -> 248
    //   245: iconst_0
    //   246: istore 6
    //   248: aload_0
    //   249: invokevirtual 64	com/app/system/streaming/h/b:p	()V
    //   252: aload_0
    //   253: getfield 201	com/app/system/streaming/h/b:J	Z
    //   256: ifeq +19 -> 275
    //   259: aload_0
    //   260: invokevirtual 208	com/app/system/streaming/h/b:s	()V
    //   263: aload_0
    //   264: getfield 205	com/app/system/streaming/h/b:C	Landroid/hardware/Camera;
    //   267: invokevirtual 213	android/hardware/Camera:stopPreview	()V
    //   270: aload_0
    //   271: iconst_0
    //   272: putfield 201	com/app/system/streaming/h/b:J	Z
    //   275: ldc2_w 214
    //   278: invokestatic 221	java/lang/Thread:sleep	(J)V
    //   281: goto +8 -> 289
    //   284: astore_3
    //   285: aload_3
    //   286: invokevirtual 224	java/lang/InterruptedException:printStackTrace	()V
    //   289: aload_0
    //   290: invokevirtual 226	com/app/system/streaming/h/b:t	()V
    //   293: new 228	android/media/MediaRecorder
    //   296: astore_3
    //   297: aload_3
    //   298: invokespecial 229	android/media/MediaRecorder:<init>	()V
    //   301: aload_0
    //   302: aload_3
    //   303: putfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   306: aload_0
    //   307: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   310: aload_0
    //   311: getfield 205	com/app/system/streaming/h/b:C	Landroid/hardware/Camera;
    //   314: invokevirtual 236	android/media/MediaRecorder:setCamera	(Landroid/hardware/Camera;)V
    //   317: aload_0
    //   318: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   321: iconst_1
    //   322: invokevirtual 239	android/media/MediaRecorder:setVideoSource	(I)V
    //   325: aload_0
    //   326: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   329: iconst_1
    //   330: invokevirtual 242	android/media/MediaRecorder:setOutputFormat	(I)V
    //   333: aload_0
    //   334: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   337: aload_0
    //   338: getfield 35	com/app/system/streaming/h/b:y	I
    //   341: invokevirtual 245	android/media/MediaRecorder:setVideoEncoder	(I)V
    //   344: aload_0
    //   345: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   348: aload_0
    //   349: getfield 248	com/app/system/streaming/h/b:w	Lcom/app/system/streaming/b/b;
    //   352: invokevirtual 254	com/app/system/streaming/b/b:getHolder	()Landroid/view/SurfaceHolder;
    //   355: invokeinterface 260 1 0
    //   360: invokevirtual 264	android/media/MediaRecorder:setPreviewDisplay	(Landroid/view/Surface;)V
    //   363: aload_0
    //   364: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   367: aload_0
    //   368: getfield 130	com/app/system/streaming/h/b:t	Lcom/app/system/streaming/h/c;
    //   371: getfield 75	com/app/system/streaming/h/c:d	I
    //   374: aload_0
    //   375: getfield 130	com/app/system/streaming/h/b:t	Lcom/app/system/streaming/h/c;
    //   378: getfield 82	com/app/system/streaming/h/c:e	I
    //   381: invokevirtual 268	android/media/MediaRecorder:setVideoSize	(II)V
    //   384: aload_0
    //   385: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   388: aload_0
    //   389: getfield 130	com/app/system/streaming/h/b:t	Lcom/app/system/streaming/h/c;
    //   392: getfield 132	com/app/system/streaming/h/c:b	I
    //   395: invokevirtual 271	android/media/MediaRecorder:setVideoFrameRate	(I)V
    //   398: aload_0
    //   399: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   402: astore_3
    //   403: aload_0
    //   404: getfield 130	com/app/system/streaming/h/b:t	Lcom/app/system/streaming/h/c;
    //   407: getfield 273	com/app/system/streaming/h/c:c	I
    //   410: istore 7
    //   412: iload 7
    //   414: i2d
    //   415: dstore 8
    //   417: dload 8
    //   419: invokestatic 279	java/lang/Double:isNaN	(D)Z
    //   422: pop
    //   423: dload 8
    //   425: ldc2_w 280
    //   428: dmul
    //   429: d2i
    //   430: istore 7
    //   432: aload_3
    //   433: iload 7
    //   435: invokevirtual 284	android/media/MediaRecorder:setVideoEncodingBitRate	(I)V
    //   438: aload_0
    //   439: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   442: aload_2
    //   443: invokevirtual 287	android/media/MediaRecorder:setOutputFile	(Ljava/lang/String;)V
    //   446: aload_0
    //   447: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   450: sipush 3000
    //   453: invokevirtual 290	android/media/MediaRecorder:setMaxDuration	(I)V
    //   456: aload_0
    //   457: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   460: astore 10
    //   462: new 6	com/app/system/streaming/h/b$1
    //   465: astore_3
    //   466: aload_3
    //   467: aload_0
    //   468: invokespecial 293	com/app/system/streaming/h/b$1:<init>	(Lcom/app/system/streaming/h/b;)V
    //   471: aload 10
    //   473: aload_3
    //   474: invokevirtual 297	android/media/MediaRecorder:setOnInfoListener	(Landroid/media/MediaRecorder$OnInfoListener;)V
    //   477: aload_0
    //   478: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   481: invokevirtual 300	android/media/MediaRecorder:prepare	()V
    //   484: aload_0
    //   485: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   488: invokevirtual 303	android/media/MediaRecorder:start	()V
    //   491: aload_0
    //   492: getfield 22	com/app/system/streaming/h/b:O	Ljava/util/concurrent/Semaphore;
    //   495: ldc2_w 304
    //   498: getstatic 311	java/util/concurrent/TimeUnit:SECONDS	Ljava/util/concurrent/TimeUnit;
    //   501: invokevirtual 315	java/util/concurrent/Semaphore:tryAcquire	(JLjava/util/concurrent/TimeUnit;)Z
    //   504: ifeq +24 -> 528
    //   507: ldc 100
    //   509: ldc_w 317
    //   512: iconst_0
    //   513: anewarray 104	java/lang/Object
    //   516: invokestatic 319	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   519: ldc2_w 320
    //   522: invokestatic 221	java/lang/Thread:sleep	(J)V
    //   525: goto +15 -> 540
    //   528: ldc 100
    //   530: ldc_w 323
    //   533: iconst_0
    //   534: anewarray 104	java/lang/Object
    //   537: invokestatic 319	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   540: aload_0
    //   541: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   544: invokevirtual 326	android/media/MediaRecorder:stop	()V
    //   547: aload_0
    //   548: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   551: invokevirtual 329	android/media/MediaRecorder:release	()V
    //   554: aload_0
    //   555: aconst_null
    //   556: putfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   559: aload_0
    //   560: invokevirtual 208	com/app/system/streaming/h/b:s	()V
    //   563: iload 6
    //   565: ifne +7 -> 572
    //   568: aload_0
    //   569: invokevirtual 332	com/app/system/streaming/h/b:q	()V
    //   572: aload_0
    //   573: iload 4
    //   575: putfield 198	com/app/system/streaming/h/b:G	Z
    //   578: iload 5
    //   580: ifeq +69 -> 649
    //   583: aload_0
    //   584: invokevirtual 335	com/app/system/streaming/h/b:l	()V
    //   587: goto +62 -> 649
    //   590: astore_3
    //   591: goto +58 -> 649
    //   594: astore_1
    //   595: goto +221 -> 816
    //   598: astore_3
    //   599: aload_3
    //   600: invokevirtual 224	java/lang/InterruptedException:printStackTrace	()V
    //   603: aload_0
    //   604: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   607: invokevirtual 326	android/media/MediaRecorder:stop	()V
    //   610: aload_0
    //   611: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   614: invokevirtual 329	android/media/MediaRecorder:release	()V
    //   617: aload_0
    //   618: aconst_null
    //   619: putfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   622: aload_0
    //   623: invokevirtual 208	com/app/system/streaming/h/b:s	()V
    //   626: iload 6
    //   628: ifne +7 -> 635
    //   631: aload_0
    //   632: invokevirtual 332	com/app/system/streaming/h/b:q	()V
    //   635: aload_0
    //   636: iload 4
    //   638: putfield 198	com/app/system/streaming/h/b:G	Z
    //   641: iload 5
    //   643: ifeq +6 -> 649
    //   646: goto -63 -> 583
    //   649: new 89	com/app/system/streaming/d/a
    //   652: dup
    //   653: aload_2
    //   654: invokespecial 336	com/app/system/streaming/d/a:<init>	(Ljava/lang/String;)V
    //   657: astore_3
    //   658: new 178	java/io/File
    //   661: dup
    //   662: aload_2
    //   663: invokespecial 190	java/io/File:<init>	(Ljava/lang/String;)V
    //   666: invokevirtual 339	java/io/File:delete	()Z
    //   669: ifne +15 -> 684
    //   672: ldc 100
    //   674: ldc_w 341
    //   677: iconst_0
    //   678: anewarray 104	java/lang/Object
    //   681: invokestatic 109	com/security/d/a:a	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   684: ldc 100
    //   686: ldc_w 343
    //   689: iconst_0
    //   690: anewarray 104	java/lang/Object
    //   693: invokestatic 187	com/security/d/a:c	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   696: aload_0
    //   697: getfield 79	com/app/system/streaming/h/b:x	Landroid/content/SharedPreferences;
    //   700: ifnull +84 -> 784
    //   703: aload_0
    //   704: getfield 79	com/app/system/streaming/h/b:x	Landroid/content/SharedPreferences;
    //   707: invokeinterface 347 1 0
    //   712: astore 10
    //   714: new 120	java/lang/StringBuilder
    //   717: dup
    //   718: invokespecial 121	java/lang/StringBuilder:<init>	()V
    //   721: astore_2
    //   722: aload_2
    //   723: aload_3
    //   724: invokevirtual 348	com/app/system/streaming/d/a:a	()Ljava/lang/String;
    //   727: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   730: pop
    //   731: aload_2
    //   732: ldc -119
    //   734: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   737: pop
    //   738: aload_2
    //   739: aload_3
    //   740: invokevirtual 350	com/app/system/streaming/d/a:c	()Ljava/lang/String;
    //   743: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   746: pop
    //   747: aload_2
    //   748: ldc -119
    //   750: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   753: pop
    //   754: aload_2
    //   755: aload_3
    //   756: invokevirtual 351	com/app/system/streaming/d/a:b	()Ljava/lang/String;
    //   759: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   762: pop
    //   763: aload 10
    //   765: aload_1
    //   766: aload_2
    //   767: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   770: invokeinterface 357 3 0
    //   775: pop
    //   776: aload 10
    //   778: invokeinterface 360 1 0
    //   783: pop
    //   784: aload_3
    //   785: areturn
    //   786: astore_2
    //   787: new 362	com/app/system/streaming/exceptions/ConfNotSupportedException
    //   790: astore_1
    //   791: aload_1
    //   792: aload_2
    //   793: invokevirtual 365	java/lang/RuntimeException:getMessage	()Ljava/lang/String;
    //   796: invokespecial 366	com/app/system/streaming/exceptions/ConfNotSupportedException:<init>	(Ljava/lang/String;)V
    //   799: aload_1
    //   800: athrow
    //   801: astore_1
    //   802: new 362	com/app/system/streaming/exceptions/ConfNotSupportedException
    //   805: astore_2
    //   806: aload_2
    //   807: aload_1
    //   808: invokevirtual 367	java/io/IOException:getMessage	()Ljava/lang/String;
    //   811: invokespecial 366	com/app/system/streaming/exceptions/ConfNotSupportedException:<init>	(Ljava/lang/String;)V
    //   814: aload_2
    //   815: athrow
    //   816: aload_0
    //   817: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   820: invokevirtual 326	android/media/MediaRecorder:stop	()V
    //   823: aload_0
    //   824: getfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   827: invokevirtual 329	android/media/MediaRecorder:release	()V
    //   830: aload_0
    //   831: aconst_null
    //   832: putfield 232	com/app/system/streaming/h/b:r	Landroid/media/MediaRecorder;
    //   835: aload_0
    //   836: invokevirtual 208	com/app/system/streaming/h/b:s	()V
    //   839: iload 6
    //   841: ifne +7 -> 848
    //   844: aload_0
    //   845: invokevirtual 332	com/app/system/streaming/h/b:q	()V
    //   848: aload_0
    //   849: iload 4
    //   851: putfield 198	com/app/system/streaming/h/b:G	Z
    //   854: iload 5
    //   856: ifeq +7 -> 863
    //   859: aload_0
    //   860: invokevirtual 335	com/app/system/streaming/h/b:l	()V
    //   863: aload_1
    //   864: athrow
    //   865: astore_1
    //   866: new 369	com/app/system/streaming/exceptions/StorageUnavailableException
    //   869: dup
    //   870: aload_1
    //   871: invokevirtual 367	java/io/IOException:getMessage	()Ljava/lang/String;
    //   874: invokespecial 370	com/app/system/streaming/exceptions/StorageUnavailableException:<init>	(Ljava/lang/String;)V
    //   877: athrow
    //   878: new 369	com/app/system/streaming/exceptions/StorageUnavailableException
    //   881: dup
    //   882: ldc_w 372
    //   885: invokespecial 370	com/app/system/streaming/exceptions/StorageUnavailableException:<init>	(Ljava/lang/String;)V
    //   888: athrow
    //   889: astore_3
    //   890: goto -620 -> 270
    //   893: astore_3
    //   894: goto -347 -> 547
    //   897: astore_3
    //   898: goto -288 -> 610
    //   901: astore_2
    //   902: goto -79 -> 823
    //   905: astore_2
    //   906: goto -43 -> 863
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	909	0	this	b
    //   7	112	1	localObject1	Object
    //   594	172	1	str	String
    //   790	10	1	localConfNotSupportedException1	com.app.system.streaming.exceptions.ConfNotSupportedException
    //   801	63	1	localIOException1	java.io.IOException
    //   865	6	1	localIOException2	java.io.IOException
    //   143	624	2	localObject2	Object
    //   786	7	2	localRuntimeException	RuntimeException
    //   805	10	2	localConfNotSupportedException2	com.app.system.streaming.exceptions.ConfNotSupportedException
    //   901	1	2	localException1	Exception
    //   905	1	2	localException2	Exception
    //   174	37	3	localObject3	Object
    //   284	2	3	localInterruptedException1	InterruptedException
    //   296	178	3	localObject4	Object
    //   590	1	3	localException3	Exception
    //   598	2	3	localInterruptedException2	InterruptedException
    //   657	128	3	locala	com.app.system.streaming.d.a
    //   889	1	3	localException4	Exception
    //   893	1	3	localException5	Exception
    //   897	1	3	localException6	Exception
    //   219	631	4	bool1	boolean
    //   230	625	5	bool2	boolean
    //   240	600	6	i	int
    //   410	24	7	j	int
    //   415	9	8	d	double
    //   460	317	10	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   275	281	284	java/lang/InterruptedException
    //   583	587	590	java/lang/Exception
    //   293	412	594	finally
    //   432	525	594	finally
    //   528	540	594	finally
    //   599	603	594	finally
    //   787	801	594	finally
    //   802	816	594	finally
    //   293	412	598	java/lang/InterruptedException
    //   432	525	598	java/lang/InterruptedException
    //   528	540	598	java/lang/InterruptedException
    //   293	412	786	java/lang/RuntimeException
    //   432	525	786	java/lang/RuntimeException
    //   528	540	786	java/lang/RuntimeException
    //   293	412	801	java/io/IOException
    //   432	525	801	java/io/IOException
    //   528	540	801	java/io/IOException
    //   201	215	865	java/io/IOException
    //   263	270	889	java/lang/Exception
    //   540	547	893	java/lang/Exception
    //   603	610	897	java/lang/Exception
    //   816	823	901	java/lang/Exception
    //   859	863	905	java/lang/Exception
  }
  
  public void d()
  {
    try
    {
      super.d();
      this.c = ((byte)this.d);
      this.u = this.t.a();
      this.P = u();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void e()
  {
    try
    {
      if (!this.f)
      {
        d();
        byte[] arrayOfByte1 = Base64.decode(this.P.b(), 2);
        byte[] arrayOfByte2 = Base64.decode(this.P.c(), 2);
        ((f)this.a).a(arrayOfByte1, arrayOfByte2);
        super.e();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String i()
  {
    try
    {
      if (this.P != null)
      {
        localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        ((StringBuilder)localObject1).append("m=video ");
        ((StringBuilder)localObject1).append(String.valueOf(a()[0]));
        ((StringBuilder)localObject1).append(" RTP/AVP 96\r\na=rtpmap:96 H264/90000\r\na=fmtp:96 packetization-mode=1;profile-level-id=");
        ((StringBuilder)localObject1).append(this.P.a());
        ((StringBuilder)localObject1).append(";sprop-parameter-sets=");
        ((StringBuilder)localObject1).append(this.P.c());
        ((StringBuilder)localObject1).append(",");
        ((StringBuilder)localObject1).append(this.P.b());
        ((StringBuilder)localObject1).append(";\r\n");
        localObject1 = ((StringBuilder)localObject1).toString();
        return (String)localObject1;
      }
      Object localObject1 = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject1).<init>("You need to call configure() first !");
      throw ((Throwable)localObject1);
    }
    finally {}
  }
}


/* Location:              ~/com/app/system/streaming/h/b.class
 *
 * Reversed by:           J
 */