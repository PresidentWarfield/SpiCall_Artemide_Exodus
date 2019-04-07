package android.support.v4.media;

import android.media.AudioAttributes;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class AudioAttributesCompatApi21
{
  private static final String TAG = "AudioAttributesCompat";
  private static Method sAudioAttributesToLegacyStreamType;
  
  public static int toLegacyStreamType(Wrapper paramWrapper)
  {
    paramWrapper = paramWrapper.unwrap();
    try
    {
      if (sAudioAttributesToLegacyStreamType == null) {
        sAudioAttributesToLegacyStreamType = AudioAttributes.class.getMethod("toLegacyStreamType", new Class[] { AudioAttributes.class });
      }
      int i = ((Integer)sAudioAttributesToLegacyStreamType.invoke(null, new Object[] { paramWrapper })).intValue();
      return i;
    }
    catch (ClassCastException paramWrapper) {}catch (IllegalAccessException paramWrapper) {}catch (InvocationTargetException paramWrapper) {}catch (NoSuchMethodException paramWrapper) {}
    Log.w("AudioAttributesCompat", "getLegacyStreamType() failed on API21+", paramWrapper);
    return -1;
  }
  
  static final class Wrapper
  {
    private AudioAttributes mWrapped;
    
    private Wrapper(AudioAttributes paramAudioAttributes)
    {
      this.mWrapped = paramAudioAttributes;
    }
    
    public static Wrapper wrap(AudioAttributes paramAudioAttributes)
    {
      if (paramAudioAttributes != null) {
        return new Wrapper(paramAudioAttributes);
      }
      throw new IllegalArgumentException("AudioAttributesApi21.Wrapper cannot wrap null");
    }
    
    public AudioAttributes unwrap()
    {
      return this.mWrapped;
    }
  }
}


/* Location:              ~/android/support/v4/media/AudioAttributesCompatApi21.class
 *
 * Reversed by:           J
 */