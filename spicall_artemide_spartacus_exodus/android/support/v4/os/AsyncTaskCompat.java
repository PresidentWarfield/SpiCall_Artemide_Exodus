package android.support.v4.os;

import android.os.AsyncTask;

@Deprecated
public final class AsyncTaskCompat
{
  @Deprecated
  public static <Params, Progress, Result> AsyncTask<Params, Progress, Result> executeParallel(AsyncTask<Params, Progress, Result> paramAsyncTask, Params... paramVarArgs)
  {
    if (paramAsyncTask != null)
    {
      paramAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paramVarArgs);
      return paramAsyncTask;
    }
    throw new IllegalArgumentException("task can not be null");
  }
}


/* Location:              ~/android/support/v4/os/AsyncTaskCompat.class
 *
 * Reversed by:           J
 */