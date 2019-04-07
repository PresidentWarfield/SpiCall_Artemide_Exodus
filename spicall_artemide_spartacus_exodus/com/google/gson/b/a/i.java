package com.google.gson.b.a;

import com.google.gson.JsonSyntaxException;
import com.google.gson.b.h;
import com.google.gson.c.a;
import com.google.gson.c.a<*>;
import com.google.gson.e;
import com.google.gson.f;
import com.google.gson.s;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.t;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class i
  implements t
{
  private final com.google.gson.b.c a;
  private final e b;
  private final com.google.gson.b.d c;
  private final d d;
  
  public i(com.google.gson.b.c paramc, e parame, com.google.gson.b.d paramd, d paramd1)
  {
    this.a = paramc;
    this.b = parame;
    this.c = paramd;
    this.d = paramd1;
  }
  
  private b a(final f paramf, final Field paramField, String paramString, final a<?> parama, boolean paramBoolean1, boolean paramBoolean2)
  {
    final boolean bool1 = com.google.gson.b.i.a(parama.a());
    Object localObject1 = (com.google.gson.a.b)paramField.getAnnotation(com.google.gson.a.b.class);
    if (localObject1 != null) {
      localObject1 = this.d.a(this.a, paramf, parama, (com.google.gson.a.b)localObject1);
    } else {
      localObject1 = null;
    }
    final boolean bool2;
    if (localObject1 != null) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    final Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = paramf.a(parama);
    }
    new b(paramString, paramBoolean1, paramBoolean2)
    {
      void a(JsonReader paramAnonymousJsonReader, Object paramAnonymousObject)
      {
        paramAnonymousJsonReader = localObject2.b(paramAnonymousJsonReader);
        if ((paramAnonymousJsonReader != null) || (!bool1)) {
          paramField.set(paramAnonymousObject, paramAnonymousJsonReader);
        }
      }
      
      void a(JsonWriter paramAnonymousJsonWriter, Object paramAnonymousObject)
      {
        Object localObject = paramField.get(paramAnonymousObject);
        if (bool2) {
          paramAnonymousObject = localObject2;
        } else {
          paramAnonymousObject = new m(paramf, localObject2, parama.b());
        }
        ((s)paramAnonymousObject).a(paramAnonymousJsonWriter, localObject);
      }
      
      public boolean a(Object paramAnonymousObject)
      {
        boolean bool1 = this.i;
        boolean bool2 = false;
        if (!bool1) {
          return false;
        }
        if (paramField.get(paramAnonymousObject) != paramAnonymousObject) {
          bool2 = true;
        }
        return bool2;
      }
    };
  }
  
  private List<String> a(Field paramField)
  {
    Object localObject = (com.google.gson.a.c)paramField.getAnnotation(com.google.gson.a.c.class);
    if (localObject == null) {
      return Collections.singletonList(this.b.a(paramField));
    }
    paramField = ((com.google.gson.a.c)localObject).a();
    String[] arrayOfString = ((com.google.gson.a.c)localObject).b();
    if (arrayOfString.length == 0) {
      return Collections.singletonList(paramField);
    }
    localObject = new ArrayList(arrayOfString.length + 1);
    ((List)localObject).add(paramField);
    int i = arrayOfString.length;
    for (int j = 0; j < i; j++) {
      ((List)localObject).add(arrayOfString[j]);
    }
    return (List<String>)localObject;
  }
  
  private Map<String, b> a(f paramf, a<?> parama, Class<?> paramClass)
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    if (paramClass.isInterface()) {
      return localLinkedHashMap;
    }
    Type localType1 = parama.b();
    Object localObject1 = paramClass;
    paramClass = parama;
    while (localObject1 != Object.class)
    {
      Field[] arrayOfField = ((Class)localObject1).getDeclaredFields();
      int i = arrayOfField.length;
      int j = 0;
      while (j < i)
      {
        Field localField = arrayOfField[j];
        boolean bool1 = a(localField, true);
        boolean bool2 = a(localField, false);
        if ((bool1) || (bool2))
        {
          localField.setAccessible(true);
          Type localType2 = com.google.gson.b.b.a(paramClass.b(), (Class)localObject1, localField.getGenericType());
          List localList = a(localField);
          int k = localList.size();
          parama = null;
          for (int m = 0; m < k; m++)
          {
            Object localObject2 = (String)localList.get(m);
            if (m != 0) {
              bool1 = false;
            }
            localObject2 = (b)localLinkedHashMap.put(localObject2, a(paramf, localField, (String)localObject2, a.a(localType2), bool1, bool2));
            if (parama == null) {
              parama = (a<?>)localObject2;
            }
          }
          if (parama != null) {}
        }
        else
        {
          j++;
          continue;
        }
        paramf = new StringBuilder();
        paramf.append(localType1);
        paramf.append(" declares multiple JSON fields named ");
        paramf.append(parama.h);
        throw new IllegalArgumentException(paramf.toString());
      }
      paramClass = a.a(com.google.gson.b.b.a(paramClass.b(), (Class)localObject1, ((Class)localObject1).getGenericSuperclass()));
      localObject1 = paramClass.a();
    }
    return localLinkedHashMap;
  }
  
  static boolean a(Field paramField, boolean paramBoolean, com.google.gson.b.d paramd)
  {
    if ((!paramd.a(paramField.getType(), paramBoolean)) && (!paramd.a(paramField, paramBoolean))) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    }
    return paramBoolean;
  }
  
  public <T> s<T> a(f paramf, a<T> parama)
  {
    Class localClass = parama.a();
    if (!Object.class.isAssignableFrom(localClass)) {
      return null;
    }
    return new a(this.a.a(parama), a(paramf, parama, localClass));
  }
  
  public boolean a(Field paramField, boolean paramBoolean)
  {
    return a(paramField, paramBoolean, this.c);
  }
  
  public static final class a<T>
    extends s<T>
  {
    private final h<T> a;
    private final Map<String, i.b> b;
    
    a(h<T> paramh, Map<String, i.b> paramMap)
    {
      this.a = paramh;
      this.b = paramMap;
    }
    
    public void a(JsonWriter paramJsonWriter, T paramT)
    {
      if (paramT == null)
      {
        paramJsonWriter.nullValue();
        return;
      }
      paramJsonWriter.beginObject();
      try
      {
        Iterator localIterator = this.b.values().iterator();
        while (localIterator.hasNext())
        {
          i.b localb = (i.b)localIterator.next();
          if (localb.a(paramT))
          {
            paramJsonWriter.name(localb.h);
            localb.a(paramJsonWriter, paramT);
          }
        }
        paramJsonWriter.endObject();
        return;
      }
      catch (IllegalAccessException paramJsonWriter)
      {
        throw new AssertionError(paramJsonWriter);
      }
    }
    
    public T b(JsonReader paramJsonReader)
    {
      if (paramJsonReader.peek() == JsonToken.NULL)
      {
        paramJsonReader.nextNull();
        return null;
      }
      Object localObject1 = this.a.a();
      try
      {
        paramJsonReader.beginObject();
        while (paramJsonReader.hasNext())
        {
          Object localObject2 = paramJsonReader.nextName();
          localObject2 = (i.b)this.b.get(localObject2);
          if ((localObject2 != null) && (((i.b)localObject2).j)) {
            ((i.b)localObject2).a(paramJsonReader, localObject1);
          } else {
            paramJsonReader.skipValue();
          }
        }
        paramJsonReader.endObject();
        return (T)localObject1;
      }
      catch (IllegalAccessException paramJsonReader)
      {
        throw new AssertionError(paramJsonReader);
      }
      catch (IllegalStateException paramJsonReader)
      {
        throw new JsonSyntaxException(paramJsonReader);
      }
    }
  }
  
  static abstract class b
  {
    final String h;
    final boolean i;
    final boolean j;
    
    protected b(String paramString, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.h = paramString;
      this.i = paramBoolean1;
      this.j = paramBoolean2;
    }
    
    abstract void a(JsonReader paramJsonReader, Object paramObject);
    
    abstract void a(JsonWriter paramJsonWriter, Object paramObject);
    
    abstract boolean a(Object paramObject);
  }
}


/* Location:              ~/com/google/gson/b/a/i.class
 *
 * Reversed by:           J
 */