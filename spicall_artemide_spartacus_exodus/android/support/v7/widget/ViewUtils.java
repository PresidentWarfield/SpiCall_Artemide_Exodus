package android.support.v7.widget;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewUtils
{
  private static final String TAG = "ViewUtils";
  private static Method sComputeFitSystemWindowsMethod;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 18) {
      try
      {
        sComputeFitSystemWindowsMethod = View.class.getDeclaredMethod("computeFitSystemWindows", new Class[] { Rect.class, Rect.class });
        if (!sComputeFitSystemWindowsMethod.isAccessible()) {
          sComputeFitSystemWindowsMethod.setAccessible(true);
        }
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Log.d("ViewUtils", "Could not find method computeFitSystemWindows. Oh well.");
      }
    }
  }
  
  public static void computeFitSystemWindows(View paramView, Rect paramRect1, Rect paramRect2)
  {
    Method localMethod = sComputeFitSystemWindowsMethod;
    if (localMethod != null) {
      try
      {
        localMethod.invoke(paramView, new Object[] { paramRect1, paramRect2 });
      }
      catch (Exception paramView)
      {
        Log.d("ViewUtils", "Could not invoke computeFitSystemWindows", paramView);
      }
    }
  }
  
  public static boolean isLayoutRtl(View paramView)
  {
    int i = ViewCompat.getLayoutDirection(paramView);
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  public static void makeOptionalFitsSystemWindows(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      try
      {
        Method localMethod = paramView.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0]);
        if (!localMethod.isAccessible()) {
          localMethod.setAccessible(true);
        }
        localMethod.invoke(paramView, new Object[0]);
      }
      catch (IllegalAccessException paramView)
      {
        Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", paramView);
      }
      catch (InvocationTargetException paramView)
      {
        Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", paramView);
      }
      catch (NoSuchMethodException paramView)
      {
        Log.d("ViewUtils", "Could not find method makeOptionalFitsSystemWindows. Oh well...");
      }
    }
  }
}


/* Location:              ~/android/support/v7/widget/ViewUtils.class
 *
 * Reversed by:           J
 */