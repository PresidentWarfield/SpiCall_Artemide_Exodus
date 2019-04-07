package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.InsetDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;

public final class DrawableCompat
{
  static final DrawableCompatBaseImpl IMPL;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 23) {
      IMPL = new DrawableCompatApi23Impl();
    } else if (Build.VERSION.SDK_INT >= 21) {
      IMPL = new DrawableCompatApi21Impl();
    } else if (Build.VERSION.SDK_INT >= 19) {
      IMPL = new DrawableCompatApi19Impl();
    } else if (Build.VERSION.SDK_INT >= 17) {
      IMPL = new DrawableCompatApi17Impl();
    } else {
      IMPL = new DrawableCompatBaseImpl();
    }
  }
  
  public static void applyTheme(Drawable paramDrawable, Resources.Theme paramTheme)
  {
    IMPL.applyTheme(paramDrawable, paramTheme);
  }
  
  public static boolean canApplyTheme(Drawable paramDrawable)
  {
    return IMPL.canApplyTheme(paramDrawable);
  }
  
  public static void clearColorFilter(Drawable paramDrawable)
  {
    IMPL.clearColorFilter(paramDrawable);
  }
  
  public static int getAlpha(Drawable paramDrawable)
  {
    return IMPL.getAlpha(paramDrawable);
  }
  
  public static ColorFilter getColorFilter(Drawable paramDrawable)
  {
    return IMPL.getColorFilter(paramDrawable);
  }
  
  public static int getLayoutDirection(Drawable paramDrawable)
  {
    return IMPL.getLayoutDirection(paramDrawable);
  }
  
  public static void inflate(Drawable paramDrawable, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
  {
    IMPL.inflate(paramDrawable, paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
  }
  
  public static boolean isAutoMirrored(Drawable paramDrawable)
  {
    return IMPL.isAutoMirrored(paramDrawable);
  }
  
  public static void jumpToCurrentState(Drawable paramDrawable)
  {
    IMPL.jumpToCurrentState(paramDrawable);
  }
  
  public static void setAutoMirrored(Drawable paramDrawable, boolean paramBoolean)
  {
    IMPL.setAutoMirrored(paramDrawable, paramBoolean);
  }
  
  public static void setHotspot(Drawable paramDrawable, float paramFloat1, float paramFloat2)
  {
    IMPL.setHotspot(paramDrawable, paramFloat1, paramFloat2);
  }
  
  public static void setHotspotBounds(Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    IMPL.setHotspotBounds(paramDrawable, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static boolean setLayoutDirection(Drawable paramDrawable, int paramInt)
  {
    return IMPL.setLayoutDirection(paramDrawable, paramInt);
  }
  
  public static void setTint(Drawable paramDrawable, int paramInt)
  {
    IMPL.setTint(paramDrawable, paramInt);
  }
  
  public static void setTintList(Drawable paramDrawable, ColorStateList paramColorStateList)
  {
    IMPL.setTintList(paramDrawable, paramColorStateList);
  }
  
  public static void setTintMode(Drawable paramDrawable, PorterDuff.Mode paramMode)
  {
    IMPL.setTintMode(paramDrawable, paramMode);
  }
  
  public static <T extends Drawable> T unwrap(Drawable paramDrawable)
  {
    if ((paramDrawable instanceof DrawableWrapper)) {
      return ((DrawableWrapper)paramDrawable).getWrappedDrawable();
    }
    return paramDrawable;
  }
  
  public static Drawable wrap(Drawable paramDrawable)
  {
    return IMPL.wrap(paramDrawable);
  }
  
  static class DrawableCompatApi17Impl
    extends DrawableCompat.DrawableCompatBaseImpl
  {
    private static final String TAG = "DrawableCompatApi17";
    private static Method sGetLayoutDirectionMethod;
    private static boolean sGetLayoutDirectionMethodFetched;
    private static Method sSetLayoutDirectionMethod;
    private static boolean sSetLayoutDirectionMethodFetched;
    
    public int getLayoutDirection(Drawable paramDrawable)
    {
      if (!sGetLayoutDirectionMethodFetched)
      {
        try
        {
          sGetLayoutDirectionMethod = Drawable.class.getDeclaredMethod("getLayoutDirection", new Class[0]);
          sGetLayoutDirectionMethod.setAccessible(true);
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
          Log.i("DrawableCompatApi17", "Failed to retrieve getLayoutDirection() method", localNoSuchMethodException);
        }
        sGetLayoutDirectionMethodFetched = true;
      }
      Method localMethod = sGetLayoutDirectionMethod;
      if (localMethod != null) {
        try
        {
          int i = ((Integer)localMethod.invoke(paramDrawable, new Object[0])).intValue();
          return i;
        }
        catch (Exception paramDrawable)
        {
          Log.i("DrawableCompatApi17", "Failed to invoke getLayoutDirection() via reflection", paramDrawable);
          sGetLayoutDirectionMethod = null;
        }
      }
      return 0;
    }
    
    public boolean setLayoutDirection(Drawable paramDrawable, int paramInt)
    {
      if (!sSetLayoutDirectionMethodFetched)
      {
        try
        {
          sSetLayoutDirectionMethod = Drawable.class.getDeclaredMethod("setLayoutDirection", new Class[] { Integer.TYPE });
          sSetLayoutDirectionMethod.setAccessible(true);
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
          Log.i("DrawableCompatApi17", "Failed to retrieve setLayoutDirection(int) method", localNoSuchMethodException);
        }
        sSetLayoutDirectionMethodFetched = true;
      }
      Method localMethod = sSetLayoutDirectionMethod;
      if (localMethod != null) {
        try
        {
          localMethod.invoke(paramDrawable, new Object[] { Integer.valueOf(paramInt) });
          return true;
        }
        catch (Exception paramDrawable)
        {
          Log.i("DrawableCompatApi17", "Failed to invoke setLayoutDirection(int) via reflection", paramDrawable);
          sSetLayoutDirectionMethod = null;
        }
      }
      return false;
    }
  }
  
  static class DrawableCompatApi19Impl
    extends DrawableCompat.DrawableCompatApi17Impl
  {
    public int getAlpha(Drawable paramDrawable)
    {
      return paramDrawable.getAlpha();
    }
    
    public boolean isAutoMirrored(Drawable paramDrawable)
    {
      return paramDrawable.isAutoMirrored();
    }
    
    public void setAutoMirrored(Drawable paramDrawable, boolean paramBoolean)
    {
      paramDrawable.setAutoMirrored(paramBoolean);
    }
    
    public Drawable wrap(Drawable paramDrawable)
    {
      if (!(paramDrawable instanceof TintAwareDrawable)) {
        return new DrawableWrapperApi19(paramDrawable);
      }
      return paramDrawable;
    }
  }
  
  static class DrawableCompatApi21Impl
    extends DrawableCompat.DrawableCompatApi19Impl
  {
    public void applyTheme(Drawable paramDrawable, Resources.Theme paramTheme)
    {
      paramDrawable.applyTheme(paramTheme);
    }
    
    public boolean canApplyTheme(Drawable paramDrawable)
    {
      return paramDrawable.canApplyTheme();
    }
    
    public void clearColorFilter(Drawable paramDrawable)
    {
      paramDrawable.clearColorFilter();
      if ((paramDrawable instanceof InsetDrawable))
      {
        clearColorFilter(((InsetDrawable)paramDrawable).getDrawable());
      }
      else if ((paramDrawable instanceof DrawableWrapper))
      {
        clearColorFilter(((DrawableWrapper)paramDrawable).getWrappedDrawable());
      }
      else if ((paramDrawable instanceof DrawableContainer))
      {
        DrawableContainer.DrawableContainerState localDrawableContainerState = (DrawableContainer.DrawableContainerState)((DrawableContainer)paramDrawable).getConstantState();
        if (localDrawableContainerState != null)
        {
          int i = 0;
          int j = localDrawableContainerState.getChildCount();
          while (i < j)
          {
            paramDrawable = localDrawableContainerState.getChild(i);
            if (paramDrawable != null) {
              clearColorFilter(paramDrawable);
            }
            i++;
          }
        }
      }
    }
    
    public ColorFilter getColorFilter(Drawable paramDrawable)
    {
      return paramDrawable.getColorFilter();
    }
    
    public void inflate(Drawable paramDrawable, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    {
      paramDrawable.inflate(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
    }
    
    public void setHotspot(Drawable paramDrawable, float paramFloat1, float paramFloat2)
    {
      paramDrawable.setHotspot(paramFloat1, paramFloat2);
    }
    
    public void setHotspotBounds(Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramDrawable.setHotspotBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void setTint(Drawable paramDrawable, int paramInt)
    {
      paramDrawable.setTint(paramInt);
    }
    
    public void setTintList(Drawable paramDrawable, ColorStateList paramColorStateList)
    {
      paramDrawable.setTintList(paramColorStateList);
    }
    
    public void setTintMode(Drawable paramDrawable, PorterDuff.Mode paramMode)
    {
      paramDrawable.setTintMode(paramMode);
    }
    
    public Drawable wrap(Drawable paramDrawable)
    {
      if (!(paramDrawable instanceof TintAwareDrawable)) {
        return new DrawableWrapperApi21(paramDrawable);
      }
      return paramDrawable;
    }
  }
  
  static class DrawableCompatApi23Impl
    extends DrawableCompat.DrawableCompatApi21Impl
  {
    public void clearColorFilter(Drawable paramDrawable)
    {
      paramDrawable.clearColorFilter();
    }
    
    public int getLayoutDirection(Drawable paramDrawable)
    {
      return paramDrawable.getLayoutDirection();
    }
    
    public boolean setLayoutDirection(Drawable paramDrawable, int paramInt)
    {
      return paramDrawable.setLayoutDirection(paramInt);
    }
    
    public Drawable wrap(Drawable paramDrawable)
    {
      return paramDrawable;
    }
  }
  
  static class DrawableCompatBaseImpl
  {
    public void applyTheme(Drawable paramDrawable, Resources.Theme paramTheme) {}
    
    public boolean canApplyTheme(Drawable paramDrawable)
    {
      return false;
    }
    
    public void clearColorFilter(Drawable paramDrawable)
    {
      paramDrawable.clearColorFilter();
    }
    
    public int getAlpha(Drawable paramDrawable)
    {
      return 0;
    }
    
    public ColorFilter getColorFilter(Drawable paramDrawable)
    {
      return null;
    }
    
    public int getLayoutDirection(Drawable paramDrawable)
    {
      return 0;
    }
    
    public void inflate(Drawable paramDrawable, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    {
      paramDrawable.inflate(paramResources, paramXmlPullParser, paramAttributeSet);
    }
    
    public boolean isAutoMirrored(Drawable paramDrawable)
    {
      return false;
    }
    
    public void jumpToCurrentState(Drawable paramDrawable)
    {
      paramDrawable.jumpToCurrentState();
    }
    
    public void setAutoMirrored(Drawable paramDrawable, boolean paramBoolean) {}
    
    public void setHotspot(Drawable paramDrawable, float paramFloat1, float paramFloat2) {}
    
    public void setHotspotBounds(Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
    
    public boolean setLayoutDirection(Drawable paramDrawable, int paramInt)
    {
      return false;
    }
    
    public void setTint(Drawable paramDrawable, int paramInt)
    {
      if ((paramDrawable instanceof TintAwareDrawable)) {
        ((TintAwareDrawable)paramDrawable).setTint(paramInt);
      }
    }
    
    public void setTintList(Drawable paramDrawable, ColorStateList paramColorStateList)
    {
      if ((paramDrawable instanceof TintAwareDrawable)) {
        ((TintAwareDrawable)paramDrawable).setTintList(paramColorStateList);
      }
    }
    
    public void setTintMode(Drawable paramDrawable, PorterDuff.Mode paramMode)
    {
      if ((paramDrawable instanceof TintAwareDrawable)) {
        ((TintAwareDrawable)paramDrawable).setTintMode(paramMode);
      }
    }
    
    public Drawable wrap(Drawable paramDrawable)
    {
      if (!(paramDrawable instanceof TintAwareDrawable)) {
        return new DrawableWrapperApi14(paramDrawable);
      }
      return paramDrawable;
    }
  }
}


/* Location:              ~/android/support/v4/graphics/drawable/DrawableCompat.class
 *
 * Reversed by:           J
 */