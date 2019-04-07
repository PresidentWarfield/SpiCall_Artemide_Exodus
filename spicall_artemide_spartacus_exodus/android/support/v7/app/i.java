package android.support.v7.app;

import android.content.Context;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.Window;
import android.view.Window.Callback;
import java.util.List;

class i
  extends l
{
  i(Context paramContext, Window paramWindow, f paramf)
  {
    super(paramContext, paramWindow, paramf);
  }
  
  Window.Callback a(Window.Callback paramCallback)
  {
    return new a(paramCallback);
  }
  
  class a
    extends l.a
  {
    a(Window.Callback paramCallback)
    {
      super(paramCallback);
    }
    
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> paramList, Menu paramMenu, int paramInt)
    {
      m.d locald = i.this.a(0, true);
      if ((locald != null) && (locald.j != null)) {
        super.onProvideKeyboardShortcuts(paramList, locald.j, paramInt);
      } else {
        super.onProvideKeyboardShortcuts(paramList, paramMenu, paramInt);
      }
    }
  }
}


/* Location:              ~/android/support/v7/app/i.class
 *
 * Reversed by:           J
 */