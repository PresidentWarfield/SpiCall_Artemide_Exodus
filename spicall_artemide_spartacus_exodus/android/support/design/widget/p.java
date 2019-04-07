package android.support.design.widget;

import android.support.v4.view.ViewCompat;
import android.view.View;

class p
{
  private final View a;
  private int b;
  private int c;
  private int d;
  private int e;
  
  public p(View paramView)
  {
    this.a = paramView;
  }
  
  private void c()
  {
    View localView = this.a;
    ViewCompat.offsetTopAndBottom(localView, this.d - (localView.getTop() - this.b));
    localView = this.a;
    ViewCompat.offsetLeftAndRight(localView, this.e - (localView.getLeft() - this.c));
  }
  
  public void a()
  {
    this.b = this.a.getTop();
    this.c = this.a.getLeft();
    c();
  }
  
  public boolean a(int paramInt)
  {
    if (this.d != paramInt)
    {
      this.d = paramInt;
      c();
      return true;
    }
    return false;
  }
  
  public int b()
  {
    return this.d;
  }
  
  public boolean b(int paramInt)
  {
    if (this.e != paramInt)
    {
      this.e = paramInt;
      c();
      return true;
    }
    return false;
  }
}


/* Location:              ~/android/support/design/widget/p.class
 *
 * Reversed by:           J
 */