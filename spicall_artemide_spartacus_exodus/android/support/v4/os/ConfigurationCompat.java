package android.support.v4.os;

import android.content.res.Configuration;
import android.os.Build.VERSION;
import java.util.Locale;

public final class ConfigurationCompat
{
  public static LocaleListCompat getLocales(Configuration paramConfiguration)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return LocaleListCompat.wrap(paramConfiguration.getLocales());
    }
    return LocaleListCompat.create(new Locale[] { paramConfiguration.locale });
  }
}


/* Location:              ~/android/support/v4/os/ConfigurationCompat.class
 *
 * Reversed by:           J
 */