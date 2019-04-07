package android.support.v4.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewPager
  extends ViewGroup
{
  private static final int CLOSE_ENOUGH = 2;
  private static final Comparator<ItemInfo> COMPARATOR = new Comparator()
  {
    public int compare(ViewPager.ItemInfo paramAnonymousItemInfo1, ViewPager.ItemInfo paramAnonymousItemInfo2)
    {
      return paramAnonymousItemInfo1.position - paramAnonymousItemInfo2.position;
    }
  };
  private static final boolean DEBUG = false;
  private static final int DEFAULT_GUTTER_SIZE = 16;
  private static final int DEFAULT_OFFSCREEN_PAGES = 1;
  private static final int DRAW_ORDER_DEFAULT = 0;
  private static final int DRAW_ORDER_FORWARD = 1;
  private static final int DRAW_ORDER_REVERSE = 2;
  private static final int INVALID_POINTER = -1;
  static final int[] LAYOUT_ATTRS = { 16842931 };
  private static final int MAX_SETTLE_DURATION = 600;
  private static final int MIN_DISTANCE_FOR_FLING = 25;
  private static final int MIN_FLING_VELOCITY = 400;
  public static final int SCROLL_STATE_DRAGGING = 1;
  public static final int SCROLL_STATE_IDLE = 0;
  public static final int SCROLL_STATE_SETTLING = 2;
  private static final String TAG = "ViewPager";
  private static final boolean USE_CACHE = false;
  private static final Interpolator sInterpolator = new Interpolator()
  {
    public float getInterpolation(float paramAnonymousFloat)
    {
      paramAnonymousFloat -= 1.0F;
      return paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat + 1.0F;
    }
  };
  private static final ViewPositionComparator sPositionComparator = new ViewPositionComparator();
  private int mActivePointerId = -1;
  PagerAdapter mAdapter;
  private List<OnAdapterChangeListener> mAdapterChangeListeners;
  private int mBottomPageBounds;
  private boolean mCalledSuper;
  private int mChildHeightMeasureSpec;
  private int mChildWidthMeasureSpec;
  private int mCloseEnough;
  int mCurItem;
  private int mDecorChildCount;
  private int mDefaultGutterSize;
  private int mDrawingOrder;
  private ArrayList<View> mDrawingOrderedChildren;
  private final Runnable mEndScrollRunnable = new Runnable()
  {
    public void run()
    {
      ViewPager.this.setScrollState(0);
      ViewPager.this.populate();
    }
  };
  private int mExpectedAdapterCount;
  private long mFakeDragBeginTime;
  private boolean mFakeDragging;
  private boolean mFirstLayout = true;
  private float mFirstOffset = -3.4028235E38F;
  private int mFlingDistance;
  private int mGutterSize;
  private boolean mInLayout;
  private float mInitialMotionX;
  private float mInitialMotionY;
  private OnPageChangeListener mInternalPageChangeListener;
  private boolean mIsBeingDragged;
  private boolean mIsScrollStarted;
  private boolean mIsUnableToDrag;
  private final ArrayList<ItemInfo> mItems = new ArrayList();
  private float mLastMotionX;
  private float mLastMotionY;
  private float mLastOffset = Float.MAX_VALUE;
  private EdgeEffect mLeftEdge;
  private Drawable mMarginDrawable;
  private int mMaximumVelocity;
  private int mMinimumVelocity;
  private boolean mNeedCalculatePageOffsets = false;
  private PagerObserver mObserver;
  private int mOffscreenPageLimit = 1;
  private OnPageChangeListener mOnPageChangeListener;
  private List<OnPageChangeListener> mOnPageChangeListeners;
  private int mPageMargin;
  private PageTransformer mPageTransformer;
  private int mPageTransformerLayerType;
  private boolean mPopulatePending;
  private Parcelable mRestoredAdapterState = null;
  private ClassLoader mRestoredClassLoader = null;
  private int mRestoredCurItem = -1;
  private EdgeEffect mRightEdge;
  private int mScrollState = 0;
  private Scroller mScroller;
  private boolean mScrollingCacheEnabled;
  private final ItemInfo mTempItem = new ItemInfo();
  private final Rect mTempRect = new Rect();
  private int mTopPageBounds;
  private int mTouchSlop;
  private VelocityTracker mVelocityTracker;
  
  public ViewPager(Context paramContext)
  {
    super(paramContext);
    initViewPager();
  }
  
  public ViewPager(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initViewPager();
  }
  
  private void calculatePageOffsets(ItemInfo paramItemInfo1, int paramInt, ItemInfo paramItemInfo2)
  {
    int i = this.mAdapter.getCount();
    int j = getClientWidth();
    float f1;
    if (j > 0) {
      f1 = this.mPageMargin / j;
    } else {
      f1 = 0.0F;
    }
    if (paramItemInfo2 != null)
    {
      j = paramItemInfo2.position;
      if (j < paramItemInfo1.position)
      {
        f2 = paramItemInfo2.offset + paramItemInfo2.widthFactor + f1;
        j++;
        k = 0;
        while ((j <= paramItemInfo1.position) && (k < this.mItems.size()))
        {
          for (paramItemInfo2 = (ItemInfo)this.mItems.get(k);; paramItemInfo2 = (ItemInfo)this.mItems.get(k))
          {
            m = j;
            f3 = f2;
            if (j <= paramItemInfo2.position) {
              break;
            }
            m = j;
            f3 = f2;
            if (k >= this.mItems.size() - 1) {
              break;
            }
            k++;
          }
          while (m < paramItemInfo2.position)
          {
            f3 += this.mAdapter.getPageWidth(m) + f1;
            m++;
          }
          paramItemInfo2.offset = f3;
          f2 = f3 + (paramItemInfo2.widthFactor + f1);
          j = m + 1;
        }
      }
      if (j > paramItemInfo1.position)
      {
        k = this.mItems.size() - 1;
        f2 = paramItemInfo2.offset;
        j--;
        while ((j >= paramItemInfo1.position) && (k >= 0))
        {
          for (paramItemInfo2 = (ItemInfo)this.mItems.get(k);; paramItemInfo2 = (ItemInfo)this.mItems.get(k))
          {
            m = j;
            f3 = f2;
            if (j >= paramItemInfo2.position) {
              break;
            }
            m = j;
            f3 = f2;
            if (k <= 0) {
              break;
            }
            k--;
          }
          while (m > paramItemInfo2.position)
          {
            f3 -= this.mAdapter.getPageWidth(m) + f1;
            m--;
          }
          f2 = f3 - (paramItemInfo2.widthFactor + f1);
          paramItemInfo2.offset = f2;
          j = m - 1;
        }
      }
    }
    int m = this.mItems.size();
    float f3 = paramItemInfo1.offset;
    j = paramItemInfo1.position - 1;
    if (paramItemInfo1.position == 0) {
      f2 = paramItemInfo1.offset;
    } else {
      f2 = -3.4028235E38F;
    }
    this.mFirstOffset = f2;
    int k = paramItemInfo1.position;
    i--;
    if (k == i) {
      f2 = paramItemInfo1.offset + paramItemInfo1.widthFactor - 1.0F;
    } else {
      f2 = Float.MAX_VALUE;
    }
    this.mLastOffset = f2;
    k = paramInt - 1;
    float f2 = f3;
    while (k >= 0)
    {
      paramItemInfo2 = (ItemInfo)this.mItems.get(k);
      while (j > paramItemInfo2.position)
      {
        f2 -= this.mAdapter.getPageWidth(j) + f1;
        j--;
      }
      f2 -= paramItemInfo2.widthFactor + f1;
      paramItemInfo2.offset = f2;
      if (paramItemInfo2.position == 0) {
        this.mFirstOffset = f2;
      }
      k--;
      j--;
    }
    f2 = paramItemInfo1.offset + paramItemInfo1.widthFactor + f1;
    k = paramItemInfo1.position + 1;
    j = paramInt + 1;
    for (paramInt = k; j < m; paramInt++)
    {
      paramItemInfo1 = (ItemInfo)this.mItems.get(j);
      while (paramInt < paramItemInfo1.position)
      {
        f2 += this.mAdapter.getPageWidth(paramInt) + f1;
        paramInt++;
      }
      if (paramItemInfo1.position == i) {
        this.mLastOffset = (paramItemInfo1.widthFactor + f2 - 1.0F);
      }
      paramItemInfo1.offset = f2;
      f2 += paramItemInfo1.widthFactor + f1;
      j++;
    }
    this.mNeedCalculatePageOffsets = false;
  }
  
  private void completeScroll(boolean paramBoolean)
  {
    if (this.mScrollState == 2) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      setScrollingCacheEnabled(false);
      if ((this.mScroller.isFinished() ^ true))
      {
        this.mScroller.abortAnimation();
        int j = getScrollX();
        k = getScrollY();
        int m = this.mScroller.getCurrX();
        n = this.mScroller.getCurrY();
        if ((j != m) || (k != n))
        {
          scrollTo(m, n);
          if (m != j) {
            pageScrolled(m);
          }
        }
      }
    }
    this.mPopulatePending = false;
    int k = 0;
    int n = i;
    for (int i = k; i < this.mItems.size(); i++)
    {
      ItemInfo localItemInfo = (ItemInfo)this.mItems.get(i);
      if (localItemInfo.scrolling)
      {
        localItemInfo.scrolling = false;
        n = 1;
      }
    }
    if (n != 0) {
      if (paramBoolean) {
        ViewCompat.postOnAnimation(this, this.mEndScrollRunnable);
      } else {
        this.mEndScrollRunnable.run();
      }
    }
  }
  
  private int determineTargetPage(int paramInt1, float paramFloat, int paramInt2, int paramInt3)
  {
    if ((Math.abs(paramInt3) > this.mFlingDistance) && (Math.abs(paramInt2) > this.mMinimumVelocity))
    {
      if (paramInt2 <= 0) {
        paramInt1++;
      }
    }
    else
    {
      float f;
      if (paramInt1 >= this.mCurItem) {
        f = 0.4F;
      } else {
        f = 0.6F;
      }
      paramInt1 += (int)(paramFloat + f);
    }
    paramInt2 = paramInt1;
    if (this.mItems.size() > 0)
    {
      ItemInfo localItemInfo = (ItemInfo)this.mItems.get(0);
      Object localObject = this.mItems;
      localObject = (ItemInfo)((ArrayList)localObject).get(((ArrayList)localObject).size() - 1);
      paramInt2 = Math.max(localItemInfo.position, Math.min(paramInt1, ((ItemInfo)localObject).position));
    }
    return paramInt2;
  }
  
  private void dispatchOnPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    Object localObject = this.mOnPageChangeListener;
    if (localObject != null) {
      ((OnPageChangeListener)localObject).onPageScrolled(paramInt1, paramFloat, paramInt2);
    }
    localObject = this.mOnPageChangeListeners;
    if (localObject != null)
    {
      int i = 0;
      int j = ((List)localObject).size();
      while (i < j)
      {
        localObject = (OnPageChangeListener)this.mOnPageChangeListeners.get(i);
        if (localObject != null) {
          ((OnPageChangeListener)localObject).onPageScrolled(paramInt1, paramFloat, paramInt2);
        }
        i++;
      }
    }
    localObject = this.mInternalPageChangeListener;
    if (localObject != null) {
      ((OnPageChangeListener)localObject).onPageScrolled(paramInt1, paramFloat, paramInt2);
    }
  }
  
  private void dispatchOnPageSelected(int paramInt)
  {
    Object localObject = this.mOnPageChangeListener;
    if (localObject != null) {
      ((OnPageChangeListener)localObject).onPageSelected(paramInt);
    }
    localObject = this.mOnPageChangeListeners;
    if (localObject != null)
    {
      int i = 0;
      int j = ((List)localObject).size();
      while (i < j)
      {
        localObject = (OnPageChangeListener)this.mOnPageChangeListeners.get(i);
        if (localObject != null) {
          ((OnPageChangeListener)localObject).onPageSelected(paramInt);
        }
        i++;
      }
    }
    localObject = this.mInternalPageChangeListener;
    if (localObject != null) {
      ((OnPageChangeListener)localObject).onPageSelected(paramInt);
    }
  }
  
  private void dispatchOnScrollStateChanged(int paramInt)
  {
    Object localObject = this.mOnPageChangeListener;
    if (localObject != null) {
      ((OnPageChangeListener)localObject).onPageScrollStateChanged(paramInt);
    }
    localObject = this.mOnPageChangeListeners;
    if (localObject != null)
    {
      int i = 0;
      int j = ((List)localObject).size();
      while (i < j)
      {
        localObject = (OnPageChangeListener)this.mOnPageChangeListeners.get(i);
        if (localObject != null) {
          ((OnPageChangeListener)localObject).onPageScrollStateChanged(paramInt);
        }
        i++;
      }
    }
    localObject = this.mInternalPageChangeListener;
    if (localObject != null) {
      ((OnPageChangeListener)localObject).onPageScrollStateChanged(paramInt);
    }
  }
  
  private void enableLayers(boolean paramBoolean)
  {
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      int k;
      if (paramBoolean) {
        k = this.mPageTransformerLayerType;
      } else {
        k = 0;
      }
      getChildAt(j).setLayerType(k, null);
    }
  }
  
  private void endDrag()
  {
    this.mIsBeingDragged = false;
    this.mIsUnableToDrag = false;
    VelocityTracker localVelocityTracker = this.mVelocityTracker;
    if (localVelocityTracker != null)
    {
      localVelocityTracker.recycle();
      this.mVelocityTracker = null;
    }
  }
  
  private Rect getChildRectInPagerCoordinates(Rect paramRect, View paramView)
  {
    Rect localRect = paramRect;
    if (paramRect == null) {
      localRect = new Rect();
    }
    if (paramView == null)
    {
      localRect.set(0, 0, 0, 0);
      return localRect;
    }
    localRect.left = paramView.getLeft();
    localRect.right = paramView.getRight();
    localRect.top = paramView.getTop();
    localRect.bottom = paramView.getBottom();
    for (paramRect = paramView.getParent(); ((paramRect instanceof ViewGroup)) && (paramRect != this); paramRect = paramRect.getParent())
    {
      paramRect = (ViewGroup)paramRect;
      localRect.left += paramRect.getLeft();
      localRect.right += paramRect.getRight();
      localRect.top += paramRect.getTop();
      localRect.bottom += paramRect.getBottom();
    }
    return localRect;
  }
  
  private int getClientWidth()
  {
    return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
  }
  
  private ItemInfo infoForCurrentScrollPosition()
  {
    int i = getClientWidth();
    float f1;
    if (i > 0) {
      f1 = getScrollX() / i;
    } else {
      f1 = 0.0F;
    }
    float f2;
    if (i > 0) {
      f2 = this.mPageMargin / i;
    } else {
      f2 = 0.0F;
    }
    Object localObject = null;
    i = 0;
    int j = 1;
    int k = -1;
    float f3 = 0.0F;
    float f4 = 0.0F;
    while (i < this.mItems.size())
    {
      ItemInfo localItemInfo1 = (ItemInfo)this.mItems.get(i);
      int m = i;
      ItemInfo localItemInfo2 = localItemInfo1;
      if (j == 0)
      {
        int n = localItemInfo1.position;
        k++;
        m = i;
        localItemInfo2 = localItemInfo1;
        if (n != k)
        {
          localItemInfo2 = this.mTempItem;
          localItemInfo2.offset = (f3 + f4 + f2);
          localItemInfo2.position = k;
          localItemInfo2.widthFactor = this.mAdapter.getPageWidth(localItemInfo2.position);
          m = i - 1;
        }
      }
      f3 = localItemInfo2.offset;
      f4 = localItemInfo2.widthFactor;
      if ((j == 0) && (f1 < f3)) {
        return (ItemInfo)localObject;
      }
      if ((f1 >= f4 + f3 + f2) && (m != this.mItems.size() - 1))
      {
        k = localItemInfo2.position;
        f4 = localItemInfo2.widthFactor;
        i = m + 1;
        j = 0;
        localObject = localItemInfo2;
      }
      else
      {
        return localItemInfo2;
      }
    }
    return (ItemInfo)localObject;
  }
  
  private static boolean isDecorView(View paramView)
  {
    boolean bool;
    if (paramView.getClass().getAnnotation(DecorView.class) != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean isGutterDrag(float paramFloat1, float paramFloat2)
  {
    boolean bool;
    if (((paramFloat1 < this.mGutterSize) && (paramFloat2 > 0.0F)) || ((paramFloat1 > getWidth() - this.mGutterSize) && (paramFloat2 < 0.0F))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void onSecondaryPointerUp(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionIndex();
    if (paramMotionEvent.getPointerId(i) == this.mActivePointerId)
    {
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      }
      this.mLastMotionX = paramMotionEvent.getX(i);
      this.mActivePointerId = paramMotionEvent.getPointerId(i);
      paramMotionEvent = this.mVelocityTracker;
      if (paramMotionEvent != null) {
        paramMotionEvent.clear();
      }
    }
  }
  
  private boolean pageScrolled(int paramInt)
  {
    if (this.mItems.size() == 0)
    {
      if (this.mFirstLayout) {
        return false;
      }
      this.mCalledSuper = false;
      onPageScrolled(0, 0.0F, 0);
      if (this.mCalledSuper) {
        return false;
      }
      throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }
    ItemInfo localItemInfo = infoForCurrentScrollPosition();
    int i = getClientWidth();
    int j = this.mPageMargin;
    float f1 = j;
    float f2 = i;
    f1 /= f2;
    int k = localItemInfo.position;
    f2 = (paramInt / f2 - localItemInfo.offset) / (localItemInfo.widthFactor + f1);
    paramInt = (int)((i + j) * f2);
    this.mCalledSuper = false;
    onPageScrolled(k, f2, paramInt);
    if (this.mCalledSuper) {
      return true;
    }
    throw new IllegalStateException("onPageScrolled did not call superclass implementation");
  }
  
  private boolean performDrag(float paramFloat)
  {
    float f1 = this.mLastMotionX;
    this.mLastMotionX = paramFloat;
    float f2 = getScrollX() + (f1 - paramFloat);
    float f3 = getClientWidth();
    paramFloat = this.mFirstOffset * f3;
    f1 = this.mLastOffset * f3;
    Object localObject1 = this.mItems;
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    localObject1 = (ItemInfo)((ArrayList)localObject1).get(0);
    Object localObject2 = this.mItems;
    localObject2 = (ItemInfo)((ArrayList)localObject2).get(((ArrayList)localObject2).size() - 1);
    if (((ItemInfo)localObject1).position != 0)
    {
      paramFloat = ((ItemInfo)localObject1).offset * f3;
      i = 0;
    }
    else
    {
      i = 1;
    }
    int j;
    if (((ItemInfo)localObject2).position != this.mAdapter.getCount() - 1)
    {
      f1 = ((ItemInfo)localObject2).offset * f3;
      j = 0;
    }
    else
    {
      j = 1;
    }
    if (f2 < paramFloat)
    {
      if (i != 0)
      {
        this.mLeftEdge.onPull(Math.abs(paramFloat - f2) / f3);
        bool3 = true;
      }
    }
    else
    {
      bool3 = bool2;
      paramFloat = f2;
      if (f2 > f1)
      {
        bool3 = bool1;
        if (j != 0)
        {
          this.mRightEdge.onPull(Math.abs(f2 - f1) / f3);
          bool3 = true;
        }
        paramFloat = f1;
      }
    }
    f1 = this.mLastMotionX;
    int i = (int)paramFloat;
    this.mLastMotionX = (f1 + (paramFloat - i));
    scrollTo(i, getScrollY());
    pageScrolled(i);
    return bool3;
  }
  
  private void recomputeScrollPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt2 > 0) && (!this.mItems.isEmpty()))
    {
      if (!this.mScroller.isFinished())
      {
        this.mScroller.setFinalX(getCurrentItem() * getClientWidth());
      }
      else
      {
        int i = getPaddingLeft();
        int j = getPaddingRight();
        int k = getPaddingLeft();
        int m = getPaddingRight();
        scrollTo((int)(getScrollX() / (paramInt2 - k - m + paramInt4) * (paramInt1 - i - j + paramInt3)), getScrollY());
      }
    }
    else
    {
      ItemInfo localItemInfo = infoForPosition(this.mCurItem);
      float f;
      if (localItemInfo != null) {
        f = Math.min(localItemInfo.offset, this.mLastOffset);
      } else {
        f = 0.0F;
      }
      paramInt1 = (int)(f * (paramInt1 - getPaddingLeft() - getPaddingRight()));
      if (paramInt1 != getScrollX())
      {
        completeScroll(false);
        scrollTo(paramInt1, getScrollY());
      }
    }
  }
  
  private void removeNonDecorViews()
  {
    int j;
    for (int i = 0; i < getChildCount(); i = j + 1)
    {
      j = i;
      if (!((LayoutParams)getChildAt(i).getLayoutParams()).isDecor)
      {
        removeViewAt(i);
        j = i - 1;
      }
    }
  }
  
  private void requestParentDisallowInterceptTouchEvent(boolean paramBoolean)
  {
    ViewParent localViewParent = getParent();
    if (localViewParent != null) {
      localViewParent.requestDisallowInterceptTouchEvent(paramBoolean);
    }
  }
  
  private boolean resetTouch()
  {
    this.mActivePointerId = -1;
    endDrag();
    this.mLeftEdge.onRelease();
    this.mRightEdge.onRelease();
    boolean bool;
    if ((!this.mLeftEdge.isFinished()) && (!this.mRightEdge.isFinished())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private void scrollToItem(int paramInt1, boolean paramBoolean1, int paramInt2, boolean paramBoolean2)
  {
    ItemInfo localItemInfo = infoForPosition(paramInt1);
    int i;
    if (localItemInfo != null) {
      i = (int)(getClientWidth() * Math.max(this.mFirstOffset, Math.min(localItemInfo.offset, this.mLastOffset)));
    } else {
      i = 0;
    }
    if (paramBoolean1)
    {
      smoothScrollTo(i, 0, paramInt2);
      if (paramBoolean2) {
        dispatchOnPageSelected(paramInt1);
      }
    }
    else
    {
      if (paramBoolean2) {
        dispatchOnPageSelected(paramInt1);
      }
      completeScroll(false);
      scrollTo(i, 0);
      pageScrolled(i);
    }
  }
  
  private void setScrollingCacheEnabled(boolean paramBoolean)
  {
    if (this.mScrollingCacheEnabled != paramBoolean) {
      this.mScrollingCacheEnabled = paramBoolean;
    }
  }
  
  private void sortChildDrawingOrder()
  {
    if (this.mDrawingOrder != 0)
    {
      Object localObject = this.mDrawingOrderedChildren;
      if (localObject == null) {
        this.mDrawingOrderedChildren = new ArrayList();
      } else {
        ((ArrayList)localObject).clear();
      }
      int i = getChildCount();
      for (int j = 0; j < i; j++)
      {
        localObject = getChildAt(j);
        this.mDrawingOrderedChildren.add(localObject);
      }
      Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
    }
  }
  
  public void addFocusables(ArrayList<View> paramArrayList, int paramInt1, int paramInt2)
  {
    int i = paramArrayList.size();
    int j = getDescendantFocusability();
    if (j != 393216) {
      for (int k = 0; k < getChildCount(); k++)
      {
        View localView = getChildAt(k);
        if (localView.getVisibility() == 0)
        {
          ItemInfo localItemInfo = infoForChild(localView);
          if ((localItemInfo != null) && (localItemInfo.position == this.mCurItem)) {
            localView.addFocusables(paramArrayList, paramInt1, paramInt2);
          }
        }
      }
    }
    if ((j != 262144) || (i == paramArrayList.size()))
    {
      if (!isFocusable()) {
        return;
      }
      if (((paramInt2 & 0x1) == 1) && (isInTouchMode()) && (!isFocusableInTouchMode())) {
        return;
      }
      if (paramArrayList != null) {
        paramArrayList.add(this);
      }
    }
  }
  
  ItemInfo addNewItem(int paramInt1, int paramInt2)
  {
    ItemInfo localItemInfo = new ItemInfo();
    localItemInfo.position = paramInt1;
    localItemInfo.object = this.mAdapter.instantiateItem(this, paramInt1);
    localItemInfo.widthFactor = this.mAdapter.getPageWidth(paramInt1);
    if ((paramInt2 >= 0) && (paramInt2 < this.mItems.size())) {
      this.mItems.add(paramInt2, localItemInfo);
    } else {
      this.mItems.add(localItemInfo);
    }
    return localItemInfo;
  }
  
  public void addOnAdapterChangeListener(OnAdapterChangeListener paramOnAdapterChangeListener)
  {
    if (this.mAdapterChangeListeners == null) {
      this.mAdapterChangeListeners = new ArrayList();
    }
    this.mAdapterChangeListeners.add(paramOnAdapterChangeListener);
  }
  
  public void addOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener)
  {
    if (this.mOnPageChangeListeners == null) {
      this.mOnPageChangeListeners = new ArrayList();
    }
    this.mOnPageChangeListeners.add(paramOnPageChangeListener);
  }
  
  public void addTouchables(ArrayList<View> paramArrayList)
  {
    for (int i = 0; i < getChildCount(); i++)
    {
      View localView = getChildAt(i);
      if (localView.getVisibility() == 0)
      {
        ItemInfo localItemInfo = infoForChild(localView);
        if ((localItemInfo != null) && (localItemInfo.position == this.mCurItem)) {
          localView.addTouchables(paramArrayList);
        }
      }
    }
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    ViewGroup.LayoutParams localLayoutParams = paramLayoutParams;
    if (!checkLayoutParams(paramLayoutParams)) {
      localLayoutParams = generateLayoutParams(paramLayoutParams);
    }
    paramLayoutParams = (LayoutParams)localLayoutParams;
    paramLayoutParams.isDecor |= isDecorView(paramView);
    if (this.mInLayout)
    {
      if ((paramLayoutParams != null) && (paramLayoutParams.isDecor)) {
        throw new IllegalStateException("Cannot add pager decor view during layout");
      }
      paramLayoutParams.needsMeasure = true;
      addViewInLayout(paramView, paramInt, localLayoutParams);
    }
    else
    {
      super.addView(paramView, paramInt, localLayoutParams);
    }
  }
  
  public boolean arrowScroll(int paramInt)
  {
    View localView1 = findFocus();
    boolean bool = false;
    View localView2 = null;
    Object localObject;
    int i;
    if (localView1 == this)
    {
      localObject = localView2;
    }
    else
    {
      if (localView1 != null)
      {
        for (localObject = localView1.getParent(); (localObject instanceof ViewGroup); localObject = ((ViewParent)localObject).getParent()) {
          if (localObject == this)
          {
            i = 1;
            break label67;
          }
        }
        i = 0;
        label67:
        if (i == 0)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(localView1.getClass().getSimpleName());
          for (localObject = localView1.getParent(); (localObject instanceof ViewGroup); localObject = ((ViewParent)localObject).getParent())
          {
            localStringBuilder.append(" => ");
            localStringBuilder.append(localObject.getClass().getSimpleName());
          }
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("arrowScroll tried to find focus based on non-child current focused view ");
          ((StringBuilder)localObject).append(localStringBuilder.toString());
          Log.e("ViewPager", ((StringBuilder)localObject).toString());
          localObject = localView2;
          break label193;
        }
      }
      localObject = localView1;
    }
    label193:
    localView2 = FocusFinder.getInstance().findNextFocus(this, (View)localObject, paramInt);
    if ((localView2 != null) && (localView2 != localObject))
    {
      int j;
      if (paramInt == 17)
      {
        i = getChildRectInPagerCoordinates(this.mTempRect, localView2).left;
        j = getChildRectInPagerCoordinates(this.mTempRect, (View)localObject).left;
        if ((localObject != null) && (i >= j)) {
          bool = pageLeft();
        } else {
          bool = localView2.requestFocus();
        }
      }
      else if (paramInt == 66)
      {
        j = getChildRectInPagerCoordinates(this.mTempRect, localView2).left;
        i = getChildRectInPagerCoordinates(this.mTempRect, (View)localObject).left;
        if ((localObject != null) && (j <= i)) {
          bool = pageRight();
        } else {
          bool = localView2.requestFocus();
        }
      }
    }
    else if ((paramInt != 17) && (paramInt != 1))
    {
      if ((paramInt == 66) || (paramInt == 2)) {
        bool = pageRight();
      }
    }
    else
    {
      bool = pageLeft();
    }
    if (bool) {
      playSoundEffect(SoundEffectConstants.getContantForFocusDirection(paramInt));
    }
    return bool;
  }
  
  public boolean beginFakeDrag()
  {
    if (this.mIsBeingDragged) {
      return false;
    }
    this.mFakeDragging = true;
    setScrollState(1);
    this.mLastMotionX = 0.0F;
    this.mInitialMotionX = 0.0F;
    Object localObject = this.mVelocityTracker;
    if (localObject == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    } else {
      ((VelocityTracker)localObject).clear();
    }
    long l = SystemClock.uptimeMillis();
    localObject = MotionEvent.obtain(l, l, 0, 0.0F, 0.0F, 0);
    this.mVelocityTracker.addMovement((MotionEvent)localObject);
    ((MotionEvent)localObject).recycle();
    this.mFakeDragBeginTime = l;
    return true;
  }
  
  protected boolean canScroll(View paramView, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    boolean bool1 = paramView instanceof ViewGroup;
    boolean bool2 = true;
    if (bool1)
    {
      ViewGroup localViewGroup = (ViewGroup)paramView;
      int i = paramView.getScrollX();
      int j = paramView.getScrollY();
      for (int k = localViewGroup.getChildCount() - 1; k >= 0; k--)
      {
        View localView = localViewGroup.getChildAt(k);
        int m = paramInt2 + i;
        if ((m >= localView.getLeft()) && (m < localView.getRight()))
        {
          int n = paramInt3 + j;
          if ((n >= localView.getTop()) && (n < localView.getBottom()) && (canScroll(localView, true, paramInt1, m - localView.getLeft(), n - localView.getTop()))) {
            return true;
          }
        }
      }
    }
    if ((paramBoolean) && (paramView.canScrollHorizontally(-paramInt1))) {
      paramBoolean = bool2;
    } else {
      paramBoolean = false;
    }
    return paramBoolean;
  }
  
  public boolean canScrollHorizontally(int paramInt)
  {
    PagerAdapter localPagerAdapter = this.mAdapter;
    boolean bool1 = false;
    boolean bool2 = false;
    if (localPagerAdapter == null) {
      return false;
    }
    int i = getClientWidth();
    int j = getScrollX();
    if (paramInt < 0)
    {
      if (j > (int)(i * this.mFirstOffset)) {
        bool2 = true;
      }
      return bool2;
    }
    if (paramInt > 0)
    {
      bool2 = bool1;
      if (j < (int)(i * this.mLastOffset)) {
        bool2 = true;
      }
      return bool2;
    }
    return false;
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    boolean bool;
    if (((paramLayoutParams instanceof LayoutParams)) && (super.checkLayoutParams(paramLayoutParams))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void clearOnPageChangeListeners()
  {
    List localList = this.mOnPageChangeListeners;
    if (localList != null) {
      localList.clear();
    }
  }
  
  public void computeScroll()
  {
    this.mIsScrollStarted = true;
    if ((!this.mScroller.isFinished()) && (this.mScroller.computeScrollOffset()))
    {
      int i = getScrollX();
      int j = getScrollY();
      int k = this.mScroller.getCurrX();
      int m = this.mScroller.getCurrY();
      if ((i != k) || (j != m))
      {
        scrollTo(k, m);
        if (!pageScrolled(k))
        {
          this.mScroller.abortAnimation();
          scrollTo(0, m);
        }
      }
      ViewCompat.postInvalidateOnAnimation(this);
      return;
    }
    completeScroll(true);
  }
  
  void dataSetChanged()
  {
    int i = this.mAdapter.getCount();
    this.mExpectedAdapterCount = i;
    int j;
    if ((this.mItems.size() < this.mOffscreenPageLimit * 2 + 1) && (this.mItems.size() < i)) {
      j = 1;
    } else {
      j = 0;
    }
    int k = this.mCurItem;
    int m = 0;
    int n = 0;
    Object localObject;
    while (m < this.mItems.size())
    {
      localObject = (ItemInfo)this.mItems.get(m);
      int i1 = this.mAdapter.getItemPosition(((ItemInfo)localObject).object);
      int i2;
      int i3;
      int i4;
      if (i1 == -1)
      {
        i2 = m;
        i3 = n;
        i4 = k;
      }
      else if (i1 == -2)
      {
        this.mItems.remove(m);
        i2 = m - 1;
        j = n;
        if (n == 0)
        {
          this.mAdapter.startUpdate(this);
          j = 1;
        }
        this.mAdapter.destroyItem(this, ((ItemInfo)localObject).position, ((ItemInfo)localObject).object);
        if (this.mCurItem == ((ItemInfo)localObject).position)
        {
          i4 = Math.max(0, Math.min(this.mCurItem, i - 1));
          k = 1;
          i3 = j;
          j = k;
        }
        else
        {
          n = 1;
          i3 = j;
          j = n;
          i4 = k;
        }
      }
      else
      {
        i2 = m;
        i3 = n;
        i4 = k;
        if (((ItemInfo)localObject).position != i1)
        {
          if (((ItemInfo)localObject).position == this.mCurItem) {
            k = i1;
          }
          ((ItemInfo)localObject).position = i1;
          j = 1;
          i4 = k;
          i3 = n;
          i2 = m;
        }
      }
      m = i2 + 1;
      n = i3;
      k = i4;
    }
    if (n != 0) {
      this.mAdapter.finishUpdate(this);
    }
    Collections.sort(this.mItems, COMPARATOR);
    if (j != 0)
    {
      n = getChildCount();
      for (j = 0; j < n; j++)
      {
        localObject = (LayoutParams)getChildAt(j).getLayoutParams();
        if (!((LayoutParams)localObject).isDecor) {
          ((LayoutParams)localObject).widthFactor = 0.0F;
        }
      }
      setCurrentItemInternal(k, false, true);
      requestLayout();
    }
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    boolean bool;
    if ((!super.dispatchKeyEvent(paramKeyEvent)) && (!executeKeyEvent(paramKeyEvent))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    if (paramAccessibilityEvent.getEventType() == 4096) {
      return super.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent);
    }
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = getChildAt(j);
      if (localView.getVisibility() == 0)
      {
        ItemInfo localItemInfo = infoForChild(localView);
        if ((localItemInfo != null) && (localItemInfo.position == this.mCurItem) && (localView.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent))) {
          return true;
        }
      }
    }
    return false;
  }
  
  float distanceInfluenceForSnapDuration(float paramFloat)
  {
    return (float)Math.sin((paramFloat - 0.5F) * 0.47123894F);
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    int i = getOverScrollMode();
    int j = 0;
    int k = 0;
    if (i != 0) {
      if (i == 1)
      {
        PagerAdapter localPagerAdapter = this.mAdapter;
        if ((localPagerAdapter != null) && (localPagerAdapter.getCount() > 1)) {}
      }
      else
      {
        this.mLeftEdge.finish();
        this.mRightEdge.finish();
        break label256;
      }
    }
    int m;
    if (!this.mLeftEdge.isFinished())
    {
      j = paramCanvas.save();
      k = getHeight() - getPaddingTop() - getPaddingBottom();
      i = getWidth();
      paramCanvas.rotate(270.0F);
      paramCanvas.translate(-k + getPaddingTop(), this.mFirstOffset * i);
      this.mLeftEdge.setSize(k, i);
      m = false | this.mLeftEdge.draw(paramCanvas);
      paramCanvas.restoreToCount(j);
    }
    j = m;
    boolean bool;
    if (!this.mRightEdge.isFinished())
    {
      i = paramCanvas.save();
      int n = getWidth();
      j = getHeight();
      int i1 = getPaddingTop();
      int i2 = getPaddingBottom();
      paramCanvas.rotate(90.0F);
      paramCanvas.translate(-getPaddingTop(), -(this.mLastOffset + 1.0F) * n);
      this.mRightEdge.setSize(j - i1 - i2, n);
      bool = m | this.mRightEdge.draw(paramCanvas);
      paramCanvas.restoreToCount(i);
    }
    label256:
    if (bool) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    Drawable localDrawable = this.mMarginDrawable;
    if ((localDrawable != null) && (localDrawable.isStateful())) {
      localDrawable.setState(getDrawableState());
    }
  }
  
  public void endFakeDrag()
  {
    if (this.mFakeDragging)
    {
      if (this.mAdapter != null)
      {
        Object localObject = this.mVelocityTracker;
        ((VelocityTracker)localObject).computeCurrentVelocity(1000, this.mMaximumVelocity);
        int i = (int)((VelocityTracker)localObject).getXVelocity(this.mActivePointerId);
        this.mPopulatePending = true;
        int j = getClientWidth();
        int k = getScrollX();
        localObject = infoForCurrentScrollPosition();
        setCurrentItemInternal(determineTargetPage(((ItemInfo)localObject).position, (k / j - ((ItemInfo)localObject).offset) / ((ItemInfo)localObject).widthFactor, i, (int)(this.mLastMotionX - this.mInitialMotionX)), true, true, i);
      }
      endDrag();
      this.mFakeDragging = false;
      return;
    }
    throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
  }
  
  public boolean executeKeyEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0)
    {
      int i = paramKeyEvent.getKeyCode();
      if (i != 61)
      {
        switch (i)
        {
        default: 
          break;
        case 22: 
          if (paramKeyEvent.hasModifiers(2)) {
            bool = pageRight();
          } else {
            bool = arrowScroll(66);
          }
          break;
        case 21: 
          if (paramKeyEvent.hasModifiers(2)) {
            bool = pageLeft();
          } else {
            bool = arrowScroll(17);
          }
          break;
        }
      }
      else
      {
        if (paramKeyEvent.hasNoModifiers()) {
          return arrowScroll(2);
        }
        if (paramKeyEvent.hasModifiers(1)) {
          return arrowScroll(1);
        }
      }
    }
    boolean bool = false;
    return bool;
  }
  
  public void fakeDragBy(float paramFloat)
  {
    if (this.mFakeDragging)
    {
      if (this.mAdapter == null) {
        return;
      }
      this.mLastMotionX += paramFloat;
      float f1 = getScrollX() - paramFloat;
      float f2 = getClientWidth();
      paramFloat = this.mFirstOffset * f2;
      float f3 = this.mLastOffset * f2;
      Object localObject1 = (ItemInfo)this.mItems.get(0);
      Object localObject2 = this.mItems;
      localObject2 = (ItemInfo)((ArrayList)localObject2).get(((ArrayList)localObject2).size() - 1);
      if (((ItemInfo)localObject1).position != 0) {
        paramFloat = ((ItemInfo)localObject1).offset * f2;
      }
      if (((ItemInfo)localObject2).position != this.mAdapter.getCount() - 1) {
        f3 = ((ItemInfo)localObject2).offset * f2;
      }
      if (f1 >= paramFloat)
      {
        paramFloat = f1;
        if (f1 > f3) {
          paramFloat = f3;
        }
      }
      f3 = this.mLastMotionX;
      int i = (int)paramFloat;
      this.mLastMotionX = (f3 + (paramFloat - i));
      scrollTo(i, getScrollY());
      pageScrolled(i);
      long l = SystemClock.uptimeMillis();
      localObject1 = MotionEvent.obtain(this.mFakeDragBeginTime, l, 2, this.mLastMotionX, 0.0F, 0);
      this.mVelocityTracker.addMovement((MotionEvent)localObject1);
      ((MotionEvent)localObject1).recycle();
      return;
    }
    throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams();
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return generateDefaultLayoutParams();
  }
  
  public PagerAdapter getAdapter()
  {
    return this.mAdapter;
  }
  
  protected int getChildDrawingOrder(int paramInt1, int paramInt2)
  {
    int i = paramInt2;
    if (this.mDrawingOrder == 2) {
      i = paramInt1 - 1 - paramInt2;
    }
    return ((LayoutParams)((View)this.mDrawingOrderedChildren.get(i)).getLayoutParams()).childIndex;
  }
  
  public int getCurrentItem()
  {
    return this.mCurItem;
  }
  
  public int getOffscreenPageLimit()
  {
    return this.mOffscreenPageLimit;
  }
  
  public int getPageMargin()
  {
    return this.mPageMargin;
  }
  
  ItemInfo infoForAnyChild(View paramView)
  {
    for (;;)
    {
      ViewParent localViewParent = paramView.getParent();
      if (localViewParent == this) {
        break label34;
      }
      if ((localViewParent == null) || (!(localViewParent instanceof View))) {
        break;
      }
      paramView = (View)localViewParent;
    }
    return null;
    label34:
    return infoForChild(paramView);
  }
  
  ItemInfo infoForChild(View paramView)
  {
    for (int i = 0; i < this.mItems.size(); i++)
    {
      ItemInfo localItemInfo = (ItemInfo)this.mItems.get(i);
      if (this.mAdapter.isViewFromObject(paramView, localItemInfo.object)) {
        return localItemInfo;
      }
    }
    return null;
  }
  
  ItemInfo infoForPosition(int paramInt)
  {
    for (int i = 0; i < this.mItems.size(); i++)
    {
      ItemInfo localItemInfo = (ItemInfo)this.mItems.get(i);
      if (localItemInfo.position == paramInt) {
        return localItemInfo;
      }
    }
    return null;
  }
  
  void initViewPager()
  {
    setWillNotDraw(false);
    setDescendantFocusability(262144);
    setFocusable(true);
    Context localContext = getContext();
    this.mScroller = new Scroller(localContext, sInterpolator);
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(localContext);
    float f = localContext.getResources().getDisplayMetrics().density;
    this.mTouchSlop = localViewConfiguration.getScaledPagingTouchSlop();
    this.mMinimumVelocity = ((int)(400.0F * f));
    this.mMaximumVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();
    this.mLeftEdge = new EdgeEffect(localContext);
    this.mRightEdge = new EdgeEffect(localContext);
    this.mFlingDistance = ((int)(25.0F * f));
    this.mCloseEnough = ((int)(2.0F * f));
    this.mDefaultGutterSize = ((int)(f * 16.0F));
    ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate());
    if (ViewCompat.getImportantForAccessibility(this) == 0) {
      ViewCompat.setImportantForAccessibility(this, 1);
    }
    ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener()
    {
      private final Rect mTempRect = new Rect();
      
      public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
      {
        paramAnonymousView = ViewCompat.onApplyWindowInsets(paramAnonymousView, paramAnonymousWindowInsetsCompat);
        if (paramAnonymousView.isConsumed()) {
          return paramAnonymousView;
        }
        paramAnonymousWindowInsetsCompat = this.mTempRect;
        paramAnonymousWindowInsetsCompat.left = paramAnonymousView.getSystemWindowInsetLeft();
        paramAnonymousWindowInsetsCompat.top = paramAnonymousView.getSystemWindowInsetTop();
        paramAnonymousWindowInsetsCompat.right = paramAnonymousView.getSystemWindowInsetRight();
        paramAnonymousWindowInsetsCompat.bottom = paramAnonymousView.getSystemWindowInsetBottom();
        int i = 0;
        int j = ViewPager.this.getChildCount();
        while (i < j)
        {
          WindowInsetsCompat localWindowInsetsCompat = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(i), paramAnonymousView);
          paramAnonymousWindowInsetsCompat.left = Math.min(localWindowInsetsCompat.getSystemWindowInsetLeft(), paramAnonymousWindowInsetsCompat.left);
          paramAnonymousWindowInsetsCompat.top = Math.min(localWindowInsetsCompat.getSystemWindowInsetTop(), paramAnonymousWindowInsetsCompat.top);
          paramAnonymousWindowInsetsCompat.right = Math.min(localWindowInsetsCompat.getSystemWindowInsetRight(), paramAnonymousWindowInsetsCompat.right);
          paramAnonymousWindowInsetsCompat.bottom = Math.min(localWindowInsetsCompat.getSystemWindowInsetBottom(), paramAnonymousWindowInsetsCompat.bottom);
          i++;
        }
        return paramAnonymousView.replaceSystemWindowInsets(paramAnonymousWindowInsetsCompat.left, paramAnonymousWindowInsetsCompat.top, paramAnonymousWindowInsetsCompat.right, paramAnonymousWindowInsetsCompat.bottom);
      }
    });
  }
  
  public boolean isFakeDragging()
  {
    return this.mFakeDragging;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }
  
  protected void onDetachedFromWindow()
  {
    removeCallbacks(this.mEndScrollRunnable);
    Scroller localScroller = this.mScroller;
    if ((localScroller != null) && (!localScroller.isFinished())) {
      this.mScroller.abortAnimation();
    }
    super.onDetachedFromWindow();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((this.mPageMargin > 0) && (this.mMarginDrawable != null) && (this.mItems.size() > 0) && (this.mAdapter != null))
    {
      int i = getScrollX();
      int j = getWidth();
      float f1 = this.mPageMargin;
      float f2 = j;
      float f3 = f1 / f2;
      Object localObject = this.mItems;
      int k = 0;
      localObject = (ItemInfo)((ArrayList)localObject).get(0);
      f1 = ((ItemInfo)localObject).offset;
      int m = this.mItems.size();
      int n = ((ItemInfo)localObject).position;
      int i1 = ((ItemInfo)this.mItems.get(m - 1)).position;
      while (n < i1)
      {
        while ((n > ((ItemInfo)localObject).position) && (k < m))
        {
          localObject = this.mItems;
          k++;
          localObject = (ItemInfo)((ArrayList)localObject).get(k);
        }
        float f4;
        if (n == ((ItemInfo)localObject).position)
        {
          f4 = (((ItemInfo)localObject).offset + ((ItemInfo)localObject).widthFactor) * f2;
          f1 = ((ItemInfo)localObject).offset + ((ItemInfo)localObject).widthFactor + f3;
        }
        else
        {
          float f5 = this.mAdapter.getPageWidth(n);
          f4 = f1 + (f5 + f3);
          f5 = (f1 + f5) * f2;
          f1 = f4;
          f4 = f5;
        }
        if (this.mPageMargin + f4 > i)
        {
          this.mMarginDrawable.setBounds(Math.round(f4), this.mTopPageBounds, Math.round(this.mPageMargin + f4), this.mBottomPageBounds);
          this.mMarginDrawable.draw(paramCanvas);
        }
        if (f4 > i + j) {
          break;
        }
        n++;
      }
    }
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction() & 0xFF;
    if ((i != 3) && (i != 1))
    {
      if (i != 0)
      {
        if (this.mIsBeingDragged) {
          return true;
        }
        if (this.mIsUnableToDrag) {
          return false;
        }
      }
      float f5;
      if (i != 0)
      {
        if (i != 2)
        {
          if (i == 6) {
            onSecondaryPointerUp(paramMotionEvent);
          }
        }
        else
        {
          i = this.mActivePointerId;
          if (i != -1)
          {
            i = paramMotionEvent.findPointerIndex(i);
            float f1 = paramMotionEvent.getX(i);
            float f2 = f1 - this.mLastMotionX;
            float f3 = Math.abs(f2);
            float f4 = paramMotionEvent.getY(i);
            f5 = Math.abs(f4 - this.mInitialMotionY);
            if ((f2 != 0.0F) && (!isGutterDrag(this.mLastMotionX, f2)) && (canScroll(this, false, (int)f2, (int)f1, (int)f4)))
            {
              this.mLastMotionX = f1;
              this.mLastMotionY = f4;
              this.mIsUnableToDrag = true;
              return false;
            }
            if ((f3 > this.mTouchSlop) && (f3 * 0.5F > f5))
            {
              this.mIsBeingDragged = true;
              requestParentDisallowInterceptTouchEvent(true);
              setScrollState(1);
              if (f2 > 0.0F) {
                f5 = this.mInitialMotionX + this.mTouchSlop;
              } else {
                f5 = this.mInitialMotionX - this.mTouchSlop;
              }
              this.mLastMotionX = f5;
              this.mLastMotionY = f4;
              setScrollingCacheEnabled(true);
            }
            else if (f5 > this.mTouchSlop)
            {
              this.mIsUnableToDrag = true;
            }
            if ((this.mIsBeingDragged) && (performDrag(f1))) {
              ViewCompat.postInvalidateOnAnimation(this);
            }
          }
        }
      }
      else
      {
        f5 = paramMotionEvent.getX();
        this.mInitialMotionX = f5;
        this.mLastMotionX = f5;
        f5 = paramMotionEvent.getY();
        this.mInitialMotionY = f5;
        this.mLastMotionY = f5;
        this.mActivePointerId = paramMotionEvent.getPointerId(0);
        this.mIsUnableToDrag = false;
        this.mIsScrollStarted = true;
        this.mScroller.computeScrollOffset();
        if ((this.mScrollState == 2) && (Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough))
        {
          this.mScroller.abortAnimation();
          this.mPopulatePending = false;
          populate();
          this.mIsBeingDragged = true;
          requestParentDisallowInterceptTouchEvent(true);
          setScrollState(1);
        }
        else
        {
          completeScroll(false);
          this.mIsBeingDragged = false;
        }
      }
      if (this.mVelocityTracker == null) {
        this.mVelocityTracker = VelocityTracker.obtain();
      }
      this.mVelocityTracker.addMovement(paramMotionEvent);
      return this.mIsBeingDragged;
    }
    resetTouch();
    return false;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getChildCount();
    int j = paramInt3 - paramInt1;
    int k = paramInt4 - paramInt2;
    paramInt2 = getPaddingLeft();
    paramInt1 = getPaddingTop();
    int m = getPaddingRight();
    paramInt4 = getPaddingBottom();
    int n = getScrollX();
    int i1 = 0;
    int i2 = 0;
    View localView;
    int i3;
    Object localObject;
    while (i2 < i)
    {
      localView = getChildAt(i2);
      i3 = paramInt2;
      int i4 = m;
      int i5 = paramInt1;
      int i6 = paramInt4;
      paramInt3 = i1;
      if (localView.getVisibility() != 8)
      {
        localObject = (LayoutParams)localView.getLayoutParams();
        i3 = paramInt2;
        i4 = m;
        i5 = paramInt1;
        i6 = paramInt4;
        paramInt3 = i1;
        if (((LayoutParams)localObject).isDecor)
        {
          paramInt3 = ((LayoutParams)localObject).gravity & 0x7;
          i6 = ((LayoutParams)localObject).gravity & 0x70;
          if (paramInt3 != 1)
          {
            if (paramInt3 != 3)
            {
              if (paramInt3 != 5)
              {
                paramInt3 = paramInt2;
                i3 = paramInt2;
              }
              else
              {
                paramInt3 = j - m - localView.getMeasuredWidth();
                m += localView.getMeasuredWidth();
                i3 = paramInt2;
              }
            }
            else
            {
              i3 = localView.getMeasuredWidth();
              paramInt3 = paramInt2;
              i3 += paramInt2;
            }
          }
          else
          {
            paramInt3 = Math.max((j - localView.getMeasuredWidth()) / 2, paramInt2);
            i3 = paramInt2;
          }
          if (i6 != 16)
          {
            if (i6 != 48)
            {
              if (i6 != 80)
              {
                paramInt2 = paramInt1;
              }
              else
              {
                paramInt2 = k - paramInt4 - localView.getMeasuredHeight();
                paramInt4 += localView.getMeasuredHeight();
              }
            }
            else
            {
              i6 = localView.getMeasuredHeight();
              paramInt2 = paramInt1;
              paramInt1 = i6 + paramInt1;
            }
          }
          else {
            paramInt2 = Math.max((k - localView.getMeasuredHeight()) / 2, paramInt1);
          }
          paramInt3 += n;
          localView.layout(paramInt3, paramInt2, localView.getMeasuredWidth() + paramInt3, paramInt2 + localView.getMeasuredHeight());
          paramInt3 = i1 + 1;
          i6 = paramInt4;
          i5 = paramInt1;
          i4 = m;
        }
      }
      i2++;
      paramInt2 = i3;
      m = i4;
      paramInt1 = i5;
      paramInt4 = i6;
      i1 = paramInt3;
    }
    for (paramInt3 = 0; paramInt3 < i; paramInt3++)
    {
      localView = getChildAt(paramInt3);
      if (localView.getVisibility() != 8)
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (!localLayoutParams.isDecor)
        {
          localObject = infoForChild(localView);
          if (localObject != null)
          {
            float f = j - paramInt2 - m;
            i3 = (int)(((ItemInfo)localObject).offset * f) + paramInt2;
            if (localLayoutParams.needsMeasure)
            {
              localLayoutParams.needsMeasure = false;
              localView.measure(View.MeasureSpec.makeMeasureSpec((int)(f * localLayoutParams.widthFactor), 1073741824), View.MeasureSpec.makeMeasureSpec(k - paramInt1 - paramInt4, 1073741824));
            }
            localView.layout(i3, paramInt1, localView.getMeasuredWidth() + i3, localView.getMeasuredHeight() + paramInt1);
          }
        }
      }
    }
    this.mTopPageBounds = paramInt1;
    this.mBottomPageBounds = (k - paramInt4);
    this.mDecorChildCount = i1;
    if (this.mFirstLayout) {
      scrollToItem(this.mCurItem, false, 0, false);
    }
    this.mFirstLayout = false;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension(getDefaultSize(0, paramInt1), getDefaultSize(0, paramInt2));
    int i = getMeasuredWidth();
    this.mGutterSize = Math.min(i / 10, this.mDefaultGutterSize);
    int j = getPaddingLeft();
    paramInt1 = getPaddingRight();
    int k = getMeasuredHeight();
    paramInt2 = getPaddingTop();
    int m = getPaddingBottom();
    int n = getChildCount();
    paramInt2 = k - paramInt2 - m;
    paramInt1 = i - j - paramInt1;
    k = 0;
    Object localObject1;
    Object localObject2;
    for (;;)
    {
      m = 1;
      int i1 = 1073741824;
      if (k >= n) {
        break;
      }
      localObject1 = getChildAt(k);
      i = paramInt1;
      j = paramInt2;
      if (((View)localObject1).getVisibility() != 8)
      {
        localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
        i = paramInt1;
        j = paramInt2;
        if (localObject2 != null)
        {
          i = paramInt1;
          j = paramInt2;
          if (((LayoutParams)localObject2).isDecor)
          {
            i = ((LayoutParams)localObject2).gravity & 0x7;
            j = ((LayoutParams)localObject2).gravity & 0x70;
            int i2;
            if ((j != 48) && (j != 80)) {
              i2 = 0;
            } else {
              i2 = 1;
            }
            int i3 = m;
            if (i != 3) {
              if (i == 5) {
                i3 = m;
              } else {
                i3 = 0;
              }
            }
            j = Integer.MIN_VALUE;
            if (i2 != 0)
            {
              j = 1073741824;
              i = Integer.MIN_VALUE;
            }
            else if (i3 != 0)
            {
              i = 1073741824;
            }
            else
            {
              i = Integer.MIN_VALUE;
            }
            if (((LayoutParams)localObject2).width != -2)
            {
              if (((LayoutParams)localObject2).width != -1)
              {
                m = ((LayoutParams)localObject2).width;
                j = 1073741824;
              }
              else
              {
                m = paramInt1;
                j = 1073741824;
              }
            }
            else {
              m = paramInt1;
            }
            if (((LayoutParams)localObject2).height != -2)
            {
              if (((LayoutParams)localObject2).height != -1) {
                i = ((LayoutParams)localObject2).height;
              } else {
                i = paramInt2;
              }
            }
            else
            {
              int i4 = paramInt2;
              i1 = i;
              i = i4;
            }
            ((View)localObject1).measure(View.MeasureSpec.makeMeasureSpec(m, j), View.MeasureSpec.makeMeasureSpec(i, i1));
            if (i2 != 0)
            {
              j = paramInt2 - ((View)localObject1).getMeasuredHeight();
              i = paramInt1;
            }
            else
            {
              i = paramInt1;
              j = paramInt2;
              if (i3 != 0)
              {
                i = paramInt1 - ((View)localObject1).getMeasuredWidth();
                j = paramInt2;
              }
            }
          }
        }
      }
      k++;
      paramInt1 = i;
      paramInt2 = j;
    }
    this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(paramInt1, 1073741824);
    this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(paramInt2, 1073741824);
    this.mInLayout = true;
    populate();
    paramInt2 = 0;
    this.mInLayout = false;
    i = getChildCount();
    while (paramInt2 < i)
    {
      localObject2 = getChildAt(paramInt2);
      if (((View)localObject2).getVisibility() != 8)
      {
        localObject1 = (LayoutParams)((View)localObject2).getLayoutParams();
        if ((localObject1 == null) || (!((LayoutParams)localObject1).isDecor)) {
          ((View)localObject2).measure(View.MeasureSpec.makeMeasureSpec((int)(paramInt1 * ((LayoutParams)localObject1).widthFactor), 1073741824), this.mChildHeightMeasureSpec);
        }
      }
      paramInt2++;
    }
  }
  
  protected void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    int i = this.mDecorChildCount;
    int j = 0;
    Object localObject;
    if (i > 0)
    {
      int k = getScrollX();
      i = getPaddingLeft();
      int m = getPaddingRight();
      int n = getWidth();
      int i1 = getChildCount();
      for (int i2 = 0; i2 < i1; i2++)
      {
        View localView = getChildAt(i2);
        localObject = (LayoutParams)localView.getLayoutParams();
        if (((LayoutParams)localObject).isDecor)
        {
          int i3 = ((LayoutParams)localObject).gravity & 0x7;
          if (i3 != 1)
          {
            int i4;
            if (i3 != 3)
            {
              if (i3 != 5)
              {
                i4 = i;
                i3 = i;
                i = i4;
              }
              else
              {
                i3 = localView.getMeasuredWidth();
                i4 = m + localView.getMeasuredWidth();
                i3 = n - m - i3;
                m = i4;
              }
            }
            else
            {
              i4 = localView.getWidth() + i;
              i3 = i;
              i = i4;
            }
          }
          else
          {
            i3 = Math.max((n - localView.getMeasuredWidth()) / 2, i);
          }
          i3 = i3 + k - localView.getLeft();
          if (i3 != 0) {
            localView.offsetLeftAndRight(i3);
          }
        }
      }
    }
    dispatchOnPageScrolled(paramInt1, paramFloat, paramInt2);
    if (this.mPageTransformer != null)
    {
      paramInt2 = getScrollX();
      i = getChildCount();
      for (paramInt1 = j; paramInt1 < i; paramInt1++)
      {
        localObject = getChildAt(paramInt1);
        if (!((LayoutParams)((View)localObject).getLayoutParams()).isDecor)
        {
          paramFloat = (((View)localObject).getLeft() - paramInt2) / getClientWidth();
          this.mPageTransformer.transformPage((View)localObject, paramFloat);
        }
      }
    }
    this.mCalledSuper = true;
  }
  
  protected boolean onRequestFocusInDescendants(int paramInt, Rect paramRect)
  {
    int i = getChildCount();
    int j = -1;
    int k;
    if ((paramInt & 0x2) != 0)
    {
      j = i;
      i = 0;
      k = 1;
    }
    else
    {
      i--;
      k = -1;
    }
    while (i != j)
    {
      View localView = getChildAt(i);
      if (localView.getVisibility() == 0)
      {
        ItemInfo localItemInfo = infoForChild(localView);
        if ((localItemInfo != null) && (localItemInfo.position == this.mCurItem) && (localView.requestFocus(paramInt, paramRect))) {
          return true;
        }
      }
      i += k;
    }
    return false;
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    paramParcelable = this.mAdapter;
    if (paramParcelable != null)
    {
      paramParcelable.restoreState(localSavedState.adapterState, localSavedState.loader);
      setCurrentItemInternal(localSavedState.position, false, true);
    }
    else
    {
      this.mRestoredCurItem = localSavedState.position;
      this.mRestoredAdapterState = localSavedState.adapterState;
      this.mRestoredClassLoader = localSavedState.loader;
    }
  }
  
  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.position = this.mCurItem;
    PagerAdapter localPagerAdapter = this.mAdapter;
    if (localPagerAdapter != null) {
      localSavedState.adapterState = localPagerAdapter.saveState();
    }
    return localSavedState;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 != paramInt3)
    {
      paramInt2 = this.mPageMargin;
      recomputeScrollPosition(paramInt1, paramInt3, paramInt2, paramInt2);
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mFakeDragging) {
      return true;
    }
    int i = paramMotionEvent.getAction();
    boolean bool = false;
    if ((i == 0) && (paramMotionEvent.getEdgeFlags() != 0)) {
      return false;
    }
    Object localObject = this.mAdapter;
    if ((localObject != null) && (((PagerAdapter)localObject).getCount() != 0))
    {
      if (this.mVelocityTracker == null) {
        this.mVelocityTracker = VelocityTracker.obtain();
      }
      this.mVelocityTracker.addMovement(paramMotionEvent);
      float f1;
      float f3;
      switch (paramMotionEvent.getAction() & 0xFF)
      {
      case 4: 
      default: 
        break;
      case 6: 
        onSecondaryPointerUp(paramMotionEvent);
        this.mLastMotionX = paramMotionEvent.getX(paramMotionEvent.findPointerIndex(this.mActivePointerId));
        break;
      case 5: 
        i = paramMotionEvent.getActionIndex();
        this.mLastMotionX = paramMotionEvent.getX(i);
        this.mActivePointerId = paramMotionEvent.getPointerId(i);
        break;
      case 3: 
        if (this.mIsBeingDragged)
        {
          scrollToItem(this.mCurItem, true, 0, false);
          bool = resetTouch();
        }
        break;
      case 2: 
        if (!this.mIsBeingDragged)
        {
          i = paramMotionEvent.findPointerIndex(this.mActivePointerId);
          if (i == -1)
          {
            bool = resetTouch();
            break;
          }
          f1 = paramMotionEvent.getX(i);
          float f2 = Math.abs(f1 - this.mLastMotionX);
          f3 = paramMotionEvent.getY(i);
          float f4 = Math.abs(f3 - this.mLastMotionY);
          if ((f2 > this.mTouchSlop) && (f2 > f4))
          {
            this.mIsBeingDragged = true;
            requestParentDisallowInterceptTouchEvent(true);
            f2 = this.mInitialMotionX;
            if (f1 - f2 > 0.0F) {
              f1 = f2 + this.mTouchSlop;
            } else {
              f1 = f2 - this.mTouchSlop;
            }
            this.mLastMotionX = f1;
            this.mLastMotionY = f3;
            setScrollState(1);
            setScrollingCacheEnabled(true);
            localObject = getParent();
            if (localObject != null) {
              ((ViewParent)localObject).requestDisallowInterceptTouchEvent(true);
            }
          }
        }
        if (this.mIsBeingDragged) {
          bool = false | performDrag(paramMotionEvent.getX(paramMotionEvent.findPointerIndex(this.mActivePointerId)));
        }
        break;
      case 1: 
        if (this.mIsBeingDragged)
        {
          localObject = this.mVelocityTracker;
          ((VelocityTracker)localObject).computeCurrentVelocity(1000, this.mMaximumVelocity);
          i = (int)((VelocityTracker)localObject).getXVelocity(this.mActivePointerId);
          this.mPopulatePending = true;
          int j = getClientWidth();
          int k = getScrollX();
          localObject = infoForCurrentScrollPosition();
          f3 = this.mPageMargin;
          f1 = j;
          f3 /= f1;
          setCurrentItemInternal(determineTargetPage(((ItemInfo)localObject).position, (k / f1 - ((ItemInfo)localObject).offset) / (((ItemInfo)localObject).widthFactor + f3), i, (int)(paramMotionEvent.getX(paramMotionEvent.findPointerIndex(this.mActivePointerId)) - this.mInitialMotionX)), true, true, i);
          bool = resetTouch();
        }
        break;
      case 0: 
        this.mScroller.abortAnimation();
        this.mPopulatePending = false;
        populate();
        f1 = paramMotionEvent.getX();
        this.mInitialMotionX = f1;
        this.mLastMotionX = f1;
        f1 = paramMotionEvent.getY();
        this.mInitialMotionY = f1;
        this.mLastMotionY = f1;
        this.mActivePointerId = paramMotionEvent.getPointerId(0);
      }
      if (bool) {
        ViewCompat.postInvalidateOnAnimation(this);
      }
      return true;
    }
    return false;
  }
  
  boolean pageLeft()
  {
    int i = this.mCurItem;
    if (i > 0)
    {
      setCurrentItem(i - 1, true);
      return true;
    }
    return false;
  }
  
  boolean pageRight()
  {
    PagerAdapter localPagerAdapter = this.mAdapter;
    if ((localPagerAdapter != null) && (this.mCurItem < localPagerAdapter.getCount() - 1))
    {
      setCurrentItem(this.mCurItem + 1, true);
      return true;
    }
    return false;
  }
  
  void populate()
  {
    populate(this.mCurItem);
  }
  
  void populate(int paramInt)
  {
    int i = this.mCurItem;
    if (i != paramInt)
    {
      localObject1 = infoForPosition(i);
      this.mCurItem = paramInt;
    }
    else
    {
      localObject1 = null;
    }
    if (this.mAdapter == null)
    {
      sortChildDrawingOrder();
      return;
    }
    if (this.mPopulatePending)
    {
      sortChildDrawingOrder();
      return;
    }
    if (getWindowToken() == null) {
      return;
    }
    this.mAdapter.startUpdate(this);
    paramInt = this.mOffscreenPageLimit;
    int j = Math.max(0, this.mCurItem - paramInt);
    int k = this.mAdapter.getCount();
    int m = Math.min(k - 1, this.mCurItem + paramInt);
    Object localObject2;
    if (k == this.mExpectedAdapterCount)
    {
      for (paramInt = 0; paramInt < this.mItems.size(); paramInt++)
      {
        localObject2 = (ItemInfo)this.mItems.get(paramInt);
        if (((ItemInfo)localObject2).position >= this.mCurItem)
        {
          if (((ItemInfo)localObject2).position != this.mCurItem) {
            break;
          }
          break label178;
        }
      }
      localObject2 = null;
      label178:
      Object localObject3 = localObject2;
      if (localObject2 == null)
      {
        localObject3 = localObject2;
        if (k > 0) {
          localObject3 = addNewItem(this.mCurItem, paramInt);
        }
      }
      if (localObject3 != null)
      {
        int n = paramInt - 1;
        if (n >= 0) {
          localObject2 = (ItemInfo)this.mItems.get(n);
        } else {
          localObject2 = null;
        }
        int i1 = getClientWidth();
        float f1;
        if (i1 <= 0) {
          f1 = 0.0F;
        } else {
          f1 = 2.0F - ((ItemInfo)localObject3).widthFactor + getPaddingLeft() / i1;
        }
        int i2 = this.mCurItem - 1;
        float f2 = 0.0F;
        Object localObject4 = localObject2;
        int i3 = paramInt;
        float f3;
        while (i2 >= 0)
        {
          if ((f2 >= f1) && (i2 < j))
          {
            if (localObject4 == null) {
              break;
            }
            f3 = f2;
            paramInt = i3;
            localObject2 = localObject4;
            i = n;
            if (i2 == ((ItemInfo)localObject4).position)
            {
              f3 = f2;
              paramInt = i3;
              localObject2 = localObject4;
              i = n;
              if (!((ItemInfo)localObject4).scrolling)
              {
                this.mItems.remove(n);
                this.mAdapter.destroyItem(this, i2, ((ItemInfo)localObject4).object);
                i = n - 1;
                paramInt = i3 - 1;
                if (i >= 0) {
                  localObject2 = (ItemInfo)this.mItems.get(i);
                } else {
                  localObject2 = null;
                }
                f3 = f2;
              }
            }
          }
          else if ((localObject4 != null) && (i2 == ((ItemInfo)localObject4).position))
          {
            f3 = f2 + ((ItemInfo)localObject4).widthFactor;
            i = n - 1;
            if (i >= 0) {
              localObject2 = (ItemInfo)this.mItems.get(i);
            } else {
              localObject2 = null;
            }
            paramInt = i3;
          }
          else
          {
            f3 = f2 + addNewItem(i2, n + 1).widthFactor;
            paramInt = i3 + 1;
            if (n >= 0) {
              localObject2 = (ItemInfo)this.mItems.get(n);
            } else {
              localObject2 = null;
            }
            i = n;
          }
          i2--;
          f2 = f3;
          i3 = paramInt;
          localObject4 = localObject2;
          n = i;
        }
        f2 = ((ItemInfo)localObject3).widthFactor;
        i = i3 + 1;
        if (f2 < 2.0F)
        {
          if (i < this.mItems.size()) {
            localObject2 = (ItemInfo)this.mItems.get(i);
          } else {
            localObject2 = null;
          }
          if (i1 <= 0) {
            f1 = 0.0F;
          } else {
            f1 = getPaddingRight() / i1 + 2.0F;
          }
          paramInt = this.mCurItem;
          localObject4 = localObject2;
          for (;;)
          {
            i2 = paramInt + 1;
            if (i2 >= k) {
              break;
            }
            if ((f2 >= f1) && (i2 > m))
            {
              if (localObject4 == null) {
                break;
              }
              f3 = f2;
              paramInt = i;
              localObject2 = localObject4;
              if (i2 == ((ItemInfo)localObject4).position)
              {
                f3 = f2;
                paramInt = i;
                localObject2 = localObject4;
                if (!((ItemInfo)localObject4).scrolling)
                {
                  this.mItems.remove(i);
                  this.mAdapter.destroyItem(this, i2, ((ItemInfo)localObject4).object);
                  if (i < this.mItems.size())
                  {
                    localObject2 = (ItemInfo)this.mItems.get(i);
                    f3 = f2;
                    paramInt = i;
                  }
                  else
                  {
                    localObject2 = null;
                    f3 = f2;
                    paramInt = i;
                  }
                }
              }
            }
            else if ((localObject4 != null) && (i2 == ((ItemInfo)localObject4).position))
            {
              f3 = f2 + ((ItemInfo)localObject4).widthFactor;
              paramInt = i + 1;
              if (paramInt < this.mItems.size()) {
                localObject2 = (ItemInfo)this.mItems.get(paramInt);
              } else {
                localObject2 = null;
              }
            }
            else
            {
              localObject2 = addNewItem(i2, i);
              paramInt = i + 1;
              f3 = f2 + ((ItemInfo)localObject2).widthFactor;
              if (paramInt < this.mItems.size()) {
                localObject2 = (ItemInfo)this.mItems.get(paramInt);
              } else {
                localObject2 = null;
              }
            }
            f2 = f3;
            i = paramInt;
            localObject4 = localObject2;
            paramInt = i2;
          }
        }
        calculatePageOffsets((ItemInfo)localObject3, i3, (ItemInfo)localObject1);
      }
      localObject1 = this.mAdapter;
      paramInt = this.mCurItem;
      if (localObject3 != null) {
        localObject2 = ((ItemInfo)localObject3).object;
      } else {
        localObject2 = null;
      }
      ((PagerAdapter)localObject1).setPrimaryItem(this, paramInt, localObject2);
      this.mAdapter.finishUpdate(this);
      i = getChildCount();
      for (paramInt = 0; paramInt < i; paramInt++)
      {
        localObject1 = getChildAt(paramInt);
        localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
        ((LayoutParams)localObject2).childIndex = paramInt;
        if ((!((LayoutParams)localObject2).isDecor) && (((LayoutParams)localObject2).widthFactor == 0.0F))
        {
          localObject1 = infoForChild((View)localObject1);
          if (localObject1 != null)
          {
            ((LayoutParams)localObject2).widthFactor = ((ItemInfo)localObject1).widthFactor;
            ((LayoutParams)localObject2).position = ((ItemInfo)localObject1).position;
          }
        }
      }
      sortChildDrawingOrder();
      if (hasFocus())
      {
        localObject2 = findFocus();
        if (localObject2 != null) {
          localObject2 = infoForAnyChild((View)localObject2);
        } else {
          localObject2 = null;
        }
        if ((localObject2 == null) || (((ItemInfo)localObject2).position != this.mCurItem)) {
          for (paramInt = 0; paramInt < getChildCount(); paramInt++)
          {
            localObject1 = getChildAt(paramInt);
            localObject2 = infoForChild((View)localObject1);
            if ((localObject2 != null) && (((ItemInfo)localObject2).position == this.mCurItem) && (((View)localObject1).requestFocus(2))) {
              break;
            }
          }
        }
      }
      return;
    }
    String str;
    try
    {
      localObject2 = getResources().getResourceName(getId());
    }
    catch (Resources.NotFoundException localNotFoundException)
    {
      str = Integer.toHexString(getId());
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: ");
    ((StringBuilder)localObject1).append(this.mExpectedAdapterCount);
    ((StringBuilder)localObject1).append(", found: ");
    ((StringBuilder)localObject1).append(k);
    ((StringBuilder)localObject1).append(" Pager id: ");
    ((StringBuilder)localObject1).append(str);
    ((StringBuilder)localObject1).append(" Pager class: ");
    ((StringBuilder)localObject1).append(getClass());
    ((StringBuilder)localObject1).append(" Problematic adapter: ");
    ((StringBuilder)localObject1).append(this.mAdapter.getClass());
    throw new IllegalStateException(((StringBuilder)localObject1).toString());
  }
  
  public void removeOnAdapterChangeListener(OnAdapterChangeListener paramOnAdapterChangeListener)
  {
    List localList = this.mAdapterChangeListeners;
    if (localList != null) {
      localList.remove(paramOnAdapterChangeListener);
    }
  }
  
  public void removeOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener)
  {
    List localList = this.mOnPageChangeListeners;
    if (localList != null) {
      localList.remove(paramOnPageChangeListener);
    }
  }
  
  public void removeView(View paramView)
  {
    if (this.mInLayout) {
      removeViewInLayout(paramView);
    } else {
      super.removeView(paramView);
    }
  }
  
  public void setAdapter(PagerAdapter paramPagerAdapter)
  {
    Object localObject = this.mAdapter;
    int i = 0;
    int j;
    if (localObject != null)
    {
      ((PagerAdapter)localObject).setViewPagerObserver(null);
      this.mAdapter.startUpdate(this);
      for (j = 0; j < this.mItems.size(); j++)
      {
        localObject = (ItemInfo)this.mItems.get(j);
        this.mAdapter.destroyItem(this, ((ItemInfo)localObject).position, ((ItemInfo)localObject).object);
      }
      this.mAdapter.finishUpdate(this);
      this.mItems.clear();
      removeNonDecorViews();
      this.mCurItem = 0;
      scrollTo(0, 0);
    }
    PagerAdapter localPagerAdapter = this.mAdapter;
    this.mAdapter = paramPagerAdapter;
    this.mExpectedAdapterCount = 0;
    if (this.mAdapter != null)
    {
      if (this.mObserver == null) {
        this.mObserver = new PagerObserver();
      }
      this.mAdapter.setViewPagerObserver(this.mObserver);
      this.mPopulatePending = false;
      boolean bool = this.mFirstLayout;
      this.mFirstLayout = true;
      this.mExpectedAdapterCount = this.mAdapter.getCount();
      if (this.mRestoredCurItem >= 0)
      {
        this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
        setCurrentItemInternal(this.mRestoredCurItem, false, true);
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
      }
      else if (!bool)
      {
        populate();
      }
      else
      {
        requestLayout();
      }
    }
    localObject = this.mAdapterChangeListeners;
    if ((localObject != null) && (!((List)localObject).isEmpty()))
    {
      int k = this.mAdapterChangeListeners.size();
      for (j = i; j < k; j++) {
        ((OnAdapterChangeListener)this.mAdapterChangeListeners.get(j)).onAdapterChanged(this, localPagerAdapter, paramPagerAdapter);
      }
    }
  }
  
  public void setCurrentItem(int paramInt)
  {
    this.mPopulatePending = false;
    setCurrentItemInternal(paramInt, this.mFirstLayout ^ true, false);
  }
  
  public void setCurrentItem(int paramInt, boolean paramBoolean)
  {
    this.mPopulatePending = false;
    setCurrentItemInternal(paramInt, paramBoolean, false);
  }
  
  void setCurrentItemInternal(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    setCurrentItemInternal(paramInt, paramBoolean1, paramBoolean2, 0);
  }
  
  void setCurrentItemInternal(int paramInt1, boolean paramBoolean1, boolean paramBoolean2, int paramInt2)
  {
    PagerAdapter localPagerAdapter = this.mAdapter;
    if ((localPagerAdapter != null) && (localPagerAdapter.getCount() > 0))
    {
      if ((!paramBoolean2) && (this.mCurItem == paramInt1) && (this.mItems.size() != 0))
      {
        setScrollingCacheEnabled(false);
        return;
      }
      paramBoolean2 = true;
      int i;
      if (paramInt1 < 0)
      {
        i = 0;
      }
      else
      {
        i = paramInt1;
        if (paramInt1 >= this.mAdapter.getCount()) {
          i = this.mAdapter.getCount() - 1;
        }
      }
      paramInt1 = this.mOffscreenPageLimit;
      int j = this.mCurItem;
      if ((i > j + paramInt1) || (i < j - paramInt1)) {
        for (paramInt1 = 0; paramInt1 < this.mItems.size(); paramInt1++) {
          ((ItemInfo)this.mItems.get(paramInt1)).scrolling = true;
        }
      }
      if (this.mCurItem == i) {
        paramBoolean2 = false;
      }
      if (this.mFirstLayout)
      {
        this.mCurItem = i;
        if (paramBoolean2) {
          dispatchOnPageSelected(i);
        }
        requestLayout();
      }
      else
      {
        populate(i);
        scrollToItem(i, paramBoolean1, paramInt2, paramBoolean2);
      }
      return;
    }
    setScrollingCacheEnabled(false);
  }
  
  OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener paramOnPageChangeListener)
  {
    OnPageChangeListener localOnPageChangeListener = this.mInternalPageChangeListener;
    this.mInternalPageChangeListener = paramOnPageChangeListener;
    return localOnPageChangeListener;
  }
  
  public void setOffscreenPageLimit(int paramInt)
  {
    int i = paramInt;
    if (paramInt < 1)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Requested offscreen page limit ");
      localStringBuilder.append(paramInt);
      localStringBuilder.append(" too small; defaulting to ");
      localStringBuilder.append(1);
      Log.w("ViewPager", localStringBuilder.toString());
      i = 1;
    }
    if (i != this.mOffscreenPageLimit)
    {
      this.mOffscreenPageLimit = i;
      populate();
    }
  }
  
  @Deprecated
  public void setOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener)
  {
    this.mOnPageChangeListener = paramOnPageChangeListener;
  }
  
  public void setPageMargin(int paramInt)
  {
    int i = this.mPageMargin;
    this.mPageMargin = paramInt;
    int j = getWidth();
    recomputeScrollPosition(j, j, paramInt, i);
    requestLayout();
  }
  
  public void setPageMarginDrawable(int paramInt)
  {
    setPageMarginDrawable(ContextCompat.getDrawable(getContext(), paramInt));
  }
  
  public void setPageMarginDrawable(Drawable paramDrawable)
  {
    this.mMarginDrawable = paramDrawable;
    if (paramDrawable != null) {
      refreshDrawableState();
    }
    boolean bool;
    if (paramDrawable == null) {
      bool = true;
    } else {
      bool = false;
    }
    setWillNotDraw(bool);
    invalidate();
  }
  
  public void setPageTransformer(boolean paramBoolean, PageTransformer paramPageTransformer)
  {
    setPageTransformer(paramBoolean, paramPageTransformer, 2);
  }
  
  public void setPageTransformer(boolean paramBoolean, PageTransformer paramPageTransformer, int paramInt)
  {
    int i = 1;
    boolean bool1;
    if (paramPageTransformer != null) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    boolean bool2;
    if (this.mPageTransformer != null) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    int j;
    if (bool1 != bool2) {
      j = 1;
    } else {
      j = 0;
    }
    this.mPageTransformer = paramPageTransformer;
    setChildrenDrawingOrderEnabled(bool1);
    if (bool1)
    {
      if (paramBoolean) {
        i = 2;
      }
      this.mDrawingOrder = i;
      this.mPageTransformerLayerType = paramInt;
    }
    else
    {
      this.mDrawingOrder = 0;
    }
    if (j != 0) {
      populate();
    }
  }
  
  void setScrollState(int paramInt)
  {
    if (this.mScrollState == paramInt) {
      return;
    }
    this.mScrollState = paramInt;
    if (this.mPageTransformer != null)
    {
      boolean bool;
      if (paramInt != 0) {
        bool = true;
      } else {
        bool = false;
      }
      enableLayers(bool);
    }
    dispatchOnScrollStateChanged(paramInt);
  }
  
  void smoothScrollTo(int paramInt1, int paramInt2)
  {
    smoothScrollTo(paramInt1, paramInt2, 0);
  }
  
  void smoothScrollTo(int paramInt1, int paramInt2, int paramInt3)
  {
    if (getChildCount() == 0)
    {
      setScrollingCacheEnabled(false);
      return;
    }
    Scroller localScroller = this.mScroller;
    int i;
    if ((localScroller != null) && (!localScroller.isFinished())) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (this.mIsScrollStarted) {
        i = this.mScroller.getCurrX();
      } else {
        i = this.mScroller.getStartX();
      }
      this.mScroller.abortAnimation();
      setScrollingCacheEnabled(false);
    }
    else
    {
      i = getScrollX();
    }
    int j = getScrollY();
    int k = paramInt1 - i;
    paramInt2 -= j;
    if ((k == 0) && (paramInt2 == 0))
    {
      completeScroll(false);
      populate();
      setScrollState(0);
      return;
    }
    setScrollingCacheEnabled(true);
    setScrollState(2);
    int m = getClientWidth();
    paramInt1 = m / 2;
    float f1 = Math.abs(k);
    float f2 = m;
    float f3 = Math.min(1.0F, f1 * 1.0F / f2);
    f1 = paramInt1;
    f3 = distanceInfluenceForSnapDuration(f3);
    paramInt1 = Math.abs(paramInt3);
    if (paramInt1 > 0)
    {
      paramInt1 = Math.round(Math.abs((f1 + f3 * f1) / paramInt1) * 1000.0F) * 4;
    }
    else
    {
      f1 = this.mAdapter.getPageWidth(this.mCurItem);
      paramInt1 = (int)((Math.abs(k) / (f2 * f1 + this.mPageMargin) + 1.0F) * 100.0F);
    }
    paramInt1 = Math.min(paramInt1, 600);
    this.mIsScrollStarted = false;
    this.mScroller.startScroll(i, j, k, paramInt2, paramInt1);
    ViewCompat.postInvalidateOnAnimation(this);
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    boolean bool;
    if ((!super.verifyDrawable(paramDrawable)) && (paramDrawable != this.mMarginDrawable)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  @Inherited
  @Retention(RetentionPolicy.RUNTIME)
  @Target({java.lang.annotation.ElementType.TYPE})
  public static @interface DecorView {}
  
  static class ItemInfo
  {
    Object object;
    float offset;
    int position;
    boolean scrolling;
    float widthFactor;
  }
  
  public static class LayoutParams
    extends ViewGroup.LayoutParams
  {
    int childIndex;
    public int gravity;
    public boolean isDecor;
    boolean needsMeasure;
    int position;
    float widthFactor = 0.0F;
    
    public LayoutParams()
    {
      super(-1);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, ViewPager.LAYOUT_ATTRS);
      this.gravity = paramContext.getInteger(0, 48);
      paramContext.recycle();
    }
  }
  
  class MyAccessibilityDelegate
    extends AccessibilityDelegateCompat
  {
    MyAccessibilityDelegate() {}
    
    private boolean canScroll()
    {
      PagerAdapter localPagerAdapter = ViewPager.this.mAdapter;
      boolean bool = true;
      if ((localPagerAdapter == null) || (ViewPager.this.mAdapter.getCount() <= 1)) {
        bool = false;
      }
      return bool;
    }
    
    public void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      super.onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
      paramAccessibilityEvent.setClassName(ViewPager.class.getName());
      paramAccessibilityEvent.setScrollable(canScroll());
      if ((paramAccessibilityEvent.getEventType() == 4096) && (ViewPager.this.mAdapter != null))
      {
        paramAccessibilityEvent.setItemCount(ViewPager.this.mAdapter.getCount());
        paramAccessibilityEvent.setFromIndex(ViewPager.this.mCurItem);
        paramAccessibilityEvent.setToIndex(ViewPager.this.mCurItem);
      }
    }
    
    public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
      paramAccessibilityNodeInfoCompat.setClassName(ViewPager.class.getName());
      paramAccessibilityNodeInfoCompat.setScrollable(canScroll());
      if (ViewPager.this.canScrollHorizontally(1)) {
        paramAccessibilityNodeInfoCompat.addAction(4096);
      }
      if (ViewPager.this.canScrollHorizontally(-1)) {
        paramAccessibilityNodeInfoCompat.addAction(8192);
      }
    }
    
    public boolean performAccessibilityAction(View paramView, int paramInt, Bundle paramBundle)
    {
      if (super.performAccessibilityAction(paramView, paramInt, paramBundle)) {
        return true;
      }
      if (paramInt != 4096)
      {
        if (paramInt != 8192) {
          return false;
        }
        if (ViewPager.this.canScrollHorizontally(-1))
        {
          paramView = ViewPager.this;
          paramView.setCurrentItem(paramView.mCurItem - 1);
          return true;
        }
        return false;
      }
      if (ViewPager.this.canScrollHorizontally(1))
      {
        paramView = ViewPager.this;
        paramView.setCurrentItem(paramView.mCurItem + 1);
        return true;
      }
      return false;
    }
  }
  
  public static abstract interface OnAdapterChangeListener
  {
    public abstract void onAdapterChanged(ViewPager paramViewPager, PagerAdapter paramPagerAdapter1, PagerAdapter paramPagerAdapter2);
  }
  
  public static abstract interface OnPageChangeListener
  {
    public abstract void onPageScrollStateChanged(int paramInt);
    
    public abstract void onPageScrolled(int paramInt1, float paramFloat, int paramInt2);
    
    public abstract void onPageSelected(int paramInt);
  }
  
  public static abstract interface PageTransformer
  {
    public abstract void transformPage(View paramView, float paramFloat);
  }
  
  private class PagerObserver
    extends DataSetObserver
  {
    PagerObserver() {}
    
    public void onChanged()
    {
      ViewPager.this.dataSetChanged();
    }
    
    public void onInvalidated()
    {
      ViewPager.this.dataSetChanged();
    }
  }
  
  public static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public ViewPager.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new ViewPager.SavedState(paramAnonymousParcel, null);
      }
      
      public ViewPager.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new ViewPager.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public ViewPager.SavedState[] newArray(int paramAnonymousInt)
      {
        return new ViewPager.SavedState[paramAnonymousInt];
      }
    };
    Parcelable adapterState;
    ClassLoader loader;
    int position;
    
    SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      ClassLoader localClassLoader = paramClassLoader;
      if (paramClassLoader == null) {
        localClassLoader = getClass().getClassLoader();
      }
      this.position = paramParcel.readInt();
      this.adapterState = paramParcel.readParcelable(localClassLoader);
      this.loader = localClassLoader;
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("FragmentPager.SavedState{");
      localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
      localStringBuilder.append(" position=");
      localStringBuilder.append(this.position);
      localStringBuilder.append("}");
      return localStringBuilder.toString();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.position);
      paramParcel.writeParcelable(this.adapterState, paramInt);
    }
  }
  
  public static class SimpleOnPageChangeListener
    implements ViewPager.OnPageChangeListener
  {
    public void onPageScrollStateChanged(int paramInt) {}
    
    public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {}
    
    public void onPageSelected(int paramInt) {}
  }
  
  static class ViewPositionComparator
    implements Comparator<View>
  {
    public int compare(View paramView1, View paramView2)
    {
      paramView1 = (ViewPager.LayoutParams)paramView1.getLayoutParams();
      paramView2 = (ViewPager.LayoutParams)paramView2.getLayoutParams();
      if (paramView1.isDecor != paramView2.isDecor)
      {
        int i;
        if (paramView1.isDecor) {
          i = 1;
        } else {
          i = -1;
        }
        return i;
      }
      return paramView1.position - paramView2.position;
    }
  }
}


/* Location:              ~/android/support/v4/view/ViewPager.class
 *
 * Reversed by:           J
 */