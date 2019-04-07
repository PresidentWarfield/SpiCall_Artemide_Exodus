package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v7.a.a.j;
import android.util.AttributeSet;
import android.view.View;

class e
{
  private final View a;
  private final AppCompatDrawableManager b;
  private int c = -1;
  private ae d;
  private ae e;
  private ae f;
  
  e(View paramView)
  {
    this.a = paramView;
    this.b = AppCompatDrawableManager.get();
  }
  
  private boolean b(Drawable paramDrawable)
  {
    if (this.f == null) {
      this.f = new ae();
    }
    ae localae = this.f;
    localae.a();
    Object localObject = ViewCompat.getBackgroundTintList(this.a);
    if (localObject != null)
    {
      localae.d = true;
      localae.a = ((ColorStateList)localObject);
    }
    localObject = ViewCompat.getBackgroundTintMode(this.a);
    if (localObject != null)
    {
      localae.c = true;
      localae.b = ((PorterDuff.Mode)localObject);
    }
    if ((!localae.d) && (!localae.c)) {
      return false;
    }
    AppCompatDrawableManager.tintDrawable(paramDrawable, localae, this.a.getDrawableState());
    return true;
  }
  
  private boolean d()
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool = true;
    if (i > 21)
    {
      if (this.d == null) {
        bool = false;
      }
      return bool;
    }
    return i == 21;
  }
  
  ColorStateList a()
  {
    Object localObject = this.e;
    if (localObject != null) {
      localObject = ((ae)localObject).a;
    } else {
      localObject = null;
    }
    return (ColorStateList)localObject;
  }
  
  void a(int paramInt)
  {
    this.c = paramInt;
    Object localObject = this.b;
    if (localObject != null) {
      localObject = ((AppCompatDrawableManager)localObject).getTintList(this.a.getContext(), paramInt);
    } else {
      localObject = null;
    }
    b((ColorStateList)localObject);
    c();
  }
  
  void a(ColorStateList paramColorStateList)
  {
    if (this.e == null) {
      this.e = new ae();
    }
    ae localae = this.e;
    localae.a = paramColorStateList;
    localae.d = true;
    c();
  }
  
  void a(PorterDuff.Mode paramMode)
  {
    if (this.e == null) {
      this.e = new ae();
    }
    ae localae = this.e;
    localae.b = paramMode;
    localae.c = true;
    c();
  }
  
  void a(Drawable paramDrawable)
  {
    this.c = -1;
    b(null);
    c();
  }
  
  void a(AttributeSet paramAttributeSet, int paramInt)
  {
    paramAttributeSet = TintTypedArray.obtainStyledAttributes(this.a.getContext(), paramAttributeSet, a.j.ViewBackgroundHelper, paramInt, 0);
    try
    {
      if (paramAttributeSet.hasValue(a.j.ViewBackgroundHelper_android_background))
      {
        this.c = paramAttributeSet.getResourceId(a.j.ViewBackgroundHelper_android_background, -1);
        ColorStateList localColorStateList = this.b.getTintList(this.a.getContext(), this.c);
        if (localColorStateList != null) {
          b(localColorStateList);
        }
      }
      if (paramAttributeSet.hasValue(a.j.ViewBackgroundHelper_backgroundTint)) {
        ViewCompat.setBackgroundTintList(this.a, paramAttributeSet.getColorStateList(a.j.ViewBackgroundHelper_backgroundTint));
      }
      if (paramAttributeSet.hasValue(a.j.ViewBackgroundHelper_backgroundTintMode)) {
        ViewCompat.setBackgroundTintMode(this.a, DrawableUtils.parseTintMode(paramAttributeSet.getInt(a.j.ViewBackgroundHelper_backgroundTintMode, -1), null));
      }
      return;
    }
    finally
    {
      paramAttributeSet.recycle();
    }
  }
  
  PorterDuff.Mode b()
  {
    Object localObject = this.e;
    if (localObject != null) {
      localObject = ((ae)localObject).b;
    } else {
      localObject = null;
    }
    return (PorterDuff.Mode)localObject;
  }
  
  void b(ColorStateList paramColorStateList)
  {
    if (paramColorStateList != null)
    {
      if (this.d == null) {
        this.d = new ae();
      }
      ae localae = this.d;
      localae.a = paramColorStateList;
      localae.d = true;
    }
    else
    {
      this.d = null;
    }
    c();
  }
  
  void c()
  {
    Drawable localDrawable = this.a.getBackground();
    if (localDrawable != null)
    {
      if ((d()) && (b(localDrawable))) {
        return;
      }
      ae localae = this.e;
      if (localae != null)
      {
        AppCompatDrawableManager.tintDrawable(localDrawable, localae, this.a.getDrawableState());
      }
      else
      {
        localae = this.d;
        if (localae != null) {
          AppCompatDrawableManager.tintDrawable(localDrawable, localae, this.a.getDrawableState());
        }
      }
    }
  }
}


/* Location:              ~/android/support/v7/widget/e.class
 *
 * Reversed by:           J
 */