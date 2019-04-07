package android.support.design.widget;

import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class SwipeDismissBehavior<V extends View>
  extends CoordinatorLayout.a<V>
{
  private boolean a;
  ViewDragHelper b;
  a c;
  int d = 2;
  float e = 0.5F;
  float f = 0.0F;
  float g = 0.5F;
  private float h = 0.0F;
  private boolean i;
  private final ViewDragHelper.Callback j = new ViewDragHelper.Callback()
  {
    private int b;
    private int c = -1;
    
    private boolean a(View paramAnonymousView, float paramAnonymousFloat)
    {
      boolean bool1 = false;
      boolean bool2 = false;
      boolean bool3 = false;
      if (paramAnonymousFloat != 0.0F)
      {
        if (ViewCompat.getLayoutDirection(paramAnonymousView) == 1) {
          i = 1;
        } else {
          i = 0;
        }
        if (SwipeDismissBehavior.this.d == 2) {
          return true;
        }
        if (SwipeDismissBehavior.this.d == 0)
        {
          if (i != 0 ? paramAnonymousFloat < 0.0F : paramAnonymousFloat > 0.0F) {
            bool3 = true;
          }
          return bool3;
        }
        if (SwipeDismissBehavior.this.d == 1)
        {
          if (i != 0)
          {
            bool3 = bool1;
            if (paramAnonymousFloat <= 0.0F) {
              break label120;
            }
          }
          else
          {
            bool3 = bool1;
            if (paramAnonymousFloat >= 0.0F) {
              break label120;
            }
          }
          bool3 = true;
          label120:
          return bool3;
        }
        return false;
      }
      int j = paramAnonymousView.getLeft();
      int i = this.b;
      int k = Math.round(paramAnonymousView.getWidth() * SwipeDismissBehavior.this.e);
      bool3 = bool2;
      if (Math.abs(j - i) >= k) {
        bool3 = true;
      }
      return bool3;
    }
    
    public int clampViewPositionHorizontal(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      if (ViewCompat.getLayoutDirection(paramAnonymousView) == 1) {
        paramAnonymousInt2 = 1;
      } else {
        paramAnonymousInt2 = 0;
      }
      int i;
      if (SwipeDismissBehavior.this.d == 0)
      {
        if (paramAnonymousInt2 != 0)
        {
          i = this.b - paramAnonymousView.getWidth();
          paramAnonymousInt2 = this.b;
        }
        else
        {
          i = this.b;
          paramAnonymousInt2 = paramAnonymousView.getWidth() + i;
        }
      }
      else if (SwipeDismissBehavior.this.d == 1)
      {
        if (paramAnonymousInt2 != 0)
        {
          i = this.b;
          paramAnonymousInt2 = paramAnonymousView.getWidth() + i;
        }
        else
        {
          i = this.b - paramAnonymousView.getWidth();
          paramAnonymousInt2 = this.b;
        }
      }
      else
      {
        i = this.b - paramAnonymousView.getWidth();
        paramAnonymousInt2 = this.b;
        paramAnonymousInt2 = paramAnonymousView.getWidth() + paramAnonymousInt2;
      }
      return SwipeDismissBehavior.a(i, paramAnonymousInt1, paramAnonymousInt2);
    }
    
    public int clampViewPositionVertical(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousView.getTop();
    }
    
    public int getViewHorizontalDragRange(View paramAnonymousView)
    {
      return paramAnonymousView.getWidth();
    }
    
    public void onViewCaptured(View paramAnonymousView, int paramAnonymousInt)
    {
      this.c = paramAnonymousInt;
      this.b = paramAnonymousView.getLeft();
      paramAnonymousView = paramAnonymousView.getParent();
      if (paramAnonymousView != null) {
        paramAnonymousView.requestDisallowInterceptTouchEvent(true);
      }
    }
    
    public void onViewDragStateChanged(int paramAnonymousInt)
    {
      if (SwipeDismissBehavior.this.c != null) {
        SwipeDismissBehavior.this.c.a(paramAnonymousInt);
      }
    }
    
    public void onViewPositionChanged(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
    {
      float f1 = this.b + paramAnonymousView.getWidth() * SwipeDismissBehavior.this.f;
      float f2 = this.b + paramAnonymousView.getWidth() * SwipeDismissBehavior.this.g;
      float f3 = paramAnonymousInt1;
      if (f3 <= f1) {
        paramAnonymousView.setAlpha(1.0F);
      } else if (f3 >= f2) {
        paramAnonymousView.setAlpha(0.0F);
      } else {
        paramAnonymousView.setAlpha(SwipeDismissBehavior.a(0.0F, 1.0F - SwipeDismissBehavior.b(f1, f2, f3), 1.0F));
      }
    }
    
    public void onViewReleased(View paramAnonymousView, float paramAnonymousFloat1, float paramAnonymousFloat2)
    {
      this.c = -1;
      int i = paramAnonymousView.getWidth();
      boolean bool;
      if (a(paramAnonymousView, paramAnonymousFloat1))
      {
        int j = paramAnonymousView.getLeft();
        int k = this.b;
        if (j < k) {
          i = k - i;
        } else {
          i = k + i;
        }
        bool = true;
      }
      else
      {
        i = this.b;
        bool = false;
      }
      if (SwipeDismissBehavior.this.b.settleCapturedViewAt(i, paramAnonymousView.getTop())) {
        ViewCompat.postOnAnimation(paramAnonymousView, new SwipeDismissBehavior.b(SwipeDismissBehavior.this, paramAnonymousView, bool));
      } else if ((bool) && (SwipeDismissBehavior.this.c != null)) {
        SwipeDismissBehavior.this.c.a(paramAnonymousView);
      }
    }
    
    public boolean tryCaptureView(View paramAnonymousView, int paramAnonymousInt)
    {
      boolean bool;
      if ((this.c == -1) && (SwipeDismissBehavior.this.a(paramAnonymousView))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  };
  
  static float a(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return Math.min(Math.max(paramFloat1, paramFloat2), paramFloat3);
  }
  
  static int a(int paramInt1, int paramInt2, int paramInt3)
  {
    return Math.min(Math.max(paramInt1, paramInt2), paramInt3);
  }
  
  private void a(ViewGroup paramViewGroup)
  {
    if (this.b == null)
    {
      if (this.i) {
        paramViewGroup = ViewDragHelper.create(paramViewGroup, this.h, this.j);
      } else {
        paramViewGroup = ViewDragHelper.create(paramViewGroup, this.j);
      }
      this.b = paramViewGroup;
    }
  }
  
  static float b(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return (paramFloat3 - paramFloat1) / (paramFloat2 - paramFloat1);
  }
  
  public void a(float paramFloat)
  {
    this.f = a(0.0F, paramFloat, 1.0F);
  }
  
  public void a(int paramInt)
  {
    this.d = paramInt;
  }
  
  public void a(a parama)
  {
    this.c = parama;
  }
  
  public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
  {
    boolean bool = this.a;
    int k = paramMotionEvent.getActionMasked();
    if (k != 3) {
      switch (k)
      {
      default: 
        break;
      case 0: 
        this.a = paramCoordinatorLayout.a(paramV, (int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
        bool = this.a;
        break;
      }
    } else {
      this.a = false;
    }
    if (bool)
    {
      a(paramCoordinatorLayout);
      return this.b.shouldInterceptTouchEvent(paramMotionEvent);
    }
    return false;
  }
  
  public boolean a(View paramView)
  {
    return true;
  }
  
  public void b(float paramFloat)
  {
    this.g = a(0.0F, paramFloat, 1.0F);
  }
  
  public boolean b(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
  {
    paramCoordinatorLayout = this.b;
    if (paramCoordinatorLayout != null)
    {
      paramCoordinatorLayout.processTouchEvent(paramMotionEvent);
      return true;
    }
    return false;
  }
  
  public static abstract interface a
  {
    public abstract void a(int paramInt);
    
    public abstract void a(View paramView);
  }
  
  private class b
    implements Runnable
  {
    private final View b;
    private final boolean c;
    
    b(View paramView, boolean paramBoolean)
    {
      this.b = paramView;
      this.c = paramBoolean;
    }
    
    public void run()
    {
      if ((SwipeDismissBehavior.this.b != null) && (SwipeDismissBehavior.this.b.continueSettling(true))) {
        ViewCompat.postOnAnimation(this.b, this);
      } else if ((this.c) && (SwipeDismissBehavior.this.c != null)) {
        SwipeDismissBehavior.this.c.a(this.b);
      }
    }
  }
}


/* Location:              ~/android/support/design/widget/SwipeDismissBehavior.class
 *
 * Reversed by:           J
 */