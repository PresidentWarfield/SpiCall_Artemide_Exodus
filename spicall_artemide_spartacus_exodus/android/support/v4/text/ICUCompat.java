package android.support.v4.text;

import android.os.Build.VERSION;
import java.util.Locale;

public final class ICUCompat
{
  private static final ICUCompatBaseImpl IMPL;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21) {
      IMPL = new ICUCompatApi21Impl();
    } else {
      IMPL = new ICUCompatBaseImpl();
    }
  }
  
  public static String maximizeAndGetScript(Locale paramLocale)
  {
    return IMPL.maximizeAndGetScript(paramLocale);
  }
  
  static class ICUCompatApi21Impl
    extends ICUCompat.ICUCompatBaseImpl
  {
    public String maximizeAndGetScript(Locale paramLocale)
    {
      return ICUCompatApi21.maximizeAndGetScript(paramLocale);
    }
  }
  
  static class ICUCompatBaseImpl
  {
    public String maximizeAndGetScript(Locale paramLocale)
    {
      return ICUCompatIcs.maximizeAndGetScript(paramLocale);
    }
  }
}


/* Location:              ~/android/support/v4/text/ICUCompat.class
 *
 * Reversed by:           J
 */