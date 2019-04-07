package android.support.v4.os;

import android.os.Build.VERSION;

public final class CancellationSignal
{
  private boolean mCancelInProgress;
  private Object mCancellationSignalObj;
  private boolean mIsCanceled;
  private OnCancelListener mOnCancelListener;
  
  private void waitForCancelFinishedLocked()
  {
    while (this.mCancelInProgress) {
      try
      {
        wait();
      }
      catch (InterruptedException localInterruptedException) {}
    }
  }
  
  /* Error */
  public void cancel()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 30	android/support/v4/os/CancellationSignal:mIsCanceled	Z
    //   6: ifeq +6 -> 12
    //   9: aload_0
    //   10: monitorexit
    //   11: return
    //   12: aload_0
    //   13: iconst_1
    //   14: putfield 30	android/support/v4/os/CancellationSignal:mIsCanceled	Z
    //   17: aload_0
    //   18: iconst_1
    //   19: putfield 24	android/support/v4/os/CancellationSignal:mCancelInProgress	Z
    //   22: aload_0
    //   23: getfield 32	android/support/v4/os/CancellationSignal:mOnCancelListener	Landroid/support/v4/os/CancellationSignal$OnCancelListener;
    //   26: astore_1
    //   27: aload_0
    //   28: getfield 34	android/support/v4/os/CancellationSignal:mCancellationSignalObj	Ljava/lang/Object;
    //   31: astore_2
    //   32: aload_0
    //   33: monitorexit
    //   34: aload_1
    //   35: ifnull +16 -> 51
    //   38: aload_1
    //   39: invokeinterface 37 1 0
    //   44: goto +7 -> 51
    //   47: astore_1
    //   48: goto +25 -> 73
    //   51: aload_2
    //   52: ifnull +41 -> 93
    //   55: getstatic 43	android/os/Build$VERSION:SDK_INT	I
    //   58: bipush 16
    //   60: if_icmplt +33 -> 93
    //   63: aload_2
    //   64: checkcast 45	android/os/CancellationSignal
    //   67: invokevirtual 47	android/os/CancellationSignal:cancel	()V
    //   70: goto +23 -> 93
    //   73: aload_0
    //   74: monitorenter
    //   75: aload_0
    //   76: iconst_0
    //   77: putfield 24	android/support/v4/os/CancellationSignal:mCancelInProgress	Z
    //   80: aload_0
    //   81: invokevirtual 50	java/lang/Object:notifyAll	()V
    //   84: aload_0
    //   85: monitorexit
    //   86: aload_1
    //   87: athrow
    //   88: astore_1
    //   89: aload_0
    //   90: monitorexit
    //   91: aload_1
    //   92: athrow
    //   93: aload_0
    //   94: monitorenter
    //   95: aload_0
    //   96: iconst_0
    //   97: putfield 24	android/support/v4/os/CancellationSignal:mCancelInProgress	Z
    //   100: aload_0
    //   101: invokevirtual 50	java/lang/Object:notifyAll	()V
    //   104: aload_0
    //   105: monitorexit
    //   106: return
    //   107: astore_1
    //   108: aload_0
    //   109: monitorexit
    //   110: aload_1
    //   111: athrow
    //   112: astore_1
    //   113: aload_0
    //   114: monitorexit
    //   115: aload_1
    //   116: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	117	0	this	CancellationSignal
    //   26	13	1	localOnCancelListener	OnCancelListener
    //   47	40	1	localObject1	Object
    //   88	4	1	localObject2	Object
    //   107	4	1	localObject3	Object
    //   112	4	1	localObject4	Object
    //   31	33	2	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   38	44	47	finally
    //   55	70	47	finally
    //   75	86	88	finally
    //   89	91	88	finally
    //   95	106	107	finally
    //   108	110	107	finally
    //   2	11	112	finally
    //   12	34	112	finally
    //   113	115	112	finally
  }
  
  public Object getCancellationSignalObject()
  {
    if (Build.VERSION.SDK_INT < 16) {
      return null;
    }
    try
    {
      if (this.mCancellationSignalObj == null)
      {
        localObject1 = new android/os/CancellationSignal;
        ((android.os.CancellationSignal)localObject1).<init>();
        this.mCancellationSignalObj = localObject1;
        if (this.mIsCanceled) {
          ((android.os.CancellationSignal)this.mCancellationSignalObj).cancel();
        }
      }
      Object localObject1 = this.mCancellationSignalObj;
      return localObject1;
    }
    finally {}
  }
  
  public boolean isCanceled()
  {
    try
    {
      boolean bool = this.mIsCanceled;
      return bool;
    }
    finally {}
  }
  
  public void setOnCancelListener(OnCancelListener paramOnCancelListener)
  {
    try
    {
      waitForCancelFinishedLocked();
      if (this.mOnCancelListener == paramOnCancelListener) {
        return;
      }
      this.mOnCancelListener = paramOnCancelListener;
      if ((this.mIsCanceled) && (paramOnCancelListener != null))
      {
        paramOnCancelListener.onCancel();
        return;
      }
      return;
    }
    finally {}
  }
  
  public void throwIfCanceled()
  {
    if (!isCanceled()) {
      return;
    }
    throw new OperationCanceledException();
  }
  
  public static abstract interface OnCancelListener
  {
    public abstract void onCancel();
  }
}


/* Location:              ~/android/support/v4/os/CancellationSignal.class
 *
 * Reversed by:           J
 */