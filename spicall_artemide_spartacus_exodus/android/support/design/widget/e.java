package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Interpolator;

class e
{
  static final Interpolator a = a.c;
  static final int[] j = { 16842919, 16842910 };
  static final int[] k = { 16842908, 16842910 };
  static final int[] l = { 16842910 };
  static final int[] m = new int[0];
  int b = 0;
  i c;
  Drawable d;
  Drawable e;
  c f;
  Drawable g;
  float h;
  float i;
  final r n;
  final j o;
  private final l p;
  private float q;
  private final Rect r = new Rect();
  private ViewTreeObserver.OnPreDrawListener s;
  
  e(r paramr, j paramj)
  {
    this.n = paramr;
    this.o = paramj;
    this.p = new l();
    this.p.a(j, a(new b()));
    this.p.a(k, a(new b()));
    this.p.a(l, a(new d()));
    this.p.a(m, a(new a()));
    this.q = this.n.getRotation();
  }
  
  private ValueAnimator a(e parame)
  {
    ValueAnimator localValueAnimator = new ValueAnimator();
    localValueAnimator.setInterpolator(a);
    localValueAnimator.setDuration(100L);
    localValueAnimator.addListener(parame);
    localValueAnimator.addUpdateListener(parame);
    localValueAnimator.setFloatValues(new float[] { 0.0F, 1.0F });
    return localValueAnimator;
  }
  
  private static ColorStateList b(int paramInt)
  {
    return new ColorStateList(new int[][] { k, j, new int[0] }, new int[] { paramInt, paramInt, 0 });
  }
  
  private void l()
  {
    if (this.s == null) {
      this.s = new ViewTreeObserver.OnPreDrawListener()
      {
        public boolean onPreDraw()
        {
          e.this.i();
          return true;
        }
      };
    }
  }
  
  private boolean m()
  {
    boolean bool;
    if ((ViewCompat.isLaidOut(this.n)) && (!this.n.isInEditMode())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void n()
  {
    if (Build.VERSION.SDK_INT == 19) {
      if (this.q % 90.0F != 0.0F)
      {
        if (this.n.getLayerType() != 1) {
          this.n.setLayerType(1, null);
        }
      }
      else if (this.n.getLayerType() != 0) {
        this.n.setLayerType(0, null);
      }
    }
    Object localObject = this.c;
    if (localObject != null) {
      ((i)localObject).a(-this.q);
    }
    localObject = this.f;
    if (localObject != null) {
      ((c)localObject).a(-this.q);
    }
  }
  
  float a()
  {
    return this.h;
  }
  
  final void a(float paramFloat)
  {
    if (this.h != paramFloat)
    {
      this.h = paramFloat;
      a(paramFloat, this.i);
    }
  }
  
  void a(float paramFloat1, float paramFloat2)
  {
    i locali = this.c;
    if (locali != null)
    {
      locali.a(paramFloat1, this.i + paramFloat1);
      e();
    }
  }
  
  void a(int paramInt)
  {
    Drawable localDrawable = this.e;
    if (localDrawable != null) {
      DrawableCompat.setTintList(localDrawable, b(paramInt));
    }
  }
  
  void a(ColorStateList paramColorStateList)
  {
    Object localObject = this.d;
    if (localObject != null) {
      DrawableCompat.setTintList((Drawable)localObject, paramColorStateList);
    }
    localObject = this.f;
    if (localObject != null) {
      ((c)localObject).a(paramColorStateList);
    }
  }
  
  void a(PorterDuff.Mode paramMode)
  {
    Drawable localDrawable = this.d;
    if (localDrawable != null) {
      DrawableCompat.setTintMode(localDrawable, paramMode);
    }
  }
  
  void a(Rect paramRect)
  {
    this.c.getPadding(paramRect);
  }
  
  void a(final c paramc, final boolean paramBoolean)
  {
    if (k()) {
      return;
    }
    this.n.animate().cancel();
    if (m())
    {
      this.b = 1;
      this.n.animate().scaleX(0.0F).scaleY(0.0F).alpha(0.0F).setDuration(200L).setInterpolator(a.c).setListener(new AnimatorListenerAdapter()
      {
        private boolean d;
        
        public void onAnimationCancel(Animator paramAnonymousAnimator)
        {
          this.d = true;
        }
        
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          paramAnonymousAnimator = e.this;
          paramAnonymousAnimator.b = 0;
          if (!this.d)
          {
            paramAnonymousAnimator = paramAnonymousAnimator.n;
            int i;
            if (paramBoolean) {
              i = 8;
            } else {
              i = 4;
            }
            paramAnonymousAnimator.a(i, paramBoolean);
            paramAnonymousAnimator = paramc;
            if (paramAnonymousAnimator != null) {
              paramAnonymousAnimator.b();
            }
          }
        }
        
        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
          e.this.n.a(0, paramBoolean);
          this.d = false;
        }
      });
    }
    else
    {
      r localr = this.n;
      int i1;
      if (paramBoolean) {
        i1 = 8;
      } else {
        i1 = 4;
      }
      localr.a(i1, paramBoolean);
      if (paramc != null) {
        paramc.b();
      }
    }
  }
  
