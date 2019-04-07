package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.a.a.j;
import android.util.AttributeSet;
import android.widget.SeekBar;

class i
  extends h
{
  private final SeekBar a;
  private Drawable b;
  private ColorStateList c = null;
  private PorterDuff.Mode d = null;
  private boolean e = false;
  private boolean f = false;
  
  i(SeekBar paramSeekBar)
  {
    super(paramSeekBar);
    this.a = paramSeekBar;
  }
  
  private void d()
  {
    if ((this.b != null) && ((this.e) || (this.f)))
    {
      this.b = DrawableCompat.wrap(this.b.mutate());
      if (this.e) {
        DrawableCompat.setTintList(this.b, this.c);
      }
      if (this.f) {
        DrawableCompat.setTintMode(this.b, this.d);
      }
      if (this.b.isStateful()) {
        this.b.setState(this.a.getDrawableState());
      }
    }
  }
  
  void a(Canvas paramCanvas)
  {
    if (this.b != null)
    {
      int i = this.a.getMax();
      int j = 1;
      if (i > 1)
      {
        int k = this.b.getIntrinsicWidth();
        int m = this.b.getIntrinsicHeight();
        if (k >= 0) {
          k /= 2;
        } else {
          k = 1;
        }
        if (m >= 0) {
          j = m / 2;
        }
        this.b.setBounds(-k, -j, k, j);
        float f1 = (this.a.getWidth() - this.a.getPaddingLeft() - this.a.getPaddingRight()) / i;
        j = paramCanvas.save();
        paramCanvas.translate(this.a.getPaddingLeft(), this.a.getHeight() / 2);
        for (k = 0; k <= i; k++)
        {
          this.b.draw(paramCanvas);
          paramCanvas.translate(f1, 0.0F);
        }
        paramCanvas.restoreToCount(j);
      }
    }
  }
  
  void a(Drawable paramDrawable)
  {
    Drawable localDrawable = this.b;
    if (localDrawable != null) {
      localDrawable.setCallback(null);
    }
    this.b = paramDrawable;
    if (paramDrawable != null)
    {
      paramDrawable.setCallback(this.a);
      DrawableCompat.setLayoutDirection(paramDrawable, ViewCompat.getLayoutDirection(this.a));
      if (paramDrawable.isStateful()) {
        paramDrawable.setState(this.a.getDrawableState());
      }
      d();
    }
    this.a.invalidate();
  }
  
  void a(AttributeSet paramAttributeSet, int paramInt)
  {
    super.a(paramAttributeSet, paramInt);
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(this.a.getContext(), paramAttributeSet, a.j.AppCompatSeekBar, paramInt, 0);
    paramAttributeSet = localTintTypedArray.getDrawableIfKnown(a.j.AppCompatSeekBar_android_thumb);
    if (paramAttributeSet != null) {
      this.a.setThumb(paramAttributeSet);
    }
    a(localTintTypedArray.getDrawable(a.j.AppCompatSeekBar_tickMark));
    if (localTintTypedArray.hasValue(a.j.AppCompatSeekBar_tickMarkTintMode))
    {
      this.d = DrawableUtils.parseTintMode(localTintTypedArray.getInt(a.j.AppCompatSeekBar_tickMarkTintMode, -1), this.d);
      this.f = true;
    }
    if (localTintTypedArray.hasValue(a.j.AppCompatSeekBar_tickMarkTint))
    {
      this.c = localTintTypedArray.getColorStateList(a.j.AppCompatSeekBar_tickMarkTint);
      this.e = true;
    }
    localTintTypedArray.recycle();
    d();
  }
  
  void b()
  {
    Drawable localDrawable = this.b;
    if (localDrawable != null) {
      localDrawable.jumpToCurrentState();
    }
  }
  
  void c()
  {
    Drawable localDrawable = this.b;
    if ((localDrawable != null) && (localDrawable.isStateful()) && (localDrawable.setState(this.a.getDrawableState()))) {
      this.a.invalidateDrawable(localDrawable);
    }
  }
}


/* Location:              ~/android/support/v7/widget/i.class
 *
 * Reversed by:           J
 */