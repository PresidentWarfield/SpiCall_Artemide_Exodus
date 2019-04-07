package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class TypefaceCompatUtil
{
  private static final String CACHE_FILE_PREFIX = ".font";
  private static final String TAG = "TypefaceCompatUtil";
  
  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable != null) {}
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException paramCloseable)
    {
      for (;;) {}
    }
  }
  
  public static ByteBuffer copyToDirectBuffer(Context paramContext, Resources paramResources, int paramInt)
  {
    paramContext = getTempFile(paramContext);
    if (paramContext == null) {
      return null;
    }
    try
    {
      boolean bool = copyToFile(paramContext, paramResources, paramInt);
      if (!bool) {
        return null;
      }
      paramResources = mmap(paramContext);
      return paramResources;
    }
    finally
    {
      paramContext.delete();
    }
  }
  
  /* Error */
  public static boolean copyToFile(File paramFile, Resources paramResources, int paramInt)
  {
    // Byte code:
    //   0: aload_1
    //   1: iload_2
    //   2: invokevirtual 51	android/content/res/Resources:openRawResource	(I)Ljava/io/InputStream;
    //   5: astore_1
    //   6: aload_0
    //   7: aload_1
    //   8: invokestatic 54	android/support/v4/graphics/TypefaceCompatUtil:copyToFile	(Ljava/io/File;Ljava/io/InputStream;)Z
    //   11: istore_3
    //   12: aload_1
    //   13: invokestatic 56	android/support/v4/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   16: iload_3
    //   17: ireturn
    //   18: astore_0
    //   19: goto +6 -> 25
    //   22: astore_0
    //   23: aconst_null
    //   24: astore_1
    //   25: aload_1
    //   26: invokestatic 56	android/support/v4/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   29: aload_0
    //   30: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	31	0	paramFile	File
    //   0	31	1	paramResources	Resources
    //   0	31	2	paramInt	int
    //   11	6	3	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   6	12	18	finally
    //   0	6	22	finally
  }
  
  /* Error */
  public static boolean copyToFile(File paramFile, java.io.InputStream paramInputStream)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: aload_3
    //   5: astore 4
    //   7: new 58	java/io/FileOutputStream
    //   10: astore 5
    //   12: aload_3
    //   13: astore 4
    //   15: aload 5
    //   17: aload_0
    //   18: iconst_0
    //   19: invokespecial 61	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
    //   22: sipush 1024
    //   25: newarray <illegal type>
    //   27: astore_0
    //   28: aload_1
    //   29: aload_0
    //   30: invokevirtual 67	java/io/InputStream:read	([B)I
    //   33: istore 6
    //   35: iload 6
    //   37: iconst_m1
    //   38: if_icmpeq +15 -> 53
    //   41: aload 5
    //   43: aload_0
    //   44: iconst_0
    //   45: iload 6
    //   47: invokevirtual 71	java/io/FileOutputStream:write	([BII)V
    //   50: goto -22 -> 28
    //   53: aload 5
    //   55: invokestatic 56	android/support/v4/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   58: iconst_1
    //   59: ireturn
    //   60: astore_0
    //   61: aload 5
    //   63: astore 4
    //   65: goto +77 -> 142
    //   68: astore_1
    //   69: aload 5
    //   71: astore_0
    //   72: goto +10 -> 82
    //   75: astore_0
    //   76: goto +66 -> 142
    //   79: astore_1
    //   80: aload_2
    //   81: astore_0
    //   82: aload_0
    //   83: astore 4
    //   85: new 73	java/lang/StringBuilder
    //   88: astore 5
    //   90: aload_0
    //   91: astore 4
    //   93: aload 5
    //   95: invokespecial 74	java/lang/StringBuilder:<init>	()V
    //   98: aload_0
    //   99: astore 4
    //   101: aload 5
    //   103: ldc 76
    //   105: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   108: pop
    //   109: aload_0
    //   110: astore 4
    //   112: aload 5
    //   114: aload_1
    //   115: invokevirtual 84	java/io/IOException:getMessage	()Ljava/lang/String;
    //   118: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: pop
    //   122: aload_0
    //   123: astore 4
    //   125: ldc 11
    //   127: aload 5
    //   129: invokevirtual 87	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   132: invokestatic 93	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   135: pop
    //   136: aload_0
    //   137: invokestatic 56	android/support/v4/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   140: iconst_0
    //   141: ireturn
    //   142: aload 4
    //   144: invokestatic 56	android/support/v4/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   147: aload_0
    //   148: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	149	0	paramFile	File
    //   0	149	1	paramInputStream	java.io.InputStream
    //   1	80	2	localObject1	Object
    //   3	10	3	localObject2	Object
    //   5	138	4	localObject3	Object
    //   10	118	5	localObject4	Object
    //   33	13	6	i	int
    // Exception table:
    //   from	to	target	type
    //   22	28	60	finally
    //   28	35	60	finally
    //   41	50	60	finally
    //   22	28	68	java/io/IOException
    //   28	35	68	java/io/IOException
    //   41	50	68	java/io/IOException
    //   7	12	75	finally
    //   15	22	75	finally
    //   85	90	75	finally
    //   93	98	75	finally
    //   101	109	75	finally
    //   112	122	75	finally
    //   125	136	75	finally
    //   7	12	79	java/io/IOException
    //   15	22	79	java/io/IOException
  }
  
  public static File getTempFile(Context paramContext)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(".font");
    ((StringBuilder)localObject1).append(Process.myPid());
    ((StringBuilder)localObject1).append("-");
    ((StringBuilder)localObject1).append(Process.myTid());
    ((StringBuilder)localObject1).append("-");
    localObject1 = ((StringBuilder)localObject1).toString();
    for (int i = 0; i < 100; i++)
    {
      File localFile = paramContext.getCacheDir();
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append(i);
      localObject2 = new File(localFile, ((StringBuilder)localObject2).toString());
      try
      {
        boolean bool = ((File)localObject2).createNewFile();
        if (bool) {
          return (File)localObject2;
        }
      }
      catch (IOException localIOException)
      {
        for (;;) {}
      }
    }
    return null;
  }
  
  /* Error */
  public static ByteBuffer mmap(Context paramContext, android.os.CancellationSignal paramCancellationSignal, android.net.Uri paramUri)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 126	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   4: astore_0
    //   5: aload_0
    //   6: aload_2
    //   7: ldc -128
    //   9: aload_1
    //   10: invokevirtual 134	android/content/ContentResolver:openFileDescriptor	(Landroid/net/Uri;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/os/ParcelFileDescriptor;
    //   13: astore_2
    //   14: new 136	java/io/FileInputStream
    //   17: astore_3
    //   18: aload_3
    //   19: aload_2
    //   20: invokevirtual 142	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
    //   23: invokespecial 145	java/io/FileInputStream:<init>	(Ljava/io/FileDescriptor;)V
    //   26: aload_3
    //   27: invokevirtual 149	java/io/FileInputStream:getChannel	()Ljava/nio/channels/FileChannel;
    //   30: astore_0
    //   31: aload_0
    //   32: invokevirtual 155	java/nio/channels/FileChannel:size	()J
    //   35: lstore 4
    //   37: aload_0
    //   38: getstatic 161	java/nio/channels/FileChannel$MapMode:READ_ONLY	Ljava/nio/channels/FileChannel$MapMode;
    //   41: lconst_0
    //   42: lload 4
    //   44: invokevirtual 165	java/nio/channels/FileChannel:map	(Ljava/nio/channels/FileChannel$MapMode;JJ)Ljava/nio/MappedByteBuffer;
    //   47: astore_0
    //   48: aload_3
    //   49: invokevirtual 166	java/io/FileInputStream:close	()V
    //   52: aload_2
    //   53: ifnull +7 -> 60
    //   56: aload_2
    //   57: invokevirtual 167	android/os/ParcelFileDescriptor:close	()V
    //   60: aload_0
    //   61: areturn
    //   62: astore_0
    //   63: aconst_null
    //   64: astore_1
    //   65: goto +7 -> 72
    //   68: astore_1
    //   69: aload_1
    //   70: athrow
    //   71: astore_0
    //   72: aload_1
    //   73: ifnull +19 -> 92
    //   76: aload_3
    //   77: invokevirtual 166	java/io/FileInputStream:close	()V
    //   80: goto +16 -> 96
    //   83: astore_3
    //   84: aload_1
    //   85: aload_3
    //   86: invokevirtual 171	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   89: goto +7 -> 96
    //   92: aload_3
    //   93: invokevirtual 166	java/io/FileInputStream:close	()V
    //   96: aload_0
    //   97: athrow
    //   98: astore_1
    //   99: aconst_null
    //   100: astore_0
    //   101: goto +7 -> 108
    //   104: astore_0
    //   105: aload_0
    //   106: athrow
    //   107: astore_1
    //   108: aload_2
    //   109: ifnull +27 -> 136
    //   112: aload_0
    //   113: ifnull +19 -> 132
    //   116: aload_2
    //   117: invokevirtual 167	android/os/ParcelFileDescriptor:close	()V
    //   120: goto +16 -> 136
    //   123: astore_2
    //   124: aload_0
    //   125: aload_2
    //   126: invokevirtual 171	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   129: goto +7 -> 136
    //   132: aload_2
    //   133: invokevirtual 167	android/os/ParcelFileDescriptor:close	()V
    //   136: aload_1
    //   137: athrow
    //   138: astore_0
    //   139: aconst_null
    //   140: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	141	0	paramContext	Context
    //   0	141	1	paramCancellationSignal	android.os.CancellationSignal
    //   0	141	2	paramUri	android.net.Uri
    //   17	60	3	localFileInputStream	java.io.FileInputStream
    //   83	10	3	localThrowable	Throwable
    //   35	8	4	l	long
    // Exception table:
    //   from	to	target	type
    //   26	48	62	finally
    //   26	48	68	java/lang/Throwable
    //   69	71	71	finally
    //   76	80	83	java/lang/Throwable
    //   14	26	98	finally
    //   48	52	98	finally
    //   76	80	98	finally
    //   84	89	98	finally
    //   92	96	98	finally
    //   96	98	98	finally
    //   14	26	104	java/lang/Throwable
    //   48	52	104	java/lang/Throwable
    //   84	89	104	java/lang/Throwable
    //   92	96	104	java/lang/Throwable
    //   96	98	104	java/lang/Throwable
    //   105	107	107	finally
    //   116	120	123	java/lang/Throwable
    //   5	14	138	java/io/IOException
    //   56	60	138	java/io/IOException
    //   116	120	138	java/io/IOException
    //   124	129	138	java/io/IOException
    //   132	136	138	java/io/IOException
    //   136	138	138	java/io/IOException
  }
  
  /* Error */
  private static ByteBuffer mmap(File paramFile)
  {
    // Byte code:
    //   0: new 136	java/io/FileInputStream
    //   3: astore_1
    //   4: aload_1
    //   5: aload_0
    //   6: invokespecial 174	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   9: aload_1
    //   10: invokevirtual 149	java/io/FileInputStream:getChannel	()Ljava/nio/channels/FileChannel;
    //   13: astore_0
    //   14: aload_0
    //   15: invokevirtual 155	java/nio/channels/FileChannel:size	()J
    //   18: lstore_2
    //   19: aload_0
    //   20: getstatic 161	java/nio/channels/FileChannel$MapMode:READ_ONLY	Ljava/nio/channels/FileChannel$MapMode;
    //   23: lconst_0
    //   24: lload_2
    //   25: invokevirtual 165	java/nio/channels/FileChannel:map	(Ljava/nio/channels/FileChannel$MapMode;JJ)Ljava/nio/MappedByteBuffer;
    //   28: astore_0
    //   29: aload_1
    //   30: invokevirtual 166	java/io/FileInputStream:close	()V
    //   33: aload_0
    //   34: areturn
    //   35: astore 4
    //   37: aconst_null
    //   38: astore_0
    //   39: goto +8 -> 47
    //   42: astore_0
    //   43: aload_0
    //   44: athrow
    //   45: astore 4
    //   47: aload_0
    //   48: ifnull +19 -> 67
    //   51: aload_1
    //   52: invokevirtual 166	java/io/FileInputStream:close	()V
    //   55: goto +16 -> 71
    //   58: astore_1
    //   59: aload_0
    //   60: aload_1
    //   61: invokevirtual 171	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   64: goto +7 -> 71
    //   67: aload_1
    //   68: invokevirtual 166	java/io/FileInputStream:close	()V
    //   71: aload 4
    //   73: athrow
    //   74: astore_0
    //   75: aconst_null
    //   76: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	77	0	paramFile	File
    //   3	49	1	localFileInputStream	java.io.FileInputStream
    //   58	10	1	localThrowable	Throwable
    //   18	7	2	l	long
    //   35	1	4	localObject1	Object
    //   45	27	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   9	29	35	finally
    //   9	29	42	java/lang/Throwable
    //   43	45	45	finally
    //   51	55	58	java/lang/Throwable
    //   0	9	74	java/io/IOException
    //   29	33	74	java/io/IOException
    //   51	55	74	java/io/IOException
    //   59	64	74	java/io/IOException
    //   67	71	74	java/io/IOException
    //   71	74	74	java/io/IOException
  }
}


/* Location:              ~/android/support/v4/graphics/TypefaceCompatUtil.class
 *
 * Reversed by:           J
 */