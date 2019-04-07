package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;

class n
  implements q
{
  private y j(p paramp)
  {
    return (y)paramp.c();
  }
  
  public float a(p paramp)
  {
    return j(paramp).a();
  }
  
  public void a() {}
  
  public void a(p paramp, float paramFloat)
  {
    j(paramp).a(paramFloat);
  }
  
  public void a(p paramp, Context paramContext, ColorStateList paramColorStateList, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    paramp.a(new y(paramColorStateList, paramFloat1));
    paramContext = paramp.d();
    paramContext.setClipToOutline(true);
    paramContext.setElevation(paramFloat2);
    b(paramp, paramFloat3);
  }
  
  public void a(p paramp, ColorStateList paramColorStateList)
  {
    j(paramp).a(paramColorStateList);
  }
  
  public float b(p paramp)
  {
    return d(paramp) * 2.0F;
  }
  
  public void b(p paramp, float paramFloat)
  {
    j(paramp).a(paramFloat, paramp.a(), paramp.b());
    f(paramp);
  }
  
  public float c(p paramp)
  {
    return d(paramp) * 2.0F;
  }
  
  public void c(p paramp, float paramFloat)
  {
    paramp.d().setElevation(paramFloat);
  }
  
  public float d(p paramp)
  {
    return j(paramp).b();
  }
  
  public float e(p paramp)
  {
    return paramp.d().getElevation();
  }
  
  public void f(p paramp)
  {
    if (!paramp.a())
    {
      paramp.a(0, 0, 0, 0);
      return;
    }
    float f1 = a(paramp);
    float f2 = d(paramp);
    int i = (int)Math.ceil(z.b(f1, f2, paramp.b()));
    int j = (int)Math.ceil(z.a(f1, f2, paramp.b()));
    paramp.a(i, j, i, j);
  }
  
  public void g(p paramp)
  {
    b(paramp, a(paramp));
  }
  
  public void h(p paramp)
  {
    b(paramp, a(paramp));
  }
  
  public ColorStateList i(p paramp)
  {
    return j(paramp).c();
  }
}


/* Location:              ~/android/support/v7/widget/n.class
 *
 * Reversed by:           J
 */