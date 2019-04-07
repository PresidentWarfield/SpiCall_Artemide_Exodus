package com.crashlytics.android.answers;

public class InviteEvent
  extends PredefinedEvent<InviteEvent>
{
  static final String METHOD_ATTRIBUTE = "method";
  static final String TYPE = "invite";
  
  String getPredefinedType()
  {
    return "invite";
  }
  
  public InviteEvent putMethod(String paramString)
  {
    this.predefinedAttributes.put("method", paramString);
    return this;
  }
}


/* Location:              ~/com/crashlytics/android/answers/InviteEvent.class
 *
 * Reversed by:           J
 */