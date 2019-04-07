package com.app.system.streaming.f;

import android.annotation.SuppressLint;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import com.security.d.a;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@SuppressLint({"NewApi"})
public class g
  extends InputStream
{
  public final String a = "MediaCodecInputStream";
  public MediaFormat b;
  private MediaCodec c = null;
  private MediaCodec.BufferInfo d = new MediaCodec.BufferInfo();
  private ByteBuffer[] e = null;
  private ByteBuffer f = null;
  private int g = -1;
  private boolean h = false;
  
  public g(MediaCodec paramMediaCodec)
  {
    this.c = paramMediaCodec;
    this.e = this.c.getOutputBuffers();
  }
  
  public MediaCodec.BufferInfo a()
  {
    return this.d;
  }
  
  public int available()
  {
    if (this.f != null) {
      return this.d.size - this.f.position();
    }
    return 0;
  }
  
  public void close()
  {
    this.h = true;
  }
  
  public int read()
  {
    return 0;
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      if (this.f == null) {
        while ((!Thread.interrupted()) && (!this.h))
        {
          this.g = this.c.dequeueOutputBuffer(this.d, 500000L);
          if (this.g >= 0)
          {
            this.f = this.e[this.g];
            this.f.position(0);
            break;
          }
          if (this.g == -3)
          {
            this.e = this.c.getOutputBuffers();
          }
          else if (this.g == -2)
          {
            this.b = this.c.getOutputFormat();
            a.c("MediaCodecInputStream", this.b.toString(), new Object[0]);
          }
          else if (this.g == -1)
          {
            a.e("MediaCodecInputStream", "No buffer available...", new Object[0]);
          }
          else
          {
            StringBuilder localStringBuilder = new java/lang/StringBuilder;
            localStringBuilder.<init>();
            localStringBuilder.append("Message: ");
            localStringBuilder.append(this.g);
            a.a("MediaCodecInputStream", localStringBuilder.toString(), new Object[0]);
          }
        }
      }
      if (!this.h)
      {
        if (paramInt2 >= this.d.size - this.f.position())
        {
          int i = this.d.size;
          paramInt2 = this.f.position();
          paramInt2 = i - paramInt2;
        }
        try
        {
          this.f.get(paramArrayOfByte, paramInt1, paramInt2);
          paramInt1 = paramInt2;
          if (this.f.position() < this.d.size) {
            return paramInt1;
          }
          this.c.releaseOutputBuffer(this.g, false);
          this.f = null;
          paramInt1 = paramInt2;
        }
        catch (RuntimeException paramArrayOfByte)
        {
          break label321;
        }
      }
      else
      {
        paramArrayOfByte = new java/io/IOException;
        paramArrayOfByte.<init>("This InputStream was closed");
        throw paramArrayOfByte;
      }
    }
    catch (RuntimeException paramArrayOfByte)
    {
      paramInt2 = 0;
      label321:
      paramArrayOfByte.printStackTrace();
      paramInt1 = paramInt2;
    }
    return paramInt1;
  }
}


/* Location:              ~/com/app/system/streaming/f/g.class
 *
 * Reversed by:           J
 */