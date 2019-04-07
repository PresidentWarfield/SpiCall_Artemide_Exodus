package com.google.gson.b;

import java.math.BigDecimal;

public final class f
  extends Number
{
  private final String a;
  
  public f(String paramString)
  {
    this.a = paramString;
  }
  
  public double doubleValue()
  {
    return Double.parseDouble(this.a);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = true;
    if (this == paramObject) {
      return true;
    }
    if ((paramObject instanceof f))
    {
      Object localObject = (f)paramObject;
      paramObject = this.a;
      localObject = ((f)localObject).a;
      boolean bool2 = bool1;
      if (paramObject != localObject) {
        if (((String)paramObject).equals(localObject)) {
          bool2 = bool1;
        } else {
          bool2 = false;
        }
      }
      return bool2;
    }
    return false;
  }
  
  public float floatValue()
  {
    return Float.parseFloat(this.a);
  }
  
  public int hashCode()
  {
    return this.a.hashCode();
  }
  
  public int intValue()
  {
    try
    {
      int i = Integer.parseInt(this.a);
      return i;
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      try
      {
        long l = Long.parseLong(this.a);
        return (int)l;
      }
      catch (NumberFormatException localNumberFormatException2) {}
    }
    return new BigDecimal(this.a).intValue();
  }
  
  public long longValue()
  {
    try
    {
      long l = Long.parseLong(this.a);
      return l;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return new BigDecimal(this.a).longValue();
  }
  
  public String toString()
  {
    return this.a;
  }
}


/* Location:              ~/com/google/gson/b/f.class
 *
 * Reversed by:           J
 */