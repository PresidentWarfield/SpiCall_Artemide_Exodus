package com.crashlytics.android.core;

import android.os.AsyncTask;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;

public class CrashTest
{
  private void privateMethodThatThrowsException(String paramString)
  {
    throw new RuntimeException(paramString);
  }
  
  public void crashAsyncTask(final long paramLong)
  {
    new AsyncTask()
    {
      protected Void doInBackground(Void... paramAnonymousVarArgs)
      {
        try
        {
          Thread.sleep(paramLong);
          CrashTest.this.throwRuntimeException("Background thread crash");
          return null;
        }
        catch (InterruptedException paramAnonymousVarArgs)
        {
          for (;;) {}
        }
      }
    }.execute(new Void[] { (Void)null });
  }
  
  public void indexOutOfBounds()
  {
    int i = new int[2][10];
    k localk = c.g();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Out of bounds value: ");
    localStringBuilder.append(i);
    localk.a("CrashlyticsCore", localStringBuilder.toString());
  }
  
  public int stackOverflow()
  {
    return stackOverflow() + (int)Math.random();
  }
  
  public void throwFiveChainedExceptions()
  {
    try
    {
      privateMethodThatThrowsException("1");
      return;
    }
    catch (Exception localException1)
    {
      try
      {
        RuntimeException localRuntimeException2 = new java/lang/RuntimeException;
        localRuntimeException2.<init>("2", localException1);
        throw localRuntimeException2;
      }
      catch (Exception localException2)
      {
        try
        {
          localRuntimeException1 = new java/lang/RuntimeException;
          localRuntimeException1.<init>("3", localException2);
          throw localRuntimeException1;
        }
        catch (Exception localException3)
        {
          try
          {
            RuntimeException localRuntimeException1 = new java/lang/RuntimeException;
            localRuntimeException1.<init>("4", localException3);
            throw localRuntimeException1;
          }
          catch (Exception localException4)
          {
            throw new RuntimeException("5", localException4);
          }
        }
      }
    }
  }
  
  public void throwRuntimeException(String paramString)
  {
    throw new RuntimeException(paramString);
  }
}


/* Location:              ~/com/crashlytics/android/core/CrashTest.class
 *
 * Reversed by:           J
 */