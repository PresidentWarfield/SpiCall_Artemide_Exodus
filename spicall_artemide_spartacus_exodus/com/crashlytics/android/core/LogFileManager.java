package com.crashlytics.android.core;

import android.content.Context;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.i;
import java.io.File;
import java.util.Set;

class LogFileManager
{
  private static final String COLLECT_CUSTOM_LOGS = "com.crashlytics.CollectCustomLogs";
  private static final String LOGFILE_EXT = ".temp";
  private static final String LOGFILE_PREFIX = "crashlytics-userlog-";
  static final int MAX_LOG_SIZE = 65536;
  private static final NoopLogStore NOOP_LOG_STORE = new NoopLogStore(null);
  private final Context context;
  private FileLogStore currentLog;
  private final DirectoryProvider directoryProvider;
  
  LogFileManager(Context paramContext, DirectoryProvider paramDirectoryProvider)
  {
    this(paramContext, paramDirectoryProvider, null);
  }
  
  LogFileManager(Context paramContext, DirectoryProvider paramDirectoryProvider, String paramString)
  {
    this.context = paramContext;
    this.directoryProvider = paramDirectoryProvider;
    this.currentLog = NOOP_LOG_STORE;
    setCurrentSession(paramString);
  }
  
  private String getSessionIdForFile(File paramFile)
  {
    paramFile = paramFile.getName();
    int i = paramFile.lastIndexOf(".temp");
    if (i == -1) {
      return paramFile;
    }
    return paramFile.substring(20, i);
  }
  
  private File getWorkingFileForSession(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("crashlytics-userlog-");
    localStringBuilder.append(paramString);
    localStringBuilder.append(".temp");
    paramString = localStringBuilder.toString();
    return new File(this.directoryProvider.getLogFileDir(), paramString);
  }
  
  void clearLog()
  {
    this.currentLog.deleteLogFile();
  }
  
  void discardOldLogFiles(Set<String> paramSet)
  {
    File[] arrayOfFile = this.directoryProvider.getLogFileDir().listFiles();
    if (arrayOfFile != null)
    {
      int i = arrayOfFile.length;
      for (int j = 0; j < i; j++)
      {
        File localFile = arrayOfFile[j];
        if (!paramSet.contains(getSessionIdForFile(localFile))) {
          localFile.delete();
        }
      }
    }
  }
  
  ByteString getByteStringForLog()
  {
    return this.currentLog.getLogAsByteString();
  }
  
  byte[] getBytesForLog()
  {
    return this.currentLog.getLogAsBytes();
  }
  
  final void setCurrentSession(String paramString)
  {
    this.currentLog.closeLogFile();
    this.currentLog = NOOP_LOG_STORE;
    if (paramString == null) {
      return;
    }
    if (!i.a(this.context, "com.crashlytics.CollectCustomLogs", true))
    {
      c.g().a("CrashlyticsCore", "Preferences requested no custom logs. Aborting log file creation.");
      return;
    }
    setLogFile(getWorkingFileForSession(paramString), 65536);
  }
  
  void setLogFile(File paramFile, int paramInt)
  {
    this.currentLog = new QueueFileLogStore(paramFile, paramInt);
  }
  
  void writeToLog(long paramLong, String paramString)
  {
    this.currentLog.writeToLog(paramLong, paramString);
  }
  
  public static abstract interface DirectoryProvider
  {
    public abstract File getLogFileDir();
  }
  
  private static final class NoopLogStore
    implements FileLogStore
  {
    public void closeLogFile() {}
    
    public void deleteLogFile() {}
    
    public ByteString getLogAsByteString()
    {
      return null;
    }
    
    public byte[] getLogAsBytes()
    {
      return null;
    }
    
    public void writeToLog(long paramLong, String paramString) {}
  }
}


/* Location:              ~/com/crashlytics/android/core/LogFileManager.class
 *
 * Reversed by:           J
 */