package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.v7.b.a.a;
import android.support.v7.b.a.b;

class z
  extends Drawable
{
  static a a;
  private static final double b = Math.cos(Math.toRadians(45.0D));
  private final int c;
  private Paint d;
  private Paint e;
  private Paint f;
  private final RectF g;
  private float h;
  private Path i;
  private float j;
  private float k;
  private float l;
  private ColorStateList m;
  private boolean n = true;
  private final int o;
  private final int p;
  private boolean q = true;
  private boolean r = false;
  
  z(Resources paramResources, ColorStateList paramColorStateList, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.o = paramResources.getColor(a.a.cardview_shadow_start_color);
    this.p = paramResources.getColor(a.a.cardview_shadow_end_color);
    this.c = paramResources.getDimensionPixelSize(a.b.cardview_compat_inset_shadow);
    this.d = new Paint(5);
    b(paramColorStateList);
    this.e = new Paint(5);
    this.e.setStyle(Paint.Style.FILL);
    this.h = ((int)(paramFloat1 + 0.5F));
    this.g = new RectF();
    this.f = new Paint(this.e);
    this.f.setAntiAlias(false);
    a(paramFloat2, paramFloat3);
  }
  
  static float a(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      double d1 = paramFloat1 * 1.5F;
      double d2 = b;
      double d3 = paramFloat2;
      Double.isNaN(d3);
      Double.isNaN(d1);
      return (float)(d1 + (1.0D - d2) * d3);
    }
    return paramFloat1 * 1.5F;
  }
  
  private void a(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 >= 0.0F)
    {
      if (paramFloat2 >= 0.0F)
      {
        float f1 = d(paramFloat1);
        paramFloat2 = d(paramFloat2);
        paramFloat1 = f1;
        if (f1 > paramFloat2)
        {
          if (!this.r) {
            this.r = true;
          }
          paramFloat1 = paramFloat2;
        }
        if ((this.l == paramFloat1) && (this.j == paramFloat2)) {
          return;
        }
        this.l = paramFloat1;
        this.j = paramFloat2;
        this.k = ((int)(paramFloat1 * 1.5F + this.c + 0.5F));
        this.n = true;
        invalidateSelf();
        return;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Invalid max shadow size ");
      localStringBuilder.append(paramFloat2);
      localStringBuilder.append(". Must be >= 0");
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Invalid shadow size ");
    localStringBuilder.append(paramFloat1);
    localStringBuilder.append(". Must be >= 0");
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  private void a(Canvas paramCanvas)
  {
    float f1 = this.h;
    float f2 = -f1 - this.k;
    float f3 = f1 + this.c + this.l / 2.0F;
    float f4 = this.g.width();
    f1 = f3 * 2.0F;
    if (f4 - f1 > 0.0F) {
      i1 = 1;
    } else {
      i1 = 0;
    }
    int i2;
    if (this.g.height() - f1 > 0.0F) {
      i2 = 1;
    } else {
      i2 = 0;
    }
    int i3 = paramCanvas.save();
    paramCanvas.translate(this.g.left + f3, this.g.top + f3);
    paramCanvas.drawPath(this.i, this.e);
    if (i1 != 0) {
      paramCanvas.drawRect(0.0F, f2, this.g.width() - f1, -this.h, this.f);
    }
    paramCanvas.restoreToCount(i3);
    i3 = paramCanvas.save();
    paramCanvas.translate(this.g.right - f3, this.g.bottom - f3);
    paramCanvas.rotate(180.0F);
    paramCanvas.drawPath(this.i, this.e);
    if (i1 != 0) {
      paramCanvas.drawRect(0.0F, f2, this.g.width() - f1, -this.h + this.k, this.f);
    }
    paramCanvas.restoreToCount(i3);
    int i1 = paramCanvas.save();
    paramCanvas.translate(this.g.left + f3, this.g.bottom - f3);
    paramCanvas.rotate(270.0F);
    paramCanvas.drawPath(this.i, this.e);
    if (i2 != 0) {
      paramCanvas.drawRect(0.0F, f2, this.g.height() - f1, -this.h, this.f);
    }
    paramCanvas.restoreToCount(i1);
    i1 = paramCanvas.save();
    paramCanvas.translate(this.g.right - f3, this.g.top + f3);
    paramCanvas.rotate(90.0F);
    paramCanvas.drawPath(this.i, this.e);
    if (i2 != 0) {
      paramCanvas.drawRect(0.0F, f2, this.g.height() - f1, -this.h, this.f);
    }
    paramCanvas.restoreToCount(i1);
  }
  
  static float b(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      double d1 = paramFloat1;
      double d2 = b;
      double d3 = paramFloat2;
      Double.isNaN(d3);
      Double.isNaN(d1);
      return (float)(d1 + (1.0D - d2) * d3);
    }
    return paramFloat1;
  }
  
  private void b(ColorStateList paramColorStateList)
  {
    ColorStateList localColorStateList = paramColorStateList;
    if (paramColorStateList == null) {
      localColorStateList = ColorStateList.valueOf(0);
    }
    this.m = localColorStateList;
    this.d.setColor(this.m.getColorForState(getState(), this.m.getDefaultColor()));
  }
  
  private void b(Rect paramRect)
  {
    float f1 = this.j * 1.5F;
    this.g.set(paramRect.left + this.j, paramRect.top + f1, paramRect.right - this.j, paramRect.bottom - f1);
    g();
  }
  
  private int d(float paramFloat)
  {
    int i1 = (int)(paramFloat + 0.5F);
    if (i1 % 2 == 1) {
      return i1 - 1;
    }
    return i1;
  }
  
  private void g()
  {
    float f1 = this.h;
    Object localObject1 = new RectF(-f1, -f1, f1, f1);
    RectF localRectF = new RectF((RectF)localObject1);
    f1 = this.k;
    localRectF.inset(-f1, -f1);
    Object localObject2 = this.i;
    if (localObject2 == null) {
      this.i = new Path();
    } else {
      ((Path)localObject2).reset();
    }
    this.i.setFillType(Path.FillType.EVEN_ODD);
    this.i.moveTo(-this.h, 0.0F);
    this.i.rLineTo(-this.k, 0.0F);
    this.i.arcTo(localRectF, 180.0F, 90.0F, false);
    this.i.arcTo((RectF)localObject1, 270.0F, -90.0F, false);
    this.i.close();
    float f2 = this.h;
    float f3 = this.k;
    f1 = f2 / (f2 + f3);
    localObject2 = this.e;
    int i1 = this.o;
    int i2 = this.p;
    localObject1 = Shader.TileMode.CLAMP;
    ((Paint)localObject2).setShader(new RadialGradient(0.0F, 0.0F, f2 + f3, new int[] { i1, i1, i2 }, new float[] { 0.0F, f1, 1.0F }, (Shader.TileMode)localObject1));
    localObject2 = this.f;
    f2 = this.h;
    f1 = -f2;
    f3 = this.k;
    f2 = -f2;
    i2 = this.o;
    i1 = this.p;
    localObject1 = Shader.TileMode.CLAMP;
    ((Paint)localObject2).setShader(new LinearGradient(0.0F, f1 + f3, 0.0F, f2 - f3, new int[] { i2, i2, i1 }, new float[] { 0.0F, 0.5F, 1.0F }, (Shader.TileMode)localObject1));
    this.f.setAntiAlias(false);
  }
  
  float a()
  {
    return this.h;
  }
  
  void a(float paramFloat)
  {
    if (paramFloat >= 0.0F)
    {
      paramFloat = (int)(paramFloat + 0.5F);
      if (this.h == paramFloat) {
        return;
      }
      this.h = paramFloat;
      this.n = true;
      invalidateSelf();
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Invalid radius ");
    localStringBuilder.append(paramFloat);
    localStringBuilder.append(". Must be >= 0");
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  void a(ColorStateList paramColorStateList)
  {
    b(paramColorStateList);
    invalidateSelf();
  }
  
  void a(Rect paramRect)
  {
    getPadding(paramRect);
  }
  
  void a(boolean paramBoolean)
  {
    this.q = paramBoolean;
    invalidateSelf();
  }
  
  float b()
  {
    return this.l;
  }
  
  void b(float paramFloat)
  {
    a(paramFloat, this.j);
  }
  
  float c()
  {
    return this.j;
  }
  
  void c(float paramFloat)
  {
    a(this.l, paramFloat);
  }
  
  float d()
  {
    float f1 = this.j;
    return Math.max(f1, this.h + this.c + f1 / 2.0F) * 2.0F + (this.j + this.c) * 2.0F;
  }
  
  public void draw(Canvas paramCanvas)
  {
    if (this.n)
    {
      b(getBounds());
      this.n = false;
    }
    paramCanvas.translate(0.0F, this.l / 2.0F);
    a(paramCanvas);
    paramCanvas.translate(0.0F, -this.l / 2.0F);
    a.a(paramCanvas, this.g, this.h, this.d);
  }
  
  float e()
  {
    float f1 = this.j;
    return Math.max(f1, this.h + this.c + f1 * 1.5F / 2.0F) * 2.0F + (this.j * 1.5F + this.c) * 2.0F;
  }
  
  ColorStateList f()
  {
    return this.m;
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  public boolean getPadding(Rect paramRect)
  {
    int i1 = (int)Math.ceil(a(this.j, this.h, this.q));
    int i2 = (int)Math.ceil(b(this.j, this.h, this.q));
    paramRect.set(i2, i1, i2, i1);
    return true;
  }
  
  public boolean isStateful()
  {
    ColorStateList localColorStateList = this.m;
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
    super.onBoundsChange(paramRect);
    this.n = true;
  }
  
  protected boolean onStateChange(int[] paramArrayOfInt)
  {
    ColorStateList localColorStateList = this.m;
    int i1 = localColorStateList.getColorForState(paramArrayOfInt, localColorStateList.getDefaultColor());
    if (this.d.getColor() == i1) {
      return false;
    }
    this.d.setColor(i1);
    this.n = true;
    invalidateSelf();
    return true;
  }
  
  public void setAlpha(int paramInt)
  {
    this.d.setAlpha(paramInt);
    this.e.setAlpha(paramInt);
    this.f.setAlpha(paramInt);
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.d.setColorFilter(paramColorFilter);
  }
  
  static abstract interface a
  {
    public abstract void a(Canvas paramCanvas, RectF paramRectF, float paramFloat, Paint paramPaint);
  }
}


/* Location:              ~/android/support/v7/widget/z.class
 *
 * Reversed by:           J
 */