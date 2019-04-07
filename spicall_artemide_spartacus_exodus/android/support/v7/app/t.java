package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.a.a.a;
import android.support.v7.a.a.f;
import android.support.v7.a.a.j;
import android.support.v7.view.b;
import android.support.v7.view.b.a;
import android.support.v7.view.g;
import android.support.v7.view.menu.h.a;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class t
  extends a
  implements ActionBarOverlayLayout.ActionBarVisibilityCallback
{
  private static final Interpolator t = new AccelerateInterpolator();
  private static final Interpolator u = new DecelerateInterpolator();
  private boolean A;
  private boolean B;
  private ArrayList<a.b> C = new ArrayList();
  private boolean D;
  private int E = 0;
  private boolean F;
  private boolean G = true;
  private boolean H;
  Context a;
  ActionBarOverlayLayout b;
  ActionBarContainer c;
  DecorToolbar d;
  ActionBarContextView e;
  View f;
  ScrollingTabContainerView g;
  a h;
  b i;
  b.a j;
  boolean k = true;
  boolean l;
  boolean m;
  android.support.v7.view.h n;
  boolean o;
  final ViewPropertyAnimatorListener p = new ViewPropertyAnimatorListenerAdapter()
  {
    public void onAnimationEnd(View paramAnonymousView)
    {
      if ((t.this.k) && (t.this.f != null))
      {
        t.this.f.setTranslationY(0.0F);
        t.this.c.setTranslationY(0.0F);
      }
      t.this.c.setVisibility(8);
      t.this.c.setTransitioning(false);
      paramAnonymousView = t.this;
      paramAnonymousView.n = null;
      paramAnonymousView.h();
      if (t.this.b != null) {
        ViewCompat.requestApplyInsets(t.this.b);
      }
    }
  };
  final ViewPropertyAnimatorListener q = new ViewPropertyAnimatorListenerAdapter()
  {
    public void onAnimationEnd(View paramAnonymousView)
    {
      paramAnonymousView = t.this;
      paramAnonymousView.n = null;
      paramAnonymousView.c.requestLayout();
    }
  };
  final ViewPropertyAnimatorUpdateListener r = new ViewPropertyAnimatorUpdateListener()
  {
    public void onAnimationUpdate(View paramAnonymousView)
    {
      ((View)t.this.c.getParent()).invalidate();
    }
  };
  private Context v;
  private Activity w;
  private Dialog x;
  private ArrayList<Object> y = new ArrayList();
  private int z = -1;
  
  public t(Activity paramActivity, boolean paramBoolean)
  {
    this.w = paramActivity;
    paramActivity = paramActivity.getWindow().getDecorView();
    a(paramActivity);
    if (!paramBoolean) {
      this.f = paramActivity.findViewById(16908290);
    }
  }
  
  public t(Dialog paramDialog)
  {
    this.x = paramDialog;
    a(paramDialog.getWindow().getDecorView());
  }
  
  private void a(View paramView)
  {
    this.b = ((ActionBarOverlayLayout)paramView.findViewById(a.f.decor_content_parent));
    ActionBarOverlayLayout localActionBarOverlayLayout = this.b;
    if (localActionBarOverlayLayout != null) {
      localActionBarOverlayLayout.setActionBarVisibilityCallback(this);
    }
    this.d = b(paramView.findViewById(a.f.action_bar));
    this.e = ((ActionBarContextView)paramView.findViewById(a.f.action_context_bar));
    this.c = ((ActionBarContainer)paramView.findViewById(a.f.action_bar_container));
    paramView = this.d;
    if ((paramView != null) && (this.e != null) && (this.c != null))
    {
      this.a = paramView.getContext();
      if ((this.d.getDisplayOptions() & 0x4) != 0) {
        i1 = 1;
      } else {
        i1 = 0;
      }
      if (i1 != 0) {
        this.A = true;
      }
      paramView = android.support.v7.view.a.a(this.a);
      boolean bool;
      if ((!paramView.f()) && (i1 == 0)) {
        bool = false;
      } else {
        bool = true;
      }
      a(bool);
      j(paramView.d());
      paramView = this.a.obtainStyledAttributes(null, a.j.ActionBar, a.a.actionBarStyle, 0);
      if (paramView.getBoolean(a.j.ActionBar_hideOnContentScroll, false)) {
        b(true);
      }
      int i1 = paramView.getDimensionPixelSize(a.j.ActionBar_elevation, 0);
      if (i1 != 0) {
        a(i1);
      }
      paramView.recycle();
      return;
    }
    paramView = new StringBuilder();
    paramView.append(getClass().getSimpleName());
    paramView.append(" can only be used ");
    paramView.append("with a compatible window decor layout");
    throw new IllegalStateException(paramView.toString());
  }
  
  static boolean a(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramBoolean3) {
      return true;
    }
    return (!paramBoolean1) && (!paramBoolean2);
  }
  
  private DecorToolbar b(View paramView)
  {
    if ((paramView instanceof DecorToolbar)) {
      return (DecorToolbar)paramView;
    }
    if ((paramView instanceof Toolbar)) {
      return ((Toolbar)paramView).getWrapper();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Can't make a decor toolbar out of ");
    localStringBuilder.append(paramView);
    if (localStringBuilder.toString() != null) {
      paramView = paramView.getClass().getSimpleName();
    } else {
      paramView = "null";
    }
    throw new IllegalStateException(paramView);
  }
  
  private void j()
  {
    if (!this.F)
    {
      this.F = true;
      ActionBarOverlayLayout localActionBarOverlayLayout = this.b;
      if (localActionBarOverlayLayout != null) {
        localActionBarOverlayLayout.setShowingForActionMode(true);
      }
      k(false);
    }
  }
  
  private void j(boolean paramBoolean)
  {
    this.D = paramBoolean;
    if (!this.D)
    {
      this.d.setEmbeddedTabView(null);
      this.c.setTabContainer(this.g);
    }
    else
    {
      this.c.setTabContainer(null);
      this.d.setEmbeddedTabView(this.g);
    }
    int i1 = i();
    boolean bool = true;
    if (i1 == 2) {
      i1 = 1;
    } else {
      i1 = 0;
    }
    Object localObject = this.g;
    if (localObject != null) {
      if (i1 != 0)
      {
        ((ScrollingTabContainerView)localObject).setVisibility(0);
        localObject = this.b;
        if (localObject != null) {
          ViewCompat.requestApplyInsets((View)localObject);
        }
      }
      else
      {
        ((ScrollingTabContainerView)localObject).setVisibility(8);
      }
    }
    localObject = this.d;
    if ((!this.D) && (i1 != 0)) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    }
    ((DecorToolbar)localObject).setCollapsible(paramBoolean);
    localObject = this.b;
    if ((!this.D) && (i1 != 0)) {
      paramBoolean = bool;
    } else {
      paramBoolean = false;
    }
    ((ActionBarOverlayLayout)localObject).setHasNonEmbeddedTabs(paramBoolean);
  }
  
  private void k()
  {
    if (this.F)
    {
      this.F = false;
      ActionBarOverlayLayout localActionBarOverlayLayout = this.b;
      if (localActionBarOverlayLayout != null) {
        localActionBarOverlayLayout.setShowingForActionMode(false);
      }
      k(false);
    }
  }
  
  private void k(boolean paramBoolean)
  {
    if (a(this.l, this.m, this.F))
    {
      if (!this.G)
      {
        this.G = true;
        g(paramBoolean);
      }
    }
    else if (this.G)
    {
      this.G = false;
      h(paramBoolean);
    }
  }
  
  private boolean l()
  {
    return ViewCompat.isLaidOut(this.c);
  }
  
  public int a()
  {
    return this.d.getDisplayOptions();
  }
  
  public b a(b.a parama)
  {
    a locala = this.h;
    if (locala != null) {
      locala.c();
    }
    this.b.setHideOnContentScrollEnabled(false);
    this.e.killMode();
    parama = new a(this.e.getContext(), parama);
    if (parama.e())
    {
      this.h = parama;
      parama.d();
      this.e.initForMode(parama);
      i(true);
      this.e.sendAccessibilityEvent(32);
      return parama;
    }
    return null;
  }
  
  public void a(float paramFloat)
  {
    ViewCompat.setElevation(this.c, paramFloat);
  }
  
  public void a(int paramInt)
  {
    this.d.setNavigationContentDescription(paramInt);
  }
  
  public void a(int paramInt1, int paramInt2)
  {
    int i1 = this.d.getDisplayOptions();
    if ((paramInt2 & 0x4) != 0) {
      this.A = true;
    }
    this.d.setDisplayOptions(paramInt1 & paramInt2 | (paramInt2 ^ 0xFFFFFFFF) & i1);
  }
  
  public void a(Configuration paramConfiguration)
  {
    j(android.support.v7.view.a.a(this.a).d());
  }
  
  public void a(Drawable paramDrawable)
  {
    this.d.setNavigationIcon(paramDrawable);
  }
  
  public void a(CharSequence paramCharSequence)
  {
    this.d.setWindowTitle(paramCharSequence);
  }
  
  public void a(boolean paramBoolean)
  {
    this.d.setHomeButtonEnabled(paramBoolean);
  }
  
  public boolean a(int paramInt, KeyEvent paramKeyEvent)
  {
    Object localObject = this.h;
    if (localObject == null) {
      return false;
    }
    localObject = ((a)localObject).b();
    if (localObject != null)
    {
      if (paramKeyEvent != null) {
        i1 = paramKeyEvent.getDeviceId();
      } else {
        i1 = -1;
      }
      int i1 = KeyCharacterMap.load(i1).getKeyboardType();
      boolean bool = true;
      if (i1 == 1) {
        bool = false;
      }
      ((Menu)localObject).setQwertyMode(bool);
      return ((Menu)localObject).performShortcut(paramInt, paramKeyEvent, 0);
    }
    return false;
  }
  
  public Context b()
  {
    if (this.v == null)
    {
      TypedValue localTypedValue = new TypedValue();
      this.a.getTheme().resolveAttribute(a.a.actionBarWidgetTheme, localTypedValue, true);
      int i1 = localTypedValue.resourceId;
      if (i1 != 0) {
        this.v = new ContextThemeWrapper(this.a, i1);
      } else {
        this.v = this.a;
      }
    }
    return this.v;
  }
  
  public void b(boolean paramBoolean)
  {
    if ((paramBoolean) && (!this.b.isInOverlayMode())) {
      throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
    }
    this.o = paramBoolean;
    this.b.setHideOnContentScrollEnabled(paramBoolean);
  }
  
  public void c(boolean paramBoolean)
  {
    if (!this.A) {
      f(paramBoolean);
    }
  }
  
  public void d(boolean paramBoolean)
  {
    this.H = paramBoolean;
    if (!paramBoolean)
    {
      android.support.v7.view.h localh = this.n;
      if (localh != null) {
        localh.c();
      }
    }
  }
  
  public void e(boolean paramBoolean)
  {
    if (paramBoolean == this.B) {
      return;
    }
    this.B = paramBoolean;
    int i1 = this.C.size();
    for (int i2 = 0; i2 < i1; i2++) {
      ((a.b)this.C.get(i2)).a(paramBoolean);
    }
  }
  
  public void enableContentAnimations(boolean paramBoolean)
  {
    this.k = paramBoolean;
  }
  
  public void f(boolean paramBoolean)
  {
    int i1;
    if (paramBoolean) {
      i1 = 4;
    } else {
      i1 = 0;
    }
    a(i1, 4);
  }
  
  public boolean f()
  {
    DecorToolbar localDecorToolbar = this.d;
    if ((localDecorToolbar != null) && (localDecorToolbar.hasExpandedActionView()))
    {
      this.d.collapseActionView();
      return true;
    }
    return false;
  }
  
  public void g(boolean paramBoolean)
  {
    Object localObject1 = this.n;
    if (localObject1 != null) {
      ((android.support.v7.view.h)localObject1).c();
    }
    this.c.setVisibility(0);
    if ((this.E == 0) && ((this.H) || (paramBoolean)))
    {
      this.c.setTranslationY(0.0F);
      float f1 = -this.c.getHeight();
      float f2 = f1;
      if (paramBoolean)
      {
        localObject1 = new int[2];
        Object tmp69_68 = localObject1;
        tmp69_68[0] = 0;
        Object tmp73_69 = tmp69_68;
        tmp73_69[1] = 0;
        tmp73_69;
        this.c.getLocationInWindow((int[])localObject1);
        f2 = f1 - localObject1[1];
      }
      this.c.setTranslationY(f2);
      localObject1 = new android.support.v7.view.h();
      Object localObject2 = ViewCompat.animate(this.c).translationY(0.0F);
      ((ViewPropertyAnimatorCompat)localObject2).setUpdateListener(this.r);
      ((android.support.v7.view.h)localObject1).a((ViewPropertyAnimatorCompat)localObject2);
      if (this.k)
      {
        localObject2 = this.f;
        if (localObject2 != null)
        {
          ((View)localObject2).setTranslationY(f2);
          ((android.support.v7.view.h)localObject1).a(ViewCompat.animate(this.f).translationY(0.0F));
        }
      }
      ((android.support.v7.view.h)localObject1).a(u);
      ((android.support.v7.view.h)localObject1).a(250L);
      ((android.support.v7.view.h)localObject1).a(this.q);
      this.n = ((android.support.v7.view.h)localObject1);
      ((android.support.v7.view.h)localObject1).a();
    }
    else
    {
      this.c.setAlpha(1.0F);
      this.c.setTranslationY(0.0F);
      if (this.k)
      {
        localObject1 = this.f;
        if (localObject1 != null) {
          ((View)localObject1).setTranslationY(0.0F);
        }
      }
      this.q.onAnimationEnd(null);
    }
    localObject1 = this.b;
    if (localObject1 != null) {
      ViewCompat.requestApplyInsets((View)localObject1);
    }
  }
  
  void h()
  {
    b.a locala = this.j;
    if (locala != null)
    {
      locala.a(this.i);
      this.i = null;
      this.j = null;
    }
  }
  
  public void h(boolean paramBoolean)
  {
    android.support.v7.view.h localh = this.n;
    if (localh != null) {
      localh.c();
    }
    if ((this.E == 0) && ((this.H) || (paramBoolean)))
    {
      this.c.setAlpha(1.0F);
      this.c.setTransitioning(true);
      localh = new android.support.v7.view.h();
      float f1 = -this.c.getHeight();
      float f2 = f1;
      if (paramBoolean)
      {
        localObject = new int[2];
        Object tmp79_77 = localObject;
        tmp79_77[0] = 0;
        Object tmp83_79 = tmp79_77;
        tmp83_79[1] = 0;
        tmp83_79;
        this.c.getLocationInWindow((int[])localObject);
        f2 = f1 - localObject[1];
      }
      Object localObject = ViewCompat.animate(this.c).translationY(f2);
      ((ViewPropertyAnimatorCompat)localObject).setUpdateListener(this.r);
      localh.a((ViewPropertyAnimatorCompat)localObject);
      if (this.k)
      {
        localObject = this.f;
        if (localObject != null) {
          localh.a(ViewCompat.animate((View)localObject).translationY(f2));
        }
      }
      localh.a(t);
      localh.a(250L);
      localh.a(this.p);
      this.n = localh;
      localh.a();
    }
    else
    {
      this.p.onAnimationEnd(null);
    }
  }
  
  public void hideForSystem()
  {
    if (!this.m)
    {
      this.m = true;
      k(true);
    }
  }
  
  public int i()
  {
    return this.d.getNavigationMode();
  }
  
  public void i(boolean paramBoolean)
  {
    if (paramBoolean) {
      j();
    } else {
      k();
    }
    if (l())
    {
      ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat1;
      ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat2;
      if (paramBoolean)
      {
        localViewPropertyAnimatorCompat1 = this.d.setupAnimatorToVisibility(4, 100L);
        localViewPropertyAnimatorCompat2 = this.e.setupAnimatorToVisibility(0, 200L);
      }
      else
      {
        localViewPropertyAnimatorCompat2 = this.d.setupAnimatorToVisibility(0, 200L);
        localViewPropertyAnimatorCompat1 = this.e.setupAnimatorToVisibility(8, 100L);
      }
      android.support.v7.view.h localh = new android.support.v7.view.h();
      localh.a(localViewPropertyAnimatorCompat1, localViewPropertyAnimatorCompat2);
      localh.a();
    }
    else if (paramBoolean)
    {
      this.d.setVisibility(4);
      this.e.setVisibility(0);
    }
    else
    {
      this.d.setVisibility(0);
      this.e.setVisibility(8);
    }
  }
  
  public void onContentScrollStarted()
  {
    android.support.v7.view.h localh = this.n;
    if (localh != null)
    {
      localh.c();
      this.n = null;
    }
  }
  
  public void onContentScrollStopped() {}
  
  public void onWindowVisibilityChanged(int paramInt)
  {
    this.E = paramInt;
  }
  
  public void showForSystem()
  {
    if (this.m)
    {
      this.m = false;
      k(true);
    }
  }
  
  public class a
    extends b
    implements h.a
  {
    private final Context b;
    private final android.support.v7.view.menu.h c;
    private b.a d;
    private WeakReference<View> e;
    
    public a(Context paramContext, b.a parama)
    {
      this.b = paramContext;
      this.d = parama;
      this.c = new android.support.v7.view.menu.h(paramContext).a(1);
      this.c.a(this);
    }
    
    public MenuInflater a()
    {
      return new g(this.b);
    }
    
    public void a(int paramInt)
    {
      b(t.this.a.getResources().getString(paramInt));
    }
    
    public void a(android.support.v7.view.menu.h paramh)
    {
      if (this.d == null) {
        return;
      }
      d();
      t.this.e.showOverflowMenu();
    }
    
    public void a(View paramView)
    {
      t.this.e.setCustomView(paramView);
      this.e = new WeakReference(paramView);
    }
    
    public void a(CharSequence paramCharSequence)
    {
      t.this.e.setSubtitle(paramCharSequence);
    }
    
    public void a(boolean paramBoolean)
    {
      super.a(paramBoolean);
      t.this.e.setTitleOptional(paramBoolean);
    }
    
    public boolean a(android.support.v7.view.menu.h paramh, MenuItem paramMenuItem)
    {
      paramh = this.d;
      if (paramh != null) {
        return paramh.a(this, paramMenuItem);
      }
      return false;
    }
    
    public Menu b()
    {
      return this.c;
    }
    
    public void b(int paramInt)
    {
      a(t.this.a.getResources().getString(paramInt));
    }
    
    public void b(CharSequence paramCharSequence)
    {
      t.this.e.setTitle(paramCharSequence);
    }
    
    public void c()
    {
      if (t.this.h != this) {
        return;
      }
      if (!t.a(t.this.l, t.this.m, false))
      {
        t localt = t.this;
        localt.i = this;
        localt.j = this.d;
      }
      else
      {
        this.d.a(this);
      }
      this.d = null;
      t.this.i(false);
      t.this.e.closeMode();
      t.this.d.getViewGroup().sendAccessibilityEvent(32);
      t.this.b.setHideOnContentScrollEnabled(t.this.o);
      t.this.h = null;
    }
    
    public void d()
    {
      if (t.this.h != this) {
        return;
      }
      this.c.g();
      try
      {
        this.d.b(this, this.c);
        return;
      }
      finally
      {
        this.c.h();
      }
    }
    
    public boolean e()
    {
      this.c.g();
      try
      {
        boolean bool = this.d.a(this, this.c);
        return bool;
      }
      finally
      {
        this.c.h();
      }
    }
    
    public CharSequence f()
    {
      return t.this.e.getTitle();
    }
    
    public CharSequence g()
    {
      return t.this.e.getSubtitle();
    }
    
    public boolean h()
    {
      return t.this.e.isTitleOptional();
    }
    
    public View i()
    {
      Object localObject = this.e;
      if (localObject != null) {
        localObject = (View)((WeakReference)localObject).get();
      } else {
        localObject = null;
      }
      return (View)localObject;
    }
  }
}


/* Location:              ~/android/support/v7/app/t.class
 *
 * Reversed by:           J
 */