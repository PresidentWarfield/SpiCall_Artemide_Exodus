package com.crashlytics.android.answers;

import io.fabric.sdk.android.services.concurrency.a.e;

class RetryManager
{
  private static final long NANOSECONDS_IN_MS = 1000000L;
  long lastRetry;
  private e retryState;
  
  public RetryManager(e parame)
  {
    if (parame != null)
    {
      this.retryState = parame;
      return;
    }
    throw new NullPointerException("retryState must not be null");
  }
  
  public boolean canRetry(long paramLong)
  {
    long l = this.retryState.a();
    boolean bool;
    if (paramLong - this.lastRetry >= l * 1000000L) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void recordRetry(long paramLong)
  {
    this.lastRetry = paramLong;
    this.retryState = this.retryState.b();
  }
  
  public void reset()
  {
    this.lastRetry = 0L;
    this.retryState = this.retryState.c();
  }
}


/* Location:              ~/com/crashlytics/android/answers/RetryManager.class
 *
 * Reversed by:           J
 */