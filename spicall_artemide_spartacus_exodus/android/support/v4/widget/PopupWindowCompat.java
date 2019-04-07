package android.support.v4.widget;

import android.os.Build.VERSION;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class PopupWindowCompat
{
  static final PopupWindowCompatBaseImpl IMPL;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 23) {
      IMPL = new PopupWindowCompatApi23Impl();
    } else if (Build.VERSION.SDK_INT >= 21) {
      IMPL = new PopupWindowCompatApi21Impl();
    } else if (Build.VERSION.SDK_INT >= 19) {
      IMPL = new PopupWindowCompatApi19Impl();
    } else {
      IMPL = new PopupWindowCompatBaseImpl();
    }
  }
  
  public static boolean getOverlapAnchor(PopupWindow paramPopupWindow)
  {
    return IMPL.getOverlapAnchor(paramPopupWindow);
  }
  
  public static int getWindowLayoutType(PopupWindow paramPopupWindow)
  {
    return IMPL.getWindowLayoutType(paramPopupWindow);
  }
  
  public static void setOverlapAnchor(PopupWindow paramPopupWindow, boolean paramBoolean)
  {
    IMPL.setOverlapAnchor(paramPopupWindow, paramBoolean);
  }
  
  public static void setWindowLayoutType(PopupWindow paramPopupWindow, int paramInt)
  {
    IMPL.setWindowLayoutType(paramPopupWindow, paramInt);
  }
  
  public static void showAsDropDown(PopupWindow paramPopupWindow, View paramView, int paramInt1, int paramInt2, int paramInt3)
  {
    IMPL.showAsDropDown(paramPopupWindow, paramView, paramInt1, paramInt2, paramInt3);
  }
  
  static class PopupWindowCompatApi19Impl
    extends PopupWindowCompat.PopupWindowCompatBaseImpl
  {
    public void showAsDropDown(PopupWindow paramPopupWindow, View paramView, int paramInt1, int paramInt2, int paramInt3)
    {
      paramPopupWindow.showAsDropDown(paramView, paramInt1, paramInt2, paramInt3);
    }
  }
  
  static class PopupWindowCompatApi21Impl
    extends PopupWindowCompat.PopupWindowCompatApi19Impl
  {
    private static final String TAG = "PopupWindowCompatApi21";
    private static Field sOverlapAnchorField;
    
    static
    {
      try
      {
        sOverlapAnchorField = PopupWindow.class.getDeclaredField("mOverlapAnchor");
        sOverlapAnchorField.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        Log.i("PopupWindowCompatApi21", "Could not fetch mOverlapAnchor field from PopupWindow", localNoSuchFieldException);
      }
    }
    
    public boolean getOverlapAnchor(PopupWindow paramPopupWindow)
    {
      Field localField = sOverlapAnchorField;
      if (localField != null) {
        try
        {
          boolean bool = ((Boolean)localField.get(paramPopupWindow)).booleanValue();
          return bool;
        }
        catch (IllegalAccessException paramPopupWindow)
        {
          Log.i("PopupWindowCompatApi21", "Could not get overlap anchor field in PopupWindow", paramPopupWindow);
        }
      }
      return false;
    }
    
    public void setOverlapAnchor(PopupWindow paramPopupWindow, boolean paramBoolean)
    {
      Field localField = sOverlapAnchorField;
      if (localField != null) {
        try
        {
          localField.set(paramPopupWindow, Boolean.valueOf(paramBoolean));
        }
        catch (IllegalAccessException paramPopupWindow)
        {
          Log.i("PopupWindowCompatApi21", "Could not set overlap anchor field in PopupWindow", paramPopupWindow);
        }
      }
    }
  }
  
  static class PopupWindowCompatApi23Impl
    extends PopupWindowCompat.PopupWindowCompatApi21Impl
  {
    public boolean getOverlapAnchor(PopupWindow paramPopupWindow)
    {
      return paramPopupWindow.getOverlapAnchor();
    }
    
    public int getWindowLayoutType(PopupWindow paramPopupWindow)
    {
      return paramPopupWindow.getWindowLayoutType();
    }
    
    public void setOverlapAnchor(PopupWindow paramPopupWindow, boolean paramBoolean)
    {
      paramPopupWindow.setOverlapAnchor(paramBoolean);
    }
    
    public void setWindowLayoutType(PopupWindow paramPopupWindow, int paramInt)
    {
      paramPopupWindow.setWindowLayoutType(paramInt);
    }
  }
  
  static class PopupWindowCompatBaseImpl
  {
    private static Method sGetWindowLayoutTypeMethod;
    private static boolean sGetWindowLayoutTypeMethodAttempted;
    private static Method sSetWindowLayoutTypeMethod;
    private static boolean sSetWindowLayoutTypeMethodAttempted;
    
    public boolean getOverlapAnchor(PopupWindow paramPopupWindow)
    {
      return false;
    }
    
    public int getWindowLayoutType(PopupWindow paramPopupWindow)
    {
      if (!sGetWindowLayoutTypeMethodAttempted) {}
      try
      {
        sGetWindowLayoutTypeMethod = PopupWindow.class.getDeclaredMethod("getWindowLayoutType", new Class[0]);
        sGetWindowLayoutTypeMethod.setAccessible(true);
        sGetWindowLayoutTypeMethodAttempted = true;
        Method localMethod = sGetWindowLayoutTypeMethod;
        if (localMethod != null) {}
        try
        {
          int i = ((Integer)localMethod.invoke(paramPopupWindow, new Object[0])).intValue();
          return i;
        }
        catch (Exception paramPopupWindow)
        {
          for (;;) {}
        }
        return 0;
      }
      catch (Exception localException)
      {
        for (;;) {}
      }
    }
    
    public void setOverlapAnchor(PopupWindow paramPopupWindow, boolean paramBoolean) {}
    
    public void setWindowLayoutType(PopupWindow paramPopupWindow, int paramInt)
    {
      if (!sSetWindowLayoutTypeMethodAttempted) {}
      try
      {
        sSetWindowLayoutTypeMethod = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", new Class[] { Integer.TYPE });
        sSetWindowLayoutTypeMethod.setAccessible(true);
        sSetWindowLayoutTypeMethodAttempted = true;
        localMethod = sSetWindowLayoutTypeMethod;
        if (localMethod == null) {}
      }
      catch (Exception localException)
      {
        try
        {
          Method localMethod;
          localMethod.invoke(paramPopupWindow, new Object[] { Integer.valueOf(paramInt) });
          return;
          localException = localException;
        }
        catch (Exception paramPopupWindow)
        {
          for (;;) {}
        }
      }
    }
    
    public void showAsDropDown(PopupWindow paramPopupWindow, View paramView, int paramInt1, int paramInt2, int paramInt3)
    {
      int i = paramInt1;
      if ((GravityCompat.getAbsoluteGravity(paramInt3, ViewCompat.getLayoutDirection(paramView)) & 0x7) == 5) {
        i = paramInt1 - (paramPopupWindow.getWidth() - paramView.getWidth());
      }
      paramPopupWindow.showAsDropDown(paramView, i, paramInt2);
    }
  }
}


/* Location:              ~/android/support/v4/widget/PopupWindowCompat.class
 *
 * Reversed by:           J
 */