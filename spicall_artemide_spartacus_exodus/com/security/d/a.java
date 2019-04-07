package com.security.d;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.system.CoreApp;
import com.app.system.common.entity.FileEntry;
import com.app.system.common.f.a.f;
import com.app.system.common.f.a.h;
import com.security.ServiceSettings;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class a
{
  private static boolean a = false;
  private static final String b;
  private static final SimpleDateFormat c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
  private static ArrayList<String> d = new ArrayList();
  
  static
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(CoreApp.a().getFilesDir().getParentFile().getPath());
    localStringBuilder.append("/log");
    b = localStringBuilder.toString();
  }
  
  private static void a(int paramInt, String paramString1, String paramString2, Object... paramVarArgs)
  {
    Object localObject = paramString2;
    if (paramVarArgs.length > 0)
    {
      localObject = paramString2;
      if ((paramVarArgs[(paramVarArgs.length - 1)] instanceof Throwable))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString2);
        ((StringBuilder)localObject).append(" [Exc: %s]");
        localObject = ((StringBuilder)localObject).toString();
      }
    }
    if (paramVarArgs.length != 0) {
      localObject = String.format((String)localObject, paramVarArgs);
    }
    paramString2 = String.format("%s\t%s:\t%s", new Object[] { c.format(Long.valueOf(System.currentTimeMillis())), paramString1, localObject });
    if (paramInt >= 3) {
      a(paramString2);
    }
    if (d.contains(paramString1)) {
      b.a(paramString1, paramString2, "LOG");
    }
    Log.println(paramInt, paramString1, (String)localObject);
  }
  
  private static void a(File paramFile)
  {
    Object localObject = new SimpleDateFormat("yyyy.MM.dd-HH.mm", Locale.US);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(((SimpleDateFormat)localObject).format(Long.valueOf(paramFile.lastModified())));
    localStringBuilder.append(".log");
    localObject = localStringBuilder.toString();
    paramFile.renameTo(new File(paramFile.getParentFile(), (String)localObject));
  }
  
  /* Error */
  private static void a(String paramString)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 142	com/security/d/a:a	Z
    //   6: istore_1
    //   7: iload_1
    //   8: ifeq +7 -> 15
    //   11: ldc 2
    //   13: monitorexit
    //   14: return
    //   15: new 36	java/io/File
    //   18: astore_2
    //   19: aload_2
    //   20: getstatic 54	com/security/d/a:b	Ljava/lang/String;
    //   23: ldc -112
    //   25: invokespecial 147	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   28: aload_2
    //   29: invokevirtual 151	java/io/File:exists	()Z
    //   32: ifne +11 -> 43
    //   35: aload_2
    //   36: invokevirtual 39	java/io/File:getParentFile	()Ljava/io/File;
    //   39: invokevirtual 154	java/io/File:mkdirs	()Z
    //   42: pop
    //   43: aload_2
    //   44: invokestatic 156	com/security/d/a:c	(Ljava/io/File;)V
    //   47: new 158	java/io/FileWriter
    //   50: astore_3
    //   51: aload_3
    //   52: aload_2
    //   53: iconst_1
    //   54: invokespecial 161	java/io/FileWriter:<init>	(Ljava/io/File;Z)V
    //   57: aconst_null
    //   58: astore 4
    //   60: aload 4
    //   62: astore_2
    //   63: new 163	java/io/BufferedWriter
    //   66: astore 5
    //   68: aload 4
    //   70: astore_2
    //   71: aload 5
    //   73: aload_3
    //   74: invokespecial 166	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
    //   77: new 168	java/io/PrintWriter
    //   80: astore 6
    //   82: aload 6
    //   84: aload 5
    //   86: invokespecial 169	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   89: aload 6
    //   91: aload_0
    //   92: invokevirtual 171	java/io/PrintWriter:println	(Ljava/lang/String;)V
    //   95: aload 6
    //   97: invokevirtual 174	java/io/PrintWriter:close	()V
    //   100: aload 4
    //   102: astore_2
    //   103: aload 5
    //   105: invokevirtual 175	java/io/BufferedWriter:close	()V
    //   108: aload_3
    //   109: invokevirtual 176	java/io/FileWriter:close	()V
    //   112: goto +149 -> 261
    //   115: astore_2
    //   116: aconst_null
    //   117: astore_0
    //   118: goto +7 -> 125
    //   121: astore_0
    //   122: aload_0
    //   123: athrow
    //   124: astore_2
    //   125: aload_0
    //   126: ifnull +22 -> 148
    //   129: aload 6
    //   131: invokevirtual 174	java/io/PrintWriter:close	()V
    //   134: goto +19 -> 153
    //   137: astore 6
    //   139: aload_0
    //   140: aload 6
    //   142: invokevirtual 180	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   145: goto +8 -> 153
    //   148: aload 6
    //   150: invokevirtual 174	java/io/PrintWriter:close	()V
    //   153: aload_2
    //   154: athrow
    //   155: astore 6
    //   157: aconst_null
    //   158: astore_0
    //   159: goto +8 -> 167
    //   162: astore_0
    //   163: aload_0
    //   164: athrow
    //   165: astore 6
    //   167: aload_0
    //   168: ifnull +28 -> 196
    //   171: aload 4
    //   173: astore_2
    //   174: aload 5
    //   176: invokevirtual 175	java/io/BufferedWriter:close	()V
    //   179: goto +25 -> 204
    //   182: astore 5
    //   184: aload 4
    //   186: astore_2
    //   187: aload_0
    //   188: aload 5
    //   190: invokevirtual 180	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   193: goto +11 -> 204
    //   196: aload 4
    //   198: astore_2
    //   199: aload 5
    //   201: invokevirtual 175	java/io/BufferedWriter:close	()V
    //   204: aload 4
    //   206: astore_2
    //   207: aload 6
    //   209: athrow
    //   210: astore_0
    //   211: goto +8 -> 219
    //   214: astore_0
    //   215: aload_0
    //   216: astore_2
    //   217: aload_0
    //   218: athrow
    //   219: aload_2
    //   220: ifnull +21 -> 241
    //   223: aload_3
    //   224: invokevirtual 176	java/io/FileWriter:close	()V
    //   227: goto +18 -> 245
    //   230: astore 6
    //   232: aload_2
    //   233: aload 6
    //   235: invokevirtual 180	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   238: goto +7 -> 245
    //   241: aload_3
    //   242: invokevirtual 176	java/io/FileWriter:close	()V
    //   245: aload_0
    //   246: athrow
    //   247: astore_0
    //   248: iconst_1
    //   249: putstatic 142	com/security/d/a:a	Z
    //   252: ldc 112
    //   254: ldc -74
    //   256: aload_0
    //   257: invokestatic 186	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   260: pop
    //   261: ldc 2
    //   263: monitorexit
    //   264: return
    //   265: astore_0
    //   266: ldc 2
    //   268: monitorexit
    //   269: aload_0
    //   270: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	271	0	paramString	String
    //   6	2	1	bool	boolean
    //   18	85	2	localObject1	Object
    //   115	1	2	localObject2	Object
    //   124	30	2	localObject3	Object
    //   173	60	2	localObject4	Object
    //   50	192	3	localFileWriter	java.io.FileWriter
    //   58	147	4	localObject5	Object
    //   66	109	5	localBufferedWriter	java.io.BufferedWriter
    //   182	18	5	localThrowable1	Throwable
    //   80	50	6	localPrintWriter	java.io.PrintWriter
    //   137	12	6	localThrowable2	Throwable
    //   155	1	6	localObject6	Object
    //   165	43	6	localObject7	Object
    //   230	4	6	localThrowable3	Throwable
    // Exception table:
    //   from	to	target	type
    //   89	95	115	finally
    //   89	95	121	java/lang/Throwable
    //   122	124	124	finally
    //   129	134	137	java/lang/Throwable
    //   77	89	155	finally
    //   95	100	155	finally
    //   129	134	155	finally
    //   139	145	155	finally
    //   148	153	155	finally
    //   153	155	155	finally
    //   77	89	162	java/lang/Throwable
    //   95	100	162	java/lang/Throwable
    //   139	145	162	java/lang/Throwable
    //   148	153	162	java/lang/Throwable
    //   153	155	162	java/lang/Throwable
    //   163	165	165	finally
    //   174	179	182	java/lang/Throwable
    //   63	68	210	finally
    //   71	77	210	finally
    //   103	108	210	finally
    //   174	179	210	finally
    //   187	193	210	finally
    //   199	204	210	finally
    //   207	210	210	finally
    //   217	219	210	finally
    //   63	68	214	java/lang/Throwable
    //   71	77	214	java/lang/Throwable
    //   103	108	214	java/lang/Throwable
    //   187	193	214	java/lang/Throwable
    //   199	204	214	java/lang/Throwable
    //   207	210	214	java/lang/Throwable
    //   223	227	230	java/lang/Throwable
    //   47	57	247	java/io/IOException
    //   108	112	247	java/io/IOException
    //   223	227	247	java/io/IOException
    //   232	238	247	java/io/IOException
    //   241	245	247	java/io/IOException
    //   245	247	247	java/io/IOException
    //   3	7	265	finally
    //   15	43	265	finally
    //   43	47	265	finally
    //   47	57	265	finally
    //   108	112	265	finally
    //   223	227	265	finally
    //   232	238	265	finally
    //   241	245	265	finally
    //   245	247	265	finally
    //   248	261	265	finally
  }
  
  public static void a(String paramString1, String paramString2, Object... paramVarArgs)
  {
    a(6, paramString1, paramString2, paramVarArgs);
  }
  
  private static void b(File paramFile)
  {
    paramFile = new f(new FileEntry(paramFile));
    h.a().a(paramFile);
  }
  
  public static void b(String paramString1, String paramString2, Object... paramVarArgs)
  {
    a(5, paramString1, paramString2, paramVarArgs);
  }
  
  private static void c(File paramFile)
  {
    if ((paramFile.exists()) && (paramFile.length() > 131072L))
    {
      a(paramFile);
      File[] arrayOfFile = paramFile.getParentFile().listFiles();
      paramFile = new a[arrayOfFile.length];
      for (int i = 0; i < arrayOfFile.length; i++) {
        paramFile[i] = new a(arrayOfFile[i]);
      }
      Arrays.sort(paramFile);
      boolean bool = ServiceSettings.a().loggingEnabled;
      int j = 49;
      if (bool) {
        for (i = 0; (i < paramFile.length) && (i < 49); i++) {
          b(paramFile[i].b);
        }
      }
      i = j;
      if (CoreApp.a().getSharedPreferences("pref", 0).getBoolean("agent-active", false)) {
        h.a().a(CoreApp.a());
      }
      for (i = j; i < paramFile.length; i++) {
        paramFile[i].b.delete();
      }
    }
  }
  
  public static void c(String paramString1, String paramString2, Object... paramVarArgs)
  {
    a(4, paramString1, paramString2, paramVarArgs);
  }
  
  public static void d(String paramString1, String paramString2, Object... paramVarArgs)
  {
    a(3, paramString1, paramString2, paramVarArgs);
  }
  
  public static void e(String paramString1, String paramString2, Object... paramVarArgs)
  {
    a(2, paramString1, paramString2, paramVarArgs);
  }
  
  private static class a
    implements Comparable<a>
  {
    public long a;
    public File b;
    
    public a(File paramFile)
    {
      this.b = paramFile;
      this.a = paramFile.lastModified();
    }
    
    public int a(a parama)
    {
      long l1 = parama.a;
      long l2 = this.a;
      int i;
      if (l2 < l1) {
        i = -1;
      } else if (l2 == l1) {
        i = 0;
      } else {
        i = 1;
      }
      return i;
    }
  }
}


/* Location:              ~/com/security/d/a.class
 *
 * Reversed by:           J
 */