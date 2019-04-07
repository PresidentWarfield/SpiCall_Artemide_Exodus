package com.crashlytics.android.answers;

import java.util.Map;

public abstract class PredefinedEvent<T extends PredefinedEvent>
  extends AnswersEvent<T>
{
  final AnswersAttributes predefinedAttributes = new AnswersAttributes(this.validator);
  
  Map<String, Object> getPredefinedAttributes()
  {
    return this.predefinedAttributes.attributes;
  }
  
  abstract String getPredefinedType();
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("{type:\"");
    localStringBuilder.append(getPredefinedType());
    localStringBuilder.append('"');
    localStringBuilder.append(", predefinedAttributes:");
    localStringBuilder.append(this.predefinedAttributes);
    localStringBuilder.append(", customAttributes:");
    localStringBuilder.append(this.customAttributes);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}


/* Location:              ~/com/crashlytics/android/answers/PredefinedEvent.class
 *
 * Reversed by:           J
 */