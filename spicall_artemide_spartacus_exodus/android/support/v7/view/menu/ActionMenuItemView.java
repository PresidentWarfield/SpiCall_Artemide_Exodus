package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.a.a.j;
import android.support.v7.widget.ActionMenuView.ActionMenuChildView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ForwardingListener;
import android.support.v7.widget.TooltipCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;

public class ActionMenuItemView
  extends AppCompatTextView
  implements p.a, ActionMenuView.ActionMenuChildView, View.OnClickListener
{
  j a;
  h.b b;
  b c;
  private CharSequence d;
  private Drawable e;
  private ForwardingListener f;
  private boolean g;
  private boolean h;
  private int i;
  private int j;
  private int k;
  
  public ActionMenuItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ActionMenuItemView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Resources localResources = paramContext.getResources();
    this.g = c();
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, a.j.ActionMenuItemView, paramInt, 0);
    this.i = paramContext.getDimensionPixelSize(a.j.ActionMenuItemView_android_minWidth, 0);
    paramContext.recycle();
    this.k = ((int)(localResources.getDisplayMetrics().density * 32.0F + 0.5F));
    setOnClickListener(this);
    this.j = -1;
    setSaveEnabled(false);
  }
  
  private boolean c()
  {
    Configuration localConfiguration = getContext().getResources().getConfiguration();
    int m = localConfiguration.screenWidthDp;
    int n = localConfiguration.screenHeightDp;
    boolean bool;
    if ((m < 480) && ((m < 640) || (n < 480)) && (localConfiguration.orientation != 2)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private void d()
  {
    boolean bool = TextUtils.isEmpty(this.d);
    int m = 1;
    int n = m;
    if (this.e != null)
    {
      if (this.a.l())
      {
        n = m;
        if (this.g) {
          break label52;
        }
        if (this.h)
        {
          n = m;
          break label52;
        }
      }
      n = 0;
    }
    label52:
    n = (bool ^ true) & n;
    Object localObject1 = null;
    if (n != 0) {
      localObject2 = this.d;
    } else {
      localObject2 = null;
    }
    setText((CharSequence)localObject2);
    Object localObject2 = this.a.getContentDescription();
    if (TextUtils.isEmpty((CharSequence)localObject2))
    {
      if (n != 0) {
        localObject2 = null;
      } else {
        localObject2 = this.a.getTitle();
      }
      setContentDescription((CharSequence)localObject2);
    }
    else
    {
      setContentDescription((CharSequence)localObject2);
    }
    localObject2 = this.a.getTooltipText();
    if (TextUtils.isEmpty((CharSequence)localObject2))
    {
      if (n != 0) {
        localObject2 = localObject1;
      } else {
        localObject2 = this.a.getTitle();
      }
      TooltipCompat.setTooltipText(this, (CharSequence)localObject2);
    }
    else
    {
      TooltipCompat.setTooltipText(this, (CharSequence)localObject2);
    }
  }
  
  public void a(j paramj, int paramInt)
  {
    this.a = paramj;
    setIcon(paramj.getIcon());
    setTitle(paramj.a(this));
    setId(paramj.getItemId());
    if (paramj.isVisible()) {
      paramInt = 0;
    } else {
      paramInt = 8;
    }
    setVisibility(paramInt);
    setEnabled(paramj.isEnabled());
    if ((paramj.hasSubMenu()) && (this.f == null)) {
      this.f = new a();
    }
  }
  
  public boolean a()
  {
    return TextUtils.isEmpty(getText()) ^ true;
  }
  
  public boolean b()
  {
    return true;
  }
  
  public j getItemData()
  {
    return this.a;
  }
  
  public boolean needsDividerAfter()
  {
    return a();
  }
  
  public boolean needsDividerBefore()
  {
    boolean bool;
    if ((a()) && (this.a.getIcon() == null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void onClick(View paramView)
  {
    paramView = this.b;
    if (paramView != null) {
      paramView.invokeItem(this.a);
    }
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    this.g = c();
    d();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    boolean bool = a();
    if (bool)
    {
      m = this.j;
      if (m >= 0) {
        super.setPadding(m, getPaddingTop(), getPaddingRight(), getPaddingBottom());
      }
    }
    super.onMeasure(paramInt1, paramInt2);
    int m = View.MeasureSpec.getMode(paramInt1);
    paramInt1 = View.MeasureSpec.getSize(paramInt1);
    int n = getMeasuredWidth();
    if (m == Integer.MIN_VALUE) {
      paramInt1 = Math.min(paramInt1, this.i);
    } else {
      paramInt1 = this.i;
    }
    if ((m != 1073741824) && (this.i > 0) && (n < paramInt1)) {
      super.onMeasure(View.MeasureSpec.makeMeasureSpec(paramInt1, 1073741824), paramInt2);
    }
    if ((!bool) && (this.e != null)) {
      super.setPadding((getMeasuredWidth() - this.e.getBounds().width()) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    super.onRestoreInstanceState(null);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.a.hasSubMenu())
    {
      ForwardingListener localForwardingListener = this.f;
      if ((localForwardingListener != null) && (localForwardingListener.onTouch(this, paramMotionEvent))) {
        return true;
      }
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void setCheckable(boolean paramBoolean) {}
  
  public void setChecked(boolean paramBoolean) {}
  
  public void setExpandedFormat(boolean paramBoolean)
  {
    if (this.h != paramBoolean)
    {
      this.h = paramBoolean;
      j localj = this.a;
      if (localj != null) {
        localj.g();
      }
    }
  }
  
  public void setIcon(Drawable paramDrawable)
  {
    this.e = paramDrawable;
    if (paramDrawable != null)
    {
      int m = paramDrawable.getIntrinsicWidth();
      int n = paramDrawable.getIntrinsicHeight();
      int i1 = this.k;
      int i2 = m;
      int i3 = n;
      float f1;
      if (m > i1)
      {
        f1 = i1 / m;
        i3 = (int)(n * f1);
        i2 = i1;
      }
      m = this.k;
      n = i2;
      i1 = i3;
      if (i3 > m)
      {
        f1 = m / i3;
        n = (int)(i2 * f1);
        i1 = m;
      }
      paramDrawable.setBounds(0, 0, n, i1);
    }
    setCompoundDrawables(paramDrawable, null, null, null);
    d();
  }
  
  public void setItemInvoker(h.b paramb)
  {
    this.b = paramb;
  }
  
  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.j = paramInt1;
    super.setPadding(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void setPopupCallback(b paramb)
  {
    this.c = paramb;
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    this.d = paramCharSequence;
    d();
  }
  
  private class a
    extends ForwardingListener
  {
    public a()
    {
      super();
    }
    
    public s getPopup()
    {
      if (ActionMenuItemView.this.c != null) {
        return ActionMenuItemView.this.c.a();
      }
      return null;
    }
    
    protected boolean onForwardingStarted()
    {
      Object localObject = ActionMenuItemView.this.b;
      boolean bool1 = false;
      if ((localObject != null) && (ActionMenuItemView.this.b.invokeItem(ActionMenuItemView.this.a)))
      {
        localObject = getPopup();
        boolean bool2 = bool1;
        if (localObject != null)
        {
          bool2 = bool1;
          if (((s)localObject).isShowing()) {
            bool2 = true;
          }
        }
        return bool2;
      }
      return false;
    }
  }
  
  public static abstract class b
  {
    public abstract s a();
  }
}


/* Location:              ~/android/support/v7/view/menu/ActionMenuItemView.class
 *
 * Reversed by:           J
 */