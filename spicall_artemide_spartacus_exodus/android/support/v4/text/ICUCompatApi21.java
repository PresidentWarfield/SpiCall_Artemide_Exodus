package android.support.v4.text;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

class ICUCompatApi21
{
  private static final String TAG = "ICUCompatApi21";
  private static Method sAddLikelySubtagsMethod;
  
  static
  {
    try
    {
      sAddLikelySubtagsMethod = Class.forName("libcore.icu.ICU").getMethod("addLikelySubtags", new Class[] { Locale.class });
      return;
    }
    catch (Exception localException)
    {
      throw new IllegalStateException(localException);
    }
  }
  
  public static String maximizeAndGetScript(Locale paramLocale)
  {
    try
    {
      String str = ((Locale)sAddLikelySubtagsMethod.invoke(null, new Object[] { paramLocale })).getScript();
      return str;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      Log.w("ICUCompatApi21", localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      Log.w("ICUCompatApi21", localInvocationTargetException);
    }
    return paramLocale.getScript();
  }
}


/* Location:              ~/android/support/v4/text/ICUCompatApi21.class
 *
 * Reversed by:           J
 */