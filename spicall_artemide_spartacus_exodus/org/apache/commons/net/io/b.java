package org.apache.commons.net.io;

import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class b
  extends FilterOutputStream
{
  private final Socket a;
  
  public b(Socket paramSocket, OutputStream paramOutputStream)
  {
    super(paramOutputStream);
    this.a = paramSocket;
  }
  
  public void close()
  {
    super.close();
    this.a.close();
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.out.write(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:              ~/org/apache/commons/net/io/b.class
 *
 * Reversed by:           J
 */