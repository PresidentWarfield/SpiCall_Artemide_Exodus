package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import java.util.List;
import java.util.ListIterator;
import org.apache.commons.net.ftp.d;

public class n
  extends b
{
  public static final d b = new d("UNIX", "yyyy-MM-dd HH:mm", null);
  final boolean c;
  
  public n()
  {
    this(null);
  }
  
  public n(d paramd)
  {
    this(paramd, false);
  }
  
  public n(d paramd, boolean paramBoolean)
  {
    super("([bcdelfmpSs-])(((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-])))\\+?\\s*(\\d+)\\s+(?:(\\S+(?:\\s\\S+)*?)\\s+)?(?:(\\S+(?:\\s\\S+)*)\\s+)?(\\d+(?:,\\s*\\d+)?)\\s+((?:\\d+[-/]\\d+[-/]\\d+)|(?:\\S{3}\\s+\\d{1,2})|(?:\\d{1,2}\\s+\\S{3})|(?:\\d{1,2}月\\s+\\d{1,2}日))\\s+((?:\\d+(?::\\d+)?)|(?:\\d{4}年))\\s(.*)");
    a(paramd);
    this.c = paramBoolean;
  }
  
  public List<String> a(List<String> paramList)
  {
    ListIterator localListIterator = paramList.listIterator();
    while (localListIterator.hasNext()) {
      if (((String)localListIterator.next()).matches("^total \\d+$")) {
        localListIterator.remove();
      }
    }
    return paramList;
  }
  
  protected d a()
  {
    return new d("UNIX", "MMM d yyyy", "MMM d HH:mm");
  }
  
  public org.apache.commons.net.ftp.f a(String paramString)
  {
    localf = new org.apache.commons.net.ftp.f();
    localf.a(paramString);
    String str1;
    String str6;
    Object localObject;
    if (c(paramString))
    {
      str1 = b(1);
      str2 = b(15);
      str3 = b(16);
      str4 = b(17);
      str5 = b(18);
      paramString = new StringBuilder();
      paramString.append(b(19));
      paramString.append(" ");
      paramString.append(b(20));
      str6 = paramString.toString();
      localObject = b(21);
      paramString = (String)localObject;
      if (this.c) {
        paramString = ((String)localObject).replaceFirst("^\\s+", "");
      }
    }
    try
    {
      if (b(19).contains("月"))
      {
        f localf1 = new org/apache/commons/net/ftp/parser/f;
        localf1.<init>();
        localObject = new org/apache/commons/net/ftp/d;
        ((d)localObject).<init>("UNIX", "M'月' d'日' yyyy'年'", "M'月' d'日' HH:mm");
        localf1.a((d)localObject);
        localf.a(localf1.a(str6));
      }
      else
      {
        localf.a(super.b(str6));
      }
    }
    catch (ParseException localNumberFormatException1)
    {
      try
      {
        int j;
        int k;
        int m;
        localf.b(Integer.parseInt(str2));
        localf.d(str3);
        localf.c(str4);
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        try
        {
          for (;;)
          {
            int i;
            localf.a(Long.parseLong(str5));
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
    i = str1.charAt(0);
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
    k = 0;
    for (m = 4; k < 3; m += 4)
    {
      localf.a(k, 0, b(m).equals("-") ^ true);
      localf.a(k, 1, b(m + 1).equals("-") ^ true);
      localObject = b(m + 2);
      if ((!((String)localObject).equals("-")) && (!Character.isUpperCase(((String)localObject).charAt(0)))) {
        localf.a(k, 2, true);
      } else {
        localf.a(k, 2, false);
      }
      k++;
    }
    if (j != 0) {}
  }
}


/* Location:              ~/org/apache/commons/net/ftp/parser/n.class
 *
 * Reversed by:           J
 */