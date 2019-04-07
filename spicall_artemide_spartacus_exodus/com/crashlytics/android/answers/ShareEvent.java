package com.crashlytics.android.answers;

public class ShareEvent
  extends PredefinedEvent<ShareEvent>
{
  static final String CONTENT_ID_ATTRIBUTE = "contentId";
  static final String CONTENT_NAME_ATTRIBUTE = "contentName";
  static final String CONTENT_TYPE_ATTRIBUTE = "contentType";
  static final String METHOD_ATTRIBUTE = "method";
  static final String TYPE = "share";
  
  String getPredefinedType()
  {
    return "share";
  }
  
  public ShareEvent putContentId(String paramString)
  {
    this.predefinedAttributes.put("contentId", paramString);
    return this;
  }
  
  public ShareEvent putContentName(String paramString)
  {
    this.predefinedAttributes.put("contentName", paramString);
    return this;
  }
  
  public ShareEvent putContentType(String paramString)
  {
    this.predefinedAttributes.put("contentType", paramString);
    return this;
  }
  
  public ShareEvent putMethod(String paramString)
  {
    this.predefinedAttributes.put("method", paramString);
    return this;
  }
}


/* Location:              ~/com/crashlytics/android/answers/ShareEvent.class
 *
 * Reversed by:           J
 */