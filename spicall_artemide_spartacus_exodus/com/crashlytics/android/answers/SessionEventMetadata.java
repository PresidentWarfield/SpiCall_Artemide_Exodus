package com.crashlytics.android.answers;

final class SessionEventMetadata
{
  public final String appBundleId;
  public final String appVersionCode;
  public final String appVersionName;
  public final String betaDeviceToken;
  public final String buildId;
  public final String deviceModel;
  public final String executionId;
  public final String installationId;
  public final Boolean limitAdTrackingEnabled;
  public final String osVersion;
  private String stringRepresentation;
  
  public SessionEventMetadata(String paramString1, String paramString2, String paramString3, Boolean paramBoolean, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    this.appBundleId = paramString1;
    this.executionId = paramString2;
    this.installationId = paramString3;
    this.limitAdTrackingEnabled = paramBoolean;
    this.betaDeviceToken = paramString4;
    this.buildId = paramString5;
    this.osVersion = paramString6;
    this.deviceModel = paramString7;
    this.appVersionCode = paramString8;
    this.appVersionName = paramString9;
  }
  
  public String toString()
  {
    if (this.stringRepresentation == null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("appBundleId=");
      localStringBuilder.append(this.appBundleId);
      localStringBuilder.append(", executionId=");
      localStringBuilder.append(this.executionId);
      localStringBuilder.append(", installationId=");
      localStringBuilder.append(this.installationId);
      localStringBuilder.append(", limitAdTrackingEnabled=");
      localStringBuilder.append(this.limitAdTrackingEnabled);
      localStringBuilder.append(", betaDeviceToken=");
      localStringBuilder.append(this.betaDeviceToken);
      localStringBuilder.append(", buildId=");
      localStringBuilder.append(this.buildId);
      localStringBuilder.append(", osVersion=");
      localStringBuilder.append(this.osVersion);
      localStringBuilder.append(", deviceModel=");
      localStringBuilder.append(this.deviceModel);
      localStringBuilder.append(", appVersionCode=");
      localStringBuilder.append(this.appVersionCode);
      localStringBuilder.append(", appVersionName=");
      localStringBuilder.append(this.appVersionName);
      this.stringRepresentation = localStringBuilder.toString();
    }
    return this.stringRepresentation;
  }
}


/* Location:              ~/com/crashlytics/android/answers/SessionEventMetadata.class
 *
 * Reversed by:           J
 */