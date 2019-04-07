package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import org.apache.commons.net.ftp.a;
import org.apache.commons.net.ftp.d;

public class i
  extends b
{
  private final e b;
  
  public i()
  {
    this(null);
  }
  
  public i(d paramd)
  {
    super("(\\S+)\\s+(\\S+)\\s+(?:(<DIR>)|([0-9]+))\\s+(\\S.*)", 32);
    a(paramd);
    paramd = new d("WINDOWS", "MM-dd-yy kk:mm", null);
    paramd.a("MM-dd-yy kk:mm");
    this.b = new f();
    ((a)this.b).a(paramd);
  }
  
  public d a()
  {
    return new d("WINDOWS", "MM-dd-yy hh:mma", null);
  }
  
  public org.apache.commons.net.ftp.f a(String paramString)
  {
    org.apache.commons.net.ftp.f localf = new org.apache.commons.net.ftp.f();
    localf.a(paramString);
    if (c(paramString))
    {
      paramString = new StringBuilder();
      paramString.append(b(1));
      paramString.append(" ");
      paramString.append(b(2));
      String str1 = paramString.toString();
      String str2 = b(3);
      String str3 = b(4);
      paramString = b(5);
      try
      {
        localf.a(super.b(str1));
      }
      catch (ParseException localParseException1)
      {
        try
        {
          localf.a(this.b.a(str1));
        }
        catch (ParseException localParseException2) {}
      }
      if ((paramString != null) && (!paramString.equals(".")) && (!paramString.equals("..")))
      {
        localf.b(paramString);
        if ("<DIR>".equals(str2))
        {
          localf.a(1);
          localf.a(0L);
        }
        else
        {
          localf.a(0);
          if (str3 != null) {
            localf.a(Long.parseLong(str3));
          }
        }
        return localf;
      }
      return null;
    }
    return null;
  }
}


/* Location:              ~/org/apache/commons/net/ftp/parser/i.class
 *
 * Reversed by:           J
 */