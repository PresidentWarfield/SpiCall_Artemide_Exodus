package android.support.v7.widget;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;
import java.util.List;

class r
{
  final b a;
  final a b;
  final List<View> c;
  
  r(b paramb)
  {
    this.a = paramb;
    this.b = new a();
    this.c = new ArrayList();
  }
  
  private int f(int paramInt)
  {
    if (paramInt < 0) {
      return -1;
    }
    int i = this.a.a();
    int j = paramInt;
    while (j < i)
    {
      int k = paramInt - (j - this.b.e(j));
      if (k == 0)
      {
        while (this.b.c(j)) {
          j++;
        }
        return j;
      }
      j += k;
    }
    return -1;
  }
  
  private void g(View paramView)
  {
    this.c.add(paramView);
    this.a.c(paramView);
  }
  
  private boolean h(View paramView)
  {
    if (this.c.remove(paramView))
    {
      this.a.d(paramView);
      return true;
    }
    return false;
  }
  
  void a()
  {
    this.b.a();
    for (int i = this.c.size() - 1; i >= 0; i--)
    {
      this.a.d((View)this.c.get(i));
      this.c.remove(i);
    }
    this.a.b();
  }
  
  void a(int paramInt)
  {
    paramInt = f(paramInt);
    View localView = this.a.b(paramInt);
    if (localView == null) {
      return;
    }
    if (this.b.d(paramInt)) {
      h(localView);
    }
    this.a.a(paramInt);
  }
  
  void a(View paramView)
  {
    int i = this.a.a(paramView);
    if (i < 0) {
      return;
    }
    if (this.b.d(i)) {
      h(paramView);
    }
    this.a.a(i);
  }
  
  void a(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams, boolean paramBoolean)
  {
    if (paramInt < 0) {
      paramInt = this.a.a();
    } else {
      paramInt = f(paramInt);
    }
    this.b.a(paramInt, paramBoolean);
    if (paramBoolean) {
      g(paramView);
    }
    this.a.a(paramView, paramInt, paramLayoutParams);
  }
  
  void a(View paramView, int paramInt, boolean paramBoolean)
  {
    if (paramInt < 0) {
      paramInt = this.a.a();
    } else {
      paramInt = f(paramInt);
    }
    this.b.a(paramInt, paramBoolean);
    if (paramBoolean) {
      g(paramView);
    }
    this.a.a(paramView, paramInt);
  }
  
  void a(View paramView, boolean paramBoolean)
  {
    a(paramView, -1, paramBoolean);
  }
  
  int b()
  {
    return this.a.a() - this.c.size();
  }
  
  int b(View paramView)
  {
    int i = this.a.a(paramView);
    if (i == -1) {
      return -1;
    }
    if (this.b.c(i)) {
      return -1;
    }
    return i - this.b.e(i);
  }
  
  View b(int paramInt)
  {
    paramInt = f(paramInt);
    return this.a.b(paramInt);
  }
  
  int c()
  {
    return this.a.a();
  }
  
  View c(int paramInt)
  {
    int i = this.c.size();
    for (int j = 0; j < i; j++)
    {
      View localView = (View)this.c.get(j);
      RecyclerView.ViewHolder localViewHolder = this.a.b(localView);
      if ((localViewHolder.getLayoutPosition() == paramInt) && (!localViewHolder.isInvalid()) && (!localViewHolder.isRemoved())) {
        return localView;
      }
    }
    return null;
  }
  
  boolean c(View paramView)
  {
    return this.c.contains(paramView);
  }
  
  View d(int paramInt)
  {
    return this.a.b(paramInt);
  }
  
