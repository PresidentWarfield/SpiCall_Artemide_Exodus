package com.google.gson.b.a;

import com.google.gson.c.a;
import com.google.gson.f;
import com.google.gson.k;
import com.google.gson.p;
import com.google.gson.q;
import com.google.gson.s;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.t;

public final class l<T>
  extends s<T>
{
  final f a;
  private final q<T> b;
  private final k<T> c;
  private final a<T> d;
  private final t e;
  private final l<T>.a f = new a(null);
  private s<T> g;
  
  public l(q<T> paramq, k<T> paramk, f paramf, a<T> parama, t paramt)
  {
    this.b = paramq;
    this.c = paramk;
    this.a = paramf;
    this.d = parama;
    this.e = paramt;
  }
  
  private s<T> b()
  {
    s locals = this.g;
    if (locals == null)
    {
      locals = this.a.a(this.e, this.d);
      this.g = locals;
    }
    return locals;
  }
  
  public void a(JsonWriter paramJsonWriter, T paramT)
  {
    q localq = this.b;
    if (localq == null)
    {
      b().a(paramJsonWriter, paramT);
      return;
    }
    if (paramT == null)
    {
      paramJsonWriter.nullValue();
      return;
    }
    com.google.gson.b.j.a(localq.a(paramT, this.d.b(), this.f), paramJsonWriter);
  }
  
  public T b(JsonReader paramJsonReader)
  {
    if (this.c == null) {
      return (T)b().b(paramJsonReader);
    }
    paramJsonReader = com.google.gson.b.j.a(paramJsonReader);
    if (paramJsonReader.j()) {
      return null;
    }
    return (T)this.c.a(paramJsonReader, this.d.b(), this.f);
  }
  
  private final class a
    implements com.google.gson.j, p
  {
    private a() {}
  }
}


/* Location:              ~/com/google/gson/b/a/l.class
 *
 * Reversed by:           J
 */