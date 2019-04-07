package io.fabric.sdk.android.services.e;

import android.content.Context;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.h;
import io.fabric.sdk.android.services.b.g;
import io.fabric.sdk.android.services.b.v;
import io.fabric.sdk.android.services.network.d;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class q
{
  private final AtomicReference<t> a = new AtomicReference();
  private final CountDownLatch b = new CountDownLatch(1);
  private s c;
  private boolean d = false;
  
  public static q a()
  {
    return a.a();
  }
  
  private void a(t paramt)
  {
    this.a.set(paramt);
    this.b.countDown();
  }
  
  public q a(h paramh, io.fabric.sdk.android.services.b.r paramr, d paramd, String paramString1, String paramString2, String paramString3)
  {
    try
    {
      boolean bool = this.d;
      if (bool) {
        return this;
      }
      if (this.c == null)
      {
        Object localObject1 = paramh.getContext();
        Object localObject2 = paramr.c();
        Object localObject3 = new io/fabric/sdk/android/services/b/g;
        ((g)localObject3).<init>();
        String str1 = ((g)localObject3).a((Context)localObject1);
        Object localObject4 = paramr.i();
        localObject3 = new io/fabric/sdk/android/services/b/v;
        ((v)localObject3).<init>();
        k localk = new io/fabric/sdk/android/services/e/k;
        localk.<init>();
        i locali = new io/fabric/sdk/android/services/e/i;
        locali.<init>(paramh);
        String str2 = io.fabric.sdk.android.services.b.i.k((Context)localObject1);
        String str3 = String.format(Locale.US, "https://settings.crashlytics.com/spi/v2/platforms/android/apps/%s/settings", new Object[] { localObject2 });
        localObject2 = new io/fabric/sdk/android/services/e/l;
        ((l)localObject2).<init>(paramh, paramString3, str3, paramd);
        str3 = paramr.g();
        paramString3 = paramr.f();
        paramd = paramr.e();
        paramr = paramr.b();
        localObject1 = io.fabric.sdk.android.services.b.i.a(new String[] { io.fabric.sdk.android.services.b.i.m((Context)localObject1) });
        int i = io.fabric.sdk.android.services.b.l.a((String)localObject4).a();
        localObject4 = new io/fabric/sdk/android/services/e/w;
        ((w)localObject4).<init>(str1, str3, paramString3, paramd, paramr, (String)localObject1, paramString2, paramString1, i, str2);
        paramr = new io/fabric/sdk/android/services/e/j;
        paramr.<init>(paramh, (w)localObject4, (io.fabric.sdk.android.services.b.k)localObject3, localk, locali, (x)localObject2);
        this.c = paramr;
      }
      this.d = true;
      return this;
    }
    finally {}
  }
  
  public t b()
  {
    try
    {
      this.b.await();
      t localt = (t)this.a.get();
      return localt;
    }
    catch (InterruptedException localInterruptedException)
    {
      c.g().e("Fabric", "Interrupted while waiting for settings data.");
    }
    return null;
  }
  
  public boolean c()
  {
    try
    {
      t localt = this.c.a();
      a(localt);
      boolean bool;
      if (localt != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean d()
  {
    try
    {
      t localt = this.c.a(r.b);
      a(localt);
      if (localt == null) {
        c.g().e("Fabric", "Failed to force reload of settings from Crashlytics.", null);
      }
      boolean bool;
      if (localt != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    finally {}
  }
  
  static class a
  {
    private static final q a = new q(null);
  }
}


/* Location:              ~/io/fabric/sdk/android/services/e/q.class
 *
 * Reversed by:           J
 */