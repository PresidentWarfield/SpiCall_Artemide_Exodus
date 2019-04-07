package io.fabric.sdk.android.services.concurrency.a;

public class c
  implements a
{
  private final long a;
  private final int b;
  
  public c(long paramLong, int paramInt)
  {
    this.a = paramLong;
    this.b = paramInt;
  }
  
  public long getDelayMillis(int paramInt)
  {
    double d1 = this.a;
    double d2 = Math.pow(this.b, paramInt);
    Double.isNaN(d1);
    return (d1 * d2);
  }
}


/* Location:              ~/io/fabric/sdk/android/services/concurrency/a/c.class
 *
 * Reversed by:           J
 */