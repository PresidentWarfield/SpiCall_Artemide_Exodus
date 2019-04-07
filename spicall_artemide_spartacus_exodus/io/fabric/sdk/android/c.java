package io.fabric.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import io.fabric.sdk.android.services.b.q;
import io.fabric.sdk.android.services.b.r;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import io.fabric.sdk.android.services.concurrency.d;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class c
{
  static volatile c a;
  static final k b = new b();
  final k c;
  final boolean d;
  private final Context e;
  private final Map<Class<? extends h>, h> f;
  private final ExecutorService g;
  private final Handler h;
  private final f<c> i;
  private final f<?> j;
  private final r k;
  private a l;
  private WeakReference<Activity> m;
  private AtomicBoolean n;
  
  c(Context paramContext, Map<Class<? extends h>, h> paramMap, io.fabric.sdk.android.services.concurrency.k paramk, Handler paramHandler, k paramk1, boolean paramBoolean, f paramf, r paramr, Activity paramActivity)
  {
    this.e = paramContext;
    this.f = paramMap;
    this.g = paramk;
    this.h = paramHandler;
    this.c = paramk1;
    this.d = paramBoolean;
    this.i = paramf;
    this.n = new AtomicBoolean(false);
    this.j = a(paramMap.size());
    this.k = paramr;
    a(paramActivity);
  }
  
  static c a()
  {
    if (a != null) {
      return a;
    }
    throw new IllegalStateException("Must Initialize Fabric before using singleton()");
  }
  
  public static c a(Context paramContext, h... paramVarArgs)
  {
    if (a == null) {
      try
      {
        if (a == null)
        {
          a locala = new io/fabric/sdk/android/c$a;
          locala.<init>(paramContext);
          c(locala.a(paramVarArgs).a());
        }
      }
      finally {}
    }
    return a;
  }
  
  public static <T extends h> T a(Class<T> paramClass)
  {
    return (h)a().f.get(paramClass);
  }
  
  private static void a(Map<Class<? extends h>, h> paramMap, Collection<? extends h> paramCollection)
  {
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext())
    {
      h localh = (h)paramCollection.next();
      paramMap.put(localh.getClass(), localh);
      if ((localh instanceof i)) {
        a(paramMap, ((i)localh).getKits());
      }
    }
  }
  
  private static Map<Class<? extends h>, h> b(Collection<? extends h> paramCollection)
  {
    HashMap localHashMap = new HashMap(paramCollection.size());
    a(localHashMap, paramCollection);
    return localHashMap;
  }
  
  private static void c(c paramc)
  {
    a = paramc;
    paramc.i();
  }
  
  private static Activity d(Context paramContext)
  {
    if ((paramContext instanceof Activity)) {
      return (Activity)paramContext;
    }
    return null;
  }
  
  public static k g()
  {
    if (a == null) {
      return b;
    }
    return a.c;
  }
  
  public static boolean h()
  {
    if (a == null) {
      return false;
    }
    return a.d;
  }
  
  private void i()
  {
    this.l = new a(this.e);
    this.l.a(new a.b()
    {
      public void onActivityCreated(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
      {
        c.this.a(paramAnonymousActivity);
      }
      
      public void onActivityResumed(Activity paramAnonymousActivity)
      {
        c.this.a(paramAnonymousActivity);
      }
      
      public void onActivityStarted(Activity paramAnonymousActivity)
      {
        c.this.a(paramAnonymousActivity);
      }
    });
    a(this.e);
  }
  
  public c a(Activity paramActivity)
  {
    this.m = new WeakReference(paramActivity);
    return this;
  }
  
  f<?> a(final int paramInt)
  {
    new f()
    {
      final CountDownLatch a = new CountDownLatch(paramInt);
      
      public void a(Exception paramAnonymousException)
      {
        c.b(c.this).a(paramAnonymousException);
      }
      
      public void a(Object paramAnonymousObject)
      {
        this.a.countDown();
        if (this.a.getCount() == 0L)
        {
          c.a(c.this).set(true);
          c.b(c.this).a(c.this);
        }
      }
    };
  }
  
  void a(Context paramContext)
  {
    Object localObject1 = b(paramContext);
    Object localObject2 = f();
    localObject1 = new l((Future)localObject1, (Collection)localObject2);
    localObject2 = new ArrayList((Collection)localObject2);
    Collections.sort((List)localObject2);
    ((l)localObject1).injectParameters(paramContext, this, f.d, this.k);
    Object localObject3 = ((List)localObject2).iterator();
    while (((Iterator)localObject3).hasNext()) {
      ((h)((Iterator)localObject3).next()).injectParameters(paramContext, this, this.j, this.k);
    }
    ((l)localObject1).initialize();
    if (g().a("Fabric", 3))
    {
      paramContext = new StringBuilder("Initializing ");
      paramContext.append(d());
      paramContext.append(" [Version: ");
      paramContext.append(c());
      paramContext.append("], with the following kits:\n");
    }
    else
    {
      paramContext = null;
    }
    localObject2 = ((List)localObject2).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (h)((Iterator)localObject2).next();
      ((h)localObject3).initializationTask.a(((l)localObject1).initializationTask);
      a(this.f, (h)localObject3);
      ((h)localObject3).initialize();
      if (paramContext != null)
      {
        paramContext.append(((h)localObject3).getIdentifier());
        paramContext.append(" [Version: ");
        paramContext.append(((h)localObject3).getVersion());
        paramContext.append("]\n");
      }
    }
    if (paramContext != null) {
      g().a("Fabric", paramContext.toString());
    }
  }
  
  void a(Map<Class<? extends h>, h> paramMap, h paramh)
  {
    Object localObject = paramh.dependsOnAnnotation;
    if (localObject != null)
    {
      Class[] arrayOfClass = ((d)localObject).a();
      int i1 = arrayOfClass.length;
      int i2 = 0;
      while (i2 < i1)
      {
        Class localClass = arrayOfClass[i2];
        if (localClass.isInterface())
        {
          localObject = paramMap.values().iterator();
          while (((Iterator)localObject).hasNext())
          {
            h localh = (h)((Iterator)localObject).next();
            if (localClass.isAssignableFrom(localh.getClass())) {
              paramh.initializationTask.a(localh.initializationTask);
            }
          }
        }
        if ((h)paramMap.get(localClass) != null)
        {
          paramh.initializationTask.a(((h)paramMap.get(localClass)).initializationTask);
          i2++;
        }
        else
        {
          throw new UnmetDependencyException("Referenced Kit was null, does the kit exist?");
        }
      }
    }
  }
  
  public Activity b()
  {
    WeakReference localWeakReference = this.m;
    if (localWeakReference != null) {
      return (Activity)localWeakReference.get();
    }
    return null;
  }
  
  Future<Map<String, j>> b(Context paramContext)
  {
    paramContext = new e(paramContext.getPackageCodePath());
    return e().submit(paramContext);
  }
  
  public String c()
  {
    return "1.4.4.27";
  }
  
  public String d()
  {
    return "io.fabric.sdk.android:fabric";
  }
  
  public ExecutorService e()
  {
    return this.g;
  }
  
  public Collection<h> f()
  {
    return this.f.values();
  }
  
  public static class a
  {
    private final Context a;
    private h[] b;
    private io.fabric.sdk.android.services.concurrency.k c;
    private Handler d;
    private k e;
    private boolean f;
    private String g;
    private String h;
    private f<c> i;
    
    public a(Context paramContext)
    {
      if (paramContext != null)
      {
        this.a = paramContext;
        return;
      }
      throw new IllegalArgumentException("Context must not be null.");
    }
    
    public a a(h... paramVarArgs)
    {
      if (this.b == null)
      {
        Object localObject = paramVarArgs;
        if (!new q().c(this.a))
        {
          ArrayList localArrayList = new ArrayList();
          int j = paramVarArgs.length;
          int k = 0;
          int n;
          for (int m = 0; k < j; m = n)
          {
            h localh = paramVarArgs[k];
            localObject = localh.getIdentifier();
            n = -1;
            int i1 = ((String)localObject).hashCode();
            if (i1 != 607220212)
            {
              if ((i1 == 1830452504) && (((String)localObject).equals("com.crashlytics.sdk.android:crashlytics"))) {
                n = 0;
              }
            }
            else if (((String)localObject).equals("com.crashlytics.sdk.android:answers")) {
              n = 1;
            }
            switch (n)
            {
            default: 
              n = m;
              if (m == 0)
              {
                c.g().d("Fabric", "Fabric will not initialize any kits when Firebase automatic data collection is disabled; to use Third-party kits with automatic data collection disabled, initialize these kits via non-Fabric means.");
                n = 1;
              }
              break;
            case 0: 
            case 1: 
              localArrayList.add(localh);
              n = m;
            }
            k++;
          }
          localObject = (h[])localArrayList.toArray(new h[0]);
        }
        this.b = ((h[])localObject);
        return this;
      }
      throw new IllegalStateException("Kits already set.");
    }
    
    public c a()
    {
      if (this.c == null) {
        this.c = io.fabric.sdk.android.services.concurrency.k.a();
      }
      if (this.d == null) {
        this.d = new Handler(Looper.getMainLooper());
      }
      if (this.e == null) {
        if (this.f) {
          this.e = new b(3);
        } else {
          this.e = new b();
        }
      }
      if (this.h == null) {
        this.h = this.a.getPackageName();
      }
      if (this.i == null) {
        this.i = f.d;
      }
      Object localObject = this.b;
      if (localObject == null) {
        localObject = new HashMap();
      } else {
        localObject = c.a(Arrays.asList((Object[])localObject));
      }
      Context localContext = this.a.getApplicationContext();
      r localr = new r(localContext, this.h, this.g, ((Map)localObject).values());
      return new c(localContext, (Map)localObject, this.c, this.d, this.e, this.f, this.i, localr, c.c(this.a));
    }
  }
}


/* Location:              ~/io/fabric/sdk/android/c.class
 *
 * Reversed by:           J
 */