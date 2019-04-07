package com.crashlytics.android.answers;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import io.fabric.sdk.android.a;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.h;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.n;
import io.fabric.sdk.android.services.b.r;
import java.util.concurrent.ScheduledExecutorService;

class SessionAnalyticsManager
  implements BackgroundManager.Listener
{
  static final String EXECUTOR_SERVICE = "Answers Events Handler";
  static final String ON_CRASH_ERROR_MSG = "onCrash called from main thread!!!";
  final BackgroundManager backgroundManager;
  final AnswersEventsHandler eventsHandler;
  private final long installedAt;
  final a lifecycleManager;
  final AnswersPreferenceManager preferenceManager;
  
  SessionAnalyticsManager(AnswersEventsHandler paramAnswersEventsHandler, a parama, BackgroundManager paramBackgroundManager, AnswersPreferenceManager paramAnswersPreferenceManager, long paramLong)
  {
    this.eventsHandler = paramAnswersEventsHandler;
    this.lifecycleManager = parama;
    this.backgroundManager = paramBackgroundManager;
    this.preferenceManager = paramAnswersPreferenceManager;
    this.installedAt = paramLong;
  }
  
  public static SessionAnalyticsManager build(h paramh, Context paramContext, r paramr, String paramString1, String paramString2, long paramLong)
  {
    SessionMetadataCollector localSessionMetadataCollector = new SessionMetadataCollector(paramContext, paramr, paramString1, paramString2);
    paramString1 = new AnswersFilesManagerProvider(paramContext, new io.fabric.sdk.android.services.d.b(paramh));
    io.fabric.sdk.android.services.network.b localb = new io.fabric.sdk.android.services.network.b(c.g());
    paramString2 = new a(paramContext);
    ScheduledExecutorService localScheduledExecutorService = n.b("Answers Events Handler");
    paramr = new BackgroundManager(localScheduledExecutorService);
    return new SessionAnalyticsManager(new AnswersEventsHandler(paramh, paramContext, paramString1, localSessionMetadataCollector, localb, localScheduledExecutorService, new FirebaseAnalyticsApiAdapter(paramContext)), paramString2, paramr, AnswersPreferenceManager.build(paramContext), paramLong);
  }
  
  public void disable()
  {
    this.lifecycleManager.a();
    this.eventsHandler.disable();
  }
  
  public void enable()
  {
    this.eventsHandler.enable();
    this.lifecycleManager.a(new AnswersLifecycleCallbacks(this, this.backgroundManager));
    this.backgroundManager.registerListener(this);
    if (isFirstLaunch())
    {
      onInstall(this.installedAt);
      this.preferenceManager.setAnalyticsLaunched();
    }
  }
  
  boolean isFirstLaunch()
  {
    return this.preferenceManager.hasAnalyticsLaunched() ^ true;
  }
  
  public void onBackground()
  {
    c.g().a("Answers", "Flush events when app is backgrounded");
    this.eventsHandler.flushEvents();
  }
  
  public void onCrash(String paramString1, String paramString2)
  {
    if (Looper.myLooper() != Looper.getMainLooper())
    {
      c.g().a("Answers", "Logged crash");
      this.eventsHandler.processEventSync(SessionEvent.crashEventBuilder(paramString1, paramString2));
      return;
    }
    throw new IllegalStateException("onCrash called from main thread!!!");
  }
  
  public void onCustom(CustomEvent paramCustomEvent)
  {
    k localk = c.g();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Logged custom event: ");
    localStringBuilder.append(paramCustomEvent);
    localk.a("Answers", localStringBuilder.toString());
    this.eventsHandler.processEventAsync(SessionEvent.customEventBuilder(paramCustomEvent));
  }
  
  public void onError(String paramString) {}
  
  public void onInstall(long paramLong)
  {
    c.g().a("Answers", "Logged install");
    this.eventsHandler.processEventAsyncAndFlush(SessionEvent.installEventBuilder(paramLong));
  }
  
  public void onLifecycle(Activity paramActivity, SessionEvent.Type paramType)
  {
    k localk = c.g();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Logged lifecycle event: ");
    localStringBuilder.append(paramType.name());
    localk.a("Answers", localStringBuilder.toString());
    this.eventsHandler.processEventAsync(SessionEvent.lifecycleEventBuilder(paramType, paramActivity));
  }
  
  public void onPredefined(PredefinedEvent paramPredefinedEvent)
  {
    k localk = c.g();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Logged predefined event: ");
    localStringBuilder.append(paramPredefinedEvent);
    localk.a("Answers", localStringBuilder.toString());
    this.eventsHandler.processEventAsync(SessionEvent.predefinedEventBuilder(paramPredefinedEvent));
  }
  
  public void setAnalyticsSettingsData(io.fabric.sdk.android.services.e.b paramb, String paramString)
  {
    this.backgroundManager.setFlushOnBackground(paramb.j);
    this.eventsHandler.setAnalyticsSettingsData(paramb, paramString);
  }
}


/* Location:              ~/com/crashlytics/android/answers/SessionAnalyticsManager.class
 *
 * Reversed by:           J
 */