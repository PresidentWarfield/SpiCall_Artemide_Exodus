package android.support.design.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class BaselineLayout
  extends ViewGroup
{
  private int a = -1;
  
  public BaselineLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 0);
  }
  
  public int getBaseline()
  {
    return this.a;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getChildCount();
    int j = getPaddingLeft();
    int k = getPaddingRight();
    int m = getPaddingTop();
    for (paramInt2 = 0; paramInt2 < i; paramInt2++)
    {
      View localView = getChildAt(paramInt2);
      if (localView.getVisibility() != 8)
      {
        int n = localView.getMeasuredWidth();
        int i1 = localView.getMeasuredHeight();
        int i2 = (paramInt3 - paramInt1 - k - j - n) / 2 + j;
        if ((this.a != -1) && (localView.getBaseline() != -1)) {
          paramInt4 = this.a + m - localView.getBaseline();
        } else {
          paramInt4 = m;
        }
        localView.layout(i2, paramInt4, n + i2, i1 + paramInt4);
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = getChildCount();
    int j = 0;
    int k = -1;
    int m = -1;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    while (j < i)
    {
      View localView = getChildAt(j);
      if (localView.getVisibility() == 8)
      {
        i3 = k;
      }
      else
      {
        measureChild(localView, paramInt1, paramInt2);
        int i4 = localView.getBaseline();
        i3 = k;
        int i5 = m;
        if (i4 != -1)
        {
          i3 = Math.max(k, i4);
          i5 = Math.max(m, localView.getMeasuredHeight() - i4);
        }
        i1 = Math.max(i1, localView.getMeasuredWidth());
        n = Math.max(n, localView.getMeasuredHeight());
        i2 = View.combineMeasuredStates(i2, localView.getMeasuredState());
        m = i5;
      }
      j++;
      k = i3;
    }
    int i3 = n;
    if (k != -1)
    {
      i3 = Math.max(n, Math.max(m, getPaddingBottom()) + k);
      this.a = k;
    }
    n = Math.max(i3, getSuggestedMinimumHeight());
    setMeasuredDimension(View.resolveSizeAndState(Math.max(i1, getSuggestedMinimumWidth()), paramInt1, i2), View.resolveSizeAndState(n, paramInt2, i2 << 16));
  }
}


/* Location:              ~/android/support/design/internal/BaselineLayout.class
 *
 * Reversed by:           J
 */