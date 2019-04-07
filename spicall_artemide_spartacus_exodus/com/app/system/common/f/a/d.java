package com.app.system.common.f.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.app.system.common.d.a.h;
import com.app.system.common.entity.FileEntry;
import com.app.system.common.h.g;

public class d
  implements e
{
  private final String a;
  private final g b;
  private final FileEntry c;
  
  public d(String paramString, g paramg, FileEntry paramFileEntry)
  {
    this.a = paramString;
    this.b = paramg;
    this.c = paramFileEntry;
  }
  
  private void b(Context paramContext)
  {
    int i = new h(paramContext, this.a).a(this.c);
    if (i == 1) {
      this.b.c(this.c);
    } else if (i == -1) {
      this.b.b(this.c.mFileName);
    }
  }
  
  /* Error */
  private void c(Context paramContext)
  {
    // Byte code:
    //   0: new 56	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: getfield 25	com/app/system/common/f/a/d:c	Lcom/app/system/common/entity/FileEntry;
    //   8: getfield 45	com/app/system/common/entity/FileEntry:mFileName	Ljava/lang/String;
    //   11: invokespecial 59	java/io/File:<init>	(Ljava/lang/String;)V
    //   14: invokevirtual 63	java/io/File:exists	()Z
    //   17: ifne +19 -> 36
    //   20: aload_0
    //   21: getfield 23	com/app/system/common/f/a/d:b	Lcom/app/system/common/h/g;
    //   24: aload_0
    //   25: getfield 25	com/app/system/common/f/a/d:c	Lcom/app/system/common/entity/FileEntry;
    //   28: getfield 45	com/app/system/common/entity/FileEntry:mFileName	Ljava/lang/String;
    //   31: invokevirtual 48	com/app/system/common/h/g:b	(Ljava/lang/String;)Z
    //   34: pop
    //   35: return
    //   36: aload_1
    //   37: ldc 65
    //   39: iconst_0
    //   40: invokevirtual 71	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   43: ldc 73
    //   45: ldc 75
    //   47: invokeinterface 81 3 0
    //   52: astore_2
    //   53: new 83	com/security/c/a
    //   56: dup
    //   57: invokespecial 84	com/security/c/a:<init>	()V
    //   60: astore_1
    //   61: aload_1
    //   62: aload_2
    //   63: sipush 2121
    //   66: ldc 86
    //   68: ldc 86
    //   70: invokevirtual 89	com/security/c/a:a	(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)Z
    //   73: ifeq +206 -> 279
    //   76: new 91	java/lang/StringBuilder
    //   79: astore_2
    //   80: aload_2
    //   81: invokespecial 92	java/lang/StringBuilder:<init>	()V
    //   84: aload_2
    //   85: ldc 94
    //   87: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   90: pop
    //   91: aload_2
    //   92: aload_0
    //   93: getfield 25	com/app/system/common/f/a/d:c	Lcom/app/system/common/entity/FileEntry;
    //   96: getfield 45	com/app/system/common/entity/FileEntry:mFileName	Ljava/lang/String;
    //   99: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: pop
    //   103: ldc 100
    //   105: aload_2
    //   106: invokevirtual 104	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   109: iconst_0
    //   110: anewarray 4	java/lang/Object
    //   113: invokestatic 110	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   116: new 91	java/lang/StringBuilder
    //   119: astore_2
    //   120: aload_2
    //   121: invokespecial 92	java/lang/StringBuilder:<init>	()V
    //   124: aload_2
    //   125: ldc 112
    //   127: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   130: pop
    //   131: aload_2
    //   132: aload_0
    //   133: getfield 21	com/app/system/common/f/a/d:a	Ljava/lang/String;
    //   136: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   139: pop
    //   140: aload_2
    //   141: ldc 114
    //   143: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   146: pop
    //   147: aload_2
    //   148: aload_0
    //   149: getfield 25	com/app/system/common/f/a/d:c	Lcom/app/system/common/entity/FileEntry;
    //   152: getfield 45	com/app/system/common/entity/FileEntry:mFileName	Ljava/lang/String;
    //   155: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: pop
    //   159: aload_2
    //   160: invokevirtual 104	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   163: astore_2
    //   164: aload_1
    //   165: aload_0
    //   166: getfield 25	com/app/system/common/f/a/d:c	Lcom/app/system/common/entity/FileEntry;
    //   169: getfield 45	com/app/system/common/entity/FileEntry:mFileName	Ljava/lang/String;
    //   172: aload_2
    //   173: invokevirtual 117	com/security/c/a:a	(Ljava/lang/String;Ljava/lang/String;)Lcom/security/c/b;
    //   176: astore_3
    //   177: new 91	java/lang/StringBuilder
    //   180: astore 4
    //   182: aload 4
    //   184: invokespecial 92	java/lang/StringBuilder:<init>	()V
    //   187: aload 4
    //   189: ldc 119
    //   191: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   194: pop
    //   195: aload 4
    //   197: aload_2
    //   198: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   201: pop
    //   202: aload 4
    //   204: ldc 121
    //   206: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   209: pop
    //   210: aload 4
    //   212: aload_3
    //   213: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   216: pop
    //   217: ldc 100
    //   219: aload 4
    //   221: invokevirtual 104	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   224: iconst_0
    //   225: anewarray 4	java/lang/Object
    //   228: invokestatic 110	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   231: getstatic 127	com/app/system/common/f/a/d$1:a	[I
    //   234: aload_3
    //   235: invokevirtual 133	com/security/c/b:ordinal	()I
    //   238: iaload
    //   239: tableswitch	default:+25->264, 1:+28->267, 2:+28->267, 3:+28->267
    //   264: goto +15 -> 279
    //   267: aload_0
    //   268: getfield 23	com/app/system/common/f/a/d:b	Lcom/app/system/common/h/g;
    //   271: aload_0
    //   272: getfield 25	com/app/system/common/f/a/d:c	Lcom/app/system/common/entity/FileEntry;
    //   275: invokevirtual 40	com/app/system/common/h/g:c	(Lcom/app/system/common/entity/FileEntry;)Z
    //   278: pop
    //   279: aload_1
    //   280: invokevirtual 135	com/security/c/a:a	()V
    //   283: goto +67 -> 350
    //   286: astore_2
    //   287: goto +64 -> 351
    //   290: astore_2
    //   291: ldc 100
    //   293: ldc -119
    //   295: iconst_1
    //   296: anewarray 4	java/lang/Object
    //   299: dup
    //   300: iconst_0
    //   301: aload_2
    //   302: aastore
    //   303: invokestatic 139	com/security/d/a:a	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   306: ldc 100
    //   308: aload_2
    //   309: invokevirtual 142	java/lang/NullPointerException:getMessage	()Ljava/lang/String;
    //   312: ldc -119
    //   314: invokestatic 147	com/security/d/b:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   317: goto -38 -> 279
    //   320: astore_2
    //   321: ldc 100
    //   323: ldc -119
    //   325: iconst_1
    //   326: anewarray 4	java/lang/Object
    //   329: dup
    //   330: iconst_0
    //   331: aload_2
    //   332: aastore
    //   333: invokestatic 139	com/security/d/a:a	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   336: ldc 100
    //   338: aload_2
    //   339: invokevirtual 148	java/io/IOException:getMessage	()Ljava/lang/String;
    //   342: ldc -119
    //   344: invokestatic 147	com/security/d/b:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   347: goto -68 -> 279
    //   350: return
    //   351: aload_1
    //   352: invokevirtual 135	com/security/c/a:a	()V
    //   355: aload_2
    //   356: athrow
    //   357: astore_1
    //   358: goto -8 -> 350
    //   361: astore_1
    //   362: goto -7 -> 355
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	365	0	this	d
    //   0	365	1	paramContext	Context
    //   52	146	2	localObject1	Object
    //   286	1	2	localObject2	Object
    //   290	19	2	localNullPointerException	NullPointerException
    //   320	36	2	localIOException	java.io.IOException
    //   176	59	3	localb	com.security.c.b
    //   180	40	4	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   61	264	286	finally
    //   267	279	286	finally
    //   291	317	286	finally
    //   321	347	286	finally
    //   61	264	290	java/lang/NullPointerException
    //   267	279	290	java/lang/NullPointerException
    //   61	264	320	java/io/IOException
    //   267	279	320	java/io/IOException
    //   279	283	357	java/lang/Exception
    //   351	355	361	java/lang/Exception
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
    if ((paramObject != null) && ((paramObject instanceof d)))
    {
      paramObject = (d)paramObject;
      boolean bool2 = bool1;
      if (this.a.equals(((d)paramObject).a))
      {
        bool2 = bool1;
        if (this.c.mFileName.equals(((d)paramObject).c.mFileName)) {
          bool2 = true;
        }
      }
      return bool2;
    }
    return false;
  }
  
  public String toString()
  {
    return String.format("FILE=%s", new Object[] { this.c.mFileName });
  }
}


/* Location:              ~/com/app/system/common/f/a/d.class
 *
 * Reversed by:           J
 */