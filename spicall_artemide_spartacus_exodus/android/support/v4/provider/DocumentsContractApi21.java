package android.support.v4.provider;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;

class DocumentsContractApi21
{
  private static final String TAG = "DocumentFile";
  
  private static void closeQuietly(AutoCloseable paramAutoCloseable)
  {
    if (paramAutoCloseable != null) {}
    try
    {
      try
      {
        paramAutoCloseable.close();
      }
      catch (RuntimeException paramAutoCloseable)
      {
        throw paramAutoCloseable;
      }
      return;
    }
    catch (Exception paramAutoCloseable)
    {
      for (;;) {}
    }
  }
  
  public static Uri createDirectory(Context paramContext, Uri paramUri, String paramString)
  {
    return createFile(paramContext, paramUri, "vnd.android.document/directory", paramString);
  }
  
  public static Uri createFile(Context paramContext, Uri paramUri, String paramString1, String paramString2)
  {
    try
    {
      paramContext = DocumentsContract.createDocument(paramContext.getContentResolver(), paramUri, paramString1, paramString2);
      return paramContext;
    }
    catch (Exception paramContext) {}
    return null;
  }
  
  /* Error */
  public static Uri[] listFiles(Context paramContext, Uri paramUri)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 38	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   4: astore_2
    //   5: aload_1
    //   6: aload_1
    //   7: invokestatic 50	android/provider/DocumentsContract:getDocumentId	(Landroid/net/Uri;)Ljava/lang/String;
    //   10: invokestatic 54	android/provider/DocumentsContract:buildChildDocumentsUriUsingTree	(Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;
    //   13: astore_3
    //   14: new 56	java/util/ArrayList
    //   17: dup
    //   18: invokespecial 57	java/util/ArrayList:<init>	()V
    //   21: astore 4
    //   23: aconst_null
    //   24: astore_0
    //   25: aconst_null
    //   26: astore 5
    //   28: aload_2
    //   29: aload_3
    //   30: iconst_1
    //   31: anewarray 59	java/lang/String
    //   34: dup
    //   35: iconst_0
    //   36: ldc 61
    //   38: aastore
    //   39: aconst_null
    //   40: aconst_null
    //   41: aconst_null
    //   42: invokevirtual 67	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   45: astore_2
    //   46: aload_2
    //   47: astore_3
    //   48: aload_2
    //   49: astore 5
    //   51: aload_2
    //   52: astore_0
    //   53: aload_2
    //   54: invokeinterface 73 1 0
    //   59: ifeq +28 -> 87
    //   62: aload_2
    //   63: astore 5
    //   65: aload_2
    //   66: astore_0
    //   67: aload 4
    //   69: aload_1
    //   70: aload_2
    //   71: iconst_0
    //   72: invokeinterface 77 2 0
    //   77: invokestatic 80	android/provider/DocumentsContract:buildDocumentUriUsingTree	(Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;
    //   80: invokevirtual 84	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   83: pop
    //   84: goto -38 -> 46
    //   87: aload_3
    //   88: invokestatic 86	android/support/v4/provider/DocumentsContractApi21:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   91: goto +59 -> 150
    //   94: astore_0
    //   95: goto +72 -> 167
    //   98: astore_2
    //   99: aload_0
    //   100: astore 5
    //   102: new 88	java/lang/StringBuilder
    //   105: astore_1
    //   106: aload_0
    //   107: astore 5
    //   109: aload_1
    //   110: invokespecial 89	java/lang/StringBuilder:<init>	()V
    //   113: aload_0
    //   114: astore 5
    //   116: aload_1
    //   117: ldc 91
    //   119: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: pop
    //   123: aload_0
    //   124: astore 5
    //   126: aload_1
    //   127: aload_2
    //   128: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   131: pop
    //   132: aload_0
    //   133: astore 5
    //   135: ldc 8
    //   137: aload_1
    //   138: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   141: invokestatic 108	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   144: pop
    //   145: aload_0
    //   146: astore_3
    //   147: goto -60 -> 87
    //   150: aload 4
    //   152: aload 4
    //   154: invokevirtual 112	java/util/ArrayList:size	()I
    //   157: anewarray 114	android/net/Uri
    //   160: invokevirtual 118	java/util/ArrayList:toArray	([Ljava/lang/Object;)[Ljava/lang/Object;
    //   163: checkcast 120	[Landroid/net/Uri;
    //   166: areturn
    //   167: aload 5
    //   169: invokestatic 86	android/support/v4/provider/DocumentsContractApi21:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   172: aload_0
    //   173: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	174	0	paramContext	Context
    //   0	174	1	paramUri	Uri
    //   4	67	2	localObject1	Object
    //   98	30	2	localException	Exception
    //   13	134	3	localObject2	Object
    //   21	132	4	localArrayList	java.util.ArrayList
    //   26	142	5	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   28	46	94	finally
    //   53	62	94	finally
    //   67	84	94	finally
    //   102	106	94	finally
    //   109	113	94	finally
    //   116	123	94	finally
    //   126	132	94	finally
    //   135	145	94	finally
    //   28	46	98	java/lang/Exception
    //   53	62	98	java/lang/Exception
    //   67	84	98	java/lang/Exception
  }
  
  public static Uri prepareTreeUri(Uri paramUri)
  {
    return DocumentsContract.buildDocumentUriUsingTree(paramUri, DocumentsContract.getTreeDocumentId(paramUri));
  }
  
  public static Uri renameTo(Context paramContext, Uri paramUri, String paramString)
  {
    try
    {
      paramContext = DocumentsContract.renameDocument(paramContext.getContentResolver(), paramUri, paramString);
      return paramContext;
    }
    catch (Exception paramContext) {}
    return null;
  }
}


/* Location:              ~/android/support/v4/provider/DocumentsContractApi21.class
 *
 * Reversed by:           J
 */