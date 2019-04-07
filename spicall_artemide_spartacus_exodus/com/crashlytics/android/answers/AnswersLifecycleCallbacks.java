package com.crashlytics.android.answers;

import android.app.Activity;
import android.os.Bundle;
import io.fabric.sdk.android.a.b;

class AnswersLifecycleCallbacks
  extends a.b
{
  private final SessionAnalyticsManager analyticsManager;
  private final BackgroundManager backgroundManager;
  
  public AnswersLifecycleCallbacks(SessionAnalyticsManager paramSessionAnalyticsManager, BackgroundManager paramBackgroundManager)
  {
    this.analyticsManager = paramSessionAnalyticsManager;
    this.backgroundManager = paramBackgroundManager;
  }
  
  public void onActivityCreated(Activity paramActivity, Bundle paramBundle) {}
  
  public void onActivityDestroyed(Activity paramActivity) {}
  
  public void onActivityPaused(Activity paramActivity)
  {
    this.analyticsManager.onLifecycle(paramActivity, SessionEvent.Type.PAUSE);
    this.backgroundManager.onActivityPaused();
  }
  
  public void onActivityResumed(Activity paramActivity)
  {
    this.analyticsManager.onLifecycle(paramActivity, SessionEvent.Type.RESUME);
    this.backgroundManager.onActivityResumed();
  }
  
  public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle) {}
  
  public void onActivityStarted(Activity paramActivity)
  {
    this.analyticsManager.onLifecycle(paramActivity, SessionEvent.Type.START);
  }
  
  public void onActivityStopped(Activity paramActivity)
  {
    this.analyticsManager.onLifecycle(paramActivity, SessionEvent.Type.STOP);
  }
}


/* Location:              ~/com/crashlytics/android/answers/AnswersLifecycleCallbacks.class
 *
 * Reversed by:           J
 */