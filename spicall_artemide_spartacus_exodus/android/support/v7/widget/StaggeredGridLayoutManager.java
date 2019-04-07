package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class StaggeredGridLayoutManager
  extends RecyclerView.LayoutManager
  implements RecyclerView.SmoothScroller.ScrollVectorProvider
{
  static final boolean DEBUG = false;
  @Deprecated
  public static final int GAP_HANDLING_LAZY = 1;
  public static final int GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS = 2;
  public static final int GAP_HANDLING_NONE = 0;
  public static final int HORIZONTAL = 0;
  static final int INVALID_OFFSET = Integer.MIN_VALUE;
  private static final float MAX_SCROLL_FACTOR = 0.33333334F;
  private static final String TAG = "StaggeredGridLManager";
  public static final int VERTICAL = 1;
  private final a mAnchorInfo = new a();
  private final Runnable mCheckForGapsRunnable;
  private int mFullSizeSpec;
  private int mGapStrategy = 2;
  private boolean mLaidOutInvalidFullSpan = false;
  private boolean mLastLayoutFromEnd;
  private boolean mLastLayoutRTL;
  private final v mLayoutState;
  b mLazySpanLookup = new b();
  private int mOrientation;
  private SavedState mPendingSavedState;
  int mPendingScrollPosition = -1;
  int mPendingScrollPositionOffset = Integer.MIN_VALUE;
  private int[] mPrefetchDistances;
  OrientationHelper mPrimaryOrientation;
  private BitSet mRemainingSpans;
  boolean mReverseLayout = false;
  OrientationHelper mSecondaryOrientation;
  boolean mShouldReverseLayout = false;
  private int mSizePerSpan;
  private boolean mSmoothScrollbarEnabled;
  private int mSpanCount = -1;
  c[] mSpans;
  private final Rect mTmpRect = new Rect();
  
  public StaggeredGridLayoutManager(int paramInt1, int paramInt2)
  {
    boolean bool = true;
    this.mSmoothScrollbarEnabled = true;
    this.mCheckForGapsRunnable = new Runnable()
    {
      public void run()
      {
        StaggeredGridLayoutManager.this.checkForGaps();
      }
    };
    this.mOrientation = paramInt2;
    setSpanCount(paramInt1);
    if (this.mGapStrategy == 0) {
      bool = false;
    }
    setAutoMeasureEnabled(bool);
    this.mLayoutState = new v();
    createOrientationHelpers();
  }
  
  public StaggeredGridLayoutManager(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    boolean bool = true;
    this.mSmoothScrollbarEnabled = true;
    this.mCheckForGapsRunnable = new Runnable()
    {
      public void run()
      {
        StaggeredGridLayoutManager.this.checkForGaps();
      }
    };
    paramContext = getProperties(paramContext, paramAttributeSet, paramInt1, paramInt2);
    setOrientation(paramContext.orientation);
    setSpanCount(paramContext.spanCount);
    setReverseLayout(paramContext.reverseLayout);
    if (this.mGapStrategy == 0) {
      bool = false;
    }
    setAutoMeasureEnabled(bool);
    this.mLayoutState = new v();
    createOrientationHelpers();
  }
  
  private void appendViewToAllSpans(View paramView)
  {
    for (int i = this.mSpanCount - 1; i >= 0; i--) {
      this.mSpans[i].b(paramView);
    }
  }
  
  private void applyPendingSavedState(a parama)
  {
    if (this.mPendingSavedState.mSpanOffsetsSize > 0)
    {
      if (this.mPendingSavedState.mSpanOffsetsSize == this.mSpanCount) {
        for (int i = 0; i < this.mSpanCount; i++)
        {
          this.mSpans[i].e();
          int j = this.mPendingSavedState.mSpanOffsets[i];
          int k = j;
          if (j != Integer.MIN_VALUE) {
            if (this.mPendingSavedState.mAnchorLayoutFromEnd) {
              k = j + this.mPrimaryOrientation.getEndAfterPadding();
            } else {
              k = j + this.mPrimaryOrientation.getStartAfterPadding();
            }
          }
          this.mSpans[i].c(k);
        }
      }
      this.mPendingSavedState.invalidateSpanInfo();
      SavedState localSavedState = this.mPendingSavedState;
      localSavedState.mAnchorPosition = localSavedState.mVisibleAnchorPosition;
    }
    this.mLastLayoutRTL = this.mPendingSavedState.mLastLayoutRTL;
    setReverseLayout(this.mPendingSavedState.mReverseLayout);
    resolveShouldLayoutReverse();
    if (this.mPendingSavedState.mAnchorPosition != -1)
    {
      this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
      parama.c = this.mPendingSavedState.mAnchorLayoutFromEnd;
    }
    else
    {
      parama.c = this.mShouldReverseLayout;
    }
    if (this.mPendingSavedState.mSpanLookupSize > 1)
    {
      this.mLazySpanLookup.a = this.mPendingSavedState.mSpanLookup;
      this.mLazySpanLookup.b = this.mPendingSavedState.mFullSpanItems;
    }
  }
  
  private void attachViewToSpans(View paramView, LayoutParams paramLayoutParams, v paramv)
  {
    if (paramv.e == 1)
    {
      if (paramLayoutParams.mFullSpan) {
        appendViewToAllSpans(paramView);
      } else {
        paramLayoutParams.mSpan.b(paramView);
      }
    }
    else if (paramLayoutParams.mFullSpan) {
      prependViewToAllSpans(paramView);
    } else {
      paramLayoutParams.mSpan.a(paramView);
    }
  }
  
  private int calculateScrollDirectionForPosition(int paramInt)
  {
    int i = getChildCount();
    int j = -1;
    if (i == 0)
    {
      if (this.mShouldReverseLayout) {
        j = 1;
      }
      return j;
    }
    int k;
    if (paramInt < getFirstChildPosition()) {
      k = 1;
    } else {
      k = 0;
    }
    if (k == this.mShouldReverseLayout) {
      j = 1;
    }
    return j;
  }
  
  private boolean checkSpanForGap(c paramc)
  {
    if (this.mShouldReverseLayout)
    {
      if (paramc.d() < this.mPrimaryOrientation.getEndAfterPadding()) {
        return paramc.c((View)paramc.a.get(paramc.a.size() - 1)).mFullSpan ^ true;
      }
    }
    else if (paramc.b() > this.mPrimaryOrientation.getStartAfterPadding()) {
      return paramc.c((View)paramc.a.get(0)).mFullSpan ^ true;
    }
    return false;
  }
  
  private int computeScrollExtent(RecyclerView.State paramState)
  {
    if (getChildCount() == 0) {
      return 0;
    }
    return ab.a(paramState, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ true), findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ true), this, this.mSmoothScrollbarEnabled);
  }
  
  private int computeScrollOffset(RecyclerView.State paramState)
  {
    if (getChildCount() == 0) {
      return 0;
    }
    return ab.a(paramState, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ true), findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
  }
  
  private int computeScrollRange(RecyclerView.State paramState)
  {
    if (getChildCount() == 0) {
      return 0;
    }
    return ab.b(paramState, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ true), findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ true), this, this.mSmoothScrollbarEnabled);
  }
  
  private int convertFocusDirectionToLayoutDirection(int paramInt)
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
  
  private StaggeredGridLayoutManager.b.a createFullSpanItemFromEnd(int paramInt)
  {
    StaggeredGridLayoutManager.b.a locala = new StaggeredGridLayoutManager.b.a();
    locala.c = new int[this.mSpanCount];
    for (int i = 0; i < this.mSpanCount; i++) {
      locala.c[i] = (paramInt - this.mSpans[i].b(paramInt));
    }
    return locala;
  }
  
  private StaggeredGridLayoutManager.b.a createFullSpanItemFromStart(int paramInt)
  {
    StaggeredGridLayoutManager.b.a locala = new StaggeredGridLayoutManager.b.a();
    locala.c = new int[this.mSpanCount];
    for (int i = 0; i < this.mSpanCount; i++) {
      locala.c[i] = (this.mSpans[i].a(paramInt) - paramInt);
    }
    return locala;
  }
  
  private void createOrientationHelpers()
  {
    this.mPrimaryOrientation = OrientationHelper.createOrientationHelper(this, this.mOrientation);
    this.mSecondaryOrientation = OrientationHelper.createOrientationHelper(this, 1 - this.mOrientation);
  }
  
  private int fill(RecyclerView.Recycler paramRecycler, v paramv, RecyclerView.State paramState)
  {
    this.mRemainingSpans.set(0, this.mSpanCount, true);
    int i;
    if (this.mLayoutState.i)
    {
      if (paramv.e == 1) {
        i = Integer.MAX_VALUE;
      } else {
        i = Integer.MIN_VALUE;
      }
    }
    else if (paramv.e == 1) {
      i = paramv.g + paramv.b;
    } else {
      i = paramv.f - paramv.b;
    }
    updateAllRemainingSpans(paramv.e, i);
    if (this.mShouldReverseLayout) {
      j = this.mPrimaryOrientation.getEndAfterPadding();
    } else {
      j = this.mPrimaryOrientation.getStartAfterPadding();
    }
    for (int k = 0; paramv.a(paramState); m = 1)
    {
      if ((!this.mLayoutState.i) && (this.mRemainingSpans.isEmpty())) {
        break;
      }
      View localView = paramv.a(paramRecycler);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      int n = localLayoutParams.getViewLayoutPosition();
      k = this.mLazySpanLookup.c(n);
      int i1;
      if (k == -1) {
        i1 = 1;
      } else {
        i1 = 0;
      }
      c localc;
      if (i1 != 0)
      {
        if (localLayoutParams.mFullSpan) {
          localc = this.mSpans[0];
        } else {
          localc = getNextSpan(paramv);
        }
        this.mLazySpanLookup.a(n, localc);
      }
      else
      {
        localc = this.mSpans[k];
      }
      localLayoutParams.mSpan = localc;
      if (paramv.e == 1) {
        addView(localView);
      } else {
        addView(localView, 0);
      }
      measureChildWithDecorationsAndMargin(localView, localLayoutParams, false);
      int i2;
      StaggeredGridLayoutManager.b.a locala;
      int i3;
      if (paramv.e == 1)
      {
        if (localLayoutParams.mFullSpan) {
          k = getMaxEnd(j);
        } else {
          k = localc.b(j);
        }
        i2 = this.mPrimaryOrientation.getDecoratedMeasurement(localView);
        if ((i1 != 0) && (localLayoutParams.mFullSpan))
        {
          locala = createFullSpanItemFromEnd(k);
          locala.b = -1;
          locala.a = n;
          this.mLazySpanLookup.a(locala);
        }
        i2 += k;
        i3 = k;
      }
      else
      {
        if (localLayoutParams.mFullSpan) {
          k = getMinStart(j);
        } else {
          k = localc.a(j);
        }
        i3 = k - this.mPrimaryOrientation.getDecoratedMeasurement(localView);
        if ((i1 != 0) && (localLayoutParams.mFullSpan))
        {
          locala = createFullSpanItemFromStart(k);
          locala.b = 1;
          locala.a = n;
          this.mLazySpanLookup.a(locala);
        }
        i2 = k;
      }
      if ((localLayoutParams.mFullSpan) && (paramv.d == -1)) {
        if (i1 != 0)
        {
          this.mLaidOutInvalidFullSpan = true;
        }
        else
        {
          boolean bool;
          if (paramv.e == 1) {
            bool = areAllEndsEqual() ^ true;
          } else {
            bool = areAllStartsEqual() ^ true;
          }
          if (bool)
          {
            locala = this.mLazySpanLookup.f(n);
            if (locala != null) {
              locala.d = true;
            }
            this.mLaidOutInvalidFullSpan = true;
          }
        }
      }
      attachViewToSpans(localView, localLayoutParams, paramv);
      if ((isLayoutRTL()) && (this.mOrientation == 1))
      {
        if (localLayoutParams.mFullSpan) {
          m = this.mSecondaryOrientation.getEndAfterPadding();
        } else {
          m = this.mSecondaryOrientation.getEndAfterPadding() - (this.mSpanCount - 1 - localc.e) * this.mSizePerSpan;
        }
        n = this.mSecondaryOrientation.getDecoratedMeasurement(localView);
        i1 = m;
        n = m - n;
        m = i1;
      }
      else
      {
        if (localLayoutParams.mFullSpan) {
          m = this.mSecondaryOrientation.getStartAfterPadding();
        } else {
          m = localc.e * this.mSizePerSpan + this.mSecondaryOrientation.getStartAfterPadding();
        }
        n = this.mSecondaryOrientation.getDecoratedMeasurement(localView);
        i1 = m;
        m = n + m;
        n = i1;
      }
      if (this.mOrientation == 1) {
        layoutDecoratedWithMargins(localView, n, i3, m, i2);
      } else {
        layoutDecoratedWithMargins(localView, i3, n, i2, m);
      }
      if (localLayoutParams.mFullSpan) {
        updateAllRemainingSpans(this.mLayoutState.e, i);
      } else {
        updateRemainingSpans(localc, this.mLayoutState.e, i);
      }
      recycle(paramRecycler, this.mLayoutState);
      if ((this.mLayoutState.h) && (localView.hasFocusable())) {
        if (localLayoutParams.mFullSpan) {
          this.mRemainingSpans.clear();
        } else {
          this.mRemainingSpans.set(localc.e, false);
        }
      }
    }
    int j = 0;
    if (m == 0) {
      recycle(paramRecycler, this.mLayoutState);
    }
    if (this.mLayoutState.e == -1)
    {
      i = getMinStart(this.mPrimaryOrientation.getStartAfterPadding());
      i = this.mPrimaryOrientation.getStartAfterPadding() - i;
    }
    else
    {
      i = getMaxEnd(this.mPrimaryOrientation.getEndAfterPadding()) - this.mPrimaryOrientation.getEndAfterPadding();
    }
    int m = j;
    if (i > 0) {
      m = Math.min(paramv.b, i);
    }
    return m;
  }
  
  private int findFirstReferenceChildPosition(int paramInt)
  {
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      int k = getPosition(getChildAt(j));
      if ((k >= 0) && (k < paramInt)) {
        return k;
      }
    }
    return 0;
  }
  
  private int findLastReferenceChildPosition(int paramInt)
  {
    for (int i = getChildCount() - 1; i >= 0; i--)
    {
      int j = getPosition(getChildAt(i));
      if ((j >= 0) && (j < paramInt)) {
        return j;
      }
    }
    return 0;
  }
  
  private void fixEndGap(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean)
  {
    int i = getMaxEnd(Integer.MIN_VALUE);
    if (i == Integer.MIN_VALUE) {
      return;
    }
    i = this.mPrimaryOrientation.getEndAfterPadding() - i;
    if (i > 0)
    {
      i -= -scrollBy(-i, paramRecycler, paramState);
      if ((paramBoolean) && (i > 0)) {
        this.mPrimaryOrientation.offsetChildren(i);
      }
      return;
    }
  }
  
  private void fixStartGap(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean)
  {
    int i = getMinStart(Integer.MAX_VALUE);
    if (i == Integer.MAX_VALUE) {
      return;
    }
    i -= this.mPrimaryOrientation.getStartAfterPadding();
    if (i > 0)
    {
      i -= scrollBy(i, paramRecycler, paramState);
      if ((paramBoolean) && (i > 0)) {
        this.mPrimaryOrientation.offsetChildren(-i);
      }
      return;
    }
  }
  
  private int getMaxEnd(int paramInt)
  {
    int i = this.mSpans[0].b(paramInt);
    int j = 1;
    while (j < this.mSpanCount)
    {
      int k = this.mSpans[j].b(paramInt);
      int m = i;
      if (k > i) {
        m = k;
      }
      j++;
      i = m;
    }
    return i;
  }
  
  private int getMaxStart(int paramInt)
  {
    int i = this.mSpans[0].a(paramInt);
    int j = 1;
    while (j < this.mSpanCount)
    {
      int k = this.mSpans[j].a(paramInt);
      int m = i;
      if (k > i) {
        m = k;
      }
      j++;
      i = m;
    }
    return i;
  }
  
  private int getMinEnd(int paramInt)
  {
    int i = this.mSpans[0].b(paramInt);
    int j = 1;
    while (j < this.mSpanCount)
    {
      int k = this.mSpans[j].b(paramInt);
      int m = i;
      if (k < i) {
        m = k;
      }
      j++;
      i = m;
    }
    return i;
  }
  
  private int getMinStart(int paramInt)
  {
    int i = this.mSpans[0].a(paramInt);
    int j = 1;
    while (j < this.mSpanCount)
    {
      int k = this.mSpans[j].a(paramInt);
      int m = i;
      if (k < i) {
        m = k;
      }
      j++;
      i = m;
    }
    return i;
  }
  
  private c getNextSpan(v paramv)
  {
    boolean bool = preferLastSpan(paramv.e);
    int i = -1;
    int j;
    int k;
    if (bool)
    {
      j = this.mSpanCount - 1;
      k = -1;
    }
    else
    {
      j = 0;
      i = this.mSpanCount;
      k = 1;
    }
    int m = paramv.e;
    c localc = null;
    paramv = null;
    int i1;
    int i2;
    if (m == 1)
    {
      m = Integer.MAX_VALUE;
      n = this.mPrimaryOrientation.getStartAfterPadding();
      while (j != i)
      {
        localc = this.mSpans[j];
        i1 = localc.b(n);
        i2 = m;
        if (i1 < m)
        {
          paramv = localc;
          i2 = i1;
        }
        j += k;
        m = i2;
      }
      return paramv;
    }
    m = Integer.MIN_VALUE;
    int n = this.mPrimaryOrientation.getEndAfterPadding();
    paramv = localc;
    while (j != i)
    {
      localc = this.mSpans[j];
      i1 = localc.a(n);
      i2 = m;
      if (i1 > m)
      {
        paramv = localc;
        i2 = i1;
      }
      j += k;
      m = i2;
    }
    return paramv;
  }
  
  private void handleUpdate(int paramInt1, int paramInt2, int paramInt3)
  {
    int i;
    if (this.mShouldReverseLayout) {
      i = getLastChildPosition();
    } else {
      i = getFirstChildPosition();
    }
    int j;
    int k;
    if (paramInt3 == 8)
    {
      if (paramInt1 < paramInt2)
      {
        j = paramInt2 + 1;
        k = paramInt1;
      }
      else
      {
        j = paramInt1 + 1;
        k = paramInt2;
      }
    }
    else
    {
      j = paramInt1 + paramInt2;
      k = paramInt1;
    }
    this.mLazySpanLookup.b(k);
    if (paramInt3 != 8)
    {
      switch (paramInt3)
      {
      default: 
        break;
      case 2: 
        this.mLazySpanLookup.a(paramInt1, paramInt2);
        break;
      case 1: 
        this.mLazySpanLookup.b(paramInt1, paramInt2);
        break;
      }
    }
    else
    {
      this.mLazySpanLookup.a(paramInt1, 1);
      this.mLazySpanLookup.b(paramInt2, 1);
    }
    if (j <= i) {
      return;
    }
    if (this.mShouldReverseLayout) {
      paramInt1 = getFirstChildPosition();
    } else {
      paramInt1 = getLastChildPosition();
    }
    if (k <= paramInt1) {
      requestLayout();
    }
  }
  
  private void measureChildWithDecorationsAndMargin(View paramView, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    calculateItemDecorationsForChild(paramView, this.mTmpRect);
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    paramInt1 = updateSpecWithExtra(paramInt1, localLayoutParams.leftMargin + this.mTmpRect.left, localLayoutParams.rightMargin + this.mTmpRect.right);
    paramInt2 = updateSpecWithExtra(paramInt2, localLayoutParams.topMargin + this.mTmpRect.top, localLayoutParams.bottomMargin + this.mTmpRect.bottom);
    if (paramBoolean) {
      paramBoolean = shouldReMeasureChild(paramView, paramInt1, paramInt2, localLayoutParams);
    } else {
      paramBoolean = shouldMeasureChild(paramView, paramInt1, paramInt2, localLayoutParams);
    }
    if (paramBoolean) {
      paramView.measure(paramInt1, paramInt2);
    }
  }
  
  private void measureChildWithDecorationsAndMargin(View paramView, LayoutParams paramLayoutParams, boolean paramBoolean)
  {
    if (paramLayoutParams.mFullSpan)
    {
      if (this.mOrientation == 1) {
        measureChildWithDecorationsAndMargin(paramView, this.mFullSizeSpec, getChildMeasureSpec(getHeight(), getHeightMode(), 0, paramLayoutParams.height, true), paramBoolean);
      } else {
        measureChildWithDecorationsAndMargin(paramView, getChildMeasureSpec(getWidth(), getWidthMode(), 0, paramLayoutParams.width, true), this.mFullSizeSpec, paramBoolean);
      }
    }
    else if (this.mOrientation == 1) {
      measureChildWithDecorationsAndMargin(paramView, getChildMeasureSpec(this.mSizePerSpan, getWidthMode(), 0, paramLayoutParams.width, false), getChildMeasureSpec(getHeight(), getHeightMode(), 0, paramLayoutParams.height, true), paramBoolean);
    } else {
      measureChildWithDecorationsAndMargin(paramView, getChildMeasureSpec(getWidth(), getWidthMode(), 0, paramLayoutParams.width, true), getChildMeasureSpec(this.mSizePerSpan, getHeightMode(), 0, paramLayoutParams.height, false), paramBoolean);
    }
  }
  
  private void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean)
  {
    a locala = this.mAnchorInfo;
    if (((this.mPendingSavedState != null) || (this.mPendingScrollPosition != -1)) && (paramState.getItemCount() == 0))
    {
      removeAndRecycleAllViews(paramRecycler);
      locala.a();
      return;
    }
    boolean bool = locala.e;
    int i = 1;
    if ((bool) && (this.mPendingScrollPosition == -1) && (this.mPendingSavedState == null)) {
      j = 0;
    } else {
      j = 1;
    }
    if (j != 0)
    {
      locala.a();
      if (this.mPendingSavedState != null)
      {
        applyPendingSavedState(locala);
      }
      else
      {
        resolveShouldLayoutReverse();
        locala.c = this.mShouldReverseLayout;
      }
      updateAnchorInfoForLayout(paramState, locala);
      locala.e = true;
    }
    if ((this.mPendingSavedState == null) && (this.mPendingScrollPosition == -1) && ((locala.c != this.mLastLayoutFromEnd) || (isLayoutRTL() != this.mLastLayoutRTL)))
    {
      this.mLazySpanLookup.a();
      locala.d = true;
    }
    if (getChildCount() > 0)
    {
      Object localObject = this.mPendingSavedState;
      if ((localObject == null) || (((SavedState)localObject).mSpanOffsetsSize < 1))
      {
        if (locala.d) {
          for (j = 0; j < this.mSpanCount; j++)
          {
            this.mSpans[j].e();
            if (locala.b != Integer.MIN_VALUE) {
              this.mSpans[j].c(locala.b);
            }
          }
        }
        if ((j == 0) && (this.mAnchorInfo.f != null)) {
          j = 0;
        }
        while (j < this.mSpanCount)
        {
          localObject = this.mSpans[j];
          ((c)localObject).e();
          ((c)localObject).c(this.mAnchorInfo.f[j]);
          j++;
          continue;
          for (j = 0; j < this.mSpanCount; j++) {
            this.mSpans[j].a(this.mShouldReverseLayout, locala.b);
          }
          this.mAnchorInfo.a(this.mSpans);
        }
      }
    }
    detachAndScrapAttachedViews(paramRecycler);
    this.mLayoutState.a = false;
    this.mLaidOutInvalidFullSpan = false;
    updateMeasureSpecs(this.mSecondaryOrientation.getTotalSpace());
    updateLayoutState(locala.a, paramState);
    if (locala.c)
    {
      setLayoutStateDirection(-1);
      fill(paramRecycler, this.mLayoutState, paramState);
      setLayoutStateDirection(1);
      this.mLayoutState.c = (locala.a + this.mLayoutState.d);
      fill(paramRecycler, this.mLayoutState, paramState);
    }
    else
    {
      setLayoutStateDirection(1);
      fill(paramRecycler, this.mLayoutState, paramState);
      setLayoutStateDirection(-1);
      this.mLayoutState.c = (locala.a + this.mLayoutState.d);
      fill(paramRecycler, this.mLayoutState, paramState);
    }
    repositionToWrapContentIfNecessary();
    if (getChildCount() > 0) {
      if (this.mShouldReverseLayout)
      {
        fixEndGap(paramRecycler, paramState, true);
        fixStartGap(paramRecycler, paramState, false);
      }
      else
      {
        fixStartGap(paramRecycler, paramState, true);
        fixEndGap(paramRecycler, paramState, false);
      }
    }
    if ((paramBoolean) && (!paramState.isPreLayout()))
    {
      if ((this.mGapStrategy != 0) && (getChildCount() > 0) && ((this.mLaidOutInvalidFullSpan) || (hasGapsToFix() != null))) {
        j = 1;
      } else {
        j = 0;
      }
      if (j != 0)
      {
        removeCallbacks(this.mCheckForGapsRunnable);
        if (checkForGaps())
        {
          j = i;
          break label667;
        }
      }
    }
    int j = 0;
    label667:
    if (paramState.isPreLayout()) {
      this.mAnchorInfo.a();
    }
    this.mLastLayoutFromEnd = locala.c;
    this.mLastLayoutRTL = isLayoutRTL();
    if (j != 0)
    {
      this.mAnchorInfo.a();
      onLayoutChildren(paramRecycler, paramState, false);
    }
  }
  
  private boolean preferLastSpan(int paramInt)
  {
    int i = this.mOrientation;
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3;
    if (i == 0)
    {
      if (paramInt == -1) {
        bool3 = true;
      } else {
        bool3 = false;
      }
      if (bool3 != this.mShouldReverseLayout) {
        bool3 = bool2;
      } else {
        bool3 = false;
      }
      return bool3;
    }
    if (paramInt == -1) {
      bool3 = true;
    } else {
      bool3 = false;
    }
    if (bool3 == this.mShouldReverseLayout) {
      bool3 = true;
    } else {
      bool3 = false;
    }
    if (bool3 == isLayoutRTL()) {
      bool3 = bool1;
    } else {
      bool3 = false;
    }
    return bool3;
  }
  
  private void prependViewToAllSpans(View paramView)
  {
    for (int i = this.mSpanCount - 1; i >= 0; i--) {
      this.mSpans[i].a(paramView);
    }
  }
  
  private void recycle(RecyclerView.Recycler paramRecycler, v paramv)
  {
    if ((paramv.a) && (!paramv.i))
    {
      if (paramv.b == 0)
      {
        if (paramv.e == -1) {
          recycleFromEnd(paramRecycler, paramv.g);
        } else {
          recycleFromStart(paramRecycler, paramv.f);
        }
      }
      else
      {
        int i;
        if (paramv.e == -1)
        {
          i = paramv.f - getMaxStart(paramv.f);
          if (i < 0) {
            i = paramv.g;
          } else {
            i = paramv.g - Math.min(i, paramv.b);
          }
          recycleFromEnd(paramRecycler, i);
        }
        else
        {
          int j = getMinEnd(paramv.g) - paramv.g;
          if (j < 0)
          {
            i = paramv.f;
          }
          else
          {
            i = paramv.f;
            i = Math.min(j, paramv.b) + i;
          }
          recycleFromStart(paramRecycler, i);
        }
      }
      return;
    }
  }
  
  private void recycleFromEnd(RecyclerView.Recycler paramRecycler, int paramInt)
  {
    int i = getChildCount() - 1;
    while (i >= 0)
    {
      View localView = getChildAt(i);
      if ((this.mPrimaryOrientation.getDecoratedStart(localView) >= paramInt) && (this.mPrimaryOrientation.getTransformedStartWithDecoration(localView) >= paramInt))
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (localLayoutParams.mFullSpan)
        {
          int j = 0;
          int m;
          for (int k = 0;; k++)
          {
            m = j;
            if (k >= this.mSpanCount) {
              break;
            }
            if (this.mSpans[k].a.size() == 1) {
              return;
            }
          }
          while (m < this.mSpanCount)
          {
            this.mSpans[m].g();
            m++;
          }
        }
        if (localLayoutParams.mSpan.a.size() == 1) {
          return;
        }
        localLayoutParams.mSpan.g();
        removeAndRecycleView(localView, paramRecycler);
        i--;
      }
      else {}
    }
  }
  
  private void recycleFromStart(RecyclerView.Recycler paramRecycler, int paramInt)
  {
    while (getChildCount() > 0)
    {
      int i = 0;
      View localView = getChildAt(0);
      if ((this.mPrimaryOrientation.getDecoratedEnd(localView) <= paramInt) && (this.mPrimaryOrientation.getTransformedEndWithDecoration(localView) <= paramInt))
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (localLayoutParams.mFullSpan)
        {
          int k;
          for (int j = 0;; j++)
          {
            k = i;
            if (j >= this.mSpanCount) {
              break;
            }
            if (this.mSpans[j].a.size() == 1) {
              return;
            }
          }
          while (k < this.mSpanCount)
          {
            this.mSpans[k].h();
            k++;
          }
        }
        if (localLayoutParams.mSpan.a.size() == 1) {
          return;
        }
        localLayoutParams.mSpan.h();
        removeAndRecycleView(localView, paramRecycler);
      }
      else {}
    }
  }
  
  private void repositionToWrapContentIfNecessary()
  {
    if (this.mSecondaryOrientation.getMode() == 1073741824) {
      return;
    }
    int i = getChildCount();
    int j = 0;
    int k = 0;
    float f1 = 0.0F;
    View localView;
    while (k < i)
    {
      localView = getChildAt(k);
      float f2 = this.mSecondaryOrientation.getDecoratedMeasurement(localView);
      if (f2 >= f1)
      {
        float f3 = f2;
        if (((LayoutParams)localView.getLayoutParams()).isFullSpan()) {
          f3 = f2 * 1.0F / this.mSpanCount;
        }
        f1 = Math.max(f1, f3);
      }
      k++;
    }
    int m = this.mSizePerSpan;
    int n = Math.round(f1 * this.mSpanCount);
    k = n;
    if (this.mSecondaryOrientation.getMode() == Integer.MIN_VALUE) {
      k = Math.min(n, this.mSecondaryOrientation.getTotalSpace());
    }
    updateMeasureSpecs(k);
    k = j;
    if (this.mSizePerSpan == m) {
      return;
    }
    while (k < i)
    {
      localView = getChildAt(k);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      if (!localLayoutParams.mFullSpan) {
        if ((isLayoutRTL()) && (this.mOrientation == 1))
        {
          localView.offsetLeftAndRight(-(this.mSpanCount - 1 - localLayoutParams.mSpan.e) * this.mSizePerSpan - -(this.mSpanCount - 1 - localLayoutParams.mSpan.e) * m);
        }
        else
        {
          j = localLayoutParams.mSpan.e * this.mSizePerSpan;
          n = localLayoutParams.mSpan.e * m;
          if (this.mOrientation == 1) {
            localView.offsetLeftAndRight(j - n);
          } else {
            localView.offsetTopAndBottom(j - n);
          }
        }
      }
      k++;
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
  
  private void setLayoutStateDirection(int paramInt)
  {
    v localv = this.mLayoutState;
    localv.e = paramInt;
    boolean bool1 = this.mShouldReverseLayout;
    int i = 1;
    boolean bool2;
    if (paramInt == -1) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    if (bool1 == bool2) {
      paramInt = i;
    } else {
      paramInt = -1;
    }
    localv.d = paramInt;
  }
  
  private void updateAllRemainingSpans(int paramInt1, int paramInt2)
  {
    for (int i = 0; i < this.mSpanCount; i++) {
      if (!this.mSpans[i].a.isEmpty()) {
        updateRemainingSpans(this.mSpans[i], paramInt1, paramInt2);
      }
    }
  }
  
  private boolean updateAnchorFromChildren(RecyclerView.State paramState, a parama)
  {
    int i;
    if (this.mLastLayoutFromEnd) {
      i = findLastReferenceChildPosition(paramState.getItemCount());
    } else {
      i = findFirstReferenceChildPosition(paramState.getItemCount());
    }
    parama.a = i;
    parama.b = Integer.MIN_VALUE;
    return true;
  }
  
  private void updateLayoutState(int paramInt, RecyclerView.State paramState)
  {
    v localv = this.mLayoutState;
    boolean bool1 = false;
    localv.b = 0;
    localv.c = paramInt;
    if (isSmoothScrolling())
    {
      i = paramState.getTargetScrollPosition();
      if (i != -1)
      {
        boolean bool2 = this.mShouldReverseLayout;
        if (i < paramInt) {
          bool3 = true;
        } else {
          bool3 = false;
        }
        if (bool2 == bool3)
        {
          paramInt = this.mPrimaryOrientation.getTotalSpace();
          i = 0;
          break label98;
        }
        i = this.mPrimaryOrientation.getTotalSpace();
        paramInt = 0;
        break label98;
      }
    }
    paramInt = 0;
    int i = 0;
    label98:
    if (getClipToPadding())
    {
      this.mLayoutState.f = (this.mPrimaryOrientation.getStartAfterPadding() - i);
      this.mLayoutState.g = (this.mPrimaryOrientation.getEndAfterPadding() + paramInt);
    }
    else
    {
      this.mLayoutState.g = (this.mPrimaryOrientation.getEnd() + paramInt);
      this.mLayoutState.f = (-i);
    }
    paramState = this.mLayoutState;
    paramState.h = false;
    paramState.a = true;
    boolean bool3 = bool1;
    if (this.mPrimaryOrientation.getMode() == 0)
    {
      bool3 = bool1;
      if (this.mPrimaryOrientation.getEnd() == 0) {
        bool3 = true;
      }
    }
    paramState.i = bool3;
  }
  
  private void updateRemainingSpans(c paramc, int paramInt1, int paramInt2)
  {
    int i = paramc.i();
    if (paramInt1 == -1)
    {
      if (paramc.b() + i <= paramInt2) {
        this.mRemainingSpans.set(paramc.e, false);
      }
    }
    else if (paramc.d() - i >= paramInt2) {
      this.mRemainingSpans.set(paramc.e, false);
    }
  }
  
  private int updateSpecWithExtra(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt2 == 0) && (paramInt3 == 0)) {
      return paramInt1;
    }
    int i = View.MeasureSpec.getMode(paramInt1);
    if ((i != Integer.MIN_VALUE) && (i != 1073741824)) {
      return paramInt1;
    }
    return View.MeasureSpec.makeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(paramInt1) - paramInt2 - paramInt3), i);
  }
  
  boolean areAllEndsEqual()
  {
    int i = this.mSpans[0].b(Integer.MIN_VALUE);
    for (int j = 1; j < this.mSpanCount; j++) {
      if (this.mSpans[j].b(Integer.MIN_VALUE) != i) {
        return false;
      }
    }
    return true;
  }
  
  boolean areAllStartsEqual()
  {
    int i = this.mSpans[0].a(Integer.MIN_VALUE);
    for (int j = 1; j < this.mSpanCount; j++) {
      if (this.mSpans[j].a(Integer.MIN_VALUE) != i) {
        return false;
      }
    }
    return true;
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
  
  boolean checkForGaps()
  {
    if ((getChildCount() != 0) && (this.mGapStrategy != 0) && (isAttachedToWindow()))
    {
      int i;
      int j;
      if (this.mShouldReverseLayout)
      {
        i = getLastChildPosition();
        j = getFirstChildPosition();
      }
      else
      {
        i = getFirstChildPosition();
        j = getLastChildPosition();
      }
      if ((i == 0) && (hasGapsToFix() != null))
      {
        this.mLazySpanLookup.a();
        requestSimpleAnimationsInNextLayout();
        requestLayout();
        return true;
      }
      if (!this.mLaidOutInvalidFullSpan) {
        return false;
      }
      int k;
      if (this.mShouldReverseLayout) {
        k = -1;
      } else {
        k = 1;
      }
      Object localObject = this.mLazySpanLookup;
      j++;
      localObject = ((b)localObject).a(i, j, k, true);
      if (localObject == null)
      {
        this.mLaidOutInvalidFullSpan = false;
        this.mLazySpanLookup.a(j);
        return false;
      }
      StaggeredGridLayoutManager.b.a locala = this.mLazySpanLookup.a(i, ((StaggeredGridLayoutManager.b.a)localObject).a, k * -1, true);
      if (locala == null) {
        this.mLazySpanLookup.a(((StaggeredGridLayoutManager.b.a)localObject).a);
      } else {
        this.mLazySpanLookup.a(locala.a + 1);
      }
      requestSimpleAnimationsInNextLayout();
      requestLayout();
      return true;
    }
    return false;
  }
  
  public boolean checkLayoutParams(RecyclerView.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  public void collectAdjacentPrefetchPositions(int paramInt1, int paramInt2, RecyclerView.State paramState, RecyclerView.LayoutManager.LayoutPrefetchRegistry paramLayoutPrefetchRegistry)
  {
    if (this.mOrientation != 0) {
      paramInt1 = paramInt2;
    }
    if ((getChildCount() != 0) && (paramInt1 != 0))
    {
      prepareLayoutStateForDelta(paramInt1, paramState);
      Object localObject = this.mPrefetchDistances;
      if ((localObject == null) || (localObject.length < this.mSpanCount)) {
        this.mPrefetchDistances = new int[this.mSpanCount];
      }
      int i = 0;
      paramInt2 = 0;
      int k;
      for (paramInt1 = 0; paramInt2 < this.mSpanCount; paramInt1 = k)
      {
        int j;
        if (this.mLayoutState.d == -1) {
          j = this.mLayoutState.f - this.mSpans[paramInt2].a(this.mLayoutState.f);
        } else {
          j = this.mSpans[paramInt2].b(this.mLayoutState.g) - this.mLayoutState.g;
        }
        k = paramInt1;
        if (j >= 0)
        {
          this.mPrefetchDistances[paramInt1] = j;
          k = paramInt1 + 1;
        }
        paramInt2++;
      }
      Arrays.sort(this.mPrefetchDistances, 0, paramInt1);
      for (paramInt2 = i; (paramInt2 < paramInt1) && (this.mLayoutState.a(paramState)); paramInt2++)
      {
        paramLayoutPrefetchRegistry.addPosition(this.mLayoutState.c, this.mPrefetchDistances[paramInt2]);
        localObject = this.mLayoutState;
        ((v)localObject).c += this.mLayoutState.d;
      }
      return;
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
    paramInt = calculateScrollDirectionForPosition(paramInt);
    PointF localPointF = new PointF();
    if (paramInt == 0) {
      return null;
    }
    if (this.mOrientation == 0)
    {
      localPointF.x = paramInt;
      localPointF.y = 0.0F;
    }
    else
    {
      localPointF.x = 0.0F;
      localPointF.y = paramInt;
    }
    return localPointF;
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
  
  public int[] findFirstCompletelyVisibleItemPositions(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) {
      paramArrayOfInt = new int[this.mSpanCount];
    } else {
      if (paramArrayOfInt.length < this.mSpanCount) {
        break label53;
      }
    }
    for (int i = 0; i < this.mSpanCount; i++) {
      paramArrayOfInt[i] = this.mSpans[i].l();
    }
    return paramArrayOfInt;
    label53:
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
    localStringBuilder.append(this.mSpanCount);
    localStringBuilder.append(", array size:");
    localStringBuilder.append(paramArrayOfInt.length);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  View findFirstVisibleItemClosestToEnd(boolean paramBoolean)
  {
    int i = this.mPrimaryOrientation.getStartAfterPadding();
    int j = this.mPrimaryOrientation.getEndAfterPadding();
    int k = getChildCount() - 1;
    Object localObject2;
    for (Object localObject1 = null; k >= 0; localObject1 = localObject2)
    {
      View localView = getChildAt(k);
      int m = this.mPrimaryOrientation.getDecoratedStart(localView);
      int n = this.mPrimaryOrientation.getDecoratedEnd(localView);
      localObject2 = localObject1;
      if (n > i) {
        if (m >= j)
        {
          localObject2 = localObject1;
        }
        else if ((n > j) && (paramBoolean))
        {
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = localView;
          }
        }
        else
        {
          return localView;
        }
      }
      k--;
    }
    return (View)localObject1;
  }
  
  View findFirstVisibleItemClosestToStart(boolean paramBoolean)
  {
    int i = this.mPrimaryOrientation.getStartAfterPadding();
    int j = this.mPrimaryOrientation.getEndAfterPadding();
    int k = getChildCount();
    Object localObject1 = null;
    int m = 0;
    while (m < k)
    {
      View localView = getChildAt(m);
      int n = this.mPrimaryOrientation.getDecoratedStart(localView);
      Object localObject2 = localObject1;
      if (this.mPrimaryOrientation.getDecoratedEnd(localView) > i) {
        if (n >= j)
        {
          localObject2 = localObject1;
        }
        else if ((n < i) && (paramBoolean))
        {
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = localView;
          }
        }
        else
        {
          return localView;
        }
      }
      m++;
      localObject1 = localObject2;
    }
    return (View)localObject1;
  }
  
  int findFirstVisibleItemPositionInt()
  {
    View localView;
    if (this.mShouldReverseLayout) {
      localView = findFirstVisibleItemClosestToEnd(true);
    } else {
      localView = findFirstVisibleItemClosestToStart(true);
    }
    int i;
    if (localView == null) {
      i = -1;
    } else {
      i = getPosition(localView);
    }
    return i;
  }
  
  public int[] findFirstVisibleItemPositions(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) {
      paramArrayOfInt = new int[this.mSpanCount];
    } else {
      if (paramArrayOfInt.length < this.mSpanCount) {
        break label53;
      }
    }
    for (int i = 0; i < this.mSpanCount; i++) {
      paramArrayOfInt[i] = this.mSpans[i].j();
    }
    return paramArrayOfInt;
    label53:
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
    localStringBuilder.append(this.mSpanCount);
    localStringBuilder.append(", array size:");
    localStringBuilder.append(paramArrayOfInt.length);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public int[] findLastCompletelyVisibleItemPositions(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) {
      paramArrayOfInt = new int[this.mSpanCount];
    } else {
      if (paramArrayOfInt.length < this.mSpanCount) {
        break label53;
      }
    }
    for (int i = 0; i < this.mSpanCount; i++) {
      paramArrayOfInt[i] = this.mSpans[i].o();
    }
    return paramArrayOfInt;
    label53:
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
    localStringBuilder.append(this.mSpanCount);
    localStringBuilder.append(", array size:");
    localStringBuilder.append(paramArrayOfInt.length);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public int[] findLastVisibleItemPositions(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) {
      paramArrayOfInt = new int[this.mSpanCount];
    } else {
      if (paramArrayOfInt.length < this.mSpanCount) {
        break label53;
      }
    }
    for (int i = 0; i < this.mSpanCount; i++) {
      paramArrayOfInt[i] = this.mSpans[i].m();
    }
    return paramArrayOfInt;
    label53:
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
    localStringBuilder.append(this.mSpanCount);
    localStringBuilder.append(", array size:");
    localStringBuilder.append(paramArrayOfInt.length);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public RecyclerView.LayoutParams generateDefaultLayoutParams()
  {
    if (this.mOrientation == 0) {
      return new LayoutParams(-2, -1);
    }
    return new LayoutParams(-1, -2);
  }
  
  public RecyclerView.LayoutParams generateLayoutParams(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new LayoutParams(paramContext, paramAttributeSet);
  }
  
  public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      return new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
    }
    return new LayoutParams(paramLayoutParams);
  }
  
  public int getColumnCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 1) {
      return this.mSpanCount;
    }
    return super.getColumnCountForAccessibility(paramRecycler, paramState);
  }
  
  int getFirstChildPosition()
  {
    int i = getChildCount();
    int j = 0;
    if (i != 0) {
      j = getPosition(getChildAt(0));
    }
    return j;
  }
  
  public int getGapStrategy()
  {
    return this.mGapStrategy;
  }
  
  int getLastChildPosition()
  {
    int i = getChildCount();
    if (i == 0) {
      i = 0;
    } else {
      i = getPosition(getChildAt(i - 1));
    }
    return i;
  }
  
  public int getOrientation()
  {
    return this.mOrientation;
  }
  
  public boolean getReverseLayout()
  {
    return this.mReverseLayout;
  }
  
  public int getRowCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 0) {
      return this.mSpanCount;
    }
    return super.getRowCountForAccessibility(paramRecycler, paramState);
  }
  
  public int getSpanCount()
  {
    return this.mSpanCount;
  }
  
  View hasGapsToFix()
  {
    int i = getChildCount() - 1;
    BitSet localBitSet = new BitSet(this.mSpanCount);
    localBitSet.set(0, this.mSpanCount, true);
    int j = this.mOrientation;
    int k = -1;
    if ((j == 1) && (isLayoutRTL())) {
      j = 1;
    } else {
      j = -1;
    }
    int m;
    if (this.mShouldReverseLayout)
    {
      m = -1;
    }
    else
    {
      m = i + 1;
      i = 0;
    }
    int n = i;
    if (i < m)
    {
      k = 1;
      n = i;
    }
    while (n != m)
    {
      View localView = getChildAt(n);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      if (localBitSet.get(localLayoutParams.mSpan.e))
      {
        if (checkSpanForGap(localLayoutParams.mSpan)) {
          return localView;
        }
        localBitSet.clear(localLayoutParams.mSpan.e);
      }
      if (!localLayoutParams.mFullSpan)
      {
        i = n + k;
        if (i != m)
        {
          Object localObject = getChildAt(i);
          int i1;
          if (this.mShouldReverseLayout)
          {
            i = this.mPrimaryOrientation.getDecoratedEnd(localView);
            i1 = this.mPrimaryOrientation.getDecoratedEnd((View)localObject);
            if (i < i1) {
              return localView;
            }
            if (i == i1) {
              i = 1;
            } else {
              i = 0;
            }
          }
          else
          {
            i = this.mPrimaryOrientation.getDecoratedStart(localView);
            i1 = this.mPrimaryOrientation.getDecoratedStart((View)localObject);
            if (i > i1) {
              return localView;
            }
            if (i == i1) {
              i = 1;
            } else {
              i = 0;
            }
          }
          if (i != 0)
          {
            localObject = (LayoutParams)((View)localObject).getLayoutParams();
            if (localLayoutParams.mSpan.e - ((LayoutParams)localObject).mSpan.e < 0) {
              i = 1;
            } else {
              i = 0;
            }
            if (j < 0) {
              i1 = 1;
            } else {
              i1 = 0;
            }
            if (i != i1) {
              return localView;
            }
          }
        }
      }
      n += k;
    }
    return null;
  }
  
  public void invalidateSpanAssignments()
  {
    this.mLazySpanLookup.a();
    requestLayout();
  }
  
  boolean isLayoutRTL()
  {
    int i = getLayoutDirection();
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  public void offsetChildrenHorizontal(int paramInt)
  {
    super.offsetChildrenHorizontal(paramInt);
    for (int i = 0; i < this.mSpanCount; i++) {
      this.mSpans[i].d(paramInt);
    }
  }
  
  public void offsetChildrenVertical(int paramInt)
  {
    super.offsetChildrenVertical(paramInt);
    for (int i = 0; i < this.mSpanCount; i++) {
      this.mSpans[i].d(paramInt);
    }
  }
  
  public void onDetachedFromWindow(RecyclerView paramRecyclerView, RecyclerView.Recycler paramRecycler)
  {
    removeCallbacks(this.mCheckForGapsRunnable);
    for (int i = 0; i < this.mSpanCount; i++) {
      this.mSpans[i].e();
    }
    paramRecyclerView.requestLayout();
  }
  
  public View onFocusSearchFailed(View paramView, int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (getChildCount() == 0) {
      return null;
    }
    paramView = findContainingItemView(paramView);
    if (paramView == null) {
      return null;
    }
    resolveShouldLayoutReverse();
    int i = convertFocusDirectionToLayoutDirection(paramInt);
    if (i == Integer.MIN_VALUE) {
      return null;
    }
    Object localObject = (LayoutParams)paramView.getLayoutParams();
    boolean bool1 = ((LayoutParams)localObject).mFullSpan;
    localObject = ((LayoutParams)localObject).mSpan;
    if (i == 1) {
      paramInt = getLastChildPosition();
    } else {
      paramInt = getFirstChildPosition();
    }
    updateLayoutState(paramInt, paramState);
    setLayoutStateDirection(i);
    v localv = this.mLayoutState;
    localv.c = (localv.d + paramInt);
    this.mLayoutState.b = ((int)(this.mPrimaryOrientation.getTotalSpace() * 0.33333334F));
    localv = this.mLayoutState;
    localv.h = true;
    int j = 0;
    localv.a = false;
    fill(paramRecycler, localv, paramState);
    this.mLastLayoutFromEnd = this.mShouldReverseLayout;
    if (!bool1)
    {
      paramRecycler = ((c)localObject).a(paramInt, i);
      if ((paramRecycler != null) && (paramRecycler != paramView)) {
        return paramRecycler;
      }
    }
    if (preferLastSpan(i)) {
      for (k = this.mSpanCount - 1; k >= 0; k--)
      {
        paramRecycler = this.mSpans[k].a(paramInt, i);
        if ((paramRecycler != null) && (paramRecycler != paramView)) {
          return paramRecycler;
        }
      }
    }
    for (int k = 0; k < this.mSpanCount; k++)
    {
      paramRecycler = this.mSpans[k].a(paramInt, i);
      if ((paramRecycler != null) && (paramRecycler != paramView)) {
        return paramRecycler;
      }
    }
    boolean bool2 = this.mReverseLayout;
    if (i == -1) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    if ((bool2 ^ true) == paramInt) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    if (!bool1)
    {
      if (paramInt != 0) {
        k = ((c)localObject).k();
      } else {
        k = ((c)localObject).n();
      }
      paramRecycler = findViewByPosition(k);
      if ((paramRecycler != null) && (paramRecycler != paramView)) {
        return paramRecycler;
      }
    }
    k = j;
    if (preferLastSpan(i)) {
      for (k = this.mSpanCount - 1; k >= 0; k--) {
        if (k != ((c)localObject).e)
        {
          if (paramInt != 0) {
            j = this.mSpans[k].k();
          } else {
            j = this.mSpans[k].n();
          }
          paramRecycler = findViewByPosition(j);
          if ((paramRecycler != null) && (paramRecycler != paramView)) {
            return paramRecycler;
          }
        }
      }
    }
    while (k < this.mSpanCount)
    {
      if (paramInt != 0) {
        j = this.mSpans[k].k();
      } else {
        j = this.mSpans[k].n();
      }
      paramRecycler = findViewByPosition(j);
      if ((paramRecycler != null) && (paramRecycler != paramView)) {
        return paramRecycler;
      }
      k++;
    }
    return null;
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    if (getChildCount() > 0)
    {
      View localView1 = findFirstVisibleItemClosestToStart(false);
      View localView2 = findFirstVisibleItemClosestToEnd(false);
      if ((localView1 != null) && (localView2 != null))
      {
        int i = getPosition(localView1);
        int j = getPosition(localView2);
        if (i < j)
        {
          paramAccessibilityEvent.setFromIndex(i);
          paramAccessibilityEvent.setToIndex(j);
        }
        else
        {
          paramAccessibilityEvent.setFromIndex(j);
          paramAccessibilityEvent.setToIndex(i);
        }
      }
      else {}
    }
  }
  
  public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
  {
    paramRecycler = paramView.getLayoutParams();
    if (!(paramRecycler instanceof LayoutParams))
    {
      super.onInitializeAccessibilityNodeInfoForItem(paramView, paramAccessibilityNodeInfoCompat);
      return;
    }
    paramRecycler = (LayoutParams)paramRecycler;
    int i;
    int j;
    if (this.mOrientation == 0)
    {
      i = paramRecycler.getSpanIndex();
      if (paramRecycler.mFullSpan) {
        j = this.mSpanCount;
      } else {
        j = 1;
      }
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(i, j, -1, -1, paramRecycler.mFullSpan, false));
    }
    else
    {
      i = paramRecycler.getSpanIndex();
      if (paramRecycler.mFullSpan) {
        j = this.mSpanCount;
      } else {
        j = 1;
      }
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(-1, -1, i, j, paramRecycler.mFullSpan, false));
    }
  }
  
  public void onItemsAdded(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    handleUpdate(paramInt1, paramInt2, 1);
  }
  
  public void onItemsChanged(RecyclerView paramRecyclerView)
  {
    this.mLazySpanLookup.a();
    requestLayout();
  }
  
  public void onItemsMoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, int paramInt3)
  {
    handleUpdate(paramInt1, paramInt2, 8);
  }
  
  public void onItemsRemoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    handleUpdate(paramInt1, paramInt2, 2);
  }
  
  public void onItemsUpdated(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, Object paramObject)
  {
    handleUpdate(paramInt1, paramInt2, 4);
  }
  
  public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    onLayoutChildren(paramRecycler, paramState, true);
  }
  
  public void onLayoutCompleted(RecyclerView.State paramState)
  {
    super.onLayoutCompleted(paramState);
    this.mPendingScrollPosition = -1;
    this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
    this.mPendingSavedState = null;
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
    Object localObject = this.mPendingSavedState;
    if (localObject != null) {
      return new SavedState((SavedState)localObject);
    }
    SavedState localSavedState = new SavedState();
    localSavedState.mReverseLayout = this.mReverseLayout;
    localSavedState.mAnchorLayoutFromEnd = this.mLastLayoutFromEnd;
    localSavedState.mLastLayoutRTL = this.mLastLayoutRTL;
    localObject = this.mLazySpanLookup;
    int i = 0;
    if ((localObject != null) && (((b)localObject).a != null))
    {
      localSavedState.mSpanLookup = this.mLazySpanLookup.a;
      localSavedState.mSpanLookupSize = localSavedState.mSpanLookup.length;
      localSavedState.mFullSpanItems = this.mLazySpanLookup.b;
    }
    else
    {
      localSavedState.mSpanLookupSize = 0;
    }
    if (getChildCount() > 0)
    {
      if (this.mLastLayoutFromEnd) {
        j = getLastChildPosition();
      } else {
        j = getFirstChildPosition();
      }
      localSavedState.mAnchorPosition = j;
      localSavedState.mVisibleAnchorPosition = findFirstVisibleItemPositionInt();
      int j = this.mSpanCount;
      localSavedState.mSpanOffsetsSize = j;
      localSavedState.mSpanOffsets = new int[j];
      while (i < this.mSpanCount)
      {
        int k;
        if (this.mLastLayoutFromEnd)
        {
          k = this.mSpans[i].b(Integer.MIN_VALUE);
          j = k;
          if (k != Integer.MIN_VALUE) {
            j = k - this.mPrimaryOrientation.getEndAfterPadding();
          }
        }
        else
        {
          k = this.mSpans[i].a(Integer.MIN_VALUE);
          j = k;
          if (k != Integer.MIN_VALUE) {
            j = k - this.mPrimaryOrientation.getStartAfterPadding();
          }
        }
        localSavedState.mSpanOffsets[i] = j;
        i++;
      }
    }
    localSavedState.mAnchorPosition = -1;
    localSavedState.mVisibleAnchorPosition = -1;
    localSavedState.mSpanOffsetsSize = 0;
    return localSavedState;
  }
  
  public void onScrollStateChanged(int paramInt)
  {
    if (paramInt == 0) {
      checkForGaps();
    }
  }
  
  void prepareLayoutStateForDelta(int paramInt, RecyclerView.State paramState)
  {
    int i;
    int j;
    if (paramInt > 0)
    {
      i = getLastChildPosition();
      j = 1;
    }
    else
    {
      i = getFirstChildPosition();
      j = -1;
    }
    this.mLayoutState.a = true;
    updateLayoutState(i, paramState);
    setLayoutStateDirection(j);
    paramState = this.mLayoutState;
    paramState.c = (i + paramState.d);
    this.mLayoutState.b = Math.abs(paramInt);
  }
  
  int scrollBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if ((getChildCount() != 0) && (paramInt != 0))
    {
      prepareLayoutStateForDelta(paramInt, paramState);
      int i = fill(paramRecycler, this.mLayoutState, paramState);
      if (this.mLayoutState.b >= i) {
        if (paramInt < 0) {
          paramInt = -i;
        } else {
          paramInt = i;
        }
      }
      this.mPrimaryOrientation.offsetChildren(-paramInt);
      this.mLastLayoutFromEnd = this.mShouldReverseLayout;
      paramState = this.mLayoutState;
      paramState.b = 0;
      recycle(paramRecycler, paramState);
      return paramInt;
    }
    return 0;
  }
  
  public int scrollHorizontallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    return scrollBy(paramInt, paramRecycler, paramState);
  }
  
  public void scrollToPosition(int paramInt)
  {
    SavedState localSavedState = this.mPendingSavedState;
    if ((localSavedState != null) && (localSavedState.mAnchorPosition != paramInt)) {
      this.mPendingSavedState.invalidateAnchorPositionInfo();
    }
    this.mPendingScrollPosition = paramInt;
    this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
    requestLayout();
  }
  
  public void scrollToPositionWithOffset(int paramInt1, int paramInt2)
  {
    SavedState localSavedState = this.mPendingSavedState;
    if (localSavedState != null) {
      localSavedState.invalidateAnchorPositionInfo();
    }
    this.mPendingScrollPosition = paramInt1;
    this.mPendingScrollPositionOffset = paramInt2;
    requestLayout();
  }
  
  public int scrollVerticallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    return scrollBy(paramInt, paramRecycler, paramState);
  }
  
  public void setGapStrategy(int paramInt)
  {
    assertNotInLayoutOrScroll(null);
    if (paramInt == this.mGapStrategy) {
      return;
    }
    if ((paramInt != 0) && (paramInt != 2)) {
      throw new IllegalArgumentException("invalid gap strategy. Must be GAP_HANDLING_NONE or GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS");
    }
    this.mGapStrategy = paramInt;
    boolean bool;
    if (this.mGapStrategy != 0) {
      bool = true;
    } else {
      bool = false;
    }
    setAutoMeasureEnabled(bool);
    requestLayout();
  }
  
  public void setMeasuredDimension(Rect paramRect, int paramInt1, int paramInt2)
  {
    int i = getPaddingLeft() + getPaddingRight();
    int j = getPaddingTop() + getPaddingBottom();
    if (this.mOrientation == 1)
    {
      paramInt2 = chooseSize(paramInt2, paramRect.height() + j, getMinimumHeight());
      paramInt1 = chooseSize(paramInt1, this.mSizePerSpan * this.mSpanCount + i, getMinimumWidth());
    }
    else
    {
      paramInt1 = chooseSize(paramInt1, paramRect.width() + i, getMinimumWidth());
      paramInt2 = chooseSize(paramInt2, this.mSizePerSpan * this.mSpanCount + j, getMinimumHeight());
    }
    setMeasuredDimension(paramInt1, paramInt2);
  }
  
  public void setOrientation(int paramInt)
  {
    if ((paramInt != 0) && (paramInt != 1)) {
      throw new IllegalArgumentException("invalid orientation.");
    }
    assertNotInLayoutOrScroll(null);
    if (paramInt == this.mOrientation) {
      return;
    }
    this.mOrientation = paramInt;
    OrientationHelper localOrientationHelper = this.mPrimaryOrientation;
    this.mPrimaryOrientation = this.mSecondaryOrientation;
    this.mSecondaryOrientation = localOrientationHelper;
    requestLayout();
  }
  
  public void setReverseLayout(boolean paramBoolean)
  {
    assertNotInLayoutOrScroll(null);
    SavedState localSavedState = this.mPendingSavedState;
    if ((localSavedState != null) && (localSavedState.mReverseLayout != paramBoolean)) {
      this.mPendingSavedState.mReverseLayout = paramBoolean;
    }
    this.mReverseLayout = paramBoolean;
    requestLayout();
  }
  
  public void setSpanCount(int paramInt)
  {
    assertNotInLayoutOrScroll(null);
    if (paramInt != this.mSpanCount)
    {
      invalidateSpanAssignments();
      this.mSpanCount = paramInt;
      this.mRemainingSpans = new BitSet(this.mSpanCount);
      this.mSpans = new c[this.mSpanCount];
      for (paramInt = 0; paramInt < this.mSpanCount; paramInt++) {
        this.mSpans[paramInt] = new c(paramInt);
      }
      requestLayout();
    }
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
    if (this.mPendingSavedState == null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean updateAnchorFromPendingData(RecyclerView.State paramState, a parama)
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
          paramState = this.mPendingSavedState;
          if ((paramState != null) && (paramState.mAnchorPosition != -1) && (this.mPendingSavedState.mSpanOffsetsSize >= 1))
          {
            parama.b = Integer.MIN_VALUE;
            parama.a = this.mPendingScrollPosition;
          }
          else
          {
            paramState = findViewByPosition(this.mPendingScrollPosition);
            if (paramState != null)
            {
              if (this.mShouldReverseLayout) {
                i = getLastChildPosition();
              } else {
                i = getFirstChildPosition();
              }
              parama.a = i;
              if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE)
              {
                if (parama.c) {
                  parama.b = (this.mPrimaryOrientation.getEndAfterPadding() - this.mPendingScrollPositionOffset - this.mPrimaryOrientation.getDecoratedEnd(paramState));
                } else {
                  parama.b = (this.mPrimaryOrientation.getStartAfterPadding() + this.mPendingScrollPositionOffset - this.mPrimaryOrientation.getDecoratedStart(paramState));
                }
                return true;
              }
              if (this.mPrimaryOrientation.getDecoratedMeasurement(paramState) > this.mPrimaryOrientation.getTotalSpace())
              {
                if (parama.c) {
                  i = this.mPrimaryOrientation.getEndAfterPadding();
                } else {
                  i = this.mPrimaryOrientation.getStartAfterPadding();
                }
                parama.b = i;
                return true;
              }
              i = this.mPrimaryOrientation.getDecoratedStart(paramState) - this.mPrimaryOrientation.getStartAfterPadding();
              if (i < 0)
              {
                parama.b = (-i);
                return true;
              }
              i = this.mPrimaryOrientation.getEndAfterPadding() - this.mPrimaryOrientation.getDecoratedEnd(paramState);
              if (i < 0)
              {
                parama.b = i;
                return true;
              }
              parama.b = Integer.MIN_VALUE;
            }
            else
            {
              parama.a = this.mPendingScrollPosition;
              i = this.mPendingScrollPositionOffset;
              if (i == Integer.MIN_VALUE)
              {
                if (calculateScrollDirectionForPosition(parama.a) == 1) {
                  bool2 = true;
                }
                parama.c = bool2;
                parama.b();
              }
              else
              {
                parama.a(i);
              }
              parama.d = true;
            }
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
  
  void updateAnchorInfoForLayout(RecyclerView.State paramState, a parama)
  {
    if (updateAnchorFromPendingData(paramState, parama)) {
      return;
    }
    if (updateAnchorFromChildren(paramState, parama)) {
      return;
    }
    parama.b();
    parama.a = 0;
  }
  
  void updateMeasureSpecs(int paramInt)
  {
    this.mSizePerSpan = (paramInt / this.mSpanCount);
    this.mFullSizeSpec = View.MeasureSpec.makeMeasureSpec(paramInt, this.mSecondaryOrientation.getMode());
  }
  
  public static class LayoutParams
    extends RecyclerView.LayoutParams
  {
    public static final int INVALID_SPAN_ID = -1;
    boolean mFullSpan;
    StaggeredGridLayoutManager.c mSpan;
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public LayoutParams(RecyclerView.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
    
    public final int getSpanIndex()
    {
      StaggeredGridLayoutManager.c localc = this.mSpan;
      if (localc == null) {
        return -1;
      }
      return localc.e;
    }
    
    public boolean isFullSpan()
    {
      return this.mFullSpan;
    }
    
    public void setFullSpan(boolean paramBoolean)
    {
      this.mFullSpan = paramBoolean;
    }
  }
  
  public static class SavedState
    implements Parcelable
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public StaggeredGridLayoutManager.SavedState a(Parcel paramAnonymousParcel)
      {
        return new StaggeredGridLayoutManager.SavedState(paramAnonymousParcel);
      }
      
      public StaggeredGridLayoutManager.SavedState[] a(int paramAnonymousInt)
      {
        return new StaggeredGridLayoutManager.SavedState[paramAnonymousInt];
      }
    };
    boolean mAnchorLayoutFromEnd;
    int mAnchorPosition;
    List<StaggeredGridLayoutManager.b.a> mFullSpanItems;
    boolean mLastLayoutRTL;
    boolean mReverseLayout;
    int[] mSpanLookup;
    int mSpanLookupSize;
    int[] mSpanOffsets;
    int mSpanOffsetsSize;
    int mVisibleAnchorPosition;
    
    public SavedState() {}
    
    SavedState(Parcel paramParcel)
    {
      this.mAnchorPosition = paramParcel.readInt();
      this.mVisibleAnchorPosition = paramParcel.readInt();
      this.mSpanOffsetsSize = paramParcel.readInt();
      int i = this.mSpanOffsetsSize;
      if (i > 0)
      {
        this.mSpanOffsets = new int[i];
        paramParcel.readIntArray(this.mSpanOffsets);
      }
      this.mSpanLookupSize = paramParcel.readInt();
      i = this.mSpanLookupSize;
      if (i > 0)
      {
        this.mSpanLookup = new int[i];
        paramParcel.readIntArray(this.mSpanLookup);
      }
      i = paramParcel.readInt();
      boolean bool1 = false;
      if (i == 1) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      this.mReverseLayout = bool2;
      if (paramParcel.readInt() == 1) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      this.mAnchorLayoutFromEnd = bool2;
      boolean bool2 = bool1;
      if (paramParcel.readInt() == 1) {
        bool2 = true;
      }
      this.mLastLayoutRTL = bool2;
      this.mFullSpanItems = paramParcel.readArrayList(StaggeredGridLayoutManager.b.a.class.getClassLoader());
    }
    
    public SavedState(SavedState paramSavedState)
    {
      this.mSpanOffsetsSize = paramSavedState.mSpanOffsetsSize;
      this.mAnchorPosition = paramSavedState.mAnchorPosition;
      this.mVisibleAnchorPosition = paramSavedState.mVisibleAnchorPosition;
      this.mSpanOffsets = paramSavedState.mSpanOffsets;
      this.mSpanLookupSize = paramSavedState.mSpanLookupSize;
      this.mSpanLookup = paramSavedState.mSpanLookup;
      this.mReverseLayout = paramSavedState.mReverseLayout;
      this.mAnchorLayoutFromEnd = paramSavedState.mAnchorLayoutFromEnd;
      this.mLastLayoutRTL = paramSavedState.mLastLayoutRTL;
      this.mFullSpanItems = paramSavedState.mFullSpanItems;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    void invalidateAnchorPositionInfo()
    {
      this.mSpanOffsets = null;
      this.mSpanOffsetsSize = 0;
      this.mAnchorPosition = -1;
      this.mVisibleAnchorPosition = -1;
    }
    
    void invalidateSpanInfo()
    {
      this.mSpanOffsets = null;
      this.mSpanOffsetsSize = 0;
      this.mSpanLookupSize = 0;
      this.mSpanLookup = null;
      this.mFullSpanItems = null;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.mAnchorPosition);
      paramParcel.writeInt(this.mVisibleAnchorPosition);
      paramParcel.writeInt(this.mSpanOffsetsSize);
      if (this.mSpanOffsetsSize > 0) {
        paramParcel.writeIntArray(this.mSpanOffsets);
      }
      paramParcel.writeInt(this.mSpanLookupSize);
      if (this.mSpanLookupSize > 0) {
        paramParcel.writeIntArray(this.mSpanLookup);
      }
      paramParcel.writeInt(this.mReverseLayout);
      paramParcel.writeInt(this.mAnchorLayoutFromEnd);
      paramParcel.writeInt(this.mLastLayoutRTL);
      paramParcel.writeList(this.mFullSpanItems);
    }
  }
  
  class a
  {
    int a;
    int b;
    boolean c;
    boolean d;
    boolean e;
    int[] f;
    
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
      this.e = false;
      int[] arrayOfInt = this.f;
      if (arrayOfInt != null) {
        Arrays.fill(arrayOfInt, -1);
      }
    }
    
    void a(int paramInt)
    {
      if (this.c) {
        this.b = (StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() - paramInt);
      } else {
        this.b = (StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding() + paramInt);
      }
    }
    
    void a(StaggeredGridLayoutManager.c[] paramArrayOfc)
    {
      int i = paramArrayOfc.length;
      int[] arrayOfInt = this.f;
      if ((arrayOfInt == null) || (arrayOfInt.length < i)) {
        this.f = new int[StaggeredGridLayoutManager.this.mSpans.length];
      }
      for (int j = 0; j < i; j++) {
        this.f[j] = paramArrayOfc[j].a(Integer.MIN_VALUE);
      }
    }
    
    void b()
    {
      int i;
      if (this.c) {
        i = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding();
      } else {
        i = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
      }
      this.b = i;
    }
  }
  
  static class b
  {
    int[] a;
    List<a> b;
    
    private void c(int paramInt1, int paramInt2)
    {
      Object localObject = this.b;
      if (localObject == null) {
        return;
      }
      for (int i = ((List)localObject).size() - 1; i >= 0; i--)
      {
        localObject = (a)this.b.get(i);
        if (((a)localObject).a >= paramInt1) {
          if (((a)localObject).a < paramInt1 + paramInt2) {
            this.b.remove(i);
          } else {
            ((a)localObject).a -= paramInt2;
          }
        }
      }
    }
    
    private void d(int paramInt1, int paramInt2)
    {
      Object localObject = this.b;
      if (localObject == null) {
        return;
      }
      for (int i = ((List)localObject).size() - 1; i >= 0; i--)
      {
        localObject = (a)this.b.get(i);
        if (((a)localObject).a >= paramInt1) {
          ((a)localObject).a += paramInt2;
        }
      }
    }
    
    private int g(int paramInt)
    {
      if (this.b == null) {
        return -1;
      }
      a locala = f(paramInt);
      if (locala != null) {
        this.b.remove(locala);
      }
      int i = this.b.size();
      for (int j = 0; j < i; j++) {
        if (((a)this.b.get(j)).a >= paramInt) {
          break label82;
        }
      }
      j = -1;
      label82:
      if (j != -1)
      {
        locala = (a)this.b.get(j);
        this.b.remove(j);
        return locala.a;
      }
      return -1;
    }
    
    int a(int paramInt)
    {
      List localList = this.b;
      if (localList != null) {
        for (int i = localList.size() - 1; i >= 0; i--) {
          if (((a)this.b.get(i)).a >= paramInt) {
            this.b.remove(i);
          }
        }
      }
      return b(paramInt);
    }
    
    public a a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      Object localObject = this.b;
      if (localObject == null) {
        return null;
      }
      int i = ((List)localObject).size();
      for (int j = 0; j < i; j++)
      {
        localObject = (a)this.b.get(j);
        if (((a)localObject).a >= paramInt2) {
          return null;
        }
        if ((((a)localObject).a >= paramInt1) && ((paramInt3 == 0) || (((a)localObject).b == paramInt3) || ((paramBoolean) && (((a)localObject).d)))) {
          return (a)localObject;
        }
      }
      return null;
    }
    
    void a()
    {
      int[] arrayOfInt = this.a;
      if (arrayOfInt != null) {
        Arrays.fill(arrayOfInt, -1);
      }
      this.b = null;
    }
    
    void a(int paramInt1, int paramInt2)
    {
      int[] arrayOfInt = this.a;
      if ((arrayOfInt != null) && (paramInt1 < arrayOfInt.length))
      {
        int i = paramInt1 + paramInt2;
        e(i);
        arrayOfInt = this.a;
        System.arraycopy(arrayOfInt, i, arrayOfInt, paramInt1, arrayOfInt.length - paramInt1 - paramInt2);
        arrayOfInt = this.a;
        Arrays.fill(arrayOfInt, arrayOfInt.length - paramInt2, arrayOfInt.length, -1);
        c(paramInt1, paramInt2);
        return;
      }
    }
    
    void a(int paramInt, StaggeredGridLayoutManager.c paramc)
    {
      e(paramInt);
      this.a[paramInt] = paramc.e;
    }
    
    public void a(a parama)
    {
      if (this.b == null) {
        this.b = new ArrayList();
      }
      int i = this.b.size();
      for (int j = 0; j < i; j++)
      {
        a locala = (a)this.b.get(j);
        if (locala.a == parama.a) {
          this.b.remove(j);
        }
        if (locala.a >= parama.a)
        {
          this.b.add(j, parama);
          return;
        }
      }
      this.b.add(parama);
    }
    
    int b(int paramInt)
    {
      int[] arrayOfInt = this.a;
      if (arrayOfInt == null) {
        return -1;
      }
      if (paramInt >= arrayOfInt.length) {
        return -1;
      }
      int i = g(paramInt);
      if (i == -1)
      {
        arrayOfInt = this.a;
        Arrays.fill(arrayOfInt, paramInt, arrayOfInt.length, -1);
        return this.a.length;
      }
      arrayOfInt = this.a;
      i++;
      Arrays.fill(arrayOfInt, paramInt, i, -1);
      return i;
    }
    
    void b(int paramInt1, int paramInt2)
    {
      int[] arrayOfInt = this.a;
      if ((arrayOfInt != null) && (paramInt1 < arrayOfInt.length))
      {
        int i = paramInt1 + paramInt2;
        e(i);
        arrayOfInt = this.a;
        System.arraycopy(arrayOfInt, paramInt1, arrayOfInt, i, arrayOfInt.length - paramInt1 - paramInt2);
        Arrays.fill(this.a, paramInt1, i, -1);
        d(paramInt1, paramInt2);
        return;
      }
    }
    
    int c(int paramInt)
    {
      int[] arrayOfInt = this.a;
      if ((arrayOfInt != null) && (paramInt < arrayOfInt.length)) {
        return arrayOfInt[paramInt];
      }
      return -1;
    }
    
    int d(int paramInt)
    {
      int i = this.a.length;
      while (i <= paramInt) {
        i *= 2;
      }
      return i;
    }
    
    void e(int paramInt)
    {
      int[] arrayOfInt1 = this.a;
      if (arrayOfInt1 == null)
      {
        this.a = new int[Math.max(paramInt, 10) + 1];
        Arrays.fill(this.a, -1);
      }
      else if (paramInt >= arrayOfInt1.length)
      {
        this.a = new int[d(paramInt)];
        System.arraycopy(arrayOfInt1, 0, this.a, 0, arrayOfInt1.length);
        int[] arrayOfInt2 = this.a;
        Arrays.fill(arrayOfInt2, arrayOfInt1.length, arrayOfInt2.length, -1);
      }
    }
    
    public a f(int paramInt)
    {
      Object localObject = this.b;
      if (localObject == null) {
        return null;
      }
      for (int i = ((List)localObject).size() - 1; i >= 0; i--)
      {
        localObject = (a)this.b.get(i);
        if (((a)localObject).a == paramInt) {
          return (a)localObject;
        }
      }
      return null;
    }
    
    static class a
      implements Parcelable
    {
      public static final Parcelable.Creator<a> CREATOR = new Parcelable.Creator()
      {
        public StaggeredGridLayoutManager.b.a a(Parcel paramAnonymousParcel)
        {
          return new StaggeredGridLayoutManager.b.a(paramAnonymousParcel);
        }
        
        public StaggeredGridLayoutManager.b.a[] a(int paramAnonymousInt)
        {
          return new StaggeredGridLayoutManager.b.a[paramAnonymousInt];
        }
      };
      int a;
      int b;
      int[] c;
      boolean d;
      
      a() {}
      
      a(Parcel paramParcel)
      {
        this.a = paramParcel.readInt();
        this.b = paramParcel.readInt();
        int i = paramParcel.readInt();
        boolean bool = true;
        if (i != 1) {
          bool = false;
        }
        this.d = bool;
        i = paramParcel.readInt();
        if (i > 0)
        {
          this.c = new int[i];
          paramParcel.readIntArray(this.c);
        }
      }
      
      int a(int paramInt)
      {
        int[] arrayOfInt = this.c;
        if (arrayOfInt == null) {
          paramInt = 0;
        } else {
          paramInt = arrayOfInt[paramInt];
        }
        return paramInt;
      }
      
      public int describeContents()
      {
        return 0;
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("FullSpanItem{mPosition=");
        localStringBuilder.append(this.a);
        localStringBuilder.append(", mGapDir=");
        localStringBuilder.append(this.b);
        localStringBuilder.append(", mHasUnwantedGapAfter=");
        localStringBuilder.append(this.d);
        localStringBuilder.append(", mGapPerSpan=");
        localStringBuilder.append(Arrays.toString(this.c));
        localStringBuilder.append('}');
        return localStringBuilder.toString();
      }
      
      public void writeToParcel(Parcel paramParcel, int paramInt)
      {
        paramParcel.writeInt(this.a);
        paramParcel.writeInt(this.b);
        paramParcel.writeInt(this.d);
        int[] arrayOfInt = this.c;
        if ((arrayOfInt != null) && (arrayOfInt.length > 0))
        {
          paramParcel.writeInt(arrayOfInt.length);
          paramParcel.writeIntArray(this.c);
        }
        else
        {
          paramParcel.writeInt(0);
        }
      }
    }
  }
  
  class c
  {
    ArrayList<View> a = new ArrayList();
    int b = Integer.MIN_VALUE;
    int c = Integer.MIN_VALUE;
    int d = 0;
    final int e;
    
    c(int paramInt)
    {
      this.e = paramInt;
    }
    
    int a(int paramInt)
    {
      int i = this.b;
      if (i != Integer.MIN_VALUE) {
        return i;
      }
      if (this.a.size() == 0) {
        return paramInt;
      }
      a();
      return this.b;
    }
    
    int a(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      return a(paramInt1, paramInt2, paramBoolean, true, false);
    }
    
    int a(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      int i = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
      int j = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding();
      int k;
      if (paramInt2 > paramInt1) {
        k = 1;
      } else {
        k = -1;
      }
      while (paramInt1 != paramInt2)
      {
        View localView = (View)this.a.get(paramInt1);
        int m = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(localView);
        int n = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(localView);
        int i1 = 0;
        int i2;
        if (paramBoolean3 ? m <= j : m < j) {
          i2 = 1;
        } else {
          i2 = 0;
        }
        if (paramBoolean3 ? n >= i : n > i) {
          i1 = 1;
        }
        if ((i2 != 0) && (i1 != 0)) {
          if ((paramBoolean1) && (paramBoolean2))
          {
            if ((m >= i) && (n <= j)) {
              return StaggeredGridLayoutManager.this.getPosition(localView);
            }
          }
          else
          {
            if (paramBoolean2) {
              return StaggeredGridLayoutManager.this.getPosition(localView);
            }
            if ((m < i) || (n > j)) {
              return StaggeredGridLayoutManager.this.getPosition(localView);
            }
          }
        }
        paramInt1 += k;
      }
      return -1;
    }
    
    public View a(int paramInt1, int paramInt2)
    {
      Object localObject1 = null;
      Object localObject2 = null;
      View localView;
      if (paramInt2 == -1)
      {
        int i = this.a.size();
        paramInt2 = 0;
        for (;;)
        {
          localObject1 = localObject2;
          if (paramInt2 >= i) {
            break;
          }
          localView = (View)this.a.get(paramInt2);
          if (StaggeredGridLayoutManager.this.mReverseLayout)
          {
            localObject1 = localObject2;
            if (StaggeredGridLayoutManager.this.getPosition(localView) <= paramInt1) {
              break;
            }
          }
          if ((!StaggeredGridLayoutManager.this.mReverseLayout) && (StaggeredGridLayoutManager.this.getPosition(localView) >= paramInt1))
          {
            localObject1 = localObject2;
            break;
          }
          localObject1 = localObject2;
          if (!localView.hasFocusable()) {
            break;
          }
          paramInt2++;
          localObject2 = localView;
        }
      }
      paramInt2 = this.a.size() - 1;
      for (localObject2 = localObject1;; localObject2 = localView)
      {
        localObject1 = localObject2;
        if (paramInt2 < 0) {
          break;
        }
        localView = (View)this.a.get(paramInt2);
        if (StaggeredGridLayoutManager.this.mReverseLayout)
        {
          localObject1 = localObject2;
          if (StaggeredGridLayoutManager.this.getPosition(localView) >= paramInt1) {
            break;
          }
        }
        if ((!StaggeredGridLayoutManager.this.mReverseLayout) && (StaggeredGridLayoutManager.this.getPosition(localView) <= paramInt1))
        {
          localObject1 = localObject2;
          break;
        }
        localObject1 = localObject2;
        if (!localView.hasFocusable()) {
          break;
        }
        paramInt2--;
      }
      return (View)localObject1;
    }
    
    void a()
    {
      View localView = (View)this.a.get(0);
      Object localObject = c(localView);
      this.b = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(localView);
      if (((StaggeredGridLayoutManager.LayoutParams)localObject).mFullSpan)
      {
        localObject = StaggeredGridLayoutManager.this.mLazySpanLookup.f(((StaggeredGridLayoutManager.LayoutParams)localObject).getViewLayoutPosition());
        if ((localObject != null) && (((StaggeredGridLayoutManager.b.a)localObject).b == -1)) {
          this.b -= ((StaggeredGridLayoutManager.b.a)localObject).a(this.e);
        }
      }
    }
    
    void a(View paramView)
    {
      StaggeredGridLayoutManager.LayoutParams localLayoutParams = c(paramView);
      localLayoutParams.mSpan = this;
      this.a.add(0, paramView);
      this.b = Integer.MIN_VALUE;
      if (this.a.size() == 1) {
        this.c = Integer.MIN_VALUE;
      }
      if ((localLayoutParams.isItemRemoved()) || (localLayoutParams.isItemChanged())) {
        this.d += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(paramView);
      }
    }
    
    void a(boolean paramBoolean, int paramInt)
    {
      int i;
      if (paramBoolean) {
        i = b(Integer.MIN_VALUE);
      } else {
        i = a(Integer.MIN_VALUE);
      }
      e();
      if (i == Integer.MIN_VALUE) {
        return;
      }
      if (((paramBoolean) && (i < StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding())) || ((!paramBoolean) && (i > StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding()))) {
        return;
      }
      int j = i;
      if (paramInt != Integer.MIN_VALUE) {
        j = i + paramInt;
      }
      this.c = j;
      this.b = j;
    }
    
    int b()
    {
      int i = this.b;
      if (i != Integer.MIN_VALUE) {
        return i;
      }
      a();
      return this.b;
    }
    
    int b(int paramInt)
    {
      int i = this.c;
      if (i != Integer.MIN_VALUE) {
        return i;
      }
      if (this.a.size() == 0) {
        return paramInt;
      }
      c();
      return this.c;
    }
    
    int b(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      return a(paramInt1, paramInt2, false, false, paramBoolean);
    }
    
    void b(View paramView)
    {
      StaggeredGridLayoutManager.LayoutParams localLayoutParams = c(paramView);
      localLayoutParams.mSpan = this;
      this.a.add(paramView);
      this.c = Integer.MIN_VALUE;
      if (this.a.size() == 1) {
        this.b = Integer.MIN_VALUE;
      }
      if ((localLayoutParams.isItemRemoved()) || (localLayoutParams.isItemChanged())) {
        this.d += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(paramView);
      }
    }
    
    StaggeredGridLayoutManager.LayoutParams c(View paramView)
    {
      return (StaggeredGridLayoutManager.LayoutParams)paramView.getLayoutParams();
    }
    
    void c()
    {
      Object localObject = this.a;
      View localView = (View)((ArrayList)localObject).get(((ArrayList)localObject).size() - 1);
      localObject = c(localView);
      this.c = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(localView);
      if (((StaggeredGridLayoutManager.LayoutParams)localObject).mFullSpan)
      {
        localObject = StaggeredGridLayoutManager.this.mLazySpanLookup.f(((StaggeredGridLayoutManager.LayoutParams)localObject).getViewLayoutPosition());
        if ((localObject != null) && (((StaggeredGridLayoutManager.b.a)localObject).b == 1)) {
          this.c += ((StaggeredGridLayoutManager.b.a)localObject).a(this.e);
        }
      }
    }
    
    void c(int paramInt)
    {
      this.b = paramInt;
      this.c = paramInt;
    }
    
    int d()
    {
      int i = this.c;
      if (i != Integer.MIN_VALUE) {
        return i;
      }
      c();
      return this.c;
    }
    
    void d(int paramInt)
    {
      int i = this.b;
      if (i != Integer.MIN_VALUE) {
        this.b = (i + paramInt);
      }
      i = this.c;
      if (i != Integer.MIN_VALUE) {
        this.c = (i + paramInt);
      }
    }
    
    void e()
    {
      this.a.clear();
      f();
      this.d = 0;
    }
    
    void f()
    {
      this.b = Integer.MIN_VALUE;
      this.c = Integer.MIN_VALUE;
    }
    
    void g()
    {
      int i = this.a.size();
      View localView = (View)this.a.remove(i - 1);
      StaggeredGridLayoutManager.LayoutParams localLayoutParams = c(localView);
      localLayoutParams.mSpan = null;
      if ((localLayoutParams.isItemRemoved()) || (localLayoutParams.isItemChanged())) {
        this.d -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(localView);
      }
      if (i == 1) {
        this.b = Integer.MIN_VALUE;
      }
      this.c = Integer.MIN_VALUE;
    }
    
    void h()
    {
      View localView = (View)this.a.remove(0);
      StaggeredGridLayoutManager.LayoutParams localLayoutParams = c(localView);
      localLayoutParams.mSpan = null;
      if (this.a.size() == 0) {
        this.c = Integer.MIN_VALUE;
      }
      if ((localLayoutParams.isItemRemoved()) || (localLayoutParams.isItemChanged())) {
        this.d -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(localView);
      }
      this.b = Integer.MIN_VALUE;
    }
    
    public int i()
    {
      return this.d;
    }
    
    public int j()
    {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = a(this.a.size() - 1, -1, false);
      } else {
        i = a(0, this.a.size(), false);
      }
      return i;
    }
    
    public int k()
    {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = b(this.a.size() - 1, -1, true);
      } else {
        i = b(0, this.a.size(), true);
      }
      return i;
    }
    
    public int l()
    {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = a(this.a.size() - 1, -1, true);
      } else {
        i = a(0, this.a.size(), true);
      }
      return i;
    }
    
    public int m()
    {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = a(0, this.a.size(), false);
      } else {
        i = a(this.a.size() - 1, -1, false);
      }
      return i;
    }
    
    public int n()
    {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = b(0, this.a.size(), true);
      } else {
        i = b(this.a.size() - 1, -1, true);
      }
      return i;
    }
    
    public int o()
    {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = a(0, this.a.size(), true);
      } else {
        i = a(this.a.size() - 1, -1, true);
      }
      return i;
    }
  }
}


/* Location:              ~/android/support/v7/widget/StaggeredGridLayoutManager.class
 *
 * Reversed by:           J
 */