package a;

import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

public class t
{
  public static final t NONE = new t()
  {
    public t deadlineNanoTime(long paramAnonymousLong)
    {
      return this;
    }
    
    public void throwIfReached() {}
    
    public t timeout(long paramAnonymousLong, TimeUnit paramAnonymousTimeUnit)
    {
      return this;
    }
  };
  private long deadlineNanoTime;
  private boolean hasDeadline;
  private long timeoutNanos;
  
  public t clearDeadline()
  {
    this.hasDeadline = false;
    return this;
  }
  
  public t clearTimeout()
  {
    this.timeoutNanos = 0L;
    return this;
  }
  
  public final t deadline(long paramLong, TimeUnit paramTimeUnit)
  {
    if (paramLong > 0L)
    {
      if (paramTimeUnit != null) {
        return deadlineNanoTime(System.nanoTime() + paramTimeUnit.toNanos(paramLong));
      }
      throw new IllegalArgumentException("unit == null");
    }
    paramTimeUnit = new StringBuilder();
    paramTimeUnit.append("duration <= 0: ");
    paramTimeUnit.append(paramLong);
    throw new IllegalArgumentException(paramTimeUnit.toString());
  }
  
  public long deadlineNanoTime()
  {
    if (this.hasDeadline) {
      return this.deadlineNanoTime;
    }
    throw new IllegalStateException("No deadline");
  }
  
  public t deadlineNanoTime(long paramLong)
  {
    this.hasDeadline = true;
    this.deadlineNanoTime = paramLong;
    return this;
  }
  
  public boolean hasDeadline()
  {
    return this.hasDeadline;
  }
  
  public void throwIfReached()
  {
    if (!Thread.interrupted())
    {
      if ((this.hasDeadline) && (this.deadlineNanoTime - System.nanoTime() <= 0L)) {
        throw new InterruptedIOException("deadline reached");
      }
      return;
    }
    Thread.currentThread().interrupt();
    throw new InterruptedIOException("interrupted");
  }
  
  public t timeout(long paramLong, TimeUnit paramTimeUnit)
  {
    if (paramLong >= 0L)
    {
      if (paramTimeUnit != null)
      {
        this.timeoutNanos = paramTimeUnit.toNanos(paramLong);
        return this;
      }
      throw new IllegalArgumentException("unit == null");
    }
    paramTimeUnit = new StringBuilder();
    paramTimeUnit.append("timeout < 0: ");
    paramTimeUnit.append(paramLong);
    throw new IllegalArgumentException(paramTimeUnit.toString());
  }
  
  public long timeoutNanos()
  {
    return this.timeoutNanos;
  }
  
  public final void waitUntilNotified(Object paramObject)
  {
    try
    {
      boolean bool = hasDeadline();
      long l1 = timeoutNanos();
      long l2 = 0L;
      if ((!bool) && (l1 == 0L))
      {
        paramObject.wait();
        return;
      }
      long l3 = System.nanoTime();
      if ((bool) && (l1 != 0L)) {
        l1 = Math.min(l1, deadlineNanoTime() - l3);
      } else if (bool) {
        l1 = deadlineNanoTime() - l3;
      }
      if (l1 > 0L)
      {
        l2 = l1 / 1000000L;
        Long.signum(l2);
        int i = (int)(l1 - 1000000L * l2);
        paramObject.wait(l2, i);
        l2 = System.nanoTime() - l3;
      }
      if (l2 < l1) {
        return;
      }
      paramObject = new java/io/InterruptedIOException;
      ((InterruptedIOException)paramObject).<init>("timeout");
      throw ((Throwable)paramObject);
    }
    catch (InterruptedException paramObject)
    {
      Thread.currentThread().interrupt();
      throw new InterruptedIOException("interrupted");
    }
  }
}


/* Location:              ~/a/t.class
 *
 * Reversed by:           J
 */