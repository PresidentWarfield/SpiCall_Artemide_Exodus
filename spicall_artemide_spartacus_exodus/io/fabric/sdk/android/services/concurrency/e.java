package io.fabric.sdk.android.services.concurrency;

public enum e
{
  private e() {}
  
  static <Y> int a(i parami, Y paramY)
  {
    if ((paramY instanceof i)) {
      paramY = ((i)paramY).getPriority();
    } else {
      paramY = b;
    }
    return paramY.ordinal() - parami.getPriority().ordinal();
  }
}


/* Location:              ~/io/fabric/sdk/android/services/concurrency/e.class
 *
 * Reversed by:           J
 */