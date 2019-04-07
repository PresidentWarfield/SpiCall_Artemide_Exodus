package android.support.v7.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.a.a.j;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.PopupWindow;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

class g
  extends PopupWindow
{
  private static final boolean a;
  private boolean b;
  
  static
  {
    boolean bool;
    if (Build.VERSION.SDK_INT < 21) {
      bool = true;
    } else {
      bool = false;
    }
    a = bool;
  }
  
  public g(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    a(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  private void a(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    paramContext = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, a.j.PopupWindow, paramInt1, paramInt2);
    if (paramContext.hasValue(a.j.PopupWindow_overlapAnchor)) {
      a(paramContext.getBoolean(a.j.PopupWindow_overlapAnchor, false));
    }
    setBackgroundDrawable(paramContext.getDrawable(a.j.PopupWindow_android_popupBackground));
    paramInt1 = Build.VERSION.SDK_INT;
    if ((paramInt2 != 0) && (paramInt1 < 11) && (paramContext.hasValue(a.j.PopupWindow_android_popupAnimationStyle))) {
      setAnimationStyle(paramContext.getResourceId(a.j.PopupWindow_android_popupAnimationStyle, -1));
    }
    paramContext.recycle();
    if (Build.VERSION.SDK_INT < 14) {
      a(this);
    }
  }
  
  private static void a(PopupWindow paramPopupWindow)
  {
    try
    {
      Field localField1 = PopupWindow.class.getDeclaredField("mAnchor");
      localField1.setAccessible(true);
      Field localField2 = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
      localField2.setAccessible(true);
      ViewTreeObserver.OnScrollChangedListener localOnScrollChangedListener = (ViewTreeObserver.OnScrollChangedListener)localField2.get(paramPopupWindow);
      ViewTreeObserver.OnScrollChangedListener local1 = new android/support/v7/widget/g$1;
      local1.<init>(localField1, paramPopupWindow, localOnScrollChangedListener);
      localField2.set(paramPopupWindow, local1);
    }
    catch (Exception paramPopupWindow)
    {
      Log.d("AppCompatPopupWindow", "Exception while installing workaround OnScrollChangedListener", paramPopupWindow);
    }
  }
  
  public void a(boolean paramBoolean)
  {
    if (a) {
      this.b = paramBoolean;
    } else {
      PopupWindowCompat.setOverlapAnchor(this, paramBoolean);
    }
  }
  
  public void showAsDropDown(View paramView, int paramInt1, int paramInt2)
  {
    int i = paramInt2;
    if (a)
    {
      i = paramInt2;
      if (this.b) {
        i = paramInt2 - paramView.getHeight();
      }
    }
    super.showAsDropDown(paramView, paramInt1, i);
  }
  
  public void showAsDropDown(View paramView, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt2;
    if (a)
    {
      i = paramInt2;
      if (this.b) {
        i = paramInt2 - paramView.getHeight();
      }
    }
    super.showAsDropDown(paramView, paramInt1, i, paramInt3);
  }
  
  public void update(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((a) && (this.b)) {
      paramInt2 -= paramView.getHeight();
    }
    super.update(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
}


/* Location:              ~/android/support/v7/widget/g.class
 *
 * Reversed by:           J
 */