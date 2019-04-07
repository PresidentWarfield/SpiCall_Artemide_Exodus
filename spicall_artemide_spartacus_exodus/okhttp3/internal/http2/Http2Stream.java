package okhttp3.internal.http2;

import a.a;
import a.c;
import a.e;
import a.r;
import a.s;
import a.t;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.internal.Util;

public final class Http2Stream
{
  long bytesLeftInWriteWindow;
  final Http2Connection connection;
  ErrorCode errorCode = null;
  private boolean hasResponseHeaders;
  private Header.Listener headersListener;
  private final Deque<Headers> headersQueue = new ArrayDeque();
  final int id;
  final StreamTimeout readTimeout = new StreamTimeout();
  final FramingSink sink;
  private final FramingSource source;
  long unacknowledgedBytesRead = 0L;
  final StreamTimeout writeTimeout = new StreamTimeout();
  
  Http2Stream(int paramInt, Http2Connection paramHttp2Connection, boolean paramBoolean1, boolean paramBoolean2, @Nullable Headers paramHeaders)
  {
    if (paramHttp2Connection != null)
    {
      this.id = paramInt;
      this.connection = paramHttp2Connection;
      this.bytesLeftInWriteWindow = paramHttp2Connection.peerSettings.getInitialWindowSize();
      this.source = new FramingSource(paramHttp2Connection.okHttpSettings.getInitialWindowSize());
      this.sink = new FramingSink();
      this.source.finished = paramBoolean2;
      this.sink.finished = paramBoolean1;
      if (paramHeaders != null) {
        this.headersQueue.add(paramHeaders);
      }
      if ((isLocallyInitiated()) && (paramHeaders != null)) {
        throw new IllegalStateException("locally-initiated streams shouldn't have headers yet");
      }
      if ((!isLocallyInitiated()) && (paramHeaders == null)) {
        throw new IllegalStateException("remotely-initiated streams should have headers");
      }
      return;
    }
    throw new NullPointerException("connection == null");
  }
  
  private boolean closeInternal(ErrorCode paramErrorCode)
  {
    try
    {
      if (this.errorCode != null) {
        return false;
      }
      if ((this.source.finished) && (this.sink.finished)) {
        return false;
      }
      this.errorCode = paramErrorCode;
      notifyAll();
      this.connection.removeStream(this.id);
      return true;
    }
    finally {}
  }
  
  void addBytesToWriteWindow(long paramLong)
  {
    this.bytesLeftInWriteWindow += paramLong;
    if (paramLong > 0L) {
      notifyAll();
    }
  }
  
  void cancelStreamIfNecessary()
  {
    try
    {
      int i;
      if ((!this.source.finished) && (this.source.closed) && ((this.sink.finished) || (this.sink.closed))) {
        i = 1;
      } else {
        i = 0;
      }
      boolean bool = isOpen();
      if (i != 0) {
        close(ErrorCode.CANCEL);
      } else if (!bool) {
        this.connection.removeStream(this.id);
      }
      return;
    }
    finally {}
  }
  
  void checkOutNotClosed()
  {
    if (!this.sink.closed)
    {
      if (!this.sink.finished)
      {
        ErrorCode localErrorCode = this.errorCode;
        if (localErrorCode == null) {
          return;
        }
        throw new StreamResetException(localErrorCode);
      }
      throw new IOException("stream finished");
    }
    throw new IOException("stream closed");
  }
  
  public void close(ErrorCode paramErrorCode)
  {
    if (!closeInternal(paramErrorCode)) {
      return;
    }
    this.connection.writeSynReset(this.id, paramErrorCode);
  }
  
  public void closeLater(ErrorCode paramErrorCode)
  {
    if (!closeInternal(paramErrorCode)) {
      return;
    }
    this.connection.writeSynResetLater(this.id, paramErrorCode);
  }
  
  public Http2Connection getConnection()
  {
    return this.connection;
  }
  
