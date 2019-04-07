package com.app.system.streaming.a;

import android.media.MediaRecorder.OutputFormat;
import java.lang.reflect.Field;

public class b
  extends d
{
  public b()
  {
    this.a = new com.app.system.streaming.f.c();
    c(5);
    try
    {
      e(MediaRecorder.OutputFormat.class.getField("RAW_AMR").getInt(null));
    }
    catch (Exception localException)
    {
      e(3);
    }
    d(1);
  }
  
  public void d()
  {
    try
    {
      super.d();
      this.c = ((byte)1);
      this.y = this.x.a();
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
  
  protected void h()
  {
    super.g();
  }
  
  public String i()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("m=audio ");
    localStringBuilder.append(String.valueOf(a()[0]));
    localStringBuilder.append(" RTP/AVP 96\r\na=rtpmap:96 AMR/8000\r\na=fmtp:96 octet-align=1;\r\n");
    return localStringBuilder.toString();
  }
}


/* Location:              ~/com/app/system/streaming/a/b.class
 *
 * Reversed by:           J
 */