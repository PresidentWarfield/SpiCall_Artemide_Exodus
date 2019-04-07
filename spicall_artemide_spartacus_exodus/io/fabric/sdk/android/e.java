package io.fabric.sdk.android;

import android.os.SystemClock;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class e
  implements Callable<Map<String, j>>
{
  final String a;
  
  e(String paramString)
  {
    this.a = paramString;
  }
  
  /* Error */
  private j a(ZipEntry paramZipEntry, ZipFile paramZipFile)
  {
    // Byte code:
    //   0: aload_2
    //   1: aload_1
    //   2: invokevirtual 26	java/util/zip/ZipFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   5: astore_3
    //   6: aload_3
    //   7: astore_2
    //   8: new 28	java/util/Properties
    //   11: astore 4
    //   13: aload_3
    //   14: astore_2
    //   15: aload 4
    //   17: invokespecial 29	java/util/Properties:<init>	()V
    //   20: aload_3
    //   21: astore_2
    //   22: aload 4
    //   24: aload_3
    //   25: invokevirtual 33	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   28: aload_3
    //   29: astore_2
    //   30: aload 4
    //   32: ldc 35
    //   34: invokevirtual 39	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   37: astore 5
    //   39: aload_3
    //   40: astore_2
    //   41: aload 4
    //   43: ldc 41
    //   45: invokevirtual 39	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   48: astore 6
    //   50: aload_3
    //   51: astore_2
    //   52: aload 4
    //   54: ldc 43
    //   56: invokevirtual 39	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   59: astore 4
    //   61: aload_3
    //   62: astore_2
    //   63: aload 5
    //   65: invokestatic 49	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   68: ifne +37 -> 105
    //   71: aload_3
    //   72: astore_2
    //   73: aload 6
    //   75: invokestatic 49	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   78: ifne +27 -> 105
    //   81: aload_3
    //   82: astore_2
    //   83: new 51	io/fabric/sdk/android/j
    //   86: dup
    //   87: aload 5
    //   89: aload 6
    //   91: aload 4
    //   93: invokespecial 54	io/fabric/sdk/android/j:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   96: astore 5
    //   98: aload_3
    //   99: invokestatic 59	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;)V
    //   102: aload 5
    //   104: areturn
    //   105: aload_3
    //   106: astore_2
    //   107: new 61	java/lang/IllegalStateException
    //   110: astore 6
    //   112: aload_3
    //   113: astore_2
    //   114: new 63	java/lang/StringBuilder
    //   117: astore 5
    //   119: aload_3
    //   120: astore_2
    //   121: aload 5
    //   123: invokespecial 64	java/lang/StringBuilder:<init>	()V
    //   126: aload_3
    //   127: astore_2
    //   128: aload 5
    //   130: ldc 66
    //   132: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: pop
    //   136: aload_3
    //   137: astore_2
    //   138: aload 5
    //   140: aload_1
    //   141: invokevirtual 76	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   144: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   147: pop
    //   148: aload_3
    //   149: astore_2
    //   150: aload 6
    //   152: aload 5
    //   154: invokevirtual 79	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   157: invokespecial 81	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   160: aload_3
    //   161: astore_2
    //   162: aload 6
    //   164: athrow
    //   165: astore_1
    //   166: goto +85 -> 251
    //   169: astore 5
    //   171: goto +13 -> 184
    //   174: astore_1
    //   175: aconst_null
    //   176: astore_2
    //   177: goto +74 -> 251
    //   180: astore 5
    //   182: aconst_null
    //   183: astore_3
    //   184: aload_3
    //   185: astore_2
    //   186: invokestatic 87	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   189: astore 4
    //   191: aload_3
    //   192: astore_2
    //   193: new 63	java/lang/StringBuilder
    //   196: astore 6
    //   198: aload_3
    //   199: astore_2
    //   200: aload 6
    //   202: invokespecial 64	java/lang/StringBuilder:<init>	()V
    //   205: aload_3
    //   206: astore_2
    //   207: aload 6
    //   209: ldc 89
    //   211: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   214: pop
    //   215: aload_3
    //   216: astore_2
    //   217: aload 6
    //   219: aload_1
    //   220: invokevirtual 76	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   223: invokevirtual 70	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   226: pop
    //   227: aload_3
    //   228: astore_2
    //   229: aload 4
    //   231: ldc 91
    //   233: aload 6
    //   235: invokevirtual 79	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   238: aload 5
    //   240: invokeinterface 97 4 0
    //   245: aload_3
    //   246: invokestatic 59	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;)V
    //   249: aconst_null
    //   250: areturn
    //   251: aload_2
    //   252: invokestatic 59	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;)V
    //   255: aload_1
    //   256: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	257	0	this	e
    //   0	257	1	paramZipEntry	ZipEntry
    //   0	257	2	paramZipFile	ZipFile
    //   5	241	3	localInputStream	java.io.InputStream
    //   11	219	4	localObject1	Object
    //   37	116	5	localObject2	Object
    //   169	1	5	localIOException1	IOException
    //   180	59	5	localIOException2	IOException
    //   48	186	6	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   8	13	165	finally
    //   15	20	165	finally
    //   22	28	165	finally
    //   30	39	165	finally
    //   41	50	165	finally
    //   52	61	165	finally
    //   63	71	165	finally
    //   73	81	165	finally
    //   83	98	165	finally
    //   107	112	165	finally
    //   114	119	165	finally
    //   121	126	165	finally
    //   128	136	165	finally
    //   138	148	165	finally
    //   150	160	165	finally
    //   162	165	165	finally
    //   186	191	165	finally
    //   193	198	165	finally
    //   200	205	165	finally
    //   207	215	165	finally
    //   217	227	165	finally
    //   229	245	165	finally
    //   8	13	169	java/io/IOException
    //   15	20	169	java/io/IOException
    //   22	28	169	java/io/IOException
    //   30	39	169	java/io/IOException
    //   41	50	169	java/io/IOException
    //   52	61	169	java/io/IOException
    //   63	71	169	java/io/IOException
    //   73	81	169	java/io/IOException
    //   83	98	169	java/io/IOException
    //   107	112	169	java/io/IOException
    //   114	119	169	java/io/IOException
    //   121	126	169	java/io/IOException
    //   128	136	169	java/io/IOException
    //   138	148	169	java/io/IOException
    //   150	160	169	java/io/IOException
    //   162	165	169	java/io/IOException
    //   0	6	174	finally
    //   0	6	180	java/io/IOException
  }
  
  private Map<String, j> c()
  {
    HashMap localHashMap = new HashMap();
    try
    {
      Class.forName("com.google.android.gms.ads.AdView");
      j localj = new io/fabric/sdk/android/j;
      localj.<init>("com.google.firebase.firebase-ads", "0.0.0", "binary");
      localHashMap.put(localj.a(), localj);
      c.g().b("Fabric", "Found kit: com.google.firebase.firebase-ads");
      return localHashMap;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  private Map<String, j> d()
  {
    HashMap localHashMap = new HashMap();
    ZipFile localZipFile = b();
    Enumeration localEnumeration = localZipFile.entries();
    while (localEnumeration.hasMoreElements())
    {
      Object localObject = (ZipEntry)localEnumeration.nextElement();
      if ((((ZipEntry)localObject).getName().startsWith("fabric/")) && (((ZipEntry)localObject).getName().length() > 7))
      {
        localObject = a((ZipEntry)localObject, localZipFile);
        if (localObject != null)
        {
          localHashMap.put(((j)localObject).a(), localObject);
          c.g().b("Fabric", String.format("Found kit:[%s] version:[%s]", new Object[] { ((j)localObject).a(), ((j)localObject).b() }));
        }
      }
    }
    if (localZipFile != null) {}
    try
    {
      localZipFile.close();
      return localHashMap;
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
  }
  
  public Map<String, j> a()
  {
    HashMap localHashMap = new HashMap();
    long l = SystemClock.elapsedRealtime();
    localHashMap.putAll(c());
    localHashMap.putAll(d());
    k localk = c.g();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("finish scanning in ");
    localStringBuilder.append(SystemClock.elapsedRealtime() - l);
    localk.b("Fabric", localStringBuilder.toString());
    return localHashMap;
  }
  
  protected ZipFile b()
  {
    return new ZipFile(this.a);
  }
}


/* Location:              ~/io/fabric/sdk/android/e.class
 *
 * Reversed by:           J
 */