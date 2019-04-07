package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.a.a.a;

class m
{
  private static final int[] a = { a.a.colorPrimary };
  
  static void a(Context paramContext)
  {
    paramContext = paramContext.obtainStyledAttributes(a);
    boolean bool = paramContext.hasValue(0);
    paramContext.recycle();
    if (!(bool ^ true)) {
      return;
    }
    throw new IllegalArgumentException("You need to use a Theme.AppCompat theme (or descendant) with the design library.");
  }
}


/* Location:              ~/android/support/design/widget/m.class
 *
 * Reversed by:           J
 */