package com.app.system.streaming.c;

import java.nio.ByteBuffer;

public class c
{
  private int a;
  private int b;
  private int c;
  private int d;
  private int e;
  private boolean f;
  private boolean g = false;
  private int h;
  private byte[] i;
  
  public int a()
  {
    return this.e * 3 / 2;
  }
  
  public void a(int paramInt)
  {
    this.c = paramInt;
  }
  
  public void a(int paramInt1, int paramInt2)
  {
    this.b = paramInt2;
    this.d = paramInt1;
    this.a = paramInt2;
    this.c = paramInt1;
    this.e = (this.d * this.b);
  }
  
  public void a(boolean paramBoolean)
  {
    this.f = paramBoolean;
  }
  
  public void a(byte[] paramArrayOfByte, ByteBuffer paramByteBuffer)
  {
    byte[] arrayOfByte = a(paramArrayOfByte);
    int j;
    if (paramByteBuffer.capacity() < paramArrayOfByte.length) {
      j = paramByteBuffer.capacity();
    } else {
      j = paramArrayOfByte.length;
    }
    paramByteBuffer.put(arrayOfByte, 0, j);
  }
  
  public byte[] a(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = this.i;
    if ((arrayOfByte == null) || (arrayOfByte.length != this.a * 3 * this.c / 2 + this.h)) {
      this.i = new byte[this.a * 3 * this.c / 2 + this.h];
    }
    int j;
    int k;
    if (!this.f)
    {
      if ((this.a == this.b) && (this.c == this.d))
      {
        if (!this.g) {
          for (j = this.e;; j += 2)
          {
            k = this.e;
            if (j >= k + k / 2) {
              break;
            }
            arrayOfByte = this.i;
            k = j + 1;
            arrayOfByte[0] = ((byte)paramArrayOfByte[k]);
            paramArrayOfByte[k] = ((byte)paramArrayOfByte[j]);
            paramArrayOfByte[j] = ((byte)arrayOfByte[0]);
          }
        }
        if (this.h > 0)
        {
          System.arraycopy(paramArrayOfByte, 0, this.i, 0, this.e);
          j = this.e;
          System.arraycopy(paramArrayOfByte, j, this.i, this.h + j, j / 2);
          return this.i;
        }
        return paramArrayOfByte;
      }
    }
    else if ((this.a == this.b) && (this.c == this.d))
    {
      int m;
      if (!this.g) {
        for (j = 0;; j++)
        {
          m = this.e;
          if (j >= m / 4) {
            break;
          }
          arrayOfByte = this.i;
          k = j * 2;
          arrayOfByte[j] = ((byte)paramArrayOfByte[(m + k + 1)]);
          arrayOfByte[(m / 4 + j)] = ((byte)paramArrayOfByte[(m + k)]);
        }
      }
      for (j = 0;; j++)
      {
        k = this.e;
        if (j >= k / 4) {
          break;
        }
        arrayOfByte = this.i;
        m = j * 2;
        arrayOfByte[j] = ((byte)paramArrayOfByte[(k + m)]);
        arrayOfByte[(k / 4 + j)] = ((byte)paramArrayOfByte[(k + m + 1)]);
      }
      if (this.h == 0)
      {
        arrayOfByte = this.i;
        j = this.e;
        System.arraycopy(arrayOfByte, 0, paramArrayOfByte, j, j / 2);
        return paramArrayOfByte;
      }
      System.arraycopy(paramArrayOfByte, 0, this.i, 0, this.e);
      paramArrayOfByte = this.i;
      j = this.e;
      System.arraycopy(paramArrayOfByte, 0, paramArrayOfByte, this.h + j, j / 2);
      return this.i;
    }
    return paramArrayOfByte;
  }
  
  public int b()
  {
    return this.c;
  }
  
  public void b(int paramInt)
  {
    this.a = paramInt;
  }
  
  public void b(boolean paramBoolean)
  {
    this.g = paramBoolean;
  }
  
  public int c()
  {
    return this.a;
  }
  
  public void c(int paramInt)
  {
    this.h = paramInt;
  }
  
  public int d()
  {
    return this.h;
  }
  
  public void d(int paramInt)
  {
    if ((paramInt != 39) && (paramInt != 2130706688)) {}
    switch (paramInt)
    {
    default: 
      break;
    case 19: 
    case 20: 
      a(true);
      break;
    case 21: 
      a(false);
    }
  }
  
  public boolean e()
  {
    return this.f;
  }
  
  public boolean f()
  {
    return this.g;
  }
}


/* Location:              ~/com/app/system/streaming/c/c.class
 *
 * Reversed by:           J
 */