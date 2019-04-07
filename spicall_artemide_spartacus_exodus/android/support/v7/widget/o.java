package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

class o
  implements q
{
  private final RectF a = new RectF();
  
  private z a(Context paramContext, ColorStateList paramColorStateList, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return new z(paramContext.getResources(), paramColorStateList, paramFloat1, paramFloat2, paramFloat3);
  }
  
  private z j(p paramp)
  {
    return (z)paramp.c();
  }
  
  public float a(p paramp)
  {
    return j(paramp).c();
  }
  
  public void a()
  {
    z.a = new z.a()
    {
      public void a(Canvas paramAnonymousCanvas, RectF paramAnonymousRectF, float paramAnonymousFloat, Paint paramAnonymousPaint)
      {
        float f1 = 2.0F * paramAnonymousFloat;
        float f2 = paramAnonymousRectF.width() - f1 - 1.0F;
        float f3 = paramAnonymousRectF.height();
        if (paramAnonymousFloat >= 1.0F)
        {
          float f4 = paramAnonymousFloat + 0.5F;
          RectF localRectF = o.a(o.this);
          float f5 = -f4;
          localRectF.set(f5, f5, f4, f4);
          int i = paramAnonymousCanvas.save();
          paramAnonymousCanvas.translate(paramAnonymousRectF.left + f4, paramAnonymousRectF.top + f4);
          paramAnonymousCanvas.drawArc(o.a(o.this), 180.0F, 90.0F, true, paramAnonymousPaint);
          paramAnonymousCanvas.translate(f2, 0.0F);
          paramAnonymousCanvas.rotate(90.0F);
          paramAnonymousCanvas.drawArc(o.a(o.this), 180.0F, 90.0F, true, paramAnonymousPaint);
          paramAnonymousCanvas.translate(f3 - f1 - 1.0F, 0.0F);
          paramAnonymousCanvas.rotate(90.0F);
          paramAnonymousCanvas.drawArc(o.a(o.this), 180.0F, 90.0F, true, paramAnonymousPaint);
          paramAnonymousCanvas.translate(f2, 0.0F);
          paramAnonymousCanvas.rotate(90.0F);
          paramAnonymousCanvas.drawArc(o.a(o.this), 180.0F, 90.0F, true, paramAnonymousPaint);
          paramAnonymousCanvas.restoreToCount(i);
          paramAnonymousCanvas.drawRect(paramAnonymousRectF.left + f4 - 1.0F, paramAnonymousRectF.top, paramAnonymousRectF.right - f4 + 1.0F, paramAnonymousRectF.top + f4, paramAnonymousPaint);
          paramAnonymousCanvas.drawRect(paramAnonymousRectF.left + f4 - 1.0F, paramAnonymousRectF.bottom - f4, paramAnonymousRectF.right - f4 + 1.0F, paramAnonymousRectF.bottom, paramAnonymousPaint);
        }
        paramAnonymousCanvas.drawRect(paramAnonymousRectF.left, paramAnonymousRectF.top + paramAnonymousFloat, paramAnonymousRectF.right, paramAnonymousRectF.bottom - paramAnonymousFloat, paramAnonymousPaint);
      }
    };
  }
  
  public void a(p paramp, float paramFloat)
  {
    j(paramp).a(paramFloat);
    f(paramp);
  }
  
  public void a(p paramp, Context paramContext, ColorStateList paramColorStateList, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    paramContext = a(paramContext, paramColorStateList, paramFloat1, paramFloat2, paramFloat3);
    paramContext.a(paramp.b());
    paramp.a(paramContext);
    f(paramp);
  }
  
  public void a(p paramp, ColorStateList paramColorStateList)
  {
    j(paramp).a(paramColorStateList);
  }
  
  public float b(p paramp)
  {
    return j(paramp).d();
  }
  
  public void b(p paramp, float paramFloat)
  {
    j(paramp).c(paramFloat);
    f(paramp);
  }
  
  public float c(p paramp)
  {
    return j(paramp).e();
  }
  
  public void c(p paramp, float paramFloat)
  {
    j(paramp).b(paramFloat);
  }
  
  public float d(p paramp)
  {
    return j(paramp).a();
  }
  
  public float e(p paramp)
  {
    return j(paramp).b();
  }
  
  public void f(p paramp)
  {
    Rect localRect = new Rect();
    j(paramp).a(localRect);
    paramp.a((int)Math.ceil(b(paramp)), (int)Math.ceil(c(paramp)));
    paramp.a(localRect.left, localRect.top, localRect.right, localRect.bottom);
  }
  
  public void g(p paramp) {}
  
  public void h(p paramp)
  {
    j(paramp).a(paramp.b());
    f(paramp);
  }
  
  public ColorStateList i(p paramp)
  {
    return j(paramp).f();
  }
}


/* Location:              ~/android/support/v7/widget/o.class
 *
 * Reversed by:           J
 */