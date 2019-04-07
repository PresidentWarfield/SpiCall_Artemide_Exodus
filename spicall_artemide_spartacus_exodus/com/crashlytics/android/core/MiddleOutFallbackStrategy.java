package com.crashlytics.android.core;

class MiddleOutFallbackStrategy
  implements StackTraceTrimmingStrategy
{
  private final int maximumStackSize;
  private final MiddleOutStrategy middleOutStrategy;
  private final StackTraceTrimmingStrategy[] trimmingStrategies;
  
  public MiddleOutFallbackStrategy(int paramInt, StackTraceTrimmingStrategy... paramVarArgs)
  {
    this.maximumStackSize = paramInt;
    this.trimmingStrategies = paramVarArgs;
    this.middleOutStrategy = new MiddleOutStrategy(paramInt);
  }
  
  public StackTraceElement[] getTrimmedStackTrace(StackTraceElement[] paramArrayOfStackTraceElement)
  {
    if (paramArrayOfStackTraceElement.length <= this.maximumStackSize) {
      return paramArrayOfStackTraceElement;
    }
    StackTraceTrimmingStrategy[] arrayOfStackTraceTrimmingStrategy = this.trimmingStrategies;
    int i = arrayOfStackTraceTrimmingStrategy.length;
    int j = 0;
    StackTraceElement[] arrayOfStackTraceElement = paramArrayOfStackTraceElement;
    while (j < i)
    {
      StackTraceTrimmingStrategy localStackTraceTrimmingStrategy = arrayOfStackTraceTrimmingStrategy[j];
      if (arrayOfStackTraceElement.length <= this.maximumStackSize) {
        break;
      }
      arrayOfStackTraceElement = localStackTraceTrimmingStrategy.getTrimmedStackTrace(paramArrayOfStackTraceElement);
      j++;
    }
    paramArrayOfStackTraceElement = arrayOfStackTraceElement;
    if (arrayOfStackTraceElement.length > this.maximumStackSize) {
      paramArrayOfStackTraceElement = this.middleOutStrategy.getTrimmedStackTrace(arrayOfStackTraceElement);
    }
    return paramArrayOfStackTraceElement;
  }
}


/* Location:              ~/com/crashlytics/android/core/MiddleOutFallbackStrategy.class
 *
 * Reversed by:           J
 */