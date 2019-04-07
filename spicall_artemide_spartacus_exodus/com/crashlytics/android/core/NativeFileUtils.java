package com.crashlytics.android.core;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

final class NativeFileUtils
{
  private static byte[] binaryImagesJsonFromBinaryLibsFile(File paramFile, Context paramContext)
  {
    paramFile = readFile(paramFile);
    if ((paramFile != null) && (paramFile.length != 0)) {
      return processBinaryImages(paramContext, new String(paramFile));
    }
    return null;
  }
  
  static byte[] binaryImagesJsonFromDirectory(File paramFile, Context paramContext)
  {
    File localFile = filter(paramFile, ".maps");
    if (localFile != null) {
      return binaryImagesJsonFromMapsFile(localFile, paramContext);
    }
    paramFile = filter(paramFile, ".binary_libs");
    if (paramFile != null) {
      return binaryImagesJsonFromBinaryLibsFile(paramFile, paramContext);
    }
    return null;
  }
  
  /* Error */
  private static byte[] binaryImagesJsonFromMapsFile(File paramFile, Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 44	java/io/File:exists	()Z
    //   4: ifne +5 -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: new 46	java/io/BufferedReader
    //   12: astore_2
    //   13: new 48	java/io/FileReader
    //   16: astore_3
    //   17: aload_3
    //   18: aload_0
    //   19: invokespecial 51	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   22: aload_2
    //   23: aload_3
    //   24: invokespecial 54	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   27: new 56	com/crashlytics/android/core/BinaryImagesConverter
    //   30: astore_0
    //   31: new 58	com/crashlytics/android/core/Sha1FileIdStrategy
    //   34: astore_3
    //   35: aload_3
    //   36: invokespecial 59	com/crashlytics/android/core/Sha1FileIdStrategy:<init>	()V
    //   39: aload_0
    //   40: aload_1
    //   41: aload_3
    //   42: invokespecial 62	com/crashlytics/android/core/BinaryImagesConverter:<init>	(Landroid/content/Context;Lcom/crashlytics/android/core/BinaryImagesConverter$FileIdStrategy;)V
    //   45: aload_0
    //   46: aload_2
    //   47: invokevirtual 66	com/crashlytics/android/core/BinaryImagesConverter:convert	(Ljava/io/BufferedReader;)[B
    //   50: astore_0
    //   51: aload_2
    //   52: invokestatic 72	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;)V
    //   55: aload_0
    //   56: areturn
    //   57: astore_1
    //   58: aload_2
    //   59: astore_0
    //   60: goto +6 -> 66
    //   63: astore_1
    //   64: aconst_null
    //   65: astore_0
    //   66: aload_0
    //   67: invokestatic 72	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;)V
    //   70: aload_1
    //   71: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	72	0	paramFile	File
    //   0	72	1	paramContext	Context
    //   12	47	2	localBufferedReader	java.io.BufferedReader
    //   16	26	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   27	51	57	finally
    //   9	27	63	finally
  }
  
  private static File filter(File paramFile, String paramString)
  {
    for (paramFile : paramFile.listFiles()) {
      if (paramFile.getName().endsWith(paramString)) {
        return paramFile;
      }
    }
    return null;
  }
  
  static byte[] metadataJsonFromDirectory(File paramFile)
  {
    paramFile = filter(paramFile, ".device_info");
    if (paramFile == null) {
      paramFile = null;
    } else {
      paramFile = readFile(paramFile);
    }
    return paramFile;
  }
  
  static byte[] minidumpFromDirectory(File paramFile)
  {
    paramFile = filter(paramFile, ".dmp");
    if (paramFile == null) {
      paramFile = new byte[0];
    } else {
      paramFile = minidumpFromFile(paramFile);
    }
    return paramFile;
  }
  
  private static byte[] minidumpFromFile(File paramFile)
  {
    return readFile(paramFile);
  }
  
  private static byte[] processBinaryImages(Context paramContext, String paramString)
  {
    return new BinaryImagesConverter(paramContext, new Sha1FileIdStrategy()).convert(paramString);
  }
  
  private static byte[] readBytes(InputStream paramInputStream)
  {
    byte[] arrayOfByte = new byte['Ѐ'];
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    for (;;)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i == -1) {
        break;
      }
      localByteArrayOutputStream.write(arrayOfByte, 0, i);
    }
    return localByteArrayOutputStream.toByteArray();
  }
  
  /* Error */
  static byte[] readFile(File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 121	java/io/FileInputStream
    //   5: astore_2
    //   6: aload_2
    //   7: aload_0
    //   8: invokespecial 122	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   11: aload_2
    //   12: invokestatic 124	com/crashlytics/android/core/NativeFileUtils:readBytes	(Ljava/io/InputStream;)[B
    //   15: astore_0
    //   16: aload_2
    //   17: invokestatic 72	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;)V
    //   20: aload_0
    //   21: areturn
    //   22: astore_0
    //   23: goto +6 -> 29
    //   26: astore_0
    //   27: aload_1
    //   28: astore_2
    //   29: aload_2
    //   30: invokestatic 72	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;)V
    //   33: aload_0
    //   34: athrow
    //   35: astore_0
    //   36: aconst_null
    //   37: astore_2
    //   38: aload_2
    //   39: invokestatic 72	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;)V
    //   42: aconst_null
    //   43: areturn
    //   44: astore_0
    //   45: aconst_null
    //   46: astore_2
    //   47: aload_2
    //   48: invokestatic 72	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;)V
    //   51: aconst_null
    //   52: areturn
    //   53: astore_0
    //   54: goto -7 -> 47
    //   57: astore_0
    //   58: goto -20 -> 38
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	61	0	paramFile	File
    //   1	27	1	localObject1	Object
    //   5	43	2	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   11	16	22	finally
    //   2	11	26	finally
    //   2	11	35	java/io/IOException
    //   2	11	44	java/io/FileNotFoundException
    //   11	16	53	java/io/FileNotFoundException
    //   11	16	57	java/io/IOException
  }
}


/* Location:              ~/com/crashlytics/android/core/NativeFileUtils.class
 *
 * Reversed by:           J
 */