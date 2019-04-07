package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.design.a.h;
import android.support.design.a.i;
import android.support.design.internal.c;
import android.support.design.internal.f;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.a.a.a;
import android.support.v7.view.g;
import android.support.v7.view.menu.h;
import android.support.v7.view.menu.h.a;
import android.support.v7.view.menu.j;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;

public class NavigationView
  extends f
{
  private static final int[] d = { 16842912 };
  private static final int[] e = { -16842910 };
  a c;
  private final android.support.design.internal.b f;
  private final c g = new c();
  private int h;
  private MenuInflater i;
  
  public NavigationView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public NavigationView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    m.a(paramContext);
    this.f = new android.support.design.internal.b(paramContext);
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, a.i.NavigationView, paramInt, a.h.Widget_Design_NavigationView);
    ViewCompat.setBackground(this, localTintTypedArray.getDrawable(a.i.NavigationView_android_background));
    if (localTintTypedArray.hasValue(a.i.NavigationView_elevation)) {
      ViewCompat.setElevation(this, localTintTypedArray.getDimensionPixelSize(a.i.NavigationView_elevation, 0));
    }
    ViewCompat.setFitsSystemWindows(this, localTintTypedArray.getBoolean(a.i.NavigationView_android_fitsSystemWindows, false));
    this.h = localTintTypedArray.getDimensionPixelSize(a.i.NavigationView_android_maxWidth, 0);
    ColorStateList localColorStateList;
    if (localTintTypedArray.hasValue(a.i.NavigationView_itemIconTint)) {
      localColorStateList = localTintTypedArray.getColorStateList(a.i.NavigationView_itemIconTint);
    } else {
      localColorStateList = c(16842808);
    }
    int j;
    if (localTintTypedArray.hasValue(a.i.NavigationView_itemTextAppearance))
    {
      j = localTintTypedArray.getResourceId(a.i.NavigationView_itemTextAppearance, 0);
      paramInt = 1;
    }
    else
    {
      paramInt = 0;
      j = 0;
    }
    paramAttributeSet = null;
    if (localTintTypedArray.hasValue(a.i.NavigationView_itemTextColor)) {
      paramAttributeSet = localTintTypedArray.getColorStateList(a.i.NavigationView_itemTextColor);
    }
    Object localObject = paramAttributeSet;
    if (paramInt == 0)
    {
      localObject = paramAttributeSet;
      if (paramAttributeSet == null) {
        localObject = c(16842806);
      }
    }
    paramAttributeSet = localTintTypedArray.getDrawable(a.i.NavigationView_itemBackground);
    this.f.a(new h.a()
    {
      public void a(h paramAnonymoush) {}
      
      public boolean a(h paramAnonymoush, MenuItem paramAnonymousMenuItem)
      {
        boolean bool;
        if ((NavigationView.this.c != null) && (NavigationView.this.c.a(paramAnonymousMenuItem))) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
    });
    this.g.a(1);
    this.g.a(paramContext, this.f);
    this.g.a(localColorStateList);
    if (paramInt != 0) {
      this.g.c(j);
    }
    this.g.b((ColorStateList)localObject);
    this.g.a(paramAttributeSet);
    this.f.a(this.g);
    addView((View)this.g.a(this));
    if (localTintTypedArray.hasValue(a.i.NavigationView_menu)) {
      a(localTintTypedArray.getResourceId(a.i.NavigationView_menu, 0));
    }
    if (localTintTypedArray.hasValue(a.i.NavigationView_headerLayout)) {
      b(localTintTypedArray.getResourceId(a.i.NavigationView_headerLayout, 0));
    }
    localTintTypedArray.recycle();
  }
  
  private ColorStateList c(int paramInt)
  {
    Object localObject = new TypedValue();
    if (!getContext().getTheme().resolveAttribute(paramInt, (TypedValue)localObject, true)) {
      return null;
    }
    ColorStateList localColorStateList = android.support.v7.c.a.b.a(getContext(), ((TypedValue)localObject).resourceId);
    if (!getContext().getTheme().resolveAttribute(a.a.colorPrimary, (TypedValue)localObject, true)) {
      return null;
    }
    int j = ((TypedValue)localObject).data;
    int k = localColorStateList.getDefaultColor();
    int[] arrayOfInt1 = e;
    int[] arrayOfInt2 = d;
    localObject = EMPTY_STATE_SET;
    paramInt = localColorStateList.getColorForState(e, k);
    return new ColorStateList(new int[][] { arrayOfInt1, arrayOfInt2, localObject }, new int[] { paramInt, j, k });
  }
  
  private MenuInflater getMenuInflater()
  {
    if (this.i == null) {
      this.i = new g(getContext());
    }
    return this.i;
  }
  
  public void a(int paramInt)
  {
    this.g.b(true);
    getMenuInflater().inflate(paramInt, this.f);
    this.g.b(false);
    this.g.a(false);
  }
  
  protected void a(WindowInsetsCompat paramWindowInsetsCompat)
  {
    this.g.a(paramWindowInsetsCompat);
  }
  
  public View b(int paramInt)
  {
    return this.g.b(paramInt);
  }
  
  public int getHeaderCount()
  {
    return this.g.d();
  }
  
  public Drawable getItemBackground()
  {
    return this.g.g();
  }
  
  public ColorStateList getItemIconTintList()
  {
    return this.g.e();
  }
  
  public ColorStateList getItemTextColor()
  {
    return this.g.f();
  }
  
  public Menu getMenu()
  {
    return this.f;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int j = View.MeasureSpec.getMode(paramInt1);
    if (j != Integer.MIN_VALUE)
    {
      if (j == 0) {
        paramInt1 = View.MeasureSpec.makeMeasureSpec(this.h, 1073741824);
      }
    }
    else {
      paramInt1 = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(paramInt1), this.h), 1073741824);
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof b))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    paramParcelable = (b)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    this.f.b(paramParcelable.a);
  }
  
  protected Parcelable onSaveInstanceState()
  {
    b localb = new b(super.onSaveInstanceState());
    localb.a = new Bundle();
    this.f.a(localb.a);
    return localb;
  }
  
  public void setCheckedItem(int paramInt)
  {
    MenuItem localMenuItem = this.f.findItem(paramInt);
    if (localMenuItem != null) {
      this.g.a((j)localMenuItem);
    }
  }
  
  public void setItemBackground(Drawable paramDrawable)
  {
    this.g.a(paramDrawable);
  }
  
  public void setItemBackgroundResource(int paramInt)
  {
    setItemBackground(ContextCompat.getDrawable(getContext(), paramInt));
  }
  
  public void setItemIconTintList(ColorStateList paramColorStateList)
  {
    this.g.a(paramColorStateList);
  }
  
  public void setItemTextAppearance(int paramInt)
  {
    this.g.c(paramInt);
  }
  
  public void setItemTextColor(ColorStateList paramColorStateList)
  {
    this.g.b(paramColorStateList);
  }
  
  public void setNavigationItemSelectedListener(a parama)
  {
    this.c = parama;
  }
  
  public static abstract interface a
  {
    public abstract boolean a(MenuItem paramMenuItem);
  }
  
  public static class b
    extends AbsSavedState
  {
    public static final Parcelable.Creator<b> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public NavigationView.b a(Parcel paramAnonymousParcel)
      {
        return new NavigationView.b(paramAnonymousParcel, null);
      }
      
      public NavigationView.b a(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new NavigationView.b(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public NavigationView.b[] a(int paramAnonymousInt)
      {
        return new NavigationView.b[paramAnonymousInt];
      }
    };
    public Bundle a;
    
    public b(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      this.a = paramParcel.readBundle(paramClassLoader);
    }
    
    public b(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeBundle(this.a);
    }
  }
}


/* Location:              ~/android/support/design/widget/NavigationView.class
 *
 * Reversed by:           J
 */