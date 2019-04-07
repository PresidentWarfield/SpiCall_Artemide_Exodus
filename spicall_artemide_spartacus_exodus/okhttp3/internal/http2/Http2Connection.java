package okhttp3.internal.http2;

import a.c;
import a.d;
import a.e;
import a.f;
import a.l;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.Protocol;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;

public final class Http2Connection
  implements Closeable
{
  static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
  private static final ExecutorService listenerExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp Http2Connection", true));
  private boolean awaitingPong;
  long bytesLeftInWriteWindow;
  final boolean client;
  final Set<Integer> currentPushRequests = new LinkedHashSet();
  final String hostname;
  int lastGoodStreamId;
  final Listener listener;
  int nextStreamId;
  Settings okHttpSettings = new Settings();
  final Settings peerSettings = new Settings();
  private final ExecutorService pushExecutor;
  final PushObserver pushObserver;
  final ReaderRunnable readerRunnable;
  boolean receivedInitialPeerSettings = false;
  boolean shutdown;
  final Socket socket;
  final Map<Integer, Http2Stream> streams = new LinkedHashMap();
  long unacknowledgedBytesRead = 0L;
  final Http2Writer writer;
  private final ScheduledExecutorService writerExecutor;
  
  Http2Connection(Builder paramBuilder)
  {
    this.pushObserver = paramBuilder.pushObserver;
    this.client = paramBuilder.client;
    this.listener = paramBuilder.listener;
    int i;
    if (paramBuilder.client) {
      i = 1;
    } else {
      i = 2;
    }
    this.nextStreamId = i;
    if (paramBuilder.client) {
      this.nextStreamId += 2;
    }
    if (paramBuilder.client) {
      this.okHttpSettings.set(7, 16777216);
    }
    this.hostname = paramBuilder.hostname;
    this.writerExecutor = new ScheduledThreadPoolExecutor(1, Util.threadFactory(Util.format("OkHttp %s Writer", new Object[] { this.hostname }), false));
    if (paramBuilder.pingIntervalMillis != 0) {
      this.writerExecutor.scheduleAtFixedRate(new PingRunnable(false, 0, 0), paramBuilder.pingIntervalMillis, paramBuilder.pingIntervalMillis, TimeUnit.MILLISECONDS);
    }
    this.pushExecutor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory(Util.format("OkHttp %s Push Observer", new Object[] { this.hostname }), true));
    this.peerSettings.set(7, 65535);
    this.peerSettings.set(5, 16384);
    this.bytesLeftInWriteWindow = this.peerSettings.getInitialWindowSize();
    this.socket = paramBuilder.socket;
    this.writer = new Http2Writer(paramBuilder.sink, this.client);
    this.readerRunnable = new ReaderRunnable(new Http2Reader(paramBuilder.source, this.client));
  }
  
  private void failConnection()
  {
    try
    {
      close(ErrorCode.PROTOCOL_ERROR, ErrorCode.PROTOCOL_ERROR);
      return;
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
  }
  
  private Http2Stream newStream(int paramInt, List<Header> paramList, boolean paramBoolean)
  {
    boolean bool = paramBoolean ^ true;
    synchronized (this.writer)
    {
      try
      {
        if (this.nextStreamId > 1073741823) {
          shutdown(ErrorCode.REFUSED_STREAM);
        }
        if (!this.shutdown)
        {
          int i = this.nextStreamId;
          this.nextStreamId += 2;
          Http2Stream localHttp2Stream = new okhttp3/internal/http2/Http2Stream;
          localHttp2Stream.<init>(i, this, bool, false, null);
          int j;
          if ((paramBoolean) && (this.bytesLeftInWriteWindow != 0L) && (localHttp2Stream.bytesLeftInWriteWindow != 0L)) {
            j = 0;
          } else {
            j = 1;
          }
          if (localHttp2Stream.isOpen()) {
            this.streams.put(Integer.valueOf(i), localHttp2Stream);
          }
          if (paramInt == 0)
          {
            this.writer.synStream(bool, i, paramInt, paramList);
          }
          else
          {
            if (this.client) {
              break label190;
            }
            this.writer.pushPromise(paramInt, i, paramList);
          }
          if (j != 0) {
            this.writer.flush();
          }
          return localHttp2Stream;
          label190:
          paramList = new java/lang/IllegalArgumentException;
          paramList.<init>("client streams shouldn't have associated stream IDs");
          throw paramList;
        }
        paramList = new okhttp3/internal/http2/ConnectionShutdownException;
        paramList.<init>();
        throw paramList;
      }
      finally {}
    }
  }
  
  private void pushExecutorExecute(NamedRunnable paramNamedRunnable)
  {
    try
    {
      if (!isShutdown()) {
        this.pushExecutor.execute(paramNamedRunnable);
      }
      return;
    }
    finally
    {
      paramNamedRunnable = finally;
      throw paramNamedRunnable;
    }
  }
  
  void awaitPong()
  {
    try
    {
      while (this.awaitingPong) {
        wait();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void close()
  {
    close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
  }
  
  void close(ErrorCode paramErrorCode1, ErrorCode paramErrorCode2)
  {
    Http2Stream[] arrayOfHttp2Stream = null;
    try
    {
      shutdown(paramErrorCode1);
      paramErrorCode1 = null;
    }
    catch (IOException paramErrorCode1) {}
    try
    {
      if (!this.streams.isEmpty())
      {
        arrayOfHttp2Stream = (Http2Stream[])this.streams.values().toArray(new Http2Stream[this.streams.size()]);
        this.streams.clear();
      }
      Object localObject = paramErrorCode1;
      if (arrayOfHttp2Stream != null)
      {
        int i = arrayOfHttp2Stream.length;
        int j = 0;
        for (;;)
        {
          localObject = paramErrorCode1;
          if (j >= i) {
            break;
          }
          localObject = arrayOfHttp2Stream[j];
          try
          {
            ((Http2Stream)localObject).close(paramErrorCode2);
            localObject = paramErrorCode1;
          }
          catch (IOException localIOException)
          {
            localObject = paramErrorCode1;
            if (paramErrorCode1 != null) {
              localObject = localIOException;
            }
          }
          j++;
          paramErrorCode1 = (ErrorCode)localObject;
        }
      }
      try
      {
        this.writer.close();
        paramErrorCode1 = (ErrorCode)localObject;
      }
      catch (IOException paramErrorCode2)
      {
        paramErrorCode1 = (ErrorCode)localObject;
        if (localObject == null) {
          paramErrorCode1 = paramErrorCode2;
        }
      }
      try
      {
        this.socket.close();
      }
      catch (IOException paramErrorCode1) {}
      this.writerExecutor.shutdown();
      this.pushExecutor.shutdown();
      if (paramErrorCode1 == null) {
        return;
      }
      throw paramErrorCode1;
    }
    finally {}
  }
  
  public void flush()
  {
    this.writer.flush();
  }
  
  public Protocol getProtocol()
  {
    return Protocol.HTTP_2;
  }
  
  Http2Stream getStream(int paramInt)
  {
    try
    {
      Http2Stream localHttp2Stream = (Http2Stream)this.streams.get(Integer.valueOf(paramInt));
      return localHttp2Stream;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean isShutdown()
  {
    try
    {
      boolean bool = this.shutdown;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int maxConcurrentStreams()
  {
    try
    {
      int i = this.peerSettings.getMaxConcurrentStreams(Integer.MAX_VALUE);
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public Http2Stream newStream(List<Header> paramList, boolean paramBoolean)
  {
    return newStream(0, paramList, paramBoolean);
  }
  
  public int openStreamCount()
  {
    try
    {
      int i = this.streams.size();
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  void pushDataLater(final int paramInt1, e parame, final int paramInt2, final boolean paramBoolean)
  {
    final c localc = new c();
    long l = paramInt2;
    parame.a(l);
    parame.read(localc, l);
    if (localc.a() == l)
    {
      pushExecutorExecute(new NamedRunnable("OkHttp %s Push Data[%s]", new Object[] { this.hostname, Integer.valueOf(paramInt1) })
      {
        public void execute()
        {
          try
          {
            boolean bool = Http2Connection.this.pushObserver.onData(paramInt1, localc, paramInt2, paramBoolean);
            if (bool) {
              Http2Connection.this.writer.rstStream(paramInt1, ErrorCode.CANCEL);
            }
            if ((bool) || (paramBoolean)) {
              synchronized (Http2Connection.this)
              {
                Http2Connection.this.currentPushRequests.remove(Integer.valueOf(paramInt1));
              }
            }
            return;
          }
          catch (IOException localIOException)
          {
            for (;;) {}
          }
        }
      });
      return;
    }
    parame = new StringBuilder();
    parame.append(localc.a());
    parame.append(" != ");
    parame.append(paramInt2);
    throw new IOException(parame.toString());
  }
  
  void pushHeadersLater(int paramInt, List<Header> paramList, boolean paramBoolean)
  {
    try
    {
      NamedRunnable local4 = new okhttp3/internal/http2/Http2Connection$4;
      local4.<init>(this, "OkHttp %s Push Headers[%s]", new Object[] { this.hostname, Integer.valueOf(paramInt) }, paramInt, paramList, paramBoolean);
      pushExecutorExecute(local4);
      return;
    }
    catch (RejectedExecutionException paramList)
    {
      for (;;) {}
    }
  }
  
  /* Error */
  void pushRequestLater(int paramInt, List<Header> paramList)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 134	okhttp3/internal/http2/Http2Connection:currentPushRequests	Ljava/util/Set;
    //   6: iload_1
    //   7: invokestatic 274	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   10: invokeinterface 442 2 0
    //   15: ifeq +14 -> 29
    //   18: aload_0
    //   19: iload_1
    //   20: getstatic 243	okhttp3/internal/http2/ErrorCode:PROTOCOL_ERROR	Lokhttp3/internal/http2/ErrorCode;
    //   23: invokevirtual 446	okhttp3/internal/http2/Http2Connection:writeSynResetLater	(ILokhttp3/internal/http2/ErrorCode;)V
    //   26: aload_0
    //   27: monitorexit
    //   28: return
    //   29: aload_0
    //   30: getfield 134	okhttp3/internal/http2/Http2Connection:currentPushRequests	Ljava/util/Set;
    //   33: iload_1
    //   34: invokestatic 274	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   37: invokeinterface 449 2 0
    //   42: pop
    //   43: aload_0
    //   44: monitorexit
    //   45: new 12	okhttp3/internal/http2/Http2Connection$3
    //   48: astore_3
    //   49: aload_3
    //   50: aload_0
    //   51: ldc_w 451
    //   54: iconst_2
    //   55: anewarray 4	java/lang/Object
    //   58: dup
    //   59: iconst_0
    //   60: aload_0
    //   61: getfield 152	okhttp3/internal/http2/Http2Connection:hostname	Ljava/lang/String;
    //   64: aastore
    //   65: dup
    //   66: iconst_1
    //   67: iload_1
    //   68: invokestatic 274	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   71: aastore
    //   72: iload_1
    //   73: aload_2
    //   74: invokespecial 454	okhttp3/internal/http2/Http2Connection$3:<init>	(Lokhttp3/internal/http2/Http2Connection;Ljava/lang/String;[Ljava/lang/Object;ILjava/util/List;)V
    //   77: aload_0
    //   78: aload_3
    //   79: invokespecial 404	okhttp3/internal/http2/Http2Connection:pushExecutorExecute	(Lokhttp3/internal/NamedRunnable;)V
    //   82: return
    //   83: astore_2
    //   84: aload_0
    //   85: monitorexit
    //   86: aload_2
    //   87: athrow
    //   88: astore_2
    //   89: goto -7 -> 82
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	92	0	this	Http2Connection
    //   0	92	1	paramInt	int
    //   0	92	2	paramList	List<Header>
    //   48	31	3	local3	3
    // Exception table:
    //   from	to	target	type
    //   2	28	83	finally
    //   29	45	83	finally
    //   84	86	83	finally
    //   45	82	88	java/util/concurrent/RejectedExecutionException
  }
  
  void pushResetLater(final int paramInt, final ErrorCode paramErrorCode)
  {
    pushExecutorExecute(new NamedRunnable("OkHttp %s Push Reset[%s]", new Object[] { this.hostname, Integer.valueOf(paramInt) })
    {
      public void execute()
      {
        Http2Connection.this.pushObserver.onReset(paramInt, paramErrorCode);
        synchronized (Http2Connection.this)
        {
          Http2Connection.this.currentPushRequests.remove(Integer.valueOf(paramInt));
          return;
        }
      }
    });
  }
  
  public Http2Stream pushStream(int paramInt, List<Header> paramList, boolean paramBoolean)
  {
    if (!this.client) {
      return newStream(paramInt, paramList, paramBoolean);
    }
    throw new IllegalStateException("Client cannot push requests.");
  }
  
  boolean pushedStream(int paramInt)
  {
    boolean bool = true;
    if ((paramInt == 0) || ((paramInt & 0x1) != 0)) {
      bool = false;
    }
    return bool;
  }
  
  Http2Stream removeStream(int paramInt)
  {
    try
    {
      Http2Stream localHttp2Stream = (Http2Stream)this.streams.remove(Integer.valueOf(paramInt));
      notifyAll();
      return localHttp2Stream;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setSettings(Settings paramSettings)
  {
    synchronized (this.writer)
    {
      try
      {
        if (!this.shutdown)
        {
          this.okHttpSettings.merge(paramSettings);
          this.writer.settings(paramSettings);
          return;
        }
        paramSettings = new okhttp3/internal/http2/ConnectionShutdownException;
        paramSettings.<init>();
        throw paramSettings;
      }
      finally {}
    }
  }
  
  public void shutdown(ErrorCode paramErrorCode)
  {
    synchronized (this.writer)
    {
      try
      {
        if (this.shutdown) {
          return;
        }
        this.shutdown = true;
        int i = this.lastGoodStreamId;
        this.writer.goAway(i, paramErrorCode, Util.EMPTY_BYTE_ARRAY);
        return;
      }
      finally {}
    }
  }
  
  public void start()
  {
    start(true);
  }
  
  void start(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.writer.connectionPreface();
      this.writer.settings(this.okHttpSettings);
      int i = this.okHttpSettings.getInitialWindowSize();
      if (i != 65535) {
        this.writer.windowUpdate(0, i - 65535);
      }
    }
    new Thread(this.readerRunnable).start();
  }
  
  void updateConnectionFlowControl(long paramLong)
  {
    try
    {
      this.unacknowledgedBytesRead += paramLong;
      if (this.unacknowledgedBytesRead >= this.okHttpSettings.getInitialWindowSize() / 2)
      {
        writeWindowUpdateLater(0, this.unacknowledgedBytesRead);
        this.unacknowledgedBytesRead = 0L;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  public void writeData(int paramInt, boolean paramBoolean, c paramc, long paramLong)
  {
    // Byte code:
    //   0: lload 4
    //   2: lstore 6
    //   4: lload 4
    //   6: lconst_0
    //   7: lcmp
    //   8: ifne +15 -> 23
    //   11: aload_0
    //   12: getfield 208	okhttp3/internal/http2/Http2Connection:writer	Lokhttp3/internal/http2/Http2Writer;
    //   15: iload_2
    //   16: iload_1
    //   17: aload_3
    //   18: iconst_0
    //   19: invokevirtual 523	okhttp3/internal/http2/Http2Writer:data	(ZILa/c;I)V
    //   22: return
    //   23: lload 6
    //   25: lconst_0
    //   26: lcmp
    //   27: ifle +166 -> 193
    //   30: aload_0
    //   31: monitorenter
    //   32: aload_0
    //   33: getfield 194	okhttp3/internal/http2/Http2Connection:bytesLeftInWriteWindow	J
    //   36: lconst_0
    //   37: lcmp
    //   38: ifgt +39 -> 77
    //   41: aload_0
    //   42: getfield 118	okhttp3/internal/http2/Http2Connection:streams	Ljava/util/Map;
    //   45: iload_1
    //   46: invokestatic 274	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   49: invokeinterface 526 2 0
    //   54: ifeq +10 -> 64
    //   57: aload_0
    //   58: invokevirtual 318	java/lang/Object:wait	()V
    //   61: goto -29 -> 32
    //   64: new 237	java/io/IOException
    //   67: astore_3
    //   68: aload_3
    //   69: ldc_w 528
    //   72: invokespecial 424	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   75: aload_3
    //   76: athrow
    //   77: lload 6
    //   79: aload_0
    //   80: getfield 194	okhttp3/internal/http2/Http2Connection:bytesLeftInWriteWindow	J
    //   83: invokestatic 534	java/lang/Math:min	(JJ)J
    //   86: l2i
    //   87: aload_0
    //   88: getfield 208	okhttp3/internal/http2/Http2Connection:writer	Lokhttp3/internal/http2/Http2Writer;
    //   91: invokevirtual 537	okhttp3/internal/http2/Http2Writer:maxDataLength	()I
    //   94: invokestatic 540	java/lang/Math:min	(II)I
    //   97: istore 8
    //   99: aload_0
    //   100: getfield 194	okhttp3/internal/http2/Http2Connection:bytesLeftInWriteWindow	J
    //   103: lstore 9
    //   105: iload 8
    //   107: i2l
    //   108: lstore 4
    //   110: aload_0
    //   111: lload 9
    //   113: lload 4
    //   115: lsub
    //   116: putfield 194	okhttp3/internal/http2/Http2Connection:bytesLeftInWriteWindow	J
    //   119: aload_0
    //   120: monitorexit
    //   121: lload 6
    //   123: lload 4
    //   125: lsub
    //   126: lstore 6
    //   128: aload_0
    //   129: getfield 208	okhttp3/internal/http2/Http2Connection:writer	Lokhttp3/internal/http2/Http2Writer;
    //   132: astore 11
    //   134: iload_2
    //   135: ifeq +16 -> 151
    //   138: lload 6
    //   140: lconst_0
    //   141: lcmp
    //   142: ifne +9 -> 151
    //   145: iconst_1
    //   146: istore 12
    //   148: goto +6 -> 154
    //   151: iconst_0
    //   152: istore 12
    //   154: aload 11
    //   156: iload 12
    //   158: iload_1
    //   159: aload_3
    //   160: iload 8
    //   162: invokevirtual 523	okhttp3/internal/http2/Http2Writer:data	(ZILa/c;I)V
    //   165: goto -142 -> 23
    //   168: astore_3
    //   169: goto +20 -> 189
    //   172: astore_3
    //   173: invokestatic 544	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   176: invokevirtual 547	java/lang/Thread:interrupt	()V
    //   179: new 549	java/io/InterruptedIOException
    //   182: astore_3
    //   183: aload_3
    //   184: invokespecial 550	java/io/InterruptedIOException:<init>	()V
    //   187: aload_3
    //   188: athrow
    //   189: aload_0
    //   190: monitorexit
    //   191: aload_3
    //   192: athrow
    //   193: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	194	0	this	Http2Connection
    //   0	194	1	paramInt	int
    //   0	194	2	paramBoolean	boolean
    //   0	194	3	paramc	c
    //   0	194	4	paramLong	long
    //   2	137	6	l1	long
    //   97	64	8	i	int
    //   103	9	9	l2	long
    //   132	23	11	localHttp2Writer	Http2Writer
    //   146	11	12	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   32	61	168	finally
    //   64	77	168	finally
    //   77	105	168	finally
    //   110	121	168	finally
    //   173	189	168	finally
    //   189	191	168	finally
    //   32	61	172	java/lang/InterruptedException
    //   64	77	172	java/lang/InterruptedException
  }
  
  void writePing(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    if (!paramBoolean) {
      try
      {
        boolean bool = this.awaitingPong;
        this.awaitingPong = true;
        if (bool)
        {
          failConnection();
          return;
        }
      }
      finally {}
    }
    try
    {
      this.writer.ping(paramBoolean, paramInt1, paramInt2);
    }
    catch (IOException localIOException)
    {
      failConnection();
    }
  }
  
  void writePingAndAwaitPong()
  {
    writePing(false, 1330343787, -257978967);
    awaitPong();
  }
  
  void writeSynReply(int paramInt, boolean paramBoolean, List<Header> paramList)
  {
    this.writer.synReply(paramBoolean, paramInt, paramList);
  }
  
  void writeSynReset(int paramInt, ErrorCode paramErrorCode)
  {
    this.writer.rstStream(paramInt, paramErrorCode);
  }
  
  void writeSynResetLater(int paramInt, ErrorCode paramErrorCode)
  {
    try
    {
      ScheduledExecutorService localScheduledExecutorService = this.writerExecutor;
      NamedRunnable local1 = new okhttp3/internal/http2/Http2Connection$1;
      local1.<init>(this, "OkHttp %s stream %d", new Object[] { this.hostname, Integer.valueOf(paramInt) }, paramInt, paramErrorCode);
      localScheduledExecutorService.execute(local1);
      return;
    }
    catch (RejectedExecutionException paramErrorCode)
    {
      for (;;) {}
    }
  }
  
  void writeWindowUpdateLater(int paramInt, long paramLong)
  {
    try
    {
      ScheduledExecutorService localScheduledExecutorService = this.writerExecutor;
      NamedRunnable local2 = new okhttp3/internal/http2/Http2Connection$2;
      local2.<init>(this, "OkHttp Window Update %s stream %d", new Object[] { this.hostname, Integer.valueOf(paramInt) }, paramInt, paramLong);
      localScheduledExecutorService.execute(local2);
      return;
    }
    catch (RejectedExecutionException localRejectedExecutionException)
    {
      for (;;) {}
    }
  }
  
  public static class Builder
  {
    boolean client;
    String hostname;
    Http2Connection.Listener listener = Http2Connection.Listener.REFUSE_INCOMING_STREAMS;
    int pingIntervalMillis;
    PushObserver pushObserver = PushObserver.CANCEL;
    d sink;
    Socket socket;
    e source;
    
    public Builder(boolean paramBoolean)
    {
      this.client = paramBoolean;
    }
    
    public Http2Connection build()
    {
      return new Http2Connection(this);
    }
    
    public Builder listener(Http2Connection.Listener paramListener)
    {
      this.listener = paramListener;
      return this;
    }
    
    public Builder pingIntervalMillis(int paramInt)
    {
      this.pingIntervalMillis = paramInt;
      return this;
    }
    
    public Builder pushObserver(PushObserver paramPushObserver)
    {
      this.pushObserver = paramPushObserver;
      return this;
    }
    
    public Builder socket(Socket paramSocket)
    {
      return socket(paramSocket, ((InetSocketAddress)paramSocket.getRemoteSocketAddress()).getHostName(), l.a(l.b(paramSocket)), l.a(l.a(paramSocket)));
    }
    
    public Builder socket(Socket paramSocket, String paramString, e parame, d paramd)
    {
      this.socket = paramSocket;
      this.hostname = paramString;
      this.source = parame;
      this.sink = paramd;
      return this;
    }
  }
  
  public static abstract class Listener
  {
    public static final Listener REFUSE_INCOMING_STREAMS = new Listener()
    {
      public void onStream(Http2Stream paramAnonymousHttp2Stream)
      {
        paramAnonymousHttp2Stream.close(ErrorCode.REFUSED_STREAM);
      }
    };
    
    public void onSettings(Http2Connection paramHttp2Connection) {}
    
    public abstract void onStream(Http2Stream paramHttp2Stream);
  }
  
  final class PingRunnable
    extends NamedRunnable
  {
    final int payload1;
    final int payload2;
    final boolean reply;
    
    PingRunnable(boolean paramBoolean, int paramInt1, int paramInt2)
    {
      super(new Object[] { Http2Connection.this.hostname, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) });
      this.reply = paramBoolean;
      this.payload1 = paramInt1;
      this.payload2 = paramInt2;
    }
    
    public void execute()
    {
      Http2Connection.this.writePing(this.reply, this.payload1, this.payload2);
    }
  }
  
  class ReaderRunnable
    extends NamedRunnable
    implements Http2Reader.Handler
  {
    final Http2Reader reader;
    
    ReaderRunnable(Http2Reader paramHttp2Reader)
    {
      super(new Object[] { Http2Connection.this.hostname });
      this.reader = paramHttp2Reader;
    }
    
    private void applyAndAckSettings(Settings paramSettings)
    {
      try
      {
        ScheduledExecutorService localScheduledExecutorService = Http2Connection.this.writerExecutor;
        NamedRunnable local3 = new okhttp3/internal/http2/Http2Connection$ReaderRunnable$3;
        local3.<init>(this, "OkHttp %s ACK Settings", new Object[] { Http2Connection.this.hostname }, paramSettings);
        localScheduledExecutorService.execute(local3);
        return;
      }
      catch (RejectedExecutionException paramSettings)
      {
        for (;;) {}
      }
    }
    
    public void ackSettings() {}
    
    public void alternateService(int paramInt1, String paramString1, f paramf, String paramString2, int paramInt2, long paramLong) {}
    
    public void data(boolean paramBoolean, int paramInt1, e parame, int paramInt2)
    {
      if (Http2Connection.this.pushedStream(paramInt1))
      {
        Http2Connection.this.pushDataLater(paramInt1, parame, paramInt2, paramBoolean);
        return;
      }
      Object localObject = Http2Connection.this.getStream(paramInt1);
      if (localObject == null)
      {
        Http2Connection.this.writeSynResetLater(paramInt1, ErrorCode.PROTOCOL_ERROR);
        localObject = Http2Connection.this;
        long l = paramInt2;
        ((Http2Connection)localObject).updateConnectionFlowControl(l);
        parame.i(l);
        return;
      }
      ((Http2Stream)localObject).receiveData(parame, paramInt2);
      if (paramBoolean) {
        ((Http2Stream)localObject).receiveFin();
      }
    }
    
    /* Error */
    protected void execute()
    {
      // Byte code:
      //   0: getstatic 107	okhttp3/internal/http2/ErrorCode:INTERNAL_ERROR	Lokhttp3/internal/http2/ErrorCode;
      //   3: astore_1
      //   4: getstatic 107	okhttp3/internal/http2/ErrorCode:INTERNAL_ERROR	Lokhttp3/internal/http2/ErrorCode;
      //   7: astore_2
      //   8: aload_1
      //   9: astore_3
      //   10: aload_1
      //   11: astore 4
      //   13: aload_0
      //   14: getfield 36	okhttp3/internal/http2/Http2Connection$ReaderRunnable:reader	Lokhttp3/internal/http2/Http2Reader;
      //   17: aload_0
      //   18: invokevirtual 113	okhttp3/internal/http2/Http2Reader:readConnectionPreface	(Lokhttp3/internal/http2/Http2Reader$Handler;)V
      //   21: aload_1
      //   22: astore_3
      //   23: aload_1
      //   24: astore 4
      //   26: aload_0
      //   27: getfield 36	okhttp3/internal/http2/Http2Connection$ReaderRunnable:reader	Lokhttp3/internal/http2/Http2Reader;
      //   30: iconst_0
      //   31: aload_0
      //   32: invokevirtual 117	okhttp3/internal/http2/Http2Reader:nextFrame	(ZLokhttp3/internal/http2/Http2Reader$Handler;)Z
      //   35: ifeq +6 -> 41
      //   38: goto -17 -> 21
      //   41: aload_1
      //   42: astore_3
      //   43: aload_1
      //   44: astore 4
      //   46: getstatic 120	okhttp3/internal/http2/ErrorCode:NO_ERROR	Lokhttp3/internal/http2/ErrorCode;
      //   49: astore_1
      //   50: aload_1
      //   51: astore_3
      //   52: aload_1
      //   53: astore 4
      //   55: getstatic 123	okhttp3/internal/http2/ErrorCode:CANCEL	Lokhttp3/internal/http2/ErrorCode;
      //   58: astore 5
      //   60: aload_0
      //   61: getfield 23	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   64: astore_2
      //   65: aload_1
      //   66: astore 4
      //   68: aload 5
      //   70: astore_3
      //   71: aload_2
      //   72: astore_1
      //   73: goto +33 -> 106
      //   76: astore 4
      //   78: goto +43 -> 121
      //   81: astore_3
      //   82: aload 4
      //   84: astore_3
      //   85: getstatic 80	okhttp3/internal/http2/ErrorCode:PROTOCOL_ERROR	Lokhttp3/internal/http2/ErrorCode;
      //   88: astore 4
      //   90: aload 4
      //   92: astore_3
      //   93: getstatic 80	okhttp3/internal/http2/ErrorCode:PROTOCOL_ERROR	Lokhttp3/internal/http2/ErrorCode;
      //   96: astore 5
      //   98: aload_0
      //   99: getfield 23	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   102: astore_1
      //   103: aload 5
      //   105: astore_3
      //   106: aload_1
      //   107: aload 4
      //   109: aload_3
      //   110: invokevirtual 127	okhttp3/internal/http2/Http2Connection:close	(Lokhttp3/internal/http2/ErrorCode;Lokhttp3/internal/http2/ErrorCode;)V
      //   113: aload_0
      //   114: getfield 36	okhttp3/internal/http2/Http2Connection$ReaderRunnable:reader	Lokhttp3/internal/http2/Http2Reader;
      //   117: invokestatic 133	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   120: return
      //   121: aload_0
      //   122: getfield 23	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   125: aload_3
      //   126: aload_2
      //   127: invokevirtual 127	okhttp3/internal/http2/Http2Connection:close	(Lokhttp3/internal/http2/ErrorCode;Lokhttp3/internal/http2/ErrorCode;)V
      //   130: aload_0
      //   131: getfield 36	okhttp3/internal/http2/Http2Connection$ReaderRunnable:reader	Lokhttp3/internal/http2/Http2Reader;
      //   134: invokestatic 133	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   137: aload 4
      //   139: athrow
      //   140: astore_3
      //   141: goto -28 -> 113
      //   144: astore_3
      //   145: goto -15 -> 130
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	148	0	this	ReaderRunnable
      //   3	104	1	localObject1	Object
      //   7	120	2	localObject2	Object
      //   9	62	3	localObject3	Object
      //   81	1	3	localIOException1	IOException
      //   84	42	3	localObject4	Object
      //   140	1	3	localIOException2	IOException
      //   144	1	3	localIOException3	IOException
      //   11	56	4	localObject5	Object
      //   76	7	4	localObject6	Object
      //   88	50	4	localErrorCode1	ErrorCode
      //   58	46	5	localErrorCode2	ErrorCode
      // Exception table:
      //   from	to	target	type
      //   13	21	76	finally
      //   26	38	76	finally
      //   46	50	76	finally
      //   55	60	76	finally
      //   85	90	76	finally
      //   93	98	76	finally
      //   13	21	81	java/io/IOException
      //   26	38	81	java/io/IOException
      //   46	50	81	java/io/IOException
      //   55	60	81	java/io/IOException
      //   60	65	140	java/io/IOException
      //   98	103	140	java/io/IOException
      //   106	113	140	java/io/IOException
      //   121	130	144	java/io/IOException
    }
    
    public void goAway(int paramInt, ErrorCode paramErrorCode, f arg3)
    {
      ???.h();
      synchronized (Http2Connection.this)
      {
        paramErrorCode = (Http2Stream[])Http2Connection.this.streams.values().toArray(new Http2Stream[Http2Connection.this.streams.size()]);
        Http2Connection.this.shutdown = true;
        int i = paramErrorCode.length;
        for (int j = 0; j < i; j++)
        {
          ??? = paramErrorCode[j];
          if ((???.getId() > paramInt) && (???.isLocallyInitiated()))
          {
            ???.receiveRstStream(ErrorCode.REFUSED_STREAM);
            Http2Connection.this.removeStream(???.getId());
          }
        }
        return;
      }
    }
    
    public void headers(boolean paramBoolean, int paramInt1, int paramInt2, List<Header> paramList)
    {
      if (Http2Connection.this.pushedStream(paramInt1))
      {
        Http2Connection.this.pushHeadersLater(paramInt1, paramList, paramBoolean);
        return;
      }
      synchronized (Http2Connection.this)
      {
        Object localObject = Http2Connection.this.getStream(paramInt1);
        if (localObject == null)
        {
          if (Http2Connection.this.shutdown) {
            return;
          }
          if (paramInt1 <= Http2Connection.this.lastGoodStreamId) {
            return;
          }
          if (paramInt1 % 2 == Http2Connection.this.nextStreamId % 2) {
            return;
          }
          localObject = Util.toHeaders(paramList);
          paramList = new okhttp3/internal/http2/Http2Stream;
          paramList.<init>(paramInt1, Http2Connection.this, false, paramBoolean, (Headers)localObject);
          Http2Connection.this.lastGoodStreamId = paramInt1;
          Http2Connection.this.streams.put(Integer.valueOf(paramInt1), paramList);
          localObject = Http2Connection.listenerExecutor;
          NamedRunnable local1 = new okhttp3/internal/http2/Http2Connection$ReaderRunnable$1;
          local1.<init>(this, "OkHttp %s stream %d", new Object[] { Http2Connection.this.hostname, Integer.valueOf(paramInt1) }, paramList);
          ((ExecutorService)localObject).execute(local1);
          return;
        }
        ((Http2Stream)localObject).receiveHeaders(paramList);
        if (paramBoolean) {
          ((Http2Stream)localObject).receiveFin();
        }
        return;
      }
    }
    
    public void ping(boolean paramBoolean, int paramInt1, int paramInt2)
    {
      if (paramBoolean) {
        synchronized (Http2Connection.this)
        {
          Http2Connection.access$302(Http2Connection.this, false);
          Http2Connection.this.notifyAll();
        }
      }
      try
      {
        ??? = Http2Connection.this.writerExecutor;
        Http2Connection.PingRunnable localPingRunnable = new okhttp3/internal/http2/Http2Connection$PingRunnable;
        localPingRunnable.<init>(Http2Connection.this, true, paramInt1, paramInt2);
        ((ScheduledExecutorService)???).execute(localPingRunnable);
        return;
      }
      catch (RejectedExecutionException localRejectedExecutionException)
      {
        for (;;) {}
      }
    }
    
    public void priority(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {}
    
    public void pushPromise(int paramInt1, int paramInt2, List<Header> paramList)
    {
      Http2Connection.this.pushRequestLater(paramInt2, paramList);
    }
    
    public void rstStream(int paramInt, ErrorCode paramErrorCode)
    {
      if (Http2Connection.this.pushedStream(paramInt))
      {
        Http2Connection.this.pushResetLater(paramInt, paramErrorCode);
        return;
      }
      Http2Stream localHttp2Stream = Http2Connection.this.removeStream(paramInt);
      if (localHttp2Stream != null) {
        localHttp2Stream.receiveRstStream(paramErrorCode);
      }
    }
    
    public void settings(boolean paramBoolean, Settings paramSettings)
    {
      synchronized (Http2Connection.this)
      {
        int i = Http2Connection.this.peerSettings.getInitialWindowSize();
        if (paramBoolean) {
          Http2Connection.this.peerSettings.clear();
        }
        Http2Connection.this.peerSettings.merge(paramSettings);
        applyAndAckSettings(paramSettings);
        int j = Http2Connection.this.peerSettings.getInitialWindowSize();
        paramSettings = null;
        long l2;
        if ((j != -1) && (j != i))
        {
          long l1 = j - i;
          if (!Http2Connection.this.receivedInitialPeerSettings) {
            Http2Connection.this.receivedInitialPeerSettings = true;
          }
          l2 = l1;
          if (!Http2Connection.this.streams.isEmpty())
          {
            paramSettings = (Http2Stream[])Http2Connection.this.streams.values().toArray(new Http2Stream[Http2Connection.this.streams.size()]);
            l2 = l1;
          }
        }
        else
        {
          l2 = 0L;
        }
        ExecutorService localExecutorService = Http2Connection.listenerExecutor;
        NamedRunnable local2 = new okhttp3/internal/http2/Http2Connection$ReaderRunnable$2;
        String str = Http2Connection.this.hostname;
        i = 0;
        local2.<init>(this, "OkHttp %s settings", new Object[] { str });
        localExecutorService.execute(local2);
        if ((paramSettings != null) && (l2 != 0L))
        {
          j = paramSettings.length;
          while (i < j) {
            synchronized (paramSettings[i])
            {
              ???.addBytesToWriteWindow(l2);
              i++;
            }
          }
        }
        return;
      }
    }
    
    public void windowUpdate(int paramInt, long paramLong)
    {
      if (paramInt == 0) {
        synchronized (Http2Connection.this)
        {
          Http2Connection localHttp2Connection = Http2Connection.this;
          localHttp2Connection.bytesLeftInWriteWindow += paramLong;
          Http2Connection.this.notifyAll();
        }
      }
      ??? = Http2Connection.this.getStream(paramInt);
      if (??? != null) {
        try
        {
          ((Http2Stream)???).addBytesToWriteWindow(paramLong);
        }
        finally {}
      }
    }
  }
}


/* Location:              ~/okhttp3/internal/http2/Http2Connection.class
 *
 * Reversed by:           J
 */