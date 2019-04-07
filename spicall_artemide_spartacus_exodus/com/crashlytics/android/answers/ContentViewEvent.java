package com.crashlytics.android.answers;

public class ContentViewEvent
  extends PredefinedEvent<ContentViewEvent>
{
  static final String CONTENT_ID_ATTRIBUTE = "contentId";
  static final String CONTENT_NAME_ATTRIBUTE = "contentName";
  static final String CONTENT_TYPE_ATTRIBUTE = "contentType";
  static final String TYPE = "contentView";
  
  String getPredefinedType()
  {
    return "contentView";
  }
  
  public ContentViewEvent putContentId(String paramString)
  {
    this.predefinedAttributes.put("contentId", paramString);
    return this;
  }
  
  public ContentViewEvent putContentName(String paramString)
  {
    this.predefinedAttributes.put("contentName", paramString);
    return this;
  }
  
  public ContentViewEvent putContentType(String paramString)
  {
    this.predefinedAttributes.put("contentType", paramString);
    return this;
  }
}


/* Location:              ~/com/crashlytics/android/answers/ContentViewEvent.class
 *
 * Reversed by:           J
 */