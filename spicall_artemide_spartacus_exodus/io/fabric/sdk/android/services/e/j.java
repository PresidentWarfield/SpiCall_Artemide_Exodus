package io.fabric.sdk.android.services.e;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import io.fabric.sdk.android.h;
import io.fabric.sdk.android.services.b.i;
import io.fabric.sdk.android.services.b.q;
import io.fabric.sdk.android.services.d.d;
import org.json.JSONObject;

class j
  implements s
{
  private final w a;
  private final v b;
  private final io.fabric.sdk.android.services.b.k c;
  private final g d;
  private final x e;
  private final h f;
  private final io.fabric.sdk.android.services.d.c g;
  
  public j(h paramh, w paramw, io.fabric.sdk.android.services.b.k paramk, v paramv, g paramg, x paramx)
  {
    this.f = paramh;
    this.a = paramw;
    this.c = paramk;
    this.b = paramv;
    this.d = paramg;
    this.e = paramx;
    this.g = new d(this.f);
  }
  
  private void a(JSONObject paramJSONObject, String paramString)
  {
    io.fabric.sdk.android.k localk = io.fabric.sdk.android.c.g();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(paramJSONObject.toString());
    localk.a("Fabric", localStringBuilder.toString());
  }
  
  private t b(r paramr)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = localObject1;
    try
    {
      if (!r.b.equals(paramr))
      {
        JSONObject localJSONObject = this.d.a();
        if (localJSONObject != null)
        {
          localObject3 = this.b.a(this.c, localJSONObject);
          if (localObject3 != null)
          {
            a(localJSONObject, "Loaded cached settings: ");
            long l = this.c.a();
            if ((!r.c.equals(paramr)) && (((t)localObject3).a(l)))
            {
              io.fabric.sdk.android.c.g().a("Fabric", "Cached settings have expired.");
              localObject3 = localObject1;
              break label188;
            }
            try
            {
              io.fabric.sdk.android.c.g().a("Fabric", "Returning cached settings.");
            }
            catch (Exception paramr)
            {
              break label175;
            }
          }
          else
          {
            io.fabric.sdk.android.c.g().e("Fabric", "Failed to transform cached settings data.", null);
            localObject3 = localObject1;
            break label188;
          }
        }
        else
        {
          io.fabric.sdk.android.c.g().a("Fabric", "No cached settings data found.");
          localObject3 = localObject1;
        }
      }
    }
    catch (Exception paramr)
    {
      localObject3 = localObject2;
      label175:
      io.fabric.sdk.android.c.g().e("Fabric", "Failed to get cached settings", paramr);
    }
    label188:
    return (t)localObject3;
  }
  
  public t a()
  {
    return a(r.a);
  }
  
  public t a(r paramr)
  {
    boolean bool = new q().c(this.f.getContext());
    Object localObject1 = null;
    JSONObject localJSONObject = null;
    if (!bool)
    {
      io.fabric.sdk.android.c.g().a("Fabric", "Not fetching settings, because data collection is disabled by Firebase.");
      return null;
    }
    Object localObject2 = localJSONObject;
    Object localObject3 = localObject1;
    try
    {
      if (!io.fabric.sdk.android.c.h())
      {
        localObject2 = localJSONObject;
        localObject3 = localObject1;
        if (!d())
        {
          localObject3 = localObject1;
          localObject2 = b(paramr);
        }
      }
      localObject3 = localObject2;
      if (localObject2 == null)
      {
        localObject3 = localObject2;
        localJSONObject = this.e.a(this.a);
        localObject3 = localObject2;
        if (localJSONObject != null)
        {
          localObject3 = localObject2;
          paramr = this.b.a(this.c, localJSONObject);
          localObject3 = paramr;
          this.d.a(paramr.g, localJSONObject);
          localObject3 = paramr;
          a(localJSONObject, "Loaded settings: ");
          localObject3 = paramr;
          a(b());
          localObject3 = paramr;
        }
      }
      paramr = (r)localObject3;
      if (localObject3 == null) {
        paramr = b(r.c);
      }
    }
    catch (Exception paramr)
    {
      io.fabric.sdk.android.c.g().e("Fabric", "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", paramr);
      paramr = (r)localObject3;
    }
    return paramr;
  }
  
  @SuppressLint({"CommitPrefEdits"})
  boolean a(String paramString)
  {
    SharedPreferences.Editor localEditor = this.g.b();
    localEditor.putString("existing_instance_identifier", paramString);
    return this.g.a(localEditor);
  }
  
  String b()
  {
    return i.a(new String[] { i.m(this.f.getContext()) });
  }
  
  String c()
  {
    return this.g.a().getString("existing_instance_identifier", "");
  }
  
  boolean d()
  {
    return c().equals(b()) ^ true;
  }
}


/* Location:              ~/io/fabric/sdk/android/services/e/j.class
 *
 * Reversed by:           J
 */