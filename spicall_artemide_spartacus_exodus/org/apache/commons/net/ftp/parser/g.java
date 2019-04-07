package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import java.util.List;
import org.apache.commons.net.ftp.d;
import org.apache.commons.net.ftp.f;

public class g
  extends b
{
  private int b = -1;
  private n c;
  
  public g()
  {
    super("");
    super.a(null);
  }
  
  private boolean a(f paramf, String paramString)
  {
    if (c(paramString))
    {
      paramf.a(paramString);
      paramString = b(2);
      String str = b(1);
      paramf.b(paramString);
      if ("PS".equals(str))
      {
        paramf.a(0);
      }
      else
      {
        if ((!"PO".equals(str)) && (!"PO-E".equals(str))) {
          return false;
        }
        paramf.a(1);
      }
      return true;
    }
    return false;
  }
  
  private boolean b(f paramf, String paramString)
  {
    if (c(paramString))
    {
      paramf.a(paramString);
      paramString = b(1);
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(b(2));
      ((StringBuilder)localObject).append(" ");
      ((StringBuilder)localObject).append(b(3));
      localObject = ((StringBuilder)localObject).toString();
      paramf.b(paramString);
      paramf.a(0);
      try
      {
        paramf.a(super.b((String)localObject));
        return true;
      }
      catch (ParseException paramf)
      {
        paramf.printStackTrace();
        return false;
      }
    }
    return false;
  }
  
  private boolean c(f paramf, String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0))
    {
      paramf.a(paramString);
      paramf.b(paramString.split(" ")[0]);
      paramf.a(0);
      return true;
    }
    return false;
  }
  
  private boolean d(f paramf, String paramString)
  {
    return this.c.a(paramString) != null;
  }
  
  private boolean e(f paramf, String paramString)
  {
    if ((c(paramString)) && (b(3).equalsIgnoreCase("OUTPUT")))
    {
      paramf.a(paramString);
      paramf.b(b(2));
      paramf.a(0);
      return true;
    }
    return false;
  }
  
  private boolean f(f paramf, String paramString)
  {
    if ((c(paramString)) && (b(4).equalsIgnoreCase("OUTPUT")))
    {
      paramf.a(paramString);
      paramf.b(b(2));
      paramf.a(0);
      return true;
    }
    return false;
  }
  
  public List<String> a(List<String> paramList)
  {
    if ((paramList != null) && (paramList.size() > 0))
    {
      String str = (String)paramList.get(0);
      if ((str.indexOf("Volume") >= 0) && (str.indexOf("Dsname") >= 0))
      {
        a(0);
        super.d("\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+[FV]\\S*\\s+\\S+\\s+\\S+\\s+(PS|PO|PO-E)\\s+(\\S+)\\s*");
      }
      else if ((str.indexOf("Name") >= 0) && (str.indexOf("Id") >= 0))
      {
        a(1);
        super.d("(\\S+)\\s+\\S+\\s+\\S+\\s+(\\S+)\\s+(\\S+)\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s*");
      }
      else if (str.indexOf("total") == 0)
      {
        a(2);
        this.c = new n();
      }
      else if (str.indexOf("Spool Files") >= 30)
      {
        a(3);
        super.d("(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*");
      }
      else if ((str.indexOf("JOBNAME") == 0) && (str.indexOf("JOBID") > 8))
      {
        a(4);
        super.d("(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+).*");
      }
      else
      {
        a(-1);
      }
      if (this.b != 3) {
        paramList.remove(0);
      }
    }
    return paramList;
  }
  
  protected d a()
  {
    return new d("MVS", "yyyy/MM/dd HH:mm", null);
  }
  
  public f a(String paramString)
  {
    f localf = new f();
    int i = this.b;
    boolean bool;
    if (i == 0)
    {
      bool = a(localf, paramString);
    }
    else if (i == 1)
    {
      bool = b(localf, paramString);
      if (!bool) {
        bool = c(localf, paramString);
      }
    }
    else if (i == 2)
    {
      bool = d(localf, paramString);
    }
    else if (i == 3)
    {
      bool = e(localf, paramString);
    }
    else if (i == 4)
    {
      bool = f(localf, paramString);
    }
    else
    {
      bool = false;
    }
    paramString = localf;
    if (!bool) {
      paramString = null;
    }
    return paramString;
  }
  
  void a(int paramInt)
  {
    this.b = paramInt;
  }
}


/* Location:              ~/org/apache/commons/net/ftp/parser/g.class
 *
 * Reversed by:           J
 */