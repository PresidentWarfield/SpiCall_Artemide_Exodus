package okhttp3.internal.http;

import a.c;
import a.f;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import okhttp3.Challenge;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;

public final class HttpHeaders
{
  private static final f QUOTED_STRING_DELIMITERS = f.a("\"\\");
  private static final f TOKEN_DELIMITERS = f.a("\t ,=");
  
  public static long contentLength(Headers paramHeaders)
  {
    return stringToLong(paramHeaders.get("Content-Length"));
  }
  
  public static long contentLength(Response paramResponse)
  {
    return contentLength(paramResponse.headers());
  }
  
  public static boolean hasBody(Response paramResponse)
  {
    if (paramResponse.request().method().equals("HEAD")) {
      return false;
    }
    int i = paramResponse.code();
    if (((i < 100) || (i >= 200)) && (i != 204) && (i != 304)) {
      return true;
    }
    return (contentLength(paramResponse) != -1L) || ("chunked".equalsIgnoreCase(paramResponse.header("Transfer-Encoding")));
  }
  
  public static boolean hasVaryAll(Headers paramHeaders)
  {
    return varyFields(paramHeaders).contains("*");
  }
  
  public static boolean hasVaryAll(Response paramResponse)
  {
    return hasVaryAll(paramResponse.headers());
  }
  
  private static void parseChallengeHeader(List<Challenge> paramList, c paramc)
  {
    Object localObject2;
    String str;
    int i;
    for (Object localObject1 = null;; localObject1 = null)
    {
      localObject2 = localObject1;
      if (localObject1 == null)
      {
        skipWhitespaceAndCommas(paramc);
        localObject1 = readToken(paramc);
        localObject2 = localObject1;
        if (localObject1 == null) {
          return;
        }
      }
      boolean bool1 = skipWhitespaceAndCommas(paramc);
      str = readToken(paramc);
      if (str == null)
      {
        if (!paramc.e()) {
          return;
        }
        paramList.add(new Challenge((String)localObject2, Collections.emptyMap()));
        return;
      }
      i = skipAll(paramc, (byte)61);
      boolean bool2 = skipWhitespaceAndCommas(paramc);
      if ((bool1) || ((!bool2) && (!paramc.e()))) {
        break;
      }
      localObject3 = (String)null;
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(str);
      ((StringBuilder)localObject1).append(repeat('=', i));
      paramList.add(new Challenge((String)localObject2, Collections.singletonMap(localObject3, ((StringBuilder)localObject1).toString())));
    }
    Object localObject3 = new LinkedHashMap();
    i += skipAll(paramc, (byte)61);
    for (;;)
    {
      localObject1 = str;
      if (str == null)
      {
        localObject1 = readToken(paramc);
        if (!skipWhitespaceAndCommas(paramc)) {
          i = skipAll(paramc, (byte)61);
        }
      }
      else
      {
        if (i != 0) {
          break label238;
        }
      }
      paramList.add(new Challenge((String)localObject2, (Map)localObject3));
      break;
      label238:
      if (i > 1) {
        return;
      }
      if (skipWhitespaceAndCommas(paramc)) {
        return;
      }
      if ((!paramc.e()) && (paramc.c(0L) == 34)) {
        str = readQuotedString(paramc);
      } else {
        str = readToken(paramc);
      }
      if (str == null) {
        return;
      }
      if ((String)((Map)localObject3).put(localObject1, str) != null) {
        return;
      }
      if ((!skipWhitespaceAndCommas(paramc)) && (!paramc.e())) {
        return;
      }
      str = null;
    }
  }
  
