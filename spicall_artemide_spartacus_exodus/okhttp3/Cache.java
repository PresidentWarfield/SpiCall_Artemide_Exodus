package okhttp3;

import a.c;
import a.d;
import a.e;
import a.f;
import a.g;
import a.h;
import a.l;
import a.r;
import a.s;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheRequest;
import okhttp3.internal.cache.CacheStrategy;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.cache.DiskLruCache.Editor;
import okhttp3.internal.cache.DiskLruCache.Snapshot;
import okhttp3.internal.cache.InternalCache;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.io.FileSystem;
import okhttp3.internal.platform.Platform;

public final class Cache
  implements Closeable, Flushable
{
  private static final int ENTRY_BODY = 1;
  private static final int ENTRY_COUNT = 2;
  private static final int ENTRY_METADATA = 0;
  private static final int VERSION = 201105;
  final DiskLruCache cache;
  private int hitCount;
  final InternalCache internalCache = new InternalCache()
  {
    public Response get(Request paramAnonymousRequest)
    {
      return Cache.this.get(paramAnonymousRequest);
    }
    
    public CacheRequest put(Response paramAnonymousResponse)
    {
      return Cache.this.put(paramAnonymousResponse);
    }
    
    public void remove(Request paramAnonymousRequest)
    {
      Cache.this.remove(paramAnonymousRequest);
    }
    
    public void trackConditionalCacheHit()
    {
      Cache.this.trackConditionalCacheHit();
    }
    
    public void trackResponse(CacheStrategy paramAnonymousCacheStrategy)
    {
      Cache.this.trackResponse(paramAnonymousCacheStrategy);
    }
    
    public void update(Response paramAnonymousResponse1, Response paramAnonymousResponse2)
    {
      Cache.this.update(paramAnonymousResponse1, paramAnonymousResponse2);
    }
  };
  private int networkCount;
  private int requestCount;
  int writeAbortCount;
  int writeSuccessCount;
  
  public Cache(File paramFile, long paramLong)
  {
    this(paramFile, paramLong, FileSystem.SYSTEM);
  }
  
  Cache(File paramFile, long paramLong, FileSystem paramFileSystem)
  {
    this.cache = DiskLruCache.create(paramFileSystem, paramFile, 201105, 2, paramLong);
  }
  
  private void abortQuietly(@Nullable DiskLruCache.Editor paramEditor)
  {
    if (paramEditor != null) {}
    try
    {
      paramEditor.abort();
      return;
    }
    catch (IOException paramEditor)
    {
      for (;;) {}
    }
  }
  
  public static String key(HttpUrl paramHttpUrl)
  {
    return f.a(paramHttpUrl.toString()).c().f();
  }
  
  static int readInt(e parame)
  {
    try
    {
      long l = parame.n();
      String str = parame.r();
      if ((l >= 0L) && (l <= 2147483647L) && (str.isEmpty())) {
        return (int)l;
      }
      parame = new java/io/IOException;
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("expected an int but was \"");
      localStringBuilder.append(l);
      localStringBuilder.append(str);
      localStringBuilder.append("\"");
      parame.<init>(localStringBuilder.toString());
      throw parame;
    }
    catch (NumberFormatException parame)
    {
      throw new IOException(parame.getMessage());
    }
  }
  
  public void close()
  {
    this.cache.close();
  }
  
  public void delete()
  {
    this.cache.delete();
  }
  
  public File directory()
  {
    return this.cache.getDirectory();
  }
  
  public void evictAll()
  {
    this.cache.evictAll();
  }
  
  public void flush()
  {
    this.cache.flush();
  }
  
  @Nullable
  Response get(Request paramRequest)
  {
    Object localObject1 = key(paramRequest.url());
    try
    {
      Object localObject2 = this.cache.get((String)localObject1);
      if (localObject2 == null) {
        return null;
      }
      try
      {
        localObject1 = new Entry(((DiskLruCache.Snapshot)localObject2).getSource(0));
        localObject2 = ((Entry)localObject1).response((DiskLruCache.Snapshot)localObject2);
        if (!((Entry)localObject1).matches(paramRequest, (Response)localObject2))
        {
          Util.closeQuietly(((Response)localObject2).body());
          return null;
        }
        return (Response)localObject2;
      }
      catch (IOException paramRequest)
      {
        Util.closeQuietly((Closeable)localObject2);
        return null;
      }
      return null;
    }
    catch (IOException paramRequest) {}
  }
  
  public int hitCount()
  {
    try
    {
      int i = this.hitCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void initialize()
  {
    this.cache.initialize();
  }
  
  public boolean isClosed()
  {
    return this.cache.isClosed();
  }
  
  public long maxSize()
  {
    return this.cache.getMaxSize();
  }
  
  public int networkCount()
  {
    try
    {
      int i = this.networkCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  @Nullable
  CacheRequest put(Response paramResponse)
  {
    Object localObject = paramResponse.request().method();
    if (HttpMethod.invalidatesCache(paramResponse.request().method())) {}
    try
    {
      remove(paramResponse.request());
      return null;
      if (!((String)localObject).equals("GET")) {
        return null;
      }
      if (HttpHeaders.hasVaryAll(paramResponse)) {
        return null;
      }
      localObject = new Entry(paramResponse);
      try
      {
        paramResponse = this.cache.edit(key(paramResponse.request().url()));
        if (paramResponse == null) {
          return null;
        }
        abortQuietly(paramResponse);
      }
      catch (IOException paramResponse)
      {
        try
        {
          ((Entry)localObject).writeTo(paramResponse);
          localObject = new CacheRequestImpl(paramResponse);
          return (CacheRequest)localObject;
        }
        catch (IOException localIOException)
        {
          for (;;) {}
        }
        paramResponse = paramResponse;
        paramResponse = null;
      }
      return null;
    }
    catch (IOException paramResponse)
    {
      for (;;) {}
    }
  }
  
  void remove(Request paramRequest)
  {
    this.cache.remove(key(paramRequest.url()));
  }
  
  public int requestCount()
  {
    try
    {
      int i = this.requestCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public long size()
  {
    return this.cache.size();
  }
  
  void trackConditionalCacheHit()
  {
    try
    {
      this.hitCount += 1;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  void trackResponse(CacheStrategy paramCacheStrategy)
  {
    try
    {
      this.requestCount += 1;
      if (paramCacheStrategy.networkRequest != null) {
        this.networkCount += 1;
      } else if (paramCacheStrategy.cacheResponse != null) {
        this.hitCount += 1;
      }
      return;
    }
    finally {}
  }
  
  /* Error */
  void update(Response paramResponse1, Response paramResponse2)
  {
    // Byte code:
    //   0: new 24	okhttp3/Cache$Entry
    //   3: dup
    //   4: aload_2
    //   5: invokespecial 254	okhttp3/Cache$Entry:<init>	(Lokhttp3/Response;)V
    //   8: astore_2
    //   9: aload_1
    //   10: invokevirtual 198	okhttp3/Response:body	()Lokhttp3/ResponseBody;
    //   13: checkcast 19	okhttp3/Cache$CacheResponseBody
    //   16: getfield 292	okhttp3/Cache$CacheResponseBody:snapshot	Lokhttp3/internal/cache/DiskLruCache$Snapshot;
    //   19: astore_1
    //   20: aload_1
    //   21: invokevirtual 295	okhttp3/internal/cache/DiskLruCache$Snapshot:edit	()Lokhttp3/internal/cache/DiskLruCache$Editor;
    //   24: astore_1
    //   25: aload_1
    //   26: ifnull +23 -> 49
    //   29: aload_2
    //   30: aload_1
    //   31: invokevirtual 261	okhttp3/Cache$Entry:writeTo	(Lokhttp3/internal/cache/DiskLruCache$Editor;)V
    //   34: aload_1
    //   35: invokevirtual 298	okhttp3/internal/cache/DiskLruCache$Editor:commit	()V
    //   38: goto +11 -> 49
    //   41: astore_1
    //   42: aconst_null
    //   43: astore_1
    //   44: aload_0
    //   45: aload_1
    //   46: invokespecial 266	okhttp3/Cache:abortQuietly	(Lokhttp3/internal/cache/DiskLruCache$Editor;)V
    //   49: return
    //   50: astore_2
    //   51: goto -7 -> 44
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	54	0	this	Cache
    //   0	54	1	paramResponse1	Response
    //   0	54	2	paramResponse2	Response
    // Exception table:
    //   from	to	target	type
    //   20	25	41	java/io/IOException
    //   29	38	50	java/io/IOException
  }
  
  public Iterator<String> urls()
  {
    new Iterator()
    {
      boolean canRemove;
      final Iterator<DiskLruCache.Snapshot> delegate = Cache.this.cache.snapshots();
      @Nullable
      String nextUrl;
      
      /* Error */
      public boolean hasNext()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 47	okhttp3/Cache$2:nextUrl	Ljava/lang/String;
        //   4: ifnull +5 -> 9
        //   7: iconst_1
        //   8: ireturn
        //   9: aload_0
        //   10: iconst_0
        //   11: putfield 49	okhttp3/Cache$2:canRemove	Z
        //   14: aload_0
        //   15: getfield 40	okhttp3/Cache$2:delegate	Ljava/util/Iterator;
        //   18: invokeinterface 51 1 0
        //   23: ifeq +54 -> 77
        //   26: aload_0
        //   27: getfield 40	okhttp3/Cache$2:delegate	Ljava/util/Iterator;
        //   30: invokeinterface 55 1 0
        //   35: checkcast 57	okhttp3/internal/cache/DiskLruCache$Snapshot
        //   38: astore_1
        //   39: aload_0
        //   40: aload_1
        //   41: iconst_0
        //   42: invokevirtual 61	okhttp3/internal/cache/DiskLruCache$Snapshot:getSource	(I)La/s;
        //   45: invokestatic 67	a/l:a	(La/s;)La/e;
        //   48: invokeinterface 73 1 0
        //   53: putfield 47	okhttp3/Cache$2:nextUrl	Ljava/lang/String;
        //   56: aload_1
        //   57: invokevirtual 76	okhttp3/internal/cache/DiskLruCache$Snapshot:close	()V
        //   60: iconst_1
        //   61: ireturn
        //   62: astore_2
        //   63: aload_1
        //   64: invokevirtual 76	okhttp3/internal/cache/DiskLruCache$Snapshot:close	()V
        //   67: aload_2
        //   68: athrow
        //   69: astore_2
        //   70: aload_1
        //   71: invokevirtual 76	okhttp3/internal/cache/DiskLruCache$Snapshot:close	()V
        //   74: goto -60 -> 14
        //   77: iconst_0
        //   78: ireturn
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	79	0	this	2
        //   38	33	1	localSnapshot	DiskLruCache.Snapshot
        //   62	6	2	localObject	Object
        //   69	1	2	localIOException	IOException
        // Exception table:
        //   from	to	target	type
        //   39	56	62	finally
        //   39	56	69	java/io/IOException
      }
      
      public String next()
      {
        if (hasNext())
        {
          String str = this.nextUrl;
          this.nextUrl = null;
          this.canRemove = true;
          return str;
        }
        throw new NoSuchElementException();
      }
      
      public void remove()
      {
        if (this.canRemove)
        {
          this.delegate.remove();
          return;
        }
        throw new IllegalStateException("remove() before next()");
      }
    };
  }
  
  public int writeAbortCount()
  {
    try
    {
      int i = this.writeAbortCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int writeSuccessCount()
  {
    try
    {
      int i = this.writeSuccessCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private final class CacheRequestImpl
    implements CacheRequest
  {
    private r body;
    private r cacheOut;
    boolean done;
    private final DiskLruCache.Editor editor;
    
    CacheRequestImpl(final DiskLruCache.Editor paramEditor)
    {
      this.editor = paramEditor;
      this.cacheOut = paramEditor.newSink(1);
      this.body = new g(this.cacheOut)
      {
        public void close()
        {
          synchronized (Cache.this)
          {
            if (Cache.CacheRequestImpl.this.done) {
              return;
            }
            Cache.CacheRequestImpl.this.done = true;
            Cache localCache2 = Cache.this;
            localCache2.writeSuccessCount += 1;
            super.close();
            paramEditor.commit();
            return;
          }
        }
      };
    }
    
    public void abort()
    {
      synchronized (Cache.this)
      {
        if (this.done) {
          return;
        }
        this.done = true;
        Cache localCache2 = Cache.this;
        localCache2.writeAbortCount += 1;
        Util.closeQuietly(this.cacheOut);
      }
      try
      {
        this.editor.abort();
        return;
        localObject = finally;
        throw ((Throwable)localObject);
      }
      catch (IOException localIOException)
      {
        for (;;) {}
      }
    }
    
    public r body()
    {
      return this.body;
    }
  }
  
  private static class CacheResponseBody
    extends ResponseBody
  {
    private final e bodySource;
    @Nullable
    private final String contentLength;
    @Nullable
    private final String contentType;
    final DiskLruCache.Snapshot snapshot;
    
    CacheResponseBody(final DiskLruCache.Snapshot paramSnapshot, String paramString1, String paramString2)
    {
      this.snapshot = paramSnapshot;
      this.contentType = paramString1;
      this.contentLength = paramString2;
      this.bodySource = l.a(new h(paramSnapshot.getSource(1))
      {
        public void close()
        {
          paramSnapshot.close();
          super.close();
        }
      });
    }
    
    public long contentLength()
    {
      long l = -1L;
      try
      {
        if (this.contentLength != null) {
          l = Long.parseLong(this.contentLength);
        }
        return l;
      }
      catch (NumberFormatException localNumberFormatException) {}
      return -1L;
    }
    
    public MediaType contentType()
    {
      Object localObject = this.contentType;
      if (localObject != null) {
        localObject = MediaType.parse((String)localObject);
      } else {
        localObject = null;
      }
      return (MediaType)localObject;
    }
    
    public e source()
    {
      return this.bodySource;
    }
  }
  
  private static final class Entry
  {
    private static final String RECEIVED_MILLIS;
    private static final String SENT_MILLIS;
    private final int code;
    @Nullable
    private final Handshake handshake;
    private final String message;
    private final Protocol protocol;
    private final long receivedResponseMillis;
    private final String requestMethod;
    private final Headers responseHeaders;
    private final long sentRequestMillis;
    private final String url;
    private final Headers varyHeaders;
    
    static
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(Platform.get().getPrefix());
      localStringBuilder.append("-Sent-Millis");
      SENT_MILLIS = localStringBuilder.toString();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(Platform.get().getPrefix());
      localStringBuilder.append("-Received-Millis");
      RECEIVED_MILLIS = localStringBuilder.toString();
    }
    
    Entry(s params)
    {
      try
      {
        Object localObject1 = l.a(params);
        this.url = ((e)localObject1).r();
        this.requestMethod = ((e)localObject1).r();
        Object localObject3 = new okhttp3/Headers$Builder;
        ((Headers.Builder)localObject3).<init>();
        int i = Cache.readInt((e)localObject1);
        int j = 0;
        for (int k = 0; k < i; k++) {
          ((Headers.Builder)localObject3).addLenient(((e)localObject1).r());
        }
        this.varyHeaders = ((Headers.Builder)localObject3).build();
        localObject3 = StatusLine.parse(((e)localObject1).r());
        this.protocol = ((StatusLine)localObject3).protocol;
        this.code = ((StatusLine)localObject3).code;
        this.message = ((StatusLine)localObject3).message;
        localObject3 = new okhttp3/Headers$Builder;
        ((Headers.Builder)localObject3).<init>();
        i = Cache.readInt((e)localObject1);
        for (k = j; k < i; k++) {
          ((Headers.Builder)localObject3).addLenient(((e)localObject1).r());
        }
        Object localObject4 = ((Headers.Builder)localObject3).get(SENT_MILLIS);
        Object localObject5 = ((Headers.Builder)localObject3).get(RECEIVED_MILLIS);
        ((Headers.Builder)localObject3).removeAll(SENT_MILLIS);
        ((Headers.Builder)localObject3).removeAll(RECEIVED_MILLIS);
        long l1 = 0L;
        if (localObject4 != null) {
          l2 = Long.parseLong((String)localObject4);
        } else {
          l2 = 0L;
        }
        this.sentRequestMillis = l2;
        long l2 = l1;
        if (localObject5 != null) {
          l2 = Long.parseLong((String)localObject5);
        }
        this.receivedResponseMillis = l2;
        this.responseHeaders = ((Headers.Builder)localObject3).build();
        if (isHttps())
        {
          localObject3 = ((e)localObject1).r();
          if (((String)localObject3).length() <= 0)
          {
            localObject4 = CipherSuite.forJavaName(((e)localObject1).r());
            localObject5 = readCertificateList((e)localObject1);
            localObject3 = readCertificateList((e)localObject1);
            if (!((e)localObject1).e()) {
              localObject1 = TlsVersion.forJavaName(((e)localObject1).r());
            } else {
              localObject1 = TlsVersion.SSL_3_0;
            }
            this.handshake = Handshake.get((TlsVersion)localObject1, (CipherSuite)localObject4, (List)localObject5, (List)localObject3);
          }
          else
          {
            localObject5 = new java/io/IOException;
            localObject1 = new java/lang/StringBuilder;
            ((StringBuilder)localObject1).<init>();
            ((StringBuilder)localObject1).append("expected \"\" but was \"");
            ((StringBuilder)localObject1).append((String)localObject3);
            ((StringBuilder)localObject1).append("\"");
            ((IOException)localObject5).<init>(((StringBuilder)localObject1).toString());
            throw ((Throwable)localObject5);
          }
        }
        else
        {
          this.handshake = null;
        }
        return;
      }
      finally
      {
        params.close();
      }
    }
    
    Entry(Response paramResponse)
    {
      this.url = paramResponse.request().url().toString();
      this.varyHeaders = HttpHeaders.varyHeaders(paramResponse);
      this.requestMethod = paramResponse.request().method();
      this.protocol = paramResponse.protocol();
      this.code = paramResponse.code();
      this.message = paramResponse.message();
      this.responseHeaders = paramResponse.headers();
      this.handshake = paramResponse.handshake();
      this.sentRequestMillis = paramResponse.sentRequestAtMillis();
      this.receivedResponseMillis = paramResponse.receivedResponseAtMillis();
    }
    
    private boolean isHttps()
    {
      return this.url.startsWith("");
    }
    
    private List<Certificate> readCertificateList(e parame)
    {
      int i = Cache.readInt(parame);
      if (i == -1) {
        return Collections.emptyList();
      }
      try
      {
        CertificateFactory localCertificateFactory = CertificateFactory.getInstance("X.509");
        ArrayList localArrayList = new java/util/ArrayList;
        localArrayList.<init>(i);
        for (int j = 0; j < i; j++)
        {
          String str = parame.r();
          c localc = new a/c;
          localc.<init>();
          localc.a(f.b(str));
          localArrayList.add(localCertificateFactory.generateCertificate(localc.f()));
        }
        return localArrayList;
      }
      catch (CertificateException parame)
      {
        throw new IOException(parame.getMessage());
      }
    }
    
    private void writeCertList(d paramd, List<Certificate> paramList)
    {
      try
      {
        paramd.n(paramList.size()).i(10);
        int i = 0;
        int j = paramList.size();
        while (i < j)
        {
          paramd.b(f.a(((Certificate)paramList.get(i)).getEncoded()).b()).i(10);
          i++;
        }
        return;
      }
      catch (CertificateEncodingException paramd)
      {
        throw new IOException(paramd.getMessage());
      }
    }
    
    public boolean matches(Request paramRequest, Response paramResponse)
    {
      boolean bool;
      if ((this.url.equals(paramRequest.url().toString())) && (this.requestMethod.equals(paramRequest.method())) && (HttpHeaders.varyMatches(paramResponse, this.varyHeaders, paramRequest))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public Response response(DiskLruCache.Snapshot paramSnapshot)
    {
      String str1 = this.responseHeaders.get("Content-Type");
      String str2 = this.responseHeaders.get("Content-Length");
      Request localRequest = new Request.Builder().url(this.url).method(this.requestMethod, null).headers(this.varyHeaders).build();
      return new Response.Builder().request(localRequest).protocol(this.protocol).code(this.code).message(this.message).headers(this.responseHeaders).body(new Cache.CacheResponseBody(paramSnapshot, str1, str2)).handshake(this.handshake).sentRequestAtMillis(this.sentRequestMillis).receivedResponseAtMillis(this.receivedResponseMillis).build();
    }
    
    public void writeTo(DiskLruCache.Editor paramEditor)
    {
      int i = 0;
      paramEditor = l.a(paramEditor.newSink(0));
      paramEditor.b(this.url).i(10);
      paramEditor.b(this.requestMethod).i(10);
      paramEditor.n(this.varyHeaders.size()).i(10);
      int j = this.varyHeaders.size();
      for (int k = 0; k < j; k++) {
        paramEditor.b(this.varyHeaders.name(k)).b(": ").b(this.varyHeaders.value(k)).i(10);
      }
      paramEditor.b(new StatusLine(this.protocol, this.code, this.message).toString()).i(10);
      paramEditor.n(this.responseHeaders.size() + 2).i(10);
      j = this.responseHeaders.size();
      for (k = i; k < j; k++) {
        paramEditor.b(this.responseHeaders.name(k)).b(": ").b(this.responseHeaders.value(k)).i(10);
      }
      paramEditor.b(SENT_MILLIS).b(": ").n(this.sentRequestMillis).i(10);
      paramEditor.b(RECEIVED_MILLIS).b(": ").n(this.receivedResponseMillis).i(10);
      if (isHttps())
      {
        paramEditor.i(10);
        paramEditor.b(this.handshake.cipherSuite().javaName()).i(10);
        writeCertList(paramEditor, this.handshake.peerCertificates());
        writeCertList(paramEditor, this.handshake.localCertificates());
        paramEditor.b(this.handshake.tlsVersion().javaName()).i(10);
      }
      paramEditor.close();
    }
  }
}


/* Location:              ~/okhttp3/Cache.class
 *
 * Reversed by:           J
 */