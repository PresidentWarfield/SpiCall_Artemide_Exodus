package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

class DrawableWrapperApi19
  extends DrawableWrapperApi14
{
  DrawableWrapperApi19(Drawable paramDrawable)
  {
    super(paramDrawable);
  }
  
  DrawableWrapperApi19(DrawableWrapperApi14.DrawableWrapperState paramDrawableWrapperState, Resources paramResources)
  {
    super(paramDrawableWrapperState, paramResources);
  }
  
  public boolean isAutoMirrored()
  {
    return this.mDrawable.isAutoMirrored();
  }
  
  DrawableWrapperApi14.DrawableWrapperState mutateConstantState()
  {
    return new DrawableWrapperStateKitKat(this.mState, null);
  }
  
  public void setAutoMirrored(boolean paramBoolean)
  {
    this.mDrawable.setAutoMirrored(paramBoolean);
  }
  
  private static class DrawableWrapperStateKitKat
    extends DrawableWrapperApi14.DrawableWrapperState
  {
    DrawableWrapperStateKitKat(DrawableWrapperApi14.DrawableWrapperState paramDrawableWrapperState, Resources paramResources)
    {
      super(paramResources);
    }
    
    public Drawable newDrawable(Resources paramResources)
    {
      return new DrawableWrapperApi19(this, paramResources);
    }
  }
}


/* Location:              ~/android/support/v4/graphics/drawable/DrawableWrapperApi19.class
 *
 * Reversed by:           J
 */