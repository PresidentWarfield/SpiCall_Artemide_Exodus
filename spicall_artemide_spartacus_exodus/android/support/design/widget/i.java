package android.support.design.widget;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.v7.d.a.a;

class i
  extends a
{
  static final double a = Math.cos(Math.toRadians(45.0D));
  final Paint b;
  final Paint c;
  final RectF d;
  float e;
  Path f;
  float g;
  float h;
  float i;
  float j;
  private boolean k;
  private final int l;
  private final int m;
  private final int n;
  private boolean o;
  private float p;
  private boolean q;
  
  public static float a(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      double d1 = paramFloat1 * 1.5F;
      double d2 = a;
      double d3 = paramFloat2;
      Double.isNaN(d3);
      Double.isNaN(d1);
      return (float)(d1 + (1.0D - d2) * d3);
    }
    return paramFloat1 * 1.5F;
  }
  
  private void a(Canvas paramCanvas)
  {
    int i1 = paramCanvas.save();
    paramCanvas.rotate(this.p, this.d.centerX(), this.d.centerY());
    float f1 = this.e;
    float f2 = -f1 - this.i;
    float f3 = this.d.width();
    float f4 = f1 * 2.0F;
    if (f3 - f4 > 0.0F) {
      i2 = 1;
    } else {
      i2 = 0;
    }
    int i3;
    if (this.d.height() - f4 > 0.0F) {
      i3 = 1;
    } else {
      i3 = 0;
    }
    f3 = this.j;
    float f5 = f1 / (f3 - 0.5F * f3 + f1);
    float f6 = f1 / (f3 - 0.25F * f3 + f1);
    f3 = f1 / (f3 - f3 * 1.0F + f1);
    int i4 = paramCanvas.save();
    paramCanvas.translate(this.d.left + f1, this.d.top + f1);
    paramCanvas.scale(f5, f6);
    paramCanvas.drawPath(this.f, this.b);
    if (i2 != 0)
    {
      paramCanvas.scale(1.0F / f5, 1.0F);
      paramCanvas.drawRect(0.0F, f2, this.d.width() - f4, -this.e, this.c);
    }
    paramCanvas.restoreToCount(i4);
    i4 = paramCanvas.save();
    paramCanvas.translate(this.d.right - f1, this.d.bottom - f1);
    paramCanvas.scale(f5, f3);
    paramCanvas.rotate(180.0F);
    paramCanvas.drawPath(this.f, this.b);
    if (i2 != 0)
    {
      paramCanvas.scale(1.0F / f5, 1.0F);
      paramCanvas.drawRect(0.0F, f2, this.d.width() - f4, -this.e + this.i, this.c);
    }
    paramCanvas.restoreToCount(i4);
    int i2 = paramCanvas.save();
    paramCanvas.translate(this.d.left + f1, this.d.bottom - f1);
    paramCanvas.scale(f5, f3);
    paramCanvas.rotate(270.0F);
    paramCanvas.drawPath(this.f, this.b);
    if (i3 != 0)
    {
      paramCanvas.scale(1.0F / f3, 1.0F);
      paramCanvas.drawRect(0.0F, f2, this.d.height() - f4, -this.e, this.c);
    }
    paramCanvas.restoreToCount(i2);
    i2 = paramCanvas.save();
    paramCanvas.translate(this.d.right - f1, this.d.top + f1);
    paramCanvas.scale(f5, f6);
    paramCanvas.rotate(90.0F);
    paramCanvas.drawPath(this.f, this.b);
    if (i3 != 0)
    {
      paramCanvas.scale(1.0F / f6, 1.0F);
      paramCanvas.drawRect(0.0F, f2, this.d.height() - f4, -this.e, this.c);
    }
    paramCanvas.restoreToCount(i2);
    paramCanvas.restoreToCount(i1);
  }
  
  private void a(Rect paramRect)
  {
    float f1 = this.h * 1.5F;
    this.d.set(paramRect.left + this.h, paramRect.top + f1, paramRect.right - this.h, paramRect.bottom - f1);
    b().setBounds((int)this.d.left, (int)this.d.top, (int)this.d.right, (int)this.d.bottom);
    c();
  }
  
  public static float b(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      double d1 = paramFloat1;
      double d2 = a;
      double d3 = paramFloat2;
      Double.isNaN(d3);
      Double.isNaN(d1);
      return (float)(d1 + (1.0D - d2) * d3);
    }
    return paramFloat1;
  }
  
  private static int c(float paramFloat)
  {
    int i1 = Math.round(paramFloat);
    int i2 = i1;
    if (i1 % 2 == 1) {
      i2 = i1 - 1;
    }
    return i2;
  }
  
  private void c()
  {
    float f1 = this.e;
    Object localObject1 = new RectF(-f1, -f1, f1, f1);
    RectF localRectF = new RectF((RectF)localObject1);
    f1 = this.i;
    localRectF.inset(-f1, -f1);
    Object localObject2 = this.f;
    if (localObject2 == null) {
      this.f = new Path();
    } else {
      ((Path)localObject2).reset();
    }
    this.f.setFillType(Path.FillType.EVEN_ODD);
    this.f.moveTo(-this.e, 0.0F);
    this.f.rLineTo(-this.i, 0.0F);
    this.f.arcTo(localRectF, 180.0F, 90.0F, false);
    this.f.arcTo((RectF)localObject1, 270.0F, -90.0F, false);
    this.f.close();
    f1 = -localRectF.top;
    if (f1 > 0.0F)
    {
      f2 = this.e / f1;
      float f3 = (1.0F - f2) / 2.0F;
      Paint localPaint = this.b;
      i1 = this.l;
      i2 = this.m;
      i3 = this.n;
      localObject2 = Shader.TileMode.CLAMP;
      localPaint.setShader(new RadialGradient(0.0F, 0.0F, f1, new int[] { 0, i1, i2, i3 }, new float[] { 0.0F, f2, f3 + f2, 1.0F }, (Shader.TileMode)localObject2));
    }
    localObject2 = this.c;
    f1 = ((RectF)localObject1).top;
    float f2 = localRectF.top;
    int i1 = this.l;
    int i2 = this.m;
    int i3 = this.n;
    localObject1 = Shader.TileMode.CLAMP;
    ((Paint)localObject2).setShader(new LinearGradient(0.0F, f1, 0.0F, f2, new int[] { i1, i2, i3 }, new float[] { 0.0F, 0.5F, 1.0F }, (Shader.TileMode)localObject1));
    this.c.setAntiAlias(false);
  }
  
  public float a()
  {
    return this.j;
  }
  
  final void a(float paramFloat)
  {
    if (this.p != paramFloat)
    {
      this.p = paramFloat;
      invalidateSelf();
    }
  }
  
  void a(float paramFloat1, float paramFloat2)
  {
    if ((paramFloat1 >= 0.0F) && (paramFloat2 >= 0.0F))
    {
      float f1 = c(paramFloat1);
      paramFloat2 = c(paramFloat2);
      paramFloat1 = f1;
      if (f1 > paramFloat2)
      {
        if (!this.q) {
          this.q = true;
        }
        paramFloat1 = paramFloat2;
      }
      if ((this.j == paramFloat1) && (this.h == paramFloat2)) {
        return;
      }
      this.j = paramFloat1;
      this.h = paramFloat2;
      this.i = Math.round(paramFloat1 * 1.5F);
      this.g = paramFloat2;
      this.k = true;
      invalidateSelf();
      return;
    }
    throw new IllegalArgumentException("invalid shadow size");
  }
  
  public void b(float paramFloat)
  {
    a(paramFloat, this.h);
  }
  
  public void draw(Canvas paramCanvas)
  {
    if (this.k)
    {
      a(getBounds());
      this.k = false;
    }
    a(paramCanvas);
    super.draw(paramCanvas);
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  public boolean getPadding(Rect paramRect)
  {
    int i1 = (int)Math.ceil(a(this.h, this.e, this.o));
    int i2 = (int)Math.ceil(b(this.h, this.e, this.o));
    paramRect.set(i2, i1, i2, i1);
    return true;
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    this.k = true;
  }
  
  public void setAlpha(int paramInt)
  {
    super.setAlpha(paramInt);
    this.b.setAlpha(paramInt);
    this.c.setAlpha(paramInt);
  }
}


/* Location:              ~/android/support/design/widget/i.class
 *
 * Reversed by:           J
 */