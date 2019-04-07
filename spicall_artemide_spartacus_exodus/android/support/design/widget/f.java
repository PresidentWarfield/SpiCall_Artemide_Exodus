package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.AnimatorSet.Builder;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

class f
  extends e
{
  private InsetDrawable p;
  
  f(r paramr, j paramj)
  {
    super(paramr, paramj);
  }
  
  public float a()
  {
    return this.n.getElevation();
  }
  
  void a(float paramFloat1, float paramFloat2)
  {
    if (Build.VERSION.SDK_INT == 21)
    {
      if (this.n.isEnabled())
      {
        this.n.setElevation(paramFloat1);
        if ((!this.n.isFocused()) && (!this.n.isPressed())) {
          this.n.setTranslationZ(0.0F);
        } else {
          this.n.setTranslationZ(paramFloat2);
        }
      }
      else
      {
        this.n.setElevation(0.0F);
        this.n.setTranslationZ(0.0F);
      }
    }
    else
    {
      StateListAnimator localStateListAnimator = new StateListAnimator();
      Object localObject = new AnimatorSet();
      ((AnimatorSet)localObject).play(ObjectAnimator.ofFloat(this.n, "elevation", new float[] { paramFloat1 }).setDuration(0L)).with(ObjectAnimator.ofFloat(this.n, View.TRANSLATION_Z, new float[] { paramFloat2 }).setDuration(100L));
      ((AnimatorSet)localObject).setInterpolator(a);
      localStateListAnimator.addState(j, (Animator)localObject);
      localObject = new AnimatorSet();
      ((AnimatorSet)localObject).play(ObjectAnimator.ofFloat(this.n, "elevation", new float[] { paramFloat1 }).setDuration(0L)).with(ObjectAnimator.ofFloat(this.n, View.TRANSLATION_Z, new float[] { paramFloat2 }).setDuration(100L));
      ((AnimatorSet)localObject).setInterpolator(a);
      localStateListAnimator.addState(k, (Animator)localObject);
      AnimatorSet localAnimatorSet = new AnimatorSet();
      localObject = new ArrayList();
      ((List)localObject).add(ObjectAnimator.ofFloat(this.n, "elevation", new float[] { paramFloat1 }).setDuration(0L));
      if ((Build.VERSION.SDK_INT >= 22) && (Build.VERSION.SDK_INT <= 24)) {
        ((List)localObject).add(ObjectAnimator.ofFloat(this.n, View.TRANSLATION_Z, new float[] { this.n.getTranslationZ() }).setDuration(100L));
      }
      ((List)localObject).add(ObjectAnimator.ofFloat(this.n, View.TRANSLATION_Z, new float[] { 0.0F }).setDuration(100L));
      localAnimatorSet.playSequentially((Animator[])((List)localObject).toArray(new ObjectAnimator[0]));
      localAnimatorSet.setInterpolator(a);
      localStateListAnimator.addState(l, localAnimatorSet);
      localObject = new AnimatorSet();
      ((AnimatorSet)localObject).play(ObjectAnimator.ofFloat(this.n, "elevation", new float[] { 0.0F }).setDuration(0L)).with(ObjectAnimator.ofFloat(this.n, View.TRANSLATION_Z, new float[] { 0.0F }).setDuration(0L));
      ((AnimatorSet)localObject).setInterpolator(a);
      localStateListAnimator.addState(m, (Animator)localObject);
      this.n.setStateListAnimator(localStateListAnimator);
    }
    if (this.o.b()) {
      e();
    }
  }
  
  void a(int paramInt)
  {
    if ((this.e instanceof RippleDrawable)) {
      ((RippleDrawable)this.e).setColor(ColorStateList.valueOf(paramInt));
    } else {
      super.a(paramInt);
    }
  }
  
  void a(Rect paramRect)
  {
    if (this.o.b())
    {
      float f1 = this.o.a();
      float f2 = a() + this.i;
      int i = (int)Math.ceil(i.b(f2, f1, false));
      int j = (int)Math.ceil(i.a(f2, f1, false));
      paramRect.set(i, j, i, j);
    }
    else
    {
      paramRect.set(0, 0, 0, 0);
    }
  }
  
  void a(int[] paramArrayOfInt) {}
  
  void b() {}
  
  void b(Rect paramRect)
  {
    if (this.o.b())
    {
      this.p = new InsetDrawable(this.e, paramRect.left, paramRect.top, paramRect.right, paramRect.bottom);
      this.o.a(this.p);
    }
    else
    {
      this.o.a(this.e);
    }
  }
  
  void d()
  {
    e();
  }
  
  boolean h()
  {
    return false;
  }
}


/* Location:              ~/android/support/design/widget/f.class
 *
 * Reversed by:           J
 */