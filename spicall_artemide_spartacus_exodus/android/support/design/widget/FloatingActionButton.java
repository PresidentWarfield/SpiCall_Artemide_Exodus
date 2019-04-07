package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.design.a.c;
import android.support.design.a.i;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import java.util.List;

@CoordinatorLayout.b(a=Behavior.class)
public class FloatingActionButton
  extends r
{
  int a;
  boolean b;
  final Rect c;
  private ColorStateList d;
  private PorterDuff.Mode e;
  private int f;
  private int g;
  private int h;
  private final Rect i;
  private AppCompatImageHelper j;
  private e k;
  
  private int a(int paramInt)
  {
    Resources localResources = getResources();
    if (paramInt != -1)
    {
      if (paramInt != 1) {
        return localResources.getDimensionPixelSize(a.c.design_fab_size_normal);
      }
      return localResources.getDimensionPixelSize(a.c.design_fab_size_mini);
    }
    if (Math.max(localResources.getConfiguration().screenWidthDp, localResources.getConfiguration().screenHeightDp) < 470) {
      paramInt = a(1);
    } else {
      paramInt = a(0);
    }
    return paramInt;
  }
  
  private static int a(int paramInt1, int paramInt2)
  {
    int m = View.MeasureSpec.getMode(paramInt2);
    int n = View.MeasureSpec.getSize(paramInt2);
    if (m != Integer.MIN_VALUE)
    {
      paramInt2 = paramInt1;
      if (m != 0) {
        if (m != 1073741824) {
          paramInt2 = paramInt1;
        } else {
          paramInt2 = n;
        }
      }
    }
    else
    {
      paramInt2 = Math.min(paramInt1, n);
    }
    return paramInt2;
  }
  
  private e.c a(final a parama)
  {
    if (parama == null) {
      return null;
    }
    new e.c()
    {
      public void a()
      {
        parama.a(FloatingActionButton.this);
      }
      
      public void b()
      {
        parama.b(FloatingActionButton.this);
      }
    };
  }
  
  private e a()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return new f(this, new b());
    }
    return new e(this, new b());
  }
  
  private e getImpl()
  {
    if (this.k == null) {
      this.k = a();
    }
    return this.k;
  }
  
  void a(a parama, boolean paramBoolean)
  {
    getImpl().b(a(parama), paramBoolean);
  }
  
  public boolean a(Rect paramRect)
  {
    if (ViewCompat.isLaidOut(this))
    {
      paramRect.set(0, 0, getWidth(), getHeight());
      paramRect.left += this.c.left;
      paramRect.top += this.c.top;
      paramRect.right -= this.c.right;
      paramRect.bottom -= this.c.bottom;
      return true;
    }
    return false;
  }
  
  void b(a parama, boolean paramBoolean)
  {
    getImpl().a(a(parama), paramBoolean);
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    getImpl().a(getDrawableState());
  }
  
  public ColorStateList getBackgroundTintList()
  {
    return this.d;
  }
  
  public PorterDuff.Mode getBackgroundTintMode()
  {
    return this.e;
  }
  
  public float getCompatElevation()
  {
    return getImpl().a();
  }
  
  public Drawable getContentBackground()
  {
    return getImpl().c();
  }
  
  public int getRippleColor()
  {
    return this.f;
  }
  
  public int getSize()
  {
    return this.g;
  }
  
  int getSizeDimension()
  {
    return a(this.g);
  }
  
  public boolean getUseCompatPadding()
  {
    return this.b;
  }
  
  public void jumpDrawablesToCurrentState()
  {
    super.jumpDrawablesToCurrentState();
    getImpl().b();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    getImpl().f();
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    getImpl().g();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int m = getSizeDimension();
    this.a = ((m - this.h) / 2);
    getImpl().e();
    paramInt1 = Math.min(a(m, paramInt1), a(m, paramInt2));
    setMeasuredDimension(this.c.left + paramInt1 + this.c.right, paramInt1 + this.c.top + this.c.bottom);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((paramMotionEvent.getAction() == 0) && (a(this.i)) && (!this.i.contains((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY()))) {
      return false;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void setBackgroundColor(int paramInt)
  {
    Log.i("FloatingActionButton", "Setting a custom background is not supported.");
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    Log.i("FloatingActionButton", "Setting a custom background is not supported.");
  }
  
  public void setBackgroundResource(int paramInt)
  {
    Log.i("FloatingActionButton", "Setting a custom background is not supported.");
  }
  
  public void setBackgroundTintList(ColorStateList paramColorStateList)
  {
    if (this.d != paramColorStateList)
    {
      this.d = paramColorStateList;
      getImpl().a(paramColorStateList);
    }
  }
  
  public void setBackgroundTintMode(PorterDuff.Mode paramMode)
  {
    if (this.e != paramMode)
    {
      this.e = paramMode;
      getImpl().a(paramMode);
    }
  }
  
  public void setCompatElevation(float paramFloat)
  {
    getImpl().a(paramFloat);
  }
  
  public void setImageResource(int paramInt)
  {
    this.j.setImageResource(paramInt);
  }
  
  public void setRippleColor(int paramInt)
  {
    if (this.f != paramInt)
    {
      this.f = paramInt;
      getImpl().a(paramInt);
    }
  }
  
  public void setSize(int paramInt)
  {
    if (paramInt != this.g)
    {
      this.g = paramInt;
      requestLayout();
    }
  }
  
  public void setUseCompatPadding(boolean paramBoolean)
  {
    if (this.b != paramBoolean)
    {
      this.b = paramBoolean;
      getImpl().d();
    }
  }
  
  public static class Behavior
    extends CoordinatorLayout.a<FloatingActionButton>
  {
    private Rect a;
    private FloatingActionButton.a b;
    private boolean c;
    
    public Behavior()
    {
      this.c = true;
    }
    
    public Behavior(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, a.i.FloatingActionButton_Behavior_Layout);
      this.c = paramContext.getBoolean(a.i.FloatingActionButton_Behavior_Layout_behavior_autoHide, true);
      paramContext.recycle();
    }
    
    private void a(CoordinatorLayout paramCoordinatorLayout, FloatingActionButton paramFloatingActionButton)
    {
      Rect localRect = paramFloatingActionButton.c;
      if ((localRect != null) && (localRect.centerX() > 0) && (localRect.centerY() > 0))
      {
        CoordinatorLayout.d locald = (CoordinatorLayout.d)paramFloatingActionButton.getLayoutParams();
        int i = paramFloatingActionButton.getRight();
        int j = paramCoordinatorLayout.getWidth();
        int k = locald.rightMargin;
        int m = 0;
        if (i >= j - k) {
          k = localRect.right;
        } else if (paramFloatingActionButton.getLeft() <= locald.leftMargin) {
          k = -localRect.left;
        } else {
          k = 0;
        }
        if (paramFloatingActionButton.getBottom() >= paramCoordinatorLayout.getHeight() - locald.bottomMargin) {
          m = localRect.bottom;
        } else if (paramFloatingActionButton.getTop() <= locald.topMargin) {
          m = -localRect.top;
        }
        if (m != 0) {
          ViewCompat.offsetTopAndBottom(paramFloatingActionButton, m);
        }
        if (k != 0) {
          ViewCompat.offsetLeftAndRight(paramFloatingActionButton, k);
        }
      }
    }
    
    private boolean a(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, FloatingActionButton paramFloatingActionButton)
    {
      if (!a(paramAppBarLayout, paramFloatingActionButton)) {
        return false;
      }
      if (this.a == null) {
        this.a = new Rect();
      }
      Rect localRect = this.a;
      n.b(paramCoordinatorLayout, paramAppBarLayout, localRect);
      if (localRect.bottom <= paramAppBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
        paramFloatingActionButton.b(this.b, false);
      } else {
        paramFloatingActionButton.a(this.b, false);
      }
      return true;
    }
    
    private static boolean a(View paramView)
    {
      paramView = paramView.getLayoutParams();
      if ((paramView instanceof CoordinatorLayout.d)) {
        return ((CoordinatorLayout.d)paramView).b() instanceof BottomSheetBehavior;
      }
      return false;
    }
    
    private boolean a(View paramView, FloatingActionButton paramFloatingActionButton)
    {
      CoordinatorLayout.d locald = (CoordinatorLayout.d)paramFloatingActionButton.getLayoutParams();
      if (!this.c) {
        return false;
      }
      if (locald.a() != paramView.getId()) {
        return false;
      }
      return paramFloatingActionButton.getUserSetVisibility() == 0;
    }
    
    private boolean b(View paramView, FloatingActionButton paramFloatingActionButton)
    {
      if (!a(paramView, paramFloatingActionButton)) {
        return false;
      }
      CoordinatorLayout.d locald = (CoordinatorLayout.d)paramFloatingActionButton.getLayoutParams();
      if (paramView.getTop() < paramFloatingActionButton.getHeight() / 2 + locald.topMargin) {
        paramFloatingActionButton.b(this.b, false);
      } else {
        paramFloatingActionButton.a(this.b, false);
      }
      return true;
    }
    
    public void a(CoordinatorLayout.d paramd)
    {
      if (paramd.h == 0) {
        paramd.h = 80;
      }
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, FloatingActionButton paramFloatingActionButton, int paramInt)
    {
      List localList = paramCoordinatorLayout.c(paramFloatingActionButton);
      int i = localList.size();
      for (int j = 0; j < i; j++)
      {
        View localView = (View)localList.get(j);
        if ((localView instanceof AppBarLayout) ? !a(paramCoordinatorLayout, (AppBarLayout)localView, paramFloatingActionButton) : (a(localView)) && (b(localView, paramFloatingActionButton))) {
          break;
        }
      }
      paramCoordinatorLayout.a(paramFloatingActionButton, paramInt);
      a(paramCoordinatorLayout, paramFloatingActionButton);
      return true;
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, FloatingActionButton paramFloatingActionButton, Rect paramRect)
    {
      paramCoordinatorLayout = paramFloatingActionButton.c;
      paramRect.set(paramFloatingActionButton.getLeft() + paramCoordinatorLayout.left, paramFloatingActionButton.getTop() + paramCoordinatorLayout.top, paramFloatingActionButton.getRight() - paramCoordinatorLayout.right, paramFloatingActionButton.getBottom() - paramCoordinatorLayout.bottom);
      return true;
    }
    
    public boolean a(CoordinatorLayout paramCoordinatorLayout, FloatingActionButton paramFloatingActionButton, View paramView)
    {
      if ((paramView instanceof AppBarLayout)) {
        a(paramCoordinatorLayout, (AppBarLayout)paramView, paramFloatingActionButton);
      } else if (a(paramView)) {
        b(paramView, paramFloatingActionButton);
      }
      return false;
    }
  }
  
  public static abstract class a
  {
    public void a(FloatingActionButton paramFloatingActionButton) {}
    
    public void b(FloatingActionButton paramFloatingActionButton) {}
  }
  
  private class b
    implements j
  {
    b() {}
    
    public float a()
    {
      return FloatingActionButton.this.getSizeDimension() / 2.0F;
    }
    
    public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      FloatingActionButton.this.c.set(paramInt1, paramInt2, paramInt3, paramInt4);
      FloatingActionButton localFloatingActionButton = FloatingActionButton.this;
      localFloatingActionButton.setPadding(paramInt1 + localFloatingActionButton.a, paramInt2 + FloatingActionButton.this.a, paramInt3 + FloatingActionButton.this.a, paramInt4 + FloatingActionButton.this.a);
    }
    
    public void a(Drawable paramDrawable)
    {
      FloatingActionButton.a(FloatingActionButton.this, paramDrawable);
    }
    
    public boolean b()
    {
      return FloatingActionButton.this.b;
    }
  }
}


/* Location:              ~/android/support/design/widget/FloatingActionButton.class
 *
 * Reversed by:           J
 */