package okhttp3.internal.ws;

import a.c;
import a.c.a;
import a.e;
import a.f;
import a.t;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;

final class WebSocketReader
{
  boolean closed;
  private final c controlFrameBuffer = new c();
  final FrameCallback frameCallback;
  long frameLength;
  final boolean isClient;
  boolean isControlFrame;
  boolean isFinalFrame;
  private final c.a maskCursor;
  private final byte[] maskKey;
  private final c messageFrameBuffer = new c();
  int opcode;
  final e source;
  
  WebSocketReader(boolean paramBoolean, e parame, FrameCallback paramFrameCallback)
  {
    if (parame != null)
    {
      if (paramFrameCallback != null)
      {
        this.isClient = paramBoolean;
        this.source = parame;
        this.frameCallback = paramFrameCallback;
        paramFrameCallback = null;
        if (paramBoolean) {
          parame = null;
        } else {
          parame = new byte[4];
        }
        this.maskKey = parame;
        if (paramBoolean) {
          parame = paramFrameCallback;
        } else {
          parame = new c.a();
        }
        this.maskCursor = parame;
        return;
      }
      throw new NullPointerException("frameCallback == null");
    }
    throw new NullPointerException("source == null");
  }
  
  private void readControlFrame()
  {
    long l = this.frameLength;
    if (l > 0L)
    {
      this.source.a(this.controlFrameBuffer, l);
      if (!this.isClient)
      {
        this.controlFrameBuffer.a(this.maskCursor);
        this.maskCursor.a(0L);
        WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
        this.maskCursor.close();
      }
    }
    Object localObject;
    switch (this.opcode)
    {
    default: 
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Unknown control opcode: ");
      ((StringBuilder)localObject).append(Integer.toHexString(this.opcode));
      throw new ProtocolException(((StringBuilder)localObject).toString());
    case 10: 
      this.frameCallback.onReadPong(this.controlFrameBuffer.p());
      break;
    case 9: 
      this.frameCallback.onReadPing(this.controlFrameBuffer.p());
      break;
    case 8: 
      int i = 1005;
      localObject = "";
      l = this.controlFrameBuffer.a();
      if (l == 1L) {
        break label265;
      }
      if (l != 0L)
      {
        i = this.controlFrameBuffer.i();
        localObject = this.controlFrameBuffer.q();
        String str = WebSocketProtocol.closeCodeExceptionMessage(i);
        if (str != null) {
          throw new ProtocolException(str);
        }
      }
      this.frameCallback.onReadClose(i, (String)localObject);
      this.closed = true;
    }
    return;
    label265:
    throw new ProtocolException("Malformed close payload length of 1.");
  }
  
  private void readHeader()
  {
    if (!this.closed)
    {
      long l = this.source.timeout().timeoutNanos();
      this.source.timeout().clearTimeout();
      try
      {
        int i = this.source.h();
        int j = i & 0xFF;
        this.source.timeout().timeout(l, TimeUnit.NANOSECONDS);
        this.opcode = (j & 0xF);
        boolean bool1 = true;
        boolean bool2;
        if ((j & 0x80) != 0) {
          bool2 = true;
        } else {
          bool2 = false;
        }
        this.isFinalFrame = bool2;
        if ((j & 0x8) != 0) {
          bool2 = true;
        } else {
          bool2 = false;
        }
        this.isControlFrame = bool2;
        if ((this.isControlFrame) && (!this.isFinalFrame)) {
          throw new ProtocolException("Control frames must be final.");
        }
        if ((j & 0x40) != 0) {
          i = 1;
        } else {
          i = 0;
        }
        int k;
        if ((j & 0x20) != 0) {
          k = 1;
        } else {
          k = 0;
        }
        if ((j & 0x10) != 0) {
          j = 1;
        } else {
          j = 0;
        }
        if ((i == 0) && (k == 0) && (j == 0))
        {
          i = this.source.h() & 0xFF;
          if ((i & 0x80) != 0) {
            bool2 = bool1;
          } else {
            bool2 = false;
          }
          bool1 = this.isClient;
          Object localObject1;
          if (bool2 == bool1)
          {
            if (bool1) {
              localObject1 = "Server-sent frames must not be masked.";
            } else {
              localObject1 = "Client-sent frames must be masked.";
            }
            throw new ProtocolException((String)localObject1);
          }
          this.frameLength = (i & 0x7F);
          l = this.frameLength;
          if (l == 126L)
          {
            this.frameLength = (this.source.i() & 0xFFFF);
          }
          else if (l == 127L)
          {
            this.frameLength = this.source.k();
            if (this.frameLength < 0L)
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("Frame length 0x");
              ((StringBuilder)localObject1).append(Long.toHexString(this.frameLength));
              ((StringBuilder)localObject1).append(" > 0x7FFFFFFFFFFFFFFF");
              throw new ProtocolException(((StringBuilder)localObject1).toString());
            }
          }
          if ((this.isControlFrame) && (this.frameLength > 125L)) {
            throw new ProtocolException("Control frame must be less than 125B.");
          }
          if (bool2) {
            this.source.a(this.maskKey);
          }
          return;
        }
        throw new ProtocolException("Reserved flags are unsupported.");
      }
      finally
      {
        this.source.timeout().timeout(l, TimeUnit.NANOSECONDS);
      }
    }
    throw new IOException("closed");
  }
  
  private void readMessage()
  {
    while (!this.closed)
    {
      long l = this.frameLength;
      if (l > 0L)
      {
        this.source.a(this.messageFrameBuffer, l);
        if (!this.isClient)
        {
          this.messageFrameBuffer.a(this.maskCursor);
          this.maskCursor.a(this.messageFrameBuffer.a() - this.frameLength);
          WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
          this.maskCursor.close();
        }
      }
      if (this.isFinalFrame) {
        return;
      }
      readUntilNonControlFrame();
      if (this.opcode != 0)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Expected continuation opcode. Got: ");
        localStringBuilder.append(Integer.toHexString(this.opcode));
        throw new ProtocolException(localStringBuilder.toString());
      }
    }
    throw new IOException("closed");
  }
  
  private void readMessageFrame()
  {
    int i = this.opcode;
    if ((i != 1) && (i != 2))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unknown opcode: ");
      localStringBuilder.append(Integer.toHexString(i));
      throw new ProtocolException(localStringBuilder.toString());
    }
    readMessage();
    if (i == 1) {
      this.frameCallback.onReadMessage(this.messageFrameBuffer.q());
    } else {
      this.frameCallback.onReadMessage(this.messageFrameBuffer.p());
    }
  }
  
  private void readUntilNonControlFrame()
  {
    while (!this.closed)
    {
      readHeader();
      if (!this.isControlFrame) {
        break;
      }
      readControlFrame();
    }
  }
  
  void processNextFrame()
  {
    readHeader();
    if (this.isControlFrame) {
      readControlFrame();
    } else {
      readMessageFrame();
    }
  }
  
  public static abstract interface FrameCallback
  {
    public abstract void onReadClose(int paramInt, String paramString);
    
    public abstract void onReadMessage(f paramf);
    
    public abstract void onReadMessage(String paramString);
    
    public abstract void onReadPing(f paramf);
    
    public abstract void onReadPong(f paramf);
  }
}


/* Location:              ~/okhttp3/internal/ws/WebSocketReader.class
 *
 * Reversed by:           J
 */