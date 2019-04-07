package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.h;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.g;
import io.fabric.sdk.android.services.c.f;
import io.fabric.sdk.android.services.e.b;
import io.fabric.sdk.android.services.network.d;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class EnabledSessionAnalyticsManagerStrategy
  implements SessionAnalyticsManagerStrategy
{
  static final int UNDEFINED_ROLLOVER_INTERVAL_SECONDS = -1;
  g apiKey = new g();
  private final Context context;
  boolean customEventsEnabled = true;
  EventFilter eventFilter = new KeepAllEventFilter();
  private final ScheduledExecutorService executorService;
  private final SessionAnalyticsFilesManager filesManager;
  f filesSender;
  private final FirebaseAnalyticsApiAdapter firebaseAnalyticsApiAdapter;
  boolean forwardToFirebaseAnalyticsEnabled = false;
  private final d httpRequestFactory;
  boolean includePurchaseEventsInForwardedEvents = false;
  private final h kit;
  final SessionEventMetadata metadata;
  boolean predefinedEventsEnabled = true;
  private final AtomicReference<ScheduledFuture<?>> rolloverFutureRef = new AtomicReference();
  volatile int rolloverIntervalSeconds = -1;
  
  public EnabledSessionAnalyticsManagerStrategy(h paramh, Context paramContext, ScheduledExecutorService paramScheduledExecutorService, SessionAnalyticsFilesManager paramSessionAnalyticsFilesManager, d paramd, SessionEventMetadata paramSessionEventMetadata, FirebaseAnalyticsApiAdapter paramFirebaseAnalyticsApiAdapter)
  {
    this.kit = paramh;
    this.context = paramContext;
    this.executorService = paramScheduledExecutorService;
    this.filesManager = paramSessionAnalyticsFilesManager;
    this.httpRequestFactory = paramd;
    this.metadata = paramSessionEventMetadata;
    this.firebaseAnalyticsApiAdapter = paramFirebaseAnalyticsApiAdapter;
  }
  
  public void cancelTimeBasedFileRollOver()
  {
    if (this.rolloverFutureRef.get() != null)
    {
      io.fabric.sdk.android.services.b.i.a(this.context, "Cancelling time-based rollover because no events are currently being generated.");
      ((ScheduledFuture)this.rolloverFutureRef.get()).cancel(false);
      this.rolloverFutureRef.set(null);
    }
  }
  
  public void deleteAllEvents()
  {
    this.filesManager.deleteAllEventsFiles();
  }
  
  public void processEvent(SessionEvent.Builder paramBuilder)
  {
    paramBuilder = paramBuilder.build(this.metadata);
    Object localObject1;
    Object localObject2;
    if ((!this.customEventsEnabled) && (SessionEvent.Type.CUSTOM.equals(paramBuilder.type)))
    {
      localObject1 = c.g();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Custom events tracking disabled - skipping event: ");
      ((StringBuilder)localObject2).append(paramBuilder);
      ((k)localObject1).a("Answers", ((StringBuilder)localObject2).toString());
      return;
    }
    if ((!this.predefinedEventsEnabled) && (SessionEvent.Type.PREDEFINED.equals(paramBuilder.type)))
    {
      localObject2 = c.g();
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Predefined events tracking disabled - skipping event: ");
      ((StringBuilder)localObject1).append(paramBuilder);
      ((k)localObject2).a("Answers", ((StringBuilder)localObject1).toString());
      return;
    }
    if (this.eventFilter.skipEvent(paramBuilder))
    {
      localObject2 = c.g();
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Skipping filtered event: ");
      ((StringBuilder)localObject1).append(paramBuilder);
      ((k)localObject2).a("Answers", ((StringBuilder)localObject1).toString());
      return;
    }
    Object localObject3;
    try
    {
      this.filesManager.writeEvent(paramBuilder);
    }
    catch (IOException localIOException)
    {
      localObject3 = c.g();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Failed to write event: ");
      ((StringBuilder)localObject2).append(paramBuilder);
      ((k)localObject3).e("Answers", ((StringBuilder)localObject2).toString(), localIOException);
    }
    scheduleTimeBasedRollOverIfNeeded();
    int i;
    if ((!SessionEvent.Type.CUSTOM.equals(paramBuilder.type)) && (!SessionEvent.Type.PREDEFINED.equals(paramBuilder.type))) {
      i = 0;
    } else {
      i = 1;
    }
    boolean bool = "purchase".equals(paramBuilder.predefinedType);
    if ((this.forwardToFirebaseAnalyticsEnabled) && (i != 0))
    {
      if ((bool) && (!this.includePurchaseEventsInForwardedEvents)) {
        return;
      }
      try
      {
        this.firebaseAnalyticsApiAdapter.processEvent(paramBuilder);
      }
      catch (Exception localException)
      {
        k localk = c.g();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("Failed to map event to Firebase: ");
        ((StringBuilder)localObject3).append(paramBuilder);
        localk.e("Answers", ((StringBuilder)localObject3).toString(), localException);
      }
      return;
    }
  }
  
  public boolean rollFileOver()
  {
    try
    {
      boolean bool = this.filesManager.rollFileOver();
      return bool;
    }
    catch (IOException localIOException)
    {
      io.fabric.sdk.android.services.b.i.a(this.context, "Failed to roll file over.", localIOException);
    }
    return false;
  }
  
  void scheduleTimeBasedFileRollOver(long paramLong1, long paramLong2)
  {
    int i;
    if (this.rolloverFutureRef.get() == null) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      io.fabric.sdk.android.services.c.i locali = new io.fabric.sdk.android.services.c.i(this.context, this);
      Context localContext = this.context;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Scheduling time based file roll over every ");
      localStringBuilder.append(paramLong2);
      localStringBuilder.append(" seconds");
      io.fabric.sdk.android.services.b.i.a(localContext, localStringBuilder.toString());
      try
      {
        this.rolloverFutureRef.set(this.executorService.scheduleAtFixedRate(locali, paramLong1, paramLong2, TimeUnit.SECONDS));
      }
      catch (RejectedExecutionException localRejectedExecutionException)
      {
        io.fabric.sdk.android.services.b.i.a(this.context, "Failed to schedule time based file roll over", localRejectedExecutionException);
      }
    }
  }
  
  public void scheduleTimeBasedRollOverIfNeeded()
  {
    int i;
    if (this.rolloverIntervalSeconds != -1) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      scheduleTimeBasedFileRollOver(this.rolloverIntervalSeconds, this.rolloverIntervalSeconds);
    }
  }
  
  public void sendEvents()
  {
    if (this.filesSender == null)
    {
      io.fabric.sdk.android.services.b.i.a(this.context, "skipping files send because we don't yet know the target endpoint");
      return;
    }
    io.fabric.sdk.android.services.b.i.a(this.context, "Sending all files");
    List localList = this.filesManager.getBatchOfFilesToSend();
    int i = 0;
    for (;;)
    {
      int j = i;
      int k = i;
      try
      {
        if (localList.size() > 0)
        {
          j = i;
          io.fabric.sdk.android.services.b.i.a(this.context, String.format(Locale.US, "attempt to send batch of %d files", new Object[] { Integer.valueOf(localList.size()) }));
          j = i;
          boolean bool = this.filesSender.send(localList);
          k = i;
          if (bool)
          {
            j = i;
            k = i + localList.size();
            j = k;
            this.filesManager.deleteSentFiles(localList);
          }
          if (bool)
          {
            j = k;
            localList = this.filesManager.getBatchOfFilesToSend();
            i = k;
          }
        }
      }
      catch (Exception localException)
      {
        Context localContext = this.context;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Failed to send batch of analytics files to server: ");
        localStringBuilder.append(localException.getMessage());
        io.fabric.sdk.android.services.b.i.a(localContext, localStringBuilder.toString(), localException);
        k = j;
        if (k == 0) {
          this.filesManager.deleteOldestInRollOverIfOverMax();
        }
      }
    }
  }
  
  public void setAnalyticsSettingsData(b paramb, String paramString)
  {
    this.filesSender = AnswersRetryFilesSender.build(new SessionAnalyticsFilesSender(this.kit, paramString, paramb.a, this.httpRequestFactory, this.apiKey.a(this.context)));
    this.filesManager.setAnalyticsSettingsData(paramb);
    this.forwardToFirebaseAnalyticsEnabled = paramb.f;
    this.includePurchaseEventsInForwardedEvents = paramb.g;
    Object localObject1 = c.g();
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Firebase analytics forwarding ");
    if (this.forwardToFirebaseAnalyticsEnabled) {
      paramString = "enabled";
    } else {
      paramString = "disabled";
    }
    ((StringBuilder)localObject2).append(paramString);
    ((k)localObject1).a("Answers", ((StringBuilder)localObject2).toString());
    localObject1 = c.g();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Firebase analytics including purchase events ");
    if (this.includePurchaseEventsInForwardedEvents) {
      paramString = "enabled";
    } else {
      paramString = "disabled";
    }
    ((StringBuilder)localObject2).append(paramString);
    ((k)localObject1).a("Answers", ((StringBuilder)localObject2).toString());
    this.customEventsEnabled = paramb.h;
    localObject2 = c.g();
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Custom event tracking ");
    if (this.customEventsEnabled) {
      paramString = "enabled";
    } else {
      paramString = "disabled";
    }
    ((StringBuilder)localObject1).append(paramString);
    ((k)localObject2).a("Answers", ((StringBuilder)localObject1).toString());
    this.predefinedEventsEnabled = paramb.i;
    localObject1 = c.g();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Predefined event tracking ");
    if (this.predefinedEventsEnabled) {
      paramString = "enabled";
    } else {
      paramString = "disabled";
    }
    ((StringBuilder)localObject2).append(paramString);
    ((k)localObject1).a("Answers", ((StringBuilder)localObject2).toString());
    if (paramb.k > 1)
    {
      c.g().a("Answers", "Event sampling enabled");
      this.eventFilter = new SamplingEventFilter(paramb.k);
    }
    this.rolloverIntervalSeconds = paramb.b;
    scheduleTimeBasedFileRollOver(0L, this.rolloverIntervalSeconds);
  }
}


/* Location:              ~/com/crashlytics/android/answers/EnabledSessionAnalyticsManagerStrategy.class
 *
 * Reversed by:           J
 */