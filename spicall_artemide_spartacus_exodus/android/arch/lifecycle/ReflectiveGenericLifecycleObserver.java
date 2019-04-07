package android.arch.lifecycle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class ReflectiveGenericLifecycleObserver
  implements a
{
  static final Map<Class, a> a = new HashMap();
  private final Object b;
  private final a c;
  
  ReflectiveGenericLifecycleObserver(Object paramObject)
  {
    this.b = paramObject;
    this.c = a(this.b.getClass());
  }
  
  private static a a(Class paramClass)
  {
    a locala = (a)a.get(paramClass);
    if (locala != null) {
      return locala;
    }
    return b(paramClass);
  }
  
  private void a(a parama, c paramc, b.a parama1)
  {
    a((List)parama.a.get(parama1), paramc, parama1);
    a((List)parama.a.get(b.a.ON_ANY), paramc, parama1);
  }
  
  private void a(b paramb, c paramc, b.a parama)
  {
    try
    {
      switch (paramb.a)
      {
      default: 
        break;
      case 2: 
        paramb.b.invoke(this.b, new Object[] { paramc, parama });
        break;
      case 1: 
        paramb.b.invoke(this.b, new Object[] { paramc });
        break;
      case 0: 
        paramb.b.invoke(this.b, new Object[0]);
      }
      return;
    }
    catch (IllegalAccessException paramb)
    {
      throw new RuntimeException(paramb);
    }
    catch (InvocationTargetException paramb)
    {
      throw new RuntimeException("Failed to call observer method", paramb.getCause());
    }
  }
  
  private void a(List<b> paramList, c paramc, b.a parama)
  {
    if (paramList != null) {
      for (int i = paramList.size() - 1; i >= 0; i--) {
        a((b)paramList.get(i), paramc, parama);
      }
    }
  }
  
  private static void a(Map<b, b.a> paramMap, b paramb, b.a parama, Class paramClass)
  {
    b.a locala = (b.a)paramMap.get(paramb);
    if ((locala != null) && (parama != locala))
    {
      paramMap = paramb.b;
      paramb = new StringBuilder();
      paramb.append("Method ");
      paramb.append(paramMap.getName());
      paramb.append(" in ");
      paramb.append(paramClass.getName());
      paramb.append(" already declared with different @OnLifecycleEvent value: previous");
      paramb.append(" value ");
      paramb.append(locala);
      paramb.append(", new value ");
      paramb.append(parama);
      throw new IllegalArgumentException(paramb.toString());
    }
    if (locala == null) {
      paramMap.put(paramb, parama);
    }
  }
  
  private static a b(Class paramClass)
  {
    Object localObject1 = paramClass.getSuperclass();
    Object localObject2 = new HashMap();
    if (localObject1 != null)
    {
      localObject1 = a((Class)localObject1);
      if (localObject1 != null) {
        ((Map)localObject2).putAll(((a)localObject1).b);
      }
    }
    localObject1 = paramClass.getDeclaredMethods();
    Class[] arrayOfClass = paramClass.getInterfaces();
    int i = arrayOfClass.length;
    Object localObject3;
    Object localObject4;
    for (int j = 0; j < i; j++)
    {
      localObject3 = a(arrayOfClass[j]).b.entrySet().iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject4 = (Map.Entry)((Iterator)localObject3).next();
        a((Map)localObject2, (b)((Map.Entry)localObject4).getKey(), (b.a)((Map.Entry)localObject4).getValue(), paramClass);
      }
    }
    int k = localObject1.length;
    i = 0;
    while (i < k)
    {
      arrayOfClass = localObject1[i];
      localObject4 = (f)arrayOfClass.getAnnotation(f.class);
      if (localObject4 != null)
      {
        localObject3 = arrayOfClass.getParameterTypes();
        if (localObject3.length > 0)
        {
          if (localObject3[0].isAssignableFrom(c.class)) {
            j = 1;
          } else {
            throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
          }
        }
        else {
          j = 0;
        }
        localObject4 = ((f)localObject4).a();
        if (localObject3.length > 1) {
          if (localObject3[1].isAssignableFrom(b.a.class))
          {
            if (localObject4 == b.a.ON_ANY) {
              j = 2;
            } else {
              throw new IllegalArgumentException("Second arg is supported only for ON_ANY value");
            }
          }
          else {
            throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
          }
        }
        if (localObject3.length <= 2) {
          a((Map)localObject2, new b(j, arrayOfClass), (b.a)localObject4, paramClass);
        }
      }
      else
      {
        i++;
        continue;
      }
      throw new IllegalArgumentException("cannot have more than 2 params");
    }
    localObject2 = new a((Map)localObject2);
    a.put(paramClass, localObject2);
    return (a)localObject2;
  }
  
  public void a(c paramc, b.a parama)
  {
    a(this.c, paramc, parama);
  }
  
  static class a
  {
    final Map<b.a, List<ReflectiveGenericLifecycleObserver.b>> a;
    final Map<ReflectiveGenericLifecycleObserver.b, b.a> b;
    
    a(Map<ReflectiveGenericLifecycleObserver.b, b.a> paramMap)
    {
      this.b = paramMap;
      this.a = new HashMap();
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        b.a locala = (b.a)localEntry.getValue();
        List localList = (List)this.a.get(locala);
        paramMap = localList;
        if (localList == null)
        {
          paramMap = new ArrayList();
          this.a.put(locala, paramMap);
        }
        paramMap.add(localEntry.getKey());
      }
    }
  }
  
  static class b
  {
    final int a;
    final Method b;
    
    b(int paramInt, Method paramMethod)
    {
      this.a = paramInt;
      this.b = paramMethod;
      this.b.setAccessible(true);
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool = true;
      if (this == paramObject) {
        return true;
      }
      if ((paramObject != null) && (getClass() == paramObject.getClass()))
      {
        paramObject = (b)paramObject;
        if ((this.a != ((b)paramObject).a) || (!this.b.getName().equals(((b)paramObject).b.getName()))) {
          bool = false;
        }
        return bool;
      }
      return false;
    }
    
    public int hashCode()
    {
      return this.a * 31 + this.b.getName().hashCode();
    }
  }
}


/* Location:              ~/android/arch/lifecycle/ReflectiveGenericLifecycleObserver.class
 *
 * Reversed by:           J
 */