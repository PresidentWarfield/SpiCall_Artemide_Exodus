package com.crashlytics.android.answers;

public class CustomEvent
  extends AnswersEvent<CustomEvent>
{
  private final String eventName;
  
  public CustomEvent(String paramString)
  {
    if (paramString != null)
    {
      this.eventName = this.validator.limitStringLength(paramString);
      return;
    }
    throw new NullPointerException("eventName must not be null");
  }
  
  String getCustomType()
  {
    return this.eventName;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("{eventName:\"");
    localStringBuilder.append(this.eventName);
    localStringBuilder.append('"');
    localStringBuilder.append(", customAttributes:");
    localStringBuilder.append(this.customAttributes);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}


/* Location:              ~/com/crashlytics/android/answers/CustomEvent.class
 *
 * Reversed by:           J
 */