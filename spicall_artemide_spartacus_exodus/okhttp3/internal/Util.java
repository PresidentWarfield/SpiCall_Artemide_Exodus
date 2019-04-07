package okhttp3.internal;

import a.c;
import a.e;
import a.f;
import a.s;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.IDN;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.internal.http2.Header;

public final class Util
{
  public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
  public static final RequestBody EMPTY_REQUEST;
  public static final ResponseBody EMPTY_RESPONSE;
  public static final String[] EMPTY_STRING_ARRAY = new String[0];
  public static final Charset ISO_8859_1;
  public static final Comparator<String> NATURAL_ORDER;
  public static final TimeZone UTC;
  private static final Charset UTF_16_BE;
  private static final f UTF_16_BE_BOM;
  private static final Charset UTF_16_LE;
  private static final f UTF_16_LE_BOM;
  private static final Charset UTF_32_BE;
  private static final f UTF_32_BE_BOM;
  private static final Charset UTF_32_LE;
  private static final f UTF_32_LE_BOM;
  public static final Charset UTF_8;
  private static final f UTF_8_BOM;
  private static final Pattern VERIFY_AS_IP_ADDRESS = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");
  private static final Method addSuppressedExceptionMethod;
  
  static
  {
    Object localObject1 = EMPTY_BYTE_ARRAY;
    Object localObject2 = null;
    EMPTY_RESPONSE = ResponseBody.create(null, (byte[])localObject1);
    EMPTY_REQUEST = RequestBody.create(null, EMPTY_BYTE_ARRAY);
    UTF_8_BOM = f.c("efbbbf");
    UTF_16_BE_BOM = f.c("feff");
    UTF_16_LE_BOM = f.c("fffe");
    UTF_32_BE_BOM = f.c("0000ffff");
    UTF_32_LE_BOM = f.c("ffff0000");
    UTF_8 = Charset.forName("UTF-8");
    ISO_8859_1 = Charset.forName("ISO-8859-1");
    UTF_16_BE = Charset.forName("UTF-16BE");
    UTF_16_LE = Charset.forName("UTF-16LE");
    UTF_32_BE = Charset.forName("UTF-32BE");
    UTF_32_LE = Charset.forName("UTF-32LE");
    UTC = TimeZone.getTimeZone("GMT");
    NATURAL_ORDER = new Comparator()
    {
      public int compare(String paramAnonymousString1, String paramAnonymousString2)
      {
        return paramAnonymousString1.compareTo(paramAnonymousString2);
      }
    };
    try
    {
      localObject1 = Throwable.class.getDeclaredMethod("addSuppressed", new Class[] { Throwable.class });
      localObject2 = localObject1;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    addSuppressedExceptionMethod = (Method)localObject2;
  }
  
  public static void addSuppressedIfPossible(Throwable paramThrowable1, Throwable paramThrowable2)
  {
    Method localMethod = addSuppressedExceptionMethod;
    if (localMethod != null) {}
    try
    {
      localMethod.invoke(paramThrowable1, new Object[] { paramThrowable2 });
      return;
    }
    catch (InvocationTargetException|IllegalAccessException paramThrowable1)
    {
      for (;;) {}
    }
  }
  
  public static AssertionError assertionError(String paramString, Exception paramException)
  {
    paramString = new AssertionError(paramString);
    try
    {
      paramString.initCause(paramException);
      return paramString;
    }
    catch (IllegalStateException paramException)
    {
      for (;;) {}
    }
  }
  
  public static Charset bomAwareCharset(e parame, Charset paramCharset)
  {
    if (parame.a(0L, UTF_8_BOM))
    {
      parame.i(UTF_8_BOM.h());
      return UTF_8;
    }
    if (parame.a(0L, UTF_16_BE_BOM))
    {
      parame.i(UTF_16_BE_BOM.h());
      return UTF_16_BE;
    }
    if (parame.a(0L, UTF_16_LE_BOM))
    {
      parame.i(UTF_16_LE_BOM.h());
      return UTF_16_LE;
    }
    if (parame.a(0L, UTF_32_BE_BOM))
    {
      parame.i(UTF_32_BE_BOM.h());
      return UTF_32_BE;
    }
    if (parame.a(0L, UTF_32_LE_BOM))
    {
      parame.i(UTF_32_LE_BOM.h());
      return UTF_32_LE;
    }
    return paramCharset;
  }
  
  public static String canonicalizeHost(String paramString)
  {
    if (paramString.contains(":"))
    {
      if ((paramString.startsWith("[")) && (paramString.endsWith("]"))) {
        localObject = decodeIpv6(paramString, 1, paramString.length() - 1);
      } else {
        localObject = decodeIpv6(paramString, 0, paramString.length());
      }
      if (localObject == null) {
        return null;
      }
      Object localObject = ((InetAddress)localObject).getAddress();
      if (localObject.length == 16) {
        return inet6AddressToAscii((byte[])localObject);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Invalid IPv6 address: '");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append("'");
      throw new AssertionError(((StringBuilder)localObject).toString());
    }
    try
    {
      paramString = IDN.toASCII(paramString).toLowerCase(Locale.US);
      if (paramString.isEmpty()) {
        return null;
      }
      boolean bool = containsInvalidHostnameAsciiCodes(paramString);
      if (bool) {
        return null;
      }
      return paramString;
    }
    catch (IllegalArgumentException paramString) {}
    return null;
  }
  
  public static int checkDuration(String paramString, long paramLong, TimeUnit paramTimeUnit)
  {
    if (paramLong >= 0L)
    {
      if (paramTimeUnit != null)
      {
        long l = paramTimeUnit.toMillis(paramLong);
        if (l <= 2147483647L)
        {
          if ((l == 0L) && (paramLong > 0L))
          {
            paramTimeUnit = new StringBuilder();
            paramTimeUnit.append(paramString);
            paramTimeUnit.append(" too small.");
            throw new IllegalArgumentException(paramTimeUnit.toString());
          }
          return (int)l;
        }
        paramTimeUnit = new StringBuilder();
        paramTimeUnit.append(paramString);
        paramTimeUnit.append(" too large.");
        throw new IllegalArgumentException(paramTimeUnit.toString());
      }
      throw new NullPointerException("unit == null");
    }
    paramTimeUnit = new StringBuilder();
    paramTimeUnit.append(paramString);
    paramTimeUnit.append(" < 0");
    throw new IllegalArgumentException(paramTimeUnit.toString());
  }
  
  public static void checkOffsetAndCount(long paramLong1, long paramLong2, long paramLong3)
  {
    if (((paramLong2 | paramLong3) >= 0L) && (paramLong2 <= paramLong1) && (paramLong1 - paramLong2 >= paramLong3)) {
      return;
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable != null) {}
    try
    {
      try
      {
        paramCloseable.close();
      }
      catch (RuntimeException paramCloseable)
      {
        throw paramCloseable;
      }
      return;
    }
    catch (Exception paramCloseable)
    {
      for (;;) {}
    }
  }
  
  public static void closeQuietly(ServerSocket paramServerSocket)
  {
    if (paramServerSocket != null) {}
    try
    {
      try
      {
        paramServerSocket.close();
      }
      catch (RuntimeException paramServerSocket)
      {
        throw paramServerSocket;
      }
      return;
    }
    catch (Exception paramServerSocket)
    {
      for (;;) {}
    }
  }
  
  public static void closeQuietly(Socket paramSocket)
  {
    if (paramSocket != null) {}
    try
    {
      try
      {
        paramSocket.close();
      }
      catch (RuntimeException paramSocket)
      {
        throw paramSocket;
      }
      catch (AssertionError paramSocket)
      {
        if (!isAndroidGetsocknameError(paramSocket)) {
          throw paramSocket;
        }
      }
      return;
    }
    catch (Exception paramSocket)
    {
      for (;;) {}
    }
  }
  
  public static String[] concat(String[] paramArrayOfString, String paramString)
  {
    String[] arrayOfString = new String[paramArrayOfString.length + 1];
    System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, paramArrayOfString.length);
    arrayOfString[(arrayOfString.length - 1)] = paramString;
    return arrayOfString;
  }
  
  private static boolean containsInvalidHostnameAsciiCodes(String paramString)
  {
    int i = 0;
    while (i < paramString.length())
    {
      int j = paramString.charAt(i);
      if ((j > 31) && (j < 127))
      {
        if (" #%/:?@[\\]".indexOf(j) != -1) {
          return true;
        }
        i++;
      }
      else
      {
        return true;
      }
    }
    return false;
  }
  
  public static int decodeHexDigit(char paramChar)
  {
    if ((paramChar >= '0') && (paramChar <= '9')) {
      return paramChar - '0';
    }
    if ((paramChar >= 'a') && (paramChar <= 'f')) {
      return paramChar - 'a' + 10;
    }
    if ((paramChar >= 'A') && (paramChar <= 'F')) {
      return paramChar - 'A' + 10;
    }
    return -1;
  }
  
  private static boolean decodeIpv4Suffix(String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3)
  {
    int i = paramInt3;
    int j = paramInt1;
    while (j < paramInt2)
    {
      if (i == paramArrayOfByte.length) {
        return false;
      }
      paramInt1 = j;
      if (i != paramInt3)
      {
        if (paramString.charAt(j) != '.') {
          return false;
        }
        paramInt1 = j + 1;
      }
      j = paramInt1;
      int k = 0;
      while (j < paramInt2)
      {
        int m = paramString.charAt(j);
        if ((m < 48) || (m > 57)) {
          break;
        }
        if ((k == 0) && (paramInt1 != j)) {
          return false;
        }
        k = k * 10 + m - 48;
        if (k > 255) {
          return false;
        }
        j++;
      }
      if (j - paramInt1 == 0) {
        return false;
      }
      paramArrayOfByte[i] = ((byte)(byte)k);
      i++;
    }
    return i == paramInt3 + 4;
  }
  
  @Nullable
  private static InetAddress decodeIpv6(String paramString, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = new byte[16];
    int i = 0;
    int j = -1;
    int k = -1;
    int m;
    int n;
    for (;;)
    {
      m = i;
      n = j;
      if (paramInt1 >= paramInt2) {
        break label285;
      }
      if (i == arrayOfByte.length) {
        return null;
      }
      m = paramInt1 + 2;
      if ((m <= paramInt2) && (paramString.regionMatches(paramInt1, "::", 0, 2)))
      {
        if (j != -1) {
          return null;
        }
        paramInt1 = i + 2;
        if (m == paramInt2)
        {
          n = paramInt1;
          m = paramInt1;
          break label285;
        }
        j = paramInt1;
        i = paramInt1;
        paramInt1 = m;
      }
      else if (i != 0)
      {
        if (paramString.regionMatches(paramInt1, ":", 0, 1))
        {
          paramInt1++;
        }
        else
        {
          if (paramString.regionMatches(paramInt1, ".", 0, 1))
          {
            if (!decodeIpv4Suffix(paramString, k, paramInt2, arrayOfByte, i - 2)) {
              return null;
            }
            m = i + 2;
            n = j;
            break label285;
          }
          return null;
        }
      }
      m = paramInt1;
      k = 0;
      while (m < paramInt2)
      {
        n = decodeHexDigit(paramString.charAt(m));
        if (n == -1) {
          break;
        }
        k = (k << 4) + n;
        m++;
      }
      n = m - paramInt1;
      if ((n == 0) || (n > 4)) {
        break;
      }
      n = i + 1;
      arrayOfByte[i] = ((byte)(byte)(k >>> 8 & 0xFF));
      i = n + 1;
      arrayOfByte[n] = ((byte)(byte)(k & 0xFF));
      k = paramInt1;
      paramInt1 = m;
    }
    return null;
    label285:
    if (m != arrayOfByte.length)
    {
      if (n == -1) {
        return null;
      }
      paramInt1 = arrayOfByte.length;
      paramInt2 = m - n;
      System.arraycopy(arrayOfByte, n, arrayOfByte, paramInt1 - paramInt2, paramInt2);
      Arrays.fill(arrayOfByte, n, arrayOfByte.length - m + n, (byte)0);
    }
    try
    {
      paramString = InetAddress.getByAddress(arrayOfByte);
      return paramString;
    }
    catch (UnknownHostException paramString)
    {
      throw new AssertionError();
    }
  }
  
  public static int delimiterOffset(String paramString, int paramInt1, int paramInt2, char paramChar)
  {
    while (paramInt1 < paramInt2)
    {
      if (paramString.charAt(paramInt1) == paramChar) {
        return paramInt1;
      }
      paramInt1++;
    }
    return paramInt2;
  }
  
  public static int delimiterOffset(String paramString1, int paramInt1, int paramInt2, String paramString2)
  {
    while (paramInt1 < paramInt2)
    {
      if (paramString2.indexOf(paramString1.charAt(paramInt1)) != -1) {
        return paramInt1;
      }
      paramInt1++;
    }
    return paramInt2;
  }
  
  public static boolean discard(s params, int paramInt, TimeUnit paramTimeUnit)
  {
    try
    {
      boolean bool = skipAll(params, paramInt, paramTimeUnit);
      return bool;
    }
    catch (IOException params) {}
    return false;
  }
  
  public static boolean equal(Object paramObject1, Object paramObject2)
  {
    boolean bool;
    if ((paramObject1 != paramObject2) && ((paramObject1 == null) || (!paramObject1.equals(paramObject2)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static String format(String paramString, Object... paramVarArgs)
  {
    return String.format(Locale.US, paramString, paramVarArgs);
  }
  
  public static String hostHeader(HttpUrl paramHttpUrl, boolean paramBoolean)
  {
    Object localObject1;
    if (paramHttpUrl.host().contains(":"))
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("[");
      ((StringBuilder)localObject1).append(paramHttpUrl.host());
      ((StringBuilder)localObject1).append("]");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else
    {
      localObject1 = paramHttpUrl.host();
    }
    Object localObject2;
    if (!paramBoolean)
    {
      localObject2 = localObject1;
      if (paramHttpUrl.port() == HttpUrl.defaultPort(paramHttpUrl.scheme())) {}
    }
    else
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append(":");
      ((StringBuilder)localObject2).append(paramHttpUrl.port());
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    return (String)localObject2;
  }
  
  public static <T> List<T> immutableList(List<T> paramList)
  {
    return Collections.unmodifiableList(new ArrayList(paramList));
  }
  
  public static <T> List<T> immutableList(T... paramVarArgs)
  {
    return Collections.unmodifiableList(Arrays.asList((Object[])paramVarArgs.clone()));
  }
  
  public static <K, V> Map<K, V> immutableMap(Map<K, V> paramMap)
  {
    if (paramMap.isEmpty()) {
      paramMap = Collections.emptyMap();
    } else {
      paramMap = Collections.unmodifiableMap(new LinkedHashMap(paramMap));
    }
    return paramMap;
  }
  
  public static int indexOf(Comparator<String> paramComparator, String[] paramArrayOfString, String paramString)
  {
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++) {
      if (paramComparator.compare(paramArrayOfString[j], paramString) == 0) {
        return j;
      }
    }
    return -1;
  }
  
  public static int indexOfControlOrNonAscii(String paramString)
  {
    int i = paramString.length();
    int j = 0;
    while (j < i)
    {
      int k = paramString.charAt(j);
      if ((k > 31) && (k < 127)) {
        j++;
      } else {
        return j;
      }
    }
    return -1;
  }
  
  private static String inet6AddressToAscii(byte[] paramArrayOfByte)
  {
    int i = 0;
    int j = 0;
    int k = -1;
    int i3;
    for (int m = 0; j < paramArrayOfByte.length; m = i3)
    {
      for (int n = j; (n < 16) && (paramArrayOfByte[n] == 0) && (paramArrayOfByte[(n + 1)] == 0); n += 2) {}
      int i1 = n - j;
      int i2 = k;
      i3 = m;
      if (i1 > m)
      {
        i2 = k;
        i3 = m;
        if (i1 >= 4)
        {
          i3 = i1;
          i2 = j;
        }
      }
      j = n + 2;
      k = i2;
    }
    c localc = new c();
    j = i;
    while (j < paramArrayOfByte.length) {
      if (j == k)
      {
        localc.b(58);
        i3 = j + m;
        j = i3;
        if (i3 == 16)
        {
          localc.b(58);
          j = i3;
        }
      }
      else
      {
        if (j > 0) {
          localc.b(58);
        }
        localc.l((paramArrayOfByte[j] & 0xFF) << 8 | paramArrayOfByte[(j + 1)] & 0xFF);
        j += 2;
      }
    }
    return localc.q();
  }
  
  public static String[] intersect(Comparator<? super String> paramComparator, String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    ArrayList localArrayList = new ArrayList();
    int i = paramArrayOfString1.length;
    for (int j = 0; j < i; j++)
    {
      String str = paramArrayOfString1[j];
      int k = paramArrayOfString2.length;
      for (int m = 0; m < k; m++) {
        if (paramComparator.compare(str, paramArrayOfString2[m]) == 0)
        {
          localArrayList.add(str);
          break;
        }
      }
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  public static boolean isAndroidGetsocknameError(AssertionError paramAssertionError)
  {
    boolean bool;
    if ((paramAssertionError.getCause() != null) && (paramAssertionError.getMessage() != null) && (paramAssertionError.getMessage().contains("getsockname failed"))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean nonEmptyIntersection(Comparator<String> paramComparator, String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    if ((paramArrayOfString1 != null) && (paramArrayOfString2 != null) && (paramArrayOfString1.length != 0) && (paramArrayOfString2.length != 0))
    {
      int i = paramArrayOfString1.length;
      for (int j = 0; j < i; j++)
      {
        String str = paramArrayOfString1[j];
        int k = paramArrayOfString2.length;
        for (int m = 0; m < k; m++) {
          if (paramComparator.compare(str, paramArrayOfString2[m]) == 0) {
            return true;
          }
        }
      }
      return false;
    }
    return false;
  }
  
  public static X509TrustManager platformTrustManager()
  {
    try
    {
      Object localObject = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      ((TrustManagerFactory)localObject).init((KeyStore)null);
      TrustManager[] arrayOfTrustManager = ((TrustManagerFactory)localObject).getTrustManagers();
      if ((arrayOfTrustManager.length == 1) && ((arrayOfTrustManager[0] instanceof X509TrustManager))) {
        return (X509TrustManager)arrayOfTrustManager[0];
      }
      IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
      localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append("Unexpected default trust managers:");
      ((StringBuilder)localObject).append(Arrays.toString(arrayOfTrustManager));
      localIllegalStateException.<init>(((StringBuilder)localObject).toString());
      throw localIllegalStateException;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw assertionError("No System TLS", localGeneralSecurityException);
    }
  }
  
  /* Error */
  public static boolean skipAll(s params, int paramInt, TimeUnit paramTimeUnit)
  {
    // Byte code:
    //   0: invokestatic 550	java/lang/System:nanoTime	()J
    //   3: lstore_3
    //   4: aload_0
    //   5: invokeinterface 556 1 0
    //   10: invokevirtual 561	a/t:hasDeadline	()Z
    //   13: ifeq +19 -> 32
    //   16: aload_0
    //   17: invokeinterface 556 1 0
    //   22: invokevirtual 564	a/t:deadlineNanoTime	()J
    //   25: lload_3
    //   26: lsub
    //   27: lstore 5
    //   29: goto +8 -> 37
    //   32: ldc2_w 565
    //   35: lstore 5
    //   37: aload_0
    //   38: invokeinterface 556 1 0
    //   43: lload 5
    //   45: aload_2
    //   46: iload_1
    //   47: i2l
    //   48: invokevirtual 569	java/util/concurrent/TimeUnit:toNanos	(J)J
    //   51: invokestatic 575	java/lang/Math:min	(JJ)J
    //   54: lload_3
    //   55: ladd
    //   56: invokevirtual 578	a/t:deadlineNanoTime	(J)La/t;
    //   59: pop
    //   60: new 468	a/c
    //   63: astore_2
    //   64: aload_2
    //   65: invokespecial 469	a/c:<init>	()V
    //   68: aload_0
    //   69: aload_2
    //   70: ldc2_w 579
    //   73: invokeinterface 584 4 0
    //   78: ldc2_w 585
    //   81: lcmp
    //   82: ifeq +10 -> 92
    //   85: aload_2
    //   86: invokevirtual 589	a/c:t	()V
    //   89: goto -21 -> 68
    //   92: lload 5
    //   94: ldc2_w 565
    //   97: lcmp
    //   98: ifne +16 -> 114
    //   101: aload_0
    //   102: invokeinterface 556 1 0
    //   107: invokevirtual 592	a/t:clearDeadline	()La/t;
    //   110: pop
    //   111: goto +17 -> 128
    //   114: aload_0
    //   115: invokeinterface 556 1 0
    //   120: lload_3
    //   121: lload 5
    //   123: ladd
    //   124: invokevirtual 578	a/t:deadlineNanoTime	(J)La/t;
    //   127: pop
    //   128: iconst_1
    //   129: ireturn
    //   130: astore_2
    //   131: lload 5
    //   133: ldc2_w 565
    //   136: lcmp
    //   137: ifne +16 -> 153
    //   140: aload_0
    //   141: invokeinterface 556 1 0
    //   146: invokevirtual 592	a/t:clearDeadline	()La/t;
    //   149: pop
    //   150: goto +17 -> 167
    //   153: aload_0
    //   154: invokeinterface 556 1 0
    //   159: lload_3
    //   160: lload 5
    //   162: ladd
    //   163: invokevirtual 578	a/t:deadlineNanoTime	(J)La/t;
    //   166: pop
    //   167: aload_2
    //   168: athrow
    //   169: astore_2
    //   170: lload 5
    //   172: ldc2_w 565
    //   175: lcmp
    //   176: ifne +16 -> 192
    //   179: aload_0
    //   180: invokeinterface 556 1 0
    //   185: invokevirtual 592	a/t:clearDeadline	()La/t;
    //   188: pop
    //   189: goto +17 -> 206
    //   192: aload_0
    //   193: invokeinterface 556 1 0
    //   198: lload_3
    //   199: lload 5
    //   201: ladd
    //   202: invokevirtual 578	a/t:deadlineNanoTime	(J)La/t;
    //   205: pop
    //   206: iconst_0
    //   207: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	208	0	params	s
    //   0	208	1	paramInt	int
    //   0	208	2	paramTimeUnit	TimeUnit
    //   3	196	3	l1	long
    //   27	173	5	l2	long
    // Exception table:
    //   from	to	target	type
    //   60	68	130	finally
    //   68	89	130	finally
    //   60	68	169	java/io/InterruptedIOException
    //   68	89	169	java/io/InterruptedIOException
  }
  
  public static int skipLeadingAsciiWhitespace(String paramString, int paramInt1, int paramInt2)
  {
    while (paramInt1 < paramInt2)
    {
      switch (paramString.charAt(paramInt1))
      {
      default: 
        return paramInt1;
      }
      paramInt1++;
    }
    return paramInt2;
  }
  
  public static int skipTrailingAsciiWhitespace(String paramString, int paramInt1, int paramInt2)
  {
    
    while (paramInt2 >= paramInt1)
    {
      switch (paramString.charAt(paramInt2))
      {
      default: 
        return paramInt2 + 1;
      }
      paramInt2--;
    }
    return paramInt1;
  }
  
  public static ThreadFactory threadFactory(String paramString, final boolean paramBoolean)
  {
    new ThreadFactory()
    {
      public Thread newThread(Runnable paramAnonymousRunnable)
      {
        paramAnonymousRunnable = new Thread(paramAnonymousRunnable, Util.this);
        paramAnonymousRunnable.setDaemon(paramBoolean);
        return paramAnonymousRunnable;
      }
    };
  }
  
  public static Headers toHeaders(List<Header> paramList)
  {
    Headers.Builder localBuilder = new Headers.Builder();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      paramList = (Header)localIterator.next();
      Internal.instance.addLenient(localBuilder, paramList.name.a(), paramList.value.a());
    }
    return localBuilder.build();
  }
  
  public static String trimSubstring(String paramString, int paramInt1, int paramInt2)
  {
    paramInt1 = skipLeadingAsciiWhitespace(paramString, paramInt1, paramInt2);
    return paramString.substring(paramInt1, skipTrailingAsciiWhitespace(paramString, paramInt1, paramInt2));
  }
  
  public static boolean verifyAsIpAddress(String paramString)
  {
    return VERIFY_AS_IP_ADDRESS.matcher(paramString).matches();
  }
}


/* Location:              ~/okhttp3/internal/Util.class
 *
 * Reversed by:           J
 */