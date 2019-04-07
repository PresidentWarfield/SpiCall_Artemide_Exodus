package android.support.v7.widget;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.view.View;

public class TooltipCompat
{
  private static final c IMPL;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 26) {
      IMPL = new a(null);
    } else {
      IMPL = new b(null);
    }
  }
  
  public static void setTooltipText(View paramView, CharSequence paramCharSequence)
  {
    IMPL.a(paramView, paramCharSequence);
  }
  
  @TargetApi(26)
  private static class a
    implements TooltipCompat.c
  {
    public void a(View paramView, CharSequence paramCharSequence)
    {
      paramView.setTooltipText(paramCharSequence);
    }
  }
  
  private static class b
    implements TooltipCompat.c
  {
    public void a(View paramView, CharSequence paramCharSequence)
    {
      ag.a(paramView, paramCharSequence);
    }
  }
  
  private static abstract interface c
  {
    public abstract void a(View paramView, CharSequence paramCharSequence);
  }
}


/* Location:              ~/android/support/v7/widget/TooltipCompat.class
 *
 * Reversed by:           J
 */