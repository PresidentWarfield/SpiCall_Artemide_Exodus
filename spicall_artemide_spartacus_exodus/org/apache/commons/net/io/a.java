package org.apache.commons.net.io;

import java.io.BufferedReader;
import java.io.Reader;

public final class a
  extends BufferedReader
{
  public a(Reader paramReader)
  {
    super(paramReader);
  }
  
  public String readLine()
  {
    Object localObject1 = new StringBuilder();
    Object localObject3 = this.lock;
    int i = 0;
    try
    {
      for (;;)
      {
        int j = read();
        if (j == -1) {
          break;
        }
        if ((i != 0) && (j == 10))
        {
          localObject1 = ((StringBuilder)localObject1).substring(0, ((StringBuilder)localObject1).length() - 1);
          return (String)localObject1;
        }
        if (j == 13) {
          i = 1;
        } else {
          i = 0;
        }
        ((StringBuilder)localObject1).append((char)j);
      }
      localObject3 = ((StringBuilder)localObject1).toString();
      if (((String)localObject3).length() == 0) {
        return null;
      }
      return (String)localObject3;
    }
    finally {}
  }
}


/* Location:              ~/org/apache/commons/net/io/a.class
 *
 * Reversed by:           J
 */