package android.support.v4.net;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ConnectivityManagerCompat
{
  private static final ConnectivityManagerCompatImpl IMPL;
  public static final int RESTRICT_BACKGROUND_STATUS_DISABLED = 1;
  public static final int RESTRICT_BACKGROUND_STATUS_ENABLED = 3;
  public static final int RESTRICT_BACKGROUND_STATUS_WHITELISTED = 2;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 24) {
      IMPL = new ConnectivityManagerCompatApi24Impl();
    } else if (Build.VERSION.SDK_INT >= 16) {
      IMPL = new ConnectivityManagerCompatApi16Impl();
    } else {
      IMPL = new ConnectivityManagerCompatBaseImpl();
    }
  }
  
  public static NetworkInfo getNetworkInfoFromBroadcast(ConnectivityManager paramConnectivityManager, Intent paramIntent)
  {
    paramIntent = (NetworkInfo)paramIntent.getParcelableExtra("networkInfo");
    if (paramIntent != null) {
      return paramConnectivityManager.getNetworkInfo(paramIntent.getType());
    }
    return null;
  }
  
  public static int getRestrictBackgroundStatus(ConnectivityManager paramConnectivityManager)
  {
    return IMPL.getRestrictBackgroundStatus(paramConnectivityManager);
  }
  
  public static boolean isActiveNetworkMetered(ConnectivityManager paramConnectivityManager)
  {
    return IMPL.isActiveNetworkMetered(paramConnectivityManager);
  }
  
  static class ConnectivityManagerCompatApi16Impl
    extends ConnectivityManagerCompat.ConnectivityManagerCompatBaseImpl
  {
    public boolean isActiveNetworkMetered(ConnectivityManager paramConnectivityManager)
    {
      return paramConnectivityManager.isActiveNetworkMetered();
    }
  }
  
  static class ConnectivityManagerCompatApi24Impl
    extends ConnectivityManagerCompat.ConnectivityManagerCompatApi16Impl
  {
    public int getRestrictBackgroundStatus(ConnectivityManager paramConnectivityManager)
    {
      return paramConnectivityManager.getRestrictBackgroundStatus();
    }
  }
  
  static class ConnectivityManagerCompatBaseImpl
    implements ConnectivityManagerCompat.ConnectivityManagerCompatImpl
  {
    public int getRestrictBackgroundStatus(ConnectivityManager paramConnectivityManager)
    {
      return 3;
    }
    
    public boolean isActiveNetworkMetered(ConnectivityManager paramConnectivityManager)
    {
      paramConnectivityManager = paramConnectivityManager.getActiveNetworkInfo();
      if (paramConnectivityManager == null) {
        return true;
      }
      switch (paramConnectivityManager.getType())
      {
      case 8: 
      default: 
        return true;
      case 1: 
      case 7: 
      case 9: 
        return false;
      }
      return true;
    }
  }
  
  static abstract interface ConnectivityManagerCompatImpl
  {
    public abstract int getRestrictBackgroundStatus(ConnectivityManager paramConnectivityManager);
    
    public abstract boolean isActiveNetworkMetered(ConnectivityManager paramConnectivityManager);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface RestrictBackgroundStatus {}
}


/* Location:              ~/android/support/v4/net/ConnectivityManagerCompat.class
 *
 * Reversed by:           J
 */