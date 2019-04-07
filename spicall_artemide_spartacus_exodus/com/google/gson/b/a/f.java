package com.google.gson.b.a;

import com.google.gson.i;
import com.google.gson.l;
import com.google.gson.m;
import com.google.gson.n;
import com.google.gson.o;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public final class f
  extends JsonWriter
{
  private static final Writer a = new Writer()
  {
    public void close()
    {
      throw new AssertionError();
    }
    
    public void flush()
    {
      throw new AssertionError();
    }
    
    public void write(char[] paramAnonymousArrayOfChar, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      throw new AssertionError();
    }
  };
  private static final o b = new o("closed");
  private final List<l> c = new ArrayList();
  private String d;
  private l e = m.a;
  
  public f()
  {
    super(a);
  }
  
  private void a(l paraml)
  {
    if (this.d != null)
    {
      if ((!paraml.j()) || (getSerializeNulls())) {
        ((n)b()).a(this.d, paraml);
      }
      this.d = null;
    }
    else if (this.c.isEmpty())
    {
      this.e = paraml;
    }
    else
    {
      l locall = b();
      if (!(locall instanceof i)) {
        break label85;
      }
      ((i)locall).a(paraml);
    }
    return;
    label85:
    throw new IllegalStateException();
  }
  
  private l b()
  {
    List localList = this.c;
    return (l)localList.get(localList.size() - 1);
  }
  
  public l a()
  {
    if (this.c.isEmpty()) {
      return this.e;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Expected one JSON element but was ");
    localStringBuilder.append(this.c);
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public JsonWriter beginArray()
  {
    i locali = new i();
    a(locali);
    this.c.add(locali);
    return this;
  }
  
  public JsonWriter beginObject()
  {
    n localn = new n();
    a(localn);
    this.c.add(localn);
    return this;
  }
  
  public void close()
  {
    if (this.c.isEmpty())
    {
      this.c.add(b);
      return;
    }
    throw new IOException("Incomplete document");
  }
  
  public JsonWriter endArray()
  {
    if ((!this.c.isEmpty()) && (this.d == null))
    {
      if ((b() instanceof i))
      {
        List localList = this.c;
        localList.remove(localList.size() - 1);
        return this;
      }
      throw new IllegalStateException();
    }
    throw new IllegalStateException();
  }
  
  public JsonWriter endObject()
  {
    if ((!this.c.isEmpty()) && (this.d == null))
    {
      if ((b() instanceof n))
      {
        List localList = this.c;
        localList.remove(localList.size() - 1);
        return this;
      }
      throw new IllegalStateException();
    }
    throw new IllegalStateException();
  }
  
  public void flush() {}
  
  public JsonWriter name(String paramString)
  {
    if ((!this.c.isEmpty()) && (this.d == null))
    {
      if ((b() instanceof n))
      {
        this.d = paramString;
        return this;
      }
      throw new IllegalStateException();
    }
    throw new IllegalStateException();
  }
  
  public JsonWriter nullValue()
  {
    a(m.a);
    return this;
  }
  
  public JsonWriter value(double paramDouble)
  {
    if ((!isLenient()) && ((Double.isNaN(paramDouble)) || (Double.isInfinite(paramDouble))))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("JSON forbids NaN and infinities: ");
      localStringBuilder.append(paramDouble);
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    a(new o(Double.valueOf(paramDouble)));
    return this;
  }
  
  public JsonWriter value(long paramLong)
  {
    a(new o(Long.valueOf(paramLong)));
    return this;
  }
  
  public JsonWriter value(Boolean paramBoolean)
  {
    if (paramBoolean == null) {
      return nullValue();
    }
    a(new o(paramBoolean));
    return this;
  }
  
  public JsonWriter value(Number paramNumber)
  {
    if (paramNumber == null) {
      return nullValue();
    }
    if (!isLenient())
    {
      double d1 = paramNumber.doubleValue();
      if ((Double.isNaN(d1)) || (Double.isInfinite(d1)))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("JSON forbids NaN and infinities: ");
        localStringBuilder.append(paramNumber);
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
    }
    a(new o(paramNumber));
    return this;
  }
  
  public JsonWriter value(String paramString)
  {
    if (paramString == null) {
      return nullValue();
    }
    a(new o(paramString));
    return this;
  }
  
  public JsonWriter value(boolean paramBoolean)
  {
    a(new o(Boolean.valueOf(paramBoolean)));
    return this;
  }
}


/* Location:              ~/com/google/gson/b/a/f.class
 *
 * Reversed by:           J
 */