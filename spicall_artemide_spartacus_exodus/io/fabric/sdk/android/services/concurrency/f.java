package io.fabric.sdk.android.services.concurrency;

import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public abstract class f<Params, Progress, Result>
  extends a<Params, Progress, Result>
  implements b<l>, i, l
{
  private final j a = new j();
  
  public void a(l paraml)
  {
    if (b() == a.d.a)
    {
      ((b)e()).addDependency(paraml);
      return;
    }
    throw new IllegalStateException("Must not add Dependency after task is running");
  }
  
  public final void a(ExecutorService paramExecutorService, Params... paramVarArgs)
  {
    super.a(new a(paramExecutorService, this), paramVarArgs);
  }
  
  public boolean areDependenciesMet()
  {
    return ((b)e()).areDependenciesMet();
  }
  
  public int compareTo(Object paramObject)
  {
    return e.a(this, paramObject);
  }
  
  public <T extends b<l>,  extends i,  extends l> T e()
  {
    return this.a;
  }
  
  public Collection<l> getDependencies()
  {
    return ((b)e()).getDependencies();
  }
  
  public e getPriority()
  {
    return ((i)e()).getPriority();
  }
  
  public boolean isFinished()
  {
    return ((l)e()).isFinished();
  }
  
  public void setError(Throwable paramThrowable)
  {
    ((l)e()).setError(paramThrowable);
  }
  
  public void setFinished(boolean paramBoolean)
  {
    ((l)e()).setFinished(paramBoolean);
  }
  
  private static class a<Result>
    implements Executor
  {
    private final Executor a;
    private final f b;
    
    public a(Executor paramExecutor, f paramf)
    {
      this.a = paramExecutor;
      this.b = paramf;
    }
    
    public void execute(Runnable paramRunnable)
    {
      this.a.execute(new h(paramRunnable, null)
      {
        public <T extends b<l>,  extends i,  extends l> T a()
        {
          return f.this;
        }
      });
    }
  }
}


/* Location:              ~/io/fabric/sdk/android/services/concurrency/f.class
 *
 * Reversed by:           J
 */