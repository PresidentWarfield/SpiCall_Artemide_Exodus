package com.app.system.streaming.h;

import com.app.system.streaming.f.e;

public class a
  extends d
{
  public a()
  {
    this(0);
  }
  
  public a(int paramInt)
  {
    super(paramInt);
    this.M = 17;
    this.y = 1;
    this.a = new e();
  }
  
  public void d()
  {
    try
    {
      super.d();
      this.c = ((byte)1);
      this.u = this.t.a();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void e()
  {
    try
    {
      if (!this.f)
      {
        d();
        super.e();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String i()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("m=video ");
    localStringBuilder.append(String.valueOf(a()[0]));
    localStringBuilder.append(" RTP/AVP 96\r\na=rtpmap:96 H263-1998/90000\r\n");
    return localStringBuilder.toString();
  }
}


/* Location:              ~/com/app/system/streaming/h/a.class
 *
 * Reversed by:           J
 */