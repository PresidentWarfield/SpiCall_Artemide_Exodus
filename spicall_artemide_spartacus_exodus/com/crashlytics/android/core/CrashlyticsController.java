package com.crashlytics.android.core;

import android.app.Activity;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.EventLogger;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.h;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.i;
import io.fabric.sdk.android.services.b.j.a;
import io.fabric.sdk.android.services.b.j.b;
import io.fabric.sdk.android.services.b.l;
import io.fabric.sdk.android.services.b.r;
import io.fabric.sdk.android.services.d.a;
import io.fabric.sdk.android.services.e.e;
import io.fabric.sdk.android.services.e.m;
import io.fabric.sdk.android.services.e.o;
import io.fabric.sdk.android.services.e.p;
import io.fabric.sdk.android.services.e.t;
import io.fabric.sdk.android.services.network.d;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

class CrashlyticsController
{
  private static final int ANALYZER_VERSION = 1;
  private static final String COLLECT_CUSTOM_KEYS = "com.crashlytics.CollectCustomKeys";
  private static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
  private static final String EVENT_TYPE_CRASH = "crash";
  private static final String EVENT_TYPE_LOGGED = "error";
  static final String FATAL_SESSION_DIR = "fatal-sessions";
  static final String FIREBASE_ANALYTICS_ORIGIN_CRASHLYTICS = "clx";
  static final String FIREBASE_APPLICATION_EXCEPTION = "_ae";
  static final String FIREBASE_CRASH_TYPE = "fatal";
  static final int FIREBASE_CRASH_TYPE_FATAL = 1;
  static final String FIREBASE_REALTIME = "_r";
  static final String FIREBASE_TIMESTAMP = "timestamp";
  private static final String GENERATOR_FORMAT = "Crashlytics Android SDK/%s";
  private static final String[] INITIAL_SESSION_PART_TAGS = { "SessionUser", "SessionApp", "SessionOS", "SessionDevice" };
  static final String INVALID_CLS_CACHE_DIR = "invalidClsFiles";
  static final Comparator<File> LARGEST_FILE_NAME_FIRST;
  static final int MAX_INVALID_SESSIONS = 4;
  private static final int MAX_LOCAL_LOGGED_EXCEPTIONS = 64;
  static final int MAX_OPEN_SESSIONS = 8;
  static final int MAX_STACK_SIZE = 1024;
  static final String NONFATAL_SESSION_DIR = "nonfatal-sessions";
  static final int NUM_STACK_REPETITIONS_ALLOWED = 10;
  private static final Map<String, String> SEND_AT_CRASHTIME_HEADER;
  static final String SESSION_APP_TAG = "SessionApp";
  static final FilenameFilter SESSION_BEGIN_FILE_FILTER = new FileNameContainsFilter("BeginSession")
  {
    public boolean accept(File paramAnonymousFile, String paramAnonymousString)
    {
      boolean bool;
      if ((super.accept(paramAnonymousFile, paramAnonymousString)) && (paramAnonymousString.endsWith(".cls"))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  };
  static final String SESSION_BEGIN_TAG = "BeginSession";
  static final String SESSION_DEVICE_TAG = "SessionDevice";
  static final FileFilter SESSION_DIRECTORY_FILTER;
  static final String SESSION_EVENT_MISSING_BINARY_IMGS_TAG = "SessionMissingBinaryImages";
  static final String SESSION_FATAL_TAG = "SessionCrash";
  static final FilenameFilter SESSION_FILE_FILTER = new FilenameFilter()
  {
    public boolean accept(File paramAnonymousFile, String paramAnonymousString)
    {
      boolean bool;
      if ((paramAnonymousString.length() == 39) && (paramAnonymousString.endsWith(".cls"))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  };
  private static final Pattern SESSION_FILE_PATTERN;
  private static final int SESSION_ID_LENGTH = 35;
  static final String SESSION_JSON_SUFFIX = ".json";
  static final String SESSION_NON_FATAL_TAG = "SessionEvent";
  static final String SESSION_OS_TAG = "SessionOS";
  static final String SESSION_USER_TAG = "SessionUser";
  private static final boolean SHOULD_PROMPT_BEFORE_SENDING_REPORTS_DEFAULT = false;
  static final Comparator<File> SMALLEST_FILE_NAME_FIRST;
  private final AppData appData;
  private final AppMeasurementEventListenerRegistrar appMeasurementEventListenerRegistrar;
  private final CrashlyticsBackgroundWorker backgroundWorker;
  private CrashlyticsUncaughtExceptionHandler crashHandler;
  private final CrashlyticsCore crashlyticsCore;
  private final DevicePowerStateListener devicePowerStateListener;
  private final AtomicInteger eventCounter = new AtomicInteger(0);
  private final a fileStore;
  private final EventLogger firebaseAnalyticsLogger;
  private final ReportUploader.HandlingExceptionCheck handlingExceptionCheck;
  private final d httpRequestFactory;
  private final r idManager;
  private final LogFileDirectoryProvider logFileDirectoryProvider;
  private final LogFileManager logFileManager;
  private final PreferenceManager preferenceManager;
  private final ReportUploader.ReportFilesProvider reportFilesProvider;
  private final StackTraceTrimmingStrategy stackTraceTrimmingStrategy;
  private final String unityVersion;
  
  static
  {
    SESSION_DIRECTORY_FILTER = new FileFilter()
    {
      public boolean accept(File paramAnonymousFile)
      {
        boolean bool;
        if ((paramAnonymousFile.isDirectory()) && (paramAnonymousFile.getName().length() == 35)) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
    };
    LARGEST_FILE_NAME_FIRST = new Comparator()
    {
      public int compare(File paramAnonymousFile1, File paramAnonymousFile2)
      {
        return paramAnonymousFile2.getName().compareTo(paramAnonymousFile1.getName());
      }
    };
    SMALLEST_FILE_NAME_FIRST = new Comparator()
    {
      public int compare(File paramAnonymousFile1, File paramAnonymousFile2)
      {
        return paramAnonymousFile1.getName().compareTo(paramAnonymousFile2.getName());
      }
    };
    SESSION_FILE_PATTERN = Pattern.compile("([\\d|A-Z|a-z]{12}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{12}).+");
    SEND_AT_CRASHTIME_HEADER = Collections.singletonMap("X-CRASHLYTICS-SEND-FLAGS", "1");
  }
  
  CrashlyticsController(CrashlyticsCore paramCrashlyticsCore, CrashlyticsBackgroundWorker paramCrashlyticsBackgroundWorker, d paramd, r paramr, PreferenceManager paramPreferenceManager, a parama, AppData paramAppData, UnityVersionProvider paramUnityVersionProvider, AppMeasurementEventListenerRegistrar paramAppMeasurementEventListenerRegistrar, EventLogger paramEventLogger)
  {
    this.crashlyticsCore = paramCrashlyticsCore;
    this.backgroundWorker = paramCrashlyticsBackgroundWorker;
    this.httpRequestFactory = paramd;
    this.idManager = paramr;
    this.preferenceManager = paramPreferenceManager;
    this.fileStore = parama;
    this.appData = paramAppData;
    this.unityVersion = paramUnityVersionProvider.getUnityVersion();
    this.appMeasurementEventListenerRegistrar = paramAppMeasurementEventListenerRegistrar;
    this.firebaseAnalyticsLogger = paramEventLogger;
    paramCrashlyticsCore = paramCrashlyticsCore.getContext();
    this.logFileDirectoryProvider = new LogFileDirectoryProvider(parama);
    this.logFileManager = new LogFileManager(paramCrashlyticsCore, this.logFileDirectoryProvider);
    this.reportFilesProvider = new ReportUploaderFilesProvider(null);
    this.handlingExceptionCheck = new ReportUploaderHandlingExceptionCheck(null);
    this.devicePowerStateListener = new DevicePowerStateListener(paramCrashlyticsCore);
    this.stackTraceTrimmingStrategy = new MiddleOutFallbackStrategy(1024, new StackTraceTrimmingStrategy[] { new RemoveRepeatsStrategy(10) });
  }
  
  private void closeOpenSessions(File[] paramArrayOfFile, int paramInt1, int paramInt2)
  {
    c.g().a("CrashlyticsCore", "Closing open sessions.");
    while (paramInt1 < paramArrayOfFile.length)
    {
      File localFile = paramArrayOfFile[paramInt1];
      String str = getSessionIdFromSessionFile(localFile);
      k localk = c.g();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Closing session: ");
      localStringBuilder.append(str);
      localk.a("CrashlyticsCore", localStringBuilder.toString());
      writeSessionPartsToSessionFile(localFile, str, paramInt2);
      paramInt1++;
    }
  }
  
  private void closeWithoutRenamingOrLog(ClsFileOutputStream paramClsFileOutputStream)
  {
    if (paramClsFileOutputStream == null) {
      return;
    }
    try
    {
      paramClsFileOutputStream.closeInProgressStream();
    }
    catch (IOException paramClsFileOutputStream)
    {
      c.g().e("CrashlyticsCore", "Error closing session file stream in the presence of an exception", paramClsFileOutputStream);
    }
  }
  
  private static void copyToCodedOutputStream(InputStream paramInputStream, CodedOutputStream paramCodedOutputStream, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    paramInt = 0;
    while (paramInt < arrayOfByte.length)
    {
      int i = paramInputStream.read(arrayOfByte, paramInt, arrayOfByte.length - paramInt);
      if (i < 0) {
        break;
      }
      paramInt += i;
    }
    paramCodedOutputStream.writeRawBytes(arrayOfByte);
  }
  
  private void deleteSessionPartFilesFor(String paramString)
  {
    paramString = listSessionPartFilesFor(paramString);
    int i = paramString.length;
    for (int j = 0; j < i; j++) {
      paramString[j].delete();
    }
  }
  
  private void doCloseSessions(p paramp, boolean paramBoolean)
  {
    trimOpenSessions(paramBoolean + true);
    File[] arrayOfFile = listSortedSessionBeginFiles();
    if (arrayOfFile.length <= paramBoolean)
    {
      c.g().a("CrashlyticsCore", "No open sessions to be closed.");
      return;
    }
    writeSessionUser(getSessionIdFromSessionFile(arrayOfFile[paramBoolean]));
    if (paramp == null)
    {
      c.g().a("CrashlyticsCore", "Unable to close session. Settings are not loaded.");
      return;
    }
    closeOpenSessions(arrayOfFile, paramBoolean, paramp.c);
  }
  
  private void doOpenSession()
  {
    Date localDate = new Date();
    String str = new CLSUUID(this.idManager).toString();
    k localk = c.g();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Opening a new session with ID ");
    localStringBuilder.append(str);
    localk.a("CrashlyticsCore", localStringBuilder.toString());
    writeBeginSession(str, localDate);
    writeSessionApp(str);
    writeSessionOS(str);
    writeSessionDevice(str);
    this.logFileManager.setCurrentSession(str);
  }
  
  /* Error */
  private void doWriteNonFatal(Date paramDate, Thread paramThread, Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 382	com/crashlytics/android/core/CrashlyticsController:getCurrentSessionId	()Ljava/lang/String;
    //   4: astore 4
    //   6: aconst_null
    //   7: astore 5
    //   9: aconst_null
    //   10: astore 6
    //   12: aconst_null
    //   13: astore 7
    //   15: aconst_null
    //   16: astore 8
    //   18: aload 4
    //   20: ifnonnull +19 -> 39
    //   23: invokestatic 450	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   26: ldc_w 452
    //   29: ldc_w 572
    //   32: aconst_null
    //   33: invokeinterface 495 4 0
    //   38: return
    //   39: aload 4
    //   41: aload_3
    //   42: invokevirtual 576	java/lang/Object:getClass	()Ljava/lang/Class;
    //   45: invokevirtual 581	java/lang/Class:getName	()Ljava/lang/String;
    //   48: invokestatic 584	com/crashlytics/android/core/CrashlyticsController:recordLoggedExceptionAnswersEvent	(Ljava/lang/String;Ljava/lang/String;)V
    //   51: invokestatic 450	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   54: astore 9
    //   56: new 466	java/lang/StringBuilder
    //   59: astore 10
    //   61: aload 10
    //   63: invokespecial 467	java/lang/StringBuilder:<init>	()V
    //   66: aload 10
    //   68: ldc_w 586
    //   71: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: pop
    //   75: aload 10
    //   77: aload_3
    //   78: invokevirtual 589	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   81: pop
    //   82: aload 10
    //   84: ldc_w 591
    //   87: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   90: pop
    //   91: aload 10
    //   93: aload_2
    //   94: invokevirtual 594	java/lang/Thread:getName	()Ljava/lang/String;
    //   97: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: pop
    //   101: aload 9
    //   103: ldc_w 452
    //   106: aload 10
    //   108: invokevirtual 476	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   111: invokeinterface 460 3 0
    //   116: aload_0
    //   117: getfield 296	com/crashlytics/android/core/CrashlyticsController:eventCounter	Ljava/util/concurrent/atomic/AtomicInteger;
    //   120: invokevirtual 598	java/util/concurrent/atomic/AtomicInteger:getAndIncrement	()I
    //   123: invokestatic 603	io/fabric/sdk/android/services/b/i:a	(I)Ljava/lang/String;
    //   126: astore 10
    //   128: new 466	java/lang/StringBuilder
    //   131: astore 9
    //   133: aload 9
    //   135: invokespecial 467	java/lang/StringBuilder:<init>	()V
    //   138: aload 9
    //   140: aload 4
    //   142: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: pop
    //   146: aload 9
    //   148: ldc -62
    //   150: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   153: pop
    //   154: aload 9
    //   156: aload 10
    //   158: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   161: pop
    //   162: aload 9
    //   164: invokevirtual 476	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   167: astore 10
    //   169: new 486	com/crashlytics/android/core/ClsFileOutputStream
    //   172: astore 9
    //   174: aload 9
    //   176: aload_0
    //   177: invokevirtual 607	com/crashlytics/android/core/CrashlyticsController:getFilesDir	()Ljava/io/File;
    //   180: aload 10
    //   182: invokespecial 610	com/crashlytics/android/core/ClsFileOutputStream:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   185: aload 6
    //   187: astore 5
    //   189: aload 9
    //   191: astore 7
    //   193: aload 9
    //   195: invokestatic 614	com/crashlytics/android/core/CodedOutputStream:newInstance	(Ljava/io/OutputStream;)Lcom/crashlytics/android/core/CodedOutputStream;
    //   198: astore 6
    //   200: aload 6
    //   202: astore 8
    //   204: aload 6
    //   206: astore 5
    //   208: aload 9
    //   210: astore 7
    //   212: aload_0
    //   213: aload 6
    //   215: aload_1
    //   216: aload_2
    //   217: aload_3
    //   218: ldc 118
    //   220: iconst_0
    //   221: invokespecial 618	com/crashlytics/android/core/CrashlyticsController:writeSessionEvent	(Lcom/crashlytics/android/core/CodedOutputStream;Ljava/util/Date;Ljava/lang/Thread;Ljava/lang/Throwable;Ljava/lang/String;Z)V
    //   224: aload 6
    //   226: astore_1
    //   227: aload 9
    //   229: astore_2
    //   230: goto +53 -> 283
    //   233: astore_3
    //   234: aload 8
    //   236: astore_1
    //   237: aload 9
    //   239: astore_2
    //   240: goto +22 -> 262
    //   243: astore_1
    //   244: aconst_null
    //   245: astore_2
    //   246: aload 7
    //   248: astore 5
    //   250: aload_2
    //   251: astore 7
    //   253: goto +73 -> 326
    //   256: astore_3
    //   257: aconst_null
    //   258: astore_2
    //   259: aload 5
    //   261: astore_1
    //   262: aload_1
    //   263: astore 5
    //   265: aload_2
    //   266: astore 7
    //   268: invokestatic 450	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   271: ldc_w 452
    //   274: ldc_w 620
    //   277: aload_3
    //   278: invokeinterface 495 4 0
    //   283: aload_1
    //   284: ldc_w 622
    //   287: invokestatic 625	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   290: aload_2
    //   291: ldc_w 627
    //   294: invokestatic 630	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   297: aload_0
    //   298: aload 4
    //   300: bipush 64
    //   302: invokespecial 634	com/crashlytics/android/core/CrashlyticsController:trimSessionEventFiles	(Ljava/lang/String;I)V
    //   305: goto +19 -> 324
    //   308: astore_1
    //   309: invokestatic 450	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   312: ldc_w 452
    //   315: ldc_w 636
    //   318: aload_1
    //   319: invokeinterface 495 4 0
    //   324: return
    //   325: astore_1
    //   326: aload 5
    //   328: ldc_w 622
    //   331: invokestatic 625	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   334: aload 7
    //   336: ldc_w 627
    //   339: invokestatic 630	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   342: aload_1
    //   343: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	344	0	this	CrashlyticsController
    //   0	344	1	paramDate	Date
    //   0	344	2	paramThread	Thread
    //   0	344	3	paramThrowable	Throwable
    //   4	295	4	str	String
    //   7	320	5	localObject1	Object
    //   10	215	6	localCodedOutputStream1	CodedOutputStream
    //   13	322	7	localObject2	Object
    //   16	219	8	localCodedOutputStream2	CodedOutputStream
    //   54	184	9	localObject3	Object
    //   59	122	10	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   193	200	233	java/lang/Exception
    //   212	224	233	java/lang/Exception
    //   51	185	243	finally
    //   51	185	256	java/lang/Exception
    //   297	305	308	java/lang/Exception
    //   193	200	325	finally
    //   212	224	325	finally
    //   268	283	325	finally
  }
  
  private File[] ensureFileArrayNotNull(File[] paramArrayOfFile)
  {
    File[] arrayOfFile = paramArrayOfFile;
    if (paramArrayOfFile == null) {
      arrayOfFile = new File[0];
    }
    return arrayOfFile;
  }
  
  private void finalizeMostRecentNativeCrash(Context paramContext, File paramFile, String paramString)
  {
    byte[] arrayOfByte1 = NativeFileUtils.minidumpFromDirectory(paramFile);
    byte[] arrayOfByte2 = NativeFileUtils.metadataJsonFromDirectory(paramFile);
    paramContext = NativeFileUtils.binaryImagesJsonFromDirectory(paramFile, paramContext);
    if ((arrayOfByte1 != null) && (arrayOfByte1.length != 0))
    {
      recordFatalExceptionAnswersEvent(paramString, "<native-crash: minidump>");
      byte[] arrayOfByte3 = readFile(paramString, "BeginSession.json");
      byte[] arrayOfByte4 = readFile(paramString, "SessionApp.json");
      byte[] arrayOfByte5 = readFile(paramString, "SessionDevice.json");
      byte[] arrayOfByte6 = readFile(paramString, "SessionOS.json");
      paramFile = NativeFileUtils.readFile(new MetaDataStore(getFilesDir()).getUserDataFileForSession(paramString));
      Object localObject = new LogFileManager(this.crashlyticsCore.getContext(), this.logFileDirectoryProvider, paramString);
      byte[] arrayOfByte7 = ((LogFileManager)localObject).getBytesForLog();
      ((LogFileManager)localObject).clearLog();
      localObject = NativeFileUtils.readFile(new MetaDataStore(getFilesDir()).getKeysFileForSession(paramString));
      paramString = new File(this.fileStore.a(), paramString);
      if (!paramString.mkdir())
      {
        c.g().a("CrashlyticsCore", "Couldn't create native sessions directory");
        return;
      }
      gzipIfNotEmpty(arrayOfByte1, new File(paramString, "minidump"));
      gzipIfNotEmpty(arrayOfByte2, new File(paramString, "metadata"));
      gzipIfNotEmpty(paramContext, new File(paramString, "binaryImages"));
      gzipIfNotEmpty(arrayOfByte3, new File(paramString, "session"));
      gzipIfNotEmpty(arrayOfByte4, new File(paramString, "app"));
      gzipIfNotEmpty(arrayOfByte5, new File(paramString, "device"));
      gzipIfNotEmpty(arrayOfByte6, new File(paramString, "os"));
      gzipIfNotEmpty(paramFile, new File(paramString, "user"));
      gzipIfNotEmpty(arrayOfByte7, new File(paramString, "logs"));
      gzipIfNotEmpty((byte[])localObject, new File(paramString, "keys"));
      return;
    }
    paramString = c.g();
    paramContext = new StringBuilder();
    paramContext.append("No minidump data found in directory ");
    paramContext.append(paramFile);
    paramString.d("CrashlyticsCore", paramContext.toString());
  }
  
  private boolean firebaseCrashExists()
  {
    try
    {
      Class.forName("com.google.firebase.crash.FirebaseCrash");
      return true;
    }
    catch (ClassNotFoundException localClassNotFoundException) {}
    return false;
  }
  
  private CreateReportSpiCall getCreateReportSpiCall(String paramString1, String paramString2)
  {
    String str = i.b(this.crashlyticsCore.getContext(), "com.crashlytics.ApiEndpoint");
    return new CompositeCreateReportSpiCall(new DefaultCreateReportSpiCall(this.crashlyticsCore, str, paramString1, this.httpRequestFactory), new NativeCreateReportSpiCall(this.crashlyticsCore, str, paramString2, this.httpRequestFactory));
  }
  
  private String getCurrentSessionId()
  {
    Object localObject = listSortedSessionBeginFiles();
    if (localObject.length > 0) {
      localObject = getSessionIdFromSessionFile(localObject[0]);
    } else {
      localObject = null;
    }
    return (String)localObject;
  }
  
  private String getPreviousSessionId()
  {
    Object localObject = listSortedSessionBeginFiles();
    if (localObject.length > 1) {
      localObject = getSessionIdFromSessionFile(localObject[1]);
    } else {
      localObject = null;
    }
    return (String)localObject;
  }
  
  static String getSessionIdFromSessionFile(File paramFile)
  {
    return paramFile.getName().substring(0, 35);
  }
  
  private File[] getTrimmedNonFatalFiles(String paramString, File[] paramArrayOfFile, int paramInt)
  {
    File[] arrayOfFile = paramArrayOfFile;
    if (paramArrayOfFile.length > paramInt)
    {
      c.g().a("CrashlyticsCore", String.format(Locale.US, "Trimming down to %d logged exceptions.", new Object[] { Integer.valueOf(paramInt) }));
      trimSessionEventFiles(paramString, paramInt);
      paramArrayOfFile = new StringBuilder();
      paramArrayOfFile.append(paramString);
      paramArrayOfFile.append("SessionEvent");
      arrayOfFile = listFilesMatching(new FileNameContainsFilter(paramArrayOfFile.toString()));
    }
    return arrayOfFile;
  }
  
  private UserMetaData getUserMetaData(String paramString)
  {
    if (isHandlingException()) {
      paramString = new UserMetaData(this.crashlyticsCore.getUserIdentifier(), this.crashlyticsCore.getUserName(), this.crashlyticsCore.getUserEmail());
    } else {
      paramString = new MetaDataStore(getFilesDir()).readUserData(paramString);
    }
    return paramString;
  }
  
  /* Error */
  private void gzip(byte[] paramArrayOfByte, File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: new 810	java/util/zip/GZIPOutputStream
    //   5: astore 4
    //   7: new 812	java/io/FileOutputStream
    //   10: astore 5
    //   12: aload 5
    //   14: aload_2
    //   15: invokespecial 813	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   18: aload 4
    //   20: aload 5
    //   22: invokespecial 816	java/util/zip/GZIPOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   25: aload 4
    //   27: aload_1
    //   28: invokevirtual 819	java/util/zip/GZIPOutputStream:write	([B)V
    //   31: aload 4
    //   33: invokevirtual 822	java/util/zip/GZIPOutputStream:finish	()V
    //   36: aload 4
    //   38: invokestatic 825	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;)V
    //   41: return
    //   42: astore_1
    //   43: aload 4
    //   45: astore_2
    //   46: goto +6 -> 52
    //   49: astore_1
    //   50: aload_3
    //   51: astore_2
    //   52: aload_2
    //   53: invokestatic 825	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;)V
    //   56: aload_1
    //   57: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	58	0	this	CrashlyticsController
    //   0	58	1	paramArrayOfByte	byte[]
    //   0	58	2	paramFile	File
    //   1	50	3	localObject	Object
    //   5	39	4	localGZIPOutputStream	java.util.zip.GZIPOutputStream
    //   10	11	5	localFileOutputStream	FileOutputStream
    // Exception table:
    //   from	to	target	type
    //   25	36	42	finally
    //   2	25	49	finally
  }
  
  private void gzipIfNotEmpty(byte[] paramArrayOfByte, File paramFile)
  {
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0)) {
      gzip(paramArrayOfByte, paramFile);
    }
  }
  
  private File[] listFiles(File paramFile)
  {
    return ensureFileArrayNotNull(paramFile.listFiles());
  }
  
  private File[] listFilesMatching(File paramFile, FilenameFilter paramFilenameFilter)
  {
    return ensureFileArrayNotNull(paramFile.listFiles(paramFilenameFilter));
  }
  
  private File[] listFilesMatching(FileFilter paramFileFilter)
  {
    return ensureFileArrayNotNull(getFilesDir().listFiles(paramFileFilter));
  }
  
  private File[] listFilesMatching(FilenameFilter paramFilenameFilter)
  {
    return listFilesMatching(getFilesDir(), paramFilenameFilter);
  }
  
  private File[] listSessionPartFilesFor(String paramString)
  {
    return listFilesMatching(new SessionPartFileFilter(paramString));
  }
  
  private File[] listSortedSessionBeginFiles()
  {
    File[] arrayOfFile = listSessionBeginFiles();
    Arrays.sort(arrayOfFile, LARGEST_FILE_NAME_FIRST);
    return arrayOfFile;
  }
  
  private byte[] readFile(String paramString1, String paramString2)
  {
    File localFile = getFilesDir();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append(paramString2);
    return NativeFileUtils.readFile(new File(localFile, localStringBuilder.toString()));
  }
  
  private static void recordFatalExceptionAnswersEvent(String paramString1, String paramString2)
  {
    Answers localAnswers = (Answers)c.a(Answers.class);
    if (localAnswers == null)
    {
      c.g().a("CrashlyticsCore", "Answers is not available");
      return;
    }
    localAnswers.onException(new j.a(paramString1, paramString2));
  }
  
  private void recordFatalFirebaseEvent(long paramLong)
  {
    if (firebaseCrashExists())
    {
      c.g().a("CrashlyticsCore", "Skipping logging Crashlytics event to Firebase, FirebaseCrash exists");
      return;
    }
    if (this.firebaseAnalyticsLogger != null)
    {
      c.g().a("CrashlyticsCore", "Logging Crashlytics event to Firebase");
      Bundle localBundle = new Bundle();
      localBundle.putInt("_r", 1);
      localBundle.putInt("fatal", 1);
      localBundle.putLong("timestamp", paramLong);
      this.firebaseAnalyticsLogger.logEvent("clx", "_ae", localBundle);
    }
    else
    {
      c.g().a("CrashlyticsCore", "Skipping logging Crashlytics event to Firebase, no Firebase Analytics");
    }
  }
  
  private static void recordLoggedExceptionAnswersEvent(String paramString1, String paramString2)
  {
    Answers localAnswers = (Answers)c.a(Answers.class);
    if (localAnswers == null)
    {
      c.g().a("CrashlyticsCore", "Answers is not available");
      return;
    }
    localAnswers.onException(new j.b(paramString1, paramString2));
  }
  
  private void recursiveDelete(File paramFile)
  {
    if (paramFile.isDirectory())
    {
      File[] arrayOfFile = paramFile.listFiles();
      int i = arrayOfFile.length;
      for (int j = 0; j < i; j++) {
        recursiveDelete(arrayOfFile[j]);
      }
    }
    paramFile.delete();
  }
  
  private void recursiveDelete(Set<File> paramSet)
  {
    paramSet = paramSet.iterator();
    while (paramSet.hasNext()) {
      recursiveDelete((File)paramSet.next());
    }
  }
  
  private void retainSessions(File[] paramArrayOfFile, Set<String> paramSet)
  {
    int i = paramArrayOfFile.length;
    for (int j = 0; j < i; j++)
    {
      File localFile = paramArrayOfFile[j];
      String str = localFile.getName();
      Object localObject1 = SESSION_FILE_PATTERN.matcher(str);
      Object localObject2;
      if (!((Matcher)localObject1).matches())
      {
        localObject2 = c.g();
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Deleting unknown file: ");
        ((StringBuilder)localObject1).append(str);
        ((k)localObject2).a("CrashlyticsCore", ((StringBuilder)localObject1).toString());
        localFile.delete();
      }
      else if (!paramSet.contains(((Matcher)localObject1).group(1)))
      {
        localObject1 = c.g();
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("Trimming session file: ");
        ((StringBuilder)localObject2).append(str);
        ((k)localObject1).a("CrashlyticsCore", ((StringBuilder)localObject2).toString());
        localFile.delete();
      }
    }
  }
  
  private void sendSessionReports(t paramt)
  {
    if (paramt == null)
    {
      c.g().d("CrashlyticsCore", "Cannot send reports. Settings are unavailable.");
      return;
    }
    Context localContext = this.crashlyticsCore.getContext();
    paramt = getCreateReportSpiCall(paramt.a.d, paramt.a.e);
    ReportUploader localReportUploader = new ReportUploader(this.appData.apiKey, paramt, this.reportFilesProvider, this.handlingExceptionCheck);
    File[] arrayOfFile = listCompleteSessionFiles();
    int i = arrayOfFile.length;
    for (int j = 0; j < i; j++)
    {
      paramt = new SessionReport(arrayOfFile[j], SEND_AT_CRASHTIME_HEADER);
      this.backgroundWorker.submit(new SendReportRunnable(localContext, paramt, localReportUploader));
    }
  }
  
  private boolean shouldPromptUserBeforeSendingCrashReports(t paramt)
  {
    boolean bool1 = false;
    if (paramt == null) {
      return false;
    }
    boolean bool2 = bool1;
    if (paramt.d.a)
    {
      bool2 = bool1;
      if (!this.preferenceManager.shouldAlwaysSendReports()) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  /* Error */
  private void synthesizeSessionFile(File paramFile1, String paramString, File[] paramArrayOfFile, File paramFile2)
  {
    // Byte code:
    //   0: aload 4
    //   2: ifnull +9 -> 11
    //   5: iconst_1
    //   6: istore 5
    //   8: goto +6 -> 14
    //   11: iconst_0
    //   12: istore 5
    //   14: iload 5
    //   16: ifeq +12 -> 28
    //   19: aload_0
    //   20: invokevirtual 1000	com/crashlytics/android/core/CrashlyticsController:getFatalSessionFilesDir	()Ljava/io/File;
    //   23: astore 6
    //   25: goto +9 -> 34
    //   28: aload_0
    //   29: invokevirtual 1003	com/crashlytics/android/core/CrashlyticsController:getNonFatalSessionFilesDir	()Ljava/io/File;
    //   32: astore 6
    //   34: aload 6
    //   36: invokevirtual 1006	java/io/File:exists	()Z
    //   39: ifne +9 -> 48
    //   42: aload 6
    //   44: invokevirtual 1009	java/io/File:mkdirs	()Z
    //   47: pop
    //   48: aconst_null
    //   49: astore 7
    //   51: aconst_null
    //   52: astore 8
    //   54: aconst_null
    //   55: astore 9
    //   57: aconst_null
    //   58: astore 10
    //   60: new 486	com/crashlytics/android/core/ClsFileOutputStream
    //   63: astore 11
    //   65: aload 11
    //   67: aload 6
    //   69: aload_2
    //   70: invokespecial 610	com/crashlytics/android/core/ClsFileOutputStream:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   73: aload 10
    //   75: astore 7
    //   77: aload 8
    //   79: astore 6
    //   81: aload 11
    //   83: astore 9
    //   85: aload 11
    //   87: invokestatic 614	com/crashlytics/android/core/CodedOutputStream:newInstance	(Ljava/io/OutputStream;)Lcom/crashlytics/android/core/CodedOutputStream;
    //   90: astore 8
    //   92: aload 8
    //   94: astore 7
    //   96: aload 8
    //   98: astore 6
    //   100: aload 11
    //   102: astore 9
    //   104: invokestatic 450	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   107: astore 10
    //   109: aload 8
    //   111: astore 7
    //   113: aload 8
    //   115: astore 6
    //   117: aload 11
    //   119: astore 9
    //   121: new 466	java/lang/StringBuilder
    //   124: astore 12
    //   126: aload 8
    //   128: astore 7
    //   130: aload 8
    //   132: astore 6
    //   134: aload 11
    //   136: astore 9
    //   138: aload 12
    //   140: invokespecial 467	java/lang/StringBuilder:<init>	()V
    //   143: aload 8
    //   145: astore 7
    //   147: aload 8
    //   149: astore 6
    //   151: aload 11
    //   153: astore 9
    //   155: aload 12
    //   157: ldc_w 1011
    //   160: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   163: pop
    //   164: aload 8
    //   166: astore 7
    //   168: aload 8
    //   170: astore 6
    //   172: aload 11
    //   174: astore 9
    //   176: aload 12
    //   178: aload_2
    //   179: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   182: pop
    //   183: aload 8
    //   185: astore 7
    //   187: aload 8
    //   189: astore 6
    //   191: aload 11
    //   193: astore 9
    //   195: aload 10
    //   197: ldc_w 452
    //   200: aload 12
    //   202: invokevirtual 476	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   205: invokeinterface 460 3 0
    //   210: aload 8
    //   212: astore 7
    //   214: aload 8
    //   216: astore 6
    //   218: aload 11
    //   220: astore 9
    //   222: aload 8
    //   224: aload_1
    //   225: invokestatic 1015	com/crashlytics/android/core/CrashlyticsController:writeToCosFromFile	(Lcom/crashlytics/android/core/CodedOutputStream;Ljava/io/File;)V
    //   228: aload 8
    //   230: astore 7
    //   232: aload 8
    //   234: astore 6
    //   236: aload 11
    //   238: astore 9
    //   240: new 543	java/util/Date
    //   243: astore_1
    //   244: aload 8
    //   246: astore 7
    //   248: aload 8
    //   250: astore 6
    //   252: aload 11
    //   254: astore 9
    //   256: aload_1
    //   257: invokespecial 544	java/util/Date:<init>	()V
    //   260: aload 8
    //   262: astore 7
    //   264: aload 8
    //   266: astore 6
    //   268: aload 11
    //   270: astore 9
    //   272: aload 8
    //   274: iconst_4
    //   275: aload_1
    //   276: invokevirtual 1019	java/util/Date:getTime	()J
    //   279: ldc2_w 1020
    //   282: ldiv
    //   283: invokevirtual 1025	com/crashlytics/android/core/CodedOutputStream:writeUInt64	(IJ)V
    //   286: aload 8
    //   288: astore 7
    //   290: aload 8
    //   292: astore 6
    //   294: aload 11
    //   296: astore 9
    //   298: aload 8
    //   300: iconst_5
    //   301: iload 5
    //   303: invokevirtual 1029	com/crashlytics/android/core/CodedOutputStream:writeBool	(IZ)V
    //   306: aload 8
    //   308: astore 7
    //   310: aload 8
    //   312: astore 6
    //   314: aload 11
    //   316: astore 9
    //   318: aload 8
    //   320: bipush 11
    //   322: iconst_1
    //   323: invokevirtual 1033	com/crashlytics/android/core/CodedOutputStream:writeUInt32	(II)V
    //   326: aload 8
    //   328: astore 7
    //   330: aload 8
    //   332: astore 6
    //   334: aload 11
    //   336: astore 9
    //   338: aload 8
    //   340: bipush 12
    //   342: iconst_3
    //   343: invokevirtual 1036	com/crashlytics/android/core/CodedOutputStream:writeEnum	(II)V
    //   346: aload 8
    //   348: astore 7
    //   350: aload 8
    //   352: astore 6
    //   354: aload 11
    //   356: astore 9
    //   358: aload_0
    //   359: aload 8
    //   361: aload_2
    //   362: invokespecial 1040	com/crashlytics/android/core/CrashlyticsController:writeInitialPartsTo	(Lcom/crashlytics/android/core/CodedOutputStream;Ljava/lang/String;)V
    //   365: aload 8
    //   367: astore 7
    //   369: aload 8
    //   371: astore 6
    //   373: aload 11
    //   375: astore 9
    //   377: aload 8
    //   379: aload_3
    //   380: aload_2
    //   381: invokestatic 1044	com/crashlytics/android/core/CrashlyticsController:writeNonFatalEventsTo	(Lcom/crashlytics/android/core/CodedOutputStream;[Ljava/io/File;Ljava/lang/String;)V
    //   384: iload 5
    //   386: ifeq +22 -> 408
    //   389: aload 8
    //   391: astore 7
    //   393: aload 8
    //   395: astore 6
    //   397: aload 11
    //   399: astore 9
    //   401: aload 8
    //   403: aload 4
    //   405: invokestatic 1015	com/crashlytics/android/core/CrashlyticsController:writeToCosFromFile	(Lcom/crashlytics/android/core/CodedOutputStream;Ljava/io/File;)V
    //   408: aload 8
    //   410: ldc_w 1046
    //   413: invokestatic 625	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   416: aload 11
    //   418: ldc_w 1048
    //   421: invokestatic 630	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   424: goto +128 -> 552
    //   427: astore_3
    //   428: aload 11
    //   430: astore_1
    //   431: goto +19 -> 450
    //   434: astore_1
    //   435: aconst_null
    //   436: astore_2
    //   437: aload 9
    //   439: astore 6
    //   441: aload_2
    //   442: astore 9
    //   444: goto +110 -> 554
    //   447: astore_3
    //   448: aconst_null
    //   449: astore_1
    //   450: aload 7
    //   452: astore 6
    //   454: aload_1
    //   455: astore 9
    //   457: invokestatic 450	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   460: astore 4
    //   462: aload 7
    //   464: astore 6
    //   466: aload_1
    //   467: astore 9
    //   469: new 466	java/lang/StringBuilder
    //   472: astore 11
    //   474: aload 7
    //   476: astore 6
    //   478: aload_1
    //   479: astore 9
    //   481: aload 11
    //   483: invokespecial 467	java/lang/StringBuilder:<init>	()V
    //   486: aload 7
    //   488: astore 6
    //   490: aload_1
    //   491: astore 9
    //   493: aload 11
    //   495: ldc_w 1050
    //   498: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   501: pop
    //   502: aload 7
    //   504: astore 6
    //   506: aload_1
    //   507: astore 9
    //   509: aload 11
    //   511: aload_2
    //   512: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   515: pop
    //   516: aload 7
    //   518: astore 6
    //   520: aload_1
    //   521: astore 9
    //   523: aload 4
    //   525: ldc_w 452
    //   528: aload 11
    //   530: invokevirtual 476	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   533: aload_3
    //   534: invokeinterface 495 4 0
    //   539: aload 7
    //   541: ldc_w 1046
    //   544: invokestatic 625	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   547: aload_0
    //   548: aload_1
    //   549: invokespecial 1052	com/crashlytics/android/core/CrashlyticsController:closeWithoutRenamingOrLog	(Lcom/crashlytics/android/core/ClsFileOutputStream;)V
    //   552: return
    //   553: astore_1
    //   554: aload 6
    //   556: ldc_w 1046
    //   559: invokestatic 625	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   562: aload 9
    //   564: ldc_w 1048
    //   567: invokestatic 630	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   570: aload_1
    //   571: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	572	0	this	CrashlyticsController
    //   0	572	1	paramFile1	File
    //   0	572	2	paramString	String
    //   0	572	3	paramArrayOfFile	File[]
    //   0	572	4	paramFile2	File
    //   6	379	5	bool	boolean
    //   23	532	6	localObject1	Object
    //   49	491	7	localObject2	Object
    //   52	357	8	localCodedOutputStream	CodedOutputStream
    //   55	508	9	localObject3	Object
    //   58	138	10	localk	k
    //   63	466	11	localObject4	Object
    //   124	77	12	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   85	92	427	java/lang/Exception
    //   104	109	427	java/lang/Exception
    //   121	126	427	java/lang/Exception
    //   138	143	427	java/lang/Exception
    //   155	164	427	java/lang/Exception
    //   176	183	427	java/lang/Exception
    //   195	210	427	java/lang/Exception
    //   222	228	427	java/lang/Exception
    //   240	244	427	java/lang/Exception
    //   256	260	427	java/lang/Exception
    //   272	286	427	java/lang/Exception
    //   298	306	427	java/lang/Exception
    //   318	326	427	java/lang/Exception
    //   338	346	427	java/lang/Exception
    //   358	365	427	java/lang/Exception
    //   377	384	427	java/lang/Exception
    //   401	408	427	java/lang/Exception
    //   60	73	434	finally
    //   60	73	447	java/lang/Exception
    //   85	92	553	finally
    //   104	109	553	finally
    //   121	126	553	finally
    //   138	143	553	finally
    //   155	164	553	finally
    //   176	183	553	finally
    //   195	210	553	finally
    //   222	228	553	finally
    //   240	244	553	finally
    //   256	260	553	finally
    //   272	286	553	finally
    //   298	306	553	finally
    //   318	326	553	finally
    //   338	346	553	finally
    //   358	365	553	finally
    //   377	384	553	finally
    //   401	408	553	finally
    //   457	462	553	finally
    //   469	474	553	finally
    //   481	486	553	finally
    //   493	502	553	finally
    //   509	516	553	finally
    //   523	539	553	finally
  }
  
  private void trimInvalidSessionFiles()
  {
    File localFile = getInvalidFilesDir();
    if (!localFile.exists()) {
      return;
    }
    File[] arrayOfFile = listFilesMatching(localFile, new InvalidPartFileFilter());
    Arrays.sort(arrayOfFile, Collections.reverseOrder());
    HashSet localHashSet = new HashSet();
    for (int i = 0; (i < arrayOfFile.length) && (localHashSet.size() < 4); i++) {
      localHashSet.add(getSessionIdFromSessionFile(arrayOfFile[i]));
    }
    retainSessions(listFiles(localFile), localHashSet);
  }
  
  private void trimOpenSessions(int paramInt)
  {
    HashSet localHashSet = new HashSet();
    File[] arrayOfFile = listSortedSessionBeginFiles();
    int i = Math.min(paramInt, arrayOfFile.length);
    for (paramInt = 0; paramInt < i; paramInt++) {
      localHashSet.add(getSessionIdFromSessionFile(arrayOfFile[paramInt]));
    }
    this.logFileManager.discardOldLogFiles(localHashSet);
    retainSessions(listFilesMatching(new AnySessionPartFileFilter(null)), localHashSet);
  }
  
  private void trimSessionEventFiles(String paramString, int paramInt)
  {
    File localFile = getFilesDir();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("SessionEvent");
    Utils.capFileCount(localFile, new FileNameContainsFilter(localStringBuilder.toString()), paramInt, SMALLEST_FILE_NAME_FIRST);
  }
  
  private void writeBeginSession(final String paramString, Date paramDate)
  {
    final String str = String.format(Locale.US, "Crashlytics Android SDK/%s", new Object[] { this.crashlyticsCore.getVersion() });
    final long l = paramDate.getTime() / 1000L;
    writeSessionPartFile(paramString, "BeginSession", new CodedOutputStreamWriteAction()
    {
      public void writeTo(CodedOutputStream paramAnonymousCodedOutputStream)
      {
        SessionProtobufHelper.writeBeginSession(paramAnonymousCodedOutputStream, paramString, str, l);
      }
    });
    writeFile(paramString, "BeginSession.json", new FileOutputStreamWriteAction()
    {
      public void writeTo(FileOutputStream paramAnonymousFileOutputStream)
      {
        paramAnonymousFileOutputStream.write(new JSONObject(new HashMap() {}).toString().getBytes());
      }
    });
  }
  
  /* Error */
  private void writeFatal(Date paramDate, Thread paramThread, Throwable paramThrowable)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: aconst_null
    //   7: astore 6
    //   9: aconst_null
    //   10: astore 7
    //   12: aload_0
    //   13: invokespecial 382	com/crashlytics/android/core/CrashlyticsController:getCurrentSessionId	()Ljava/lang/String;
    //   16: astore 8
    //   18: aload 8
    //   20: ifnonnull +33 -> 53
    //   23: invokestatic 450	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   26: ldc_w 452
    //   29: ldc_w 1109
    //   32: aconst_null
    //   33: invokeinterface 495 4 0
    //   38: aconst_null
    //   39: ldc_w 1111
    //   42: invokestatic 625	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   45: aconst_null
    //   46: ldc_w 1113
    //   49: invokestatic 630	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   52: return
    //   53: aload 8
    //   55: aload_3
    //   56: invokevirtual 576	java/lang/Object:getClass	()Ljava/lang/Class;
    //   59: invokevirtual 581	java/lang/Class:getName	()Ljava/lang/String;
    //   62: invokestatic 656	com/crashlytics/android/core/CrashlyticsController:recordFatalExceptionAnswersEvent	(Ljava/lang/String;Ljava/lang/String;)V
    //   65: aload_0
    //   66: invokevirtual 607	com/crashlytics/android/core/CrashlyticsController:getFilesDir	()Ljava/io/File;
    //   69: astore 9
    //   71: new 466	java/lang/StringBuilder
    //   74: astore 10
    //   76: aload 10
    //   78: invokespecial 467	java/lang/StringBuilder:<init>	()V
    //   81: aload 10
    //   83: aload 8
    //   85: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: pop
    //   89: aload 10
    //   91: ldc -73
    //   93: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   96: pop
    //   97: new 486	com/crashlytics/android/core/ClsFileOutputStream
    //   100: dup
    //   101: aload 9
    //   103: aload 10
    //   105: invokevirtual 476	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   108: invokespecial 610	com/crashlytics/android/core/ClsFileOutputStream:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   111: astore 10
    //   113: aload 5
    //   115: astore 4
    //   117: aload 10
    //   119: astore 6
    //   121: aload 10
    //   123: invokestatic 614	com/crashlytics/android/core/CodedOutputStream:newInstance	(Ljava/io/OutputStream;)Lcom/crashlytics/android/core/CodedOutputStream;
    //   126: astore 5
    //   128: aload 5
    //   130: astore 7
    //   132: aload 5
    //   134: astore 4
    //   136: aload 10
    //   138: astore 6
    //   140: aload_0
    //   141: aload 5
    //   143: aload_1
    //   144: aload_2
    //   145: aload_3
    //   146: ldc 115
    //   148: iconst_1
    //   149: invokespecial 618	com/crashlytics/android/core/CrashlyticsController:writeSessionEvent	(Lcom/crashlytics/android/core/CodedOutputStream;Ljava/util/Date;Ljava/lang/Thread;Ljava/lang/Throwable;Ljava/lang/String;Z)V
    //   152: aload 5
    //   154: astore_1
    //   155: aload 10
    //   157: astore_2
    //   158: goto +53 -> 211
    //   161: astore_3
    //   162: aload 7
    //   164: astore_1
    //   165: aload 10
    //   167: astore_2
    //   168: goto +22 -> 190
    //   171: astore_1
    //   172: aconst_null
    //   173: astore_2
    //   174: aload 6
    //   176: astore 4
    //   178: aload_2
    //   179: astore 6
    //   181: goto +46 -> 227
    //   184: astore_3
    //   185: aconst_null
    //   186: astore_2
    //   187: aload 4
    //   189: astore_1
    //   190: aload_1
    //   191: astore 4
    //   193: aload_2
    //   194: astore 6
    //   196: invokestatic 450	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   199: ldc_w 452
    //   202: ldc_w 1115
    //   205: aload_3
    //   206: invokeinterface 495 4 0
    //   211: aload_1
    //   212: ldc_w 1111
    //   215: invokestatic 625	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   218: aload_2
    //   219: ldc_w 1113
    //   222: invokestatic 630	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   225: return
    //   226: astore_1
    //   227: aload 4
    //   229: ldc_w 1111
    //   232: invokestatic 625	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   235: aload 6
    //   237: ldc_w 1113
    //   240: invokestatic 630	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   243: aload_1
    //   244: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	245	0	this	CrashlyticsController
    //   0	245	1	paramDate	Date
    //   0	245	2	paramThread	Thread
    //   0	245	3	paramThrowable	Throwable
    //   1	227	4	localObject1	Object
    //   4	149	5	localCodedOutputStream1	CodedOutputStream
    //   7	229	6	localObject2	Object
    //   10	153	7	localCodedOutputStream2	CodedOutputStream
    //   16	68	8	str	String
    //   69	33	9	localFile	File
    //   74	92	10	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   121	128	161	java/lang/Exception
    //   140	152	161	java/lang/Exception
    //   12	18	171	finally
    //   23	38	171	finally
    //   53	113	171	finally
    //   12	18	184	java/lang/Exception
    //   23	38	184	java/lang/Exception
    //   53	113	184	java/lang/Exception
    //   121	128	226	finally
    //   140	152	226	finally
    //   196	211	226	finally
  }
  
  /* Error */
  private void writeFile(String paramString1, String paramString2, FileOutputStreamWriteAction paramFileOutputStreamWriteAction)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: new 812	java/io/FileOutputStream
    //   6: astore 5
    //   8: new 516	java/io/File
    //   11: astore 6
    //   13: aload_0
    //   14: invokevirtual 607	com/crashlytics/android/core/CrashlyticsController:getFilesDir	()Ljava/io/File;
    //   17: astore 7
    //   19: new 466	java/lang/StringBuilder
    //   22: astore 8
    //   24: aload 8
    //   26: invokespecial 467	java/lang/StringBuilder:<init>	()V
    //   29: aload 8
    //   31: aload_1
    //   32: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: pop
    //   36: aload 8
    //   38: aload_2
    //   39: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: pop
    //   43: aload 6
    //   45: aload 7
    //   47: aload 8
    //   49: invokevirtual 476	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   52: invokespecial 697	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   55: aload 5
    //   57: aload 6
    //   59: invokespecial 813	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   62: aload_3
    //   63: aload 5
    //   65: invokeinterface 1119 2 0
    //   70: new 466	java/lang/StringBuilder
    //   73: dup
    //   74: invokespecial 467	java/lang/StringBuilder:<init>	()V
    //   77: astore_1
    //   78: aload_1
    //   79: ldc_w 1121
    //   82: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   85: pop
    //   86: aload_1
    //   87: aload_2
    //   88: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: pop
    //   92: aload_1
    //   93: ldc_w 1123
    //   96: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: pop
    //   100: aload 5
    //   102: aload_1
    //   103: invokevirtual 476	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   106: invokestatic 630	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   109: return
    //   110: astore_1
    //   111: aload 5
    //   113: astore_3
    //   114: goto +7 -> 121
    //   117: astore_1
    //   118: aload 4
    //   120: astore_3
    //   121: new 466	java/lang/StringBuilder
    //   124: dup
    //   125: invokespecial 467	java/lang/StringBuilder:<init>	()V
    //   128: astore 5
    //   130: aload 5
    //   132: ldc_w 1121
    //   135: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: pop
    //   139: aload 5
    //   141: aload_2
    //   142: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: pop
    //   146: aload 5
    //   148: ldc_w 1123
    //   151: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   154: pop
    //   155: aload_3
    //   156: aload 5
    //   158: invokevirtual 476	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   161: invokestatic 630	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   164: aload_1
    //   165: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	166	0	this	CrashlyticsController
    //   0	166	1	paramString1	String
    //   0	166	2	paramString2	String
    //   0	166	3	paramFileOutputStreamWriteAction	FileOutputStreamWriteAction
    //   1	118	4	localObject1	Object
    //   6	151	5	localObject2	Object
    //   11	47	6	localFile1	File
    //   17	29	7	localFile2	File
    //   22	26	8	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   62	70	110	finally
    //   3	62	117	finally
  }
  
  private void writeInitialPartsTo(CodedOutputStream paramCodedOutputStream, String paramString)
  {
    for (String str : INITIAL_SESSION_PART_TAGS)
    {
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(paramString);
      ((StringBuilder)localObject1).append(str);
      ((StringBuilder)localObject1).append(".cls");
      File[] arrayOfFile = listFilesMatching(new FileNameContainsFilter(((StringBuilder)localObject1).toString()));
      Object localObject2;
      if (arrayOfFile.length == 0)
      {
        localObject1 = c.g();
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("Can't find ");
        ((StringBuilder)localObject2).append(str);
        ((StringBuilder)localObject2).append(" data for session ID ");
        ((StringBuilder)localObject2).append(paramString);
        ((k)localObject1).e("CrashlyticsCore", ((StringBuilder)localObject2).toString(), null);
      }
      else
      {
        localObject2 = c.g();
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Collecting ");
        ((StringBuilder)localObject1).append(str);
        ((StringBuilder)localObject1).append(" data for session ID ");
        ((StringBuilder)localObject1).append(paramString);
        ((k)localObject2).a("CrashlyticsCore", ((StringBuilder)localObject1).toString());
        writeToCosFromFile(paramCodedOutputStream, arrayOfFile[0]);
      }
    }
  }
  
  private static void writeNonFatalEventsTo(CodedOutputStream paramCodedOutputStream, File[] paramArrayOfFile, String paramString)
  {
    Arrays.sort(paramArrayOfFile, i.a);
    int i = paramArrayOfFile.length;
    for (int j = 0; j < i; j++)
    {
      File localFile = paramArrayOfFile[j];
      try
      {
        c.g().a("CrashlyticsCore", String.format(Locale.US, "Found Non Fatal for session ID %s in %s ", new Object[] { paramString, localFile.getName() }));
        writeToCosFromFile(paramCodedOutputStream, localFile);
      }
      catch (Exception localException)
      {
        c.g().e("CrashlyticsCore", "Error writting non-fatal to session.", localException);
      }
    }
  }
  
  private void writeSessionApp(String paramString)
  {
    final String str1 = this.idManager.c();
    final String str2 = this.appData.versionCode;
    final String str3 = this.appData.versionName;
    final String str4 = this.idManager.b();
    final int i = l.a(this.appData.installerPackageName).a();
    writeSessionPartFile(paramString, "SessionApp", new CodedOutputStreamWriteAction()
    {
      public void writeTo(CodedOutputStream paramAnonymousCodedOutputStream)
      {
        SessionProtobufHelper.writeSessionApp(paramAnonymousCodedOutputStream, str1, CrashlyticsController.this.appData.apiKey, str2, str3, str4, i, CrashlyticsController.this.unityVersion);
      }
    });
    writeFile(paramString, "SessionApp.json", new FileOutputStreamWriteAction()
    {
      public void writeTo(FileOutputStream paramAnonymousFileOutputStream)
      {
        paramAnonymousFileOutputStream.write(new JSONObject(new HashMap() {}).toString().getBytes());
      }
    });
  }
  
  private void writeSessionDevice(String paramString)
  {
    Context localContext = this.crashlyticsCore.getContext();
    Object localObject = new StatFs(Environment.getDataDirectory().getPath());
    final int i = i.a();
    final int j = Runtime.getRuntime().availableProcessors();
    final long l1 = i.b();
    long l2 = ((StatFs)localObject).getBlockCount() * ((StatFs)localObject).getBlockSize();
    final boolean bool = i.f(localContext);
    localObject = this.idManager.h();
    final int k = i.h(localContext);
    writeSessionPartFile(paramString, "SessionDevice", new CodedOutputStreamWriteAction()
    {
      public void writeTo(CodedOutputStream paramAnonymousCodedOutputStream)
      {
        SessionProtobufHelper.writeSessionDevice(paramAnonymousCodedOutputStream, i, Build.MODEL, j, l1, bool, k, this.val$ids, this.val$state, Build.MANUFACTURER, Build.PRODUCT);
      }
    });
    writeFile(paramString, "SessionDevice.json", new FileOutputStreamWriteAction()
    {
      public void writeTo(FileOutputStream paramAnonymousFileOutputStream)
      {
        paramAnonymousFileOutputStream.write(new JSONObject(new HashMap() {}).toString().getBytes());
      }
    });
  }
  
  private void writeSessionEvent(CodedOutputStream paramCodedOutputStream, Date paramDate, Thread paramThread, Throwable paramThrowable, String paramString, boolean paramBoolean)
  {
    TrimmedThrowableData localTrimmedThrowableData = new TrimmedThrowableData(paramThrowable, this.stackTraceTrimmingStrategy);
    Context localContext = this.crashlyticsCore.getContext();
    long l1 = paramDate.getTime() / 1000L;
    Float localFloat = i.c(localContext);
    int i = i.a(localContext, this.devicePowerStateListener.isPowerConnected());
    boolean bool = i.d(localContext);
    int j = localContext.getResources().getConfiguration().orientation;
    long l2 = i.b();
    long l3 = i.b(localContext);
    long l4 = i.c(Environment.getDataDirectory().getPath());
    ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = i.a(localContext.getPackageName(), localContext);
    LinkedList localLinkedList = new LinkedList();
    StackTraceElement[] arrayOfStackTraceElement = localTrimmedThrowableData.stacktrace;
    String str1 = this.appData.buildId;
    String str2 = this.idManager.c();
    int k = 0;
    if (paramBoolean)
    {
      paramDate = Thread.getAllStackTraces();
      paramThrowable = new Thread[paramDate.size()];
      paramDate = paramDate.entrySet().iterator();
      while (paramDate.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramDate.next();
        paramThrowable[k] = ((Thread)localEntry.getKey());
        localLinkedList.add(this.stackTraceTrimmingStrategy.getTrimmedStackTrace((StackTraceElement[])localEntry.getValue()));
        k++;
      }
    }
    else
    {
      paramThrowable = new Thread[0];
    }
    if (!i.a(localContext, "com.crashlytics.CollectCustomKeys", true))
    {
      paramDate = new TreeMap();
    }
    else
    {
      paramDate = this.crashlyticsCore.getAttributes();
      if ((paramDate != null) && (paramDate.size() > 1)) {
        paramDate = new TreeMap(paramDate);
      }
    }
    SessionProtobufHelper.writeSessionEvent(paramCodedOutputStream, l1, paramString, localTrimmedThrowableData, paramThread, arrayOfStackTraceElement, paramThrowable, localLinkedList, paramDate, this.logFileManager, localRunningAppProcessInfo, j, str2, str1, localFloat, i, bool, l2 - l3, l4);
  }
  
  private void writeSessionOS(String paramString)
  {
    final boolean bool = i.g(this.crashlyticsCore.getContext());
    writeSessionPartFile(paramString, "SessionOS", new CodedOutputStreamWriteAction()
    {
      public void writeTo(CodedOutputStream paramAnonymousCodedOutputStream)
      {
        SessionProtobufHelper.writeSessionOS(paramAnonymousCodedOutputStream, Build.VERSION.RELEASE, Build.VERSION.CODENAME, bool);
      }
    });
    writeFile(paramString, "SessionOS.json", new FileOutputStreamWriteAction()
    {
      public void writeTo(FileOutputStream paramAnonymousFileOutputStream)
      {
        paramAnonymousFileOutputStream.write(new JSONObject(new HashMap() {}).toString().getBytes());
      }
    });
  }
  
  /* Error */
  private void writeSessionPartFile(String paramString1, String paramString2, CodedOutputStreamWriteAction paramCodedOutputStreamWriteAction)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: new 486	com/crashlytics/android/core/ClsFileOutputStream
    //   9: astore 6
    //   11: aload_0
    //   12: invokevirtual 607	com/crashlytics/android/core/CrashlyticsController:getFilesDir	()Ljava/io/File;
    //   15: astore 7
    //   17: new 466	java/lang/StringBuilder
    //   20: astore 8
    //   22: aload 8
    //   24: invokespecial 467	java/lang/StringBuilder:<init>	()V
    //   27: aload 8
    //   29: aload_1
    //   30: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: pop
    //   34: aload 8
    //   36: aload_2
    //   37: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: pop
    //   41: aload 6
    //   43: aload 7
    //   45: aload 8
    //   47: invokevirtual 476	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   50: invokespecial 610	com/crashlytics/android/core/ClsFileOutputStream:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   53: aload 5
    //   55: astore_1
    //   56: aload 6
    //   58: invokestatic 614	com/crashlytics/android/core/CodedOutputStream:newInstance	(Ljava/io/OutputStream;)Lcom/crashlytics/android/core/CodedOutputStream;
    //   61: astore 4
    //   63: aload 4
    //   65: astore_1
    //   66: aload_3
    //   67: aload 4
    //   69: invokeinterface 1314 2 0
    //   74: new 466	java/lang/StringBuilder
    //   77: dup
    //   78: invokespecial 467	java/lang/StringBuilder:<init>	()V
    //   81: astore_1
    //   82: aload_1
    //   83: ldc_w 1316
    //   86: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   89: pop
    //   90: aload_1
    //   91: aload_2
    //   92: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: pop
    //   96: aload_1
    //   97: ldc_w 1123
    //   100: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   103: pop
    //   104: aload 4
    //   106: aload_1
    //   107: invokevirtual 476	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   110: invokestatic 625	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   113: new 466	java/lang/StringBuilder
    //   116: dup
    //   117: invokespecial 467	java/lang/StringBuilder:<init>	()V
    //   120: astore_1
    //   121: aload_1
    //   122: ldc_w 1318
    //   125: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: pop
    //   129: aload_1
    //   130: aload_2
    //   131: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: pop
    //   135: aload_1
    //   136: ldc_w 1123
    //   139: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   142: pop
    //   143: aload 6
    //   145: aload_1
    //   146: invokevirtual 476	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   149: invokestatic 630	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   152: return
    //   153: astore 5
    //   155: aload_1
    //   156: astore 4
    //   158: aload 6
    //   160: astore_3
    //   161: aload 5
    //   163: astore_1
    //   164: goto +6 -> 170
    //   167: astore_1
    //   168: aconst_null
    //   169: astore_3
    //   170: new 466	java/lang/StringBuilder
    //   173: dup
    //   174: invokespecial 467	java/lang/StringBuilder:<init>	()V
    //   177: astore 5
    //   179: aload 5
    //   181: ldc_w 1316
    //   184: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   187: pop
    //   188: aload 5
    //   190: aload_2
    //   191: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   194: pop
    //   195: aload 5
    //   197: ldc_w 1123
    //   200: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   203: pop
    //   204: aload 4
    //   206: aload 5
    //   208: invokevirtual 476	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   211: invokestatic 625	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Flushable;Ljava/lang/String;)V
    //   214: new 466	java/lang/StringBuilder
    //   217: dup
    //   218: invokespecial 467	java/lang/StringBuilder:<init>	()V
    //   221: astore 4
    //   223: aload 4
    //   225: ldc_w 1318
    //   228: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   231: pop
    //   232: aload 4
    //   234: aload_2
    //   235: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   238: pop
    //   239: aload 4
    //   241: ldc_w 1123
    //   244: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   247: pop
    //   248: aload_3
    //   249: aload 4
    //   251: invokevirtual 476	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   254: invokestatic 630	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   257: aload_1
    //   258: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	259	0	this	CrashlyticsController
    //   0	259	1	paramString1	String
    //   0	259	2	paramString2	String
    //   0	259	3	paramCodedOutputStreamWriteAction	CodedOutputStreamWriteAction
    //   1	249	4	localObject1	Object
    //   4	50	5	localObject2	Object
    //   153	9	5	localObject3	Object
    //   177	30	5	localStringBuilder1	StringBuilder
    //   9	150	6	localClsFileOutputStream	ClsFileOutputStream
    //   15	29	7	localFile	File
    //   20	26	8	localStringBuilder2	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   56	63	153	finally
    //   66	74	153	finally
    //   6	53	167	finally
  }
  
  private void writeSessionPartsToSessionFile(File paramFile, String paramString, int paramInt)
  {
    Object localObject1 = c.g();
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Collecting session parts for ID ");
    ((StringBuilder)localObject2).append(paramString);
    ((k)localObject1).a("CrashlyticsCore", ((StringBuilder)localObject2).toString());
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(paramString);
    ((StringBuilder)localObject2).append("SessionCrash");
    localObject2 = listFilesMatching(new FileNameContainsFilter(((StringBuilder)localObject2).toString()));
    boolean bool1;
    if ((localObject2 != null) && (localObject2.length > 0)) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    c.g().a("CrashlyticsCore", String.format(Locale.US, "Session %s has fatal exception: %s", new Object[] { paramString, Boolean.valueOf(bool1) }));
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(paramString);
    ((StringBuilder)localObject1).append("SessionEvent");
    localObject1 = listFilesMatching(new FileNameContainsFilter(((StringBuilder)localObject1).toString()));
    boolean bool2;
    if ((localObject1 != null) && (localObject1.length > 0)) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    c.g().a("CrashlyticsCore", String.format(Locale.US, "Session %s has non-fatal exceptions: %s", new Object[] { paramString, Boolean.valueOf(bool2) }));
    if ((!bool1) && (!bool2))
    {
      localObject2 = c.g();
      paramFile = new StringBuilder();
      paramFile.append("No events present for session ID ");
      paramFile.append(paramString);
      ((k)localObject2).a("CrashlyticsCore", paramFile.toString());
    }
    else
    {
      localObject1 = getTrimmedNonFatalFiles(paramString, (File[])localObject1, paramInt);
      if (bool1) {
        localObject2 = localObject2[0];
      } else {
        localObject2 = null;
      }
      synthesizeSessionFile(paramFile, paramString, (File[])localObject1, (File)localObject2);
    }
    localObject2 = c.g();
    paramFile = new StringBuilder();
    paramFile.append("Removing session part files for ID ");
    paramFile.append(paramString);
    ((k)localObject2).a("CrashlyticsCore", paramFile.toString());
    deleteSessionPartFilesFor(paramString);
  }
  
  private void writeSessionUser(String paramString)
  {
    writeSessionPartFile(paramString, "SessionUser", new CodedOutputStreamWriteAction()
    {
      public void writeTo(CodedOutputStream paramAnonymousCodedOutputStream)
      {
        SessionProtobufHelper.writeSessionUser(paramAnonymousCodedOutputStream, this.val$userMetaData.id, this.val$userMetaData.name, this.val$userMetaData.email);
      }
    });
  }
  
  /* Error */
  private static void writeToCosFromFile(CodedOutputStream paramCodedOutputStream, File paramFile)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 1006	java/io/File:exists	()Z
    //   4: ifne +47 -> 51
    //   7: invokestatic 450	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   10: astore_2
    //   11: new 466	java/lang/StringBuilder
    //   14: dup
    //   15: invokespecial 467	java/lang/StringBuilder:<init>	()V
    //   18: astore_0
    //   19: aload_0
    //   20: ldc_w 1346
    //   23: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: pop
    //   27: aload_0
    //   28: aload_1
    //   29: invokevirtual 760	java/io/File:getName	()Ljava/lang/String;
    //   32: invokevirtual 473	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: pop
    //   36: aload_2
    //   37: ldc_w 452
    //   40: aload_0
    //   41: invokevirtual 476	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   44: aconst_null
    //   45: invokeinterface 495 4 0
    //   50: return
    //   51: new 1348	java/io/FileInputStream
    //   54: astore_2
    //   55: aload_2
    //   56: aload_1
    //   57: invokespecial 1349	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   60: aload_2
    //   61: aload_0
    //   62: aload_1
    //   63: invokevirtual 1352	java/io/File:length	()J
    //   66: l2i
    //   67: invokestatic 1354	com/crashlytics/android/core/CrashlyticsController:copyToCodedOutputStream	(Ljava/io/InputStream;Lcom/crashlytics/android/core/CodedOutputStream;I)V
    //   70: aload_2
    //   71: ldc_w 1356
    //   74: invokestatic 630	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   77: return
    //   78: astore_0
    //   79: aload_2
    //   80: astore_1
    //   81: goto +6 -> 87
    //   84: astore_0
    //   85: aconst_null
    //   86: astore_1
    //   87: aload_1
    //   88: ldc_w 1356
    //   91: invokestatic 630	io/fabric/sdk/android/services/b/i:a	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   94: aload_0
    //   95: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	96	0	paramCodedOutputStream	CodedOutputStream
    //   0	96	1	paramFile	File
    //   10	70	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   60	70	78	finally
    //   51	60	84	finally
  }
  
  void cacheKeyData(final Map<String, String> paramMap)
  {
    this.backgroundWorker.submit(new Callable()
    {
      public Void call()
      {
        String str = CrashlyticsController.this.getCurrentSessionId();
        new MetaDataStore(CrashlyticsController.this.getFilesDir()).writeKeyData(str, paramMap);
        return null;
      }
    });
  }
  
  void cacheUserData(final String paramString1, final String paramString2, final String paramString3)
  {
    this.backgroundWorker.submit(new Callable()
    {
      public Void call()
      {
        String str = CrashlyticsController.this.getCurrentSessionId();
        new MetaDataStore(CrashlyticsController.this.getFilesDir()).writeUserData(str, new UserMetaData(paramString1, paramString2, paramString3));
        return null;
      }
    });
  }
  
  void cleanInvalidTempFiles()
  {
    this.backgroundWorker.submit(new Runnable()
    {
      public void run()
      {
        CrashlyticsController localCrashlyticsController = CrashlyticsController.this;
        localCrashlyticsController.doCleanInvalidTempFiles(localCrashlyticsController.listFilesMatching(new CrashlyticsController.InvalidPartFileFilter()));
      }
    });
  }
  
  void doCleanInvalidTempFiles(File[] paramArrayOfFile)
  {
    final HashSet localHashSet = new HashSet();
    int i = paramArrayOfFile.length;
    int j = 0;
    Object localObject1;
    k localk;
    for (int k = 0; k < i; k++)
    {
      localObject1 = paramArrayOfFile[k];
      localk = c.g();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Found invalid session part file: ");
      ((StringBuilder)localObject2).append(localObject1);
      localk.a("CrashlyticsCore", ((StringBuilder)localObject2).toString());
      localHashSet.add(getSessionIdFromSessionFile((File)localObject1));
    }
    if (localHashSet.isEmpty()) {
      return;
    }
    paramArrayOfFile = getInvalidFilesDir();
    if (!paramArrayOfFile.exists()) {
      paramArrayOfFile.mkdir();
    }
    Object localObject2 = listFilesMatching(new FilenameFilter()
    {
      public boolean accept(File paramAnonymousFile, String paramAnonymousString)
      {
        if (paramAnonymousString.length() < 35) {
          return false;
        }
        return localHashSet.contains(paramAnonymousString.substring(0, 35));
      }
    });
    i = localObject2.length;
    for (k = j; k < i; k++)
    {
      localHashSet = localObject2[k];
      localk = c.g();
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Moving session file: ");
      ((StringBuilder)localObject1).append(localHashSet);
      localk.a("CrashlyticsCore", ((StringBuilder)localObject1).toString());
      if (!localHashSet.renameTo(new File(paramArrayOfFile, localHashSet.getName())))
      {
        localk = c.g();
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Could not move session file. Deleting ");
        ((StringBuilder)localObject1).append(localHashSet);
        localk.a("CrashlyticsCore", ((StringBuilder)localObject1).toString());
        localHashSet.delete();
      }
    }
    trimInvalidSessionFiles();
  }
  
  void doCloseSessions(p paramp)
  {
    doCloseSessions(paramp, false);
  }
  
  void enableExceptionHandling(Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler, boolean paramBoolean)
  {
    openSession();
    this.crashHandler = new CrashlyticsUncaughtExceptionHandler(new CrashlyticsUncaughtExceptionHandler.CrashListener()new DefaultSettingsDataProvider
    {
      public void onUncaughtException(CrashlyticsUncaughtExceptionHandler.SettingsDataProvider paramAnonymousSettingsDataProvider, Thread paramAnonymousThread, Throwable paramAnonymousThrowable, boolean paramAnonymousBoolean)
      {
        CrashlyticsController.this.handleUncaughtException(paramAnonymousSettingsDataProvider, paramAnonymousThread, paramAnonymousThrowable, paramAnonymousBoolean);
      }
    }, new DefaultSettingsDataProvider(null), paramBoolean, paramUncaughtExceptionHandler);
    Thread.setDefaultUncaughtExceptionHandler(this.crashHandler);
  }
  
  boolean finalizeNativeReport(final CrashlyticsNdkData paramCrashlyticsNdkData)
  {
    if (paramCrashlyticsNdkData == null) {
      return true;
    }
    ((Boolean)this.backgroundWorker.submitAndWait(new Callable()
    {
      public Boolean call()
      {
        TreeSet localTreeSet = paramCrashlyticsNdkData.timestampedDirectories;
        String str = CrashlyticsController.this.getPreviousSessionId();
        if ((str != null) && (!localTreeSet.isEmpty()))
        {
          File localFile = (File)localTreeSet.first();
          if (localFile != null)
          {
            CrashlyticsController localCrashlyticsController = CrashlyticsController.this;
            localCrashlyticsController.finalizeMostRecentNativeCrash(localCrashlyticsController.crashlyticsCore.getContext(), localFile, str);
          }
        }
        CrashlyticsController.this.recursiveDelete(localTreeSet);
        return Boolean.TRUE;
      }
    })).booleanValue();
  }
  
  boolean finalizeSessions(final p paramp)
  {
    ((Boolean)this.backgroundWorker.submitAndWait(new Callable()
    {
      public Boolean call()
      {
        if (CrashlyticsController.this.isHandlingException())
        {
          c.g().a("CrashlyticsCore", "Skipping session finalization because a crash has already occurred.");
          return Boolean.FALSE;
        }
        c.g().a("CrashlyticsCore", "Finalizing previously open sessions.");
        CrashlyticsController.this.doCloseSessions(paramp, true);
        c.g().a("CrashlyticsCore", "Closed all previously open sessions");
        return Boolean.TRUE;
      }
    })).booleanValue();
  }
  
  File getFatalSessionFilesDir()
  {
    return new File(getFilesDir(), "fatal-sessions");
  }
  
  File getFilesDir()
  {
    return this.fileStore.a();
  }
  
  File getInvalidFilesDir()
  {
    return new File(getFilesDir(), "invalidClsFiles");
  }
  
  File getNonFatalSessionFilesDir()
  {
    return new File(getFilesDir(), "nonfatal-sessions");
  }
  
  void handleUncaughtException(CrashlyticsUncaughtExceptionHandler.SettingsDataProvider paramSettingsDataProvider, Thread paramThread, Throwable paramThrowable, boolean paramBoolean)
  {
    try
    {
      Object localObject1 = c.g();
      Object localObject2 = new java/lang/StringBuilder;
      ((StringBuilder)localObject2).<init>();
      ((StringBuilder)localObject2).append("Crashlytics is handling uncaught exception \"");
      ((StringBuilder)localObject2).append(paramThrowable);
      ((StringBuilder)localObject2).append("\" from thread ");
      ((StringBuilder)localObject2).append(paramThread.getName());
      ((k)localObject1).a("CrashlyticsCore", ((StringBuilder)localObject2).toString());
      this.devicePowerStateListener.dispose();
      localObject2 = new java/util/Date;
      ((Date)localObject2).<init>();
      CrashlyticsBackgroundWorker localCrashlyticsBackgroundWorker = this.backgroundWorker;
      localObject1 = new com/crashlytics/android/core/CrashlyticsController$7;
      ((7)localObject1).<init>(this, (Date)localObject2, paramThread, paramThrowable, paramSettingsDataProvider, paramBoolean);
      localCrashlyticsBackgroundWorker.submitAndWait((Callable)localObject1);
      return;
    }
    finally
    {
      paramSettingsDataProvider = finally;
      throw paramSettingsDataProvider;
    }
  }
  
  boolean hasOpenSession()
  {
    boolean bool;
    if (listSessionBeginFiles().length > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean isHandlingException()
  {
    CrashlyticsUncaughtExceptionHandler localCrashlyticsUncaughtExceptionHandler = this.crashHandler;
    boolean bool;
    if ((localCrashlyticsUncaughtExceptionHandler != null) && (localCrashlyticsUncaughtExceptionHandler.isHandlingException())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  File[] listCompleteSessionFiles()
  {
    LinkedList localLinkedList = new LinkedList();
    Collections.addAll(localLinkedList, listFilesMatching(getFatalSessionFilesDir(), SESSION_FILE_FILTER));
    Collections.addAll(localLinkedList, listFilesMatching(getNonFatalSessionFilesDir(), SESSION_FILE_FILTER));
    Collections.addAll(localLinkedList, listFilesMatching(getFilesDir(), SESSION_FILE_FILTER));
    return (File[])localLinkedList.toArray(new File[localLinkedList.size()]);
  }
  
  File[] listNativeSessionFileDirectories()
  {
    return listFilesMatching(SESSION_DIRECTORY_FILTER);
  }
  
  File[] listSessionBeginFiles()
  {
    return listFilesMatching(SESSION_BEGIN_FILE_FILTER);
  }
  
  void openSession()
  {
    this.backgroundWorker.submit(new Callable()
    {
      public Void call()
      {
        CrashlyticsController.this.doOpenSession();
        return null;
      }
    });
  }
  
  void registerAnalyticsEventListener(t paramt)
  {
    if ((paramt.d.e) && (this.appMeasurementEventListenerRegistrar.register())) {
      c.g().a("CrashlyticsCore", "Registered Firebase Analytics event listener");
    }
  }
  
  void registerDevicePowerStateListener()
  {
    this.devicePowerStateListener.initialize();
  }
  
  void submitAllReports(float paramFloat, t paramt)
  {
    if (paramt == null)
    {
      c.g().d("CrashlyticsCore", "Could not send reports. Settings are not available.");
      return;
    }
    CreateReportSpiCall localCreateReportSpiCall = getCreateReportSpiCall(paramt.a.d, paramt.a.e);
    if (shouldPromptUserBeforeSendingCrashReports(paramt)) {
      paramt = new PrivacyDialogCheck(this.crashlyticsCore, this.preferenceManager, paramt.c);
    } else {
      paramt = new ReportUploader.AlwaysSendCheck();
    }
    new ReportUploader(this.appData.apiKey, localCreateReportSpiCall, this.reportFilesProvider, this.handlingExceptionCheck).uploadReports(paramFloat, paramt);
  }
  
  void trimSessionFiles(int paramInt)
  {
    int i = paramInt - Utils.capFileCount(getFatalSessionFilesDir(), paramInt, SMALLEST_FILE_NAME_FIRST);
    paramInt = Utils.capFileCount(getNonFatalSessionFilesDir(), i, SMALLEST_FILE_NAME_FIRST);
    Utils.capFileCount(getFilesDir(), SESSION_FILE_FILTER, i - paramInt, SMALLEST_FILE_NAME_FIRST);
  }
  
  void writeNonFatalException(final Thread paramThread, final Throwable paramThrowable)
  {
    final Date localDate = new Date();
    this.backgroundWorker.submit(new Runnable()
    {
      public void run()
      {
        if (!CrashlyticsController.this.isHandlingException()) {
          CrashlyticsController.this.doWriteNonFatal(localDate, paramThread, paramThrowable);
        }
      }
    });
  }
  
  void writeToLog(final long paramLong, String paramString)
  {
    this.backgroundWorker.submit(new Callable()
    {
      public Void call()
      {
        if (!CrashlyticsController.this.isHandlingException()) {
          CrashlyticsController.this.logFileManager.writeToLog(paramLong, this.val$msg);
        }
        return null;
      }
    });
  }
  
  private static class AnySessionPartFileFilter
    implements FilenameFilter
  {
    public boolean accept(File paramFile, String paramString)
    {
      boolean bool;
      if ((!CrashlyticsController.SESSION_FILE_FILTER.accept(paramFile, paramString)) && (CrashlyticsController.SESSION_FILE_PATTERN.matcher(paramString).matches())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  private static abstract interface CodedOutputStreamWriteAction
  {
    public abstract void writeTo(CodedOutputStream paramCodedOutputStream);
  }
  
  private static final class DefaultSettingsDataProvider
    implements CrashlyticsUncaughtExceptionHandler.SettingsDataProvider
  {
    public t getSettingsData()
    {
      return io.fabric.sdk.android.services.e.q.a().b();
    }
  }
  
  static class FileNameContainsFilter
    implements FilenameFilter
  {
    private final String string;
    
    public FileNameContainsFilter(String paramString)
    {
      this.string = paramString;
    }
    
    public boolean accept(File paramFile, String paramString)
    {
      boolean bool;
      if ((paramString.contains(this.string)) && (!paramString.endsWith(".cls_temp"))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  private static abstract interface FileOutputStreamWriteAction
  {
    public abstract void writeTo(FileOutputStream paramFileOutputStream);
  }
  
  static class InvalidPartFileFilter
    implements FilenameFilter
  {
    public boolean accept(File paramFile, String paramString)
    {
      boolean bool;
      if ((!ClsFileOutputStream.TEMP_FILENAME_FILTER.accept(paramFile, paramString)) && (!paramString.contains("SessionMissingBinaryImages"))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
  }
  
  private static final class LogFileDirectoryProvider
    implements LogFileManager.DirectoryProvider
  {
    private static final String LOG_FILES_DIR = "log-files";
    private final a rootFileStore;
    
    public LogFileDirectoryProvider(a parama)
    {
      this.rootFileStore = parama;
    }
    
    public File getLogFileDir()
    {
      File localFile = new File(this.rootFileStore.a(), "log-files");
      if (!localFile.exists()) {
        localFile.mkdirs();
      }
      return localFile;
    }
  }
  
  private static final class PrivacyDialogCheck
    implements ReportUploader.SendCheck
  {
    private final h kit;
    private final PreferenceManager preferenceManager;
    private final o promptData;
    
    public PrivacyDialogCheck(h paramh, PreferenceManager paramPreferenceManager, o paramo)
    {
      this.kit = paramh;
      this.preferenceManager = paramPreferenceManager;
      this.promptData = paramo;
    }
    
    public boolean canSendReports()
    {
      Activity localActivity = this.kit.getFabric().b();
      if ((localActivity != null) && (!localActivity.isFinishing()))
      {
        final Object localObject = new CrashPromptDialog.AlwaysSendCallback()
        {
          public void sendUserReportsWithoutPrompting(boolean paramAnonymousBoolean)
          {
            CrashlyticsController.PrivacyDialogCheck.this.preferenceManager.setShouldAlwaysSendReports(paramAnonymousBoolean);
          }
        };
        localObject = CrashPromptDialog.create(localActivity, this.promptData, (CrashPromptDialog.AlwaysSendCallback)localObject);
        localActivity.runOnUiThread(new Runnable()
        {
          public void run()
          {
            localObject.show();
          }
        });
        c.g().a("CrashlyticsCore", "Waiting for user opt-in.");
        ((CrashPromptDialog)localObject).await();
        return ((CrashPromptDialog)localObject).getOptIn();
      }
      return true;
    }
  }
  
  private final class ReportUploaderFilesProvider
    implements ReportUploader.ReportFilesProvider
  {
    private ReportUploaderFilesProvider() {}
    
    public File[] getCompleteSessionFiles()
    {
      return CrashlyticsController.this.listCompleteSessionFiles();
    }
    
    public File[] getInvalidSessionFiles()
    {
      return CrashlyticsController.this.getInvalidFilesDir().listFiles();
    }
    
    public File[] getNativeReportFiles()
    {
      return CrashlyticsController.this.listNativeSessionFileDirectories();
    }
  }
  
  private final class ReportUploaderHandlingExceptionCheck
    implements ReportUploader.HandlingExceptionCheck
  {
    private ReportUploaderHandlingExceptionCheck() {}
    
    public boolean isHandlingException()
    {
      return CrashlyticsController.this.isHandlingException();
    }
  }
  
  private static final class SendReportRunnable
    implements Runnable
  {
    private final Context context;
    private final Report report;
    private final ReportUploader reportUploader;
    
    public SendReportRunnable(Context paramContext, Report paramReport, ReportUploader paramReportUploader)
    {
      this.context = paramContext;
      this.report = paramReport;
      this.reportUploader = paramReportUploader;
    }
    
    public void run()
    {
      if (!i.n(this.context)) {
        return;
      }
      c.g().a("CrashlyticsCore", "Attempting to send crash report at time of crash...");
      this.reportUploader.forceUpload(this.report);
    }
  }
  
  static class SessionPartFileFilter
    implements FilenameFilter
  {
    private final String sessionId;
    
    public SessionPartFileFilter(String paramString)
    {
      this.sessionId = paramString;
    }
    
    public boolean accept(File paramFile, String paramString)
    {
      paramFile = new StringBuilder();
      paramFile.append(this.sessionId);
      paramFile.append(".cls");
      boolean bool1 = paramString.equals(paramFile.toString());
      boolean bool2 = false;
      if (bool1) {
        return false;
      }
      bool1 = bool2;
      if (paramString.contains(this.sessionId))
      {
        bool1 = bool2;
        if (!paramString.endsWith(".cls_temp")) {
          bool1 = true;
        }
      }
      return bool1;
    }
  }
}


/* Location:              ~/com/crashlytics/android/core/CrashlyticsController.class
 *
 * Reversed by:           J
 */