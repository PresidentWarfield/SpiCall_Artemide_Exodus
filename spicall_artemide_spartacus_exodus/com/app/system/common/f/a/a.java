package com.app.system.common.f.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.app.system.common.d.a.m;
import java.io.File;

public class a
  implements e
{
  private final String a;
  private final File b;
  
  public a(String paramString, File paramFile)
  {
    this.a = paramString;
    this.b = paramFile;
  }
  
  private void b(Context paramContext)
  {
    if (new m(this.a).a(paramContext, this.b.getName(), this.b) == 0) {
      this.b.delete();
    }
  }
  
  /* Error */
  private void c(Context paramContext)
  {
    // Byte code:
    //   0: new 48	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 49	java/lang/StringBuilder:<init>	()V
    //   7: astore_2
    //   8: aload_2
    //   9: aload_0
    //   10: getfield 19	com/app/system/common/f/a/a:a	Ljava/lang/String;
    //   13: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: pop
    //   17: aload_2
    //   18: bipush 95
    //   20: invokevirtual 56	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   23: pop
    //   24: aload_2
    //   25: aload_0
    //   26: getfield 21	com/app/system/common/f/a/a:b	Ljava/io/File;
    //   29: invokevirtual 34	java/io/File:getName	()Ljava/lang/String;
    //   32: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: pop
    //   36: aload_2
    //   37: invokevirtual 59	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   40: astore_2
    //   41: aload_1
    //   42: ldc 61
    //   44: iconst_0
    //   45: invokevirtual 67	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   48: ldc 69
    //   50: ldc 71
    //   52: invokeinterface 77 3 0
    //   57: astore_3
    //   58: new 79	com/security/c/a
    //   61: dup
    //   62: invokespecial 80	com/security/c/a:<init>	()V
    //   65: astore_1
    //   66: aload_1
    //   67: aload_3
    //   68: sipush 2121
    //   71: ldc 82
    //   73: ldc 82
    //   75: invokevirtual 85	com/security/c/a:a	(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)Z
    //   78: ifeq +193 -> 271
    //   81: new 48	java/lang/StringBuilder
    //   84: astore_3
    //   85: aload_3
    //   86: invokespecial 49	java/lang/StringBuilder:<init>	()V
    //   89: aload_3
    //   90: ldc 87
    //   92: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: pop
    //   96: aload_3
    //   97: aload_0
    //   98: getfield 21	com/app/system/common/f/a/a:b	Ljava/io/File;
    //   101: invokevirtual 90	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   104: pop
    //   105: ldc 92
    //   107: aload_3
    //   108: invokevirtual 59	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   111: iconst_0
    //   112: anewarray 4	java/lang/Object
    //   115: invokestatic 98	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   118: new 48	java/lang/StringBuilder
    //   121: astore_3
    //   122: aload_3
    //   123: invokespecial 49	java/lang/StringBuilder:<init>	()V
    //   126: aload_3
    //   127: ldc 100
    //   129: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   132: pop
    //   133: aload_3
    //   134: aload_0
    //   135: getfield 19	com/app/system/common/f/a/a:a	Ljava/lang/String;
    //   138: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   141: pop
    //   142: aload_3
    //   143: ldc 102
    //   145: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   148: pop
    //   149: aload_3
    //   150: aload_2
    //   151: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   154: pop
    //   155: aload_3
    //   156: invokevirtual 59	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   159: astore_2
    //   160: aload_1
    //   161: aload_0
    //   162: getfield 21	com/app/system/common/f/a/a:b	Ljava/io/File;
    //   165: invokevirtual 105	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   168: aload_2
    //   169: invokevirtual 108	com/security/c/a:a	(Ljava/lang/String;Ljava/lang/String;)Lcom/security/c/b;
    //   172: astore_3
    //   173: new 48	java/lang/StringBuilder
    //   176: astore 4
    //   178: aload 4
    //   180: invokespecial 49	java/lang/StringBuilder:<init>	()V
    //   183: aload 4
    //   185: ldc 110
    //   187: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   190: pop
    //   191: aload 4
    //   193: aload_2
    //   194: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   197: pop
    //   198: aload 4
    //   200: ldc 112
    //   202: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   205: pop
    //   206: aload 4
    //   208: aload_3
    //   209: invokevirtual 90	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   212: pop
    //   213: ldc 92
    //   215: aload 4
    //   217: invokevirtual 59	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   220: iconst_0
    //   221: anewarray 4	java/lang/Object
    //   224: invokestatic 98	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   227: getstatic 115	com/app/system/common/f/a/a$1:a	[I
    //   230: aload_3
    //   231: invokevirtual 121	com/security/c/b:ordinal	()I
    //   234: iaload
    //   235: tableswitch	default:+25->260, 1:+28->263, 2:+28->263, 3:+28->263
    //   260: goto +11 -> 271
    //   263: aload_0
    //   264: getfield 21	com/app/system/common/f/a/a:b	Ljava/io/File;
    //   267: invokevirtual 41	java/io/File:delete	()Z
    //   270: pop
    //   271: aload_1
    //   272: invokevirtual 123	com/security/c/a:a	()V
    //   275: goto +37 -> 312
    //   278: astore_2
    //   279: goto +34 -> 313
    //   282: astore_2
    //   283: ldc 92
    //   285: ldc 125
    //   287: iconst_1
    //   288: anewarray 4	java/lang/Object
    //   291: dup
    //   292: iconst_0
    //   293: aload_2
    //   294: aastore
    //   295: invokestatic 127	com/security/d/a:a	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   298: ldc 92
    //   300: aload_2
    //   301: invokevirtual 130	java/io/IOException:getMessage	()Ljava/lang/String;
    //   304: ldc 125
    //   306: invokestatic 135	com/security/d/b:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   309: goto -38 -> 271
    //   312: return
    //   313: aload_1
    //   314: invokevirtual 123	com/security/c/a:a	()V
    //   317: aload_2
    //   318: athrow
    //   319: astore_1
    //   320: goto -8 -> 312
    //   323: astore_1
    //   324: goto -7 -> 317
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	327	0	this	a
    //   0	327	1	paramContext	Context
    //   7	187	2	localObject1	Object
    //   278	1	2	localObject2	Object
    //   282	36	2	localIOException	java.io.IOException
    //   57	174	3	localObject3	Object
    //   176	40	4	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   66	260	278	finally
    //   263	271	278	finally
    //   283	309	278	finally
    //   66	260	282	java/io/IOException
    //   263	271	282	java/io/IOException
    //   271	275	319	java/lang/Exception
    //   313	317	323	java/lang/Exception
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
    if ((paramObject != null) && ((paramObject instanceof a)))
    {
      paramObject = (a)paramObject;
      boolean bool2 = bool1;
      if (this.a.equals(((a)paramObject).a))
      {
        bool2 = bool1;
        if (this.b.equals(((a)paramObject).b)) {
          bool2 = true;
        }
      }
      return bool2;
    }
    return false;
  }
  
  public String toString()
  {
    return String.format("PIC=%s", new Object[] { this.b.getName() });
  }
}


/* Location:              ~/com/app/system/common/f/a/a.class
 *
 * Reversed by:           J
 */