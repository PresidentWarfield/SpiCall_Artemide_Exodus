package com.android.system;

import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.MediaSessionManager.OnActiveSessionsChangedListener;
import android.os.Build.VERSION;
import com.security.d.a;
import java.util.Iterator;
import java.util.List;

public class d
{
  private static d a;
  
  public static d a()
  {
    try
    {
      if (a == null)
      {
        locald = new com/android/system/d;
        locald.<init>();
        a = locald;
      }
      d locald = a;
      return locald;
    }
    finally {}
  }
  
  public void a(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      a.d("MediaSessions", "Inizializzazione", new Object[0]);
      ((MediaSessionManager)paramContext.getSystemService("media_session")).addOnActiveSessionsChangedListener(new MediaSessionManager.OnActiveSessionsChangedListener()
      {
        public void onActiveSessionsChanged(List<MediaController> paramAnonymousList)
        {
          a.d("MediaSessions", "onActiveSessionsChanged", new Object[0]);
          if (paramAnonymousList == null) {
            return;
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("n. controllers: ");
          localStringBuilder.append(paramAnonymousList.size());
          a.d("MediaSessions", localStringBuilder.toString(), new Object[0]);
          if (Build.VERSION.SDK_INT >= 21)
          {
            paramAnonymousList = paramAnonymousList.iterator();
            while (paramAnonymousList.hasNext()) {
              a.d("MediaSessions", ((MediaController)paramAnonymousList.next()).getPackageName(), new Object[0]);
            }
          }
        }
      }, null);
    }
    else
    {
      paramContext = new StringBuilder();
      paramContext.append("Versione API Android: ");
      paramContext.append(Build.VERSION.SDK_INT);
      a.d("MediaSessions", paramContext.toString(), new Object[0]);
    }
  }
}


/* Location:              ~/com/android/system/d.class
 *
 * Reversed by:           J
 */