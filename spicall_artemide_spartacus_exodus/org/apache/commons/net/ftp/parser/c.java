package org.apache.commons.net.ftp.parser;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class c
  implements d
{
  private static final Pattern a = Pattern.compile("(\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*\\.)+\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*");
  
  private org.apache.commons.net.ftp.g a(String paramString, org.apache.commons.net.ftp.d paramd)
  {
    if (a.matcher(paramString).matches()) {}
    try
    {
      Object localObject1 = Class.forName(paramString);
      try
      {
        org.apache.commons.net.ftp.g localg = (org.apache.commons.net.ftp.g)((Class)localObject1).newInstance();
      }
      catch (ExceptionInInitializerError localExceptionInInitializerError)
      {
        localObject1 = new org/apache/commons/net/ftp/parser/ParserInitializationException;
        ((ParserInitializationException)localObject1).<init>("Error initializing parser", localExceptionInInitializerError);
        throw ((Throwable)localObject1);
      }
      catch (Exception localException)
      {
        localParserInitializationException = new org/apache/commons/net/ftp/parser/ParserInitializationException;
        localParserInitializationException.<init>("Error initializing parser", localException);
        throw localParserInitializationException;
      }
      catch (ClassCastException localClassCastException)
      {
        localParserInitializationException = new org/apache/commons/net/ftp/parser/ParserInitializationException;
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append(localException.getName());
        localStringBuilder.append(" does not implement the interface ");
        localStringBuilder.append("org.apache.commons.net.ftp.FTPFileEntryParser.");
        localParserInitializationException.<init>(localStringBuilder.toString(), localClassCastException);
        throw localParserInitializationException;
      }
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      ParserInitializationException localParserInitializationException;
      Object localObject2;
      for (;;) {}
    }
    localParserInitializationException = null;
    localObject2 = localParserInitializationException;
    if (localParserInitializationException == null)
    {
      localObject2 = paramString.toUpperCase(Locale.ENGLISH);
      if (((String)localObject2).indexOf("UNIX_LTRIM") >= 0) {
        localObject2 = new n(paramd, true);
      } else if (((String)localObject2).indexOf("UNIX") >= 0) {
        localObject2 = new n(paramd, false);
      } else if (((String)localObject2).indexOf("VMS") >= 0) {
        localObject2 = new p(paramd);
      } else if (((String)localObject2).indexOf("WINDOWS") >= 0) {
        localObject2 = b(paramd);
      } else if (((String)localObject2).indexOf("OS/2") >= 0) {
        localObject2 = new k(paramd);
      } else if ((((String)localObject2).indexOf("OS/400") < 0) && (((String)localObject2).indexOf("AS/400") < 0))
      {
        if (((String)localObject2).indexOf("MVS") >= 0)
        {
          localObject2 = new g();
        }
        else if (((String)localObject2).indexOf("NETWARE") >= 0)
        {
          localObject2 = new j(paramd);
        }
        else if (((String)localObject2).indexOf("MACOS PETER") >= 0)
        {
          localObject2 = new h(paramd);
        }
        else if (((String)localObject2).indexOf("TYPE: L8") >= 0)
        {
          localObject2 = new n(paramd);
        }
        else
        {
          paramd = new StringBuilder();
          paramd.append("Unknown parser type: ");
          paramd.append(paramString);
          throw new ParserInitializationException(paramd.toString());
        }
      }
      else {
        localObject2 = c(paramd);
      }
    }
    if ((localObject2 instanceof org.apache.commons.net.ftp.a)) {
      ((org.apache.commons.net.ftp.a)localObject2).a(paramd);
    }
    return (org.apache.commons.net.ftp.g)localObject2;
  }
  
  private org.apache.commons.net.ftp.g b(org.apache.commons.net.ftp.d paramd)
  {
    if ((paramd != null) && ("WINDOWS".equals(paramd.a()))) {
      return new i(paramd);
    }
    org.apache.commons.net.ftp.d locald;
    if (paramd != null) {
      locald = new org.apache.commons.net.ftp.d(paramd);
    } else {
      locald = null;
    }
    paramd = new i(paramd);
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (locald != null)
    {
      bool2 = bool1;
      if ("UNIX_LTRIM".equals(locald.a())) {
        bool2 = true;
      }
    }
    return new a(new org.apache.commons.net.ftp.g[] { paramd, new n(locald, bool2) });
  }
  
  private org.apache.commons.net.ftp.g c(org.apache.commons.net.ftp.d paramd)
  {
    if ((paramd != null) && ("OS/400".equals(paramd.a()))) {
      return new l(paramd);
    }
    org.apache.commons.net.ftp.d locald;
    if (paramd != null) {
      locald = new org.apache.commons.net.ftp.d(paramd);
    } else {
      locald = null;
    }
    paramd = new l(paramd);
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (locald != null)
    {
      bool2 = bool1;
      if ("UNIX_LTRIM".equals(locald.a())) {
        bool2 = true;
      }
    }
    return new a(new org.apache.commons.net.ftp.g[] { paramd, new n(locald, bool2) });
  }
  
  public org.apache.commons.net.ftp.g a(String paramString)
  {
    if (paramString != null) {
      return a(paramString, null);
    }
    throw new ParserInitializationException("Parser key cannot be null");
  }
  
  public org.apache.commons.net.ftp.g a(org.apache.commons.net.ftp.d paramd)
  {
    return a(paramd.a(), paramd);
  }
}


/* Location:              ~/org/apache/commons/net/ftp/parser/c.class
 *
 * Reversed by:           J
 */