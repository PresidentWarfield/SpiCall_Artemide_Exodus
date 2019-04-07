package com.google.gson.b.a;

import com.google.gson.i;
import com.google.gson.m;
import com.google.gson.n;
import com.google.gson.o;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public final class e
  extends JsonReader
{
  private static final Reader a = new Reader()
  {
    public void close()
    {
      throw new AssertionError();
    }
    
    public int read(char[] paramAnonymousArrayOfChar, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      throw new AssertionError();
    }
  };
  private static final Object b = new Object();
  private Object[] c;
  private int d;
  private String[] e;
  private int[] f;
  
  private void a(JsonToken paramJsonToken)
  {
    if (peek() == paramJsonToken) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Expected ");
    localStringBuilder.append(paramJsonToken);
    localStringBuilder.append(" but was ");
    localStringBuilder.append(peek());
    localStringBuilder.append(d());
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  private void a(Object paramObject)
  {
    int i = this.d;
    Object[] arrayOfObject1 = this.c;
    if (i == arrayOfObject1.length)
    {
      Object[] arrayOfObject2 = new Object[i * 2];
      localObject = new int[i * 2];
      String[] arrayOfString = new String[i * 2];
      System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, i);
      System.arraycopy(this.f, 0, localObject, 0, this.d);
      System.arraycopy(this.e, 0, arrayOfString, 0, this.d);
      this.c = arrayOfObject2;
      this.f = ((int[])localObject);
      this.e = arrayOfString;
    }
    Object localObject = this.c;
    i = this.d;
    this.d = (i + 1);
    localObject[i] = paramObject;
  }
  
  private Object b()
  {
    return this.c[(this.d - 1)];
  }
  
  private Object c()
  {
    Object[] arrayOfObject = this.c;
    int i = this.d - 1;
    this.d = i;
    Object localObject = arrayOfObject[i];
    arrayOfObject[this.d] = null;
    return localObject;
  }
  
  private String d()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" at path ");
    localStringBuilder.append(getPath());
    return localStringBuilder.toString();
  }
  
  public void a()
  {
    a(JsonToken.NAME);
    Map.Entry localEntry = (Map.Entry)((Iterator)b()).next();
    a(localEntry.getValue());
    a(new o((String)localEntry.getKey()));
  }
  
  public void beginArray()
  {
    a(JsonToken.BEGIN_ARRAY);
    a(((i)b()).iterator());
    this.f[(this.d - 1)] = 0;
  }
  
  public void beginObject()
  {
    a(JsonToken.BEGIN_OBJECT);
    a(((n)b()).o().iterator());
  }
  
  public void close()
  {
    this.c = new Object[] { b };
    this.d = 1;
  }
  
  public void endArray()
  {
    a(JsonToken.END_ARRAY);
    c();
    c();
    int i = this.d;
    if (i > 0)
    {
      int[] arrayOfInt = this.f;
      i--;
      arrayOfInt[i] += 1;
    }
  }
  
  public void endObject()
  {
    a(JsonToken.END_OBJECT);
    c();
    c();
    int i = this.d;
    if (i > 0)
    {
      int[] arrayOfInt = this.f;
      i--;
      arrayOfInt[i] += 1;
    }
  }
  
  public String getPath()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('$');
    int j;
    for (int i = 0; i < this.d; i = j + 1)
    {
      Object localObject = this.c;
      if ((localObject[i] instanceof i))
      {
        i++;
        j = i;
        if ((localObject[i] instanceof Iterator))
        {
          localStringBuilder.append('[');
          localStringBuilder.append(this.f[i]);
          localStringBuilder.append(']');
          j = i;
        }
      }
      else
      {
        j = i;
        if ((localObject[i] instanceof n))
        {
          i++;
          j = i;
          if ((localObject[i] instanceof Iterator))
          {
            localStringBuilder.append('.');
            localObject = this.e;
            j = i;
            if (localObject[i] != null)
            {
              localStringBuilder.append(localObject[i]);
              j = i;
            }
          }
        }
      }
    }
    return localStringBuilder.toString();
  }
  
  public boolean hasNext()
  {
    JsonToken localJsonToken = peek();
    boolean bool;
    if ((localJsonToken != JsonToken.END_OBJECT) && (localJsonToken != JsonToken.END_ARRAY)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean nextBoolean()
  {
    a(JsonToken.BOOLEAN);
    boolean bool = ((o)c()).f();
    int i = this.d;
    if (i > 0)
    {
      int[] arrayOfInt = this.f;
      i--;
      arrayOfInt[i] += 1;
    }
    return bool;
  }
  
  public double nextDouble()
  {
    Object localObject = peek();
    if ((localObject != JsonToken.NUMBER) && (localObject != JsonToken.STRING))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Expected ");
      localStringBuilder.append(JsonToken.NUMBER);
      localStringBuilder.append(" but was ");
      localStringBuilder.append(localObject);
      localStringBuilder.append(d());
      throw new IllegalStateException(localStringBuilder.toString());
    }
    double d1 = ((o)b()).c();
    if ((!isLenient()) && ((Double.isNaN(d1)) || (Double.isInfinite(d1))))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("JSON forbids NaN and infinities: ");
      ((StringBuilder)localObject).append(d1);
      throw new NumberFormatException(((StringBuilder)localObject).toString());
    }
    c();
    int i = this.d;
    if (i > 0)
    {
      localObject = this.f;
      i--;
      localObject[i] += 1;
    }
    return d1;
  }
  
  public int nextInt()
  {
    Object localObject = peek();
    if ((localObject != JsonToken.NUMBER) && (localObject != JsonToken.STRING))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Expected ");
      localStringBuilder.append(JsonToken.NUMBER);
      localStringBuilder.append(" but was ");
      localStringBuilder.append(localObject);
      localStringBuilder.append(d());
      throw new IllegalStateException(localStringBuilder.toString());
    }
    int i = ((o)b()).e();
    c();
    int j = this.d;
    if (j > 0)
    {
      localObject = this.f;
      j--;
      localObject[j] += 1;
    }
    return i;
  }
  
  public long nextLong()
  {
    Object localObject = peek();
    if ((localObject != JsonToken.NUMBER) && (localObject != JsonToken.STRING))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Expected ");
      localStringBuilder.append(JsonToken.NUMBER);
      localStringBuilder.append(" but was ");
      localStringBuilder.append(localObject);
      localStringBuilder.append(d());
      throw new IllegalStateException(localStringBuilder.toString());
    }
    long l = ((o)b()).d();
    c();
    int i = this.d;
    if (i > 0)
    {
      localObject = this.f;
      i--;
      localObject[i] += 1;
    }
    return l;
  }
  
  public String nextName()
  {
    a(JsonToken.NAME);
    Map.Entry localEntry = (Map.Entry)((Iterator)b()).next();
    String str = (String)localEntry.getKey();
    this.e[(this.d - 1)] = str;
    a(localEntry.getValue());
    return str;
  }
  
  public void nextNull()
  {
    a(JsonToken.NULL);
    c();
    int i = this.d;
    if (i > 0)
    {
      int[] arrayOfInt = this.f;
      i--;
      arrayOfInt[i] += 1;
    }
  }
  
  public String nextString()
  {
    Object localObject1 = peek();
    if ((localObject1 != JsonToken.STRING) && (localObject1 != JsonToken.NUMBER))
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Expected ");
      ((StringBuilder)localObject2).append(JsonToken.STRING);
      ((StringBuilder)localObject2).append(" but was ");
      ((StringBuilder)localObject2).append(localObject1);
      ((StringBuilder)localObject2).append(d());
      throw new IllegalStateException(((StringBuilder)localObject2).toString());
    }
    Object localObject2 = ((o)c()).b();
    int i = this.d;
    if (i > 0)
    {
      localObject1 = this.f;
      i--;
      localObject1[i] += 1;
    }
    return (String)localObject2;
  }
  
  public JsonToken peek()
  {
    if (this.d == 0) {
      return JsonToken.END_DOCUMENT;
    }
    Object localObject = b();
    if ((localObject instanceof Iterator))
    {
      boolean bool = this.c[(this.d - 2)] instanceof n;
      localObject = (Iterator)localObject;
      if (((Iterator)localObject).hasNext())
      {
        if (bool) {
          return JsonToken.NAME;
        }
        a(((Iterator)localObject).next());
        return peek();
      }
      if (bool) {
        localObject = JsonToken.END_OBJECT;
      } else {
        localObject = JsonToken.END_ARRAY;
      }
      return (JsonToken)localObject;
    }
    if ((localObject instanceof n)) {
      return JsonToken.BEGIN_OBJECT;
    }
    if ((localObject instanceof i)) {
      return JsonToken.BEGIN_ARRAY;
    }
    if ((localObject instanceof o))
    {
      localObject = (o)localObject;
      if (((o)localObject).q()) {
        return JsonToken.STRING;
      }
      if (((o)localObject).o()) {
        return JsonToken.BOOLEAN;
      }
      if (((o)localObject).p()) {
        return JsonToken.NUMBER;
      }
      throw new AssertionError();
    }
    if ((localObject instanceof m)) {
      return JsonToken.NULL;
    }
    if (localObject == b) {
      throw new IllegalStateException("JsonReader is closed");
    }
    throw new AssertionError();
  }
  
  public void skipValue()
  {
    if (peek() == JsonToken.NAME)
    {
      nextName();
      this.e[(this.d - 2)] = "null";
    }
    else
    {
      c();
      i = this.d;
      if (i > 0) {
        this.e[(i - 1)] = "null";
      }
    }
    int i = this.d;
    if (i > 0)
    {
      int[] arrayOfInt = this.f;
      i--;
      arrayOfInt[i] += 1;
    }
  }
  
  public String toString()
  {
    return getClass().getSimpleName();
  }
}


/* Location:              ~/com/google/gson/b/a/e.class
 *
 * Reversed by:           J
 */