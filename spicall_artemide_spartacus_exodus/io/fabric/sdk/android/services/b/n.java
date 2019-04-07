package io.fabric.sdk.android.services.b;

import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class n
{
  public static ExecutorService a(String paramString)
  {
    ExecutorService localExecutorService = Executors.newSingleThreadExecutor(c(paramString));
    a(paramString, localExecutorService);
    return localExecutorService;
  }
  
  private static final void a(String paramString, ExecutorService paramExecutorService)
  {
    a(paramString, paramExecutorService, 2L, TimeUnit.SECONDS);
  }
  
  public static final void a(String paramString, final ExecutorService paramExecutorService, final long paramLong, TimeUnit paramTimeUnit)
  {
    Runtime localRuntime = Runtime.getRuntime();
    paramExecutorService = new h()
    {
      public void onRun()
      {
        try
        {
          Object localObject1 = c.g();
          Object localObject2 = new java/lang/StringBuilder;
          ((StringBuilder)localObject2).<init>();
          ((StringBuilder)localObject2).append("Executing shutdown hook for ");
          ((StringBuilder)localObject2).append(this.a);
          ((k)localObject1).a("Fabric", ((StringBuilder)localObject2).toString());
          paramExecutorService.shutdown();
          if (!paramExecutorService.awaitTermination(paramLong, this.d))
          {
            localObject2 = c.g();
            localObject1 = new java/lang/StringBuilder;
            ((StringBuilder)localObject1).<init>();
            ((StringBuilder)localObject1).append(this.a);
            ((StringBuilder)localObject1).append(" did not shut down in the allocated time. Requesting immediate shutdown.");
            ((k)localObject2).a("Fabric", ((StringBuilder)localObject1).toString());
            paramExecutorService.shutdownNow();
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          c.g().a("Fabric", String.format(Locale.US, "Interrupted while waiting for %s to shut down. Requesting immediate shutdown.", new Object[] { this.a }));
          paramExecutorService.shutdownNow();
        }
      }
    };
    paramTimeUnit = new StringBuilder();
    paramTimeUnit.append("Crashlytics Shutdown Hook for ");
    paramTimeUnit.append(paramString);
    localRuntime.addShutdownHook(new Thread(paramExecutorService, paramTimeUnit.toString()));
  }
  
  public static ScheduledExecutorService b(String paramString)
  {
    ScheduledExecutorService localScheduledExecutorService = Executors.newSingleThreadScheduledExecutor(c(paramString));
    a(paramString, localScheduledExecutorService);
    return localScheduledExecutorService;
  }
  
  public static final ThreadFactory c(String paramString)
  {
    new ThreadFactory()
    {
      public Thread newThread(final Runnable paramAnonymousRunnable)
      {
        Thread localThread = Executors.defaultThreadFactory().newThread(new h()
        {
          public void onRun()
          {
            paramAnonymousRunnable.run();
          }
        });
        paramAnonymousRunnable = new StringBuilder();
        paramAnonymousRunnable.append(this.a);
        paramAnonymousRunnable.append(this.b.getAndIncrement());
        localThread.setName(paramAnonymousRunnable.toString());
        return localThread;
      }
    };
  }
}


/* Location:              ~/io/fabric/sdk/android/services/b/n.class
 *
 * Reversed by:           J
 */