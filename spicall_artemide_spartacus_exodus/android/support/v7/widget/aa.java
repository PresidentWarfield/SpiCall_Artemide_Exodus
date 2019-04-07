package android.support.v7.widget;

class aa
{
  private int a = 0;
  private int b = 0;
  private int c = Integer.MIN_VALUE;
  private int d = Integer.MIN_VALUE;
  private int e = 0;
  private int f = 0;
  private boolean g = false;
  private boolean h = false;
  
  public int a()
  {
    return this.a;
  }
  
  public void a(int paramInt1, int paramInt2)
  {
    this.c = paramInt1;
    this.d = paramInt2;
    this.h = true;
    if (this.g)
    {
      if (paramInt2 != Integer.MIN_VALUE) {
        this.a = paramInt2;
      }
      if (paramInt1 != Integer.MIN_VALUE) {
        this.b = paramInt1;
      }
    }
    else
    {
      if (paramInt1 != Integer.MIN_VALUE) {
        this.a = paramInt1;
      }
      if (paramInt2 != Integer.MIN_VALUE) {
        this.b = paramInt2;
      }
    }
  }
  
  public void a(boolean paramBoolean)
  {
    if (paramBoolean == this.g) {
      return;
    }
    this.g = paramBoolean;
    if (this.h)
    {
      int i;
      if (paramBoolean)
      {
        i = this.d;
        if (i == Integer.MIN_VALUE) {
          i = this.e;
        }
        this.a = i;
        i = this.c;
        if (i == Integer.MIN_VALUE) {
          i = this.f;
        }
        this.b = i;
      }
      else
      {
        i = this.c;
        if (i == Integer.MIN_VALUE) {
          i = this.e;
        }
        this.a = i;
        i = this.d;
        if (i == Integer.MIN_VALUE) {
          i = this.f;
        }
        this.b = i;
      }
    }
    else
    {
      this.a = this.e;
      this.b = this.f;
    }
  }
  
  public int b()
  {
    return this.b;
  }
  
  public void b(int paramInt1, int paramInt2)
  {
    this.h = false;
    if (paramInt1 != Integer.MIN_VALUE)
    {
      this.e = paramInt1;
      this.a = paramInt1;
    }
    if (paramInt2 != Integer.MIN_VALUE)
    {
      this.f = paramInt2;
      this.b = paramInt2;
    }
  }
  
  public int c()
  {
    int i;
    if (this.g) {
      i = this.b;
    } else {
      i = this.a;
    }
    return i;
  }
  
  public int d()
  {
    int i;
    if (this.g) {
      i = this.a;
    } else {
      i = this.b;
    }
    return i;
  }
}


/* Location:              ~/android/support/v7/widget/aa.class
 *
 * Reversed by:           J
 */