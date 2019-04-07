package com.google.gson;

import com.google.gson.b.j;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.StringWriter;

public abstract class l
{
  public Number a()
  {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public String b()
  {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public double c()
  {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public long d()
  {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public int e()
  {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public boolean f()
  {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public boolean g()
  {
    return this instanceof i;
  }
  
  public boolean h()
  {
    return this instanceof n;
  }
  
  public boolean i()
  {
    return this instanceof o;
  }
  
  public boolean j()
  {
    return this instanceof m;
  }
  
  public n k()
  {
    if (h()) {
      return (n)this;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Not a JSON Object: ");
    localStringBuilder.append(this);
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public i l()
  {
    if (g()) {
      return (i)this;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Not a JSON Array: ");
    localStringBuilder.append(this);
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public o m()
  {
    if (i()) {
      return (o)this;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Not a JSON Primitive: ");
    localStringBuilder.append(this);
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  Boolean n()
  {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public String toString()
  {
    try
    {
      StringWriter localStringWriter = new java/io/StringWriter;
      localStringWriter.<init>();
      Object localObject = new com/google/gson/stream/JsonWriter;
      ((JsonWriter)localObject).<init>(localStringWriter);
      ((JsonWriter)localObject).setLenient(true);
      j.a(this, (JsonWriter)localObject);
      localObject = localStringWriter.toString();
      return (String)localObject;
    }
    catch (IOException localIOException)
    {
      throw new AssertionError(localIOException);
    }
  }
}


/* Location:              ~/com/google/gson/l.class
 *
 * Reversed by:           J
 */