package okhttp3.internal.http2;

import a.c;
import a.e;
import a.f;
import a.s;
import a.t;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.internal.Util;

final class Http2Reader
  implements Closeable
{
  static final Logger logger = Logger.getLogger(Http2.class.getName());
  private final boolean client;
  private final ContinuationSource continuation;
  final Hpack.Reader hpackReader;
  private final e source;
  
  Http2Reader(e parame, boolean paramBoolean)
  {
    this.source = parame;
    this.client = paramBoolean;
    this.continuation = new ContinuationSource(this.source);
    this.hpackReader = new Hpack.Reader(4096, this.continuation);
  }
  
  static int lengthWithoutPadding(int paramInt, byte paramByte, short paramShort)
  {
    short s = paramInt;
    if ((paramByte & 0x8) != 0) {
      s = paramInt - 1;
    }
    if (paramShort <= s) {
      return (short)(s - paramShort);
    }
    throw Http2.ioException("PROTOCOL_ERROR padding %s > remaining length %s", new Object[] { Short.valueOf(paramShort), Integer.valueOf(s) });
  }
  
  private void readData(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
  {
    short s1 = 0;
    if (paramInt2 != 0)
    {
      short s2 = 1;
      boolean bool;
      if ((paramByte & 0x1) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      if ((paramByte & 0x20) == 0) {
        s2 = 0;
      }
      if (s2 == 0)
      {
        short s3 = s1;
        if ((paramByte & 0x8) != 0)
        {
          s2 = (short)(this.source.h() & 0xFF);
          s3 = s2;
        }
        paramInt1 = lengthWithoutPadding(paramInt1, paramByte, s3);
        paramHandler.data(bool, paramInt2, this.source, paramInt1);
        this.source.i(s3);
        return;
      }
      throw Http2.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
    }
    throw Http2.ioException("PROTOCOL_ERROR: TYPE_DATA streamId == 0", new Object[0]);
  }
  
  private void readGoAway(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
  {
    if (paramInt1 >= 8)
    {
      if (paramInt2 == 0)
      {
        paramByte = this.source.j();
        paramInt2 = this.source.j();
        paramInt1 -= 8;
        ErrorCode localErrorCode = ErrorCode.fromHttp2(paramInt2);
        if (localErrorCode != null)
        {
          f localf = f.b;
          if (paramInt1 > 0) {
            localf = this.source.d(paramInt1);
          }
          paramHandler.goAway(paramByte, localErrorCode, localf);
          return;
        }
        throw Http2.ioException("TYPE_GOAWAY unexpected error code: %d", new Object[] { Integer.valueOf(paramInt2) });
      }
      throw Http2.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
    }
    throw Http2.ioException("TYPE_GOAWAY length < 8: %s", new Object[] { Integer.valueOf(paramInt1) });
  }
  
  private List<Header> readHeaderBlock(int paramInt1, short paramShort, byte paramByte, int paramInt2)
  {
    ContinuationSource localContinuationSource = this.continuation;
    localContinuationSource.left = paramInt1;
    localContinuationSource.length = paramInt1;
    localContinuationSource.padding = ((short)paramShort);
    localContinuationSource.flags = ((byte)paramByte);
    localContinuationSource.streamId = paramInt2;
    this.hpackReader.readHeaders();
    return this.hpackReader.getAndResetHeaderList();
  }
  
  private void readHeaders(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
  {
    int i = 0;
    if (paramInt2 != 0)
    {
      boolean bool;
      if ((paramByte & 0x1) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      int j = i;
      if ((paramByte & 0x8) != 0)
      {
        i = (short)(this.source.h() & 0xFF);
        j = i;
      }
      i = paramInt1;
      if ((paramByte & 0x20) != 0)
      {
        readPriority(paramHandler, paramInt2);
        i = paramInt1 - 5;
      }
      paramHandler.headers(bool, paramInt2, -1, readHeaderBlock(lengthWithoutPadding(i, paramByte, j), j, paramByte, paramInt2));
      return;
    }
    throw Http2.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
  }
  
  static int readMedium(e parame)
  {
    int i = parame.h();
    int j = parame.h();
    return parame.h() & 0xFF | (i & 0xFF) << 16 | (j & 0xFF) << 8;
  }
  
  private void readPing(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
  {
    boolean bool = false;
    if (paramInt1 == 8)
    {
      if (paramInt2 == 0)
      {
        paramInt2 = this.source.j();
        paramInt1 = this.source.j();
        if ((paramByte & 0x1) != 0) {
          bool = true;
        }
        paramHandler.ping(bool, paramInt2, paramInt1);
        return;
      }
      throw Http2.ioException("TYPE_PING streamId != 0", new Object[0]);
    }
    throw Http2.ioException("TYPE_PING length != 8: %s", new Object[] { Integer.valueOf(paramInt1) });
  }
  
  private void readPriority(Handler paramHandler, int paramInt)
  {
    int i = this.source.j();
    boolean bool;
    if ((0x80000000 & i) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    paramHandler.priority(paramInt, i & 0x7FFFFFFF, (this.source.h() & 0xFF) + 1, bool);
  }
  
  private void readPriority(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
  {
    if (paramInt1 == 5)
    {
      if (paramInt2 != 0)
      {
        readPriority(paramHandler, paramInt2);
        return;
      }
      throw Http2.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
    }
    throw Http2.ioException("TYPE_PRIORITY length: %d != 5", new Object[] { Integer.valueOf(paramInt1) });
  }
  
  private void readPushPromise(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
  {
    short s1 = 0;
    if (paramInt2 != 0)
    {
      short s2 = s1;
      if ((paramByte & 0x8) != 0)
      {
        s1 = (short)(this.source.h() & 0xFF);
        s2 = s1;
      }
      paramHandler.pushPromise(paramInt2, this.source.j() & 0x7FFFFFFF, readHeaderBlock(lengthWithoutPadding(paramInt1 - 4, paramByte, s2), s2, paramByte, paramInt2));
      return;
    }
    throw Http2.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
  }
  
  private void readRstStream(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
  {
    if (paramInt1 == 4)
    {
      if (paramInt2 != 0)
      {
        paramInt1 = this.source.j();
        ErrorCode localErrorCode = ErrorCode.fromHttp2(paramInt1);
        if (localErrorCode != null)
        {
          paramHandler.rstStream(paramInt2, localErrorCode);
          return;
        }
        throw Http2.ioException("TYPE_RST_STREAM unexpected error code: %d", new Object[] { Integer.valueOf(paramInt1) });
      }
      throw Http2.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
    }
    throw Http2.ioException("TYPE_RST_STREAM length: %d != 4", new Object[] { Integer.valueOf(paramInt1) });
  }
  
  private void readSettings(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
  {
    if (paramInt2 == 0)
    {
      if ((paramByte & 0x1) != 0)
      {
        if (paramInt1 == 0)
        {
          paramHandler.ackSettings();
          return;
        }
        throw Http2.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
      }
      if (paramInt1 % 6 == 0)
      {
        Settings localSettings = new Settings();
        for (paramInt2 = 0; paramInt2 < paramInt1; paramInt2 += 6)
        {
          byte b = this.source.i() & 0xFFFF;
          int i = this.source.j();
          paramByte = b;
          switch (b)
          {
          default: 
            paramByte = b;
            break;
          case 5: 
            if ((i >= 16384) && (i <= 16777215)) {
              paramByte = b;
            } else {
              throw Http2.ioException("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", new Object[] { Integer.valueOf(i) });
            }
            break;
          case 4: 
            paramByte = 7;
            if (i < 0) {
              throw Http2.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
            }
            break;
          case 3: 
            paramByte = 4;
            break;
          case 2: 
            paramByte = b;
            if (i != 0) {
              if (i == 1) {
                paramByte = b;
              } else {
                throw Http2.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
              }
            }
            break;
          }
          localSettings.set(paramByte, i);
        }
        paramHandler.settings(false, localSettings);
        return;
      }
      throw Http2.ioException("TYPE_SETTINGS length %% 6 != 0: %s", new Object[] { Integer.valueOf(paramInt1) });
    }
    throw Http2.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
  }
  
  private void readWindowUpdate(Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
  {
    if (paramInt1 == 4)
    {
      long l = this.source.j() & 0x7FFFFFFF;
      if (l != 0L)
      {
        paramHandler.windowUpdate(paramInt2, l);
        return;
      }
      throw Http2.ioException("windowSizeIncrement was 0", new Object[] { Long.valueOf(l) });
    }
    throw Http2.ioException("TYPE_WINDOW_UPDATE length !=4: %s", new Object[] { Integer.valueOf(paramInt1) });
  }
  
  public void close()
  {
    this.source.close();
  }
  
  public boolean nextFrame(boolean paramBoolean, Handler paramHandler)
  {
    try
    {
      this.source.a(9L);
      int i = readMedium(this.source);
      if ((i >= 0) && (i <= 16384))
      {
        byte b1 = (byte)(this.source.h() & 0xFF);
        if ((paramBoolean) && (b1 != 4)) {
          throw Http2.ioException("Expected a SETTINGS frame but was %s", new Object[] { Byte.valueOf(b1) });
        }
        byte b2 = (byte)(this.source.h() & 0xFF);
        int j = this.source.j() & 0x7FFFFFFF;
        if (logger.isLoggable(Level.FINE)) {
          logger.fine(Http2.frameLog(true, j, i, b1, b2));
        }
        switch (b1)
        {
        default: 
          this.source.i(i);
          break;
        case 8: 
          readWindowUpdate(paramHandler, i, b2, j);
          break;
        case 7: 
          readGoAway(paramHandler, i, b2, j);
          break;
        case 6: 
          readPing(paramHandler, i, b2, j);
          break;
        case 5: 
          readPushPromise(paramHandler, i, b2, j);
          break;
        case 4: 
          readSettings(paramHandler, i, b2, j);
          break;
        case 3: 
          readRstStream(paramHandler, i, b2, j);
          break;
        case 2: 
          readPriority(paramHandler, i, b2, j);
          break;
        case 1: 
          readHeaders(paramHandler, i, b2, j);
          break;
        case 0: 
          readData(paramHandler, i, b2, j);
        }
        return true;
      }
      throw Http2.ioException("FRAME_SIZE_ERROR: %s", new Object[] { Integer.valueOf(i) });
    }
    catch (IOException paramHandler) {}
    return false;
  }
  
  public void readConnectionPreface(Handler paramHandler)
  {
    if (this.client)
    {
      if (!nextFrame(true, paramHandler)) {
        throw Http2.ioException("Required SETTINGS preface not received", new Object[0]);
      }
    }
    else
    {
      paramHandler = this.source.d(Http2.CONNECTION_PREFACE.h());
      if (logger.isLoggable(Level.FINE)) {
        logger.fine(Util.format("<< CONNECTION %s", new Object[] { paramHandler.f() }));
      }
      if (!Http2.CONNECTION_PREFACE.equals(paramHandler)) {
        break label93;
      }
    }
    return;
    label93:
    throw Http2.ioException("Expected a connection header but was %s", new Object[] { paramHandler.a() });
  }
  
  static final class ContinuationSource
    implements s
  {
    byte flags;
    int left;
    int length;
    short padding;
    private final e source;
    int streamId;
    
    ContinuationSource(e parame)
    {
      this.source = parame;
    }
    
    private void readContinuationHeader()
    {
      int i = this.streamId;
      int j = Http2Reader.readMedium(this.source);
      this.left = j;
      this.length = j;
      byte b = (byte)(this.source.h() & 0xFF);
      this.flags = ((byte)(byte)(this.source.h() & 0xFF));
      if (Http2Reader.logger.isLoggable(Level.FINE)) {
        Http2Reader.logger.fine(Http2.frameLog(true, this.streamId, this.length, b, this.flags));
      }
      this.streamId = (this.source.j() & 0x7FFFFFFF);
      if (b == 9)
      {
        if (this.streamId == i) {
          return;
        }
        throw Http2.ioException("TYPE_CONTINUATION streamId changed", new Object[0]);
      }
      throw Http2.ioException("%s != TYPE_CONTINUATION", new Object[] { Byte.valueOf(b) });
    }
    
    public void close() {}
    
    public long read(c paramc, long paramLong)
    {
      int i;
      for (;;)
      {
        i = this.left;
        if (i != 0) {
          break;
        }
        this.source.i(this.padding);
        this.padding = ((short)0);
        if ((this.flags & 0x4) != 0) {
          return -1L;
        }
        readContinuationHeader();
      }
      paramLong = this.source.read(paramc, Math.min(paramLong, i));
      if (paramLong == -1L) {
        return -1L;
      }
      this.left = ((int)(this.left - paramLong));
      return paramLong;
    }
    
    public t timeout()
    {
      return this.source.timeout();
    }
  }
  
  static abstract interface Handler
  {
    public abstract void ackSettings();
    
    public abstract void alternateService(int paramInt1, String paramString1, f paramf, String paramString2, int paramInt2, long paramLong);
    
    public abstract void data(boolean paramBoolean, int paramInt1, e parame, int paramInt2);
    
    public abstract void goAway(int paramInt, ErrorCode paramErrorCode, f paramf);
    
    public abstract void headers(boolean paramBoolean, int paramInt1, int paramInt2, List<Header> paramList);
    
    public abstract void ping(boolean paramBoolean, int paramInt1, int paramInt2);
    
    public abstract void priority(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);
    
    public abstract void pushPromise(int paramInt1, int paramInt2, List<Header> paramList);
    
    public abstract void rstStream(int paramInt, ErrorCode paramErrorCode);
    
    public abstract void settings(boolean paramBoolean, Settings paramSettings);
    
    public abstract void windowUpdate(int paramInt, long paramLong);
  }
}


/* Location:              ~/okhttp3/internal/http2/Http2Reader.class
 *
 * Reversed by:           J
 */