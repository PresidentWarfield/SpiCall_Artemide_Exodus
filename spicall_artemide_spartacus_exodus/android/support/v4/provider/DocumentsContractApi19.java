package android.support.v4.provider;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.text.TextUtils;

class DocumentsContractApi19
{
  private static final int FLAG_VIRTUAL_DOCUMENT = 512;
  private static final String TAG = "DocumentFile";
  
  public static boolean canRead(Context paramContext, Uri paramUri)
  {
    if (paramContext.checkCallingOrSelfUriPermission(paramUri, 1) != 0) {
      return false;
    }
    return !TextUtils.isEmpty(getRawType(paramContext, paramUri));
  }
  
  public static boolean canWrite(Context paramContext, Uri paramUri)
  {
    if (paramContext.checkCallingOrSelfUriPermission(paramUri, 2) != 0) {
      return false;
    }
    String str = getRawType(paramContext, paramUri);
    int i = queryForInt(paramContext, paramUri, "flags", 0);
    if (TextUtils.isEmpty(str)) {
      return false;
    }
    if ((i & 0x4) != 0) {
      return true;
    }
    if (("vnd.android.document/directory".equals(str)) && ((i & 0x8) != 0)) {
      return true;
    }
    return (!TextUtils.isEmpty(str)) && ((i & 0x2) != 0);
  }
  
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
  
  public static boolean delete(Context paramContext, Uri paramUri)
  {
    try
    {
      boolean bool = DocumentsContract.deleteDocument(paramContext.getContentResolver(), paramUri);
      return bool;
    }
    catch (Exception paramContext) {}
    return false;
  }
  
