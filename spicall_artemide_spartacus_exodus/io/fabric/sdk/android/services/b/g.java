package io.fabric.sdk.android.services.b;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;

public class g
{
  protected String a()
  {
    return "Fabric could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"io.fabric.ApiKey\" android:value=\"YOUR_API_KEY\"/>";
  }
  
  public String a(Context paramContext)
  {
    Object localObject1 = c(paramContext);
    Object localObject2 = localObject1;
    if (TextUtils.isEmpty((CharSequence)localObject1)) {
      localObject2 = d(paramContext);
    }
    localObject1 = localObject2;
    if (TextUtils.isEmpty((CharSequence)localObject2)) {
      localObject1 = b(paramContext);
    }
    if (TextUtils.isEmpty((CharSequence)localObject1)) {
      e(paramContext);
    }
    return (String)localObject1;
  }
  
  protected String b(Context paramContext)
  {
    return new q().a(paramContext);
  }
  
  protected String c(Context paramContext)
  {
    StringBuilder localStringBuilder = null;
    Object localObject1 = null;
    Context localContext = null;
    Object localObject2 = localStringBuilder;
    try
    {
      Object localObject3 = paramContext.getPackageName();
      localObject2 = localStringBuilder;
      localObject3 = paramContext.getPackageManager().getApplicationInfo((String)localObject3, 128).metaData;
      paramContext = (Context)localObject1;
      if (localObject3 == null) {
        return paramContext;
      }
      localObject2 = localStringBuilder;
      paramContext = ((Bundle)localObject3).getString("io.fabric.ApiKey");
      try
      {
        if ("@string/twitter_consumer_secret".equals(paramContext)) {
          c.g().a("Fabric", "Ignoring bad default value for Fabric ApiKey set by FirebaseUI-Auth");
        } else {
          localContext = paramContext;
        }
        paramContext = localContext;
        if (localContext != null) {
          return paramContext;
        }
        localObject2 = localContext;
        c.g().a("Fabric", "Falling back to Crashlytics key lookup from Manifest");
        localObject2 = localContext;
        paramContext = ((Bundle)localObject3).getString("com.crashlytics.ApiKey");
      }
      catch (Exception localException1) {}
      localObject2 = c.g();
    }
    catch (Exception localException2)
    {
      paramContext = (Context)localObject2;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("Caught non-fatal exception while retrieving apiKey: ");
    localStringBuilder.append(localException2);
    ((k)localObject2).a("Fabric", localStringBuilder.toString());
    return paramContext;
  }
  
  protected String d(Context paramContext)
  {
    int i = i.a(paramContext, "io.fabric.ApiKey", "string");
    int j = i;
    if (i == 0)
    {
      c.g().a("Fabric", "Falling back to Crashlytics key lookup from Strings");
      j = i.a(paramContext, "com.crashlytics.ApiKey", "string");
    }
    if (j != 0) {
      paramContext = paramContext.getResources().getString(j);
    } else {
      paramContext = null;
    }
    return paramContext;
  }
  
  protected void e(Context paramContext)
  {
    if ((!c.h()) && (!i.i(paramContext)))
    {
      c.g().e("Fabric", a());
      return;
    }
    throw new IllegalArgumentException(a());
  }
}


/* Location:              ~/io/fabric/sdk/android/services/b/g.class
 *
 * Reversed by:           J
 */