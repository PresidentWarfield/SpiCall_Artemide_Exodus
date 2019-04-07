package okhttp3;

import a.a;
import a.t;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okhttp3.internal.platform.Platform;

final class RealCall
  implements Call
{
  final OkHttpClient client;
  @Nullable
  private EventListener eventListener;
  private boolean executed;
  final boolean forWebSocket;
  final Request originalRequest;
  final RetryAndFollowUpInterceptor retryAndFollowUpInterceptor;
  final a timeout;
  
  private RealCall(OkHttpClient paramOkHttpClient, Request paramRequest, boolean paramBoolean)
  {
    this.client = paramOkHttpClient;
    this.originalRequest = paramRequest;
    this.forWebSocket = paramBoolean;
    this.retryAndFollowUpInterceptor = new RetryAndFollowUpInterceptor(paramOkHttpClient, paramBoolean);
    this.timeout = new a()
    {
      protected void timedOut()
      {
        RealCall.this.cancel();
      }
    };
    this.timeout.timeout(paramOkHttpClient.callTimeoutMillis(), TimeUnit.MILLISECONDS);
  }
  
  private void captureCallStackTrace()
  {
    Object localObject = Platform.get().getStackTraceForCloseable("response.body().close()");
    this.retryAndFollowUpInterceptor.setCallStackTrace(localObject);
  }
  
  static RealCall newRealCall(OkHttpClient paramOkHttpClient, Request paramRequest, boolean paramBoolean)
  {
    paramRequest = new RealCall(paramOkHttpClient, paramRequest, paramBoolean);
    paramRequest.eventListener = paramOkHttpClient.eventListenerFactory().create(paramRequest);
    return paramRequest;
  }
  
  public void cancel()
  {
    this.retryAndFollowUpInterceptor.cancel();
  }
  
  public RealCall clone()
  {
    return newRealCall(this.client, this.originalRequest, this.forWebSocket);
  }
  
  public void enqueue(Callback paramCallback)
  {
    try
    {
      if (!this.executed)
      {
        this.executed = true;
        captureCallStackTrace();
        this.eventListener.callStart(this);
        this.client.dispatcher().enqueue(new AsyncCall(paramCallback));
        return;
      }
      paramCallback = new java/lang/IllegalStateException;
      paramCallback.<init>("Already Executed");
      throw paramCallback;
    }
    finally {}
  }
  
  /* Error */
  public Response execute()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 116	okhttp3/RealCall:executed	Z
    //   6: ifne +109 -> 115
    //   9: aload_0
    //   10: iconst_1
    //   11: putfield 116	okhttp3/RealCall:executed	Z
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_0
    //   17: invokespecial 118	okhttp3/RealCall:captureCallStackTrace	()V
    //   20: aload_0
    //   21: getfield 48	okhttp3/RealCall:timeout	La/a;
    //   24: invokevirtual 150	a/a:enter	()V
    //   27: aload_0
    //   28: getfield 70	okhttp3/RealCall:eventListener	Lokhttp3/EventListener;
    //   31: aload_0
    //   32: invokevirtual 124	okhttp3/EventListener:callStart	(Lokhttp3/Call;)V
    //   35: aload_0
    //   36: getfield 32	okhttp3/RealCall:client	Lokhttp3/OkHttpClient;
    //   39: invokevirtual 128	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
    //   42: aload_0
    //   43: invokevirtual 152	okhttp3/Dispatcher:executed	(Lokhttp3/RealCall;)V
    //   46: aload_0
    //   47: invokevirtual 155	okhttp3/RealCall:getResponseWithInterceptorChain	()Lokhttp3/Response;
    //   50: astore_1
    //   51: aload_1
    //   52: ifnull +16 -> 68
    //   55: aload_0
    //   56: getfield 32	okhttp3/RealCall:client	Lokhttp3/OkHttpClient;
    //   59: invokevirtual 128	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
    //   62: aload_0
    //   63: invokevirtual 158	okhttp3/Dispatcher:finished	(Lokhttp3/RealCall;)V
    //   66: aload_1
    //   67: areturn
    //   68: new 147	java/io/IOException
    //   71: astore_1
    //   72: aload_1
    //   73: ldc -96
    //   75: invokespecial 161	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   78: aload_1
    //   79: athrow
    //   80: astore_1
    //   81: goto +21 -> 102
    //   84: astore_1
    //   85: aload_0
    //   86: aload_1
    //   87: invokevirtual 165	okhttp3/RealCall:timeoutExit	(Ljava/io/IOException;)Ljava/io/IOException;
    //   90: astore_1
    //   91: aload_0
    //   92: getfield 70	okhttp3/RealCall:eventListener	Lokhttp3/EventListener;
    //   95: aload_0
    //   96: aload_1
    //   97: invokevirtual 169	okhttp3/EventListener:callFailed	(Lokhttp3/Call;Ljava/io/IOException;)V
    //   100: aload_1
    //   101: athrow
    //   102: aload_0
    //   103: getfield 32	okhttp3/RealCall:client	Lokhttp3/OkHttpClient;
    //   106: invokevirtual 128	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
    //   109: aload_0
    //   110: invokevirtual 158	okhttp3/Dispatcher:finished	(Lokhttp3/RealCall;)V
    //   113: aload_1
    //   114: athrow
    //   115: new 138	java/lang/IllegalStateException
    //   118: astore_1
    //   119: aload_1
    //   120: ldc -116
    //   122: invokespecial 143	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   125: aload_1
    //   126: athrow
    //   127: astore_1
    //   128: aload_0
    //   129: monitorexit
    //   130: aload_1
    //   131: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	132	0	this	RealCall
    //   50	29	1	localObject1	Object
    //   80	1	1	localObject2	Object
    //   84	3	1	localIOException	IOException
    //   90	36	1	localObject3	Object
    //   127	4	1	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   35	51	80	finally
    //   68	80	80	finally
    //   85	102	80	finally
    //   35	51	84	java/io/IOException
    //   68	80	84	java/io/IOException
    //   2	16	127	finally
    //   115	127	127	finally
    //   128	130	127	finally
  }
  
  Response getResponseWithInterceptorChain()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.addAll(this.client.interceptors());
    localArrayList.add(this.retryAndFollowUpInterceptor);
    localArrayList.add(new BridgeInterceptor(this.client.cookieJar()));
    localArrayList.add(new CacheInterceptor(this.client.internalCache()));
    localArrayList.add(new ConnectInterceptor(this.client));
    if (!this.forWebSocket) {
      localArrayList.addAll(this.client.networkInterceptors());
    }
    localArrayList.add(new CallServerInterceptor(this.forWebSocket));
    return new RealInterceptorChain(localArrayList, null, null, null, 0, this.originalRequest, this, this.eventListener, this.client.connectTimeoutMillis(), this.client.readTimeoutMillis(), this.client.writeTimeoutMillis()).proceed(this.originalRequest);
  }
  
  public boolean isCanceled()
  {
    return this.retryAndFollowUpInterceptor.isCanceled();
  }
  
  public boolean isExecuted()
  {
    try
    {
      boolean bool = this.executed;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  String redactedUrl()
  {
    return this.originalRequest.url().redact();
  }
  
  public Request request()
  {
    return this.originalRequest;
  }
  
  StreamAllocation streamAllocation()
  {
    return this.retryAndFollowUpInterceptor.streamAllocation();
  }
  
  public t timeout()
  {
    return this.timeout;
  }
  
  @Nullable
  IOException timeoutExit(@Nullable IOException paramIOException)
  {
    if (!this.timeout.exit()) {
      return paramIOException;
    }
    InterruptedIOException localInterruptedIOException = new InterruptedIOException("timeout");
    if (paramIOException != null) {
      localInterruptedIOException.initCause(paramIOException);
    }
    return localInterruptedIOException;
  }
  
  String toLoggableString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str;
    if (isCanceled()) {
      str = "canceled ";
    } else {
      str = "";
    }
    localStringBuilder.append(str);
    if (this.forWebSocket) {
      str = "web socket";
    } else {
      str = "call";
    }
    localStringBuilder.append(str);
    localStringBuilder.append(" to ");
    localStringBuilder.append(redactedUrl());
    return localStringBuilder.toString();
  }
  
  final class AsyncCall
    extends NamedRunnable
  {
    private final Callback responseCallback;
    
    AsyncCall(Callback paramCallback)
    {
      super(new Object[] { RealCall.this.redactedUrl() });
      this.responseCallback = paramCallback;
    }
    
    /* Error */
    protected void execute()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   4: getfield 41	okhttp3/RealCall:timeout	La/a;
      //   7: invokevirtual 46	a/a:enter	()V
      //   10: iconst_1
      //   11: istore_1
      //   12: aload_0
      //   13: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   16: invokevirtual 50	okhttp3/RealCall:getResponseWithInterceptorChain	()Lokhttp3/Response;
      //   19: astore_2
      //   20: aload_0
      //   21: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   24: getfield 54	okhttp3/RealCall:retryAndFollowUpInterceptor	Lokhttp3/internal/http/RetryAndFollowUpInterceptor;
      //   27: invokevirtual 60	okhttp3/internal/http/RetryAndFollowUpInterceptor:isCanceled	()Z
      //   30: istore_3
      //   31: iload_3
      //   32: ifeq +38 -> 70
      //   35: aload_0
      //   36: getfield 34	okhttp3/RealCall$AsyncCall:responseCallback	Lokhttp3/Callback;
      //   39: astore 4
      //   41: aload_0
      //   42: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   45: astore 5
      //   47: new 37	java/io/IOException
      //   50: astore_2
      //   51: aload_2
      //   52: ldc 62
      //   54: invokespecial 65	java/io/IOException:<init>	(Ljava/lang/String;)V
      //   57: aload 4
      //   59: aload 5
      //   61: aload_2
      //   62: invokeinterface 71 3 0
      //   67: goto +17 -> 84
      //   70: aload_0
      //   71: getfield 34	okhttp3/RealCall$AsyncCall:responseCallback	Lokhttp3/Callback;
      //   74: aload_0
      //   75: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   78: aload_2
      //   79: invokeinterface 75 3 0
      //   84: aload_0
      //   85: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   88: getfield 79	okhttp3/RealCall:client	Lokhttp3/OkHttpClient;
      //   91: invokevirtual 85	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   94: aload_0
      //   95: invokevirtual 91	okhttp3/Dispatcher:finished	(Lokhttp3/RealCall$AsyncCall;)V
      //   98: goto +112 -> 210
      //   101: astore_2
      //   102: goto +10 -> 112
      //   105: astore_2
      //   106: goto +105 -> 211
      //   109: astore_2
      //   110: iconst_0
      //   111: istore_1
      //   112: aload_0
      //   113: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   116: aload_2
      //   117: invokevirtual 95	okhttp3/RealCall:timeoutExit	(Ljava/io/IOException;)Ljava/io/IOException;
      //   120: astore 5
      //   122: iload_1
      //   123: ifeq +53 -> 176
      //   126: invokestatic 101	okhttp3/internal/platform/Platform:get	()Lokhttp3/internal/platform/Platform;
      //   129: astore_2
      //   130: new 103	java/lang/StringBuilder
      //   133: astore 4
      //   135: aload 4
      //   137: invokespecial 105	java/lang/StringBuilder:<init>	()V
      //   140: aload 4
      //   142: ldc 107
      //   144: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   147: pop
      //   148: aload 4
      //   150: aload_0
      //   151: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   154: invokevirtual 114	okhttp3/RealCall:toLoggableString	()Ljava/lang/String;
      //   157: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   160: pop
      //   161: aload_2
      //   162: iconst_4
      //   163: aload 4
      //   165: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   168: aload 5
      //   170: invokevirtual 121	okhttp3/internal/platform/Platform:log	(ILjava/lang/String;Ljava/lang/Throwable;)V
      //   173: goto -89 -> 84
      //   176: aload_0
      //   177: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   180: invokestatic 125	okhttp3/RealCall:access$000	(Lokhttp3/RealCall;)Lokhttp3/EventListener;
      //   183: aload_0
      //   184: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   187: aload 5
      //   189: invokevirtual 130	okhttp3/EventListener:callFailed	(Lokhttp3/Call;Ljava/io/IOException;)V
      //   192: aload_0
      //   193: getfield 34	okhttp3/RealCall$AsyncCall:responseCallback	Lokhttp3/Callback;
      //   196: aload_0
      //   197: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   200: aload 5
      //   202: invokeinterface 71 3 0
      //   207: goto -123 -> 84
      //   210: return
      //   211: aload_0
      //   212: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   215: getfield 79	okhttp3/RealCall:client	Lokhttp3/OkHttpClient;
      //   218: invokevirtual 85	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   221: aload_0
      //   222: invokevirtual 91	okhttp3/Dispatcher:finished	(Lokhttp3/RealCall$AsyncCall;)V
      //   225: aload_2
      //   226: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	227	0	this	AsyncCall
      //   11	112	1	i	int
      //   19	60	2	localObject1	Object
      //   101	1	2	localIOException1	IOException
      //   105	1	2	localObject2	Object
      //   109	8	2	localIOException2	IOException
      //   129	97	2	localPlatform	Platform
      //   30	2	3	bool	boolean
      //   39	125	4	localObject3	Object
      //   45	156	5	localObject4	Object
      // Exception table:
      //   from	to	target	type
      //   35	67	101	java/io/IOException
      //   70	84	101	java/io/IOException
      //   12	31	105	finally
      //   35	67	105	finally
      //   70	84	105	finally
      //   112	122	105	finally
      //   126	173	105	finally
      //   176	207	105	finally
      //   12	31	109	java/io/IOException
    }
    
    /* Error */
    void executeOn(java.util.concurrent.ExecutorService paramExecutorService)
    {
      // Byte code:
      //   0: aload_1
      //   1: aload_0
      //   2: invokeinterface 139 2 0
      //   7: goto +67 -> 74
      //   10: astore_1
      //   11: goto +64 -> 75
      //   14: astore_2
      //   15: new 141	java/io/InterruptedIOException
      //   18: astore_1
      //   19: aload_1
      //   20: ldc -113
      //   22: invokespecial 144	java/io/InterruptedIOException:<init>	(Ljava/lang/String;)V
      //   25: aload_1
      //   26: aload_2
      //   27: invokevirtual 148	java/io/InterruptedIOException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
      //   30: pop
      //   31: aload_0
      //   32: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   35: invokestatic 125	okhttp3/RealCall:access$000	(Lokhttp3/RealCall;)Lokhttp3/EventListener;
      //   38: aload_0
      //   39: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   42: aload_1
      //   43: invokevirtual 130	okhttp3/EventListener:callFailed	(Lokhttp3/Call;Ljava/io/IOException;)V
      //   46: aload_0
      //   47: getfield 34	okhttp3/RealCall$AsyncCall:responseCallback	Lokhttp3/Callback;
      //   50: aload_0
      //   51: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   54: aload_1
      //   55: invokeinterface 71 3 0
      //   60: aload_0
      //   61: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   64: getfield 79	okhttp3/RealCall:client	Lokhttp3/OkHttpClient;
      //   67: invokevirtual 85	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   70: aload_0
      //   71: invokevirtual 91	okhttp3/Dispatcher:finished	(Lokhttp3/RealCall$AsyncCall;)V
      //   74: return
      //   75: aload_0
      //   76: getfield 21	okhttp3/RealCall$AsyncCall:this$0	Lokhttp3/RealCall;
      //   79: getfield 79	okhttp3/RealCall:client	Lokhttp3/OkHttpClient;
      //   82: invokevirtual 85	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   85: aload_0
      //   86: invokevirtual 91	okhttp3/Dispatcher:finished	(Lokhttp3/RealCall$AsyncCall;)V
      //   89: aload_1
      //   90: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	91	0	this	AsyncCall
      //   0	91	1	paramExecutorService	java.util.concurrent.ExecutorService
      //   14	13	2	localRejectedExecutionException	java.util.concurrent.RejectedExecutionException
      // Exception table:
      //   from	to	target	type
      //   0	7	10	finally
      //   15	60	10	finally
      //   0	7	14	java/util/concurrent/RejectedExecutionException
    }
    
    RealCall get()
    {
      return RealCall.this;
    }
    
    String host()
    {
      return RealCall.this.originalRequest.url().host();
    }
    
    Request request()
    {
      return RealCall.this.originalRequest;
    }
  }
}


/* Location:              ~/okhttp3/RealCall.class
 *
 * Reversed by:           J
 */