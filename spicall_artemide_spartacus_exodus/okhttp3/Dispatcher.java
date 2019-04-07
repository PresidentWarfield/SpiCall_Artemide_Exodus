package okhttp3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.internal.Util;

public final class Dispatcher
{
  @Nullable
  private ExecutorService executorService;
  @Nullable
  private Runnable idleCallback;
  private int maxRequests = 64;
  private int maxRequestsPerHost = 5;
  private final Deque<RealCall.AsyncCall> readyAsyncCalls = new ArrayDeque();
  private final Deque<RealCall.AsyncCall> runningAsyncCalls = new ArrayDeque();
  private final Deque<RealCall> runningSyncCalls = new ArrayDeque();
  
  public Dispatcher() {}
  
  public Dispatcher(ExecutorService paramExecutorService)
  {
    this.executorService = paramExecutorService;
  }
  
  private <T> void finished(Deque<T> paramDeque, T paramT)
  {
    try
    {
      if (paramDeque.remove(paramT))
      {
        paramDeque = this.idleCallback;
        if ((!promoteAndExecute()) && (paramDeque != null)) {
          paramDeque.run();
        }
        return;
      }
      paramDeque = new java/lang/AssertionError;
      paramDeque.<init>("Call wasn't in-flight!");
      throw paramDeque;
    }
    finally {}
  }
  
  private boolean promoteAndExecute()
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      Iterator localIterator = this.readyAsyncCalls.iterator();
      while (localIterator.hasNext())
      {
        RealCall.AsyncCall localAsyncCall = (RealCall.AsyncCall)localIterator.next();
        if (this.runningAsyncCalls.size() >= this.maxRequests) {
          break;
        }
        if (runningCallsForHost(localAsyncCall) < this.maxRequestsPerHost)
        {
          localIterator.remove();
          localArrayList.add(localAsyncCall);
          this.runningAsyncCalls.add(localAsyncCall);
        }
      }
      int i = runningCallsCount();
      int j = 0;
      boolean bool;
      if (i > 0) {
        bool = true;
      } else {
        bool = false;
      }
      i = localArrayList.size();
      while (j < i)
      {
        ((RealCall.AsyncCall)localArrayList.get(j)).executeOn(executorService());
        j++;
      }
      return bool;
    }
    finally {}
  }
  
  private int runningCallsForHost(RealCall.AsyncCall paramAsyncCall)
  {
    Iterator localIterator = this.runningAsyncCalls.iterator();
    for (int i = 0; localIterator.hasNext(); i++)
    {
      label12:
      RealCall.AsyncCall localAsyncCall = (RealCall.AsyncCall)localIterator.next();
      if ((localAsyncCall.get().forWebSocket) || (!localAsyncCall.host().equals(paramAsyncCall.host()))) {
        break label12;
      }
    }
    return i;
  }
  
  public void cancelAll()
  {
    try
    {
      Iterator localIterator = this.readyAsyncCalls.iterator();
      while (localIterator.hasNext()) {
        ((RealCall.AsyncCall)localIterator.next()).get().cancel();
      }
      localIterator = this.runningAsyncCalls.iterator();
      while (localIterator.hasNext()) {
        ((RealCall.AsyncCall)localIterator.next()).get().cancel();
      }
      localIterator = this.runningSyncCalls.iterator();
      while (localIterator.hasNext()) {
        ((RealCall)localIterator.next()).cancel();
      }
      return;
    }
    finally {}
  }
  
  void enqueue(RealCall.AsyncCall paramAsyncCall)
  {
    try
    {
      this.readyAsyncCalls.add(paramAsyncCall);
      promoteAndExecute();
      return;
    }
    finally {}
  }
  
  void executed(RealCall paramRealCall)
  {
    try
    {
      this.runningSyncCalls.add(paramRealCall);
      return;
    }
    finally
    {
      paramRealCall = finally;
      throw paramRealCall;
    }
  }
  
  public ExecutorService executorService()
  {
    try
    {
      if (this.executorService == null)
      {
        ThreadPoolExecutor localThreadPoolExecutor = new java/util/concurrent/ThreadPoolExecutor;
        TimeUnit localTimeUnit = TimeUnit.SECONDS;
        localObject1 = new java/util/concurrent/SynchronousQueue;
        ((SynchronousQueue)localObject1).<init>();
        localThreadPoolExecutor.<init>(0, Integer.MAX_VALUE, 60L, localTimeUnit, (BlockingQueue)localObject1, Util.threadFactory("OkHttp Dispatcher", false));
        this.executorService = localThreadPoolExecutor;
      }
      Object localObject1 = this.executorService;
      return (ExecutorService)localObject1;
    }
    finally {}
  }
  
  void finished(RealCall.AsyncCall paramAsyncCall)
  {
    finished(this.runningAsyncCalls, paramAsyncCall);
  }
  
  void finished(RealCall paramRealCall)
  {
    finished(this.runningSyncCalls, paramRealCall);
  }
  
  public int getMaxRequests()
  {
    try
    {
      int i = this.maxRequests;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getMaxRequestsPerHost()
  {
    try
    {
      int i = this.maxRequestsPerHost;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public List<Call> queuedCalls()
  {
    try
    {
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>();
      Object localObject1 = this.readyAsyncCalls.iterator();
      while (((Iterator)localObject1).hasNext()) {
        localArrayList.add(((RealCall.AsyncCall)((Iterator)localObject1).next()).get());
      }
      localObject1 = Collections.unmodifiableList(localArrayList);
      return (List<Call>)localObject1;
    }
    finally {}
  }
  
  public int queuedCallsCount()
  {
    try
    {
      int i = this.readyAsyncCalls.size();
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public List<Call> runningCalls()
  {
    try
    {
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>();
      localArrayList.addAll(this.runningSyncCalls);
      Object localObject1 = this.runningAsyncCalls.iterator();
      while (((Iterator)localObject1).hasNext()) {
        localArrayList.add(((RealCall.AsyncCall)((Iterator)localObject1).next()).get());
      }
      localObject1 = Collections.unmodifiableList(localArrayList);
      return (List<Call>)localObject1;
    }
    finally {}
  }
  
  public int runningCallsCount()
  {
    try
    {
      int i = this.runningAsyncCalls.size();
      int j = this.runningSyncCalls.size();
      return i + j;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setIdleCallback(@Nullable Runnable paramRunnable)
  {
    try
    {
      this.idleCallback = paramRunnable;
      return;
    }
    finally
    {
      paramRunnable = finally;
      throw paramRunnable;
    }
  }
  
  public void setMaxRequests(int paramInt)
  {
    if (paramInt >= 1) {
      try
      {
        this.maxRequests = paramInt;
        promoteAndExecute();
        return;
      }
      finally {}
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("max < 1: ");
    localStringBuilder.append(paramInt);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public void setMaxRequestsPerHost(int paramInt)
  {
    if (paramInt >= 1) {
      try
      {
        this.maxRequestsPerHost = paramInt;
        promoteAndExecute();
        return;
      }
      finally {}
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("max < 1: ");
    localStringBuilder.append(paramInt);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
}


/* Location:              ~/okhttp3/Dispatcher.class
 *
 * Reversed by:           J
 */