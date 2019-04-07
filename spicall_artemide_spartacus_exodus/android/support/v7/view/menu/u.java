package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public class u
  extends h
  implements SubMenu
{
  private h d;
  private j e;
  
  public u(Context paramContext, h paramh, j paramj)
  {
    super(paramContext);
    this.d = paramh;
    this.e = paramj;
  }
  
  public String a()
  {
    Object localObject = this.e;
    int i;
    if (localObject != null) {
      i = ((j)localObject).getItemId();
    } else {
      i = 0;
    }
    if (i == 0) {
      return null;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(super.a());
    ((StringBuilder)localObject).append(":");
    ((StringBuilder)localObject).append(i);
    return ((StringBuilder)localObject).toString();
  }
  
  public void a(h.a parama)
  {
    this.d.a(parama);
  }
  
  boolean a(h paramh, MenuItem paramMenuItem)
  {
    boolean bool;
    if ((!super.a(paramh, paramMenuItem)) && (!this.d.a(paramh, paramMenuItem))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean b()
  {
    return this.d.b();
  }
  
  public boolean c()
  {
    return this.d.c();
  }
  
  public boolean c(j paramj)
  {
    return this.d.c(paramj);
  }
  
  public boolean d(j paramj)
  {
    return this.d.d(paramj);
  }
  
  public MenuItem getItem()
  {
    return this.e;
  }
  
  public h p()
  {
    return this.d.p();
  }
  
  public Menu s()
  {
    return this.d;
  }
  
  public SubMenu setHeaderIcon(int paramInt)
  {
    return (SubMenu)super.e(paramInt);
  }
  
  public SubMenu setHeaderIcon(Drawable paramDrawable)
  {
    return (SubMenu)super.a(paramDrawable);
  }
  
  public SubMenu setHeaderTitle(int paramInt)
  {
    return (SubMenu)super.d(paramInt);
  }
  
  public SubMenu setHeaderTitle(CharSequence paramCharSequence)
  {
    return (SubMenu)super.a(paramCharSequence);
  }
  
  public SubMenu setHeaderView(View paramView)
  {
    return (SubMenu)super.a(paramView);
  }
  
  public SubMenu setIcon(int paramInt)
  {
    this.e.setIcon(paramInt);
    return this;
  }
  
  public SubMenu setIcon(Drawable paramDrawable)
  {
    this.e.setIcon(paramDrawable);
    return this;
  }
  
  public void setQwertyMode(boolean paramBoolean)
  {
    this.d.setQwertyMode(paramBoolean);
  }
}


/* Location:              ~/android/support/v7/view/menu/u.class
 *
 * Reversed by:           J
 */