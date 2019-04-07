package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

class y
  extends Drawable
{
  private float a;
  private final Paint b;
  private final RectF c;
  private final Rect d;
  private float e;
  private boolean f = false;
  private boolean g = true;
  private ColorStateList h;
  private PorterDuffColorFilter i;
  private ColorStateList j;
  private PorterDuff.Mode k = PorterDuff.Mode.SRC_IN;
  
  y(ColorStateList paramColorStateList, float paramFloat)
  {
    this.a = paramFloat;
    this.b = new Paint(5);
    b(paramColorStateList);
    this.c = new RectF();
    this.d = new Rect();
  }
  
  private PorterDuffColorFilter a(ColorStateList paramColorStateList, PorterDuff.Mode paramMode)
  {
    if ((paramColorStateList != null) && (paramMode != null)) {
      return new PorterDuffColorFilter(paramColorStateList.getColorForState(getState(), 0), paramMode);
    }
    return null;
  }
  
  private void a(Rect paramRect)
  {
    Rect localRect = paramRect;
    if (paramRect == null) {
      localRect = getBounds();
    }
    this.c.set(localRect.left, localRect.top, localRect.right, localRect.bottom);
    this.d.set(localRect);
    if (this.f)
    {
      float f1 = z.a(this.e, this.a, this.g);
      float f2 = z.b(this.e, this.a, this.g);
      this.d.inset((int)Math.ceil(f2), (int)Math.ceil(f1));
      this.c.set(this.d);
    }
  }
  
  private void b(ColorStateList paramColorStateList)
  {
    ColorStateList localColorStateList = paramColorStateList;
    if (paramColorStateList == null) {
      localColorStateList = ColorStateList.valueOf(0);
    }
    this.h = localColorStateList;
    this.b.setColor(this.h.getColorForState(getState(), this.h.getDefaultColor()));
  }
  
  float a()
  {
    return this.e;
  }
  
  void a(float paramFloat)
  {
    if (paramFloat == this.a) {
      return;
    }
    this.a = paramFloat;
    a(null);
    invalidateSelf();
  }
  
  void a(float paramFloat, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramFloat == this.e) && (this.f == paramBoolean1) && (this.g == paramBoolean2)) {
      return;
    }
    this.e = paramFloat;
    this.f = paramBoolean1;
    this.g = paramBoolean2;
    a(null);
    invalidateSelf();
  }
  
  public void a(ColorStateList paramColorStateList)
  {
    b(paramColorStateList);
    invalidateSelf();
  }
  
  public float b()
  {
    return this.a;
  }
  
  public ColorStateList c()
  {
    return this.h;
  }
  
  public void draw(Canvas paramCanvas)
  {
    Paint localPaint = this.b;
    int m;
    if ((this.i != null) && (localPaint.getColorFilter() == null))
    {
      localPaint.setColorFilter(this.i);
      m = 1;
    }
    else
    {
      m = 0;
    }
    RectF localRectF = this.c;
    float f1 = this.a;
    paramCanvas.drawRoundRect(localRectF, f1, f1, localPaint);
    if (m != 0) {
      localPaint.setColorFilter(null);
    }
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  public void getOutline(Outline paramOutline)
  {
    paramOutline.setRoundRect(this.d, this.a);
  }
  
  public boolean isStateful()
  {
    ColorStateList localColorStateList = this.j;
    if ((localColorStateList == null) || (!localColorStateList.isStateful()))
    {
      localColorStateList = this.h;
      if (((localColorStateList == null) || (!localColorStateList.isStateful())) && (!super.isStateful())) {}
    }
    else
    {
      return true;
    }
    boolean bool = false;
    return bool;
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    super.onBoundsChange(paramRect);
    a(paramRect);
  }
  
  protected boolean onStateChange(int[] paramArrayOfInt)
  {
    ColorStateList localColorStateList = this.h;
    int m = localColorStateList.getColorForState(paramArrayOfInt, localColorStateList.getDefaultColor());
    boolean bool;
    if (m != this.b.getColor()) {
      bool = true;
    } else {
      bool = false;
    }
    if (bool) {
      this.b.setColor(m);
    }
    localColorStateList = this.j;
    if (localColorStateList != null)
    {
      paramArrayOfInt = this.k;
      if (paramArrayOfInt != null)
      {
        this.i = a(localColorStateList, paramArrayOfInt);
        return true;
      }
    }
    return bool;
  }
  
  public void setAlpha(int paramInt)
  {
    this.b.setAlpha(paramInt);
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.b.setColorFilter(paramColorFilter);
  }
  
  public void setTintList(ColorStateList paramColorStateList)
  {
    this.j = paramColorStateList;
    this.i = a(this.j, this.k);
    invalidateSelf();
  }
  
  public void setTintMode(PorterDuff.Mode paramMode)
  {
    this.k = paramMode;
    this.i = a(this.j, this.k);
    invalidateSelf();
  }
}


/* Location:              ~/android/support/v7/widget/y.class
 *
 * Reversed by:           J
 */