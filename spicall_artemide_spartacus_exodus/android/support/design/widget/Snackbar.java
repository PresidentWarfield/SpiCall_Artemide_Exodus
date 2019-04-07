package android.support.design.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;

public final class Snackbar
  extends b<Snackbar>
{
  public static final class SnackbarLayout
    extends b.f
  {
    public SnackbarLayout(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      super.onMeasure(paramInt1, paramInt2);
      int i = getChildCount();
      int j = getMeasuredWidth();
      int k = getPaddingLeft();
      paramInt2 = getPaddingRight();
      for (paramInt1 = 0; paramInt1 < i; paramInt1++)
      {
        View localView = getChildAt(paramInt1);
        if (localView.getLayoutParams().width == -1) {
          localView.measure(View.MeasureSpec.makeMeasureSpec(j - k - paramInt2, 1073741824), View.MeasureSpec.makeMeasureSpec(localView.getMeasuredHeight(), 1073741824));
        }
      }
    }
  }
}


/* Location:              ~/android/support/design/widget/Snackbar.class
 *
 * Reversed by:           J
 */