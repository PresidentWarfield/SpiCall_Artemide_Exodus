package android.support.v7.widget;

import android.view.View;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

class ViewBoundsCheck
{
  final b a;
  a b;
  
  ViewBoundsCheck(b paramb)
  {
    this.a = paramb;
    this.b = new a();
  }
  
  View a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = this.a.a();
    int j = this.a.b();
    int k;
    if (paramInt2 > paramInt1) {
      k = 1;
    } else {
      k = -1;
    }
    Object localObject2;
    for (Object localObject1 = null; paramInt1 != paramInt2; localObject1 = localObject2)
    {
      View localView = this.a.a(paramInt1);
      int m = this.a.a(localView);
      int n = this.a.b(localView);
      this.b.a(i, j, m, n);
      if (paramInt3 != 0)
      {
        this.b.a();
        this.b.a(paramInt3);
        if (this.b.b()) {
          return localView;
        }
      }
      localObject2 = localObject1;
      if (paramInt4 != 0)
      {
        this.b.a();
        this.b.a(paramInt4);
        localObject2 = localObject1;
        if (this.b.b()) {
          localObject2 = localView;
        }
      }
      paramInt1 += k;
    }
    return (View)localObject1;
  }
  
  boolean a(View paramView, int paramInt)
  {
    this.b.a(this.a.a(), this.a.b(), this.a.a(paramView), this.a.b(paramView));
    if (paramInt != 0)
    {
      this.b.a();
      this.b.a(paramInt);
      return this.b.b();
    }
    return false;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ViewBounds {}
  
  static class a
  {
    int a = 0;
    int b;
    int c;
    int d;
    int e;
    
    int a(int paramInt1, int paramInt2)
    {
      if (paramInt1 > paramInt2) {
        return 1;
      }
      if (paramInt1 == paramInt2) {
        return 2;
      }
      return 4;
    }
    
    void a()
    {
      this.a = 0;
    }
    
    void a(int paramInt)
    {
      this.a = (paramInt | this.a);
    }
    
    void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      this.b = paramInt1;
      this.c = paramInt2;
      this.d = paramInt3;
      this.e = paramInt4;
    }
    
    boolean b()
    {
      int i = this.a;
      if (((i & 0x7) != 0) && ((i & a(this.d, this.b) << 0) == 0)) {
        return false;
      }
      i = this.a;
      if (((i & 0x70) != 0) && ((i & a(this.d, this.c) << 4) == 0)) {
        return false;
      }
      i = this.a;
      if (((i & 0x700) != 0) && ((i & a(this.e, this.b) << 8) == 0)) {
        return false;
      }
      i = this.a;
      return ((i & 0x7000) == 0) || ((i & a(this.e, this.c) << 12) != 0);
    }
  }
  
  static abstract interface b
  {
    public abstract int a();
    
    public abstract int a(View paramView);
    
    public abstract View a(int paramInt);
    
    public abstract int b();
    
    public abstract int b(View paramView);
  }
}


/* Location:              ~/android/support/v7/widget/ViewBoundsCheck.class
 *
 * Reversed by:           J
 */