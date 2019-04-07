package com.app.system.common.f.a;

import android.content.Context;
import com.app.system.common.entity.FileEntry;

public class f
  implements e
{
  private final FileEntry a;
  
  public f(FileEntry paramFileEntry)
  {
    this.a = paramFileEntry;
  }
  
  /* Error */
  private void b(Context paramContext)
  {
    // Byte code:
    //   0: new 26	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: getfield 17	com/app/system/common/f/a/f:a	Lcom/app/system/common/entity/FileEntry;
    //   8: getfield 32	com/app/system/common/entity/FileEntry:mFileName	Ljava/lang/String;
    //   11: invokespecial 35	java/io/File:<init>	(Ljava/lang/String;)V
    //   14: astore_2
    //   15: aload_2
    //   16: invokevirtual 39	java/io/File:exists	()Z
    //   19: ifne +25 -> 44
    //   22: ldc 41
    //   24: ldc 43
    //   26: iconst_1
    //   27: anewarray 4	java/lang/Object
    //   30: dup
    //   31: iconst_0
    //   32: aload_0
    //   33: getfield 17	com/app/system/common/f/a/f:a	Lcom/app/system/common/entity/FileEntry;
    //   36: getfield 32	com/app/system/common/entity/FileEntry:mFileName	Ljava/lang/String;
    //   39: aastore
    //   40: invokestatic 48	com/security/d/a:b	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   43: return
    //   44: aload_1
    //   45: ldc 50
    //   47: iconst_0
    //   48: invokevirtual 56	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   51: ldc 58
    //   53: ldc 60
    //   55: invokeinterface 66 3 0
    //   60: astore_3
    //   61: aload_1
    //   62: invokestatic 72	com/app/system/common/h:c	(Landroid/content/Context;)Ljava/lang/String;
    //   65: astore 4
    //   67: new 74	com/security/c/a
    //   70: dup
    //   71: invokespecial 75	com/security/c/a:<init>	()V
    //   74: astore_1
    //   75: aload_1
    //   76: aload_3
    //   77: sipush 2121
    //   80: ldc 77
    //   82: ldc 77
    //   84: invokevirtual 80	com/security/c/a:a	(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)Z
    //   87: ifeq +213 -> 300
    //   90: new 82	java/lang/StringBuilder
    //   93: astore_3
    //   94: aload_3
    //   95: invokespecial 83	java/lang/StringBuilder:<init>	()V
    //   98: aload_3
    //   99: ldc 85
    //   101: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   104: pop
    //   105: aload_3
    //   106: aload 4
    //   108: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: pop
    //   112: aload_3
    //   113: ldc 91
    //   115: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: pop
    //   119: aload_3
    //   120: aload_2
    //   121: invokevirtual 95	java/io/File:getName	()Ljava/lang/String;
    //   124: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   127: pop
    //   128: aload_3
    //   129: invokevirtual 98	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   132: astore 4
    //   134: new 82	java/lang/StringBuilder
    //   137: astore_3
    //   138: aload_3
    //   139: invokespecial 83	java/lang/StringBuilder:<init>	()V
    //   142: aload_3
    //   143: ldc 100
    //   145: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   148: pop
    //   149: aload_3
    //   150: aload_0
    //   151: getfield 17	com/app/system/common/f/a/f:a	Lcom/app/system/common/entity/FileEntry;
    //   154: getfield 32	com/app/system/common/entity/FileEntry:mFileName	Ljava/lang/String;
    //   157: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: pop
    //   161: aload_3
    //   162: ldc 102
    //   164: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: pop
    //   168: aload_3
    //   169: aload 4
    //   171: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   174: pop
    //   175: ldc 41
    //   177: aload_3
    //   178: invokevirtual 98	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   181: iconst_0
    //   182: anewarray 4	java/lang/Object
    //   185: invokestatic 105	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   188: aload_1
    //   189: aload_0
    //   190: getfield 17	com/app/system/common/f/a/f:a	Lcom/app/system/common/entity/FileEntry;
    //   193: getfield 32	com/app/system/common/entity/FileEntry:mFileName	Ljava/lang/String;
    //   196: aload 4
    //   198: invokevirtual 108	com/security/c/a:a	(Ljava/lang/String;Ljava/lang/String;)Lcom/security/c/b;
    //   201: astore_3
    //   202: new 82	java/lang/StringBuilder
    //   205: astore 5
    //   207: aload 5
    //   209: invokespecial 83	java/lang/StringBuilder:<init>	()V
    //   212: aload 5
    //   214: ldc 110
    //   216: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   219: pop
    //   220: aload 5
    //   222: aload 4
    //   224: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   227: pop
    //   228: aload 5
    //   230: ldc 112
    //   232: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   235: pop
    //   236: aload 5
    //   238: aload_3
    //   239: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   242: pop
    //   243: ldc 41
    //   245: aload 5
    //   247: invokevirtual 98	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   250: iconst_0
    //   251: anewarray 4	java/lang/Object
    //   254: invokestatic 105	com/security/d/a:d	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   257: getstatic 118	com/app/system/common/f/a/f$1:a	[I
    //   260: aload_3
    //   261: invokevirtual 124	com/security/c/b:ordinal	()I
    //   264: iaload
    //   265: tableswitch	default:+27->292, 1:+30->295, 2:+30->295, 3:+30->295
    //   292: goto +8 -> 300
    //   295: aload_2
    //   296: invokevirtual 127	java/io/File:delete	()Z
    //   299: pop
    //   300: aload_1
    //   301: invokevirtual 129	com/security/c/a:a	()V
    //   304: goto +45 -> 349
    //   307: astore_2
    //   308: goto +42 -> 350
    //   311: astore_2
    //   312: ldc 41
    //   314: ldc -125
    //   316: iconst_1
    //   317: anewarray 4	java/lang/Object
    //   320: dup
    //   321: iconst_0
    //   322: aload_2
    //   323: aastore
    //   324: invokestatic 133	com/security/d/a:a	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   327: goto -27 -> 300
    //   330: astore_2
    //   331: ldc 41
    //   333: ldc -125
    //   335: iconst_1
    //   336: anewarray 4	java/lang/Object
    //   339: dup
    //   340: iconst_0
    //   341: aload_2
    //   342: aastore
    //   343: invokestatic 133	com/security/d/a:a	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   346: goto -46 -> 300
    //   349: return
    //   350: aload_1
    //   351: invokevirtual 129	com/security/c/a:a	()V
    //   354: aload_2
    //   355: athrow
    //   356: astore_1
    //   357: goto -8 -> 349
    //   360: astore_1
    //   361: goto -7 -> 354
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	364	0	this	f
    //   0	364	1	paramContext	Context
    //   14	282	2	localFile	java.io.File
    //   307	1	2	localObject1	Object
    //   311	12	2	localException	Exception
    //   330	25	2	localIOException	java.io.IOException
    //   60	201	3	localObject2	Object
    //   65	158	4	str	String
    //   205	41	5	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   75	292	307	finally
    //   295	300	307	finally
    //   312	327	307	finally
    //   331	346	307	finally
    //   75	292	311	java/lang/Exception
    //   295	300	311	java/lang/Exception
    //   75	292	330	java/io/IOException
    //   295	300	330	java/io/IOException
    //   300	304	356	java/lang/Exception
    //   350	354	360	java/lang/Exception
  }
  
  public void a(Context paramContext)
  {
    b(paramContext);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject != null) && ((paramObject instanceof f)))
    {
      paramObject = (f)paramObject;
      return this.a.mFileName.equals(((f)paramObject).a.mFileName);
    }
    return false;
  }
  
  public String toString()
  {
    return String.format("LOGFILE=%s", new Object[] { this.a.mFileName });
  }
}


/* Location:              ~/com/app/system/common/f/a/f.class
 *
 * Reversed by:           J
 */