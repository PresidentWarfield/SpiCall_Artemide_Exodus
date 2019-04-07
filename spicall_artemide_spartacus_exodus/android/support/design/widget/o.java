package android.support.design.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

class o<V extends View>
  extends CoordinatorLayout.a<V>
{
  private p a;
  private int b = 0;
  private int c = 0;
  
  public o() {}
  
  public o(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public boolean a(int paramInt)
  {
    p localp = this.a;
    if (localp != null) {
      return localp.a(paramInt);
    }
    this.b = paramInt;
    return false;
  }
  
  public boolean a(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt)
  {
    b(paramCoordinatorLayout, paramV, paramInt);
    if (this.a == null) {
      this.a = new p(paramV);
    }
    this.a.a();
    paramInt = this.b;
    if (paramInt != 0)
    {
      this.a.a(paramInt);
      this.b = 0;
    }
    paramInt = this.c;
    if (paramInt != 0)
    {
      this.a.b(paramInt);
      this.c = 0;
    }
    return true;
  }
  
  public int b()
  {
    p localp = this.a;
    int i;
    if (localp != null) {
      i = localp.b();
    } else {
      i = 0;
    }
    return i;
  }
  
  protected void b(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt)
  {
    paramCoordinatorLayout.a(paramV, paramInt);
  }
}


/* Location:              ~/android/support/design/widget/o.class
 *
 * Reversed by:           J
 */