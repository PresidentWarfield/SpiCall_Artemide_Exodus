package okhttp3.internal.cache2;

import a.c;
import a.f;
import a.s;
import a.t;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import okhttp3.internal.Util;

final class Relay
{
  private static final long FILE_HEADER_SIZE = 32L;
  static final f PREFIX_CLEAN = f.a("OkHttp cache v1\n");
  static final f PREFIX_DIRTY = f.a("OkHttp DIRTY :(\n");
  private static final int SOURCE_FILE = 2;
  private static final int SOURCE_UPSTREAM = 1;
  final c buffer = new c();
  final long bufferMaxSize;
  boolean complete;
  RandomAccessFile file;
  private final f metadata;
  int sourceCount;
  s upstream;
  final c upstreamBuffer = new c();
  long upstreamPos;
  Thread upstreamReader;
  
  private Relay(RandomAccessFile paramRandomAccessFile, s params, long paramLong1, f paramf, long paramLong2)
  {
    this.file = paramRandomAccessFile;
    this.upstream = params;
    boolean bool;
    if (params == null) {
      bool = true;
    } else {
      bool = false;
    }
    this.complete = bool;
    this.upstreamPos = paramLong1;
    this.metadata = paramf;
    this.bufferMaxSize = paramLong2;
  }
  
  public static Relay edit(File paramFile, s params, f paramf, long paramLong)
  {
    paramFile = new RandomAccessFile(paramFile, "rw");
    params = new Relay(paramFile, params, 0L, paramf, paramLong);
    paramFile.setLength(0L);
    params.writeHeader(PREFIX_DIRTY, -1L, -1L);
    return params;
  }
  
  public static Relay read(File paramFile)
  {
    RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramFile, "rw");
    paramFile = new FileOperator(localRandomAccessFile.getChannel());
    c localc = new c();
    paramFile.read(0L, localc, 32L);
    if (localc.d(PREFIX_CLEAN.h()).equals(PREFIX_CLEAN))
    {
      long l1 = localc.k();
      long l2 = localc.k();
      localc = new c();
      paramFile.read(l1 + 32L, localc, l2);
      return new Relay(localRandomAccessFile, null, l1, localc.p(), 0L);
    }
    throw new IOException("unreadable cache file");
  }
  
  private void writeHeader(f paramf, long paramLong1, long paramLong2)
  {
    c localc = new c();
    localc.a(paramf);
    localc.j(paramLong1);
    localc.j(paramLong2);
    if (localc.a() == 32L)
    {
      new FileOperator(this.file.getChannel()).write(0L, localc, 32L);
      return;
    }
    throw new IllegalArgumentException();
  }
  
  private void writeMetadata(long paramLong)
  {
    c localc = new c();
    localc.a(this.metadata);
    new FileOperator(this.file.getChannel()).write(32L + paramLong, localc, this.metadata.h());
  }
  
  void commit(long paramLong)
  {
    writeMetadata(paramLong);
    this.file.getChannel().force(false);
    writeHeader(PREFIX_CLEAN, paramLong, this.metadata.h());
    this.file.getChannel().force(false);
    try
    {
      this.complete = true;
      Util.closeQuietly(this.upstream);
      this.upstream = null;
      return;
    }
    finally {}
  }
  
  boolean isClosed()
  {
    boolean bool;
    if (this.file == null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public f metadata()
  {
    return this.metadata;
  }
  
  public s newSource()
  {
    try
    {
      if (this.file == null) {
        return null;
      }
      this.sourceCount += 1;
      return new RelaySource();
    }
    finally {}
  }
  
  class RelaySource
    implements s
  {
    private FileOperator fileOperator = new FileOperator(Relay.this.file.getChannel());
    private long sourcePos;
    private final t timeout = new t();
    
    RelaySource() {}
    
    public void close()
    {
      if (this.fileOperator == null) {
        return;
      }
      RandomAccessFile localRandomAccessFile = null;
      this.fileOperator = null;
      synchronized (Relay.this)
      {
        Relay localRelay2 = Relay.this;
        localRelay2.sourceCount -= 1;
        if (Relay.this.sourceCount == 0)
        {
          localRandomAccessFile = Relay.this.file;
          Relay.this.file = null;
        }
        if (localRandomAccessFile != null) {
          Util.closeQuietly(localRandomAccessFile);
        }
        return;
      }
    }
    
    public long read(c arg1, long paramLong)
    {
      if (this.fileOperator != null) {
        synchronized (Relay.this)
        {
          for (;;)
          {
            l1 = this.sourcePos;
            l2 = Relay.this.upstreamPos;
            if (l1 != l2) {
              break label99;
            }
            if (Relay.this.complete) {
              return -1L;
            }
            if (Relay.this.upstreamReader == null) {
              break;
            }
            this.timeout.waitUntilNotified(Relay.this);
          }
          Relay.this.upstreamReader = Thread.currentThread();
          int i = 1;
          break label130;
          label99:
          l1 = l2 - Relay.this.buffer.a();
          if (this.sourcePos < l1)
          {
            i = 2;
            label130:
            if (i == 2)
            {
              paramLong = Math.min(paramLong, l2 - this.sourcePos);
              this.fileOperator.read(this.sourcePos + 32L, ???, paramLong);
              this.sourcePos += paramLong;
              return paramLong;
            }
            try
            {
              l1 = Relay.this.upstream.read(Relay.this.upstreamBuffer, Relay.this.bufferMaxSize);
              if (l1 == -1L)
              {
                Relay.this.commit(l2);
                synchronized (Relay.this)
                {
                  Relay.this.upstreamReader = null;
                  Relay.this.notifyAll();
                  return -1L;
                }
              }
              paramLong = Math.min(l1, paramLong);
              Relay.this.upstreamBuffer.a(???, 0L, paramLong);
              this.sourcePos += paramLong;
              this.fileOperator.write(l2 + 32L, Relay.this.upstreamBuffer.u(), l1);
              synchronized (Relay.this)
              {
                Relay.this.buffer.write(Relay.this.upstreamBuffer, l1);
                if (Relay.this.buffer.a() > Relay.this.bufferMaxSize) {
                  Relay.this.buffer.i(Relay.this.buffer.a() - Relay.this.bufferMaxSize);
                }
                ??? = Relay.this;
                ???.upstreamPos += l1;
                synchronized (Relay.this)
                {
                  Relay.this.upstreamReader = null;
                  Relay.this.notifyAll();
                  return paramLong;
                }
              }
              synchronized (Relay.this)
              {
                Relay.this.upstreamReader = null;
                Relay.this.notifyAll();
                throw ((Throwable)localObject3);
              }
            }
            finally {}
          }
        }
      }
    }
    
    public t timeout()
    {
      return this.timeout;
    }
  }
}


/* Location:              ~/okhttp3/internal/cache2/Relay.class
 *
 * Reversed by:           J
 */