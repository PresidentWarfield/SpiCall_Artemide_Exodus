package android.support.v7.app;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.view.i;
import android.support.v7.view.menu.h;
import android.support.v7.view.menu.h.a;
import android.support.v7.view.menu.o.a;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window.Callback;
import java.util.ArrayList;

class q
  extends a
{
  DecorToolbar a;
  boolean b;
  Window.Callback c;
  private boolean d;
  private boolean e;
  private ArrayList<a.b> f = new ArrayList();
  private final Runnable g = new Runnable()
  {
    public void run()
    {
      q.this.i();
    }
  };
  private final Toolbar.OnMenuItemClickListener h = new Toolbar.OnMenuItemClickListener()
  {
    public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
    {
      return q.this.c.onMenuItemSelected(0, paramAnonymousMenuItem);
    }
  };
  
  q(Toolbar paramToolbar, CharSequence paramCharSequence, Window.Callback paramCallback)
  {
    this.a = new ToolbarWidgetWrapper(paramToolbar, false);
    this.c = new c(paramCallback);
    this.a.setWindowCallback(this.c);
    paramToolbar.setOnMenuItemClickListener(this.h);
    this.a.setWindowTitle(paramCharSequence);
  }
  
  private Menu j()
  {
    if (!this.d)
    {
      this.a.setMenuCallbacks(new a(), new b());
      this.d = true;
    }
    return this.a.getMenu();
  }
  
  public int a()
  {
    return this.a.getDisplayOptions();
  }
  
  public void a(float paramFloat)
  {
    ViewCompat.setElevation(this.a.getViewGroup(), paramFloat);
  }
  
  public void a(int paramInt)
  {
    this.a.setNavigationContentDescription(paramInt);
  }
  
  public void a(Configuration paramConfiguration)
  {
    super.a(paramConfiguration);
  }
  
  public void a(Drawable paramDrawable)
  {
    this.a.setNavigationIcon(paramDrawable);
  }
  
  public void a(CharSequence paramCharSequence)
  {
    this.a.setWindowTitle(paramCharSequence);
  }
  
  public void a(boolean paramBoolean) {}
  
  public boolean a(int paramInt, KeyEvent paramKeyEvent)
  {
    Menu localMenu = j();
    if (localMenu != null)
    {
      if (paramKeyEvent != null) {
        i = paramKeyEvent.getDeviceId();
      } else {
        i = -1;
      }
      int i = KeyCharacterMap.load(i).getKeyboardType();
      boolean bool = true;
      if (i == 1) {
        bool = false;
      }
      localMenu.setQwertyMode(bool);
      return localMenu.performShortcut(paramInt, paramKeyEvent, 0);
    }
    return false;
  }
  
  public boolean a(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 1) {
      c();
    }
    return true;
  }
  
  public Context b()
  {
    return this.a.getContext();
  }
  
  public void c(boolean paramBoolean) {}
  
  public boolean c()
  {
    return this.a.showOverflowMenu();
  }
  
  public void d(boolean paramBoolean) {}
  
  public boolean d()
  {
    return this.a.hideOverflowMenu();
  }
  
  public void e(boolean paramBoolean)
  {
    if (paramBoolean == this.e) {
      return;
    }
    this.e = paramBoolean;
    int i = this.f.size();
    for (int j = 0; j < i; j++) {
      ((a.b)this.f.get(j)).a(paramBoolean);
    }
  }
  
  public boolean e()
  {
    this.a.getViewGroup().removeCallbacks(this.g);
    ViewCompat.postOnAnimation(this.a.getViewGroup(), this.g);
    return true;
  }
  
  public boolean f()
  {
    if (this.a.hasExpandedActionView())
    {
      this.a.collapseActionView();
      return true;
    }
    return false;
  }
  
  void g()
  {
    this.a.getViewGroup().removeCallbacks(this.g);
  }
  
  public Window.Callback h()
  {
    return this.c;
  }
  
  void i()
  {
    Menu localMenu = j();
    h localh;
    if ((localMenu instanceof h)) {
      localh = (h)localMenu;
    } else {
      localh = null;
    }
    if (localh != null) {
      localh.g();
    }
    try
    {
      localMenu.clear();
      if ((!this.c.onCreatePanelMenu(0, localMenu)) || (!this.c.onPreparePanel(0, null, localMenu))) {
        localMenu.clear();
      }
      return;
    }
    finally
    {
      if (localh != null) {
        localh.h();
      }
    }
  }
  
  private final class a
    implements o.a
  {
    private boolean b;
    
    a() {}
    
    public void a(h paramh, boolean paramBoolean)
    {
      if (this.b) {
        return;
      }
      this.b = true;
      q.this.a.dismissPopupMenus();
      if (q.this.c != null) {
        q.this.c.onPanelClosed(108, paramh);
      }
      this.b = false;
    }
    
    public boolean a(h paramh)
    {
      if (q.this.c != null)
      {
        q.this.c.onMenuOpened(108, paramh);
        return true;
      }
      return false;
    }
  }
  
  private final class b
    implements h.a
  {
    b() {}
    
    public void a(h paramh)
    {
      if (q.this.c != null) {
        if (q.this.a.isOverflowMenuShowing()) {
          q.this.c.onPanelClosed(108, paramh);
        } else if (q.this.c.onPreparePanel(0, null, paramh)) {
          q.this.c.onMenuOpened(108, paramh);
        }
      }
    }
    
    public boolean a(h paramh, MenuItem paramMenuItem)
    {
      return false;
    }
  }
  
  private class c
    extends i
  {
    public c(Window.Callback paramCallback)
    {
      super();
    }
    
    public View onCreatePanelView(int paramInt)
    {
      if (paramInt == 0) {
        return new View(q.this.a.getContext());
      }
      return super.onCreatePanelView(paramInt);
    }
    
    public boolean onPreparePanel(int paramInt, View paramView, Menu paramMenu)
    {
      boolean bool = super.onPreparePanel(paramInt, paramView, paramMenu);
      if ((bool) && (!q.this.b))
      {
        q.this.a.setMenuPrepared();
        q.this.b = true;
      }
      return bool;
    }
  }
}


/* Location:              ~/android/support/v7/app/q.class
 *
 * Reversed by:           J
 */