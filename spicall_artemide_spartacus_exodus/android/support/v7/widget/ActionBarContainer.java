package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v7.a.a.f;
import android.support.v7.a.a.j;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class ActionBarContainer
  extends FrameLayout
{
  private View mActionBarView;
  Drawable mBackground;
  private View mContextView;
  private int mHeight;
  boolean mIsSplit;
  boolean mIsStacked;
  private boolean mIsTransitioning;
  Drawable mSplitBackground;
  Drawable mStackedBackground;
  private View mTabContainer;
  
  public ActionBarContainer(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ActionBarContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Object localObject;
    if (Build.VERSION.SDK_INT >= 21) {
      localObject = new b(this);
    } else {
      localObject = new a(this);
    }
    ViewCompat.setBackground(this, (Drawable)localObject);
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, a.j.ActionBar);
    this.mBackground = paramContext.getDrawable(a.j.ActionBar_background);
    this.mStackedBackground = paramContext.getDrawable(a.j.ActionBar_backgroundStacked);
    this.mHeight = paramContext.getDimensionPixelSize(a.j.ActionBar_height, -1);
    if (getId() == a.f.split_action_bar)
    {
      this.mIsSplit = true;
      this.mSplitBackground = paramContext.getDrawable(a.j.ActionBar_backgroundSplit);
    }
    paramContext.recycle();
    boolean bool1 = this.mIsSplit;
    boolean bool2 = false;
    if (bool1)
    {
      bool1 = bool2;
      if (this.mSplitBackground != null) {}
    }
    else
    {
      do
      {
        bool1 = true;
        break;
        bool1 = bool2;
        if (this.mBackground != null) {
          break;
        }
        bool1 = bool2;
      } while (this.mStackedBackground == null);
    }
    setWillNotDraw(bool1);
  }
  
  private int getMeasuredHeightWithMargins(View paramView)
  {
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)paramView.getLayoutParams();
    return paramView.getMeasuredHeight() + localLayoutParams.topMargin + localLayoutParams.bottomMargin;
  }
  
  private boolean isCollapsed(View paramView)
  {
    boolean bool;
    if ((paramView != null) && (paramView.getVisibility() != 8) && (paramView.getMeasuredHeight() != 0)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    Drawable localDrawable = this.mBackground;
    if ((localDrawable != null) && (localDrawable.isStateful())) {
      this.mBackground.setState(getDrawableState());
    }
    localDrawable = this.mStackedBackground;
    if ((localDrawable != null) && (localDrawable.isStateful())) {
      this.mStackedBackground.setState(getDrawableState());
    }
    localDrawable = this.mSplitBackground;
    if ((localDrawable != null) && (localDrawable.isStateful())) {
      this.mSplitBackground.setState(getDrawableState());
    }
  }
  
  public View getTabContainer()
  {
    return this.mTabContainer;
  }
  
  public void jumpDrawablesToCurrentState()
  {
    super.jumpDrawablesToCurrentState();
    Drawable localDrawable = this.mBackground;
    if (localDrawable != null) {
      localDrawable.jumpToCurrentState();
    }
    localDrawable = this.mStackedBackground;
    if (localDrawable != null) {
      localDrawable.jumpToCurrentState();
    }
    localDrawable = this.mSplitBackground;
    if (localDrawable != null) {
      localDrawable.jumpToCurrentState();
    }
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    this.mActionBarView = findViewById(a.f.action_bar);
    this.mContextView = findViewById(a.f.action_context_bar);
  }
  
  public boolean onHoverEvent(MotionEvent paramMotionEvent)
  {
    super.onHoverEvent(paramMotionEvent);
    return true;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool;
    if ((!this.mIsTransitioning) && (!super.onInterceptTouchEvent(paramMotionEvent))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    Object localObject1 = this.mTabContainer;
    paramInt2 = 1;
    paramInt4 = 0;
    if ((localObject1 != null) && (((View)localObject1).getVisibility() != 8)) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    }
    Object localObject2;
    if ((localObject1 != null) && (((View)localObject1).getVisibility() != 8))
    {
      int i = getMeasuredHeight();
      localObject2 = (FrameLayout.LayoutParams)((View)localObject1).getLayoutParams();
      ((View)localObject1).layout(paramInt1, i - ((View)localObject1).getMeasuredHeight() - ((FrameLayout.LayoutParams)localObject2).bottomMargin, paramInt3, i - ((FrameLayout.LayoutParams)localObject2).bottomMargin);
    }
    if (this.mIsSplit)
    {
      localObject1 = this.mSplitBackground;
      if (localObject1 != null)
      {
        ((Drawable)localObject1).setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        paramInt1 = paramInt2;
      }
      else
      {
        paramInt1 = 0;
      }
    }
    else
    {
      paramInt1 = paramInt4;
      if (this.mBackground != null)
      {
        if (this.mActionBarView.getVisibility() == 0)
        {
          this.mBackground.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
        }
        else
        {
          localObject2 = this.mContextView;
          if ((localObject2 != null) && (((View)localObject2).getVisibility() == 0)) {
            this.mBackground.setBounds(this.mContextView.getLeft(), this.mContextView.getTop(), this.mContextView.getRight(), this.mContextView.getBottom());
          } else {
            this.mBackground.setBounds(0, 0, 0, 0);
          }
        }
        paramInt1 = 1;
      }
      this.mIsStacked = paramBoolean;
      if (paramBoolean)
      {
        localObject2 = this.mStackedBackground;
        if (localObject2 != null)
        {
          ((Drawable)localObject2).setBounds(((View)localObject1).getLeft(), ((View)localObject1).getTop(), ((View)localObject1).getRight(), ((View)localObject1).getBottom());
          paramInt1 = paramInt2;
        }
      }
    }
    if (paramInt1 != 0) {
      invalidate();
    }
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    int i = paramInt2;
    if (this.mActionBarView == null)
    {
      i = paramInt2;
      if (View.MeasureSpec.getMode(paramInt2) == Integer.MIN_VALUE)
      {
        int j = this.mHeight;
        i = paramInt2;
        if (j >= 0) {
          i = View.MeasureSpec.makeMeasureSpec(Math.min(j, View.MeasureSpec.getSize(paramInt2)), Integer.MIN_VALUE);
        }
      }
    }
    super.onMeasure(paramInt1, i);
    if (this.mActionBarView == null) {
      return;
    }
    paramInt2 = View.MeasureSpec.getMode(i);
    View localView = this.mTabContainer;
    if ((localView != null) && (localView.getVisibility() != 8) && (paramInt2 != 1073741824))
    {
      if (!isCollapsed(this.mActionBarView)) {
        paramInt1 = getMeasuredHeightWithMargins(this.mActionBarView);
      } else if (!isCollapsed(this.mContextView)) {
        paramInt1 = getMeasuredHeightWithMargins(this.mContextView);
      } else {
        paramInt1 = 0;
      }
      if (paramInt2 == Integer.MIN_VALUE) {
        paramInt2 = View.MeasureSpec.getSize(i);
      } else {
        paramInt2 = Integer.MAX_VALUE;
      }
      setMeasuredDimension(getMeasuredWidth(), Math.min(paramInt1 + getMeasuredHeightWithMargins(this.mTabContainer), paramInt2));
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    super.onTouchEvent(paramMotionEvent);
    return true;
  }
  
  public void setPrimaryBackground(Drawable paramDrawable)
  {
    Drawable localDrawable = this.mBackground;
    if (localDrawable != null)
    {
      localDrawable.setCallback(null);
      unscheduleDrawable(this.mBackground);
    }
    this.mBackground = paramDrawable;
    if (paramDrawable != null)
    {
      paramDrawable.setCallback(this);
      paramDrawable = this.mActionBarView;
      if (paramDrawable != null) {
        this.mBackground.setBounds(paramDrawable.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
      }
    }
    boolean bool1 = this.mIsSplit;
    boolean bool2 = true;
    if (bool1 ? this.mSplitBackground == null : (this.mBackground != null) || (this.mStackedBackground != null)) {
      bool2 = false;
    }
    setWillNotDraw(bool2);
    invalidate();
  }
  
  public void setSplitBackground(Drawable paramDrawable)
  {
    Drawable localDrawable = this.mSplitBackground;
    if (localDrawable != null)
    {
      localDrawable.setCallback(null);
      unscheduleDrawable(this.mSplitBackground);
    }
    this.mSplitBackground = paramDrawable;
    boolean bool1 = false;
    if (paramDrawable != null)
    {
      paramDrawable.setCallback(this);
      if (this.mIsSplit)
      {
        paramDrawable = this.mSplitBackground;
        if (paramDrawable != null) {
          paramDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        }
      }
    }
    boolean bool2;
    if (this.mIsSplit)
    {
      bool2 = bool1;
      if (this.mSplitBackground != null) {}
    }
    else
    {
      do
      {
        bool2 = true;
        break;
        bool2 = bool1;
        if (this.mBackground != null) {
          break;
        }
        bool2 = bool1;
      } while (this.mStackedBackground == null);
    }
    setWillNotDraw(bool2);
    invalidate();
  }
  
  public void setStackedBackground(Drawable paramDrawable)
  {
    Drawable localDrawable = this.mStackedBackground;
    if (localDrawable != null)
    {
      localDrawable.setCallback(null);
      unscheduleDrawable(this.mStackedBackground);
    }
    this.mStackedBackground = paramDrawable;
    if (paramDrawable != null)
    {
      paramDrawable.setCallback(this);
      if (this.mIsStacked)
      {
        paramDrawable = this.mStackedBackground;
        if (paramDrawable != null) {
          paramDrawable.setBounds(this.mTabContainer.getLeft(), this.mTabContainer.getTop(), this.mTabContainer.getRight(), this.mTabContainer.getBottom());
        }
      }
    }
    boolean bool1 = this.mIsSplit;
    boolean bool2 = true;
    if (bool1 ? this.mSplitBackground == null : (this.mBackground != null) || (this.mStackedBackground != null)) {
      bool2 = false;
    }
    setWillNotDraw(bool2);
    invalidate();
  }
  
  public void setTabContainer(ScrollingTabContainerView paramScrollingTabContainerView)
  {
    Object localObject = this.mTabContainer;
    if (localObject != null) {
      removeView((View)localObject);
    }
    this.mTabContainer = paramScrollingTabContainerView;
    if (paramScrollingTabContainerView != null)
    {
      addView(paramScrollingTabContainerView);
      localObject = paramScrollingTabContainerView.getLayoutParams();
      ((ViewGroup.LayoutParams)localObject).width = -1;
      ((ViewGroup.LayoutParams)localObject).height = -2;
      paramScrollingTabContainerView.setAllowCollapse(false);
    }
  }
  
  public void setTransitioning(boolean paramBoolean)
  {
    this.mIsTransitioning = paramBoolean;
    int i;
    if (paramBoolean) {
      i = 393216;
    } else {
      i = 262144;
    }
    setDescendantFocusability(i);
  }
  
  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    boolean bool;
    if (paramInt == 0) {
      bool = true;
    } else {
      bool = false;
    }
    Drawable localDrawable = this.mBackground;
    if (localDrawable != null) {
      localDrawable.setVisible(bool, false);
    }
    localDrawable = this.mStackedBackground;
    if (localDrawable != null) {
      localDrawable.setVisible(bool, false);
    }
    localDrawable = this.mSplitBackground;
    if (localDrawable != null) {
      localDrawable.setVisible(bool, false);
    }
  }
  
  public ActionMode startActionModeForChild(View paramView, ActionMode.Callback paramCallback)
  {
    return null;
  }
  
  public ActionMode startActionModeForChild(View paramView, ActionMode.Callback paramCallback, int paramInt)
  {
    if (paramInt != 0) {
      return super.startActionModeForChild(paramView, paramCallback, paramInt);
    }
    return null;
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    boolean bool;
    if (((paramDrawable == this.mBackground) && (!this.mIsSplit)) || ((paramDrawable == this.mStackedBackground) && (this.mIsStacked)) || ((paramDrawable == this.mSplitBackground) && (this.mIsSplit)) || (super.verifyDrawable(paramDrawable))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:              ~/android/support/v7/widget/ActionBarContainer.class
 *
 * Reversed by:           J
 */