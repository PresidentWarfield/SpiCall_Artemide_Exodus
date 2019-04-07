package android.support.v7.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.a.a.a;
import android.view.MotionEvent;
import android.view.View;

class s
  extends ListViewCompat
{
  private ViewPropertyAnimatorCompat mClickAnimation;
  private boolean mDrawsInPressedState;
  private boolean mHijackFocus;
  private boolean mListSelectionHidden;
  private ListViewAutoScrollHelper mScrollHelper;
  
  public s(Context paramContext, boolean paramBoolean)
  {
    super(paramContext, null, a.a.dropDownListViewStyle);
    this.mHijackFocus = paramBoolean;
    setCacheColorHint(0);
  }
  
  private void clearPressedItem()
  {
    this.mDrawsInPressedState = false;
    setPressed(false);
    drawableStateChanged();
    Object localObject = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
    if (localObject != null) {
      ((View)localObject).setPressed(false);
    }
    localObject = this.mClickAnimation;
    if (localObject != null)
    {
      ((ViewPropertyAnimatorCompat)localObject).cancel();
      this.mClickAnimation = null;
    }
  }
  
  private void clickPressedItem(View paramView, int paramInt)
  {
    performItemClick(paramView, paramInt, getItemIdAtPosition(paramInt));
  }
  
  private void setPressedItem(View paramView, int paramInt, float paramFloat1, float paramFloat2)
  {
    this.mDrawsInPressedState = true;
    if (Build.VERSION.SDK_INT >= 21) {
      drawableHotspotChanged(paramFloat1, paramFloat2);
    }
    if (!isPressed()) {
      setPressed(true);
    }
    layoutChildren();
    if (this.mMotionPosition != -1)
    {
      View localView = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
      if ((localView != null) && (localView != paramView) && (localView.isPressed())) {
        localView.setPressed(false);
      }
    }
    this.mMotionPosition = paramInt;
    float f1 = paramView.getLeft();
    float f2 = paramView.getTop();
    if (Build.VERSION.SDK_INT >= 21) {
      paramView.drawableHotspotChanged(paramFloat1 - f1, paramFloat2 - f2);
    }
    if (!paramView.isPressed()) {
      paramView.setPressed(true);
    }
    positionSelectorLikeTouchCompat(paramInt, paramView, paramFloat1, paramFloat2);
    setSelectorEnabled(false);
    refreshDrawableState();
  }
  
  public boolean hasFocus()
  {
    boolean bool;
    if ((!this.mHijackFocus) && (!super.hasFocus())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean hasWindowFocus()
  {
    boolean bool;
    if ((!this.mHijackFocus) && (!super.hasWindowFocus())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean isFocused()
  {
    boolean bool;
    if ((!this.mHijackFocus) && (!super.isFocused())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean isInTouchMode()
  {
    boolean bool;
    if (((this.mHijackFocus) && (this.mListSelectionHidden)) || (super.isInTouchMode())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean onForwardedEvent(MotionEvent paramMotionEvent, int paramInt)
  {
    int i = paramMotionEvent.getActionMasked();
    boolean bool;
    switch (i)
    {
    default: 
      paramInt = 0;
      bool = true;
      break;
    case 3: 
      paramInt = 0;
      bool = false;
      break;
    case 2: 
      bool = true;
      break;
    case 1: 
      bool = false;
    }
    int j = paramMotionEvent.findPointerIndex(paramInt);
    if (j < 0)
    {
      paramInt = 0;
      bool = false;
    }
    else
    {
      paramInt = (int)paramMotionEvent.getX(j);
      int k = (int)paramMotionEvent.getY(j);
      j = pointToPosition(paramInt, k);
      if (j == -1)
      {
        paramInt = 1;
      }
      else
      {
        View localView = getChildAt(j - getFirstVisiblePosition());
        setPressedItem(localView, j, paramInt, k);
        if (i == 1) {
          clickPressedItem(localView, j);
        }
        paramInt = 0;
        bool = true;
      }
    }
    if ((!bool) || (paramInt != 0)) {
      clearPressedItem();
    }
    if (bool)
    {
      if (this.mScrollHelper == null) {
        this.mScrollHelper = new ListViewAutoScrollHelper(this);
      }
      this.mScrollHelper.setEnabled(true);
      this.mScrollHelper.onTouch(this, paramMotionEvent);
    }
    else
    {
      paramMotionEvent = this.mScrollHelper;
      if (paramMotionEvent != null) {
        paramMotionEvent.setEnabled(false);
      }
    }
    return bool;
  }
  
  void setListSelectionHidden(boolean paramBoolean)
  {
    this.mListSelectionHidden = paramBoolean;
  }
  
  protected boolean touchModeDrawsInPressedStateCompat()
  {
    boolean bool;
    if ((!this.mDrawsInPressedState) && (!super.touchModeDrawsInPressedStateCompat())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
}


/* Location:              ~/android/support/v7/widget/s.class
 *
 * Reversed by:           J
 */