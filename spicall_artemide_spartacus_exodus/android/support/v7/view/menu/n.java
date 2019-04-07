package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.a.a.d;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow.OnDismissListener;

public class n
{
  private final Context a;
  private final h b;
  private final boolean c;
  private final int d;
  private final int e;
  private View f;
  private int g = 8388611;
  private boolean h;
  private o.a i;
  private m j;
  private PopupWindow.OnDismissListener k;
  private final PopupWindow.OnDismissListener l = new PopupWindow.OnDismissListener()
  {
    public void onDismiss()
    {
      n.this.f();
    }
  };
  
  public n(Context paramContext, h paramh, View paramView, boolean paramBoolean, int paramInt)
  {
    this(paramContext, paramh, paramView, paramBoolean, paramInt, 0);
  }
  
  public n(Context paramContext, h paramh, View paramView, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    this.a = paramContext;
    this.b = paramh;
    this.f = paramView;
    this.c = paramBoolean;
    this.d = paramInt1;
    this.e = paramInt2;
  }
  
  private void a(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    m localm = c();
    localm.c(paramBoolean2);
    if (paramBoolean1)
    {
      int m = paramInt1;
      if ((GravityCompat.getAbsoluteGravity(this.g, ViewCompat.getLayoutDirection(this.f)) & 0x7) == 5) {
        m = paramInt1 + this.f.getWidth();
      }
      localm.b(m);
      localm.c(paramInt2);
      paramInt1 = (int)(this.a.getResources().getDisplayMetrics().density * 48.0F / 2.0F);
      localm.a(new Rect(m - paramInt1, paramInt2 - paramInt1, m + paramInt1, paramInt2 + paramInt1));
    }
    localm.show();
  }
  
  private m h()
  {
    Object localObject = ((WindowManager)this.a.getSystemService("window")).getDefaultDisplay();
    Point localPoint = new Point();
    if (Build.VERSION.SDK_INT >= 17) {
      ((Display)localObject).getRealSize(localPoint);
    } else {
      ((Display)localObject).getSize(localPoint);
    }
    int m;
    if (Math.min(localPoint.x, localPoint.y) >= this.a.getResources().getDimensionPixelSize(a.d.abc_cascading_menus_min_smallest_width)) {
      m = 1;
    } else {
      m = 0;
    }
    if (m != 0) {
      localObject = new e(this.a, this.f, this.d, this.e, this.c);
    } else {
      localObject = new t(this.a, this.b, this.f, this.d, this.e, this.c);
    }
    ((m)localObject).a(this.b);
    ((m)localObject).a(this.l);
    ((m)localObject).a(this.f);
    ((m)localObject).a(this.i);
    ((m)localObject).b(this.h);
    ((m)localObject).a(this.g);
    return (m)localObject;
  }
  
  public int a()
  {
    return this.g;
  }
  
  public void a(int paramInt)
  {
    this.g = paramInt;
  }
  
  public void a(o.a parama)
  {
    this.i = parama;
    m localm = this.j;
    if (localm != null) {
      localm.a(parama);
    }
  }
  
  public void a(View paramView)
  {
    this.f = paramView;
  }
  
  public void a(PopupWindow.OnDismissListener paramOnDismissListener)
  {
    this.k = paramOnDismissListener;
  }
  
  public void a(boolean paramBoolean)
  {
    this.h = paramBoolean;
    m localm = this.j;
    if (localm != null) {
      localm.b(paramBoolean);
    }
  }
  
  public boolean a(int paramInt1, int paramInt2)
  {
    if (g()) {
      return true;
    }
    if (this.f == null) {
      return false;
    }
    a(paramInt1, paramInt2, true, true);
    return true;
  }
  
  public void b()
  {
    if (d()) {
      return;
    }
    throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
  }
  
  public m c()
  {
    if (this.j == null) {
      this.j = h();
    }
    return this.j;
  }
  
  public boolean d()
  {
    if (g()) {
      return true;
    }
    if (this.f == null) {
      return false;
    }
    a(0, 0, false, false);
    return true;
  }
  
  public void e()
  {
    if (g()) {
      this.j.dismiss();
    }
  }
  
  protected void f()
  {
    this.j = null;
    PopupWindow.OnDismissListener localOnDismissListener = this.k;
    if (localOnDismissListener != null) {
      localOnDismissListener.onDismiss();
    }
  }
  
  public boolean g()
  {
    m localm = this.j;
    boolean bool;
    if ((localm != null) && (localm.isShowing())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:              ~/android/support/v7/view/menu/n.class
 *
 * Reversed by:           J
 */