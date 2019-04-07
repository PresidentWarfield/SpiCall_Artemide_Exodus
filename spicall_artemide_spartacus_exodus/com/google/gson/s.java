package com.google.gson;

import com.google.gson.b.a.f;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public abstract class s<T>
{
  public final l a(T paramT)
  {
    try
    {
      f localf = new com/google/gson/b/a/f;
      localf.<init>();
      a(localf, paramT);
      paramT = localf.a();
      return paramT;
    }
    catch (IOException paramT)
    {
      throw new JsonIOException(paramT);
    }
  }
  
  public final s<T> a()
  {
    new s()
    {
      public void a(JsonWriter paramAnonymousJsonWriter, T paramAnonymousT)
      {
        if (paramAnonymousT == null) {
          paramAnonymousJsonWriter.nullValue();
        } else {
          s.this.a(paramAnonymousJsonWriter, paramAnonymousT);
        }
      }
      
      public T b(JsonReader paramAnonymousJsonReader)
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return (T)s.this.b(paramAnonymousJsonReader);
      }
    };
  }
  
  public abstract void a(JsonWriter paramJsonWriter, T paramT);
  
  public abstract T b(JsonReader paramJsonReader);
}


/* Location:              ~/com/google/gson/s.class
 *
 * Reversed by:           J
 */