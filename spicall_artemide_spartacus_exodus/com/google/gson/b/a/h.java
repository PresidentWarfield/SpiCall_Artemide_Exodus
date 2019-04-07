package com.google.gson.b.a;

import com.google.gson.b.g;
import com.google.gson.c.a;
import com.google.gson.f;
import com.google.gson.s;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.t;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class h
  extends s<Object>
{
  public static final t a = new t()
  {
    public <T> s<T> a(f paramAnonymousf, a<T> paramAnonymousa)
    {
      if (paramAnonymousa.a() == Object.class) {
        return new h(paramAnonymousf);
      }
      return null;
    }
  };
  private final f b;
  
  h(f paramf)
  {
    this.b = paramf;
  }
  
  public void a(JsonWriter paramJsonWriter, Object paramObject)
  {
    if (paramObject == null)
    {
      paramJsonWriter.nullValue();
      return;
    }
    s locals = this.b.a(paramObject.getClass());
    if ((locals instanceof h))
    {
      paramJsonWriter.beginObject();
      paramJsonWriter.endObject();
      return;
    }
    locals.a(paramJsonWriter, paramObject);
  }
  
  public Object b(JsonReader paramJsonReader)
  {
    Object localObject = paramJsonReader.peek();
    switch (2.a[localObject.ordinal()])
    {
    default: 
      throw new IllegalStateException();
    case 6: 
      paramJsonReader.nextNull();
      return null;
    case 5: 
      return Boolean.valueOf(paramJsonReader.nextBoolean());
    case 4: 
      return Double.valueOf(paramJsonReader.nextDouble());
    case 3: 
      return paramJsonReader.nextString();
    case 2: 
      localObject = new g();
      paramJsonReader.beginObject();
      while (paramJsonReader.hasNext()) {
        ((Map)localObject).put(paramJsonReader.nextName(), b(paramJsonReader));
      }
      paramJsonReader.endObject();
      return localObject;
    }
    localObject = new ArrayList();
    paramJsonReader.beginArray();
    while (paramJsonReader.hasNext()) {
      ((List)localObject).add(b(paramJsonReader));
    }
    paramJsonReader.endArray();
    return localObject;
  }
}


/* Location:              ~/com/google/gson/b/a/h.class
 *
 * Reversed by:           J
 */