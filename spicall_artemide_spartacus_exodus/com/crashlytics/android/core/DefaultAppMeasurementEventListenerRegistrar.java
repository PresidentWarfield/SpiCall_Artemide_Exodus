package com.crashlytics.android.core;

import android.content.Context;
import android.os.Bundle;
import io.fabric.sdk.android.c;
import io.fabric.sdk.android.k;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class DefaultAppMeasurementEventListenerRegistrar
  implements AppMeasurementEventListenerRegistrar
{
  private static final String ANALYTIC_CLASS = "com.google.android.gms.measurement.AppMeasurement";
  private static final String ANALYTIC_CLASS_ON_EVENT_LISTENER = "com.google.android.gms.measurement.AppMeasurement$OnEventListener";
  private static final String CRASH_ORIGIN = "crash";
  private static final String GET_INSTANCE_METHOD = "getInstance";
  private static final String NAME = "name";
  private static final String PARAMETERS = "parameters";
  private static final String REGISTER_METHOD = "registerOnMeasurementEventListener";
  private final CrashlyticsCore crashlyticsCore;
  
  private DefaultAppMeasurementEventListenerRegistrar(CrashlyticsCore paramCrashlyticsCore)
  {
    this.crashlyticsCore = paramCrashlyticsCore;
  }
  
  private Class<?> getClass(String paramString)
  {
    try
    {
      paramString = this.crashlyticsCore.getContext().getClassLoader().loadClass(paramString);
      return paramString;
    }
    catch (Exception paramString) {}
    return null;
  }
  
  private Object getInstance(Class<?> paramClass)
  {
    try
    {
      paramClass = paramClass.getDeclaredMethod("getInstance", new Class[] { Context.class }).invoke(paramClass, new Object[] { this.crashlyticsCore.getContext() });
      return paramClass;
    }
    catch (Exception paramClass)
    {
      c.g().a("CrashlyticsCore", "Could not get instance of com.google.android.gms.measurement.AppMeasurement", paramClass);
    }
    return null;
  }
  
  static AppMeasurementEventListenerRegistrar instanceFrom(CrashlyticsCore paramCrashlyticsCore)
  {
    return new DefaultAppMeasurementEventListenerRegistrar(paramCrashlyticsCore);
  }
  
  private boolean invoke(Class<?> paramClass, Object paramObject, String paramString)
  {
    Object localObject = getClass("com.google.android.gms.measurement.AppMeasurement$OnEventListener");
    if (localObject == null)
    {
      c.g().a("CrashlyticsCore", "Could not get class com.google.android.gms.measurement.AppMeasurement$OnEventListener");
      return false;
    }
    try
    {
      paramClass.getDeclaredMethod(paramString, new Class[] { localObject }).invoke(paramObject, new Object[] { onEventListenerProxy((Class)localObject) });
      return true;
    }
    catch (IllegalAccessException paramObject)
    {
      localObject = c.g();
      paramClass = new StringBuilder();
      paramClass.append("Cannot access method: ");
      paramClass.append(paramString);
      ((k)localObject).d("CrashlyticsCore", paramClass.toString(), (Throwable)paramObject);
      return false;
    }
    catch (InvocationTargetException paramObject)
    {
      paramClass = c.g();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Cannot invoke method: ");
      ((StringBuilder)localObject).append(paramString);
      paramClass.d("CrashlyticsCore", ((StringBuilder)localObject).toString(), (Throwable)paramObject);
      return false;
    }
    catch (NoSuchMethodException paramClass)
    {
      localObject = c.g();
      paramObject = new StringBuilder();
      ((StringBuilder)paramObject).append("Expected method missing: ");
      ((StringBuilder)paramObject).append(paramString);
      ((k)localObject).d("CrashlyticsCore", ((StringBuilder)paramObject).toString(), paramClass);
    }
    return false;
  }
  
  private Object onEventListenerProxy(Class paramClass)
  {
    ClassLoader localClassLoader = this.crashlyticsCore.getContext().getClassLoader();
    InvocationHandler local1 = new InvocationHandler()
    {
      public Object invoke(Object paramAnonymousObject, Method paramAnonymousMethod, Object[] paramAnonymousArrayOfObject)
      {
        if (paramAnonymousArrayOfObject.length == 4)
        {
          paramAnonymousObject = (String)paramAnonymousArrayOfObject[0];
          paramAnonymousMethod = (String)paramAnonymousArrayOfObject[1];
          paramAnonymousArrayOfObject = (Bundle)paramAnonymousArrayOfObject[2];
          if ((paramAnonymousObject != null) && (!((String)paramAnonymousObject).equals("crash"))) {
            DefaultAppMeasurementEventListenerRegistrar.writeEventToUserLog(DefaultAppMeasurementEventListenerRegistrar.this.crashlyticsCore, paramAnonymousMethod, paramAnonymousArrayOfObject);
          }
          return null;
        }
        throw new RuntimeException("Unexpected AppMeasurement.OnEventListener signature");
      }
    };
    return Proxy.newProxyInstance(localClassLoader, new Class[] { paramClass }, local1);
  }
  
  private static String serializeEvent(String paramString, Bundle paramBundle)
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    Iterator localIterator = paramBundle.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localJSONObject2.put(str, paramBundle.get(str));
    }
    localJSONObject1.put("name", paramString);
    localJSONObject1.put("parameters", localJSONObject2);
    return localJSONObject1.toString();
  }
  
  private static void writeEventToUserLog(CrashlyticsCore paramCrashlyticsCore, String paramString, Bundle paramBundle)
  {
    try
    {
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("$A$:");
      localStringBuilder.append(serializeEvent(paramString, paramBundle));
      paramCrashlyticsCore.log(localStringBuilder.toString());
    }
    catch (JSONException paramCrashlyticsCore)
    {
      paramCrashlyticsCore = c.g();
      paramBundle = new StringBuilder();
      paramBundle.append("Unable to serialize Firebase Analytics event; ");
      paramBundle.append(paramString);
      paramCrashlyticsCore.d("CrashlyticsCore", paramBundle.toString());
    }
  }
  
  public boolean register()
  {
    Class localClass = getClass("com.google.android.gms.measurement.AppMeasurement");
    if (localClass == null)
    {
      c.g().d("CrashlyticsCore", "Firebase Analytics is not present; you will not see automatic logging of events before a crash occurs.");
      return false;
    }
    Object localObject = getInstance(localClass);
    if (localObject == null)
    {
      c.g().d("CrashlyticsCore", "Could not create an instance of Firebase Analytics.");
      return false;
    }
    return invoke(localClass, localObject, "registerOnMeasurementEventListener");
  }
}


/* Location:              ~/com/crashlytics/android/core/DefaultAppMeasurementEventListenerRegistrar.class
 *
 * Reversed by:           J
 */