package org.apache.commons.net.ftp;

import java.io.Serializable;
import java.util.Calendar;

public class f
  implements Serializable
{
  private int a;
  private int b;
  private long c;
  private String d;
  private String e;
  private String f;
  private String g;
  private String h;
  private Calendar i;
  private final boolean[][] j;
  
  public f()
  {
    this.j = new boolean[3][3];
    this.a = 3;
    this.b = 0;
    this.c = -1L;
    this.e = "";
    this.f = "";
    this.i = null;
    this.g = null;
  }
  
  f(String paramString)
  {
    this.j = ((boolean[][])null);
    this.d = paramString;
    this.a = 3;
    this.b = 0;
    this.c = -1L;
    this.e = "";
    this.f = "";
    this.i = null;
    this.g = null;
  }
  
  public String a()
  {
    return this.d;
  }
  
  public void a(int paramInt)
  {
    this.a = paramInt;
  }
  
  public void a(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.j[paramInt1][paramInt2] = paramBoolean;
  }
  
  public void a(long paramLong)
  {
    this.c = paramLong;
  }
  
  public void a(String paramString)
  {
    this.d = paramString;
  }
  
  public void a(Calendar paramCalendar)
  {
    this.i = paramCalendar;
  }
  
  public void b(int paramInt)
  {
    this.b = paramInt;
  }
  
  public void b(String paramString)
  {
    this.g = paramString;
  }
  
  public boolean b()
  {
    int k = this.a;
    boolean bool = true;
    if (k != 1) {
      bool = false;
    }
    return bool;
  }
  
  public long c()
  {
    return this.c;
  }
  
  public void c(String paramString)
  {
    this.f = paramString;
  }
  
  public void d(String paramString)
  {
    this.e = paramString;
  }
  
  public void e(String paramString)
  {
    this.h = paramString;
  }
  
  public String toString()
  {
    return a();
  }
}


/* Location:              ~/org/apache/commons/net/ftp/f.class
 *
 * Reversed by:           J
 */