package android.support.v7.d.a;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.a.a.a;
import android.support.v7.a.a.i;
import android.support.v7.a.a.j;

public class b
  extends Drawable
{
  private static final float b = (float)Math.toRadians(45.0D);
  private final Paint a = new Paint();
  private float c;
  private float d;
  private float e;
  private float f;
  private boolean g;
  private final Path h = new Path();
  private final int i;
  private boolean j = false;
  private float k;
  private float l;
  private int m = 2;
  
  public b(Context paramContext)
  {
    this.a.setStyle(Paint.Style.STROKE);
    this.a.setStrokeJoin(Paint.Join.MITER);
    this.a.setStrokeCap(Paint.Cap.BUTT);
    this.a.setAntiAlias(true);
    paramContext = paramContext.getTheme().obtainStyledAttributes(null, a.j.DrawerArrowToggle, a.a.drawerArrowStyle, a.i.Base_Widget_AppCompat_DrawerArrowToggle);
    a(paramContext.getColor(a.j.DrawerArrowToggle_color, 0));
    a(paramContext.getDimension(a.j.DrawerArrowToggle_thickness, 0.0F));
    a(paramContext.getBoolean(a.j.DrawerArrowToggle_spinBars, true));
    b(Math.round(paramContext.getDimension(a.j.DrawerArrowToggle_gapBetweenBars, 0.0F)));
    this.i = paramContext.getDimensionPixelSize(a.j.DrawerArrowToggle_drawableSize, 0);
    this.d = Math.round(paramContext.getDimension(a.j.DrawerArrowToggle_barLength, 0.0F));
    this.c = Math.round(paramContext.getDimension(a.j.DrawerArrowToggle_arrowHeadLength, 0.0F));
    this.e = paramContext.getDimension(a.j.DrawerArrowToggle_arrowShaftLength, 0.0F);
    paramContext.recycle();
  }
  
  private static float a(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return paramFloat1 + (paramFloat2 - paramFloat1) * paramFloat3;
  }
  
  public void a(float paramFloat)
  {
    if (this.a.getStrokeWidth() != paramFloat)
    {
      this.a.setStrokeWidth(paramFloat);
      double d1 = paramFloat / 2.0F;
      double d2 = Math.cos(b);
      Double.isNaN(d1);
      this.l = ((float)(d1 * d2));
      invalidateSelf();
    }
  }
  
  public void a(int paramInt)
  {
    if (paramInt != this.a.getColor())
    {
      this.a.setColor(paramInt);
      invalidateSelf();
    }
  }
  
  public void a(boolean paramBoolean)
  {
    if (this.g != paramBoolean)
    {
      this.g = paramBoolean;
      invalidateSelf();
    }
  }
  
  public void b(float paramFloat)
  {
    if (paramFloat != this.f)
    {
      this.f = paramFloat;
      invalidateSelf();
    }
  }
  
  public void b(boolean paramBoolean)
  {
    if (this.j != paramBoolean)
    {
      this.j = paramBoolean;
      invalidateSelf();
    }
  }
  
  public void c(float paramFloat)
  {
    if (this.k != paramFloat)
    {
      this.k = paramFloat;
      invalidateSelf();
    }
  }
  
  public void draw(Canvas paramCanvas)
  {
    Rect localRect = getBounds();
    int n = this.m;
    int i1 = 0;
    int i2;
    if (n != 3) {
      i2 = i1;
    }
    switch (n)
    {
    default: 
      i2 = i1;
      if (DrawableCompat.getLayoutDirection(this) == 1) {
        i2 = 1;
      }
      break;
    case 1: 
      i2 = 1;
      break;
      i2 = i1;
      if (DrawableCompat.getLayoutDirection(this) == 0) {
        i2 = 1;
      }
      break;
    }
    float f1 = this.c;
    f1 = (float)Math.sqrt(f1 * f1 * 2.0F);
    float f2 = a(this.d, f1, this.k);
    float f3 = a(this.d, this.e, this.k);
    float f4 = Math.round(a(0.0F, this.l, this.k));
    float f5 = a(0.0F, b, this.k);
    if (i2 != 0) {
      f1 = 0.0F;
    } else {
      f1 = -180.0F;
    }
    if (i2 != 0) {
      f6 = 180.0F;
    } else {
      f6 = 0.0F;
    }
    f1 = a(f1, f6, this.k);
    double d1 = f2;
    double d2 = f5;
    double d3 = Math.cos(d2);
    Double.isNaN(d1);
    float f6 = (float)Math.round(d3 * d1);
    d2 = Math.sin(d2);
    Double.isNaN(d1);
    f2 = (float)Math.round(d1 * d2);
    this.h.rewind();
    f5 = a(this.f + this.a.getStrokeWidth(), -this.l, this.k);
    float f7 = -f3 / 2.0F;
    this.h.moveTo(f7 + f4, 0.0F);
    this.h.rLineTo(f3 - f4 * 2.0F, 0.0F);
    this.h.moveTo(f7, f5);
    this.h.rLineTo(f6, f2);
    this.h.moveTo(f7, -f5);
    this.h.rLineTo(f6, -f2);
    this.h.close();
    paramCanvas.save();
    f6 = this.a.getStrokeWidth();
    f4 = localRect.height();
    f3 = this.f;
    f4 = (int)(f4 - 3.0F * f6 - 2.0F * f3) / 4 * 2;
    paramCanvas.translate(localRect.centerX(), f4 + (f6 * 1.5F + f3));
    if (this.g)
    {
      if ((this.j ^ i2)) {
        i2 = -1;
      } else {
        i2 = 1;
      }
      paramCanvas.rotate(f1 * i2);
    }
    else if (i2 != 0)
    {
      paramCanvas.rotate(180.0F);
    }
    paramCanvas.drawPath(this.h, this.a);
    paramCanvas.restore();
  }
  
  public int getIntrinsicHeight()
  {
    return this.i;
  }
  
  public int getIntrinsicWidth()
  {
    return this.i;
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  public void setAlpha(int paramInt)
  {
    if (paramInt != this.a.getAlpha())
    {
      this.a.setAlpha(paramInt);
      invalidateSelf();
    }
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.a.setColorFilter(paramColorFilter);
    invalidateSelf();
  }
}


/* Location:              ~/android/support/v7/d/a/b.class
 *
 * Reversed by:           J
 */