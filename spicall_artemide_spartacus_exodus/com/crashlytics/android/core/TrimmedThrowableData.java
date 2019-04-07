package com.crashlytics.android.core;

class TrimmedThrowableData
{
  public final TrimmedThrowableData cause;
  public final String className;
  public final String localizedMessage;
  public final StackTraceElement[] stacktrace;
  
  public TrimmedThrowableData(Throwable paramThrowable, StackTraceTrimmingStrategy paramStackTraceTrimmingStrategy)
  {
    this.localizedMessage = paramThrowable.getLocalizedMessage();
    this.className = paramThrowable.getClass().getName();
    this.stacktrace = paramStackTraceTrimmingStrategy.getTrimmedStackTrace(paramThrowable.getStackTrace());
    paramThrowable = paramThrowable.getCause();
    if (paramThrowable != null) {
      paramThrowable = new TrimmedThrowableData(paramThrowable, paramStackTraceTrimmingStrategy);
    } else {
      paramThrowable = null;
    }
    this.cause = paramThrowable;
  }
}


/* Location:              ~/com/crashlytics/android/core/TrimmedThrowableData.class
 *
 * Reversed by:           J
 */