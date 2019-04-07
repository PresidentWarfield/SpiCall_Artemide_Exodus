package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.a.a.j;
import android.support.v7.c.a.b;
import android.util.AttributeSet;
import android.widget.CompoundButton;

class f
{
  private final CompoundButton a;
  private ColorStateList b = null;
  private PorterDuff.Mode c = null;
  private boolean d = false;
  private boolean e = false;
  private boolean f;
  
  f(CompoundButton paramCompoundButton)
  {
    this.a = paramCompoundButton;
  }
  
  int a(int paramInt)
  {
    int i = paramInt;
    if (Build.VERSION.SDK_INT < 17)
    {
      Drawable localDrawable = CompoundButtonCompat.getButtonDrawable(this.a);
      i = paramInt;
      if (localDrawable != null) {
        i = paramInt + localDrawable.getIntrinsicWidth();
      }
    }
    return i;
  }
  
  ColorStateList a()
  {
    return this.b;
  }
  
  void a(ColorStateList paramColorStateList)
  {
    this.b = paramColorStateList;
    this.d = true;
    d();
  }
  
  void a(PorterDuff.Mode paramMode)
  {
    this.c = paramMode;
    this.e = true;
    d();
  }
  
  void a(AttributeSet paramAttributeSet, int paramInt)
  {
    TypedArray localTypedArray = this.a.getContext().obtainStyledAttributes(paramAttributeSet, a.j.CompoundButton, paramInt, 0);
    try
    {
      if (localTypedArray.hasValue(a.j.CompoundButton_android_button))
      {
        paramInt = localTypedArray.getResourceId(a.j.CompoundButton_android_button, 0);
        if (paramInt != 0) {
          this.a.setButtonDrawable(b.b(this.a.getContext(), paramInt));
        }
      }
      if (localTypedArray.hasValue(a.j.CompoundButton_buttonTint)) {
        CompoundButtonCompat.setButtonTintList(this.a, localTypedArray.getColorStateList(a.j.CompoundButton_buttonTint));
      }
      if (localTypedArray.hasValue(a.j.CompoundButton_buttonTintMode)) {
        CompoundButtonCompat.setButtonTintMode(this.a, DrawableUtils.parseTintMode(localTypedArray.getInt(a.j.CompoundButton_buttonTintMode, -1), null));
      }
      return;
    }
    finally
    {
      localTypedArray.recycle();
    }
  }
  
  PorterDuff.Mode b()
  {
    return this.c;
  }
  
  void c()
  {
    if (this.f)
    {
      this.f = false;
      return;
    }
    this.f = true;
    d();
  }
  
  void d()
  {
    Drawable localDrawable = CompoundButtonCompat.getButtonDrawable(this.a);
    if ((localDrawable != null) && ((this.d) || (this.e)))
    {
      localDrawable = DrawableCompat.wrap(localDrawable).mutate();
      if (this.d) {
        DrawableCompat.setTintList(localDrawable, this.b);
      }
      if (this.e) {
        DrawableCompat.setTintMode(localDrawable, this.c);
      }
      if (localDrawable.isStateful()) {
        localDrawable.setState(this.a.getDrawableState());
      }
      this.a.setButtonDrawable(localDrawable);
    }
  }
}


/* Location:              ~/android/support/v7/widget/f.class
 *
 * Reversed by:           J
 */