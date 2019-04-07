package com.crashlytics.android.answers;

import io.fabric.sdk.android.services.e.b;

class DisabledSessionAnalyticsManagerStrategy
  implements SessionAnalyticsManagerStrategy
{
  public void cancelTimeBasedFileRollOver() {}
  
  public void deleteAllEvents() {}
  
  public void processEvent(SessionEvent.Builder paramBuilder) {}
  
  public boolean rollFileOver()
  {
    return false;
  }
  
  public void scheduleTimeBasedRollOverIfNeeded() {}
  
  public void sendEvents() {}
  
  public void setAnalyticsSettingsData(b paramb, String paramString) {}
}


/* Location:              ~/com/crashlytics/android/answers/DisabledSessionAnalyticsManagerStrategy.class
 *
 * Reversed by:           J
 */