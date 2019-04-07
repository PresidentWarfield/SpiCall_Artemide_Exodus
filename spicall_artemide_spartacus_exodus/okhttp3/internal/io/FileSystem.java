package okhttp3.internal.io;

import a.l;
import a.r;
import a.s;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract interface FileSystem
{
  public static final FileSystem SYSTEM = new FileSystem()
  {
    public r appendingSink(File paramAnonymousFile)
    {
      try
      {
        r localr = l.c(paramAnonymousFile);
        return localr;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        paramAnonymousFile.getParentFile().mkdirs();
      }
      return l.c(paramAnonymousFile);
    }
    
    public void delete(File paramAnonymousFile)
    {
      if ((!paramAnonymousFile.delete()) && (paramAnonymousFile.exists()))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("failed to delete ");
        localStringBuilder.append(paramAnonymousFile);
        throw new IOException(localStringBuilder.toString());
      }
    }
    
    public void deleteContents(File paramAnonymousFile)
    {
      Object localObject = paramAnonymousFile.listFiles();
      if (localObject != null)
      {
        int i = localObject.length;
        int j = 0;
        while (j < i)
        {
          paramAnonymousFile = localObject[j];
          if (paramAnonymousFile.isDirectory()) {
            deleteContents(paramAnonymousFile);
          }
          if (paramAnonymousFile.delete())
          {
            j++;
          }
          else
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("failed to delete ");
            ((StringBuilder)localObject).append(paramAnonymousFile);
            throw new IOException(((StringBuilder)localObject).toString());
          }
        }
        return;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("not a readable directory: ");
      ((StringBuilder)localObject).append(paramAnonymousFile);
      throw new IOException(((StringBuilder)localObject).toString());
    }
    
    public boolean exists(File paramAnonymousFile)
    {
      return paramAnonymousFile.exists();
    }
    
    public void rename(File paramAnonymousFile1, File paramAnonymousFile2)
    {
      delete(paramAnonymousFile2);
      if (paramAnonymousFile1.renameTo(paramAnonymousFile2)) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("failed to rename ");
      localStringBuilder.append(paramAnonymousFile1);
      localStringBuilder.append(" to ");
      localStringBuilder.append(paramAnonymousFile2);
      throw new IOException(localStringBuilder.toString());
    }
    
    public r sink(File paramAnonymousFile)
    {
      try
      {
        r localr = l.b(paramAnonymousFile);
        return localr;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        paramAnonymousFile.getParentFile().mkdirs();
      }
      return l.b(paramAnonymousFile);
    }
    
    public long size(File paramAnonymousFile)
    {
      return paramAnonymousFile.length();
    }
    
    public s source(File paramAnonymousFile)
    {
      return l.a(paramAnonymousFile);
    }
  };
  
  public abstract r appendingSink(File paramFile);
  
  public abstract void delete(File paramFile);
  
  public abstract void deleteContents(File paramFile);
  
  public abstract boolean exists(File paramFile);
  
  public abstract void rename(File paramFile1, File paramFile2);
  
  public abstract r sink(File paramFile);
  
  public abstract long size(File paramFile);
  
  public abstract s source(File paramFile);
}


/* Location:              ~/okhttp3/internal/io/FileSystem.class
 *
 * Reversed by:           J
 */