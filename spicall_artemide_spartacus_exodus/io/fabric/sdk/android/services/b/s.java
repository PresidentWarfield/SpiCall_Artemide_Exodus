package io.fabric.sdk.android.services.b;

import android.content.Context;
import android.content.pm.PackageManager;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.a.b;
import io.fabric.sdk.android.services.a.d;

public class s
{
  private final d<String> a = new d()
  {
    public String a(Context paramAnonymousContext)
    {
      String str = paramAnonymousContext.getPackageManager().getInstallerPackageName(paramAnonymousContext.getPackageName());
      paramAnonymousContext = str;
      if (str == null) {
        paramAnonymousContext = "";
      }
      return paramAnonymousContext;
    }
  };
  private final b<String> b = new b();
  
  public String a(Context paramContext)
  {
    try
    {
      paramContext = (String)this.b.a(paramContext, this.a);
      boolean bool = "".equals(paramContext);
      if (bool) {
        paramContext = null;
      }
      return paramContext;
    }
    catch (Exception paramContext)
    {
      c.g().e("Fabric", "Failed to determine installer package name", paramContext);
    }
    return null;
  }
}


/* Location:              ~/io/fabric/sdk/android/services/b/s.class
 *
 * Reversed by:           J
 */