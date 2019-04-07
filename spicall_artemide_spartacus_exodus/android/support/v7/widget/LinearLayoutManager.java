package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v7.widget.helper.ItemTouchHelper.ViewDropHandler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

public class LinearLayoutManager
  extends RecyclerView.LayoutManager
  implements RecyclerView.SmoothScroller.ScrollVectorProvider, ItemTouchHelper.ViewDropHandler
{
  static final boolean DEBUG = false;
  public static final int HORIZONTAL = 0;
  public static final int INVALID_OFFSET = Integer.MIN_VALUE;
  private static final float MAX_SCROLL_FACTOR = 0.33333334F;
  private static final String TAG = "LinearLayoutManager";
  public static final int VERTICAL = 1;
  final a mAnchorInfo = new a();
  private int mInitialPrefetchItemCount = 2;
  private boolean mLastStackFromEnd;
  private final LayoutChunkResult mLayoutChunkResult = new LayoutChunkResult();
  private b mLayoutState;
  int mOrientation;
  OrientationHelper mOrientationHelper;
  SavedState mPendingSavedState = null;
  int mPendingScrollPosition = -1;
  int mPendingScrollPositionOffset = Integer.MIN_VALUE;
  private boolean mRecycleChildrenOnDetach;
  private boolean mReverseLayout = false;
  boolean mShouldReverseLayout = false;
  private boolean mSmoothScrollbarEnabled = true;
  private boolean mStackFromEnd = false;
  
  public LinearLayoutManager(Context paramContext)
  {
    this(paramContext, 1, false);
  }
  
  public LinearLayoutManager(Context paramContext, int paramInt, boolean paramBoolean)
  {
    setOrientation(paramInt);
    setReverseLayout(paramBoolean);
    setAutoMeasureEnabled(true);
  }
  
  public LinearLayoutManager(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    paramContext = getProperties(paramContext, paramAttributeSet, paramInt1, paramInt2);
    setOrientation(paramContext.orientation);
    setReverseLayout(paramContext.reverseLayout);
    setStackFromEnd(paramContext.stackFromEnd);
    setAutoMeasureEnabled(true);
  }
  
  private int computeScrollExtent(RecyclerView.State paramState)
  {
    if (getChildCount() == 0) {
      return 0;
    }
    ensureLayoutState();
    return ab.a(paramState, this.mOrientationHelper, findFirstVisibleChildClosestToStart(this.mSmoothScrollbarEnabled ^ true, true), findFirstVisibleChildClosestToEnd(this.mSmoothScrollbarEnabled ^ true, true), this, this.mSmoothScrollbarEnabled);
  }
  
  private int computeScrollOffset(RecyclerView.State paramState)
  {
    if (getChildCount() == 0) {
      return 0;
    }
    ensureLayoutState();
    return ab.a(paramState, this.mOrientationHelper, findFirstVisibleChildClosestToStart(this.mSmoothScrollbarEnabled ^ true, true), findFirstVisibleChildClosestToEnd(this.mSmoothScrollbarEnabled ^ true, true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
  }
  
  private int computeScrollRange(RecyclerView.State paramState)
  {
    if (getChildCount() == 0) {
      return 0;
    }
    ensureLayoutState();
    return ab.b(paramState, this.mOrientationHelper, findFirstVisibleChildClosestToStart(this.mSmoothScrollbarEnabled ^ true, true), findFirstVisibleChildClosestToEnd(this.mSmoothScrollbarEnabled ^ true, true), this, this.mSmoothScrollbarEnabled);
  }
  
  private View findFirstPartiallyOrCompletelyInvisibleChild(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    return findOnePartiallyOrCompletelyInvisibleChild(0, getChildCount());
  }
  
  private View findFirstReferenceChild(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    return findReferenceChild(paramRecycler, paramState, 0, getChildCount(), paramState.getItemCount());
  }
  
  private View findFirstVisibleChildClosestToEnd(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mShouldReverseLayout) {
      return findOneVisibleChild(0, getChildCount(), paramBoolean1, paramBoolean2);
    }
    return findOneVisibleChild(getChildCount() - 1, -1, paramBoolean1, paramBoolean2);
  }
  
  private View findFirstVisibleChildClosestToStart(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mShouldReverseLayout) {
      return findOneVisibleChild(getChildCount() - 1, -1, paramBoolean1, paramBoolean2);
    }
    return findOneVisibleChild(0, getChildCount(), paramBoolean1, paramBoolean2);
  }
  
  private View findLastPartiallyOrCompletelyInvisibleChild(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    return findOnePartiallyOrCompletelyInvisibleChild(getChildCount() - 1, -1);
  }
  
  private View findLastReferenceChild(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    return findReferenceChild(paramRecycler, paramState, getChildCount() - 1, -1, paramState.getItemCount());
  }
  
  private View findPartiallyOrCompletelyInvisibleChildClosestToEnd(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mShouldReverseLayout) {
      paramRecycler = findFirstPartiallyOrCompletelyInvisibleChild(paramRecycler, paramState);
    } else {
      paramRecycler = findLastPartiallyOrCompletelyInvisibleChild(paramRecycler, paramState);
    }
    return paramRecycler;
  }
  
  private View findPartiallyOrCompletelyInvisibleChildClosestToStart(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mShouldReverseLayout) {
      paramRecycler = findLastPartiallyOrCompletelyInvisibleChild(paramRecycler, paramState);
    } else {
      paramRecycler = findFirstPartiallyOrCompletelyInvisibleChild(paramRecycler, paramState);
    }
    return paramRecycler;
  }
  
  private View findReferenceChildClosestToEnd(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mShouldReverseLayout) {
      paramRecycler = findFirstReferenceChild(paramRecycler, paramState);
    } else {
      paramRecycler = findLastReferenceChild(paramRecycler, paramState);
    }
    return paramRecycler;
  }
  
  private View findReferenceChildClosestToStart(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mShouldReverseLayout) {
      paramRecycler = findLastReferenceChild(paramRecycler, paramState);
    } else {
      paramRecycler = findFirstReferenceChild(paramRecycler, paramState);
    }
    return paramRecycler;
  }
  
  private int fixLayoutEndGap(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean)
  {
    int i = this.mOrientationHelper.getEndAfterPadding() - paramInt;
    if (i > 0)
    {
      i = -scrollBy(-i, paramRecycler, paramState);
      if (paramBoolean)
      {
        paramInt = this.mOrientationHelper.getEndAfterPadding() - (paramInt + i);
        if (paramInt > 0)
        {
          this.mOrientationHelper.offsetChildren(paramInt);
          return paramInt + i;
        }
      }
      return i;
    }
    return 0;
  }
  
  private int fixLayoutStartGap(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean)
  {
    int i = paramInt - this.mOrientationHelper.getStartAfterPadding();
    if (i > 0)
    {
      i = -scrollBy(i, paramRecycler, paramState);
      if (paramBoolean)
      {
        paramInt = paramInt + i - this.mOrientationHelper.getStartAfterPadding();
        if (paramInt > 0)
        {
          this.mOrientationHelper.offsetChildren(-paramInt);
          return i - paramInt;
        }
      }
      return i;
    }
    return 0;
  }
  
  private View getChildClosestToEnd()
  {
    int i;
    if (this.mShouldReverseLayout) {
      i = 0;
    } else {
      i = getChildCount() - 1;
    }
    return getChildAt(i);
  }
  
  private View getChildClosestToStart()
  {
    int i;
    if (this.mShouldReverseLayout) {
      i = getChildCount() - 1;
    } else {
      i = 0;
    }
    return getChildAt(i);
  }
  
  private void layoutForPredictiveAnimations(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2)
  {
    if ((paramState.willRunPredictiveAnimations()) && (getChildCount() != 0) && (!paramState.isPreLayout()) && (supportsPredictiveItemAnimations()))
    {
      Object localObject = paramRecycler.getScrapList();
      int i = ((List)localObject).size();
      int j = getPosition(getChildAt(0));
      int k = 0;
      int m = 0;
      int n = 0;
      while (k < i)
      {
        RecyclerView.ViewHolder localViewHolder = (RecyclerView.ViewHolder)((List)localObject).get(k);
        if (!localViewHolder.isRemoved())
        {
          int i1 = localViewHolder.getLayoutPosition();
          int i2 = 1;
          int i3;
          if (i1 < j) {
            i3 = 1;
          } else {
            i3 = 0;
          }
          if (i3 != this.mShouldReverseLayout) {
            i2 = -1;
          }
          if (i2 == -1) {
            m += this.mOrientationHelper.getDecoratedMeasurement(localViewHolder.itemView);
          } else {
            n += this.mOrientationHelper.getDecoratedMeasurement(localViewHolder.itemView);
          }
        }
        k++;
      }
      this.mLayoutState.k = ((List)localObject);
      if (m > 0)
      {
        updateLayoutStateToFillStart(getPosition(getChildClosestToStart()), paramInt1);
        localObject = this.mLayoutState;
        ((b)localObject).h = m;
        ((b)localObject).c = 0;
        ((b)localObject).a();
        fill(paramRecycler, this.mLayoutState, paramState, false);
      }
      if (n > 0)
      {
        updateLayoutStateToFillEnd(getPosition(getChildClosestToEnd()), paramInt2);
        localObject = this.mLayoutState;
        ((b)localObject).h = n;
        ((b)localObject).c = 0;
        ((b)localObject).a();
        fill(paramRecycler, this.mLayoutState, paramState, false);
      }
      this.mLayoutState.k = null;
      return;
    }
  }
  
  private void logChildren()
  {
    Log.d("LinearLayoutManager", "internal representation of views on the screen");
    for (int i = 0; i < getChildCount(); i++)
    {
      View localView = getChildAt(i);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("item ");
      localStringBuilder.append(getPosition(localView));
      localStringBuilder.append(", coord:");
      localStringBuilder.append(this.mOrientationHelper.getDecoratedStart(localView));
      Log.d("LinearLayoutManager", localStringBuilder.toString());
    }
    Log.d("LinearLayoutManager", "==============");
  }
  
  private void recycleByLayoutState(RecyclerView.Recycler paramRecycler, b paramb)
  {
    if ((paramb.a) && (!paramb.l))
    {
      if (paramb.f == -1) {
        recycleViewsFromEnd(paramRecycler, paramb.g);
      } else {
        recycleViewsFromStart(paramRecycler, paramb.g);
      }
      return;
    }
  }
  
  private void recycleChildren(RecyclerView.Recycler paramRecycler, int paramInt1, int paramInt2)
  {
    if (paramInt1 == paramInt2) {
      return;
    }
    int i = paramInt1;
    if (paramInt2 > paramInt1)
    {
      paramInt2--;
      while (paramInt2 >= paramInt1)
      {
        removeAndRecycleViewAt(paramInt2, paramRecycler);
        paramInt2--;
      }
    }
    while (i > paramInt2)
    {
      removeAndRecycleViewAt(i, paramRecycler);
      i--;
    }
  }
  
  private void recycleViewsFromEnd(RecyclerView.Recycler paramRecycler, int paramInt)
  {
    int i = getChildCount();
    if (paramInt < 0) {
      return;
    }
    int j = this.mOrientationHelper.getEnd() - paramInt;
    View localView;
    if (this.mShouldReverseLayout)
    {
      for (paramInt = 0;; paramInt++)
      {
        if (paramInt >= i) {
          return;
        }
        localView = getChildAt(paramInt);
        if ((this.mOrientationHelper.getDecoratedStart(localView) < j) || (this.mOrientationHelper.getTransformedStartWithDecoration(localView) < j)) {
          break;
        }
      }
      recycleChildren(paramRecycler, 0, paramInt);
      return;
    }
    i--;
    paramInt = i;
    while (paramInt >= 0)
    {
      localView = getChildAt(paramInt);
      if ((this.mOrientationHelper.getDecoratedStart(localView) >= j) && (this.mOrientationHelper.getTransformedStartWithDecoration(localView) >= j))
      {
        paramInt--;
      }
      else
      {
        recycleChildren(paramRecycler, i, paramInt);
        return;
      }
    }
  }
  
  private void recycleViewsFromStart(RecyclerView.Recycler paramRecycler, int paramInt)
  {
    if (paramInt < 0) {
      return;
    }
    int i = getChildCount();
    View localView;
    if (this.mShouldReverseLayout)
    {
      i--;
      for (j = i;; j--)
      {
        if (j < 0) {
          return;
        }
        localView = getChildAt(j);
        if ((this.mOrientationHelper.getDecoratedEnd(localView) > paramInt) || (this.mOrientationHelper.getTransformedEndWithDecoration(localView) > paramInt)) {
          break;
        }
      }
      recycleChildren(paramRecycler, i, j);
      return;
    }
    int j = 0;
    while (j < i)
    {
      localView = getChildAt(j);
      if ((this.mOrientationHelper.getDecoratedEnd(localView) <= paramInt) && (this.mOrientationHelper.getTransformedEndWithDecoration(localView) <= paramInt))
      {
        j++;
      }
      else
      {
        recycleChildren(paramRecycler, 0, j);
        return;
      }
    }
  }
  
  private void resolveShouldLayoutReverse()
  {
    if ((this.mOrientation != 1) && (isLayoutRTL())) {
      this.mShouldReverseLayout = (this.mReverseLayout ^ true);
    } else {
      this.mShouldReverseLayout = this.mReverseLayout;
    }
  }
  
  private boolean updateAnchorFromChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, a parama)
  {
    int i = getChildCount();
    int j = 0;
    if (i == 0) {
      return false;
    }
    View localView = getFocusedChild();
    if ((localView != null) && (parama.a(localView, paramState)))
    {
      parama.a(localView);
      return true;
    }
    if (this.mLastStackFromEnd != this.mStackFromEnd) {
      return false;
    }
    if (parama.c) {
      paramRecycler = findReferenceChildClosestToEnd(paramRecycler, paramState);
    } else {
      paramRecycler = findReferenceChildClosestToStart(paramRecycler, paramState);
    }
    if (paramRecycler != null)
    {
      parama.b(paramRecycler);
      if ((!paramState.isPreLayout()) && (supportsPredictiveItemAnimations()))
      {
        if ((this.mOrientationHelper.getDecoratedStart(paramRecycler) >= this.mOrientationHelper.getEndAfterPadding()) || (this.mOrientationHelper.getDecoratedEnd(paramRecycler) < this.mOrientationHelper.getStartAfterPadding())) {
          j = 1;
        }
        if (j != 0)
        {
          if (parama.c) {
            j = this.mOrientationHelper.getEndAfterPadding();
          } else {
            j = this.mOrientationHelper.getStartAfterPadding();
          }
          parama.b = j;
        }
      }
      return true;
    }
    return false;
  }
  
  private boolean updateAnchorFromPendingData(RecyclerView.State paramState, a parama)
  {
    boolean bool1 = paramState.isPreLayout();
    boolean bool2 = false;
    if (!bool1)
    {
      int i = this.mPendingScrollPosition;
      if (i != -1)
      {
        if ((i >= 0) && (i < paramState.getItemCount()))
        {
          parama.a = this.mPendingScrollPosition;
          paramState = this.mPendingSavedState;
          if ((paramState != null) && (paramState.hasValidAnchor()))
          {
            parama.c = this.mPendingSavedState.mAnchorLayoutFromEnd;
            if (parama.c) {
              parama.b = (this.mOrientationHelper.getEndAfterPadding() - this.mPendingSavedState.mAnchorOffset);
            } else {
              parama.b = (this.mOrientationHelper.getStartAfterPadding() + this.mPendingSavedState.mAnchorOffset);
            }
            return true;
          }
          if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE)
          {
            paramState = findViewByPosition(this.mPendingScrollPosition);
            if (paramState != null)
            {
              if (this.mOrientationHelper.getDecoratedMeasurement(paramState) > this.mOrientationHelper.getTotalSpace())
              {
                parama.b();
                return true;
              }
              if (this.mOrientationHelper.getDecoratedStart(paramState) - this.mOrientationHelper.getStartAfterPadding() < 0)
              {
                parama.b = this.mOrientationHelper.getStartAfterPadding();
                parama.c = false;
                return true;
              }
              if (this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(paramState) < 0)
              {
                parama.b = this.mOrientationHelper.getEndAfterPadding();
                parama.c = true;
                return true;
              }
              if (parama.c) {
                i = this.mOrientationHelper.getDecoratedEnd(paramState) + this.mOrientationHelper.getTotalSpaceChange();
              } else {
                i = this.mOrientationHelper.getDecoratedStart(paramState);
              }
              parama.b = i;
            }
            else
            {
              if (getChildCount() > 0)
              {
                i = getPosition(getChildAt(0));
                if (this.mPendingScrollPosition < i) {
                  bool1 = true;
                } else {
                  bool1 = false;
                }
                if (bool1 == this.mShouldReverseLayout) {
                  bool2 = true;
                }
                parama.c = bool2;
              }
              parama.b();
            }
            return true;
          }
          bool1 = this.mShouldReverseLayout;
          parama.c = bool1;
          if (bool1) {
            parama.b = (this.mOrientationHelper.getEndAfterPadding() - this.mPendingScrollPositionOffset);
          } else {
            parama.b = (this.mOrientationHelper.getStartAfterPadding() + this.mPendingScrollPositionOffset);
          }
          return true;
        }
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        return false;
      }
    }
    return false;
  }
  
  private void updateAnchorInfoForLayout(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, a parama)
  {
    if (updateAnchorFromPendingData(paramState, parama)) {
      return;
    }
    if (updateAnchorFromChildren(paramRecycler, paramState, parama)) {
      return;
    }
    parama.b();
    int i;
    if (this.mStackFromEnd) {
      i = paramState.getItemCount() - 1;
    } else {
      i = 0;
    }
    parama.a = i;
  }
  
  private void updateLayoutState(int paramInt1, int paramInt2, boolean paramBoolean, RecyclerView.State paramState)
  {
    this.mLayoutState.l = resolveIsInfinite();
    this.mLayoutState.h = getExtraLayoutSpace(paramState);
    paramState = this.mLayoutState;
    paramState.f = paramInt1;
    int i = -1;
    Object localObject;
    if (paramInt1 == 1)
    {
      paramState.h += this.mOrientationHelper.getEndPadding();
      localObject = getChildClosestToEnd();
      paramState = this.mLayoutState;
      if (!this.mShouldReverseLayout) {
        i = 1;
      }
      paramState.e = i;
      this.mLayoutState.d = (getPosition((View)localObject) + this.mLayoutState.e);
      this.mLayoutState.b = this.mOrientationHelper.getDecoratedEnd((View)localObject);
      paramInt1 = this.mOrientationHelper.getDecoratedEnd((View)localObject) - this.mOrientationHelper.getEndAfterPadding();
    }
    else
    {
      paramState = getChildClosestToStart();
      localObject = this.mLayoutState;
      ((b)localObject).h += this.mOrientationHelper.getStartAfterPadding();
      localObject = this.mLayoutState;
      if (this.mShouldReverseLayout) {
        i = 1;
      }
      ((b)localObject).e = i;
      this.mLayoutState.d = (getPosition(paramState) + this.mLayoutState.e);
      this.mLayoutState.b = this.mOrientationHelper.getDecoratedStart(paramState);
      paramInt1 = -this.mOrientationHelper.getDecoratedStart(paramState) + this.mOrientationHelper.getStartAfterPadding();
    }
    paramState = this.mLayoutState;
    paramState.c = paramInt2;
    if (paramBoolean) {
      paramState.c -= paramInt1;
    }
    this.mLayoutState.g = paramInt1;
  }
  
  private void updateLayoutStateToFillEnd(int paramInt1, int paramInt2)
  {
    this.mLayoutState.c = (this.mOrientationHelper.getEndAfterPadding() - paramInt2);
    b localb = this.mLayoutState;
    int i;
    if (this.mShouldReverseLayout) {
      i = -1;
    } else {
      i = 1;
    }
    localb.e = i;
    localb = this.mLayoutState;
    localb.d = paramInt1;
    localb.f = 1;
    localb.b = paramInt2;
    localb.g = Integer.MIN_VALUE;
  }
  
  private void updateLayoutStateToFillEnd(a parama)
  {
    updateLayoutStateToFillEnd(parama.a, parama.b);
  }
  
  private void updateLayoutStateToFillStart(int paramInt1, int paramInt2)
  {
    this.mLayoutState.c = (paramInt2 - this.mOrientationHelper.getStartAfterPadding());
    b localb = this.mLayoutState;
    localb.d = paramInt1;
    if (this.mShouldReverseLayout) {
      paramInt1 = 1;
    } else {
      paramInt1 = -1;
    }
    localb.e = paramInt1;
    localb = this.mLayoutState;
    localb.f = -1;
    localb.b = paramInt2;
    localb.g = Integer.MIN_VALUE;
  }
  
  private void updateLayoutStateToFillStart(a parama)
  {
    updateLayoutStateToFillStart(parama.a, parama.b);
  }
  
  public void assertNotInLayoutOrScroll(String paramString)
  {
    if (this.mPendingSavedState == null) {
      super.assertNotInLayoutOrScroll(paramString);
    }
  }
  
  public boolean canScrollHorizontally()
  {
    boolean bool;
    if (this.mOrientation == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean canScrollVertically()
  {
    int i = this.mOrientation;
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  public void collectAdjacentPrefetchPositions(int paramInt1, int paramInt2, RecyclerView.State paramState, RecyclerView.LayoutManager.LayoutPrefetchRegistry paramLayoutPrefetchRegistry)
  {
    if (this.mOrientation != 0) {
      paramInt1 = paramInt2;
    }
    if ((getChildCount() != 0) && (paramInt1 != 0))
    {
      ensureLayoutState();
      if (paramInt1 > 0) {
        paramInt2 = 1;
      } else {
        paramInt2 = -1;
      }
      updateLayoutState(paramInt2, Math.abs(paramInt1), true, paramState);
      collectPrefetchPositionsForLayoutState(paramState, this.mLayoutState, paramLayoutPrefetchRegistry);
      return;
    }
  }
  
  public void collectInitialPrefetchPositions(int paramInt, RecyclerView.LayoutManager.LayoutPrefetchRegistry paramLayoutPrefetchRegistry)
  {
    SavedState localSavedState = this.mPendingSavedState;
    int i = -1;
    boolean bool1;
    if ((localSavedState != null) && (localSavedState.hasValidAnchor()))
    {
      bool1 = this.mPendingSavedState.mAnchorLayoutFromEnd;
      j = this.mPendingSavedState.mAnchorPosition;
    }
    else
    {
      resolveShouldLayoutReverse();
      boolean bool2 = this.mShouldReverseLayout;
      k = this.mPendingScrollPosition;
      bool1 = bool2;
      j = k;
      if (k == -1) {
        if (bool2)
        {
          j = paramInt - 1;
          bool1 = bool2;
        }
        else
        {
          j = 0;
          bool1 = bool2;
        }
      }
    }
    if (!bool1) {
      i = 1;
    }
    int m = 0;
    int k = j;
    for (int j = m; (j < this.mInitialPrefetchItemCount) && (k >= 0) && (k < paramInt); j++)
    {
      paramLayoutPrefetchRegistry.addPosition(k, 0);
      k += i;
    }
  }
  
  void collectPrefetchPositionsForLayoutState(RecyclerView.State paramState, b paramb, RecyclerView.LayoutManager.LayoutPrefetchRegistry paramLayoutPrefetchRegistry)
  {
    int i = paramb.d;
    if ((i >= 0) && (i < paramState.getItemCount())) {
      paramLayoutPrefetchRegistry.addPosition(i, Math.max(0, paramb.g));
    }
  }
  
  public int computeHorizontalScrollExtent(RecyclerView.State paramState)
  {
    return computeScrollExtent(paramState);
  }
  
  public int computeHorizontalScrollOffset(RecyclerView.State paramState)
  {
    return computeScrollOffset(paramState);
  }
  
  public int computeHorizontalScrollRange(RecyclerView.State paramState)
  {
    return computeScrollRange(paramState);
  }
  
  public PointF computeScrollVectorForPosition(int paramInt)
  {
    if (getChildCount() == 0) {
      return null;
    }
    int i = 0;
    int j = getPosition(getChildAt(0));
    int k = 1;
    if (paramInt < j) {
      i = 1;
    }
    paramInt = k;
    if (i != this.mShouldReverseLayout) {
      paramInt = -1;
    }
    if (this.mOrientation == 0) {
      return new PointF(paramInt, 0.0F);
    }
    return new PointF(0.0F, paramInt);
  }
  
  public int computeVerticalScrollExtent(RecyclerView.State paramState)
  {
    return computeScrollExtent(paramState);
  }
  
  public int computeVerticalScrollOffset(RecyclerView.State paramState)
  {
    return computeScrollOffset(paramState);
  }
  
  public int computeVerticalScrollRange(RecyclerView.State paramState)
  {
    return computeScrollRange(paramState);
  }
  
  int convertFocusDirectionToLayoutDirection(int paramInt)
  {
    int i = -1;
    int j = Integer.MIN_VALUE;
    if (paramInt != 17)
    {
      if (paramInt != 33)
      {
        if (paramInt != 66)
        {
          if (paramInt != 130)
          {
            switch (paramInt)
            {
            default: 
              return Integer.MIN_VALUE;
            case 2: 
              if (this.mOrientation == 1) {
                return 1;
              }
              if (isLayoutRTL()) {
                return -1;
              }
              return 1;
            }
            if (this.mOrientation == 1) {
              return -1;
            }
            if (isLayoutRTL()) {
              return 1;
            }
            return -1;
          }
          if (this.mOrientation == 1) {
            j = 1;
          }
          return j;
        }
        if (this.mOrientation == 0) {
          j = 1;
        }
        return j;
      }
      if (this.mOrientation != 1) {
        i = Integer.MIN_VALUE;
      }
      return i;
    }
    if (this.mOrientation != 0) {
      i = Integer.MIN_VALUE;
    }
    return i;
  }
  
  b createLayoutState()
  {
    return new b();
  }
  
  void ensureLayoutState()
  {
    if (this.mLayoutState == null) {
      this.mLayoutState = createLayoutState();
    }
    if (this.mOrientationHelper == null) {
      this.mOrientationHelper = OrientationHelper.createOrientationHelper(this, this.mOrientation);
    }
  }
  
  int fill(RecyclerView.Recycler paramRecycler, b paramb, RecyclerView.State paramState, boolean paramBoolean)
  {
    int i = paramb.c;
    if (paramb.g != Integer.MIN_VALUE)
    {
      if (paramb.c < 0) {
        paramb.g += paramb.c;
      }
      recycleByLayoutState(paramRecycler, paramb);
    }
    int j = paramb.c + paramb.h;
    LayoutChunkResult localLayoutChunkResult = this.mLayoutChunkResult;
    do
    {
      int k;
      do
      {
        if (((!paramb.l) && (j <= 0)) || (!paramb.a(paramState))) {
          break;
        }
        localLayoutChunkResult.resetInternal();
        layoutChunk(paramRecycler, paramState, paramb, localLayoutChunkResult);
        if (localLayoutChunkResult.mFinished) {
          break;
        }
        paramb.b += localLayoutChunkResult.mConsumed * paramb.f;
        if ((localLayoutChunkResult.mIgnoreConsumed) && (this.mLayoutState.k == null))
        {
          k = j;
          if (paramState.isPreLayout()) {}
        }
        else
        {
          paramb.c -= localLayoutChunkResult.mConsumed;
          k = j - localLayoutChunkResult.mConsumed;
        }
        if (paramb.g != Integer.MIN_VALUE)
        {
          paramb.g += localLayoutChunkResult.mConsumed;
          if (paramb.c < 0) {
            paramb.g += paramb.c;
          }
          recycleByLayoutState(paramRecycler, paramb);
        }
        j = k;
      } while (!paramBoolean);
      j = k;
    } while (!localLayoutChunkResult.mFocusable);
    return i - paramb.c;
  }
  
  public int findFirstCompletelyVisibleItemPosition()
  {
    View localView = findOneVisibleChild(0, getChildCount(), true, false);
    int i;
    if (localView == null) {
      i = -1;
    } else {
      i = getPosition(localView);
    }
    return i;
  }
  
  public int findFirstVisibleItemPosition()
  {
    View localView = findOneVisibleChild(0, getChildCount(), false, true);
    int i;
    if (localView == null) {
      i = -1;
    } else {
      i = getPosition(localView);
    }
    return i;
  }
  
  public int findLastCompletelyVisibleItemPosition()
  {
    int i = getChildCount();
    int j = -1;
    View localView = findOneVisibleChild(i - 1, -1, true, false);
    if (localView != null) {
      j = getPosition(localView);
    }
    return j;
  }
  
  public int findLastVisibleItemPosition()
  {
    int i = getChildCount();
    int j = -1;
    View localView = findOneVisibleChild(i - 1, -1, false, true);
    if (localView != null) {
      j = getPosition(localView);
    }
    return j;
  }
  
  View findOnePartiallyOrCompletelyInvisibleChild(int paramInt1, int paramInt2)
  {
    ensureLayoutState();
    int i;
    if (paramInt2 > paramInt1) {
      i = 1;
    } else if (paramInt2 < paramInt1) {
      i = -1;
    } else {
      i = 0;
    }
    if (i == 0) {
      return getChildAt(paramInt1);
    }
    int j;
    if (this.mOrientationHelper.getDecoratedStart(getChildAt(paramInt1)) < this.mOrientationHelper.getStartAfterPadding())
    {
      i = 16644;
      j = 16388;
    }
    else
    {
      i = 4161;
      j = 4097;
    }
    View localView;
    if (this.mOrientation == 0) {
      localView = this.mHorizontalBoundCheck.a(paramInt1, paramInt2, i, j);
    } else {
      localView = this.mVerticalBoundCheck.a(paramInt1, paramInt2, i, j);
    }
    return localView;
  }
  
  View findOneVisibleChild(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    ensureLayoutState();
    int i = 320;
    int j;
    if (paramBoolean1) {
      j = 24579;
    } else {
      j = 320;
    }
    if (!paramBoolean2) {
      i = 0;
    }
    View localView;
    if (this.mOrientation == 0) {
      localView = this.mHorizontalBoundCheck.a(paramInt1, paramInt2, j, i);
    } else {
      localView = this.mVerticalBoundCheck.a(paramInt1, paramInt2, j, i);
    }
    return localView;
  }
  
  View findReferenceChild(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2, int paramInt3)
  {
    ensureLayoutState();
    int i = this.mOrientationHelper.getStartAfterPadding();
    int j = this.mOrientationHelper.getEndAfterPadding();
    int k;
    if (paramInt2 > paramInt1) {
      k = 1;
    } else {
      k = -1;
    }
    paramState = null;
    Object localObject2;
    for (paramRecycler = null; paramInt1 != paramInt2; paramRecycler = (RecyclerView.Recycler)localObject2)
    {
      View localView = getChildAt(paramInt1);
      int m = getPosition(localView);
      Object localObject1 = paramState;
      localObject2 = paramRecycler;
      if (m >= 0)
      {
        localObject1 = paramState;
        localObject2 = paramRecycler;
        if (m < paramInt3) {
          if (((RecyclerView.LayoutParams)localView.getLayoutParams()).isItemRemoved())
          {
            localObject1 = paramState;
            localObject2 = paramRecycler;
            if (paramRecycler == null)
            {
              localObject2 = localView;
              localObject1 = paramState;
            }
          }
          else
          {
            if ((this.mOrientationHelper.getDecoratedStart(localView) < j) && (this.mOrientationHelper.getDecoratedEnd(localView) >= i)) {
              return localView;
            }
            localObject1 = paramState;
            localObject2 = paramRecycler;
            if (paramState == null)
            {
              localObject1 = localView;
              localObject2 = paramRecycler;
            }
          }
        }
      }
      paramInt1 += k;
      paramState = (RecyclerView.State)localObject1;
    }
    if (paramState != null) {
      paramRecycler = paramState;
    }
    return paramRecycler;
  }
  
  public View findViewByPosition(int paramInt)
  {
    int i = getChildCount();
    if (i == 0) {
      return null;
    }
    int j = paramInt - getPosition(getChildAt(0));
    if ((j >= 0) && (j < i))
    {
      View localView = getChildAt(j);
      if (getPosition(localView) == paramInt) {
        return localView;
      }
    }
    return super.findViewByPosition(paramInt);
  }
  
  public RecyclerView.LayoutParams generateDefaultLayoutParams()
  {
    return new RecyclerView.LayoutParams(-2, -2);
  }
  
  protected int getExtraLayoutSpace(RecyclerView.State paramState)
  {
    if (paramState.hasTargetScrollPosition()) {
      return this.mOrientationHelper.getTotalSpace();
    }
    return 0;
  }
  
  public int getInitialPrefetchItemCount()
  {
    return this.mInitialPrefetchItemCount;
  }
  
  public int getOrientation()
  {
    return this.mOrientation;
  }
  
  public boolean getRecycleChildrenOnDetach()
  {
    return this.mRecycleChildrenOnDetach;
  }
  
  public boolean getReverseLayout()
  {
    return this.mReverseLayout;
  }
  
  public boolean getStackFromEnd()
  {
    return this.mStackFromEnd;
  }
  
  protected boolean isLayoutRTL()
  {
    int i = getLayoutDirection();
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  public boolean isSmoothScrollbarEnabled()
  {
    return this.mSmoothScrollbarEnabled;
  }
  
  void layoutChunk(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, b paramb, LayoutChunkResult paramLayoutChunkResult)
  {
    paramRecycler = paramb.a(paramRecycler);
    if (paramRecycler == null)
    {
      paramLayoutChunkResult.mFinished = true;
      return;
    }
    paramState = (RecyclerView.LayoutParams)paramRecycler.getLayoutParams();
    boolean bool1;
    boolean bool2;
    if (paramb.k == null)
    {
      bool1 = this.mShouldReverseLayout;
      if (paramb.f == -1) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      if (bool1 == bool2) {
        addView(paramRecycler);
      } else {
        addView(paramRecycler, 0);
      }
    }
    else
    {
      bool1 = this.mShouldReverseLayout;
      if (paramb.f == -1) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      if (bool1 == bool2) {
        addDisappearingView(paramRecycler);
      } else {
        addDisappearingView(paramRecycler, 0);
      }
    }
    measureChildWithMargins(paramRecycler, 0, 0);
    paramLayoutChunkResult.mConsumed = this.mOrientationHelper.getDecoratedMeasurement(paramRecycler);
    int i;
    int j;
    int k;
    int m;
    int n;
    if (this.mOrientation == 1)
    {
      if (isLayoutRTL())
      {
        i = getWidth() - getPaddingRight();
        j = i - this.mOrientationHelper.getDecoratedMeasurementInOther(paramRecycler);
      }
      else
      {
        j = getPaddingLeft();
        i = this.mOrientationHelper.getDecoratedMeasurementInOther(paramRecycler) + j;
      }
      if (paramb.f == -1)
      {
        k = paramb.b;
        m = paramb.b - paramLayoutChunkResult.mConsumed;
        n = i;
        i = m;
      }
      else
      {
        m = paramb.b;
        k = paramb.b + paramLayoutChunkResult.mConsumed;
        n = i;
        i = m;
      }
    }
    else
    {
      k = getPaddingTop();
      i = this.mOrientationHelper.getDecoratedMeasurementInOther(paramRecycler) + k;
      if (paramb.f == -1)
      {
        n = paramb.b;
        j = paramb.b;
        int i1 = paramLayoutChunkResult.mConsumed;
        m = i;
        j -= i1;
        i = k;
        k = m;
      }
      else
      {
        m = paramb.b;
        n = paramb.b + paramLayoutChunkResult.mConsumed;
        j = k;
        k = i;
        i = j;
        j = m;
      }
    }
    layoutDecoratedWithMargins(paramRecycler, j, i, n, k);
    if ((paramState.isItemRemoved()) || (paramState.isItemChanged())) {
      paramLayoutChunkResult.mIgnoreConsumed = true;
    }
    paramLayoutChunkResult.mFocusable = paramRecycler.hasFocusable();
  }
  
  void onAnchorReady(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, a parama, int paramInt) {}
  
  public void onDetachedFromWindow(RecyclerView paramRecyclerView, RecyclerView.Recycler paramRecycler)
  {
    super.onDetachedFromWindow(paramRecyclerView, paramRecycler);
    if (this.mRecycleChildrenOnDetach)
    {
      removeAndRecycleAllViews(paramRecycler);
      paramRecycler.clear();
    }
  }
  
  public View onFocusSearchFailed(View paramView, int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    resolveShouldLayoutReverse();
    if (getChildCount() == 0) {
      return null;
    }
    paramInt = convertFocusDirectionToLayoutDirection(paramInt);
    if (paramInt == Integer.MIN_VALUE) {
      return null;
    }
    ensureLayoutState();
    ensureLayoutState();
    updateLayoutState(paramInt, (int)(this.mOrientationHelper.getTotalSpace() * 0.33333334F), false, paramState);
    paramView = this.mLayoutState;
    paramView.g = Integer.MIN_VALUE;
    paramView.a = false;
    fill(paramRecycler, paramView, paramState, true);
    if (paramInt == -1) {
      paramView = findPartiallyOrCompletelyInvisibleChildClosestToStart(paramRecycler, paramState);
    } else {
      paramView = findPartiallyOrCompletelyInvisibleChildClosestToEnd(paramRecycler, paramState);
    }
    if (paramInt == -1) {
      paramRecycler = getChildClosestToStart();
    } else {
      paramRecycler = getChildClosestToEnd();
    }
    if (paramRecycler.hasFocusable())
    {
      if (paramView == null) {
        return null;
      }
      return paramRecycler;
    }
    return paramView;
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    if (getChildCount() > 0)
    {
      paramAccessibilityEvent.setFromIndex(findFirstVisibleItemPosition());
      paramAccessibilityEvent.setToIndex(findLastVisibleItemPosition());
    }
  }
  
  public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    Object localObject = this.mPendingSavedState;
    int i = -1;
    if (((localObject != null) || (this.mPendingScrollPosition != -1)) && (paramState.getItemCount() == 0))
    {
      removeAndRecycleAllViews(paramRecycler);
      return;
    }
    localObject = this.mPendingSavedState;
    if ((localObject != null) && (((SavedState)localObject).hasValidAnchor())) {
      this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
    }
    ensureLayoutState();
    this.mLayoutState.a = false;
    resolveShouldLayoutReverse();
    localObject = getFocusedChild();
    if ((this.mAnchorInfo.d) && (this.mPendingScrollPosition == -1) && (this.mPendingSavedState == null))
    {
      if ((localObject != null) && ((this.mOrientationHelper.getDecoratedStart((View)localObject) >= this.mOrientationHelper.getEndAfterPadding()) || (this.mOrientationHelper.getDecoratedEnd((View)localObject) <= this.mOrientationHelper.getStartAfterPadding()))) {
        this.mAnchorInfo.a((View)localObject);
      }
    }
    else
    {
      this.mAnchorInfo.a();
      localObject = this.mAnchorInfo;
      ((a)localObject).c = (this.mShouldReverseLayout ^ this.mStackFromEnd);
      updateAnchorInfoForLayout(paramRecycler, paramState, (a)localObject);
      this.mAnchorInfo.d = true;
    }
    int j = getExtraLayoutSpace(paramState);
    if (this.mLayoutState.j >= 0)
    {
      k = j;
      j = 0;
    }
    else
    {
      k = 0;
    }
    int m = j + this.mOrientationHelper.getStartAfterPadding();
    int n = k + this.mOrientationHelper.getEndPadding();
    int k = m;
    j = n;
    if (paramState.isPreLayout())
    {
      int i1 = this.mPendingScrollPosition;
      k = m;
      j = n;
      if (i1 != -1)
      {
        k = m;
        j = n;
        if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE)
        {
          localObject = findViewByPosition(i1);
          k = m;
          j = n;
          if (localObject != null)
          {
            if (this.mShouldReverseLayout)
            {
              k = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd((View)localObject) - this.mPendingScrollPositionOffset;
            }
            else
            {
              k = this.mOrientationHelper.getDecoratedStart((View)localObject);
              j = this.mOrientationHelper.getStartAfterPadding();
              k = this.mPendingScrollPositionOffset - (k - j);
            }
            if (k > 0)
            {
              k = m + k;
              j = n;
            }
            else
            {
              j = n - k;
              k = m;
            }
          }
        }
      }
    }
    if (this.mAnchorInfo.c)
    {
      if (this.mShouldReverseLayout) {
        i = 1;
      }
    }
    else if (!this.mShouldReverseLayout) {
      i = 1;
    }
    onAnchorReady(paramRecycler, paramState, this.mAnchorInfo, i);
    detachAndScrapAttachedViews(paramRecycler);
    this.mLayoutState.l = resolveIsInfinite();
    this.mLayoutState.i = paramState.isPreLayout();
    if (this.mAnchorInfo.c)
    {
      updateLayoutStateToFillStart(this.mAnchorInfo);
      localObject = this.mLayoutState;
      ((b)localObject).h = k;
      fill(paramRecycler, (b)localObject, paramState, false);
      i = this.mLayoutState.b;
      n = this.mLayoutState.d;
      k = j;
      if (this.mLayoutState.c > 0) {
        k = j + this.mLayoutState.c;
      }
      updateLayoutStateToFillEnd(this.mAnchorInfo);
      localObject = this.mLayoutState;
      ((b)localObject).h = k;
      ((b)localObject).d += this.mLayoutState.e;
      fill(paramRecycler, this.mLayoutState, paramState, false);
      m = this.mLayoutState.b;
      j = i;
      k = m;
      if (this.mLayoutState.c > 0)
      {
        k = this.mLayoutState.c;
        updateLayoutStateToFillStart(n, i);
        localObject = this.mLayoutState;
        ((b)localObject).h = k;
        fill(paramRecycler, (b)localObject, paramState, false);
        j = this.mLayoutState.b;
        k = m;
      }
    }
    else
    {
      updateLayoutStateToFillEnd(this.mAnchorInfo);
      localObject = this.mLayoutState;
      ((b)localObject).h = j;
      fill(paramRecycler, (b)localObject, paramState, false);
      i = this.mLayoutState.b;
      n = this.mLayoutState.d;
      j = k;
      if (this.mLayoutState.c > 0) {
        j = k + this.mLayoutState.c;
      }
      updateLayoutStateToFillStart(this.mAnchorInfo);
      localObject = this.mLayoutState;
      ((b)localObject).h = j;
      ((b)localObject).d += this.mLayoutState.e;
      fill(paramRecycler, this.mLayoutState, paramState, false);
      m = this.mLayoutState.b;
      j = m;
      k = i;
      if (this.mLayoutState.c > 0)
      {
        k = this.mLayoutState.c;
        updateLayoutStateToFillEnd(n, i);
        localObject = this.mLayoutState;
        ((b)localObject).h = k;
        fill(paramRecycler, (b)localObject, paramState, false);
        k = this.mLayoutState.b;
        j = m;
      }
    }
    i = j;
    m = k;
    if (getChildCount() > 0) {
      if ((this.mShouldReverseLayout ^ this.mStackFromEnd))
      {
        m = fixLayoutEndGap(k, paramRecycler, paramState, true);
        i = j + m;
        j = fixLayoutStartGap(i, paramRecycler, paramState, false);
        i += j;
        m = k + m + j;
      }
      else
      {
        i = fixLayoutStartGap(j, paramRecycler, paramState, true);
        k += i;
        m = fixLayoutEndGap(k, paramRecycler, paramState, false);
        i = j + i + m;
        m = k + m;
      }
    }
    layoutForPredictiveAnimations(paramRecycler, paramState, i, m);
    if (!paramState.isPreLayout()) {
      this.mOrientationHelper.onLayoutComplete();
    } else {
      this.mAnchorInfo.a();
    }
    this.mLastStackFromEnd = this.mStackFromEnd;
  }
  
  public void onLayoutCompleted(RecyclerView.State paramState)
  {
    super.onLayoutCompleted(paramState);
    this.mPendingSavedState = null;
    this.mPendingScrollPosition = -1;
    this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
    this.mAnchorInfo.a();
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof SavedState))
    {
      this.mPendingSavedState = ((SavedState)paramParcelable);
      requestLayout();
    }
  }
  
  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = this.mPendingSavedState;
    if (localSavedState != null) {
      return new SavedState(localSavedState);
    }
    localSavedState = new SavedState();
    if (getChildCount() > 0)
    {
      ensureLayoutState();
      boolean bool = this.mLastStackFromEnd ^ this.mShouldReverseLayout;
      localSavedState.mAnchorLayoutFromEnd = bool;
      View localView;
      if (bool)
      {
        localView = getChildClosestToEnd();
        localSavedState.mAnchorOffset = (this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(localView));
        localSavedState.mAnchorPosition = getPosition(localView);
      }
      else
      {
        localView = getChildClosestToStart();
        localSavedState.mAnchorPosition = getPosition(localView);
        localSavedState.mAnchorOffset = (this.mOrientationHelper.getDecoratedStart(localView) - this.mOrientationHelper.getStartAfterPadding());
      }
    }
    else
    {
      localSavedState.invalidateAnchor();
    }
    return localSavedState;
  }
  
  public void prepareForDrop(View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    assertNotInLayoutOrScroll("Cannot drop a view during a scroll or layout calculation");
    ensureLayoutState();
    resolveShouldLayoutReverse();
    paramInt1 = getPosition(paramView1);
    paramInt2 = getPosition(paramView2);
    if (paramInt1 < paramInt2) {
      paramInt1 = 1;
    } else {
      paramInt1 = -1;
    }
    if (this.mShouldReverseLayout)
    {
      if (paramInt1 == 1) {
        scrollToPositionWithOffset(paramInt2, this.mOrientationHelper.getEndAfterPadding() - (this.mOrientationHelper.getDecoratedStart(paramView2) + this.mOrientationHelper.getDecoratedMeasurement(paramView1)));
      } else {
        scrollToPositionWithOffset(paramInt2, this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(paramView2));
      }
    }
    else if (paramInt1 == -1) {
      scrollToPositionWithOffset(paramInt2, this.mOrientationHelper.getDecoratedStart(paramView2));
    } else {
      scrollToPositionWithOffset(paramInt2, this.mOrientationHelper.getDecoratedEnd(paramView2) - this.mOrientationHelper.getDecoratedMeasurement(paramView1));
    }
  }
  
  boolean resolveIsInfinite()
  {
    boolean bool;
    if ((this.mOrientationHelper.getMode() == 0) && (this.mOrientationHelper.getEnd() == 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  int scrollBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if ((getChildCount() != 0) && (paramInt != 0))
    {
      this.mLayoutState.a = true;
      ensureLayoutState();
      int i;
      if (paramInt > 0) {
        i = 1;
      } else {
        i = -1;
      }
      int j = Math.abs(paramInt);
      updateLayoutState(i, j, true, paramState);
      int k = this.mLayoutState.g + fill(paramRecycler, this.mLayoutState, paramState, false);
      if (k < 0) {
        return 0;
      }
      if (j > k) {
        paramInt = i * k;
      }
      this.mOrientationHelper.offsetChildren(-paramInt);
      this.mLayoutState.j = paramInt;
      return paramInt;
    }
    return 0;
  }
  
  public int scrollHorizontallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 1) {
      return 0;
    }
    return scrollBy(paramInt, paramRecycler, paramState);
  }
  
  public void scrollToPosition(int paramInt)
  {
    this.mPendingScrollPosition = paramInt;
    this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
    SavedState localSavedState = this.mPendingSavedState;
    if (localSavedState != null) {
      localSavedState.invalidateAnchor();
    }
    requestLayout();
  }
  
  public void scrollToPositionWithOffset(int paramInt1, int paramInt2)
  {
    this.mPendingScrollPosition = paramInt1;
    this.mPendingScrollPositionOffset = paramInt2;
    SavedState localSavedState = this.mPendingSavedState;
    if (localSavedState != null) {
      localSavedState.invalidateAnchor();
    }
    requestLayout();
  }
  
  public int scrollVerticallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 0) {
      return 0;
    }
    return scrollBy(paramInt, paramRecycler, paramState);
  }
  
  public void setInitialPrefetchItemCount(int paramInt)
  {
    this.mInitialPrefetchItemCount = paramInt;
  }
  
  public void setOrientation(int paramInt)
  {
    if ((paramInt != 0) && (paramInt != 1))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("invalid orientation:");
      localStringBuilder.append(paramInt);
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    assertNotInLayoutOrScroll(null);
    if (paramInt == this.mOrientation) {
      return;
    }
    this.mOrientation = paramInt;
    this.mOrientationHelper = null;
    requestLayout();
  }
  
  public void setRecycleChildrenOnDetach(boolean paramBoolean)
  {
    this.mRecycleChildrenOnDetach = paramBoolean;
  }
  
  public void setReverseLayout(boolean paramBoolean)
  {
    assertNotInLayoutOrScroll(null);
    if (paramBoolean == this.mReverseLayout) {
      return;
    }
    this.mReverseLayout = paramBoolean;
    requestLayout();
  }
  
  public void setSmoothScrollbarEnabled(boolean paramBoolean)
  {
    this.mSmoothScrollbarEnabled = paramBoolean;
  }
  
  public void setStackFromEnd(boolean paramBoolean)
  {
    assertNotInLayoutOrScroll(null);
    if (this.mStackFromEnd == paramBoolean) {
      return;
    }
    this.mStackFromEnd = paramBoolean;
    requestLayout();
  }
  
  boolean shouldMeasureTwice()
  {
    boolean bool;
    if ((getHeightMode() != 1073741824) && (getWidthMode() != 1073741824) && (hasFlexibleChildInBothOrientations())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void smoothScrollToPosition(RecyclerView paramRecyclerView, RecyclerView.State paramState, int paramInt)
  {
    paramRecyclerView = new LinearSmoothScroller(paramRecyclerView.getContext());
    paramRecyclerView.setTargetPosition(paramInt);
    startSmoothScroll(paramRecyclerView);
  }
  
  public boolean supportsPredictiveItemAnimations()
  {
    boolean bool;
    if ((this.mPendingSavedState == null) && (this.mLastStackFromEnd == this.mStackFromEnd)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void validateChildOrder()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("validating child count ");
    ((StringBuilder)localObject).append(getChildCount());
    Log.d("LinearLayoutManager", ((StringBuilder)localObject).toString());
    if (getChildCount() < 1) {
      return;
    }
    boolean bool1 = false;
    boolean bool2 = false;
    int i = getPosition(getChildAt(0));
    int j = this.mOrientationHelper.getDecoratedStart(getChildAt(0));
    int m;
    int n;
    if (this.mShouldReverseLayout)
    {
      for (k = 1;; k++)
      {
        if (k >= getChildCount()) {
          return;
        }
        localObject = getChildAt(k);
        m = getPosition((View)localObject);
        n = this.mOrientationHelper.getDecoratedStart((View)localObject);
        if (m < i)
        {
          logChildren();
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("detected invalid position. loc invalid? ");
          if (n < j) {
            bool2 = true;
          }
          ((StringBuilder)localObject).append(bool2);
          throw new RuntimeException(((StringBuilder)localObject).toString());
        }
        if (n > j) {
          break;
        }
      }
      logChildren();
      throw new RuntimeException("detected invalid location");
    }
    int k = 1;
    while (k < getChildCount())
    {
      localObject = getChildAt(k);
      n = getPosition((View)localObject);
      m = this.mOrientationHelper.getDecoratedStart((View)localObject);
      if (n < i)
      {
        logChildren();
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("detected invalid position. loc invalid? ");
        bool2 = bool1;
        if (m < j) {
          bool2 = true;
        }
        ((StringBuilder)localObject).append(bool2);
        throw new RuntimeException(((StringBuilder)localObject).toString());
      }
      if (m >= j)
      {
        k++;
      }
      else
      {
        logChildren();
        throw new RuntimeException("detected invalid location");
      }
    }
  }
  
  protected static class LayoutChunkResult
  {
    public int mConsumed;
    public boolean mFinished;
    public boolean mFocusable;
    public boolean mIgnoreConsumed;
    
    void resetInternal()
    {
      this.mConsumed = 0;
      this.mFinished = false;
      this.mIgnoreConsumed = false;
      this.mFocusable = false;
    }
  }
  
  public static class SavedState
    implements Parcelable
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public LinearLayoutManager.SavedState a(Parcel paramAnonymousParcel)
      {
        return new LinearLayoutManager.SavedState(paramAnonymousParcel);
      }
      
      public LinearLayoutManager.SavedState[] a(int paramAnonymousInt)
      {
        return new LinearLayoutManager.SavedState[paramAnonymousInt];
      }
    };
    boolean mAnchorLayoutFromEnd;
    int mAnchorOffset;
    int mAnchorPosition;
    
    public SavedState() {}
    
    SavedState(Parcel paramParcel)
    {
      this.mAnchorPosition = paramParcel.readInt();
      this.mAnchorOffset = paramParcel.readInt();
      int i = paramParcel.readInt();
      boolean bool = true;
      if (i != 1) {
        bool = false;
      }
      this.mAnchorLayoutFromEnd = bool;
    }
    
    public SavedState(SavedState paramSavedState)
    {
      this.mAnchorPosition = paramSavedState.mAnchorPosition;
      this.mAnchorOffset = paramSavedState.mAnchorOffset;
      this.mAnchorLayoutFromEnd = paramSavedState.mAnchorLayoutFromEnd;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    boolean hasValidAnchor()
    {
      boolean bool;
      if (this.mAnchorPosition >= 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    void invalidateAnchor()
    {
      this.mAnchorPosition = -1;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.mAnchorPosition);
      paramParcel.writeInt(this.mAnchorOffset);
      paramParcel.writeInt(this.mAnchorLayoutFromEnd);
    }
  }
  
  class a
  {
    int a;
    int b;
    boolean c;
    boolean d;
    
    a()
    {
      a();
    }
    
    void a()
    {
      this.a = -1;
      this.b = Integer.MIN_VALUE;
      this.c = false;
      this.d = false;
    }
    
    public void a(View paramView)
    {
      int i = LinearLayoutManager.this.mOrientationHelper.getTotalSpaceChange();
      if (i >= 0)
      {
        b(paramView);
        return;
      }
      this.a = LinearLayoutManager.this.getPosition(paramView);
      int j;
      int k;
      int m;
      if (this.c)
      {
        j = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - i - LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd(paramView);
        this.b = (LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - j);
        if (j > 0)
        {
          k = LinearLayoutManager.this.mOrientationHelper.getDecoratedMeasurement(paramView);
          m = this.b;
          i = LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding();
          i = m - k - (i + Math.min(LinearLayoutManager.this.mOrientationHelper.getDecoratedStart(paramView) - i, 0));
          if (i < 0) {
            this.b += Math.min(j, -i);
          }
        }
      }
      else
      {
        k = LinearLayoutManager.this.mOrientationHelper.getDecoratedStart(paramView);
        j = k - LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding();
        this.b = k;
        if (j > 0)
        {
          m = LinearLayoutManager.this.mOrientationHelper.getDecoratedMeasurement(paramView);
          int n = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding();
          int i1 = LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd(paramView);
          i = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - Math.min(0, n - i - i1) - (k + m);
          if (i < 0) {
            this.b -= Math.min(j, -i);
          }
        }
      }
    }
    
    boolean a(View paramView, RecyclerView.State paramState)
    {
      paramView = (RecyclerView.LayoutParams)paramView.getLayoutParams();
      boolean bool;
      if ((!paramView.isItemRemoved()) && (paramView.getViewLayoutPosition() >= 0) && (paramView.getViewLayoutPosition() < paramState.getItemCount())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    void b()
    {
      int i;
      if (this.c) {
        i = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding();
      } else {
        i = LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding();
      }
      this.b = i;
    }
    
    public void b(View paramView)
    {
      if (this.c) {
        this.b = (LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd(paramView) + LinearLayoutManager.this.mOrientationHelper.getTotalSpaceChange());
      } else {
        this.b = LinearLayoutManager.this.mOrientationHelper.getDecoratedStart(paramView);
      }
      this.a = LinearLayoutManager.this.getPosition(paramView);
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("AnchorInfo{mPosition=");
      localStringBuilder.append(this.a);
      localStringBuilder.append(", mCoordinate=");
      localStringBuilder.append(this.b);
      localStringBuilder.append(", mLayoutFromEnd=");
      localStringBuilder.append(this.c);
      localStringBuilder.append(", mValid=");
      localStringBuilder.append(this.d);
      localStringBuilder.append('}');
      return localStringBuilder.toString();
    }
  }
  
  static class b
  {
    boolean a = true;
    int b;
    int c;
    int d;
    int e;
    int f;
    int g;
    int h = 0;
    boolean i = false;
    int j;
    List<RecyclerView.ViewHolder> k = null;
    boolean l;
    
    private View b()
    {
      int m = this.k.size();
      for (int n = 0; n < m; n++)
      {
        View localView = ((RecyclerView.ViewHolder)this.k.get(n)).itemView;
        RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)localView.getLayoutParams();
        if ((!localLayoutParams.isItemRemoved()) && (this.d == localLayoutParams.getViewLayoutPosition()))
        {
          a(localView);
          return localView;
        }
      }
      return null;
    }
    
    View a(RecyclerView.Recycler paramRecycler)
    {
      if (this.k != null) {
        return b();
      }
      paramRecycler = paramRecycler.getViewForPosition(this.d);
      this.d += this.e;
      return paramRecycler;
    }
    
    public void a()
    {
      a(null);
    }
    
    public void a(View paramView)
    {
      paramView = b(paramView);
      if (paramView == null) {
        this.d = -1;
      } else {
        this.d = ((RecyclerView.LayoutParams)paramView.getLayoutParams()).getViewLayoutPosition();
      }
    }
    
    boolean a(RecyclerView.State paramState)
    {
      int m = this.d;
      boolean bool;
      if ((m >= 0) && (m < paramState.getItemCount())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public View b(View paramView)
    {
      int m = this.k.size();
      Object localObject1 = null;
      int n = Integer.MAX_VALUE;
      int i1 = 0;
      Object localObject2;
      for (;;)
      {
        localObject2 = localObject1;
        if (i1 >= m) {
          break;
        }
        localObject2 = ((RecyclerView.ViewHolder)this.k.get(i1)).itemView;
        RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)((View)localObject2).getLayoutParams();
        Object localObject3 = localObject1;
        int i2 = n;
        if (localObject2 != paramView) {
          if (localLayoutParams.isItemRemoved())
          {
            localObject3 = localObject1;
            i2 = n;
          }
          else
          {
            int i3 = (localLayoutParams.getViewLayoutPosition() - this.d) * this.e;
            if (i3 < 0)
            {
              localObject3 = localObject1;
              i2 = n;
            }
            else
            {
              localObject3 = localObject1;
              i2 = n;
              if (i3 < n)
              {
                if (i3 == 0) {
                  break;
                }
                i2 = i3;
                localObject3 = localObject2;
              }
            }
          }
        }
        i1++;
        localObject1 = localObject3;
        n = i2;
      }
      return (View)localObject2;
    }
  }
}


/* Location:              ~/android/support/v7/widget/LinearLayoutManager.class
 *
 * Reversed by:           J
 */