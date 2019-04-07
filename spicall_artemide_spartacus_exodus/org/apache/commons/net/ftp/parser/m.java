package org.apache.commons.net.ftp.parser;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.net.ftp.h;

public abstract class m
  extends h
{
  protected Matcher a = null;
  private Pattern b = null;
  private MatchResult c = null;
  
  public m(String paramString)
  {
    a(paramString, 0);
  }
  
  public m(String paramString, int paramInt)
  {
    a(paramString, paramInt);
  }
  
  private void a(String paramString, int paramInt)
  {
    try
    {
      this.b = Pattern.compile(paramString, paramInt);
      return;
    }
    catch (PatternSyntaxException localPatternSyntaxException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unparseable regex supplied: ");
      localStringBuilder.append(paramString);
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
  }
  
  public String b(int paramInt)
  {
    MatchResult localMatchResult = this.c;
    if (localMatchResult == null) {
      return null;
    }
    return localMatchResult.group(paramInt);
  }
  
  public boolean c(String paramString)
  {
    this.c = null;
    this.a = this.b.matcher(paramString);
    if (this.a.matches()) {
      this.c = this.a.toMatchResult();
    }
    boolean bool;
    if (this.c != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean d(String paramString)
  {
    a(paramString, 0);
    return true;
  }
}


/* Location:              ~/org/apache/commons/net/ftp/parser/m.class
 *
 * Reversed by:           J
 */