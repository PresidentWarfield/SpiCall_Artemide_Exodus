package com.app.system.streaming.d;

import android.util.Base64;
import java.io.IOException;

public class a
{
  private b a;
  private String b;
  private String c;
  private String d;
  
  public a(String paramString)
  {
    try
    {
      this.a = b.a(paramString);
      paramString = this.a.b();
      this.c = paramString.b();
      this.d = paramString.c();
      this.b = paramString.a();
      this.a.a();
      return;
    }
    catch (IOException paramString)
    {
      for (;;) {}
    }
  }
  
  public a(String paramString1, String paramString2)
  {
    this.c = paramString2;
    this.d = paramString1;
    this.b = b.a(Base64.decode(paramString1, 2), 1, 3);
  }
  
  public a(String paramString1, String paramString2, String paramString3)
  {
    this.b = paramString1;
    this.c = paramString3;
    this.d = paramString2;
  }
  
  public String a()
  {
    return this.b;
  }
  
  public String b()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("PPS: ");
    localStringBuilder.append(this.c);
    com.security.d.a.d("MP4Config", localStringBuilder.toString(), new Object[0]);
    return this.c;
  }
  
  public String c()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("SPS: ");
    localStringBuilder.append(this.d);
    com.security.d.a.d("MP4Config", localStringBuilder.toString(), new Object[0]);
    return this.d;
  }
}


/* Location:              ~/com/app/system/streaming/d/a.class
 *
 * Reversed by:           J
 */