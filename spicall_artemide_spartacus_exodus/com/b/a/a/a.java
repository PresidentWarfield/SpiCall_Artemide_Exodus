package com.b.a.a;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager.WakeLock;

public abstract class a
  extends IntentService
{
  private static volatile PowerManager.WakeLock a;
  
  public a(String paramString)
  {
    super(paramString);
    setIntentRedelivery(true);
  }
  
  /* Error */
  private static PowerManager.WakeLock a(Context paramContext)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: ldc 2
    //   5: monitorenter
    //   6: getstatic 20	com/b/a/a/a:a	Landroid/os/PowerManager$WakeLock;
    //   9: ifnonnull +28 -> 37
    //   12: aload_0
    //   13: ldc 22
    //   15: invokevirtual 28	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   18: checkcast 30	android/os/PowerManager
    //   21: iconst_1
    //   22: ldc 32
    //   24: invokevirtual 36	android/os/PowerManager:newWakeLock	(ILjava/lang/String;)Landroid/os/PowerManager$WakeLock;
    //   27: putstatic 20	com/b/a/a/a:a	Landroid/os/PowerManager$WakeLock;
    //   30: getstatic 20	com/b/a/a/a:a	Landroid/os/PowerManager$WakeLock;
    //   33: iconst_1
    //   34: invokevirtual 41	android/os/PowerManager$WakeLock:setReferenceCounted	(Z)V
    //   37: getstatic 20	com/b/a/a/a:a	Landroid/os/PowerManager$WakeLock;
    //   40: astore_0
    //   41: ldc 2
    //   43: monitorexit
    //   44: ldc 2
    //   46: monitorexit
    //   47: aload_0
    //   48: areturn
    //   49: astore_0
    //   50: ldc 2
    //   52: monitorexit
    //   53: aload_0
    //   54: athrow
    //   55: astore_0
    //   56: ldc 2
    //   58: monitorexit
    //   59: aload_0
    //   60: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	61	0	paramContext	Context
    // Exception table:
    //   from	to	target	type
    //   6	37	49	finally
    //   37	44	49	finally
    //   50	53	49	finally
    //   3	6	55	finally
    //   53	55	55	finally
  }
  
  public static void a(Context paramContext, Intent paramIntent)
  {
    a(paramContext.getApplicationContext()).acquire();
    paramContext.startService(paramIntent);
  }
  
  protected abstract void a(Intent paramIntent);
  
  protected final void onHandleIntent(Intent paramIntent)
  {
    try
    {
      a(paramIntent);
      return;
    }
    finally
    {
      PowerManager.WakeLock localWakeLock = a(getApplicationContext());
      if (localWakeLock.isHeld()) {
        localWakeLock.release();
      }
    }
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    PowerManager.WakeLock localWakeLock = a(getApplicationContext());
    if ((!localWakeLock.isHeld()) || ((paramInt1 & 0x1) != 0)) {
      localWakeLock.acquire();
    }
    super.onStartCommand(paramIntent, paramInt1, paramInt2);
    return 3;
  }
}


/* Location:              ~/com/b/a/a/a.class
 *
 * Reversed by:           J
 */