  void a(int[] paramArrayOfInt)
  {
    this.p.a(paramArrayOfInt);
  }
  
  void b()
  {
    this.p.a();
  }
  
  void b(Rect paramRect) {}
  
  void b(final c paramc, final boolean paramBoolean)
  {
    if (j()) {
      return;
    }
    this.n.animate().cancel();
    if (m())
    {
      this.b = 2;
      if (this.n.getVisibility() != 0)
      {
        this.n.setAlpha(0.0F);
        this.n.setScaleY(0.0F);
        this.n.setScaleX(0.0F);
      }
      this.n.animate().scaleX(1.0F).scaleY(1.0F).alpha(1.0F).setDuration(200L).setInterpolator(a.d).setListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          e.this.b = 0;
          paramAnonymousAnimator = paramc;
          if (paramAnonymousAnimator != null) {
            paramAnonymousAnimator.a();
          }
        }
        
        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
          e.this.n.a(0, paramBoolean);
        }
      });
    }
    else
    {
      this.n.a(0, paramBoolean);
      this.n.setAlpha(1.0F);
      this.n.setScaleY(1.0F);
      this.n.setScaleX(1.0F);
      if (paramc != null) {
        paramc.a();
      }
    }
  }
  
  final Drawable c()
  {
    return this.g;
  }
  
  void d() {}
  
  final void e()
  {
    Rect localRect = this.r;
    a(localRect);
    b(localRect);
    this.o.a(localRect.left, localRect.top, localRect.right, localRect.bottom);
  }
  
  void f()
  {
    if (h())
    {
      l();
      this.n.getViewTreeObserver().addOnPreDrawListener(this.s);
    }
  }
  
  void g()
  {
    if (this.s != null)
    {
      this.n.getViewTreeObserver().removeOnPreDrawListener(this.s);
      this.s = null;
    }
  }
  
  boolean h()
  {
    return true;
  }
  
  void i()
  {
    float f1 = this.n.getRotation();
    if (this.q != f1)
    {
      this.q = f1;
      n();
    }
  }
  
  boolean j()
  {
    int i1 = this.n.getVisibility();
    boolean bool1 = false;
    boolean bool2 = false;
    if (i1 != 0)
    {
      if (this.b == 2) {
        bool2 = true;
      }
      return bool2;
    }
    bool2 = bool1;
    if (this.b != 1) {
      bool2 = true;
    }
    return bool2;
  }
  
  boolean k()
  {
    int i1 = this.n.getVisibility();
    boolean bool1 = false;
    boolean bool2 = false;
    if (i1 == 0)
    {
      if (this.b == 1) {
        bool2 = true;
      }
      return bool2;
    }
    bool2 = bool1;
    if (this.b != 2) {
      bool2 = true;
    }
    return bool2;
  }
  
  private class a
    extends e.e
  {
    a()
    {
      super(null);
    }
    
    protected float a()
    {
      return 0.0F;
    }
  }
  
  private class b
    extends e.e
  {
    b()
    {
      super(null);
    }
    
    protected float a()
    {
      return e.this.h + e.this.i;
    }
  }
  
  static abstract interface c
  {
    public abstract void a();
    
    public abstract void b();
  }
  
  private class d
    extends e.e
  {
    d()
    {
      super(null);
    }
    
    protected float a()
    {
      return e.this.h;
    }
  }
  
  private abstract class e
    extends AnimatorListenerAdapter
    implements ValueAnimator.AnimatorUpdateListener
  {
    private boolean a;
    private float c;
    private float d;
    
    private e() {}
    
    protected abstract float a();
    
    public void onAnimationEnd(Animator paramAnimator)
    {
      e.this.c.b(this.d);
      this.a = false;
    }
    
    public void onAnimationUpdate(ValueAnimator paramValueAnimator)
    {
      if (!this.a)
      {
        this.c = e.this.c.a();
        this.d = a();
        this.a = true;
      }
      i locali = e.this.c;
      float f = this.c;
      locali.b(f + (this.d - f) * paramValueAnimator.getAnimatedFraction());
    }
  }
}


/* Location:              ~/android/support/design/widget/e.class
 *
 * Reversed by:           J
 */