  /* Error */
  public static boolean exists(Context paramContext, Uri paramUri)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 65	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   4: astore_2
    //   5: iconst_0
    //   6: istore_3
    //   7: aconst_null
    //   8: astore 4
    //   10: aconst_null
    //   11: astore_0
    //   12: aload_2
    //   13: aload_1
    //   14: iconst_1
    //   15: anewarray 45	java/lang/String
    //   18: dup
    //   19: iconst_0
    //   20: ldc 74
    //   22: aastore
    //   23: aconst_null
    //   24: aconst_null
    //   25: aconst_null
    //   26: invokevirtual 80	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   29: astore_1
    //   30: aload_1
    //   31: astore_0
    //   32: aload_1
    //   33: astore 4
    //   35: aload_1
    //   36: invokeinterface 86 1 0
    //   41: istore 5
    //   43: iload 5
    //   45: ifle +5 -> 50
    //   48: iconst_1
    //   49: istore_3
    //   50: aload_1
    //   51: invokestatic 88	android/support/v4/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   54: iload_3
    //   55: ireturn
    //   56: astore_1
    //   57: goto +57 -> 114
    //   60: astore_1
    //   61: aload 4
    //   63: astore_0
    //   64: new 90	java/lang/StringBuilder
    //   67: astore_2
    //   68: aload 4
    //   70: astore_0
    //   71: aload_2
    //   72: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   75: aload 4
    //   77: astore_0
    //   78: aload_2
    //   79: ldc 93
    //   81: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   84: pop
    //   85: aload 4
    //   87: astore_0
    //   88: aload_2
    //   89: aload_1
    //   90: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   93: pop
    //   94: aload 4
    //   96: astore_0
    //   97: ldc 11
    //   99: aload_2
    //   100: invokevirtual 104	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   103: invokestatic 110	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   106: pop
    //   107: aload 4
    //   109: invokestatic 88	android/support/v4/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   112: iconst_0
    //   113: ireturn
    //   114: aload_0
    //   115: invokestatic 88	android/support/v4/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   118: aload_1
    //   119: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	120	0	paramContext	Context
    //   0	120	1	paramUri	Uri
    //   4	96	2	localObject	Object
    //   6	49	3	bool	boolean
    //   8	100	4	localUri	Uri
    //   41	3	5	i	int
    // Exception table:
    //   from	to	target	type
    //   12	30	56	finally
    //   35	43	56	finally
    //   64	68	56	finally
    //   71	75	56	finally
    //   78	85	56	finally
    //   88	94	56	finally
    //   97	107	56	finally
    //   12	30	60	java/lang/Exception
    //   35	43	60	java/lang/Exception
  }
  
  public static long getFlags(Context paramContext, Uri paramUri)
  {
    return queryForLong(paramContext, paramUri, "flags", 0L);
  }
  
  public static String getName(Context paramContext, Uri paramUri)
  {
    return queryForString(paramContext, paramUri, "_display_name", null);
  }
  
  private static String getRawType(Context paramContext, Uri paramUri)
  {
    return queryForString(paramContext, paramUri, "mime_type", null);
  }
  
  public static String getType(Context paramContext, Uri paramUri)
  {
    paramContext = getRawType(paramContext, paramUri);
    if ("vnd.android.document/directory".equals(paramContext)) {
      return null;
    }
    return paramContext;
  }
  
  public static boolean isDirectory(Context paramContext, Uri paramUri)
  {
    return "vnd.android.document/directory".equals(getRawType(paramContext, paramUri));
  }
  
  public static boolean isDocumentUri(Context paramContext, Uri paramUri)
  {
    return DocumentsContract.isDocumentUri(paramContext, paramUri);
  }
  
  public static boolean isFile(Context paramContext, Uri paramUri)
  {
    paramContext = getRawType(paramContext, paramUri);
    return (!"vnd.android.document/directory".equals(paramContext)) && (!TextUtils.isEmpty(paramContext));
  }
  
  public static boolean isVirtual(Context paramContext, Uri paramUri)
  {
    boolean bool1 = isDocumentUri(paramContext, paramUri);
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    if ((getFlags(paramContext, paramUri) & 0x200) != 0L) {
      bool2 = true;
    }
    return bool2;
  }
  
  public static long lastModified(Context paramContext, Uri paramUri)
  {
    return queryForLong(paramContext, paramUri, "last_modified", 0L);
  }
  
  public static long length(Context paramContext, Uri paramUri)
  {
    return queryForLong(paramContext, paramUri, "_size", 0L);
  }
  
  private static int queryForInt(Context paramContext, Uri paramUri, String paramString, int paramInt)
  {
    return (int)queryForLong(paramContext, paramUri, paramString, paramInt);
  }
  
  /* Error */
  private static long queryForLong(Context paramContext, Uri paramUri, String paramString, long paramLong)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 65	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   4: astore 5
    //   6: aconst_null
    //   7: astore 6
    //   9: aconst_null
    //   10: astore_0
    //   11: aload 5
    //   13: aload_1
    //   14: iconst_1
    //   15: anewarray 45	java/lang/String
    //   18: dup
    //   19: iconst_0
    //   20: aload_2
    //   21: aastore
    //   22: aconst_null
    //   23: aconst_null
    //   24: aconst_null
    //   25: invokevirtual 80	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   28: astore_1
    //   29: aload_1
    //   30: astore_0
    //   31: aload_1
    //   32: astore 6
    //   34: aload_1
    //   35: invokeinterface 147 1 0
    //   40: ifeq +39 -> 79
    //   43: aload_1
    //   44: astore_0
    //   45: aload_1
    //   46: astore 6
    //   48: aload_1
    //   49: iconst_0
    //   50: invokeinterface 151 2 0
    //   55: ifne +24 -> 79
    //   58: aload_1
    //   59: astore_0
    //   60: aload_1
    //   61: astore 6
    //   63: aload_1
    //   64: iconst_0
    //   65: invokeinterface 155 2 0
    //   70: lstore 7
    //   72: aload_1
    //   73: invokestatic 88	android/support/v4/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   76: lload 7
    //   78: lreturn
    //   79: aload_1
    //   80: invokestatic 88	android/support/v4/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   83: lload_3
    //   84: lreturn
    //   85: astore_1
    //   86: goto +57 -> 143
    //   89: astore_1
    //   90: aload 6
    //   92: astore_0
    //   93: new 90	java/lang/StringBuilder
    //   96: astore_2
    //   97: aload 6
    //   99: astore_0
    //   100: aload_2
    //   101: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   104: aload 6
    //   106: astore_0
    //   107: aload_2
    //   108: ldc 93
    //   110: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: pop
    //   114: aload 6
    //   116: astore_0
    //   117: aload_2
    //   118: aload_1
    //   119: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   122: pop
    //   123: aload 6
    //   125: astore_0
    //   126: ldc 11
    //   128: aload_2
    //   129: invokevirtual 104	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   132: invokestatic 110	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   135: pop
    //   136: aload 6
    //   138: invokestatic 88	android/support/v4/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   141: lload_3
    //   142: lreturn
    //   143: aload_0
    //   144: invokestatic 88	android/support/v4/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   147: aload_1
    //   148: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	149	0	paramContext	Context
    //   0	149	1	paramUri	Uri
    //   0	149	2	paramString	String
    //   0	149	3	paramLong	long
    //   4	8	5	localContentResolver	android.content.ContentResolver
    //   7	130	6	localUri	Uri
    //   70	7	7	l	long
    // Exception table:
    //   from	to	target	type
    //   11	29	85	finally
    //   34	43	85	finally
    //   48	58	85	finally
    //   63	72	85	finally
    //   93	97	85	finally
    //   100	104	85	finally
    //   107	114	85	finally
    //   117	123	85	finally
    //   126	136	85	finally
    //   11	29	89	java/lang/Exception
    //   34	43	89	java/lang/Exception
    //   48	58	89	java/lang/Exception
    //   63	72	89	java/lang/Exception
  }
  
  /* Error */
  private static String queryForString(Context paramContext, Uri paramUri, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 65	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   4: astore 4
    //   6: aconst_null
    //   7: astore 5
    //   9: aconst_null
    //   10: astore_0
    //   11: aload 4
    //   13: aload_1
    //   14: iconst_1
    //   15: anewarray 45	java/lang/String
    //   18: dup
    //   19: iconst_0
    //   20: aload_2
    //   21: aastore
    //   22: aconst_null
    //   23: aconst_null
    //   24: aconst_null
    //   25: invokevirtual 80	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   28: astore_1
    //   29: aload_1
    //   30: astore_0
    //   31: aload_1
    //   32: astore 5
    //   34: aload_1
    //   35: invokeinterface 147 1 0
    //   40: ifeq +37 -> 77
    //   43: aload_1
    //   44: astore_0
    //   45: aload_1
    //   46: astore 5
    //   48: aload_1
    //   49: iconst_0
    //   50: invokeinterface 151 2 0
    //   55: ifne +22 -> 77
    //   58: aload_1
    //   59: astore_0
    //   60: aload_1
    //   61: astore 5
    //   63: aload_1
    //   64: iconst_0
    //   65: invokeinterface 159 2 0
    //   70: astore_2
    //   71: aload_1
    //   72: invokestatic 88	android/support/v4/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   75: aload_2
    //   76: areturn
    //   77: aload_1
    //   78: invokestatic 88	android/support/v4/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   81: aload_3
    //   82: areturn
    //   83: astore_1
    //   84: goto +57 -> 141
    //   87: astore_2
    //   88: aload 5
    //   90: astore_0
    //   91: new 90	java/lang/StringBuilder
    //   94: astore_1
    //   95: aload 5
    //   97: astore_0
    //   98: aload_1
    //   99: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   102: aload 5
    //   104: astore_0
    //   105: aload_1
    //   106: ldc 93
    //   108: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: pop
    //   112: aload 5
    //   114: astore_0
    //   115: aload_1
    //   116: aload_2
    //   117: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   120: pop
    //   121: aload 5
    //   123: astore_0
    //   124: ldc 11
    //   126: aload_1
    //   127: invokevirtual 104	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   130: invokestatic 110	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   133: pop
    //   134: aload 5
    //   136: invokestatic 88	android/support/v4/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   139: aload_3
    //   140: areturn
    //   141: aload_0
    //   142: invokestatic 88	android/support/v4/provider/DocumentsContractApi19:closeQuietly	(Ljava/lang/AutoCloseable;)V
    //   145: aload_1
    //   146: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	147	0	paramContext	Context
    //   0	147	1	paramUri	Uri
    //   0	147	2	paramString1	String
    //   0	147	3	paramString2	String
    //   4	8	4	localContentResolver	android.content.ContentResolver
    //   7	128	5	localUri	Uri
    // Exception table:
    //   from	to	target	type
    //   11	29	83	finally
    //   34	43	83	finally
    //   48	58	83	finally
    //   63	71	83	finally
    //   91	95	83	finally
    //   98	102	83	finally
    //   105	112	83	finally
    //   115	121	83	finally
    //   124	134	83	finally
    //   11	29	87	java/lang/Exception
    //   34	43	87	java/lang/Exception
    //   48	58	87	java/lang/Exception
    //   63	71	87	java/lang/Exception
  }
}


/* Location:              ~/android/support/v4/provider/DocumentsContractApi19.class
 *
 * Reversed by:           J
 */