package android.support.v4.util;

import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AtomicFile
{
  private final File mBackupName;
  private final File mBaseName;
  
  public AtomicFile(File paramFile)
  {
    this.mBaseName = paramFile;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramFile.getPath());
    localStringBuilder.append(".bak");
    this.mBackupName = new File(localStringBuilder.toString());
  }
  
  static boolean sync(FileOutputStream paramFileOutputStream)
  {
    if (paramFileOutputStream != null) {
      try
      {
        paramFileOutputStream.getFD().sync();
      }
      catch (IOException paramFileOutputStream)
      {
        return false;
      }
    }
    return true;
  }
  
  public void delete()
  {
    this.mBaseName.delete();
    this.mBackupName.delete();
  }
  
  public void failWrite(FileOutputStream paramFileOutputStream)
  {
    if (paramFileOutputStream != null)
    {
      sync(paramFileOutputStream);
      try
      {
        paramFileOutputStream.close();
        this.mBaseName.delete();
        this.mBackupName.renameTo(this.mBaseName);
      }
      catch (IOException paramFileOutputStream)
      {
        Log.w("AtomicFile", "failWrite: Got exception:", paramFileOutputStream);
      }
    }
  }
  
  public void finishWrite(FileOutputStream paramFileOutputStream)
  {
    if (paramFileOutputStream != null)
    {
      sync(paramFileOutputStream);
      try
      {
        paramFileOutputStream.close();
        this.mBackupName.delete();
      }
      catch (IOException paramFileOutputStream)
      {
        Log.w("AtomicFile", "finishWrite: Got exception:", paramFileOutputStream);
      }
    }
  }
  
  public File getBaseFile()
  {
    return this.mBaseName;
  }
  
  public FileInputStream openRead()
  {
    if (this.mBackupName.exists())
    {
      this.mBaseName.delete();
      this.mBackupName.renameTo(this.mBaseName);
    }
    return new FileInputStream(this.mBaseName);
  }
  
  /* Error */
  public byte[] readFully()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 95	android/support/v4/util/AtomicFile:openRead	()Ljava/io/FileInputStream;
    //   4: astore_1
    //   5: aload_1
    //   6: invokevirtual 99	java/io/FileInputStream:available	()I
    //   9: newarray <illegal type>
    //   11: astore_2
    //   12: iconst_0
    //   13: istore_3
    //   14: aload_1
    //   15: aload_2
    //   16: iload_3
    //   17: aload_2
    //   18: arraylength
    //   19: iload_3
    //   20: isub
    //   21: invokevirtual 103	java/io/FileInputStream:read	([BII)I
    //   24: istore 4
    //   26: iload 4
    //   28: ifgt +9 -> 37
    //   31: aload_1
    //   32: invokevirtual 104	java/io/FileInputStream:close	()V
    //   35: aload_2
    //   36: areturn
    //   37: iload_3
    //   38: iload 4
    //   40: iadd
    //   41: istore 4
    //   43: aload_1
    //   44: invokevirtual 99	java/io/FileInputStream:available	()I
    //   47: istore 5
    //   49: iload 4
    //   51: istore_3
    //   52: iload 5
    //   54: aload_2
    //   55: arraylength
    //   56: iload 4
    //   58: isub
    //   59: if_icmple -45 -> 14
    //   62: iload 5
    //   64: iload 4
    //   66: iadd
    //   67: newarray <illegal type>
    //   69: astore 6
    //   71: aload_2
    //   72: iconst_0
    //   73: aload 6
    //   75: iconst_0
    //   76: iload 4
    //   78: invokestatic 110	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   81: aload 6
    //   83: astore_2
    //   84: iload 4
    //   86: istore_3
    //   87: goto -73 -> 14
    //   90: astore_2
    //   91: aload_1
    //   92: invokevirtual 104	java/io/FileInputStream:close	()V
    //   95: aload_2
    //   96: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	97	0	this	AtomicFile
    //   4	88	1	localFileInputStream	FileInputStream
    //   11	73	2	localObject1	Object
    //   90	6	2	localObject2	Object
    //   13	74	3	i	int
    //   24	61	4	j	int
    //   47	20	5	k	int
    //   69	13	6	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   5	12	90	finally
    //   14	26	90	finally
    //   43	49	90	finally
    //   52	81	90	finally
  }
  
  public FileOutputStream startWrite()
  {
    Object localObject;
    if (this.mBaseName.exists()) {
      if (!this.mBackupName.exists())
      {
        if (!this.mBaseName.renameTo(this.mBackupName))
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Couldn't rename file ");
          ((StringBuilder)localObject).append(this.mBaseName);
          ((StringBuilder)localObject).append(" to backup file ");
          ((StringBuilder)localObject).append(this.mBackupName);
          Log.w("AtomicFile", ((StringBuilder)localObject).toString());
        }
      }
      else {
        this.mBaseName.delete();
      }
    }
    try
    {
      localObject = new java/io/FileOutputStream;
      ((FileOutputStream)localObject).<init>(this.mBaseName);
    }
    catch (FileNotFoundException localFileNotFoundException1)
    {
      if (!this.mBaseName.getParentFile().mkdirs()) {
        break label175;
      }
    }
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(this.mBaseName);
      return localFileOutputStream;
    }
    catch (FileNotFoundException localFileNotFoundException2)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Couldn't create ");
      localStringBuilder.append(this.mBaseName);
      throw new IOException(localStringBuilder.toString());
    }
    label175:
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Couldn't create directory ");
    localStringBuilder.append(this.mBaseName);
    throw new IOException(localStringBuilder.toString());
  }
}


/* Location:              ~/android/support/v4/util/AtomicFile.class
 *
 * Reversed by:           J
 */