package android.support.v7.widget;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewParent;

public abstract class ForwardingListener
  implements View.OnAttachStateChangeListener, View.OnTouchListener
{
  private int mActivePointerId;
  private Runnable mDisallowIntercept;
  private boolean mForwarding;
  private final int mLongPressTimeout;
  private final float mScaledTouchSlop;
  final View mSrc;
  private final int mTapTimeout;
  private final int[] mTmpLocation = new int[2];
  private Runnable mTriggerLongPress;
  
  public ForwardingListener(View paramView)
  {
    this.mSrc = paramView;
    paramView.setLongClickable(true);
    paramView.addOnAttachStateChangeListener(this);
    this.mScaledTouchSlop = ViewConfiguration.get(paramView.getContext()).getScaledTouchSlop();
    this.mTapTimeout = ViewConfiguration.getTapTimeout();
    this.mLongPressTimeout = ((this.mTapTimeout + ViewConfiguration.getLongPressTimeout()) / 2);
  }
  
  private void clearCallbacks()
  {
    Runnable localRunnable = this.mTriggerLongPress;
    if (localRunnable != null) {
      this.mSrc.removeCallbacks(localRunnable);
    }
    localRunnable = this.mDisallowIntercept;
    if (localRunnable != null) {
      this.mSrc.removeCallbacks(localRunnable);
    }
  }
  
  private boolean onTouchForwarded(MotionEvent paramMotionEvent)
  {
    View localView = this.mSrc;
    Object localObject = getPopup();
    if ((localObject != null) && (((android.support.v7.view.menu.s)localObject).isShowing()))
    {
      s locals = (s)((android.support.v7.view.menu.s)localObject).getListView();
      if ((locals != null) && (locals.isShown()))
      {
        localObject = MotionEvent.obtainNoHistory(paramMotionEvent);
        toGlobalMotionEvent(localView, (MotionEvent)localObject);
        toLocalMotionEvent(locals, (MotionEvent)localObject);
        boolean bool1 = locals.onForwardedEvent((MotionEvent)localObject, this.mActivePointerId);
        ((MotionEvent)localObject).recycle();
        int i = paramMotionEvent.getActionMasked();
        boolean bool2 = true;
        if ((i != 1) && (i != 3)) {
          i = 1;
        } else {
          i = 0;
        }
        if ((!bool1) || (i == 0)) {
          bool2 = false;
        }
        return bool2;
      }
      return false;
    }
    return false;
  }
  
  private boolean onTouchObserved(MotionEvent paramMotionEvent)
  {
    View localView = this.mSrc;
    if (!localView.isEnabled()) {
      return false;
    }
    switch (paramMotionEvent.getActionMasked())
    {
    default: 
      break;
    case 2: 
      int i = paramMotionEvent.findPointerIndex(this.mActivePointerId);
      if ((i >= 0) && (!pointInView(localView, paramMotionEvent.getX(i), paramMotionEvent.getY(i), this.mScaledTouchSlop)))
      {
        clearCallbacks();
        localView.getParent().requestDisallowInterceptTouchEvent(true);
        return true;
      }
      break;
    case 1: 
    case 3: 
      clearCallbacks();
      break;
    case 0: 
      this.mActivePointerId = paramMotionEvent.getPointerId(0);
      if (this.mDisallowIntercept == null) {
        this.mDisallowIntercept = new a();
      }
      localView.postDelayed(this.mDisallowIntercept, this.mTapTimeout);
      if (this.mTriggerLongPress == null) {
        this.mTriggerLongPress = new b();
      }
      localView.postDelayed(this.mTriggerLongPress, this.mLongPressTimeout);
    }
    return false;
  }
  
  private static boolean pointInView(View paramView, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f = -paramFloat3;
    boolean bool;
    if ((paramFloat1 >= f) && (paramFloat2 >= f) && (paramFloat1 < paramView.getRight() - paramView.getLeft() + paramFloat3) && (paramFloat2 < paramView.getBottom() - paramView.getTop() + paramFloat3)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean toGlobalMotionEvent(View paramView, MotionEvent paramMotionEvent)
  {
    int[] arrayOfInt = this.mTmpLocation;
    paramView.getLocationOnScreen(arrayOfInt);
    paramMotionEvent.offsetLocation(arrayOfInt[0], arrayOfInt[1]);
    return true;
  }
  
  private boolean toLocalMotionEvent(View paramView, MotionEvent paramMotionEvent)
  {
    int[] arrayOfInt = this.mTmpLocation;
    paramView.getLocationOnScreen(arrayOfInt);
    paramMotionEvent.offsetLocation(-arrayOfInt[0], -arrayOfInt[1]);
    return true;
  }
  
  public abstract android.support.v7.view.menu.s getPopup();
  
  protected boolean onForwardingStarted()
  {
    android.support.v7.view.menu.s locals = getPopup();
    if ((locals != null) && (!locals.isShowing())) {
      locals.show();
    }
    return true;
  }
  
  protected boolean onForwardingStopped()
  {
    android.support.v7.view.menu.s locals = getPopup();
    if ((locals != null) && (locals.isShowing())) {
      locals.dismiss();
    }
    return true;
  }
  
  void onLongPress()
  {
    clearCallbacks();
    View localView = this.mSrc;
    if ((localView.isEnabled()) && (!localView.isLongClickable()))
    {
      if (!onForwardingStarted()) {
        return;
      }
      localView.getParent().requestDisallowInterceptTouchEvent(true);
      long l = SystemClock.uptimeMillis();
      MotionEvent localMotionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
      localView.onTouchEvent(localMotionEvent);
      localMotionEvent.recycle();
      this.mForwarding = true;
      return;
    }
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    boolean bool1 = this.mForwarding;
    boolean bool2 = true;
    boolean bool3;
    if (bool1)
    {
      if ((!onTouchForwarded(paramMotionEvent)) && (onForwardingStopped())) {
        bool3 = false;
      } else {
        bool3 = true;
      }
    }
    else
    {
      if ((onTouchObserved(paramMotionEvent)) && (onForwardingStarted())) {
        bool4 = true;
      } else {
        bool4 = false;
      }
      bool3 = bool4;
      if (bool4)
      {
        long l = SystemClock.uptimeMillis();
        paramView = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
        this.mSrc.onTouchEvent(paramView);
        paramView.recycle();
        bool3 = bool4;
      }
    }
    this.mForwarding = bool3;
    boolean bool4 = bool2;
    if (!bool3) {
      if (bool1) {
        bool4 = bool2;
      } else {
        bool4 = false;
      }
    }
    return bool4;
  }
  
  public void onViewAttachedToWindow(View paramView) {}
  
  public void onViewDetachedFromWindow(View paramView)
  {
    this.mForwarding = false;
    this.mActivePointerId = -1;
    paramView = this.mDisallowIntercept;
    if (paramView != null) {
      this.mSrc.removeCallbacks(paramView);
    }
  }
  
  private class a
    implements Runnable
  {
    a() {}
    
    public void run()
    {
      ViewParent localViewParent = ForwardingListener.this.mSrc.getParent();
      if (localViewParent != null) {
        localViewParent.requestDisallowInterceptTouchEvent(true);
      }
    }
  }
  
  private class b
    implements Runnable
  {
    b() {}
    
    public void run()
    {
      ForwardingListener.this.onLongPress();
    }
  }
}


/* Location:              ~/android/support/v7/widget/ForwardingListener.class
 *
 * Reversed by:           J
 */