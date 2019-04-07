package android.support.design.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.design.a.b;
import android.support.design.a.h;
import android.support.design.a.i;
import android.support.v4.math.MathUtils;
import android.support.v4.util.ObjectsCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import java.lang.ref.WeakReference;
import java.util.List;

@CoordinatorLayout.b(a=Behavior.class)
public class AppBarLayout
  extends LinearLayout
{
  private int a = -1;
  private int b = -1;
  private int c = -1;
  private boolean d;
  private int e = 0;
  private WindowInsetsCompat f;
  private List<b> g;
  private boolean h;
  private boolean i;
  private int[] j;
  
  public AppBarLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setOrientation(1);
    m.a(paramContext);
    if (Build.VERSION.SDK_INT >= 21)
    {
      q.a(this);
      q.a(this, paramAttributeSet, 0, a.h.Widget_Design_AppBarLayout);
    }
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, a.i.AppBarLayout, 0, a.h.Widget_Design_AppBarLayout);
    ViewCompat.setBackground(this, paramContext.getDrawable(a.i.AppBarLayout_android_background));
    if (paramContext.hasValue(a.i.AppBarLayout_expanded)) {
      a(paramContext.getBoolean(a.i.AppBarLayout_expanded, false), false, false);
    }
    if ((Build.VERSION.SDK_INT >= 21) && (paramContext.hasValue(a.i.AppBarLayout_elevation))) {
      q.a(this, paramContext.getDimensionPixelSize(a.i.AppBarLayout_elevation, 0));
    }
    if (Build.VERSION.SDK_INT >= 26)
    {
      if (paramContext.hasValue(a.i.AppBarLayout_android_keyboardNavigationCluster)) {
        setKeyboardNavigationCluster(paramContext.getBoolean(a.i.AppBarLayout_android_keyboardNavigationCluster, false));
      }
      if (paramContext.hasValue(a.i.AppBarLayout_android_touchscreenBlocksFocus)) {
        setTouchscreenBlocksFocus(paramContext.getBoolean(a.i.AppBarLayout_android_touchscreenBlocksFocus, false));
      }
    }
    paramContext.recycle();
    ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener()
    {
      public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
      {
        return AppBarLayout.this.a(paramAnonymousWindowInsetsCompat);
      }
    });
  }
  
  private void a(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    int k;
    if (paramBoolean1) {
      k = 1;
    } else {
      k = 2;
    }
    int m = 0;
    int n;
    if (paramBoolean2) {
      n = 4;
    } else {
      n = 0;
    }
    if (paramBoolean3) {
      m = 8;
    }
    this.e = (k | n | m);
    requestLayout();
  }
  
  private boolean b(boolean paramBoolean)
  {
    if (this.h != paramBoolean)
    {
      this.h = paramBoolean;
      refreshDrawableState();
      return true;
    }
    return false;
  }
  
  private void e()
  {
    int k = getChildCount();
    boolean bool1 = false;
    boolean bool2;
    for (int m = 0;; m++)
    {
      bool2 = bool1;
      if (m >= k) {
        break;
      }
      if (((a)getChildAt(m).getLayoutParams()).c())
      {
        bool2 = true;
        break;
      }
    }
    b(bool2);
  }
  
  private void f()
  {
    this.a = -1;
    this.b = -1;
    this.c = -1;
  }
  
  protected a a()
  {
    return new a(-1, -2);
  }
  
  public a a(AttributeSet paramAttributeSet)
  {
    return new a(getContext(), paramAttributeSet);
  }
  
  protected a a(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((Build.VERSION.SDK_INT >= 19) && ((paramLayoutParams instanceof LinearLayout.LayoutParams))) {
      return new a((LinearLayout.LayoutParams)paramLayoutParams);
    }
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      return new a((ViewGroup.MarginLayoutParams)paramLayoutParams);
    }
    return new a(paramLayoutParams);
  }
  
  WindowInsetsCompat a(WindowInsetsCompat paramWindowInsetsCompat)
  {
    WindowInsetsCompat localWindowInsetsCompat;
    if (ViewCompat.getFitsSystemWindows(this)) {
      localWindowInsetsCompat = paramWindowInsetsCompat;
    } else {
      localWindowInsetsCompat = null;
    }
    if (!ObjectsCompat.equals(this.f, localWindowInsetsCompat))
    {
      this.f = localWindowInsetsCompat;
      f();
    }
    return paramWindowInsetsCompat;
  }
  
  void a(int paramInt)
  {
    Object localObject = this.g;
    if (localObject != null)
    {
      int k = 0;
      int m = ((List)localObject).size();
      while (k < m)
      {
        localObject = (b)this.g.get(k);
        if (localObject != null) {
          ((b)localObject).a(this, paramInt);
        }
        k++;
      }
    }
  }
  
  public void a(boolean paramBoolean1, boolean paramBoolean2)
  {
    a(paramBoolean1, paramBoolean2, true);
  }
  
  boolean a(boolean paramBoolean)
  {
    if (this.i != paramBoolean)
    {
      this.i = paramBoolean;
      refreshDrawableState();
      return true;
    }
    return false;
  }
  
  boolean b()
  {
    return this.d;
  }
  
  boolean c()
  {
    boolean bool;
    if (getTotalScrollRange() != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof a;
  }
  
  void d()
  {
    this.e = 0;
  }
  
  int getDownNestedPreScrollRange()
  {
    int k = this.b;
    if (k != -1) {
      return k;
    }
    int m = getChildCount() - 1;
    for (int n = 0; m >= 0; n = k)
    {
      View localView = getChildAt(m);
      a locala = (a)localView.getLayoutParams();
      k = localView.getMeasuredHeight();
      int i1 = locala.a;
      if ((i1 & 0x5) == 5)
      {
        n += locala.topMargin + locala.bottomMargin;
        if ((i1 & 0x8) != 0) {
          k = n + ViewCompat.getMinimumHeight(localView);
        } else if ((i1 & 0x2) != 0) {
          k = n + (k - ViewCompat.getMinimumHeight(localView));
        } else {
          k = n + (k - getTopInset());
        }
      }
      else
      {
        k = n;
        if (n > 0) {
          break;
        }
      }
      m--;
    }
    k = Math.max(0, n);
    this.b = k;
    return k;
  }
  
  int getDownNestedScrollRange()
  {
    int k = this.c;
    if (k != -1) {
      return k;
    }
    int m = getChildCount();
    int n = 0;
    k = 0;
    int i1;
    for (;;)
    {
      i1 = k;
      if (n >= m) {
        break;
      }
      View localView = getChildAt(n);
      a locala = (a)localView.getLayoutParams();
      int i2 = localView.getMeasuredHeight();
      int i3 = locala.topMargin;
      int i4 = locala.bottomMargin;
      int i5 = locala.a;
      i1 = k;
      if ((i5 & 0x1) == 0) {
        break;
      }
      k += i2 + (i3 + i4);
      if ((i5 & 0x2) != 0)
      {
        i1 = k - (ViewCompat.getMinimumHeight(localView) + getTopInset());
        break;
      }
      n++;
    }
    k = Math.max(0, i1);
    this.c = k;
    return k;
  }
  
  final int getMinimumHeightForVisibleOverlappingContent()
  {
    int k = getTopInset();
    int m = ViewCompat.getMinimumHeight(this);
    if (m != 0) {
      return m * 2 + k;
    }
    m = getChildCount();
    if (m >= 1) {
      m = ViewCompat.getMinimumHeight(getChildAt(m - 1));
    } else {
      m = 0;
    }
    if (m != 0) {
      return m * 2 + k;
    }
    return getHeight() / 3;
  }
  
  int getPendingAction()
  {
    return this.e;
  }
  
  @Deprecated
  public float getTargetElevation()
  {
    return 0.0F;
  }
  
  final int getTopInset()
  {
    WindowInsetsCompat localWindowInsetsCompat = this.f;
    int k;
    if (localWindowInsetsCompat != null) {
      k = localWindowInsetsCompat.getSystemWindowInsetTop();
    } else {
      k = 0;
    }
    return k;
  }
  
  public final int getTotalScrollRange()
  {
    int k = this.a;
    if (k != -1) {
      return k;
    }
    int m = getChildCount();
    int n = 0;
    k = 0;
    int i1;
    for (;;)
    {
      i1 = k;
      if (n >= m) {
        break;
      }
      View localView = getChildAt(n);
      a locala = (a)localView.getLayoutParams();
      int i2 = localView.getMeasuredHeight();
      int i3 = locala.a;
      i1 = k;
      if ((i3 & 0x1) == 0) {
        break;
      }
      k += i2 + locala.topMargin + locala.bottomMargin;
      if ((i3 & 0x2) != 0)
      {
        i1 = k - ViewCompat.getMinimumHeight(localView);
        break;
      }
      n++;
    }
    k = Math.max(0, i1 - getTopInset());
    this.a = k;
    return k;
  }
  
  int getUpNestedPreScrollRange()
  {
    return getTotalScrollRange();
  }
  
  protected int[] onCreateDrawableState(int paramInt)
  {
    if (this.j == null) {
      this.j = new int[2];
    }
    int[] arrayOfInt1 = this.j;
    int[] arrayOfInt2 = super.onCreateDrawableState(paramInt + arrayOfInt1.length);
    if (this.h) {
      paramInt = a.b.state_collapsible;
    } else {
      paramInt = -a.b.state_collapsible;
    }
    arrayOfInt1[0] = paramInt;
    if ((this.h) && (this.i)) {
      paramInt = a.b.state_collapsed;
    } else {
      paramInt = -a.b.state_collapsed;
    }
    arrayOfInt1[1] = paramInt;
    return mergeDrawableStates(arrayOfInt2, arrayOfInt1);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    f();
    paramInt1 = 0;
    this.d = false;
    paramInt2 = getChildCount();
    while (paramInt1 < paramInt2)
    {
      if (((a)getChildAt(paramInt1).getLayoutParams()).b() != null)
      {
        this.d = true;
        break;
      }
      paramInt1++;
    }
    e();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    f();
  }
  
  public void setExpanded(boolean paramBoolean)
  {
    a(paramBoolean, ViewCompat.isLaidOut(this));
  }
  
  public void setOrientation(int paramInt)
  {
    if (paramInt == 1)
    {
      super.setOrientation(paramInt);
      return;
    }
    throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
  }
  
  @Deprecated
  public void setTargetElevation(float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      q.a(this, paramFloat);
    }
  }
  
  public static class Behavior
    extends g<AppBarLayout>
  {
    private int b;
    private ValueAnimator c;
    private int d = -1;
    private boolean e;
    private float f;
    private WeakReference<View> g;
    private a h;
    
    public Behavior() {}
    
    public Behavior(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    private int a(AppBarLayout paramAppBarLayout, int paramInt)
    {
      int i = paramAppBarLayout.getChildCount();
      for (int j = 0; j < i; j++)
      {
        View localView = paramAppBarLayout.getChildAt(j);
        int k = localView.getTop();
        int m = -paramInt;
        if ((k <= m) && (localView.getBottom() >= m)) {
          return j;
        }
      }
      return -1;
    }
    
    private void a(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, int paramInt, float paramFloat)
    {
      int i = Math.abs(a() - paramInt);
      paramFloat = Math.abs(paramFloat);
      if (paramFloat > 0.0F) {
        i = Math.round(i / paramFloat * 1000.0F) * 3;
      } else {
        i = (int)((i / paramAppBarLayout.getHeight() + 1.0F) * 150.0F);
      }
      a(paramCoordinatorLayout, paramAppBarLayout, paramInt, i);
    }
    
    private void a(final CoordinatorLayout paramCoordinatorLayout, final AppBarLayout paramAppBarLayout, int paramInt1, int paramInt2)
    {
      int i = a();
      if (i == paramInt1)
      {
        paramCoordinatorLayout = this.c;
        if ((paramCoordinatorLayout != null) && (paramCoordinatorLayout.isRunning())) {
          this.c.cancel();
        }
        return;
      }
      ValueAnimator localValueAnimator = this.c;
      if (localValueAnimator == null)
      {
        this.c = new ValueAnimator();
        this.c.setInterpolator(a.e);
        this.c.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
          public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
          {
            AppBarLayout.Behavior.this.a_(paramCoordinatorLayout, paramAppBarLayout, ((Integer)paramAnonymousValueAnimator.getAnimatedValue()).intValue());
          }
        });
      }
      else
      {
        localValueAnimator.cancel();
      }
      this.c.setDuration(Math.min(paramInt2, 600));
      this.c.setIntValues(new int[] { i, paramInt1 });
      this.c.start();
    }
    
    private void a(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      View localView = c(paramAppBarLayout, paramInt1);
      if (localView != null)
      {
        int i = ((AppBarLayout.a)localView.getLayoutParams()).a();
        boolean bool1 = false;
        boolean bool2 = bool1;
        if ((i & 0x1) != 0)
        {
          int j = ViewCompat.getMinimumHeight(localView);
          if ((paramInt2 > 0) && ((i & 0xC) != 0))
          {
            bool2 = bool1;
            if (-paramInt1 >= localView.getBottom() - j - paramAppBarLayout.getTopInset()) {
              bool2 = true;
            }
          }
          else
          {
            bool2 = bool1;
            if ((i & 0x2) != 0)
            {
              bool2 = bool1;
              if (-paramInt1 >= localView.getBottom() - j - paramAppBarLayout.getTopInset()) {
                bool2 = true;
              }
            }
          }
        }
        bool2 = paramAppBarLayout.a(bool2);
        if ((Build.VERSION.SDK_INT >= 11) && ((paramBoolean) || ((bool2) && (d(paramCoordinatorLayout, paramAppBarLayout))))) {
          paramAppBarLayout.jumpDrawablesToCurrentState();
        }
      }
    }
    
    private static boolean a(int paramInt1, int paramInt2)
    {
      boolean bool;
      if ((paramInt1 & paramInt2) == paramInt2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private int b(AppBarLayout paramAppBarLayout, int paramInt)
    {
      int i = Math.abs(paramInt);
      int j = paramAppBarLayout.getChildCount();
      int k = 0;
      for (int m = 0; m < j; m++)
      {
        View localView = paramAppBarLayout.getChildAt(m);
        AppBarLayout.a locala = (AppBarLayout.a)localView.getLayoutParams();
        Interpolator localInterpolator = locala.b();
        if ((i >= localView.getTop()) && (i <= localView.getBottom()))
        {
          if (localInterpolator == null) {
            break;
          }
          j = locala.a();
          m = k;
          if ((j & 0x1) != 0)
          {
            k = 0 + (localView.getHeight() + locala.topMargin + locala.bottomMargin);
            m = k;
            if ((j & 0x2) != 0) {
              m = k - ViewCompat.getMinimumHeight(localView);
            }
          }
          k = m;
          if (ViewCompat.getFitsSystemWindows(localView)) {
            k = m - paramAppBarLayout.getTopInset();
          }
          if (k <= 0) {
            break;
          }
          m = localView.getTop();
          float f1 = k;
          m = Math.round(f1 * localInterpolator.getInterpolation((i - m) / f1));
          return Integer.signum(paramInt) * (localView.getTop() + m);
        }
      }
      return paramInt;
    }
    
    private static View c(AppBarLayout paramAppBarLayout, int paramInt)
    {
      int i = Math.abs(paramInt);
      int j = paramAppBarLayout.getChildCount();
      for (paramInt = 0; paramInt < j; paramInt++)
      {
        View localView = paramAppBarLayout.getChildAt(paramInt);
        if ((i >= localView.getTop()) && (i <= localView.getBottom())) {
          return localView;
        }
      }
      return null;
    }
    
    private void c(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout)
    {
      int i = a();
      int j = a(paramAppBarLayout, i);
      if (j >= 0)
      {
        View localView = paramAppBarLayout.getChildAt(j);
        int k = ((AppBarLayout.a)localView.getLayoutParams()).a();
        if ((k & 0x11) == 17)
        {
          int m = -localView.getTop();
          int n = -localView.getBottom();
          int i1 = n;
          if (j == paramAppBarLayout.getChildCount() - 1) {
            i1 = n + paramAppBarLayout.getTopInset();
          }
          if (a(k, 2))
          {
            n = i1 + ViewCompat.getMinimumHeight(localView);
            j = m;
          }
          else
          {
            j = m;
            n = i1;
            if (a(k, 5))
            {
              n = ViewCompat.getMinimumHeight(localView) + i1;
              if (i < n)
              {
                j = n;
                n = i1;
              }
              else
              {
                j = m;
              }
            }
          }
          i1 = j;
          if (i < (n + j) / 2) {
            i1 = n;
          }
          a(paramCoordinatorLayout, paramAppBarLayout, MathUtils.clamp(i1, -paramAppBarLayout.getTotalScrollRange(), 0), 0.0F);
        }
      }
    }
    
    private boolean d(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout)
    {
      paramCoordinatorLayout = paramCoordinatorLayout.d(paramAppBarLayout);
      int i = paramCoordinatorLayout.size();
      boolean bool = false;
      for (int j = 0; j < i; j++)
      {
        paramAppBarLayout = ((CoordinatorLayout.d)((View)paramCoordinatorLayout.get(j)).getLayoutParams()).b();
        if ((paramAppBarLayout instanceof AppBarLayout.ScrollingViewBehavior))
        {
          if (((AppBarLayout.ScrollingViewBehavior)paramAppBarLayout).d() != 0) {
            bool = true;
          }
          return bool;
        }
      }
      return false;
    }
    
    int a()
    {
      return b() + this.b;
    }
    
    int a(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, int paramInt1, int paramInt2, int paramInt3)
    {
      int i = a();
      int j = 0;
      if ((paramInt2 != 0) && (i >= paramInt2) && (i <= paramInt3))
      {
        paramInt2 = MathUtils.clamp(paramInt1, paramInt2, paramInt3);
        paramInt1 = j;
        if (i != paramInt2)
        {
          if (paramAppBarLayout.b()) {
            paramInt1 = b(paramAppBarLayout, paramInt2);
          } else {
            paramInt1 = paramInt2;
          }
          boolean bool = a(paramInt1);
          paramInt3 = i - paramInt2;
          this.b = (paramInt2 - paramInt1);
          if ((!bool) && (paramAppBarLayout.b())) {
            paramCoordinatorLayout.b(paramAppBarLayout);
          }
          paramAppBarLayout.a(b());
          if (paramInt2 < i) {
            paramInt1 = -1;
          } else {
            paramInt1 = 1;
          }
          a(paramCoordinatorLayout, paramAppBarLayout, paramInt2, paramInt1, false);
          paramInt1 = paramInt3;
        }
      }
      else
      {
        this.b = 0;
        paramInt1 = j;
      }
      return paramInt1;
    }
    
    void a(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout)
    {
      c(paramCoordinatorLayout, paramAppBarLayout);
    }
    
    public void a(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, Parcelable paramParcelable)
    {
      if ((paramParcelable instanceof b))
      {
        paramParcelable = (b)paramParcelable;
        super.a(paramCoordinatorLayout, paramAppBarLayout, paramParcelable.getSuperState());
        this.d = paramParcelable.a;
        this.f = paramParcelable.b;
        this.e = paramParcelable.c;
      }
      else
      {
        super.a(paramCoordinatorLayout, paramAppBarLayout, paramParcelable);
        this.d = -1;
      }
    }
    
    public void a(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, View paramView, int paramInt)
    {
      if (paramInt == 0) {
        c(paramCoordinatorLayout, paramAppBarLayout);
      }
      this.g = new WeakReference(paramView);
    }
    
    public void a(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      if (paramInt4 < 0) {
        b(paramCoordinatorLayout, paramAppBarLayout, paramInt4, -paramAppBarLayout.getDownNestedScrollRange(), 0);
      }
    }
    
    public void a(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
    {
      if (paramInt2 != 0)
      {
        if (paramInt2 < 0)
        {
          paramInt3 = -paramAppBarLayout.getTotalScrollRange();
          int i = paramAppBarLayout.getDownNestedPreScrollRange();
          paramInt1 = paramInt3;
          paramInt3 = i + paramInt3;
        }
        else
        {
          paramInt1 = -paramAppBarLayout.getUpNestedPreScrollRange();
          paramInt3 = 0;
        }
        if (paramInt1 != paramInt3) {
          paramArrayOfInt[1] = b(paramCoordinatorLayout, paramAppBarLayout, paramInt2, paramInt1, paramInt3);
        }
      }
    }
    
    boolean a(AppBarLayout paramAppBarLayout)
    {
      a locala = this.h;
      if (locala != null) {
        return locala.a(paramAppBarLayout);
      }
      paramAppBarLayout = this.g;
      boolean bool = true;
      if (paramAppBarLayout != null)
      {
        paramAppBarLayout = (View)paramAppBarLayout.get();
        if ((paramAppBarLayout == null) || (!paramAppBarLayout.isShown()) || (paramAppBarLayout.canScrollVertically(-1))) {
          bool = false;
        }
        return bool;
      }
      return true;
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, int paramInt)
    {
      boolean bool = super.a(paramCoordinatorLayout, paramAppBarLayout, paramInt);
      int i = paramAppBarLayout.getPendingAction();
      paramInt = this.d;
      if ((paramInt >= 0) && ((i & 0x8) == 0))
      {
        View localView = paramAppBarLayout.getChildAt(paramInt);
        paramInt = -localView.getBottom();
        if (this.e) {
          paramInt += ViewCompat.getMinimumHeight(localView) + paramAppBarLayout.getTopInset();
        } else {
          paramInt += Math.round(localView.getHeight() * this.f);
        }
        a_(paramCoordinatorLayout, paramAppBarLayout, paramInt);
      }
      else if (i != 0)
      {
        if ((i & 0x4) != 0) {
          paramInt = 1;
        } else {
          paramInt = 0;
        }
        if ((i & 0x2) != 0)
        {
          i = -paramAppBarLayout.getUpNestedPreScrollRange();
          if (paramInt != 0) {
            a(paramCoordinatorLayout, paramAppBarLayout, i, 0.0F);
          } else {
            a_(paramCoordinatorLayout, paramAppBarLayout, i);
          }
        }
        else if ((i & 0x1) != 0)
        {
          if (paramInt != 0) {
            a(paramCoordinatorLayout, paramAppBarLayout, 0, 0.0F);
          } else {
            a_(paramCoordinatorLayout, paramAppBarLayout, 0);
          }
        }
      }
      paramAppBarLayout.d();
      this.d = -1;
      a(MathUtils.clamp(b(), -paramAppBarLayout.getTotalScrollRange(), 0));
      a(paramCoordinatorLayout, paramAppBarLayout, b(), 0, true);
      paramAppBarLayout.a(b());
      return bool;
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      if (((CoordinatorLayout.d)paramAppBarLayout.getLayoutParams()).height == -2)
      {
        paramCoordinatorLayout.a(paramAppBarLayout, paramInt1, paramInt2, View.MeasureSpec.makeMeasureSpec(0, 0), paramInt4);
        return true;
      }
      return super.a(paramCoordinatorLayout, paramAppBarLayout, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, View paramView1, View paramView2, int paramInt1, int paramInt2)
    {
      boolean bool;
      if (((paramInt1 & 0x2) != 0) && (paramAppBarLayout.c()) && (paramCoordinatorLayout.getHeight() - paramView1.getHeight() <= paramAppBarLayout.getHeight())) {
        bool = true;
      } else {
        bool = false;
      }
      if (bool)
      {
        paramCoordinatorLayout = this.c;
        if (paramCoordinatorLayout != null) {
          paramCoordinatorLayout.cancel();
        }
      }
      this.g = null;
      return bool;
    }
    
    int b(AppBarLayout paramAppBarLayout)
    {
      return -paramAppBarLayout.getDownNestedScrollRange();
    }
    
    public Parcelable b(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout)
    {
      Object localObject = super.b(paramCoordinatorLayout, paramAppBarLayout);
      int i = b();
      int j = paramAppBarLayout.getChildCount();
      boolean bool = false;
      for (int k = 0; k < j; k++)
      {
        paramCoordinatorLayout = paramAppBarLayout.getChildAt(k);
        int m = paramCoordinatorLayout.getBottom() + i;
        if ((paramCoordinatorLayout.getTop() + i <= 0) && (m >= 0))
        {
          localObject = new b((Parcelable)localObject);
          ((b)localObject).a = k;
          if (m == ViewCompat.getMinimumHeight(paramCoordinatorLayout) + paramAppBarLayout.getTopInset()) {
            bool = true;
          }
          ((b)localObject).c = bool;
          ((b)localObject).b = (m / paramCoordinatorLayout.getHeight());
          return (Parcelable)localObject;
        }
      }
      return (Parcelable)localObject;
    }
    
    int c(AppBarLayout paramAppBarLayout)
    {
      return paramAppBarLayout.getTotalScrollRange();
    }
    
    public static abstract class a
    {
      public abstract boolean a(AppBarLayout paramAppBarLayout);
    }
    
    protected static class b
      extends AbsSavedState
    {
      public static final Parcelable.Creator<b> CREATOR = new Parcelable.ClassLoaderCreator()
      {
        public AppBarLayout.Behavior.b a(Parcel paramAnonymousParcel)
        {
          return new AppBarLayout.Behavior.b(paramAnonymousParcel, null);
        }
        
        public AppBarLayout.Behavior.b a(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
        {
          return new AppBarLayout.Behavior.b(paramAnonymousParcel, paramAnonymousClassLoader);
        }
        
        public AppBarLayout.Behavior.b[] a(int paramAnonymousInt)
        {
          return new AppBarLayout.Behavior.b[paramAnonymousInt];
        }
      };
      int a;
      float b;
      boolean c;
      
      public b(Parcel paramParcel, ClassLoader paramClassLoader)
      {
        super(paramClassLoader);
        this.a = paramParcel.readInt();
        this.b = paramParcel.readFloat();
        boolean bool;
        if (paramParcel.readByte() != 0) {
          bool = true;
        } else {
          bool = false;
        }
        this.c = bool;
      }
      
      public b(Parcelable paramParcelable)
      {
        super();
      }
      
      public void writeToParcel(Parcel paramParcel, int paramInt)
      {
        super.writeToParcel(paramParcel, paramInt);
        paramParcel.writeInt(this.a);
        paramParcel.writeFloat(this.b);
        paramParcel.writeByte((byte)this.c);
      }
    }
  }
  
  public static class ScrollingViewBehavior
    extends h
  {
    public ScrollingViewBehavior() {}
    
    public ScrollingViewBehavior(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, a.i.ScrollingViewBehavior_Layout);
      b(paramContext.getDimensionPixelSize(a.i.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
      paramContext.recycle();
    }
    
    private static int a(AppBarLayout paramAppBarLayout)
    {
      paramAppBarLayout = ((CoordinatorLayout.d)paramAppBarLayout.getLayoutParams()).b();
      if ((paramAppBarLayout instanceof AppBarLayout.Behavior)) {
        return ((AppBarLayout.Behavior)paramAppBarLayout).a();
      }
      return 0;
    }
    
    private void e(CoordinatorLayout paramCoordinatorLayout, View paramView1, View paramView2)
    {
      paramCoordinatorLayout = ((CoordinatorLayout.d)paramView2.getLayoutParams()).b();
      if ((paramCoordinatorLayout instanceof AppBarLayout.Behavior))
      {
        paramCoordinatorLayout = (AppBarLayout.Behavior)paramCoordinatorLayout;
        ViewCompat.offsetTopAndBottom(paramView1, paramView2.getBottom() - paramView1.getTop() + AppBarLayout.Behavior.a(paramCoordinatorLayout) + a() - c(paramView2));
      }
    }
    
    float a(View paramView)
    {
      if ((paramView instanceof AppBarLayout))
      {
        paramView = (AppBarLayout)paramView;
        int i = paramView.getTotalScrollRange();
        int j = paramView.getDownNestedPreScrollRange();
        int k = a(paramView);
        if ((j != 0) && (i + k <= j)) {
          return 0.0F;
        }
        j = i - j;
        if (j != 0) {
          return k / j + 1.0F;
        }
      }
      return 0.0F;
    }
    
    AppBarLayout a(List<View> paramList)
    {
      int i = paramList.size();
      for (int j = 0; j < i; j++)
      {
        View localView = (View)paramList.get(j);
        if ((localView instanceof AppBarLayout)) {
          return (AppBarLayout)localView;
        }
      }
      return null;
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, View paramView, Rect paramRect, boolean paramBoolean)
    {
      AppBarLayout localAppBarLayout = a(paramCoordinatorLayout.c(paramView));
      if (localAppBarLayout != null)
      {
        paramRect.offset(paramView.getLeft(), paramView.getTop());
        paramView = this.a;
        paramView.set(0, 0, paramCoordinatorLayout.getWidth(), paramCoordinatorLayout.getHeight());
        if (!paramView.contains(paramRect))
        {
          localAppBarLayout.a(false, paramBoolean ^ true);
          return true;
        }
      }
      return false;
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, View paramView1, View paramView2)
    {
      return paramView2 instanceof AppBarLayout;
    }
    
    int b(View paramView)
    {
      if ((paramView instanceof AppBarLayout)) {
        return ((AppBarLayout)paramView).getTotalScrollRange();
      }
      return super.b(paramView);
    }
    
    public boolean b(CoordinatorLayout paramCoordinatorLayout, View paramView1, View paramView2)
    {
      e(paramCoordinatorLayout, paramView1, paramView2);
      return false;
    }
  }
  
  public static class a
    extends LinearLayout.LayoutParams
  {
    int a = 1;
    Interpolator b;
    
    public a(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public a(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, a.i.AppBarLayout_Layout);
      this.a = paramAttributeSet.getInt(a.i.AppBarLayout_Layout_layout_scrollFlags, 0);
      if (paramAttributeSet.hasValue(a.i.AppBarLayout_Layout_layout_scrollInterpolator)) {
        this.b = AnimationUtils.loadInterpolator(paramContext, paramAttributeSet.getResourceId(a.i.AppBarLayout_Layout_layout_scrollInterpolator, 0));
      }
      paramAttributeSet.recycle();
    }
    
    public a(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public a(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
    
    public a(LinearLayout.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public int a()
    {
      return this.a;
    }
    
    public Interpolator b()
    {
      return this.b;
    }
    
    boolean c()
    {
      int i = this.a;
      boolean bool = true;
      if (((i & 0x1) != 1) || ((i & 0xA) == 0)) {
        bool = false;
      }
      return bool;
    }
  }
  
  public static abstract interface b
  {
    public abstract void a(AppBarLayout paramAppBarLayout, int paramInt);
  }
}


/* Location:              ~/android/support/design/widget/AppBarLayout.class
 *
 * Reversed by:           J
 */