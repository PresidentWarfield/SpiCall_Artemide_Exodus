package com.crashlytics.android.core;

import io.fabric.sdk.android.h;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.a;
import io.fabric.sdk.android.services.b.u;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.d;
import java.io.File;

class NativeCreateReportSpiCall
  extends a
  implements CreateReportSpiCall
{
  private static final String APP_META_FILE_MULTIPART_PARAM = "app_meta_file";
  private static final String BINARY_IMAGES_FILE_MULTIPART_PARAM = "binary_images_file";
  private static final String DEVICE_META_FILE_MULTIPART_PARAM = "device_meta_file";
  private static final String GZIP_FILE_CONTENT_TYPE = "application/octet-stream";
  private static final String KEYS_FILE_MULTIPART_PARAM = "keys_file";
  private static final String LOGS_FILE_MULTIPART_PARAM = "logs_file";
  private static final String METADATA_FILE_MULTIPART_PARAM = "crash_meta_file";
  private static final String MINIDUMP_FILE_MULTIPART_PARAM = "minidump_file";
  private static final String OS_META_FILE_MULTIPART_PARAM = "os_meta_file";
  private static final String REPORT_IDENTIFIER_PARAM = "report_id";
  private static final String SESSION_META_FILE_MULTIPART_PARAM = "session_meta_file";
  private static final String USER_META_FILE_MULTIPART_PARAM = "user_meta_file";
  
  public NativeCreateReportSpiCall(h paramh, String paramString1, String paramString2, d paramd)
  {
    super(paramh, paramString1, paramString2, paramd, io.fabric.sdk.android.services.network.c.b);
  }
  
  private HttpRequest applyHeadersTo(HttpRequest paramHttpRequest, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Crashlytics Android SDK/");
    localStringBuilder.append(this.kit.getVersion());
    paramHttpRequest.a("User-Agent", localStringBuilder.toString()).a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").a("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion()).a("X-CRASHLYTICS-API-KEY", paramString);
    return paramHttpRequest;
  }
  
  private HttpRequest applyMultipartDataTo(HttpRequest paramHttpRequest, Report paramReport)
  {
    paramHttpRequest.e("report_id", paramReport.getIdentifier());
    for (paramReport : paramReport.getFiles()) {
      if (paramReport.getName().equals("minidump")) {
        paramHttpRequest.a("minidump_file", paramReport.getName(), "application/octet-stream", paramReport);
      } else if (paramReport.getName().equals("metadata")) {
        paramHttpRequest.a("crash_meta_file", paramReport.getName(), "application/octet-stream", paramReport);
      } else if (paramReport.getName().equals("binaryImages")) {
        paramHttpRequest.a("binary_images_file", paramReport.getName(), "application/octet-stream", paramReport);
      } else if (paramReport.getName().equals("session")) {
        paramHttpRequest.a("session_meta_file", paramReport.getName(), "application/octet-stream", paramReport);
      } else if (paramReport.getName().equals("app")) {
        paramHttpRequest.a("app_meta_file", paramReport.getName(), "application/octet-stream", paramReport);
      } else if (paramReport.getName().equals("device")) {
        paramHttpRequest.a("device_meta_file", paramReport.getName(), "application/octet-stream", paramReport);
      } else if (paramReport.getName().equals("os")) {
        paramHttpRequest.a("os_meta_file", paramReport.getName(), "application/octet-stream", paramReport);
      } else if (paramReport.getName().equals("user")) {
        paramHttpRequest.a("user_meta_file", paramReport.getName(), "application/octet-stream", paramReport);
      } else if (paramReport.getName().equals("logs")) {
        paramHttpRequest.a("logs_file", paramReport.getName(), "application/octet-stream", paramReport);
      } else if (paramReport.getName().equals("keys")) {
        paramHttpRequest.a("keys_file", paramReport.getName(), "application/octet-stream", paramReport);
      }
    }
    return paramHttpRequest;
  }
  
  public boolean invoke(CreateReportRequest paramCreateReportRequest)
  {
    paramCreateReportRequest = applyMultipartDataTo(applyHeadersTo(getHttpRequest(), paramCreateReportRequest.apiKey), paramCreateReportRequest.report);
    k localk = io.fabric.sdk.android.c.g();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Sending report to: ");
    localStringBuilder.append(getUrl());
    localk.a("CrashlyticsCore", localStringBuilder.toString());
    int i = paramCreateReportRequest.b();
    paramCreateReportRequest = io.fabric.sdk.android.c.g();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("Result was: ");
    localStringBuilder.append(i);
    paramCreateReportRequest.a("CrashlyticsCore", localStringBuilder.toString());
    boolean bool;
    if (u.a(i) == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:              ~/com/crashlytics/android/core/NativeCreateReportSpiCall.class
 *
 * Reversed by:           J
 */