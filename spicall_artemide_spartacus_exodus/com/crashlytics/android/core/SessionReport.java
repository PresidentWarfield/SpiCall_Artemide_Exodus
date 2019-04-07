package com.crashlytics.android.core;

import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class SessionReport
  implements Report
{
  private final Map<String, String> customHeaders;
  private final File file;
  private final File[] files;
  
  public SessionReport(File paramFile)
  {
    this(paramFile, Collections.emptyMap());
  }
  
  public SessionReport(File paramFile, Map<String, String> paramMap)
  {
    this.file = paramFile;
    this.files = new File[] { paramFile };
    this.customHeaders = new HashMap(paramMap);
    if (this.file.length() == 0L) {
      this.customHeaders.putAll(ReportUploader.HEADER_INVALID_CLS_FILE);
    }
  }
  
  public Map<String, String> getCustomHeaders()
  {
    return Collections.unmodifiableMap(this.customHeaders);
  }
  
  public File getFile()
  {
    return this.file;
  }
  
  public String getFileName()
  {
    return getFile().getName();
  }
  
  public File[] getFiles()
  {
    return this.files;
  }
  
  public String getIdentifier()
  {
    String str = getFileName();
    return str.substring(0, str.lastIndexOf('.'));
  }
  
  public Report.Type getType()
  {
    return Report.Type.JAVA;
  }
  
  public void remove()
  {
    k localk = c.g();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Removing report at ");
    localStringBuilder.append(this.file.getPath());
    localk.a("CrashlyticsCore", localStringBuilder.toString());
    this.file.delete();
  }
}


/* Location:              ~/com/crashlytics/android/core/SessionReport.class
 *
 * Reversed by:           J
 */