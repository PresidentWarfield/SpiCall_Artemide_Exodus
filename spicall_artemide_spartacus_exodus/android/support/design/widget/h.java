package android.support.design.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.math.MathUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import java.util.List;

abstract class h
  extends o<View>
{
  final Rect a = new Rect();
  final Rect b = new Rect();
  private int c = 0;
  private int d;
  
  public h() {}
  
  public h(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private static int c(int paramInt)
  {
    int i = paramInt;
    if (paramInt == 0) {
      i = 8388659;
    }
    return i;
  }
  
  float a(View paramView)
  {
    return 1.0F;
  }
  
  final int a()
  {
    return this.c;
  }
  
  public boolean a(CoordinatorLayout paramCoordinatorLayout, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramView.getLayoutParams().height;
    if ((i == -1) || (i == -2))
    {
      View localView = b(paramCoordinatorLayout.c(paramView));
      if (localView != null)
      {
        if (ViewCompat.getFitsSystemWindows(localView)) {
          if (!ViewCompat.getFitsSystemWindows(paramView))
          {
            ViewCompat.setFitsSystemWindows(paramView, true);
            if (ViewCompat.getFitsSystemWindows(paramView))
            {
              paramView.requestLayout();
              return true;
            }
          }
          else {}
        }
        int j = View.MeasureSpec.getSize(paramInt3);
        paramInt3 = j;
        if (j == 0) {
          paramInt3 = paramCoordinatorLayout.getHeight();
        }
        int k = localView.getMeasuredHeight();
        int m = b(localView);
        if (i == -1) {
          j = 1073741824;
        } else {
          j = Integer.MIN_VALUE;
        }
        paramCoordinatorLayout.a(paramView, paramInt1, paramInt2, View.MeasureSpec.makeMeasureSpec(paramInt3 - k + m, j), paramInt4);
        return true;
      }
    }
    return false;
  }
  
  int b(View paramView)
  {
    return paramView.getMeasuredHeight();
  }
  
  abstract View b(List<View> paramList);
  
  public final void b(int paramInt)
  {
    this.d = paramInt;
  }
  
  protected void b(CoordinatorLayout paramCoordinatorLayout, View paramView, int paramInt)
  {
    View localView = b(paramCoordinatorLayout.c(paramView));
    if (localView != null)
    {
      CoordinatorLayout.d locald = (CoordinatorLayout.d)paramView.getLayoutParams();
      Rect localRect = this.a;
      localRect.set(paramCoordinatorLayout.getPaddingLeft() + locald.leftMargin, localView.getBottom() + locald.topMargin, paramCoordinatorLayout.getWidth() - paramCoordinatorLayout.getPaddingRight() - locald.rightMargin, paramCoordinatorLayout.getHeight() + localView.getBottom() - paramCoordinatorLayout.getPaddingBottom() - locald.bottomMargin);
      WindowInsetsCompat localWindowInsetsCompat = paramCoordinatorLayout.getLastWindowInsets();
      if ((localWindowInsetsCompat != null) && (ViewCompat.getFitsSystemWindows(paramCoordinatorLayout)) && (!ViewCompat.getFitsSystemWindows(paramView)))
      {
        localRect.left += localWindowInsetsCompat.getSystemWindowInsetLeft();
        localRect.right -= localWindowInsetsCompat.getSystemWindowInsetRight();
      }
      paramCoordinatorLayout = this.b;
      GravityCompat.apply(c(locald.c), paramView.getMeasuredWidth(), paramView.getMeasuredHeight(), localRect, paramCoordinatorLayout, paramInt);
      paramInt = c(localView);
      paramView.layout(paramCoordinatorLayout.left, paramCoordinatorLayout.top - paramInt, paramCoordinatorLayout.right, paramCoordinatorLayout.bottom - paramInt);
      this.c = (paramCoordinatorLayout.top - localView.getBottom());
    }
    else
    {
      super.b(paramCoordinatorLayout, paramView, paramInt);
      this.c = 0;
    }
  }
  
  final int c(View paramView)
  {
    int i = this.d;
    int j = 0;
    if (i != 0)
    {
      float f = a(paramView);
      j = this.d;
      j = MathUtils.clamp((int)(f * j), 0, j);
    }
    return j;
  }
  
  public final int d()
  {
    return this.d;
  }
}


/* Location:              ~/android/support/design/widget/h.class
 *
 * Reversed by:           J
 */