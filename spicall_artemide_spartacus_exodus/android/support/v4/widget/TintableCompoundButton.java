package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;

public abstract interface TintableCompoundButton
{
  public abstract ColorStateList getSupportButtonTintList();
  
  public abstract PorterDuff.Mode getSupportButtonTintMode();
  
  public abstract void setSupportButtonTintList(ColorStateList paramColorStateList);
  
  public abstract void setSupportButtonTintMode(PorterDuff.Mode paramMode);
}


/* Location:              ~/android/support/v4/widget/TintableCompoundButton.class
 *
 * Reversed by:           J
 */