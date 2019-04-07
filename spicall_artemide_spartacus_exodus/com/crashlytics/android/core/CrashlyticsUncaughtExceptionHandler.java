package com.crashlytics.android.core;

import io.fabric.sdk.android.services.e.t;
import java.util.concurrent.atomic.AtomicBoolean;

class CrashlyticsUncaughtExceptionHandler
  implements Thread.UncaughtExceptionHandler
{
  private final CrashListener crashListener;
  private final Thread.UncaughtExceptionHandler defaultHandler;
  private final boolean firebaseCrashlyticsClientFlag;
  private final AtomicBoolean isHandlingException;
  private final SettingsDataProvider settingsDataProvider;
  
  public CrashlyticsUncaughtExceptionHandler(CrashListener paramCrashListener, SettingsDataProvider paramSettingsDataProvider, boolean paramBoolean, Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler)
  {
    this.crashListener = paramCrashListener;
    this.settingsDataProvider = paramSettingsDataProvider;
    this.firebaseCrashlyticsClientFlag = paramBoolean;
    this.defaultHandler = paramUncaughtExceptionHandler;
    this.isHandlingException = new AtomicBoolean(false);
  }
  
  boolean isHandlingException()
  {
    return this.isHandlingException.get();
  }
  
  /* Error */
  public void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 42	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:isHandlingException	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   4: iconst_1
    //   5: invokevirtual 54	java/util/concurrent/atomic/AtomicBoolean:set	(Z)V
    //   8: aload_0
    //   9: getfield 29	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:crashListener	Lcom/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler$CrashListener;
    //   12: aload_0
    //   13: getfield 31	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:settingsDataProvider	Lcom/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler$SettingsDataProvider;
    //   16: aload_1
    //   17: aload_2
    //   18: aload_0
    //   19: getfield 33	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:firebaseCrashlyticsClientFlag	Z
    //   22: invokeinterface 58 5 0
    //   27: invokestatic 64	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   30: ldc 66
    //   32: ldc 68
    //   34: invokeinterface 74 3 0
    //   39: aload_0
    //   40: getfield 35	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:defaultHandler	Ljava/lang/Thread$UncaughtExceptionHandler;
    //   43: aload_1
    //   44: aload_2
    //   45: invokeinterface 76 3 0
    //   50: aload_0
    //   51: getfield 42	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:isHandlingException	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   54: iconst_0
    //   55: invokevirtual 54	java/util/concurrent/atomic/AtomicBoolean:set	(Z)V
    //   58: goto +24 -> 82
    //   61: astore_3
    //   62: goto +21 -> 83
    //   65: astore_3
    //   66: invokestatic 64	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   69: ldc 66
    //   71: ldc 78
    //   73: aload_3
    //   74: invokeinterface 82 4 0
    //   79: goto -52 -> 27
    //   82: return
    //   83: invokestatic 64	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   86: ldc 66
    //   88: ldc 68
    //   90: invokeinterface 74 3 0
    //   95: aload_0
    //   96: getfield 35	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:defaultHandler	Ljava/lang/Thread$UncaughtExceptionHandler;
    //   99: aload_1
    //   100: aload_2
    //   101: invokeinterface 76 3 0
    //   106: aload_0
    //   107: getfield 42	com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler:isHandlingException	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   110: iconst_0
    //   111: invokevirtual 54	java/util/concurrent/atomic/AtomicBoolean:set	(Z)V
    //   114: aload_3
    //   115: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	116	0	this	CrashlyticsUncaughtExceptionHandler
    //   0	116	1	paramThread	Thread
    //   0	116	2	paramThrowable	Throwable
    //   61	1	3	localObject	Object
    //   65	50	3	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   8	27	61	finally
    //   66	79	61	finally
    //   8	27	65	java/lang/Exception
  }
  
  static abstract interface CrashListener
  {
    public abstract void onUncaughtException(CrashlyticsUncaughtExceptionHandler.SettingsDataProvider paramSettingsDataProvider, Thread paramThread, Throwable paramThrowable, boolean paramBoolean);
  }
  
  static abstract interface SettingsDataProvider
  {
    public abstract t getSettingsData();
  }
}


/* Location:              ~/com/crashlytics/android/core/CrashlyticsUncaughtExceptionHandler.class
 *
 * Reversed by:           J
 */