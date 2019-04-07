package com.crashlytics.android.core;

class CreateReportRequest
{
  public final String apiKey;
  public final Report report;
  
  public CreateReportRequest(String paramString, Report paramReport)
  {
    this.apiKey = paramString;
    this.report = paramReport;
  }
}


/* Location:              ~/com/crashlytics/android/core/CreateReportRequest.class
 *
 * Reversed by:           J
 */