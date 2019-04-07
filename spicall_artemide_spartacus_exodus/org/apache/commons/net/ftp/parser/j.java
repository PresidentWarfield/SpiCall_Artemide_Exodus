package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import org.apache.commons.net.ftp.d;
import org.apache.commons.net.ftp.f;

public class j
  extends b
{
  public j()
  {
    this(null);
  }
  
  public j(d paramd)
  {
    super("(d|-){1}\\s+\\[([-A-Z]+)\\]\\s+(\\S+)\\s+(\\d+)\\s+(\\S+\\s+\\S+\\s+((\\d+:\\d+)|(\\d{4})))\\s+(.*)");
    a(paramd);
  }
  
  protected d a()
  {
    return new d("NETWARE", "MMM dd yyyy", "MMM dd HH:mm");
  }
  
  public f a(String paramString)
  {
    f localf = new f();
    String str1;
    String str2;
    String str3;
    String str4;
    String str5;
    if (c(paramString))
    {
      str1 = b(1);
      str2 = b(2);
      str3 = b(3);
      str4 = b(4);
      str5 = b(5);
      paramString = b(9);
    }
    try
    {
      localf.a(super.b(str5));
      if (str1.trim().equals("d")) {
        localf.a(1);
      } else {
        localf.a(0);
      }
      localf.d(str3);
      localf.b(paramString.trim());
      localf.a(Long.parseLong(str4.trim()));
      if (str2.indexOf("R") != -1) {
        localf.a(0, 0, true);
      }
      if (str2.indexOf("W") != -1) {
        localf.a(0, 1, true);
      }
      return localf;
      return null;
    }
    catch (ParseException localParseException)
    {
      for (;;) {}
    }
  }
}


/* Location:              ~/org/apache/commons/net/ftp/parser/j.class
 *
 * Reversed by:           J
 */