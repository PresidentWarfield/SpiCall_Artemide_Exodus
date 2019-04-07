package com.google.gson.b;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

public final class b
{
  static final Type[] a = new Type[0];
  
  static int a(Object paramObject)
  {
    int i;
    if (paramObject != null) {
      i = paramObject.hashCode();
    } else {
      i = 0;
    }
    return i;
  }
  
  private static int a(Object[] paramArrayOfObject, Object paramObject)
  {
    int i = paramArrayOfObject.length;
    for (int j = 0; j < i; j++) {
      if (paramObject.equals(paramArrayOfObject[j])) {
        return j;
      }
    }
    throw new NoSuchElementException();
  }
  
  private static Class<?> a(TypeVariable<?> paramTypeVariable)
  {
    paramTypeVariable = paramTypeVariable.getGenericDeclaration();
    if ((paramTypeVariable instanceof Class)) {
      paramTypeVariable = (Class)paramTypeVariable;
    } else {
      paramTypeVariable = null;
    }
    return paramTypeVariable;
  }
  
  public static GenericArrayType a(Type paramType)
  {
    return new a(paramType);
  }
  
  public static ParameterizedType a(Type paramType1, Type paramType2, Type... paramVarArgs)
  {
    return new b(paramType1, paramType2, paramVarArgs);
  }
  
  public static Type a(Type paramType, Class<?> paramClass)
  {
    paramClass = b(paramType, paramClass, Collection.class);
    paramType = paramClass;
    if ((paramClass instanceof WildcardType)) {
      paramType = ((WildcardType)paramClass).getUpperBounds()[0];
    }
    if ((paramType instanceof ParameterizedType)) {
      return ((ParameterizedType)paramType).getActualTypeArguments()[0];
    }
    return Object.class;
  }
  
  static Type a(Type paramType, Class<?> paramClass1, Class<?> paramClass2)
  {
    if (paramClass2 == paramClass1) {
      return paramType;
    }
    if (paramClass2.isInterface())
    {
      paramType = paramClass1.getInterfaces();
      int i = 0;
      int j = paramType.length;
      while (i < j)
      {
        if (paramType[i] == paramClass2) {
          return paramClass1.getGenericInterfaces()[i];
        }
        if (paramClass2.isAssignableFrom(paramType[i])) {
          return a(paramClass1.getGenericInterfaces()[i], paramType[i], paramClass2);
        }
        i++;
      }
    }
    if (!paramClass1.isInterface()) {
      while (paramClass1 != Object.class)
      {
        paramType = paramClass1.getSuperclass();
        if (paramType == paramClass2) {
          return paramClass1.getGenericSuperclass();
        }
        if (paramClass2.isAssignableFrom(paramType)) {
          return a(paramClass1.getGenericSuperclass(), paramType, paramClass2);
        }
        paramClass1 = paramType;
      }
    }
    return paramClass2;
  }
  
