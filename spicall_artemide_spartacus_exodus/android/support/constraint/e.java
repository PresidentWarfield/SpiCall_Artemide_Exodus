package android.support.constraint;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class e
  extends View
{
  public e(Context paramContext)
  {
    super(paramContext);
    super.setVisibility(8);
  }
  
  public void draw(Canvas paramCanvas) {}
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension(0, 0);
  }
  
  public void setGuidelineBegin(int paramInt)
  {
    ConstraintLayout.a locala = (ConstraintLayout.a)getLayoutParams();
    locala.a = paramInt;
    setLayoutParams(locala);
  }
  
  public void setGuidelineEnd(int paramInt)
  {
    ConstraintLayout.a locala = (ConstraintLayout.a)getLayoutParams();
    locala.b = paramInt;
    setLayoutParams(locala);
  }
  
  public void setGuidelinePercent(float paramFloat)
  {
    ConstraintLayout.a locala = (ConstraintLayout.a)getLayoutParams();
    locala.c = paramFloat;
    setLayoutParams(locala);
  }
  
  public void setVisibility(int paramInt) {}
}


/* Location:              ~/android/support/constraint/e.class
 *
 * Reversed by:           J
 */