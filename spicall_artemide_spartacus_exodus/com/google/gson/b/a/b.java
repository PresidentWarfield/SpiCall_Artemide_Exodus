package com.google.gson.b.a;

import com.google.gson.b.c;
import com.google.gson.b.h;
import com.google.gson.c.a;
import com.google.gson.f;
import com.google.gson.s;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.t;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

public final class b
  implements t
{
  private final c a;
  
  public b(c paramc)
  {
    this.a = paramc;
  }
  
  public <T> s<T> a(f paramf, a<T> parama)
  {
    Type localType = parama.b();
    Object localObject = parama.a();
    if (!Collection.class.isAssignableFrom((Class)localObject)) {
      return null;
    }
    localObject = com.google.gson.b.b.a(localType, (Class)localObject);
    return new a(paramf, (Type)localObject, paramf.a(a.a((Type)localObject)), this.a.a(parama));
  }
  
  private static final class a<E>
    extends s<Collection<E>>
  {
    private final s<E> a;
    private final h<? extends Collection<E>> b;
    
    public a(f paramf, Type paramType, s<E> params, h<? extends Collection<E>> paramh)
    {
      this.a = new m(paramf, params, paramType);
      this.b = paramh;
    }
    
    public Collection<E> a(JsonReader paramJsonReader)
    {
      if (paramJsonReader.peek() == JsonToken.NULL)
      {
        paramJsonReader.nextNull();
        return null;
      }
      Collection localCollection = (Collection)this.b.a();
      paramJsonReader.beginArray();
      while (paramJsonReader.hasNext()) {
        localCollection.add(this.a.b(paramJsonReader));
      }
      paramJsonReader.endArray();
      return localCollection;
    }
    
    public void a(JsonWriter paramJsonWriter, Collection<E> paramCollection)
    {
      if (paramCollection == null)
      {
        paramJsonWriter.nullValue();
        return;
      }
      paramJsonWriter.beginArray();
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext())
      {
        paramCollection = localIterator.next();
        this.a.a(paramJsonWriter, paramCollection);
      }
      paramJsonWriter.endArray();
    }
  }
}


/* Location:              ~/com/google/gson/b/a/b.class
 *
 * Reversed by:           J
 */