package android.support.constraint;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;

public class f
  extends View
{
  private int a;
  private View b;
  private int c;
  
  public void a(ConstraintLayout paramConstraintLayout)
  {
    if ((this.a == -1) && (!isInEditMode())) {
      setVisibility(this.c);
    }
    this.b = paramConstraintLayout.findViewById(this.a);
    paramConstraintLayout = this.b;
    if (paramConstraintLayout != null)
    {
      ((ConstraintLayout.a)paramConstraintLayout.getLayoutParams()).aa = true;
      this.b.setVisibility(0);
      setVisibility(0);
    }
  }
  
  public void b(ConstraintLayout paramConstraintLayout)
  {
    if (this.b == null) {
      return;
    }
    paramConstraintLayout = (ConstraintLayout.a)getLayoutParams();
    ConstraintLayout.a locala = (ConstraintLayout.a)this.b.getLayoutParams();
    locala.al.e(0);
    paramConstraintLayout.al.j(locala.al.p());
    paramConstraintLayout.al.k(locala.al.r());
    locala.al.e(8);
  }
  
  public View getContent()
  {
    return this.b;
  }
  
  public int getEmptyVisibility()
  {
    return this.c;
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    if (isInEditMode())
    {
      paramCanvas.drawRGB(223, 223, 223);
      Paint localPaint = new Paint();
      localPaint.setARGB(255, 210, 210, 210);
      localPaint.setTextAlign(Paint.Align.CENTER);
      localPaint.setTypeface(Typeface.create(Typeface.DEFAULT, 0));
      Rect localRect = new Rect();
      paramCanvas.getClipBounds(localRect);
      localPaint.setTextSize(localRect.height());
      int i = localRect.height();
      int j = localRect.width();
      localPaint.setTextAlign(Paint.Align.LEFT);
      localPaint.getTextBounds("?", 0, 1, localRect);
      paramCanvas.drawText("?", j / 2.0F - localRect.width() / 2.0F - localRect.left, i / 2.0F + localRect.height() / 2.0F - localRect.bottom, localPaint);
    }
  }
  
  public void setContentId(int paramInt)
  {
    if (this.a == paramInt) {
      return;
    }
    View localView = this.b;
    if (localView != null)
    {
      localView.setVisibility(0);
      ((ConstraintLayout.a)this.b.getLayoutParams()).aa = false;
      this.b = null;
    }
    this.a = paramInt;
    if (paramInt != -1)
    {
      localView = ((View)getParent()).findViewById(paramInt);
      if (localView != null) {
        localView.setVisibility(8);
      }
    }
  }
  
  public void setEmptyVisibility(int paramInt)
  {
    this.c = paramInt;
  }
}


/* Location:              ~/android/support/constraint/f.class
 *
 * Reversed by:           J
 */