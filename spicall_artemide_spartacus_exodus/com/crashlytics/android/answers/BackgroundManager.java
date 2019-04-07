package com.crashlytics.android.answers;

import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class BackgroundManager
{
  private static final int BACKGROUND_DELAY = 5000;
  final AtomicReference<ScheduledFuture<?>> backgroundFutureRef = new AtomicReference();
  private final ScheduledExecutorService executorService;
  private volatile boolean flushOnBackground = true;
  boolean inBackground = true;
  private final List<Listener> listeners = new ArrayList();
  
  public BackgroundManager(ScheduledExecutorService paramScheduledExecutorService)
  {
    this.executorService = paramScheduledExecutorService;
  }
  
  private void notifyBackground()
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext()) {
      ((Listener)localIterator.next()).onBackground();
    }
  }
  
  public void onActivityPaused()
  {
    if ((this.flushOnBackground) && (!this.inBackground))
    {
      this.inBackground = true;
      try
      {
        AtomicReference localAtomicReference = this.backgroundFutureRef;
        ScheduledExecutorService localScheduledExecutorService = this.executorService;
        Runnable local1 = new com/crashlytics/android/answers/BackgroundManager$1;
        local1.<init>(this);
        localAtomicReference.compareAndSet(null, localScheduledExecutorService.schedule(local1, 5000L, TimeUnit.MILLISECONDS));
      }
      catch (RejectedExecutionException localRejectedExecutionException)
      {
        c.g().a("Answers", "Failed to schedule background detector", localRejectedExecutionException);
      }
    }
  }
  
  public void onActivityResumed()
  {
    this.inBackground = false;
    ScheduledFuture localScheduledFuture = (ScheduledFuture)this.backgroundFutureRef.getAndSet(null);
    if (localScheduledFuture != null) {
      localScheduledFuture.cancel(false);
    }
  }
  
  public void registerListener(Listener paramListener)
  {
    this.listeners.add(paramListener);
  }
  
  public void setFlushOnBackground(boolean paramBoolean)
  {
    this.flushOnBackground = paramBoolean;
  }
  
  public static abstract interface Listener
  {
    public abstract void onBackground();
  }
}


/* Location:              ~/com/crashlytics/android/answers/BackgroundManager.class
 *
 * Reversed by:           J
 */