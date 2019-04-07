package com.crashlytics.android.core;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import io.fabric.sdk.android.services.d.c;
import io.fabric.sdk.android.services.d.d;

@SuppressLint({"CommitPrefEdits"})
class PreferenceManager
{
  static final String PREF_ALWAYS_SEND_REPORTS_KEY = "always_send_reports_opt_in";
  private static final String PREF_MIGRATION_COMPLETE = "preferences_migration_complete";
  private static final boolean SHOULD_ALWAYS_SEND_REPORTS_DEFAULT = false;
  private final CrashlyticsCore kit;
  private final c preferenceStore;
  
  public PreferenceManager(c paramc, CrashlyticsCore paramCrashlyticsCore)
  {
    this.preferenceStore = paramc;
    this.kit = paramCrashlyticsCore;
  }
  
  public static PreferenceManager create(c paramc, CrashlyticsCore paramCrashlyticsCore)
  {
    return new PreferenceManager(paramc, paramCrashlyticsCore);
  }
  
  void setShouldAlwaysSendReports(boolean paramBoolean)
  {
    c localc = this.preferenceStore;
    localc.a(localc.b().putBoolean("always_send_reports_opt_in", paramBoolean));
  }
  
  boolean shouldAlwaysSendReports()
  {
    if (!this.preferenceStore.a().contains("preferences_migration_complete"))
    {
      Object localObject = new d(this.kit);
      int i;
      if ((!this.preferenceStore.a().contains("always_send_reports_opt_in")) && (((c)localObject).a().contains("always_send_reports_opt_in"))) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        boolean bool = ((c)localObject).a().getBoolean("always_send_reports_opt_in", false);
        localObject = this.preferenceStore;
        ((c)localObject).a(((c)localObject).b().putBoolean("always_send_reports_opt_in", bool));
      }
      localObject = this.preferenceStore;
      ((c)localObject).a(((c)localObject).b().putBoolean("preferences_migration_complete", true));
    }
    return this.preferenceStore.a().getBoolean("always_send_reports_opt_in", false);
  }
}


/* Location:              ~/com/crashlytics/android/core/PreferenceManager.class
 *
 * Reversed by:           J
 */