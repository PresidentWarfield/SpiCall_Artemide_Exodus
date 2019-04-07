package android.support.design.widget;

import android.content.Context;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

abstract class g<V extends View>
  extends o<V>
{
  OverScroller a;
  private Runnable b;
  private boolean c;
  private int d = -1;
  private int e;
  private int f = -1;
  private VelocityTracker g;
  
  public g() {}
  
  public g(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void d()
  {
    if (this.g == null) {
      this.g = VelocityTracker.obtain();
    }
  }
  
  int a()
  {
    return b();
  }
  
  int a(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = b();
    if ((paramInt2 != 0) && (i >= paramInt2) && (i <= paramInt3))
    {
      paramInt1 = MathUtils.clamp(paramInt1, paramInt2, paramInt3);
      if (i != paramInt1)
      {
        a(paramInt1);
        return i - paramInt1;
      }
    }
    paramInt1 = 0;
    return paramInt1;
  }
  
  int a(V paramV)
  {
    return paramV.getHeight();
  }
  
  void a(CoordinatorLayout paramCoordinatorLayout, V paramV) {}
  
  final boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt1, int paramInt2, float paramFloat)
  {
    Runnable localRunnable = this.b;
    if (localRunnable != null)
    {
      paramV.removeCallbacks(localRunnable);
      this.b = null;
    }
    if (this.a == null) {
      this.a = new OverScroller(paramV.getContext());
    }
    this.a.fling(0, b(), 0, Math.round(paramFloat), 0, 0, paramInt1, paramInt2);
    if (this.a.computeScrollOffset())
    {
      this.b = new a(paramCoordinatorLayout, paramV);
      ViewCompat.postOnAnimation(paramV, this.b);
      return true;
    }
    a(paramCoordinatorLayout, paramV);
    return false;
  }
  
  public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
  {
    if (this.f < 0) {
      this.f = ViewConfiguration.get(paramCoordinatorLayout.getContext()).getScaledTouchSlop();
    }
    if ((paramMotionEvent.getAction() == 2) && (this.c)) {
      return true;
    }
    int i;
    switch (paramMotionEvent.getActionMasked())
    {
    default: 
      break;
    case 2: 
      i = this.d;
      if (i != -1)
      {
        i = paramMotionEvent.findPointerIndex(i);
        if (i != -1)
        {
          i = (int)paramMotionEvent.getY(i);
          if (Math.abs(i - this.e) > this.f)
          {
            this.c = true;
            this.e = i;
          }
        }
      }
      break;
    case 1: 
    case 3: 
      this.c = false;
      this.d = -1;
      paramCoordinatorLayout = this.g;
      if (paramCoordinatorLayout != null)
      {
        paramCoordinatorLayout.recycle();
        this.g = null;
      }
      break;
    case 0: 
      this.c = false;
      int j = (int)paramMotionEvent.getX();
      i = (int)paramMotionEvent.getY();
      if ((c(paramV)) && (paramCoordinatorLayout.a(paramV, j, i)))
      {
        this.e = i;
        this.d = paramMotionEvent.getPointerId(0);
        d();
      }
      break;
    }
    paramCoordinatorLayout = this.g;
    if (paramCoordinatorLayout != null) {
      paramCoordinatorLayout.addMovement(paramMotionEvent);
    }
    return this.c;
  }
  
  int a_(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt)
  {
    return a(paramCoordinatorLayout, paramV, paramInt, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }
  
  final int b(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt1, int paramInt2, int paramInt3)
  {
    return a(paramCoordinatorLayout, paramV, a() - paramInt1, paramInt2, paramInt3);
  }
  
  int b(V paramV)
  {
    return -paramV.getHeight();
  }
  
  public boolean b(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
  {
    if (this.f < 0) {
      this.f = ViewConfiguration.get(paramCoordinatorLayout.getContext()).getScaledTouchSlop();
    }
    switch (paramMotionEvent.getActionMasked())
    {
    default: 
      break;
    case 2: 
      i = paramMotionEvent.findPointerIndex(this.d);
      if (i == -1) {
        return false;
      }
      j = (int)paramMotionEvent.getY(i);
      i = this.e - j;
      if (!this.c)
      {
        int k = Math.abs(i);
        int m = this.f;
        if (k > m)
        {
          this.c = true;
          if (i > 0) {
            i -= m;
          } else {
            i += m;
          }
        }
      }
      if (!this.c) {
        break label328;
      }
      this.e = j;
      b(paramCoordinatorLayout, paramV, i, b(paramV), 0);
      break;
    case 1: 
      VelocityTracker localVelocityTracker = this.g;
      if (localVelocityTracker != null)
      {
        localVelocityTracker.addMovement(paramMotionEvent);
        this.g.computeCurrentVelocity(1000);
        float f1 = this.g.getYVelocity(this.d);
        a(paramCoordinatorLayout, paramV, -a(paramV), 0, f1);
      }
    case 3: 
      this.c = false;
      this.d = -1;
      paramCoordinatorLayout = this.g;
      if (paramCoordinatorLayout == null) {
        break label328;
      }
      paramCoordinatorLayout.recycle();
      this.g = null;
      break;
    }
    int i = (int)paramMotionEvent.getX();
    int j = (int)paramMotionEvent.getY();
    if ((paramCoordinatorLayout.a(paramV, i, j)) && (c(paramV)))
    {
      this.e = j;
      this.d = paramMotionEvent.getPointerId(0);
      d();
    }
    else
    {
      return false;
    }
    label328:
    paramCoordinatorLayout = this.g;
    if (paramCoordinatorLayout != null) {
      paramCoordinatorLayout.addMovement(paramMotionEvent);
    }
    return true;
  }
  
  boolean c(V paramV)
  {
    return false;
  }
  
  private class a
    implements Runnable
  {
    private final CoordinatorLayout b;
    private final V c;
    
    a(V paramV)
    {
      this.b = paramV;
      View localView;
      this.c = localView;
    }
    
    public void run()
    {
      if ((this.c != null) && (g.this.a != null)) {
        if (g.this.a.computeScrollOffset())
        {
          g localg = g.this;
          localg.a_(this.b, this.c, localg.a.getCurrY());
          ViewCompat.postOnAnimation(this.c, this);
        }
        else
        {
          g.this.a(this.b, this.c);
        }
      }
    }
  }
}


/* Location:              ~/android/support/design/widget/g.class
 *
 * Reversed by:           J
 */