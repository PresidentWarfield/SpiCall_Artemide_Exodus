package org.apache.commons.net.ftp.parser;

import java.util.Calendar;
import org.apache.commons.net.ftp.a;
import org.apache.commons.net.ftp.d;

public abstract class b
  extends m
  implements a
{
  private final e b = new f();
  
  public b(String paramString)
  {
    super(paramString);
  }
  
  public b(String paramString, int paramInt)
  {
    super(paramString, paramInt);
  }
  
  protected abstract d a();
  
  public void a(d paramd)
  {
    if ((this.b instanceof a))
    {
      d locald = a();
      if (paramd != null)
      {
        if (paramd.b() == null) {
          paramd.a(locald.b());
        }
        if (paramd.c() == null) {
          paramd.b(locald.c());
        }
        ((a)this.b).a(paramd);
      }
      else
      {
        ((a)this.b).a(locald);
      }
    }
  }
  
  public Calendar b(String paramString)
  {
    return this.b.a(paramString);
  }
}


/* Location:              ~/org/apache/commons/net/ftp/parser/b.class
 *
 * Reversed by:           J
 */