package io.fabric.sdk.android.services.a;

import android.content.Context;

public abstract class a<T>
  implements c<T>
{
  private final c<T> a;
  
  public a(c<T> paramc)
  {
    this.a = paramc;
  }
  
  private void b(Context paramContext, T paramT)
  {
    if (paramT != null)
    {
      a(paramContext, paramT);
      return;
    }
    throw new NullPointerException();
  }
  
  protected abstract T a(Context paramContext);
  
  public final T a(Context paramContext, d<T> paramd)
  {
    try
    {
      Object localObject1 = a(paramContext);
      Object localObject2 = localObject1;
      if (localObject1 == null)
      {
        if (this.a != null) {
          paramd = this.a.a(paramContext, paramd);
        } else {
          paramd = paramd.load(paramContext);
        }
        b(paramContext, paramd);
        localObject2 = paramd;
      }
      return (T)localObject2;
    }
    finally {}
  }
  
  protected abstract void a(Context paramContext, T paramT);
}


/* Location:              ~/io/fabric/sdk/android/services/a/a.class
 *
 * Reversed by:           J
 */