package android.support.v7.widget;

import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build.VERSION;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.support.v7.d.a.a;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DrawableUtils
{
  public static final Rect INSETS_NONE = new Rect();
  private static final String TAG = "DrawableUtils";
  private static final String VECTOR_DRAWABLE_CLAZZ_NAME = "android.graphics.drawable.VectorDrawable";
  private static Class<?> sInsetsClazz;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 18) {}
    try
    {
      sInsetsClazz = Class.forName("android.graphics.Insets");
      return;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      for (;;) {}
    }
  }
  
  public static boolean canSafelyMutateDrawable(Drawable paramDrawable)
  {
    if ((Build.VERSION.SDK_INT < 15) && ((paramDrawable instanceof InsetDrawable))) {
      return false;
    }
    if ((Build.VERSION.SDK_INT < 15) && ((paramDrawable instanceof GradientDrawable))) {
      return false;
    }
    if ((Build.VERSION.SDK_INT < 17) && ((paramDrawable instanceof LayerDrawable))) {
      return false;
    }
    if ((paramDrawable instanceof DrawableContainer))
    {
      paramDrawable = paramDrawable.getConstantState();
      if ((paramDrawable instanceof DrawableContainer.DrawableContainerState))
      {
        paramDrawable = ((DrawableContainer.DrawableContainerState)paramDrawable).getChildren();
        int i = paramDrawable.length;
        for (int j = 0; j < i; j++) {
          if (!canSafelyMutateDrawable(paramDrawable[j])) {
            return false;
          }
        }
      }
    }
    else
    {
      if ((paramDrawable instanceof DrawableWrapper)) {
        return canSafelyMutateDrawable(((DrawableWrapper)paramDrawable).getWrappedDrawable());
      }
      if ((paramDrawable instanceof a)) {
        return canSafelyMutateDrawable(((a)paramDrawable).b());
      }
      if ((paramDrawable instanceof ScaleDrawable)) {
        return canSafelyMutateDrawable(((ScaleDrawable)paramDrawable).getDrawable());
      }
    }
    return true;
  }
  
  static void fixDrawable(Drawable paramDrawable)
  {
    if ((Build.VERSION.SDK_INT == 21) && ("android.graphics.drawable.VectorDrawable".equals(paramDrawable.getClass().getName()))) {
      fixVectorDrawableTinting(paramDrawable);
    }
  }
  
  private static void fixVectorDrawableTinting(Drawable paramDrawable)
  {
    int[] arrayOfInt = paramDrawable.getState();
    if ((arrayOfInt != null) && (arrayOfInt.length != 0)) {
      paramDrawable.setState(ad.h);
    } else {
      paramDrawable.setState(ad.e);
    }
    paramDrawable.setState(arrayOfInt);
  }
  
  public static Rect getOpticalBounds(Drawable paramDrawable)
  {
    if (sInsetsClazz != null) {
      try
      {
        paramDrawable = DrawableCompat.unwrap(paramDrawable);
        Object localObject1 = paramDrawable.getClass().getMethod("getOpticalInsets", new Class[0]).invoke(paramDrawable, new Object[0]);
        if (localObject1 != null)
        {
          Rect localRect = new android/graphics/Rect;
          localRect.<init>();
          label182:
          for (Object localObject2 : sInsetsClazz.getFields())
          {
            String str = ((Field)localObject2).getName();
            int k = str.hashCode();
            if (k != -1383228885)
            {
              if (k != 115029)
              {
                if (k != 3317767)
                {
                  if ((k == 108511772) && (str.equals("right")))
                  {
                    k = 2;
                    break label182;
                  }
                }
                else if (str.equals("left"))
                {
                  k = 0;
                  break label182;
                }
              }
              else if (str.equals("top"))
              {
                k = 1;
                break label182;
              }
            }
            else if (str.equals("bottom"))
            {
              k = 3;
              break label182;
            }
            k = -1;
            switch (k)
            {
            default: 
              break;
            case 3: 
              localRect.bottom = ((Field)localObject2).getInt(localObject1);
              break;
            case 2: 
              localRect.right = ((Field)localObject2).getInt(localObject1);
              break;
            case 1: 
              localRect.top = ((Field)localObject2).getInt(localObject1);
              break;
            case 0: 
              localRect.left = ((Field)localObject2).getInt(localObject1);
            }
          }
          return localRect;
        }
      }
      catch (Exception paramDrawable)
      {
        Log.e("DrawableUtils", "Couldn't obtain the optical insets. Ignoring.");
      }
    }
    return INSETS_NONE;
  }
  
  public static PorterDuff.Mode parseTintMode(int paramInt, PorterDuff.Mode paramMode)
  {
    if (paramInt != 3)
    {
      if (paramInt != 5)
      {
        if (paramInt != 9)
        {
          switch (paramInt)
          {
          default: 
            return paramMode;
          case 16: 
            if (Build.VERSION.SDK_INT >= 11) {
              paramMode = PorterDuff.Mode.valueOf("ADD");
            }
            return paramMode;
          case 15: 
            return PorterDuff.Mode.SCREEN;
          }
          return PorterDuff.Mode.MULTIPLY;
        }
        return PorterDuff.Mode.SRC_ATOP;
      }
      return PorterDuff.Mode.SRC_IN;
    }
    return PorterDuff.Mode.SRC_OVER;
  }
}


/* Location:              ~/android/support/v7/widget/DrawableUtils.class
 *
 * Reversed by:           J
 */