package io.fabric.sdk.android.services.b;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import io.fabric.sdk.android.k;

class c
{
  private final Context a;
  private final io.fabric.sdk.android.services.d.c b;
  
  public c(Context paramContext)
  {
    this.a = paramContext.getApplicationContext();
    this.b = new io.fabric.sdk.android.services.d.d(paramContext, "TwitterAdvertisingInfoPreferences");
  }
  
  private void a(final b paramb)
  {
    new Thread(new h()
    {
      public void onRun()
      {
        b localb = c.a(c.this);
        if (!paramb.equals(localb))
        {
          io.fabric.sdk.android.c.g().a("Fabric", "Asychronously getting Advertising Info and storing it to preferences");
          c.a(c.this, localb);
        }
      }
    }).start();
  }
  
  @SuppressLint({"CommitPrefEdits"})
  private void b(b paramb)
  {
    if (c(paramb))
    {
      io.fabric.sdk.android.services.d.c localc = this.b;
      localc.a(localc.b().putString("advertising_id", paramb.a).putBoolean("limit_ad_tracking_enabled", paramb.b));
    }
    else
    {
      paramb = this.b;
      paramb.a(paramb.b().remove("advertising_id").remove("limit_ad_tracking_enabled"));
    }
  }
  
  private boolean c(b paramb)
  {
    boolean bool;
    if ((paramb != null) && (!TextUtils.isEmpty(paramb.a))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private b e()
  {
    b localb = c().a();
    if (!c(localb))
    {
      localb = d().a();
      if (!c(localb)) {
        io.fabric.sdk.android.c.g().a("Fabric", "AdvertisingInfo not present");
      } else {
        io.fabric.sdk.android.c.g().a("Fabric", "Using AdvertisingInfo from Service Provider");
      }
    }
    else
    {
      io.fabric.sdk.android.c.g().a("Fabric", "Using AdvertisingInfo from Reflection Provider");
    }
    return localb;
  }
  
  public b a()
  {
    b localb = b();
    if (c(localb))
    {
      io.fabric.sdk.android.c.g().a("Fabric", "Using AdvertisingInfo from Preference Store");
      a(localb);
      return localb;
    }
    localb = e();
    b(localb);
    return localb;
  }
  
  protected b b()
  {
    return new b(this.b.a().getString("advertising_id", ""), this.b.a().getBoolean("limit_ad_tracking_enabled", false));
  }
  
  public f c()
  {
    return new d(this.a);
  }
  
  public f d()
  {
    return new e(this.a);
  }
}


/* Location:              ~/io/fabric/sdk/android/services/b/c.class
 *
 * Reversed by:           J
 */