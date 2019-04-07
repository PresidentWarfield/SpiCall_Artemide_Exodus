package com.crashlytics.android.core;

import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class InvalidSessionReport
  implements Report
{
  private final Map<String, String> customHeaders;
  private final File[] files;
  private final String identifier;
  
  public InvalidSessionReport(String paramString, File[] paramArrayOfFile)
  {
    this.files = paramArrayOfFile;
    this.customHeaders = new HashMap(ReportUploader.HEADER_INVALID_CLS_FILE);
    this.identifier = paramString;
  }
  
  public Map<String, String> getCustomHeaders()
  {
    return Collections.unmodifiableMap(this.customHeaders);
  }
  
  public File getFile()
  {
    return this.files[0];
  }
  
  public String getFileName()
  {
    return this.files[0].getName();
  }
  
  public File[] getFiles()
  {
    return this.files;
  }
  
  public String getIdentifier()
  {
    return this.identifier;
  }
  
  public Report.Type getType()
  {
    return Report.Type.JAVA;
  }
  
  public void remove()
  {
    for (File localFile : this.files)
    {
      k localk = c.g();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Removing invalid report file at ");
      localStringBuilder.append(localFile.getPath());
      localk.a("CrashlyticsCore", localStringBuilder.toString());
      localFile.delete();
    }
  }
}


/* Location:              ~/com/crashlytics/android/core/InvalidSessionReport.class
 *
 * Reversed by:           J
 */