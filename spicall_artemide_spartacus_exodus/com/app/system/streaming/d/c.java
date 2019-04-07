package com.app.system.streaming.d;

import android.util.Base64;
import java.io.IOException;
import java.io.RandomAccessFile;

class c
{
  private RandomAccessFile a;
  private byte[] b = new byte[4];
  private long c = 0L;
  private byte[] d;
  private byte[] e;
  private int f;
  private int g;
  
  public c(RandomAccessFile paramRandomAccessFile, long paramLong)
  {
    this.a = paramRandomAccessFile;
    this.c = paramLong;
    e();
    d();
  }
  
  private boolean d()
  {
    try
    {
      this.a.skipBytes(7);
      this.f = (this.a.readByte() & 0xFF);
      this.e = new byte[this.f];
      this.a.read(this.e, 0, this.f);
      this.a.skipBytes(2);
      this.g = (this.a.readByte() & 0xFF);
      this.d = new byte[this.g];
      this.a.read(this.d, 0, this.g);
      return true;
    }
    catch (IOException localIOException) {}
    return false;
  }
  
  private boolean e()
  {
    try
    {
      this.a.seek(this.c + 8L);
      int i;
      do
      {
        do
        {
          while (this.a.read() != 97) {}
          this.a.read(this.b, 0, 3);
        } while ((this.b[0] != 118) || (this.b[1] != 99));
        i = this.b[2];
      } while (i != 67);
      return true;
    }
    catch (IOException localIOException) {}
    return false;
  }
  
  public String a()
  {
    return b.a(this.e, 1, 3);
  }
  
  public String b()
  {
    return Base64.encodeToString(this.d, 0, this.g, 2);
  }
  
  public String c()
  {
    return Base64.encodeToString(this.e, 0, this.f, 2);
  }
}


/* Location:              ~/com/app/system/streaming/d/c.class
 *
 * Reversed by:           J
 */