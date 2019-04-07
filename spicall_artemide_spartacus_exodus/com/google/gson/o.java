package com.google.gson;

import com.google.gson.b.a;
import com.google.gson.b.f;
import java.math.BigInteger;

public final class o
  extends l
{
  private static final Class<?>[] a = { Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class };
  private Object b;
  
  public o(Boolean paramBoolean)
  {
    a(paramBoolean);
  }
  
  public o(Number paramNumber)
  {
    a(paramNumber);
  }
  
  public o(String paramString)
  {
    a(paramString);
  }
  
  private static boolean a(o paramo)
  {
    paramo = paramo.b;
    boolean bool1 = paramo instanceof Number;
    boolean bool2 = false;
    if (bool1)
    {
      paramo = (Number)paramo;
      if (((paramo instanceof BigInteger)) || ((paramo instanceof Long)) || ((paramo instanceof Integer)) || ((paramo instanceof Short)) || ((paramo instanceof Byte))) {
        bool2 = true;
      }
      return bool2;
    }
    return false;
  }
  
  private static boolean b(Object paramObject)
  {
    if ((paramObject instanceof String)) {
      return true;
    }
    paramObject = paramObject.getClass();
    Class[] arrayOfClass = a;
    int i = arrayOfClass.length;
    for (int j = 0; j < i; j++) {
      if (arrayOfClass[j].isAssignableFrom((Class)paramObject)) {
        return true;
      }
    }
    return false;
  }
  
  public Number a()
  {
    Object localObject = this.b;
    if ((localObject instanceof String)) {
      localObject = new f((String)localObject);
    } else {
      localObject = (Number)localObject;
    }
    return (Number)localObject;
  }
  
  void a(Object paramObject)
  {
    if ((paramObject instanceof Character))
    {
      this.b = String.valueOf(((Character)paramObject).charValue());
    }
    else
    {
      boolean bool;
      if ((!(paramObject instanceof Number)) && (!b(paramObject))) {
        bool = false;
      } else {
        bool = true;
      }
      a.a(bool);
      this.b = paramObject;
    }
  }
  
  public String b()
  {
    if (p()) {
      return a().toString();
    }
    if (o()) {
      return n().toString();
    }
    return (String)this.b;
  }
  
  public double c()
  {
    double d;
    if (p()) {
      d = a().doubleValue();
    } else {
      d = Double.parseDouble(b());
    }
    return d;
  }
  
  public long d()
  {
    long l;
    if (p()) {
      l = a().longValue();
    } else {
      l = Long.parseLong(b());
    }
    return l;
  }
  
  public int e()
  {
    int i;
    if (p()) {
      i = a().intValue();
    } else {
      i = Integer.parseInt(b());
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3 = true;
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (o)paramObject;
      if (this.b == null)
      {
        if (((o)paramObject).b != null) {
          bool3 = false;
        }
        return bool3;
      }
      if ((a(this)) && (a((o)paramObject)))
      {
        if (a().longValue() == ((o)paramObject).a().longValue()) {
          bool3 = bool1;
        } else {
          bool3 = false;
        }
        return bool3;
      }
      if (((this.b instanceof Number)) && ((((o)paramObject).b instanceof Number)))
      {
        double d1 = a().doubleValue();
        double d2 = ((o)paramObject).a().doubleValue();
        bool3 = bool2;
        if (d1 != d2) {
          if ((Double.isNaN(d1)) && (Double.isNaN(d2))) {
            bool3 = bool2;
          } else {
            bool3 = false;
          }
        }
        return bool3;
      }
      return this.b.equals(((o)paramObject).b);
    }
    return false;
  }
  
  public boolean f()
  {
    if (o()) {
      return n().booleanValue();
    }
    return Boolean.parseBoolean(b());
  }
  
  public int hashCode()
  {
    if (this.b == null) {
      return 31;
    }
    long l;
    if (a(this))
    {
      l = a().longValue();
      return (int)(l >>> 32 ^ l);
    }
    Object localObject = this.b;
    if ((localObject instanceof Number))
    {
      l = Double.doubleToLongBits(a().doubleValue());
      return (int)(l >>> 32 ^ l);
    }
    return localObject.hashCode();
  }
  
  Boolean n()
  {
    return (Boolean)this.b;
  }
  
  public boolean o()
  {
    return this.b instanceof Boolean;
  }
  
  public boolean p()
  {
    return this.b instanceof Number;
  }
  
  public boolean q()
  {
    return this.b instanceof String;
  }
}


/* Location:              ~/com/google/gson/o.class
 *
 * Reversed by:           J
 */