package android.support.v7.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import android.widget.ProgressBar;

class h
{
  private static final int[] a = { 16843067, 16843068 };
  private final ProgressBar b;
  private Bitmap c;
  
  h(ProgressBar paramProgressBar)
  {
    this.b = paramProgressBar;
  }
  
  private Drawable a(Drawable paramDrawable)
  {
    Object localObject = paramDrawable;
    if ((paramDrawable instanceof AnimationDrawable))
    {
      AnimationDrawable localAnimationDrawable = (AnimationDrawable)paramDrawable;
      int i = localAnimationDrawable.getNumberOfFrames();
      localObject = new AnimationDrawable();
      ((AnimationDrawable)localObject).setOneShot(localAnimationDrawable.isOneShot());
      for (int j = 0; j < i; j++)
      {
        paramDrawable = a(localAnimationDrawable.getFrame(j), true);
        paramDrawable.setLevel(10000);
        ((AnimationDrawable)localObject).addFrame(paramDrawable, localAnimationDrawable.getDuration(j));
      }
      ((AnimationDrawable)localObject).setLevel(10000);
    }
    return (Drawable)localObject;
  }
  
  private Drawable a(Drawable paramDrawable, boolean paramBoolean)
  {
    Object localObject1;
    Object localObject2;
    if ((paramDrawable instanceof DrawableWrapper))
    {
      localObject1 = (DrawableWrapper)paramDrawable;
      localObject2 = ((DrawableWrapper)localObject1).getWrappedDrawable();
      if (localObject2 != null) {
        ((DrawableWrapper)localObject1).setWrappedDrawable(a((Drawable)localObject2, paramBoolean));
      }
    }
    else
    {
      if ((paramDrawable instanceof LayerDrawable))
      {
        paramDrawable = (LayerDrawable)paramDrawable;
        int i = paramDrawable.getNumberOfLayers();
        localObject1 = new Drawable[i];
        int j = 0;
        for (int k = 0; k < i; k++)
        {
          int m = paramDrawable.getId(k);
          localObject2 = paramDrawable.getDrawable(k);
          if ((m != 16908301) && (m != 16908303)) {
            paramBoolean = false;
          } else {
            paramBoolean = true;
          }
          localObject1[k] = a((Drawable)localObject2, paramBoolean);
        }
        localObject1 = new LayerDrawable((Drawable[])localObject1);
        for (k = j; k < i; k++) {
          ((LayerDrawable)localObject1).setId(k, paramDrawable.getId(k));
        }
        return (Drawable)localObject1;
      }
      if ((paramDrawable instanceof BitmapDrawable))
      {
        localObject1 = (BitmapDrawable)paramDrawable;
        localObject2 = ((BitmapDrawable)localObject1).getBitmap();
        if (this.c == null) {
          this.c = ((Bitmap)localObject2);
        }
        paramDrawable = new ShapeDrawable(b());
        localObject2 = new BitmapShader((Bitmap)localObject2, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        paramDrawable.getPaint().setShader((Shader)localObject2);
        paramDrawable.getPaint().setColorFilter(((BitmapDrawable)localObject1).getPaint().getColorFilter());
        if (paramBoolean) {
          paramDrawable = new ClipDrawable(paramDrawable, 3, 1);
        }
        return paramDrawable;
      }
    }
    return paramDrawable;
  }
  
  private Shape b()
  {
    return new RoundRectShape(new float[] { 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F }, null, null);
  }
  
  Bitmap a()
  {
    return this.c;
  }
  
  void a(AttributeSet paramAttributeSet, int paramInt)
  {
    paramAttributeSet = TintTypedArray.obtainStyledAttributes(this.b.getContext(), paramAttributeSet, a, paramInt, 0);
    Drawable localDrawable = paramAttributeSet.getDrawableIfKnown(0);
    if (localDrawable != null) {
      this.b.setIndeterminateDrawable(a(localDrawable));
    }
    localDrawable = paramAttributeSet.getDrawableIfKnown(1);
    if (localDrawable != null) {
      this.b.setProgressDrawable(a(localDrawable, false));
    }
    paramAttributeSet.recycle();
  }
}


/* Location:              ~/android/support/v7/widget/h.class
 *
 * Reversed by:           J
 */