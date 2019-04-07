package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;

class t
  extends RecyclerView.ItemDecoration
  implements RecyclerView.OnItemTouchListener
{
  private static final int[] g = { 16842919 };
  private static final int[] h = new int[0];
  private final int[] A = new int[2];
  private final ValueAnimator B = ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F });
  private int C = 0;
  private final Runnable D = new Runnable()
  {
    public void run()
    {
      t.this.a(500);
    }
  };
  private final RecyclerView.OnScrollListener E = new RecyclerView.OnScrollListener()
  {
    public void onScrolled(RecyclerView paramAnonymousRecyclerView, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      t.this.a(paramAnonymousRecyclerView.computeHorizontalScrollOffset(), paramAnonymousRecyclerView.computeVerticalScrollOffset());
    }
  };
  int a;
  int b;
  float c;
  int d;
  int e;
  float f;
  private final int i;
  private final int j;
  private final StateListDrawable k;
  private final Drawable l;
  private final int m;
  private final int n;
  private final StateListDrawable o;
  private final Drawable p;
  private final int q;
  private final int r;
  private int s = 0;
  private int t = 0;
  private RecyclerView u;
  private boolean v = false;
  private boolean w = false;
  private int x = 0;
  private int y = 0;
  private final int[] z = new int[2];
  
  t(RecyclerView paramRecyclerView, StateListDrawable paramStateListDrawable1, Drawable paramDrawable1, StateListDrawable paramStateListDrawable2, Drawable paramDrawable2, int paramInt1, int paramInt2, int paramInt3)
  {
    this.k = paramStateListDrawable1;
    this.l = paramDrawable1;
    this.o = paramStateListDrawable2;
    this.p = paramDrawable2;
    this.m = Math.max(paramInt1, paramStateListDrawable1.getIntrinsicWidth());
    this.n = Math.max(paramInt1, paramDrawable1.getIntrinsicWidth());
    this.q = Math.max(paramInt1, paramStateListDrawable2.getIntrinsicWidth());
    this.r = Math.max(paramInt1, paramDrawable2.getIntrinsicWidth());
    this.i = paramInt2;
    this.j = paramInt3;
    this.k.setAlpha(255);
    this.l.setAlpha(255);
    this.B.addListener(new a(null));
    this.B.addUpdateListener(new b(null));
    a(paramRecyclerView);
  }
  
  private int a(float paramFloat1, float paramFloat2, int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    int i1 = paramArrayOfInt[1] - paramArrayOfInt[0];
    if (i1 == 0) {
      return 0;
    }
    paramFloat1 = (paramFloat2 - paramFloat1) / i1;
    paramInt1 -= paramInt3;
    paramInt3 = (int)(paramFloat1 * paramInt1);
    paramInt2 += paramInt3;
    if ((paramInt2 < paramInt1) && (paramInt2 >= 0)) {
      return paramInt3;
    }
    return 0;
  }
  
  private void a(float paramFloat)
  {
    int[] arrayOfInt = g();
    paramFloat = Math.max(arrayOfInt[0], Math.min(arrayOfInt[1], paramFloat));
    if (Math.abs(this.b - paramFloat) < 2.0F) {
      return;
    }
    int i1 = a(this.c, paramFloat, arrayOfInt, this.u.computeVerticalScrollRange(), this.u.computeVerticalScrollOffset(), this.t);
    if (i1 != 0) {
      this.u.scrollBy(0, i1);
    }
    this.c = paramFloat;
  }
  
  private void a(Canvas paramCanvas)
  {
    int i1 = this.s;
    int i2 = this.m;
    int i3 = i1 - i2;
    int i4 = this.b;
    i1 = this.a;
    i4 -= i1 / 2;
    this.k.setBounds(0, 0, i2, i1);
    this.l.setBounds(0, 0, this.n, this.t);
    if (e())
    {
      this.l.draw(paramCanvas);
      paramCanvas.translate(this.m, i4);
      paramCanvas.scale(-1.0F, 1.0F);
      this.k.draw(paramCanvas);
      paramCanvas.scale(1.0F, 1.0F);
      paramCanvas.translate(-this.m, -i4);
    }
    else
    {
      paramCanvas.translate(i3, 0.0F);
      this.l.draw(paramCanvas);
      paramCanvas.translate(0.0F, i4);
      this.k.draw(paramCanvas);
      paramCanvas.translate(-i3, -i4);
    }
  }
  
  private void b()
  {
    this.u.addItemDecoration(this);
    this.u.addOnItemTouchListener(this);
    this.u.addOnScrollListener(this.E);
  }
  
  private void b(float paramFloat)
  {
    int[] arrayOfInt = h();
    paramFloat = Math.max(arrayOfInt[0], Math.min(arrayOfInt[1], paramFloat));
    if (Math.abs(this.e - paramFloat) < 2.0F) {
      return;
    }
    int i1 = a(this.f, paramFloat, arrayOfInt, this.u.computeHorizontalScrollRange(), this.u.computeHorizontalScrollOffset(), this.s);
    if (i1 != 0) {
      this.u.scrollBy(i1, 0);
    }
    this.f = paramFloat;
  }
  
  private void b(int paramInt)
  {
    if ((paramInt == 2) && (this.x != 2))
    {
      this.k.setState(g);
      f();
    }
    if (paramInt == 0) {
      d();
    } else {
      a();
    }
    if ((this.x == 2) && (paramInt != 2))
    {
      this.k.setState(h);
      c(1200);
    }
    else if (paramInt == 1)
    {
      c(1500);
    }
    this.x = paramInt;
  }
  
  private void b(Canvas paramCanvas)
  {
    int i1 = this.t;
    int i2 = this.q;
    i1 -= i2;
    int i3 = this.e;
    int i4 = this.d;
    i3 -= i4 / 2;
    this.o.setBounds(0, 0, i4, i2);
    this.p.setBounds(0, 0, this.s, this.r);
    paramCanvas.translate(0.0F, i1);
    this.p.draw(paramCanvas);
    paramCanvas.translate(i3, 0.0F);
    this.o.draw(paramCanvas);
    paramCanvas.translate(-i3, -i1);
  }
  
  private void c()
  {
    this.u.removeItemDecoration(this);
    this.u.removeOnItemTouchListener(this);
    this.u.removeOnScrollListener(this.E);
    f();
  }
  
  private void c(int paramInt)
  {
    f();
    this.u.postDelayed(this.D, paramInt);
  }
  
  private void d()
  {
    this.u.invalidate();
  }
  
  private boolean e()
  {
    int i1 = ViewCompat.getLayoutDirection(this.u);
    boolean bool = true;
    if (i1 != 1) {
      bool = false;
    }
    return bool;
  }
  
  private void f()
  {
    this.u.removeCallbacks(this.D);
  }
  
  private int[] g()
  {
    int[] arrayOfInt = this.z;
    int i1 = this.j;
    arrayOfInt[0] = i1;
    arrayOfInt[1] = (this.t - i1);
    return arrayOfInt;
  }
  
  private int[] h()
  {
    int[] arrayOfInt = this.A;
    int i1 = this.j;
    arrayOfInt[0] = i1;
    arrayOfInt[1] = (this.s - i1);
    return arrayOfInt;
  }
  
  public void a()
  {
    int i1 = this.C;
    if (i1 != 0)
    {
      if (i1 == 3) {
        this.B.cancel();
      }
    }
    else
    {
      this.C = 1;
      ValueAnimator localValueAnimator = this.B;
      localValueAnimator.setFloatValues(new float[] { ((Float)localValueAnimator.getAnimatedValue()).floatValue(), 1.0F });
      this.B.setDuration(500L);
      this.B.setStartDelay(0L);
      this.B.start();
    }
  }
  
  void a(int paramInt)
  {
    switch (this.C)
    {
    default: 
      break;
    case 1: 
      this.B.cancel();
    case 2: 
      this.C = 3;
      ValueAnimator localValueAnimator = this.B;
      localValueAnimator.setFloatValues(new float[] { ((Float)localValueAnimator.getAnimatedValue()).floatValue(), 0.0F });
      this.B.setDuration(paramInt);
      this.B.start();
    }
  }
  
  void a(int paramInt1, int paramInt2)
  {
    int i1 = this.u.computeVerticalScrollRange();
    int i2 = this.t;
    boolean bool;
    if ((i1 - i2 > 0) && (i2 >= this.i)) {
      bool = true;
    } else {
      bool = false;
    }
    this.v = bool;
    int i3 = this.u.computeHorizontalScrollRange();
    int i4 = this.s;
    if ((i3 - i4 > 0) && (i4 >= this.i)) {
      bool = true;
    } else {
      bool = false;
    }
    this.w = bool;
    if ((!this.v) && (!this.w))
    {
      if (this.x != 0) {
        b(0);
      }
      return;
    }
    float f1;
    float f2;
    if (this.v)
    {
      f1 = paramInt2;
      f2 = i2;
      this.b = ((int)(f2 * (f1 + f2 / 2.0F) / i1));
      this.a = Math.min(i2, i2 * i2 / i1);
    }
    if (this.w)
    {
      f2 = paramInt1;
      f1 = i4;
      this.e = ((int)(f1 * (f2 + f1 / 2.0F) / i3));
      this.d = Math.min(i4, i4 * i4 / i3);
    }
    paramInt1 = this.x;
    if ((paramInt1 == 0) || (paramInt1 == 1)) {
      b(1);
    }
  }
  
  public void a(RecyclerView paramRecyclerView)
  {
    RecyclerView localRecyclerView = this.u;
    if (localRecyclerView == paramRecyclerView) {
      return;
    }
    if (localRecyclerView != null) {
      c();
    }
    this.u = paramRecyclerView;
    if (this.u != null) {
      b();
    }
  }
  
  boolean a(float paramFloat1, float paramFloat2)
  {
    if (e() ? paramFloat1 <= this.m / 2 : paramFloat1 >= this.s - this.m)
    {
      int i1 = this.b;
      int i2 = this.a;
      if ((paramFloat2 >= i1 - i2 / 2) && (paramFloat2 <= i1 + i2 / 2))
      {
        bool = true;
        break label81;
      }
    }
    boolean bool = false;
    label81:
    return bool;
  }
  
  boolean b(float paramFloat1, float paramFloat2)
  {
    if (paramFloat2 >= this.t - this.q)
    {
      int i1 = this.e;
      int i2 = this.d;
      if ((paramFloat1 >= i1 - i2 / 2) && (paramFloat1 <= i1 + i2 / 2))
      {
        bool = true;
        break label59;
      }
    }
    boolean bool = false;
    label59:
    return bool;
  }
  
  public void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    if ((this.s == this.u.getWidth()) && (this.t == this.u.getHeight()))
    {
      if (this.C != 0)
      {
        if (this.v) {
          a(paramCanvas);
        }
        if (this.w) {
          b(paramCanvas);
        }
      }
      return;
    }
    this.s = this.u.getWidth();
    this.t = this.u.getHeight();
    b(0);
  }
  
  public boolean onInterceptTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent)
  {
    int i1 = this.x;
    boolean bool1 = false;
    boolean bool4;
    if (i1 == 1)
    {
      boolean bool2 = a(paramMotionEvent.getX(), paramMotionEvent.getY());
      boolean bool3 = b(paramMotionEvent.getX(), paramMotionEvent.getY());
      bool4 = bool1;
      if (paramMotionEvent.getAction() == 0) {
        if (!bool2)
        {
          bool4 = bool1;
          if (!bool3) {}
        }
        else
        {
          if (bool3)
          {
            this.y = 1;
            this.f = ((int)paramMotionEvent.getX());
          }
          else if (bool2)
          {
            this.y = 2;
            this.c = ((int)paramMotionEvent.getY());
          }
          b(2);
          bool4 = true;
        }
      }
    }
    else
    {
      bool4 = bool1;
      if (i1 == 2) {
        bool4 = true;
      }
    }
    return bool4;
  }
  
  public void onRequestDisallowInterceptTouchEvent(boolean paramBoolean) {}
  
  public void onTouchEvent(RecyclerView paramRecyclerView, MotionEvent paramMotionEvent)
  {
    if (this.x == 0) {
      return;
    }
    if (paramMotionEvent.getAction() == 0)
    {
      boolean bool1 = a(paramMotionEvent.getX(), paramMotionEvent.getY());
      boolean bool2 = b(paramMotionEvent.getX(), paramMotionEvent.getY());
      if ((bool1) || (bool2))
      {
        if (bool2)
        {
          this.y = 1;
          this.f = ((int)paramMotionEvent.getX());
        }
        else if (bool1)
        {
          this.y = 2;
          this.c = ((int)paramMotionEvent.getY());
        }
        b(2);
      }
    }
    else if ((paramMotionEvent.getAction() == 1) && (this.x == 2))
    {
      this.c = 0.0F;
      this.f = 0.0F;
      b(1);
      this.y = 0;
    }
    else if ((paramMotionEvent.getAction() == 2) && (this.x == 2))
    {
      a();
      if (this.y == 1) {
        b(paramMotionEvent.getX());
      }
      if (this.y == 2) {
        a(paramMotionEvent.getY());
      }
    }
  }
  
  private class a
    extends AnimatorListenerAdapter
  {
    private boolean b = false;
    
    private a() {}
    
    public void onAnimationCancel(Animator paramAnimator)
    {
      this.b = true;
    }
    
    public void onAnimationEnd(Animator paramAnimator)
    {
      if (this.b)
      {
        this.b = false;
        return;
      }
      if (((Float)t.a(t.this).getAnimatedValue()).floatValue() == 0.0F)
      {
        t.a(t.this, 0);
        t.b(t.this, 0);
      }
      else
      {
        t.a(t.this, 2);
        t.b(t.this);
      }
    }
  }
  
  private class b
    implements ValueAnimator.AnimatorUpdateListener
  {
    private b() {}
    
    public void onAnimationUpdate(ValueAnimator paramValueAnimator)
    {
      int i = (int)(((Float)paramValueAnimator.getAnimatedValue()).floatValue() * 255.0F);
      t.c(t.this).setAlpha(i);
      t.d(t.this).setAlpha(i);
      t.b(t.this);
    }
  }
}


/* Location:              ~/android/support/v7/widget/t.class
 *
 * Reversed by:           J
 */