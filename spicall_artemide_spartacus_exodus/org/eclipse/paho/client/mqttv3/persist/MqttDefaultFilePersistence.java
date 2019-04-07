package org.eclipse.paho.client.mqttv3.persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.internal.FileLock;
import org.eclipse.paho.client.mqttv3.internal.MqttPersistentData;

public class MqttDefaultFilePersistence
  implements MqttClientPersistence
{
  private static FilenameFilter FILENAME_FILTER;
  private static final String LOCK_FILENAME = ".lck";
  private static final String MESSAGE_BACKUP_FILE_EXTENSION = ".bup";
  private static final String MESSAGE_FILE_EXTENSION = ".msg";
  private File clientDir = null;
  private File dataDir;
  private FileLock fileLock = null;
  
  public MqttDefaultFilePersistence()
  {
    this(System.getProperty("user.dir"));
  }
  
  public MqttDefaultFilePersistence(String paramString)
  {
    this.dataDir = new File(paramString);
  }
  
  private void checkIsOpen()
  {
    if (this.clientDir != null) {
      return;
    }
    throw new MqttPersistenceException();
  }
  
  private static FilenameFilter getFilenameFilter()
  {
    if (FILENAME_FILTER == null) {
      FILENAME_FILTER = new PersistanceFileNameFilter(".msg");
    }
    return FILENAME_FILTER;
  }
  
  private File[] getFiles()
  {
    checkIsOpen();
    File[] arrayOfFile = this.clientDir.listFiles(getFilenameFilter());
    if (arrayOfFile != null) {
      return arrayOfFile;
    }
    throw new MqttPersistenceException();
  }
  
  private boolean isSafeChar(char paramChar)
  {
    boolean bool;
    if ((!Character.isJavaIdentifierPart(paramChar)) && (paramChar != '-')) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private void restoreBackups(File paramFile)
  {
    File[] arrayOfFile = paramFile.listFiles(new PersistanceFileFilter(".bup"));
    if (arrayOfFile != null)
    {
      for (int i = 0; i < arrayOfFile.length; i++)
      {
        File localFile = new File(paramFile, arrayOfFile[i].getName().substring(0, arrayOfFile[i].getName().length() - 4));
        if (!arrayOfFile[i].renameTo(localFile))
        {
          localFile.delete();
          arrayOfFile[i].renameTo(localFile);
        }
      }
      return;
    }
    throw new MqttPersistenceException();
  }
  
  public void clear()
  {
    checkIsOpen();
    File[] arrayOfFile = getFiles();
    for (int i = 0; i < arrayOfFile.length; i++) {
      arrayOfFile[i].delete();
    }
    this.clientDir.delete();
  }
  
  public void close()
  {
    try
    {
      if (this.fileLock != null) {
        this.fileLock.release();
      }
      if (getFiles().length == 0) {
        this.clientDir.delete();
      }
      this.clientDir = null;
      return;
    }
    finally {}
  }
  
  public boolean containsKey(String paramString)
  {
    checkIsOpen();
    File localFile = this.clientDir;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(".msg");
    return new File(localFile, localStringBuilder.toString()).exists();
  }
  
  public MqttPersistable get(String paramString)
  {
    checkIsOpen();
    try
    {
      Object localObject1 = new java/io/File;
      File localFile = this.clientDir;
      Object localObject2 = new java/lang/StringBuilder;
      ((StringBuilder)localObject2).<init>();
      ((StringBuilder)localObject2).append(paramString);
      ((StringBuilder)localObject2).append(".msg");
      ((File)localObject1).<init>(localFile, ((StringBuilder)localObject2).toString());
      localObject2 = new java/io/FileInputStream;
      ((FileInputStream)localObject2).<init>((File)localObject1);
      int i = ((FileInputStream)localObject2).available();
      localObject1 = new byte[i];
      int j = 0;
      while (j < i) {
        j += ((FileInputStream)localObject2).read((byte[])localObject1, j, i - j);
      }
      ((FileInputStream)localObject2).close();
      paramString = new MqttPersistentData(paramString, (byte[])localObject1, 0, localObject1.length, null, 0, 0);
      return paramString;
    }
    catch (IOException paramString)
    {
      throw new MqttPersistenceException(paramString);
    }
  }
  
  public Enumeration keys()
  {
    checkIsOpen();
    File[] arrayOfFile = getFiles();
    Vector localVector = new Vector(arrayOfFile.length);
    for (int i = 0; i < arrayOfFile.length; i++)
    {
      String str = arrayOfFile[i].getName();
      localVector.addElement(str.substring(0, str.length() - 4));
    }
    return localVector.elements();
  }
  
  /* Error */
  public void open(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 48	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:dataDir	Ljava/io/File;
    //   4: invokevirtual 133	java/io/File:exists	()Z
    //   7: ifeq +24 -> 31
    //   10: aload_0
    //   11: getfield 48	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:dataDir	Ljava/io/File;
    //   14: invokevirtual 179	java/io/File:isDirectory	()Z
    //   17: ifeq +6 -> 23
    //   20: goto +11 -> 31
    //   23: new 51	org/eclipse/paho/client/mqttv3/MqttPersistenceException
    //   26: dup
    //   27: invokespecial 52	org/eclipse/paho/client/mqttv3/MqttPersistenceException:<init>	()V
    //   30: athrow
    //   31: aload_0
    //   32: getfield 48	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:dataDir	Ljava/io/File;
    //   35: invokevirtual 133	java/io/File:exists	()Z
    //   38: ifne +24 -> 62
    //   41: aload_0
    //   42: getfield 48	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:dataDir	Ljava/io/File;
    //   45: invokevirtual 182	java/io/File:mkdirs	()Z
    //   48: ifeq +6 -> 54
    //   51: goto +11 -> 62
    //   54: new 51	org/eclipse/paho/client/mqttv3/MqttPersistenceException
    //   57: dup
    //   58: invokespecial 52	org/eclipse/paho/client/mqttv3/MqttPersistenceException:<init>	()V
    //   61: athrow
    //   62: aload_0
    //   63: getfield 48	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:dataDir	Ljava/io/File;
    //   66: invokevirtual 185	java/io/File:canWrite	()Z
    //   69: ifeq +191 -> 260
    //   72: new 187	java/lang/StringBuffer
    //   75: dup
    //   76: invokespecial 188	java/lang/StringBuffer:<init>	()V
    //   79: astore_3
    //   80: iconst_0
    //   81: istore 4
    //   83: iconst_0
    //   84: istore 5
    //   86: iload 5
    //   88: aload_1
    //   89: invokevirtual 94	java/lang/String:length	()I
    //   92: if_icmpge +33 -> 125
    //   95: aload_1
    //   96: iload 5
    //   98: invokevirtual 192	java/lang/String:charAt	(I)C
    //   101: istore 6
    //   103: aload_0
    //   104: iload 6
    //   106: invokespecial 194	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:isSafeChar	(C)Z
    //   109: ifeq +10 -> 119
    //   112: aload_3
    //   113: iload 6
    //   115: invokevirtual 197	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   118: pop
    //   119: iinc 5 1
    //   122: goto -36 -> 86
    //   125: aload_3
    //   126: ldc -57
    //   128: invokevirtual 202	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   131: pop
    //   132: iload 4
    //   134: istore 5
    //   136: iload 5
    //   138: aload_2
    //   139: invokevirtual 94	java/lang/String:length	()I
    //   142: if_icmpge +33 -> 175
    //   145: aload_2
    //   146: iload 5
    //   148: invokevirtual 192	java/lang/String:charAt	(I)C
    //   151: istore 6
    //   153: aload_0
    //   154: iload 6
    //   156: invokespecial 194	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:isSafeChar	(C)Z
    //   159: ifeq +10 -> 169
    //   162: aload_3
    //   163: iload 6
    //   165: invokevirtual 197	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   168: pop
    //   169: iinc 5 1
    //   172: goto -36 -> 136
    //   175: aload_0
    //   176: monitorenter
    //   177: aload_0
    //   178: getfield 41	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:clientDir	Ljava/io/File;
    //   181: ifnonnull +44 -> 225
    //   184: aload_3
    //   185: invokevirtual 203	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   188: astore_2
    //   189: new 45	java/io/File
    //   192: astore_1
    //   193: aload_1
    //   194: aload_0
    //   195: getfield 48	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:dataDir	Ljava/io/File;
    //   198: aload_2
    //   199: invokespecial 101	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   202: aload_0
    //   203: aload_1
    //   204: putfield 41	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:clientDir	Ljava/io/File;
    //   207: aload_0
    //   208: getfield 41	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:clientDir	Ljava/io/File;
    //   211: invokevirtual 133	java/io/File:exists	()Z
    //   214: ifne +11 -> 225
    //   217: aload_0
    //   218: getfield 41	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:clientDir	Ljava/io/File;
    //   221: invokevirtual 206	java/io/File:mkdir	()Z
    //   224: pop
    //   225: new 115	org/eclipse/paho/client/mqttv3/internal/FileLock
    //   228: astore_1
    //   229: aload_1
    //   230: aload_0
    //   231: getfield 41	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:clientDir	Ljava/io/File;
    //   234: ldc 12
    //   236: invokespecial 207	org/eclipse/paho/client/mqttv3/internal/FileLock:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   239: aload_0
    //   240: aload_1
    //   241: putfield 43	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:fileLock	Lorg/eclipse/paho/client/mqttv3/internal/FileLock;
    //   244: aload_0
    //   245: aload_0
    //   246: getfield 41	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:clientDir	Ljava/io/File;
    //   249: invokespecial 209	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:restoreBackups	(Ljava/io/File;)V
    //   252: aload_0
    //   253: monitorexit
    //   254: return
    //   255: astore_1
    //   256: aload_0
    //   257: monitorexit
    //   258: aload_1
    //   259: athrow
    //   260: new 51	org/eclipse/paho/client/mqttv3/MqttPersistenceException
    //   263: dup
    //   264: invokespecial 52	org/eclipse/paho/client/mqttv3/MqttPersistenceException:<init>	()V
    //   267: athrow
    //   268: astore_1
    //   269: goto -25 -> 244
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	272	0	this	MqttDefaultFilePersistence
    //   0	272	1	paramString1	String
    //   0	272	2	paramString2	String
    //   79	106	3	localStringBuffer	StringBuffer
    //   81	52	4	i	int
    //   84	86	5	j	int
    //   101	63	6	c	char
    // Exception table:
    //   from	to	target	type
    //   177	225	255	finally
    //   225	244	255	finally
    //   244	254	255	finally
    //   256	258	255	finally
    //   225	244	268	java/lang/Exception
  }
  
  /* Error */
  public void put(String paramString, MqttPersistable paramMqttPersistable)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 63	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:checkIsOpen	()V
    //   4: aload_0
    //   5: getfield 41	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:clientDir	Ljava/io/File;
    //   8: astore_3
    //   9: new 122	java/lang/StringBuilder
    //   12: dup
    //   13: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   16: astore 4
    //   18: aload 4
    //   20: aload_1
    //   21: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: pop
    //   25: aload 4
    //   27: ldc 18
    //   29: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   32: pop
    //   33: new 45	java/io/File
    //   36: dup
    //   37: aload_3
    //   38: aload 4
    //   40: invokevirtual 130	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   43: invokespecial 101	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   46: astore 4
    //   48: aload_0
    //   49: getfield 41	org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence:clientDir	Ljava/io/File;
    //   52: astore_3
    //   53: new 122	java/lang/StringBuilder
    //   56: dup
    //   57: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   60: astore 5
    //   62: aload 5
    //   64: aload_1
    //   65: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   68: pop
    //   69: aload 5
    //   71: ldc 18
    //   73: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: pop
    //   77: aload 5
    //   79: ldc 15
    //   81: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   84: pop
    //   85: new 45	java/io/File
    //   88: dup
    //   89: aload_3
    //   90: aload 5
    //   92: invokevirtual 130	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   95: invokespecial 101	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   98: astore_1
    //   99: aload 4
    //   101: invokevirtual 133	java/io/File:exists	()Z
    //   104: ifeq +24 -> 128
    //   107: aload 4
    //   109: aload_1
    //   110: invokevirtual 105	java/io/File:renameTo	(Ljava/io/File;)Z
    //   113: ifne +15 -> 128
    //   116: aload_1
    //   117: invokevirtual 109	java/io/File:delete	()Z
    //   120: pop
    //   121: aload 4
    //   123: aload_1
    //   124: invokevirtual 105	java/io/File:renameTo	(Ljava/io/File;)Z
    //   127: pop
    //   128: new 213	java/io/FileOutputStream
    //   131: astore_3
    //   132: aload_3
    //   133: aload 4
    //   135: invokespecial 214	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   138: aload_3
    //   139: aload_2
    //   140: invokeinterface 220 1 0
    //   145: aload_2
    //   146: invokeinterface 223 1 0
    //   151: aload_2
    //   152: invokeinterface 226 1 0
    //   157: invokevirtual 230	java/io/FileOutputStream:write	([BII)V
    //   160: aload_2
    //   161: invokeinterface 233 1 0
    //   166: ifnull +25 -> 191
    //   169: aload_3
    //   170: aload_2
    //   171: invokeinterface 233 1 0
    //   176: aload_2
    //   177: invokeinterface 236 1 0
    //   182: aload_2
    //   183: invokeinterface 239 1 0
    //   188: invokevirtual 230	java/io/FileOutputStream:write	([BII)V
    //   191: aload_3
    //   192: invokevirtual 243	java/io/FileOutputStream:getFD	()Ljava/io/FileDescriptor;
    //   195: invokevirtual 248	java/io/FileDescriptor:sync	()V
    //   198: aload_3
    //   199: invokevirtual 249	java/io/FileOutputStream:close	()V
    //   202: aload_1
    //   203: invokevirtual 133	java/io/File:exists	()Z
    //   206: ifeq +8 -> 214
    //   209: aload_1
    //   210: invokevirtual 109	java/io/File:delete	()Z
    //   213: pop
    //   214: aload_1
    //   215: invokevirtual 133	java/io/File:exists	()Z
    //   218: ifeq +25 -> 243
    //   221: aload_1
    //   222: aload 4
    //   224: invokevirtual 105	java/io/File:renameTo	(Ljava/io/File;)Z
    //   227: ifne +16 -> 243
    //   230: aload 4
    //   232: invokevirtual 109	java/io/File:delete	()Z
    //   235: pop
    //   236: aload_1
    //   237: aload 4
    //   239: invokevirtual 105	java/io/File:renameTo	(Ljava/io/File;)Z
    //   242: pop
    //   243: return
    //   244: astore_2
    //   245: goto +15 -> 260
    //   248: astore_2
    //   249: new 51	org/eclipse/paho/client/mqttv3/MqttPersistenceException
    //   252: astore_3
    //   253: aload_3
    //   254: aload_2
    //   255: invokespecial 158	org/eclipse/paho/client/mqttv3/MqttPersistenceException:<init>	(Ljava/lang/Throwable;)V
    //   258: aload_3
    //   259: athrow
    //   260: aload_1
    //   261: invokevirtual 133	java/io/File:exists	()Z
    //   264: ifeq +25 -> 289
    //   267: aload_1
    //   268: aload 4
    //   270: invokevirtual 105	java/io/File:renameTo	(Ljava/io/File;)Z
    //   273: ifne +16 -> 289
    //   276: aload 4
    //   278: invokevirtual 109	java/io/File:delete	()Z
    //   281: pop
    //   282: aload_1
    //   283: aload 4
    //   285: invokevirtual 105	java/io/File:renameTo	(Ljava/io/File;)Z
    //   288: pop
    //   289: aload_2
    //   290: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	291	0	this	MqttDefaultFilePersistence
    //   0	291	1	paramString	String
    //   0	291	2	paramMqttPersistable	MqttPersistable
    //   8	251	3	localObject1	Object
    //   16	268	4	localObject2	Object
    //   60	31	5	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   128	191	244	finally
    //   191	214	244	finally
    //   249	260	244	finally
    //   128	191	248	java/io/IOException
    //   191	214	248	java/io/IOException
  }
  
  public void remove(String paramString)
  {
    checkIsOpen();
    File localFile = this.clientDir;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(".msg");
    paramString = new File(localFile, localStringBuilder.toString());
    if (paramString.exists()) {
      paramString.delete();
    }
  }
}


/* Location:              ~/org/eclipse/paho/client/mqttv3/persist/MqttDefaultFilePersistence.class
 *
 * Reversed by:           J
 */