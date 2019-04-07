package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.a.a.j;
import android.support.v7.c.a.b;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AppCompatImageHelper
{
  private ae mImageTint;
  private ae mInternalImageTint;
  private ae mTmpInfo;
  private final ImageView mView;
  
  public AppCompatImageHelper(ImageView paramImageView)
  {
    this.mView = paramImageView;
  }
  
  private boolean applyFrameworkTintUsingColorFilter(Drawable paramDrawable)
  {
    if (this.mTmpInfo == null) {
      this.mTmpInfo = new ae();
    }
    ae localae = this.mTmpInfo;
    localae.a();
    Object localObject = ImageViewCompat.getImageTintList(this.mView);
    if (localObject != null)
    {
      localae.d = true;
      localae.a = ((ColorStateList)localObject);
    }
    localObject = ImageViewCompat.getImageTintMode(this.mView);
    if (localObject != null)
    {
      localae.c = true;
      localae.b = ((PorterDuff.Mode)localObject);
    }
    if ((!localae.d) && (!localae.c)) {
      return false;
    }
    AppCompatDrawableManager.tintDrawable(paramDrawable, localae, this.mView.getDrawableState());
    return true;
  }
  
  private boolean shouldApplyFrameworkTintUsingColorFilter()
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool = true;
    if (i > 21)
    {
      if (this.mInternalImageTint == null) {
        bool = false;
      }
      return bool;
    }
    return i == 21;
  }
  
  void applySupportImageTint()
  {
    Drawable localDrawable = this.mView.getDrawable();
    if (localDrawable != null) {
      DrawableUtils.fixDrawable(localDrawable);
    }
    if (localDrawable != null)
    {
      if ((shouldApplyFrameworkTintUsingColorFilter()) && (applyFrameworkTintUsingColorFilter(localDrawable))) {
        return;
      }
      ae localae = this.mImageTint;
      if (localae != null)
      {
        AppCompatDrawableManager.tintDrawable(localDrawable, localae, this.mView.getDrawableState());
      }
      else
      {
        localae = this.mInternalImageTint;
        if (localae != null) {
          AppCompatDrawableManager.tintDrawable(localDrawable, localae, this.mView.getDrawableState());
        }
      }
    }
  }
  
  ColorStateList getSupportImageTintList()
  {
    Object localObject = this.mImageTint;
    if (localObject != null) {
      localObject = ((ae)localObject).a;
    } else {
      localObject = null;
    }
    return (ColorStateList)localObject;
  }
  
  PorterDuff.Mode getSupportImageTintMode()
  {
    Object localObject = this.mImageTint;
    if (localObject != null) {
      localObject = ((ae)localObject).b;
    } else {
      localObject = null;
    }
    return (PorterDuff.Mode)localObject;
  }
  
  boolean hasOverlappingRendering()
  {
    Drawable localDrawable = this.mView.getBackground();
    return (Build.VERSION.SDK_INT < 21) || (!(localDrawable instanceof RippleDrawable));
  }
  
  public void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), paramAttributeSet, a.j.AppCompatImageView, paramInt, 0);
    try
    {
      Drawable localDrawable = this.mView.getDrawable();
      paramAttributeSet = localDrawable;
      if (localDrawable == null)
      {
        paramInt = localTintTypedArray.getResourceId(a.j.AppCompatImageView_srcCompat, -1);
        paramAttributeSet = localDrawable;
        if (paramInt != -1)
        {
          localDrawable = b.b(this.mView.getContext(), paramInt);
          paramAttributeSet = localDrawable;
          if (localDrawable != null)
          {
            this.mView.setImageDrawable(localDrawable);
            paramAttributeSet = localDrawable;
          }
        }
      }
      if (paramAttributeSet != null) {
        DrawableUtils.fixDrawable(paramAttributeSet);
      }
      if (localTintTypedArray.hasValue(a.j.AppCompatImageView_tint)) {
        ImageViewCompat.setImageTintList(this.mView, localTintTypedArray.getColorStateList(a.j.AppCompatImageView_tint));
      }
      if (localTintTypedArray.hasValue(a.j.AppCompatImageView_tintMode)) {
        ImageViewCompat.setImageTintMode(this.mView, DrawableUtils.parseTintMode(localTintTypedArray.getInt(a.j.AppCompatImageView_tintMode, -1), null));
      }
      return;
    }
    finally
    {
      localTintTypedArray.recycle();
    }
  }
  
  public void setImageResource(int paramInt)
  {
    if (paramInt != 0)
    {
      Drawable localDrawable = b.b(this.mView.getContext(), paramInt);
      if (localDrawable != null) {
        DrawableUtils.fixDrawable(localDrawable);
      }
      this.mView.setImageDrawable(localDrawable);
    }
    else
    {
      this.mView.setImageDrawable(null);
    }
    applySupportImageTint();
  }
  
  void setInternalImageTint(ColorStateList paramColorStateList)
  {
    if (paramColorStateList != null)
    {
      if (this.mInternalImageTint == null) {
        this.mInternalImageTint = new ae();
      }
      ae localae = this.mInternalImageTint;
      localae.a = paramColorStateList;
      localae.d = true;
    }
    else
    {
      this.mInternalImageTint = null;
    }
    applySupportImageTint();
  }
  
  void setSupportImageTintList(ColorStateList paramColorStateList)
  {
    if (this.mImageTint == null) {
      this.mImageTint = new ae();
    }
    ae localae = this.mImageTint;
    localae.a = paramColorStateList;
    localae.d = true;
    applySupportImageTint();
  }
  
  void setSupportImageTintMode(PorterDuff.Mode paramMode)
  {
    if (this.mImageTint == null) {
      this.mImageTint = new ae();
    }
    ae localae = this.mImageTint;
    localae.b = paramMode;
    localae.c = true;
    applySupportImageTint();
  }
}


/* Location:              ~/android/support/v7/widget/AppCompatImageHelper.class
 *
 * Reversed by:           J
 */