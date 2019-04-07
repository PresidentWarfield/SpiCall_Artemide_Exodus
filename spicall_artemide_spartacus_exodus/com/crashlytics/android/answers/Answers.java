package com.crashlytics.android.answers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.h;
import io.fabric.sdk.android.k;
import io.fabric.sdk.android.services.b.i;
import io.fabric.sdk.android.services.b.j.a;
import io.fabric.sdk.android.services.b.j.b;
import io.fabric.sdk.android.services.e.m;
import io.fabric.sdk.android.services.e.t;
import java.io.File;

public class Answers
  extends h<Boolean>
{
  static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
  public static final String TAG = "Answers";
  SessionAnalyticsManager analyticsManager;
  boolean firebaseEnabled = false;
  
  public static Answers getInstance()
  {
    return (Answers)c.a(Answers.class);
  }
  
  private void logFirebaseModeEnabledWarning(String paramString)
  {
    k localk = c.g();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Method ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(" is not supported when using Crashlytics through Firebase.");
    localk.d("Answers", localStringBuilder.toString());
  }
  
  protected Boolean doInBackground()
  {
    if (!new io.fabric.sdk.android.services.b.q().c(getContext()))
    {
      c.g().a("Fabric", "Analytics collection disabled, because data collection is disabled by Firebase.");
      this.analyticsManager.disable();
      return Boolean.valueOf(false);
    }
    try
    {
      t localt = io.fabric.sdk.android.services.e.q.a().b();
      if (localt == null)
      {
        c.g().e("Answers", "Failed to retrieve settings");
        return Boolean.valueOf(false);
      }
      if (localt.d.d)
      {
        c.g().a("Answers", "Analytics collection enabled");
        this.analyticsManager.setAnalyticsSettingsData(localt.e, getOverridenSpiEndpoint());
        return Boolean.valueOf(true);
      }
      c.g().a("Answers", "Analytics collection disabled");
      this.analyticsManager.disable();
      return Boolean.valueOf(false);
    }
    catch (Exception localException)
    {
      c.g().e("Answers", "Error dealing with settings", localException);
    }
    return Boolean.valueOf(false);
  }
  
  public String getIdentifier()
  {
    return "com.crashlytics.sdk.android:answers";
  }
  
  String getOverridenSpiEndpoint()
  {
    return i.b(getContext(), "com.crashlytics.ApiEndpoint");
  }
  
  public String getVersion()
  {
    return "1.4.3.27";
  }
  
  public void logAddToCart(AddToCartEvent paramAddToCartEvent)
  {
    if (paramAddToCartEvent != null)
    {
      if (this.firebaseEnabled)
      {
        logFirebaseModeEnabledWarning("logAddToCart");
        return;
      }
      SessionAnalyticsManager localSessionAnalyticsManager = this.analyticsManager;
      if (localSessionAnalyticsManager != null) {
        localSessionAnalyticsManager.onPredefined(paramAddToCartEvent);
      }
      return;
    }
    throw new NullPointerException("event must not be null");
  }
  
  public void logContentView(ContentViewEvent paramContentViewEvent)
  {
    if (paramContentViewEvent != null)
    {
      if (this.firebaseEnabled)
      {
        logFirebaseModeEnabledWarning("logContentView");
        return;
      }
      SessionAnalyticsManager localSessionAnalyticsManager = this.analyticsManager;
      if (localSessionAnalyticsManager != null) {
        localSessionAnalyticsManager.onPredefined(paramContentViewEvent);
      }
      return;
    }
    throw new NullPointerException("event must not be null");
  }
  
  public void logCustom(CustomEvent paramCustomEvent)
  {
    if (paramCustomEvent != null)
    {
      if (this.firebaseEnabled)
      {
        logFirebaseModeEnabledWarning("logCustom");
        return;
      }
      SessionAnalyticsManager localSessionAnalyticsManager = this.analyticsManager;
      if (localSessionAnalyticsManager != null) {
        localSessionAnalyticsManager.onCustom(paramCustomEvent);
      }
      return;
    }
    throw new NullPointerException("event must not be null");
  }
  
  public void logInvite(InviteEvent paramInviteEvent)
  {
    if (paramInviteEvent != null)
    {
      if (this.firebaseEnabled)
      {
        logFirebaseModeEnabledWarning("logInvite");
        return;
      }
      SessionAnalyticsManager localSessionAnalyticsManager = this.analyticsManager;
      if (localSessionAnalyticsManager != null) {
        localSessionAnalyticsManager.onPredefined(paramInviteEvent);
      }
      return;
    }
    throw new NullPointerException("event must not be null");
  }
  
  public void logLevelEnd(LevelEndEvent paramLevelEndEvent)
  {
    if (paramLevelEndEvent != null)
    {
      if (this.firebaseEnabled)
      {
        logFirebaseModeEnabledWarning("logLevelEnd");
        return;
      }
      SessionAnalyticsManager localSessionAnalyticsManager = this.analyticsManager;
      if (localSessionAnalyticsManager != null) {
        localSessionAnalyticsManager.onPredefined(paramLevelEndEvent);
      }
      return;
    }
    throw new NullPointerException("event must not be null");
  }
  
  public void logLevelStart(LevelStartEvent paramLevelStartEvent)
  {
    if (paramLevelStartEvent != null)
    {
      if (this.firebaseEnabled)
      {
        logFirebaseModeEnabledWarning("logLevelStart");
        return;
      }
      SessionAnalyticsManager localSessionAnalyticsManager = this.analyticsManager;
      if (localSessionAnalyticsManager != null) {
        localSessionAnalyticsManager.onPredefined(paramLevelStartEvent);
      }
      return;
    }
    throw new NullPointerException("event must not be null");
  }
  
  public void logLogin(LoginEvent paramLoginEvent)
  {
    if (paramLoginEvent != null)
    {
      if (this.firebaseEnabled)
      {
        logFirebaseModeEnabledWarning("logLogin");
        return;
      }
      SessionAnalyticsManager localSessionAnalyticsManager = this.analyticsManager;
      if (localSessionAnalyticsManager != null) {
        localSessionAnalyticsManager.onPredefined(paramLoginEvent);
      }
      return;
    }
    throw new NullPointerException("event must not be null");
  }
  
  public void logPurchase(PurchaseEvent paramPurchaseEvent)
  {
    if (paramPurchaseEvent != null)
    {
      if (this.firebaseEnabled)
      {
        logFirebaseModeEnabledWarning("logPurchase");
        return;
      }
      SessionAnalyticsManager localSessionAnalyticsManager = this.analyticsManager;
      if (localSessionAnalyticsManager != null) {
        localSessionAnalyticsManager.onPredefined(paramPurchaseEvent);
      }
      return;
    }
    throw new NullPointerException("event must not be null");
  }
  
  public void logRating(RatingEvent paramRatingEvent)
  {
    if (paramRatingEvent != null)
    {
      if (this.firebaseEnabled)
      {
        logFirebaseModeEnabledWarning("logRating");
        return;
      }
      SessionAnalyticsManager localSessionAnalyticsManager = this.analyticsManager;
      if (localSessionAnalyticsManager != null) {
        localSessionAnalyticsManager.onPredefined(paramRatingEvent);
      }
      return;
    }
    throw new NullPointerException("event must not be null");
  }
  
  public void logSearch(SearchEvent paramSearchEvent)
  {
    if (paramSearchEvent != null)
    {
      if (this.firebaseEnabled)
      {
        logFirebaseModeEnabledWarning("logSearch");
        return;
      }
      SessionAnalyticsManager localSessionAnalyticsManager = this.analyticsManager;
      if (localSessionAnalyticsManager != null) {
        localSessionAnalyticsManager.onPredefined(paramSearchEvent);
      }
      return;
    }
    throw new NullPointerException("event must not be null");
  }
  
  public void logShare(ShareEvent paramShareEvent)
  {
    if (paramShareEvent != null)
    {
      if (this.firebaseEnabled)
      {
        logFirebaseModeEnabledWarning("logShare");
        return;
      }
      SessionAnalyticsManager localSessionAnalyticsManager = this.analyticsManager;
      if (localSessionAnalyticsManager != null) {
        localSessionAnalyticsManager.onPredefined(paramShareEvent);
      }
      return;
    }
    throw new NullPointerException("event must not be null");
  }
  
  public void logSignUp(SignUpEvent paramSignUpEvent)
  {
    if (paramSignUpEvent != null)
    {
      if (this.firebaseEnabled)
      {
        logFirebaseModeEnabledWarning("logSignUp");
        return;
      }
      SessionAnalyticsManager localSessionAnalyticsManager = this.analyticsManager;
      if (localSessionAnalyticsManager != null) {
        localSessionAnalyticsManager.onPredefined(paramSignUpEvent);
      }
      return;
    }
    throw new NullPointerException("event must not be null");
  }
  
  public void logStartCheckout(StartCheckoutEvent paramStartCheckoutEvent)
  {
    if (paramStartCheckoutEvent != null)
    {
      if (this.firebaseEnabled)
      {
        logFirebaseModeEnabledWarning("logStartCheckout");
        return;
      }
      SessionAnalyticsManager localSessionAnalyticsManager = this.analyticsManager;
      if (localSessionAnalyticsManager != null) {
        localSessionAnalyticsManager.onPredefined(paramStartCheckoutEvent);
      }
      return;
    }
    throw new NullPointerException("event must not be null");
  }
  
  public void onException(j.a parama)
  {
    SessionAnalyticsManager localSessionAnalyticsManager = this.analyticsManager;
    if (localSessionAnalyticsManager != null) {
      localSessionAnalyticsManager.onCrash(parama.a(), parama.b());
    }
  }
  
  public void onException(j.b paramb)
  {
    SessionAnalyticsManager localSessionAnalyticsManager = this.analyticsManager;
    if (localSessionAnalyticsManager != null) {
      localSessionAnalyticsManager.onError(paramb.a());
    }
  }
  
  @SuppressLint({"NewApi"})
  protected boolean onPreExecute()
  {
    try
    {
      Context localContext = getContext();
      Object localObject1 = localContext.getPackageManager();
      String str1 = localContext.getPackageName();
      Object localObject2 = ((PackageManager)localObject1).getPackageInfo(str1, 0);
      String str2 = Integer.toString(((PackageInfo)localObject2).versionCode);
      if (((PackageInfo)localObject2).versionName == null) {
        localObject3 = "0.0";
      } else {
        localObject3 = ((PackageInfo)localObject2).versionName;
      }
      long l;
      if (Build.VERSION.SDK_INT >= 9)
      {
        l = ((PackageInfo)localObject2).firstInstallTime;
      }
      else
      {
        localObject1 = ((PackageManager)localObject1).getApplicationInfo(str1, 0);
        localObject2 = new java/io/File;
        ((File)localObject2).<init>(((ApplicationInfo)localObject1).sourceDir);
        l = ((File)localObject2).lastModified();
      }
      this.analyticsManager = SessionAnalyticsManager.build(this, localContext, getIdManager(), str2, (String)localObject3, l);
      this.analyticsManager.enable();
      Object localObject3 = new io/fabric/sdk/android/services/b/q;
      ((io.fabric.sdk.android.services.b.q)localObject3).<init>();
      this.firebaseEnabled = ((io.fabric.sdk.android.services.b.q)localObject3).b(localContext);
      return true;
    }
    catch (Exception localException)
    {
      c.g().e("Answers", "Error retrieving app properties", localException);
    }
    return false;
  }
}


/* Location:              ~/com/crashlytics/android/answers/Answers.class
 *
 * Reversed by:           J
 */