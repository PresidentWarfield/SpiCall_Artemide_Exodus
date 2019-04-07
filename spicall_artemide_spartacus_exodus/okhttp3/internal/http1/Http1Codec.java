package okhttp3.internal.http1;

import a.c;
import a.d;
import a.e;
import a.i;
import a.l;
import a.r;
import a.s;
import a.t;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import okhttp3.EventListener;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.http.StatusLine;

public final class Http1Codec
  implements HttpCodec
{
  private static final int HEADER_LIMIT = 262144;
  private static final int STATE_CLOSED = 6;
  private static final int STATE_IDLE = 0;
  private static final int STATE_OPEN_REQUEST_BODY = 1;
  private static final int STATE_OPEN_RESPONSE_BODY = 4;
  private static final int STATE_READING_RESPONSE_BODY = 5;
  private static final int STATE_READ_RESPONSE_HEADERS = 3;
  private static final int STATE_WRITING_REQUEST_BODY = 2;
  final OkHttpClient client;
  private long headerLimit = 262144L;
  final d sink;
  final e source;
  int state = 0;
  final StreamAllocation streamAllocation;
  
  public Http1Codec(OkHttpClient paramOkHttpClient, StreamAllocation paramStreamAllocation, e parame, d paramd)
  {
    this.client = paramOkHttpClient;
    this.streamAllocation = paramStreamAllocation;
    this.source = parame;
    this.sink = paramd;
  }
  
  private String readHeaderLine()
  {
    String str = this.source.f(this.headerLimit);
    this.headerLimit -= str.length();
    return str;
  }
  
  public void cancel()
  {
    RealConnection localRealConnection = this.streamAllocation.connection();
    if (localRealConnection != null) {
      localRealConnection.cancel();
    }
  }
  
  public r createRequestBody(Request paramRequest, long paramLong)
  {
    if ("chunked".equalsIgnoreCase(paramRequest.header("Transfer-Encoding"))) {
      return newChunkedSink();
    }
    if (paramLong != -1L) {
      return newFixedLengthSink(paramLong);
    }
    throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
  }
  
  void detachTimeout(i parami)
  {
    t localt = parami.a();
    parami.a(t.NONE);
    localt.clearDeadline();
    localt.clearTimeout();
  }
  
  public void finishRequest()
  {
    this.sink.flush();
  }
  
  public void flushRequest()
  {
    this.sink.flush();
  }
  
  public boolean isClosed()
  {
    boolean bool;
    if (this.state == 6) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public r newChunkedSink()
  {
    if (this.state == 1)
    {
      this.state = 2;
      return new ChunkedSink();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("state: ");
    localStringBuilder.append(this.state);
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public s newChunkedSource(HttpUrl paramHttpUrl)
  {
    if (this.state == 4)
    {
      this.state = 5;
      return new ChunkedSource(paramHttpUrl);
    }
    paramHttpUrl = new StringBuilder();
    paramHttpUrl.append("state: ");
    paramHttpUrl.append(this.state);
    throw new IllegalStateException(paramHttpUrl.toString());
  }
  
  public r newFixedLengthSink(long paramLong)
  {
    if (this.state == 1)
    {
      this.state = 2;
      return new FixedLengthSink(paramLong);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("state: ");
    localStringBuilder.append(this.state);
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public s newFixedLengthSource(long paramLong)
  {
    if (this.state == 4)
    {
      this.state = 5;
      return new FixedLengthSource(paramLong);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("state: ");
    localStringBuilder.append(this.state);
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public s newUnknownLengthSource()
  {
    if (this.state == 4)
    {
      localObject = this.streamAllocation;
      if (localObject != null)
      {
        this.state = 5;
        ((StreamAllocation)localObject).noNewStreams();
        return new UnknownLengthSource();
      }
      throw new IllegalStateException("streamAllocation == null");
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("state: ");
    ((StringBuilder)localObject).append(this.state);
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public ResponseBody openResponseBody(Response paramResponse)
  {
    this.streamAllocation.eventListener.responseBodyStart(this.streamAllocation.call);
    String str = paramResponse.header("Content-Type");
    if (!HttpHeaders.hasBody(paramResponse)) {
      return new RealResponseBody(str, 0L, l.a(newFixedLengthSource(0L)));
    }
    if ("chunked".equalsIgnoreCase(paramResponse.header("Transfer-Encoding"))) {
      return new RealResponseBody(str, -1L, l.a(newChunkedSource(paramResponse.request().url())));
    }
    long l = HttpHeaders.contentLength(paramResponse);
    if (l != -1L) {
      return new RealResponseBody(str, l, l.a(newFixedLengthSource(l)));
    }
    return new RealResponseBody(str, -1L, l.a(newUnknownLengthSource()));
  }
  
  public Headers readHeaders()
  {
    Headers.Builder localBuilder = new Headers.Builder();
    for (;;)
    {
      String str = readHeaderLine();
      if (str.length() == 0) {
        break;
      }
      Internal.instance.addLenient(localBuilder, str);
    }
    return localBuilder.build();
  }
  
  public Response.Builder readResponseHeaders(boolean paramBoolean)
  {
    int i = this.state;
    Object localObject1;
    if ((i != 1) && (i != 3))
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("state: ");
      ((StringBuilder)localObject1).append(this.state);
      throw new IllegalStateException(((StringBuilder)localObject1).toString());
    }
    try
    {
      localObject1 = StatusLine.parse(readHeaderLine());
      localObject2 = new okhttp3/Response$Builder;
      ((Response.Builder)localObject2).<init>();
      localObject2 = ((Response.Builder)localObject2).protocol(((StatusLine)localObject1).protocol).code(((StatusLine)localObject1).code).message(((StatusLine)localObject1).message).headers(readHeaders());
      if ((paramBoolean) && (((StatusLine)localObject1).code == 100)) {
        return null;
      }
      if (((StatusLine)localObject1).code == 100)
      {
        this.state = 3;
        return (Response.Builder)localObject2;
      }
      this.state = 4;
      return (Response.Builder)localObject2;
    }
    catch (EOFException localEOFException)
    {
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("unexpected end of stream on ");
      ((StringBuilder)localObject2).append(this.streamAllocation);
      localObject2 = new IOException(((StringBuilder)localObject2).toString());
      ((IOException)localObject2).initCause(localEOFException);
      throw ((Throwable)localObject2);
    }
  }
  
  public void writeRequest(Headers paramHeaders, String paramString)
  {
    if (this.state == 0)
    {
      this.sink.b(paramString).b("\r\n");
      int i = 0;
      int j = paramHeaders.size();
      while (i < j)
      {
        this.sink.b(paramHeaders.name(i)).b(": ").b(paramHeaders.value(i)).b("\r\n");
        i++;
      }
      this.sink.b("\r\n");
      this.state = 1;
      return;
    }
    paramHeaders = new StringBuilder();
    paramHeaders.append("state: ");
    paramHeaders.append(this.state);
    throw new IllegalStateException(paramHeaders.toString());
  }
  
  public void writeRequestHeaders(Request paramRequest)
  {
    String str = RequestLine.get(paramRequest, this.streamAllocation.connection().route().proxy().type());
    writeRequest(paramRequest.headers(), str);
  }
  
  private abstract class AbstractSource
    implements s
  {
    protected long bytesRead = 0L;
    protected boolean closed;
    protected final i timeout = new i(Http1Codec.this.source.timeout());
    
    private AbstractSource() {}
    
    protected final void endOfInput(boolean paramBoolean, IOException paramIOException)
    {
      if (Http1Codec.this.state == 6) {
        return;
      }
      if (Http1Codec.this.state == 5)
      {
        Http1Codec.this.detachTimeout(this.timeout);
        Http1Codec localHttp1Codec = Http1Codec.this;
        localHttp1Codec.state = 6;
        if (localHttp1Codec.streamAllocation != null) {
          Http1Codec.this.streamAllocation.streamFinished(paramBoolean ^ true, Http1Codec.this, this.bytesRead, paramIOException);
        }
        return;
      }
      paramIOException = new StringBuilder();
      paramIOException.append("state: ");
      paramIOException.append(Http1Codec.this.state);
      throw new IllegalStateException(paramIOException.toString());
    }
    
    public long read(c paramc, long paramLong)
    {
      try
      {
        paramLong = Http1Codec.this.source.read(paramc, paramLong);
        if (paramLong > 0L) {
          this.bytesRead += paramLong;
        }
        return paramLong;
      }
      catch (IOException paramc)
      {
        endOfInput(false, paramc);
        throw paramc;
      }
    }
    
    public t timeout()
    {
      return this.timeout;
    }
  }
  
  private final class ChunkedSink
    implements r
  {
    private boolean closed;
    private final i timeout = new i(Http1Codec.this.sink.timeout());
    
    ChunkedSink() {}
    
    public void close()
    {
      try
      {
        boolean bool = this.closed;
        if (bool) {
          return;
        }
        this.closed = true;
        Http1Codec.this.sink.b("0\r\n\r\n");
        Http1Codec.this.detachTimeout(this.timeout);
        Http1Codec.this.state = 3;
        return;
      }
      finally {}
    }
    
    public void flush()
    {
      try
      {
        boolean bool = this.closed;
        if (bool) {
          return;
        }
        Http1Codec.this.sink.flush();
        return;
      }
      finally {}
    }
    
    public t timeout()
    {
      return this.timeout;
    }
    
    public void write(c paramc, long paramLong)
    {
      if (!this.closed)
      {
        if (paramLong == 0L) {
          return;
        }
        Http1Codec.this.sink.m(paramLong);
        Http1Codec.this.sink.b("\r\n");
        Http1Codec.this.sink.write(paramc, paramLong);
        Http1Codec.this.sink.b("\r\n");
        return;
      }
      throw new IllegalStateException("closed");
    }
  }
  
  private class ChunkedSource
    extends Http1Codec.AbstractSource
  {
    private static final long NO_CHUNK_YET = -1L;
    private long bytesRemainingInChunk = -1L;
    private boolean hasMoreChunks = true;
    private final HttpUrl url;
    
    ChunkedSource(HttpUrl paramHttpUrl)
    {
      super(null);
      this.url = paramHttpUrl;
    }
    
    private void readChunkSize()
    {
      if (this.bytesRemainingInChunk != -1L) {
        Http1Codec.this.source.r();
      }
      try
      {
        this.bytesRemainingInChunk = Http1Codec.this.source.o();
        String str = Http1Codec.this.source.r().trim();
        if (this.bytesRemainingInChunk >= 0L) {
          if (!str.isEmpty())
          {
            boolean bool = str.startsWith(";");
            if (!bool) {}
          }
          else
          {
            if (this.bytesRemainingInChunk == 0L)
            {
              this.hasMoreChunks = false;
              HttpHeaders.receiveHeaders(Http1Codec.this.client.cookieJar(), this.url, Http1Codec.this.readHeaders());
              endOfInput(true, null);
            }
            return;
          }
        }
        ProtocolException localProtocolException = new java/net/ProtocolException;
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("expected chunk size and optional extensions but was \"");
        localStringBuilder.append(this.bytesRemainingInChunk);
        localStringBuilder.append(str);
        localStringBuilder.append("\"");
        localProtocolException.<init>(localStringBuilder.toString());
        throw localProtocolException;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new ProtocolException(localNumberFormatException.getMessage());
      }
    }
    
    public void close()
    {
      if (this.closed) {
        return;
      }
      if ((this.hasMoreChunks) && (!Util.discard(this, 100, TimeUnit.MILLISECONDS))) {
        endOfInput(false, null);
      }
      this.closed = true;
    }
    
    public long read(c paramc, long paramLong)
    {
      if (paramLong >= 0L)
      {
        if (!this.closed)
        {
          if (!this.hasMoreChunks) {
            return -1L;
          }
          long l = this.bytesRemainingInChunk;
          if ((l == 0L) || (l == -1L))
          {
            readChunkSize();
            if (!this.hasMoreChunks) {
              return -1L;
            }
          }
          paramLong = super.read(paramc, Math.min(paramLong, this.bytesRemainingInChunk));
          if (paramLong != -1L)
          {
            this.bytesRemainingInChunk -= paramLong;
            return paramLong;
          }
          paramc = new ProtocolException("unexpected end of stream");
          endOfInput(false, paramc);
          throw paramc;
        }
        throw new IllegalStateException("closed");
      }
      paramc = new StringBuilder();
      paramc.append("byteCount < 0: ");
      paramc.append(paramLong);
      throw new IllegalArgumentException(paramc.toString());
    }
  }
  
  private final class FixedLengthSink
    implements r
  {
    private long bytesRemaining;
    private boolean closed;
    private final i timeout = new i(Http1Codec.this.sink.timeout());
    
    FixedLengthSink(long paramLong)
    {
      this.bytesRemaining = paramLong;
    }
    
    public void close()
    {
      if (this.closed) {
        return;
      }
      this.closed = true;
      if (this.bytesRemaining <= 0L)
      {
        Http1Codec.this.detachTimeout(this.timeout);
        Http1Codec.this.state = 3;
        return;
      }
      throw new ProtocolException("unexpected end of stream");
    }
    
    public void flush()
    {
      if (this.closed) {
        return;
      }
      Http1Codec.this.sink.flush();
    }
    
    public t timeout()
    {
      return this.timeout;
    }
    
    public void write(c paramc, long paramLong)
    {
      if (!this.closed)
      {
        Util.checkOffsetAndCount(paramc.a(), 0L, paramLong);
        if (paramLong <= this.bytesRemaining)
        {
          Http1Codec.this.sink.write(paramc, paramLong);
          this.bytesRemaining -= paramLong;
          return;
        }
        paramc = new StringBuilder();
        paramc.append("expected ");
        paramc.append(this.bytesRemaining);
        paramc.append(" bytes but received ");
        paramc.append(paramLong);
        throw new ProtocolException(paramc.toString());
      }
      throw new IllegalStateException("closed");
    }
  }
  
  private class FixedLengthSource
    extends Http1Codec.AbstractSource
  {
    private long bytesRemaining;
    
    FixedLengthSource(long paramLong)
    {
      super(null);
      this.bytesRemaining = paramLong;
      if (this.bytesRemaining == 0L) {
        endOfInput(true, null);
      }
    }
    
    public void close()
    {
      if (this.closed) {
        return;
      }
      if ((this.bytesRemaining != 0L) && (!Util.discard(this, 100, TimeUnit.MILLISECONDS))) {
        endOfInput(false, null);
      }
      this.closed = true;
    }
    
    public long read(c paramc, long paramLong)
    {
      if (paramLong >= 0L)
      {
        if (!this.closed)
        {
          long l = this.bytesRemaining;
          if (l == 0L) {
            return -1L;
          }
          paramLong = super.read(paramc, Math.min(l, paramLong));
          if (paramLong != -1L)
          {
            this.bytesRemaining -= paramLong;
            if (this.bytesRemaining == 0L) {
              endOfInput(true, null);
            }
            return paramLong;
          }
          paramc = new ProtocolException("unexpected end of stream");
          endOfInput(false, paramc);
          throw paramc;
        }
        throw new IllegalStateException("closed");
      }
      paramc = new StringBuilder();
      paramc.append("byteCount < 0: ");
      paramc.append(paramLong);
      throw new IllegalArgumentException(paramc.toString());
    }
  }
  
  private class UnknownLengthSource
    extends Http1Codec.AbstractSource
  {
    private boolean inputExhausted;
    
    UnknownLengthSource()
    {
      super(null);
    }
    
    public void close()
    {
      if (this.closed) {
        return;
      }
      if (!this.inputExhausted) {
        endOfInput(false, null);
      }
      this.closed = true;
    }
    
    public long read(c paramc, long paramLong)
    {
      if (paramLong >= 0L)
      {
        if (!this.closed)
        {
          if (this.inputExhausted) {
            return -1L;
          }
          paramLong = super.read(paramc, paramLong);
          if (paramLong == -1L)
          {
            this.inputExhausted = true;
            endOfInput(true, null);
            return -1L;
          }
          return paramLong;
        }
        throw new IllegalStateException("closed");
      }
      paramc = new StringBuilder();
      paramc.append("byteCount < 0: ");
      paramc.append(paramLong);
      throw new IllegalArgumentException(paramc.toString());
    }
  }
}


/* Location:              ~/okhttp3/internal/http1/Http1Codec.class
 *
 * Reversed by:           J
 */