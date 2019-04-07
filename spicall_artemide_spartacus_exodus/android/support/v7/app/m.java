package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.a.a.a;
import android.support.v7.a.a.c;
import android.support.v7.a.a.f;
import android.support.v7.a.a.g;
import android.support.v7.a.a.i;
import android.support.v7.a.a.j;
import android.support.v7.view.b.a;
import android.support.v7.view.d;
import android.support.v7.view.e;
import android.support.v7.view.menu.h.a;
import android.support.v7.view.menu.o.a;
import android.support.v7.view.menu.p;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.ContentFrameLayout.OnAttachListener;
import android.support.v7.widget.DecorContentParent;
import android.support.v7.widget.FitWindowsViewGroup;
import android.support.v7.widget.FitWindowsViewGroup.OnFitSystemWindowsListener;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.VectorEnabledTintResources;
import android.support.v7.widget.ViewStubCompat;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.LayoutInflater.Factory2;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.view.Window.Callback;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;

class m
  extends h
  implements h.a, LayoutInflater.Factory2
{
  private static final boolean t;
  private View A;
  private boolean B;
  private boolean C;
  private boolean D;
  private d[] E;
  private d F;
  private boolean G;
  private final Runnable H = new Runnable()
  {
    public void run()
    {
      if ((m.this.s & 0x1) != 0) {
        m.this.f(0);
      }
      if ((m.this.s & 0x1000) != 0) {
        m.this.f(108);
      }
      m localm = m.this;
      localm.r = false;
      localm.s = 0;
    }
  };
  private boolean I;
  private Rect J;
  private Rect K;
  private o L;
  android.support.v7.view.b m;
  ActionBarContextView n;
  PopupWindow o;
  Runnable p;
  ViewPropertyAnimatorCompat q = null;
  boolean r;
  int s;
  private DecorContentParent u;
  private a v;
  private e w;
  private boolean x;
  private ViewGroup y;
  private TextView z;
  
  static
  {
    boolean bool;
    if (Build.VERSION.SDK_INT < 21) {
      bool = true;
    } else {
      bool = false;
    }
    t = bool;
  }
  
  m(Context paramContext, Window paramWindow, f paramf)
  {
    super(paramContext, paramWindow, paramf);
  }
  
  private void A()
  {
    if (!this.x) {
      return;
    }
    throw new AndroidRuntimeException("Window feature must be requested before adding content");
  }
  
  private void a(d paramd, KeyEvent paramKeyEvent)
  {
    if ((!paramd.o) && (!q()))
    {
      if (paramd.a == 0)
      {
        localObject = this.a;
        if ((((Context)localObject).getResources().getConfiguration().screenLayout & 0xF) == 4) {
          i = 1;
        } else {
          i = 0;
        }
        int j;
        if (((Context)localObject).getApplicationInfo().targetSdkVersion >= 11) {
          j = 1;
        } else {
          j = 0;
        }
        if ((i != 0) && (j != 0)) {
          return;
        }
      }
      Object localObject = r();
      if ((localObject != null) && (!((Window.Callback)localObject).onMenuOpened(paramd.a, paramd.j)))
      {
        a(paramd, true);
        return;
      }
      WindowManager localWindowManager = (WindowManager)this.a.getSystemService("window");
      if (localWindowManager == null) {
        return;
      }
      if (!b(paramd, paramKeyEvent)) {
        return;
      }
      if ((paramd.g != null) && (!paramd.q))
      {
        if (paramd.i != null)
        {
          paramKeyEvent = paramd.i.getLayoutParams();
          if ((paramKeyEvent != null) && (paramKeyEvent.width == -1))
          {
            i = -1;
            break label370;
          }
        }
      }
      else
      {
        if (paramd.g == null)
        {
          if ((a(paramd)) && (paramd.g != null)) {}
        }
        else if ((paramd.q) && (paramd.g.getChildCount() > 0)) {
          paramd.g.removeAllViews();
        }
        if ((!c(paramd)) || (!paramd.a())) {
          break label436;
        }
        localObject = paramd.h.getLayoutParams();
        paramKeyEvent = (KeyEvent)localObject;
        if (localObject == null) {
          paramKeyEvent = new ViewGroup.LayoutParams(-2, -2);
        }
        i = paramd.b;
        paramd.g.setBackgroundResource(i);
        localObject = paramd.h.getParent();
        if ((localObject != null) && ((localObject instanceof ViewGroup))) {
          ((ViewGroup)localObject).removeView(paramd.h);
        }
        paramd.g.addView(paramd.h, paramKeyEvent);
        if (!paramd.h.hasFocus()) {
          paramd.h.requestFocus();
        }
      }
      int i = -2;
      label370:
      paramd.n = false;
      paramKeyEvent = new WindowManager.LayoutParams(i, -2, paramd.d, paramd.e, 1002, 8519680, -3);
      paramKeyEvent.gravity = paramd.c;
      paramKeyEvent.windowAnimations = paramd.f;
      localWindowManager.addView(paramd.g, paramKeyEvent);
      paramd.o = true;
      return;
      label436:
      return;
    }
  }
  
  private void a(android.support.v7.view.menu.h paramh, boolean paramBoolean)
  {
    paramh = this.u;
    if ((paramh != null) && (paramh.canShowOverflowMenu()) && ((!ViewConfiguration.get(this.a).hasPermanentMenuKey()) || (this.u.isOverflowMenuShowPending())))
    {
      paramh = r();
      if ((this.u.isOverflowMenuShowing()) && (paramBoolean))
      {
        this.u.hideOverflowMenu();
        if (!q()) {
          paramh.onPanelClosed(108, a(0, true).j);
        }
      }
      else if ((paramh != null) && (!q()))
      {
        if ((this.r) && ((this.s & 0x1) != 0))
        {
          this.b.getDecorView().removeCallbacks(this.H);
          this.H.run();
        }
        d locald = a(0, true);
        if ((locald.j != null) && (!locald.r) && (paramh.onPreparePanel(0, locald.i, locald.j)))
        {
          paramh.onMenuOpened(108, locald.j);
          this.u.showOverflowMenu();
        }
      }
      return;
    }
    paramh = a(0, true);
    paramh.q = true;
    a(paramh, false);
    a(paramh, null);
  }
  
  private boolean a(d paramd)
  {
    paramd.a(o());
    paramd.g = new c(paramd.l);
    paramd.c = 81;
    return true;
  }
  
  private boolean a(d paramd, int paramInt1, KeyEvent paramKeyEvent, int paramInt2)
  {
    boolean bool1 = paramKeyEvent.isSystem();
    boolean bool2 = false;
    if (bool1) {
      return false;
    }
    if (!paramd.m)
    {
      bool1 = bool2;
      if (!b(paramd, paramKeyEvent)) {}
    }
    else
    {
      bool1 = bool2;
      if (paramd.j != null) {
        bool1 = paramd.j.performShortcut(paramInt1, paramKeyEvent, paramInt2);
      }
    }
    if ((bool1) && ((paramInt2 & 0x1) == 0) && (this.u == null)) {
      a(paramd, true);
    }
    return bool1;
  }
  
  private boolean a(ViewParent paramViewParent)
  {
    if (paramViewParent == null) {
      return false;
    }
    View localView = this.b.getDecorView();
    for (;;)
    {
      if (paramViewParent == null) {
        return true;
      }
      if ((paramViewParent == localView) || (!(paramViewParent instanceof View)) || (ViewCompat.isAttachedToWindow((View)paramViewParent))) {
        break;
      }
      paramViewParent = paramViewParent.getParent();
    }
    return false;
  }
  
  private boolean b(d paramd)
  {
    Context localContext = this.a;
    if (paramd.a != 0)
    {
      localObject1 = localContext;
      if (paramd.a != 108) {}
    }
    else
    {
      localObject1 = localContext;
      if (this.u != null)
      {
        TypedValue localTypedValue = new TypedValue();
        Resources.Theme localTheme = localContext.getTheme();
        localTheme.resolveAttribute(a.a.actionBarTheme, localTypedValue, true);
        localObject1 = null;
        if (localTypedValue.resourceId != 0)
        {
          localObject1 = localContext.getResources().newTheme();
          ((Resources.Theme)localObject1).setTo(localTheme);
          ((Resources.Theme)localObject1).applyStyle(localTypedValue.resourceId, true);
          ((Resources.Theme)localObject1).resolveAttribute(a.a.actionBarWidgetTheme, localTypedValue, true);
        }
        else
        {
          localTheme.resolveAttribute(a.a.actionBarWidgetTheme, localTypedValue, true);
        }
        Object localObject2 = localObject1;
        if (localTypedValue.resourceId != 0)
        {
          localObject2 = localObject1;
          if (localObject1 == null)
          {
            localObject2 = localContext.getResources().newTheme();
            ((Resources.Theme)localObject2).setTo(localTheme);
          }
          ((Resources.Theme)localObject2).applyStyle(localTypedValue.resourceId, true);
        }
        localObject1 = localContext;
        if (localObject2 != null)
        {
          localObject1 = new d(localContext, 0);
          ((Context)localObject1).getTheme().setTo((Resources.Theme)localObject2);
        }
      }
    }
    Object localObject1 = new android.support.v7.view.menu.h((Context)localObject1);
    ((android.support.v7.view.menu.h)localObject1).a(this);
    paramd.a((android.support.v7.view.menu.h)localObject1);
    return true;
  }
  
  private boolean b(d paramd, KeyEvent paramKeyEvent)
  {
    if (q()) {
      return false;
    }
    if (paramd.m) {
      return true;
    }
    Object localObject = this.F;
    if ((localObject != null) && (localObject != paramd)) {
      a((d)localObject, false);
    }
    Window.Callback localCallback = r();
    if (localCallback != null) {
      paramd.i = localCallback.onCreatePanelView(paramd.a);
    }
    int i;
    if ((paramd.a != 0) && (paramd.a != 108)) {
      i = 0;
    } else {
      i = 1;
    }
    if (i != 0)
    {
      localObject = this.u;
      if (localObject != null) {
        ((DecorContentParent)localObject).setMenuPrepared();
      }
    }
    if ((paramd.i == null) && ((i == 0) || (!(n() instanceof q))))
    {
      if ((paramd.j == null) || (paramd.r))
      {
        if ((paramd.j == null) && ((!b(paramd)) || (paramd.j == null))) {
          return false;
        }
        if ((i != 0) && (this.u != null))
        {
          if (this.v == null) {
            this.v = new a();
          }
          this.u.setMenu(paramd.j, this.v);
        }
        paramd.j.g();
        if (!localCallback.onCreatePanelMenu(paramd.a, paramd.j))
        {
          paramd.a(null);
          if (i != 0)
          {
            paramd = this.u;
            if (paramd != null) {
              paramd.setMenu(null, this.v);
            }
          }
          return false;
        }
        paramd.r = false;
      }
      paramd.j.g();
      if (paramd.s != null)
      {
        paramd.j.d(paramd.s);
        paramd.s = null;
      }
      if (!localCallback.onPreparePanel(0, paramd.i, paramd.j))
      {
        if (i != 0)
        {
          paramKeyEvent = this.u;
          if (paramKeyEvent != null) {
            paramKeyEvent.setMenu(null, this.v);
          }
        }
        paramd.j.h();
        return false;
      }
      if (paramKeyEvent != null) {
        i = paramKeyEvent.getDeviceId();
      } else {
        i = -1;
      }
      boolean bool;
      if (KeyCharacterMap.load(i).getKeyboardType() != 1) {
        bool = true;
      } else {
        bool = false;
      }
      paramd.p = bool;
      paramd.j.setQwertyMode(paramd.p);
      paramd.j.h();
    }
    paramd.m = true;
    paramd.n = false;
    this.F = paramd;
    return true;
  }
  
  private boolean c(d paramd)
  {
    View localView = paramd.i;
    boolean bool = true;
    if (localView != null)
    {
      paramd.h = paramd.i;
      return true;
    }
    if (paramd.j == null) {
      return false;
    }
    if (this.w == null) {
      this.w = new e();
    }
    paramd.h = ((View)paramd.a(this.w));
    if (paramd.h == null) {
      bool = false;
    }
    return bool;
  }
  
  private void d(int paramInt)
  {
    this.s = (1 << paramInt | this.s);
    if (!this.r)
    {
      ViewCompat.postOnAnimation(this.b.getDecorView(), this.H);
      this.r = true;
    }
  }
  
  private boolean d(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getRepeatCount() == 0)
    {
      d locald = a(paramInt, true);
      if (!locald.o) {
        return b(locald, paramKeyEvent);
      }
    }
    return false;
  }
  
  private boolean e(int paramInt, KeyEvent paramKeyEvent)
  {
    if (this.m != null) {
      return false;
    }
    d locald = a(paramInt, true);
    boolean bool;
    if (paramInt == 0)
    {
      DecorContentParent localDecorContentParent = this.u;
      if ((localDecorContentParent != null) && (localDecorContentParent.canShowOverflowMenu()) && (!ViewConfiguration.get(this.a).hasPermanentMenuKey()))
      {
        if (!this.u.isOverflowMenuShowing())
        {
          if ((q()) || (!b(locald, paramKeyEvent))) {
            break label177;
          }
          bool = this.u.showOverflowMenu();
          break label195;
        }
        bool = this.u.hideOverflowMenu();
        break label195;
      }
    }
    if ((!locald.o) && (!locald.n))
    {
      if (locald.m)
      {
        if (locald.r)
        {
          locald.m = false;
          bool = b(locald, paramKeyEvent);
        }
        else
        {
          bool = true;
        }
        if (bool)
        {
          a(locald, paramKeyEvent);
          bool = true;
          break label195;
        }
      }
      label177:
      bool = false;
    }
    else
    {
      bool = locald.o;
      a(locald, true);
    }
    label195:
    if (bool)
    {
      paramKeyEvent = (AudioManager)this.a.getSystemService("audio");
      if (paramKeyEvent != null) {
        paramKeyEvent.playSoundEffect(0);
      } else {
        Log.w("AppCompatDelegate", "Couldn't get audio manager");
      }
    }
    return bool;
  }
  
  private int h(int paramInt)
  {
    if (paramInt == 8)
    {
      Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
      return 108;
    }
    if (paramInt == 9)
    {
      Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
      return 109;
    }
    return paramInt;
  }
  
  private void x()
  {
    if (!this.x)
    {
      this.y = y();
      Object localObject = s();
      if (!TextUtils.isEmpty((CharSequence)localObject)) {
        b((CharSequence)localObject);
      }
      z();
      a(this.y);
      this.x = true;
      localObject = a(0, false);
      if ((!q()) && ((localObject == null) || (((d)localObject).j == null))) {
        d(108);
      }
    }
  }
  
  private ViewGroup y()
  {
    Object localObject1 = this.a.obtainStyledAttributes(a.j.AppCompatTheme);
    if (((TypedArray)localObject1).hasValue(a.j.AppCompatTheme_windowActionBar))
    {
      if (((TypedArray)localObject1).getBoolean(a.j.AppCompatTheme_windowNoTitle, false)) {
        c(1);
      } else if (((TypedArray)localObject1).getBoolean(a.j.AppCompatTheme_windowActionBar, false)) {
        c(108);
      }
      if (((TypedArray)localObject1).getBoolean(a.j.AppCompatTheme_windowActionBarOverlay, false)) {
        c(109);
      }
      if (((TypedArray)localObject1).getBoolean(a.j.AppCompatTheme_windowActionModeOverlay, false)) {
        c(10);
      }
      this.k = ((TypedArray)localObject1).getBoolean(a.j.AppCompatTheme_android_windowIsFloating, false);
      ((TypedArray)localObject1).recycle();
      this.b.getDecorView();
      localObject1 = LayoutInflater.from(this.a);
      Object localObject2;
      if (!this.l)
      {
        if (this.k)
        {
          localObject1 = (ViewGroup)((LayoutInflater)localObject1).inflate(a.g.abc_dialog_title_material, null);
          this.i = false;
          this.h = false;
        }
        else if (this.h)
        {
          localObject1 = new TypedValue();
          this.a.getTheme().resolveAttribute(a.a.actionBarTheme, (TypedValue)localObject1, true);
          if (((TypedValue)localObject1).resourceId != 0) {
            localObject1 = new d(this.a, ((TypedValue)localObject1).resourceId);
          } else {
            localObject1 = this.a;
          }
          localObject2 = (ViewGroup)LayoutInflater.from((Context)localObject1).inflate(a.g.abc_screen_toolbar, null);
          this.u = ((DecorContentParent)((ViewGroup)localObject2).findViewById(a.f.decor_content_parent));
          this.u.setWindowCallback(r());
          if (this.i) {
            this.u.initFeature(109);
          }
          if (this.B) {
            this.u.initFeature(2);
          }
          localObject1 = localObject2;
          if (this.C)
          {
            this.u.initFeature(5);
            localObject1 = localObject2;
          }
        }
        else
        {
          localObject1 = null;
        }
      }
      else
      {
        if (this.j) {
          localObject1 = (ViewGroup)((LayoutInflater)localObject1).inflate(a.g.abc_screen_simple_overlay_action_mode, null);
        } else {
          localObject1 = (ViewGroup)((LayoutInflater)localObject1).inflate(a.g.abc_screen_simple, null);
        }
        if (Build.VERSION.SDK_INT >= 21) {
          ViewCompat.setOnApplyWindowInsetsListener((View)localObject1, new OnApplyWindowInsetsListener()
          {
            public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
            {
              int i = paramAnonymousWindowInsetsCompat.getSystemWindowInsetTop();
              int j = m.this.g(i);
              WindowInsetsCompat localWindowInsetsCompat = paramAnonymousWindowInsetsCompat;
              if (i != j) {
                localWindowInsetsCompat = paramAnonymousWindowInsetsCompat.replaceSystemWindowInsets(paramAnonymousWindowInsetsCompat.getSystemWindowInsetLeft(), j, paramAnonymousWindowInsetsCompat.getSystemWindowInsetRight(), paramAnonymousWindowInsetsCompat.getSystemWindowInsetBottom());
              }
              return ViewCompat.onApplyWindowInsets(paramAnonymousView, localWindowInsetsCompat);
            }
          });
        } else {
          ((FitWindowsViewGroup)localObject1).setOnFitSystemWindowsListener(new FitWindowsViewGroup.OnFitSystemWindowsListener()
          {
            public void onFitSystemWindows(Rect paramAnonymousRect)
            {
              paramAnonymousRect.top = m.this.g(paramAnonymousRect.top);
            }
          });
        }
      }
      if (localObject1 != null)
      {
        if (this.u == null) {
          this.z = ((TextView)((ViewGroup)localObject1).findViewById(a.f.title));
        }
        ViewUtils.makeOptionalFitsSystemWindows((View)localObject1);
        localObject2 = (ContentFrameLayout)((ViewGroup)localObject1).findViewById(a.f.action_bar_activity_content);
        ViewGroup localViewGroup = (ViewGroup)this.b.findViewById(16908290);
        if (localViewGroup != null)
        {
          while (localViewGroup.getChildCount() > 0)
          {
            View localView = localViewGroup.getChildAt(0);
            localViewGroup.removeViewAt(0);
            ((ContentFrameLayout)localObject2).addView(localView);
          }
          localViewGroup.setId(-1);
          ((ContentFrameLayout)localObject2).setId(16908290);
          if ((localViewGroup instanceof FrameLayout)) {
            ((FrameLayout)localViewGroup).setForeground(null);
          }
        }
        this.b.setContentView((View)localObject1);
        ((ContentFrameLayout)localObject2).setAttachListener(new ContentFrameLayout.OnAttachListener()
        {
          public void onAttachedFromWindow() {}
          
          public void onDetachedFromWindow()
          {
            m.this.w();
          }
        });
        return (ViewGroup)localObject1;
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("AppCompat does not support the current theme features: { windowActionBar: ");
      ((StringBuilder)localObject1).append(this.h);
      ((StringBuilder)localObject1).append(", windowActionBarOverlay: ");
      ((StringBuilder)localObject1).append(this.i);
      ((StringBuilder)localObject1).append(", android:windowIsFloating: ");
      ((StringBuilder)localObject1).append(this.k);
      ((StringBuilder)localObject1).append(", windowActionModeOverlay: ");
      ((StringBuilder)localObject1).append(this.j);
      ((StringBuilder)localObject1).append(", windowNoTitle: ");
      ((StringBuilder)localObject1).append(this.l);
      ((StringBuilder)localObject1).append(" }");
      throw new IllegalArgumentException(((StringBuilder)localObject1).toString());
    }
    ((TypedArray)localObject1).recycle();
    throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
  }
  
  private void z()
  {
    ContentFrameLayout localContentFrameLayout = (ContentFrameLayout)this.y.findViewById(16908290);
    Object localObject = this.b.getDecorView();
    localContentFrameLayout.setDecorPadding(((View)localObject).getPaddingLeft(), ((View)localObject).getPaddingTop(), ((View)localObject).getPaddingRight(), ((View)localObject).getPaddingBottom());
    localObject = this.a.obtainStyledAttributes(a.j.AppCompatTheme);
    ((TypedArray)localObject).getValue(a.j.AppCompatTheme_windowMinWidthMajor, localContentFrameLayout.getMinWidthMajor());
    ((TypedArray)localObject).getValue(a.j.AppCompatTheme_windowMinWidthMinor, localContentFrameLayout.getMinWidthMinor());
    if (((TypedArray)localObject).hasValue(a.j.AppCompatTheme_windowFixedWidthMajor)) {
      ((TypedArray)localObject).getValue(a.j.AppCompatTheme_windowFixedWidthMajor, localContentFrameLayout.getFixedWidthMajor());
    }
    if (((TypedArray)localObject).hasValue(a.j.AppCompatTheme_windowFixedWidthMinor)) {
      ((TypedArray)localObject).getValue(a.j.AppCompatTheme_windowFixedWidthMinor, localContentFrameLayout.getFixedWidthMinor());
    }
    if (((TypedArray)localObject).hasValue(a.j.AppCompatTheme_windowFixedHeightMajor)) {
      ((TypedArray)localObject).getValue(a.j.AppCompatTheme_windowFixedHeightMajor, localContentFrameLayout.getFixedHeightMajor());
    }
    if (((TypedArray)localObject).hasValue(a.j.AppCompatTheme_windowFixedHeightMinor)) {
      ((TypedArray)localObject).getValue(a.j.AppCompatTheme_windowFixedHeightMinor, localContentFrameLayout.getFixedHeightMinor());
    }
    ((TypedArray)localObject).recycle();
    localContentFrameLayout.requestLayout();
  }
  
  protected d a(int paramInt, boolean paramBoolean)
  {
    Object localObject1 = this.E;
    Object localObject2;
    if (localObject1 != null)
    {
      localObject2 = localObject1;
      if (localObject1.length > paramInt) {}
    }
    else
    {
      localObject2 = new d[paramInt + 1];
      if (localObject1 != null) {
        System.arraycopy(localObject1, 0, localObject2, 0, localObject1.length);
      }
      this.E = ((d[])localObject2);
    }
    Object localObject3 = localObject2[paramInt];
    localObject1 = localObject3;
    if (localObject3 == null)
    {
      localObject1 = new d(paramInt);
      localObject2[paramInt] = localObject1;
    }
    return (d)localObject1;
  }
  
  d a(Menu paramMenu)
  {
    d[] arrayOfd = this.E;
    int i = 0;
    int j;
    if (arrayOfd != null) {
      j = arrayOfd.length;
    } else {
      j = 0;
    }
    while (i < j)
    {
      d locald = arrayOfd[i];
      if ((locald != null) && (locald.j == paramMenu)) {
        return locald;
      }
      i++;
    }
    return null;
  }
  
  android.support.v7.view.b a(b.a parama)
  {
    u();
    Object localObject1 = this.m;
    if (localObject1 != null) {
      ((android.support.v7.view.b)localObject1).c();
    }
    localObject1 = parama;
    if (!(parama instanceof b)) {
      localObject1 = new b(parama);
    }
    if ((this.e != null) && (!q())) {}
    try
    {
      parama = this.e.a((b.a)localObject1);
    }
    catch (AbstractMethodError parama)
    {
      boolean bool;
      Object localObject2;
      Resources.Theme localTheme;
      int i;
      for (;;) {}
    }
    parama = null;
    if (parama != null)
    {
      this.m = parama;
    }
    else
    {
      parama = this.n;
      bool = true;
      if (parama == null) {
        if (this.k)
        {
          localObject2 = new TypedValue();
          parama = this.a.getTheme();
          parama.resolveAttribute(a.a.actionBarTheme, (TypedValue)localObject2, true);
          if (((TypedValue)localObject2).resourceId != 0)
          {
            localTheme = this.a.getResources().newTheme();
            localTheme.setTo(parama);
            localTheme.applyStyle(((TypedValue)localObject2).resourceId, true);
            parama = new d(this.a, 0);
            parama.getTheme().setTo(localTheme);
          }
          else
          {
            parama = this.a;
          }
          this.n = new ActionBarContextView(parama);
          this.o = new PopupWindow(parama, null, a.a.actionModePopupWindowStyle);
          PopupWindowCompat.setWindowLayoutType(this.o, 2);
          this.o.setContentView(this.n);
          this.o.setWidth(-1);
          parama.getTheme().resolveAttribute(a.a.actionBarSize, (TypedValue)localObject2, true);
          i = TypedValue.complexToDimensionPixelSize(((TypedValue)localObject2).data, parama.getResources().getDisplayMetrics());
          this.n.setContentHeight(i);
          this.o.setHeight(-2);
          this.p = new Runnable()
          {
            public void run()
            {
              m.this.o.showAtLocation(m.this.n, 55, 0, 0);
              m.this.u();
              if (m.this.t())
              {
                m.this.n.setAlpha(0.0F);
                m localm = m.this;
                localm.q = ViewCompat.animate(localm.n).alpha(1.0F);
                m.this.q.setListener(new ViewPropertyAnimatorListenerAdapter()
                {
                  public void onAnimationEnd(View paramAnonymous2View)
                  {
                    m.this.n.setAlpha(1.0F);
                    m.this.q.setListener(null);
                    m.this.q = null;
                  }
                  
                  public void onAnimationStart(View paramAnonymous2View)
                  {
                    m.this.n.setVisibility(0);
                  }
                });
              }
              else
              {
                m.this.n.setAlpha(1.0F);
                m.this.n.setVisibility(0);
              }
            }
          };
        }
        else
        {
          parama = (ViewStubCompat)this.y.findViewById(a.f.action_mode_bar_stub);
          if (parama != null)
          {
            parama.setLayoutInflater(LayoutInflater.from(o()));
            this.n = ((ActionBarContextView)parama.inflate());
          }
        }
      }
      if (this.n != null)
      {
        u();
        this.n.killMode();
        localObject2 = this.n.getContext();
        parama = this.n;
        if (this.o != null) {
          bool = false;
        }
        parama = new e((Context)localObject2, parama, (b.a)localObject1, bool);
        if (((b.a)localObject1).a(parama, parama.b()))
        {
          parama.d();
          this.n.initForMode(parama);
          this.m = parama;
          if (t())
          {
            this.n.setAlpha(0.0F);
            this.q = ViewCompat.animate(this.n).alpha(1.0F);
            this.q.setListener(new ViewPropertyAnimatorListenerAdapter()
            {
              public void onAnimationEnd(View paramAnonymousView)
              {
                m.this.n.setAlpha(1.0F);
                m.this.q.setListener(null);
                m.this.q = null;
              }
              
              public void onAnimationStart(View paramAnonymousView)
              {
                m.this.n.setVisibility(0);
                m.this.n.sendAccessibilityEvent(32);
                if ((m.this.n.getParent() instanceof View)) {
                  ViewCompat.requestApplyInsets((View)m.this.n.getParent());
                }
              }
            });
          }
          else
          {
            this.n.setAlpha(1.0F);
            this.n.setVisibility(0);
            this.n.sendAccessibilityEvent(32);
            if ((this.n.getParent() instanceof View)) {
              ViewCompat.requestApplyInsets((View)this.n.getParent());
            }
          }
          if (this.o != null) {
            this.b.getDecorView().post(this.p);
          }
        }
        else
        {
          this.m = null;
        }
      }
    }
    if ((this.m != null) && (this.e != null)) {
      this.e.a(this.m);
    }
    return this.m;
  }
  
  public <T extends View> T a(int paramInt)
  {
    x();
    return this.b.findViewById(paramInt);
  }
  
  View a(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    if ((this.c instanceof LayoutInflater.Factory))
    {
      paramView = ((LayoutInflater.Factory)this.c).onCreateView(paramString, paramContext, paramAttributeSet);
      if (paramView != null) {
        return paramView;
      }
    }
    return null;
  }
  
  void a(int paramInt, d paramd, Menu paramMenu)
  {
    Object localObject1 = paramd;
    Object localObject2 = paramMenu;
    if (paramMenu == null)
    {
      d locald = paramd;
      if (paramd == null)
      {
        locald = paramd;
        if (paramInt >= 0)
        {
          localObject1 = this.E;
          locald = paramd;
          if (paramInt < localObject1.length) {
            locald = localObject1[paramInt];
          }
        }
      }
      localObject1 = locald;
      localObject2 = paramMenu;
      if (locald != null)
      {
        localObject2 = locald.j;
        localObject1 = locald;
      }
    }
    if ((localObject1 != null) && (!((d)localObject1).o)) {
      return;
    }
    if (!q()) {
      this.c.onPanelClosed(paramInt, (Menu)localObject2);
    }
  }
  
  void a(int paramInt, Menu paramMenu)
  {
    if (paramInt == 108)
    {
      paramMenu = a();
      if (paramMenu != null) {
        paramMenu.e(false);
      }
    }
    else if (paramInt == 0)
    {
      paramMenu = a(paramInt, true);
      if (paramMenu.o) {
        a(paramMenu, false);
      }
    }
  }
  
  public void a(Configuration paramConfiguration)
  {
    if ((this.h) && (this.x))
    {
      a locala = a();
      if (locala != null) {
        locala.a(paramConfiguration);
      }
    }
    AppCompatDrawableManager.get().onConfigurationChanged(this.a);
    j();
  }
  
  public void a(Bundle paramBundle)
  {
    if (((this.c instanceof Activity)) && (NavUtils.getParentActivityName((Activity)this.c) != null))
    {
      paramBundle = n();
      if (paramBundle == null) {
        this.I = true;
      } else {
        paramBundle.c(true);
      }
    }
  }
  
  void a(d paramd, boolean paramBoolean)
  {
    if ((paramBoolean) && (paramd.a == 0))
    {
      localObject = this.u;
      if ((localObject != null) && (((DecorContentParent)localObject).isOverflowMenuShowing()))
      {
        b(paramd.j);
        return;
      }
    }
    Object localObject = (WindowManager)this.a.getSystemService("window");
    if ((localObject != null) && (paramd.o) && (paramd.g != null))
    {
      ((WindowManager)localObject).removeView(paramd.g);
      if (paramBoolean) {
        a(paramd.a, paramd, null);
      }
    }
    paramd.m = false;
    paramd.n = false;
    paramd.o = false;
    paramd.h = null;
    paramd.q = true;
    if (this.F == paramd) {
      this.F = null;
    }
  }
  
  public void a(android.support.v7.view.menu.h paramh)
  {
    a(paramh, true);
  }
  
  public void a(Toolbar paramToolbar)
  {
    if (!(this.c instanceof Activity)) {
      return;
    }
    a locala = a();
    if (!(locala instanceof t))
    {
      this.g = null;
      if (locala != null) {
        locala.g();
      }
      if (paramToolbar != null)
      {
        paramToolbar = new q(paramToolbar, ((Activity)this.c).getTitle(), this.d);
        this.f = paramToolbar;
        this.b.setCallback(paramToolbar.h());
      }
      else
      {
        this.f = null;
        this.b.setCallback(this.d);
      }
      f();
      return;
    }
    throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
  }
  
  public void a(View paramView)
  {
    x();
    ViewGroup localViewGroup = (ViewGroup)this.y.findViewById(16908290);
    localViewGroup.removeAllViews();
    localViewGroup.addView(paramView);
    this.c.onContentChanged();
  }
  
  public void a(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    x();
    ViewGroup localViewGroup = (ViewGroup)this.y.findViewById(16908290);
    localViewGroup.removeAllViews();
    localViewGroup.addView(paramView, paramLayoutParams);
    this.c.onContentChanged();
  }
  
  void a(ViewGroup paramViewGroup) {}
  
  boolean a(int paramInt, KeyEvent paramKeyEvent)
  {
    Object localObject = a();
    if ((localObject != null) && (((a)localObject).a(paramInt, paramKeyEvent))) {
      return true;
    }
    localObject = this.F;
    if ((localObject != null) && (a((d)localObject, paramKeyEvent.getKeyCode(), paramKeyEvent, 1)))
    {
      paramKeyEvent = this.F;
      if (paramKeyEvent != null) {
        paramKeyEvent.n = true;
      }
      return true;
    }
    if (this.F == null)
    {
      localObject = a(0, true);
      b((d)localObject, paramKeyEvent);
      boolean bool = a((d)localObject, paramKeyEvent.getKeyCode(), paramKeyEvent, 1);
      ((d)localObject).m = false;
      if (bool) {
        return true;
      }
    }
    return false;
  }
  
  public boolean a(android.support.v7.view.menu.h paramh, MenuItem paramMenuItem)
  {
    Window.Callback localCallback = r();
    if ((localCallback != null) && (!q()))
    {
      paramh = a(paramh.p());
      if (paramh != null) {
        return localCallback.onMenuItemSelected(paramh.a, paramMenuItem);
      }
    }
    return false;
  }
  
  boolean a(KeyEvent paramKeyEvent)
  {
    int i = paramKeyEvent.getKeyCode();
    int j = 1;
    if ((i == 82) && (this.c.dispatchKeyEvent(paramKeyEvent))) {
      return true;
    }
    i = paramKeyEvent.getKeyCode();
    if (paramKeyEvent.getAction() != 0) {
      j = 0;
    }
    boolean bool;
    if (j != 0) {
      bool = c(i, paramKeyEvent);
    } else {
      bool = b(i, paramKeyEvent);
    }
    return bool;
  }
  
  public android.support.v7.view.b b(b.a parama)
  {
    if (parama != null)
    {
      Object localObject = this.m;
      if (localObject != null) {
        ((android.support.v7.view.b)localObject).c();
      }
      parama = new b(parama);
      localObject = a();
      if (localObject != null)
      {
        this.m = ((a)localObject).a(parama);
        if ((this.m != null) && (this.e != null)) {
          this.e.a(this.m);
        }
      }
      if (this.m == null) {
        this.m = a(parama);
      }
      return this.m;
    }
    throw new IllegalArgumentException("ActionMode callback can not be null.");
  }
  
  public View b(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    if (this.L == null) {
      this.L = new o();
    }
    boolean bool1 = t;
    boolean bool2 = false;
    if (bool1)
    {
      if ((paramAttributeSet instanceof XmlPullParser))
      {
        if (((XmlPullParser)paramAttributeSet).getDepth() > 1) {
          bool2 = true;
        }
      }
      else {
        bool2 = a((ViewParent)paramView);
      }
    }
    else {
      bool2 = false;
    }
    return this.L.a(paramView, paramString, paramContext, paramAttributeSet, bool2, t, true, VectorEnabledTintResources.shouldBeUsed());
  }
  
  public void b(int paramInt)
  {
    x();
    ViewGroup localViewGroup = (ViewGroup)this.y.findViewById(16908290);
    localViewGroup.removeAllViews();
    LayoutInflater.from(this.a).inflate(paramInt, localViewGroup);
    this.c.onContentChanged();
  }
  
  public void b(Bundle paramBundle)
  {
    x();
  }
  
  void b(android.support.v7.view.menu.h paramh)
  {
    if (this.D) {
      return;
    }
    this.D = true;
    this.u.dismissPopups();
    Window.Callback localCallback = r();
    if ((localCallback != null) && (!q())) {
      localCallback.onPanelClosed(108, paramh);
    }
    this.D = false;
  }
  
  public void b(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    x();
    ((ViewGroup)this.y.findViewById(16908290)).addView(paramView, paramLayoutParams);
    this.c.onContentChanged();
  }
  
  void b(CharSequence paramCharSequence)
  {
    Object localObject = this.u;
    if (localObject != null)
    {
      ((DecorContentParent)localObject).setWindowTitle(paramCharSequence);
    }
    else if (n() != null)
    {
      n().a(paramCharSequence);
    }
    else
    {
      localObject = this.z;
      if (localObject != null) {
        ((TextView)localObject).setText(paramCharSequence);
      }
    }
  }
  
  boolean b(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt != 4)
    {
      if (paramInt == 82)
      {
        e(0, paramKeyEvent);
        return true;
      }
    }
    else
    {
      boolean bool = this.G;
      this.G = false;
      paramKeyEvent = a(0, false);
      if ((paramKeyEvent != null) && (paramKeyEvent.o))
      {
        if (!bool) {
          a(paramKeyEvent, true);
        }
        return true;
      }
      if (v()) {
        return true;
      }
    }
    return false;
  }
  
  boolean b(int paramInt, Menu paramMenu)
  {
    if (paramInt == 108)
    {
      paramMenu = a();
      if (paramMenu != null) {
        paramMenu.e(true);
      }
      return true;
    }
    return false;
  }
  
  public boolean c(int paramInt)
  {
    paramInt = h(paramInt);
    if ((this.l) && (paramInt == 108)) {
      return false;
    }
    if ((this.h) && (paramInt == 1)) {
      this.h = false;
    }
    switch (paramInt)
    {
    default: 
      return this.b.requestFeature(paramInt);
    case 109: 
      A();
      this.i = true;
      return true;
    case 108: 
      A();
      this.h = true;
      return true;
    case 10: 
      A();
      this.j = true;
      return true;
    case 5: 
      A();
      this.C = true;
      return true;
    case 2: 
      A();
      this.B = true;
      return true;
    }
    A();
    this.l = true;
    return true;
  }
  
  boolean c(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = true;
    if (paramInt != 4)
    {
      if (paramInt == 82)
      {
        d(0, paramKeyEvent);
        return true;
      }
    }
    else
    {
      if ((paramKeyEvent.getFlags() & 0x80) == 0) {
        bool = false;
      }
      this.G = bool;
    }
    if (Build.VERSION.SDK_INT < 11) {
      a(paramInt, paramKeyEvent);
    }
    return false;
  }
  
  public void d()
  {
    a locala = a();
    if (locala != null) {
      locala.d(false);
    }
  }
  
  public void e()
  {
    a locala = a();
    if (locala != null) {
      locala.d(true);
    }
  }
  
  void e(int paramInt)
  {
    a(a(paramInt, true), true);
  }
  
  public void f()
  {
    a locala = a();
    if ((locala != null) && (locala.e())) {
      return;
    }
    d(0);
  }
  
  void f(int paramInt)
  {
    d locald = a(paramInt, true);
    if (locald.j != null)
    {
      Bundle localBundle = new Bundle();
      locald.j.c(localBundle);
      if (localBundle.size() > 0) {
        locald.s = localBundle;
      }
      locald.j.g();
      locald.j.clear();
    }
    locald.r = true;
    locald.q = true;
    if (((paramInt == 108) || (paramInt == 0)) && (this.u != null))
    {
      locald = a(0, false);
      if (locald != null)
      {
        locald.m = false;
        b(locald, null);
      }
    }
  }
  
  int g(int paramInt)
  {
    Object localObject1 = this.n;
    int i = 0;
    int k;
    int i1;
    if ((localObject1 != null) && ((((ActionBarContextView)localObject1).getLayoutParams() instanceof ViewGroup.MarginLayoutParams)))
    {
      localObject1 = (ViewGroup.MarginLayoutParams)this.n.getLayoutParams();
      boolean bool = this.n.isShown();
      int j = 1;
      int i2;
      int i3;
      if (bool)
      {
        if (this.J == null)
        {
          this.J = new Rect();
          this.K = new Rect();
        }
        Rect localRect = this.J;
        Object localObject2 = this.K;
        localRect.set(0, paramInt, 0, 0);
        ViewUtils.computeFitSystemWindows(this.y, localRect, (Rect)localObject2);
        if (((Rect)localObject2).top == 0) {
          k = paramInt;
        } else {
          k = 0;
        }
        if (((ViewGroup.MarginLayoutParams)localObject1).topMargin != k)
        {
          ((ViewGroup.MarginLayoutParams)localObject1).topMargin = paramInt;
          localObject2 = this.A;
          if (localObject2 == null)
          {
            this.A = new View(this.a);
            this.A.setBackgroundColor(this.a.getResources().getColor(a.c.abc_input_method_navigation_guard));
            this.y.addView(this.A, -1, new ViewGroup.LayoutParams(-1, paramInt));
          }
          else
          {
            localObject2 = ((View)localObject2).getLayoutParams();
            if (((ViewGroup.LayoutParams)localObject2).height != paramInt)
            {
              ((ViewGroup.LayoutParams)localObject2).height = paramInt;
              this.A.setLayoutParams((ViewGroup.LayoutParams)localObject2);
            }
          }
          i1 = 1;
        }
        else
        {
          i1 = 0;
        }
        if (this.A == null) {
          j = 0;
        }
        i2 = i1;
        k = j;
        i3 = paramInt;
        if (!this.j)
        {
          i2 = i1;
          k = j;
          i3 = paramInt;
          if (j != 0)
          {
            i3 = 0;
            i2 = i1;
            k = j;
          }
        }
      }
      else if (((ViewGroup.MarginLayoutParams)localObject1).topMargin != 0)
      {
        ((ViewGroup.MarginLayoutParams)localObject1).topMargin = 0;
        i2 = 1;
        k = 0;
        i3 = paramInt;
      }
      else
      {
        i2 = 0;
        k = 0;
        i3 = paramInt;
      }
      i1 = k;
      paramInt = i3;
      if (i2 != 0)
      {
        this.n.setLayoutParams((ViewGroup.LayoutParams)localObject1);
        i1 = k;
        paramInt = i3;
      }
    }
    else
    {
      i1 = 0;
    }
    localObject1 = this.A;
    if (localObject1 != null)
    {
      if (i1 != 0) {
        k = i;
      } else {
        k = 8;
      }
      ((View)localObject1).setVisibility(k);
    }
    return paramInt;
  }
  
  public void g()
  {
    if (this.r) {
      this.b.getDecorView().removeCallbacks(this.H);
    }
    super.g();
    if (this.f != null) {
      this.f.g();
    }
  }
  
  public void i()
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(this.a);
    if (localLayoutInflater.getFactory() == null) {
      LayoutInflaterCompat.setFactory2(localLayoutInflater, this);
    } else if (!(localLayoutInflater.getFactory2() instanceof m)) {
      Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
    }
  }
  
  public void m()
  {
    x();
    if ((this.h) && (this.f == null))
    {
      if ((this.c instanceof Activity)) {
        this.f = new t((Activity)this.c, this.i);
      } else if ((this.c instanceof Dialog)) {
        this.f = new t((Dialog)this.c);
      }
      if (this.f != null) {
        this.f.c(this.I);
      }
      return;
    }
  }
  
  public final View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    View localView = a(paramView, paramString, paramContext, paramAttributeSet);
    if (localView != null) {
      return localView;
    }
    return b(paramView, paramString, paramContext, paramAttributeSet);
  }
  
  public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    return onCreateView(null, paramString, paramContext, paramAttributeSet);
  }
  
  final boolean t()
  {
    if (this.x)
    {
      ViewGroup localViewGroup = this.y;
      if ((localViewGroup != null) && (ViewCompat.isLaidOut(localViewGroup))) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  void u()
  {
    ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat = this.q;
    if (localViewPropertyAnimatorCompat != null) {
      localViewPropertyAnimatorCompat.cancel();
    }
  }
  
  boolean v()
  {
    Object localObject = this.m;
    if (localObject != null)
    {
      ((android.support.v7.view.b)localObject).c();
      return true;
    }
    localObject = a();
    return (localObject != null) && (((a)localObject).f());
  }
  
  void w()
  {
    Object localObject = this.u;
    if (localObject != null) {
      ((DecorContentParent)localObject).dismissPopups();
    }
    if (this.o != null)
    {
      this.b.getDecorView().removeCallbacks(this.p);
      if (!this.o.isShowing()) {}
    }
    try
    {
      this.o.dismiss();
      this.o = null;
      u();
      localObject = a(0, false);
      if ((localObject != null) && (((d)localObject).j != null)) {
        ((d)localObject).j.close();
      }
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;) {}
    }
  }
  
  private final class a
    implements o.a
  {
    a() {}
    
    public void a(android.support.v7.view.menu.h paramh, boolean paramBoolean)
    {
      m.this.b(paramh);
    }
    
    public boolean a(android.support.v7.view.menu.h paramh)
    {
      Window.Callback localCallback = m.this.r();
      if (localCallback != null) {
        localCallback.onMenuOpened(108, paramh);
      }
      return true;
    }
  }
  
  class b
    implements b.a
  {
    private b.a b;
    
    public b(b.a parama)
    {
      this.b = parama;
    }
    
    public void a(android.support.v7.view.b paramb)
    {
      this.b.a(paramb);
      if (m.this.o != null) {
        m.this.b.getDecorView().removeCallbacks(m.this.p);
      }
      if (m.this.n != null)
      {
        m.this.u();
        paramb = m.this;
        paramb.q = ViewCompat.animate(paramb.n).alpha(0.0F);
        m.this.q.setListener(new ViewPropertyAnimatorListenerAdapter()
        {
          public void onAnimationEnd(View paramAnonymousView)
          {
            m.this.n.setVisibility(8);
            if (m.this.o != null) {
              m.this.o.dismiss();
            } else if ((m.this.n.getParent() instanceof View)) {
              ViewCompat.requestApplyInsets((View)m.this.n.getParent());
            }
            m.this.n.removeAllViews();
            m.this.q.setListener(null);
            m.this.q = null;
          }
        });
      }
      if (m.this.e != null) {
        m.this.e.b(m.this.m);
      }
      m.this.m = null;
    }
    
    public boolean a(android.support.v7.view.b paramb, Menu paramMenu)
    {
      return this.b.a(paramb, paramMenu);
    }
    
    public boolean a(android.support.v7.view.b paramb, MenuItem paramMenuItem)
    {
      return this.b.a(paramb, paramMenuItem);
    }
    
    public boolean b(android.support.v7.view.b paramb, Menu paramMenu)
    {
      return this.b.b(paramb, paramMenu);
    }
  }
  
  private class c
    extends ContentFrameLayout
  {
    public c(Context paramContext)
    {
      super();
    }
    
    private boolean a(int paramInt1, int paramInt2)
    {
      boolean bool;
      if ((paramInt1 >= -5) && (paramInt2 >= -5) && (paramInt1 <= getWidth() + 5) && (paramInt2 <= getHeight() + 5)) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      boolean bool;
      if ((!m.this.a(paramKeyEvent)) && (!super.dispatchKeyEvent(paramKeyEvent))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
    {
      if ((paramMotionEvent.getAction() == 0) && (a((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())))
      {
        m.this.e(0);
        return true;
      }
      return super.onInterceptTouchEvent(paramMotionEvent);
    }
    
    public void setBackgroundResource(int paramInt)
    {
      setBackgroundDrawable(android.support.v7.c.a.b.b(getContext(), paramInt));
    }
  }
  
  protected static final class d
  {
    int a;
    int b;
    int c;
    int d;
    int e;
    int f;
    ViewGroup g;
    View h;
    View i;
    android.support.v7.view.menu.h j;
    android.support.v7.view.menu.f k;
    Context l;
    boolean m;
    boolean n;
    boolean o;
    public boolean p;
    boolean q;
    boolean r;
    Bundle s;
    
    d(int paramInt)
    {
      this.a = paramInt;
      this.q = false;
    }
    
    p a(o.a parama)
    {
      if (this.j == null) {
        return null;
      }
      if (this.k == null)
      {
        this.k = new android.support.v7.view.menu.f(this.l, a.g.abc_list_menu_item_layout);
        this.k.a(parama);
        this.j.a(this.k);
      }
      return this.k.a(this.g);
    }
    
    void a(Context paramContext)
    {
      TypedValue localTypedValue = new TypedValue();
      Resources.Theme localTheme = paramContext.getResources().newTheme();
      localTheme.setTo(paramContext.getTheme());
      localTheme.resolveAttribute(a.a.actionBarPopupTheme, localTypedValue, true);
      if (localTypedValue.resourceId != 0) {
        localTheme.applyStyle(localTypedValue.resourceId, true);
      }
      localTheme.resolveAttribute(a.a.panelMenuListTheme, localTypedValue, true);
      if (localTypedValue.resourceId != 0) {
        localTheme.applyStyle(localTypedValue.resourceId, true);
      } else {
        localTheme.applyStyle(a.i.Theme_AppCompat_CompactMenu, true);
      }
      paramContext = new d(paramContext, 0);
      paramContext.getTheme().setTo(localTheme);
      this.l = paramContext;
      paramContext = paramContext.obtainStyledAttributes(a.j.AppCompatTheme);
      this.b = paramContext.getResourceId(a.j.AppCompatTheme_panelBackground, 0);
      this.f = paramContext.getResourceId(a.j.AppCompatTheme_android_windowAnimationStyle, 0);
      paramContext.recycle();
    }
    
    void a(android.support.v7.view.menu.h paramh)
    {
      Object localObject = this.j;
      if (paramh == localObject) {
        return;
      }
      if (localObject != null) {
        ((android.support.v7.view.menu.h)localObject).b(this.k);
      }
      this.j = paramh;
      if (paramh != null)
      {
        localObject = this.k;
        if (localObject != null) {
          paramh.a((android.support.v7.view.menu.o)localObject);
        }
      }
    }
    
    public boolean a()
    {
      View localView = this.h;
      boolean bool = false;
      if (localView == null) {
        return false;
      }
      if (this.i != null) {
        return true;
      }
      if (this.k.d().getCount() > 0) {
        bool = true;
      }
      return bool;
    }
  }
  
  private final class e
    implements o.a
  {
    e() {}
    
    public void a(android.support.v7.view.menu.h paramh, boolean paramBoolean)
    {
      android.support.v7.view.menu.h localh = paramh.p();
      int i;
      if (localh != paramh) {
        i = 1;
      } else {
        i = 0;
      }
      m localm = m.this;
      if (i != 0) {
        paramh = localh;
      }
      paramh = localm.a(paramh);
      if (paramh != null) {
        if (i != 0)
        {
          m.this.a(paramh.a, paramh, localh);
          m.this.a(paramh, true);
        }
        else
        {
          m.this.a(paramh, paramBoolean);
        }
      }
    }
    
    public boolean a(android.support.v7.view.menu.h paramh)
    {
      if ((paramh == null) && (m.this.h))
      {
        Window.Callback localCallback = m.this.r();
        if ((localCallback != null) && (!m.this.q())) {
          localCallback.onMenuOpened(108, paramh);
        }
      }
      return true;
    }
  }
}


/* Location:              ~/android/support/v7/app/m.class
 *
 * Reversed by:           J
 */