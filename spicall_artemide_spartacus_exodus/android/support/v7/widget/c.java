package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.ActionProvider.SubUiVisibilityListener;
import android.support.v7.a.a.a;
import android.support.v7.a.a.g;
import android.support.v7.view.a;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.ActionMenuItemView.b;
import android.support.v7.view.menu.b;
import android.support.v7.view.menu.h;
import android.support.v7.view.menu.j;
import android.support.v7.view.menu.m;
import android.support.v7.view.menu.n;
import android.support.v7.view.menu.o.a;
import android.support.v7.view.menu.p;
import android.support.v7.view.menu.p.a;
import android.support.v7.view.menu.s;
import android.support.v7.view.menu.u;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import java.util.ArrayList;

class c
  extends b
  implements ActionProvider.SubUiVisibilityListener
{
  private b A;
  d g;
  e h;
  a i;
  c j;
  final f k = new f();
  int l;
  private Drawable m;
  private boolean n;
  private boolean o;
  private boolean p;
  private int q;
  private int r;
  private int s;
  private boolean t;
  private boolean u;
  private boolean v;
  private boolean w;
  private int x;
  private final SparseBooleanArray y = new SparseBooleanArray();
  private View z;
  
  public c(Context paramContext)
  {
    super(paramContext, a.g.abc_action_menu_layout, a.g.abc_action_menu_item_layout);
  }
  
  private View a(MenuItem paramMenuItem)
  {
    ViewGroup localViewGroup = (ViewGroup)this.f;
    if (localViewGroup == null) {
      return null;
    }
    int i1 = localViewGroup.getChildCount();
    for (int i2 = 0; i2 < i1; i2++)
    {
      View localView = localViewGroup.getChildAt(i2);
      if (((localView instanceof p.a)) && (((p.a)localView).getItemData() == paramMenuItem)) {
        return localView;
      }
    }
    return null;
  }
  
  public p a(ViewGroup paramViewGroup)
  {
    p localp = this.f;
    paramViewGroup = super.a(paramViewGroup);
    if (localp != paramViewGroup) {
      ((ActionMenuView)paramViewGroup).setPresenter(this);
    }
    return paramViewGroup;
  }
  
  public View a(j paramj, View paramView, ViewGroup paramViewGroup)
  {
    View localView = paramj.getActionView();
    if ((localView == null) || (paramj.m())) {
      localView = super.a(paramj, paramView, paramViewGroup);
    }
    int i1;
    if (paramj.isActionViewExpanded()) {
      i1 = 8;
    } else {
      i1 = 0;
    }
    localView.setVisibility(i1);
    paramView = (ActionMenuView)paramViewGroup;
    paramj = localView.getLayoutParams();
    if (!paramView.checkLayoutParams(paramj)) {
      localView.setLayoutParams(paramView.generateLayoutParams(paramj));
    }
    return localView;
  }
  
  public void a(Context paramContext, h paramh)
  {
    super.a(paramContext, paramh);
    paramh = paramContext.getResources();
    paramContext = a.a(paramContext);
    if (!this.p) {
      this.o = paramContext.b();
    }
    if (!this.v) {
      this.q = paramContext.c();
    }
    if (!this.t) {
      this.s = paramContext.a();
    }
    int i1 = this.q;
    if (this.o)
    {
      if (this.g == null)
      {
        this.g = new d(this.a);
        if (this.n)
        {
          this.g.setImageDrawable(this.m);
          this.m = null;
          this.n = false;
        }
        int i2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.g.measure(i2, i2);
      }
      i1 -= this.g.getMeasuredWidth();
    }
    else
    {
      this.g = null;
    }
    this.r = i1;
    this.x = ((int)(paramh.getDisplayMetrics().density * 56.0F));
    this.z = null;
  }
  
  public void a(Configuration paramConfiguration)
  {
    if (!this.t) {
      this.s = a.a(this.b).a();
    }
    if (this.c != null) {
      this.c.a(true);
    }
  }
  
  public void a(Drawable paramDrawable)
  {
    d locald = this.g;
    if (locald != null)
    {
      locald.setImageDrawable(paramDrawable);
    }
    else
    {
      this.n = true;
      this.m = paramDrawable;
    }
  }
  
  public void a(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof g)) {
      return;
    }
    paramParcelable = (g)paramParcelable;
    if (paramParcelable.a > 0)
    {
      paramParcelable = this.c.findItem(paramParcelable.a);
      if (paramParcelable != null) {
        a((u)paramParcelable.getSubMenu());
      }
    }
  }
  
  public void a(h paramh, boolean paramBoolean)
  {
    h();
    super.a(paramh, paramBoolean);
  }
  
  public void a(j paramj, p.a parama)
  {
    parama.a(paramj, 0);
    paramj = (ActionMenuView)this.f;
    parama = (ActionMenuItemView)parama;
    parama.setItemInvoker(paramj);
    if (this.A == null) {
      this.A = new b();
    }
    parama.setPopupCallback(this.A);
  }
  
  public void a(ActionMenuView paramActionMenuView)
  {
    this.f = paramActionMenuView;
    paramActionMenuView.initialize(this.c);
  }
  
  public void a(boolean paramBoolean)
  {
    super.a(paramBoolean);
    ((View)this.f).requestLayout();
    Object localObject = this.c;
    int i1 = 0;
    int i2;
    if (localObject != null)
    {
      localObject = this.c.k();
      i2 = ((ArrayList)localObject).size();
      for (i3 = 0; i3 < i2; i3++)
      {
        ActionProvider localActionProvider = ((j)((ArrayList)localObject).get(i3)).getSupportActionProvider();
        if (localActionProvider != null) {
          localActionProvider.setSubUiVisibilityListener(this);
        }
      }
    }
    if (this.c != null) {
      localObject = this.c.l();
    } else {
      localObject = null;
    }
    int i3 = i1;
    boolean bool;
    if (this.o)
    {
      i3 = i1;
      if (localObject != null)
      {
        i2 = ((ArrayList)localObject).size();
        if (i2 == 1)
        {
          bool = ((j)((ArrayList)localObject).get(0)).isActionViewExpanded() ^ true;
        }
        else
        {
          bool = i1;
          if (i2 > 0) {
            bool = true;
          }
        }
      }
    }
    if (bool)
    {
      if (this.g == null) {
        this.g = new d(this.a);
      }
      localObject = (ViewGroup)this.g.getParent();
      if (localObject != this.f)
      {
        if (localObject != null) {
          ((ViewGroup)localObject).removeView(this.g);
        }
        localObject = (ActionMenuView)this.f;
        ((ActionMenuView)localObject).addView(this.g, ((ActionMenuView)localObject).generateOverflowButtonLayoutParams());
      }
    }
    else
    {
      localObject = this.g;
      if ((localObject != null) && (((d)localObject).getParent() == this.f)) {
        ((ViewGroup)this.f).removeView(this.g);
      }
    }
    ((ActionMenuView)this.f).setOverflowReserved(this.o);
  }
  
  public boolean a()
  {
    Object localObject1 = this;
    ArrayList localArrayList;
    int i1;
    if (((c)localObject1).c != null)
    {
      localArrayList = ((c)localObject1).c.i();
      i1 = localArrayList.size();
    }
    else
    {
      localArrayList = null;
      i1 = 0;
    }
    int i2 = ((c)localObject1).s;
    int i3 = ((c)localObject1).r;
    int i4 = View.MeasureSpec.makeMeasureSpec(0, 0);
    ViewGroup localViewGroup = (ViewGroup)((c)localObject1).f;
    int i5 = 0;
    int i6 = 0;
    int i7 = 0;
    int i8 = 0;
    while (i5 < i1)
    {
      localObject2 = (j)localArrayList.get(i5);
      if (((j)localObject2).k()) {
        i7++;
      } else if (((j)localObject2).j()) {
        i8++;
      } else {
        i6 = 1;
      }
      i9 = i2;
      if (((c)localObject1).w)
      {
        i9 = i2;
        if (((j)localObject2).isActionViewExpanded()) {
          i9 = 0;
        }
      }
      i5++;
      i2 = i9;
    }
    i5 = i2;
    if (((c)localObject1).o) {
      if (i6 == 0)
      {
        i5 = i2;
        if (i8 + i7 <= i2) {}
      }
      else
      {
        i5 = i2 - 1;
      }
    }
    i8 = i5 - i7;
    Object localObject2 = ((c)localObject1).y;
    ((SparseBooleanArray)localObject2).clear();
    if (((c)localObject1).u)
    {
      i2 = ((c)localObject1).x;
      i7 = i3 / i2;
      i6 = i2 + i3 % i2 / i7;
    }
    else
    {
      i6 = 0;
      i7 = 0;
    }
    i5 = i3;
    i3 = 0;
    i2 = 0;
    int i9 = i1;
    for (;;)
    {
      localObject1 = this;
      if (i3 >= i9) {
        break;
      }
      j localj = (j)localArrayList.get(i3);
      View localView;
      if (localj.k())
      {
        localView = ((c)localObject1).a(localj, ((c)localObject1).z, localViewGroup);
        if (((c)localObject1).z == null) {
          ((c)localObject1).z = localView;
        }
        if (((c)localObject1).u) {
          i7 -= ActionMenuView.measureChildForCells(localView, i6, i7, i4, 0);
        } else {
          localView.measure(i4, i4);
        }
        i1 = localView.getMeasuredWidth();
        i5 -= i1;
        if (i2 == 0) {
          i2 = i1;
        }
        i1 = localj.getGroupId();
        if (i1 != 0) {
          ((SparseBooleanArray)localObject2).put(i1, true);
        }
        localj.d(true);
      }
      else if (localj.j())
      {
        int i10 = localj.getGroupId();
        boolean bool = ((SparseBooleanArray)localObject2).get(i10);
        int i11;
        if (((i8 > 0) || (bool)) && (i5 > 0) && ((!((c)localObject1).u) || (i7 > 0))) {
          i11 = 1;
        } else {
          i11 = 0;
        }
        int i12;
        if (i11 != 0)
        {
          localView = ((c)localObject1).a(localj, ((c)localObject1).z, localViewGroup);
          if (((c)localObject1).z == null) {
            ((c)localObject1).z = localView;
          }
          if (((c)localObject1).u)
          {
            i12 = ActionMenuView.measureChildForCells(localView, i6, i7, i4, 0);
            i1 = i7 - i12;
            i7 = i1;
            if (i12 == 0)
            {
              i11 = 0;
              i7 = i1;
            }
          }
          else
          {
            localView.measure(i4, i4);
          }
          i12 = localView.getMeasuredWidth();
          i5 -= i12;
          i1 = i2;
          if (i2 == 0) {
            i1 = i12;
          }
          if (((c)localObject1).u)
          {
            if (i5 >= 0) {
              i2 = 1;
            } else {
              i2 = 0;
            }
            i11 &= i2;
            i2 = i1;
          }
          else
          {
            if (i5 + i1 > 0) {
              i2 = 1;
            } else {
              i2 = 0;
            }
            i11 &= i2;
            i2 = i1;
          }
        }
        if ((i11 != 0) && (i10 != 0))
        {
          ((SparseBooleanArray)localObject2).put(i10, true);
          i1 = i8;
        }
        else
        {
          i1 = i8;
          if (bool)
          {
            ((SparseBooleanArray)localObject2).put(i10, false);
            i12 = 0;
            for (;;)
            {
              i1 = i8;
              if (i12 >= i3) {
                break;
              }
              localObject1 = (j)localArrayList.get(i12);
              i1 = i8;
              if (((j)localObject1).getGroupId() == i10)
              {
                i1 = i8;
                if (((j)localObject1).i()) {
                  i1 = i8 + 1;
                }
                ((j)localObject1).d(false);
              }
              i12++;
              i8 = i1;
            }
          }
        }
        i8 = i1;
        if (i11 != 0) {
          i8 = i1 - 1;
        }
        localj.d(i11);
      }
      else
      {
        localj.d(false);
      }
      i3++;
    }
    return true;
  }
  
  public boolean a(int paramInt, j paramj)
  {
    return paramj.i();
  }
  
  public boolean a(u paramu)
  {
    boolean bool1 = paramu.hasVisibleItems();
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    for (Object localObject = paramu; ((u)localObject).s() != this.c; localObject = (u)((u)localObject).s()) {}
    localObject = a(((u)localObject).getItem());
    if (localObject == null) {
      return false;
    }
    this.l = paramu.getItem().getItemId();
    int i1 = paramu.size();
    for (int i2 = 0;; i2++)
    {
      bool1 = bool2;
      if (i2 >= i1) {
        break;
      }
      MenuItem localMenuItem = paramu.getItem(i2);
      if ((localMenuItem.isVisible()) && (localMenuItem.getIcon() != null))
      {
        bool1 = true;
        break;
      }
    }
    this.i = new a(this.b, paramu, (View)localObject);
    this.i.a(bool1);
    this.i.b();
    super.a(paramu);
    return true;
  }
  
  public boolean a(ViewGroup paramViewGroup, int paramInt)
  {
    if (paramViewGroup.getChildAt(paramInt) == this.g) {
      return false;
    }
    return super.a(paramViewGroup, paramInt);
  }
  
  public void b(boolean paramBoolean)
  {
    this.o = paramBoolean;
    this.p = true;
  }
  
  public Parcelable c()
  {
    g localg = new g();
    localg.a = this.l;
    return localg;
  }
  
  public void c(boolean paramBoolean)
  {
    this.w = paramBoolean;
  }
  
  public Drawable e()
  {
    d locald = this.g;
    if (locald != null) {
      return locald.getDrawable();
    }
    if (this.n) {
      return this.m;
    }
    return null;
  }
  
  public boolean f()
  {
    if ((this.o) && (!j()) && (this.c != null) && (this.f != null) && (this.j == null) && (!this.c.l().isEmpty()))
    {
      this.j = new c(new e(this.b, this.c, this.g, true));
      ((View)this.f).post(this.j);
      super.a(null);
      return true;
    }
    return false;
  }
  
  public boolean g()
  {
    if ((this.j != null) && (this.f != null))
    {
      ((View)this.f).removeCallbacks(this.j);
      this.j = null;
      return true;
    }
    e locale = this.h;
    if (locale != null)
    {
      locale.e();
      return true;
    }
    return false;
  }
  
  public boolean h()
  {
    return g() | i();
  }
  
  public boolean i()
  {
    a locala = this.i;
    if (locala != null)
    {
      locala.e();
      return true;
    }
    return false;
  }
  
  public boolean j()
  {
    e locale = this.h;
    boolean bool;
    if ((locale != null) && (locale.g())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean k()
  {
    boolean bool;
    if ((this.j == null) && (!j())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean l()
  {
    return this.o;
  }
  
  public void onSubUiVisibilityChanged(boolean paramBoolean)
  {
    if (paramBoolean) {
      super.a(null);
    } else if (this.c != null) {
      this.c.b(false);
    }
  }
  
  private class a
    extends n
  {
    public a(Context paramContext, u paramu, View paramView)
    {
      super(paramu, paramView, false, a.a.actionOverflowMenuStyle);
      if (!((j)paramu.getItem()).i())
      {
        if (c.this.g == null) {
          paramContext = (View)c.c(c.this);
        } else {
          paramContext = c.this.g;
        }
        a(paramContext);
      }
      a(c.this.k);
    }
    
    protected void f()
    {
      c localc = c.this;
      localc.i = null;
      localc.l = 0;
      super.f();
    }
  }
  
  private class b
    extends ActionMenuItemView.b
  {
    b() {}
    
    public s a()
    {
      m localm;
      if (c.this.i != null) {
        localm = c.this.i.c();
      } else {
        localm = null;
      }
      return localm;
    }
  }
  
  private class c
    implements Runnable
  {
    private c.e b;
    
    public c(c.e parame)
    {
      this.b = parame;
    }
    
    public void run()
    {
      if (c.d(c.this) != null) {
        c.e(c.this).f();
      }
      View localView = (View)c.f(c.this);
      if ((localView != null) && (localView.getWindowToken() != null) && (this.b.d())) {
        c.this.h = this.b;
      }
      c.this.j = null;
    }
  }
  
  private class d
    extends AppCompatImageView
    implements ActionMenuView.ActionMenuChildView
  {
    private final float[] b = new float[2];
    
    public d(Context paramContext)
    {
      super(null, a.a.actionOverflowButtonStyle);
      setClickable(true);
      setFocusable(true);
      setVisibility(0);
      setEnabled(true);
      TooltipCompat.setTooltipText(this, getContentDescription());
      setOnTouchListener(new ForwardingListener(this)
      {
        public s getPopup()
        {
          if (c.this.h == null) {
            return null;
          }
          return c.this.h.c();
        }
        
        public boolean onForwardingStarted()
        {
          c.this.f();
          return true;
        }
        
        public boolean onForwardingStopped()
        {
          if (c.this.j != null) {
            return false;
          }
          c.this.g();
          return true;
        }
      });
    }
    
    public boolean needsDividerAfter()
    {
      return false;
    }
    
    public boolean needsDividerBefore()
    {
      return false;
    }
    
    public boolean performClick()
    {
      if (super.performClick()) {
        return true;
      }
      playSoundEffect(0);
      c.this.f();
      return true;
    }
    
    protected boolean setFrame(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      boolean bool = super.setFrame(paramInt1, paramInt2, paramInt3, paramInt4);
      Drawable localDrawable1 = getDrawable();
      Drawable localDrawable2 = getBackground();
      if ((localDrawable1 != null) && (localDrawable2 != null))
      {
        int i = getWidth();
        paramInt2 = getHeight();
        paramInt1 = Math.max(i, paramInt2) / 2;
        int j = getPaddingLeft();
        int k = getPaddingRight();
        paramInt4 = getPaddingTop();
        paramInt3 = getPaddingBottom();
        i = (i + (j - k)) / 2;
        paramInt2 = (paramInt2 + (paramInt4 - paramInt3)) / 2;
        DrawableCompat.setHotspotBounds(localDrawable2, i - paramInt1, paramInt2 - paramInt1, i + paramInt1, paramInt2 + paramInt1);
      }
      return bool;
    }
  }
  
  private class e
    extends n
  {
    public e(Context paramContext, h paramh, View paramView, boolean paramBoolean)
    {
      super(paramh, paramView, paramBoolean, a.a.actionOverflowMenuStyle);
      a(8388613);
      a(c.this.k);
    }
    
    protected void f()
    {
      if (c.a(c.this) != null) {
        c.b(c.this).close();
      }
      c.this.h = null;
      super.f();
    }
  }
  
  private class f
    implements o.a
  {
    f() {}
    
    public void a(h paramh, boolean paramBoolean)
    {
      if ((paramh instanceof u)) {
        paramh.p().b(false);
      }
      o.a locala = c.this.d();
      if (locala != null) {
        locala.a(paramh, paramBoolean);
      }
    }
    
    public boolean a(h paramh)
    {
      boolean bool = false;
      if (paramh == null) {
        return false;
      }
      c.this.l = ((u)paramh).getItem().getItemId();
      o.a locala = c.this.d();
      if (locala != null) {
        bool = locala.a(paramh);
      }
      return bool;
    }
  }
  
  private static class g
    implements Parcelable
  {
    public static final Parcelable.Creator<g> CREATOR = new Parcelable.Creator()
    {
      public c.g a(Parcel paramAnonymousParcel)
      {
        return new c.g(paramAnonymousParcel);
      }
      
      public c.g[] a(int paramAnonymousInt)
      {
        return new c.g[paramAnonymousInt];
      }
    };
    public int a;
    
    g() {}
    
    g(Parcel paramParcel)
    {
      this.a = paramParcel.readInt();
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.a);
    }
  }
}


/* Location:              ~/android/support/v7/widget/c.class
 *
 * Reversed by:           J
 */