package com.google.gson.b.a;

import com.google.gson.c.a;
import com.google.gson.f;
import com.google.gson.s;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.t;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public final class c
  extends s<Date>
{
  public static final t a = new t()
  {
    public <T> s<T> a(f paramAnonymousf, a<T> paramAnonymousa)
    {
      if (paramAnonymousa.a() == Date.class) {
        paramAnonymousf = new c();
      } else {
        paramAnonymousf = null;
      }
      return paramAnonymousf;
    }
  };
  private final DateFormat b = DateFormat.getDateTimeInstance(2, 2, Locale.US);
  private final DateFormat c = DateFormat.getDateTimeInstance(2, 2);
  
  /* Error */
  private Date a(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 40	com/google/gson/b/a/c:c	Ljava/text/DateFormat;
    //   6: aload_1
    //   7: invokevirtual 46	java/text/DateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   10: astore_2
    //   11: aload_0
    //   12: monitorexit
    //   13: aload_2
    //   14: areturn
    //   15: astore_1
    //   16: goto +50 -> 66
    //   19: astore_2
    //   20: aload_0
    //   21: getfield 35	com/google/gson/b/a/c:b	Ljava/text/DateFormat;
    //   24: aload_1
    //   25: invokevirtual 46	java/text/DateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   28: astore_2
    //   29: aload_0
    //   30: monitorexit
    //   31: aload_2
    //   32: areturn
    //   33: astore_2
    //   34: new 48	java/text/ParsePosition
    //   37: astore_2
    //   38: aload_2
    //   39: iconst_0
    //   40: invokespecial 51	java/text/ParsePosition:<init>	(I)V
    //   43: aload_1
    //   44: aload_2
    //   45: invokestatic 56	com/google/gson/b/a/a/a:a	(Ljava/lang/String;Ljava/text/ParsePosition;)Ljava/util/Date;
    //   48: astore_2
    //   49: aload_0
    //   50: monitorexit
    //   51: aload_2
    //   52: areturn
    //   53: astore_3
    //   54: new 58	com/google/gson/JsonSyntaxException
    //   57: astore_2
    //   58: aload_2
    //   59: aload_1
    //   60: aload_3
    //   61: invokespecial 61	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   64: aload_2
    //   65: athrow
    //   66: aload_0
    //   67: monitorexit
    //   68: aload_1
    //   69: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	70	0	this	c
    //   0	70	1	paramString	String
    //   10	4	2	localDate1	Date
    //   19	1	2	localParseException1	java.text.ParseException
    //   28	4	2	localDate2	Date
    //   33	1	2	localParseException2	java.text.ParseException
    //   37	28	2	localObject	Object
    //   53	8	3	localParseException3	java.text.ParseException
    // Exception table:
    //   from	to	target	type
    //   2	11	15	finally
    //   20	29	15	finally
    //   34	49	15	finally
    //   54	66	15	finally
    //   2	11	19	java/text/ParseException
    //   20	29	33	java/text/ParseException
    //   34	49	53	java/text/ParseException
  }
  
  public Date a(JsonReader paramJsonReader)
  {
    if (paramJsonReader.peek() == JsonToken.NULL)
    {
      paramJsonReader.nextNull();
      return null;
    }
    return a(paramJsonReader.nextString());
  }
  
  public void a(JsonWriter paramJsonWriter, Date paramDate)
  {
    if (paramDate == null) {}
    try
    {
      paramJsonWriter.nullValue();
      return;
    }
    finally {}
    paramJsonWriter.value(this.b.format(paramDate));
  }
}


/* Location:              ~/com/google/gson/b/a/c.class
 *
 * Reversed by:           J
 */