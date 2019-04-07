package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;

public class PagerSnapHelper
  extends SnapHelper
{
  private static final int MAX_SCROLL_ON_FLING_DURATION = 100;
  private OrientationHelper mHorizontalHelper;
  private OrientationHelper mVerticalHelper;
  
  private int distanceToCenter(RecyclerView.LayoutManager paramLayoutManager, View paramView, OrientationHelper paramOrientationHelper)
  {
    int i = paramOrientationHelper.getDecoratedStart(paramView);
    int j = paramOrientationHelper.getDecoratedMeasurement(paramView) / 2;
    int k;
    if (paramLayoutManager.getClipToPadding()) {
      k = paramOrientationHelper.getStartAfterPadding() + paramOrientationHelper.getTotalSpace() / 2;
    } else {
      k = paramOrientationHelper.getEnd() / 2;
    }
    return i + j - k;
  }
  
  private View findCenterView(RecyclerView.LayoutManager paramLayoutManager, OrientationHelper paramOrientationHelper)
  {
    int i = paramLayoutManager.getChildCount();
    Object localObject = null;
    if (i == 0) {
      return null;
    }
    int j;
    if (paramLayoutManager.getClipToPadding()) {
      j = paramOrientationHelper.getStartAfterPadding() + paramOrientationHelper.getTotalSpace() / 2;
    } else {
      j = paramOrientationHelper.getEnd() / 2;
    }
    int k = Integer.MAX_VALUE;
    int m = 0;
    while (m < i)
    {
      View localView = paramLayoutManager.getChildAt(m);
      int n = Math.abs(paramOrientationHelper.getDecoratedStart(localView) + paramOrientationHelper.getDecoratedMeasurement(localView) / 2 - j);
      int i1 = k;
      if (n < k)
      {
        localObject = localView;
        i1 = n;
      }
      m++;
      k = i1;
    }
    return (View)localObject;
  }
  
  private View findStartView(RecyclerView.LayoutManager paramLayoutManager, OrientationHelper paramOrientationHelper)
  {
    int i = paramLayoutManager.getChildCount();
    Object localObject = null;
    if (i == 0) {
      return null;
    }
    int j = Integer.MAX_VALUE;
    int k = 0;
    while (k < i)
    {
      View localView = paramLayoutManager.getChildAt(k);
      int m = paramOrientationHelper.getDecoratedStart(localView);
      int n = j;
      if (m < j)
      {
        localObject = localView;
        n = m;
      }
      k++;
      j = n;
    }
    return (View)localObject;
  }
  
  private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager paramLayoutManager)
  {
    OrientationHelper localOrientationHelper = this.mHorizontalHelper;
    if ((localOrientationHelper == null) || (localOrientationHelper.mLayoutManager != paramLayoutManager)) {
      this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(paramLayoutManager);
    }
    return this.mHorizontalHelper;
  }
  
  private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager paramLayoutManager)
  {
    OrientationHelper localOrientationHelper = this.mVerticalHelper;
    if ((localOrientationHelper == null) || (localOrientationHelper.mLayoutManager != paramLayoutManager)) {
      this.mVerticalHelper = OrientationHelper.createVerticalHelper(paramLayoutManager);
    }
    return this.mVerticalHelper;
  }
  
  public int[] calculateDistanceToFinalSnap(RecyclerView.LayoutManager paramLayoutManager, View paramView)
  {
    int[] arrayOfInt = new int[2];
    if (paramLayoutManager.canScrollHorizontally()) {
      arrayOfInt[0] = distanceToCenter(paramLayoutManager, paramView, getHorizontalHelper(paramLayoutManager));
    } else {
      arrayOfInt[0] = 0;
    }
    if (paramLayoutManager.canScrollVertically()) {
      arrayOfInt[1] = distanceToCenter(paramLayoutManager, paramView, getVerticalHelper(paramLayoutManager));
    } else {
      arrayOfInt[1] = 0;
    }
    return arrayOfInt;
  }
  
  protected LinearSmoothScroller createSnapScroller(RecyclerView.LayoutManager paramLayoutManager)
  {
    if (!(paramLayoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
      return null;
    }
    new LinearSmoothScroller(this.mRecyclerView.getContext())
    {
      protected float calculateSpeedPerPixel(DisplayMetrics paramAnonymousDisplayMetrics)
      {
        return 100.0F / paramAnonymousDisplayMetrics.densityDpi;
      }
      
      protected int calculateTimeForScrolling(int paramAnonymousInt)
      {
        return Math.min(100, super.calculateTimeForScrolling(paramAnonymousInt));
      }
      
      protected void onTargetFound(View paramAnonymousView, RecyclerView.State paramAnonymousState, RecyclerView.SmoothScroller.Action paramAnonymousAction)
      {
        paramAnonymousState = PagerSnapHelper.this;
        paramAnonymousView = paramAnonymousState.calculateDistanceToFinalSnap(paramAnonymousState.mRecyclerView.getLayoutManager(), paramAnonymousView);
        int i = paramAnonymousView[0];
        int j = paramAnonymousView[1];
        int k = calculateTimeForDeceleration(Math.max(Math.abs(i), Math.abs(j)));
        if (k > 0) {
          paramAnonymousAction.update(i, j, k, this.mDecelerateInterpolator);
        }
      }
    };
  }
  
  public View findSnapView(RecyclerView.LayoutManager paramLayoutManager)
  {
    if (paramLayoutManager.canScrollVertically()) {
      return findCenterView(paramLayoutManager, getVerticalHelper(paramLayoutManager));
    }
    if (paramLayoutManager.canScrollHorizontally()) {
      return findCenterView(paramLayoutManager, getHorizontalHelper(paramLayoutManager));
    }
    return null;
  }
  
  public int findTargetSnapPosition(RecyclerView.LayoutManager paramLayoutManager, int paramInt1, int paramInt2)
  {
    int i = paramLayoutManager.getItemCount();
    if (i == 0) {
      return -1;
    }
    View localView = null;
    if (paramLayoutManager.canScrollVertically()) {
      localView = findStartView(paramLayoutManager, getVerticalHelper(paramLayoutManager));
    } else if (paramLayoutManager.canScrollHorizontally()) {
      localView = findStartView(paramLayoutManager, getHorizontalHelper(paramLayoutManager));
    }
    if (localView == null) {
      return -1;
    }
    int j = paramLayoutManager.getPosition(localView);
    if (j == -1) {
      return -1;
    }
    boolean bool = paramLayoutManager.canScrollHorizontally();
    int k = 0;
    if (bool)
    {
      if (paramInt1 > 0) {
        paramInt1 = 1;
      } else {
        paramInt1 = 0;
      }
    }
    else if (paramInt2 > 0) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    }
    paramInt2 = k;
    if ((paramLayoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider))
    {
      paramLayoutManager = ((RecyclerView.SmoothScroller.ScrollVectorProvider)paramLayoutManager).computeScrollVectorForPosition(i - 1);
      paramInt2 = k;
      if (paramLayoutManager != null) {
        if (paramLayoutManager.x >= 0.0F)
        {
          paramInt2 = k;
          if (paramLayoutManager.y >= 0.0F) {}
        }
        else
        {
          paramInt2 = 1;
        }
      }
    }
    if (paramInt2 != 0)
    {
      paramInt2 = j;
      if (paramInt1 != 0) {
        paramInt2 = j - 1;
      }
    }
    else
    {
      paramInt2 = j;
      if (paramInt1 != 0) {
        paramInt2 = j + 1;
      }
    }
    return paramInt2;
  }
}


/* Location:              ~/android/support/v7/widget/PagerSnapHelper.class
 *
 * Reversed by:           J
 */