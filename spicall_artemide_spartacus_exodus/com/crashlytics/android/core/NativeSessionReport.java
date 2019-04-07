package com.crashlytics.android.core;

import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.io.File;
import java.util.Map;

class NativeSessionReport
  implements Report
{
  private final File reportDirectory;
  
  public NativeSessionReport(File paramFile)
  {
    this.reportDirectory = paramFile;
  }
  
  public Map<String, String> getCustomHeaders()
  {
    return null;
  }
  
  public File getFile()
  {
    return null;
  }
  
  public String getFileName()
  {
    return null;
  }
  
  public File[] getFiles()
  {
    return this.reportDirectory.listFiles();
  }
  
  public String getIdentifier()
  {
    return this.reportDirectory.getName();
  }
  
  public Report.Type getType()
  {
    return Report.Type.NATIVE;
  }
  
  public void remove()
  {
    for (File localFile : getFiles())
    {
      localObject1 = c.g();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Removing native report file at ");
      ((StringBuilder)localObject2).append(localFile.getPath());
      ((k)localObject1).a("CrashlyticsCore", ((StringBuilder)localObject2).toString());
      localFile.delete();
    }
    Object localObject2 = c.g();
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Removing native report directory at ");
    ((StringBuilder)localObject1).append(this.reportDirectory);
    ((k)localObject2).a("CrashlyticsCore", ((StringBuilder)localObject1).toString());
    this.reportDirectory.delete();
  }
}


/* Location:              ~/com/crashlytics/android/core/NativeSessionReport.class
 *
 * Reversed by:           J
 */