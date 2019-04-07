package android.support.v4.widget;

import android.os.Build.VERSION;
import android.view.View.OnTouchListener;
import android.widget.PopupMenu;

public final class PopupMenuCompat
{
  public static View.OnTouchListener getDragToOpenListener(Object paramObject)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return ((PopupMenu)paramObject).getDragToOpenListener();
    }
    return null;
  }
}


/* Location:              ~/android/support/v4/widget/PopupMenuCompat.class
 *
 * Reversed by:           J
 */