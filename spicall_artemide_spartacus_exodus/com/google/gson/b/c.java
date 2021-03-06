package com.google.gson.b;

import com.google.gson.JsonIOException;
import com.google.gson.c.a;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public final class c
{
  private final Map<Type, com.google.gson.h<?>> a;
  
  public c(Map<Type, com.google.gson.h<?>> paramMap)
  {
    this.a = paramMap;
  }
  
  private <T> h<T> a(final Class<? super T> paramClass)
  {
    try
    {
      paramClass = paramClass.getDeclaredConstructor(new Class[0]);
      if (!paramClass.isAccessible()) {
        paramClass.setAccessible(true);
      }
      paramClass = new h()
      {
        public T a()
        {
          try
          {
            Object localObject = paramClass.newInstance(null);
            return (T)localObject;
          }
          catch (IllegalAccessException localIllegalAccessException)
          {
            throw new AssertionError(localIllegalAccessException);
          }
          catch (InvocationTargetException localInvocationTargetException)
          {
            StringBuilder localStringBuilder1 = new StringBuilder();
            localStringBuilder1.append("Failed to invoke ");
            localStringBuilder1.append(paramClass);
            localStringBuilder1.append(" with no args");
            throw new RuntimeException(localStringBuilder1.toString(), localInvocationTargetException.getTargetException());
          }
          catch (InstantiationException localInstantiationException)
          {
            StringBuilder localStringBuilder2 = new StringBuilder();
            localStringBuilder2.append("Failed to invoke ");
            localStringBuilder2.append(paramClass);
            localStringBuilder2.append(" with no args");
            throw new RuntimeException(localStringBuilder2.toString(), localInstantiationException);
          }
        }
      };
      return paramClass;
    }
    catch (NoSuchMethodException paramClass) {}
    return null;
  }
  
  private <T> h<T> a(final Type paramType, Class<? super T> paramClass)
  {
    if (Collection.class.isAssignableFrom(paramClass))
    {
      if (SortedSet.class.isAssignableFrom(paramClass)) {
        new h()
        {
          public T a()
          {
            return new TreeSet();
          }
        };
      }
      if (EnumSet.class.isAssignableFrom(paramClass)) {
        new h()
        {
          public T a()
          {
            Object localObject = paramType;
            if ((localObject instanceof ParameterizedType))
            {
              localObject = ((ParameterizedType)localObject).getActualTypeArguments()[0];
              if ((localObject instanceof Class)) {
                return EnumSet.noneOf((Class)localObject);
              }
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append("Invalid EnumSet type: ");
              ((StringBuilder)localObject).append(paramType.toString());
              throw new JsonIOException(((StringBuilder)localObject).toString());
            }
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("Invalid EnumSet type: ");
            ((StringBuilder)localObject).append(paramType.toString());
            throw new JsonIOException(((StringBuilder)localObject).toString());
          }
        };
      }
      if (Set.class.isAssignableFrom(paramClass)) {
        new h()
        {
          public T a()
          {
            return new LinkedHashSet();
          }
        };
      }
      if (Queue.class.isAssignableFrom(paramClass)) {
        new h()
        {
          public T a()
          {
            return new ArrayDeque();
          }
        };
      }
      new h()
      {
        public T a()
        {
          return new ArrayList();
        }
      };
    }
    if (Map.class.isAssignableFrom(paramClass))
    {
      if (ConcurrentNavigableMap.class.isAssignableFrom(paramClass)) {
        new h()
        {
          public T a()
          {
            return new ConcurrentSkipListMap();
          }
        };
      }
      if (ConcurrentMap.class.isAssignableFrom(paramClass)) {
        new h()
        {
          public T a()
          {
            return new ConcurrentHashMap();
          }
        };
      }
      if (SortedMap.class.isAssignableFrom(paramClass)) {
        new h()
        {
          public T a()
          {
            return new TreeMap();
          }
        };
      }
      if (((paramType instanceof ParameterizedType)) && (!String.class.isAssignableFrom(a.a(((ParameterizedType)paramType).getActualTypeArguments()[0]).a()))) {
        new h()
        {
          public T a()
          {
            return new LinkedHashMap();
          }
        };
      }
      new h()
      {
        public T a()
        {
          return new g();
        }
      };
    }
    return null;
  }
  
  private <T> h<T> b(final Type paramType, final Class<? super T> paramClass)
  {
    new h()
    {
      private final k d = k.a();
      
      public T a()
      {
        try
        {
          Object localObject = this.d.a(paramClass);
          return (T)localObject;
        }
        catch (Exception localException)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Unable to invoke no-args constructor for ");
          localStringBuilder.append(paramType);
          localStringBuilder.append(". ");
          localStringBuilder.append("Register an InstanceCreator with Gson for this type may fix this problem.");
          throw new RuntimeException(localStringBuilder.toString(), localException);
        }
      }
    };
  }
  
  public <T> h<T> a(a<T> parama)
  {
    final Type localType = parama.b();
    parama = parama.a();
    final Object localObject = (com.google.gson.h)this.a.get(localType);
    if (localObject != null) {
      new h()
      {
        public T a()
        {
          return (T)localObject.a(localType);
        }
      };
    }
    localObject = (com.google.gson.h)this.a.get(parama);
    if (localObject != null) {
      new h()
      {
        public T a()
        {
          return (T)localObject.a(localType);
        }
      };
    }
    localObject = a(parama);
    if (localObject != null) {
      return (h<T>)localObject;
    }
    localObject = a(localType, parama);
    if (localObject != null) {
      return (h<T>)localObject;
    }
    return b(localType, parama);
  }
  
  public String toString()
  {
    return this.a.toString();
  }
}


/* Location:              ~/com/google/gson/b/c.class
 *
 * Reversed by:           J
 */