package io.fabric.sdk.android.services.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class j
  implements b<l>, i, l
{
  private final List<l> dependencies = new ArrayList();
  private final AtomicBoolean hasRun = new AtomicBoolean(false);
  private final AtomicReference<Throwable> throwable = new AtomicReference(null);
  
  public static boolean isProperDelegate(Object paramObject)
  {
    boolean bool1 = false;
    try
    {
      b localb = (b)paramObject;
      l locall = (l)paramObject;
      paramObject = (i)paramObject;
      boolean bool2 = bool1;
      if (localb != null)
      {
        bool2 = bool1;
        if (locall != null)
        {
          bool2 = bool1;
          if (paramObject != null) {
            bool2 = true;
          }
        }
      }
      return bool2;
    }
    catch (ClassCastException paramObject) {}
    return false;
  }
  
  public void addDependency(l paraml)
  {
    try
    {
      this.dependencies.add(paraml);
      return;
    }
    finally
    {
      paraml = finally;
      throw paraml;
    }
  }
  
  public boolean areDependenciesMet()
  {
    Iterator localIterator = getDependencies().iterator();
    while (localIterator.hasNext()) {
      if (!((l)localIterator.next()).isFinished()) {
        return false;
      }
    }
    return true;
  }
  
  public int compareTo(Object paramObject)
  {
    return e.a(this, paramObject);
  }
  
  public Collection<l> getDependencies()
  {
    try
    {
      Collection localCollection = Collections.unmodifiableCollection(this.dependencies);
      return localCollection;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public Throwable getError()
  {
    return (Throwable)this.throwable.get();
  }
  
  public e getPriority()
  {
    return e.b;
  }
  
  public boolean isFinished()
  {
    return this.hasRun.get();
  }
  
  public void setError(Throwable paramThrowable)
  {
    this.throwable.set(paramThrowable);
  }
  
  public void setFinished(boolean paramBoolean)
  {
    try
    {
      this.hasRun.set(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              ~/io/fabric/sdk/android/services/concurrency/j.class
 *
 * Reversed by:           J
 */