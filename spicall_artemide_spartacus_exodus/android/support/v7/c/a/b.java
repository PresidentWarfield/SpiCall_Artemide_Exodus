package android.support.v7.c.a;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import java.util.WeakHashMap;

public final class b
{
  private static final ThreadLocal<TypedValue> a = new ThreadLocal();
  private static final WeakHashMap<Context, SparseArray<a>> b = new WeakHashMap(0);
  private static final Object c = new Object();
  
  public static ColorStateList a(Context paramContext, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return paramContext.getColorStateList(paramInt);
    }
    ColorStateList localColorStateList = d(paramContext, paramInt);
    if (localColorStateList != null) {
      return localColorStateList;
    }
    localColorStateList = c(paramContext, paramInt);
    if (localColorStateList != null)
    {
      a(paramContext, paramInt, localColorStateList);
      return localColorStateList;
    }
    return ContextCompat.getColorStateList(paramContext, paramInt);
  }
  
  private static TypedValue a()
  {
    TypedValue localTypedValue1 = (TypedValue)a.get();
    TypedValue localTypedValue2 = localTypedValue1;
    if (localTypedValue1 == null)
    {
      localTypedValue2 = new TypedValue();
      a.set(localTypedValue2);
    }
    return localTypedValue2;
  }
  
  private static void a(Context paramContext, int paramInt, ColorStateList paramColorStateList)
  {
    synchronized (c)
    {
      Object localObject2 = (SparseArray)b.get(paramContext);
      Object localObject3 = localObject2;
      if (localObject2 == null)
      {
        localObject3 = new android/util/SparseArray;
        ((SparseArray)localObject3).<init>();
        b.put(paramContext, localObject3);
      }
      localObject2 = new android/support/v7/c/a/b$a;
      ((a)localObject2).<init>(paramColorStateList, paramContext.getResources().getConfiguration());
      ((SparseArray)localObject3).append(paramInt, localObject2);
      return;
    }
  }
  
  public static Drawable b(Context paramContext, int paramInt)
  {
    return AppCompatDrawableManager.get().getDrawable(paramContext, paramInt);
  }
  
  private static ColorStateList c(Context paramContext, int paramInt)
  {
    if (e(paramContext, paramInt)) {
      return null;
    }
    Resources localResources = paramContext.getResources();
    XmlResourceParser localXmlResourceParser = localResources.getXml(paramInt);
    try
    {
      paramContext = a.a(localResources, localXmlResourceParser, paramContext.getTheme());
      return paramContext;
    }
    catch (Exception paramContext)
    {
      Log.e("AppCompatResources", "Failed to inflate ColorStateList, leaving it to the framework", paramContext);
    }
    return null;
  }
  
  private static ColorStateList d(Context paramContext, int paramInt)
  {
    synchronized (c)
    {
      SparseArray localSparseArray = (SparseArray)b.get(paramContext);
      if ((localSparseArray != null) && (localSparseArray.size() > 0))
      {
        a locala = (a)localSparseArray.get(paramInt);
        if (locala != null)
        {
          if (locala.b.equals(paramContext.getResources().getConfiguration()))
          {
            paramContext = locala.a;
            return paramContext;
          }
          localSparseArray.remove(paramInt);
        }
      }
      return null;
    }
  }
  
  private static boolean e(Context paramContext, int paramInt)
  {
    paramContext = paramContext.getResources();
    TypedValue localTypedValue = a();
    boolean bool = true;
    paramContext.getValue(paramInt, localTypedValue, true);
    if ((localTypedValue.type < 28) || (localTypedValue.type > 31)) {
      bool = false;
    }
    return bool;
  }
  
  private static class a
  {
    final ColorStateList a;
    final Configuration b;
    
    a(ColorStateList paramColorStateList, Configuration paramConfiguration)
    {
      this.a = paramColorStateList;
      this.b = paramConfiguration;
    }
  }
}


/* Location:              ~/android/support/v7/c/a/b.class
 *
 * Reversed by:           J
 */