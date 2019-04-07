package com.google.gson.b.a;

import com.google.gson.a.b;
import com.google.gson.b.c;
import com.google.gson.b.h;
import com.google.gson.c.a;
import com.google.gson.f;
import com.google.gson.k;
import com.google.gson.q;
import com.google.gson.s;
import com.google.gson.t;

public final class d
  implements t
{
  private final c a;
  
  public d(c paramc)
  {
    this.a = paramc;
  }
  
  s<?> a(c paramc, f paramf, a<?> parama, b paramb)
  {
    Object localObject = paramc.a(a.b(paramb.a())).a();
    if ((localObject instanceof s))
    {
      paramc = (s)localObject;
    }
    else if ((localObject instanceof t))
    {
      paramc = ((t)localObject).a(paramf, parama);
    }
    else
    {
      boolean bool = localObject instanceof q;
      if ((!bool) && (!(localObject instanceof k)))
      {
        paramc = new StringBuilder();
        paramc.append("Invalid attempt to bind an instance of ");
        paramc.append(localObject.getClass().getName());
        paramc.append(" as a @JsonAdapter for ");
        paramc.append(parama.toString());
        paramc.append(". @JsonAdapter value must be a TypeAdapter, TypeAdapterFactory,");
        paramc.append(" JsonSerializer or JsonDeserializer.");
        throw new IllegalArgumentException(paramc.toString());
      }
      k localk = null;
      if (bool) {
        paramc = (q)localObject;
      } else {
        paramc = null;
      }
      if ((localObject instanceof k)) {
        localk = (k)localObject;
      }
      paramc = new l(paramc, localk, paramf, parama, null);
    }
    paramf = paramc;
    if (paramc != null)
    {
      paramf = paramc;
      if (paramb.b()) {
        paramf = paramc.a();
      }
    }
    return paramf;
  }
  
  public <T> s<T> a(f paramf, a<T> parama)
  {
    b localb = (b)parama.a().getAnnotation(b.class);
    if (localb == null) {
      return null;
    }
    return a(this.a, paramf, parama, localb);
  }
}


/* Location:              ~/com/google/gson/b/a/d.class
 *
 * Reversed by:           J
 */