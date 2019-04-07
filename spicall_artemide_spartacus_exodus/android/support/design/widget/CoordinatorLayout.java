package android.support.design.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.design.a.h;
import android.support.design.a.i;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.math.MathUtils;
import android.support.v4.util.ObjectsCompat;
import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SynchronizedPool;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorLayout
  extends ViewGroup
  implements NestedScrollingParent2
{
  static final String a;
  static final Class<?>[] b = { Context.class, AttributeSet.class };
  static final ThreadLocal<Map<String, Constructor<a>>> c = new ThreadLocal();
  static final Comparator<View> d;
  private static final Pools.Pool<Rect> f = new Pools.SynchronizedPool(12);
  ViewGroup.OnHierarchyChangeListener e;
  private final List<View> g = new ArrayList();
  private final d<View> h = new d();
  private final List<View> i = new ArrayList();
  private final List<View> j = new ArrayList();
  private final int[] k = new int[2];
  private Paint l;
  private boolean m;
  private boolean n;
  private int[] o;
  private View p;
  private View q;
  private e r;
  private boolean s;
  private WindowInsetsCompat t;
  private boolean u;
  private Drawable v;
  private OnApplyWindowInsetsListener w;
  private final NestedScrollingParentHelper x = new NestedScrollingParentHelper(this);
  
  static
  {
    Object localObject = CoordinatorLayout.class.getPackage();
    if (localObject != null) {
      localObject = ((Package)localObject).getName();
    } else {
      localObject = null;
    }
    a = (String)localObject;
    if (Build.VERSION.SDK_INT >= 21) {
      d = new g();
    } else {
      d = null;
    }
  }
  
  public CoordinatorLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public CoordinatorLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    m.a(paramContext);
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, a.i.CoordinatorLayout, paramInt, a.h.Widget_Design_CoordinatorLayout);
    int i1 = a.i.CoordinatorLayout_keylines;
    paramInt = 0;
    i1 = paramAttributeSet.getResourceId(i1, 0);
    if (i1 != 0)
    {
      paramContext = paramContext.getResources();
      this.o = paramContext.getIntArray(i1);
      float f1 = paramContext.getDisplayMetrics().density;
      i1 = this.o.length;
      while (paramInt < i1)
      {
        paramContext = this.o;
        paramContext[paramInt] = ((int)(paramContext[paramInt] * f1));
        paramInt++;
      }
    }
    this.v = paramAttributeSet.getDrawable(a.i.CoordinatorLayout_statusBarBackground);
    paramAttributeSet.recycle();
    h();
    super.setOnHierarchyChangeListener(new c());
  }
  
  static a a(Context paramContext, AttributeSet paramAttributeSet, String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    Object localObject1;
    if (paramString.startsWith("."))
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(paramContext.getPackageName());
      ((StringBuilder)localObject1).append(paramString);
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if (paramString.indexOf('.') >= 0)
    {
      localObject1 = paramString;
    }
    else
    {
      localObject1 = paramString;
      if (!TextUtils.isEmpty(a))
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(a);
        ((StringBuilder)localObject1).append('.');
        ((StringBuilder)localObject1).append(paramString);
        localObject1 = ((StringBuilder)localObject1).toString();
      }
    }
    try
    {
      Object localObject2 = (Map)c.get();
      paramString = (String)localObject2;
      if (localObject2 == null)
      {
        paramString = new java/util/HashMap;
        paramString.<init>();
        c.set(paramString);
      }
      Constructor localConstructor = (Constructor)paramString.get(localObject1);
      localObject2 = localConstructor;
      if (localConstructor == null)
      {
        localObject2 = Class.forName((String)localObject1, true, paramContext.getClassLoader()).getConstructor(b);
        ((Constructor)localObject2).setAccessible(true);
        paramString.put(localObject1, localObject2);
      }
      paramContext = (a)((Constructor)localObject2).newInstance(new Object[] { paramContext, paramAttributeSet });
      return paramContext;
    }
    catch (Exception paramContext)
    {
      paramAttributeSet = new StringBuilder();
      paramAttributeSet.append("Could not inflate Behavior subclass ");
      paramAttributeSet.append((String)localObject1);
      throw new RuntimeException(paramAttributeSet.toString(), paramContext);
    }
  }
  
  private static void a(Rect paramRect)
  {
    paramRect.setEmpty();
    f.release(paramRect);
  }
  
  private void a(d paramd, Rect paramRect, int paramInt1, int paramInt2)
  {
    int i1 = getWidth();
    int i2 = getHeight();
    i1 = Math.max(getPaddingLeft() + paramd.leftMargin, Math.min(paramRect.left, i1 - getPaddingRight() - paramInt1 - paramd.rightMargin));
    i2 = Math.max(getPaddingTop() + paramd.topMargin, Math.min(paramRect.top, i2 - getPaddingBottom() - paramInt2 - paramd.bottomMargin));
    paramRect.set(i1, i2, paramInt1 + i1, paramInt2 + i2);
  }
  
  private void a(View paramView, int paramInt1, Rect paramRect1, Rect paramRect2, d paramd, int paramInt2, int paramInt3)
  {
    int i1 = GravityCompat.getAbsoluteGravity(e(paramd.c), paramInt1);
    int i2 = GravityCompat.getAbsoluteGravity(c(paramd.d), paramInt1);
    int i3 = i1 & 0x7;
    int i4 = i1 & 0x70;
    paramInt1 = i2 & 0x7;
    i2 &= 0x70;
    if (paramInt1 != 1)
    {
      if (paramInt1 != 5) {
        paramInt1 = paramRect1.left;
      } else {
        paramInt1 = paramRect1.right;
      }
    }
    else {
      paramInt1 = paramRect1.left + paramRect1.width() / 2;
    }
    if (i2 != 16)
    {
      if (i2 != 80) {
        i2 = paramRect1.top;
      } else {
        i2 = paramRect1.bottom;
      }
    }
    else {
      i2 = paramRect1.top + paramRect1.height() / 2;
    }
    if (i3 != 1)
    {
      i1 = paramInt1;
      if (i3 != 5) {
        i1 = paramInt1 - paramInt2;
      }
    }
    else
    {
      i1 = paramInt1 - paramInt2 / 2;
    }
    if (i4 != 16)
    {
      paramInt1 = i2;
      if (i4 != 80) {
        paramInt1 = i2 - paramInt3;
      }
    }
    else
    {
      paramInt1 = i2 - paramInt3 / 2;
    }
    paramRect2.set(i1, paramInt1, paramInt2 + i1, paramInt3 + paramInt1);
  }
  
  private void a(View paramView, Rect paramRect, int paramInt)
  {
    if (!ViewCompat.isLaidOut(paramView)) {
      return;
    }
    if ((paramView.getWidth() > 0) && (paramView.getHeight() > 0))
    {
      d locald = (d)paramView.getLayoutParams();
      a locala = locald.b();
      Rect localRect1 = e();
      Rect localRect2 = e();
      localRect2.set(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom());
      if ((locala != null) && (locala.a(this, paramView, localRect1)))
      {
        if (!localRect2.contains(localRect1))
        {
          paramView = new StringBuilder();
          paramView.append("Rect should be within the child's bounds. Rect:");
          paramView.append(localRect1.toShortString());
          paramView.append(" | Bounds:");
          paramView.append(localRect2.toShortString());
          throw new IllegalArgumentException(paramView.toString());
        }
      }
      else {
        localRect1.set(localRect2);
      }
      a(localRect2);
      if (localRect1.isEmpty())
      {
        a(localRect1);
        return;
      }
      int i1 = GravityCompat.getAbsoluteGravity(locald.h, paramInt);
      if ((i1 & 0x30) == 48)
      {
        paramInt = localRect1.top - locald.topMargin - locald.j;
        if (paramInt < paramRect.top)
        {
          e(paramView, paramRect.top - paramInt);
          paramInt = 1;
          break label249;
        }
      }
      paramInt = 0;
      label249:
      int i2 = paramInt;
      if ((i1 & 0x50) == 80)
      {
        int i3 = getHeight() - localRect1.bottom - locald.bottomMargin + locald.j;
        i2 = paramInt;
        if (i3 < paramRect.bottom)
        {
          e(paramView, i3 - paramRect.bottom);
          i2 = 1;
        }
      }
      if (i2 == 0) {
        e(paramView, 0);
      }
      if ((i1 & 0x3) == 3)
      {
        paramInt = localRect1.left - locald.leftMargin - locald.i;
        if (paramInt < paramRect.left)
        {
          d(paramView, paramRect.left - paramInt);
          paramInt = 1;
          break label376;
        }
      }
      paramInt = 0;
      label376:
      i2 = paramInt;
      if ((i1 & 0x5) == 5)
      {
        i1 = getWidth() - localRect1.right - locald.rightMargin + locald.i;
        i2 = paramInt;
        if (i1 < paramRect.right)
        {
          d(paramView, i1 - paramRect.right);
          i2 = 1;
        }
      }
      if (i2 == 0) {
        d(paramView, 0);
      }
      a(localRect1);
      return;
    }
  }
  
  private void a(View paramView1, View paramView2, int paramInt)
  {
    Object localObject = (d)paramView1.getLayoutParams();
    localObject = e();
    Rect localRect = e();
    try
    {
      a(paramView2, (Rect)localObject);
      a(paramView1, paramInt, (Rect)localObject, localRect);
      paramView1.layout(localRect.left, localRect.top, localRect.right, localRect.bottom);
      return;
    }
    finally
    {
      a((Rect)localObject);
      a(localRect);
    }
  }
  
  private void a(List<View> paramList)
  {
    paramList.clear();
    boolean bool = isChildrenDrawingOrderEnabled();
    int i1 = getChildCount();
    for (int i2 = i1 - 1; i2 >= 0; i2--)
    {
      int i3;
      if (bool) {
        i3 = getChildDrawingOrder(i1, i2);
      } else {
        i3 = i2;
      }
      paramList.add(getChildAt(i3));
    }
    Comparator localComparator = d;
    if (localComparator != null) {
      Collections.sort(paramList, localComparator);
    }
  }
  
  private boolean a(MotionEvent paramMotionEvent, int paramInt)
  {
    int i1 = paramMotionEvent.getActionMasked();
    List localList = this.i;
    a(localList);
    int i2 = localList.size();
    Object localObject1 = null;
    int i3 = 0;
    boolean bool1 = false;
    int i4 = 0;
    boolean bool2;
    for (;;)
    {
      bool2 = bool1;
      if (i3 >= i2) {
        break;
      }
      View localView = (View)localList.get(i3);
      Object localObject2 = (d)localView.getLayoutParams();
      a locala = ((d)localObject2).b();
      boolean bool3;
      int i5;
      if (((bool1) || (i4 != 0)) && (i1 != 0))
      {
        bool3 = bool1;
        i5 = i4;
        localObject2 = localObject1;
        if (locala == null) {
          break label376;
        }
        localObject2 = localObject1;
        if (localObject1 == null)
        {
          long l1 = SystemClock.uptimeMillis();
          localObject2 = MotionEvent.obtain(l1, l1, 3, 0.0F, 0.0F, 0);
        }
      }
      switch (paramInt)
      {
      default: 
        bool3 = bool1;
        i5 = i4;
        break;
      case 1: 
        locala.b(this, localView, (MotionEvent)localObject2);
        bool3 = bool1;
        i5 = i4;
        break;
      case 0: 
        locala.a(this, localView, (MotionEvent)localObject2);
        bool3 = bool1;
        i5 = i4;
        break;
        bool2 = bool1;
        if (!bool1)
        {
          bool2 = bool1;
          if (locala != null)
          {
            switch (paramInt)
            {
            default: 
              break;
            case 1: 
              bool1 = locala.b(this, localView, paramMotionEvent);
              break;
            case 0: 
              bool1 = locala.a(this, localView, paramMotionEvent);
            }
            bool2 = bool1;
            if (bool1)
            {
              this.p = localView;
              bool2 = bool1;
            }
          }
        }
        bool3 = ((d)localObject2).e();
        bool1 = ((d)localObject2).a(this, localView);
        if ((bool1) && (!bool3)) {
          i4 = 1;
        } else {
          i4 = 0;
        }
        bool3 = bool2;
        i5 = i4;
        localObject2 = localObject1;
        if (bool1)
        {
          bool3 = bool2;
          i5 = i4;
          localObject2 = localObject1;
          if (i4 == 0) {
            break label394;
          }
        }
        break;
      }
      label376:
      i3++;
      bool1 = bool3;
      i4 = i5;
      localObject1 = localObject2;
    }
    label394:
    localList.clear();
    return bool2;
  }
  
  private int b(int paramInt)
  {
    Object localObject = this.o;
    if (localObject == null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("No keylines defined for ");
      ((StringBuilder)localObject).append(this);
      ((StringBuilder)localObject).append(" - attempted index lookup ");
      ((StringBuilder)localObject).append(paramInt);
      Log.e("CoordinatorLayout", ((StringBuilder)localObject).toString());
      return 0;
    }
    if ((paramInt >= 0) && (paramInt < localObject.length)) {
      return localObject[paramInt];
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Keyline index ");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append(" out of range for ");
    ((StringBuilder)localObject).append(this);
    Log.e("CoordinatorLayout", ((StringBuilder)localObject).toString());
    return 0;
  }
  
  private WindowInsetsCompat b(WindowInsetsCompat paramWindowInsetsCompat)
  {
    if (paramWindowInsetsCompat.isConsumed()) {
      return paramWindowInsetsCompat;
    }
    int i1 = 0;
    int i2 = getChildCount();
    WindowInsetsCompat localWindowInsetsCompat;
    for (;;)
    {
      localWindowInsetsCompat = paramWindowInsetsCompat;
      if (i1 >= i2) {
        break;
      }
      View localView = getChildAt(i1);
      localWindowInsetsCompat = paramWindowInsetsCompat;
      if (ViewCompat.getFitsSystemWindows(localView))
      {
        a locala = ((d)localView.getLayoutParams()).b();
        localWindowInsetsCompat = paramWindowInsetsCompat;
        if (locala != null)
        {
          paramWindowInsetsCompat = locala.a(this, localView, paramWindowInsetsCompat);
          localWindowInsetsCompat = paramWindowInsetsCompat;
          if (paramWindowInsetsCompat.isConsumed())
          {
            localWindowInsetsCompat = paramWindowInsetsCompat;
            break;
          }
        }
      }
      i1++;
      paramWindowInsetsCompat = localWindowInsetsCompat;
    }
    return localWindowInsetsCompat;
  }
  
  private void b(View paramView, int paramInt1, int paramInt2)
  {
    d locald = (d)paramView.getLayoutParams();
    int i1 = GravityCompat.getAbsoluteGravity(d(locald.c), paramInt2);
    int i2 = i1 & 0x7;
    int i3 = i1 & 0x70;
    int i4 = getWidth();
    int i5 = getHeight();
    int i6 = paramView.getMeasuredWidth();
    int i7 = paramView.getMeasuredHeight();
    i1 = paramInt1;
    if (paramInt2 == 1) {
      i1 = i4 - paramInt1;
    }
    paramInt1 = b(i1) - i6;
    paramInt2 = 0;
    if (i2 != 1)
    {
      if (i2 == 5) {
        paramInt1 += i6;
      }
    }
    else {
      paramInt1 += i6 / 2;
    }
    if (i3 != 16)
    {
      if (i3 == 80) {
        paramInt2 = i7 + 0;
      }
    }
    else {
      paramInt2 = 0 + i7 / 2;
    }
    paramInt1 = Math.max(getPaddingLeft() + locald.leftMargin, Math.min(paramInt1, i4 - getPaddingRight() - i6 - locald.rightMargin));
    paramInt2 = Math.max(getPaddingTop() + locald.topMargin, Math.min(paramInt2, i5 - getPaddingBottom() - i7 - locald.bottomMargin));
    paramView.layout(paramInt1, paramInt2, i6 + paramInt1, i7 + paramInt2);
  }
  
  private static int c(int paramInt)
  {
    int i1 = paramInt;
    if ((paramInt & 0x7) == 0) {
      i1 = paramInt | 0x800003;
    }
    paramInt = i1;
    if ((i1 & 0x70) == 0) {
      paramInt = i1 | 0x30;
    }
    return paramInt;
  }
  
  private void c(View paramView, int paramInt)
  {
    d locald = (d)paramView.getLayoutParams();
    Rect localRect1 = e();
    localRect1.set(getPaddingLeft() + locald.leftMargin, getPaddingTop() + locald.topMargin, getWidth() - getPaddingRight() - locald.rightMargin, getHeight() - getPaddingBottom() - locald.bottomMargin);
    if ((this.t != null) && (ViewCompat.getFitsSystemWindows(this)) && (!ViewCompat.getFitsSystemWindows(paramView)))
    {
      localRect1.left += this.t.getSystemWindowInsetLeft();
      localRect1.top += this.t.getSystemWindowInsetTop();
      localRect1.right -= this.t.getSystemWindowInsetRight();
      localRect1.bottom -= this.t.getSystemWindowInsetBottom();
    }
    Rect localRect2 = e();
    GravityCompat.apply(c(locald.c), paramView.getMeasuredWidth(), paramView.getMeasuredHeight(), localRect1, localRect2, paramInt);
    paramView.layout(localRect2.left, localRect2.top, localRect2.right, localRect2.bottom);
    a(localRect1);
    a(localRect2);
  }
  
  private static int d(int paramInt)
  {
    int i1 = paramInt;
    if (paramInt == 0) {
      i1 = 8388661;
    }
    return i1;
  }
  
  private void d(View paramView, int paramInt)
  {
    d locald = (d)paramView.getLayoutParams();
    if (locald.i != paramInt)
    {
      ViewCompat.offsetLeftAndRight(paramView, paramInt - locald.i);
      locald.i = paramInt;
    }
  }
  
  private static int e(int paramInt)
  {
    int i1 = paramInt;
    if (paramInt == 0) {
      i1 = 17;
    }
    return i1;
  }
  
  private static Rect e()
  {
    Rect localRect1 = (Rect)f.acquire();
    Rect localRect2 = localRect1;
    if (localRect1 == null) {
      localRect2 = new Rect();
    }
    return localRect2;
  }
  
  private void e(View paramView, int paramInt)
  {
    d locald = (d)paramView.getLayoutParams();
    if (locald.j != paramInt)
    {
      ViewCompat.offsetTopAndBottom(paramView, paramInt - locald.j);
      locald.j = paramInt;
    }
  }
  
  private boolean e(View paramView)
  {
    return this.h.e(paramView);
  }
  
  private void f()
  {
    Object localObject = this.p;
    if (localObject != null)
    {
      a locala = ((d)((View)localObject).getLayoutParams()).b();
      if (locala != null)
      {
        long l1 = SystemClock.uptimeMillis();
        localObject = MotionEvent.obtain(l1, l1, 3, 0.0F, 0.0F, 0);
        locala.b(this, this.p, (MotionEvent)localObject);
        ((MotionEvent)localObject).recycle();
      }
      this.p = null;
    }
    int i1 = getChildCount();
    for (int i2 = 0; i2 < i1; i2++) {
      ((d)getChildAt(i2).getLayoutParams()).f();
    }
    this.m = false;
  }
  
  private void g()
  {
    this.g.clear();
    this.h.a();
    int i1 = getChildCount();
    for (int i2 = 0; i2 < i1; i2++)
    {
      View localView1 = getChildAt(i2);
      d locald = a(localView1);
      locald.b(this, localView1);
      this.h.a(localView1);
      for (int i3 = 0; i3 < i1; i3++) {
        if (i3 != i2)
        {
          View localView2 = getChildAt(i3);
          if (locald.a(this, localView1, localView2))
          {
            if (!this.h.b(localView2)) {
              this.h.a(localView2);
            }
            this.h.a(localView2, localView1);
          }
        }
      }
    }
    this.g.addAll(this.h.b());
    Collections.reverse(this.g);
  }
  
  private void h()
  {
    if (Build.VERSION.SDK_INT < 21) {
      return;
    }
    if (ViewCompat.getFitsSystemWindows(this))
    {
      if (this.w == null) {
        this.w = new OnApplyWindowInsetsListener()
        {
          public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
          {
            return CoordinatorLayout.this.a(paramAnonymousWindowInsetsCompat);
          }
        };
      }
      ViewCompat.setOnApplyWindowInsetsListener(this, this.w);
      setSystemUiVisibility(1280);
    }
    else
    {
      ViewCompat.setOnApplyWindowInsetsListener(this, null);
    }
  }
  
  public d a(AttributeSet paramAttributeSet)
  {
    return new d(getContext(), paramAttributeSet);
  }
  
  d a(View paramView)
  {
    d locald = (d)paramView.getLayoutParams();
    if (!locald.b)
    {
      Class localClass = paramView.getClass();
      Object localObject;
      for (paramView = null; localClass != null; paramView = (View)localObject)
      {
        localObject = (b)localClass.getAnnotation(b.class);
        paramView = (View)localObject;
        if (localObject != null) {
          break;
        }
        localClass = localClass.getSuperclass();
      }
      if (paramView != null) {
        try
        {
          locald.a((a)paramView.a().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
        }
        catch (Exception localException)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Default behavior class ");
          ((StringBuilder)localObject).append(paramView.a().getName());
          ((StringBuilder)localObject).append(" could not be instantiated. Did you forget a default constructor?");
          Log.e("CoordinatorLayout", ((StringBuilder)localObject).toString(), localException);
        }
      }
      locald.b = true;
    }
    return locald;
  }
  
  protected d a(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramLayoutParams instanceof d)) {
      return new d((d)paramLayoutParams);
    }
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      return new d((ViewGroup.MarginLayoutParams)paramLayoutParams);
    }
    return new d(paramLayoutParams);
  }
  
  final WindowInsetsCompat a(WindowInsetsCompat paramWindowInsetsCompat)
  {
    WindowInsetsCompat localWindowInsetsCompat = paramWindowInsetsCompat;
    if (!ObjectsCompat.equals(this.t, paramWindowInsetsCompat))
    {
      this.t = paramWindowInsetsCompat;
      boolean bool1 = true;
      boolean bool2;
      if ((paramWindowInsetsCompat != null) && (paramWindowInsetsCompat.getSystemWindowInsetTop() > 0)) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      this.u = bool2;
      if ((!this.u) && (getBackground() == null)) {
        bool2 = bool1;
      } else {
        bool2 = false;
      }
      setWillNotDraw(bool2);
      localWindowInsetsCompat = b(paramWindowInsetsCompat);
      requestLayout();
    }
    return localWindowInsetsCompat;
  }
  
  void a()
  {
    int i1 = getChildCount();
    int i2 = 0;
    int i4;
    for (int i3 = 0;; i3++)
    {
      i4 = i2;
      if (i3 >= i1) {
        break;
      }
      if (e(getChildAt(i3)))
      {
        i4 = 1;
        break;
      }
    }
    if (i4 != this.s) {
      if (i4 != 0) {
        b();
      } else {
        c();
      }
    }
  }
  
  final void a(int paramInt)
  {
    int i1 = ViewCompat.getLayoutDirection(this);
    int i2 = this.g.size();
    Rect localRect1 = e();
    Rect localRect2 = e();
    Rect localRect3 = e();
    for (int i3 = 0; i3 < i2; i3++)
    {
      View localView = (View)this.g.get(i3);
      Object localObject1 = (d)localView.getLayoutParams();
      if ((paramInt != 0) || (localView.getVisibility() != 8))
      {
        Object localObject2;
        for (int i4 = 0; i4 < i3; i4++)
        {
          localObject2 = (View)this.g.get(i4);
          if (((d)localObject1).l == localObject2) {
            b(localView, i1);
          }
        }
        a(localView, true, localRect2);
        if ((((d)localObject1).g != 0) && (!localRect2.isEmpty()))
        {
          int i5 = GravityCompat.getAbsoluteGravity(((d)localObject1).g, i1);
          i4 = i5 & 0x70;
          if (i4 != 48)
          {
            if (i4 == 80) {
              localRect1.bottom = Math.max(localRect1.bottom, getHeight() - localRect2.top);
            }
          }
          else {
            localRect1.top = Math.max(localRect1.top, localRect2.bottom);
          }
          i4 = i5 & 0x7;
          if (i4 != 3)
          {
            if (i4 == 5) {
              localRect1.right = Math.max(localRect1.right, getWidth() - localRect2.left);
            }
          }
          else {
            localRect1.left = Math.max(localRect1.left, localRect2.right);
          }
        }
        if ((((d)localObject1).h != 0) && (localView.getVisibility() == 0)) {
          a(localView, localRect1, i1);
        }
        if (paramInt != 2)
        {
          c(localView, localRect3);
          if (!localRect3.equals(localRect2)) {
            b(localView, localRect2);
          }
        }
        else
        {
          for (i4 = i3 + 1; i4 < i2; i4++)
          {
            localObject1 = (View)this.g.get(i4);
            localObject2 = (d)((View)localObject1).getLayoutParams();
            a locala = ((d)localObject2).b();
            if ((locala != null) && (locala.a(this, (View)localObject1, localView))) {
              if ((paramInt == 0) && (((d)localObject2).g()))
              {
                ((d)localObject2).h();
              }
              else
              {
                boolean bool;
                if (paramInt != 2)
                {
                  bool = locala.b(this, (View)localObject1, localView);
                }
                else
                {
                  locala.d(this, (View)localObject1, localView);
                  bool = true;
                }
                if (paramInt == 1) {
                  ((d)localObject2).a(bool);
                }
              }
            }
          }
        }
      }
    }
    a(localRect1);
    a(localRect2);
    a(localRect3);
  }
  
  public void a(View paramView, int paramInt)
  {
    d locald = (d)paramView.getLayoutParams();
    if (!locald.d())
    {
      if (locald.k != null) {
        a(paramView, locald.k, paramInt);
      } else if (locald.e >= 0) {
        b(paramView, locald.e, paramInt);
      } else {
        c(paramView, paramInt);
      }
      return;
    }
    throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
  }
  
  public void a(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    measureChildWithMargins(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  void a(View paramView, int paramInt, Rect paramRect1, Rect paramRect2)
  {
    d locald = (d)paramView.getLayoutParams();
    int i1 = paramView.getMeasuredWidth();
    int i2 = paramView.getMeasuredHeight();
    a(paramView, paramInt, paramRect1, paramRect2, locald, i1, i2);
    a(locald, paramRect2, i1, i2);
  }
  
  void a(View paramView, Rect paramRect)
  {
    n.b(this, paramView, paramRect);
  }
  
  void a(View paramView, boolean paramBoolean, Rect paramRect)
  {
    if ((!paramView.isLayoutRequested()) && (paramView.getVisibility() != 8))
    {
      if (paramBoolean) {
        a(paramView, paramRect);
      } else {
        paramRect.set(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom());
      }
      return;
    }
    paramRect.setEmpty();
  }
  
  public boolean a(View paramView, int paramInt1, int paramInt2)
  {
    Rect localRect = e();
    a(paramView, localRect);
    try
    {
      boolean bool = localRect.contains(paramInt1, paramInt2);
      return bool;
    }
    finally
    {
      a(localRect);
    }
  }
  
  void b()
  {
    if (this.n)
    {
      if (this.r == null) {
        this.r = new e();
      }
      getViewTreeObserver().addOnPreDrawListener(this.r);
    }
    this.s = true;
  }
  
  public void b(View paramView)
  {
    List localList = this.h.c(paramView);
    if ((localList != null) && (!localList.isEmpty())) {
      for (int i1 = 0; i1 < localList.size(); i1++)
      {
        View localView = (View)localList.get(i1);
        a locala = ((d)localView.getLayoutParams()).b();
        if (locala != null) {
          locala.b(this, localView, paramView);
        }
      }
    }
  }
  
  void b(View paramView, int paramInt)
  {
    d locald = (d)paramView.getLayoutParams();
    if (locald.k != null)
    {
      Rect localRect1 = e();
      Rect localRect2 = e();
      Rect localRect3 = e();
      a(locald.k, localRect1);
      int i1 = 0;
      a(paramView, false, localRect2);
      int i2 = paramView.getMeasuredWidth();
      int i3 = paramView.getMeasuredHeight();
      a(paramView, paramInt, localRect1, localRect3, locald, i2, i3);
      if (localRect3.left == localRect2.left)
      {
        paramInt = i1;
        if (localRect3.top == localRect2.top) {}
      }
      else
      {
        paramInt = 1;
      }
      a(locald, localRect3, i2, i3);
      i1 = localRect3.left - localRect2.left;
      i3 = localRect3.top - localRect2.top;
      if (i1 != 0) {
        ViewCompat.offsetLeftAndRight(paramView, i1);
      }
      if (i3 != 0) {
        ViewCompat.offsetTopAndBottom(paramView, i3);
      }
      if (paramInt != 0)
      {
        a locala = locald.b();
        if (locala != null) {
          locala.b(this, paramView, locald.k);
        }
      }
      a(localRect1);
      a(localRect2);
      a(localRect3);
    }
  }
  
  void b(View paramView, Rect paramRect)
  {
    ((d)paramView.getLayoutParams()).a(paramRect);
  }
  
  public List<View> c(View paramView)
  {
    paramView = this.h.d(paramView);
    this.j.clear();
    if (paramView != null) {
      this.j.addAll(paramView);
    }
    return this.j;
  }
  
  void c()
  {
    if ((this.n) && (this.r != null)) {
      getViewTreeObserver().removeOnPreDrawListener(this.r);
    }
    this.s = false;
  }
  
  void c(View paramView, Rect paramRect)
  {
    paramRect.set(((d)paramView.getLayoutParams()).c());
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    boolean bool;
    if (((paramLayoutParams instanceof d)) && (super.checkLayoutParams(paramLayoutParams))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected d d()
  {
    return new d(-2, -2);
  }
  
  public List<View> d(View paramView)
  {
    paramView = this.h.c(paramView);
    this.j.clear();
    if (paramView != null) {
      this.j.addAll(paramView);
    }
    return this.j;
  }
  
  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    d locald = (d)paramView.getLayoutParams();
    if (locald.a != null)
    {
      float f1 = locald.a.d(this, paramView);
      if (f1 > 0.0F)
      {
        if (this.l == null) {
          this.l = new Paint();
        }
        this.l.setColor(locald.a.c(this, paramView));
        this.l.setAlpha(MathUtils.clamp(Math.round(f1 * 255.0F), 0, 255));
        int i1 = paramCanvas.save();
        if (paramView.isOpaque()) {
          paramCanvas.clipRect(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom(), Region.Op.DIFFERENCE);
        }
        paramCanvas.drawRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom(), this.l);
        paramCanvas.restoreToCount(i1);
      }
    }
    return super.drawChild(paramCanvas, paramView, paramLong);
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    int[] arrayOfInt = getDrawableState();
    Drawable localDrawable = this.v;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (localDrawable != null)
    {
      bool2 = bool1;
      if (localDrawable.isStateful()) {
        bool2 = false | localDrawable.setState(arrayOfInt);
      }
    }
    if (bool2) {
      invalidate();
    }
  }
  
  final List<View> getDependencySortedChildren()
  {
    g();
    return Collections.unmodifiableList(this.g);
  }
  
  final WindowInsetsCompat getLastWindowInsets()
  {
    return this.t;
  }
  
  public int getNestedScrollAxes()
  {
    return this.x.getNestedScrollAxes();
  }
  
  public Drawable getStatusBarBackground()
  {
    return this.v;
  }
  
  protected int getSuggestedMinimumHeight()
  {
    return Math.max(super.getSuggestedMinimumHeight(), getPaddingTop() + getPaddingBottom());
  }
  
  protected int getSuggestedMinimumWidth()
  {
    return Math.max(super.getSuggestedMinimumWidth(), getPaddingLeft() + getPaddingRight());
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    f();
    if (this.s)
    {
      if (this.r == null) {
        this.r = new e();
      }
      getViewTreeObserver().addOnPreDrawListener(this.r);
    }
    if ((this.t == null) && (ViewCompat.getFitsSystemWindows(this))) {
      ViewCompat.requestApplyInsets(this);
    }
    this.n = true;
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    f();
    if ((this.s) && (this.r != null)) {
      getViewTreeObserver().removeOnPreDrawListener(this.r);
    }
    View localView = this.q;
    if (localView != null) {
      onStopNestedScroll(localView);
    }
    this.n = false;
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((this.u) && (this.v != null))
    {
      WindowInsetsCompat localWindowInsetsCompat = this.t;
      int i1;
      if (localWindowInsetsCompat != null) {
        i1 = localWindowInsetsCompat.getSystemWindowInsetTop();
      } else {
        i1 = 0;
      }
      if (i1 > 0)
      {
        this.v.setBounds(0, 0, getWidth(), i1);
        this.v.draw(paramCanvas);
      }
    }
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i1 = paramMotionEvent.getActionMasked();
    if (i1 == 0) {
      f();
    }
    boolean bool = a(paramMotionEvent, 0);
    if ((i1 == 1) || (i1 == 3)) {
      f();
    }
    return bool;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramInt2 = ViewCompat.getLayoutDirection(this);
    paramInt3 = this.g.size();
    for (paramInt1 = 0; paramInt1 < paramInt3; paramInt1++)
    {
      View localView = (View)this.g.get(paramInt1);
      if (localView.getVisibility() != 8)
      {
        a locala = ((d)localView.getLayoutParams()).b();
        if ((locala == null) || (!locala.a(this, localView, paramInt2))) {
          a(localView, paramInt2);
        }
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    g();
    a();
    int i1 = getPaddingLeft();
    int i2 = getPaddingTop();
    int i3 = getPaddingRight();
    int i4 = getPaddingBottom();
    int i5 = ViewCompat.getLayoutDirection(this);
    int i6;
    if (i5 == 1) {
      i6 = 1;
    } else {
      i6 = 0;
    }
    int i7 = View.MeasureSpec.getMode(paramInt1);
    int i8 = View.MeasureSpec.getSize(paramInt1);
    int i9 = View.MeasureSpec.getMode(paramInt2);
    int i10 = View.MeasureSpec.getSize(paramInt2);
    int i11 = getSuggestedMinimumWidth();
    int i12 = getSuggestedMinimumHeight();
    int i13;
    if ((this.t != null) && (ViewCompat.getFitsSystemWindows(this))) {
      i13 = 1;
    } else {
      i13 = 0;
    }
    int i14 = this.g.size();
    int i15 = 0;
    for (int i16 = 0; i16 < i14; i16++)
    {
      View localView = (View)this.g.get(i16);
      if (localView.getVisibility() != 8)
      {
        d locald = (d)localView.getLayoutParams();
        int i18;
        if ((locald.e >= 0) && (i7 != 0))
        {
          i17 = b(locald.e);
          i18 = GravityCompat.getAbsoluteGravity(d(locald.c), i5) & 0x7;
          if (((i18 == 3) && (i6 == 0)) || ((i18 == 5) && (i6 != 0)))
          {
            i17 = Math.max(0, i8 - i3 - i17);
            break label296;
          }
          if (((i18 == 5) && (i6 == 0)) || ((i18 == 3) && (i6 != 0)))
          {
            i17 = Math.max(0, i17 - i1);
            break label296;
          }
        }
        int i17 = 0;
        label296:
        int i19 = i12;
        if ((i13 != 0) && (!ViewCompat.getFitsSystemWindows(localView)))
        {
          i12 = this.t.getSystemWindowInsetLeft();
          int i20 = this.t.getSystemWindowInsetRight();
          int i21 = this.t.getSystemWindowInsetTop();
          i18 = this.t.getSystemWindowInsetBottom();
          i12 = View.MeasureSpec.makeMeasureSpec(i8 - (i12 + i20), i7);
          i18 = View.MeasureSpec.makeMeasureSpec(i10 - (i21 + i18), i9);
        }
        else
        {
          i12 = paramInt1;
          i18 = paramInt2;
        }
        a locala = locald.b();
        if (locala != null) {
          if (locala.a(this, localView, i12, i17, i18, 0)) {
            break label434;
          }
        }
        a(localView, i12, i17, i18, 0);
        label434:
        i11 = Math.max(i11, i1 + i3 + localView.getMeasuredWidth() + locald.leftMargin + locald.rightMargin);
        i12 = Math.max(i19, i2 + i4 + localView.getMeasuredHeight() + locald.topMargin + locald.bottomMargin);
        i15 = View.combineMeasuredStates(i15, localView.getMeasuredState());
      }
    }
    setMeasuredDimension(View.resolveSizeAndState(i11, paramInt1, 0xFF000000 & i15), View.resolveSizeAndState(i12, paramInt2, i15 << 16));
  }
  
  public boolean onNestedFling(View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    int i1 = getChildCount();
    int i2 = 0;
    boolean bool2;
    for (boolean bool1 = false; i2 < i1; bool1 = bool2)
    {
      View localView = getChildAt(i2);
      if (localView.getVisibility() == 8)
      {
        bool2 = bool1;
      }
      else
      {
        Object localObject = (d)localView.getLayoutParams();
        if (!((d)localObject).b(0))
        {
          bool2 = bool1;
        }
        else
        {
          localObject = ((d)localObject).b();
          bool2 = bool1;
          if (localObject != null) {
            bool2 = bool1 | ((a)localObject).a(this, localView, paramView, paramFloat1, paramFloat2, paramBoolean);
          }
        }
      }
      i2++;
    }
    if (bool1) {
      a(1);
    }
    return bool1;
  }
  
  public boolean onNestedPreFling(View paramView, float paramFloat1, float paramFloat2)
  {
    int i1 = getChildCount();
    int i2 = 0;
    boolean bool2;
    for (boolean bool1 = false; i2 < i1; bool1 = bool2)
    {
      View localView = getChildAt(i2);
      if (localView.getVisibility() == 8)
      {
        bool2 = bool1;
      }
      else
      {
        Object localObject = (d)localView.getLayoutParams();
        if (!((d)localObject).b(0))
        {
          bool2 = bool1;
        }
        else
        {
          localObject = ((d)localObject).b();
          bool2 = bool1;
          if (localObject != null) {
            bool2 = bool1 | ((a)localObject).a(this, localView, paramView, paramFloat1, paramFloat2);
          }
        }
      }
      i2++;
    }
    return bool1;
  }
  
  public void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    onNestedPreScroll(paramView, paramInt1, paramInt2, paramArrayOfInt, 0);
  }
  
  public void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
  {
    int i1 = getChildCount();
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i7;
    for (int i5 = 0; i3 < i1; i5 = i7)
    {
      View localView = getChildAt(i3);
      int i6;
      if (localView.getVisibility() == 8)
      {
        i6 = i4;
        i7 = i5;
      }
      else
      {
        Object localObject = (d)localView.getLayoutParams();
        if (!((d)localObject).b(paramInt3))
        {
          i6 = i4;
          i7 = i5;
        }
        else
        {
          localObject = ((d)localObject).b();
          i6 = i4;
          i7 = i5;
          if (localObject != null)
          {
            int[] arrayOfInt = this.k;
            arrayOfInt[1] = 0;
            arrayOfInt[0] = 0;
            ((a)localObject).a(this, localView, paramView, paramInt1, paramInt2, arrayOfInt, paramInt3);
            if (paramInt1 > 0) {
              i7 = Math.max(i4, this.k[0]);
            } else {
              i7 = Math.min(i4, this.k[0]);
            }
            if (paramInt2 > 0) {
              i5 = Math.max(i5, this.k[1]);
            } else {
              i5 = Math.min(i5, this.k[1]);
            }
            i6 = i7;
            i7 = i5;
            i2 = 1;
          }
        }
      }
      i3++;
      i4 = i6;
    }
    paramArrayOfInt[0] = i4;
    paramArrayOfInt[1] = i5;
    if (i2 != 0) {
      a(1);
    }
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    onNestedScroll(paramView, paramInt1, paramInt2, paramInt3, paramInt4, 0);
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    int i1 = getChildCount();
    int i2 = 0;
    for (int i3 = 0; i3 < i1; i3++)
    {
      View localView = getChildAt(i3);
      if (localView.getVisibility() != 8)
      {
        Object localObject = (d)localView.getLayoutParams();
        if (((d)localObject).b(paramInt5))
        {
          localObject = ((d)localObject).b();
          if (localObject != null)
          {
            ((a)localObject).a(this, localView, paramView, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
            i2 = 1;
          }
        }
      }
    }
    if (i2 != 0) {
      a(1);
    }
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt)
  {
    onNestedScrollAccepted(paramView1, paramView2, paramInt, 0);
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    this.x.onNestedScrollAccepted(paramView1, paramView2, paramInt1, paramInt2);
    this.q = paramView2;
    int i1 = getChildCount();
    for (int i2 = 0; i2 < i1; i2++)
    {
      View localView = getChildAt(i2);
      Object localObject = (d)localView.getLayoutParams();
      if (((d)localObject).b(paramInt2))
      {
        localObject = ((d)localObject).b();
        if (localObject != null) {
          ((a)localObject).b(this, localView, paramView1, paramView2, paramInt1, paramInt2);
        }
      }
    }
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof f))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    paramParcelable = (f)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    SparseArray localSparseArray = paramParcelable.a;
    int i1 = 0;
    int i2 = getChildCount();
    while (i1 < i2)
    {
      paramParcelable = getChildAt(i1);
      int i3 = paramParcelable.getId();
      a locala = a(paramParcelable).b();
      if ((i3 != -1) && (locala != null))
      {
        Parcelable localParcelable = (Parcelable)localSparseArray.get(i3);
        if (localParcelable != null) {
          locala.a(this, paramParcelable, localParcelable);
        }
      }
      i1++;
    }
  }
  
  protected Parcelable onSaveInstanceState()
  {
    f localf = new f(super.onSaveInstanceState());
    SparseArray localSparseArray = new SparseArray();
    int i1 = getChildCount();
    for (int i2 = 0; i2 < i1; i2++)
    {
      View localView = getChildAt(i2);
      int i3 = localView.getId();
      Object localObject = ((d)localView.getLayoutParams()).b();
      if ((i3 != -1) && (localObject != null))
      {
        localObject = ((a)localObject).b(this, localView);
        if (localObject != null) {
          localSparseArray.append(i3, localObject);
        }
      }
    }
    localf.a = localSparseArray;
    return localf;
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt)
  {
    return onStartNestedScroll(paramView1, paramView2, paramInt, 0);
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    int i1 = getChildCount();
    int i2 = 0;
    boolean bool1 = false;
    while (i2 < i1)
    {
      View localView = getChildAt(i2);
      if (localView.getVisibility() != 8)
      {
        d locald = (d)localView.getLayoutParams();
        a locala = locald.b();
        if (locala != null)
        {
          boolean bool2 = locala.a(this, localView, paramView1, paramView2, paramInt1, paramInt2);
          locald.a(paramInt2, bool2);
          bool1 |= bool2;
        }
        else
        {
          locald.a(paramInt2, false);
        }
      }
      i2++;
    }
    return bool1;
  }
  
  public void onStopNestedScroll(View paramView)
  {
    onStopNestedScroll(paramView, 0);
  }
  
  public void onStopNestedScroll(View paramView, int paramInt)
  {
    this.x.onStopNestedScroll(paramView, paramInt);
    int i1 = getChildCount();
    for (int i2 = 0; i2 < i1; i2++)
    {
      View localView = getChildAt(i2);
      d locald = (d)localView.getLayoutParams();
      if (locald.b(paramInt))
      {
        a locala = locald.b();
        if (locala != null) {
          locala.a(this, localView, paramView, paramInt);
        }
        locald.a(paramInt);
        locald.h();
      }
    }
    this.q = null;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i1 = paramMotionEvent.getActionMasked();
    Object localObject = this.p;
    boolean bool1 = false;
    boolean bool2;
    if (localObject == null)
    {
      bool2 = a(paramMotionEvent, 1);
      bool3 = bool2;
      bool4 = bool1;
      if (!bool2) {
        break label86;
      }
    }
    else
    {
      bool2 = false;
    }
    localObject = ((d)this.p.getLayoutParams()).b();
    boolean bool3 = bool2;
    boolean bool4 = bool1;
    if (localObject != null)
    {
      bool4 = ((a)localObject).b(this, this.p, paramMotionEvent);
      bool3 = bool2;
    }
    label86:
    View localView = this.p;
    localObject = null;
    if (localView == null)
    {
      bool2 = bool4 | super.onTouchEvent(paramMotionEvent);
      paramMotionEvent = (MotionEvent)localObject;
    }
    else
    {
      bool2 = bool4;
      paramMotionEvent = (MotionEvent)localObject;
      if (bool3)
      {
        long l1 = SystemClock.uptimeMillis();
        paramMotionEvent = MotionEvent.obtain(l1, l1, 3, 0.0F, 0.0F, 0);
        super.onTouchEvent(paramMotionEvent);
        bool2 = bool4;
      }
    }
    if (paramMotionEvent != null) {
      paramMotionEvent.recycle();
    }
    if ((i1 == 1) || (i1 == 3)) {
      f();
    }
    return bool2;
  }
  
  public boolean requestChildRectangleOnScreen(View paramView, Rect paramRect, boolean paramBoolean)
  {
    a locala = ((d)paramView.getLayoutParams()).b();
    if ((locala != null) && (locala.a(this, paramView, paramRect, paramBoolean))) {
      return true;
    }
    return super.requestChildRectangleOnScreen(paramView, paramRect, paramBoolean);
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean)
  {
    super.requestDisallowInterceptTouchEvent(paramBoolean);
    if ((paramBoolean) && (!this.m))
    {
      f();
      this.m = true;
    }
  }
  
  public void setFitsSystemWindows(boolean paramBoolean)
  {
    super.setFitsSystemWindows(paramBoolean);
    h();
  }
  
  public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener paramOnHierarchyChangeListener)
  {
    this.e = paramOnHierarchyChangeListener;
  }
  
  public void setStatusBarBackground(Drawable paramDrawable)
  {
    Drawable localDrawable1 = this.v;
    if (localDrawable1 != paramDrawable)
    {
      Drawable localDrawable2 = null;
      if (localDrawable1 != null) {
        localDrawable1.setCallback(null);
      }
      if (paramDrawable != null) {
        localDrawable2 = paramDrawable.mutate();
      }
      this.v = localDrawable2;
      paramDrawable = this.v;
      if (paramDrawable != null)
      {
        if (paramDrawable.isStateful()) {
          this.v.setState(getDrawableState());
        }
        DrawableCompat.setLayoutDirection(this.v, ViewCompat.getLayoutDirection(this));
        paramDrawable = this.v;
        boolean bool;
        if (getVisibility() == 0) {
          bool = true;
        } else {
          bool = false;
        }
        paramDrawable.setVisible(bool, false);
        this.v.setCallback(this);
      }
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  public void setStatusBarBackgroundColor(int paramInt)
  {
    setStatusBarBackground(new ColorDrawable(paramInt));
  }
  
  public void setStatusBarBackgroundResource(int paramInt)
  {
    Drawable localDrawable;
    if (paramInt != 0) {
      localDrawable = ContextCompat.getDrawable(getContext(), paramInt);
    } else {
      localDrawable = null;
    }
    setStatusBarBackground(localDrawable);
  }
  
  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    boolean bool;
    if (paramInt == 0) {
      bool = true;
    } else {
      bool = false;
    }
    Drawable localDrawable = this.v;
    if ((localDrawable != null) && (localDrawable.isVisible() != bool)) {
      this.v.setVisible(bool, false);
    }
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    boolean bool;
    if ((!super.verifyDrawable(paramDrawable)) && (paramDrawable != this.v)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static abstract class a<V extends View>
  {
    public a() {}
    
    public a(Context paramContext, AttributeSet paramAttributeSet) {}
    
    public WindowInsetsCompat a(CoordinatorLayout paramCoordinatorLayout, V paramV, WindowInsetsCompat paramWindowInsetsCompat)
    {
      return paramWindowInsetsCompat;
    }
    
    public void a(CoordinatorLayout.d paramd) {}
    
    public void a(CoordinatorLayout paramCoordinatorLayout, V paramV, Parcelable paramParcelable) {}
    
    public void a(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt)
    {
      if (paramInt == 0) {
        c(paramCoordinatorLayout, paramV, paramView);
      }
    }
    
    @Deprecated
    public void a(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
    
    public void a(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      if (paramInt5 == 0) {
        a(paramCoordinatorLayout, paramV, paramView, paramInt1, paramInt2, paramInt3, paramInt4);
      }
    }
    
    @Deprecated
    public void a(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt) {}
    
    public void a(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
    {
      if (paramInt3 == 0) {
        a(paramCoordinatorLayout, paramV, paramView, paramInt1, paramInt2, paramArrayOfInt);
      }
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt)
    {
      return false;
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      return false;
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, Rect paramRect)
    {
      return false;
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, Rect paramRect, boolean paramBoolean)
    {
      return false;
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
    {
      return false;
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView)
    {
      return false;
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, float paramFloat1, float paramFloat2)
    {
      return false;
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean)
    {
      return false;
    }
    
    @Deprecated
    public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView1, View paramView2, int paramInt)
    {
      return false;
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView1, View paramView2, int paramInt1, int paramInt2)
    {
      if (paramInt2 == 0) {
        return a(paramCoordinatorLayout, paramV, paramView1, paramView2, paramInt1);
      }
      return false;
    }
    
    public Parcelable b(CoordinatorLayout paramCoordinatorLayout, V paramV)
    {
      return View.BaseSavedState.EMPTY_STATE;
    }
    
    @Deprecated
    public void b(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView1, View paramView2, int paramInt) {}
    
    public void b(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView1, View paramView2, int paramInt1, int paramInt2)
    {
      if (paramInt2 == 0) {
        b(paramCoordinatorLayout, paramV, paramView1, paramView2, paramInt1);
      }
    }
    
    public boolean b(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
    {
      return false;
    }
    
    public boolean b(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView)
    {
      return false;
    }
    
    public int c(CoordinatorLayout paramCoordinatorLayout, V paramV)
    {
      return -16777216;
    }
    
    public void c() {}
    
    @Deprecated
    public void c(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView) {}
    
    public float d(CoordinatorLayout paramCoordinatorLayout, V paramV)
    {
      return 0.0F;
    }
    
    public void d(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView) {}
    
    public boolean e(CoordinatorLayout paramCoordinatorLayout, V paramV)
    {
      boolean bool;
      if (d(paramCoordinatorLayout, paramV) > 0.0F) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface b
  {
    Class<? extends CoordinatorLayout.a> a();
  }
  
  private class c
    implements ViewGroup.OnHierarchyChangeListener
  {
    c() {}
    
    public void onChildViewAdded(View paramView1, View paramView2)
    {
      if (CoordinatorLayout.this.e != null) {
        CoordinatorLayout.this.e.onChildViewAdded(paramView1, paramView2);
      }
    }
    
    public void onChildViewRemoved(View paramView1, View paramView2)
    {
      CoordinatorLayout.this.a(2);
      if (CoordinatorLayout.this.e != null) {
        CoordinatorLayout.this.e.onChildViewRemoved(paramView1, paramView2);
      }
    }
  }
  
  public static class d
    extends ViewGroup.MarginLayoutParams
  {
    CoordinatorLayout.a a;
    boolean b = false;
    public int c = 0;
    public int d = 0;
    public int e = -1;
    int f = -1;
    public int g = 0;
    public int h = 0;
    int i;
    int j;
    View k;
    View l;
    final Rect m = new Rect();
    Object n;
    private boolean o;
    private boolean p;
    private boolean q;
    private boolean r;
    
    public d(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    d(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, a.i.CoordinatorLayout_Layout);
      this.c = localTypedArray.getInteger(a.i.CoordinatorLayout_Layout_android_layout_gravity, 0);
      this.f = localTypedArray.getResourceId(a.i.CoordinatorLayout_Layout_layout_anchor, -1);
      this.d = localTypedArray.getInteger(a.i.CoordinatorLayout_Layout_layout_anchorGravity, 0);
      this.e = localTypedArray.getInteger(a.i.CoordinatorLayout_Layout_layout_keyline, -1);
      this.g = localTypedArray.getInt(a.i.CoordinatorLayout_Layout_layout_insetEdge, 0);
      this.h = localTypedArray.getInt(a.i.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
      this.b = localTypedArray.hasValue(a.i.CoordinatorLayout_Layout_layout_behavior);
      if (this.b) {
        this.a = CoordinatorLayout.a(paramContext, paramAttributeSet, localTypedArray.getString(a.i.CoordinatorLayout_Layout_layout_behavior));
      }
      localTypedArray.recycle();
      paramContext = this.a;
      if (paramContext != null) {
        paramContext.a(this);
      }
    }
    
    public d(d paramd)
    {
      super();
    }
    
    public d(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public d(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
    
    private void a(View paramView, CoordinatorLayout paramCoordinatorLayout)
    {
      this.k = paramCoordinatorLayout.findViewById(this.f);
      View localView = this.k;
      if (localView != null)
      {
        if (localView == paramCoordinatorLayout)
        {
          if (paramCoordinatorLayout.isInEditMode())
          {
            this.l = null;
            this.k = null;
            return;
          }
          throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
        }
        for (localObject = localView.getParent(); (localObject != paramCoordinatorLayout) && (localObject != null); localObject = ((ViewParent)localObject).getParent())
        {
          if (localObject == paramView)
          {
            if (paramCoordinatorLayout.isInEditMode())
            {
              this.l = null;
              this.k = null;
              return;
            }
            throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
          }
          if ((localObject instanceof View)) {
            localView = (View)localObject;
          }
        }
        this.l = localView;
        return;
      }
      if (paramCoordinatorLayout.isInEditMode())
      {
        this.l = null;
        this.k = null;
        return;
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Could not find CoordinatorLayout descendant view with id ");
      ((StringBuilder)localObject).append(paramCoordinatorLayout.getResources().getResourceName(this.f));
      ((StringBuilder)localObject).append(" to anchor view ");
      ((StringBuilder)localObject).append(paramView);
      throw new IllegalStateException(((StringBuilder)localObject).toString());
    }
    
    private boolean a(View paramView, int paramInt)
    {
      int i1 = GravityCompat.getAbsoluteGravity(((d)paramView.getLayoutParams()).g, paramInt);
      boolean bool;
      if ((i1 != 0) && ((GravityCompat.getAbsoluteGravity(this.h, paramInt) & i1) == i1)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private boolean b(View paramView, CoordinatorLayout paramCoordinatorLayout)
    {
      if (this.k.getId() != this.f) {
        return false;
      }
      View localView = this.k;
      ViewParent localViewParent = localView.getParent();
      while (localViewParent != paramCoordinatorLayout) {
        if ((localViewParent != null) && (localViewParent != paramView))
        {
          if ((localViewParent instanceof View)) {
            localView = (View)localViewParent;
          }
          localViewParent = localViewParent.getParent();
        }
        else
        {
          this.l = null;
          this.k = null;
          return false;
        }
      }
      this.l = localView;
      return true;
    }
    
    public int a()
    {
      return this.f;
    }
    
    void a(int paramInt)
    {
      a(paramInt, false);
    }
    
    void a(int paramInt, boolean paramBoolean)
    {
      switch (paramInt)
      {
      default: 
        break;
      case 1: 
        this.q = paramBoolean;
        break;
      case 0: 
        this.p = paramBoolean;
      }
    }
    
    void a(Rect paramRect)
    {
      this.m.set(paramRect);
    }
    
    public void a(CoordinatorLayout.a parama)
    {
      CoordinatorLayout.a locala = this.a;
      if (locala != parama)
      {
        if (locala != null) {
          locala.c();
        }
        this.a = parama;
        this.n = null;
        this.b = true;
        if (parama != null) {
          parama.a(this);
        }
      }
    }
    
    void a(boolean paramBoolean)
    {
      this.r = paramBoolean;
    }
    
    boolean a(CoordinatorLayout paramCoordinatorLayout, View paramView)
    {
      boolean bool1 = this.o;
      if (bool1) {
        return true;
      }
      CoordinatorLayout.a locala = this.a;
      boolean bool2;
      if (locala != null) {
        bool2 = locala.e(paramCoordinatorLayout, paramView);
      } else {
        bool2 = false;
      }
      bool2 |= bool1;
      this.o = bool2;
      return bool2;
    }
    
    boolean a(CoordinatorLayout paramCoordinatorLayout, View paramView1, View paramView2)
    {
      if ((paramView2 != this.l) && (!a(paramView2, ViewCompat.getLayoutDirection(paramCoordinatorLayout))))
      {
        CoordinatorLayout.a locala = this.a;
        if ((locala == null) || (!locala.a(paramCoordinatorLayout, paramView1, paramView2)))
        {
          bool = false;
          break label54;
        }
      }
      boolean bool = true;
      label54:
      return bool;
    }
    
    public CoordinatorLayout.a b()
    {
      return this.a;
    }
    
    View b(CoordinatorLayout paramCoordinatorLayout, View paramView)
    {
      if (this.f == -1)
      {
        this.l = null;
        this.k = null;
        return null;
      }
      if ((this.k == null) || (!b(paramView, paramCoordinatorLayout))) {
        a(paramView, paramCoordinatorLayout);
      }
      return this.k;
    }
    
    boolean b(int paramInt)
    {
      switch (paramInt)
      {
      default: 
        return false;
      case 1: 
        return this.q;
      }
      return this.p;
    }
    
    Rect c()
    {
      return this.m;
    }
    
    boolean d()
    {
      boolean bool;
      if ((this.k == null) && (this.f != -1)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean e()
    {
      if (this.a == null) {
        this.o = false;
      }
      return this.o;
    }
    
    void f()
    {
      this.o = false;
    }
    
    boolean g()
    {
      return this.r;
    }
    
    void h()
    {
      this.r = false;
    }
  }
  
  class e
    implements ViewTreeObserver.OnPreDrawListener
  {
    e() {}
    
    public boolean onPreDraw()
    {
      CoordinatorLayout.this.a(0);
      return true;
    }
  }
  
  protected static class f
    extends AbsSavedState
  {
    public static final Parcelable.Creator<f> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public CoordinatorLayout.f a(Parcel paramAnonymousParcel)
      {
        return new CoordinatorLayout.f(paramAnonymousParcel, null);
      }
      
      public CoordinatorLayout.f a(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new CoordinatorLayout.f(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public CoordinatorLayout.f[] a(int paramAnonymousInt)
      {
        return new CoordinatorLayout.f[paramAnonymousInt];
      }
    };
    SparseArray<Parcelable> a;
    
    public f(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      int i = paramParcel.readInt();
      int[] arrayOfInt = new int[i];
      paramParcel.readIntArray(arrayOfInt);
      paramParcel = paramParcel.readParcelableArray(paramClassLoader);
      this.a = new SparseArray(i);
      for (int j = 0; j < i; j++) {
        this.a.append(arrayOfInt[j], paramParcel[j]);
      }
    }
    
    public f(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      Object localObject = this.a;
      int i = 0;
      int j;
      if (localObject != null) {
        j = ((SparseArray)localObject).size();
      } else {
        j = 0;
      }
      paramParcel.writeInt(j);
      int[] arrayOfInt = new int[j];
      localObject = new Parcelable[j];
      while (i < j)
      {
        arrayOfInt[i] = this.a.keyAt(i);
        localObject[i] = ((Parcelable)this.a.valueAt(i));
        i++;
      }
      paramParcel.writeIntArray(arrayOfInt);
      paramParcel.writeParcelableArray((Parcelable[])localObject, paramInt);
    }
  }
  
  static class g
    implements Comparator<View>
  {
    public int a(View paramView1, View paramView2)
    {
      float f1 = ViewCompat.getZ(paramView1);
      float f2 = ViewCompat.getZ(paramView2);
      if (f1 > f2) {
        return -1;
      }
      if (f1 < f2) {
        return 1;
      }
      return 0;
    }
  }
}


/* Location:              ~/android/support/design/widget/CoordinatorLayout.class
 *
 * Reversed by:           J
 */