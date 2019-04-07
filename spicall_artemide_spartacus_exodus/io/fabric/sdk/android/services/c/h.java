package io.fabric.sdk.android.services.c;

import android.content.Context;
import io.fabric.sdk.android.services.b.i;
import io.fabric.sdk.android.services.b.t;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class h
  implements c
{
  private final Context a;
  private final File b;
  private final String c;
  private final File d;
  private t e;
  private File f;
  
  public h(Context paramContext, File paramFile, String paramString1, String paramString2)
  {
    this.a = paramContext;
    this.b = paramFile;
    this.c = paramString2;
    this.d = new File(this.b, paramString1);
    this.e = new t(this.d);
    e();
  }
  
  /* Error */
  private void a(File paramFile1, File paramFile2)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: new 47	java/io/FileInputStream
    //   8: astore 5
    //   10: aload 5
    //   12: aload_1
    //   13: invokespecial 48	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   16: aload 4
    //   18: astore_3
    //   19: aload_0
    //   20: aload_2
    //   21: invokevirtual 51	io/fabric/sdk/android/services/c/h:a	(Ljava/io/File;)Ljava/io/OutputStream;
    //   24: astore_2
    //   25: aload_2
    //   26: astore_3
    //   27: aload 5
    //   29: aload_2
    //   30: sipush 1024
    //   33: newarray <illegal type>
    //   35: invokestatic 56	io/fabric/sdk/android/services/b/i:a	(Ljava/io/InputStream;Ljava/io/OutputStream;[B)V
    //   38: aload 5
    //   40: ldc 58
    //   42: invokestatic 61	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   45: aload_2
    //   46: ldc 63
    //   48: invokestatic 61	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   51: aload_1
    //   52: invokevirtual 67	java/io/File:delete	()Z
    //   55: pop
    //   56: return
    //   57: astore 4
    //   59: aload 5
    //   61: astore_2
    //   62: goto +7 -> 69
    //   65: astore 4
    //   67: aconst_null
    //   68: astore_2
    //   69: aload_2
    //   70: ldc 58
    //   72: invokestatic 61	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   75: aload_3
    //   76: ldc 63
    //   78: invokestatic 61	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   81: aload_1
    //   82: invokevirtual 67	java/io/File:delete	()Z
    //   85: pop
    //   86: aload 4
    //   88: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	89	0	this	h
    //   0	89	1	paramFile1	File
    //   0	89	2	paramFile2	File
    //   1	75	3	localObject1	Object
    //   3	14	4	localObject2	Object
    //   57	1	4	localObject3	Object
    //   65	22	4	localObject4	Object
    //   8	52	5	localFileInputStream	java.io.FileInputStream
    // Exception table:
    //   from	to	target	type
    //   19	25	57	finally
    //   27	38	57	finally
    //   5	16	65	finally
  }
  
  private void e()
  {
    this.f = new File(this.b, this.c);
    if (!this.f.exists()) {
      this.f.mkdirs();
    }
  }
  
  public int a()
  {
    return this.e.a();
  }
  
  public OutputStream a(File paramFile)
  {
    return new FileOutputStream(paramFile);
  }
  
  public List<File> a(int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    File[] arrayOfFile = this.f.listFiles();
    int i = arrayOfFile.length;
    for (int j = 0; j < i; j++)
    {
      localArrayList.add(arrayOfFile[j]);
      if (localArrayList.size() >= paramInt) {
        break;
      }
    }
    return localArrayList;
  }
  
  public void a(String paramString)
  {
    this.e.close();
    a(this.d, new File(this.f, paramString));
    this.e = new t(this.d);
  }
  
  public void a(List<File> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      File localFile = (File)paramList.next();
      i.a(this.a, String.format("deleting sent analytics file %s", new Object[] { localFile.getName() }));
      localFile.delete();
    }
  }
  
  public void a(byte[] paramArrayOfByte)
  {
    this.e.a(paramArrayOfByte);
  }
  
  public boolean a(int paramInt1, int paramInt2)
  {
    return this.e.a(paramInt1, paramInt2);
  }
  
  public boolean b()
  {
    return this.e.b();
  }
  
  public List<File> c()
  {
    return Arrays.asList(this.f.listFiles());
  }
  
  public void d()
  {
    try
    {
      this.e.close();
      this.d.delete();
      return;
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
  }
}


/* Location:              ~/io/fabric/sdk/android/services/c/h.class
 *
 * Reversed by:           J
 */