package okhttp3.internal.cache;

import a.d;
import a.e;
import a.l;
import a.r;
import a.s;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Flushable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.io.FileSystem;

public final class DiskLruCache
  implements Closeable, Flushable
{
  static final long ANY_SEQUENCE_NUMBER = -1L;
  private static final String CLEAN = "CLEAN";
  private static final String DIRTY = "DIRTY";
  static final String JOURNAL_FILE = "journal";
  static final String JOURNAL_FILE_BACKUP = "journal.bkp";
  static final String JOURNAL_FILE_TEMP = "journal.tmp";
  static final Pattern LEGAL_KEY_PATTERN = Pattern.compile("[a-z0-9_-]{1,120}");
  static final String MAGIC = "libcore.io.DiskLruCache";
  private static final String READ = "READ";
  private static final String REMOVE = "REMOVE";
  static final String VERSION_1 = "1";
  private final int appVersion;
  private final Runnable cleanupRunnable = new Runnable()
  {
    public void run()
    {
      synchronized (DiskLruCache.this)
      {
        int i;
        if (!DiskLruCache.this.initialized) {
          i = 1;
        } else {
          i = 0;
        }
        if ((i | DiskLruCache.this.closed) != 0) {
          return;
        }
        try
        {
          DiskLruCache.this.trimToSize();
        }
        catch (IOException localIOException1)
        {
          DiskLruCache.this.mostRecentTrimFailed = true;
        }
        try
        {
          if (DiskLruCache.this.journalRebuildRequired())
          {
            DiskLruCache.this.rebuildJournal();
            DiskLruCache.this.redundantOpCount = 0;
          }
        }
        catch (IOException localIOException2)
        {
          DiskLruCache.this.mostRecentRebuildFailed = true;
          DiskLruCache.this.journalWriter = l.a(l.a());
        }
        return;
      }
    }
  };
  boolean closed;
  final File directory;
  private final Executor executor;
  final FileSystem fileSystem;
  boolean hasJournalErrors;
  boolean initialized;
  private final File journalFile;
  private final File journalFileBackup;
  private final File journalFileTmp;
  d journalWriter;
  final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap(0, 0.75F, true);
  private long maxSize;
  boolean mostRecentRebuildFailed;
  boolean mostRecentTrimFailed;
  private long nextSequenceNumber = 0L;
  int redundantOpCount;
  private long size = 0L;
  final int valueCount;
  
  DiskLruCache(FileSystem paramFileSystem, File paramFile, int paramInt1, int paramInt2, long paramLong, Executor paramExecutor)
  {
    this.fileSystem = paramFileSystem;
    this.directory = paramFile;
    this.appVersion = paramInt1;
    this.journalFile = new File(paramFile, "journal");
    this.journalFileTmp = new File(paramFile, "journal.tmp");
    this.journalFileBackup = new File(paramFile, "journal.bkp");
    this.valueCount = paramInt2;
    this.maxSize = paramLong;
    this.executor = paramExecutor;
  }
  
  private void checkNotClosed()
  {
    try
    {
      boolean bool = isClosed();
      if (!bool) {
        return;
      }
      IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
      localIllegalStateException.<init>("cache is closed");
      throw localIllegalStateException;
    }
    finally {}
  }
  
  public static DiskLruCache create(FileSystem paramFileSystem, File paramFile, int paramInt1, int paramInt2, long paramLong)
  {
    if (paramLong > 0L)
    {
      if (paramInt2 > 0) {
        return new DiskLruCache(paramFileSystem, paramFile, paramInt1, paramInt2, paramLong, new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp DiskLruCache", true)));
      }
      throw new IllegalArgumentException("valueCount <= 0");
    }
    throw new IllegalArgumentException("maxSize <= 0");
  }
  
  private d newJournalWriter()
  {
    l.a(new FaultHidingSink(this.fileSystem.appendingSink(this.journalFile))
    {
      protected void onException(IOException paramAnonymousIOException)
      {
        DiskLruCache.this.hasJournalErrors = true;
      }
    });
  }
  
  private void processJournal()
  {
    this.fileSystem.delete(this.journalFileTmp);
    Iterator localIterator = this.lruEntries.values().iterator();
    while (localIterator.hasNext())
    {
      Entry localEntry = (Entry)localIterator.next();
      Editor localEditor = localEntry.currentEditor;
      int i = 0;
      int j = 0;
      if (localEditor == null)
      {
        while (j < this.valueCount)
        {
          this.size += localEntry.lengths[j];
          j++;
        }
      }
      else
      {
        localEntry.currentEditor = null;
        for (j = i; j < this.valueCount; j++)
        {
          this.fileSystem.delete(localEntry.cleanFiles[j]);
          this.fileSystem.delete(localEntry.dirtyFiles[j]);
        }
        localIterator.remove();
      }
    }
  }
  
  private void readJournal()
  {
    e locale = l.a(this.fileSystem.source(this.journalFile));
    try
    {
      String str1 = locale.r();
      String str2 = locale.r();
      Object localObject2 = locale.r();
      String str3 = locale.r();
      String str4 = locale.r();
      if (("libcore.io.DiskLruCache".equals(str1)) && ("1".equals(str2)) && (Integer.toString(this.appVersion).equals(localObject2)) && (Integer.toString(this.valueCount).equals(str3)))
      {
        boolean bool = "".equals(str4);
        if (bool)
        {
          int i = 0;
          try
          {
            for (;;)
            {
              readJournalLine(locale.r());
              i++;
            }
            localIOException = new java/io/IOException;
          }
          catch (EOFException localEOFException)
          {
            this.redundantOpCount = (i - this.lruEntries.size());
            if (!locale.e()) {
              rebuildJournal();
            } else {
              this.journalWriter = newJournalWriter();
            }
            return;
          }
        }
      }
      IOException localIOException;
      localObject2 = new java/lang/StringBuilder;
      ((StringBuilder)localObject2).<init>();
      ((StringBuilder)localObject2).append("unexpected journal header: [");
      ((StringBuilder)localObject2).append(localEOFException);
      ((StringBuilder)localObject2).append(", ");
      ((StringBuilder)localObject2).append(str2);
      ((StringBuilder)localObject2).append(", ");
      ((StringBuilder)localObject2).append(str3);
      ((StringBuilder)localObject2).append(", ");
      ((StringBuilder)localObject2).append(str4);
      ((StringBuilder)localObject2).append("]");
      localIOException.<init>(((StringBuilder)localObject2).toString());
      throw localIOException;
    }
    finally
    {
      Util.closeQuietly(locale);
    }
  }
  
  private void readJournalLine(String paramString)
  {
    int i = paramString.indexOf(' ');
    if (i != -1)
    {
      int j = i + 1;
      int k = paramString.indexOf(' ', j);
      if (k == -1)
      {
        localObject1 = paramString.substring(j);
        localObject2 = localObject1;
        if (i == 6)
        {
          localObject2 = localObject1;
          if (paramString.startsWith("REMOVE")) {
            this.lruEntries.remove(localObject1);
          }
        }
      }
      else
      {
        localObject2 = paramString.substring(j, k);
      }
      Entry localEntry = (Entry)this.lruEntries.get(localObject2);
      Object localObject1 = localEntry;
      if (localEntry == null)
      {
        localObject1 = new Entry((String)localObject2);
        this.lruEntries.put(localObject2, localObject1);
      }
      if ((k != -1) && (i == 5) && (paramString.startsWith("CLEAN")))
      {
        paramString = paramString.substring(k + 1).split(" ");
        ((Entry)localObject1).readable = true;
        ((Entry)localObject1).currentEditor = null;
        ((Entry)localObject1).setLengths(paramString);
      }
      else if ((k == -1) && (i == 5) && (paramString.startsWith("DIRTY")))
      {
        ((Entry)localObject1).currentEditor = new Editor((Entry)localObject1);
      }
      else
      {
        if ((k != -1) || (i != 4) || (!paramString.startsWith("READ"))) {
          break label243;
        }
      }
      return;
      label243:
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("unexpected journal line: ");
      ((StringBuilder)localObject2).append(paramString);
      throw new IOException(((StringBuilder)localObject2).toString());
    }
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("unexpected journal line: ");
    ((StringBuilder)localObject2).append(paramString);
    throw new IOException(((StringBuilder)localObject2).toString());
  }
  
  private void validateKey(String paramString)
  {
    if (LEGAL_KEY_PATTERN.matcher(paramString).matches()) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("keys must match regex [a-z0-9_-]{1,120}: \"");
    localStringBuilder.append(paramString);
    localStringBuilder.append("\"");
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public void close()
  {
    try
    {
      if ((this.initialized) && (!this.closed))
      {
        for (Entry localEntry : (Entry[])this.lruEntries.values().toArray(new Entry[this.lruEntries.size()])) {
          if (localEntry.currentEditor != null) {
            localEntry.currentEditor.abort();
          }
        }
        trimToSize();
        this.journalWriter.close();
        this.journalWriter = null;
        this.closed = true;
        return;
      }
      this.closed = true;
      return;
    }
    finally {}
  }
  
  void completeEdit(Editor paramEditor, boolean paramBoolean)
  {
    try
    {
      Object localObject = paramEditor.entry;
      if (((Entry)localObject).currentEditor == paramEditor)
      {
        int i = 0;
        int j = i;
        if (paramBoolean)
        {
          j = i;
          if (!((Entry)localObject).readable)
          {
            for (int k = 0;; k++)
            {
              j = i;
              if (k >= this.valueCount) {
                break label136;
              }
              if (paramEditor.written[k] == 0) {
                break;
              }
              if (!this.fileSystem.exists(localObject.dirtyFiles[k]))
              {
                paramEditor.abort();
                return;
              }
            }
            paramEditor.abort();
            paramEditor = new java/lang/IllegalStateException;
            localObject = new java/lang/StringBuilder;
            ((StringBuilder)localObject).<init>();
            ((StringBuilder)localObject).append("Newly created entry didn't create value for index ");
            ((StringBuilder)localObject).append(k);
            paramEditor.<init>(((StringBuilder)localObject).toString());
            throw paramEditor;
          }
        }
        label136:
        long l1;
        while (j < this.valueCount)
        {
          File localFile = localObject.dirtyFiles[j];
          if (paramBoolean)
          {
            if (this.fileSystem.exists(localFile))
            {
              paramEditor = localObject.cleanFiles[j];
              this.fileSystem.rename(localFile, paramEditor);
              l1 = localObject.lengths[j];
              long l2 = this.fileSystem.size(paramEditor);
              ((Entry)localObject).lengths[j] = l2;
              this.size = (this.size - l1 + l2);
            }
          }
          else {
            this.fileSystem.delete(localFile);
          }
          j++;
        }
        this.redundantOpCount += 1;
        ((Entry)localObject).currentEditor = null;
        if ((((Entry)localObject).readable | paramBoolean))
        {
          ((Entry)localObject).readable = true;
          this.journalWriter.b("CLEAN").i(32);
          this.journalWriter.b(((Entry)localObject).key);
          ((Entry)localObject).writeLengths(this.journalWriter);
          this.journalWriter.i(10);
          if (paramBoolean)
          {
            l1 = this.nextSequenceNumber;
            this.nextSequenceNumber = (1L + l1);
            ((Entry)localObject).sequenceNumber = l1;
          }
        }
        else
        {
          this.lruEntries.remove(((Entry)localObject).key);
          this.journalWriter.b("REMOVE").i(32);
          this.journalWriter.b(((Entry)localObject).key);
          this.journalWriter.i(10);
        }
        this.journalWriter.flush();
        if ((this.size > this.maxSize) || (journalRebuildRequired())) {
          this.executor.execute(this.cleanupRunnable);
        }
        return;
      }
      paramEditor = new java/lang/IllegalStateException;
      paramEditor.<init>();
      throw paramEditor;
    }
    finally {}
  }
  
  public void delete()
  {
    close();
    this.fileSystem.deleteContents(this.directory);
  }
  
  @Nullable
  public Editor edit(String paramString)
  {
    return edit(paramString, -1L);
  }
  
  Editor edit(String paramString, long paramLong)
  {
    try
    {
      initialize();
      checkNotClosed();
      validateKey(paramString);
      Entry localEntry = (Entry)this.lruEntries.get(paramString);
      if (paramLong != -1L) {
        if (localEntry != null)
        {
          long l = localEntry.sequenceNumber;
          if (l == paramLong) {}
        }
        else
        {
          return null;
        }
      }
      Object localObject;
      if (localEntry != null)
      {
        localObject = localEntry.currentEditor;
        if (localObject != null) {
          return null;
        }
      }
      if ((!this.mostRecentTrimFailed) && (!this.mostRecentRebuildFailed))
      {
        this.journalWriter.b("DIRTY").i(32).b(paramString).i(10);
        this.journalWriter.flush();
        boolean bool = this.hasJournalErrors;
        if (bool) {
          return null;
        }
        localObject = localEntry;
        if (localEntry == null)
        {
          localObject = new okhttp3/internal/cache/DiskLruCache$Entry;
          ((Entry)localObject).<init>(this, paramString);
          this.lruEntries.put(paramString, localObject);
        }
        paramString = new okhttp3/internal/cache/DiskLruCache$Editor;
        paramString.<init>(this, (Entry)localObject);
        ((Entry)localObject).currentEditor = paramString;
        return paramString;
      }
      this.executor.execute(this.cleanupRunnable);
      return null;
    }
    finally {}
  }
  
  public void evictAll()
  {
    try
    {
      initialize();
      Entry[] arrayOfEntry = (Entry[])this.lruEntries.values().toArray(new Entry[this.lruEntries.size()]);
      int i = arrayOfEntry.length;
      for (int j = 0; j < i; j++) {
        removeEntry(arrayOfEntry[j]);
      }
      this.mostRecentTrimFailed = false;
      return;
    }
    finally {}
  }
  
  public void flush()
  {
    try
    {
      boolean bool = this.initialized;
      if (!bool) {
        return;
      }
      checkNotClosed();
      trimToSize();
      this.journalWriter.flush();
      return;
    }
    finally {}
  }
  
  public Snapshot get(String paramString)
  {
    try
    {
      initialize();
      checkNotClosed();
      validateKey(paramString);
      Object localObject = (Entry)this.lruEntries.get(paramString);
      if ((localObject != null) && (((Entry)localObject).readable))
      {
        localObject = ((Entry)localObject).snapshot();
        if (localObject == null) {
          return null;
        }
        this.redundantOpCount += 1;
        this.journalWriter.b("READ").i(32).b(paramString).i(10);
        if (journalRebuildRequired()) {
          this.executor.execute(this.cleanupRunnable);
        }
        return (Snapshot)localObject;
      }
      return null;
    }
    finally {}
  }
  
  public File getDirectory()
  {
    return this.directory;
  }
  
  public long getMaxSize()
  {
    try
    {
      long l = this.maxSize;
      return l;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  public void initialize()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 384	okhttp3/internal/cache/DiskLruCache:initialized	Z
    //   6: istore_1
    //   7: iload_1
    //   8: ifeq +6 -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_0
    //   15: getfield 122	okhttp3/internal/cache/DiskLruCache:fileSystem	Lokhttp3/internal/io/FileSystem;
    //   18: aload_0
    //   19: getfield 137	okhttp3/internal/cache/DiskLruCache:journalFileBackup	Ljava/io/File;
    //   22: invokeinterface 416 2 0
    //   27: ifeq +52 -> 79
    //   30: aload_0
    //   31: getfield 122	okhttp3/internal/cache/DiskLruCache:fileSystem	Lokhttp3/internal/io/FileSystem;
    //   34: aload_0
    //   35: getfield 133	okhttp3/internal/cache/DiskLruCache:journalFile	Ljava/io/File;
    //   38: invokeinterface 416 2 0
    //   43: ifeq +19 -> 62
    //   46: aload_0
    //   47: getfield 122	okhttp3/internal/cache/DiskLruCache:fileSystem	Lokhttp3/internal/io/FileSystem;
    //   50: aload_0
    //   51: getfield 137	okhttp3/internal/cache/DiskLruCache:journalFileBackup	Ljava/io/File;
    //   54: invokeinterface 212 2 0
    //   59: goto +20 -> 79
    //   62: aload_0
    //   63: getfield 122	okhttp3/internal/cache/DiskLruCache:fileSystem	Lokhttp3/internal/io/FileSystem;
    //   66: aload_0
    //   67: getfield 137	okhttp3/internal/cache/DiskLruCache:journalFileBackup	Ljava/io/File;
    //   70: aload_0
    //   71: getfield 133	okhttp3/internal/cache/DiskLruCache:journalFile	Ljava/io/File;
    //   74: invokeinterface 425 3 0
    //   79: aload_0
    //   80: getfield 122	okhttp3/internal/cache/DiskLruCache:fileSystem	Lokhttp3/internal/io/FileSystem;
    //   83: aload_0
    //   84: getfield 133	okhttp3/internal/cache/DiskLruCache:journalFile	Ljava/io/File;
    //   87: invokeinterface 416 2 0
    //   92: istore_1
    //   93: iload_1
    //   94: ifeq +112 -> 206
    //   97: aload_0
    //   98: invokespecial 499	okhttp3/internal/cache/DiskLruCache:readJournal	()V
    //   101: aload_0
    //   102: invokespecial 501	okhttp3/internal/cache/DiskLruCache:processJournal	()V
    //   105: aload_0
    //   106: iconst_1
    //   107: putfield 384	okhttp3/internal/cache/DiskLruCache:initialized	Z
    //   110: aload_0
    //   111: monitorexit
    //   112: return
    //   113: astore_2
    //   114: invokestatic 506	okhttp3/internal/platform/Platform:get	()Lokhttp3/internal/platform/Platform;
    //   117: astore_3
    //   118: new 305	java/lang/StringBuilder
    //   121: astore 4
    //   123: aload 4
    //   125: invokespecial 306	java/lang/StringBuilder:<init>	()V
    //   128: aload 4
    //   130: ldc_w 508
    //   133: invokevirtual 312	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   136: pop
    //   137: aload 4
    //   139: aload_0
    //   140: getfield 124	okhttp3/internal/cache/DiskLruCache:directory	Ljava/io/File;
    //   143: invokevirtual 511	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   146: pop
    //   147: aload 4
    //   149: ldc_w 513
    //   152: invokevirtual 312	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: pop
    //   156: aload 4
    //   158: aload_2
    //   159: invokevirtual 516	java/io/IOException:getMessage	()Ljava/lang/String;
    //   162: invokevirtual 312	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   165: pop
    //   166: aload 4
    //   168: ldc_w 518
    //   171: invokevirtual 312	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   174: pop
    //   175: aload_3
    //   176: iconst_5
    //   177: aload 4
    //   179: invokevirtual 318	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   182: aload_2
    //   183: invokevirtual 522	okhttp3/internal/platform/Platform:log	(ILjava/lang/String;Ljava/lang/Throwable;)V
    //   186: aload_0
    //   187: invokevirtual 524	okhttp3/internal/cache/DiskLruCache:delete	()V
    //   190: aload_0
    //   191: iconst_0
    //   192: putfield 386	okhttp3/internal/cache/DiskLruCache:closed	Z
    //   195: goto +11 -> 206
    //   198: astore_2
    //   199: aload_0
    //   200: iconst_0
    //   201: putfield 386	okhttp3/internal/cache/DiskLruCache:closed	Z
    //   204: aload_2
    //   205: athrow
    //   206: aload_0
    //   207: invokevirtual 293	okhttp3/internal/cache/DiskLruCache:rebuildJournal	()V
    //   210: aload_0
    //   211: iconst_1
    //   212: putfield 384	okhttp3/internal/cache/DiskLruCache:initialized	Z
    //   215: aload_0
    //   216: monitorexit
    //   217: return
    //   218: astore_2
    //   219: aload_0
    //   220: monitorexit
    //   221: aload_2
    //   222: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	223	0	this	DiskLruCache
    //   6	88	1	bool	boolean
    //   113	70	2	localIOException	IOException
    //   198	7	2	localObject1	Object
    //   218	4	2	localObject2	Object
    //   117	59	3	localPlatform	okhttp3.internal.platform.Platform
    //   121	57	4	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   97	110	113	java/io/IOException
    //   186	190	198	finally
    //   2	7	218	finally
    //   14	59	218	finally
    //   62	79	218	finally
    //   79	93	218	finally
    //   97	110	218	finally
    //   114	186	218	finally
    //   190	195	218	finally
    //   199	206	218	finally
    //   206	215	218	finally
  }
  
  public boolean isClosed()
  {
    try
    {
      boolean bool = this.closed;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  boolean journalRebuildRequired()
  {
    int i = this.redundantOpCount;
    boolean bool;
    if ((i >= 2000) && (i >= this.lruEntries.size())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  /* Error */
  void rebuildJournal()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 297	okhttp3/internal/cache/DiskLruCache:journalWriter	La/d;
    //   6: ifnull +12 -> 18
    //   9: aload_0
    //   10: getfield 297	okhttp3/internal/cache/DiskLruCache:journalWriter	La/d;
    //   13: invokeinterface 402 1 0
    //   18: aload_0
    //   19: getfield 122	okhttp3/internal/cache/DiskLruCache:fileSystem	Lokhttp3/internal/io/FileSystem;
    //   22: aload_0
    //   23: getfield 135	okhttp3/internal/cache/DiskLruCache:journalFileTmp	Ljava/io/File;
    //   26: invokeinterface 527 2 0
    //   31: invokestatic 207	a/l:a	(La/r;)La/d;
    //   34: astore_1
    //   35: aload_1
    //   36: ldc 51
    //   38: invokeinterface 432 2 0
    //   43: bipush 10
    //   45: invokeinterface 436 2 0
    //   50: pop
    //   51: aload_1
    //   52: ldc 58
    //   54: invokeinterface 432 2 0
    //   59: bipush 10
    //   61: invokeinterface 436 2 0
    //   66: pop
    //   67: aload_1
    //   68: aload_0
    //   69: getfield 126	okhttp3/internal/cache/DiskLruCache:appVersion	I
    //   72: i2l
    //   73: invokeinterface 531 3 0
    //   78: bipush 10
    //   80: invokeinterface 436 2 0
    //   85: pop
    //   86: aload_1
    //   87: aload_0
    //   88: getfield 139	okhttp3/internal/cache/DiskLruCache:valueCount	I
    //   91: i2l
    //   92: invokeinterface 531 3 0
    //   97: bipush 10
    //   99: invokeinterface 436 2 0
    //   104: pop
    //   105: aload_1
    //   106: bipush 10
    //   108: invokeinterface 436 2 0
    //   113: pop
    //   114: aload_0
    //   115: getfield 113	okhttp3/internal/cache/DiskLruCache:lruEntries	Ljava/util/LinkedHashMap;
    //   118: invokevirtual 216	java/util/LinkedHashMap:values	()Ljava/util/Collection;
    //   121: invokeinterface 222 1 0
    //   126: astore_2
    //   127: aload_2
    //   128: invokeinterface 227 1 0
    //   133: ifeq +103 -> 236
    //   136: aload_2
    //   137: invokeinterface 231 1 0
    //   142: checkcast 21	okhttp3/internal/cache/DiskLruCache$Entry
    //   145: astore_3
    //   146: aload_3
    //   147: getfield 235	okhttp3/internal/cache/DiskLruCache$Entry:currentEditor	Lokhttp3/internal/cache/DiskLruCache$Editor;
    //   150: ifnull +42 -> 192
    //   153: aload_1
    //   154: ldc 37
    //   156: invokeinterface 432 2 0
    //   161: bipush 32
    //   163: invokeinterface 436 2 0
    //   168: pop
    //   169: aload_1
    //   170: aload_3
    //   171: getfield 439	okhttp3/internal/cache/DiskLruCache$Entry:key	Ljava/lang/String;
    //   174: invokeinterface 432 2 0
    //   179: pop
    //   180: aload_1
    //   181: bipush 10
    //   183: invokeinterface 436 2 0
    //   188: pop
    //   189: goto -62 -> 127
    //   192: aload_1
    //   193: ldc 35
    //   195: invokeinterface 432 2 0
    //   200: bipush 32
    //   202: invokeinterface 436 2 0
    //   207: pop
    //   208: aload_1
    //   209: aload_3
    //   210: getfield 439	okhttp3/internal/cache/DiskLruCache$Entry:key	Ljava/lang/String;
    //   213: invokeinterface 432 2 0
    //   218: pop
    //   219: aload_3
    //   220: aload_1
    //   221: invokevirtual 443	okhttp3/internal/cache/DiskLruCache$Entry:writeLengths	(La/d;)V
    //   224: aload_1
    //   225: bipush 10
    //   227: invokeinterface 436 2 0
    //   232: pop
    //   233: goto -106 -> 127
    //   236: aload_1
    //   237: invokeinterface 402 1 0
    //   242: aload_0
    //   243: getfield 122	okhttp3/internal/cache/DiskLruCache:fileSystem	Lokhttp3/internal/io/FileSystem;
    //   246: aload_0
    //   247: getfield 133	okhttp3/internal/cache/DiskLruCache:journalFile	Ljava/io/File;
    //   250: invokeinterface 416 2 0
    //   255: ifeq +20 -> 275
    //   258: aload_0
    //   259: getfield 122	okhttp3/internal/cache/DiskLruCache:fileSystem	Lokhttp3/internal/io/FileSystem;
    //   262: aload_0
    //   263: getfield 133	okhttp3/internal/cache/DiskLruCache:journalFile	Ljava/io/File;
    //   266: aload_0
    //   267: getfield 137	okhttp3/internal/cache/DiskLruCache:journalFileBackup	Ljava/io/File;
    //   270: invokeinterface 425 3 0
    //   275: aload_0
    //   276: getfield 122	okhttp3/internal/cache/DiskLruCache:fileSystem	Lokhttp3/internal/io/FileSystem;
    //   279: aload_0
    //   280: getfield 135	okhttp3/internal/cache/DiskLruCache:journalFileTmp	Ljava/io/File;
    //   283: aload_0
    //   284: getfield 133	okhttp3/internal/cache/DiskLruCache:journalFile	Ljava/io/File;
    //   287: invokeinterface 425 3 0
    //   292: aload_0
    //   293: getfield 122	okhttp3/internal/cache/DiskLruCache:fileSystem	Lokhttp3/internal/io/FileSystem;
    //   296: aload_0
    //   297: getfield 137	okhttp3/internal/cache/DiskLruCache:journalFileBackup	Ljava/io/File;
    //   300: invokeinterface 212 2 0
    //   305: aload_0
    //   306: aload_0
    //   307: invokespecial 295	okhttp3/internal/cache/DiskLruCache:newJournalWriter	()La/d;
    //   310: putfield 297	okhttp3/internal/cache/DiskLruCache:journalWriter	La/d;
    //   313: aload_0
    //   314: iconst_0
    //   315: putfield 483	okhttp3/internal/cache/DiskLruCache:hasJournalErrors	Z
    //   318: aload_0
    //   319: iconst_0
    //   320: putfield 481	okhttp3/internal/cache/DiskLruCache:mostRecentRebuildFailed	Z
    //   323: aload_0
    //   324: monitorexit
    //   325: return
    //   326: astore_3
    //   327: aload_1
    //   328: invokeinterface 402 1 0
    //   333: aload_3
    //   334: athrow
    //   335: astore_1
    //   336: aload_0
    //   337: monitorexit
    //   338: aload_1
    //   339: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	340	0	this	DiskLruCache
    //   34	294	1	locald	d
    //   335	4	1	localObject1	Object
    //   126	11	2	localIterator	Iterator
    //   145	75	3	localEntry	Entry
    //   326	8	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   35	127	326	finally
    //   127	189	326	finally
    //   192	233	326	finally
    //   2	18	335	finally
    //   18	35	335	finally
    //   236	275	335	finally
    //   275	323	335	finally
    //   327	335	335	finally
  }
  
  public boolean remove(String paramString)
  {
    try
    {
      initialize();
      checkNotClosed();
      validateKey(paramString);
      paramString = (Entry)this.lruEntries.get(paramString);
      if (paramString == null) {
        return false;
      }
      boolean bool = removeEntry(paramString);
      if ((bool) && (this.size <= this.maxSize)) {
        this.mostRecentTrimFailed = false;
      }
      return bool;
    }
    finally {}
  }
  
  boolean removeEntry(Entry paramEntry)
  {
    if (paramEntry.currentEditor != null) {
      paramEntry.currentEditor.detach();
    }
    for (int i = 0; i < this.valueCount; i++)
    {
      this.fileSystem.delete(paramEntry.cleanFiles[i]);
      this.size -= paramEntry.lengths[i];
      paramEntry.lengths[i] = 0L;
    }
    this.redundantOpCount += 1;
    this.journalWriter.b("REMOVE").i(32).b(paramEntry.key).i(10);
    this.lruEntries.remove(paramEntry.key);
    if (journalRebuildRequired()) {
      this.executor.execute(this.cleanupRunnable);
    }
    return true;
  }
  
  public void setMaxSize(long paramLong)
  {
    try
    {
      this.maxSize = paramLong;
      if (this.initialized) {
        this.executor.execute(this.cleanupRunnable);
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public long size()
  {
    try
    {
      initialize();
      long l = this.size;
      return l;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public Iterator<Snapshot> snapshots()
  {
    try
    {
      initialize();
      Iterator local3 = new Iterator()
      {
        final Iterator<DiskLruCache.Entry> delegate = new ArrayList(DiskLruCache.this.lruEntries.values()).iterator();
        DiskLruCache.Snapshot nextSnapshot;
        DiskLruCache.Snapshot removeSnapshot;
        
        public boolean hasNext()
        {
          if (this.nextSnapshot != null) {
            return true;
          }
          synchronized (DiskLruCache.this)
          {
            if (DiskLruCache.this.closed) {
              return false;
            }
            while (this.delegate.hasNext())
            {
              DiskLruCache.Snapshot localSnapshot = ((DiskLruCache.Entry)this.delegate.next()).snapshot();
              if (localSnapshot != null)
              {
                this.nextSnapshot = localSnapshot;
                return true;
              }
            }
            return false;
          }
        }
        
        public DiskLruCache.Snapshot next()
        {
          if (hasNext())
          {
            this.removeSnapshot = this.nextSnapshot;
            this.nextSnapshot = null;
            return this.removeSnapshot;
          }
          throw new NoSuchElementException();
        }
        
        /* Error */
        public void remove()
        {
          // Byte code:
          //   0: aload_0
          //   1: getfield 73	okhttp3/internal/cache/DiskLruCache$3:removeSnapshot	Lokhttp3/internal/cache/DiskLruCache$Snapshot;
          //   4: astore_1
          //   5: aload_1
          //   6: ifnull +32 -> 38
          //   9: aload_0
          //   10: getfield 24	okhttp3/internal/cache/DiskLruCache$3:this$0	Lokhttp3/internal/cache/DiskLruCache;
          //   13: aload_1
          //   14: invokestatic 85	okhttp3/internal/cache/DiskLruCache$Snapshot:access$000	(Lokhttp3/internal/cache/DiskLruCache$Snapshot;)Ljava/lang/String;
          //   17: invokevirtual 88	okhttp3/internal/cache/DiskLruCache:remove	(Ljava/lang/String;)Z
          //   20: pop
          //   21: goto +11 -> 32
          //   24: astore_1
          //   25: aload_0
          //   26: aconst_null
          //   27: putfield 73	okhttp3/internal/cache/DiskLruCache$3:removeSnapshot	Lokhttp3/internal/cache/DiskLruCache$Snapshot;
          //   30: aload_1
          //   31: athrow
          //   32: aload_0
          //   33: aconst_null
          //   34: putfield 73	okhttp3/internal/cache/DiskLruCache$3:removeSnapshot	Lokhttp3/internal/cache/DiskLruCache$Snapshot;
          //   37: return
          //   38: new 90	java/lang/IllegalStateException
          //   41: dup
          //   42: ldc 92
          //   44: invokespecial 95	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
          //   47: athrow
          //   48: astore_1
          //   49: goto -17 -> 32
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	52	0	this	3
          //   4	10	1	localSnapshot	DiskLruCache.Snapshot
          //   24	7	1	localObject	Object
          //   48	1	1	localIOException	IOException
          // Exception table:
          //   from	to	target	type
          //   9	21	24	finally
          //   9	21	48	java/io/IOException
        }
      };
      return local3;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  void trimToSize()
  {
    while (this.size > this.maxSize) {
      removeEntry((Entry)this.lruEntries.values().iterator().next());
    }
    this.mostRecentTrimFailed = false;
  }
  
  public final class Editor
  {
    private boolean done;
    final DiskLruCache.Entry entry;
    final boolean[] written;
    
    Editor(DiskLruCache.Entry paramEntry)
    {
      this.entry = paramEntry;
      if (paramEntry.readable) {
        this$1 = null;
      } else {
        this$1 = new boolean[DiskLruCache.this.valueCount];
      }
      this.written = DiskLruCache.this;
    }
    
    public void abort()
    {
      synchronized (DiskLruCache.this)
      {
        if (!this.done)
        {
          if (this.entry.currentEditor == this) {
            DiskLruCache.this.completeEdit(this, false);
          }
          this.done = true;
          return;
        }
        IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
        localIllegalStateException.<init>();
        throw localIllegalStateException;
      }
    }
    
    public void abortUnlessCommitted()
    {
      synchronized (DiskLruCache.this)
      {
        if (!this.done)
        {
          Editor localEditor = this.entry.currentEditor;
          if (localEditor != this) {}
        }
      }
      try
      {
        DiskLruCache.this.completeEdit(this, false);
        return;
        localObject = finally;
        throw ((Throwable)localObject);
      }
      catch (IOException localIOException)
      {
        for (;;) {}
      }
    }
    
    public void commit()
    {
      synchronized (DiskLruCache.this)
      {
        if (!this.done)
        {
          if (this.entry.currentEditor == this) {
            DiskLruCache.this.completeEdit(this, true);
          }
          this.done = true;
          return;
        }
        IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
        localIllegalStateException.<init>();
        throw localIllegalStateException;
      }
    }
    
    void detach()
    {
      int i;
      if (this.entry.currentEditor == this) {
        i = 0;
      }
      for (;;)
      {
        if (i < DiskLruCache.this.valueCount) {}
        try
        {
          DiskLruCache.this.fileSystem.delete(this.entry.dirtyFiles[i]);
          i++;
          continue;
          this.entry.currentEditor = null;
          return;
        }
        catch (IOException localIOException)
        {
          for (;;) {}
        }
      }
    }
    
    public r newSink(int paramInt)
    {
      synchronized (DiskLruCache.this)
      {
        if (!this.done)
        {
          if (this.entry.currentEditor != this)
          {
            localObject1 = l.a();
            return (r)localObject1;
          }
          if (!this.entry.readable) {
            this.written[paramInt] = true;
          }
          Object localObject1 = this.entry.dirtyFiles[paramInt];
          try
          {
            r localr = DiskLruCache.this.fileSystem.sink((File)localObject1);
            localObject1 = new okhttp3/internal/cache/DiskLruCache$Editor$1;
            ((1)localObject1).<init>(this, localr);
            return (r)localObject1;
          }
          catch (FileNotFoundException localFileNotFoundException)
          {
            localObject2 = l.a();
            return (r)localObject2;
          }
        }
        Object localObject2 = new java/lang/IllegalStateException;
        ((IllegalStateException)localObject2).<init>();
        throw ((Throwable)localObject2);
      }
    }
    
    public s newSource(int paramInt)
    {
      synchronized (DiskLruCache.this)
      {
        if (!this.done)
        {
          if (this.entry.readable)
          {
            Object localObject1 = this.entry.currentEditor;
            if (localObject1 == this) {
              try
              {
                localObject1 = DiskLruCache.this.fileSystem.source(this.entry.cleanFiles[paramInt]);
                return (s)localObject1;
              }
              catch (FileNotFoundException localFileNotFoundException)
              {
                return null;
              }
            }
          }
          return null;
        }
        IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
        localIllegalStateException.<init>();
        throw localIllegalStateException;
      }
    }
  }
  
  private final class Entry
  {
    final File[] cleanFiles;
    DiskLruCache.Editor currentEditor;
    final File[] dirtyFiles;
    final String key;
    final long[] lengths;
    boolean readable;
    long sequenceNumber;
    
    Entry(String paramString)
    {
      this.key = paramString;
      this.lengths = new long[DiskLruCache.this.valueCount];
      this.cleanFiles = new File[DiskLruCache.this.valueCount];
      this.dirtyFiles = new File[DiskLruCache.this.valueCount];
      paramString = new StringBuilder(paramString);
      paramString.append('.');
      int i = paramString.length();
      for (int j = 0; j < DiskLruCache.this.valueCount; j++)
      {
        paramString.append(j);
        this.cleanFiles[j] = new File(DiskLruCache.this.directory, paramString.toString());
        paramString.append(".tmp");
        this.dirtyFiles[j] = new File(DiskLruCache.this.directory, paramString.toString());
        paramString.setLength(i);
      }
    }
    
    private IOException invalidLengths(String[] paramArrayOfString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("unexpected journal line: ");
      localStringBuilder.append(Arrays.toString(paramArrayOfString));
      throw new IOException(localStringBuilder.toString());
    }
    
    void setLengths(String[] paramArrayOfString)
    {
      if (paramArrayOfString.length == DiskLruCache.this.valueCount)
      {
        int i = 0;
        try
        {
          while (i < paramArrayOfString.length)
          {
            this.lengths[i] = Long.parseLong(paramArrayOfString[i]);
            i++;
          }
          return;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw invalidLengths(paramArrayOfString);
        }
      }
      throw invalidLengths(paramArrayOfString);
    }
    
    DiskLruCache.Snapshot snapshot()
    {
      if (Thread.holdsLock(DiskLruCache.this))
      {
        s[] arrayOfs = new s[DiskLruCache.this.valueCount];
        Object localObject = (long[])this.lengths.clone();
        i = 0;
        for (j = 0;; j++)
        {
          try
          {
            while (j < DiskLruCache.this.valueCount)
            {
              arrayOfs[j] = DiskLruCache.this.fileSystem.source(this.cleanFiles[j]);
              j++;
            }
            localObject = new DiskLruCache.Snapshot(DiskLruCache.this, this.key, this.sequenceNumber, arrayOfs, (long[])localObject);
            return (DiskLruCache.Snapshot)localObject;
          }
          catch (FileNotFoundException localFileNotFoundException)
          {
            try
            {
              DiskLruCache.this.removeEntry(this);
              return null;
              throw new AssertionError();
              localFileNotFoundException = localFileNotFoundException;
              j = i;
            }
            catch (IOException localIOException)
            {
              for (;;) {}
            }
          }
          if ((j >= DiskLruCache.this.valueCount) || (arrayOfs[j] == null)) {
            break;
          }
          Util.closeQuietly(arrayOfs[j]);
        }
      }
    }
    
    void writeLengths(d paramd)
    {
      for (long l : this.lengths) {
        paramd.i(32).n(l);
      }
    }
  }
  
  public final class Snapshot
    implements Closeable
  {
    private final String key;
    private final long[] lengths;
    private final long sequenceNumber;
    private final s[] sources;
    
    Snapshot(String paramString, long paramLong, s[] paramArrayOfs, long[] paramArrayOfLong)
    {
      this.key = paramString;
      this.sequenceNumber = paramLong;
      this.sources = paramArrayOfs;
      this.lengths = paramArrayOfLong;
    }
    
    public void close()
    {
      s[] arrayOfs = this.sources;
      int i = arrayOfs.length;
      for (int j = 0; j < i; j++) {
        Util.closeQuietly(arrayOfs[j]);
      }
    }
    
    @Nullable
    public DiskLruCache.Editor edit()
    {
      return DiskLruCache.this.edit(this.key, this.sequenceNumber);
    }
    
    public long getLength(int paramInt)
    {
      return this.lengths[paramInt];
    }
    
    public s getSource(int paramInt)
    {
      return this.sources[paramInt];
    }
    
    public String key()
    {
      return this.key;
    }
  }
}


/* Location:              ~/okhttp3/internal/cache/DiskLruCache.class
 *
 * Reversed by:           J
 */