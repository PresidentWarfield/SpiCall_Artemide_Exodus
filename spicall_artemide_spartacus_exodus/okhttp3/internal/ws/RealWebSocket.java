package okhttp3.internal.ws;

import a.d;
import a.e;
import a.f;
import a.t;
import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;

public final class RealWebSocket
  implements WebSocket, WebSocketReader.FrameCallback
{
  private static final long CANCEL_AFTER_CLOSE_MILLIS = 60000L;
  private static final long MAX_QUEUE_SIZE = 16777216L;
  private static final List<Protocol> ONLY_HTTP1 = Collections.singletonList(Protocol.HTTP_1_1);
  private boolean awaitingPong;
  private Call call;
  private ScheduledFuture<?> cancelFuture;
  private boolean enqueuedClose;
  private ScheduledExecutorService executor;
  private boolean failed;
  private final String key;
  final WebSocketListener listener;
  private final ArrayDeque<Object> messageAndCloseQueue = new ArrayDeque();
  private final Request originalRequest;
  private final long pingIntervalMillis;
  private final ArrayDeque<f> pongQueue = new ArrayDeque();
  private long queueSize;
  private final Random random;
  private WebSocketReader reader;
  private int receivedCloseCode = -1;
  private String receivedCloseReason;
  private int receivedPingCount;
  private int receivedPongCount;
  private int sentPingCount;
  private Streams streams;
  private WebSocketWriter writer;
  private final Runnable writerRunnable;
  
  public RealWebSocket(Request paramRequest, WebSocketListener paramWebSocketListener, Random paramRandom, long paramLong)
  {
    if ("GET".equals(paramRequest.method()))
    {
      this.originalRequest = paramRequest;
      this.listener = paramWebSocketListener;
      this.random = paramRandom;
      this.pingIntervalMillis = paramLong;
      paramRequest = new byte[16];
      paramRandom.nextBytes(paramRequest);
      this.key = f.a(paramRequest).b();
      this.writerRunnable = new Runnable()
      {
        public void run()
        {
          try
          {
            boolean bool;
            do
            {
              bool = RealWebSocket.this.writeOneFrame();
            } while (bool);
            return;
          }
          catch (IOException localIOException)
          {
            RealWebSocket.this.failWebSocket(localIOException, null);
          }
        }
      };
      return;
    }
    paramWebSocketListener = new StringBuilder();
    paramWebSocketListener.append("Request must be GET: ");
    paramWebSocketListener.append(paramRequest.method());
    throw new IllegalArgumentException(paramWebSocketListener.toString());
  }
  
  private void runWriter()
  {
    ScheduledExecutorService localScheduledExecutorService = this.executor;
    if (localScheduledExecutorService != null) {
      localScheduledExecutorService.execute(this.writerRunnable);
    }
  }
  
  private boolean send(f paramf, int paramInt)
  {
    try
    {
      if ((!this.failed) && (!this.enqueuedClose))
      {
        if (this.queueSize + paramf.h() > 16777216L)
        {
          close(1001, null);
          return false;
        }
        this.queueSize += paramf.h();
        ArrayDeque localArrayDeque = this.messageAndCloseQueue;
        Message localMessage = new okhttp3/internal/ws/RealWebSocket$Message;
        localMessage.<init>(paramInt, paramf);
        localArrayDeque.add(localMessage);
        runWriter();
        return true;
      }
      return false;
    }
    finally {}
  }
  
  void awaitTermination(int paramInt, TimeUnit paramTimeUnit)
  {
    this.executor.awaitTermination(paramInt, paramTimeUnit);
  }
  
  public void cancel()
  {
    this.call.cancel();
  }
  
  void checkResponse(Response paramResponse)
  {
    if (paramResponse.code() == 101)
    {
      localObject = paramResponse.header("Connection");
      if ("Upgrade".equalsIgnoreCase((String)localObject))
      {
        localObject = paramResponse.header("Upgrade");
        if ("websocket".equalsIgnoreCase((String)localObject))
        {
          paramResponse = paramResponse.header("Sec-WebSocket-Accept");
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append(this.key);
          ((StringBuilder)localObject).append("258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
          localObject = f.a(((StringBuilder)localObject).toString()).d().b();
          if (((String)localObject).equals(paramResponse)) {
            return;
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Expected 'Sec-WebSocket-Accept' header value '");
          localStringBuilder.append((String)localObject);
          localStringBuilder.append("' but was '");
          localStringBuilder.append(paramResponse);
          localStringBuilder.append("'");
          throw new ProtocolException(localStringBuilder.toString());
        }
        paramResponse = new StringBuilder();
        paramResponse.append("Expected 'Upgrade' header value 'websocket' but was '");
        paramResponse.append((String)localObject);
        paramResponse.append("'");
        throw new ProtocolException(paramResponse.toString());
      }
      paramResponse = new StringBuilder();
      paramResponse.append("Expected 'Connection' header value 'Upgrade' but was '");
      paramResponse.append((String)localObject);
      paramResponse.append("'");
      throw new ProtocolException(paramResponse.toString());
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected HTTP 101 response but was '");
    ((StringBuilder)localObject).append(paramResponse.code());
    ((StringBuilder)localObject).append(" ");
    ((StringBuilder)localObject).append(paramResponse.message());
    ((StringBuilder)localObject).append("'");
    throw new ProtocolException(((StringBuilder)localObject).toString());
  }
  
  public boolean close(int paramInt, String paramString)
  {
    return close(paramInt, paramString, 60000L);
  }
  
  boolean close(int paramInt, String paramString, long paramLong)
  {
    try
    {
      WebSocketProtocol.validateCloseCode(paramInt);
      Object localObject1 = null;
      Object localObject2;
      if (paramString != null)
      {
        localObject1 = f.a(paramString);
        if (((f)localObject1).h() > 123L)
        {
          localObject2 = new java/lang/IllegalArgumentException;
          localObject1 = new java/lang/StringBuilder;
          ((StringBuilder)localObject1).<init>();
          ((StringBuilder)localObject1).append("reason.size() > 123: ");
          ((StringBuilder)localObject1).append(paramString);
          ((IllegalArgumentException)localObject2).<init>(((StringBuilder)localObject1).toString());
          throw ((Throwable)localObject2);
        }
      }
      if ((!this.failed) && (!this.enqueuedClose))
      {
        this.enqueuedClose = true;
        localObject2 = this.messageAndCloseQueue;
        paramString = new okhttp3/internal/ws/RealWebSocket$Close;
        paramString.<init>(paramInt, (f)localObject1, paramLong);
        ((ArrayDeque)localObject2).add(paramString);
        runWriter();
        return true;
      }
      return false;
    }
    finally {}
  }
  
  public void connect(final OkHttpClient paramOkHttpClient)
  {
    OkHttpClient localOkHttpClient = paramOkHttpClient.newBuilder().eventListener(EventListener.NONE).protocols(ONLY_HTTP1).build();
    paramOkHttpClient = this.originalRequest.newBuilder().header("Upgrade", "websocket").header("Connection", "Upgrade").header("Sec-WebSocket-Key", this.key).header("Sec-WebSocket-Version", "13").build();
    this.call = Internal.instance.newWebSocketCall(localOkHttpClient, paramOkHttpClient);
    this.call.timeout().clearTimeout();
    this.call.enqueue(new Callback()
    {
      public void onFailure(Call paramAnonymousCall, IOException paramAnonymousIOException)
      {
        RealWebSocket.this.failWebSocket(paramAnonymousIOException, null);
      }
      
      public void onResponse(Call paramAnonymousCall, Response paramAnonymousResponse)
      {
        try
        {
          RealWebSocket.this.checkResponse(paramAnonymousResponse);
          StreamAllocation localStreamAllocation = Internal.instance.streamAllocation(paramAnonymousCall);
          localStreamAllocation.noNewStreams();
          paramAnonymousCall = localStreamAllocation.connection().newWebSocketStreams(localStreamAllocation);
          try
          {
            RealWebSocket.this.listener.onOpen(RealWebSocket.this, paramAnonymousResponse);
            paramAnonymousResponse = new java/lang/StringBuilder;
            paramAnonymousResponse.<init>();
            paramAnonymousResponse.append("OkHttp WebSocket ");
            paramAnonymousResponse.append(paramOkHttpClient.url().redact());
            paramAnonymousResponse = paramAnonymousResponse.toString();
            RealWebSocket.this.initReaderAndWriter(paramAnonymousResponse, paramAnonymousCall);
            localStreamAllocation.connection().socket().setSoTimeout(0);
            RealWebSocket.this.loopReader();
          }
          catch (Exception paramAnonymousCall)
          {
            RealWebSocket.this.failWebSocket(paramAnonymousCall, null);
          }
          return;
        }
        catch (ProtocolException paramAnonymousCall)
        {
          RealWebSocket.this.failWebSocket(paramAnonymousCall, paramAnonymousResponse);
          Util.closeQuietly(paramAnonymousResponse);
        }
      }
    });
  }
  
  /* Error */
  public void failWebSocket(Exception paramException, @javax.annotation.Nullable Response paramResponse)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 183	okhttp3/internal/ws/RealWebSocket:failed	Z
    //   6: ifeq +6 -> 12
    //   9: aload_0
    //   10: monitorexit
    //   11: return
    //   12: aload_0
    //   13: iconst_1
    //   14: putfield 183	okhttp3/internal/ws/RealWebSocket:failed	Z
    //   17: aload_0
    //   18: getfield 362	okhttp3/internal/ws/RealWebSocket:streams	Lokhttp3/internal/ws/RealWebSocket$Streams;
    //   21: astore_3
    //   22: aload_0
    //   23: aconst_null
    //   24: putfield 362	okhttp3/internal/ws/RealWebSocket:streams	Lokhttp3/internal/ws/RealWebSocket$Streams;
    //   27: aload_0
    //   28: getfield 364	okhttp3/internal/ws/RealWebSocket:cancelFuture	Ljava/util/concurrent/ScheduledFuture;
    //   31: ifnull +14 -> 45
    //   34: aload_0
    //   35: getfield 364	okhttp3/internal/ws/RealWebSocket:cancelFuture	Ljava/util/concurrent/ScheduledFuture;
    //   38: iconst_0
    //   39: invokeinterface 369 2 0
    //   44: pop
    //   45: aload_0
    //   46: getfield 173	okhttp3/internal/ws/RealWebSocket:executor	Ljava/util/concurrent/ScheduledExecutorService;
    //   49: ifnull +12 -> 61
    //   52: aload_0
    //   53: getfield 173	okhttp3/internal/ws/RealWebSocket:executor	Ljava/util/concurrent/ScheduledExecutorService;
    //   56: invokeinterface 372 1 0
    //   61: aload_0
    //   62: monitorexit
    //   63: aload_0
    //   64: getfield 127	okhttp3/internal/ws/RealWebSocket:listener	Lokhttp3/WebSocketListener;
    //   67: aload_0
    //   68: aload_1
    //   69: aload_2
    //   70: invokevirtual 378	okhttp3/WebSocketListener:onFailure	(Lokhttp3/WebSocket;Ljava/lang/Throwable;Lokhttp3/Response;)V
    //   73: aload_3
    //   74: invokestatic 384	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   77: return
    //   78: astore_1
    //   79: aload_3
    //   80: invokestatic 384	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   83: aload_1
    //   84: athrow
    //   85: astore_1
    //   86: aload_0
    //   87: monitorexit
    //   88: aload_1
    //   89: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	90	0	this	RealWebSocket
    //   0	90	1	paramException	Exception
    //   0	90	2	paramResponse	Response
    //   21	59	3	localStreams	Streams
    // Exception table:
    //   from	to	target	type
    //   63	73	78	finally
    //   2	11	85	finally
    //   12	45	85	finally
    //   45	61	85	finally
    //   61	63	85	finally
    //   86	88	85	finally
  }
  
  public void initReaderAndWriter(String paramString, Streams paramStreams)
  {
    try
    {
      this.streams = paramStreams;
      Object localObject = new okhttp3/internal/ws/WebSocketWriter;
      ((WebSocketWriter)localObject).<init>(paramStreams.client, paramStreams.sink, this.random);
      this.writer = ((WebSocketWriter)localObject);
      localObject = new java/util/concurrent/ScheduledThreadPoolExecutor;
      ((ScheduledThreadPoolExecutor)localObject).<init>(1, Util.threadFactory(paramString, false));
      this.executor = ((ScheduledExecutorService)localObject);
      if (this.pingIntervalMillis != 0L)
      {
        paramString = this.executor;
        localObject = new okhttp3/internal/ws/RealWebSocket$PingRunnable;
        ((PingRunnable)localObject).<init>(this);
        paramString.scheduleAtFixedRate((Runnable)localObject, this.pingIntervalMillis, this.pingIntervalMillis, TimeUnit.MILLISECONDS);
      }
      if (!this.messageAndCloseQueue.isEmpty()) {
        runWriter();
      }
      this.reader = new WebSocketReader(paramStreams.client, paramStreams.source, this);
      return;
    }
    finally {}
  }
  
  public void loopReader()
  {
    while (this.receivedCloseCode == -1) {
      this.reader.processNextFrame();
    }
  }
  
  public void onReadClose(int paramInt, String paramString)
  {
    if (paramInt != -1) {
      try
      {
        if (this.receivedCloseCode == -1)
        {
          this.receivedCloseCode = paramInt;
          this.receivedCloseReason = paramString;
          Streams localStreams;
          if ((this.enqueuedClose) && (this.messageAndCloseQueue.isEmpty()))
          {
            localStreams = this.streams;
            this.streams = null;
            if (this.cancelFuture != null) {
              this.cancelFuture.cancel(false);
            }
            this.executor.shutdown();
          }
          else
          {
            localStreams = null;
          }
          try
          {
            this.listener.onClosing(this, paramInt, paramString);
            if (localStreams != null) {
              this.listener.onClosed(this, paramInt, paramString);
            }
            return;
          }
          finally
          {
            Util.closeQuietly(localStreams);
          }
        }
        paramString = new java/lang/IllegalStateException;
        paramString.<init>("already closed");
        throw paramString;
      }
      finally {}
    }
    throw new IllegalArgumentException();
  }
  
  public void onReadMessage(f paramf)
  {
    this.listener.onMessage(this, paramf);
  }
  
  public void onReadMessage(String paramString)
  {
    this.listener.onMessage(this, paramString);
  }
  
  public void onReadPing(f paramf)
  {
    try
    {
      if ((!this.failed) && ((!this.enqueuedClose) || (!this.messageAndCloseQueue.isEmpty())))
      {
        this.pongQueue.add(paramf);
        runWriter();
        this.receivedPingCount += 1;
        return;
      }
      return;
    }
    finally {}
  }
  
  public void onReadPong(f paramf)
  {
    try
    {
      this.receivedPongCount += 1;
      this.awaitingPong = false;
      return;
    }
    finally
    {
      paramf = finally;
      throw paramf;
    }
  }
  
  boolean pong(f paramf)
  {
    try
    {
      if ((!this.failed) && ((!this.enqueuedClose) || (!this.messageAndCloseQueue.isEmpty())))
      {
        this.pongQueue.add(paramf);
        runWriter();
        return true;
      }
      return false;
    }
    finally {}
  }
  
  boolean processNextFrame()
  {
    boolean bool = false;
    try
    {
      this.reader.processNextFrame();
      int i = this.receivedCloseCode;
      if (i == -1) {
        bool = true;
      }
      return bool;
    }
    catch (Exception localException)
    {
      failWebSocket(localException, null);
    }
    return false;
  }
  
  public long queueSize()
  {
    try
    {
      long l = this.queueSize;
      return l;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  int receivedPingCount()
  {
    try
    {
      int i = this.receivedPingCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  int receivedPongCount()
  {
    try
    {
      int i = this.receivedPongCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public Request request()
  {
    return this.originalRequest;
  }
  
  public boolean send(f paramf)
  {
    if (paramf != null) {
      return send(paramf, 2);
    }
    throw new NullPointerException("bytes == null");
  }
  
  public boolean send(String paramString)
  {
    if (paramString != null) {
      return send(f.a(paramString), 1);
    }
    throw new NullPointerException("text == null");
  }
  
  int sentPingCount()
  {
    try
    {
      int i = this.sentPingCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  void tearDown()
  {
    ScheduledFuture localScheduledFuture = this.cancelFuture;
    if (localScheduledFuture != null) {
      localScheduledFuture.cancel(false);
    }
    this.executor.shutdown();
    this.executor.awaitTermination(10L, TimeUnit.SECONDS);
  }
  
  /* Error */
  boolean writeOneFrame()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 183	okhttp3/internal/ws/RealWebSocket:failed	Z
    //   6: ifeq +7 -> 13
    //   9: aload_0
    //   10: monitorexit
    //   11: iconst_0
    //   12: ireturn
    //   13: aload_0
    //   14: getfield 401	okhttp3/internal/ws/RealWebSocket:writer	Lokhttp3/internal/ws/WebSocketWriter;
    //   17: astore_1
    //   18: aload_0
    //   19: getfield 105	okhttp3/internal/ws/RealWebSocket:pongQueue	Ljava/util/ArrayDeque;
    //   22: invokevirtual 504	java/util/ArrayDeque:poll	()Ljava/lang/Object;
    //   25: checkcast 139	a/f
    //   28: astore_2
    //   29: aconst_null
    //   30: astore_3
    //   31: aload_2
    //   32: ifnonnull +122 -> 154
    //   35: aload_0
    //   36: getfield 107	okhttp3/internal/ws/RealWebSocket:messageAndCloseQueue	Ljava/util/ArrayDeque;
    //   39: invokevirtual 504	java/util/ArrayDeque:poll	()Ljava/lang/Object;
    //   42: astore 4
    //   44: aload 4
    //   46: instanceof 17
    //   49: ifeq +87 -> 136
    //   52: aload_0
    //   53: getfield 109	okhttp3/internal/ws/RealWebSocket:receivedCloseCode	I
    //   56: istore 5
    //   58: aload_0
    //   59: getfield 444	okhttp3/internal/ws/RealWebSocket:receivedCloseReason	Ljava/lang/String;
    //   62: astore 6
    //   64: iload 5
    //   66: iconst_m1
    //   67: if_icmpeq +25 -> 92
    //   70: aload_0
    //   71: getfield 362	okhttp3/internal/ws/RealWebSocket:streams	Lokhttp3/internal/ws/RealWebSocket$Streams;
    //   74: astore_3
    //   75: aload_0
    //   76: aconst_null
    //   77: putfield 362	okhttp3/internal/ws/RealWebSocket:streams	Lokhttp3/internal/ws/RealWebSocket$Streams;
    //   80: aload_0
    //   81: getfield 173	okhttp3/internal/ws/RealWebSocket:executor	Ljava/util/concurrent/ScheduledExecutorService;
    //   84: invokeinterface 372 1 0
    //   89: goto +75 -> 164
    //   92: aload_0
    //   93: getfield 173	okhttp3/internal/ws/RealWebSocket:executor	Ljava/util/concurrent/ScheduledExecutorService;
    //   96: astore 7
    //   98: new 14	okhttp3/internal/ws/RealWebSocket$CancelRunnable
    //   101: astore 8
    //   103: aload 8
    //   105: aload_0
    //   106: invokespecial 505	okhttp3/internal/ws/RealWebSocket$CancelRunnable:<init>	(Lokhttp3/internal/ws/RealWebSocket;)V
    //   109: aload_0
    //   110: aload 7
    //   112: aload 8
    //   114: aload 4
    //   116: checkcast 17	okhttp3/internal/ws/RealWebSocket$Close
    //   119: getfield 508	okhttp3/internal/ws/RealWebSocket$Close:cancelAfterCloseMillis	J
    //   122: getstatic 417	java/util/concurrent/TimeUnit:MILLISECONDS	Ljava/util/concurrent/TimeUnit;
    //   125: invokeinterface 512 5 0
    //   130: putfield 364	okhttp3/internal/ws/RealWebSocket:cancelFuture	Ljava/util/concurrent/ScheduledFuture;
    //   133: goto +31 -> 164
    //   136: aload 4
    //   138: ifnonnull +7 -> 145
    //   141: aload_0
    //   142: monitorexit
    //   143: iconst_0
    //   144: ireturn
    //   145: aconst_null
    //   146: astore 6
    //   148: iconst_m1
    //   149: istore 5
    //   151: goto +13 -> 164
    //   154: aconst_null
    //   155: astore 4
    //   157: aload 4
    //   159: astore 6
    //   161: iconst_m1
    //   162: istore 5
    //   164: aload_0
    //   165: monitorexit
    //   166: aload_2
    //   167: ifnull +11 -> 178
    //   170: aload_1
    //   171: aload_2
    //   172: invokevirtual 515	okhttp3/internal/ws/WebSocketWriter:writePong	(La/f;)V
    //   175: goto +135 -> 310
    //   178: aload 4
    //   180: instanceof 20
    //   183: ifeq +82 -> 265
    //   186: aload 4
    //   188: checkcast 20	okhttp3/internal/ws/RealWebSocket$Message
    //   191: getfield 519	okhttp3/internal/ws/RealWebSocket$Message:data	La/f;
    //   194: astore 6
    //   196: aload_1
    //   197: aload 4
    //   199: checkcast 20	okhttp3/internal/ws/RealWebSocket$Message
    //   202: getfield 522	okhttp3/internal/ws/RealWebSocket$Message:formatOpcode	I
    //   205: aload 6
    //   207: invokevirtual 191	a/f:h	()I
    //   210: i2l
    //   211: invokevirtual 526	okhttp3/internal/ws/WebSocketWriter:newMessageSink	(IJ)La/r;
    //   214: invokestatic 531	a/l:a	(La/r;)La/d;
    //   217: astore 4
    //   219: aload 4
    //   221: aload 6
    //   223: invokeinterface 537 2 0
    //   228: pop
    //   229: aload 4
    //   231: invokeinterface 539 1 0
    //   236: aload_0
    //   237: monitorenter
    //   238: aload_0
    //   239: aload_0
    //   240: getfield 187	okhttp3/internal/ws/RealWebSocket:queueSize	J
    //   243: aload 6
    //   245: invokevirtual 191	a/f:h	()I
    //   248: i2l
    //   249: lsub
    //   250: putfield 187	okhttp3/internal/ws/RealWebSocket:queueSize	J
    //   253: aload_0
    //   254: monitorexit
    //   255: goto +55 -> 310
    //   258: astore 6
    //   260: aload_0
    //   261: monitorexit
    //   262: aload 6
    //   264: athrow
    //   265: aload 4
    //   267: instanceof 17
    //   270: ifeq +46 -> 316
    //   273: aload 4
    //   275: checkcast 17	okhttp3/internal/ws/RealWebSocket$Close
    //   278: astore 4
    //   280: aload_1
    //   281: aload 4
    //   283: getfield 541	okhttp3/internal/ws/RealWebSocket$Close:code	I
    //   286: aload 4
    //   288: getfield 544	okhttp3/internal/ws/RealWebSocket$Close:reason	La/f;
    //   291: invokevirtual 547	okhttp3/internal/ws/WebSocketWriter:writeClose	(ILa/f;)V
    //   294: aload_3
    //   295: ifnull +15 -> 310
    //   298: aload_0
    //   299: getfield 127	okhttp3/internal/ws/RealWebSocket:listener	Lokhttp3/WebSocketListener;
    //   302: aload_0
    //   303: iload 5
    //   305: aload 6
    //   307: invokevirtual 451	okhttp3/WebSocketListener:onClosed	(Lokhttp3/WebSocket;ILjava/lang/String;)V
    //   310: aload_3
    //   311: invokestatic 384	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   314: iconst_1
    //   315: ireturn
    //   316: new 549	java/lang/AssertionError
    //   319: astore 6
    //   321: aload 6
    //   323: invokespecial 550	java/lang/AssertionError:<init>	()V
    //   326: aload 6
    //   328: athrow
    //   329: astore 6
    //   331: aload_3
    //   332: invokestatic 384	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   335: aload 6
    //   337: athrow
    //   338: astore 6
    //   340: aload_0
    //   341: monitorexit
    //   342: aload 6
    //   344: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	345	0	this	RealWebSocket
    //   17	264	1	localWebSocketWriter	WebSocketWriter
    //   28	144	2	localf	f
    //   30	302	3	localStreams	Streams
    //   42	245	4	localObject1	Object
    //   56	248	5	i	int
    //   62	182	6	localObject2	Object
    //   258	48	6	str	String
    //   319	8	6	localAssertionError	AssertionError
    //   329	7	6	localObject3	Object
    //   338	5	6	localObject4	Object
    //   96	15	7	localScheduledExecutorService	ScheduledExecutorService
    //   101	12	8	localCancelRunnable	CancelRunnable
    // Exception table:
    //   from	to	target	type
    //   238	255	258	finally
    //   260	262	258	finally
    //   170	175	329	finally
    //   178	238	329	finally
    //   262	265	329	finally
    //   265	294	329	finally
    //   298	310	329	finally
    //   316	329	329	finally
    //   2	11	338	finally
    //   13	29	338	finally
    //   35	64	338	finally
    //   70	89	338	finally
    //   92	133	338	finally
    //   141	143	338	finally
    //   164	166	338	finally
    //   340	342	338	finally
  }
  
  void writePingFrame()
  {
    try
    {
      if (this.failed) {
        return;
      }
      Object localObject1 = this.writer;
      int i;
      if (this.awaitingPong) {
        i = this.sentPingCount;
      } else {
        i = -1;
      }
      this.sentPingCount += 1;
      this.awaitingPong = true;
      if (i != -1)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("sent ping but didn't receive pong within ");
        ((StringBuilder)localObject1).append(this.pingIntervalMillis);
        ((StringBuilder)localObject1).append("ms (after ");
        ((StringBuilder)localObject1).append(i - 1);
        ((StringBuilder)localObject1).append(" successful ping/pongs)");
        failWebSocket(new SocketTimeoutException(((StringBuilder)localObject1).toString()), null);
        return;
      }
      try
      {
        ((WebSocketWriter)localObject1).writePing(f.b);
      }
      catch (IOException localIOException)
      {
        failWebSocket(localIOException, null);
      }
      return;
    }
    finally {}
  }
  
  final class CancelRunnable
    implements Runnable
  {
    CancelRunnable() {}
    
    public void run()
    {
      RealWebSocket.this.cancel();
    }
  }
  
  static final class Close
  {
    final long cancelAfterCloseMillis;
    final int code;
    final f reason;
    
    Close(int paramInt, f paramf, long paramLong)
    {
      this.code = paramInt;
      this.reason = paramf;
      this.cancelAfterCloseMillis = paramLong;
    }
  }
  
  static final class Message
  {
    final f data;
    final int formatOpcode;
    
    Message(int paramInt, f paramf)
    {
      this.formatOpcode = paramInt;
      this.data = paramf;
    }
  }
  
  private final class PingRunnable
    implements Runnable
  {
    PingRunnable() {}
    
    public void run()
    {
      RealWebSocket.this.writePingFrame();
    }
  }
  
  public static abstract class Streams
    implements Closeable
  {
    public final boolean client;
    public final d sink;
    public final e source;
    
    public Streams(boolean paramBoolean, e parame, d paramd)
    {
      this.client = paramBoolean;
      this.source = parame;
      this.sink = paramd;
    }
  }
}


/* Location:              ~/okhttp3/internal/ws/RealWebSocket.class
 *
 * Reversed by:           J
 */