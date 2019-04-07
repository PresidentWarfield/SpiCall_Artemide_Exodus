package org.apache.commons.net.ftp;

import java.text.DateFormatSymbols;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class d
{
  private static final Map<String, Object> i = new TreeMap();
  private final String a;
  private String b = null;
  private String c = null;
  private boolean d = true;
  private String e = null;
  private String f = null;
  private String g = null;
  private boolean h = false;
  
  static
  {
    i.put("en", Locale.ENGLISH);
    i.put("de", Locale.GERMAN);
    i.put("it", Locale.ITALIAN);
    i.put("es", new Locale("es", "", ""));
    i.put("pt", new Locale("pt", "", ""));
    i.put("da", new Locale("da", "", ""));
    i.put("sv", new Locale("sv", "", ""));
    i.put("no", new Locale("no", "", ""));
    i.put("nl", new Locale("nl", "", ""));
    i.put("ro", new Locale("ro", "", ""));
    i.put("sq", new Locale("sq", "", ""));
    i.put("sh", new Locale("sh", "", ""));
    i.put("sk", new Locale("sk", "", ""));
    i.put("sl", new Locale("sl", "", ""));
    i.put("fr", "jan|fév|mar|avr|mai|jun|jui|aoû|sep|oct|nov|déc");
  }
  
  public d()
  {
    this("UNIX");
  }
  
  public d(String paramString)
  {
    this.a = paramString;
  }
  
  public d(String paramString1, String paramString2, String paramString3)
  {
    this(paramString1);
  }
  
  d(String paramString, d paramd)
  {
    this.a = paramString;
    this.b = paramd.b;
    this.d = paramd.d;
    this.c = paramd.c;
    this.h = paramd.h;
    this.e = paramd.e;
    this.g = paramd.g;
    this.f = paramd.f;
  }
  
  public d(d paramd)
  {
    this.a = paramd.a;
    this.b = paramd.b;
    this.d = paramd.d;
    this.c = paramd.c;
    this.h = paramd.h;
    this.e = paramd.e;
    this.g = paramd.g;
    this.f = paramd.f;
  }
  
  public static DateFormatSymbols c(String paramString)
  {
    paramString = i.get(paramString);
    if (paramString != null)
    {
      if ((paramString instanceof Locale)) {
        return new DateFormatSymbols((Locale)paramString);
      }
      if ((paramString instanceof String)) {
        return d((String)paramString);
      }
    }
    return new DateFormatSymbols(Locale.US);
  }
  
  public static DateFormatSymbols d(String paramString)
  {
    paramString = e(paramString);
    DateFormatSymbols localDateFormatSymbols = new DateFormatSymbols(Locale.US);
    localDateFormatSymbols.setShortMonths(paramString);
    return localDateFormatSymbols;
  }
  
  private static String[] e(String paramString)
  {
    paramString = new StringTokenizer(paramString, "|");
    if (12 == paramString.countTokens())
    {
      String[] arrayOfString = new String[13];
      for (int j = 0; paramString.hasMoreTokens(); j++) {
        arrayOfString[j] = paramString.nextToken();
      }
      arrayOfString[j] = "";
      return arrayOfString;
    }
    throw new IllegalArgumentException("expecting a pipe-delimited string containing 12 tokens");
  }
  
  public String a()
  {
    return this.a;
  }
  
  public void a(String paramString)
  {
    this.b = paramString;
  }
  
  public String b()
  {
    return this.b;
  }
  
  public void b(String paramString)
  {
    this.c = paramString;
  }
  
  public String c()
  {
    return this.c;
  }
  
  public String d()
  {
    return this.g;
  }
  
  public String e()
  {
    return this.f;
  }
  
  public String f()
  {
    return this.e;
  }
  
  public boolean g()
  {
    return this.d;
  }
  
  public boolean h()
  {
    return this.h;
  }
}


/* Location:              ~/org/apache/commons/net/ftp/d.class
 *
 * Reversed by:           J
 */