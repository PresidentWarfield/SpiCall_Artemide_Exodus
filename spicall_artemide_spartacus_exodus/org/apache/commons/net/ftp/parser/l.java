package org.apache.commons.net.ftp.parser;

import java.io.File;
import java.text.ParseException;
import org.apache.commons.net.ftp.d;
import org.apache.commons.net.ftp.f;

public class l
  extends b
{
  public l()
  {
    this(null);
  }
  
  public l(d paramd)
  {
    super("(\\S+)\\s+(?:(\\d+)\\s+)?(?:(\\S+)\\s+(\\S+)\\s+)?(\\*STMF|\\*DIR|\\*FILE|\\*MEM)\\s+(?:(\\S+)\\s*)?");
    a(paramd);
  }
  
  private boolean e(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }
  
  protected d a()
  {
    return new d("OS/400", "yy/MM/dd HH:mm:ss", null);
  }
  
  public f a(String paramString)
  {
    f localf = new f();
    localf.a(paramString);
    String str1;
    String str2;
    int i;
    String str3;
    String str4;
    if (c(paramString))
    {
      str1 = b(1);
      str2 = b(2);
      paramString = "";
      i = 3;
      if ((!e(b(3))) || (!e(b(4))))
      {
        paramString = new StringBuilder();
        paramString.append(b(3));
        paramString.append(" ");
        paramString.append(b(4));
        paramString = paramString.toString();
      }
      str3 = b(5);
      str4 = b(6);
    }
    try
    {
      localf.a(super.b(paramString));
      if (str3.equalsIgnoreCase("*STMF"))
      {
        if ((!e(str2)) && (!e(str4)))
        {
          j = 1;
          i = 0;
          paramString = str4;
        }
        else
        {
          return null;
        }
      }
      else if (str3.equalsIgnoreCase("*DIR"))
      {
        if ((!e(str2)) && (!e(str4)))
        {
          j = 1;
          i = 1;
          paramString = str4;
        }
        else
        {
          return null;
        }
      }
      else if (str3.equalsIgnoreCase("*FILE"))
      {
        if ((str4 != null) && (str4.toUpperCase().endsWith(".SAVF")))
        {
          j = 0;
          i = 0;
          paramString = str4;
        }
        else
        {
          return null;
        }
      }
      else if (str3.equalsIgnoreCase("*MEM"))
      {
        if (e(str4)) {
          return null;
        }
        if ((e(str2)) && (e(paramString)))
        {
          paramString = str4.replace('/', File.separatorChar);
          j = 0;
          i = 0;
        }
        else
        {
          return null;
        }
      }
      else
      {
        j = 1;
        paramString = str4;
      }
      localf.a(i);
      localf.d(str1);
    }
    catch (ParseException localParseException)
    {
      try
      {
        int j;
        localf.a(Long.parseLong(str2));
        str4 = paramString;
        if (paramString.endsWith("/")) {
          str4 = paramString.substring(0, paramString.length() - 1);
        }
        paramString = str4;
        if (j != 0)
        {
          j = str4.lastIndexOf('/');
          paramString = str4;
          if (j > -1) {
            paramString = str4.substring(j + 1);
          }
        }
        localf.b(paramString);
        return localf;
        return null;
        localParseException = localParseException;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        for (;;) {}
      }
    }
  }
}


/* Location:              ~/org/apache/commons/net/ftp/parser/l.class
 *
 * Reversed by:           J
 */