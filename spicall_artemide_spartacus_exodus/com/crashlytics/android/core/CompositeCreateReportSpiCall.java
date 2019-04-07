package com.crashlytics.android.core;

class CompositeCreateReportSpiCall
  implements CreateReportSpiCall
{
  private final DefaultCreateReportSpiCall javaReportSpiCall;
  private final NativeCreateReportSpiCall nativeReportSpiCall;
  
  public CompositeCreateReportSpiCall(DefaultCreateReportSpiCall paramDefaultCreateReportSpiCall, NativeCreateReportSpiCall paramNativeCreateReportSpiCall)
  {
    this.javaReportSpiCall = paramDefaultCreateReportSpiCall;
    this.nativeReportSpiCall = paramNativeCreateReportSpiCall;
  }
  
  public boolean invoke(CreateReportRequest paramCreateReportRequest)
  {
    switch (paramCreateReportRequest.report.getType())
    {
    default: 
      return false;
    case ???: 
      this.nativeReportSpiCall.invoke(paramCreateReportRequest);
      return true;
    }
    this.javaReportSpiCall.invoke(paramCreateReportRequest);
    return true;
  }
}


/* Location:              ~/com/crashlytics/android/core/CompositeCreateReportSpiCall.class
 *
 * Reversed by:           J
 */