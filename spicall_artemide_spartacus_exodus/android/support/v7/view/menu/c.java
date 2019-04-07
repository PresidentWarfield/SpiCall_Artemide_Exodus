package android.support.v7.view.menu;

import android.content.Context;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.internal.view.SupportSubMenu;
import android.support.v4.util.ArrayMap;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

abstract class c<T>
  extends d<T>
{
  final Context a;
  private Map<SupportMenuItem, MenuItem> c;
  private Map<SupportSubMenu, SubMenu> d;
  
  c(Context paramContext, T paramT)
  {
    super(paramT);
    this.a = paramContext;
  }
  
  final MenuItem a(MenuItem paramMenuItem)
  {
    if ((paramMenuItem instanceof SupportMenuItem))
    {
      SupportMenuItem localSupportMenuItem = (SupportMenuItem)paramMenuItem;
      if (this.c == null) {
        this.c = new ArrayMap();
      }
      MenuItem localMenuItem = (MenuItem)this.c.get(paramMenuItem);
      paramMenuItem = localMenuItem;
      if (localMenuItem == null)
      {
        paramMenuItem = q.a(this.a, localSupportMenuItem);
        this.c.put(localSupportMenuItem, paramMenuItem);
      }
      return paramMenuItem;
    }
    return paramMenuItem;
  }
  
  final SubMenu a(SubMenu paramSubMenu)
  {
    if ((paramSubMenu instanceof SupportSubMenu))
    {
      SupportSubMenu localSupportSubMenu = (SupportSubMenu)paramSubMenu;
      if (this.d == null) {
        this.d = new ArrayMap();
      }
      SubMenu localSubMenu = (SubMenu)this.d.get(localSupportSubMenu);
      paramSubMenu = localSubMenu;
      if (localSubMenu == null)
      {
        paramSubMenu = q.a(this.a, localSupportSubMenu);
        this.d.put(localSupportSubMenu, paramSubMenu);
      }
      return paramSubMenu;
    }
    return paramSubMenu;
  }
  
  final void a()
  {
    Map localMap = this.c;
    if (localMap != null) {
      localMap.clear();
    }
    localMap = this.d;
    if (localMap != null) {
      localMap.clear();
    }
  }
  
  final void a(int paramInt)
  {
    Object localObject = this.c;
    if (localObject == null) {
      return;
    }
    localObject = ((Map)localObject).keySet().iterator();
    while (((Iterator)localObject).hasNext()) {
      if (paramInt == ((MenuItem)((Iterator)localObject).next()).getGroupId()) {
        ((Iterator)localObject).remove();
      }
    }
  }
  
  final void b(int paramInt)
  {
    Object localObject = this.c;
    if (localObject == null) {
      return;
    }
    localObject = ((Map)localObject).keySet().iterator();
    while (((Iterator)localObject).hasNext()) {
      if (paramInt == ((MenuItem)((Iterator)localObject).next()).getItemId()) {
        ((Iterator)localObject).remove();
      }
    }
  }
}


/* Location:              ~/android/support/v7/view/menu/c.class
 *
 * Reversed by:           J
 */