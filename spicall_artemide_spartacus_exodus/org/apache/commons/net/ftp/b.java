package org.apache.commons.net.ftp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.c;
import org.apache.commons.net.d;

public class b
  extends d
{
  protected int j;
  protected ArrayList<String> k;
  protected boolean l;
  protected String m;
  protected String n;
  protected c o;
  protected boolean p = false;
  protected BufferedReader q;
  protected BufferedWriter r;
  private boolean s = true;
  
  public b()
  {
    a(21);
    this.k = new ArrayList();
    this.l = false;
    this.m = null;
    this.n = "ISO-8859-1";
    this.o = new c(this);
  }
  
  private void a(boolean paramBoolean)
  {
    this.l = true;
    this.k.clear();
    String str = this.q.readLine();
    if (str != null)
    {
      int i = str.length();
      if (i >= 3) {
        try
        {
          Object localObject = str.substring(0, 3);
          this.j = Integer.parseInt((String)localObject);
          this.k.add(str);
          if (i > 3)
          {
            int i1 = str.charAt(3);
            if (i1 == 45)
            {
              do
              {
                for (;;)
                {
                  str = this.q.readLine();
                  if (str == null) {
                    break label131;
                  }
                  this.k.add(str);
                  if (!p()) {
                    break;
                  }
                  if (!d(str, (String)localObject)) {
                    break label260;
                  }
                }
              } while (h(str));
              break label260;
              label131:
              throw new FTPConnectionClosedException("Connection closed without indication.");
            }
            else if (q())
            {
              if (i != 4)
              {
                if (i1 != 32)
                {
                  localObject = new StringBuilder();
                  ((StringBuilder)localObject).append("Invalid server reply: '");
                  ((StringBuilder)localObject).append(str);
                  ((StringBuilder)localObject).append("'");
                  throw new MalformedServerReplyException(((StringBuilder)localObject).toString());
                }
              }
              else
              {
                localObject = new StringBuilder();
                ((StringBuilder)localObject).append("Truncated server reply: '");
                ((StringBuilder)localObject).append(str);
                ((StringBuilder)localObject).append("'");
                throw new MalformedServerReplyException(((StringBuilder)localObject).toString());
              }
            }
          }
          else
          {
            if (q()) {
              break label297;
            }
          }
          label260:
          if (paramBoolean) {
            a(this.j, k());
          }
          if (this.j != 421) {
            return;
          }
          throw new FTPConnectionClosedException("FTP response 421 received.  Server closed connection.");
          label297:
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Truncated server reply: '");
          ((StringBuilder)localObject).append(str);
          ((StringBuilder)localObject).append("'");
          throw new MalformedServerReplyException(((StringBuilder)localObject).toString());
        }
        catch (NumberFormatException localNumberFormatException)
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Could not parse response code.\nServer Reply: ");
          localStringBuilder.append(str);
          throw new MalformedServerReplyException(localStringBuilder.toString());
        }
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Truncated server reply: ");
      localStringBuilder.append(str);
      throw new MalformedServerReplyException(localStringBuilder.toString());
    }
    throw new FTPConnectionClosedException("Connection closed without indication.");
  }
  
  private boolean d(String paramString1, String paramString2)
  {
    boolean bool;
    if ((paramString1.startsWith(paramString2)) && (paramString1.charAt(3) == ' ')) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private String e(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    if (paramString2 != null)
    {
      localStringBuilder.append(' ');
      localStringBuilder.append(paramString2);
    }
    localStringBuilder.append("\r\n");
    return localStringBuilder.toString();
  }
  
  private boolean h(String paramString)
  {
    int i = paramString.length();
    boolean bool = false;
    if ((i <= 3) || (paramString.charAt(3) == '-') || (!Character.isDigit(paramString.charAt(0)))) {
      bool = true;
    }
    return bool;
  }
  
  private void i(String paramString)
  {
    try
    {
      this.r.write(paramString);
      this.r.flush();
      return;
    }
    catch (SocketException paramString)
    {
      if (!c()) {
        throw new FTPConnectionClosedException("Connection unexpectedly closed.");
      }
      throw paramString;
    }
  }
  
  private void r()
  {
    a(true);
  }
  
  public int a(InetAddress paramInetAddress, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder(24);
    localStringBuilder.append(paramInetAddress.getHostAddress().replace('.', ','));
    localStringBuilder.append(',');
    localStringBuilder.append(paramInt >>> 8);
    localStringBuilder.append(',');
    localStringBuilder.append(paramInt & 0xFF);
    return a(e.w, localStringBuilder.toString());
  }
  
  public int a(e parame)
  {
    return a(parame, null);
  }
  
  public int a(e parame, String paramString)
  {
    return b(parame.a(), paramString);
  }
  
  protected void a()
  {
    a(null);
  }
  
  /* Error */
  protected void a(java.io.Reader paramReader)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 219	org/apache/commons/net/d:a	()V
    //   4: aload_1
    //   5: ifnonnull +32 -> 37
    //   8: aload_0
    //   9: new 221	org/apache/commons/net/io/a
    //   12: dup
    //   13: new 223	java/io/InputStreamReader
    //   16: dup
    //   17: aload_0
    //   18: getfield 226	org/apache/commons/net/ftp/b:e	Ljava/io/InputStream;
    //   21: aload_0
    //   22: invokevirtual 229	org/apache/commons/net/ftp/b:g	()Ljava/lang/String;
    //   25: invokespecial 232	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   28: invokespecial 234	org/apache/commons/net/io/a:<init>	(Ljava/io/Reader;)V
    //   31: putfield 63	org/apache/commons/net/ftp/b:q	Ljava/io/BufferedReader;
    //   34: goto +15 -> 49
    //   37: aload_0
    //   38: new 221	org/apache/commons/net/io/a
    //   41: dup
    //   42: aload_1
    //   43: invokespecial 234	org/apache/commons/net/io/a:<init>	(Ljava/io/Reader;)V
    //   46: putfield 63	org/apache/commons/net/ftp/b:q	Ljava/io/BufferedReader;
    //   49: aload_0
    //   50: new 168	java/io/BufferedWriter
    //   53: dup
    //   54: new 236	java/io/OutputStreamWriter
    //   57: dup
    //   58: aload_0
    //   59: getfield 240	org/apache/commons/net/ftp/b:f	Ljava/io/OutputStream;
    //   62: aload_0
    //   63: invokevirtual 229	org/apache/commons/net/ftp/b:g	()Ljava/lang/String;
    //   66: invokespecial 243	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   69: invokespecial 246	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
    //   72: putfield 166	org/apache/commons/net/ftp/b:r	Ljava/io/BufferedWriter;
    //   75: aload_0
    //   76: getfield 248	org/apache/commons/net/ftp/b:i	I
    //   79: ifle +85 -> 164
    //   82: aload_0
    //   83: getfield 251	org/apache/commons/net/ftp/b:b	Ljava/net/Socket;
    //   86: invokevirtual 256	java/net/Socket:getSoTimeout	()I
    //   89: istore_2
    //   90: aload_0
    //   91: getfield 251	org/apache/commons/net/ftp/b:b	Ljava/net/Socket;
    //   94: aload_0
    //   95: getfield 248	org/apache/commons/net/ftp/b:i	I
    //   98: invokevirtual 259	java/net/Socket:setSoTimeout	(I)V
    //   101: aload_0
    //   102: invokespecial 261	org/apache/commons/net/ftp/b:r	()V
    //   105: aload_0
    //   106: getfield 87	org/apache/commons/net/ftp/b:j	I
    //   109: invokestatic 266	org/apache/commons/net/ftp/l:a	(I)Z
    //   112: ifeq +7 -> 119
    //   115: aload_0
    //   116: invokespecial 261	org/apache/commons/net/ftp/b:r	()V
    //   119: aload_0
    //   120: getfield 251	org/apache/commons/net/ftp/b:b	Ljava/net/Socket;
    //   123: iload_2
    //   124: invokevirtual 259	java/net/Socket:setSoTimeout	(I)V
    //   127: goto +55 -> 182
    //   130: astore_1
    //   131: goto +23 -> 154
    //   134: astore_3
    //   135: new 268	java/io/IOException
    //   138: astore_1
    //   139: aload_1
    //   140: ldc_w 270
    //   143: invokespecial 271	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   146: aload_1
    //   147: aload_3
    //   148: invokevirtual 275	java/io/IOException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   151: pop
    //   152: aload_1
    //   153: athrow
    //   154: aload_0
    //   155: getfield 251	org/apache/commons/net/ftp/b:b	Ljava/net/Socket;
    //   158: iload_2
    //   159: invokevirtual 259	java/net/Socket:setSoTimeout	(I)V
    //   162: aload_1
    //   163: athrow
    //   164: aload_0
    //   165: invokespecial 261	org/apache/commons/net/ftp/b:r	()V
    //   168: aload_0
    //   169: getfield 87	org/apache/commons/net/ftp/b:j	I
    //   172: invokestatic 266	org/apache/commons/net/ftp/l:a	(I)Z
    //   175: ifeq +7 -> 182
    //   178: aload_0
    //   179: invokespecial 261	org/apache/commons/net/ftp/b:r	()V
    //   182: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	183	0	this	b
    //   0	183	1	paramReader	java.io.Reader
    //   89	70	2	i	int
    //   134	14	3	localSocketTimeoutException	java.net.SocketTimeoutException
    // Exception table:
    //   from	to	target	type
    //   101	119	130	finally
    //   135	154	130	finally
    //   101	119	134	java/net/SocketTimeoutException
  }
  
  public void a(String paramString)
  {
    this.n = paramString;
  }
  
  public int b(int paramInt)
  {
    return a(e.M, "AEILNTCFRPSBC".substring(paramInt, paramInt + 1));
  }
  
  public int b(String paramString)
  {
    return a(e.N, paramString);
  }
  
  public int b(String paramString1, String paramString2)
  {
    if (this.r != null)
    {
      paramString2 = e(paramString1, paramString2);
      i(paramString2);
      a(paramString1, paramString2);
      r();
      return this.j;
    }
    throw new IOException("Connection is not open");
  }
  
  public int b(InetAddress paramInetAddress, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str1 = paramInetAddress.getHostAddress();
    int i = str1.indexOf("%");
    String str2 = str1;
    if (i > 0) {
      str2 = str1.substring(0, i);
    }
    localStringBuilder.append("|");
    if ((paramInetAddress instanceof Inet4Address)) {
      localStringBuilder.append("1");
    } else if ((paramInetAddress instanceof Inet6Address)) {
      localStringBuilder.append("2");
    }
    localStringBuilder.append("|");
    localStringBuilder.append(str2);
    localStringBuilder.append("|");
    localStringBuilder.append(paramInt);
    localStringBuilder.append("|");
    return a(e.h, localStringBuilder.toString());
  }
  
  public void b()
  {
    super.b();
    this.q = null;
    this.r = null;
    this.l = false;
    this.m = null;
  }
  
  public int c(String paramString)
  {
    return a(e.u, paramString);
  }
  
  public int c(String paramString1, String paramString2)
  {
    e locale = e.n;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString2);
    localStringBuilder.append(" ");
    localStringBuilder.append(paramString1);
    return a(locale, localStringBuilder.toString());
  }
  
  public int d(String paramString)
  {
    return a(e.f, paramString);
  }
  
  public int e(String paramString)
  {
    return a(e.A, paramString);
  }
  
  public int f(String paramString)
  {
    return a(e.g, paramString);
  }
  
  protected c f()
  {
    return this.o;
  }
  
  public int g(String paramString)
  {
    return a(e.o, paramString);
  }
  
  public String g()
  {
    return this.n;
  }
  
  public int h()
  {
    return this.j;
  }
  
  public int i()
  {
    r();
    return this.j;
  }
  
  public String[] j()
  {
    ArrayList localArrayList = this.k;
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  public String k()
  {
    if (!this.l) {
      return this.m;
    }
    Object localObject = new StringBuilder(256);
    Iterator localIterator = this.k.iterator();
    while (localIterator.hasNext())
    {
      ((StringBuilder)localObject).append((String)localIterator.next());
      ((StringBuilder)localObject).append("\r\n");
    }
    this.l = false;
    localObject = ((StringBuilder)localObject).toString();
    this.m = ((String)localObject);
    return (String)localObject;
  }
  
  public int l()
  {
    return a(e.v);
  }
  
  public int m()
  {
    return a(e.i);
  }
  
  public int n()
  {
    return a(e.j);
  }
  
  public int o()
  {
    return a(e.L);
  }
  
  public boolean p()
  {
    return this.p;
  }
  
  public boolean q()
  {
    return this.s;
  }
}


/* Location:              ~/org/apache/commons/net/ftp/b.class
 *
 * Reversed by:           J
 */