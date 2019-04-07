package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import java.util.Arrays;

public class GridLayoutManager
  extends LinearLayoutManager
{
  private static final boolean DEBUG = false;
  public static final int DEFAULT_SPAN_COUNT = -1;
  private static final String TAG = "GridLayoutManager";
  int[] mCachedBorders;
  final Rect mDecorInsets = new Rect();
  boolean mPendingSpanCountChange = false;
  final SparseIntArray mPreLayoutSpanIndexCache = new SparseIntArray();
  final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
  View[] mSet;
  int mSpanCount = -1;
  SpanSizeLookup mSpanSizeLookup = new DefaultSpanSizeLookup();
  
  public GridLayoutManager(Context paramContext, int paramInt)
  {
    super(paramContext);
    setSpanCount(paramInt);
  }
  
  public GridLayoutManager(Context paramContext, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    super(paramContext, paramInt2, paramBoolean);
    setSpanCount(paramInt1);
  }
  
  public GridLayoutManager(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    setSpanCount(getProperties(paramContext, paramAttributeSet, paramInt1, paramInt2).spanCount);
  }
  
  private void assignSpans(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = 0;
    int j = -1;
    if (paramBoolean)
    {
      int k = 0;
      paramInt2 = 1;
      j = paramInt1;
      paramInt1 = k;
    }
    else
    {
      paramInt1--;
      paramInt2 = -1;
    }
    while (paramInt1 != j)
    {
      View localView = this.mSet[paramInt1];
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      localLayoutParams.mSpanSize = getSpanSize(paramRecycler, paramState, getPosition(localView));
      localLayoutParams.mSpanIndex = i;
      i += localLayoutParams.mSpanSize;
      paramInt1 += paramInt2;
    }
  }
  
  private void cachePreLayoutSpanMapping()
  {
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      LayoutParams localLayoutParams = (LayoutParams)getChildAt(j).getLayoutParams();
      int k = localLayoutParams.getViewLayoutPosition();
      this.mPreLayoutSpanSizeCache.put(k, localLayoutParams.getSpanSize());
      this.mPreLayoutSpanIndexCache.put(k, localLayoutParams.getSpanIndex());
    }
  }
  
  private void calculateItemBorders(int paramInt)
  {
    this.mCachedBorders = calculateItemBorders(this.mCachedBorders, this.mSpanCount, paramInt);
  }
  
  static int[] calculateItemBorders(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int i = 1;
    int[] arrayOfInt;
    if ((paramArrayOfInt != null) && (paramArrayOfInt.length == paramInt1 + 1))
    {
      arrayOfInt = paramArrayOfInt;
      if (paramArrayOfInt[(paramArrayOfInt.length - 1)] == paramInt2) {}
    }
    else
    {
      arrayOfInt = new int[paramInt1 + 1];
    }
    int j = 0;
    arrayOfInt[0] = 0;
    int k = paramInt2 / paramInt1;
    int m = paramInt2 % paramInt1;
    int n = 0;
    paramInt2 = j;
    while (i <= paramInt1)
    {
      paramInt2 += m;
      if ((paramInt2 > 0) && (paramInt1 - paramInt2 < m))
      {
        j = k + 1;
        paramInt2 -= paramInt1;
      }
      else
      {
        j = k;
      }
      n += j;
      arrayOfInt[i] = n;
      i++;
    }
    return arrayOfInt;
  }
  
  private void clearPreLayoutSpanMappingCache()
  {
    this.mPreLayoutSpanSizeCache.clear();
    this.mPreLayoutSpanIndexCache.clear();
  }
  
  private void ensureAnchorIsInCorrectSpan(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LinearLayoutManager.a parama, int paramInt)
  {
    if (paramInt == 1) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    int i = getSpanIndex(paramRecycler, paramState, parama.a);
    if (paramInt != 0) {
      while ((i > 0) && (parama.a > 0))
      {
        parama.a -= 1;
        i = getSpanIndex(paramRecycler, paramState, parama.a);
      }
    }
    int j = paramState.getItemCount();
    paramInt = parama.a;
    while (paramInt < j - 1)
    {
      int k = paramInt + 1;
      int m = getSpanIndex(paramRecycler, paramState, k);
      if (m <= i) {
        break;
      }
      paramInt = k;
      i = m;
    }
    parama.a = paramInt;
  }
  
  private void ensureViewSet()
  {
    View[] arrayOfView = this.mSet;
    if ((arrayOfView == null) || (arrayOfView.length != this.mSpanCount)) {
      this.mSet = new View[this.mSpanCount];
    }
  }
  
  private int getSpanGroupIndex(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt)
  {
    if (!paramState.isPreLayout()) {
      return this.mSpanSizeLookup.getSpanGroupIndex(paramInt, this.mSpanCount);
    }
    int i = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (i == -1)
    {
      paramRecycler = new StringBuilder();
      paramRecycler.append("Cannot find span size for pre layout position. ");
      paramRecycler.append(paramInt);
      Log.w("GridLayoutManager", paramRecycler.toString());
      return 0;
    }
    return this.mSpanSizeLookup.getSpanGroupIndex(i, this.mSpanCount);
  }
  
  private int getSpanIndex(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt)
  {
    if (!paramState.isPreLayout()) {
      return this.mSpanSizeLookup.getCachedSpanIndex(paramInt, this.mSpanCount);
    }
    int i = this.mPreLayoutSpanIndexCache.get(paramInt, -1);
    if (i != -1) {
      return i;
    }
    i = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (i == -1)
    {
      paramRecycler = new StringBuilder();
      paramRecycler.append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
      paramRecycler.append(paramInt);
      Log.w("GridLayoutManager", paramRecycler.toString());
      return 0;
    }
    return this.mSpanSizeLookup.getCachedSpanIndex(i, this.mSpanCount);
  }
  
  private int getSpanSize(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt)
  {
    if (!paramState.isPreLayout()) {
      return this.mSpanSizeLookup.getSpanSize(paramInt);
    }
    int i = this.mPreLayoutSpanSizeCache.get(paramInt, -1);
    if (i != -1) {
      return i;
    }
    i = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (i == -1)
    {
      paramRecycler = new StringBuilder();
      paramRecycler.append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
      paramRecycler.append(paramInt);
      Log.w("GridLayoutManager", paramRecycler.toString());
      return 1;
    }
    return this.mSpanSizeLookup.getSpanSize(i);
  }
  
  private void guessMeasurement(float paramFloat, int paramInt)
  {
    calculateItemBorders(Math.max(Math.round(paramFloat * this.mSpanCount), paramInt));
  }
  
  private void measureChild(View paramView, int paramInt, boolean paramBoolean)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    Rect localRect = localLayoutParams.mDecorInsets;
    int i = localRect.top + localRect.bottom + localLayoutParams.topMargin + localLayoutParams.bottomMargin;
    int j = localRect.left + localRect.right + localLayoutParams.leftMargin + localLayoutParams.rightMargin;
    int k = getSpaceForSpanRange(localLayoutParams.mSpanIndex, localLayoutParams.mSpanSize);
    if (this.mOrientation == 1)
    {
      j = getChildMeasureSpec(k, paramInt, j, localLayoutParams.width, false);
      paramInt = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getHeightMode(), i, localLayoutParams.height, true);
    }
    else
    {
      paramInt = getChildMeasureSpec(k, paramInt, i, localLayoutParams.height, false);
      j = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getWidthMode(), j, localLayoutParams.width, true);
    }
    measureChildWithDecorationsAndMargin(paramView, j, paramInt, paramBoolean);
  }
  
  private void measureChildWithDecorationsAndMargin(View paramView, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
    if (paramBoolean) {
      paramBoolean = shouldReMeasureChild(paramView, paramInt1, paramInt2, localLayoutParams);
    } else {
      paramBoolean = shouldMeasureChild(paramView, paramInt1, paramInt2, localLayoutParams);
    }
    if (paramBoolean) {
      paramView.measure(paramInt1, paramInt2);
    }
  }
  
  private void updateMeasurements()
  {
    int i;
    if (getOrientation() == 1) {
      i = getWidth() - getPaddingRight() - getPaddingLeft();
    } else {
      i = getHeight() - getPaddingBottom() - getPaddingTop();
    }
    calculateItemBorders(i);
  }
  
  public boolean checkLayoutParams(RecyclerView.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  void collectPrefetchPositionsForLayoutState(RecyclerView.State paramState, LinearLayoutManager.b paramb, RecyclerView.LayoutManager.LayoutPrefetchRegistry paramLayoutPrefetchRegistry)
  {
    int i = this.mSpanCount;
    for (int j = 0; (j < this.mSpanCount) && (paramb.a(paramState)) && (i > 0); j++)
    {
      int k = paramb.d;
      paramLayoutPrefetchRegistry.addPosition(k, Math.max(0, paramb.g));
      i -= this.mSpanSizeLookup.getSpanSize(k);
      paramb.d += paramb.e;
    }
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
    Object localObject1 = null;
    Object localObject4;
    for (Object localObject2 = null; paramInt1 != paramInt2; localObject2 = localObject4)
    {
      View localView = getChildAt(paramInt1);
      int m = getPosition(localView);
      Object localObject3 = localObject1;
      localObject4 = localObject2;
      if (m >= 0)
      {
        localObject3 = localObject1;
        localObject4 = localObject2;
        if (m < paramInt3) {
          if (getSpanIndex(paramRecycler, paramState, m) != 0)
          {
            localObject3 = localObject1;
            localObject4 = localObject2;
          }
          else if (((RecyclerView.LayoutParams)localView.getLayoutParams()).isItemRemoved())
          {
            localObject3 = localObject1;
            localObject4 = localObject2;
            if (localObject2 == null)
            {
              localObject4 = localView;
              localObject3 = localObject1;
            }
          }
          else
          {
            if ((this.mOrientationHelper.getDecoratedStart(localView) < j) && (this.mOrientationHelper.getDecoratedEnd(localView) >= i)) {
              return localView;
            }
            localObject3 = localObject1;
            localObject4 = localObject2;
            if (localObject1 == null)
            {
              localObject3 = localView;
              localObject4 = localObject2;
            }
          }
        }
      }
      paramInt1 += k;
      localObject1 = localObject3;
    }
    if (localObject1 == null) {
      localObject1 = localObject2;
    }
    return (View)localObject1;
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
    if (paramState.getItemCount() < 1) {
      return 0;
    }
    return getSpanGroupIndex(paramRecycler, paramState, paramState.getItemCount() - 1) + 1;
  }
  
  public int getRowCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 0) {
      return this.mSpanCount;
    }
    if (paramState.getItemCount() < 1) {
      return 0;
    }
    return getSpanGroupIndex(paramRecycler, paramState, paramState.getItemCount() - 1) + 1;
  }
  
  int getSpaceForSpanRange(int paramInt1, int paramInt2)
  {
    if ((this.mOrientation == 1) && (isLayoutRTL()))
    {
      arrayOfInt = this.mCachedBorders;
      int i = this.mSpanCount;
      return arrayOfInt[(i - paramInt1)] - arrayOfInt[(i - paramInt1 - paramInt2)];
    }
    int[] arrayOfInt = this.mCachedBorders;
    return arrayOfInt[(paramInt2 + paramInt1)] - arrayOfInt[paramInt1];
  }
  
  public int getSpanCount()
  {
    return this.mSpanCount;
  }
  
  public SpanSizeLookup getSpanSizeLookup()
  {
    return this.mSpanSizeLookup;
  }
  
  void layoutChunk(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LinearLayoutManager.b paramb, LinearLayoutManager.LayoutChunkResult paramLayoutChunkResult)
  {
    int i = this.mOrientationHelper.getModeInOther();
    int j;
    if (i != 1073741824) {
      j = 1;
    } else {
      j = 0;
    }
    int k;
    if (getChildCount() > 0) {
      k = this.mCachedBorders[this.mSpanCount];
    } else {
      k = 0;
    }
    if (j != 0) {
      updateMeasurements();
    }
    boolean bool;
    if (paramb.e == 1) {
      bool = true;
    } else {
      bool = false;
    }
    int m = this.mSpanCount;
    int i1;
    if (!bool)
    {
      m = getSpanIndex(paramRecycler, paramState, paramb.d) + getSpanSize(paramRecycler, paramState, paramb.d);
      n = 0;
      i1 = 0;
    }
    else
    {
      n = 0;
      i1 = 0;
    }
    View localView;
    while ((i1 < this.mSpanCount) && (paramb.a(paramState)) && (m > 0))
    {
      i2 = paramb.d;
      i3 = getSpanSize(paramRecycler, paramState, i2);
      if (i3 <= this.mSpanCount)
      {
        m -= i3;
        if (m >= 0)
        {
          localView = paramb.a(paramRecycler);
          if (localView != null)
          {
            n += i3;
            this.mSet[i1] = localView;
            i1++;
          }
        }
      }
      else
      {
        paramRecycler = new StringBuilder();
        paramRecycler.append("Item at position ");
        paramRecycler.append(i2);
        paramRecycler.append(" requires ");
        paramRecycler.append(i3);
        paramRecycler.append(" spans but GridLayoutManager has only ");
        paramRecycler.append(this.mSpanCount);
        paramRecycler.append(" spans.");
        throw new IllegalArgumentException(paramRecycler.toString());
      }
    }
    if (i1 == 0)
    {
      paramLayoutChunkResult.mFinished = true;
      return;
    }
    float f1 = 0.0F;
    assignSpans(paramRecycler, paramState, i1, n, bool);
    m = 0;
    int n = 0;
    while (m < i1)
    {
      paramState = this.mSet[m];
      if (paramb.k == null)
      {
        if (bool) {
          addView(paramState);
        } else {
          addView(paramState, 0);
        }
      }
      else if (bool) {
        addDisappearingView(paramState);
      } else {
        addDisappearingView(paramState, 0);
      }
      calculateItemDecorationsForChild(paramState, this.mDecorInsets);
      measureChild(paramState, i, false);
      i2 = this.mOrientationHelper.getDecoratedMeasurement(paramState);
      i3 = n;
      if (i2 > n) {
        i3 = i2;
      }
      paramRecycler = (LayoutParams)paramState.getLayoutParams();
      float f2 = this.mOrientationHelper.getDecoratedMeasurementInOther(paramState) * 1.0F / paramRecycler.mSpanSize;
      float f3 = f1;
      if (f2 > f1) {
        f3 = f2;
      }
      m++;
      n = i3;
      f1 = f3;
    }
    m = n;
    if (j != 0)
    {
      guessMeasurement(f1, k);
      j = 0;
      for (n = 0;; n = m)
      {
        m = n;
        if (j >= i1) {
          break;
        }
        paramRecycler = this.mSet[j];
        measureChild(paramRecycler, 1073741824, true);
        k = this.mOrientationHelper.getDecoratedMeasurement(paramRecycler);
        m = n;
        if (k > n) {
          m = k;
        }
        j++;
      }
    }
    for (n = 0; n < i1; n++)
    {
      localView = this.mSet[n];
      if (this.mOrientationHelper.getDecoratedMeasurement(localView) != m)
      {
        paramState = (LayoutParams)localView.getLayoutParams();
        paramRecycler = paramState.mDecorInsets;
        k = paramRecycler.top + paramRecycler.bottom + paramState.topMargin + paramState.bottomMargin;
        j = paramRecycler.left + paramRecycler.right + paramState.leftMargin + paramState.rightMargin;
        i3 = getSpaceForSpanRange(paramState.mSpanIndex, paramState.mSpanSize);
        if (this.mOrientation == 1)
        {
          j = getChildMeasureSpec(i3, 1073741824, j, paramState.width, false);
          k = View.MeasureSpec.makeMeasureSpec(m - k, 1073741824);
        }
        else
        {
          j = View.MeasureSpec.makeMeasureSpec(m - j, 1073741824);
          k = getChildMeasureSpec(i3, 1073741824, k, paramState.height, false);
        }
        measureChildWithDecorationsAndMargin(localView, j, k, true);
      }
    }
    int i2 = 0;
    paramLayoutChunkResult.mConsumed = m;
    if (this.mOrientation == 1)
    {
      if (paramb.f == -1)
      {
        n = paramb.b;
        i3 = n;
        n -= m;
        j = 0;
        k = 0;
        m = i3;
        i3 = i2;
      }
      else
      {
        j = paramb.b;
        n = j;
        m += j;
        j = 0;
        k = 0;
        i3 = i2;
      }
    }
    else if (paramb.f == -1)
    {
      j = paramb.b;
      n = 0;
      i3 = 0;
      k = j;
      j -= m;
      m = i3;
      i3 = i2;
    }
    else
    {
      j = paramb.b;
      k = m + j;
      n = 0;
      m = 0;
    }
    for (int i3 = i2; i3 < i1; i3 = i2)
    {
      paramState = this.mSet[i3];
      paramRecycler = (LayoutParams)paramState.getLayoutParams();
      if (this.mOrientation == 1)
      {
        if (isLayoutRTL())
        {
          j = getPaddingLeft() + this.mCachedBorders[(this.mSpanCount - paramRecycler.mSpanIndex)];
          i2 = this.mOrientationHelper.getDecoratedMeasurementInOther(paramState);
          k = j;
          j -= i2;
          i2 = m;
          m = k;
          k = i2;
        }
        else
        {
          k = getPaddingLeft() + this.mCachedBorders[paramRecycler.mSpanIndex];
          i2 = this.mOrientationHelper.getDecoratedMeasurementInOther(paramState);
          j = k;
          i2 += k;
          k = m;
          m = i2;
        }
      }
      else
      {
        i2 = getPaddingTop() + this.mCachedBorders[paramRecycler.mSpanIndex];
        i = this.mOrientationHelper.getDecoratedMeasurementInOther(paramState);
        m = k;
        n = i2;
        k = i + i2;
      }
      layoutDecoratedWithMargins(paramState, j, n, m, k);
      if ((paramRecycler.isItemRemoved()) || (paramRecycler.isItemChanged())) {
        paramLayoutChunkResult.mIgnoreConsumed = true;
      }
      paramLayoutChunkResult.mFocusable |= paramState.hasFocusable();
      i2 = i3 + 1;
      i3 = m;
      m = k;
      k = i3;
    }
    Arrays.fill(this.mSet, null);
  }
  
  void onAnchorReady(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LinearLayoutManager.a parama, int paramInt)
  {
    super.onAnchorReady(paramRecycler, paramState, parama, paramInt);
    updateMeasurements();
    if ((paramState.getItemCount() > 0) && (!paramState.isPreLayout())) {
      ensureAnchorIsInCorrectSpan(paramRecycler, paramState, parama, paramInt);
    }
    ensureViewSet();
  }
  
  public View onFocusSearchFailed(View paramView, int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    View localView = findContainingItemView(paramView);
    Object localObject1 = null;
    if (localView == null) {
      return null;
    }
    Object localObject2 = (LayoutParams)localView.getLayoutParams();
    int i = ((LayoutParams)localObject2).mSpanIndex;
    int j = ((LayoutParams)localObject2).mSpanIndex + ((LayoutParams)localObject2).mSpanSize;
    if (super.onFocusSearchFailed(paramView, paramInt, paramRecycler, paramState) == null) {
      return null;
    }
    int k;
    if (convertFocusDirectionToLayoutDirection(paramInt) == 1) {
      k = 1;
    } else {
      k = 0;
    }
    if (k != this.mShouldReverseLayout) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    int n;
    if (paramInt != 0)
    {
      paramInt = getChildCount() - 1;
      m = -1;
      n = -1;
    }
    else
    {
      m = getChildCount();
      paramInt = 0;
      n = 1;
    }
    int i1;
    if ((this.mOrientation == 1) && (isLayoutRTL())) {
      i1 = 1;
    } else {
      i1 = 0;
    }
    int i2 = getSpanGroupIndex(paramRecycler, paramState, paramInt);
    paramView = null;
    int i3 = -1;
    int i4 = 0;
    int i5 = 0;
    int i6 = -1;
    int i7 = m;
    int m = i3;
    i3 = paramInt;
    while (i3 != i7)
    {
      paramInt = getSpanGroupIndex(paramRecycler, paramState, i3);
      localObject2 = getChildAt(i3);
      if (localObject2 == localView) {
        break;
      }
      if ((((View)localObject2).hasFocusable()) && (paramInt != i2))
      {
        if (localObject1 != null) {
          break;
        }
      }
      else
      {
        LayoutParams localLayoutParams = (LayoutParams)((View)localObject2).getLayoutParams();
        int i8 = localLayoutParams.mSpanIndex;
        int i9 = localLayoutParams.mSpanIndex + localLayoutParams.mSpanSize;
        if ((((View)localObject2).hasFocusable()) && (i8 == i) && (i9 == j)) {
          return (View)localObject2;
        }
        if (((((View)localObject2).hasFocusable()) && (localObject1 == null)) || ((!((View)localObject2).hasFocusable()) && (paramView == null)))
        {
          paramInt = 1;
        }
        else
        {
          paramInt = Math.max(i8, i);
          i10 = Math.min(i9, j) - paramInt;
          if (((View)localObject2).hasFocusable())
          {
            if (i10 > i4)
            {
              paramInt = 1;
              break label473;
            }
            if (i10 == i4)
            {
              if (i8 > m) {
                paramInt = 1;
              } else {
                paramInt = 0;
              }
              if (i1 == paramInt)
              {
                paramInt = 1;
                break label473;
              }
            }
          }
          else if (localObject1 == null)
          {
            paramInt = 0;
            if (isViewPartiallyVisible((View)localObject2, false, true))
            {
              i11 = i5;
              if (i10 > i11)
              {
                paramInt = 1;
                break label473;
              }
              if (i10 == i11)
              {
                if (i8 > i6) {
                  paramInt = 1;
                }
                if (i1 != paramInt) {
                  break label471;
                }
                paramInt = 1;
                break label473;
              }
            }
            else {}
          }
          label471:
          paramInt = 0;
        }
        label473:
        int i11 = m;
        int i10 = i5;
        if (paramInt != 0)
        {
          if (((View)localObject2).hasFocusable())
          {
            i5 = localLayoutParams.mSpanIndex;
            paramInt = Math.min(i9, j);
            m = Math.max(i8, i);
            i4 = paramInt - m;
            localObject1 = localObject2;
            paramInt = i10;
            break label579;
          }
          i6 = localLayoutParams.mSpanIndex;
          paramInt = Math.min(i9, j);
          i5 = Math.max(i8, i);
          paramView = (View)localObject2;
          paramInt -= i5;
          i5 = i11;
          break label579;
        }
      }
      paramInt = i5;
      i5 = m;
      label579:
      i3 += n;
      m = i5;
      i5 = paramInt;
    }
    if (localObject1 != null) {
      paramView = (View)localObject1;
    }
    return paramView;
  }
  
  public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
  {
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    if (!(localLayoutParams instanceof LayoutParams))
    {
      super.onInitializeAccessibilityNodeInfoForItem(paramView, paramAccessibilityNodeInfoCompat);
      return;
    }
    paramView = (LayoutParams)localLayoutParams;
    int i = getSpanGroupIndex(paramRecycler, paramState, paramView.getViewLayoutPosition());
    int j;
    int k;
    boolean bool;
    if (this.mOrientation == 0)
    {
      j = paramView.getSpanIndex();
      k = paramView.getSpanSize();
      if ((this.mSpanCount > 1) && (paramView.getSpanSize() == this.mSpanCount)) {
        bool = true;
      } else {
        bool = false;
      }
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(j, k, i, 1, bool, false));
    }
    else
    {
      j = paramView.getSpanIndex();
      k = paramView.getSpanSize();
      if ((this.mSpanCount > 1) && (paramView.getSpanSize() == this.mSpanCount)) {
        bool = true;
      } else {
        bool = false;
      }
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(i, 1, j, k, bool, false));
    }
  }
  
  public void onItemsAdded(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void onItemsChanged(RecyclerView paramRecyclerView)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void onItemsMoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, int paramInt3)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void onItemsRemoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void onItemsUpdated(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, Object paramObject)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (paramState.isPreLayout()) {
      cachePreLayoutSpanMapping();
    }
    super.onLayoutChildren(paramRecycler, paramState);
    clearPreLayoutSpanMappingCache();
  }
  
  public void onLayoutCompleted(RecyclerView.State paramState)
  {
    super.onLayoutCompleted(paramState);
    this.mPendingSpanCountChange = false;
  }
  
  public int scrollHorizontallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    updateMeasurements();
    ensureViewSet();
    return super.scrollHorizontallyBy(paramInt, paramRecycler, paramState);
  }
  
  public int scrollVerticallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    updateMeasurements();
    ensureViewSet();
    return super.scrollVerticallyBy(paramInt, paramRecycler, paramState);
  }
  
  public void setMeasuredDimension(Rect paramRect, int paramInt1, int paramInt2)
  {
    if (this.mCachedBorders == null) {
      super.setMeasuredDimension(paramRect, paramInt1, paramInt2);
    }
    int i = getPaddingLeft() + getPaddingRight();
    int j = getPaddingTop() + getPaddingBottom();
    if (this.mOrientation == 1)
    {
      paramInt2 = chooseSize(paramInt2, paramRect.height() + j, getMinimumHeight());
      paramRect = this.mCachedBorders;
      j = chooseSize(paramInt1, paramRect[(paramRect.length - 1)] + i, getMinimumWidth());
      paramInt1 = paramInt2;
      paramInt2 = j;
    }
    else
    {
      paramInt1 = chooseSize(paramInt1, paramRect.width() + i, getMinimumWidth());
      paramRect = this.mCachedBorders;
      j = chooseSize(paramInt2, paramRect[(paramRect.length - 1)] + j, getMinimumHeight());
      paramInt2 = paramInt1;
      paramInt1 = j;
    }
    setMeasuredDimension(paramInt2, paramInt1);
  }
  
  public void setSpanCount(int paramInt)
  {
    if (paramInt == this.mSpanCount) {
      return;
    }
    this.mPendingSpanCountChange = true;
    if (paramInt >= 1)
    {
      this.mSpanCount = paramInt;
      this.mSpanSizeLookup.invalidateSpanIndexCache();
      requestLayout();
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Span count should be at least 1. Provided ");
    localStringBuilder.append(paramInt);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public void setSpanSizeLookup(SpanSizeLookup paramSpanSizeLookup)
  {
    this.mSpanSizeLookup = paramSpanSizeLookup;
  }
  
  public void setStackFromEnd(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      super.setStackFromEnd(false);
      return;
    }
    throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
  }
  
  public boolean supportsPredictiveItemAnimations()
  {
    boolean bool;
    if ((this.mPendingSavedState == null) && (!this.mPendingSpanCountChange)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final class DefaultSpanSizeLookup
    extends GridLayoutManager.SpanSizeLookup
  {
    public int getSpanIndex(int paramInt1, int paramInt2)
    {
      return paramInt1 % paramInt2;
    }
    
    public int getSpanSize(int paramInt)
    {
      return 1;
    }
  }
  
  public static class LayoutParams
    extends RecyclerView.LayoutParams
  {
    public static final int INVALID_SPAN_ID = -1;
    int mSpanIndex = -1;
    int mSpanSize = 0;
    
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
    
    public int getSpanIndex()
    {
      return this.mSpanIndex;
    }
    
    public int getSpanSize()
    {
      return this.mSpanSize;
    }
  }
  
  public static abstract class SpanSizeLookup
  {
    private boolean mCacheSpanIndices = false;
    final SparseIntArray mSpanIndexCache = new SparseIntArray();
    
    int findReferenceIndexFromCache(int paramInt)
    {
      int i = this.mSpanIndexCache.size() - 1;
      int j = 0;
      while (j <= i)
      {
        int k = j + i >>> 1;
        if (this.mSpanIndexCache.keyAt(k) < paramInt) {
          j = k + 1;
        } else {
          i = k - 1;
        }
      }
      paramInt = j - 1;
      if ((paramInt >= 0) && (paramInt < this.mSpanIndexCache.size())) {
        return this.mSpanIndexCache.keyAt(paramInt);
      }
      return -1;
    }
    
    int getCachedSpanIndex(int paramInt1, int paramInt2)
    {
      if (!this.mCacheSpanIndices) {
        return getSpanIndex(paramInt1, paramInt2);
      }
      int i = this.mSpanIndexCache.get(paramInt1, -1);
      if (i != -1) {
        return i;
      }
      paramInt2 = getSpanIndex(paramInt1, paramInt2);
      this.mSpanIndexCache.put(paramInt1, paramInt2);
      return paramInt2;
    }
    
    public int getSpanGroupIndex(int paramInt1, int paramInt2)
    {
      int i = getSpanSize(paramInt1);
      int j = 0;
      int k = 0;
      int i2;
      for (int m = 0; j < paramInt1; m = i2)
      {
        int n = getSpanSize(j);
        int i1 = k + n;
        if (i1 == paramInt2)
        {
          i2 = m + 1;
          k = 0;
        }
        else
        {
          k = i1;
          i2 = m;
          if (i1 > paramInt2)
          {
            i2 = m + 1;
            k = n;
          }
        }
        j++;
      }
      paramInt1 = m;
      if (k + i > paramInt2) {
        paramInt1 = m + 1;
      }
      return paramInt1;
    }
    
    public int getSpanIndex(int paramInt1, int paramInt2)
    {
      int i = getSpanSize(paramInt1);
      if (i == paramInt2) {
        return 0;
      }
      if ((this.mCacheSpanIndices) && (this.mSpanIndexCache.size() > 0))
      {
        j = findReferenceIndexFromCache(paramInt1);
        if (j >= 0)
        {
          k = this.mSpanIndexCache.get(j) + getSpanSize(j);
          j++;
          break label72;
        }
      }
      int j = 0;
      int k = 0;
      label72:
      while (j < paramInt1)
      {
        int m = getSpanSize(j);
        int n = k + m;
        if (n == paramInt2)
        {
          k = 0;
        }
        else
        {
          k = n;
          if (n > paramInt2) {
            k = m;
          }
        }
        j++;
      }
      if (i + k <= paramInt2) {
        return k;
      }
      return 0;
    }
    
    public abstract int getSpanSize(int paramInt);
    
    public void invalidateSpanIndexCache()
    {
      this.mSpanIndexCache.clear();
    }
    
    public boolean isSpanIndexCacheEnabled()
    {
      return this.mCacheSpanIndices;
    }
    
    public void setSpanIndexCacheEnabled(boolean paramBoolean)
    {
      this.mCacheSpanIndices = paramBoolean;
    }
  }
}


/* Location:              ~/android/support/v7/widget/GridLayoutManager.class
 *
 * Reversed by:           J
 */