package com.app.system.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class b
{
  public static NetworkInfo a(Context paramContext)
  {
    return ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
  }
  
  public static boolean b(Context paramContext)
  {
    paramContext = a(paramContext);
    return (paramContext != null) && (paramContext.isConnected());
  }
}


/* Location:              ~/com/app/system/common/b.class
 *
 * Reversed by:           J
 */