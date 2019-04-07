package okhttp3.internal.http2;

import a.c;
import a.d;
import a.f;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.internal.Util;

final class Http2Writer
  implements Closeable
{
  private static final Logger logger = Logger.getLogger(Http2.class.getName());
  private final boolean client;
  private boolean closed;
  private final c hpackBuffer;
  final Hpack.Writer hpackWriter;
  private int maxFrameSize;
  private final d sink;
  
  Http2Writer(d paramd, boolean paramBoolean)
  {
    this.sink = paramd;
    this.client = paramBoolean;
    this.hpackBuffer = new c();
    this.hpackWriter = new Hpack.Writer(this.hpackBuffer);
    this.maxFrameSize = 16384;
  }
  
  private void writeContinuationFrames(int paramInt, long paramLong)
  {
    while (paramLong > 0L)
    {
      int i = (int)Math.min(this.maxFrameSize, paramLong);
      long l = i;
      paramLong -= l;
      byte b1;
      byte b2;
      if (paramLong == 0L)
      {
        b1 = 4;
        b2 = b1;
      }
      else
      {
        b1 = 0;
        b2 = b1;
      }
      frameHeader(paramInt, i, (byte)9, b2);
      this.sink.write(this.hpackBuffer, l);
    }
  }
  
  private static void writeMedium(d paramd, int paramInt)
  {
    paramd.i(paramInt >>> 16 & 0xFF);
    paramd.i(paramInt >>> 8 & 0xFF);
    paramd.i(paramInt & 0xFF);
  }
  
  public void applyAndAckSettings(Settings paramSettings)
  {
    try
    {
      if (!this.closed)
      {
        this.maxFrameSize = paramSettings.getMaxFrameSize(this.maxFrameSize);
        if (paramSettings.getHeaderTableSize() != -1) {
          this.hpackWriter.setHeaderTableSizeSetting(paramSettings.getHeaderTableSize());
        }
        frameHeader(0, 0, (byte)4, (byte)1);
        this.sink.flush();
        return;
      }
      paramSettings = new java/io/IOException;
      paramSettings.<init>("closed");
      throw paramSettings;
    }
    finally {}
  }
  
  public void close()
  {
    try
    {
      this.closed = true;
      this.sink.close();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void connectionPreface()
  {
    try
    {
      if (!this.closed)
      {
        boolean bool = this.client;
        if (!bool) {
          return;
        }
        if (logger.isLoggable(Level.FINE)) {
          logger.fine(Util.format(">> CONNECTION %s", new Object[] { Http2.CONNECTION_PREFACE.f() }));
        }
        this.sink.c(Http2.CONNECTION_PREFACE.i());
        this.sink.flush();
        return;
      }
      IOException localIOException = new java/io/IOException;
      localIOException.<init>("closed");
      throw localIOException;
    }
    finally {}
  }
  
  public void data(boolean paramBoolean, int paramInt1, c paramc, int paramInt2)
  {
    try
    {
      if (!this.closed)
      {
        byte b1 = 0;
        byte b2 = b1;
        if (paramBoolean)
        {
          b1 = (byte)1;
          b2 = b1;
        }
        dataFrame(paramInt1, b2, paramc, paramInt2);
        return;
      }
      paramc = new java/io/IOException;
      paramc.<init>("closed");
      throw paramc;
    }
    finally {}
  }
  
  void dataFrame(int paramInt1, byte paramByte, c paramc, int paramInt2)
  {
    frameHeader(paramInt1, paramInt2, (byte)0, paramByte);
    if (paramInt2 > 0) {
      this.sink.write(paramc, paramInt2);
    }
  }
  
  public void flush()
  {
    try
    {
      if (!this.closed)
      {
        this.sink.flush();
        return;
      }
      IOException localIOException = new java/io/IOException;
      localIOException.<init>("closed");
      throw localIOException;
    }
    finally {}
  }
  
  public void frameHeader(int paramInt1, int paramInt2, byte paramByte1, byte paramByte2)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.fine(Http2.frameLog(false, paramInt1, paramInt2, paramByte1, paramByte2));
    }
    int i = this.maxFrameSize;
    if (paramInt2 <= i)
    {
      if ((0x80000000 & paramInt1) == 0)
      {
        writeMedium(this.sink, paramInt2);
        this.sink.i(paramByte1 & 0xFF);
        this.sink.i(paramByte2 & 0xFF);
        this.sink.g(paramInt1 & 0x7FFFFFFF);
        return;
      }
      throw Http2.illegalArgument("reserved bit set: %s", new Object[] { Integer.valueOf(paramInt1) });
    }
    throw Http2.illegalArgument("FRAME_SIZE_ERROR length > %d: %d", new Object[] { Integer.valueOf(i), Integer.valueOf(paramInt2) });
  }
  
  public void goAway(int paramInt, ErrorCode paramErrorCode, byte[] paramArrayOfByte)
  {
    try
    {
      if (!this.closed)
      {
        if (paramErrorCode.httpCode != -1)
        {
          frameHeader(0, paramArrayOfByte.length + 8, (byte)7, (byte)0);
          this.sink.g(paramInt);
          this.sink.g(paramErrorCode.httpCode);
          if (paramArrayOfByte.length > 0) {
            this.sink.c(paramArrayOfByte);
          }
          this.sink.flush();
          return;
        }
        throw Http2.illegalArgument("errorCode.httpCode == -1", new Object[0]);
      }
      paramErrorCode = new java/io/IOException;
      paramErrorCode.<init>("closed");
      throw paramErrorCode;
    }
    finally {}
  }
  
  public void headers(int paramInt, List<Header> paramList)
  {
    try
    {
      if (!this.closed)
      {
        headers(false, paramInt, paramList);
        return;
      }
      paramList = new java/io/IOException;
      paramList.<init>("closed");
      throw paramList;
    }
    finally {}
  }
  
  void headers(boolean paramBoolean, int paramInt, List<Header> paramList)
  {
    if (!this.closed)
    {
      this.hpackWriter.writeHeaders(paramList);
      long l1 = this.hpackBuffer.a();
      int i = (int)Math.min(this.maxFrameSize, l1);
      long l2 = i;
      byte b1;
      if (l1 == l2) {
        b1 = 4;
      } else {
        b1 = 0;
      }
      byte b2 = b1;
      if (paramBoolean)
      {
        b1 = (byte)(b1 | 0x1);
        b2 = b1;
      }
      frameHeader(paramInt, i, (byte)1, b2);
      this.sink.write(this.hpackBuffer, l2);
      if (l1 > l2) {
        writeContinuationFrames(paramInt, l1 - l2);
      }
      return;
    }
    throw new IOException("closed");
  }
  
  public int maxDataLength()
  {
    return this.maxFrameSize;
  }
  
  public void ping(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    try
    {
      if (!this.closed)
      {
        byte b1;
        byte b2;
        if (paramBoolean)
        {
          b1 = 1;
          b2 = b1;
        }
        else
        {
          b1 = 0;
          b2 = b1;
        }
        frameHeader(0, 8, (byte)6, b2);
        this.sink.g(paramInt1);
        this.sink.g(paramInt2);
        this.sink.flush();
        return;
      }
      IOException localIOException = new java/io/IOException;
      localIOException.<init>("closed");
      throw localIOException;
    }
    finally {}
  }
  
  public void pushPromise(int paramInt1, int paramInt2, List<Header> paramList)
  {
    try
    {
      if (!this.closed)
      {
        this.hpackWriter.writeHeaders(paramList);
        long l1 = this.hpackBuffer.a();
        int i = (int)Math.min(this.maxFrameSize - 4, l1);
        long l2 = i;
        byte b1;
        byte b2;
        if (l1 == l2)
        {
          b1 = 4;
          b2 = b1;
        }
        else
        {
          b1 = 0;
          b2 = b1;
        }
        frameHeader(paramInt1, i + 4, (byte)5, b2);
        this.sink.g(paramInt2 & 0x7FFFFFFF);
        this.sink.write(this.hpackBuffer, l2);
        if (l1 > l2) {
          writeContinuationFrames(paramInt1, l1 - l2);
        }
        return;
      }
      paramList = new java/io/IOException;
      paramList.<init>("closed");
      throw paramList;
    }
    finally {}
  }
  
  public void rstStream(int paramInt, ErrorCode paramErrorCode)
  {
    try
    {
      if (!this.closed)
      {
        if (paramErrorCode.httpCode != -1)
        {
          frameHeader(paramInt, 4, (byte)3, (byte)0);
          this.sink.g(paramErrorCode.httpCode);
          this.sink.flush();
          return;
        }
        paramErrorCode = new java/lang/IllegalArgumentException;
        paramErrorCode.<init>();
        throw paramErrorCode;
      }
      paramErrorCode = new java/io/IOException;
      paramErrorCode.<init>("closed");
      throw paramErrorCode;
    }
    finally {}
  }
  
  public void settings(Settings paramSettings)
  {
    try
    {
      if (!this.closed)
      {
        int i = paramSettings.size();
        int j = 0;
        frameHeader(0, i * 6, (byte)4, (byte)0);
        while (j < 10)
        {
          if (paramSettings.isSet(j))
          {
            if (j == 4) {
              i = 3;
            } else if (j == 7) {
              i = 4;
            } else {
              i = j;
            }
            this.sink.h(i);
            this.sink.g(paramSettings.get(j));
          }
          j++;
        }
        this.sink.flush();
        return;
      }
      paramSettings = new java/io/IOException;
      paramSettings.<init>("closed");
      throw paramSettings;
    }
    finally {}
  }
  
  public void synReply(boolean paramBoolean, int paramInt, List<Header> paramList)
  {
    try
    {
      if (!this.closed)
      {
        headers(paramBoolean, paramInt, paramList);
        return;
      }
      paramList = new java/io/IOException;
      paramList.<init>("closed");
      throw paramList;
    }
    finally {}
  }
  
  public void synStream(boolean paramBoolean, int paramInt1, int paramInt2, List<Header> paramList)
  {
    try
    {
      if (!this.closed)
      {
        headers(paramBoolean, paramInt1, paramList);
        return;
      }
      paramList = new java/io/IOException;
      paramList.<init>("closed");
      throw paramList;
    }
    finally {}
  }
  
  public void windowUpdate(int paramInt, long paramLong)
  {
    try
    {
      if (!this.closed)
      {
        if ((paramLong != 0L) && (paramLong <= 2147483647L))
        {
          frameHeader(paramInt, 4, (byte)8, (byte)0);
          this.sink.g((int)paramLong);
          this.sink.flush();
          return;
        }
        throw Http2.illegalArgument("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", new Object[] { Long.valueOf(paramLong) });
      }
      IOException localIOException = new java/io/IOException;
      localIOException.<init>("closed");
      throw localIOException;
    }
    finally {}
  }
}


/* Location:              ~/okhttp3/internal/http2/Http2Writer.class
 *
 * Reversed by:           J
 */