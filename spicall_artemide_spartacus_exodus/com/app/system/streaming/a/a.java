package com.app.system.streaming.a;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OutputFormat;
import android.os.Build.VERSION;
import android.os.Environment;
import com.app.system.streaming.f.b;
import com.app.system.streaming.f.g;
import com.app.system.streaming.f.h;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public class a
  extends d
{
  public static final int[] t = { 96000, 88200, 64000, 48000, 44100, 32000, 24000, 22050, 16000, 12000, 11025, 8000, 7350, -1, -1, -1 };
  private static final String[] z = { "NULL", "AAC Main", "AAC LC (Low Complexity)", "AAC SSR (Scalable Sample Rate)", "AAC LTP (Long Term Prediction)" };
  private String A = null;
  private int B;
  private int C;
  private int D;
  private int E;
  private SharedPreferences F = null;
  private AudioRecord G = null;
  private Thread H = null;
  
  public a()
  {
    if (l())
    {
      com.security.d.a.d("AACStream", "AAC supported on this phone", new Object[0]);
      return;
    }
    com.security.d.a.a("AACStream", "AAC not supported on this phone", new Object[0]);
    throw new RuntimeException("AAC not supported by this phone !");
  }
  
  private static boolean l()
  {
    if (Build.VERSION.SDK_INT < 14) {
      return false;
    }
    try
    {
      MediaRecorder.OutputFormat.class.getField("AAC_ADTS");
      return true;
    }
    catch (Exception localException) {}
    return false;
  }
  
  @SuppressLint({"InlinedApi"})
  private void m()
  {
    d(3);
    try
    {
      e(MediaRecorder.OutputFormat.class.getField("AAC_ADTS").getInt(null));
    }
    catch (Exception localException)
    {
      e(6);
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("libstreaming-aac-");
    ((StringBuilder)localObject1).append(this.y.b);
    localObject1 = ((StringBuilder)localObject1).toString();
    Object localObject2 = this.F;
    if ((localObject2 != null) && (((SharedPreferences)localObject2).contains((String)localObject1)))
    {
      localObject1 = this.F.getString((String)localObject1, "").split(",");
      this.y.b = Integer.valueOf(localObject1[0]).intValue();
      this.E = Integer.valueOf(localObject1[1]).intValue();
      this.D = Integer.valueOf(localObject1[2]).intValue();
      return;
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(Environment.getExternalStorageDirectory().getPath());
    ((StringBuilder)localObject2).append("/spydroid-test.adts");
    Object localObject3 = ((StringBuilder)localObject2).toString();
    Object localObject4;
    if (Environment.getExternalStorageState().equals("mounted"))
    {
      localObject4 = new byte[9];
      this.r = new MediaRecorder();
      this.r.setAudioSource(this.u);
      this.r.setOutputFormat(this.v);
      this.r.setAudioEncoder(this.w);
      this.r.setAudioChannels(1);
      this.r.setAudioSamplingRate(this.y.b);
      this.r.setAudioEncodingBitRate(this.y.c);
      this.r.setOutputFile((String)localObject3);
      this.r.setMaxDuration(1000);
      this.r.prepare();
      this.r.start();
    }
    try
    {
      Thread.sleep(2000L);
      this.r.stop();
      this.r.release();
      this.r = null;
      localObject2 = new File((String)localObject3);
      do
      {
        while ((((RandomAccessFile)localObject3).readByte() & 0xFF) != 255) {}
        localObject4[0] = ((RandomAccessFile)localObject3).readByte();
      } while ((localObject4[0] & 0xF0) != 240);
      ((RandomAccessFile)localObject3).read((byte[])localObject4, 1, 5);
      this.C = ((localObject4[1] & 0x3C) >> 2);
      this.B = (((localObject4[1] & 0xC0) >> 6) + 1);
      int i = localObject4[1];
      this.D = ((localObject4[2] & 0xC0) >> 6 | (i & 0x1) << 2);
      c localc = this.y;
      Object localObject5 = t;
      i = this.C;
      localc.b = localObject5[i];
      this.E = ((this.B & 0x1F) << 11 | (i & 0xF) << 7 | (this.D & 0xF) << 3);
      localObject5 = new StringBuilder();
      ((StringBuilder)localObject5).append("MPEG VERSION: ");
      ((StringBuilder)localObject5).append((localObject4[0] & 0x8) >> 3);
      com.security.d.a.c("AACStream", ((StringBuilder)localObject5).toString(), new Object[0]);
      localObject5 = new StringBuilder();
      ((StringBuilder)localObject5).append("PROTECTION: ");
      ((StringBuilder)localObject5).append(localObject4[0] & 0x1);
      com.security.d.a.c("AACStream", ((StringBuilder)localObject5).toString(), new Object[0]);
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append("PROFILE: ");
      ((StringBuilder)localObject4).append(z[this.B]);
      com.security.d.a.c("AACStream", ((StringBuilder)localObject4).toString(), new Object[0]);
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append("SAMPLING FREQUENCY: ");
      ((StringBuilder)localObject4).append(this.y.b);
      com.security.d.a.c("AACStream", ((StringBuilder)localObject4).toString(), new Object[0]);
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append("CHANNEL: ");
      ((StringBuilder)localObject4).append(this.D);
      com.security.d.a.c("AACStream", ((StringBuilder)localObject4).toString(), new Object[0]);
      ((RandomAccessFile)localObject3).close();
      localObject3 = this.F;
      if (localObject3 != null)
      {
        localObject4 = ((SharedPreferences)localObject3).edit();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append(this.y.b);
        ((StringBuilder)localObject3).append(",");
        ((StringBuilder)localObject3).append(this.E);
        ((StringBuilder)localObject3).append(",");
        ((StringBuilder)localObject3).append(this.D);
        ((SharedPreferences.Editor)localObject4).putString((String)localObject1, ((StringBuilder)localObject3).toString());
        ((SharedPreferences.Editor)localObject4).commit();
      }
      if (!((File)localObject2).delete()) {
        com.security.d.a.a("AACStream", "Temp file could not be erased", new Object[0]);
      }
      return;
      throw new IllegalStateException("No external storage or external storage not ready !");
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;) {}
    }
  }
  
  public void a(SharedPreferences paramSharedPreferences)
  {
    this.F = paramSharedPreferences;
  }
  
  public void d()
  {
    try
    {
      super.d();
      this.y = this.x.a();
      for (int i = 0; i < t.length; i++) {
        if (t[i] == this.y.b)
        {
          this.C = i;
          break;
        }
      }
      if (i > 12) {
        this.y.b = 16000;
      }
      Object localObject1;
      if ((this.c != this.d) || (this.a == null))
      {
        this.c = ((byte)this.d);
        if (this.c == 1)
        {
          localObject1 = new com/app/system/streaming/f/a;
          ((com.app.system.streaming.f.a)localObject1).<init>();
          this.a = ((com.app.system.streaming.f.d)localObject1);
        }
        else
        {
          localObject1 = new com/app/system/streaming/f/b;
          ((b)localObject1).<init>();
          this.a = ((com.app.system.streaming.f.d)localObject1);
        }
        this.a.a(this.l, this.h, this.i);
        this.a.c().a(this.k, this.j);
      }
      if (this.c == 1)
      {
        m();
        localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        ((StringBuilder)localObject1).append("m=audio ");
        ((StringBuilder)localObject1).append(String.valueOf(a()[0]));
        ((StringBuilder)localObject1).append(" RTP/AVP 96\r\na=rtpmap:96 mpeg4-generic/");
        ((StringBuilder)localObject1).append(this.y.b);
        ((StringBuilder)localObject1).append("\r\na=fmtp:96 streamtype=5; profile-level-id=15; mode=AAC-hbr; config=");
        ((StringBuilder)localObject1).append(Integer.toHexString(this.E));
        ((StringBuilder)localObject1).append("; SizeLength=13; IndexLength=3; IndexDeltaLength=3;\r\n");
        this.A = ((StringBuilder)localObject1).toString();
      }
      else
      {
        this.B = 2;
        this.D = 1;
        this.E = ((this.B & 0x1F) << 11 | (this.C & 0xF) << 7 | (this.D & 0xF) << 3);
        localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        ((StringBuilder)localObject1).append("m=audio ");
        ((StringBuilder)localObject1).append(String.valueOf(a()[0]));
        ((StringBuilder)localObject1).append(" RTP/AVP 96\r\na=rtpmap:96 mpeg4-generic/");
        ((StringBuilder)localObject1).append(this.y.b);
        ((StringBuilder)localObject1).append("\r\na=fmtp:96 streamtype=5; profile-level-id=15; mode=AAC-hbr; config=");
        ((StringBuilder)localObject1).append(Integer.toHexString(this.E));
        ((StringBuilder)localObject1).append("; SizeLength=13; IndexLength=3; IndexDeltaLength=3;\r\n");
        this.A = ((StringBuilder)localObject1).toString();
      }
      return;
    }
    finally {}
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
  
  public void f()
  {
    try
    {
      if (this.f)
      {
        if (this.c == 2)
        {
          com.security.d.a.d("AACStream", "Interrupting threads...", new Object[0]);
          this.H.interrupt();
          this.G.stop();
          this.G.release();
          this.G = null;
        }
        super.f();
      }
      return;
    }
    finally {}
  }
  
  protected void g()
  {
    m();
    ((com.app.system.streaming.f.a)this.a).a(this.y.b);
    super.g();
  }
  
  @SuppressLint({"InlinedApi", "NewApi"})
  protected void h()
  {
    final int i = AudioRecord.getMinBufferSize(this.y.b, 16, 2) * 2;
    ((b)this.a).a(this.y.b);
    this.G = new AudioRecord(1, this.y.b, 16, 2, i);
    this.s = MediaCodec.createEncoderByType("audio/mp4a-latm");
    Object localObject = new MediaFormat();
    ((MediaFormat)localObject).setString("mime", "audio/mp4a-latm");
    ((MediaFormat)localObject).setInteger("bitrate", this.y.c);
    ((MediaFormat)localObject).setInteger("channel-count", 1);
    ((MediaFormat)localObject).setInteger("sample-rate", this.y.b);
    ((MediaFormat)localObject).setInteger("aac-profile", 2);
    ((MediaFormat)localObject).setInteger("max-input-size", i);
    this.s.configure((MediaFormat)localObject, null, null, 1);
    this.G.startRecording();
    this.s.start();
    localObject = new g(this.s);
    this.H = new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          while (!Thread.interrupted())
          {
            int i = a.a(a.this).dequeueInputBuffer(10000L);
            if (i >= 0)
            {
              this.a[i].clear();
              int j = a.b(a.this).read(this.a[i], i);
              if ((j != -3) && (j != -2)) {
                a.c(a.this).queueInputBuffer(i, 0, j, System.nanoTime() / 1000L, 0);
              } else {
                com.security.d.a.a("AACStream", "An error occured with the AudioRecord API !", new Object[0]);
              }
            }
          }
          return;
        }
        catch (RuntimeException localRuntimeException)
        {
          localRuntimeException.printStackTrace();
        }
      }
    });
    this.H.start();
    this.a.a((InputStream)localObject);
    this.a.a();
    this.f = true;
  }
  
  public String i()
  {
    String str = this.A;
    if (str != null) {
      return str;
    }
    throw new IllegalStateException("You need to call configure() first !");
  }
}


/* Location:              ~/com/app/system/streaming/a/a.class
 *
 * Reversed by:           J
 */