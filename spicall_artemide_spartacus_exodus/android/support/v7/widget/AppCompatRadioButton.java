package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.TintableCompoundButton;
import android.support.v7.a.a.a;
import android.support.v7.c.a.b;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class AppCompatRadioButton
  extends RadioButton
  implements TintableCompoundButton
{
  private final f mCompoundButtonHelper = new f(this);
  
  public AppCompatRadioButton(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AppCompatRadioButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, a.a.radioButtonStyle);
  }
  
  public AppCompatRadioButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(TintContextWrapper.wrap(paramContext), paramAttributeSet, paramInt);
    this.mCompoundButtonHelper.a(paramAttributeSet, paramInt);
  }
  
  public int getCompoundPaddingLeft()
  {
    int i = super.getCompoundPaddingLeft();
    f localf = this.mCompoundButtonHelper;
    int j = i;
    if (localf != null) {
      j = localf.a(i);
    }
    return j;
  }
  
  public ColorStateList getSupportButtonTintList()
  {
    Object localObject = this.mCompoundButtonHelper;
    if (localObject != null) {
      localObject = ((f)localObject).a();
    } else {
      localObject = null;
    }
    return (ColorStateList)localObject;
  }
  
  public PorterDuff.Mode getSupportButtonTintMode()
  {
    Object localObject = this.mCompoundButtonHelper;
    if (localObject != null) {
      localObject = ((f)localObject).b();
    } else {
      localObject = null;
    }
    return (PorterDuff.Mode)localObject;
  }
  
  public void setButtonDrawable(int paramInt)
  {
    setButtonDrawable(b.b(getContext(), paramInt));
  }
  
  public void setButtonDrawable(Drawable paramDrawable)
  {
    super.setButtonDrawable(paramDrawable);
    paramDrawable = this.mCompoundButtonHelper;
    if (paramDrawable != null) {
      paramDrawable.c();
    }
  }
  
  public void setSupportButtonTintList(ColorStateList paramColorStateList)
  {
    f localf = this.mCompoundButtonHelper;
    if (localf != null) {
      localf.a(paramColorStateList);
    }
  }
  
  public void setSupportButtonTintMode(PorterDuff.Mode paramMode)
  {
    f localf = this.mCompoundButtonHelper;
    if (localf != null) {
      localf.a(paramMode);
    }
  }
}


/* Location:              ~/android/support/v7/widget/AppCompatRadioButton.class
 *
 * Reversed by:           J
 */