package com.crashlytics.android.core;

abstract interface FileLogStore
{
  public abstract void closeLogFile();
  
  public abstract void deleteLogFile();
  
  public abstract ByteString getLogAsByteString();
  
  public abstract byte[] getLogAsBytes();
  
  public abstract void writeToLog(long paramLong, String paramString);
}


/* Location:              ~/com/crashlytics/android/core/FileLogStore.class
 *
 * Reversed by:           J
 */