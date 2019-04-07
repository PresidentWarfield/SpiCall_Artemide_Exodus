package android.support.constraint;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;

public class a
  extends b
{
  private int f;
  private int g;
  private android.support.constraint.a.a.b h;
  
  public a(Context paramContext)
  {
    super(paramContext);
    super.setVisibility(8);
  }
  
  protected void a(AttributeSet paramAttributeSet)
  {
    super.a(paramAttributeSet);
    this.h = new android.support.constraint.a.a.b();
    if (paramAttributeSet != null)
    {
      paramAttributeSet = getContext().obtainStyledAttributes(paramAttributeSet, g.b.ConstraintLayout_Layout);
      int i = paramAttributeSet.getIndexCount();
      for (int j = 0; j < i; j++)
      {
        int k = paramAttributeSet.getIndex(j);
        if (k == g.b.ConstraintLayout_Layout_barrierDirection) {
          setType(paramAttributeSet.getInt(k, 0));
        } else if (k == g.b.ConstraintLayout_Layout_barrierAllowsGoneWidgets) {
          this.h.a(paramAttributeSet.getBoolean(k, true));
        }
      }
    }
    this.d = this.h;
    a();
  }
  
  public int getType()
  {
    return this.f;
  }
  
  public void setAllowsGoneWidget(boolean paramBoolean)
  {
    this.h.a(paramBoolean);
  }
  
  public void setType(int paramInt)
  {
    this.f = paramInt;
    this.g = paramInt;
    if (Build.VERSION.SDK_INT < 17)
    {
      paramInt = this.f;
      if (paramInt == 5) {
        this.g = 0;
      } else if (paramInt == 6) {
        this.g = 1;
      }
    }
    else
    {
      if (1 == getResources().getConfiguration().getLayoutDirection()) {
        paramInt = 1;
      } else {
        paramInt = 0;
      }
      if (paramInt != 0)
      {
        paramInt = this.f;
        if (paramInt == 5) {
          this.g = 1;
        } else if (paramInt == 6) {
          this.g = 0;
        }
      }
      else
      {
        paramInt = this.f;
        if (paramInt == 5) {
          this.g = 0;
        } else if (paramInt == 6) {
          this.g = 1;
        }
      }
    }
    this.h.a(this.g);
  }
}


/* Location:              ~/android/support/constraint/a.class
 *
 * Reversed by:           J
 */