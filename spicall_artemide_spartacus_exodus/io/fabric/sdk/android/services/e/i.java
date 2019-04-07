package io.fabric.sdk.android.services.e;

import io.fabric.sdk.android.h;

class i
  implements g
{
  private final h a;
  
  public i(h paramh)
  {
    this.a = paramh;
  }
  
  /* Error */
  public org.json.JSONObject a()
  {
    // Byte code:
    //   0: invokestatic 25	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   3: ldc 27
    //   5: ldc 29
    //   7: invokeinterface 34 3 0
    //   12: aconst_null
    //   13: astore_1
    //   14: aconst_null
    //   15: astore_2
    //   16: aconst_null
    //   17: astore_3
    //   18: new 36	java/io/File
    //   21: astore 4
    //   23: new 38	io/fabric/sdk/android/services/d/b
    //   26: astore 5
    //   28: aload 5
    //   30: aload_0
    //   31: getfield 15	io/fabric/sdk/android/services/e/i:a	Lio/fabric/sdk/android/h;
    //   34: invokespecial 40	io/fabric/sdk/android/services/d/b:<init>	(Lio/fabric/sdk/android/h;)V
    //   37: aload 4
    //   39: aload 5
    //   41: invokevirtual 43	io/fabric/sdk/android/services/d/b:a	()Ljava/io/File;
    //   44: ldc 45
    //   46: invokespecial 48	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   49: aload 4
    //   51: invokevirtual 52	java/io/File:exists	()Z
    //   54: ifeq +54 -> 108
    //   57: new 54	java/io/FileInputStream
    //   60: astore 5
    //   62: aload 5
    //   64: aload 4
    //   66: invokespecial 57	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   69: aload 5
    //   71: astore_2
    //   72: aload 5
    //   74: invokestatic 62	io/fabric/sdk/android/services/b/i:a	(Ljava/io/InputStream;)Ljava/lang/String;
    //   77: astore 4
    //   79: aload 5
    //   81: astore_2
    //   82: new 64	org/json/JSONObject
    //   85: astore_3
    //   86: aload 5
    //   88: astore_2
    //   89: aload_3
    //   90: aload 4
    //   92: invokespecial 67	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   95: aload 5
    //   97: astore_2
    //   98: aload_3
    //   99: astore 5
    //   101: goto +24 -> 125
    //   104: astore_3
    //   105: goto +38 -> 143
    //   108: invokestatic 25	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   111: ldc 27
    //   113: ldc 69
    //   115: invokeinterface 34 3 0
    //   120: aconst_null
    //   121: astore 5
    //   123: aload_3
    //   124: astore_2
    //   125: aload_2
    //   126: ldc 71
    //   128: invokestatic 74	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   131: goto +38 -> 169
    //   134: astore 5
    //   136: goto +38 -> 174
    //   139: astore_3
    //   140: aconst_null
    //   141: astore 5
    //   143: aload 5
    //   145: astore_2
    //   146: invokestatic 25	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   149: ldc 27
    //   151: ldc 76
    //   153: aload_3
    //   154: invokeinterface 80 4 0
    //   159: aload 5
    //   161: ldc 71
    //   163: invokestatic 74	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   166: aload_1
    //   167: astore 5
    //   169: aload 5
    //   171: areturn
    //   172: astore 5
    //   174: aload_2
    //   175: ldc 71
    //   177: invokestatic 74	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   180: aload 5
    //   182: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	183	0	this	i
    //   13	154	1	localObject1	Object
    //   15	160	2	localObject2	Object
    //   17	82	3	localJSONObject	org.json.JSONObject
    //   104	20	3	localException1	Exception
    //   139	15	3	localException2	Exception
    //   21	70	4	localObject3	Object
    //   26	96	5	localObject4	Object
    //   134	1	5	localObject5	Object
    //   141	29	5	localObject6	Object
    //   172	9	5	localObject7	Object
    // Exception table:
    //   from	to	target	type
    //   72	79	104	java/lang/Exception
    //   82	86	104	java/lang/Exception
    //   89	95	104	java/lang/Exception
    //   18	69	134	finally
    //   108	120	134	finally
    //   18	69	139	java/lang/Exception
    //   108	120	139	java/lang/Exception
    //   72	79	172	finally
    //   82	86	172	finally
    //   89	95	172	finally
    //   146	159	172	finally
  }
  
  /* Error */
  public void a(long paramLong, org.json.JSONObject paramJSONObject)
  {
    // Byte code:
    //   0: invokestatic 25	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   3: ldc 27
    //   5: ldc 83
    //   7: invokeinterface 34 3 0
    //   12: aload_3
    //   13: ifnull +176 -> 189
    //   16: aconst_null
    //   17: astore 4
    //   19: aconst_null
    //   20: astore 5
    //   22: aload 5
    //   24: astore 6
    //   26: aload_3
    //   27: ldc 85
    //   29: lload_1
    //   30: invokevirtual 89	org/json/JSONObject:put	(Ljava/lang/String;J)Lorg/json/JSONObject;
    //   33: pop
    //   34: aload 5
    //   36: astore 6
    //   38: new 91	java/io/FileWriter
    //   41: astore 7
    //   43: aload 5
    //   45: astore 6
    //   47: new 36	java/io/File
    //   50: astore 8
    //   52: aload 5
    //   54: astore 6
    //   56: new 38	io/fabric/sdk/android/services/d/b
    //   59: astore 9
    //   61: aload 5
    //   63: astore 6
    //   65: aload 9
    //   67: aload_0
    //   68: getfield 15	io/fabric/sdk/android/services/e/i:a	Lio/fabric/sdk/android/h;
    //   71: invokespecial 40	io/fabric/sdk/android/services/d/b:<init>	(Lio/fabric/sdk/android/h;)V
    //   74: aload 5
    //   76: astore 6
    //   78: aload 8
    //   80: aload 9
    //   82: invokevirtual 43	io/fabric/sdk/android/services/d/b:a	()Ljava/io/File;
    //   85: ldc 45
    //   87: invokespecial 48	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   90: aload 5
    //   92: astore 6
    //   94: aload 7
    //   96: aload 8
    //   98: invokespecial 92	java/io/FileWriter:<init>	(Ljava/io/File;)V
    //   101: aload 7
    //   103: aload_3
    //   104: invokevirtual 96	org/json/JSONObject:toString	()Ljava/lang/String;
    //   107: invokevirtual 99	java/io/FileWriter:write	(Ljava/lang/String;)V
    //   110: aload 7
    //   112: invokevirtual 102	java/io/FileWriter:flush	()V
    //   115: aload 7
    //   117: ldc 104
    //   119: invokestatic 74	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   122: goto +67 -> 189
    //   125: astore_3
    //   126: aload 7
    //   128: astore 6
    //   130: goto +50 -> 180
    //   133: astore 6
    //   135: aload 7
    //   137: astore_3
    //   138: aload 6
    //   140: astore 7
    //   142: goto +12 -> 154
    //   145: astore_3
    //   146: goto +34 -> 180
    //   149: astore 7
    //   151: aload 4
    //   153: astore_3
    //   154: aload_3
    //   155: astore 6
    //   157: invokestatic 25	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   160: ldc 27
    //   162: ldc 106
    //   164: aload 7
    //   166: invokeinterface 80 4 0
    //   171: aload_3
    //   172: ldc 104
    //   174: invokestatic 74	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   177: goto +12 -> 189
    //   180: aload 6
    //   182: ldc 104
    //   184: invokestatic 74	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   187: aload_3
    //   188: athrow
    //   189: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	190	0	this	i
    //   0	190	1	paramLong	long
    //   0	190	3	paramJSONObject	org.json.JSONObject
    //   17	135	4	localObject1	Object
    //   20	71	5	localObject2	Object
    //   24	105	6	localObject3	Object
    //   133	6	6	localException1	Exception
    //   155	26	6	localJSONObject	org.json.JSONObject
    //   41	100	7	localObject4	Object
    //   149	16	7	localException2	Exception
    //   50	47	8	localFile	java.io.File
    //   59	22	9	localb	io.fabric.sdk.android.services.d.b
    // Exception table:
    //   from	to	target	type
    //   101	115	125	finally
    //   101	115	133	java/lang/Exception
    //   26	34	145	finally
    //   38	43	145	finally
    //   47	52	145	finally
    //   56	61	145	finally
    //   65	74	145	finally
    //   78	90	145	finally
    //   94	101	145	finally
    //   157	171	145	finally
    //   26	34	149	java/lang/Exception
    //   38	43	149	java/lang/Exception
    //   47	52	149	java/lang/Exception
    //   56	61	149	java/lang/Exception
    //   65	74	149	java/lang/Exception
    //   78	90	149	java/lang/Exception
    //   94	101	149	java/lang/Exception
  }
}


/* Location:              ~/io/fabric/sdk/android/services/e/i.class
 *
 * Reversed by:           J
 */