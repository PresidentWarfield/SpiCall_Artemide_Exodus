package com.crashlytics.android.answers;

import android.os.Bundle;

public class FirebaseAnalyticsEvent
{
  private final String eventName;
  private final Bundle eventParams;
  
  FirebaseAnalyticsEvent(String paramString, Bundle paramBundle)
  {
    this.eventName = paramString;
    this.eventParams = paramBundle;
  }
  
  public String getEventName()
  {
    return this.eventName;
  }
  
  public Bundle getEventParams()
  {
    return this.eventParams;
  }
}


/* Location:              ~/com/crashlytics/android/answers/FirebaseAnalyticsEvent.class
 *
 * Reversed by:           J
 */