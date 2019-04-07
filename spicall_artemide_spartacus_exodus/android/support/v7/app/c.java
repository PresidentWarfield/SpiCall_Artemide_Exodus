package android.support.v7.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.Method;

class c
{
  private static final int[] a = { 16843531 };
  
  public static Drawable a(Activity paramActivity)
  {
    paramActivity = paramActivity.obtainStyledAttributes(a);
    Drawable localDrawable = paramActivity.getDrawable(0);
    paramActivity.recycle();
    return localDrawable;
  }
  
  public static a a(a parama, Activity paramActivity, int paramInt)
  {
    a locala = parama;
    if (parama == null) {
      locala = new a(paramActivity);
    }
    if (locala.a != null) {
      try
      {
        parama = paramActivity.getActionBar();
        locala.b.invoke(parama, new Object[] { Integer.valueOf(paramInt) });
        if (Build.VERSION.SDK_INT <= 19) {
          parama.setSubtitle(parama.getSubtitle());
        }
      }
      catch (Exception parama)
      {
        Log.w("ActionBarDrawerToggleHoneycomb", "Couldn't set content description via JB-MR2 API", parama);
      }
    }
    return locala;
  }
  
  public static a a(a parama, Activity paramActivity, Drawable paramDrawable, int paramInt)
  {
    parama = new a(paramActivity);
    if (parama.a != null) {
      try
      {
        paramActivity = paramActivity.getActionBar();
        parama.a.invoke(paramActivity, new Object[] { paramDrawable });
        parama.b.invoke(paramActivity, new Object[] { Integer.valueOf(paramInt) });
      }
      catch (Exception paramActivity)
      {
        Log.w("ActionBarDrawerToggleHoneycomb", "Couldn't set home-as-up indicator via JB-MR2 API", paramActivity);
      }
    } else if (parama.c != null) {
      parama.c.setImageDrawable(paramDrawable);
    } else {
      Log.w("ActionBarDrawerToggleHoneycomb", "Couldn't set home-as-up indicator");
    }
    return parama;
  }
  
  static class a
  {
    public Method a;
    public Method b;
    public ImageView c;
    
    a(Activity paramActivity)
    {
      try
      {
        this.a = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", new Class[] { Drawable.class });
        this.b = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", new Class[] { Integer.TYPE });
        return;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        paramActivity = paramActivity.findViewById(16908332);
        if (paramActivity == null) {
          return;
        }
        Object localObject = (ViewGroup)paramActivity.getParent();
        if (((ViewGroup)localObject).getChildCount() != 2) {
          return;
        }
        paramActivity = ((ViewGroup)localObject).getChildAt(0);
        localObject = ((ViewGroup)localObject).getChildAt(1);
        if (paramActivity.getId() == 16908332) {
          paramActivity = (Activity)localObject;
        }
        if ((paramActivity instanceof ImageView)) {
          this.c = ((ImageView)paramActivity);
        }
      }
    }
  }
}


/* Location:              ~/android/support/v7/app/c.class
 *
 * Reversed by:           J
 */