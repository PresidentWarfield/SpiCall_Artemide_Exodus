package android.support.v7.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.a.a.d;
import android.support.v7.a.a.f;
import android.support.v7.a.a.g;
import android.support.v7.a.a.i;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

class ah
{
  private final Context a;
  private final View b;
  private final TextView c;
  private final WindowManager.LayoutParams d = new WindowManager.LayoutParams();
  private final Rect e = new Rect();
  private final int[] f = new int[2];
  private final int[] g = new int[2];
  
  ah(Context paramContext)
  {
    this.a = paramContext;
    this.b = LayoutInflater.from(this.a).inflate(a.g.tooltip, null);
    this.c = ((TextView)this.b.findViewById(a.f.message));
    this.d.setTitle(getClass().getSimpleName());
    this.d.packageName = this.a.getPackageName();
    paramContext = this.d;
    paramContext.type = 1002;
    paramContext.width = -2;
    paramContext.height = -2;
    paramContext.format = -3;
    paramContext.windowAnimations = a.i.Animation_AppCompat_Tooltip;
    this.d.flags = 24;
  }
  
  private static View a(View paramView)
  {
    for (Context localContext = paramView.getContext(); (localContext instanceof ContextWrapper); localContext = ((ContextWrapper)localContext).getBaseContext()) {
      if ((localContext instanceof Activity)) {
        return ((Activity)localContext).getWindow().getDecorView();
      }
    }
    return paramView.getRootView();
  }
  
  private void a(View paramView, int paramInt1, int paramInt2, boolean paramBoolean, WindowManager.LayoutParams paramLayoutParams)
  {
    int i = this.a.getResources().getDimensionPixelOffset(a.d.tooltip_precise_anchor_threshold);
    if (paramView.getWidth() < i) {
      paramInt1 = paramView.getWidth() / 2;
    }
    if (paramView.getHeight() >= i)
    {
      j = this.a.getResources().getDimensionPixelOffset(a.d.tooltip_precise_anchor_extra_offset);
      i = paramInt2 + j;
      j = paramInt2 - j;
      paramInt2 = i;
      i = j;
    }
    else
    {
      paramInt2 = paramView.getHeight();
      i = 0;
    }
    paramLayoutParams.gravity = 49;
    Object localObject1 = this.a.getResources();
    if (paramBoolean) {
      j = a.d.tooltip_y_offset_touch;
    } else {
      j = a.d.tooltip_y_offset_non_touch;
    }
    int k = ((Resources)localObject1).getDimensionPixelOffset(j);
    localObject1 = a(paramView);
    if (localObject1 == null)
    {
      Log.e("TooltipPopup", "Cannot find app view");
      return;
    }
    ((View)localObject1).getWindowVisibleDisplayFrame(this.e);
    if ((this.e.left < 0) && (this.e.top < 0))
    {
      Object localObject2 = this.a.getResources();
      j = ((Resources)localObject2).getIdentifier("status_bar_height", "dimen", "android");
      if (j != 0) {
        j = ((Resources)localObject2).getDimensionPixelSize(j);
      } else {
        j = 0;
      }
      localObject2 = ((Resources)localObject2).getDisplayMetrics();
      this.e.set(0, j, ((DisplayMetrics)localObject2).widthPixels, ((DisplayMetrics)localObject2).heightPixels);
    }
    ((View)localObject1).getLocationOnScreen(this.g);
    paramView.getLocationOnScreen(this.f);
    paramView = this.f;
    int j = paramView[0];
    localObject1 = this.g;
    paramView[0] = (j - localObject1[0]);
    paramView[1] -= localObject1[1];
    paramLayoutParams.x = (paramView[0] + paramInt1 - this.e.width() / 2);
    paramInt1 = View.MeasureSpec.makeMeasureSpec(0, 0);
    this.b.measure(paramInt1, paramInt1);
    paramInt1 = this.b.getMeasuredHeight();
    paramView = this.f;
    i = paramView[1] + i - k - paramInt1;
    paramInt2 = paramView[1] + paramInt2 + k;
    if (paramBoolean)
    {
      if (i >= 0) {
        paramLayoutParams.y = i;
      } else {
        paramLayoutParams.y = paramInt2;
      }
    }
    else if (paramInt1 + paramInt2 <= this.e.height()) {
      paramLayoutParams.y = paramInt2;
    } else {
      paramLayoutParams.y = i;
    }
  }
  
  void a()
  {
    if (!b()) {
      return;
    }
    ((WindowManager)this.a.getSystemService("window")).removeView(this.b);
  }
  
  void a(View paramView, int paramInt1, int paramInt2, boolean paramBoolean, CharSequence paramCharSequence)
  {
    if (b()) {
      a();
    }
    this.c.setText(paramCharSequence);
    a(paramView, paramInt1, paramInt2, paramBoolean, this.d);
    ((WindowManager)this.a.getSystemService("window")).addView(this.b, this.d);
  }
  
  boolean b()
  {
    boolean bool;
    if (this.b.getParent() != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:              ~/android/support/v7/widget/ah.class
 *
 * Reversed by:           J
 */