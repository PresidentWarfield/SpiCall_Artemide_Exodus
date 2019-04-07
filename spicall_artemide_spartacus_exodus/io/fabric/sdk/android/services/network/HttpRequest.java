package io.fabric.sdk.android.services.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.zip.GZIPInputStream;

public class HttpRequest
{
  private static final String[] b = new String[0];
  private static b c = b.a;
  public final URL a;
  private HttpURLConnection d = null;
  private final String e;
  private d f;
  private boolean g;
  private boolean h = true;
  private boolean i = false;
  private int j = 8192;
  private String k;
  private int l;
  
  public HttpRequest(CharSequence paramCharSequence, String paramString)
  {
    try
    {
      URL localURL = new java/net/URL;
      localURL.<init>(paramCharSequence.toString());
      this.a = localURL;
      this.e = paramString;
      return;
    }
    catch (MalformedURLException paramCharSequence)
    {
      throw new HttpRequestException(paramCharSequence);
    }
  }
  
  public static HttpRequest a(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    paramMap = a(paramCharSequence, paramMap);
    paramCharSequence = paramMap;
    if (paramBoolean) {
      paramCharSequence = a(paramMap);
    }
    return b(paramCharSequence);
  }
  
  /* Error */
  public static String a(CharSequence paramCharSequence)
  {
    // Byte code:
    //   0: new 66	java/net/URL
    //   3: dup
    //   4: aload_0
    //   5: invokeinterface 72 1 0
    //   10: invokespecial 75	java/net/URL:<init>	(Ljava/lang/String;)V
    //   13: astore_1
    //   14: aload_1
    //   15: invokevirtual 102	java/net/URL:getHost	()Ljava/lang/String;
    //   18: astore_0
    //   19: aload_1
    //   20: invokevirtual 106	java/net/URL:getPort	()I
    //   23: istore_2
    //   24: iload_2
    //   25: iconst_m1
    //   26: if_icmpeq +41 -> 67
    //   29: new 108	java/lang/StringBuilder
    //   32: dup
    //   33: invokespecial 109	java/lang/StringBuilder:<init>	()V
    //   36: astore_3
    //   37: aload_3
    //   38: aload_0
    //   39: invokevirtual 113	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: pop
    //   43: aload_3
    //   44: bipush 58
    //   46: invokevirtual 116	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   49: pop
    //   50: aload_3
    //   51: iload_2
    //   52: invokestatic 121	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   55: invokevirtual 113	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   58: pop
    //   59: aload_3
    //   60: invokevirtual 122	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   63: astore_0
    //   64: goto +3 -> 67
    //   67: new 124	java/net/URI
    //   70: astore_3
    //   71: aload_3
    //   72: aload_1
    //   73: invokevirtual 127	java/net/URL:getProtocol	()Ljava/lang/String;
    //   76: aload_0
    //   77: aload_1
    //   78: invokevirtual 130	java/net/URL:getPath	()Ljava/lang/String;
    //   81: aload_1
    //   82: invokevirtual 133	java/net/URL:getQuery	()Ljava/lang/String;
    //   85: aconst_null
    //   86: invokespecial 136	java/net/URI:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   89: aload_3
    //   90: invokevirtual 139	java/net/URI:toASCIIString	()Ljava/lang/String;
    //   93: astore_1
    //   94: aload_1
    //   95: bipush 63
    //   97: invokevirtual 143	java/lang/String:indexOf	(I)I
    //   100: istore_2
    //   101: aload_1
    //   102: astore_0
    //   103: iload_2
    //   104: ifle +57 -> 161
    //   107: iinc 2 1
    //   110: aload_1
    //   111: astore_0
    //   112: iload_2
    //   113: aload_1
    //   114: invokevirtual 146	java/lang/String:length	()I
    //   117: if_icmpge +44 -> 161
    //   120: new 108	java/lang/StringBuilder
    //   123: astore_0
    //   124: aload_0
    //   125: invokespecial 109	java/lang/StringBuilder:<init>	()V
    //   128: aload_0
    //   129: aload_1
    //   130: iconst_0
    //   131: iload_2
    //   132: invokevirtual 150	java/lang/String:substring	(II)Ljava/lang/String;
    //   135: invokevirtual 113	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: pop
    //   139: aload_0
    //   140: aload_1
    //   141: iload_2
    //   142: invokevirtual 152	java/lang/String:substring	(I)Ljava/lang/String;
    //   145: ldc -102
    //   147: ldc -100
    //   149: invokevirtual 160	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   152: invokevirtual 113	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: pop
    //   156: aload_0
    //   157: invokevirtual 122	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   160: astore_0
    //   161: aload_0
    //   162: areturn
    //   163: astore_1
    //   164: new 97	java/io/IOException
    //   167: dup
    //   168: ldc -94
    //   170: invokespecial 163	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   173: astore_0
    //   174: aload_0
    //   175: aload_1
    //   176: invokevirtual 167	java/io/IOException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   179: pop
    //   180: new 8	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   183: dup
    //   184: aload_0
    //   185: invokespecial 82	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
    //   188: athrow
    //   189: astore_0
    //   190: new 8	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   193: dup
    //   194: aload_0
    //   195: invokespecial 82	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
    //   198: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	199	0	paramCharSequence	CharSequence
    //   13	128	1	localObject1	Object
    //   163	13	1	localURISyntaxException	java.net.URISyntaxException
    //   23	119	2	m	int
    //   36	54	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   67	101	163	java/net/URISyntaxException
    //   112	161	163	java/net/URISyntaxException
    //   0	14	189	java/io/IOException
  }
  
