package android.support.v4.app;

import android.os.Bundle;
import android.os.Parcelable;
import java.util.Arrays;

class BundleUtil
{
  public static Bundle[] getBundleArrayFromBundle(Bundle paramBundle, String paramString)
  {
    Object localObject = paramBundle.getParcelableArray(paramString);
    if ((!(localObject instanceof Bundle[])) && (localObject != null))
    {
      localObject = (Bundle[])Arrays.copyOf((Object[])localObject, localObject.length, Bundle[].class);
      paramBundle.putParcelableArray(paramString, (Parcelable[])localObject);
      return (Bundle[])localObject;
    }
    return (Bundle[])localObject;
  }
}


/* Location:              ~/android/support/v4/app/BundleUtil.class
 *
 * Reversed by:           J
 */