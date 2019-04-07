package org.eclipse.paho.client.mqttv3.internal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;

public class FileLock
{
  private RandomAccessFile file;
  private Object fileLock;
  private File lockFile;
  
  public FileLock(File paramFile, String paramString)
  {
    this.lockFile = new File(paramFile, paramString);
    if (ExceptionHelper.isClassAvailable("java.nio.channels.FileLock"))
    {
      try
      {
        paramFile = new java/io/RandomAccessFile;
        paramFile.<init>(this.lockFile, "rw");
        this.file = paramFile;
        paramFile = this.file.getClass().getMethod("getChannel", new Class[0]).invoke(this.file, new Object[0]);
        this.fileLock = paramFile.getClass().getMethod("tryLock", new Class[0]).invoke(paramFile, new Object[0]);
      }
      catch (IllegalAccessException paramFile)
      {
        this.fileLock = null;
      }
      catch (IllegalArgumentException paramFile)
      {
        this.fileLock = null;
      }
      catch (NoSuchMethodException paramFile)
      {
        this.fileLock = null;
      }
      if (this.fileLock == null)
      {
        release();
        throw new Exception("Problem obtaining file lock");
      }
    }
  }
  
  public void release()
  {
    try
    {
      if (this.fileLock != null)
      {
        this.fileLock.getClass().getMethod("release", new Class[0]).invoke(this.fileLock, new Object[0]);
        this.fileLock = null;
      }
      localObject = this.file;
      if (localObject == null) {}
    }
    catch (Exception localException)
    {
      try
      {
        ((RandomAccessFile)localObject).close();
        this.file = null;
        Object localObject = this.lockFile;
        if ((localObject != null) && (((File)localObject).exists())) {
          this.lockFile.delete();
        }
        this.lockFile = null;
        return;
        localException = localException;
      }
      catch (IOException localIOException)
      {
        for (;;) {}
      }
    }
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/internal/FileLock.class
 *
 * Reversed by:           J
 */