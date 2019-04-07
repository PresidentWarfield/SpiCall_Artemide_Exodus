package io.fabric.sdk.android.services.b;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Debug;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class i
{
  public static final Comparator<File> a = new Comparator()
  {
    public int a(File paramAnonymousFile1, File paramAnonymousFile2)
    {
      return (int)(paramAnonymousFile1.lastModified() - paramAnonymousFile2.lastModified());
    }
  };
  private static Boolean b;
  private static final char[] c = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  private static long d = -1L;
  
  public static int a()
  {
    return a.a().ordinal();
  }
  
  public static int a(Context paramContext, String paramString1, String paramString2)
  {
    return paramContext.getResources().getIdentifier(paramString1, paramString2, j(paramContext));
  }
  
  public static int a(Context paramContext, boolean paramBoolean)
  {
    paramContext = c(paramContext);
    if ((paramBoolean) && (paramContext != null))
    {
      if (paramContext.floatValue() >= 99.0D) {
        return 3;
      }
      if (paramContext.floatValue() < 99.0D) {
        return 2;
      }
      return 0;
    }
    return 1;
  }
  
  static long a(String paramString1, String paramString2, int paramInt)
  {
    return Long.parseLong(paramString1.split(paramString2)[0].trim()) * paramInt;
  }
  
  public static ActivityManager.RunningAppProcessInfo a(String paramString, Context paramContext)
  {
    paramContext = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses();
    if (paramContext != null)
    {
      Iterator localIterator = paramContext.iterator();
      while (localIterator.hasNext())
      {
        paramContext = (ActivityManager.RunningAppProcessInfo)localIterator.next();
        if (paramContext.processName.equals(paramString)) {
          return paramContext;
        }
      }
    }
    paramString = null;
    return paramString;
  }
  
  public static SharedPreferences a(Context paramContext)
  {
    return paramContext.getSharedPreferences("com.crashlytics.prefs", 0);
  }
  
  public static String a(int paramInt)
  {
    if (paramInt >= 0) {
      return String.format(Locale.US, "%1$10s", new Object[] { Integer.valueOf(paramInt) }).replace(' ', '0');
    }
    throw new IllegalArgumentException("value must be zero or greater");
  }
  
  /* Error */
  public static String a(File paramFile, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 184	java/io/File:exists	()Z
    //   4: istore_2
    //   5: aconst_null
    //   6: astore_3
    //   7: aconst_null
    //   8: astore 4
    //   10: iload_2
    //   11: ifeq +203 -> 214
    //   14: new 186	java/io/BufferedReader
    //   17: astore 5
    //   19: new 188	java/io/FileReader
    //   22: astore_3
    //   23: aload_3
    //   24: aload_0
    //   25: invokespecial 191	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   28: aload 5
    //   30: aload_3
    //   31: sipush 1024
    //   34: invokespecial 194	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   37: aload 5
    //   39: astore_3
    //   40: aload 5
    //   42: invokevirtual 197	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   45: astore 6
    //   47: aload 5
    //   49: astore 7
    //   51: aload 4
    //   53: astore_3
    //   54: aload 6
    //   56: ifnull +52 -> 108
    //   59: aload 5
    //   61: astore_3
    //   62: ldc -57
    //   64: invokestatic 205	java/util/regex/Pattern:compile	(Ljava/lang/String;)Ljava/util/regex/Pattern;
    //   67: aload 6
    //   69: iconst_2
    //   70: invokevirtual 208	java/util/regex/Pattern:split	(Ljava/lang/CharSequence;I)[Ljava/lang/String;
    //   73: astore 7
    //   75: aload 5
    //   77: astore_3
    //   78: aload 7
    //   80: arraylength
    //   81: iconst_1
    //   82: if_icmple -45 -> 37
    //   85: aload 5
    //   87: astore_3
    //   88: aload 7
    //   90: iconst_0
    //   91: aaload
    //   92: aload_1
    //   93: invokevirtual 139	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   96: ifeq -59 -> 37
    //   99: aload 7
    //   101: iconst_1
    //   102: aaload
    //   103: astore_3
    //   104: aload 5
    //   106: astore 7
    //   108: aload 7
    //   110: ldc -46
    //   112: invokestatic 213	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   115: goto +99 -> 214
    //   118: astore_3
    //   119: aload 5
    //   121: astore_1
    //   122: aload_3
    //   123: astore 5
    //   125: goto +13 -> 138
    //   128: astore_0
    //   129: aconst_null
    //   130: astore_3
    //   131: goto +75 -> 206
    //   134: astore 5
    //   136: aconst_null
    //   137: astore_1
    //   138: aload_1
    //   139: astore_3
    //   140: invokestatic 219	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   143: astore 7
    //   145: aload_1
    //   146: astore_3
    //   147: new 221	java/lang/StringBuilder
    //   150: astore 6
    //   152: aload_1
    //   153: astore_3
    //   154: aload 6
    //   156: invokespecial 222	java/lang/StringBuilder:<init>	()V
    //   159: aload_1
    //   160: astore_3
    //   161: aload 6
    //   163: ldc -32
    //   165: invokevirtual 228	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   168: pop
    //   169: aload_1
    //   170: astore_3
    //   171: aload 6
    //   173: aload_0
    //   174: invokevirtual 231	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   177: pop
    //   178: aload_1
    //   179: astore_3
    //   180: aload 7
    //   182: ldc -23
    //   184: aload 6
    //   186: invokevirtual 236	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   189: aload 5
    //   191: invokeinterface 242 4 0
    //   196: aload_1
    //   197: astore 7
    //   199: aload 4
    //   201: astore_3
    //   202: goto -94 -> 108
    //   205: astore_0
    //   206: aload_3
    //   207: ldc -46
    //   209: invokestatic 213	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   212: aload_0
    //   213: athrow
    //   214: aload_3
    //   215: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	216	0	paramFile	File
    //   0	216	1	paramString	String
    //   4	7	2	bool	boolean
    //   6	98	3	localObject1	Object
    //   118	5	3	localException1	Exception
    //   130	85	3	localObject2	Object
    //   8	192	4	localObject3	Object
    //   17	107	5	localObject4	Object
    //   134	56	5	localException2	Exception
    //   45	140	6	localObject5	Object
    //   49	149	7	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   40	47	118	java/lang/Exception
    //   62	75	118	java/lang/Exception
    //   78	85	118	java/lang/Exception
    //   88	99	118	java/lang/Exception
    //   14	37	128	finally
    //   14	37	134	java/lang/Exception
    //   40	47	205	finally
    //   62	75	205	finally
    //   78	85	205	finally
    //   88	99	205	finally
    //   140	145	205	finally
    //   147	152	205	finally
    //   154	159	205	finally
    //   161	169	205	finally
    //   171	178	205	finally
    //   180	196	205	finally
  }
  
  public static String a(InputStream paramInputStream)
  {
    paramInputStream = new Scanner(paramInputStream).useDelimiter("\\A");
    if (paramInputStream.hasNext()) {
      paramInputStream = paramInputStream.next();
    } else {
      paramInputStream = "";
    }
    return paramInputStream;
  }
  
  private static String a(InputStream paramInputStream, String paramString)
  {
    try
    {
      paramString = MessageDigest.getInstance(paramString);
      byte[] arrayOfByte = new byte['Ѐ'];
      for (;;)
      {
        int i = paramInputStream.read(arrayOfByte);
        if (i == -1) {
          break;
        }
        paramString.update(arrayOfByte, 0, i);
      }
      paramInputStream = a(paramString.digest());
      return paramInputStream;
    }
    catch (Exception paramInputStream)
    {
      c.g().e("Fabric", "Could not calculate hash for app icon.", paramInputStream);
    }
    return "";
  }
  
  public static String a(String paramString)
  {
    return a(paramString, "SHA-1");
  }
  
  private static String a(String paramString1, String paramString2)
  {
    return a(paramString1.getBytes(), paramString2);
  }
  
  public static String a(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar1 = new char[paramArrayOfByte.length * 2];
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      int j = paramArrayOfByte[i] & 0xFF;
      int k = i * 2;
      char[] arrayOfChar2 = c;
      arrayOfChar1[k] = ((char)arrayOfChar2[(j >>> 4)]);
      arrayOfChar1[(k + 1)] = ((char)arrayOfChar2[(j & 0xF)]);
    }
    return new String(arrayOfChar1);
  }
  
  private static String a(byte[] paramArrayOfByte, String paramString)
  {
    try
    {
      localObject = MessageDigest.getInstance(paramString);
      ((MessageDigest)localObject).update(paramArrayOfByte);
      return a(((MessageDigest)localObject).digest());
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      paramArrayOfByte = c.g();
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Could not create hashing algorithm: ");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(", returning empty string.");
      paramArrayOfByte.e("Fabric", ((StringBuilder)localObject).toString(), localNoSuchAlgorithmException);
    }
    return "";
  }
  
  public static String a(String... paramVarArgs)
  {
    Object localObject1 = null;
    if ((paramVarArgs != null) && (paramVarArgs.length != 0))
    {
      Object localObject2 = new ArrayList();
      int i = paramVarArgs.length;
      for (int j = 0; j < i; j++)
      {
        String str = paramVarArgs[j];
        if (str != null) {
          ((List)localObject2).add(str.replace("-", "").toLowerCase(Locale.US));
        }
      }
      Collections.sort((List)localObject2);
      paramVarArgs = new StringBuilder();
      localObject2 = ((List)localObject2).iterator();
      while (((Iterator)localObject2).hasNext()) {
        paramVarArgs.append((String)((Iterator)localObject2).next());
      }
      localObject2 = paramVarArgs.toString();
      paramVarArgs = (String[])localObject1;
      if (((String)localObject2).length() > 0) {
        paramVarArgs = a((String)localObject2);
      }
      return paramVarArgs;
    }
    return null;
  }
  
  public static void a(Context paramContext, int paramInt, String paramString1, String paramString2)
  {
    if (e(paramContext)) {
      c.g().a(paramInt, "Fabric", paramString2);
    }
  }
  
  public static void a(Context paramContext, String paramString)
  {
    if (e(paramContext)) {
      c.g().a("Fabric", paramString);
    }
  }
  
  public static void a(Context paramContext, String paramString, Throwable paramThrowable)
  {
    if (e(paramContext)) {
      c.g().e("Fabric", paramString);
    }
  }
  
  public static void a(Closeable paramCloseable)
  {
    if (paramCloseable != null) {}
    try
    {
      try
      {
        paramCloseable.close();
      }
      catch (RuntimeException paramCloseable)
      {
        throw paramCloseable;
      }
      return;
    }
    catch (Exception paramCloseable)
    {
      for (;;) {}
    }
  }
  
  public static void a(Closeable paramCloseable, String paramString)
  {
    if (paramCloseable != null) {
      try
      {
        paramCloseable.close();
      }
      catch (IOException paramCloseable)
      {
        c.g().e("Fabric", paramString, paramCloseable);
      }
    }
  }
  
  public static void a(Flushable paramFlushable, String paramString)
  {
    if (paramFlushable != null) {
      try
      {
        paramFlushable.flush();
      }
      catch (IOException paramFlushable)
      {
        c.g().e("Fabric", paramString, paramFlushable);
      }
    }
  }
  
  public static void a(InputStream paramInputStream, OutputStream paramOutputStream, byte[] paramArrayOfByte)
  {
    for (;;)
    {
      int i = paramInputStream.read(paramArrayOfByte);
      if (i == -1) {
        break;
      }
      paramOutputStream.write(paramArrayOfByte, 0, i);
    }
  }
  
  public static boolean a(Context paramContext, String paramString, boolean paramBoolean)
  {
    if (paramContext != null)
    {
      Resources localResources = paramContext.getResources();
      if (localResources != null)
      {
        int i = a(paramContext, paramString, "bool");
        if (i > 0) {
          return localResources.getBoolean(i);
        }
        i = a(paramContext, paramString, "string");
        if (i > 0) {
          return Boolean.parseBoolean(paramContext.getString(i));
        }
      }
    }
    return paramBoolean;
  }
  
  public static long b()
  {
    try
    {
      if (d == -1L)
      {
        long l1 = 0L;
        Object localObject1 = new java/io/File;
        ((File)localObject1).<init>("/proc/meminfo");
        localObject1 = a((File)localObject1, "MemTotal");
        l2 = l1;
        if (!TextUtils.isEmpty((CharSequence)localObject1))
        {
          localObject1 = ((String)localObject1).toUpperCase(Locale.US);
          try
          {
            if (((String)localObject1).endsWith("KB"))
            {
              l2 = a((String)localObject1, "KB", 1024);
            }
            else if (((String)localObject1).endsWith("MB"))
            {
              l2 = a((String)localObject1, "MB", 1048576);
            }
            else if (((String)localObject1).endsWith("GB"))
            {
              l2 = a((String)localObject1, "GB", 1073741824);
            }
            else
            {
              localk = c.g();
              StringBuilder localStringBuilder1 = new java/lang/StringBuilder;
              localStringBuilder1.<init>();
              localStringBuilder1.append("Unexpected meminfo format while computing RAM: ");
              localStringBuilder1.append((String)localObject1);
              localk.a("Fabric", localStringBuilder1.toString());
              l2 = l1;
            }
          }
          catch (NumberFormatException localNumberFormatException)
          {
            k localk = c.g();
            StringBuilder localStringBuilder2 = new java/lang/StringBuilder;
            localStringBuilder2.<init>();
            localStringBuilder2.append("Unexpected meminfo format while computing RAM: ");
            localStringBuilder2.append((String)localObject1);
            localk.e("Fabric", localStringBuilder2.toString(), localNumberFormatException);
            l2 = l1;
          }
        }
        d = l2;
      }
      long l2 = d;
      return l2;
    }
    finally {}
  }
  
  public static long b(Context paramContext)
  {
    ActivityManager.MemoryInfo localMemoryInfo = new ActivityManager.MemoryInfo();
    ((ActivityManager)paramContext.getSystemService("activity")).getMemoryInfo(localMemoryInfo);
    return localMemoryInfo.availMem;
  }
  
  public static String b(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "?";
    case 7: 
      return "A";
    case 6: 
      return "E";
    case 5: 
      return "W";
    case 4: 
      return "I";
    case 3: 
      return "D";
    }
    return "V";
  }
  
  public static String b(Context paramContext, String paramString)
  {
    int i = a(paramContext, paramString, "string");
    if (i > 0) {
      return paramContext.getString(i);
    }
    return "";
  }
  
  public static String b(InputStream paramInputStream)
  {
    return a(paramInputStream, "SHA-1");
  }
  
  public static String b(String paramString)
  {
    return a(paramString, "SHA-256");
  }
  
  public static long c(String paramString)
  {
    paramString = new StatFs(paramString);
    long l = paramString.getBlockSize();
    return paramString.getBlockCount() * l - l * paramString.getAvailableBlocks();
  }
  
  public static Float c(Context paramContext)
  {
    paramContext = paramContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    if (paramContext == null) {
      return null;
    }
    int i = paramContext.getIntExtra("level", -1);
    int j = paramContext.getIntExtra("scale", -1);
    return Float.valueOf(i / j);
  }
  
  public static boolean c()
  {
    boolean bool;
    if ((!Debug.isDebuggerConnected()) && (!Debug.waitingForDebugger())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static boolean c(Context paramContext, String paramString)
  {
    boolean bool;
    if (paramContext.checkCallingOrSelfPermission(paramString) == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean d(Context paramContext)
  {
    boolean bool1 = f(paramContext);
    boolean bool2 = false;
    if (bool1) {
      return false;
    }
    if (((SensorManager)paramContext.getSystemService("sensor")).getDefaultSensor(8) != null) {
      bool2 = true;
    }
    return bool2;
  }
  
  public static boolean d(String paramString)
  {
    boolean bool;
    if ((paramString != null) && (paramString.length() != 0)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static boolean e(Context paramContext)
  {
    if (b == null) {
      b = Boolean.valueOf(a(paramContext, "com.crashlytics.Trace", false));
    }
    return b.booleanValue();
  }
  
  public static boolean f(Context paramContext)
  {
    paramContext = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
    boolean bool;
    if ((!"sdk".equals(Build.PRODUCT)) && (!"google_sdk".equals(Build.PRODUCT)) && (paramContext != null)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static boolean g(Context paramContext)
  {
    boolean bool = f(paramContext);
    paramContext = Build.TAGS;
    if ((!bool) && (paramContext != null) && (paramContext.contains("test-keys"))) {
      return true;
    }
    if (new File("/system/app/Superuser.apk").exists()) {
      return true;
    }
    paramContext = new File("/system/xbin/su");
    return (!bool) && (paramContext.exists());
  }
  
  public static int h(Context paramContext)
  {
    if (f(paramContext)) {
      i = 1;
    } else {
      i = 0;
    }
    int j = i;
    if (g(paramContext)) {
      j = i | 0x2;
    }
    int i = j;
    if (c()) {
      i = j | 0x4;
    }
    return i;
  }
  
  public static boolean i(Context paramContext)
  {
    boolean bool;
    if ((paramContext.getApplicationInfo().flags & 0x2) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static String j(Context paramContext)
  {
    int i = paramContext.getApplicationContext().getApplicationInfo().icon;
    if (i > 0) {
      return paramContext.getResources().getResourcePackageName(i);
    }
    return paramContext.getPackageName();
  }
  
  /* Error */
  public static String k(Context paramContext)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: invokevirtual 61	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   6: aload_0
    //   7: invokestatic 591	io/fabric/sdk/android/services/b/i:l	(Landroid/content/Context;)I
    //   10: invokevirtual 595	android/content/res/Resources:openRawResource	(I)Ljava/io/InputStream;
    //   13: astore_2
    //   14: aload_2
    //   15: astore_0
    //   16: aload_2
    //   17: invokestatic 597	io/fabric/sdk/android/services/b/i:b	(Ljava/io/InputStream;)Ljava/lang/String;
    //   20: astore_3
    //   21: aload_2
    //   22: astore_0
    //   23: aload_3
    //   24: invokestatic 599	io/fabric/sdk/android/services/b/i:d	(Ljava/lang/String;)Z
    //   27: istore 4
    //   29: iload 4
    //   31: ifeq +8 -> 39
    //   34: aload_1
    //   35: astore_0
    //   36: goto +5 -> 41
    //   39: aload_3
    //   40: astore_0
    //   41: aload_2
    //   42: ldc_w 601
    //   45: invokestatic 213	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   48: aload_0
    //   49: areturn
    //   50: astore_1
    //   51: goto +12 -> 63
    //   54: astore_2
    //   55: aconst_null
    //   56: astore_0
    //   57: goto +32 -> 89
    //   60: astore_1
    //   61: aconst_null
    //   62: astore_2
    //   63: aload_2
    //   64: astore_0
    //   65: invokestatic 219	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   68: ldc -23
    //   70: ldc_w 285
    //   73: aload_1
    //   74: invokeinterface 242 4 0
    //   79: aload_2
    //   80: ldc_w 601
    //   83: invokestatic 213	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   86: aconst_null
    //   87: areturn
    //   88: astore_2
    //   89: aload_0
    //   90: ldc_w 601
    //   93: invokestatic 213	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   96: aload_2
    //   97: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	98	0	paramContext	Context
    //   1	34	1	localObject1	Object
    //   50	1	1	localException1	Exception
    //   60	14	1	localException2	Exception
    //   13	29	2	localInputStream	InputStream
    //   54	1	2	localObject2	Object
    //   62	18	2	localCloseable	Closeable
    //   88	9	2	localObject3	Object
    //   20	20	3	str	String
    //   27	3	4	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   16	21	50	java/lang/Exception
    //   23	29	50	java/lang/Exception
    //   2	14	54	finally
    //   2	14	60	java/lang/Exception
    //   16	21	88	finally
    //   23	29	88	finally
    //   65	79	88	finally
  }
  
  public static int l(Context paramContext)
  {
    return paramContext.getApplicationContext().getApplicationInfo().icon;
  }
  
  public static String m(Context paramContext)
  {
    int i = a(paramContext, "io.fabric.android.build_id", "string");
    int j = i;
    if (i == 0) {
      j = a(paramContext, "com.crashlytics.android.build_id", "string");
    }
    if (j != 0)
    {
      paramContext = paramContext.getResources().getString(j);
      k localk = c.g();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Build ID is: ");
      localStringBuilder.append(paramContext);
      localk.a("Fabric", localStringBuilder.toString());
    }
    else
    {
      paramContext = null;
    }
    return paramContext;
  }
  
  @SuppressLint({"MissingPermission"})
  public static boolean n(Context paramContext)
  {
    boolean bool1 = c(paramContext, "android.permission.ACCESS_NETWORK_STATE");
    boolean bool2 = true;
    if (bool1)
    {
      paramContext = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if ((paramContext == null) || (!paramContext.isConnectedOrConnecting())) {
        bool2 = false;
      }
      return bool2;
    }
    return true;
  }
  
  static enum a
  {
    private static final Map<String, a> k;
    
    static
    {
      k = new HashMap(4);
      k.put("armeabi-v7a", g);
      k.put("armeabi", f);
      k.put("arm64-v8a", j);
      k.put("x86", a);
    }
    
    private a() {}
    
    static a a()
    {
      Object localObject = Build.CPU_ABI;
      if (TextUtils.isEmpty((CharSequence)localObject))
      {
        c.g().a("Fabric", "Architecture#getValue()::Build.CPU_ABI returned null or empty");
        return h;
      }
      localObject = ((String)localObject).toLowerCase(Locale.US);
      a locala = (a)k.get(localObject);
      localObject = locala;
      if (locala == null) {
        localObject = h;
      }
      return (a)localObject;
    }
  }
}


/* Location:              ~/io/fabric/sdk/android/services/b/i.class
 *
 * Reversed by:           J
 */