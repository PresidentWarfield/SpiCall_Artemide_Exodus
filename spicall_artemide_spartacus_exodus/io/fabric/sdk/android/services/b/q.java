package io.fabric.sdk.android.services.b;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;

public class q
{
  protected String a(Context paramContext)
  {
    int i = i.a(paramContext, "google_app_id", "string");
    if (i != 0)
    {
      c.g().a("Fabric", "Generating Crashlytics ApiKey from google_app_id in Strings");
      return a(paramContext.getResources().getString(i));
    }
    return null;
  }
  
  protected String a(String paramString)
  {
    return i.b(paramString).substring(0, 40);
  }
  
  public boolean b(Context paramContext)
  {
    boolean bool1 = false;
    if (i.a(paramContext, "com.crashlytics.useFirebaseAppId", false)) {
      return true;
    }
    int i;
    if (i.a(paramContext, "google_app_id", "string") != 0) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if ((TextUtils.isEmpty(new g().c(paramContext))) && (TextUtils.isEmpty(new g().d(paramContext)))) {
      j = 0;
    } else {
      j = 1;
    }
    boolean bool2 = bool1;
    if (i != 0)
    {
      bool2 = bool1;
      if (j == 0) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  public boolean c(Context paramContext)
  {
    paramContext = p.a(paramContext);
    if (paramContext == null) {
      return true;
    }
    return paramContext.a();
  }
}


/* Location:              ~/io/fabric/sdk/android/services/b/q.class
 *
 * Reversed by:           J
 */