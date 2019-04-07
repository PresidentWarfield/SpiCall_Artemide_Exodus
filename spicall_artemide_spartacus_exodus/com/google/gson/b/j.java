package com.google.gson.b;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.b.a.n;
import com.google.gson.l;
import com.google.gson.m;
import com.google.gson.s;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Writer;

public final class j
{
  public static l a(JsonReader paramJsonReader)
  {
    try
    {
      paramJsonReader.peek();
      int i = 0;
      try
      {
        paramJsonReader = (l)n.X.b(paramJsonReader);
        return paramJsonReader;
      }
      catch (EOFException paramJsonReader) {}
      if (i == 0) {
        break label65;
      }
    }
    catch (NumberFormatException paramJsonReader)
    {
      throw new JsonSyntaxException(paramJsonReader);
    }
    catch (IOException paramJsonReader)
    {
      throw new JsonIOException(paramJsonReader);
    }
    catch (MalformedJsonException paramJsonReader)
    {
      throw new JsonSyntaxException(paramJsonReader);
    }
    catch (EOFException paramJsonReader)
    {
      i = 1;
    }
    return m.a;
    label65:
    throw new JsonSyntaxException(paramJsonReader);
  }
  
  public static Writer a(Appendable paramAppendable)
  {
    if ((paramAppendable instanceof Writer)) {
      paramAppendable = (Writer)paramAppendable;
    } else {
      paramAppendable = new a(paramAppendable);
    }
    return paramAppendable;
  }
  
  public static void a(l paraml, JsonWriter paramJsonWriter)
  {
    n.X.a(paramJsonWriter, paraml);
  }
  
  private static final class a
    extends Writer
  {
    private final Appendable a;
    private final a b = new a();
    
    a(Appendable paramAppendable)
    {
      this.a = paramAppendable;
    }
    
    public void close() {}
    
    public void flush() {}
    
    public void write(int paramInt)
    {
      this.a.append((char)paramInt);
    }
    
    public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      a locala = this.b;
      locala.a = paramArrayOfChar;
      this.a.append(locala, paramInt1, paramInt2 + paramInt1);
    }
    
    static class a
      implements CharSequence
    {
      char[] a;
      
      public char charAt(int paramInt)
      {
        return this.a[paramInt];
      }
      
      public int length()
      {
        return this.a.length;
      }
      
      public CharSequence subSequence(int paramInt1, int paramInt2)
      {
        return new String(this.a, paramInt1, paramInt2 - paramInt1);
      }
    }
  }
}


/* Location:              ~/com/google/gson/b/j.class
 *
 * Reversed by:           J
 */