package com.crashlytics.android.core;

class MiddleOutStrategy
  implements StackTraceTrimmingStrategy
{
  private final int trimmedSize;
  
  public MiddleOutStrategy(int paramInt)
  {
    this.trimmedSize = paramInt;
  }
  
  public StackTraceElement[] getTrimmedStackTrace(StackTraceElement[] paramArrayOfStackTraceElement)
  {
    int i = paramArrayOfStackTraceElement.length;
    int j = this.trimmedSize;
    if (i <= j) {
      return paramArrayOfStackTraceElement;
    }
    int k = j / 2;
    i = j - k;
    StackTraceElement[] arrayOfStackTraceElement = new StackTraceElement[j];
    System.arraycopy(paramArrayOfStackTraceElement, 0, arrayOfStackTraceElement, 0, i);
    System.arraycopy(paramArrayOfStackTraceElement, paramArrayOfStackTraceElement.length - k, arrayOfStackTraceElement, i, k);
    return arrayOfStackTraceElement;
  }
}


/* Location:              ~/com/crashlytics/android/core/MiddleOutStrategy.class
 *
 * Reversed by:           J
 */