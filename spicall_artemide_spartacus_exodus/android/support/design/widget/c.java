package android.support.design.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.ColorUtils;

class c
  extends Drawable
{
  final Paint a;
  final Rect b;
  final RectF c;
  float d;
  private int e;
  private int f;
  private int g;
  private int h;
  private ColorStateList i;
  private int j;
  private boolean k;
  private float l;
  
  private Shader a()
  {
    Object localObject = this.b;
    copyBounds((Rect)localObject);
    float f1 = this.d / ((Rect)localObject).height();
    int m = ColorUtils.compositeColors(this.e, this.j);
    int n = ColorUtils.compositeColors(this.f, this.j);
    int i1 = ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.f, 0), this.j);
    int i2 = ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.h, 0), this.j);
    int i3 = ColorUtils.compositeColors(this.h, this.j);
    int i4 = ColorUtils.compositeColors(this.g, this.j);
    float f2 = ((Rect)localObject).top;
    float f3 = ((Rect)localObject).bottom;
    localObject = Shader.TileMode.CLAMP;
    return new LinearGradient(0.0F, f2, 0.0F, f3, new int[] { m, n, i1, i2, i3, i4 }, new float[] { 0.0F, f1, 0.5F, 0.5F, 1.0F - f1, 1.0F }, (Shader.TileMode)localObject);
  }
  
  final void a(float paramFloat)
  {
    if (paramFloat != this.l)
    {
      this.l = paramFloat;
      invalidateSelf();
    }
  }
  
  void a(ColorStateList paramColorStateList)
  {
    if (paramColorStateList != null) {
      this.j = paramColorStateList.getColorForState(getState(), this.j);
    }
    this.i = paramColorStateList;
    this.k = true;
    invalidateSelf();
  }
  
  public void draw(Canvas paramCanvas)
  {
    if (this.k)
    {
      this.a.setShader(a());
      this.k = false;
    }
    float f1 = this.a.getStrokeWidth() / 2.0F;
    RectF localRectF = this.c;
    copyBounds(this.b);
    localRectF.set(this.b);
    localRectF.left += f1;
    localRectF.top += f1;
    localRectF.right -= f1;
    localRectF.bottom -= f1;
    paramCanvas.save();
    paramCanvas.rotate(this.l, localRectF.centerX(), localRectF.centerY());
    paramCanvas.drawOval(localRectF, this.a);
    paramCanvas.restore();
  }
  
  public int getOpacity()
  {
    int m;
    if (this.d > 0.0F) {
      m = -3;
    } else {
      m = -2;
    }
    return m;
  }
  
  public boolean getPadding(Rect paramRect)
  {
    int m = Math.round(this.d);
    paramRect.set(m, m, m, m);
    return true;
  }
  
  public boolean isStateful()
  {
    ColorStateList localColorStateList = this.i;
    boolean bool;
    if (((localColorStateList != null) && (localColorStateList.isStateful())) || (super.isStateful())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    this.k = true;
  }
  
  protected boolean onStateChange(int[] paramArrayOfInt)
  {
    ColorStateList localColorStateList = this.i;
    if (localColorStateList != null)
    {
      int m = localColorStateList.getColorForState(paramArrayOfInt, this.j);
      if (m != this.j)
      {
        this.k = true;
        this.j = m;
      }
    }
    if (this.k) {
      invalidateSelf();
    }
    return this.k;
  }
  
  public void setAlpha(int paramInt)
  {
    this.a.setAlpha(paramInt);
    invalidateSelf();
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.a.setColorFilter(paramColorFilter);
    invalidateSelf();
  }
}


/* Location:              ~/android/support/design/widget/c.class
 *
 * Reversed by:           J
 */