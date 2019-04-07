package com.crashlytics.android.answers;

import android.content.Context;
import android.os.Looper;
import io.fabric.sdk.android.services.b.v;
import io.fabric.sdk.android.services.c.c;
import io.fabric.sdk.android.services.c.g;
import io.fabric.sdk.android.services.d.a;
import java.io.File;

class AnswersFilesManagerProvider
{
  static final String SESSION_ANALYTICS_FILE_NAME = "session_analytics.tap";
  static final String SESSION_ANALYTICS_TO_SEND_DIR = "session_analytics_to_send";
  final Context context;
  final a fileStore;
  
  public AnswersFilesManagerProvider(Context paramContext, a parama)
  {
    this.context = paramContext;
    this.fileStore = parama;
  }
  
  public SessionAnalyticsFilesManager getAnalyticsFilesManager()
  {
    if (Looper.myLooper() != Looper.getMainLooper())
    {
      SessionEventTransform localSessionEventTransform = new SessionEventTransform();
      v localv = new v();
      Object localObject = this.fileStore.a();
      localObject = new g(this.context, (File)localObject, "session_analytics.tap", "session_analytics_to_send");
      return new SessionAnalyticsFilesManager(this.context, localSessionEventTransform, localv, (c)localObject);
    }
    throw new IllegalStateException("AnswersFilesManagerProvider cannot be called on the main thread");
  }
}


/* Location:              ~/com/crashlytics/android/answers/AnswersFilesManagerProvider.class
 *
 * Reversed by:           J
 */