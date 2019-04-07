package io.fabric.sdk.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import io.fabric.sdk.android.services.b.g;
import io.fabric.sdk.android.services.b.i;
import io.fabric.sdk.android.services.b.r;
import io.fabric.sdk.android.services.e.e;
import io.fabric.sdk.android.services.e.n;
import io.fabric.sdk.android.services.e.q;
import io.fabric.sdk.android.services.e.t;
import io.fabric.sdk.android.services.e.y;
import io.fabric.sdk.android.services.network.b;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Future;

class l
  extends h<Boolean>
{
  private final io.fabric.sdk.android.services.network.d a = new b();
  private PackageManager b;
  private String c;
  private PackageInfo d;
  private String e;
  private String f;
  private String g;
  private String h;
  private String i;
  private final Future<Map<String, j>> j;
  private final Collection<h> k;
  
  public l(Future<Map<String, j>> paramFuture, Collection<h> paramCollection)
  {
    this.j = paramFuture;
    this.k = paramCollection;
  }
  
  private io.fabric.sdk.android.services.e.d a(n paramn, Collection<j> paramCollection)
  {
    Object localObject = getContext();
    String str = new g().a((Context)localObject);
    localObject = i.a(new String[] { i.m((Context)localObject) });
    int m = io.fabric.sdk.android.services.b.l.a(this.g).a();
    return new io.fabric.sdk.android.services.e.d(str, getIdManager().c(), this.f, this.e, (String)localObject, this.h, m, this.i, "0", paramn, paramCollection);
  }
  
  private boolean a(e parame, n paramn, Collection<j> paramCollection)
  {
    paramn = a(paramn, paramCollection);
    return new y(this, b(), parame.c, this.a).a(paramn);
  }
  
  private boolean a(String paramString, e parame, Collection<j> paramCollection)
  {
    boolean bool;
    if ("new".equals(parame.b))
    {
      if (b(paramString, parame, paramCollection))
      {
        bool = q.a().d();
      }
      else
      {
        c.g().e("Fabric", "Failed to create app with Crashlytics service.", null);
        bool = false;
      }
    }
    else if ("configured".equals(parame.b))
    {
      bool = q.a().d();
    }
    else
    {
      if (parame.f)
      {
        c.g().a("Fabric", "Server says an update is required - forcing a full App update.");
        c(paramString, parame, paramCollection);
      }
      bool = true;
    }
    return bool;
  }
  
  private boolean b(String paramString, e parame, Collection<j> paramCollection)
  {
    paramString = a(n.a(getContext(), paramString), paramCollection);
    return new io.fabric.sdk.android.services.e.h(this, b(), parame.c, this.a).a(paramString);
  }
  
  private t c()
  {
    try
    {
      q.a().a(this, this.idManager, this.a, this.e, this.f, b()).c();
      t localt = q.a().b();
      return localt;
    }
    catch (Exception localException)
    {
      c.g().e("Fabric", "Error dealing with settings", localException);
    }
    return null;
  }
  
  private boolean c(String paramString, e parame, Collection<j> paramCollection)
  {
    return a(parame, n.a(getContext(), paramString), paramCollection);
  }
  
  protected Boolean a()
  {
    String str = i.k(getContext());
    t localt = c();
    if (localt != null) {
      try
      {
        if (this.j != null) {
          localObject = (Map)this.j.get();
        } else {
          localObject = new HashMap();
        }
        Object localObject = a((Map)localObject, this.k);
        bool = a(str, localt.a, ((Map)localObject).values());
      }
      catch (Exception localException)
      {
        c.g().e("Fabric", "Error performing auto configuration.", localException);
      }
    }
    boolean bool = false;
    return Boolean.valueOf(bool);
  }
  
  Map<String, j> a(Map<String, j> paramMap, Collection<h> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      paramCollection = (h)localIterator.next();
      if (!paramMap.containsKey(paramCollection.getIdentifier())) {
        paramMap.put(paramCollection.getIdentifier(), new j(paramCollection.getIdentifier(), paramCollection.getVersion(), "binary"));
      }
    }
    return paramMap;
  }
  
  String b()
  {
    return i.b(getContext(), "com.crashlytics.ApiEndpoint");
  }
  
  public String getIdentifier()
  {
    return "io.fabric.sdk.android:fabric";
  }
  
  public String getVersion()
  {
    return "1.4.4.27";
  }
  
  protected boolean onPreExecute()
  {
    try
    {
      this.g = getIdManager().i();
      this.b = getContext().getPackageManager();
      this.c = getContext().getPackageName();
      this.d = this.b.getPackageInfo(this.c, 0);
      this.e = Integer.toString(this.d.versionCode);
      String str;
      if (this.d.versionName == null) {
        str = "0.0";
      } else {
        str = this.d.versionName;
      }
      this.f = str;
      this.h = this.b.getApplicationLabel(getContext().getApplicationInfo()).toString();
      this.i = Integer.toString(getContext().getApplicationInfo().targetSdkVersion);
      return true;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      c.g().e("Fabric", "Failed init", localNameNotFoundException);
    }
    return false;
  }
}


/* Location:              ~/io/fabric/sdk/android/l.class
 *
 * Reversed by:           J
 */