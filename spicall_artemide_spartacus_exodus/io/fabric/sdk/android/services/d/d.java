package io.fabric.sdk.android.services.d;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import io.fabric.sdk.android.h;

public class d
  implements c
{
  private final SharedPreferences a;
  private final String b;
  private final Context c;
  
  public d(Context paramContext, String paramString)
  {
    if (paramContext != null)
    {
      this.c = paramContext;
      this.b = paramString;
      this.a = this.c.getSharedPreferences(this.b, 0);
      return;
    }
    throw new IllegalStateException("Cannot get directory before context has been set. Call Fabric.with() first");
  }
  
  @Deprecated
  public d(h paramh)
  {
    this(paramh.getContext(), paramh.getClass().getName());
  }
  
  public SharedPreferences a()
  {
    return this.a;
  }
  
  @TargetApi(9)
  public boolean a(SharedPreferences.Editor paramEditor)
  {
    if (Build.VERSION.SDK_INT >= 9)
    {
      paramEditor.apply();
      return true;
    }
    return paramEditor.commit();
  }
  
  public SharedPreferences.Editor b()
  {
    return this.a.edit();
  }
}


/* Location:              ~/io/fabric/sdk/android/services/d/d.class
 *
 * Reversed by:           J
 */