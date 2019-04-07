package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.a.a.a;
import android.support.v7.a.a.f;
import android.support.v7.a.a.g;
import android.support.v7.a.a.j;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.TextView;

public class ListMenuItemView
  extends LinearLayout
  implements p.a
{
  private j a;
  private ImageView b;
  private RadioButton c;
  private TextView d;
  private CheckBox e;
  private TextView f;
  private ImageView g;
  private Drawable h;
  private int i;
  private Context j;
  private boolean k;
  private Drawable l;
  private int m;
  private LayoutInflater n;
  private boolean o;
  
  public ListMenuItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, a.a.listMenuViewStyle);
  }
  
  public ListMenuItemView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet);
    paramAttributeSet = TintTypedArray.obtainStyledAttributes(getContext(), paramAttributeSet, a.j.MenuView, paramInt, 0);
    this.h = paramAttributeSet.getDrawable(a.j.MenuView_android_itemBackground);
    this.i = paramAttributeSet.getResourceId(a.j.MenuView_android_itemTextAppearance, -1);
    this.k = paramAttributeSet.getBoolean(a.j.MenuView_preserveIconSpacing, false);
    this.j = paramContext;
    this.l = paramAttributeSet.getDrawable(a.j.MenuView_subMenuArrow);
    paramAttributeSet.recycle();
  }
  
  private void a()
  {
    this.b = ((ImageView)getInflater().inflate(a.g.abc_list_menu_item_icon, this, false));
    addView(this.b, 0);
  }
  
  private void c()
  {
    this.c = ((RadioButton)getInflater().inflate(a.g.abc_list_menu_item_radio, this, false));
    addView(this.c);
  }
  
  private void d()
  {
    this.e = ((CheckBox)getInflater().inflate(a.g.abc_list_menu_item_checkbox, this, false));
    addView(this.e);
  }
  
  private LayoutInflater getInflater()
  {
    if (this.n == null) {
      this.n = LayoutInflater.from(getContext());
    }
    return this.n;
  }
  
  private void setSubMenuArrowVisible(boolean paramBoolean)
  {
    ImageView localImageView = this.g;
    if (localImageView != null)
    {
      int i1;
      if (paramBoolean) {
        i1 = 0;
      } else {
        i1 = 8;
      }
      localImageView.setVisibility(i1);
    }
  }
  
  public void a(j paramj, int paramInt)
  {
    this.a = paramj;
    this.m = paramInt;
    if (paramj.isVisible()) {
      paramInt = 0;
    } else {
      paramInt = 8;
    }
    setVisibility(paramInt);
    setTitle(paramj.a(this));
    setCheckable(paramj.isCheckable());
    a(paramj.e(), paramj.c());
    setIcon(paramj.getIcon());
    setEnabled(paramj.isEnabled());
    setSubMenuArrowVisible(paramj.hasSubMenu());
    setContentDescription(paramj.getContentDescription());
  }
  
  public void a(boolean paramBoolean, char paramChar)
  {
    if ((paramBoolean) && (this.a.e())) {
      paramChar = '\000';
    } else {
      paramChar = '\b';
    }
    if (paramChar == 0) {
      this.f.setText(this.a.d());
    }
    if (this.f.getVisibility() != paramChar) {
      this.f.setVisibility(paramChar);
    }
  }
  
  public boolean b()
  {
    return false;
  }
  
  public j getItemData()
  {
    return this.a;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    ViewCompat.setBackground(this, this.h);
    this.d = ((TextView)findViewById(a.f.title));
    int i1 = this.i;
    if (i1 != -1) {
      this.d.setTextAppearance(this.j, i1);
    }
    this.f = ((TextView)findViewById(a.f.shortcut));
    this.g = ((ImageView)findViewById(a.f.submenuarrow));
    ImageView localImageView = this.g;
    if (localImageView != null) {
      localImageView.setImageDrawable(this.l);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if ((this.b != null) && (this.k))
    {
      ViewGroup.LayoutParams localLayoutParams = getLayoutParams();
      LinearLayout.LayoutParams localLayoutParams1 = (LinearLayout.LayoutParams)this.b.getLayoutParams();
      if ((localLayoutParams.height > 0) && (localLayoutParams1.width <= 0)) {
        localLayoutParams1.width = localLayoutParams.height;
      }
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void setCheckable(boolean paramBoolean)
  {
    if ((!paramBoolean) && (this.c == null) && (this.e == null)) {
      return;
    }
    Object localObject1;
    Object localObject2;
    if (this.a.f())
    {
      if (this.c == null) {
        c();
      }
      localObject1 = this.c;
      localObject2 = this.e;
    }
    else
    {
      if (this.e == null) {
        d();
      }
      localObject1 = this.e;
      localObject2 = this.c;
    }
    if (paramBoolean)
    {
      ((CompoundButton)localObject1).setChecked(this.a.isChecked());
      int i1;
      if (paramBoolean) {
        i1 = 0;
      } else {
        i1 = 8;
      }
      if (((CompoundButton)localObject1).getVisibility() != i1) {
        ((CompoundButton)localObject1).setVisibility(i1);
      }
      if ((localObject2 != null) && (((CompoundButton)localObject2).getVisibility() != 8)) {
        ((CompoundButton)localObject2).setVisibility(8);
      }
    }
    else
    {
      localObject1 = this.e;
      if (localObject1 != null) {
        ((CheckBox)localObject1).setVisibility(8);
      }
      localObject1 = this.c;
      if (localObject1 != null) {
        ((RadioButton)localObject1).setVisibility(8);
      }
    }
  }
  
  public void setChecked(boolean paramBoolean)
  {
    Object localObject;
    if (this.a.f())
    {
      if (this.c == null) {
        c();
      }
      localObject = this.c;
    }
    else
    {
      if (this.e == null) {
        d();
      }
      localObject = this.e;
    }
    ((CompoundButton)localObject).setChecked(paramBoolean);
  }
  
  public void setForceShowIcon(boolean paramBoolean)
  {
    this.o = paramBoolean;
    this.k = paramBoolean;
  }
  
  public void setIcon(Drawable paramDrawable)
  {
    int i1;
    if ((!this.a.h()) && (!this.o)) {
      i1 = 0;
    } else {
      i1 = 1;
    }
    if ((i1 == 0) && (!this.k)) {
      return;
    }
    if ((this.b == null) && (paramDrawable == null) && (!this.k)) {
      return;
    }
    if (this.b == null) {
      a();
    }
    if ((paramDrawable == null) && (!this.k))
    {
      this.b.setVisibility(8);
    }
    else
    {
      ImageView localImageView = this.b;
      if (i1 == 0) {
        paramDrawable = null;
      }
      localImageView.setImageDrawable(paramDrawable);
      if (this.b.getVisibility() != 0) {
        this.b.setVisibility(0);
      }
    }
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    if (paramCharSequence != null)
    {
      this.d.setText(paramCharSequence);
      if (this.d.getVisibility() != 0) {
        this.d.setVisibility(0);
      }
    }
    else if (this.d.getVisibility() != 8)
    {
      this.d.setVisibility(8);
    }
  }
}


/* Location:              ~/android/support/v7/view/menu/ListMenuItemView.class
 *
 * Reversed by:           J
 */