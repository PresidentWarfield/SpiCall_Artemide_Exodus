package io.fabric.sdk.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class a
{
  private final Application a;
  private a b;
  
  public a(Context paramContext)
  {
    this.a = ((Application)paramContext.getApplicationContext());
    if (Build.VERSION.SDK_INT >= 14) {
      this.b = new a(this.a);
    }
  }
  
  public void a()
  {
    a locala = this.b;
    if (locala != null) {
      a.a(locala);
    }
  }
  
  public boolean a(b paramb)
  {
    a locala = this.b;
    boolean bool;
    if ((locala != null) && (a.a(locala, paramb))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static class a
  {
    private final Set<Application.ActivityLifecycleCallbacks> a = new HashSet();
    private final Application b;
    
    a(Application paramApplication)
    {
      this.b = paramApplication;
    }
    
    @TargetApi(14)
    private void a()
    {
      Iterator localIterator = this.a.iterator();
      while (localIterator.hasNext())
      {
        Application.ActivityLifecycleCallbacks localActivityLifecycleCallbacks = (Application.ActivityLifecycleCallbacks)localIterator.next();
        this.b.unregisterActivityLifecycleCallbacks(localActivityLifecycleCallbacks);
      }
    }
    
    @TargetApi(14)
    private boolean a(final a.b paramb)
    {
      if (this.b != null)
      {
        paramb = new Application.ActivityLifecycleCallbacks()
        {
          public void onActivityCreated(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
          {
            paramb.onActivityCreated(paramAnonymousActivity, paramAnonymousBundle);
          }
          
          public void onActivityDestroyed(Activity paramAnonymousActivity)
          {
            paramb.onActivityDestroyed(paramAnonymousActivity);
          }
          
          public void onActivityPaused(Activity paramAnonymousActivity)
          {
            paramb.onActivityPaused(paramAnonymousActivity);
          }
          
          public void onActivityResumed(Activity paramAnonymousActivity)
          {
            paramb.onActivityResumed(paramAnonymousActivity);
          }
          
          public void onActivitySaveInstanceState(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
          {
            paramb.onActivitySaveInstanceState(paramAnonymousActivity, paramAnonymousBundle);
          }
          
          public void onActivityStarted(Activity paramAnonymousActivity)
          {
            paramb.onActivityStarted(paramAnonymousActivity);
          }
          
          public void onActivityStopped(Activity paramAnonymousActivity)
          {
            paramb.onActivityStopped(paramAnonymousActivity);
          }
        };
        this.b.registerActivityLifecycleCallbacks(paramb);
        this.a.add(paramb);
        return true;
      }
      return false;
    }
  }
  
  public static abstract class b
  {
    public void onActivityCreated(Activity paramActivity, Bundle paramBundle) {}
    
    public void onActivityDestroyed(Activity paramActivity) {}
    
    public void onActivityPaused(Activity paramActivity) {}
    
    public void onActivityResumed(Activity paramActivity) {}
    
    public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle) {}
    
    public void onActivityStarted(Activity paramActivity) {}
    
    public void onActivityStopped(Activity paramActivity) {}
  }
}


/* Location:              ~/io/fabric/sdk/android/a.class
 *
 * Reversed by:           J
 */