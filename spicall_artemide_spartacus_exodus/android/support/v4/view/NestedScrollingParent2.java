package android.support.v4.view;

import android.view.View;

public abstract interface NestedScrollingParent2
  extends NestedScrollingParent
{
  public abstract void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3);
  
  public abstract void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  public abstract void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt1, int paramInt2);
  
  public abstract boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt1, int paramInt2);
  
  public abstract void onStopNestedScroll(View paramView, int paramInt);
}


/* Location:              ~/android/support/v4/view/NestedScrollingParent2.class
 *
 * Reversed by:           J
 */