package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.NotFoundException;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.a.a.j;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;

class j
{
  final TextView a;
  private ae b;
  private ae c;
  private ae d;
  private ae e;
  private final l f;
  private int g = 0;
  private Typeface h;
  
  j(TextView paramTextView)
  {
    this.a = paramTextView;
    this.f = new l(this.a);
  }
  
  protected static ae a(Context paramContext, AppCompatDrawableManager paramAppCompatDrawableManager, int paramInt)
  {
    paramContext = paramAppCompatDrawableManager.getTintList(paramContext, paramInt);
    if (paramContext != null)
    {
      paramAppCompatDrawableManager = new ae();
      paramAppCompatDrawableManager.d = true;
      paramAppCompatDrawableManager.a = paramContext;
      return paramAppCompatDrawableManager;
    }
    return null;
  }
  
  static j a(TextView paramTextView)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return new k(paramTextView);
    }
    return new j(paramTextView);
  }
  
  private void a(Context paramContext, TintTypedArray paramTintTypedArray)
  {
    this.g = paramTintTypedArray.getInt(a.j.TextAppearance_android_textStyle, this.g);
    if ((paramTintTypedArray.hasValue(a.j.TextAppearance_android_fontFamily)) || (paramTintTypedArray.hasValue(a.j.TextAppearance_fontFamily)))
    {
      this.h = null;
      int i;
      if (paramTintTypedArray.hasValue(a.j.TextAppearance_android_fontFamily)) {
        i = a.j.TextAppearance_android_fontFamily;
      } else {
        i = a.j.TextAppearance_fontFamily;
      }
      if (!paramContext.isRestricted()) {
        try
        {
          this.h = paramTintTypedArray.getFont(i, this.g, this.a);
        }
        catch (UnsupportedOperationException|Resources.NotFoundException paramContext) {}
      }
      if (this.h == null) {
        this.h = Typeface.create(paramTintTypedArray.getString(i), this.g);
      }
    }
  }
  
  private void b(int paramInt, float paramFloat)
  {
    this.f.a(paramInt, paramFloat);
  }
  
  void a()
  {
    if ((this.b != null) || (this.c != null) || (this.d != null) || (this.e != null))
    {
      Drawable[] arrayOfDrawable = this.a.getCompoundDrawables();
      a(arrayOfDrawable[0], this.b);
      a(arrayOfDrawable[1], this.c);
      a(arrayOfDrawable[2], this.d);
      a(arrayOfDrawable[3], this.e);
    }
  }
  
  void a(int paramInt)
  {
    this.f.a(paramInt);
  }
  
  void a(int paramInt, float paramFloat)
  {
    if ((Build.VERSION.SDK_INT < 26) && (!c())) {
      b(paramInt, paramFloat);
    }
  }
  
  void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.f.a(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  void a(Context paramContext, int paramInt)
  {
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramInt, a.j.TextAppearance);
    if (localTintTypedArray.hasValue(a.j.TextAppearance_textAllCaps)) {
      a(localTintTypedArray.getBoolean(a.j.TextAppearance_textAllCaps, false));
    }
    if ((Build.VERSION.SDK_INT < 23) && (localTintTypedArray.hasValue(a.j.TextAppearance_android_textColor)))
    {
      ColorStateList localColorStateList = localTintTypedArray.getColorStateList(a.j.TextAppearance_android_textColor);
      if (localColorStateList != null) {
        this.a.setTextColor(localColorStateList);
      }
    }
    a(paramContext, localTintTypedArray);
    localTintTypedArray.recycle();
    paramContext = this.h;
    if (paramContext != null) {
      this.a.setTypeface(paramContext, this.g);
    }
  }
  
  final void a(Drawable paramDrawable, ae paramae)
  {
    if ((paramDrawable != null) && (paramae != null)) {
      AppCompatDrawableManager.tintDrawable(paramDrawable, paramae, this.a.getDrawableState());
    }
  }
  
  void a(AttributeSet paramAttributeSet, int paramInt)
  {
    Context localContext = this.a.getContext();
    Object localObject1 = AppCompatDrawableManager.get();
    Object localObject2 = TintTypedArray.obtainStyledAttributes(localContext, paramAttributeSet, a.j.AppCompatTextHelper, paramInt, 0);
    int i = ((TintTypedArray)localObject2).getResourceId(a.j.AppCompatTextHelper_android_textAppearance, -1);
    if (((TintTypedArray)localObject2).hasValue(a.j.AppCompatTextHelper_android_drawableLeft)) {
      this.b = a(localContext, (AppCompatDrawableManager)localObject1, ((TintTypedArray)localObject2).getResourceId(a.j.AppCompatTextHelper_android_drawableLeft, 0));
    }
    if (((TintTypedArray)localObject2).hasValue(a.j.AppCompatTextHelper_android_drawableTop)) {
      this.c = a(localContext, (AppCompatDrawableManager)localObject1, ((TintTypedArray)localObject2).getResourceId(a.j.AppCompatTextHelper_android_drawableTop, 0));
    }
    if (((TintTypedArray)localObject2).hasValue(a.j.AppCompatTextHelper_android_drawableRight)) {
      this.d = a(localContext, (AppCompatDrawableManager)localObject1, ((TintTypedArray)localObject2).getResourceId(a.j.AppCompatTextHelper_android_drawableRight, 0));
    }
    if (((TintTypedArray)localObject2).hasValue(a.j.AppCompatTextHelper_android_drawableBottom)) {
      this.e = a(localContext, (AppCompatDrawableManager)localObject1, ((TintTypedArray)localObject2).getResourceId(a.j.AppCompatTextHelper_android_drawableBottom, 0));
    }
    ((TintTypedArray)localObject2).recycle();
    boolean bool1 = this.a.getTransformationMethod() instanceof PasswordTransformationMethod;
    int j = 1;
    Object localObject3 = null;
    localObject2 = null;
    boolean bool2;
    Object localObject5;
    if (i != -1)
    {
      localObject4 = TintTypedArray.obtainStyledAttributes(localContext, i, a.j.TextAppearance);
      if ((!bool1) && (((TintTypedArray)localObject4).hasValue(a.j.TextAppearance_textAllCaps)))
      {
        bool2 = ((TintTypedArray)localObject4).getBoolean(a.j.TextAppearance_textAllCaps, false);
        i = 1;
      }
      else
      {
        i = 0;
        bool2 = false;
      }
      a(localContext, (TintTypedArray)localObject4);
      if (Build.VERSION.SDK_INT < 23)
      {
        if (((TintTypedArray)localObject4).hasValue(a.j.TextAppearance_android_textColor)) {
          localObject1 = ((TintTypedArray)localObject4).getColorStateList(a.j.TextAppearance_android_textColor);
        } else {
          localObject1 = null;
        }
        if (((TintTypedArray)localObject4).hasValue(a.j.TextAppearance_android_textColorHint)) {
          localObject5 = ((TintTypedArray)localObject4).getColorStateList(a.j.TextAppearance_android_textColorHint);
        } else {
          localObject5 = null;
        }
        if (((TintTypedArray)localObject4).hasValue(a.j.TextAppearance_android_textColorLink))
        {
          localObject3 = ((TintTypedArray)localObject4).getColorStateList(a.j.TextAppearance_android_textColorLink);
          localObject2 = localObject1;
          localObject1 = localObject3;
          localObject3 = localObject5;
        }
        else
        {
          localObject3 = null;
          localObject2 = localObject1;
          localObject1 = localObject3;
          localObject3 = localObject5;
        }
      }
      else
      {
        localObject1 = null;
        localObject3 = localObject1;
      }
      ((TintTypedArray)localObject4).recycle();
      localObject5 = localObject1;
      localObject1 = localObject3;
    }
    else
    {
      localObject5 = null;
      localObject1 = localObject5;
      i = 0;
      bool2 = false;
      localObject2 = localObject3;
    }
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(localContext, paramAttributeSet, a.j.TextAppearance, paramInt, 0);
    if ((!bool1) && (localTintTypedArray.hasValue(a.j.TextAppearance_textAllCaps)))
    {
      bool2 = localTintTypedArray.getBoolean(a.j.TextAppearance_textAllCaps, false);
      i = j;
    }
    Object localObject6 = localObject2;
    Object localObject4 = localObject5;
    localObject3 = localObject1;
    if (Build.VERSION.SDK_INT < 23)
    {
      if (localTintTypedArray.hasValue(a.j.TextAppearance_android_textColor)) {
        localObject2 = localTintTypedArray.getColorStateList(a.j.TextAppearance_android_textColor);
      }
      if (localTintTypedArray.hasValue(a.j.TextAppearance_android_textColorHint)) {
        localObject1 = localTintTypedArray.getColorStateList(a.j.TextAppearance_android_textColorHint);
      }
      localObject6 = localObject2;
      localObject4 = localObject5;
      localObject3 = localObject1;
      if (localTintTypedArray.hasValue(a.j.TextAppearance_android_textColorLink))
      {
        localObject4 = localTintTypedArray.getColorStateList(a.j.TextAppearance_android_textColorLink);
        localObject3 = localObject1;
        localObject6 = localObject2;
      }
    }
    a(localContext, localTintTypedArray);
    localTintTypedArray.recycle();
    if (localObject6 != null) {
      this.a.setTextColor((ColorStateList)localObject6);
    }
    if (localObject3 != null) {
      this.a.setHintTextColor((ColorStateList)localObject3);
    }
    if (localObject4 != null) {
      this.a.setLinkTextColor((ColorStateList)localObject4);
    }
    if ((!bool1) && (i != 0)) {
      a(bool2);
    }
    localObject1 = this.h;
    if (localObject1 != null) {
      this.a.setTypeface((Typeface)localObject1, this.g);
    }
    this.f.a(paramAttributeSet, paramInt);
    if ((Build.VERSION.SDK_INT >= 26) && (this.f.a() != 0))
    {
      paramAttributeSet = this.f.e();
      if (paramAttributeSet.length > 0) {
        if (this.a.getAutoSizeStepGranularity() != -1.0F) {
          this.a.setAutoSizeTextTypeUniformWithConfiguration(this.f.c(), this.f.d(), this.f.b(), 0);
        } else {
          this.a.setAutoSizeTextTypeUniformWithPresetSizes(paramAttributeSet, 0);
        }
      }
    }
  }
  
  void a(boolean paramBoolean)
  {
    this.a.setAllCaps(paramBoolean);
  }
  
  void a(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (Build.VERSION.SDK_INT < 26) {
      b();
    }
  }
  
  void a(int[] paramArrayOfInt, int paramInt)
  {
    this.f.a(paramArrayOfInt, paramInt);
  }
  
  void b()
  {
    this.f.f();
  }
  
  boolean c()
  {
    return this.f.g();
  }
  
  int d()
  {
    return this.f.a();
  }
  
  int e()
  {
    return this.f.b();
  }
  
  int f()
  {
    return this.f.c();
  }
  
  int g()
  {
    return this.f.d();
  }
  
  int[] h()
  {
    return this.f.e();
  }
}


/* Location:              ~/android/support/v7/widget/j.class
 *
 * Reversed by:           J
 */