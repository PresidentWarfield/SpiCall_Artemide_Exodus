package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v7.a.a.f;
import android.support.v7.a.a.j;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ButtonBarLayout
  extends LinearLayout
{
  private static final int ALLOW_STACKING_MIN_HEIGHT_DP = 320;
  private static final int PEEK_BUTTON_DP = 16;
  private boolean mAllowStacking;
  private int mLastWidthSize = -1;
  private int mMinimumHeight;
  
  public ButtonBarLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    boolean bool = false;
    this.mMinimumHeight = 0;
    if (getResources().getConfiguration().screenHeightDp >= 320) {
      bool = true;
    }
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, a.j.ButtonBarLayout);
    this.mAllowStacking = paramContext.getBoolean(a.j.ButtonBarLayout_allowStacking, bool);
    paramContext.recycle();
  }
  
  private int getNextVisibleChildIndex(int paramInt)
  {
    int i = getChildCount();
    while (paramInt < i)
    {
      if (getChildAt(paramInt).getVisibility() == 0) {
        return paramInt;
      }
      paramInt++;
    }
    return -1;
  }
  
  private boolean isStacked()
  {
    int i = getOrientation();
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  private void setStacked(boolean paramBoolean)
  {
    setOrientation(paramBoolean);
    int i;
    if (paramBoolean) {
      i = 5;
    } else {
      i = 80;
    }
    setGravity(i);
    View localView = findViewById(a.f.spacer);
    if (localView != null)
    {
      if (paramBoolean) {
        paramBoolean = true;
      } else {
        paramBoolean = true;
      }
      localView.setVisibility(paramBoolean);
    }
    for (paramBoolean = getChildCount() - 2; !paramBoolean; paramBoolean--) {
      bringChildToFront(getChildAt(paramBoolean));
    }
  }
  
  public int getMinimumHeight()
  {
    return Math.max(this.mMinimumHeight, super.getMinimumHeight());
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    boolean bool = this.mAllowStacking;
    int j = 0;
    if (bool)
    {
      if ((i > this.mLastWidthSize) && (isStacked())) {
        setStacked(false);
      }
      this.mLastWidthSize = i;
    }
    int k;
    if ((!isStacked()) && (View.MeasureSpec.getMode(paramInt1) == 1073741824))
    {
      k = View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE);
      i = 1;
    }
    else
    {
      k = paramInt1;
      i = 0;
    }
    super.onMeasure(k, paramInt2);
    int m = i;
    if (this.mAllowStacking)
    {
      m = i;
      if (!isStacked())
      {
        if ((getMeasuredWidthAndState() & 0xFF000000) == 16777216) {
          k = 1;
        } else {
          k = 0;
        }
        m = i;
        if (k != 0)
        {
          setStacked(true);
          m = 1;
        }
      }
    }
    if (m != 0) {
      super.onMeasure(paramInt1, paramInt2);
    }
    paramInt2 = getNextVisibleChildIndex(0);
    paramInt1 = j;
    if (paramInt2 >= 0)
    {
      View localView = getChildAt(paramInt2);
      LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)localView.getLayoutParams();
      paramInt1 = getPaddingTop() + localView.getMeasuredHeight() + localLayoutParams.topMargin + localLayoutParams.bottomMargin + 0;
      if (isStacked())
      {
        paramInt2 = getNextVisibleChildIndex(paramInt2 + 1);
        if (paramInt2 >= 0) {
          paramInt1 += getChildAt(paramInt2).getPaddingTop() + (int)(getResources().getDisplayMetrics().density * 16.0F);
        }
      }
      else
      {
        paramInt1 += getPaddingBottom();
      }
    }
    if (ViewCompat.getMinimumHeight(this) != paramInt1) {
      setMinimumHeight(paramInt1);
    }
  }
  
  public void setAllowStacking(boolean paramBoolean)
  {
    if (this.mAllowStacking != paramBoolean)
    {
      this.mAllowStacking = paramBoolean;
      if ((!this.mAllowStacking) && (getOrientation() == 1)) {
        setStacked(false);
      }
      requestLayout();
    }
  }
}


/* Location:              ~/android/support/v7/widget/ButtonBarLayout.class
 *
 * Reversed by:           J
 */