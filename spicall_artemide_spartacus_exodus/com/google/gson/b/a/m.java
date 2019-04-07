package com.google.gson.b.a;

import com.google.gson.c.a;
import com.google.gson.f;
import com.google.gson.s;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class m<T>
  extends s<T>
{
  private final f a;
  private final s<T> b;
  private final Type c;
  
  m(f paramf, s<T> params, Type paramType)
  {
    this.a = paramf;
    this.b = params;
    this.c = paramType;
  }
  
  private Type a(Type paramType, Object paramObject)
  {
    Object localObject = paramType;
    if (paramObject != null) {
      if ((paramType != Object.class) && (!(paramType instanceof TypeVariable)))
      {
        localObject = paramType;
        if (!(paramType instanceof Class)) {}
      }
      else
      {
        localObject = paramObject.getClass();
      }
    }
    return (Type)localObject;
  }
  
  public void a(JsonWriter paramJsonWriter, T paramT)
  {
    Object localObject1 = this.b;
    Object localObject2 = a(this.c, paramT);
    if (localObject2 != this.c)
    {
      localObject1 = this.a.a(a.a((Type)localObject2));
      if ((localObject1 instanceof i.a))
      {
        localObject2 = this.b;
        if (!(localObject2 instanceof i.a)) {
          localObject1 = localObject2;
        }
      }
    }
    ((s)localObject1).a(paramJsonWriter, paramT);
  }
  
  public T b(JsonReader paramJsonReader)
  {
    return (T)this.b.b(paramJsonReader);
  }
}


/* Location:              ~/com/google/gson/b/a/m.class
 *
 * Reversed by:           J
 */