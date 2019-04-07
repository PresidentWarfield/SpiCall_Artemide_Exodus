package org.apache.commons.net.ftp.parser;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.net.ftp.d;

public class p
  extends o
{
  private final Pattern b;
  
  public p()
  {
    this(null);
  }
  
  public p(d paramd)
  {
    a(paramd);
    try
    {
      this.b = Pattern.compile("(.*?);([0-9]+)\\s*.*");
      return;
    }
    catch (PatternSyntaxException paramd)
    {
      throw new IllegalArgumentException("Unparseable regex supplied:  (.*?);([0-9]+)\\s*.*");
    }
  }
  
  public List<String> a(List<String> paramList)
  {
    HashMap localHashMap = new HashMap();
    ListIterator localListIterator = paramList.listIterator();
    Object localObject1;
    Object localObject2;
    while (localListIterator.hasNext())
    {
      localObject1 = ((String)localListIterator.next()).trim();
      localObject1 = this.b.matcher((CharSequence)localObject1);
      if (((Matcher)localObject1).matches())
      {
        localObject2 = ((Matcher)localObject1).toMatchResult();
        localObject1 = ((MatchResult)localObject2).group(1);
        Integer localInteger = Integer.valueOf(((MatchResult)localObject2).group(2));
        localObject2 = (Integer)localHashMap.get(localObject1);
        if ((localObject2 != null) && (localInteger.intValue() < ((Integer)localObject2).intValue())) {
          localListIterator.remove();
        } else {
          localHashMap.put(localObject1, localInteger);
        }
      }
    }
    while (localListIterator.hasPrevious())
    {
      localObject1 = ((String)localListIterator.previous()).trim();
      localObject1 = this.b.matcher((CharSequence)localObject1);
      if (((Matcher)localObject1).matches())
      {
        localObject1 = ((Matcher)localObject1).toMatchResult();
        localObject2 = ((MatchResult)localObject1).group(1);
        localObject1 = Integer.valueOf(((MatchResult)localObject1).group(2));
        localObject2 = (Integer)localHashMap.get(localObject2);
        if ((localObject2 != null) && (((Integer)localObject1).intValue() < ((Integer)localObject2).intValue())) {
          localListIterator.remove();
        }
      }
    }
    return paramList;
  }
  
  protected boolean b()
  {
    return true;
  }
}


/* Location:              ~/org/apache/commons/net/ftp/parser/p.class
 *
 * Reversed by:           J
 */