package com.crashlytics.android.answers;

import android.os.Bundle;

public abstract interface EventLogger
{
  public abstract void logEvent(String paramString, Bundle paramBundle);
  
  public abstract void logEvent(String paramString1, String paramString2, Bundle paramBundle);
}


/* Location:              ~/com/crashlytics/android/answers/EventLogger.class
 *
 * Reversed by:           J
 */