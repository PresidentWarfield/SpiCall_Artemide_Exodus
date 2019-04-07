package io.fabric.sdk.android.services.concurrency;

import android.annotation.TargetApi;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class k
  extends ThreadPoolExecutor
{
  private static final int a = Runtime.getRuntime().availableProcessors();
  private static final int b;
  private static final int c;
  
  static
  {
    int i = a;
    b = i + 1;
    c = i * 2 + 1;
  }
  
  <T extends Runnable,  extends b,  extends l,  extends i> k(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, c<T> paramc, ThreadFactory paramThreadFactory)
  {
    super(paramInt1, paramInt2, paramLong, paramTimeUnit, paramc, paramThreadFactory);
    prestartAllCoreThreads();
  }
  
  public static k a()
  {
    return a(b, c);
  }
  
  public static <T extends Runnable,  extends b,  extends l,  extends i> k a(int paramInt1, int paramInt2)
  {
    return new k(paramInt1, paramInt2, 1L, TimeUnit.SECONDS, new c(), new a(10));
  }
  
  protected void afterExecute(Runnable paramRunnable, Throwable paramThrowable)
  {
    l locall = (l)paramRunnable;
    locall.setFinished(true);
    locall.setError(paramThrowable);
    b().d();
    super.afterExecute(paramRunnable, paramThrowable);
  }
  
  public c b()
  {
    return (c)super.getQueue();
  }
  
  @TargetApi(9)
  public void execute(Runnable paramRunnable)
  {
    if (j.isProperDelegate(paramRunnable)) {
      super.execute(paramRunnable);
    } else {
      super.execute(newTaskFor(paramRunnable, null));
    }
  }
  
  protected <T> RunnableFuture<T> newTaskFor(Runnable paramRunnable, T paramT)
  {
    return new h(paramRunnable, paramT);
  }
  
  protected <T> RunnableFuture<T> newTaskFor(Callable<T> paramCallable)
  {
    return new h(paramCallable);
  }
  
  protected static final class a
    implements ThreadFactory
  {
    private final int a;
    
    public a(int paramInt)
    {
      this.a = paramInt;
    }
    
    public Thread newThread(Runnable paramRunnable)
    {
      paramRunnable = new Thread(paramRunnable);
      paramRunnable.setPriority(this.a);
      paramRunnable.setName("Queue");
      return paramRunnable;
    }
  }
}


/* Location:              ~/io/fabric/sdk/android/services/concurrency/k.class
 *
 * Reversed by:           J
 */