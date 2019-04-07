package com.crashlytics.android.answers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import io.fabric.sdk.android.services.d.c;
import io.fabric.sdk.android.services.d.d;

class AnswersPreferenceManager
{
  static final String PREFKEY_ANALYTICS_LAUNCHED = "analytics_launched";
  static final String PREF_STORE_NAME = "settings";
  private final c prefStore;
  
  AnswersPreferenceManager(c paramc)
  {
    this.prefStore = paramc;
  }
  
  public static AnswersPreferenceManager build(Context paramContext)
  {
    return new AnswersPreferenceManager(new d(paramContext, "settings"));
  }
  
  @SuppressLint({"CommitPrefEdits"})
  public boolean hasAnalyticsLaunched()
  {
    return this.prefStore.a().getBoolean("analytics_launched", false);
  }
  
  @SuppressLint({"CommitPrefEdits"})
  public void setAnalyticsLaunched()
  {
    c localc = this.prefStore;
    localc.a(localc.b().putBoolean("analytics_launched", true));
  }
}


/* Location:              ~/com/crashlytics/android/answers/AnswersPreferenceManager.class
 *
 * Reversed by:           J
 */