  public static String a(CharSequence paramCharSequence, Map<?, ?> paramMap)
  {
    Object localObject = paramCharSequence.toString();
    if ((paramMap != null) && (!paramMap.isEmpty()))
    {
      paramCharSequence = new StringBuilder((String)localObject);
      a((String)localObject, paramCharSequence);
      b((String)localObject, paramCharSequence);
      paramMap = paramMap.entrySet().iterator();
      localObject = (Map.Entry)paramMap.next();
      paramCharSequence.append(((Map.Entry)localObject).getKey().toString());
      paramCharSequence.append('=');
      localObject = ((Map.Entry)localObject).getValue();
      if (localObject != null) {
        paramCharSequence.append(localObject);
      }
      while (paramMap.hasNext())
      {
        paramCharSequence.append('&');
        localObject = (Map.Entry)paramMap.next();
        paramCharSequence.append(((Map.Entry)localObject).getKey().toString());
        paramCharSequence.append('=');
        localObject = ((Map.Entry)localObject).getValue();
        if (localObject != null) {
          paramCharSequence.append(localObject);
        }
      }
      return paramCharSequence.toString();
    }
    return (String)localObject;
  }
  
  private static StringBuilder a(String paramString, StringBuilder paramStringBuilder)
  {
    if (paramString.indexOf(':') + 2 == paramString.lastIndexOf('/')) {
      paramStringBuilder.append('/');
    }
    return paramStringBuilder;
  }
  
  public static HttpRequest b(CharSequence paramCharSequence)
  {
    return new HttpRequest(paramCharSequence, "GET");
  }
  
  public static HttpRequest b(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    paramMap = a(paramCharSequence, paramMap);
    paramCharSequence = paramMap;
    if (paramBoolean) {
      paramCharSequence = a(paramMap);
    }
    return c(paramCharSequence);
  }
  
  private static StringBuilder b(String paramString, StringBuilder paramStringBuilder)
  {
    int m = paramString.indexOf('?');
    int n = paramStringBuilder.length() - 1;
    if (m == -1) {
      paramStringBuilder.append('?');
    } else if ((m < n) && (paramString.charAt(n) != '&')) {
      paramStringBuilder.append('&');
    }
    return paramStringBuilder;
  }
  
  public static HttpRequest c(CharSequence paramCharSequence)
  {
    return new HttpRequest(paramCharSequence, "POST");
  }
  
  public static HttpRequest d(CharSequence paramCharSequence)
  {
    return new HttpRequest(paramCharSequence, "PUT");
  }
  
  public static HttpRequest e(CharSequence paramCharSequence)
  {
    return new HttpRequest(paramCharSequence, "DELETE");
  }
  
