package com.google.gson.b.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.b.b;
import com.google.gson.b.c;
import com.google.gson.b.e;
import com.google.gson.b.h;
import com.google.gson.b.j;
import com.google.gson.c.a;
import com.google.gson.f;
import com.google.gson.l;
import com.google.gson.o;
import com.google.gson.s;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.t;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class g
  implements t
{
  final boolean a;
  private final c b;
  
  public g(c paramc, boolean paramBoolean)
  {
    this.b = paramc;
    this.a = paramBoolean;
  }
  
  private s<?> a(f paramf, Type paramType)
  {
    if ((paramType != Boolean.TYPE) && (paramType != Boolean.class)) {
      paramf = paramf.a(a.a(paramType));
    } else {
      paramf = n.f;
    }
    return paramf;
  }
  
  public <T> s<T> a(f paramf, a<T> parama)
  {
    Object localObject = parama.b();
    if (!Map.class.isAssignableFrom(parama.a())) {
      return null;
    }
    localObject = b.b((Type)localObject, b.e((Type)localObject));
    s locals1 = a(paramf, localObject[0]);
    s locals2 = paramf.a(a.a(localObject[1]));
    parama = this.b.a(parama);
    return new a(paramf, localObject[0], locals1, localObject[1], locals2, parama);
  }
  
  private final class a<K, V>
    extends s<Map<K, V>>
  {
    private final s<K> b;
    private final s<V> c;
    private final h<? extends Map<K, V>> d;
    
    public a(Type paramType1, s<K> params, Type paramType2, s<V> params1, h<? extends Map<K, V>> paramh)
    {
      this.b = new m(paramType1, paramType2, params);
      this.c = new m(paramType1, paramh, params1);
      h localh;
      this.d = localh;
    }
    
    private String a(l paraml)
    {
      if (paraml.i())
      {
        paraml = paraml.m();
        if (paraml.p()) {
          return String.valueOf(paraml.a());
        }
        if (paraml.o()) {
          return Boolean.toString(paraml.f());
        }
        if (paraml.q()) {
          return paraml.b();
        }
        throw new AssertionError();
      }
      if (paraml.j()) {
        return "null";
      }
      throw new AssertionError();
    }
    
    public Map<K, V> a(JsonReader paramJsonReader)
    {
      Object localObject = paramJsonReader.peek();
      if (localObject == JsonToken.NULL)
      {
        paramJsonReader.nextNull();
        return null;
      }
      Map localMap = (Map)this.d.a();
      if (localObject == JsonToken.BEGIN_ARRAY)
      {
        paramJsonReader.beginArray();
        while (paramJsonReader.hasNext())
        {
          paramJsonReader.beginArray();
          localObject = this.b.b(paramJsonReader);
          if (localMap.put(localObject, this.c.b(paramJsonReader)) == null)
          {
            paramJsonReader.endArray();
          }
          else
          {
            paramJsonReader = new StringBuilder();
            paramJsonReader.append("duplicate key: ");
            paramJsonReader.append(localObject);
            throw new JsonSyntaxException(paramJsonReader.toString());
          }
        }
        paramJsonReader.endArray();
      }
      else
      {
        paramJsonReader.beginObject();
        while (paramJsonReader.hasNext())
        {
          e.INSTANCE.promoteNameToValue(paramJsonReader);
          localObject = this.b.b(paramJsonReader);
          if (localMap.put(localObject, this.c.b(paramJsonReader)) != null)
          {
            paramJsonReader = new StringBuilder();
            paramJsonReader.append("duplicate key: ");
            paramJsonReader.append(localObject);
            throw new JsonSyntaxException(paramJsonReader.toString());
          }
        }
        paramJsonReader.endObject();
      }
      return localMap;
    }
    
    public void a(JsonWriter paramJsonWriter, Map<K, V> paramMap)
    {
      if (paramMap == null)
      {
        paramJsonWriter.nullValue();
        return;
      }
      if (!g.this.a)
      {
        paramJsonWriter.beginObject();
        localObject = paramMap.entrySet().iterator();
        while (((Iterator)localObject).hasNext())
        {
          paramMap = (Map.Entry)((Iterator)localObject).next();
          paramJsonWriter.name(String.valueOf(paramMap.getKey()));
          this.c.a(paramJsonWriter, paramMap.getValue());
        }
        paramJsonWriter.endObject();
        return;
      }
      ArrayList localArrayList = new ArrayList(paramMap.size());
      Object localObject = new ArrayList(paramMap.size());
      Iterator localIterator = paramMap.entrySet().iterator();
      int i = 0;
      int j = 0;
      int k = 0;
      int m;
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        paramMap = this.b.a(localEntry.getKey());
        localArrayList.add(paramMap);
        ((List)localObject).add(localEntry.getValue());
        if ((!paramMap.g()) && (!paramMap.h())) {
          m = 0;
        } else {
          m = 1;
        }
        k |= m;
      }
      if (k != 0)
      {
        paramJsonWriter.beginArray();
        m = localArrayList.size();
        for (k = j; k < m; k++)
        {
          paramJsonWriter.beginArray();
          j.a((l)localArrayList.get(k), paramJsonWriter);
          this.c.a(paramJsonWriter, ((List)localObject).get(k));
          paramJsonWriter.endArray();
        }
        paramJsonWriter.endArray();
      }
      else
      {
        paramJsonWriter.beginObject();
        m = localArrayList.size();
        for (k = i; k < m; k++)
        {
          paramJsonWriter.name(a((l)localArrayList.get(k)));
          this.c.a(paramJsonWriter, ((List)localObject).get(k));
        }
        paramJsonWriter.endObject();
      }
    }
  }
}


/* Location:              ~/com/google/gson/b/a/g.class
 *
 * Reversed by:           J
 */