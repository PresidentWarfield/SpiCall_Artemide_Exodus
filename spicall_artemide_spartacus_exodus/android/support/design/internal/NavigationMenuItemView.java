package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.StateListDrawable;
import android.support.design.a.c;
import android.support.design.a.d;
import android.support.design.a.e;
import android.support.design.a.g;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.a.a.a;
import android.support.v7.view.menu.j;
import android.support.v7.view.menu.p.a;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.support.v7.widget.TooltipCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;

public class NavigationMenuItemView
  extends a
  implements p.a
{
  private static final int[] d = { 16842912 };
  boolean c;
  private final int e;
  private boolean f;
  private final CheckedTextView g;
  private FrameLayout h;
  private j i;
  private ColorStateList j;
  private boolean k;
  private Drawable l;
  private final AccessibilityDelegateCompat m = new AccessibilityDelegateCompat()
  {
    public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
    {
      super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
      paramAnonymousAccessibilityNodeInfoCompat.setCheckable(NavigationMenuItemView.this.c);
    }
  };
  
  public NavigationMenuItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public NavigationMenuItemView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setOrientation(0);
    LayoutInflater.from(paramContext).inflate(a.g.design_navigation_menu_item, this, true);
    this.e = paramContext.getResources().getDimensionPixelSize(a.c.design_navigation_icon_size);
    this.g = ((CheckedTextView)findViewById(a.e.design_menu_item_text));
    this.g.setDuplicateParentStateEnabled(true);
    ViewCompat.setAccessibilityDelegate(this.g, this.m);
  }
  
  private boolean c()
  {
    boolean bool;
    if ((this.i.getTitle() == null) && (this.i.getIcon() == null) && (this.i.getActionView() != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void d()
  {
    Object localObject;
    if (c())
    {
      this.g.setVisibility(8);
      localObject = this.h;
      if (localObject != null)
      {
        localObject = (LinearLayoutCompat.LayoutParams)((FrameLayout)localObject).getLayoutParams();
        ((LinearLayoutCompat.LayoutParams)localObject).width = -1;
        this.h.setLayoutParams((ViewGroup.LayoutParams)localObject);
      }
    }
    else
    {
      this.g.setVisibility(0);
      localObject = this.h;
      if (localObject != null)
      {
        localObject = (LinearLayoutCompat.LayoutParams)((FrameLayout)localObject).getLayoutParams();
        ((LinearLayoutCompat.LayoutParams)localObject).width = -2;
        this.h.setLayoutParams((ViewGroup.LayoutParams)localObject);
      }
    }
  }
  
  private StateListDrawable e()
  {
    TypedValue localTypedValue = new TypedValue();
    if (getContext().getTheme().resolveAttribute(a.a.colorControlHighlight, localTypedValue, true))
    {
      StateListDrawable localStateListDrawable = new StateListDrawable();
      localStateListDrawable.addState(d, new ColorDrawable(localTypedValue.data));
      localStateListDrawable.addState(EMPTY_STATE_SET, new ColorDrawable(0));
      return localStateListDrawable;
    }
    return null;
  }
  
  private void setActionView(View paramView)
  {
    if (paramView != null)
    {
      if (this.h == null) {
        this.h = ((FrameLayout)((ViewStub)findViewById(a.e.design_menu_item_action_area_stub)).inflate());
      }
      this.h.removeAllViews();
      this.h.addView(paramView);
    }
  }
  
  public void a()
  {
    FrameLayout localFrameLayout = this.h;
    if (localFrameLayout != null) {
      localFrameLayout.removeAllViews();
    }
    this.g.setCompoundDrawables(null, null, null, null);
  }
  
  public void a(j paramj, int paramInt)
  {
    this.i = paramj;
    if (paramj.isVisible()) {
      paramInt = 0;
    } else {
      paramInt = 8;
    }
    setVisibility(paramInt);
    if (getBackground() == null) {
      ViewCompat.setBackground(this, e());
    }
    setCheckable(paramj.isCheckable());
    setChecked(paramj.isChecked());
    setEnabled(paramj.isEnabled());
    setTitle(paramj.getTitle());
    setIcon(paramj.getIcon());
    setActionView(paramj.getActionView());
    setContentDescription(paramj.getContentDescription());
    TooltipCompat.setTooltipText(this, paramj.getTooltipText());
    d();
  }
  
  public boolean b()
  {
    return false;
  }
  
  public j getItemData()
  {
    return this.i;
  }
  
  protected int[] onCreateDrawableState(int paramInt)
  {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 1);
    j localj = this.i;
    if ((localj != null) && (localj.isCheckable()) && (this.i.isChecked())) {
      mergeDrawableStates(arrayOfInt, d);
    }
    return arrayOfInt;
  }
  
  public void setCheckable(boolean paramBoolean)
  {
    refreshDrawableState();
    if (this.c != paramBoolean)
    {
      this.c = paramBoolean;
      this.m.sendAccessibilityEvent(this.g, 2048);
    }
  }
  
  public void setChecked(boolean paramBoolean)
  {
    refreshDrawableState();
    this.g.setChecked(paramBoolean);
  }
  
  public void setIcon(Drawable paramDrawable)
  {
    int n;
    if (paramDrawable != null)
    {
      Object localObject = paramDrawable;
      if (this.k)
      {
        localObject = paramDrawable.getConstantState();
        if (localObject != null) {
          paramDrawable = ((Drawable.ConstantState)localObject).newDrawable();
        }
        localObject = DrawableCompat.wrap(paramDrawable).mutate();
        DrawableCompat.setTintList((Drawable)localObject, this.j);
      }
      n = this.e;
      ((Drawable)localObject).setBounds(0, 0, n, n);
      paramDrawable = (Drawable)localObject;
    }
    else if (this.f)
    {
      if (this.l == null)
      {
        this.l = ResourcesCompat.getDrawable(getResources(), a.d.navigation_empty_icon, getContext().getTheme());
        paramDrawable = this.l;
        if (paramDrawable != null)
        {
          n = this.e;
          paramDrawable.setBounds(0, 0, n, n);
        }
      }
      paramDrawable = this.l;
    }
    TextViewCompat.setCompoundDrawablesRelative(this.g, paramDrawable, null, null, null);
  }
  
  void setIconTintList(ColorStateList paramColorStateList)
  {
    this.j = paramColorStateList;
    boolean bool;
    if (this.j != null) {
      bool = true;
    } else {
      bool = false;
    }
    this.k = bool;
    paramColorStateList = this.i;
    if (paramColorStateList != null) {
      setIcon(paramColorStateList.getIcon());
    }
  }
  
  public void setNeedsEmptyIcon(boolean paramBoolean)
  {
    this.f = paramBoolean;
  }
  
  public void setTextAppearance(int paramInt)
  {
    TextViewCompat.setTextAppearance(this.g, paramInt);
  }
  
  public void setTextColor(ColorStateList paramColorStateList)
  {
    this.g.setTextColor(paramColorStateList);
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    this.g.setText(paramCharSequence);
  }
}


/* Location:              ~/android/support/design/internal/NavigationMenuItemView.class
 *
 * Reversed by:           J
 */