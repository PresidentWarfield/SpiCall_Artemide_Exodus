package android.support.v7.widget;

import android.graphics.Outline;
import android.graphics.drawable.Drawable;

class b
  extends a
{
  public b(ActionBarContainer paramActionBarContainer)
  {
    super(paramActionBarContainer);
  }
  
  public void getOutline(Outline paramOutline)
  {
    if (this.a.mIsSplit)
    {
      if (this.a.mSplitBackground != null) {
        this.a.mSplitBackground.getOutline(paramOutline);
      }
    }
    else if (this.a.mBackground != null) {
      this.a.mBackground.getOutline(paramOutline);
    }
  }
}


/* Location:              ~/android/support/v7/widget/b.class
 *
 * Reversed by:           J
 */