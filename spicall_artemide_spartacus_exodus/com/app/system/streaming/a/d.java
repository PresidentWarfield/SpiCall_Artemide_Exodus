package com.app.system.streaming.a;

import android.media.MediaRecorder;
import android.net.LocalSocket;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

public abstract class d
  extends com.app.system.streaming.a
{
  protected int u;
  protected int v;
  protected int w;
  protected c x = c.a.a();
  protected c y = this.x.a();
  
  public d()
  {
    c(5);
  }
  
  public void a(c paramc)
  {
    this.x = paramc;
  }
  
  public void c(int paramInt)
  {
    this.u = paramInt;
  }
  
  protected void d(int paramInt)
  {
    this.w = paramInt;
  }
  
  protected void e(int paramInt)
  {
    this.v = paramInt;
  }
  
  protected void g()
  {
    j();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Requested audio with ");
    ((StringBuilder)localObject).append(this.y.c / 1000);
    ((StringBuilder)localObject).append("kbps at ");
    ((StringBuilder)localObject).append(this.y.b / 1000);
    ((StringBuilder)localObject).append("kHz");
    com.security.d.a.e("MediaStream", ((StringBuilder)localObject).toString(), new Object[0]);
    this.r = new MediaRecorder();
    this.r.setAudioSource(this.u);
    this.r.setOutputFormat(this.v);
    this.r.setAudioEncoder(this.w);
    this.r.setAudioChannels(1);
    this.r.setAudioSamplingRate(this.y.b);
    this.r.setAudioEncodingBitRate(this.y.c);
    if (e == 2) {
      localObject = this.o.getFileDescriptor();
    } else {
      localObject = this.q.getFileDescriptor();
    }
    this.r.setOutputFile((FileDescriptor)localObject);
    this.r.setOutputFile((FileDescriptor)localObject);
    this.r.prepare();
    this.r.start();
    if (e == 2) {
      localObject = new ParcelFileDescriptor.AutoCloseInputStream(this.n);
    }
    try
    {
      localObject = this.p.getInputStream();
      this.a.a((InputStream)localObject);
      this.a.a();
      this.f = true;
      return;
    }
    catch (IOException localIOException)
    {
      f();
      throw new IOException("Something happened with the local sockets :/ Start failed !");
    }
  }
}


/* Location:              ~/com/app/system/streaming/a/d.class
 *
 * Reversed by:           J
 */