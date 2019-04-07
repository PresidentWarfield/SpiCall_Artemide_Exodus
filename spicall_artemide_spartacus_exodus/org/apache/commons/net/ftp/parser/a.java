package org.apache.commons.net.ftp.parser;

import org.apache.commons.net.ftp.f;
import org.apache.commons.net.ftp.g;
import org.apache.commons.net.ftp.h;

public class a
  extends h
{
  private final g[] a;
  private g b = null;
  
  public a(g[] paramArrayOfg)
  {
    this.a = paramArrayOfg;
  }
  
  public f a(String paramString)
  {
    Object localObject = this.b;
    if (localObject != null)
    {
      paramString = ((g)localObject).a(paramString);
      if (paramString != null) {
        return paramString;
      }
    }
    else
    {
      for (g localg : this.a)
      {
        localObject = localg.a(paramString);
        if (localObject != null)
        {
          this.b = localg;
          return (f)localObject;
        }
      }
    }
    return null;
  }
}


/* Location:              ~/org/apache/commons/net/ftp/parser/a.class
 *
 * Reversed by:           J
 */