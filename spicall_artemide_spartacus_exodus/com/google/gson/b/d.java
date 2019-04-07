package com.google.gson.b;

import com.google.gson.a.e;
import com.google.gson.b;
import com.google.gson.c;
import com.google.gson.f;
import com.google.gson.s;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.t;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class d
  implements t, Cloneable
{
  public static final d a = new d();
  private double b = -1.0D;
  private int c = 136;
  private boolean d = true;
  private boolean e;
  private List<b> f = Collections.emptyList();
  private List<b> g = Collections.emptyList();
  
  private boolean a(com.google.gson.a.d paramd)
  {
    return (paramd == null) || (paramd.a() <= this.b);
  }
  
  private boolean a(com.google.gson.a.d paramd, e parame)
  {
    boolean bool;
    if ((a(paramd)) && (a(parame))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean a(e parame)
  {
    return (parame == null) || (parame.a() > this.b);
  }
  
  private boolean a(Class<?> paramClass)
  {
    boolean bool;
    if ((!Enum.class.isAssignableFrom(paramClass)) && ((paramClass.isAnonymousClass()) || (paramClass.isLocalClass()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean b(Class<?> paramClass)
  {
    boolean bool;
    if ((paramClass.isMemberClass()) && (!c(paramClass))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean c(Class<?> paramClass)
  {
    boolean bool;
    if ((paramClass.getModifiers() & 0x8) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected d a()
  {
    try
    {
      d locald = (d)super.clone();
      return locald;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      throw new AssertionError(localCloneNotSupportedException);
    }
  }
  
  public d a(double paramDouble)
  {
    d locald = a();
    locald.b = paramDouble;
    return locald;
  }
  
  public <T> s<T> a(final f paramf, final com.google.gson.c.a<T> parama)
  {
    Class localClass = parama.a();
    final boolean bool1 = a(localClass, true);
    final boolean bool2 = a(localClass, false);
    if ((!bool1) && (!bool2)) {
      return null;
    }
    new s()
    {
      private s<T> f;
      
      private s<T> b()
      {
        s locals = this.f;
        if (locals == null)
        {
          locals = paramf.a(d.this, parama);
          this.f = locals;
        }
        return locals;
      }
      
      public void a(JsonWriter paramAnonymousJsonWriter, T paramAnonymousT)
      {
        if (bool1)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        b().a(paramAnonymousJsonWriter, paramAnonymousT);
      }
      
      public T b(JsonReader paramAnonymousJsonReader)
      {
        if (bool2)
        {
          paramAnonymousJsonReader.skipValue();
          return null;
        }
        return (T)b().b(paramAnonymousJsonReader);
      }
    };
  }
  
  public boolean a(Class<?> paramClass, boolean paramBoolean)
  {
    if ((this.b != -1.0D) && (!a((com.google.gson.a.d)paramClass.getAnnotation(com.google.gson.a.d.class), (e)paramClass.getAnnotation(e.class)))) {
      return true;
    }
    if ((!this.d) && (b(paramClass))) {
      return true;
    }
    if (a(paramClass)) {
      return true;
    }
    if (paramBoolean) {
      localObject = this.f;
    } else {
      localObject = this.g;
    }
    Object localObject = ((List)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      if (((b)((Iterator)localObject).next()).a(paramClass)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean a(Field paramField, boolean paramBoolean)
  {
    if ((this.c & paramField.getModifiers()) != 0) {
      return true;
    }
    if ((this.b != -1.0D) && (!a((com.google.gson.a.d)paramField.getAnnotation(com.google.gson.a.d.class), (e)paramField.getAnnotation(e.class)))) {
      return true;
    }
    if (paramField.isSynthetic()) {
      return true;
    }
    Object localObject;
    if (this.e)
    {
      localObject = (com.google.gson.a.a)paramField.getAnnotation(com.google.gson.a.a.class);
      if ((localObject == null) || (paramBoolean ? !((com.google.gson.a.a)localObject).a() : !((com.google.gson.a.a)localObject).b())) {
        return true;
      }
    }
    if ((!this.d) && (b(paramField.getType()))) {
      return true;
    }
    if (a(paramField.getType())) {
      return true;
    }
    if (paramBoolean) {
      localObject = this.f;
    } else {
      localObject = this.g;
    }
    if (!((List)localObject).isEmpty())
    {
      paramField = new c(paramField);
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        if (((b)((Iterator)localObject).next()).a(paramField)) {
          return true;
        }
      }
    }
    return false;
  }
}


/* Location:              ~/com/google/gson/b/d.class
 *
 * Reversed by:           J
 */