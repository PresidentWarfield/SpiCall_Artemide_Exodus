package a;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public class a
  extends t
{
  private static final long IDLE_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(60L);
  private static final long IDLE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(IDLE_TIMEOUT_MILLIS);
  private static final int TIMEOUT_WRITE_SIZE = 65536;
  @Nullable
  static a head;
  private boolean inQueue;
  @Nullable
  private a next;
  private long timeoutAt;
  
  @Nullable
  static a awaitTimeout()
  {
    Object localObject1 = head.next;
    Object localObject2 = null;
    long l1;
    if (localObject1 == null)
    {
      l1 = System.nanoTime();
      a.class.wait(IDLE_TIMEOUT_MILLIS);
      localObject1 = localObject2;
      if (head.next == null)
      {
        localObject1 = localObject2;
        if (System.nanoTime() - l1 >= IDLE_TIMEOUT_NANOS) {
          localObject1 = head;
        }
      }
      return (a)localObject1;
    }
    long l2 = ((a)localObject1).remainingNanos(System.nanoTime());
    if (l2 > 0L)
    {
      l1 = l2 / 1000000L;
      a.class.wait(l1, (int)(l2 - 1000000L * l1));
      return null;
    }
    head.next = ((a)localObject1).next;
    ((a)localObject1).next = null;
    return (a)localObject1;
  }
  
  private static boolean cancelScheduledTimeout(a parama)
  {
    try
    {
      for (a locala = head; locala != null; locala = locala.next) {
        if (locala.next == parama)
        {
          locala.next = parama.next;
          parama.next = null;
          return false;
        }
      }
      return true;
    }
    finally {}
  }
  
  private long remainingNanos(long paramLong)
  {
    return this.timeoutAt - paramLong;
  }
  
  private static void scheduleTimeout(a parama, long paramLong, boolean paramBoolean)
  {
    try
    {
      if (head == null)
      {
        localObject = new a/a;
        ((a)localObject).<init>();
        head = (a)localObject;
        localObject = new a/a$a;
        ((a)localObject).<init>();
        ((a)localObject).start();
      }
      long l = System.nanoTime();
      if ((paramLong != 0L) && (paramBoolean))
      {
        parama.timeoutAt = (Math.min(paramLong, parama.deadlineNanoTime() - l) + l);
      }
      else if (paramLong != 0L)
      {
        parama.timeoutAt = (paramLong + l);
      }
      else
      {
        if (!paramBoolean) {
          break label184;
        }
        parama.timeoutAt = parama.deadlineNanoTime();
      }
      paramLong = parama.remainingNanos(l);
      for (Object localObject = head; (((a)localObject).next != null) && (paramLong >= ((a)localObject).next.remainingNanos(l)); localObject = ((a)localObject).next) {}
      parama.next = ((a)localObject).next;
      ((a)localObject).next = parama;
      if (localObject == head) {
        a.class.notify();
      }
      return;
      label184:
      parama = new java/lang/AssertionError;
      parama.<init>();
      throw parama;
    }
    finally {}
  }
  
  public final void enter()
  {
    if (!this.inQueue)
    {
      long l = timeoutNanos();
      boolean bool = hasDeadline();
      if ((l == 0L) && (!bool)) {
        return;
      }
      this.inQueue = true;
      scheduleTimeout(this, l, bool);
      return;
    }
    throw new IllegalStateException("Unbalanced enter/exit");
  }
  
  final IOException exit(IOException paramIOException)
  {
    if (!exit()) {
      return paramIOException;
    }
    return newTimeoutException(paramIOException);
  }
  
  final void exit(boolean paramBoolean)
  {
    if ((exit()) && (paramBoolean)) {
      throw newTimeoutException(null);
    }
  }
  
  public final boolean exit()
  {
    if (!this.inQueue) {
      return false;
    }
    this.inQueue = false;
    return cancelScheduledTimeout(this);
  }
  
  protected IOException newTimeoutException(@Nullable IOException paramIOException)
  {
    InterruptedIOException localInterruptedIOException = new InterruptedIOException("timeout");
    if (paramIOException != null) {
      localInterruptedIOException.initCause(paramIOException);
    }
    return localInterruptedIOException;
  }
  
  public final r sink(final r paramr)
  {
    new r()
    {
      /* Error */
      public void close()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 19	a/a$1:b	La/a;
        //   4: invokevirtual 31	a/a:enter	()V
        //   7: aload_0
        //   8: getfield 21	a/a$1:a	La/r;
        //   11: invokeinterface 33 1 0
        //   16: aload_0
        //   17: getfield 19	a/a$1:b	La/a;
        //   20: iconst_1
        //   21: invokevirtual 37	a/a:exit	(Z)V
        //   24: return
        //   25: astore_1
        //   26: goto +13 -> 39
        //   29: astore_1
        //   30: aload_0
        //   31: getfield 19	a/a$1:b	La/a;
        //   34: aload_1
        //   35: invokevirtual 40	a/a:exit	(Ljava/io/IOException;)Ljava/io/IOException;
        //   38: athrow
        //   39: aload_0
        //   40: getfield 19	a/a$1:b	La/a;
        //   43: iconst_0
        //   44: invokevirtual 37	a/a:exit	(Z)V
        //   47: aload_1
        //   48: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	49	0	this	1
        //   25	1	1	localObject	Object
        //   29	19	1	localIOException	IOException
        // Exception table:
        //   from	to	target	type
        //   7	16	25	finally
        //   30	39	25	finally
        //   7	16	29	java/io/IOException
      }
      
      /* Error */
      public void flush()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 19	a/a$1:b	La/a;
        //   4: invokevirtual 31	a/a:enter	()V
        //   7: aload_0
        //   8: getfield 21	a/a$1:a	La/r;
        //   11: invokeinterface 43 1 0
        //   16: aload_0
        //   17: getfield 19	a/a$1:b	La/a;
        //   20: iconst_1
        //   21: invokevirtual 37	a/a:exit	(Z)V
        //   24: return
        //   25: astore_1
        //   26: goto +13 -> 39
        //   29: astore_1
        //   30: aload_0
        //   31: getfield 19	a/a$1:b	La/a;
        //   34: aload_1
        //   35: invokevirtual 40	a/a:exit	(Ljava/io/IOException;)Ljava/io/IOException;
        //   38: athrow
        //   39: aload_0
        //   40: getfield 19	a/a$1:b	La/a;
        //   43: iconst_0
        //   44: invokevirtual 37	a/a:exit	(Z)V
        //   47: aload_1
        //   48: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	49	0	this	1
        //   25	1	1	localObject	Object
        //   29	19	1	localIOException	IOException
        // Exception table:
        //   from	to	target	type
        //   7	16	25	finally
        //   30	39	25	finally
        //   7	16	29	java/io/IOException
      }
      
      public t timeout()
      {
        return a.this;
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("AsyncTimeout.sink(");
        localStringBuilder.append(paramr);
        localStringBuilder.append(")");
        return localStringBuilder.toString();
      }
      
      /* Error */
      public void write(c paramAnonymousc, long paramAnonymousLong)
      {
        // Byte code:
        //   0: aload_1
        //   1: getfield 70	a/c:b	J
        //   4: lconst_0
        //   5: lload_2
        //   6: invokestatic 75	a/u:a	(JJJ)V
        //   9: lconst_0
        //   10: lstore 4
        //   12: lload_2
        //   13: lconst_0
        //   14: lcmp
        //   15: ifle +121 -> 136
        //   18: aload_1
        //   19: getfield 78	a/c:a	La/o;
        //   22: astore 6
        //   24: lload 4
        //   26: lstore 7
        //   28: lload 4
        //   30: ldc2_w 79
        //   33: lcmp
        //   34: ifge +43 -> 77
        //   37: lload 4
        //   39: aload 6
        //   41: getfield 86	a/o:c	I
        //   44: aload 6
        //   46: getfield 88	a/o:b	I
        //   49: isub
        //   50: i2l
        //   51: ladd
        //   52: lstore 4
        //   54: lload 4
        //   56: lload_2
        //   57: lcmp
        //   58: iflt +9 -> 67
        //   61: lload_2
        //   62: lstore 7
        //   64: goto +13 -> 77
        //   67: aload 6
        //   69: getfield 91	a/o:f	La/o;
        //   72: astore 6
        //   74: goto -50 -> 24
        //   77: aload_0
        //   78: getfield 19	a/a$1:b	La/a;
        //   81: invokevirtual 31	a/a:enter	()V
        //   84: aload_0
        //   85: getfield 21	a/a$1:a	La/r;
        //   88: aload_1
        //   89: lload 7
        //   91: invokeinterface 93 4 0
        //   96: lload_2
        //   97: lload 7
        //   99: lsub
        //   100: lstore_2
        //   101: aload_0
        //   102: getfield 19	a/a$1:b	La/a;
        //   105: iconst_1
        //   106: invokevirtual 37	a/a:exit	(Z)V
        //   109: goto -100 -> 9
        //   112: astore_1
        //   113: goto +13 -> 126
        //   116: astore_1
        //   117: aload_0
        //   118: getfield 19	a/a$1:b	La/a;
        //   121: aload_1
        //   122: invokevirtual 40	a/a:exit	(Ljava/io/IOException;)Ljava/io/IOException;
        //   125: athrow
        //   126: aload_0
        //   127: getfield 19	a/a$1:b	La/a;
        //   130: iconst_0
        //   131: invokevirtual 37	a/a:exit	(Z)V
        //   134: aload_1
        //   135: athrow
        //   136: return
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	137	0	this	1
        //   0	137	1	paramAnonymousc	c
        //   0	137	2	paramAnonymousLong	long
        //   10	45	4	l1	long
        //   22	51	6	localo	o
        //   26	72	7	l2	long
        // Exception table:
        //   from	to	target	type
        //   84	96	112	finally
        //   117	126	112	finally
        //   84	96	116	java/io/IOException
      }
    };
  }
  
  public final s source(final s params)
  {
    new s()
    {
      /* Error */
      public void close()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 21	a/a$2:a	La/s;
        //   4: invokeinterface 30 1 0
        //   9: aload_0
        //   10: getfield 19	a/a$2:b	La/a;
        //   13: iconst_1
        //   14: invokevirtual 34	a/a:exit	(Z)V
        //   17: return
        //   18: astore_1
        //   19: goto +13 -> 32
        //   22: astore_1
        //   23: aload_0
        //   24: getfield 19	a/a$2:b	La/a;
        //   27: aload_1
        //   28: invokevirtual 37	a/a:exit	(Ljava/io/IOException;)Ljava/io/IOException;
        //   31: athrow
        //   32: aload_0
        //   33: getfield 19	a/a$2:b	La/a;
        //   36: iconst_0
        //   37: invokevirtual 34	a/a:exit	(Z)V
        //   40: aload_1
        //   41: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	42	0	this	2
        //   18	1	1	localObject	Object
        //   22	19	1	localIOException	IOException
        // Exception table:
        //   from	to	target	type
        //   0	9	18	finally
        //   23	32	18	finally
        //   0	9	22	java/io/IOException
      }
      
      /* Error */
      public long read(c paramAnonymousc, long paramAnonymousLong)
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 19	a/a$2:b	La/a;
        //   4: invokevirtual 42	a/a:enter	()V
        //   7: aload_0
        //   8: getfield 21	a/a$2:a	La/s;
        //   11: aload_1
        //   12: lload_2
        //   13: invokeinterface 44 4 0
        //   18: lstore_2
        //   19: aload_0
        //   20: getfield 19	a/a$2:b	La/a;
        //   23: iconst_1
        //   24: invokevirtual 34	a/a:exit	(Z)V
        //   27: lload_2
        //   28: lreturn
        //   29: astore_1
        //   30: goto +13 -> 43
        //   33: astore_1
        //   34: aload_0
        //   35: getfield 19	a/a$2:b	La/a;
        //   38: aload_1
        //   39: invokevirtual 37	a/a:exit	(Ljava/io/IOException;)Ljava/io/IOException;
        //   42: athrow
        //   43: aload_0
        //   44: getfield 19	a/a$2:b	La/a;
        //   47: iconst_0
        //   48: invokevirtual 34	a/a:exit	(Z)V
        //   51: aload_1
        //   52: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	53	0	this	2
        //   0	53	1	paramAnonymousc	c
        //   0	53	2	paramAnonymousLong	long
        // Exception table:
        //   from	to	target	type
        //   7	19	29	finally
        //   34	43	29	finally
        //   7	19	33	java/io/IOException
      }
      
      public t timeout()
      {
        return a.this;
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("AsyncTimeout.source(");
        localStringBuilder.append(params);
        localStringBuilder.append(")");
        return localStringBuilder.toString();
      }
    };
  }
  
  protected void timedOut() {}
  
  private static final class a
    extends Thread
  {
    a()
    {
      super();
      setDaemon(true);
    }
    
    public void run()
    {
      try
      {
        for (;;)
        {
          try
          {
            a locala = a.awaitTimeout();
            if (locala == null) {}
            if (locala == a.head)
            {
              a.head = null;
              return;
            }
            locala.timedOut();
          }
          finally {}
        }
      }
      catch (InterruptedException localInterruptedException) {}
    }
  }
}


/* Location:              ~/a/a.class
 *
 * Reversed by:           J
 */