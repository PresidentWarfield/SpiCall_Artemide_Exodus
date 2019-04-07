package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;

class FirebaseAnalyticsApiAdapter
{
  private final Context context;
  private EventLogger eventLogger;
  private final FirebaseAnalyticsEventMapper eventMapper;
  
  public FirebaseAnalyticsApiAdapter(Context paramContext)
  {
    this(paramContext, new FirebaseAnalyticsEventMapper());
  }
  
  public FirebaseAnalyticsApiAdapter(Context paramContext, FirebaseAnalyticsEventMapper paramFirebaseAnalyticsEventMapper)
  {
    this.context = paramContext;
    this.eventMapper = paramFirebaseAnalyticsEventMapper;
  }
  
  public EventLogger getFirebaseAnalytics()
  {
    if (this.eventLogger == null) {
      this.eventLogger = AppMeasurementEventLogger.getEventLogger(this.context);
    }
    return this.eventLogger;
  }
  
  public void processEvent(SessionEvent paramSessionEvent)
  {
    Object localObject1 = getFirebaseAnalytics();
    if (localObject1 == null)
    {
      c.g().a("Answers", "Firebase analytics logging was enabled, but not available...");
      return;
    }
    Object localObject2 = this.eventMapper.mapEvent(paramSessionEvent);
    if (localObject2 == null)
    {
      localObject2 = c.g();
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Fabric event was not mappable to Firebase event: ");
      ((StringBuilder)localObject1).append(paramSessionEvent);
      ((k)localObject2).a("Answers", ((StringBuilder)localObject1).toString());
      return;
    }
    ((EventLogger)localObject1).logEvent(((FirebaseAnalyticsEvent)localObject2).getEventName(), ((FirebaseAnalyticsEvent)localObject2).getEventParams());
    if ("levelEnd".equals(paramSessionEvent.predefinedType)) {
      ((EventLogger)localObject1).logEvent("post_score", ((FirebaseAnalyticsEvent)localObject2).getEventParams());
    }
  }
}


/* Location:              ~/com/crashlytics/android/answers/FirebaseAnalyticsApiAdapter.class
 *
 * Reversed by:           J
 */