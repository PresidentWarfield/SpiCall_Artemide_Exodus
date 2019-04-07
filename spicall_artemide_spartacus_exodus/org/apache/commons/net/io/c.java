package org.apache.commons.net.io;

import java.io.FilterOutputStream;
import java.io.OutputStream;

public final class c
  extends FilterOutputStream
{
  private boolean a = false;
  
  public c(OutputStream paramOutputStream)
  {
    super(paramOutputStream);
  }
  
  public void write(int paramInt)
  {
    if ((paramInt == 10) || (paramInt == 13)) {}
    try
    {
      this.a = true;
      this.out.write(13);
      return;
    }
    finally {}
    if (!this.a) {
      this.out.write(13);
    }
    this.a = false;
    this.out.write(paramInt);
  }
  
  public void write(byte[] paramArrayOfByte)
  {
    try
    {
      write(paramArrayOfByte, 0, paramArrayOfByte.length);
      return;
    }
    finally
    {
      paramArrayOfByte = finally;
      throw paramArrayOfByte;
    }
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    for (;;)
    {
      if (paramInt2 > 0) {}
      try
      {
        write(paramArrayOfByte[paramInt1]);
        paramInt1++;
        paramInt2--;
      }
      finally {}
    }
  }
}


/* Location:              ~/org/apache/commons/net/io/c.class
 *
 * Reversed by:           J
 */