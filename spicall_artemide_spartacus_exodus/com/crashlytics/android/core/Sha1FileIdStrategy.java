package com.crashlytics.android.core;

import java.io.File;

class Sha1FileIdStrategy
  implements BinaryImagesConverter.FileIdStrategy
{
  /* Error */
  private static String getFileSHA(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 15	java/io/BufferedInputStream
    //   5: astore_2
    //   6: new 17	java/io/FileInputStream
    //   9: astore_3
    //   10: aload_3
    //   11: aload_0
    //   12: invokespecial 20	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   15: aload_2
    //   16: aload_3
    //   17: invokespecial 23	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   20: aload_2
    //   21: invokestatic 29	io/fabric/sdk/android/services/b/i:b	(Ljava/io/InputStream;)Ljava/lang/String;
    //   24: astore_0
    //   25: aload_2
    //   26: invokestatic 33	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;)V
    //   29: aload_0
    //   30: areturn
    //   31: astore_0
    //   32: aload_2
    //   33: astore_1
    //   34: goto +4 -> 38
    //   37: astore_0
    //   38: aload_1
    //   39: invokestatic 33	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;)V
    //   42: aload_0
    //   43: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	44	0	paramString	String
    //   1	38	1	localObject	Object
    //   5	28	2	localBufferedInputStream	java.io.BufferedInputStream
    //   9	8	3	localFileInputStream	java.io.FileInputStream
    // Exception table:
    //   from	to	target	type
    //   20	25	31	finally
    //   2	20	37	finally
  }
  
  public String createId(File paramFile)
  {
    return getFileSHA(paramFile.getPath());
  }
}


/* Location:              ~/com/crashlytics/android/core/Sha1FileIdStrategy.class
 *
 * Reversed by:           J
 */