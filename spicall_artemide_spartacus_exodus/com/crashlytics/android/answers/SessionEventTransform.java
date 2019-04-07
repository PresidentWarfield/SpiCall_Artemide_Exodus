package com.crashlytics.android.answers;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import io.fabric.sdk.android.services.c.a;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

class SessionEventTransform
  implements a<SessionEvent>
{
  static final String APP_BUNDLE_ID_KEY = "appBundleId";
  static final String APP_VERSION_CODE_KEY = "appVersionCode";
  static final String APP_VERSION_NAME_KEY = "appVersionName";
  static final String BETA_DEVICE_TOKEN_KEY = "betaDeviceToken";
  static final String BUILD_ID_KEY = "buildId";
  static final String CUSTOM_ATTRIBUTES = "customAttributes";
  static final String CUSTOM_TYPE = "customType";
  static final String DETAILS_KEY = "details";
  static final String DEVICE_MODEL_KEY = "deviceModel";
  static final String EXECUTION_ID_KEY = "executionId";
  static final String INSTALLATION_ID_KEY = "installationId";
  static final String LIMIT_AD_TRACKING_ENABLED_KEY = "limitAdTrackingEnabled";
  static final String OS_VERSION_KEY = "osVersion";
  static final String PREDEFINED_ATTRIBUTES = "predefinedAttributes";
  static final String PREDEFINED_TYPE = "predefinedType";
  static final String TIMESTAMP_KEY = "timestamp";
  static final String TYPE_KEY = "type";
  
  @TargetApi(9)
  public JSONObject buildJsonForEvent(SessionEvent paramSessionEvent)
  {
    try
    {
      JSONObject localJSONObject = new org/json/JSONObject;
      localJSONObject.<init>();
      Object localObject = paramSessionEvent.sessionEventMetadata;
      localJSONObject.put("appBundleId", ((SessionEventMetadata)localObject).appBundleId);
      localJSONObject.put("executionId", ((SessionEventMetadata)localObject).executionId);
      localJSONObject.put("installationId", ((SessionEventMetadata)localObject).installationId);
      localJSONObject.put("limitAdTrackingEnabled", ((SessionEventMetadata)localObject).limitAdTrackingEnabled);
      localJSONObject.put("betaDeviceToken", ((SessionEventMetadata)localObject).betaDeviceToken);
      localJSONObject.put("buildId", ((SessionEventMetadata)localObject).buildId);
      localJSONObject.put("osVersion", ((SessionEventMetadata)localObject).osVersion);
      localJSONObject.put("deviceModel", ((SessionEventMetadata)localObject).deviceModel);
      localJSONObject.put("appVersionCode", ((SessionEventMetadata)localObject).appVersionCode);
      localJSONObject.put("appVersionName", ((SessionEventMetadata)localObject).appVersionName);
      localJSONObject.put("timestamp", paramSessionEvent.timestamp);
      localJSONObject.put("type", paramSessionEvent.type.toString());
      if (paramSessionEvent.details != null)
      {
        localObject = new org/json/JSONObject;
        ((JSONObject)localObject).<init>(paramSessionEvent.details);
        localJSONObject.put("details", localObject);
      }
      localJSONObject.put("customType", paramSessionEvent.customType);
      if (paramSessionEvent.customAttributes != null)
      {
        localObject = new org/json/JSONObject;
        ((JSONObject)localObject).<init>(paramSessionEvent.customAttributes);
        localJSONObject.put("customAttributes", localObject);
      }
      localJSONObject.put("predefinedType", paramSessionEvent.predefinedType);
      if (paramSessionEvent.predefinedAttributes != null)
      {
        localObject = new org/json/JSONObject;
        ((JSONObject)localObject).<init>(paramSessionEvent.predefinedAttributes);
        localJSONObject.put("predefinedAttributes", localObject);
      }
      return localJSONObject;
    }
    catch (JSONException paramSessionEvent)
    {
      if (Build.VERSION.SDK_INT >= 9) {
        throw new IOException(paramSessionEvent.getMessage(), paramSessionEvent);
      }
      throw new IOException(paramSessionEvent.getMessage());
    }
  }
  
  public byte[] toBytes(SessionEvent paramSessionEvent)
  {
    return buildJsonForEvent(paramSessionEvent).toString().getBytes("UTF-8");
  }
}


/* Location:              ~/com/crashlytics/android/answers/SessionEventTransform.class
 *
 * Reversed by:           J
 */