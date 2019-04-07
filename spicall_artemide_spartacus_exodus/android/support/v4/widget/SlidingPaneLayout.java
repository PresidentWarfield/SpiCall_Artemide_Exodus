package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SlidingPaneLayout
  extends ViewGroup
{
  private static final int DEFAULT_FADE_COLOR = -858993460;
  private static final int DEFAULT_OVERHANG_SIZE = 32;
  static final SlidingPanelLayoutImpl IMPL;
  private static final int MIN_FLING_VELOCITY = 400;
  private static final String TAG = "SlidingPaneLayout";
  private boolean mCanSlide;
  private int mCoveredFadeColor;
  final ViewDragHelper mDragHelper;
  private boolean mFirstLayout = true;
  private float mInitialMotionX;
  private float mInitialMotionY;
  boolean mIsUnableToDrag;
  private final int mOverhangSize;
  private PanelSlideListener mPanelSlideListener;
  private int mParallaxBy;
  private float mParallaxOffset;
  final ArrayList<DisableLayerRunnable> mPostedRunnables = new ArrayList();
  boolean mPreservedOpenState;
  private Drawable mShadowDrawableLeft;
  private Drawable mShadowDrawableRight;
  float mSlideOffset;
  int mSlideRange;
  View mSlideableView;
  private int mSliderFadeColor = -858993460;
  private final Rect mTmpRect = new Rect();
  
  static
  {
    if (Build.VERSION.SDK_INT >= 17) {
      IMPL = new SlidingPanelLayoutImplJBMR1();
    } else if (Build.VERSION.SDK_INT >= 16) {
      IMPL = new SlidingPanelLayoutImplJB();
    } else {
      IMPL = new SlidingPanelLayoutImplBase();
    }
  }
  
  public SlidingPaneLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SlidingPaneLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public SlidingPaneLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    float f = paramContext.getResources().getDisplayMetrics().density;
    this.mOverhangSize = ((int)(32.0F * f + 0.5F));
    setWillNotDraw(false);
    ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
    ViewCompat.setImportantForAccessibility(this, 1);
    this.mDragHelper = ViewDragHelper.create(this, 0.5F, new DragHelperCallback());
    this.mDragHelper.setMinVelocity(f * 400.0F);
  }
  
  private boolean closePane(View paramView, int paramInt)
  {
    if ((!this.mFirstLayout) && (!smoothSlideTo(0.0F, paramInt))) {
      return false;
    }
    this.mPreservedOpenState = false;
    return true;
  }
  
  private void dimChildView(View paramView, float paramFloat, int paramInt)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    if ((paramFloat > 0.0F) && (paramInt != 0))
    {
      int i = (int)(((0xFF000000 & paramInt) >>> 24) * paramFloat);
      if (localLayoutParams.dimPaint == null) {
        localLayoutParams.dimPaint = new Paint();
      }
      localLayoutParams.dimPaint.setColorFilter(new PorterDuffColorFilter(i << 24 | paramInt & 0xFFFFFF, PorterDuff.Mode.SRC_OVER));
      if (paramView.getLayerType() != 2) {
        paramView.setLayerType(2, localLayoutParams.dimPaint);
      }
      invalidateChildRegion(paramView);
    }
    else if (paramView.getLayerType() != 0)
    {
      if (localLayoutParams.dimPaint != null) {
        localLayoutParams.dimPaint.setColorFilter(null);
      }
      paramView = new DisableLayerRunnable(paramView);
      this.mPostedRunnables.add(paramView);
      ViewCompat.postOnAnimation(this, paramView);
    }
  }
  
  private boolean openPane(View paramView, int paramInt)
  {
    if ((!this.mFirstLayout) && (!smoothSlideTo(1.0F, paramInt))) {
      return false;
    }
    this.mPreservedOpenState = true;
    return true;
  }
  
  private void parallaxOtherViews(float paramFloat)
  {
    boolean bool1 = isLayoutRtlSupport();
    Object localObject = (LayoutParams)this.mSlideableView.getLayoutParams();
    boolean bool2 = ((LayoutParams)localObject).dimWhenOffset;
    int i = 0;
    if (bool2)
    {
      if (bool1) {
        j = ((LayoutParams)localObject).rightMargin;
      } else {
        j = ((LayoutParams)localObject).leftMargin;
      }
      if (j <= 0)
      {
        j = 1;
        break label63;
      }
    }
    int j = 0;
    label63:
    int k = getChildCount();
    while (i < k)
    {
      localObject = getChildAt(i);
      if (localObject != this.mSlideableView)
      {
        float f = this.mParallaxOffset;
        int m = this.mParallaxBy;
        int n = (int)((1.0F - f) * m);
        this.mParallaxOffset = paramFloat;
        m = n - (int)((1.0F - paramFloat) * m);
        n = m;
        if (bool1) {
          n = -m;
        }
        ((View)localObject).offsetLeftAndRight(n);
        if (j != 0)
        {
          if (bool1) {
            f = this.mParallaxOffset - 1.0F;
          } else {
            f = 1.0F - this.mParallaxOffset;
          }
          dimChildView((View)localObject, f, this.mCoveredFadeColor);
        }
      }
      i++;
    }
  }
  
  private static boolean viewIsOpaque(View paramView)
  {
    boolean bool1 = paramView.isOpaque();
    boolean bool2 = true;
    if (bool1) {
      return true;
    }
    if (Build.VERSION.SDK_INT >= 18) {
      return false;
    }
    paramView = paramView.getBackground();
    if (paramView != null)
    {
      if (paramView.getOpacity() != -1) {
        bool2 = false;
      }
      return bool2;
    }
    return false;
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
    if (paramBoolean)
    {
      if (!isLayoutRtlSupport()) {
        paramInt1 = -paramInt1;
      }
      if (paramView.canScrollHorizontally(paramInt1)) {
        return bool2;
      }
    }
    paramBoolean = false;
    return paramBoolean;
  }
  
  @Deprecated
  public boolean canSlide()
  {
    return this.mCanSlide;
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
  
  public boolean closePane()
  {
    return closePane(this.mSlideableView, 0);
  }
  
  public void computeScroll()
  {
    if (this.mDragHelper.continueSettling(true))
    {
      if (!this.mCanSlide)
      {
        this.mDragHelper.abort();
        return;
      }
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  void dispatchOnPanelClosed(View paramView)
  {
    PanelSlideListener localPanelSlideListener = this.mPanelSlideListener;
    if (localPanelSlideListener != null) {
      localPanelSlideListener.onPanelClosed(paramView);
    }
    sendAccessibilityEvent(32);
  }
  
  void dispatchOnPanelOpened(View paramView)
  {
    PanelSlideListener localPanelSlideListener = this.mPanelSlideListener;
    if (localPanelSlideListener != null) {
      localPanelSlideListener.onPanelOpened(paramView);
    }
    sendAccessibilityEvent(32);
  }
  
  void dispatchOnPanelSlide(View paramView)
  {
    PanelSlideListener localPanelSlideListener = this.mPanelSlideListener;
    if (localPanelSlideListener != null) {
      localPanelSlideListener.onPanelSlide(paramView, this.mSlideOffset);
    }
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    Drawable localDrawable;
    if (isLayoutRtlSupport()) {
      localDrawable = this.mShadowDrawableRight;
    } else {
      localDrawable = this.mShadowDrawableLeft;
    }
    View localView;
    if (getChildCount() > 1) {
      localView = getChildAt(1);
    } else {
      localView = null;
    }
    if ((localView != null) && (localDrawable != null))
    {
      int i = localView.getTop();
      int j = localView.getBottom();
      int k = localDrawable.getIntrinsicWidth();
      int m;
      int n;
      if (isLayoutRtlSupport())
      {
        m = localView.getRight();
        n = k + m;
      }
      else
      {
        m = localView.getLeft();
        n = m;
        m -= k;
      }
      localDrawable.setBounds(m, i, n, j);
      localDrawable.draw(paramCanvas);
      return;
    }
  }
  
  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    Object localObject = (LayoutParams)paramView.getLayoutParams();
    int i = paramCanvas.save(2);
    if ((this.mCanSlide) && (!((LayoutParams)localObject).slideable) && (this.mSlideableView != null))
    {
      paramCanvas.getClipBounds(this.mTmpRect);
      if (isLayoutRtlSupport())
      {
        localObject = this.mTmpRect;
        ((Rect)localObject).left = Math.max(((Rect)localObject).left, this.mSlideableView.getRight());
      }
      else
      {
        localObject = this.mTmpRect;
        ((Rect)localObject).right = Math.min(((Rect)localObject).right, this.mSlideableView.getLeft());
      }
      paramCanvas.clipRect(this.mTmpRect);
    }
    boolean bool = super.drawChild(paramCanvas, paramView, paramLong);
    paramCanvas.restoreToCount(i);
    return bool;
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
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      paramLayoutParams = new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
    } else {
      paramLayoutParams = new LayoutParams(paramLayoutParams);
    }
    return paramLayoutParams;
  }
  
  public int getCoveredFadeColor()
  {
    return this.mCoveredFadeColor;
  }
  
  public int getParallaxDistance()
  {
    return this.mParallaxBy;
  }
  
  public int getSliderFadeColor()
  {
    return this.mSliderFadeColor;
  }
  
  void invalidateChildRegion(View paramView)
  {
    IMPL.invalidateChildRegion(this, paramView);
  }
  
  boolean isDimmed(View paramView)
  {
    boolean bool1 = false;
    if (paramView == null) {
      return false;
    }
    paramView = (LayoutParams)paramView.getLayoutParams();
    boolean bool2 = bool1;
    if (this.mCanSlide)
    {
      bool2 = bool1;
      if (paramView.dimWhenOffset)
      {
        bool2 = bool1;
        if (this.mSlideOffset > 0.0F) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  boolean isLayoutRtlSupport()
  {
    int i = ViewCompat.getLayoutDirection(this);
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  public boolean isOpen()
  {
    boolean bool;
    if ((this.mCanSlide) && (this.mSlideOffset != 1.0F)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean isSlideable()
  {
    return this.mCanSlide;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.mFirstLayout = true;
    int i = this.mPostedRunnables.size();
    for (int j = 0; j < i; j++) {
      ((DisableLayerRunnable)this.mPostedRunnables.get(j)).run();
    }
    this.mPostedRunnables.clear();
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionMasked();
    boolean bool1 = this.mCanSlide;
    boolean bool2 = true;
    if ((!bool1) && (i == 0) && (getChildCount() > 1))
    {
      View localView = getChildAt(1);
      if (localView != null) {
        this.mPreservedOpenState = (this.mDragHelper.isViewUnder(localView, (int)paramMotionEvent.getX(), (int)paramMotionEvent.getY()) ^ true);
      }
    }
    if ((this.mCanSlide) && ((!this.mIsUnableToDrag) || (i == 0)))
    {
      if ((i != 3) && (i != 1))
      {
        float f1;
        float f2;
        if (i != 0)
        {
          if (i == 2)
          {
            f1 = paramMotionEvent.getX();
            f2 = paramMotionEvent.getY();
            f1 = Math.abs(f1 - this.mInitialMotionX);
            f2 = Math.abs(f2 - this.mInitialMotionY);
            if ((f1 > this.mDragHelper.getTouchSlop()) && (f2 > f1))
            {
              this.mDragHelper.cancel();
              this.mIsUnableToDrag = true;
              return false;
            }
          }
        }
        else
        {
          this.mIsUnableToDrag = false;
          f1 = paramMotionEvent.getX();
          f2 = paramMotionEvent.getY();
          this.mInitialMotionX = f1;
          this.mInitialMotionY = f2;
          if ((this.mDragHelper.isViewUnder(this.mSlideableView, (int)f1, (int)f2)) && (isDimmed(this.mSlideableView)))
          {
            i = 1;
            break label251;
          }
        }
        i = 0;
        label251:
        bool1 = bool2;
        if (!this.mDragHelper.shouldInterceptTouchEvent(paramMotionEvent)) {
          if (i != 0) {
            bool1 = bool2;
          } else {
            bool1 = false;
          }
        }
        return bool1;
      }
      this.mDragHelper.cancel();
      return false;
    }
    this.mDragHelper.cancel();
    return super.onInterceptTouchEvent(paramMotionEvent);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool = isLayoutRtlSupport();
    if (bool) {
      this.mDragHelper.setEdgeTrackingEnabled(2);
    } else {
      this.mDragHelper.setEdgeTrackingEnabled(1);
    }
    int i = paramInt3 - paramInt1;
    if (bool) {
      paramInt1 = getPaddingRight();
    } else {
      paramInt1 = getPaddingLeft();
    }
    if (bool) {
      paramInt4 = getPaddingLeft();
    } else {
      paramInt4 = getPaddingRight();
    }
    int j = getPaddingTop();
    int k = getChildCount();
    if (this.mFirstLayout)
    {
      float f;
      if ((this.mCanSlide) && (this.mPreservedOpenState)) {
        f = 1.0F;
      } else {
        f = 0.0F;
      }
      this.mSlideOffset = f;
    }
    paramInt2 = paramInt1;
    for (int m = 0; m < k; m++)
    {
      View localView = getChildAt(m);
      if (localView.getVisibility() != 8)
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        int n = localView.getMeasuredWidth();
        int i2;
        if (localLayoutParams.slideable)
        {
          int i1 = localLayoutParams.leftMargin;
          paramInt3 = localLayoutParams.rightMargin;
          i2 = i - paramInt4;
          i1 = Math.min(paramInt1, i2 - this.mOverhangSize) - paramInt2 - (i1 + paramInt3);
          this.mSlideRange = i1;
          if (bool) {
            paramInt3 = localLayoutParams.rightMargin;
          } else {
            paramInt3 = localLayoutParams.leftMargin;
          }
          if (paramInt2 + paramInt3 + i1 + n / 2 > i2) {
            paramBoolean = true;
          } else {
            paramBoolean = false;
          }
          localLayoutParams.dimWhenOffset = paramBoolean;
          i2 = (int)(i1 * this.mSlideOffset);
          paramInt2 = paramInt3 + i2 + paramInt2;
          this.mSlideOffset = (i2 / this.mSlideRange);
          paramInt3 = 0;
        }
        else
        {
          if (this.mCanSlide)
          {
            paramInt2 = this.mParallaxBy;
            if (paramInt2 != 0)
            {
              paramInt3 = (int)((1.0F - this.mSlideOffset) * paramInt2);
              paramInt2 = paramInt1;
              break label356;
            }
          }
          paramInt2 = paramInt1;
          paramInt3 = 0;
        }
        label356:
        if (bool)
        {
          paramInt3 = i - paramInt2 + paramInt3;
          i2 = paramInt3 - n;
        }
        else
        {
          i2 = paramInt2 - paramInt3;
          paramInt3 = i2 + n;
        }
        localView.layout(i2, j, paramInt3, localView.getMeasuredHeight() + j);
        paramInt1 += localView.getWidth();
      }
    }
    if (this.mFirstLayout)
    {
      if (this.mCanSlide)
      {
        if (this.mParallaxBy != 0) {
          parallaxOtherViews(this.mSlideOffset);
        }
        if (((LayoutParams)this.mSlideableView.getLayoutParams()).dimWhenOffset) {
          dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
        }
      }
      else
      {
        for (paramInt1 = 0; paramInt1 < k; paramInt1++) {
          dimChildView(getChildAt(paramInt1), 0.0F, this.mSliderFadeColor);
        }
      }
      updateObscuredViewsVisibility(this.mSlideableView);
    }
    this.mFirstLayout = false;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt1);
    int k = View.MeasureSpec.getMode(paramInt2);
    paramInt2 = View.MeasureSpec.getSize(paramInt2);
    int m;
    int n;
    if (i != 1073741824)
    {
      if (isInEditMode())
      {
        if (i == Integer.MIN_VALUE)
        {
          m = j;
          n = k;
          paramInt1 = paramInt2;
        }
        else
        {
          m = j;
          n = k;
          paramInt1 = paramInt2;
          if (i == 0)
          {
            m = 300;
            n = k;
            paramInt1 = paramInt2;
          }
        }
      }
      else {
        throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
      }
    }
    else
    {
      m = j;
      n = k;
      paramInt1 = paramInt2;
      if (k == 0) {
        if (isInEditMode())
        {
          m = j;
          n = k;
          paramInt1 = paramInt2;
          if (k == 0)
          {
            n = Integer.MIN_VALUE;
            paramInt1 = 300;
            m = j;
          }
        }
        else
        {
          throw new IllegalStateException("Height must not be UNSPECIFIED");
        }
      }
    }
    if (n != Integer.MIN_VALUE)
    {
      if (n != 1073741824)
      {
        paramInt1 = 0;
        paramInt2 = 0;
      }
      else
      {
        paramInt1 = paramInt1 - getPaddingTop() - getPaddingBottom();
        paramInt2 = paramInt1;
      }
    }
    else
    {
      paramInt2 = paramInt1 - getPaddingTop() - getPaddingBottom();
      paramInt1 = 0;
    }
    int i1 = m - getPaddingLeft() - getPaddingRight();
    int i2 = getChildCount();
    if (i2 > 2) {
      Log.e("SlidingPaneLayout", "onMeasure: More than two child views are not supported.");
    }
    this.mSlideableView = null;
    i = i1;
    int i3 = 0;
    boolean bool1 = false;
    float f1 = 0.0F;
    View localView;
    LayoutParams localLayoutParams;
    int i4;
    for (j = paramInt1; i3 < i2; j = paramInt1)
    {
      localView = getChildAt(i3);
      localLayoutParams = (LayoutParams)localView.getLayoutParams();
      float f2;
      if (localView.getVisibility() == 8)
      {
        localLayoutParams.dimWhenOffset = false;
        f2 = f1;
        paramInt1 = j;
      }
      else
      {
        f2 = f1;
        if (localLayoutParams.weight > 0.0F)
        {
          f1 += localLayoutParams.weight;
          f2 = f1;
          if (localLayoutParams.width == 0)
          {
            f2 = f1;
            paramInt1 = j;
            break label588;
          }
        }
        paramInt1 = localLayoutParams.leftMargin + localLayoutParams.rightMargin;
        if (localLayoutParams.width == -2) {
          paramInt1 = View.MeasureSpec.makeMeasureSpec(i1 - paramInt1, Integer.MIN_VALUE);
        } else if (localLayoutParams.width == -1) {
          paramInt1 = View.MeasureSpec.makeMeasureSpec(i1 - paramInt1, 1073741824);
        } else {
          paramInt1 = View.MeasureSpec.makeMeasureSpec(localLayoutParams.width, 1073741824);
        }
        if (localLayoutParams.height == -2) {
          k = View.MeasureSpec.makeMeasureSpec(paramInt2, Integer.MIN_VALUE);
        } else if (localLayoutParams.height == -1) {
          k = View.MeasureSpec.makeMeasureSpec(paramInt2, 1073741824);
        } else {
          k = View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, 1073741824);
        }
        localView.measure(paramInt1, k);
        k = localView.getMeasuredWidth();
        i4 = localView.getMeasuredHeight();
        paramInt1 = j;
        if (n == Integer.MIN_VALUE)
        {
          paramInt1 = j;
          if (i4 > j) {
            paramInt1 = Math.min(i4, paramInt2);
          }
        }
        i -= k;
        boolean bool2;
        if (i < 0) {
          bool2 = true;
        } else {
          bool2 = false;
        }
        localLayoutParams.slideable = bool2;
        if (localLayoutParams.slideable) {
          this.mSlideableView = localView;
        }
        bool1 = bool2 | bool1;
      }
      label588:
      i3++;
      f1 = f2;
    }
    if ((bool1) || (f1 > 0.0F))
    {
      k = i1 - this.mOverhangSize;
      for (n = 0; n < i2; n++)
      {
        localView = getChildAt(n);
        if (localView.getVisibility() != 8)
        {
          localLayoutParams = (LayoutParams)localView.getLayoutParams();
          if (localView.getVisibility() != 8)
          {
            if ((localLayoutParams.width == 0) && (localLayoutParams.weight > 0.0F)) {
              paramInt1 = 1;
            } else {
              paramInt1 = 0;
            }
            if (paramInt1 != 0) {
              i3 = 0;
            } else {
              i3 = localView.getMeasuredWidth();
            }
            if ((bool1) && (localView != this.mSlideableView))
            {
              if ((localLayoutParams.width < 0) && ((i3 > k) || (localLayoutParams.weight > 0.0F)))
              {
                if (paramInt1 != 0)
                {
                  if (localLayoutParams.height == -2) {
                    paramInt1 = View.MeasureSpec.makeMeasureSpec(paramInt2, Integer.MIN_VALUE);
                  } else if (localLayoutParams.height == -1) {
                    paramInt1 = View.MeasureSpec.makeMeasureSpec(paramInt2, 1073741824);
                  } else {
                    paramInt1 = View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, 1073741824);
                  }
                }
                else {
                  paramInt1 = View.MeasureSpec.makeMeasureSpec(localView.getMeasuredHeight(), 1073741824);
                }
                localView.measure(View.MeasureSpec.makeMeasureSpec(k, 1073741824), paramInt1);
              }
            }
            else if (localLayoutParams.weight > 0.0F)
            {
              if (localLayoutParams.width == 0)
              {
                if (localLayoutParams.height == -2) {
                  paramInt1 = View.MeasureSpec.makeMeasureSpec(paramInt2, Integer.MIN_VALUE);
                } else if (localLayoutParams.height == -1) {
                  paramInt1 = View.MeasureSpec.makeMeasureSpec(paramInt2, 1073741824);
                } else {
                  paramInt1 = View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, 1073741824);
                }
              }
              else {
                paramInt1 = View.MeasureSpec.makeMeasureSpec(localView.getMeasuredHeight(), 1073741824);
              }
              if (bool1)
              {
                int i5 = i1 - (localLayoutParams.leftMargin + localLayoutParams.rightMargin);
                i4 = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
                if (i3 != i5) {
                  localView.measure(i4, paramInt1);
                }
              }
              else
              {
                i4 = Math.max(0, i);
                localView.measure(View.MeasureSpec.makeMeasureSpec(i3 + (int)(localLayoutParams.weight * i4 / f1), 1073741824), paramInt1);
              }
            }
          }
        }
      }
    }
    setMeasuredDimension(m, j + getPaddingTop() + getPaddingBottom());
    this.mCanSlide = bool1;
    if ((this.mDragHelper.getViewDragState() != 0) && (!bool1)) {
      this.mDragHelper.abort();
    }
  }
  
  void onPanelDragged(int paramInt)
  {
    if (this.mSlideableView == null)
    {
      this.mSlideOffset = 0.0F;
      return;
    }
    boolean bool = isLayoutRtlSupport();
    LayoutParams localLayoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
    int i = this.mSlideableView.getWidth();
    int j = paramInt;
    if (bool) {
      j = getWidth() - paramInt - i;
    }
    if (bool) {
      paramInt = getPaddingRight();
    } else {
      paramInt = getPaddingLeft();
    }
    if (bool) {
      i = localLayoutParams.rightMargin;
    } else {
      i = localLayoutParams.leftMargin;
    }
    this.mSlideOffset = ((j - (paramInt + i)) / this.mSlideRange);
    if (this.mParallaxBy != 0) {
      parallaxOtherViews(this.mSlideOffset);
    }
    if (localLayoutParams.dimWhenOffset) {
      dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
    }
    dispatchOnPanelSlide(this.mSlideableView);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    if (paramParcelable.isOpen) {
      openPane();
    } else {
      closePane();
    }
    this.mPreservedOpenState = paramParcelable.isOpen;
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    boolean bool;
    if (isSlideable()) {
      bool = isOpen();
    } else {
      bool = this.mPreservedOpenState;
    }
    localSavedState.isOpen = bool;
    return localSavedState;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 != paramInt3) {
      this.mFirstLayout = true;
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (!this.mCanSlide) {
      return super.onTouchEvent(paramMotionEvent);
    }
    this.mDragHelper.processTouchEvent(paramMotionEvent);
    float f1;
    float f4;
    switch (paramMotionEvent.getActionMasked())
    {
    default: 
      break;
    case 1: 
      if (isDimmed(this.mSlideableView))
      {
        f1 = paramMotionEvent.getX();
        float f2 = paramMotionEvent.getY();
        float f3 = f1 - this.mInitialMotionX;
        f4 = f2 - this.mInitialMotionY;
        int i = this.mDragHelper.getTouchSlop();
        if ((f3 * f3 + f4 * f4 < i * i) && (this.mDragHelper.isViewUnder(this.mSlideableView, (int)f1, (int)f2))) {
          closePane(this.mSlideableView, 0);
        }
      }
      break;
    case 0: 
      f4 = paramMotionEvent.getX();
      f1 = paramMotionEvent.getY();
      this.mInitialMotionX = f4;
      this.mInitialMotionY = f1;
    }
    return true;
  }
  
  public boolean openPane()
  {
    return openPane(this.mSlideableView, 0);
  }
  
  public void requestChildFocus(View paramView1, View paramView2)
  {
    super.requestChildFocus(paramView1, paramView2);
    if ((!isInTouchMode()) && (!this.mCanSlide))
    {
      boolean bool;
      if (paramView1 == this.mSlideableView) {
        bool = true;
      } else {
        bool = false;
      }
      this.mPreservedOpenState = bool;
    }
  }
  
  void setAllChildrenVisible()
  {
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = getChildAt(j);
      if (localView.getVisibility() == 4) {
        localView.setVisibility(0);
      }
    }
  }
  
  public void setCoveredFadeColor(int paramInt)
  {
    this.mCoveredFadeColor = paramInt;
  }
  
  public void setPanelSlideListener(PanelSlideListener paramPanelSlideListener)
  {
    this.mPanelSlideListener = paramPanelSlideListener;
  }
  
  public void setParallaxDistance(int paramInt)
  {
    this.mParallaxBy = paramInt;
    requestLayout();
  }
  
  @Deprecated
  public void setShadowDrawable(Drawable paramDrawable)
  {
    setShadowDrawableLeft(paramDrawable);
  }
  
  public void setShadowDrawableLeft(Drawable paramDrawable)
  {
    this.mShadowDrawableLeft = paramDrawable;
  }
  
  public void setShadowDrawableRight(Drawable paramDrawable)
  {
    this.mShadowDrawableRight = paramDrawable;
  }
  
  @Deprecated
  public void setShadowResource(int paramInt)
  {
    setShadowDrawable(getResources().getDrawable(paramInt));
  }
  
  public void setShadowResourceLeft(int paramInt)
  {
    setShadowDrawableLeft(ContextCompat.getDrawable(getContext(), paramInt));
  }
  
  public void setShadowResourceRight(int paramInt)
  {
    setShadowDrawableRight(ContextCompat.getDrawable(getContext(), paramInt));
  }
  
  public void setSliderFadeColor(int paramInt)
  {
    this.mSliderFadeColor = paramInt;
  }
  
  @Deprecated
  public void smoothSlideClosed()
  {
    closePane();
  }
  
  @Deprecated
  public void smoothSlideOpen()
  {
    openPane();
  }
  
  boolean smoothSlideTo(float paramFloat, int paramInt)
  {
    if (!this.mCanSlide) {
      return false;
    }
    boolean bool = isLayoutRtlSupport();
    Object localObject = (LayoutParams)this.mSlideableView.getLayoutParams();
    if (bool)
    {
      paramInt = getPaddingRight();
      int i = ((LayoutParams)localObject).rightMargin;
      int j = this.mSlideableView.getWidth();
      paramInt = (int)(getWidth() - (paramInt + i + paramFloat * this.mSlideRange + j));
    }
    else
    {
      paramInt = (int)(getPaddingLeft() + ((LayoutParams)localObject).leftMargin + paramFloat * this.mSlideRange);
    }
    ViewDragHelper localViewDragHelper = this.mDragHelper;
    localObject = this.mSlideableView;
    if (localViewDragHelper.smoothSlideViewTo((View)localObject, paramInt, ((View)localObject).getTop()))
    {
      setAllChildrenVisible();
      ViewCompat.postInvalidateOnAnimation(this);
      return true;
    }
    return false;
  }
  
  void updateObscuredViewsVisibility(View paramView)
  {
    boolean bool = isLayoutRtlSupport();
    int i;
    if (bool) {
      i = getWidth() - getPaddingRight();
    } else {
      i = getPaddingLeft();
    }
    int j;
    if (bool) {
      j = getPaddingLeft();
    } else {
      j = getWidth() - getPaddingRight();
    }
    int k = getPaddingTop();
    int m = getHeight();
    int n = getPaddingBottom();
    int i1;
    int i2;
    int i3;
    int i4;
    if ((paramView != null) && (viewIsOpaque(paramView)))
    {
      i1 = paramView.getLeft();
      i2 = paramView.getRight();
      i3 = paramView.getTop();
      i4 = paramView.getBottom();
    }
    else
    {
      i1 = 0;
      i2 = 0;
      i3 = 0;
      i4 = 0;
    }
    int i5 = getChildCount();
    for (int i6 = 0; i6 < i5; i6++)
    {
      View localView = getChildAt(i6);
      if (localView == paramView) {
        break;
      }
      if (localView.getVisibility() != 8)
      {
        if (bool) {
          i7 = j;
        } else {
          i7 = i;
        }
        int i8 = Math.max(i7, localView.getLeft());
        int i9 = Math.max(k, localView.getTop());
        if (bool) {
          i7 = i;
        } else {
          i7 = j;
        }
        int i7 = Math.min(i7, localView.getRight());
        int i10 = Math.min(m - n, localView.getBottom());
        if ((i8 >= i1) && (i9 >= i3) && (i7 <= i2) && (i10 <= i4)) {
          i7 = 4;
        } else {
          i7 = 0;
        }
        localView.setVisibility(i7);
      }
    }
  }
  
  class AccessibilityDelegate
    extends AccessibilityDelegateCompat
  {
    private final Rect mTmpRect = new Rect();
    
    AccessibilityDelegate() {}
    
    private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat1, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat2)
    {
      Rect localRect = this.mTmpRect;
      paramAccessibilityNodeInfoCompat2.getBoundsInParent(localRect);
      paramAccessibilityNodeInfoCompat1.setBoundsInParent(localRect);
      paramAccessibilityNodeInfoCompat2.getBoundsInScreen(localRect);
      paramAccessibilityNodeInfoCompat1.setBoundsInScreen(localRect);
      paramAccessibilityNodeInfoCompat1.setVisibleToUser(paramAccessibilityNodeInfoCompat2.isVisibleToUser());
      paramAccessibilityNodeInfoCompat1.setPackageName(paramAccessibilityNodeInfoCompat2.getPackageName());
      paramAccessibilityNodeInfoCompat1.setClassName(paramAccessibilityNodeInfoCompat2.getClassName());
      paramAccessibilityNodeInfoCompat1.setContentDescription(paramAccessibilityNodeInfoCompat2.getContentDescription());
      paramAccessibilityNodeInfoCompat1.setEnabled(paramAccessibilityNodeInfoCompat2.isEnabled());
      paramAccessibilityNodeInfoCompat1.setClickable(paramAccessibilityNodeInfoCompat2.isClickable());
      paramAccessibilityNodeInfoCompat1.setFocusable(paramAccessibilityNodeInfoCompat2.isFocusable());
      paramAccessibilityNodeInfoCompat1.setFocused(paramAccessibilityNodeInfoCompat2.isFocused());
      paramAccessibilityNodeInfoCompat1.setAccessibilityFocused(paramAccessibilityNodeInfoCompat2.isAccessibilityFocused());
      paramAccessibilityNodeInfoCompat1.setSelected(paramAccessibilityNodeInfoCompat2.isSelected());
      paramAccessibilityNodeInfoCompat1.setLongClickable(paramAccessibilityNodeInfoCompat2.isLongClickable());
      paramAccessibilityNodeInfoCompat1.addAction(paramAccessibilityNodeInfoCompat2.getActions());
      paramAccessibilityNodeInfoCompat1.setMovementGranularities(paramAccessibilityNodeInfoCompat2.getMovementGranularities());
    }
    
    public boolean filter(View paramView)
    {
      return SlidingPaneLayout.this.isDimmed(paramView);
    }
    
    public void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      super.onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
      paramAccessibilityEvent.setClassName(SlidingPaneLayout.class.getName());
    }
    
    public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      AccessibilityNodeInfoCompat localAccessibilityNodeInfoCompat = AccessibilityNodeInfoCompat.obtain(paramAccessibilityNodeInfoCompat);
      super.onInitializeAccessibilityNodeInfo(paramView, localAccessibilityNodeInfoCompat);
      copyNodeInfoNoChildren(paramAccessibilityNodeInfoCompat, localAccessibilityNodeInfoCompat);
      localAccessibilityNodeInfoCompat.recycle();
      paramAccessibilityNodeInfoCompat.setClassName(SlidingPaneLayout.class.getName());
      paramAccessibilityNodeInfoCompat.setSource(paramView);
      paramView = ViewCompat.getParentForAccessibility(paramView);
      if ((paramView instanceof View)) {
        paramAccessibilityNodeInfoCompat.setParent((View)paramView);
      }
      int i = SlidingPaneLayout.this.getChildCount();
      for (int j = 0; j < i; j++)
      {
        paramView = SlidingPaneLayout.this.getChildAt(j);
        if ((!filter(paramView)) && (paramView.getVisibility() == 0))
        {
          ViewCompat.setImportantForAccessibility(paramView, 1);
          paramAccessibilityNodeInfoCompat.addChild(paramView);
        }
      }
    }
    
    public boolean onRequestSendAccessibilityEvent(ViewGroup paramViewGroup, View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      if (!filter(paramView)) {
        return super.onRequestSendAccessibilityEvent(paramViewGroup, paramView, paramAccessibilityEvent);
      }
      return false;
    }
  }
  
  private class DisableLayerRunnable
    implements Runnable
  {
    final View mChildView;
    
    DisableLayerRunnable(View paramView)
    {
      this.mChildView = paramView;
    }
    
    public void run()
    {
      if (this.mChildView.getParent() == SlidingPaneLayout.this)
      {
        this.mChildView.setLayerType(0, null);
        SlidingPaneLayout.this.invalidateChildRegion(this.mChildView);
      }
      SlidingPaneLayout.this.mPostedRunnables.remove(this);
    }
  }
  
  private class DragHelperCallback
    extends ViewDragHelper.Callback
  {
    DragHelperCallback() {}
    
    public int clampViewPositionHorizontal(View paramView, int paramInt1, int paramInt2)
    {
      paramView = (SlidingPaneLayout.LayoutParams)SlidingPaneLayout.this.mSlideableView.getLayoutParams();
      int i;
      if (SlidingPaneLayout.this.isLayoutRtlSupport())
      {
        paramInt2 = SlidingPaneLayout.this.getWidth() - (SlidingPaneLayout.this.getPaddingRight() + paramView.rightMargin + SlidingPaneLayout.this.mSlideableView.getWidth());
        i = SlidingPaneLayout.this.mSlideRange;
        paramInt1 = Math.max(Math.min(paramInt1, paramInt2), paramInt2 - i);
      }
      else
      {
        i = SlidingPaneLayout.this.getPaddingLeft() + paramView.leftMargin;
        paramInt2 = SlidingPaneLayout.this.mSlideRange;
        paramInt1 = Math.min(Math.max(paramInt1, i), paramInt2 + i);
      }
      return paramInt1;
    }
    
    public int clampViewPositionVertical(View paramView, int paramInt1, int paramInt2)
    {
      return paramView.getTop();
    }
    
    public int getViewHorizontalDragRange(View paramView)
    {
      return SlidingPaneLayout.this.mSlideRange;
    }
    
    public void onEdgeDragStarted(int paramInt1, int paramInt2)
    {
      SlidingPaneLayout.this.mDragHelper.captureChildView(SlidingPaneLayout.this.mSlideableView, paramInt2);
    }
    
    public void onViewCaptured(View paramView, int paramInt)
    {
      SlidingPaneLayout.this.setAllChildrenVisible();
    }
    
    public void onViewDragStateChanged(int paramInt)
    {
      if (SlidingPaneLayout.this.mDragHelper.getViewDragState() == 0)
      {
        SlidingPaneLayout localSlidingPaneLayout;
        if (SlidingPaneLayout.this.mSlideOffset == 0.0F)
        {
          localSlidingPaneLayout = SlidingPaneLayout.this;
          localSlidingPaneLayout.updateObscuredViewsVisibility(localSlidingPaneLayout.mSlideableView);
          localSlidingPaneLayout = SlidingPaneLayout.this;
          localSlidingPaneLayout.dispatchOnPanelClosed(localSlidingPaneLayout.mSlideableView);
          SlidingPaneLayout.this.mPreservedOpenState = false;
        }
        else
        {
          localSlidingPaneLayout = SlidingPaneLayout.this;
          localSlidingPaneLayout.dispatchOnPanelOpened(localSlidingPaneLayout.mSlideableView);
          SlidingPaneLayout.this.mPreservedOpenState = true;
        }
      }
    }
    
    public void onViewPositionChanged(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      SlidingPaneLayout.this.onPanelDragged(paramInt1);
      SlidingPaneLayout.this.invalidate();
    }
    
    public void onViewReleased(View paramView, float paramFloat1, float paramFloat2)
    {
      SlidingPaneLayout.LayoutParams localLayoutParams = (SlidingPaneLayout.LayoutParams)paramView.getLayoutParams();
      int i;
      int j;
      if (SlidingPaneLayout.this.isLayoutRtlSupport())
      {
        i = SlidingPaneLayout.this.getPaddingRight() + localLayoutParams.rightMargin;
        if (paramFloat1 >= 0.0F)
        {
          j = i;
          if (paramFloat1 == 0.0F)
          {
            j = i;
            if (SlidingPaneLayout.this.mSlideOffset <= 0.5F) {}
          }
        }
        else
        {
          j = i + SlidingPaneLayout.this.mSlideRange;
        }
        i = SlidingPaneLayout.this.mSlideableView.getWidth();
        j = SlidingPaneLayout.this.getWidth() - j - i;
      }
      else
      {
        j = SlidingPaneLayout.this.getPaddingLeft();
        i = localLayoutParams.leftMargin + j;
        if (paramFloat1 <= 0.0F)
        {
          j = i;
          if (paramFloat1 == 0.0F)
          {
            j = i;
            if (SlidingPaneLayout.this.mSlideOffset <= 0.5F) {}
          }
        }
        else
        {
          j = i + SlidingPaneLayout.this.mSlideRange;
        }
      }
      SlidingPaneLayout.this.mDragHelper.settleCapturedViewAt(j, paramView.getTop());
      SlidingPaneLayout.this.invalidate();
    }
    
    public boolean tryCaptureView(View paramView, int paramInt)
    {
      if (SlidingPaneLayout.this.mIsUnableToDrag) {
        return false;
      }
      return ((SlidingPaneLayout.LayoutParams)paramView.getLayoutParams()).slideable;
    }
  }
  
  public static class LayoutParams
    extends ViewGroup.MarginLayoutParams
  {
    private static final int[] ATTRS = { 16843137 };
    Paint dimPaint;
    boolean dimWhenOffset;
    boolean slideable;
    public float weight = 0.0F;
    
    public LayoutParams()
    {
      super(-1);
    }
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, ATTRS);
      this.weight = paramContext.getFloat(0, 0.0F);
      paramContext.recycle();
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
      this.weight = paramLayoutParams.weight;
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
  }
  
  public static abstract interface PanelSlideListener
  {
    public abstract void onPanelClosed(View paramView);
    
    public abstract void onPanelOpened(View paramView);
    
    public abstract void onPanelSlide(View paramView, float paramFloat);
  }
  
  static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public SlidingPaneLayout.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new SlidingPaneLayout.SavedState(paramAnonymousParcel, null);
      }
      
      public SlidingPaneLayout.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new SlidingPaneLayout.SavedState(paramAnonymousParcel, null);
      }
      
      public SlidingPaneLayout.SavedState[] newArray(int paramAnonymousInt)
      {
        return new SlidingPaneLayout.SavedState[paramAnonymousInt];
      }
    };
    boolean isOpen;
    
    SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      boolean bool;
      if (paramParcel.readInt() != 0) {
        bool = true;
      } else {
        bool = false;
      }
      this.isOpen = bool;
    }
    
    SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.isOpen);
    }
  }
  
  public static class SimplePanelSlideListener
    implements SlidingPaneLayout.PanelSlideListener
  {
    public void onPanelClosed(View paramView) {}
    
    public void onPanelOpened(View paramView) {}
    
    public void onPanelSlide(View paramView, float paramFloat) {}
  }
  
  static abstract interface SlidingPanelLayoutImpl
  {
    public abstract void invalidateChildRegion(SlidingPaneLayout paramSlidingPaneLayout, View paramView);
  }
  
  static class SlidingPanelLayoutImplBase
    implements SlidingPaneLayout.SlidingPanelLayoutImpl
  {
    public void invalidateChildRegion(SlidingPaneLayout paramSlidingPaneLayout, View paramView)
    {
      ViewCompat.postInvalidateOnAnimation(paramSlidingPaneLayout, paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom());
    }
  }
  
  static class SlidingPanelLayoutImplJB
    extends SlidingPaneLayout.SlidingPanelLayoutImplBase
  {
    private Method mGetDisplayList;
    private Field mRecreateDisplayList;
    
    SlidingPanelLayoutImplJB()
    {
      try
      {
        this.mGetDisplayList = View.class.getDeclaredMethod("getDisplayList", (Class[])null);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Log.e("SlidingPaneLayout", "Couldn't fetch getDisplayList method; dimming won't work right.", localNoSuchMethodException);
      }
      try
      {
        this.mRecreateDisplayList = View.class.getDeclaredField("mRecreateDisplayList");
        this.mRecreateDisplayList.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        Log.e("SlidingPaneLayout", "Couldn't fetch mRecreateDisplayList field; dimming will be slow.", localNoSuchFieldException);
      }
    }
    
    public void invalidateChildRegion(SlidingPaneLayout paramSlidingPaneLayout, View paramView)
    {
      if (this.mGetDisplayList != null)
      {
        Field localField = this.mRecreateDisplayList;
        if (localField != null)
        {
          try
          {
            localField.setBoolean(paramView, true);
            this.mGetDisplayList.invoke(paramView, (Object[])null);
          }
          catch (Exception localException)
          {
            Log.e("SlidingPaneLayout", "Error refreshing display list state", localException);
          }
          super.invalidateChildRegion(paramSlidingPaneLayout, paramView);
          return;
        }
      }
      paramView.invalidate();
    }
  }
  
  static class SlidingPanelLayoutImplJBMR1
    extends SlidingPaneLayout.SlidingPanelLayoutImplBase
  {
    public void invalidateChildRegion(SlidingPaneLayout paramSlidingPaneLayout, View paramView)
    {
      ViewCompat.setLayerPaint(paramView, ((SlidingPaneLayout.LayoutParams)paramView.getLayoutParams()).dimPaint);
    }
  }
}


/* Location:              ~/android/support/v4/widget/SlidingPaneLayout.class
 *
 * Reversed by:           J
 */