  public static List<Challenge> parseChallenges(Headers paramHeaders, String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramHeaders.size(); i++) {
      if (paramString.equalsIgnoreCase(paramHeaders.name(i))) {
        parseChallengeHeader(localArrayList, new c().a(paramHeaders.value(i)));
      }
    }
    return localArrayList;
  }
  
  public static int parseSeconds(String paramString, int paramInt)
  {
    try
    {
      long l = Long.parseLong(paramString);
      if (l > 2147483647L) {
        return Integer.MAX_VALUE;
      }
      if (l < 0L) {
        return 0;
      }
      return (int)l;
    }
    catch (NumberFormatException paramString) {}
    return paramInt;
  }
  
  private static String readQuotedString(c paramc)
  {
    if (paramc.h() == 34)
    {
      c localc = new c();
      for (;;)
      {
        long l = paramc.b(QUOTED_STRING_DELIMITERS);
        if (l == -1L) {
          return null;
        }
        if (paramc.c(l) == 34)
        {
          localc.write(paramc, l);
          paramc.h();
          return localc.q();
        }
        if (paramc.a() == l + 1L) {
          return null;
        }
        localc.write(paramc, l);
        paramc.h();
        localc.write(paramc, 1L);
      }
    }
    throw new IllegalArgumentException();
  }
  
  private static String readToken(c paramc)
  {
    try
    {
      long l1 = paramc.b(TOKEN_DELIMITERS);
      long l2 = l1;
      if (l1 == -1L) {
        l2 = paramc.a();
      }
      if (l2 != 0L) {
        paramc = paramc.e(l2);
      } else {
        paramc = null;
      }
      return paramc;
    }
    catch (EOFException paramc)
    {
      throw new AssertionError();
    }
  }
  
  public static void receiveHeaders(CookieJar paramCookieJar, HttpUrl paramHttpUrl, Headers paramHeaders)
  {
    if (paramCookieJar == CookieJar.NO_COOKIES) {
      return;
    }
    paramHeaders = Cookie.parseAll(paramHttpUrl, paramHeaders);
    if (paramHeaders.isEmpty()) {
      return;
    }
    paramCookieJar.saveFromResponse(paramHttpUrl, paramHeaders);
  }
  
  private static String repeat(char paramChar, int paramInt)
  {
    char[] arrayOfChar = new char[paramInt];
    Arrays.fill(arrayOfChar, paramChar);
    return new String(arrayOfChar);
  }
  
  private static int skipAll(c paramc, byte paramByte)
  {
    int i = 0;
    while ((!paramc.e()) && (paramc.c(0L) == paramByte))
    {
      i++;
      paramc.h();
    }
    return i;
  }
  
  public static int skipUntil(String paramString1, int paramInt, String paramString2)
  {
    while ((paramInt < paramString1.length()) && (paramString2.indexOf(paramString1.charAt(paramInt)) == -1)) {
      paramInt++;
    }
    return paramInt;
  }
  
  public static int skipWhitespace(String paramString, int paramInt)
  {
    while (paramInt < paramString.length())
    {
      int i = paramString.charAt(paramInt);
      if ((i != 32) && (i != 9)) {
        break;
      }
      paramInt++;
    }
    return paramInt;
  }
  
  private static boolean skipWhitespaceAndCommas(c paramc)
  {
    boolean bool = false;
    while (!paramc.e())
    {
      int i = paramc.c(0L);
      if (i == 44)
      {
        paramc.h();
        bool = true;
      }
      else
      {
        if ((i != 32) && (i != 9)) {
          break;
        }
        paramc.h();
      }
    }
    return bool;
  }
  
  private static long stringToLong(String paramString)
  {
    if (paramString == null) {
      return -1L;
    }
    try
    {
      long l = Long.parseLong(paramString);
      return l;
    }
    catch (NumberFormatException paramString) {}
    return -1L;
  }
  
  public static Set<String> varyFields(Headers paramHeaders)
  {
    Object localObject1 = Collections.emptySet();
    int i = paramHeaders.size();
    for (int j = 0; j < i; j++) {
      if ("Vary".equalsIgnoreCase(paramHeaders.name(j)))
      {
        Object localObject2 = paramHeaders.value(j);
        Object localObject3 = localObject1;
        if (((Set)localObject1).isEmpty()) {
          localObject3 = new TreeSet(String.CASE_INSENSITIVE_ORDER);
        }
        localObject2 = ((String)localObject2).split(",");
        int k = localObject2.length;
        for (int m = 0;; m++)
        {
          localObject1 = localObject3;
          if (m >= k) {
            break;
          }
          ((Set)localObject3).add(localObject2[m].trim());
        }
      }
    }
    return (Set<String>)localObject1;
  }
  
  private static Set<String> varyFields(Response paramResponse)
  {
    return varyFields(paramResponse.headers());
  }
  
  public static Headers varyHeaders(Headers paramHeaders1, Headers paramHeaders2)
  {
    Set localSet = varyFields(paramHeaders2);
    if (localSet.isEmpty()) {
      return new Headers.Builder().build();
    }
    Headers.Builder localBuilder = new Headers.Builder();
    int i = 0;
    int j = paramHeaders1.size();
    while (i < j)
    {
      paramHeaders2 = paramHeaders1.name(i);
      if (localSet.contains(paramHeaders2)) {
        localBuilder.add(paramHeaders2, paramHeaders1.value(i));
      }
      i++;
    }
    return localBuilder.build();
  }
  
  public static Headers varyHeaders(Response paramResponse)
  {
    return varyHeaders(paramResponse.networkResponse().request().headers(), paramResponse.headers());
  }
  
  public static boolean varyMatches(Response paramResponse, Headers paramHeaders, Request paramRequest)
  {
    paramResponse = varyFields(paramResponse).iterator();
    while (paramResponse.hasNext())
    {
      String str = (String)paramResponse.next();
      if (!Util.equal(paramHeaders.values(str), paramRequest.headers(str))) {
        return false;
      }
    }
    return true;
  }
}


/* Location:              ~/okhttp3/internal/http/HttpHeaders.class
 *
 * Reversed by:           J
 */