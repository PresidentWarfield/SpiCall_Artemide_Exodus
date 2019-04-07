package android.support.v4.os;

import android.os.Build.VERSION;

public class BuildCompat
{
  @Deprecated
  public static boolean isAtLeastN()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 24) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Deprecated
  public static boolean isAtLeastNMR1()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 25) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isAtLeastO()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 26) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isAtLeastOMR1()
  {
    boolean bool;
    if ((!Build.VERSION.CODENAME.startsWith("OMR")) && (!isAtLeastP())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isAtLeastP()
  {
    return Build.VERSION.CODENAME.equals("P");
  }
}


/* Location:              ~/android/support/v4/os/BuildCompat.class
 *
 * Reversed by:           J
 */