package android.support.v7.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.a.a.a;
import android.support.v7.a.a.f;
import android.support.v7.a.a.g;
import android.support.v7.a.a.j;
import android.support.v7.view.b;
import android.support.v7.view.menu.h;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActionBarContextView
  extends AbsActionBarView
{
  private static final String TAG = "ActionBarContextView";
  private View mClose;
  private int mCloseItemLayout;
  private View mCustomView;
  private CharSequence mSubtitle;
  private int mSubtitleStyleRes;
  private TextView mSubtitleView;
  private CharSequence mTitle;
  private LinearLayout mTitleLayout;
  private boolean mTitleOptional;
  private int mTitleStyleRes;
  private TextView mTitleView;
  
  public ActionBarContextView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ActionBarContextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, a.a.actionModeStyle);
  }
  
  public ActionBarContextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    paramContext = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, a.j.ActionMode, paramInt, 0);
    ViewCompat.setBackground(this, paramContext.getDrawable(a.j.ActionMode_background));
    this.mTitleStyleRes = paramContext.getResourceId(a.j.ActionMode_titleTextStyle, 0);
    this.mSubtitleStyleRes = paramContext.getResourceId(a.j.ActionMode_subtitleTextStyle, 0);
    this.mContentHeight = paramContext.getLayoutDimension(a.j.ActionMode_height, 0);
    this.mCloseItemLayout = paramContext.getResourceId(a.j.ActionMode_closeItemLayout, a.g.abc_action_mode_close_item_material);
    paramContext.recycle();
  }
  
  private void initTitle()
  {
    if (this.mTitleLayout == null)
    {
      LayoutInflater.from(getContext()).inflate(a.g.abc_action_bar_title_item, this);
      this.mTitleLayout = ((LinearLayout)getChildAt(getChildCount() - 1));
      this.mTitleView = ((TextView)this.mTitleLayout.findViewById(a.f.action_bar_title));
      this.mSubtitleView = ((TextView)this.mTitleLayout.findViewById(a.f.action_bar_subtitle));
      if (this.mTitleStyleRes != 0) {
        this.mTitleView.setTextAppearance(getContext(), this.mTitleStyleRes);
      }
      if (this.mSubtitleStyleRes != 0) {
        this.mSubtitleView.setTextAppearance(getContext(), this.mSubtitleStyleRes);
      }
    }
    this.mTitleView.setText(this.mTitle);
    this.mSubtitleView.setText(this.mSubtitle);
    boolean bool1 = TextUtils.isEmpty(this.mTitle);
    boolean bool2 = TextUtils.isEmpty(this.mSubtitle) ^ true;
    Object localObject = this.mSubtitleView;
    int i = 0;
    if (bool2) {
      j = 0;
    } else {
      j = 8;
    }
    ((TextView)localObject).setVisibility(j);
    localObject = this.mTitleLayout;
    int j = i;
    if (!(bool1 ^ true)) {
      if (bool2) {
        j = i;
      } else {
        j = 8;
      }
    }
    ((LinearLayout)localObject).setVisibility(j);
    if (this.mTitleLayout.getParent() == null) {
      addView(this.mTitleLayout);
    }
  }
  
  public void closeMode()
  {
    if (this.mClose == null)
    {
      killMode();
      return;
    }
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    return new ViewGroup.MarginLayoutParams(-1, -2);
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new ViewGroup.MarginLayoutParams(getContext(), paramAttributeSet);
  }
  
  public CharSequence getSubtitle()
  {
    return this.mSubtitle;
  }
  
  public CharSequence getTitle()
  {
    return this.mTitle;
  }
  
  public boolean hideOverflowMenu()
  {
    if (this.mActionMenuPresenter != null) {
      return this.mActionMenuPresenter.g();
    }
    return false;
  }
  
  public void initForMode(final b paramb)
  {
    Object localObject = this.mClose;
    if (localObject == null)
    {
      this.mClose = LayoutInflater.from(getContext()).inflate(this.mCloseItemLayout, this, false);
      addView(this.mClose);
    }
    else if (((View)localObject).getParent() == null)
    {
      addView(this.mClose);
    }
    this.mClose.findViewById(a.f.action_mode_close_button).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramb.c();
      }
    });
    localObject = (h)paramb.b();
    if (this.mActionMenuPresenter != null) {
      this.mActionMenuPresenter.h();
    }
    this.mActionMenuPresenter = new c(getContext());
    this.mActionMenuPresenter.b(true);
    paramb = new ViewGroup.LayoutParams(-2, -1);
    ((h)localObject).a(this.mActionMenuPresenter, this.mPopupContext);
    this.mMenuView = ((ActionMenuView)this.mActionMenuPresenter.a(this));
    ViewCompat.setBackground(this.mMenuView, null);
    addView(this.mMenuView, paramb);
  }
  
  public boolean isOverflowMenuShowing()
  {
    if (this.mActionMenuPresenter != null) {
      return this.mActionMenuPresenter.j();
    }
    return false;
  }
  
  public boolean isTitleOptional()
  {
    return this.mTitleOptional;
  }
  
  public void killMode()
  {
    removeAllViews();
    this.mCustomView = null;
    this.mMenuView = null;
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.mActionMenuPresenter != null)
    {
      this.mActionMenuPresenter.g();
      this.mActionMenuPresenter.i();
    }
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    if (paramAccessibilityEvent.getEventType() == 32)
    {
      paramAccessibilityEvent.setSource(this);
      paramAccessibilityEvent.setClassName(getClass().getName());
      paramAccessibilityEvent.setPackageName(getContext().getPackageName());
      paramAccessibilityEvent.setContentDescription(this.mTitle);
    }
    else
    {
      super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramBoolean = ViewUtils.isLayoutRtl(this);
    int i;
    if (paramBoolean) {
      i = paramInt3 - paramInt1 - getPaddingRight();
    } else {
      i = getPaddingLeft();
    }
    int j = getPaddingTop();
    int k = paramInt4 - paramInt2 - getPaddingTop() - getPaddingBottom();
    Object localObject = this.mClose;
    if ((localObject != null) && (((View)localObject).getVisibility() != 8))
    {
      localObject = (ViewGroup.MarginLayoutParams)this.mClose.getLayoutParams();
      if (paramBoolean) {
        paramInt2 = ((ViewGroup.MarginLayoutParams)localObject).rightMargin;
      } else {
        paramInt2 = ((ViewGroup.MarginLayoutParams)localObject).leftMargin;
      }
      if (paramBoolean) {
        paramInt4 = ((ViewGroup.MarginLayoutParams)localObject).leftMargin;
      } else {
        paramInt4 = ((ViewGroup.MarginLayoutParams)localObject).rightMargin;
      }
      paramInt2 = next(i, paramInt2, paramBoolean);
      paramInt2 = next(paramInt2 + positionChild(this.mClose, paramInt2, j, k, paramBoolean), paramInt4, paramBoolean);
    }
    else
    {
      paramInt2 = i;
    }
    localObject = this.mTitleLayout;
    if ((localObject != null) && (this.mCustomView == null) && (((LinearLayout)localObject).getVisibility() != 8)) {
      paramInt2 += positionChild(this.mTitleLayout, paramInt2, j, k, paramBoolean);
    }
    localObject = this.mCustomView;
    if (localObject != null) {
      positionChild((View)localObject, paramInt2, j, k, paramBoolean);
    }
    if (paramBoolean) {
      paramInt1 = getPaddingLeft();
    } else {
      paramInt1 = paramInt3 - paramInt1 - getPaddingRight();
    }
    if (this.mMenuView != null) {
      positionChild(this.mMenuView, paramInt1, j, k, paramBoolean ^ true);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = 1073741824;
    if (i == 1073741824)
    {
      if (View.MeasureSpec.getMode(paramInt2) != 0)
      {
        int k = View.MeasureSpec.getSize(paramInt1);
        if (this.mContentHeight > 0) {
          i = this.mContentHeight;
        } else {
          i = View.MeasureSpec.getSize(paramInt2);
        }
        int m = getPaddingTop() + getPaddingBottom();
        paramInt1 = k - getPaddingLeft() - getPaddingRight();
        int n = i - m;
        int i1 = View.MeasureSpec.makeMeasureSpec(n, Integer.MIN_VALUE);
        localObject = this.mClose;
        int i2 = 0;
        paramInt2 = paramInt1;
        if (localObject != null)
        {
          paramInt1 = measureChildView((View)localObject, paramInt1, i1, 0);
          localObject = (ViewGroup.MarginLayoutParams)this.mClose.getLayoutParams();
          paramInt2 = paramInt1 - (((ViewGroup.MarginLayoutParams)localObject).leftMargin + ((ViewGroup.MarginLayoutParams)localObject).rightMargin);
        }
        paramInt1 = paramInt2;
        if (this.mMenuView != null)
        {
          paramInt1 = paramInt2;
          if (this.mMenuView.getParent() == this) {
            paramInt1 = measureChildView(this.mMenuView, paramInt2, i1, 0);
          }
        }
        localObject = this.mTitleLayout;
        paramInt2 = paramInt1;
        if (localObject != null)
        {
          paramInt2 = paramInt1;
          if (this.mCustomView == null) {
            if (this.mTitleOptional)
            {
              paramInt2 = View.MeasureSpec.makeMeasureSpec(0, 0);
              this.mTitleLayout.measure(paramInt2, i1);
              int i3 = this.mTitleLayout.getMeasuredWidth();
              if (i3 <= paramInt1) {
                i1 = 1;
              } else {
                i1 = 0;
              }
              paramInt2 = paramInt1;
              if (i1 != 0) {
                paramInt2 = paramInt1 - i3;
              }
              localObject = this.mTitleLayout;
              if (i1 != 0) {
                paramInt1 = 0;
              } else {
                paramInt1 = 8;
              }
              ((LinearLayout)localObject).setVisibility(paramInt1);
            }
            else
            {
              paramInt2 = measureChildView((View)localObject, paramInt1, i1, 0);
            }
          }
        }
        localObject = this.mCustomView;
        if (localObject != null)
        {
          localObject = ((View)localObject).getLayoutParams();
          if (((ViewGroup.LayoutParams)localObject).width != -2) {
            paramInt1 = 1073741824;
          } else {
            paramInt1 = Integer.MIN_VALUE;
          }
          i1 = paramInt2;
          if (((ViewGroup.LayoutParams)localObject).width >= 0) {
            i1 = Math.min(((ViewGroup.LayoutParams)localObject).width, paramInt2);
          }
          if (((ViewGroup.LayoutParams)localObject).height != -2) {
            paramInt2 = j;
          } else {
            paramInt2 = Integer.MIN_VALUE;
          }
          j = n;
          if (((ViewGroup.LayoutParams)localObject).height >= 0) {
            j = Math.min(((ViewGroup.LayoutParams)localObject).height, n);
          }
          this.mCustomView.measure(View.MeasureSpec.makeMeasureSpec(i1, paramInt1), View.MeasureSpec.makeMeasureSpec(j, paramInt2));
        }
        if (this.mContentHeight <= 0)
        {
          j = getChildCount();
          i = 0;
          paramInt1 = i2;
          while (paramInt1 < j)
          {
            i1 = getChildAt(paramInt1).getMeasuredHeight() + m;
            paramInt2 = i;
            if (i1 > i) {
              paramInt2 = i1;
            }
            paramInt1++;
            i = paramInt2;
          }
          setMeasuredDimension(k, i);
        }
        else
        {
          setMeasuredDimension(k, i);
        }
        return;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(getClass().getSimpleName());
      ((StringBuilder)localObject).append(" can only be used ");
      ((StringBuilder)localObject).append("with android:layout_height=\"wrap_content\"");
      throw new IllegalStateException(((StringBuilder)localObject).toString());
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(getClass().getSimpleName());
    ((StringBuilder)localObject).append(" can only be used ");
    ((StringBuilder)localObject).append("with android:layout_width=\"match_parent\" (or fill_parent)");
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public void setContentHeight(int paramInt)
  {
    this.mContentHeight = paramInt;
  }
  
  public void setCustomView(View paramView)
  {
    Object localObject = this.mCustomView;
    if (localObject != null) {
      removeView((View)localObject);
    }
    this.mCustomView = paramView;
    if (paramView != null)
    {
      localObject = this.mTitleLayout;
      if (localObject != null)
      {
        removeView((View)localObject);
        this.mTitleLayout = null;
      }
    }
    if (paramView != null) {
      addView(paramView);
    }
    requestLayout();
  }
  
  public void setSubtitle(CharSequence paramCharSequence)
  {
    this.mSubtitle = paramCharSequence;
    initTitle();
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    this.mTitle = paramCharSequence;
    initTitle();
  }
  
  public void setTitleOptional(boolean paramBoolean)
  {
    if (paramBoolean != this.mTitleOptional) {
      requestLayout();
    }
    this.mTitleOptional = paramBoolean;
  }
  
  public boolean shouldDelayChildPressedState()
  {
    return false;
  }
  
  public boolean showOverflowMenu()
  {
    if (this.mActionMenuPresenter != null) {
      return this.mActionMenuPresenter.f();
    }
    return false;
  }
}


/* Location:              ~/android/support/v7/widget/ActionBarContextView.class
 *
 * Reversed by:           J
 */