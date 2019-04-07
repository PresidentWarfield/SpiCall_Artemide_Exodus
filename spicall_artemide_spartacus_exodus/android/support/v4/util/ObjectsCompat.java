package android.support.v4.util;

import android.os.Build.VERSION;
import java.util.Objects;

public class ObjectsCompat
{
  private static final ImplBase IMPL;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 19) {
      IMPL = new ImplApi19(null);
    } else {
      IMPL = new ImplBase(null);
    }
  }
  
  public static boolean equals(Object paramObject1, Object paramObject2)
  {
    return IMPL.equals(paramObject1, paramObject2);
  }
  
  private static class ImplApi19
    extends ObjectsCompat.ImplBase
  {
    private ImplApi19()
    {
      super();
    }
    
    public boolean equals(Object paramObject1, Object paramObject2)
    {
      return Objects.equals(paramObject1, paramObject2);
    }
  }
  
  private static class ImplBase
  {
    public boolean equals(Object paramObject1, Object paramObject2)
    {
      boolean bool;
      if ((paramObject1 != paramObject2) && ((paramObject1 == null) || (!paramObject1.equals(paramObject2)))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
  }
}


/* Location:              ~/android/support/v4/util/ObjectsCompat.class
 *
 * Reversed by:           J
 */