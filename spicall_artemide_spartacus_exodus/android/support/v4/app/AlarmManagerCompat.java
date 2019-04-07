package android.support.v4.app;

import android.app.AlarmManager;
import android.app.AlarmManager.AlarmClockInfo;
import android.app.PendingIntent;
import android.os.Build.VERSION;

public final class AlarmManagerCompat
{
  public static void setAlarmClock(AlarmManager paramAlarmManager, long paramLong, PendingIntent paramPendingIntent1, PendingIntent paramPendingIntent2)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      paramAlarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(paramLong, paramPendingIntent1), paramPendingIntent2);
    } else {
      setExact(paramAlarmManager, 0, paramLong, paramPendingIntent2);
    }
  }
  
  public static void setAndAllowWhileIdle(AlarmManager paramAlarmManager, int paramInt, long paramLong, PendingIntent paramPendingIntent)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      paramAlarmManager.setAndAllowWhileIdle(paramInt, paramLong, paramPendingIntent);
    } else {
      paramAlarmManager.set(paramInt, paramLong, paramPendingIntent);
    }
  }
  
  public static void setExact(AlarmManager paramAlarmManager, int paramInt, long paramLong, PendingIntent paramPendingIntent)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      paramAlarmManager.setExact(paramInt, paramLong, paramPendingIntent);
    } else {
      paramAlarmManager.set(paramInt, paramLong, paramPendingIntent);
    }
  }
  
  public static void setExactAndAllowWhileIdle(AlarmManager paramAlarmManager, int paramInt, long paramLong, PendingIntent paramPendingIntent)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      paramAlarmManager.setExactAndAllowWhileIdle(paramInt, paramLong, paramPendingIntent);
    } else {
      setExact(paramAlarmManager, paramInt, paramLong, paramPendingIntent);
    }
  }
}


/* Location:              ~/android/support/v4/app/AlarmManagerCompat.class
 *
 * Reversed by:           J
 */