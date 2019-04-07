package io.fabric.sdk.android.services.b;

import android.content.Context;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.lang.reflect.Method;

final class p
  implements o
{
  private final Method a;
  private final Object b;
  
  private p(Class paramClass, Object paramObject)
  {
    this.b = paramObject;
    this.a = paramClass.getDeclaredMethod("isDataCollectionDefaultEnabled", new Class[0]);
  }
  
  public static o a(Context paramContext)
  {
    try
    {
      paramContext = paramContext.getClassLoader().loadClass("com.google.firebase.FirebaseApp");
      paramContext = new p(paramContext, paramContext.getDeclaredMethod("getInstance", new Class[0]).invoke(paramContext, new Object[0]));
      return paramContext;
    }
    catch (Exception paramContext)
    {
      c.g().a("Fabric", "Unexpected error loading FirebaseApp instance.", paramContext);
    }
    catch (NoSuchMethodException paramContext)
    {
      k localk = c.g();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Could not find method: ");
      localStringBuilder.append(paramContext.getMessage());
      localk.a("Fabric", localStringBuilder.toString());
    }
    catch (ClassNotFoundException paramContext)
    {
      c.g().a("Fabric", "Could not find class: com.google.firebase.FirebaseApp");
    }
    return null;
  }
  
  public boolean a()
  {
    try
    {
      boolean bool = ((Boolean)this.a.invoke(this.b, new Object[0])).booleanValue();
      return bool;
    }
    catch (Exception localException)
    {
      c.g().a("Fabric", "Cannot check isDataCollectionDefaultEnabled on FirebaseApp.", localException);
    }
    return false;
  }
}


/* Location:              ~/io/fabric/sdk/android/services/b/p.class
 *
 * Reversed by:           J
 */