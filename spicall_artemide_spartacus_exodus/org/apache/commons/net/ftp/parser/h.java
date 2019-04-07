package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import org.apache.commons.net.ftp.d;
import org.apache.commons.net.ftp.f;

public class h
  extends b
{
  public h()
  {
    this(null);
  }
  
  public h(d paramd)
  {
    super("([bcdelfmpSs-])(((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-])))\\+?\\s+((folder\\s+)|((\\d+)\\s+(\\d+)\\s+))(\\d+)\\s+((?:\\d+[-/]\\d+[-/]\\d+)|(?:\\S{3}\\s+\\d{1,2})|(?:\\d{1,2}\\s+\\S{3}))\\s+(\\d+(?::\\d+)?)\\s+(\\S*)(\\s*.*)");
    a(paramd);
  }
  
  protected d a()
  {
    return new d("UNIX", "MMM d yyyy", "MMM d HH:mm");
  }
  
  public f a(String paramString)
  {
    f localf = new f();
    localf.a(paramString);
    String str1;
    Object localObject;
    String str2;
    String str3;
    if (c(paramString))
    {
      str1 = b(1);
      localObject = b(20);
      paramString = new StringBuilder();
      paramString.append(b(21));
      paramString.append(" ");
      paramString.append(b(22));
      str2 = paramString.toString();
      str3 = b(23);
      paramString = b(24);
    }
    try
    {
      localf.a(super.b(str2));
      i = str1.charAt(0);
      int j;
      if (i != 45)
      {
        if (i != 108) {}
        switch (i)
        {
        default: 
          i = 3;
          j = 0;
          break;
        case 101: 
          i = 2;
          j = 0;
          break;
        case 100: 
          i = 1;
          j = 0;
          break;
        case 98: 
        case 99: 
          i = 0;
          j = 1;
          break;
          i = 2;
          j = 0;
          break;
        }
      }
      else
      {
        i = 0;
        j = 0;
      }
      localf.a(i);
      int k = 0;
      for (int m = 4; k < 3; m += 4)
      {
        localf.a(k, 0, b(m).equals("-") ^ true);
        localf.a(k, 1, b(m + 1).equals("-") ^ true);
        str1 = b(m + 2);
        if ((!str1.equals("-")) && (!Character.isUpperCase(str1.charAt(0)))) {
          localf.a(k, 2, true);
        } else {
          localf.a(k, 2, false);
        }
        k++;
      }
      if (j != 0) {}
    }
    catch (ParseException localNumberFormatException1)
    {
      try
      {
        localf.b(Integer.parseInt("0"));
        localf.d(null);
        localf.c(null);
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        try
        {
          for (;;)
          {
            int i;
            localf.a(Long.parseLong((String)localObject));
            if (paramString == null)
            {
              localf.b(str3);
            }
            else
            {
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append(str3);
              ((StringBuilder)localObject).append(paramString);
              paramString = ((StringBuilder)localObject).toString();
              if (i == 2)
              {
                i = paramString.indexOf(" -> ");
                if (i == -1)
                {
                  localf.b(paramString);
                }
                else
                {
                  localf.b(paramString.substring(0, i));
                  localf.e(paramString.substring(i + 4));
                }
              }
              else
              {
                localf.b(paramString);
              }
            }
            return localf;
            return null;
            localParseException = localParseException;
          }
          localNumberFormatException1 = localNumberFormatException1;
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          for (;;) {}
        }
      }
    }
  }
}


/* Location:              ~/org/apache/commons/net/ftp/parser/h.class
 *
 * Reversed by:           J
 */