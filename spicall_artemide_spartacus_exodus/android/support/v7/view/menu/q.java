package android.support.v7.view.menu;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.internal.view.SupportSubMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public final class q
{
  public static Menu a(Context paramContext, SupportMenu paramSupportMenu)
  {
    return new r(paramContext, paramSupportMenu);
  }
  
  public static MenuItem a(Context paramContext, SupportMenuItem paramSupportMenuItem)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return new l(paramContext, paramSupportMenuItem);
    }
    return new k(paramContext, paramSupportMenuItem);
  }
  
  public static SubMenu a(Context paramContext, SupportSubMenu paramSupportSubMenu)
  {
    return new v(paramContext, paramSupportSubMenu);
  }
}


/* Location:              ~/android/support/v7/view/menu/q.class
 *
 * Reversed by:           J
 */