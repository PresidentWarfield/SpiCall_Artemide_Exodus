package com.crashlytics.android.core;

import android.content.Context;
import android.util.Log;
import com.crashlytics.android.answers.AppMeasurementEventLogger;
import com.crashlytics.android.answers.EventLogger;
import io.fabric.sdk.android.h;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.i;
import io.fabric.sdk.android.services.b.n;
import io.fabric.sdk.android.services.b.q;
import io.fabric.sdk.android.services.b.r;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import io.fabric.sdk.android.services.concurrency.e;
import io.fabric.sdk.android.services.concurrency.l;
import io.fabric.sdk.android.services.d.a;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.f;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.net.ssl.HttpsURLConnection;

@io.fabric.sdk.android.services.concurrency.d(a={CrashlyticsNdkDataProvider.class})
public class CrashlyticsCore
  extends h<Void>
{
  static final float CLS_DEFAULT_PROCESS_DELAY = 1.0F;
  static final String CRASHLYTICS_REQUIRE_BUILD_ID = "com.crashlytics.RequireBuildId";
  static final boolean CRASHLYTICS_REQUIRE_BUILD_ID_DEFAULT = true;
  static final String CRASH_MARKER_FILE_NAME = "crash_marker";
  static final int DEFAULT_MAIN_HANDLER_TIMEOUT_SEC = 4;
  private static final String INITIALIZATION_MARKER_FILE_NAME = "initialization_marker";
  static final int MAX_ATTRIBUTES = 64;
  static final int MAX_ATTRIBUTE_SIZE = 1024;
  private static final String MISSING_BUILD_ID_MSG = "This app relies on Crashlytics. Please sign up for access at https://fabric.io/sign_up,\ninstall an Android build tool and ask a team member to invite you to this app's organization.";
  private static final String PREFERENCE_STORE_NAME = "com.crashlytics.android.core.CrashlyticsCore";
  public static final String TAG = "CrashlyticsCore";
  private final ConcurrentHashMap<String, String> attributes;
  private CrashlyticsBackgroundWorker backgroundWorker;
  private CrashlyticsController controller;
  private CrashlyticsFileMarker crashMarker;
  private CrashlyticsNdkDataProvider crashlyticsNdkDataProvider;
  private float delay;
  private boolean disabled;
  private io.fabric.sdk.android.services.network.d httpRequestFactory;
  private CrashlyticsFileMarker initializationMarker;
  private CrashlyticsListener listener;
  private final PinningInfoProvider pinningInfo;
  private final long startTime;
  private String userEmail = null;
  private String userId = null;
  private String userName = null;
  
  public CrashlyticsCore()
  {
    this(1.0F, null, null, false);
  }
  
  CrashlyticsCore(float paramFloat, CrashlyticsListener paramCrashlyticsListener, PinningInfoProvider paramPinningInfoProvider, boolean paramBoolean)
  {
    this(paramFloat, paramCrashlyticsListener, paramPinningInfoProvider, paramBoolean, n.a("Crashlytics Exception Handler"));
  }
  
  CrashlyticsCore(float paramFloat, CrashlyticsListener paramCrashlyticsListener, PinningInfoProvider paramPinningInfoProvider, boolean paramBoolean, ExecutorService paramExecutorService)
  {
    this.delay = paramFloat;
    if (paramCrashlyticsListener == null) {
      paramCrashlyticsListener = new NoOpListener(null);
    }
    this.listener = paramCrashlyticsListener;
    this.pinningInfo = paramPinningInfoProvider;
    this.disabled = paramBoolean;
    this.backgroundWorker = new CrashlyticsBackgroundWorker(paramExecutorService);
    this.attributes = new ConcurrentHashMap();
    this.startTime = System.currentTimeMillis();
  }
  
  private void checkForPreviousCrash()
  {
    Boolean localBoolean = (Boolean)this.backgroundWorker.submitAndWait(new CrashMarkerCheck(this.crashMarker));
    if (!Boolean.TRUE.equals(localBoolean)) {
      return;
    }
    try
    {
      this.listener.crashlyticsDidDetectCrashDuringPreviousExecution();
    }
    catch (Exception localException)
    {
      io.fabric.sdk.android.c.g().e("CrashlyticsCore", "Exception thrown by CrashlyticsListener while notifying of previous crash.", localException);
    }
  }
  
  private void doLog(int paramInt, String paramString1, String paramString2)
  {
    if (this.disabled) {
      return;
    }
    if (!ensureFabricWithCalled("prior to logging messages.")) {
      return;
    }
    long l1 = System.currentTimeMillis();
    long l2 = this.startTime;
    this.controller.writeToLog(l1 - l2, formatLogMessage(paramInt, paramString1, paramString2));
  }
  
  private static boolean ensureFabricWithCalled(String paramString)
  {
    Object localObject = getInstance();
    if ((localObject != null) && (((CrashlyticsCore)localObject).controller != null)) {
      return true;
    }
    localObject = io.fabric.sdk.android.c.g();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Crashlytics must be initialized by calling Fabric.with(Context) ");
    localStringBuilder.append(paramString);
    ((k)localObject).e("CrashlyticsCore", localStringBuilder.toString(), null);
    return false;
  }
  
  private void finishInitSynchronously()
  {
    Object localObject = new io.fabric.sdk.android.services.concurrency.g()
    {
      public Void call()
      {
        return CrashlyticsCore.this.doInBackground();
      }
      
      public e getPriority()
      {
        return e.d;
      }
    };
    Iterator localIterator = getDependencies().iterator();
    while (localIterator.hasNext()) {
      ((io.fabric.sdk.android.services.concurrency.g)localObject).addDependency((l)localIterator.next());
    }
    localObject = getFabric().e().submit((Callable)localObject);
    io.fabric.sdk.android.c.g().a("CrashlyticsCore", "Crashlytics detected incomplete initialization on previous app launch. Will initialize synchronously.");
    try
    {
      ((Future)localObject).get(4L, TimeUnit.SECONDS);
    }
    catch (TimeoutException localTimeoutException)
    {
      io.fabric.sdk.android.c.g().e("CrashlyticsCore", "Crashlytics timed out during initialization.", localTimeoutException);
    }
    catch (ExecutionException localExecutionException)
    {
      io.fabric.sdk.android.c.g().e("CrashlyticsCore", "Problem encountered during Crashlytics initialization.", localExecutionException);
    }
    catch (InterruptedException localInterruptedException)
    {
      io.fabric.sdk.android.c.g().e("CrashlyticsCore", "Crashlytics was interrupted during initialization.", localInterruptedException);
    }
  }
  
  private static String formatLogMessage(int paramInt, String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(i.b(paramInt));
    localStringBuilder.append("/");
    localStringBuilder.append(paramString1);
    localStringBuilder.append(" ");
    localStringBuilder.append(paramString2);
    return localStringBuilder.toString();
  }
  
  public static CrashlyticsCore getInstance()
  {
    return (CrashlyticsCore)io.fabric.sdk.android.c.a(CrashlyticsCore.class);
  }
  
  static boolean isBuildIdValid(String paramString, boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      io.fabric.sdk.android.c.g().a("CrashlyticsCore", "Configured not to require a build ID.");
      return true;
    }
    if (!i.d(paramString)) {
      return true;
    }
    Log.e("CrashlyticsCore", ".");
    Log.e("CrashlyticsCore", ".     |  | ");
    Log.e("CrashlyticsCore", ".     |  |");
    Log.e("CrashlyticsCore", ".     |  |");
    Log.e("CrashlyticsCore", ".   \\ |  | /");
    Log.e("CrashlyticsCore", ".    \\    /");
    Log.e("CrashlyticsCore", ".     \\  /");
    Log.e("CrashlyticsCore", ".      \\/");
    Log.e("CrashlyticsCore", ".");
    Log.e("CrashlyticsCore", "This app relies on Crashlytics. Please sign up for access at https://fabric.io/sign_up,\ninstall an Android build tool and ask a team member to invite you to this app's organization.");
    Log.e("CrashlyticsCore", ".");
    Log.e("CrashlyticsCore", ".      /\\");
    Log.e("CrashlyticsCore", ".     /  \\");
    Log.e("CrashlyticsCore", ".    /    \\");
    Log.e("CrashlyticsCore", ".   / |  | \\");
    Log.e("CrashlyticsCore", ".     |  |");
    Log.e("CrashlyticsCore", ".     |  |");
    Log.e("CrashlyticsCore", ".     |  |");
    Log.e("CrashlyticsCore", ".");
    return false;
  }
  
  private static String sanitizeAttribute(String paramString)
  {
    String str = paramString;
    if (paramString != null)
    {
      paramString = paramString.trim();
      str = paramString;
      if (paramString.length() > 1024) {
        str = paramString.substring(0, 1024);
      }
    }
    return str;
  }
  
  public void crash()
  {
    new CrashTest().indexOutOfBounds();
  }
  
  void createCrashMarker()
  {
    this.crashMarker.create();
  }
  
  boolean didPreviousInitializationFail()
  {
    return this.initializationMarker.isPresent();
  }
  
  /* Error */
  protected Void doInBackground()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 378	com/crashlytics/android/core/CrashlyticsCore:markInitializationStarted	()V
    //   4: aload_0
    //   5: getfield 189	com/crashlytics/android/core/CrashlyticsCore:controller	Lcom/crashlytics/android/core/CrashlyticsController;
    //   8: invokevirtual 381	com/crashlytics/android/core/CrashlyticsController:cleanInvalidTempFiles	()V
    //   11: aload_0
    //   12: getfield 189	com/crashlytics/android/core/CrashlyticsCore:controller	Lcom/crashlytics/android/core/CrashlyticsController;
    //   15: invokevirtual 384	com/crashlytics/android/core/CrashlyticsController:registerDevicePowerStateListener	()V
    //   18: invokestatic 389	io/fabric/sdk/android/services/e/q:a	()Lio/fabric/sdk/android/services/e/q;
    //   21: invokevirtual 392	io/fabric/sdk/android/services/e/q:b	()Lio/fabric/sdk/android/services/e/t;
    //   24: astore_1
    //   25: aload_1
    //   26: ifnonnull +22 -> 48
    //   29: invokestatic 171	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   32: ldc 55
    //   34: ldc_w 394
    //   37: invokeinterface 396 3 0
    //   42: aload_0
    //   43: invokevirtual 399	com/crashlytics/android/core/CrashlyticsCore:markInitializationComplete	()V
    //   46: aconst_null
    //   47: areturn
    //   48: aload_0
    //   49: getfield 189	com/crashlytics/android/core/CrashlyticsCore:controller	Lcom/crashlytics/android/core/CrashlyticsController;
    //   52: aload_1
    //   53: invokevirtual 403	com/crashlytics/android/core/CrashlyticsController:registerAnalyticsEventListener	(Lio/fabric/sdk/android/services/e/t;)V
    //   56: aload_1
    //   57: getfield 408	io/fabric/sdk/android/services/e/t:d	Lio/fabric/sdk/android/services/e/m;
    //   60: getfield 413	io/fabric/sdk/android/services/e/m:c	Z
    //   63: ifne +22 -> 85
    //   66: invokestatic 171	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   69: ldc 55
    //   71: ldc_w 415
    //   74: invokeinterface 272 3 0
    //   79: aload_0
    //   80: invokevirtual 399	com/crashlytics/android/core/CrashlyticsCore:markInitializationComplete	()V
    //   83: aconst_null
    //   84: areturn
    //   85: new 417	io/fabric/sdk/android/services/b/q
    //   88: astore_2
    //   89: aload_2
    //   90: invokespecial 418	io/fabric/sdk/android/services/b/q:<init>	()V
    //   93: aload_2
    //   94: aload_0
    //   95: invokevirtual 422	com/crashlytics/android/core/CrashlyticsCore:getContext	()Landroid/content/Context;
    //   98: invokevirtual 425	io/fabric/sdk/android/services/b/q:c	(Landroid/content/Context;)Z
    //   101: ifne +22 -> 123
    //   104: invokestatic 171	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   107: ldc 55
    //   109: ldc_w 427
    //   112: invokeinterface 272 3 0
    //   117: aload_0
    //   118: invokevirtual 399	com/crashlytics/android/core/CrashlyticsCore:markInitializationComplete	()V
    //   121: aconst_null
    //   122: areturn
    //   123: aload_0
    //   124: invokevirtual 431	com/crashlytics/android/core/CrashlyticsCore:getNativeCrashData	()Lcom/crashlytics/android/core/CrashlyticsNdkData;
    //   127: astore_2
    //   128: aload_2
    //   129: ifnull +27 -> 156
    //   132: aload_0
    //   133: getfield 189	com/crashlytics/android/core/CrashlyticsCore:controller	Lcom/crashlytics/android/core/CrashlyticsController;
    //   136: aload_2
    //   137: invokevirtual 435	com/crashlytics/android/core/CrashlyticsController:finalizeNativeReport	(Lcom/crashlytics/android/core/CrashlyticsNdkData;)Z
    //   140: ifne +16 -> 156
    //   143: invokestatic 171	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   146: ldc 55
    //   148: ldc_w 437
    //   151: invokeinterface 272 3 0
    //   156: aload_0
    //   157: getfield 189	com/crashlytics/android/core/CrashlyticsCore:controller	Lcom/crashlytics/android/core/CrashlyticsController;
    //   160: aload_1
    //   161: getfield 440	io/fabric/sdk/android/services/e/t:b	Lio/fabric/sdk/android/services/e/p;
    //   164: invokevirtual 444	com/crashlytics/android/core/CrashlyticsController:finalizeSessions	(Lio/fabric/sdk/android/services/e/p;)Z
    //   167: ifne +16 -> 183
    //   170: invokestatic 171	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   173: ldc 55
    //   175: ldc_w 446
    //   178: invokeinterface 272 3 0
    //   183: aload_0
    //   184: getfield 189	com/crashlytics/android/core/CrashlyticsCore:controller	Lcom/crashlytics/android/core/CrashlyticsController;
    //   187: aload_0
    //   188: getfield 105	com/crashlytics/android/core/CrashlyticsCore:delay	F
    //   191: aload_1
    //   192: invokevirtual 450	com/crashlytics/android/core/CrashlyticsController:submitAllReports	(FLio/fabric/sdk/android/services/e/t;)V
    //   195: goto +22 -> 217
    //   198: astore_1
    //   199: goto +24 -> 223
    //   202: astore_1
    //   203: invokestatic 171	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   206: ldc 55
    //   208: ldc_w 452
    //   211: aload_1
    //   212: invokeinterface 179 4 0
    //   217: aload_0
    //   218: invokevirtual 399	com/crashlytics/android/core/CrashlyticsCore:markInitializationComplete	()V
    //   221: aconst_null
    //   222: areturn
    //   223: aload_0
    //   224: invokevirtual 399	com/crashlytics/android/core/CrashlyticsCore:markInitializationComplete	()V
    //   227: aload_1
    //   228: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	229	0	this	CrashlyticsCore
    //   24	168	1	localt	io.fabric.sdk.android.services.e.t
    //   198	1	1	localObject1	Object
    //   202	26	1	localException	Exception
    //   88	49	2	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   11	25	198	finally
    //   29	42	198	finally
    //   48	79	198	finally
    //   85	117	198	finally
    //   123	128	198	finally
    //   132	156	198	finally
    //   156	183	198	finally
    //   183	195	198	finally
    //   203	217	198	finally
    //   11	25	202	java/lang/Exception
    //   29	42	202	java/lang/Exception
    //   48	79	202	java/lang/Exception
    //   85	117	202	java/lang/Exception
    //   123	128	202	java/lang/Exception
    //   132	156	202	java/lang/Exception
    //   156	183	202	java/lang/Exception
    //   183	195	202	java/lang/Exception
  }
  
  Map<String, String> getAttributes()
  {
    return Collections.unmodifiableMap(this.attributes);
  }
  
  CrashlyticsController getController()
  {
    return this.controller;
  }
  
  public String getIdentifier()
  {
    return "com.crashlytics.sdk.android.crashlytics-core";
  }
  
  CrashlyticsNdkData getNativeCrashData()
  {
    CrashlyticsNdkDataProvider localCrashlyticsNdkDataProvider = this.crashlyticsNdkDataProvider;
    if (localCrashlyticsNdkDataProvider != null) {
      return localCrashlyticsNdkDataProvider.getCrashlyticsNdkData();
    }
    return null;
  }
  
  public PinningInfoProvider getPinningInfoProvider()
  {
    PinningInfoProvider localPinningInfoProvider;
    if (!this.disabled) {
      localPinningInfoProvider = this.pinningInfo;
    } else {
      localPinningInfoProvider = null;
    }
    return localPinningInfoProvider;
  }
  
  String getUserEmail()
  {
    String str;
    if (getIdManager().a()) {
      str = this.userEmail;
    } else {
      str = null;
    }
    return str;
  }
  
  String getUserIdentifier()
  {
    String str;
    if (getIdManager().a()) {
      str = this.userId;
    } else {
      str = null;
    }
    return str;
  }
  
  String getUserName()
  {
    String str;
    if (getIdManager().a()) {
      str = this.userName;
    } else {
      str = null;
    }
    return str;
  }
  
  public String getVersion()
  {
    return "2.6.4.27";
  }
  
  boolean internalVerifyPinning(URL paramURL)
  {
    if (getPinningInfoProvider() != null)
    {
      paramURL = this.httpRequestFactory.a(io.fabric.sdk.android.services.network.c.a, paramURL.toString());
      ((HttpsURLConnection)paramURL.a()).setInstanceFollowRedirects(false);
      paramURL.b();
      return true;
    }
    return false;
  }
  
  public void log(int paramInt, String paramString1, String paramString2)
  {
    doLog(paramInt, paramString1, paramString2);
    k localk = io.fabric.sdk.android.c.g();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(paramString1);
    paramString1 = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(paramString2);
    localk.a(paramInt, paramString1, localStringBuilder.toString(), true);
  }
  
  public void log(String paramString)
  {
    doLog(3, "CrashlyticsCore", paramString);
  }
  
  public void logException(Throwable paramThrowable)
  {
    if (this.disabled) {
      return;
    }
    if (!ensureFabricWithCalled("prior to logging exceptions.")) {
      return;
    }
    if (paramThrowable == null)
    {
      io.fabric.sdk.android.c.g().a(5, "CrashlyticsCore", "Crashlytics is ignoring a request to log a null exception.");
      return;
    }
    this.controller.writeNonFatalException(Thread.currentThread(), paramThrowable);
  }
  
  void markInitializationComplete()
  {
    this.backgroundWorker.submit(new Callable()
    {
      public Boolean call()
      {
        try
        {
          boolean bool = CrashlyticsCore.this.initializationMarker.remove();
          k localk = io.fabric.sdk.android.c.g();
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localStringBuilder.append("Initialization marker file removed: ");
          localStringBuilder.append(bool);
          localk.a("CrashlyticsCore", localStringBuilder.toString());
          return Boolean.valueOf(bool);
        }
        catch (Exception localException)
        {
          io.fabric.sdk.android.c.g().e("CrashlyticsCore", "Problem encountered deleting Crashlytics initialization marker.", localException);
        }
        return Boolean.valueOf(false);
      }
    });
  }
  
  void markInitializationStarted()
  {
    this.backgroundWorker.submitAndWait(new Callable()
    {
      public Void call()
      {
        CrashlyticsCore.this.initializationMarker.create();
        io.fabric.sdk.android.c.g().a("CrashlyticsCore", "Initialization marker file created.");
        return null;
      }
    });
  }
  
  protected boolean onPreExecute()
  {
    return onPreExecute(super.getContext());
  }
  
  boolean onPreExecute(Context paramContext)
  {
    if (!new q().c(paramContext))
    {
      io.fabric.sdk.android.c.g().a("CrashlyticsCore", "Crashlytics is disabled, because data collection is disabled by Firebase.");
      this.disabled = true;
    }
    if (this.disabled) {
      return false;
    }
    Object localObject1 = new io.fabric.sdk.android.services.b.g().a(paramContext);
    if (localObject1 == null) {
      return false;
    }
    Object localObject2 = i.m(paramContext);
    if (isBuildIdValid((String)localObject2, i.a(paramContext, "com.crashlytics.RequireBuildId", true))) {
      try
      {
        Object localObject3 = io.fabric.sdk.android.c.g();
        Object localObject4 = new java/lang/StringBuilder;
        ((StringBuilder)localObject4).<init>();
        ((StringBuilder)localObject4).append("Initializing Crashlytics ");
        ((StringBuilder)localObject4).append(getVersion());
        ((k)localObject3).c("CrashlyticsCore", ((StringBuilder)localObject4).toString());
        localObject4 = new io/fabric/sdk/android/services/d/b;
        ((io.fabric.sdk.android.services.d.b)localObject4).<init>(this);
        localObject3 = new com/crashlytics/android/core/CrashlyticsFileMarker;
        ((CrashlyticsFileMarker)localObject3).<init>("crash_marker", (a)localObject4);
        this.crashMarker = ((CrashlyticsFileMarker)localObject3);
        localObject3 = new com/crashlytics/android/core/CrashlyticsFileMarker;
        ((CrashlyticsFileMarker)localObject3).<init>("initialization_marker", (a)localObject4);
        this.initializationMarker = ((CrashlyticsFileMarker)localObject3);
        localObject3 = new io/fabric/sdk/android/services/d/d;
        ((io.fabric.sdk.android.services.d.d)localObject3).<init>(getContext(), "com.crashlytics.android.core.CrashlyticsCore");
        PreferenceManager localPreferenceManager = PreferenceManager.create((io.fabric.sdk.android.services.d.c)localObject3, this);
        if (this.pinningInfo != null)
        {
          localObject3 = new com/crashlytics/android/core/CrashlyticsPinningInfoProvider;
          ((CrashlyticsPinningInfoProvider)localObject3).<init>(this.pinningInfo);
        }
        else
        {
          localObject3 = null;
        }
        Object localObject5 = new io/fabric/sdk/android/services/network/b;
        ((io.fabric.sdk.android.services.network.b)localObject5).<init>(io.fabric.sdk.android.c.g());
        this.httpRequestFactory = ((io.fabric.sdk.android.services.network.d)localObject5);
        this.httpRequestFactory.a((f)localObject3);
        localObject3 = getIdManager();
        localObject2 = AppData.create(paramContext, (r)localObject3, (String)localObject1, (String)localObject2);
        localObject5 = new com/crashlytics/android/core/ManifestUnityVersionProvider;
        ((ManifestUnityVersionProvider)localObject5).<init>(paramContext, ((AppData)localObject2).packageName);
        AppMeasurementEventListenerRegistrar localAppMeasurementEventListenerRegistrar = DefaultAppMeasurementEventListenerRegistrar.instanceFrom(this);
        localObject1 = AppMeasurementEventLogger.getEventLogger(paramContext);
        Object localObject6 = io.fabric.sdk.android.c.g();
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("Installer package name is: ");
        localStringBuilder.append(((AppData)localObject2).installerPackageName);
        ((k)localObject6).a("CrashlyticsCore", localStringBuilder.toString());
        localObject6 = new com/crashlytics/android/core/CrashlyticsController;
        ((CrashlyticsController)localObject6).<init>(this, this.backgroundWorker, this.httpRequestFactory, (r)localObject3, localPreferenceManager, (a)localObject4, (AppData)localObject2, (UnityVersionProvider)localObject5, localAppMeasurementEventListenerRegistrar, (EventLogger)localObject1);
        this.controller = ((CrashlyticsController)localObject6);
        boolean bool1 = didPreviousInitializationFail();
        checkForPreviousCrash();
        localObject3 = new io/fabric/sdk/android/services/b/q;
        ((q)localObject3).<init>();
        boolean bool2 = ((q)localObject3).b(paramContext);
        this.controller.enableExceptionHandling(Thread.getDefaultUncaughtExceptionHandler(), bool2);
        if ((bool1) && (i.n(paramContext)))
        {
          io.fabric.sdk.android.c.g().a("CrashlyticsCore", "Crashlytics did not finish previous background initialization. Initializing synchronously.");
          finishInitSynchronously();
          return false;
        }
        io.fabric.sdk.android.c.g().a("CrashlyticsCore", "Exception handling initialization successful");
        return true;
      }
      catch (Exception paramContext)
      {
        io.fabric.sdk.android.c.g().e("CrashlyticsCore", "Crashlytics was not started due to an exception during initialization", paramContext);
        this.controller = null;
        return false;
      }
    }
    throw new UnmetDependencyException("This app relies on Crashlytics. Please sign up for access at https://fabric.io/sign_up,\ninstall an Android build tool and ask a team member to invite you to this app's organization.");
  }
  
  public void setBool(String paramString, boolean paramBoolean)
  {
    setString(paramString, Boolean.toString(paramBoolean));
  }
  
  void setCrashlyticsNdkDataProvider(CrashlyticsNdkDataProvider paramCrashlyticsNdkDataProvider)
  {
    this.crashlyticsNdkDataProvider = paramCrashlyticsNdkDataProvider;
  }
  
  public void setDouble(String paramString, double paramDouble)
  {
    setString(paramString, Double.toString(paramDouble));
  }
  
  public void setFloat(String paramString, float paramFloat)
  {
    setString(paramString, Float.toString(paramFloat));
  }
  
  public void setInt(String paramString, int paramInt)
  {
    setString(paramString, Integer.toString(paramInt));
  }
  
  @Deprecated
  public void setListener(CrashlyticsListener paramCrashlyticsListener)
  {
    try
    {
      io.fabric.sdk.android.c.g().d("CrashlyticsCore", "Use of setListener is deprecated.");
      if (paramCrashlyticsListener != null)
      {
        this.listener = paramCrashlyticsListener;
        return;
      }
      paramCrashlyticsListener = new java/lang/IllegalArgumentException;
      paramCrashlyticsListener.<init>("listener must not be null.");
      throw paramCrashlyticsListener;
    }
    finally {}
  }
  
  public void setLong(String paramString, long paramLong)
  {
    setString(paramString, Long.toString(paramLong));
  }
  
  public void setString(String paramString1, String paramString2)
  {
    if (this.disabled) {
      return;
    }
    if (!ensureFabricWithCalled("prior to setting keys.")) {
      return;
    }
    if (paramString1 == null)
    {
      paramString1 = getContext();
      if ((paramString1 != null) && (i.i(paramString1))) {
        throw new IllegalArgumentException("Custom attribute key must not be null.");
      }
      io.fabric.sdk.android.c.g().e("CrashlyticsCore", "Attempting to set custom attribute with null key, ignoring.", null);
      return;
    }
    String str = sanitizeAttribute(paramString1);
    if ((this.attributes.size() >= 64) && (!this.attributes.containsKey(str)))
    {
      io.fabric.sdk.android.c.g().a("CrashlyticsCore", "Exceeded maximum number of custom attributes (64)");
      return;
    }
    if (paramString2 == null) {
      paramString1 = "";
    } else {
      paramString1 = sanitizeAttribute(paramString2);
    }
    this.attributes.put(str, paramString1);
    this.controller.cacheKeyData(this.attributes);
  }
  
  public void setUserEmail(String paramString)
  {
    if (this.disabled) {
      return;
    }
    if (!ensureFabricWithCalled("prior to setting user data.")) {
      return;
    }
    this.userEmail = sanitizeAttribute(paramString);
    this.controller.cacheUserData(this.userId, this.userName, this.userEmail);
  }
  
  public void setUserIdentifier(String paramString)
  {
    if (this.disabled) {
      return;
    }
    if (!ensureFabricWithCalled("prior to setting user data.")) {
      return;
    }
    this.userId = sanitizeAttribute(paramString);
    this.controller.cacheUserData(this.userId, this.userName, this.userEmail);
  }
  
  public void setUserName(String paramString)
  {
    if (this.disabled) {
      return;
    }
    if (!ensureFabricWithCalled("prior to setting user data.")) {
      return;
    }
    this.userName = sanitizeAttribute(paramString);
    this.controller.cacheUserData(this.userId, this.userName, this.userEmail);
  }
  
  public boolean verifyPinning(URL paramURL)
  {
    try
    {
      boolean bool = internalVerifyPinning(paramURL);
      return bool;
    }
    catch (Exception paramURL)
    {
      io.fabric.sdk.android.c.g().e("CrashlyticsCore", "Could not verify SSL pinning", paramURL);
    }
    return false;
  }
  
  public static class Builder
  {
    private float delay = -1.0F;
    private boolean disabled = false;
    private CrashlyticsListener listener;
    private PinningInfoProvider pinningInfoProvider;
    
    public CrashlyticsCore build()
    {
      if (this.delay < 0.0F) {
        this.delay = 1.0F;
      }
      return new CrashlyticsCore(this.delay, this.listener, this.pinningInfoProvider, this.disabled);
    }
    
    public Builder delay(float paramFloat)
    {
      if (paramFloat > 0.0F)
      {
        if (this.delay <= 0.0F)
        {
          this.delay = paramFloat;
          return this;
        }
        throw new IllegalStateException("delay already set.");
      }
      throw new IllegalArgumentException("delay must be greater than 0");
    }
    
    public Builder disabled(boolean paramBoolean)
    {
      this.disabled = paramBoolean;
      return this;
    }
    
    public Builder listener(CrashlyticsListener paramCrashlyticsListener)
    {
      if (paramCrashlyticsListener != null)
      {
        if (this.listener == null)
        {
          this.listener = paramCrashlyticsListener;
          return this;
        }
        throw new IllegalStateException("listener already set.");
      }
      throw new IllegalArgumentException("listener must not be null.");
    }
    
    @Deprecated
    public Builder pinningInfo(PinningInfoProvider paramPinningInfoProvider)
    {
      if (paramPinningInfoProvider != null)
      {
        if (this.pinningInfoProvider == null)
        {
          this.pinningInfoProvider = paramPinningInfoProvider;
          return this;
        }
        throw new IllegalStateException("pinningInfoProvider already set.");
      }
      throw new IllegalArgumentException("pinningInfoProvider must not be null.");
    }
  }
  
  private static final class CrashMarkerCheck
    implements Callable<Boolean>
  {
    private final CrashlyticsFileMarker crashMarker;
    
    public CrashMarkerCheck(CrashlyticsFileMarker paramCrashlyticsFileMarker)
    {
      this.crashMarker = paramCrashlyticsFileMarker;
    }
    
    public Boolean call()
    {
      if (!this.crashMarker.isPresent()) {
        return Boolean.FALSE;
      }
      io.fabric.sdk.android.c.g().a("CrashlyticsCore", "Found previous crash marker.");
      this.crashMarker.remove();
      return Boolean.TRUE;
    }
  }
  
  private static final class NoOpListener
    implements CrashlyticsListener
  {
    public void crashlyticsDidDetectCrashDuringPreviousExecution() {}
  }
}


/* Location:              ~/com/crashlytics/android/core/CrashlyticsCore.class
 *
 * Reversed by:           J
 */