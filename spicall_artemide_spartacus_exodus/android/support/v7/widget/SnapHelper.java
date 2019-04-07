package android.support.v7.widget;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

public abstract class SnapHelper
  extends RecyclerView.OnFlingListener
{
  static final float MILLISECONDS_PER_INCH = 100.0F;
  private Scroller mGravityScroller;
  RecyclerView mRecyclerView;
  private final RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener()
  {
    boolean a = false;
    
    public void onScrollStateChanged(RecyclerView paramAnonymousRecyclerView, int paramAnonymousInt)
    {
      super.onScrollStateChanged(paramAnonymousRecyclerView, paramAnonymousInt);
      if ((paramAnonymousInt == 0) && (this.a))
      {
        this.a = false;
        SnapHelper.this.snapToTargetExistingView();
      }
    }
    
    public void onScrolled(RecyclerView paramAnonymousRecyclerView, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      if ((paramAnonymousInt1 != 0) || (paramAnonymousInt2 != 0)) {
        this.a = true;
      }
    }
  };
  
  private void destroyCallbacks()
  {
    this.mRecyclerView.removeOnScrollListener(this.mScrollListener);
    this.mRecyclerView.setOnFlingListener(null);
  }
  
  private void setupCallbacks()
  {
    if (this.mRecyclerView.getOnFlingListener() == null)
    {
      this.mRecyclerView.addOnScrollListener(this.mScrollListener);
      this.mRecyclerView.setOnFlingListener(this);
      return;
    }
    throw new IllegalStateException("An instance of OnFlingListener already set.");
  }
  
  private boolean snapFromFling(RecyclerView.LayoutManager paramLayoutManager, int paramInt1, int paramInt2)
  {
    if (!(paramLayoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
      return false;
    }
    RecyclerView.SmoothScroller localSmoothScroller = createScroller(paramLayoutManager);
    if (localSmoothScroller == null) {
      return false;
    }
    paramInt1 = findTargetSnapPosition(paramLayoutManager, paramInt1, paramInt2);
    if (paramInt1 == -1) {
      return false;
    }
    localSmoothScroller.setTargetPosition(paramInt1);
    paramLayoutManager.startSmoothScroll(localSmoothScroller);
    return true;
  }
  
  public void attachToRecyclerView(RecyclerView paramRecyclerView)
  {
    RecyclerView localRecyclerView = this.mRecyclerView;
    if (localRecyclerView == paramRecyclerView) {
      return;
    }
    if (localRecyclerView != null) {
      destroyCallbacks();
    }
    this.mRecyclerView = paramRecyclerView;
    if (this.mRecyclerView != null)
    {
      setupCallbacks();
      this.mGravityScroller = new Scroller(this.mRecyclerView.getContext(), new DecelerateInterpolator());
      snapToTargetExistingView();
    }
  }
  
  public abstract int[] calculateDistanceToFinalSnap(RecyclerView.LayoutManager paramLayoutManager, View paramView);
  
  public int[] calculateScrollDistance(int paramInt1, int paramInt2)
  {
    this.mGravityScroller.fling(0, 0, paramInt1, paramInt2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
    return new int[] { this.mGravityScroller.getFinalX(), this.mGravityScroller.getFinalY() };
  }
  
  protected RecyclerView.SmoothScroller createScroller(RecyclerView.LayoutManager paramLayoutManager)
  {
    return createSnapScroller(paramLayoutManager);
  }
  
  @Deprecated
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
      
      protected void onTargetFound(View paramAnonymousView, RecyclerView.State paramAnonymousState, RecyclerView.SmoothScroller.Action paramAnonymousAction)
      {
        paramAnonymousState = SnapHelper.this;
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
  
  public abstract View findSnapView(RecyclerView.LayoutManager paramLayoutManager);
  
  public abstract int findTargetSnapPosition(RecyclerView.LayoutManager paramLayoutManager, int paramInt1, int paramInt2);
  
  public boolean onFling(int paramInt1, int paramInt2)
  {
    RecyclerView.LayoutManager localLayoutManager = this.mRecyclerView.getLayoutManager();
    boolean bool1 = false;
    if (localLayoutManager == null) {
      return false;
    }
    if (this.mRecyclerView.getAdapter() == null) {
      return false;
    }
    int i = this.mRecyclerView.getMinFlingVelocity();
    boolean bool2;
    if (Math.abs(paramInt2) <= i)
    {
      bool2 = bool1;
      if (Math.abs(paramInt1) <= i) {}
    }
    else
    {
      bool2 = bool1;
      if (snapFromFling(localLayoutManager, paramInt1, paramInt2)) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  void snapToTargetExistingView()
  {
    Object localObject = this.mRecyclerView;
    if (localObject == null) {
      return;
    }
    localObject = ((RecyclerView)localObject).getLayoutManager();
    if (localObject == null) {
      return;
    }
    View localView = findSnapView((RecyclerView.LayoutManager)localObject);
    if (localView == null) {
      return;
    }
    localObject = calculateDistanceToFinalSnap((RecyclerView.LayoutManager)localObject, localView);
    if ((localObject[0] != 0) || (localObject[1] != 0)) {
      this.mRecyclerView.smoothScrollBy(localObject[0], localObject[1]);
    }
  }
}


/* Location:              ~/android/support/v7/widget/SnapHelper.class
 *
 * Reversed by:           J
 */