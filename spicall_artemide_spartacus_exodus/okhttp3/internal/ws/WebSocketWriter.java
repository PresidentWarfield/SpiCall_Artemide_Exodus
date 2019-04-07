package okhttp3.internal.ws;

import a.c;
import a.c.a;
import a.d;
import a.f;
import a.r;
import a.t;
import java.io.IOException;
import java.util.Random;

final class WebSocketWriter
{
  boolean activeWriter;
  final c buffer = new c();
  final FrameSink frameSink = new FrameSink();
  final boolean isClient;
  private final c.a maskCursor;
  private final byte[] maskKey;
  final Random random;
  final d sink;
  final c sinkBuffer;
  boolean writerClosed;
  
  WebSocketWriter(boolean paramBoolean, d paramd, Random paramRandom)
  {
    if (paramd != null)
    {
      if (paramRandom != null)
      {
        this.isClient = paramBoolean;
        this.sink = paramd;
        this.sinkBuffer = paramd.b();
        this.random = paramRandom;
        paramRandom = null;
        if (paramBoolean) {
          paramd = new byte[4];
        } else {
          paramd = null;
        }
        this.maskKey = paramd;
        paramd = paramRandom;
        if (paramBoolean) {
          paramd = new c.a();
        }
        this.maskCursor = paramd;
        return;
      }
      throw new NullPointerException("random == null");
    }
    throw new NullPointerException("sink == null");
  }
  
  private void writeControlFrame(int paramInt, f paramf)
  {
    if (!this.writerClosed)
    {
      int i = paramf.h();
      if (i <= 125L)
      {
        this.sinkBuffer.b(paramInt | 0x80);
        if (this.isClient)
        {
          this.sinkBuffer.b(i | 0x80);
          this.random.nextBytes(this.maskKey);
          this.sinkBuffer.b(this.maskKey);
          if (i > 0)
          {
            long l = this.sinkBuffer.a();
            this.sinkBuffer.a(paramf);
            this.sinkBuffer.a(this.maskCursor);
            this.maskCursor.a(l);
            WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
            this.maskCursor.close();
          }
        }
        else
        {
          this.sinkBuffer.b(i);
          this.sinkBuffer.a(paramf);
        }
        this.sink.flush();
        return;
      }
      throw new IllegalArgumentException("Payload size must be less than or equal to 125");
    }
    throw new IOException("closed");
  }
  
  r newMessageSink(int paramInt, long paramLong)
  {
    if (!this.activeWriter)
    {
      this.activeWriter = true;
      FrameSink localFrameSink = this.frameSink;
      localFrameSink.formatOpcode = paramInt;
      localFrameSink.contentLength = paramLong;
      localFrameSink.isFirstFrame = true;
      localFrameSink.closed = false;
      return localFrameSink;
    }
    throw new IllegalStateException("Another message writer is active. Did you call close()?");
  }
  
  void writeClose(int paramInt, f paramf)
  {
    Object localObject = f.b;
    if ((paramInt != 0) || (paramf != null))
    {
      if (paramInt != 0) {
        WebSocketProtocol.validateCloseCode(paramInt);
      }
      localObject = new c();
      ((c)localObject).c(paramInt);
      if (paramf != null) {
        ((c)localObject).a(paramf);
      }
      localObject = ((c)localObject).p();
    }
    try
    {
      writeControlFrame(8, (f)localObject);
      return;
    }
    finally
    {
      this.writerClosed = true;
    }
  }
  
  void writeMessageFrame(int paramInt, long paramLong, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (!this.writerClosed)
    {
      int i = 0;
      if (!paramBoolean1) {
        paramInt = 0;
      }
      int j = paramInt;
      if (paramBoolean2) {
        j = paramInt | 0x80;
      }
      this.sinkBuffer.b(j);
      paramInt = i;
      if (this.isClient) {
        paramInt = 128;
      }
      if (paramLong <= 125L)
      {
        j = (int)paramLong;
        this.sinkBuffer.b(j | paramInt);
      }
      else if (paramLong <= 65535L)
      {
        this.sinkBuffer.b(paramInt | 0x7E);
        this.sinkBuffer.c((int)paramLong);
      }
      else
      {
        this.sinkBuffer.b(paramInt | 0x7F);
        this.sinkBuffer.j(paramLong);
      }
      if (this.isClient)
      {
        this.random.nextBytes(this.maskKey);
        this.sinkBuffer.b(this.maskKey);
        if (paramLong > 0L)
        {
          long l = this.sinkBuffer.a();
          this.sinkBuffer.write(this.buffer, paramLong);
          this.sinkBuffer.a(this.maskCursor);
          this.maskCursor.a(l);
          WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
          this.maskCursor.close();
        }
      }
      else
      {
        this.sinkBuffer.write(this.buffer, paramLong);
      }
      this.sink.d();
      return;
    }
    throw new IOException("closed");
  }
  
  void writePing(f paramf)
  {
    writeControlFrame(9, paramf);
  }
  
  void writePong(f paramf)
  {
    writeControlFrame(10, paramf);
  }
  
  final class FrameSink
    implements r
  {
    boolean closed;
    long contentLength;
    int formatOpcode;
    boolean isFirstFrame;
    
    FrameSink() {}
    
    public void close()
    {
      if (!this.closed)
      {
        WebSocketWriter localWebSocketWriter = WebSocketWriter.this;
        localWebSocketWriter.writeMessageFrame(this.formatOpcode, localWebSocketWriter.buffer.a(), this.isFirstFrame, true);
        this.closed = true;
        WebSocketWriter.this.activeWriter = false;
        return;
      }
      throw new IOException("closed");
    }
    
    public void flush()
    {
      if (!this.closed)
      {
        WebSocketWriter localWebSocketWriter = WebSocketWriter.this;
        localWebSocketWriter.writeMessageFrame(this.formatOpcode, localWebSocketWriter.buffer.a(), this.isFirstFrame, false);
        this.isFirstFrame = false;
        return;
      }
      throw new IOException("closed");
    }
    
    public t timeout()
    {
      return WebSocketWriter.this.sink.timeout();
    }
    
    public void write(c paramc, long paramLong)
    {
      if (!this.closed)
      {
        WebSocketWriter.this.buffer.write(paramc, paramLong);
        int i;
        if ((this.isFirstFrame) && (this.contentLength != -1L) && (WebSocketWriter.this.buffer.a() > this.contentLength - 8192L)) {
          i = 1;
        } else {
          i = 0;
        }
        paramLong = WebSocketWriter.this.buffer.g();
        if ((paramLong > 0L) && (i == 0))
        {
          WebSocketWriter.this.writeMessageFrame(this.formatOpcode, paramLong, this.isFirstFrame, false);
          this.isFirstFrame = false;
        }
        return;
      }
      throw new IOException("closed");
    }
  }
}


/* Location:              ~/okhttp3/internal/ws/WebSocketWriter.class
 *
 * Reversed by:           J
 */