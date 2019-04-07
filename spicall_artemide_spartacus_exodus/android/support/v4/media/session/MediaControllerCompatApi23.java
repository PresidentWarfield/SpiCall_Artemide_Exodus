package android.support.v4.media.session;

import android.media.session.MediaController.TransportControls;
import android.net.Uri;
import android.os.Bundle;

class MediaControllerCompatApi23
{
  public static class TransportControls
    extends MediaControllerCompatApi21.TransportControls
  {
    public static void playFromUri(Object paramObject, Uri paramUri, Bundle paramBundle)
    {
      ((MediaController.TransportControls)paramObject).playFromUri(paramUri, paramBundle);
    }
  }
}


/* Location:              ~/android/support/v4/media/session/MediaControllerCompatApi23.class
 *
 * Reversed by:           J
 */