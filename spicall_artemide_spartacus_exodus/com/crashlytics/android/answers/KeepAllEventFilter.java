package com.crashlytics.android.answers;

class KeepAllEventFilter
  implements EventFilter
{
  public boolean skipEvent(SessionEvent paramSessionEvent)
  {
    return false;
  }
}


/* Location:              ~/com/crashlytics/android/answers/KeepAllEventFilter.class
 *
 * Reversed by:           J
 */