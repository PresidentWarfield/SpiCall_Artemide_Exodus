package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import org.apache.commons.net.ftp.d;
import org.apache.commons.net.ftp.f;

public class k
  extends b
{
  public k()
  {
    this(null);
  }
  
  public k(d paramd)
  {
    super("\\s*([0-9]+)\\s*(\\s+|[A-Z]+)\\s*(DIR|\\s+)\\s*(\\S+)\\s+(\\S+)\\s+(\\S.*)");
    a(paramd);
  }
  
  protected d a()
  {
    return new d("OS/2", "MM-dd-yy HH:mm", null);
  }
  
  public f a(String paramString)
  {
    f localf = new f();
    String str1;
    String str2;
    Object localObject;
    String str3;
    if (c(paramString))
    {
      str1 = b(1);
      str2 = b(2);
      paramString = b(3);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(b(4));
      ((StringBuilder)localObject).append(" ");
      ((StringBuilder)localObject).append(b(5));
      str3 = ((StringBuilder)localObject).toString();
      localObject = b(6);
    }
    try
    {
      localf.a(super.b(str3));
      if ((!paramString.trim().equals("DIR")) && (!str2.trim().equals("DIR"))) {
        localf.a(0);
      } else {
        localf.a(1);
      }
      localf.b(((String)localObject).trim());
      localf.a(Long.parseLong(str1.trim()));
      return localf;
      return null;
    }
    catch (ParseException localParseException)
    {
      for (;;) {}
    }
  }
}


/* Location:              ~/org/apache/commons/net/ftp/parser/k.class
 *
 * Reversed by:           J
 */