package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.h;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.e.b;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

class AnswersEventsHandler
  implements io.fabric.sdk.android.services.c.d
{
  private final Context context;
  final ScheduledExecutorService executor;
  private final AnswersFilesManagerProvider filesManagerProvider;
  private final FirebaseAnalyticsApiAdapter firebaseAnalyticsApiAdapter;
  private final h kit;
  private final SessionMetadataCollector metadataCollector;
  private final io.fabric.sdk.android.services.network.d requestFactory;
  SessionAnalyticsManagerStrategy strategy = new DisabledSessionAnalyticsManagerStrategy();
  
  public AnswersEventsHandler(h paramh, Context paramContext, AnswersFilesManagerProvider paramAnswersFilesManagerProvider, SessionMetadataCollector paramSessionMetadataCollector, io.fabric.sdk.android.services.network.d paramd, ScheduledExecutorService paramScheduledExecutorService, FirebaseAnalyticsApiAdapter paramFirebaseAnalyticsApiAdapter)
  {
    this.kit = paramh;
    this.context = paramContext;
    this.filesManagerProvider = paramAnswersFilesManagerProvider;
    this.metadataCollector = paramSessionMetadataCollector;
    this.requestFactory = paramd;
    this.executor = paramScheduledExecutorService;
    this.firebaseAnalyticsApiAdapter = paramFirebaseAnalyticsApiAdapter;
  }
  
  private void executeAsync(Runnable paramRunnable)
  {
    try
    {
      this.executor.submit(paramRunnable);
    }
    catch (Exception paramRunnable)
    {
      c.g().e("Answers", "Failed to submit events task", paramRunnable);
    }
  }
  
  private void executeSync(Runnable paramRunnable)
  {
    try
    {
      this.executor.submit(paramRunnable).get();
    }
    catch (Exception paramRunnable)
    {
      c.g().e("Answers", "Failed to run events task", paramRunnable);
    }
  }
  
  public void disable()
  {
    executeAsync(new Runnable()
    {
      public void run()
      {
        try
        {
          SessionAnalyticsManagerStrategy localSessionAnalyticsManagerStrategy = AnswersEventsHandler.this.strategy;
          AnswersEventsHandler localAnswersEventsHandler = AnswersEventsHandler.this;
          DisabledSessionAnalyticsManagerStrategy localDisabledSessionAnalyticsManagerStrategy = new com/crashlytics/android/answers/DisabledSessionAnalyticsManagerStrategy;
          localDisabledSessionAnalyticsManagerStrategy.<init>();
          localAnswersEventsHandler.strategy = localDisabledSessionAnalyticsManagerStrategy;
          localSessionAnalyticsManagerStrategy.deleteAllEvents();
        }
        catch (Exception localException)
        {
          c.g().e("Answers", "Failed to disable events", localException);
        }
      }
    });
  }
  
  public void enable()
  {
    executeAsync(new Runnable()
    {
      public void run()
      {
        try
        {
          SessionEventMetadata localSessionEventMetadata = AnswersEventsHandler.this.metadataCollector.getMetadata();
          SessionAnalyticsFilesManager localSessionAnalyticsFilesManager = AnswersEventsHandler.this.filesManagerProvider.getAnalyticsFilesManager();
          localSessionAnalyticsFilesManager.registerRollOverListener(AnswersEventsHandler.this);
          AnswersEventsHandler localAnswersEventsHandler = AnswersEventsHandler.this;
          EnabledSessionAnalyticsManagerStrategy localEnabledSessionAnalyticsManagerStrategy = new com/crashlytics/android/answers/EnabledSessionAnalyticsManagerStrategy;
          localEnabledSessionAnalyticsManagerStrategy.<init>(AnswersEventsHandler.this.kit, AnswersEventsHandler.this.context, AnswersEventsHandler.this.executor, localSessionAnalyticsFilesManager, AnswersEventsHandler.this.requestFactory, localSessionEventMetadata, AnswersEventsHandler.this.firebaseAnalyticsApiAdapter);
          localAnswersEventsHandler.strategy = localEnabledSessionAnalyticsManagerStrategy;
        }
        catch (Exception localException)
        {
          c.g().e("Answers", "Failed to enable events", localException);
        }
      }
    });
  }
  
  public void flushEvents()
  {
    executeAsync(new Runnable()
    {
      public void run()
      {
        try
        {
          AnswersEventsHandler.this.strategy.rollFileOver();
        }
        catch (Exception localException)
        {
          c.g().e("Answers", "Failed to flush events", localException);
        }
      }
    });
  }
  
  public void onRollOver(String paramString)
  {
    executeAsync(new Runnable()
    {
      public void run()
      {
        try
        {
          AnswersEventsHandler.this.strategy.sendEvents();
        }
        catch (Exception localException)
        {
          c.g().e("Answers", "Failed to send events files", localException);
        }
      }
    });
  }
  
  void processEvent(final SessionEvent.Builder paramBuilder, boolean paramBoolean1, final boolean paramBoolean2)
  {
    paramBuilder = new Runnable()
    {
      public void run()
      {
        try
        {
          AnswersEventsHandler.this.strategy.processEvent(paramBuilder);
          if (paramBoolean2) {
            AnswersEventsHandler.this.strategy.rollFileOver();
          }
        }
        catch (Exception localException)
        {
          c.g().e("Answers", "Failed to process event", localException);
        }
      }
    };
    if (paramBoolean1) {
      executeSync(paramBuilder);
    } else {
      executeAsync(paramBuilder);
    }
  }
  
  public void processEventAsync(SessionEvent.Builder paramBuilder)
  {
    processEvent(paramBuilder, false, false);
  }
  
  public void processEventAsyncAndFlush(SessionEvent.Builder paramBuilder)
  {
    processEvent(paramBuilder, false, true);
  }
  
  public void processEventSync(SessionEvent.Builder paramBuilder)
  {
    processEvent(paramBuilder, true, false);
  }
  
  public void setAnalyticsSettingsData(final b paramb, final String paramString)
  {
    executeAsync(new Runnable()
    {
      public void run()
      {
        try
        {
          AnswersEventsHandler.this.strategy.setAnalyticsSettingsData(paramb, paramString);
        }
        catch (Exception localException)
        {
          c.g().e("Answers", "Failed to set analytics settings data", localException);
        }
      }
    });
  }
}


/* Location:              ~/com/crashlytics/android/answers/AnswersEventsHandler.class
 *
 * Reversed by:           J
 */