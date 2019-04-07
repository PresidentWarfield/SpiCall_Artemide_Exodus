package com.security.c;

import java.io.File;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.net.ftp.c;
import org.apache.commons.net.ftp.f;
import org.apache.commons.net.ftp.l;

public class a
{
  private static final SimpleDateFormat b = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
  public c a = new c();
  
  public b a(String paramString, File paramFile, c paramc, long paramLong)
  {
    RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramFile, "r");
    Object localObject = paramc.k(new String(paramString.getBytes("GBK"), "iso-8859-1"));
    if (paramLong > 0L)
    {
      paramc.b(paramLong);
      localRandomAccessFile.seek(paramLong);
    }
    byte[] arrayOfByte = new byte['Ѐ'];
    for (;;)
    {
      int i = localRandomAccessFile.read(arrayOfByte);
      if (i == -1) {
        break;
      }
      ((OutputStream)localObject).write(arrayOfByte, 0, i);
    }
    ((OutputStream)localObject).flush();
    localRandomAccessFile.close();
    ((OutputStream)localObject).close();
    boolean bool = paramc.s();
    if (paramLong > 0L)
    {
      if (bool) {
        localObject = b.g;
      } else {
        localObject = b.h;
      }
    }
    else if (bool) {
      localObject = b.c;
    } else {
      localObject = b.d;
    }
    if ((localObject == b.c) || (localObject == b.g))
    {
      paramLong = paramFile.lastModified();
      if (!paramc.h(paramString, b.format(new Date(paramLong)))) {
        com.security.d.a.b("SmartFTPClient", "SET FILE TIME FALLITA", new Object[0]);
      }
    }
    paramString = new StringBuilder();
    paramString.append("UPLOAD FILE: ");
    paramString.append(paramFile.getName());
    paramString.append(" EXIT WITH");
    paramString.append(((b)localObject).name());
    com.security.d.a.d("SmartFTPClient", paramString.toString(), new Object[0]);
    return (b)localObject;
  }
  
  public b a(String paramString1, String paramString2)
  {
    com.security.d.a.d("TAG", "UPLOAD FILE.... Stand-by", new Object[0]);
    this.a.r();
    this.a.c(2);
    this.a.a("GBK");
    String str = paramString2;
    if (paramString2.contains("/"))
    {
      str = paramString2.substring(paramString2.lastIndexOf("/") + 1);
      if (a(paramString2, this.a) == b.a) {
        return b.a;
      }
    }
    paramString2 = this.a.o(new String(str.getBytes("GBK"), "iso-8859-1"));
    if (paramString2.length == 1)
    {
      long l1 = paramString2[0].c();
      File localFile = new File(paramString1);
      long l2 = localFile.length();
      if (l1 == l2)
      {
        com.security.d.a.d("TAG", "UPLOAD FILE.... STOP / FILE-EXISTS", new Object[0]);
        return b.e;
      }
      if (l1 > l2)
      {
        com.security.d.a.d("TAG", "UPLOAD FILE....STOP / Remote_bigger_local", new Object[0]);
        return b.f;
      }
      paramString2 = a(str, localFile, this.a, l1);
      paramString1 = paramString2;
      if (paramString2 == b.h)
      {
        if (!this.a.m(str)) {
          return b.i;
        }
        paramString1 = a(str, localFile, this.a, 0L);
      }
    }
    else
    {
      paramString1 = a(str, new File(paramString1), this.a, 0L);
    }
    paramString2 = new StringBuilder();
    paramString2.append("UPLOAD FILE....COMPLETED WITH:");
    paramString2.append(paramString1.name());
    com.security.d.a.d("TAG", paramString2.toString(), new Object[0]);
    return paramString1;
  }
  
  public b a(String paramString, c paramc)
  {
    b localb = b.b;
    String str1 = paramString.substring(0, paramString.lastIndexOf("/") + 1);
    if ((!str1.equalsIgnoreCase("/")) && (!paramc.j(new String(str1.getBytes("GBK"), "iso-8859-1"))))
    {
      int i = str1.startsWith("/");
      int j = str1.indexOf("/", i);
      int k;
      int m;
      do
      {
        String str2 = new String(paramString.substring(i, j).getBytes("GBK"), "iso-8859-1");
        if (!paramc.j(str2)) {
          if (paramc.n(str2)) {
            paramc.j(str2);
          } else {
            return b.a;
          }
        }
        k = j + 1;
        m = str1.indexOf("/", k);
        i = k;
        j = m;
      } while (m > k);
    }
    return localb;
  }
  
  public void a()
  {
    if (this.a.c()) {
      this.a.b();
    }
  }
  
  public boolean a(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    com.security.d.a.d("TAG", "Connessione al server FTP... stand-by", new Object[0]);
    this.a.a(paramString1, paramInt);
    this.a.a("GBK");
    if ((l.b(this.a.h())) && (this.a.f(paramString2, paramString3)))
    {
      com.security.d.a.d("TAG", "Connessione al server FTP... CONNESSO", new Object[0]);
      return true;
    }
    a();
    com.security.d.a.d("TAG", "Connessione al server FTP... FALLITA", new Object[0]);
    return false;
  }
}


/* Location:              ~/com/security/c/a.class
 *
 * Reversed by:           J
 */