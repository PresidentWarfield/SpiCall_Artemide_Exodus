package com.crashlytics.android.answers;

abstract interface EventFilter
{
  public abstract boolean skipEvent(SessionEvent paramSessionEvent);
}


/* Location:              ~/com/crashlytics/android/answers/EventFilter.class
 *
 * Reversed by:           J
 */