  private static String f(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      return paramString;
    }
    return "UTF-8";
  }
  
  private Proxy p()
  {
    return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.k, this.l));
  }
  
  private HttpURLConnection q()
  {
    try
    {
      HttpURLConnection localHttpURLConnection;
      if (this.k != null) {
        localHttpURLConnection = c.a(this.a, p());
      } else {
        localHttpURLConnection = c.a(this.a);
      }
      localHttpURLConnection.setRequestMethod(this.e);
      return localHttpURLConnection;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }
  
  public int a(String paramString, int paramInt)
  {
    k();
    return a().getHeaderFieldInt(paramString, paramInt);
  }
  
  public HttpRequest a(int paramInt)
  {
    a().setConnectTimeout(paramInt);
    return this;
  }
  
  protected HttpRequest a(final InputStream paramInputStream, final OutputStream paramOutputStream)
  {
    (HttpRequest)new a(paramInputStream, this.h)
    {
      public HttpRequest a()
      {
        byte[] arrayOfByte = new byte[HttpRequest.a(HttpRequest.this)];
        for (;;)
        {
          int i = paramInputStream.read(arrayOfByte);
          if (i == -1) {
            break;
          }
          paramOutputStream.write(arrayOfByte, 0, i);
        }
        return HttpRequest.this;
      }
    }.call();
  }
  
  public HttpRequest a(String paramString, Number paramNumber)
  {
    return a(paramString, null, paramNumber);
  }
  
  public HttpRequest a(String paramString1, String paramString2)
  {
    a().setRequestProperty(paramString1, paramString2);
    return this;
  }
  
  public HttpRequest a(String paramString1, String paramString2, Number paramNumber)
  {
    if (paramNumber != null) {
      paramNumber = paramNumber.toString();
    } else {
      paramNumber = null;
    }
    return b(paramString1, paramString2, paramNumber);
  }
  
  protected HttpRequest a(String paramString1, String paramString2, String paramString3)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("form-data; name=\"");
    localStringBuilder.append(paramString1);
    if (paramString2 != null)
    {
      localStringBuilder.append("\"; filename=\"");
      localStringBuilder.append(paramString2);
    }
    localStringBuilder.append('"');
    f("Content-Disposition", localStringBuilder.toString());
    if (paramString3 != null) {
      f("Content-Type", paramString3);
    }
    return f("\r\n");
  }
  
  /* Error */
  public HttpRequest a(String paramString1, String paramString2, String paramString3, java.io.File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 6
    //   6: aload 6
    //   8: astore 7
    //   10: new 323	java/io/BufferedInputStream
    //   13: astore 8
    //   15: aload 6
    //   17: astore 7
    //   19: new 325	java/io/FileInputStream
    //   22: astore 9
    //   24: aload 6
    //   26: astore 7
    //   28: aload 9
    //   30: aload 4
    //   32: invokespecial 328	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   35: aload 6
    //   37: astore 7
    //   39: aload 8
    //   41: aload 9
    //   43: invokespecial 331	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   46: aload_0
    //   47: aload_1
    //   48: aload_2
    //   49: aload_3
    //   50: aload 8
    //   52: invokevirtual 334	io/fabric/sdk/android/services/network/HttpRequest:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/io/InputStream;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   55: astore_1
    //   56: aload 8
    //   58: invokevirtual 339	java/io/InputStream:close	()V
    //   61: aload_1
    //   62: areturn
    //   63: astore_1
    //   64: aload 8
    //   66: astore 7
    //   68: goto +38 -> 106
    //   71: astore_2
    //   72: aload 8
    //   74: astore_1
    //   75: goto +11 -> 86
    //   78: astore_1
    //   79: goto +27 -> 106
    //   82: astore_2
    //   83: aload 5
    //   85: astore_1
    //   86: aload_1
    //   87: astore 7
    //   89: new 8	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   92: astore_3
    //   93: aload_1
    //   94: astore 7
    //   96: aload_3
    //   97: aload_2
    //   98: invokespecial 82	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
    //   101: aload_1
    //   102: astore 7
    //   104: aload_3
    //   105: athrow
    //   106: aload 7
    //   108: ifnull +8 -> 116
    //   111: aload 7
    //   113: invokevirtual 339	java/io/InputStream:close	()V
    //   116: aload_1
    //   117: athrow
    //   118: astore_2
    //   119: goto -58 -> 61
    //   122: astore_2
    //   123: goto -7 -> 116
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	126	0	this	HttpRequest
    //   0	126	1	paramString1	String
    //   0	126	2	paramString2	String
    //   0	126	3	paramString3	String
    //   0	126	4	paramFile	java.io.File
    //   1	83	5	localObject1	Object
    //   4	32	6	localObject2	Object
    //   8	104	7	localObject3	Object
    //   13	60	8	localBufferedInputStream	BufferedInputStream
    //   22	20	9	localFileInputStream	java.io.FileInputStream
    // Exception table:
    //   from	to	target	type
    //   46	56	63	finally
    //   46	56	71	java/io/IOException
    //   10	15	78	finally
    //   19	24	78	finally
    //   28	35	78	finally
    //   39	46	78	finally
    //   89	93	78	finally
    //   96	101	78	finally
    //   104	106	78	finally
    //   10	15	82	java/io/IOException
    //   19	24	82	java/io/IOException
    //   28	35	82	java/io/IOException
    //   39	46	82	java/io/IOException
    //   56	61	118	java/io/IOException
    //   111	116	122	java/io/IOException
  }
  
  public HttpRequest a(String paramString1, String paramString2, String paramString3, InputStream paramInputStream)
  {
    try
    {
      m();
      a(paramString1, paramString2, paramString3);
      a(paramInputStream, this.f);
      return this;
    }
    catch (IOException paramString1)
    {
      throw new HttpRequestException(paramString1);
    }
  }
  
  public HttpRequest a(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    try
    {
      m();
      a(paramString1, paramString2, paramString3);
      this.f.a(paramString4);
      return this;
    }
    catch (IOException paramString1)
    {
      throw new HttpRequestException(paramString1);
    }
  }
  
  public HttpRequest a(Map.Entry<String, String> paramEntry)
  {
    return a((String)paramEntry.getKey(), (String)paramEntry.getValue());
  }
  
  public HttpRequest a(boolean paramBoolean)
  {
    a().setUseCaches(paramBoolean);
    return this;
  }
  
  public String a(String paramString)
  {
    ByteArrayOutputStream localByteArrayOutputStream = c();
    try
    {
      a(e(), localByteArrayOutputStream);
      paramString = localByteArrayOutputStream.toString(f(paramString));
      return paramString;
    }
    catch (IOException paramString)
    {
      throw new HttpRequestException(paramString);
    }
  }
  
  public HttpURLConnection a()
  {
    if (this.d == null) {
      this.d = q();
    }
    return this.d;
  }
  
  public int b()
  {
    try
    {
      j();
      int m = a().getResponseCode();
      return m;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }
  
  public HttpRequest b(String paramString1, String paramString2, String paramString3)
  {
    return a(paramString1, paramString2, null, paramString3);
  }
  
  public String b(String paramString)
  {
    k();
    return a().getHeaderField(paramString);
  }
  
  public String b(String paramString1, String paramString2)
  {
    return c(b(paramString1), paramString2);
  }
  
  public int c(String paramString)
  {
    return a(paramString, -1);
  }
  
  protected ByteArrayOutputStream c()
  {
    int m = i();
    if (m > 0) {
      return new ByteArrayOutputStream(m);
    }
    return new ByteArrayOutputStream();
  }
  
  protected String c(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString1.length() != 0))
    {
      int m = paramString1.length();
      int n = paramString1.indexOf(';') + 1;
      if ((n != 0) && (n != m))
      {
        int i1 = paramString1.indexOf(';', n);
        int i2 = n;
        int i3 = i1;
        if (i1 == -1)
        {
          i3 = m;
          i2 = n;
        }
        while (i2 < i3)
        {
          n = paramString1.indexOf('=', i2);
          if ((n != -1) && (n < i3) && (paramString2.equals(paramString1.substring(i2, n).trim())))
          {
            String str = paramString1.substring(n + 1, i3).trim();
            i2 = str.length();
            if (i2 != 0)
            {
              if ((i2 > 2) && ('"' == str.charAt(0)))
              {
                i3 = i2 - 1;
                if ('"' == str.charAt(i3)) {
                  return str.substring(1, i3);
                }
              }
              return str;
            }
          }
          n = i3 + 1;
          i1 = paramString1.indexOf(';', n);
          i2 = n;
          i3 = i1;
          if (i1 == -1)
          {
            i3 = m;
            i2 = n;
          }
        }
        return null;
      }
      return null;
    }
    return null;
  }
  
  public HttpRequest d(String paramString)
  {
    return d(paramString, null);
  }
  
  public HttpRequest d(String paramString1, String paramString2)
  {
    if ((paramString2 != null) && (paramString2.length() > 0))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString1);
      localStringBuilder.append("; charset=");
      localStringBuilder.append(paramString2);
      return a("Content-Type", localStringBuilder.toString());
    }
    return a("Content-Type", paramString1);
  }
  
  public String d()
  {
    return a(g());
  }
  
  public HttpRequest e(String paramString1, String paramString2)
  {
    return b(paramString1, null, paramString2);
  }
  
  public BufferedInputStream e()
  {
    return new BufferedInputStream(f(), this.j);
  }
  
  public HttpRequest f(CharSequence paramCharSequence)
  {
    try
    {
      l();
      this.f.a(paramCharSequence.toString());
      return this;
    }
    catch (IOException paramCharSequence)
    {
      throw new HttpRequestException(paramCharSequence);
    }
  }
  
  public HttpRequest f(String paramString1, String paramString2)
  {
    return f(paramString1).f(": ").f(paramString2).f("\r\n");
  }
  
  public InputStream f()
  {
    if (b() < 400)
    {
      try
      {
        InputStream localInputStream1 = a().getInputStream();
      }
      catch (IOException localIOException1)
      {
        throw new HttpRequestException(localIOException1);
      }
    }
    else
    {
      InputStream localInputStream2 = a().getErrorStream();
      Object localObject = localInputStream2;
      if (localInputStream2 == null) {
        try
        {
          localObject = a().getInputStream();
        }
        catch (IOException localIOException2)
        {
          throw new HttpRequestException(localIOException2);
        }
      }
    }
    if ((this.i) && ("gzip".equals(h()))) {
      try
      {
        GZIPInputStream localGZIPInputStream = new GZIPInputStream(localIOException2);
        return localGZIPInputStream;
      }
      catch (IOException localIOException3)
      {
        throw new HttpRequestException(localIOException3);
      }
    }
    return localIOException3;
  }
  
  public String g()
  {
    return b("Content-Type", "charset");
  }
  
  public String h()
  {
    return b("Content-Encoding");
  }
  
  public int i()
  {
    return c("Content-Length");
  }
  
  protected HttpRequest j()
  {
    d locald = this.f;
    if (locald == null) {
      return this;
    }
    if (this.g) {
      locald.a("\r\n--00content0boundary00--\r\n");
    }
    if (this.h) {}
    try
    {
      this.f.close();
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
    this.f.close();
    this.f = null;
    return this;
  }
  
  protected HttpRequest k()
  {
    try
    {
      HttpRequest localHttpRequest = j();
      return localHttpRequest;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }
  
  protected HttpRequest l()
  {
    if (this.f != null) {
      return this;
    }
    a().setDoOutput(true);
    String str = c(a().getRequestProperty("Content-Type"), "charset");
    this.f = new d(a().getOutputStream(), str, this.j);
    return this;
  }
  
  protected HttpRequest m()
  {
    if (!this.g)
    {
      this.g = true;
      d("multipart/form-data; boundary=00content0boundary00").l();
      this.f.a("--00content0boundary00\r\n");
    }
    else
    {
      this.f.a("\r\n--00content0boundary00\r\n");
    }
    return this;
  }
  
  public URL n()
  {
    return a().getURL();
  }
  
  public String o()
  {
    return a().getRequestMethod();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(o());
    localStringBuilder.append(' ');
    localStringBuilder.append(n());
    return localStringBuilder.toString();
  }
  
  public static class HttpRequestException
    extends RuntimeException
  {
    protected HttpRequestException(IOException paramIOException)
    {
      super();
    }
    
    public IOException a()
    {
      return (IOException)super.getCause();
    }
  }
  
  protected static abstract class a<V>
    extends HttpRequest.c<V>
  {
    private final Closeable a;
    private final boolean b;
    
    protected a(Closeable paramCloseable, boolean paramBoolean)
    {
      this.a = paramCloseable;
      this.b = paramBoolean;
    }
    
    protected void c()
    {
      Closeable localCloseable = this.a;
      if ((localCloseable instanceof Flushable)) {
        ((Flushable)localCloseable).flush();
      }
      if (this.b) {}
      try
      {
        this.a.close();
      }
      catch (IOException localIOException)
      {
        for (;;) {}
      }
      this.a.close();
    }
  }
  
  public static abstract interface b
  {
    public static final b a = new b()
    {
      public HttpURLConnection a(URL paramAnonymousURL)
      {
        return (HttpURLConnection)paramAnonymousURL.openConnection();
      }
      
      public HttpURLConnection a(URL paramAnonymousURL, Proxy paramAnonymousProxy)
      {
        return (HttpURLConnection)paramAnonymousURL.openConnection(paramAnonymousProxy);
      }
    };
    
    public abstract HttpURLConnection a(URL paramURL);
    
    public abstract HttpURLConnection a(URL paramURL, Proxy paramProxy);
  }
  
  protected static abstract class c<V>
    implements Callable<V>
  {
    protected abstract V b();
    
    protected abstract void c();
    
    /* Error */
    public V call()
    {
      // Byte code:
      //   0: iconst_1
      //   1: istore_1
      //   2: aload_0
      //   3: invokevirtual 24	io/fabric/sdk/android/services/network/HttpRequest$c:b	()Ljava/lang/Object;
      //   6: astore_2
      //   7: aload_0
      //   8: invokevirtual 26	io/fabric/sdk/android/services/network/HttpRequest$c:c	()V
      //   11: aload_2
      //   12: areturn
      //   13: astore_2
      //   14: new 20	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   17: dup
      //   18: aload_2
      //   19: invokespecial 29	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
      //   22: athrow
      //   23: astore_2
      //   24: iconst_0
      //   25: istore_1
      //   26: goto +19 -> 45
      //   29: astore_2
      //   30: new 20	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   33: astore_3
      //   34: aload_3
      //   35: aload_2
      //   36: invokespecial 29	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
      //   39: aload_3
      //   40: athrow
      //   41: astore_2
      //   42: aload_2
      //   43: athrow
      //   44: astore_2
      //   45: aload_0
      //   46: invokevirtual 26	io/fabric/sdk/android/services/network/HttpRequest$c:c	()V
      //   49: goto +17 -> 66
      //   52: astore_3
      //   53: iload_1
      //   54: ifne +12 -> 66
      //   57: new 20	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   60: dup
      //   61: aload_3
      //   62: invokespecial 29	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
      //   65: athrow
      //   66: aload_2
      //   67: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	68	0	this	c
      //   1	53	1	i	int
      //   6	6	2	localObject1	Object
      //   13	6	2	localIOException1	IOException
      //   23	1	2	localObject2	Object
      //   29	7	2	localIOException2	IOException
      //   41	2	2	localHttpRequestException1	HttpRequest.HttpRequestException
      //   44	23	2	localObject3	Object
      //   33	7	3	localHttpRequestException2	HttpRequest.HttpRequestException
      //   52	10	3	localIOException3	IOException
      // Exception table:
      //   from	to	target	type
      //   7	11	13	java/io/IOException
      //   2	7	23	finally
      //   2	7	29	java/io/IOException
      //   2	7	41	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   30	41	44	finally
      //   42	44	44	finally
      //   45	49	52	java/io/IOException
    }
  }
  
  public static class d
    extends BufferedOutputStream
  {
    private final CharsetEncoder a;
    
    public d(OutputStream paramOutputStream, String paramString, int paramInt)
    {
      super(paramInt);
      this.a = Charset.forName(HttpRequest.e(paramString)).newEncoder();
    }
    
    public d a(String paramString)
    {
      paramString = this.a.encode(CharBuffer.wrap(paramString));
      super.write(paramString.array(), 0, paramString.limit());
      return this;
    }
  }
}


/* Location:              ~/io/fabric/sdk/android/services/network/HttpRequest.class
 *
 * Reversed by:           J
 */