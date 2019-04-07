package org.apache.commons.net.ftp;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import org.apache.commons.net.MalformedServerReplyException;

public class c
  extends b
  implements a
{
  private static final Pattern U = Pattern.compile("(\\d{1,3},\\d{1,3},\\d{1,3},\\d{1,3}),(\\d{1,3}),(\\d{1,3})");
  private InetAddress A;
  private InetAddress B;
  private int C;
  private int D;
  private int E;
  private int F;
  private boolean G;
  private long H;
  private org.apache.commons.net.ftp.parser.d I;
  private int J;
  private int K;
  private int L;
  private boolean M;
  private boolean N;
  private String O;
  private g P;
  private String Q;
  private d R;
  private int S = 1000;
  private a T = new b(this);
  private boolean V = false;
  private HashMap<String, Set<String>> W;
  private int s;
  private int t;
  private int u;
  private String v;
  private final Random w;
  private int x;
  private int y;
  private InetAddress z;
  
  public c()
  {
    x();
    this.t = -1;
    this.G = true;
    this.I = new org.apache.commons.net.ftp.parser.c();
    this.R = null;
    this.M = false;
    this.N = false;
    this.w = new Random();
    this.B = null;
  }
  
  private InetAddress A()
  {
    InetAddress localInetAddress = this.A;
    if (localInetAddress != null) {
      return localInetAddress;
    }
    return z();
  }
  
  private boolean B()
  {
    if (this.W == null)
    {
      int i = n();
      int j = 0;
      if (i == 530) {
        return false;
      }
      boolean bool = l.b(i);
      this.W = new HashMap();
      if (!bool) {
        return false;
      }
      String[] arrayOfString = j();
      i = arrayOfString.length;
      while (j < i)
      {
        Object localObject1 = arrayOfString[j];
        if (((String)localObject1).startsWith(" "))
        {
          String str1 = "";
          int k = ((String)localObject1).indexOf(' ', 1);
          if (k > 0)
          {
            localObject2 = ((String)localObject1).substring(1, k);
            str1 = ((String)localObject1).substring(k + 1);
          }
          else
          {
            localObject2 = ((String)localObject1).substring(1);
          }
          String str2 = ((String)localObject2).toUpperCase(Locale.ENGLISH);
          localObject1 = (Set)this.W.get(str2);
          Object localObject2 = localObject1;
          if (localObject1 == null)
          {
            localObject2 = new HashSet();
            this.W.put(str2, localObject2);
          }
          ((Set)localObject2).add(str1);
        }
        j++;
      }
    }
    return true;
  }
  
  private OutputStream a(OutputStream paramOutputStream)
  {
    int i = this.J;
    if (i > 0) {
      return new BufferedOutputStream(paramOutputStream, i);
    }
    return new BufferedOutputStream(paramOutputStream);
  }
  
  private k a(g paramg, String paramString)
  {
    paramString = b(e.l, q(paramString));
    paramg = new k(paramg, this.R);
    if (paramString == null) {
      return paramg;
    }
    try
    {
      paramg.a(paramString.getInputStream(), g());
      org.apache.commons.net.io.d.a(paramString);
      s();
      return paramg;
    }
    finally
    {
      org.apache.commons.net.io.d.a(paramString);
    }
  }
  
  private OutputStream c(e parame, String paramString)
  {
    return d(parame.a(), paramString);
  }
  
  private static Properties w()
  {
    return c.a;
  }
  
  private void x()
  {
    this.s = 0;
    this.v = null;
    this.u = -1;
    this.z = null;
    this.A = null;
    this.x = 0;
    this.y = 0;
    this.C = 0;
    this.E = 7;
    this.D = 4;
    this.F = 10;
    this.H = 0L;
    this.O = null;
    this.P = null;
    this.Q = "";
    this.W = null;
  }
  
  private int y()
  {
    int i = this.x;
    if (i > 0)
    {
      int j = this.y;
      if (j >= i)
      {
        if (j == i) {
          return j;
        }
        return this.w.nextInt(j - i + 1) + this.x;
      }
    }
    return 0;
  }
  
  private InetAddress z()
  {
    InetAddress localInetAddress = this.z;
    if (localInetAddress != null) {
      return localInetAddress;
    }
    return d();
  }
  
  protected void a()
  {
    a(null);
  }
  
  protected void a(Reader paramReader)
  {
    super.a(paramReader);
    x();
    if (this.V)
    {
      paramReader = new ArrayList(this.k);
      int i = this.j;
      if ((l("UTF8")) || (l("UTF-8")))
      {
        a("UTF-8");
        this.q = new org.apache.commons.net.io.a(new InputStreamReader(this.e, g()));
        this.r = new BufferedWriter(new OutputStreamWriter(this.f, g()));
      }
      this.k.clear();
      this.k.addAll(paramReader);
      this.j = i;
      this.l = true;
    }
  }
  
  public void a(d paramd)
  {
    this.R = paramd;
  }
  
  protected boolean a(long paramLong)
  {
    this.H = 0L;
    return l.c(e(Long.toString(paramLong)));
  }
  
  protected Socket b(e parame, String paramString)
  {
    return e(parame.a(), paramString);
  }
  
  public void b()
  {
    super.b();
    x();
  }
  
  public void b(long paramLong)
  {
    if (paramLong >= 0L) {
      this.H = paramLong;
    }
  }
  
  public boolean c(int paramInt)
  {
    if (l.b(b(paramInt)))
    {
      this.C = paramInt;
      this.D = 4;
      return true;
    }
    return false;
  }
  
  protected OutputStream d(String paramString1, String paramString2)
  {
    paramString2 = e(paramString1, paramString2);
    if (paramString2 == null) {
      return null;
    }
    if (this.C == 0) {
      paramString1 = new org.apache.commons.net.io.c(a(paramString2.getOutputStream()));
    } else {
      paramString1 = paramString2.getOutputStream();
    }
    return new org.apache.commons.net.io.b(paramString2, paramString1);
  }
  
  protected Socket e(String paramString1, String paramString2)
  {
    int i = this.s;
    if ((i != 0) && (i != 2)) {
      return null;
    }
    boolean bool = e() instanceof Inet6Address;
    i = this.s;
    int j = 1;
    if (i == 0)
    {
      localObject = this.h.createServerSocket(y(), 1, z());
      if (bool) {}
      try
      {
        bool = l.b(b(A(), ((ServerSocket)localObject).getLocalPort()));
        if (!bool)
        {
          return null;
          bool = l.b(a(A(), ((ServerSocket)localObject).getLocalPort()));
          if (!bool) {
            return null;
          }
        }
        if (this.H > 0L)
        {
          bool = a(this.H);
          if (!bool) {
            return null;
          }
        }
        bool = l.a(b(paramString1, paramString2));
        if (!bool) {
          return null;
        }
        if (this.t >= 0) {
          ((ServerSocket)localObject).setSoTimeout(this.t);
        }
        paramString1 = ((ServerSocket)localObject).accept();
        if (this.t >= 0) {
          paramString1.setSoTimeout(this.t);
        }
        if (this.L > 0) {
          paramString1.setReceiveBufferSize(this.L);
        }
        if (this.K > 0) {
          paramString1.setSendBufferSize(this.K);
        }
      }
      finally
      {
        ((ServerSocket)localObject).close();
      }
    }
    i = j;
    if (!v()) {
      if (bool) {
        i = j;
      } else {
        i = 0;
      }
    }
    if ((i != 0) && (m() == 229))
    {
      i((String)this.k.get(0));
    }
    else
    {
      if (bool) {
        return null;
      }
      if (l() != 227) {
        return null;
      }
      h((String)this.k.get(0));
    }
    Object localObject = this.g.createSocket();
    i = this.L;
    if (i > 0) {
      ((Socket)localObject).setReceiveBufferSize(i);
    }
    i = this.K;
    if (i > 0) {
      ((Socket)localObject).setSendBufferSize(i);
    }
    InetAddress localInetAddress = this.B;
    if (localInetAddress != null) {
      ((Socket)localObject).bind(new InetSocketAddress(localInetAddress, 0));
    }
    i = this.t;
    if (i >= 0) {
      ((Socket)localObject).setSoTimeout(i);
    }
    ((Socket)localObject).connect(new InetSocketAddress(this.v, this.u), this.i);
    long l = this.H;
    if ((l > 0L) && (!a(l)))
    {
      ((Socket)localObject).close();
      return null;
    }
    if (!l.a(b(paramString1, paramString2)))
    {
      ((Socket)localObject).close();
      return null;
    }
    paramString1 = (String)localObject;
    if ((this.G) && (!a(paramString1)))
    {
      paramString1.close();
      paramString2 = new StringBuilder();
      paramString2.append("Host attempting data connection ");
      paramString2.append(paramString1.getInetAddress().getHostAddress());
      paramString2.append(" is not same as server ");
      paramString2.append(e().getHostAddress());
      throw new IOException(paramString2.toString());
    }
    return paramString1;
  }
  
  public boolean f(String paramString1, String paramString2)
  {
    b(paramString1);
    if (l.b(this.j)) {
      return true;
    }
    if (!l.c(this.j)) {
      return false;
    }
    return l.b(c(paramString2));
  }
  
  public k g(String paramString1, String paramString2)
  {
    p(paramString1);
    return a(this.P, paramString2);
  }
  
  protected void h(String paramString)
  {
    Object localObject = U.matcher(paramString);
    if (((Matcher)localObject).find())
    {
      this.v = ((Matcher)localObject).group(1).replace(',', '.');
      try
      {
        int i = Integer.parseInt(((Matcher)localObject).group(2));
        this.u = (Integer.parseInt(((Matcher)localObject).group(3)) | i << 8);
        localObject = this.T;
        if (localObject != null) {
          try
          {
            localObject = ((a)localObject).a(this.v);
            if (!this.v.equals(localObject))
            {
              StringBuilder localStringBuilder3 = new java/lang/StringBuilder;
              localStringBuilder3.<init>();
              localStringBuilder3.append("[Replacing PASV mode reply address ");
              localStringBuilder3.append(this.v);
              localStringBuilder3.append(" with ");
              localStringBuilder3.append((String)localObject);
              localStringBuilder3.append("]\n");
              a(0, localStringBuilder3.toString());
              this.v = ((String)localObject);
            }
          }
          catch (UnknownHostException localUnknownHostException)
          {
            StringBuilder localStringBuilder1 = new StringBuilder();
            localStringBuilder1.append("Could not parse passive host information.\nServer Reply: ");
            localStringBuilder1.append(paramString);
            throw new MalformedServerReplyException(localStringBuilder1.toString());
          }
        }
        return;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        localStringBuilder2 = new StringBuilder();
        localStringBuilder2.append("Could not parse passive port information.\nServer Reply: ");
        localStringBuilder2.append(paramString);
        throw new MalformedServerReplyException(localStringBuilder2.toString());
      }
    }
    StringBuilder localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append("Could not parse passive host information.\nServer Reply: ");
    localStringBuilder2.append(paramString);
    throw new MalformedServerReplyException(localStringBuilder2.toString());
  }
  
  public boolean h(String paramString1, String paramString2)
  {
    return l.b(c(paramString1, paramString2));
  }
  
  protected void i(String paramString)
  {
    paramString = paramString.substring(paramString.indexOf('(') + 1, paramString.indexOf(')')).trim();
    int i = paramString.charAt(0);
    int j = paramString.charAt(1);
    int k = paramString.charAt(2);
    int m = paramString.charAt(paramString.length() - 1);
    if ((i == j) && (j == k) && (k == m)) {
      try
      {
        m = Integer.parseInt(paramString.substring(3, paramString.length() - 1));
        this.v = e().getHostAddress();
        this.u = m;
        return;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Could not parse extended passive host information.\nServer Reply: ");
        localStringBuilder.append(paramString);
        throw new MalformedServerReplyException(localStringBuilder.toString());
      }
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Could not parse extended passive host information.\nServer Reply: ");
    localStringBuilder.append(paramString);
    throw new MalformedServerReplyException(localStringBuilder.toString());
  }
  
  public boolean j(String paramString)
  {
    return l.b(d(paramString));
  }
  
  public OutputStream k(String paramString)
  {
    return c(e.d, paramString);
  }
  
  public boolean l(String paramString)
  {
    if (!B()) {
      return false;
    }
    return this.W.containsKey(paramString.toUpperCase(Locale.ENGLISH));
  }
  
  public boolean m(String paramString)
  {
    return l.b(f(paramString));
  }
  
  public boolean n(String paramString)
  {
    return l.b(g(paramString));
  }
  
  public f[] o(String paramString)
  {
    return g((String)null, paramString).a();
  }
  
  void p(String paramString)
  {
    if ((this.P == null) || ((paramString != null) && (!this.Q.equals(paramString)))) {
      if (paramString != null)
      {
        this.P = this.I.a(paramString);
        this.Q = paramString;
      }
      else
      {
        paramString = this.R;
        if ((paramString != null) && (paramString.a().length() > 0))
        {
          this.P = this.I.a(this.R);
          this.Q = this.R.a();
        }
        else
        {
          Object localObject1 = System.getProperty("org.apache.commons.net.ftp.systemType");
          paramString = (String)localObject1;
          if (localObject1 == null)
          {
            localObject1 = t();
            Object localObject2 = w();
            paramString = (String)localObject1;
            if (localObject2 != null)
            {
              localObject2 = ((Properties)localObject2).getProperty((String)localObject1);
              paramString = (String)localObject1;
              if (localObject2 != null) {
                paramString = (String)localObject2;
              }
            }
          }
          localObject1 = this.R;
          if (localObject1 != null) {
            this.P = this.I.a(new d(paramString, (d)localObject1));
          } else {
            this.P = this.I.a(paramString);
          }
          this.Q = paramString;
        }
      }
    }
  }
  
  protected String q(String paramString)
  {
    if (u())
    {
      if (paramString != null)
      {
        StringBuilder localStringBuilder = new StringBuilder(paramString.length() + 3);
        localStringBuilder.append("-a ");
        localStringBuilder.append(paramString);
        return localStringBuilder.toString();
      }
      return "-a";
    }
    return paramString;
  }
  
  public void r()
  {
    this.s = 2;
    this.v = null;
    this.u = -1;
  }
  
  public boolean s()
  {
    return l.b(i());
  }
  
  public String t()
  {
    if (this.O == null) {
      if (l.b(o()))
      {
        this.O = ((String)this.k.get(this.k.size() - 1)).substring(4);
      }
      else
      {
        Object localObject = System.getProperty("org.apache.commons.net.ftp.systemType.default");
        if (localObject != null)
        {
          this.O = ((String)localObject);
        }
        else
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Unable to determine system type - response: ");
          ((StringBuilder)localObject).append(k());
          throw new IOException(((StringBuilder)localObject).toString());
        }
      }
    }
    return this.O;
  }
  
  public boolean u()
  {
    return this.M;
  }
  
  public boolean v()
  {
    return this.N;
  }
  
  public static abstract interface a
  {
    public abstract String a(String paramString);
  }
  
  public static class b
    implements c.a
  {
    private c a;
    
    public b(c paramc)
    {
      this.a = paramc;
    }
    
    public String a(String paramString)
    {
      String str = paramString;
      if (InetAddress.getByName(paramString).isSiteLocalAddress())
      {
        InetAddress localInetAddress = this.a.e();
        str = paramString;
        if (!localInetAddress.isSiteLocalAddress()) {
          str = localInetAddress.getHostAddress();
        }
      }
      return str;
    }
  }
  
  private static class c
  {
    static final Properties a;
    
    /* Error */
    static
    {
      // Byte code:
      //   0: ldc 6
      //   2: ldc 15
      //   4: invokevirtual 21	java/lang/Class:getResourceAsStream	(Ljava/lang/String;)Ljava/io/InputStream;
      //   7: astore_0
      //   8: aload_0
      //   9: ifnull +30 -> 39
      //   12: new 23	java/util/Properties
      //   15: dup
      //   16: invokespecial 26	java/util/Properties:<init>	()V
      //   19: astore_1
      //   20: aload_1
      //   21: aload_0
      //   22: invokevirtual 30	java/util/Properties:load	(Ljava/io/InputStream;)V
      //   25: aload_0
      //   26: invokevirtual 35	java/io/InputStream:close	()V
      //   29: goto +12 -> 41
      //   32: astore_1
      //   33: aload_0
      //   34: invokevirtual 35	java/io/InputStream:close	()V
      //   37: aload_1
      //   38: athrow
      //   39: aconst_null
      //   40: astore_1
      //   41: aload_1
      //   42: putstatic 37	org/apache/commons/net/ftp/c$c:a	Ljava/util/Properties;
      //   45: return
      //   46: astore_2
      //   47: goto -22 -> 25
      //   50: astore_0
      //   51: goto -10 -> 41
      //   54: astore_0
      //   55: goto -18 -> 37
      // Local variable table:
      //   start	length	slot	name	signature
      //   7	27	0	localInputStream	java.io.InputStream
      //   50	1	0	localIOException1	IOException
      //   54	1	0	localIOException2	IOException
      //   19	2	1	localProperties1	Properties
      //   32	6	1	localObject	Object
      //   40	2	1	localProperties2	Properties
      //   46	1	2	localIOException3	IOException
      // Exception table:
      //   from	to	target	type
      //   20	25	32	finally
      //   20	25	46	java/io/IOException
      //   25	29	50	java/io/IOException
      //   33	37	54	java/io/IOException
    }
  }
}


/* Location:              ~/org/apache/commons/net/ftp/c.class
 *
 * Reversed by:           J
 */