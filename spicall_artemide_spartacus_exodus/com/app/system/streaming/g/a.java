package com.app.system.streaming.g;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.app.system.streaming.b;
import com.app.system.streaming.d;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class a
{
  protected static final char[] a = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  private int b = 0;
  private b c = new b(null);
  private b d;
  private int e = 0;
  private Socket f;
  private String g;
  private String h;
  private BufferedReader i;
  private OutputStream j;
  private a k;
  private Handler l;
  private Handler m;
  private Runnable n = new Runnable()
  {
    public void run()
    {
      if (a.a(a.this) == 0) {
        try
        {
          a.h(a.this);
          a.g(a.this).postDelayed(a.f(a.this), 6000L);
        }
        catch (IOException localIOException)
        {
          a.b(a.this, 4);
          com.security.d.a.a("RtspClient", "Connection lost with the server...", new Object[0]);
          a.c(a.this).e.k();
          a.g(a.this).post(a.i(a.this));
        }
      }
    }
  };
  private Runnable o = new Runnable()
  {
    public void run()
    {
      if (a.a(a.this) == 0) {
        try
        {
          com.security.d.a.a("RtspClient", "Trying to reconnect...", new Object[0]);
          a.d(a.this);
          try
          {
            a.c(a.this).e.i();
            a.g(a.this).post(a.f(a.this));
            a.b(a.this, 5);
          }
          catch (Exception localException)
          {
            a.e(a.this);
          }
          return;
        }
        catch (IOException localIOException)
        {
          a.g(a.this).postDelayed(a.i(a.this), 1000L);
        }
      }
    }
  };
  
  public a()
  {
    final Object localObject = this.c;
    ((b)localObject).f = 1935;
    ((b)localObject).d = "/";
    ((b)localObject).g = 0;
    this.h = null;
    this.k = null;
    this.l = new Handler(Looper.getMainLooper());
    this.b = 3;
    localObject = new Semaphore(0);
    new HandlerThread("com.app.system.streaming.RtspClient")
    {
      protected void onLooperPrepared()
      {
        a.a(a.this, new Handler());
        localObject.release();
      }
    }.start();
    ((Semaphore)localObject).acquireUninterruptibly();
  }
  
  private static String a(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar1 = new char[paramArrayOfByte.length * 2];
    for (int i1 = 0; i1 < paramArrayOfByte.length; i1++)
    {
      int i2 = paramArrayOfByte[i1] & 0xFF;
      int i3 = i1 * 2;
      char[] arrayOfChar2 = a;
      arrayOfChar1[i3] = ((char)arrayOfChar2[(i2 >>> 4)]);
      arrayOfChar1[(i3 + 1)] = ((char)arrayOfChar2[(i2 & 0xF)]);
    }
    return new String(arrayOfChar1);
  }
  
  private void a(final int paramInt)
  {
    this.l.post(new Runnable()
    {
      public void run()
      {
        if (a.j(a.this) != null) {
          a.j(a.this).a(paramInt, null);
        }
      }
    });
  }
  
  private void a(final int paramInt, final Exception paramException)
  {
    this.l.post(new Runnable()
    {
      public void run()
      {
        if (a.j(a.this) != null) {
          a.j(a.this).a(paramInt, paramException);
        }
      }
    });
  }
  
  private String b(String paramString)
  {
    try
    {
      paramString = a(MessageDigest.getInstance("MD5").digest(paramString.getBytes("UTF-8")));
      return paramString;
    }
    catch (NoSuchAlgorithmException|UnsupportedEncodingException paramString) {}
    return "";
  }
  
  private void d()
  {
    try
    {
      i();
    }
    catch (Exception localException1)
    {
      try
      {
        for (;;)
        {
          this.f.close();
          this.m.removeCallbacks(this.n);
          this.m.removeCallbacks(this.o);
          this.b = 3;
          return;
          localException1 = localException1;
        }
      }
      catch (Exception localException2)
      {
        for (;;) {}
      }
    }
  }
  
  private void e()
  {
    this.e = 0;
    this.f = new Socket(this.d.a, this.d.f);
    this.i = new BufferedReader(new InputStreamReader(this.f.getInputStream()));
    this.j = new BufferedOutputStream(this.f.getOutputStream());
    f();
    g();
    h();
  }
  
  private void f()
  {
    String str = this.d.e.e();
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("ANNOUNCE rtsp://");
    ((StringBuilder)localObject2).append(this.d.a);
    ((StringBuilder)localObject2).append(":");
    ((StringBuilder)localObject2).append(this.d.f);
    ((StringBuilder)localObject2).append(this.d.d);
    ((StringBuilder)localObject2).append(" RTSP/1.0\r\nCSeq: ");
    int i1 = this.e + 1;
    this.e = i1;
    ((StringBuilder)localObject2).append(i1);
    ((StringBuilder)localObject2).append("\r\nContent-Length: ");
    ((StringBuilder)localObject2).append(str.length());
    ((StringBuilder)localObject2).append("\r\nContent-Type: application/sdp\r\n\r\n");
    ((StringBuilder)localObject2).append(str);
    localObject2 = ((StringBuilder)localObject2).toString();
    com.security.d.a.c("RtspClient", ((String)localObject2).substring(0, ((String)localObject2).indexOf("\r\n")), new Object[0]);
    this.j.write(((String)localObject2).getBytes("UTF-8"));
    this.j.flush();
    localObject2 = c.a(this.i);
    Object localObject3;
    if (((c)localObject2).g.containsKey("server"))
    {
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("RTSP server name:");
      ((StringBuilder)localObject3).append((String)((c)localObject2).g.get("server"));
      com.security.d.a.e("RtspClient", ((StringBuilder)localObject3).toString(), new Object[0]);
    }
    else
    {
      com.security.d.a.e("RtspClient", "RTSP server name unknown", new Object[0]);
    }
    Object localObject1;
    if (((c)localObject2).g.containsKey("session")) {
      try
      {
        localObject3 = c.d.matcher((CharSequence)((c)localObject2).g.get("session"));
        ((Matcher)localObject3).find();
        this.g = ((Matcher)localObject3).group(1);
      }
      catch (Exception localException1)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Invalid response from server. Session id: ");
        ((StringBuilder)localObject1).append(this.g);
        throw new IOException(((StringBuilder)localObject1).toString());
      }
    }
    if (((c)localObject2).f == 401)
    {
      if ((this.d.b != null) && (this.d.c != null)) {
        try
        {
          Object localObject4 = c.c.matcher((CharSequence)((c)localObject2).g.get("www-authenticate"));
          ((Matcher)localObject4).find();
          localObject2 = ((Matcher)localObject4).group(2);
          localObject3 = ((Matcher)localObject4).group(1);
          Object localObject5 = new StringBuilder();
          ((StringBuilder)localObject5).append("rtsp://");
          ((StringBuilder)localObject5).append(this.d.a);
          ((StringBuilder)localObject5).append(":");
          ((StringBuilder)localObject5).append(this.d.f);
          ((StringBuilder)localObject5).append(this.d.d);
          localObject5 = ((StringBuilder)localObject5).toString();
          Object localObject6 = new StringBuilder();
          ((StringBuilder)localObject6).append(this.d.b);
          ((StringBuilder)localObject6).append(":");
          ((StringBuilder)localObject6).append(((Matcher)localObject4).group(1));
          ((StringBuilder)localObject6).append(":");
          ((StringBuilder)localObject6).append(this.d.c);
          localObject6 = b(((StringBuilder)localObject6).toString());
          Object localObject7 = new StringBuilder();
          ((StringBuilder)localObject7).append("ANNOUNCE:");
          ((StringBuilder)localObject7).append((String)localObject5);
          localObject7 = b(((StringBuilder)localObject7).toString());
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append((String)localObject6);
          localStringBuilder.append(":");
          localStringBuilder.append(((Matcher)localObject4).group(2));
          localStringBuilder.append(":");
          localStringBuilder.append((String)localObject7);
          localObject6 = b(localStringBuilder.toString());
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append("Digest username=\"");
          ((StringBuilder)localObject4).append(this.d.b);
          ((StringBuilder)localObject4).append("\",realm=\"");
          ((StringBuilder)localObject4).append((String)localObject3);
          ((StringBuilder)localObject4).append("\",nonce=\"");
          ((StringBuilder)localObject4).append((String)localObject2);
          ((StringBuilder)localObject4).append("\",uri=\"");
          ((StringBuilder)localObject4).append((String)localObject5);
          ((StringBuilder)localObject4).append("\",response=\"");
          ((StringBuilder)localObject4).append((String)localObject6);
          ((StringBuilder)localObject4).append("\"");
          this.h = ((StringBuilder)localObject4).toString();
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("ANNOUNCE rtsp://");
          ((StringBuilder)localObject2).append(this.d.a);
          ((StringBuilder)localObject2).append(":");
          ((StringBuilder)localObject2).append(this.d.f);
          ((StringBuilder)localObject2).append(this.d.d);
          ((StringBuilder)localObject2).append(" RTSP/1.0\r\nCSeq: ");
          i1 = this.e + 1;
          this.e = i1;
          ((StringBuilder)localObject2).append(i1);
          ((StringBuilder)localObject2).append("\r\nContent-Length: ");
          ((StringBuilder)localObject2).append(((String)localObject1).length());
          ((StringBuilder)localObject2).append("\r\nAuthorization: ");
          ((StringBuilder)localObject2).append(this.h);
          ((StringBuilder)localObject2).append("\r\nSession: ");
          ((StringBuilder)localObject2).append(this.g);
          ((StringBuilder)localObject2).append("\r\nContent-Type: application/sdp\r\n\r\n");
          ((StringBuilder)localObject2).append((String)localObject1);
          localObject1 = ((StringBuilder)localObject2).toString();
          com.security.d.a.c("RtspClient", ((String)localObject1).substring(0, ((String)localObject1).indexOf("\r\n")), new Object[0]);
          this.j.write(((String)localObject1).getBytes("UTF-8"));
          this.j.flush();
          if (c.a(this.i).f != 401) {
            break label1056;
          }
          throw new RuntimeException("Bad credentials !");
        }
        catch (Exception localException2)
        {
          throw new IOException("Invalid response from server");
        }
      }
      throw new IllegalStateException("Authentication is enabled and setCredentials(String,String) was not called !");
    }
    if (((c)localObject2).f != 403) {
      label1056:
      return;
    }
    throw new RuntimeException("Access forbidden !");
  }
  
  private void g()
  {
    for (int i1 = 0; i1 < 2; i1++)
    {
      Object localObject1 = this.d.e.c(i1);
      if (localObject1 != null)
      {
        int i2;
        if (this.d.g == 1)
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("TCP;interleaved=");
          i2 = i1 * 2;
          ((StringBuilder)localObject2).append(i2);
          ((StringBuilder)localObject2).append("-");
          ((StringBuilder)localObject2).append(i2 + 1);
        }
        else
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("UDP;unicast;client_port=");
          i2 = i1 * 2 + 5000;
          ((StringBuilder)localObject2).append(i2);
          ((StringBuilder)localObject2).append("-");
          ((StringBuilder)localObject2).append(i2 + 1);
          ((StringBuilder)localObject2).append(";mode=receive");
        }
        Object localObject3 = ((StringBuilder)localObject2).toString();
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("SETUP rtsp://");
        ((StringBuilder)localObject2).append(this.d.a);
        ((StringBuilder)localObject2).append(":");
        ((StringBuilder)localObject2).append(this.d.f);
        ((StringBuilder)localObject2).append(this.d.d);
        ((StringBuilder)localObject2).append("/trackID=");
        ((StringBuilder)localObject2).append(i1);
        ((StringBuilder)localObject2).append(" RTSP/1.0\r\nTransport: RTP/AVP/");
        ((StringBuilder)localObject2).append((String)localObject3);
        ((StringBuilder)localObject2).append("\r\n");
        ((StringBuilder)localObject2).append(k());
        localObject2 = ((StringBuilder)localObject2).toString();
        com.security.d.a.c("RtspClient", ((String)localObject2).substring(0, ((String)localObject2).indexOf("\r\n")), new Object[0]);
        this.j.write(((String)localObject2).getBytes("UTF-8"));
        this.j.flush();
        localObject2 = c.a(this.i);
        StringBuilder localStringBuilder;
        if (((c)localObject2).g.containsKey("session")) {
          try
          {
            localObject3 = c.d.matcher((CharSequence)((c)localObject2).g.get("session"));
            ((Matcher)localObject3).find();
            this.g = ((Matcher)localObject3).group(1);
          }
          catch (Exception localException1)
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("Invalid response from server. Session id: ");
            localStringBuilder.append(this.g);
            throw new IOException(localStringBuilder.toString());
          }
        }
        if (this.d.g == 0) {
          try
          {
            localObject3 = c.e.matcher((CharSequence)localStringBuilder.g.get("transport"));
            ((Matcher)localObject3).find();
            ((d)localObject1).a(Integer.parseInt(((Matcher)localObject3).group(3)), Integer.parseInt(((Matcher)localObject3).group(4)));
            localStringBuilder = new java/lang/StringBuilder;
            localStringBuilder.<init>();
            localStringBuilder.append("Setting destination ports: ");
            localStringBuilder.append(Integer.parseInt(((Matcher)localObject3).group(3)));
            localStringBuilder.append(", ");
            localStringBuilder.append(Integer.parseInt(((Matcher)localObject3).group(4)));
            com.security.d.a.d("RtspClient", localStringBuilder.toString(), new Object[0]);
          }
          catch (Exception localException2)
          {
            localException2.printStackTrace();
            int[] arrayOfInt = ((d)localObject1).a();
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("Server did not specify ports, using default ports: ");
            ((StringBuilder)localObject1).append(arrayOfInt[0]);
            ((StringBuilder)localObject1).append("-");
            ((StringBuilder)localObject1).append(arrayOfInt[1]);
            com.security.d.a.d("RtspClient", ((StringBuilder)localObject1).toString(), new Object[0]);
          }
        } else {
          ((d)localObject1).a(this.j, (byte)(i1 * 2));
        }
      }
    }
  }
  
  private void h()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("RECORD rtsp://");
    ((StringBuilder)localObject).append(this.d.a);
    ((StringBuilder)localObject).append(":");
    ((StringBuilder)localObject).append(this.d.f);
    ((StringBuilder)localObject).append(this.d.d);
    ((StringBuilder)localObject).append(" RTSP/1.0\r\nRange: npt=0.000-\r\n");
    ((StringBuilder)localObject).append(k());
    localObject = ((StringBuilder)localObject).toString();
    com.security.d.a.c("RtspClient", ((String)localObject).substring(0, ((String)localObject).indexOf("\r\n")), new Object[0]);
    this.j.write(((String)localObject).getBytes("UTF-8"));
    this.j.flush();
    c.a(this.i);
  }
  
  private void i()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("TEARDOWN rtsp://");
    ((StringBuilder)localObject).append(this.d.a);
    ((StringBuilder)localObject).append(":");
    ((StringBuilder)localObject).append(this.d.f);
    ((StringBuilder)localObject).append(this.d.d);
    ((StringBuilder)localObject).append(" RTSP/1.0\r\n");
    ((StringBuilder)localObject).append(k());
    localObject = ((StringBuilder)localObject).toString();
    com.security.d.a.c("RtspClient", ((String)localObject).substring(0, ((String)localObject).indexOf("\r\n")), new Object[0]);
    this.j.write(((String)localObject).getBytes("UTF-8"));
    this.j.flush();
  }
  
  private void j()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("OPTIONS rtsp://");
    ((StringBuilder)localObject).append(this.d.a);
    ((StringBuilder)localObject).append(":");
    ((StringBuilder)localObject).append(this.d.f);
    ((StringBuilder)localObject).append(this.d.d);
    ((StringBuilder)localObject).append(" RTSP/1.0\r\n");
    ((StringBuilder)localObject).append(k());
    localObject = ((StringBuilder)localObject).toString();
    com.security.d.a.c("RtspClient", ((String)localObject).substring(0, ((String)localObject).indexOf("\r\n")), new Object[0]);
    this.j.write(((String)localObject).getBytes("UTF-8"));
    this.j.flush();
    c.a(this.i);
  }
  
  private String k()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CSeq: ");
    int i1 = this.e + 1;
    this.e = i1;
    localStringBuilder.append(i1);
    localStringBuilder.append("\r\nContent-Length: 0\r\nSession: ");
    localStringBuilder.append(this.g);
    localStringBuilder.append("\r\n");
    Object localObject;
    if (this.h != null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Authorization: ");
      ((StringBuilder)localObject).append(this.h);
      ((StringBuilder)localObject).append("\r\n");
      localObject = ((StringBuilder)localObject).toString();
    }
    else
    {
      localObject = "";
    }
    localStringBuilder.append((String)localObject);
    localStringBuilder.append("\r\n");
    return localStringBuilder.toString();
  }
  
  public void a(b paramb)
  {
    this.c.e = paramb;
  }
  
  public void a(String paramString)
  {
    this.c.d = paramString;
  }
  
  public void a(String paramString, int paramInt)
  {
    b localb = this.c;
    localb.f = paramInt;
    localb.a = paramString;
  }
  
  public void a(String paramString1, String paramString2)
  {
    b localb = this.c;
    localb.b = paramString1;
    localb.c = paramString2;
  }
  
  public boolean a()
  {
    int i1 = this.b;
    boolean bool1 = true;
    boolean bool2 = bool1;
    if (i1 != 0) {
      if (i1 == 1) {
        bool2 = bool1;
      } else {
        bool2 = false;
      }
    }
    return bool2;
  }
  
  public void b()
  {
    if (this.c.a != null)
    {
      if (this.c.e != null)
      {
        this.m.post(new Runnable()
        {
          public void run()
          {
            if (a.a(a.this) != 3) {
              return;
            }
            a.a(a.this, 1);
            com.security.d.a.d("RtspClient", "Connecting to RTSP server...", new Object[0]);
            a locala = a.this;
            a.a(locala, a.b(locala).a());
            a.c(a.this).e.b(a.b(a.this).a);
            try
            {
              a.c(a.this).e.h();
              try
              {
                a.d(a.this);
                try
                {
                  a.c(a.this).e.j();
                  a.a(a.this, 0);
                  if (a.c(a.this).g == 0) {
                    a.g(a.this).post(a.f(a.this));
                  }
                }
                catch (Exception localException1)
                {
                  a.e(a.this);
                }
                return;
              }
              catch (Exception localException2)
              {
                a.a(a.this, 1, localException2);
                a.e(a.this);
                return;
              }
              return;
            }
            catch (Exception localException3)
            {
              a.c(a.this).e = null;
              a.a(a.this, 3);
            }
          }
        });
        return;
      }
      throw new IllegalStateException("setSession() has not been called !");
    }
    throw new IllegalStateException("setServerAddress(String,int) has not been called !");
  }
  
  public void c()
  {
    this.m.post(new Runnable()
    {
      public void run()
      {
        if ((a.c(a.this) != null) && (a.c(a.this).e != null)) {
          a.c(a.this).e.k();
        }
        if (a.a(a.this) != 3)
        {
          a.a(a.this, 2);
          a.e(a.this);
        }
      }
    });
  }
  
  public static abstract interface a
  {
    public abstract void a(int paramInt, Exception paramException);
  }
  
  private class b
  {
    public String a;
    public String b;
    public String c;
    public String d;
    public b e;
    public int f;
    public int g;
    
    private b() {}
    
    public b a()
    {
      b localb = new b(a.this);
      localb.a = this.a;
      localb.b = this.b;
      localb.c = this.c;
      localb.d = this.d;
      localb.e = this.e;
      localb.f = this.f;
      localb.g = this.g;
      return localb;
    }
  }
  
  static class c
  {
    public static final Pattern a = Pattern.compile("RTSP/\\d.\\d (\\d+) (\\w+)", 2);
    public static final Pattern b = Pattern.compile("(\\S+):(.+)", 2);
    public static final Pattern c = Pattern.compile("realm=\"(.+)\",\\s+nonce=\"(\\w+)\"", 2);
    public static final Pattern d = Pattern.compile("(\\d+)", 2);
    public static final Pattern e = Pattern.compile("client_port=(\\d+)-(\\d+).+server_port=(\\d+)-(\\d+)", 2);
    public int f;
    public HashMap<String, String> g = new HashMap();
    
    public static c a(BufferedReader paramBufferedReader)
    {
      c localc = new c();
      Object localObject = paramBufferedReader.readLine();
      if (localObject != null)
      {
        localObject = a.matcher((CharSequence)localObject);
        ((Matcher)localObject).find();
        localc.f = Integer.parseInt(((Matcher)localObject).group(1));
        for (;;)
        {
          localObject = paramBufferedReader.readLine();
          if ((localObject == null) || (((String)localObject).length() <= 3)) {
            break;
          }
          localObject = b.matcher((CharSequence)localObject);
          ((Matcher)localObject).find();
          localc.g.put(((Matcher)localObject).group(1).toLowerCase(Locale.US), ((Matcher)localObject).group(2));
        }
        if (localObject != null)
        {
          paramBufferedReader = new StringBuilder();
          paramBufferedReader.append("Response from server: ");
          paramBufferedReader.append(localc.f);
          com.security.d.a.d("RtspClient", paramBufferedReader.toString(), new Object[0]);
          return localc;
        }
        throw new SocketException("Connection lost");
      }
      throw new SocketException("Connection lost");
    }
  }
}


/* Location:              ~/com/app/system/streaming/g/a.class
 *
 * Reversed by:           J
 */