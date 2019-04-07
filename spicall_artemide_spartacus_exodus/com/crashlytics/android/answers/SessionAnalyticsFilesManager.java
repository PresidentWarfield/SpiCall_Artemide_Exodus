package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.services.b.k;
import io.fabric.sdk.android.services.c.c;
import java.util.UUID;

class SessionAnalyticsFilesManager
  extends io.fabric.sdk.android.services.c.b<SessionEvent>
{
  private static final String SESSION_ANALYTICS_TO_SEND_FILE_EXTENSION = ".tap";
  private static final String SESSION_ANALYTICS_TO_SEND_FILE_PREFIX = "sa";
  private io.fabric.sdk.android.services.e.b analyticsSettingsData;
  
  SessionAnalyticsFilesManager(Context paramContext, SessionEventTransform paramSessionEventTransform, k paramk, c paramc)
  {
    super(paramContext, paramSessionEventTransform, paramk, paramc, 100);
  }
  
  protected String generateUniqueRollOverFileName()
  {
    UUID localUUID = UUID.randomUUID();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("sa");
    localStringBuilder.append("_");
    localStringBuilder.append(localUUID.toString());
    localStringBuilder.append("_");
    localStringBuilder.append(this.currentTimeProvider.a());
    localStringBuilder.append(".tap");
    return localStringBuilder.toString();
  }
  
  protected int getMaxByteSizePerFile()
  {
    io.fabric.sdk.android.services.e.b localb = this.analyticsSettingsData;
    int i;
    if (localb == null) {
      i = super.getMaxByteSizePerFile();
    } else {
      i = localb.c;
    }
    return i;
  }
  
  protected int getMaxFilesToKeep()
  {
    io.fabric.sdk.android.services.e.b localb = this.analyticsSettingsData;
    int i;
    if (localb == null) {
      i = super.getMaxFilesToKeep();
    } else {
      i = localb.e;
    }
    return i;
  }
  
  void setAnalyticsSettingsData(io.fabric.sdk.android.services.e.b paramb)
  {
    this.analyticsSettingsData = paramb;
  }
}


/* Location:              ~/com/crashlytics/android/answers/SessionAnalyticsFilesManager.class
 *
 * Reversed by:           J
 */