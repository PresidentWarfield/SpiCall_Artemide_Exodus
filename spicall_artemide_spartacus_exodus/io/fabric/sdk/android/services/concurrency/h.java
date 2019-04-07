package io.fabric.sdk.android.services.concurrency;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class h<V>
  extends FutureTask<V>
  implements b<l>, i, l
{
  final Object b = a(paramCallable);
  
  public h(Runnable paramRunnable, V paramV)
  {
    super(paramRunnable, paramV);
  }
  
  public h(Callable<V> paramCallable)
  {
    super(paramCallable);
  }
  
  public <T extends b<l>,  extends i,  extends l> T a()
  {
    return (b)this.b;
  }
  
  protected <T extends b<l>,  extends i,  extends l> T a(Object paramObject)
  {
    if (j.isProperDelegate(paramObject)) {
      return (b)paramObject;
    }
    return new j();
  }
  
  public void a(l paraml)
  {
    ((b)a()).addDependency(paraml);
  }
  
  public boolean areDependenciesMet()
  {
    return ((b)a()).areDependenciesMet();
  }
  
  public int compareTo(Object paramObject)
  {
    return ((i)a()).compareTo(paramObject);
  }
  
  public Collection<l> getDependencies()
  {
    return ((b)a()).getDependencies();
  }
  
  public e getPriority()
  {
    return ((i)a()).getPriority();
  }
  
  public boolean isFinished()
  {
    return ((l)a()).isFinished();
  }
  
  public void setError(Throwable paramThrowable)
  {
    ((l)a()).setError(paramThrowable);
  }
  
  public void setFinished(boolean paramBoolean)
  {
    ((l)a()).setFinished(paramBoolean);
  }
}


/* Location:              ~/io/fabric/sdk/android/services/concurrency/h.class
 *
 * Reversed by:           J
 */