package io.fabric.sdk.android.services.b;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import io.fabric.sdk.android.h;
import io.fabric.sdk.android.k;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class r
{
  private static final Pattern e = Pattern.compile("[^\\p{Alnum}]");
  private static final String f = Pattern.quote("/");
  c a;
  b b;
  boolean c;
  q d;
  private final ReentrantLock g = new ReentrantLock();
  private final s h;
  private final boolean i;
  private final boolean j;
  private final Context k;
  private final String l;
  private final String m;
  private final Collection<h> n;
  
  public r(Context paramContext, String paramString1, String paramString2, Collection<h> paramCollection)
  {
    if (paramContext != null)
    {
      if (paramString1 != null)
      {
        if (paramCollection != null)
        {
          this.k = paramContext;
          this.l = paramString1;
          this.m = paramString2;
          this.n = paramCollection;
          this.h = new s();
          this.a = new c(paramContext);
          this.d = new q();
          this.i = i.a(paramContext, "com.crashlytics.CollectDeviceIdentifiers", true);
          if (!this.i)
          {
            paramString1 = io.fabric.sdk.android.c.g();
            paramString2 = new StringBuilder();
            paramString2.append("Device ID collection disabled for ");
            paramString2.append(paramContext.getPackageName());
            paramString1.a("Fabric", paramString2.toString());
          }
          this.j = i.a(paramContext, "com.crashlytics.CollectUserIdentifiers", true);
          if (!this.j)
          {
            paramString1 = io.fabric.sdk.android.c.g();
            paramString2 = new StringBuilder();
            paramString2.append("User information collection disabled for ");
            paramString2.append(paramContext.getPackageName());
            paramString1.a("Fabric", paramString2.toString());
          }
          return;
        }
        throw new IllegalArgumentException("kits must not be null");
      }
      throw new IllegalArgumentException("appIdentifier must not be null");
    }
    throw new IllegalArgumentException("appContext must not be null");
  }
  
  @SuppressLint({"CommitPrefEdits"})
  private String a(SharedPreferences paramSharedPreferences)
  {
    this.g.lock();
    try
    {
      String str1 = paramSharedPreferences.getString("crashlytics.installation.id", null);
      String str2 = str1;
      if (str1 == null)
      {
        str2 = a(UUID.randomUUID().toString());
        paramSharedPreferences.edit().putString("crashlytics.installation.id", str2).commit();
      }
      return str2;
    }
    finally
    {
      this.g.unlock();
    }
  }
  
  private String a(String paramString)
  {
    if (paramString == null) {
      paramString = null;
    } else {
      paramString = e.matcher(paramString).replaceAll("").toLowerCase(Locale.US);
    }
    return paramString;
  }
  
  @SuppressLint({"CommitPrefEdits"})
  private void a(SharedPreferences paramSharedPreferences, String paramString)
  {
    this.g.lock();
    try
    {
      boolean bool = TextUtils.isEmpty(paramString);
      if (bool) {
        return;
      }
      String str = paramSharedPreferences.getString("crashlytics.advertising.id", null);
      if (TextUtils.isEmpty(str)) {
        paramSharedPreferences.edit().putString("crashlytics.advertising.id", paramString).commit();
      } else if (!str.equals(paramString)) {
        paramSharedPreferences.edit().remove("crashlytics.installation.id").putString("crashlytics.advertising.id", paramString).commit();
      }
      return;
    }
    finally
    {
      this.g.unlock();
    }
  }
  
  private void a(Map<a, String> paramMap, a parama, String paramString)
  {
    if (paramString != null) {
      paramMap.put(parama, paramString);
    }
  }
  
  private String b(String paramString)
  {
    return paramString.replaceAll(f, "");
  }
  
  private void b(SharedPreferences paramSharedPreferences)
  {
    b localb = l();
    if (localb != null) {
      a(paramSharedPreferences, localb.a);
    }
  }
  
  private Boolean m()
  {
    b localb = l();
    if (localb != null) {
      return Boolean.valueOf(localb.b);
    }
    return null;
  }
  
  public boolean a()
  {
    return this.j;
  }
  
  public String b()
  {
    Object localObject1 = this.m;
    Object localObject2 = localObject1;
    if (localObject1 == null)
    {
      localObject1 = i.a(this.k);
      b((SharedPreferences)localObject1);
      localObject2 = ((SharedPreferences)localObject1).getString("crashlytics.installation.id", null);
      if (localObject2 == null) {
        localObject2 = a((SharedPreferences)localObject1);
      }
    }
    return (String)localObject2;
  }
  
  public String c()
  {
    return this.l;
  }
  
  public String d()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(e());
    localStringBuilder.append("/");
    localStringBuilder.append(f());
    return localStringBuilder.toString();
  }
  
  public String e()
  {
    return b(Build.VERSION.RELEASE);
  }
  
  public String f()
  {
    return b(Build.VERSION.INCREMENTAL);
  }
  
  public String g()
  {
    return String.format(Locale.US, "%s/%s", new Object[] { b(Build.MANUFACTURER), b(Build.MODEL) });
  }
  
  public Map<a, String> h()
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator1 = this.n.iterator();
    while (localIterator1.hasNext())
    {
      Object localObject = (h)localIterator1.next();
      if ((localObject instanceof m))
      {
        Iterator localIterator2 = ((m)localObject).getDeviceIdentifiers().entrySet().iterator();
        while (localIterator2.hasNext())
        {
          localObject = (Map.Entry)localIterator2.next();
          a(localHashMap, (a)((Map.Entry)localObject).getKey(), (String)((Map.Entry)localObject).getValue());
        }
      }
    }
    return Collections.unmodifiableMap(localHashMap);
  }
  
  public String i()
  {
    return this.h.a(this.k);
  }
  
  public Boolean j()
  {
    Boolean localBoolean;
    if (k()) {
      localBoolean = m();
    } else {
      localBoolean = null;
    }
    return localBoolean;
  }
  
  protected boolean k()
  {
    boolean bool;
    if ((this.i) && (!this.d.b(this.k))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  b l()
  {
    try
    {
      if (!this.c)
      {
        this.b = this.a.a();
        this.c = true;
      }
      b localb = this.b;
      return localb;
    }
    finally {}
  }
  
  public static enum a
  {
    public final int h;
    
    private a(int paramInt)
    {
      this.h = paramInt;
    }
  }
}


/* Location:              ~/io/fabric/sdk/android/services/b/r.class
 *
 * Reversed by:           J
 */