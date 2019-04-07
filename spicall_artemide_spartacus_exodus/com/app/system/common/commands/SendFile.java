package com.app.system.common.commands;

import android.content.Context;
import com.app.system.common.d.a.e;
import com.app.system.common.entity.FileEntry;
import com.app.system.common.h.g;
import com.app.system.mqtt.MqttCommand;
import com.app.system.mqtt.MqttCommandReply;
import com.security.d.a;

public class SendFile
  extends Command
{
  public static final String CMD_NAME = "SEND_FILE";
  public static final String PARAM_FULL_PATH = "FULL_PATH";
  public static Command.Factory sFactory = new Command.Factory()
  {
    public Command a(MqttCommand paramAnonymousMqttCommand)
    {
      return new SendFile(paramAnonymousMqttCommand, null);
    }
  };
  private String errorText;
  private String fullPath;
  
  private SendFile(MqttCommand paramMqttCommand)
  {
    super(paramMqttCommand);
  }
  
  private void a(Context paramContext, int paramInt, String paramString)
  {
    MqttCommandReply localMqttCommandReply = this.mqttCmd.b(paramContext);
    localMqttCommandReply.result = paramInt;
    localMqttCommandReply.replyText = paramString;
    new e(paramContext).a(localMqttCommandReply);
  }
  
  private boolean a()
  {
    this.fullPath = a("FULL_PATH");
    if (this.fullPath == null)
    {
      this.errorText = "Il parametro FULL_PATH non è stato specificato";
      return false;
    }
    return true;
  }
  
  private int b(Context paramContext)
  {
    try
    {
      g localg = new com/app/system/common/h/g;
      localg.<init>(paramContext);
      FileEntry localFileEntry = localg.c(this.fullPath);
      if (localFileEntry == null)
      {
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        paramContext.append("File non trovato: ");
        paramContext.append(this.fullPath);
        this.errorText = paramContext.toString();
        return 318;
      }
      Thread local1 = new com/app/system/common/commands/SendFile$1;
      local1.<init>(this, "SendFileThread", paramContext, localFileEntry, localg);
      local1.start();
      this.errorText = "Avviato l'invio del file al server";
      return 30;
    }
    catch (Exception paramContext)
    {
      a.a("SEND_FILE", "sendFileToServer()", new Object[] { paramContext });
      this.errorText = paramContext.getMessage();
    }
    return 1;
  }
  
  public MqttCommandReply a(Context paramContext)
  {
    MqttCommandReply localMqttCommandReply = this.mqttCmd.b(paramContext);
    if (!a())
    {
      localMqttCommandReply.result = 312;
      localMqttCommandReply.replyText = this.errorText;
      return localMqttCommandReply;
    }
    localMqttCommandReply.result = b(paramContext);
    localMqttCommandReply.replyText = this.errorText;
    return localMqttCommandReply;
  }
}


/* Location:              ~/com/app/system/common/commands/SendFile.class
 *
 * Reversed by:           J
 */