package android.support.v4.media;

import android.media.browse.MediaBrowser.MediaItem;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

class ParceledListSliceAdapterApi21
{
  private static Constructor sConstructor;
  
  static
  {
    try
    {
      sConstructor = Class.forName("android.content.pm.ParceledListSlice").getConstructor(new Class[] { List.class });
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}catch (ClassNotFoundException localClassNotFoundException) {}
    localClassNotFoundException.printStackTrace();
  }
  
  static Object newInstance(List<MediaBrowser.MediaItem> paramList)
  {
    try
    {
      paramList = sConstructor.newInstance(new Object[] { paramList });
    }
    catch (InvocationTargetException paramList) {}catch (IllegalAccessException paramList) {}catch (InstantiationException paramList) {}
    paramList.printStackTrace();
    paramList = null;
    return paramList;
  }
}


/* Location:              ~/android/support/v4/media/ParceledListSliceAdapterApi21.class
 *
 * Reversed by:           J
 */