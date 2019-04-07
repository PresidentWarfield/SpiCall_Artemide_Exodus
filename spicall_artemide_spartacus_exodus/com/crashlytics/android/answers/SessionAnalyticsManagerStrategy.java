package com.crashlytics.android.answers;

import io.fabric.sdk.android.services.c.e;
import io.fabric.sdk.android.services.e.b;

abstract interface SessionAnalyticsManagerStrategy
  extends e
{
  public abstract void deleteAllEvents();
  
  public abstract void processEvent(SessionEvent.Builder paramBuilder);
  
  public abstract void sendEvents();
  
  public abstract void setAnalyticsSettingsData(b paramb, String paramString);
}


/* Location:              ~/com/crashlytics/android/answers/SessionAnalyticsManagerStrategy.class
 *
 * Reversed by:           J
 */