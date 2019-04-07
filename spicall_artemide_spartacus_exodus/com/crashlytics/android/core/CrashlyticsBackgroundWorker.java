package com.crashlytics.android.core;

import android.os.Looper;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

class CrashlyticsBackgroundWorker
{
  private final ExecutorService executorService;
  
  public CrashlyticsBackgroundWorker(ExecutorService paramExecutorService)
  {
    this.executorService = paramExecutorService;
  }
  
  Future<?> submit(Runnable paramRunnable)
  {
    try
    {
      ExecutorService localExecutorService = this.executorService;
      Runnable local1 = new com/crashlytics/android/core/CrashlyticsBackgroundWorker$1;
      local1.<init>(this, paramRunnable);
      paramRunnable = localExecutorService.submit(local1);
      return paramRunnable;
    }
    catch (RejectedExecutionException paramRunnable)
    {
      c.g().a("CrashlyticsCore", "Executor is shut down because we're handling a fatal crash.");
    }
    return null;
  }
  
  <T> Future<T> submit(Callable<T> paramCallable)
  {
    try
    {
      ExecutorService localExecutorService = this.executorService;
      Callable local2 = new com/crashlytics/android/core/CrashlyticsBackgroundWorker$2;
      local2.<init>(this, paramCallable);
      paramCallable = localExecutorService.submit(local2);
      return paramCallable;
    }
    catch (RejectedExecutionException paramCallable)
    {
      c.g().a("CrashlyticsCore", "Executor is shut down because we're handling a fatal crash.");
    }
    return null;
  }
  
  <T> T submitAndWait(Callable<T> paramCallable)
  {
    try
    {
      if (Looper.getMainLooper() == Looper.myLooper()) {
        return (T)this.executorService.submit(paramCallable).get(4L, TimeUnit.SECONDS);
      }
      paramCallable = this.executorService.submit(paramCallable).get();
      return paramCallable;
    }
    catch (Exception paramCallable)
    {
      c.g().e("CrashlyticsCore", "Failed to execute task.", paramCallable);
      return null;
    }
    catch (RejectedExecutionException paramCallable)
    {
      c.g().a("CrashlyticsCore", "Executor is shut down because we're handling a fatal crash.");
    }
    return null;
  }
}


/* Location:              ~/com/crashlytics/android/core/CrashlyticsBackgroundWorker.class
 *
 * Reversed by:           J
 */