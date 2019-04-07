package android.support.design.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.design.a.c;
import android.support.design.a.i;
import android.support.v4.math.MathUtils;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;

public class BottomSheetBehavior<V extends View>
  extends CoordinatorLayout.a<V>
{
  int a;
  int b;
  boolean c;
  int d = 4;
  ViewDragHelper e;
  int f;
  WeakReference<V> g;
  WeakReference<View> h;
  int i;
  boolean j;
  private float k;
  private int l;
  private boolean m;
  private int n;
  private boolean o;
  private boolean p;
  private int q;
  private boolean r;
  private a s;
  private VelocityTracker t;
  private int u;
  private final ViewDragHelper.Callback v = new ViewDragHelper.Callback()
  {
    public int clampViewPositionHorizontal(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousView.getLeft();
    }
    
    public int clampViewPositionVertical(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      int i = BottomSheetBehavior.this.a;
      if (BottomSheetBehavior.this.c) {
        paramAnonymousInt2 = BottomSheetBehavior.this.f;
      } else {
        paramAnonymousInt2 = BottomSheetBehavior.this.b;
      }
      return MathUtils.clamp(paramAnonymousInt1, i, paramAnonymousInt2);
    }
    
    public int getViewVerticalDragRange(View paramAnonymousView)
    {
      if (BottomSheetBehavior.this.c) {
        return BottomSheetBehavior.this.f - BottomSheetBehavior.this.a;
      }
      return BottomSheetBehavior.this.b - BottomSheetBehavior.this.a;
    }
    
    public void onViewDragStateChanged(int paramAnonymousInt)
    {
      if (paramAnonymousInt == 1) {
        BottomSheetBehavior.this.b(1);
      }
    }
    
    public void onViewPositionChanged(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
    {
      BottomSheetBehavior.this.c(paramAnonymousInt2);
    }
    
    public void onViewReleased(View paramAnonymousView, float paramAnonymousFloat1, float paramAnonymousFloat2)
    {
      int i = 3;
      int j;
      if (paramAnonymousFloat2 < 0.0F)
      {
        j = BottomSheetBehavior.this.a;
      }
      else if ((BottomSheetBehavior.this.c) && (BottomSheetBehavior.this.a(paramAnonymousView, paramAnonymousFloat2)))
      {
        j = BottomSheetBehavior.this.f;
        i = 5;
      }
      else if (paramAnonymousFloat2 == 0.0F)
      {
        j = paramAnonymousView.getTop();
        if (Math.abs(j - BottomSheetBehavior.this.a) < Math.abs(j - BottomSheetBehavior.this.b))
        {
          j = BottomSheetBehavior.this.a;
        }
        else
        {
          j = BottomSheetBehavior.this.b;
          i = 4;
        }
      }
      else
      {
        j = BottomSheetBehavior.this.b;
        i = 4;
      }
      if (BottomSheetBehavior.this.e.settleCapturedViewAt(paramAnonymousView.getLeft(), j))
      {
        BottomSheetBehavior.this.b(2);
        ViewCompat.postOnAnimation(paramAnonymousView, new BottomSheetBehavior.c(BottomSheetBehavior.this, paramAnonymousView, i));
      }
      else
      {
        BottomSheetBehavior.this.b(i);
      }
    }
    
    public boolean tryCaptureView(View paramAnonymousView, int paramAnonymousInt)
    {
      int i = BottomSheetBehavior.this.d;
      boolean bool = true;
      if (i == 1) {
        return false;
      }
      if (BottomSheetBehavior.this.j) {
        return false;
      }
      if ((BottomSheetBehavior.this.d == 3) && (BottomSheetBehavior.this.i == paramAnonymousInt))
      {
        View localView = (View)BottomSheetBehavior.this.h.get();
        if ((localView != null) && (localView.canScrollVertically(-1))) {
          return false;
        }
      }
      if ((BottomSheetBehavior.this.g == null) || (BottomSheetBehavior.this.g.get() != paramAnonymousView)) {
        bool = false;
      }
      return bool;
    }
  };
  
  public BottomSheetBehavior() {}
  
  public BottomSheetBehavior(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, a.i.BottomSheetBehavior_Layout);
    TypedValue localTypedValue = paramAttributeSet.peekValue(a.i.BottomSheetBehavior_Layout_behavior_peekHeight);
    if ((localTypedValue != null) && (localTypedValue.data == -1)) {
      a(localTypedValue.data);
    } else {
      a(paramAttributeSet.getDimensionPixelSize(a.i.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
    }
    a(paramAttributeSet.getBoolean(a.i.BottomSheetBehavior_Layout_behavior_hideable, false));
    b(paramAttributeSet.getBoolean(a.i.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
    paramAttributeSet.recycle();
    this.k = ViewConfiguration.get(paramContext).getScaledMaximumFlingVelocity();
  }
  
  private void a()
  {
    this.i = -1;
    VelocityTracker localVelocityTracker = this.t;
    if (localVelocityTracker != null)
    {
      localVelocityTracker.recycle();
      this.t = null;
    }
  }
  
  private float b()
  {
    this.t.computeCurrentVelocity(1000, this.k);
    return this.t.getYVelocity(this.i);
  }
  
  View a(View paramView)
  {
    if (ViewCompat.isNestedScrollingEnabled(paramView)) {
      return paramView;
    }
    if ((paramView instanceof ViewGroup))
    {
      paramView = (ViewGroup)paramView;
      int i1 = 0;
      int i2 = paramView.getChildCount();
      while (i1 < i2)
      {
        View localView = a(paramView.getChildAt(i1));
        if (localView != null) {
          return localView;
        }
        i1++;
      }
    }
    return null;
  }
  
  public final void a(int paramInt)
  {
    int i1 = 1;
    if (paramInt == -1)
    {
      if (!this.m)
      {
        this.m = true;
        paramInt = i1;
        break label73;
      }
    }
    else {
      if ((this.m) || (this.l != paramInt)) {
        break label47;
      }
    }
    paramInt = 0;
    break label73;
    label47:
    this.m = false;
    this.l = Math.max(0, paramInt);
    this.b = (this.f - paramInt);
    paramInt = i1;
    label73:
    if ((paramInt != 0) && (this.d == 4))
    {
      Object localObject = this.g;
      if (localObject != null)
      {
        localObject = (View)((WeakReference)localObject).get();
        if (localObject != null) {
          ((View)localObject).requestLayout();
        }
      }
    }
  }
  
  public void a(CoordinatorLayout paramCoordinatorLayout, V paramV, Parcelable paramParcelable)
  {
    paramParcelable = (b)paramParcelable;
    super.a(paramCoordinatorLayout, paramV, paramParcelable.getSuperState());
    if ((paramParcelable.a != 1) && (paramParcelable.a != 2)) {
      this.d = paramParcelable.a;
    } else {
      this.d = 4;
    }
  }
  
  public void a(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    if (paramView != (View)this.h.get()) {
      return;
    }
    paramInt1 = paramV.getTop();
    int i1 = paramInt1 - paramInt2;
    int i2;
    if (paramInt2 > 0)
    {
      i2 = this.a;
      if (i1 < i2)
      {
        paramArrayOfInt[1] = (paramInt1 - i2);
        ViewCompat.offsetTopAndBottom(paramV, -paramArrayOfInt[1]);
        b(3);
      }
      else
      {
        paramArrayOfInt[1] = paramInt2;
        ViewCompat.offsetTopAndBottom(paramV, -paramInt2);
        b(1);
      }
    }
    else if ((paramInt2 < 0) && (!paramView.canScrollVertically(-1)))
    {
      i2 = this.b;
      if ((i1 > i2) && (!this.c))
      {
        paramArrayOfInt[1] = (paramInt1 - i2);
        ViewCompat.offsetTopAndBottom(paramV, -paramArrayOfInt[1]);
        b(4);
      }
      else
      {
        paramArrayOfInt[1] = paramInt2;
        ViewCompat.offsetTopAndBottom(paramV, -paramInt2);
        b(1);
      }
    }
    c(paramV.getTop());
    this.q = paramInt2;
    this.r = true;
  }
  
  public void a(boolean paramBoolean)
  {
    this.c = paramBoolean;
  }
  
  public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt)
  {
    if ((ViewCompat.getFitsSystemWindows(paramCoordinatorLayout)) && (!ViewCompat.getFitsSystemWindows(paramV))) {
      ViewCompat.setFitsSystemWindows(paramV, true);
    }
    int i1 = paramV.getTop();
    paramCoordinatorLayout.a(paramV, paramInt);
    this.f = paramCoordinatorLayout.getHeight();
    if (this.m)
    {
      if (this.n == 0) {
        this.n = paramCoordinatorLayout.getResources().getDimensionPixelSize(a.c.design_bottom_sheet_peek_height_min);
      }
      paramInt = Math.max(this.n, this.f - paramCoordinatorLayout.getWidth() * 9 / 16);
    }
    else
    {
      paramInt = this.l;
    }
    this.a = Math.max(0, this.f - paramV.getHeight());
    this.b = Math.max(this.f - paramInt, this.a);
    paramInt = this.d;
    if (paramInt == 3)
    {
      ViewCompat.offsetTopAndBottom(paramV, this.a);
    }
    else if ((this.c) && (paramInt == 5))
    {
      ViewCompat.offsetTopAndBottom(paramV, this.f);
    }
    else
    {
      paramInt = this.d;
      if (paramInt == 4) {
        ViewCompat.offsetTopAndBottom(paramV, this.b);
      } else if ((paramInt == 1) || (paramInt == 2)) {
        ViewCompat.offsetTopAndBottom(paramV, i1 - paramV.getTop());
      }
    }
    if (this.e == null) {
      this.e = ViewDragHelper.create(paramCoordinatorLayout, this.v);
    }
    this.g = new WeakReference(paramV);
    this.h = new WeakReference(a(paramV));
    return true;
  }
  
  public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
  {
    boolean bool1 = paramV.isShown();
    boolean bool2 = false;
    if (!bool1)
    {
      this.p = true;
      return false;
    }
    int i1 = paramMotionEvent.getActionMasked();
    if (i1 == 0) {
      a();
    }
    if (this.t == null) {
      this.t = VelocityTracker.obtain();
    }
    this.t.addMovement(paramMotionEvent);
    if (i1 != 3)
    {
      switch (i1)
      {
      default: 
        break;
      case 0: 
        int i2 = (int)paramMotionEvent.getX();
        this.u = ((int)paramMotionEvent.getY());
        Object localObject = this.h;
        if (localObject != null) {
          localObject = (View)((WeakReference)localObject).get();
        } else {
          localObject = null;
        }
        if ((localObject != null) && (paramCoordinatorLayout.a((View)localObject, i2, this.u)))
        {
          this.i = paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex());
          this.j = true;
        }
        if ((this.i == -1) && (!paramCoordinatorLayout.a(paramV, i2, this.u))) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        this.p = bool1;
        break;
      }
    }
    else
    {
      this.j = false;
      this.i = -1;
      if (this.p)
      {
        this.p = false;
        return false;
      }
    }
    if ((!this.p) && (this.e.shouldInterceptTouchEvent(paramMotionEvent))) {
      return true;
    }
    paramV = (View)this.h.get();
    bool1 = bool2;
    if (i1 == 2)
    {
      bool1 = bool2;
      if (paramV != null)
      {
        bool1 = bool2;
        if (!this.p)
        {
          bool1 = bool2;
          if (this.d != 1)
          {
            bool1 = bool2;
            if (!paramCoordinatorLayout.a(paramV, (int)paramMotionEvent.getX(), (int)paramMotionEvent.getY()))
            {
              bool1 = bool2;
              if (Math.abs(this.u - paramMotionEvent.getY()) > this.e.getTouchSlop()) {
                bool1 = true;
              }
            }
          }
        }
      }
    }
    return bool1;
  }
  
  public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, float paramFloat1, float paramFloat2)
  {
    boolean bool;
    if ((paramView == this.h.get()) && ((this.d != 3) || (super.a(paramCoordinatorLayout, paramV, paramView, paramFloat1, paramFloat2)))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView1, View paramView2, int paramInt)
  {
    boolean bool = false;
    this.q = 0;
    this.r = false;
    if ((paramInt & 0x2) != 0) {
      bool = true;
    }
    return bool;
  }
  
  boolean a(View paramView, float paramFloat)
  {
    boolean bool1 = this.o;
    boolean bool2 = true;
    if (bool1) {
      return true;
    }
    if (paramView.getTop() < this.b) {
      return false;
    }
    if (Math.abs(paramView.getTop() + paramFloat * 0.1F - this.b) / this.l <= 0.5F) {
      bool2 = false;
    }
    return bool2;
  }
  
  public Parcelable b(CoordinatorLayout paramCoordinatorLayout, V paramV)
  {
    return new b(super.b(paramCoordinatorLayout, paramV), this.d);
  }
  
  void b(int paramInt)
  {
    if (this.d == paramInt) {
      return;
    }
    this.d = paramInt;
    View localView = (View)this.g.get();
    if (localView != null)
    {
      a locala = this.s;
      if (locala != null) {
        locala.a(localView, paramInt);
      }
    }
  }
  
  public void b(boolean paramBoolean)
  {
    this.o = paramBoolean;
  }
  
  public boolean b(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
  {
    if (!paramV.isShown()) {
      return false;
    }
    int i1 = paramMotionEvent.getActionMasked();
    if ((this.d == 1) && (i1 == 0)) {
      return true;
    }
    this.e.processTouchEvent(paramMotionEvent);
    if (i1 == 0) {
      a();
    }
    if (this.t == null) {
      this.t = VelocityTracker.obtain();
    }
    this.t.addMovement(paramMotionEvent);
    if ((i1 == 2) && (!this.p) && (Math.abs(this.u - paramMotionEvent.getY()) > this.e.getTouchSlop())) {
      this.e.captureChildView(paramV, paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex()));
    }
    return this.p ^ true;
  }
  
  void c(int paramInt)
  {
    View localView = (View)this.g.get();
    if (localView != null)
    {
      a locala = this.s;
      if (locala != null)
      {
        int i1 = this.b;
        if (paramInt > i1) {
          locala.a(localView, (i1 - paramInt) / (this.f - i1));
        } else {
          locala.a(localView, (i1 - paramInt) / (i1 - this.a));
        }
      }
    }
  }
  
  public void c(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView)
  {
    int i1 = paramV.getTop();
    int i2 = this.a;
    int i3 = 3;
    if (i1 == i2)
    {
      b(3);
      return;
    }
    paramCoordinatorLayout = this.h;
    if ((paramCoordinatorLayout != null) && (paramView == paramCoordinatorLayout.get()) && (this.r))
    {
      if (this.q > 0)
      {
        i1 = this.a;
      }
      else if ((this.c) && (a(paramV, b())))
      {
        i1 = this.f;
        i3 = 5;
      }
      else if (this.q == 0)
      {
        i1 = paramV.getTop();
        if (Math.abs(i1 - this.a) < Math.abs(i1 - this.b))
        {
          i1 = this.a;
        }
        else
        {
          i1 = this.b;
          i3 = 4;
        }
      }
      else
      {
        i1 = this.b;
        i3 = 4;
      }
      if (this.e.smoothSlideViewTo(paramV, paramV.getLeft(), i1))
      {
        b(2);
        ViewCompat.postOnAnimation(paramV, new c(paramV, i3));
      }
      else
      {
        b(i3);
      }
      this.r = false;
      return;
    }
  }
  
  public static abstract class a
  {
    public abstract void a(View paramView, float paramFloat);
    
    public abstract void a(View paramView, int paramInt);
  }
  
  protected static class b
    extends AbsSavedState
  {
    public static final Parcelable.Creator<b> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public BottomSheetBehavior.b a(Parcel paramAnonymousParcel)
      {
        return new BottomSheetBehavior.b(paramAnonymousParcel, null);
      }
      
      public BottomSheetBehavior.b a(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new BottomSheetBehavior.b(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public BottomSheetBehavior.b[] a(int paramAnonymousInt)
      {
        return new BottomSheetBehavior.b[paramAnonymousInt];
      }
    };
    final int a;
    
    public b(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      this.a = paramParcel.readInt();
    }
    
    public b(Parcelable paramParcelable, int paramInt)
    {
      super();
      this.a = paramInt;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.a);
    }
  }
  
  private class c
    implements Runnable
  {
    private final View b;
    private final int c;
    
    c(View paramView, int paramInt)
    {
      this.b = paramView;
      this.c = paramInt;
    }
    
    public void run()
    {
      if ((BottomSheetBehavior.this.e != null) && (BottomSheetBehavior.this.e.continueSettling(true))) {
        ViewCompat.postOnAnimation(this.b, this);
      } else {
        BottomSheetBehavior.this.b(this.c);
      }
    }
  }
}


/* Location:              ~/android/support/design/widget/BottomSheetBehavior.class
 *
 * Reversed by:           J
 */