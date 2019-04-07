package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.widget.TintableImageSourceView;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AppCompatImageView
  extends ImageView
  implements TintableBackgroundView, TintableImageSourceView
{
  private final e mBackgroundTintHelper = new e(this);
  private final AppCompatImageHelper mImageHelper;
  
  public AppCompatImageView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AppCompatImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public AppCompatImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(TintContextWrapper.wrap(paramContext), paramAttributeSet, paramInt);
    this.mBackgroundTintHelper.a(paramAttributeSet, paramInt);
    this.mImageHelper = new AppCompatImageHelper(this);
    this.mImageHelper.loadFromAttributes(paramAttributeSet, paramInt);
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    Object localObject = this.mBackgroundTintHelper;
    if (localObject != null) {
      ((e)localObject).c();
    }
    localObject = this.mImageHelper;
    if (localObject != null) {
      ((AppCompatImageHelper)localObject).applySupportImageTint();
    }
  }
  
  public ColorStateList getSupportBackgroundTintList()
  {
    Object localObject = this.mBackgroundTintHelper;
    if (localObject != null) {
      localObject = ((e)localObject).a();
    } else {
      localObject = null;
    }
    return (ColorStateList)localObject;
  }
  
  public PorterDuff.Mode getSupportBackgroundTintMode()
  {
    Object localObject = this.mBackgroundTintHelper;
    if (localObject != null) {
      localObject = ((e)localObject).b();
    } else {
      localObject = null;
    }
    return (PorterDuff.Mode)localObject;
  }
  
  public ColorStateList getSupportImageTintList()
  {
    Object localObject = this.mImageHelper;
    if (localObject != null) {
      localObject = ((AppCompatImageHelper)localObject).getSupportImageTintList();
    } else {
      localObject = null;
    }
    return (ColorStateList)localObject;
  }
  
  public PorterDuff.Mode getSupportImageTintMode()
  {
    Object localObject = this.mImageHelper;
    if (localObject != null) {
      localObject = ((AppCompatImageHelper)localObject).getSupportImageTintMode();
    } else {
      localObject = null;
    }
    return (PorterDuff.Mode)localObject;
  }
  
  public boolean hasOverlappingRendering()
  {
    boolean bool;
    if ((this.mImageHelper.hasOverlappingRendering()) && (super.hasOverlappingRendering())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    super.setBackgroundDrawable(paramDrawable);
    e locale = this.mBackgroundTintHelper;
    if (locale != null) {
      locale.a(paramDrawable);
    }
  }
  
  public void setBackgroundResource(int paramInt)
  {
    super.setBackgroundResource(paramInt);
    e locale = this.mBackgroundTintHelper;
    if (locale != null) {
      locale.a(paramInt);
    }
  }
  
  public void setImageBitmap(Bitmap paramBitmap)
  {
    super.setImageBitmap(paramBitmap);
    paramBitmap = this.mImageHelper;
    if (paramBitmap != null) {
      paramBitmap.applySupportImageTint();
    }
  }
  
  public void setImageDrawable(Drawable paramDrawable)
  {
    super.setImageDrawable(paramDrawable);
    paramDrawable = this.mImageHelper;
    if (paramDrawable != null) {
      paramDrawable.applySupportImageTint();
    }
  }
  
  public void setImageIcon(Icon paramIcon)
  {
    super.setImageIcon(paramIcon);
    paramIcon = this.mImageHelper;
    if (paramIcon != null) {
      paramIcon.applySupportImageTint();
    }
  }
  
  public void setImageResource(int paramInt)
  {
    AppCompatImageHelper localAppCompatImageHelper = this.mImageHelper;
    if (localAppCompatImageHelper != null) {
      localAppCompatImageHelper.setImageResource(paramInt);
    }
  }
  
  public void setImageURI(Uri paramUri)
  {
    super.setImageURI(paramUri);
    paramUri = this.mImageHelper;
    if (paramUri != null) {
      paramUri.applySupportImageTint();
    }
  }
  
  public void setSupportBackgroundTintList(ColorStateList paramColorStateList)
  {
    e locale = this.mBackgroundTintHelper;
    if (locale != null) {
      locale.a(paramColorStateList);
    }
  }
  
  public void setSupportBackgroundTintMode(PorterDuff.Mode paramMode)
  {
    e locale = this.mBackgroundTintHelper;
    if (locale != null) {
      locale.a(paramMode);
    }
  }
  
  public void setSupportImageTintList(ColorStateList paramColorStateList)
  {
    AppCompatImageHelper localAppCompatImageHelper = this.mImageHelper;
    if (localAppCompatImageHelper != null) {
      localAppCompatImageHelper.setSupportImageTintList(paramColorStateList);
    }
  }
  
  public void setSupportImageTintMode(PorterDuff.Mode paramMode)
  {
    AppCompatImageHelper localAppCompatImageHelper = this.mImageHelper;
    if (localAppCompatImageHelper != null) {
      localAppCompatImageHelper.setSupportImageTintMode(paramMode);
    }
  }
}


/* Location:              ~/android/support/v7/widget/AppCompatImageView.class
 *
 * Reversed by:           J
 */