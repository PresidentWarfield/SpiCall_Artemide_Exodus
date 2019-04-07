package io.fabric.sdk.android.services.concurrency;

import java.util.Collection;

public abstract interface b<T>
{
  public abstract void addDependency(T paramT);
  
  public abstract boolean areDependenciesMet();
  
  public abstract Collection<T> getDependencies();
}


/* Location:              ~/io/fabric/sdk/android/services/concurrency/b.class
 *
 * Reversed by:           J
 */