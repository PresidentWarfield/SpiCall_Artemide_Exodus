package io.fabric.sdk.android.services.c;

import android.content.Context;
import io.fabric.sdk.android.services.b.i;
import io.fabric.sdk.android.services.b.k;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class b<T>
{
  public static final int MAX_BYTE_SIZE_PER_FILE = 8000;
  public static final int MAX_FILES_IN_BATCH = 1;
  public static final int MAX_FILES_TO_KEEP = 100;
  public static final String ROLL_OVER_FILE_NAME_SEPARATOR = "_";
  protected final Context context;
  protected final k currentTimeProvider;
  private final int defaultMaxFilesToKeep;
  protected final c eventStorage;
  protected volatile long lastRollOverTime;
  protected final List<d> rollOverListeners = new CopyOnWriteArrayList();
  protected final a<T> transform;
  
  public b(Context paramContext, a<T> parama, k paramk, c paramc, int paramInt)
  {
    this.context = paramContext.getApplicationContext();
    this.transform = parama;
    this.eventStorage = paramc;
    this.currentTimeProvider = paramk;
    this.lastRollOverTime = this.currentTimeProvider.a();
    this.defaultMaxFilesToKeep = paramInt;
  }
  
  private void rollFileOverIfNeeded(int paramInt)
  {
    if (!this.eventStorage.a(paramInt, getMaxByteSizePerFile()))
    {
      String str = String.format(Locale.US, "session analytics events file is %d bytes, new event is %d bytes, this is over flush limit of %d, rolling it over", new Object[] { Integer.valueOf(this.eventStorage.a()), Integer.valueOf(paramInt), Integer.valueOf(getMaxByteSizePerFile()) });
      i.a(this.context, 4, "Fabric", str);
      rollFileOver();
    }
  }
  
  private void triggerRollOverOnListeners(String paramString)
  {
    Iterator localIterator = this.rollOverListeners.iterator();
    while (localIterator.hasNext())
    {
      d locald = (d)localIterator.next();
      try
      {
        locald.onRollOver(paramString);
      }
      catch (Exception localException)
      {
        i.a(this.context, "One of the roll over listeners threw an exception", localException);
      }
    }
  }
  
  public void deleteAllEventsFiles()
  {
    c localc = this.eventStorage;
    localc.a(localc.c());
    this.eventStorage.d();
  }
  
  public void deleteOldestInRollOverIfOverMax()
  {
    Object localObject1 = this.eventStorage.c();
    int i = getMaxFilesToKeep();
    if (((List)localObject1).size() <= i) {
      return;
    }
    int j = ((List)localObject1).size() - i;
    i.a(this.context, String.format(Locale.US, "Found %d files in  roll over directory, this is greater than %d, deleting %d oldest files", new Object[] { Integer.valueOf(((List)localObject1).size()), Integer.valueOf(i), Integer.valueOf(j) }));
    Object localObject2 = new TreeSet(new Comparator()
    {
      public int a(b.a paramAnonymousa1, b.a paramAnonymousa2)
      {
        return (int)(paramAnonymousa1.b - paramAnonymousa2.b);
      }
    });
    Iterator localIterator = ((List)localObject1).iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (File)localIterator.next();
      ((TreeSet)localObject2).add(new a((File)localObject1, parseCreationTimestampFromFileName(((File)localObject1).getName())));
    }
    localObject1 = new ArrayList();
    localObject2 = ((TreeSet)localObject2).iterator();
    do
    {
      if (!((Iterator)localObject2).hasNext()) {
        break;
      }
      ((ArrayList)localObject1).add(((a)((Iterator)localObject2).next()).a);
    } while (((ArrayList)localObject1).size() != j);
    this.eventStorage.a((List)localObject1);
  }
  
  public void deleteSentFiles(List<File> paramList)
  {
    this.eventStorage.a(paramList);
  }
  
  protected abstract String generateUniqueRollOverFileName();
  
  public List<File> getBatchOfFilesToSend()
  {
    return this.eventStorage.a(1);
  }
  
  public long getLastRollOverTime()
  {
    return this.lastRollOverTime;
  }
  
  protected int getMaxByteSizePerFile()
  {
    return 8000;
  }
  
  protected int getMaxFilesToKeep()
  {
    return this.defaultMaxFilesToKeep;
  }
  
  public long parseCreationTimestampFromFileName(String paramString)
  {
    paramString = paramString.split("_");
    if (paramString.length != 3) {
      return 0L;
    }
    try
    {
      long l = Long.valueOf(paramString[2]).longValue();
      return l;
    }
    catch (NumberFormatException paramString) {}
    return 0L;
  }
  
  public void registerRollOverListener(d paramd)
  {
    if (paramd != null) {
      this.rollOverListeners.add(paramd);
    }
  }
  
  public boolean rollFileOver()
  {
    boolean bool1 = this.eventStorage.b();
    boolean bool2 = true;
    String str;
    if (!bool1)
    {
      str = generateUniqueRollOverFileName();
      this.eventStorage.a(str);
      i.a(this.context, 4, "Fabric", String.format(Locale.US, "generated new file %s", new Object[] { str }));
      this.lastRollOverTime = this.currentTimeProvider.a();
    }
    else
    {
      str = null;
      bool2 = false;
    }
    triggerRollOverOnListeners(str);
    return bool2;
  }
  
  public void writeEvent(T paramT)
  {
    paramT = this.transform.toBytes(paramT);
    rollFileOverIfNeeded(paramT.length);
    this.eventStorage.a(paramT);
  }
  
  static class a
  {
    final File a;
    final long b;
    
    public a(File paramFile, long paramLong)
    {
      this.a = paramFile;
      this.b = paramLong;
    }
  }
}


/* Location:              ~/io/fabric/sdk/android/services/c/b.class
 *
 * Reversed by:           J
 */