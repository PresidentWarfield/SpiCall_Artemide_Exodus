package com.crashlytics.android;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.beta.Beta;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsCore.Builder;
import com.crashlytics.android.core.CrashlyticsListener;
import com.crashlytics.android.core.PinningInfoProvider;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.h;
import io.fabric.sdk.android.i;
import io.fabric.sdk.android.k;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Crashlytics
  extends h<Void>
  implements i
{
  public static final String TAG = "Crashlytics";
  public final Answers answers;
  public final Beta beta;
  public final CrashlyticsCore core;
  public final Collection<? extends h> kits;
  
  public Crashlytics()
  {
    this(new Answers(), new Beta(), new CrashlyticsCore());
  }
  
  Crashlytics(Answers paramAnswers, Beta paramBeta, CrashlyticsCore paramCrashlyticsCore)
  {
    this.answers = paramAnswers;
    this.beta = paramBeta;
    this.core = paramCrashlyticsCore;
    this.kits = Collections.unmodifiableCollection(Arrays.asList(new h[] { paramAnswers, paramBeta, paramCrashlyticsCore }));
  }
  
  private static void checkInitialized()
  {
    if (getInstance() != null) {
      return;
    }
    throw new IllegalStateException("Crashlytics must be initialized by calling Fabric.with(Context) prior to calling Crashlytics.getInstance()");
  }
  
  public static Crashlytics getInstance()
  {
    return (Crashlytics)c.a(Crashlytics.class);
  }
  
  public static PinningInfoProvider getPinningInfoProvider()
  {
    checkInitialized();
    return getInstance().core.getPinningInfoProvider();
  }
  
  public static void log(int paramInt, String paramString1, String paramString2)
  {
    checkInitialized();
    getInstance().core.log(paramInt, paramString1, paramString2);
  }
  
  public static void log(String paramString)
  {
    checkInitialized();
    getInstance().core.log(paramString);
  }
  
  public static void logException(Throwable paramThrowable)
  {
    checkInitialized();
    getInstance().core.logException(paramThrowable);
  }
  
  public static void setBool(String paramString, boolean paramBoolean)
  {
    checkInitialized();
    getInstance().core.setBool(paramString, paramBoolean);
  }
  
  public static void setDouble(String paramString, double paramDouble)
  {
    checkInitialized();
    getInstance().core.setDouble(paramString, paramDouble);
  }
  
  public static void setFloat(String paramString, float paramFloat)
  {
    checkInitialized();
    getInstance().core.setFloat(paramString, paramFloat);
  }
  
  public static void setInt(String paramString, int paramInt)
  {
    checkInitialized();
    getInstance().core.setInt(paramString, paramInt);
  }
  
  public static void setLong(String paramString, long paramLong)
  {
    checkInitialized();
    getInstance().core.setLong(paramString, paramLong);
  }
  
  @Deprecated
  public static void setPinningInfoProvider(PinningInfoProvider paramPinningInfoProvider)
  {
    c.g().d("Crashlytics", "Use of Crashlytics.setPinningInfoProvider is deprecated");
  }
  
  public static void setString(String paramString1, String paramString2)
  {
    checkInitialized();
    getInstance().core.setString(paramString1, paramString2);
  }
  
  public static void setUserEmail(String paramString)
  {
    checkInitialized();
    getInstance().core.setUserEmail(paramString);
  }
  
  public static void setUserIdentifier(String paramString)
  {
    checkInitialized();
    getInstance().core.setUserIdentifier(paramString);
  }
  
  public static void setUserName(String paramString)
  {
    checkInitialized();
    getInstance().core.setUserName(paramString);
  }
  
  public void crash()
  {
    this.core.crash();
  }
  
  protected Void doInBackground()
  {
    return null;
  }
  
  @Deprecated
  public boolean getDebugMode()
  {
    c.g().d("Crashlytics", "Use of Crashlytics.getDebugMode is deprecated.");
    getFabric();
    return c.h();
  }
  
  public String getIdentifier()
  {
    return "com.crashlytics.sdk.android:crashlytics";
  }
  
  public Collection<? extends h> getKits()
  {
    return this.kits;
  }
  
  public String getVersion()
  {
    return "2.9.5.27";
  }
  
  @Deprecated
  public void setDebugMode(boolean paramBoolean)
  {
    c.g().d("Crashlytics", "Use of Crashlytics.setDebugMode is deprecated.");
  }
  
  @Deprecated
  public void setListener(CrashlyticsListener paramCrashlyticsListener)
  {
    try
    {
      this.core.setListener(paramCrashlyticsListener);
      return;
    }
    finally
    {
      paramCrashlyticsListener = finally;
      throw paramCrashlyticsListener;
    }
  }
  
  public boolean verifyPinning(URL paramURL)
  {
    return this.core.verifyPinning(paramURL);
  }
  
  public static class Builder
  {
    private Answers answers;
    private Beta beta;
    private CrashlyticsCore core;
    private CrashlyticsCore.Builder coreBuilder;
    
    private CrashlyticsCore.Builder getCoreBuilder()
    {
      try
      {
        if (this.coreBuilder == null)
        {
          localBuilder = new com/crashlytics/android/core/CrashlyticsCore$Builder;
          localBuilder.<init>();
          this.coreBuilder = localBuilder;
        }
        CrashlyticsCore.Builder localBuilder = this.coreBuilder;
        return localBuilder;
      }
      finally {}
    }
    
    public Builder answers(Answers paramAnswers)
    {
      if (paramAnswers != null)
      {
        if (this.answers == null)
        {
          this.answers = paramAnswers;
          return this;
        }
        throw new IllegalStateException("Answers Kit already set.");
      }
      throw new NullPointerException("Answers Kit must not be null.");
    }
    
    public Builder beta(Beta paramBeta)
    {
      if (paramBeta != null)
      {
        if (this.beta == null)
        {
          this.beta = paramBeta;
          return this;
        }
        throw new IllegalStateException("Beta Kit already set.");
      }
      throw new NullPointerException("Beta Kit must not be null.");
    }
    
    public Crashlytics build()
    {
      CrashlyticsCore.Builder localBuilder = this.coreBuilder;
      if (localBuilder != null) {
        if (this.core == null) {
          this.core = localBuilder.build();
        } else {
          throw new IllegalStateException("Must not use Deprecated methods delay(), disabled(), listener(), pinningInfoProvider() with core()");
        }
      }
      if (this.answers == null) {
        this.answers = new Answers();
      }
      if (this.beta == null) {
        this.beta = new Beta();
      }
      if (this.core == null) {
        this.core = new CrashlyticsCore();
      }
      return new Crashlytics(this.answers, this.beta, this.core);
    }
    
    public Builder core(CrashlyticsCore paramCrashlyticsCore)
    {
      if (paramCrashlyticsCore != null)
      {
        if (this.core == null)
        {
          this.core = paramCrashlyticsCore;
          return this;
        }
        throw new IllegalStateException("CrashlyticsCore Kit already set.");
      }
      throw new NullPointerException("CrashlyticsCore Kit must not be null.");
    }
    
    @Deprecated
    public Builder delay(float paramFloat)
    {
      getCoreBuilder().delay(paramFloat);
      return this;
    }
    
    @Deprecated
    public Builder disabled(boolean paramBoolean)
    {
      getCoreBuilder().disabled(paramBoolean);
      return this;
    }
    
    @Deprecated
    public Builder listener(CrashlyticsListener paramCrashlyticsListener)
    {
      getCoreBuilder().listener(paramCrashlyticsListener);
      return this;
    }
    
    @Deprecated
    public Builder pinningInfo(PinningInfoProvider paramPinningInfoProvider)
    {
      getCoreBuilder().pinningInfo(paramPinningInfoProvider);
      return this;
    }
  }
}


/* Location:              ~/com/crashlytics/android/Crashlytics.class
 *
 * Reversed by:           J
 */