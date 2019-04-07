package io.fabric.sdk.android.services.a;

import android.content.Context;

public class b<T>
  extends a<T>
{
  private T a;
  
  public b()
  {
    this(null);
  }
  
  public b(c<T> paramc)
  {
    super(paramc);
  }
  
  protected T a(Context paramContext)
  {
    return (T)this.a;
  }
  
  protected void a(Context paramContext, T paramT)
  {
    this.a = paramT;
  }
}


/* Location:              ~/io/fabric/sdk/android/services/a/b.class
 *
 * Reversed by:           J
 */