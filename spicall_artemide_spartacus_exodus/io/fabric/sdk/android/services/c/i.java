package io.fabric.sdk.android.services.c;

import android.content.Context;

public class i
  implements Runnable
{
  private final Context a;
  private final e b;
  
  public i(Context paramContext, e parame)
  {
    this.a = paramContext;
    this.b = parame;
  }
  
  public void run()
  {
    try
    {
      io.fabric.sdk.android.services.b.i.a(this.a, "Performing time based file roll over.");
      if (!this.b.rollFileOver()) {
        this.b.cancelTimeBasedFileRollOver();
      }
    }
    catch (Exception localException)
    {
      io.fabric.sdk.android.services.b.i.a(this.a, "Failed to roll over file", localException);
    }
  }
}


/* Location:              ~/io/fabric/sdk/android/services/c/i.class
 *
 * Reversed by:           J
 */