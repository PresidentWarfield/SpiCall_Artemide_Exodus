package android.support.v7.app;

import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.Log;
import android.util.LongSparseArray;
import java.lang.reflect.Field;
import java.util.Map;

class p
{
  private static Field a;
  private static boolean b;
  private static Class c;
  private static boolean d;
  private static Field e;
  private static boolean f;
  private static Field g;
  private static boolean h;
  
  static boolean a(Resources paramResources)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return d(paramResources);
    }
    if (Build.VERSION.SDK_INT >= 23) {
      return c(paramResources);
    }
    if (Build.VERSION.SDK_INT >= 21) {
      return b(paramResources);
    }
    return false;
  }
  
  private static boolean a(Object paramObject)
  {
    if (!d)
    {
      try
      {
        c = Class.forName("android.content.res.ThemedResourceCache");
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        Log.e("ResourcesFlusher", "Could not find ThemedResourceCache class", localClassNotFoundException);
      }
      d = true;
    }
    Class localClass = c;
    if (localClass == null) {
      return false;
    }
    if (!f)
    {
      try
      {
        e = localClass.getDeclaredField("mUnthemedEntries");
        e.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        Log.e("ResourcesFlusher", "Could not retrieve ThemedResourceCache#mUnthemedEntries field", localNoSuchFieldException);
      }
      f = true;
    }
    Field localField = e;
    if (localField == null) {
      return false;
    }
    try
    {
      paramObject = (LongSparseArray)localField.get(paramObject);
    }
    catch (IllegalAccessException paramObject)
    {
      Log.e("ResourcesFlusher", "Could not retrieve value from ThemedResourceCache#mUnthemedEntries", (Throwable)paramObject);
      paramObject = null;
    }
    if (paramObject != null)
    {
      ((LongSparseArray)paramObject).clear();
      return true;
    }
    return false;
  }
  
  private static boolean b(Resources paramResources)
  {
    if (!b)
    {
      try
      {
        a = Resources.class.getDeclaredField("mDrawableCache");
        a.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        Log.e("ResourcesFlusher", "Could not retrieve Resources#mDrawableCache field", localNoSuchFieldException);
      }
      b = true;
    }
    Field localField = a;
    if (localField != null)
    {
      try
      {
        paramResources = (Map)localField.get(paramResources);
      }
      catch (IllegalAccessException paramResources)
      {
        Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mDrawableCache", paramResources);
        paramResources = null;
      }
      if (paramResources != null)
      {
        paramResources.clear();
        return true;
      }
    }
    return false;
  }
  
  private static boolean c(Resources paramResources)
  {
    if (!b)
    {
      try
      {
        a = Resources.class.getDeclaredField("mDrawableCache");
        a.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        Log.e("ResourcesFlusher", "Could not retrieve Resources#mDrawableCache field", localNoSuchFieldException);
      }
      b = true;
    }
    Object localObject2 = null;
    Field localField = a;
    Object localObject1 = localObject2;
    if (localField != null) {
      try
      {
        localObject1 = localField.get(paramResources);
      }
      catch (IllegalAccessException paramResources)
      {
        Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mDrawableCache", paramResources);
        localObject1 = localObject2;
      }
    }
    boolean bool1 = false;
    if (localObject1 == null) {
      return false;
    }
    boolean bool2 = bool1;
    if (localObject1 != null)
    {
      bool2 = bool1;
      if (a(localObject1)) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  private static boolean d(Resources paramResources)
  {
    boolean bool1 = h;
    boolean bool2 = true;
    if (!bool1)
    {
      try
      {
        g = Resources.class.getDeclaredField("mResourcesImpl");
        g.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException1)
      {
        Log.e("ResourcesFlusher", "Could not retrieve Resources#mResourcesImpl field", localNoSuchFieldException1);
      }
      h = true;
    }
    Field localField1 = g;
    if (localField1 == null) {
      return false;
    }
    Object localObject2 = null;
    try
    {
      paramResources = localField1.get(paramResources);
    }
    catch (IllegalAccessException paramResources)
    {
      Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mResourcesImpl", paramResources);
      paramResources = null;
    }
    if (paramResources == null) {
      return false;
    }
    if (!b)
    {
      try
      {
        a = paramResources.getClass().getDeclaredField("mDrawableCache");
        a.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException2)
      {
        Log.e("ResourcesFlusher", "Could not retrieve ResourcesImpl#mDrawableCache field", localNoSuchFieldException2);
      }
      b = true;
    }
    Field localField2 = a;
    Object localObject1 = localObject2;
    if (localField2 != null) {
      try
      {
        localObject1 = localField2.get(paramResources);
      }
      catch (IllegalAccessException paramResources)
      {
        Log.e("ResourcesFlusher", "Could not retrieve value from ResourcesImpl#mDrawableCache", paramResources);
        localObject1 = localObject2;
      }
    }
    if ((localObject1 == null) || (!a(localObject1))) {
      bool2 = false;
    }
    return bool2;
  }
}


/* Location:              ~/android/support/v7/app/p.class
 *
 * Reversed by:           J
 */