  public static Type a(Type paramType1, Class<?> paramClass, Type paramType2)
  {
    Object localObject1;
    while ((paramType2 instanceof TypeVariable))
    {
      localObject1 = (TypeVariable)paramType2;
      paramType2 = a(paramType1, paramClass, (TypeVariable)localObject1);
      if (paramType2 == localObject1) {
        return paramType2;
      }
    }
    if ((paramType2 instanceof Class))
    {
      localObject1 = (Class)paramType2;
      if (((Class)localObject1).isArray())
      {
        paramType2 = ((Class)localObject1).getComponentType();
        paramType1 = a(paramType1, paramClass, paramType2);
        if (paramType2 == paramType1) {
          paramType1 = (Type)localObject1;
        } else {
          paramType1 = a(paramType1);
        }
        return paramType1;
      }
    }
    if ((paramType2 instanceof GenericArrayType))
    {
      paramType2 = (GenericArrayType)paramType2;
      localObject1 = paramType2.getGenericComponentType();
      paramType1 = a(paramType1, paramClass, (Type)localObject1);
      if (localObject1 == paramType1) {
        paramType1 = paramType2;
      } else {
        paramType1 = a(paramType1);
      }
      return paramType1;
    }
    boolean bool = paramType2 instanceof ParameterizedType;
    int i = 0;
    Object localObject2;
    if (bool)
    {
      localObject2 = (ParameterizedType)paramType2;
      paramType2 = ((ParameterizedType)localObject2).getOwnerType();
      Type localType1 = a(paramType1, paramClass, paramType2);
      int j;
      if (localType1 != paramType2) {
        j = 1;
      } else {
        j = 0;
      }
      paramType2 = ((ParameterizedType)localObject2).getActualTypeArguments();
      int k = paramType2.length;
      while (i < k)
      {
        Type localType2 = a(paramType1, paramClass, paramType2[i]);
        int m = j;
        localObject1 = paramType2;
        if (localType2 != paramType2[i])
        {
          m = j;
          localObject1 = paramType2;
          if (j == 0)
          {
            localObject1 = (Type[])paramType2.clone();
            m = 1;
          }
          localObject1[i] = localType2;
        }
        i++;
        j = m;
        paramType2 = (Type)localObject1;
      }
      paramType1 = (Type)localObject2;
      if (j != 0) {
        paramType1 = a(localType1, ((ParameterizedType)localObject2).getRawType(), paramType2);
      }
      return paramType1;
    }
    if ((paramType2 instanceof WildcardType))
    {
      paramType2 = (WildcardType)paramType2;
      localObject2 = paramType2.getLowerBounds();
      localObject1 = paramType2.getUpperBounds();
      if (localObject2.length == 1)
      {
        paramType1 = a(paramType1, paramClass, localObject2[0]);
        if (paramType1 != localObject2[0]) {
          return c(paramType1);
        }
      }
      else if (localObject1.length == 1)
      {
        localObject2 = localObject1[0];
      }
    }
    try
    {
      paramType1 = a(paramType1, paramClass, (Type)localObject2);
      if (paramType1 != localObject1[0]) {
        return b(paramType1);
      }
      return paramType2;
    }
    catch (Throwable paramType1)
    {
      throw paramType1;
    }
    return paramType2;
  }
  
  static Type a(Type paramType, Class<?> paramClass, TypeVariable<?> paramTypeVariable)
  {
    Class localClass = a(paramTypeVariable);
    if (localClass == null) {
      return paramTypeVariable;
    }
    paramType = a(paramType, paramClass, localClass);
    if ((paramType instanceof ParameterizedType))
    {
      int i = a(localClass.getTypeParameters(), paramTypeVariable);
      return ((ParameterizedType)paramType).getActualTypeArguments()[i];
    }
    return paramTypeVariable;
  }
  
