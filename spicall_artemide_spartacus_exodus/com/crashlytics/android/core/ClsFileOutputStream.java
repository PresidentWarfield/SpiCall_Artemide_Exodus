package com.crashlytics.android.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

class ClsFileOutputStream
  extends FileOutputStream
{
  public static final String IN_PROGRESS_SESSION_FILE_EXTENSION = ".cls_temp";
  public static final String SESSION_FILE_EXTENSION = ".cls";
  public static final FilenameFilter TEMP_FILENAME_FILTER = new FilenameFilter()
  {
    public boolean accept(File paramAnonymousFile, String paramAnonymousString)
    {
      return paramAnonymousString.endsWith(".cls_temp");
    }
  };
  private boolean closed = false;
  private File complete;
  private File inProgress;
  private final String root;
  
  public ClsFileOutputStream(File paramFile, String paramString)
  {
    super(new File(paramFile, localStringBuilder.toString()));
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramFile);
    localStringBuilder.append(File.separator);
    localStringBuilder.append(paramString);
    this.root = localStringBuilder.toString();
    paramFile = new StringBuilder();
    paramFile.append(this.root);
    paramFile.append(".cls_temp");
    this.inProgress = new File(paramFile.toString());
  }
  
  public ClsFileOutputStream(String paramString1, String paramString2)
  {
    this(new File(paramString1), paramString2);
  }
  
  public void close()
  {
    try
    {
      boolean bool = this.closed;
      if (bool) {
        return;
      }
      this.closed = true;
      super.flush();
      super.close();
      File localFile = new java/io/File;
      Object localObject1 = new java/lang/StringBuilder;
      ((StringBuilder)localObject1).<init>();
      ((StringBuilder)localObject1).append(this.root);
      ((StringBuilder)localObject1).append(".cls");
      localFile.<init>(((StringBuilder)localObject1).toString());
      if (this.inProgress.renameTo(localFile))
      {
        this.inProgress = null;
        this.complete = localFile;
        return;
      }
      localObject1 = "";
      if (!localFile.exists())
      {
        if (!this.inProgress.exists()) {
          localObject1 = " (source does not exist)";
        }
      }
      else {
        localObject1 = " (target already exists)";
      }
      IOException localIOException = new java/io/IOException;
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("Could not rename temp file: ");
      localStringBuilder.append(this.inProgress);
      localStringBuilder.append(" -> ");
      localStringBuilder.append(localFile);
      localStringBuilder.append((String)localObject1);
      localIOException.<init>(localStringBuilder.toString());
      throw localIOException;
    }
    finally {}
  }
  
  public void closeInProgressStream()
  {
    if (this.closed) {
      return;
    }
    this.closed = true;
    super.flush();
    super.close();
  }
  
  public File getCompleteFile()
  {
    return this.complete;
  }
  
  public File getInProgressFile()
  {
    return this.inProgress;
  }
}


/* Location:              ~/com/crashlytics/android/core/ClsFileOutputStream.class
 *
 * Reversed by:           J
 */