package android.support.constraint;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class d
  extends ViewGroup
{
  c a;
  
  protected a a()
  {
    return new a(-2, -2);
  }
  
  public a a(AttributeSet paramAttributeSet)
  {
    return new a(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new ConstraintLayout.a(paramLayoutParams);
  }
  
  public c getConstraintSet()
  {
    if (this.a == null) {
      this.a = new c();
    }
    this.a.a(this);
    return this.a;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public static class a
    extends ConstraintLayout.a
  {
    public float an = 1.0F;
    public boolean ao;
    public float ap;
    public float aq;
    public float ar;
    public float as;
    public float at;
    public float au;
    public float av;
    public float aw;
    public float ax;
    public float ay;
    public float az;
    
    public a(int paramInt1, int paramInt2)
    {
      super(paramInt2);
      this.ao = false;
      this.ap = 0.0F;
      this.aq = 0.0F;
      this.ar = 0.0F;
      this.as = 0.0F;
      this.at = 1.0F;
      this.au = 1.0F;
      this.av = 0.0F;
      this.aw = 0.0F;
      this.ax = 0.0F;
      this.ay = 0.0F;
      this.az = 0.0F;
    }
    
    public a(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      int i = 0;
      this.ao = false;
      this.ap = 0.0F;
      this.aq = 0.0F;
      this.ar = 0.0F;
      this.as = 0.0F;
      this.at = 1.0F;
      this.au = 1.0F;
      this.av = 0.0F;
      this.aw = 0.0F;
      this.ax = 0.0F;
      this.ay = 0.0F;
      this.az = 0.0F;
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, g.b.ConstraintSet);
      int j = paramContext.getIndexCount();
      while (i < j)
      {
        int k = paramContext.getIndex(i);
        if (k == g.b.ConstraintSet_android_alpha)
        {
          this.an = paramContext.getFloat(k, this.an);
        }
        else if (k == g.b.ConstraintSet_android_elevation)
        {
          this.ap = paramContext.getFloat(k, this.ap);
          this.ao = true;
        }
        else if (k == g.b.ConstraintSet_android_rotationX)
        {
          this.ar = paramContext.getFloat(k, this.ar);
        }
        else if (k == g.b.ConstraintSet_android_rotationY)
        {
          this.as = paramContext.getFloat(k, this.as);
        }
        else if (k == g.b.ConstraintSet_android_rotation)
        {
          this.aq = paramContext.getFloat(k, this.aq);
        }
        else if (k == g.b.ConstraintSet_android_scaleX)
        {
          this.at = paramContext.getFloat(k, this.at);
        }
        else if (k == g.b.ConstraintSet_android_scaleY)
        {
          this.au = paramContext.getFloat(k, this.au);
        }
        else if (k == g.b.ConstraintSet_android_transformPivotX)
        {
          this.av = paramContext.getFloat(k, this.av);
        }
        else if (k == g.b.ConstraintSet_android_transformPivotY)
        {
          this.aw = paramContext.getFloat(k, this.aw);
        }
        else if (k == g.b.ConstraintSet_android_translationX)
        {
          this.ax = paramContext.getFloat(k, this.ax);
        }
        else if (k == g.b.ConstraintSet_android_translationY)
        {
          this.ay = paramContext.getFloat(k, this.ay);
        }
        else if (k == g.b.ConstraintSet_android_translationZ)
        {
          this.ax = paramContext.getFloat(k, this.az);
        }
        i++;
      }
    }
  }
}


/* Location:              ~/android/support/constraint/d.class
 *
 * Reversed by:           J
 */