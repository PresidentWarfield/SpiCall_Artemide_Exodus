package com.google.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Locale;

final class a
  extends s<java.util.Date>
{
  private final Class<? extends java.util.Date> a;
  private final DateFormat b;
  private final DateFormat c;
  
  public a(Class<? extends java.util.Date> paramClass, int paramInt1, int paramInt2)
  {
    this(paramClass, DateFormat.getDateTimeInstance(paramInt1, paramInt2, Locale.US), DateFormat.getDateTimeInstance(paramInt1, paramInt2));
  }
  
  a(Class<? extends java.util.Date> paramClass, String paramString)
  {
    this(paramClass, new SimpleDateFormat(paramString, Locale.US), new SimpleDateFormat(paramString));
  }
  
  a(Class<? extends java.util.Date> paramClass, DateFormat paramDateFormat1, DateFormat paramDateFormat2)
  {
    if ((paramClass != java.util.Date.class) && (paramClass != java.sql.Date.class) && (paramClass != Timestamp.class))
    {
      paramDateFormat1 = new StringBuilder();
      paramDateFormat1.append("Date type must be one of ");
      paramDateFormat1.append(java.util.Date.class);
      paramDateFormat1.append(", ");
      paramDateFormat1.append(Timestamp.class);
      paramDateFormat1.append(", or ");
      paramDateFormat1.append(java.sql.Date.class);
      paramDateFormat1.append(" but was ");
      paramDateFormat1.append(paramClass);
      throw new IllegalArgumentException(paramDateFormat1.toString());
    }
    this.a = paramClass;
    this.b = paramDateFormat1;
    this.c = paramDateFormat2;
  }
  
  private java.util.Date a(String paramString)
  {
    try
    {
      synchronized (this.c)
      {
        java.util.Date localDate1 = this.c.parse(paramString);
        return localDate1;
      }
    }
    catch (ParseException localParseException1)
    {
      try
      {
        java.util.Date localDate2 = this.b.parse(paramString);
        return localDate2;
      }
      catch (ParseException localParseException2)
      {
        try
        {
          Object localObject = new java/text/ParsePosition;
          ((ParsePosition)localObject).<init>(0);
          localObject = com.google.gson.b.a.a.a.a(paramString, (ParsePosition)localObject);
          return (java.util.Date)localObject;
        }
        catch (ParseException localParseException3)
        {
          JsonSyntaxException localJsonSyntaxException = new com/google/gson/JsonSyntaxException;
          localJsonSyntaxException.<init>(paramString, localParseException3);
          throw localJsonSyntaxException;
        }
      }
    }
  }
  
  public java.util.Date a(JsonReader paramJsonReader)
  {
    if (paramJsonReader.peek() == JsonToken.STRING)
    {
      java.util.Date localDate = a(paramJsonReader.nextString());
      paramJsonReader = this.a;
      if (paramJsonReader == java.util.Date.class) {
        return localDate;
      }
      if (paramJsonReader == Timestamp.class) {
        return new Timestamp(localDate.getTime());
      }
      if (paramJsonReader == java.sql.Date.class) {
        return new java.sql.Date(localDate.getTime());
      }
      throw new AssertionError();
    }
    throw new JsonParseException("The date should be a string value");
  }
  
  public void a(JsonWriter paramJsonWriter, java.util.Date paramDate)
  {
    synchronized (this.c)
    {
      paramJsonWriter.value(this.b.format(paramDate));
      return;
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DefaultDateTypeAdapter");
    localStringBuilder.append('(');
    localStringBuilder.append(this.c.getClass().getSimpleName());
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
}


/* Location:              ~/com/google/gson/a.class
 *
 * Reversed by:           J
 */