package com.google.gson.b.a;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.a.c;
import com.google.gson.c.a;
import com.google.gson.i;
import com.google.gson.l;
import com.google.gson.m;
import com.google.gson.o;
import com.google.gson.s;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.t;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class n
{
  public static final s<String> A = new s()
  {
    public String a(JsonReader paramAnonymousJsonReader)
    {
      JsonToken localJsonToken = paramAnonymousJsonReader.peek();
      if (localJsonToken == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      if (localJsonToken == JsonToken.BOOLEAN) {
        return Boolean.toString(paramAnonymousJsonReader.nextBoolean());
      }
      return paramAnonymousJsonReader.nextString();
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, String paramAnonymousString)
    {
      paramAnonymousJsonWriter.value(paramAnonymousString);
    }
  };
  public static final s<BigDecimal> B = new s()
  {
    public BigDecimal a(JsonReader paramAnonymousJsonReader)
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      try
      {
        paramAnonymousJsonReader = new BigDecimal(paramAnonymousJsonReader.nextString());
        return paramAnonymousJsonReader;
      }
      catch (NumberFormatException paramAnonymousJsonReader)
      {
        throw new JsonSyntaxException(paramAnonymousJsonReader);
      }
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, BigDecimal paramAnonymousBigDecimal)
    {
      paramAnonymousJsonWriter.value(paramAnonymousBigDecimal);
    }
  };
  public static final s<BigInteger> C = new s()
  {
    public BigInteger a(JsonReader paramAnonymousJsonReader)
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      try
      {
        paramAnonymousJsonReader = new BigInteger(paramAnonymousJsonReader.nextString());
        return paramAnonymousJsonReader;
      }
      catch (NumberFormatException paramAnonymousJsonReader)
      {
        throw new JsonSyntaxException(paramAnonymousJsonReader);
      }
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, BigInteger paramAnonymousBigInteger)
    {
      paramAnonymousJsonWriter.value(paramAnonymousBigInteger);
    }
  };
  public static final t D = a(String.class, A);
  public static final s<StringBuilder> E = new s()
  {
    public StringBuilder a(JsonReader paramAnonymousJsonReader)
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      return new StringBuilder(paramAnonymousJsonReader.nextString());
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, StringBuilder paramAnonymousStringBuilder)
    {
      if (paramAnonymousStringBuilder == null) {
        paramAnonymousStringBuilder = null;
      } else {
        paramAnonymousStringBuilder = paramAnonymousStringBuilder.toString();
      }
      paramAnonymousJsonWriter.value(paramAnonymousStringBuilder);
    }
  };
  public static final t F = a(StringBuilder.class, E);
  public static final s<StringBuffer> G = new s()
  {
    public StringBuffer a(JsonReader paramAnonymousJsonReader)
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      return new StringBuffer(paramAnonymousJsonReader.nextString());
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, StringBuffer paramAnonymousStringBuffer)
    {
      if (paramAnonymousStringBuffer == null) {
        paramAnonymousStringBuffer = null;
      } else {
        paramAnonymousStringBuffer = paramAnonymousStringBuffer.toString();
      }
      paramAnonymousJsonWriter.value(paramAnonymousStringBuffer);
    }
  };
  public static final t H = a(StringBuffer.class, G);
  public static final s<URL> I = new s()
  {
    public URL a(JsonReader paramAnonymousJsonReader)
    {
      JsonToken localJsonToken1 = paramAnonymousJsonReader.peek();
      JsonToken localJsonToken2 = JsonToken.NULL;
      Object localObject = null;
      if (localJsonToken1 == localJsonToken2)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      paramAnonymousJsonReader = paramAnonymousJsonReader.nextString();
      if ("null".equals(paramAnonymousJsonReader)) {
        paramAnonymousJsonReader = (JsonReader)localObject;
      } else {
        paramAnonymousJsonReader = new URL(paramAnonymousJsonReader);
      }
      return paramAnonymousJsonReader;
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, URL paramAnonymousURL)
    {
      if (paramAnonymousURL == null) {
        paramAnonymousURL = null;
      } else {
        paramAnonymousURL = paramAnonymousURL.toExternalForm();
      }
      paramAnonymousJsonWriter.value(paramAnonymousURL);
    }
  };
  public static final t J = a(URL.class, I);
  public static final s<URI> K = new s()
  {
    public URI a(JsonReader paramAnonymousJsonReader)
    {
      JsonToken localJsonToken1 = paramAnonymousJsonReader.peek();
      JsonToken localJsonToken2 = JsonToken.NULL;
      Object localObject = null;
      if (localJsonToken1 == localJsonToken2)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      try
      {
        paramAnonymousJsonReader = paramAnonymousJsonReader.nextString();
        if ("null".equals(paramAnonymousJsonReader)) {
          paramAnonymousJsonReader = (JsonReader)localObject;
        } else {
          paramAnonymousJsonReader = new URI(paramAnonymousJsonReader);
        }
        return paramAnonymousJsonReader;
      }
      catch (URISyntaxException paramAnonymousJsonReader)
      {
        throw new JsonIOException(paramAnonymousJsonReader);
      }
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, URI paramAnonymousURI)
    {
      if (paramAnonymousURI == null) {
        paramAnonymousURI = null;
      } else {
        paramAnonymousURI = paramAnonymousURI.toASCIIString();
      }
      paramAnonymousJsonWriter.value(paramAnonymousURI);
    }
  };
  public static final t L = a(URI.class, K);
  public static final s<InetAddress> M = new s()
  {
    public InetAddress a(JsonReader paramAnonymousJsonReader)
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      return InetAddress.getByName(paramAnonymousJsonReader.nextString());
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, InetAddress paramAnonymousInetAddress)
    {
      if (paramAnonymousInetAddress == null) {
        paramAnonymousInetAddress = null;
      } else {
        paramAnonymousInetAddress = paramAnonymousInetAddress.getHostAddress();
      }
      paramAnonymousJsonWriter.value(paramAnonymousInetAddress);
    }
  };
  public static final t N = b(InetAddress.class, M);
  public static final s<UUID> O = new s()
  {
    public UUID a(JsonReader paramAnonymousJsonReader)
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      return UUID.fromString(paramAnonymousJsonReader.nextString());
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, UUID paramAnonymousUUID)
    {
      if (paramAnonymousUUID == null) {
        paramAnonymousUUID = null;
      } else {
        paramAnonymousUUID = paramAnonymousUUID.toString();
      }
      paramAnonymousJsonWriter.value(paramAnonymousUUID);
    }
  };
  public static final t P = a(UUID.class, O);
  public static final s<Currency> Q = new s()
  {
    public Currency a(JsonReader paramAnonymousJsonReader)
    {
      return Currency.getInstance(paramAnonymousJsonReader.nextString());
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, Currency paramAnonymousCurrency)
    {
      paramAnonymousJsonWriter.value(paramAnonymousCurrency.getCurrencyCode());
    }
  }.a();
  public static final t R = a(Currency.class, Q);
  public static final t S = new t()
  {
    public <T> s<T> a(com.google.gson.f paramAnonymousf, a<T> paramAnonymousa)
    {
      if (paramAnonymousa.a() != Timestamp.class) {
        return null;
      }
      new s()
      {
        public Timestamp a(JsonReader paramAnonymous2JsonReader)
        {
          paramAnonymous2JsonReader = (Date)this.a.b(paramAnonymous2JsonReader);
          if (paramAnonymous2JsonReader != null) {
            paramAnonymous2JsonReader = new Timestamp(paramAnonymous2JsonReader.getTime());
          } else {
            paramAnonymous2JsonReader = null;
          }
          return paramAnonymous2JsonReader;
        }
        
        public void a(JsonWriter paramAnonymous2JsonWriter, Timestamp paramAnonymous2Timestamp)
        {
          this.a.a(paramAnonymous2JsonWriter, paramAnonymous2Timestamp);
        }
      };
    }
  };
  public static final s<Calendar> T = new s()
  {
    public Calendar a(JsonReader paramAnonymousJsonReader)
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      paramAnonymousJsonReader.beginObject();
      int i = 0;
      int j = 0;
      int k = 0;
      int m = 0;
      int n = 0;
      int i1 = 0;
      while (paramAnonymousJsonReader.peek() != JsonToken.END_OBJECT)
      {
        String str = paramAnonymousJsonReader.nextName();
        int i2 = paramAnonymousJsonReader.nextInt();
        if ("year".equals(str)) {
          i = i2;
        } else if ("month".equals(str)) {
          j = i2;
        } else if ("dayOfMonth".equals(str)) {
          k = i2;
        } else if ("hourOfDay".equals(str)) {
          m = i2;
        } else if ("minute".equals(str)) {
          n = i2;
        } else if ("second".equals(str)) {
          i1 = i2;
        }
      }
      paramAnonymousJsonReader.endObject();
      return new GregorianCalendar(i, j, k, m, n, i1);
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, Calendar paramAnonymousCalendar)
    {
      if (paramAnonymousCalendar == null)
      {
        paramAnonymousJsonWriter.nullValue();
        return;
      }
      paramAnonymousJsonWriter.beginObject();
      paramAnonymousJsonWriter.name("year");
      paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(1));
      paramAnonymousJsonWriter.name("month");
      paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(2));
      paramAnonymousJsonWriter.name("dayOfMonth");
      paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(5));
      paramAnonymousJsonWriter.name("hourOfDay");
      paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(11));
      paramAnonymousJsonWriter.name("minute");
      paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(12));
      paramAnonymousJsonWriter.name("second");
      paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(13));
      paramAnonymousJsonWriter.endObject();
    }
  };
  public static final t U = b(Calendar.class, GregorianCalendar.class, T);
  public static final s<Locale> V = new s()
  {
    public Locale a(JsonReader paramAnonymousJsonReader)
    {
      Object localObject1 = paramAnonymousJsonReader.peek();
      Object localObject2 = JsonToken.NULL;
      String str = null;
      if (localObject1 == localObject2)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      localObject2 = new StringTokenizer(paramAnonymousJsonReader.nextString(), "_");
      if (((StringTokenizer)localObject2).hasMoreElements()) {
        paramAnonymousJsonReader = ((StringTokenizer)localObject2).nextToken();
      } else {
        paramAnonymousJsonReader = null;
      }
      if (((StringTokenizer)localObject2).hasMoreElements()) {
        localObject1 = ((StringTokenizer)localObject2).nextToken();
      } else {
        localObject1 = null;
      }
      if (((StringTokenizer)localObject2).hasMoreElements()) {
        str = ((StringTokenizer)localObject2).nextToken();
      }
      if ((localObject1 == null) && (str == null)) {
        return new Locale(paramAnonymousJsonReader);
      }
      if (str == null) {
        return new Locale(paramAnonymousJsonReader, (String)localObject1);
      }
      return new Locale(paramAnonymousJsonReader, (String)localObject1, str);
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, Locale paramAnonymousLocale)
    {
      if (paramAnonymousLocale == null) {
        paramAnonymousLocale = null;
      } else {
        paramAnonymousLocale = paramAnonymousLocale.toString();
      }
      paramAnonymousJsonWriter.value(paramAnonymousLocale);
    }
  };
  public static final t W = a(Locale.class, V);
  public static final s<l> X = new s()
  {
    public l a(JsonReader paramAnonymousJsonReader)
    {
      Object localObject;
      switch (n.29.a[paramAnonymousJsonReader.peek().ordinal()])
      {
      default: 
        throw new IllegalArgumentException();
      case 6: 
        localObject = new com.google.gson.n();
        paramAnonymousJsonReader.beginObject();
        while (paramAnonymousJsonReader.hasNext()) {
          ((com.google.gson.n)localObject).a(paramAnonymousJsonReader.nextName(), a(paramAnonymousJsonReader));
        }
        paramAnonymousJsonReader.endObject();
        return (l)localObject;
      case 5: 
        localObject = new i();
        paramAnonymousJsonReader.beginArray();
        while (paramAnonymousJsonReader.hasNext()) {
          ((i)localObject).a(a(paramAnonymousJsonReader));
        }
        paramAnonymousJsonReader.endArray();
        return (l)localObject;
      case 4: 
        paramAnonymousJsonReader.nextNull();
        return m.a;
      case 3: 
        return new o(paramAnonymousJsonReader.nextString());
      case 2: 
        return new o(Boolean.valueOf(paramAnonymousJsonReader.nextBoolean()));
      }
      return new o(new com.google.gson.b.f(paramAnonymousJsonReader.nextString()));
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, l paramAnonymousl)
    {
      if ((paramAnonymousl != null) && (!paramAnonymousl.j()))
      {
        if (paramAnonymousl.i())
        {
          paramAnonymousl = paramAnonymousl.m();
          if (paramAnonymousl.p()) {
            paramAnonymousJsonWriter.value(paramAnonymousl.a());
          } else if (paramAnonymousl.o()) {
            paramAnonymousJsonWriter.value(paramAnonymousl.f());
          } else {
            paramAnonymousJsonWriter.value(paramAnonymousl.b());
          }
        }
        else if (paramAnonymousl.g())
        {
          paramAnonymousJsonWriter.beginArray();
          paramAnonymousl = paramAnonymousl.l().iterator();
          while (paramAnonymousl.hasNext()) {
            a(paramAnonymousJsonWriter, (l)paramAnonymousl.next());
          }
          paramAnonymousJsonWriter.endArray();
        }
        else if (paramAnonymousl.h())
        {
          paramAnonymousJsonWriter.beginObject();
          paramAnonymousl = paramAnonymousl.k().o().iterator();
          while (paramAnonymousl.hasNext())
          {
            Map.Entry localEntry = (Map.Entry)paramAnonymousl.next();
            paramAnonymousJsonWriter.name((String)localEntry.getKey());
            a(paramAnonymousJsonWriter, (l)localEntry.getValue());
          }
          paramAnonymousJsonWriter.endObject();
        }
        else
        {
          paramAnonymousJsonWriter = new StringBuilder();
          paramAnonymousJsonWriter.append("Couldn't write ");
          paramAnonymousJsonWriter.append(paramAnonymousl.getClass());
          throw new IllegalArgumentException(paramAnonymousJsonWriter.toString());
        }
      }
      else {
        paramAnonymousJsonWriter.nullValue();
      }
    }
  };
  public static final t Y = b(l.class, X);
  public static final t Z = new t()
  {
    public <T> s<T> a(com.google.gson.f paramAnonymousf, a<T> paramAnonymousa)
    {
      paramAnonymousa = paramAnonymousa.a();
      if ((Enum.class.isAssignableFrom(paramAnonymousa)) && (paramAnonymousa != Enum.class))
      {
        paramAnonymousf = paramAnonymousa;
        if (!paramAnonymousa.isEnum()) {
          paramAnonymousf = paramAnonymousa.getSuperclass();
        }
        return new n.a(paramAnonymousf);
      }
      return null;
    }
  };
  public static final s<Class> a = new s()
  {
    public Class a(JsonReader paramAnonymousJsonReader)
    {
      throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, Class paramAnonymousClass)
    {
      paramAnonymousJsonWriter = new StringBuilder();
      paramAnonymousJsonWriter.append("Attempted to serialize java.lang.Class: ");
      paramAnonymousJsonWriter.append(paramAnonymousClass.getName());
      paramAnonymousJsonWriter.append(". Forgot to register a type adapter?");
      throw new UnsupportedOperationException(paramAnonymousJsonWriter.toString());
    }
  }.a();
  public static final t b = a(Class.class, a);
  public static final s<BitSet> c = new s()
  {
    public BitSet a(JsonReader paramAnonymousJsonReader)
    {
      BitSet localBitSet = new BitSet();
      paramAnonymousJsonReader.beginArray();
      Object localObject = paramAnonymousJsonReader.peek();
      int i = 0;
      while (localObject != JsonToken.END_ARRAY)
      {
        int j = n.29.a[localObject.ordinal()];
        boolean bool = true;
        switch (j)
        {
        default: 
          paramAnonymousJsonReader = new StringBuilder();
          paramAnonymousJsonReader.append("Invalid bitset value type: ");
          paramAnonymousJsonReader.append(localObject);
          throw new JsonSyntaxException(paramAnonymousJsonReader.toString());
        case 3: 
          localObject = paramAnonymousJsonReader.nextString();
          try
          {
            j = Integer.parseInt((String)localObject);
            if (j == 0) {
              bool = false;
            }
          }
          catch (NumberFormatException paramAnonymousJsonReader)
          {
            paramAnonymousJsonReader = new StringBuilder();
            paramAnonymousJsonReader.append("Error: Expecting: bitset number value (1, 0), Found: ");
            paramAnonymousJsonReader.append((String)localObject);
            throw new JsonSyntaxException(paramAnonymousJsonReader.toString());
          }
        case 2: 
          bool = paramAnonymousJsonReader.nextBoolean();
          break;
        case 1: 
          if (paramAnonymousJsonReader.nextInt() == 0) {
            bool = false;
          }
          break;
        }
        if (bool) {
          localBitSet.set(i);
        }
        i++;
        localObject = paramAnonymousJsonReader.peek();
      }
      paramAnonymousJsonReader.endArray();
      return localBitSet;
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, BitSet paramAnonymousBitSet)
    {
      paramAnonymousJsonWriter.beginArray();
      int i = paramAnonymousBitSet.length();
      for (int j = 0; j < i; j++) {
        paramAnonymousJsonWriter.value(paramAnonymousBitSet.get(j));
      }
      paramAnonymousJsonWriter.endArray();
    }
  }.a();
  public static final t d = a(BitSet.class, c);
  public static final s<Boolean> e = new s()
  {
    public Boolean a(JsonReader paramAnonymousJsonReader)
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      if (paramAnonymousJsonReader.peek() == JsonToken.STRING) {
        return Boolean.valueOf(Boolean.parseBoolean(paramAnonymousJsonReader.nextString()));
      }
      return Boolean.valueOf(paramAnonymousJsonReader.nextBoolean());
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, Boolean paramAnonymousBoolean)
    {
      paramAnonymousJsonWriter.value(paramAnonymousBoolean);
    }
  };
  public static final s<Boolean> f = new s()
  {
    public Boolean a(JsonReader paramAnonymousJsonReader)
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      return Boolean.valueOf(paramAnonymousJsonReader.nextString());
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, Boolean paramAnonymousBoolean)
    {
      if (paramAnonymousBoolean == null) {
        paramAnonymousBoolean = "null";
      } else {
        paramAnonymousBoolean = paramAnonymousBoolean.toString();
      }
      paramAnonymousJsonWriter.value(paramAnonymousBoolean);
    }
  };
  public static final t g = a(Boolean.TYPE, Boolean.class, e);
  public static final s<Number> h = new s()
  {
    public Number a(JsonReader paramAnonymousJsonReader)
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      try
      {
        byte b = (byte)paramAnonymousJsonReader.nextInt();
        return Byte.valueOf(b);
      }
      catch (NumberFormatException paramAnonymousJsonReader)
      {
        throw new JsonSyntaxException(paramAnonymousJsonReader);
      }
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
    {
      paramAnonymousJsonWriter.value(paramAnonymousNumber);
    }
  };
  public static final t i = a(Byte.TYPE, Byte.class, h);
  public static final s<Number> j = new s()
  {
    public Number a(JsonReader paramAnonymousJsonReader)
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      try
      {
        short s = (short)paramAnonymousJsonReader.nextInt();
        return Short.valueOf(s);
      }
      catch (NumberFormatException paramAnonymousJsonReader)
      {
        throw new JsonSyntaxException(paramAnonymousJsonReader);
      }
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
    {
      paramAnonymousJsonWriter.value(paramAnonymousNumber);
    }
  };
  public static final t k = a(Short.TYPE, Short.class, j);
  public static final s<Number> l = new s()
  {
    public Number a(JsonReader paramAnonymousJsonReader)
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      try
      {
        int i = paramAnonymousJsonReader.nextInt();
        return Integer.valueOf(i);
      }
      catch (NumberFormatException paramAnonymousJsonReader)
      {
        throw new JsonSyntaxException(paramAnonymousJsonReader);
      }
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
    {
      paramAnonymousJsonWriter.value(paramAnonymousNumber);
    }
  };
  public static final t m = a(Integer.TYPE, Integer.class, l);
  public static final s<AtomicInteger> n = new s()
  {
    public AtomicInteger a(JsonReader paramAnonymousJsonReader)
    {
      try
      {
        paramAnonymousJsonReader = new AtomicInteger(paramAnonymousJsonReader.nextInt());
        return paramAnonymousJsonReader;
      }
      catch (NumberFormatException paramAnonymousJsonReader)
      {
        throw new JsonSyntaxException(paramAnonymousJsonReader);
      }
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, AtomicInteger paramAnonymousAtomicInteger)
    {
      paramAnonymousJsonWriter.value(paramAnonymousAtomicInteger.get());
    }
  }.a();
  public static final t o = a(AtomicInteger.class, n);
  public static final s<AtomicBoolean> p = new s()
  {
    public AtomicBoolean a(JsonReader paramAnonymousJsonReader)
    {
      return new AtomicBoolean(paramAnonymousJsonReader.nextBoolean());
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, AtomicBoolean paramAnonymousAtomicBoolean)
    {
      paramAnonymousJsonWriter.value(paramAnonymousAtomicBoolean.get());
    }
  }.a();
  public static final t q = a(AtomicBoolean.class, p);
  public static final s<AtomicIntegerArray> r = new s()
  {
    public AtomicIntegerArray a(JsonReader paramAnonymousJsonReader)
    {
      ArrayList localArrayList = new ArrayList();
      paramAnonymousJsonReader.beginArray();
      while (paramAnonymousJsonReader.hasNext()) {
        try
        {
          localArrayList.add(Integer.valueOf(paramAnonymousJsonReader.nextInt()));
        }
        catch (NumberFormatException paramAnonymousJsonReader)
        {
          throw new JsonSyntaxException(paramAnonymousJsonReader);
        }
      }
      paramAnonymousJsonReader.endArray();
      int i = localArrayList.size();
      paramAnonymousJsonReader = new AtomicIntegerArray(i);
      for (int j = 0; j < i; j++) {
        paramAnonymousJsonReader.set(j, ((Integer)localArrayList.get(j)).intValue());
      }
      return paramAnonymousJsonReader;
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, AtomicIntegerArray paramAnonymousAtomicIntegerArray)
    {
      paramAnonymousJsonWriter.beginArray();
      int i = paramAnonymousAtomicIntegerArray.length();
      for (int j = 0; j < i; j++) {
        paramAnonymousJsonWriter.value(paramAnonymousAtomicIntegerArray.get(j));
      }
      paramAnonymousJsonWriter.endArray();
    }
  }.a();
  public static final t s = a(AtomicIntegerArray.class, r);
  public static final s<Number> t = new s()
  {
    public Number a(JsonReader paramAnonymousJsonReader)
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      try
      {
        long l = paramAnonymousJsonReader.nextLong();
        return Long.valueOf(l);
      }
      catch (NumberFormatException paramAnonymousJsonReader)
      {
        throw new JsonSyntaxException(paramAnonymousJsonReader);
      }
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
    {
      paramAnonymousJsonWriter.value(paramAnonymousNumber);
    }
  };
  public static final s<Number> u = new s()
  {
    public Number a(JsonReader paramAnonymousJsonReader)
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
      paramAnonymousJsonWriter.value(paramAnonymousNumber);
    }
  };
  public static final s<Number> v = new s()
  {
    public Number a(JsonReader paramAnonymousJsonReader)
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
      paramAnonymousJsonWriter.value(paramAnonymousNumber);
    }
  };
  public static final s<Number> w = new s()
  {
    public Number a(JsonReader paramAnonymousJsonReader)
    {
      JsonToken localJsonToken = paramAnonymousJsonReader.peek();
      int i = n.29.a[localJsonToken.ordinal()];
      if (i != 1) {
        switch (i)
        {
        default: 
          paramAnonymousJsonReader = new StringBuilder();
          paramAnonymousJsonReader.append("Expecting number, got: ");
          paramAnonymousJsonReader.append(localJsonToken);
          throw new JsonSyntaxException(paramAnonymousJsonReader.toString());
        case 4: 
          paramAnonymousJsonReader.nextNull();
          return null;
        }
      }
      return new com.google.gson.b.f(paramAnonymousJsonReader.nextString());
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
    {
      paramAnonymousJsonWriter.value(paramAnonymousNumber);
    }
  };
  public static final t x = a(Number.class, w);
  public static final s<Character> y = new s()
  {
    public Character a(JsonReader paramAnonymousJsonReader)
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      paramAnonymousJsonReader = paramAnonymousJsonReader.nextString();
      if (paramAnonymousJsonReader.length() == 1) {
        return Character.valueOf(paramAnonymousJsonReader.charAt(0));
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Expecting character, got: ");
      localStringBuilder.append(paramAnonymousJsonReader);
      throw new JsonSyntaxException(localStringBuilder.toString());
    }
    
    public void a(JsonWriter paramAnonymousJsonWriter, Character paramAnonymousCharacter)
    {
      if (paramAnonymousCharacter == null) {
        paramAnonymousCharacter = null;
      } else {
        paramAnonymousCharacter = String.valueOf(paramAnonymousCharacter);
      }
      paramAnonymousJsonWriter.value(paramAnonymousCharacter);
    }
  };
  public static final t z = a(Character.TYPE, Character.class, y);
  
  public static <TT> t a(Class<TT> paramClass, final s<TT> params)
  {
    new t()
    {
      public <T> s<T> a(com.google.gson.f paramAnonymousf, a<T> paramAnonymousa)
      {
        if (paramAnonymousa.a() == this.a) {
          paramAnonymousf = params;
        } else {
          paramAnonymousf = null;
        }
        return paramAnonymousf;
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Factory[type=");
        localStringBuilder.append(this.a.getName());
        localStringBuilder.append(",adapter=");
        localStringBuilder.append(params);
        localStringBuilder.append("]");
        return localStringBuilder.toString();
      }
    };
  }
  
  public static <TT> t a(Class<TT> paramClass1, final Class<TT> paramClass2, final s<? super TT> params)
  {
    new t()
    {
      public <T> s<T> a(com.google.gson.f paramAnonymousf, a<T> paramAnonymousa)
      {
        paramAnonymousf = paramAnonymousa.a();
        if ((paramAnonymousf != this.a) && (paramAnonymousf != paramClass2)) {
          paramAnonymousf = null;
        } else {
          paramAnonymousf = params;
        }
        return paramAnonymousf;
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Factory[type=");
        localStringBuilder.append(paramClass2.getName());
        localStringBuilder.append("+");
        localStringBuilder.append(this.a.getName());
        localStringBuilder.append(",adapter=");
        localStringBuilder.append(params);
        localStringBuilder.append("]");
        return localStringBuilder.toString();
      }
    };
  }
  
  public static <T1> t b(Class<T1> paramClass, final s<T1> params)
  {
    new t()
    {
      public <T2> s<T2> a(final com.google.gson.f paramAnonymousf, a<T2> paramAnonymousa)
      {
        paramAnonymousf = paramAnonymousa.a();
        if (!this.a.isAssignableFrom(paramAnonymousf)) {
          return null;
        }
        new s()
        {
          public void a(JsonWriter paramAnonymous2JsonWriter, T1 paramAnonymous2T1)
          {
            n.28.this.b.a(paramAnonymous2JsonWriter, paramAnonymous2T1);
          }
          
          public T1 b(JsonReader paramAnonymous2JsonReader)
          {
            Object localObject = n.28.this.b.b(paramAnonymous2JsonReader);
            if ((localObject != null) && (!paramAnonymousf.isInstance(localObject)))
            {
              paramAnonymous2JsonReader = new StringBuilder();
              paramAnonymous2JsonReader.append("Expected a ");
              paramAnonymous2JsonReader.append(paramAnonymousf.getName());
              paramAnonymous2JsonReader.append(" but was ");
              paramAnonymous2JsonReader.append(localObject.getClass().getName());
              throw new JsonSyntaxException(paramAnonymous2JsonReader.toString());
            }
            return (T1)localObject;
          }
        };
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Factory[typeHierarchy=");
        localStringBuilder.append(this.a.getName());
        localStringBuilder.append(",adapter=");
        localStringBuilder.append(params);
        localStringBuilder.append("]");
        return localStringBuilder.toString();
      }
    };
  }
  
  public static <TT> t b(Class<TT> paramClass, final Class<? extends TT> paramClass1, final s<? super TT> params)
  {
    new t()
    {
      public <T> s<T> a(com.google.gson.f paramAnonymousf, a<T> paramAnonymousa)
      {
        paramAnonymousf = paramAnonymousa.a();
        if ((paramAnonymousf != this.a) && (paramAnonymousf != paramClass1)) {
          paramAnonymousf = null;
        } else {
          paramAnonymousf = params;
        }
        return paramAnonymousf;
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Factory[type=");
        localStringBuilder.append(this.a.getName());
        localStringBuilder.append("+");
        localStringBuilder.append(paramClass1.getName());
        localStringBuilder.append(",adapter=");
        localStringBuilder.append(params);
        localStringBuilder.append("]");
        return localStringBuilder.toString();
      }
    };
  }
  
  private static final class a<T extends Enum<T>>
    extends s<T>
  {
    private final Map<String, T> a = new HashMap();
    private final Map<T, String> b = new HashMap();
    
    public a(Class<T> paramClass)
    {
      try
      {
        for (Enum localEnum : (Enum[])paramClass.getEnumConstants())
        {
          Object localObject1 = localEnum.name();
          Object localObject2 = (c)paramClass.getField((String)localObject1).getAnnotation(c.class);
          if (localObject2 != null)
          {
            String str = ((c)localObject2).a();
            localObject2 = ((c)localObject2).b();
            int k = localObject2.length;
            for (int m = 0;; m++)
            {
              localObject1 = str;
              if (m >= k) {
                break;
              }
              localObject1 = localObject2[m];
              this.a.put(localObject1, localEnum);
            }
          }
          this.a.put(localObject1, localEnum);
          this.b.put(localEnum, localObject1);
        }
        return;
      }
      catch (NoSuchFieldException paramClass)
      {
        throw new AssertionError(paramClass);
      }
    }
    
    public T a(JsonReader paramJsonReader)
    {
      if (paramJsonReader.peek() == JsonToken.NULL)
      {
        paramJsonReader.nextNull();
        return null;
      }
      return (Enum)this.a.get(paramJsonReader.nextString());
    }
    
    public void a(JsonWriter paramJsonWriter, T paramT)
    {
      if (paramT == null) {
        paramT = null;
      } else {
        paramT = (String)this.b.get(paramT);
      }
      paramJsonWriter.value(paramT);
    }
  }
}


/* Location:              ~/com/google/gson/b/a/n.class
 *
 * Reversed by:           J
 */