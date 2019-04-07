package com.crashlytics.android.core;

import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.h;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ReportUploader
{
  static final Map<String, String> HEADER_INVALID_CLS_FILE = Collections.singletonMap("X-CRASHLYTICS-INVALID-SESSION", "1");
  private static final short[] RETRY_INTERVALS = { 10, 20, 30, 60, 120, 300 };
  private final String apiKey;
  private final CreateReportSpiCall createReportCall;
  private final Object fileAccessLock = new Object();
  private final HandlingExceptionCheck handlingExceptionCheck;
  private final ReportFilesProvider reportFilesProvider;
  private Thread uploadThread;
  
  public ReportUploader(String paramString, CreateReportSpiCall paramCreateReportSpiCall, ReportFilesProvider paramReportFilesProvider, HandlingExceptionCheck paramHandlingExceptionCheck)
  {
    if (paramCreateReportSpiCall != null)
    {
      this.createReportCall = paramCreateReportSpiCall;
      this.apiKey = paramString;
      this.reportFilesProvider = paramReportFilesProvider;
      this.handlingExceptionCheck = paramHandlingExceptionCheck;
      return;
    }
    throw new IllegalArgumentException("createReportCall must not be null.");
  }
  
  List<Report> findReports()
  {
    c.g().a("CrashlyticsCore", "Checking for crash reports...");
    synchronized (this.fileAccessLock)
    {
      Object localObject2 = this.reportFilesProvider.getCompleteSessionFiles();
      Object localObject3 = this.reportFilesProvider.getInvalidSessionFiles();
      File[] arrayOfFile = this.reportFilesProvider.getNativeReportFiles();
      ??? = new LinkedList();
      int i = 0;
      int j;
      int k;
      Object localObject5;
      Object localObject6;
      Object localObject7;
      if (localObject2 != null)
      {
        j = localObject2.length;
        for (k = 0; k < j; k++)
        {
          localObject5 = localObject2[k];
          localObject6 = c.g();
          localObject7 = new StringBuilder();
          ((StringBuilder)localObject7).append("Found crash report ");
          ((StringBuilder)localObject7).append(((File)localObject5).getPath());
          ((k)localObject6).a("CrashlyticsCore", ((StringBuilder)localObject7).toString());
          ((List)???).add(new SessionReport((File)localObject5));
        }
      }
      localObject2 = new HashMap();
      if (localObject3 != null)
      {
        j = localObject3.length;
        for (k = 0; k < j; k++)
        {
          localObject6 = localObject3[k];
          localObject7 = CrashlyticsController.getSessionIdFromSessionFile((File)localObject6);
          if (!((Map)localObject2).containsKey(localObject7)) {
            ((Map)localObject2).put(localObject7, new LinkedList());
          }
          ((List)((Map)localObject2).get(localObject7)).add(localObject6);
        }
      }
      localObject3 = ((Map)localObject2).keySet().iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject6 = (String)((Iterator)localObject3).next();
        localObject5 = c.g();
        localObject7 = new StringBuilder();
        ((StringBuilder)localObject7).append("Found invalid session: ");
        ((StringBuilder)localObject7).append((String)localObject6);
        ((k)localObject5).a("CrashlyticsCore", ((StringBuilder)localObject7).toString());
        localObject7 = (List)((Map)localObject2).get(localObject6);
        ((List)???).add(new InvalidSessionReport((String)localObject6, (File[])((List)localObject7).toArray(new File[((List)localObject7).size()])));
      }
      if (arrayOfFile != null)
      {
        j = arrayOfFile.length;
        for (k = i; k < j; k++) {
          ((List)???).add(new NativeSessionReport(arrayOfFile[k]));
        }
      }
      if (((List)???).isEmpty()) {
        c.g().a("CrashlyticsCore", "No reports found.");
      }
      return (List<Report>)???;
    }
  }
  
  /* Error */
  boolean forceUpload(Report paramReport)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 65	com/crashlytics/android/core/ReportUploader:fileAccessLock	Ljava/lang/Object;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: iconst_0
    //   8: istore_3
    //   9: new 225	com/crashlytics/android/core/CreateReportRequest
    //   12: astore 4
    //   14: aload 4
    //   16: aload_0
    //   17: getfield 69	com/crashlytics/android/core/ReportUploader:apiKey	Ljava/lang/String;
    //   20: aload_1
    //   21: invokespecial 228	com/crashlytics/android/core/CreateReportRequest:<init>	(Ljava/lang/String;Lcom/crashlytics/android/core/Report;)V
    //   24: aload_0
    //   25: getfield 67	com/crashlytics/android/core/ReportUploader:createReportCall	Lcom/crashlytics/android/core/CreateReportSpiCall;
    //   28: aload 4
    //   30: invokeinterface 234 2 0
    //   35: istore 5
    //   37: invokestatic 96	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   40: astore 6
    //   42: new 121	java/lang/StringBuilder
    //   45: astore 7
    //   47: aload 7
    //   49: invokespecial 122	java/lang/StringBuilder:<init>	()V
    //   52: aload 7
    //   54: ldc -20
    //   56: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: pop
    //   60: iload 5
    //   62: ifeq +10 -> 72
    //   65: ldc -18
    //   67: astore 4
    //   69: goto +7 -> 76
    //   72: ldc -16
    //   74: astore 4
    //   76: aload 7
    //   78: aload 4
    //   80: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   83: pop
    //   84: aload 7
    //   86: aload_1
    //   87: invokeinterface 245 1 0
    //   92: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: pop
    //   96: aload 6
    //   98: ldc 98
    //   100: aload 7
    //   102: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   105: invokeinterface 248 3 0
    //   110: iload_3
    //   111: istore 8
    //   113: iload 5
    //   115: ifeq +70 -> 185
    //   118: aload_1
    //   119: invokeinterface 251 1 0
    //   124: iconst_1
    //   125: istore 8
    //   127: goto +58 -> 185
    //   130: astore_1
    //   131: goto +59 -> 190
    //   134: astore 4
    //   136: invokestatic 96	io/fabric/sdk/android/c:g	()Lio/fabric/sdk/android/k;
    //   139: astore 6
    //   141: new 121	java/lang/StringBuilder
    //   144: astore 7
    //   146: aload 7
    //   148: invokespecial 122	java/lang/StringBuilder:<init>	()V
    //   151: aload 7
    //   153: ldc -3
    //   155: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: pop
    //   159: aload 7
    //   161: aload_1
    //   162: invokevirtual 256	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   165: pop
    //   166: aload 6
    //   168: ldc 98
    //   170: aload 7
    //   172: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   175: aload 4
    //   177: invokeinterface 260 4 0
    //   182: iload_3
    //   183: istore 8
    //   185: aload_2
    //   186: monitorexit
    //   187: iload 8
    //   189: ireturn
    //   190: aload_2
    //   191: monitorexit
    //   192: aload_1
    //   193: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	194	0	this	ReportUploader
    //   0	194	1	paramReport	Report
    //   4	187	2	localObject1	Object
    //   8	175	3	bool1	boolean
    //   12	67	4	localObject2	Object
    //   134	42	4	localException	Exception
    //   35	79	5	bool2	boolean
    //   40	127	6	localk	k
    //   45	126	7	localStringBuilder	StringBuilder
    //   111	77	8	bool3	boolean
    // Exception table:
    //   from	to	target	type
    //   9	60	130	finally
    //   76	110	130	finally
    //   118	124	130	finally
    //   136	182	130	finally
    //   185	187	130	finally
    //   190	192	130	finally
    //   9	60	134	java/lang/Exception
    //   76	110	134	java/lang/Exception
    //   118	124	134	java/lang/Exception
  }
  
  boolean isUploading()
  {
    boolean bool;
    if (this.uploadThread != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void uploadReports(float paramFloat, SendCheck paramSendCheck)
  {
    try
    {
      if (this.uploadThread != null)
      {
        c.g().a("CrashlyticsCore", "Report upload has already been started.");
        return;
      }
      Worker localWorker = new com/crashlytics/android/core/ReportUploader$Worker;
      localWorker.<init>(this, paramFloat, paramSendCheck);
      paramSendCheck = new java/lang/Thread;
      paramSendCheck.<init>(localWorker, "Crashlytics Report Uploader");
      this.uploadThread = paramSendCheck;
      this.uploadThread.start();
      return;
    }
    finally {}
  }
  
  static final class AlwaysSendCheck
    implements ReportUploader.SendCheck
  {
    public boolean canSendReports()
    {
      return true;
    }
  }
  
  static abstract interface HandlingExceptionCheck
  {
    public abstract boolean isHandlingException();
  }
  
  static abstract interface ReportFilesProvider
  {
    public abstract File[] getCompleteSessionFiles();
    
    public abstract File[] getInvalidSessionFiles();
    
    public abstract File[] getNativeReportFiles();
  }
  
  static abstract interface SendCheck
  {
    public abstract boolean canSendReports();
  }
  
  private class Worker
    extends h
  {
    private final float delay;
    private final ReportUploader.SendCheck sendCheck;
    
    Worker(float paramFloat, ReportUploader.SendCheck paramSendCheck)
    {
      this.delay = paramFloat;
      this.sendCheck = paramSendCheck;
    }
    
    private void attemptUploadWithRetry()
    {
      k localk = c.g();
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Starting report processing in ");
      ((StringBuilder)localObject2).append(this.delay);
      ((StringBuilder)localObject2).append(" second(s)...");
      localk.a("CrashlyticsCore", ((StringBuilder)localObject2).toString());
      float f = this.delay;
      long l;
      if (f > 0.0F)
      {
        l = (f * 1000.0F);
        try
        {
          Thread.sleep(l);
        }
        catch (InterruptedException localInterruptedException1)
        {
          Thread.currentThread().interrupt();
          return;
        }
      }
      Object localObject1 = ReportUploader.this.findReports();
      if (ReportUploader.this.handlingExceptionCheck.isHandlingException()) {
        return;
      }
      StringBuilder localStringBuilder;
      if ((!((List)localObject1).isEmpty()) && (!this.sendCheck.canSendReports()))
      {
        localObject2 = c.g();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("User declined to send. Removing ");
        localStringBuilder.append(((List)localObject1).size());
        localStringBuilder.append(" Report(s).");
        ((k)localObject2).a("CrashlyticsCore", localStringBuilder.toString());
        localObject1 = ((List)localObject1).iterator();
        while (((Iterator)localObject1).hasNext()) {
          ((Report)((Iterator)localObject1).next()).remove();
        }
        return;
      }
      int i = 0;
      while (!((List)localObject1).isEmpty())
      {
        if (ReportUploader.this.handlingExceptionCheck.isHandlingException()) {
          return;
        }
        localObject2 = c.g();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Attempting to send ");
        localStringBuilder.append(((List)localObject1).size());
        localStringBuilder.append(" report(s)");
        ((k)localObject2).a("CrashlyticsCore", localStringBuilder.toString());
        localObject2 = ((List)localObject1).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject1 = (Report)((Iterator)localObject2).next();
          ReportUploader.this.forceUpload((Report)localObject1);
        }
        localObject2 = ReportUploader.this.findReports();
        localObject1 = localObject2;
        if (!((List)localObject2).isEmpty())
        {
          l = ReportUploader.RETRY_INTERVALS[Math.min(i, ReportUploader.RETRY_INTERVALS.length - 1)];
          localObject1 = c.g();
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Report submisson: scheduling delayed retry in ");
          localStringBuilder.append(l);
          localStringBuilder.append(" seconds");
          ((k)localObject1).a("CrashlyticsCore", localStringBuilder.toString());
          try
          {
            Thread.sleep(l * 1000L);
            i++;
            localObject1 = localObject2;
          }
          catch (InterruptedException localInterruptedException2)
          {
            Thread.currentThread().interrupt();
            return;
          }
        }
      }
    }
    
    public void onRun()
    {
      try
      {
        attemptUploadWithRetry();
      }
      catch (Exception localException)
      {
        c.g().e("CrashlyticsCore", "An unexpected error occurred while attempting to upload crash reports.", localException);
      }
      ReportUploader.access$002(ReportUploader.this, null);
    }
  }
}


/* Location:              ~/com/crashlytics/android/core/ReportUploader.class
 *
 * Reversed by:           J
 */