package com.crashlytics.android.core;

import io.fabric.sdk.android.h;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.a;
import io.fabric.sdk.android.services.b.u;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.d;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class DefaultCreateReportSpiCall
  extends a
  implements CreateReportSpiCall
{
  static final String FILE_CONTENT_TYPE = "application/octet-stream";
  static final String FILE_PARAM = "report[file]";
  static final String IDENTIFIER_PARAM = "report[identifier]";
  static final String MULTI_FILE_PARAM = "report[file";
  
  public DefaultCreateReportSpiCall(h paramh, String paramString1, String paramString2, d paramd)
  {
    super(paramh, paramString1, paramString2, paramd, io.fabric.sdk.android.services.network.c.b);
  }
  
  DefaultCreateReportSpiCall(h paramh, String paramString1, String paramString2, d paramd, io.fabric.sdk.android.services.network.c paramc)
  {
    super(paramh, paramString1, paramString2, paramd, paramc);
  }
  
  private HttpRequest applyHeadersTo(HttpRequest paramHttpRequest, CreateReportRequest paramCreateReportRequest)
  {
    paramHttpRequest = paramHttpRequest.a("X-CRASHLYTICS-API-KEY", paramCreateReportRequest.apiKey).a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").a("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion());
    paramCreateReportRequest = paramCreateReportRequest.report.getCustomHeaders().entrySet().iterator();
    while (paramCreateReportRequest.hasNext()) {
      paramHttpRequest = paramHttpRequest.a((Map.Entry)paramCreateReportRequest.next());
    }
    return paramHttpRequest;
  }
  
  private HttpRequest applyMultipartDataTo(HttpRequest paramHttpRequest, Report paramReport)
  {
    paramHttpRequest.e("report[identifier]", paramReport.getIdentifier());
    k localk;
    if (paramReport.getFiles().length == 1)
    {
      localk = io.fabric.sdk.android.c.g();
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Adding single file ");
      ((StringBuilder)localObject1).append(paramReport.getFileName());
      ((StringBuilder)localObject1).append(" to report ");
      ((StringBuilder)localObject1).append(paramReport.getIdentifier());
      localk.a("CrashlyticsCore", ((StringBuilder)localObject1).toString());
      return paramHttpRequest.a("report[file]", paramReport.getFileName(), "application/octet-stream", paramReport.getFile());
    }
    Object localObject1 = paramReport.getFiles();
    int i = localObject1.length;
    int j = 0;
    int k = 0;
    while (j < i)
    {
      localk = localObject1[j];
      Object localObject2 = io.fabric.sdk.android.c.g();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Adding file ");
      localStringBuilder.append(localk.getName());
      localStringBuilder.append(" to report ");
      localStringBuilder.append(paramReport.getIdentifier());
      ((k)localObject2).a("CrashlyticsCore", localStringBuilder.toString());
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("report[file");
      ((StringBuilder)localObject2).append(k);
      ((StringBuilder)localObject2).append("]");
      paramHttpRequest.a(((StringBuilder)localObject2).toString(), localk.getName(), "application/octet-stream", localk);
      k++;
      j++;
    }
    return paramHttpRequest;
  }
  
  public boolean invoke(CreateReportRequest paramCreateReportRequest)
  {
    paramCreateReportRequest = applyMultipartDataTo(applyHeadersTo(getHttpRequest(), paramCreateReportRequest), paramCreateReportRequest.report);
    Object localObject1 = io.fabric.sdk.android.c.g();
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Sending report to: ");
    ((StringBuilder)localObject2).append(getUrl());
    ((k)localObject1).a("CrashlyticsCore", ((StringBuilder)localObject2).toString());
    int i = paramCreateReportRequest.b();
    localObject2 = io.fabric.sdk.android.c.g();
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Create report request ID: ");
    ((StringBuilder)localObject1).append(paramCreateReportRequest.b("X-REQUEST-ID"));
    ((k)localObject2).a("CrashlyticsCore", ((StringBuilder)localObject1).toString());
    paramCreateReportRequest = io.fabric.sdk.android.c.g();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Result was: ");
    ((StringBuilder)localObject2).append(i);
    paramCreateReportRequest.a("CrashlyticsCore", ((StringBuilder)localObject2).toString());
    boolean bool;
    if (u.a(i) == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:              ~/com/crashlytics/android/core/DefaultCreateReportSpiCall.class
 *
 * Reversed by:           J
 */