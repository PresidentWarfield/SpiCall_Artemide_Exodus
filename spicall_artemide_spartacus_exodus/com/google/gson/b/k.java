package com.google.gson.b;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract class k
{
  public static k a()
  {
    try
    {
      Object localObject1 = Class.forName("sun.misc.Unsafe");
      final Object localObject4 = ((Class)localObject1).getDeclaredField("theUnsafe");
      ((Field)localObject4).setAccessible(true);
      localObject4 = ((Field)localObject4).get(null);
      localObject1 = new k()
      {
        public <T> T a(Class<T> paramAnonymousClass)
        {
          b(paramAnonymousClass);
          return (T)this.a.invoke(localObject4, new Object[] { paramAnonymousClass });
        }
      };
      return (k)localObject1;
    }
    catch (Exception localException1)
    {
      try
      {
        Object localObject2 = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[] { Class.class });
        ((Method)localObject2).setAccessible(true);
        final int i = ((Integer)((Method)localObject2).invoke(null, new Object[] { Object.class })).intValue();
        localObject2 = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[] { Class.class, Integer.TYPE });
        ((Method)localObject2).setAccessible(true);
        localObject2 = new k()
        {
          public <T> T a(Class<T> paramAnonymousClass)
          {
            b(paramAnonymousClass);
            return (T)this.a.invoke(null, new Object[] { paramAnonymousClass, Integer.valueOf(i) });
          }
        };
        return (k)localObject2;
      }
      catch (Exception localException2)
      {
        try
        {
          Object localObject3 = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[] { Class.class, Class.class });
          ((Method)localObject3).setAccessible(true);
          localObject3 = new k()
          {
            public <T> T a(Class<T> paramAnonymousClass)
            {
              b(paramAnonymousClass);
              return (T)this.a.invoke(null, new Object[] { paramAnonymousClass, Object.class });
            }
          };
          return (k)localObject3;
        }
        catch (Exception localException3) {}
      }
    }
    new k()
    {
      public <T> T a(Class<T> paramAnonymousClass)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Cannot allocate ");
        localStringBuilder.append(paramAnonymousClass);
        throw new UnsupportedOperationException(localStringBuilder.toString());
      }
    };
  }
  
  static void b(Class<?> paramClass)
  {
    int i = paramClass.getModifiers();
    if (!Modifier.isInterface(i))
    {
      if (!Modifier.isAbstract(i)) {
        return;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Abstract class can't be instantiated! Class name: ");
      localStringBuilder.append(paramClass.getName());
      throw new UnsupportedOperationException(localStringBuilder.toString());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Interface can't be instantiated! Interface name: ");
    localStringBuilder.append(paramClass.getName());
    throw new UnsupportedOperationException(localStringBuilder.toString());
  }
  
  public abstract <T> T a(Class<T> paramClass);
}


/* Location:              ~/com/google/gson/b/k.class
 *
 * Reversed by:           J
 */