package com.crashlytics.android.core;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

class MetaDataStore
{
  private static final String KEYDATA_SUFFIX = "keys";
  private static final String KEY_USER_EMAIL = "userEmail";
  private static final String KEY_USER_ID = "userId";
  private static final String KEY_USER_NAME = "userName";
  private static final String METADATA_EXT = ".meta";
  private static final String USERDATA_SUFFIX = "user";
  private static final Charset UTF_8 = Charset.forName("UTF-8");
  private final File filesDir;
  
  public MetaDataStore(File paramFile)
  {
    this.filesDir = paramFile;
  }
  
  private static Map<String, String> jsonToKeysData(String paramString)
  {
    JSONObject localJSONObject = new JSONObject(paramString);
    paramString = new HashMap();
    Iterator localIterator = localJSONObject.keys();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      paramString.put(str, valueOrNull(localJSONObject, str));
    }
    return paramString;
  }
  
  private static UserMetaData jsonToUserData(String paramString)
  {
    paramString = new JSONObject(paramString);
    return new UserMetaData(valueOrNull(paramString, "userId"), valueOrNull(paramString, "userName"), valueOrNull(paramString, "userEmail"));
  }
  
  private static String keysDataToJson(Map<String, String> paramMap)
  {
    return new JSONObject(paramMap).toString();
  }
  
  private static String userDataToJson(UserMetaData paramUserMetaData)
  {
    new JSONObject() {}.toString();
  }
  
  private static String valueOrNull(JSONObject paramJSONObject, String paramString)
  {
    boolean bool = paramJSONObject.isNull(paramString);
    String str = null;
    if (!bool) {
      str = paramJSONObject.optString(paramString, null);
    }
    return str;
  }
  
  public File getKeysFileForSession(String paramString)
  {
    File localFile = this.filesDir;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("keys");
    localStringBuilder.append(".meta");
    return new File(localFile, localStringBuilder.toString());
  }
  
  public File getUserDataFileForSession(String paramString)
  {
    File localFile = this.filesDir;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("user");
    localStringBuilder.append(".meta");
    return new File(localFile, localStringBuilder.toString());
  }
  
  /* Error */
  public Map<String, String> readKeyData(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 137	com/crashlytics/android/core/MetaDataStore:getKeysFileForSession	(Ljava/lang/String;)Ljava/io/File;
    //   5: astore_2
    //   6: aload_2
    //   7: invokevirtual 140	java/io/File:exists	()Z
    //   10: ifne +7 -> 17
    //   13: invokestatic 146	java/util/Collections:emptyMap	()Ljava/util/Map;
    //   16: areturn
    //   17: aconst_null
    //   18: astore_3
    //   19: aconst_null
    //   20: astore 4
    //   22: aload 4
    //   24: astore_1
    //   25: new 148	java/io/FileInputStream
    //   28: astore 5
    //   30: aload 4
    //   32: astore_1
    //   33: aload 5
    //   35: aload_2
    //   36: invokespecial 150	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   39: aload 5
    //   41: invokestatic 156	io/fabric/sdk/android/services/b/i:a	(Ljava/io/InputStream;)Ljava/lang/String;
    //   44: invokestatic 158	com/crashlytics/android/core/MetaDataStore:jsonToKeysData	(Ljava/lang/String;)Ljava/util/Map;
    //   47: astore_1
    //   48: aload 5
    //   50: ldc -96
    //   52: invokestatic 163	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   55: aload_1
    //   56: areturn
    //   57: astore 4
    //   59: aload 5
    //   61: astore_1
    //   62: aload 4
    //   64: astore 5
    //   66: goto +48 -> 114
    //   69: astore_1
    //   70: aload_1
    //   71: astore 4
    //   73: goto +13 -> 86
    //   76: astore 5
    //   78: goto +36 -> 114
    //   81: astore 4
    //   83: aload_3
    //   84: astore 5
    //   86: aload 5
    //   88: astore_1
    //   89: invokestatic 169	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   92: ldc -85
    //   94: ldc -83
    //   96: aload 4
    //   98: invokeinterface 179 4 0
    //   103: aload 5
    //   105: ldc -96
    //   107: invokestatic 163	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   110: invokestatic 146	java/util/Collections:emptyMap	()Ljava/util/Map;
    //   113: areturn
    //   114: aload_1
    //   115: ldc -96
    //   117: invokestatic 163	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   120: aload 5
    //   122: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	123	0	this	MetaDataStore
    //   0	123	1	paramString	String
    //   5	31	2	localFile	File
    //   18	66	3	localObject1	Object
    //   20	11	4	localObject2	Object
    //   57	6	4	localObject3	Object
    //   71	1	4	str	String
    //   81	16	4	localException	Exception
    //   28	37	5	localObject4	Object
    //   76	1	5	localObject5	Object
    //   84	37	5	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   39	48	57	finally
    //   39	48	69	java/lang/Exception
    //   25	30	76	finally
    //   33	39	76	finally
    //   89	103	76	finally
    //   25	30	81	java/lang/Exception
    //   33	39	81	java/lang/Exception
  }
  
  /* Error */
  public UserMetaData readUserData(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 182	com/crashlytics/android/core/MetaDataStore:getUserDataFileForSession	(Ljava/lang/String;)Ljava/io/File;
    //   5: astore_2
    //   6: aload_2
    //   7: invokevirtual 140	java/io/File:exists	()Z
    //   10: ifne +7 -> 17
    //   13: getstatic 186	com/crashlytics/android/core/UserMetaData:EMPTY	Lcom/crashlytics/android/core/UserMetaData;
    //   16: areturn
    //   17: aconst_null
    //   18: astore_3
    //   19: aconst_null
    //   20: astore 4
    //   22: aload 4
    //   24: astore_1
    //   25: new 148	java/io/FileInputStream
    //   28: astore 5
    //   30: aload 4
    //   32: astore_1
    //   33: aload 5
    //   35: aload_2
    //   36: invokespecial 150	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   39: aload 5
    //   41: invokestatic 156	io/fabric/sdk/android/services/b/i:a	(Ljava/io/InputStream;)Ljava/lang/String;
    //   44: invokestatic 188	com/crashlytics/android/core/MetaDataStore:jsonToUserData	(Ljava/lang/String;)Lcom/crashlytics/android/core/UserMetaData;
    //   47: astore_1
    //   48: aload 5
    //   50: ldc -96
    //   52: invokestatic 163	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   55: aload_1
    //   56: areturn
    //   57: astore_1
    //   58: goto +52 -> 110
    //   61: astore 4
    //   63: goto +19 -> 82
    //   66: astore 4
    //   68: aload_1
    //   69: astore 5
    //   71: aload 4
    //   73: astore_1
    //   74: goto +36 -> 110
    //   77: astore 4
    //   79: aload_3
    //   80: astore 5
    //   82: aload 5
    //   84: astore_1
    //   85: invokestatic 169	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   88: ldc -85
    //   90: ldc -83
    //   92: aload 4
    //   94: invokeinterface 179 4 0
    //   99: aload 5
    //   101: ldc -96
    //   103: invokestatic 163	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   106: getstatic 186	com/crashlytics/android/core/UserMetaData:EMPTY	Lcom/crashlytics/android/core/UserMetaData;
    //   109: areturn
    //   110: aload 5
    //   112: ldc -96
    //   114: invokestatic 163	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   117: aload_1
    //   118: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	119	0	this	MetaDataStore
    //   0	119	1	paramString	String
    //   5	31	2	localFile	File
    //   18	62	3	localObject1	Object
    //   20	11	4	localObject2	Object
    //   61	1	4	localException1	Exception
    //   66	6	4	localObject3	Object
    //   77	16	4	localException2	Exception
    //   28	83	5	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   39	48	57	finally
    //   39	48	61	java/lang/Exception
    //   25	30	66	finally
    //   33	39	66	finally
    //   85	99	66	finally
    //   25	30	77	java/lang/Exception
    //   33	39	77	java/lang/Exception
  }
  
  /* Error */
  public void writeKeyData(String paramString, Map<String, String> paramMap)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 137	com/crashlytics/android/core/MetaDataStore:getKeysFileForSession	(Ljava/lang/String;)Ljava/io/File;
    //   5: astore_3
    //   6: aconst_null
    //   7: astore 4
    //   9: aconst_null
    //   10: astore 5
    //   12: aload 5
    //   14: astore_1
    //   15: aload_2
    //   16: invokestatic 192	com/crashlytics/android/core/MetaDataStore:keysDataToJson	(Ljava/util/Map;)Ljava/lang/String;
    //   19: astore 6
    //   21: aload 5
    //   23: astore_1
    //   24: new 194	java/io/BufferedWriter
    //   27: astore_2
    //   28: aload 5
    //   30: astore_1
    //   31: new 196	java/io/OutputStreamWriter
    //   34: astore 7
    //   36: aload 5
    //   38: astore_1
    //   39: new 198	java/io/FileOutputStream
    //   42: astore 8
    //   44: aload 5
    //   46: astore_1
    //   47: aload 8
    //   49: aload_3
    //   50: invokespecial 199	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   53: aload 5
    //   55: astore_1
    //   56: aload 7
    //   58: aload 8
    //   60: getstatic 41	com/crashlytics/android/core/MetaDataStore:UTF_8	Ljava/nio/charset/Charset;
    //   63: invokespecial 202	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;Ljava/nio/charset/Charset;)V
    //   66: aload 5
    //   68: astore_1
    //   69: aload_2
    //   70: aload 7
    //   72: invokespecial 205	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
    //   75: aload_2
    //   76: aload 6
    //   78: invokevirtual 210	java/io/Writer:write	(Ljava/lang/String;)V
    //   81: aload_2
    //   82: invokevirtual 213	java/io/Writer:flush	()V
    //   85: aload_2
    //   86: ldc -41
    //   88: invokestatic 163	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   91: goto +51 -> 142
    //   94: astore 5
    //   96: aload_2
    //   97: astore_1
    //   98: aload 5
    //   100: astore_2
    //   101: goto +42 -> 143
    //   104: astore_1
    //   105: aload_1
    //   106: astore 5
    //   108: goto +12 -> 120
    //   111: astore_2
    //   112: goto +31 -> 143
    //   115: astore 5
    //   117: aload 4
    //   119: astore_2
    //   120: aload_2
    //   121: astore_1
    //   122: invokestatic 169	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   125: ldc -85
    //   127: ldc -39
    //   129: aload 5
    //   131: invokeinterface 179 4 0
    //   136: aload_2
    //   137: ldc -41
    //   139: invokestatic 163	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   142: return
    //   143: aload_1
    //   144: ldc -41
    //   146: invokestatic 163	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   149: aload_2
    //   150: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	MetaDataStore
    //   0	151	1	paramString	String
    //   0	151	2	paramMap	Map<String, String>
    //   5	45	3	localFile	File
    //   7	111	4	localObject1	Object
    //   10	57	5	localObject2	Object
    //   94	5	5	localObject3	Object
    //   106	1	5	str1	String
    //   115	15	5	localException	Exception
    //   19	58	6	str2	String
    //   34	37	7	localOutputStreamWriter	java.io.OutputStreamWriter
    //   42	17	8	localFileOutputStream	java.io.FileOutputStream
    // Exception table:
    //   from	to	target	type
    //   75	85	94	finally
    //   75	85	104	java/lang/Exception
    //   15	21	111	finally
    //   24	28	111	finally
    //   31	36	111	finally
    //   39	44	111	finally
    //   47	53	111	finally
    //   56	66	111	finally
    //   69	75	111	finally
    //   122	136	111	finally
    //   15	21	115	java/lang/Exception
    //   24	28	115	java/lang/Exception
    //   31	36	115	java/lang/Exception
    //   39	44	115	java/lang/Exception
    //   47	53	115	java/lang/Exception
    //   56	66	115	java/lang/Exception
    //   69	75	115	java/lang/Exception
  }
  
  /* Error */
  public void writeUserData(String paramString, UserMetaData paramUserMetaData)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 182	com/crashlytics/android/core/MetaDataStore:getUserDataFileForSession	(Ljava/lang/String;)Ljava/io/File;
    //   5: astore_3
    //   6: aconst_null
    //   7: astore 4
    //   9: aconst_null
    //   10: astore 5
    //   12: aload 5
    //   14: astore_1
    //   15: aload_2
    //   16: invokestatic 222	com/crashlytics/android/core/MetaDataStore:userDataToJson	(Lcom/crashlytics/android/core/UserMetaData;)Ljava/lang/String;
    //   19: astore 6
    //   21: aload 5
    //   23: astore_1
    //   24: new 194	java/io/BufferedWriter
    //   27: astore_2
    //   28: aload 5
    //   30: astore_1
    //   31: new 196	java/io/OutputStreamWriter
    //   34: astore 7
    //   36: aload 5
    //   38: astore_1
    //   39: new 198	java/io/FileOutputStream
    //   42: astore 8
    //   44: aload 5
    //   46: astore_1
    //   47: aload 8
    //   49: aload_3
    //   50: invokespecial 199	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   53: aload 5
    //   55: astore_1
    //   56: aload 7
    //   58: aload 8
    //   60: getstatic 41	com/crashlytics/android/core/MetaDataStore:UTF_8	Ljava/nio/charset/Charset;
    //   63: invokespecial 202	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;Ljava/nio/charset/Charset;)V
    //   66: aload 5
    //   68: astore_1
    //   69: aload_2
    //   70: aload 7
    //   72: invokespecial 205	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
    //   75: aload_2
    //   76: aload 6
    //   78: invokevirtual 210	java/io/Writer:write	(Ljava/lang/String;)V
    //   81: aload_2
    //   82: invokevirtual 213	java/io/Writer:flush	()V
    //   85: aload_2
    //   86: ldc -96
    //   88: invokestatic 163	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   91: goto +51 -> 142
    //   94: astore_1
    //   95: goto +48 -> 143
    //   98: astore_1
    //   99: aload_1
    //   100: astore 5
    //   102: goto +18 -> 120
    //   105: astore 5
    //   107: aload_1
    //   108: astore_2
    //   109: aload 5
    //   111: astore_1
    //   112: goto +31 -> 143
    //   115: astore 5
    //   117: aload 4
    //   119: astore_2
    //   120: aload_2
    //   121: astore_1
    //   122: invokestatic 169	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   125: ldc -85
    //   127: ldc -32
    //   129: aload 5
    //   131: invokeinterface 179 4 0
    //   136: aload_2
    //   137: ldc -96
    //   139: invokestatic 163	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   142: return
    //   143: aload_2
    //   144: ldc -96
    //   146: invokestatic 163	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   149: aload_1
    //   150: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	MetaDataStore
    //   0	151	1	paramString	String
    //   0	151	2	paramUserMetaData	UserMetaData
    //   5	45	3	localFile	File
    //   7	111	4	localObject1	Object
    //   10	91	5	str1	String
    //   105	5	5	localObject2	Object
    //   115	15	5	localException	Exception
    //   19	58	6	str2	String
    //   34	37	7	localOutputStreamWriter	java.io.OutputStreamWriter
    //   42	17	8	localFileOutputStream	java.io.FileOutputStream
    // Exception table:
    //   from	to	target	type
    //   75	85	94	finally
    //   75	85	98	java/lang/Exception
    //   15	21	105	finally
    //   24	28	105	finally
    //   31	36	105	finally
    //   39	44	105	finally
    //   47	53	105	finally
    //   56	66	105	finally
    //   69	75	105	finally
    //   122	136	105	finally
    //   15	21	115	java/lang/Exception
    //   24	28	115	java/lang/Exception
    //   31	36	115	java/lang/Exception
    //   39	44	115	java/lang/Exception
    //   47	53	115	java/lang/Exception
    //   56	66	115	java/lang/Exception
    //   69	75	115	java/lang/Exception
  }
}


/* Location:              ~/com/crashlytics/android/core/MetaDataStore.class
 *
 * Reversed by:           J
 */