  void d(View paramView)
  {
    int i = this.a.a(paramView);
    if (i >= 0)
    {
      this.b.a(i);
      g(paramView);
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("view is not a child, cannot hide ");
    localStringBuilder.append(paramView);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  void e(int paramInt)
  {
    paramInt = f(paramInt);
    this.b.d(paramInt);
    this.a.c(paramInt);
  }
  
  void e(View paramView)
  {
    int i = this.a.a(paramView);
    if (i >= 0)
    {
      if (this.b.c(i))
      {
        this.b.b(i);
        h(paramView);
        return;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("trying to unhide a view that was not hidden");
      localStringBuilder.append(paramView);
      throw new RuntimeException(localStringBuilder.toString());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("view is not a child, cannot hide ");
    localStringBuilder.append(paramView);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  boolean f(View paramView)
  {
    int i = this.a.a(paramView);
    if (i == -1)
    {
      h(paramView);
      return true;
    }
    if (this.b.c(i))
    {
      this.b.d(i);
      h(paramView);
      this.a.a(i);
      return true;
    }
    return false;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.b.toString());
    localStringBuilder.append(", hidden list:");
    localStringBuilder.append(this.c.size());
    return localStringBuilder.toString();
  }
  
  static class a
  {
    long a = 0L;
    a b;
    
    private void b()
    {
      if (this.b == null) {
        this.b = new a();
      }
    }
    
    void a()
    {
      this.a = 0L;
      a locala = this.b;
      if (locala != null) {
        locala.a();
      }
    }
    
    void a(int paramInt)
    {
      if (paramInt >= 64)
      {
        b();
        this.b.a(paramInt - 64);
      }
      else
      {
        this.a |= 1L << paramInt;
      }
    }
    
    void a(int paramInt, boolean paramBoolean)
    {
      if (paramInt >= 64)
      {
        b();
        this.b.a(paramInt - 64, paramBoolean);
      }
      else
      {
        boolean bool;
        if ((this.a & 0x8000000000000000) != 0L) {
          bool = true;
        } else {
          bool = false;
        }
        long l1 = (1L << paramInt) - 1L;
        long l2 = this.a;
        this.a = ((l2 & (l1 ^ 0xFFFFFFFFFFFFFFFF)) << 1 | l2 & l1);
        if (paramBoolean) {
          a(paramInt);
        } else {
          b(paramInt);
        }
        if ((bool) || (this.b != null))
        {
          b();
          this.b.a(0, bool);
        }
      }
    }
    
    void b(int paramInt)
    {
      if (paramInt >= 64)
      {
        a locala = this.b;
        if (locala != null) {
          locala.b(paramInt - 64);
        }
      }
      else
      {
        this.a &= (1L << paramInt ^ 0xFFFFFFFFFFFFFFFF);
      }
    }
    
    boolean c(int paramInt)
    {
      if (paramInt >= 64)
      {
        b();
        return this.b.c(paramInt - 64);
      }
      boolean bool;
      if ((this.a & 1L << paramInt) != 0L) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean d(int paramInt)
    {
      if (paramInt >= 64)
      {
        b();
        return this.b.d(paramInt - 64);
      }
      long l1 = 1L << paramInt;
      boolean bool;
      if ((this.a & l1) != 0L) {
        bool = true;
      } else {
        bool = false;
      }
      this.a &= (l1 ^ 0xFFFFFFFFFFFFFFFF);
      l1 -= 1L;
      long l2 = this.a;
      this.a = (Long.rotateRight(l2 & (l1 ^ 0xFFFFFFFFFFFFFFFF), 1) | l2 & l1);
      a locala = this.b;
      if (locala != null)
      {
        if (locala.c(0)) {
          a(63);
        }
        this.b.d(0);
      }
      return bool;
    }
    
    int e(int paramInt)
    {
      a locala = this.b;
      if (locala == null)
      {
        if (paramInt >= 64) {
          return Long.bitCount(this.a);
        }
        return Long.bitCount(this.a & (1L << paramInt) - 1L);
      }
      if (paramInt < 64) {
        return Long.bitCount(this.a & (1L << paramInt) - 1L);
      }
      return locala.e(paramInt - 64) + Long.bitCount(this.a);
    }
    
    public String toString()
    {
      Object localObject;
      if (this.b == null)
      {
        localObject = Long.toBinaryString(this.a);
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(this.b.toString());
        ((StringBuilder)localObject).append("xx");
        ((StringBuilder)localObject).append(Long.toBinaryString(this.a));
        localObject = ((StringBuilder)localObject).toString();
      }
      return (String)localObject;
    }
  }
  
  static abstract interface b
  {
    public abstract int a();
    
    public abstract int a(View paramView);
    
    public abstract void a(int paramInt);
    
    public abstract void a(View paramView, int paramInt);
    
    public abstract void a(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams);
    
    public abstract RecyclerView.ViewHolder b(View paramView);
    
    public abstract View b(int paramInt);
    
    public abstract void b();
    
    public abstract void c(int paramInt);
    
    public abstract void c(View paramView);
    
    public abstract void d(View paramView);
  }
}


/* Location:              ~/android/support/v7/widget/r.class
 *
 * Reversed by:           J
 */