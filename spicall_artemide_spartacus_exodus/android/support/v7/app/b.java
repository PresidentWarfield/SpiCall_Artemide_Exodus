package android.support.v7.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class b
  implements DrawerLayout.DrawerListener
{
  boolean a = true;
  View.OnClickListener b;
  private final a c;
  private final DrawerLayout d;
  private android.support.v7.d.a.b e;
  private boolean f = true;
  private Drawable g;
  private final int h;
  private final int i;
  private boolean j = false;
  
  public b(Activity paramActivity, DrawerLayout paramDrawerLayout, Toolbar paramToolbar, int paramInt1, int paramInt2)
  {
    this(paramActivity, paramToolbar, paramDrawerLayout, null, paramInt1, paramInt2);
  }
  
  b(Activity paramActivity, Toolbar paramToolbar, DrawerLayout paramDrawerLayout, android.support.v7.d.a.b paramb, int paramInt1, int paramInt2)
  {
    if (paramToolbar != null)
    {
      this.c = new g(paramToolbar);
      paramToolbar.setNavigationOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if (b.this.a) {
            b.this.b();
          } else if (b.this.b != null) {
            b.this.b.onClick(paramAnonymousView);
          }
        }
      });
    }
    else if ((paramActivity instanceof b))
    {
      this.c = ((b)paramActivity).a();
    }
    else if (Build.VERSION.SDK_INT >= 18)
    {
      this.c = new f(paramActivity);
    }
    else if (Build.VERSION.SDK_INT >= 14)
    {
      this.c = new e(paramActivity);
    }
    else if (Build.VERSION.SDK_INT >= 11)
    {
      this.c = new d(paramActivity);
    }
    else
    {
      this.c = new c(paramActivity);
    }
    this.d = paramDrawerLayout;
    this.h = paramInt1;
    this.i = paramInt2;
    if (paramb == null) {
      this.e = new android.support.v7.d.a.b(this.c.b());
    } else {
      this.e = paramb;
    }
    this.g = c();
  }
  
  private void a(float paramFloat)
  {
    if (paramFloat == 1.0F) {
      this.e.b(true);
    } else if (paramFloat == 0.0F) {
      this.e.b(false);
    }
    this.e.c(paramFloat);
  }
  
  public void a()
  {
    if (this.d.isDrawerOpen(8388611)) {
      a(1.0F);
    } else {
      a(0.0F);
    }
    if (this.a)
    {
      android.support.v7.d.a.b localb = this.e;
      int k;
      if (this.d.isDrawerOpen(8388611)) {
        k = this.i;
      } else {
        k = this.h;
      }
      a(localb, k);
    }
  }
  
  void a(int paramInt)
  {
    this.c.a(paramInt);
  }
  
  void a(Drawable paramDrawable, int paramInt)
  {
    if ((!this.j) && (!this.c.c()))
    {
      Log.w("ActionBarDrawerToggle", "DrawerToggle may not show up because NavigationIcon is not visible. You may need to call actionbar.setDisplayHomeAsUpEnabled(true);");
      this.j = true;
    }
    this.c.a(paramDrawable, paramInt);
  }
  
  void b()
  {
    int k = this.d.getDrawerLockMode(8388611);
    if ((this.d.isDrawerVisible(8388611)) && (k != 2)) {
      this.d.closeDrawer(8388611);
    } else if (k != 1) {
      this.d.openDrawer(8388611);
    }
  }
  
  Drawable c()
  {
    return this.c.a();
  }
  
  public void onDrawerClosed(View paramView)
  {
    a(0.0F);
    if (this.a) {
      a(this.h);
    }
  }
  
  public void onDrawerOpened(View paramView)
  {
    a(1.0F);
    if (this.a) {
      a(this.i);
    }
  }
  
  public void onDrawerSlide(View paramView, float paramFloat)
  {
    if (this.f) {
      a(Math.min(1.0F, Math.max(0.0F, paramFloat)));
    } else {
      a(0.0F);
    }
  }
  
  public void onDrawerStateChanged(int paramInt) {}
  
  public static abstract interface a
  {
    public abstract Drawable a();
    
    public abstract void a(int paramInt);
    
    public abstract void a(Drawable paramDrawable, int paramInt);
    
    public abstract Context b();
    
    public abstract boolean c();
  }
  
  public static abstract interface b
  {
    public abstract b.a a();
  }
  
  static class c
    implements b.a
  {
    final Activity a;
    
    c(Activity paramActivity)
    {
      this.a = paramActivity;
    }
    
    public Drawable a()
    {
      return null;
    }
    
    public void a(int paramInt) {}
    
    public void a(Drawable paramDrawable, int paramInt) {}
    
    public Context b()
    {
      return this.a;
    }
    
    public boolean c()
    {
      return true;
    }
  }
  
  private static class d
    implements b.a
  {
    final Activity a;
    c.a b;
    
    d(Activity paramActivity)
    {
      this.a = paramActivity;
    }
    
    public Drawable a()
    {
      return c.a(this.a);
    }
    
    public void a(int paramInt)
    {
      this.b = c.a(this.b, this.a, paramInt);
    }
    
    public void a(Drawable paramDrawable, int paramInt)
    {
      ActionBar localActionBar = this.a.getActionBar();
      if (localActionBar != null)
      {
        localActionBar.setDisplayShowHomeEnabled(true);
        this.b = c.a(this.b, this.a, paramDrawable, paramInt);
        localActionBar.setDisplayShowHomeEnabled(false);
      }
    }
    
    public Context b()
    {
      return this.a;
    }
    
    public boolean c()
    {
      ActionBar localActionBar = this.a.getActionBar();
      boolean bool;
      if ((localActionBar != null) && ((localActionBar.getDisplayOptions() & 0x4) != 0)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  private static class e
    extends b.d
  {
    e(Activity paramActivity)
    {
      super();
    }
    
    public Context b()
    {
      Object localObject = this.a.getActionBar();
      if (localObject != null) {
        localObject = ((ActionBar)localObject).getThemedContext();
      } else {
        localObject = this.a;
      }
      return (Context)localObject;
    }
  }
  
  private static class f
    implements b.a
  {
    final Activity a;
    
    f(Activity paramActivity)
    {
      this.a = paramActivity;
    }
    
    public Drawable a()
    {
      TypedArray localTypedArray = b().obtainStyledAttributes(null, new int[] { 16843531 }, 16843470, 0);
      Drawable localDrawable = localTypedArray.getDrawable(0);
      localTypedArray.recycle();
      return localDrawable;
    }
    
    public void a(int paramInt)
    {
      ActionBar localActionBar = this.a.getActionBar();
      if (localActionBar != null) {
        localActionBar.setHomeActionContentDescription(paramInt);
      }
    }
    
    public void a(Drawable paramDrawable, int paramInt)
    {
      ActionBar localActionBar = this.a.getActionBar();
      if (localActionBar != null)
      {
        localActionBar.setHomeAsUpIndicator(paramDrawable);
        localActionBar.setHomeActionContentDescription(paramInt);
      }
    }
    
    public Context b()
    {
      Object localObject = this.a.getActionBar();
      if (localObject != null) {
        localObject = ((ActionBar)localObject).getThemedContext();
      } else {
        localObject = this.a;
      }
      return (Context)localObject;
    }
    
    public boolean c()
    {
      ActionBar localActionBar = this.a.getActionBar();
      boolean bool;
      if ((localActionBar != null) && ((localActionBar.getDisplayOptions() & 0x4) != 0)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  static class g
    implements b.a
  {
    final Toolbar a;
    final Drawable b;
    final CharSequence c;
    
    g(Toolbar paramToolbar)
    {
      this.a = paramToolbar;
      this.b = paramToolbar.getNavigationIcon();
      this.c = paramToolbar.getNavigationContentDescription();
    }
    
    public Drawable a()
    {
      return this.b;
    }
    
    public void a(int paramInt)
    {
      if (paramInt == 0) {
        this.a.setNavigationContentDescription(this.c);
      } else {
        this.a.setNavigationContentDescription(paramInt);
      }
    }
    
    public void a(Drawable paramDrawable, int paramInt)
    {
      this.a.setNavigationIcon(paramDrawable);
      a(paramInt);
    }
    
    public Context b()
    {
      return this.a.getContext();
    }
    
    public boolean c()
    {
      return true;
    }
  }
}


/* Location:              ~/android/support/v7/app/b.class
 *
 * Reversed by:           J
 */