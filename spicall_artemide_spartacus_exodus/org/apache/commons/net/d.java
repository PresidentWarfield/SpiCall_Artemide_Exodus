package org.apache.commons.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;

public abstract class d
{
  private static final SocketFactory j = ;
  private static final ServerSocketFactory k = ServerSocketFactory.getDefault();
  protected int a = 0;
  protected Socket b = null;
  protected String c = null;
  protected int d = 0;
  protected InputStream e = null;
  protected OutputStream f = null;
  protected SocketFactory g = j;
  protected ServerSocketFactory h = k;
  protected int i = 0;
  private c l;
  private int m = -1;
  private int n = -1;
  private Charset o = Charset.defaultCharset();
  
  private void a(Closeable paramCloseable)
  {
    if (paramCloseable != null) {}
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException paramCloseable)
    {
      for (;;) {}
    }
  }
  
  private void a(InetAddress paramInetAddress1, int paramInt1, InetAddress paramInetAddress2, int paramInt2)
  {
    this.b = this.g.createSocket();
    int i1 = this.m;
    if (i1 != -1) {
      this.b.setReceiveBufferSize(i1);
    }
    i1 = this.n;
    if (i1 != -1) {
      this.b.setSendBufferSize(i1);
    }
    if (paramInetAddress2 != null) {
      this.b.bind(new InetSocketAddress(paramInetAddress2, paramInt2));
    }
    this.b.connect(new InetSocketAddress(paramInetAddress1, paramInt1), this.i);
    a();
  }
  
  private void b(Socket paramSocket)
  {
    if (paramSocket != null) {}
    try
    {
      paramSocket.close();
      return;
    }
    catch (IOException paramSocket)
    {
      for (;;) {}
    }
  }
  
  protected void a()
  {
    this.b.setSoTimeout(this.a);
    this.e = this.b.getInputStream();
    this.f = this.b.getOutputStream();
  }
  
  public void a(int paramInt)
  {
    this.d = paramInt;
  }
  
  protected void a(int paramInt, String paramString)
  {
    if (f().a() > 0) {
      f().a(paramInt, paramString);
    }
  }
  
  public void a(String paramString, int paramInt)
  {
    this.c = paramString;
    a(InetAddress.getByName(paramString), paramInt, null, -1);
  }
  
  protected void a(String paramString1, String paramString2)
  {
    if (f().a() > 0) {
      f().a(paramString1, paramString2);
    }
  }
  
  public boolean a(Socket paramSocket)
  {
    return paramSocket.getInetAddress().equals(e());
  }
  
  public void b()
  {
    b(this.b);
    a(this.e);
    a(this.f);
    this.b = null;
    this.c = null;
    this.e = null;
    this.f = null;
  }
  
  public boolean c()
  {
    Socket localSocket = this.b;
    if (localSocket == null) {
      return false;
    }
    return localSocket.isConnected();
  }
  
  public InetAddress d()
  {
    return this.b.getLocalAddress();
  }
  
  public InetAddress e()
  {
    return this.b.getInetAddress();
  }
  
  protected c f()
  {
    return this.l;
  }
}


/* Location:              ~/org/apache/commons/net/d.class
 *
 * Reversed by:           J
 */