package com.crashlytics.android.answers;

import android.content.Context;
import android.os.Bundle;
import java.lang.reflect.Method;

public class AppMeasurementEventLogger
  implements EventLogger
{
  private static final String ANALYTIC_CLASS = "com.google.android.gms.measurement.AppMeasurement";
  private static final String GET_INSTANCE_METHOD = "getInstance";
  private static final String LOG_METHOD = "logEventInternal";
  private final Object logEventInstance;
  private final Method logEventMethod;
  
  public AppMeasurementEventLogger(Object paramObject, Method paramMethod)
  {
    this.logEventInstance = paramObject;
    this.logEventMethod = paramMethod;
  }
  
  private static Class getClass(Context paramContext)
  {
    try
    {
      paramContext = paramContext.getClassLoader().loadClass("com.google.android.gms.measurement.AppMeasurement");
      return paramContext;
    }
    catch (Exception paramContext) {}
    return null;
  }
  
  public static EventLogger getEventLogger(Context paramContext)
  {
    Class localClass = getClass(paramContext);
    if (localClass == null) {
      return null;
    }
    Object localObject = getInstance(paramContext, localClass);
    if (localObject == null) {
      return null;
    }
    paramContext = getLogEventMethod(paramContext, localClass);
    if (paramContext == null) {
      return null;
    }
    return new AppMeasurementEventLogger(localObject, paramContext);
  }
  
  private static Object getInstance(Context paramContext, Class paramClass)
  {
    try
    {
      paramContext = paramClass.getDeclaredMethod("getInstance", new Class[] { Context.class }).invoke(paramClass, new Object[] { paramContext });
      return paramContext;
    }
    catch (Exception paramContext) {}
    return null;
  }
  
  private static Method getLogEventMethod(Context paramContext, Class paramClass)
  {
    try
    {
      paramContext = paramClass.getDeclaredMethod("logEventInternal", new Class[] { String.class, String.class, Bundle.class });
      return paramContext;
    }
    catch (Exception paramContext) {}
    return null;
  }
  
  public void logEvent(String paramString, Bundle paramBundle)
  {
    logEvent("fab", paramString, paramBundle);
  }
  
  public void logEvent(String paramString1, String paramString2, Bundle paramBundle)
  {
    try
    {
      this.logEventMethod.invoke(this.logEventInstance, new Object[] { paramString1, paramString2, paramBundle });
      return;
    }
    catch (Exception paramString1)
    {
      for (;;) {}
    }
  }
}


/* Location:              ~/com/crashlytics/android/answers/AppMeasurementEventLogger.class
 *
 * Reversed by:           J
 */