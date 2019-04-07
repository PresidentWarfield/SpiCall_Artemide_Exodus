package com.crashlytics.android.answers;

import io.fabric.sdk.android.services.concurrency.a.a;
import java.util.Random;

class RandomBackoff
  implements a
{
  final a backoff;
  final double jitterPercent;
  final Random random;
  
  public RandomBackoff(a parama, double paramDouble)
  {
    this(parama, paramDouble, new Random());
  }
  
  public RandomBackoff(a parama, double paramDouble, Random paramRandom)
  {
    if ((paramDouble >= 0.0D) && (paramDouble <= 1.0D))
    {
      if (parama != null)
      {
        if (paramRandom != null)
        {
          this.backoff = parama;
          this.jitterPercent = paramDouble;
          this.random = paramRandom;
          return;
        }
        throw new NullPointerException("random must not be null");
      }
      throw new NullPointerException("backoff must not be null");
    }
    throw new IllegalArgumentException("jitterPercent must be between 0.0 and 1.0");
  }
  
  public long getDelayMillis(int paramInt)
  {
    double d1 = randomJitter();
    double d2 = this.backoff.getDelayMillis(paramInt);
    Double.isNaN(d2);
    return (d1 * d2);
  }
  
  double randomJitter()
  {
    double d1 = this.jitterPercent;
    double d2 = 1.0D - d1;
    return d2 + (d1 + 1.0D - d2) * this.random.nextDouble();
  }
}


/* Location:              ~/com/crashlytics/android/answers/RandomBackoff.class
 *
 * Reversed by:           J
 */