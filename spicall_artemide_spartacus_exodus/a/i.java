package a;

import java.util.concurrent.TimeUnit;

public class i
  extends t
{
  private t a;
  
  public i(t paramt)
  {
    if (paramt != null)
    {
      this.a = paramt;
      return;
    }
    throw new IllegalArgumentException("delegate == null");
  }
  
  public final i a(t paramt)
  {
    if (paramt != null)
    {
      this.a = paramt;
      return this;
    }
    throw new IllegalArgumentException("delegate == null");
  }
  
  public final t a()
  {
    return this.a;
  }
  
  public t clearDeadline()
  {
    return this.a.clearDeadline();
  }
  
  public t clearTimeout()
  {
    return this.a.clearTimeout();
  }
  
  public long deadlineNanoTime()
  {
    return this.a.deadlineNanoTime();
  }
  
  public t deadlineNanoTime(long paramLong)
  {
    return this.a.deadlineNanoTime(paramLong);
  }
  
  public boolean hasDeadline()
  {
    return this.a.hasDeadline();
  }
  
  public void throwIfReached()
  {
    this.a.throwIfReached();
  }
  
  public t timeout(long paramLong, TimeUnit paramTimeUnit)
  {
    return this.a.timeout(paramLong, paramTimeUnit);
  }
  
  public long timeoutNanos()
  {
    return this.a.timeoutNanos();
  }
}


/* Location:              ~/a/i.class
 *
 * Reversed by:           J
 */