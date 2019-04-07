package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.support.design.a.a;
import android.support.design.a.i;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import java.util.List;

public abstract class b<B extends b<B>>
{
  static final Handler a = new Handler(Looper.getMainLooper(), new Handler.Callback()
  {
    public boolean handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return false;
      case 1: 
        ((b)paramAnonymousMessage.obj).b(paramAnonymousMessage.arg1);
        return true;
      }
      ((b)paramAnonymousMessage.obj).b();
      return true;
    }
  });
  private static final boolean d;
  final f b;
  final k.a c;
  private final ViewGroup e;
  private final c f;
  private List<a<B>> g;
  private final AccessibilityManager h;
  
  static
  {
    boolean bool;
    if ((Build.VERSION.SDK_INT >= 16) && (Build.VERSION.SDK_INT <= 19)) {
      bool = true;
    } else {
      bool = false;
    }
    d = bool;
  }
  
  private void d(final int paramInt)
  {
    Object localObject;
    if (Build.VERSION.SDK_INT >= 12)
    {
      localObject = new ValueAnimator();
      ((ValueAnimator)localObject).setIntValues(new int[] { 0, this.b.getHeight() });
      ((ValueAnimator)localObject).setInterpolator(a.b);
      ((ValueAnimator)localObject).setDuration(250L);
      ((ValueAnimator)localObject).addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          b.this.c(paramInt);
        }
        
        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
          b.a(b.this).b(0, 180);
        }
      });
      ((ValueAnimator)localObject).addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        private int b = 0;
        
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          int i = ((Integer)paramAnonymousValueAnimator.getAnimatedValue()).intValue();
          if (b.f()) {
            ViewCompat.offsetTopAndBottom(b.this.b, i - this.b);
          } else {
            b.this.b.setTranslationY(i);
          }
          this.b = i;
        }
      });
      ((ValueAnimator)localObject).start();
    }
    else
    {
      localObject = AnimationUtils.loadAnimation(this.b.getContext(), a.a.design_snackbar_out);
      ((Animation)localObject).setInterpolator(a.b);
      ((Animation)localObject).setDuration(250L);
      ((Animation)localObject).setAnimationListener(new Animation.AnimationListener()
      {
        public void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          b.this.c(paramInt);
        }
        
        public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
        
        public void onAnimationStart(Animation paramAnonymousAnimation) {}
      });
      this.b.startAnimation((Animation)localObject);
    }
  }
  
  void a(int paramInt)
  {
    k.a().a(this.c, paramInt);
  }
  
  public boolean a()
  {
    return k.a().e(this.c);
  }
  
  final void b()
  {
    if (this.b.getParent() == null)
    {
      Object localObject = this.b.getLayoutParams();
      if ((localObject instanceof CoordinatorLayout.d))
      {
        CoordinatorLayout.d locald = (CoordinatorLayout.d)localObject;
        localObject = new b();
        ((b)localObject).a(0.1F);
        ((b)localObject).b(0.6F);
        ((b)localObject).a(0);
        ((b)localObject).a(new SwipeDismissBehavior.a()
        {
          public void a(int paramAnonymousInt)
          {
            switch (paramAnonymousInt)
            {
            default: 
              break;
            case 1: 
            case 2: 
              k.a().c(b.this.c);
              break;
            case 0: 
              k.a().d(b.this.c);
            }
          }
          
          public void a(View paramAnonymousView)
          {
            paramAnonymousView.setVisibility(8);
            b.this.a(0);
          }
        });
        locald.a((CoordinatorLayout.a)localObject);
        locald.g = 80;
      }
      this.e.addView(this.b);
    }
    this.b.setOnAttachStateChangeListener(new d()
    {
      public void a(View paramAnonymousView) {}
      
      public void b(View paramAnonymousView)
      {
        if (b.this.a()) {
          b.a.post(new Runnable()
          {
            public void run()
            {
              b.this.c(3);
            }
          });
        }
      }
    });
    if (ViewCompat.isLaidOut(this.b))
    {
      if (e()) {
        c();
      } else {
        d();
      }
    }
    else {
      this.b.setOnLayoutChangeListener(new e()
      {
        public void a(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
        {
          b.this.b.setOnLayoutChangeListener(null);
          if (b.this.e()) {
            b.this.c();
          } else {
            b.this.d();
          }
        }
      });
    }
  }
  
  final void b(int paramInt)
  {
    if ((e()) && (this.b.getVisibility() == 0)) {
      d(paramInt);
    } else {
      c(paramInt);
    }
  }
  
  void c()
  {
    Object localObject;
    if (Build.VERSION.SDK_INT >= 12)
    {
      final int i = this.b.getHeight();
      if (d) {
        ViewCompat.offsetTopAndBottom(this.b, i);
      } else {
        this.b.setTranslationY(i);
      }
      localObject = new ValueAnimator();
      ((ValueAnimator)localObject).setIntValues(new int[] { i, 0 });
      ((ValueAnimator)localObject).setInterpolator(a.b);
      ((ValueAnimator)localObject).setDuration(250L);
      ((ValueAnimator)localObject).addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          b.this.d();
        }
        
        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
          b.a(b.this).a(70, 180);
        }
      });
      ((ValueAnimator)localObject).addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        private int c = i;
        
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          int i = ((Integer)paramAnonymousValueAnimator.getAnimatedValue()).intValue();
          if (b.f()) {
            ViewCompat.offsetTopAndBottom(b.this.b, i - this.c);
          } else {
            b.this.b.setTranslationY(i);
          }
          this.c = i;
        }
      });
      ((ValueAnimator)localObject).start();
    }
    else
    {
      localObject = AnimationUtils.loadAnimation(this.b.getContext(), a.a.design_snackbar_in);
      ((Animation)localObject).setInterpolator(a.b);
      ((Animation)localObject).setDuration(250L);
      ((Animation)localObject).setAnimationListener(new Animation.AnimationListener()
      {
        public void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          b.this.d();
        }
        
        public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
        
        public void onAnimationStart(Animation paramAnonymousAnimation) {}
      });
      this.b.startAnimation((Animation)localObject);
    }
  }
  
  void c(int paramInt)
  {
    k.a().a(this.c);
    Object localObject = this.g;
    if (localObject != null) {
      for (int i = ((List)localObject).size() - 1; i >= 0; i--) {
        ((a)this.g.get(i)).a(this, paramInt);
      }
    }
    if (Build.VERSION.SDK_INT < 11) {
      this.b.setVisibility(8);
    }
    localObject = this.b.getParent();
    if ((localObject instanceof ViewGroup)) {
      ((ViewGroup)localObject).removeView(this.b);
    }
  }
  
  void d()
  {
    k.a().b(this.c);
    List localList = this.g;
    if (localList != null) {
      for (int i = localList.size() - 1; i >= 0; i--) {
        ((a)this.g.get(i)).a(this);
      }
    }
  }
  
  boolean e()
  {
    return this.h.isEnabled() ^ true;
  }
  
  public static abstract class a<B>
  {
    public void a(B paramB) {}
    
    public void a(B paramB, int paramInt) {}
  }
  
  final class b
    extends SwipeDismissBehavior<b.f>
  {
    b() {}
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, b.f paramf, MotionEvent paramMotionEvent)
    {
      int i = paramMotionEvent.getActionMasked();
      if (i != 3) {
        switch (i)
        {
        default: 
          break;
        case 0: 
          if (!paramCoordinatorLayout.a(paramf, (int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())) {
            break;
          }
          k.a().c(b.this.c);
          break;
        }
      } else {
        k.a().d(b.this.c);
      }
      return super.a(paramCoordinatorLayout, paramf, paramMotionEvent);
    }
    
    public boolean a(View paramView)
    {
      return paramView instanceof b.f;
    }
  }
  
  public static abstract interface c
  {
    public abstract void a(int paramInt1, int paramInt2);
    
    public abstract void b(int paramInt1, int paramInt2);
  }
  
  static abstract interface d
  {
    public abstract void a(View paramView);
    
    public abstract void b(View paramView);
  }
  
  static abstract interface e
  {
    public abstract void a(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  }
  
  static class f
    extends FrameLayout
  {
    private b.e a;
    private b.d b;
    
    f(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, a.i.SnackbarLayout);
      if (paramContext.hasValue(a.i.SnackbarLayout_elevation)) {
        ViewCompat.setElevation(this, paramContext.getDimensionPixelSize(a.i.SnackbarLayout_elevation, 0));
      }
      paramContext.recycle();
      setClickable(true);
    }
    
    protected void onAttachedToWindow()
    {
      super.onAttachedToWindow();
      b.d locald = this.b;
      if (locald != null) {
        locald.a(this);
      }
      ViewCompat.requestApplyInsets(this);
    }
    
    protected void onDetachedFromWindow()
    {
      super.onDetachedFromWindow();
      b.d locald = this.b;
      if (locald != null) {
        locald.b(this);
      }
    }
    
    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
      b.e locale = this.a;
      if (locale != null) {
        locale.a(this, paramInt1, paramInt2, paramInt3, paramInt4);
      }
    }
    
    void setOnAttachStateChangeListener(b.d paramd)
    {
      this.b = paramd;
    }
    
    void setOnLayoutChangeListener(b.e parame)
    {
      this.a = parame;
    }
  }
}


/* Location:              ~/android/support/design/widget/b.class
 *
 * Reversed by:           J
 */