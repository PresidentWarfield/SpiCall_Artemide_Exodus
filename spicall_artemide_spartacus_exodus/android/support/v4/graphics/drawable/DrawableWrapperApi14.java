package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;

class DrawableWrapperApi14
  extends Drawable
  implements Drawable.Callback, DrawableWrapper, TintAwareDrawable
{
  static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
  private boolean mColorFilterSet;
  private int mCurrentColor;
  private PorterDuff.Mode mCurrentMode;
  Drawable mDrawable;
  private boolean mMutated;
  DrawableWrapperState mState;
  
  DrawableWrapperApi14(Drawable paramDrawable)
  {
    this.mState = mutateConstantState();
    setWrappedDrawable(paramDrawable);
  }
  
  DrawableWrapperApi14(DrawableWrapperState paramDrawableWrapperState, Resources paramResources)
  {
    this.mState = paramDrawableWrapperState;
    updateLocalState(paramResources);
  }
  
  private void updateLocalState(Resources paramResources)
  {
    DrawableWrapperState localDrawableWrapperState = this.mState;
    if ((localDrawableWrapperState != null) && (localDrawableWrapperState.mDrawableState != null)) {
      setWrappedDrawable(newDrawableFromState(this.mState.mDrawableState, paramResources));
    }
  }
  
  private boolean updateTint(int[] paramArrayOfInt)
  {
    if (!isCompatTintEnabled()) {
      return false;
    }
    ColorStateList localColorStateList = this.mState.mTint;
    PorterDuff.Mode localMode = this.mState.mTintMode;
    if ((localColorStateList != null) && (localMode != null))
    {
      int i = localColorStateList.getColorForState(paramArrayOfInt, localColorStateList.getDefaultColor());
      if ((!this.mColorFilterSet) || (i != this.mCurrentColor) || (localMode != this.mCurrentMode))
      {
        setColorFilter(i, localMode);
        this.mCurrentColor = i;
        this.mCurrentMode = localMode;
        this.mColorFilterSet = true;
        return true;
      }
    }
    else
    {
      this.mColorFilterSet = false;
      clearColorFilter();
    }
    return false;
  }
  
  public void draw(Canvas paramCanvas)
  {
    this.mDrawable.draw(paramCanvas);
  }
  
  public int getChangingConfigurations()
  {
    int i = super.getChangingConfigurations();
    DrawableWrapperState localDrawableWrapperState = this.mState;
    int j;
    if (localDrawableWrapperState != null) {
      j = localDrawableWrapperState.getChangingConfigurations();
    } else {
      j = 0;
    }
    return i | j | this.mDrawable.getChangingConfigurations();
  }
  
  public Drawable.ConstantState getConstantState()
  {
    DrawableWrapperState localDrawableWrapperState = this.mState;
    if ((localDrawableWrapperState != null) && (localDrawableWrapperState.canConstantState()))
    {
      this.mState.mChangingConfigurations = getChangingConfigurations();
      return this.mState;
    }
    return null;
  }
  
  public Drawable getCurrent()
  {
    return this.mDrawable.getCurrent();
  }
  
  public int getIntrinsicHeight()
  {
    return this.mDrawable.getIntrinsicHeight();
  }
  
  public int getIntrinsicWidth()
  {
    return this.mDrawable.getIntrinsicWidth();
  }
  
  public int getMinimumHeight()
  {
    return this.mDrawable.getMinimumHeight();
  }
  
  public int getMinimumWidth()
  {
    return this.mDrawable.getMinimumWidth();
  }
  
  public int getOpacity()
  {
    return this.mDrawable.getOpacity();
  }
  
  public boolean getPadding(Rect paramRect)
  {
    return this.mDrawable.getPadding(paramRect);
  }
  
  public int[] getState()
  {
    return this.mDrawable.getState();
  }
  
  public Region getTransparentRegion()
  {
    return this.mDrawable.getTransparentRegion();
  }
  
  public final Drawable getWrappedDrawable()
  {
    return this.mDrawable;
  }
  
  public void invalidateDrawable(Drawable paramDrawable)
  {
    invalidateSelf();
  }
  
  protected boolean isCompatTintEnabled()
  {
    return true;
  }
  
  public boolean isStateful()
  {
    if (isCompatTintEnabled())
    {
      localObject = this.mState;
      if (localObject != null)
      {
        localObject = ((DrawableWrapperState)localObject).mTint;
        break label26;
      }
    }
    Object localObject = null;
    label26:
    boolean bool;
    if (((localObject != null) && (((ColorStateList)localObject).isStateful())) || (this.mDrawable.isStateful())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void jumpToCurrentState()
  {
    this.mDrawable.jumpToCurrentState();
  }
  
  public Drawable mutate()
  {
    if ((!this.mMutated) && (super.mutate() == this))
    {
      this.mState = mutateConstantState();
      Object localObject = this.mDrawable;
      if (localObject != null) {
        ((Drawable)localObject).mutate();
      }
      DrawableWrapperState localDrawableWrapperState = this.mState;
      if (localDrawableWrapperState != null)
      {
        localObject = this.mDrawable;
        if (localObject != null) {
          localObject = ((Drawable)localObject).getConstantState();
        } else {
          localObject = null;
        }
        localDrawableWrapperState.mDrawableState = ((Drawable.ConstantState)localObject);
      }
      this.mMutated = true;
    }
    return this;
  }
  
  DrawableWrapperState mutateConstantState()
  {
    return new DrawableWrapperStateBase(this.mState, null);
  }
  
  protected Drawable newDrawableFromState(Drawable.ConstantState paramConstantState, Resources paramResources)
  {
    return paramConstantState.newDrawable(paramResources);
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    Drawable localDrawable = this.mDrawable;
    if (localDrawable != null) {
      localDrawable.setBounds(paramRect);
    }
  }
  
  protected boolean onLevelChange(int paramInt)
  {
    return this.mDrawable.setLevel(paramInt);
  }
  
  public void scheduleDrawable(Drawable paramDrawable, Runnable paramRunnable, long paramLong)
  {
    scheduleSelf(paramRunnable, paramLong);
  }
  
  public void setAlpha(int paramInt)
  {
    this.mDrawable.setAlpha(paramInt);
  }
  
  public void setChangingConfigurations(int paramInt)
  {
    this.mDrawable.setChangingConfigurations(paramInt);
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.mDrawable.setColorFilter(paramColorFilter);
  }
  
  public void setDither(boolean paramBoolean)
  {
    this.mDrawable.setDither(paramBoolean);
  }
  
  public void setFilterBitmap(boolean paramBoolean)
  {
    this.mDrawable.setFilterBitmap(paramBoolean);
  }
  
  public boolean setState(int[] paramArrayOfInt)
  {
    boolean bool = this.mDrawable.setState(paramArrayOfInt);
    if ((!updateTint(paramArrayOfInt)) && (!bool)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public void setTint(int paramInt)
  {
    setTintList(ColorStateList.valueOf(paramInt));
  }
  
  public void setTintList(ColorStateList paramColorStateList)
  {
    this.mState.mTint = paramColorStateList;
    updateTint(getState());
  }
  
  public void setTintMode(PorterDuff.Mode paramMode)
  {
    this.mState.mTintMode = paramMode;
    updateTint(getState());
  }
  
  public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((!super.setVisible(paramBoolean1, paramBoolean2)) && (!this.mDrawable.setVisible(paramBoolean1, paramBoolean2))) {
      paramBoolean1 = false;
    } else {
      paramBoolean1 = true;
    }
    return paramBoolean1;
  }
  
  public final void setWrappedDrawable(Drawable paramDrawable)
  {
    Object localObject = this.mDrawable;
    if (localObject != null) {
      ((Drawable)localObject).setCallback(null);
    }
    this.mDrawable = paramDrawable;
    if (paramDrawable != null)
    {
      paramDrawable.setCallback(this);
      setVisible(paramDrawable.isVisible(), true);
      setState(paramDrawable.getState());
      setLevel(paramDrawable.getLevel());
      setBounds(paramDrawable.getBounds());
      localObject = this.mState;
      if (localObject != null) {
        ((DrawableWrapperState)localObject).mDrawableState = paramDrawable.getConstantState();
      }
    }
    invalidateSelf();
  }
  
  public void unscheduleDrawable(Drawable paramDrawable, Runnable paramRunnable)
  {
    unscheduleSelf(paramRunnable);
  }
  
  protected static abstract class DrawableWrapperState
    extends Drawable.ConstantState
  {
    int mChangingConfigurations;
    Drawable.ConstantState mDrawableState;
    ColorStateList mTint = null;
    PorterDuff.Mode mTintMode = DrawableWrapperApi14.DEFAULT_TINT_MODE;
    
    DrawableWrapperState(DrawableWrapperState paramDrawableWrapperState, Resources paramResources)
    {
      if (paramDrawableWrapperState != null)
      {
        this.mChangingConfigurations = paramDrawableWrapperState.mChangingConfigurations;
        this.mDrawableState = paramDrawableWrapperState.mDrawableState;
        this.mTint = paramDrawableWrapperState.mTint;
        this.mTintMode = paramDrawableWrapperState.mTintMode;
      }
    }
    
    boolean canConstantState()
    {
      boolean bool;
      if (this.mDrawableState != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public int getChangingConfigurations()
    {
      int i = this.mChangingConfigurations;
      Drawable.ConstantState localConstantState = this.mDrawableState;
      int j;
      if (localConstantState != null) {
        j = localConstantState.getChangingConfigurations();
      } else {
        j = 0;
      }
      return i | j;
    }
    
    public Drawable newDrawable()
    {
      return newDrawable(null);
    }
    
    public abstract Drawable newDrawable(Resources paramResources);
  }
  
  private static class DrawableWrapperStateBase
    extends DrawableWrapperApi14.DrawableWrapperState
  {
    DrawableWrapperStateBase(DrawableWrapperApi14.DrawableWrapperState paramDrawableWrapperState, Resources paramResources)
    {
      super(paramResources);
    }
    
    public Drawable newDrawable(Resources paramResources)
    {
      return new DrawableWrapperApi14(this, paramResources);
    }
  }
}


/* Location:              ~/android/support/v4/graphics/drawable/DrawableWrapperApi14.class
 *
 * Reversed by:           J
 */