  static boolean a(Object paramObject1, Object paramObject2)
  {
    boolean bool;
    if ((paramObject1 != paramObject2) && ((paramObject1 == null) || (!paramObject1.equals(paramObject2)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static boolean a(Type paramType1, Type paramType2)
  {
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3 = true;
    if (paramType1 == paramType2) {
      return true;
    }
    if ((paramType1 instanceof Class)) {
      return paramType1.equals(paramType2);
    }
    if ((paramType1 instanceof ParameterizedType))
    {
      if (!(paramType2 instanceof ParameterizedType)) {
        return false;
      }
      paramType1 = (ParameterizedType)paramType1;
      paramType2 = (ParameterizedType)paramType2;
      if ((!a(paramType1.getOwnerType(), paramType2.getOwnerType())) || (!paramType1.getRawType().equals(paramType2.getRawType())) || (!Arrays.equals(paramType1.getActualTypeArguments(), paramType2.getActualTypeArguments()))) {
        bool3 = false;
      }
      return bool3;
    }
    if ((paramType1 instanceof GenericArrayType))
    {
      if (!(paramType2 instanceof GenericArrayType)) {
        return false;
      }
      paramType1 = (GenericArrayType)paramType1;
      paramType2 = (GenericArrayType)paramType2;
      return a(paramType1.getGenericComponentType(), paramType2.getGenericComponentType());
    }
    if ((paramType1 instanceof WildcardType))
    {
      if (!(paramType2 instanceof WildcardType)) {
        return false;
      }
      paramType1 = (WildcardType)paramType1;
      paramType2 = (WildcardType)paramType2;
      if ((Arrays.equals(paramType1.getUpperBounds(), paramType2.getUpperBounds())) && (Arrays.equals(paramType1.getLowerBounds(), paramType2.getLowerBounds()))) {
        bool3 = bool1;
      } else {
        bool3 = false;
      }
      return bool3;
    }
    if ((paramType1 instanceof TypeVariable))
    {
      if (!(paramType2 instanceof TypeVariable)) {
        return false;
      }
      paramType1 = (TypeVariable)paramType1;
      paramType2 = (TypeVariable)paramType2;
      if ((paramType1.getGenericDeclaration() == paramType2.getGenericDeclaration()) && (paramType1.getName().equals(paramType2.getName()))) {
        bool3 = bool2;
      } else {
        bool3 = false;
      }
      return bool3;
    }
    return false;
  }
  
  static Type b(Type paramType, Class<?> paramClass1, Class<?> paramClass2)
  {
    a.a(paramClass2.isAssignableFrom(paramClass1));
    return a(paramType, paramClass1, a(paramType, paramClass1, paramClass2));
  }
  
  public static WildcardType b(Type paramType)
  {
    if ((paramType instanceof WildcardType)) {
      paramType = ((WildcardType)paramType).getUpperBounds();
    } else {
      paramType = new Type[] { paramType };
    }
    return new c(paramType, a);
  }
  
  public static Type[] b(Type paramType, Class<?> paramClass)
  {
    if (paramType == Properties.class) {
      return new Type[] { String.class, String.class };
    }
    paramType = b(paramType, paramClass, Map.class);
    if ((paramType instanceof ParameterizedType)) {
      return ((ParameterizedType)paramType).getActualTypeArguments();
    }
    return new Type[] { Object.class, Object.class };
  }
  
  public static WildcardType c(Type paramType)
  {
    if ((paramType instanceof WildcardType)) {
      paramType = ((WildcardType)paramType).getLowerBounds();
    } else {
      paramType = new Type[] { paramType };
    }
    return new c(new Type[] { Object.class }, paramType);
  }
  
  public static Type d(Type paramType)
  {
    if ((paramType instanceof Class))
    {
      Class localClass = (Class)paramType;
      paramType = localClass;
      if (localClass.isArray()) {
        paramType = new a(d(localClass.getComponentType()));
      }
      return (Type)paramType;
    }
    if ((paramType instanceof ParameterizedType))
    {
      paramType = (ParameterizedType)paramType;
      return new b(paramType.getOwnerType(), paramType.getRawType(), paramType.getActualTypeArguments());
    }
    if ((paramType instanceof GenericArrayType)) {
      return new a(((GenericArrayType)paramType).getGenericComponentType());
    }
    if ((paramType instanceof WildcardType))
    {
      paramType = (WildcardType)paramType;
      return new c(paramType.getUpperBounds(), paramType.getLowerBounds());
    }
    return paramType;
  }
  
  public static Class<?> e(Type paramType)
  {
    if ((paramType instanceof Class)) {
      return (Class)paramType;
    }
    if ((paramType instanceof ParameterizedType))
    {
      paramType = ((ParameterizedType)paramType).getRawType();
      a.a(paramType instanceof Class);
      return (Class)paramType;
    }
    if ((paramType instanceof GenericArrayType)) {
      return Array.newInstance(e(((GenericArrayType)paramType).getGenericComponentType()), 0).getClass();
    }
    if ((paramType instanceof TypeVariable)) {
      return Object.class;
    }
    if ((paramType instanceof WildcardType)) {
      return e(((WildcardType)paramType).getUpperBounds()[0]);
    }
    String str;
    if (paramType == null) {
      str = "null";
    } else {
      str = paramType.getClass().getName();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Expected a Class, ParameterizedType, or GenericArrayType, but <");
    localStringBuilder.append(paramType);
    localStringBuilder.append("> is of type ");
    localStringBuilder.append(str);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public static String f(Type paramType)
  {
    if ((paramType instanceof Class)) {
      paramType = ((Class)paramType).getName();
    } else {
      paramType = paramType.toString();
    }
    return paramType;
  }
  
  public static Type g(Type paramType)
  {
    if ((paramType instanceof GenericArrayType)) {
      paramType = ((GenericArrayType)paramType).getGenericComponentType();
    } else {
      paramType = ((Class)paramType).getComponentType();
    }
    return paramType;
  }
  
  static void h(Type paramType)
  {
    boolean bool;
    if (((paramType instanceof Class)) && (((Class)paramType).isPrimitive())) {
      bool = false;
    } else {
      bool = true;
    }
    a.a(bool);
  }
  
  private static final class a
    implements Serializable, GenericArrayType
  {
    private final Type a;
    
    public a(Type paramType)
    {
      this.a = b.d(paramType);
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool;
      if (((paramObject instanceof GenericArrayType)) && (b.a(this, (GenericArrayType)paramObject))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public Type getGenericComponentType()
    {
      return this.a;
    }
    
    public int hashCode()
    {
      return this.a.hashCode();
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(b.f(this.a));
      localStringBuilder.append("[]");
      return localStringBuilder.toString();
    }
  }
  
  private static final class b
    implements Serializable, ParameterizedType
  {
    private final Type a;
    private final Type b;
    private final Type[] c;
    
    public b(Type paramType1, Type paramType2, Type... paramVarArgs)
    {
      boolean bool1 = paramType2 instanceof Class;
      int i = 0;
      if (bool1)
      {
        Class localClass = (Class)paramType2;
        bool1 = Modifier.isStatic(localClass.getModifiers());
        boolean bool2 = true;
        if ((!bool1) && (localClass.getEnclosingClass() != null)) {
          j = 0;
        } else {
          j = 1;
        }
        bool1 = bool2;
        if (paramType1 == null) {
          if (j != 0) {
            bool1 = bool2;
          } else {
            bool1 = false;
          }
        }
        a.a(bool1);
      }
      if (paramType1 == null) {
        paramType1 = null;
      } else {
        paramType1 = b.d(paramType1);
      }
      this.a = paramType1;
      this.b = b.d(paramType2);
      this.c = ((Type[])paramVarArgs.clone());
      int k = this.c.length;
      for (int j = i; j < k; j++)
      {
        a.a(this.c[j]);
        b.h(this.c[j]);
        paramType1 = this.c;
        paramType1[j] = b.d(paramType1[j]);
      }
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool;
      if (((paramObject instanceof ParameterizedType)) && (b.a(this, (ParameterizedType)paramObject))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public Type[] getActualTypeArguments()
    {
      return (Type[])this.c.clone();
    }
    
    public Type getOwnerType()
    {
      return this.a;
    }
    
    public Type getRawType()
    {
      return this.b;
    }
    
    public int hashCode()
    {
      return Arrays.hashCode(this.c) ^ this.b.hashCode() ^ b.a(this.a);
    }
    
    public String toString()
    {
      int i = this.c.length;
      if (i == 0) {
        return b.f(this.b);
      }
      StringBuilder localStringBuilder = new StringBuilder((i + 1) * 30);
      localStringBuilder.append(b.f(this.b));
      localStringBuilder.append("<");
      localStringBuilder.append(b.f(this.c[0]));
      for (int j = 1; j < i; j++)
      {
        localStringBuilder.append(", ");
        localStringBuilder.append(b.f(this.c[j]));
      }
      localStringBuilder.append(">");
      return localStringBuilder.toString();
    }
  }
  
  private static final class c
    implements Serializable, WildcardType
  {
    private final Type a;
    private final Type b;
    
    public c(Type[] paramArrayOfType1, Type[] paramArrayOfType2)
    {
      int i = paramArrayOfType2.length;
      boolean bool1 = true;
      boolean bool2;
      if (i <= 1) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      a.a(bool2);
      if (paramArrayOfType1.length == 1) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      a.a(bool2);
      if (paramArrayOfType2.length == 1)
      {
        a.a(paramArrayOfType2[0]);
        b.h(paramArrayOfType2[0]);
        if (paramArrayOfType1[0] == Object.class) {
          bool2 = bool1;
        } else {
          bool2 = false;
        }
        a.a(bool2);
        this.b = b.d(paramArrayOfType2[0]);
        this.a = Object.class;
      }
      else
      {
        a.a(paramArrayOfType1[0]);
        b.h(paramArrayOfType1[0]);
        this.b = null;
        this.a = b.d(paramArrayOfType1[0]);
      }
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool;
      if (((paramObject instanceof WildcardType)) && (b.a(this, (WildcardType)paramObject))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public Type[] getLowerBounds()
    {
      Type localType = this.b;
      Type[] arrayOfType;
      if (localType != null)
      {
        arrayOfType = new Type[1];
        arrayOfType[0] = localType;
      }
      else
      {
        arrayOfType = b.a;
      }
      return arrayOfType;
    }
    
    public Type[] getUpperBounds()
    {
      return new Type[] { this.a };
    }
    
    public int hashCode()
    {
      Type localType = this.b;
      int i;
      if (localType != null) {
        i = localType.hashCode() + 31;
      } else {
        i = 1;
      }
      return i ^ this.a.hashCode() + 31;
    }
    
    public String toString()
    {
      if (this.b != null)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("? super ");
        localStringBuilder.append(b.f(this.b));
        return localStringBuilder.toString();
      }
      if (this.a == Object.class) {
        return "?";
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("? extends ");
      localStringBuilder.append(b.f(this.a));
      return localStringBuilder.toString();
    }
  }
}


/* Location:              ~/com/google/gson/b/b.class
 *
 * Reversed by:           J
 */