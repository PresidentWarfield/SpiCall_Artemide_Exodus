package com.google.gson.b.a;

import com.google.gson.c.a;
import com.google.gson.f;
import com.google.gson.s;
import com.google.gson.stream.JsonWriter;
import com.google.gson.t;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class j
  extends s<Date>
{
  public static final t a = new t()
  {
    public <T> s<T> a(f paramAnonymousf, a<T> paramAnonymousa)
    {
      if (paramAnonymousa.a() == Date.class) {
        paramAnonymousf = new j();
      } else {
        paramAnonymousf = null;
      }
      return paramAnonymousf;
    }
  };
  private final DateFormat b = new SimpleDateFormat("MMM d, yyyy");
  
  /* Error */
  public Date a(com.google.gson.stream.JsonReader paramJsonReader)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokevirtual 38	com/google/gson/stream/JsonReader:peek	()Lcom/google/gson/stream/JsonToken;
    //   6: getstatic 44	com/google/gson/stream/JsonToken:NULL	Lcom/google/gson/stream/JsonToken;
    //   9: if_acmpne +11 -> 20
    //   12: aload_1
    //   13: invokevirtual 47	com/google/gson/stream/JsonReader:nextNull	()V
    //   16: aload_0
    //   17: monitorexit
    //   18: aconst_null
    //   19: areturn
    //   20: new 49	java/sql/Date
    //   23: dup
    //   24: aload_0
    //   25: getfield 29	com/google/gson/b/a/j:b	Ljava/text/DateFormat;
    //   28: aload_1
    //   29: invokevirtual 53	com/google/gson/stream/JsonReader:nextString	()Ljava/lang/String;
    //   32: invokevirtual 59	java/text/DateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   35: invokevirtual 65	java/util/Date:getTime	()J
    //   38: invokespecial 68	java/sql/Date:<init>	(J)V
    //   41: astore_1
    //   42: aload_0
    //   43: monitorexit
    //   44: aload_1
    //   45: areturn
    //   46: astore_2
    //   47: new 70	com/google/gson/JsonSyntaxException
    //   50: astore_1
    //   51: aload_1
    //   52: aload_2
    //   53: invokespecial 73	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/Throwable;)V
    //   56: aload_1
    //   57: athrow
    //   58: astore_1
    //   59: aload_0
    //   60: monitorexit
    //   61: aload_1
    //   62: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	63	0	this	j
    //   0	63	1	paramJsonReader	com.google.gson.stream.JsonReader
    //   46	7	2	localParseException	java.text.ParseException
    // Exception table:
    //   from	to	target	type
    //   20	42	46	java/text/ParseException
    //   2	16	58	finally
    //   20	42	58	finally
    //   47	58	58	finally
  }
  
  public void a(JsonWriter paramJsonWriter, Date paramDate)
  {
    if (paramDate == null) {
      paramDate = null;
    }
    try
    {
      paramDate = this.b.format(paramDate);
      paramJsonWriter.value(paramDate);
      return;
    }
    finally {}
  }
}


/* Location:              ~/com/google/gson/b/a/j.class
 *
 * Reversed by:           J
 */