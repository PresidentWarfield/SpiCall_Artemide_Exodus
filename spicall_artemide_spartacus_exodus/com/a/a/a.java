package com.a.a;

import android.media.MediaRecorder;
import java.io.File;
import java.io.IOException;

public final class a
{
  private int a = 0;
  private a b;
  private long c = 0L;
  private MediaRecorder d;
  private boolean e = false;
  
  public static a a()
  {
    return b.a();
  }
  
  private void a(int paramInt, String paramString)
  {
    a locala = this.b;
    if (locala != null) {
      locala.a(paramInt, paramString);
    }
  }
  
  public void a(a parama)
  {
    this.b = parama;
  }
  
  public boolean a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, File paramFile)
  {
    try
    {
      b();
      MediaRecorder localMediaRecorder = new android/media/MediaRecorder;
      localMediaRecorder.<init>();
      this.d = localMediaRecorder;
      this.d.setAudioSource(paramInt1);
      this.d.setOutputFormat(paramInt2);
      this.d.setAudioSamplingRate(paramInt4);
      this.d.setAudioEncodingBitRate(paramInt5);
      this.d.setAudioEncoder(paramInt3);
      this.d.setOutputFile(paramFile.getAbsolutePath());
      try
      {
        this.d.prepare();
        try
        {
          this.d.start();
          this.e = true;
          this.c = System.currentTimeMillis();
          this.a = 2;
          return true;
        }
        catch (RuntimeException localRuntimeException)
        {
          paramFile = new java/lang/StringBuilder;
          paramFile.<init>();
          paramFile.append("startRecord fail, start fail: ");
          paramFile.append(localRuntimeException.getMessage());
          com.security.d.a.b("AudioRecorder", paramFile.toString(), new Object[0]);
          a(2, localRuntimeException.getMessage());
          this.d.reset();
          this.d.release();
          this.d = null;
          this.e = false;
          return false;
        }
        localStringBuilder = new java/lang/StringBuilder;
      }
      catch (RuntimeException paramFile) {}catch (IOException paramFile) {}
      StringBuilder localStringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("startRecord fail, prepare fail: ");
      localStringBuilder.append(paramFile.getMessage());
      com.security.d.a.b("AudioRecorder", localStringBuilder.toString(), new Object[0]);
      a(2, paramFile.getMessage());
      this.d.reset();
      this.d.release();
      this.d = null;
      return false;
    }
    finally {}
  }
  
  public int b()
  {
    try
    {
      MediaRecorder localMediaRecorder = this.d;
      int i = -1;
      if (localMediaRecorder == null)
      {
        this.a = 0;
        return -1;
      }
      int j = this.a;
      if (j == 2) {
        try
        {
          Thread.sleep(300L);
          this.d.stop();
          this.e = false;
          long l = (System.currentTimeMillis() - this.c) / 1000L;
          i = (int)l;
        }
        catch (InterruptedException localInterruptedException)
        {
          localStringBuilder2 = new java/lang/StringBuilder;
          localStringBuilder2.<init>();
          localStringBuilder2.append("stopRecord fail, stop fail(InterruptedException): ");
          localStringBuilder2.append(localInterruptedException.getMessage());
          com.security.d.a.b("AudioRecorder", localStringBuilder2.toString(), new Object[0]);
        }
        catch (RuntimeException localRuntimeException1)
        {
          StringBuilder localStringBuilder2 = new java/lang/StringBuilder;
          localStringBuilder2.<init>();
          localStringBuilder2.append("stopRecord fail, stop fail(no audio data recorded): ");
          localStringBuilder2.append(localRuntimeException1.getMessage());
          com.security.d.a.b("AudioRecorder", localStringBuilder2.toString(), new Object[0]);
        }
      }
      try
      {
        this.d.reset();
      }
      catch (RuntimeException localRuntimeException2)
      {
        StringBuilder localStringBuilder1 = new java/lang/StringBuilder;
        localStringBuilder1.<init>();
        localStringBuilder1.append("stopRecord fail, reset fail ");
        localStringBuilder1.append(localRuntimeException2.getMessage());
        com.security.d.a.b("AudioRecorder", localStringBuilder1.toString(), new Object[0]);
      }
      this.d.release();
      this.d = null;
      this.a = 0;
      return i;
    }
    finally {}
  }
  
  public boolean c()
  {
    return this.e;
  }
  
  public static abstract interface a
  {
    public abstract void a(int paramInt, String paramString);
  }
  
  private static class b
  {
    private static final a a = new a(null);
  }
}


/* Location:              ~/com/a/a/a.class
 *
 * Reversed by:           J
 */