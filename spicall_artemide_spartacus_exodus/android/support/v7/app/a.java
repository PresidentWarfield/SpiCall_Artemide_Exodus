package android.support.v7.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.a.a.j;
import android.support.v7.view.b;
import android.support.v7.view.b.a;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;

public abstract class a
{
  public abstract int a();
  
  public b a(b.a parama)
  {
    return null;
  }
  
  public void a(float paramFloat)
  {
    if (paramFloat == 0.0F) {
      return;
    }
    throw new UnsupportedOperationException("Setting a non-zero elevation is not supported in this action bar configuration.");
  }
  
  public void a(int paramInt) {}
  
  public void a(Configuration paramConfiguration) {}
  
  public void a(Drawable paramDrawable) {}
  
  public void a(CharSequence paramCharSequence) {}
  
  public void a(boolean paramBoolean) {}
  
  public boolean a(int paramInt, KeyEvent paramKeyEvent)
  {
    return false;
  }
  
  public boolean a(KeyEvent paramKeyEvent)
  {
    return false;
  }
  
  public Context b()
  {
    return null;
  }
  
  public void b(boolean paramBoolean)
  {
    if (!paramBoolean) {
      return;
    }
    throw new UnsupportedOperationException("Hide on content scroll is not supported in this action bar configuration.");
  }
  
  public void c(boolean paramBoolean) {}
  
  public boolean c()
  {
    return false;
  }
  
  public void d(boolean paramBoolean) {}
  
  public boolean d()
  {
    return false;
  }
  
  public void e(boolean paramBoolean) {}
  
  public boolean e()
  {
    return false;
  }
  
  public boolean f()
  {
    return false;
  }
  
  void g() {}
  
  public static class a
    extends ViewGroup.MarginLayoutParams
  {
    public int gravity = 0;
    
    public a(int paramInt)
    {
      this(-2, -1, paramInt);
    }
    
    public a(int paramInt1, int paramInt2)
    {
      super(paramInt2);
      this.gravity = 8388627;
    }
    
    public a(int paramInt1, int paramInt2, int paramInt3)
    {
      super(paramInt2);
      this.gravity = paramInt3;
    }
    
    public a(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, a.j.ActionBarLayout);
      this.gravity = paramContext.getInt(a.j.ActionBarLayout_android_layout_gravity, 0);
      paramContext.recycle();
    }
    
    public a(a parama)
    {
      super();
      this.gravity = parama.gravity;
    }
    
    public a(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
  }
  
  public static abstract interface b
  {
    public abstract void a(boolean paramBoolean);
  }
  
  @Deprecated
  public static abstract class c
  {
    public abstract Drawable a();
    
    public abstract CharSequence b();
    
    public abstract View c();
    
    public abstract void d();
    
    public abstract CharSequence e();
  }
}


/* Location:              ~/android/support/v7/app/a.class
 *
 * Reversed by:           J
 */