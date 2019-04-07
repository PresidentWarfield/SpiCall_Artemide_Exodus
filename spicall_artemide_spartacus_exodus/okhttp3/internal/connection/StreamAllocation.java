package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.List;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.ConnectionPool;
import okhttp3.EventListener;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.StreamResetException;

public final class StreamAllocation
{
  public final Address address;
  public final Call call;
  private final Object callStackTrace;
  private boolean canceled;
  private HttpCodec codec;
  private RealConnection connection;
  private final ConnectionPool connectionPool;
  public final EventListener eventListener;
  private int refusedStreamCount;
  private boolean released;
  private boolean reportedAcquired;
  private Route route;
  private RouteSelector.Selection routeSelection;
  private final RouteSelector routeSelector;
  
  public StreamAllocation(ConnectionPool paramConnectionPool, Address paramAddress, Call paramCall, EventListener paramEventListener, Object paramObject)
  {
    this.connectionPool = paramConnectionPool;
    this.address = paramAddress;
    this.call = paramCall;
    this.eventListener = paramEventListener;
    this.routeSelector = new RouteSelector(paramAddress, routeDatabase(), paramCall, paramEventListener);
    this.callStackTrace = paramObject;
  }
  
  private Socket deallocate(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramBoolean3) {
      this.codec = null;
    }
    if (paramBoolean2) {
      this.released = true;
    }
    Object localObject = this.connection;
    if (localObject != null)
    {
      if (paramBoolean1) {
        ((RealConnection)localObject).noNewStreams = true;
      }
      if ((this.codec == null) && ((this.released) || (this.connection.noNewStreams)))
      {
        release(this.connection);
        if (this.connection.allocations.isEmpty())
        {
          this.connection.idleAtNanos = System.nanoTime();
          if (Internal.instance.connectionBecameIdle(this.connectionPool, this.connection))
          {
            localObject = this.connection.socket();
            break label128;
          }
        }
        localObject = null;
        label128:
        this.connection = null;
        break label139;
      }
    }
    localObject = null;
    label139:
    return (Socket)localObject;
  }
  
  private RealConnection findConnection(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    synchronized (this.connectionPool)
    {
      if (!this.released)
      {
        if (this.codec == null)
        {
          if (!this.canceled)
          {
            Object localObject2 = this.connection;
            Object localObject3 = releaseIfNoNewStreams();
            Object localObject4 = this.connection;
            Object localObject9 = null;
            if (localObject4 != null)
            {
              localObject4 = this.connection;
              localObject2 = null;
            }
            else
            {
              localObject4 = null;
            }
            Object localObject10 = localObject2;
            if (!this.reportedAcquired) {
              localObject10 = null;
            }
            int i;
            if (localObject4 == null)
            {
              Internal.instance.get(this.connectionPool, this.address, this, null);
              if (this.connection != null)
              {
                localObject2 = this.connection;
                localObject4 = null;
                i = 1;
              }
              else
              {
                ??? = this.route;
                i = 0;
                localObject2 = localObject4;
                localObject4 = ???;
              }
            }
            else
            {
              localObject2 = localObject4;
              localObject4 = null;
              i = 0;
            }
            Util.closeQuietly((Socket)localObject3);
            if (localObject10 != null) {
              this.eventListener.connectionReleased(this.call, (Connection)localObject10);
            }
            if (i != 0) {
              this.eventListener.connectionAcquired(this.call, (Connection)localObject2);
            }
            if (localObject2 != null) {
              return (RealConnection)localObject2;
            }
            if (localObject4 == null)
            {
              localObject10 = this.routeSelection;
              if ((localObject10 == null) || (!((RouteSelector.Selection)localObject10).hasNext()))
              {
                this.routeSelection = this.routeSelector.next();
                j = 1;
                break label255;
              }
            }
            int j = 0;
            label255:
            synchronized (this.connectionPool)
            {
              if (!this.canceled)
              {
                int k = i;
                localObject10 = localObject2;
                if (j != 0)
                {
                  localObject3 = this.routeSelection.getAll();
                  int m = ((List)localObject3).size();
                  for (j = 0;; j++)
                  {
                    k = i;
                    localObject10 = localObject2;
                    if (j >= m) {
                      break;
                    }
                    ??? = (Route)((List)localObject3).get(j);
                    Internal.instance.get(this.connectionPool, this.address, this, (Route)???);
                    if (this.connection != null)
                    {
                      localObject10 = this.connection;
                      this.route = ((Route)???);
                      k = 1;
                      break;
                    }
                  }
                }
                localObject2 = localObject10;
                if (k == 0)
                {
                  localObject2 = localObject4;
                  if (localObject4 == null) {
                    localObject2 = this.routeSelection.next();
                  }
                  this.route = ((Route)localObject2);
                  this.refusedStreamCount = 0;
                  localObject4 = new okhttp3/internal/connection/RealConnection;
                  ((RealConnection)localObject4).<init>(this.connectionPool, (Route)localObject2);
                  acquire((RealConnection)localObject4, false);
                  localObject2 = localObject4;
                }
                if (k != 0)
                {
                  this.eventListener.connectionAcquired(this.call, (Connection)localObject2);
                  return (RealConnection)localObject2;
                }
                ((RealConnection)localObject2).connect(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean, this.call, this.eventListener);
                routeDatabase().connected(((RealConnection)localObject2).route());
                synchronized (this.connectionPool)
                {
                  this.reportedAcquired = true;
                  Internal.instance.put(this.connectionPool, (RealConnection)localObject2);
                  localObject10 = localObject9;
                  localObject4 = localObject2;
                  if (((RealConnection)localObject2).isMultiplexed())
                  {
                    localObject10 = Internal.instance.deduplicate(this.connectionPool, this.address, this);
                    localObject4 = this.connection;
                  }
                  Util.closeQuietly((Socket)localObject10);
                  this.eventListener.connectionAcquired(this.call, (Connection)localObject4);
                  return (RealConnection)localObject4;
                }
              }
              IOException localIOException = new java/io/IOException;
              localIOException.<init>("Canceled");
              throw localIOException;
            }
          }
          localObject7 = new java/io/IOException;
          ((IOException)localObject7).<init>("Canceled");
          throw ((Throwable)localObject7);
        }
        localObject7 = new java/lang/IllegalStateException;
        ((IllegalStateException)localObject7).<init>("codec != null");
        throw ((Throwable)localObject7);
      }
      Object localObject7 = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject7).<init>("released");
      throw ((Throwable)localObject7);
    }
  }
  
  private RealConnection findHealthyConnection(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2)
  {
    for (;;)
    {
      RealConnection localRealConnection = findConnection(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean1);
      synchronized (this.connectionPool)
      {
        if (localRealConnection.successCount == 0) {
          return localRealConnection;
        }
        if (!localRealConnection.isHealthy(paramBoolean2))
        {
          noNewStreams();
          continue;
        }
        return localRealConnection;
      }
    }
  }
  
  private void release(RealConnection paramRealConnection)
  {
    int i = paramRealConnection.allocations.size();
    for (int j = 0; j < i; j++) {
      if (((Reference)paramRealConnection.allocations.get(j)).get() == this)
      {
        paramRealConnection.allocations.remove(j);
        return;
      }
    }
    throw new IllegalStateException();
  }
  
  private Socket releaseIfNoNewStreams()
  {
    RealConnection localRealConnection = this.connection;
    if ((localRealConnection != null) && (localRealConnection.noNewStreams)) {
      return deallocate(false, false, true);
    }
    return null;
  }
  
  private RouteDatabase routeDatabase()
  {
    return Internal.instance.routeDatabase(this.connectionPool);
  }
  
  public void acquire(RealConnection paramRealConnection, boolean paramBoolean)
  {
    if (this.connection == null)
    {
      this.connection = paramRealConnection;
      this.reportedAcquired = paramBoolean;
      paramRealConnection.allocations.add(new StreamAllocationReference(this, this.callStackTrace));
      return;
    }
    throw new IllegalStateException();
  }
  
  public void cancel()
  {
    synchronized (this.connectionPool)
    {
      this.canceled = true;
      HttpCodec localHttpCodec = this.codec;
      RealConnection localRealConnection = this.connection;
      if (localHttpCodec != null) {
        localHttpCodec.cancel();
      } else if (localRealConnection != null) {
        localRealConnection.cancel();
      }
      return;
    }
  }
  
  public HttpCodec codec()
  {
    synchronized (this.connectionPool)
    {
      HttpCodec localHttpCodec = this.codec;
      return localHttpCodec;
    }
  }
  
  public RealConnection connection()
  {
    try
    {
      RealConnection localRealConnection = this.connection;
      return localRealConnection;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean hasMoreRoutes()
  {
    if (this.route == null)
    {
      RouteSelector.Selection localSelection = this.routeSelection;
      if (((localSelection == null) || (!localSelection.hasNext())) && (!this.routeSelector.hasNext())) {
        return false;
      }
    }
    boolean bool = true;
    return bool;
  }
  
  /* Error */
  public HttpCodec newStream(okhttp3.OkHttpClient arg1, okhttp3.Interceptor.Chain paramChain, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_2
    //   1: invokeinterface 267 1 0
    //   6: istore 4
    //   8: aload_2
    //   9: invokeinterface 270 1 0
    //   14: istore 5
    //   16: aload_2
    //   17: invokeinterface 273 1 0
    //   22: istore 6
    //   24: aload_1
    //   25: invokevirtual 278	okhttp3/OkHttpClient:pingIntervalMillis	()I
    //   28: istore 7
    //   30: aload_1
    //   31: invokevirtual 281	okhttp3/OkHttpClient:retryOnConnectionFailure	()Z
    //   34: istore 8
    //   36: aload_0
    //   37: iload 4
    //   39: iload 5
    //   41: iload 6
    //   43: iload 7
    //   45: iload 8
    //   47: iload_3
    //   48: invokespecial 283	okhttp3/internal/connection/StreamAllocation:findHealthyConnection	(IIIIZZ)Lokhttp3/internal/connection/RealConnection;
    //   51: aload_1
    //   52: aload_2
    //   53: aload_0
    //   54: invokevirtual 287	okhttp3/internal/connection/RealConnection:newCodec	(Lokhttp3/OkHttpClient;Lokhttp3/Interceptor$Chain;Lokhttp3/internal/connection/StreamAllocation;)Lokhttp3/internal/http/HttpCodec;
    //   57: astore_2
    //   58: aload_0
    //   59: getfield 44	okhttp3/internal/connection/StreamAllocation:connectionPool	Lokhttp3/ConnectionPool;
    //   62: astore_1
    //   63: aload_1
    //   64: monitorenter
    //   65: aload_0
    //   66: aload_2
    //   67: putfield 67	okhttp3/internal/connection/StreamAllocation:codec	Lokhttp3/internal/http/HttpCodec;
    //   70: aload_1
    //   71: monitorexit
    //   72: aload_2
    //   73: areturn
    //   74: astore_2
    //   75: aload_1
    //   76: monitorexit
    //   77: aload_2
    //   78: athrow
    //   79: astore_1
    //   80: new 289	okhttp3/internal/connection/RouteException
    //   83: dup
    //   84: aload_1
    //   85: invokespecial 292	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   88: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	89	0	this	StreamAllocation
    //   0	89	2	paramChain	okhttp3.Interceptor.Chain
    //   0	89	3	paramBoolean	boolean
    //   6	32	4	i	int
    //   14	26	5	j	int
    //   22	20	6	k	int
    //   28	16	7	m	int
    //   34	12	8	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   65	72	74	finally
    //   75	77	74	finally
    //   36	65	79	java/io/IOException
    //   77	79	79	java/io/IOException
  }
  
  public void noNewStreams()
  {
    synchronized (this.connectionPool)
    {
      RealConnection localRealConnection = this.connection;
      Socket localSocket = deallocate(true, false, false);
      if (this.connection != null) {
        localRealConnection = null;
      }
      Util.closeQuietly(localSocket);
      if (localRealConnection != null) {
        this.eventListener.connectionReleased(this.call, localRealConnection);
      }
      return;
    }
  }
  
  public void release()
  {
    synchronized (this.connectionPool)
    {
      RealConnection localRealConnection = this.connection;
      Socket localSocket = deallocate(false, true, false);
      if (this.connection != null) {
        localRealConnection = null;
      }
      Util.closeQuietly(localSocket);
      if (localRealConnection != null)
      {
        Internal.instance.timeoutExit(this.call, null);
        this.eventListener.connectionReleased(this.call, localRealConnection);
        this.eventListener.callEnd(this.call);
      }
      return;
    }
  }
  
  public Socket releaseAndAcquire(RealConnection paramRealConnection)
  {
    if ((this.codec == null) && (this.connection.allocations.size() == 1))
    {
      Reference localReference = (Reference)this.connection.allocations.get(0);
      Socket localSocket = deallocate(true, false, false);
      this.connection = paramRealConnection;
      paramRealConnection.allocations.add(localReference);
      return localSocket;
    }
    throw new IllegalStateException();
  }
  
  public Route route()
  {
    return this.route;
  }
  
  public void streamFailed(IOException paramIOException)
  {
    synchronized (this.connectionPool)
    {
      boolean bool;
      if ((paramIOException instanceof StreamResetException))
      {
        paramIOException = ((StreamResetException)paramIOException).errorCode;
        if (paramIOException == ErrorCode.REFUSED_STREAM)
        {
          this.refusedStreamCount += 1;
          if (this.refusedStreamCount > 1)
          {
            this.route = null;
            bool = true;
            break label148;
          }
        }
        else if (paramIOException != ErrorCode.CANCEL)
        {
          this.route = null;
          bool = true;
          break label148;
        }
        bool = false;
      }
      else if ((this.connection != null) && ((!this.connection.isMultiplexed()) || ((paramIOException instanceof ConnectionShutdownException))))
      {
        if (this.connection.successCount == 0)
        {
          if ((this.route != null) && (paramIOException != null)) {
            this.routeSelector.connectFailed(this.route, paramIOException);
          }
          this.route = null;
        }
        bool = true;
      }
      else
      {
        bool = false;
      }
      label148:
      paramIOException = this.connection;
      Socket localSocket = deallocate(bool, false, true);
      if ((this.connection != null) || (!this.reportedAcquired)) {
        paramIOException = null;
      }
      Util.closeQuietly(localSocket);
      if (paramIOException != null) {
        this.eventListener.connectionReleased(this.call, paramIOException);
      }
      return;
    }
  }
  
  public void streamFinished(boolean paramBoolean, HttpCodec paramHttpCodec, long paramLong, IOException paramIOException)
  {
    this.eventListener.responseBodyEnd(this.call, paramLong);
    ConnectionPool localConnectionPool = this.connectionPool;
    if (paramHttpCodec != null) {}
    try
    {
      if (paramHttpCodec == this.codec)
      {
        if (!paramBoolean)
        {
          paramHttpCodec = this.connection;
          paramHttpCodec.successCount += 1;
        }
        paramHttpCodec = this.connection;
        localObject = deallocate(paramBoolean, false, true);
        if (this.connection != null) {
          paramHttpCodec = null;
        }
        paramBoolean = this.released;
        Util.closeQuietly((Socket)localObject);
        if (paramHttpCodec != null) {
          this.eventListener.connectionReleased(this.call, paramHttpCodec);
        }
        if (paramIOException != null)
        {
          paramHttpCodec = Internal.instance.timeoutExit(this.call, paramIOException);
          this.eventListener.callFailed(this.call, paramHttpCodec);
        }
        else if (paramBoolean)
        {
          Internal.instance.timeoutExit(this.call, null);
          this.eventListener.callEnd(this.call);
        }
        return;
      }
      Object localObject = new java/lang/IllegalStateException;
      paramIOException = new java/lang/StringBuilder;
      paramIOException.<init>();
      paramIOException.append("expected ");
      paramIOException.append(this.codec);
      paramIOException.append(" but was ");
      paramIOException.append(paramHttpCodec);
      ((IllegalStateException)localObject).<init>(paramIOException.toString());
      throw ((Throwable)localObject);
    }
    finally {}
  }
  
  public String toString()
  {
    Object localObject = connection();
    if (localObject != null) {
      localObject = ((RealConnection)localObject).toString();
    } else {
      localObject = this.address.toString();
    }
    return (String)localObject;
  }
  
  public static final class StreamAllocationReference
    extends WeakReference<StreamAllocation>
  {
    public final Object callStackTrace;
    
    StreamAllocationReference(StreamAllocation paramStreamAllocation, Object paramObject)
    {
      super();
      this.callStackTrace = paramObject;
    }
  }
}


/* Location:              ~/okhttp3/internal/connection/StreamAllocation.class
 *
 * Reversed by:           J
 */