package com.crashlytics.android.core;

import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.i;
import io.fabric.sdk.android.services.b.t;
import io.fabric.sdk.android.services.b.t.c;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

class QueueFileLogStore
  implements FileLogStore
{
  private t logFile;
  private final int maxLogSize;
  private final File workingFile;
  
  public QueueFileLogStore(File paramFile, int paramInt)
  {
    this.workingFile = paramFile;
    this.maxLogSize = paramInt;
  }
  
  private void doWriteToLog(long paramLong, String paramString)
  {
    if (this.logFile == null) {
      return;
    }
    String str = paramString;
    if (paramString == null) {
      str = "null";
    }
    try
    {
      int i = this.maxLogSize / 4;
      paramString = str;
      if (str.length() > i)
      {
        paramString = new java/lang/StringBuilder;
        paramString.<init>();
        paramString.append("...");
        paramString.append(str.substring(str.length() - i));
        paramString = paramString.toString();
      }
      paramString = paramString.replaceAll("\r", " ").replaceAll("\n", " ");
      paramString = String.format(Locale.US, "%d %s%n", new Object[] { Long.valueOf(paramLong), paramString }).getBytes("UTF-8");
      this.logFile.a(paramString);
      while ((!this.logFile.b()) && (this.logFile.a() > this.maxLogSize)) {
        this.logFile.c();
      }
      return;
    }
    catch (IOException paramString)
    {
      c.g().e("CrashlyticsCore", "There was a problem writing to the Crashlytics log.", paramString);
    }
  }
  
  private LogBytes getLogBytes()
  {
    if (!this.workingFile.exists()) {
      return null;
    }
    openLogFile();
    Object localObject = this.logFile;
    if (localObject == null) {
      return null;
    }
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = 0;
    localObject = new byte[((t)localObject).a()];
    try
    {
      t localt = this.logFile;
      t.c local1 = new com/crashlytics/android/core/QueueFileLogStore$1;
      local1.<init>(this, (byte[])localObject, arrayOfInt);
      localt.a(local1);
    }
    catch (IOException localIOException)
    {
      c.g().e("CrashlyticsCore", "A problem occurred while reading the Crashlytics log file.", localIOException);
    }
    return new LogBytes((byte[])localObject, arrayOfInt[0]);
  }
  
  private void openLogFile()
  {
    if (this.logFile == null) {
      try
      {
        localObject = new io/fabric/sdk/android/services/b/t;
        ((t)localObject).<init>(this.workingFile);
        this.logFile = ((t)localObject);
      }
      catch (IOException localIOException)
      {
        Object localObject = c.g();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Could not open log file: ");
        localStringBuilder.append(this.workingFile);
        ((k)localObject).e("CrashlyticsCore", localStringBuilder.toString(), localIOException);
      }
    }
  }
  
  public void closeLogFile()
  {
    i.a(this.logFile, "There was a problem closing the Crashlytics log file.");
    this.logFile = null;
  }
  
  public void deleteLogFile()
  {
    closeLogFile();
    this.workingFile.delete();
  }
  
  public ByteString getLogAsByteString()
  {
    Object localObject = getLogBytes();
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = ByteString.copyFrom(((LogBytes)localObject).bytes, 0, ((LogBytes)localObject).offset);
    }
    return (ByteString)localObject;
  }
  
  public byte[] getLogAsBytes()
  {
    Object localObject = getLogBytes();
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = ((LogBytes)localObject).bytes;
    }
    return (byte[])localObject;
  }
  
  public void writeToLog(long paramLong, String paramString)
  {
    openLogFile();
    doWriteToLog(paramLong, paramString);
  }
  
  public class LogBytes
  {
    public final byte[] bytes;
    public final int offset;
    
    public LogBytes(byte[] paramArrayOfByte, int paramInt)
    {
      this.bytes = paramArrayOfByte;
      this.offset = paramInt;
    }
  }
}


/* Location:              ~/com/crashlytics/android/core/QueueFileLogStore.class
 *
 * Reversed by:           J
 */