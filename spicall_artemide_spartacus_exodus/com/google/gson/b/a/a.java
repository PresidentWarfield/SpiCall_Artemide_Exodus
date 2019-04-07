package com.google.gson.b.a;

import com.google.gson.b.b;
import com.google.gson.f;
import com.google.gson.s;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.t;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;

public final class a<E>
  extends s<Object>
{
  public static final t a = new t()
  {
    public <T> s<T> a(f paramAnonymousf, com.google.gson.c.a<T> paramAnonymousa)
    {
      paramAnonymousa = paramAnonymousa.b();
      if ((!(paramAnonymousa instanceof GenericArrayType)) && ((!(paramAnonymousa instanceof Class)) || (!((Class)paramAnonymousa).isArray()))) {
        return null;
      }
      paramAnonymousa = b.g(paramAnonymousa);
      return new a(paramAnonymousf, paramAnonymousf.a(com.google.gson.c.a.a(paramAnonymousa)), b.e(paramAnonymousa));
    }
  };
  private final Class<E> b;
  private final s<E> c;
  
  public a(f paramf, s<E> params, Class<E> paramClass)
  {
    this.c = new m(paramf, params, paramClass);
    this.b = paramClass;
  }
  
  public void a(JsonWriter paramJsonWriter, Object paramObject)
  {
    if (paramObject == null)
    {
      paramJsonWriter.nullValue();
      return;
    }
    paramJsonWriter.beginArray();
    int i = 0;
    int j = Array.getLength(paramObject);
    while (i < j)
    {
      Object localObject = Array.get(paramObject, i);
      this.c.a(paramJsonWriter, localObject);
      i++;
    }
    paramJsonWriter.endArray();
  }
  
  public Object b(JsonReader paramJsonReader)
  {
    if (paramJsonReader.peek() == JsonToken.NULL)
    {
      paramJsonReader.nextNull();
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    paramJsonReader.beginArray();
    while (paramJsonReader.hasNext()) {
      localArrayList.add(this.c.b(paramJsonReader));
    }
    paramJsonReader.endArray();
    int i = localArrayList.size();
    paramJsonReader = Array.newInstance(this.b, i);
    for (int j = 0; j < i; j++) {
      Array.set(paramJsonReader, j, localArrayList.get(j));
    }
    return paramJsonReader;
  }
}


/* Location:              ~/com/google/gson/b/a/a.class
 *
 * Reversed by:           J
 */