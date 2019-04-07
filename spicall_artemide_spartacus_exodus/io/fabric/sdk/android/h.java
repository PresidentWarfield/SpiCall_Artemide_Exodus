package io.fabric.sdk.android;

import android.content.Context;
import io.fabric.sdk.android.services.b.r;
import io.fabric.sdk.android.services.concurrency.l;
import java.io.File;
import java.util.Collection;

public abstract class h<Result>
  implements Comparable<h>
{
  Context context;
  final io.fabric.sdk.android.services.concurrency.d dependsOnAnnotation = (io.fabric.sdk.android.services.concurrency.d)getClass().getAnnotation(io.fabric.sdk.android.services.concurrency.d.class);
  c fabric;
  r idManager;
  f<Result> initializationCallback;
  g<Result> initializationTask = new g(this);
  
  public int compareTo(h paramh)
  {
    if (containsAnnotatedDependency(paramh)) {
      return 1;
    }
    if (paramh.containsAnnotatedDependency(this)) {
      return -1;
    }
    if ((hasAnnotatedDependency()) && (!paramh.hasAnnotatedDependency())) {
      return 1;
    }
    if ((!hasAnnotatedDependency()) && (paramh.hasAnnotatedDependency())) {
      return -1;
    }
    return 0;
  }
  
  boolean containsAnnotatedDependency(h paramh)
  {
    if (hasAnnotatedDependency())
    {
      Class[] arrayOfClass = this.dependsOnAnnotation.a();
      int i = arrayOfClass.length;
      for (int j = 0; j < i; j++) {
        if (arrayOfClass[j].isAssignableFrom(paramh.getClass())) {
          return true;
        }
      }
    }
    return false;
  }
  
  protected abstract Result doInBackground();
  
  public Context getContext()
  {
    return this.context;
  }
  
  protected Collection<l> getDependencies()
  {
    return this.initializationTask.getDependencies();
  }
  
  public c getFabric()
  {
    return this.fabric;
  }
  
  protected r getIdManager()
  {
    return this.idManager;
  }
  
  public abstract String getIdentifier();
  
  public String getPath()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(".Fabric");
    localStringBuilder.append(File.separator);
    localStringBuilder.append(getIdentifier());
    return localStringBuilder.toString();
  }
  
  public abstract String getVersion();
  
  boolean hasAnnotatedDependency()
  {
    boolean bool;
    if (this.dependsOnAnnotation != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  final void initialize()
  {
    this.initializationTask.a(this.fabric.e(), new Void[] { (Void)null });
  }
  
  void injectParameters(Context paramContext, c paramc, f<Result> paramf, r paramr)
  {
    this.fabric = paramc;
    this.context = new d(paramContext, getIdentifier(), getPath());
    this.initializationCallback = paramf;
    this.idManager = paramr;
  }
  
  protected void onCancelled(Result paramResult) {}
  
  protected void onPostExecute(Result paramResult) {}
  
  protected boolean onPreExecute()
  {
    return true;
  }
}


/* Location:              ~/io/fabric/sdk/android/h.class
 *
 * Reversed by:           J
 */