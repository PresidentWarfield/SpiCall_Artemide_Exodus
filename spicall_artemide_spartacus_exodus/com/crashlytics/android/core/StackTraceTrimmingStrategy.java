package com.crashlytics.android.core;

abstract interface StackTraceTrimmingStrategy
{
  public abstract StackTraceElement[] getTrimmedStackTrace(StackTraceElement[] paramArrayOfStackTraceElement);
}


/* Location:              ~/com/crashlytics/android/core/StackTraceTrimmingStrategy.class
 *
 * Reversed by:           J
 */