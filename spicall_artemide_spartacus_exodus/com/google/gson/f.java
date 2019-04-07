package com.google.gson;

import com.google.gson.b.a.b;
import com.google.gson.b.a.g;
import com.google.gson.b.a.k;
import com.google.gson.b.a.n;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public final class f
{
  private static final com.google.gson.c.a<?> a = com.google.gson.c.a.b(Object.class);
  private final ThreadLocal<Map<com.google.gson.c.a<?>, a<?>>> b = new ThreadLocal();
  private final Map<com.google.gson.c.a<?>, s<?>> c = new ConcurrentHashMap();
  private final List<t> d;
  private final com.google.gson.b.c e;
  private final com.google.gson.b.d f;
  private final e g;
  private final boolean h;
  private final boolean i;
  private final boolean j;
  private final boolean k;
  private final boolean l;
  private final com.google.gson.b.a.d m;
  
  public f()
  {
    this(com.google.gson.b.d.a, d.a, Collections.emptyMap(), false, false, false, true, false, false, false, r.a, Collections.emptyList());
  }
  
  f(com.google.gson.b.d paramd, e parame, Map<Type, h<?>> paramMap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, r paramr, List<t> paramList)
  {
    this.e = new com.google.gson.b.c(paramMap);
    this.f = paramd;
    this.g = parame;
    this.h = paramBoolean1;
    this.j = paramBoolean3;
    this.i = paramBoolean4;
    this.k = paramBoolean5;
    this.l = paramBoolean6;
    paramMap = new ArrayList();
    paramMap.add(n.Y);
    paramMap.add(com.google.gson.b.a.h.a);
    paramMap.add(paramd);
    paramMap.addAll(paramList);
    paramMap.add(n.D);
    paramMap.add(n.m);
    paramMap.add(n.g);
    paramMap.add(n.i);
    paramMap.add(n.k);
    paramr = a(paramr);
    paramMap.add(n.a(Long.TYPE, Long.class, paramr));
    paramMap.add(n.a(Double.TYPE, Double.class, a(paramBoolean7)));
    paramMap.add(n.a(Float.TYPE, Float.class, b(paramBoolean7)));
    paramMap.add(n.x);
    paramMap.add(n.o);
    paramMap.add(n.q);
    paramMap.add(n.a(AtomicLong.class, a(paramr)));
    paramMap.add(n.a(AtomicLongArray.class, b(paramr)));
    paramMap.add(n.s);
    paramMap.add(n.z);
    paramMap.add(n.F);
    paramMap.add(n.H);
    paramMap.add(n.a(BigDecimal.class, n.B));
    paramMap.add(n.a(BigInteger.class, n.C));
    paramMap.add(n.J);
    paramMap.add(n.L);
    paramMap.add(n.P);
    paramMap.add(n.R);
    paramMap.add(n.W);
    paramMap.add(n.N);
    paramMap.add(n.d);
    paramMap.add(com.google.gson.b.a.c.a);
    paramMap.add(n.U);
    paramMap.add(k.a);
    paramMap.add(com.google.gson.b.a.j.a);
    paramMap.add(n.S);
    paramMap.add(com.google.gson.b.a.a.a);
    paramMap.add(n.b);
    paramMap.add(new b(this.e));
    paramMap.add(new g(this.e, paramBoolean2));
    this.m = new com.google.gson.b.a.d(this.e);
    paramMap.add(this.m);
    paramMap.add(n.Z);
    paramMap.add(new com.google.gson.b.a.i(this.e, parame, paramd, this.m));
    this.d = Collections.unmodifiableList(paramMap);
  }
  
  private static s<Number> a(r paramr)
  {
    if (paramr == r.a) {
      return n.t;
    }
    new s()
    {
      public Number a(JsonReader paramAnonymousJsonReader)
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return Long.valueOf(paramAnonymousJsonReader.nextLong());
      }
      
      public void a(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
      {
        if (paramAnonymousNumber == null)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        paramAnonymousJsonWriter.value(paramAnonymousNumber.toString());
      }
    };
  }
  
  private static s<AtomicLong> a(s<Number> params)
  {
    new s()
    {
      public AtomicLong a(JsonReader paramAnonymousJsonReader)
      {
        return new AtomicLong(((Number)this.a.b(paramAnonymousJsonReader)).longValue());
      }
      
      public void a(JsonWriter paramAnonymousJsonWriter, AtomicLong paramAnonymousAtomicLong)
      {
        this.a.a(paramAnonymousJsonWriter, Long.valueOf(paramAnonymousAtomicLong.get()));
      }
    }.a();
  }
  
  private s<Number> a(boolean paramBoolean)
  {
    if (paramBoolean) {
      return n.v;
    }
    new s()
    {
      public Double a(JsonReader paramAnonymousJsonReader)
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return Double.valueOf(paramAnonymousJsonReader.nextDouble());
      }
      
      public void a(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
      {
        if (paramAnonymousNumber == null)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        f.a(paramAnonymousNumber.doubleValue());
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
  }
  
  static void a(double paramDouble)
  {
    if ((!Double.isNaN(paramDouble)) && (!Double.isInfinite(paramDouble))) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramDouble);
    localStringBuilder.append(" is not a valid double value as per JSON specification. To override this");
    localStringBuilder.append(" behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  private static void a(Object paramObject, JsonReader paramJsonReader)
  {
    if (paramObject != null) {
      try
      {
        if (paramJsonReader.peek() != JsonToken.END_DOCUMENT)
        {
          paramObject = new com/google/gson/JsonIOException;
          ((JsonIOException)paramObject).<init>("JSON document was not fully consumed.");
          throw ((Throwable)paramObject);
        }
      }
      catch (IOException paramObject)
      {
        throw new JsonIOException((Throwable)paramObject);
      }
      catch (MalformedJsonException paramObject)
      {
        throw new JsonSyntaxException((Throwable)paramObject);
      }
    }
  }
  
  private static s<AtomicLongArray> b(s<Number> params)
  {
    new s()
    {
      public AtomicLongArray a(JsonReader paramAnonymousJsonReader)
      {
        ArrayList localArrayList = new ArrayList();
        paramAnonymousJsonReader.beginArray();
        while (paramAnonymousJsonReader.hasNext()) {
          localArrayList.add(Long.valueOf(((Number)this.a.b(paramAnonymousJsonReader)).longValue()));
        }
        paramAnonymousJsonReader.endArray();
        int i = localArrayList.size();
        paramAnonymousJsonReader = new AtomicLongArray(i);
        for (int j = 0; j < i; j++) {
          paramAnonymousJsonReader.set(j, ((Long)localArrayList.get(j)).longValue());
        }
        return paramAnonymousJsonReader;
      }
      
      public void a(JsonWriter paramAnonymousJsonWriter, AtomicLongArray paramAnonymousAtomicLongArray)
      {
        paramAnonymousJsonWriter.beginArray();
        int i = paramAnonymousAtomicLongArray.length();
        for (int j = 0; j < i; j++) {
          this.a.a(paramAnonymousJsonWriter, Long.valueOf(paramAnonymousAtomicLongArray.get(j)));
        }
        paramAnonymousJsonWriter.endArray();
      }
    }.a();
  }
  
  private s<Number> b(boolean paramBoolean)
  {
    if (paramBoolean) {
      return n.u;
    }
    new s()
    {
      public Float a(JsonReader paramAnonymousJsonReader)
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return Float.valueOf((float)paramAnonymousJsonReader.nextDouble());
      }
      
      public void a(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
      {
        if (paramAnonymousNumber == null)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        f.a(paramAnonymousNumber.floatValue());
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
  }
  
  public <T> s<T> a(com.google.gson.c.a<T> parama)
  {
    Object localObject1 = this.c;
    if (parama == null) {
      localObject3 = a;
    } else {
      localObject3 = parama;
    }
    Object localObject3 = (s)((Map)localObject1).get(localObject3);
    if (localObject3 != null) {
      return (s<T>)localObject3;
    }
    localObject1 = (Map)this.b.get();
    int n = 0;
    localObject3 = localObject1;
    if (localObject1 == null)
    {
      localObject3 = new HashMap();
      this.b.set(localObject3);
      n = 1;
    }
    localObject1 = (a)((Map)localObject3).get(parama);
    if (localObject1 != null) {
      return (s<T>)localObject1;
    }
    try
    {
      a locala = new com/google/gson/f$a;
      locala.<init>();
      ((Map)localObject3).put(parama, locala);
      localObject1 = this.d.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject4 = ((t)((Iterator)localObject1).next()).a(this, parama);
        if (localObject4 != null)
        {
          locala.a((s)localObject4);
          this.c.put(parama, localObject4);
          return (s<T>)localObject4;
        }
      }
      Object localObject4 = new java/lang/IllegalArgumentException;
      localObject1 = new java/lang/StringBuilder;
      ((StringBuilder)localObject1).<init>();
      ((StringBuilder)localObject1).append("GSON cannot handle ");
      ((StringBuilder)localObject1).append(parama);
      ((IllegalArgumentException)localObject4).<init>(((StringBuilder)localObject1).toString());
      throw ((Throwable)localObject4);
    }
    finally
    {
      ((Map)localObject3).remove(parama);
      if (n != 0) {
        this.b.remove();
      }
    }
  }
  
  public <T> s<T> a(t paramt, com.google.gson.c.a<T> parama)
  {
    Object localObject1 = paramt;
    if (!this.d.contains(paramt)) {
      localObject1 = this.m;
    }
    int n = 0;
    paramt = this.d.iterator();
    while (paramt.hasNext())
    {
      Object localObject2 = (t)paramt.next();
      if (n == 0)
      {
        if (localObject2 == localObject1) {
          n = 1;
        }
      }
      else
      {
        localObject2 = ((t)localObject2).a(this, parama);
        if (localObject2 != null) {
          return (s<T>)localObject2;
        }
      }
    }
    paramt = new StringBuilder();
    paramt.append("GSON cannot serialize ");
    paramt.append(parama);
    throw new IllegalArgumentException(paramt.toString());
  }
  
  public <T> s<T> a(Class<T> paramClass)
  {
    return a(com.google.gson.c.a.b(paramClass));
  }
  
  public JsonReader a(Reader paramReader)
  {
    paramReader = new JsonReader(paramReader);
    paramReader.setLenient(this.l);
    return paramReader;
  }
  
  public JsonWriter a(Writer paramWriter)
  {
    if (this.j) {
      paramWriter.write(")]}'\n");
    }
    paramWriter = new JsonWriter(paramWriter);
    if (this.k) {
      paramWriter.setIndent("  ");
    }
    paramWriter.setSerializeNulls(this.h);
    return paramWriter;
  }
  
  /* Error */
  public <T> T a(JsonReader paramJsonReader, Type paramType)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 472	com/google/gson/stream/JsonReader:isLenient	()Z
    //   4: istore_3
    //   5: iconst_1
    //   6: istore 4
    //   8: aload_1
    //   9: iconst_1
    //   10: invokevirtual 443	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   13: aload_1
    //   14: invokevirtual 345	com/google/gson/stream/JsonReader:peek	()Lcom/google/gson/stream/JsonToken;
    //   17: pop
    //   18: iconst_0
    //   19: istore 4
    //   21: aload_0
    //   22: aload_2
    //   23: invokestatic 475	com/google/gson/c/a:a	(Ljava/lang/reflect/Type;)Lcom/google/gson/c/a;
    //   26: invokevirtual 434	com/google/gson/f:a	(Lcom/google/gson/c/a;)Lcom/google/gson/s;
    //   29: aload_1
    //   30: invokevirtual 478	com/google/gson/s:b	(Lcom/google/gson/stream/JsonReader;)Ljava/lang/Object;
    //   33: astore_2
    //   34: aload_1
    //   35: iload_3
    //   36: invokevirtual 443	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   39: aload_2
    //   40: areturn
    //   41: astore_2
    //   42: goto +58 -> 100
    //   45: astore_2
    //   46: new 361	com/google/gson/JsonSyntaxException
    //   49: astore 5
    //   51: aload 5
    //   53: aload_2
    //   54: invokespecial 362	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/Throwable;)V
    //   57: aload 5
    //   59: athrow
    //   60: astore 5
    //   62: new 361	com/google/gson/JsonSyntaxException
    //   65: astore_2
    //   66: aload_2
    //   67: aload 5
    //   69: invokespecial 362	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/Throwable;)V
    //   72: aload_2
    //   73: athrow
    //   74: astore 5
    //   76: iload 4
    //   78: ifeq +10 -> 88
    //   81: aload_1
    //   82: iload_3
    //   83: invokevirtual 443	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   86: aconst_null
    //   87: areturn
    //   88: new 361	com/google/gson/JsonSyntaxException
    //   91: astore_2
    //   92: aload_2
    //   93: aload 5
    //   95: invokespecial 362	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/Throwable;)V
    //   98: aload_2
    //   99: athrow
    //   100: aload_1
    //   101: iload_3
    //   102: invokevirtual 443	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   105: aload_2
    //   106: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	107	0	this	f
    //   0	107	1	paramJsonReader	JsonReader
    //   0	107	2	paramType	Type
    //   4	98	3	bool	boolean
    //   6	71	4	n	int
    //   49	9	5	localJsonSyntaxException	JsonSyntaxException
    //   60	8	5	localIllegalStateException	IllegalStateException
    //   74	20	5	localEOFException	java.io.EOFException
    // Exception table:
    //   from	to	target	type
    //   13	18	41	finally
    //   21	34	41	finally
    //   46	60	41	finally
    //   62	74	41	finally
    //   88	100	41	finally
    //   13	18	45	java/io/IOException
    //   21	34	45	java/io/IOException
    //   13	18	60	java/lang/IllegalStateException
    //   21	34	60	java/lang/IllegalStateException
    //   13	18	74	java/io/EOFException
    //   21	34	74	java/io/EOFException
  }
  
  public <T> T a(Reader paramReader, Type paramType)
  {
    paramReader = a(paramReader);
    paramType = a(paramReader, paramType);
    a(paramType, paramReader);
    return paramType;
  }
  
  public <T> T a(String paramString, Class<T> paramClass)
  {
    paramString = a(paramString, paramClass);
    return (T)com.google.gson.b.i.a(paramClass).cast(paramString);
  }
  
  public <T> T a(String paramString, Type paramType)
  {
    if (paramString == null) {
      return null;
    }
    return (T)a(new StringReader(paramString), paramType);
  }
  
  public String a(l paraml)
  {
    StringWriter localStringWriter = new StringWriter();
    a(paraml, localStringWriter);
    return localStringWriter.toString();
  }
  
  public String a(Object paramObject)
  {
    if (paramObject == null) {
      return a(m.a);
    }
    return a(paramObject, paramObject.getClass());
  }
  
  public String a(Object paramObject, Type paramType)
  {
    StringWriter localStringWriter = new StringWriter();
    a(paramObject, paramType, localStringWriter);
    return localStringWriter.toString();
  }
  
  /* Error */
  public void a(l paraml, JsonWriter paramJsonWriter)
  {
    // Byte code:
    //   0: aload_2
    //   1: invokevirtual 536	com/google/gson/stream/JsonWriter:isLenient	()Z
    //   4: istore_3
    //   5: aload_2
    //   6: iconst_1
    //   7: invokevirtual 537	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   10: aload_2
    //   11: invokevirtual 540	com/google/gson/stream/JsonWriter:isHtmlSafe	()Z
    //   14: istore 4
    //   16: aload_2
    //   17: aload_0
    //   18: getfield 109	com/google/gson/f:i	Z
    //   21: invokevirtual 543	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   24: aload_2
    //   25: invokevirtual 546	com/google/gson/stream/JsonWriter:getSerializeNulls	()Z
    //   28: istore 5
    //   30: aload_2
    //   31: aload_0
    //   32: getfield 105	com/google/gson/f:h	Z
    //   35: invokevirtual 464	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   38: aload_1
    //   39: aload_2
    //   40: invokestatic 550	com/google/gson/b/j:a	(Lcom/google/gson/l;Lcom/google/gson/stream/JsonWriter;)V
    //   43: aload_2
    //   44: iload_3
    //   45: invokevirtual 537	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   48: aload_2
    //   49: iload 4
    //   51: invokevirtual 543	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   54: aload_2
    //   55: iload 5
    //   57: invokevirtual 464	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   60: return
    //   61: astore_1
    //   62: goto +18 -> 80
    //   65: astore_1
    //   66: new 353	com/google/gson/JsonIOException
    //   69: astore 6
    //   71: aload 6
    //   73: aload_1
    //   74: invokespecial 359	com/google/gson/JsonIOException:<init>	(Ljava/lang/Throwable;)V
    //   77: aload 6
    //   79: athrow
    //   80: aload_2
    //   81: iload_3
    //   82: invokevirtual 537	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   85: aload_2
    //   86: iload 4
    //   88: invokevirtual 543	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   91: aload_2
    //   92: iload 5
    //   94: invokevirtual 464	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   97: aload_1
    //   98: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	99	0	this	f
    //   0	99	1	paraml	l
    //   0	99	2	paramJsonWriter	JsonWriter
    //   4	78	3	bool1	boolean
    //   14	73	4	bool2	boolean
    //   28	65	5	bool3	boolean
    //   69	9	6	localJsonIOException	JsonIOException
    // Exception table:
    //   from	to	target	type
    //   38	43	61	finally
    //   66	80	61	finally
    //   38	43	65	java/io/IOException
  }
  
  public void a(l paraml, Appendable paramAppendable)
  {
    try
    {
      a(paraml, a(com.google.gson.b.j.a(paramAppendable)));
      return;
    }
    catch (IOException paraml)
    {
      throw new JsonIOException(paraml);
    }
  }
  
  /* Error */
  public void a(Object paramObject, Type paramType, JsonWriter paramJsonWriter)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_2
    //   2: invokestatic 475	com/google/gson/c/a:a	(Ljava/lang/reflect/Type;)Lcom/google/gson/c/a;
    //   5: invokevirtual 434	com/google/gson/f:a	(Lcom/google/gson/c/a;)Lcom/google/gson/s;
    //   8: astore_2
    //   9: aload_3
    //   10: invokevirtual 536	com/google/gson/stream/JsonWriter:isLenient	()Z
    //   13: istore 4
    //   15: aload_3
    //   16: iconst_1
    //   17: invokevirtual 537	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   20: aload_3
    //   21: invokevirtual 540	com/google/gson/stream/JsonWriter:isHtmlSafe	()Z
    //   24: istore 5
    //   26: aload_3
    //   27: aload_0
    //   28: getfield 109	com/google/gson/f:i	Z
    //   31: invokevirtual 543	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   34: aload_3
    //   35: invokevirtual 546	com/google/gson/stream/JsonWriter:getSerializeNulls	()Z
    //   38: istore 6
    //   40: aload_3
    //   41: aload_0
    //   42: getfield 105	com/google/gson/f:h	Z
    //   45: invokevirtual 464	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   48: aload_2
    //   49: aload_3
    //   50: aload_1
    //   51: invokevirtual 560	com/google/gson/s:a	(Lcom/google/gson/stream/JsonWriter;Ljava/lang/Object;)V
    //   54: aload_3
    //   55: iload 4
    //   57: invokevirtual 537	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   60: aload_3
    //   61: iload 5
    //   63: invokevirtual 543	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   66: aload_3
    //   67: iload 6
    //   69: invokevirtual 464	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   72: return
    //   73: astore_1
    //   74: goto +15 -> 89
    //   77: astore_1
    //   78: new 353	com/google/gson/JsonIOException
    //   81: astore_2
    //   82: aload_2
    //   83: aload_1
    //   84: invokespecial 359	com/google/gson/JsonIOException:<init>	(Ljava/lang/Throwable;)V
    //   87: aload_2
    //   88: athrow
    //   89: aload_3
    //   90: iload 4
    //   92: invokevirtual 537	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   95: aload_3
    //   96: iload 5
    //   98: invokevirtual 543	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   101: aload_3
    //   102: iload 6
    //   104: invokevirtual 464	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   107: aload_1
    //   108: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	109	0	this	f
    //   0	109	1	paramObject	Object
    //   0	109	2	paramType	Type
    //   0	109	3	paramJsonWriter	JsonWriter
    //   13	78	4	bool1	boolean
    //   24	73	5	bool2	boolean
    //   38	65	6	bool3	boolean
    // Exception table:
    //   from	to	target	type
    //   48	54	73	finally
    //   78	89	73	finally
    //   48	54	77	java/io/IOException
  }
  
  public void a(Object paramObject, Type paramType, Appendable paramAppendable)
  {
    try
    {
      a(paramObject, paramType, a(com.google.gson.b.j.a(paramAppendable)));
      return;
    }
    catch (IOException paramObject)
    {
      throw new JsonIOException((Throwable)paramObject);
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("{serializeNulls:");
    localStringBuilder.append(this.h);
    localStringBuilder.append(",factories:");
    localStringBuilder.append(this.d);
    localStringBuilder.append(",instanceCreators:");
    localStringBuilder.append(this.e);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
  
  static class a<T>
    extends s<T>
  {
    private s<T> a;
    
    public void a(s<T> params)
    {
      if (this.a == null)
      {
        this.a = params;
        return;
      }
      throw new AssertionError();
    }
    
    public void a(JsonWriter paramJsonWriter, T paramT)
    {
      s locals = this.a;
      if (locals != null)
      {
        locals.a(paramJsonWriter, paramT);
        return;
      }
      throw new IllegalStateException();
    }
    
    public T b(JsonReader paramJsonReader)
    {
      s locals = this.a;
      if (locals != null) {
        return (T)locals.b(paramJsonReader);
      }
      throw new IllegalStateException();
    }
  }
}


/* Location:              ~/com/google/gson/f.class
 *
 * Reversed by:           J
 */