  public ErrorCode getErrorCode()
  {
    try
    {
      ErrorCode localErrorCode = this.errorCode;
      return localErrorCode;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public r getSink()
  {
    try
    {
      if ((!this.hasResponseHeaders) && (!isLocallyInitiated()))
      {
        IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
        localIllegalStateException.<init>("reply before requesting the sink");
        throw localIllegalStateException;
      }
      return this.sink;
    }
    finally {}
  }
  
  public s getSource()
  {
    return this.source;
  }
  
  public boolean isLocallyInitiated()
  {
    int i = this.id;
    boolean bool1 = true;
    boolean bool2;
    if ((i & 0x1) == 1) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    if (this.connection.client == bool2) {
      bool2 = bool1;
    } else {
      bool2 = false;
    }
    return bool2;
  }
  
  public boolean isOpen()
  {
    try
    {
      ErrorCode localErrorCode = this.errorCode;
      if (localErrorCode != null) {
        return false;
      }
      if (((this.source.finished) || (this.source.closed)) && ((this.sink.finished) || (this.sink.closed)))
      {
        boolean bool = this.hasResponseHeaders;
        if (bool) {
          return false;
        }
      }
      return true;
    }
    finally {}
  }
  
  public t readTimeout()
  {
    return this.readTimeout;
  }
  
  void receiveData(e parame, int paramInt)
  {
    this.source.receive(parame, paramInt);
  }
  
  void receiveFin()
  {
    try
    {
      this.source.finished = true;
      boolean bool = isOpen();
      notifyAll();
      if (!bool) {
        this.connection.removeStream(this.id);
      }
      return;
    }
    finally {}
  }
  
  void receiveHeaders(List<Header> paramList)
  {
    try
    {
      this.hasResponseHeaders = true;
      this.headersQueue.add(Util.toHeaders(paramList));
      boolean bool = isOpen();
      notifyAll();
      if (!bool) {
        this.connection.removeStream(this.id);
      }
      return;
    }
    finally {}
  }
  
  void receiveRstStream(ErrorCode paramErrorCode)
  {
    try
    {
      if (this.errorCode == null)
      {
        this.errorCode = paramErrorCode;
        notifyAll();
      }
      return;
    }
    finally
    {
      paramErrorCode = finally;
      throw paramErrorCode;
    }
  }
  
  public void setHeadersListener(Header.Listener paramListener)
  {
    try
    {
      this.headersListener = paramListener;
      if ((!this.headersQueue.isEmpty()) && (paramListener != null)) {
        notifyAll();
      }
      return;
    }
    finally {}
  }
  
  /* Error */
  public Headers takeHeaders()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 58	okhttp3/internal/http2/Http2Stream:readTimeout	Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
    //   6: invokevirtual 221	okhttp3/internal/http2/Http2Stream$StreamTimeout:enter	()V
    //   9: aload_0
    //   10: getfield 53	okhttp3/internal/http2/Http2Stream:headersQueue	Ljava/util/Deque;
    //   13: invokeinterface 216 1 0
    //   18: ifeq +17 -> 35
    //   21: aload_0
    //   22: getfield 62	okhttp3/internal/http2/Http2Stream:errorCode	Lokhttp3/internal/http2/ErrorCode;
    //   25: ifnonnull +10 -> 35
    //   28: aload_0
    //   29: invokevirtual 224	okhttp3/internal/http2/Http2Stream:waitForIo	()V
    //   32: goto -23 -> 9
    //   35: aload_0
    //   36: getfield 58	okhttp3/internal/http2/Http2Stream:readTimeout	Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
    //   39: invokevirtual 227	okhttp3/internal/http2/Http2Stream$StreamTimeout:exitAndThrowIfTimedOut	()V
    //   42: aload_0
    //   43: getfield 53	okhttp3/internal/http2/Http2Stream:headersQueue	Ljava/util/Deque;
    //   46: invokeinterface 216 1 0
    //   51: ifne +20 -> 71
    //   54: aload_0
    //   55: getfield 53	okhttp3/internal/http2/Http2Stream:headersQueue	Ljava/util/Deque;
    //   58: invokeinterface 231 1 0
    //   63: checkcast 233	okhttp3/Headers
    //   66: astore_1
    //   67: aload_0
    //   68: monitorexit
    //   69: aload_1
    //   70: areturn
    //   71: new 157	okhttp3/internal/http2/StreamResetException
    //   74: astore_1
    //   75: aload_1
    //   76: aload_0
    //   77: getfield 62	okhttp3/internal/http2/Http2Stream:errorCode	Lokhttp3/internal/http2/ErrorCode;
    //   80: invokespecial 159	okhttp3/internal/http2/StreamResetException:<init>	(Lokhttp3/internal/http2/ErrorCode;)V
    //   83: aload_1
    //   84: athrow
    //   85: astore_1
    //   86: aload_0
    //   87: getfield 58	okhttp3/internal/http2/Http2Stream:readTimeout	Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
    //   90: invokevirtual 227	okhttp3/internal/http2/Http2Stream$StreamTimeout:exitAndThrowIfTimedOut	()V
    //   93: aload_1
    //   94: athrow
    //   95: astore_1
    //   96: aload_0
    //   97: monitorexit
    //   98: aload_1
    //   99: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	100	0	this	Http2Stream
    //   66	18	1	localObject1	Object
    //   85	9	1	localObject2	Object
    //   95	4	1	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   9	32	85	finally
    //   2	9	95	finally
    //   35	67	95	finally
    //   71	85	95	finally
    //   86	95	95	finally
  }
  
  void waitForIo()
  {
    try
    {
      wait();
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      Thread.currentThread().interrupt();
      throw new InterruptedIOException();
    }
  }
  
  public void writeHeaders(List<Header> paramList, boolean paramBoolean)
  {
    if (paramList != null) {
      try
      {
        this.hasResponseHeaders = true;
        int i;
        if (!paramBoolean)
        {
          this.sink.finished = true;
          i = 1;
          paramBoolean = true;
        }
        else
        {
          i = 0;
          paramBoolean = false;
        }
        int j = i;
        if (i == 0) {
          synchronized (this.connection)
          {
            if (this.connection.bytesLeftInWriteWindow == 0L) {
              i = 1;
            } else {
              i = 0;
            }
            j = i;
          }
        }
        this.connection.writeSynReply(this.id, paramBoolean, paramList);
        if (j != 0) {
          this.connection.flush();
        }
        return;
      }
      finally {}
    }
    throw new NullPointerException("headers == null");
  }
  
  public t writeTimeout()
  {
    return this.writeTimeout;
  }
  
  final class FramingSink
    implements r
  {
    private static final long EMIT_BUFFER_SIZE = 16384L;
    boolean closed;
    boolean finished;
    private final c sendBuffer = new c();
    
    FramingSink() {}
    
    private void emitFrame(boolean paramBoolean)
    {
      synchronized (Http2Stream.this)
      {
        Http2Stream.this.writeTimeout.enter();
        try
        {
          while ((Http2Stream.this.bytesLeftInWriteWindow <= 0L) && (!this.finished) && (!this.closed) && (Http2Stream.this.errorCode == null)) {
            Http2Stream.this.waitForIo();
          }
          Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
          Http2Stream.this.checkOutNotClosed();
          long l = Math.min(Http2Stream.this.bytesLeftInWriteWindow, this.sendBuffer.a());
          Http2Stream localHttp2Stream = Http2Stream.this;
          localHttp2Stream.bytesLeftInWriteWindow -= l;
          Http2Stream.this.writeTimeout.enter();
          try
          {
            ??? = Http2Stream.this.connection;
            int i = Http2Stream.this.id;
            if ((paramBoolean) && (l == this.sendBuffer.a())) {
              paramBoolean = true;
            } else {
              paramBoolean = false;
            }
            ((Http2Connection)???).writeData(i, paramBoolean, this.sendBuffer, l);
            return;
          }
          finally
          {
            Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
          }
          localObject4 = finally;
        }
        finally
        {
          Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
        }
      }
    }
    
    public void close()
    {
      synchronized (Http2Stream.this)
      {
        if (this.closed) {
          return;
        }
        if (!Http2Stream.this.sink.finished)
        {
          if (this.sendBuffer.a() > 0L) {
            while (this.sendBuffer.a() > 0L) {
              emitFrame(true);
            }
          }
          Http2Stream.this.connection.writeData(Http2Stream.this.id, true, null, 0L);
        }
        synchronized (Http2Stream.this)
        {
          this.closed = true;
          Http2Stream.this.connection.flush();
          Http2Stream.this.cancelStreamIfNecessary();
          return;
        }
      }
    }
    
    public void flush()
    {
      synchronized (Http2Stream.this)
      {
        Http2Stream.this.checkOutNotClosed();
        while (this.sendBuffer.a() > 0L)
        {
          emitFrame(false);
          Http2Stream.this.connection.flush();
        }
        return;
      }
    }
    
    public t timeout()
    {
      return Http2Stream.this.writeTimeout;
    }
    
    public void write(c paramc, long paramLong)
    {
      this.sendBuffer.write(paramc, paramLong);
      while (this.sendBuffer.a() >= 16384L) {
        emitFrame(false);
      }
    }
  }
  
  private final class FramingSource
    implements s
  {
    boolean closed;
    boolean finished;
    private final long maxByteCount;
    private final c readBuffer = new c();
    private final c receiveBuffer = new c();
    
    FramingSource(long paramLong)
    {
      this.maxByteCount = paramLong;
    }
    
    private void updateConnectionFlowControl(long paramLong)
    {
      Http2Stream.this.connection.updateConnectionFlowControl(paramLong);
    }
    
    public void close()
    {
      synchronized (Http2Stream.this)
      {
        this.closed = true;
        long l = this.readBuffer.a();
        this.readBuffer.t();
        boolean bool = Http2Stream.this.headersQueue.isEmpty();
        Object localObject1 = null;
        Header.Listener localListener;
        if ((!bool) && (Http2Stream.this.headersListener != null))
        {
          localObject1 = new java/util/ArrayList;
          ((ArrayList)localObject1).<init>(Http2Stream.this.headersQueue);
          Http2Stream.this.headersQueue.clear();
          localListener = Http2Stream.this.headersListener;
        }
        else
        {
          localListener = null;
        }
        Http2Stream.this.notifyAll();
        if (l > 0L) {
          updateConnectionFlowControl(l);
        }
        Http2Stream.this.cancelStreamIfNecessary();
        if (localListener != null)
        {
          localObject1 = ((List)localObject1).iterator();
          while (((Iterator)localObject1).hasNext()) {
            localListener.onHeaders((Headers)((Iterator)localObject1).next());
          }
        }
        return;
      }
    }
    
    public long read(c paramc, long paramLong)
    {
      if (paramLong >= 0L) {
        synchronized (Http2Stream.this)
        {
          for (;;)
          {
            Http2Stream.this.readTimeout.enter();
            try
            {
              ErrorCode localErrorCode;
              if (Http2Stream.this.errorCode != null) {
                localErrorCode = Http2Stream.this.errorCode;
              } else {
                localErrorCode = null;
              }
              if (!this.closed)
              {
                Object localObject;
                Header.Listener localListener;
                long l1;
                if ((!Http2Stream.this.headersQueue.isEmpty()) && (Http2Stream.this.headersListener != null))
                {
                  localObject = (Headers)Http2Stream.this.headersQueue.removeFirst();
                  localListener = Http2Stream.this.headersListener;
                  l1 = -1L;
                }
                else
                {
                  if (this.readBuffer.a() > 0L)
                  {
                    long l2 = this.readBuffer.read(paramc, Math.min(paramLong, this.readBuffer.a()));
                    localObject = Http2Stream.this;
                    ((Http2Stream)localObject).unacknowledgedBytesRead += l2;
                    l1 = l2;
                    if (localErrorCode == null)
                    {
                      l1 = l2;
                      if (Http2Stream.this.unacknowledgedBytesRead >= Http2Stream.this.connection.okHttpSettings.getInitialWindowSize() / 2)
                      {
                        Http2Stream.this.connection.writeWindowUpdateLater(Http2Stream.this.id, Http2Stream.this.unacknowledgedBytesRead);
                        Http2Stream.this.unacknowledgedBytesRead = 0L;
                        l1 = l2;
                      }
                    }
                  }
                  else
                  {
                    if ((!this.finished) && (localErrorCode == null))
                    {
                      Http2Stream.this.waitForIo();
                      Http2Stream.this.readTimeout.exitAndThrowIfTimedOut();
                      continue;
                    }
                    l1 = -1L;
                  }
                  localObject = null;
                  localListener = null;
                }
                Http2Stream.this.readTimeout.exitAndThrowIfTimedOut();
                if ((localObject != null) && (localListener != null))
                {
                  localListener.onHeaders((Headers)localObject);
                }
                else
                {
                  if (l1 != -1L)
                  {
                    updateConnectionFlowControl(l1);
                    return l1;
                  }
                  if (localErrorCode == null) {
                    return -1L;
                  }
                  throw new StreamResetException(localErrorCode);
                }
              }
              else
              {
                paramc = new java/io/IOException;
                paramc.<init>("stream closed");
                throw paramc;
              }
            }
            finally
            {
              Http2Stream.this.readTimeout.exitAndThrowIfTimedOut();
            }
          }
        }
      }
      paramc = new StringBuilder();
      paramc.append("byteCount < 0: ");
      paramc.append(paramLong);
      throw new IllegalArgumentException(paramc.toString());
    }
    
    void receive(e parame, long paramLong)
    {
      if (paramLong > 0L) {
        synchronized (Http2Stream.this)
        {
          boolean bool = this.finished;
          long l1 = this.readBuffer.a();
          long l2 = this.maxByteCount;
          int i = 1;
          int j;
          if (l1 + paramLong > l2) {
            j = 1;
          } else {
            j = 0;
          }
          if (j != 0)
          {
            parame.i(paramLong);
            Http2Stream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
            return;
          }
          if (bool)
          {
            parame.i(paramLong);
            return;
          }
          l1 = parame.read(this.receiveBuffer, paramLong);
          if (l1 != -1L)
          {
            paramLong -= l1;
            synchronized (Http2Stream.this)
            {
              if (this.readBuffer.a() == 0L) {
                j = i;
              } else {
                j = 0;
              }
              this.readBuffer.a(this.receiveBuffer);
              if (j != 0) {
                Http2Stream.this.notifyAll();
              }
            }
          }
          throw new EOFException();
        }
      }
    }
    
    public t timeout()
    {
      return Http2Stream.this.readTimeout;
    }
  }
  
  class StreamTimeout
    extends a
  {
    StreamTimeout() {}
    
    public void exitAndThrowIfTimedOut()
    {
      if (!exit()) {
        return;
      }
      throw newTimeoutException(null);
    }
    
    protected IOException newTimeoutException(IOException paramIOException)
    {
      SocketTimeoutException localSocketTimeoutException = new SocketTimeoutException("timeout");
      if (paramIOException != null) {
        localSocketTimeoutException.initCause(paramIOException);
      }
      return localSocketTimeoutException;
    }
    
    protected void timedOut()
    {
      Http2Stream.this.closeLater(ErrorCode.CANCEL);
    }
  }
}


/* Location:              ~/okhttp3/internal/http2/Http2Stream.class
 *
 * Reversed by:           J
 */