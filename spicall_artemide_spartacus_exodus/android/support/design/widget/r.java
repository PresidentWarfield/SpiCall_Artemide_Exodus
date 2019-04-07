package android.support.design.widget;

import android.widget.ImageButton;

class r
  extends ImageButton
{
  private int a;
  
  final void a(int paramInt, boolean paramBoolean)
  {
    super.setVisibility(paramInt);
    if (paramBoolean) {
      this.a = paramInt;
    }
  }
  
  final int getUserSetVisibility()
  {
    return this.a;
  }
  
  public void setVisibility(int paramInt)
  {
    a(paramInt, true);
  }
}


/* Location:              ~/android/support/design/widget/r.class
 *
 * Reversed by:           J
 */