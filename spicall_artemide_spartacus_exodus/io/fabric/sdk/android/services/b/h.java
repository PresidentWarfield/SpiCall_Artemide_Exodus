package io.fabric.sdk.android.services.b;

import android.os.Process;

public abstract class h
  implements Runnable
{
  protected abstract void onRun();
  
  public final void run()
  {
    Process.setThreadPriority(10);
    onRun();
  }
}


/* Location:              ~/io/fabric/sdk/android/services/b/h.class
 *
 * Reversed by:           J
 */