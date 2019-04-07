package org.apache.commons.net.ftp.parser;

import java.io.BufferedReader;
import java.text.ParseException;
import java.util.StringTokenizer;
import org.apache.commons.net.ftp.d;
import org.apache.commons.net.ftp.f;

public class o
  extends b
{
  public o()
  {
    this(null);
  }
  
  public o(d paramd)
  {
    super("(.*?;[0-9]+)\\s*(\\d+)/\\d+\\s*(\\S+)\\s+(\\S+)\\s+\\[(([0-9$A-Za-z_]+)|([0-9$A-Za-z_]+),([0-9$a-zA-Z_]+))\\]?\\s*\\([a-zA-Z]*,([a-zA-Z]*),([a-zA-Z]*),([a-zA-Z]*)\\)");
    a(paramd);
  }
  
  public String a(BufferedReader paramBufferedReader)
  {
    String str = paramBufferedReader.readLine();
    StringBuilder localStringBuilder = new StringBuilder();
    while (str != null) {
      if ((!str.startsWith("Directory")) && (!str.startsWith("Total")))
      {
        localStringBuilder.append(str);
        if (str.trim().endsWith(")")) {
          break;
        }
        str = paramBufferedReader.readLine();
      }
      else
      {
        str = paramBufferedReader.readLine();
      }
    }
    if (localStringBuilder.length() == 0) {
      paramBufferedReader = null;
    } else {
      paramBufferedReader = localStringBuilder.toString();
    }
    return paramBufferedReader;
  }
  
  protected d a()
  {
    return new d("VMS", "d-MMM-yyyy HH:mm:ss", null);
  }
  
  public f a(String paramString)
  {
    boolean bool = c(paramString);
    String str1 = null;
    f localf;
    String str2;
    String str3;
    String str4;
    String str5;
    String str6;
    String str7;
    if (bool)
    {
      localf = new f();
      localf.a(paramString);
      str2 = b(1);
      str3 = b(2);
      paramString = new StringBuilder();
      paramString.append(b(3));
      paramString.append(" ");
      paramString.append(b(4));
      str4 = paramString.toString();
      paramString = b(5);
      str5 = b(9);
      str6 = b(10);
      str7 = b(11);
    }
    try
    {
      localf.a(super.b(str4));
      paramString = new StringTokenizer(paramString, ",");
      switch (paramString.countTokens())
      {
      default: 
        paramString = null;
        break;
      case 2: 
        str1 = paramString.nextToken();
        paramString = paramString.nextToken();
        break;
      case 1: 
        paramString = paramString.nextToken();
      }
      if (str2.lastIndexOf(".DIR") != -1) {
        localf.a(1);
      } else {
        localf.a(0);
      }
      if (b()) {
        localf.b(str2);
      } else {
        localf.b(str2.substring(0, str2.lastIndexOf(";")));
      }
      localf.a(Long.parseLong(str3) * 512L);
      localf.c(str1);
      localf.d(paramString);
      for (int i = 0; i < 3; i++)
      {
        paramString = new String[] { str5, str6, str7 }[i];
        if (paramString.indexOf('R') >= 0) {
          bool = true;
        } else {
          bool = false;
        }
        localf.a(i, 0, bool);
        if (paramString.indexOf('W') >= 0) {
          bool = true;
        } else {
          bool = false;
        }
        localf.a(i, 1, bool);
        if (paramString.indexOf('E') >= 0) {
          bool = true;
        } else {
          bool = false;
        }
        localf.a(i, 2, bool);
      }
      return localf;
      return null;
    }
    catch (ParseException localParseException)
    {
      for (;;) {}
    }
  }
  
  protected boolean b()
  {
    return false;
  }
}


/* Location:              ~/org/apache/commons/net/ftp/parser/o.class
 *
 * Reversed by:           J
 */